/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/EBPartialSCCertificateItemLocalHome.java,v 1.1 2003/08/11 12:49:32 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBPartialSCCertificateItem Entity
 * Bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/11 12:49:32 $ Tag: $Name: $
 */
public interface EBPartialSCCertificateItemLocalHome extends EJBLocalHome {
	/**
	 * Create a scc item information
	 * @param anIPartialSCCertificateItem of ISCCertificateItem type
	 * @return EBPartialSCCertificateItemLocal
	 * @throws CreateException on error
	 */
	public EBPartialSCCertificateItemLocal create(IPartialSCCertificateItem anIPartialSCCertificateItem)
			throws CreateException;

	/**
	 * Find by Primary Key which is the scc item ID.
	 * @param aSCCertItemID of long type
	 * @return EBPartialSCCertificateItemLocal
	 * @throws FinderException on error
	 */
	public EBPartialSCCertificateItemLocal findByPrimaryKey(Long aSCCertItemID) throws FinderException;

	/**
	 * Find by unique Key which is the scc item ID.
	 * @param aSCCertItemRef of long type
	 * @return EBPartialSCCertificateItemLocal
	 * @throws FinderException on error
	 */
	public EBPartialSCCertificateItemLocal findBySCCertificateItemRef(long aSCCertItemRef) throws FinderException;
}