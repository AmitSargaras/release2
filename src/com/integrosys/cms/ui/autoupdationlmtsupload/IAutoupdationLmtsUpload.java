package com.integrosys.cms.ui.autoupdationlmtsupload;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;


public interface IAutoupdationLmtsUpload extends Serializable, IValueObject {


    public FormFile getFileUpload();
    public void setFileUpload(FormFile fileUpload);

}

