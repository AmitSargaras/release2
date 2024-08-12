/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/EBDeferralRequestHome.java,v 1.2 2003/09/12 02:29:26 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Home interface for the deferral request entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/12 02:29:26 $ Tag: $Name: $
 */

public interface EBDeferralRequestHome extends EJBHome {
	/**
	 * Create a deferral request
	 * @param anIDeferralRequest of IDeferralRequest type
	 * @return EBDeferralRequest - the remote handler for the deferral request
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBDeferralRequest create(IDeferralRequest anIDeferralRequest) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the request ID
	 * @param aPK of Long type
	 * @return EBDeferralRequest - the remote handler for the deferral request
	 *         that has the PK as specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBDeferralRequest findByPrimaryKey(Long aPK) throws FinderException, RemoteException;

	/**
	 * To get the number of deferral request that satisfy the criteria
	 * @param aCriteria of DeferralRequestSearchCriteria type
	 * @return int - the number of deferral request that satisfy the criteria
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfDeferralRequest(DeferralRequestSearchCriteria aCriteria) throws SearchDAOException,
			RemoteException;

}