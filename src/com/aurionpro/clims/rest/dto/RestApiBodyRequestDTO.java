/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.aurionpro.clims.rest.dto;

/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by AA Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */


public class RestApiBodyRequestDTO{
	
	

	private String entryId;
	private String entryCode;
	private String entryName;
	private String categoryCode;
	private String status;
	private String refEntryCode;
	
	public String getEntryId() {
		return entryId;
	}
	public String getRefEntryCode() {
		return refEntryCode;
	}
	public void setRefEntryCode(String refEntryCode) {
		this.refEntryCode = refEntryCode;
	}
	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
	public String getEntryCode() {
		return entryCode;
	}
	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}
	public String getEntryName() {
		return entryName;
	}
	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	
}