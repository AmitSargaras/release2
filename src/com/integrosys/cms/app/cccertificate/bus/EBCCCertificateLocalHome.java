/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/EBCCCertificateLocalHome.java,v 1.1 2004/01/13 06:21:45 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//javax
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Home interface for the sc certificate entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/01/13 06:21:45 $ Tag: $Name: $
 */

public interface EBCCCertificateLocalHome extends EJBLocalHome {
	/**
	 * Create a sc certificate
	 * @param anICCCertificate of ICCCertificate type
	 * @return EBCCCertificateLocal - the local handler for the created ccc
	 * @throws CreateException if creation fails
	 */
	public EBCCCertificateLocal create(ICCCertificate anICCCertificate) throws CreateException;

	/**
	 * Find by primary Key, the ccc ID
	 * @param aPK of Long type
	 * @return EBCCCertificate - the local handler for the ccc that has the PK
	 *         as specified
	 * @throws FinderException
	 */
	public EBCCCertificateLocal findByPrimaryKey(Long aPK) throws FinderException;

	/**
	 * Find by limit profile ID, category and sub profile ID
	 * @param aLimitProfileID of Long type
	 * @param aCategory of String type
	 * @param aSubProfileID of Long type
	 * @return EBCCCertificate - the local handler for the cc certificate that
	 *         is of the limit profile ID, category and subprofile ID
	 * @throws FinderException
	 */
	public EBCCCertificateLocal findByLimitProfileAndSubProfile(Long aLimitProfileID, String aCategory,
			Long aSubProfileID) throws FinderException;

	/**
	 * Find by limit profile ID, category and pledgor ID
	 * @param aLimitProfileID of Long type
	 * @param aCategory of String type
	 * @param aSubProfileID of Long type
	 * @return EBCCCertificate - the local handler for the cc certificate that
	 *         is of the limit profile ID, category and pledgor ID
	 * @throws FinderException
	 */
	public EBCCCertificateLocal findByLimitProfileAndPledgor(Long aLimitProfileID, String aCategory, Long aPledgorID)
			throws FinderException;

	/**
	 * Find by category and subprofile ID
	 * @param aCategory of String type
	 * @param aSubProfileID of Long type
	 * @return EBCCCertificate - the local handler for the cc certificates that
	 *         is of the category and subprofile ID
	 * @throws FinderException
	 */
	public EBCCCertificateLocal findByCategorySubProfile(String aCategory, Long aSubProfileID) throws FinderException;

	/**
	 * Get the cc certificate ID
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return long - the CCC ID
	 * @throws SearchDAOException on errors
	 */
	public long getCCCID(CCCertificateSearchCriteria aCriteria) throws SearchDAOException;

	/**
	 * Get the cc certificate trx ID
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return String - the CCC TRX ID
	 * @throws SearchDAOException on errors
	 */
	public String getCCCTrxID(CCCertificateSearchCriteria aCriteria) throws SearchDAOException;

	/**
	 * Get the list of cc certificate trx based on the limit profile ID
	 * @param aLimitProfileID of long type
	 * @return CCCertificateSearchResult[] - the list of cc certificate trx info
	 * @throws SearchDAOException on errors
	 */
	public CCCertificateSearchResult[] getNoOfCCCGenerated(long aLimitProfileID) throws SearchDAOException;

	/**
	 * To get the number of cc certificate that satisfy the criteria
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return int - the number of cc certificate that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfCCCertificate(CCCertificateSearchCriteria aCriteria) throws SearchDAOException;
}