/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBContactBean.java,v 1.8 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This entity bean represents the persistence for Contact Information
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.8 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public abstract class EBContactBean implements EntityBean, IContact {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_CONTACT;

	private static final String[] EXCLUDE_METHOD = new String[] { "getContactID", "getLEID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBContactBean() {
	}

	// ************* Non-persistent methods ***********
	/**
	 * Get the contact ID
	 * 
	 * @return long
	 */
	public long getContactID() {
		if (null != getContactPK()) {
			return getContactPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

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
	 * Set the contact ID
	 * 
	 * @param value is of type long
	 */
	public void setContactID(long value) {
		setContactPK(new Long(value));
	}

	/**
	 * Set Legal ID
	 * 
	 * @param value is of type long
	 */
	public void setLEID(long value) {
		setLEFK(new Long(value));
	}

	// ************** Abstract methods ************
	/**
	 * Get the contact PK
	 * 
	 * @return Long
	 */
	public abstract Long getContactPK();

	/**
	 * Get Legal FK
	 * 
	 * @return Long
	 */
	public abstract Long getLEFK();

	/**
	 * Set the contact PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setContactPK(Long value);

	/**
	 * Set Legal FK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setLEFK(Long value);

	// *****************************************************
	/**
	 * Create a Contact Information
	 * 
	 * @param legalID the customer ID of type long
	 * @param value is the IContact object
	 * @return Long the contact ID primary key
	 * @throws CreateException on error
	 */
	public Long ejbCreate(long legalID, IContact value) throws CreateException {
		if (null == value) {
			throw new CreateException("IContact is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));
			/*
			 * long pk =com.integrosys.cms.app.common.constant.ICMSConstant.
			 * LONG_INVALID_VALUE; if(value.getContactID() ==
			 * com.integrosys.cms.
			 * app.common.constant.ICMSConstant.LONG_INVALID_VALUE) { pk =
			 * Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME,
			 * true)); } else { pk = value.getContactID(); }
			 */
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			// setLEID(legalID); //to be set by cmr
			setContactID(pk);

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
	public void ejbPostCreate(long legalID, IContact value) {
		// do nothing
	}

	/**
	 * Return the OB representation of this object
	 * 
	 * @return IContact
	 */
	public IContact getValue() {
		OBContact value = new OBContact();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Persist a contact information
	 * 
	 * @param value is of type IContact
	 */
	public void setValue(IContact value) {
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

    public abstract String getContactReference();

    public abstract String getContactType();

    public abstract String getAttentionParty();

    public abstract String getAddressLine1();

    public abstract String getAddressLine2();
    
    public abstract String getAddressLine3();
    
    public abstract String getAddressLine4();
    
    public abstract String getAddressLine5();    

    public abstract String getCity();

    public abstract String getState();

    public abstract String getPostalCode();

    public abstract String getCountryCode();

    public abstract String getFaxNumber();

    public abstract String getEmailAddress();

    public abstract String getTelephoneNumer();

    public abstract String getTelex();
    
    public abstract String getRegion();

    public abstract void setContactReference(String value);

    public abstract void setContactType(String value);

    public abstract void setAttentionParty(String value);

    public abstract void setAddressLine1(String value);

    public abstract void setAddressLine2(String value);
    
    public abstract void setAddressLine3(String value);
    
    public abstract void setAddressLine4(String value);
    
    public abstract void setAddressLine5(String value);

    public abstract void setCity(String value);

    public abstract void setState(String value);

    public abstract void setPostalCode(String value);

    public abstract void setCountryCode(String value);

    public abstract void setFaxNumber(String value);

    public abstract void setEmailAddress(String value);

    public abstract void setTelephoneNumer(String value);

    public abstract void setTelex(String value);
    
    public abstract  String getStdCodeTelNo();

	public abstract  void setStdCodeTelNo(String stdCodeTelNo);

	public abstract  String getStdCodeTelex() ;

	public abstract  void setStdCodeTelex(String stdCodeTelex);
    
    public abstract void setRegion(String value);
}