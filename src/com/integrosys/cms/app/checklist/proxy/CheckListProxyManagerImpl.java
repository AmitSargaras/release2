/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/proxy/CheckListProxyManagerImpl.java,v 1.105 2006/11/20 03:04:04 czhou Exp $
 */
package com.integrosys.cms.app.checklist.proxy;

//java
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
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
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This class act as a facade to the services offered by the checklist modules
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.105 $
 * @since $Date: 2006/11/20 03:04:04 $ Tag: $Name: $
 */
public class CheckListProxyManagerImpl implements ICheckListProxyManager {

	private SBCheckListProxyManager slsbCheckListProxyManager;

	public void setSlsbCheckListProxyManager(SBCheckListProxyManager slsbCheckListProxyManager) {
		this.slsbCheckListProxyManager = slsbCheckListProxyManager;
	}

	/**
	 * Get transaction sub-type for a checklist transaction.
	 * 
	 * @param aTrxID - primitive long denoting the checklist transaction
	 * @return String denoting the transaction sub-type
	 * @throws CheckListException on errors
	 */
	public String[] getTrxSubTypeByTrxID(long aTrxID) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getTrxSubTypeByTrxID(aTrxID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDocumentItemList: " + ex.toString());
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
			return this.slsbCheckListProxyManager.getItemList(anICheckList);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getItemList: ", ex.getCause());
		}
	}

    	/**
	 * Get the global items that are not in the checklist as well as those that
	 * are newly added at template level
	 * @param anICheckList - ICheckList
	 * @param anICheckList - collateralID
	 * @return IItem[] - the list of items that are not in the checkList as well
	 *         as those newly added at template level
	 * @throws CheckListException on errors
	 * @throws SearchDAOException is DAO errors
	 */
    //kc
	public IItem[] getItemList(ICheckList anICheckList, String collateralID) throws CheckListTemplateException, CheckListException,
			SearchDAOException {
		try {
			return this.slsbCheckListProxyManager.getItemList(anICheckList, collateralID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getItemList: ", ex.getCause());
		}
	}

	/**
	 * Get the list of c/c checklist summary info.
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListTemplateException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCCCheckListSummaryList(anIContext, aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCCCheckListSummaryList: ", ex.getCause());
		}
	}
	
	public FacilityCheckListSummary[] getFacilityCheckListSummaryList(IContext anIContext, long aLimitProfileID)
	throws CheckListTemplateException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.getFacilityCheckListSummaryList(anIContext, aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCCCheckListSummaryList: ", ex.getCause());
		}
		}

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
			throws CheckListTemplateException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.getAllCCCheckListSummaryList(anIContext, aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getAllCCCheckListSummaryList: ", ex.getCause());
		}
	}

	/**
	 * Get the list of c/c checklist summary info for non borrower
	 * @param anIContext of IContext type
	 * @param aCustomerID of long type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryListForNonBorrower(IContext anIContext, long aLimitProfileID,
			long aCustomerID, boolean isNBCheckListOnly) throws CheckListTemplateException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCCCheckListSummaryListForNonBorrower(anIContext, aLimitProfileID,
					aCustomerID, isNBCheckListOnly);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCollateralCheckListSummaryList: ", ex.getCause());
		}
	}

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
			long aCustomerID) throws CheckListTemplateException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.getAllCCCheckListSummaryListForNonBorrower(anIContext,
					aLimitProfileID, aCustomerID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getAllCCCheckListSummaryListForNonBorrower: "
					+ ex.toString());
		}
	}

	public HashMap getAllCCCheckListSummaryListForCustodian(IContext anIContext, long aLimitProfileID)
			throws CheckListTemplateException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.getAllCCCheckListSummaryListForCustodian(anIContext, aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getAllCCCheckListSummaryListForCustodian: "
					+ ex.toString());
		}
	}

	/**
	 * Get the list of c/c checklist summary info for custodian.
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryListForCustodian(IContext anIContext, long aLimitProfileID)
			throws CheckListTemplateException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCCCheckListSummaryListForCustodian(anIContext, aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCCCheckListSummaryListForCustodian: ", ex.getCause());
		}
	}

	/**
	 * Get the list of c/c checklist summary info for cc collaboration task
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryForCollaboration(IContext anIContext, ILimitProfile anILimitProfile)
			throws CheckListTemplateException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCCCheckListSummaryForCollaboration(anIContext, anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCCCheckListSummaryForCollaboration: ", ex.getCause());
		}
	}

	/**
	 * Get the list of c/c checklist summary info for ccc generation
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryForCCC(IContext anIContext, ILimitProfile anILimitProfile)
			throws CheckListTemplateException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCCCheckListSummaryForCCC(anIContext, anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCCCheckListSummaryForCCC: ", ex.getCause());
		}
	}

	/**
	 * Get the list of collateral checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errrors
	 */
	public CollateralCheckListSummary[] getCollateralCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCollateralCheckListSummaryList(anIContext, aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCollateralCheckListSummaryList: ", ex.getCause());
		}
	}
	
	/**
	 * Get the list of collateral checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errrors
	 */
	public CAMCheckListSummary[] getCAMCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCAMCheckListSummaryList(anIContext, aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCAMCheckListSummaryList: ", ex.getCause());
		}
	}

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
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getAllCollateralCheckListSummaryList(anIContext, aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getAllCollateralCheckListSummaryList: ", ex.getCause());
		}
	}

	
	/**
	 * Get the list of collateral checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 */
	public HashMap getAllCAMCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getAllCAMCheckListSummaryList(anIContext, aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getAllCollateralCheckListSummaryList: ", ex.getCause());
		}
	}

	
	public HashMap getAllFacilityCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getAllFacilityCheckListSummaryList(anIContext, aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getAllCollateralCheckListSummaryList: ", ex.getCause());
		}
	}
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
			long aLimitProfileID, boolean aCustodianInd) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getFilteredCollateralCheckListSummaryList(anIContext,
					aLimitProfileID, aCustodianInd);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getFilteredCollateralCheckListSummaryList: "
					+ ex.toString());
		}
	}

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
			boolean aCustodianInd) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getAllFilteredCollateralCheckListSummaryList(anIContext,
					aLimitProfileID, aCustodianInd);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getAllFilteredCollateralCheckListSummaryList: "
					+ ex.toString());
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
			return this.slsbCheckListProxyManager.getCollateralCheckListStatus(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCollateralCheckListStatus: ", ex.getCause());
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
			return this.slsbCheckListProxyManager.getCheckListByID(aCheckListID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCheckListByID: ", ex.getCause());
		}
	}
	
	public CheckListSearchResult getCheckListByCollateralID(long aCheckListID) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCheckListByCollateralID(aCheckListID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCheckListByID: ", ex.getCause());
		}
	}

	public CheckListSearchResult getCAMCheckListByCategoryAndProfileID(String category,long aCheckListID) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCAMCheckListByCategoryAndProfileID(category, aCheckListID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCheckListByID: ", ex.getCause());
		}
	}
	
	public CheckListSearchResult[] getCheckListByCategory(String category) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCheckListByCategory(category);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCheckListByID: ", ex.getCause());
		}
	}
	/**
	 * Get the collateral checklist trx value based on the checklist ID
	 * @param aCheckListID of long type
	 * @return ICheckListTrxValue - the checklist trx value containing the
	 *         checklist with the specified ID
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue getCheckList(long aCheckListID) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCheckList(aCheckListID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCheckList: ", ex.getCause());
		}
	}

	/**
	 * Get a checklist by transaction ID
	 * @param aTrxID of String type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue getCheckListByTrxID(String aTrxID) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCheckListByTrxID(aTrxID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCheckListByTrxID: ", ex.getCause());
		}
	}

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
			throws CheckListTemplateException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.searchCheckList(ctx, criteria);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in searchCheckList: ", ex.getCause());
		}
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
			return this.slsbCheckListProxyManager.getDefaultCCCheckList(anICheckListOwner, aLegalConstitution,
					anIBookingLocation, law);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDefaultCCCheckList: ", ex.getCause());
		}
	}

	public ICheckList getDefaultCCCheckList(String customerType, String legalConstitution, String country, String law)
			throws TemplateNotSetupException, CheckListTemplateException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.getDefaultCCCheckList(customerType, legalConstitution, country, law);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDefaultCCCheckList: ", ex.getCause());
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
			return this.slsbCheckListProxyManager.getDefaultCollateralCheckList(anICheckListOwner, aCollateralType,
					aCollateralSubType, aCountry, aOrgCode);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDefaultCollateralCheckList: ", ex.getCause());
		}
	}

	public ICheckList getDefaultCollateralCheckList(String country, String collateralType, String collateralSubType,
			String applicationType, String goodsStatus, String pbrInd) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.getDefaultCollateralCheckList(country, collateralType,
					collateralSubType, applicationType, goodsStatus, pbrInd);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDefaultCollateralCheckList: ", ex.getCause());
		}
	}
	
	
	public ICheckList getDefaultCAMCheckList(ICheckListOwner anICheckListOwner,String country, String collateralType, String collateralSubType,
			String applicationType, String goodsStatus, String pbrInd) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.getDefaultCAMCheckList(anICheckListOwner,country, collateralType,
					collateralSubType, applicationType, goodsStatus, pbrInd);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDefaultCollateralCheckList: ", ex.getCause());
		}
	}

	public ICheckList getDefaultFacilityCheckList(ICheckListOwner anICheckListOwner,String country, String collateralType, String collateralSubType,
			String applicationType, String goodsStatus, String pbrInd) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.getDefaultFacilityCheckList(anICheckListOwner,country, collateralType,
					collateralSubType, applicationType, goodsStatus, pbrInd);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDefaultCollateralCheckList: ", ex.getCause());
		}
	}
	/**
	 * Maker creation of a checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerCreateCheckList(ITrxContext anITrxContext, ICheckList anICheckList)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.makerCreateCheckList(anITrxContext, anICheckList);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in makerCreateCheckList: ", ex.getCause());
		}
	}

	public ICheckListTrxValue makerCreateCheckListWithoutApproval(ITrxContext anITrxContext, ICheckList anICheckList)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.makerCreateCheckListWithoutApproval(anITrxContext, anICheckList);
		}
		catch (RemoteException ex) {
			throw new CheckListException("failed to create checklist through checklist proxy remote interface", ex
					.getCause());
		}
	}

	public ICheckListTrxValue makerUpdateCheckListWithoutApproval(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.makerUpdateCheckListWithoutApproval(anITrxContext,
					anICheckListTrxValue, anICheckList);
		}
		catch (RemoteException ex) {
			throw new CheckListException("failed to update checklist through checklist proxy remote interface", ex
					.getCause());
		}
	}

	/**
	 * Copy Checklist due to BCA Renewal
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue copyCheckList(ITrxContext anITrxContext, ICheckList anICheckList)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.copyCheckList(anITrxContext, anICheckList);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in copyCheckList: ", ex.getCause());
		}
	}

	/**
	 * Checker approves a checklist trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue checkerApproveCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.checkerApproveCheckList(anITrxContext, anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in checkerApproveCheckList: ", ex.getCause());
		}
	}

	/**
	 * Manager approves a checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue managerApproveCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.managerApproveCheckListReceipt(anITrxContext, anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in managerApproveCheckListReceipt: ", ex.getCause());
		}
	}

	/**
	 * Checker rejects a checklist trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue checkerRejectCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.checkerRejectCheckList(anITrxContext, anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in checkerRejectCheckList: ", ex.getCause());
		}
	}

	/**
	 * Maker closes a checklist trx that has been rejected by the checker
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerCloseCheckListTrx(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.makerCloseCheckListTrx(anITrxContext, anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in makerCloseCheckListTrx: ", ex.getCause());
		}
	}

	/**
	 * Maker edits a rejected checklist
	 * @param anITrxContext - ITrxContext
	 * @param anICheckListTrxValue - ICheckListTrxValue
	 * @param anICheckList - ICheckList
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerEditRejectedCheckListTrx(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.makerEditRejectedCheckListTrx(anITrxContext, anICheckListTrxValue,
					anICheckList);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in makerEditRejectedCheckListTrx: ", ex.getCause());
		}
	}

	/**
	 * Maker updates a checklist
	 * @param anITrxContext - ITrxContext
	 * @param anICheckListTrxValue - ICheckListTrxValue
	 * @param anICheckList - ICheckList
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerUpdateCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue,
			ICheckList anICheckList) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.makerUpdateCheckList(anITrxContext, anICheckListTrxValue,
					anICheckList);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in makerUpdateCheckList: ", ex.getCause());
		}
	}

	/**
	 * Maker deletes a checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerDeleteCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.makerDeleteCheckList(anITrxContext, anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in makerDeleteCheckList: ", ex.getCause());
		}
	}

	/**
	 * Maker updates a checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerUpdateCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.makerUpdateCheckListReceipt(anITrxContext, anICheckListTrxValue,
					anICheckList);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in makerUpdateCheckListReceipt: ", ex.getCause());
		}
	}

	/**
	 * Checker approves a checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue checkerApproveCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.checkerApproveCheckListReceipt(anITrxContext, anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in checkerApproveCheckListReceipt: ", ex.getCause());
		}
	}
	
	/*public ICheckListTrxValue checkerApproveCheckListReceiptScheduler(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue,String flagScheduler) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.checkerApproveCheckListReceiptScheduler(anITrxContext, anICheckListTrxValue,flagScheduler);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in checkerApproveCheckListReceiptScheduler: ", ex.getCause());
		}
	}*/

	/**
	 * Checker rejects a checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue checkerRejectCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.checkerRejectCheckListReceipt(anITrxContext, anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in checkerRejectCheckListReceipt: ", ex.getCause());
		}
	}

	/**
	 * Checker updates a checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue checkerUpdateCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.checkerUpdateCheckListReceipt(anITrxContext, anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in checkerRejectCheckListReceipt: ", ex.getCause());
		}
	}

	/**
	 * Updates the status of the shared checklists. Should be called after
	 * Checker Approve Update Checklist / Receipt (for both cc and security)
	 * @param trxValue of ICheckListTrxValue type
	 * @throws CheckListException on errors
	 */
	public void updateSharedChecklistStatus(ICheckListTrxValue trxValue) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.updateSharedChecklistStatus(trxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in checkerRejectCheckListReceipt: ", ex.getCause());
		}
	}

	/**
	 * Manager rejects a checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue managerRejectCheckListReceipt(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.managerRejectCheckListReceipt(anITrxContext, anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in managerRejectCheckListReceipt: ", ex.getCause());
		}
	}

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
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.makerCloseCheckListReceiptTrx(anITrxContext, anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in makerCloseCheckListReceiptTrx: ", ex.getCause());
		}
	}

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
			ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.makerEditRejectedCheckListReceiptTrx(anITrxContext,
					anICheckListTrxValue, anICheckList);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in makerEditRejectedCheckListReceiptTrx: ", ex.getCause());
		}
	}

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
			ICheckList anICheckList) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.makerSaveCheckList(anITrxContext, anICheckListTrxValue, anICheckList);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in makerSaveCheckList: ", ex.getCause());
		}
	}

	/**
	 * Maker cancel a save the checkList as draft
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerCancelSavedCheckList(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.makerCancelSavedCheckList(anITrxContext, anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in makerCancelSaveCheckList: ", ex.getCause());
		}
	}

	/**
	 * Retrieve next checklist receipts based on user action. This may result in
	 * the receipt state being modified and/or a new receipt being created.
	 * 
	 * @param receipt checklist receipt on which the action will be performed
	 * @param action user action on the checklist receipt
	 * @throws CheckListException error in retrieving next state of the
	 *         checklist receipt
	 */
	public ICheckListItem[] getNextCheckListReceipts(ICheckListItem receipt, String action) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getNextCheckListReceipts(receipt, action);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getNextCheckListReceipts: ", ex.getCause());
		}
	}
	
	public ICheckListItem[] getNextCheckListReceiptsOld(ICheckListItem receipt, String action) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getNextCheckListReceiptsOld(receipt, action);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getNextCheckListReceipts: ", ex.getCause());
		}
	}

	/**
	 * To get the next state of the checklist item
	 * @param aCurrentState of String type
	 * @param aCustodianState of String type
	 * @param anAction of String type
	 * @return HashMap - the next checklist item state and the custodian state
	 * @throws CheckListException on errors
	 */
	public HashMap getNextCheckListItemState(String aCurrentState, String aCustodianState, String anAction)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getNextCheckListItemState(aCurrentState, aCustodianState, anAction);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getNextCheckListItemState: ", ex.getCause());
		}
	}

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
			long aCheckListItemID) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.resetCheckListItem(anICheckListTrxValue, anICheckList,
					aCheckListItemID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getNextCheckListItemState: ", ex.getCause());
		}
	}

	/**
	 * Get a checklist item by the item reference
	 * @param aCheckListItemRef of long type
	 * @return ICheckListItem - the checklist item with the checklist item
	 *         reference
	 * @throws CheckListException on errors
	 */
	public ICheckListItem getCheckListItem(long aCheckListItemRef) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCheckListItem(aCheckListItemRef);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCheckListItem: ", ex.getCause());
		}
	}

	/**
	 * Update a checklist item based on the primary key
	 * @param aStagingCheckListItemID of long type
	 * @param aCheckListItemRef of long type
	 * @throws CheckListException on errors
	 */
	public void expireCheckListItem(long aStagingCheckListItemID, long aCheckListItemRef) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.expireCheckListItem(aStagingCheckListItemID, aCheckListItemRef);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in expireCheckListItem: ", ex.getCause());
		}
	}

	/**
	 * Get the list of allowable checklist item operations
	 * @return ICheckListItemOperation[] - the list of checklist item operations
	 * @throws CheckListException on errors
	 */
	public ICheckListItemOperation[] getAllowableOperationList() throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getAllowableOperationList();
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getAllowableOperationList: ", ex.getCause());
		}
	}

	/**
	 * To return false if there is any pending trx
	 * @param anICheckListOwner of ICheckListOwner type
	 * @return int - true if there already exist and false otherwise
	 * @throws CheckListException on errors
	 */
	public int allowCheckListTrx(ICheckListOwner anICheckListOwner) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.allowCheckListTrx(anICheckListOwner);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in allowCheckListTrx: ", ex.getCause());
		}
	}

	/**
	 * To close the collateral related checklist
	 * @param anITrxContext of ITrxContext type
	 * @param aCollateralID of long type
	 * @throws CheckListException on errors
	 */
	public void systemCloseCollateralCheckListTrx(ITrxContext anITrxContext, long aCollateralID)
			throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemCloseCollateralCheckListTrx(anITrxContext, aCollateralID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemCloseCollateralCheckListTrx: ", ex.getCause());
		}
	}

	/**
	 * To delete the collateral related checklist only
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteCollateralCheckListTrxOnly(long aLimitProfileID, long aCollateralID)
			throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemDeleteCollateralCheckListTrxOnly(aLimitProfileID, aCollateralID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemDeleteCollateralCheckListTrxOnly: ", ex.getCause());
		}
	}

	/**
	 * To delete the collateral related checklist
	 * @param anICollateral of ICollateral type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteCollateralCheckListTrx(ICollateral anICollateral) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemDeleteCollateralCheckListTrx(anICollateral);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemDeleteCollateralCheckListTrx: ", ex.getCause());
		}
	}

	/**
	 * To delete all the cc checklist related to a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteCCCheckListTrx(ILimitProfile anILimitProfile) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemDeleteCCCheckListTrx(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemDeleteCCCheckListTrx: ", ex.getCause());
		}
	}

	/**
	 * To renew all the cc checklist related to a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CheckListException on errors
	 */
	public void systemRenewCCCheckListTrx(ILimitProfile anILimitProfile) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemRenewCCCheckListTrx(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemRenewCCCheckListTrx: ", ex.getCause());
		}
	}

	public void systemDeleteCollateralCheckListTrx(long aLimitProfileID, long aCollateralID,
			long[] aDeletedLimitSecMapList) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemDeleteCollateralCheckListTrx(aLimitProfileID, aCollateralID,
					aDeletedLimitSecMapList);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemDeleteCollateralCheckListTrx: ", ex.getCause());
		}
	}

	/**
	 * To delete the collateral related checklist
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteCollateralCheckListTrx(long aLimitProfileID, long aCollateralID) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemDeleteCollateralCheckListTrx(aLimitProfileID, aCollateralID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemDeleteCollateralCheckListTrx: ", ex.getCause());
		}
	}

	/**
	 * To renew all the collateral related checklists
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CheckListException on errors
	 */
	public void systemRenewCollateralCheckListTrx(ILimitProfile anILimitProfile) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemRenewCollateralCheckListTrx(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemRenewCollateralCheckListTrx: ", ex.getCause());
		}
	}

	/**
	 * To renew the collateral related checklist
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @throws CheckListException on errors
	 */
	public void systemRenewCollateralCheckListTrx(long aLimitProfileID, long aCollateralID) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemRenewCollateralCheckListTrx(aLimitProfileID, aCollateralID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemRenewCollateralCheckListTrx: ", ex.getCause());
		}
	}

	/**
	 * To delete the non borrower checklist related to a customer
	 * @param aCustomerID of long type
	 * @throws CheckListException on error
	 */
	public void systemDeleteNonBorrowerCheckListTrx(long aCustomerID) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemDeleteNonBorrowerCheckListTrx(aCustomerID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemDeleteNonBorrowerCheckListTrx: ", ex.getCause());
		}
	}

	/**
	 * To delete the main borrower checklist related to a limit profile and a
	 * customer
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteMainBorrowerCheckListTrx(long aLimitProfileID, long aCustomerID) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemDeleteMainBorrowerCheckListTrx(aLimitProfileID, aCustomerID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemDeleteMainBorrowerCheckListTrx: ", ex.getCause());
		}
	}

	/**
	 * To delete all the cc checklist related to a limit profile and a
	 * coborrower
	 * @param aCustomerID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteCoBorrowerCheckListTrx(long aCustomerID) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemDeleteCoBorrowerCheckListTrx(aCustomerID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemDeleteCoBorrowerCheckListTrx: ", ex.getCause());
		}
	}

	/**
	 * To delete all the cc checklist related to a limit profile and a
	 * coborrower
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteCoBorrowerCheckListTrx(long aLimitProfileID, long aCustomerID) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemDeleteCoBorrowerCheckListTrx(aLimitProfileID, aCustomerID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemDeleteCoBorrowerCheckListTrx: ", ex.getCause());
		}
	}

	/**
	 * To delete all the cc checklist related to a pledgor
	 * @param aPledgorID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeletePledgorCheckListTrx(long aPledgorID) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemDeletePledgorCheckListTrx(aPledgorID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemDeletePledgorCheckListTrx: ", ex.getCause());
		}
	}

	/**
	 * To delete all the cc checklist related to a limit profile and a pledgor
	 * @param aLimitProfileID of long type
	 * @param aPledgorID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeletePledgorCheckListTrx(long aLimitProfileID, long aPledgorID) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemDeletePledgorCheckListTrx(aLimitProfileID, aPledgorID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemDeletePledgorCheckListTrx: ", ex.getCause());
		}
	}

	/**
	 * To close the checklist trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemCloseCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.systemCloseCheckList(anITrxContext, anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemCloseCheckList: ", ex.getCause());
		}
	}

	/**
	 * Get the list of checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @return HashMap - the list of checklist items that requires auditing
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 */
	public HashMap getCheckListAuditList(long aLimitProfileID) throws SearchDAOException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCheckListAuditList(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemCloseCheckList: ", ex.getCause());
		}
	}

	/**
	 * Get the list of checklist items that qualify for audit under a limit
	 * profile
	 * @param aLimitProfileID of long String
	 * @return IAuditItem[] - the list of checklist that qualifies for audit
	 * @throws CheckListException on errors
	 */
	public IAuditItem[] getAuditItemList(long aLimitProfileID) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getAuditItemList(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getAuditItemList: ", ex.getCause());
		}
	}

	/**
	 * Get the list of checklist items that qualify for audit under a non
	 * borrower
	 * @param aCustomerID of long String
	 * @return HashMap - the list of checklist that qualifies for audit
	 * @throws CheckListException on errors
	 * @throws SearchDAOException on DAO errors
	 */
	public HashMap getCheckListAuditListForNonBorrower(long aCustomerID) throws CheckListException, SearchDAOException {
		try {
			return this.slsbCheckListProxyManager.getCheckListAuditListForNonBorrower(aCustomerID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCheckListAuditListForNonBorrower: ", ex.getCause());
		}
	}

	/**
	 * Get the list of checklist items that qualify for audit under a non
	 * borrower
	 * @param aCustomerID of long String
	 * @return IAuditItem[] - the list of checklist that qualifies for audit
	 * @throws CheckListException on errors
	 * @throws CheckListException on DAO errors
	 */
	public IAuditItem[] getAuditItemListForNonBorrower(long aCustomerID) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getAuditItemListForNonBorrower(aCustomerID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getAuditItemListForNonBorrower: ", ex.getCause());
		}
	}

	/**
	 * Get the list of document categories for borrower.
	 * @param aLimitProfileID of long type
	 * @return HashMap - the list of document categories
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 */
	public HashMap getDocumentCategories(long aLimitProfileID) throws SearchDAOException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.getDocumentCategories(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDocumentCategories: ", ex.getCause());
		}
	}

	/**
	 * Get the list of document categories for non-borrower.
	 * @param aCustomerID of long type
	 * @return HashMap - the list of document categories
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 */
	public HashMap getDocumentCategoriesForNonBorrower(long aCustomerID, long aLimitProfileID)
			throws SearchDAOException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.getDocumentCategoriesForNonBorrower(aCustomerID, aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDocumentCategories: ", ex.getCause());
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
			return this.slsbCheckListProxyManager.getDocumentsHeld(criteria);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDocumentsHeld: ", ex.getCause());
		}
	}

	/**
	 * Get the list of securities pledged for this borrower type.
	 * @param aLimitProfileID the limit profile ID
	 * @param pledgorID the pledgor ID
	 * @return HashMap - the securities pledged for this customer
	 * @throws SearchDAOException on errors
	 * @throws CheckListException on errors
	 */
	public HashMap getSecuritiesPledged(long aLimitProfileID, long pledgorID) throws SearchDAOException,
			CheckListException {
		try {
			return this.slsbCheckListProxyManager.getSecuritiesPledged(aLimitProfileID, pledgorID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getSecuritiesPledged: ", ex.getCause());
		}
	}

	/**
	 * To check if a cc doc item description already exist or not
	 * @param aDocItemDescription of String type
	 * @return boolean - true if the doc item description already exist and
	 *         false otherwise
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public boolean checkCCItemDescAlreadyExist(ITemplate anITemplate, String aDocItemDescription)
			throws CheckListTemplateException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.checkCCItemDescAlreadyExist(anITemplate, aDocItemDescription);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in checkCCItemDescAlreadyExist: ", ex.getCause());
		}
	}

	/**
	 * To check if a security doc item description already exist or not
	 * @param aDocItemDescription of String type
	 * @return boolean - true if the doc item description already exist and
	 *         false otherwise
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public boolean checkCollateralItemDescAlreadyExist(ITemplate anITemplate, String aDocItemDescription)
			throws CheckListTemplateException, CheckListException {
		try {
			return this.slsbCheckListProxyManager.checkCollateralItemDescAlreadyExist(anITemplate, aDocItemDescription);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in checkCollateralItemDescAlreadyExist: ", ex.getCause());
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
			return this.slsbCheckListProxyManager.getCollateralCheckList(aLimitProfileID, aCollateralID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCollateralCheckList: ", ex.getCause());
		}
	}

	/**
	 * System trigger for waiver generation
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemGenerateCheckListWaiver(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.systemGenerateCheckListWaiver(anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemGenerateCheckListWaiver: ", ex.getCause());
		}
	}

	/**
	 * System trigger for reject waiver generation
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemRejectGenerateCheckListWaiver(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.systemRejectGenerateCheckListWaiver(anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemRejectGenerateCheckListWaiver: ", ex.getCause());
		}
	}

	/**
	 * System trigger for approving waiver generation
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemApproveGenerateCheckListWaiver(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.systemApproveGenerateCheckListWaiver(anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemApproveGenerateCheckListWaiver: ", ex.getCause());
		}
	}

	/**
	 * System trigger for deferral generation
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemGenerateCheckListDeferral(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.systemGenerateCheckListDeferral(anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemGenerateCheckListDeferral: ", ex.getCause());
		}
	}

	/**
	 * System trigger for reject deferral generation
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemRejectGenerateCheckListDeferral(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.systemRejectGenerateCheckListDeferral(anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemRejectGenerateCheckListDeferral: ", ex.getCause());
		}
	}

	/**
	 * System trigger for approving deferral generation
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemApproveGenerateCheckListDeferral(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.systemApproveGenerateCheckListDeferral(anICheckListTrxValue);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemApproveGenerateCheckListDeferral: ", ex.getCause());
		}
	}

	/**
	 * System convert non-borrower to borrower
	 * @param aLimitProfileID of long type
	 * @throws CheckListException on errors
	 */
	public void systemConvertNonBorrowerToBorrower(long aLimitProfileID) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemConvertNonBorrowerToBorrower(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemConvertNonBorrowerToBorrower: ", ex.getCause());
		}
	}

	/**
	 * System convert a borrower to a non borrower
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @throws CheckListException on errors
	 */
	public void systemConvertBorrowerToNonBorrower(long aLimitProfileID, long aCustomerID) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemConvertBorrowerToNonBorrower(aLimitProfileID, aCustomerID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemConvertBorrowerToNonBorrower: ", ex.getCause());
		}
	}

	/**
	 * To lock all the checklist items under a checklist
	 * @param aCheckListID of long type
	 * @throws CheckListException
	 */
	public void lockCheckListItemByCheckList(long aCheckListID) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.lockCheckListItemByCheckList(aCheckListID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in lockCheckListItemByCheckList: ", ex.getCause());
		}
	}

	/**
	 * To system update a checklist by checklist ID
	 * @param aCheckListID of long type
	 * @throws CheckListException on errors
	 */
	public void systemUpdateCheckList(long aCheckListID) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemUpdateCheckList(aCheckListID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemUpdateCheckList: ", ex.getCause());
		}
	}

	/**
	 * To unlock all the checklist items under a checklist
	 * @param aCheckListID of long type
	 * @throws CheckListException
	 */
	public void unlockCheckListItemByCheckList(long aCheckListID) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.unlockCheckListItemByCheckList(aCheckListID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in unlockCheckListItemByCheckList: ", ex.getCause());
		}
	}

	/**
	 * To lock all the checklist items under a limitprofile
	 * @param aLimitProfileID of long type
	 * @throws CheckListException
	 */
	public void lockCheckListItemByLimitProfile(long aLimitProfileID) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.lockCheckListItemByLimitProfile(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in lockCheckListItemByLimitProfile: ", ex.getCause());
		}
	}

	/**
	 * To unlock all the checklist items under a limitprofile
	 * @param aLimitProfileID of long type
	 * @throws CheckListException
	 */
	public void unlockCheckListItemByLimitProfile(long aLimitProfileID) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.unlockCheckListItemByLimitProfile(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in unlockCheckListItemByLimitProfile: ", ex.getCause());
		}
	}

	/**
	 * Check if all collateral checklists are being deleted
	 * @param aCollateralID of long type
	 * @return boolean - true of all collateral checklists are deleted and false
	 *         otherwise
	 * @throws CheckListException on errors
	 */
	public boolean areAllCheckListsDeleted(long aCollateralID) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.areAllCheckListsDeleted(aCollateralID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in areAllCheckListsDeleted: ", ex.getCause());
		}
	}

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
			String collateralLocation) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.isInSameCountry(anIContext, anILimitProfile, collateralID,
					collateralLocation);
		}
		catch (RemoteException ex) {
			throw new CheckListException("CheckListException in isInSameCountry: ", ex.getCause());
		}
	}

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
			String domicileCountry, String custCategory) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.isInSameCountry(anIContext, anILimitProfile, customerID,
					domicileCountry, custCategory);
		}
		catch (RemoteException ex) {
			throw new CheckListException("CheckListException in isInSameCountry: ", ex.getCause());
		}
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
			return this.slsbCheckListProxyManager.getSecurityChkListCount(aCollateralID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in checkCCItemDescAlreadyExist: ", ex.getCause());
		}
	}

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
			CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCheckListAudit(aLimitProfileID, asOfDate, aCustCat);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemCloseCheckList: ", ex.getCause());
		}
	}

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
			throws CheckListException, SearchDAOException {
		try {
			return this.slsbCheckListProxyManager.getCheckListAuditForNonBorrower(aCustomerID, asOfDate, aCustCat);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCheckListAuditListForNonBorrower: ", ex.getCause());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.integrosys.cms.app.checklist.proxy.ICheckListProxyManager#
	 * officerForwardCommodityDeal
	 * (com.integrosys.cms.app.transaction.ITrxContext,
	 * com.integrosys.cms.app.checklist.trx.ICheckListTrxValue)
	 */
	public ICheckListTrxValue officeOperation(ITrxContext ctx, ICheckListTrxValue trxVal) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.officeOperation(ctx, trxVal);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in officerForwardCommodityDeal: ", ex.getCause());
		}
	}

	/**
	 * To system delete pledgor checklist if it is no longer valid due to this
	 * co-borrower
	 * @param aCoBorrowerLegalRef
	 * @param aLimitProfileRef
	 * @throws CheckListException
	 */
	public void systemHandleCoBorrowerCheckList(long aCoBorrowerLegalRef, long aLimitProfileRef)
			throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemHandleCoBorrowerCheckList(aCoBorrowerLegalRef, aLimitProfileRef);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemHandleCoBorrowerCheckList", ex.getCause());
		}
	}

	/**
	 * To system delete pledgor checklists if it is no longer valid due to the
	 * legal reference of the pledgor
	 * @param aPledgorID of long type
	 * @param aPledgorLegalRef of long type
	 * @throws CheckListException on errors
	 */
	public void systemHandlePledgorCheckList(long aPledgorID, long aPledgorLegalRef) throws CheckListException {
		try {
			this.slsbCheckListProxyManager.systemHandlePledgorCheckList(aPledgorID, aPledgorLegalRef);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in systemHandlePledgorCheckList", ex.getCause());
		}
	}

	/**
	 * Get a list of checklist created for the given limit profile.
	 * 
	 * @param limitProfile of type ILimitProfile
	 * @return a list of checklist
	 * @throws CheckListException on any errors encountered
	 */
	public ICheckList[] getAllCheckList(ILimitProfile limitProfile) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getAllCheckList(limitProfile);
		}
		catch (RemoteException e) {
			throw new CheckListException("RemoteException caught in getAllCheckList:", e.getCause());
		}
	}

	/**
	 * Generate checklist item sequence number.
	 * 
	 * @return long
	 * @throws CheckListException on any error encountered
	 */
	public long generateCheckListItemSeqNo() throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.generateCheckListItemSeqNo();
		}
		catch (RemoteException e) {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * system create check list
	 */
	public ICheckListTrxValue systemCreateCheckList(ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.systemCreateCheckList(anICheckListTrxValue, anICheckList);
		}
		catch (RemoteException e) {
			throw new CheckListException("failed to create checklist [" + anICheckList + "]", e.getCause());
		}
	}

	/**
	 * system create document checklist
	 */
	public ICheckListTrxValue systemCreateDocumentCheckList(ICheckListTrxValue anICheckListTrxValue,
			ICheckList anICheckList) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.systemCreateDocumentCheckList(anICheckListTrxValue, anICheckList);
		}
		catch (RemoteException e) {
			throw new CheckListException("failed to create document checklist [" + anICheckList + "]", e.getCause());
		}
	}

	/**
	 * system update check list
	 */
	public ICheckListTrxValue systemUpdateCheckList(ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList)
			throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.systemUpdateCheckList(anICheckListTrxValue, anICheckList);
		}
		catch (RemoteException e) {
			throw new CheckListException("failed to update checklist [" + anICheckList + "]", e.getCause());
		}
	}

	/**
	 * direct update checklist
	 */
	public ICheckListTrxValue directUpdateCheckList(ICheckListTrxValue trxValue) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.directUpdateCheckList(trxValue);
		}
		catch (RemoteException e) {
			throw new CheckListException("failed to direct update checklist [" + trxValue + "]", e.getCause());
		}
	}

	public List getCheckListDetailsByCheckListId(String[] aCheckListId, String categoryType) throws CheckListException,
			SearchDAOException {
		try {
			return this.slsbCheckListProxyManager.getCheckListDetailsByCheckListId(aCheckListId, categoryType);
		}
		catch (RemoteException e) {
			throw new CheckListException("failed to get checklist details by checklist id ["
					+ ArrayUtils.toString(aCheckListId) + "], category type [" + categoryType + "]", e.getCause());
		}
	}

	public List getCheckListDetailsByCheckListId(String[] aCheckListId) throws CheckListException, SearchDAOException {
		try {
			return this.slsbCheckListProxyManager.getCheckListDetailsByCheckListId(aCheckListId);
		}
		catch (RemoteException e) {
			throw new CheckListException("failed to get checklist details by checklist id ["
					+ ArrayUtils.toString(aCheckListId) + "]", e.getCause());
		}
	}

	public List getAllShareDocuments(long id, boolean isNonBorrower) throws CheckListException, SearchDAOException {
		try {
			return this.slsbCheckListProxyManager.getAllShareDocuments(id, isNonBorrower);
		}
		catch (RemoteException e) {
			throw new CheckListException("failed to get all shared documents , id [" + id + "], is non borrower ["
					+ isNonBorrower + "]", e.getCause());
		}
	}
	
	public List getAllDeferCreditApprover() throws CheckListException, SearchDAOException {
		try {
			return this.slsbCheckListProxyManager.getAllDeferCreditApprover();
		}
		catch (Exception e) {
			throw new CheckListException("failed to get all shared documents , id ");
		}
	}
	
	public List getAllWaiveCreditApprover() throws CheckListException, SearchDAOException {
		try {
			return this.slsbCheckListProxyManager.getAllWaiveCreditApprover();
		}
		catch (Exception e) {
			throw new CheckListException("failed to get all shared documents ");
		}
	}
	
	public List getAllBothCreditApprover() throws CheckListException, SearchDAOException {
		try {
			return this.slsbCheckListProxyManager.getAllBothCreditApprover();
		}
		catch (Exception e) {
			throw new CheckListException("failed to get all shared documents ");
		}
	}

	public HashMap getCollateralLimitMap(ILimitProfile anILimitProfile) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCollateralLimitMap(anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDocumentIds: ", ex.getCause());
		}
	}

	public List getCustomerListByCollateralID(long collateralId) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCustomerListByCollateralID(collateralId);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCustomerListByCollateralID: ", ex.getCause());
		}
	}

	// Method added by Pratheepa on 29Sep2006 while fixing bug CMS-3479
	public ArrayList getDocumentIdsForCheckList(ArrayList documentIds) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getDocumentIdsForCheckList(documentIds);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCustomerListByCollateralID: ", ex.getCause());
		}
	}

	public HashMap getCollateralIdForSharedDocs(long documentNo, Long[] collateralIds) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCollateralIdForSharedDocs(documentNo, collateralIds);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCustomerListByCollateralID: ", ex.getCause());
		}
	}

	public HashMap getSecuritySubTypes(String secType) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getSecuritySubTypes(secType);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getSecurityTypes: ", ex.getCause());
		}
	}

	public HashMap getStateDistrictMukim(HashMap map) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getStateDistrictMukim(map);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getStateDistrictMukim: ", ex.getCause());
		}
	}

	public HashMap getDocumentationStatus(long lmtProfileID) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getDocumentationStatus(lmtProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDocumentationStatus: ", ex.getCause());
		}
	}

	public boolean getChecklistCompletedStatus(long collID) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getChecklistCompletedStatus(collID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getChecklistCompletedStatus: ", ex.getCause());
		}
	}

	public int getAssetResidualLife(String secSubTypeId) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getAssetResidualLife(secSubTypeId);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getAssetResidualLife: ", ex.getCause());
		}
	}

	public HashMap[] getDetailsForPreDisbursementReminderLetter(long limitProfileID) throws SearchDAOException,
			CheckListException {
		try {
			return this.slsbCheckListProxyManager.getDetailsForPreDisbursementReminderLetter(limitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDetailsForPreDisbursementReminderLetter: ", ex
					.getCause());
		}
	}

	public HashMap[] getDetailsForPostDisbursementReminderLetter(long limitProfileID) throws SearchDAOException,
			CheckListException {
		try {
			return this.slsbCheckListProxyManager.getDetailsForPostDisbursementReminderLetter(limitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDetailsForPostDisbursementReminderLetter ", ex
					.getCause());
		}
	}

	public HashMap getCCCheckListStatus(long aLimitProfileID, boolean isFullListInd) throws SearchDAOException,
			CheckListException {
		try {
			return this.slsbCheckListProxyManager.getCCCheckListStatus(aLimitProfileID, isFullListInd);
		}
		catch (RemoteException ex) {
			throw new CheckListException("Failed to retrieve CC checklist status for limit profile id ["
					+ aLimitProfileID + "] full list indicator [" + isFullListInd + "]", ex.getCause());
		}
	}

    public HashMap getCheckListItemListbyCategory(long aLimitProfileID, String aCheckListCategory) throws SearchDAOException, CheckListException {
        try {

            return this.slsbCheckListProxyManager.getCheckListItemListbyCategory(aLimitProfileID, aCheckListCategory);

        } catch (RemoteException ex) {
            throw new CheckListException("Failed to retrieve checklist item by limit profile [" + aLimitProfileID + "] and checklist category [" + aCheckListCategory + "]", ex.getCause());
        }
    }

	public ICheckList getDefaultPariPassuCheckList(
			ICheckListOwner anICheckListOwner, String pariPassuType,
			String pariPassuSubType, String country, String orgCode)
			throws TemplateNotSetupException, CheckListTemplateException,
			CheckListException {
		try {
			return this.slsbCheckListProxyManager.getDefaultPariPassuCheckList(anICheckListOwner, pariPassuType,
					pariPassuSubType, country, orgCode);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getDefaultCollateralCheckList: ", ex.getCause());
		}
	}

	public PariPassuCheckListSummary[] getPariPassuCheckListSummaryList(
			IContext anIContext, long limitProfileID) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getPariPassuCheckListSummaryList(anIContext, limitProfileID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCAMCheckListSummaryList: ", ex.getCause());
		}
	}

	public CheckListSearchResult getPariPassuCheckListByCategoryAndProfileID(
			String category, long paripassuID) throws CheckListException {
		try {
			return this.slsbCheckListProxyManager.getPariPassuCheckListByCategoryAndProfileID(category, paripassuID);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException in getCheckListByID: ", ex.getCause());
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
			ICheckListProxyManager icheckListProxyManager1;
			if(this.slsbCheckListProxyManager == null) {
				icheckListProxyManager1 = (ICheckListProxyManager) BeanHouse.get("slsbCheckListProxyManager");
				this.slsbCheckListProxyManager = (SBCheckListProxyManager) icheckListProxyManager1;
			}
			return this.slsbCheckListProxyManager.getCheckListItemById(checkListItemId);
		}
		catch (RemoteException ex) {
			throw new CheckListException("RemoteException enctr at getCheckListItemById: ", ex.getCause());
		}
	}	

}
