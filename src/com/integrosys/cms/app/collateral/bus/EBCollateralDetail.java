/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralDetail.java,v 1.4 2003/07/07 06:13:53 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Entity bean remote interface to the details of a collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/07/07 06:13:53 $ Tag: $Name: $
 */
public interface EBCollateralDetail extends EJBObject {
	/**
	 * Get the collateral business object.
	 * 
	 * @param collateral of type ICollateral
	 * @return collateral
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateral getValue(ICollateral collateral) throws RemoteException;

	/**
	 * Set the collateral business object.
	 * 
	 * @param collateral is of type ICollateral
	 * @throws RemoteException on error during remote method call
	 */
	public void setValue(ICollateral collateral) throws RemoteException;
}