package com.integrosys.cms.app.chktemplate.proxy;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.chktemplate.bus.IDynamicPropertySetup;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.bus.LawSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.SBCheckListTemplateBusManager;
import com.integrosys.cms.app.chktemplate.bus.SBCheckListTemplateBusManagerHome;
import com.integrosys.cms.app.chktemplate.bus.TemplateItemSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException;
import com.integrosys.cms.app.chktemplate.bus.TemplateSearchCriteria;
import com.integrosys.cms.app.chktemplate.trx.IDocumentItemTrxValue;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterDAOFactory;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Created by IntelliJ IDEA. User: user Date: Jul 16, 2008 Time: 6:20:39 PM To
 * change this template use File | Settings | File Templates.
 */
public class SBCheckListTemplateProxyManagerBean extends AbstractCheckListTemplateProxyManager implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBCheckListTemplateProxyManagerBean() {
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
	 * Get the list of document items
	 * @param aCriteria - DocumentSearchCriteria
	 * @return SearchResult - the result containing the list of document items
	 *         that satisfy the criteria
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is error
	 *         at the DAO
	 * @throws java.rmi.RemoteException on errors
	 */
	public SearchResult getDocumentItemList(DocumentSearchCriteria aCriteria) throws SearchDAOException,
			CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getDocumentItemList(aCriteria);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getDocumentItemList: ", ex);
		}
	}
	
	

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
			SearchDAOException {
		try {
			return getSBCheckListTemplateBusManager().getParentItemList(checklistCategory, templateID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getParentItemList: ", ex);
		}
	}

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
			SearchDAOException {
		try {
			return getSBCheckListTemplateBusManager().getParentItemList(checklistCategory, templateID, goodStatus, pbrInd);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getParentItemList: ", ex);
		}catch(CheckListTemplateException ex){
            throw ex;
        }catch(SearchDAOException ex){
            throw ex;
        }
	}

	/**
	 * Get the country template based on the law, legal constitution and country
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @param aCountry - String
	 * @return ITemplate - the country template of the specified law, legal
	 *         constitution and country
	 * @throws com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException
	 *         if no template is being setup
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplate getDefaultCCTemplate(String aLaw, String aLegalConstitution, String aCountry)
			throws TemplateNotSetupException, CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getDefaultCCTemplate(aLaw, aLegalConstitution, aCountry);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getCCTemplate: ", ex);
		}
	}

	/**
	 * Get the master security template based on the collateral type and sub
	 * type
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @param aCountry - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException
	 *         if template is not setup
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplate getDefaultCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
			throws TemplateNotSetupException, CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getDefaultCollateralTemplate(aCollateralType, aCollateralSubType,
					aCountry);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getCollateralTemplate: ", ex);
		}
	}

	/**
	 * Get the master template based on the law and legal constitution
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @return ITemplate - the master template of the specified law and legal
	 *         constitution
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	protected ITemplate getCCTemplate(String aLaw, String aLegalConstitution) throws CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getCCTemplate(aLaw, aLegalConstitution);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getCCTemplate: ", ex);
		}
	}

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
			throws CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getCCTemplate(aLaw, aLegalConstitution, aCountry);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getCCTemplate: ", ex);
		}
	}

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
			String borrowerType, String preApprovalDocFlag) throws CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getCCTemplate(aLaw, aLegalConstitution, aCountry, applicationType, borrowerType,
					preApprovalDocFlag);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getCCTemplate: ", ex);
		}
	}

	/**
	 * Get the global security template based on the collateral type and sub
	 * type
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws CheckListTemplateException on errors
	 */
	protected ITemplate getCollateralTemplate(String aCollateralType, String aCollateralSubType)
			throws CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getCollateralTemplate(aCollateralType, aCollateralSubType);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getCollateralTemplate: ", ex);
		}
	}

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
	public ITemplate getCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
			throws CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getCollateralTemplate(aCollateralType, aCollateralSubType,
					aCountry);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getCollateralTemplate: ", ex);
		}
	}

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
	public ITemplate getCAMTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
			throws CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getCAMTemplate(aCollateralType, aCollateralSubType,
					aCountry);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getCollateralTemplate: ", ex);
		}
	}
	
	public ITemplate getFacilityTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
	throws CheckListTemplateException {
try {
	return getSBCheckListTemplateBusManager().getFacilityTemplate(aCollateralType, aCollateralSubType,
			aCountry);
}
catch (RemoteException ex) {
	_context.setRollbackOnly();
	throw new CheckListTemplateException("RemoteException enctr at getCollateralTemplate: ", ex);
}
}

	public ITemplate getCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry,
			String applicationType, String goodsStatus, String pbrInd, String preApproveDocFlag)
			throws CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getCollateralTemplate(aCollateralType, aCollateralSubType,
					aCountry, applicationType, goodsStatus, pbrInd, preApproveDocFlag);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getCollateralTemplate: ", ex);
		}
	}

	/**
	 * Get the document items that are not in the template
	 * @param anITemplate - ITemplate
	 * @return DocumentSearchResultItem[] - the list of document items that are
	 *         not in the template
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is DAO
	 *         errors
	 */
	public DocumentSearchResultItem[] getDocumentItemList(ITemplate anITemplate) throws CheckListTemplateException,
			SearchDAOException, TemplateNotSetupException {
		try {
			return getSBCheckListTemplateBusManager().getDocumentItemList(anITemplate);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getDocumentItemList: ", ex);
		}
	}

	/**
	 * Call the bus session bean to get the list of law and customer types
	 * @param lawList of String type
	 * @return LawSearchResultItem[] - the list of las and customer types
	 * @throws com.integrosys.base.businfra.search.SearchDAOException if errors
	 *         at DAO
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	protected LawSearchResultItem[] getLawCustomerTypes(String[] lawList) throws SearchDAOException,
			CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getLawCustomerTypes(lawList);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getLawCustomerTypes: ", ex);
		}
	}

	/**
	 * Search for a list of templates based on the criteria
	 * @param aCriteria - TemplateSearchCriteria
	 * @return SearchResult - the result from the search that contains the list
	 *         of templates satisfying the criteria
	 * @throws com.integrosys.base.businfra.search.SearchDAOException on DAO
	 *         errors
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	protected SearchResult searchTemplateList(TemplateSearchCriteria aCriteria) throws SearchDAOException,
			CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().searchTemplateList(aCriteria);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at searchTemplateList: ", ex);
		}
	}

	public ITemplateItem[] searchTemplateItemList(TemplateItemSearchCriteria aCriteria) throws SearchDAOException,
			CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().searchTemplateItemList(aCriteria);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at searchTemplateItemList: ", ex);
		}
	}

	public String getLaw(String aCountry) throws CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getLaw(aCountry);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getLaw: ", ex);
		}
	}

	public String[] getLaw(String aCountry, boolean allFlag) throws CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getLaw(aCountry, allFlag);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getLaw: ", ex);
		}
	}

	public int getNoOfDocItemByDesc(String aCategory, String aDocItemDescription) throws SearchDAOException,
			CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getNoOfDocItemByDesc(aCategory, aDocItemDescription);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getNoOfDocItemByDesc: ", ex);
		}
	}

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
	 * @return true if code is unique; false otherwise
	 */
	public boolean isDocumentCodeUnique(String docCode, String category) throws SearchDAOException,
			CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().isDocumentCodeUnique(docCode, category);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at isDocumentCodeUnique: ", ex);
		}
	}

	/**
	 * Retrieves the set of dynamic properties for a given security subtype
	 * @param securitySubtype - security subtype to retrieve the dynamic
	 *        properties for
	 * @return set of dynamic properties of IDynamicPropertySetup for the given
	 *         subtype
	 * @throws SearchDAOException if errors during retrieval
	 */
	public IDynamicPropertySetup[] getDynamicPropertySetup(String securitySubtype) throws SearchDAOException,
			CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getDynamicPropertySetup(securitySubtype);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at isDocumentCodeUnique: ", ex);
		}
	}

	public String getTrxSubTypeByTrxID(long transactionID) throws SearchDAOException, CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getTrxSubTypeByTrxID(transactionID);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getTrxSubTypeByTrxID: ", ex);
		}
	}

	/**
	 * Search for a list of staging templates based on theh search criteria
	 * @param aCriteria - TemplateSearchCriteria
	 * @return SearchResult - the result from the search that contains the list
	 *         of staging templates that satisfy the c criteria
	 * @throws com.integrosys.base.businfra.search.SearchDAOException on DAO
	 *         errors
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	protected SearchResult searchStagingTemplateList(TemplateSearchCriteria aCriteria) throws SearchDAOException,
			CheckListTemplateException {
		try {
			return getSBStagingCheckListTemplateBusManager().searchTemplateList(aCriteria);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at searchTemplateList: ", ex);
		}
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
	 * @throws CheckListTemplateException for any errors encountered
	 */
	private SBCheckListTemplateBusManager getSBCheckListTemplateBusManager() throws CheckListTemplateException {
		SBCheckListTemplateBusManager busmgr = (SBCheckListTemplateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CHECKLIST_TEMPLATE_BUS_JNDI, SBCheckListTemplateBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new CheckListTemplateException("SBCheckListTemplateBusManager is null!");
		}
		return busmgr;
	}

	/**
	 * Helper method to return the staging checklist bus session bean
	 * 
	 * @return SBCheckListBusManager - the remote handler for the checklist bus
	 *         manager session bean
	 * @throws CheckListTemplateException for any errors encountered
	 */
	private SBCheckListTemplateBusManager getSBStagingCheckListTemplateBusManager() throws CheckListTemplateException {
		SBCheckListTemplateBusManager busmgr = (SBCheckListTemplateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_CHECKLIST_TEMPLATE_BUS_JNDI, SBCheckListTemplateBusManagerHome.class
						.getName());
		if (busmgr == null) {
			throw new CheckListTemplateException("SBCheckListTemplateBusManager is null!");
		}
		return busmgr;
	}

	
	public SearchResult getCollateralList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public SearchResult getFacilityList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SearchResult getCollateralIdList(String secSubType) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public ITemplate getPariPassuTemplate(String pariPassuType,
			String pariPassuSubType, String country)
			throws CheckListTemplateException {
		// TODO Auto-generated method stub
		return null;
	}

	public SearchResult getFilteredDocumentItemList(DocumentSearchCriteria aCriteria, List docCrit) throws SearchDAOException,
			CheckListTemplateException {
		try {
			return getSBCheckListTemplateBusManager().getFilteredDocumentItemList(aCriteria,docCrit);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getDocumentItemList: ", ex);
		}
	}
	

	

	
	

}
