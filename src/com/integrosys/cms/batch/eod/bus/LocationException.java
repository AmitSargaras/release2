package com.integrosys.cms.batch.eod.bus;

public class LocationException extends Exception{

	private static final long serialVersionUID = 1L;

	public LocationException() {
		super();
	}
	public LocationException(String message) {
		super(message);
	}
	public LocationException(Throwable cause) {
		super(cause);
	}
	public LocationException(String message, Throwable cause) {
		super(message, cause);
	}

}
