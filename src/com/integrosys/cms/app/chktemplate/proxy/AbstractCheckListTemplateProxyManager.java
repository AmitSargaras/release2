package com.integrosys.cms.app.chktemplate.proxy;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.CollateralSubTypeSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.CustomerTypeResultItem;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.chktemplate.bus.IDynamicPropertySetup;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.bus.LawSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.TemplateItemSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.TemplateSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.TemplateSearchResultItem;
import com.integrosys.cms.app.chktemplate.trx.CheckListTemplateTrxControllerFactory;
import com.integrosys.cms.app.chktemplate.trx.IDocumentItemTrxValue;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.chktemplate.trx.OBDocumentItemTrxValue;
import com.integrosys.cms.app.chktemplate.trx.OBTemplateTrxValue;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.collateral.bus.OBCollateralType;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * Created by IntelliJ IDEA. User: user Date: Jul 16, 2008 Time: 5:48:09 PM To
 * change this template use File | Settings | File Templates.
 */
public abstract class AbstractCheckListTemplateProxyManager implements ICheckListTemplateProxyManager {
	/**
	 * Get a document item by transaction ID
	 * @param aTrxID - String
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public IDocumentItemTrxValue getDocumentItemByTrxID(String aTrxID) throws CheckListTemplateException {
		if (aTrxID == null) {
			throw new CheckListTemplateException("The TrxID is null!!!");
		}
		IDocumentItemTrxValue trxValue = new OBDocumentItemTrxValue();
		trxValue.setTransactionID(aTrxID);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_DOC_ITEM);
		return operate(trxValue, param);
	}

	/**
	 * Maker creation of a document item checklist
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public IDocumentItemTrxValue makerCreateDocItem(ITrxContext anITrxContext, IDocumentItem anIDocumentItem)
			throws CheckListTemplateException {
		if (anITrxContext == null) {
			throw new CheckListTemplateException("The anITrxContext is null!!!");
		}
		if (anIDocumentItem == null) {
			throw new CheckListTemplateException("The IDocumentItem to be created is null!!!");
		}
		IDocumentItemTrxValue trxValue = formulateTrxValue(anITrxContext, anIDocumentItem);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_DOC_ITEM);
		return operate(trxValue, param);
	}

	/**
	 * Checker approve the creation of a doc item checklist
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public IDocumentItemTrxValue checkerApproveDocItem(ITrxContext anITrxContext,
			IDocumentItemTrxValue anIDocumentItemTrxValue) throws CheckListTemplateException {
		if (anITrxContext == null) {
			throw new CheckListTemplateException("The anITrxContext is null!!!");
		}
		if (anIDocumentItemTrxValue == null) {
			throw new CheckListTemplateException("The anIDocumentItemTrxValue to be approved is null!!!");
		}
		anIDocumentItemTrxValue = formulateTrxValue(anITrxContext, anIDocumentItemTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_DOC_ITEM);
		return operate(anIDocumentItemTrxValue, param);
	}

	/**
	 * Checker reject a doc item trx
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public IDocumentItemTrxValue checkerRejectDocItem(ITrxContext anITrxContext,
			IDocumentItemTrxValue anIDocumentItemTrxValue) throws CheckListTemplateException {
		if (anITrxContext == null) {
			throw new CheckListTemplateException("The anITrxContext is null!!!");
		}
		if (anIDocumentItemTrxValue == null) {
			throw new CheckListTemplateException("The anIDocumentItemTrxValue to be rejected is null!!!");
		}
		anIDocumentItemTrxValue = formulateTrxValue(anITrxContext, anIDocumentItemTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_DOC_ITEM);
		return operate(anIDocumentItemTrxValue, param);
	}

	/**
	 * Maker closes a doc item trx that has been rejected by the checker
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public IDocumentItemTrxValue makerCloseDocItemTrx(ITrxContext anITrxContext,
			IDocumentItemTrxValue anIDocumentItemTrxValue) throws CheckListTemplateException {
		if (anITrxContext == null) {
			throw new CheckListTemplateException("The anITrxContext is null!!!");
		}
		if (anIDocumentItemTrxValue == null) {
			throw new CheckListTemplateException("The anIDocumentItemTrxValue to be closed is null!!!");
		}
		anIDocumentItemTrxValue = formulateTrxValue(anITrxContext, anIDocumentItemTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DOC_ITEM);
		return operate(anIDocumentItemTrxValue, param);
	}

	/**
	 * Maker edit a rejected create doc trx
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public IDocumentItemTrxValue makerEditRejectedDocItemTrx(ITrxContext anITrxContext,
			IDocumentItemTrxValue anIDocumentItemTrxValue, IDocumentItem anIDocumentItem)
			throws CheckListTemplateException {
		if (anITrxContext == null) {
			throw new CheckListTemplateException("The anITrxContext is null!!!");
		}
		if (anIDocumentItemTrxValue == null) {
			throw new CheckListTemplateException("The anIDocumentItemTrxValue to be edited is null!!!");
		}
		anIDocumentItemTrxValue = formulateTrxValue(anITrxContext, anIDocumentItemTrxValue, anIDocumentItem);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DOC_ITEM);
		return operate(anIDocumentItemTrxValue, param);
	}

	/**
	 * Maker update a doc item
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public IDocumentItemTrxValue makerUpdateDocItem(ITrxContext anITrxContext,
			IDocumentItemTrxValue anIDocumentItemTrxValue, IDocumentItem anIDocumentItem)
			throws CheckListTemplateException {
		if (anITrxContext == null) {
			throw new CheckListTemplateException("The anITrxContext is null!!!");
		}
		if (anIDocumentItemTrxValue == null) {
			throw new CheckListTemplateException("The anIDocumentItemTrxValue to be updated is null!!!");
		}
		if (anIDocumentItem == null) {
			throw new CheckListTemplateException("The IDocumentItem to be updated is null !!!");
		}
		anIDocumentItemTrxValue = formulateTrxValue(anITrxContext, anIDocumentItemTrxValue, anIDocumentItem);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_DOC_ITEM);
		return operate(anIDocumentItemTrxValue, param);
	}
	
	
	/**
	 * Maker delete a doc item
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public IDocumentItemTrxValue makerDeleteDocItem(ITrxContext anITrxContext,
			IDocumentItemTrxValue anIDocumentItemTrxValue, IDocumentItem anIDocumentItem)
			throws CheckListTemplateException {
		if (anITrxContext == null) {
			throw new CheckListTemplateException("The anITrxContext is null!!!");
		}
		if (anIDocumentItemTrxValue == null) {
			throw new CheckListTemplateException("The anIDocumentItemTrxValue to be updated is null!!!");
		}
		if (anIDocumentItem == null) {
			throw new CheckListTemplateException("The IDocumentItem to be updated is null !!!");
		}
		anIDocumentItemTrxValue = formulateTrxValue(anITrxContext, anIDocumentItemTrxValue, anIDocumentItem);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_DOC_ITEM);
		return operate(anIDocumentItemTrxValue, param);
	}

	/**
	 * To get the list of laws and customer types with templates indication
	 * @param aCriteria - TemplateSearchCriteria
	 * @return LawSearchResultItem[] - the list of laws and customer types with
	 *         template indication
	 * @throws com.integrosys.base.businfra.search.SearchDAOException on DAO
	 *         errors
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public LawSearchResultItem[] getLawCustomerTypes(TemplateSearchCriteria aCriteria) throws SearchDAOException,
			CheckListTemplateException {
		String country = aCriteria.getCountry();
		String[] lawList = getLaw(country, true);
		LawSearchResultItem[] resultItem = getLawCustomerTypes(lawList);
		SearchResult searchResult = searchTemplateList(aCriteria);

		if ((resultItem == null) || (resultItem.length == 0)) {
			throw new CheckListTemplateException("Law and customer types not setup !!!");
		}

		if (searchResult == null) {
			return resultItem;
		}
		Collection col = searchResult.getResultList();
		if (col.size() == 0) {
			return resultItem;
		}
		TemplateSearchResultItem item = null;
		DefaultLogger.debug(this, "Number of Result Items: " + resultItem.length);
		DefaultLogger.debug(this, "Number of Search Result Items: " + col.size());
		for (int ii = 0; ii < resultItem.length; ii++) {
			CustomerTypeResultItem[] custTypes = resultItem[ii].getCustomerTypeList();
			if (custTypes != null) {
				for (int jj = 0; jj < custTypes.length; jj++) {
					Iterator iter = col.iterator();
					while (iter.hasNext()) {
						item = (TemplateSearchResultItem) iter.next();
						if ((item.getLaw().equals(resultItem[ii].getLawCode()))
								&& (item.getLegalConstitution().equals(custTypes[jj].getCustTypeCode()))) {
							custTypes[jj].setTemplateID(item.getTemplateID());
							custTypes[jj].setTrxID(item.getTrxID());
							custTypes[jj].setTrxStatus(item.getTrxStatus());
						}
					}
				}
				resultItem[ii].setCustomerTypeList(custTypes);
			}
		}
		return resultItem;
	}

	/**
	 * To get the list of collateral with templates indication
	 * @param aCriteria - TemplateSearchCriteria
	 * @return CollateralSubTypeSearchResultItem[] - the list of collateral
	 *         subtype with template indication
	 * @throws com.integrosys.base.businfra.search.SearchDAOException on DAO
	 *         errors
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public CollateralSubTypeSearchResultItem[] getCollateralSubType(TemplateSearchCriteria aCriteria)
			throws SearchDAOException, CheckListTemplateException {
		if (aCriteria == null) {
			throw new CheckListTemplateException("The TemplateSearchCriteria is null !!!");
		}
		if (aCriteria.getCollateralType() == null) {
			throw new CheckListTemplateException("The Collateral type is compulsary !!!");
		}
		CollateralSubTypeSearchResultItem[] subTypeList = getCollateralSubType(aCriteria.getCollateralType());
		SearchResult searchResult = searchTemplateList(aCriteria);
		if (searchResult == null) {
			return subTypeList;
		}
		Collection col = searchResult.getResultList();
		if (col.size() == 0) {
			return subTypeList;
		}
		TemplateSearchResultItem item = null;
		for (int ii = 0; ii < subTypeList.length; ii++) {
			Iterator iter = col.iterator();
			while (iter.hasNext()) {
				item = (TemplateSearchResultItem) iter.next();
				if (subTypeList[ii].getCollateralSubTypeCode().equals(item.getCollateralSubType())) {
					subTypeList[ii].setTemplateID(item.getTemplateID());
					subTypeList[ii].setTrxID(item.getTrxID());
					subTypeList[ii].setTrxStatus(item.getTrxStatus());
				}
			}
		}
		return subTypeList;
	}
	
	
	
	public CollateralSubTypeSearchResultItem[] getFacilitySubType(TemplateSearchCriteria aCriteria)
	throws SearchDAOException, CheckListTemplateException {
		if (aCriteria == null) {
			throw new CheckListTemplateException("The TemplateSearchCriteria is null !!!");
		}
//		if (aCriteria.getCollateralType() == null) {
//			throw new CheckListTemplateException("The Collateral type is compulsary !!!");
//		}
		CollateralSubTypeSearchResultItem[] subTypeList = getFacilitySubType();
		TemplateSearchCriteria searchCriteria = new TemplateSearchCriteria();
		searchCriteria.setTemplateType("F");
		searchCriteria.setCountry("IN");
		SearchResult searchResult = searchTemplateList(searchCriteria);
		if (searchResult == null) {
			return subTypeList;
		}
		Collection col = searchResult.getResultList();
		if (col.size() == 0) {
			return subTypeList;
		}
		TemplateSearchResultItem item = null;
		for (int ii = 0; ii < subTypeList.length; ii++) {
			Iterator iter = col.iterator();
			while (iter.hasNext()) {
				item = (TemplateSearchResultItem) iter.next();
				if (subTypeList[ii].getCollateralSubTypeCode().equals(item.getCollateralSubType())) {
					subTypeList[ii].setTemplateID(item.getTemplateID());
					subTypeList[ii].setTrxID(item.getTrxID());
					subTypeList[ii].setTrxStatus(item.getTrxStatus());
				}
			}
		}
	return subTypeList;
	}

	/**
	 * Get the list of collateral subtypes based on the collateral type
	 * @param aCollateralType - String
	 * @return CollateralSubTypeSearchResultItem[] - the list of collateral
	 *         subtypes under a collateral type
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	private CollateralSubTypeSearchResultItem[] getCollateralSubType(String aCollateralType)
			throws CheckListTemplateException {
		try {
			ICollateralType type = new OBCollateralType();
			type.setTypeCode(aCollateralType);
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			ICollateralSubType[] subTypeList = proxy.getCollateralSubTypeByType(type);
			if ((subTypeList == null) || (subTypeList.length == 0)) {
				throw new CheckListTemplateException("The list collateral subtypes under " + aCollateralType
						+ " is not setup");
			}
			CollateralSubTypeSearchResultItem[] itemList = new CollateralSubTypeSearchResultItem[subTypeList.length];
			for (int ii = 0; ii < subTypeList.length; ii++) {
				itemList[ii] = new CollateralSubTypeSearchResultItem(subTypeList[ii].getSubTypeCode(), subTypeList[ii]
						.getSubTypeName());
			}
			return itemList;
		}
		catch (CollateralException ex) {
			rollback();
			throw new CheckListTemplateException(ex);
		}
	}
	
	
	private CollateralSubTypeSearchResultItem[] getFacilitySubType()
	throws CheckListTemplateException {
		try {
			SearchResult sr = null;
			try {
				ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
				 sr=proxy.getFacilityList();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			List list=(List) sr.getResultList();
			CollateralSubTypeSearchResultItem[] itemList = new CollateralSubTypeSearchResultItem[list.size()];
			for (int ii = 0; ii < list.size(); ii++) {
				IFacilityNewMaster newMaster=(IFacilityNewMaster) list.get(ii);
				itemList[ii] = new CollateralSubTypeSearchResultItem(newMaster.getNewFacilityCode(), newMaster.getNewFacilityName());
			}
			return itemList;
		}
		catch (Exception ex) {
			rollback();
			throw new CheckListTemplateException(ex);
		}
	}

	/**
	 * Get a template by transaction ID
	 * @param aTrxID - String
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         object
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplateTrxValue getTemplateByTrxID(String aTrxID) throws CheckListTemplateException {
		if (aTrxID == null) {
			throw new CheckListTemplateException("The TrxID is null!!!");
		}
		ITemplateTrxValue trxValue = new OBTemplateTrxValue();
		trxValue.setTransactionID(aTrxID);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_TEMPLATE);
		return operate(trxValue, param);
	}

	/**
	 * To check if there is any pending create template based on the template
	 * type
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @return boolean - true if there exist and false otherwise
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public boolean pendingCreateCCTemplateAlreadyExist(String aLaw, String aLegalConstitution)
			throws CheckListTemplateException {
		TemplateSearchCriteria criteria = new TemplateSearchCriteria();
		criteria.setTemplateType(ICMSConstant.DOC_TYPE_CC);
		criteria.setLaw(aLaw);
		criteria.setLegalConstitution(aLegalConstitution);
		String[] trxStatusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_REJECTED };
		criteria.setTrxStatusList(trxStatusList);
		return pendingCreateTemplateAlreadyExist(criteria);
	}

	/**
	 * To check if there is any pending create template based on the template
	 * type
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @param aCountry - String
	 * @return boolean - true if there exist and false otherwise
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public boolean pendingCreateCCTemplateAlreadyExist(String aLaw, String aLegalConstitution, String aCountry)
			throws CheckListTemplateException {
		TemplateSearchCriteria criteria = new TemplateSearchCriteria();
		criteria.setTemplateType(ICMSConstant.DOC_TYPE_CC);
		criteria.setLaw(aLaw);
		criteria.setLegalConstitution(aLegalConstitution);
		criteria.setCountry(aCountry);
		String[] trxStatusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_REJECTED };
		criteria.setTrxStatusList(trxStatusList);
		return pendingCreateTemplateAlreadyExist(criteria);
	}

	/**
	 * To check if there is any pending create template based on the template
	 * type
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @return boolean - true if there exist and false otherwise
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public boolean pendingCreateCollateralTemplateAlreadyExist(String aCollateralType, String aCollateralSubType)
			throws CheckListTemplateException {
		TemplateSearchCriteria criteria = new TemplateSearchCriteria();
		criteria.setTemplateType(ICMSConstant.DOC_TYPE_SECURITY);
		criteria.setCollateralType(aCollateralType);
		criteria.setCollateralSubType(aCollateralSubType);
		String[] trxStatusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_REJECTED };
		criteria.setTrxStatusList(trxStatusList);
		return pendingCreateTemplateAlreadyExist(criteria);
	}

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
	public boolean pendingCreateCollateralTemplateAlreadyExist(String aCollateralType, String aCollateralSubType,
			String aCountry) throws CheckListTemplateException {
		TemplateSearchCriteria criteria = new TemplateSearchCriteria();
		criteria.setTemplateType(ICMSConstant.DOC_TYPE_SECURITY);
		criteria.setCollateralType(aCollateralType);
		criteria.setCollateralSubType(aCollateralSubType);
		criteria.setCountry(aCountry);
		String[] trxStatusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_REJECTED };
		criteria.setTrxStatusList(trxStatusList);
		return pendingCreateTemplateAlreadyExist(criteria);
	}

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
	public ITemplateTrxValue makerCreateTemplate(ITrxContext anITrxContext, ITemplate anITemplate)
			throws CheckListTemplateException {
		if (anITrxContext == null) {
			throw new CheckListTemplateException("The anITrxContext is null!!!");
		}
		if (anITemplate == null) {
			throw new CheckListTemplateException("The ITemplate to be created is null!!!");
		}
		validate(anITemplate);
		ITemplateTrxValue trxValue = formulateTrxValue(anITrxContext, anITemplate);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_TEMPLATE);
		return operate(trxValue, param);
	}

	/**
	 * Check if a template exist or not based on the criteria
	 * @param aCriteria - TemplateSearchCriteria
	 * @return boolean - true if it already exist and false otherwise
	 */
	private boolean pendingCreateTemplateAlreadyExist(TemplateSearchCriteria aCriteria)
			throws CheckListTemplateException {
		try {
			SearchResult result = searchStagingTemplateList(aCriteria);
			if (result == null) {
				return false;
			}
			Collection col = result.getResultList();
			if ((col == null) || (col.size() == 0)) {
				return false;
			}

			Iterator iter = col.iterator();
			while (iter.hasNext()) {
				TemplateSearchResultItem item = (TemplateSearchResultItem) iter.next();
				if ((item.getTrxStatus().equals(ICMSConstant.STATE_PENDING_CREATE))
						|| ((item.getTrxStatus().equals(ICMSConstant.STATE_REJECTED)) && (item.getTrxFromState()
								.equals(ICMSConstant.STATE_PENDING_CREATE)))) {
					return true;
				}
			}
			return false;
		}
		catch (Exception ex) {
			rollback();
			throw new CheckListTemplateException("Exception in pendingCreateTemplateAlreadyExist: " + ex.toString());
		}
	}

	/**
	 * Perform validation of template
	 * @param anITemplate - ITemplate
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	private void validate(ITemplate anITemplate) throws CheckListTemplateException {
		if (anITemplate.getTemplateType().equals(ICMSConstant.DOC_TYPE_CC)) {
			validateCC(anITemplate);
			return;
		}
		if (anITemplate.getTemplateType().equals(ICMSConstant.DOC_TYPE_SECURITY)) {
			validateCollateral(anITemplate);
			return;
		}
		DefaultLogger.info(this, "No validation required for template type : " + anITemplate.getTemplateType());
	}

	/**
	 * Perform validation for the CC Template
	 * @param anITemplate - ITemplate
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	private void validateCC(ITemplate anITemplate) throws CheckListTemplateException {
		if ((anITemplate.getLaw() == null) || (anITemplate.getLaw().trim().length() == 0)) {
			throw new CheckListTemplateException("The Law cannot be null or empty !!!");
		}
		if ((anITemplate.getLegalConstitution() == null) || (anITemplate.getLegalConstitution().trim().length() == 0)) {
			throw new CheckListTemplateException("The Legal Constitution/Customer Type cannot be null or empty !!!");
		}
		if ((anITemplate.getParentTemplateID() != ICMSConstant.LONG_INVALID_VALUE)
				&& (anITemplate.getCountry() == null)) {
			throw new CheckListTemplateException("The country cannot be null for country template!!!");
		}
		if (anITemplate.getCountry() == null) {
			if (getCCTemplate(anITemplate.getLaw(), anITemplate.getLegalConstitution()) != null) {
				throw new CheckListTemplateException("A CC template with law " + anITemplate.getLaw()
						+ " and legal constitution " + anITemplate.getLegalConstitution() + " already exist !!!");
			}
			if (pendingCreateCCTemplateAlreadyExist(anITemplate.getLaw(), anITemplate.getLegalConstitution())) {
				throw new CheckListTemplateException(new ConcurrentUpdateException("A CC template with law "
						+ anITemplate.getLaw() + " and legal constitution " + anITemplate.getLegalConstitution()
						+ " already exist!!!"));
			}
			return;
		}
		if (getCCTemplate(anITemplate.getLaw(), anITemplate.getLegalConstitution(), anITemplate.getCountry()) != null) {
			throw new CheckListTemplateException("A CC template with law " + anITemplate.getLaw()
					+ " and legal constitution " + anITemplate.getLegalConstitution() + " and country "
					+ anITemplate.getCountry() + " already exist !!!");
		}
		if (pendingCreateCCTemplateAlreadyExist(anITemplate.getLaw(), anITemplate.getLegalConstitution(), anITemplate
				.getCountry())) {
			throw new CheckListTemplateException(new ConcurrentUpdateException("A CC template with law "
					+ anITemplate.getLaw() + " and legal constitution " + anITemplate.getLegalConstitution()
					+ " and country " + anITemplate.getCountry() + " already exist !!!"));
		}
	}

	/**
	 * Perform validation for the Collateral Template
	 * @param anITemplate - ITemplate
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	private void validateCollateral(ITemplate anITemplate) throws CheckListTemplateException {
		if ((anITemplate.getCollateralType() == null) || (anITemplate.getCollateralType().trim().length() == 0)) {
			throw new CheckListTemplateException("The Collateral Type cannot be null or empty !!!");
		}
		if ((anITemplate.getCollateralSubType() == null) || (anITemplate.getCollateralSubType().trim().length() == 0)) {
			throw new CheckListTemplateException("The Collateral SubType cannot be null or empty !!!");
		}
		if ((anITemplate.getParentTemplateID() != ICMSConstant.LONG_INVALID_VALUE)
				&& (anITemplate.getCountry() == null)) {
			throw new CheckListTemplateException("The country cannot be null for country template!!!");
		}
		if (anITemplate.getCountry() == null) {
			if (getCollateralTemplate(anITemplate.getCollateralType(), anITemplate.getCollateralSubType()) != null) {
				throw new CheckListTemplateException("A Collateral template with type "
						+ anITemplate.getCollateralType() + " and subtype " + anITemplate.getCollateralSubType()
						+ " already exist !!!");
			}
			if (pendingCreateCollateralTemplateAlreadyExist(anITemplate.getCollateralType(), anITemplate
					.getCollateralSubType())) {
				throw new CheckListTemplateException(new ConcurrentUpdateException("A Collateral template with type "
						+ anITemplate.getCollateralType() + " and subtype " + anITemplate.getCollateralSubType()
						+ " already exist !!!"));
			}
			return;
		}
		if (getCollateralTemplate(anITemplate.getCollateralType(), anITemplate.getCollateralSubType(), anITemplate
				.getCountry()) != null) {
			throw new CheckListTemplateException("A Collateral template with type " + anITemplate.getCollateralType()
					+ ", subtype " + anITemplate.getCollateralSubType() + " and country " + anITemplate.getCountry()
					+ " already exist !!!");
		}
		if (pendingCreateCollateralTemplateAlreadyExist(anITemplate.getCollateralType(), anITemplate
				.getCollateralSubType(), anITemplate.getCountry())) {
			throw new CheckListTemplateException(new ConcurrentUpdateException("A Collateral template with type "
					+ anITemplate.getCollateralType() + ", subtype " + anITemplate.getCollateralSubType()
					+ " and country " + anITemplate.getCountry() + " already exist !!!"));
		}
	}

	/**
	 * Checker approves a template trx
	 * @param anITrxContext - ITrxContext
	 * @param anITemplateTrxValue - ITemplateTrxValue
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplateTrxValue checkerApproveTemplate(ITrxContext anITrxContext, ITemplateTrxValue anITemplateTrxValue)
			throws CheckListTemplateException {
		if (anITrxContext == null) {
			throw new CheckListTemplateException("The anITrxContext is null!!!");
		}
		if (anITemplateTrxValue == null) {
			throw new CheckListTemplateException("The anITemplateTrxValue to be approved is null!!!");
		}
		anITemplateTrxValue = formulateTrxValue(anITrxContext, anITemplateTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_TEMPLATE);
		return operate(anITemplateTrxValue, param);
	}

	/**
	 * Checker rejects a template trx
	 * @param anITrxContext - ITrxContext
	 * @param anITemplateTrxValue - anITemplateTrxValue
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplateTrxValue checkerRejectTemplate(ITrxContext anITrxContext, ITemplateTrxValue anITemplateTrxValue)
			throws CheckListTemplateException {
		if (anITrxContext == null) {
			throw new CheckListTemplateException("The anITrxContext is null!!!");
		}
		if (anITemplateTrxValue == null) {
			throw new CheckListTemplateException("The anITemplateTrxValue to be rejected is null!!!");
		}
		anITemplateTrxValue = formulateTrxValue(anITrxContext, anITemplateTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_TEMPLATE);
		return operate(anITemplateTrxValue, param);
	}

	/**
	 * Maker closes a template trx that has been rejected by the checker
	 * @param anITrxContext - ITrxContext
	 * @param anITemplateTrxValue - ITemplateTrxValue
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplateTrxValue makerCloseTemplateTrx(ITrxContext anITrxContext, ITemplateTrxValue anITemplateTrxValue)
			throws CheckListTemplateException {
		if (anITrxContext == null) {
			throw new CheckListTemplateException("The anITrxContext to be created is null!!!");
		}
		if (anITemplateTrxValue == null) {
			throw new CheckListTemplateException("The anITemplateTrxValue to be created is null!!!");
		}
		anITemplateTrxValue = formulateTrxValue(anITrxContext, anITemplateTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_TEMPLATE);
		return operate(anITemplateTrxValue, param);
	}

	/**
	 * Maker edits a rejected template
	 * @param anITrxContext - ITrxContext
	 * @param anITemplateTrxValue - ITemplateTrxValue
	 * @param anITemplate - ITemplate
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplateTrxValue makerEditRejectedTemplateTrx(ITrxContext anITrxContext,
			ITemplateTrxValue anITemplateTrxValue, ITemplate anITemplate) throws CheckListTemplateException {
		if (anITrxContext == null) {
			throw new CheckListTemplateException("The anITrxContext is null!!!");
		}
		if (anITemplateTrxValue == null) {
			throw new CheckListTemplateException("The ITemplateTrxValue to be update is null!!!");
		}
		if (anITemplate == null) {
			throw new CheckListTemplateException("The ITemplate to be updated is null !!!");
		}

		anITemplateTrxValue = formulateTrxValue(anITrxContext, anITemplateTrxValue, anITemplate);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_TEMPLATE);
		return operate(anITemplateTrxValue, param);
	}

	/**
	 * Maker updates a template
	 * @param anITrxContext - ITrxContext
	 * @param anITemplateTrxValue - ITemplateTrxValue
	 * @param anITemplate - ITemplate
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplateTrxValue makerUpdateTemplate(ITrxContext anITrxContext, ITemplateTrxValue anITemplateTrxValue,
			ITemplate anITemplate) throws CheckListTemplateException {
		if (anITrxContext == null) {
			throw new CheckListTemplateException("The anITrxContext is null!!!");
		}
		if (anITemplateTrxValue == null) {
			throw new CheckListTemplateException("The anITemplateTrxValue to be updated is null!!!"); 
		}
		if (anITemplate == null) {
			throw new CheckListTemplateException("The ITemplate to be updated is null !!!");
		}
		anITemplateTrxValue = formulateTrxValue(anITrxContext, anITemplateTrxValue, anITemplate);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_TEMPLATE);
		return operate(anITemplateTrxValue, param);
	}

	/**
	 * Formulate the document item Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItemTrxValue - the document item trx interface
	 *         formulated
	 */
	private IDocumentItemTrxValue formulateTrxValue(ITrxContext anITrxContext, IDocumentItem anIDocumentItem) {
		return formulateTrxValue(anITrxContext, null, anIDocumentItem);
	}

	/**
	 * Formulate the template Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anITemplate - ITemplate
	 * @return ITemplateTrxValue - the template trx interface formulated
	 */
	private ITemplateTrxValue formulateTrxValue(ITrxContext anITrxContext, ITemplate anITemplate) {
		return formulateTrxValue(anITrxContext, null, anITemplate);
	}

	/**
	 * Formulate the document item Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItemTrxValue - the document item trx interface
	 *         formulated
	 */
	private IDocumentItemTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IDocumentItem anIDocumentItem) {
		IDocumentItemTrxValue docItemTrxValue = null;
		if (anICMSTrxValue != null) {
			docItemTrxValue = new OBDocumentItemTrxValue(anICMSTrxValue);
		}
		else {
			docItemTrxValue = new OBDocumentItemTrxValue();
		}
		docItemTrxValue.setStagingDocumentItem(anIDocumentItem);
		docItemTrxValue = formulateTrxValue(anITrxContext, docItemTrxValue);
		return docItemTrxValue;
	}

	/**
	 * Formulate the template Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @param anITemplate - ITemplate
	 * @return ITemplateTrxValue - the template trx interface formulated
	 */
	private ITemplateTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			ITemplate anITemplate) {
		ITemplateTrxValue templateTrxValue = null;
		if (anICMSTrxValue != null) {
			templateTrxValue = new OBTemplateTrxValue(anICMSTrxValue);
		}
		else {
			templateTrxValue = new OBTemplateTrxValue();
		}
		templateTrxValue.setStagingTemplate(anITemplate);
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>> before.fromState: " + templateTrxValue.getFromState());
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>> before.toState: " + templateTrxValue.getToState());
		templateTrxValue = formulateTrxValue(anITrxContext, templateTrxValue);
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>> after.fromState: " + templateTrxValue.getFromState());
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>> after.toState: " + templateTrxValue.getToState());
		return templateTrxValue;
	}

	/**
	 * Formulate the document item trx object
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @return IDocumentItemTrxValue - the document item trx interface
	 *         formulated
	 */
	private IDocumentItemTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IDocumentItemTrxValue anIDocumentItemTrxValue) {
		anIDocumentItemTrxValue.setTrxContext(anITrxContext);
		anIDocumentItemTrxValue.setTransactionType(ICMSConstant.INSTANCE_DOC_ITEM_LIST);
		anIDocumentItemTrxValue.setTransactionSubType(getTransactionSubType(anIDocumentItemTrxValue
				.getStagingDocumentItem()));
		return anIDocumentItemTrxValue;
	}

	/**
	 * Formulate the template trx object
	 * @param anITrxContext - ITrxContext
	 * @param anITemplateTrxValue - ITemplateTrxValue
	 * @return ITemplateTrxValue - the template trx interface formulated
	 */
	private ITemplateTrxValue formulateTrxValue(ITrxContext anITrxContext, ITemplateTrxValue anITemplateTrxValue) {
		anITemplateTrxValue.setTrxContext(anITrxContext);
		anITemplateTrxValue.setTransactionType(ICMSConstant.INSTANCE_TEMPLATE_LIST);
		anITemplateTrxValue.setTransactionSubType(getTransactionSubType(anITemplateTrxValue.getStagingTemplate()));
		return anITemplateTrxValue;
	}

	private String getTransactionSubType(IDocumentItem docItem) {
		if (docItem == null) {
			return null;
		}

		if (ICMSConstant.DOC_TYPE_CC.equals(docItem.getItemType())) {
			return ICMSConstant.TRX_TYPE_CC_GLOBAL_TEMPLATE;
		}
		 if (ICMSConstant.DOC_TYPE_SECURITY.equals(docItem.getItemType())) {
			return ICMSConstant.TRX_TYPE_COL_GLOBAL_TEMPLATE;
		} 
		 if (ICMSConstant.DOC_TYPE_FACILITY.equals(docItem.getItemType())) {
			return ICMSConstant.TRX_TYPE_FAC_GLOBAL_TEMPLATE;
		}
		if (ICMSConstant.DOC_TYPE_CAM.equals(docItem.getItemType())) {
			return ICMSConstant.TRX_TYPE_CAM_GLOBAL_TEMPLATE;
		}
		if (ICMSConstant.DOC_TYPE_OTHER.equals(docItem.getItemType())) {
			return ICMSConstant.TRX_TYPE_OTHER_GLOBAL_TEMPLATE;
		}
		if (ICMSConstant.DOC_TYPE_RECURRENT_MASTER.equals(docItem.getItemType())) {
			return ICMSConstant.TRX_TYPE_RECURRENT_GLOBAL_TEMPLATE;
		}
		
		

		return null;
	}

	private String getTransactionSubType(ITemplate anITemplate) {
		if (anITemplate == null) {
			return null;
		}
		
		if (ICMSConstant.DOC_TYPE_CC.equals(anITemplate.getTemplateType())) {
			if (null == anITemplate.getCountry()) {
				return ICMSConstant.TRX_TYPE_CC_MASTER_TEMPLATE;
			}
			return ICMSConstant.TRX_TYPE_CC_COUNTRY_TEMPLATE;
		}

		if (ICMSConstant.DOC_TYPE_SECURITY.equals(anITemplate.getTemplateType())) {
			if (null == anITemplate.getCountry()) {
				return ICMSConstant.TRX_TYPE_COL_MASTER_TEMPLATE;
			}
			return ICMSConstant.TRX_TYPE_COL_MASTER_TEMPLATE;
		}
		if (ICMSConstant.DOC_TYPE_FACILITY.equals(anITemplate.getTemplateType())) {
			if (null == anITemplate.getCountry()) {
				return ICMSConstant.TRX_TYPE_FAC_MASTER_TEMPLATE;
			}
			return ICMSConstant.TRX_TYPE_FAC_MASTER_TEMPLATE;
		}
		return null;
	}

	/**
	 * Helper method to perform the document item transactions.
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return IDocumentItemTrxValue - the trx interface
	 */
	private IDocumentItemTrxValue operate(IDocumentItemTrxValue anIDocumentItemTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws CheckListTemplateException {
		ICMSTrxResult result = operateForResult(anIDocumentItemTrxValue, anOBCMSTrxParameter);
		return (IDocumentItemTrxValue) result.getTrxValue();
	}

	/**
	 * Helper method to perform the template transactions.
	 * @param anITemplateTrxValue - ITemplateTrxValue
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return ITemplateTrxValue - the trx interface
	 */
	private ITemplateTrxValue operate(ITemplateTrxValue anITemplateTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CheckListTemplateException {
		ICMSTrxResult result = operateForResult(anITemplateTrxValue, anOBCMSTrxParameter);
		return (ITemplateTrxValue) result.getTrxValue();
	}

	/**
	 * Helper method to perform the document item transactions.
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return ICMSTrxResult - the trx result interface
	 */
	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CheckListTemplateException {
		try {
			ITrxController controller = (new CheckListTemplateTrxControllerFactory()).getController(anICMSTrxValue,
					anOBCMSTrxParameter);
			if (controller == null) {
				throw new CheckListTemplateException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			rollback();
			throw new CheckListTemplateException(e);
		}
		catch (Exception ex) {
			rollback();
			throw new CheckListTemplateException(ex.toString());
		}
	}

	protected abstract ITemplate getCCTemplate(String aLaw, String aLegalConstitution)
			throws CheckListTemplateException;

	public abstract ITemplate getCCTemplate(String aLaw, String aLegalConstitution, String aCountry)
			throws CheckListTemplateException;

	public abstract ITemplate getCCTemplate(String aLaw, String aLegalConstitution, String aCountry,
			String applicationType, String borrowerType, String preApprovalDocFlag) throws CheckListTemplateException;

	protected abstract ITemplate getCollateralTemplate(String aCollateralType, String aCollateralSubType)
			throws CheckListTemplateException;

	public abstract ITemplate getCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
			throws CheckListTemplateException;
	
	public abstract ITemplate getCAMTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
	throws CheckListTemplateException;
	
	public abstract ITemplate getFacilityTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
	throws CheckListTemplateException;

	public abstract ITemplate getCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry,
			String applicationType, String goodsStatus, String pbrInd, String preApproveDocFlag)
			throws CheckListTemplateException;

	protected abstract LawSearchResultItem[] getLawCustomerTypes(String[] lawList) throws SearchDAOException,
			CheckListTemplateException;

	protected abstract SearchResult searchTemplateList(TemplateSearchCriteria aCriteria) throws SearchDAOException,
			CheckListTemplateException;

	public abstract ITemplateItem[] searchTemplateItemList(TemplateItemSearchCriteria aCriteria)
			throws SearchDAOException, CheckListTemplateException;

	protected abstract SearchResult searchStagingTemplateList(TemplateSearchCriteria aCriteria)
			throws SearchDAOException, CheckListTemplateException;

	public abstract int getNoOfDocItemByDesc(String aCategory, String aDocItemDescription) throws SearchDAOException,
			CheckListTemplateException;

	public abstract String getLaw(String aCountry) throws CheckListTemplateException;

	public abstract String[] getLaw(String aCountry, boolean allFlag) throws CheckListTemplateException;

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
	public abstract boolean isDocumentCodeUnique(String docCode, String category) throws SearchDAOException,
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
	 * @throws java.rmi.RemoteException on remote errors
	 * @return transaction sub type of the given transaction id
	 */
	public abstract String getTrxSubTypeByTrxID(long transactionID) throws SearchDAOException,
			CheckListTemplateException;

	protected abstract void rollback() throws CheckListTemplateException;
	
	
	public IDocumentItemTrxValue makerDraftDocItem(ITrxContext anITrxContext,
			IDocumentItem anIDocumentItem) throws CheckListTemplateException {
		if (anITrxContext == null) {
			throw new CheckListTemplateException("The anITrxContext is null!!!");
		}
		if (anIDocumentItem == null) {
			throw new CheckListTemplateException("The IDocumentItem to be created is null!!!");
		}
		IDocumentItemTrxValue trxValue = formulateTrxValue(anITrxContext, anIDocumentItem);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		 trxValue.setFromState("DRAFT");
	        trxValue.setStatus("PENDING_PERFECTION");
		param.setAction(ICMSConstant.ACTION_MAKER_DRAFT_DOC_ITEM);
		return operate(trxValue, param);
	}
	
	
	public IDocumentItemTrxValue makerUpdateDraftCreateDocItem(
			ITrxContext anITrxContext,
			IDocumentItemTrxValue anIDocumentItemTrxValue,
			IDocumentItem anIDocumentItem) throws CheckListTemplateException {
		if (anITrxContext == null) {
			throw new CheckListTemplateException("The anITrxContext is null!!!");
		}
		if (anIDocumentItemTrxValue == null) {
			throw new CheckListTemplateException("The anIDocumentItemTrxValue to be updated is null!!!");
		}
		if (anIDocumentItem == null) {
			throw new CheckListTemplateException("The IDocumentItem to be updated is null !!!");
		}
		anIDocumentItemTrxValue = formulateTrxValue(anITrxContext, anIDocumentItemTrxValue, anIDocumentItem);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_DOC_ITEM);
		return operate(anIDocumentItemTrxValue, param);
	}

}
