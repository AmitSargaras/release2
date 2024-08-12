package com.integrosys.cms.app.leiDetailsUpload;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *@author $Author: Mukesh Mohapatra.
 *Form Bean for  CERSAI Acknowledgment Upload
 */

public class LeiDetailsUploadForm  extends TrxContextForm implements Serializable {


	private FormFile fileUpload;
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	public String[][] getMapper() {
		String[][] input = {  {  
				"leiDetailsUploadObj", LEIDETAILSUPLOAD_MAPPER },
							{ "theOBTrxContext", TRX_MAPPER }};
		return input;

	}
	public static final String LEIDETAILSUPLOAD_MAPPER = "com.integrosys.cms.app.leiDetailsUpload.LeiDetailsUploadMapper";
	
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";


}
