package com.integrosys.cms.ui.bulkudfupdateupload;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

public class BulkUDFUploadForm extends TrxContextForm implements Serializable {

	private FormFile fileUpload;
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	public String[][] getMapper() {
		String[][] input = {  {  
				"bulkudfUploadObj", BULKUDFUPLOAD_MAPPER },
							{ "theOBTrxContext", TRX_MAPPER }};
		return input;

	}
	public static final String BULKUDFUPLOAD_MAPPER = "com.integrosys.cms.ui.bulkudfupdateupload.BulkUDFUploadMapper";
	
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
}
