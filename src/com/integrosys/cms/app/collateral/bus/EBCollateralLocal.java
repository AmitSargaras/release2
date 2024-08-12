/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralLocal.java,v 1.4 2005/07/18 09:51:19 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.cms.app.sharesecurity.bus.IShareSecurity;

/**
 * Entity bean local interface to EBCollateralBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/07/18 09:51:19 $ Tag: $Name: $
 */
public interface EBCollateralLocal extends EJBLocalObject {
	/**
	 * Get the collateral business object.
	 * 
	 * @return collateral based on its type/subtype
	 * @throws CollateralException on error getting the value
	 */
	public ICollateral getValue() throws CollateralException;

	/**
	 * Get the collateral business object. getValue (true) will return the same
	 * object as getValue(). getValue(false) will return collateral without
	 * child references.
	 * 
	 * @param withRef true if with child references, otherwise false
	 * @return collateral based on its type/subtype
	 * @throws CollateralException on error getting the value
	 */
	public ICollateral getValue(boolean withRef) throws CollateralException;

	/**
	 * Get the collateral business object.
	 * 
	 * @param collateral of type ICollateral
	 * @return collateral
	 */
	public ICollateral getValue(ICollateral collateral);

	/**
	 * Set the collateral business object.
	 * 
	 * @param collateral is of type ICollateral
	 * @throws VersionMismatchException if the collateral version is different
	 *         from backend
	 */
	public void setValue(ICollateral collateral) throws VersionMismatchException;

	/**
	 * Create references for the collateral.
	 * 
	 * @param collateral of type ICollateral
	 * @param versionTime of type long
	 * @throws VersionMismatchException if the collateral version is different
	 *         from backend
	 */
	public void createDependants(ICollateral collateral, long versionTime, boolean createPledgor)
			throws VersionMismatchException;

	/**
	 * Get version of this entity.
	 * 
	 * @return version time
	 */
	public long getVersionTime();

	/**
	 * Set new version for this entity.
	 * 
	 * @return newly generated version time
	 * @throws VersionMismatchException if the collateral version is different
	 *         from backend
	 */
	public long updateVersionTime(ICollateral collateral) throws VersionMismatchException;

	/**
	 * Revaluate this collateral. It will create new valuation for the
	 * collateral and the previous valuation will become history.
	 * 
	 * @param newValuation re-valuation value
	 * @return newly created valuation
	 */
	public IValuation createValuation(IValuation newValuation);

	public IShareSecurity createShareSecurity(IShareSecurity sec) throws CollateralException;

	public ICollateralSubType getCollateralSubType();
}