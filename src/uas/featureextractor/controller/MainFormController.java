package uas.featureextractor.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Size;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uas.featureextractor.client.Operation;
import uas.featureextractor.face.Cropper;
import uas.featureextractor.lgip.Extractor;
import uas.featureextractor.lgip.Regionsize;
import uas.featureextractor.log.LogType;
import uas.featureextractor.log.MyLogger;
import uas.featureextractor.utils.Util;

public class MainFormController implements Initializable {

	@FXML
	private Button btnBrowse, btnStart;

	@FXML
	private TextField txtPath;

	@FXML
	private Label lblProcess, lblStatus, lblEx;

	@FXML
	private TextArea txtareadetail;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblStatus.textProperty().bind(MyLogger.logProperty());
		lblProcess.textProperty().bind(MyLogger.opeationProperty());
		lblEx.textProperty().bind(MyLogger.exceptionProperty());
		txtareadetail.textProperty().bind(MyLogger.detailProperty());
	}

	public void browseFolder(ActionEvent event) {
		DirectoryChooser dir = new DirectoryChooser();
		dir.setTitle("Image Path");
		File defaultDir = new File("D:\\");
		dir.setInitialDirectory(defaultDir);
		File selected = dir.showDialog(null);
		if (selected != null)
			txtPath.setText(selected.getAbsolutePath());
	}

	public void startProcess(ActionEvent event) {
		final String path = txtPath.getText();
		if (path.compareToIgnoreCase("") == 0) {
			generateDialog("Select image folder path", LogType.Info);
		} else {

			@SuppressWarnings("rawtypes")
			Task task = new Task<Void>() {
				@Override
				protected Void call() {
					doWork(path);
					return null;
				}
			};
			new Thread(task).start();
		}

	}

	private void doWork(String path) {

		File[] level1files, level2files;
		File root;
		int totallevel1, totallevel2;
		String infilepath, outfilename, outfilepathforCrop, outfilepathforExtract;
		String notcropped = "";
		int noofnotcropped = 0;
		Util genfunction = new Util();

		root = new File(path);

		if (root.exists()) {
			try {
				level1files = root.listFiles();
				totallevel1 = level1files.length;
				long starttime = System.currentTimeMillis();
				if (createdirectory("CroppedImages")) {

					createdirectory("features");

					Operation crop = new Cropper(new Size(72, 72));
					Operation extract = new Extractor(Regionsize.Twelve);

					outfilepathforCrop = "CroppedImages\\";
					outfilepathforExtract = "features\\";

					for (int level1 = 0; level1 < totallevel1; level1++) {
						level2files = level1files[level1].listFiles();
						totallevel2 = level2files.length;
						createdirectory(outfilepathforCrop + level1files[level1].getName() + "\\");
						createdirectory(outfilepathforExtract + level1files[level1].getName() + "\\");

						for (int level2 = 0; level2 < totallevel2; level2++) {

							outfilename = genfunction.removeExtention(level2files[level2].getName());
							SetUIValue(outfilename + " - crop", LogType.Operation);
							if (crop.DoOperation(level2files[level2].getAbsolutePath(),
									outfilepathforCrop + level1files[level1].getName() + "\\" + outfilename + ".png")) {
								infilepath = outfilepathforCrop + level1files[level1].getName() + "\\" + outfilename
										+ ".png";
								SetUIValue(outfilename + " - extract", LogType.Operation);
								extract.DoOperation(infilepath, outfilepathforExtract + level1files[level1].getName()
										+ "\\" + outfilename + ".txt");
								outfilename = "";
							} else {
								noofnotcropped++;
								notcropped += outfilename + "; ";
								SetUIValue("Could not crop face : " + noofnotcropped + " : " + notcropped,
										LogType.Detail);

							}
						}
						SetUIValue((level1 + 1) + ":" + level1files[level1].getName() + " completed.", LogType.Info);
					}

				}
				long stoptime = System.currentTimeMillis();
				SetUIValue("Total time taken: " + time(stoptime - starttime, TimeUnit.SECONDS), LogType.Info);

			} catch (Exception ex) {
				SetUIValue(ex.toString(), LogType.Error);
				// MyLogger.setException(ex.toString());
			}
		}
	}

	private long time(long elapsedtime, TimeUnit unit) {
		return unit.convert(elapsedtime, TimeUnit.MILLISECONDS);
	}

	private boolean createdirectory(String path) {
		try {

			// int i = 1 / 0;
			File file = new File(path);
			if (!file.exists()) {
				file.mkdir();
			}
			return true;
		} catch (Exception ex) {
			generateDialog("Error in createDirectory:" + path, LogType.Error);
			return false;
		}
	}

	private void generateDialog(final String message, final LogType type) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);

				Text text = new Text(message);
				text.setFont(new Font(20));
				if (type == LogType.Error)
					text.setFill(Color.RED);

				Button button = new Button("Ok");
				button.setPrefWidth(60);
				button.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
						dialog.close();
					}
				});
				dialog.setScene(new Scene(VBoxBuilder.create().children(text, button).alignment(Pos.CENTER)
						.padding(new Insets(20)).build()));
				dialog.sizeToScene();
				dialog.show();
			}
		});
	}

	private void SetUIValue(final String message, final LogType type) {

		switch (type) {
		default:
			break;
		case Error:
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					MyLogger.setException(message);
				}
			});
			break;
		case Info:
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					MyLogger.setLog(message);
				}
			});
			break;
		case Operation:
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					MyLogger.setOperation(message);
				}
			});
			break;
		case Detail:
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					MyLogger.setDetail(message);
				}
			});
			break;
		}
	}
}
