/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBCheckListLocal.java,v 1.1 2003/07/16 09:08:33 hltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//javax
import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Local interface for the checklist entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/16 09:08:33 $ Tag: $Name: $
 */
public interface EBCheckListLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of a checklist
	 * @return ICheckList - the object encapsulating the checklist info
	 * @throws CheckListException on errors
	 */
	public ICheckList getValue() throws CheckListException;

	/**
	 * Set the checklist object
	 * @param anICheckList - ICheckList
	 * @throws CheckListException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(ICheckList anICheckList) throws CheckListException, ConcurrentUpdateException;

	/**
	 * Create the child items that are under this checklist
	 * @param anICheckList - ICheckList
	 * @throws CheckListException
	 */
	public void createCheckListItems(ICheckList anICheckList) throws CheckListException;
}