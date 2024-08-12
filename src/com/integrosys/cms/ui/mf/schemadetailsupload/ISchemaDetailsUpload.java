package com.integrosys.cms.ui.mf.schemadetailsupload;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface ISchemaDetailsUpload extends IValueObject {

    public FormFile getFileUpload();
    public void setFileUpload(FormFile fileUpload);
	
}
