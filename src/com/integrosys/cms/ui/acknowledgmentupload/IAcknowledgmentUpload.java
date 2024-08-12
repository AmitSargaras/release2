package com.integrosys.cms.ui.acknowledgmentupload;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * @author  Mukesh Mohapatra. 
 */
public interface IAcknowledgmentUpload extends Serializable, IValueObject {


    public FormFile getFileUpload();
    public void setFileUpload(FormFile fileUpload);

}

