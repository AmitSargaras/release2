/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

/**
 * This interface represents a MF Template Security SubType.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IMFTemplateSecSubType extends java.io.Serializable {

	/**
	 * Get Template Sub Type ID.
	 * 
	 * @return long
	 */
	public long getTemplateSubTypeID();

	/**
	 * Set Template Sub Type ID.
	 * 
	 * @param templateSubTypeID of type long
	 */
	public void setTemplateSubTypeID(long templateSubTypeID);

	/**
	 * Get MF Template ID.
	 * 
	 * @return long
	 */
	public long getMFTemplateID();

	/**
	 * Set MF Template ID.
	 * 
	 * @param mFTemplateID of type long
	 */
	public void setMFTemplateID(long mFTemplateID);

	/**
	 * Get Security SubType ID.
	 * 
	 * @return String
	 */
	public String getSecSubTypeID();

	/**
	 * Set Security SubType ID.
	 * 
	 * @param secSubTypeID of type String
	 */
	public void setSecSubTypeID(String secSubTypeID);

	/**
	 * Get Status.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set Status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

}