package com.integrosys.cms.ui.stockdetailsupload;

import org.apache.struts.upload.FormFile;

public class OBStockDetailsUpload implements IStockDetailsUpload {

	private FormFile fileUpload;
	
	public FormFile getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	
	public long getVersionTime() {
		return 0;
	}
	
	public void setVersionTime(long version) {
	}

}
