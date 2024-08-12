/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

/**
 * This interface represents a MF Item.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IMFItem extends ICommonMFItem {
	/**
	 * Get status
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set status
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

}
