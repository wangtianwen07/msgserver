package com.css.msgserver.utils;

public class DecryptException extends RuntimeException {

	public DecryptException(String string) {
		super(string);
	}
	public DecryptException(String string,Exception e) {
		super(string,e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
