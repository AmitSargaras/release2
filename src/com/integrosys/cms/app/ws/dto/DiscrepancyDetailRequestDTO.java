/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.struts.action.ActionErrors;


/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by AA Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DiscrepancyDetailInfo")
public class DiscrepancyDetailRequestDTO {
	
	private static final long serialVersionUID = -114309476199266724L;
	
	@XmlElement(name = "cpsDiscrepancyId",required=true)
	private String cpsDiscrepancyId;

	@XmlElement(name = "creationDate",required=true)
	private String creationDate;
	
	
	@XmlElement(name = "approvedDate",required=true)
	private String approvedDate;
	
	
	@XmlElement(name = "originalTargetDate",required=true)
	private String originalTargetDate;
	
	
	@XmlElement(name = "creditApprover",required=true)
	private String creditApprover;
	
	
	@XmlElement(name = "remarks",required=true)
	private String remarks;
	
	@XmlTransient
	private ActionErrors errors;
	
	@XmlTransient
	private String customerId;
	
	@XmlTransient
	private String event;
	
	public String getCpsDiscrepancyId() {
		return cpsDiscrepancyId;
	}

	public void setCpsDiscrepancyId(String cpsDiscrepancyId) {
		this.cpsDiscrepancyId = cpsDiscrepancyId;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getOriginalTargetDate() {
		return originalTargetDate;
	}

	public void setOriginalTargetDate(String originalTargetDate) {
		this.originalTargetDate = originalTargetDate;
	}

	public String getCreditApprover() {
		return creditApprover;
	}

	public void setCreditApprover(String creditApprover) {
		this.creditApprover = creditApprover;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ActionErrors getErrors() {
		return errors;
	}

	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
	
	
	
}