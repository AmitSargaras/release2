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
 * Operation to read interest rate.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadInterestRateOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadInterestRateOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_INT_RATE;
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
			ICMSTrxValue cmsTrxValue = super.getCMSTrxValue(val);

			IInterestRateTrxValue trxVal = (IInterestRateTrxValue) val;

			String intRateType = trxVal.getIntRateType();
			Date monthYear = trxVal.getMonthYear();

			DefaultLogger.debug(this, " Type Code: " + intRateType);
			DefaultLogger.debug(this, " monthYear: " + monthYear);

			IInterestRate[] stageIntRates = null;
			IInterestRate[] actualIntRates = null;
			IInterestRateBusManager mgr = null;
			boolean found = false;

			// get staging interest rate
			mgr = InterestRateBusManagerFactory.getStagingInterestRateBusManager();
			stageIntRates = mgr.getInterestRate(intRateType, monthYear);

			// get actual interest rate
			mgr = InterestRateBusManagerFactory.getActualInterestRateBusManager();
			actualIntRates = mgr.getInterestRate(intRateType, monthYear);

			String actualRefID = null;
			if ((actualIntRates != null) && (actualIntRates.length != 0)) {
				actualRefID = String.valueOf(actualIntRates[0].getGroupID());
				if (actualRefID != null) {
					DefaultLogger.debug(this, "************ group id/ actualRefID" + actualRefID);

					try {
						cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(actualRefID,
								ICMSConstant.INSTANCE_INT_RATE);
					}
					catch (Exception e) {
						// do nothing here coz the the first col IntRates
						// created without trx
					}
				}
				found = true;

			}
			else if ((stageIntRates != null) && (stageIntRates.length != 0)) {
				actualRefID = String.valueOf(stageIntRates[0].getGroupID());
				if (actualRefID != null) {
					DefaultLogger.debug(this, "************ group id/ stageRefID" + actualRefID);

					try {
						cmsTrxValue = getTrxManager().getTrxByStageRefIDAndTrxType(actualRefID,
								ICMSConstant.INSTANCE_INT_RATE);
					}
					catch (Exception e) {
						// do nothing here coz the the first col IntRates
						// created without trx
					}
				}

				if (!trxVal.getStatus().equals(ICMSConstant.STATE_ND)) {

					found = true;
				}

			}

			if (!found) {
				actualIntRates = InterestRateHelper.initialIntRate(intRateType, monthYear);
			}

			OBInterestRateTrxValue intRateTrx = new OBInterestRateTrxValue(cmsTrxValue);

			intRateTrx.setInterestRates(actualIntRates);

			if ((stageIntRates == null) || (stageIntRates.length == 0)) {
				stageIntRates = actualIntRates;
			}

			intRateTrx.setStagingInterestRates(stageIntRates);

			return intRateTrx;

		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}
}
