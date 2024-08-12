/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.partycamupload;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *@author $Author: Abhijeet Jadhav.
 *Form Bean for UBS Upload
 */

public class PartyCamUploadForm  extends TrxContextForm implements Serializable {


	private FormFile fileUpload;
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	public String[][] getMapper() {
		String[][] input = {  { "partyCamUploadObj", PARTYCAMUPLOAD_MAPPER },
							{ "theOBTrxContext", TRX_MAPPER }};
		return input;

	}
	public static final String PARTYCAMUPLOAD_MAPPER = "com.integrosys.cms.ui.partycamupload.PartyCamUploadMapper";
	
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";


}
