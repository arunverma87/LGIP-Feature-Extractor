package uas.featureextractor.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {

	public List<Float> ConvertStringtoFloatArray(String vector) {
		// System.out.println(vector);
		String[] strVector = vector.split("	");
		List<Float> converted = new ArrayList<>();
		for (int i = 0; i < strVector.length; i++) {
			converted.add(Float.parseFloat(strVector[i]));
		}
		return converted;
	}

	public String ConvertFloatArraytoString(List<Float> vector, boolean withdelimeter) {
		String ltemp = "";
		for (int i = 0; i < vector.size(); i++) {
			if (withdelimeter)
				ltemp += vector.get(i).toString() + "	";
			else
				ltemp += vector.get(i).toString();
		}
		if (ltemp != "")
			if (withdelimeter)
				return ltemp.substring(0, ltemp.length() - 1);
			else
				return ltemp;
		else
			return "";
	}

	public List<Integer> ConvertStringtoIntArray(String vector) {
		// System.out.println(vector);
		String[] strVector = vector.split("	");
		List<Integer> converted = new ArrayList<>();
		for (int i = 0; i < strVector.length; i++) {
			converted.add(Integer.parseInt(strVector[i]));
		}
		return converted;
	}

	public String ConvertIntArraytoString(List<Integer> vector, boolean withdelimeter) {
		String ltemp = "";
		for (int i = 0; i < vector.size(); i++) {
			if (withdelimeter)
				ltemp += vector.get(i).toString() + "	";
			else
				ltemp += vector.get(i).toString();
		}
		if (ltemp != "")
			if (withdelimeter)
				return ltemp.substring(0, ltemp.length() - 1);
			else
				return ltemp;
		else
			return "";
	}

	public List<Boolean> ConvertStringtoBooleanArray(String vector) {
		String[] strVector = vector.split("	");
		List<Boolean> converted = new ArrayList<>();
		for (int i = 0; i < strVector.length; i++) {
			converted.add(strVector[i].equalsIgnoreCase("1") ? true : false);
		}
		return converted;
	}

	public String ConvertBooleanArrayToString(List<Boolean> vector, boolean withdelimeter) {
		String ltemp = "";
		for (int i = 0; i < vector.size(); i++) {
			if (withdelimeter)
				ltemp += (vector.get(i) == true ? "1" : "0") + "	";
			else
				ltemp += (vector.get(i) == true ? "1" : "0");
		}
		if (ltemp != "")
			if (withdelimeter)
				return ltemp.substring(0, ltemp.length() - 1);
			else
				return ltemp;
		else
			return "";
	}

	public List<String> DirectoryFiles(boolean Directory, String path) {
		List<String> directoryOrfiles = new ArrayList<String>();
		File dir = new File(path);
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				if (Directory) {
					directoryOrfiles.add(file.getPath());
				}
			} else {
				if (!Directory) {
					if (file.getName().endsWith((".txt"))) {
						directoryOrfiles.add(file.getPath());
					}
				}
			}
		}
		return directoryOrfiles;
	}

	public String ReadFile(String path) {
		String everything = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();
			everything = everything.substring(0, everything.length() - 2);
		} catch (Exception e) {

		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return everything;
	}

	public String getFileName(String path) {
		String filename;
		if (!path.contains("\\"))
			filename = "";
		else {
			filename = path.substring(path.lastIndexOf("\\") + 1);
		}
		return filename;
	}

	public String removeExtention(String filename) {
		try {
			int pos = filename.lastIndexOf(".");
			return pos > 0 ? filename.substring(0, pos) : filename;
		} catch (Exception ex) {
			return filename;
		}
	}

	public String getSubStringFileName(String name, String searchstring, int offset) {
		String filename;
		if (!name.contains(searchstring))
			filename = name;
		else {
			filename = name.substring(0, name.indexOf(searchstring) + offset);
		}
		return filename;
	}

}
