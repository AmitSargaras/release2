/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersException;

/**
 * This entity bean represents the persistence for MF Template Security SubType.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBMFTemplateSecSubTypeBean implements EntityBean, IMFTemplateSecSubType {

	private static final String[] EXCLUDE_METHOD = new String[] { "getTemplateSubTypeID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBMFTemplateSecSubTypeBean() {
	}

	// ************ Non-persistence method *************

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplateSecSubType#getTemplateSubTypeID
	 */
	public long getTemplateSubTypeID() {
		if (null != getTemplateSubTypeIDPK()) {
			return getTemplateSubTypeIDPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplateSecSubType#setTemplateSubTypeID
	 */
	public void setTemplateSubTypeID(long value) {
		setTemplateSubTypeIDPK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplateSecSubType#getMFTemplateID
	 */
	public long getMFTemplateID() {
		if (null != getMFTemplateIDFK()) {
			return getMFTemplateIDFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplateSecSubType#setMFTemplateID
	 */
	public void setMFTemplateID(long value) {
		setMFTemplateIDFK(new Long(value));
	}

	// ********************** Abstract Methods **********************

	// Getters
	public abstract Long getTemplateSubTypeIDPK();

	public abstract Long getMFTemplateIDFK();

	public abstract String getSecSubTypeID();

	public abstract String getStatus();

	// Setters
	public abstract void setTemplateSubTypeIDPK(Long value);

	public abstract void setMFTemplateIDFK(Long value);

	public abstract void setSecSubTypeID(String value);

	public abstract void setStatus(String value);

	// ************************ ejbCreate methods ********************

	/**
	 * Create a MF Template Security SubType
	 * 
	 * @param value is the IMFTemplateSecSubType object
	 * @return Long the primary key
	 */
	public Long ejbCreate(IMFTemplateSecSubType value) throws CreateException {
		if (null == value) {
			throw new CreateException("IMFTemplateSecSubType is null!");
		}

		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(getPKSequenceName(), true));
			DefaultLogger.debug(this, "Creating Security sub type with ID: " + pk);

			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);

			setTemplateSubTypeIDPK(new Long(pk));
			setStatus(ICMSConstant.STATE_ACTIVE);

			return new Long(pk);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}

	/**
	 * Post Create a MF Template Security SubType
	 * 
	 * @param value is the IMFTemplateSecSubType object
	 */
	public void ejbPostCreate(IMFTemplateSecSubType value) throws CreateException {
		// do nothing
	}

	/**
	 * Get the sequence of primary key for this MF Template Security SubType.
	 * 
	 * @return String
	 */
	protected String getPKSequenceName() {
		return ICMSConstant.SEQUENCE_MF_SEC_SUBTYPE;
	}

	/**
	 * Method to get an object representation from persistance
	 * 
	 * @return IMFTemplateSecSubType
	 */
	public IMFTemplateSecSubType getValue() {
		OBMFTemplateSecSubType value = new OBMFTemplateSecSubType();
		AccessorUtil.copyValue(this, value);

		return value;
	}

	/**
	 * Method to set an object representation into persistance
	 * 
	 * @param value is of type IMFTemplateSecSubType
	 * @throws PropertyParametersException on error
	 */
	public void setValue(IMFTemplateSecSubType value) throws PropertyParametersException {
		if (null != value) {
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
		}
		else {
			throw new PropertyParametersException("IMFTemplateSecSubType is null!");
		}
	}

	/**
	 * Delete this MF Template Security SubType.
	 */
	public void delete() {
		setStatus(ICMSConstant.STATE_DELETED);
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
	public void ejbRemove() throws RemoveException, EJBException {
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
}