/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Remote home interface for EBCollateralAssetLife.
 * 
 * @author $Author: $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBCollateralAssetLifeHome extends EJBHome {
	/**
	 * Called by the client to create an local EJB object.
	 * 
	 * @param assetLife of type ICollateralAssetLife
	 * @return collateral assetLife ejb object
	 * @throws CreateException on error while creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBCollateralAssetLife create(ICollateralAssetLife assetLife) throws CreateException, RemoteException;

	/**
	 * Find the local ejb object by primary key, the collateral sub type code.
	 * 
	 * @param subTypeCode collateral sub type code
	 * @return local collateral subtype ejb object
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBCollateralAssetLife findByPrimaryKey(String subTypeCode) throws FinderException, RemoteException;

	/**
	 * Find all collateral assetlifes.
	 * 
	 * @return a collection of <code>EBCollateralAssetLife</code>s
	 * @throws FinderException on error finding collateral assetlifes
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findAll() throws FinderException, RemoteException;

	/**
	 * Find security assetlife its group id.
	 * 
	 * @param groupID group id
	 * @return a collection of <code>EBCollateralAssetLife</code>s
	 * @throws FinderException on error finding the assetlife
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findByGroupID(long groupID) throws FinderException, RemoteException;
}