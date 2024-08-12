/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralParameter.java,v 1.2 2003/08/15 10:16:39 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Entity bean remote interface for security parameter.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/15 10:16:39 $ Tag: $Name: $
 */
public interface EBCollateralParameter extends EJBObject {
	/**
	 * Gets the business object of security parameter.
	 * 
	 * @return security parameter
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralParameter getValue() throws RemoteException;

	/**
	 * Sets the business object of security parameter.
	 * 
	 * @param colParam of type ICollateralParameter
	 * @throws VersionMismatchException if the security parameter's version is
	 *         invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setValue(ICollateralParameter colParam) throws VersionMismatchException, RemoteException;
}
