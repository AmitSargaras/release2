package com.integrosys.cms.app.user.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;


public interface ISMSUserUploadLog extends Serializable, IValueObject{

	public long getId();
	public void setId(long id);
	

	public String getUploadFileName();
	public void setUploadFileName(String uploadFileName) ;
	
	public Date getUploadDate();
	public void setUploadDate(Date uploadDate);
	
	public String getUserId();
	public void setUserId(String userId);
	

    public long getVersionTime();
    public void setVersionTime(long versionTime);
    
	
	public String getRejectReason();
	public void setRejectReason(String rejectReason) ;
	
	public String getActivity() ;
	public void setActivity(String activity);
	
	public String getSuccessReject() ;
	public void setSuccessReject(String successReject) ;
	
	public String getDeptCode() ;
	public void setDeptCode(String deptCode) ;
	
	public String getBranchCode() ;
	public void setBranchCode(String branchCode) ;
	
	public String getUserRole();
	public void setUserRole(String userRole);
	
	public String getUserName();
	public void setUserName(String userName) ;
}
