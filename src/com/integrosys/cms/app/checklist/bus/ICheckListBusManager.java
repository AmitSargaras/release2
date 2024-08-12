/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ICheckListBusManager.java,v 1.6 2004/10/10 08:13:03 wltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.io.Serializable;
import java.util.HashMap;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * This interface defines the biz services that is available for checklist
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/10/10 08:13:03 $ Tag: $Name: $
 */
public interface ICheckListBusManager extends Serializable {
	/**
	 * Create a checklist
	 * @param anIChecklist - ICheckList
	 * @return ICheckList - the checkList being created
	 * @throws CheckListException;
	 */
	public ICheckList create(ICheckList anIChecklist) throws CheckListException;

	/**
	 * Update a checklist
	 * @param anICheckList - ICheckList
	 * @return ICheckList - the checkList being updated
	 * @throws CheckListException
	 */
	public ICheckList update(ICheckList anICheckList) throws ConcurrentUpdateException, CheckListException;

	/**
	 * Get a checklist by ID
	 * @param aCheckListID - long
	 * @return ICheckList - the checklist
	 * @throws CheckListException
	 */
	public ICheckList getCheckListByID(long aCheckListID) throws CheckListException;

	/**
	 * Get a checklist by ID
	 * @param monitorForDays - int
	 * @return List -
	 * @throws CheckListException public List getCheckListItemMonitorList(int
	 *         monitorForDays, String[] statusArray) throws CheckListException;
	 */

	/**
	 * Search checklist based on the criteria specified. Currently only used to
	 * search for checklist pending multi-level approval.
	 * 
	 * @param criteria of type CheckListSearchCriteria
	 * @return CheckListSearchResult[]
	 * @throws CheckListException on errors encountered
	 */
	public CheckListSearchResult[] searchCheckList(CheckListSearchCriteria criteria) throws CheckListException;
        
    public HashMap[] getDetailsForPreDisbursementReminderLetter(long limitProfileID)
            throws SearchDAOException, CheckListException;

    public HashMap[] getDetailsForPostDisbursementReminderLetter(long limitProfileID)
            throws SearchDAOException, CheckListException;



}
