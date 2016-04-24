package uas.featureextractor.lgip;

import java.util.HashMap;
import java.util.Map;

public class ListHistogramData {
	// private int regionindex;
	private Map<Integer, Integer> HistoData;

	public Map<Integer, Integer> getHistoData() {
		return HistoData;
	}

	public ListHistogramData() {
		// this.regionindex = region;
		HistoData = new HashMap<>();
	}

	public void AddLGIPPatternData(int pattern, int data) {
		HistoData.put(pattern, data);
	}

}
