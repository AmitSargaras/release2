/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.ubsupload;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 *@author $Author: Abhijeet Jadhav.
 *Form Bean for UBS Upload
 */

public class UBSUploadForm  extends CommonForm implements Serializable {


	private FormFile fileUpload;
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	public String[][] getMapper() {
		String[][] input = {  { "ubsuploadObj", UBSUPLOAD_MAPPER }};
		return input;

	}
	public static final String UBSUPLOAD_MAPPER = "com.integrosys.cms.ui.ubsupload.UbsUploadMapper";


}
