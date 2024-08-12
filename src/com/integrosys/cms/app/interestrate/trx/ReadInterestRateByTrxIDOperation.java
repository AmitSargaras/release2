/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.trx;

import java.util.Date;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.interestrate.bus.IInterestRate;
import com.integrosys.cms.app.interestrate.bus.IInterestRateBusManager;
import com.integrosys.cms.app.interestrate.bus.InterestRateBusManagerFactory;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * The operation is to read interest rate.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadInterestRateByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadInterestRateByTrxIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_INT_RATE_BY_TRXID;
	}

	/**
	 * This method is used to read a transaction object.
	 * 
	 * @param val transaction value required for retrieving transaction record
	 * @return transaction value
	 * @throws TransactionException on errors retrieving the transaction value
	 */
	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		try {

			ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(val.getTransactionID());

			OBInterestRateTrxValue intRateTrx = new OBInterestRateTrxValue(cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			IInterestRate intRate = null;
			IInterestRate[] stageIntRate = null;
			IInterestRate[] actualIntRate = null;

			if (stagingRef != null) {

				IInterestRateBusManager mgr = InterestRateBusManagerFactory.getStagingInterestRateBusManager();
				stageIntRate = mgr.getInterestRateByGroupID(Long.parseLong(stagingRef));
				intRateTrx.setStagingInterestRates(stageIntRate);

				if ((intRate == null) && (stageIntRate != null) && (stageIntRate.length != 0)) {
					intRate = stageIntRate[0];
				}
			}

			if (intRate != null) {
				intRateTrx.setIntRateType(intRate.getIntRateType());
				intRateTrx.setMonthYear(intRate.getIntRateDate());
			}

			String intRateType = intRateTrx.getIntRateType();
			Date monthYear = intRateTrx.getMonthYear();

			if (actualRef != null) {
				IInterestRateBusManager mgr = InterestRateBusManagerFactory.getActualInterestRateBusManager();
				actualIntRate = mgr.getInterestRateByGroupID(Long.parseLong(actualRef));

				if ((actualIntRate == null) || (actualIntRate.length == 0)) {
					actualIntRate = InterestRateHelper.initialIntRate(intRateType, monthYear);
				}
				intRateTrx.setInterestRates(actualIntRate);
			}

			return intRateTrx;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}

}