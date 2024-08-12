/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/proxy/AbstractCheckListProxyManager.java,v 1.254 2006/11/23 08:14:23 jychong Exp $
 */
package com.integrosys.cms.app.checklist.proxy;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.DefaultTrxProcess;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateException;
import com.integrosys.cms.app.cccertificate.proxy.CCCertificateProxyManagerFactory;
import com.integrosys.cms.app.cccertificate.proxy.ICCCertificateProxyManager;
import com.integrosys.cms.app.checklist.bus.CAMCheckListSummary;
import com.integrosys.cms.app.checklist.bus.CCCheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.CCCheckListSummary;
import com.integrosys.cms.app.checklist.bus.CheckListBusManagerFactory;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchCriteria;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
import com.integrosys.cms.app.checklist.bus.FacilityCheckListSummary;
import com.integrosys.cms.app.checklist.bus.IAuditItem;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListAudit;
import com.integrosys.cms.app.checklist.bus.ICheckListBusManager;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICheckListSummary;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.trx.CheckListTrxControllerFactory;
import com.integrosys.cms.app.checklist.trx.CheckListTrxDAO;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.checklist.trx.OBCheckListTrxValue;
import com.integrosys.cms.app.checklist.trx.OfficeCheckListTrxController;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.DocumentHeldSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.bus.TemplateItemSearchCriteria;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.collaborationtask.bus.CCTaskSearchCriteria;
import com.integrosys.cms.app.collaborationtask.bus.CCTaskSearchResult;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.bus.ICCTask;
import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.collaborationtask.proxy.CollaborationTaskProxyManagerFactory;
import com.integrosys.cms.app.collaborationtask.proxy.ICollaborationTaskProxyManager;
import com.integrosys.cms.app.collaborationtask.trx.ICCTaskTrxValue;
import com.integrosys.cms.app.collaborationtask.trx.ICollateralTaskTrxValue;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.IPledgor;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMasterJdbc;
import com.integrosys.cms.app.collateralNewMaster.bus.OBCollateralNewMaster;
import com.integrosys.cms.app.common.IContext;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.custodian.bus.CustodianException;
import com.integrosys.cms.app.custodian.bus.CustodianSearchCriteria;
import com.integrosys.cms.app.custodian.proxy.CustodianProxyManagerFactory;
import com.integrosys.cms.app.custodian.proxy.ICustodianProxyManager;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.documentlocation.bus.DocumentLocationException;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;
import com.integrosys.cms.app.documentlocation.proxy.DocumentLocationProxyManagerFactory;
import com.integrosys.cms.app.documentlocation.proxy.IDocumentLocationProxyManager;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateException;
import com.integrosys.cms.app.sccertificate.proxy.ISCCertificateProxyManager;
import com.integrosys.cms.app.sccertificate.proxy.SCCertificateProxyManagerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.checklist.CheckListUtil;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * This abstract class will contains all the biz related logic that is
 * independent of any technology implementation such as EJB
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.254 $
 * @since $Date: 2006/11/23 08:14:23 $ Tag: $Name: $
 */
public abstract class AbstractCheckListProxyManager implements ICheckListProxyManager {
	/**
	 * Get transaction sub-type for a checklist transaction.
	 * 
	 * @param aTrxID - primitive long denoting the checklist transaction
	 * @return result[0] - String denoting the transaction sub-type
	 * @return result[1] - String denoting the checklist category
	 * @throws CheckListException on errors
	 */
	public String[] getTrxSubTypeByTrxID(long aTrxID) throws CheckListException {
		try {
			CheckListTrxDAO dao = new CheckListTrxDAO();
			return dao.getTrxSubTypeAndCheckListCategoryByTrxID(aTrxID);
		}
		catch (SearchDAOException e) {
			DefaultLogger.error(this, "", e);
			throw new CheckListException("getTrxSubTypeAndCheckListCategoryByTrxID exception:" + e.toString());
		}
	}

	/**
	 * Get the list of c/c checklist summary info for ccc generation
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListException on errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryForCCC(IContext anIContext, ILimitProfile anILimitProfile)
			throws CheckListTemplateException, CheckListException {
		try {
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer customer = custProxy.getCustomer(anILimitProfile.getCustomerID());
			ArrayList resultList = new ArrayList();
			CCCheckListSummary summary = null;
			HashMap checkListMap = getCCCheckListStatus(anILimitProfile.getLimitProfileID(), true);

			if (CheckListUtil.isInCountry(anIContext.getTeam(), anILimitProfile, null)) {
				summary = formulateCCCheckListSummary(anIContext, anILimitProfile, checkListMap, customer,
						ICMSConstant.CHECKLIST_MAIN_BORROWER);
				if (summary != null) {
					resultList.add(summary);
				}
			}
			if (CheckListUtil.isInCountry(anIContext.getTeam(), anILimitProfile, null)) {
				getCoBorrowerPledgorCheckList(anIContext, anILimitProfile, checkListMap, resultList, false);
			}
			else {
				getCoBorrowerPledgorCheckList(anIContext, anILimitProfile, checkListMap, resultList, true);
			}
			CCCheckListSummary[] summaryList = (CCCheckListSummary[]) resultList.toArray(new CCCheckListSummary[0]);
			if ((summaryList != null) && (summaryList.length > 0)) {
				Arrays.sort(summaryList);
				// String[] param = { "CustCategoryNo", "LegalID" };
				// SortUtil.sortObject(summaryList, param);
			}
			return summaryList;
		}
		catch (CustomerException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryForCCC", ex);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryForCCC", ex);
		}
	}

	public CCCheckListSummary[] getCCCheckListSummaryForCollaboration(IContext anIContext, ILimitProfile anILimitProfile)
			throws CheckListTemplateException, CheckListException {
		try {
			ArrayList resultList = new ArrayList();
			HashMap checkListMap = getCCCheckListStatus(anILimitProfile.getLimitProfileID(), true);
			// For CR CMS-534
			if (CheckListUtil.isInCountry(anIContext.getTeam(), anILimitProfile, null)) {
				getCoBorrowerPledgorCheckListColTask(anIContext, anILimitProfile, checkListMap, resultList, false);
			}
			else {
				getCoBorrowerPledgorCheckList(anIContext, anILimitProfile, checkListMap, resultList, true);
			}

			CCCheckListSummary[] summaryList = (CCCheckListSummary[]) resultList.toArray(new CCCheckListSummary[0]);
			if ((summaryList != null) && (summaryList.length > 0)) {
				Arrays.sort(summaryList);
				// String[] param = { "CustCategoryNo", "LegalID" };
				// SortUtil.sortObject(summaryList, param);
			}
			return summaryList;
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryList", ex);
		}
	}

	private void populateOrgCode(ILimitProfile anILimitProfile, CCCheckListSummary aCCCheckListSummary)
			throws CheckListException {
		try {
			CCTaskSearchCriteria criteria = new CCTaskSearchCriteria();
			criteria.setLimitProfileID(anILimitProfile.getLimitProfileID());
			criteria.setCustomerCategory(aCCCheckListSummary.getCustCategory());
			criteria.setCustomerID(aCCCheckListSummary.getSubProfileID());
			criteria.setDomicileCountry(aCCCheckListSummary.getDomicileCtry());
			String[] trxStatusList = { ICMSConstant.STATE_ACTIVE };
			criteria.setTrxStatusList(trxStatusList);
			ICollaborationTaskProxyManager proxy = CollaborationTaskProxyManagerFactory.getProxyManager();
			CCTaskSearchResult[] result = proxy.getCCTask(criteria);
			if ((result != null) && (result.length == 1)) {
				aCCCheckListSummary.setOrgCode(result[0].getOrgCode());
				return;
			}
			else {
				if (!ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(aCCCheckListSummary.getCustCategory())) {
					aCCCheckListSummary.setOrgCode(null);
				}
			}
		}
		catch (CollaborationTaskException ex) {
			throw new CheckListException("Exception in populateOrgCode", ex);
		}
	}

	public HashMap getAllCCCheckListSummaryListForCustodian(IContext anIContext, long aLimitProfileID)
			throws CheckListTemplateException, CheckListException {
		CCCheckListSummary[] summaryList = getCCCheckListSummaryListForCustodian(anIContext, aLimitProfileID);
		CCCheckListSummary[] deleteList = getDeletedCCCheckListSummaryList(anIContext, aLimitProfileID);
		HashMap map = new HashMap();
		if (summaryList.length > 0) {
			map.put(ICMSConstant.NORMAL_LIST, summaryList);
		}
		if (deleteList.length > 0) {
			map.put(ICMSConstant.DELETED_LIST, deleteList);
		}
		return map;
	}

	/**
	 * Get the list of c/c checklist summary info for custodian.
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListException on errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryListForCustodian(IContext anIContext, long aLimitProfileID)
			throws CheckListTemplateException, CheckListException {
		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = proxy.getLimitProfile(aLimitProfileID);
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer customer = custProxy.getCustomer(limitProfile.getCustomerID());
			ArrayList resultList = new ArrayList();
			CCCheckListSummary summary = new CCCheckListSummary();
			HashMap checkListMap = getCCCheckListStatus(aLimitProfileID, true);
			// DefaultLogger.debug(this, "HashMap: " + checkListMap);
			CCCheckListSummary[] summaryList = null;
			if (CheckListUtil.isInCountry(anIContext.getTeam(), limitProfile, null)) {
				summary = formulateCCCheckListSummary(anIContext, limitProfile, checkListMap, customer,
						ICMSConstant.CHECKLIST_MAIN_BORROWER);
				if (summary != null) {
					resultList.add(summary);
				}
			}
			getCoBorrowerPledgorCheckList(anIContext, limitProfile, checkListMap, resultList, true);
			summaryList = (CCCheckListSummary[]) resultList.toArray(new CCCheckListSummary[0]);
			summaryList = filterOffDeletedCheckList(summaryList);
			if ((summaryList != null) && (summaryList.length > 0)) {
				Arrays.sort(summaryList);
				// String[] param = { "CustCategoryNo", "LegalID" };
				// SortUtil.sortObject(summaryList, param);
			}
			return summaryList;
		}
		catch (CustomerException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryListForCustodian", ex);
		}
		catch (LimitException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryListForCustodian", ex);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryListForCustodian", ex);
		}
	}

	/**
	 * Get the list of c/c checklist summary info.
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListException on errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListTemplateException, CheckListException {
		return getCCCheckListSummaryList(anIContext, aLimitProfileID, true);
	}
	
	public FacilityCheckListSummary[] getFacilityCheckListSummaryList(IContext anIContext, long aLimitProfileID)
	throws CheckListTemplateException, CheckListException {
		return getFacilityCheckListSummaryList(anIContext, aLimitProfileID, true);
		}

	/**
	 * Get the list of c/c checklist summary info. This includes the deleted
	 * checklist as well.
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return HashMap - contains the list of cc summary for those checklist
	 *         that are deleted as well as not deleted
	 * @throws CheckListException on errors
	 */
	public HashMap getAllCCCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListTemplateException, CheckListException {
		CCCheckListSummary[] summaryList = getCCCheckListSummaryList(anIContext, aLimitProfileID, false);
		CCCheckListSummary[] deleteList = getDeletedCCCheckListSummaryList(anIContext, aLimitProfileID);
		HashMap map = new HashMap();
		if (summaryList.length > 0) {
			map.put(ICMSConstant.NORMAL_LIST, summaryList);
		}
		if (deleteList.length > 0) {
			map.put(ICMSConstant.DELETED_LIST, deleteList);
		}
		return map;
	}

	private CCCheckListSummary[] getDeletedCCCheckListSummaryListForNonBorrower(IContext anIContext, long aCustomerID)
			throws CheckListTemplateException, CheckListException {
		try {
			CCCheckListSummary[] summaryList = getDeletedCCCheckListStatusForNonBorrower(aCustomerID);
			if ((summaryList == null) || (summaryList.length == 0)) {
				return null;
			}
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICheckListTemplateProxyManager templateProxy = CheckListTemplateProxyManagerFactory
					.getCheckListTemplateProxyManager();
			ICMSCustomer customer = null;
			String law = null;
			for (int ii = 0; ii < summaryList.length; ii++) {
				if (ICMSConstant.CHECKLIST_NON_BORROWER.equals(summaryList[ii].getCustCategory())) {
					customer = custProxy.getCustomer(summaryList[ii].getSubProfileID());
					law = templateProxy.getLaw(customer.getOriginatingLocation().getCountryCode());
					summaryList[ii].setLegalID(String.valueOf(customer.getCMSLegalEntity().getLEReference()));
					summaryList[ii].setLegalName(customer.getCMSLegalEntity().getLegalName());
					// summaryList[ii].setDomicileCtry(customer.getOriginatingLocation().getCountryCode());
					summaryList[ii].setCustomerSegmentCode(customer.getCMSLegalEntity().getLegalConstitution());
					summaryList[ii].setGovernLaw(law);
					populateOrgCode(new OBLimitProfile(), summaryList[ii]);
					summaryList[ii].setCustomerSegmentCode(customer.getCMSLegalEntity().getLegalConstitution());
					summaryList[ii].setSameCountryInd(isAccessAllowed(anIContext, summaryList[ii]));
				}
			}
			return summaryList;
		}
		catch (CustomerException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryList", ex);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in getDeletedCCCheckListSummaryListForNonBorrower", ex);
		}
	}

	private CCCheckListSummary[] getDeletedCCCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListTemplateException, CheckListException {
		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = proxy.getLimitProfile(aLimitProfileID);

			CCCheckListSummary[] summaryList = getDeletedCCCheckListStatus(aLimitProfileID, limitProfile
					.getCustomerID());
			if ((summaryList == null) || (summaryList.length == 0)) {
				return summaryList;
			}
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICollateralProxy colProxy = CollateralProxyFactory.getProxy();
			ICheckListTemplateProxyManager templateProxy = CheckListTemplateProxyManagerFactory
					.getCheckListTemplateProxyManager();
			ICMSCustomer customer = null;
			IPledgor pledgor = null;
			String law = null;
			ArrayList resultList = new ArrayList();
			boolean inCountry = CheckListUtil.isInCountry(anIContext.getTeam(), limitProfile, null);
			for (int ii = 0; ii < summaryList.length; ii++) {
				if (inCountry || isInSameCountry(anIContext, limitProfile, summaryList[ii])) {
					law = templateProxy.getLaw(summaryList[ii].getDomicileCtry());
					if ((ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(summaryList[ii].getCustCategory()))
							|| (ICMSConstant.CHECKLIST_CO_BORROWER.equals(summaryList[ii].getCustCategory()))
							|| (ICMSConstant.CHECKLIST_NON_BORROWER.equals(summaryList[ii].getCustCategory()))
							|| (ICMSConstant.CHECKLIST_JOINT_BORROWER.equals(summaryList[ii].getCustCategory()))) {
						customer = custProxy.getCustomer(summaryList[ii].getSubProfileID());
						summaryList[ii].setLegalID(String.valueOf(customer.getCMSLegalEntity().getLEReference()));
						summaryList[ii].setLegalName(customer.getCMSLegalEntity().getLegalName());
						summaryList[ii].setGovernLaw(law);
						summaryList[ii].setCustomerSegmentCode(customer.getCMSLegalEntity().getLegalConstitution());
						summaryList[ii].setSameCountryInd(isAccessAllowed(anIContext, summaryList[ii]));
					}
					else if (ICMSConstant.CHECKLIST_PLEDGER.equals(summaryList[ii].getCustCategory())) {
						DefaultLogger.debug(this, "PLEDGOR ID: " + summaryList[ii].getSubProfileID());
						pledgor = colProxy.getPledgor(summaryList[ii].getSubProfileID());
						summaryList[ii].setPledgorReference(String.valueOf(pledgor.getSysGenPledgorID()));
						summaryList[ii].setLegalID(String.valueOf(pledgor.getLegalID()));
						summaryList[ii].setLegalName(pledgor.getPledgorName());
						summaryList[ii].setGovernLaw(law);
						summaryList[ii].setCustomerSegmentCode(pledgor.getLegalType());
						summaryList[ii].setSameCountryInd(isAccessAllowed(anIContext, summaryList[ii]));
					}
					resultList.add(summaryList[ii]);
				}
			}
			// return summaryList;
			return (CCCheckListSummary[]) resultList.toArray(new CCCheckListSummary[0]);
		}
		catch (LimitException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryList", ex);
		}
		catch (CustomerException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryList", ex);
		}
		catch (CollateralException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryList", ex);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryList", ex);
		}
	}

	/**
	 * Get the list of c/c checklist summary info.
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListException on errors
	 */
	protected CCCheckListSummary[] getCCCheckListSummaryList(IContext anIContext, long aLimitProfileID,
			boolean aFullListInd) throws CheckListTemplateException, CheckListException {
		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = proxy.getLimitProfile(aLimitProfileID);
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer customer = custProxy.getCustomer(limitProfile.getCustomerID());
			ArrayList resultList = new ArrayList();
			CCCheckListSummary summary = new CCCheckListSummary();
			HashMap checkListMap = getCCCheckListStatus(aLimitProfileID, true);
			// DefaultLogger.debug(this, "HashMap: " + checkListMap);
			CCCheckListSummary[] summaryList = null;
			if (CheckListUtil.isInCountry(anIContext.getTeam(), limitProfile, null)) {
				summary = formulateCCCheckListSummary(anIContext, limitProfile, checkListMap, customer,
						ICMSConstant.CHECKLIST_MAIN_BORROWER);
				if (summary != null) {
					resultList.add(summary);
				}

				String NBChkLstKey = ICMSConstant.CHECKLIST_NON_BORROWER + customer.getCustomerID();
				if (checkListMap.containsKey(NBChkLstKey)) {
					CCCheckListSummary NBChkLstSummary = formulateCCCheckListSummary(anIContext, limitProfile,
							checkListMap, customer, ICMSConstant.CHECKLIST_NON_BORROWER);
					if (NBChkLstSummary != null) {
						resultList.add(NBChkLstSummary);
					}
				}

				getJointBorrowerCheckList(anIContext, limitProfile, checkListMap, resultList, false);
				getCoBorrowerPledgorCheckList(anIContext, limitProfile, checkListMap, resultList, false);
			}
			else {
				getCoBorrowerPledgorCheckList(anIContext, limitProfile, checkListMap, resultList, true);

			}
			summaryList = (CCCheckListSummary[]) resultList.toArray(new CCCheckListSummary[0]);

			if (!aFullListInd) {
				summaryList = filterOffDeletedCheckList(summaryList);
			}
			if ((summaryList != null) && (summaryList.length > 0)) {
				Arrays.sort(summaryList);
				// String[] param = { "CustCategoryNo", "LegalID" };
				// SortUtil.sortObject(summaryList, param);
			}

			return summaryList;
		}
		catch (CustomerException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryList", ex);
		}
		catch (LimitException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryList", ex);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryList", ex);
		}
	}

	protected FacilityCheckListSummary[] getFacilityCheckListSummaryList(IContext anIContext, long aLimitProfileID,
			boolean aFullListInd) throws CheckListTemplateException, CheckListException {
		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = proxy.getLimitProfile(aLimitProfileID);
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer customer = custProxy.getCustomer(limitProfile.getCustomerID());
			ArrayList resultList = new ArrayList();
			FacilityCheckListSummary summary = new FacilityCheckListSummary();
			HashMap checkListMap = getFacilityCheckListStatus(aLimitProfileID, true);
			// DefaultLogger.debug(this, "HashMap: " + checkListMap);
			FacilityCheckListSummary[] summaryList = null;
			if (CheckListUtil.isInCountry(anIContext.getTeam(), limitProfile, null)) {
				summary = formulateFacilityCheckListSummary(anIContext, limitProfile, checkListMap, customer,
						ICMSConstant.CHECKLIST_MAIN_BORROWER);
				if (summary != null) {
					resultList.add(summary);
				}

				String NBChkLstKey = ICMSConstant.CHECKLIST_NON_BORROWER + customer.getCustomerID();
				/*if (checkListMap.containsKey(NBChkLstKey)) {
					CCCheckListSummary NBChkLstSummary = formulateCCCheckListSummary(anIContext, limitProfile,
							checkListMap, customer, ICMSConstant.CHECKLIST_NON_BORROWER);
					if (NBChkLstSummary != null) {
						resultList.add(NBChkLstSummary);
					}
				}*/

				getJointBorrowerCheckList(anIContext, limitProfile, checkListMap, resultList, false);
//				getCoBorrowerPledgorCheckList(anIContext, limitProfile, checkListMap, resultList, false);
			}
			else {
				getCoBorrowerPledgorCheckList(anIContext, limitProfile, checkListMap, resultList, true);

			}
			summaryList = (FacilityCheckListSummary[]) resultList.toArray(new FacilityCheckListSummary[0]);

			if (!aFullListInd) {
				summaryList = filterOffDeletedCheckList(summaryList);
			}
			if ((summaryList != null) && (summaryList.length > 0)) {
				Arrays.sort(summaryList);
				// String[] param = { "CustCategoryNo", "LegalID" };
				// SortUtil.sortObject(summaryList, param);
			}

			return summaryList;
		}
		catch (CustomerException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryList", ex);
		}
		catch (LimitException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryList", ex);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryList", ex);
		}
	}
	private CCCheckListSummary[] filterOffDeletedCheckList(CCCheckListSummary[] aList) {
		if ((aList == null) || (aList.length == 0)) {
			return aList;
		}
		ArrayList resultList = new ArrayList();
		for (int ii = 0; ii < aList.length; ii++) {
			if (!ICMSConstant.STATE_CHECKLIST_DELETED.equals(aList[ii].getCheckListStatus())) {
				resultList.add(aList[ii]);
			}
		}
		return (CCCheckListSummary[]) resultList.toArray(new CCCheckListSummary[0]);
	}
	
	private FacilityCheckListSummary[] filterOffDeletedCheckList(FacilityCheckListSummary[] aList) {
		if ((aList == null) || (aList.length == 0)) {
			return aList;
		}
		ArrayList resultList = new ArrayList();
		for (int ii = 0; ii < aList.length; ii++) {
			if (!ICMSConstant.STATE_CHECKLIST_DELETED.equals(aList[ii].getCheckListStatus())) {
				resultList.add(aList[ii]);
			}
		}
		return (FacilityCheckListSummary[]) resultList.toArray(new FacilityCheckListSummary[0]);
	}

	/**
	 * Get the list of c/c checklist summary info for non borrower
	 * @param anIContext of IContext type
	 * @param aCustomerID of long type
	 * @return CCCheckListSummary[] - the list of c/c checklist summaries
	 * @throws CheckListException on errors
	 */
	public CCCheckListSummary[] getCCCheckListSummaryListForNonBorrower(IContext anIContext, long aLimitProfileID,
			long aCustomerID, boolean isNBCheckListOnly) throws CheckListTemplateException, CheckListException {
		try {
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer customer = custProxy.getCustomer(aCustomerID);

			ICheckListTemplateProxyManager templateProxy = CheckListTemplateProxyManagerFactory
					.getCheckListTemplateProxyManager();
			String law = null;

			HashMap checkListMap = getCCCheckListStatusForNonBorrower(aLimitProfileID, aCustomerID);
			DefaultLogger.debug(this, "############### <getCCCheckListSummaryListForNonBorrower> HashMap size: "
					+ checkListMap.size());

			String key = ICMSConstant.CHECKLIST_NON_BORROWER + customer.getCustomerID();
			CheckListSearchResult checkList = (CheckListSearchResult) checkListMap.get(key);

			ArrayList allSummaryList = new ArrayList();

			if ((checkList == null) || !ICMSConstant.STATE_DELETED.equals(checkList.getCheckListStatus())) {
				CCCheckListSummary NBSummaryList = new CCCheckListSummary();

				IBookingLocation location = null;
				if (checkList != null) {
					NBSummaryList.setCheckListID(checkList.getCheckListID());
					NBSummaryList.setCheckListStatus(checkList.getCheckListStatus());
					NBSummaryList.setAllowDeleteInd(checkList.getAllowDeleteInd());
					NBSummaryList.setTrxID(checkList.getTrxID());
					NBSummaryList.setTrxStatus(checkList.getTrxStatus());
					NBSummaryList.setTrxFromState(checkList.getTrxFromState());
					NBSummaryList.setDisableTaskInd(checkList.getDisableTaskInd());
					location = checkList.getCheckListLocation();
				}
				if ((location == null) || (location.getCountryCode() == null)) {
					location = getDocumentLocation(null, ICMSConstant.CHECKLIST_NON_BORROWER, customer.getCustomerID(),
							customer.getOriginatingLocation());
				}

				law = templateProxy.getLaw(NBSummaryList.getDomicileCtry());
				NBSummaryList.setCustCategory(ICMSConstant.CHECKLIST_NON_BORROWER);
				NBSummaryList.setSubProfileID(customer.getCustomerID());
				NBSummaryList.setLegalID(customer.getCMSLegalEntity().getLEReference());
				NBSummaryList.setLegalName(customer.getCMSLegalEntity().getLegalName());
				NBSummaryList.setTaskTrx(getCCTaskTrx(ICMSConstant.LONG_INVALID_VALUE, aCustomerID,
						ICMSConstant.CHECKLIST_NON_BORROWER));
				NBSummaryList.setDomicileCtry(location.getCountryCode());
				NBSummaryList.setOrgCode(location.getOrganisationCode());
				NBSummaryList.setCustomerSegmentCode(customer.getCMSLegalEntity().getLegalConstitution());
				NBSummaryList.setGovernLaw(law);
				NBSummaryList.setSameCountryInd(isAccessAllowed(anIContext, NBSummaryList));

				allSummaryList.add(NBSummaryList);
			}

			/*** Previously DELETED CC Checklist ***/
			if ((aLimitProfileID != ICMSConstant.LONG_INVALID_VALUE) && !isNBCheckListOnly) {
				CCCheckListSummary[] deletedSummaryList = getDeletedCCCheckListSummaryList(anIContext, aLimitProfileID);
				DefaultLogger.debug(this, "###### <getCCCheckListSummaryListForNonBorrower> deletedSummaryList.length "
						+ deletedSummaryList.length);
				if (deletedSummaryList != null) {
					allSummaryList.addAll(Arrays.asList(deletedSummaryList));
				}
			}

			CCCheckListSummary[] checkListSummaryList = (CCCheckListSummary[]) allSummaryList
					.toArray(new CCCheckListSummary[0]);

			if ((checkListSummaryList != null) && (checkListSummaryList.length > 0)) {
				Arrays.sort(checkListSummaryList);

				// String[] param = { "CustCategoryNo", "LegalID" };
				// SortUtil.sortObject(checkListSummaryList, param);
			}

			return checkListSummaryList;
		}
		catch (CustomerException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryList", ex);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in getCCCheckListSummaryList", ex);
		}
	}

	/**
	 * Get the list of c/c checklist summary info for non borrower including
	 * those that are deleted
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return HashMap - the list of c/c checklist summaries
	 * @throws CheckListException on errors
	 */
	public HashMap getAllCCCheckListSummaryListForNonBorrower(IContext anIContext, long aLimitProfileID,
			long aCustomerID) throws CheckListTemplateException, CheckListException {
		CCCheckListSummary[] normalList = getCCCheckListSummaryListForNonBorrower(anIContext, aLimitProfileID,
				aCustomerID, true);
		CCCheckListSummary[] deletedList = null;
		if (ICMSConstant.LONG_MIN_VALUE == aLimitProfileID) {
			deletedList = getDeletedCCCheckListSummaryListForNonBorrower(anIContext, aCustomerID);
		}
		else {
			deletedList = getDeletedCCCheckListSummaryList(anIContext, aLimitProfileID);
		}
		HashMap map = new HashMap();
		if ((normalList != null) && (normalList.length > 0)) {
			map.put(ICMSConstant.NORMAL_LIST, normalList);
		}
		if ((deletedList != null) && (deletedList.length > 0)) {
			map.put(ICMSConstant.DELETED_LIST, deletedList);
		}
		return map;
	}

	/**
	 * Helper method to formulate a cc checklist summary based on the
	 * parameters.
	 * 
	 * @param anIContext
	 * @param anILimitProfile
	 * @param aCheckListMap
	 * @param anICMSCustomer
	 * @param aCustomerType
	 * @return
	 * @throws CheckListException
	 */
	private CCCheckListSummary formulateCCCheckListSummary(IContext anIContext, ILimitProfile anILimitProfile,
			HashMap aCheckListMap, ICMSCustomer anICMSCustomer, String aCustomerType)
			throws CheckListTemplateException, CheckListException {
		String key = aCustomerType + anICMSCustomer.getCustomerID();
		CheckListSearchResult checkList = (CheckListSearchResult) aCheckListMap.get(key);
		return formulateCCCheckListSummary(anIContext, anILimitProfile, checkList, anICMSCustomer, aCustomerType);
	}
	
	private FacilityCheckListSummary formulateFacilityCheckListSummary(IContext anIContext, ILimitProfile anILimitProfile,
			HashMap aCheckListMap, ICMSCustomer anICMSCustomer, String aCustomerType)
			throws CheckListTemplateException, CheckListException {
		String key = aCustomerType + anICMSCustomer.getCustomerID();
		CheckListSearchResult checkList = (CheckListSearchResult) aCheckListMap.get(key);
		return formulateFacilityCheckListSummary(anIContext, anILimitProfile, checkList, anICMSCustomer, aCustomerType);
	}

	/**
	 * Helper method to formulate a cc checklist summary based on the
	 * parameters.
	 * 
	 * @param anIContext
	 * @param anILimitProfile
	 * @param chklistSearchResult
	 * @param anICMSCustomer
	 * @return
	 * @throws CheckListException
	 */
	private CCCheckListSummary formulateCCCheckListSummary(IContext anIContext, ILimitProfile anILimitProfile,
			CheckListSearchResult chklistSearchResult, ICMSCustomer anICMSCustomer) throws CheckListTemplateException,
			CheckListException {
		return formulateCCCheckListSummary(anIContext, anILimitProfile, chklistSearchResult, anICMSCustomer,
				((CCCheckListSearchResult) chklistSearchResult).getCustomerType());
	}

	/**
	 * Helper method to formulate a cc checklist summary based on the
	 * parameters.
	 * 
	 * @param anIContext
	 * @param anILimitProfile
	 * @param chklistSearchResult
	 * @param anICMSCustomer
	 * @param aCustomerType
	 * @return
	 * @throws CheckListException
	 */
	private CCCheckListSummary formulateCCCheckListSummary(IContext anIContext, ILimitProfile anILimitProfile,
			CheckListSearchResult chklistSearchResult, ICMSCustomer anICMSCustomer, String aCustomerType)
			throws CheckListTemplateException, CheckListException {

		CCCheckListSummary summary = new CCCheckListSummary();
		summary.setCustCategory(aCustomerType);
		summary.setSubProfileID(anICMSCustomer.getCustomerID());
		summary.setLegalID(anICMSCustomer.getCMSLegalEntity().getLEReference());
		summary.setLegalName(anICMSCustomer.getCMSLegalEntity().getLegalName());
		summary.setCustomerSegmentCode(anICMSCustomer.getCMSLegalEntity().getLegalConstitution());
		summary.setApplicationType(anILimitProfile.getApplicationType());
		IBookingLocation location = null;
		if (chklistSearchResult != null) {
			summary.setCheckListID(chklistSearchResult.getCheckListID());
			summary.setCheckListStatus(chklistSearchResult.getCheckListStatus());
			summary.setAllowDeleteInd(chklistSearchResult.getAllowDeleteInd());
			summary.setTrxID(chklistSearchResult.getTrxID());
			summary.setTrxStatus(chklistSearchResult.getTrxStatus());
			summary.setTrxFromState(chklistSearchResult.getTrxFromState());
			summary.setDisableTaskInd(chklistSearchResult.getDisableTaskInd());
			location = chklistSearchResult.getCheckListLocation();
		}
		else {
			setSummaryTrxValue((ICheckListSummary) summary, anILimitProfile.getLimitProfileID(), anICMSCustomer
					.getCustomerID(), ICMSConstant.LONG_INVALID_VALUE, aCustomerType);
		}

		if ((location == null) || (location.getCountryCode() == null)) {
			location = getDocumentLocation(anILimitProfile, aCustomerType, anICMSCustomer.getCustomerID(),
					anICMSCustomer.getOriginatingLocation());
		}

		summary.setDomicileCtry(location.getCountryCode());
		summary.setTaskTrx(getCCTaskTrx(anILimitProfile.getLimitProfileID(), anICMSCustomer.getCustomerID(),
				aCustomerType));
		summary.setOrgCode(location.getOrganisationCode());
		summary.setGovernLaw(anILimitProfile.getApplicationLawType());
		summary.setSameCountryInd(isAccessAllowed(anIContext, summary));
		return summary;
	}

	private FacilityCheckListSummary formulateFacilityCheckListSummary(IContext anIContext, ILimitProfile anILimitProfile,
			CheckListSearchResult chklistSearchResult, ICMSCustomer anICMSCustomer, String aCustomerType)
			throws CheckListTemplateException, CheckListException {

		FacilityCheckListSummary summary = new FacilityCheckListSummary();
		summary.setCustCategory(aCustomerType);
		summary.setSubProfileID(anICMSCustomer.getCustomerID());
		//summary.setLegalID(anICMSCustomer.getCMSLegalEntity().getLEReference());
		//summary.setLegalName(anICMSCustomer.getCMSLegalEntity().getLegalName());
		//summary.setCustomerSegmentCode(anICMSCustomer.getCMSLegalEntity().getLegalConstitution());
		summary.setApplicationType(anILimitProfile.getApplicationType());
		IBookingLocation location = null;
		if (chklistSearchResult != null) {
			summary.setCheckListID(chklistSearchResult.getCheckListID());
			summary.setCheckListStatus(chklistSearchResult.getCheckListStatus());
			summary.setAllowDeleteInd(chklistSearchResult.getAllowDeleteInd());
			summary.setTrxID(chklistSearchResult.getTrxID());
			summary.setTrxStatus(chklistSearchResult.getTrxStatus());
			summary.setTrxFromState(chklistSearchResult.getTrxFromState());
			summary.setDisableTaskInd(chklistSearchResult.getDisableTaskInd());
			location = chklistSearchResult.getCheckListLocation();
		}
		else {
			setSummaryTrxValue((ICheckListSummary) summary, anILimitProfile.getLimitProfileID(), anICMSCustomer
					.getCustomerID(), ICMSConstant.LONG_INVALID_VALUE, aCustomerType);
		}

		if ((location == null) || (location.getCountryCode() == null)) {
			location = getDocumentLocation(anILimitProfile, aCustomerType, anICMSCustomer.getCustomerID(),
					anICMSCustomer.getOriginatingLocation());
		}

		//summary.setDomicileCtry(location.getCountryCode());
		summary.setTaskTrx(getCCTaskTrx(anILimitProfile.getLimitProfileID(), anICMSCustomer.getCustomerID(),
				aCustomerType));
		//summary.setOrgCode(location.getOrganisationCode());
		///summary.setGovernLaw(anILimitProfile.getApplicationLawType());
		summary.setSameCountryInd(true);
		return summary;
	}
	private ICMSTrxValue getCCTaskTrx(long aLimitProfileID, long aCustomerID, String aCustomerType)
			throws CheckListException {
		ICCTaskTrxValue ccTaskTrxValue;
		ICollaborationTaskProxyManager proxyTask = CollaborationTaskProxyManagerFactory.getProxyManager();
		try {
			ccTaskTrxValue = proxyTask.getCCTaskTrxValue(aLimitProfileID, aCustomerType, aCustomerID, null);
		}
		catch (CollaborationTaskException e) {
			throw new CheckListException("Caught CollaborationTaskException in existTask", e);
		}
		return ccTaskTrxValue;
	}

	private ICMSTrxValue getCollateralTaskTrxValueTaskTrx(long aLimitProfileID, long aCollateralID,
			String aCollateralLocation) throws CheckListException {
		ICollateralTaskTrxValue collateralTaskTrxValue;
		ICollaborationTaskProxyManager proxyTask = CollaborationTaskProxyManagerFactory.getProxyManager();
		try {
			collateralTaskTrxValue = proxyTask.getCollateralTaskTrxValue(aLimitProfileID, aCollateralID,
					aCollateralLocation);
		}
		catch (CollaborationTaskException e) {
			throw new CheckListException("Caught CollaborationTaskException in existTask", e);
		}
		return collateralTaskTrxValue;
	}

	private ICMSTrxValue getCCCheckListTrx(long aLimitProfileID, long aCustomerID, String aCustomerType) {

		String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_PENDING_UPDATE,
				ICMSConstant.STATE_ACTIVE, ICMSConstant.STATE_REJECTED };

		ICCCheckListOwner owner = new OBCCCheckListOwner(aLimitProfileID, aCustomerID, aCustomerType);

		CheckListSearchResult[] resultList = new CheckListSearchResult[0];
		try {
			resultList = getCheckList(owner, statusList);
		}
		catch (SearchDAOException e) {

		}
		catch (CheckListException e) {

		}
		if ((resultList == null) || (resultList.length == 0)) {

			return null;
		}
		if (resultList.length != 1) {

		}
		String trxID = resultList[0].getTrxID();

		ICheckListTrxValue checkListTrxValue = null;
		try {
			checkListTrxValue = getCheckListByTrxID(trxID);
		}
		catch (CheckListException e) {
			e.printStackTrace();
		}

		return checkListTrxValue;
	}

	private ICMSTrxValue getCollateralCheckListTrx(long aLimitProfileID, long aCollateralID) {

		ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(aLimitProfileID, aCollateralID);
		String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_ACTIVE,
				ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT,
				ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ, ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
				ICMSConstant.STATE_PENDING_MGR_VERIFY, ICMSConstant.STATE_PENDING_AUTH,
				ICMSConstant.STATE_PENDING_OFFICE, ICMSConstant.STATE_PENDING_VERIFY,
				ICMSConstant.STATE_OFFICER_REJECTED };

		CheckListSearchResult[] resultList = new CheckListSearchResult[0];
		try {
			resultList = getCheckList(owner, statusList);
		}
		catch (SearchDAOException e) {

		}
		catch (CheckListException e) {

		}
		if ((resultList == null) || (resultList.length == 0)) {

			return null;
		}
		if (resultList.length != 1) {

		}
		String trxID = resultList[0].getTrxID();

		ICheckListTrxValue checkListTrxValue = null;
		try {
			checkListTrxValue = getCheckListByTrxID(trxID);
		}
		catch (CheckListException e) {
			e.printStackTrace();
		}

		return checkListTrxValue;
	}

	private void getCoBorrowerPledgorCheckList(IContext anIContext, ILimitProfile anILimitProfile,
			HashMap aCheckListMap, ArrayList aList, boolean aFilteredInd) throws CheckListTemplateException,
			CheckListException {
		try {
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer customer = null;
			List legalIdList = new ArrayList();
			List pledgorIDList = new ArrayList();

			if (aList.size() > 0) {
				CCCheckListSummary mainSummary = (CCCheckListSummary) aList.get(0);
				DefaultLogger.debug(this, "LegalID: " + mainSummary.getLegalID());
				legalIdList.add(mainSummary.getLegalID());

				// add JointBorrower to the legal id list as well
				for (int i = 0; i < aList.size(); i++) {
					CCCheckListSummary summary = (CCCheckListSummary) aList.get(i);
					if (ICMSConstant.CHECKLIST_JOINT_BORROWER.equals(summary.getCustCategory())) {
						DefaultLogger.debug(this, ">>>>>> Joint Borrower LegalID: " + summary.getLegalID());
						legalIdList.add(summary.getLegalID());
					}
				}
			}
			else {
				customer = custProxy.getCustomer(anILimitProfile.getCustomerID());
				legalIdList.add(customer.getCMSLegalEntity().getLEReference());
			}
			// TODO: if all limits deleted, no pledgor and coborrower checklist
			// will be showing
			// ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
			ILimit[] limitList = anILimitProfile.getLimits();
			if ((limitList == null) || (limitList.length == 0)) {
				return;
			}
			CCCheckListSummary summary = new CCCheckListSummary();
			ICoBorrowerLimit[] coBorrowerLimitList = getAllCoBorrowerLimitList(limitList, aCheckListMap);
			ICollateralPledgor[] pledgorList = getAllPledgorList(limitList, aCheckListMap);

			for (int ii = 0; ii < coBorrowerLimitList.length; ii++) {
				long customerID = coBorrowerLimitList[ii].getCustomerID();
				if (aCheckListMap.get(new Long(customerID)) == null) {
					customer = custProxy.getCustomer(customerID);

					if (!legalIdList.contains(customer.getCMSLegalEntity().getLEReference())) {
						DefaultLogger.debug(this, "co-borrower: " + customer.getCMSLegalEntity().getLEReference());
						legalIdList.add(customer.getCMSLegalEntity().getLEReference());
						summary = formulateCCCheckListSummary(anIContext, anILimitProfile, aCheckListMap, customer,
								ICMSConstant.CHECKLIST_CO_BORROWER);
						if (summary != null) {
							if ((!aFilteredInd) || isInSameCountry(anIContext, anILimitProfile, summary)) {
								aList.add(summary);
							}
						}
					}
				}
			}

			for (int kk = 0; kk < pledgorList.length; kk++) {
				if (((pledgorList[kk].getLegalID() != null) && (pledgorList[kk].getLegalID().trim().length() > 0))) {
					if (!legalIdList.contains(pledgorList[kk].getLegalID())) {
						DefaultLogger.debug(this, "Pledgor LEID: " + pledgorList[kk].getLegalID());
						legalIdList.add(pledgorList[kk].getLegalID());
						pledgorIDList.add(new Long(pledgorList[kk].getPledgorID()));
						summary = formulateCCCheckListSummary(anIContext, anILimitProfile, aCheckListMap,
								pledgorList[kk]);
						if (summary != null) {
							if ((!aFilteredInd) || isInSameCountry(anIContext, anILimitProfile, summary)) {
								aList.add(summary);
							}
						}
					}
				}
				else {
					DefaultLogger.debug(this, "Pledgor does not have legal ID !!!");
					if (!pledgorIDList.contains(new Long(pledgorList[kk].getPledgorID()))) {
						DefaultLogger.debug(this, "PledgorID: " + pledgorList[kk].getPledgorID());
						pledgorIDList.add(new Long(pledgorList[kk].getPledgorID()));
						summary = formulateCCCheckListSummary(anIContext, anILimitProfile, aCheckListMap,
								pledgorList[kk]);
						if (summary != null) {
							if ((!aFilteredInd) || isInSameCountry(anIContext, anILimitProfile, summary)) {
								aList.add(summary);
							}
						}
					}
				}
			}
			DefaultLogger.debug(this, "SIZE: " + aList.size());
		}
		catch (CustomerException ex) {
			rollback();
			throw new CheckListException("Exception in getCoBorrowerPledgorCheckList", ex);
		}
	}

	private ICoBorrowerLimit[] getCoBorrowerLimitList(ILimit[] anILimitList) {
		ArrayList result = new ArrayList();
		ArrayList resultID = new ArrayList();
		if ((anILimitList != null) && (anILimitList.length > 0)) {
			for (int ii = 0; ii < anILimitList.length; ii++) {
				ICoBorrowerLimit[] coBorrowerLimitList = anILimitList[ii].getNonDeletedCoBorrowerLimits();
				if ((coBorrowerLimitList != null) && (coBorrowerLimitList.length > 0)) {
					for (int jj = 0; jj < coBorrowerLimitList.length; jj++) {
						if (!resultID.contains(new Long(coBorrowerLimitList[jj].getLimitID()))) {
							resultID.add(new Long(coBorrowerLimitList[jj].getLimitID()));
							result.add(coBorrowerLimitList[jj]);
						}
					}
				}
			}

		}
		ICoBorrowerLimit[] coBorrowerList = (ICoBorrowerLimit[]) result.toArray(new ICoBorrowerLimit[0]);
		if ((coBorrowerList != null) && (coBorrowerList.length > 1)) {
			Arrays.sort(coBorrowerList);
		}
		return coBorrowerList;
	}

	// TODO : Performance - to get from the DAO instead
	private ICollateralPledgor[] getPledgorList(ILimit[] anILimitList) throws CheckListException {
		ArrayList result = new ArrayList();
		ArrayList resultID = new ArrayList();
		ArrayList collateralID = new ArrayList();
		ICollateralAllocation[] colAllocationList = null;
		try {
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			for (int ii = 0; ii < anILimitList.length; ii++) {
				colAllocationList = anILimitList[ii].getNonDeletedCollateralAllocations();
				if (colAllocationList != null) {
					int colLen = colAllocationList.length;
					if (colLen > 1) {
						Arrays.sort(colAllocationList);
					}
					for (int jj = 0; jj < colLen; jj++) {
						Long colID = new Long(colAllocationList[jj].getCollateral().getCollateralID());
						if (!collateralID.contains(colID)) {
							collateralID.add(colID);
							ICollateralPledgor[] pledgorList = proxy.getCollateralPledgors(colID.longValue());
							if (pledgorList != null) {
								int pledgorLen = pledgorList.length;
								if (pledgorLen > 1) {
									Arrays.sort(pledgorList);
								}
								for (int kk = 0; kk < pledgorLen; kk++) {
									if (!resultID.contains(new Long(pledgorList[kk].getPledgorID()))) {
										DefaultLogger.debug(this, "Pledgor status indicator="
												+ pledgorList[kk].getPledgorStatus());
										// bernard - if-condition added to
										// filter off deleted pledgors
										// (CMSSP-153)
										// bernard - added null condition to
										// correctly retrieve all active
										// pledgors (CMS-1478)
										if ((pledgorList[kk].getSCIPledgorMapStatus() == null)
												|| ((pledgorList[kk].getSCIPledgorMapStatus() != null) && !pledgorList[kk]
														.getSCIPledgorMapStatus().equals("D"))) {
											DefaultLogger.debug(this, "Adding Pledgor ID: "
													+ pledgorList[kk].getPledgorID());
											resultID.add(new Long(pledgorList[kk].getPledgorID()));
											result.add(pledgorList[kk]);
										}
									}
								}
							}
						}
					}
				}
			}
			ICollateralPledgor[] collateralPledgorList = (ICollateralPledgor[]) result
					.toArray(new ICollateralPledgor[0]);
			/*
			 * if (collateralPledgorList != null && collateralPledgorList.length
			 * > 1) { String[] param = {"PledgorID"};
			 * SortUtil.sortObject(collateralPledgorList, param); }
			 */
			return collateralPledgorList;
		}
		catch (CollateralException ex) {
			rollback();
			throw new CheckListException("Exception in getCoBorrowerPledgorCheckList", ex);
		}
	}

	private void getJointBorrowerCheckList(IContext anIContext, ILimitProfile anILimitProfile, HashMap aCheckListMap,
			ArrayList aList, boolean aFilteredInd) throws CheckListTemplateException, CheckListException {
		try {
			// use limit profile to get all the joint borrowers
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			List jointBorrowerList = custProxy.getOnlyJointBorrowerList(anILimitProfile.getLimitProfileID());
			CCCheckListSummary summary = new CCCheckListSummary();
			DefaultLogger.debug(this, ">>>>>>>>>>> filterInd = " + aFilteredInd);

			for (int i = 0; i < jointBorrowerList.size(); i++) {
				ICMSCustomer jb = (ICMSCustomer) jointBorrowerList.get(i);
				summary = formulateCCCheckListSummary(anIContext, anILimitProfile, aCheckListMap, jb,
						ICMSConstant.CHECKLIST_JOINT_BORROWER);
				if (summary != null) {
					boolean isInSameCountry = isInSameCountry(anIContext, anILimitProfile, summary);
					DefaultLogger.debug(this, ">>>>>>>>>>> isInSameCountry = " + isInSameCountry);
					if ((!aFilteredInd) || isInSameCountry) {
						aList.add(summary);
					}
				}
			}

		}
		catch (Exception ex) {
			rollback();
			throw new CheckListException("Exception in getJointBorrowerCheckList", ex);
		}
	}

	/**
	 * Determines if the security is in the same country as the user.
	 * @param anIContext the context object containing user information
	 * @param anILimitProfile the limit profile
	 * @param aSummary the checklist summary
	 * @return boolean - true if security is in the same country as the user,
	 *         false otherwise
	 * @throws CheckListException on errors
	 */
	private boolean isInSameCountry(IContext anIContext, ILimitProfile anILimitProfile,
			CollateralCheckListSummary aSummary) throws CheckListException {
		return isInSameCountry(anIContext, anILimitProfile, aSummary.getCollateralID(), aSummary
				.getCollateralLocation());
	}
	
	
	private boolean isInSameCountry(IContext anIContext, ILimitProfile anILimitProfile,
			CAMCheckListSummary aSummary) throws CheckListException {
		return isInSameCountry(anIContext, anILimitProfile, aSummary.getCollateralID(), aSummary
				.getCollateralLocation());
	}

	private boolean isInSameCountry(IContext anIContext, ILimitProfile anILimitProfile,
			FacilityCheckListSummary aSummary) throws CheckListException {
		return isInSameCountry(anIContext, anILimitProfile, aSummary.getCollateralID(), aSummary
				.getCollateralLocation());
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
		ITeam team = anIContext.getTeam();
		String[] countryList = team.getCountryCodes();
		// String[] orgCodeList = team.getOrganisationCodes();
		if (countryList == null) {
			return false;
		}
		// String secLoc = aSummary.getCollateralLocation();
		String secLoc = collateralLocation;
		DefaultLogger.debug(this, "Collateral location: " + secLoc);
		boolean sameCtry = false;
		for (int ii = 0; ii < countryList.length; ii++) {
			if (countryList[ii].equals(secLoc)) {
				if (CheckListUtil.isInCountry(anIContext.getTeam(), anILimitProfile, null)) {
					return true;
				}
				sameCtry = true;
			}
		}

		if (!sameCtry) {
			return false;
		}

		ICollateralTask colTask = getCollateralTask(anILimitProfile.getLimitProfileID(), collateralID,
				collateralLocation);
		if (colTask != null) {
			return true;
		}
		return false;
	}

	/**
	 * Determines if the c/c checklist is in the same country as the user.
	 * @param anIContext the context object containing user information
	 * @param anILimitProfile the limit profile
	 * @param aSummary the checklist summary
	 * @return boolean - true if c/c checklist is in the same country as the
	 *         user, false otherwise
	 * @throws CheckListException on errors
	 */
	private boolean isInSameCountry(IContext anIContext, ILimitProfile anILimitProfile, CCCheckListSummary aSummary)
			throws CheckListException {
		return isInSameCountry(anIContext, anILimitProfile, aSummary.getSubProfileID(), aSummary.getDomicileCtry(),
				aSummary.getCustCategory());
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
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer customer = custProxy.getCustomer(anILimitProfile.getCustomerID());

			ITeam team = anIContext.getTeam();
			String[] countryList = team.getCountryCodes();
			String[] orgCodeList = team.getOrganisationCodes();
			if (countryList == null) {
				return false;
			}
			// String domCtry = aSummary.getDomicileCtry();
			String domCtry = domicileCountry;
			DefaultLogger.debug(this, "Summary Category: " + custCategory);
			DefaultLogger.debug(this, "Summary Country: " + domCtry);
			boolean sameCtry = false;
			for (int ii = 0; ii < countryList.length; ii++) {
				if (countryList[ii].equals(domCtry)) {
					if (CheckListUtil.isInCountry(team, anILimitProfile, customer)) {
						return true;
					}
					sameCtry = true;
				}
			}

			if (!sameCtry) {
				return false;
			}

			ICCTask ccTask = getCCTask(anILimitProfile.getLimitProfileID(), custCategory, customerID);
			if (ccTask != null) {
				String orgCode = ccTask.getOrgCode();
				for (int ii = 0; ii < orgCodeList.length; ii++) {
					if (orgCodeList[ii].equals(orgCode)) {
						return true;
					}
				}
			}
			return false;
		}
		catch (Exception e) {
			throw new CheckListException("Exception in isInSameCountry" + e);
		}
	}

	/**
	 * Helper method to formulate a cc checklist summary object based on the
	 * parameters.
	 * 
	 * @param anIContext
	 * @param anILimitProfile
	 * @param aCheckListMap
	 * @param anIPledgor
	 * @return
	 * @throws CheckListException
	 */
	private CCCheckListSummary formulateCCCheckListSummary(IContext anIContext, ILimitProfile anILimitProfile,
			HashMap aCheckListMap, IPledgor anIPledgor) throws CheckListTemplateException, CheckListException {
		String key = ICMSConstant.CHECKLIST_PLEDGER + anIPledgor.getPledgorID();
		DefaultLogger.debug(this, "KEY: " + key);
		CheckListSearchResult checkList = (CheckListSearchResult) aCheckListMap.get(key);
		return formulateCCCheckListSummary(anIContext, anILimitProfile, checkList, anIPledgor);
	}

	/**
	 * Helper method to formulate a cc checklist summary object base on the
	 * parameters.
	 * 
	 * @param anIContext
	 * @param anILimitProfile
	 * @param chklistSearchResult
	 * @param anIPledgor
	 * @return
	 * @throws CheckListException
	 */
	private CCCheckListSummary formulateCCCheckListSummary(IContext anIContext, ILimitProfile anILimitProfile,
			CheckListSearchResult chklistSearchResult, IPledgor anIPledgor) throws CheckListTemplateException,
			CheckListException {

		CCCheckListSummary summary = new CCCheckListSummary();
		summary.setCustCategory(ICMSConstant.CHECKLIST_PLEDGER);
		summary.setSubProfileID(anIPledgor.getPledgorID());
		summary.setLegalID(String.valueOf(anIPledgor.getLegalID()));
		summary.setLegalName(anIPledgor.getPledgorName());
		summary.setApplicationType(anILimitProfile.getApplicationType());

		IBookingLocation location = null;
		if (chklistSearchResult != null) {
			summary.setCheckListID(chklistSearchResult.getCheckListID());
			summary.setCheckListStatus(chklistSearchResult.getCheckListStatus());
			summary.setAllowDeleteInd(chklistSearchResult.getAllowDeleteInd());
			summary.setTrxID(chklistSearchResult.getTrxID());
			summary.setTrxStatus(chklistSearchResult.getTrxStatus());
			summary.setTrxFromState(chklistSearchResult.getTrxFromState());
			summary.setDisableTaskInd(chklistSearchResult.getDisableTaskInd());
			location = chklistSearchResult.getCheckListLocation();
		}
		else {
			setSummaryTrxValue((ICheckListSummary) summary, anILimitProfile.getLimitProfileID(), anIPledgor
					.getPledgorID(), ICMSConstant.LONG_INVALID_VALUE, ICMSConstant.CHECKLIST_PLEDGER);
		}

		if ((location == null) || (location.getCountryCode() == null)) {
			location = getDocumentLocation(anILimitProfile, ICMSConstant.CHECKLIST_PLEDGER, anIPledgor.getPledgorID(),
					anIPledgor.getBookingLocation());
		}

		summary.setTaskTrx(getCCTaskTrx(anILimitProfile.getLimitProfileID(), anIPledgor.getPledgorID(),
				ICMSConstant.CHECKLIST_PLEDGER));
		summary.setDomicileCtry(location.getCountryCode());
		summary.setOrgCode(location.getOrganisationCode());
		summary.setCustomerSegmentCode(anIPledgor.getLegalType());
		summary.setGovernLaw(anILimitProfile.getApplicationLawType());
		summary.setPledgorReference(String.valueOf(anIPledgor.getSysGenPledgorID()));
		summary.setSameCountryInd(isAccessAllowed(anIContext, summary));
		return summary;
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
	public CollateralCheckListSummary[] getCollateralCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListException {
		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = proxy.getLimitProfile(aLimitProfileID);
			return getCollateralCheckListSummaryList(anIContext, limitProfile);
		}
		catch (LimitException ex) {
			rollback();
			throw new CheckListException("Exception in getCollateralCheckListSummaryList", ex);
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
//	public CAMCheckListSummary[] getCAMCheckListSummaryList(IContext anIContext, long aLimitProfileID)
//			throws CheckListException {
//		try {
//			ILimitProxy proxy = LimitProxyFactory.getProxy();
//			ILimitProfile limitProfile = proxy.getLimitProfile(aLimitProfileID);
//			return getCAMCheckListSummaryList(anIContext, limitProfile);
//		}
//		catch (LimitException ex) {
//			rollback();
//			throw new CheckListException("Exception in getCollateralCheckListSummaryList", ex);
//		}
//	}
	/**
	 * Get the list of collateral checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param aLimitProfileID of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 */
	/*
	 * private CollateralCheckListSummary[]
	 * getFilteredCollateralCheckListSummaryList(IContext anIContext, long
	 * aLimitProfileID) throws CheckListException { return
	 * getFilteredCollateralCheckListSummaryList(anIContext, aLimitProfileID,
	 * false); }
	 */

	public CollateralCheckListSummary[] getFilteredCollateralCheckListSummaryList(IContext anIContext,
			long aLimitProfileID, boolean aCustodianInd) throws CheckListException {
		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = proxy.getLimitProfile(aLimitProfileID);
			return getCollateralCheckListSummaryList(anIContext, limitProfile, true, true, aCustodianInd);
		}
		catch (LimitException ex) {
			rollback();
			throw new CheckListException("Exception in getFilteredCollateralCheckListSummaryList", ex);
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
		CollateralCheckListSummary[] summaryList = getFilteredCollateralCheckListSummaryList(anIContext,
				aLimitProfileID, aCustodianInd);
		if ((summaryList == null) || (summaryList.length == 0)) {
			return null;
		}
		ArrayList normalList = new ArrayList();
		ArrayList deletedList = new ArrayList();
		for (int ii = 0; ii < summaryList.length; ii++) {
			if (ICMSConstant.STATE_DELETED.equals(summaryList[ii].getCheckListStatus())) {
				deletedList.add(summaryList[ii]);
			}
			else {
				normalList.add(summaryList[ii]);
			}
		}

		HashMap map = new HashMap();
		if (normalList.size() > 0) {
			map.put(ICMSConstant.NORMAL_LIST, normalList.toArray(new CollateralCheckListSummary[0]));
		}
		if (deletedList.size() > 0) {
			map.put(ICMSConstant.DELETED_LIST, deletedList.toArray(new CollateralCheckListSummary[0]));
		}
		return map;
	}

	/**
	 * Get the list of collateral checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param anILimitProfile of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 */
	public CollateralCheckListSummary[] getCollateralCheckListSummaryList(IContext anIContext,
			ILimitProfile anILimitProfile) throws CheckListException {
		DefaultLogger.debug(this, "<<<<<<< entering getCollateralCheckListSummaryList... ");
		return getCollateralCheckListSummaryList(anIContext, anILimitProfile, false, true, false);
	}
	

	/**
	 * Get the list of collateral checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param anILimitProfile of long type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 */
//	public CAMCheckListSummary[] getCAMCheckListSummaryList(IContext anIContext,
//			ILimitProfile anILimitProfile) throws CheckListException {
//		DefaultLogger.debug(this, "<<<<<<< entering getCollateralCheckListSummaryList... ");
//		return getCAMCheckListSummaryList(anIContext, anILimitProfile, false, true, false);
//	}

	/*
	 * private CollateralCheckListSummary[]
	 * getCollateralCheckListSummaryList(IContext anIContext, ILimitProfile
	 * anILimitProfile, boolean aCustodianInd) throws CheckListException {
	 * return getCollateralCheckListSummaryList(anIContext, anILimitProfile,
	 * false, true, aCustodianInd); }
	 */

	public HashMap getAllCollateralCheckListSummaryList(IContext anIContext, long aLimitProfileID)
			throws CheckListException {
		try {
			//
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = limitProxy.getLimitProfile(aLimitProfileID);
			CollateralCheckListSummary[] summaryList = getCollateralCheckListSummaryList(anIContext, limitProfile,
					false, true);
			if ((summaryList == null) || (summaryList.length == 0)) {
				return null;
			}
			ArrayList normalList = new ArrayList();
			ArrayList deletedList = new ArrayList();
			for (int ii = 0; ii < summaryList.length; ii++) {
				if (ICMSConstant.STATE_DELETED.equals(summaryList[ii].getCheckListStatus())) {
					deletedList.add(summaryList[ii]);
				}
				else {
					normalList.add(summaryList[ii]);
				}
			}

			HashMap map = new HashMap();
			if (normalList.size() > 0) {
				map.put(ICMSConstant.NORMAL_LIST, normalList.toArray(new CollateralCheckListSummary[0]));
			}
			if (deletedList.size() > 0) {
				map.put(ICMSConstant.DELETED_LIST, deletedList.toArray(new CollateralCheckListSummary[0]));
			}
			return map;
		}
		catch (LimitException ex) {
			throw new CheckListException(ex);
		}
	}
	
	
		public HashMap getAllCAMCheckListSummaryList(IContext anIContext, long aLimitProfileID)
		throws CheckListException {
		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = limitProxy.getLimitProfile(aLimitProfileID);
			CAMCheckListSummary[] summaryList = getCAMCheckListSummaryList(anIContext, limitProfile,
					false, true);
			if ((summaryList == null) || (summaryList.length == 0)) {
				return null;
			}
			ArrayList normalList = new ArrayList();
			ArrayList deletedList = new ArrayList();
			for (int ii = 0; ii < summaryList.length; ii++) {
				if (ICMSConstant.STATE_DELETED.equals(summaryList[ii].getCheckListStatus())) {
					deletedList.add(summaryList[ii]);
				}
				else {
					normalList.add(summaryList[ii]);
				}
			}
		
			HashMap map = new HashMap();
			if (normalList.size() > 0) {
				map.put(ICMSConstant.NORMAL_LIST, normalList.toArray(new CAMCheckListSummary[0]));
			}
			if (deletedList.size() > 0) {
				map.put(ICMSConstant.DELETED_LIST, deletedList.toArray(new CAMCheckListSummary[0]));
			}
			return map;
		}
		catch (LimitException ex) {
			throw new CheckListException(ex);
		}
	}

		public HashMap getAllFacilityCheckListSummaryList(IContext anIContext, long aLimitProfileID)
		throws CheckListException {
		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = limitProxy.getLimitProfile(aLimitProfileID);
			FacilityCheckListSummary[] summaryList = getFacilityCheckListSummaryList(anIContext, limitProfile,
					false, true);
			if ((summaryList == null) || (summaryList.length == 0)) {
				return null;
			}
			ArrayList normalList = new ArrayList();
			ArrayList deletedList = new ArrayList();
			for (int ii = 0; ii < summaryList.length; ii++) {
				if (ICMSConstant.STATE_DELETED.equals(summaryList[ii].getCheckListStatus())) {
					deletedList.add(summaryList[ii]);
				}
				else {
					normalList.add(summaryList[ii]);
				}
			}
		
			HashMap map = new HashMap();
			if (normalList.size() > 0) {
				map.put(ICMSConstant.NORMAL_LIST, normalList.toArray(new FacilityCheckListSummary[0]));
			}
			if (deletedList.size() > 0) {
				map.put(ICMSConstant.DELETED_LIST, deletedList.toArray(new FacilityCheckListSummary[0]));
			}
			return map;
		}
		catch (LimitException ex) {
			throw new CheckListException(ex);
		}
	}

	/**
	 * Get the list of collateral checklist summary info. This will involve DAP
	 * filtering based on the security location
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return CollateralCheckListSummary[] - the list of collateral checklist
	 *         summaries
	 * @throws CheckListException on errors
	 */
	public CollateralCheckListSummary[] getCollateralCheckListSummaryList(IContext anIContext,
			ILimitProfile anILimitProfile, boolean aFilteredInd, boolean anIncludeDeletedInd) throws CheckListException {
		return getCollateralCheckListSummaryList(anIContext, anILimitProfile, aFilteredInd, anIncludeDeletedInd, false);
	}

	private CollateralCheckListSummary[] getCollateralCheckListSummaryList(IContext anIContext,
			ILimitProfile anILimitProfile, boolean aFilteredInd, boolean anIncludeDeletedInd, boolean aCustodianInd)
			throws CheckListException {
		try {
			// ILimitProxy proxy = LimitProxyFactory.getProxy();
			// HashMap hmap =
			// proxy.getCollateralLimitMap(anILimitProfile.getLimitProfileID());

			HashMap collateralLimitMap = getCollateralLimitMap(anILimitProfile);
			//HashMap camLimitMap = getCAMMap(anILimitProfile);
			HashMap hmap = (HashMap) collateralLimitMap.get(ICMSConstant.CHECKLIST_MAIN_BORROWER);
			HashMap cbMap = (HashMap) collateralLimitMap.get(ICMSConstant.CHECKLIST_CO_BORROWER);
			HashMap colMap = (HashMap) collateralLimitMap.get("COLLATERAL");
			//HashMap camMap = (HashMap) camLimitMap.get("CAM");
		
			ArrayList resultList = new ArrayList();
			HashMap checkListMap = getCollateralCheckListStatus(anILimitProfile.getLimitProfileID());
			addDeleteCollaterals(colMap, checkListMap);
			Iterator i = colMap.keySet().iterator();

			CollateralCheckListSummary summary = null;
			boolean inCountry = CheckListUtil.isInCountry(anIContext.getTeam(), anILimitProfile, null);

			String filterSourceIdIndStr = PropertyManager.getValue("secchklist.filtersourceid.ind");
			String filterSourceIdListStr = PropertyManager.getValue("secchklist.filtersourceid.list");
			boolean filterSourceIdInd = Boolean.valueOf(filterSourceIdIndStr).booleanValue();
			String[] filterSourceIdList = filterSourceIdListStr.split(",");
			HashMap filterSourceIdMap = new HashMap();
			for (int j = 0; j < filterSourceIdList.length; j++) {
				filterSourceIdMap.put(filterSourceIdList[j], null);
			}

			while (i.hasNext()) {
				ICollateral col = (ICollateral) i.next();

				if (filterSourceIdInd && filterSourceIdMap.containsKey(col.getSourceId())) {
					DefaultLogger.debug(this, "Collateral ID: " + col.getCollateralID() + " With Source ID: "
							+ col.getSourceId() + " is being filtered off");
					continue;
				}
				// By Abhijit R : for retriving collateral name from collateral code.
				ICollateralNewMasterJdbc newICollateralNewMasterJdbc= (ICollateralNewMasterJdbc)BeanHouse.get("collateralNewMasterJdbc");
				SearchResult searchResult = newICollateralNewMasterJdbc.getAllCollateralNewMaster();
				ArrayList list= (ArrayList)searchResult.getResultList();
				HashMap collateralHashMap = new HashMap();
				
				if(list!=null){
					for(int l=0;l<list.size();l++){
						OBCollateralNewMaster master=(OBCollateralNewMaster)list.get(l);
						collateralHashMap.put(master.getNewCollateralCode(), master.getNewCollateralDescription());
					}
				}
				summary = new CollateralCheckListSummary();
				boolean colEqualBkgLoc = false;
				summary.setCollateralLocation(col.getCollateralLocation());
				summary.setSecurityOrganization(col.getSecurityOrganization());
				summary.setCollateralName((String)collateralHashMap.get(col.getCollateralCode()));
				summary.setCollateralID(col.getCollateralID());
				summary.setApplicationType(anILimitProfile.getApplicationType());
				CheckListSearchResult checkList = (CheckListSearchResult) checkListMap.get(new Long(col
						.getCollateralID()));
				if (checkList != null) {
					summary.setCheckListID(checkList.getCheckListID());
					summary.setCustCategory(checkList.getCustomerType());
					summary.setCheckListStatus(checkList.getCheckListStatus());
					summary.setAllowDeleteInd(checkList.getAllowDeleteInd());
					summary.setApplicationType(checkList.getApplicationType());
					summary.setTrxID(checkList.getTrxID());
					summary.setTrxStatus(checkList.getTrxStatus());
					summary.setTrxFromState(checkList.getTrxFromState());
					if (checkList.getCheckListLocation() != null) {
						summary.setCollateralLocation(checkList.getCheckListLocation().getCountryCode());
					}
					if (checkList.getCheckListOrganization() != null) {
						summary.setSecurityOrganization(checkList.getCheckListOrganization());
					}

					summary.setDisableTaskInd(checkList.getDisableTaskInd());
				}
				else {
					setSummaryTrxValue((ICheckListSummary) summary, anILimitProfile.getLimitProfileID(),
							ICMSConstant.LONG_INVALID_VALUE, col.getCollateralID(), ICMSConstant.DOC_TYPE_SECURITY);
				}
				summary.setTaskTrx(getCollateralTaskTrxValueTaskTrx(anILimitProfile.getLimitProfileID(), summary
						.getCollateralID(), summary.getCollateralLocation()));
				// summary.setChecklistTrx(getCollateralCheckListTrx(
				// anILimitProfile
				// .getLimitProfileID(),summary.getCollateralID()));

				//27th Aug, 2011 For HDFC, as this is PAN india colEqualBkgLoc = True
				//colEqualBkgLoc = isCollateralAccessAllowed(anIContext, summary);
				colEqualBkgLoc=true;
				
				DefaultLogger.debug(this, ">>>>>summary<<<<<<<<<<"+colEqualBkgLoc +"<<<<>>>>>>>>>>>>"+summary.getCollateralID());
				// DefaultLogger.debug(this, "ColEqBkgLoc: " + colEqualBkgLoc);
				// if ((inCountry && (!aFilteredInd)) || colEqualBkgLoc)
				
				//27th Aug, 2011 For HDFC, as this is PAN india colEqualBkgLoc = True
				//if ((inCountry && (!aCustodianInd)) || isInSameCountry(anIContext, anILimitProfile, summary)) {
					summary.setCollateralType(col.getCollateralType());
					summary.setCollateralSubType(col.getCollateralSubType());
					// summary.setCollateralLocation(col.getCollateralLocation())
					// ;
					// summary.setCollateralRef(col.getSCISecurityID());
					summary.setCollateralRef(String.valueOf(col.getCollateralID()));
					summary.setSameCountryInd(colEqualBkgLoc);
					if (!aCustodianInd) {
						// ITeam team = anIContext.getTeam();
						long teamType = getTeamType(anIContext);
						if ((ICMSConstant.TEAM_TYPE_CPC_CHECKER == teamType)
								|| (ICMSConstant.TEAM_TYPE_CPC_MANAGER_USER == teamType)
								|| (ICMSConstant.TEAM_TYPE_CPC_CUSTODIAN_MAKER == teamType)
								|| (ICMSConstant.TEAM_TYPE_CPC_CUSTODIAN_CHECKER == teamType)
								|| (ICMSConstant.TEAM_TYPE_CPC_SUPPORT_USER == teamType)
								|| (ICMSConstant.TEAM_TYPE_FAM_USER == teamType)
								|| (ICMSConstant.TEAM_TYPE_GAM_USER == teamType)
								|| (ICMSConstant.TEAM_TYPE_SCO_USER == teamType)
								|| (ICMSConstant.TEAM_TYPE_RCO_USER == teamType)) {
							summary.setSameCountryInd(false);
						}
					}
					ArrayList limitList = (ArrayList) hmap.get(col);

					if ((limitList != null) && (limitList.size() > 0)) {
						ILimit[] limits = (ILimit[]) limitList.toArray(new ILimit[limitList.size()]);
						summary.setLimitList(limits);
						// summary.setSubProfileID(anILimitProfile.getCustomerID(
						// )); //for security checklist, all subprofile in db
						// will be empty
						summary.setCustCategory(ICMSConstant.CHECKLIST_MAIN_BORROWER);
						summary.setLeSubProfileID(anILimitProfile.getCustomerID());
					}
					else {
						// DefaultLogger.debug(this,
						// ">>>>>> Setting CoBorrower Limits into CollateralCheckListSummary!!"
						// );
						ArrayList cbLimitList = (ArrayList) cbMap.get(col);
						if (cbLimitList != null) {
							// DefaultLogger.debug(this,
							// ">>>>>> cbLimitList.size: " +
							// cbLimitList.size());
							ICoBorrowerLimit[] cbLimits = (ICoBorrowerLimit[]) cbLimitList
									.toArray(new ICoBorrowerLimit[cbLimitList.size()]);
							summary.setCoborrowerLimitList(cbLimits);
							// summary.setSubProfileID((cbLimits[0] == null) ?
							// ICMSConstant.LONG_INVALID_VALUE :
							// cbLimits[0].getCustomerID());
							summary.setSubProfileID(ICMSConstant.LONG_INVALID_VALUE);
							summary.setCustCategory(ICMSConstant.CHECKLIST_CO_BORROWER);
							// summary.setLeSubProfileID(cbLimits[0].getCustomerID
							// ());
						}
					}
					DefaultLogger.debug(this, ">>>>>summary<<<<<<<<<<"+summary.getCheckListStatus() );
					if ((anIncludeDeletedInd) || (!ICMSConstant.STATE_DELETED.equals(summary.getCheckListStatus()))) {
						resultList.add(summary);
					}
				}
			//27th Aug, 2011 For HDFC, as this is PAN india colEqualBkgLoc = True
			//}

			CollateralCheckListSummary[] summaryList = (CollateralCheckListSummary[]) resultList
					.toArray(new CollateralCheckListSummary[0]);
			if ((summaryList != null) && (summaryList.length > 0)) {
				Arrays.sort(summaryList);
				// String[] param = { "CollateralRef" };
				// SortUtil.sortObject(summaryList, param);
			}
			return summaryList;
		}
		catch (CheckListException ex) {
			rollback();
			throw new CheckListException("Exception in getCollateralCheckListSummaryList", ex);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in getCollateralCheckListSummaryList", ex);
		}
	}
	public CAMCheckListSummary[] getCAMCheckListSummaryList(IContext anIContext,
			ILimitProfile anILimitProfile, boolean aFilteredInd, boolean anIncludeDeletedInd) throws CheckListException {
		return getCAMCheckListSummaryList(anIContext, anILimitProfile, aFilteredInd, anIncludeDeletedInd, false);
	}
	
	public FacilityCheckListSummary[] getFacilityCheckListSummaryList(IContext anIContext,
			ILimitProfile anILimitProfile, boolean aFilteredInd, boolean anIncludeDeletedInd) throws CheckListException {
		return getFacilityCheckListSummaryList(anIContext, anILimitProfile, aFilteredInd, anIncludeDeletedInd, false);
	}
	
	
	private CAMCheckListSummary[] getCAMCheckListSummaryList(IContext anIContext,
			ILimitProfile anILimitProfile, boolean aFilteredInd, boolean anIncludeDeletedInd, boolean aCustodianInd)
			throws CheckListException {
		try {
			// ILimitProxy proxy = LimitProxyFactory.getProxy();
			// HashMap hmap =
			// proxy.getCollateralLimitMap(anILimitProfile.getLimitProfileID());

			HashMap collateralLimitMap = getCollateralLimitMap(anILimitProfile);
			HashMap camLimitMap = getCAMMap(anILimitProfile);
			HashMap hmap = (HashMap) collateralLimitMap.get(ICMSConstant.CHECKLIST_MAIN_BORROWER);
			HashMap cbMap = (HashMap) collateralLimitMap.get(ICMSConstant.CHECKLIST_CO_BORROWER);
			HashMap colMap = (HashMap) collateralLimitMap.get("COLLATERAL");
			HashMap camMap = (HashMap) camLimitMap.get("CAM");

			ArrayList resultList = new ArrayList();
			HashMap checkListMap = getCollateralCheckListStatus(anILimitProfile.getLimitProfileID());
			addDeleteCollaterals(colMap, checkListMap);
			Iterator i = colMap.keySet().iterator();

			CAMCheckListSummary summary = null;
			boolean inCountry = CheckListUtil.isInCountry(anIContext.getTeam(), anILimitProfile, null);

			String filterSourceIdIndStr = PropertyManager.getValue("secchklist.filtersourceid.ind");
			String filterSourceIdListStr = PropertyManager.getValue("secchklist.filtersourceid.list");
			boolean filterSourceIdInd = Boolean.valueOf(filterSourceIdIndStr).booleanValue();
			String[] filterSourceIdList = filterSourceIdListStr.split(",");
			HashMap filterSourceIdMap = new HashMap();
			for (int j = 0; j < filterSourceIdList.length; j++) {
				filterSourceIdMap.put(filterSourceIdList[j], null);
			}

			while (i.hasNext()) {
				ICollateral col = (ICollateral) i.next();

				if (filterSourceIdInd && filterSourceIdMap.containsKey(col.getSourceId())) {
					DefaultLogger.debug(this, "Collateral ID: " + col.getCollateralID() + " With Source ID: "
							+ col.getSourceId() + " is being filtered off");
					continue;
				}

				summary = new CAMCheckListSummary();
				boolean colEqualBkgLoc = false;
				summary.setCollateralLocation(col.getCollateralLocation());
				summary.setSecurityOrganization(col.getSecurityOrganization());
				summary.setCamName(col.getSCIReferenceNote());
				summary.setCollateralID(col.getCollateralID());
				summary.setApplicationType(anILimitProfile.getApplicationType());
				CheckListSearchResult checkList = (CheckListSearchResult) checkListMap.get(new Long(col
						.getCollateralID()));
				if (checkList != null) {
					summary.setCheckListID(checkList.getCheckListID());
					summary.setCustCategory(checkList.getCustomerType());
					summary.setCheckListStatus(checkList.getCheckListStatus());
					summary.setAllowDeleteInd(checkList.getAllowDeleteInd());
					summary.setApplicationType(checkList.getApplicationType());
					summary.setTrxID(checkList.getTrxID());
					summary.setTrxStatus(checkList.getTrxStatus());
					summary.setTrxFromState(checkList.getTrxFromState());
					if (checkList.getCheckListLocation() != null) {
						summary.setCollateralLocation(checkList.getCheckListLocation().getCountryCode());
					}
					if (checkList.getCheckListOrganization() != null) {
						summary.setSecurityOrganization(checkList.getCheckListOrganization());
					}

					summary.setDisableTaskInd(checkList.getDisableTaskInd());
				}
				else {
					setSummaryTrxValue((ICheckListSummary) summary, anILimitProfile.getLimitProfileID(),
							ICMSConstant.LONG_INVALID_VALUE, col.getCollateralID(), ICMSConstant.DOC_TYPE_SECURITY);
				}
				summary.setTaskTrx(getCollateralTaskTrxValueTaskTrx(anILimitProfile.getLimitProfileID(), summary
						.getCollateralID(), summary.getCollateralLocation()));
				// summary.setChecklistTrx(getCollateralCheckListTrx(
				// anILimitProfile
				// .getLimitProfileID(),summary.getCollateralID()));

				//27th Aug, 2011 For HDFC, as this is PAN india colEqualBkgLoc = True
				//colEqualBkgLoc = isCollateralAccessAllowed(anIContext, summary);
				colEqualBkgLoc=true;


				// DefaultLogger.debug(this, "ColEqBkgLoc: " + colEqualBkgLoc);
				// if ((inCountry && (!aFilteredInd)) || colEqualBkgLoc)
				
				//27th Aug, 2011 For HDFC, as this is PAN india colEqualBkgLoc = True
				//if ((inCountry && (!aCustodianInd)) || isInSameCountry(anIContext, anILimitProfile, summary)) {
					summary.setCamType(col.getCollateralType());
					summary.setCamSubType(col.getCollateralSubType());
					// summary.setCollateralLocation(col.getCollateralLocation())
					// ;
					// summary.setCollateralRef(col.getSCISecurityID());
					summary.setCollateralRef(String.valueOf(col.getCollateralID()));
					summary.setSameCountryInd(colEqualBkgLoc);
					if (!aCustodianInd) {
						// ITeam team = anIContext.getTeam();
						long teamType = getTeamType(anIContext);
						if ((ICMSConstant.TEAM_TYPE_CPC_CHECKER == teamType)
								|| (ICMSConstant.TEAM_TYPE_CPC_MANAGER_USER == teamType)
								|| (ICMSConstant.TEAM_TYPE_CPC_CUSTODIAN_MAKER == teamType)
								|| (ICMSConstant.TEAM_TYPE_CPC_CUSTODIAN_CHECKER == teamType)
								|| (ICMSConstant.TEAM_TYPE_CPC_SUPPORT_USER == teamType)
								|| (ICMSConstant.TEAM_TYPE_FAM_USER == teamType)
								|| (ICMSConstant.TEAM_TYPE_GAM_USER == teamType)
								|| (ICMSConstant.TEAM_TYPE_SCO_USER == teamType)
								|| (ICMSConstant.TEAM_TYPE_RCO_USER == teamType)) {
							summary.setSameCountryInd(false);
						}
					}
					ArrayList limitList = (ArrayList) hmap.get(col);

					if ((limitList != null) && (limitList.size() > 0)) {
						ILimit[] limits = (ILimit[]) limitList.toArray(new ILimit[limitList.size()]);
						summary.setLimitList(limits);
						// summary.setSubProfileID(anILimitProfile.getCustomerID(
						// )); //for security checklist, all subprofile in db
						// will be empty
						summary.setCustCategory(ICMSConstant.CHECKLIST_MAIN_BORROWER);
						summary.setLeSubProfileID(anILimitProfile.getCustomerID());
					}
					else {
						// DefaultLogger.debug(this,
						// ">>>>>> Setting CoBorrower Limits into CollateralCheckListSummary!!"
						// );
						ArrayList cbLimitList = (ArrayList) cbMap.get(col);
						if (cbLimitList != null) {
							// DefaultLogger.debug(this,
							// ">>>>>> cbLimitList.size: " +
							// cbLimitList.size());
							ICoBorrowerLimit[] cbLimits = (ICoBorrowerLimit[]) cbLimitList
									.toArray(new ICoBorrowerLimit[cbLimitList.size()]);
							summary.setCoborrowerLimitList(cbLimits);
							// summary.setSubProfileID((cbLimits[0] == null) ?
							// ICMSConstant.LONG_INVALID_VALUE :
							// cbLimits[0].getCustomerID());
							summary.setSubProfileID(ICMSConstant.LONG_INVALID_VALUE);
							summary.setCustCategory(ICMSConstant.CHECKLIST_CO_BORROWER);
							// summary.setLeSubProfileID(cbLimits[0].getCustomerID
							// ());
						}
					}

					if ((anIncludeDeletedInd) || (!ICMSConstant.STATE_DELETED.equals(summary.getCheckListStatus()))) {
						resultList.add(summary);
					}
				}
			//27th Aug, 2011 For HDFC, as this is PAN india colEqualBkgLoc = True
			//}

			CAMCheckListSummary[] summaryList = (CAMCheckListSummary[]) resultList
					.toArray(new CAMCheckListSummary[0]);
			if ((summaryList != null) && (summaryList.length > 0)) {
				Arrays.sort(summaryList);
				// String[] param = { "CollateralRef" };
				// SortUtil.sortObject(summaryList, param);
			}
			return summaryList;
		}
		catch (CheckListException ex) {
			rollback();
			throw new CheckListException("Exception in getCollateralCheckListSummaryList", ex);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in getCollateralCheckListSummaryList", ex);
		}
	}


	private FacilityCheckListSummary[] getFacilityCheckListSummaryList(IContext anIContext,
			ILimitProfile anILimitProfile, boolean aFilteredInd, boolean anIncludeDeletedInd, boolean aCustodianInd)
			throws CheckListException {
		try {
			// ILimitProxy proxy = LimitProxyFactory.getProxy();
			// HashMap hmap =
			// proxy.getCollateralLimitMap(anILimitProfile.getLimitProfileID());

			HashMap collateralLimitMap = getCollateralLimitMap(anILimitProfile);
			HashMap camLimitMap = getCAMMap(anILimitProfile);
			HashMap hmap = (HashMap) collateralLimitMap.get(ICMSConstant.CHECKLIST_MAIN_BORROWER);
			HashMap cbMap = (HashMap) collateralLimitMap.get(ICMSConstant.CHECKLIST_CO_BORROWER);
			HashMap colMap = (HashMap) collateralLimitMap.get("COLLATERAL");
			HashMap camMap = (HashMap) camLimitMap.get("CAM");

			ArrayList resultList = new ArrayList();
			HashMap checkListMap = getCollateralCheckListStatus(anILimitProfile.getLimitProfileID());
			addDeleteCollaterals(colMap, checkListMap);
			Iterator i = colMap.keySet().iterator();

			FacilityCheckListSummary summary = null;
			boolean inCountry = CheckListUtil.isInCountry(anIContext.getTeam(), anILimitProfile, null);

			String filterSourceIdIndStr = PropertyManager.getValue("secchklist.filtersourceid.ind");
			String filterSourceIdListStr = PropertyManager.getValue("secchklist.filtersourceid.list");
			boolean filterSourceIdInd = Boolean.valueOf(filterSourceIdIndStr).booleanValue();
			String[] filterSourceIdList = filterSourceIdListStr.split(",");
			HashMap filterSourceIdMap = new HashMap();
			for (int j = 0; j < filterSourceIdList.length; j++) {
				filterSourceIdMap.put(filterSourceIdList[j], null);
			}

			while (i.hasNext()) {
				ICollateral col = (ICollateral) i.next();

				if (filterSourceIdInd && filterSourceIdMap.containsKey(col.getSourceId())) {
					DefaultLogger.debug(this, "Collateral ID: " + col.getCollateralID() + " With Source ID: "
							+ col.getSourceId() + " is being filtered off");
					continue;
				}

				summary = new FacilityCheckListSummary();
				boolean colEqualBkgLoc = false;
				summary.setCollateralLocation(col.getCollateralLocation());
				summary.setSecurityOrganization(col.getSecurityOrganization());
				summary.setFacName(col.getSCIReferenceNote());
				summary.setCollateralID(col.getCollateralID());
				summary.setApplicationType(anILimitProfile.getApplicationType());
				CheckListSearchResult checkList = (CheckListSearchResult) checkListMap.get(new Long(col
						.getCollateralID()));
				if (checkList != null) {
					summary.setCheckListID(checkList.getCheckListID());
					summary.setCustCategory(checkList.getCustomerType());
					summary.setCheckListStatus(checkList.getCheckListStatus());
					summary.setAllowDeleteInd(checkList.getAllowDeleteInd());
					summary.setApplicationType(checkList.getApplicationType());
					summary.setTrxID(checkList.getTrxID());
					summary.setTrxStatus(checkList.getTrxStatus());
					summary.setTrxFromState(checkList.getTrxFromState());
					if (checkList.getCheckListLocation() != null) {
						summary.setCollateralLocation(checkList.getCheckListLocation().getCountryCode());
					}
					if (checkList.getCheckListOrganization() != null) {
						summary.setSecurityOrganization(checkList.getCheckListOrganization());
					}

					summary.setDisableTaskInd(checkList.getDisableTaskInd());
				}
				else {
					setSummaryTrxValue((ICheckListSummary) summary, anILimitProfile.getLimitProfileID(),
							ICMSConstant.LONG_INVALID_VALUE, col.getCollateralID(), ICMSConstant.DOC_TYPE_SECURITY);
				}
				summary.setTaskTrx(getCollateralTaskTrxValueTaskTrx(anILimitProfile.getLimitProfileID(), summary
						.getCollateralID(), summary.getCollateralLocation()));
				// summary.setChecklistTrx(getCollateralCheckListTrx(
				// anILimitProfile
				// .getLimitProfileID(),summary.getCollateralID()));

				//27th Aug, 2011 For HDFC, as this is PAN india colEqualBkgLoc = True
				//colEqualBkgLoc = isCollateralAccessAllowed(anIContext, summary);
				colEqualBkgLoc=true;


				// DefaultLogger.debug(this, "ColEqBkgLoc: " + colEqualBkgLoc);
				// if ((inCountry && (!aFilteredInd)) || colEqualBkgLoc)
				
				//27th Aug, 2011 For HDFC, as this is PAN india colEqualBkgLoc = True
				//if ((inCountry && (!aCustodianInd)) || isInSameCountry(anIContext, anILimitProfile, summary)) {
					summary.setFacType(col.getCollateralType());
					summary.setFacSubType(col.getCollateralSubType());
					// summary.setCollateralLocation(col.getCollateralLocation())
					// ;
					// summary.setCollateralRef(col.getSCISecurityID());
					summary.setCollateralRef(String.valueOf(col.getCollateralID()));
					summary.setSameCountryInd(colEqualBkgLoc);
					if (!aCustodianInd) {
						// ITeam team = anIContext.getTeam();
						long teamType = getTeamType(anIContext);
						if ((ICMSConstant.TEAM_TYPE_CPC_CHECKER == teamType)
								|| (ICMSConstant.TEAM_TYPE_CPC_MANAGER_USER == teamType)
								|| (ICMSConstant.TEAM_TYPE_CPC_CUSTODIAN_MAKER == teamType)
								|| (ICMSConstant.TEAM_TYPE_CPC_CUSTODIAN_CHECKER == teamType)
								|| (ICMSConstant.TEAM_TYPE_CPC_SUPPORT_USER == teamType)
								|| (ICMSConstant.TEAM_TYPE_FAM_USER == teamType)
								|| (ICMSConstant.TEAM_TYPE_GAM_USER == teamType)
								|| (ICMSConstant.TEAM_TYPE_SCO_USER == teamType)
								|| (ICMSConstant.TEAM_TYPE_RCO_USER == teamType)) {
							summary.setSameCountryInd(false);
						}
					}
					ArrayList limitList = (ArrayList) hmap.get(col);

					if ((limitList != null) && (limitList.size() > 0)) {
						ILimit[] limits = (ILimit[]) limitList.toArray(new ILimit[limitList.size()]);
						summary.setLimitList(limits);
						// summary.setSubProfileID(anILimitProfile.getCustomerID(
						// )); //for security checklist, all subprofile in db
						// will be empty
						summary.setCustCategory(ICMSConstant.CHECKLIST_MAIN_BORROWER);
						summary.setLeSubProfileID(anILimitProfile.getCustomerID());
					}
					else {
						// DefaultLogger.debug(this,
						// ">>>>>> Setting CoBorrower Limits into CollateralCheckListSummary!!"
						// );
						ArrayList cbLimitList = (ArrayList) cbMap.get(col);
						if (cbLimitList != null) {
							// DefaultLogger.debug(this,
							// ">>>>>> cbLimitList.size: " +
							// cbLimitList.size());
							ICoBorrowerLimit[] cbLimits = (ICoBorrowerLimit[]) cbLimitList
									.toArray(new ICoBorrowerLimit[cbLimitList.size()]);
							summary.setCoborrowerLimitList(cbLimits);
							// summary.setSubProfileID((cbLimits[0] == null) ?
							// ICMSConstant.LONG_INVALID_VALUE :
							// cbLimits[0].getCustomerID());
							summary.setSubProfileID(ICMSConstant.LONG_INVALID_VALUE);
							summary.setCustCategory(ICMSConstant.CHECKLIST_CO_BORROWER);
							// summary.setLeSubProfileID(cbLimits[0].getCustomerID
							// ());
						}
					}

					if ((anIncludeDeletedInd) || (!ICMSConstant.STATE_DELETED.equals(summary.getCheckListStatus()))) {
						resultList.add(summary);
					}
				}
			//}

			FacilityCheckListSummary[] summaryList = (FacilityCheckListSummary[]) resultList
					.toArray(new FacilityCheckListSummary[0]);
			if ((summaryList != null) && (summaryList.length > 0)) {
				Arrays.sort(summaryList);
				// String[] param = { "CollateralRef" };
				// SortUtil.sortObject(summaryList, param);
			}
			return summaryList;
		}
		catch (CheckListException ex) {
			rollback();
			throw new CheckListException("Exception in getCollateralCheckListSummaryList", ex);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in getCollateralCheckListSummaryList", ex);
		}
	}
	/**
	 * Helper method to find all the limits tied to a specific collateral for a
	 * limit profile.
	 * 
	 * @param limitProfile
	 * @param collateralID
	 * @return HashMap with ICollateral as key and ILimit[] as value
	 * @throws LimitException
	 */
	private HashMap getCollateralLimitMap(ILimitProfile limitProfile, long collateralID) {
		ILimit[] allLimitsList = limitProfile.getLimits();

		if (allLimitsList == null) {
			return null;
		}

		ICollateral theCollateral = null;
		ArrayList limitsList = new ArrayList();
		for (int i = 0; i < allLimitsList.length; i++) {
			ILimit theLimit = allLimitsList[i];
			ICollateralAllocation[] allocList = theLimit.getNonDeletedCollateralAllocations();
			if (allocList != null) {
				for (int j = 0; j < allocList.length; j++) {
					ICollateral col = allocList[j].getCollateral();
					if ((col != null) && (col.getCollateralID() == collateralID)) {
						theCollateral = col;
						limitsList.add(theLimit);
					}
				}
			}
		}

		HashMap resultMap = null;
		if ((theCollateral != null) && (limitsList.size() != 0)) {
			resultMap = new HashMap(1);
			resultMap.put(theCollateral, limitsList);
		}

		return resultMap;
	}

	public HashMap getCollateralLimitMap(ILimitProfile anILimitProfile) throws CheckListException {
		ILimit[] limitList = anILimitProfile.getLimits();
		if (null == limitList) {
			return null;
		}

		HashMap hmap = new HashMap();
		HashMap cbMap = new HashMap();
		HashMap colMap = new HashMap();

		for (int i = 0; i < limitList.length; i++) {
			ILimit limit = limitList[i];
			ICollateralAllocation[] allocList = limit.getNonDeletedCollateralAllocations();
			ICoBorrowerLimit[] coborrowerLimit = limit.getCoBorrowerLimits();
			boolean found = true;

			// ICollateralAllocation[] allocList =
			// limit.getCollateralAllocations();
			if (null != allocList) {
				for (int j = 0; j < allocList.length; j++) {
					ICollateral col = allocList[j].getCollateral();
					if (null != col) {
						ArrayList alist = (ArrayList) hmap.get(col);

						if (null == alist) {
							alist = new ArrayList();
						}
						alist.add(limit);
						colMap.put(col, null);
						/*
						 * if (found) { //DefaultLogger.debug(this,
						 * "Limit Col: \n\n" + col); found = false; }
						 */
						hmap.put(col, alist);
					}
					else {
						throw new CheckListException("IColalteral in ICollateralAllocation is null for LimitID: "
								+ limit.getLimitID());
					}
				}
			}
			else {
				// do nothing, ignore
			}

			for (int j = 0; j < coborrowerLimit.length; j++) {
				ICoBorrowerLimit cbLimit = coborrowerLimit[j];
				// DefaultLogger.debug(this, ">>>>>>>>>>> CBLimit: " +
				// cbLimit.getLimitRef());
				ICollateralAllocation[] cbAllocList = cbLimit.getNonDeletedCollateralAllocations();
				// DefaultLogger.debug(this, ">>>>>>>>>>> CBLimit Collateral: "
				// + ((cbAllocList == null) ? 0 : cbAllocList.length));
				if (cbAllocList != null) {
					for (int k = 0; k < cbAllocList.length; k++) {
						ICollateral col = cbAllocList[k].getCollateral();
						if (col != null) {
							ArrayList cbLimitList = (ArrayList) cbMap.get(col);
							if (cbLimitList == null) {
								cbLimitList = new ArrayList();
							}
							cbLimitList.add(cbLimit);
							// DefaultLogger.debug(this,
							// ">>>>>>>>>>> CBLimit Collateral ID : " +
							// col.getCollateralID());
							// DefaultLogger.debug(this,
							// ">>>>>>>>>>> CBLimit Collateral Type : " +
							// col.getCollateralType());
							// DefaultLogger.debug(this,
							// ">>>>>>>>>>> CBLimit Collateral SubType : " +
							// col.getCollateralSubType());

							// DefaultLogger.debug(this, "CB Col: \n\n" + col);
							colMap.put(col, null);
							cbMap.put(col, cbLimitList);
						}
					}

				}
			}
		}

		HashMap returnMap = new HashMap();
		returnMap.put(ICMSConstant.CHECKLIST_MAIN_BORROWER, hmap);
		returnMap.put(ICMSConstant.CHECKLIST_CO_BORROWER, cbMap);
		returnMap.put("COLLATERAL", colMap);

		// return hmap;
		return returnMap;
	}
	
	
	public HashMap getCAMMap(ILimitProfile anILimitProfile) throws CheckListException {
		ILimit[] limitList = anILimitProfile.getLimits();
		
		ArrayList globalDocChkList = new ArrayList();
		ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
		DocumentSearchCriteria criteria = new DocumentSearchCriteria();
		criteria.setDocumentType("CAM");

		SearchResult sr = null;
		try {
			sr = proxy.getDocumentItemList(criteria);
		}
		catch (CheckListTemplateException ex) {
			throw new CommandProcessingException("failed to retrieve document item list using criteria [" + criteria
					+ "]", ex);
		}

		if (sr != null && sr.getResultList() != null) {
			globalDocChkList.addAll(sr.getResultList());
			if (true) {
				Collections.sort(globalDocChkList);
			}
		}
		HashMap hmap = new HashMap();
		HashMap cbMap = new HashMap();
		HashMap colMap = new HashMap();

		if (null != globalDocChkList) {
			for (int j = 0; j < globalDocChkList.size(); j++) {
				
					colMap.put(globalDocChkList.get(j), null);
					/*
					 * if (found) { //DefaultLogger.debug(this,
					 * "Limit Col: \n\n" + col); found = false; }
					 */
					//hmap.put(col, alist);
				}
				
			}
		HashMap returnMap = new HashMap();
		returnMap.put("CAM", colMap);

		// return hmap;
		return returnMap;
	}

	private void addDeleteCollaterals(HashMap aLimitSecurityMap, HashMap aCheckListMap) throws CheckListException {
		ICollateral col = null;
		ArrayList list = new ArrayList();

		Iterator iter = aLimitSecurityMap.keySet().iterator();
		while (iter.hasNext()) {
			col = (ICollateral) iter.next();
			list.add(new Long(col.getCollateralID()));
		}

		try {
			ICollateralProxy colProxy = CollateralProxyFactory.getProxy();

			iter = aCheckListMap.keySet().iterator();
			while (iter.hasNext()) {
				Long collateralID = (Long) iter.next();
				if (!list.contains(collateralID)) {
					CheckListSearchResult checkList = (CheckListSearchResult) aCheckListMap.get(collateralID);
					if (checkList.getCheckListStatus().equals(ICMSConstant.STATE_DELETED)) {
						col = colProxy.getCollateral(collateralID.longValue(), true);
						aLimitSecurityMap.put(col, null);
					}
				}
			}
		}
		catch (CollateralException ex) {
			throw new CheckListException("Exception in addDeleteCollaterals", ex);
		}
	}

	/**
	 * To check if it is in-country CPC users
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile
	 * @return boolean - true if allowed and false otherwise
	 */
	/**
	 * protected boolean isInCountry(IContext anIContext, ILimitProfile
	 * anILimitProfile) { ITeam team = anIContext.getTeam(); String[]
	 * countryList = team.getCountryCodes(); if (countryList == null) { return
	 * false; } String bkgLocation =
	 * anILimitProfile.getOriginatingLocation().getCountryCode(); for (int ii=0;
	 * ii<countryList.length; ii++) { if (countryList[ii].equals(bkgLocation)) {
	 * return true; } } return false; }
	 **/

	/**
	 * To check if the collateral location is the same as the limit booking
	 * location country
	 * @param anIContext of IContext type
	 * @param aSummary of CollateralCheckListSummary type
	 * @return boolean - true if they are equals and false otherwise
	 */
	private boolean isCollateralAccessAllowed(IContext anIContext, CollateralCheckListSummary aSummary) {
		ITeam team = anIContext.getTeam();
		// long teamType = getTeamType(anIContext);
		/*
		 * if (!aCustodianInd) { if (ICMSConstant.TEAM_TYPE_CPC_CHECKER ==
		 * teamType || ICMSConstant.TEAM_TYPE_CPC_MANAGER_USER == teamType ||
		 * ICMSConstant.TEAM_TYPE_CPC_CUSTODIAN_MAKER == teamType ||
		 * ICMSConstant.TEAM_TYPE_CPC_CUSTODIAN_CHECKER == teamType ||
		 * ICMSConstant.TEAM_TYPE_CPC_SUPPORT_USER == teamType) { return false;
		 * } }
		 */
		String[] countryList = team.getCountryCodes();
		// DefaultLogger.debug(this, "CountryList: " + countryList);
		if (countryList == null) {
			return false;
		}
		String bkgLocation = aSummary.getCollateralLocation();
		for (int ii = 0; ii < countryList.length; ii++) {
			if (countryList[ii].equals(bkgLocation)) {
				return true;
			}
		}
		return false;
	}

	private boolean isAccessAllowed(IContext anIContext, CCCheckListSummary anSummary) {
		ITeam team = anIContext.getTeam();
		long teamType = getTeamType(anIContext);
		if ((ICMSConstant.TEAM_TYPE_CPC_CHECKER == teamType) || (ICMSConstant.TEAM_TYPE_CPC_MANAGER_USER == teamType)
				//-- [Start] customize for alliance, commented the condition below
                //           to enable the viewing of CCDoc from custodian module
                //|| (ICMSConstant.TEAM_TYPE_CPC_CUSTODIAN_MAKER == teamType)
				//|| (ICMSConstant.TEAM_TYPE_CPC_CUSTODIAN_CHECKER == teamType)
                //-- [End]
				|| (ICMSConstant.TEAM_TYPE_CPC_SUPPORT_USER == teamType)
				|| (ICMSConstant.TEAM_TYPE_RCO_USER == teamType) || (ICMSConstant.TEAM_TYPE_SCO_USER == teamType)
				// || (ICMSConstant.TEAM_TYPE_FAM_USER == teamType)
				|| (ICMSConstant.TEAM_TYPE_GAM_USER == teamType)) {
			return false;
		}
		String[] countryList = team.getCountryCodes();
		if (countryList == null) {
			return false;
		}
		String domCountry = anSummary.getDomicileCtry();
		for (int ii = 0; ii < countryList.length; ii++) {
			if (countryList[ii].equals(domCountry)) {
				return true;
			}
		}
		return false;
	}

	private long getTeamType(IContext anIContext) {
		ITeam team = anIContext.getTeam();
		ICommonUser user = anIContext.getUser();

		for (int i = 0; i < team.getTeamMemberships().length; i++) {
			for (int j = 0; j < team.getTeamMemberships()[i].getTeamMembers().length; j++) {
				if (team.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser().getUserID() == user
						.getUserID()) {
					return team.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID();
				}
			}
		}
		return 0;
	}

	/*
	 * private boolean isAccessAllowed(IContext anIContext, ICMSCustomer
	 * anICMSCustomer) { ITeam team = anIContext.getTeam(); String[] countryList
	 * = team.getCountryCodes(); if (countryList == null) { return false; }
	 * String domCountry =
	 * anICMSCustomer.getOriginatingLocation().getCountryCode(); for (int ii=0;
	 * ii<countryList.length; ii++) { if (countryList[ii].equals(domCountry)) {
	 * return true; } } return false; }
	 */

	/*
	 * private boolean isAccessAllowed(IContext anIContext, IPledgor anIPledgor)
	 * { ITeam team = anIContext.getTeam(); String[] countryList =
	 * team.getCountryCodes(); if (countryList == null) { return false; } String
	 * domCountry = anIPledgor.getBookingLocation().getCountryCode(); for (int
	 * ii=0; ii<countryList.length; ii++) { if
	 * (countryList[ii].equals(domCountry)) { return true; } } return false; }
	 */

	/**
	 * Get the collateral checklist trx value based on the checklist ID
	 * @param aCheckListID of long type
	 * @return ICheckListTrxValue - the checklist trx value containing the
	 *         checklist with the specified ID
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue getCheckList(long aCheckListID) throws CheckListException {
		if (aCheckListID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new CheckListException("The checkListID is invalid !!!");
		}

		ICheckListTrxValue trxValue = new OBCheckListTrxValue();
		trxValue.setReferenceID(String.valueOf(aCheckListID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_CHECKLIST);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CHECKLIST_ID);
		return operate(trxValue, param);
	}

	/**
	 * Get a checklist by transaction ID
	 * @param aTrxID of String type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue getCheckListByTrxID(String aTrxID) throws CheckListException {
		if ((aTrxID == null) || (aTrxID.trim().length() == 0)) {
			throw new CheckListException("The TrxID is null !!!");
		}
		ICheckListTrxValue trxValue = new OBCheckListTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CHECKLIST);
		return operate(trxValue, param);
	}

	/**
	 * Search checklist based on the criteria specified. Currently only used to
	 * search for checklist pending multi-level approval.
	 * 
	 * @param ctx transaction context
	 * @param criteria of type CheckListSearchCriteria
	 * @return search result
	 * @throws CheckListException on errors encountered
	 */
	public SearchResult searchCheckList(ITrxContext ctx, CheckListSearchCriteria criteria)
			throws CheckListTemplateException, CheckListException {
		ICheckListBusManager mgr = CheckListBusManagerFactory.getCheckListBusManager();
		criteria.setTrxContext(ctx);
		CheckListSearchResult[] resultsList = mgr.searchCheckList(criteria);

		if (resultsList == null) {
			return null;
		}

		Collection summaryList = getCheckListSummaryList(ctx, resultsList);
		if (criteria.isPendingOfficerApproval()) {
			Iterator i = summaryList.iterator();
			while (i.hasNext()) {
				ICheckListSummary chkListSummary = (ICheckListSummary) i.next();
				ICheckListTrxValue trxValue = getCheckListByTrxID(chkListSummary.getTrxID());
				mapOBtoSummary(trxValue.getCheckList(), chkListSummary);
				chkListSummary.setTrxValue(trxValue);
			}
		}

		return (summaryList != null) ? new SearchResult(0, 0, summaryList.size(), summaryList) : null;
	}

	/**
	 * Helper method to get a list of summaries from the list of
	 * checklistsearchresults.
	 * 
	 * @param ctx
	 * @param resultsList
	 * @return Collection
	 * @throws CheckListException
	 */
	private Collection getCheckListSummaryList(ITrxContext ctx, CheckListSearchResult[] resultsList)
			throws CheckListTemplateException, CheckListException {
		if (resultsList == null) {
			return null;
		}

		ArrayList summaryList = new ArrayList(resultsList.length);
		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ICustomerProxy customerProxy = CustomerProxyFactory.getProxy();
			ICollateralProxy collateralProxy = CollateralProxyFactory.getProxy();
			summaryList = new ArrayList(resultsList.length);
			for (int i = 0; i < resultsList.length; i++) {
				ILimitProfile theLimitProfile = null;
				if (resultsList[i].getLimitProfileID() != ICMSConstant.LONG_INVALID_VALUE) {
					theLimitProfile = limitProxy.getLimitProfile(resultsList[i].getLimitProfileID());
				}
				if (resultsList[i] instanceof CCCheckListSearchResult) {
					CCCheckListSearchResult ccSearchResult = (CCCheckListSearchResult) resultsList[i];
					CCCheckListSummary ccSummary = null;
					if ((ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(ccSearchResult.getCustomerType()))
							|| (ICMSConstant.CHECKLIST_CO_BORROWER.equals(ccSearchResult.getCustomerType()))
							|| (ICMSConstant.CHECKLIST_NON_BORROWER.equals(ccSearchResult.getCustomerType()))) {
						ICMSCustomer theCustomer = customerProxy.getCustomer(ccSearchResult.getCustomerID());
						ccSummary = formulateCCCheckListSummary(ctx, theLimitProfile, ccSearchResult, theCustomer);
					}
					else if (ICMSConstant.CHECKLIST_PLEDGER.equals(ccSearchResult.getCustomerType())) {
						IPledgor thePledgor = collateralProxy.getPledgor(ccSearchResult.getCustomerID());
						ccSummary = formulateCCCheckListSummary(ctx, theLimitProfile, ccSearchResult, thePledgor);
					}
					summaryList.add(ccSummary);
				}
				else if (resultsList[i] instanceof CollateralCheckListSearchResult) {
					CollateralCheckListSearchResult colSearchResult = (CollateralCheckListSearchResult) resultsList[i];
					HashMap collateralLimitMap = getCollateralLimitMap(theLimitProfile, colSearchResult
							.getCollateralID());

					if (collateralLimitMap != null) {
						Map.Entry anEntrySet = (Map.Entry) collateralLimitMap.entrySet().iterator().next();
						ICollateral theCollateral = (ICollateral) anEntrySet.getKey();
						Collection theLimits = (Collection) anEntrySet.getValue();
						CollateralCheckListSummary colSummary = formulateCollateralCheckListSummary(ctx,
								theLimitProfile, colSearchResult, theCollateral, theLimits, false);
						summaryList.add(colSummary);
					}
				}
			}
		}
		catch (LimitException ex) {
			throw new CheckListException("Exception in searchCheckList", ex);
		}
		catch (CustomerException ex) {
			throw new CheckListException("Exception in searchCheckList", ex);
		}
		catch (CollateralException ex) {
			throw new CheckListException("Exception in searchCheckList", ex);
		}
		return summaryList;
	}

	/**
	 * Helper method to formulate a collateral checklist summary based on the
	 * parameters.
	 * 
	 * @param ctx
	 * @param limitProfile
	 * @param collateral
	 * @param limitsList
	 * @return
	 */
	private CollateralCheckListSummary formulateCollateralCheckListSummary(ITrxContext ctx, ILimitProfile limitProfile,
			CheckListSearchResult chklistSearchResult, ICollateral collateral, Collection limitsList,
			boolean aCustodianInd) throws CheckListException {
		CollateralCheckListSummary colSummary = new CollateralCheckListSummary();
		colSummary.setCollateralLocation(collateral.getCollateralLocation());
		colSummary.setSecurityOrganization(collateral.getSecurityOrganization());
		colSummary.setCollateralID(collateral.getCollateralID());
		if (chklistSearchResult != null) {
			colSummary.setCheckListID(chklistSearchResult.getCheckListID());
			colSummary.setCheckListStatus(chklistSearchResult.getCheckListStatus());
			colSummary.setAllowDeleteInd(chklistSearchResult.getAllowDeleteInd());
			colSummary.setTrxID(chklistSearchResult.getTrxID());
			colSummary.setTrxStatus(chklistSearchResult.getTrxStatus());
			colSummary.setTrxFromState(chklistSearchResult.getTrxFromState());
			if (chklistSearchResult.getCheckListLocation() != null) {
				colSummary.setCollateralLocation(chklistSearchResult.getCheckListLocation().getCountryCode());
			}
			if (chklistSearchResult.getCheckListOrganization() != null) {
				colSummary.setSecurityOrganization(chklistSearchResult.getCheckListOrganization());
			}
			colSummary.setDisableTaskInd(chklistSearchResult.getDisableTaskInd());
		}
		else {
			setSummaryTrxValue((ICheckListSummary) colSummary, limitProfile.getLimitProfileID(),
					ICMSConstant.LONG_INVALID_VALUE, collateral.getCollateralID(), ICMSConstant.DOC_TYPE_SECURITY);
		}
		colSummary.setTaskTrx(getCollateralTaskTrxValueTaskTrx(limitProfile.getLimitProfileID(), collateral
				.getCollateralID(), colSummary.getCollateralLocation()));
		// colSummary.setChecklistTrx(getCollateralCheckListTrx(limitProfile.
		// getLimitProfileID(),collateral.getCollateralID()));

		//27th Aug, 2011 For HDFC, as this is PAN india colEqualBkgLoc = True
		//boolean colEqualBkgLoc = isCollateralAccessAllowed(ctx, colSummary);
		boolean colEqualBkgLoc=true;
		
		DefaultLogger.debug(this, "ColEqBkgLoc: " + colEqualBkgLoc);
		//27th Aug, 2011 For HDFC, as this is PAN india colEqualBkgLoc = True
		//if ((CheckListUtil.isInCountry(ctx.getTeam(), limitProfile, null) && (!aCustodianInd))	|| isInSameCountry(ctx, limitProfile, colSummary)) {
			colSummary.setCollateralType(collateral.getCollateralType());
			colSummary.setCollateralSubType(collateral.getCollateralSubType());
			colSummary.setCollateralRef(collateral.getSCISecurityID());
			colSummary.setSameCountryInd(colEqualBkgLoc);
			if (!aCustodianInd) {
				// ITeam team = ctx.getTeam();
				long teamType = getTeamType(ctx);
				if ((ICMSConstant.TEAM_TYPE_CPC_CHECKER == teamType)
						|| (ICMSConstant.TEAM_TYPE_CPC_MANAGER_USER == teamType)
						|| (ICMSConstant.TEAM_TYPE_CPC_CUSTODIAN_MAKER == teamType)
						|| (ICMSConstant.TEAM_TYPE_CPC_CUSTODIAN_CHECKER == teamType)
						|| (ICMSConstant.TEAM_TYPE_CPC_SUPPORT_USER == teamType)
						|| (ICMSConstant.TEAM_TYPE_FAM_USER == teamType)
						|| (ICMSConstant.TEAM_TYPE_GAM_USER == teamType)
						|| (ICMSConstant.TEAM_TYPE_SCO_USER == teamType)
						|| (ICMSConstant.TEAM_TYPE_RCO_USER == teamType)) {
					colSummary.setSameCountryInd(false);
				}
			}

			if (limitsList != null) {
				ILimit[] limits = (ILimit[]) limitsList.toArray(new ILimit[limitsList.size()]);
				colSummary.setLimitList(limits);
			}
			DefaultLogger.debug(this, "Status: " + colSummary.getCheckListStatus());
		//27th Aug, 2011 For HDFC, as this is PAN india colEqualBkgLoc = True
		//}
		return colSummary;
	}

	/**
	 * Helper method to map checklist business object to checklist summary.
	 * 
	 * @param actual of type ICheckList
	 * @param summary of type CCCheckListSummary
	 */
	private void mapOBtoSummary(ICheckList actual, ICheckListSummary summary) {
		summary.setCheckListReferenceID(actual.getCheckListID());
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
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext is null!!!");
		}
		if (anICheckList == null) {
			throw new CheckListException("The ICheckList to be created is null!!!");
		}
		// DefaultLogger.debug(this, "CheckListOwner: " +
		// anICheckList.getCheckListOwner());
		if(!(anICheckList.getCheckListID()==-999999999)){
		if (!allowCheckListTrxCreation(anICheckList.getCheckListOwner())) {
			throw new CheckListException("CheckList workflow has been created before", new ConcurrentUpdateException(
					"Trx already created !!!"));
		}
		}
		ICheckListTrxValue trxValue = formulateTrxValue(anITrxContext, anICheckList, ICMSConstant.CHECKLIST_MAINTAIN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_CHECKLIST);
		return operate(trxValue, param);
	}

	public ICheckListTrxValue makerCreateCheckListWithoutApproval(ITrxContext anITrxContext, ICheckList anICheckList)
			throws CheckListException {
		ICheckListTrxValue checklistTrxValue = makerCreateCheckList(anITrxContext, anICheckList);
		return checkerApproveCheckList(anITrxContext, checklistTrxValue);
	}

	public ICheckListTrxValue makerUpdateCheckListWithoutApproval(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList) throws CheckListException {
		ICheckListTrxValue checklistTrxValue = makerUpdateCheckList(anITrxContext, anICheckListTrxValue, anICheckList);
		return checkerApproveCheckList(anITrxContext, checklistTrxValue);
	}

	/**
	 * Copy Checklist due to BCA Renewal
	 * @param anITrxContext of ITrxContext type
	 * @param anITrxValueCheckList of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue copyCheckList(ITrxContext anITrxContext, ICheckListTrxValue anITrxValueCheckList)
			throws CheckListException {
		// if (anITrxContext == null)
		// {
		// throw new CheckListException("The ITrxContext is null!!!");
		// }
		if (anITrxValueCheckList == null) {
			throw new CheckListException("The anITrxValueCheckList to be created is null!!!");
		}
		// validate(anICheckList);
		ICheckListTrxValue trxValue = formulateTrxValue(anITrxContext, anITrxValueCheckList,
				ICMSConstant.CHECKLIST_MAINTAIN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_COPY_CHECKLIST);
		return operate(trxValue, param);
	}

	public ICheckListTrxValue copyCheckList(ITrxContext anITrxContext, ICheckList anICheckList)
			throws CheckListException {
		// if (anITrxContext == null)
		// {
		// throw new CheckListException("The ITrxContext is null!!!");
		// }
		if (anICheckList == null) {
			throw new CheckListException("The ICheckList to be created is null!!!");
		}
		// validate(anICheckList);
		ICheckListTrxValue trxValue = formulateTrxValue(anITrxContext, anICheckList, ICMSConstant.CHECKLIST_MAINTAIN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_COPY_CHECKLIST);
		return operate(trxValue, param);
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
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The anICheckListTrxValue to be approved is null!!!");
		}
		try {
			anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue,
					ICMSConstant.CHECKLIST_MAINTAIN);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			if (obsoleteCheckList(anICheckListTrxValue.getStagingCheckList().getCheckListOwner(), anICheckListTrxValue
					.getTransactionSubType())) {
				param.setAction(ICMSConstant.ACTION_SYSTEM_OBSOLETE_CHECKLIST);
			}
			else {
				param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CHECKLIST);
			}
			// return operate (anICheckListTrxValue, param);
			return operate(anICheckListTrxValue, param);
		}
		catch (CheckListException ex) {
			rollback();
			throw ex;
		}
	}

	/**
	 * Checker rejects a checklist trx
	 * @param anITrxContext - ITrxContext
	 * @param anICheckListTrxValue - anICheckListTrxValue
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue checkerRejectCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The anICheckListTrxValue to be rejected is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, ICMSConstant.CHECKLIST_MAINTAIN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CHECKLIST);
		return operate(anICheckListTrxValue, param);
	}

	/**
	 * Maker closes a checklist trx that has been rejected by the checker
	 * @param anITrxContext - ITrxContext
	 * @param anICheckListTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerCloseCheckListTrx(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext to be created is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The anICheckListTrxValue to be created is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, ICMSConstant.CHECKLIST_MAINTAIN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CHECKLIST);
		return operate(anICheckListTrxValue, param);
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
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The ICheckListTrxValue to be update is null!!!");
		}
		if (anICheckList == null) {
			throw new CheckListException("The ICheckList to be updated is null !!!");
		}

		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, anICheckList,
				ICMSConstant.CHECKLIST_MAINTAIN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CHECKLIST);
		return operate(anICheckListTrxValue, param);
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
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The anICheckListTrxValue to be updated is null!!!");
		}
		if (anICheckList == null) {
			throw new CheckListException("The ICheckList to be updated is null !!!");
		}
		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, anICheckList,
				ICMSConstant.CHECKLIST_MAINTAIN);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CHECKLIST);
		return operate(anICheckListTrxValue, param);
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
		ICheckList stage = anICheckListTrxValue.getCheckList();
		stage.setCheckListStatus(ICMSConstant.STATE_DELETED);
		return makerUpdateCheckList(anITrxContext, anICheckListTrxValue, stage);
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
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The anICheckListTrxValue to be updated is null!!!");
		}
		if (anICheckList == null) {
			throw new CheckListException("The ICheckList to be updated is null !!!");
		}
		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, anICheckList,
				ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CHECKLIST_RECEIPT);
		return operate(anICheckListTrxValue, param);
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
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The anICheckListTrxValue to be approved is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		/*
		 * if (trxRequiresAnotherApproval(anICheckListTrxValue)) {
		 * param.setAction
		 * (ICMSConstant.ACTION_CHECKER_VERIFY_CHECKLIST_RECEIPT); } else {
		 */
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CHECKLIST_RECEIPT);
		/* } */
		return operate(anICheckListTrxValue, param);
	}
	
	/*public ICheckListTrxValue checkerApproveCheckListReceiptScheduler(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue,String flagScheduler) throws CheckListException {
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The anICheckListTrxValue to be approved is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		
		 * if (trxRequiresAnotherApproval(anICheckListTrxValue)) {
		 * param.setAction
		 * (ICMSConstant.ACTION_CHECKER_VERIFY_CHECKLIST_RECEIPT); } else {
		 
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CHECKLIST_RECEIPT);
		 } 
		return operateScheduler(anICheckListTrxValue, param,flagScheduler);
	}*/

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
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The anICheckListTrxValue to be approved is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		/*
		 * if (trxRequiresAnotherApproval(anICheckListTrxValue)) {
		 * param.setAction
		 * (ICMSConstant.ACTION_CHECKER_VERIFY_CHECKLIST_RECEIPT); } else {
		 */
		param.setAction(ICMSConstant.ACTION_CHECKER_UPDATE_CHECKLIST_RECEIPT);
		/* } */
		return operate(anICheckListTrxValue, param);
	}

	// R1.5 CR17
	public void updateSharedChecklistStatus(ICheckListTrxValue trxValue) throws CheckListException {
		try {
			ShareCheckListProcessor shareCheckListProcessor = new ShareCheckListProcessor();
			Long[] sharedCheckList = shareCheckListProcessor.prepareDistinctShareCheckListIDList(trxValue);

			if ((sharedCheckList != null) && (sharedCheckList.length > 0)) {
				for (int i = 0; i < sharedCheckList.length; i++) {
					DefaultLogger.debug(this,
							">>>>>>>> (Checker Approve Op - updateShareCheckListsStatus) CheckList that is system-updated: "
									+ sharedCheckList[i]);
					systemUpdateCheckList(sharedCheckList[i].longValue());
				}
			}

		}
		catch (SearchDAOException e) {
			DefaultLogger.debug(this, e.getMessage());
			throw new CheckListException(
					"Error in updating the list of checklist impacted by the sharing of documents", e);
		}
	}

	private boolean obsoleteCheckList(ICheckListOwner owner, String transactionSubType) {
		if ((owner instanceof ICCCheckListOwner)
				&& ICMSConstant.TRX_TYPE_OBSOLETE_CC_CHECKLIST.equals(transactionSubType)) {
			return true;
		}

		if ((owner instanceof ICollateralCheckListOwner)
				&& ICMSConstant.TRX_TYPE_OBSOLETE_COL_CHECKLIST.equals(transactionSubType)) {
			return true;
		}
		return false;
		/***
		 * if (ICMSConstant.STATE_CHECKLIST_DELETED.equals(anICheckList.
		 * getCheckListStatus()) && anICheckList.getAllowDeleteInd()) { return
		 * true; } return false;
		 ***/
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
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The anICheckListTrxValue to be approved is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MANAGER_VERIFY_CHECKLIST_RECEIPT);
		return operate(anICheckListTrxValue, param);
	}

	/**
	 * To check if the checklist trx requires another level of approval. If one
	 * of the checklist item status is pending_waiver or pending_deferral then
	 * it requires another authoriser. (biz requirement)
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return boolean - true if it requires another authoriser and false
	 *         otherwise
	 */
	/*
	 * private boolean trxRequiresAnotherApproval(ICheckListTrxValue
	 * anICheckListTrxValue) throws CheckListException { ICheckList checkList =
	 * anICheckListTrxValue.getStagingCheckList(); if (checkList == null) {
	 * throw new CheckListException("The staging checklist is null !!!"); }
	 * 
	 * ICheckListItem[] itemList = checkList.getCheckListItemList(); if
	 * ((itemList == null) || (itemList.length == 0)) { return false; } for (int
	 * ii=0; ii<itemList.length; ii++) { if
	 * ((ICMSConstant.STATE_ITEM_PENDING_WAIVER
	 * .equals(itemList[ii].getItemStatus())) ||
	 * (ICMSConstant.STATE_ITEM_PENDING_DEFERRAL
	 * .equals(itemList[ii].getItemStatus()))) { return true; } } return false;
	 * }
	 */

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
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The anICheckListTrxValue to be rejected is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CHECKLIST_RECEIPT);
		return operate(anICheckListTrxValue, param);
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
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The anICheckListTrxValue to be rejected is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MANAGER_REJECT_CHECKLIST_RECEIPT);
		return operate(anICheckListTrxValue, param);
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
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext to be created is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The anICheckListTrxValue to be created is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CHECKLIST_RECEIPT);
		return operate(anICheckListTrxValue, param);
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
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The ICheckListTrxValue to be update is null!!!");
		}
		if (anICheckList == null) {
			throw new CheckListException("The ICheckList to be updated is null !!!");
		}

		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, anICheckList,
				ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CHECKLIST_RECEIPT);
		return operate(anICheckListTrxValue, param);
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
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The anICheckListTrxValue to be updated is null!!!");
		}
		if (anICheckList == null) {
			throw new CheckListException("The ICheckList to be updated is null !!!");
		}
		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, anICheckList,
				ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_CHECKLIST);
		return operate(anICheckListTrxValue, param);
	}

	/**
	 * Maker cancel a save the checkList as draft
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue makerCancelSavedCheckList(ITrxContext anITrxContext,
			ICheckListTrxValue anICheckListTrxValue) throws CheckListException {
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext to be created is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The ICheckListTrxValue to be created is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, ICMSConstant.CHECKLIST_RECEIPT);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_SAVE_CHECKLIST);
		return operate(anICheckListTrxValue, param);
	}

	/**
	 * Retrieve next checklist receipts based on user action. This may result in
	 * the receipt state being modified and/or a new receipt being created.
	 * 
	 * @param receipt checklist receipt on which the action will be performed
	 * @param action user action on the checklist receipt
	 * @throws CheckListException error in saving next state of the checklist
	 *         receipt
	 */
	public ICheckListItem[] getNextCheckListReceipts(ICheckListItem receipt, String action) throws CheckListException {
		if (receipt != null) {
			try {
				String status=receipt.getItemStatus();
				ICheckListItem[] newItems = null; // as for now it'll be 1 or 2
				// items only.
				DefaultTrxProcess defProcess = new DefaultTrxProcess();
				
				String newState = null;
				
				//CY : Added this to take from cpc custodian status instead from status. Due to status "COMPLETED"
				if((receipt.getCPCCustodianStatus() != null) && (!receipt.getCPCCustodianStatus().equalsIgnoreCase(ICMSConstant.STATE_ITEM_RECEIVED)))
				{
					newState = defProcess.getNextState(receipt.getCPCCustodianStatus(), action,
							ICMSConstant.INSTANCE_CHECKLIST_ITEM);
				}
				else if(receipt.getItemStatus().equals("DISCREPANCY") && "DEFER".equals(action)){
					newState="PENDING_DEFER";
				}
				else if(!receipt.getItemStatus().equals("AWAITING")){
					newState=status;
				}
				else if(receipt.getItemStatus().equals("AWAITING"))
				{
					newState = defProcess.getNextState(receipt.getItemStatus(), action,ICMSConstant.INSTANCE_CHECKLIST_ITEM);
				}
				// CMS-2114 : update cpc cust status last update date when
				// allow_temp_uplift, allow_perm_uplift and allow_relodge
				if (action.equals(ICMSConstant.ACTION_ITEM_ALLOW_TEMP_UPLIFT)
						|| action.equals(ICMSConstant.ACTION_ITEM_ALLOW_PERM_UPLIFT)
						|| action.equals(ICMSConstant.ACTION_ITEM_ALLOW_RELODGE)) {
					receipt.setCPCCustodianStatusUpdateDate(DateUtil.getDate());
					receipt.setCustodianDocStatus(newState);
					newItems = new OBCheckListItem[1];
					newItems[0] = receipt;

				}
				else {
					receipt.setLastUpdateDate(DateUtil.getDate());
					if("RECEIVED".equals(newState)){
					receipt.setItemStatus("PENDING_RECEIVED");
					}
					else if("WAIVED".equals(newState)){
						receipt.setItemStatus("PENDING_WAIVER");
					}
					else if("DEFERRED".equals(newState)){
						receipt.setItemStatus("PENDING_DEFER");
					}					
					else{
						receipt.setItemStatus(newState);
					}
					//CY : Added this to take from cpc custodian status instead from status. Due to status "COMPLETED" For items that are lodged previously
					if(receipt.getCPCCustodianStatus() != null)
					{
                        if (requireCustodianApproval(newState)) {
                            receipt.setItemStatus(ICMSConstant.STATE_ITEM_COMPLETED);
						    receipt.setCPCCustodianStatus(newState);    
                        } else if (!receipt.getCPCCustodianStatus().equals(ICMSConstant.STATE_ITEM_RECEIVED)) {
                            receipt.setItemStatus(ICMSConstant.STATE_ITEM_COMPLETED);
						    receipt.setCPCCustodianStatus(newState);    
                        }
						//receipt.setItemStatus(ICMSConstant.STATE_ITEM_COMPLETED);
						//receipt.setCPCCustodianStatus(newState);
					}
                    /*
                    else if(requireCustodianApproval(newState))
					{
						receipt.setItemStatus(ICMSConstant.STATE_ITEM_COMPLETED);
						receipt.setCPCCustodianStatus(newState);
					}
					*/
					if (action.equals(ICMSConstant.ACTION_ITEM_RENEW)) {
						// renew the expired receipt
						ICheckListItem newItem = renewCheckListReceipt(receipt);
						newItems = new OBCheckListItem[2];
						newItems[0] = receipt;
						newItems[1] = newItem;
					}
					else {
						newItems = new OBCheckListItem[1];
						newItems[0] = receipt;
					}
				}
				return newItems;
			}
			catch (Exception e) {
				throw new CheckListException("Exception in retrieveNextCheckListReceipts: " + e.toString());
			}
		}
		else {
			return null;
		}

	}
	
	public ICheckListItem[] getNextCheckListReceiptsOld(ICheckListItem receipt, String action) throws CheckListException {
		if (receipt != null) {
			try {
				ICheckListItem[] newItems = null; // as for now it'll be 1 or 2
				// items only.
				DefaultTrxProcess defProcess = new DefaultTrxProcess();
				
				String newState = null;
				
				//CY : Added this to take from cpc custodian status instead from status. Due to status "COMPLETED"
				if((receipt.getCPCCustodianStatus() != null) && (!receipt.getCPCCustodianStatus().equalsIgnoreCase(ICMSConstant.STATE_ITEM_RECEIVED)))
				{
					newState = defProcess.getNextState(receipt.getCPCCustodianStatus(), action,
							ICMSConstant.INSTANCE_CHECKLIST_ITEM);
				}
				else
				{
					newState = defProcess.getNextState(receipt.getItemStatus(), action,ICMSConstant.INSTANCE_CHECKLIST_ITEM);
				}
				// CMS-2114 : update cpc cust status last update date when
				// allow_temp_uplift, allow_perm_uplift and allow_relodge
				if (action.equals(ICMSConstant.ACTION_ITEM_ALLOW_TEMP_UPLIFT)
						|| action.equals(ICMSConstant.ACTION_ITEM_ALLOW_PERM_UPLIFT)
						|| action.equals(ICMSConstant.ACTION_ITEM_ALLOW_RELODGE)) {
					receipt.setCPCCustodianStatusUpdateDate(DateUtil.getDate());
					receipt.setCustodianDocStatus(newState);
					newItems = new OBCheckListItem[1];
					newItems[0] = receipt;

				}
				else {
					receipt.setLastUpdateDate(DateUtil.getDate()); // CR CMS-382
					receipt.setItemStatus(newState);
					//CY : Added this to take from cpc custodian status instead from status. Due to status "COMPLETED" For items that are lodged previously
					if(receipt.getCPCCustodianStatus() != null)
					{
                        if (requireCustodianApproval(newState)) {
                            receipt.setItemStatus(ICMSConstant.STATE_ITEM_COMPLETED);
						    receipt.setCPCCustodianStatus(newState);    
                        } else if (!receipt.getCPCCustodianStatus().equals(ICMSConstant.STATE_ITEM_RECEIVED)) {
                            receipt.setItemStatus(ICMSConstant.STATE_ITEM_COMPLETED);
						    receipt.setCPCCustodianStatus(newState);    
                        }
						//receipt.setItemStatus(ICMSConstant.STATE_ITEM_COMPLETED);
						//receipt.setCPCCustodianStatus(newState);
					}
                    /*
                    else if(requireCustodianApproval(newState))
					{
						receipt.setItemStatus(ICMSConstant.STATE_ITEM_COMPLETED);
						receipt.setCPCCustodianStatus(newState);
					}
					*/
					if (action.equals(ICMSConstant.ACTION_ITEM_RENEW)) {
						// renew the expired receipt
						ICheckListItem newItem = renewCheckListReceipt(receipt);
						newItems = new OBCheckListItem[2];
						newItems[0] = receipt;
						newItems[1] = newItem;
					}
					else {
						newItems = new OBCheckListItem[1];
						newItems[0] = receipt;
					}
				}
				return newItems;
			}
			catch (Exception e) {
				throw new CheckListException("Exception in retrieveNextCheckListReceipts: " + e.toString());
			}
		}
		else {
			return null;
		}

	}

	/**
	 * Helper method to create new checklist receipt from its expired receipt.
	 * 
	 * @param expiredReceipt expired checklist receipt
	 * @return renewed checklist receipt
	 * @throws CheckListException
	 */
	private ICheckListItem renewCheckListReceipt(ICheckListItem expiredReceipt) throws CheckListException {
		try {
			ICheckListItem newItem = (ICheckListItem) CommonUtil.deepClone(expiredReceipt);
			newItem.setCheckListItemID(ICMSConstant.LONG_INVALID_VALUE);
			// newItem.setCheckListItemRef (ICMSConstant.LONG_INVALID_VALUE);
			newItem.setCheckListItemRef(generateCheckListItemSeqNo()); // CMSSP-
			// 653
			newItem.setParentCheckListItemRef(expiredReceipt.getCheckListItemRef());
			newItem.setItemStatus(ICMSConstant.STATE_ITEM_AWAITING);
			newItem.setCPCCustodianStatus(null);
			newItem.setCustodianDocStatus(null);
			newItem.setDocRef(null);
			newItem.setFormNo(null);
			newItem.setDocDate(null);
			newItem.setExpiryDate(null);
			newItem.setDeferExpiryDate(null);
			newItem.setActionParty(null);
			newItem.setCreditApprover(null);
			newItem.setRemarks(null);
			return newItem;
		}
		catch (Exception e) {
			throw new CheckListException("Exception in createNewCheckListItem " + e.toString());
		}
	}

	/**
	 * To get the next state of the checklist item
	 * @param aCurrentState of String type
	 * @param anAction of String type
	 * @return HashMap - the next checklist item state and the custodian status
	 * @throws CheckListException on errors
	 */
	public HashMap getNextCheckListItemState(String aCurrentState, String aCustodianState, String anAction)
			throws CheckListException {
		try {
			DefaultTrxProcess defProcess = new DefaultTrxProcess();
			String state = defProcess.getNextState(aCurrentState, anAction, ICMSConstant.INSTANCE_CHECKLIST_ITEM);
			DefaultLogger.debug(this, "current state=" + aCurrentState + ", operation=" + anAction + ", stateins="
					+ ICMSConstant.INSTANCE_CHECKLIST_ITEM + ", next state=" + state);
			HashMap map = new HashMap();
			if (isCustodianState(state)) {
				map.put(ICMSConstant.CHECKLIST_ITEM_STATE, aCurrentState);
				map.put(ICMSConstant.CUSTODIAN_STATE, state);
				return map;
			}
			map.put(ICMSConstant.CHECKLIST_ITEM_STATE, state);
			map.put(ICMSConstant.CUSTODIAN_STATE, aCustodianState);
			return map;
		}
		catch (Exception ex) {
			rollback();
			throw new CheckListException("Exception in getNextCheckListItemState: " + ex.toString());
		}
	}

	/**
	 * Reset a checklist item based on the actual checklist item in the trx
	 * value object
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @return ICheckList - the checklist with the resetted checklist item
	 * @throws CheckListException on errors
	 */
	public ICheckList resetCheckListItem(ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList,
			long aCheckListItemRef) throws CheckListException {
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The ICheckListTrxValue is null !!!");
		}
		if (anICheckList == null) {
			throw new CheckListException("The ICheckList is null !!!");
		}
		if (aCheckListItemRef == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new CheckListException("Invalid CheckList Item ID !!!");
		}
		if (anICheckListTrxValue.getCheckList() == null) {
			throw new CheckListException("No Actual CheckList info available !!!");
		}
		ICheckListItem[] tempList = anICheckList.getCheckListItemList();
		if ((tempList == null) || (tempList.length == 0)) {
			throw new CheckListException("No CheckList item available in the checklist !!!");
		}

		ICheckListTrxValue trxValue = getCheckListByTrxID(anICheckListTrxValue.getTransactionID());
		ICheckListItem[] orgList = trxValue.getCheckList().getCheckListItemList();
		if ((orgList == null) || (orgList.length == 0)) {
			throw new CheckListException("No CheckList item available in the original checklist !!!");
		}

		// int index = Integer.MIN_VALUE;
		for (int ii = 0; ii < orgList.length; ii++) {
			if (orgList[ii].getCheckListItemRef() == aCheckListItemRef) {
				if (tempList[ii].getCheckListItemRef() == aCheckListItemRef) {
					tempList[ii] = orgList[ii];
					break;
				}
				for (int jj = 0; jj < tempList.length; jj++) {
					if (tempList[jj].getCheckListItemRef() == aCheckListItemRef) {
						tempList[jj] = orgList[ii];
						break;
					}
				}
				break;
			}
		}
		anICheckList.setCheckListItemList(tempList);
		return anICheckList;
	}

	/**
	 * To return false if there is any pending trx
	 * @param anICheckListOwner of ICheckListOwner type
	 * @return boolean - true if there already exist and false otherwise
	 * @throws CheckListException on errors
	 */
	public int allowCheckListTrx(ICheckListOwner anICheckListOwner) throws CheckListException {
		try {
			if (anICheckListOwner instanceof ICCCheckListOwner) {
				IDocumentLocationProxyManager docMgr = DocumentLocationProxyManagerFactory.getProxyManager();
				ICCCheckListOwner owner = (ICCCheckListOwner) anICheckListOwner;
				if (docMgr.hasPendingCCDocumentLocationTrx(owner.getLimitProfileID(), owner.getSubOwnerType(), owner
						.getSubOwnerID())) {
					return ICMSConstant.HAS_PENDING_DOC_LOC_TRX;
				}
			}
			String[] statusList = { ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_PENDING_CREATE,
					ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT, ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ,
					ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ, ICMSConstant.STATE_PENDING_MGR_VERIFY,
					// New Pending status for multi-level approval
					ICMSConstant.STATE_PENDING_AUTH, ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					// New Rejected status for multi-level approval
					ICMSConstant.STATE_OFFICER_REJECTED };

			CheckListSearchResult[] resultList = getCheckList(anICheckListOwner, statusList);
			if ((resultList == null) || (resultList.length == 0)) {
				return ICMSConstant.NO_PENDING_TRX;
			}
			return ICMSConstant.HAS_PENDING_CHECKLIST_TRX;
		}
		catch (DocumentLocationException ex) {
			rollback();
			throw new CheckListException("Caught DocumentLocationException in allowCheckListTrx", ex);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in allowCheckListTrx", ex);
		}
	}

	private boolean allowCheckListTrxCreation(ICheckListOwner anICheckListOwner) throws CheckListException {
		try {
			String[] statusList = { ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_PENDING_CREATE,
					ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT, ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ,
					ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ, ICMSConstant.STATE_PENDING_MGR_VERIFY,
					ICMSConstant.STATE_ACTIVE,
					// New Pending status for multi-level approval
					ICMSConstant.STATE_PENDING_AUTH, ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					// New Rejected status for multi-level approval
					ICMSConstant.STATE_OFFICER_REJECTED };
			CheckListSearchResult[] resultList = getCheckList(anICheckListOwner, statusList);
			if ((resultList == null) || (resultList.length == 0)) {
				return true;
			}
			return false;
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in allowCheckListTrx", ex);
		}
	}

	/**
	 * To close the collateral related checklist
	 * @param aCollateralID of long type
	 * @throws CheckListException on errors
	 */
	public void systemCloseCollateralCheckListTrx(ITrxContext anITrxContext, long aCollateralID)
			throws CheckListException {
		if (anITrxContext == null) {
			throw new CheckListException("The ITrxContext is null !!!");
		}

		if (aCollateralID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new CheckListException("The collateral ID is invalid !!!");
		}

		try {
			ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(
					com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE, aCollateralID);
			String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_ACTIVE,
					ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT,
					ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ, ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
					ICMSConstant.STATE_PENDING_MGR_VERIFY,
					// New Pending status for multi-level approval
					ICMSConstant.STATE_PENDING_AUTH, ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					// New Rejected status for multi-level approval
					ICMSConstant.STATE_OFFICER_REJECTED };
			CheckListSearchResult[] resultList = getCheckList(owner, statusList);
			if ((resultList != null) && (resultList.length > 0)) {
				for (int ii = 0; ii < resultList.length; ii++) {
					if (resultList[ii].getTrxID() != null) {
						ICheckListTrxValue trxValue = getCheckListByTrxID(resultList[ii].getTrxID());
						if (custodianAllPermOut(trxValue.getCheckList())) {
							systemCloseCheckList(anITrxContext, trxValue);
						}
						else {
							throw new CheckListException("Cannot close checklist "
									+ trxValue.getCheckList().getCheckListID() + " as doc not perm out yet !!! ");
						}
					}
				}
			}
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in systemCloseCollateralCheckListTrx", ex);
		}
	}

	/**
	 * To delete the collateral related checklist
	 * @param anICollateral of ICollateral type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteCollateralCheckListTrx(ICollateral anICollateral) throws CheckListException {
		if (anICollateral == null) {
			throw new CheckListException("The ICollateral is null !!!");
		}

		try {
			ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(
					com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE, anICollateral
							.getCollateralID());
			String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_ACTIVE,
					ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT,
					ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ, ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
					ICMSConstant.STATE_PENDING_MGR_VERIFY,
					// New Pending status for multi-level approval
					ICMSConstant.STATE_PENDING_AUTH, ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					// New Rejected status for multi-level approval
					ICMSConstant.STATE_OFFICER_REJECTED };
			CheckListSearchResult[] resultList = getCheckList(owner, statusList);
			ISCCertificateProxyManager sccProxy = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = null;
			if ((resultList != null) && (resultList.length > 0)) {
				for (int ii = 0; ii < resultList.length; ii++) {
					if (resultList[ii].getTrxID() != null) {
						ICheckListTrxValue trxValue = getCheckListByTrxID(resultList[ii].getTrxID());
						if (ICMSConstant.STATE_PENDING_CREATE.equals(trxValue)) {
							systemCloseCheckList(new OBTrxContext(), trxValue);
						}
						else {
							systemDeleteCheckList(null, trxValue);
							limitProfile = limitProxy.getLimitProfile(resultList[ii].getLimitProfileID());
							sccProxy.systemClosePartialSCC(limitProfile);
							sccProxy.systemCloseSCC(limitProfile);
						}
					}
				}
			}
			systemDeleteAllPledgorCheckListByCollateral(anICollateral);
		}
		catch (LimitException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeleteCollateralCheckListTrx", ex);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeleteCollateralCheckListTrx", ex);
		}
		catch (SCCertificateException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeleteCollateralCheckListTrx", ex);
		}
	}

	/**
	 * To delete all the cc checklist related to a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteCCCheckListTrx(ILimitProfile anILimitProfile) throws CheckListException {
		if (anILimitProfile == null) {
			throw new CheckListException("The ILimitProfile is invalid !!!");
		}

		try {
			ICCCheckListOwner owner = new OBCCCheckListOwner(anILimitProfile.getLimitProfileID(),
					com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE, null);
			String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_ACTIVE,
					ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT,
					ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ, ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
					ICMSConstant.STATE_PENDING_MGR_VERIFY,
					// New Pending status for multi-level approval
					ICMSConstant.STATE_PENDING_AUTH, ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					// New Rejected status for multi-level approval
					ICMSConstant.STATE_OFFICER_REJECTED };
			CheckListSearchResult[] resultList = getCheckList(owner, statusList);
			if ((resultList != null) && (resultList.length > 0)) {
				for (int ii = 0; ii < resultList.length; ii++) {
					if (resultList[ii].getTrxID() != null) {
						ICheckListTrxValue trxValue = getCheckListByTrxID(resultList[ii].getTrxID());
						if (ICMSConstant.STATE_PENDING_CREATE.equals(trxValue.getStatus())) {
							systemCloseCheckList(new OBTrxContext(), trxValue);
						}
						else {
							systemDeleteCheckList(null, trxValue);
						}
					}
				}
			}
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeleteCCCheckListTrx", ex);
		}
	}

	/**
	 * To renew all the cc checklist related to a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CheckListException on errors
	 */
	public void systemRenewCCCheckListTrx(ILimitProfile anILimitProfile) throws CheckListException {
		if (anILimitProfile == null) {
			throw new CheckListException("The ILimitProfile is invalid !!!");
		}

		try {
			ICCCheckListOwner owner = new OBCCCheckListOwner(anILimitProfile.getLimitProfileID(),
					com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE, null);
			String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_ACTIVE,
					ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT,
					ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ, ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
					ICMSConstant.STATE_PENDING_MGR_VERIFY,
					// New Pending status for multi-level approval
					ICMSConstant.STATE_PENDING_AUTH, ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					// New Rejected status for multi-level approval
					ICMSConstant.STATE_OFFICER_REJECTED };
			CheckListSearchResult[] resultList = getCheckList(owner, statusList);
			if ((resultList != null) && (resultList.length > 0)) {
				for (int ii = 0; ii < resultList.length; ii++) {
					if (resultList[ii].getTrxID() != null) {
						ICheckListTrxValue trxValue = getCheckListByTrxID(resultList[ii].getTrxID());
						ICheckList checkList = trxValue.getCheckList();

						if (checkList == null) {
							systemCloseCheckList(new OBTrxContext(), trxValue);
						}
						else {
							if (!ICMSConstant.STATE_DELETED.equals(checkList.getCheckListStatus())) {
								// DefaultLogger.debug(this, "CHeckListID: " +
								// checkList.getCheckListID());
								// checkList.setCheckListStatus(ICMSConstant.
								// STATE_CHECKLIST_IN_PROGRESS);
								systemUpdateCheckList(trxValue, checkList);
							}
						}
					}
				}
			}
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("SearchDAOException in systemRenewCCCheckListTrx", ex);
		}
		catch (Exception e) {
			rollback();
			throw new CheckListException("Unknown Exception in systemRenewCCCheckListTrx", e);
		}
	}

	/**
	 * System create checklist trx and checklist.
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the checklist trx that is updated
	 * @throws CheckListException on error
	 */
	public ICheckListTrxValue systemCreateCheckList(ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList)
			throws CheckListException {
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The ICheckListTrxValue is null!!!");
		}
		if (anICheckList == null) {
			throw new CheckListException("The ICheckList to be created is null !!!");
		}
		anICheckListTrxValue = formulateTrxValue(null, anICheckListTrxValue, anICheckList,
				ICMSConstant.CHECKLIST_SYSTEM);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_CREATE_CHECKLIST);
		return operate(anICheckListTrxValue, param);
	}

	/**
	 * System create checklist trx and checklist.
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the checklist trx that is updated
	 * @throws CheckListException on error
	 */
	public ICheckListTrxValue systemCreateDocumentCheckList(ICheckListTrxValue anICheckListTrxValue,
			ICheckList anICheckList) throws CheckListException {
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The ICheckListTrxValue is null!!!");
		}
		if (anICheckList == null) {
			throw new CheckListException("The ICheckList to be created is null !!!");
		}
		anICheckListTrxValue = formulateTrxValue(null, anICheckListTrxValue, anICheckList,
				ICMSConstant.CHECKLIST_SYSTEM);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_CREATE_DOCUMENT_CHECKLIST);
		return operate(anICheckListTrxValue, param);
	}

	/**
	 * System triggered update to the checklist trx. Those pending trx will be
	 * reset back to ACTIVE.
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @param anICheckList of ICheckList type
	 * @return ICheckListTrxValue - the checklist trx that is updated
	 * @throws CheckListException on error
	 */
	public ICheckListTrxValue systemUpdateCheckList(ICheckListTrxValue anICheckListTrxValue, ICheckList anICheckList)
			throws CheckListException {
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The ICheckListTrxValue to be updated is null!!!");
		}
		if (anICheckList == null) {
			throw new CheckListException("The ICheckList to be updated is null !!!");
		}
		anICheckListTrxValue = formulateTrxValue(null, anICheckListTrxValue, anICheckList,
				ICMSConstant.CHECKLIST_SYSTEM);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_UPDATE_CHECKLIST);
		return operate(anICheckListTrxValue, param);
	}

	/**
	 * System triggered direct update to the checklist trx. This allow updating
	 * of pending trx as well
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the checklist trx that is updated
	 * @throws CheckListException on error
	 */
	public ICheckListTrxValue directUpdateCheckList(ICheckListTrxValue anICheckListTrxValue) throws CheckListException {
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The ICheckListTrxValue to be updated is null!!!");
		}
		// anICheckListTrxValue = formulateTrxValue(null, anICheckListTrxValue,
		// anICheckList, ICMSConstant.CHECKLIST_SYSTEM);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_DIRECT_UPDATE_CHECKLIST);
		return operate(anICheckListTrxValue, param);
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
			ISCCertificateProxyManager sccProxy = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
			ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(aLimitProfileID, aCollateralID);
			String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_ACTIVE,
					ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT,
					ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ, ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
					ICMSConstant.STATE_PENDING_MGR_VERIFY,
					// New Pending status for multi-level approval
					ICMSConstant.STATE_PENDING_AUTH, ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					// New Rejected status for multi-level approval
					ICMSConstant.STATE_OFFICER_REJECTED };
			CheckListSearchResult[] resultList = getCheckList(owner, statusList);
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = null;
			if ((resultList != null) && (resultList.length > 0)) {
				for (int ii = 0; ii < resultList.length; ii++) {
					if (resultList[ii].getTrxID() != null) {
						ICheckListTrxValue trxValue = getCheckListByTrxID(resultList[ii].getTrxID());
						if (ICMSConstant.STATE_PENDING_CREATE.equals(trxValue.getStatus())) {
							systemCloseCheckList(new OBTrxContext(), trxValue);
						}
						else {
							systemDeleteCheckList(null, trxValue);
							limitProfile = limitProxy.getLimitProfile(resultList[ii].getLimitProfileID());
							sccProxy.systemClosePartialSCC(limitProfile);
							sccProxy.systemCloseSCC(limitProfile);
						}
					}
				}
			}
		}
		catch (LimitException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeleteCollateralCheckListTrxOnly", ex);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeleteCollateralCheckListTrxOnly", ex);
		}
		catch (SCCertificateException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeleteCollateralCheckListTrxOnly", ex);
		}
	}

	public void systemDeleteCollateralCheckListTrx(long aLimitProfileID, long aCollateralID,
			long[] aDeleteLimitSecMapList) throws CheckListException {
		// if (deleteCollateralCheckList(aLimitProfileID, aCollateralID))
		// {
		systemDeleteCollateralCheckListTrxOnly(aLimitProfileID, aCollateralID);
		systemDeleteAllPledgorCheckListTrx(aLimitProfileID, aCollateralID, aDeleteLimitSecMapList);
		// }
	}

	/**
	 * To delete the collateral related checklist and the pledgor checklist
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteCollateralCheckListTrx(long aLimitProfileID, long aCollateralID) throws CheckListException {
		systemDeleteCollateralCheckListTrxOnly(aLimitProfileID, aCollateralID);
		systemDeleteAllPledgorCheckListTrx(aLimitProfileID, aCollateralID);
	}

	/**
	 * To renew all the collateral related checklists
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CheckListException on errors
	 */
	public void systemRenewCollateralCheckListTrx(ILimitProfile anILimitProfile) throws CheckListException {
		if (anILimitProfile == null) {
			throw new CheckListException("The ILimitProfile is null!!!");
		}
		long limitProfileID = anILimitProfile.getLimitProfileID();
		ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
		ArrayList colIDList = new ArrayList();
		if ((limitList != null) && (limitList.length > 0)) {
			for (int ii = 0; ii < limitList.length; ii++) {
				ICollateralAllocation[] colAllocList = limitList[ii].getNonDeletedCollateralAllocations();
				if ((colAllocList != null) && (colAllocList.length > 0)) {
					for (int jj = 0; jj < colAllocList.length; jj++) {
						if (!colIDList.contains(new Long(colAllocList[jj].getCollateral().getCollateralID()))) {
							systemRenewCollateralCheckListTrx(limitProfileID, colAllocList[jj].getCollateral()
									.getCollateralID());
							colIDList.add(new Long(colAllocList[jj].getCollateral().getCollateralID()));
						}
					}
				}
			}
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
			ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(aLimitProfileID, aCollateralID);
			String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_ACTIVE,
					ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT,
					ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ, ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
					ICMSConstant.STATE_PENDING_MGR_VERIFY,
					// New Pending status for multi-level approval
					ICMSConstant.STATE_PENDING_AUTH, ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					// New Rejected status for multi-level approval
					ICMSConstant.STATE_OFFICER_REJECTED };
			CheckListSearchResult[] resultList = getCheckList(owner, statusList);
			if ((resultList != null) && (resultList.length > 0)) {
				for (int ii = 0; ii < resultList.length; ii++) {
					if (resultList[ii].getTrxID() != null) {

						ICheckListTrxValue trxValue = getCheckListByTrxID(resultList[ii].getTrxID());
						ICheckList aChecklist = trxValue.getCheckList();

						if (null == aChecklist) {
							systemCloseCheckList(new OBTrxContext(), trxValue);
						}
						else {
							// ICheckList checkList = trxValue.getCheckList();
							if (!ICMSConstant.STATE_DELETED.equals(aChecklist.getCheckListStatus())) {
								// checkList.setCheckListStatus(ICMSConstant.
								// STATE_CHECKLIST_IN_PROGRESS);
								systemUpdateCheckList(trxValue, aChecklist);
							}
						}
					}
				}
			}
			// systemDeleteAllPledgorCheckListTrx(aLimitProfileID,
			// aCollateralID);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in systemRenewCollateralCheckListTrx", ex);
		}
		catch (Exception e) {
			rollback();
			throw new CheckListException("Unknown Exception in systemRenewCollateralCheckListTrx", e);
		}
	}

	/**
	 * To delete the non borrower checklist related to a customer
	 * @param aCustomerID of long type
	 * @throws CheckListException on error
	 */
	public void systemDeleteNonBorrowerCheckListTrx(long aCustomerID) throws CheckListException {
		try {
			ICCCheckListOwner owner = new OBCCCheckListOwner(ICMSConstant.LONG_MIN_VALUE, aCustomerID,
					ICMSConstant.CHECKLIST_NON_BORROWER);
			String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_ACTIVE,
					ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT,
					ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ, ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
					ICMSConstant.STATE_PENDING_MGR_VERIFY,
					// New Pending status for multi-level approval
					ICMSConstant.STATE_PENDING_AUTH, ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					// New Rejected status for multi-level approval
					ICMSConstant.STATE_OFFICER_REJECTED };
			CheckListSearchResult[] resultList = getCheckList(owner, statusList);
			if ((resultList != null) && (resultList.length > 0)) {
				ICCCertificateProxyManager cccProxy = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
				for (int ii = 0; ii < resultList.length; ii++) {
					if (resultList[ii].getTrxID() != null) {
						ICheckListTrxValue trxValue = getCheckListByTrxID(resultList[ii].getTrxID());
						if (ICMSConstant.STATE_PENDING_CREATE.equals(trxValue.getStatus())) {
							systemCloseCheckList(new OBTrxContext(), trxValue);
						}
						else {
							systemDeleteCheckList(null, trxValue);
							cccProxy.systemCloseNonBorrowerCCC(aCustomerID);
						}
					}
				}
			}
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeleteNonBorrowerCheckListTrx", ex);
		}
		catch (CCCertificateException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeleteNonBorrowerCheckListTrx", ex);
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
			ICCCheckListOwner owner = new OBCCCheckListOwner(aLimitProfileID, aCustomerID,
					ICMSConstant.CHECKLIST_MAIN_BORROWER);
			String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_ACTIVE,
					ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT,
					ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ, ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
					ICMSConstant.STATE_PENDING_MGR_VERIFY,
					// New Pending status for multi-level approval
					ICMSConstant.STATE_PENDING_AUTH, ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					// New Rejected status for multi-level approval
					ICMSConstant.STATE_OFFICER_REJECTED };
			CheckListSearchResult[] resultList = getCheckList(owner, statusList);
			if ((resultList != null) && (resultList.length > 0)) {
				ICCCertificateProxyManager cccProxy = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
				for (int ii = 0; ii < resultList.length; ii++) {
					if (resultList[ii].getTrxID() != null) {
						ICheckListTrxValue trxValue = getCheckListByTrxID(resultList[ii].getTrxID());
						if (ICMSConstant.STATE_PENDING_CREATE.equals(trxValue.getStatus())) {
							systemCloseCheckList(new OBTrxContext(), trxValue);
						}
						else {
							systemDeleteCheckList(null, trxValue);
							cccProxy.systemCloseMainBorrowerCCC(resultList[ii].getLimitProfileID(), aCustomerID);
						}
					}
				}
			}
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeleteMainBorrowerCheckListTrx", ex);
		}
		catch (CCCertificateException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeleteMainBorrowerCheckListTrx", ex);
		}
	}

	private void systemDeleteAllPledgorCheckListByCollateral(ICollateral anICollateral) throws CheckListException {
		ICollateralPledgor[] pledgors = anICollateral.getPledgors();
		if ((pledgors == null) || (pledgors.length == 0)) {
			return;
		}

		int numOfColPledged = 0;
		try {
			ICollateralProxy colProxy = CollateralProxyFactory.getProxy();
			for (int ii = 0; ii < pledgors.length; ii++) {
				numOfColPledged = colProxy.getCollateralCountForPledgor(pledgors[ii].getPledgorID());
				if (numOfColPledged == 0) {
					// system delete all pledgor checklists since this pledgor
					// only pledged for 1 collateral
					systemDeletePledgorCheckListTrx(ICMSConstant.LONG_MIN_VALUE, pledgors[ii].getPledgorID());
				}
				else {
					ICCCheckListOwner owner = new OBCCCheckListOwner(ICMSConstant.LONG_MIN_VALUE, pledgors[ii]
							.getPledgorID(), ICMSConstant.CHECKLIST_PLEDGER);
					String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_ACTIVE,
							ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT,
							ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ, ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
							ICMSConstant.STATE_PENDING_MGR_VERIFY,
							// New Pending status for multi-level approval
							ICMSConstant.STATE_PENDING_AUTH, ICMSConstant.STATE_PENDING_OFFICE,
							ICMSConstant.STATE_PENDING_VERIFY,
							// New Rejected status for multi-level approval
							ICMSConstant.STATE_OFFICER_REJECTED };
					CheckListSearchResult[] resultList = getCheckList(owner, statusList);
					if ((resultList != null) && (resultList.length > 0)) {
						// HashMap colMap = new HashMap();
						ICCCertificateProxyManager cccProxy = CCCertificateProxyManagerFactory
								.getCCCertificateProxyManager();
						for (int jj = 0; jj < resultList.length; jj++) {
							if (deletePledgorCheckList(resultList[jj].getLimitProfileID(), anICollateral, pledgors[ii]
									.getPledgorID())) {
								if (resultList[jj].getTrxID() != null) {
									ICheckListTrxValue trxValue = getCheckListByTrxID(resultList[jj].getTrxID());
									if (ICMSConstant.STATE_PENDING_CREATE.equals(trxValue.getStatus())) {
										systemCloseCheckList(new OBTrxContext(), trxValue);
									}
									else {
										systemDeleteCheckList(null, trxValue);
										cccProxy.systemClosePledgorCCC(resultList[jj].getLimitProfileID(), pledgors[ii]
												.getPledgorID());
									}
								}
							}
						}
					}
				}
			}
		}
		catch (CollateralException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeleteAllPledgorCheckListByCollateral", ex);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeleteAllPledgorCheckListByCollateral", ex);
		}
		catch (CCCertificateException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeleteAllPledgorCheckListByCollateral", ex);
		}
	}

	private boolean deletePledgorCheckList(long aLimitProfileID, ICollateral anICollateral, long aPledgorID)
			throws CheckListException {
		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = limitProxy.getLimitProfile(aLimitProfileID);
			ILimit[] limitList = limitProfile.getNonDeletedLimits();
			if ((limitList != null) && (limitList.length > 0)) {
				ICollateralAllocation[] colAllocationList = null;
				ICollateral col = null;
				ICollateralProxy colProxy = CollateralProxyFactory.getProxy();
				for (int ii = 0; ii < limitList.length; ii++) {
					colAllocationList = limitList[ii].getNonDeletedCollateralAllocations();
					if ((colAllocationList != null) && (colAllocationList.length > 0)) {
						for (int jj = 0; jj < colAllocationList.length; jj++) {
							long collateralID = colAllocationList[jj].getCollateral().getCollateralID();
							if (collateralID != anICollateral.getCollateralID()) {
								col = colProxy.getCollateral(collateralID, true);
								ICollateralPledgor[] pledgorList = col.getPledgors();
								for (int kk = 0; kk < pledgorList.length; kk++) {
									if (pledgorList[kk].getPledgorID() == aPledgorID) {
										return false;
									}
								}
							}
						}
					}
				}
				// Assumption: Clean type BCA is not using this method
				return true;
			}
			else {
				return false;
			}
		}
		catch (LimitException ex) {
			throw new CheckListException("Exception in deletePledgorCheckList", ex);
		}
		catch (CollateralException ex) {
			throw new CheckListException("Exception in deletePledgorCheckList", ex);
		}
	}

	private void systemDeleteAllPledgorCheckListTrx(long aLimitProfileID, long aCollateralID) throws CheckListException {
		systemDeleteAllPledgorCheckListTrx(aLimitProfileID, aCollateralID, null);
	}

	private void systemDeleteAllPledgorCheckListTrx(long aLimitProfileID, long aCollateralID,
			long[] aDeletedLimitSecMapList) throws CheckListException {
		try {
			// ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			// ILimitProfile limitProfile =
			// limitProxy.getLimitProfile(aLimitProfileID);
			ICollateralProxy colProxy = CollateralProxyFactory.getProxy();
			ICollateral col = colProxy.getCollateral(aCollateralID, true);
			if (col != null) {
				ICollateralPledgor[] pledgorList = col.getPledgors();
				if ((pledgorList != null) && (pledgorList.length > 0)) {
					for (int ii = 0; ii < pledgorList.length; ii++) {
						if (deletePledgorCheckList(aLimitProfileID, pledgorList[ii].getPledgorID(),
								aDeletedLimitSecMapList)) {
							systemDeletePledgorCheckListTrx(aLimitProfileID, pledgorList[ii].getPledgorID());
						}
					}
				}
			}
		}
		catch (CollateralException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeleteAllPledgorCheckListTrx", ex);
		}
		/*
		 * catch(LimitException ex) { rollback(); throw new
		 * CheckListException("Exception in systemDeleteAllPledgorCheckListTrx",
		 * ex); }
		 */
	}

	private boolean deleteCoBorrowerCheckList(long aLimitProfileID, long aCustomerID) throws CheckListException {
		try {
			int count = getLimitProfileCoBorrowerCount(aLimitProfileID, aCustomerID);
			if (count > 0) {
				return false;
			}
			return true;

		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in deleteCoBorrowerCheckList", ex);
		}
	}

	/*
	 * private boolean deleteCollateralCheckList(long aLimitProfileID, long
	 * aCollateralID) throws CheckListException { try { int count =
	 * getLimitProfileCollateralCount(aLimitProfileID, aCollateralID); if (count
	 * > 0) { return false; } return true; } catch(SearchDAOException ex) {
	 * rollback(); throw new
	 * CheckListException("Exception in deleteCollateralCheckList", ex); } }
	 */

	private boolean deletePledgorCheckList(long aLimitProfileID, long aPledgorID, long[] aDeletedLimitSecMapList)
			throws CheckListException {
		try {
			int count = getLimitProfilePledgorCount(aLimitProfileID, aPledgorID, aDeletedLimitSecMapList);
			if (count > 0) {
				return false;
			}
			return true;
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in deletePledgorCheckList", ex);
		}
	}

	/*
	 * private boolean deletePledgorCheckList(long aLimitProfileID, long
	 * aPledgorID) throws CheckListException { try { int count =
	 * getLimitProfilePledgorCount(aLimitProfileID, aPledgorID); if (count > 0)
	 * { return false; } return true; } catch(SearchDAOException ex) {
	 * rollback(); throw new
	 * CheckListException("Exception in deletePledgorCheckList", ex); } }
	 */

	/**
	 * To delete all the cc checklist related to a limit profile and a
	 * coborrower
	 * @param aCustomerID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeleteCoBorrowerCheckListTrx(long aCustomerID) throws CheckListException {
		systemDeleteCoBorrowerCheckListTrx(ICMSConstant.LONG_INVALID_VALUE, aCustomerID);
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
			// to check if this coborrower is still existing in BCA before
			// allowing checklist deletion
			/*
			 * if (!deleteCoBorrowerCheckList(aLimitProfileID, aCustomerID)) {
			 * return; }
			 */
			ICCCheckListOwner owner = new OBCCCheckListOwner(aLimitProfileID, aCustomerID,
					ICMSConstant.CHECKLIST_CO_BORROWER);
			String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_ACTIVE,
					ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT,
					ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ, ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
					ICMSConstant.STATE_PENDING_MGR_VERIFY,
					// New Pending status for multi-level approval
					ICMSConstant.STATE_PENDING_AUTH, ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					// New Rejected status for multi-level approval
					ICMSConstant.STATE_OFFICER_REJECTED };
			CheckListSearchResult[] resultList = getCheckList(owner, statusList);
			if ((resultList != null) && (resultList.length > 0)) {
				ICCCertificateProxyManager cccProxy = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
				for (int ii = 0; ii < resultList.length; ii++) {
					if (resultList[ii].getTrxID() != null) {
						ICheckListTrxValue trxValue = getCheckListByTrxID(resultList[ii].getTrxID());
						if (ICMSConstant.STATE_PENDING_CREATE.equals(trxValue.getStatus())) {
							systemCloseCheckList(new OBTrxContext(), trxValue);
						}
						else {
							systemDeleteCheckList(null, trxValue);
						}
					}
				}
			}
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeleteCoBorrowerCheckListTrx", ex);
		}
	}

	/**
	 * To delete all the cc checklist related to a pledgor
	 * @param aPledgorID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeletePledgorCheckListTrx(long aPledgorID) throws CheckListException {
		systemDeletePledgorCheckListTrx(ICMSConstant.LONG_INVALID_VALUE, aPledgorID);
	}

	/**
	 * To delete all the cc checklist related to a limit profile and a pledgor
	 * @param aLimitProfileID of long type
	 * @param aPledgorID of long type
	 * @throws CheckListException on errors
	 */
	public void systemDeletePledgorCheckListTrx(long aLimitProfileID, long aPledgorID) throws CheckListException {
		try {
			ICCCertificateProxyManager cccProxy = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
			ICCCheckListOwner owner = new OBCCCheckListOwner(aLimitProfileID, aPledgorID,
					ICMSConstant.CHECKLIST_PLEDGER);
			String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_ACTIVE,
					ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT,
					ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ, ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
					ICMSConstant.STATE_PENDING_MGR_VERIFY,
					// New Pending status for multi-level approval
					ICMSConstant.STATE_PENDING_AUTH, ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					// New Rejected status for multi-level approval
					ICMSConstant.STATE_OFFICER_REJECTED };
			CheckListSearchResult[] resultList = getCheckList(owner, statusList);
			if ((resultList != null) && (resultList.length > 0)) {
				for (int ii = 0; ii < resultList.length; ii++) {
					if (resultList[ii].getTrxID() != null) {
						ICheckListTrxValue trxValue = getCheckListByTrxID(resultList[ii].getTrxID());
						if (ICMSConstant.STATE_PENDING_CREATE.equals(trxValue.getStatus())) {
							systemCloseCheckList(new OBTrxContext(), trxValue);
						}
						else {
							systemDeleteCheckList(null, trxValue);
							cccProxy.systemClosePledgorCCC(resultList[ii].getLimitProfileID(), aPledgorID);
						}
					}
				}
			}
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeletePledgorCheckListTrx", ex);
		}
		catch (CCCertificateException ex) {
			rollback();
			throw new CheckListException("Exception in systemDeletePledgorCheckListTrx", ex);
		}
	}

	/**
	 * To check if all the custodian doc are being perm uplifted.
	 * @param anICheckList of ICheckList type
	 * @return boolean - true if all the custodian docs are being perm out and
	 *         false otherwise
	 */
	private boolean custodianAllPermOut(ICheckList anICheckList) throws CheckListException {
		try {
			ICustodianProxyManager mgr = CustodianProxyManagerFactory.getCustodianProxyManager();
			CustodianSearchCriteria criteria = new CustodianSearchCriteria();
			criteria.setCheckListID(anICheckList.getCheckListID());
			String[] statusList = { ICMSConstant.STATE_RECEIVED, ICMSConstant.STATE_PENDING_RECEIVED,
					ICMSConstant.STATE_PENDING_LODGE, ICMSConstant.STATE_LODGED,
					ICMSConstant.STATE_PENDING_AUTHZ_TEMP_UPLIFT, ICMSConstant.STATE_PENDING_AUTHZ_PERM_UPLIFT,
					ICMSConstant.STATE_AUTHZ_TEMP_UPLIFTED, ICMSConstant.STATE_AUTHZ_PERM_UPLIFTED,
					ICMSConstant.STATE_PENDING_TEMP_UPLIFT, ICMSConstant.STATE_TEMP_UPLIFTED,
					ICMSConstant.STATE_PENDING_PERM_UPLIFT };
			criteria.setTrxStatus(statusList);
			SearchResult result = mgr.getDocList(criteria);
			if (result == null) {
				return true;
			}
			Collection col = result.getResultList();
			DefaultLogger.debug(this, "No of cust doc: " + col.size());
			if (col.size() == 0) {
				return true;
			}
			return false;
		}
		catch (CustodianException ex) {
			rollback();
			throw new CheckListException("Exception in custodianAllPermOut", ex);
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
	public ICheckListTrxValue systemCloseCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		if (anITrxContext == null) {
			throw new CheckListException("The anITrxContext to be created is null!!!");
		}
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The ICheckListTrxValue to be created is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, ICMSConstant.CHECKLIST_SYSTEM);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_CHECKLIST);
		return operate(anICheckListTrxValue, param);
	}

	/**
	 * System delete a checklist trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemDeleteCheckList(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		/*
		 * if (anITrxContext == null) { throw new
		 * CheckListException("The anITrxContext to be created is null!!!"); }
		 */
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The ICheckListTrxValue to be created is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(anITrxContext, anICheckListTrxValue, ICMSConstant.CHECKLIST_SYSTEM);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_DELETE_CHECKLIST);
		return operate(anICheckListTrxValue, param);
	}

	/**
	 * System trigger for waiver generation
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemGenerateCheckListWaiver(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The ICheckListTrxValue to be created is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(null, anICheckListTrxValue, ICMSConstant.CHECKLIST_SYSTEM);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_GENERATE_CHECKLIST_WAIVER);
		return operate(anICheckListTrxValue, param);
	}

	/**
	 * System trigger for reject waiver generation
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemRejectGenerateCheckListWaiver(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The ICheckListTrxValue to be created is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(null, anICheckListTrxValue, ICMSConstant.CHECKLIST_SYSTEM);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_REJECT_GENERATE_CHECKLIST_WAIVER);
		return operate(anICheckListTrxValue, param);
	}

	/**
	 * System trigger for approving waiver generation
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemApproveGenerateCheckListWaiver(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The ICheckListTrxValue to be created is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(null, anICheckListTrxValue, ICMSConstant.CHECKLIST_SYSTEM);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_APPROVE_GENERATE_CHECKLIST_WAIVER);
		return operate(anICheckListTrxValue, param);
	}

	/**
	 * System trigger for deferral generation
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemGenerateCheckListDeferral(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The ICheckListTrxValue to be created is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(null, anICheckListTrxValue, ICMSConstant.CHECKLIST_SYSTEM);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_GENERATE_CHECKLIST_DEFERRAL);
		return operate(anICheckListTrxValue, param);
	}

	/**
	 * System trigger for reject deferral generation
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemRejectGenerateCheckListDeferral(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The ICheckListTrxValue to be created is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(null, anICheckListTrxValue, ICMSConstant.CHECKLIST_SYSTEM);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_REJECT_GENERATE_CHECKLIST_DEFERRAL);
		return operate(anICheckListTrxValue, param);
	}

	/**
	 * System trigger for approving deferral generation
	 * @param anICheckListTrxValue of ICheckListTrxValue type
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws CheckListException on errors
	 */
	public ICheckListTrxValue systemApproveGenerateCheckListDeferral(ICheckListTrxValue anICheckListTrxValue)
			throws CheckListException {
		if (anICheckListTrxValue == null) {
			throw new CheckListException("The ICheckListTrxValue to be created is null!!!");
		}
		anICheckListTrxValue = formulateTrxValue(null, anICheckListTrxValue, ICMSConstant.CHECKLIST_SYSTEM);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_APPROVE_GENERATE_CHECKLIST_DEFERRAL);
		return operate(anICheckListTrxValue, param);
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
			HashMap map = new HashMap();
			String[] statusList = { ICMSConstant.STATE_ITEM_COMPLETED };
			ICheckListAudit[] auditList = getCheckListAuditList(aLimitProfileID, statusList);
			sortAuditList(map, auditList);
			return map;
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException(
					"Exception in getCheckListAuditList with limit Profile ID: " + aLimitProfileID, ex);
		}
	}

	private void sortAuditList(HashMap aMap, ICheckListAudit[] auditList) throws CheckListException {
		if ((auditList == null) || (auditList.length == 0)) {
			return;
		}
		ArrayList mainBorrowerList = new ArrayList();
		ArrayList coBorrowerList = new ArrayList();
		ArrayList pledgorList = new ArrayList();
		ArrayList colAuditList = new ArrayList();

		for (int ii = 0; ii < auditList.length; ii++) {
			if ((ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(auditList[ii].getCustomerCategory()))
					|| (ICMSConstant.CHECKLIST_NON_BORROWER.equals(auditList[ii].getCustomerCategory()))) {
				mainBorrowerList.add(auditList[ii]);
				continue;
			}
			if (ICMSConstant.CHECKLIST_CO_BORROWER.equals(auditList[ii].getCustomerCategory())) {
				coBorrowerList.add(auditList[ii]);
				continue;
			}
			if (ICMSConstant.CHECKLIST_PLEDGER.equals(auditList[ii].getCustomerCategory())) {
				pledgorList.add(auditList[ii]);
				continue;
			}
			colAuditList.add(auditList[ii]);
		}
		// mainBorrowerList.addAll(coBorrowerList);
		// mainBorrowerList.addAll(pledgorList);

		aMap.put(ICMSConstant.DOC_TYPE_CC, mainBorrowerList.toArray(new ICheckListAudit[0]));
		ICheckListAudit[] collist = (ICheckListAudit[]) colAuditList.toArray(new ICheckListAudit[0]);
		getCollateralDetails(collist);
		aMap.put(ICMSConstant.DOC_TYPE_SECURITY, collist);
	}

	private void getCollateralDetails(ICheckListAudit[] anICheckListAuditList) throws CheckListException {
		ICollateralProxy colProxy = CollateralProxyFactory.getProxy();
		// long collateralID = ICMSConstant.LONG_INVALID_VALUE;
		ICollateral col = null;
		try {
			if ((anICheckListAuditList != null) && (anICheckListAuditList.length > 0)) {
				for (int ii = 0; ii < anICheckListAuditList.length; ii++) {
					col = colProxy.getCollateral(anICheckListAuditList[ii].getCollateralID(), true);
					sortLimitCharge(col);
					anICheckListAuditList[ii].setCollateral(col);
				}
			}
		}
		catch (CollateralException ex) {
			throw new CheckListException(ex);
		}
	}

	private void sortLimitCharge(ICollateral anICollateral) {
		ILimitCharge[] chargeList = anICollateral.getLimitCharges();
		if ((chargeList != null) && (chargeList.length > 0)) {
			Arrays.sort(chargeList);
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
			String[] statusList = { ICMSConstant.STATE_ITEM_COMPLETED };
			IAuditItem[] itemList = getAuditItemList(aLimitProfileID, statusList);
			return itemList;
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in getAuditItemList with limit Profile ID: " + aLimitProfileID, ex);
		}
	}

	/**
	 * Get the list of checklist items that qualify for audit under a non
	 * borrower
	 * @param aCustomerID of long String
	 * @return ICheckListAudit[] - the list of checklist that qualifies for
	 *         audit
	 * @throws CheckListException on errors
	 * @throws SearchDAOException on DAO errors
	 */
	public HashMap getCheckListAuditListForNonBorrower(long aCustomerID) throws CheckListException, SearchDAOException {
		try {
			String[] statusList = { ICMSConstant.STATE_ITEM_COMPLETED };
			ICheckListAudit[] auditList = getCheckListAuditListForNonBorrower(aCustomerID, statusList);
			HashMap map = new HashMap();
			map.put(ICMSConstant.DOC_TYPE_CC, auditList);
			return map;
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in getCheckListAuditListForNonBorrower with customer ID: "
					+ aCustomerID, ex);
		}
	}

	/**
	 * Get the list of checklist items that qualify for audit under a non
	 * borrower
	 * @param aCustomerID of long String
	 * @return IAuditItem[] - the list of checklist that qualifies for audit
	 * @throws CheckListException on errors
	 */
	public IAuditItem[] getAuditItemListForNonBorrower(long aCustomerID) throws CheckListException {
		try {
			String[] statusList = { ICMSConstant.STATE_ITEM_COMPLETED };
			IAuditItem[] itemList = getAuditItemListForNonBorrower(aCustomerID, statusList);
			return itemList;
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException(
					"Exception in getAuditItemListForNonBorrower with customer ID: " + aCustomerID, ex);
		}
	}

	/**
	 * To check if a cc doc item description already exist or not
	 * @param aDocItemDescription of String type
	 * @return boolean - true if the doc item description already exist and
	 *         false otherwise
	 * @throws CheckListException on errors
	 */
	public boolean checkCCItemDescAlreadyExist(ITemplate anITemplate, String aDocItemDescription)
			throws CheckListTemplateException, CheckListException {
		if (anITemplate != null) {
			boolean result = checkItemDescAlreadyExist(anITemplate, aDocItemDescription);
			if (result) {
				return true;
			}
		}
		try {
			ICheckListTemplateProxyManager templateProxy = CheckListTemplateProxyManagerFactory
					.getCheckListTemplateProxyManager();
			int numOfRecords = templateProxy.getNoOfDocItemByDesc(ICMSConstant.DOC_TYPE_CC, aDocItemDescription);
			if (numOfRecords == 0) {
				TemplateItemSearchCriteria criteria = new TemplateItemSearchCriteria();
				criteria.setTemplateType(ICMSConstant.DOC_TYPE_CC);
				criteria.setItemDesc(aDocItemDescription);
				ITemplateItem[] itemList = templateProxy.searchTemplateItemList(criteria);
				if (itemList != null) {
					if (itemList.length > 0) {
						return true;
					}
				}
				return false;
			}
			return true;
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception at checkCCItemDescAlreadyExist", ex);
		}
	}

	/**
	 * To check if a security doc item description already exist or not
	 * @param aDocItemDescription of String type
	 * @return boolean - true if the doc item description already exist and
	 *         false otherwise
	 * @throws CheckListException on errors
	 */
	public boolean checkCollateralItemDescAlreadyExist(ITemplate anITemplate, String aDocItemDescription)
			throws CheckListTemplateException, CheckListException {
		if ((anITemplate.getCountry() != null) && (anITemplate.getCountry().trim().length() > 0)) {
			return checkItemDescAlreadyExist(anITemplate, aDocItemDescription);
		}
		try {
			ICheckListTemplateProxyManager templateProxy = CheckListTemplateProxyManagerFactory
					.getCheckListTemplateProxyManager();
			TemplateItemSearchCriteria criteria = new TemplateItemSearchCriteria();
			criteria.setTemplateType(ICMSConstant.DOC_TYPE_SECURITY);
			criteria.setCollateralType(anITemplate.getCollateralType());
			criteria.setCollateralSubType(anITemplate.getCollateralSubType());
			criteria.setItemDesc(aDocItemDescription);
			ITemplateItem[] itemList = templateProxy.searchTemplateItemList(criteria);
			if (itemList != null) {
				if (itemList.length > 0) {
					return true;
				}
			}
			return false;
		}
		catch (SearchDAOException ex) {
			throw new CheckListException(ex);
		}
	}

	public void systemConvertNonBorrowerToBorrower(long aLimitProfileID) throws CheckListException {
		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = proxy.getLimitProfile(aLimitProfileID);
			// DefaultLogger.debug(this, "limitProfile: " + limitProfile);
			systemConvertNonBorrowerToBorrower(limitProfile);
			ICCCertificateProxyManager cccProxy = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
			cccProxy.systemCloseNonBorrowerCCC(limitProfile.getCustomerID());
		}
		catch (LimitException ex) {
			throw new CheckListException("Exception in systemConvertNonBorrowerToBorrower", ex);
		}
		catch (CCCertificateException ex) {
			throw new CheckListException("Exception in systemConvertNonBorrowerToBorrower", ex);
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
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = proxy.getLimitProfile(aLimitProfileID);
			systemConvertBorrowerToNonBorrower(limitProfile);
			ICCCertificateProxyManager cccProxy = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
			cccProxy.systemCloseMainBorrowerCCC(limitProfile.getLimitProfileID(), limitProfile.getCustomerID());
		}
		catch (CCCertificateException ex) {
			throw new CheckListException("Caught CCCertificateException in systemConvertBorrowerToNonBorrower", ex);
		}
		catch (LimitException ex) {
			throw new CheckListException("Exception in systemConvertBorrowerToNonBorrower", ex);
		}
	}

	/**
	 * To lock all the checklist items under a checklist
	 * @param aCheckListID of long type
	 * @throws CheckListException
	 * @throws RemoteException
	 */
	public void lockCheckListItemByCheckList(long aCheckListID) throws CheckListException {
		ICheckListTrxValue trxValue = getCheckList(aCheckListID);
		if ((trxValue != null) && (trxValue.getCheckList() != null)) {
			ICheckList actual = trxValue.getCheckList();
			ICheckList staging = trxValue.getStagingCheckList();
			ICheckListItem[] actualItemList = actual.getCheckListItemList();
			ICheckListItem[] stageItemList = staging.getCheckListItemList();
			for (int ii = 0; ii < actualItemList.length; ii++) {
				actualItemList[ii].setIsLockedInd(true);
				for (int jj = 0; jj < stageItemList.length; jj++) {
					if (stageItemList[jj].getCheckListItemRef() == actualItemList[ii].getCheckListItemRef()) {
						stageItemList[jj].setIsLockedInd(true);
						break;
					}
				}
			}
			actual.setCheckListItemList(actualItemList);
			staging.setCheckListItemList(stageItemList);
			trxValue.setCheckList(actual);
			trxValue.setStagingCheckList(staging);
			directUpdateCheckList(trxValue);
		}
	}

	public void systemUpdateCheckList(long aCheckListID) throws CheckListException {
		ICheckListTrxValue trxValue = getCheckList(aCheckListID);
		if ((trxValue != null) && (trxValue.getCheckList() != null)) {
			directUpdateCheckList(trxValue);
		}
	}

	/**
	 * To unlock all the checklist items under a checklist
	 * @param aCheckListID of long type
	 * @throws CheckListException
	 */
	public void unlockCheckListItemByCheckList(long aCheckListID) throws CheckListException {
		ICheckListTrxValue trxValue = getCheckList(aCheckListID);
		if ((trxValue != null) && (trxValue.getCheckList() != null)) {
			ICheckList actual = trxValue.getCheckList();
			ICheckList staging = trxValue.getStagingCheckList();
			ICheckListItem[] actualItemList = actual.getCheckListItemList();
			ICheckListItem[] stageItemList = staging.getCheckListItemList();
			for (int ii = 0; ii < actualItemList.length; ii++) {
				actualItemList[ii].setIsLockedInd(false);
			}
			for (int ii = 0; ii < stageItemList.length; ii++) {
				stageItemList[ii].setIsLockedInd(false);
			}
			actual.setCheckListItemList(actualItemList);
			staging.setCheckListItemList(stageItemList);
			trxValue.setCheckList(actual);
			trxValue.setStagingCheckList(staging);
			directUpdateCheckList(trxValue);
		}
	}

	/**
	 * To lock all the checklist items under a limitprofile
	 * @param aLimitProfileID of long type
	 * @throws CheckListException
	 */
	public void lockCheckListItemByLimitProfile(long aLimitProfileID) throws CheckListException {
		try {
			/*
			 * ICollateralCheckListOwner owner = new
			 * OBCollateralCheckListOwner(aLimitProfileID,
			 * com.integrosys.cms.app
			 * .common.constant.ICMSConstant.LONG_INVALID_VALUE); String[]
			 * statusList = { ICMSConstant.STATE_ACTIVE,
			 * ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED,
			 * ICMSConstant.STATE_DRAFT,
			 * ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ,
			 * ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
			 * ICMSConstant.STATE_PENDING_MGR_VERIFY}; CheckListSearchResult[]
			 * resultList = getCheckList(owner, statusList); if ((resultList !=
			 * null) && resultList.length > 0) { for (int ii=0;
			 * ii<resultList.length; ii++) {
			 * lockCheckListItemByCheckList(resultList[ii].getCheckListID()); }
			 * }
			 */

			// CR146: Update "SCC Issued" in Maintain CC Checklist
			HashMap checkListMap = getCCCheckListStatus(aLimitProfileID, true);
			Iterator i = checkListMap.values().iterator();
			while (i.hasNext()) {
				CheckListSearchResult sr = (CheckListSearchResult) i.next();
				if (!sr.getCheckListStatus().equals(ICMSConstant.STATE_DELETED)) {
					lockCheckListItemByCheckList(sr.getCheckListID());
				}
			}

			HashMap map = getCollateralCheckListStatus(aLimitProfileID);
			Iterator iter = map.keySet().iterator();
			while (iter.hasNext()) {
				Long collateralID = (Long) iter.next();
				CheckListSearchResult checkList = (CheckListSearchResult) map.get(collateralID);
				if (!checkList.getCheckListStatus().equals(ICMSConstant.STATE_DELETED)) {
					lockCheckListItemByCheckList(checkList.getCheckListID());
				}
			}
		}
		catch (SearchDAOException ex) {
			throw new CheckListException("Caught SearchDAOException in lockCheckListItemByLimitProfile", ex);
		}
	}

	/**
	 * To unlock all the checklist items under a limitprofile
	 * @param aLimitProfileID of long type
	 * @throws CheckListException
	 */
	public void unlockCheckListItemByLimitProfile(long aLimitProfileID) throws CheckListException {
		try {
			/*
			 * ICollateralCheckListOwner owner = new
			 * OBCollateralCheckListOwner(aLimitProfileID,
			 * com.integrosys.cms.app
			 * .common.constant.ICMSConstant.LONG_INVALID_VALUE); String[]
			 * statusList = { ICMSConstant.STATE_ACTIVE,
			 * ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED,
			 * ICMSConstant.STATE_DRAFT,
			 * ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ,
			 * ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
			 * ICMSConstant.STATE_PENDING_MGR_VERIFY}; CheckListSearchResult[]
			 * resultList = getCheckList(owner, statusList); if ((resultList !=
			 * null) && resultList.length > 0) { for (int ii=0;
			 * ii<resultList.length; ii++) {
			 * unlockCheckListItemByCheckList(resultList[ii].getCheckListID());
			 * } }
			 */
			HashMap map = getCollateralCheckListStatus(aLimitProfileID);
			Iterator iter = map.keySet().iterator();
			while (iter.hasNext()) {
				Long collateralID = (Long) iter.next();
				CheckListSearchResult checkList = (CheckListSearchResult) map.get(collateralID);
				if (!checkList.getCheckListStatus().equals(ICMSConstant.STATE_DELETED)) {
					unlockCheckListItemByCheckList(checkList.getCheckListID());
				}
			}
		}
		catch (SearchDAOException ex) {
			throw new CheckListException("Caught SearchDAOException in lockCheckListItemByLimitProfile", ex);
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
			ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(
					com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE, aCollateralID);
			String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_ACTIVE,
					ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT,
					ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ, ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
					ICMSConstant.STATE_PENDING_MGR_VERIFY,
					// New Pending status for multi-level approval
					ICMSConstant.STATE_PENDING_AUTH, ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					// New Rejected status for multi-level approval
					ICMSConstant.STATE_OFFICER_REJECTED };
			CheckListSearchResult[] resultList = getCheckList(owner, statusList);
			if ((resultList == null) || (resultList.length == 0)) {
				return true;
			}
			for (int ii = 0; ii < resultList.length; ii++) {
				if (!ICMSConstant.STATE_DELETED.equals(resultList[ii].getCheckListStatus())) {
					return false;
				}
			}
			return true;
		}
		catch (SearchDAOException ex) {
			throw new CheckListException("Caught SearchDAOException in areAllCheckListsDeleted", ex);
		}
		catch (Exception ex) {
			throw new CheckListException("Caught Exception in areAllCheckListsDeleted", ex);
		}
	}

	/**
	 * System convert non-borrower to borrower
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CheckListException on errors
	 */
	private void systemConvertNonBorrowerToBorrower(ILimitProfile anILimitProfile) throws CheckListException {
		if (anILimitProfile == null) {
			throw new CheckListException("The ILimitProfile is invalid !!!");
		}

		try {
			ICCCheckListOwner owner = new OBCCCheckListOwner(
					com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE, anILimitProfile
							.getCustomerID(), ICMSConstant.CHECKLIST_NON_BORROWER);
			String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_ACTIVE,
					ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT,
					ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ, ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
					ICMSConstant.STATE_PENDING_MGR_VERIFY,
					// New Pending status for multi-level approval
					ICMSConstant.STATE_PENDING_AUTH, ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					// New Rejected status for multi-level approval
					ICMSConstant.STATE_OFFICER_REJECTED };
			CheckListSearchResult[] resultList = getCheckList(owner, statusList);
			if ((resultList != null) && (resultList.length > 0)) {
				for (int ii = 0; ii < resultList.length; ii++) {
					if (resultList[ii].getTrxID() != null) {
						ICheckListTrxValue trxValue = getCheckListByTrxID(resultList[ii].getTrxID());
						if (ICMSConstant.STATE_PENDING_CREATE.equals(trxValue.getStatus())) {
							systemCloseCheckList(new OBTrxContext(), trxValue);
						}
						else {
							if (needToConvert(anILimitProfile)) {
								if (trxValue.getCheckList() != null) {
									ICheckList newCheckList = (ICheckList) CommonUtil
											.deepClone(trxValue.getCheckList());
									ICCCheckListOwner ccOwner = (ICCCheckListOwner) newCheckList.getCheckListOwner();
									ccOwner.setLimitProfileID(anILimitProfile.getLimitProfileID());
									ccOwner.setSubOwnerID(anILimitProfile.getCustomerID());
									ccOwner.setSubOwnerType(ICMSConstant.CHECKLIST_MAIN_BORROWER);
									trxValue.setStagingCheckList(newCheckList);
									copyCheckList(new OBTrxContext(), trxValue);
									return;
								}
							}
							else {
								// close the non borrower checklist
								// systemCloseCheckList(new OBTrxContext(),
								// trxValue);
								systemDeleteCheckList(null, trxValue);
							}
						}
					}
				}
			}
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("SearchDAOException in systemConvertNonBorrowerToBorrower", ex);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			rollback();
			throw new CheckListException("Exception on systemConvertNonBorrowerToBorrower: " + ex.toString());
		}
	}

	/**
	 * System convert borrower to non-borrower
	 * @param anILimitProfile of ILimitProfile type
	 * @throws CheckListException on errors
	 */
	private void systemConvertBorrowerToNonBorrower(ILimitProfile anILimitProfile) throws CheckListException {
		if (anILimitProfile == null) {
			throw new CheckListException("The ILimitProfile is invalid !!!");
		}

		try {
			// ICCCheckListOwner owner = new
			// OBCCCheckListOwner(com.integrosys.cms
			// .app.common.constant.ICMSConstant.LONG_INVALID_VALUE,
			// anILimitProfile.getCustomerID(),
			// ICMSConstant.CHECKLIST_MAIN_BORROWER);
			ICCCheckListOwner owner = new OBCCCheckListOwner(anILimitProfile.getLimitProfileID(), anILimitProfile
					.getCustomerID(), ICMSConstant.CHECKLIST_MAIN_BORROWER);
			String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_ACTIVE,
					ICMSConstant.STATE_PENDING_UPDATE, ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_DRAFT,
					ICMSConstant.STATE_PENDING_GEN_WAIVER_REQ, ICMSConstant.STATE_PENDING_GEN_DEFERRAL_REQ,
					ICMSConstant.STATE_PENDING_MGR_VERIFY,
					// New Pending status for multi-level approval
					ICMSConstant.STATE_PENDING_AUTH, ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					// New Rejected status for multi-level approval
					ICMSConstant.STATE_OFFICER_REJECTED };
			CheckListSearchResult[] resultList = getCheckList(owner, statusList);
			if ((resultList != null) && (resultList.length > 0)) {
				for (int ii = 0; ii < resultList.length; ii++) {
					if (resultList[ii].getTrxID() != null) {
						ICheckListTrxValue trxValue = getCheckListByTrxID(resultList[ii].getTrxID());
						if (trxValue.getCheckList() == null) {
							systemCloseCheckList(new OBTrxContext(), trxValue);
						}
						else {
							if (needToConvertToNonBorrower(anILimitProfile)) {
								if (trxValue.getCheckList() != null) {
									ICheckList newCheckList = (ICheckList) CommonUtil
											.deepClone(trxValue.getCheckList());
									ICCCheckListOwner ccOwner = (ICCCheckListOwner) newCheckList.getCheckListOwner();
									// CMSSP-798, not to set Limit Profile Id to
									// Min Value, for case like
									// a Customer having 2 BCA in a same
									// Location, if both checklist converted
									// system will only get one checklist for
									// different BCA
									// ccOwner.setLimitProfileID(ICMSConstant.
									// LONG_MIN_VALUE);
									ccOwner.setSubOwnerID(anILimitProfile.getCustomerID());
									ccOwner.setSubOwnerType(ICMSConstant.CHECKLIST_NON_BORROWER);
									trxValue.setStagingCheckList(newCheckList);
									copyCheckList(new OBTrxContext(), trxValue);
									return;
								}
							}
							else {
								// delete the main borrower checklist
								systemDeleteCheckList(null, trxValue);
							}
						}
					}
				}
			}
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("SearchDAOException in systemConvertNonBorrowerToBorrower", ex);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			rollback();
			throw new CheckListException("Exception on systemConvertNonBorrowerToBorrower: " + ex.toString());
		}
	}

	private boolean needToConvert(ILimitProfile anILimitProfile) throws CheckListException {
		try {
			// long customerID = anILimitProfile.getCustomerID();
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer customer = custProxy.getCustomer(anILimitProfile.getCustomerID());

			String orgCode = customer.getOriginatingLocation().getOrganisationCode();
			CCTaskSearchCriteria criteria = new CCTaskSearchCriteria();
			criteria.setCustomerCategory(ICMSConstant.CHECKLIST_NON_BORROWER);
			criteria.setCustomerID(anILimitProfile.getCustomerID());
			criteria.setDomicileCountry(customer.getOriginatingLocation().getCountryCode());
			String[] trxStatusList = { ICMSConstant.STATE_ACTIVE };
			criteria.setTrxStatusList(trxStatusList);
			ICollaborationTaskProxyManager proxy = CollaborationTaskProxyManagerFactory.getProxyManager();
			CCTaskSearchResult[] result = proxy.getCCTask(criteria);
			if ((result != null) && (result.length == 1)) {
				orgCode = result[0].getOrgCode();
			}
			IBookingLocation bkgLocation = anILimitProfile.getOriginatingLocation();
			if ((customer.getOriginatingLocation().getCountryCode().equals(bkgLocation.getCountryCode()))
					&& (bkgLocation.getOrganisationCode().equals(orgCode))) {
				return true;
			}
			return false;
		}
		catch (CustomerException ex) {
			throw new CheckListException(ex);
		}
		catch (CollaborationTaskException ex) {
			throw new CheckListException(ex);
		}
	}

	private boolean needToConvertToNonBorrower(ILimitProfile anILimitProfile) throws CheckListException {
		try {
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer customer = custProxy.getCustomer(anILimitProfile.getCustomerID());
			IBookingLocation bkgLocation = anILimitProfile.getOriginatingLocation();
			DefaultLogger.debug(this, "###### 1 <needToConvertToNonBorrower> anILimitProfile.getLimitProfileID() "
					+ anILimitProfile.getLimitProfileID());
			DefaultLogger.debug(this, "###### 2 <needToConvertToNonBorrower> Cust: "
					+ customer.getOriginatingLocation());
			DefaultLogger.debug(this, "###### 3 <needToConvertToNonBorrower> Booking Location: " + bkgLocation);
			if ((customer.getOriginatingLocation().getCountryCode().equals(bkgLocation.getCountryCode()))
					&& (bkgLocation.getOrganisationCode().equals(customer.getOriginatingLocation()
							.getOrganisationCode()))) {
				return true;
			}
			return false;
		}
		catch (CustomerException ex) {
			throw new CheckListException(ex);
		}
	}

	private boolean checkItemDescAlreadyExist(ITemplate anITemplate, String aDocItemDescription) {
		ITemplateItem[] itemList = anITemplate.getTemplateItemList();
		if ((itemList == null) || (itemList.length == 0)) {
			return false;
		}

		for (int ii = 0; ii < itemList.length; ii++) {
			if (itemList[ii].equals(aDocItemDescription)) {
				return true;
			}
		}
		return false;
	}

	private boolean isCustodianState(String aState) {
		if ((aState.equals(ICMSConstant.STATE_ITEM_PENDING_TEMP_UPLIFT_AUTHZ))
				|| (aState.equals(ICMSConstant.STATE_ITEM_PENDING_PERM_UPLIFT_AUTHZ))
				|| (aState.equals(ICMSConstant.STATE_ITEM_PENDING_RELODGE_AUTHZ))) // bernard
		// -
		// added
		// to
		// support
		// allow
		// relodge
		{
			return true;
		}
		return false;
	}

	/**
	 * Formulate the checklist Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICheckList - ICheckList
	 * @param anOperationType - int
	 * @return ICheckListTrxValue - the checklist trx interface formulated
	 */
	private ICheckListTrxValue formulateTrxValue(ITrxContext anITrxContext, ICheckList anICheckList, int anOperationType) {
		return formulateTrxValue(anITrxContext, null, anICheckList, anOperationType);
	}

	/**
	 * Formulate the checklist Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @param anICheckList - ICheckList
	 * @param anOperationType - int
	 * @return ICheckListTrxValue - the checklist trx interface formulated
	 */
	private ICheckListTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			ICheckList anICheckList, int anOperationType) {
		ICheckListTrxValue checkListTrxValue = null;
		if (anICMSTrxValue != null) {
			checkListTrxValue = new OBCheckListTrxValue(anICMSTrxValue);
		}
		else {
			checkListTrxValue = new OBCheckListTrxValue();
		}
		checkListTrxValue.setStagingCheckList(anICheckList);
		checkListTrxValue = formulateTrxValue(anITrxContext, checkListTrxValue, anOperationType);
		return checkListTrxValue;
	}

	/**
	 * Formulate the checklist trx object
	 * @param anITrxContext - ITrxContext
	 * @param anICheckListTrxValue - ICheckListTrxValue
	 * @param anOperationType - int
	 * @return ICheckListTrxValue - the checklist trx interface formulated
	 */
	private ICheckListTrxValue formulateTrxValue(ITrxContext anITrxContext, ICheckListTrxValue anICheckListTrxValue,
			int anOperationType) {
		if (anITrxContext != null) {
			anICheckListTrxValue.setTrxContext(anITrxContext);
		}
		anICheckListTrxValue.setTransactionType(ICMSConstant.INSTANCE_CHECKLIST);

		String subType = getTransactionSubType(anICheckListTrxValue.getStagingCheckList(), anOperationType);
//		subType="COL_CHECKLIST";
		if (subType != null) {
			anICheckListTrxValue.setTransactionSubType(subType);
		}
		return anICheckListTrxValue;
	}

	private String getTransactionSubType(ICheckList anICheckList, int anOperationType) {
		if (anICheckList == null) {
			return null;
		}

		ICheckListOwner owner = anICheckList.getCheckListOwner();
		if (owner == null) {
			return null;
		}
		if(anICheckList.getCheckListType().equalsIgnoreCase("CAM")){
			if (ICMSConstant.STATE_DELETED.equals(anICheckList.getCheckListStatus())) {
				if (ICMSConstant.TRUE_VALUE.equals(((OBCheckList) anICheckList).getObsolete())) {
					return ICMSConstant.TRX_TYPE_OBSOLETE_CAM_CHECKLIST;
				}
				if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
					return ICMSConstant.TRX_TYPE_DELETE_CAM_CHECKLIST;
				}
				return ICMSConstant.TRX_TYPE_DELETE_CAM_CHECKLIST_RECEIPT;
			}
			if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
				return ICMSConstant.TRX_TYPE_CAM_CHECKLIST;
			}
			return ICMSConstant.TRX_TYPE_CAM_CHECKLIST_RECEIPT;
			
		}else if(anICheckList.getCheckListType().equalsIgnoreCase("O")){
			if (ICMSConstant.STATE_DELETED.equals(anICheckList.getCheckListStatus())) {
				if (ICMSConstant.TRUE_VALUE.equals(((OBCheckList) anICheckList).getObsolete())) {
					return ICMSConstant.TRX_TYPE_OBSOLETE_OTHER_CHECKLIST;
				}
				if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
					return ICMSConstant.TRX_TYPE_DELETE_OTHER_CHECKLIST;
				}
				return ICMSConstant.TRX_TYPE_DELETE_OTHER_CHECKLIST_RECEIPT;
			}
			if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
				return ICMSConstant.TRX_TYPE_OTHER_CHECKLIST;
			}
			return ICMSConstant.TRX_TYPE_OTHER_CHECKLIST_RECEIPT;
			
		}else if(anICheckList.getCheckListType().equalsIgnoreCase("LAD")){
			if (ICMSConstant.STATE_DELETED.equals(anICheckList.getCheckListStatus())) {
				if (ICMSConstant.TRUE_VALUE.equals(((OBCheckList) anICheckList).getObsolete())) {
					return ICMSConstant.TRX_TYPE_OBSOLETE_LAD_CHECKLIST;
				}
				if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
					return ICMSConstant.TRX_TYPE_DELETE_LAD_CHECKLIST;
				}
				return ICMSConstant.TRX_TYPE_DELETE_LAD_CHECKLIST_RECEIPT;
			}
			if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
				return ICMSConstant.TRX_TYPE_LAD_CHECKLIST;
			}
			return ICMSConstant.TRX_TYPE_LAD_CHECKLIST_RECEIPT;
			
		}else if(anICheckList.getCheckListType().equalsIgnoreCase("REC")){
			if (ICMSConstant.STATE_DELETED.equals(anICheckList.getCheckListStatus())) {
				if (ICMSConstant.TRUE_VALUE.equals(((OBCheckList) anICheckList).getObsolete())) {
					return ICMSConstant.TRX_TYPE_OBSOLETE_RECURRENTDOC_CHECKLIST;
				}
				if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
					return ICMSConstant.TRX_TYPE_DELETE_RECURRENTDOC_CHECKLIST;
				}
				return ICMSConstant.TRX_TYPE_DELETE_RECURRENTDOC_CHECKLIST_RECEIPT;
			}
			if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
				return ICMSConstant.TRX_TYPE_RECURRENTDOC_CHECKLIST;
			}
			return ICMSConstant.TRX_TYPE_RECURRENTDOC_CHECKLIST_RECEIPT;
			
		}
		else if(anICheckList.getCheckListType().equalsIgnoreCase("PARIPASSU")){
			if (ICMSConstant.STATE_DELETED.equals(anICheckList.getCheckListStatus())) {
				if (ICMSConstant.TRUE_VALUE.equals(((OBCheckList) anICheckList).getObsolete())) {
					return ICMSConstant.TRX_TYPE_OBSOLETE_PARIPASSU_CHECKLIST;
				}
				if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
					return ICMSConstant.TRX_TYPE_DELETE_PARIPASSU_CHECKLIST;
				}
				return ICMSConstant.TRX_TYPE_DELETE_PARIPASSU_CHECKLIST_RECEIPT;
			}
			if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
				return ICMSConstant.TRX_TYPE_PARIPASSU_CHECKLIST;
			}
			return ICMSConstant.TRX_TYPE_PARIPASSU_CHECKLIST_RECEIPT;
			
		}else if(anICheckList.getCheckListType().equalsIgnoreCase("F")){
			if (ICMSConstant.STATE_DELETED.equals(anICheckList.getCheckListStatus())) {
				if (ICMSConstant.TRUE_VALUE.equals(((OBCheckList) anICheckList).getObsolete())) {
					return ICMSConstant.TRX_TYPE_OBSOLETE_FAC_CHECKLIST;
				}
				if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
					return ICMSConstant.TRX_TYPE_DELETE_FAC_CHECKLIST;
				}
				return ICMSConstant.TRX_TYPE_DELETE_FAC_CHECKLIST_RECEIPT;
			}
			if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
				return ICMSConstant.TRX_TYPE_FAC_CHECKLIST;
			}
			return ICMSConstant.TRX_TYPE_FAC_CHECKLIST_RECEIPT;
			
		}
		else{
		
		if (owner instanceof ICCCheckListOwner) {
			if (ICMSConstant.STATE_DELETED.equals(anICheckList.getCheckListStatus())) {
				if (ICMSConstant.TRUE_VALUE.equals(((OBCheckList) anICheckList).getObsolete())) {
					return ICMSConstant.TRX_TYPE_OBSOLETE_CC_CHECKLIST;
				}
				if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
					return ICMSConstant.TRX_TYPE_DELETE_CC_CHECKLIST;
				}
				return ICMSConstant.TRX_TYPE_DELETE_CC_CHECKLIST_RECEIPT;
			}
			if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
				return ICMSConstant.TRX_TYPE_CC_CHECKLIST;
			}
			return ICMSConstant.TRX_TYPE_CC_CHECKLIST_RECEIPT;
		}

		if (owner instanceof ICollateralCheckListOwner) {
			if (ICMSConstant.STATE_DELETED.equals(anICheckList.getCheckListStatus())) {
				if (ICMSConstant.TRUE_VALUE.equals(((OBCheckList) anICheckList).getObsolete())) {
					return ICMSConstant.TRX_TYPE_OBSOLETE_COL_CHECKLIST;
				}
				if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
					return ICMSConstant.TRX_TYPE_DELETE_COL_CHECKLIST;
				}
				return ICMSConstant.TRX_TYPE_DELETE_COL_CHECKLIST_RECEIPT;
			}
			if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
				return ICMSConstant.TRX_TYPE_COL_CHECKLIST;
			}
			return ICMSConstant.TRX_TYPE_COL_CHECKLIST_RECEIPT;
		}
		}
		return null;
	}

	/**
	 * Helper method to perform the checklist transactions.
	 * @param anICheckListTrxValue - ICheckListTrxValue
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return ICheckListTrxValue - the trx interface
	 */
	private ICheckListTrxValue operate(ICheckListTrxValue anICheckListTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CheckListException {
		ICMSTrxResult trxResult = operateForResult(anICheckListTrxValue, anOBCMSTrxParameter);
		ICheckListTrxValue result = (ICheckListTrxValue) trxResult.getTrxValue();

		/*
		 * It is setting to itself, so comment this part if
		 * (result.getCheckList() != null) { ICheckList checkList =
		 * result.getCheckList(); ICheckListItem[] items =
		 * checkList.getCheckListItemList();
		 * checkList.setCheckListItemList(items);
		 * result.setCheckList(checkList); }
		 */

		return result;
	}
	
	/*private ICheckListTrxValue operateScheduler(ICheckListTrxValue anICheckListTrxValue, OBCMSTrxParameter anOBCMSTrxParameter,String flagScheduler)
			throws CheckListException {
		ICMSTrxResult trxResult = operateForResultScheduler(anICheckListTrxValue, anOBCMSTrxParameter,flagScheduler);
		ICheckListTrxValue result = (ICheckListTrxValue) trxResult.getTrxValue();

		
		 * It is setting to itself, so comment this part if
		 * (result.getCheckList() != null) { ICheckList checkList =
		 * result.getCheckList(); ICheckListItem[] items =
		 * checkList.getCheckListItemList();
		 * checkList.setCheckListItemList(items);
		 * result.setCheckList(checkList); }
		 

		return result;
	}*/

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
			HashMap map = new HashMap();
			ICheckListAudit[] auditList = getCheckListAudit(aLimitProfileID, asOfDate);
			sortAuditListCr310(map, auditList, aCustCat);
			return map;
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in getCheckListAudit with limit Profile ID: " + aLimitProfileID, ex);
		}
	}

	private void sortAuditListCr310(HashMap aMap, ICheckListAudit[] auditList, String aCustCat)
			throws CheckListException {
		if ((auditList == null) || (auditList.length == 0)) {
			return;
		}
		ArrayList mainBorrowerList = new ArrayList();
		ArrayList coBorrowerList = new ArrayList();
		ArrayList pledgorList = new ArrayList();
		ArrayList colAuditList = new ArrayList();

		for (int ii = 0; ii < auditList.length; ii++) {
			if ((ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(auditList[ii].getCustomerCategory()))
					|| (ICMSConstant.CHECKLIST_NON_BORROWER.equals(auditList[ii].getCustomerCategory()))) {
				mainBorrowerList.add(auditList[ii]);
				continue;
			}
			if (ICMSConstant.CHECKLIST_CO_BORROWER.equals(auditList[ii].getCustomerCategory())) {
				coBorrowerList.add(auditList[ii]);
				continue;
			}
			if (ICMSConstant.CHECKLIST_PLEDGER.equals(auditList[ii].getCustomerCategory())) {
				pledgorList.add(auditList[ii]);
				continue;
			}
			colAuditList.add(auditList[ii]);
		}
		// mainBorrowerList.addAll(coBorrowerList);
		// mainBorrowerList.addAll(pledgorList);

		if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(aCustCat)) {
			aMap.put(ICMSConstant.CHECKLIST_MAIN_BORROWER, mainBorrowerList.toArray(new ICheckListAudit[0]));
			ICheckListAudit[] collist = (ICheckListAudit[]) colAuditList.toArray(new ICheckListAudit[0]);
			getCollateralDetails(collist);
			aMap.put(ICMSConstant.DOC_TYPE_SECURITY, collist);
		}
		if (ICMSConstant.CHECKLIST_CO_BORROWER.equals(aCustCat)) {
			aMap.put(ICMSConstant.CHECKLIST_CO_BORROWER, coBorrowerList.toArray(new ICheckListAudit[0]));
		}
		if (ICMSConstant.CHECKLIST_PLEDGER.equals(aCustCat)) {
			aMap.put(ICMSConstant.CHECKLIST_PLEDGER, pledgorList.toArray(new ICheckListAudit[0]));
		}

	}

	protected abstract ICheckListAudit[] getCheckListAudit(long aLimitProfileID, String asOfDate)
			throws SearchDAOException, CheckListException;

	/**
	 * FOR CR CMS-310 Get the list of checklist items that qualify for audit
	 * under a non borrower
	 * @param aCustomerID of long String
	 * @return ICheckListAudit[] - the list of checklist that qualifies for
	 *         audit
	 * @throws CheckListException on errors
	 * @throws SearchDAOException on DAO errors
	 */
	public HashMap getCheckListAuditForNonBorrower(long aCustomerID, String asOfDate, String aCustCat)
			throws CheckListException, SearchDAOException {
		try {
			ICheckListAudit[] auditList = getCheckListAuditForNonBorrower(aCustomerID, asOfDate);
			HashMap map = new HashMap();
			map.put(ICMSConstant.DOC_TYPE_CC, auditList);
			return map;
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CheckListException("Exception in getCheckListAuditForNonBorrower with customer ID: "
					+ aCustomerID, ex);
		}
	}

	protected abstract ICheckListAudit[] getCheckListAuditForNonBorrower(long aCustomerID, String asOfDate)
			throws SearchDAOException, CheckListException;

	/** FOR CR CMS-534 */
	private ICoBorrowerLimit[] getAllCoBorrowerLimitList(ILimit[] anILimitList, HashMap checkListMap) {
		ArrayList result = new ArrayList();
		ArrayList resultID = new ArrayList();
		if ((anILimitList != null) && (anILimitList.length > 0)) {
			for (int ii = 0; ii < anILimitList.length; ii++) {
				ICoBorrowerLimit[] coBorrowerLimitList = anILimitList[ii].getCoBorrowerLimits();
				if ((coBorrowerLimitList != null) && (coBorrowerLimitList.length > 0)) {
					for (int jj = 0; jj < coBorrowerLimitList.length; jj++) {
						// CMSSP-559: Can see checklist in update cc but can't
						// see in col task
						boolean addCoboLimit = true;
						if (ICMSConstant.HOST_STATUS_DELETE.equals(coBorrowerLimitList[jj].getHostStatus())
								&& (null != coBorrowerLimitList[jj].getHostStatus())) {
							String key = ICMSConstant.CHECKLIST_CO_BORROWER + coBorrowerLimitList[jj].getCustomerID();
							CheckListSearchResult checkList = null;

							if (checkListMap != null) {
								checkList = (CheckListSearchResult) checkListMap.get(key);
							}

							// only allow deleted checklist
							if ((checkList == null)
									|| !ICMSConstant.STATE_CHECKLIST_DELETED.equals(checkList.getCheckListStatus())) {
								// DefaultLogger.debug(this,
								// "Checklist does not exist!");
								addCoboLimit = false;
							}
						}

						if (addCoboLimit && !resultID.contains(new Long(coBorrowerLimitList[jj].getLimitID()))) {
							resultID.add(new Long(coBorrowerLimitList[jj].getLimitID()));
							result.add(coBorrowerLimitList[jj]);
						}
					}
				}
			}

		}
		ICoBorrowerLimit[] coBorrowerList = (ICoBorrowerLimit[]) result.toArray(new ICoBorrowerLimit[0]);
		if ((coBorrowerList != null) && (coBorrowerList.length > 1)) {
			Arrays.sort(coBorrowerList);
		}
		return coBorrowerList;
	}

	/** FOR CR CMS-534 */
	private ICollateralPledgor[] getAllPledgorList(ILimit[] anILimitList, HashMap checkListMap)
			throws CheckListException {
		ArrayList result = new ArrayList();
		ArrayList resultID = new ArrayList();
		// ArrayList collateralID = new ArrayList();
		HashMap colPldgrMap = new HashMap();
		ICollateralAllocation[] colAllocationList = null;
		try {
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			for (int ii = 0; ii < anILimitList.length; ii++) {
				// colAllocationList =
				// anILimitList[ii].getCollateralAllocations();
				ArrayList tempColAllocList = new ArrayList(); // =
				// anILimitList[ii
				// ].
				// getCollateralAllocations
				// ();
				if ((anILimitList[ii].getCollateralAllocations() != null)
						&& (anILimitList[ii].getCollateralAllocations().length > 0)) {
					tempColAllocList.addAll(Arrays.asList(anILimitList[ii].getCollateralAllocations()));
				}

				// R1.5 CR35
				ICoBorrowerLimit[] cbLimits = anILimitList[ii].getCoBorrowerLimits();
				if (cbLimits != null) {
					for (int k = 0; k < cbLimits.length; k++) {
						if ((cbLimits[k].getCollateralAllocations() != null)
								&& (cbLimits[k].getCollateralAllocations().length > 0)) {
							tempColAllocList.addAll(Arrays.asList(cbLimits[k].getCollateralAllocations()));
						}
					}
				}

				colAllocationList = (ICollateralAllocation[]) tempColAllocList.toArray(new ICollateralAllocation[0]);

				if (colAllocationList != null) {
					int colLen = colAllocationList.length;
					if (colLen > 1) {
						Arrays.sort(colAllocationList);
					}
					for (int jj = 0; jj < colLen; jj++) {
						Long colID = new Long(colAllocationList[jj].getCollateral().getCollateralID());
						// if (!collateralID.contains(colID))
						// {

						// CMSSP-559: Can see checklist in update cc but can't
						// see in col task
						if (!colPldgrMap.containsKey(colID)) {
							colPldgrMap.put(colID, proxy.getCollateralPledgors(colID.longValue()));
						}

						ICollateralPledgor[] pledgorList = (ICollateralPledgor[]) colPldgrMap.get(colID);

						if (pledgorList != null) {
							int pledgorLen = pledgorList.length;
							if (pledgorLen > 1) {
								Arrays.sort(pledgorList);
							}
							for (int kk = 0; kk < pledgorLen; kk++) {
								// bernard - implemented additional checks for
								// CMS-1637
								// 1. Deleted limit-security linkage
								// 2. Deleted security-pledgor linkage
								// 3. Presence of existing checklist
								boolean addPledgor = true;
								if (ICMSConstant.HOST_STATUS_DELETE.equals(colAllocationList[jj].getHostStatus())
										|| ICMSConstant.HOST_STATUS_DELETE.equals(pledgorList[kk]
												.getSCIPledgorMapStatus())) {
									// DefaultLogger.debug(this,
									// "Deleted limit-security-pledgor linkage chargeID/pledgorID="
									// +colAllocationList[jj].getChargeID()+"/"+
									// pledgorList[kk].getPledgorID());
									String key = ICMSConstant.CHECKLIST_PLEDGER + pledgorList[kk].getPledgorID();
									// DefaultLogger.debug(this, "KEY: " + key);
									CheckListSearchResult checkList = null;
									if (checkListMap != null) {
										checkList = (CheckListSearchResult) checkListMap.get(key);
									}
									// only allow for deleted checklist
									if ((checkList == null)
											|| !ICMSConstant.STATE_CHECKLIST_DELETED.equals(checkList
													.getCheckListStatus())) {
										DefaultLogger.debug(this, "Checklist does not exist!");
										addPledgor = false;
									}
								}

								if (addPledgor && !resultID.contains(new Long(pledgorList[kk].getPledgorID()))) {
									DefaultLogger.debug(this, "Pledgor ID: " + pledgorList[kk].getPledgorID());
									resultID.add(new Long(pledgorList[kk].getPledgorID()));
									result.add(pledgorList[kk]);
								}
							}
						}
						// }
					}
				}
			}
			ICollateralPledgor[] collateralPledgorList = (ICollateralPledgor[]) result
					.toArray(new ICollateralPledgor[0]);
			/*
			 * if (collateralPledgorList != null && collateralPledgorList.length
			 * > 1) { String[] param = {"PledgorID"};
			 * SortUtil.sortObject(collateralPledgorList, param); }
			 */
			return collateralPledgorList;
		}
		catch (CollateralException ex) {
			ex.printStackTrace();
			rollback();
			throw new CheckListException("Exception in getCoBorrowerPledgorCheckList", ex);
		}
	}

	/** FOR CR CMS-534 */
	private void getCoBorrowerPledgorCheckListColTask(IContext anIContext, ILimitProfile anILimitProfile,
			HashMap aCheckListMap, ArrayList aList, boolean aFilteredInd) throws CheckListTemplateException,
			CheckListException {
		try {
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer customer = null;
			ArrayList list = new ArrayList();
			ArrayList pledgorIDList = new ArrayList();
			if (aList.size() > 0) {
				CCCheckListSummary mainSummary = (CCCheckListSummary) aList.get(0);
				DefaultLogger.debug(this, "LegalID: " + mainSummary.getLegalID());
				list.add(new Long(mainSummary.getLegalID()));
			}
			else {
				customer = custProxy.getCustomer(anILimitProfile.getCustomerID());
				list.add(new Long(customer.getCMSLegalEntity().getLEReference()));
			}
			// ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
			ILimit[] limitList = anILimitProfile.getLimits();
			if ((limitList == null) || (limitList.length == 0)) {
				return;
			}
			CCCheckListSummary summary = new CCCheckListSummary();
			ICoBorrowerLimit[] coBorrowerLimitList = getAllCoBorrowerLimitList(limitList, aCheckListMap);
			ICollateralPledgor[] pledgorList = getAllPledgorList(limitList, aCheckListMap);

			for (int ii = 0; ii < coBorrowerLimitList.length; ii++) {
				long customerID = coBorrowerLimitList[ii].getCustomerID();
				// String key = ICMSConstant.CHECKLIST_CO_BORROWER + customerID;
				if (aCheckListMap.get(new Long(customerID)) == null) {
					customer = custProxy.getCustomer(customerID);
					if (!list.contains(new Long(customer.getCMSLegalEntity().getLEReference()))) {
						DefaultLogger.debug(this, "co-borrower: " + customer.getCMSLegalEntity().getLEReference());
						list.add(new Long(customer.getCMSLegalEntity().getLEReference()));
						summary = formulateCCCheckListSummary(anIContext, anILimitProfile, aCheckListMap, customer,
								ICMSConstant.CHECKLIST_CO_BORROWER);
						if (summary != null) {
							if ((!aFilteredInd) || isInSameCountry(anIContext, anILimitProfile, summary)) {
								aList.add(summary);
							}
						}
					}
				}
			}

			for (int kk = 0; kk < pledgorList.length; kk++) {
				if ((pledgorList[kk].getLegalID() != null) && (pledgorList[kk].getLegalID().trim().length() > 0)) {
					if (!list.contains(pledgorList[kk].getLegalID())) {
						DefaultLogger.debug(this, "Pledgor LEID: " + pledgorList[kk].getLegalID());
						list.add(pledgorList[kk].getLegalID());
						pledgorIDList.add(new Long(pledgorList[kk].getPledgorID()));
						summary = formulateCCCheckListSummary(anIContext, anILimitProfile, aCheckListMap,
								pledgorList[kk]);
						if (summary != null) {
							if ((!aFilteredInd) || isInSameCountry(anIContext, anILimitProfile, summary)) {
								aList.add(summary);
							}
						}
					}
				}
				else {
					DefaultLogger.debug(this, "Pledgor does not have legal ID !!!");
					if (!pledgorIDList.contains(new Long(pledgorList[kk].getPledgorID()))) {
						DefaultLogger.debug(this, "PledgorID: " + pledgorList[kk].getPledgorID());
						pledgorIDList.add(new Long(pledgorList[kk].getPledgorID()));
						summary = formulateCCCheckListSummary(anIContext, anILimitProfile, aCheckListMap,
								pledgorList[kk]);
						if (summary != null) {
							if ((!aFilteredInd) || isInSameCountry(anIContext, anILimitProfile, summary)) {
								aList.add(summary);
							}
						}
					}
				}
			}

			DefaultLogger.debug(this, "SIZE: " + aList.size());
		}
		catch (CustomerException ex) {
			rollback();
			throw new CheckListException("Exception in getCoBorrowerPledgorCheckList", ex);
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
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_FORWARD);
		trxVal = formulateTrxValue(ctx, trxVal, ICMSConstant.CHECKLIST_RECEIPT);

		ICheckListTrxValue newTrxValue = null;
		try {
			OfficeCheckListTrxController controller = new OfficeCheckListTrxController();
			// Instance on which this controller to do
			controller.setInstanceName(trxVal.getTransactionType());
			ITrxResult trxResult = controller.operate(trxVal, param);

			newTrxValue = (ICheckListTrxValue) trxResult.getTrxValue();
			if (newTrxValue.getCheckList() != null) {
				ICheckList checkList = newTrxValue.getCheckList();
				ICheckListItem[] items = checkList.getCheckListItemList();
				checkList.setCheckListItemList(items);
				newTrxValue.setCheckList(checkList);
			}

		}
		catch (TransactionException e) {
			rollback();
			throw new CheckListException(e);
		}
		catch (Exception ex) {
			rollback();
			throw new CheckListException(ex.toString());
		}
		return newTrxValue;
	}

	/**
	 * To system delete pledgor checklist if it is no longer valid due to this
	 * co-borrower
	 * @aCoBorrowerLegalRef of long type
	 * @aLimitProfileRef of long type
	 * @throws CheckListException on errors
	 */
	public void systemHandleCoBorrowerCheckList(long aCoBorrowerLegalRef, long aLimitProfileRef)
			throws CheckListException {
		try {
			Long[] checkListIDList = getAffectedPledgorCheckList(aCoBorrowerLegalRef, aLimitProfileRef);
			if ((checkListIDList == null) || (checkListIDList.length == 0)) {
				return;
			}
			ICheckListTrxValue trxValue = null;
			for (int ii = 0; ii < checkListIDList.length; ii++) {
				trxValue = getCheckList(checkListIDList[ii].longValue());
				systemDeleteCheckList(null, trxValue);
			}
		}
		catch (SearchDAOException ex) {
			throw new CheckListException("Caught Exception in systemHandleCoBorrowerCheckList", ex);
		}
	}

	/**
	 * To system delete pledgor checklists if it is no longer valid due to the
	 * legal reference of the pledgor
	 * @param aPledgorLegalRef of long type
	 * @throws CheckListException, RemoteException on errors
	 */
	public void systemHandlePledgorCheckList(long aPledgorID, long aPledgorLegalRef) throws CheckListException {
		try {
			Long[] limitProfileIDList = getAffectedLimitProfileID(aPledgorLegalRef);
			if ((limitProfileIDList == null) || (limitProfileIDList.length == 0)) {
				return;
			}
			for (int ii = 0; ii < limitProfileIDList.length; ii++) {
				systemDeletePledgorCheckListTrx(limitProfileIDList[ii].longValue(), aPledgorID);
			}
		}
		catch (SearchDAOException ex) {
			throw new CheckListException("Caught Exception in SearchDAOException", ex);
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
			ArrayList list = new ArrayList();
			HashMap checkListMap = getCCCheckListStatus(limitProfile.getLimitProfileID(), true);
			Iterator i = checkListMap.values().iterator();
			while (i.hasNext()) {
				CheckListSearchResult sr = (CheckListSearchResult) i.next();
				ICheckList checkList = getCheckListByID(sr.getCheckListID());
				list.add(checkList);
			}

			checkListMap = getCollateralCheckListStatus(limitProfile.getLimitProfileID());
			i = checkListMap.values().iterator();
			while (i.hasNext()) {
				CheckListSearchResult sr = (CheckListSearchResult) i.next();
				ICheckList checkList = getCheckListByID(sr.getCheckListID());
				list.add(checkList);
			}
			
			CheckListSearchResult checkListCAM = getCAMCheckListByCategoryAndProfileID("CAM",limitProfile.getLimitProfileID());
			if(checkListCAM!=null){
			ICheckList checkListCam=getCheckListByID(checkListCAM.getCheckListID());	
			list.add(checkListCam);
			}
			ILimit[] OB=limitProfile.getLimits();
          	for(int j=0;j<OB.length;j++){
              ILimit obLimit= OB[j];
              long limitId= obLimit.getLimitID();
           
              try {
            	  CheckListSearchResult checkListFAC=getCheckListByCollateralID(limitId);
            	  if(checkListFAC!=null){
            	  ICheckList checkListFac=getCheckListByID(checkListFAC.getCheckListID());	
      			list.add(checkListFac);
            	  }
			} catch (CheckListException e) {
				
				e.printStackTrace();
				throw new CommandProcessingException("failed to retrieve  checklist ", e);
			}
          	}
          	CheckListSearchResult checkListOther = getCAMCheckListByCategoryAndProfileID("O",limitProfile.getLimitProfileID());
			if(checkListOther!=null){
			ICheckList checkListOtherType=getCheckListByID(checkListOther.getCheckListID());	
			list.add(checkListOtherType);
			}
			/*checkListMap = getFacilityCheckListStatus(limitProfile.getLimitProfileID(), true);
			i = checkListMap.values().iterator();
			while (i.hasNext()) {
				CheckListSearchResult sr = (CheckListSearchResult) i.next();
				ICheckList checkList = getCheckListByID(sr.getCheckListID());
				list.add(checkList);
			}*/
			return (ICheckList[]) list.toArray(new ICheckList[0]);
		}
		catch (SearchDAOException e) {
			throw new CheckListException("SearchDAOException caught in getAllCheckList");
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
			ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
			return dao.getCheckListItemSeqNo();
		}
		catch (Exception e) {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	private void setSummaryTrxValue(ICheckListSummary summary, long aLimitProfileID, long aCustomerID,
			long aCollateralID, String aCustomerType) throws CheckListException {
		try {
			String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_REJECTED };

			if (summary instanceof CCCheckListSummary) {
				ICCCheckListOwner owner = new OBCCCheckListOwner(aLimitProfileID, aCustomerID, aCustomerType);
				CheckListSearchResult[] resultList = getCheckList(owner, statusList);
				if ((summary != null) && (resultList != null) && (resultList.length > 0) && (resultList[0] != null)) {
					summary.setTrxStatus(resultList[0].getTrxStatus());
					summary.setTrxFromState(resultList[0].getTrxFromState());
				}
			}
			else {
				ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(aLimitProfileID, aCollateralID);
				CheckListSearchResult[] resultList = getCheckList(owner, statusList);
				if ((summary != null) && (resultList != null) && (resultList.length > 0) && (resultList[0] != null)) {
					summary.setTrxStatus(resultList[0].getTrxStatus());
					summary.setTrxFromState(resultList[0].getTrxFromState());
				}
			}
		}
		catch (Exception ex) {
			throw new CheckListException("Caught Exception in setSummaryTrxValue", ex);
		}
	}

	protected IBookingLocation getDocumentLocation(ILimitProfile anILimitProfile, String anOwnerType, long anOwnerID,
			IBookingLocation aDefaultBookingLocation) throws CheckListException {
		if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(anOwnerType)
				|| ICMSConstant.CHECKLIST_JOINT_BORROWER.equals(anOwnerType)
				|| ICMSConstant.CHECKLIST_PLEDGER.equals(anOwnerType)) {
			return anILimitProfile.getOriginatingLocation();
		}
		IBookingLocation location = null;
		if (anILimitProfile != null) {
			location = getDocumentLocation(anILimitProfile.getLimitProfileID(), anOwnerType, anOwnerID);
		}
		else {
			location = getDocumentLocation(ICMSConstant.LONG_MIN_VALUE, anOwnerType, anOwnerID);
		}
		DefaultLogger.debug(this, "Location: " + location);
		if (location != null) {
			return location;
		}
		return aDefaultBookingLocation;
	}

	private IBookingLocation getDocumentLocation(long aLimitProfileID, String anOwnerType, long anOwnerID)
			throws CheckListException {
		try {
			IDocumentLocationProxyManager mgr = DocumentLocationProxyManagerFactory.getProxyManager();
			ICCDocumentLocation[] ccLocation = mgr.getCCDocumentLocation(anOwnerType, aLimitProfileID, anOwnerID);
			if ((ccLocation != null) && (ccLocation.length > 0)) {
				return ccLocation[0].getOriginatingLocation();
			}
			return null;
		}
		catch (DocumentLocationException ex) {
			throw new CheckListException("Caught DocumentLocationException in getDocumentLocation", ex);
		}
	}

	/**
	 * Helper method to perform the document item transactions.
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return ICMSTrxResult - the trx result interface
	 */
	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CheckListException {
		try {
			ITrxController controller = (new CheckListTrxControllerFactory()).getController(anICMSTrxValue,
					anOBCMSTrxParameter);
			if (controller == null) {
				throw new CheckListException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			rollback();
			throw new CheckListException(e);
		}
		catch (Exception ex) {
			rollback();
			throw new CheckListException(ex.toString());
		}
	}
	
	/*protected ICMSTrxResult operateForResultScheduler(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter,String flagScheduler)
			throws CheckListException {
		try {
			ITrxController controller = (new CheckListTrxControllerFactory()).getController(anICMSTrxValue,
					anOBCMSTrxParameter);
			if (controller == null) {
				throw new CheckListException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			rollback();
			throw new CheckListException(e);
		}
		catch (Exception ex) {
			rollback();
			throw new CheckListException(ex.toString());
		}
	}*/
	
	protected boolean requireCustodianApproval(String anItemStatus) {
		if ((anItemStatus.equals(ICMSConstant.ACTION_ITEM_ALLOW_PENDING_LODGE)))
		{
			return true;                //can be summarize to like 'PENDING_%' to change state?
		}
		return false;
	}

	public abstract HashMap getCollateralCheckListStatus(long aLimitProfileID) throws SearchDAOException,
			CheckListException;

	public abstract HashMap getDocumentCategories(long aLimitProfileID) throws SearchDAOException, CheckListException;

	public abstract HashMap getDocumentCategoriesForNonBorrower(long aCustomerID, long aLimitProfileID)
			throws SearchDAOException, CheckListException;

	public abstract HashMap getDocumentsHeld(DocumentHeldSearchCriteria criteria) throws SearchDAOException,
			CheckListException;

	public abstract HashMap getSecuritiesPledged(long aLimitProfileID, long pledgorID) throws SearchDAOException,
			CheckListException;

	public abstract int getSecurityChkListCount(long aCollateralID) throws SearchDAOException, CheckListException;

	public abstract HashMap getCCCheckListStatus(long aLimitProfileID, boolean isFullListInd)
			throws SearchDAOException, CheckListException;
	
	public abstract HashMap getFacilityCheckListStatus(long aLimitProfileID, boolean isFullListInd)
	throws SearchDAOException, CheckListException;

	protected abstract CCCheckListSummary[] getDeletedCCCheckListStatus(long aLimitProfileID, long aCustomerID)
			throws SearchDAOException, CheckListException;

	protected abstract CCCheckListSummary[] getDeletedCCCheckListStatusForNonBorrower(long aCustomerID)
			throws SearchDAOException, CheckListException;

	protected abstract HashMap getCCCheckListStatusForNonBorrower(long aLimitProfileID, long aCustomerID)
			throws SearchDAOException, CheckListException;

	protected abstract CheckListSearchResult[] getCheckList(ICheckListOwner anICheckListOwner, String[] aStatusList)
			throws SearchDAOException, CheckListException;

	protected abstract ICheckListAudit[] getCheckListAuditList(long aLimitProfileID, String[] aStatusList)
			throws SearchDAOException, CheckListException;

	protected abstract IAuditItem[] getAuditItemList(long aLimitProfileID, String[] aStatusList)
			throws SearchDAOException, CheckListException;

	protected abstract ICheckListAudit[] getCheckListAuditListForNonBorrower(long aCustomerID, String[] aStatusList)
			throws SearchDAOException, CheckListException;

	protected abstract IAuditItem[] getAuditItemListForNonBorrower(long aCustomerID, String[] aStatusList)
			throws SearchDAOException, CheckListException;

	protected abstract ICCTask getCCTask(long aLimitProfileID, String aCustCategory, long aCustomerID)
			throws CheckListException;

	protected abstract ICollateralTask getCollateralTask(long aLimitProfileID, long aCollateralID,
			String aCollateralLocation) throws CheckListException;

	protected abstract int getLimitProfileCollateralCount(long aLimitProfileID, long aCollateralID)
			throws SearchDAOException, CheckListException;

	protected abstract int getLimitProfilePledgorCount(long aLimitProfileID, long aPledgorID)
			throws SearchDAOException, CheckListException;

	protected abstract int getLimitProfilePledgorCount(long aLimitProfileID, long aPledgorID,
			long[] aDeletedLimitSecMapList) throws SearchDAOException, CheckListException;

	protected abstract int getLimitProfileCoBorrowerCount(long aLimitProfileID, long aCustomerID)
			throws SearchDAOException, CheckListException;

	protected abstract void rollback() throws CheckListException;

	protected abstract Long[] getAffectedPledgorCheckList(long aCoBorrowerLegalRef, long aLimitProfileRef)
			throws SearchDAOException, CheckListException;

	protected abstract Long[] getAffectedLimitProfileID(long aPledgorLegalRef) throws SearchDAOException,
			CheckListException;

	// Start for cr-17
	// public abstract List getCheckListDetailsByCheckListId(String[]
	// aCheckListId, String categoryType, String subCategoryType) throws
	// SearchDAOException, CheckListException;
	public abstract List getCheckListDetailsByCheckListId(String[] aCheckListId, String categoryType)
			throws SearchDAOException, CheckListException;

	public abstract List getCheckListDetailsByCheckListId(String[] aCheckListId) throws SearchDAOException,
			CheckListException;

	public abstract List getAllShareDocuments(long id, boolean isNonBorrower) throws CheckListException,
			SearchDAOException;
	
	public abstract List getAllDeferCreditApprover() throws CheckListException, SearchDAOException;
	
	public abstract List getAllWaiveCreditApprover() throws CheckListException, SearchDAOException;
	
	public abstract List getAllBothCreditApprover() throws CheckListException, SearchDAOException;

	public abstract HashMap[] getDetailsForPreDisbursementReminderLetter(long limitProfileID)
			throws SearchDAOException, CheckListException;

	public abstract HashMap[] getDetailsForPostDisbursementReminderLetter(long limitProfileID)
			throws SearchDAOException, CheckListException;

	// public abstract OBShareDoc getLeName(long aProfileId) throws
	// CheckListException,SearchDAOException;
	// public abstract OBShareDoc getSecuritySubDetails(long aProfileId, long
	// collateralId) throws CheckListException,SearchDAOException;
	// End for cr-17

}
