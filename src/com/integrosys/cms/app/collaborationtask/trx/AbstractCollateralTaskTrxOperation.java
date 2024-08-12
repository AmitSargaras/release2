/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/AbstractCollateralTaskTrxOperation.java,v 1.8 2006/09/11 07:38:35 jzhai Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

//java
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.checklist.bus.ISecurityCustomer;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManager;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManagerHome;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.collaborationtask.bus.SBCollaborationTaskBusManager;
import com.integrosys.cms.app.collaborationtask.bus.SBCollaborationTaskBusManagerHome;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.IContext;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.collaborationtask.CollaborationTaskListener;
import com.integrosys.cms.app.eventmonitor.collaborationtask.OBCollaborationTaskInfo;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Abstract class that contain methods that is common among the set of
 * collateral task trx operations
 * 
 * @author $Author: jzhai $
 * @version $Revision: 1.8 $
 * @since $Date: 2006/09/11 07:38:35 $ Tag: $Name: $
 */
public abstract class AbstractCollateralTaskTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	private static final String EVENT_COL_TASK_CREATION = "EV_COL_TASK_CREATION";

	private static final String EVENT_COL_TASK_REJECTION = "EV_COL_TASK_REJECTION";

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
	 * This method set the primary key from the original to the copied
	 * collateral task objects. It is required for the case of updating staging
	 * from actual and vice versa as there is a need to perform a deep clone of
	 * the object and set the required attribute in the object to the original
	 * one so that a proper update can be done.
	 * @param anOrginal of ICollateralTask type
	 * @param aCopy of ICollateralTask type
	 * @return ICollateralTask - the copied object with required attributes from
	 *         the original collateral task
	 * @throws TrxOperationException on errors
	 */
	protected ICollateralTask mergeCollateralTask(ICollateralTask anOriginal, ICollateralTask aCopy)
			throws TrxOperationException {
		aCopy.setTaskID(anOriginal.getTaskID());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	/**
	 * Create the staging document item doc
	 * @param anICollateralTaskTrxValue of ICollateralTaskTrxValue type
	 * @return ICollateralTaskTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws TrxOperationException if errors
	 */
	protected ICollateralTaskTrxValue createStagingCollateralTask(ICollateralTaskTrxValue anICollateralTaskTrxValue)
			throws TrxOperationException {
		try {
			ICollateralTask colTask = getSBStagingCollaborationTaskBusManager().createCollateralTask(
					anICollateralTaskTrxValue.getStagingCollateralTask());
			anICollateralTaskTrxValue.setStagingCollateralTask(colTask);
			anICollateralTaskTrxValue.setStagingReferenceID(String.valueOf(colTask.getTaskID()));
			return anICollateralTaskTrxValue;
		}
		catch (CollaborationTaskException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Update a collateral task transaction
	 * @param anITrxValue of ITrxValue type
	 * @return ICollateralTaskTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected ICollateralTaskTrxValue updateCollateralTaskTransaction(ICollateralTaskTrxValue anICollateralTaskTrxValue)
			throws TrxOperationException {
		try {
			anICollateralTaskTrxValue = prepareTrxValue(anICollateralTaskTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(anICollateralTaskTrxValue);
			OBCollateralTaskTrxValue newValue = new OBCollateralTaskTrxValue(tempValue);
			newValue.setCollateralTask(anICollateralTaskTrxValue.getCollateralTask());
			newValue.setStagingCollateralTask(anICollateralTaskTrxValue.getStagingCollateralTask());
			return newValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue of ITrxValue type
	 * @return ICollateralTaskTrxValue - the document item specific trx value
	 *         object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected ICollateralTaskTrxValue getCollateralTaskTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (ICollateralTaskTrxValue) anITrxValue;
		}
		catch (ClassCastException ex) {
			throw new TrxOperationException("The ITrxValue is not of type OBCollateralTaskTrxValue: " + ex.toString());
		}
	}

	/**
	 * To get the remote handler for the staging collaboration task session bean
	 * @return SBCollaborationTaskBusManager - the remote handler for the
	 *         staging collaboration task session bean
	 */
	protected SBCollaborationTaskBusManager getSBStagingCollaborationTaskBusManager() {
		SBCollaborationTaskBusManager remote = (SBCollaborationTaskBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_COLLABORATION_BUS_JNDI, SBCollaborationTaskBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * To get the remote handler for the collaboration task session bean
	 * @return SBCollaborationTaskBusManager - the remote handler for the
	 *         Collaboration task session bean
	 */
	protected SBCollaborationTaskBusManager getSBCollaborationTaskBusManager() {
		SBCollaborationTaskBusManager remote = (SBCollaborationTaskBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_COLLABORATION_BUS_JNDI, SBCollaborationTaskBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * Prepares a trx object
	 */
	protected ICollateralTaskTrxValue prepareTrxValue(ICollateralTaskTrxValue anICollateralTaskTrxValue) {
		if (anICollateralTaskTrxValue != null) {
			ICollateralTask actual = anICollateralTaskTrxValue.getCollateralTask();
			ICollateralTask staging = anICollateralTaskTrxValue.getStagingCollateralTask();
			if ((actual != null)
					&& (actual.getTaskID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anICollateralTaskTrxValue.setReferenceID(String.valueOf(actual.getTaskID()));
			}
			else {
				anICollateralTaskTrxValue.setReferenceID(null);
			}
			if ((staging != null)
					&& (staging.getTaskID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anICollateralTaskTrxValue.setStagingReferenceID(String.valueOf(staging.getTaskID()));
			}
			else {
				anICollateralTaskTrxValue.setStagingReferenceID(null);
			}
			return anICollateralTaskTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value of ICollateralTaskTrxValue type
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ICollateralTaskTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * Send collateral collaboration task notification
	 */
	protected void sendCollateralNotification(ICollateralTaskTrxValue anICollateralTaskTrxValue)
			throws TrxOperationException {
		try {
			String[] countryList = new String[1];
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ICollateralProxy colProxy = CollateralProxyFactory.getProxy();

			ICollateralTask colTask = anICollateralTaskTrxValue.getCollateralTask();
			ILimitProfile limitProfile = proxy.getLimitProfile(colTask.getLimitProfileID());
			ICollateral collateral = colProxy.getCollateral(colTask.getCollateralID(), true);

			boolean isInCountry = isInCountry(anICollateralTaskTrxValue.getTrxContext(), limitProfile);
			if (isInCountry) {
				countryList[0] = colTask.getCollateralLocation();
			}
			else {
				countryList[0] = limitProfile.getOriginatingLocation().getCountryCode();
			}

			String eventName = null;
			if (ICMSConstant.STATE_PENDING_REJECT.equals(anICollateralTaskTrxValue.getFromState())) {
				eventName = EVENT_COL_TASK_REJECTION;
			}
			else {
				eventName = EVENT_COL_TASK_CREATION;
			}
			OBCollaborationTaskInfo info = new OBCollaborationTaskInfo();
			info.setSecurityID(String.valueOf(collateral.getSCISecurityID()));
			info.setSecuritySubTypeID(collateral.getCollateralSubType().getSubTypeCode());
			info.setSecurityMaturityDate(getSecurityMaturityDate(collateral));
			info.setSecurityLocation(collateral.getCollateralLocation());
			info.setOriginatingCountry(countryList[0]);
			info.setSecurityRemarks(colTask.getRemarks());
			info.setBcaBkgCountry(limitProfile.getOriginatingLocation().getCountryCode());

			List customerList = getSBCheckListBusManager().getCustomerListByCollateralID(collateral.getCollateralID());
			ISecurityCustomer[] customerArray = null;
			if ((customerList != null) && (customerList.size() > 0)) {
				customerArray = (ISecurityCustomer[]) customerList.toArray(new ISecurityCustomer[0]);
			}
			if ((customerArray == null) && (customerArray.length == 0)) {
				DefaultLogger.debug(this, "Cannot find any customer, no notification!");
				return;
			}
			CollaborationTaskListener listener = new CollaborationTaskListener();
			for (int index = 0; index < customerArray.length; index++) {
				info.setLeID(String.valueOf(customerArray[index].getLeID()));
				info.setLeName(customerArray[index].getLeName());
				info.setSegment(customerArray[index].getSegment());
				ArrayList paramList = new ArrayList();
				paramList.add(info);
				paramList.add("");
				paramList.add(constructRuleParam());
				listener.fireEvent(eventName, paramList);
			}
		}
		catch (LimitException ex) {
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
	private IRuleParam constructRuleParam() {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID("R_COL_TASK_CREATION");
		param.setSysDate(DateUtil.getDate());
		param.setRuleNum(-1);
		return param;
	}

	/**
	 * This method is to help to check whether the user is in Country
	 */
	private boolean isInCountry(IContext anIContext, ILimitProfile anILimitProfile) {
		ITeam team = anIContext.getTeam();
		String[] countryList = team.getCountryCodes();
		if (countryList == null) {
			return false;
		}
		String bkgLocation = anILimitProfile.getOriginatingLocation().getCountryCode();
		for (int ii = 0; ii < countryList.length; ii++) {
			if (countryList[ii].equals(bkgLocation)) {
				return true;
			}
		}
		return false;
	}

	private Date[] getSecurityMaturityDate(ICollateral collateral) {
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

	protected SBCheckListBusManager getSBCheckListBusManager() {
		SBCheckListBusManager remote = (SBCheckListBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CHECKLIST_BUS_JNDI, SBCheckListBusManagerHome.class.getName());
		return remote;
	}
}