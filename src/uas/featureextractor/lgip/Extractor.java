package uas.featureextractor.lgip;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.opencv.imgcodecs.Imgcodecs;

import uas.featureextractor.client.Operation;
import uas.featureextractor.utils.Util;

public class Extractor extends Operation {

	public Extractor(Regionsize pregionsize) {
		try {
			features = new MyMat(pregionsize);
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public boolean DoOperation(String InputFile, String OutputFile) {
		boolean retvalue = false;
		this.infile = InputFile;
		this.outfile = OutputFile;
		try {
			Util genfunction = new Util();
			BufferedWriter writer = null;
			String datastring;
			imageToExtractFeature = Imgcodecs.imread(infile);

			features.setHeight(imageToExtractFeature.height());
			features.setWidth(imageToExtractFeature.width());
			features.setMatrix(imageToExtractFeature);
			features.CalculateRegions();

			datastring = genfunction.ConvertIntArraytoString(features.CalculateFeatures(), true);
			try {
				writer = new BufferedWriter(new FileWriter(outfile));
				writer.write(datastring);

			} catch (IOException ioex) {

			} finally {
				try {
					if (writer != null) {
						writer.close();
						retvalue = true;
					}
				} catch (IOException e) {
				}
			}
		} catch (Exception ex) {
			throw ex;
			// MyLogger.setException(ex.toString());
		}

		return retvalue;
	}

}
