package com.integrosys.cms.ui.mf.schemadetailsupload;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

public class SchemaDetailsUploadForm  extends TrxContextForm implements Serializable,ISchemaDetailsUploadConstants {

	private FormFile fileUpload;
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	public String[][] getMapper() {
		String[][] input = {  
					{ SCHEMA_DETAILS_UPLOAD_FORM, SCHEMA_DETAILS_UPLOAD_MAPPER },
					{ SESSION_TRX_OBJ, TRX_MAPPER }
				};
		return input;

	}

}
