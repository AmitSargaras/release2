/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.releaselinedetailsupload;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *@author $Author: Abhijeet Jadhav.
 *Form Bean for UBS Upload
 */

public class ReleaselinedetailsUploadForm  extends TrxContextForm implements Serializable {


	private FormFile fileUpload;
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	public String[][] getMapper() {
		String[][] input = {  {  
				"releaselinedetailsUploadObj", RELEASELINEDETAILSUPLOAD_MAPPER },
							{ "theOBTrxContext", TRX_MAPPER }};
		return input;

	}
	public static final String RELEASELINEDETAILSUPLOAD_MAPPER = "com.integrosys.cms.ui.releaselinedetailsupload.ReleaselinedetailsUploadMapper";
	
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";


}