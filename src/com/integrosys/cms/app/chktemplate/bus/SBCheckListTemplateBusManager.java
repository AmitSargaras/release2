package com.integrosys.cms.app.chktemplate.bus;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJBObject;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Created by IntelliJ IDEA. User: user Date: Jul 16, 2008 Time: 6:33:58 PM To
 * change this template use File | Settings | File Templates.
 */
public interface SBCheckListTemplateBusManager extends EJBObject {
	/**
	 * Get the list of document items
	 * @param aCriteria - DocumentSearchCriteria
	 * @return SearchResult - the result containing the list of document items
	 *         that satisfy the criteria
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is error
	 *         at the DAO
	 * @throws TemplateNotSetupException if the template required is not setup
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
	 */                                                 
    public IItem[] getParentItemList(String checklistCategory, long templateID, String goodsStatus, String pbrInd)
            throws CheckListTemplateException, SearchDAOException, RemoteException;

	/**
	 * Create a document item
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItem - the document item being updated
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	IDocumentItem create(IDocumentItem anIDocumentItem) throws CheckListTemplateException, RemoteException;

	/**
	 * Update a document item
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItem - the document item being updated
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 *         is enctr concurrent update
	 * @throws java.rmi.RemoteException on remote errors
	 */
	IDocumentItem update(IDocumentItem anIDocumentItem) throws CheckListTemplateException, ConcurrentUpdateException,
			RemoteException;

	/**
	 * Get a document item by ID
	 * @param aDocumentItemID - String
	 * @return IDocument - the document
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	IDocumentItem getDocumentItemByID(long aDocumentItemID) throws CheckListTemplateException, RemoteException;

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
	 * Get the number of doc item under the same category and having the same
	 * description
	 * @param aCategory of String type
	 * @param aDescription of String type
	 * @return int - the number of doc items
	 * @throws com.integrosys.base.businfra.search.SearchDAOException ,
	 *         RemoteException, CheckListTemplateException
	 */
	int getNoOfDocItemByDesc(String aCategory, String aDescription) throws CheckListTemplateException,
			SearchDAOException, RemoteException;

	/**
	 * Get the list of laws and customer types
	 * @param lawList of String type
	 * @return LawSearchResultItem[] - the list of law and customer types
	 * @throws com.integrosys.base.businfra.search.SearchDAOException on DAO
	 *         errors
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	public LawSearchResultItem[] getLawCustomerTypes(String[] lawList) throws SearchDAOException,
			CheckListTemplateException, RemoteException;

	/**
	 * Get the law based on the country
	 * @param aCountry of String type
	 * @return String - the law for that country
	 * @throws CheckListTemplateException on errors
	 * @throws RemoteException on remote errors
	 */
	public String getLaw(String aCountry) throws CheckListTemplateException, RemoteException;

	/**
	 * Gets the laws for the given country. The law(s) in this case refer to the
	 * application law type, not the country governing law. As such, more than 1
	 * law is possible. Method will return all the laws, to retrieve only 1
	 * (original) use getLaw(String)
	 * @param aCountry - Country
	 * @return law of the country
	 * @throws CheckListTemplateException
	 */
	public String[] getLaw(String aCountry, boolean allFlag) throws CheckListTemplateException, RemoteException;

	/**
	 * To get the list of templates based on the criteria specified
	 * @param aCriteria - TemplateSearchCriteria
	 * @return SearchResult - contain the list fo templates that satisfy the
	 *         criteria
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is errors
	 *         in DAO
	 * @throws java.rmi.RemoteException on remote errors
	 */
	SearchResult searchTemplateList(TemplateSearchCriteria aCriteria) throws SearchDAOException,
			CheckListTemplateException, RemoteException;

	/**
	 * To get the list of template items based on the criteria specified
	 * @param aCriteria - TemplateSearchCriteria
	 * @return ITemplateItem[] - contain the list fo templates that satisfy the
	 *         criteria
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is errors
	 *         in DAO
	 * @throws java.rmi.RemoteException on remote errors
	 */
	ITemplateItem[] searchTemplateItemList(TemplateItemSearchCriteria aCriteria) throws SearchDAOException,
			CheckListTemplateException, RemoteException;

	/**
	 * Get the master template based on the law and legal constitution
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @return ITemplate - the master template of the specified law and legal
	 *         constitution
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	ITemplate getCCTemplate(String aLaw, String aLegalConstitution) throws CheckListTemplateException, RemoteException;

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
	ITemplate getCCTemplate(String aLaw, String aLegalConstitution, String aCountry) throws CheckListTemplateException,
			RemoteException;

	/**
	 * Used by SI search (LOS) - for method details refer to
	 * getCCTemplate(String aLaw, String aLegalConstitution, String aCountry)
	 * The differences between the 2 methods is such that this will return
	 * 
	 * Borrower Type: =============== 1. Only docs applicable for borrower only
	 * when "borrowerType" = ICMSConstant.CHECKLIST_MAIN_BORROWER 2. Only docs
	 * applicable for pledgor only when "borrowerType" =
	 * ICMSConstant.CHECKLIST_PLEDGER 3. All docs when "borrowerType" = ALL
	 * (currently this is not in use)
	 * 
	 * Pre-Approval Flag: ================== 1. Only Pre-Approval Doc when param
	 * "preApprovalDocFlag" is 'P' [PreApprove] 2. Only Non Pre-Approval Doc
	 * when param "preApprovalDocFlag" is 'N' [Non-PreApprove] 3. All docs when
	 * param "preApprovalDocFlag" is 'A' [All] (currently this is not in use) If
	 * for CMS usage, the flag "preApprovalDocFlag" should always be 'A'
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
			String borrowerType, String preApprovalDocFlag) throws CheckListTemplateException, RemoteException;

	/**
	 * Get the global security template based on the collateral type and sub
	 * type
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	ITemplate getCollateralTemplate(String aCollateralType, String aCollateralSubType)
			throws CheckListTemplateException, RemoteException;

	/**
	 * Get the master security template based on the collateral type and sub
	 * type
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @param aCountry - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	ITemplate getCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
			throws CheckListTemplateException, RemoteException;
	
	
	ITemplate getCAMTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
	throws CheckListTemplateException, RemoteException;
	
	
	ITemplate getFacilityTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
	throws CheckListTemplateException, RemoteException;


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
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws CheckListTemplateException on errors
	 */
	public ITemplate getCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry,
			String applicationType, String goodsStatus, String pbrInd, String preApproveDocFlag)
			throws CheckListTemplateException, RemoteException;

	/**
	 * Create a template
	 * @param anITemplate - ITemplate
	 * @return ICheckList - the template being created
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         ;
	 * @throws java.rmi.RemoteException
	 */
	ITemplate create(ITemplate anITemplate) throws CheckListTemplateException, RemoteException;

	/**
	 * Update a checklist
	 * @param anITemplate - ITemplate
	 * @return ITemplate - the template being updated
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 * @throws java.rmi.RemoteException
	 */
	ITemplate update(ITemplate anITemplate) throws ConcurrentUpdateException, CheckListTemplateException,
			RemoteException;

	/**
	 * Get a template based on the value in the template type
	 * @param anITemplateType - ITemplateType
	 * @return ICheckList - the checklist
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 * @throws java.rmi.RemoteException
	 */
	ITemplate getTemplateList(ITemplateType anITemplateType) throws CheckListTemplateException, RemoteException;

	/**
	 * Get a template based on the template ID
	 * @param aTemplateID - long
	 * @return ITemplate - the biz object containing the template info
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 * @throws java.rmi.RemoteException
	 */
	ITemplate getTemplateByID(long aTemplateID) throws CheckListTemplateException, RemoteException;

	/**
	 * Get the country template based on the law, legal constitution and country
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @param aCountry - String
	 * @return ITemplate - the country template of the specified law, legal
	 *         constitution and country
	 * @throws TemplateNotSetupException if template not setup
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
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws java.rmi.RemoteException on remote errors
	 */
	ITemplate getDefaultCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
			throws TemplateNotSetupException, CheckListTemplateException, RemoteException;

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
	 * @throws RemoteException on remote errors
	 * 
	 * @return true if code is unique; false otherwise
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
	 * Get the number of doc item under the same category and having the same
	 * doc code
	 * @param aitemCode of String type
	 * @return Collection - the number of doc items
	 * @throws com.integrosys.base.businfra.search.SearchDAOException ,
	 *         CheckListTemplateException
	 * @throws FinderException 
	 */
	public Collection getDocumentItemByItemCode(String aItemCode) throws SearchDAOException,
			CheckListTemplateException, FinderException , RemoteException;
		
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
	public String getTrxSubTypeByTrxID(long transactionID) throws SearchDAOException, CheckListTemplateException,
			RemoteException;

}
