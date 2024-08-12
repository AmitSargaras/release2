package com.integrosys.cms.app.eventmonitor.collaborationtask;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ISecurityCustomer;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManager;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManagerHome;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.SBCheckListTemplateBusManager;
import com.integrosys.cms.app.chktemplate.bus.SBCheckListTemplateBusManagerHome;
import com.integrosys.cms.app.collateral.bus.CollateralException;
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
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.checklist.CheckListUtil;

/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author $Author: hmbao $
 * @version $Revision: 1.22 $
 * @since $Date: 2006/11/16 09:16:18 $ Tag: $Name: $
 */
public abstract class AbstractCollaborationTaskNotificationInterceptor implements MethodInterceptor {
	protected String checklistOldStatus = null;

	// notification events
	private static final String EVENT_CHECKLIST_COMPLETION = "EV_COL_CHECKLIST_COMPLETION";

	private static final String EVENT_CC_CHECKLIST_COMPLETION = "EV_CC_CHECKLIST_COMPLETION";

	private static final String EVENT_CHECKLIST_STATUS_CHANGE = "EV_COL_CHECKLIST_STATUS_CHANGE";

	private static final String EVENT_CC_CHECKLIST_STATUS_CHANGE = "EV_CC_CHECKLIST_STATUS_CHANGE";

	private static final String EVENT_CC_CHECKLIST_DELETION_READY = "EV_CC_CHECKLIST_DELETION_READY";

	private static final String EVENT_COL_CHECKLIST_DELETION_READY = "EV_COL_CHECKLIST_DELETION_READY";

	private static final String EVENT_CC_ITEM_WAIVE_DEFER_DELETE = "EV_CC_ITEM_WAIVE_DEFER_DELETE";

	private static final String EVENT_COL_ITEM_WAIVE_DEFER_DELETE = "EV_COL_ITEM_WAIVE_DEFER_DELETE";

	// notification rules
	private static final String RULE_CHECKLIST_COMPLETION = "R_CHECKLIST_COMPLETION";

	private static final String RULE_CHECKLIST_STATUS_CHANGE = "R_CHECKLIST_STATUS_CHANGE";

	private static final String RULE_CHECKLIST_DELETION = "R_CHECKLIST_DELETION";

	private static final String RULE_CHECKLIST_ITEM_WAIVE_DEFER_DELETE = "R_CHECKLIST_ITEM_WAIVE_DEFER_DELETE";

	public final Object invoke(MethodInvocation invocation) throws Throwable {
		try {
			return doInvoke(invocation);
		}
		catch (InvocationTargetException ex) {
			throw ex.getCause();
		}
	}

	/**
	 * To invoke the proxy method and throwable will be handled by the caller
	 * which is {@link #invoke(MethodInvocation)}.
	 * 
	 * @param invocation the method invocation consist of target, method,
	 *        arguments
	 * @return result from the invocation on the method
	 * @throws Throwable if there is any error occurred
	 */
	protected abstract Object doInvoke(MethodInvocation invocation) throws Throwable;

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
	 * Method moved to CollaborationTaskNotificationInterceptor instead
	 * 
	 * @param anICheckListTrxValue
	 * @param isObselete
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException
	 * 
	 * @see AbstractCollaborationTaskNotificationInterceptor
	 */
	protected void sendCollateralNotification(ICheckListTrxValue anICheckListTrxValue, boolean isObselete)
			throws TrxOperationException {
		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ICollateralProxy colProxy = CollateralProxyFactory.getProxy();
			ICollateralCheckListOwner owner = (ICollateralCheckListOwner) anICheckListTrxValue.getCheckList()
					.getCheckListOwner();
			ICheckList checkList = anICheckListTrxValue.getCheckList();
			String[] countryList = new String[1];
			ILimitProfile limitProfile = anICheckListTrxValue.getTrxContext().getLimitProfile();

			boolean isInCountry = CheckListUtil.isInCountry(anICheckListTrxValue.getTrxContext().getTeam(),
					limitProfile);
			if (isInCountry) {
				return;
			}

			DefaultLogger.debug(this, "<<<<<<<<<<<<<<<< is limitprofile null: "
					+ (limitProfile == null ? "NULL" : "not null"));
			if (limitProfile == null) {
				limitProfile = proxy.getLimitProfile(owner.getLimitProfileID());
			}

			ICollateral collateral = colProxy.getCollateral(owner.getCollateralID(), true);
			countryList[0] = limitProfile.getOriginatingLocation().getCountryCode();
			if (!countryList[0].equals(collateral.getCollateralLocation())) {
				OBCollaborationTaskInfo info = new OBCollaborationTaskInfo();
				info.setSecurityID(String.valueOf(collateral.getSCISecurityID()));
				info.setSecuritySubTypeID(collateral.getCollateralSubType().getSubTypeCode());
				info.setSecurityMaturityDate(getSecurityMaturityDate(collateral));
				info.setSecurityLocation(collateral.getCollateralLocation());
				info.setOriginatingCountry(countryList[0]);
				if (isObselete) {
					info.setSecurityRemarks("");
				}
				else {
					info.setSecurityRemarks(anICheckListTrxValue.getRemarks());
				}
				info.setBcaBkgCountry(limitProfile.getOriginatingLocation().getCountryCode());
				info.setBeforeChangeStatus(checklistOldStatus);
				info.setAfterChangeStatus(checkList.getCheckListStatus());
				String eventName = null;
				String ruleName = null;
				if (anICheckListTrxValue.getSendItemNotificationInd()) {
					info.setItemList(setNotificationItemList(checkList));
					ruleName = RULE_CHECKLIST_ITEM_WAIVE_DEFER_DELETE;
					eventName = EVENT_COL_ITEM_WAIVE_DEFER_DELETE;
				}
				else if (anICheckListTrxValue.getSendNotificationInd()) {
					if (isObselete) {
						ruleName = RULE_CHECKLIST_DELETION;
						eventName = EVENT_COL_CHECKLIST_DELETION_READY;
					}
					else if (isCheckListCompletedNotification(checklistOldStatus, checkList.getCheckListStatus())) {
						ruleName = RULE_CHECKLIST_COMPLETION;
						eventName = EVENT_CHECKLIST_COMPLETION;
					}
					else {
						ruleName = RULE_CHECKLIST_STATUS_CHANGE;
						eventName = EVENT_CHECKLIST_STATUS_CHANGE;
					}
				}
				CollaborationTaskListener listener = new CollaborationTaskListener();
				if (eventName != null) {
					List customerList = getSBCheckListBusManager().getSecurityOwnerList(owner.getCollateralID(),
							limitProfile.getLimitProfileID());
					ISecurityCustomer[] customerArray = null;
					if ((customerList != null) && (customerList.size() > 0)) {
						customerArray = (ISecurityCustomer[]) customerList.toArray(new ISecurityCustomer[0]);
					}
					if ((customerArray == null) || (customerArray.length == 0)) {
						throw new CollateralException("Cannot find customer with : - CollID: "
								+ owner.getCollateralID() + " LPID: " + limitProfile.getLimitProfileID());
					}
					for (int index = 0; index < customerArray.length; index++) {
						info.setLeID(String.valueOf(customerArray[index].getLeID()));
						info.setLeName(customerArray[index].getLeName());
						info.setSegment(customerArray[index].getSegment());
						ArrayList paramList = new ArrayList();
						paramList.add(info);
						paramList.add("");
						paramList.add(constructRuleParam(ruleName));
						listener.fireEvent(eventName, paramList);
					}
				}
			}
		}
		catch (CollateralException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("General Exception in sendCollateralNotification" + ex.toString());
		}
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
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException
	 * 
	 * @see AbstractCollaborationTaskNotificationInterceptor
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
			throw new TrxOperationException(ex);
		}
		catch (CollateralException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("General Exception in sendCollateralNotification" + ex.toString());
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
		if (!anOldStatus.equals(newStatus)) {
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
