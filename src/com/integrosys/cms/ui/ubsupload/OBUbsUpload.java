
package com.integrosys.cms.ui.ubsupload;


import org.apache.struts.upload.FormFile;

/**
 * @author Abhijeet J 
 */
public class OBUbsUpload implements IUbsUpload {
	private FormFile fileUpload;
	/**
	 * constructor
	 */
	public OBUbsUpload() {
		
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