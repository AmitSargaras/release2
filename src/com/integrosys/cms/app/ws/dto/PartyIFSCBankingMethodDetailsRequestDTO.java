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

public class PartyIFSCBankingMethodDetailsRequestDTO {
	
	private static final long serialVersionUID = -114309476199266725L;
	
	@XmlElement(name = "ifscCode",required=true)
	private String ifscCode;
	@XmlElement(name = "bankName",required=true)
	private String bankName;
	@XmlElement(name = "branchName",required=true)
	private String branchName;
	@XmlElement(name = "email",required=true)
	private String email;
	@XmlElement(name = "address",required=true)
	private String address;
	@XmlElement(name = "nodalLead",required=true)
	private String nodalLead;

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNodalLead() {
		return nodalLead;
	}

	public void setNodalLead(String nodalLead) {
		this.nodalLead = nodalLead;
	}

}