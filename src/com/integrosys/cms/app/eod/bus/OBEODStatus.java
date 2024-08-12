package com.integrosys.cms.app.eod.bus;

import java.util.Date;

public class OBEODStatus implements IEODStatus {
	
	private long id;
	//private long  versionTime;
	private Date eodDate;
	private String activity;
	private String status;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
/*	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}*/
	public Date getEodDate() {
		return eodDate;
	}
	public void setEodDate(Date eodDate) {
		this.eodDate = eodDate;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
