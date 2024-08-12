/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/EBDeferralRequestLocal.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//javax
import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Local interface for the deferral request entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public interface EBDeferralRequestLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of a deferral request
	 * @return IDeferralRequest - the object encapsulating the deferral request
	 *         info
	 * @throws GenerateRequestException on errors
	 */
	public IDeferralRequest getValue() throws GenerateRequestException;

	/**
	 * Set the deferral request object
	 * @param anIDeferralRequest - IDeferralRequest
	 * @throws GenerateRequestException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(IDeferralRequest anIDeferralRequest) throws GenerateRequestException,
			ConcurrentUpdateException;

	/**
	 * Create the child items that are under this deferral request
	 * @param anIDeferralRequest - IDeferralRequest
	 * @throws GenerateRequestException on errors
	 */
	public void createDeferralRequestItems(IDeferralRequest anIDeferralRequest) throws GenerateRequestException;
}