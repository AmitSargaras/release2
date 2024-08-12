/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface to SBInterestRateManagerBean session bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface SBInterestRateBusManagerHome extends EJBHome {
	/**
	 * Creates interestrate manager ejb object.
	 * 
	 * @return colateral manager session bean
	 * @throws RemoteException on errors during remote method call
	 */
	public SBInterestRateBusManager create() throws CreateException, RemoteException;
}