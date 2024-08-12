package com.integrosys.cms.batch;

public class DeferralReportBatchJobException extends BatchJobException{

	private static final long serialVersionUID = 1L;
	
	public DeferralReportBatchJobException(String msg) {
		super(msg);
	}

	public DeferralReportBatchJobException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public String getMessage() {
		return super.getMessage();
	}
}
