package com.sslee.batch.exception;

public class BatchException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public BatchException(String message) {
		super(message);
	}
	
	public BatchException(Exception e) {
		super(e);
	}
	
	public BatchException(String message,Exception e) {
		super(message,e);
	}

}
