/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/EBCCCertificateItemLocalHome.java,v 1.1 2004/01/13 06:21:45 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBCCCertificateItem Entity Bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/01/13 06:21:45 $ Tag: $Name: $
 */
public interface EBCCCertificateItemLocalHome extends EJBLocalHome {
	/**
	 * Create a ccc item information
	 * @param anICCCertificateItem of ICCCertificateItem type
	 * @return EBCCCertificateItemLocal
	 * @throws CreateException on error
	 */
	public EBCCCertificateItemLocal create(ICCCertificateItem anICCCertificateItem) throws CreateException;

	/**
	 * Find by Primary Key which is the ccc item ID.
	 * @param aCCCertItemID of long type
	 * @return EBCCCertificateItemLocal
	 * @throws FinderException on error
	 */
	public EBCCCertificateItemLocal findByPrimaryKey(Long aCCCertItemID) throws FinderException;

	/**
	 * Find by unique Key which is the ccc item ID.
	 * @param aCCCertItemRef of long type
	 * @return EBCCCertificateItemLocal
	 * @throws FinderException on error
	 */
	public EBCCCertificateItemLocal findByCCCertificateItemRef(long aCCCertItemRef) throws FinderException;
}