package uas.featureextractor.lgip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.opencv.core.Mat;
import org.opencv.core.Point;


public class MyMat {

	private int height;
	private int width;
	private Mat matrix;
	private int regionsize;
	String TAG = "featureextractor:MyMat";

	private List<ListMatRegions> regions;

	public List<ListMatRegions> getRegions() {
		return regions;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public Mat getMatrix() {
		return matrix;
	}

	public void setMatrix(Mat matrix) {
		this.matrix = matrix;
	}
	
	public MyMat(int matheight, int matwidth, Mat mat, Regionsize matregionsize) {
		this.height = matheight;
		this.width = matwidth;
		this.matrix = mat;
		this.regionsize = matregionsize.Value();
		//CalculateRegions();
	}

	public MyMat(Regionsize matregionsize){
		this.regionsize = matregionsize.Value();
		//CalculateRegions();
	}
	
	public List<Integer> CalculateFeatures() {
		try {
			Masking mask = new Masking(matrix, height);
			Map<Integer, Integer> maskedvalue = mask.CalculateMask();			
			return CalculateHistogram(maskedvalue);
		} catch (Exception ex) {
			throw ex;
		}
	}

	public void CalculateRegions() {
		try {
			regions = new ArrayList<>();
			ListMatRegions singleregion = null;
			int regionno = 0;
			int xstart = 0, ystart = 0;
			int xend = 0, yend = 0;
			do {
				yend = ystart + regionsize - 1;
				do {
					regionno++;
					xend = xstart + regionsize - 1;
					singleregion = new ListMatRegions(regionno, new Point(xstart, ystart), new Point(xend,
							yend));
					regions.add(singleregion);
					xstart += (regionsize / 2);
				} while (xstart + (regionsize / 2) < width);
				xstart = 0;
				ystart += (regionsize / 2);
			} while (ystart + (regionsize / 2) < height);
		} catch (Exception ex) {
			throw ex;
		}
	}

	private List<Integer> CalculateHistogram(Map<Integer, Integer> data) {
		List<Integer> histogramdata = new ArrayList<>();
		int patternlabel;
		int regionlabelvalue = 0;
		double row, column;
		int maskeddata;
		Point regionstartpointer, regionendpointer;
		Map<Integer, ListHistogramData> histogramdataListwise = new HashMap<>();
		ListHistogramData regionwiselabeldata;
		try {
			for (ListMatRegions reg : regions) {
				regionwiselabeldata = new ListHistogramData();
				patternlabel = 0;
				regionstartpointer = reg.getStartAddress();
				regionendpointer = reg.getEndAddress();
				while (patternlabel < 37) {
					for (row = regionstartpointer.x; row <= regionendpointer.x; row++)
						for (column = regionstartpointer.y; column <= regionendpointer.y; column++) {
							maskeddata = data.get((int) (row * height + column));
							regionlabelvalue += (maskeddata == patternlabel) ? 1 : 0;
						}
					regionwiselabeldata.AddLGIPPatternData(patternlabel, regionlabelvalue);
					histogramdata.add(regionlabelvalue);
					regionlabelvalue = 0;
					patternlabel++;
				}
				histogramdataListwise.put(reg.getRegno(), regionwiselabeldata);
				regionwiselabeldata = null;
			}
		} catch (Exception ex) {
			throw ex;
		}
		return histogramdata;
	}
}
