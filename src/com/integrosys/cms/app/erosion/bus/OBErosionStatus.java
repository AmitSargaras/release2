package com.integrosys.cms.app.erosion.bus;

import java.util.Date;

public class OBErosionStatus implements IErosionStatus {
	
	private long id;
	private Date reportingDate;
	private String activity;
	private String status;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getReportingDate() {
		return reportingDate;
	}
	public void setReportingDate(Date reportingDate) {
		this.reportingDate = reportingDate;
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
