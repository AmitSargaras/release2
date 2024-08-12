package com.integrosys.cms.batch.reports;

/**
 * Exception to be raised whenever there is invalid parameter / missing
 * parameters that required for report generation.
 * 
 * @author Chong Jun Yong
 * 
 */
public class InvalidReportParameterException extends ReportException {
	private String[] missingParameters;

	/**
	 * Constructor with error detail message supplied
	 * 
	 * @param msg error detail message
	 */
	public InvalidReportParameterException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with error detail message supplied and the missing parameters
	 * which will be output
	 * 
	 * @param msg error detail message
	 * @param missingParameters missing parameters that required for the report
	 *        generation
	 * 
	 */
	public InvalidReportParameterException(String msg, String[] missingParameters) {
		super(msg);
		this.missingParameters = missingParameters;
	}

	public String getMessage() {
		StringBuffer buf = new StringBuffer();
		buf.append(super.getMessage());

		if (missingParameters != null) {
			buf.append("; missing parameters [");
			for (int i = 0; i < missingParameters.length; i++) {
				buf.append("'").append(missingParameters[i]).append("'");
			}
			buf.append("]");
		}

		return buf.toString();
	}
}
