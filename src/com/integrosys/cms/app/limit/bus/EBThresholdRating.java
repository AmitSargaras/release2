/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limit.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * This is the remote interface to the EBThresholdRating entity bean
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBThresholdRating extends EJBObject {

	/**
	 * Get an object representation from persistance
	 * 
	 * @return IThresholdRating
	 * @throws RemoteException
	 */
	public IThresholdRating getValue() throws RemoteException;

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type IThresholdRating
	 * @throws LimitException, RemoteException
	 */
	public void setValue(IThresholdRating value) throws LimitException, RemoteException;
}