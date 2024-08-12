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
 * This entity bean represents the persistence for MF Item.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBMFItemBean implements EntityBean, IMFItem {

	private static final String[] EXCLUDE_METHOD = new String[] { "getMFItemID", "getMFParentID", "getMFItemRef" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBMFItemBean() {
	}

	// ************ Non-persistence method *************

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem#getMFItemID
	 */
	public long getMFItemID() {
		if (null != getMFItemIDPK()) {
			return getMFItemIDPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem#setMFItemID
	 */
	public void setMFItemID(long value) {
		setMFItemIDPK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem#getMFParentID
	 */
	public long getMFParentID() {
		if (null != getMFTemplateIDFK()) {
			return getMFTemplateIDFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem#setMFParentID
	 */
	public void setMFParentID(long value) {
		setMFTemplateIDFK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem#getMFItemRef
	 */
	public long getMFItemRef() {
		if (null != getEBMFItemRef()) {
			return getEBMFItemRef().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem#setMFItemRef
	 */
	public void setMFItemRef(long value) {
		setEBMFItemRef(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem#getWeightPercentage
	 */
	public double getWeightPercentage() {
		if (getEBWeightPercentage() != null) {
			return getEBWeightPercentage().doubleValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.DOUBLE_INVALID_VALUE;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem#setWeightPercentage
	 */
	public void setWeightPercentage(double value) {
		setEBWeightPercentage(new Double(value));
	}

	// ********************** Abstract Methods **********************

	// Getters
	public abstract Long getMFItemIDPK();

	public abstract Long getMFTemplateIDFK();

	public abstract String getFactorDescription();

	public abstract Double getEBWeightPercentage();

	public abstract String getStatus();

	public abstract Long getEBMFItemRef();

	// Setters
	public abstract void setMFItemIDPK(Long value);

	public abstract void setMFTemplateIDFK(Long value);

	public abstract void setFactorDescription(String value);

	public abstract void setEBWeightPercentage(Double value);

	public abstract void setStatus(String value);

	public abstract void setEBMFItemRef(Long value);

	// ************************ ejbCreate methods ********************
	/**
	 * Get the sequence of primary key for this MF Item.
	 * 
	 * @return String
	 */
	protected String getPKSequenceName() {
		return ICMSConstant.SEQUENCE_MF_ITEM;
	}

	/**
	 * Create a MF Item
	 * 
	 * @param value is the IMFItem object
	 * @return Long the primary key
	 */
	public Long ejbCreate(IMFItem value) throws CreateException {
		if (null == value) {
			throw new CreateException("IMFItem is null!");
		}

		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(getPKSequenceName(), true));

			DefaultLogger.debug(this, "Creating item with ID: " + pk);

			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setMFItemIDPK(new Long(pk));

			if (value.getMFItemRef() == ICMSConstant.LONG_INVALID_VALUE) {
				setEBMFItemRef(new Long(pk));
			}
			else {
				// else maintain this reference id.
				setEBMFItemRef(new Long(value.getMFItemRef()));
			}
			setStatus(ICMSConstant.STATE_ACTIVE);
			return new Long(pk);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}

	/**
	 * Post Create a MF Item
	 * 
	 * @param value is the IMFItem object
	 */
	public void ejbPostCreate(IMFItem value) throws CreateException {
		// do nothing
	}

	/**
	 * Method to get an object representation from persistance
	 * 
	 * @return IMFItem
	 */
	public IMFItem getValue() {
		OBMFItem value = new OBMFItem();
		AccessorUtil.copyValue(this, value);

		return value;
	}

	/**
	 * Method to set an object representation into persistance
	 * 
	 * @param value is of type IMFItem
	 * @throws PropertyParametersException on error
	 */
	public void setValue(IMFItem value) throws PropertyParametersException {
		if (null != value) {
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
		}
		else {
			throw new PropertyParametersException("IMFItem is null!");
		}
	}

	/**
	 * Delete this MF Item.
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