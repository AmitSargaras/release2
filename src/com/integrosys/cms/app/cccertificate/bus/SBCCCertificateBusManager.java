/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/SBCCCertificateBusManager.java,v 1.4 2003/11/06 10:47:19 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

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
 * @version $Revision: 1.4 $
 * @since $Date: 2003/11/06 10:47:19 $ Tag: $Name: $
 */
public interface SBCCCertificateBusManager extends EJBObject {
	/**
	 * Get the CC Certificate by the CC Certificate ID
	 * @param aCCCertID of long type
	 * @return ICCCertificate - the cc certificate
	 * @throws CertificationException on errors
	 * @throws RemoteExcepion on remote errors
	 */
	public ICCCertificate getCCCertificate(long aCCCertID) throws CCCertificateException, RemoteException;

	/**
	 * Get the list of cc certificate trx based on the limit profile ID
	 * @param aLimitProfileID of long type
	 * @return CCCertificateSearchResult[] - the list of cc certificate trx info
	 * @throws SearchDAOException on errors
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public CCCertificateSearchResult[] getCCCertificateGenerated(long aLimitProfileID) throws SearchDAOException,
			CCCertificateException, RemoteException;

	/**
	 * To get the number of cc certificate that satisfy the criteria
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return int - the number of cc certificate that satisfy the criteria
	 * @throws CCCertificateException on errors
	 * @throws SearchDAOException on DAO errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfCCCertificate(CCCertificateSearchCriteria aCriteria) throws CCCertificateException,
			SearchDAOException, RemoteException;

	/**
	 * Get the Main borrower CC Certificate
	 * @param aLimitProfileID of long type
	 * @param anOwnerID of long type
	 * @return ICCCertificate - the cc certificate
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCCertificate getMainBorrowerCCC(long aLimitProfileID, long anOwnerID) throws CCCertificateException,
			RemoteException;

	/**
	 * Get the Co borrower CC Certificate
	 * @param aLimitProfileID of long type
	 * @param anOwnerID of long type
	 * @return ICCCertificate - the cc certificate
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCCertificate getCoBorrowerCCC(long aLimitProfileID, long anOwnerID) throws CCCertificateException,
			RemoteException;

	/**
	 * Get the pledgor CC Certificate
	 * @param aLimitProfileID of long type
	 * @param anOwnerID of long type
	 * @return ICCCertificate - the cc certificate
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCCertificate getPledgorCCC(long aLimitProfileID, long anOwnerID) throws CCCertificateException,
			RemoteException;

	/**
	 * Get the non borrower CC Certificate
	 * @param aLimitProfileID of long type
	 * @param anOwnerID of long type
	 * @return ICCCertificate - the cc certificate
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCCertificate getNonBorrowerCCC(long anOwnerID) throws CCCertificateException, RemoteException;

	/**
	 * Create a CC Certificate
	 * @param anICCCertificate of ICCCertificate type
	 * @return ICCCertificate - the cc certificate created
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCCertificate createCCCertificate(ICCCertificate anICCCertificate) throws CCCertificateException,
			RemoteException;

	/**
	 * Update a CC Certificate
	 * @param anICCCerticate of ICCCertificate
	 * @return ICCCertificate - the cc certificate updated
	 * @throws ConcurrentUpdateException if there is a concurrent update
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCCertificate updateCCCertificate(ICCCertificate anICCCertificate) throws ConcurrentUpdateException,
			CCCertificateException, RemoteException;

	/**
	 * Get the cc certificate trx ID
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return String - the CCC TRX ID
	 * @throws CCCertificateException on errors
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public String getCCCTrxID(CCCertificateSearchCriteria aCriteria) throws SearchDAOException, CCCertificateException,
			RemoteException;

}
