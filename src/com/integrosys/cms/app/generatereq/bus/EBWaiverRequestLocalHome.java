/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/EBWaiverRequestLocalHome.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//javax
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local Home interface for the waiver request entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */

public interface EBWaiverRequestLocalHome extends EJBLocalHome {
	/**
	 * Create a waiver request
	 * @param anIWaiverRequest of IWaiverRequest type
	 * @return EBWaiverRequestLocal - the local handler for the waiver request
	 * @throws CreateException if creation fails
	 */
	public EBWaiverRequestLocal create(IWaiverRequest anIWaiverRequest) throws CreateException;

	/**
	 * Find by primary Key, the request ID
	 * @param aPK of Long type
	 * @return EBWaiverRequestLocal - the local handler for the waiver request
	 *         that has the PK as specified
	 * @throws FinderException
	 */
	public EBWaiverRequestLocal findByPrimaryKey(Long aPK) throws FinderException;
}