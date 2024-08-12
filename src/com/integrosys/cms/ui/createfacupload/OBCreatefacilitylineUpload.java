
package com.integrosys.cms.ui.createfacupload;


import org.apache.struts.upload.FormFile;


public class OBCreatefacilitylineUpload implements ICreatefacilitylineUpload {
	private FormFile fileUpload;
	/**
	 * constructor
	 */
	public OBCreatefacilitylineUpload() {
		
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