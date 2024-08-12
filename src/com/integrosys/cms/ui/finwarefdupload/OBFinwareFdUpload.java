
package com.integrosys.cms.ui.finwarefdupload;


import java.util.Date;

import org.apache.struts.upload.FormFile;

/**
 * @author Abhijeet J
 */
public class OBFinwareFdUpload implements IFinwareFdUpload {
	private FormFile fileUpload;
	/**
	 * constructor
	 */
	public OBFinwareFdUpload() {
		
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