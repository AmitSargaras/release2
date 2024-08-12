/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * AbstractCreditRiskParamOperation
 *
 * Created on 1:51:35 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.creditriskparam.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.creditriskparam.bus.CreditRiskParamBusDelegate;
import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 15, 2007 Time: 1:51:35 PM
 */
public abstract class AbstractCreditRiskParamOperation extends CMSTrxOperation implements ITrxRouteOperation {

	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		DefaultLogger.debug(this, "Begin process for operation : " + getOperationName());

		return super.preProcess(value);
	}

	public ITrxResult postProcess(ITrxResult iTrxResult) throws TrxOperationException {
		DefaultLogger.debug(this, "End process for operation : " + getOperationName());

		return super.postProcess(iTrxResult);
	}

	protected ICreditRiskParamGroupTrxValue createStagingData(ICreditRiskParamGroupTrxValue groupTrxValue)
			throws TrxOperationException {
		try {
			CreditRiskParamBusDelegate delegate = new CreditRiskParamBusDelegate();
			ICreditRiskParamGroup stagingData = groupTrxValue.getStagingCreditRiskParamGroup();

			// create staging data
			ICreditRiskParamGroup groupResult = delegate.createStgCreditRiskParameters(stagingData);

			groupTrxValue.setStagingCreditRiskParamGroup(groupResult);
			// todo think of a better way to get the staging reference id
			groupTrxValue.setStagingReferenceID(String.valueOf(groupResult.getFeedEntries()[0].getParameterRef()));

			return groupTrxValue;
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new TrxOperationException(e);
		}
	}

	protected ICreditRiskParamGroupTrxValue updateActualData(ICreditRiskParamGroupTrxValue groupTrxValue)
			throws TrxOperationException {
		try {
			CreditRiskParamBusDelegate delegate = new CreditRiskParamBusDelegate();
			ICreditRiskParamGroup stagingData = groupTrxValue.getStagingCreditRiskParamGroup();
			ICreditRiskParamGroup actualData = groupTrxValue.getCreditRiskParamGroup();
			ICreditRiskParamGroup clone = (ICreditRiskParamGroup) CommonUtil.deepClone(stagingData);

			clone.setVersionTime(actualData.getVersionTime());
			clone.setCreditRiskParamGroupID(actualData.getCreditRiskParamGroupID());

			ICreditRiskParamGroup groupResult = delegate.updateCreditRiskParameters(clone);

			groupTrxValue.setCreditRiskParamGroup(groupResult);
			;

			return groupTrxValue;
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new TrxOperationException(e);
		}
	}

	protected ICreditRiskParamGroupTrxValue updateTransactionData(ICreditRiskParamGroupTrxValue groupTrxValue)
			throws TrxOperationException {
		DefaultLogger.debug(this, "Updating data in transaction table now");

		try {
			groupTrxValue = prepareTrxValue(groupTrxValue);

			DefaultLogger.debug(this, "Now updating transaction data for CREDIT_RISK_PARAM");
			DefaultLogger.debug(this, "groupTrxValue.getReferenceID () : " + groupTrxValue.getReferenceID());
			DefaultLogger.debug(this, "groupTrxValue.getStagingReferenceID () : "
					+ groupTrxValue.getStagingReferenceID());

			ICMSTrxValue tempValue = super.updateTransaction(groupTrxValue);
			OBCreditRiskParamGroupTrxValue newValue = new OBCreditRiskParamGroupTrxValue(tempValue);

			newValue.setStagingCreditRiskParamGroup(groupTrxValue.getStagingCreditRiskParamGroup());
			newValue.setCreditRiskParamGroup(groupTrxValue.getStagingCreditRiskParamGroup());

			DefaultLogger.debug(this, "Transaction data updated");

			return newValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
	}

	protected ICreditRiskParamGroupTrxValue getCreditRiskParamGroupTrxValue(ITrxValue trxValue)
			throws TrxOperationException {
		try {
			if (trxValue instanceof ICreditRiskParamGroupTrxValue) {
				DefaultLogger.debug(this, "ITrxValue is an instance of ICreditRiskParamGroupTrxValue");

				return (ICreditRiskParamGroupTrxValue) trxValue;
			}
			else {
				DefaultLogger
						.debug(
								this,
								"ITrxValue is not an instance of ICreditRiskParamGroupTrxValue , creating new identical ICreditRiskParamGroupTrxValue object");

				return new OBCreditRiskParamGroupTrxValue(trxValue);
			}
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception is converting ITrxValue into ICreditRiskParamGroupTrxValue : "
					+ ex.toString(), ex);
		}
	}

	protected ICreditRiskParamGroupTrxValue prepareTrxValue(ICreditRiskParamGroupTrxValue groupTrxValue) {
		if (groupTrxValue != null) {
			DefaultLogger.debug(this, "ICreditRiskParamGroupTrxValue value not null");

			ICreditRiskParamGroup actual = groupTrxValue.getCreditRiskParamGroup();
			ICreditRiskParamGroup staging = groupTrxValue.getStagingCreditRiskParamGroup();

			if (actual != null) {
				// todo - check this out ....
				groupTrxValue.setReferenceID(String.valueOf(actual.getFeedEntries()[0].getFeedGroupId()));

				DefaultLogger.debug(this, "Setting reference id to : " + actual.getFeedEntries()[0].getFeedGroupId());
			}
			else {
				groupTrxValue.setReferenceID(null);

				DefaultLogger.debug(this, "Actual data not found in ICreditRiskParamGroupTrxValue !");
			}

			if ((staging != null) && (staging.getFeedEntries() != null) && (staging.getFeedEntries().length > 0)) {
				groupTrxValue.setStagingReferenceID(String.valueOf(staging.getFeedEntries()[0].getParameterRef()));

				DefaultLogger.debug(this, "Setting staging reference id to : "
						+ staging.getFeedEntries()[0].getParameterRef());
			}
			else {
				groupTrxValue.setStagingReferenceID(null);

				DefaultLogger.debug(this, "Staging data not found in ICreditRiskParamGroupTrxValue !");
			}

			return groupTrxValue;
		}

		return null;
	}

	protected ITrxResult prepareResult(ICreditRiskParamGroupTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

}
