/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/EBDDNItemLocalHome.java,v 1.1 2003/08/13 11:27:25 hltan Exp $
 */
package com.integrosys.cms.app.ddn.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBDDNItem Entity Bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/13 11:27:25 $ Tag: $Name: $
 */
public interface EBDDNItemLocalHome extends EJBLocalHome {
	/**
	 * Create a ddn item information
	 * @param anIDDNItem of IDDNItem type
	 * @return EBDDNItemLocal
	 * @throws CreateException on error
	 */
	public EBDDNItemLocal create(IDDNItem anIDDNItem) throws CreateException;

	/**
	 * Find by Primary Key which is the ddn item ID.
	 * @param aDDNItemID of long type
	 * @return EBDDNItemLocal
	 * @throws FinderException on error
	 */
	public EBDDNItemLocal findByPrimaryKey(Long aDDNItemID) throws FinderException;

	/**
	 * Find by unique Key which is the ddn item ID.
	 * @param aDDNItemRef of long type
	 * @return EBDDNItemLocal
	 * @throws FinderException on error
	 */
	public EBDDNItemLocal findByDDNItemRef(long aDDNItemRef) throws FinderException;
}