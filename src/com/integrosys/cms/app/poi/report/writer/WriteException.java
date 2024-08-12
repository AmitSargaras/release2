package com.integrosys.cms.app.poi.report.writer;

public class WriteException extends RuntimeException {

	private static final long serialVersionUID = -3415286986171198361L;

	public WriteException() {
		super();
	}

	public WriteException(String message, Throwable cause) {
		super(message, cause);
	}

	public WriteException(String message) {
		super(message);
	}

	public WriteException(Throwable cause) {
		super(cause);
	}
}
