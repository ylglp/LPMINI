package org.lpmini.util;

public class InvalidDataValueException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidDataValueException() {}
	
	public InvalidDataValueException(String msg) {
		super(msg);
	}
	
}
