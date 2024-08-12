/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBTemplateItemLocalHome.java,v 1.1 2003/06/24 11:36:00 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBTemplateItem Entity Bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/24 11:36:00 $ Tag: $Name: $
 */
public interface EBTemplateItemLocalHome extends EJBLocalHome {
	/**
	 * Create a template item information
	 * 
	 * @param aTemplateID the template ID of type long
	 * @param anITemplateItem is the ITemplateItem object
	 * @return EBContactLocal
	 * @throws CreateException on error
	 */
	public EBTemplateItemLocal create(Long aTemplateID, ITemplateItem anITemplateItem) throws CreateException;

	/**
	 * Find by Primary Key which is the template item ID.
	 * 
	 * @param aTemplateItemID is long value of the contact ID
	 * @return EBTemplateItemLocal
	 * @throws FinderException on error
	 */
	public EBTemplateItemLocal findByPrimaryKey(Long aTemplateItemID) throws FinderException;
}