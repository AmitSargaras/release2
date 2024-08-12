package com.integrosys.cms.ui.ubsupload;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * @author  Abhijit R. 
 */
public interface IUbsUpload extends Serializable, IValueObject {


    public FormFile getFileUpload();
    public void setFileUpload(FormFile fileUpload);

}
