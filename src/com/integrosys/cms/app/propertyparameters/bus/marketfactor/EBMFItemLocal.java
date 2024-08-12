/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import javax.ejb.EJBLocalObject;

import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersException;

/**
 * This is the local remote interface to the EBMFItem entity bean
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBMFItemLocal extends EJBLocalObject {

	/**
	 * Get an object representation from persistance
	 * 
	 * @return IMFItem
	 */
	public IMFItem getValue();

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type IMFItem
	 * @throws PropertyParametersException on errors
	 */
	public void setValue(IMFItem value) throws PropertyParametersException;

	/**
	 * Delete this MF Item.
	 */
	public void delete();

	/**
	 * Get the MF Item Reference ID
	 * 
	 * @return long
	 */
	public long getMFItemRef();

}