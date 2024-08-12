
package com.integrosys.cms.ui.partycamupload;


import org.apache.struts.upload.FormFile;

/**
 * @author Abhijeet J 
 */
public class OBPartyCamUpload implements IPartyCamUpload {
	private FormFile fileUpload;
	/**
	 * constructor
	 */
	public OBPartyCamUpload() {
		
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