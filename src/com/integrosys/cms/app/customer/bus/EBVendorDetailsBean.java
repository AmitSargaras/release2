package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBVendorDetailsBean implements EntityBean, IVendor {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_CONTACT;

	private static final String[] EXCLUDE_METHOD = new String[] { "getVendorId","getLEID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBVendorDetailsBean() {
	}

	// ************* Non-persistent methods ***********
	/**
	 * Get the contact ID
	 * 
	 * @return long
	 */
	public long getVendorId() {
		if (null != getSystemPK()) {
			return getSystemPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	
	
	public void setVendorId(long value) {
		setSystemPK(new Long(value));
	}

	

	public Long ejbCreate(IVendor value) throws CreateException {
		if (null == value) {
			throw new CreateException("IVendor is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setVendorId(pk);
			

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
	 * @param value is the IContact object
	 */
	public void ejbPostCreate(IVendor value) {
		// do nothing
	}

	/**
	 * Return the OB representation of this object
	 * 
	 * @return IContact
	 */
	public IVendor getValue() {
		OBVendor value = new OBVendor();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Persist a contact information
	 * 
	 * @param value is of type IContact
	 */
	public void setValue(IVendor value) {
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

	public long getLEID() {
		if (null != getLEFK()) {
			return getLEFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}
	
	public void setLEID(long value) {
		setLEFK(new Long(value));
	}

	public abstract String getVendorName();

	public abstract void setVendorName(String vendorName) ;/*

	public abstract String getCustomerId();

	public abstract void setCustomerId(String customerId) ;*/
	
	public abstract Long getSystemPK();

	public abstract void setSystemPK(Long value);
	
	public abstract Long getLEFK();
	
	public abstract void setLEFK(Long value);

	

}
