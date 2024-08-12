package com.integrosys.cms.ui.bonddetailsupload;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

public class BondDetailsUploadForm  extends TrxContextForm implements Serializable,IBondDetailsUploadConstants {

	private FormFile fileUpload;
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	public String[][] getMapper() {
		String[][] input = {  
					{ BOND_DETAILS_UPLOAD_FORM, BOND_DETAILS_UPLOAD_MAPPER },
					{ SESSION_TRX_OBJ, TRX_MAPPER }
				};
		return input;

	}

}
