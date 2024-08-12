/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Remote interface to EBCollateralAssetLifeBean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBCollateralAssetLife extends EJBObject {
	/**
	 * Get the collateral asset life business object.
	 * 
	 * @return collateral asset life object
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralAssetLife getValue() throws RemoteException;

	/**
	 * Set the collateral asset life to this entity.
	 * 
	 * @param assetLife is of type ICollateralAssetLife
	 * @throws VersionMismatchException if the collateral asset life is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setValue(ICollateralAssetLife assetLife) throws VersionMismatchException, RemoteException;

	/**
	 * Set the life span value for security asset life.
	 * 
	 * @param assetLife of type ICollateralAssetLife
	 * @throws VersionMismatchException if the asset life is invalid
	 * @throws RemoteException on error during remote method call
	 */
	public void setLifeSpanValue(ICollateralAssetLife assetLife) throws VersionMismatchException, RemoteException;
}