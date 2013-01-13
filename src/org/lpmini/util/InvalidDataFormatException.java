package org.lpmini.util;

public class InvalidDataFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidDataFormatException() {}
	
	public InvalidDataFormatException(String msg) {
		super(msg);
	}

}
