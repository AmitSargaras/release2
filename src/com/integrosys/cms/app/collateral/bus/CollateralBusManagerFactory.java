/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/CollateralBusManagerFactory.java,v 1.2 2003/07/02 08:27:20 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

/**
 * This factory creates ICollateralBusManager.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/02 08:27:20 $ Tag: $Name: $
 */
public class CollateralBusManagerFactory {
	/**
	 * Default Constructor
	 */
	public CollateralBusManagerFactory() {
	}

	/**
	 * Create the collateral business manager.
	 * 
	 * @return collateral business manager
	 */
	public static ICollateralBusManager getActualCollateralBusManager() {
		return new CollateralBusManagerImpl();
	}

	public static ICollateralBusManager getStagingCollateralBusManager() {
		return new CollateralBusManagerStagingImpl();
	}

}