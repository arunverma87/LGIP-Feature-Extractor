package uas.featureextractor.lgip;

public enum Regionsize {
	Six(6),
	Twelve(12);
	
	private int numVal;

	Regionsize(int numVal) {
        this.numVal = numVal;
    }

    public int Value() {
        return numVal;
    }
}
