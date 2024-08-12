package com.integrosys.cms.ui.bonddetailsupload;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IBondDetailsUpload extends IValueObject {

    public FormFile getFileUpload();
    public void setFileUpload(FormFile fileUpload);
	
}
