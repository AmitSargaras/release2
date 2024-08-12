/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import javax.ejb.EJBLocalObject;

import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersException;

/**
 * This is the local remote interface to the EBMFTemplateSecSubType entity bean
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBMFTemplateSecSubTypeLocal extends EJBLocalObject {

	/**
	 * Get an object representation from persistance
	 * 
	 * @return IMFTemplateSecSubType
	 */
	public IMFTemplateSecSubType getValue();

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type IMFTemplateSecSubType
	 * @throws PropertyParametersException on errors
	 */
	public void setValue(IMFTemplateSecSubType value) throws PropertyParametersException;

	/**
	 * Delete this MF Template Security SubType.
	 */
	public void delete();

}