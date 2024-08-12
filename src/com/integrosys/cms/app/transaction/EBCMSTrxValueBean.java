/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/EBCMSTrxValueBean.java,v 1.32 2006/07/06 10:25:19 wltan Exp $
 */
package com.integrosys.cms.app.transaction;

import java.util.Collection;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.account.IAccount;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.transaction.OBTrxHistoryValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;

/**
 * This entity bean represents the transaction data for CMS
 * 
 * @author Alfred Lee
 * @version $Revision: 1.32 $
 * @since $Date: 2006/07/06 10:25:19 $
 */
public abstract class EBCMSTrxValueBean implements EntityBean, ICMSTrxValue {

	private static final long serialVersionUID = -7304343960491385872L;

	/**
	 * The Entity Context
	 */
	protected EntityContext entityContext = null;

	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_TRX;

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getTransactionID" };

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getTransactionID", "getTransactionType" };

	private static final String[] EXCLUDE_METHOD_READ = new String[] { "getUserID" };

	private static final String HISTORY_SEQUENCE_NAME = ICMSConstant.SEQUENCE_TRX_HISTORY;

	/**
	 * Default Constructor
	 */
	public EBCMSTrxValueBean() {
	}

	// ***************** abstract Methods ****************

	/**
	 * Get the next team ID to be forwarded this transaction
	 * 
	 * @return Long
	 */
	public abstract Long getCMPTeamID();

	/**
	 * Set the previous state. Not implemented.
	 * 
	 * @param state is of type String
	 */
	public void setPreviousState(String state) {
		// do nothing
	}

	public String getPreviousState() {
		return null;
	}

	public void setToState(String state) {
		// do nothing
	}

	public String getToState() {
		return null;
	}

	/**
	 * Get the team membership ID
	 * 
	 * @return Long
	 */
	public abstract Long getCMPTeamMembershipID();

	/**
	 * Set the instance name.
	 * 
	 * @param name is of type String
	 */
	public void setInstanceName(String name) {
		// do nothing
	}

	public String getInstanceName() {
		return null;
	}

	/**
	 * Sets the status
	 * 
	 * @param status is of type String
	 */
	public abstract void setStatus(String status);

	/**
	 * Sets the transaction ID
	 * 
	 * @param id is of type String
	 */
	public abstract void setTransactionID(String id);

	/**
	 * Sets the UID
	 * 
	 * @param id is of type long
	 */
	public abstract void setUID(long id);

	/**
	 * Set the team ID to be forwarded this transaction
	 * 
	 * @param id is of type Long
	 */
	public abstract void setCMPTeamID(Long id);

	/**
	 * Set the team membership ID
	 * 
	 * @param id is of type Long
	 */
	public abstract void setCMPTeamMembershipID(Long id);

	/**
	 * Sets the transaction type
	 * 
	 * @param type is of type String
	 */
	public abstract void setTransactionType(String type);

	/**
	 * Sets the transaction date
	 * 
	 * @param date is of type Date
	 */
	public abstract void setTransactionDate(Date date);
	
	
	public abstract void setSystemDate(Date date);

	/**
	 * Sets the creation date
	 * 
	 * @param date is of type Date
	 */
	public abstract void setCreateDate(Date date);

	/**
	 * Sets the version time
	 * 
	 * @param version is of type long
	 */
	public abstract void setVersionTime(long version);

	/**
	 * Get the business object reference ID
	 * 
	 * @return long
	 */
	public abstract Long getEBReferenceID();

	/**
	 * Set the business object reference ID
	 * 
	 * @param id is of type long
	 */
	public abstract void setEBReferenceID(Long id);

	/**
	 * Get the staging object reference ID
	 * 
	 * @return long
	 */
	public abstract Long getEBStagingReferenceID();

	/**
	 * Set the staging object reference ID
	 * 
	 * @param id is of type long
	 */
	public abstract void setEBStagingReferenceID(Long id);

	public abstract void setCurrentTrxHistoryID(String value);

	public abstract String getCurrentTrxHistoryID();

	/**
	 * Set the deal no if this represents a commodity deal transaction
	 * 
	 * @param dealNo of type String
	 */
	public abstract void setDealNo(String dealNo);

	/**
	 * Get the deal no if this represents a commodity deal transaction
	 * 
	 * @return String - deal No
	 */
	public abstract String getDealNo();

	/**
	 * Set the transaction reference ID
	 * 
	 * @param trxRefID of type Long
	 */
	public abstract void setEBTrxReferenceID(Long trxRefID);

	/**
	 * Get the transaction reference ID
	 * 
	 * @return String - Transaction Reference ID
	 */
	public abstract Long getEBTrxReferenceID();

	/**
	 * Set the originating country
	 * 
	 * @param ctryCode of type String
	 */
	public abstract void setEBOriginatingCountry(String ctryCode);

	/**
	 * Get the originating country
	 * 
	 * @return String - Originating Country Code
	 */
	public abstract String getEBOriginatingCountry();

	/**
	 * Set the originating organisation
	 * 
	 * @param orgCode of type String
	 */
	public abstract void setEBOriginatingOrganisation(String orgCode);

	/**
	 * Get the originating organisation
	 * 
	 * @return String - Originating Organisation Code
	 */
	public abstract String getEBOriginatingOrganisation();

	/**
	 * Set the segment
	 * 
	 * @param segment of type String
	 */
	public abstract void setEBSegment(String segment);

	/**
	 * Get the segment
	 * 
	 * @return String - segment
	 */
	public abstract String getEBSegment();

	/*
	 * public abstract void setCustomerID(long custID); public abstract void
	 * setCustomerName(String custName);
	 */

	// To make this CMR ?
	public OBTrxHistoryValue[] getTransactionHistory() {
		return null;
	}

	public void setTransactionHistory(OBTrxHistoryValue[] histValue) {
		// Empty Implementation
	}

	public Collection getTransactionHistoryCollection() {
		return null;
	}

	public void setTransactionHistoryCollection(Collection histValue) {
		// Empty Implementation
	}

	public OBCMSTrxRouteInfo[] getNextRouteList() {
		return null;
	}

	public void setNextRouteList(OBCMSTrxRouteInfo[] nextRouteList) {
		// Empty implementation
	}

	public Collection getNextRouteCollection() {
		return null;
	}

	public void setNextRouteCollection(Collection nextRouteCollection) {
		// Empty implementation
	}

	// Now no persistence of toUserInfo, instead it get from DB by DAO. If EJB
	// persisted, remove the bean methods.,
	public String getToUserInfo() {
		return null;
	}

	public void setToUserInfo(String toUserInfo) {
		// Empty implementation
	}

	// ***************** non persistent methods ***********

	/**
	 * Wrapper method to set the transaction reference ID. <br>
	 * If no trx reference ID is specified, then the default trx <br>
	 * reference ID will be persisted. <br>
	 * To Do query will no longer check for null trx reference ID. <br>
	 * 
	 * @param trxRefID of type String
	 */
	public void setTrxReferenceID(String trxRefIDStr) {
		Long trxRefID = null;
		try {
			trxRefID = (CommonUtil.isEmpty(trxRefIDStr)) ? new Long(ICMSConstant.LONG_INVALID_VALUE) : new Long(
					trxRefIDStr);
		}
		catch (Exception e) {
			// convert long exception... do nothing
		}
		setEBTrxReferenceID(trxRefID);
	}

	/**
	 * Wrapper method to get transaction reference ID for a transaction. Return
	 * null if default trx reference ID encountered. <br>
	 * To Do query will no longer check for null trx reference ID. <br>
	 * 
	 * @return String - Originating Country
	 */
	public String getTrxReferenceID() {
		Long trxRefID = getEBTrxReferenceID();
		if (trxRefID == null) {
			return null;
		}
		return (trxRefID.longValue() == ICMSConstant.LONG_INVALID_VALUE) ? null : trxRefID.toString();
	}

	/**
	 * Wrapper method to set the transaction reference ID. <br>
	 * If no reference ID is specified, then the default trx <br>
	 * reference ID will be persisted. <br>
	 * To Do query will no longer check for null reference ID. <br>
	 * 
	 * @param RefID of type String
	 */
	public void setReferenceID(String refIDStr) {
		Long refID = null;
		try {
			refID = (CommonUtil.isEmpty(refIDStr)) ? null : new Long(refIDStr);
		}
		catch (Exception e) {
			// convert long exception... do nothing
		}
		setEBReferenceID(refID);
	}

	/**
	 * Wrapper method to get reference ID for a transaction. Return invalid
	 * value if default reference ID encountered. <br>
	 * To Do query will no longer check for null reference ID. <br>
	 * 
	 * @return String - reference ID
	 */
	public String getReferenceID() {
		Long refID = getEBReferenceID();
		if (refID == null) {
			return null;
		}
		return (refID.longValue() == ICMSConstant.LONG_INVALID_VALUE) ? null : refID.toString();
	}

	/**
	 * Wrapper method to set the transaction reference ID. <br>
	 * If no staging reference ID is specified, then the default trx <br>
	 * staging streference ID will be persisted. <br>
	 * To Do query will no longer check for null reference ID. <br>
	 * 
	 * @param RefID of type String
	 */
	public void setStagingReferenceID(String refIDStr) {
		Long refID = null;
		try {
			refID = (CommonUtil.isEmpty(refIDStr)) ? null : new Long(refIDStr);
		}
		catch (Exception e) {
			// convert long exception... do nothing
		}
		setEBStagingReferenceID(refID);
	}

	/**
	 * Wrapper method to get staging reference ID for a transaction. Return
	 * invalid value if default staging reference ID encountered. <br>
	 * To Do query will no longer check for null staging reference ID. <br>
	 * 
	 * @return String - staging reference ID
	 */
	public String getStagingReferenceID() {
		Long refID = getEBStagingReferenceID();
		if (refID == null) {
			return null;
		}
		return (refID.longValue() == ICMSConstant.LONG_INVALID_VALUE) ? null : refID.toString();
	}

	/**
	 * Wrapper method to set country code for a transaction. <br>
	 * If no country code is specified, then the default country code will be
	 * persisted. <br>
	 * To Do query will no longer check for null country code values. <br>
	 * 
	 * @param ctryCode of type String
	 */
	public void setOriginatingCountry(String ctryCode) {
		String trxCtryCode = (CommonUtil.isEmpty(ctryCode)) ? ICMSConstant.CTRY_CODE_INVALID_VALUE : ctryCode;
		setEBOriginatingCountry(trxCtryCode);
	}

	/**
	 * Wrapper method to get country code for a transaction. Return null if
	 * default country code encountered. <br>
	 * To Do query will no longer check for null country code values. <br>
	 * 
	 * @return String - Originating Country
	 */
	public String getOriginatingCountry() {
		String trxCtryCode = getEBOriginatingCountry();
		return (ICMSConstant.CTRY_CODE_INVALID_VALUE.equals(trxCtryCode)) ? null : trxCtryCode;
	}

	/**
	 * Wrapper method to set organisation code for a transaction. <br>
	 * If no organisation code is specified, then the default organisation code
	 * will be persisted. <br>
	 * To Do query will no longer check for null organisation code values. <br>
	 * 
	 * @param orgCode of type String
	 */
	public void setOriginatingOrganisation(String orgCode) {
		String trxOrgCode = (CommonUtil.isEmpty(orgCode)) ? ICMSConstant.ORG_CODE_INVALID_VALUE : orgCode;
		setEBOriginatingOrganisation(trxOrgCode);
	}

	/**
	 * Wrapper method to get organisation code for a transaction. Return null if
	 * default organisation code encountered. <br>
	 * To Do query will no longer check for null organisation code values. <br>
	 * 
	 * @return String - Originating Organisation Code
	 */
	public String getOriginatingOrganisation() {
		String trxOrgCode = getEBOriginatingOrganisation();
		return (ICMSConstant.ORG_CODE_INVALID_VALUE.equals(trxOrgCode)) ? null : trxOrgCode;
	}

	/**
	 * Wrapper method to set segment code for a transaction. <br>
	 * If no segment specified, then the default segment will be persisted. <br>
	 * To Do query will no longer check for null segment values. <br>
	 * 
	 * @param segment of type String
	 */
	public void setSegment(String segment) {
		String trxSegment = (CommonUtil.isEmpty(segment)) ? ICMSConstant.SEGMENT_INVALID_VALUE : segment;
		setEBSegment(trxSegment);
	}

	/**
	 * Wrapper method to get segment code for a transaction. Return null if
	 * default segment encountered. <br>
	 * To Do query will no longer check for null segment values. <br>
	 * 
	 * @return String - Segment Code
	 */
	public String getSegment() {
		String trxSegment = getEBSegment();
		return (ICMSConstant.SEGMENT_INVALID_VALUE.equals(trxSegment)) ? null : trxSegment;
	}

	/**
	 * Get the ITrxContext associated to this transaction Not implemented
	 * @return ITrxContext
	 */
	public ITrxContext getTrxContext() {

		OBTrxContext ob = new OBTrxContext();
		ob.setTrxCountryOrigin(getOriginatingCountry());
		ob.setTrxOrganisationOrigin(getOriginatingOrganisation());
		ob.setTrxSegment(getSegment());

		OBCMSCustomer obcust = new OBCMSCustomer();
		obcust.setCustomerID(getCustomerID());
		obcust.setCustomerName(getCustomerName());

		ILimitProfile limitProfile = new OBLimitProfile();
		limitProfile.setLimitProfileID(getLimitProfileID());
		limitProfile.setBCAReference(getLimitProfileReferenceNumber());

		ob.setCustomer(obcust);
		ob.setLimitProfile(limitProfile);

		return ob;
	}

	/**
	 * Set the ITrxContext associated to this transaction Not implemented
	 * @param value is of type ITrxContext
	 */
	public void setTrxContext(ITrxContext value) {

		if (null == value) {
			return;
		}

		setOriginatingCountry(value.getTrxCountryOrigin());
		setOriginatingOrganisation(value.getTrxOrganisationOrigin());

		if (value.getCustomer() != null) {
			if (value.getTrxSegment() == null) {
				if (value.getLimitProfile() != null) {
					setSegment(value.getLimitProfile().getSegment());
				}
				else if (value.getCustomer().getCMSLegalEntity() != null) {
					setSegment(value.getCustomer().getCMSLegalEntity().getCustomerSegment());
				}
			}
			else {
				setSegment(value.getTrxSegment());
			}

			setCustomerID(value.getCustomer().getCustomerID());
			setCustomerName(value.getCustomer().getCustomerName());
		}
		else {
			DefaultLogger.warn(this, "Customer is not in the trx context !!!");

			// to ensure default value is set into the transaction
			setSegment(null);
		}
	}

	/**
	 * Set the team ID to be forwarded this transaction
	 * 
	 * @param id is of type long
	 */
	public void setTeamID(long id) {
		setCMPTeamID(new Long(id));
	}

	/**
	 * Set the team membership ID
	 * 
	 * @param id is of type long
	 */
	public void setTeamMembershipID(long id) {
		setCMPTeamMembershipID(new Long(id));
	}

	/**
	 * Get the team ID to be forwarded this transaction
	 * 
	 * @return long
	 */
	public long getTeamID() {
		if (getCMPTeamID() != null) {
			return getCMPTeamID().longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Get the team membership ID
	 * 
	 * @return long
	 */
	public long getTeamMembershipID() {
		if (getCMPTeamMembershipID() != null) {
			return getCMPTeamMembershipID().longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Return the Service Request ID
	 * 
	 * @return String
	 */
	public String getServiceReqId() {
		return null;
	}

	/**
	 * Set the service request ID
	 * 
	 * @param id is of type String
	 */
	public void setServiceReqId(String id) {
		// do nothing
	}

	/**
	 * Return the Service Code
	 * 
	 * @return String
	 */
	public String getServiceCode() {
		return null;
	}

	/**
	 * Set the service code
	 * 
	 * @param code is of type String
	 */
	public void setServiceCode(String code) {
		// do nothing
	}

	/**
	 * Set the user ID. not implemented. use setUID instead
	 * 
	 * @param id is of type string
	 */
	public void setUserID(String id) {
		// do nothing
	}

	/**
	 * Get the user ID. not implemented. use getUID instead
	 * 
	 * @return string
	 */
	public String getUserID() {
		return null;
	}

	/**
	 * Sets the transaction amount
	 * 
	 * @param amt is of type Amount
	 */
	public void setAmount(Amount amt) {
		// do nothing
	}

	/**
	 * Get the transaction amount
	 * 
	 * @return Amount
	 */
	public Amount getAmount() {
		return null;
	}

	/**
	 * Sets the transaction account
	 * 
	 * @param acct is of type IAccount
	 */
	public void setAccount(IAccount acct) {
		// do nothing
	}

	/**
	 * Get the transaction account
	 * 
	 * @return IAccount
	 */
	public IAccount getAccount() {
		return null;
	}

	public abstract long getToUserId();

	public abstract void setToUserId(long uid_);

	public abstract long getToAuthGId();

	public abstract void setToAuthGId(long gid_);

	public abstract long getToAuthGroupTypeId();

	public abstract void setToAuthGroupTypeId(long gtypeid_);

	/**
	 * decorator method to match OBCMSTrxHistoryValue for copy
	 */
	public String getOperationDescField() {
		return this.getOpDesc();
	}

	public String getComment() {
		return this.getRemarks();
	}

	public String getTransactionHistoryID() {
		return this.getCurrentTrxHistoryID();
	}

	// coarse-grained methods
	/**
	 * Return a transaction object.
	 * 
	 * @return OBCMSTrxValue
	 * @throws TransactionException on error
	 */
	public OBCMSTrxValue getTransaction() throws TransactionException {
		OBCMSTrxValue value = new OBCMSTrxValue();
		AccessorUtil.copyValue(this, value, EXCLUDE_METHOD_READ);

		value.setToState(getStatus());
		value.setInstanceName(getTransactionType());
		value.setTrxContext(getTrxContext());

		return value;
	}

	/**
	 * Sets the transaction object.
	 * 
	 * @param value is the ICMSTrxValue object
	 * @throws TransactionException if any transaction related exceptions occur
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setTransaction(ICMSTrxValue value) throws TransactionException, ConcurrentUpdateException {
		if (null == value) {
			throw new TrxParameterException("ICMSTrxValue is null!");
		}
		try {
			String hisSeq = new SequenceManager().getSeqNum(HISTORY_SEQUENCE_NAME, true);
			value.setCurrentTrxHistoryID(hisSeq);
		}
		catch (Exception e) {
			throw new TransactionException("failed to create trx history seq for trx value, trx id ["
					+ value.getTransactionID() + "]", e);
		}

		long beanVer = value.getVersionTime();
		long currentVer = getVersionTime();
		if (beanVer != currentVer) {
			throw new ConcurrentUpdateException("version mismatched, current [" + currentVer + "] submitted version ["
					+ beanVer + "]");
		}

		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD_UPDATE);

		setDealNo(value.getDealNo());
		setStatus(value.getToState());
		setVersionTime(VersionGenerator.getVersionNumber());
		setTrxContext(value.getTrxContext());
	}

	/**
	 * EJB Callback Method
	 */
	/**
	 * Create a record
	 * 
	 * @param value is of type ICMSTrxValue
	 * @return String
	 * @throws CreateException on error
	 */
	public String ejbCreate(ICMSTrxValue value) throws CreateException {

		String transID = null;
		try {
			String hisSeq = new SequenceManager().getSeqNum(HISTORY_SEQUENCE_NAME, true);
			transID = (new SequenceManager()).getSeqNum(SEQUENCE_NAME, true);
			//String hisSeq = new SequenceManager().getSeqNum(HISTORY_SEQUENCE_NAME, true);
			value.setCurrentTrxHistoryID(hisSeq);
		}
		catch (Exception e) {
			CreateException cex = new CreateException("fail to create seq for trx value, trx type ["
					+ value.getTransactionType() + "]");
			cex.initCause(e);
			try {
				if (transID == null) {
					String hisSeq = new SequenceManager().getSeqNum(HISTORY_SEQUENCE_NAME, true);
					transID = hisSeq;
					value.setCurrentTrxHistoryID(hisSeq);
				}
			}catch(Exception e1){
				e1.printStackTrace();
				CreateException cex1 = new CreateException("fail to create seq for trx value, trx type ["
						+ value.getTransactionType() + "]");
				cex1.initCause(e1);
				throw cex1;
			}
			throw cex;
		}

		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD_CREATE);

		setDealNo(value.getDealNo());
		setStatus(value.getToState());
		setVersionTime(VersionGenerator.getVersionNumber());
		setTransactionID(transID);
		setTrxContext(value.getTrxContext());

		return transID;
	}

	/**
	 * Post-Create a record
	 * 
	 * @param value is of type ICMSTrxValue
	 */
	public void ejbPostCreate(ICMSTrxValue value) {
		// do nothing
	}

	/**
	 * EJB callback method
	 */
	public void ejbActivate() {
		// DefaultLogger.debug(this, "in ejbActivate...");
	}

	/**
	 * EJB callback method
	 */
	public void ejbPassivate() {
		// DefaultLogger.debug(this, "in ejbPassivate...");
	}

	/**
	 * EJB callback method
	 */
	public void ejbLoad() {
		// DefaultLogger.debug(this, "in ejbLoad...");
	}

	/**
	 * EJB callback method
	 */
	public void ejbStore() {
		// DefaultLogger.debug(this, "in ejbStore...");
		// DefaultLogger.debug(this, "Saving State: " + getStatus());
	}

	/**
	 * EJB callback method
	 */
	public void ejbRemove() {
		// DefaultLogger.debug(this, "in ejbRemove...");
	}

	/**
	 * EJB Callback Method
	 */
	public void setEntityContext(EntityContext ctx) {
		// DefaultLogger.debug(this, "in setEntityContext...");
		entityContext = ctx;
	}

	/**
	 * EJB Callback Method
	 */
	public void unsetEntityContext() {
		// DefaultLogger.debug(this, "in unsetEntityContext...");
		entityContext = null;
	}

	public abstract long getTeamTypeID();

	public abstract String getRemarks();

	public abstract String getTransactionType();

	public abstract String getOpDesc();

	public abstract String getLegalName();

	public abstract String getCustomerName();

	public abstract String getLegalID();

	public abstract long getCustomerID();

	public abstract long getLimitProfileID();

	public abstract String getTransactionSubType();

	public abstract void setTeamTypeID(long value);

	public abstract void setRemarks(String aRemarks);

	public abstract void setOpDesc(String value);

	public abstract void setLegalName(String value);

	public abstract void setCustomerName(String value);

	public abstract void setLegalID(String value);

	public abstract void setCustomerID(long value);

	public abstract void setLimitProfileID(long value);

	public abstract void setTransactionSubType(String value);

	public abstract void setUserInfo(String userInfo);

	public abstract String getUserInfo();

	public abstract String getStatus();

	public abstract String getFromState();

	public abstract String getTransactionID();

	public abstract long getUID();

	public abstract Date getTransactionDate();

	public abstract Date getSystemDate();
	
	public abstract Date getCreateDate();

	public abstract void setFromState(String s);

	public abstract long getVersionTime();

	public abstract String getLimitProfileReferenceNumber();

	public abstract void setLimitProfileReferenceNumber(String limitProfileReferenceNumber);

	public abstract String getLoginId();

	public abstract void setLoginId(String loginId);
	
	public abstract String getMinEmployeeGrade();

	public abstract void setMinEmployeeGrade(String minEmployeeGrade);
}