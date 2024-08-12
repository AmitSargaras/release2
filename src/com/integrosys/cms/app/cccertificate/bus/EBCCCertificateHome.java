/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/EBCCCertificateHome.java,v 1.5 2003/11/06 10:47:19 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Home interface for the certificate entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/11/06 10:47:19 $ Tag: $Name: $
 */

public interface EBCCCertificateHome extends EJBHome {
	/**
	 * Create a CC certificate
	 * @param anICCCertificate of ICCCertificate
	 * @return EBCCCertificate - the remote handler for the created certificate
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBCCCertificate create(ICCCertificate anICCCertificate) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the checklist ID
	 * @param aPK - Long
	 * @return EBCCCertificate - the remote handler for the cc certificate that
	 *         has the PK as specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBCCCertificate findByPrimaryKey(Long aPK) throws FinderException, RemoteException;

	/**
	 * Find by limit profile ID, category and sub profile ID
	 * @param aLimitProfileID of Long type
	 * @param aCategory of String type
	 * @param aSubProfileID of Long type
	 * @return EBCCCertificate - the remote handler for the cc certificate that
	 *         is of the limit profile ID, category and subprofile ID
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBCCCertificate findByLimitProfileAndSubProfile(Long aLimitProfileID, String aCategory, Long aSubProfileID)
			throws FinderException, RemoteException;

	/**
	 * Find by limit profile ID, category and pledgor ID
	 * @param aLimitProfileID of Long type
	 * @param aCategory of String type
	 * @param aSubProfileID of Long type
	 * @return EBCCCertificate - the remote handler for the cc certificate that
	 *         is of the limit profile ID, category and pledgor ID
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBCCCertificate findByLimitProfileAndPledgor(Long aLimitProfileID, String aCategory, Long aPledgorID)
			throws FinderException, RemoteException;

	/**
	 * Find by category and subprofile ID
	 * @param aCategory of String type
	 * @param aSubProfileID of Long type
	 * @return EBCCCertificate - the remote handler for the cc certificates that
	 *         is of the category and subprofile ID
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBCCCertificate findByCategorySubProfile(String aCategory, Long aSubProfileID) throws FinderException,
			RemoteException;

	/**
	 * Get the cc certificate ID
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return long - the CCC ID
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public long getCCCID(CCCertificateSearchCriteria aCriteria) throws SearchDAOException, RemoteException;

	/**
	 * Get the cc certificate trx ID
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return String - the CCC TRX ID
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public String getCCCTrxID(CCCertificateSearchCriteria aCriteria) throws SearchDAOException, RemoteException;

	/**
	 * Get the list of cc certificate trx based on the limit profile ID
	 * @param aLimitProfileID of long type
	 * @return CCCertificateSearchResult[] - the list of cc certificate trx info
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public CCCertificateSearchResult[] getNoOfCCCGenerated(long aLimitProfileID) throws SearchDAOException,
			RemoteException;

	/**
	 * To get the number of cc certificate that satisfy the criteria
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return int - the number of cc certificate that satisfy the criteria
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfCCCertificate(CCCertificateSearchCriteria aCriteria) throws SearchDAOException, RemoteException;

}