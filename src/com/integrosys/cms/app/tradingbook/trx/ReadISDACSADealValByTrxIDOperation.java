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
import com.integrosys.cms.app.tradingbook.bus.IDealValuation;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealVal;
import com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager;
import com.integrosys.cms.app.tradingbook.bus.TradingBookBusManagerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * The operation is to read ISDA CSA deal valuation by transaction ID.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadISDACSADealValByTrxIDOperation extends AbstractTradingBookReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadISDACSADealValByTrxIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_ISDA_DEAL_VAL_BY_TRXID;
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

			OBISDACSADealValTrxValue trxVal = new OBISDACSADealValTrxValue(cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				ITradingBookBusManager mgr = TradingBookBusManagerFactory.getStagingTradingBookBusManager();
				IDealValuation[] stageDealVal = mgr.getDealValuation(Long.parseLong(stagingRef));
				IISDACSADealVal[] stageVal = super.updateISDACSADealDetail(stageDealVal);
				DefaultLogger.debug(this, "size for staging=" + stageVal.length);
				trxVal.setStagingISDACSADealValuation(stageVal);
			}

			if (actualRef != null) {
				ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
				IISDACSADealVal[] dealList = mgr.getISDACSADealValuationByGroupID(Long.parseLong(actualRef));
				DefaultLogger.debug(this, "size for actual=" + dealList.length);
				trxVal.setISDACSADealValuation(dealList);
			}

			super.updateCPAgreementDetail(trxVal);
			return trxVal;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}

}