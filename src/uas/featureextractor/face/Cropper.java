package uas.featureextractor.face;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

import uas.featureextractor.client.Operation;

public class Cropper extends Operation {

	private CascadeClassifier classifier;
	private Size finalsize;

	public Cropper(Size psize) {
		try {
			classifier = new CascadeClassifier("cascade\\lbpcascade_frontalface.xml");
		} catch (Exception ex) {

		}
		finalsize = psize;
	}

	@Override
	public boolean DoOperation(String InputFile, String OutputFile) {
		boolean retvalue = false;
		this.infile = InputFile;
		this.outfile = OutputFile;
		try {
			Mat img = Imgcodecs.imread(infile);
			MatOfRect faces = new MatOfRect();
			classifier.detectMultiScale(img, faces);

			Rect[] facesArray = faces.toArray();
			if (facesArray.length > 0) {
				Mat cropped = new Mat(img, facesArray[0]);
			    //for (Rect rect : facesArray) {
			    //    Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
			    //}
				Imgproc.resize(cropped, cropped, finalsize);
				Mat gray = new Mat();
				Imgproc.cvtColor(cropped, gray, Imgproc.COLOR_BGR2GRAY);
				Imgcodecs.imwrite(outfile, gray);
				//Imgcodecs.imwrite(outfile, img);
				gray = null;
				retvalue = true;
			}

			img = null;
			faces = null;
			facesArray = null;

		} catch (Exception ex) {
			throw ex;
			// MyLogger.setException(ex.toString());
		}
		return retvalue;
	}
}






