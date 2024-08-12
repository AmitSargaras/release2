/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralDetailLocal.java,v 1.1 2003/07/30 02:35:22 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBLocalObject;

/**
 * Entity bean local interface to the details of a collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/30 02:35:22 $ Tag: $Name: $
 */
public interface EBCollateralDetailLocal extends EJBLocalObject {
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
	 */
	public void setValue(ICollateral collateral);
}