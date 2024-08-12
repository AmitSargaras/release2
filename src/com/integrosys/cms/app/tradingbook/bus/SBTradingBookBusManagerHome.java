/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface to SBTradingBookManagerBean session bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface SBTradingBookBusManagerHome extends EJBHome {
	/**
	 * Creates tradingbook manager ejb object.
	 * 
	 * @return trading book manager session bean
	 * @throws RemoteException on errors during remote method call
	 */
	public SBTradingBookBusManager create() throws CreateException, RemoteException;
}