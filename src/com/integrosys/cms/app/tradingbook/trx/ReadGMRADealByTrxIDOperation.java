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
 * The operation is to read GMRA deal.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadGMRADealByTrxIDOperation extends AbstractTradingBookReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadGMRADealByTrxIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_GMRA_DEAL_BY_TRXID;
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

			OBGMRADealTrxValue trxVal = new OBGMRADealTrxValue(cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();
			long agreementID = ICMSConstant.LONG_INVALID_VALUE;

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				ITradingBookBusManager mgr = TradingBookBusManagerFactory.getStagingTradingBookBusManager();
				IGMRADeal gmra = mgr.getGMRADeal(Long.parseLong(stagingRef));
				trxVal.setStagingGMRADeal(gmra);
				agreementID = gmra.getAgreementID();
			}

			if (actualRef != null) {
				ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
				IGMRADeal gmra = mgr.getGMRADeal(Long.parseLong(actualRef));
				trxVal.setGMRADeal(gmra);
				agreementID = gmra.getAgreementID();
			}
			super.updateCPAgreementDetail(agreementID, trxVal);
			return trxVal;

		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}

}