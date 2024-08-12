/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBRecurrentCheckListItemLocalHome.java,v 1.2 2003/07/28 12:48:33 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBRecurrentCheckListItem Entity
 * Bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/28 12:48:33 $ Tag: $Name: $
 */
public interface EBRecurrentCheckListItemLocalHome extends EJBLocalHome {
	/**
	 * Create a checklist item information
	 * @param aCheckListID of Long type
	 * @param anIRecurrentCheckListItem of IRecurrentCheckListItem type
	 * @return EBRecurrentCheckListItemLocal
	 * @throws CreateException on error
	 */
	public EBRecurrentCheckListItemLocal create(Long aCheckListID, IRecurrentCheckListItem anIRecurrentCheckListItem)
			throws CreateException;

	/**
	 * Find by Primary Key which is the checklist item ID.
	 * @param aCheckListItemID of Long type
	 * @return EBRecurrentCheckListItemLocal
	 * @throws FinderException on error
	 */
	public EBRecurrentCheckListItemLocal findByPrimaryKey(Long aCheckListItemID) throws FinderException;

	/**
	 * Find by unique Key which is the checklist item ID.
	 * @param aCheckListItemRef of long type
	 * @return EBRecurrentCheckListItemLocal
	 * @throws FinderException on error
	 */
	public EBRecurrentCheckListItemLocal findByCheckListItemRef(long aCheckListItemRef) throws FinderException;

}