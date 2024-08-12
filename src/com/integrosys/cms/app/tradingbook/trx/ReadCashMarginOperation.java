/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.ICashMargin;
import com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager;
import com.integrosys.cms.app.tradingbook.bus.TradingBookBusManagerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Operation to read cash margin.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadCashMarginOperation extends AbstractTradingBookReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadCashMarginOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_CASH_MARGIN;
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
			ICashMarginTrxValue trxVal = (ICashMarginTrxValue) val;

			ICashMargin[] stageCashMargin = null;
			ICashMargin[] actualCashMargin = null;
			long agreementID = trxVal.getAgreementID();

			ICMSTrxValue newCMSTrxValue = getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(agreementID),
					ICMSConstant.INSTANCE_CASH_MARGIN);

			if (newCMSTrxValue != null) {
				trxVal = new OBCashMarginTrxValue(newCMSTrxValue);

				ITradingBookBusManager mgr = null;

				String stagingRef = trxVal.getStagingReferenceID();
				String actualRef = trxVal.getReferenceID();

				DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

				if (stagingRef != null) {
					// get staging cash margin
					mgr = TradingBookBusManagerFactory.getStagingTradingBookBusManager();
					stageCashMargin = mgr.getCashMarginByGroupID(Long.parseLong(stagingRef));
				}
				// get actual cash margin
				mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
				actualCashMargin = mgr.getCashMarginByAgreementID(Long.parseLong(actualRef));

			}

			trxVal.setCashMargin(actualCashMargin);

			if ((stageCashMargin == null) || (stageCashMargin.length == 0)) {
				stageCashMargin = actualCashMargin;
			}

			trxVal.setStagingCashMargin(stageCashMargin);

			super.updateCPAgreementDetail(trxVal);
			super.updateTotalCashInterest(trxVal);

			return trxVal;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}
}
