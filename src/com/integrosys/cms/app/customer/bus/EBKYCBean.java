/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBKYCBean.java,v 1.8 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Date;

/**
 * This entity bean represents the persistence for KYC Information
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.8 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public abstract class EBKYCBean implements EntityBean, IKYC {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_KYC;

	private static final String[] EXCLUDE_METHOD = new String[] { "getKYCID", "getLEID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBKYCBean() {
	}

	// ************* Non-persistent methods ***********
	/**
	 * Get Legal ID
	 * 
	 * @return long
	 */
	public long getLEID() {
		if (null != getLEFK()) {
			return getLEFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Get the KYC ID
	 * 
	 * @return long
	 */
	public long getKYCID() {
		if (null != getKYCPK()) {
			return getKYCPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Get CDD Doc Submit Indicator
	 * 
	 * @return boolean
	 */
	public boolean getCDDSubmitInd() {
		String value = getCDDSubmitStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Get KYC Doc Submit Indicator
	 * 
	 * @return boolean
	 */
	public boolean getKYCSubmitInd() {
		String value = getKYCSubmitStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Get GIC Doc Submit Indicator
	 * 
	 * @return boolean
	 */
	public boolean getGICSubmitInd() {
		String value = getGICSubmitStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Get Exceptional Approval Indicator
	 * 
	 * @return boolean
	 */
	public boolean getExceptionalApprovalInd() {
		String value = getExceptionalApprovalStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	// Setters
	/**
	 * Set Legal ID
	 * 
	 * @param value is of type long
	 */
	public void setLEID(long value) {
		setLEFK(new Long(value));
	}

	/**
	 * Set the KYC ID
	 * 
	 * @param value is of type long
	 */
	public void setKYCID(long value) {
		setKYCPK(new Long(value));
	}

	/**
	 * Set CDD Doc Submit Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setCDDSubmitInd(boolean value) {
		if (true == value) {
			setCDDSubmitStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setCDDSubmitStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set KYC Doc Submit Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setKYCSubmitInd(boolean value) {
		if (true == value) {
			setKYCSubmitStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setKYCSubmitStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set GIC Doc Submit Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setGICSubmitInd(boolean value) {
		if (true == value) {
			setGICSubmitStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setGICSubmitStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set Exceptional Approval Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setExceptionalApprovalInd(boolean value) {
		if (true == value) {
			setExceptionalApprovalStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setExceptionalApprovalStr(ICMSConstant.FALSE_VALUE);
		}
	}

	// ************** Abstract methods ************
	/**
	 * Get Legal FK
	 * 
	 * @return Long
	 */
	public abstract Long getLEFK();

	/**
	 * Get KYC PK
	 * 
	 * @return Long
	 */
	public abstract Long getKYCPK();

	/**
	 * Get CDD Doc Submit Indicator
	 * 
	 * @return String
	 */
	public abstract String getCDDSubmitStr();

	/**
	 * Get KYC Doc Submit Indicator
	 * 
	 * @return String
	 */
	public abstract String getKYCSubmitStr();

	/**
	 * Get GIC Doc Submit Indicator
	 * 
	 * @return String
	 */
	public abstract String getGICSubmitStr();

	/**
	 * Get Exceptional Approval Indicator
	 * 
	 * @return String
	 */
	public abstract String getExceptionalApprovalStr();

	/**
	 * Set Legal ID
	 * 
	 * @param value is of type Long
	 */
	public abstract void setLEFK(Long value);

	/**
	 * Set the KYC PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setKYCPK(Long value);

	/**
	 * Set CDD Doc Submit Indicator
	 * 
	 * @param value is of type String
	 */
	public abstract void setCDDSubmitStr(String value);

	/**
	 * Set KYC Doc Submit Indicator
	 * 
	 * @param value is of type String
	 */
	public abstract void setKYCSubmitStr(String value);

	/**
	 * Set GIC Doc Submit Indicator
	 * 
	 * @param value is of type String
	 */
	public abstract void setGICSubmitStr(String value);

	/**
	 * Set Exceptional Approval Indicator
	 * 
	 * @param value is of type String
	 */
	public abstract void setExceptionalApprovalStr(String value);

	// *****************************************************
	/**
	 * Create a KYC Information
	 * 
	 * @param legalID the customer ID of type long
	 * @param value is the IKYC object
	 * @return Long the KYC ID primary key
	 * @throws CreateException on error
	 */
	public Long ejbCreate(long legalID, IKYC value) throws CreateException {
		if (null == value) {
			throw new CreateException("IKYC is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true)); // always
																								// regenerate
																								// PK
			/*
			 * long pk =com.integrosys.cms.app.common.constant.ICMSConstant.
			 * LONG_INVALID_VALUE; if(value.getKYCID() ==
			 * com.integrosys.cms.app.
			 * common.constant.ICMSConstant.LONG_INVALID_VALUE) { pk =
			 * Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME,
			 * true)); } else { pk = value.getKYCID(); }
			 */
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			// setLEID(legalID); //to be set by cmr
			setKYCID(pk);

			return new Long(pk);
		}
		catch (Exception e) {
			e.printStackTrace();
			_context.setRollbackOnly();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}

	/**
	 * Post-Create a record
	 * 
	 * @param legalID the customer ID of type long
	 * @param value is the IKYC object
	 */
	public void ejbPostCreate(long legalID, IKYC value) {
		// do nothing
	}

	/**
	 * Return the OB representation of this object
	 * 
	 * @return IKYC
	 */
	public IKYC getValue() {
		OBKYC value = new OBKYC();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Persist a KYC information
	 * 
	 * @param value is of type IKYC
	 */
	public void setValue(IKYC value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}

	// ************************************************************************
	/**
	 * EJB callback method
	 */
	public void ejbActivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbPassivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbLoad() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbStore() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbRemove() {
	}

	/**
	 * EJB Callback Method
	 */
	public void setEntityContext(EntityContext ctx) {
		_context = ctx;
	}

	/**
	 * EJB Callback Method
	 */
	public void unsetEntityContext() {
		_context = null;
	}

    public abstract String getOriginatingLocation();

    public abstract String getRiskRating();

    public abstract Date getLastReviewDate();

    public abstract Date getNextReviewDate();

    public abstract void setOriginatingLocation(String value);

    public abstract void setRiskRating(String value);

    public abstract void setLastReviewDate(Date value);

    public abstract void setNextReviewDate(Date value);
}