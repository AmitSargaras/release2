package com.integrosys.cms.app.relationshipmgr.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IHRMSData extends Serializable, IValueObject {
	
	public long getId();
		
	public void setId(long id);
	
	public long getVersionTime();

	public void setVersionTime(long arg0);

	public String getEmployeeCode();
	
	public void setEmployeeCode(String employeeCode);
	
	public String getName();
	
	public void setName(String name);
	
	public String getEmailId();
	
	public void setEmailId(String emailId);

	public String getMobileNo();
	
	public void setMobileNo(String mobileNo);
	
	public String getRegion();
	
	public void setRegion(String region);
	
	public String getCity();
	
	public void setCity(String city);
	
	public String getState();
	
	public void setState(String state);
	
	public String getWboRegion();
	
	public void setWboRegion(String wboRegion);
	
	public String getSupervisorEmployeeCode();
	
	public void setSupervisorEmployeeCode(String supervisorEmployeeCode);
	
	public String getBranchCode();
	
	public void setBranchCode(String branchCode);
	
	public String getCreatedBy();
	
	public void setCreatedBy(String createdBy);
	
	public String getLastUpdateBy();
	
	public void setLastUpdateBy(String lastUpdateBy);
	
	public Date getCreationDate();
	
	public void setCreationDate(Date creationDate);
	
	public Date getLastUpdateDate();
	
	public void setLastUpdateDate(Date lastUpdateDate);
	
	public String getDeprecated();
	
	public void setDeprecated(String deprecated);
	
	public String getStatus();
	
	public void setStatus(String status);
	
	public String getBand();
	
	public void setBand(String band);
}
