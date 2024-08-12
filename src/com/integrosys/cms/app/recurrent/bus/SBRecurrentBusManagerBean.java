package com.integrosys.cms.app.recurrent.bus;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;

/**
 * Session bean implementation of the services provided by the checklist bus
 * manager. This will only contains the persistance logic.
 *
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.73 $
 * @since $Date: 2006/10/05 02:49:27 $ Tag: $Name: $
 */
public class SBRecurrentBusManagerBean extends AbstractRecurrentBusManager implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBRecurrentBusManagerBean() {
	}

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(SessionContext sc) {
		_context = sc;
	}


	/**
	 * Get a recurrent checklist by ID
	 * @param aCheckListID - long
	 * @return IRecurrentCheckList - the recurrent checklist
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException
	 */
	public IRecurrentCheckList getRecurrentCheckListByID(long aCheckListID) throws RecurrentException {
		try {
			EBRecurrentCheckList remoteChkList = getEBRecurrentCheckListHome().findByPrimaryKey(new Long(aCheckListID));
			return remoteChkList.getValue();
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("FinderException enctr in getRecurrentCheckListByID " + aCheckListID + ": "
					+ ex.toString());
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("RemoteException enctr in getRecurrentCheckListByID " + aCheckListID + ": "
					+ ex.toString());
		}
	}

	/**
	 * Get the recurrent checklist using the limit profile ID
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @return IRecurrentCheckList - the recurrent checklist of the limit
	 *         profile
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckList getRecurrentCheckList(long aLimitProfileID, long aSubProfileID)
			throws RecurrentException {
		try {
			EBRecurrentCheckList remote = getEBRecurrentCheckListHome().findByLimitProfileAndSubProfile(
					new Long(aLimitProfileID), new Long(aSubProfileID));
			return remote.getValue();
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			DefaultLogger.error(this, "FinderException in getRecurrentCheckList", ex);
			return null;
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("RemoteException enctr at getRecurrentCheckList: " + ex.toString());
		}
	}

	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @param aStatusList of String[] type
	 * @return RecurrentSearchResult[] - the list of checklist result
	 * @throws com.integrosys.base.businfra.search.SearchDAOException
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException
	 */
	public RecurrentSearchResult[] getRecurrentCheckList(long aLimitProfileID, long aSubProfileID, String[] aStatusList)
			throws SearchDAOException, RecurrentException {
		try {
			return getEBRecurrentCheckListHome().getCheckList(aLimitProfileID, aSubProfileID, aStatusList);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("RemoteException enctr at getRecurrentCheckList: " + ex.toString());
		}
	}
	
	
	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @param aStatusList of String[] type
	 * @return RecurrentSearchResult[] - the list of checklist result
	 * @throws com.integrosys.base.businfra.search.SearchDAOException
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException
	 */
	public RecurrentSearchResult[] getRecurrentCheckListByAnnexureId(long aLimitProfileID, long aSubProfileID, String[] aStatusList,String annexureId)
			throws SearchDAOException, RecurrentException {
		try {
			return getEBRecurrentCheckListHome().getCheckListByAnnexureId(aLimitProfileID, aSubProfileID, aStatusList, annexureId);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("RemoteException enctr at getRecurrentCheckList: " + ex.toString());
		}
	}

	/**
	 * Create a recurrent checklist
	 * @param anIRecurrentCheckList of IRecurrentCheckList type
	 * @return IRecurrentCheckList - the recurrent checkList being created
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException
	 */
	public IRecurrentCheckList create(IRecurrentCheckList anIRecurrentCheckList) throws RecurrentException {
		try {
			if (anIRecurrentCheckList == null) {
				throw new RecurrentException("IRecurrentCheckList is null!!!");
			}
			EBRecurrentCheckList remote = getEBRecurrentCheckListHome().create(anIRecurrentCheckList);
			remote.createDependents(anIRecurrentCheckList);
			return remote.getValue();
		}
		catch (RecurrentException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (CreateException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("CreateException enctr in create: " + ex.toString());
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("RemoteException enctr in create: " + ex.toString());
		}
	}

	/**
	 * Update a recurrent checklist
	 * @param anIRecurrentCheckList of IRecurrentCheckList tyoe
	 * @return IRecurrentCheckList - the recurrent checkList being updated
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException
	 */
	protected IRecurrentCheckList updateCheckList(IRecurrentCheckList anIRecurrentCheckList)
			throws ConcurrentUpdateException, RecurrentException {
		try {
			if (anIRecurrentCheckList == null) {
				throw new RecurrentException("IRecurrentCheckList is null!!!");
			}
			Long pk = new Long(anIRecurrentCheckList.getCheckListID());
			EBRecurrentCheckList remoteChkList = getEBRecurrentCheckListHome().findByPrimaryKey(pk);
			remoteChkList.setValue(anIRecurrentCheckList);
			return remoteChkList.getValue();
		}
		catch (RecurrentException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("FinderException enctr at update " + anIRecurrentCheckList.getCheckListID()
					+ ": " + ex.toString());
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("RemoteException enctr at update: " + ex.toString());
		}
	}

	/**
	 * To get the list of recurrent checklist item history based on the item
	 * reference
	 * @param anItemReference of long type
	 * @return IRecurrentCheckListItem[] - the list of recurrent checklist items
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListItem[] getRecurrentItemHistory(long anItemReference) throws SearchDAOException,
			RecurrentException {
		try {
			return getEBRecurrentCheckListHome().getRecurrentItemHistory(anItemReference);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("RemoteException enctr at getRecurrentItemHistory: " + ex.toString());
		}
	}

	// cr 26
	public IConvenant[] getConvenantHistory(long anItemReference) throws SearchDAOException, RecurrentException {
		try {
			return getEBRecurrentCheckListHome().getConvenantHistory(anItemReference);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("RemoteException enctr at getRecurrentItemHistory: " + ex.toString());
		}
	}

	public IRecurrentCheckListItem getRecurrentCheckListItemByRef(long anItemRef) throws RecurrentException {
		try {
			// EBRecurrentCheckListItemLocal local =
			// getEBRecurrentCheckListItemHome
			// ().findByCheckListItemRef(anItemRef);
			// return local.getValue();
			return getEBRecurrentCheckListHome().getRecurrentCheckListItem(anItemRef);
		}
		catch (SearchDAOException ex) {
			throw new RecurrentException("Exception in getRecurrentCheckListItemByRef", ex);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("RemoteException enctr at getRecurrentCheckListItemByRef: " + ex.toString());
		}
	}

	// cr 26
	public IConvenant getConvenantByRef(long anItemRef) throws RecurrentException {
		try {
			// EBRecurrentCheckListItemLocal local =
			// getEBRecurrentCheckListItemHome
			// ().findByCheckListItemRef(anItemRef);
			// return local.getValue();
			return getEBRecurrentCheckListHome().getConvenant(anItemRef);
		}
		catch (SearchDAOException ex) {
			throw new RecurrentException("Exception in getConvenantByRef", ex);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("RemoteException enctr at getConvenantByRef: " + ex.toString());
		}
	}

	public List createRecurrentCheckListSubItem(long anItemID, List aCreateList) throws RecurrentException {
		try {
			EBRecurrentCheckListItemLocal local = getEBRecurrentCheckListItemHome()
					.findByPrimaryKey(new Long(anItemID));
			IRecurrentCheckListItem aRecItem = local.getValue();
			IRecurrentCheckListSubItem[] aPendingSubItemList = aRecItem
					.getSubItemsByCondition(ICMSConstant.RECCOV_SUB_ITEM_COND_PENDING);
			boolean isItemNoPendingSubItems = (aPendingSubItemList.length == 0);
			List createdSubItemsList = local.createSubItems(aCreateList);

			if (isItemNoPendingSubItems) {
				aRecItem = local.getValue();
				aPendingSubItemList = aRecItem.getSubItemsByCondition(ICMSConstant.RECCOV_SUB_ITEM_COND_PENDING);
				DefaultLogger.debug(this,
						">>>>>>>> createRecurrentCheckListSubItem() <<<<<<<< initial end date changed from "
								+ aRecItem.getInitialDocEndDate() + " to " + aPendingSubItemList[0].getDocEndDate());
				aRecItem.setInitialDocEndDate(aPendingSubItemList[0].getDocEndDate());
				aRecItem.setInitialDueDate(aPendingSubItemList[0].getDueDate());
				local.setValue(aRecItem);
			}

			return createdSubItemsList;
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("RemoteException enctr at createRecurrentCheckListSubItem: ", ex);
		}
	}

	// cr 26
	public List createConvenantSubItem(long anItemID, List aCreateList) throws RecurrentException {
		try {
			EBConvenantLocal local = getEBConvenantHome().findByPrimaryKey(new Long(anItemID));
			IConvenant aCovItem = local.getValue();
			IConvenantSubItem[] aPendingSubItemList = aCovItem
					.getSubItemsByCondition(ICMSConstant.RECCOV_SUB_ITEM_COND_PENDING);
			boolean isItemNoPendingSubItems = (aPendingSubItemList.length == 0);
			List createdSubItemsList = local.createSubItems(aCreateList);

			if (isItemNoPendingSubItems) {
				aCovItem = local.getValue();
				aPendingSubItemList = aCovItem.getSubItemsByCondition(ICMSConstant.RECCOV_SUB_ITEM_COND_PENDING);
				DefaultLogger.debug(this, ">>>>>>>> createConvenantSubItem() <<<<<<<< initial end date changed from "
						+ aCovItem.getInitialDocEndDate() + " to " + aPendingSubItemList[0].getDocEndDate());
				aCovItem.setInitialDocEndDate(aPendingSubItemList[0].getDocEndDate());
				aCovItem.setInitialDueDate(aPendingSubItemList[0].getDueDate());
				local.setValue(aCovItem);
			}

			return createdSubItemsList;
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("RemoteException enctr at createConvenantSubItem: ", ex);
		}
	}


	/**
	 * To get the home handler for the recurrent checklist Entity Bean
	 * @return EBRecurrentCheckListHome - the home handler for the checklist
	 *         entity bean
	 */
	protected EBRecurrentCheckListHome getEBRecurrentCheckListHome() {
		EBRecurrentCheckListHome ejbHome = (EBRecurrentCheckListHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_RECURRENT_CHECKLIST_JNDI, EBRecurrentCheckListHome.class.getName());
		return ejbHome;
	}

	/**
	 * To get the home handler for the recurrent checklist Entity Bean
	 * @return EBRecurrentCheckListHome - the home handler for the checklist
	 *         entity bean
	 */
	protected EBRecurrentCheckListItemLocalHome getEBRecurrentCheckListItemHome() throws RecurrentException {
		EBRecurrentCheckListItemLocalHome home = (EBRecurrentCheckListItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_RECURRENT_CHECKLIST_ITEM_LOCAL_JNDI, EBRecurrentCheckListItemLocalHome.class
						.getName());
		if (home != null) {
			return home;
		}
		throw new RecurrentException("EBRecurrentCheckListItemLocal is null!");
	}

	// cr 26
	/**
	 * To get the home handler for the recurrent checklist Entity Bean
	 * @return EBConvenantHome - the home handler for the checklist entity bean
	 */
	protected EBConvenantLocalHome getEBConvenantHome() throws RecurrentException {
		EBConvenantLocalHome home = (EBConvenantLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CONVENANT_LOCAL_JNDI, EBConvenantLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new RecurrentException("EBConvenantLocal is null!");
	}

	// cr 26
	/**
	 * To get the home handler for the recurrent checklist Entity Bean
	 * @return EBConvenantSubItemLocalHome - the home handler for the checklist
	 *         entity bean
	 */
	protected EBConvenantSubItemLocalHome getEBConvenantSubItemHome() throws RecurrentException {
		EBConvenantSubItemLocalHome home = (EBConvenantSubItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CONVENANT_SUB_ITEM_LOCAL_JNDI, EBConvenantSubItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new RecurrentException("EBConvenantSubItemLocal is null!");
	}

	/**
	 * To get the home handler for the recurrent checklist Entity Bean
	 * @return EBRecurrentCheckListHome - the home handler for the checklist
	 *         entity bean
	 */
	protected EBRecurrentCheckListSubItemLocalHome getEBRecurrentCheckListSubItemHome() {
		EBRecurrentCheckListSubItemLocalHome ejbHome = (EBRecurrentCheckListSubItemLocalHome) BeanController
				.getEJBHome(ICMSJNDIConstant.EB_RECURRENT_CHECKLIST_SUB_ITEM_LOCAL_JNDI,
						EBRecurrentCheckListSubItemLocalHome.class.getName());
		return ejbHome;
	}

	
	public long getRecurrentDocId(long aLimitProfileID, long aSubProfileID) throws SearchDAOException, RecurrentException{
		try{
			return RecurrentDAOFactory.getRecurrentCheckListDAO().getRecurrentDocId(aLimitProfileID, aSubProfileID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("RemoteException enctr at getRecurrentCheckList: " + ex.toString());
		}	
	}
	
	public String getBankingType(long aLimitProfileID, long aSubProfileID) throws SearchDAOException, RecurrentException{
		try{
			return RecurrentDAOFactory.getRecurrentCheckListDAO().getBankingType(aLimitProfileID, aSubProfileID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("RemoteException enctr at getRecurrentCheckList: " + ex.toString());
		}	
	}
	
	public void insertAnnexures(ILimitProfile aLimitProfile)
		throws RecurrentException,RemoteException {
	
		RecurrentDAOFactory.getRecurrentCheckListDAO().insertAnnexures(aLimitProfile);
	}
}
