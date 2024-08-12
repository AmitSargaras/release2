/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/proxy/CollateralProxyFactory.java,v 1.1 2003/06/11 09:11:05 lyng Exp $
 */
package com.integrosys.cms.app.collateral.proxy;

import com.integrosys.base.techinfra.context.BeanHouse;

/**
 * This factory creates ICollateralProxy object.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/11 09:11:05 $ Tag: $Name: $
 */
public final class CollateralProxyFactory {

	/**
	 * Default Constructor
	 */
	public CollateralProxyFactory() {
	}

	/**
	 * Creates an ICollateralProxy.
	 * 
	 * @return ICollateralProxy
	 */
	public static ICollateralProxy getProxy() {
		return (ICollateralProxy) BeanHouse.get("collateralProxy");
	}
}