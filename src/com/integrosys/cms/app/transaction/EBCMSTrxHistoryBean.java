/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/EBCMSTrxHistoryBean.java,v 1.17 2005/09/29 10:28:05 whuang Exp $
 */
package com.integrosys.cms.app.transaction;

import java.util.Date;

import javax.ejb.CreateException;

import com.integrosys.base.businfra.transaction.EBTrxHistoryBean;
import com.integrosys.base.businfra.transaction.OBTrxHistoryValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This entity bean stores the transaction history. All the basic persistence
 * operations required for logging the transaction history can be found here.
 * 
 * @author $Author: whuang $
 * @version $Revision: 1.17 $
 * @since $Date: 2005/09/29 10:28:05 $
 */
public abstract class EBCMSTrxHistoryBean extends EBTrxHistoryBean {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_TRX_HISTORY;

	long a = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	public abstract String getReferenceID();

	public abstract void setReferenceID(String aReferenceID);

	public abstract String getStagingReferenceID();

	public abstract void setStagingReferenceID(String aStagingReferenceID);

	public abstract String getTransactionType();

	public abstract void setTransactionType(String aTransactionType);

	public abstract Date getCreateDate();

	public abstract void setCreateDate(Date d);

	public abstract String getTrxReferenceID();

	public abstract void setTrxReferenceID(String aTrxReferenceID);

	public abstract String getStatus();

	public abstract void setStatus(String aStatus);

	public abstract String getComment();

	public abstract void setComment(String aComment);

	public abstract String getM_toUId();

	public abstract void setM_toUId(String aUID);

	public abstract void setM_toAuthGId(String aUID);

	public abstract String getM_toAuthGId();

	/**
	 * Get the Legal Name of the customer
	 * 
	 * @return String
	 */
	public abstract String getLegalName();

	/**
	 * Get Custoomer Name
	 * 
	 * @return String
	 */
	public abstract String getCustomerName();

	/**
	 * Get Legal ID
	 * 
	 * @return String
	 */
	public abstract String getLegalID();

	/**
	 * Get CustomerID
	 * 
	 * @return long
	 */
	public abstract long getCustomerID();

	/**
	 * Get the Originating Country
	 * 
	 * @return String
	 */
	public abstract String getOriginatingCountry();

	/**
	 * Get the Originating Organisation
	 * 
	 * @return String
	 */
	public abstract String getOriginatingOrganisation();

	/**
	 * Get the Transaction sub-type
	 * 
	 * @return String
	 */
	public abstract String getTransactionSubType();

	/**
	 * Get the limit profile ID
	 * 
	 * @return long
	 */
	public abstract long getLimitProfileID();

	/**
	 * Get the limit profile reference number, ie AA number
	 * 
	 * @return limit profile reference number, ie AA number
	 */
	public abstract String getLimitProfileReferenceNumber();

	/**
	 * Get the team type ID
	 * 
	 * @return long
	 */
	public abstract long getTeamTypeID();

	/**
	 * Set the team type ID
	 * 
	 * @param value is of type long
	 */
	public abstract void setTeamTypeID(long value);

	/**
	 * Set the Legal Name of the customer
	 * 
	 * @param value is of type String
	 */
	public abstract void setLegalName(String value);

	/**
	 * Set Custoomer Name
	 * 
	 * @param value is of type String
	 */
	public abstract void setCustomerName(String value);

	/**
	 * Set Legal ID
	 * 
	 * @param value is of type String
	 */
	public abstract void setLegalID(String value);

	/**
	 * Set CustomerID
	 * 
	 * @param value is of type long
	 */
	public abstract void setCustomerID(long value);

	/**
	 * Set the Originating Country
	 * 
	 * @param value is of type String
	 */
	public abstract void setOriginatingCountry(String value);

	/**
	 * Set the Originating Organisation
	 * 
	 * @param value is of type String
	 */
	public abstract void setOriginatingOrganisation(String value);

	/**
	 * Set the limit profile ID
	 * 
	 * @param value is of type long
	 */
	public abstract void setLimitProfileID(long value);

	/**
	 * Set the limit profile reference number, ie AA number
	 * 
	 * @param value the limit profile reference number to be set
	 */
	public abstract void setLimitProfileReferenceNumber(String value);

	public abstract void setLoginId(String loginId);

	public abstract String getLoginId();

	/**
	 * Set the Transaction sub-type
	 * 
	 * @param value is of type String
	 */
	public abstract void setTransactionSubType(String value);

	public abstract void setSegment(String segment);

	public abstract String getSegment();

	public abstract void setDealNo(String dealNo);

	public abstract String getDealNo();

	public abstract void setTeamMembershipID(long value);

	public abstract long getTeamMembershipID();
	
	
	public abstract String getMinEmployeeGrade();

	public abstract void setMinEmployeeGrade(String minEmployeeGrade) ;


	/**
	 * Default Constructor
	 */
	public EBCMSTrxHistoryBean() {
		super();
	}

	/**
	 * Not implemented here
	 */
	public String getM_description() {
		return null;
	}

	/**
	 * Not implemented here
	 */
	public void setM_description(String aDescription) {
	}

	/**
	 * Not implemented here
	 */
	public String getAuthenticationCode() {
		return null;
	}

	/**
	 * Not implemented here
	 */
	public void setAuthenticationCode(String anAuthenticationCode) {
	}

	/**
	 * Not implemented here
	 */
	public String getM_verificationId() {
		return null;
	}

	/**
	 * Not implemented here
	 */
	public void setM_verificationId(String aVerificationId) {
	}

	/**
	 * Retrieve the Sequence Name
	 * 
	 * @return String
	 */
	protected String retrieveSequenceName() {
		return SEQUENCE_NAME;
	}

	/**
	 * Updates a record history for a transaction.
	 * 
	 * @param value transaction history business object
	 */
	public void setValue(OBTrxHistoryValue value) {
		if (value == null) {
			return;
		}
		OBCMSTrxHistoryValue val = (OBCMSTrxHistoryValue) value;
		super.setValue(val);
		setReferenceID(val.getReferenceID());
		setStagingReferenceID(val.getStagingReferenceID());
		setTransactionType(val.getTransactionType());
		setCreateDate(val.getCreateDate());
		setTrxReferenceID(val.getTrxReferenceID());
		setStatus(val.getStatus());
		setComment(val.getComment());
		setOperationDescField(val.getOperationDescField());
		setLegalName(val.getLegalName());
		setTeamTypeID(val.getTeamTypeID());
		setCustomerName(val.getCustomerName());
		setLegalID(val.getLegalID());
		setCustomerID(val.getCustomerID());
		setOriginatingCountry(val.getOriginatingCountry());
		setOriginatingOrganisation(val.getOriginatingOrganisation());
		setLimitProfileID(val.getLimitProfileID());
		setTransactionSubType(val.getTransactionSubType());
		setTeamMembershipID(val.getTeamMembershipID());
		setLoginId(val.getLoginId());
		setMinEmployeeGrade(val.getMinEmployeeGrade());

		setSegment(val.getSegment());
	}

	/**
	 * Get the transaction history value of this entity bean.
	 * 
	 * @return transaction history business object
	 */
	public OBTrxHistoryValue getValue() {
		OBCMSTrxHistoryValue value = new OBCMSTrxHistoryValue(getM_transactionId(), getM_timeOccured());
		value.setFromState(getFromState());
		value.setToState(getToState());
		value.setFromUser(getM_fromUId(), getM_fromAuthGId());
		value.setToUser(getM_toUId(), getM_toAuthGId());
		value.setComment(getM_description());
		value.setReferenceCode(getAuthenticationCode());
		value.setTimeOccured(getM_timeOccured());
		value.setCurrentCycle(getM_currentCycle());
		value.setVerificationId(getM_verificationId());
		value.setReferenceID(getReferenceID());
		value.setStagingReferenceID(getStagingReferenceID());
		value.setTransactionType(getTransactionType());
		value.setCreateDate(getCreateDate());
		value.setTrxReferenceID(getTrxReferenceID());
		value.setStatus(getStatus());
		value.setComment(getComment());
		value.setOperationDescField(getOperationDescField());
		value.setLegalName(getLegalName());
		value.setTeamTypeID(getTeamTypeID());
		value.setCustomerName(getCustomerName());
		value.setLegalID(getLegalID());
		value.setCustomerID(getCustomerID());
		value.setOriginatingCountry(getOriginatingCountry());
		value.setOriginatingOrganisation(getOriginatingOrganisation());
		value.setLimitProfileID(getLimitProfileID());
		value.setLimitProfileReferenceNumber(getLimitProfileReferenceNumber());
		value.setTransactionSubType(getTransactionSubType());
		value.setTeamMembershipID(getTeamMembershipID());
		value.setLoginId(getLoginId());
		value.setMinEmployeeGrade(getMinEmployeeGrade());
		
		return value;
	}

	/**
	 * Creates history for a transaction.
	 * 
	 * @param value transaction history business object
	 * @throws CreateException on error creating the transaction history
	 */
	public String ejbCreate(OBTrxHistoryValue value) throws CreateException {

		// String historyID = (new
		// SequenceManager()).getSeqNum(retrieveSequenceName());
		// DefaultLogger.debug(this, "HistoryID: " + historyID);
		// setHistoryId(historyID);
		setM_transactionId(value.getTransactionId());
		setM_fromUId(value.getFromUser());
		setFromState(value.getFromState());
		setToState(value.getToState());
		setM_fromAuthGId(value.getFromAuthGId());
		setM_toUId(value.getToUser());
		setM_toAuthGId(value.getToAuthGId());
		setM_description(value.getComment());
		setOperationDescField(value.getOperationDesc());
		setM_currentCycle(value.getCurrentCycle());
		setM_timeOccured(value.getTimeOccured());
		setAuthenticationCode(value.getReferenceCode());
		setM_verificationId(value.getVerificationId());
		// return historyID;

		// super.ejbCreate (value);

		setHistoryId(value.getTransactionHistoryID());

		OBCMSTrxHistoryValue val = (OBCMSTrxHistoryValue) value;

		setM_fromUId(String.valueOf(val.getUserID()));
		setM_fromAuthGId(String.valueOf(val.getTeamID()));
		setReferenceID(val.getReferenceID());
		setStagingReferenceID(val.getStagingReferenceID());
		setTransactionType(val.getTransactionType());
		setCreateDate(val.getCreateDate());
		setTrxReferenceID(val.getTrxReferenceID());
		setStatus(val.getStatus());
		setComment(val.getComment());
		setOperationDescField(val.getOperationDescField());
		setLegalName(val.getLegalName());
		setTeamTypeID(val.getTeamTypeID());
		setCustomerName(val.getCustomerName());
		setLegalID(val.getLegalID());
		setCustomerID(val.getCustomerID());
		setOriginatingCountry(val.getOriginatingCountry());
		setOriginatingOrganisation(val.getOriginatingOrganisation());
		setLimitProfileID(val.getLimitProfileID());
		setLimitProfileReferenceNumber(val.getLimitProfileReferenceNumber());
		setTransactionSubType(val.getTransactionSubType());
		setTeamMembershipID(val.getTeamMembershipID());
		setLoginId(val.getLoginId());
		setMinEmployeeGrade(val.getMinEmployeeGrade());
		
		return getHistoryId();
	}
}