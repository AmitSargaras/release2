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
import com.integrosys.cms.app.liquidation.bus.LiquidationDAO;
import com.integrosys.cms.app.liquidation.bus.LiquidationDAOFactory;
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
public class ReadLiquidationByTrxRefIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadLiquidationByTrxRefIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_LIQUIDATION_BY_TRXREFID;
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

			ILiquidationTrxValue liqTrx = null;
//			System.out.println("Before get trxID");
			// Get the Liquidatoin Transaction ID using the Trx Reference which
			// is collateral Transaction ID.
			String trxID = "";
			ICMSTrxValue cmsTrxValue = (ICMSTrxValue) val;
			String collateralTrxID = cmsTrxValue.getTrxReferenceID();
			if ((collateralTrxID != null) && !collateralTrxID.trim().equals("")) {
				try {
					LiquidationDAO dao = LiquidationDAOFactory.getDAO();

					trxID = dao.getTrxIDByTrxRefID(collateralTrxID, cmsTrxValue.getTransactionType());
//					System.out.println("trxID = " + trxID);
				}
				catch (Exception e) {
					// no transaction yet.
				}
				cmsTrxValue = getTrxManager().getTransaction(trxID);
				// return cmsTrxValue;
				cmsTrxValue = super.getCMSTrxValue(cmsTrxValue);
//				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//				System.out.println("cmsTrxValue = " + cmsTrxValue);
//				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

			}

//			System.out.println("Before liqTrx = (ILiquidationTrxValue) cmsTrxValue ");
//			System.out.println("cmsTrxvalue =  " + cmsTrxValue);

			liqTrx = (ILiquidationTrxValue) cmsTrxValue;

//			System.out.println("NO TRAsaction CREATING ONE Transaction = " + cmsTrxValue);

			if ((val != null) && (val.getTransactionID() != null)) {
//				System.out.println("GOING INSIDE val.getTransactionID() != null ");
				if (cmsTrxValue == null) {
					cmsTrxValue = getTrxManager().getTransaction(val.getTransactionID());
				}
			}
			// copied from ReadLiquidation
			{
//				System.out.println("Staging Ref ");
				String stagingRef = null;
				String actualRef = null;
				if (cmsTrxValue != null) {
					stagingRef = cmsTrxValue.getStagingReferenceID();
					actualRef = cmsTrxValue.getReferenceID();
				}
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
					DefaultLogger.debug(this, "stageLiquidation = " + stageLiquidation);
				}
				if (lactualRef > 0) {
					// get actual liquidation
					mgr = LiquidationBusManagerFactory.getActualLiquidationBusManager();
					actualLiquidation = mgr.getLiquidation(lactualRef);
					DefaultLogger.debug(this, "actualLiquidation = " + actualLiquidation);
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
							// do nothing here coz the the first col Liqs
							// created without trx
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
							// do nothing here coz the the first col Liqs
							// created without trx
						}
					}

					if (!liqTrx.getStatus().equals(ICMSConstant.STATE_ND)) {

						found = true;
					}

				}

				if (!found) {
					actualLiquidation = LiquidationHelper.initialLiquidation(lactualRef);
				}

				liqTrx = new OBLiquidationTrxValue(cmsTrxValue);

				liqTrx.setLiquidation(actualLiquidation);

				if (stageLiquidation == null) {
					stageLiquidation = actualLiquidation;
				}

				liqTrx.setStagingLiquidation(stageLiquidation);

				return liqTrx;
			}
			// if (cmsTrxValue != null){
			// liqTrx = new OBLiquidationTrxValue (cmsTrxValue);

			/*
			 * String stagingRef = cmsTrxValue.getStagingReferenceID(); String
			 * actualRef = cmsTrxValue.getReferenceID();
			 * 
			 * DefaultLogger.debug (this, "Actual Reference: " + actualRef +
			 * " , Staging Reference: " + stagingRef);
			 * 
			 * ILiquidation liq = null; ILiquidation stageLiq= null;
			 * ILiquidation actualLiq= null;
			 * 
			 * if (stagingRef != null) {
			 * 
			 * ILiquidationBusManager mgr =
			 * LiquidationBusManagerFactory.getStagingLiquidationBusManager();
			 * stageLiq = mgr.getLiquidation (Long.parseLong (stagingRef));
			 * liqTrx.setStagingLiquidation (stageLiq);
			 * 
			 * if(liq == null && stageLiq != null) { liq = stageLiq; }
			 * 
			 * }
			 * 
			 * 
			 * // long liquidationID = liqTrx.getCollateralID();
			 * 
			 * 
			 * if (actualRef != null) { ILiquidationBusManager mgr =
			 * LiquidationBusManagerFactory.getActualLiquidationBusManager();
			 * actualLiq = mgr.getLiquidation(Long.parseLong (actualRef));
			 * 
			 * if(actualLiq == null && actualRef != null) { actualLiq =
			 * LiquidationHelper.initialLiquidation(Long.parseLong(actualRef));
			 * } liqTrx.setLiquidation (actualLiq); } return liqTrx;
			 */
			// }
			/*
			 * // If Transaction not found, then create new transaction
			 * ILiquidation actualLiquidation =
			 * LiquidationHelper.initialLiquidation(0); ILiquidation
			 * stageLiquidation = null;
			 * 
			 * liqTrx = new OBLiquidationTrxValue ((ICMSTrxValue)val);
			 * 
			 * liqTrx.setLiquidation (actualLiquidation);
			 * 
			 * if (stageLiquidation == null) stageLiquidation =
			 * actualLiquidation;
			 * 
			 * liqTrx.setStagingLiquidation (stageLiquidation);
			 * 
			 * return liqTrx;
			 */
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
		// return null;// Should not come here./
	}

}