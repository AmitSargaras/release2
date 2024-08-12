package com.integrosys.cms.ui.segmentWiseEmail;

import java.io.Serializable;
import java.util.List;

import com.integrosys.cms.ui.common.TrxContextForm;

public class SegmentWiseEmailForm extends TrxContextForm implements Serializable{
	
	private String ID;
	private String segment;
	private String emailList;
	private String status;
	private String versionTime;
	
	private String lastUpdateDate;
	private String creationDate;
	private String createdBy;
	private String lastUpdatedBy;
	private String segmentWiseEmailList;
	
	
	public String getSegmentWiseEmailList() {
		return segmentWiseEmailList;
	}
	public void setSegmentWiseEmailList(String segmentWiseEmailList) {
		this.segmentWiseEmailList = segmentWiseEmailList;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
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
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	public String getEmailList() {
		return emailList;
	}
	public void setEmailList(String emailList) {
		this.emailList = emailList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}
	
	



	public static final String SEGMENT_WISE_EMAIL_MAPPER = "com.integrosys.cms.ui.segmentWiseEmail.SegmentWiseEmailMapper";
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	public String[][] getMapper() {
		String[][] input = {  { "segmentWiseEmailObj", SEGMENT_WISE_EMAIL_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}
}
