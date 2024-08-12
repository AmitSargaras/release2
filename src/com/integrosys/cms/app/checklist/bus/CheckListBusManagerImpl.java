/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CheckListBusManagerImpl.java,v 1.8 2004/10/10 08:13:03 wltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.rmi.RemoteException;
import java.util.HashMap;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.chktemplate.bus.IDocumentBusManager;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateBusManagerImpl;

/**
 * This class act as a facade to the business services provided by the checklist
 * 
 * @author $Author: Abhijit Rudrakshawar $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2004/10/10 08:13:03 $ Tag: $Name: $
 */
public class CheckListBusManagerImpl implements ICheckListBusManager {

    /**
	 * Create a checklist
	 * @param anICheckList - ICheckList
	 * @return ICheckList - the checkList being created
	 * @throws CheckListException;
	 */
	public ICheckList create(ICheckList anICheckList) throws CheckListException {
		try {
			return getCheckListBusManager().create(anICheckList);
		}
		catch (RemoteException ex) {
			throw new CheckListException(ex.toString());
		}

	}

	/**
	 * Update a checklist
	 * @param anICheckList - ICheckList
	 * @return ICheckList - the checkList being updated
	 * @throws CheckListException
	 */
	public ICheckList update(ICheckList anICheckList) throws ConcurrentUpdateException, CheckListException {
		try {
			return getCheckListBusManager().update(anICheckList);
		}
		catch (RemoteException ex) {
			throw new CheckListException(ex.toString());
		}
	}

	/**
	 * Get a checklist by ID
	 * @param aCheckListID - long
	 * @return ICheckList - the checklist
	 * @throws CheckListException
	 */
	public ICheckList getCheckListByID(long aCheckListID) throws CheckListException {
		try {
			return getCheckListBusManager().getCheckListByID(aCheckListID);
		}
		catch (RemoteException ex) {
			throw new CheckListException(ex.toString());
		}
	}

	public CheckListSearchResult getCheckListByCollateralID(long collateralID) throws CheckListException {
		try {
			return getCheckListBusManager().getCheckListByCollateralID(collateralID);
		}
		catch (RemoteException ex) {
			throw new CheckListException(ex.toString());
		}
	}
	
	
	public CheckListSearchResult getCAMCheckListByCategoryAndProfileID(String category,long aCheckListID) throws CheckListException {
		try {
			return getCheckListBusManager().getCAMCheckListByCategoryAndProfileID(category, aCheckListID);
		}
		catch (RemoteException ex) {
			throw new CheckListException(ex.toString());
		}
	}
	
	public CheckListSearchResult getPariPassuCheckListByCategoryAndProfileID(String category,long aCheckListID) throws CheckListException {
		try {
			return getCheckListBusManager().getPariPassuCheckListByCategoryAndProfileID(category, aCheckListID);
		}
		catch (RemoteException ex) {
			throw new CheckListException(ex.toString());
		}
	}
	/**
	 * Get a checklist by ID
	 * @param monitorForDays - int
	 * @return List - the checklist
	 * @throws CheckListException public List getCheckListItemMonitorList(int
	 *         monitorForDays, String[] statusArray) throws CheckListException {
	 *         try { return
	 *         getCheckListBusManager().getCheckListItemMonitorList(
	 *         monitorForDays, statusArray); } catch(RemoteException ex) { throw
	 *         new CheckListException(ex.toString()); } }
	 */

	/**
	 * Search checklist based on the criteria specified. Currently only used to
	 * search for checklist pending multi-level approval.
	 * 
	 * @param criteria of type CheckListSearchCriteria
	 * @return search result
	 * @throws CheckListException on errors encountered
	 */
	public CheckListSearchResult[] searchCheckList(CheckListSearchCriteria criteria) throws CheckListException {
		SBCheckListBusManager theEjb = getCheckListBusManager();
		try {
			return theEjb.searchCheckList(criteria);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new CheckListException("RemoteException caught!" + e.toString());
		}
	}


    public HashMap[] getDetailsForPreDisbursementReminderLetter(long limitProfileID)
            throws SearchDAOException, CheckListException {
        try {
            return getCheckListBusManager().getDetailsForPreDisbursementReminderLetter(limitProfileID);
        }
        catch (RemoteException ex) {
            throw new CheckListException(ex.toString());
        }
    }

    public HashMap[] getDetailsForPostDisbursementReminderLetter(long limitProfileID)
            throws SearchDAOException, CheckListException {
        try {
            return getCheckListBusManager().getDetailsForPostDisbursementReminderLetter(limitProfileID);
        }
        catch (RemoteException ex) {
            throw new CheckListException(ex.toString());
        }
    }



    /**
	 * To get the remote handler for the checklist bus manager
	 * @return SBCheckListBusManager - the remote handler for the checklist bus
	 *         manager
	 */
	private SBCheckListBusManager getCheckListBusManager() {
		SBCheckListBusManager busmgr = (SBCheckListBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CHECKLIST_BUS_JNDI, SBCheckListBusManagerHome.class.getName());
		return busmgr;
	}
}