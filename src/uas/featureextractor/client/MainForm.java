package uas.featureextractor.client;

import org.opencv.core.Core;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainForm extends Application {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("MainFormView.fxml"));
		Scene scene = new Scene(root, 700, 500);
		//Image icon = new Image("C:/Dev/JavaApplication/FeatureExtractorForDesktop/Icon/icon.png");
		//primaryStage.getIcons().add(icon);
		primaryStage.setTitle("LGIP Feature Extractor");
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#stop()
	 */
	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
		Platform.exit();
	}

}
