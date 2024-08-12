/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/proxy/AbstractDocumentLocationProxyManager.java,v 1.6 2006/09/21 09:45:19 jychong Exp $
 */
package com.integrosys.cms.app.documentlocation.proxy;

//java
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.CCCheckListSummary;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.IContext;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.documentlocation.bus.CCDocumentLocationDAO;
import com.integrosys.cms.app.documentlocation.bus.CCDocumentLocationSearchCriteria;
import com.integrosys.cms.app.documentlocation.bus.CCDocumentLocationSearchResult;
import com.integrosys.cms.app.documentlocation.bus.CCDocumentLocationSummary;
import com.integrosys.cms.app.documentlocation.bus.DocumentLocationException;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;
import com.integrosys.cms.app.documentlocation.trx.DocumentLocationTrxControllerFactory;
import com.integrosys.cms.app.documentlocation.trx.ICCDocumentLocationTrxValue;
import com.integrosys.cms.app.documentlocation.trx.OBCCDocumentLocationTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;

/**
 * This abstract class will contains all the biz related logic that is
 * independent of any technology implementation such as EJB
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/09/21 09:45:19 $ Tag: $Name: $
 */
public abstract class AbstractDocumentLocationProxyManager implements IDocumentLocationProxyManager {
	/**
	 * Check if there is any pending CC document location trx
	 * @param aLimitProfileID of long type
	 * @param aCategory of String type
	 * @param aCustomerID of long type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws DocumentLocationException on errors
	 */
	public boolean hasPendingCCDocumentLocationTrx(long aLimitProfileID, String aCategory, long aCustomerID)
			throws DocumentLocationException {
		CCDocumentLocationSearchCriteria criteria = new CCDocumentLocationSearchCriteria();
		criteria.setLimitProfileID(aLimitProfileID);
		criteria.setDocLocationCategory(aCategory);
		criteria.setCustomerID(aCustomerID);
		String[] trxStatusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_PENDING_UPDATE,
				ICMSConstant.STATE_REJECTED };
		criteria.setTrxStatusList(trxStatusList);
		int count = getNoOfCCDocumentLocation(criteria);
		if (count == 0) {
			return false;
		}
		return true;
	}

	/**
	 * Get the CC summary list
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return CCDocumentLocationSummary[] - the will contain the CC summary
	 *         list
	 * @throws DocumentLocationException
	 */
	public CCDocumentLocationSummary[] getCCSummaryList(IContext anIContext, ILimitProfile anILimitProfile)
			throws DocumentLocationException {
		if (anIContext == null) {
			throw new DocumentLocationException("The IContext is null !!!");
		}
		if (anILimitProfile == null) {
			throw new DocumentLocationException("The ILimitProfile is null !!!");
		}
		try {
			ICheckListProxyManager mgr = CheckListProxyManagerFactory.getCheckListProxyManager();
			CCCheckListSummary[] summaryList = mgr.getCCCheckListSummaryForCollaboration(anIContext, anILimitProfile);
			if ((summaryList == null) || (summaryList.length == 0)) {
				DocumentLocationException exp = new DocumentLocationException();
				exp.setErrorCode(ICMSErrorCodes.CC_DOC_LOC_NOT_REQUIRED);
				throw exp;
			}
			List isViewableList = getIsViewableStatus(summaryList, anILimitProfile.getLimitProfileID());
			CCDocumentLocationSummary[] resultList = new CCDocumentLocationSummary[summaryList.length];
			for (int ii = 0; ii < summaryList.length; ii++) {
				resultList[ii] = new CCDocumentLocationSummary(summaryList[ii]);
				resultList[ii].setIsViewable(isViewableList.contains(String.valueOf(summaryList[ii].getSubProfileID())
						+ resultList[ii].getDocumentLocationCategory()));
			}
			return resultList;
		}
        catch (CheckListTemplateException ex) {
            rollback();
            throw new DocumentLocationException("Exception in getCCSummaryList", ex);
        }
		catch (CheckListException ex) {
			rollback();
			throw new DocumentLocationException("Exception in getCCSummaryList", ex);
		}
	}

	/**
	 * Get the CC summary list for non borrower
	 * @param anIContext of IContext type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return CCDocumentLocationSummary[] - the will contain the CC summary
	 *         list
	 * @throws DocumentLocationException
	 */
	public CCDocumentLocationSummary[] getCCSummaryList(IContext anIContext, ICMSCustomer anICMSCustomer)
			throws DocumentLocationException {
		if (anIContext == null) {
			throw new DocumentLocationException("The IContext is null !!!");
		}

		if (anICMSCustomer == null) {
			throw new DocumentLocationException("The ICMSCustomer is null !!!");
		}
		try {
			ICheckListProxyManager mgr = CheckListProxyManagerFactory.getCheckListProxyManager();
			CCCheckListSummary[] summaryList = mgr.getCCCheckListSummaryListForNonBorrower(anIContext,
					ICMSConstant.LONG_INVALID_VALUE, anICMSCustomer.getCustomerID(), true);
			if ((summaryList == null) || (summaryList.length == 0)) {
				DocumentLocationException exp = new DocumentLocationException();
				exp.setErrorCode(ICMSErrorCodes.CC_DOC_LOC_NOT_REQUIRED);
				throw exp;
			}
			List isViewableList = getIsViewableStatus(summaryList, ICMSConstant.LONG_MIN_VALUE);
			CCDocumentLocationSummary[] resultList = new CCDocumentLocationSummary[summaryList.length];
			for (int ii = 0; ii < summaryList.length; ii++) {
				resultList[ii] = new CCDocumentLocationSummary(summaryList[ii]);
				resultList[ii].setIsViewable(isViewableList.contains(String.valueOf(summaryList[ii].getSubProfileID())
						+ resultList[ii].getDocumentLocationCategory()));
			}
			return resultList;
		}
        catch (CheckListTemplateException ex) {
            rollback();
            throw new DocumentLocationException("Exception in getCCSummaryList", ex);
        }
		catch (CheckListException ex) {
			rollback();
			throw new DocumentLocationException("Exception in getCCSummaryList", ex);
		}
	}

	/**
	 * Get the list of document location which can be view by user
	 * @param summaryList of CCCheckListSummary type
	 * @param limitProfileID of long type
	 * @return List - it will contain the key (subprofile id or pledgor id
	 *         concate with document type)
	 * @throws DocumentLocationException
	 */
	private List getIsViewableStatus(CCCheckListSummary[] summaryList, long limitProfileID)
			throws DocumentLocationException {
		try {
			ArrayList pledgorIDList = new ArrayList();
			ArrayList subProfileIDList = new ArrayList();
			for (int i = 0; i < summaryList.length; i++) {
				if (ICMSConstant.CHECKLIST_PLEDGER.equals(summaryList[i].getCustCategory())) {
					DefaultLogger.debug(this, "<<<<<<< pledgor id: " + summaryList[i].getSubProfileID());
					pledgorIDList.add(new Long(summaryList[i].getSubProfileID()));
				}
				else {
					DefaultLogger.debug(this, "<<<<<<< sub profile id: " + summaryList[i].getSubProfileID());
					subProfileIDList.add(new Long(summaryList[i].getSubProfileID()));
				}
			}
			return new CCDocumentLocationDAO().getIsViewableStatus(limitProfileID, pledgorIDList, subProfileIDList);
		}
		catch (Exception e) {
			throw new DocumentLocationException("Exception in getIsViewableStatus:", e);
		}
	}

	/**
	 * Get the cc document location trx value using the limitprofile id,
	 * category and customer id
	 * @param aLimitProfileID of long type
	 * @param aCategory of String type
	 * @param aCustomerID of long type
	 * @return ICCDocumentLocationTrxValue - the trx value of the CC document
	 *         location
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue getCCDocumentLocationTrxValue(long aLimitProfileID, String aCategory,
			long aCustomerID) throws DocumentLocationException {
		/*
		 * CCDocumentLocationSearchCriteria criteria = new
		 * CCDocumentLocationSearchCriteria();
		 * criteria.setLimitProfileID(aLimitProfileID);
		 * criteria.setDocLocationCategory(aCategory);
		 * criteria.setCustomerID(aCustomerID); String[] statusList = {
		 * ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_PENDING_UPDATE,
		 * ICMSConstant.STATE_ACTIVE, ICMSConstant.STATE_REJECTED};
		 * criteria.setTrxStatusList(statusList);
		 * CCDocumentLocationSearchResult[] resultList =
		 * getCCDocumentLocation(criteria); if ((resultList == null) ||
		 * (resultList.length == 0)) { return null; } if (resultList.length !=
		 * 1) { throw new
		 * DocumentLocationException("More than 1 records with LimitProfileID "
		 * + aLimitProfileID + " Category " + aCategory + " and CustomerID " +
		 * aCustomerID); }
		 * 
		 * String trxID = resultList[0].getTrxID(); return
		 * getCCDocumentLocationByTrxID(trxID);
		 */

		ICCDocumentLocation[] list = getCCDocumentLocation(aCategory, aLimitProfileID, aCustomerID);
		if ((list == null) || (list.length == 0)) {
			return null;
		}

		long docID = list[0].getDocLocationID();
		return getCCDocumentLocationTrxValue(docID);
	}

	/**
	 * Maker create the document location
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocation of ICCDocumentLocationTrxValue type
	 * @param anICCDocumentLocation of ICCDocumentLocation type
	 * @return ICCDocumentLocationTrxValue - the generates CC document location
	 *         trx value
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue makerCreateDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocation anICCDocumentLocation) throws DocumentLocationException {
		if (anITrxContext == null) {
			throw new DocumentLocationException("The ITrxContext is null!!!");
		}
		if (anICCDocumentLocation == null) {
			throw new DocumentLocationException("The ICCDocumentLocation to be updated is null !!!");
		}
		ICCDocumentLocationTrxValue trxValue = formulateTrxValue(anITrxContext, anICCDocumentLocation);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_CC_DOC_LOC);
		return operate(trxValue, param);
	}

	/**
	 * Maker update the document location
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue type
	 * @param anICCDocumentLocation of ICCDocumentLocation type
	 * @return ICCDocumentLocationTrxValue - the generates CC document location
	 *         trx value
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue makerUpdateDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue, ICCDocumentLocation anICCDocumentLocation)
			throws DocumentLocationException {
		if (anITrxContext == null) {
			throw new DocumentLocationException("The ITrxContext is null!!!");
		}
		if (anICCDocumentLocation == null) {
			throw new DocumentLocationException("The ICCDocumentLocation to be updated is null !!!");
		}
		ICCDocumentLocationTrxValue trxValue = formulateTrxValue(anITrxContext, anICCDocumentLocationTrxValue,
				anICCDocumentLocation);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CC_DOC_LOC);
		return operate(trxValue, param);
	}

	/**
	 * Checker approve the document location
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue type
	 * @return ICCDocumentLocationTrxValue - the generated CC document location
	 *         trx value
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue checkerApproveDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) throws DocumentLocationException {
		if (anITrxContext == null) {
			throw new DocumentLocationException("The ITrxContext is null!!!");
		}
		if (anICCDocumentLocationTrxValue == null) {
			throw new DocumentLocationException("The ICCDocumentLocationTrxValue to be updated is null!!!");
		}
		anICCDocumentLocationTrxValue = formulateTrxValue(anITrxContext, anICCDocumentLocationTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CC_DOC_LOC);
		return operate(anICCDocumentLocationTrxValue, param);
	}

	/**
	 * Checker reject the document location
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue type
	 * @return ICCDocumentLocationTrxValue - the CC document location trx value
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue checkerRejectDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) throws DocumentLocationException {
		if (anITrxContext == null) {
			throw new DocumentLocationException("The ITrxContext is null!!!");
		}
		if (anICCDocumentLocationTrxValue == null) {
			throw new DocumentLocationException("The ICCDocumentLocationTrxValue to be updated is null!!!");
		}
		anICCDocumentLocationTrxValue = formulateTrxValue(anITrxContext, anICCDocumentLocationTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CC_DOC_LOC);
		return operate(anICCDocumentLocationTrxValue, param);
	}

	/**
	 * Maker edit the rejected document location
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue
	 * @param anICCDocumentLocation of ICCDocumentLocation
	 * @return ICCDocumentLocationTrxValue - the CC document location trx
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue makerEditRejectedDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue, ICCDocumentLocation anICCDocumentLocation)
			throws DocumentLocationException {
		if (anITrxContext == null) {
			throw new DocumentLocationException("The ITrxContext is null!!!");
		}
		if (anICCDocumentLocationTrxValue == null) {
			throw new DocumentLocationException("The ICCDocumentLocationTrxValue to be updated is null!!!");
		}
		if (anICCDocumentLocation == null) {
			throw new DocumentLocationException("The ICCDocumentLocation to be updated is null !!!");
		}
		anICCDocumentLocationTrxValue = formulateTrxValue(anITrxContext, anICCDocumentLocationTrxValue,
				anICCDocumentLocation);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CC_DOC_LOC);
		return operate(anICCDocumentLocationTrxValue, param);
	}

	/**
	 * Make close the rejected document location
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue type
	 * @return ICCDocumentLocationTrxValue - the CC document location trx
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocationTrxValue makerCloseRejectedDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) throws DocumentLocationException {
		if (anITrxContext == null) {
			throw new DocumentLocationException("The ITrxContext is null!!!");
		}
		if (anICCDocumentLocationTrxValue == null) {
			throw new DocumentLocationException("The ICCDocumentLocationTrxValue to be updated is null!!!");
		}
		anICCDocumentLocationTrxValue = formulateTrxValue(anITrxContext, anICCDocumentLocationTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CC_DOC_LOC);
		return operate(anICCDocumentLocationTrxValue, param);
	}

	/**
	 * System close a document location trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue type
	 * @return ICCDocumentLocationTrxValue - the interface representing the
	 *         checklist trx obj
	 * @throws CheckListException on errors
	 */
	public ICCDocumentLocationTrxValue systemCloseDocumentLocation(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) throws DocumentLocationException {
		if (anITrxContext == null) {
			throw new DocumentLocationException("The anITrxContext to be created is null!!!");
		}
		if (anICCDocumentLocationTrxValue == null) {
			throw new DocumentLocationException("The ICCDocumentLocationTrxValue to be created is null!!!");
		}
		anICCDocumentLocationTrxValue = formulateTrxValue(anITrxContext, anICCDocumentLocationTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_CC_DOC_LOC);
		return operate(anICCDocumentLocationTrxValue, param);
	}

	/**
	 * Get the CC document location trx value using the trx ID
	 * @param aTrxID of String type
	 * @return ICCDocumentLocationTrxValue - the CC document location trx value
	 * @throws DocumentLocationException
	 */
	public ICCDocumentLocationTrxValue getCCDocumentLocationByTrxID(String aTrxID) throws DocumentLocationException {
		if (aTrxID == null) {
			throw new DocumentLocationException("The TrxID is null !!!");
		}
		ICCDocumentLocationTrxValue trxValue = new OBCCDocumentLocationTrxValue();
		trxValue.setTransactionID(aTrxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_CC_DOC_LOC);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CC_DOC_LOC);
		return operate(trxValue, param);
	}

	/**
	 * Get the CC document location trx value based on the CC document location
	 * ID
	 * @param aDocumentLocationID of long type
	 * @return ICCDocumentLocationTrxValue - the CC document location trx value
	 * @throws DocumentLocationException
	 */
	private ICCDocumentLocationTrxValue getCCDocumentLocationTrxValue(long aDocumentLocationID)
			throws DocumentLocationException {
		if (aDocumentLocationID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new DocumentLocationException("The DocumentLocationID is invalid !!!");
		}
		ICCDocumentLocationTrxValue trxValue = new OBCCDocumentLocationTrxValue();
		trxValue.setReferenceID(String.valueOf(aDocumentLocationID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_CC_DOC_LOC);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CC_DOC_LOC_ID);
		return operate(trxValue, param);
	}

	/**
	 * Formulate the CC document location Trx Object
	 * @param anITrxContext of ITrxContext type
	 * @param anICCDocumentLocation of ICCDocumentLocation type
	 * @return ICCDocumentLocationTrxValue - the CC document location trx
	 *         interface formulated
	 */
	private ICCDocumentLocationTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICCDocumentLocation anICCDocumentLocation) {
		return formulateTrxValue(anITrxContext, null, anICCDocumentLocation);
	}

	/**
	 * Formulate the CC document location Trx Object
	 * @param anITrxContext of ITrxContext type
	 * @param anICMSTrxValue of ICMSTrxValue type
	 * @param anICCDocumentLocation of ICCDocumentLocation type
	 * @return ICCDocumentLocationTrxValue - the CC document location trx
	 *         interface formulated
	 */
	private ICCDocumentLocationTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			ICCDocumentLocation anICCDocumentLocation) {
		ICCDocumentLocationTrxValue ccDocumentLocationTrxValue = null;
		if (anICMSTrxValue != null) {
			ccDocumentLocationTrxValue = new OBCCDocumentLocationTrxValue(anICMSTrxValue);
		}
		else {
			ccDocumentLocationTrxValue = new OBCCDocumentLocationTrxValue();
		}
		ccDocumentLocationTrxValue = formulateTrxValue(anITrxContext,
				(ICCDocumentLocationTrxValue) ccDocumentLocationTrxValue);
		ccDocumentLocationTrxValue.setStagingCCDocumentLocation(anICCDocumentLocation);
		return ccDocumentLocationTrxValue;
	}

	/**
	 * Formulate the CC document location trx object
	 * @param anITrxContext - ITrxContext
	 * @param anICCDocumentLocationTrxValue - ICCDocumentLocationTrxValue
	 * @return ICCDocumentLocationTrxValue - the CC document location trx
	 *         interface formulated
	 */
	private ICCDocumentLocationTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) {
		anICCDocumentLocationTrxValue.setTrxContext(anITrxContext);
		anICCDocumentLocationTrxValue.setTransactionType(ICMSConstant.INSTANCE_CC_DOC_LOC);
		return anICCDocumentLocationTrxValue;
	}

	private ICCDocumentLocationTrxValue operate(ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws DocumentLocationException {
		ICMSTrxResult result = operateForResult(anICCDocumentLocationTrxValue, anOBCMSTrxParameter);
		return (ICCDocumentLocationTrxValue) result.getTrxValue();
	}

	private ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws DocumentLocationException {
		try {
			ITrxController controller = (new DocumentLocationTrxControllerFactory()).getController(anICMSTrxValue,
					anOBCMSTrxParameter);
			if (controller == null) {
				throw new DocumentLocationException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			rollback();
			throw new DocumentLocationException(e);
		}
		catch (Exception ex) {
			rollback();
			throw new DocumentLocationException(ex.toString());
		}
	}

	public abstract ICCDocumentLocation[] getCCDocumentLocation(String anOwnerType, long aLimitProfileID, long anOwnerID)
			throws DocumentLocationException;

	public abstract CCDocumentLocationSearchResult[] getCCDocumentLocation(CCDocumentLocationSearchCriteria aCriteria)
			throws DocumentLocationException;

	protected abstract int getNoOfCCDocumentLocation(CCDocumentLocationSearchCriteria aCriteria)
			throws DocumentLocationException;

	protected abstract void rollback();
}
