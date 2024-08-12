package com.integrosys.cms.ui.bonddetailsupload;

import org.apache.struts.upload.FormFile;

public class OBBondDetailsUpload implements IBondDetailsUpload {

	private FormFile fileUpload;
	
	public long getVersionTime() {
		return 0;
	}

	public void setVersionTime(long arg0) {

	}

	public FormFile getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}

}
