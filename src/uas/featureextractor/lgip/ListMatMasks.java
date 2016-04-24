package uas.featureextractor.lgip;

public class ListMatMasks {
	private int pixelno;
	private int maskvalue;
	
	public int getPixelno() {
		return pixelno;
	}

	public void setPixelno(int pixelno) {
		this.pixelno = pixelno;
	}

	public int getMaskvalue() {
		return maskvalue;
	}

	public void setMaskvalue(int maskvalue) {
		this.maskvalue = maskvalue;
	}

	public ListMatMasks() {
		pixelno = 0;
		maskvalue = 0;
	}

}
