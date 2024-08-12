/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/EBSCCertificateLocalHome.java,v 1.1 2003/08/08 12:44:14 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Home interface for the sc certificate entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/08 12:44:14 $ Tag: $Name: $
 */

public interface EBSCCertificateLocalHome extends EJBLocalHome {
	/**
	 * Create a sc certificate
	 * @param anISCCertificate of ISCCertificate type
	 * @return EBSCCertificateLocal - the local handler for the created scc
	 * @throws CreateException if creation fails
	 */
	public EBSCCertificateLocal create(ISCCertificate anISCCertificate) throws CreateException;

	/**
	 * Find by primary Key, the scc ID
	 * @param aPK of Long type
	 * @return EBSCCertificate - the local handler for the scc that has the PK
	 *         as specified
	 * @throws FinderException
	 */
	public EBSCCertificateLocal findByPrimaryKey(Long aPK) throws FinderException;

	/**
	 * Find by primary Key, the scc ID
	 * @param aLimitProfileID of Long type
	 * @return Collection - a collection of local handlers for the scc that has
	 *         the limit profile as specified
	 * @throws FinderException
	 */
	public Collection findByLimitProfileID(Long aLimitProfileID) throws FinderException;

	/**
	 * To get the number of sc certificate that satisfy the criteria
	 * @param aCriteria of SCCertificateSearchCriteria type
	 * @return int - the number of sc certificate that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfSCCertificate(SCCertificateSearchCriteria aCriteria) throws SearchDAOException;

}