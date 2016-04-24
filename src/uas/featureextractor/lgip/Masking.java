package uas.featureextractor.lgip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.Mat;


public class Masking {
	String TAG = "featureextractor:Masking";
	private Mat matrix;
	private int imagesize;
	private int matrixcols = 0;
	private int matrixrows = 0;
	boolean prevrow = false, prevcol = false;
	boolean nextrow = true, nextcol = true;
	String lookuptable[][] = { { "-1", "-1", "34", "35", "36", "-1", "-1" },
			{ "-1", "20", "21", "22", "23", "24", "-1" }, { "33", "19", "6", "7", "8", "9", "25" },
			{ "32", "18", "5", "0", "1", "10", "26" }, { "31", "17", "4", "3", "2", "11", "27" },
			{ "-1", "16", "15", "14", "13", "12", "-1" }, { "-1", "-1", "30", "29", "28", "-1", "-1" } };

	Map<Integer, Integer> matrixmask = new HashMap<>();

	public Masking(Mat matrixForMasking, int imagesize) {
		this.matrix = matrixForMasking;
		this.imagesize = imagesize;
		matrixrows = matrix.rows();
		matrixcols = matrix.cols();
	}

	public Map<Integer, Integer> CalculateMask() {
		List<Integer> pixelmask = null;
		int atRow = 0, atColumn = 0;
		try {
			for (atRow = 0; atRow < matrix.rows(); atRow++) {
				if (atRow == matrix.rows() - 1)
					nextrow = false;
				prevcol = false;
				for (atColumn = 0; atColumn < matrix.cols(); atColumn++) {
					if (atColumn == matrix.cols() - 1)
						nextcol = false;
					pixelmask = new ArrayList<>();
					pixelmask.add(ApplyMask(LGIPMasks.M0, atRow, atColumn));
					pixelmask.add(ApplyMask(LGIPMasks.M1, atRow, atColumn));
					pixelmask.add(ApplyMask(LGIPMasks.M2, atRow, atColumn));
					pixelmask.add(ApplyMask(LGIPMasks.M3, atRow, atColumn));
					pixelmask.add(ApplyMask(LGIPMasks.M4, atRow, atColumn));
					pixelmask.add(ApplyMask(LGIPMasks.M5, atRow, atColumn));
					pixelmask.add(ApplyMask(LGIPMasks.M6, atRow, atColumn));
					pixelmask.add(ApplyMask(LGIPMasks.M7, atRow, atColumn));
					matrixmask.put(atRow * imagesize + atColumn, CalculateOTV(pixelmask));
					prevcol = true;
					pixelmask = null;
				}
				prevrow = true;
			}

		} catch (Exception ex) {
			matrixmask = null;
			//Log.e(TAG, ex.toString());
		}
		return matrixmask;
	}

	private int ApplyMask(LGIPMasks mask, int forRow, int forCol) {
		double value = (-2) * matrix.get(forRow, forCol)[0];
		try {
			switch (mask) {
			case M0:
				if (prevrow) {
					value += (-1) * matrix.get(forRow - 1, forCol)[0];
					if (nextcol)
						value += matrix.get(forRow - 1, forCol + 1)[0];
				}
				if (nextcol) {
					value += 2 * matrix.get(forRow, forCol + 1)[0];
					if (nextrow)
						value += matrix.get(forRow + 1, forCol + 1)[0];
				}
				if (nextrow) {
					value += (-1) * matrix.get(forRow + 1, forCol)[0];
				}
				break;
			case M1:
				if (prevrow) {
					if (prevcol)
						value += (-1) * matrix.get(forRow - 1, forCol - 1)[0];
					if (nextcol)
						value += 2 * matrix.get(forRow - 1, forCol + 1)[0];
					if (forRow - 2 >= 0)
						value += matrix.get(forRow - 2, forCol)[0];
				}
				if (nextcol) {
					if (nextrow)
						value += (-1) * matrix.get(forRow + 1, forCol + 1)[0];
					if (forCol + 2 <= matrixcols - 1)
						value += matrix.get(forRow, forCol + 2)[0];
				}
				break;
			case M2:
				if (prevrow) {
					value += 2 * matrix.get(forRow - 1, forCol)[0];
					if (prevcol)
						value += matrix.get(forRow - 1, forCol - 1)[0];
					if (nextcol)
						value += matrix.get(forRow - 1, forCol + 1)[0];
				}
				if (nextcol)
					value += (-1) * matrix.get(forRow, forCol + 1)[0];
				if (prevcol)
					value += (-1) * matrix.get(forRow, forCol - 1)[0];
				break;
			case M3:
				if (prevrow) {
					if (prevcol)
						value += 2 * matrix.get(forRow - 1, forCol - 1)[0];
					if (nextcol)
						value += (-1) * matrix.get(forRow - 1, forCol + 1)[0];
					if (forRow - 2 >= 0)
						value += matrix.get(forRow - 2, forCol)[0];
				}
				if (prevcol) {
					if (nextrow)
						value += (-1) * matrix.get(forRow + 1, forCol - 1)[0];
					if (forCol - 2 >= 0)
						value += matrix.get(forRow, forCol - 2)[0];
				}
				break;
			case M4:
				if (prevrow) {
					value += (-1) * matrix.get(forRow - 1, forCol)[0];
					if (prevcol)
						value += matrix.get(forRow - 1, forCol - 1)[0];
				}
				if (prevcol) {
					value += 2 * matrix.get(forRow, forCol - 1)[0];
					if (nextrow)
						value += matrix.get(forRow + 1, forCol - 1)[0];
				}
				if (nextrow) {
					value += (-1) * matrix.get(forRow + 1, forCol)[0];
				}
				break;
			case M5:
				if (prevcol) {
					if (prevrow)
						value += (-1) * matrix.get(forRow - 1, forCol - 1)[0];
					if (nextrow)
						value += 2 * matrix.get(forRow + 1, forCol - 1)[0];
					if (forCol - 2 >= 0)
						value += matrix.get(forRow, forCol - 2)[0];
				}
				if (nextrow) {
					if (nextcol)
						value += (-1) * matrix.get(forRow + 1, forCol + 1)[0];
					if (forRow + 2 <= matrixrows - 1)
						value += matrix.get(forRow + 2, forCol)[0];
				}
				break;
			case M6:
				if (nextrow) {
					value += 2 * matrix.get(forRow + 1, forCol)[0];
					if (prevcol)
						value += matrix.get(forRow + 1, forCol - 1)[0];
					if (nextcol)
						value += matrix.get(forRow + 1, forCol + 1)[0];
				}
				if (nextcol)
					value += (-1) * matrix.get(forRow, forCol + 1)[0];
				if (prevcol)
					value += (-1) * matrix.get(forRow, forCol - 1)[0];
				break;
			case M7:
				if (nextrow) {
					if (prevcol)
						value += (-1) * matrix.get(forRow + 1, forCol - 1)[0];
					if (nextcol)
						value += 2 * matrix.get(forRow + 1, forCol + 1)[0];
					if (forRow + 2 <= matrixrows - 1)
						value += matrix.get(forRow + 2, forCol)[0];
				}
				if (nextcol) {
					if (prevrow)
						value += (-1) * matrix.get(forRow - 1, forCol + 1)[0];
					if (forCol + 2 <= matrixcols - 1)
						value += matrix.get(forRow, forCol + 2)[0];
				}
				break;
			}
		} catch (Exception ex) {
			//Log.e(TAG, ex.toString());
			return 0;
		}
		return value > 0 ? 1 : 0;
	}

	private int CalculateOTV(List<Integer> pattern) {
		int value = 0;
		int otvx = 0, otvy = 0;
		try {
			otvx = pattern.get(0) + pattern.get(1) + pattern.get(7) - pattern.get(3) - pattern.get(4)
					- pattern.get(5);
			otvy = pattern.get(1) + pattern.get(2) + pattern.get(3) - pattern.get(5) - pattern.get(6)
					- pattern.get(7);

			value = Integer.parseInt(lookuptable[otvy + 3][otvx + 3]);

		} catch (Exception ex) {
			//Log.e(TAG, ex.toString());
			value = 0;
		}
		return value;
	}
}
