/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collaborationtask/OBCollaborationTaskInfo.java,v 1.6 2006/03/06 12:19:48 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.collaborationtask;

import java.util.Date;

/**
 * Bean to hold the information that are used for sending notifications
 */
public class OBCollaborationTaskInfo extends OBCheckListTaskInfo {
	// private String securityID;
	private String securityType;

	private String securitySubType;

	private String securitySubTypeID;

	private String securityLocation;

	private String securityRemarks;

	private Date[] securityMaturityDate;

	private String bcaBkgCountry;

	/*
	 * public String getSecurityID() { return securityID; }
	 * 
	 * public void setSecurityID(String securityID) { this.securityID =
	 * securityID; }
	 */
	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String getSecuritySubType() {
		return securitySubType;
	}

	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}

	public String getSecuritySubTypeID() {
		return securitySubTypeID;
	}

	public void setSecuritySubTypeID(String securitySubTypeID) {
		this.securitySubTypeID = securitySubTypeID;
	}

	public String getSecurityLocation() {
		return securityLocation;
	}

	public void setSecurityLocation(String securityLocation) {
		this.securityLocation = securityLocation;
	}

	public String getSecurityRemarks() {
		return this.securityRemarks;
	}

	public void setSecurityRemarks(String aSecurityRemarks) {
		this.securityRemarks = aSecurityRemarks;
	}

	public String getBcaBkgCountry() {
		return bcaBkgCountry;
	}

	public void setBcaBkgCountry(String bcaBkgCountry) {
		this.bcaBkgCountry = bcaBkgCountry;
	}

	public Date[] getSecurityMaturityDate() {
		return securityMaturityDate;
	}

	public void setSecurityMaturityDate(Date[] securityMaturityDate) {
		this.securityMaturityDate = securityMaturityDate;
	}
}
