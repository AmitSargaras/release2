/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Local interface to EBRecoveryBean.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBRecoveryLocal extends EJBLocalObject {
	/**
	 * Get the Recovery business object.
	 * 
	 * @return Recovery object
	 */
	public IRecovery getValue();

	/**
	 * Set the to this entity.
	 * 
	 * @param recovery is of type IRecovery
	 * @throws com.integrosys.base.businfra.common.exception.VersionMismatchException
	 *         if the NPL Info is invalid
	 */
	public void setValue(IRecovery recovery) throws VersionMismatchException;

	public void setStatus(String status);

}