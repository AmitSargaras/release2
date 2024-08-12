/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * AbstractPreDealOperation
 *
 * Created on 12:24:00 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.predeal.bus.IPreDeal;
import com.integrosys.cms.app.predeal.bus.SBPreDealBusManager;
import com.integrosys.cms.app.predeal.bus.SBPreDealBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 26, 2007 Time: 12:24:00 PM
 */
public abstract class AbstractPreDealTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		DefaultLogger.debug(this, "Prefrom process for operation  : " + getOperationName());

		return null;
	}

	protected IPreDeal mergePreDeal(IPreDeal anOriginal, IPreDeal aCopy) throws TrxOperationException {
		aCopy.setFeedId(anOriginal.getFeedId());
		aCopy.setEarMarkId(anOriginal.getEarMarkId());
		// aCopy.setEarMarkGroupId ( anOriginal.getEarMarkGroupId () ) ;
		aCopy.setVersionTime(anOriginal.getVersionTime());

		return aCopy;
	}

	protected IPreDealTrxValue createStagingPreDeal(IPreDealTrxValue value) throws TrxOperationException {
		try {
			IPreDeal predeal = getSBStagingPreDealBusManager().createNewEarMark(value.getStagingPreDeal());

			value.setStagingPreDeal(predeal);
			value.setStagingReferenceID(String.valueOf(predeal.getEarMarkId()));

			return value;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
	}

	protected IPreDealTrxValue updateStagingPreDeal(IPreDealTrxValue value) throws TrxOperationException {
		try {
			IPreDeal predeal = getSBStagingPreDealBusManager().updateEarMark(value.getStagingPreDeal());

			value.setStagingPreDeal(predeal);
			value.setStagingReferenceID(String.valueOf(predeal.getEarMarkId()));

			return value;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
	}

	protected IPreDealTrxValue updateActualPreDeal(IPreDealTrxValue value) throws TrxOperationException {
		try {
			IPreDeal staging = value.getStagingPreDeal();
			IPreDeal actual = value.getPreDeal();

			if (actual != null) {
				IPreDeal updActual = (IPreDeal) CommonUtil.deepClone(staging);

				updActual = mergePreDeal(actual, updActual);

				IPreDeal updated = getSBPreDealBusManager().updateEarMark(updActual);

				value.setPreDeal(updated);
			}

			return value;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
	}

	protected IPreDealTrxValue updatePreDealTransaction(IPreDealTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);

			ICMSTrxValue tempValue = super.updateTransaction(value);
			OBPreDealTrxValue newValue = new OBPreDealTrxValue(tempValue);

			newValue.setPreDeal(value.getPreDeal());
			newValue.setStagingPreDeal(value.getStagingPreDeal());

			return newValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	protected IPreDealTrxValue getPreDealTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IPreDealTrxValue) anITrxValue;
		}
		catch (ClassCastException ex) {
			throw new TrxOperationException("The ITrxValue is not of type OBCommonCodeTypeTrxValue: " + ex.toString());
		}
	}

	protected IPreDealTrxValue prepareTrxValue(IPreDealTrxValue value) {
		if (value != null) {
			IPreDeal actual = value.getPreDeal();
			IPreDeal staging = value.getStagingPreDeal();

			if ((actual != null)
					&& (actual.getEarMarkId().longValue() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				value.setReferenceID(String.valueOf(actual.getEarMarkId()));
			}
			else {
				value.setReferenceID(null);
			}

			if ((staging != null)
					&& (staging.getEarMarkId().longValue() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				value.setStagingReferenceID(String.valueOf(staging.getEarMarkId()));
			}
			else {
				value.setStagingReferenceID(null);
			}

			if ((value.getTransactionType() == null) || "".equals(value.getTransactionType().trim())) {
				value.setTransactionType(ICMSConstant.INSTANCE_PRE_DEAL);
			}

			value.setCustomerName(null);
			value.setCustomerID(ICMSConstant.LONG_INVALID_VALUE);

			return value;
		}
		return null;
	}

	protected SBPreDealBusManager getSBPreDealBusManager() {
		SBPreDealBusManager busMgr = (SBPreDealBusManager) BeanController.getEJB(ICMSJNDIConstant.SB_PRE_DEAL_BUS_JNDI,
				SBPreDealBusManagerHome.class.getName());

		if (busMgr == null) {
			DefaultLogger.debug(this, "Business Bean is null");
		}

		return busMgr;
	}

	protected SBPreDealBusManager getSBStagingPreDealBusManager() {
		SBPreDealBusManager busMgr = (SBPreDealBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_PRE_DEAL_BUS_JNDI, SBPreDealBusManagerHome.class.getName());

		if (busMgr == null) {
			DefaultLogger.debug(this, "Staging Business Bean is null");
		}

		return busMgr;
	}

	protected ITrxResult prepareResult(IPreDealTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();

		result.setTrxValue(value);

		return result;
	}

	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}
}