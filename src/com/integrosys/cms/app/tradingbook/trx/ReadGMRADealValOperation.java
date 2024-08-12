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
import com.integrosys.cms.app.tradingbook.bus.IGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager;
import com.integrosys.cms.app.tradingbook.bus.TradingBookBusManagerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Operation to read GMRA deal valuation.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadGMRADealValOperation extends AbstractTradingBookReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadGMRADealValOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_GMRA_DEAL_VAL;
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

			IGMRADealValTrxValue trxVal = (IGMRADealValTrxValue) val;

			long agreementID = trxVal.getAgreementID();

			IGMRADealVal[] stageGMRADealVal = null;
			IGMRADealVal[] actualGMRADealVal = null;
			ITradingBookBusManager mgr = null;

			String stagingRef = trxVal.getStagingReferenceID();
			String actualRef = trxVal.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);
			DefaultLogger.debug(this, "AgreementID: " + agreementID);

			if (stagingRef != null) {
				// get staging GMRA deal valuation
				mgr = TradingBookBusManagerFactory.getStagingTradingBookBusManager();
				stageGMRADealVal = mgr.getGMRADealValuationByGroupID(Long.parseLong(stagingRef));
			}

			// get actual GMRA deal valuation
			mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
			actualGMRADealVal = super.getGMRADealValuationByAgreementID(agreementID);

			String actualRefID = null;
			if ((actualGMRADealVal != null) && (actualGMRADealVal.length != 0)) {
				actualRefID = String.valueOf(actualGMRADealVal[0].getGroupID());
				if (actualRefID != null) {
					DefaultLogger.debug(this, "************ group id/ actualRefID" + actualRefID);

					try {
						cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(actualRefID,
								ICMSConstant.INSTANCE_GMRA_DEAL_VAL);
					}
					catch (Exception e) {
						// do nothing here coz the the first GMRA deal valuation
						// created without trx
					}
				}

			}
			else if ((stageGMRADealVal != null) && (stageGMRADealVal.length != 0)) {
				actualRefID = String.valueOf(stageGMRADealVal[0].getGroupID());
				if (actualRefID != null) {
					DefaultLogger.debug(this, "************ group id/ stageRefID" + actualRefID);

					try {
						cmsTrxValue = getTrxManager().getTrxByStageRefIDAndTrxType(actualRefID,
								ICMSConstant.INSTANCE_GMRA_DEAL_VAL);
					}
					catch (Exception e) {
						// do nothing here coz the the first GMRA deal valuation
						// created without trx
					}
				}
			}

			IGMRADealValTrxValue cmtrxVal = new OBGMRADealValTrxValue(cmsTrxValue);

			cmtrxVal.setGMRADealValuation(actualGMRADealVal);

			if ((stageGMRADealVal == null) || (stageGMRADealVal.length == 0)) {
				stageGMRADealVal = actualGMRADealVal;
			}

			cmtrxVal.setStagingGMRADealValuation(stageGMRADealVal);

			super.updateCPAgreementDetail(agreementID, cmtrxVal);

			return cmtrxVal;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}
}
