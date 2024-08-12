package com.integrosys.cms.app.fileUpload.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class FileUploadException extends OFAException{
	
	public FileUploadException(String msg) {
		super(msg);
	}
	
	
	public FileUploadException(String msg, Throwable t) {
		super(msg, t);
	}

}
