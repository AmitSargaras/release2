/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Local interface to EBNPLInfoBean.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBNPLInfoLocal extends EJBLocalObject {
	/**
	 * Get the NPL Info business object.
	 * 
	 * @return NPL Info object
	 */
	public INPLInfo getValue();

	/**
	 * Set the NPL Info to this entity.
	 * 
	 * @param nplInfo is of type INPLInfo
	 * @throws com.integrosys.base.businfra.common.exception.VersionMismatchException
	 *         if the NPL Info is invalid
	 */
	public void setValue(INPLInfo nplInfo) throws VersionMismatchException;

}