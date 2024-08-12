
package com.integrosys.cms.ui.finwareupload;


import org.apache.struts.upload.FormFile;

/**
 * @author Abhijeet J
 */
public class OBFinwareUpload implements IFinwareUpload {
	private FormFile fileUpload;
	/**
	 * constructor
	 */
	public OBFinwareUpload() {
		
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