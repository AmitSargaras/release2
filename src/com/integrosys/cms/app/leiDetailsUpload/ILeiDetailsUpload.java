package com.integrosys.cms.app.leiDetailsUpload;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface ILeiDetailsUpload  extends Serializable, IValueObject {

	   public FormFile getFileUpload();
	    public void setFileUpload(FormFile fileUpload);
}
