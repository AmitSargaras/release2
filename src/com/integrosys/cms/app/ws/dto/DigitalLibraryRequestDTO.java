package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.struts.action.ActionErrors;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DigitalLibraryInfo")
public class DigitalLibraryRequestDTO extends RequestDTO{
	
	private static final long serialVersionUID = -114309476199266724L;
	/*@XmlElement(name = "facilityMasterId",required=true)
	private String facilityMasterId;*/
	
	@XmlElement(name = "partyId",required=true)
	private String partyId;
	
	@XmlElement(name = "panNo",required=true)
	private String panNo;
	
	@XmlElement(name = "systemId",required=true)
	private String systemId;
	
	@XmlElement(name = "docType",required=true)
	private String docType;
	
	@XmlElement(name = "docRecvFromDate",required=true)
	private String docRecvFromDate;
	
	@XmlElement(name = "docRecvToDate",required=true)
	private String docRecvToDate;
	
	@XmlElement(name = "docFromAmt",required=true)
	private String docFromAmt;
	
	@XmlElement(name = "docToAmt",required=true)
	private String docToAmt;
	
	@XmlElement(name = "systemName",required=true)
	private String systemName;
	
	@XmlElement(name = "docTagFromDate",required=true)
	private String docTagFromDate;
	
	@XmlElement(name = "docTagToDate",required=true)
	private String docTagToDate;
	
	@XmlElement(name = "docFromDate",required=true)
	private String docFromDate;
	
	@XmlElement(name = "docToDate",required=true)
	private String docToDate;
	
	@XmlElement(name = "partyName",required=true)
	private String partyName;
	
	@XmlElement(name = "docCode",required=true)
	private String docCode;
	
	@XmlElement(name = "employeeCodeRM",required=true)
	private String employeeCodeRM;
		
	@XmlTransient
	private ActionErrors errors;
	
	@XmlTransient
	private String event;

	
	
	


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

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocRecvFromDate() {
		return docRecvFromDate;
	}

	public void setDocRecvFromDate(String docRecvFromDate) {
		this.docRecvFromDate = docRecvFromDate;
	}

	public String getDocRecvToDate() {
		return docRecvToDate;
	}

	public void setDocRecvToDate(String docRecvToDate) {
		this.docRecvToDate = docRecvToDate;
	}

	

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getDocTagFromDate() {
		return docTagFromDate;
	}

	public void setDocTagFromDate(String docTagFromDate) {
		this.docTagFromDate = docTagFromDate;
	}

	public String getDocTagToDate() {
		return docTagToDate;
	}

	public void setDocTagToDate(String docTagToDate) {
		this.docTagToDate = docTagToDate;
	}

	public String getDocFromDate() {
		return docFromDate;
	}

	public void setDocFromDate(String docFromDate) {
		this.docFromDate = docFromDate;
	}

	public String getDocToDate() {
		return docToDate;
	}

	public void setDocToDate(String docToDate) {
		this.docToDate = docToDate;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getDocFromAmt() {
		return docFromAmt;
	}

	public void setDocFromAmt(String docFromAmt) {
		this.docFromAmt = docFromAmt;
	}

	public String getDocToAmt() {
		return docToAmt;
	}

	public void setDocToAmt(String docToAmt) {
		this.docToAmt = docToAmt;
	}

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getEmployeeCodeRM() {
		return employeeCodeRM;
	}

	public void setEmployeeCodeRM(String employeeCodeRM) {
		this.employeeCodeRM = employeeCodeRM;
	}
	
	
	
	
	
	

}
