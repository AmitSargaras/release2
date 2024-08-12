package com.integrosys.cms.app.fileUpload.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IFileUpload extends Serializable{
	
	public long getId();
	public void setId(long id);
	public String getFileName();
	public void setFileName(String fileName);
	public String getUploadBy();
	public void setUploadBy(String uploadBy);
	public String getApproveBy();
	public void setApproveBy(String approveBy);
	public Date getUploadTime();
	public void setUploadTime(Date uploadTime);
	public String getTotalRecords();
	public void setTotalRecords(String totalRecords);
	public String getApproveRecords();
	public void setApproveRecords(String approveRecords);
	public String getFileType();
	public void setFileType(String fileType);

}
