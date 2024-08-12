/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface to SBLiquidationBusManagerBean session bean.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface SBLiquidationBusManagerHome extends EJBHome {
	/**
	 * Creates Liquidation manager ejb object.
	 * 
	 * @return colateral manager session bean
	 * @throws java.rmi.RemoteException on errors during remote method call
	 */
	public SBLiquidationBusManager create() throws CreateException, RemoteException;
}