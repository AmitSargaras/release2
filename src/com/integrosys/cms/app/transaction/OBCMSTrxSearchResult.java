/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/OBCMSTrxSearchResult.java,v 1.12 2005/09/22 02:39:44 whuang Exp $
 */
package com.integrosys.cms.app.transaction;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * This class represents a transaction search result data.
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2005/09/22 02:39:44 $ Tag: $Name: $
 */
public class OBCMSTrxSearchResult implements Serializable {
	private String transactionID;

	private String trxHistoryID;

	private String legalName;

	private String customerName;

	private String leID;

	private String subProfileID;

	private String originatingLocation;

	private String fam;

	private String transactionType;

	private String userTransactionType;

	private String userState;

	private Date transactionDate;
	
	private Date systemDate;

	private String status;

	private String limitProfileID;
	
	private String limitProfileReferenceNumber;

	private String referenceID;

	// key as user action and value as URL
	private HashMap actionUrls;

	// key as user action and value as URL
	private HashMap totrackActionUrls;

	private Date sciApprovedDate;

	private String sciLegalID;

	private long sciSubprofileID;

	private String transactionSubType;

	private boolean isMainTask = false;

	private boolean isSecColTask = false;

	private boolean isCCColTask = false;

	private boolean isLimitTask = false;

	private String dealNo;
	
	private String userInfo;

	private String login_id;

	private String minEmployeeGradeLoa;
	
	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String loginId) {
		login_id = loginId;
	}

	public Date getSystemDate() {
		return systemDate;
	}

	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}

	public HashMap getActionUrls() {
		return actionUrls;
	}

	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @return Returns the dealNo.
	 */
	public String getDealNo() {
		return dealNo;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getFam() {
		return fam;
	}

	public String getLegalName() {
		return legalName;
	}

	public String getLeID() {
		return leID;
	}

	public String getLimitProfileID() {
		return limitProfileID;
	}

	public String getLimitProfileReferenceNumber() {
		return limitProfileReferenceNumber;
	}

	public String getOriginatingLocation() {
		return originatingLocation;
	}

	public String getReferenceID() {
		return referenceID;
	}

	public Date getSciApprovedDate() {
		return sciApprovedDate;
	}

	public String getSciLegalID() {
		return sciLegalID;
	}

	public long getSciSubprofileID() {
		return sciSubprofileID;
	}

	public String getStatus() {
		return status;
	}

	public String getSubProfileID() {
		return subProfileID;
	}

	public HashMap getTotrackActionUrls() {
		return totrackActionUrls;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public String getTransactionSubType() {
		return transactionSubType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public String getTrxHistoryID() {
		return trxHistoryID;
	}

	public String getUserState() {
		return userState;
	}

	public String getUserTransactionType() {
		return userTransactionType;
	}

	public boolean isCCColTask() {
		return isCCColTask;
	}

	public boolean isLimitTask() {
		return isLimitTask;
	}

	public boolean isMainTask() {
		return isMainTask;
	}

	public boolean isSecColTask() {
		return isSecColTask;
	}

	public void setActionUrls(HashMap actionUrls) {
		this.actionUrls = actionUrls;
	}

	public void setCCColTask(boolean CCColTask) {
		isCCColTask = CCColTask;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @param dealNo The dealNo to set.
	 */
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}

	public void setFam(String fam) {
		this.fam = fam;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public void setLeID(String leID) {
		this.leID = leID;
	}

	public void setLimitProfileID(String limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	public void setLimitProfileReferenceNumber(String limitProfileReferenceNumber) {
		this.limitProfileReferenceNumber = limitProfileReferenceNumber;
	}

	public void setLimitTask(boolean limitTask) {
		isLimitTask = limitTask;
	}

	public void setMainTask(boolean mainTask) {
		isMainTask = mainTask;
	}

	public void setOriginatingLocation(String originatingLocation) {
		this.originatingLocation = originatingLocation;
	}

	public void setReferenceID(String referenceID) {
		this.referenceID = referenceID;
	}

	public void setSciApprovedDate(Date sciApprovedDate) {
		this.sciApprovedDate = sciApprovedDate;
	}

	public void setSciLegalID(String sciLegalID) {
		this.sciLegalID = sciLegalID;
	}

	public void setSciSubprofileID(long sciSubprofileID) {
		this.sciSubprofileID = sciSubprofileID;
	}

	public void setSecColTask(boolean secColTask) {
		isSecColTask = secColTask;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSubProfileID(String subProfileID) {
		this.subProfileID = subProfileID;
	}

	public void setTotrackActionUrls(HashMap totrackActionUrls) {
		this.totrackActionUrls = totrackActionUrls;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public void setTransactionSubType(String transactionSubType) {
		this.transactionSubType = transactionSubType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public void setTrxHistoryID(String trxHistoryID) {
		this.trxHistoryID = trxHistoryID;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public void setUserTransactionType(String userTransactionType) {
		this.userTransactionType = userTransactionType;
	}

	public String getMinEmployeeGradeLoa() {
		return minEmployeeGradeLoa;
	}

	public void setMinEmployeeGradeLoa(String minEmployeeGradeLoa) {
		this.minEmployeeGradeLoa = minEmployeeGradeLoa;
	}
}