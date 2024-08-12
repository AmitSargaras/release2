/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


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
public class DiscrepancyReadDetailResponseDTO{
	
	@XmlElement(name = "discrepancyId")
	private String discrepancyId;

	@XmlElement(name = "status")
	private String status;
	
	@XmlElement(name = "creationDate")
	private String creationDate;
	
	@XmlElement(name = "nextDueDate")
	private String nextDueDate;
	
	@XmlElement(name = "closeDate")
	private String closeDate;
	
	@XmlElement(name = "discrepancyType")
	private String discrepancyType;
	
	@XmlElement(name = "approvedDate")
	private String approvedDate;
	
	@XmlElement(name = "originalTargetDate")
	private String originalTargetDate;
	
	@XmlElement(name = "creditApprover")
	private String creditApprover;
	
	@XmlElement(name = "remarks")
	private String remarks;

	public String getDiscrepancyId() {
		return discrepancyId;
	}

	public void setDiscrepancyId(String discrepancyId) {
		this.discrepancyId = discrepancyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(String nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	public String getDiscrepancyType() {
		return discrepancyType;
	}

	public void setDiscrepancyType(String discrepancyType) {
		this.discrepancyType = discrepancyType;
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
	
	
	
	
	
	
}