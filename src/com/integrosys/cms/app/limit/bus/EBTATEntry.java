/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBTATEntry.java,v 1.1 2003/07/15 08:24:49 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * This is the remote interface to the EBTATEntry entity bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/15 08:24:49 $ Tag: $Name: $
 */
public interface EBTATEntry extends EJBObject {
	/**
	 * Get an object representation from persistance
	 * 
	 * @return ITATEntry
	 * @throws RemoteException
	 */
	public ITATEntry getValue() throws RemoteException;

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type ITATEntry
	 * @throws LimitException, RemoteException
	 */
	public void setValue(ITATEntry value) throws LimitException, RemoteException;
}