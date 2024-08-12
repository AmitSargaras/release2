/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collaborationtask/OBCCCollaborationTaskInfo.java,v 1.4 2005/10/13 03:39:35 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.collaborationtask;

/**
 * Bean to hold the information that are used for sending notifications
 */
public class OBCCCollaborationTaskInfo extends OBCheckListTaskInfo {
	private String customerID;

	private String customerCategory;

	private String domicileCountry;

	private String governLaw;

	private String bcaBkgCountry;

	private String ccRemarks;

	private String coborrowerPledgorID;

	private String coborrowerPledgorLeName;

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	public String getDomicileCountry() {
		return domicileCountry;
	}

	public void setDomicileCountry(String domicileCountry) {
		this.domicileCountry = domicileCountry;
	}

	public String getGovernLaw() {
		return governLaw;
	}

	public void setGovernLaw(String governLaw) {
		this.governLaw = governLaw;
	}

	public String getBcaBkgCountry() {
		return bcaBkgCountry;
	}

	public void setBcaBkgCountry(String bcaBkgCountry) {
		this.bcaBkgCountry = bcaBkgCountry;
	}

	public String getCCRemarks() {
		return this.ccRemarks;
	}

	public void setCCRemarks(String aCCRemarks) {
		this.ccRemarks = aCCRemarks;
	}

	public String getCoborrowerPledgorID() {
		return this.coborrowerPledgorID;
	}

	public void setCoborrowerPledgorID(String coborrowerPledgorID) {
		this.coborrowerPledgorID = coborrowerPledgorID;
	}

	public String getCoborrowerPledgorLeName() {
		return this.coborrowerPledgorLeName;
	}

	public void setCoborrowerPledgorLeName(String coborrowerPledgorLeName) {
		this.coborrowerPledgorLeName = coborrowerPledgorLeName;
	}

}
