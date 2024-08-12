/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Remote interface to the Liquidation business manager session bean.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface SBLiquidationBusManager extends EJBObject {

	public ILiquidation getLiquidation(long collateralID) throws LiquidationException, RemoteException;

	public ILiquidation createLiquidation(ILiquidation liq) throws LiquidationException, RemoteException;

	public ILiquidation updateLiquidation(ILiquidation liq) throws LiquidationException, RemoteException;
}