/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/ICMSTrxValue.java,v 1.26 2005/09/29 10:28:21 whuang Exp $
 */
package com.integrosys.cms.app.transaction;

import java.util.Collection;
import java.util.Date;

import com.integrosys.base.businfra.account.IAccount;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.OBTrxHistoryValue;

/**
 * This interface represents the transaction value object for CMS.
 * 
 * @author Alfred Lee
 */
public interface ICMSTrxValue extends ITrxValue {
	public String getComment();

	/**
	 * Get the current transaction history ID
	 * 
	 * @return String
	 */
	public String getCurrentTrxHistoryID();

	/**
	 * Get CustomerID
	 * 
	 * @return long
	 */
	public long getCustomerID();

	/**
	 * Get Custoomer Name
	 * 
	 * @return String
	 */
	public String getCustomerName();

	public String getDealNo();

	/**
	 * Get Legal ID
	 * 
	 * @return String
	 */
	public String getLegalID();

	/**
	 * Get the Legal Name of the customer
	 * 
	 * @return String
	 */
	public String getLegalName();

	/**
	 * Get the limit profile ID
	 * 
	 * @return long
	 */
	public long getLimitProfileID();

	/**
	 * Get the limit profile reference number, ie. AA number
	 * 
	 * @return limit profile reference number which is the AA number
	 */
	public String getLimitProfileReferenceNumber();

	public String getLoginId();

	public Collection getNextRouteCollection();

	/**
	 * next Route List
	 * @since 01/07/2004
	 */
	public OBCMSTrxRouteInfo[] getNextRouteList();

	/**
	 * Get the Operation Description
	 * 
	 * @return String
	 */
	public String getOpDesc();

	/**
	 * decorator method to match OBCMSTrxHistoryValue for copy
	 */
	public String getOperationDescField();

	/**
	 * Get the Originating Country
	 * 
	 * @return String
	 */
	public String getOriginatingCountry();

	/**
	 * Get the Originating Organisation
	 * 
	 * @return String
	 */
	public String getOriginatingOrganisation();

	/**
	 * Get the business object reference ID
	 * 
	 * @return String
	 */
	public String getReferenceID();

	/**
	 * Get the remarks
	 * 
	 * @return String
	 */
	public String getRemarks();

	/**
	 * Get the staging object reference ID
	 * 
	 * @return String
	 */
	public String getStagingReferenceID();

	/**
	 * Get the team ID
	 * 
	 * @return long
	 */
	public long getTeamID();

	/**
	 * Get the Team Membership ID - long
	 * 
	 * @return long
	 */
	public long getTeamMembershipID();

	/**
	 * Get the team type ID
	 * 
	 * @return long
	 */
	public long getTeamTypeID();

	public long getToAuthGId();

	public long getToAuthGroupTypeId();

	/**
	 * next selected route information, set by Operation's getNextRoute
	 * @since 03/06/2004
	 */
	public long getToUserId();

	public String getToUserInfo();

	public OBTrxHistoryValue[] getTransactionHistory();

	public Collection getTransactionHistoryCollection();

	public String getTransactionHistoryID();

	/**
	 * Get the Transaction sub-type
	 * 
	 * @return String
	 */
	public String getTransactionSubType();

	/**
	 * Get the transactionType
	 * 
	 * @return String
	 */
	public String getTransactionType();

	/**
	 * Get the ITrxContext associated to this transaction
	 * 
	 * @return ITrxContext
	 */
	public ITrxContext getTrxContext();

	/**
	 * Get Route Criteria
	 * 
	 * @return ITrxRouteCriteria
	 */
	// public ITrxRouteCriteria getRouteCriteria();
	/**
	 * Get the trx reference ID. This will be the transaction ID of the parents
	 * 
	 * @return String
	 */
	public String getTrxReferenceID();

	public String getUserInfo();

	/**
	 * Set the Account related to this transaction
	 * 
	 * @param acct is of type IAccount
	 */
	public void setAccount(IAccount acct);

	/**
	 * Set the transaction amount
	 * 
	 * @param value is of type Amount
	 */
	public void setAmount(Amount value);

	/**
	 * Set the create date
	 * 
	 * @param value is of type Date
	 */
	public void setCreateDate(Date value);

	/**
	 * Set the current transaction history ID
	 * 
	 * @param value is of type String
	 */
	public void setCurrentTrxHistoryID(String value);

	/**
	 * Set CustomerID
	 * 
	 * @param value is of type long
	 */
	public void setCustomerID(long value);

	/**
	 * Set Custoomer Name
	 * 
	 * @param value is of type String
	 */
	public void setCustomerName(String value);

	public void setDealNo(String dealNo);

	/**
	 * Set Legal ID
	 * 
	 * @param value is of type String
	 */
	public void setLegalID(String value);

	/**
	 * Set the Legal Name of the customer
	 * 
	 * @param value is of type String
	 */
	public void setLegalName(String value);

	/**
	 * Set the limit profile ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitProfileID(long value);

	/**
	 * Set the limit profile reference number, ie AA number
	 * 
	 * @param limitProfileReferenceNumber limit profile reference number, ie AA
	 *        number to be set into transaction value.
	 */
	public void setLimitProfileReferenceNumber(String limitProfileReferenceNumber);

	public void setLoginId(String loginId);

	public void setNextRouteCollection(Collection nextRouteCollection);

	public void setNextRouteList(OBCMSTrxRouteInfo[] nextRouteList);

	/**
	 * Set the Operation Description
	 * 
	 * @param value is of type String
	 */
	public void setOpDesc(String value);

	/**
	 * Set the Originating Country
	 * 
	 * @param value is of type String
	 */
	public void setOriginatingCountry(String value);

	/**
	 * Set the Originating Organisation
	 * 
	 * @param value is of type String
	 */
	public void setOriginatingOrganisation(String value);

	/**
	 * Set the previous state
	 * 
	 * @param aPreviousState - String
	 */
	public void setPreviousState(String aPreviousState);

	// setters
	/**
	 * Set the business object reference ID
	 * 
	 * @param aReferenceID - String
	 */
	public void setReferenceID(String aReferenceID);

	/**
	 * Set the remarks
	 */
	public void setRemarks(String aRemarks);

	/**
	 * Set the staging object reference ID
	 * 
	 * @param aStagingReferenceID - String
	 */
	public void setStagingReferenceID(String aStagingReferenceID);

	/**
	 * Set status
	 * 
	 * @param value is of type String
	 */
	public void setStatus(String value);

	/**
	 * Set the team ID
	 * 
	 * @param aTeamID - long
	 */
	public void setTeamID(long aTeamID);

	/**
	 * Set the Team Membership ID
	 * 
	 * @param value is of type long
	 */
	public void setTeamMembershipID(long value);

	/**
	 * Set the team type ID
	 * 
	 * @param value is of type long
	 */
	public void setTeamTypeID(long value);

	public void setToAuthGId(long gid_);

	public void setToAuthGroupTypeId(long gtypeid_);

	public void setToUserId(long uid_);

	public void setToUserInfo(String toUserInfo);

	/**
	 * Set the transaction date
	 * 
	 * @param value is of type Date
	 */
	public void setTransactionDate(Date value);
	
	public void setSystemDate(Date value);

	public void setTransactionHistory(OBTrxHistoryValue[] historyValue);

	public void setTransactionHistoryCollection(Collection historyValue);

	/**
	 * Set the transaction ID
	 * 
	 * @param value is of type String
	 */
	public void setTransactionID(String value);

	/**
	 * Set the Transaction sub-type
	 * 
	 * @param value is of type String
	 */
	public void setTransactionSubType(String value);

	/**
	 * Set the transaction type
	 * 
	 * @param aTransactionType - String
	 */
	public void setTransactionType(String aTransactionType);

	/**
	 * Set Route Criteria
	 * 
	 * @param value is of type ITrxRouteCriteria
	 */
	// public void setRouteCriteria(ITrxRouteCriteria value);
	/**
	 * Set the ITrxContext associated to this transaction
	 * 
	 * @param value is of type ITrxContext
	 */
	public void setTrxContext(ITrxContext value);

	/**
	 * Set the trx reference ID
	 * @param aTrxReferenceID - String
	 */
	public void setTrxReferenceID(String aTrxReferenceID);

	/**
	 * Set the UID
	 * 
	 * @param value is of type long
	 */
	public void setUID(long value);

	/**
	 * Set the User ID
	 * 
	 * @param value is of type String
	 */
	public void setUserID(String value);

	public void setUserInfo(String userInfo);

	
	public String getMinEmployeeGrade();
	public void setMinEmployeeGrade(String minEmployeeGrade);
}
