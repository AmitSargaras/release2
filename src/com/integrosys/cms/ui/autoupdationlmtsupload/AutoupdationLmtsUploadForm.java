package com.integrosys.cms.ui.autoupdationlmtsupload;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *@author $Author: Mukesh Mohapatra.
 *Form Bean for  CERSAI AutoupdationLmts Upload
 */

public class AutoupdationLmtsUploadForm  extends TrxContextForm implements Serializable {


	private FormFile fileUpload;
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	public String[][] getMapper() {
		String[][] input = {  {  
				"autoupdationlmtsUploadObj", AUTOUPDATIONLMTSUPLOAD_MAPPER },
							{ "theOBTrxContext", TRX_MAPPER }};
		return input;

	}
	public static final String AUTOUPDATIONLMTSUPLOAD_MAPPER = "com.integrosys.cms.ui.autoupdationlmtsupload.AutoupdationLmtsUploadMapper";
	
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";


}
