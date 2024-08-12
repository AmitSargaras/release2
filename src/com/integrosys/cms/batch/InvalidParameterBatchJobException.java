package com.integrosys.cms.batch;

/**
 * Exception to be thrown when the required parameters for the batch job is
 * missing.
 * 
 * @author Chong Jun Yong
 * 
 */
public class InvalidParameterBatchJobException extends BatchJobException {

	private String[] missingParameters;

	/**
	 * Constructor with error detail message supplied
	 * 
	 * @param msg error detail message
	 */
	public InvalidParameterBatchJobException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with error detail message and cause supplied
	 * 
	 * @param msg error detail message
	 * @param cause the cause that throw this exception
	 */
	public InvalidParameterBatchJobException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Constructor with error detail message supplied and the missing parameters
	 * which will be output
	 * 
	 * @param msg error detail message
	 * @param missingParameters missing parameters that required for the batch
	 *        job
	 */
	public InvalidParameterBatchJobException(String msg, String[] missingParameters) {
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
