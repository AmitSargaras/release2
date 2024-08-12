/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBItemLocalHome.java,v 1.1 2003/06/24 11:36:00 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.integrosys.cms.app.documentlocation.bus.IDocumentAppTypeItem;

/**
 * This is the Local Home interface for the EBItem Entity Bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/24 11:36:00 $ Tag: $Name: $
 */
public interface EBDocAppItemLocalHome extends EJBLocalHome {

	/**
	 * Find by Primary Key which is the item ID.
	 * 
	 * @param aItemID is long value of the contact ID
	 * @return EBItemLocal
	 * @throws FinderException on error
	 */
	public EBDocAppItemLocal findByPrimaryKey(Long aItemID) throws FinderException;
	
	public EBDocAppItemLocal create(Long aDocumentId ,IDocumentAppTypeItem aDocumentAppTypeItem) throws CreateException;
}