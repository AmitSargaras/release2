/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBCheckListItemLocalHome.java,v 1.3 2006/03/27 03:13:33 jitendra Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBCheckListItem Entity Bean.
 * 
 * @author $Author: jitendra $
 * @version $Revision: 1.3 $
 * @since $Date: 2006/03/27 03:13:33 $ Tag: $Name: $
 */
public interface EBCheckListItemLocalHome extends EJBLocalHome {
	/**
	 * Create a checklist item information
	 * 
	 * @param legalID the credit application ID of type long
	 * @param value is the IContact object
	 * @return EBContactLocal
	 * @throws CreateException on error
	 */
	public EBCheckListItemLocal create(Long aCheckListID, ICheckListItem anICheckListItem) throws CheckListException,
			CreateException;

	/**
	 * Find by Primary Key which is the checklist item ID.
	 * @param aCheckListItemID of long type
	 * @return EBCheckListItemLocal
	 * @throws FinderException on error
	 */
	public EBCheckListItemLocal findByPrimaryKey(Long aCheckListItemID) throws FinderException;

	/**
	 * Find by unique Key which is the checklist item ID.
	 * @param aCheckListItemRef of long type
	 * @return EBCheckListItemLocal
	 * @throws FinderException on error
	 */
	public EBCheckListItemLocal findByCheckListItemRef(long aCheckListItemRef) throws FinderException;
}