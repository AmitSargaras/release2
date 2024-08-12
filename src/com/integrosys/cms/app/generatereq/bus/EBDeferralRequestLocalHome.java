/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/EBDeferralRequestLocalHome.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//javax
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local Home interface for the deferral request entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */

public interface EBDeferralRequestLocalHome extends EJBLocalHome {
	/**
	 * Create a deferral request
	 * @param anIDeferralRequest of IDeferralRequest type
	 * @return EBDeferralRequestLocal - the local handler for the deferral
	 *         request
	 * @throws CreateException if creation fails
	 */
	public EBDeferralRequestLocal create(IDeferralRequest anIDeferralRequest) throws CreateException;

	/**
	 * Find by primary Key, the request ID
	 * @param aPK of Long type
	 * @return EBDeferralRequestLocal - the local handler for the deferral
	 *         request that has the PK as specified
	 * @throws FinderException
	 */
	public EBDeferralRequestLocal findByPrimaryKey(Long aPK) throws FinderException;
}