/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/proxy/SBCheckListProxyManager.java,v 1.106 2006/11/20 03:04:04 czhou Exp $
 */
package com.integrosys.cms.app.checklist.proxy;

//java
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJBObject;

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
 * Session bean remote interface for the services provided by the checklist
 * proxy manager
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.106 $
 * @since $Date: 2006/11/20 03:04:04 $ Tag: $Name: $
 */
public interface SBCheckListProxyManager extends EJBObject {
	/**
	 * Get transaction sub-type for a checklist transaction.
	 * 
	 * @param aTrxID - primitive long denoting the checklist transaction
	 * @return String denoting the transaction sub-type
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public String[] getTrxSubTypeByTrxID(long aTrxID) throws CheckListException, RemoteException;

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
	public IItem[] getItemList(ICheckList anICheckList) throws CheckListTemplateException, CheckListException,
			SearchDAOException, RemoteException;

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
	public IItem[] getItemList(ICheckList anICheckList, String collateralID) throws CheckListTemplateException, CheckListException,
			SearchDAOException, RemoteException;
	/**
	 * Get the list of c/c checklist summary info.
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListTemplateException, CheckListException, RemoteException;
	
	
	public FacilityCheckListSummary[] getFacilityCheckListSummaryList(IContext anIContext, long aLimitProfileID)
	throws CheckListTemplateException, CheckListException, RemoteException;

	/**
	 * Get the list of c/c checklist summary info. This includes the deleted
	 * checklist as well.
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return HashMap - contains the list of cc summary for those checklist
	 *         that are deleted as well as not deleted
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getAllCCCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListTemplateException, CheckListException, RemoteException;

	/**
	 * Get the list of c/c checklist summary info for non borrower
	 * @param anIContext of IContext type
	 * @param aCustomerID of long type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryListForNonBorrower(IContext anIContext, long aLimitProfileID,
			long aCustomerID, boolean isNBCheckListOnly) throws CheckListTemplateException, CheckListException,
			RemoteException;

	/**
	 * Get the list of c/c checklist summary info for non borrower including
	 * those that are deleted
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return HashMap - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getAllCCCheckListSummaryListForNonBorrower(IContext anIContext, long aLimitProfileID,
			long aCustomerID) throws CheckListTemplateException, CheckListException, RemoteException;

	public HashMap getAllCCCheckListSummaryListForCustodian(IContext anIContext, long aLimitProfileID)
			throws CheckListTemplateException, CheckListException, RemoteException;

	/**
	 * Get the list of c/c checklist summary info for custodian.
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryListForCustodian(IContext anIContext, long aLimitProfileID)
			throws CheckListTemplateException, CheckListException, RemoteException;

	/**
	 * Get the list of c/c checklist summary info for cc collaboration task
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryForCollaboration(IContext anIContext, ILimitProfile anILimitProfile)
			throws CheckListTemplateException, CheckListException, RemoteException;

	/**
	 * Get the list of c/c checklist summary info for ccc generation
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryForCCC(IContext anIContext, ILimitProfile anILimitProfile)
			throws CheckListTemplateException, CheckListException, RemoteException;

	/**
	 * Get the list of collateral checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public CollateralCheckListSummary[] getCollateralCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListException, RemoteException;
	
	/**
	 * Get the list of cam checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public CAMCheckListSummary[] getCAMCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListException, RemoteException;

	/**
	 * Get the list of collateral checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getAllCollateralCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListException, RemoteException;
	
	
	/**
	 * Get the list of cam checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public PariPassuCheckListSummary[] getPariPassuCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListException, RemoteException;

	/**
	 * Get the list of collateral checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getAllCAMCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListException, RemoteException;
	
	
	public HashMap getAllFacilityCheckListSummaryList(IContext anIContext, long aLimitProfileID)
	throws CheckListException, RemoteException;
	/**
	 * Get the list of collateral checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @param aCustodianInd of boolean type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public CollateralCheckListSummary[] getFilteredCollateralCheckListSummaryList(IContext anIContext,
			long aLimitProfileID, boolean aCustodianInd) throws CheckListException, RemoteException;

	/**
	 * Get the list of collateral checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 * @throws RemoteException on errors
	 */
	public HashMap getAllFilteredCollateralCheckListSummaryList(IContext anIContext, long aLimitProfileID,
			boolean aCustodianInd) throws CheckListException, RemoteException;

	/**
	 * Get Hashtable with collateral ID and the status
	 * @param aLimitProfileID of long type
	 * @return HashMap - this the security ID as the key and the status as the
	 *         value
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getCollateralCheckListStatus(long aLimitProfileID) throws SearchDAOException, CheckListException,
			RemoteException;

	public abstract HashMap getCCCheckListStatus(long aLimitProfileID, boolean isFullListInd)
			throws SearchDAOException, CheckListException, RemoteException;

	/**
	 * Get the checklist based on the checklist ID
	 * @param aCheckListID of long type
	 * @return ICheckList - the checklist containing the checklist with the
	 *         specified ID
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckList getCheckListByID(long aCheckListID) throws CheckListException, RemoteException;
	
	public CheckListSearchResult getCheckListByCollateralID(long aCheckListID) throws CheckListException, RemoteException;

	public CheckListSearchResult getCAMCheckListByCategoryAndProfileID(String category,long collateralID) throws CheckListException, RemoteException;
	
	public CheckListSearchResult[] getCheckListByCategory(String category) throws CheckListException, RemoteException;
	
	
	public CheckListSearchResult getPariPassuCheckListByCategoryAndProfileID(String category,long paripassuID) throws CheckListException, RemoteException;
	/**
	 * Get the collateral checklist trx value based on the checklist ID
	 * @param aCheckListID of long type
	 * @return ICheckListTrxValue - the checklist trx value containing the
	 *         checklist with the specified ID
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue getCheckList(long aCheckListID) throws CheckListException, RemoteException;

	/**
	 * Get a checklist by transaction ID
	 * @param aTrxID of String type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue getCheckListByTrxID(String aTrxID) throws CheckListException, RemoteException;

	/**
	 * Search checklist based on the criteria specified. Currently only used to
	 * search for checklist pending multi-level approval.
	 * 
	 * @param ctx transaction context
	 * @param criteria of type CheckListSearchCriteria
	 * @return search result
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors encountered
	 * @throws RemoteException on remote errors
	 */
	public SearchResult searchCheckList(ITrxContext ctx, CheckListSearchCriteria criteria)
			throws CheckListTemplateException, CheckListException, RemoteException;

	/**
	 * Formulate the default checklist from the template selected based on the
	 * law, legal constitution and country
	 * @param anICheckListOwner - ICheckListOwner
	 * @param aLegalConstitution - String
	 * @param anIBookingLocation - IBookingLocation
	 * @return ICheckList - the default checklist
	 * @throws com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException
	 *         if there is no template setup for the checklist
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckList getDefaultCCCheckList(ICheckListOwner anICheckListOwner, String aLegalConstitution,
			IBookingLocation anIBookingLocation, String law) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException, RemoteException;

	/**
	 * For use by SI to retrieve the default cc checklist
	 * @param customerType - customer type (e.g. MAIN_BORROWER, PLEDGOR etc)
	 * @param legalConstitution - legal constituition
	 * @param country - country
	 * @param law - law
	 * @return
	 * @throws TemplateNotSetupException if there is no template setup for the
	 *         checklist
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
			String applicationType, String goodsStatus, String pbrInd) throws TemplateNotSetupException,
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
	public ICheckList getDefaultCAMCheckList(ICheckListOwner anICheckListOwner,String country, String collateralType, String collateralSubType,
			String applicationType, String goodsStatus, String pbrInd) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException, RemoteException;
	
	
	public ICheckList getDefaultFacilityCheckList(ICheckListOwner anICheckListOwner,String country, String collateralType, String collateralSubType,
			String applicationType, String goodsStatus, String pbrInd) throws TemplateNotSetupException,
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
	public ICheckList getDefaultPariPassuCheckList(
			ICheckListOwner anICheckListOwner, String pariPassuType,
			String pariPassuSubType, String country, String orgCode) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException, RemoteException;

	/**
	 * Maker creation of a checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue makerCreateCheckList(ITrxContext anITrxContext, ICheckList anICheckList)
			throws CheckListException, RemoteException;

	/**
	 * @see ICheckListProxyManager#makerCreateCheckListWithoutApproval(ITrxContext,
	 *      ICheckList)
	 */
	public ICheckListTrxValue makerCreateCheckListWithoutApproval(ITrxContext anITrxContext, ICheckList anICheckList)
			throws CheckListException, RemoteException;

	/**
	 * @see ICheckListProxyManager#makerUpdateCheckListWithoutApproval(ITrxContext,
	 *      ICheckListTrxValue, ICheckList)
	 */
	public ICheckListTrxValue makerUpdateCheckListWithoutApproval(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList) throws CheckListException,
			RemoteException;

	/**
	 * Copy Checklist due to BCA Renewal
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue copyCheckList(ITrxContext anITrxContext, ICheckList anICheckList)
			throws CheckListException, RemoteException;

	/**
	 * Checker approves a checklist trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue checkerApproveCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException, RemoteException;

	/**
	 * Maker closes a checklist trx that has been rejected by the checker
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue makerCloseCheckListTrx(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException, RemoteException;

	/**
	 * Maker edits a rejected checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue makerEditRejectedCheckListTrx(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList) throws CheckListException,
			RemoteException;

	/**
	 * Maker updates a checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue makerUpdateCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue,
			ICheckList anICheckList) throws CheckListException, RemoteException;

	/**
	 * Maker deletes a checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue makerDeleteCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException, RemoteException;

	/**
	 * Maker updates a checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue makerUpdateCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList) throws CheckListException,
			RemoteException;

	/**
	 * Checker approves a checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue checkerApproveCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException, RemoteException;

	/**
	 * Checker updates a checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue checkerUpdateCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException, RemoteException;

	/**
	 * Updates the status of the shared checklists. Should be called after
	 * Checker Approve Update Checklist / Receipt (for both cc and security)
	 * @param trxValue of ICheckListTrxValue type
	 * @throws CheckListException on errors
	 */
	public void updateSharedChecklistStatus(ICheckListTrxValue trxValue) throws CheckListException, RemoteException;

	/**
	 * Manager approves a checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue managerApproveCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException, RemoteException;

	/**
	 * Checker rejects a checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue checkerRejectCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException, RemoteException;

	/**
	 * Manager rejects a checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue managerRejectCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException, RemoteException;

	/**
	 * Maker closes a checklist receipt trx that has been rejected by the
	 * checker
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue makerCloseCheckListReceiptTrx(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException, RemoteException;

	/**
	 * Maker edits a rejected checklist receipt
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue makerEditRejectedCheckListReceiptTrx(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList) throws CheckListException,
			RemoteException;

	/**
	 * Checker rejects a checklist trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue checkerRejectCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException, RemoteException;

	/**
	 * Maker save the checkList as draft
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue makerSaveCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue,
			ICheckList anICheckList) throws CheckListException, RemoteException;

	/**
	 * Maker cancel a save the checkList as draft
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue makerCancelSavedCheckList(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException, RemoteException;

	/**
	 * Retrieve next checklist receipts based on user action. This may result in
	 * the receipt state being modified and/or a new receipt being created.
	 * 
	 * @param receipt checklist receipt on which the action will be performed
	 * @param action user action on the checklist receipt
	 * @throws CheckListException error in retrieving next state of the
	 *         checklist receipt
	 * @throws RemoteException on any unexpected error during remote method call
	 */
	public ICheckListItem[] getNextCheckListReceipts(ICheckListItem receipt, String action) throws CheckListException,
			RemoteException;
	
	public ICheckListItem[] getNextCheckListReceiptsOld(ICheckListItem receipt, String action) throws CheckListException,
	RemoteException;

	/**
	 * To get the next state of the checklist item
	 * @param aCurrentState of String type
	 * @param aCustodianState of String type
	 * @param anAction of String type
	 * @return String - the next checklist item state
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getNextCheckListItemState(String aCurrentState, String aCustodianState, String anAction)
			throws CheckListException, RemoteException;

	/**
	 * Reset a checklist item based on the actual checklist item in the trx
	 * value object
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @param aCheckListItemID of long type
	 * @return ICheckList - the checklist with the resetted checklist item
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckList resetCheckListItem(ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList,
			long aCheckListItemID) throws CheckListException, RemoteException;

	/**
	 * Get a checklist item by the item reference
	 * @param aCheckListItemRef of long type
	 * @return ICheckListItem - the checklist item with the checklist item
	 *         reference
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListItem getCheckListItem(long aCheckListItemRef) throws CheckListException, RemoteException;

	/**
	 * Update a checklist item based on the primary key
	 * @param aStagingCheckListItemID of long type
	 * @param aCheckListItemRef of long type
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public void expireCheckListItem(long aStagingCheckListItemID, long aCheckListItemRef) throws CheckListException,
			RemoteException;

	/**
	 * Get the list of allowable checklist item operations
	 * @return ICheckListItemOperation[] - the list of checklist item operations
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListItemOperation[] getAllowableOperationList() throws CheckListException, RemoteException;

	/**
	 * To return false if there is any pending trx
	 * @param anICheckListOwner of ICheckListOwner type
	 * @return int - true if there already exist and false otherwise
	 * @throws CheckListException on errors
	 * @throws RemoteException on errors
	 */
	public int allowCheckListTrx(ICheckListOwner anICheckListOwner) throws CheckListException, RemoteException;

	/**
	 * To close the collateral related checklist
	 * @param anITrxContext of ITrxContext type
	 * @param aCollateralID of long type
	 * @throws CheckListException on errors
	 * @throws RemoteException
	 */
	public void systemCloseCollateralCheckListTrx(ITrxContext anITrxContext, long aCollateralID)
			throws CheckListException, RemoteException;

	/**
	 * To delete the collateral related checklist only
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public void systemDeleteCollateralCheckListTrxOnly(long aLimitProfileID, long aCollateralID)
			throws CheckListException, RemoteException;

	/**
	 * To delete the collateral related checklist
	 * @param anICollateral of ICollateral type
	 * @throws CheckListException on errors
	 * @throws RemoteException
	 */
	public void systemDeleteCollateralCheckListTrx(ICollateral anICollateral) throws CheckListException,
			RemoteException;

	/**
	 * To delete all the cc checklist related to a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CheckListException on errors
	 * @throws RemoteException
	 */
	public void systemDeleteCCCheckListTrx(ILimitProfile anILimitProfile) throws CheckListException, RemoteException;

	/**
	 * To renew all the cc checklist related to a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CheckListException on errors
	 * @throws RemoteException
	 */
	public void systemRenewCCCheckListTrx(ILimitProfile anILimitProfile) throws CheckListException, RemoteException;

	public void systemDeleteCollateralCheckListTrx(long aLimitProfileID, long aCollateralID,
			long[] aDeletedLimitSecMapList) throws CheckListException, RemoteException;

	/**
	 * To delete the collateral related checklist
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @throws CheckListException on errors
	 * @throws RemoteException
	 */
	public void systemDeleteCollateralCheckListTrx(long aLimitProfileID, long aCollateralID) throws CheckListException,
			RemoteException;

	/**
	 * To renew all the collateral related checklists
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CheckListException on errors
	 * @throws RemoteException
	 */
	public void systemRenewCollateralCheckListTrx(ILimitProfile anILimitProfile) throws CheckListException,
			RemoteException;

	/**
	 * To renew the collateral related checklist
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @throws CheckListException on errors
	 * @throws RemoteException
	 */
	public void systemRenewCollateralCheckListTrx(long aLimitProfileID, long aCollateralID) throws CheckListException,
			RemoteException;

	/**
	 * To delete the non borrower checklist related to a customer
	 * @param aCustomerID of long type
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public void systemDeleteNonBorrowerCheckListTrx(long aCustomerID) throws CheckListException, RemoteException;

	/**
	 * To delete the main borrower checklist related to a limit profile and a
	 * customer
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteMainBorrowerCheckListTrx(long aLimitProfileID, long aCustomerID) throws CheckListException,
			RemoteException;

	/**
	 * To delete all the cc checklist related to a limit profile and a
	 * coborrower
	 * @param aCustomerID of long type
	 * @throws CheckListException on errors
	 * @throws RemoteException
	 */
	public void systemDeleteCoBorrowerCheckListTrx(long aCustomerID) throws CheckListException, RemoteException;

	/**
	 * To delete all the cc checklist related to a limit profile and a
	 * coborrower
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @throws CheckListException on errors
	 * @throws RemoteException
	 */
	public void systemDeleteCoBorrowerCheckListTrx(long aLimitProfileID, long aCustomerID) throws CheckListException,
			RemoteException;

	/**
	 * To delete all the cc checklist related to a pledgor
	 * @param aPledgorID of long type
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public void systemDeletePledgorCheckListTrx(long aPledgorID) throws CheckListException, RemoteException;

	/**
	 * To delete all the cc checklist related to a limit profile and a pledgor
	 * @param aLimitProfileID of long type
	 * @param aPledgorID of long type
	 * @throws CheckListException on errors
	 * @throws RemoteException
	 */
	public void systemDeletePledgorCheckListTrx(long aLimitProfileID, long aPledgorID) throws CheckListException,
			RemoteException;

	/**
	 * To close the collateral related checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 * @throws RemoteException
	 */
	public ICheckListTrxValue systemCloseCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException, RemoteException;

	/**
	 * Get the list of checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @return HashMap - the list of checklist items that requires auditing
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getCheckListAuditList(long aLimitProfileID) throws SearchDAOException, CheckListException,
			RemoteException;

	/**
	 * Get the list of checklist items that qualify for audit under a limit
	 * profile
	 * @param aLimitProfileID of long String
	 * @return IAuditItem[] - the list of checklist that qualifies for audit
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public IAuditItem[] getAuditItemList(long aLimitProfileID) throws CheckListException, RemoteException;

	/**
	 * Get the list of checklist items that qualify for audit under a non
	 * borrower
	 * @param aCustomerID of long String
	 * @return HashMap - the list of checklist that qualifies for audit
	 * @throws CheckListException on errors
	 * @throws SearchDAOException on DAO errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getCheckListAuditListForNonBorrower(long aCustomerID) throws CheckListException, SearchDAOException,
			RemoteException;

	/**
	 * Get the list of checklist items that qualify for audit under a non
	 * borrower
	 * @param aCustomerID of long String
	 * @return IAuditItem[] - the list of checklist that qualifies for audit
	 * @throws CheckListException on errors
	 * @throws RemoteException on errors
	 */
	public IAuditItem[] getAuditItemListForNonBorrower(long aCustomerID) throws CheckListException, RemoteException;

	/**
	 * Get the document categories for a borrower.
	 * @param aLimitProfileID of long String
	 * @return HashMap - a map of documents
	 * @throws CheckListException on errors
	 * @throws SearchDAOException on DAO errors
	 * @throws RemoteException on errors
	 */
	public HashMap getDocumentCategories(long aLimitProfileID) throws SearchDAOException, CheckListException,
			RemoteException;

	/**
	 * Get the document categories for a non-borrower.
	 * @param aCustomerID of long String
	 * @return HashMap - a map of documents
	 * @throws CheckListException on errors
	 * @throws SearchDAOException on DAO errors
	 * @throws RemoteException on errors
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
	 * @throws RemoteException on errors
	 */
	public HashMap getDocumentsHeld(DocumentHeldSearchCriteria criteria) throws SearchDAOException, CheckListException,
			RemoteException;

	/**
	 * Get the list of securities pledged for this non-borrower.
	 * @param aLimitProfileID the customer ID
	 * @param pledgorID the pledgor ID
	 * @return HashMap - the securities pledged for this customer
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on errors
	 */
	public HashMap getSecuritiesPledged(long aLimitProfileID, long pledgorID) throws SearchDAOException,
			CheckListException, RemoteException;

	/**
	 * To check if a cc doc item description already exist or not
	 * @param aDocItemDescription of String type
	 * @return boolean - true if the doc item description already exist and
	 *         false otherwise
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public boolean checkCCItemDescAlreadyExist(ITemplate anITemplate, String aDocItemDescription)
			throws CheckListTemplateException, CheckListException, RemoteException;

	/**
	 * To check if a security doc item description already exist or not
	 * @param aDocItemDescription of String type
	 * @return boolean - true if the doc item description already exist and
	 *         false otherwise
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public boolean checkCollateralItemDescAlreadyExist(ITemplate anITemplate, String aDocItemDescription)
			throws CheckListTemplateException, CheckListException, RemoteException;

	/**
	 * To get a collateral checklist based on the limit profile ID and the
	 * collateral ID
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @return ICheckList - the collateral checklist
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckList getCollateralCheckList(long aLimitProfileID, long aCollateralID) throws CheckListException,
			RemoteException;

	/**
	 * System trigger for waiver generation
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue systemGenerateCheckListWaiver(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException, RemoteException;

	/**
	 * System trigger for reject waiver generation
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue systemRejectGenerateCheckListWaiver(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException, RemoteException;

	/**
	 * System trigger for approving waiver generation
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue systemApproveGenerateCheckListWaiver(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException, RemoteException;

	/**
	 * System trigger for deferral generation
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue systemGenerateCheckListDeferral(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException, RemoteException;

	/**
	 * System trigger for reject deferral generation
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue systemRejectGenerateCheckListDeferral(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException, RemoteException;

	/**
	 * System trigger for approving deferral generation
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListTrxValue systemApproveGenerateCheckListDeferral(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException, RemoteException;

	/**
	 * System convert non-borrower to borrower
	 * @param aLimitProfileID of long type
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public void systemConvertNonBorrowerToBorrower(long aLimitProfileID) throws CheckListException, RemoteException;

	/**
	 * System convert a borrower to a non borrower
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public void systemConvertBorrowerToNonBorrower(long aLimitProfileID, long aCustomerID) throws CheckListException,
			RemoteException;

	/**
	 * To lock all the checklist items under a checklist
	 * @param aCheckListID of long type
	 * @throws CheckListException
	 * @throws RemoteException
	 */
	public void lockCheckListItemByCheckList(long aCheckListID) throws CheckListException, RemoteException;

	/**
	 * System create a checklist
	 * @param anICheckListTrxValue
	 * @param anICheckList
	 * @return
	 * @throws CheckListException
	 * @throws RemoteException
	 */
	public ICheckListTrxValue systemCreateCheckList(ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList)
			throws CheckListException, RemoteException;

	/**
	 * System create a document checklist
	 * @param anICheckListTrxValue
	 * @param anICheckList
	 * @return
	 * @throws CheckListException
	 * @throws RemoteException
	 */
	public ICheckListTrxValue systemCreateDocumentCheckList(ICheckListTrxValue anICheckListTrxValue,
			ICheckList anICheckList) throws CheckListException, RemoteException;

	/**
	 * To system update a checklist by checklist ID
	 * @param aCheckListID of long type
	 * @throws CheckListException on errors
	 */
	public void systemUpdateCheckList(long aCheckListID) throws CheckListException, RemoteException;

	/**
	 * System update a checklist
	 * @param anICheckListTrxValue
	 * @param anICheckList
	 * @return
	 * @throws CheckListException
	 * @throws RemoteException
	 */
	public ICheckListTrxValue systemUpdateCheckList(ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList)
			throws CheckListException, RemoteException;

	/**
	 * direct update checklist
	 * @param trxValue
	 * @return
	 * @throws RemoteException
	 * @throws CheckListException
	 */
	public ICheckListTrxValue directUpdateCheckList(ICheckListTrxValue trxValue) throws RemoteException,
			CheckListException;

	/**
	 * To unlock all the checklist items under a checklist
	 * @param aCheckListID of long type
	 * @throws CheckListException
	 * @throws RemoteException
	 */
	public void unlockCheckListItemByCheckList(long aCheckListID) throws CheckListException, RemoteException;

	/**
	 * To lock all the checklist items under a limitprofile
	 * @param aLimitProfileID of long type
	 * @throws CheckListException
	 * @throws RemoteException
	 */
	public void lockCheckListItemByLimitProfile(long aLimitProfileID) throws CheckListException, RemoteException;

	/**
	 * To unlock all the checklist items under a limitprofile
	 * @param aLimitProfileID of long type
	 * @throws CheckListException
	 * @throws RemoteException
	 */
	public void unlockCheckListItemByLimitProfile(long aLimitProfileID) throws CheckListException, RemoteException;

	/**
	 * Check if all collateral checklists are being deleted
	 * @param aCollateralID of long type
	 * @return boolean - true of all collateral checklists are deleted and false
	 *         otherwise
	 * @throws CheckListException on errors
	 * @throws RemoteException on errors
	 */
	public boolean areAllCheckListsDeleted(long aCollateralID) throws CheckListException, RemoteException;

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
			String collateralLocation) throws CheckListException, RemoteException;

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
			String domicileCountry, String custCategory) throws CheckListException, RemoteException;

	/**
	 * FOR CR CMS-662 Get all CHECKLIST related to a SECURITY based on
	 * CMS_COLLATERAL_ID.
	 * @param aCollateralID of long type
	 * @return int - the number of checklist related to security.
	 * @throws SearchDAOException
	 * @throws CheckListException
	 * @throws RemoteException on errors
	 */
	public int getSecurityChkListCount(long aCollateralID) throws SearchDAOException, CheckListException,
			RemoteException;

	/**
	 * FOR CR CMS-310 Get the list of checklist items that qualify for audit
	 * under a non borrower
	 * @param aCustomerID of long String
	 * @param asOfDate of String type
	 * @param aCustCat of String type
	 * @return HashMap - the list of checklist that qualifies for audit
	 * @throws CheckListException on errors
	 * @throws SearchDAOException on DAO errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getCheckListAuditForNonBorrower(long aCustomerID, String asOfDate, String aCustCat)
			throws CheckListException, SearchDAOException, RemoteException;

	/**
	 * FOR CR CMS-310 Get the list of checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param asOfDate of String type
	 * @param aCustCat of String type
	 * @return HashMap - the list of checklist items that requires auditing
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public HashMap getCheckListAudit(long aLimitProfileID, String asOfDate, String aCustCat) throws SearchDAOException,
			CheckListException, RemoteException;

	/**
	 * Officer forwards .
	 * 
	 * @param ctx of type ITrxContext
	 * @param trxVal of type ICheckListTrxValue
	 * @return ICheckListTrxValue
	 * @throws CheckListException on errors encountered
	 */
	public ICheckListTrxValue officeOperation(ITrxContext ctx, ICheckListTrxValue trxVal) throws CheckListException,
			RemoteException;

	/**
	 * To system delete pledgor checklist if it is no longer valid due to this
	 * co-borrower
	 * @param aCoBorrowerLegalRef of long type
	 * @param aLimitProfileRef of long type
	 * @throws CheckListException
	 * @throws RemoteException on errors
	 */
	public void systemHandleCoBorrowerCheckList(long aCoBorrowerLegalRef, long aLimitProfileRef)
			throws CheckListException, RemoteException;

	/**
	 * To system delete pledgor checklists if it is no longer valid due to the
	 * legal reference of the pledgor
	 * @param aPledgorID of long type
	 * @param aPledgorLegalRef of long type
	 * @throws CheckListException
	 * @throws RemoteException on errors
	 */
	public void systemHandlePledgorCheckList(long aPledgorID, long aPledgorLegalRef) throws CheckListException,
			RemoteException;

	/**
	 * Get a list of checklist created for the given limit profile.
	 * 
	 * @param limitProfile of type ILimitProfile
	 * @return a list of checklist
	 * @throws CheckListException on any errors encountered
	 */
	public ICheckList[] getAllCheckList(ILimitProfile limitProfile) throws CheckListException, RemoteException;

	/**
	 * Generate checklist item sequence number.
	 * 
	 * @return long
	 * @throws CheckListException on any error encountered
	 * @throws RemoteException on any unexpected error during remote method call
	 */
	public long generateCheckListItemSeqNo() throws CheckListException, RemoteException;

	// Start for cr-17
	/**
	 * 
	 * @param aCheckListId
	 * @param categoryType
	 * @return List of OBShareDoc Object
	 * @throws CheckListException
	 * @throws SearchDAOException
	 * @throws RemoteException
	 */
	// public List getCheckListDetailsByCheckListId(String[] aCheckListId,
	// String categoryType, String subCategoryType) throws CheckListException,
	// SearchDAOException, RemoteException;
	public List getCheckListDetailsByCheckListId(String[] aCheckListId, String categoryType) throws CheckListException,
			SearchDAOException, RemoteException;

	public List getCheckListDetailsByCheckListId(String[] aCheckListId) throws CheckListException, SearchDAOException,
			RemoteException;

	public List getAllShareDocuments(long id, boolean isNonBorrower) throws CheckListException, SearchDAOException,
			RemoteException;
	
	public List getAllDeferCreditApprover() throws CheckListException, SearchDAOException ,RemoteException;
	
	public List getAllWaiveCreditApprover() throws CheckListException, SearchDAOException,RemoteException;
	
	public List getAllBothCreditApprover() throws CheckListException, SearchDAOException, RemoteException;

	public HashMap getCollateralLimitMap(ILimitProfile anILimitProfile) throws CheckListException, RemoteException;

	public List getCustomerListByCollateralID(long collateralId) throws CheckListException, RemoteException;

	// Method added by Pratheepa on 29Sep2006 while fixing bug CMS-3479
	public ArrayList getDocumentIdsForCheckList(ArrayList documentIds) throws CheckListException, RemoteException;

	public HashMap getCollateralIdForSharedDocs(long documentNo, Long[] collateralIds) throws CheckListException,
			RemoteException;

	public HashMap getSecuritySubTypes(String secType) throws CheckListException, RemoteException;

	public HashMap getStateDistrictMukim(HashMap input) throws CheckListException, RemoteException;

	public HashMap getDocumentationStatus(long lmtProfileID) throws CheckListException, RemoteException;

	public boolean getChecklistCompletedStatus(long collID) throws CheckListException, RemoteException;

	public int getAssetResidualLife(String secSubTypeId) throws CheckListException, RemoteException;

	public HashMap[] getDetailsForPreDisbursementReminderLetter(long limitProfileID) throws SearchDAOException,
			CheckListException, RemoteException;

	public HashMap[] getDetailsForPostDisbursementReminderLetter(long limitProfileID) throws SearchDAOException,
			CheckListException, RemoteException;

    public HashMap getCheckListItemListbyCategory(long aLimitProfileID, String aCheckListCategory) throws SearchDAOException, CheckListException, RemoteException;

    /**
     * Added By Anil
	 * Get a checklist item by the item id
	 * @param checkListItemId of long type
	 * @return ICheckListItem - the checklist item with the checklist item id
	 * @throws CheckListException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICheckListItem getCheckListItemById(long checkListItemId) throws CheckListException, RemoteException;

	/*public ICheckListTrxValue checkerApproveCheckListReceiptScheduler(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue, String flagScheduler) throws CheckListException, RemoteException;*/

}
