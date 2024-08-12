/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import java.util.HashMap;

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
 * Operation to read ISDA CSA deal valuation.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadISDACSADealValOperation extends AbstractTradingBookReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadISDACSADealValOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_ISDA_DEAL_VAL;
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

			DefaultLogger.debug(this, "Reference ID: " + cmsTrxValue.getReferenceID());

			cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(cmsTrxValue.getReferenceID(),
					ICMSConstant.INSTANCE_ISDA_DEAL_VAL);

			OBISDACSADealValTrxValue trxVal = new OBISDACSADealValTrxValue(cmsTrxValue);

			String stagingRef = trxVal.getStagingReferenceID();
			String actualRef = trxVal.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				ITradingBookBusManager mgr = TradingBookBusManagerFactory.getStagingTradingBookBusManager();
				IDealValuation[] stageDealVal = mgr.getDealValuation(Long.parseLong(stagingRef));
				IISDACSADealVal[] stageVal = super.updateISDACSADealDetail(stageDealVal);
				trxVal.setStagingISDACSADealValuation(stageVal);

				// to prevent duplicate deal comes from Batch file
				HashMap tempKeyMap = new HashMap();
				for (int i = 0; i < stageVal.length; i++) {
					DefaultLogger.debug(this, "staging=" + stageVal[i]);
					if (tempKeyMap.get(new Long(stageVal[i].getCMSDealID())) != null) {
						throw new TrxOperationException("Duplicate CMS deal ID for stage record, CMS deal ID="
								+ stageVal[i].getCMSDealID());
					}
					tempKeyMap.put(new Long(stageVal[i].getCMSDealID()), new Long(stageVal[i].getCMSDealID()));
				}
			}

			if (actualRef != null) {
				ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
				IISDACSADealVal[] actualVal = mgr.getISDACSADealValuationByGroupID(Long.parseLong(actualRef));
				trxVal.setISDACSADealValuation(actualVal);

				for (int i = 0; i < actualVal.length; i++) {
					// DefaultLogger.debug (this, "actual=" + actualVal[i]);
				}
			}

			super.updateCPAgreementDetail(trxVal);

			return trxVal;

		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}
}
