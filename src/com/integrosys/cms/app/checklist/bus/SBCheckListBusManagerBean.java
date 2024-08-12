/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/SBCheckListBusManagerBean.java,v 1.73 2006/10/05 02:49:27 hmbao Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.chktemplate.bus.DocumentHeldSearchCriteria;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IPledgor;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;


/**
 * Session bean implementation of the services provided by the checklist bus
 * manager. This will only contains the persistance logic.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.73 $
 * @since $Date: 2006/10/05 02:49:27 $ Tag: $Name: $
 */
public class SBCheckListBusManagerBean extends AbstractCheckListBusManager implements SessionBean {

	private static final long serialVersionUID = -6598690213224621871L;

	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBCheckListBusManagerBean() {
	}

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(javax.ejb.SessionContext sc) {
		_context = sc;
	}

	/**
	 * Get the list of document items
	 * @param monitorForDays
	 * @return List - the result containing the list of document items that
	 *         satisfy the criteria
	 * 
	 *         public List getCheckListItemMonitorList(int monitorForDays,
	 *         String[] statusArray) throws CheckListException { try{ return
	 *         CheckListDAOFactory
	 *         .getCheckListItemMonitorDAO().searchCheckListItemList
	 *         (monitorForDays,statusArray); }catch(SearchDAOException e){ throw
	 *         new CheckListException(e); } }
	 */

	/**
	 * Get Hashtable with borrower ID/pledger ID and the status
	 * @param aLimitProfileID of long type
	 * @return HashMap - this the borrowerID/pledgerID as the key and the status
	 *         as the value
	 * @throws CheckListException
	 * @throws SearchDAOException on errors
	 */
	public HashMap getCCCheckListStatus(long aLimitProfileID, boolean isFullListInd) throws SearchDAOException,
			CheckListException {
		try {
			return getEBCheckListHome().getCCCheckListStatus(aLimitProfileID, isFullListInd);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve full cc checklist for limit profile id ["
					+ aLimitProfileID + "]", ex.getCause());
		}
	}

	
	public HashMap getFacilityCheckListStatus(long aLimitProfileID, boolean isFullListInd) throws SearchDAOException,
	CheckListException {
		try {
			return getEBCheckListHome().getFacilityCheckListStatus(aLimitProfileID, isFullListInd);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve full cc checklist for limit profile id ["
					+ aLimitProfileID + "]", ex.getCause());
		}
		}
	/**
	 * Get Hashtable with borrower ID/pledger ID and the status for deleted
	 * checklist
	 * @param aLimitProfileID of long type
	 * @return CCCheckListSummary[] - this the borrowerID/pledgerID as the key
	 *         and the status as the value
	 * @throws CheckListException
	 * @throws SearchDAOException on errors
	 */
	public CCCheckListSummary[] getDeletedCCCheckListStatus(long aLimitProfileID) throws SearchDAOException,
			CheckListException {
		try {
			return getEBCheckListHome().getCCCheckListList(aLimitProfileID, true);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve deleted full cc checklist for limit profile id ["
					+ aLimitProfileID + "]", ex.getCause());
		}
	}

	public CCCheckListSummary[] getDeletedCCCheckListStatusForNonBorrower(long aCustomerID) throws SearchDAOException,
			CheckListException {
		try {
			return getEBCheckListHome().getCCCheckListListForNonBorrower(aCustomerID, true);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve deleted full cc checklist for non borrower, customer id ["
					+ aCustomerID + "]", ex.getCause());
		}
	}

	public CCCheckListSummary[] getCCCheckListList(long aLimitProfileID) throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getCCCheckListList(aLimitProfileID, false);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve full cc checklist for limit profile id ["
					+ aLimitProfileID + "]", ex.getCause());
		}
	}

	public CCCheckListSummary[] getCCCheckListListForNonBorrower(long aCustomerID) throws SearchDAOException,
			CheckListException {
		try {
			return getEBCheckListHome().getCCCheckListListForNonBorrower(aCustomerID, false);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve full deleted cc checklist for non borrower, customer id ["
					+ aCustomerID + "]", ex.getCause());
		}
	}

	/**
	 * Get Hashtable with borrower ID/pledger ID and the status
	 * @param aCustomerID of long type
	 * @return HashMap - this the borrowerID/pledgerID as the key and the status
	 *         as the value
	 * @throws CheckListException
	 * @throws SearchDAOException on errors
	 */
	public HashMap getCCCheckListStatusForNonBorrower(long aLimitProfileID, long aCustomerID)
			throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getCCCheckListStatusForNonBorrower(aLimitProfileID, aCustomerID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve full deleted cc checklist for non borrower, customer id ["
					+ aCustomerID + "], limit profile id [" + aLimitProfileID + "]", ex.getCause());
		}
	}

	/**
	 * Get the number of limits that a collateral is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @return int - the number of limits that a collateral is related in a
	 *         limit profile
	 * @throws SearchDAOException on errors
	 */
	public int getLimitProfileCollateralCount(long aLimitProfileID, long aCollateralID) throws SearchDAOException,
			CheckListException {
		try {
			return getEBCheckListHome().getLimitProfileCollateralCount(aLimitProfileID, aCollateralID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve collateral count for limit profile id [" + aLimitProfileID
					+ "], collateral id [" + aCollateralID + "]", ex.getCause());
		}
	}

	public int getLimitProfilePledgorCount(long aLimitProfileID, long aPledgorID, long[] aDeletedLimitSecMapList)
			throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getLimitProfilePledgorCount(aLimitProfileID, aPledgorID,
					aDeletedLimitSecMapList);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException(
					"failed to retrieve pledgor count for limit profile id [" + aLimitProfileID + "], pledgor id ["
							+ aPledgorID + "], deleted limit sec map ids [" + aDeletedLimitSecMapList + "]", ex
							.getCause());
		}
	}

	/**
	 * Get the number of limits that a pledgor is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aPledgorID of long type
	 * @return int - the number of limits that a pledgor is related in a limit
	 *         profile
	 * @throws SearchDAOException on errors
	 */
	public int getLimitProfilePledgorCount(long aLimitProfileID, long aPledgorID) throws SearchDAOException,
			CheckListException {
		try {
			return getEBCheckListHome().getLimitProfilePledgorCount(aLimitProfileID, aPledgorID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve pledgor count for limit profile id [" + aLimitProfileID
					+ "], pledgor id [" + aPledgorID + "]", ex.getCause());
		}
	}

	public int getLimitProfileCoBorrowerCount(long aLimitProfileID, long aCustomerID) throws SearchDAOException,
			CheckListException {
		try {
			return getEBCheckListHome().getLimitProfileCoBorrowerCount(aLimitProfileID, aCustomerID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve co borrower count for limit profile id ["
					+ aLimitProfileID + "], customer id [" + aCustomerID + "]", ex.getCause());
		}
	}

	/**
	 * Get Hashtable with collateral ID and the status
	 * @param aLimitProfileID of long type
	 * @return HashMap - this the security ID as the key and the status as the
	 *         value
	 * @throws SearchDAOException on errors
	 */
	public HashMap getCollateralCheckListStatus(long aLimitProfileID) throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getCollateralCheckListStatus(aLimitProfileID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve full collateral checklist for limit profile id ["
					+ aLimitProfileID + "]", ex.getCause());
		}
	}

	/**
	 * Create a checklist
	 * @param anIChecklist - ICheckList
	 * @return ICheckList - the checkList being created
	 * @throws CheckListException;
	 */
	public ICheckList createCheckList(ICheckList anIChecklist) throws CheckListException {
		try {
			if (anIChecklist == null) {
				throw new CheckListException("ICheckList is null!!!");
			}
			EBCheckList remote = getEBCheckListHome().create(anIChecklist);
			remote.createCheckListItems(anIChecklist);
			return remote.getValue();
		}
		catch (CheckListException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (CreateException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to create checklist [" + anIChecklist + "]", ex);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to create checklist [" + anIChecklist + "]", ex.getCause());
		}
	}

	/**
	 * Update a checklist
	 * @param anICheckList - ICheckList
	 * @return ICheckList - the checkList being updated
	 * @throws ConcurrentUpdateException
	 * @throws CheckListException on errors
	 */
	public ICheckList updateCheckList(ICheckList anICheckList) throws ConcurrentUpdateException, CheckListException {
		try {
			if (anICheckList == null) {
				throw new CheckListException("ICheckList is null!!!");
			}
			Long pk = new Long(anICheckList.getCheckListID());
			EBCheckList remoteChkList = getEBCheckListHome().findByPrimaryKey(pk);
			DefaultLogger.debug(this, "<<<>>> Before calling remoteChkList.setValue(anICheckList) method of SBCheckListBusManagerBean.java");
			remoteChkList.setValue(anICheckList);
			DefaultLogger.debug(this, "<<<>>> After calling remoteChkList.setValue(anICheckList) method of SBCheckListBusManagerBean.java");
			return remoteChkList.getValue();
		}
		catch (CheckListException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to find checklist using id [" + anICheckList.getCheckListID() + "]",
					ex);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to update checklist [" + anICheckList + "]", ex.getCause());
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
			EBCheckList remoteChkList = getEBCheckListHome().findByPrimaryKey(new Long(aCheckListID));
			ICheckList checkList = remoteChkList.getValue();
			//DefaultLogger.debug(this, "----------checkList------------------"+checkList);
			ICheckListOwner owner = checkList.getCheckListOwner();
			//DefaultLogger.debug(this, "----------owner------------------"+owner);
			if(!checkList.getCheckListType().equals("F")){
				//DefaultLogger.debug(this, "----------in not F------------------");
				
			if (owner instanceof ICollateralCheckListOwner) {
				//DefaultLogger.debug(this, "----------in ICollateralCheckListOwner------------------");
				ICollateralCheckListOwner colOwner = (ICollateralCheckListOwner) owner;
				
				if(colOwner.getCollateralID()!=0){
					//DefaultLogger.debug(this, "----------colOwner.getCollateralID()------------------"+colOwner.getCollateralID());
					ICollateral col = getCollateral(colOwner.getCollateralID(), false);
					colOwner.setCollateralRef(col.getSCISecurityID());
				}
				// setCollateralOwnerInfo((ICollateralCheckListOwner)owner);
				checkList.setCheckListOwner(colOwner);
			}
			else {
				//DefaultLogger.debug(this, "----------in Else------------------");
				setCCOwnerInfo((ICCCheckListOwner) owner);
			}
			}
			return checkList;
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to find checklist using id [" + aCheckListID + "]", ex);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve checklist using id [" + aCheckListID + "]", ex.getCause());
		}
	}
	
	public CheckListSearchResult getCheckListByCollateralID(long collateralID) throws CheckListException {
		try {
			return getEBCheckListHome().getCheckListByCollateralID(collateralID);
			
			
		}
		
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve checklist using id [" + collateralID + "]", ex.getCause());
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to find checklist using id [" + collateralID + "]", ex);
		}
	}
	
	
	public CheckListSearchResult getCAMCheckListByCategoryAndProfileID(String category,long aCheckListID) throws CheckListException {
		try {
                return getEBCheckListHome().getCAMCheckListByCategoryAndProfileID(category, aCheckListID);
		}
		
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve checklist using id [" + aCheckListID + "]", ex.getCause());
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to find checklist using id [" + aCheckListID + "]", ex);
		}
	}
	
	
	public CheckListSearchResult[] getCheckListByCategory(String category) throws CheckListException {
		try {
                return getEBCheckListHome().getCheckListByCategory(category);
		}
		
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve checklist using id ", ex.getCause());
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to find checklist using id ", ex);
		}
	}

	public CheckListSearchResult getPariPassuCheckListByCategoryAndProfileID(String category,long aCheckListID) throws CheckListException {
		try {
                return getEBCheckListHome().getPariPassuCheckListByCategoryAndProfileID(category, aCheckListID);
		}
		
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve checklist using id [" + aCheckListID + "]", ex.getCause());
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to find checklist using id [" + aCheckListID + "]", ex);
		}
	}

	/**
	 * Set the checklist owner information that is specific to the CC checklist
	 * @param anICCCheckListOwner of ICCCheckListOwner type
	 * @throws CheckListException on errors
	 */
	protected void setCCOwnerInfo(ICCCheckListOwner anICCCheckListOwner) throws CheckListException {
		if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(anICCCheckListOwner.getSubOwnerType())
				|| ICMSConstant.CHECKLIST_CO_BORROWER.equals(anICCCheckListOwner.getSubOwnerType())
				|| ICMSConstant.CHECKLIST_NON_BORROWER.equals(anICCCheckListOwner.getSubOwnerType())
				|| ICMSConstant.CHECKLIST_JOINT_BORROWER.equals(anICCCheckListOwner.getSubOwnerType())) {
			setBorrowerInfo(anICCCheckListOwner);
		}
		else if (ICMSConstant.CHECKLIST_PLEDGER.equals(anICCCheckListOwner.getSubOwnerType())) {
			setPledgorInfo(anICCCheckListOwner);
		}
		return;
	}

	protected void setCollateralOwnerInfo(ICollateralCheckListOwner checkListOwner) throws CheckListException {
		if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(checkListOwner.getSubOwnerType())
				|| ICMSConstant.CHECKLIST_CO_BORROWER.equals(checkListOwner.getSubOwnerType())
				|| ICMSConstant.CHECKLIST_JOINT_BORROWER.equals(checkListOwner.getSubOwnerType())) {
			setBorrowerInfo(checkListOwner);
		}
	}

	private void setBorrowerInfo(ICCCheckListOwner anICCCheckListOwner) throws CheckListException {
		setBorrowerInfo((ICheckListOwner) anICCCheckListOwner);
	}

	private void setBorrowerInfo(ICheckListOwner anICCCheckListOwner) throws CheckListException {
		try {
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer customer = custProxy.getCustomer(anICCCheckListOwner.getSubOwnerID());
			anICCCheckListOwner.setSubOwnerReference(customer.getCustomerReference());
			anICCCheckListOwner.setLegalReference(customer.getCMSLegalEntity().getLEReference());
			anICCCheckListOwner.setLegalName(customer.getCMSLegalEntity().getLegalName());
		}
		catch (CustomerException ex) {
			throw new CheckListException("failed to retieve customer using id [" + anICCCheckListOwner.getSubOwnerID()
					+ "]", ex);
		}
	}

	private void setPledgorInfo(ICCCheckListOwner anICCCheckListOwner) throws CheckListException {
		try {
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			IPledgor pledgor = proxy.getPledgor(anICCCheckListOwner.getSubOwnerID());
			long pledgor_ref = pledgor.getSysGenPledgorID();
			if (pledgor_ref > 0) {
				anICCCheckListOwner.setSubOwnerReference(String.valueOf(pledgor_ref));
			}
			String pledgor_leID = pledgor.getLegalID();
			if (pledgor_leID != null) {
				anICCCheckListOwner.setLegalReference(pledgor_leID);
			}
			anICCCheckListOwner.setLegalName(pledgor.getPledgorName());
		}
		catch (CollateralException ex) {
			throw new CheckListException("failed to retieve pledgor using id [" + anICCCheckListOwner.getSubOwnerID()
					+ "]", ex);
		}
	}

	/**
	 * Get the list of allowable operation for the checklist items
	 * @return ICheckListItemOperation[] - the list of allowed checklist item
	 *         operations
	 * @throws SearchDAOException on DAO errors
	 * @throws CheckListException on errors
	 */
	protected ICheckListItemOperation[] getCheckListItemOperation() throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getCheckListItemOperation();
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve checklist item operations", ex.getCause());
		}
	}

	/**
	 * Update the checklist item status by the checklist item ID
	 * @param aCheckListItemID of long type
	 * @param aStatus of String type
	 * @return ICheckListItem - the checklist item updated
	 * @throws CheckListException on errors
	 */
	public ICheckListItem updateCheckListItemStatusByID(long aCheckListItemID, String aStatus)
			throws CheckListException {
		try {
			EBCheckListItemLocal local = getEBCheckListItemLocalHome().findByPrimaryKey(new Long(aCheckListItemID));
			local.setItemStatus(aStatus);
			return local.getValue();
		}
		catch (FinderException ex) {
			throw new CheckListException("failed to retrieve checklist item using id [" + aCheckListItemID + "]", ex);
		}
	}

	/**
	 * Get a checklist item by the item reference
	 * @param aCheckListItemRef of long type
	 * @return ICheckListItem - the checklist item with the checklist item
	 *         reference
	 * @throws CheckListException
	 */
	public ICheckListItem getCheckListItemByRef(long aCheckListItemRef) throws CheckListException {
		try {
			EBCheckListItemLocal local = getEBCheckListItemLocalHome().findByCheckListItemRef(aCheckListItemRef);
			return local.getValue();
		}
		catch (FinderException ex) {
			throw new CheckListException("failed to retrieve checklist item using ref [" + aCheckListItemRef + "]", ex);
		}
	}

	/**
	 * Update the checklist item status by the checklist item ref
	 * @param aCheckListItemRef of long type
	 * @param aStatus of String type
	 * @return ICheckListItem - the checklist item updated
	 * @throws CheckListException on errors
	 */
	public ICheckListItem updateCheckListItemStatusByRef(long aCheckListItemRef, String aStatus)
			throws CheckListException {
		try {
			EBCheckListItemLocal local = getEBCheckListItemLocalHome().findByCheckListItemRef(aCheckListItemRef);
			local.setItemStatus(aStatus);
			return local.getValue();
		}
		catch (FinderException ex) {
			throw new CheckListException("failed to retrieve checklist item using ref [" + aCheckListItemRef + "]", ex);
		}
	}

	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param anICheckListOwner of ICheckListOwner
	 * @param aStatusList of String[] type
	 * @return CheckListSearchResult[] - the list of checklist result
	 * @throws SearchDAOException
	 * @throws CheckListException
	 */
	public CheckListSearchResult[] getCheckList(ICheckListOwner anICheckListOwner, String[] aStatusList)
			throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getCheckList(anICheckListOwner, aStatusList);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve checklists using owner info [" + anICheckListOwner
					+ "], and status [" + Arrays.asList(aStatusList) + "]", ex.getCause());
		}
	}

	/**
	 * Get the collateral checklist using the limit profile ID
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @return ICheckList - the collateral checklist
	 * @throws CheckListException on errors
	 */
	public ICheckList getCollateralCheckList(long aLimitProfileID, long aCollateralID) throws CheckListException {
		try {
			EBCheckListHome home = getEBCheckListHome();
			EBCheckList remote = home.findByLimitProfileIDAndCollateralID(new Long(aLimitProfileID), new Long(
					aCollateralID));
			return remote.getValue();
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to find checklist by limit profile id [" + aLimitProfileID
					+ "] and collateral id + [" + aCollateralID + "]", ex);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve checklist by limit profile id [" + aLimitProfileID
					+ "] and collateral id + [" + aCollateralID + "]", ex.getCause());
		}
	}

	/**
	 * Get the checklist item for waiver/deferral generation
	 * @param aLimitProfileID of long type
	 * @param anItemStatus of String type
	 * @return HashMap - the checkListID and the list of checklist item (not the
	 *         full detail)
	 * @throws SearchDAOException
	 */
	public HashMap getCheckListItemListbyStatus(long aLimitProfileID, String anItemStatus) throws SearchDAOException,
			CheckListException {
		try {
			return getEBCheckListHome().getCheckListItemListbyStatus(aLimitProfileID, anItemStatus);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve checklist item by limit profile id [" + aLimitProfileID
					+ "] and status + [" + anItemStatus + "]", ex.getCause());
		}
	}

    /**
     * Get the checklist item by checklist category
     *
     * @param aLimitProfileID    of long type
     * @param aCheckListCategory of String type
     * @return HashMap - the checkListID and the list of checklist item (not the full detail)
     * @throws SearchDAOException
     */
    public HashMap getCheckListItemListbyCategory(long aLimitProfileID, String aCheckListCategory) throws SearchDAOException,
            CheckListException {
        try {
            return getEBCheckListHome().getCheckListItemListbyCategory(aLimitProfileID, aCheckListCategory);
        }
        catch (RemoteException ex) {
            _context.setRollbackOnly();
            throw new CheckListException("Failed to retrieve checklist item by limit profile id [" + aLimitProfileID + "] and checklist category [" + aCheckListCategory + "]", ex.getCause());
		}
	}

	/**
	 * Get the checklist item for waiver/deferral generation for non borrower
	 * @param aCustomerID of long type
	 * @param anItemStatus of String type
	 * @return HashMap - the checkListID and the list of checklist item (not the
	 *         full detail)
	 * @throws SearchDAOException
	 */
	public HashMap getCheckListItemListbyStatusForNonBorrower(long aCustomerID, String anItemStatus)
			throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getCheckListItemListbyStatusForNonBorrower(aCustomerID, anItemStatus);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve checklist item by customer id [" + aCustomerID
					+ "] and status + [" + anItemStatus + "]", ex.getCause());
		}
	}

	/**
	 * Get the list of checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param aStatusList of String[] type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 */
	public ICheckListAudit[] getCheckListAuditList(long aLimitProfileID, String[] aStatusList)
			throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getCheckListAuditList(aLimitProfileID, aStatusList);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve audit checklist by limit profile id [" + aLimitProfileID
					+ "] and status + [" + Arrays.asList(aStatusList) + "]", ex.getCause());
		}
	}

	/**
	 * Get the list of Checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param aStatusList of String[] type
	 * @return IAuditItem[] - the list of checklist items that requires auditing
	 * @throws SearchDAOException on errors
	 */
	public IAuditItem[] getCheckListItemListForAudit(long aLimitProfileID, String[] aStatusList)
			throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getCheckListItemListForAudit(aLimitProfileID, aStatusList);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve audit checklist items by limit profile id ["
					+ aLimitProfileID + "] and status + [" + Arrays.asList(aStatusList) + "]", ex.getCause());
		}
	}

	/**
	 * Get the list of checklist items that requires auditing
	 * @param aCustomerID of long type
	 * @param aStatusList of String[] type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 */
	public ICheckListAudit[] getCheckListAuditListForNonBorrower(long aCustomerID, String[] aStatusList)
			throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getCheckListAuditListForNonBorrower(aCustomerID, aStatusList);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve audit checklist by customer id [" + aCustomerID
					+ "] and status + [" + Arrays.asList(aStatusList) + "]", ex.getCause());
		}
	}

	/**
	 * Get the list of Checklist items that requires auditing for non borrower
	 * @param aCustomerID of long type
	 * @param aStatusList of String[] type
	 * @return IAuditItem[] - the list of checklist items that requires auditing
	 * @throws SearchDAOException on errors
	 */
	public IAuditItem[] getCheckListItemListForAuditForNonBorrower(long aCustomerID, String[] aStatusList)
			throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getCheckListItemListForAuditForNonBorrower(aCustomerID, aStatusList);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve audit checklist items by customer id [" + aCustomerID
					+ "] and status + [" + Arrays.asList(aStatusList) + "]", ex.getCause());
		}
	}

	/**
	 * Get the document categories for this borrower.
	 * @param aLimitProfileID of long type
	 * @return HashMap - the list document categories
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 */
	public HashMap getDocumentCategories(long aLimitProfileID) throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getDocumentCategories(aLimitProfileID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve document categories by limit profile id ["
					+ aLimitProfileID + "]", ex.getCause());
		}
	}

	/**
	 * Get the document categories for this non-borrower.
	 * @param aCustomerID of long type
	 * @return HashMap - the list document categories
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 */
	public HashMap getDocumentCategoriesForNonBorrower(long aCustomerID, long aLimitProfileID)
			throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getDocumentCategoriesForNonBorrower(aCustomerID, aLimitProfileID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve document categories by customer id [" + aCustomerID
					+ "] and limit profile id [" + aLimitProfileID + "]", ex.getCause());
		}
	}

	/**
	 * Get the map of documents held given the search criteria.
	 * 
	 * @param criteria - DocumentHeldSearchCriteria
	 * @return HashMap - Map of list of document held fulfilling the criteria
	 * @throws SearchDAOException on errors
	 */
	public HashMap getDocumentsHeld(DocumentHeldSearchCriteria criteria) throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getDocumentsHeld(criteria);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve document held by criteria [" + criteria + "]", ex
					.getCause());
		}
	}

	/**
	 * Get the securities pledged for this borrower type.
	 * @param aLimitProfileID the limit profile identifier
	 * @param pledgorID the pledgor identifier
	 * @return HashMap - the securities pledged for this customer
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 */
	public HashMap getSecuritiesPledged(long aLimitProfileID, long pledgorID) throws SearchDAOException,
			CheckListException {
		try {
			return getEBCheckListHome().getSecuritiesPledged(aLimitProfileID, pledgorID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve securities pledged held by limit profile id ["
					+ aLimitProfileID + "] and pledgor id [" + pledgorID + "]", ex.getCause());
		}
	}

	protected ICollateral getCollateral(long aCollateralID, boolean includeDetails) throws CheckListException {
		try {
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			return proxy.getCollateral(aCollateralID, includeDetails);
		}
		catch (CollateralException ex) {
			throw new CheckListException("failed to retrieve collateral using collateral id [" + aCollateralID + "]",
					ex);
		}
	}

	/**
	 * To get the home handler for the checklist Entity Bean
	 * @return EBCheckListHome - the home handler for the checklist entity bean
	 */
	protected EBCheckListHome getEBCheckListHome() {
		EBCheckListHome ejbHome = (EBCheckListHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_CHECKLIST_JNDI,
				EBCheckListHome.class.getName());
		return ejbHome;
	}

	/**
	 * To get the local home handler for the checklist Entity Bean
	 * @return EBCheckListHome - the home handler for the checklist entity bean
	 */
	protected EBCheckListLocalHome getEBCheckListLocalHome() {
		EBCheckListLocalHome ejbHome = (EBCheckListLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CHECKLIST_LOCAL_JNDI, EBCheckListLocalHome.class.getName());
		return ejbHome;
	}

	/**
	 * Method to get EB Local Home for checkList item
	 */
	protected EBCheckListItemLocalHome getEBCheckListItemLocalHome() throws CheckListException {
		EBCheckListItemLocalHome home = (EBCheckListItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CHECKLIST_ITEM_LOCAL_JNDI, EBCheckListItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListException("EBCheckListItemLocal is null!");
	}

	/**
	 * FOR CR CMS-662 Get all CHECKLIST related to a SECURITY based on
	 * CMS_COLLATERAL_ID.
	 * @param aCollateralID of long type
	 * @return int - the number of checklist related to security.
	 * @throws SearchDAOException , CheckListException on errors
	 */
	public int getSecurityChkListCount(long aCollateralID) throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getSecurityChkListCount(aCollateralID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve collateral checklist count for collateral id ["
					+ aCollateralID + "]", ex.getCause());
		}
	}

	/**
	 * FOR CR CMS-310 Get the list of checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param asOfDate of String type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 */
	public ICheckListAudit[] getCheckListAudit(long aLimitProfileID, String asOfDate) throws SearchDAOException,
			CheckListException {
		try {
			return getEBCheckListHome().getCheckListAudit(aLimitProfileID, asOfDate);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve audit checklist by limit profile id [" + aLimitProfileID
					+ "], as of date [" + asOfDate + "]", ex.getCause());
		}
	}

	/**
	 * FOR CR CMS-310 Get the list of checklist items that requires auditing
	 * @param aCustomerID of long type
	 * @param asOfDate of String type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 */
	public ICheckListAudit[] getCheckListAuditForNonBorrower(long aCustomerID, String asOfDate)
			throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getCheckListAuditForNonBorrower(aCustomerID, asOfDate);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve audit checklist for customer, by customer id ["
					+ aCustomerID + "], as of date [" + asOfDate + "]", ex.getCause());
		}
	}

	/**
	 * Get the checklist ID of those pledgor checklist that is no longer valid
	 * due to this coborrower
	 * @param aCoBorrowerLegalRef of long type
	 * @param aLimitProfileRef of long type
	 * @return Long[] - the list of checklist IDs of those pledgor checklist
	 *         affected
	 * @throws SearchDAOException , CheckListException on error
	 */
	public Long[] getAffectedPledgorCheckList(long aCoBorrowerLegalRef, long aLimitProfileRef)
			throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getAffectedPledgorCheckList(aCoBorrowerLegalRef, aLimitProfileRef);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException(
					"failed to retrieve affected pledgor checklist ids for co borrower legal ref ["
							+ aCoBorrowerLegalRef + "], and limit profile ref [" + aLimitProfileRef + "]", ex
							.getCause());
		}
	}

	/**
	 * Get the limit profile IDs that are linked to this pledgor
	 * @param aPledgorLegalRef of long type
	 * @return Long[] - the list of limit profile IDs linked to this pledgor
	 * @throws SearchDAOException , CheckListException on error
	 */
	public Long[] getAffectedLimitProfileID(long aPledgorLegalRef) throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getAffectedLimitProfileID(aPledgorLegalRef);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("failed to retrieve affected limit profile ids for pledgor legal ref ["
					+ aPledgorLegalRef + "]", ex.getCause());
		}
	}

	/**
	 * Search checklist based on the criteria specified. Currently only used to
	 * search for checklist pending multi-level approval.
	 * 
	 * @param criteria of type CheckListSearchCriteria
	 * @return CheckListSearchResult[]
	 * @throws CheckListException on errors encountered
	 */
	public CheckListSearchResult[] searchCheckList(CheckListSearchCriteria criteria) throws CheckListException {
		try {
			EBCheckListLocalHome ejbHome = getEBCheckListLocalHome();
			return ejbHome.searchCheckList(criteria);
		}
		catch (SearchDAOException ex) {
			throw new CheckListException("failed to search checklist using criteria [" + criteria + "]", ex);
		}
	}

	public List getCheckListDetailsByCheckListId(String[] aCheckListId, String categoryType) throws SearchDAOException,
			CheckListException {
		try {
			return getEBCheckListHome().getCheckListDetailsByCheckListId(aCheckListId, categoryType);
		}
		catch (RemoteException ex) {
			throw new CheckListException("failed to retrieve checklist details by checklist ids ["
					+ Arrays.asList(aCheckListId) + "] and category [" + categoryType + "]", ex.getCause());
		}

	}

	public List getCheckListDetailsByCheckListId(String[] aCheckListId) throws SearchDAOException, CheckListException {
		try {
			return getEBCheckListHome().getCheckListDetailsByCheckListId(aCheckListId);
		}
		catch (RemoteException ex) {
			throw new CheckListException("failed to retrieve checklist details by checklist ids ["
					+ Arrays.asList(aCheckListId) + "]", ex.getCause());
		}
	}

	public HashMap[] getDetailsForPreDisbursementReminderLetter(long limitProfileID) throws SearchDAOException,
			CheckListException {
		return CheckListDAOFactory.getCheckListDAO().getDetailsForPreDisbursementReminderLetter(limitProfileID);
	}

	public HashMap[] getDetailsForPostDisbursementReminderLetter(long limitProfileID) throws SearchDAOException,
			CheckListException {
		return CheckListDAOFactory.getCheckListDAO().getDetailsForPostDisbursementReminderLetter(limitProfileID);
	}

	protected EBDocumentshareLocalHome getEBDocumentshareLocalHome() throws CheckListException {
		EBDocumentshareLocalHome home = (EBDocumentshareLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CHECKLIST_DOCUMENT_SHARE_LOCAL_JNDI, EBDocumentshareLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListException("EBDocumentshareLocalHome is null!");
	}

	public List getCustomerListByCollateralID(long collateralID) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getMutiCustomerListByCollateralID(collateralID);
	}

	public List getSecurityOwnerList(long collateralID, long lmtProfileID) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getSecurityOwnerList(collateralID, lmtProfileID);
	}
	/**
	 * Added by Anil
	 * Get a checklist item by the item id
	 * @param checkListItemId of long type
	 * @return ICheckListItem - the checklist item with the checklist item id
	 *         reference
	 * @throws CheckListException
	 */
	public ICheckListItem getCheckListItemById(long checkListItemId) throws CheckListException {
		try {
			EBCheckListItemLocal local = getEBCheckListItemLocalHome().findByPrimaryKey(new Long(checkListItemId));
			return local.getValue();
		}
		catch (FinderException ex) {
			throw new CheckListException("failed to retrieve checklist item using ref [" + checkListItemId + "]", ex);
		}
	}
}
