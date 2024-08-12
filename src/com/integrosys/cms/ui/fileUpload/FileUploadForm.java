package com.integrosys.cms.ui.fileUpload;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class FileUploadForm extends TrxContextForm implements Serializable{
	
	public String[][] getMapper() {
		String[][] input = {  { "fileObj", FILE_UPLOAD_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}
	
	private String fileUploadSystem ;
	private String uploadStatus ;
	
	public static final String FILE_UPLOAD_MAPPER = "com.integrosys.cms.ui.component.ComponentMapper";
	
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

	public String getFileUploadSystem() {
		return fileUploadSystem;
	}

	public void setFileUploadSystem(String fileUploadSystem) {
		this.fileUploadSystem = fileUploadSystem;
	}

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	
	

}
