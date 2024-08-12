/**
 * 
 */
package com.integrosys.cms.app.poi.report;

/**
 * @author anil.pandey
 *
 */
public class ReportException extends RuntimeException {

	/**
	 * @param msg
	 */
	public ReportException(String msg) {
		super(msg);
	}


	/**
	 * @param msg
	 * @param cause
	 */
	public ReportException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
