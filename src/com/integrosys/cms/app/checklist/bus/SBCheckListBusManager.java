/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/SBCheckListBusManager.java,v 1.61 2006/10/09 05:41:15 hshii Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.DocumentHeldSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException;
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * Session bean remote interface for the services provided by the checklist bus
 * manager
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.61 $
 * @since $Date: 2006/10/09 05:41:15 $ Tag: $Name: $
 */
public interface SBCheckListBusManager extends EJBObject {

    /**
	 * Get the global items that are not in the checklist as well as those that
	 * are newly added at template level
	 * @param anICheckList - ICheckList
	 * @return IItem[] - the list of items that are not in the checkList as well
	 *         as those newly added at template level
	 * @throws CheckListException on errors
	 * @throws SearchDAOException is DAO errors
	 * @throws RemoteException on remote errors
	 */
	public IItem[] getItemList(ICheckList anICheckList) throws CheckListTemplateException, CheckListException, SearchDAOException, RemoteException;


        /**
	 * Get the global items that are not in the checklist as well as those that
	 * are newly added at template level
	 * @param anICheckList - ICheckList
	 * @param collateralID - String
	 * @return IItem[] - the list of items that are not in the checkList as well
	 *         as those newly added at template level
	 * @throws CheckListException on errors
	 * @throws SearchDAOException is DAO errors
	 * @throws RemoteException on remote errors
	 */
    //kc       last
	public IItem[] getItemList(ICheckList anICheckList, String collateralID) throws CheckListTemplateException, CheckListException, SearchDAOException, RemoteException;

    /**
	 * Get Hashtable with borrower ID/pledger ID and the status
	 * @param aLimitProfileID of long type
	 * @return HashMap - this the borrowerID/pledgerID as the key and the status
	 *         as the value
	 * @throws CheckListException
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getCCCheckListStatus(long aLimitProfileID, boolean isFullListInd) throws SearchDAOException,
			CheckListException, RemoteException;
	
	
	public HashMap getFacilityCheckListStatus(long aLimitProfileID, boolean isFullListInd) throws SearchDAOException,
	CheckListException, RemoteException;

	/**
	 * Get Hashtable with borrower ID/pledger ID and the status for deleted
	 * checklist
	 * @param aLimitProfileID of long type
	 * @return CCCheckListSummary[] - this the borrowerID/pledgerID as the key
	 *         and the status as the value
	 * @throws CheckListException
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public CCCheckListSummary[] getDeletedCCCheckListStatus(long aLimitProfileID) throws SearchDAOException,
			CheckListException, RemoteException;

	public CCCheckListSummary[] getDeletedCCCheckListStatusForNonBorrower(long aCustomerID) throws SearchDAOException,
			CheckListException, RemoteException;

	public CCCheckListSummary[] getCCCheckListList(long aLimitProfileID) throws SearchDAOException, CheckListException,
			RemoteException;

	public CCCheckListSummary[] getCCCheckListListForNonBorrower(long aCustomerID) throws SearchDAOException,
			CheckListException, RemoteException;

	/**
	 * Get Hashtable with non borrower ID and the status
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return HashMap - this the non borrowerID as the key and the status as
	 *         the value
	 * @throws CheckListException
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getCCCheckListStatusForNonBorrower(long aLimitProfileID, long aCustomerID)
			throws SearchDAOException, CheckListException, RemoteException;

	/**
	 * Get the number of limits that a collateral is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @return int - the number of limits that a collateral is related in a
	 *         limit profile
	 * @throws SearchDAOException , CheckListException, RemoteException on
	 *         errors
	 */
	public int getLimitProfileCollateralCount(long aLimitProfileID, long aCollateralID) throws SearchDAOException,
			CheckListException, RemoteException;

	public int getLimitProfilePledgorCount(long aLimitProfileID, long aPledgorID, long[] aDeletedLimitSecMapList)
			throws SearchDAOException, CheckListException, RemoteException;

	/**
	 * Get the number of limits that a pledgor is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aPledgorID of long type
	 * @return int - the number of limits that a pledgor is related in a limit
	 *         profile
	 * @throws SearchDAOException , CheckListException, RemoteException on
	 *         errors
	 */
	public int getLimitProfilePledgorCount(long aLimitProfileID, long aPledgorID) throws SearchDAOException,
			CheckListException, RemoteException;

	/**
	 * Get the number of limits that a coborrower is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return int - the number of limits that a coborrower is related in a
	 *         limit profile
	 * @throws SearchDAOException , CheckListException, RemoteException on
	 *         errors
	 */
	public int getLimitProfileCoBorrowerCount(long aLimitProfileID, long aCustomerID) throws SearchDAOException,
			CheckListException, RemoteException;

	/**
	 * Get Hashtable with collateral ID and the status
	 * @param aLimitProfileID of long type
	 * @return HashMap - this the security ID as the key and the status as the
	 *         value
	 * @throws CheckListException
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getCollateralCheckListStatus(long aLimitProfileID) throws SearchDAOException, CheckListException,
			RemoteException;

	/**
	 * Formulate the default checklist from the template selected based on the
	 * law, legal constitution and country
	 * @param anICheckListOwner - ICheckListOwner
	 * @param aLegalConstitution - String
	 * @param anIBookingLocation - IBookingLocation
	 * @return ICheckList - the default checklist
	 * @throws com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException if there is no template setup for the
	 *         checklist
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckList getDefaultCCCheckList(ICheckListOwner anICheckListOwner, String aLegalConstitution,
			IBookingLocation anIBookingLocation, String law)
            throws TemplateNotSetupException, CheckListTemplateException, CheckListException, RemoteException;

    /**
     * For use by SI to retrieve the default cc checklist
     * @param legalConstitution - legal constituition
     * @param country - country
     * @param law - law
     * @return
     * @throws TemplateNotSetupException if there is no template setup for the checklist
     * @throws CheckListTemplateException on errors
     * @throws CheckListException on errors
     */
    public ICheckList getDefaultCCCheckList(String customerType, String legalConstitution, String country, String law)
            throws TemplateNotSetupException, CheckListTemplateException, CheckListException, RemoteException;


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
	 * @throws RemoteException on remote errors
	 */
	public ICheckList getDefaultCollateralCheckList(ICheckListOwner anICheckListOwner, String aCollateralType,
			String aCollateralSubType, String aCountry, String aOrgCode) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException, RemoteException;


    /**
     * For use by SI to retrieve the default collateral checklist
     * @param country - country
     * @param collateralType - collateral type
     * @param collateralSubType - collateral subtype
     * @param applicationType - application type
     * @param goodsStatus - goods status
     * @param pbrInd - pbr/pbt indicator
     * @return default collateral checklist
     * @throws TemplateNotSetupException
     * @throws CheckListTemplateException
     * @throws CheckListException
     */
    public ICheckList getDefaultCollateralCheckList(String country, String collateralType, String collateralSubType,
                                                    String applicationType, String goodsStatus, String pbrInd)
            throws TemplateNotSetupException, CheckListTemplateException, CheckListException, RemoteException;
    

    /**
     * For use by SI to retrieve the default collateral checklist
     * @param country - country
     * @param collateralType - collateral type
     * @param collateralSubType - collateral subtype
     * @param applicationType - application type
     * @param goodsStatus - goods status
     * @param pbrInd - pbr/pbt indicator
     * @return default collateral checklist
     * @throws TemplateNotSetupException
     * @throws CheckListTemplateException
     * @throws CheckListException
     */
    public ICheckList getDefaultCAMCheckList(ICheckListOwner anICheckListOwner,String country, String collateralType, String collateralSubType,
                                                    String applicationType, String goodsStatus, String pbrInd)
            throws TemplateNotSetupException, CheckListTemplateException, CheckListException, RemoteException;
    
    
    public ICheckList getDefaultFacilityCheckList(ICheckListOwner anICheckListOwner,String country, String collateralType, String collateralSubType,
            String applicationType, String goodsStatus, String pbrInd)
    		throws TemplateNotSetupException, CheckListTemplateException, CheckListException, RemoteException;

    
    
    /**
     * For use by SI to retrieve the default collateral checklist
     * @param country - country
     * @param collateralType - collateral type
     * @param collateralSubType - collateral subtype
     * @param applicationType - application type
     * @param goodsStatus - goods status
     * @param pbrInd - pbr/pbt indicator
     * @return default collateral checklist
     * @throws TemplateNotSetupException
     * @throws CheckListTemplateException
     * @throws CheckListException
     */
    public ICheckList getDefaultPariPassuCheckList(ICheckListOwner anICheckListOwner, String collateralType, String collateralSubType,
    							String country, String orgCode)
            throws TemplateNotSetupException, CheckListTemplateException, CheckListException, RemoteException;
    
    /**
	 * Create a checklist
	 * @param anIChecklist - ICheckList
	 * @return ICheckList - the checkList being created
	 * @throws CheckListException
	 * @throws RemoteException
	 */
	public ICheckList create(ICheckList anIChecklist) throws CheckListException, RemoteException;

	/**
	 * Create a checklist without deriving the checklist status
	 * @param anIChecklist - ICheckList
	 * @return ICheckList - the checkList being created
	 * @throws CheckListException
	 * @throws RemoteException
	 */
	public ICheckList createCheckList(ICheckList anIChecklist) throws CheckListException, RemoteException;

	/**
	 * Update a checklist
	 * @param anICheckList - ICheckList
	 * @return ICheckList - the checkList being updated
	 * @throws ConcurrentUpdateException
	 * @throws CheckListException
	 * @throws RemoteException
	 */
	public ICheckList update(ICheckList anICheckList) throws ConcurrentUpdateException, CheckListException,
			RemoteException;

	/**
	 * Update a checklist without deriving the checklist status
	 * @param anICheckList - ICheckList
	 * @return ICheckList - the checkList being updated
	 * @throws ConcurrentUpdateException
	 * @throws CheckListException on errors
	 * @throws RemoteException on errors
	 */
	public ICheckList updateCheckList(ICheckList anICheckList) throws ConcurrentUpdateException, CheckListException,
			RemoteException;

	/**
	 * Get a checklist by ID
	 * @param aCheckListID - long
	 * @return ICheckList - the checklist
	 * @throws CheckListException
	 * @throws RemoteException
	 */
	public ICheckList getCheckListByID(long aCheckListID) throws CheckListException, RemoteException;
	
	
	public CheckListSearchResult getCheckListByCollateralID(long aCheckListID) throws CheckListException, RemoteException;

	public CheckListSearchResult getCAMCheckListByCategoryAndProfileID(String category,long collateralID) throws CheckListException, RemoteException;
	
	public CheckListSearchResult[] getCheckListByCategory(String category) throws CheckListException, RemoteException;
	
	public CheckListSearchResult getPariPassuCheckListByCategoryAndProfileID(String category,long paripassuId) throws CheckListException, RemoteException;
	/**
	 * Get the list of allowable operation for the checklist items
	 * @return ICheckListOperationList[] - the list of allowed checklist item
	 *         operations
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListItemOperation[] getAllowableOperationList() throws CheckListException, RemoteException;

	/**
	 * Update the checklist item status by the checklist item ID
	 * @param aCheckListItemID of long type
	 * @param aStatus of String type
	 * @return ICheckListItem - the checklist item updated
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListItem updateCheckListItemStatusByID(long aCheckListItemID, String aStatus)
			throws CheckListException, RemoteException;

	/**
	 * Get a checklist item by the item reference
	 * @param aCheckListItemRef of long type
	 * @return ICheckListItem - the checklist item with the checklist item
	 *         reference
	 * @throws CheckListException
	 */
	public ICheckListItem getCheckListItemByRef(long aCheckListItemRef) throws CheckListException, RemoteException;

	/**
	 * Update the checklist item status by the checklist item ref
	 * @param aCheckListItemRef of long type
	 * @param aStatus of String type
	 * @return ICheckListItem - the checklist item updated
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListItem updateCheckListItemStatusByRef(long aCheckListItemRef, String aStatus)
			throws CheckListException, RemoteException;

	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param anICheckListOwner of ICheckListOwner
	 * @param aStatusList of String[] type
	 * @return CheckListSearchResult[] - the list of checklist result
	 * @throws SearchDAOException
	 * @throws CheckListException
	 * @throws RemoteException on remote errors
	 */
	public CheckListSearchResult[] getCheckList(ICheckListOwner anICheckListOwner, String[] aStatusList)
			throws SearchDAOException, CheckListException, RemoteException;


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
			CheckListException, RemoteException;

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
            CheckListException, RemoteException;

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
			throws SearchDAOException, CheckListException, RemoteException;

	/**
	 * Get the list of checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param aStatusList of String[] type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListAudit[] getCheckListAuditList(long aLimitProfileID, String[] aStatusList)
			throws SearchDAOException, CheckListException, RemoteException;

	/**
	 * Get the list of Checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param aStatusList of String[] type
	 * @return IAuditItem[] - the list of checklist items that requires auditing
	 * @throws SearchDAOException on DAO errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public IAuditItem[] getCheckListItemListForAudit(long aLimitProfileID, String[] aStatusList)
			throws SearchDAOException, CheckListException, RemoteException;

	/**
	 * Get the list of checklist items that requires auditing
	 * @param aCustomerID of long type
	 * @param aStatusList of String[] type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListAudit[] getCheckListAuditListForNonBorrower(long aCustomerID, String[] aStatusList)
			throws SearchDAOException, CheckListException, RemoteException;

	/**
	 * Get the list of Checklist items that requires auditing for non borrower
	 * @param aCustomerID of long type
	 * @param aStatusList of String[] type
	 * @return IAuditItem[] - the list of checklist items that requires auditing
	 * @throws SearchDAOException on DAO errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public IAuditItem[] getCheckListItemListForAuditForNonBorrower(long aCustomerID, String[] aStatusList)
			throws SearchDAOException, CheckListException, RemoteException;

	/**
	 * Get the document categories for this borrower.
	 * @param aLimitProfileID of long type
	 * @return HashMap - the map of document categories
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getDocumentCategories(long aLimitProfileID) throws SearchDAOException, CheckListException,
			RemoteException;

	/**
	 * Get the document categories for this non-borrower.
	 * @param aCustomerID of long type
	 * @return HashMap - the map of document categories
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getDocumentCategoriesForNonBorrower(long aCustomerID, long aLimitProfileID)
			throws SearchDAOException, CheckListException, RemoteException;

	/**
	 * Get the map of documents held given the search criteria.
	 * 
	 * @param criteria - DocumentHeldSearchCriteria
	 * @return HashMap - Map of list of document held fulfilling the criteria
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getDocumentsHeld(DocumentHeldSearchCriteria criteria) throws SearchDAOException, CheckListException,
			RemoteException;

	/**
	 * Get the list of securities pledged for this borrower type.
	 * @param aLimitProfileID the limit profile ID
	 * @param pledgorID the pledgor ID
	 * @return HashMap - the securities pledged for this customer
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on errors
	 */
	public HashMap getSecuritiesPledged(long aLimitProfileID, long pledgorID) throws SearchDAOException,
			CheckListException, RemoteException;

	/**
	 * Get the collateral checklist using the limit profile ID
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @return ICheckList - the collateral checklist
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckList getCollateralCheckList(long aLimitProfileID, long aCollateralID) throws CheckListException,
			RemoteException;

	/**
	 * FOR CR CMS-662 Get all CHECKLIST related to a SECURITY based on
	 * CMS_COLLATERAL_ID.
	 * @param aCollateralID of long type
	 * @return int - the number of checklist related to security.
	 * @throws SearchDAOException , CheckListException,RemoteException on errors
	 */
	public int getSecurityChkListCount(long aCollateralID) throws SearchDAOException, CheckListException,
			RemoteException;

	/**
	 * FOR CR CMS-310 Get the list of checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param asOfDate of String type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListAudit[] getCheckListAudit(long aLimitProfileID, String asOfDate) throws SearchDAOException,
			CheckListException, RemoteException;

	/**
	 * FOR CR CMS-310 Get the list of checklist items that requires auditing
	 * @param aCustomerID of long type
	 * @param asOfDate of String[] type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListAudit[] getCheckListAuditForNonBorrower(long aCustomerID, String asOfDate)
			throws SearchDAOException, CheckListException, RemoteException;

	/**
	 * Get the checklist ID of those pledgor checklist that is no longer valid
	 * due to this coborrower
	 * @param aCoBorrowerLegalRef of long type
	 * @param aLimitProfileRef of long type
	 * @return Long[] - the list of checklist IDs of those pledgor checklist
	 *         affected
	 * @throws SearchDAOException , CheckListException, RemoteException on error
	 */
	public Long[] getAffectedPledgorCheckList(long aCoBorrowerLegalRef, long aLimitProfileRef)
			throws SearchDAOException, CheckListException, RemoteException;

	/**
	 * Get the limit profile IDs that are linked to this pledgor
	 * @param aPledgorLegalRef of long type
	 * @return Long[] - the list of limit profile IDs linked to this pledgor
	 * @throws SearchDAOException , CheckListException, RemoteException on error
	 */
	public Long[] getAffectedLimitProfileID(long aPledgorLegalRef) throws SearchDAOException, CheckListException,
			RemoteException;

	/**
	 * Search checklist based on the criteria specified. Currently only used to
	 * search for checklist pending multi-level approval.
	 * 
	 * @param criteria of type CheckListSearchCriteria
	 * @return CheckListSearchResult[]
	 * @throws CheckListException on errors encountered
	 */

	// Start for cr-17
	public CheckListSearchResult[] searchCheckList(CheckListSearchCriteria criteria) throws CheckListException,
			RemoteException;

	public List getCheckListDetailsByCheckListId(String[] aCheckListId, String categoryType) throws SearchDAOException,
			CheckListException, RemoteException;

	public List getCheckListDetailsByCheckListId(String[] aCheckListId) throws SearchDAOException, CheckListException,
			RemoteException;

	// public List getCheckListDetailsByCheckListId(String[] aCheckListId,
	// String categoryType,String subCategoryType) throws SearchDAOException,
	// CheckListException, RemoteException;
	// public OBShareDoc getLeName(long aProfileId) throws SearchDAOException,
	// CheckListException, RemoteException;
	// public OBShareDoc getSecuritySubDetails(long aProfileId, long
	// collateralId) throws SearchDAOException, CheckListException,
	// RemoteException;
	// End for cr-17
	public List getCustomerListByCollateralID(long collateralId) throws SearchDAOException, RemoteException;

	public List getSecurityOwnerList(long collateralID, long lmtProfileID) throws SearchDAOException, RemoteException;

    public HashMap[] getDetailsForPreDisbursementReminderLetter(long limitProfileID)
            throws SearchDAOException, CheckListException, RemoteException;

    public HashMap[] getDetailsForPostDisbursementReminderLetter(long limitProfileID)
            throws SearchDAOException, CheckListException, RemoteException;


    /**
     * Added by Anil
	 * Get a checklist item by the item id
	 * @param checkListItemId of long type
	 * @return ICheckListItem - the checklist item with the checklist item id
	 *         reference
	 * @throws CheckListException
	 */
	public ICheckListItem getCheckListItemById(long checkListItemId) throws CheckListException, RemoteException;


//	public ICheckList update(ICheckList updActual, String flagSchedulers) throws ConcurrentUpdateException, CheckListException,
//	RemoteException;
}
