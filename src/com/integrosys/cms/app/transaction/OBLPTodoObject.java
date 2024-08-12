package com.integrosys.cms.app.transaction;

import java.io.Serializable;
import java.util.Date;

public class OBLPTodoObject implements Serializable {

	private String actionID;

	private String actionName;

	private String processURL;

	private String viewURL;

	private long processMembershipID;

	private boolean isEnabled;

	private String legalName;

	private String companyName;

	private String leID;

	private String subProfileID;

	private String originatingLocation;

	private String fam;

	private String transactionType;

	private Date transactionDate;

	private Date systemDate;
	
	private String status;

	public Date getSystemDate() {
		return systemDate;
	}

	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLeID() {
		return leID;
	}

	public void setLeID(String leID) {
		this.leID = leID;
	}

	public String getSubProfileID() {
		return subProfileID;
	}

	public void setSubProfileID(String subProfileID) {
		this.subProfileID = subProfileID;
	}

	public String getOriginatingLocation() {
		return originatingLocation;
	}

	public void setOriginatingLocation(String originatingLocation) {
		this.originatingLocation = originatingLocation;
	}

	public String getFam() {
		return fam;
	}

	public void setFam(String fam) {
		this.fam = fam;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean enabled) {
		isEnabled = enabled;
	}

	public String getProcessURL() {
		return processURL;
	}

	public void setProcessURL(String processURL) {
		this.processURL = processURL;
	}

	public String getViewURL() {
		return viewURL;
	}

	public void setViewURL(String viewURL) {
		this.viewURL = viewURL;
	}

	public long getProcessMembershipID() {
		return processMembershipID;
	}

	public void setProcessMembershipID(long processMembershipID) {
		this.processMembershipID = processMembershipID;
	}

	public String getActionID() {
		return actionID;
	}

	public void setActionID(String actionID) {
		this.actionID = actionID;
	}
}
