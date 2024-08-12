/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBRecurrentCheckList.java,v 1.1 2003/07/24 01:31:04 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

//java

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Remote interface for the recurrent checklist entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/24 01:31:04 $ Tag: $Name: $
 */
public interface EBRecurrentCheckList extends EJBObject {
	/**
	 * Retrieve an instance of a checklist
	 * @return IRecurrentCheckList - the object encapsulating the recurrent
	 *         checklist info
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 * @throws RemoteException on remote errors
	 */
	public IRecurrentCheckList getValue() throws RecurrentException, RemoteException;

	/**
	 * Set the recurrent checklist object
	 * @param anIRecurrentCheckList - IRecurrentCheckList
	 * @throws RecurrentException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException
	 */
	public void setValue(IRecurrentCheckList anIRecurrentCheckList) throws RecurrentException,
			ConcurrentUpdateException, RemoteException;

	/**
	 * Create the child items and convenant that are under this recurrent
	 * checklist
	 * @param anIRecurrentCheckList - IRecurrentCheckList
	 * @throws RecurrentException on errors
	 * @throws RemoteException on remote errors
	 */
	public void createDependents(IRecurrentCheckList anIRecurrentCheckList) throws RecurrentException, RemoteException;
}