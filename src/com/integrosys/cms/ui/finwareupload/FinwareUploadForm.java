/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.finwareupload;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 *@author $Author: Abhijeet Jadhav.
 *Form Bean for UBS Upload
 */

public class FinwareUploadForm  extends CommonForm implements Serializable {


	private FormFile fileUpload;
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	public String[][] getMapper() {
		String[][] input = {  { "finwareuploadObj", FINWAREUPLOAD_MAPPER }};
		return input;

	}
	public static final String FINWAREUPLOAD_MAPPER = "com.integrosys.cms.ui.finwareupload.FinwareUploadMapper";



	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

}
