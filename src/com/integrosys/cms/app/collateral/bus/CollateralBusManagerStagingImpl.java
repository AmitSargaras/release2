/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/CollateralBusManagerStagingImpl.java,v 1.2 2003/07/02 09:50:04 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This class facades the ICollateralManager implementation by delegating the
 * handling of requests to an ejb session bean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/02 09:50:04 $ Tag: $Name: $
 */
public class CollateralBusManagerStagingImpl extends CollateralBusManagerImpl {
	/**
	 * Default constructor.
	 */
	public CollateralBusManagerStagingImpl() {
		super();
	}

	/**
	 * helper method to get an ejb object to collateral business manager session
	 * bean.
	 * 
	 * @return collateral manager ejb object
	 * @throws CollateralException on errors encountered
	 */
	protected SBCollateralBusManager getBusManager() throws CollateralException {
		SBCollateralBusManager theEjb = (SBCollateralBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_COLLATERAL_MGR_STAGING_JNDI, SBCollateralBusManagerHome.class.getName());

		if (theEjb == null) {
			throw new CollateralException("SBCollateralBusManager for Staging is null!");
		}

		return theEjb;
	}
}