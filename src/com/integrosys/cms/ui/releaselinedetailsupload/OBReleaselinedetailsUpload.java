
package com.integrosys.cms.ui.releaselinedetailsupload;


import java.util.Date;

import org.apache.struts.upload.FormFile;

/**
 * @author Abhijeet J
 */
public class OBReleaselinedetailsUpload implements IReleaselinedetailsUpload {
	private FormFile fileUpload;
	/**
	 * constructor
	 */
	public OBReleaselinedetailsUpload() {
		
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