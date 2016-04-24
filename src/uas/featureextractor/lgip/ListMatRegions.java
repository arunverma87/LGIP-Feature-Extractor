package uas.featureextractor.lgip;

import org.opencv.core.Point;

public class ListMatRegions {
	private int regno;
	private Point StartAddress;
	private Point EndAddress;

	public ListMatRegions() {

	}

	public ListMatRegions(int regno, Point startadd, Point endadd) {
		this.regno = regno;
		this.StartAddress = startadd;
		this.EndAddress = endadd;
	}

	public int getRegno() {
		return regno;
	}

	public void setRegno(int regno) {
		this.regno = regno;
	}

	public Point getStartAddress() {
		return StartAddress;
	}

	public void setStartAddress(Point startAddress) {
		StartAddress = startAddress;
	}

	public Point getEndAddress() {
		return EndAddress;
	}

	public void setEndAddress(Point endAddress) {
		EndAddress = endAddress;
	}
}
