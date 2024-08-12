package com.integrosys.cms.app.commoncode.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.commoncode.bus.CommonCodeTypeException;
import com.integrosys.cms.app.commoncode.bus.ICommonCodeType;
import com.integrosys.cms.app.commoncode.bus.SBCommonCodeTypeBusManager;
import com.integrosys.cms.app.commoncode.bus.SBCommonCodeTypeBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public abstract class AbstractCommonCodeTypeTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
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
	 * This method set the primary key from the original to the copied ddn
	 * objects. It is required for the case of updating staging from actual and
	 * vice versa as there is a need to perform a deep clone of the object and
	 * set the required attribute in the object to the original one so that a
	 * proper update can be done. @ param anOrginal of ICommonCodeType type
	 * @param aCopy of ICommonCodeType type
	 * @return ICommonCodeType - the copied object with required attributes from
	 *         the original ddn
	 * @throws TrxOperationException on errors
	 */
	protected ICommonCodeType mergeCommonCodeType(ICommonCodeType anOriginal, ICommonCodeType aCopy)
			throws TrxOperationException {
		aCopy.setCommonCategoryId(anOriginal.getCommonCategoryId());
		aCopy.setCommonCategoryCode(anOriginal.getCommonCategoryCode());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	/**
	 * Create the staging document item doc
	 * @param anICommonCodeTypeTrxValue of ICommonCodeTypeTrxValue type
	 * @return ICommonCodeTypeTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws TrxOperationException if errors
	 */
	protected ICommonCodeTypeTrxValue createStagingCommonCodeType(ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue)
			throws TrxOperationException {
		try {
			ICommonCodeType iCommonCodeType = getSBStagingCommonCodeTypeBusManager().createCommonCodeType(
					anICommonCodeTypeTrxValue.getStagingCommonCodeType());
			anICommonCodeTypeTrxValue.setStagingCommonCodeType(iCommonCodeType);
			anICommonCodeTypeTrxValue.setStagingReferenceID(String.valueOf(iCommonCodeType.getCommonCategoryId()));
			return anICommonCodeTypeTrxValue;
		}
		catch (CommonCodeTypeException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Update a ddn transaction @ param anITrxValue of ITrxValue type
	 * @return ICommonCodeTypeTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected ICommonCodeTypeTrxValue updateCommonCodeTypeTransaction(ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue)
			throws TrxOperationException {
		try {
			anICommonCodeTypeTrxValue = prepareTrxValue(anICommonCodeTypeTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(anICommonCodeTypeTrxValue);
			OBCommonCodeTypeTrxValue newValue = new OBCommonCodeTypeTrxValue(tempValue);
			newValue.setCommonCodeType(anICommonCodeTypeTrxValue.getCommonCodeType());
			newValue.setStagingCommonCodeType(anICommonCodeTypeTrxValue.getStagingCommonCodeType());
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
	 * @return ICommonCodeTypeTrxValue - the document item specific trx value
	 *         object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected ICommonCodeTypeTrxValue getCommonCodeTypeTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (ICommonCodeTypeTrxValue) anITrxValue;
		}
		catch (ClassCastException ex) {
			throw new TrxOperationException("The ITrxValue is not of type OBCommonCodeTypeTrxValue: " + ex.toString());
		}
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anICommonCodeTypeTrxValue - ICommonCodeTypeTrxValue
	 * @return ICommonCodeTypeTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	protected ICommonCodeTypeTrxValue updateActualCommonCodeType(ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue)
			throws TrxOperationException {
		try {
			ICommonCodeType staging = anICommonCodeTypeTrxValue.getStagingCommonCodeType();
			ICommonCodeType actual = anICommonCodeTypeTrxValue.getCommonCodeType();
			if (actual != null) {
				ICommonCodeType updActual = (ICommonCodeType) CommonUtil.deepClone(staging);
				updActual = mergeCommonCodeType(actual, updActual);
				ICommonCodeType actualCommonCodeType = getSBCommonCodeTypeBusManager().update(updActual);
				anICommonCodeTypeTrxValue.setCommonCodeType(actualCommonCodeType);
			}
			return anICommonCodeTypeTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (CommonCodeTypeException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActualCommonCodeType(): " + ex.toString());
		}
	}

	/**
	 * To get the remote handler for the staging CommonCodeType session bean
	 * @return SBCommonCodeTypeBusManager - the remote handler for the staging
	 *         ddn session bean
	 */
	protected SBCommonCodeTypeBusManager getSBStagingCommonCodeTypeBusManager() {
		SBCommonCodeTypeBusManager remote = (SBCommonCodeTypeBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_COMMON_CODE_TYPE_BUS_JNDI, SBCommonCodeTypeBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * To get the remote handler for the CommonCodeType session bean
	 * @return SBCommonCodeTypeBusManager - the remote handler for the
	 *         CommonCodeType session bean
	 */
	protected SBCommonCodeTypeBusManager getSBCommonCodeTypeBusManager() {
		SBCommonCodeTypeBusManager remote = (SBCommonCodeTypeBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_COMMON_CODE_TYPE_BUS_JNDI, SBCommonCodeTypeBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * Prepares a trx object
	 */
	protected ICommonCodeTypeTrxValue prepareTrxValue(ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue) {
		if (anICommonCodeTypeTrxValue != null) {
			ICommonCodeType actual = anICommonCodeTypeTrxValue.getCommonCodeType();
			ICommonCodeType staging = anICommonCodeTypeTrxValue.getStagingCommonCodeType();
			if ((actual != null)
					&& (actual.getCommonCategoryId() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anICommonCodeTypeTrxValue.setReferenceID(String.valueOf(actual.getCommonCategoryId()));
			}
			else {
				anICommonCodeTypeTrxValue.setReferenceID(null);
			}
			if ((staging != null)
					&& (staging.getCommonCategoryId() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anICommonCodeTypeTrxValue.setStagingReferenceID(String.valueOf(staging.getCommonCategoryId()));
			}
			else {
				anICommonCodeTypeTrxValue.setStagingReferenceID(null);
			}
			return anICommonCodeTypeTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value of ICommonCodeTypeTrxValue type
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ICommonCodeTypeTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}
}
