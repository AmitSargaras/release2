package com.integrosys.cms.ui.bulkudfupdateupload;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IBulkUDFUpload  extends Serializable, IValueObject {
	

    public FormFile getFileUpload();
    public void setFileUpload(FormFile fileUpload);

}
