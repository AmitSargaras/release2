/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ICheckListDAO.java,v 1.45 2006/10/09 09:44:23 pratheepa Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.chktemplate.bus.DocumentHeldSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICriInfo;
import com.integrosys.cms.app.limit.bus.ILimitProfile;

/**
 * This interface defines the constant specific to the checklist table and the
 * methods required by the checklist
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.45 $
 * @since $Date: 2006/10/09 09:44:23 $ Tag: $Name: $
 */
public interface ICheckListDAO extends ICheckListTableConstants, ICheckListItemTableConstants {
	/**
	 * Get the number of limits that a collateral is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @return int - the number of limits that a collateral is related in a
	 *         limit profile
	 * @throws SearchDAOException on errors
	 */
	public int getLimitProfileCollateralCount(long aLimitProfileID, long aCollateralID) throws SearchDAOException;

	public int getLimitProfilePledgorCount(long aLimitProfileID, long aPledgorID, long[] aDeletedLimitSecMapList)
			throws SearchDAOException;

	/**
	 * Get the number of limits that a pledgor is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aPledgorID of long type
	 * @return int - the number of limits that a pledgor is related in a limit
	 *         profile
	 * @throws SearchDAOException on errors
	 */
	public int getLimitProfilePledgorCount(long aLimitProfileID, long aPledgorID) throws SearchDAOException;

	/**
	 * Get the number of limits that a coborrower is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return int - the number of limits that a coborrower is related in a
	 *         limit profile
	 * @throws SearchDAOException on errors
	 */
	public int getLimitProfileCoBorrowerCount(long aLimitProfileID, long aCustomerID) throws SearchDAOException;

	/**
	 * Get Hashtable with collateral ID and the status
	 * @param aLimitProfileID of long type
	 * @return HashMap - this the security ID as the key and the status as the
	 *         value
	 * @throws SearchDAOException on errors
	 */
	public HashMap getCollateralCheckListStatus(long aLimitProfileID) throws SearchDAOException;
	
	
	public CheckListSearchResult getCheckListByCollateralID(long collateralId) throws SearchDAOException;

	public CheckListSearchResult getCAMCheckListByCategoryAndProfileID(String category,long collateralID) throws SearchDAOException;
	
	
	
	public CheckListSearchResult[] getCheckListByCategory(String category) throws SearchDAOException;
	
	
	public CheckListSearchResult getPariPassuCheckListByCategoryAndProfileID(String category,long collateralID) throws SearchDAOException;
	/**
	 * Get Hashtable with borrower ID/pledger ID and the status
	 * @param aLimitProfileID of long type
	 * @param isFullListInd of boolean
	 * @return HashMap - this the borrowerID/pledgerID as the key and the status
	 *         as the value
	 * @throws SearchDAOException on errors
	 */
	public HashMap getCCCheckListStatus(long aLimitProfileID, boolean isFullListInd) throws SearchDAOException;

	
	public HashMap getFacilityCheckListStatus(long aLimitProfileID, boolean isFullListInd) throws SearchDAOException;

	/**
	 * Get Hashtable with borrower ID/pledger ID and the status for deleted
	 * checklist
	 * @param aLimitProfileID of long type
	 * @param aDeletedInd of boolean
	 * @return HashMap - this the borrowerID/pledgerID as the key and the status
	 *         as the value
	 * @throws SearchDAOException on errors
	 */
	public CCCheckListSummary[] getCCCheckListList(long aLimitProfileID, boolean aDeletedInd) throws SearchDAOException;

	public CCCheckListSummary[] getCCCheckListListForNonBorrower(long aCustomerID, boolean aDeletedInd)
			throws SearchDAOException;

	/**
	 * Get Hashtable with customerID and the status
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return HashMap - this the customerID as the key and the status as the
	 *         value
	 * @throws SearchDAOException on errors
	 */
	public HashMap getCCCheckListStatusForNonBorrower(long aLimitProfileID, long aCustomerID) throws SearchDAOException;

	/**
	 * Get the list of checklist item operation
	 * @return ICheckListItemOperation[] - the list of checklist item
	 * @throws SearchDAOException on errors
	 */
	public ICheckListItemOperation[] getCheckListItemOperation() throws SearchDAOException;

	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param anICheckListOwner of ICheckListOwner
	 * @param aStatusList of String[] type
	 * @return CheckListSearchResult[] - the list of checklist result
	 * @throws SearchDAOException
	 */
	public CheckListSearchResult[] getCheckList(ICheckListOwner anICheckListOwner, String[] aStatusList)
			throws SearchDAOException;

	/**
	 * Get the checklist item for waiver/deferral generation
	 * @param aLimitProfileID of long type
	 * @param anItemStatus of String type
	 * @return HashMap - the checkListID and the list of checklist item (not the
	 *         full detail)
	 * @throws SearchDAOException
	 */
	public HashMap getCheckListItemListbyStatus(long aLimitProfileID, String anItemStatus) throws SearchDAOException;

    /**
     * Get the checklist item by checklist category
     *
     * @param aLimitProfileID of long type
     * @param aCheckListCategory    of String type
     * @return HashMap - the checkListID and the list of checklist item (not the full detail)
     * @throws SearchDAOException
     */
    public HashMap getCheckListItemListbyCategory(long aLimitProfileID, String aCheckListCategory) throws SearchDAOException;

	/**
	 * Get the checklist item for waiver/deferral generation for non borrower
	 * @param aCustomerID of long type
	 * @param anItemStatus of String type
	 * @return HashMap - the checkListID and the list of checklist item (not the
	 *         full detail)
	 * @throws SearchDAOException
	 */
	public HashMap getCheckListItemListbyStatusForNonBorrower(long aCustomerID, String anItemStatus)
			throws SearchDAOException;

	/**
	 * Get the list of Checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param aStatusList of String[] type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 */
	public ICheckListAudit[] getCheckListAuditList(long aLimitProfileID, String[] aStatusList)
			throws SearchDAOException;

	/**
	 * Get the list of Checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param aStatusList of String[] type
	 * @return IAuditItem[] - the list of checklist items that requires auditing
	 * @throws SearchDAOException on errors
	 */
	public IAuditItem[] getCheckListItemListForAudit(long aLimitProfileID, String[] aStatusList)
			throws SearchDAOException;

	/**
	 * Get the list of Checklist items that requires auditing for non borrower
	 * @param aCustomerID of long type
	 * @param aStatusList of String[] type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 */
	public ICheckListAudit[] getCheckListAuditListForNonBorrower(long aCustomerID, String[] aStatusList)
			throws SearchDAOException;

	/**
	 * Get the list of Checklist items that requires auditing for non borrower
	 * @param aCustomerID of long type
	 * @param aStatusList of String[] type
	 * @return IAuditItem[] - the list of checklist items that requires auditing
	 * @throws SearchDAOException on errors
	 */
	public IAuditItem[] getCheckListItemListForAuditForNonBorrower(long aCustomerID, String[] aStatusList)
			throws SearchDAOException;

	/**
	 * Get the list of document categories for this borrower.
	 * @param aLimitProfileID of long type
	 * @return HashMap - the list of document categories
	 * @throws SearchDAOException on errors
	 */
	public HashMap getDocumentCategories(long aLimitProfileID) throws SearchDAOException;

	/**
	 * Get the list of document categories for this non-borrower.
	 * @param aCustomerID of long type
	 * @return HashMap - the list of document categories
	 * @throws SearchDAOException on errors
	 */
	public HashMap getDocumentCategoriesForNonBorrower(long aCustomerID, long aLimitProfileID)
			throws SearchDAOException;

	/**
	 * Get the map of documents held given the search criteria.
	 * 
	 * @param criteria - DocumentHeldSearchCriteria
	 * @return HashMap - Map of list of document held fulfilling the criteria
	 * @throws SearchDAOException on errors
	 */
	public HashMap getDocumentsHeld(DocumentHeldSearchCriteria criteria) throws SearchDAOException;

	/**
	 * Get the list of securities pledged for this borrower type.
	 * @param aLimitProfileID the limit profile ID
	 * @param pledgorID the pledgor ID
	 * @return HashMap - the securities pledged for this customer
	 * @throws SearchDAOException on errors
	 */
	public HashMap getSecuritiesPledged(long aLimitProfileID, long pledgorID) throws SearchDAOException;

	/**
	 * FOR CR CMS-662 Get all CHECKLIST related to a SECURITY based on
	 * CMS_COLLATERAL_ID.
	 * @param aCollateralID of long type
	 * @return int - the number of checklist related to security.
	 * @throws SearchDAOException on errors
	 */

	public int getSecurityChkListCount(long aCollateralID) throws SearchDAOException;

	/**
	 * FOR CR CMS-310 Get the list of Checklist items that requires auditing for
	 * non borrower
	 * @param aCustomerID of long type
	 * @param asOfDate of String type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 */
	public ICheckListAudit[] getCheckListAuditForNonBorrower(long aCustomerID, String asOfDate)
			throws SearchDAOException;

	/**
	 * FOR CR CMS-310 Get the list of Checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param asOfDate of String type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 */
	public ICheckListAudit[] getCheckListAudit(long aLimitProfileID, String asOfDate) throws SearchDAOException;

	/**
	 * Get the checklist ID of those pledgor checklist that is no longer valid
	 * due to this coborrower
	 * @param aCoBorrowerLegalRef of long type
	 * @param aLimitProfileRef of long type
	 * @return long[] - the list of checklist IDs of those pledgor checklist
	 *         affected
	 * @throws SearchDAOException on error
	 */
	public Long[] getAffectedPledgorCheckList(long aCoBorrowerLegalRef, long aLimitProfileRef)
			throws SearchDAOException;

	/**
	 * Get the limit profile IDs that are linked to this pledgor
	 * @param aPledgorLegalRef of long type
	 * @return long[] - the list of limit profile IDs linked to this pledgor
	 * @throws SearchDAOException on error
	 */
	public Long[] getAffectedLimitProfileID(long aPledgorLegalRef) throws SearchDAOException;

	/**
	 * Search checklist based on the criteria specified. Currently only used to
	 * search for checklist pending multi-level approval.
	 * 
	 * @param criteria of type CheckListSearchCriteria
	 * @return CheckListSearchResult[]
	 * @throws SearchDAOException on errors encountered searching for checklist
	 */
	public CheckListSearchResult[] searchCheckList(CheckListSearchCriteria criteria) throws SearchDAOException;

	/**
	 * Get checklist item sequence number. This number is useful if the caller
	 * needs to set checklist item reference before the actual primary key
	 * creation.
	 * 
	 * @return checklist item sequence number
	 */
	public long getCheckListItemSeqNo();

	// Start for cr-17
	// public List getCheckListDetailsByCheckListId(String[] aCheckListId,String
	// categoryType, String subCategoryType) throws SearchDAOException ;
	// public IShareDoc[] getStagingSharedDocuments(long checkListItemId) throws
	// SearchDAOException;
	// public IShareDoc[] getSharedDocuments(long checkListItemId) throws
	// SearchDAOException;
	// public Collection retrieveLeIDNameForStagingShareDoc(String[]
	// checkListIds) throws SearchDAOException;
	// public Collection retrieveLeIDNameForActualShareDoc(String[]
	// checkListIds) throws SearchDAOException;
	public ArrayList getCheckListDetailsByCheckListId(String[] aCheckListId) throws SearchDAOException;

	public ArrayList getCheckListDetailsByCheckListId(String[] aCheckListId, String category) throws SearchDAOException;

	// public List getCheckListDetailsByCheckListIdForChild(String[]
	// aCheckListId) throws SearchDAOException;
	// public List getShareDocumentItemId(long aCheckListId) throws
	// SearchDAOException;
	// public OBShareDoc getLeName(long aProfileId) throws SearchDAOException;
	// public OBShareDoc getSecuritySubDetails(long aProfileId,long
	// collateralId) throws SearchDAOException ;
	public HashMap getCheckListStatus(Long[] checkListIDList) throws SearchDAOException;

	public List getAllShareDocuments(long id, boolean isNonBorrower) throws SearchDAOException;
	
	public List getAllDeferCreditApprover() throws  SearchDAOException;
	
	public List getAllWaiveCreditApprover() throws  SearchDAOException;
	
	public List getAllBothCreditApprover() throws  SearchDAOException;

	// End for cr-17
	/**
	 * Generate DocementIDs.
	 * 
	 * @param limitProfileId of type long and CollateralId of type long
	 * @return Collection
	 * @throws CheckListException on any error encountered
	 */
	public ArrayList getMutiCustomerListByCollateralID(long collateralID) throws SearchDAOException;

	public ArrayList getSecurityOwnerList(long collateralID, long lmtProfileID) throws SearchDAOException;

	// Method added by Pratheepa on 29Sep2006 while fixing bug CMS-3479
	public ArrayList getDocumentIdsForCheckList(ArrayList documentIds) throws SearchDAOException;

	public HashMap getCollateralIdForSharedDocs(long documentNo, Long[] collateralIds) throws SearchDAOException;

	public HashMap getSecuritySubTypes(String secType) throws SearchDAOException;

	public HashMap getStateDistrictMukim(HashMap input) throws SearchDAOException;

	public HashMap getDocumentationStatus(long lmtProfileID) throws SearchDAOException;

	public boolean getChecklistCompletedStatus(long collID) throws SearchDAOException;

	public int getAssetResidualLife(String secSubTypeId) throws SearchDAOException;

    public HashMap[] getDetailsForPreDisbursementReminderLetter(long limitProfileID)
            throws SearchDAOException;

    public HashMap[] getDetailsForPostDisbursementReminderLetter(long limitProfileID)
            throws SearchDAOException;

    public ArrayList getMigratedCheckListItem(String limitId,String Category) throws Exception ;
    
    public boolean getErrorInChecklist(String checklistId) throws Exception;
    
    public boolean getErrorInChecklistItemDocCode(String checklistId,String documentCode) throws Exception ;
    
    public void updateOldCheckLists(long limitProfileID) ;
    
    public HashMap getBulkCAMCheckListByCategoryAndProfileID(String category,long collateralID) throws SearchDAOException;

	public HashMap getSearchByCAM(long limitProfileID,String searchCam) throws SearchDAOException;
	
	public CheckListSearchResult getCAMCheckListByCategoryAndProfileIDMaintain(String category,long collateralID) throws SearchDAOException;
	
	public ICheckListItem[] searchDoc(long collateralID,String list,String searchType,String search) throws SearchDAOException;
	
	public ICheckListItem[] searchStageDoc(long checkListId,String list,String searchType,String search) throws SearchDAOException;

	
    public long getCollateralIdMap(String custID,String docCode) throws SearchDAOException ;
    
    public ILimitProfile retriveCam(String camNo);
    
    public List getAllCheckListId(String limitId);
    
    public List getAllCheckListItem(String checklistId);
    
    public String getFacilityInfo(long id);
    
    public String getSecurityInfo(long id);
    
    public List getSearchCheckListId(String limitId, String category);
    
    public List getSearchCheckListItem(String checklistId,String status,String description);
    
    public ICheckListItem viewCheckListItem(long id);
    
    public List getAllCheckListStatus(String checklistId);
    
    public ICheckList getCAM(String limitId);
    
    public ICheckListItem getCheckListItem(long docId);
    
    public int updateLAD(long docId,String version,String status,String amount,String docDate,String expiryDate);

  //Uma:Start:Prod issue: To add masterIdlist for facility checklist
    public String getMasterListId(String facilityCode);
    public void updateCheckListMasterlistId(String masterListId,String apprLimitId);
  //Uma:End:Prod issue: To add masterIdlist for facility checklist
    
    //Uma Khot::Insurance Deferral maintainance
    public String getChecklistId(String Category,long lspLmtProfileId);

	public String getChecklistIdByCustomerId(String string, long customerID);

	public int getOtherChecklistCount(String string, String checklistId);
    
    public int getCountSecurityDocsInCheckList(long aCollateralID) throws SearchDAOException ;

	public int getPendingChecklistCount(long checkListID);

	public int getPendingStageChecklistCount(String category, long limitProfileID);
	
	public String getChecklistIdByLimitId(String string, long customerID);

	public List getAllRamratingDocumentlist(ICriInfo[] criList);

	public void updateChecklist(String docId,String checkListId, long limitProfileID ,String docRefId);

	public String getDocSeqId();

	public void updateChecklistMarchEnding(String docId, String checkListId,
			long limitProfileID,String docRefId);

	public void updateChecklistDetails(String checkListId);

	public boolean getRamRatingDocument(long limitProfileID);

	public boolean isRamDocAlreadyExist(long limitProfileID);
	
	public String getReferenceId(String trxId);

	public String getDocumentCount();
	
	public String getOtherChecklistCountPartyName(String string, String checklistId);
}
