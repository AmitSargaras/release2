/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/EBSCCertificateItemLocalHome.java,v 1.1 2003/08/06 05:28:54 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBSCCertificateItem Entity Bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/06 05:28:54 $ Tag: $Name: $
 */
public interface EBSCCertificateItemLocalHome extends EJBLocalHome {
	/**
	 * Create a scc item information
	 * @param anISCCertificateItem of ISCCertificateItem type
	 * @return EBSCCertificateItemLocal
	 * @throws CreateException on error
	 */
	public EBSCCertificateItemLocal create(ISCCertificateItem anISCCertificateItem) throws CreateException;

	/**
	 * Find by Primary Key which is the scc item ID.
	 * @param aSCCertItemID of long type
	 * @return EBSCCertificateItemLocal
	 * @throws FinderException on error
	 */
	public EBSCCertificateItemLocal findByPrimaryKey(Long aSCCertItemID) throws FinderException;

	/**
	 * Find by unique Key which is the scc item ID.
	 * @param aSCCertItemRef of long type
	 * @return EBSCCertificateItemLocal
	 * @throws FinderException on error
	 */
	public EBSCCertificateItemLocal findBySCCertificateItemRef(long aSCCertItemRef) throws FinderException;
}