/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/CollateralParameterDAOFactory.java,v 1.1 2003/08/13 12:50:32 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

/**
 * This factory class will load ICollateralParameterDAO implementations.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/13 12:50:32 $ Tag: $Name: $
 */
public class CollateralParameterDAOFactory {
	/**
	 * Create a default security parameter DAO implementation.
	 * 
	 * @return ICollateralParameterDAO
	 */
	public static ICollateralParameterDAO getDAO() {
		return new CollateralParameterDAO();
	}

	/**
	 * Create a default security parameter DAO implementation.
	 * 
	 * @return ICollateralParameterDAO
	 */
	public static ICollateralParameterDAO getStagingDAO() {
		return new CollateralParameterStagingDAO();
	}
}