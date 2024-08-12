/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.proxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface to the SBTradingBookProxy session bean
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface SBTradingBookProxyHome extends EJBHome {
	/**
	 * Default Create method
	 * 
	 * @throws CreateException on error creating the ejb object
	 * @throws RemoteException on error during remote method call
	 */
	public SBTradingBookProxy create() throws CreateException, RemoteException;
}