package com.integrosys.cms.app.newtatmaster.bus;

import java.io.Serializable;
import java.util.Date;


public interface INewTatMaster extends Serializable {
	
	public long getId();
	public void setId(long id);
	
	public long getVersionTime();
	public void setVersionTime(long versionTime) ;
	
	public String getStatus() ;
	public void setStatus(String status);
	
	public String getDeprecated() ;
	public void setDeprecated(String deprecated) ;
	
	public String getStartTime();
	public void setStartTime(String startTime);
	

	
	public String getLastUpdatedBy();
	public void setLastUpdatedBy(String lastUpdatedBy) ;
	
	public String getCreatedBy() ;
	public void setCreatedBy(String createdBy) ;
	
	public Date getLastUpdatedOn() ;
	public void setLastUpdatedOn(Date lastUpdatedOn);
	
	public Date getCreatedOn() ;
	public void setCreatedOn(Date date);
	
	public String getEndTime();
	public void setEndTime(String endTime) ;
	
	public String getUserEvent();
	public void setUserEvent(String userEvent);
	
	public String getTimingHours();
	public void setTimingHours(String timingHours);
	
	public String getTimingMin() ;
	public void setTimingMin(String timingMin);
	
	public String getEventCode();
	public void setEventCode(String eventCode);
	

}
