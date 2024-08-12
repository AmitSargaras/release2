/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/proxy/SBCheckListProxyManagerBean.java,v 1.65 2006/10/09 09:52:11 pratheepa Exp $
 */
package com.integrosys.cms.app.checklist.proxy;

//java
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.CAMCheckListSummary;
import com.integrosys.cms.app.checklist.bus.CCCheckListSummary;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.IAuditItem;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListAudit;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICheckListItemOperation;
import com.integrosys.cms.app.checklist.bus.ICheckListOwner;
import com.integrosys.cms.app.checklist.bus.PariPassuCheckListSummary;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManager;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManagerHome;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.DocumentHeldSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.IDocumentHeld;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.bus.ICCTask;
import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.collaborationtask.bus.SBCollaborationTaskBusManager;
import com.integrosys.cms.app.collaborationtask.bus.SBCollaborationTaskBusManagerHome;
import com.integrosys.cms.app.collaborationtask.proxy.CollaborationTaskProxyManagerFactory;
import com.integrosys.cms.app.collaborationtask.proxy.ICollaborationTaskProxyManager;
import com.integrosys.cms.app.collaborationtask.trx.ICCTaskTrxValue;
import com.integrosys.cms.app.collaborationtask.trx.ICollateralTaskTrxValue;
import com.integrosys.cms.app.common.IContext;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.ui.checklist.CheckListUtil;

/**
 * Session bean implementation of the services provided by the checklist proxy
 * manager. This will only contains the persistance logic.
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.65 $
 * @since $Date: 2006/10/09 09:52:11 $ Tag: $Name: $
 */
public class SBCheckListProxyManagerBean extends AbstractCheckListProxyManager implements SessionBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7190638135707305388L;

	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBCheckListProxyManagerBean() {
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
	 * Formulate the default checklist from the template selected based on the
	 * law, legal constitution and country
	 * @param anICheckListOwner - ICheckListOwner
	 * @param aLegalConstitution - String
	 * @param anIBookingLocation - IBookingLocation
	 * @return ICheckList - the default checklist
	 * @throws TemplateNotSetupException if there is no template setup for the
	 *         checklist
	 * @throws CheckListException on errors
	 */
	public ICheckList getDefaultCCCheckList(ICheckListOwner anICheckListOwner, String aLegalConstitution,
			IBookingLocation anIBookingLocation, String law) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException {
		try {
			return getSBCheckListBusManager().getDefaultCCCheckList(anICheckListOwner, aLegalConstitution,
					anIBookingLocation, law);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getDefaultCCCheckList: ", ex.getCause());
		}
	}

	public ICheckList getDefaultCCCheckList(String customerType, String legalConstitution, String country, String law)
			throws TemplateNotSetupException, CheckListTemplateException, CheckListException {
		try {
			return getSBCheckListBusManager().getDefaultCCCheckList(customerType, legalConstitution, country, law);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getDefaultCCCheckList: ", ex.getCause());
		}
	}

	/**
	 * Formulate the default checklist from the template selected based on the
	 * collateral type, collateral subtype and country
	 * @param anICheckListOwner - ICheckListOwner
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @param aCountry - String
	 * @return ICheckList - the default checklist
	 * @throws TemplateNotSetupException if there is no template setup for the
	 *         checklist
	 * @throws CheckListException on errors
	 */
	public ICheckList getDefaultCollateralCheckList(ICheckListOwner anICheckListOwner, String aCollateralType,
			String aCollateralSubType, String aCountry, String aOrgCode) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException {
		try {
			return getSBCheckListBusManager().getDefaultCollateralCheckList(anICheckListOwner, aCollateralType,
					aCollateralSubType, aCountry, aOrgCode);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getDefaultCollateralCheckList: ", ex.getCause());
		}
	}

	public ICheckList getDefaultCollateralCheckList(String country, String collateralType, String collateralSubType,
			String applicationType, String goodsStatus, String pbrInd) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException {
		try {
			return getSBCheckListBusManager().getDefaultCollateralCheckList(country, collateralType, collateralSubType,
					applicationType, goodsStatus, pbrInd);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDefaultCollateralCheckList: ", ex.getCause());
		}
	}
	

	public ICheckList getDefaultCAMCheckList(ICheckListOwner anICheckListOwner,String country, String collateralType, String collateralSubType,
			String applicationType, String goodsStatus, String pbrInd) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException {
		try {
			return getSBCheckListBusManager().getDefaultCAMCheckList(anICheckListOwner,country, collateralType, collateralSubType,
					applicationType, goodsStatus, pbrInd);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDefaultCollateralCheckList: ", ex.getCause());
		}
	}
	
	public ICheckList getDefaultFacilityCheckList(ICheckListOwner anICheckListOwner,String country, String collateralType, String collateralSubType,
			String applicationType, String goodsStatus, String pbrInd) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException {
		try {
			return getSBCheckListBusManager().getDefaultFacilityCheckList(anICheckListOwner,country, collateralType, collateralSubType,
					applicationType, goodsStatus, pbrInd);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDefaultCollateralCheckList: ", ex.getCause());
		}
	}

	/**
	 * Get the global items that are not in the checklist as well as those that
	 * are newly added at template level
	 * @param anICheckList - ICheckList
	 * @return IItem[] - the list of items that are not in the checkList as well
	 *         as those newly added at template level
	 * @throws CheckListException on errors
	 * @throws SearchDAOException is DAO errors
	 */
	public IItem[] getItemList(ICheckList anICheckList) throws CheckListTemplateException, CheckListException,
			SearchDAOException {
		try {
			return getSBCheckListBusManager().getItemList(anICheckList);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getItemList: ", ex.getCause());
		}
	}

    /**
	 * Get the global items that are not in the checklist as well as those that
	 * are newly added at template level
	 * @param anICheckList - ICheckList
	 * @param collateralID - String
	 * @return IItem[] - the list of items that are not in the checkList as well
	 *         as those newly added at template level
	 * @throws CheckListException on errors
	 * @throws SearchDAOException is DAO errors
	 */

	public IItem[] getItemList(ICheckList anICheckList, String collateralID) throws CheckListTemplateException, CheckListException,
			SearchDAOException {
		try {
			return getSBCheckListBusManager().getItemList(anICheckList, collateralID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getItemList: ", ex.getCause());
		}
	}

	/**
	 * Get the list of allowable checklist item operations
	 * @return ICheckListItemOperation[] - the list of checklist item operations
	 * @throws CheckListException on errors
	 */
	public ICheckListItemOperation[] getAllowableOperationList() throws CheckListException {
		try {
			return getSBCheckListBusManager().getAllowableOperationList();
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getAllowableOperationList: ", ex.getCause());
		}
	}

	/**
	 * Get a checklist item by the item reference
	 * @param aCheckListItemRef of long type
	 * @return ICheckListItem - the checklist item with the checklist item
	 *         reference
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListItem getCheckListItem(long aCheckListItemRef) throws CheckListException {
		try {
			return getSBCheckListBusManager().getCheckListItemByRef(aCheckListItemRef);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCheckListItemByRef: ", ex.getCause());
		}
	}

	/**
	 * Update a checklist item based on the primary key 1. Update the status of
	 * staging checklist item based on the checklistItemID 2. Update the status
	 * of the actual checklist item based on the checklistItemref
	 * @param aStagingCheckListItemID of long type
	 * @param aCheckListItemRef of long type
	 * @return ICheckListItem - the checklist item updated
	 * @throws CheckListException on errors
	 */
	public void expireCheckListItem(long aStagingCheckListItemID, long aCheckListItemRef) throws CheckListException {
		try {
			getSBStagingCheckListBusManager().updateCheckListItemStatusByID(aStagingCheckListItemID,
					ICMSConstant.STATE_ITEM_EXPIRED);
			getSBCheckListBusManager().updateCheckListItemStatusByRef(aCheckListItemRef,
					ICMSConstant.STATE_ITEM_EXPIRED);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at expireCheckListItem: ", ex.getCause());
		}
	}

	/**
	 * Get Hashtable with borrower ID/pledger ID and the status
	 * @param aLimitProfileID of long type
	 * @return HashMap - this the security ID as the key and the status as the
	 *         value
	 * @throws SearchDAOException on errors
	 */
	public HashMap getCCCheckListStatus(long aLimitProfileID, boolean isFullListInd) throws SearchDAOException,
			CheckListException {
		try {
			return getSBCheckListBusManager().getCCCheckListStatus(aLimitProfileID, isFullListInd);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCCCheckListStatus: ", ex.getCause());
		}
	}
	
	public HashMap getFacilityCheckListStatus(long aLimitProfileID, boolean isFullListInd) throws SearchDAOException,
	CheckListException {
			try {
				return getSBCheckListBusManager().getFacilityCheckListStatus(aLimitProfileID, isFullListInd);
			}
			catch (RemoteException ex) {
				_context.setRollbackOnly();
				throw new CheckListException("RemoteException enctr at getCCCheckListStatus: ", ex.getCause());
			}
			}

	protected CCCheckListSummary[] getDeletedCCCheckListStatusForNonBorrower(long aCustomerID)
			throws SearchDAOException, CheckListException {
		try {
			SBCheckListBusManager mgr = getSBCheckListBusManager();
			return mgr.getDeletedCCCheckListStatusForNonBorrower(aCustomerID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getDeletedCCCheckListStatusForNonBorrower: "
					+ ex.toString());
		}
	}

	protected CCCheckListSummary[] getDeletedCCCheckListStatus(long aLimitProfileID, long aCustomerID)
			throws SearchDAOException, CheckListException {
		try {
			SBCheckListBusManager mgr = getSBCheckListBusManager();
			CCCheckListSummary[] summaryList = mgr.getDeletedCCCheckListStatus(aLimitProfileID);
			/*
			 * CCCheckListSummary[] nonBorrowerList =
			 * mgr.getDeletedCCCheckListStatusForNonBorrower(aCustomerID); if
			 * ((summaryList == null) || (summaryList.length == 0)) return
			 * nonBorrowerList; if ((nonBorrowerList == null) ||
			 * (nonBorrowerList.length == 0)) return summaryList; ArrayList list
			 * = new ArrayList(); for (int ii=0; ii<summaryList.length; ii++) {
			 * list.add(summaryList[ii]); } for (int ii=0;
			 * ii<nonBorrowerList.length; ii++) { list.add(nonBorrowerList[ii]);
			 * } return (CCCheckListSummary[])list.toArray(new
			 * CCCheckListSummary[0]);
			 */

			return summaryList;
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getDeletedCCCheckListStatus: ", ex.getCause());
		}
	}

	/**
	 * Get Hashtable with borrower ID/pledger ID and the status
	 * @param aLimitProfileID of long type
	 * @return HashMap - this the security ID as the key and the status as the
	 *         value
	 * @throws SearchDAOException on errors
	 */
	protected HashMap getCCCheckListStatusForNonBorrower(long aLimitProfileID, long aCustomerID)
			throws SearchDAOException, CheckListException {
		try {
			return getSBCheckListBusManager().getCCCheckListStatusForNonBorrower(aLimitProfileID, aCustomerID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCCCheckListStatusForNonBorrower: "
					+ ex.toString());
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
	protected int getLimitProfileCollateralCount(long aLimitProfileID, long aCollateralID) throws SearchDAOException,
			CheckListException {
		try {
			return getSBCheckListBusManager().getLimitProfileCollateralCount(aLimitProfileID, aCollateralID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getLimitProfileCollateralCount: ", ex.getCause());
		}
	}

	protected int getLimitProfilePledgorCount(long aLimitProfileID, long aPledgorID, long[] aDeletedLimitSecMapList)
			throws SearchDAOException, CheckListException {
		try {
			return getSBCheckListBusManager().getLimitProfilePledgorCount(aLimitProfileID, aPledgorID,
					aDeletedLimitSecMapList);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getLimitProfilePledgorCount: ", ex.getCause());
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
	protected int getLimitProfilePledgorCount(long aLimitProfileID, long aPledgorID) throws SearchDAOException,
			CheckListException {
		try {
			return getSBCheckListBusManager().getLimitProfilePledgorCount(aLimitProfileID, aPledgorID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getLimitProfilePledgorCount: ", ex.getCause());
		}
	}

	protected int getLimitProfileCoBorrowerCount(long aLimitProfileID, long aCustomerID) throws SearchDAOException,
			CheckListException {
		try {
			return getSBCheckListBusManager().getLimitProfileCoBorrowerCount(aLimitProfileID, aCustomerID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getLimitProfileCoBorrowerCount: ", ex.getCause());
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
			return getSBCheckListBusManager().getCollateralCheckListStatus(aLimitProfileID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCollateralCheckListStatus: ", ex.getCause());
		}
	}

	/**
	 * Get the checklist based on the checklist ID
	 * @param aCheckListID of long type
	 * @return ICheckList - the checklist containing the checklist with the
	 *         specified ID
	 * @throws CheckListException on errors
	 */
	public ICheckList getCheckListByID(long aCheckListID) throws CheckListException {
		try {
			return getSBCheckListBusManager().getCheckListByID(aCheckListID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCheckListByID: ", ex.getCause());
		}
	}
	
	public CheckListSearchResult getCheckListByCollateralID(long aCheckListID) throws CheckListException {
		try {
			return getSBCheckListBusManager().getCheckListByCollateralID(aCheckListID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCheckListByID: ", ex.getCause());
		}
	}
	
	public CheckListSearchResult getCAMCheckListByCategoryAndProfileID(String category,long aCheckListID) throws CheckListException {
		try {
			return getSBCheckListBusManager().getCAMCheckListByCategoryAndProfileID(category, aCheckListID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCheckListByID: ", ex.getCause());
		}
	}
	public CheckListSearchResult[] getCheckListByCategory(String category) throws CheckListException {
		try {
			return getSBCheckListBusManager().getCheckListByCategory(category);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCheckListByID: ", ex.getCause());
		}
	}

	/**
	 * To get a collateral checklist based on the limit profile ID and the
	 * collateral ID
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @return ICheckList - the collateral checklist
	 * @throws CheckListException on errors
	 */
	public ICheckList getCollateralCheckList(long aLimitProfileID, long aCollateralID) throws CheckListException {
		try {
			return getSBCheckListBusManager().getCollateralCheckList(aLimitProfileID, aCollateralID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCollateralCheckList: ", ex.getCause());
		}
	}

	protected CheckListSearchResult[] getCheckList(ICheckListOwner anICheckListOwner, String[] aStatusList)
			throws SearchDAOException, CheckListException {
		try {
			return getSBCheckListBusManager().getCheckList(anICheckListOwner, aStatusList);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCheckList: ", ex.getCause());
		}
	}

	protected ICheckListAudit[] getCheckListAuditList(long aLimitProfileID, String[] aStatusList)
			throws SearchDAOException, CheckListException {
		try {
			return getSBCheckListBusManager().getCheckListAuditList(aLimitProfileID, aStatusList);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCheckListAuditList: ", ex.getCause());
		}
	}

	/**
	 * Get the list of checklist items that qualify for audit under a limit
	 * profile
	 * @param aLimitProfileID of long String
	 * @return IAuditItem[] - the list of checklist that qualifies for audit
	 * @throws CheckListException on errors
	 * @throws SearchDAOException on DAO errrors
	 */
	protected IAuditItem[] getAuditItemList(long aLimitProfileID, String[] aStatusList) throws CheckListException,
			SearchDAOException {
		try {
			return getSBCheckListBusManager().getCheckListItemListForAudit(aLimitProfileID, aStatusList);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getAuditItemList: ", ex.getCause());
		}
	}

	/**
	 * Get the list of checklist items that qualify for audit under a limit
	 * profile
	 * @param aCustomerID of long String
	 * @param aStatusList of String[] type
	 * @return ICheckListAudit[] - the list of checklist that qualifies for
	 *         audit
	 * @throws CheckListException on errors
	 * @throws SearchDAOException on DAO errrors
	 */
	protected ICheckListAudit[] getCheckListAuditListForNonBorrower(long aCustomerID, String[] aStatusList)
			throws SearchDAOException, CheckListException {
		try {
			return getSBCheckListBusManager().getCheckListAuditListForNonBorrower(aCustomerID, aStatusList);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getAuditItemListForNonBorrower: ", ex.getCause());
		}
	}

	/**
	 * Get the list of checklist items that qualify for audit under a limit
	 * profile
	 * @param aCustomerID of long String
	 * @return IAuditItem[] - the list of checklist that qualifies for audit
	 * @throws CheckListException on errors
	 * @throws SearchDAOException on DAO errrors
	 */
	protected IAuditItem[] getAuditItemListForNonBorrower(long aCustomerID, String[] aStatusList)
			throws SearchDAOException, CheckListException {
		try {
			return getSBCheckListBusManager().getCheckListItemListForAuditForNonBorrower(aCustomerID, aStatusList);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getAuditItemListForNonBorrower: ", ex.getCause());
		}
	}

	/**
	 * Get the list of document categories for this borrower.
	 * @param aLimitProfileID of long String
	 * @return HashMap - the list of document categories
	 * @throws SearchDAOException on DAO errrors
	 * @throws CheckListException on errors
	 */
	public HashMap getDocumentCategories(long aLimitProfileID) throws SearchDAOException, CheckListException {
		try {
			return getSBCheckListBusManager().getDocumentCategories(aLimitProfileID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getDocumentCategories: ", ex.getCause());
		}
	}

	/**
	 * Get the list of document categories for this non-borrower.
	 * @param aCustomerID of long String
	 * @return HashMap - the list of document categories
	 * @throws SearchDAOException on DAO errrors
	 * @throws CheckListException on errors
	 */
	public HashMap getDocumentCategoriesForNonBorrower(long aCustomerID, long aLimitProfileID)
			throws SearchDAOException, CheckListException {
		try {
			return getSBCheckListBusManager().getDocumentCategoriesForNonBorrower(aCustomerID, aLimitProfileID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getDocumentCategories: ", ex.getCause());
		}
	}

	/**
	 * Get the map of documents held given the search criteria.
	 * 
	 * @param criteria - DocumentHeldSearchCriteria
	 * @return HashMap - Map of list of document held fulfilling the criteria
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 */
	public HashMap getDocumentsHeld(DocumentHeldSearchCriteria criteria) throws SearchDAOException, CheckListException {
		try {
			HashMap docListMap = getSBCheckListBusManager().getDocumentsHeld(criteria);
			if(docListMap !=null){
				DefaultLogger.debug(this,":::::::::::::::::::docListMap::::::::::::::::::::"+docListMap.size());
			}
			if (criteria.isAllReqDoc()) {
				List mainBorrowerList = (List) docListMap.get(ICMSConstant.CHECKLIST_MAIN_BORROWER);
				if (mainBorrowerList != null) {
					if (!CheckListUtil
							.isInCountry(criteria.getTrxContext().getTeam(), criteria.getLimitProfile(), null)) {
						docListMap.remove(ICMSConstant.CHECKLIST_MAIN_BORROWER);
						List coBorrowerList = (List) docListMap.get(ICMSConstant.CHECKLIST_CO_BORROWER);
						if (coBorrowerList != null) {
							Iterator i = coBorrowerList.iterator();
							while (i.hasNext()) {
								IDocumentHeld doc = (IDocumentHeld) i.next();
								if (!isInSameCountry(criteria.getTrxContext(), criteria.getLimitProfile(), doc
										.getCustomerID(), doc.getCheckListLocation().getCountryCode(),
										ICMSConstant.CHECKLIST_CO_BORROWER)) {
									i.remove();
								}
							}
						}
						List pledgorList = (List) docListMap.get(ICMSConstant.CHECKLIST_PLEDGER);
						if (pledgorList != null) {
							Iterator i = pledgorList.iterator();
							while (i.hasNext()) {
								IDocumentHeld doc = (IDocumentHeld) i.next();
								if (!isInSameCountry(criteria.getTrxContext(), criteria.getLimitProfile(), doc
										.getCustomerID(), doc.getCheckListLocation().getCountryCode(),
										ICMSConstant.CHECKLIST_PLEDGER)) {
									i.remove();
								}
							}
						}
					}
					List securityList = (List) docListMap.get(ICMSConstant.CHECKLIST_SECURITY);
					if (securityList != null) {
						Iterator i = securityList.iterator();
						while (i.hasNext()) {
							IDocumentHeld doc = (IDocumentHeld) i.next();
							if (!isInSameCountry(criteria.getTrxContext(), criteria.getLimitProfile(), doc
									.getCollateral().getCollateralID(), doc.getCheckListLocation().getCountryCode(),
									ICMSConstant.CHECKLIST_SECURITY)) {
								i.remove();
							}
						}
					}
				}
				else {
					// non borrower??
				}
			}
			return docListMap;
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getDocumentsHeld: ", ex.getCause());
		}
	}

	/**
	 * Get the list of securities pledged for this borrower type.
	 * @param aLimitProfileID the limit profile identifier
	 * @param pledgorID the pledgor identifier
	 * @return HashMap - the securities pledged for this customer
	 * @throws SearchDAOException on DAO errrors
	 * @throws CheckListException on errors
	 */
	public HashMap getSecuritiesPledged(long aLimitProfileID, long pledgorID) throws SearchDAOException,
			CheckListException {
		try {
			return getSBCheckListBusManager().getSecuritiesPledged(aLimitProfileID, pledgorID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getSecuritiesPledged: ", ex.getCause());
		}
	}

	protected ICollateralTask getCollateralTask(long aLimitProfileID, long aCollateralID, String aCollateralLocation)
			throws CheckListException {
		try {
			ICollaborationTaskProxyManager mgr = CollaborationTaskProxyManagerFactory.getProxyManager();
			ICollateralTaskTrxValue trxValue = mgr.getCollateralTaskTrxValue(aLimitProfileID, aCollateralID,
					aCollateralLocation);
			if (trxValue != null) {
				return trxValue.getCollateralTask();
			}
			return null;
		}
		catch (CollaborationTaskException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("Exception enctr at getCollateralTask", ex);
		}
	}

	protected ICCTask getCCTask(long aLimitProfileID, String aCustCategory, long aCustomerID) throws CheckListException {
		try {
			// return
			// getSBCollaborationTaskBusManager().getCCTask(aLimitProfileID,
			// aCustCategory, aCustomerID);
			ICollaborationTaskProxyManager mgr = CollaborationTaskProxyManagerFactory.getProxyManager();
			ICCTaskTrxValue trxValue = mgr.getCCTaskTrxValue(aLimitProfileID, aCustCategory, aCustomerID, null);
			if (trxValue != null) {
				return trxValue.getCCTask();
			}
			return null;
		}
		catch (CollaborationTaskException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("Exception enctr at getCCTask", ex);
		}
		/*
		 * catch(RemoteException ex) { _context.setRollbackOnly(); throw new
		 * CheckListException("RemoteException enctr at getCCTask: " +
		 * ex.toString()); }
		 */
	}

	/**
	 * To rollback a transaction
	 */
	protected void rollback() {
		_context.setRollbackOnly();
	}

	/**
	 * Helper method to return the checklist bus session bean
	 * 
	 * @return SBCheckListBusManager - the remote handler for the checklist bus
	 *         manager session bean
	 * @throws CheckListException for any errors encountered
	 */
	private SBCheckListBusManager getSBCheckListBusManager() throws CheckListException {
		SBCheckListBusManager busmgr = (SBCheckListBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CHECKLIST_BUS_JNDI, SBCheckListBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new CheckListException("SBCheckListBusManager is null!");
		}
		return busmgr;
	}

	/**
	 * Helper method to return the staging checklist bus session bean
	 * 
	 * @return SBCheckListBusManager - the remote handler for the checklist bus
	 *         manager session bean
	 * @throws CheckListException for any errors encountered
	 */
	private SBCheckListBusManager getSBStagingCheckListBusManager() throws CheckListException {
		SBCheckListBusManager busmgr = (SBCheckListBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_CHECKLIST_BUS_JNDI, SBCheckListBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new CheckListException("SBCheckListBusManager is null!");
		}
		return busmgr;
	}

	/**
	 * Helper method to return the collaboration task bus session bean
	 * 
	 * @return SBCollaborationTaskBusManager - the remote handler for the
	 *         collaboration task bus manager session bean
	 * @throws CheckListException for any errors encountered
	 */
	private SBCollaborationTaskBusManager getSBCollaborationTaskBusManager() throws CheckListException {
		SBCollaborationTaskBusManager busmgr = (SBCollaborationTaskBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_COLLABORATION_BUS_JNDI, SBCollaborationTaskBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new CheckListException("SBCollaborationTaskBusManager is null!");
		}
		return busmgr;
	}

	/**
	 * FOR CR CMS-662 Get all CHECKLIST related to a SECURITY based on
	 * CMS_COLLATERAL_ID.
	 * @param aCollateralID of long type
	 * @return int - the number of checklist related to security.
	 * @throws SearchDAOException on errors
	 */
	public int getSecurityChkListCount(long aCollateralID) throws SearchDAOException, CheckListException {
		try {
			return getSBCheckListBusManager().getSecurityChkListCount(aCollateralID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getSecurityChkListCount: ", ex.getCause());
		}
	}

	/**
	 * FOR CR CMS-310 Get the list of checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param asOfDate of String type
	 * @return HashMap - the list of checklist items that requires auditing
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	protected ICheckListAudit[] getCheckListAudit(long aLimitProfileID, String asOfDate) throws SearchDAOException,
			CheckListException {
		try {
			return getSBCheckListBusManager().getCheckListAudit(aLimitProfileID, asOfDate);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCheckListAudit: ", ex.getCause());
		}
	}

	/**
	 * FOR CR CMS-310 Get the list of checklist items that qualify for audit
	 * under a limit profile
	 * @param aCustomerID of long String
	 * @param asOfDate of String type
	 * @return ICheckListAudit[] - the list of checklist that qualifies for
	 *         audit
	 * @throws CheckListException on errors
	 * @throws SearchDAOException on DAO errrors
	 */
	protected ICheckListAudit[] getCheckListAuditForNonBorrower(long aCustomerID, String asOfDate)
			throws SearchDAOException, CheckListException {
		try {
			return getSBCheckListBusManager().getCheckListAuditForNonBorrower(aCustomerID, asOfDate);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCheckListAuditForNonBorrower: ", ex.getCause());
		}
	}

	protected Long[] getAffectedPledgorCheckList(long aCoBorrowerLegalRef, long aLimitProfileRef)
			throws SearchDAOException, CheckListException {
		try {
			return getSBCheckListBusManager().getAffectedPledgorCheckList(aCoBorrowerLegalRef, aLimitProfileRef);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getAffectedPledgorCheckList: ", ex.getCause());
		}
	}

	protected Long[] getAffectedLimitProfileID(long aPledgorLegalRef) throws SearchDAOException, CheckListException {
		try {
			return getSBCheckListBusManager().getAffectedLimitProfileID(aPledgorLegalRef);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getAffectedLimitProfileID: ", ex.getCause());
		}
	}

	// Star for cr-17
	/**
	 * 
	 * @param aCheckListId
	 * @param categoryType
	 * @return
	 * @throws CheckListException
	 * @throws SearchDAOException
	 */
	public List getCheckListDetailsByCheckListId(String[] aCheckListId, String categoryType) throws CheckListException,
			SearchDAOException {
		try {
			return getSBCheckListBusManager().getCheckListDetailsByCheckListId(aCheckListId, categoryType);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCheckListDetailsByCheckListId: ", ex.getCause());
		}
	}

	public List getCheckListDetailsByCheckListId(String[] aCheckListId) throws CheckListException, SearchDAOException {
		try {
			return getSBCheckListBusManager().getCheckListDetailsByCheckListId(aCheckListId);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCheckListDetailsByCheckListId: ", ex.getCause());
		}
	}

	public List getAllShareDocuments(long id, boolean isNonBorrower) throws CheckListException, SearchDAOException {
		try {
			return CheckListDAOFactory.getCheckListDAO().getAllShareDocuments(id, isNonBorrower);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CheckListException("Exception at getAllShareDocuments: ", ex);
		}
	}
	
	public List getAllDeferCreditApprover() throws CheckListException, SearchDAOException {
		try {
			return CheckListDAOFactory.getCheckListDAO().getAllDeferCreditApprover();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CheckListException("Exception at getAllShareDocuments: ", ex);
		}
	}
	public List getAllWaiveCreditApprover() throws CheckListException, SearchDAOException {
		try {
			return CheckListDAOFactory.getCheckListDAO().getAllWaiveCreditApprover();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CheckListException("Exception at getAllShareDocuments: ", ex);
		}
	}
	public List getAllBothCreditApprover() throws CheckListException, SearchDAOException {
		try {
			return CheckListDAOFactory.getCheckListDAO().getAllBothCreditApprover();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CheckListException("Exception at getAllShareDocuments: ", ex);
		}
	}

	/*
	 * 
	 * @param aProfileId
	 * 
	 * @return
	 * 
	 * @throws SearchDAOException
	 * 
	 * @throws CheckListException
	 */
	/*
	 * public OBShareDoc getLeName(long aProfileId) throws SearchDAOException,
	 * CheckListException{ try { return
	 * getSBCheckListBusManager().getLeName(aProfileId); } catch(RemoteException
	 * ex){ _context.setRollbackOnly(); throw new
	 * CheckListException("RemoteException enctr at getLeName: " +
	 * ex.toString()); } }
	 */
	/*
	 * 
	 * @param aProfileId
	 * 
	 * @param collateralId
	 * 
	 * @return
	 * 
	 * @throws SearchDAOException
	 * 
	 * @throws CheckListException
	 */
	/*
	 * public OBShareDoc getSecuritySubDetails(long aProfileId, long
	 * collateralId) throws SearchDAOException, CheckListException{ try { return
	 * getSBCheckListBusManager
	 * ().getSecuritySubDetails(aProfileId,collateralId); }
	 * catch(RemoteException ex){ _context.setRollbackOnly(); throw new
	 * CheckListException("RemoteException enctr at getSecuritySubDetails: " +
	 * ex.toString()); } }
	 */

	public List getCustomerListByCollateralID(long collateralId) throws CheckListException {
		try {
			return getSBCheckListBusManager().getCustomerListByCollateralID(collateralId);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException  at getCustomerListByCollateralID: ", ex.getCause());
		}
		catch (SearchDAOException e) {
			throw new CheckListException("CheckListException at getCustomerListByCollateralID :" + e.toString());
		}

	}

	// Method added by Pratheepa on 29Sep2006 while fixing bug CMS-3479
	public ArrayList getDocumentIdsForCheckList(ArrayList documentIds) throws CheckListException {
		try {
			ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
			return dao.getDocumentIdsForCheckList(documentIds);
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new CheckListException("Caught Exception!", e);
		}
	}

	public HashMap getCollateralIdForSharedDocs(long documentNo, Long[] collateralIds) throws CheckListException {
		try {
			ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
			return dao.getCollateralIdForSharedDocs(documentNo, collateralIds);
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new CheckListException("Caught Exception!", e);
		}
	}

	public HashMap getSecuritySubTypes(String secType) throws CheckListException {
		try {
			ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
			return dao.getSecuritySubTypes(secType);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CheckListException("Caught Exception!", e);
		}
	}

	public HashMap getStateDistrictMukim(HashMap input) throws CheckListException {
		try {
			ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
			return dao.getStateDistrictMukim(input);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CheckListException("Caught Exception!", e);
		}
	}

	public HashMap getDocumentationStatus(long lmtProfileID) throws CheckListException {
		try {
			ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
			return dao.getDocumentationStatus(lmtProfileID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CheckListException("Caught Exception!", e);
		}
	}

	public boolean getChecklistCompletedStatus(long collID) throws CheckListException {
		try {
			ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
			return dao.getChecklistCompletedStatus(collID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CheckListException("Caught Exception!", e);
		}
	}

	public int getAssetResidualLife(String secSubTypeId) throws CheckListException {
		try {
			ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
			return dao.getAssetResidualLife(secSubTypeId);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CheckListException("Caught Exception!", e);
		}
	}

	public HashMap[] getDetailsForPreDisbursementReminderLetter(long limitProfileID) throws SearchDAOException,
			CheckListException {
		try {
			return getSBCheckListBusManager().getDetailsForPreDisbursementReminderLetter(limitProfileID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException  at getDetailsForPreDisbursementReminderLetter ", ex
					.getCause());
		}
	}

	public HashMap[] getDetailsForPostDisbursementReminderLetter(long limitProfileID) throws SearchDAOException,
			CheckListException {
		try {
			return getSBCheckListBusManager().getDetailsForPostDisbursementReminderLetter(limitProfileID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException  at getDetailsForPostDisbursementReminderLetter ", ex
					.getCause());
		}
	}

    public HashMap getCheckListItemListbyCategory(long aLimitProfileID, String aCheckListCategory) throws SearchDAOException, CheckListException {
        try {

            return getSBCheckListBusManager().getCheckListItemListbyCategory(aLimitProfileID, aCheckListCategory);

        } catch (RemoteException ex) {
            _context.setRollbackOnly();
            throw new CheckListException("RemoteException at getCheckListItemListbyCategory ", ex.getCause());
        }
    }

	
	public CAMCheckListSummary[] getCAMCheckListSummaryList(
			IContext anIContext, long aLimitProfileID)
			throws CheckListException {
		// TODO Auto-generated method stub
		return null;
	}

	public ICheckList getDefaultPariPassuCheckList(
			ICheckListOwner anICheckListOwner, String pariPassuType,
			String pariPassuSubType, String country, String orgCode)
			throws TemplateNotSetupException, CheckListTemplateException,
			CheckListException {
		try {
			return getSBCheckListBusManager().getDefaultPariPassuCheckList(anICheckListOwner ,pariPassuType, pariPassuSubType,
					country, orgCode);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDefaultCollateralCheckList: ", ex.getCause());
		}
	}

	public PariPassuCheckListSummary[] getPariPassuCheckListSummaryList(
			IContext anIContext, long limitProfileID) throws CheckListException {
		// TODO Auto-generated method stub
		return null;
	}

	public CheckListSearchResult getPariPassuCheckListByCategoryAndProfileID(
			String category, long paripassuID) throws CheckListException {
		try {
			return getSBCheckListBusManager().getPariPassuCheckListByCategoryAndProfileID(category, paripassuID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCheckListByID: ", ex.getCause());
		}
	}

	/**
	 * Added by Anil
	 * Get a checklist item by the item id
	 * @param checkListItemId of long type
	 * @return ICheckListItem - the checklist item with the checklist item id
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListItem getCheckListItemById(long checkListItemId) throws CheckListException {
		try {
			return getSBCheckListBusManager().getCheckListItemById(checkListItemId);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListException("RemoteException enctr at getCheckListItemById: ", ex.getCause());
		}
	}
	



	
	

}
