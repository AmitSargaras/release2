/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import java.rmi.RemoteException;
import java.util.Collection;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This class facades the ILiquidationManager implementation by delegating the
 * handling of requests to an ejb session bean.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class LiquidationBusManagerImpl implements ILiquidationBusManager {

	private static final long serialVersionUID = -7455016605937991426L;

	public Collection getNPLInfo(long collateralID) throws LiquidationException {
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.liquidation.bus.ILiquidationBusManager#getLiquidation
	 */
	public ILiquidation getLiquidation(long liquidationID) throws LiquidationException {

		SBLiquidationBusManager theEjb = getBusManager();

		try {
			return theEjb.getLiquidation(liquidationID);
		}
		catch (RemoteException e) {
			throw new LiquidationException(
					"failed to work on liquidation bus manager remote interface for get liquidation, throwing root case",
					e.getCause());
		}
	}

	public ILiquidation createLiquidation(ILiquidation liq) throws LiquidationException {
		SBLiquidationBusManager theEjb = getBusManager();

		try {
			return theEjb.createLiquidation(liq);
		}
		catch (RemoteException e) {
			throw new LiquidationException(
					"failed to work on liquidation bus manager remote interface for create liquidation, throwing root case",
					e.getCause());
		}
	}

	public ILiquidation updateLiquidation(ILiquidation liq) throws LiquidationException {
		SBLiquidationBusManager theEjb = getBusManager();

		try {
			return theEjb.updateLiquidation(liq);
		}
		catch (RemoteException e) {
			throw new LiquidationException(
					"failed to work on liquidation bus manager remote interface for update liquidation, throwing root case",
					e.getCause());
		}
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
				ICMSJNDIConstant.SB_LIQUIDATION_MGR_JNDI, SBLiquidationBusManagerHome.class.getName());

		if (theEjb == null) {
			throw new LiquidationException("SBLiquidationManager for Actual is null!");
		}

		return theEjb;
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws LiquidationException on errors encountered
	 */
	protected void rollback() throws LiquidationException {
	}
}