/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import com.integrosys.cms.app.liquidation.proxy.ILiquidationProxy;
import com.integrosys.base.techinfra.context.BeanHouse;

/**
 * This factory class will load ILiquidationDAO implementations.
 * 
 * @author $Author: Lini $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class LiquidationDAOFactory {
	/**
	 * Create a default liquidation DAO implementation.
	 * 
	 * @return ILiquidationDAO
	 */
	public static LiquidationDAO getDAO() {
//		return new LiquidationDAO();
         return (LiquidationDAO) BeanHouse.get("liquidationDAO");
    }

	/**
	 * Create a default liquidation DAO implementation for staging.
	 * 
	 * @return ILiquidationDAO
	 */
	public static LiquidationDAO getStagingDAO() {
//		return new LiquidationStagingDAO();
         return (LiquidationDAO) BeanHouse.get("stagingLiquidationDAO");
    }

}