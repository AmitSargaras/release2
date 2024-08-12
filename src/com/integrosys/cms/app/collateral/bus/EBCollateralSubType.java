/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralSubType.java,v 1.4 2003/08/15 06:00:31 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Remote interface to EBCollateralSubTypeBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/15 06:00:31 $ Tag: $Name: $
 */
public interface EBCollateralSubType extends EJBObject {
	/**
	 * Get the collateral type business object.
	 * 
	 * @return collateral type object
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralSubType getValue() throws RemoteException;

	/**
	 * Set the collateral type to this entity.
	 * 
	 * @param colType is of type ICollateralSubType
	 * @throws VersionMismatchException if the collateral subtype is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setValue(ICollateralSubType colType) throws VersionMismatchException, RemoteException;

	/**
	 * Set the max value, standardised approach, foundation IRB, advanced IRB
	 * for security subtype.
	 * 
	 * @param colType of type ICollateralSubType
	 * @throws VersionMismatchException if the subtype is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setSubTypeValue(ICollateralSubType colType) throws VersionMismatchException, RemoteException;

}