/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.property.marketfactor;

import javax.ejb.EJBLocalObject;

import com.integrosys.cms.app.collateral.bus.CollateralException;

/**
 * This is the local remote interface to the EBMFChecklistItem entity bean
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBMFChecklistItemLocal extends EJBLocalObject {

	/**
	 * Get an object representation from persistance
	 * 
	 * @return IMFChecklistItem
	 */
	public IMFChecklistItem getValue();

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type IMFChecklistItem
	 * @throws CollateralException on errors
	 */
	public void setValue(IMFChecklistItem value) throws CollateralException;

	/**
	 * Get the MF Item Reference ID
	 * 
	 * @return long
	 */
	public long getMFItemRef();

}