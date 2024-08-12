package com.integrosys.cms.ui.acknowledgmentupload;


import org.apache.struts.upload.FormFile;

/**
 * @author Mukesh Mohapatra for Cersai Acknowledgment Upload
 */
public class OBAcknowledgmentUpload implements IAcknowledgmentUpload {
	private FormFile fileUpload;
	/**
	 * constructor
	 */
	public OBAcknowledgmentUpload() {
		
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