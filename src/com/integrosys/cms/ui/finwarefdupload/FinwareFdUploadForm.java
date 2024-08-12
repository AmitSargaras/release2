/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.finwarefdupload;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 *@author $Author: Abhijeet Jadhav.
 *Form Bean for UBS Upload
 */

public class FinwareFdUploadForm  extends CommonForm implements Serializable {


	private FormFile fileUpload;
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	public String[][] getMapper() {
		String[][] input = {  { "finwarefduploadObj", FINWARFDUPLOAD_MAPPER }};
		return input;

	}
	public static final String FINWARFDUPLOAD_MAPPER = "com.integrosys.cms.ui.finwarefdupload.FinwareFdUploadMapper";




}
