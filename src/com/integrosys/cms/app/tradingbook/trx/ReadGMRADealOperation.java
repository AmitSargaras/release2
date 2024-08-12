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
import com.integrosys.cms.app.tradingbook.bus.IGMRADeal;
import com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager;
import com.integrosys.cms.app.tradingbook.bus.TradingBookBusManagerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Operation to read GMRA deal.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadGMRADealOperation extends AbstractTradingBookReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadGMRADealOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_GMRA_DEAL;
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

			IGMRADeal stageGMRADeal = null;
			IGMRADeal actualGMRADeal = null;
			long agreementID = ICMSConstant.LONG_INVALID_VALUE;

			cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(cmsTrxValue.getReferenceID(),
					ICMSConstant.INSTANCE_GMRA_DEAL);

			IGMRADealTrxValue trxVal = new OBGMRADealTrxValue(cmsTrxValue);

			String stagingRef = trxVal.getStagingReferenceID();
			String actualRef = trxVal.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				// get staging GMRA deal
				ITradingBookBusManager mgr = TradingBookBusManagerFactory.getStagingTradingBookBusManager();
				stageGMRADeal = mgr.getGMRADeal(Long.parseLong(stagingRef));
				trxVal.setStagingGMRADeal(stageGMRADeal);
				agreementID = stageGMRADeal.getAgreementID();
			}
			if (actualRef != null) {
				// get actual GMRA deal
				ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
				actualGMRADeal = mgr.getGMRADeal(Long.parseLong(actualRef));
				trxVal.setGMRADeal(actualGMRADeal);
				agreementID = actualGMRADeal.getAgreementID();
			}

			super.updateCPAgreementDetail(agreementID, trxVal);

			return trxVal;

		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}
}
