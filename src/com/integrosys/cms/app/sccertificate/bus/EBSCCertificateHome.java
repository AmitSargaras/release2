/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/EBSCCertificateHome.java,v 1.4 2003/11/06 10:47:55 hltan Exp $
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
 * Home interface for the sc certificate entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/11/06 10:47:55 $ Tag: $Name: $
 */

public interface EBSCCertificateHome extends EJBHome {
	/**
	 * Create a sc certificate
	 * @param anISCCertificate of ISCCertificate type
	 * @return EBSCCertificate - the remote handler for the created scc
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBSCCertificate create(ISCCertificate anISCCertificate) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the scc ID
	 * @param aPK of Long type
	 * @return EBSCCertificate - the remote handler for the scc that has the PK
	 *         as specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBSCCertificate findByPrimaryKey(Long aPK) throws FinderException, RemoteException;

	/**
	 * Find by primary Key, the scc ID
	 * @param aLimitProfileID of Long type
	 * @return Collection - a collection of remote handlers for the scc that has
	 *         the limit profile as specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public Collection findByLimitProfileID(Long aLimitProfileID) throws FinderException, RemoteException;

	/**
	 * To get the SCCID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return long - the SCC ID
	 * @throws SearchDAOException
	 * @throws RemoteException on remote errors
	 */
	public long getSCCIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException, RemoteException;

	/**
	 * To get the number of sc certificate that satisfy the criteria
	 * @param aCriteria of SCCertificateSearchCriteria type
	 * @return int - the number of sc certificate that satisfy the criteria
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfSCCertificate(SCCertificateSearchCriteria aCriteria) throws SearchDAOException, RemoteException;

	/**
	 * To get the SCC Trx ID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return String - the SCC Trx ID
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on errors
	 */
	public String getSCCTrxIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException, RemoteException;

}