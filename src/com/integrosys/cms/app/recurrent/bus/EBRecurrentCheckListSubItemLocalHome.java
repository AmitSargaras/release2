/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBRecurrentCheckListSubItemLocalHome.java,v 1.1 2004/07/01 02:11:21 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBRecurrentCheckListSubItem Entity
 * Bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/07/01 02:11:21 $ Tag: $Name: $
 */
public interface EBRecurrentCheckListSubItemLocalHome extends EJBLocalHome {
	/**
	 * Create a checklist item information
	 * @param aCheckListItemID of Long type
	 * @param anIRecurrentCheckListSubItem of IRecurrentCheckListSubItem type
	 * @return EBRecurrentCheckListSubItemLocal
	 * @throws CreateException on error
	 */
	public EBRecurrentCheckListSubItemLocal create(Long aCheckListItemID,
			IRecurrentCheckListSubItem anIRecurrentCheckListSubItem) throws CreateException;

	/**
	 * Find by Primary Key which is the checklist item ID.
	 * @param aCheckListSubItemID of Long type
	 * @return EBRecurrentCheckListSubItemLocal
	 * @throws FinderException on error
	 */
	public EBRecurrentCheckListSubItemLocal findByPrimaryKey(Long aCheckListSubItemID) throws FinderException;

	/**
	 * Find by unique Key which is the checklist sub item ID.
	 * @param aCheckListSubItemRef of long type
	 * @return EBRecurrentCheckListItemLocal
	 * @throws FinderException on error
	 */
	public EBRecurrentCheckListSubItemLocal findByCheckListSubItemRef(long aCheckListSubItemRef) throws FinderException;

}