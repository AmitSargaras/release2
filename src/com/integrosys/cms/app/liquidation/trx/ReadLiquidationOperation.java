/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.liquidation.bus.ILiquidation;
import com.integrosys.cms.app.liquidation.bus.ILiquidationBusManager;
import com.integrosys.cms.app.liquidation.bus.LiquidationBusManagerFactory;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Operation to read liquidation.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadLiquidationOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadLiquidationOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_LIQUIDATION;
	}

	/**
	 * This method is used to read a transaction object.
	 * 
	 * @param val transaction value required for retrieving transaction record
	 * @return transaction value
	 * @throws com.integrosys.base.businfra.transaction.TransactionException on
	 *         errors retrieving the transaction value
	 */
	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		try {
//			System.out.println("ReadLiquidationOperation getTransaction start");
			ICMSTrxValue cmsTrxValue = super.getCMSTrxValue(val);

			ILiquidationTrxValue trxVal = (ILiquidationTrxValue) val;
			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();

			long lactualRef = 0;
			if (actualRef != null) {
				lactualRef = Long.parseLong(actualRef);
			}
			DefaultLogger.debug(this, " lactualRef " + lactualRef);

			long lstagingRef = 0;
			if (stagingRef != null) {
				lstagingRef = Long.parseLong(stagingRef);
			}
			DefaultLogger.debug(this, " lstagingRef " + lstagingRef);

			ILiquidation stageLiquidation = null;
			ILiquidation actualLiquidation = null;
			ILiquidationBusManager mgr = null;
			boolean found = false;

			if (lstagingRef > 0) {
				// get staging liquidation
				mgr = LiquidationBusManagerFactory.getStagingLiquidationBusManager();
				stageLiquidation = mgr.getLiquidation(lstagingRef);
			}
			if (lactualRef > 0) {
				// get actual liquidation
				mgr = LiquidationBusManagerFactory.getActualLiquidationBusManager();
				actualLiquidation = mgr.getLiquidation(lactualRef);
			}

			String actualRefID = null;
			if (actualLiquidation != null) {
				actualRefID = String.valueOf(actualLiquidation.getLiquidationID());
				if (actualRefID != null) {
					DefaultLogger.debug(this, "************ collateral id/ actualRefID" + actualRefID);

					try {
						cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(actualRefID,
								ICMSConstant.INSTANCE_LIQUIDATION);
					}
					catch (Exception e) {
						// do nothing here coz the the first col Liqs created
						// without trx
					}
				}
				found = true;

			}
			else if (stageLiquidation != null) {
				actualRefID = String.valueOf(stageLiquidation.getLiquidationID());
				if (actualRefID != null) {
					DefaultLogger.debug(this, "************ group id/ stageRefID" + actualRefID);

					try {
						cmsTrxValue = getTrxManager().getTrxByStageRefIDAndTrxType(actualRefID,
								ICMSConstant.INSTANCE_LIQUIDATION);
					}
					catch (Exception e) {
						// do nothing here coz the the first col Liqs created
						// without trx
					}
				}

				if (!trxVal.getStatus().equals(ICMSConstant.STATE_ND)) {

					found = true;
				}

			}

			if (!found) {
				actualLiquidation = LiquidationHelper.initialLiquidation(lactualRef);
			}

			OBLiquidationTrxValue liqTrx = new OBLiquidationTrxValue(cmsTrxValue);

			liqTrx.setLiquidation(actualLiquidation);

			if (stageLiquidation == null) {
				stageLiquidation = actualLiquidation;
			}

			liqTrx.setStagingLiquidation(stageLiquidation);

			return liqTrx;

		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}
}
