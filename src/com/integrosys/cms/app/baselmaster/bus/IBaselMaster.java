package com.integrosys.cms.app.baselmaster.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IBaselMaster extends Serializable, IValueObject{

	public long getId();
	public void setId(long id) ;
	public long getVersionTime();
	public void setVersionTime(long versionTime) ;
	public String getStatus() ;
	public void setStatus(String status);
	public String getDeprecated() ;
	public void setDeprecated(String deprecated) ;
	public String getSystem() ;
	public void setSystem(String system) ;
	public String getSystemValue() ;
	public void setSystemValue(String systemValue) ;
	public String getExposureSource() ;
	public void setExposureSource(String exposureSource) ;
	public String getBaselValidation() ;
	public void setBaselValidation(String baselValidation) ;
	public String getReportHandOff() ;
	public void setReportHandOff(String reportHandOff) ;
	public String getLastUpdatedBy() ;
	public void setLastUpdatedBy(String lastUpdatedBy) ;
	public String getCreatedBy() ;
	public void setCreatedBy(String createdBy);
	public Date getLastUpdatedOn();
	public void setLastUpdatedOn(Date lastUpdatedOn);
	public Date getCreatedOn();
	public void setCreatedOn(Date createdOn) ;
	
}
