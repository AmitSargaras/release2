/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/EBWaiverRequestLocal.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//javax
import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Local interface for the waiver request entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public interface EBWaiverRequestLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of a waiver request
	 * @return IWaiverRequest - the object encapsulating the waiver request info
	 * @throws GenerateRequestException on errors
	 */
	public IWaiverRequest getValue() throws GenerateRequestException;

	/**
	 * Set the waiver request object
	 * @param anIWaiverRequest - IWaiverRequest
	 * @throws GenerateRequestException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(IWaiverRequest anIWaiverRequest) throws GenerateRequestException, ConcurrentUpdateException;

	/**
	 * Create the child items that are under this waiver request
	 * @param anIWaiverRequest - IWaiverRequest
	 * @throws GenerateRequestException on errors
	 */
	public void createWaiverRequestItems(IWaiverRequest anIWaiverRequest) throws GenerateRequestException;
}