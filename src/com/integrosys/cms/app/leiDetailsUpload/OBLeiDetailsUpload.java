package com.integrosys.cms.app.leiDetailsUpload;

import java.util.Date;

import org.apache.struts.upload.FormFile;

public class OBLeiDetailsUpload implements ILeiDetailsUpload,Cloneable {
	

	private FormFile fileUpload;
	/**
	 * constructor
	 */
	public OBLeiDetailsUpload() {
		
	}
 
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}

	public long getVersionTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setVersionTime(long version) {
		// TODO Auto-generated method stub
		
	}
	
	
}
