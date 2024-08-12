/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This class facades the ILiquidationManager implementation by delegating the
 * handling of requests to an ejb session bean.
 * 
 * @author $Author: Lini $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class LiquidationBusManagerStagingImpl extends LiquidationBusManagerImpl {

	private static final long serialVersionUID = -4683088172029327475L;

	/**
	 * Default constructor.
	 */
	public LiquidationBusManagerStagingImpl() {
		super();
	}

	/**
	 * helper method to get an ejb object to liquidation business manager
	 * session bean.
	 * 
	 * @return liquidation manager ejb object
	 * @throws LiquidationException on errors encountered
	 */
	protected SBLiquidationBusManager getBusManager() throws LiquidationException {
		SBLiquidationBusManager theEjb = (SBLiquidationBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_LIQUIDATION_MGR_STAGING_JNDI, SBLiquidationBusManagerHome.class.getName());

		if (theEjb == null) {
			throw new LiquidationException("SBLiquidationBusManager for Staging is null!");
		}

		return theEjb;
	}
}