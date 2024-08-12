/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.property.marketfactor;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This entity bean represents the persistence for MF Checklist Item.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBMFChecklistItemBean implements EntityBean, IMFChecklistItem {

	private static final String[] EXCLUDE_METHOD = new String[] { "getMFItemID", "getMFParentID", "getMFItemRef" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBMFChecklistItemBean() {
	}

	// ************ Non-persistence method *************

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklistItem#getMFItemID
	 */
	public long getMFItemID() {
		if (null != getMFChecklistItemIDPK()) {
			return getMFChecklistItemIDPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklistItem#setMFItemID
	 */
	public void setMFItemID(long value) {
		setMFChecklistItemIDPK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklistItem#getMFParentID
	 */
	public long getMFParentID() {
		if (null != getMFChecklistIDFK()) {
			return getMFChecklistIDFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklistItem#setMFParentID
	 */
	public void setMFParentID(long value) {
		setMFChecklistIDFK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklistItem#getMFItemRef
	 */
	public long getMFItemRef() {
		if (null != getEBMFChecklistItemRef()) {
			return getEBMFChecklistItemRef().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklistItem#setMFItemRef
	 */
	public void setMFItemRef(long value) {
		setEBMFChecklistItemRef(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklistItem#getWeightPercentage
	 */
	public double getWeightPercentage() {
		if (getEBWeightPercentage() != null) {
			return getEBWeightPercentage().doubleValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.DOUBLE_INVALID_VALUE;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklistItem#setWeightPercentage
	 */
	public void setWeightPercentage(double value) {
		setEBWeightPercentage(new Double(value));
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklistItem#getValuerAssignFactor
	 */
	public double getValuerAssignFactor() {
		if (getEBValuerAssignFactor() != null) {
			return getEBValuerAssignFactor().doubleValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.DOUBLE_INVALID_VALUE;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklistItem#setValuerAssignFactor
	 */
	public void setValuerAssignFactor(double value) {
		setEBValuerAssignFactor(new Double(value));
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklistItem#getWeightScore
	 */
	public double getWeightScore() {
		if (getEBWeightScore() != null) {
			return getEBWeightScore().doubleValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.DOUBLE_INVALID_VALUE;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklistItem#setWeightScore
	 */
	public void setWeightScore(double value) {
		setEBWeightScore(new Double(value));
	}

	// ********************** Abstract Methods **********************

	// Getters
	public abstract Long getMFChecklistItemIDPK();

	public abstract Long getMFChecklistIDFK();

	public abstract String getFactorDescription();

	public abstract Double getEBWeightPercentage();

	public abstract Double getEBValuerAssignFactor();

	public abstract Double getEBWeightScore();

	public abstract Long getEBMFChecklistItemRef();

	// Setters
	public abstract void setMFChecklistItemIDPK(Long value);

	public abstract void setMFChecklistIDFK(Long value);

	public abstract void setFactorDescription(String value);

	public abstract void setEBWeightPercentage(Double value);

	public abstract void setEBValuerAssignFactor(Double value);

	public abstract void setEBWeightScore(Double value);

	public abstract void setEBMFChecklistItemRef(Long value);

	// ************************ ejbCreate methods ********************
	/**
	 * Get the sequence of primary key for this MF Checklist Item.
	 * 
	 * @return String
	 */
	protected String getPKSequenceName() {
		return ICMSConstant.SEQUENCE_MF_ITEM;
	}

	/**
	 * Create a MF Checklist Item
	 * 
	 * @param value is the IMFChecklistItem object
	 * @return Long the primary key
	 */
	public Long ejbCreate(IMFChecklistItem value) throws CreateException {
		if (null == value) {
			throw new CreateException("IMFChecklistItem is null!");
		}

		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(getPKSequenceName(), true));

			DefaultLogger.debug(this, "Creating checklist item with ID: " + pk);

			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setMFChecklistItemIDPK(new Long(pk));

			if (value.getMFItemRef() == ICMSConstant.LONG_INVALID_VALUE) {
				setEBMFChecklistItemRef(new Long(pk));
			}
			else {
				// else maintain this reference id.
				setEBMFChecklistItemRef(new Long(value.getMFItemRef()));
			}

			return new Long(pk);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}

	/**
	 * Post Create a MF Checklist Item
	 * 
	 * @param value is the IMFChecklistItem object
	 */
	public void ejbPostCreate(IMFChecklistItem value) throws CreateException {
		// do nothing
	}

	/**
	 * Method to get an object representation from persistance
	 * 
	 * @return IMFChecklistItem
	 */
	public IMFChecklistItem getValue() {
		OBMFChecklistItem value = new OBMFChecklistItem();
		AccessorUtil.copyValue(this, value);

		return value;
	}

	/**
	 * Method to set an object representation into persistance
	 * 
	 * @param value is of type IMFChecklistItem
	 * @throws CollateralException on error
	 */
	public void setValue(IMFChecklistItem value) throws CollateralException {
		if (null != value) {
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
		}
		else {
			throw new CollateralException("IMFChecklistItem is null!");
		}
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