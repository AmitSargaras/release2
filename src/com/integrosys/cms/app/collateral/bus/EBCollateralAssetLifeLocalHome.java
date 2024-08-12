/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local home interface for EBCollateralAssetLifeBean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBCollateralAssetLifeLocalHome extends EJBLocalHome {
	/**
	 * Called by the client to create an local EJB object.
	 * 
	 * @param assetLife of type ICollateralAssetLife
	 * @return local collateral assetLife ejb object
	 * @throws CreateException on error while creating the ejb
	 */
	public EBCollateralAssetLifeLocal create(ICollateralAssetLife assetLife) throws CreateException;

	/**
	 * Find the local ejb object by primary key, the collateral sub type code.
	 * 
	 * @param subTypeCode collateral sub type code
	 * @return local collateral assetLife ejb object
	 * @throws FinderException on error while finding the ejb
	 */
	public EBCollateralAssetLifeLocal findByPrimaryKey(String subTypeCode) throws FinderException;

	/**
	 * Find all collateral assetLife.
	 * 
	 * @return a collection of <code>EBCollateralAssetLife</code>s
	 * @throws FinderException on error finding collateral assetLife
	 */
	public Collection findAll() throws FinderException;

	/**
	 * Find security assetLife by its group id.
	 * 
	 * @param groupID group id
	 * @return a collection of <code>EBCollateralAssetLife</code>s
	 * @throws FinderException on error finding the assetLife
	 */
	public Collection findByGroupID(long groupID) throws FinderException;
}