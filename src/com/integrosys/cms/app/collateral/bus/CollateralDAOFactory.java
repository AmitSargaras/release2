/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/CollateralDAOFactory.java,v 1.2 2003/08/14 13:46:14 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.techinfra.context.BeanHouse;

/**
 * This factory class will load ICollateralDAO implementations.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/14 13:46:14 $ Tag: $Name: $
 */
public class CollateralDAOFactory {
	/**
	 * Create a default collateral DAO implementation.
	 * 
	 * @return ICollateralDAO
	 */
	public static ICollateralDAO getDAO() {
		return (ICollateralDAO) BeanHouse.get("collateralDao");
	}

	/**
	 * Create a default collateral DAO implementation for staging.
	 * 
	 * @return ICollateralDAO
	 */
	public static ICollateralDAO getStagingDAO() {
		return (ICollateralDAO) BeanHouse.get("stagingCollateralDao");
	}
}