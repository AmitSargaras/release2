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
public abstract class EBCriFacBean implements EntityBean, ICriFac {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_CONTACT;

	private static final String[] EXCLUDE_METHOD = new String[] { "getCriFacID", "getLEID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBCriFacBean() {
	}

	// ************* Non-persistent methods ***********
	/**
	 * Get the contact ID
	 * 
	 * @return long
	 */
	public long getCriFacID() {
		if (null != getCriFacPK()) {
			return getCriFacPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	
	public long getLEID() {
		if (null != getCmsLeMainProfileIdFK()) {
			return getCmsLeMainProfileIdFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}
	
	public void setLEID(long value) {
		setCmsLeMainProfileIdFK(new Long(value));
	}
	
	public abstract Long getCmsLeMainProfileIdFK();
	
	public abstract void setCmsLeMainProfileIdFK(Long value);
	/**
	 * Get Legal ID
	 * 
	 * @return long
	 */
	

	/**
	 * Set the contact ID
	 * 
	 * @param value is of type long
	 */
	public void setCriFacID(long value) {
		setCriFacPK(new Long(value));
	}

	/**
	 * Set Legal ID
	 * 
	 * @param value is of type long
	 */
	
	// ************** Abstract methods ************
	/**
	 * Get the contact PK
	 * 
	 * @return Long
	 */
	public abstract Long getCriFacPK();

	/**
	 * Get Legal FK
	 * 
	 * @return Long
	 */
	

	/**
	 * Set the contact PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setCriFacPK(Long value);

	/**
	 * Set Legal FK
	 * 
	 * @param value is of type Long
	 */


	// *****************************************************
	/**
	 * Create a Contact Information
	 * 
	 * @param legalID the customer ID of type long
	 * @param value is the IContact object
	 * @return Long the contact ID primary key
	 * @throws CreateException on error
	 */
	public Long ejbCreate(ICriFac value) throws CreateException {
		if (null == value) {
			throw new CreateException("ICriFac is null!");
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
			setCriFacID(pk);

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
	public void ejbPostCreate(ICriFac value) {
		// do nothing
	}

	/**
	 * Return the OB representation of this object
	 * 
	 * @return IContact
	 */
	public ICriFac getValue() {
		OBCriFac value = new OBCriFac();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Persist a contact information
	 * 
	 * @param value is of type IContact
	 */
	public void setValue(ICriFac value) {
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

	public abstract  String getFacilityFor();

	public abstract  void setFacilityFor(String facilityFor);
	
	public abstract  String getFacilityName();

	public abstract  void setFacilityName(String facilityName);

	public abstract  String getFacilityAmount();

	public abstract  void setFacilityAmount(String facilityAmount);
	
	public abstract  String getLineNo();

	public abstract  void setLineNo(String lineNo);	
	
	public abstract  String getSerialNo();

	public abstract  void setSerialNo(String serialNo);	
	
	public abstract  String getEstateType();

	public abstract  void setEstateType(String estateType);	
	
	public abstract  String getCommRealEstateType();

	public abstract  void setCommRealEstateType(String commRealEstateType);	
	
	public abstract  String getPrioritySector();

	public abstract  void setPrioritySector(String prioritySector);	
	            
}