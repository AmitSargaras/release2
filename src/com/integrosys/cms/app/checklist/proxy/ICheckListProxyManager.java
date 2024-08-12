/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/proxy/ICheckListProxyManager.java,v 1.107 2006/11/20 03:04:04 czhou Exp $
 */
package com.integrosys.cms.app.checklist.proxy;

//java

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.checklist.bus.CAMCheckListSummary;
import com.integrosys.cms.app.checklist.bus.CCCheckListSummary;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchCriteria;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
import com.integrosys.cms.app.checklist.bus.FacilityCheckListSummary;
import com.integrosys.cms.app.checklist.bus.IAuditItem;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICheckListItemOperation;
import com.integrosys.cms.app.checklist.bus.ICheckListOwner;
import com.integrosys.cms.app.checklist.bus.PariPassuCheckListSummary;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.DocumentHeldSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.IContext;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the list of attributes that will be available to a
 * checklist
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.107 $
 * @since $Date: 2006/11/20 03:04:04 $ Tag: $Name: $
 */
public interface ICheckListProxyManager {
	/**
	 * Get transaction sub-type for a checklist transaction.
	 * 
	 * @param aTrxID - primitive long denoting the checklist transaction
	 * @return String denoting the transaction sub-type
	 * @throws CheckListException on errors
	 */
	public String[] getTrxSubTypeByTrxID(long aTrxID) throws CheckListException;

	/**
	 * Get the global items that are not in the checklist as well as those that
	 * are newly added at template level
	 * @param anICheckList of ICheckList type
	 * @return IItem[] - the list of items that are not in the checkList as well
	 *         as those newly added at template level
	 * @throws CheckListException on errors
	 * @throws SearchDAOException is DAO errors
	 */
	public IItem[] getItemList(ICheckList anICheckList) throws CheckListTemplateException, CheckListException,
			SearchDAOException;

    	/**
	 * Get the global items that are not in the checklist as well as those that
	 * are newly added at template level
	 * @param anICheckList of ICheckList type
	 * @param collateralID - String
	 * @return IItem[] - the list of items that are not in the checkList as well
	 *         as those newly added at template level
	 * @throws CheckListException on errors
	 * @throws SearchDAOException is DAO errors
	 */
	public IItem[] getItemList(ICheckList anICheckList, String collateralID) throws CheckListTemplateException, CheckListException,
			SearchDAOException;

	/**
	 * Get the list of c/c checklist summary info.
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListTemplateException, CheckListException;
	
	public FacilityCheckListSummary[] getFacilityCheckListSummaryList(IContext anIContext, long aLimitProfileID)
	throws CheckListTemplateException, CheckListException;

	/**
	 * Get the list of c/c checklist summary info. This includes the deleted
	 * checklist as well.
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return HashMap - contains the list of cc summary for those checklist
	 *         that are deleted as well as not deleted
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public HashMap getAllCCCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListTemplateException, CheckListException;

	/**
	 * Get the list of c/c checklist summary info for non borrower
	 * @param anIContext of IContext type
	 * @param aCustomerID of long type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryListForNonBorrower(IContext anIContext, long aLimitProfileID,
			long aCustomerID, boolean isNBCheckListOnly) throws CheckListTemplateException, CheckListException;

	/**
	 * Get the list of c/c checklist summary info for non borrower including
	 * those that are deleted
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return HashMap - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public HashMap getAllCCCheckListSummaryListForNonBorrower(IContext anIContext, long aLimitProfileID,
			long aCustomerID) throws CheckListTemplateException, CheckListException;

	public HashMap getAllCCCheckListSummaryListForCustodian(IContext anIContext, long aLimitProfileID)
			throws CheckListTemplateException, CheckListException;

	/**
	 * Get the list of c/c checklist summary info for custodian.
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryListForCustodian(IContext anIContext, long aLimitProfileID)
			throws CheckListTemplateException, CheckListException;

	/**
	 * Get the list of c/c checklist summary info for cc collaboration task
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryForCollaboration(IContext anIContext, ILimitProfile anILimitProfile)
			throws CheckListTemplateException, CheckListException;

	/**
	 * Get the list of c/c checklist summary info for ccc generation
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryForCCC(IContext anIContext, ILimitProfile anILimitProfile)
			throws CheckListTemplateException, CheckListException;

	/**
	 * Get the list of collateral checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 */
	public CollateralCheckListSummary[] getCollateralCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListException;

	
	/**
	 * Get the list of PariPassu checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return PariPassuCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 */
	public PariPassuCheckListSummary[] getPariPassuCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListException;
	
	/**
	 * Get the list of CAM checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 */
	public CAMCheckListSummary[] getCAMCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListException;
	/**
	 * Get the list of collateral checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 */
	public HashMap getAllCollateralCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListException;
	
	public HashMap getAllCAMCheckListSummaryList(IContext anIContext, long aLimitProfileID)
	throws CheckListException;
	
	
	public HashMap getAllFacilityCheckListSummaryList(IContext anIContext, long aLimitProfileID)
	throws CheckListException;


	/**
	 * Get the list of collateral checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 */
	public CollateralCheckListSummary[] getFilteredCollateralCheckListSummaryList(IContext anIContext,
			long aLimitProfileID, boolean aCustodianInd) throws CheckListException;

	/**
	 * Get the list of collateral checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 */
	public HashMap getAllFilteredCollateralCheckListSummaryList(IContext anIContext, long aLimitProfileID,
			boolean aCustodianInd) throws CheckListException;

	/**
	 * Get Hashtable with collateral ID and the status
	 * @param aLimitProfileID of long type
	 * @return HashMap - this the security ID as the key and the status as the
	 *         value
	 * @throws SearchDAOException on errors
	 */
	public HashMap getCollateralCheckListStatus(long aLimitProfileID) throws SearchDAOException, CheckListException;

	public HashMap getCCCheckListStatus(long aLimitProfileID, boolean isFullListInd) throws SearchDAOException,
			CheckListException;

	/**
	 * Get the checklist based on the checklist ID
	 * @param aCheckListID of long type
	 * @return ICheckList - the checklist containing the checklist with the
	 *         specified ID
	 * @throws CheckListException on errors
	 */
	public ICheckList getCheckListByID(long aCheckListID) throws CheckListException;
	
	public CheckListSearchResult getCheckListByCollateralID(long collateralID) throws CheckListException;
	
	public CheckListSearchResult getCAMCheckListByCategoryAndProfileID(String category,long collateralID) throws CheckListException;
	
	public CheckListSearchResult[] getCheckListByCategory(String category) throws CheckListException;
	
	
	public CheckListSearchResult getPariPassuCheckListByCategoryAndProfileID(String category,long collateralID) throws CheckListException;

	/**
	 * Get the checklist trx value based on the checklist ID
	 * @param aCheckListID of long type
	 * @return ICheckListTrxValue - the checklist trx value containing the
	 *         checklist with the specified ID
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue getCheckList(long aCheckListID) throws CheckListException;

	/**
	 * Get a checklist by transaction ID
	 * @param aTrxID of String type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue getCheckListByTrxID(String aTrxID) throws CheckListException;

	/**
	 * Search checklist based on the criteria specified. Currently only used to
	 * search for checklist pending multi-level approval.
	 * 
	 * @param ctx transaction context
	 * @param criteria of type CheckListSearchCriteria
	 * @return search result
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors encountered
	 */
	public SearchResult searchCheckList(ITrxContext ctx, CheckListSearchCriteria criteria)
			throws CheckListTemplateException, CheckListException;

	/**
	 * Method will return the trx based on the checklist owner if there exist
	 * else it will return a new trx object with the default set of checklist
	 * items inherited from the template
	 * @param anICheckListOwner of ICheckListOwner type
	 * @param aLegalConstitution of String type
	 * @param anIBookingLocation of IBookingLocation type
	 * @return ICheckListTrxValue - the trx value checklist
	 * @throws TemplateNotSetupException if there is no template setup for the
	 *         checklist
	 * @throws CheckListException on errors
	 */
	public ICheckList getDefaultCCCheckList(ICheckListOwner anICheckListOwner, String aLegalConstitution,
			IBookingLocation anIBookingLocation, String law) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException;

	/**
	 * To retrieve default CC Checklist based on customer type, legal
	 * constitution, country and law.
	 * @param customerType - customer type (e.g. MAIN_BORROWER, PLEDGOR etc)
	 * @param legalConstitution - legal constituition
	 * @param country - country
	 * @param law - law
	 * @return A Default CC Checklist pertaining to the information supplied
	 * @throws TemplateNotSetupException if there is no template setup for the
	 *         checklist
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 * @deprecated use
	 *             {@link #getDefaultCCCheckList(ICheckListOwner, String, IBookingLocation, String)}
	 *             instead to provide more information using checklist owner.
	 */
	public ICheckList getDefaultCCCheckList(String customerType, String legalConstitution, String country, String law)
			throws TemplateNotSetupException, CheckListTemplateException, CheckListException;

	/**
	 * Method will return the trx based on the checklist owner if there exist
	 * else it will return a new trx object with the default set of checklist
	 * items inherited from the template
	 * @param anICheckListOwner of ICheckListOwner type
	 * @param aCollateralType of String type
	 * @param aCollateralSubType of String type
	 * @param aCountry of String type
	 * @return ICheckList - the default checklist
	 * @throws TemplateNotSetupException if there is no template setup for the
	 *         checklist
	 * @throws CheckListException on errors
	 */
	public ICheckList getDefaultCollateralCheckList(ICheckListOwner anICheckListOwner, String aCollateralType,
			String aCollateralSubType, String aCountry, String aOrgCode) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException;

	
	/**
	 * Method will return the trx based on the checklist owner if there exist
	 * else it will return a new trx object with the default set of checklist
	 * items inherited from the template
	 * @param anICheckListOwner of ICheckListOwner type
	 * @param aCollateralType of String type
	 * @param aCollateralSubType of String type
	 * @param aCountry of String type
	 * @return ICheckList - the default checklist
	 * @throws TemplateNotSetupException if there is no template setup for the
	 *         checklist
	 * @throws CheckListException on errors
	 */
	public ICheckList getDefaultPariPassuCheckList(ICheckListOwner anICheckListOwner, String aPariPassuType,
			String aPariPassuSubType, String aCountry, String aOrgCode) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException;
	
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
			String applicationType, String goodsStatus, String pbrInd) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException;
	
	

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
			String applicationType, String goodsStatus, String pbrInd) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException;
	
	

	public ICheckList getDefaultFacilityCheckList(ICheckListOwner anICheckListOwner,String country, String collateralType, String collateralSubType,
			String applicationType, String goodsStatus, String pbrInd) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException;

	/**
	 * Maker creation of a checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerCreateCheckList(ITrxContext anITrxContext, ICheckList anICheckList)
			throws CheckListException;

	/**
	 * Maker create a checklist without approval of checker. Meaning, direct
	 * create a checklist as a actual copy.
	 * 
	 * @param anITrxContext workflow context info
	 * @param anICheckList a checklist to be created directly
	 * @return workflow object with information on the created checklist
	 * @throws CheckListException if there is any error thrown from the
	 *         checklist core module
	 */
	public ICheckListTrxValue makerCreateCheckListWithoutApproval(ITrxContext anITrxContext, ICheckList anICheckList)
			throws CheckListException;

	/**
	 * Copy Checklist due to BCA Renewal
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue copyCheckList(ITrxContext anITrxContext, ICheckList anICheckList)
			throws CheckListException;

	/**
	 * Checker approves a checklist trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue checkerApproveCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException;

	/**
	 * Checker rejects a checklist trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue checkerRejectCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException;

	/**
	 * Maker closes a checklist trx that has been rejected by the checker
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerCloseCheckListTrx(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException;

	/**
	 * Maker edits a rejected checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerEditRejectedCheckListTrx(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList) throws CheckListException;

	/**
	 * Maker updates a checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerUpdateCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue,
			ICheckList anICheckList) throws CheckListException;

	/**
	 * Maker directly update the actual copy of checklist without submit to
	 * checker for further approval.
	 * 
	 * @param anITrxContext workflow context info
	 * @param anICheckListTrxValue a previous workflow value consist of previous
	 *        checklist created or updated
	 * @param anICheckList newly updated checklist to updated to actual copy
	 * @return a update workflow value consist of updated checklist
	 * @throws CheckListException if there is any error throw from the checklist
	 *         core module
	 */
	public ICheckListTrxValue makerUpdateCheckListWithoutApproval(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList) throws CheckListException;

	/**
	 * Maker deletes a checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerDeleteCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException;

	/**
	 * Maker updates a checklist receipt
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerUpdateCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList) throws CheckListException;

	/**
	 * Checker approves a checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue checkerApproveCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException;

	/*public ICheckListTrxValue checkerApproveCheckListReceiptScheduler(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException;*/
	/**
	 * Checker updates a checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue checkerUpdateCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException;

	/**
	 * Updates the status of the shared checklists. Should be called after
	 * Checker Approve Update Checklist / Receipt (for both cc and security)
	 * @param trxValue of ICheckListTrxValue type
	 * @throws CheckListException on errors
	 */
	public void updateSharedChecklistStatus(ICheckListTrxValue trxValue) throws CheckListException;

	/**
	 * Manager approves a checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue managerApproveCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException;

	/**
	 * Checker rejects a checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue checkerRejectCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException;

	/**
	 * Manager rejects a checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue managerRejectCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException;

	/**
	 * Maker closes a checklist receipt trx that has been rejected by the
	 * checker
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerCloseCheckListReceiptTrx(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException;

	/**
	 * Maker edits a rejected checklist receipt
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerEditRejectedCheckListReceiptTrx(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList) throws CheckListException;

	/**
	 * Maker save the checkList as draft
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerSaveCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue,
			ICheckList anICheckList) throws CheckListException;

	/**
	 * Maker cancel a save the checkList as draft
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerCancelSavedCheckList(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException;

	/**
	 * Retrieve next checklist receipts based on user action. This may result in
	 * the receipt state being modified and/or a new receipt being created.
	 * 
	 * @param receipt checklist receipt on which the action will be performed
	 * @param action user action on the checklist receipt
	 * @throws CheckListException error in retrieving next state of the
	 *         checklist receipt
	 */
	public ICheckListItem[] getNextCheckListReceipts(ICheckListItem receipt, String action) throws CheckListException;

	/**
	 * To get the next state of the checklist item
	 * @param aCurrentState of String type
	 * @param aCustodianState of String type
	 * @param anAction of String type
	 * @return HashMap - the next checklist item state and the custodian doc
	 *         state
	 * @throws CheckListException on errors
	 */
	public HashMap getNextCheckListItemState(String aCurrentState, String aCustodianState, String anAction)
			throws CheckListException;

	/**
	 * Reset a checklist item based on the actual checklist item in the trx
	 * value object
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @param aCheckListItemID of long type
	 * @return ICheckList - the checklist with the resetted checklist item
	 * @throws CheckListException on errors
	 */
	public ICheckList resetCheckListItem(ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList,
			long aCheckListItemID) throws CheckListException;

	/**
	 * Get a checklist item by the item reference
	 * @param aCheckListItemRef of long type
	 * @return ICheckListItem - the checklist item with the checklist item
	 *         reference
	 * @throws CheckListException on errors
	 */
	public ICheckListItem getCheckListItem(long aCheckListItemRef) throws CheckListException;

	/**
	 * Update a checklist item based on the primary key
	 * @param aStagingCheckListItemID of long type
	 * @param aCheckListItemRef of long type
	 * @throws CheckListException on errors
	 */
	public void expireCheckListItem(long aStagingCheckListItemID, long aCheckListItemRef) throws CheckListException;

	/**
	 * Get the list of allowable checklist item operations
	 * @return ICheckListItemOperation[] - the list of checklist item operations
	 * @throws CheckListException on errors
	 */
	public ICheckListItemOperation[] getAllowableOperationList() throws CheckListException;

	/**
	 * To return false if there is any pending trx
	 * @param anICheckListOwner of ICheckListOwner type
	 * @return int - true if there already exist and false otherwise
	 * @throws CheckListException on errors
	 */
	public int allowCheckListTrx(ICheckListOwner anICheckListOwner) throws CheckListException;

	/**
	 * To close the collateral related checklist
	 * @param anITrxContext of ITrxContext type
	 * @param aCollateralID of long type
	 * @throws CheckListException on errors
	 */
	public void systemCloseCollateralCheckListTrx(ITrxContext anITrxContext, long aCollateralID)
			throws CheckListException;

	/**
	 * To delete the collateral related checklist
	 * @param anICollateral of ICollateral type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteCollateralCheckListTrx(ICollateral anICollateral) throws CheckListException;

	/**
	 * To delete all the cc checklist related to a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteCCCheckListTrx(ILimitProfile anILimitProfile) throws CheckListException;

	/**
	 * To renew all the cc checklist related to a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CheckListException on errors
	 */
	public void systemRenewCCCheckListTrx(ILimitProfile anILimitProfile) throws CheckListException;

	/**
	 * To delete the collateral related checklist only
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteCollateralCheckListTrxOnly(long aLimitProfileID, long aCollateralID)
			throws CheckListException;

	/**
	 * To delete the collateral related checklist
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteCollateralCheckListTrx(long aLimitProfileID, long aCollateralID) throws CheckListException;

	public void systemDeleteCollateralCheckListTrx(long aLimitProfileID, long aCollateralID,
			long[] aDeletedLimitSecMapList) throws CheckListException;

	/**
	 * To renew all the collateral related checklists
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CheckListException on errors
	 */
	public void systemRenewCollateralCheckListTrx(ILimitProfile anILimitProfile) throws CheckListException;

	/**
	 * To renew the collateral related checklist
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @throws CheckListException on errors
	 */
	public void systemRenewCollateralCheckListTrx(long aLimitProfileID, long aCollateralID) throws CheckListException;

	/**
	 * To delete the non borrower checklist related to a customer
	 * @param aCustomerID of long type
	 * @throws CheckListException on error
	 */
	public void systemDeleteNonBorrowerCheckListTrx(long aCustomerID) throws CheckListException;

	/**
	 * To delete the main borrower checklist related to a limit profile and a
	 * customer
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteMainBorrowerCheckListTrx(long aLimitProfileID, long aCustomerID) throws CheckListException;

	/**
	 * To delete all the cc checklist related to a limit profile and a
	 * coborrower
	 * @param aCustomerID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteCoBorrowerCheckListTrx(long aCustomerID) throws CheckListException;

	/**
	 * To delete all the cc checklist related to a limit profile and a
	 * coborrower
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteCoBorrowerCheckListTrx(long aLimitProfileID, long aCustomerID) throws CheckListException;

	/**
	 * To delete all the cc checklist related to a pledgor
	 * @param aPledgorID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeletePledgorCheckListTrx(long aPledgorID) throws CheckListException;

	/**
	 * To delete all the cc checklist related to a limit profile and a pledgor
	 * @param aLimitProfileID of long type
	 * @param aPledgorID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeletePledgorCheckListTrx(long aLimitProfileID, long aPledgorID) throws CheckListException;

	/**
	 * To close the collateral related checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the checklist trx value that is being closed
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemCloseCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException;

	/**
	 * Get the list of checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @return HashMap - the list of checklist items that requires auditing
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 */
	public HashMap getCheckListAuditList(long aLimitProfileID) throws SearchDAOException, CheckListException;

	/**
	 * Get the list of checklist items that qualify for audit under a limit
	 * profile
	 * @param aLimitProfileID of long String
	 * @return IAuditItem[] - the list of checklist that qualifies for audit
	 * @throws CheckListException on errors
	 * @throws SearchDAOException on DAO errors
	 */
	public IAuditItem[] getAuditItemList(long aLimitProfileID) throws CheckListException, SearchDAOException;

	/**
	 * Get the list of checklist items that qualify for audit under a non
	 * borrower
	 * @param aCustomerID of long String
	 * @return HashMap - the list of checklist that qualifies for audit
	 * @throws CheckListException on errors
	 * @throws SearchDAOException on DAO errors
	 */
	public HashMap getCheckListAuditListForNonBorrower(long aCustomerID) throws CheckListException, SearchDAOException;

	/**
	 * Get the list of checklist items that qualify for audit under a non
	 * borrower
	 * @param aCustomerID of long String
	 * @return IAuditItem[] - the list of checklist that qualifies for audit
	 * @throws CheckListException on errors
	 * @throws SearchDAOException on DAO errors
	 */
	public IAuditItem[] getAuditItemListForNonBorrower(long aCustomerID) throws CheckListException, SearchDAOException;

	/**
	 * Get the list of document categories for this borrower.
	 * @param aLimitProfileID of long type
	 * @return HashMap - the list of document categories
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 */
	public HashMap getDocumentCategories(long aLimitProfileID) throws SearchDAOException, CheckListException;

	/**
	 * Get the list of document categories for this non-borrower.
	 * @param aCustomerID of long type
	 * @return HashMap - the list of document categories
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 */
	public HashMap getDocumentCategoriesForNonBorrower(long aCustomerID, long aLimitProfileID)
			throws SearchDAOException, CheckListException;

	/**
	 * Get the map of documents held given the search criteria.
	 * 
	 * @param criteria - DocumentHeldSearchCriteria
	 * @return HashMap - Map of list of document held fulfilling the criteria
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 */
	public HashMap getDocumentsHeld(DocumentHeldSearchCriteria criteria) throws SearchDAOException, CheckListException;

	/**
	 * Get the securities pledged for this borrower type.
	 * @param aLimitProfileID the limit profile ID
	 * @param pledgorID the pledgor ID
	 * @return HashMap - the securities pledged for this customer
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 */
	public HashMap getSecuritiesPledged(long aLimitProfileID, long pledgorID) throws SearchDAOException,
			CheckListException;

	/**
	 * To check if a cc doc item description already exist or not
	 * @param aDocItemDescription of String type
	 * @return boolean - true if the doc item description already exist and
	 *         false otherwise
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public boolean checkCCItemDescAlreadyExist(ITemplate anITemplate, String aDocItemDescription)
			throws CheckListTemplateException, CheckListException;

	/**
	 * To check if a security doc item description already exist or not
	 * @param aDocItemDescription of String type
	 * @return boolean - true if the doc item description already exist and
	 *         false otherwise
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public boolean checkCollateralItemDescAlreadyExist(ITemplate anITemplate, String aDocItemDescription)
			throws CheckListTemplateException, CheckListException;

	/**
	 * To get a collateral checklist based on the limit profile ID and the
	 * collateral ID
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @return ICheckList - the collateral checklist
	 * @throws CheckListException on errors
	 */
	public ICheckList getCollateralCheckList(long aLimitProfileID, long aCollateralID) throws CheckListException;

	/**
	 * System trigger for waiver generation
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemGenerateCheckListWaiver(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException;

	/**
	 * System trigger for reject waiver generation
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemRejectGenerateCheckListWaiver(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException;

	/**
	 * System trigger for approving waiver generation
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemApproveGenerateCheckListWaiver(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException;

	/**
	 * System trigger for deferral generation
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemGenerateCheckListDeferral(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException;

	/**
	 * System trigger for reject deferral generation
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemRejectGenerateCheckListDeferral(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException;

	/**
	 * System trigger for approving deferral generation
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemApproveGenerateCheckListDeferral(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException;

	/**
	 * System convert non-borrower to borrower
	 * @param aLimitProfileID of long type
	 * @throws CheckListException on errors
	 */
	public void systemConvertNonBorrowerToBorrower(long aLimitProfileID) throws CheckListException;

	/**
	 * System convert a borrower to a non borrower
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @throws CheckListException on errors
	 */
	public void systemConvertBorrowerToNonBorrower(long aLimitProfileID, long aCustomerID) throws CheckListException;

	/**
	 * To lock all the checklist items under a checklist
	 * @param aCheckListID of long type
	 * @throws CheckListException
	 */
	public void lockCheckListItemByCheckList(long aCheckListID) throws CheckListException;

	/**
	 * System create a checklist - from host
	 * @param anICheckListTrxValue
	 * @param anICheckList
	 * @return
	 * @throws CheckListException
	 */
	public ICheckListTrxValue systemCreateCheckList(ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList)
			throws CheckListException;

	/**
	 * System create a document checklist - from host
	 * @param anICheckListTrxValue
	 * @param anICheckList
	 * @return
	 * @throws CheckListException
	 */
	public ICheckListTrxValue systemCreateDocumentCheckList(ICheckListTrxValue anICheckListTrxValue,
			ICheckList anICheckList) throws CheckListException;

	/**
	 * To system update a checklist by checklist ID
	 * @param aCheckListID of long type
	 * @throws CheckListException on errors
	 */
	public void systemUpdateCheckList(long aCheckListID) throws CheckListException;

	/**
	 * System update a checklist
	 * @param anICheckListTrxValue
	 * @param anICheckList
	 * @return
	 * @throws CheckListException
	 */
	public ICheckListTrxValue systemUpdateCheckList(ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList)
			throws CheckListException;

	/**
	 * direct update checklist
	 * @param trxValue
	 * @return
	 * @throws CheckListException
	 */
	public ICheckListTrxValue directUpdateCheckList(ICheckListTrxValue trxValue) throws CheckListException;

	/**
	 * To unlock all the checklist items under a checklist
	 * @param aCheckListID of long type
	 * @throws CheckListException
	 */
	public void unlockCheckListItemByCheckList(long aCheckListID) throws CheckListException;

	/**
	 * To lock all the checklist items under a limitprofile
	 * @param aLimitProfileID of long type
	 * @throws CheckListException
	 */
	public void lockCheckListItemByLimitProfile(long aLimitProfileID) throws CheckListException;

	/**
	 * To unlock all the checklist items under a limitprofile
	 * @param aLimitProfileID of long type
	 * @throws CheckListException
	 */
	public void unlockCheckListItemByLimitProfile(long aLimitProfileID) throws CheckListException;

	/**
	 * Check if all collateral checklists are being deleted
	 * @param aCollateralID of long type
	 * @return boolean - true of all collateral checklists are deleted and false
	 *         otherwise
	 * @throws CheckListException on errors
	 */
	public boolean areAllCheckListsDeleted(long aCollateralID) throws CheckListException;

	/**
	 * Overloaded method to determine if the security is in the same country as
	 * the user.
	 * @param anIContext the context object containing user information
	 * @param anILimitProfile the limit profile
	 * @param collateralID the collateral identifier
	 * @param collateralLocation the collateral location
	 * @return boolean - true if security is in the same country as the user,
	 *         false otherwise
	 * @throws CheckListException on errors
	 */
	public boolean isInSameCountry(IContext anIContext, ILimitProfile anILimitProfile, long collateralID,
			String collateralLocation) throws CheckListException;

	/**
	 * Overloaded method determines if the c/c checklist is in the same country
	 * as the user.
	 * @param anIContext the context object containing user information
	 * @param anILimitProfile the limit profile
	 * @param customerID the customer identifier
	 * @param domicileCountry domicile country of the checklist
	 * @param custCategory the customer category
	 * @return boolean - true if c/c checklist is in the same country as the
	 *         user, false otherwise
	 * @throws CheckListException on errors
	 */
	public boolean isInSameCountry(IContext anIContext, ILimitProfile anILimitProfile, long customerID,
			String domicileCountry, String custCategory) throws CheckListException;

	/**
	 * FOR CR CMS-662 Get all CHECKLIST related to a SECURITY based on
	 * CMS_COLLATERAL_ID.
	 * @param aCollateralID of long type
	 * @return int - the number of checklist related to security.
	 * @throws SearchDAOException
	 * @throws CheckListException on errors
	 */

	public int getSecurityChkListCount(long aCollateralID) throws SearchDAOException, CheckListException;

	/**
	 * FOR CR CMS-310 Get the list of checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param asOfDate of String type
	 * @param aCustCat of String type
	 * @return HashMap - the list of checklist items that requires auditing
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 */
	public HashMap getCheckListAudit(long aLimitProfileID, String asOfDate, String aCustCat) throws SearchDAOException,
			CheckListException;

	/**
	 * FOR CR CMS-310 Get the list of checklist items that qualify for audit
	 * under a non borrower
	 * @param aCustomerID of long String
	 * @param asOfDate of String type
	 * @param aCustCat of String type
	 * @return HashMap - the list of checklist that qualifies for audit
	 * @throws CheckListException on errors
	 * @throws SearchDAOException on DAO errors
	 */
	public HashMap getCheckListAuditForNonBorrower(long aCustomerID, String asOfDate, String aCustCat)
			throws CheckListException, SearchDAOException;

	/**
	 * Officer forwards .
	 * 
	 * @param ctx of type ITrxContext
	 * @param trxVal of type ICheckListTrxValue
	 * @return ICheckListTrxValue
	 * @throws CheckListException on errors encountered
	 */
	public ICheckListTrxValue officeOperation(ITrxContext ctx, ICheckListTrxValue trxVal) throws CheckListException;

	/**
	 * To system delete pledgor checklist if it is no longer valid due to this
	 * co-borrower
	 * @param aCoBorrowerLegalRef of long type
	 * @param aLimitProfileRef of long type
	 * @throws CheckListException on errors
	 */
	public void systemHandleCoBorrowerCheckList(long aCoBorrowerLegalRef, long aLimitProfileRef)
			throws CheckListException;

	/**
	 * To system delete pledgor checklists if it is no longer valid due to the
	 * legal reference of the pledgor
	 * @param aPledgorID of long type
	 * @param aPledgorLegalRef of long type
	 * @throws CheckListException on errors
	 */
	public void systemHandlePledgorCheckList(long aPledgorID, long aPledgorLegalRef) throws CheckListException;

	/**
	 * Get a list of checklist created for the given limit profile.
	 * 
	 * @param limitProfile of type ILimitProfile
	 * @return a list of checklist
	 * @throws CheckListException on any errors encountered
	 */
	public ICheckList[] getAllCheckList(ILimitProfile limitProfile) throws CheckListException;

	/**
	 * Generate checklist item sequence number.
	 * 
	 * @return long
	 * @throws CheckListException on any error encountered
	 */
	public long generateCheckListItemSeqNo() throws CheckListException;

	// Start for cr-17
	// public List getCheckListDetailsByCheckListId(String[] aCheckListId,
	// String categoryType, String subCategoryType) throws
	// CheckListException,SearchDAOException;
	public List getCheckListDetailsByCheckListId(String[] aCheckListId, String categoryType) throws CheckListException,
			SearchDAOException;

	public List getCheckListDetailsByCheckListId(String[] aCheckListId) throws CheckListException, SearchDAOException;

	public List getAllShareDocuments(long id, boolean isNonBorrower) throws CheckListException, SearchDAOException;
	
	public List getAllDeferCreditApprover() throws CheckListException, SearchDAOException;
	
	public List getAllWaiveCreditApprover() throws CheckListException, SearchDAOException;
	
	public List getAllBothCreditApprover() throws CheckListException, SearchDAOException;

	// public OBShareDoc getLeName(long aProfileId) throws
	// CheckListException,SearchDAOException;
	// public OBShareDoc getSecuritySubDetails(long aProfileId,long
	// collateralId) throws CheckListException,SearchDAOException;

	public HashMap getCollateralLimitMap(ILimitProfile anILimitProfile) throws CheckListException;

	public List getCustomerListByCollateralID(long collateralId) throws CheckListException;

	// Method added by Pratheepa on 29Sep2006 while fixing bug CMS-3479
	public ArrayList getDocumentIdsForCheckList(ArrayList documentIds) throws CheckListException;

	public HashMap getCollateralIdForSharedDocs(long documentNo, Long[] collateralIds) throws CheckListException;

	public HashMap getSecuritySubTypes(String secType) throws CheckListException;

	public HashMap getStateDistrictMukim(HashMap input) throws CheckListException;

	public HashMap getDocumentationStatus(long lmtProfileID) throws CheckListException;

	public boolean getChecklistCompletedStatus(long collID) throws CheckListException;

	public int getAssetResidualLife(String secSubTypeId) throws CheckListException;

	public HashMap[] getDetailsForPreDisbursementReminderLetter(long limitProfileID) throws SearchDAOException,
			CheckListException;

	public HashMap[] getDetailsForPostDisbursementReminderLetter(long limitProfileID) throws SearchDAOException,
			CheckListException;

    public HashMap getCheckListItemListbyCategory(long aLimitProfileID, String aCheckListCategory) throws SearchDAOException, CheckListException;
    /**
     * Added by Anil
	 * Get a checklist item by the item id
	 * @param aCheckListItemRef of long type
	 * @return ICheckListItem - the checklist item with the checklist item id
	 * @throws CheckListException on errors
	 */
	public ICheckListItem getCheckListItemById(long checkListItemId) throws CheckListException;
	
	public ICheckListItem[] getNextCheckListReceiptsOld(ICheckListItem receipt, String action) throws CheckListException ;
}
