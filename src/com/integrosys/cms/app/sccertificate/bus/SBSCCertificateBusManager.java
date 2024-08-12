/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/SBSCCertificateBusManager.java,v 1.3 2003/11/06 10:48:18 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

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
 * @version $Revision: 1.3 $
 * @since $Date: 2003/11/06 10:48:18 $ Tag: $Name: $
 */
public interface SBSCCertificateBusManager extends EJBObject {
	/**
	 * Get the SC Certificate by the CC Certificate ID
	 * @param aSCCertID of long type
	 * @return ISCCertificate - the sc certificate
	 * @throws CertificationException on errors
	 * @throws RemoteExcepion on remote errors
	 */
	public ISCCertificate getSCCertificate(long aSCCertID) throws SCCertificateException, RemoteException;

	/**
	 * Get the Partial SC Certificate by the Partial SC Certificate ID
	 * @param aSCCertID of long type
	 * @return ISCCertificate - the sc certificate
	 * @throws CertificationException on errors
	 * @throws RemoteExcepion on remote errors
	 */
	public IPartialSCCertificate getPartialSCCertificate(long aSCCertID) throws SCCertificateException, RemoteException;

	/**
	 * Get the SC Certificate by the limit profile ID
	 * @param aLimitProfileID of long type
	 * @return ISCCertificate - the sc certificate
	 * @throws CertificationException on errors
	 * @throws RemoteExcepion on remote errors
	 */
	public ISCCertificate getSCCertificateByLimitProfileID(long aLimitProfileID) throws SCCertificateException,
			RemoteException;

	/**
	 * Get the Partial SC Certificate by the limit profile ID
	 * @param aLimitProfileID of long type
	 * @return IPartialSCCertificate - the partial sc certificate
	 * @throws CertificationException on errors
	 * @throws RemoteExcepion on remote errors
	 */
	public IPartialSCCertificate getPartialSCCertificateByLimitProfileID(long aLimitProfileID)
			throws SCCertificateException, RemoteException;

	/**
	 * To get the number of sc certificate that satisfy the criteria
	 * @param aCriteria of SCCertificateSearchCriteria type
	 * @return int - the number of sc certificate that satisfy the criteria
	 * @throws SCCertificateException on errors
	 * @throws SearchDAOException on DAO errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfSCCertificate(SCCertificateSearchCriteria aCriteria) throws SCCertificateException,
			SearchDAOException, RemoteException;

	/**
	 * To get the number of partial sc certificate that satisfy the criteria
	 * @param aCriteria of SCCertificateSearchCriteria type
	 * @return int - the number of sc certificate that satisfy the criteria
	 * @throws SCCertificateException on errors
	 * @throws SearchDAOException on DAO errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfPartialSCCertificate(SCCertificateSearchCriteria aCriteria) throws SCCertificateException,
			SearchDAOException, RemoteException;

	/**
	 * Create a SC Certificate
	 * @param anISCCertificate of ISCCertificate type
	 * @return ISCCertificate - the SC certificate created
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ISCCertificate createSCCertificate(ISCCertificate anISCCertificate) throws SCCertificateException,
			RemoteException;

	/**
	 * Create a partial SC Certificate
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @return IPartialSCCertificate - the Partial SC certificate created
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public IPartialSCCertificate createPartialSCCertificate(IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException, RemoteException;

	/**
	 * Update a SC Certificate
	 * @param anISCCerticate of ISCCertificate
	 * @return ISCCertificate - the SC certificate updated
	 * @throws ConcurrentUpdateException if there is a concurrent update
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ISCCertificate updateSCCertificate(ISCCertificate anISCCertificate) throws ConcurrentUpdateException,
			SCCertificateException, RemoteException;

	/**
	 * Update a Partial SC Certificate
	 * @param anIPartialSCCerticate of IPartialSCCertificate
	 * @return IPartialSCCertificate - the Partial SC certificate updated
	 * @throws ConcurrentUpdateException if there is a concurrent update
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public IPartialSCCertificate updatePartialSCCertificate(IPartialSCCertificate anIPartialSCCertificate)
			throws ConcurrentUpdateException, SCCertificateException, RemoteException;

	/**
	 * To get the SCC Trx ID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return String - the SCC Trx ID
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public String getSCCTrxIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException, SCCertificateException,
			RemoteException;

	/**
	 * To get the PSCC Trx ID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return String - the SCC Trx ID
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public String getPSCCTrxIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException, SCCertificateException,
			RemoteException;

}
