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
import com.integrosys.cms.app.tradingbook.bus.IGMRADeal;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager;
import com.integrosys.cms.app.tradingbook.bus.TradingBookBusManagerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * The operation is to read GMRA deal valuation by transaction ID.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadGMRADealValByTrxIDOperation extends AbstractTradingBookReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadGMRADealValByTrxIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_GMRA_DEAL_VAL_BY_TRXID;
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

			OBGMRADealValTrxValue trxVal = new OBGMRADealValTrxValue(cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();
			long agreementID = ICMSConstant.LONG_INVALID_VALUE;

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {

				ITradingBookBusManager mgr = TradingBookBusManagerFactory.getStagingTradingBookBusManager();
				IDealValuation[] stageDealVal = mgr.getDealValuation(Long.parseLong(stagingRef));
				IGMRADealVal[] stageVal = super.updateGMRADealDetail(stageDealVal);
				trxVal.setStagingGMRADealValuation(stageVal);
				if ((stageVal != null) && (stageVal.length >= 1)) {
					IGMRADeal gmra = stageVal[0].getGMRADealDetail();
					agreementID = gmra.getAgreementID();
				}
			}

			if (actualRef != null) {

				ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
				IGMRADealVal[] dealList = mgr.getGMRADealValuationByGroupID(Long.parseLong(actualRef));
				trxVal.setGMRADealValuation(dealList);
			}
			super.updateCPAgreementDetail(agreementID, trxVal);
			return trxVal;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}

}