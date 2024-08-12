/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBItemLocalHome.java,v 1.1 2003/06/24 11:36:00 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBItem Entity Bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/24 11:36:00 $ Tag: $Name: $
 */
public interface EBItemLocalHome extends EJBLocalHome {

	/**
	 * Find by Primary Key which is the item ID.
	 * 
	 * @param aItemID is long value of the contact ID
	 * @return EBItemLocal
	 * @throws FinderException on error
	 */
	public EBItemLocal findByPrimaryKey(Long aItemID) throws FinderException;
}