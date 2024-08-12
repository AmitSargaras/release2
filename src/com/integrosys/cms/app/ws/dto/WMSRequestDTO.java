package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.struts.action.ActionErrors;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "WMSInfo")
public class WMSRequestDTO extends RequestDTO {
		
	@XmlElement(name = "systemId",required=true)
	private String systemId;

	@XmlElement(name = "lineNo",required=true)
	private String lineNo;
	
	@XmlElement(name = "serialNo",required=true)
	private String serialNo;
	
	@XmlElement(name = "liabBranch",required=true)
	private String liabBranch;
	
	@XmlElement(name = "releasedAmount")
	private String releasedAmount;
	
	@XmlElement(name = "closeLineFlag")
	private String closeLineFlag;
	
	private String status;
	
	private String facilitySystem;
	
	@XmlTransient
	private ActionErrors errors;
	
	@XmlTransient
	private String event;

	private String xrefID;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getLiabBranch() {
		return liabBranch;
	}

	public void setLiabBranch(String liabBranch) {
		this.liabBranch = liabBranch;
	}

	public String getReleasedAmount() {
		return releasedAmount;
	}

	public void setReleasedAmount(String releasedAmount) {
		this.releasedAmount = releasedAmount;
	}

	public String getCloseLineFlag() {
		return closeLineFlag;
	}

	public void setCloseLineFlag(String closeLineFlag) {
		this.closeLineFlag = closeLineFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFacilitySystem() {
		return facilitySystem;
	}

	public void setFacilitySystem(String facilitySystem) {
		this.facilitySystem = facilitySystem;
	}

	public ActionErrors getErrors() {
		return errors;
	}

	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getXrefID() {
		return xrefID;
	}

	public void setXrefID(String xrefID) {
		this.xrefID = xrefID;
	}
	
	
}
