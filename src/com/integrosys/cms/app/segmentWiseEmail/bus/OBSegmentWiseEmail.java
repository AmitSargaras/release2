package com.integrosys.cms.app.segmentWiseEmail.bus;

import java.util.Date;
import java.util.List;


public class OBSegmentWiseEmail implements ISegmentWiseEmail{
	
	//private List<ISegmentWiseEmail> obList;
	
	
	private long ID;
	private String segment;
	private String email;
	private String status;
	private long versionTime;
	
	private Date lastUpdateDate;
	private Date creationDate;
	private String createdBy;
	private String lastUpdatedBy;
	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
/*	public List<ISegmentWiseEmail> getObList() {
		return obList;
	}
	public void setObList(List<ISegmentWiseEmail> obList) {
		this.obList = obList;
	}*/
	
	
	
	
}
