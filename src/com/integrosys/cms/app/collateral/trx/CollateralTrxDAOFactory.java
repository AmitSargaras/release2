/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/CollateralTrxDAOFactory.java,v 1.1 2003/07/15 08:46:39 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import com.integrosys.base.techinfra.context.BeanHouse;

/**
 * This factory class will load ICollateralTrxDAO implementations.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/15 08:46:39 $ Tag: $Name: $
 */
public class CollateralTrxDAOFactory {
	/**
	 * Create a default collateral transaction DAO implementation.
	 * 
	 * @return ICollateralTrxDAO
	 */
	public static ICollateralTrxDAO getDAO() {
		return (ICollateralTrxDAO) BeanHouse.get("collateralTrxJdbcDao");
	}
}