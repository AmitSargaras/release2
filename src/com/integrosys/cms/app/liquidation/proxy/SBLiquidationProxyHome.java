/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.proxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface to the SBLiquidationProxy session bean
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface SBLiquidationProxyHome extends EJBHome {
	/**
	 * Default Create method
	 * 
	 * @throws javax.ejb.CreateException on error creating the ejb object
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public SBLiquidationProxy create() throws CreateException, RemoteException;
}