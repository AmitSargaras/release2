/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCreditStatusBean.java,v 1.7 2003/08/22 11:13:26 sathish Exp $
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
 * This entity bean represents the persistence for Credit Status Information
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.7 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public abstract class EBCreditStatusBean implements EntityBean, ICreditStatus {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_CREDIT_STATUS;

	private static final String[] EXCLUDE_METHOD = new String[] { "getCSID", "getLEID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBCreditStatusBean() {
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
	 * Get the CS ID
	 * 
	 * @return long
	 */
	public long getCSID() {
		if (null != getCSPK()) {
			return getCSPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Set Legal ID
	 * 
	 * @param value is of type long
	 */
	public void setLEID(long value) {
		setLEFK(new Long(value));
	}

	/**
	 * Set the CS ID
	 * 
	 * @param value is of type long
	 */
	public void setCSID(long value) {
		setCSPK(new Long(value));
	}

	// ************** Abstract methods ************
	/**
	 * Get Legal FK
	 * 
	 * @return Long
	 */
	public abstract Long getLEFK();

	/**
	 * Get the CS PK
	 * 
	 * @return Long
	 */
	public abstract Long getCSPK();

	/**
	 * Set Legal FK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setLEFK(Long value);

	/**
	 * Set the CS PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setCSPK(Long value);

	// *****************************************************
	/**
	 * Create a CreditStatus Information
	 * 
	 * @param legalID the customer ID of type long
	 * @param value is the ICreditStatus object
	 * @return Long the CreditStatus ID primary key
	 * @throws CreateException on error
	 */
	public Long ejbCreate(long legalID, ICreditStatus value) throws CreateException {
		if (null == value) {
			throw new CreateException("ICreditStatus is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));
			/*
			 * long pk =com.integrosys.cms.app.common.constant.ICMSConstant.
			 * LONG_INVALID_VALUE; if(value.getCSID() ==
			 * com.integrosys.cms.app.common
			 * .constant.ICMSConstant.LONG_INVALID_VALUE) { pk =
			 * Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME,
			 * true)); } else { pk = value.getCSID(); }
			 */
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			// setLEID(legalID); ////to be set by cmr
			setCSID(pk);

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
	 * @param value is the ICreditStatus object
	 */
	public void ejbPostCreate(long legalID, ICreditStatus value) {
		// do nothing
	}

	/**
	 * Return the OB representation of this object
	 * 
	 * @return ICreditStatus
	 */
	public ICreditStatus getValue() {
		OBCreditStatus value = new OBCreditStatus();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Persist a CreditStatus information
	 * 
	 * @param value is of type ICreditStatus
	 */
	public void setValue(ICreditStatus value) {
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

    public abstract String getCSReference();

    public abstract String getCSType();

    public abstract String getCSValue();

    public abstract Date getEffectiveDate();

    public abstract String getComments();

    public abstract void setCSReference(String value);

    public abstract void setCSType(String value);

    public abstract void setCSValue(String value);

    public abstract void setEffectiveDate(Date value);

    public abstract void setComments(String value);
}