/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/EBDeferralRequestItemLocalHome.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBDeferralRequestItem Entity Bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public interface EBDeferralRequestItemLocalHome extends EJBLocalHome {
	/**
	 * Create a request item information
	 * @param anIRequestItem of IDeferralRequestItem type
	 * @return EBDeferralRequestItemLocal
	 * @throws CreateException on error
	 */
	public EBDeferralRequestItemLocal create(IDeferralRequestItem anIRequestItem) throws CreateException;

	/**
	 * Find by Primary Key which is the request item ID.
	 * @param aRequestItemID of long type
	 * @return EBDeferralRequestItemLocal
	 * @throws FinderException on error
	 */
	public EBDeferralRequestItemLocal findByPrimaryKey(Long aRequestItemID) throws FinderException;

	/**
	 * Find by unique Key which is the request item ID.
	 * @param aRequestItemRef of long type
	 * @return EBDeferralRequestItemLocal
	 * @throws FinderException on error
	 */
	public EBDeferralRequestItemLocal findByRequestItemRef(long aRequestItemRef) throws FinderException;
}