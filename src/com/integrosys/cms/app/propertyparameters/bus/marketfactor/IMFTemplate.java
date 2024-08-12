/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

/**
 * This interface represents a MF Template.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IMFTemplate extends ICommonMFTemplate {

	/**
	 * Get MF Template Status.
	 * 
	 * @return String
	 */
	public String getMFTemplateStatus();

	/**
	 * Set MF Template Status.
	 * 
	 * @param mFTemplateStatus of type String
	 */
	public void setMFTemplateStatus(String mFTemplateStatus);

	/**
	 * Get Security Type ID.
	 * 
	 * @return String
	 */
	public String getSecurityTypeID();

	/**
	 * Set Security Type ID.
	 * 
	 * @param secTypeID of type String
	 */
	public void setSecurityTypeID(String secTypeID);

	/**
	 * Get list of Security Sub Type.
	 * 
	 * @return array of IMFTemplateSecSubType
	 */
	public IMFTemplateSecSubType[] getSecuritySubTypeList();

	/**
	 * Set list of Security Sub Type.
	 * 
	 * @param secSubTypeList of type array of IMFTemplateSecSubType
	 */
	public void setSecuritySubTypeList(IMFTemplateSecSubType[] secSubTypeList);

	/**
	 * Get list of MF Item.
	 * 
	 * @return array of IMFItem
	 */
	public IMFItem[] getMFItemList();

	/**
	 * Set list of MF Item.
	 * 
	 * @param mFItemList of type array of IMFItem
	 */
	public void setMFItemList(IMFItem[] mFItemList);

}
