/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.trx;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.liquidation.bus.ILiquidation;
import com.integrosys.cms.app.liquidation.bus.ILiquidationBusManager;
import com.integrosys.cms.app.liquidation.bus.IRecovery;
import com.integrosys.cms.app.liquidation.bus.LiquidationBusManagerFactory;
import com.integrosys.cms.app.liquidation.bus.OBRecovery;
import com.integrosys.cms.app.liquidation.bus.OBRecoveryExpense;
import com.integrosys.cms.app.liquidation.bus.OBRecoveryIncome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * The operation is to read liquidation.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadLiquidationByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadLiquidationByTrxIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_LIQUIDATION_BY_TRXID;
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

			ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(val.getTransactionID());

			OBLiquidationTrxValue liqTrx = new OBLiquidationTrxValue(cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			ILiquidation liq = null;
			ILiquidation stageLiq = null;
			ILiquidation actualLiq = null;

			if (stagingRef != null) {

				ILiquidationBusManager mgr = LiquidationBusManagerFactory.getStagingLiquidationBusManager();
				stageLiq = mgr.getLiquidation(Long.parseLong(stagingRef));

				// sort recovery expense
				if ((stageLiq != null) && (stageLiq.getRecoveryExpense() != null)) {
					java.util.Collections.sort((ArrayList) stageLiq.getRecoveryExpense(), new Comparator() {
						public int compare(Object o1, Object o2) {
							long long1 = ((OBRecoveryExpense) o1).getRefID();
							long long2 = ((OBRecoveryExpense) o2).getRefID();
							return (int) (long2 - long1);
						}
					});
				}

				// sort recovery
				if ((stageLiq != null) && (stageLiq.getRecovery() != null)) {
					java.util.Collections.sort((ArrayList) stageLiq.getRecovery(), new Comparator() {
						public int compare(Object o1, Object o2) {
							long long1 = ((OBRecovery) o1).getRefID();
							long long2 = ((OBRecovery) o2).getRefID();
							return (int) (long2 - long1);
						}
					});

					// sort recovery income
					for (Iterator iterator = stageLiq.getRecovery().iterator(); iterator.hasNext();) {
						IRecovery rec = (IRecovery) iterator.next();
						if (rec.getRecoveryIncome() != null) {
							java.util.Collections.sort((ArrayList) rec.getRecoveryIncome(), new Comparator() {
								public int compare(Object o1, Object o2) {
									long long1 = ((OBRecoveryIncome) o1).getRefID();
									long long2 = ((OBRecoveryIncome) o2).getRefID();
									return (int) (long2 - long1);
								}
							});
						}

					}
				}

				liqTrx.setStagingLiquidation(stageLiq);

				if ((liq == null) && (stageLiq != null)) {
					liq = stageLiq;
				}
			}

			if (actualRef != null) {
				ILiquidationBusManager mgr = LiquidationBusManagerFactory.getActualLiquidationBusManager();
				actualLiq = mgr.getLiquidation(Long.parseLong(actualRef));

				if ((actualLiq == null) && (actualRef != null)) {
					actualLiq = LiquidationHelper.initialLiquidation(Long.parseLong(actualRef));
				}

				// sort recovery expense
				if ((actualLiq != null) && (actualLiq.getRecoveryExpense() != null)) {
					java.util.Collections.sort((ArrayList) actualLiq.getRecoveryExpense(), new Comparator() {
						public int compare(Object o1, Object o2) {
							long long1 = ((OBRecoveryExpense) o1).getRefID();
							long long2 = ((OBRecoveryExpense) o2).getRefID();
							return (int) (long2 - long1);
						}
					});
				}

				// sort recovery
				if ((actualLiq != null) && (actualLiq.getRecovery() != null)) {
					java.util.Collections.sort((ArrayList) actualLiq.getRecovery(), new Comparator() {
						public int compare(Object o1, Object o2) {
							long long1 = ((OBRecovery) o1).getRefID();
							long long2 = ((OBRecovery) o2).getRefID();
							return (int) (long2 - long1);
						}
					});

					// sort recovery income
					for (Iterator iterator = actualLiq.getRecovery().iterator(); iterator.hasNext();) {
						IRecovery rec = (IRecovery) iterator.next();
						if (rec.getRecoveryIncome() != null) {
							java.util.Collections.sort((ArrayList) rec.getRecoveryIncome(), new Comparator() {
								public int compare(Object o1, Object o2) {
									long long1 = ((OBRecoveryIncome) o1).getRefID();
									long long2 = ((OBRecoveryIncome) o2).getRefID();
									return (int) (long2 - long1);
								}
							});
						}
					}
				}

				liqTrx.setLiquidation(actualLiq);
			}

			return liqTrx;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}

}