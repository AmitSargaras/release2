/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/AbstractCCTaskTrxOperation.java,v 1.5 2006/09/11 07:38:29 jzhai Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

//java
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManager;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManagerHome;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.bus.ICCTask;
import com.integrosys.cms.app.collaborationtask.bus.SBCollaborationTaskBusManager;
import com.integrosys.cms.app.collaborationtask.bus.SBCollaborationTaskBusManagerHome;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.IContext;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
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
import com.integrosys.cms.app.chktemplate.bus.SBCheckListTemplateBusManager;
import com.integrosys.cms.app.chktemplate.bus.SBCheckListTemplateBusManagerHome;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Abstract class that contain methods that is common among the set of CC task
 * trx operations
 * 
 * @author $Author: jzhai $
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/11 07:38:29 $ Tag: $Name: $
 */
public abstract class AbstractCCTaskTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	private static final String EVENT_CC_TASK_CREATION = "EV_CC_TASK_CREATION";

	private static final String EVENT_CC_TASK_REJECTION = "EV_CC_TASK_REJECTION";

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
	 * This method set the primary key from the original to the copied CC task
	 * objects. It is required for the case of updating staging from actual and
	 * vice versa as there is a need to perform a deep clone of the object and
	 * set the required attribute in the object to the original one so that a
	 * proper update can be done.
	 * @param anOriginal of ICCTask type
	 * @param aCopy of ICCTask type
	 * @return ICCTask - the copied object with required attributes from the
	 *         original CC task
	 * @throws TrxOperationException on errors
	 */
	protected ICCTask mergeCCTask(ICCTask anOriginal, ICCTask aCopy) throws TrxOperationException {
		aCopy.setTaskID(anOriginal.getTaskID());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	/**
	 * Create the staging document item doc
	 * @param anICCTaskTrxValue of ICCTaskTrxValue type
	 * @return ICCTaskTrxValue - the trx object containing the created staging
	 *         document item
	 * @throws TrxOperationException if errors
	 */
	protected ICCTaskTrxValue createStagingCCTask(ICCTaskTrxValue anICCTaskTrxValue) throws TrxOperationException {
		try {
			ICCTask colTask = getSBStagingCollaborationTaskBusManager().createCCTask(
					anICCTaskTrxValue.getStagingCCTask());
			anICCTaskTrxValue.setStagingCCTask(colTask);
			anICCTaskTrxValue.setStagingReferenceID(String.valueOf(colTask.getTaskID()));
			return anICCTaskTrxValue;
		}
		catch (CollaborationTaskException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Update a CC task transaction
	 * @param anICCTaskTrxValue of ICCTaskTrxValue type
	 * @return ICCTaskTrxValue - the document item specific transaction object
	 *         created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected ICCTaskTrxValue updateCCTaskTransaction(ICCTaskTrxValue anICCTaskTrxValue) throws TrxOperationException {
		try {
			anICCTaskTrxValue = prepareTrxValue(anICCTaskTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(anICCTaskTrxValue);
			OBCCTaskTrxValue newValue = new OBCCTaskTrxValue(tempValue);
			newValue.setCCTask(anICCTaskTrxValue.getCCTask());
			newValue.setStagingCCTask(anICCTaskTrxValue.getStagingCCTask());
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
	 * @return ICCTaskTrxValue - the document item specific trx value object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected ICCTaskTrxValue getCCTaskTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (ICCTaskTrxValue) anITrxValue;
		}
		catch (ClassCastException ex) {
			throw new TrxOperationException("The ITrxValue is not of type OBCCTaskTrxValue: " + ex.toString());
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
	protected ICCTaskTrxValue prepareTrxValue(ICCTaskTrxValue anICCTaskTrxValue) {
		if (anICCTaskTrxValue != null) {
			ICCTask actual = anICCTaskTrxValue.getCCTask();
			ICCTask staging = anICCTaskTrxValue.getStagingCCTask();
			if ((actual != null)
					&& (actual.getTaskID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anICCTaskTrxValue.setReferenceID(String.valueOf(actual.getTaskID()));
			}
			else {
				anICCTaskTrxValue.setReferenceID(null);
			}
			if ((staging != null)
					&& (staging.getTaskID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anICCTaskTrxValue.setStagingReferenceID(String.valueOf(staging.getTaskID()));
			}
			else {
				anICCTaskTrxValue.setStagingReferenceID(null);
			}
			return anICCTaskTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value of ICCTaskTrxValue type
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ICCTaskTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	protected void sendCCNotification(ICCTaskTrxValue anICCTaskTrxValue) throws TrxOperationException {
		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ILimitProfile limitProfile = null;
			String[] countryList = new String[1];
			String segment = null;
			ICCTask ccTask = anICCTaskTrxValue.getCCTask();

			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer customer = null;
			boolean isInCountry = false;
			if (ccTask.getLimitProfileID() != ICMSConstant.LONG_INVALID_VALUE) {
				limitProfile = anICCTaskTrxValue.getTrxContext().getLimitProfile();
				customer = custProxy.getCustomer(limitProfile.getCustomerID());
				segment = customer.getCMSLegalEntity().getCustomerSegment();
				isInCountry = isInCountry(anICCTaskTrxValue.getTrxContext(), limitProfile);
				if (isInCountry) {
					countryList[0] = ccTask.getDomicileCountry();
				}
				else {
					countryList[0] = limitProfile.getOriginatingLocation().getCountryCode();
				}
			}
			else {
				customer = custProxy.getCustomer(ccTask.getCustomerID());
				segment = customer.getCMSLegalEntity().getCustomerSegment();
				isInCountry = isInCountry(anICCTaskTrxValue.getTrxContext(), customer);
				if (isInCountry) {
					countryList[0] = ccTask.getDomicileCountry();
				}
				else {
					countryList[0] = customer.getOriginatingLocation().getCountryCode();
				}
			}

			OBCCCollaborationTaskInfo info = new OBCCCollaborationTaskInfo();
			info.setLeID(customer.getCMSLegalEntity().getLEReference());
			info.setLeName(customer.getCMSLegalEntity().getLegalName());
			info.setCustomerCategory(ccTask.getCustomerCategory());
			info.setCustomerID(String.valueOf(ccTask.getCustomerID()));
			info.setOrgCode(ccTask.getOrgCode());
			info.setOriginatingOrganisation(ccTask.getOrgCode());
			info.setDomicileCountry(ccTask.getDomicileCountry());
			info.setCCRemarks(ccTask.getRemarks());
			info.setCoborrowerPledgorLeName(ccTask.getLegalName());
			if (ICMSConstant.CHECKLIST_CO_BORROWER.equals(ccTask.getCustomerCategory())) {
				info.setGovernLaw(getSBCheckListTemplateBusManager().getLaw(ccTask.getDomicileCountry()));

				info.setBcaBkgCountry(limitProfile.getOriginatingLocation().getCountryCode());
				if ((ccTask.getLegalRef() != null) && !ccTask.getLegalRef().equals("0")) {
					info.setCoborrowerPledgorID(ccTask.getLegalRef());
				}
			}
			else {
				if (ICMSConstant.CHECKLIST_PLEDGER.equals(ccTask.getCustomerCategory())) {
					info.setGovernLaw(getSBCheckListTemplateBusManager().getLaw(ccTask.getDomicileCountry()));
					info.setBcaBkgCountry(limitProfile.getOriginatingLocation().getCountryCode());
					ICollateralProxy colProxy = CollateralProxyFactory.getProxy();
					info.setCoborrowerPledgorID(String.valueOf(colProxy.getPledgor(ccTask.getCustomerID())
							.getSysGenPledgorID()));
				}
				else {
					// non borrower
					info.setGovernLaw(getSBCheckListTemplateBusManager().getLaw(ccTask.getDomicileCountry()));
				}
			}
			info.setOriginatingCountry(countryList[0]);
			info.setSegment(segment);
			ArrayList list = new ArrayList();
			list.add(info);
			list.add("");
			list.add(constructRuleParam());
			CCCollaborationTaskListener listener = new CCCollaborationTaskListener();
			if (ICMSConstant.STATE_PENDING_REJECT.equals(anICCTaskTrxValue.getFromState())) {
				listener.fireEvent(EVENT_CC_TASK_REJECTION, list);
			}
			else {
				listener.fireEvent(EVENT_CC_TASK_CREATION, list);
			}
		}
		catch (CustomerException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("General Exception in sendCCNotification" + ex.toString());
		}
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	protected IRuleParam constructRuleParam() {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID("R_CC_TASK_CREATION");
		param.setSysDate(DateUtil.getDate());
		param.setRuleNum(-1);
		return param;
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
	 * This method is to help to check whether the user is in Country by
	 * ILimitProfile
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

	/**
	 * This method is to help to check whether the user is in Country by
	 * ICMSCustomer
	 */
	private boolean isInCountry(IContext anIContext, ICMSCustomer customer) {
		ITeam team = anIContext.getTeam();
		String[] countryList = team.getCountryCodes();
		if (countryList == null) {
			return false;
		}
		String bkgLocation = customer.getOriginatingLocation().getCountryCode();
		for (int ii = 0; ii < countryList.length; ii++) {
			if (countryList[ii].equals(bkgLocation)) {
				return true;
			}
		}
		return false;
	}
}