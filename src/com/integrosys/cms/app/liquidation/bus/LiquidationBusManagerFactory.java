/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

/**
 * This factory creates ILiquidationBusManager.
 * 
 * @author $Author: Lini $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class LiquidationBusManagerFactory {
	/**
	 * Default Constructor
	 */
	public LiquidationBusManagerFactory() {
	}

	/**
	 * Create the liquidation business manager.
	 * 
	 * @return liquidation business manager
	 */
	public static ILiquidationBusManager getActualLiquidationBusManager() {
		return new LiquidationBusManagerImpl();
	}

	/**
	 * Create the stage liquidation business manager.
	 * 
	 * @return stage liquidation business manager
	 */
	public static ILiquidationBusManager getStagingLiquidationBusManager() {
		return new LiquidationBusManagerStagingImpl();
	}

}