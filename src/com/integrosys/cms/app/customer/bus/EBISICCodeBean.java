/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBISICCodeBean.java,v 1.7 2003/08/22 11:13:26 sathish Exp $
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
 * This entity bean represents the persistence for ISIC Code Information
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.7 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public abstract class EBISICCodeBean implements EntityBean, IISICCode {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_ISIC_CODE;

	private static final String[] EXCLUDE_METHOD = new String[] { "getISICID", "getLEID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBISICCodeBean() {
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
	 * Get the ISIC ID
	 * 
	 * @return long
	 */
	public long getISICID() {
		if (null != getISICPK()) {
			return getISICPK().longValue();
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
	 * Set the ISIC ID
	 * 
	 * @param value is of type long
	 */
	public void setISICID(long value) {
		setISICPK(new Long(value));
	}

	// ************** Abstract methods ************
	/**
	 * Get Legal FK
	 * 
	 * @return Long
	 */
	public abstract Long getLEFK();

	/**
	 * Get the ISIC PK
	 * 
	 * @return Long
	 */
	public abstract Long getISICPK();

	/**
	 * Set Legal FK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setLEFK(Long value);

	/**
	 * Set the ISIC PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setISICPK(Long value);

	// *****************************************************
	/**
	 * Create a ISICCode Information
	 * 
	 * @param legalID the customer ID of type long
	 * @param value is the IISICCode object
	 * @return Long the ISICCode ID primary key
	 * @throws CreateException on error
	 */
	public Long ejbCreate(long legalID, IISICCode value) throws CreateException {
		if (null == value) {
			throw new CreateException("IISICCode is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));
			/*
			 * long pk =com.integrosys.cms.app.common.constant.ICMSConstant.
			 * LONG_INVALID_VALUE; if(value.getISICID() ==
			 * com.integrosys.cms.app
			 * .common.constant.ICMSConstant.LONG_INVALID_VALUE) { pk =
			 * Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME,
			 * true)); } else { pk = value.getISICID(); }
			 */
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			// setLEID(legalID); //to be set by cmr
			setISICID(pk);

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
	 * @param value is the IISICCode object
	 */
	public void ejbPostCreate(long legalID, IISICCode value) {
		// do nothing
	}

	/**
	 * Return the OB representation of this object
	 * 
	 * @return IISICCode
	 */
	public IISICCode getValue() {
		OBISICCode value = new OBISICCode();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Persist a ISICCode information
	 * 
	 * @param value is of type IISICCode
	 */
	public void setValue(IISICCode value) {
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

    public abstract String getISICReference();

    public abstract String getISICType();

    public abstract String getISICCode();

    public abstract Date getEffectiveDate();

    public abstract String getISICWeightage();

    public abstract String getISICStatus();

    public abstract void setISICReference(String value);

    public abstract void setISICType(String value);

    public abstract void setISICCode(String value);

    public abstract void setEffectiveDate(Date value);

    public abstract void setISICWeightage(String value);

    public abstract void setISICStatus(String value);
}