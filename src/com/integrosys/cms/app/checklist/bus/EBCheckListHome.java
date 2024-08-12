/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBCheckListHome.java,v 1.32 2006/09/07 10:22:36 jychong Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.chktemplate.bus.DocumentHeldSearchCriteria;

/**
 * Home interface for the checklist entity bean
 * 
 * @author $Author: Abhijit Rudrakshawar $<br>
 * @version $Revision: 1.32 $
 * @since $Date: 2006/09/07 10:22:36 $ Tag: $Name: $
 */

public interface EBCheckListHome extends EJBHome {
	/**
	 * Create a checklist
	 * @param anICheckList - ICheckList
	 * @return EBCheckList - the remote handler for the created checklist
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBCheckList create(ICheckList anICheckList) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the checklist ID
	 * @param aPK - Long
	 * @return EBCheckList - the remote handler for the checklist that has the
	 *         PK as specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBCheckList findByPrimaryKey(Long aPK) throws FinderException, RemoteException;

	/**
	 * Find by limit profile ID and Collateral ID
	 * @param aLimitProfileID of Long type
	 * @param aCollateralID of Long type
	 * @return EBCheckList - the remote checklist handler
	 * @throws FinderException and RemoteException
	 */
	public EBCheckList findByLimitProfileIDAndCollateralID(Long aLimitProfileID, Long aCollateralID)
			throws FinderException, RemoteException;

	/**
	 * Get the number of limits that a collateral is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @return int - the number of limits that a collateral is related in a
	 *         limit profile
	 * @throws SearchDAOException and RemoteException on errors
	 */
	public int getLimitProfileCollateralCount(long aLimitProfileID, long aCollateralID) throws SearchDAOException,
			RemoteException;

	public int getLimitProfilePledgorCount(long aLimitProfileID, long aPledgorID, long[] aDeletedLimitSecMapList)
			throws SearchDAOException, RemoteException;

	/**
	 * Get the number of limits that a pledgor is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aPledgorID of long type
	 * @return int - the number of limits that a pledgor is related in a limit
	 *         profile
	 * @throws SearchDAOException and RemoteException on errors
	 */
	public int getLimitProfilePledgorCount(long aLimitProfileID, long aPledgorID) throws SearchDAOException,
			RemoteException;

	/**
	 * Get the number of limits that a coborrower is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return int - the number of limits that a coborrower is related in a
	 *         limit profile
	 * @throws SearchDAOException and CheckListException, RemoteException on
	 *         errors
	 */
	public int getLimitProfileCoBorrowerCount(long aLimitProfileID, long aCustomerID) throws SearchDAOException,
			RemoteException;

	/**
	 * Get Hashtable with collateral ID and the status
	 * @param aLimitProfileID of long type
	 * @return HashMap - this the security ID as the key and the status as the
	 *         value
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getCollateralCheckListStatus(long aLimitProfileID) throws SearchDAOException, RemoteException;
	
	public CheckListSearchResult getCheckListByCollateralID(long collateralId) throws SearchDAOException, RemoteException;

	public CheckListSearchResult getCAMCheckListByCategoryAndProfileID(String category,long collateralID) throws SearchDAOException, RemoteException;
	
	public CheckListSearchResult[] getCheckListByCategory(String category) throws SearchDAOException, RemoteException;
	
	
	public CheckListSearchResult getPariPassuCheckListByCategoryAndProfileID(String category,long paripassuId) throws SearchDAOException, RemoteException;
	
	/**
	 * Get Hashtable with borrower ID/pledger ID and the status
	 * @param aLimitProfileID of long type
	 * @param isFullListInd of Boolean Type
	 * @return HashMap - this the borrowerID/pledgerID as the key and the status
	 *         as the value
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getCCCheckListStatus(long aLimitProfileID, boolean isFullListInd) throws SearchDAOException,
			RemoteException;
	
	
	public HashMap getFacilityCheckListStatus(long aLimitProfileID, boolean isFullListInd) throws SearchDAOException,
	RemoteException;


	/**
	 * Get Hashtable with borrower ID/pledger ID and the status for deleted
	 * checklist
	 * @param aLimitProfileID of long type
	 * @param aDeletedInd of boolean Type
	 * @return CCCheckListSummary[] - this the borrowerID/pledgerID as the key
	 *         and the status as the value
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public CCCheckListSummary[] getCCCheckListList(long aLimitProfileID, boolean aDeletedInd)
			throws SearchDAOException, RemoteException;

	public CCCheckListSummary[] getCCCheckListListForNonBorrower(long aCustomerID, boolean aDeletedInd)
			throws SearchDAOException, RemoteException;

	/**
	 * Get Hashtable with borrower ID/pledger ID and the status
	 * @param aCustomerID of long type
	 * @return HashMap - this the customer ID as the key and the status as the
	 *         value
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getCCCheckListStatusForNonBorrower(long aLimitProfileID, long aCustomerID)
			throws SearchDAOException, RemoteException;

	/**
	 * Get the list of checklist item operation
	 * @return ICheckListItemOperation[] - the list of checklist item
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListItemOperation[] getCheckListItemOperation() throws SearchDAOException, RemoteException;

	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param anICheckListOwner of ICheckListOwner
	 * @param aStatusList of String[] type
	 * @return CheckListSearchResult[] - the list of checklist result
	 * @throws SearchDAOException
	 * @throws RemoteException on remote errors
	 */
	public CheckListSearchResult[] getCheckList(ICheckListOwner anICheckListOwner, String[] aStatusList)
			throws SearchDAOException, RemoteException;

	/**
	 * Get the checklist item for waiver/deferral generation
	 * @param aLimitProfileID of long type
	 * @param anItemStatus of String type
	 * @return HashMap - the checkListID and the list of checklist item (not the
	 *         full detail)
	 * @throws SearchDAOException
	 * @throws RemoteException on remote errors
	 */
	public HashMap getCheckListItemListbyStatus(long aLimitProfileID, String anItemStatus) throws SearchDAOException,
			RemoteException;

    /**
     * Get the checklist item by checklist category
     *
     * @param aLimitProfileID of long type
     * @param aCheckListCategory    of String type
     * @return HashMap - the checkListID and the list of checklist item (not the full detail)
     * @throws SearchDAOException
     * @throws RemoteException    on remote errors
     */
    public HashMap getCheckListItemListbyCategory(long aLimitProfileID, String aCheckListCategory) throws SearchDAOException,
			RemoteException;

	/**
	 * Get the checklist item for waiver/deferral generation for non borrower
	 * @param aCustomerID of long type
	 * @param anItemStatus of String type
	 * @return HashMap - the checkListID and the list of checklist item (not the
	 *         full detail)
	 * @throws SearchDAOException
	 * @throws RemoteException on remote errors
	 */
	public HashMap getCheckListItemListbyStatusForNonBorrower(long aCustomerID, String anItemStatus)
			throws SearchDAOException, RemoteException;

	/**
	 * Get the list of checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param aStatusList of String[] type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListAudit[] getCheckListAuditList(long aLimitProfileID, String[] aStatusList)
			throws SearchDAOException, RemoteException;

	/**
	 * Get the list of Checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param aStatusList of String[] type
	 * @return IAuditItem[] - the list of checklist items that requires auditing
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public IAuditItem[] getCheckListItemListForAudit(long aLimitProfileID, String[] aStatusList)
			throws SearchDAOException, RemoteException;

	/**
	 * Get the list of checklist items that requires auditing
	 * @param aCustomerID of long type
	 * @param aStatusList of String[] type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListAudit[] getCheckListAuditListForNonBorrower(long aCustomerID, String[] aStatusList)
			throws SearchDAOException, RemoteException;

	/**
	 * Get the list of Checklist items that requires auditing for non borrower
	 * @param aCustomerID of long type
	 * @param aStatusList of String[] type
	 * @return IAuditItem[] - the list of checklist items that requires auditing
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public IAuditItem[] getCheckListItemListForAuditForNonBorrower(long aCustomerID, String[] aStatusList)
			throws SearchDAOException, RemoteException;

	/**
	 * Get the list of document categories for this borrower.
	 * @param aLimitProfileID of long type
	 * @return HashMap - the list of document categories
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getDocumentCategories(long aLimitProfileID) throws SearchDAOException, RemoteException;

	/**
	 * Get the list of document categories for this non-borrower.
	 * @param aCustomerID of long type
	 * @return HashMap - the list of document categories
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getDocumentCategoriesForNonBorrower(long aCustomerID, long aLimitProfileID)
			throws SearchDAOException, RemoteException;

	/**
	 * Get the map of documents held given the search criteria.
	 * 
	 * @param criteria - DocumentHeldSearchCriteria
	 * @return HashMap - Map of list of document held fulfilling the criteria
	 * @throws SearchDAOException on errors
	 */
	public HashMap getDocumentsHeld(DocumentHeldSearchCriteria criteria) throws SearchDAOException, RemoteException;

	/**
	 * Get the list of securities pledged for this borrower type.
	 * @param aLimitProfileID the limit profile ID
	 * @param pledgorID the pledgor ID
	 * @return HashMap - stores the securities pledged for this customer
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on errors
	 */
	public HashMap getSecuritiesPledged(long aLimitProfileID, long pledgorID) throws SearchDAOException,
			RemoteException;

	/**
	 * FOR CR CMS-662 Get all CHECKLIST related to a SECURITY based on
	 * CMS_COLLATERAL_ID.
	 * @param aCollateralID of long type
	 * @return int - the number of checklist related to security.
	 * @throws SearchDAOException and RemoteException on errors
	 */
	public int getSecurityChkListCount(long aCollateralID) throws SearchDAOException, RemoteException;

	/**
	 * FOR CR CMS-310 Get the list of checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param asOfDate of String type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListAudit[] getCheckListAudit(long aLimitProfileID, String asOfDate) throws SearchDAOException,
			RemoteException;

	/**
	 * FOR CR CMS-310 Get the list of checklist items that requires auditing
	 * @param aCustomerID of long type
	 * @param asOfDate of String type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListAudit[] getCheckListAuditForNonBorrower(long aCustomerID, String asOfDate)
			throws SearchDAOException, RemoteException;

	/**
	 * Get the checklist ID of those pledgor checklist that is no longer valid
	 * due to this coborrower
	 * @param aCoBorrowerLegalRef of long type
	 * @param aLimitProfileRef of long type
	 * @return Long[] - the list of checklist IDs of those pledgor checklist
	 *         affected
	 * @throws SearchDAOException and RemoteException on error
	 */
	public Long[] getAffectedPledgorCheckList(long aCoBorrowerLegalRef, long aLimitProfileRef)
			throws SearchDAOException, RemoteException;

	/**
	 * Get the limit profile IDs that are linked to this pledgor
	 * @param aPledgorLegalRef of long type
	 * @return Long[] - the list of limit profile IDs linked to this pledgor
	 * @throws SearchDAOException and RemoteException on error
	 */
	public Long[] getAffectedLimitProfileID(long aPledgorLegalRef) throws SearchDAOException, RemoteException;

	// Start for cr-17
	public List getCheckListDetailsByCheckListId(String[] aCheckListId, String categoryType) throws SearchDAOException,
			RemoteException;

	public List getCheckListDetailsByCheckListId(String[] aCheckListId) throws SearchDAOException, RemoteException;
	// public List getCheckListDetailsByCheckListId(String[] aCheckListId,
	// String categoryType,String subCategoryType) throws SearchDAOException,
	// RemoteException;
	// public OBShareDoc getLeName(long aProfileId) throws SearchDAOException,
	// RemoteException;
	// public OBShareDoc getSecuritySubDetails(long aProfileId,long
	// collateralId) throws SearchDAOException, RemoteException;
	// End for cr-17
}