/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.property.marketfactor;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Remote home interface for EBMFChecklist.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBMFChecklistHome extends EJBHome {
	/**
	 * Called by the client to create a MF Checklist ejb object.
	 * 
	 * @param mFChecklist of type IMFChecklist
	 * @return EBMFChecklist
	 * @throws CreateException on error while creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBMFChecklist create(IMFChecklist mFChecklist) throws CreateException, RemoteException;

	/**
	 * Find the MF Checklist ejb object by primary key, the MF Checklist ID.
	 * 
	 * @param mFChecklistIDPK MF Checklist ID of type Long
	 * @return EBMFChecklist
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBMFChecklist findByPrimaryKey(Long mFChecklistIDPK) throws FinderException, RemoteException;

	/**
	 * Find the MF Checklist ejb object by collateral ID.
	 * 
	 * @param collateralID collateral ID ID of type long
	 * @return EBMFChecklist
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBMFChecklist findByCollateralID(long collateralID) throws FinderException, RemoteException;

}