/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/SBGenerateRequestBusManager.java,v 1.2 2003/09/12 02:29:26 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Session bean remote interface for the services provided by the certificate
 * bus manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/12 02:29:26 $ Tag: $Name: $
 */
public interface SBGenerateRequestBusManager extends EJBObject {
	/**
	 * To get the number of waiver request that satisfy the criteria
	 * @param aCriteria of WaiverRequestSearchCriteria type
	 * @return int - the number of waiver request that satisfy the criteria
	 * @throws SearchDAOException, GenerateRequestException on errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfWaiverRequest(WaiverRequestSearchCriteria aCriteria) throws SearchDAOException,
			GenerateRequestException, RemoteException;

	/**
	 * To get the number of deferral request that satisfy the criteria
	 * @param aCriteria of DeferralRequestSearchCriteria type
	 * @return int - the number of deferral request that satisfy the criteria
	 * @throws SearchDAOException, GenerateRequestException on errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfDeferralRequest(DeferralRequestSearchCriteria aCriteria) throws SearchDAOException,
			GenerateRequestException, RemoteException;

	/**
	 * Get the waiver request by the waiver request ID
	 * @param aRequestID of long type
	 * @return IWaiverRequest - the waiver request
	 * @throws GenerateRequestException on errors
	 * @throws RemoteExcepion on remote errors
	 */
	public IWaiverRequest getWaiverRequest(long aRequestID) throws GenerateRequestException, RemoteException;

	/**
	 * Get the deferral request by the deferral request ID
	 * @param aRequestID of long type
	 * @return IDeferralRequest - the deferral request
	 * @throws GenerateRequestException on errors
	 * @throws RemoteExcepion on remote errors
	 */
	public IDeferralRequest getDeferralRequest(long aRequestID) throws GenerateRequestException, RemoteException;

	/**
	 * Create a waiver request
	 * @param anIWaiverRequest of IWaiverRequest type
	 * @return IWaiverRequest - the waiver request created
	 * @throws GenerateRequestException on errors
	 * @throws RemoteException on remote errors
	 */
	public IWaiverRequest createRequest(IWaiverRequest anIWaiverRequest) throws GenerateRequestException,
			RemoteException;

	/**
	 * Create a deferral request
	 * @param anIDeferralRequest of IDeferralRequest type
	 * @return IDeferralRequest - the deferral request created
	 * @throws GenerateRequestException on errors
	 * @throws RemoteException on remote errors
	 */
	public IDeferralRequest createRequest(IDeferralRequest anIDeferralRequest) throws GenerateRequestException,
			RemoteException;

	/**
	 * Update a waiver request
	 * @param anIWaiverRequest of IWaiverRequest
	 * @return IWaiverRequest - the waiver request updated
	 * @throws ConcurrentUpdateException if there is a concurrent update
	 * @throws GenerateRequestException on errors
	 * @throws RemoteException on remote errors
	 */
	public IWaiverRequest updateRequest(IWaiverRequest anIWaiverRequest) throws ConcurrentUpdateException,
			GenerateRequestException, RemoteException;

	/**
	 * Update a deferral request
	 * @param anIDeferralRequest of IDeferralRequest
	 * @return IDeferralRequest - the deferral request updated
	 * @throws ConcurrentUpdateException if there is a concurrent update
	 * @throws GenerateRequestException on errors
	 * @throws RemoteException on remote errors
	 */
	public IDeferralRequest updateRequest(IDeferralRequest anIDeferralRequest) throws ConcurrentUpdateException,
			GenerateRequestException, RemoteException;
}
