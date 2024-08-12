/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBCheckListLocalHome.java,v 1.3 2004/10/10 08:13:03 wltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//javax
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Home interface for the checklist entity bean
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/10/10 08:13:03 $ Tag: $Name: $
 */

public interface EBCheckListLocalHome extends EJBLocalHome {
	/**
	 * Create a checklist
	 * @param anICheckList - ICheckList
	 * @return EBCheckListLocal - the local handler for the created checklist
	 * @throws CreateException if creation fails
	 */
	public EBCheckListLocal create(ICheckList anICheckList) throws CreateException;

	/**
	 * Find by primary Key, the checklist ID
	 * @param aPK - Long
	 * @return EBCheckListLocal - the local handler for the checklist that has
	 *         the PK as specified
	 * @throws FinderException
	 */
	public EBCheckListLocal findByPrimaryKey(Long aPK) throws FinderException;

	/**
	 * Search checklist based on the criteria specified. Currently only used to
	 * search for checklist pending multi-level approval.
	 * 
	 * @param criteria of type CheckListSearchCriteria
	 * @return CheckListSearchResult[]
	 * @throws SearchDAOException on errors encountered searching for checklist
	 */
	public CheckListSearchResult[] searchCheckList(CheckListSearchCriteria criteria) throws SearchDAOException;
}