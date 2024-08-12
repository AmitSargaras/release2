/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/EBWaiverRequestItemLocalHome.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBWaiverRequestItem Entity Bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public interface EBWaiverRequestItemLocalHome extends EJBLocalHome {
	/**
	 * Create a request item information
	 * @param anIRequestItem of IWaiverRequestItem type
	 * @return EBWaiverRequestItemLocal
	 * @throws CreateException on error
	 */
	public EBWaiverRequestItemLocal create(IWaiverRequestItem anIRequestItem) throws CreateException;

	/**
	 * Find by Primary Key which is the request item ID.
	 * @param aRequestItemID of long type
	 * @return EBWaiverRequestItemLocal
	 * @throws FinderException on error
	 */
	public EBWaiverRequestItemLocal findByPrimaryKey(Long aRequestItemID) throws FinderException;

	/**
	 * Find by unique Key which is the request item ID.
	 * @param aRequestItemRef of long type
	 * @return EBWaiverRequestItemLocal
	 * @throws FinderException on error
	 */
	public EBWaiverRequestItemLocal findByRequestItemRef(long aRequestItemRef) throws FinderException;
}