package com.integrosys.cms.ui.autoupdationlmtsupload;


import org.apache.struts.upload.FormFile;

/**
 * @author Mukesh Mohapatra for Cersai AutoupdationLmts Upload
 */
public class OBAutoupdationLmtsUpload implements IAutoupdationLmtsUpload {
	private FormFile fileUpload;
	/**
	 * constructor
	 */
	public OBAutoupdationLmtsUpload() {
		
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