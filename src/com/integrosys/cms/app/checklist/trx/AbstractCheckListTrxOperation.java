/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.app.checklist.trx;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.exception.OFACheckedException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManager;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManagerHome;
import com.integrosys.cms.app.chktemplate.bus.SBCheckListTemplateBusManager;
import com.integrosys.cms.app.chktemplate.bus.SBCheckListTemplateBusManagerHome;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IPledgor;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.SBCustomerManager;
import com.integrosys.cms.app.customer.bus.SBCustomerManagerHome;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.collaborationtask.CCCollaborationTaskListener;
import com.integrosys.cms.app.eventmonitor.collaborationtask.OBCCCollaborationTaskInfo;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.ui.checklist.CheckListUtil;

/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author $Author: hmbao $
 * @version $Revision: 1.22 $
 * @since $Date: 2006/11/16 09:16:18 $ Tag: $Name: $
 */
public abstract class AbstractCheckListTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	private static final long serialVersionUID = -4621932986613751747L;

	protected String checklistOldStatus = null;

	public static final String EVENT_CHECKLIST_COMPLETION = "EV_COL_CHECKLIST_COMPLETION";

	public static final String EVENT_CC_CHECKLIST_COMPLETION = "EV_CC_CHECKLIST_COMPLETION";

	public static final String EVENT_CHECKLIST_STATUS_CHANGE = "EV_COL_CHECKLIST_STATUS_CHANGE";

	public static final String EVENT_CC_CHECKLIST_STATUS_CHANGE = "EV_CC_CHECKLIST_STATUS_CHANGE";

	public static final String EVENT_CC_CHECKLIST_DELETION_READY = "EV_CC_CHECKLIST_DELETION_READY";

	public static final String EVENT_COL_CHECKLIST_DELETION_READY = "EV_COL_CHECKLIST_DELETION_READY";

	public static final String EVENT_CC_ITEM_WAIVE_DEFER_DELETE = "EV_CC_ITEM_WAIVE_DEFER_DELETE";

	public static final String EVENT_COL_ITEM_WAIVE_DEFER_DELETE = "EV_COL_ITEM_WAIVE_DEFER_DELETE";

	// notification rules
	public static final String RULE_CHECKLIST_COMPLETION = "R_CHECKLIST_COMPLETION";

	public static final String RULE_CHECKLIST_STATUS_CHANGE = "R_CHECKLIST_STATUS_CHANGE";

	public static final String RULE_CHECKLIST_DELETION = "R_CHECKLIST_DELETION";

	public static final String RULE_CHECKLIST_ITEM_WAIVE_DEFER_DELETE = "R_CHECKLIST_ITEM_WAIVE_DEFER_DELETE";

	/**
	 * Sets the next route requirements into the ITrxValue.
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue containing the required routing information for next
	 *         user
	 * @throws TransactionException on error
	 */
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	/**
	 * This method set the primary key from the original to the copied checklist
	 * objects. It is required for the case of updating staging from actual and
	 * vice versa as there is a need to perform a deep clone of the object and
	 * set the required attribute in the object to the original one so that a
	 * proper update can be done.
	 * @param anOriginal - ICheckList
	 * @param aCopy - ICheckList
	 * @return ICheckList - the copied object with required attributes from the
	 *         original checklist
	 * @throws TrxOperationException on errors
	 */
	protected ICheckList mergeCheckList(ICheckList anOriginal, ICheckList aCopy) throws TrxOperationException {
		aCopy.setCheckListID(anOriginal.getCheckListID());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	/**
	 * Create the staging document item doc
	 * @param anICheckListTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws TrxOperationException if errors
	 */
	protected ICheckListTrxValue createStagingCheckList(ICheckListTrxValue anICheckListTrxValue)
			throws TrxOperationException {
		try { 
			
//			if("Y".equals(anICheckListTrxValue.getFlagSchedulers())) {
//				anICheckListTrxValue.getStagingCheckList().setFlagSchedulersCheck("Y");
//			}
			ICheckList checkList = getSBStagingCheckListBusManager().create(anICheckListTrxValue.getStagingCheckList());
			anICheckListTrxValue.setStagingCheckList(checkList);
			anICheckListTrxValue.setStagingReferenceID(String.valueOf(checkList.getCheckListID()));
			return anICheckListTrxValue;
		}
		catch (CheckListException ex) {
			throw new TrxOperationException("failed to create staging checklist ["
					+ anICheckListTrxValue.getStagingCheckList() + "]", ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("failed to create staging checklist ["
					+ anICheckListTrxValue.getStagingCheckList() + "]", ex.getCause());
		}
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anICheckListTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	protected ICheckListTrxValue updateActualCheckList(ICheckListTrxValue anICheckListTrxValue)
			throws TrxOperationException {
		try {
			ICheckList staging = anICheckListTrxValue.getStagingCheckList();
			ICheckList actual = anICheckListTrxValue.getCheckList();
			if (actual != null) {
				ICheckList updActual = (ICheckList) CommonUtil.deepClone(staging);
				updActual = mergeCheckList(actual, updActual);
				
				if("Y".equals(anICheckListTrxValue.getFlagSchedulers())) {
					updActual.setFlagSchedulersCheck(anICheckListTrxValue.getFlagSchedulers());
				}
				ICheckList actualCheckList = getSBCheckListBusManager().update(updActual);
				anICheckListTrxValue.setCheckList(actualCheckList);
			}
			return anICheckListTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(
					"failed to update actual checklist using staging checklist due to concurrent update", ex);
		}
		catch (CheckListException ex) {
			throw new TrxOperationException("failed to update actual checklist using staging checklist", ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("failed to update actual checklist using staging checklist", ex.getCause());
		}
		catch (IOException ex) {
			throw new TrxOperationException("failed to clone staging checklist", ex);
		}
		catch (ClassNotFoundException ex) {
			throw new TrxOperationException("failed to clone staging checklist", ex);
		}
	}

	protected ICheckListTrxValue updateActualCheckListWithoutMerge(ICheckListTrxValue anICheckListTrxValue)
			throws TrxOperationException {
		try {
			ICheckList actual = anICheckListTrxValue.getCheckList();
			ICheckList actualCheckList = getSBCheckListBusManager().update(actual);
			anICheckListTrxValue.setCheckList(actualCheckList);
			return anICheckListTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException("failed to update actual checklist due to concurrent update", ex);
		}
		catch (CheckListException ex) {
			throw new TrxOperationException("failed to update actual checklist", ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("failed to update actual checklist", ex.getCause());
		}
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anICheckList - ICheckList
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	protected ICheckList updateActualCheckList(ICheckList anICheckList) throws TrxOperationException {
		try {
			ICheckList actualCheckList = getSBCheckListBusManager().update(anICheckList);
			return actualCheckList;
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException("failed to update actual checklist due to concurrent update", ex);
		}
		catch (CheckListException ex) {
			throw new TrxOperationException("failed to update actual checklist", ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("failed to update actual checklist", ex.getCause());
		}
	}

	/**
	 * Update a checklist transaction
	 * @param anICheckListTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected ICheckListTrxValue updateCheckListTransaction(ICheckListTrxValue anICheckListTrxValue)
			throws TrxOperationException {
		anICheckListTrxValue = prepareTrxValue(anICheckListTrxValue);
		ICMSTrxValue tempValue = super.updateTransaction(anICheckListTrxValue);
		OBCheckListTrxValue newValue = new OBCheckListTrxValue(tempValue);
		newValue.setCheckList(anICheckListTrxValue.getCheckList());
		newValue.setStagingCheckList(anICheckListTrxValue.getStagingCheckList());
		return newValue;
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue - ITrxValue
	 * @return ICheckListTrxValue - the document item specific trx value object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected ICheckListTrxValue getCheckListTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		return (ICheckListTrxValue) anITrxValue;
	}

	/**
	 * To update the customer status using the customer ID
	 * @param aCustomerID of long type
	 * @param aCCCStatus of String type
	 * @throw TrxOperationException on error
	 */
	protected void updateCustomerStatus(long aCustomerID, String aCCCStatus) throws TrxOperationException {
		try {
			ICMSCustomer customer = getCustomerManager().getCustomer(aCustomerID);
			customer.setCCCStatus(aCCCStatus);
			getCustomerManager().updateCustomer(customer);
		}
		catch (CustomerException ex) {
			throw new TrxOperationException("failed to retrieve or update customer, for customer id [" + aCustomerID
					+ "]", ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("failed to retrieve or update customer, for customer id [" + aCustomerID
					+ "]", ex.getCause());
		}
	}

	private SBCustomerManager getCustomerManager() {
		SBCustomerManager remote = (SBCustomerManager) BeanController.getEJB(ICMSJNDIConstant.SB_CUSTOMER_MGR_JNDI,
				SBCustomerManagerHome.class.getName());
		return remote;
	}

	/**
	 * To get the remote handler for the staging checklist session bean
	 * @return SBCheckListBusManager - the remote handler for the staging
	 *         checklist session bean
	 */
	protected SBCheckListBusManager getSBStagingCheckListBusManager() {
		SBCheckListBusManager remote = (SBCheckListBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_CHECKLIST_BUS_JNDI, SBCheckListBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * To get the remote handler for the checklist session bean
	 * @return SBCheckListBusManager - the remote handler for the checklist
	 *         session bean
	 */
	protected SBCheckListBusManager getSBCheckListBusManager() {
		SBCheckListBusManager remote = (SBCheckListBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CHECKLIST_BUS_JNDI, SBCheckListBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * To get the remote handler for the checklist session bean
	 * @return SBCheckListBusManager - the remote handler for the checklist
	 *         session bean
	 */
	protected SBCheckListTemplateBusManager getSBCheckListTemplateBusManager() {
		SBCheckListTemplateBusManager remote = (SBCheckListTemplateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CHECKLIST_TEMPLATE_BUS_JNDI, SBCheckListTemplateBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * Prepares a trx object
	 */
	protected ICheckListTrxValue prepareTrxValue(ICheckListTrxValue anICheckListTrxValue) {
		if (anICheckListTrxValue != null) {
			ICheckList actual = anICheckListTrxValue.getCheckList();
			ICheckList staging = anICheckListTrxValue.getStagingCheckList();
			if (actual != null) {
				anICheckListTrxValue.setReferenceID(String.valueOf(actual.getCheckListID()));
			}
			else {
				anICheckListTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				anICheckListTrxValue.setStagingReferenceID(String.valueOf(staging.getCheckListID()));
			}
			else {
				anICheckListTrxValue.setStagingReferenceID(null);
			}
			return anICheckListTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type ICheckListTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ICheckListTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * Method moved to CollaborationTaskNotificationInterceptor instead
	 * 
	 * @param anICheckListTrxValue
	 * @param isObselete
	 * @throws TrxOperationException
	 * 
	 * @see com.integrosys.cms.app.eventmonitor.collaborationtask.CollaborationTaskNotificationInterceptor
	 */
	protected void sendCollateralNotification(ICheckListTrxValue anICheckListTrxValue, boolean isObselete)
			throws TrxOperationException {
		/*
		 * try { ILimitProxy proxy = LimitProxyFactory.getProxy();
		 * ICollateralProxy colProxy = CollateralProxyFactory.getProxy();
		 * ICollateralCheckListOwner owner = (ICollateralCheckListOwner)
		 * anICheckListTrxValue.getCheckList() .getCheckListOwner(); ICheckList
		 * checkList = anICheckListTrxValue.getCheckList(); String[] countryList
		 * = new String[1]; ILimitProfile limitProfile =
		 * anICheckListTrxValue.getTrxContext().getLimitProfile();
		 * 
		 * boolean isInCountry =
		 * CheckListUtil.isInCountry(anICheckListTrxValue.getTrxContext
		 * ().getTeam(), limitProfile); if (isInCountry) { return; }
		 * 
		 * DefaultLogger.debug(this, "<<<<<<<<<<<<<<<< is limitprofile null: " +
		 * (limitProfile == null ? "NULL" : "not null")); if (limitProfile ==
		 * null) { limitProfile =
		 * proxy.getLimitProfile(owner.getLimitProfileID()); }
		 * 
		 * ICollateral collateral =
		 * colProxy.getCollateral(owner.getCollateralID(), true); countryList[0]
		 * = limitProfile.getOriginatingLocation().getCountryCode(); if
		 * (!countryList[0].equals(collateral.getCollateralLocation())) {
		 * OBCollaborationTaskInfo info = new OBCollaborationTaskInfo();
		 * info.setSecurityID(String.valueOf(collateral.getSCISecurityID()));
		 * info
		 * .setSecuritySubTypeID(collateral.getCollateralSubType().getSubTypeCode
		 * ());
		 * info.setSecurityMaturityDate(getSecurityMaturityDate(collateral));
		 * info.setSecurityLocation(collateral.getCollateralLocation());
		 * info.setOriginatingCountry(countryList[0]); if (isObselete) {
		 * info.setSecurityRemarks(""); } else {
		 * info.setSecurityRemarks(anICheckListTrxValue.getRemarks()); }
		 * info.setBcaBkgCountry
		 * (limitProfile.getOriginatingLocation().getCountryCode());
		 * info.setBeforeChangeStatus(checklistOldStatus);
		 * info.setAfterChangeStatus(checkList.getCheckListStatus()); String
		 * eventName = null; String ruleName = null; if
		 * (anICheckListTrxValue.getSendItemNotificationInd()) {
		 * info.setItemList(setNotificationItemList(checkList)); ruleName =
		 * RULE_CHECKLIST_ITEM_WAIVE_DEFER_DELETE; eventName =
		 * EVENT_COL_ITEM_WAIVE_DEFER_DELETE; } else if
		 * (anICheckListTrxValue.getSendNotificationInd()) { if (isObselete) {
		 * ruleName = RULE_CHECKLIST_DELETION; eventName =
		 * EVENT_COL_CHECKLIST_DELETION_READY; } else if
		 * (isCheckListCompletedNotification(checklistOldStatus,
		 * checkList.getCheckListStatus())) { ruleName =
		 * RULE_CHECKLIST_COMPLETION; eventName = EVENT_CHECKLIST_COMPLETION; }
		 * else { ruleName = RULE_CHECKLIST_STATUS_CHANGE; eventName =
		 * EVENT_CHECKLIST_STATUS_CHANGE; } } CollaborationTaskListener listener
		 * = new CollaborationTaskListener(); if (eventName != null) { List
		 * customerList =
		 * getSBCheckListBusManager().getSecurityOwnerList(owner.getCollateralID
		 * (), limitProfile.getLimitProfileID()); ISecurityCustomer[]
		 * customerArray = null; if ((customerList != null) &&
		 * (customerList.size() > 0)) { customerArray = (ISecurityCustomer[])
		 * customerList.toArray(new ISecurityCustomer[0]); } if ((customerArray
		 * == null) || (customerArray.length == 0)) { throw new
		 * CollateralException("Cannot find customer with : - CollID: " +
		 * owner.getCollateralID() + " LPID: " +
		 * limitProfile.getLimitProfileID()); } for (int index = 0; index <
		 * customerArray.length; index++) {
		 * info.setLeID(String.valueOf(customerArray[index].getLeID()));
		 * info.setLeName(customerArray[index].getLeName());
		 * info.setSegment(customerArray[index].getSegment()); ArrayList
		 * paramList = new ArrayList(); paramList.add(info); paramList.add("");
		 * paramList.add(constructRuleParam(ruleName));
		 * listener.fireEvent(eventName, paramList); } } } } catch
		 * (CollateralException ex) { throw new TrxOperationException(ex); }
		 * catch (Exception ex) { ex.printStackTrace(); throw new
		 * TrxOperationException
		 * ("General Exception in sendCollateralNotification" + ex.toString());
		 * }
		 */
	}

	protected Date[] getSecurityMaturityDate(ICollateral collateral) {
		ArrayList dateList = new ArrayList();
		if (collateral instanceof ICashCollateral) {
			ICashDeposit[] deposit = ((ICashCollateral) collateral).getDepositInfo();
			if (deposit != null) {
				for (int i = 0; i < deposit.length; i++) {
					if (deposit[i].getDepositMaturityDate() != null) {
						dateList.add(deposit[i].getDepositMaturityDate());
					}
				}
			}
		}
		else if (collateral instanceof IMarketableCollateral) {
			IMarketableEquity[] equityList = ((IMarketableCollateral) collateral).getEquityList();
			if (equityList != null) {
				for (int i = 0; i < equityList.length; i++) {
					if (equityList[i].getCollateralMaturityDate() != null) {
						dateList.add(equityList[i].getCollateralMaturityDate());
					}
				}
			}
		}
		else {
			if (collateral.getCollateralMaturityDate() != null) {
				dateList.add(collateral.getCollateralMaturityDate());
			}
		}

		if (dateList.size() > 0) {
			return (Date[]) dateList.toArray(new Date[0]);
		}

		return null;
	}

	/**
	 * Method moved to CollaborationTaskNotificationInterceptor
	 * 
	 * @param anICheckListTrxValue
	 * @param isObselete
	 * @throws TrxOperationException
	 * 
	 * @see com.integrosys.cms.app.eventmonitor.collaborationtask.CollaborationTaskNotificationInterceptor
	 */
	protected void sendCCNotification(ICheckListTrxValue anICheckListTrxValue, boolean isObselete)
			throws TrxOperationException {
		try {
			ICCCheckListOwner owner = (ICCCheckListOwner) anICheckListTrxValue.getCheckList().getCheckListOwner();
			ILimitProfile limitProfile = null;
			String[] countryList = new String[1];
			String orgCode = null;
			String segment = null;
			ICheckList checkList = anICheckListTrxValue.getCheckList();
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer customer = null;
			boolean isInCountry = false;
			if (owner.getLimitProfileID() != ICMSConstant.LONG_INVALID_VALUE) {
				limitProfile = anICheckListTrxValue.getTrxContext().getLimitProfile();
				DefaultLogger.debug(this, "<<<<<<<<<<<<<<<< is limitprofile null: "
						+ (limitProfile == null ? "NULL" : "not null"));
				if (limitProfile == null) {
					ILimitProxy lmtProxy = LimitProxyFactory.getProxy();
					limitProfile = lmtProxy.getLimitProfile(owner.getLimitProfileID());
				}
				countryList[0] = limitProfile.getOriginatingLocation().getCountryCode();
				orgCode = limitProfile.getOriginatingLocation().getOrganisationCode();
				customer = custProxy.getCustomer(limitProfile.getCustomerID());
				segment = customer.getCMSLegalEntity().getCustomerSegment();
				isInCountry = CheckListUtil.isInCountry(anICheckListTrxValue.getTrxContext().getTeam(), limitProfile);
			}
			else {
				customer = custProxy.getCustomer(owner.getSubOwnerID());
				segment = customer.getCMSLegalEntity().getCustomerSegment();
				countryList[0] = customer.getOriginatingLocation().getCountryCode();
				isInCountry = CheckListUtil.isInCountry(anICheckListTrxValue.getTrxContext().getTeam(), customer);
			}
			if (isInCountry) {
				return;
			}
			String checklistCountry = checkList.getCheckListLocation().getCountryCode();
			if (!countryList[0].equals(checklistCountry)) {
				OBCCCollaborationTaskInfo info = new OBCCCollaborationTaskInfo();
				info.setLeID(customer.getCMSLegalEntity().getLEReference());
				info.setLeName(customer.getCMSLegalEntity().getLegalName());
				info.setCustomerCategory(owner.getSubOwnerType());
				info.setCustomerID(String.valueOf(owner.getSubOwnerID()));
				if (isObselete) {
					info.setCCRemarks("");
				}
				else {
					info.setCCRemarks(anICheckListTrxValue.getRemarks());
				}
				info.setOrgCode(anICheckListTrxValue.getCheckList().getCheckListLocation().getOrganisationCode());
				info.setDomicileCountry(checklistCountry);

				if (ICMSConstant.CHECKLIST_CO_BORROWER.equals(owner.getSubOwnerType())) {
					customer = custProxy.getCustomer(owner.getSubOwnerID());
					if ((countryList[0].equals(checklistCountry))
							&& ((info.getOrgCode() == null) || (orgCode.equals(info.getOrgCode())))) {
						return;
					}
					info.setOriginatingOrganisation(orgCode);
					info.setGovernLaw(getSBCheckListTemplateBusManager().getLaw(checklistCountry));
					info.setBcaBkgCountry(limitProfile.getOriginatingLocation().getCountryCode());
					info.setCoborrowerPledgorLeName(customer.getCMSLegalEntity().getLegalName());
					info.setCoborrowerPledgorID(customer.getCMSLegalEntity().getLEReference());
				}
				else {
					if (ICMSConstant.CHECKLIST_PLEDGER.equals(owner.getSubOwnerType())) {
						ICollateralProxy colProxy = CollateralProxyFactory.getProxy();
						IPledgor pledgor = colProxy.getPledgor(owner.getSubOwnerID());
						if ((countryList[0].equals(checklistCountry))
								&& ((info.getOrgCode() == null) || (orgCode.equals(info.getOrgCode())))) {
							return;
						}
						info.setOriginatingOrganisation(orgCode);
						info.setBcaBkgCountry(limitProfile.getOriginatingLocation().getCountryCode());
						info.setCoborrowerPledgorLeName(pledgor.getPledgorName());
						info.setCoborrowerPledgorID(String.valueOf(pledgor.getSysGenPledgorID()));
					}
					else {
						// non borrower
						customer = custProxy.getCustomer(owner.getSubOwnerID());
						info.setGovernLaw(getSBCheckListTemplateBusManager().getLaw(checklistCountry));
						info.setOriginatingOrganisation(info.getOrgCode());
					}
				}

				info.setGovernLaw(getSBCheckListTemplateBusManager().getLaw(info.getDomicileCountry()));
				info.setOriginatingCountry(countryList[0]);
				info.setSegment(segment);
				CCCollaborationTaskListener listener = new CCCollaborationTaskListener();
				if (anICheckListTrxValue.getSendItemNotificationInd()) {
					info.setItemList(setNotificationItemList(checkList));
					ArrayList list = new ArrayList();
					list.add(info);
					list.add("");
					list.add(constructRuleParam(RULE_CHECKLIST_ITEM_WAIVE_DEFER_DELETE));
					listener.fireEvent(EVENT_CC_ITEM_WAIVE_DEFER_DELETE, list);
				}
				else if (anICheckListTrxValue.getSendNotificationInd()) {
					if (isObselete) {
						ArrayList list = new ArrayList();
						list.add(info);
						list.add("");
						list.add(constructRuleParam(RULE_CHECKLIST_DELETION));
						listener.fireEvent(EVENT_CC_CHECKLIST_DELETION_READY, list);
					}
					else if (isCheckListCompletedNotification(checklistOldStatus, checkList.getCheckListStatus())) {
						ArrayList list = new ArrayList();
						list.add(info);
						list.add("");
						list.add(constructRuleParam(RULE_CHECKLIST_COMPLETION));
						listener.fireEvent(EVENT_CC_CHECKLIST_COMPLETION, list);
					}
					else {
						info.setBeforeChangeStatus(checklistOldStatus);
						info.setAfterChangeStatus(checkList.getCheckListStatus());
						ArrayList list = new ArrayList();
						list.add(info);
						list.add("");
						list.add(constructRuleParam(RULE_CHECKLIST_STATUS_CHANGE));
						listener.fireEvent(EVENT_CC_CHECKLIST_STATUS_CHANGE, list);
					}
				}
			}
		}
		catch (CustomerException ex) {
			throw new TrxOperationException("failed to retrieve customer", ex);
		}
		catch (OFACheckedException ex) {
			throw new TrxOperationException("there is error when accessing the app module", ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("failed to work on EJB", ex.getCause());
		}
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	public IRuleParam constructRuleParam(String ruleID) {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID(ruleID);
		param.setSysDate(DateUtil.getDate());
		param.setRuleNum(-1);
		return param;
	}

	/**
	 * This method is to set sending notification indicator
	 */
	protected void setSendNotificationIndicator(String anOldStatus, ICheckListTrxValue anICheckListTrxValue) {
		ICheckList checkList = anICheckListTrxValue.getCheckList();
		String newStatus = checkList.getCheckListStatus();
		if (!newStatus.equals(anOldStatus)) {
			anICheckListTrxValue.setSendNotificationInd(true);
		}
	}

	/**
	 * This method is set sending checklist item notification indicator
	 */
	protected void setSendCheckListItemNotificationInd(ICheckListTrxValue anICheckListTrxValue) {
		ICheckList stageCheckList = anICheckListTrxValue.getStagingCheckList();
		ICheckListItem[] itemList = stageCheckList.getCheckListItemList();
		if (itemList != null) {
			for (int i = 0; i < itemList.length; i++) {
				if (ICMSConstant.STATE_ITEM_PENDING_WAIVER.equals(itemList[i].getItemStatus())
						|| ICMSConstant.STATE_ITEM_PENDING_DEFERRAL.equals(itemList[i].getItemStatus())
						|| ICMSConstant.STATE_ITEM_PENDING_DELETE.equals(itemList[i].getItemStatus())) {
					anICheckListTrxValue.setSendItemNotificationInd(true);
					return;
				}
			}
		}
	}

	/**
	 * This method is to set checklist item list notification
	 */
	private ICheckListItem[] setNotificationItemList(ICheckList checkList) {
		ICheckListItem[] itemList = checkList.getCheckListItemList();
		ArrayList list = new ArrayList();

		if (itemList != null) {
			for (int i = 0; i < itemList.length; i++) {
				if (ICMSConstant.STATE_ITEM_WAIVED.equals(itemList[i].getItemStatus())
						|| ICMSConstant.STATE_ITEM_DEFERRED.equals(itemList[i].getItemStatus())
						|| ICMSConstant.STATE_ITEM_DELETED.equals(itemList[i].getItemStatus())) {
					list.add(itemList[i]);
				}
			}
		}
		return ((ICheckListItem[]) list.toArray(new ICheckListItem[0]));
	}

	/**
	 * This method is to check whether the notification is send for checklist
	 * completion
	 */
	private boolean isCheckListCompletedNotification(String anOldStatus, String newStatus) {
		if ((!ICMSConstant.STATE_CHECKLIST_COMPLETED.equals(anOldStatus))
				&& (ICMSConstant.STATE_CHECKLIST_COMPLETED.equals(newStatus))) {
			return true;
		}
		return false;
	}
}