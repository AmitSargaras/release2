package com.integrosys.cms.app.chktemplate.bus;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.transaction.CMSTransactionDAOFactory;

/**
 * Created by IntelliJ IDEA. User: user Date: Jul 16, 2008 Time: 6:36:12 PM To
 * change this template use File | Settings | File Templates.
 */
public class SBCheckListTemplateBusManagerBean extends AbstractCheckListTemplateBusManager implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBCheckListTemplateBusManagerBean() {
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
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public SearchResult getDocumentItemList(DocumentSearchCriteria aCriteria) throws SearchDAOException,
			CheckListTemplateException {
		try {
			aCriteria.setFirstSort(IDocumentDAO.DOCITEMTBL_ITEM_DESC_PREF);
//			aCriteria.setDocumentType("CAM");
			return getEBDocumentItemHome().getDocumentItemList(aCriteria);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getDocumentItemList: ", ex);
		}
	}
	
	public SearchResult getFilteredDocumentItemList(DocumentSearchCriteria aCriteria,List docCrit) throws SearchDAOException,
	CheckListTemplateException {
try {
	aCriteria.setFirstSort(IDocumentDAO.DOCITEMTBL_ITEM_DESC_PREF);
	return getEBDocumentItemHome().getFilteredDocumentItemList(aCriteria,docCrit);
}
catch (RemoteException ex) {
	_context.setRollbackOnly();
	throw new CheckListTemplateException("RemoteException enctr at getDocumentItemList: ", ex);
}
}


	/**
	 * Get the number of doc item under the same category and having the same
	 * description
	 * @param aCategory of String type
	 * @param aDescription of String type
	 * @return int - the number of doc items
	 * @throws com.integrosys.base.businfra.search.SearchDAOException ,
	 *         CheckListTemplateException
	 */
	public int getNoOfDocItemByDesc(String aCategory, String aDescription) throws SearchDAOException,
			CheckListTemplateException {
		try {
			return getEBDocumentItemHome().getNoOfDocItemByDesc(aCategory, aDescription);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getNoOfDocItemByDesc: ", ex);
		}
	}

	/**
	 * Create a document item
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItem - the document item being updated
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public IDocumentItem create(IDocumentItem anIDocumentItem) throws CheckListTemplateException {
		try {
			if (anIDocumentItem == null) {
				throw new CheckListTemplateException("IDocumentItem is null!!!");
			}
			EBDocumentItem remoteItem = getEBDocumentItemHome().create(anIDocumentItem);
			//remoteItem.createDocumentAppItem(anIDocumentItem);
			return remoteItem.getValue();
		}
		catch (CheckListTemplateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (CreateException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("CreateException enctr at update: ", ex);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at update: ", ex);
		}
	}

	/**
	 * Update a document item
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItem - the document item being updated
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 *         if enctr concurrent update
	 */
	public IDocumentItem update(IDocumentItem anIDocumentItem) throws CheckListTemplateException,
			ConcurrentUpdateException {
		try {
			if (anIDocumentItem == null) {
				throw new CheckListTemplateException("IDocumentItem is null!!!");
			}
			Long pk = new Long(anIDocumentItem.getItemID());
			EBDocumentItem remoteItem = getEBDocumentItemHome().findByPrimaryKey(pk);
			remoteItem.setValue(anIDocumentItem);
			return remoteItem.getValue();
		}
		catch (CheckListTemplateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("FinderException enctr at update: ", ex);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at update: ", ex);
		}
	}

	/**
	 * Get a document item by ID
	 * @param aDocumentItemID - String
	 * @return IDocumentItem - the document item
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 */
	public IDocumentItem getDocumentItemByID(long aDocumentItemID) throws CheckListTemplateException {
		try {
			EBDocumentItem remote = getEBDocumentItemHome().findByPrimaryKey(new Long(aDocumentItemID));
			return remote.getValue();
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("FinderException enctr at getDocumentItemByID: ", ex);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getDocumentItemByID: ", ex);
		}
	}

	/**
	 * Get the list of laws and customer types
	 * @param lawList of String type
	 * @return LawSearchResultItem[] - the list of law and customer types
	 * @throws com.integrosys.base.businfra.search.SearchDAOException on DAO
	 *         errors
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public LawSearchResultItem[] getLawCustomerTypes(String[] lawList) throws SearchDAOException, CheckListTemplateException {
		try {
			return getEBTemplateHome().getLawCustomerTypes(lawList);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getLawCustomerTypes: ", ex);
		}
	}

	/**
	 * To get the list of templates based on the criteria specified
	 * @param aCriteria - TemplateSearchCriteria
	 * @return SearchResult - contain the list fo templates that satisfy the
	 *         criteria
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is errors
	 *         in DAO
	 */
	public SearchResult searchTemplateList(TemplateSearchCriteria aCriteria) throws SearchDAOException,
			CheckListTemplateException {
		try {
			return getEBTemplateHome().searchTemplateList(aCriteria);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at searchTemplateList: ", ex);
		}
	}

	/**
	 * To get the list of template item based on the criteria specified
	 * @param aCriteria - TemplateItemSearchCriteria
	 * @return SearchResult - contain the list fo templates that satisfy the
	 *         criteria
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is errors
	 *         in DAO
	 */
	public ITemplateItem[] searchTemplateItemList(TemplateItemSearchCriteria aCriteria) throws SearchDAOException,
			CheckListTemplateException {
		// try
		// {
		// return getEBTemplateHome().searchTemplateItemList(aCriteria);
		// TODO
		return null;
		// }
		// catch(RemoteException ex)
		// {
		// _context.setRollbackOnly();
		// throw new
		// CheckListTemplateException(
		// "RemoteException enctr at searchTemplateItemList: "
		// + ex.toString());
		// }
	}

	/**
	 * Get the list of Contractual/Constitutional templates based on law and
	 * legal constitution
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @return ITemplate[] - the list of CC master templates of the specified
	 *         law and legal constition
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	protected ITemplate[] getCCTemplateList(String aLaw, String aLegalConstitution) throws CheckListTemplateException {
		try {
			Collection remoteList = getEBTemplateHome().findByLawConstitution(ICMSConstant.DOC_TYPE_CC, aLaw,
					aLegalConstitution);
			if ((remoteList == null) || ((remoteList.size()) == 0)) {
				return null;
			}
			ITemplate[] templateList = new OBTemplate[remoteList.size()];
			Iterator iter = remoteList.iterator();
			int ctr = 0;
			while (iter.hasNext()) {
				templateList[ctr] = ((EBTemplate) iter.next()).getValue();
				ctr++;
			}
			return templateList;
		}
		catch (FinderException ex) {
			DefaultLogger.info(this, "No template with law " + aLaw + " and legal constitution " + aLegalConstitution
					+ " is found.");
			return null;
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getCCTemplateList: ", ex);
		}
	}

	/**
	 * Get the list of Contractual/Constitutional templates based on law, legal
	 * constitution and country
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @param aCountry - String
	 * @return ITemplate[] - the list of CC master templates of the specified
	 *         law, legal constition and country
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	protected ITemplate[] getCCTemplateList(String aLaw, String aLegalConstitution, String aCountry)
			throws CheckListTemplateException {
		try {
			Collection remoteList = getEBTemplateHome().findByLawConstitutionCountry(ICMSConstant.DOC_TYPE_CC, aLaw,
					aLegalConstitution, aCountry);
			if ((remoteList == null) || ((remoteList.size()) == 0)) {
				return null;
			}
			ITemplate[] templateList = new OBTemplate[remoteList.size()];
			Iterator iter = remoteList.iterator();
			int ctr = 0;
			while (iter.hasNext()) {
				templateList[ctr] = ((EBTemplate) iter.next()).getValue();
				ctr++;
			}
			return templateList;
		}
		catch (FinderException ex) {
			DefaultLogger.info(this, "No template with law " + aLaw + ", legal constitution " + aLegalConstitution
					+ " and Country " + aCountry + " is found.");
			return null;
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getCCTemplateList: ", ex);
		}
	}

	/**
	 * Get the list of Collateral templates based on collateral type and sub
	 * type
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @return ITemplate[] - the list of CC master templates of the specified
	 *         collateral type and subtype
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	protected ITemplate[] getCollateralTemplateList(String aCollateralType, String aCollateralSubType)
			throws CheckListTemplateException {
		try {
			Collection remoteList = getEBTemplateHome().findByCollateralTypeSubType(ICMSConstant.DOC_TYPE_SECURITY,
					aCollateralType, aCollateralSubType);
			if ((remoteList == null) || ((remoteList.size()) == 0)) {
				return null;
			}
			ITemplate[] templateList = new OBTemplate[remoteList.size()];
			Iterator iter = remoteList.iterator();
			int ctr = 0;
			while (iter.hasNext()) {
				templateList[ctr] = ((EBTemplate) iter.next()).getValue();
				ctr++;
			}
			return templateList;
		}
		catch (FinderException ex) {
			DefaultLogger.info(this, "No template with Collateral Type " + aCollateralType + " and sub type "
					+ aCollateralSubType + " is found.");
			return null;
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getCCTemplateList: ", ex);
		}
	}

	/**
	 * Get the list of Collateral templates based on collateral type, sub type
	 * and country
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @param aCountry - String
	 * @return ITemplate[] - the list of Collateral global templates of the
	 *         specified collateral type, subtype and country
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	protected ITemplate[] getCollateralTemplateList(String aCollateralType, String aCollateralSubType, String aCountry)
			throws CheckListTemplateException {
		try {
			Collection remoteList = getEBTemplateHome().findByCollateralTypeSubTypeCountry(
					ICMSConstant.DOC_TYPE_SECURITY, aCollateralType, aCollateralSubType, aCountry);
			
			if ((remoteList == null) || ((remoteList.size()) == 0)) {
				return null;
			}
			ITemplate[] templateList = new OBTemplate[remoteList.size()];
			Iterator iter = remoteList.iterator();
			int ctr = 0;
			while (iter.hasNext()) {
				templateList[ctr] = ((EBTemplate) iter.next()).getValue();
				ctr++;
			}
			return templateList;
		}
		catch (FinderException ex) {
			DefaultLogger.info(this, "No template with Collateral type " + aCollateralType + ", sub type "
					+ aCollateralSubType + " and Country " + aCountry + " is found.");
			return null;
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getCCTemplateList: ", ex);
		}
	}



	protected ITemplate[] getCAMTemplateList(String aCollateralType, String aCollateralSubType, String aCountry)
	throws CheckListTemplateException {
	try {
		//	Collection remoteList = getEBTemplateHome().findByCollateralTypeSubTypeCountry(
		//			ICMSConstant.DOC_TYPE_SECURITY, aCollateralType, aCollateralSubType, aCountry);
		Collection remoteList=null;
			if("CAM".equals(aCollateralType)){
				remoteList = getEBTemplateHome().findByCollateralTypeSubTypeCountry(
					ICMSConstant.DOC_TYPE_CAM, aCollateralType, aCollateralSubType, aCountry);
			}
			if("O".equals(aCollateralType)){
				remoteList = getEBTemplateHome().findByCollateralTypeSubTypeCountry(
						ICMSConstant.DOC_TYPE_OTHER, aCollateralType, aCollateralSubType, aCountry);
			}
			if("REC".equals(aCollateralType)){
				remoteList = getEBTemplateHome().findByCollateralTypeSubTypeCountry(
						ICMSConstant.DOC_TYPE_RECURRENT_MASTER, aCollateralType, aCollateralSubType, aCountry);
			}
			if ((remoteList == null) || ((remoteList.size()) == 0)) {
				return null;
			}
			ITemplate[] templateList = new OBTemplate[remoteList.size()];
			Iterator iter = remoteList.iterator();
			int ctr = 0;
			while (iter.hasNext()) {
				templateList[ctr] = ((EBTemplate) iter.next()).getValue();
				ctr++;
			}
			return templateList;
		}
		catch (FinderException ex) {
			DefaultLogger.info(this, "No template with Collateral type " + aCollateralType + ", sub type "
					+ aCollateralSubType + " and Country " + aCountry + " is found.");
			return null;
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getCCTemplateList: ", ex);
		}
	}
// Start By Abhijit R for Facility Checklist 6 july 2011
	protected ITemplate[] getFacilityTemplateList(String aCollateralType, String aCollateralSubType, String aCountry)
	throws CheckListTemplateException {
	try {
		Collection remoteList = getEBTemplateHome().findByCollateralTypeSubTypeCountry(
				ICMSConstant.DOC_TYPE_FACILITY, aCollateralType, aCollateralSubType, aCountry);
			if ((remoteList == null) || ((remoteList.size()) == 0)) {
				return null;
			}
			ITemplate[] templateList = new OBTemplate[remoteList.size()];
			Iterator iter = remoteList.iterator();
			int ctr = 0;
			while (iter.hasNext()) {
				templateList[ctr] = ((EBTemplate) iter.next()).getValue();
				ctr++;
			}
			return templateList;
		}
		catch (FinderException ex) {
			DefaultLogger.info(this, "No template with Facility type " + aCollateralType + ", sub type "
					+ aCollateralSubType + " and Country " + aCountry + " is found.");
			return null;
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at getCCTemplateList: ", ex);
		}
	}
	
// Start By abhijit R for Facility Checklist End	
    /**
	 * Create a template
	 * @param anITemplate - ITemplate
	 * @return ICheckList - the template being created
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         ;
	 */
	public ITemplate create(ITemplate anITemplate) throws CheckListTemplateException {
		try {
			if (anITemplate == null) {
				throw new CheckListTemplateException("ITemplate is null!!!");
			}
			EBTemplate remote = getEBTemplateHome().create(anITemplate);
			remote.createTemplateItems(anITemplate);
			return remote.getValue();
		}
		catch (CheckListTemplateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (CreateException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("CreateException enctr in create: ", ex);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr in create: ", ex);
		}
	}

	/**
	 * Update a template
	 * @param anITemplate - ITemplate
	 * @return ITemplate - the template being updated
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 */
	public ITemplate update(ITemplate anITemplate) throws ConcurrentUpdateException, CheckListTemplateException {
		try {
			if (anITemplate == null) {
				throw new CheckListTemplateException("ITemplate is null!!!");
			}
			Long pk = new Long(anITemplate.getTemplateID());
			DefaultLogger.debug(this, "Primary Key: " + pk);
			EBTemplate remoteTemp = getEBTemplateHome().findByPrimaryKey(pk);
			remoteTemp.setValue(anITemplate);
			return remoteTemp.getValue();
		}
		catch (CheckListTemplateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("FinderException enctr at update template id ["
					+ anITemplate.getTemplateID() + "]", ex);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr at update: ", ex);
		}
	}

	/**
	 * Get a template based on the value in the template type
	 * @param anITemplateType - ITemplateType
	 * @return ICheckList - the checklist
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 */
	public ITemplate getTemplateList(ITemplateType anITemplateType) throws CheckListTemplateException {
		// TODO
		return null;
	}

	/**
	 * Get a template based on the value in the template type
	 * @param aTemplateID - long
	 * @return ITemplate - the biz object containing the template info
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 */
	public ITemplate getTemplateByID(long aTemplateID) throws CheckListTemplateException {
		try {
			EBTemplate remoteTemp = getEBTemplateHome().findByPrimaryKey(new Long(aTemplateID));
			ITemplate template = remoteTemp.getValue();
			return template;
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("FinderException enctr in getTemplateByID " + aTemplateID, ex);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException enctr in getTemplateByID " + aTemplateID, ex);
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
	 * 
	 * @return true if code is unique; false otherwise
	 */
	public boolean isDocumentCodeUnique(String docCode, String category) throws SearchDAOException,
			CheckListTemplateException {
		return CheckListTemplateDAOFactory.getDocumentDAO().getIsDocumentCodeUnique(docCode, category);
	}
	
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
			CheckListTemplateException, FinderException {
		try {
			return getEBDocumentItemHome().findByItemCode(aItemCode);
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("RemoteException exception at getDocumentItemByItemCode: ", ex);
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
		return CheckListTemplateDAOFactory.getDocumentDAO().getDynamicPropertySetup(securitySubtype);
	}

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
	public String getTrxSubTypeByTrxID(long transactionID) throws SearchDAOException, CheckListTemplateException {
		return CMSTransactionDAOFactory.getDAO().getTrxSubTypeByTrxID(transactionID);
	}

	/**
	 * To get the home handler for the document item Entity Bean
	 * @return EBDocumentItemHome - the home handler for the document item
	 *         entity bean
	 */
	protected EBDocumentItemHome getEBDocumentItemHome() {
		EBDocumentItemHome ejbHome = (EBDocumentItemHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_DOCUMENT_ITEM_JNDI, EBDocumentItemHome.class.getName());
		return ejbHome;
	}

	/**
	 * To get the home handler for the template Entity Bean
	 * @return EBTemplateHome - the home handler for the template entity bean
	 */
	protected EBTemplateHome getEBTemplateHome() {
		EBTemplateHome ejbHome = (EBTemplateHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_TEMPLATE_JNDI,
				EBTemplateHome.class.getName());
		return ejbHome;
	}
}
