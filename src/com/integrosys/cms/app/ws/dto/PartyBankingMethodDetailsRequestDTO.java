/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


/**
 * Describe this class. Purpose: To set getter and setter method for the value needed
 * by Party Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Ankit
 * @version $Revision:$
 * @since $Date:05-AUG-2014 $ Tag: $Name$
 */

@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "PartyBankingMethodDetailsRequestDTO", propOrder = {"leadNodalFlag", "branchId", "bankType"})
//@XmlRootElement(name = "PartyDetails")

public class PartyBankingMethodDetailsRequestDTO {
	
	private static final long serialVersionUID = -114309476199266725L;
	
	@XmlElement(name = "leadNodalFlag",required=true)
	private String leadNodalFlag;
	@XmlElement(name = "branchId",required=true)
	private String branchId;
	@XmlElement(name = "bankType",required=true)
	private String bankType;

	public String getLeadNodalFlag() {
		return leadNodalFlag;
	}

	public void setLeadNodalFlag(String leadNodalFlag) {
		this.leadNodalFlag = leadNodalFlag;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
}