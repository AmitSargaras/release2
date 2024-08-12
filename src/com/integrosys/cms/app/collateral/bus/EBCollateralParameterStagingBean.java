/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralParameterStagingBean.java,v 1.1 2003/08/13 12:48:24 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

/**
 * Entity bean implementation for security parameter.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/13 12:48:24 $ Tag: $Name: $
 */
public abstract class EBCollateralParameterStagingBean extends EBCollateralParameterBean {
	/**
	 * Get DAO implementation for collateral parameter.
	 * 
	 * @return ICollateralParameterDAO
	 */
	protected ICollateralParameterDAO getCollateralParameterDAO() {
		return CollateralParameterDAOFactory.getStagingDAO();
	}
}
