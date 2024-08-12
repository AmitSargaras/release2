package com.integrosys.cms.ui.checklist.document;

import java.util.Date;

public class OBViewCompleteCheckList implements IViewCompleteCheckList{
	
	public String docType;
	
	public String type;
	
	public String description;
	
	public String version;
	
	public String status;
	
	public Date docDate;
	
	public Date docExpDate;
	
	public String docAmount;
	
	public long docItemId;
	
	public String active;
	
	

	public long getDocItemId() {
		return docItemId;
	}

	public void setDocItemId(long docItemId) {
		this.docItemId = docItemId;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public Date getDocExpDate() {
		return docExpDate;
	}

	public void setDocExpDate(Date docExpDate) {
		this.docExpDate = docExpDate;
	}

	public String getDocAmount() {
		return docAmount;
	}

	public void setDocAmount(String docAmount) {
		this.docAmount = docAmount;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	
	
	

}
