/*
 * Copyright Integro Technologies Pte Ltd
 * 
 */
package com.integrosys.cms.app.transaction;

import java.util.Collection;
import java.util.Date;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.OBTrxHistoryValue;
import com.integrosys.base.businfra.transaction.OBTrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents the transaction object for CMS.
 * 
 * @author Alfred Lee
 * @author Chong Jun Yong
 * @since 1.0
 */
public class OBCMSTrxValue extends OBTrxValue implements ICMSTrxValue {

	private static final long serialVersionUID = -1579683908314237206L;

	private String referenceId = null;

	private String stagingReferenceId = null;

	private String transactionReferenceId = null;

	private long teamId = ICMSConstant.LONG_INVALID_VALUE;

	private String remarks = null;

	private ITrxContext trxContext = null;

	private String operationDescription = null;

	private String legalName = null;
	
	private Date systemDate=null;

	private String customerName = null;

	private String legalId = null;

	private long customerId = ICMSConstant.LONG_INVALID_VALUE;

	private String originatingCountry = null;

	private String originatingOrganisation = null;

	private long limitProfileId = ICMSConstant.LONG_INVALID_VALUE;

	private String limitProfileReferenceNumber = null;

	private String currentTransactionHistoryId = null;

	private long teamTypeId = ICMSConstant.LONG_INVALID_VALUE;

	private String transactionSubType = null;

	private long teamMembershipId = ICMSConstant.LONG_INVALID_VALUE;

	private OBTrxHistoryValue[] historyValue;

	private Collection transactionHistoryCollection;

	private OBCMSTrxRouteInfo[] nextRouteList;

	private Collection nextRouteCollection;

	private String userInfo;

	private String dealNo;

	private long toUserId = ICMSConstant.LONG_INVALID_VALUE;

	private long toAuthGId = ICMSConstant.LONG_INVALID_VALUE;

	private long toAuthGroupTypeId = ICMSConstant.LONG_INVALID_VALUE;

	private String toUserInfo;

	private String loginId;
	
	
	private String minEmployeeGrade;
	

	/**
	 * Default Constructor
	 */
	public OBCMSTrxValue() {
		super();
		super.setStatus(ICMSConstant.STATE_ND);
		super.setFromState(ICMSConstant.STATE_ND);
	}

	/**
	 * Constructr the OB from its interface
	 * 
	 * @param in is the ICMSTrxValue object
	 */
	public OBCMSTrxValue(ICMSTrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	/**
	 * Constructr the OB from its interface
	 * 
	 * @param in is the ITrxValue object
	 */
	public OBCMSTrxValue(ITrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	public String getComment() {
		return this.getRemarks();
	}

	public String getCurrentTrxHistoryID() {
		return currentTransactionHistoryId;
	}

	public Date getSystemDate() {
		return systemDate;
	}

	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}

	/**
	 * Get CustomerID
	 * 
	 * @return long
	 */
	public long getCustomerID() {
		return customerId;
	}

	/**
	 * Get Custoomer Name
	 * 
	 * @return String
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @return Returns the dealNo.
	 */
	public String getDealNo() {
		return dealNo;
	}

	/**
	 * Get Legal ID
	 * 
	 * @return String
	 */
	public String getLegalID() {
		return legalId;
	}

	/**
	 * Get the Legal Name of the customer
	 * 
	 * @return String
	 */
	public String getLegalName() {
		return legalName;
	}

	/**
	 * Get the limit profile ID
	 * 
	 * @return long
	 */
	public long getLimitProfileID() {
		return limitProfileId;
	}

	public String getLimitProfileReferenceNumber() {
		return limitProfileReferenceNumber;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public Collection getNextRouteCollection() {
		return nextRouteCollection;
	}

	public OBCMSTrxRouteInfo[] getNextRouteList() {
		return nextRouteList;
	}

	/**
	 * Get the Operation Description
	 * 
	 * @return String
	 */
	public String getOpDesc() {
		return operationDescription;
	}

	/**
	 * decorator method to match OBCMSTrxHistoryValue for copy
	 */
	public String getOperationDescField() {
		return this.getOpDesc();
	}

	/**
	 * Get the Originating Country
	 * 
	 * @return String
	 */
	public String getOriginatingCountry() {
		return originatingCountry;
	}

	/**
	 * Get the Originating Organisation
	 * 
	 * @return String
	 */
	public String getOriginatingOrganisation() {
		return originatingOrganisation;
	}

	/**
	 * Get the business object reference ID
	 * 
	 * @return String
	 */
	public String getReferenceID() {
		return referenceId;
	}

	/**
	 * Get the remarks
	 * 
	 * @return String
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Get the staging object reference ID
	 * 
	 * @return String
	 */
	public String getStagingReferenceID() {
		return stagingReferenceId;
	}

	/**
	 * Get the team ID of the current user
	 * 
	 * @return long
	 */
	public long getTeamID() {
		return teamId;
	}

	/**
	 * Get the team membership ID
	 * 
	 * @return long
	 */
	public long getTeamMembershipID() {
		return teamMembershipId;
	}

	/**
	 * Get the team type ID
	 * 
	 * @return long
	 */
	public long getTeamTypeID() {
		return teamTypeId;
	}

	public long getToAuthGId() {
		return toAuthGId;
	}

	public long getToAuthGroupTypeId() {
		return this.toAuthGroupTypeId;
	}

	public long getToUserId() {
		return toUserId;
	}

	public String getToUserInfo() {
		return toUserInfo;
	}

	public OBTrxHistoryValue[] getTransactionHistory() {
		return historyValue;
	}

	public Collection getTransactionHistoryCollection() {
		return transactionHistoryCollection;
	}

	public String getTransactionHistoryID() {
		return this.getCurrentTrxHistoryID();
	}

	/**
	 * Get the Transaction sub-type
	 * 
	 * @return String
	 */
	public String getTransactionSubType() {
		return transactionSubType;
	}

	/**
	 * Get the ITrxContext associated to this transaction
	 * 
	 * @return ITrxContext
	 */
	public ITrxContext getTrxContext() {
		return trxContext;
	}

	/**
	 * Get the trx reference ID. This refers to the parent reference ID
	 * @return String - the trx reference ID
	 */
	public String getTrxReferenceID() {
		return transactionReferenceId;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setCurrentTrxHistoryID(String value) {
		currentTransactionHistoryId = value;
	}

	/**
	 * Set CustomerID
	 * 
	 * @param value is of type long
	 */
	public void setCustomerID(long value) {
		customerId = value;
	}

	/**
	 * Set Custoomer Name
	 * 
	 * @param value is of type String
	 */
	public void setCustomerName(String value) {
		customerName = value;
	}

	/**
	 * @param dealNo The dealNo to set.
	 */
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}

	/**
	 * Set Legal ID
	 * 
	 * @param value is of type String
	 */
	public void setLegalID(String value) {
		legalId = value;
	}

	/**
	 * Set the Legal Name of the customer
	 * 
	 * @param value is of type String
	 */
	public void setLegalName(String value) {
		legalName = value;
	}

	/**
	 * Set the limit profile ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitProfileID(long value) {
		limitProfileId = value;
	}

	public void setLimitProfileReferenceNumber(String limitProfileReferenceNumber) {
		this.limitProfileReferenceNumber = limitProfileReferenceNumber;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void setNextRouteCollection(Collection nextRouteCollection) {
		this.nextRouteCollection = nextRouteCollection;
	}

	public void setNextRouteList(OBCMSTrxRouteInfo[] nextRouteList) {
		this.nextRouteList = nextRouteList;
	}

	/**
	 * Set the Operation Description
	 * 
	 * @param value is of type String
	 */
	public void setOpDesc(String value) {
		operationDescription = value;
	}

	/**
	 * Set the Originating Country
	 * 
	 * @param value is of type String
	 */
	public void setOriginatingCountry(String value) {
		originatingCountry = value;
	}

	/**
	 * Set the Originating Organisation
	 * 
	 * @param value is of type String
	 */
	public void setOriginatingOrganisation(String value) {
		originatingOrganisation = value;
	}

	/**
	 * Set the business object reference ID
	 * 
	 * @param id is of type String
	 */
	public void setReferenceID(String id) {
		referenceId = id;
	}

	/**
	 * Set the remarks
	 * 
	 * @param remarks is of type String
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	// setters
	/**
	 * Set the staging object reference ID
	 * 
	 * @param id is of type String
	 */
	public void setStagingReferenceID(String id) {
		stagingReferenceId = id;
	}

	/**
	 * Set the team ID of the current user
	 * 
	 * @param id is of type long
	 */
	public void setTeamID(long id) {
		teamId = id;
	}

	/**
	 * Set the team membership ID of the current user
	 * 
	 * @param id is of type long
	 */
	public void setTeamMembershipID(long value) {
		teamMembershipId = value;
	}

	/**
	 * Set the team type ID
	 * 
	 * @param value is of type long
	 */
	public void setTeamTypeID(long value) {
		teamTypeId = value;
	}

	public void setToAuthGId(long gid_) {
		this.toAuthGId = gid_;
	}

	public void setToAuthGroupTypeId(long gtypeid_) {
		this.toAuthGroupTypeId = gtypeid_;
	}

	public void setToUserId(long uid_) {
		this.toUserId = uid_;
	}

	public void setToUserInfo(String toUserInfo) {
		this.toUserInfo = toUserInfo;
	}

	public void setTransactionHistory(OBTrxHistoryValue[] historyValue) {
		this.historyValue = historyValue;
	}

	public void setTransactionHistoryCollection(Collection transactionHistoryCollection) {
		this.transactionHistoryCollection = transactionHistoryCollection;
	}

	/**
	 * Set the Transaction sub-type
	 * 
	 * @param value is of type String
	 */
	public void setTransactionSubType(String value) {
		transactionSubType = value;
	}

	/**
	 * Set the ITrxContext associated to this transaction
	 * 
	 * @param value is of type ITrxContext
	 */
	public void setTrxContext(ITrxContext value) {
		trxContext = value;
	}

	/**
	 * Set the trx reference ID.
	 * @param id - String
	 */
	public void setTrxReferenceID(String id) {
		this.transactionReferenceId = id;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	

	public String getMinEmployeeGrade() {
		return minEmployeeGrade;
	}

	public void setMinEmployeeGrade(String minEmployeeGrade) {
		this.minEmployeeGrade = minEmployeeGrade;
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer("CMSTrxValue");
		buf.append("@").append(System.identityHashCode(this)).append(", ");
		buf.append("transaction id: [").append(getTransactionID()).append("], ");
		buf.append("transaction type: [").append(getTransactionType()).append("], ");
		buf.append("reference id: [").append(referenceId).append("], ");
		buf.append("staging reference id: [").append(stagingReferenceId).append("], ");
		buf.append("team id: [").append(teamId).append("], ");
		buf.append("user id: [").append(getUID()).append("]");

		return buf.toString();
	}
}
