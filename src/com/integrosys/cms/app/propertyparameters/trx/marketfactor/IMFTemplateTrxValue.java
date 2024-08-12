/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.trx.marketfactor;

import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Contains actual MF Template and staging MF Template for transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IMFTemplateTrxValue extends ICMSTrxValue {
	/**
	 * Gets the actual MF Template objects in this transaction.
	 * 
	 * @return The actual IMFTemplate
	 */
	public IMFTemplate getMFTemplate();

	/**
	 * Sets the actual MF Template objects for this transaction.
	 * 
	 * @param value the actual IMFTemplate
	 */
	public void setMFTemplate(IMFTemplate value);

	/**
	 * Gets the staging MF Template objects in this transaction.
	 * 
	 * @return the staging IMFTemplate
	 */
	public IMFTemplate getStagingMFTemplate();

	/**
	 * Sets the staging MF Template objects for this transaction.
	 * 
	 * @param value the staging IMFTemplate
	 */
	public void setStagingMFTemplate(IMFTemplate value);

}
