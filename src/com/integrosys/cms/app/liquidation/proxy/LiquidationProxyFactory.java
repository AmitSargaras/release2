/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.proxy;

import com.integrosys.base.techinfra.context.BeanHouse;

/**
 * This factory creates ILiquidationProxy object.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class LiquidationProxyFactory {
	/**
	 * Default Constructor
	 */
	public LiquidationProxyFactory() {
	}

	/**
	 * Creates an ILiquidationProxy.
	 * 
	 * @return ILiquidationProxy
	 */
	public static ILiquidationProxy getProxy() {
//		return new LiquidationProxyImpl();
        return (ILiquidationProxy) BeanHouse.get("liquidationProxy");
    }
}