package com.integrosys.cms.ui.mf.schemadetailsupload;

import org.apache.struts.upload.FormFile;

public class OBSchemaDetailsUpload implements ISchemaDetailsUpload {

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
