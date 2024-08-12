package com.integrosys.cms.app.chktemplate.proxy;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.CollateralSubTypeSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.chktemplate.bus.IDynamicPropertySetup;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.bus.LawSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.TemplateItemSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException;
import com.integrosys.cms.app.chktemplate.bus.TemplateSearchCriteria;
import com.integrosys.cms.app.chktemplate.trx.IDocumentItemTrxValue;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Created by IntelliJ IDEA. User: user Date: Jul 16, 2008 Time: 5:24:52 PM To
 * change this template use File | Settings | File Templates.
 */
public interface SBCheckListTemplateProxyManager extends EJBObject {
	/**
	 * Get the document item by criteria
	 * @param aCriteria - DocumentSearchCriteria
	 * @return SearchResult - the search result containing the list of document
	 *         items that satisfy the criteria
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on general errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is DAO
	 *         errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	SearchResult getDocumentItemList(DocumentSearchCriteria aCriteria) throws CheckListTemplateException,
			SearchDAOException, RemoteException;
	
	SearchResult getFilteredDocumentItemList(DocumentSearchCriteria aCriteria,List docCrit) throws CheckListTemplateException,
	SearchDAOException, RemoteException;

	/**
	 * Get the global items as well as those that are newly added at template
	 * level It will perform the following 1. Get the list of global doc if any
	 * 2. Get the list of item under the template that the checklist inherited
	 * from 3. Merge the 2 lists
	 * @param checklistCategory - Category of checklist
	 * @param templateID - Template ID
	 * @return IItem[] - the list of items that are in global as well as those
	 *         newly added at template level
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is DAO
	 *         errors
	 */
	public IItem[] getParentItemList(String checklistCategory, long templateID) throws CheckListTemplateException,
			SearchDAOException, RemoteException;

    	/**
	 * Get the global items as well as those that are newly added at template
	 * level It will perform the following 1. Get the list of global doc if any
	 * 2. Get the list of item under the template that the checklist inherited
	 * from 3. Merge the 2 lists
	 * @param checklistCategory - Category of checklist
	 * @param templateID - Template ID
     * @param goodStatus - N, U, R, I, G
     * @param pbrInd     - PBR, PBT, NA
	 * @return IItem[] - the list of items that are in global as well as those
	 *         newly added at template level
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is DAO
	 *         errors
	 */                                                      //ckc
	public IItem[] getParentItemList(String checklistCategory, long templateID, String goodStatus, String pbrInd) throws CheckListTemplateException,
			SearchDAOException, RemoteException;

	/**
	 * Get a document item by transaction ID
	 * @param aTrxID - String
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	IDocumentItemTrxValue getDocumentItemByTrxID(String aTrxID) throws CheckListTemplateException, RemoteException;

	/**
	 * Maker creation of a doc item checklist
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	IDocumentItemTrxValue makerCreateDocItem(ITrxContext anITrxContext, IDocumentItem anIDocumentItem)
			throws CheckListTemplateException, RemoteException;

	/**
	 * Maker Draft of a doc item checklist
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	IDocumentItemTrxValue makerDraftDocItem(ITrxContext anITrxContext, IDocumentItem anIDocumentItem)
			throws CheckListTemplateException, RemoteException;

	/**
	 * Checker approve a doc item transaction
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	IDocumentItemTrxValue checkerApproveDocItem(ITrxContext anITrxContext, IDocumentItemTrxValue anIDocumentItemTrxValue)
			throws CheckListTemplateException, RemoteException;

	/**
	 * Checker reject a doc item trx
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	IDocumentItemTrxValue checkerRejectDocItem(ITrxContext anITrxContext, IDocumentItemTrxValue anIDocumentItemTrxValue)
			throws CheckListTemplateException, RemoteException;

	/**
	 * Maker closes a doc item trx that has been rejected by the checker
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	IDocumentItemTrxValue makerCloseDocItemTrx(ITrxContext anITrxContext, IDocumentItemTrxValue anIDocumentItemTrxValue)
			throws CheckListTemplateException, RemoteException;

	/**
	 * Get the document items that are not in the template
	 * @param anITemplate - ITemplate
	 * @return DocumentSearchResultItem[] - the list of document items that are
	 *         not in the template
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is DAO
	 *         errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	DocumentSearchResultItem[] getDocumentItemList(ITemplate anITemplate) throws CheckListTemplateException,
			SearchDAOException, TemplateNotSetupException, RemoteException;

	/**
	 * Maker edit a rejected doc trx
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	IDocumentItemTrxValue makerEditRejectedDocItemTrx(ITrxContext anITrxContext,
			IDocumentItemTrxValue anIDocumentItemTrxValue, IDocumentItem anIDocumentItem)
			throws CheckListTemplateException, RemoteException;

	/**
	 * Maker update a doc item
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	IDocumentItemTrxValue makerUpdateDocItem(ITrxContext anITrxContext, IDocumentItemTrxValue anIDocumentItemTrxValue,
			IDocumentItem anIDocumentItem) throws CheckListTemplateException, RemoteException;
	
	/**
	 * Maker delete a doc item
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	IDocumentItemTrxValue makerDeleteDocItem(ITrxContext anITrxContext, IDocumentItemTrxValue anIDocumentItemTrxValue,
			IDocumentItem anIDocumentItem) throws CheckListTemplateException, RemoteException;
	
	/**
	 * Maker update a doc item
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	IDocumentItemTrxValue makerUpdateDraftCreateDocItem(ITrxContext anITrxContext, IDocumentItemTrxValue anIDocumentItemTrxValue,
			IDocumentItem anIDocumentItem) throws CheckListTemplateException, RemoteException;


	/**
	 * To get the list of laws and customer types with templates indication
	 * @param aCriteria - TemplateSearchCriteria
	 * @return LawSearchResultItem[] - the list of laws and customer types with
	 *         template indication
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is DAO
	 *         errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	LawSearchResultItem[] getLawCustomerTypes(TemplateSearchCriteria aCriteria) throws CheckListTemplateException,
			SearchDAOException, RemoteException;

	/**
	 * To get the list of laws and customer types with templates indication
	 * @param aCriteria - TemplateSearchCriteria
	 * @return CollateralSubTypeSearchResultItem[] - the list of collateral
	 *         subtype with template indication
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is DAO
	 *         errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	CollateralSubTypeSearchResultItem[] getCollateralSubType(TemplateSearchCriteria aCriteria)
			throws CheckListTemplateException, SearchDAOException, RemoteException;

	CollateralSubTypeSearchResultItem[] getFacilitySubType(TemplateSearchCriteria aCriteria)
	throws CheckListTemplateException, SearchDAOException, RemoteException;

	/**
	 * Get the country template based on the law, legal constitution and country
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @param aCountry - String
	 * @return ITemplate - the country template of the specified law, legal
	 *         constitution and country
	 * @throws com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException
	 *         if template not setup
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	ITemplate getDefaultCCTemplate(String aLaw, String aLegalConstitution, String aCountry)
			throws TemplateNotSetupException, CheckListTemplateException, RemoteException;

	/**
	 * Get the global security template based on the collateral type and sub
	 * type
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @param aCountry - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException
	 *         if template not setup
	 * @throws java.rmi.RemoteException on remote errors
	 */
	ITemplate getDefaultCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
			throws TemplateNotSetupException, CheckListTemplateException, RemoteException;

	/**
	 * Get a template by transaction ID
	 * @param aTrxID - String
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         object
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	ITemplateTrxValue getTemplateByTrxID(String aTrxID) throws CheckListTemplateException, RemoteException;

	/**
	 * To check if there is any pending create template based on the template
	 * type
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @return boolean - true if there exist and false otherwise
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on errors
	 */
	boolean pendingCreateCCTemplateAlreadyExist(String aLaw, String aLegalConstitution)
			throws CheckListTemplateException, RemoteException;

	/**
	 * To check if there is any pending create template based on the template
	 * type
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @param aCountry - String
	 * @return boolean - true if there exist and false otherwise
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on errors
	 */
	boolean pendingCreateCCTemplateAlreadyExist(String aLaw, String aLegalConstitution, String aCountry)
			throws CheckListTemplateException, RemoteException;

	/**
	 * To check if there is any pending create template based on the template
	 * type
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @return boolean - true if there exist and false otherwise
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	boolean pendingCreateCollateralTemplateAlreadyExist(String aCollateralType, String aCollateralSubType)
			throws CheckListTemplateException, RemoteException;

	/**
	 * To check if there is any pending create template based on the template
	 * type
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @param aCountry - String
	 * @return boolean - true if there exist and false otherwise
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	boolean pendingCreateCollateralTemplateAlreadyExist(String aCollateralType, String aCollateralSubType,
			String aCountry) throws CheckListTemplateException, RemoteException;

	/**
	 * Maker creation of a template checklist
	 * @param anITrxContext - ITrxContext
	 * @param anITemplate - ITemplate
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	ITemplateTrxValue makerCreateTemplate(ITrxContext anITrxContext, ITemplate anITemplate)
			throws CheckListTemplateException, RemoteException;

	/**
	 * Checker approves a template trx
	 * @param anITrxContext - ITrxContext
	 * @param anITemplateTrxValue - ITemplateTrxValue
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	ITemplateTrxValue checkerApproveTemplate(ITrxContext anITrxContext, ITemplateTrxValue anITemplateTrxValue)
			throws CheckListTemplateException, RemoteException;

	/**
	 * Checker rejects a template trx
	 * @param anITrxContext - ITrxContext
	 * @param anITemplateTrxValue - anITemplateTrxValue
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	ITemplateTrxValue checkerRejectTemplate(ITrxContext anITrxContext, ITemplateTrxValue anITemplateTrxValue)
			throws CheckListTemplateException, RemoteException;

	/**
	 * Maker closes a template trx that has been rejected by the checker
	 * @param anITrxContext - ITrxContext
	 * @param anITemplateTrxValue - ITemplateTrxValue
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	ITemplateTrxValue makerCloseTemplateTrx(ITrxContext anITrxContext, ITemplateTrxValue anITemplateTrxValue)
			throws CheckListTemplateException, RemoteException;

	/**
	 * Maker edits a rejected template
	 * @param anITrxContext - ITrxContext
	 * @param anITemplateTrxValue - ITemplateTrxValue
	 * @param anITemplate - ITemplate
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	ITemplateTrxValue makerEditRejectedTemplateTrx(ITrxContext anITrxContext, ITemplateTrxValue anITemplateTrxValue,
			ITemplate anITemplate) throws CheckListTemplateException, RemoteException;

	/**
	 * Maker updates a template
	 * @param anITrxContext - ITrxContext
	 * @param anITemplateTrxValue - ITemplateTrxValue
	 * @param anITemplate - ITemplate
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	ITemplateTrxValue makerUpdateTemplate(ITrxContext anITrxContext, ITemplateTrxValue anITemplateTrxValue,
			ITemplate anITemplate) throws CheckListTemplateException, RemoteException;

	public ITemplateItem[] searchTemplateItemList(TemplateItemSearchCriteria aCriteria) throws SearchDAOException,
			CheckListTemplateException, RemoteException;

	/**
	 * Gets the law for the given country
	 * @param aCountry - Country
	 * @return law of the country
	 * @throws CheckListTemplateException
	 */
	public abstract String getLaw(String aCountry) throws CheckListTemplateException, RemoteException;

    public abstract String[] getLaw(String aCountry, boolean allFlag) throws CheckListTemplateException, RemoteException;

	public int getNoOfDocItemByDesc(String aCategory, String aDocItemDescription) throws SearchDAOException,
			CheckListTemplateException, RemoteException;

	/**
	 * Get the master security template based on the collateral type and sub
	 * type
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @param aCountry - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws CheckListTemplateException on errors
	 */
	public abstract ITemplate getCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
			throws CheckListTemplateException, RemoteException;
	
	
	
	/**
	 * Get the master security template based on the collateral type and sub
	 * type
	 * @param aPariPassuType - String
	 * @param aPariPassuSubType - String
	 * @param aCountry - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws CheckListTemplateException on errors
	 */
	public abstract ITemplate getPariPassuTemplate(String aPariPassuType, String aPariPassuSubType, String aCountry)
			throws CheckListTemplateException, RemoteException;
	public abstract ITemplate getCAMTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
	throws CheckListTemplateException, RemoteException;
	
	
	public abstract ITemplate getFacilityTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
	throws CheckListTemplateException, RemoteException;

    public abstract ITemplate getCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry,
            String applicationType, String goodsStatus, String pbrInd, String preApproveDocFlag)
            throws CheckListTemplateException, RemoteException;

	/**
	 * Get the country template based on the law, legal constitution and country
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @param aCountry - String
	 * @return ITemplate - the country template of the specified law, legal
	 *         constitution and country
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public ITemplate getCCTemplate(String aLaw, String aLegalConstitution, String aCountry)
			throws CheckListTemplateException, RemoteException;

    /**
     * Used by SI search (LOS) - for method details refer to
     * getCCTemplate(String aLaw, String aLegalConstitution, String aCountry)
     * The differences between the 2 methods is such that this will return
     *
     * Borrower Type:
     * ===============
     * 1. Only docs applicable for borrower only when "borrowerType" = ICMSConstant.CHECKLIST_MAIN_BORROWER
     * 2. Only docs applicable for pledgor only when "borrowerType" = ICMSConstant.CHECKLIST_PLEDGER
     * 3. All docs when "borrowerType" = ALL (currently this is not in use)
     *
     * Pre-Approval Flag:
     * ==================
     * 1. Only Pre-Approval Doc when param "preApprovalDocFlag" is 'P' [PreApprove]
     * 2. Only Non Pre-Approval Doc when param "preApprovalDocFlag" is 'N' [Non-PreApprove]
     * 3. All docs when param "preApprovalDocFlag" is 'A' [All] (currently this is not in use)
     * If for CMS usage, the flag "preApprovalDocFlag" should always be 'A'
     *
     * @param aLaw - String
     * @param aLegalConstitution - String
     * @param aCountry - String
     * @param borrowerType - String
     * @param preApprovalDocFlag - 'P' to return only pre-approve documents only,
     *                             'N' to return non-pre-approve documents only,
     *                             'A' to return all documents (pre-approve + non-pre-approve)
     * @return ITemplate - the country template of the specified law, legal constitution and country
     * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException on errors
     */
    public ITemplate getCCTemplate(String aLaw, String aLegalConstitution, String aCountry, String applicationType, 
    			String borrowerType, String preApprovalDocFlag) throws CheckListTemplateException, RemoteException;


    /**
	 * Check that the document code entered is unique. Business Rule: Even
	 * deleted document code cannot be reused.
	 * 
	 * @param docCode - document code to be checked for uniqueness
	 * @param category - category of document to check for. Takes one of these 3
	 *        values: 1. ICMSConstant.DOC_TYPE_CC - for CC 2.
	 *        ICMSConstant.DOC_TYPE_SECURITY - for Security 3. null - will not
	 *        check against specific category (unique for both category)
	 * 
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is errors
	 *         in DAO
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public boolean isDocumentCodeUnique(String docCode, String category) throws SearchDAOException,
			CheckListTemplateException, RemoteException;

	/**
	 * Retrieves the set of dynamic properties for a given security subtype
	 * @param securitySubtype - security subtype to retrieve the dynamic
	 *        properties for
	 * @return set of dynamic properties of IDynamicPropertySetup for the given
	 *         subtype
	 * @throws SearchDAOException if errors during retrieval
	 */
	public IDynamicPropertySetup[] getDynamicPropertySetup(String securitySubtype) throws SearchDAOException,
			CheckListTemplateException, RemoteException;

	/**
	 * Retrieves the transaction subtype by transaction id. Used for action
	 * redirection
	 * 
	 * @param transactionID - transaction id
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is errors
	 *         in DAO
	 * @throws java.rmi.RemoteException on remote errors
	 * @return transaction sub type of the given transaction id
	 */
	public String getTrxSubTypeByTrxID(long transactionID) throws SearchDAOException, CheckListTemplateException,
			RemoteException;

}
