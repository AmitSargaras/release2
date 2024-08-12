/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateral.java,v 1.15 2005/07/18 09:51:19 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.cms.app.sharesecurity.bus.IShareSecurity;

/**
 * Entity bean remote interface to EBCollateralBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2005/07/18 09:51:19 $ Tag: $Name: $
 */
public interface EBCollateral extends EJBObject {
	/**
	 * Get the collateral business object.
	 * 
	 * @return collateral based on its type/subtype
	 * @throws CollateralException on error getting the value
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateral getValue() throws RemoteException, CollateralException;

	/**
	 * Get the collateral business object. getValue (true) will return the same
	 * object as getValue(). getValue(false) will return collateral without
	 * child references.
	 * 
	 * @param withRef true if with child references, otherwise false
	 * @return collateral based on its type/subtype
	 * @throws CollateralException on error getting the value
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateral getValue(boolean withRef) throws RemoteException, CollateralException;

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
	 * @throws VersionMismatchException if the collateral version is different
	 *         from backend
	 * @throws RemoteException on error during remote method call
	 */
	public void setValue(ICollateral collateral) throws VersionMismatchException, RemoteException;

	/**
	 * Create references for the collateral.
	 * 
	 * @param collateral of type ICollateral
	 * @param versionTime of type long
	 * @throws VersionMismatchException if the collateral version is different
	 *         from backend
	 * @throws RemoteException on error during remote method call
	 */
	public void createDependants(ICollateral collateral, long versionTime, boolean createPledgor)
			throws RemoteException, VersionMismatchException;

	/**
	 * Get version of this entity.
	 * 
	 * @return version time
	 * @throws RemoteException on error during remote method call
	 */
	public long getVersionTime() throws RemoteException;

	/**
	 * Set new version for this entity.
	 * 
	 * @return newly generated version time
	 * @throws RemoteException on any unexpected error during remote method call
	 * @throws VersionMismatchException if the collateral version is different
	 *         from backend
	 */
	public long updateVersionTime(ICollateral collateral) throws RemoteException, VersionMismatchException;

	/**
	 * Revaluate this collateral. It will create new valuation for the
	 * collateral and the previous valuation will become history.
	 * 
	 * @param newValuation re-valuation value
	 * @return newly created valuation
	 * @throws RemoteException on error during remote method call
	 */
	public IValuation createValuation(IValuation newValuation) throws RemoteException;

	public IShareSecurity createShareSecurity(IShareSecurity sec) throws RemoteException, CollateralException;

	/**
	 * Create the limit charge map reference if charge details is found and it
	 * is not link to the limit when this collateral is link to limit, Remove
	 * the limit charge map reference if charge details is found and it is link
	 * to the limit when this collateral is unlink from limit
	 * 
	 * @param lmtChargeMap limit charge map details of type ILimitChargeMap
	 * @param isDelete true if to remove limit charge map reference, else false
	 *        to create new reference
	 * @throws CollateralException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public void setLimitChargeMapRef(ILimitChargeMap lmtChargeMap, boolean isDelete) throws RemoteException;

	/**
	 * Gets the collateral limit map details by cms limit ID of this collateral
	 * 
	 * @param cmsLimitID cms limit ID
	 * @return ICollateralLimitMap
	 * @throws CollateralException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralLimitMap getCollateralLimitMap(long cmsLimitID) throws RemoteException;

}