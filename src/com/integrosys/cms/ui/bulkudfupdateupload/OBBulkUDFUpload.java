 package com.integrosys.cms.ui.bulkudfupdateupload;

import org.apache.struts.upload.FormFile;

public class OBBulkUDFUpload implements IBulkUDFUpload {
	
	private FormFile fileUpload;
	
	public OBBulkUDFUpload() {
		
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
