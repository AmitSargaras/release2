package com.integrosys.cms.app.chktemplate.proxy;

import java.util.List;

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

public interface ICheckListTemplateProxyManager {
	/**
	 * Get the document item by criteria
	 * @param aCriteria of DocumentSearchCriteria type
	 * @return SearchResult - the search result containing the list of document
	 *         items that satisfy the criteria
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is DAO
	 *         errors
	 */
	SearchResult getDocumentItemList(DocumentSearchCriteria aCriteria) throws CheckListTemplateException,
			SearchDAOException;
	
	SearchResult getFilteredDocumentItemList(DocumentSearchCriteria aCriteria,List docCrit) throws CheckListTemplateException,
	SearchDAOException;
	
	SearchResult getCollateralList() throws Exception;
	
	SearchResult getFacilityList() throws Exception;
	
	SearchResult getCollateralIdList(String secSubType) throws Exception;
	

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
			SearchDAOException;

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
	 */

	public IItem[] getParentItemList(String checklistCategory, long templateID, String goodStatus, String pbrInd) throws CheckListTemplateException,
			SearchDAOException;

	/**
	 * Get a document item by transaction ID
	 * @param aTrxID of String type
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	IDocumentItemTrxValue getDocumentItemByTrxID(String aTrxID) throws CheckListTemplateException;

	/**
	 * Maker creation of a doc item checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anIDocumentItem of IDocumentItem type
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	IDocumentItemTrxValue makerCreateDocItem(ITrxContext anITrxContext, IDocumentItem anIDocumentItem)
			throws CheckListTemplateException;
	
	/**
	 * Maker Draft of a doc item checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anIDocumentItem of IDocumentItem type
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	IDocumentItemTrxValue makerDraftDocItem(ITrxContext anITrxContext, IDocumentItem anIDocumentItem)
			throws CheckListTemplateException;


	/**
	 * Checker approves the creation of a doc item checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anIDocumentItemTrxValue of IDocumentItemTrxValue type
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	IDocumentItemTrxValue checkerApproveDocItem(ITrxContext anITrxContext, IDocumentItemTrxValue anIDocumentItemTrxValue)
			throws CheckListTemplateException;

	/**
	 * Checker rejects a doc item trx
	 * @param anITrxContext of ITrxContext type
	 * @param anIDocumentItemTrxValue of IDocumentItemTrxValue type
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	IDocumentItemTrxValue checkerRejectDocItem(ITrxContext anITrxContext, IDocumentItemTrxValue anIDocumentItemTrxValue)
			throws CheckListTemplateException;

	/**
	 * Maker closes a doc item trx that has been rejected by the checker
	 * @param anITrxContext of ITrxContext type
	 * @param anIDocumentItemTrxValue of IDocumentItemTrxValue type
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	IDocumentItemTrxValue makerCloseDocItemTrx(ITrxContext anITrxContext, IDocumentItemTrxValue anIDocumentItemTrxValue)
			throws CheckListTemplateException;

	/**
	 * Maker edits a rejected doc trx
	 * @param anITrxContext of ITrxContext type
	 * @param anIDocumentItemTrxValue of IDocumentItemTrxValue type
	 * @param anIDocumentItem of IDocumentItem type
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	IDocumentItemTrxValue makerEditRejectedDocItemTrx(ITrxContext anITrxContext,
			IDocumentItemTrxValue anIDocumentItemTrxValue, IDocumentItem anIDocumentItem)
			throws CheckListTemplateException;

	/**
	 * Maker updates a doc item
	 * @param anITrxContext of ITrxContext type
	 * @param anIDocumentItemTrxValue of IDocumentItemTrxValue type
	 * @param anIDocumentItem of IDocumentItem type
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	IDocumentItemTrxValue makerUpdateDocItem(ITrxContext anITrxContext, IDocumentItemTrxValue anIDocumentItemTrxValue,
			IDocumentItem anIDocumentItem) throws CheckListTemplateException;
	
	
	/**
	 * Maker delete a doc item
	 * @param anITrxContext of ITrxContext type
	 * @param anIDocumentItemTrxValue of IDocumentItemTrxValue type
	 * @param anIDocumentItem of IDocumentItem type
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	IDocumentItemTrxValue makerDeleteDocItem(ITrxContext anITrxContext, IDocumentItemTrxValue anIDocumentItemTrxValue,
			IDocumentItem anIDocumentItem) throws CheckListTemplateException;
	

	/**
	 * Maker updates draft a doc item
	 * @param anITrxContext of ITrxContext type
	 * @param anIDocumentItemTrxValue of IDocumentItemTrxValue type
	 * @param anIDocumentItem of IDocumentItem type
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	IDocumentItemTrxValue makerUpdateDraftCreateDocItem(ITrxContext anITrxContext, IDocumentItemTrxValue anIDocumentItemTrxValue,
			IDocumentItem anIDocumentItem) throws CheckListTemplateException;


	/**
	 * Get the document items that are not in the template
	 * @param anITemplate of ITemplate type
	 * @return DocumentSearchResultItem[] - the list of document items that are
	 *         not in the template
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is DAO
	 *         errors
	 */
	DocumentSearchResultItem[] getDocumentItemList(ITemplate anITemplate) throws CheckListTemplateException,
			SearchDAOException, TemplateNotSetupException;

	/**
	 * To get the list of laws and customer types with templates indication
	 * @param aCriteria of TemplateSearchCriteria type
	 * @return LawSearchResultItem[] - the list of laws and customer types with
	 *         template indication
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is DAO
	 *         errors
	 */
	LawSearchResultItem[] getLawCustomerTypes(TemplateSearchCriteria aCriteria) throws CheckListTemplateException,
			SearchDAOException;

	/**
	 * To get the list of collateral with templates indication
	 * @param aCriteria of TemplateSearchCriteria type
	 * @return CollateralSubTypeSearchResultItem[] - the list of collateral
	 *         subtype with template indication
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is DAO
	 *         errors
	 */
	CollateralSubTypeSearchResultItem[] getCollateralSubType(TemplateSearchCriteria aCriteria)
			throws CheckListTemplateException, SearchDAOException;

	
	CollateralSubTypeSearchResultItem[] getFacilitySubType(TemplateSearchCriteria aCriteria)
	throws CheckListTemplateException, SearchDAOException;

	/**
	 * Get the country template based on the law, legal constitution and country
	 * @param aLaw of String type
	 * @param aLegalConstitution of String type
	 * @param aCountry of String type
	 * @return ITemplate - the country template of the specified law, legal
	 *         constitution and country
	 * @throws com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException
	 *         if no template being setup
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	ITemplate getDefaultCCTemplate(String aLaw, String aLegalConstitution, String aCountry)
			throws TemplateNotSetupException, CheckListTemplateException;

	/**
	 * Get the master collateral template based on the collateral type, sub type
	 * and country
	 * @param aCollateralType of String type
	 * @param aCollateralSubType of String type
	 * @param aCountry of String type
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException
	 *         if no template being setup
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	ITemplate getDefaultCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
			throws TemplateNotSetupException, CheckListTemplateException;

	/**
	 * Get a template by transaction ID
	 * @param aTrxID of String type
	 * @return IDocumentItemTrxValue - the interface representing the template
	 *         trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	ITemplateTrxValue getTemplateByTrxID(String aTrxID) throws CheckListTemplateException;

	/**
	 * To check if there is any pending create template based on the template
	 * type
	 * @param aLaw of String type
	 * @param aLegalConstitution of String type
	 * @return boolean - true if there exist and false otherwise
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	boolean pendingCreateCCTemplateAlreadyExist(String aLaw, String aLegalConstitution)
			throws CheckListTemplateException;

	/**
	 * To check if there is any pending create template based on the template
	 * type
	 * @param aLaw of String type
	 * @param aLegalConstitution of String type
	 * @param aCountry of String type
	 * @return boolean - true if there exist and false otherwise
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	boolean pendingCreateCCTemplateAlreadyExist(String aLaw, String aLegalConstitution, String aCountry)
			throws CheckListTemplateException;

	/**
	 * To check if there is any pending create template based on the template
	 * type
	 * @param aCollateralType of String type
	 * @param aCollateralSubType of String type
	 * @return boolean - true if there exist and false otherwise
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	boolean pendingCreateCollateralTemplateAlreadyExist(String aCollateralType, String aCollateralSubType)
			throws CheckListTemplateException;

	/**
	 * To check if there is any pending create template based on the template
	 * type
	 * @param aCollateralType of String type
	 * @param aCollateralSubType of String type
	 * @param aCountry of String type
	 * @return boolean - true if there exist and false otherwise
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	boolean pendingCreateCollateralTemplateAlreadyExist(String aCollateralType, String aCollateralSubType,
			String aCountry) throws CheckListTemplateException;

	/**
	 * Maker creation of a template checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anITemplate of ITemplate type
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	ITemplateTrxValue makerCreateTemplate(ITrxContext anITrxContext, ITemplate anITemplate)
			throws CheckListTemplateException;

	/**
	 * Checker approves a template trx
	 * @param anITrxContext of ITrxContext type
	 * @param anITemplateTrxValue of ITemplateTrxValue type
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	ITemplateTrxValue checkerApproveTemplate(ITrxContext anITrxContext, ITemplateTrxValue anITemplateTrxValue)
			throws CheckListTemplateException;

	/**
	 * Checker rejects a template trx
	 * @param anITrxContext of ITrxContext type
	 * @param anITemplateTrxValue of ITemplateTrxValue type
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	ITemplateTrxValue checkerRejectTemplate(ITrxContext anITrxContext, ITemplateTrxValue anITemplateTrxValue)
			throws CheckListTemplateException;

	/**
	 * Maker closes a template trx that has been rejected by the checker
	 * @param anITrxContext of ITrxContext type
	 * @param anITemplateTrxValue of ITemplateTrxValue type
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	ITemplateTrxValue makerCloseTemplateTrx(ITrxContext anITrxContext, ITemplateTrxValue anITemplateTrxValue)
			throws CheckListTemplateException;

	/**
	 * Maker edits a rejected template
	 * @param anITrxContext of ITrxContext type
	 * @param anITemplateTrxValue of ITemplateTrxValue type
	 * @param anITemplate of ITemplate type
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	ITemplateTrxValue makerEditRejectedTemplateTrx(ITrxContext anITrxContext, ITemplateTrxValue anITemplateTrxValue,
			ITemplate anITemplate) throws CheckListTemplateException;

	/**
	 * Maker updates a template
	 * @param anITrxContext of ITrxContext type
	 * @param anITemplateTrxValue of ITemplateTrxValue type
	 * @param anITemplate of ITemplate type
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	ITemplateTrxValue makerUpdateTemplate(ITrxContext anITrxContext, ITemplateTrxValue anITemplateTrxValue,
			ITemplate anITemplate) throws CheckListTemplateException;

	public ITemplateItem[] searchTemplateItemList(TemplateItemSearchCriteria aCriteria) throws SearchDAOException,
			CheckListTemplateException;

	/**
	 * Gets the law for the given country. Return the first law only if there is
	 * more than 1. To retrieve all, use getLaw(String, boolean).
	 * @param aCountry - Country
	 * @return law of the country
	 * @throws CheckListTemplateException
	 */
	public String getLaw(String aCountry) throws CheckListTemplateException;

	/**
	 * Gets the laws for the given country. The law(s) in this case refer to the
	 * application law type, not the country governing law. As such, more than 1
	 * law is possible. Method will return all the laws, to retrieve only 1
	 * (original) use getLaw(String)
	 * @param aCountry - Country
	 * @return law of the country
	 * @throws CheckListTemplateException
	 */
	public String[] getLaw(String aCountry, boolean allFlag) throws CheckListTemplateException;

	public int getNoOfDocItemByDesc(String aCategory, String aDocItemDescription) throws SearchDAOException,
			CheckListTemplateException;

	/**
	 * Get the country security template based on the collateral type and sub
	 * type and country
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @param aCountry - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws CheckListTemplateException on errors
	 */
	public abstract ITemplate getCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
			throws CheckListTemplateException;
	

	/**
	 * Get the country security template based on the collateral type and sub
	 * type and country
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @param aCountry - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws CheckListTemplateException on errors
	 */
	public abstract ITemplate getPariPassuTemplate(String aPariPassuType, String aPariPassuSubType, String aCountry)
			throws CheckListTemplateException;
	
	/**
	 * Get the country security template based on the collateral type and sub
	 * type and country
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @param aCountry - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws CheckListTemplateException on errors
	 */
	public abstract ITemplate getCAMTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
			throws CheckListTemplateException;
	
	
	public abstract ITemplate getFacilityTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
	throws CheckListTemplateException;

	/**
	 * Used by SI search Get the country security template based on the
	 * collateral type and sub type and country, filter based on PBT/PBR
	 * Indicator and Goods Status and Application Type. Conditions is
	 * ApplicationType && (PBT/PBR_Indicator || Goods Status)
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @param aCountry - String
	 * @param applicationType - Application Type
	 * @param goodsStatus - Goods Status
	 * @param pbrInd - PBT/PBR Indicator
	 * @Param preApproveDocFlag - 'P' for PreApproval Doc Only, 'N' for
	 *        Non-PreApproval Doc Only, 'A' for all docs
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws CheckListTemplateException on errors
	 */
	public abstract ITemplate getCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry,
			String applicationType, String goodsStatus, String pbrInd, String preApproveDocFlag)
			throws CheckListTemplateException;

	/**
	 * Get the country template based on the law, legal constitution and country
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @param aCountry - String
	 * @return ITemplate - the country template of the specified law, legal
	 *         constitution and country
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplate getCCTemplate(String aLaw, String aLegalConstitution, String aCountry)
			throws CheckListTemplateException;

	/**
	 * <p>
	 * Used by SI search (LOS) - for method details refer to
	 * getCCTemplate(String aLaw, String aLegalConstitution, String aCountry)
	 * The differences between the 2 methods is such that this will return
	 *<p>
	 * Borrower Type:<br/>
	 * ===============
	 * <ol>
	 * <li>Only docs applicable for borrower only when "borrowerType" =
	 * ICMSConstant.CHECKLIST_MAIN_BORROWER
	 * <li>Only docs applicable for pledgor only when "borrowerType" =
	 * ICMSConstant.CHECKLIST_PLEDGER
	 * <li>All docs when "borrowerType" = ALL (currently this is not in use)
	 * </ol>
	 *<p>
	 * Pre-Approval Flag:<br/>
	 * ==================
	 * <ol>
	 * <li>Only Pre-Approval Doc when param "preApprovalDocFlag" is 'P'
	 * [PreApprove]
	 * <li>Only Non Pre-Approval Doc when param "preApprovalDocFlag" is 'N'
	 * [Non-PreApprove]
	 * <li>All docs when param "preApprovalDocFlag" is 'A' [All] (currently this
	 * is not in use) If for CMS usage, the flag "preApprovalDocFlag" should
	 * always be 'A'
	 * </ol>
	 * 
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @param aCountry - String
	 * @param borrowerType - String
	 * @param preApprovalDocFlag - 'P' to return only pre-approve documents
	 *        only, 'N' to return non-pre-approve documents only, 'A' to return
	 *        all documents (pre-approve + non-pre-approve)
	 * @return ITemplate - the country template of the specified law, legal
	 *         constitution and country
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplate getCCTemplate(String aLaw, String aLegalConstitution, String aCountry, String applicationType,
			String borrowerType, String preApprovalDocFlag) throws CheckListTemplateException;

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
	 * 
	 * @return true if code is unique; false otherwise
	 */
	public boolean isDocumentCodeUnique(String docCode, String category) throws SearchDAOException,
			CheckListTemplateException;

	/**
	 * Retrieves the set of dynamic properties for a given security subtype
	 * @param securitySubtype - security subtype to retrieve the dynamic
	 *        properties for
	 * @return set of dynamic properties of IDynamicPropertySetup for the given
	 *         subtype
	 * @throws SearchDAOException if errors during retrieval
	 */
	public abstract IDynamicPropertySetup[] getDynamicPropertySetup(String securitySubtype) throws SearchDAOException,
			CheckListTemplateException;

	/**
	 * Retrieves the transaction subtype by transaction id. Used for action
	 * redirection
	 * 
	 * @param transactionID - transaction id
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is errors
	 *         in DAO
	 * @return transaction sub type of the given transaction id
	 */
	public String getTrxSubTypeByTrxID(long transactionID) throws SearchDAOException, CheckListTemplateException;

}
