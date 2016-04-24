 package uas.featureextractor.client;

import org.opencv.core.Mat;
import uas.featureextractor.lgip.MyMat;

public abstract class Operation {

	protected String infile;
	protected String outfile;
	protected Mat imageToExtractFeature;
	protected MyMat features;

	public abstract boolean DoOperation(String InputFile, String OutputFile);
}
