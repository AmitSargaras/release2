package com.integrosys.cms.ui.newtatmaster;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.ui.common.TrxContextForm;

public class MaintainNewTatForm extends TrxContextForm implements Serializable{
	
	public static final String TAT_MASTER_MAPPER = "com.integrosys.cms.ui.newtatmaster.NewTatMasterMapper";
	
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

	
	
	private String id;	
	private String versionTime;
	private String status;
	private String deprecated;
	private String startTime;
	private String endTime;
	
	private String userEvent;
	private String timing;
	private String eventCode;
	
	private String lastUpdatedBy;
	private String createdBy;
	private String lastUpdatedOn;
	private String createdOn;
	
	
	
	
	
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}
	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(String versionTime) {
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
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	
	public String getUserEvent() {
		return userEvent;
	}
	public void setUserEvent(String userEvent) {
		this.userEvent = userEvent;
	}
	
	
	
	public String getTiming() {
		return timing;
	}
	public void setTiming(String timing) {
		this.timing = timing;
	}
	public String[][] getMapper() {
		String[][] input = {  { "tatEventObj", TAT_MASTER_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}
	
	
	

}
