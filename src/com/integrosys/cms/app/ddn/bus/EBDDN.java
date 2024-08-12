/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/EBDDN.java,v 1.1 2003/08/13 11:27:25 hltan Exp $
 */
package com.integrosys.cms.app.ddn.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Remote interface for the ddn entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/13 11:27:25 $ Tag: $Name: $
 */
public interface EBDDN extends EJBObject {
	/**
	 * Retrieve an instance of a ddn certificate
	 * @return IDDN - the object encapsulating the ddn info
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public IDDN getValue() throws DDNException, RemoteException;

	/**
	 * Set the ddn object
	 * @param anIDDN of IDDN type
	 * @throws DDNException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException
	 */
	public void setValue(IDDN anIDDN) throws DDNException, ConcurrentUpdateException, RemoteException;

	/**
	 * Create the child items that are under this ddn
	 * @param anIDDN - IDDN
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public void createDDNItems(IDDN anIDDN) throws DDNException, RemoteException;
}