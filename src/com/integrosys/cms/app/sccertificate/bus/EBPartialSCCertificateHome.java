/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/EBPartialSCCertificateHome.java,v 1.3 2003/11/26 10:26:02 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//java
import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Home interface for the partial sc certificate entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/11/26 10:26:02 $ Tag: $Name: $
 */

public interface EBPartialSCCertificateHome extends EJBHome {
	/**
	 * Create a partial sc certificate
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @return EBPartialSCCertificate - the remote handler for the created
	 *         partial scc
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBPartialSCCertificate create(IPartialSCCertificate anIPartialSCCertificate) throws CreateException,
			RemoteException;

	/**
	 * Find by primary Key, the scc ID
	 * @param aPK of Long type
	 * @return EBPartialSCCertificate - the remote handler for the partial scc
	 *         that has the PK as specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBPartialSCCertificate findByPrimaryKey(Long aPK) throws FinderException, RemoteException;

	/**
	 * Find by primary Key, the scc ID
	 * @param aLimitProfileID of Long type
	 * @return Collection - a collection of remote handlers for the partial scc
	 *         that has the limit profile as specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public Collection findByLimitProfileID(Long aLimitProfileID) throws FinderException, RemoteException;

	/**
	 * To get the PSCCID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return long - the PSCC ID
	 * @throws SearchDAOException
	 * @throws RemoteException on remote errors
	 */
	public long getPSCCIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException, RemoteException;

	/**
	 * To get the number of partial sc certificate that satisfy the criteria
	 * @param aCriteria of SCCertificateSearchCriteria type
	 * @return int - the number of partial sc certificate that satisfy the
	 *         criteria
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfPartialSCCertificate(SCCertificateSearchCriteria aCriteria) throws SearchDAOException,
			RemoteException;

	/**
	 * To get the PSCC Trx ID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return String - the SCC Trx ID
	 * @throws SearchDAOException
	 * @throws RemoteException
	 */
	public String getPSCCTrxIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException, RemoteException;

}