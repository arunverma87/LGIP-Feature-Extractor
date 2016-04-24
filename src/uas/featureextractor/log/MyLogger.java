package uas.featureextractor.log;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MyLogger {

	private static StringProperty log = new SimpleStringProperty();
	private static StringProperty operation = new SimpleStringProperty();	
	private static StringProperty exception = new SimpleStringProperty();
	private static StringProperty detail = new SimpleStringProperty();

	public static final String getLog() {
		return log.get();
	}

	public static final void setLog(String message) {
		log.setValue(message);
	}

	public static StringProperty logProperty() {
		return log;
	}

	public static final String getOperation() {
		return operation.get();
	}

	public static final void setOperation(String op) {
		operation.setValue(op);
	}

	public static StringProperty opeationProperty() {
		return operation;
	}
	
	public static final String getException() {
		return exception.get();
	}

	public static final void setException(String message) {
		exception.setValue(message);
	}

	public static StringProperty exceptionProperty() {
		return exception;
	}
	
	public static final String getDetail() {
		return detail.get();
	}

	public static final void setDetail(String message) {
		detail.setValue(message);
	}

	public static StringProperty detailProperty() {
		return detail;
	}

}
