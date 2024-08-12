package com.integrosys.cms.app.newtatmaster.bus;



import java.util.Date;

import com.integrosys.cms.app.component.bus.IComponent;

public class OBNewTatMaster implements INewTatMaster{
	
public OBNewTatMaster(){
		
	}

	private long id;		
	private long versionTime;
	private String status;
	private String deprecated;
	
	private String startTime;
	private String endTime;
	private String timingHours;
	private String timingMin;
	private String userEvent;
	private String eventCode;
	
	private String lastUpdatedBy;
	private String createdBy;
	private Date lastUpdatedOn;
	private Date createdOn;
	
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}
	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getUserEvent() {
		return userEvent;
	}
	public void setUserEvent(String userEvent) {
		this.userEvent = userEvent;
	}
	public String getTimingHours() {
		return timingHours;
	}
	public void setTimingHours(String timingHours) {
		this.timingHours = timingHours;
	}
	public String getTimingMin() {
		return timingMin;
	}
	public void setTimingMin(String timingMin) {
		this.timingMin = timingMin;
	}
	
	
	

}
