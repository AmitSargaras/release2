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
 * The operation is to read cash margin.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadCashMarginByTrxIDOperation extends AbstractTradingBookReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadCashMarginByTrxIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_CASH_MARGIN_BY_TRXID;
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

			ICashMarginTrxValue trxVal = new OBCashMarginTrxValue(cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();
			long agreementID = ICMSConstant.LONG_INVALID_VALUE;

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {

				ITradingBookBusManager mgr = TradingBookBusManagerFactory.getStagingTradingBookBusManager();
				ICashMargin[] cashMargin = mgr.getCashMarginByGroupID(Long.parseLong(stagingRef));
				trxVal.setStagingCashMargin(cashMargin);
				if ((cashMargin != null) && (cashMargin.length >= 1)) {
					agreementID = cashMargin[0].getAgreementID();
				}
			}

			if (actualRef != null) {

				ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
				ICashMargin[] cashMargin = mgr.getCashMarginByAgreementID(Long.parseLong(actualRef));
				trxVal.setCashMargin(cashMargin);
				if ((cashMargin != null) && (cashMargin.length >= 1)) {
					agreementID = cashMargin[0].getAgreementID();
				}
			}
			super.updateCPAgreementDetail(agreementID, trxVal);
			super.updateTotalCashInterest(trxVal);
			return trxVal;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}
}