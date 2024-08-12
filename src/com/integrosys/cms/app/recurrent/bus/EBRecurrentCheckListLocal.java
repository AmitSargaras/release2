/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBRecurrentCheckListLocal.java,v 1.1 2003/08/07 02:35:31 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

//javax

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import javax.ejb.EJBLocalObject;

/**
 * Remote interface for the recurrent checklist entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/07 02:35:31 $ Tag: $Name: $
 */
public interface EBRecurrentCheckListLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of a checklist
	 * @return IRecurrentCheckList - the object encapsulating the recurrent
	 *         checklist info
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckList getValue() throws RecurrentException;

	/**
	 * Set the recurrent checklist object
	 * @param anIRecurrentCheckList - IRecurrentCheckList
	 * @throws RecurrentException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(IRecurrentCheckList anIRecurrentCheckList) throws RecurrentException,
			ConcurrentUpdateException;

	/**
	 * Create the child items and convenant that are under this recurrent
	 * checklist
	 * @param anIRecurrentCheckList - IRecurrentCheckList
	 * @throws RecurrentException on errors
	 */
	public void createDependents(IRecurrentCheckList anIRecurrentCheckList) throws RecurrentException;
}