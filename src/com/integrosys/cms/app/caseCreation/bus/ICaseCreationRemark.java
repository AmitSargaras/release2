package com.integrosys.cms.app.caseCreation.bus;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * @author  Abhijit R. 
 */
public interface ICaseCreationRemark extends Serializable, IValueObject {



    public long getId();
    public void setId(long id);	


    public String getStatus();
    public void setStatus(String aStatus);	


    public long getVersionTime();
    public void setVersionTime(long versionTime);
    
    public String getUserTeam();
	public void setUserTeam(String userTeam) ;
	public long getUserId();
	public void setUserId(long userId);
	public String getUserRole();
	public void setUserRole(String userRole);
	public Date getUpdatedDate();
	public void setUpdatedDate(Date updatedDate);
	public long getLimitProfileId();
	public void setLimitProfileId(long limitProfileId) ;
	public String getRemark();
	public void setRemark(String remark);
	

}
