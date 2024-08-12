/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/proxy/AbstractCollaborationTaskProxyManager.java,v 1.24 2006/09/21 09:45:08 jychong Exp $
 */
package com.integrosys.cms.app.collaborationtask.proxy;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.checklist.bus.CCCheckListSummary;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collaborationtask.bus.CCTaskSearchCriteria;
import com.integrosys.cms.app.collaborationtask.bus.CCTaskSearchResult;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskNotAllowedException;
import com.integrosys.cms.app.collaborationtask.bus.CollateralTaskSearchCriteria;
import com.integrosys.cms.app.collaborationtask.bus.CollateralTaskSearchResult;
import com.integrosys.cms.app.collaborationtask.bus.ICCTask;
import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.collaborationtask.trx.CollaborationTaskTrxControllerFactory;
import com.integrosys.cms.app.collaborationtask.trx.ICCTaskTrxValue;
import com.integrosys.cms.app.collaborationtask.trx.ICollateralTaskTrxValue;
import com.integrosys.cms.app.collaborationtask.trx.OBCCTaskTrxValue;
import com.integrosys.cms.app.collaborationtask.trx.OBCollateralTaskTrxValue;
import com.integrosys.cms.app.common.IContext;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * This abstract class will contains all the biz related logic that is
 * independent of any technology implementation such as EJB
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.24 $
 * @since $Date: 2006/09/21 09:45:08 $ Tag: $Name: $
 */
public abstract class AbstractCollaborationTaskProxyManager implements ICollaborationTaskProxyManager {
	/**
	 * Check if there is any pending collateral task trx
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @param aCollateralLocation of String type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws CollaborationTaskException on errors
	 */
	public boolean hasPendingCollateralTaskTrx(long aLimitProfileID, long aCollateralID, String aCollateralLocation)
			throws CollaborationTaskException {
		CollateralTaskSearchCriteria criteria = new CollateralTaskSearchCriteria();
		criteria.setLimitProfileID(aLimitProfileID);
		criteria.setCollateralID(aCollateralID);
		criteria.setCollateralLocation(aCollateralLocation);
		String[] trxStatusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_PENDING_UPDATE,
				ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_PENDING_REJECT, ICMSConstant.STATE_REJECT_REJECTED };
		criteria.setTrxStatusList(trxStatusList);
		int count = getNoOfCollateralTask(criteria);
		return count != 0;
	}

	/**
	 * Check if there is any pending CC task trx
	 * @param aLimitProfileID of long type
	 * @param aCustomerCategory of String type
	 * @param aCustomerID of long type
	 * @param aDomicileCtry of String type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws CollaborationTaskException on errors
	 */
	public boolean hasPendingCCTaskTrx(long aLimitProfileID, String aCustomerCategory, long aCustomerID,
			String aDomicileCtry) throws CollaborationTaskException {
		CCTaskSearchCriteria criteria = new CCTaskSearchCriteria();
		criteria.setLimitProfileID(aLimitProfileID);
		criteria.setCustomerCategory(aCustomerCategory);
		criteria.setCustomerID(aCustomerID);
		// criteria.setDomicileCountry(aDomicileCtry);
		String[] trxStatusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_PENDING_UPDATE,
				ICMSConstant.STATE_REJECTED, ICMSConstant.STATE_PENDING_REJECT, ICMSConstant.STATE_REJECT_REJECTED };
		criteria.setTrxStatusList(trxStatusList);
		int count = getNoOfCCTask(criteria);
		return count != 0;
	}

	/**
	 * Get the collateral summary list
	 * @param anIContext of IContext type
	 * @param anILimitProfile Limit Profile
	 * @return HashMap - the will contain the collateral summary list and the
	 *         whether collaboration is allowed or not
	 * @throws CollaborationTaskNotAllowedException CollaborationTaskException
	 */
	public HashMap getCollateralSummaryList(IContext anIContext, ILimitProfile anILimitProfile)
			throws CollaborationTaskNotAllowedException, CollaborationTaskException {
		if (anIContext == null) {
			throw new CollaborationTaskException("The IContext is null !!!");
		}

		if (anILimitProfile == null) {
			throw new CollaborationTaskException("The ILimitProfile is null !!!");
		}
		/*
		 * if (!canCreateTask(anIContext, anILimitProfile)) { throw new
		 * CollaborationTaskNotAllowedException
		 * ("Collaboration task is not allowed"); }
		 */
		try {
			ICheckListProxyManager mgr = CheckListProxyManagerFactory.getCheckListProxyManager();
			CollateralCheckListSummary[] summaryList = mgr.getCollateralCheckListSummaryList(anIContext,
					anILimitProfile.getLimitProfileID());

			if ((summaryList == null) || (summaryList.length == 0)) {
				CollaborationTaskException exp = new CollaborationTaskException();
				exp.setErrorCode(ICMSErrorCodes.COLLATERAL_TASK_NOT_REQUIRED);
				throw exp;
			}
			HashMap map = new HashMap();
			for (int ii = 0; ii < summaryList.length; ii++) {
				map.put(summaryList[ii], getAllowCreateTaskInd(anIContext, anILimitProfile, summaryList[ii]
						.getCollateralID(), summaryList[ii].getCollateralLocation()));
			}
			map.put(ICMSConstant.SORTED_TASK_LIST, summaryList);
			return map;
		}
		catch (CheckListException ex) {
			rollback();
			throw new CollaborationTaskException("Exception in getCollateralSummaryList", ex);
		}
	}

	/**
	 * Get the CC summary list
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return HashMap - the will contain the CC summary list and the whether
	 *         collaboration is allowed or not
	 * @throws CollaborationTaskNotAllowedException CollaborationTaskException
	 */
	public HashMap getCCSummaryList(IContext anIContext, ILimitProfile anILimitProfile)
			throws CollaborationTaskNotAllowedException, CollaborationTaskException {
		if (anIContext == null) {
			throw new CollaborationTaskException("The IContext is null !!!");
		}

		if (anILimitProfile == null) {
			throw new CollaborationTaskException("The ILimitProfile is null !!!");
		}
		/*
		 * if (!canCreateTask(anIContext, anILimitProfile)) { throw new
		 * CollaborationTaskNotAllowedException
		 * ("Collaboration task is not allowed"); }
		 */
		try {
			ICheckListProxyManager mgr = CheckListProxyManagerFactory.getCheckListProxyManager();
			CCCheckListSummary[] summaryList = mgr.getCCCheckListSummaryForCollaboration(anIContext, anILimitProfile);
			if ((summaryList == null) || (summaryList.length == 0)) {
				CollaborationTaskException exp = new CollaborationTaskException();
				exp.setErrorCode(ICMSErrorCodes.CC_TASK_NOT_REQUIRED);
				throw exp;
			}
			HashMap map = new HashMap();
			for (int ii = 0; ii < summaryList.length; ii++) {
				/*
				 * if (summaryList[ii].getDomicileCtry() != null) { if
				 * (!summaryList[ii].getDomicileCtry().equals(anILimitProfile.
				 * getOriginatingLocation().getCountryCode())) {
				 * summaryList[ii].setOrgCode(null); } }
				 */
				map.put(summaryList[ii], getAllowCreateTaskInd(anIContext, anILimitProfile, summaryList[ii]));
			}
			map.put(ICMSConstant.SORTED_TASK_LIST, summaryList);
			return map;
		}
        catch (CheckListTemplateException ex) {
            rollback();
            throw new CollaborationTaskException("Exception in getCollateralSummaryList", ex);
        }
		catch (CheckListException ex) {
			rollback();
			throw new CollaborationTaskException("Exception in getCollateralSummaryList", ex);
		}
	}

	/**
	 * Get the CC summary list for non borrower
	 * @param anIContext of IContext type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return HashMap - the will contain the CC summary list and the whether
	 *         collaboration is allowed or not
	 * @throws CollaborationTaskNotAllowedException CollaborationTaskException
	 */
	public HashMap getCCSummaryList(IContext anIContext, ICMSCustomer anICMSCustomer)
			throws CollaborationTaskNotAllowedException, CollaborationTaskException {
		if (anIContext == null) {
			throw new CollaborationTaskException("The IContext is null !!!");
		}

		if (anICMSCustomer == null) {
			throw new CollaborationTaskException("The ICMSCustomer is null !!!");
		}
		/*
		 * if (!canCreateTask(anIContext, anICMSCustomer)) { throw new
		 * CollaborationTaskNotAllowedException
		 * ("Collaboration task is not allowed"); }
		 */
		try {
			ICheckListProxyManager mgr = CheckListProxyManagerFactory.getCheckListProxyManager();
			CCCheckListSummary[] summaryList = mgr.getCCCheckListSummaryListForNonBorrower(anIContext,
					ICMSConstant.LONG_INVALID_VALUE, anICMSCustomer.getCustomerID(), true);
			if ((summaryList == null) || (summaryList.length == 0)) {
				CollaborationTaskException exp = new CollaborationTaskException();
				exp.setErrorCode(ICMSErrorCodes.CC_TASK_NOT_REQUIRED);
				throw exp;
			}
			HashMap map = new HashMap();
			for (int ii = 0; ii < summaryList.length; ii++) {
				if (!summaryList[ii].getDomicileCtry().equals(anICMSCustomer.getOriginatingLocation().getCountryCode())) {
					summaryList[ii].setOrgCode(null);
				}
				map.put(summaryList[ii], getAllowCreateTaskInd(anIContext, summaryList[ii]));
			}
			map.put(ICMSConstant.SORTED_TASK_LIST, summaryList);
			return map;
		}
        catch (CheckListTemplateException ex) {
            rollback();
            throw new CollaborationTaskException("Exception in getCollateralSummaryList", ex);
        }
		catch (CheckListException ex) {
			rollback();
			throw new CollaborationTaskException("Exception in getCollateralSummaryList", ex);
		}
	}

	/**
	 * Get the collateral task trx value using the limitprofile id, collateral
	 * ID and collateral location
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @param aCollateralLocation of String type
	 * @return ICollateralTaskTrxValue - the trx value of the collateral task
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue getCollateralTaskTrxValue(long aLimitProfileID, long aCollateralID,
			String aCollateralLocation) throws CollaborationTaskException {

		CollateralTaskSearchCriteria criteria = new CollateralTaskSearchCriteria();
		criteria.setLimitProfileID(aLimitProfileID);
		criteria.setCollateralID(aCollateralID);
		criteria.setCollateralLocation(aCollateralLocation);
		CollateralTaskSearchResult[] resultList = getCollateralTask(criteria);

		if ((resultList == null) || (resultList.length == 0)) {
			// throw new
			// CollaborationTaskException("No record with LimitProfileID " +
			// aLimitProfileID + " CollateralID " + aCollateralID +
			// " and location " + aCollateralLocation);
			return null;
		}
		if (resultList.length != 1) {
			throw new CollaborationTaskException("More than 1 records with LimitProfileID " + aLimitProfileID
					+ " CollateralID " + aCollateralID + " and location " + aCollateralLocation);
		}

		String trxID = resultList[0].getTrxID();
		return getCollateralTaskByTrxID(trxID);
	}

	/**
	 * Get the collateral task trx value using the limitprofile id, collateral
	 * ID and collateral location
	 * @param resultList of CollateralTaskSearchResult type
	 * @return ICollateralTaskTrxValue - the trx value of the collateral task
	 * @throws CollaborationTaskException on errors
	 */
	private ICollateralTaskTrxValue getCollateralTaskTrxValue(CollateralTaskSearchResult[] resultList)
			throws CollaborationTaskException {
		if ((resultList == null) || (resultList.length == 0)) {
			// throw new
			// CollaborationTaskException("No record with LimitProfileID " +
			// aLimitProfileID + " CollateralID " + aCollateralID +
			// " and location " + aCollateralLocation);
			return null;
		}
		if (resultList.length != 1) {
			throw new CollaborationTaskException(
					"More than 1 records at getCollateralTaskTrxValue collateralTaskSearchResult[]");
		}

		String trxID = resultList[0].getTrxID();
		return getCollateralTaskByTrxID(trxID);
	}

	/**
	 * Get the collateral task search result
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @param aCollateralLocation of String type
	 * @return CollateralTaskSearchResult[] - result list of collateral task
	 * @throws CollaborationTaskException on errors
	 */
	public CollateralTaskSearchResult[] getCollateralTask(long aLimitProfileID, long aCollateralID,
			String aCollateralLocation) throws CollaborationTaskException {
		CollateralTaskSearchCriteria criteria = new CollateralTaskSearchCriteria();
		criteria.setLimitProfileID(aLimitProfileID);
		criteria.setCollateralID(aCollateralID);
		criteria.setCollateralLocation(aCollateralLocation);
		return getCollateralTask(criteria);
	}

	/**
	 * Get the cc task trx value using the limitprofile id, customer type,
	 * customer id and the country
	 * @param aLimitProfileID of long type
	 * @param aCustomerCategory of String type
	 * @param aCustomerID of long type
	 * @param aDomicileCtry of String type
	 * @return ICCTaskTrxValue - the trx value of the CC task
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue getCCTaskTrxValue(long aLimitProfileID, String aCustomerCategory, long aCustomerID,
			String aDomicileCtry) throws CollaborationTaskException {
		CCTaskSearchCriteria criteria = new CCTaskSearchCriteria();
		criteria.setLimitProfileID(aLimitProfileID);
		criteria.setCustomerCategory(aCustomerCategory);
		criteria.setCustomerID(aCustomerID);
		// criteria.setDomicileCountry(aDomicileCtry);
		String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_PENDING_UPDATE,
				ICMSConstant.STATE_ACTIVE, ICMSConstant.STATE_REJECTED };
		criteria.setTrxStatusList(statusList);
		CCTaskSearchResult[] resultList = getCCTask(criteria);
		if ((resultList == null) || (resultList.length == 0)) {
			// throw new
			// CollaborationTaskException("No record with LimitProfileID " +
			// aLimitProfileID + " CustomerType " + aCustomerCategory +
			// "CustomerID " + aCustomerID + " and location " + aDomicileCtry);
			return null;
		}
		if (resultList.length != 1) {
			throw new CollaborationTaskException("More than 1 records with LimitProfileID " + aLimitProfileID
					+ " CustomerType " + aCustomerCategory + "CustomerID " + aCustomerID + " and location "
					+ aDomicileCtry);
		}

		String trxID = resultList[0].getTrxID();
		return getCCTaskByTrxID(trxID);
	}

	/**
	 * Get the cc task trx value using the limitprofile id, customer type,
	 * customer id and the country
	 * @param resultList of CCTaskSearchResult type
	 * @return ICCTaskTrxValue - the trx value of the CC task
	 * @throws CollaborationTaskException on errors
	 */
	private ICCTaskTrxValue getCCTaskTrxValue(CCTaskSearchResult[] resultList) throws CollaborationTaskException {
		if ((resultList == null) || (resultList.length == 0)) {
			// throw new
			// CollaborationTaskException("No record with LimitProfileID " +
			// aLimitProfileID + " CustomerType " + aCustomerCategory +
			// "CustomerID " + aCustomerID + " and location " + aDomicileCtry);
			return null;
		}
		if (resultList.length != 1) {
			throw new CollaborationTaskException("More than 1 records at getCCTaskTrxValue");
		}

		String trxID = resultList[0].getTrxID();
		return getCCTaskByTrxID(trxID);
	}

	/**
	 * Maker create the collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTask of ICollateralTask type
	 * @return ICollateralTaskTrxValue - the generates Collateral Task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue makerCreateCollaborationTask(ITrxContext anITrxContext,
			ICollateralTask anICollateralTask) throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The ITrxContext is null!!!");
		}
		if (anICollateralTask == null) {
			throw new CollaborationTaskException("The ICollateralTask to be updated is null !!!");
		}
		ICollateralTaskTrxValue trxValue = formulateTrxValue(anITrxContext, anICollateralTask);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_COLLATERAL_TASK);
		return operate(trxValue, param);
	}

	/**
	 * Maker update the collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTaskTrxValue of ICollateralTaskTrxValue type
	 * @param anICollateralTask of ICollateralTask type
	 * @return ICollateralTaskTrxValue - the generates Collateral Task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue makerUpdateCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue, ICollateralTask anICollateralTask)
			throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The ITrxContext is null!!!");
		}
		if (anICollateralTask == null) {
			throw new CollaborationTaskException("The ICollateralTask to be updated is null !!!");
		}
		ICollateralTaskTrxValue trxValue = formulateTrxValue(anITrxContext, anICollateralTaskTrxValue,
				anICollateralTask);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_COLLATERAL_TASK);
		return operate(trxValue, param);
	}

	/**
	 * Maker create the collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTask of ICCTask type
	 * @return ICCTaskTrxValue - the generates CC Task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue makerCreateCollaborationTask(ITrxContext anITrxContext, ICCTask anICCTask)
			throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The ITrxContext is null!!!");
		}
		if (anICCTask == null) {
			throw new CollaborationTaskException("The ICCTask to be updated is null !!!");
		}
		ICCTaskTrxValue trxValue = formulateTrxValue(anITrxContext, anICCTask);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_CC_TASK);
		return operate(trxValue, param);
	}

	/**
	 * Maker update the collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTaskTrxValue of ICCTaskTrxValue type
	 * @param anICCTask of ICCTask type
	 * @return ICCTaskTrxValue - the generates CC Task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue makerUpdateCollaborationTask(ITrxContext anITrxContext, ICCTaskTrxValue anICCTaskTrxValue,
			ICCTask anICCTask) throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The ITrxContext is null!!!");
		}
		if (anICCTask == null) {
			throw new CollaborationTaskException("The ICCTask to be updated is null !!!");
		}
		ICCTaskTrxValue trxValue = formulateTrxValue(anITrxContext, anICCTaskTrxValue, anICCTask);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CC_TASK);
		return operate(trxValue, param);
	}

	public ICCTaskTrxValue makerRejectCollaborationTask(ITrxContext anITrxContext, ICCTaskTrxValue anICCTaskTrxValue,
			ICCTask anICCTask) throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The ITrxContext is null!!!");
		}
		if (anICCTask == null) {
			throw new CollaborationTaskException("The ICCTask to be rejected is null !!!");
		}
		ICCTaskTrxValue trxValue = formulateTrxValue(anITrxContext, anICCTaskTrxValue, anICCTask);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_REJECT_CC_TASK);
		return operate(trxValue, param);

	}

	public ICollateralTaskTrxValue makerRejectCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue, ICollateralTask anICollateralTask)
			throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The ITrxContext is null!!!");
		}
		if (anICollateralTask == null) {
			throw new CollaborationTaskException("The ICollateralTask to be updated is null !!!");
		}
		ICollateralTaskTrxValue trxValue = formulateTrxValue(anITrxContext, anICollateralTaskTrxValue,
				anICollateralTask);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_REJECT_COLLATERAL_TASK);
		return operate(trxValue, param);
	}

	/**
	 * Checker approve the collaboration Task
	 * @param anITrxContext of ITrxContext type
	 * @return ICollateralTaskTrxValue - the generated Collateral Task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue checkerApproveCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue) throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The ITrxContext is null!!!");
		}
		if (anICollateralTaskTrxValue == null) {
			throw new CollaborationTaskException("The ICollateralTaskTrxValue to be updated is null!!!");
		}
		anICollateralTaskTrxValue = formulateTrxValue(anITrxContext, anICollateralTaskTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_COLLATERAL_TASK);
		return operate(anICollateralTaskTrxValue, param);
	}

	/**
	 * Checker approve the collaboration Task
	 * @param anITrxContext of ITrxContext type
	 * @return ICCTaskTrxValue - the generated CC Task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue checkerApproveCollaborationTask(ITrxContext anITrxContext, ICCTaskTrxValue anICCTaskTrxValue)
			throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The ITrxContext is null!!!");
		}
		if (anICCTaskTrxValue == null) {
			throw new CollaborationTaskException("The ICCTaskTrxValue to be updated is null!!!");
		}
		anICCTaskTrxValue = formulateTrxValue(anITrxContext, anICCTaskTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CC_TASK);
		return operate(anICCTaskTrxValue, param);
	}

	/**
	 * Checker reject the collaboration Task
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTaskTrxValue of ICollateralTaskTrxValue type
	 * @return ICollateralTaskTrxValue - the Collateral Task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue checkerRejectCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue) throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The ITrxContext is null!!!");
		}
		if (anICollateralTaskTrxValue == null) {
			throw new CollaborationTaskException("The ICollateralTaskTrxValue to be updated is null!!!");
		}
		anICollateralTaskTrxValue = formulateTrxValue(anITrxContext, anICollateralTaskTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_COLLATERAL_TASK);
		return operate(anICollateralTaskTrxValue, param);
	}

	/**
	 * Checker reject the collaboration Task
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTaskTrxValue of ICCTaskTrxValue type
	 * @return ICCTaskTrxValue - the CC Task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue checkerRejectCollaborationTask(ITrxContext anITrxContext, ICCTaskTrxValue anICCTaskTrxValue)
			throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The ITrxContext is null!!!");
		}
		if (anICCTaskTrxValue == null) {
			throw new CollaborationTaskException("The ICCTaskTrxValue to be updated is null!!!");
		}
		anICCTaskTrxValue = formulateTrxValue(anITrxContext, anICCTaskTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CC_TASK);
		return operate(anICCTaskTrxValue, param);
	}

	/**
	 * Maker edit the rejected collaboration Task
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTaskTrxValue of ICollateralTaskTrxValue
	 * @param anICollateralTask of ICollateralTask
	 * @return ICollateralTaskTrxValue - the Collateral task trx
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue makerEditRejectedCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue, ICollateralTask anICollateralTask)
			throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The ITrxContext is null!!!");
		}
		if (anICollateralTaskTrxValue == null) {
			throw new CollaborationTaskException("The ICollateralTaskTrxValue to be updated is null!!!");
		}
		if (anICollateralTask == null) {
			throw new CollaborationTaskException("The ICollateralTask to be updated is null !!!");
		}
		anICollateralTaskTrxValue = formulateTrxValue(anITrxContext, anICollateralTaskTrxValue, anICollateralTask);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_COLLATERAL_TASK);
		return operate(anICollateralTaskTrxValue, param);
	}

	/**
	 * Maker edit the rejected collaboration Task
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTaskTrxValue of ICCTaskTrxValue
	 * @param anICCTask of ICCTask
	 * @return ICCTaskTrxValue - the CC task trx
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue makerEditRejectedCollaborationTask(ITrxContext anITrxContext,
			ICCTaskTrxValue anICCTaskTrxValue, ICCTask anICCTask) throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The ITrxContext is null!!!");
		}
		if (anICCTaskTrxValue == null) {
			throw new CollaborationTaskException("The ICCTaskTrxValue to be updated is null!!!");
		}
		if (anICCTask == null) {
			throw new CollaborationTaskException("The ICCTask to be updated is null !!!");
		}
		anICCTaskTrxValue = formulateTrxValue(anITrxContext, anICCTaskTrxValue, anICCTask);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CC_TASK);
		return operate(anICCTaskTrxValue, param);
	}

	/**
	 * Make close the rejected collaboration Task
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTaskTrxValue of ICollateralTaskTrxValue type
	 * @return ICollateralTaskTrxValue - the Collateral task trx
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue makerCloseRejectedCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue) throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The ITrxContext is null!!!");
		}
		if (anICollateralTaskTrxValue == null) {
			throw new CollaborationTaskException("The ICollateralTaskTrxValue to be updated is null!!!");
		}
		anICollateralTaskTrxValue = formulateTrxValue(anITrxContext, anICollateralTaskTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_COLLATERAL_TASK);
		return operate(anICollateralTaskTrxValue, param);
	}

	/**
	 * Make close the rejected collaboration Task
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTaskTrxValue of ICCTaskTrxValue type
	 * @return ICCTaskTrxValue - the CC task trx
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue makerCloseRejectedCollaborationTask(ITrxContext anITrxContext,
			ICCTaskTrxValue anICCTaskTrxValue) throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The ITrxContext is null!!!");
		}
		if (anICCTaskTrxValue == null) {
			throw new CollaborationTaskException("The ICCTaskTrxValue to be updated is null!!!");
		}
		anICCTaskTrxValue = formulateTrxValue(anITrxContext, anICCTaskTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CC_TASK);
		return operate(anICCTaskTrxValue, param);
	}

	/**
	 * System close the collateral task for a collateral
	 * @param anITrxContext of ITrxContext type
	 * @param aCollateralID of long type
	 * @throws CollaborationTaskException
	 */
	public void systemCloseCollateralCollaborationTaskTrx(ITrxContext anITrxContext, long aCollateralID)
			throws CollaborationTaskException {
		CollateralTaskSearchCriteria criteria = new CollateralTaskSearchCriteria();
		criteria.setCollateralID(aCollateralID);
		String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_PENDING_UPDATE,
				ICMSConstant.STATE_ACTIVE, ICMSConstant.STATE_REJECTED };
		criteria.setTrxStatusList(statusList);
		CollateralTaskSearchResult[] resultList = getCollateralTask(criteria);
		if ((resultList != null) && (resultList.length > 0)) {
			for (int ii = 0; ii < resultList.length; ii++) {
				String trxID = resultList[ii].getTrxID();
				ICollateralTaskTrxValue trxValue = getCollateralTaskByTrxID(trxID);
				systemCloseCollaborationTask(anITrxContext, trxValue);
			}
		}
	}

	/**
	 * System close a collaboration task trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTaskTrxValue of ICollateralTaskTrxValue type
	 * @return ICollateralTaskTrxValue - the interface representing the
	 *         checklist trx obj
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue systemCloseCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue) throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The anITrxContext to be created is null!!!");
		}
		if (anICollateralTaskTrxValue == null) {
			throw new CollaborationTaskException("The ICollateralTaskTrxValue to be created is null!!!");
		}
		anICollateralTaskTrxValue = formulateTrxValue(anITrxContext, anICollateralTaskTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_COLLATERAL_TASK);
		return operate(anICollateralTaskTrxValue, param);
	}

	/**
	 * System close a collaboration task trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTaskTrxValue of ICCTaskTrxValue type
	 * @return ICCTaskTrxValue - the interface representing the checklist trx
	 *         obj
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue systemCloseCollaborationTask(ITrxContext anITrxContext, ICCTaskTrxValue anICCTaskTrxValue)
			throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The anITrxContext to be created is null!!!");
		}
		if (anICCTaskTrxValue == null) {
			throw new CollaborationTaskException("The ICCTaskTrxValue to be created is null!!!");
		}
		anICCTaskTrxValue = formulateTrxValue(anITrxContext, anICCTaskTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_CC_TASK);
		return operate(anICCTaskTrxValue, param);
	}

	/**
	 * Get the collateral task trx value using the trx ID
	 * @param aTrxID of String type
	 * @return ICollateralTaskTrxValue - the collateral task trx value
	 * @throws CollaborationTaskException
	 */
	public ICollateralTaskTrxValue getCollateralTaskByTrxID(String aTrxID) throws CollaborationTaskException {
		if (aTrxID == null) {
			throw new CollaborationTaskException("The TrxID is null !!!");
		}
		ICollateralTaskTrxValue trxValue = new OBCollateralTaskTrxValue();
		trxValue.setTransactionID(aTrxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_COLLATERAL_TASK);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_COLLATERAL_TASK);
		return operate(trxValue, param);
	}

	/**
	 * Get the CC task trx value using the trx ID
	 * @param aTrxID of String type
	 * @return ICCTaskTrxValue - the CC task trx value
	 * @throws CollaborationTaskException
	 */
	public ICCTaskTrxValue getCCTaskByTrxID(String aTrxID) throws CollaborationTaskException {
		if (aTrxID == null) {
			throw new CollaborationTaskException("The TrxID is null !!!");
		}
		ICCTaskTrxValue trxValue = new OBCCTaskTrxValue();
		trxValue.setTransactionID(aTrxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_CC_TASK);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CC_TASK);
		return operate(trxValue, param);
	}

	/**
	 * System update the CC Collaboration task transaction
	 * @param aLimitProfileID of long type
	 * @param aCustomerCategory of String type
	 * @param aCustomerID of long type
	 * @throws CollaborationTaskException on errors
	 */
	public void systemUpdateCCCollaborationTask(long aLimitProfileID, String aCustomerCategory, long aCustomerID)
			throws CollaborationTaskException {
		CCTaskSearchResult[] resultList = getCCTask(aLimitProfileID, aCustomerCategory, aCustomerID);
		if ((resultList != null) && (resultList.length > 0)) {
			for (int ii = 0; ii < resultList.length; ii++) {
				String trxID = resultList[ii].getTrxID();
				ICCTaskTrxValue trxValue = getCCTaskByTrxID(trxID);
				if (trxValue.getCCTask() == null) {
					systemCloseCollaborationTask(trxValue.getTrxContext(), trxValue);
				}
				else {
					systemUpdateCollaborationTask(trxValue.getTrxContext(), trxValue);
				}
			}
		}
	}

	/**
	 * System update the Collateral Collaboration Task transaction
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @throws CollaborationTaskException on errors
	 */
	public void systemUpdateCollateralCollaborationTask(long aLimitProfileID, long aCollateralID)
			throws CollaborationTaskException {
		CollateralTaskSearchResult[] resultList = getCollateralTask(aLimitProfileID, aCollateralID);
		if ((resultList != null) && (resultList.length > 0)) {
			for (int ii = 0; ii < resultList.length; ii++) {
				String trxID = resultList[ii].getTrxID();
				ICollateralTaskTrxValue trxValue = getCollateralTaskByTrxID(trxID);
				systemUpdateCollaborationTask(trxValue.getTrxContext(), trxValue);
			}
		}
	}

	/**
	 * System update the Collateral Collaboration Task transaction
	 * @param aCollateralID of long type
	 * @throws CollaborationTaskException on errors
	 */
	public void systemUpdateCollateralCollaborationTask(long aCollateralID) throws CollaborationTaskException {
		CollateralTaskSearchResult[] resultList = getCollateralTask(aCollateralID);
		if ((resultList != null) && (resultList.length > 0)) {
			for (int ii = 0; ii < resultList.length; ii++) {
				String trxID = resultList[ii].getTrxID();
				ICollateralTaskTrxValue trxValue = getCollateralTaskByTrxID(trxID);
				systemUpdateCollaborationTask(trxValue.getTrxContext(), trxValue);
			}
		}
	}

	/**
	 * System update a cc collaboration task trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTaskTrxValue of ICCTaskTrxValue type
	 * @return ICCTaskTrxValue - the interface representing the cc task trx obj
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue systemUpdateCollaborationTask(ITrxContext anITrxContext, ICCTaskTrxValue anICCTaskTrxValue)
			throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The anITrxContext to be created is null!!!");
		}
		if (anICCTaskTrxValue == null) {
			throw new CollaborationTaskException("The anICCTaskTrxValue to be created is null!!!");
		}
		anICCTaskTrxValue = formulateTrxValue(anITrxContext, anICCTaskTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_UPDATE_CC_TASK);
		return operate(anICCTaskTrxValue, param);
	}

	public ICollateralTaskTrxValue systemUpdateCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue) throws CollaborationTaskException {
		if (anITrxContext == null) {
			throw new CollaborationTaskException("The anITrxContext to be created is null!!!");
		}
		if (anICollateralTaskTrxValue == null) {
			throw new CollaborationTaskException("The anICollateralTaskTrxValue to be created is null!!!");
		}
		anICollateralTaskTrxValue = formulateTrxValue(anITrxContext, anICollateralTaskTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_UPDATE_COLLATERAL_TASK);
		return operate(anICollateralTaskTrxValue, param);
	}

	private CCTaskSearchResult[] getCCTask(long aLimitProfileID, String aCustomerCategory, long aCustomerID)
			throws CollaborationTaskException {
		CCTaskSearchCriteria criteria = new CCTaskSearchCriteria();
		criteria.setLimitProfileID(aLimitProfileID);
		criteria.setCustomerCategory(aCustomerCategory);
		criteria.setCustomerID(aCustomerID);
		String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_PENDING_UPDATE,
				ICMSConstant.STATE_ACTIVE, ICMSConstant.STATE_REJECTED };
		criteria.setTrxStatusList(statusList);
		return getCCTask(criteria);
	}

	/**
	 * Get the list of collateral task for a collateral
	 * @param aCollateralID of long type
	 * @return CollateralTaskSearchResult[] - the list of collateral task for a
	 *         collateral
	 * @throws CollaborationTaskException on errors
	 */
	private CollateralTaskSearchResult[] getCollateralTask(long aCollateralID) throws CollaborationTaskException {
		return getCollateralTask(ICMSConstant.LONG_MIN_VALUE, aCollateralID);
	}

	/**
	 * Get the list of collateral task for a limit profile and a collateral
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @return CollateralTaskSearchResult[] - the list of collateral task for a
	 *         collateral
	 * @throws CollaborationTaskException on errors
	 */
	private CollateralTaskSearchResult[] getCollateralTask(long aLimitProfileID, long aCollateralID)
			throws CollaborationTaskException {
		CollateralTaskSearchCriteria criteria = new CollateralTaskSearchCriteria();
		criteria.setLimitProfileID(aLimitProfileID);
		criteria.setCollateralID(aCollateralID);
		String[] trxStatusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_PENDING_UPDATE,
				ICMSConstant.STATE_REJECTED };
		criteria.setTrxStatusList(trxStatusList);
		return getCollateralTask(criteria);
	}

	/**
	 * Task creation is only required if the limit profile booking location
	 * country is not equal to the collateral location
	 * @param anILimitProfile of ILimitProfile
	 * @param aCollateralLocation of String type
	 * @return String - the status indicator
	 */
	private String getAllowCreateTaskInd(IContext anIContext, ILimitProfile anILimitProfile, long aCollateralID,
			String aCollateralLocation) throws CollaborationTaskException {
		String bkgLocationCtry = anILimitProfile.getOriginatingLocation().getCountryCode();
		if ((aCollateralLocation == null) || (aCollateralLocation.trim().length() == 0)) {
			return ICMSConstant.TASK_NOT_REQUIRED;
		}
		if (aCollateralLocation.equals(bkgLocationCtry)) {
			return ICMSConstant.TASK_NOT_REQUIRED;
		}
		CollateralTaskSearchResult[] resultList = getCollateralTask(anILimitProfile.getLimitProfileID(), aCollateralID,
				aCollateralLocation);
		// if (isCollateralTaskCreated(anILimitProfile, aCollateralID,
		// aCollateralLocation))
		if (isCollateralTaskCreated(resultList)) {
			if (isPendingTrx(resultList)) {
				if (canViewTask(anIContext, resultList)) {
					return ICMSConstant.TASK_VIEW_ONLY;
				}
			}
			return ICMSConstant.TASK_ALREADY_CREATED;
		}
		return ICMSConstant.TASK_REQUIRED;
	}

	private String getAllowCreateTaskInd(IContext anIContext, ILimitProfile anILimitProfile,
			CCCheckListSummary aCCCheckListSummary) throws CollaborationTaskException {
		String bkgLocationCtry = anILimitProfile.getOriginatingLocation().getCountryCode();
		// String bkgLocationOrgCode =
		// anILimitProfile.getOriginatingLocation().getOrganisationCode();

		String domicileCtry = aCCCheckListSummary.getDomicileCtry();
		if ((domicileCtry == null) || (domicileCtry.trim().length() == 0)) {
			return ICMSConstant.TASK_NOT_REQUIRED;
		}
		if (domicileCtry.equals(bkgLocationCtry)) {
			return ICMSConstant.TASK_NOT_REQUIRED;
		}

		CCTaskSearchResult[] resultList = getCCTask(anILimitProfile.getLimitProfileID(), aCCCheckListSummary
				.getCustCategory(), aCCCheckListSummary.getSubProfileID());
		if (isCCTaskCreated(resultList))
		// if (isCCTaskCreated(anILimitProfile,
		// aCCCheckListSummary.getCustCategory(),
		// aCCCheckListSummary.getSubProfileID(), domicileCtry))
		{
			// populateOrgCode(anILimitProfile, aCCCheckListSummary);
			if (isPendingTrx(resultList)) {
				if (canViewTask(anIContext, resultList)) {
					return ICMSConstant.TASK_VIEW_ONLY;
				}
			}

			return ICMSConstant.TASK_ALREADY_CREATED;
		}
		return ICMSConstant.TASK_REQUIRED;
	}

	private String getAllowCreateTaskInd(IContext anIContext, CCCheckListSummary aCCCheckListSummary)
			throws CollaborationTaskException {
		// String bkgLocationCtry =
		// anICMSCustomer.getOriginatingLocation().getCountryCode();
		String domicileCtry = aCCCheckListSummary.getDomicileCtry();

		if ((domicileCtry == null) || (domicileCtry.trim().length() == 0)) {
			return ICMSConstant.TASK_NOT_REQUIRED;
		}
		/*
		 * if (domicileCtry.equals(bkgLocationCtry)) { if
		 * (aCCCheckListSummary.getOrgCode() == null) { return
		 * ICMSConstant.TASK_NOT_REQUIRED; } }
		 */
		ILimitProfile dummyProfile = new OBLimitProfile();
		CCTaskSearchResult[] resultList = getCCTask(dummyProfile.getLimitProfileID(), aCCCheckListSummary
				.getCustCategory(), aCCCheckListSummary.getSubProfileID());
		if (isCCTaskCreated(resultList))
		// if (isCCTaskCreated(dummyProfile,
		// aCCCheckListSummary.getCustCategory(),
		// aCCCheckListSummary.getSubProfileID(), domicileCtry))
		{
			// populateOrgCode(dummyProfile, aCCCheckListSummary);
			if (isPendingTrx(resultList)) {
				if (canViewTask(anIContext, resultList)) {
					return ICMSConstant.TASK_VIEW_ONLY;
				}
			}
			return ICMSConstant.TASK_ALREADY_CREATED;
		}
		return ICMSConstant.TASK_REQUIRED;
	}

	/*
	 * private void populateOrgCode(ILimitProfile anILimitProfile,
	 * CCCheckListSummary aCCCheckListSummary) throws CollaborationTaskException
	 * { ICCTaskTrxValue trx =
	 * getCCTaskTrxValue(anILimitProfile.getLimitProfileID(),
	 * aCCCheckListSummary.getCustCategory(),
	 * aCCCheckListSummary.getSubProfileID(),
	 * aCCCheckListSummary.getDomicileCtry()); if (trx.getCCTask() != null) {
	 * ICCTask task = trx.getCCTask();
	 * aCCCheckListSummary.setOrgCode(task.getOrgCode()); }
	 * 
	 * }
	 */

	private boolean isPendingTrx(CollateralTaskSearchResult[] resultList) {
		if (!ICMSConstant.STATE_ACTIVE.equals(resultList[0].getTrxStatus())) {
			return true;
		}
		return false;
	}

	private boolean isPendingTrx(CCTaskSearchResult[] resultList) {
		if (!ICMSConstant.STATE_ACTIVE.equals(resultList[0].getTrxStatus())) {
			return true;
		}
		return false;
	}

	/**
	 * Check if the collateral collaboration is created or not
	 * @param resultList of CollateralTaskSearchResult[]
	 * @return boolean - true if it is created and false otherwise
	 */
	private boolean isCollateralTaskCreated(CollateralTaskSearchResult[] resultList) throws CollaborationTaskException {
		ICollateralTaskTrxValue trx = getCollateralTaskTrxValue(resultList);
		if (trx == null) {
			return false;
		}
		else {
			return trx.getReferenceID() != null;
		}
	}

	/**
	 * Check if the collateral collaboration is created or not
	 * @param anILimitProfile of ILimitProfile type
	 * @param aCollateralID of long type
	 * @param aCollateralLocation of String type
	 * @return boolean - true if it is created and false otherwise
	 */
	/*
	 * private boolean isCollateralTaskCreated(ILimitProfile anILimitProfile,
	 * long aCollateralID, String aCollateralLocation) throws
	 * CollaborationTaskException { ICollateralTaskTrxValue trx =
	 * getCollateralTaskTrxValue(anILimitProfile.getLimitProfileID(),
	 * aCollateralID, aCollateralLocation); if (trx == null) { return false; }
	 * else { return trx.getReferenceID() != null; } }
	 */

	private boolean isCCTaskCreated(CCTaskSearchResult[] resultList) throws CollaborationTaskException {
		ICCTaskTrxValue trx = getCCTaskTrxValue(resultList);
		if (trx == null) {
			return false;
		}
		else {
			return trx.getReferenceID() != null;
		}
	}

	/*
	 * private boolean isCCTaskCreated(ILimitProfile anILimitProfile, String
	 * aCustomerCategory, long aCustomerID, String aDomicileCtry) throws
	 * CollaborationTaskException { ICCTaskTrxValue trx =
	 * getCCTaskTrxValue(anILimitProfile.getLimitProfileID(), aCustomerCategory,
	 * aCustomerID, aDomicileCtry); if (trx == null) { return false; } else {
	 * return trx.getReferenceID() != null; } }
	 */

	/**
	 * To allow collaboration task only for in-country CPC users
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile
	 * @return boolean - true if allowed and false otherwise
	 */
	private boolean canCreateTask(IContext anIContext, ILimitProfile anILimitProfile) {
		ITeam team = anIContext.getTeam();
		String[] countryList = team.getCountryCodes();
		if (countryList == null) {
			return false;
		}
		String bkgLocationCtry = anILimitProfile.getOriginatingLocation().getCountryCode();
		for (int ii = 0; ii < countryList.length; ii++) {
			if (countryList[ii].equals(bkgLocationCtry)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * To allow collaboration task only for in-country CPC users
	 * @param anIContext of IContext type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return boolean - true if allowed and false otherwise
	 */
	private boolean canCreateTask(IContext anIContext, ICMSCustomer anICMSCustomer) {
		ITeam team = anIContext.getTeam();
		String[] countryList = team.getCountryCodes();
		if (countryList == null) {
			return false;
		}

		if (anICMSCustomer.getOriginatingLocation() != null) {
			String domicileCtry = anICMSCustomer.getOriginatingLocation().getCountryCode();
			for (int ii = 0; ii < countryList.length; ii++) {
				if (countryList[ii].equals(domicileCtry)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * To allow collaboration task to view for in/out country if transaction
	 * pending at out/in country If the transaction is pending and updated by in
	 * country, in country cannot view the trx
	 * @param anIContext of IContext type
	 * @param resultList of CollateralTaskSearchResult type
	 * @return boolean - true if allowed and false otherwise
	 */
	private boolean canViewTask(IContext anIContext, CollateralTaskSearchResult[] resultList) {
		ITeam team = anIContext.getTeam();
		String[] countryList = team.getCountryCodes();
		if (countryList == null) {
			return false;
		}

		String trxCountry = resultList[0].getTrxOriginCountry();
		for (int i = 0; i < countryList.length; i++) {
			if (countryList[i].equals(trxCountry)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * To allow collaboration task to view for in/out country if transaction
	 * pending at out/in country If the transaction is pending and updated by in
	 * country, in country cannot view the trx
	 * @param anIContext of IContext type
	 * @param resultList of CCTaskSearchResult type
	 * @return boolean - true if allowed and false otherwise
	 */
	private boolean canViewTask(IContext anIContext, CCTaskSearchResult[] resultList) {
		ITeam team = anIContext.getTeam();
		String[] countryList = team.getCountryCodes();
		if (countryList == null) {
			return false;
		}

		String trxCountry = resultList[0].getTrxOriginCountry();
		for (int i = 0; i < countryList.length; i++) {
			if (countryList[i].equals(trxCountry)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Formulate the collateral task Trx Object
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTask of ICollateralTask type
	 * @return ICollateralTaskTrxValue - the collateral task trx interface
	 *         formulated
	 */
	private ICollateralTaskTrxValue formulateTrxValue(ITrxContext anITrxContext, ICollateralTask anICollateralTask) {
		return formulateTrxValue(anITrxContext, null, anICollateralTask);
	}

	/**
	 * Formulate the CC task Trx Object
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTask of ICCTask type
	 * @return ICCTaskTrxValue - the CC task trx interface formulated
	 */
	private ICCTaskTrxValue formulateTrxValue(ITrxContext anITrxContext, ICCTask anICCTask) {
		return formulateTrxValue(anITrxContext, null, anICCTask);
	}

	/**
	 * Formulate the collateral task Trx Object
	 * @param anITrxContext of ITrxContext type
	 * @param anICMSTrxValue of ICMSTrxValue type
	 * @param anICollateralTask of ICollateralTask type
	 * @return ICollateralTaskTrxValue - the collateral task trx interface
	 *         formulated
	 */
	private ICollateralTaskTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			ICollateralTask anICollateralTask) {
		ICollateralTaskTrxValue colTaskTrxValue;
		if (anICMSTrxValue != null) {
			colTaskTrxValue = new OBCollateralTaskTrxValue(anICMSTrxValue);
		}
		else {
			colTaskTrxValue = new OBCollateralTaskTrxValue();
		}
		colTaskTrxValue = formulateTrxValue(anITrxContext, colTaskTrxValue);
		colTaskTrxValue.setStagingCollateralTask(anICollateralTask);
		return colTaskTrxValue;
	}

	/**
	 * Formulate the CC task Trx Object
	 * @param anITrxContext of ITrxContext type
	 * @param anICMSTrxValue of ICMSTrxValue type
	 * @param anICCTask of ICCTask type
	 * @return ICCTaskTrxValue - the CC task trx interface formulated
	 */
	private ICCTaskTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, ICCTask anICCTask) {
		ICCTaskTrxValue ccTaskTrxValue;
		if (anICMSTrxValue != null) {
			ccTaskTrxValue = new OBCCTaskTrxValue(anICMSTrxValue);
		}
		else {
			ccTaskTrxValue = new OBCCTaskTrxValue();
		}
		ccTaskTrxValue = formulateTrxValue(anITrxContext, ccTaskTrxValue);
		ccTaskTrxValue.setStagingCCTask(anICCTask);
		return ccTaskTrxValue;
	}

	/**
	 * Formulate the collateral task trx object
	 * @param anITrxContext - ITrxContext
	 * @param anICollateralTaskTrxValue - ICollateralTaskTrxValue
	 * @return ICollateralTaskTrxValue - the collateral task trx interface
	 *         formulated
	 */
	private ICollateralTaskTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue) {
		anICollateralTaskTrxValue.setTrxContext(anITrxContext);
		anICollateralTaskTrxValue.setTransactionType(ICMSConstant.INSTANCE_COLLATERAL_TASK);
		return anICollateralTaskTrxValue;
	}

	/**
	 * Formulate the CC task trx object
	 * @param anITrxContext - ITrxContext
	 * @param anICCTaskTrxValue - ICCTaskTrxValue
	 * @return ICCTaskTrxValue - the CC task trx interface formulated
	 */
	private ICCTaskTrxValue formulateTrxValue(ITrxContext anITrxContext, ICCTaskTrxValue anICCTaskTrxValue) {
		anICCTaskTrxValue.setTrxContext(anITrxContext);
		anICCTaskTrxValue.setTransactionType(ICMSConstant.INSTANCE_CC_TASK);
		return anICCTaskTrxValue;
	}

	private ICollateralTaskTrxValue operate(ICollateralTaskTrxValue anICollateralTaskTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws CollaborationTaskException {
		ICMSTrxResult result = operateForResult(anICollateralTaskTrxValue, anOBCMSTrxParameter);
		return (ICollateralTaskTrxValue) result.getTrxValue();
	}

	private ICCTaskTrxValue operate(ICCTaskTrxValue anICCTaskTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CollaborationTaskException {
		ICMSTrxResult result = operateForResult(anICCTaskTrxValue, anOBCMSTrxParameter);
		return (ICCTaskTrxValue) result.getTrxValue();
	}

	private ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CollaborationTaskException {
		try {
			ITrxController controller = (new CollaborationTaskTrxControllerFactory()).getController(anICMSTrxValue,
					anOBCMSTrxParameter);
			if (controller == null) {
				throw new CollaborationTaskException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			rollback();
			throw new CollaborationTaskException(e);
		}
		catch (Exception ex) {
			rollback();
			throw new CollaborationTaskException(ex.toString());
		}
	}

	protected abstract CollateralTaskSearchResult[] getCollateralTask(CollateralTaskSearchCriteria aCriteria)
			throws CollaborationTaskException;

	public abstract CCTaskSearchResult[] getCCTask(CCTaskSearchCriteria aCriteria) throws CollaborationTaskException;

	protected abstract int getNoOfCollateralTask(CollateralTaskSearchCriteria aCriteria)
			throws CollaborationTaskException;

	protected abstract int getNoOfCCTask(CCTaskSearchCriteria aCriteria) throws CollaborationTaskException;

	protected abstract void rollback();
}
