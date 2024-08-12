/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Local interface to EBCollateralAssetLifeBean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBCollateralAssetLifeLocal extends EJBLocalObject {
	/**
	 * Get the collateral assetLife business object.
	 * 
	 * @return collateral assetLife object
	 */
	public ICollateralAssetLife getValue();

	/**
	 * Set the collateral assetLife to this entity.
	 * 
	 * @param assetLife is of type ICollateralAssetLife
	 * @throws VersionMismatchException if the collateral assetLife is invalid
	 */
	public void setValue(ICollateralAssetLife assetLife) throws VersionMismatchException;

	/**
	 * Set the life span value for security assetLife.
	 * 
	 * @param assetLife of type ICollateralAssetLife
	 * @throws VersionMismatchException if the assetLife is invalid
	 */
	public void setLifeSpanValue(ICollateralAssetLife assetLife) throws VersionMismatchException;
}