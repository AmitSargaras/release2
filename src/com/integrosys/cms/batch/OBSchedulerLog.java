package com.integrosys.cms.batch;

import java.util.Date;

public class OBSchedulerLog {

	private long id;
	private long versionTime;
	private String schedulerCode;
	private String schedulerName;
	private Date startDate;
	private Date endDate;
	private String status;
	private String message;
	
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

	public String getSchedulerCode() {
		return schedulerCode;
	}

	public void setSchedulerCode(String schedulerCode) {
		this.schedulerCode = schedulerCode;
	}

	public String getSchedulerName() {
		return schedulerName;
	}

	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "[schedulerCode=" + schedulerCode + ", " + "startDate=" + startDate + 
				", endDate=" + endDate + ", status=" + status + "]";
	}
	
}
