/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBCheckList.java,v 1.3 2003/07/16 09:08:33 hltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Remote interface for the checklist entity bean
 * 
 * @author $Author: Abhijit Rudrakshawar $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/16 09:08:33 $ Tag: $Name: $
 */
public interface EBCheckList extends EJBObject {
	/**
	 * Retrieve an instance of a checklist
	 * @return ICheckList - the object encapsulating the checklist info
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckList getValue() throws CheckListException, RemoteException;

	/**
	 * Set the checklist object
	 * @param anICheckList - ICheckList
	 * @throws CheckListException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException
	 */
	public void setValue(ICheckList anICheckList) throws CheckListException, ConcurrentUpdateException, RemoteException;

	/**
	 * Create the child items that are under this checklist
	 * @param anICheckList - ICheckList
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public void createCheckListItems(ICheckList anICheckList) throws CheckListException, RemoteException;
}