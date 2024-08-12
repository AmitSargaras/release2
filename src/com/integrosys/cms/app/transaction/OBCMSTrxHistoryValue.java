/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/OBCMSTrxHistoryValue.java,v 1.14 2003/09/04 06:31:41 kllee Exp $
 */
package com.integrosys.cms.app.transaction;

import java.util.Date;

import com.integrosys.base.businfra.transaction.OBTrxHistoryValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents the transaction history for CMS.
 * 
 * @author $Author: kllee $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2003/09/04 06:31:41 $ Tag: $Name: $
 */
public class OBCMSTrxHistoryValue extends OBTrxHistoryValue {
	private String transactionType = null;

	private Date createDate = null;

	private String trxReferenceID = null;

	private String referenceID = null;

	private String status = null;

	private String stagingReferenceID = null;

	private String opDesc = null;

	private String legalName = null;

	private String customerName = null;

	private String legalID = null;

	private long customerID = ICMSConstant.LONG_INVALID_VALUE;

	private String originCountry = null;

	private String originOrganisation = null;

	private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private String limitProfileReferenceNumber = null;

	private String loginId = null;

	private long teamTypeID = ICMSConstant.LONG_INVALID_VALUE;

	private String trxSubType = null;

	private String segment = null;

	private long teamMembershipID = ICMSConstant.LONG_INVALID_VALUE;
	
	
	private String minEmployeeGrade = null;

	/**
	 * Default Constructor
	 */
	public OBCMSTrxHistoryValue() {
		super();
	}

	/**
	 * Constructs a history value object with transaction id and time of
	 * logging.
	 */
	public OBCMSTrxHistoryValue(String trxID, Date trxDate) {
		super(trxID, trxDate);
	}

	/**
	 * Get the create date
	 * @return Date - the transaction date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Get CustomerID
	 * 
	 * @return long
	 */
	public long getCustomerID() {
		return customerID;
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
	 * Get Legal ID
	 * 
	 * @return String
	 */
	public String getLegalID() {
		return legalID;
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
		return limitProfileID;
	}

	public String getLimitProfileReferenceNumber() {
		return limitProfileReferenceNumber;
	}

	public String getLoginId() {
		return loginId;
	}

	/**
	 * Get the next Team ID
	 * @return long - the to team ID
	 */
	public long getNextTeamID() {
		Long teamID = new Long(getToAuthGId());
		if (teamID != null) {
			return teamID.longValue();
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Get the operation desc field
	 * 
	 * @return String
	 */
	public String getOperationDescField() {
		return opDesc;
	}

	/**
	 * Get the Originating Country
	 * 
	 * @return String
	 */
	public String getOriginatingCountry() {
		return originCountry;
	}

	/**
	 * Get the Originating Organisation
	 * 
	 * @return String
	 */
	public String getOriginatingOrganisation() {
		return originOrganisation;
	}

	/**
	 * Get the reference ID
	 * @return String - the reference ID
	 */
	public String getReferenceID() {
		return referenceID;
	}

	/**
	 * Get the remarks
	 * @return String - the remarks
	 */
	public String getRemarks() {
		return getComment();
	}

	public String getSegment() {
		return segment;
	}

	/**
	 * Get the staging reference ID
	 * @return String - the staging reference ID
	 */
	public String getStagingReferenceID() {
		return stagingReferenceID;
	}

	/**
	 * Get the status
	 * @return String - the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Get the from team ID
	 * @return long - the from team ID
	 */
	public long getTeamID() {
		Long teamID = new Long(getFromAuthGId());
		if (teamID != null) {
			return teamID.longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Get the team membership ID
	 * 
	 * @return long
	 */
	public long getTeamMembershipID() {
		return teamMembershipID;
	}

	/**
	 * Get the team type ID
	 * 
	 * @return long
	 */
	public long getTeamTypeID() {
		return teamTypeID;
	}

	/**
	 * Get the Transaction sub-type
	 * 
	 * @return String
	 */
	public String getTransactionSubType() {
		return trxSubType;
	}

	/**
	 * Get the transaction Type
	 * @return String - the transaction type
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * Get the trx reference ID
	 * @return - the trx reference ID
	 */
	public String getTrxReferenceID() {
		return trxReferenceID;
	}

	/**
	 * Get the from user ID
	 * @return String
	 */
	public String getUserID() {
		return getFromUser();
	}

	/**
	 * Set the create date
	 * @param d - Date
	 */
	public void setCreateDate(Date d) {
		createDate = d;
	}

	/**
	 * Set CustomerID
	 * 
	 * @param value is of type long
	 */
	public void setCustomerID(long value) {
		customerID = value;
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
	 * Set Legal ID
	 * 
	 * @param value is of type String
	 */
	public void setLegalID(String value) {
		legalID = value;
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
		limitProfileID = value;
	}

	public void setLimitProfileReferenceNumber(String limitProfileReferenceNumber) {
		this.limitProfileReferenceNumber = limitProfileReferenceNumber;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	// Setters

	/**
	 * Set the operation desc field
	 * 
	 * @param value is of type String
	 */
	public void setOperationDescField(String value) {
		opDesc = value;
	}

	/**
	 * Set the Originating Country
	 * 
	 * @param value is of type String
	 */
	public void setOriginatingCountry(String value) {
		originCountry = value;
	}

	/**
	 * Set the Originating Organisation
	 * 
	 * @param value is of type String
	 */
	public void setOriginatingOrganisation(String value) {
		originOrganisation = value;
	}

	/**
	 * Set the reference ID
	 * @param aReferenceID - String
	 */
	public void setReferenceID(String aReferenceID) {
		referenceID = aReferenceID;
	}

	/**
	 * Set the remarks
	 * @param aRemarks - String
	 */
	public void setRemarks(String aRemarks) {
		setComment(aRemarks);
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	/**
	 * Set the staging reference ID
	 * @param aStagingReferenceID - String
	 */
	public void setStagingReferenceID(String aStagingReferenceID) {
		stagingReferenceID = aStagingReferenceID;
	}

	/**
	 * Set the status
	 * @param aStatus - String
	 */
	public void setStatus(String aStatus) {
		status = aStatus;
	}

	/**
	 * Set the team membership ID
	 * 
	 * @param value is of type long
	 */
	public void setTeamMembershipID(long value) {
		teamMembershipID = value;
	}

	/**
	 * Set the team type ID
	 * 
	 * @param value is of type long
	 */
	public void setTeamTypeID(long value) {
		teamTypeID = value;
	}

	/**
	 * Set the Transaction sub-type
	 * 
	 * @param value is of type String
	 */
	public void setTransactionSubType(String value) {
		trxSubType = value;
	}

	/**
	 * Set the transaction type
	 * @param aTransactionType - String
	 */
	public void setTransactionType(String aTransactionType) {
		transactionType = aTransactionType;
	}

	/**
	 * Set the trx reference ID
	 * @param aTrxReferenceID - String
	 */
	public void setTrxReferenceID(String aTrxReferenceID) {
		trxReferenceID = aTrxReferenceID;
	}
	
	
	public String getMinEmployeeGrade() {
		return minEmployeeGrade;
	}

	public void setMinEmployeeGrade(String minEmployeeGrade) {
		this.minEmployeeGrade = minEmployeeGrade;
	}

}