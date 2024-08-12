package com.integrosys.cms.ui.acknowledgmentupload;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *@author $Author: Mukesh Mohapatra.
 *Form Bean for  CERSAI Acknowledgment Upload
 */

public class AcknowledgmentUploadForm  extends TrxContextForm implements Serializable {


	private FormFile fileUpload;
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	public String[][] getMapper() {
		String[][] input = {  {  
				"acknowledgmentUploadObj", ACKNOWLEDGMENTUPLOAD_MAPPER },
							{ "theOBTrxContext", TRX_MAPPER }};
		return input;

	}
	public static final String ACKNOWLEDGMENTUPLOAD_MAPPER = "com.integrosys.cms.ui.acknowledgmentupload.AcknowledgmentUploadMapper";
	
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";


}
