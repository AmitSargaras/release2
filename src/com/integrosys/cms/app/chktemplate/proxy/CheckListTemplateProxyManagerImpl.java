package com.integrosys.cms.app.chktemplate.proxy;

import java.rmi.RemoteException;
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
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterDAOFactory;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterDAOFactory;
import com.integrosys.cms.app.transaction.ITrxContext;

public class CheckListTemplateProxyManagerImpl implements ICheckListTemplateProxyManager {

	private SBCheckListTemplateProxyManager slsbCheckListTemplateProxyManager;

	public void setSlsbCheckListTemplateProxyManager(SBCheckListTemplateProxyManager slsbCheckListTemplateProxyManager) {
		this.slsbCheckListTemplateProxyManager = slsbCheckListTemplateProxyManager;
	}

	/**
	 * Get the document item by criteria
	 * @param aCriteria - DocumentSearchCriteria
	 * @return SearchResult - the search result containing the list of document
	 *         items that satisfy the criteria
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is DAO
	 *         errors
	 */
	public SearchResult getDocumentItemList(final DocumentSearchCriteria aCriteria) throws CheckListTemplateException,
			SearchDAOException {
		return (SearchResult) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getDocumentItemList(aCriteria);
			}
		});
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
	public IItem[] getParentItemList(final String checklistCategory, final long templateID)
			throws CheckListTemplateException, SearchDAOException {
		return (IItem[]) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getParentItemList(checklistCategory, templateID);
			}
		});
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
	public IItem[] getParentItemList(final String checklistCategory, final long templateID, final String goodStatus, final String pbrInd)
			throws CheckListTemplateException, SearchDAOException {
		return (IItem[]) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {
                       //ckc
			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getParentItemList(checklistCategory, templateID, goodStatus, pbrInd);
			}
		});
	}


	/**
	 * Get a document item by transaction ID
	 * @param aTrxID - String
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public IDocumentItemTrxValue getDocumentItemByTrxID(final String aTrxID) throws CheckListTemplateException {
		return (IDocumentItemTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getDocumentItemByTrxID(aTrxID);
			}
		});
	}

	/**
	 * Maker creation of a doc item checklist
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public IDocumentItemTrxValue makerCreateDocItem(final ITrxContext anITrxContext, final IDocumentItem anIDocumentItem)
			throws CheckListTemplateException {
		return (IDocumentItemTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.makerCreateDocItem(anITrxContext, anIDocumentItem);
			}
		});
	}

	/**
	 * Checker approve the a doc item transaction
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public IDocumentItemTrxValue checkerApproveDocItem(final ITrxContext anITrxContext,
			final IDocumentItemTrxValue anIDocumentItemTrxValue) throws CheckListTemplateException {
		return (IDocumentItemTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.checkerApproveDocItem(anITrxContext, anIDocumentItemTrxValue);
			}
		});
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
	public IDocumentItemTrxValue checkerRejectDocItem(final ITrxContext anITrxContext,
			final IDocumentItemTrxValue anIDocumentItemTrxValue) throws CheckListTemplateException {
		return (IDocumentItemTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.checkerRejectDocItem(anITrxContext, anIDocumentItemTrxValue);
			}
		});
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
	public IDocumentItemTrxValue makerCloseDocItemTrx(final ITrxContext anITrxContext,
			final IDocumentItemTrxValue anIDocumentItemTrxValue) throws CheckListTemplateException {
		return (IDocumentItemTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.makerCloseDocItemTrx(anITrxContext, anIDocumentItemTrxValue);
			}
		});
	}

	/**
	 * Maker edit a rejected doc trx
	 * @param anITrxContext - ITrxContext
	 * @param anIDocumentItemTrxValue - IDocumentItemTrxValue
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentItemTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public IDocumentItemTrxValue makerEditRejectedDocItemTrx(final ITrxContext anITrxContext,
			final IDocumentItemTrxValue anIDocumentItemTrxValue, final IDocumentItem anIDocumentItem)
			throws CheckListTemplateException {
		return (IDocumentItemTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.makerEditRejectedDocItemTrx(anITrxContext, anIDocumentItemTrxValue, anIDocumentItem);
			}
		});
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
	public IDocumentItemTrxValue makerUpdateDocItem(final ITrxContext anITrxContext,
			final IDocumentItemTrxValue anIDocumentItemTrxValue, final IDocumentItem anIDocumentItem)
			throws CheckListTemplateException {
		return (IDocumentItemTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.makerUpdateDocItem(anITrxContext, anIDocumentItemTrxValue, anIDocumentItem);
			}
		});
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
	public IDocumentItemTrxValue makerDeleteDocItem(final ITrxContext anITrxContext,
			final IDocumentItemTrxValue anIDocumentItemTrxValue, final IDocumentItem anIDocumentItem)
			throws CheckListTemplateException {
		return (IDocumentItemTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.makerDeleteDocItem(anITrxContext, anIDocumentItemTrxValue, anIDocumentItem);
			}
		});
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
	public DocumentSearchResultItem[] getDocumentItemList(final ITemplate anITemplate)
			throws CheckListTemplateException, SearchDAOException, TemplateNotSetupException {
		try {
			return (DocumentSearchResultItem[]) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

				public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
					return proxy.getDocumentItemList(anITemplate);
				}
			});
		}
		catch (CheckListTemplateException ex) {
			if (ex.getCause() instanceof TemplateNotSetupException) {
				throw (TemplateNotSetupException) ex.getCause();
			}

			throw ex;
		}
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
	public LawSearchResultItem[] getLawCustomerTypes(final TemplateSearchCriteria aCriteria) throws SearchDAOException,
			CheckListTemplateException {
		return (LawSearchResultItem[]) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getLawCustomerTypes(aCriteria);
			}
		});
	}

	/**
	 * To get the list of laws and customer types with templates indication
	 * @param aCriteria - TemplateSearchCriteria
	 * @return CollateralSubTypeSearchResultItem[] - the list of collateral
	 *         subtype with template indication
	 * @throws CheckListTemplateException on errors
	 * @throws SearchDAOException is DAO errors
	 */
	public CollateralSubTypeSearchResultItem[] getCollateralSubType(final TemplateSearchCriteria aCriteria)
			throws CheckListTemplateException, SearchDAOException {
		return (CollateralSubTypeSearchResultItem[]) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getCollateralSubType(aCriteria);
			}
		});
	}

	/**
	 * Get the country template based on the law, legal constitution and country
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @param aCountry - String
	 * @return ITemplate - the country template of the specified law, legal
	 *         constitution and country
	 * @throws com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException
	 *         if template is not setup
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplate getDefaultCCTemplate(final String aLaw, final String aLegalConstitution, final String aCountry)
			throws TemplateNotSetupException, CheckListTemplateException {
		try {
			return (ITemplate) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

				public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
					return proxy.getDefaultCCTemplate(aLaw, aLegalConstitution, aCountry);
				}
			});
		}
		catch (CheckListTemplateException ex) {
			if (ex.getCause() instanceof TemplateNotSetupException) {
				throw (TemplateNotSetupException) ex.getCause();
			}

			throw ex;
		}
	}

	/**
	 * Get the master security template based on the collateral type and sub
	 * type
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException
	 *         if no template is being setup
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplate getDefaultCollateralTemplate(final String aCollateralType, final String aCollateralSubType,
			final String aCountry) throws TemplateNotSetupException, CheckListTemplateException {
		try {
			return (ITemplate) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

				public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
					return proxy.getDefaultCollateralTemplate(aCollateralType, aCollateralSubType, aCountry);
				}
			});
		}
		catch (CheckListTemplateException ex) {
			if (ex.getCause() instanceof TemplateNotSetupException) {
				throw (TemplateNotSetupException) ex.getCause();
			}

			throw ex;
		}
	}

	/**
	 * Get a template by transaction ID
	 * @param aTrxID - String
	 * @return IDocumentItemTrxValue - the interface representing the template
	 *         trx object
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplateTrxValue getTemplateByTrxID(final String aTrxID) throws CheckListTemplateException {
		return (ITemplateTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getTemplateByTrxID(aTrxID);
			}
		});
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
	public boolean pendingCreateCCTemplateAlreadyExist(final String aLaw, final String aLegalConstitution)
			throws CheckListTemplateException {
		Boolean isPendingCreateCCTemplateAlreadyExist = (Boolean) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return Boolean.valueOf(proxy.pendingCreateCCTemplateAlreadyExist(aLaw, aLegalConstitution));
			}
		});

		return isPendingCreateCCTemplateAlreadyExist.booleanValue();
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
	public boolean pendingCreateCCTemplateAlreadyExist(final String aLaw, final String aLegalConstitution,
			final String aCountry) throws CheckListTemplateException {
		Boolean isPendingCreateCCTemplateAlreadyExist = (Boolean) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return Boolean.valueOf(proxy.pendingCreateCCTemplateAlreadyExist(aLaw, aLegalConstitution, aCountry));
			}
		});

		return isPendingCreateCCTemplateAlreadyExist.booleanValue();
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
	public boolean pendingCreateCollateralTemplateAlreadyExist(final String aCollateralType,
			final String aCollateralSubType) throws CheckListTemplateException {
		Boolean isPendingCreateCollateralTemplateAlreadyExist = (Boolean) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return Boolean.valueOf(proxy.pendingCreateCollateralTemplateAlreadyExist(aCollateralType,
						aCollateralSubType));
			}
		});

		return isPendingCreateCollateralTemplateAlreadyExist.booleanValue();
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
	public boolean pendingCreateCollateralTemplateAlreadyExist(final String aCollateralType,
			final String aCollateralSubType, final String aCountry) throws CheckListTemplateException {
		Boolean isPendingCreateCollateralTemplateAlreadyExist = (Boolean) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return Boolean.valueOf(proxy.pendingCreateCollateralTemplateAlreadyExist(aCollateralType,
						aCollateralSubType, aCountry));
			}
		});

		return isPendingCreateCollateralTemplateAlreadyExist.booleanValue();
	}

	/**
	 * Maker creation of a template checklist
	 * @param anITrxContext - ITrxContext
	 * @param anITemplate - ITemplate
	 * @return ITemplateTrxValue - the interface representing the template trx
	 *         obj
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplateTrxValue makerCreateTemplate(final ITrxContext anITrxContext, final ITemplate anITemplate)
			throws CheckListTemplateException {
		return (ITemplateTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.makerCreateTemplate(anITrxContext, anITemplate);
			}
		});
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
	public ITemplateTrxValue checkerApproveTemplate(final ITrxContext anITrxContext,
			final ITemplateTrxValue anITemplateTrxValue) throws CheckListTemplateException {
		return (ITemplateTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.checkerApproveTemplate(anITrxContext, anITemplateTrxValue);
			}
		});
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
	public ITemplateTrxValue checkerRejectTemplate(final ITrxContext anITrxContext,
			final ITemplateTrxValue anITemplateTrxValue) throws CheckListTemplateException {
		return (ITemplateTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.checkerRejectTemplate(anITrxContext, anITemplateTrxValue);
			}
		});
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
	public ITemplateTrxValue makerCloseTemplateTrx(final ITrxContext anITrxContext,
			final ITemplateTrxValue anITemplateTrxValue) throws CheckListTemplateException {
		return (ITemplateTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.makerCloseTemplateTrx(anITrxContext, anITemplateTrxValue);
			}
		});
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
	public ITemplateTrxValue makerEditRejectedTemplateTrx(final ITrxContext anITrxContext,
			final ITemplateTrxValue anITemplateTrxValue, final ITemplate anITemplate) throws CheckListTemplateException {
		return (ITemplateTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.makerEditRejectedTemplateTrx(anITrxContext, anITemplateTrxValue, anITemplate);
			}
		});
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
	public ITemplateTrxValue makerUpdateTemplate(final ITrxContext anITrxContext,
			final ITemplateTrxValue anITemplateTrxValue, final ITemplate anITemplate) throws CheckListTemplateException {
		return (ITemplateTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.makerUpdateTemplate(anITrxContext, anITemplateTrxValue, anITemplate);
			}
		});
	}

	public ITemplateItem[] searchTemplateItemList(final TemplateItemSearchCriteria aCriteria)
			throws SearchDAOException, CheckListTemplateException {
		return (ITemplateItem[]) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.searchTemplateItemList(aCriteria);
			}
		});
	}

	/**
	 * Gets the law for the given country
	 * @param aCountry - Country
	 * @return law of the country
	 * @throws CheckListTemplateException
	 */
	public String getLaw(final String aCountry) throws CheckListTemplateException {
		return (String) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getLaw(aCountry);
			}
		});
	}

	public String[] getLaw(final String aCountry, final boolean allFlag) throws CheckListTemplateException {
		return (String[]) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getLaw(aCountry, allFlag);
			}
		});
	}

	public int getNoOfDocItemByDesc(final String aCategory, final String aDocItemDescription)
			throws SearchDAOException, CheckListTemplateException {
		Integer noOfDocItemByDesc = (Integer) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return new Integer(proxy.getNoOfDocItemByDesc(aCategory, aDocItemDescription));
			}
		});

		return noOfDocItemByDesc.intValue();
	}

	/**
	 * Retrieves the set of dynamic properties for a given security subtype
	 * @param securitySubtype - security subtype to retrieve the dynamic
	 *        properties for
	 * @return set of dynamic properties of IDynamicPropertySetup for the given
	 *         subtype
	 * @throws SearchDAOException if errors during retrieval
	 */
	public IDynamicPropertySetup[] getDynamicPropertySetup(final String securitySubtype) throws SearchDAOException,
			CheckListTemplateException {
		return (IDynamicPropertySetup[]) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getDynamicPropertySetup(securitySubtype);
			}
		});
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
	public ITemplate getCollateralTemplate(final String aCollateralType, final String aCollateralSubType,
			final String aCountry) throws CheckListTemplateException {
		return (ITemplate) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getCollateralTemplate(aCollateralType, aCollateralSubType, aCountry);
			}
		});
	}
	
	/**
	 * Get the master cam template based on the collateral type and sub
	 * type
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @param aCountry - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws CheckListTemplateException on errors
	 */
	public ITemplate getCAMTemplate(final String aCollateralType, final String aCollateralSubType,
			final String aCountry) throws CheckListTemplateException {
		return (ITemplate) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getCAMTemplate(aCollateralType, aCollateralSubType, aCountry);
			}
		});
	}

	
	public ITemplate getFacilityTemplate(final String aCollateralType, final String aCollateralSubType,
			final String aCountry) throws CheckListTemplateException {
		return (ITemplate) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getFacilityTemplate(aCollateralType, aCollateralSubType, aCountry);
			}
		});
	}
	
	
	public ITemplate getCollateralTemplate(final String aCollateralType, final String aCollateralSubType,
			final String aCountry, final String applicationType, final String goodsStatus, final String pbrInd,
			final String preApproveDocFlag) throws CheckListTemplateException {
		return (ITemplate) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getCollateralTemplate(aCollateralType, aCollateralSubType, aCountry, applicationType,
						goodsStatus, pbrInd, preApproveDocFlag);
			}
		});
	}

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
	public ITemplate getCCTemplate(final String aLaw, final String aLegalConstitution, final String aCountry)
			throws CheckListTemplateException {
		return (ITemplate) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getCCTemplate(aLaw, aLegalConstitution, aCountry);
			}
		});
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
	public ITemplate getCCTemplate(final String aLaw, final String aLegalConstitution, final String aCountry,
			final String applicationType, final String borrowerType, final String preApprovalDocFlag)
			throws CheckListTemplateException {
		return (ITemplate) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {
			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getCCTemplate(aLaw, aLegalConstitution, aCountry, applicationType, borrowerType,
						preApprovalDocFlag);
			}
		});
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
	 */
	public boolean isDocumentCodeUnique(final String docCode, final String category) throws SearchDAOException,
			CheckListTemplateException {
		Boolean isDocumentCodeUnique = (Boolean) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return Boolean.valueOf(proxy.isDocumentCodeUnique(docCode, category));
			}
		});

		return isDocumentCodeUnique.booleanValue();
	}

	public String getTrxSubTypeByTrxID(final long transactionID) throws SearchDAOException, CheckListTemplateException {
		return (String) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getTrxSubTypeByTrxID(transactionID);
			}
		});
	}

	protected Object executeCheckListTemplateProxyAction(CheckListTemplateProxyAction action)
			throws CheckListTemplateException {
		try {
			return action.doInCheckListTemplateProxy(this.slsbCheckListTemplateProxyManager);
		}
		catch (Exception e) {
			if (e instanceof RemoteException) {
				throw new CheckListTemplateException(
						"error raised in accessing template proxy remote interface, throwing root cause.", e.getCause());
			}

			if (e instanceof CheckListTemplateException) {
				throw (CheckListTemplateException) e;
			}

			throw new CheckListTemplateException("uncategorized exception raised in checklist template module ["
					+ e.getClass() + "]", e);
		}

	}

	interface CheckListTemplateProxyAction {
		public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception;
	}

	
	public IDocumentItemTrxValue makerDraftDocItem(final ITrxContext anITrxContext,
			final IDocumentItem anIDocumentItem) throws CheckListTemplateException {
		return (IDocumentItemTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.makerDraftDocItem(anITrxContext, anIDocumentItem);
			}
		});
	}

	
	public IDocumentItemTrxValue makerUpdateDraftCreateDocItem(
			final ITrxContext anITrxContext,
			final IDocumentItemTrxValue anIDocumentItemTrxValue,
			final IDocumentItem anIDocumentItem) throws CheckListTemplateException {
		return (IDocumentItemTrxValue) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.makerUpdateDraftCreateDocItem(anITrxContext, anIDocumentItemTrxValue, anIDocumentItem);
			}
		});
	}

	
	public SearchResult getCollateralList() throws Exception {
		try {
			return  CollateralNewMasterDAOFactory.getCollateralNewMasterJDBC().getAllCollateralNewMaster();
		}
		catch (Exception ex) {
			throw new Exception();
		}
	}
	
	
	
	public SearchResult getFacilityList() throws Exception {
		try {
			return  FacilityNewMasterDAOFactory.getFacilityNewMasterJDBC().getAllFacilityNewMaster();
		}
		catch (Exception ex) {
			throw new Exception();
		}
	}
	public SearchResult getCollateralIdList(String secSubType) throws Exception {
		try {
			return  CollateralNewMasterDAOFactory.getCollateralNewMasterJDBC().getAllCollateralNewMasterBySecSubType(secSubType);
		}
		catch (Exception ex) {
			throw new Exception();
		}
	}
	
	public CollateralSubTypeSearchResultItem[] getFacilitySubType(final TemplateSearchCriteria aCriteria)
	throws CheckListTemplateException, SearchDAOException {
return (CollateralSubTypeSearchResultItem[]) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

	public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
		return proxy.getFacilitySubType(aCriteria);
	}
});
}
	
	/**
	 * Get the master security template based on the collateral type and sub
	 * type
	 * @param pariPassuType - String
	 * @param pariPassuSubType - String
	 * @param country - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws CheckListTemplateException on errors
	 */

	public ITemplate getPariPassuTemplate(final String pariPassuType,
			final String pariPassuSubType,final String country)
			throws CheckListTemplateException {
		return (ITemplate) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {

			public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
				return proxy.getPariPassuTemplate(pariPassuType, pariPassuSubType, country);
			}
		});
	}

	public SearchResult getFilteredDocumentItemList(final DocumentSearchCriteria aCriteria,final List docCrit)
					 throws CheckListTemplateException,
						SearchDAOException {
					return (SearchResult) executeCheckListTemplateProxyAction(new CheckListTemplateProxyAction() {
						public Object doInCheckListTemplateProxy(SBCheckListTemplateProxyManager proxy) throws Exception {
							return proxy.getFilteredDocumentItemList(aCriteria,docCrit);
						}
					});
				}

}
