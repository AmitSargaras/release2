/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import java.io.Serializable;

/**
 * This interface represents a common values for Marketability Factor item.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface ICommonMFItem extends Serializable {
	/**
	 * Get MF Item ID.
	 * 
	 * @return long
	 */
	public long getMFItemID();

	/**
	 * Set MF Item ID.
	 * 
	 * @param mFItemID of type long
	 */
	public void setMFItemID(long mFItemID);

	/**
	 * Get Parent ID for this item.
	 * 
	 * @return long
	 */
	public long getMFParentID();

	/**
	 * Set Parent ID for this item.
	 * 
	 * @param mFTemplateID of type long
	 */
	public void setMFParentID(long mFParentID);

	/**
	 * Get factor description.
	 * 
	 * @return long
	 */
	public String getFactorDescription();

	/**
	 * Set factor description.
	 * 
	 * @param factorDescription of type String
	 */
	public void setFactorDescription(String factorDescription);

	/**
	 * Get weight percentage
	 * 
	 * @return double
	 */
	public double getWeightPercentage();

	/**
	 * Set weight percentage
	 * 
	 * @param weightPercentage of type double
	 */
	public void setWeightPercentage(double weightPercentage);

	/**
	 * Get the MF Item Reference ID.
	 * 
	 * @return long
	 */
	public long getMFItemRef();

	/**
	 * Set the MF Item Reference ID.
	 * 
	 * @param mFItemRef is of type long
	 */
	public void setMFItemRef(long mFItemRef);

}
