/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/EBPartialSCCertificateLocalHome.java,v 1.1 2003/08/11 12:49:32 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//java
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Home interface for the partial sc certificate entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/11 12:49:32 $ Tag: $Name: $
 */

public interface EBPartialSCCertificateLocalHome extends EJBLocalHome {
	/**
	 * Create a partial sc certificate
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @return EBPartialSCCertificateLocal - the local handler for the created
	 *         partial scc
	 * @throws CreateException if creation fails
	 */
	public EBPartialSCCertificateLocal create(IPartialSCCertificate anIPartialSCCertificate) throws CreateException;

	/**
	 * Find by primary Key, the scc ID
	 * @param aPK of Long type
	 * @return EBPartialSCCertificateLocal - the local handler for the partial
	 *         scc that has the PK as specified
	 * @throws FinderException
	 */
	public EBPartialSCCertificateLocal findByPrimaryKey(Long aPK) throws FinderException;

	/**
	 * Find by primary Key, the scc ID
	 * @param aLimitProfileID of Long type
	 * @return Collection - a collection of remote handlers for the partial scc
	 *         that has the limit profile as specified
	 * @throws FinderException
	 */
	public Collection findByLimitProfileID(Long aLimitProfileID) throws FinderException;

	/**
	 * To get the number of partial sc certificate that satisfy the criteria
	 * @param aCriteria of SCCertificateSearchCriteria type
	 * @return int - the number of partial sc certificate that satisfy the
	 *         criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfPartialSCCertificate(SCCertificateSearchCriteria aCriteria) throws SearchDAOException;

}