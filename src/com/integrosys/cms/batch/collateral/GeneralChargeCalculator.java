package com.integrosys.cms.batch.collateral;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.SessionContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.GeneralChargeUtil;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IStock;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBStockSummary;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorAdapter;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.batch.common.BatchJobTrxUtil;

public class GeneralChargeCalculator extends AbstractMonitorAdapter {

	public void start(String countryCode, SessionContext context) throws EventMonitorException {
		DefaultLogger.debug(this, "- Start Job -");
		try {
			recalculate(countryCode, context);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
			throw new EventMonitorException(e);
		}
	}

	/**
	 * Recalculate stock.recoverable amt and asst.shortfall amt for all valid
	 * general charge
	 */
	private void recalculate(String countryCode, SessionContext context) throws Exception {
		BatchJobTrxUtil trxUtil = new BatchJobTrxUtil(context);
		try {
			trxUtil.beginUserTrx();
			GeneralChargeDAO dao = new GeneralChargeDAO();
			IGeneralCharge[] genChrgList = dao.getGeneralCharge(countryCode);

			DefaultLogger.debug(this, "- num : " + genChrgList.length);

			for (int idx = 0; idx < genChrgList.length; idx++) {
				DefaultLogger.debug(this, "- ID : " + genChrgList[idx].getCollateralID());
				DefaultLogger.debug(this, "- getStockInsrShortfallAmount : "
						+ genChrgList[idx].getStockInsrShortfallAmount());
				DefaultLogger.debug(this, "- getDrawingPowerGrossAmount : "
						+ genChrgList[idx].getDrawingPowerGrossAmount());
				DefaultLogger.debug(this, "- getDrawingPowerLessInsrGrossAmount : "
						+ genChrgList[idx].getDrawingPowerLessInsrGrossAmount());
				// get the stock summary list which recalculates
				// the recoverable amt based on insr expiry date and prevalent
				// forex
				// rates in cms
				List stockSummaryList = GeneralChargeUtil.formatStockList(genChrgList[idx]);

				// use the stock summary list to update the values in
				// IGeneralCharge
				update(genChrgList[idx], stockSummaryList);
			}
			dao.persist(genChrgList);
			trxUtil.commitUserTrx();
		}
		catch (Exception e) {
			trxUtil.rollbackUserTrx();
			throw e;
		}
	}

	/**
	 * Update stock.recoverableAmt and asst.stockInsrShortfallAmt in general
	 * charge object with values from the summary.
	 * 
	 * @param genChrg - IGeneralCharge
	 * @param stockSummaryList - List of OBStockSummary
	 * @throws Exception on error updating the values
	 */
	private void update(IGeneralCharge genChrg, List stockSummaryList) throws Exception {

		DefaultLogger.debug("GeneralChargeCalculator.update", ">>>>>>>> cms_collateral_id : "
				+ genChrg.getCollateralID() + " >>>>>>>>>");

		if ((genChrg == null) || (genChrg.getStocks() == null) || (stockSummaryList == null)) {
			DefaultLogger.debug("GeneralChargeCalculator.update", "invalid params.");
			return;
		}

		// update recoverable amt for each stock
		Map stockMap = genChrg.getStocks();
		Iterator summaryItr = stockSummaryList.iterator();
		while (summaryItr.hasNext()) {
			OBStockSummary summary = (OBStockSummary) summaryItr.next();
			IStock stock = (IStock) stockMap.get(summary.getID());
			Amount recoverableAmt = (GeneralChargeUtil.isForexErrorAmount(summary.getRecoverableAmount())) ? null
					: summary.getRecoverableAmount();
			DefaultLogger.debug("GeneralChargeCalculator.update", ">> recoverable amt for " + summary.getID()
					+ " FROM [" + stock.getRecoverableAmount() + "] TO [" + recoverableAmt + "]");
			stock.setRecoverableAmount(recoverableAmt);
		}

		// update stock insurance shortfall amount for the asset
		Amount stockInsrShortFallAmt = null;
		Amount stockGrossAmt = ((OBGeneralCharge) genChrg).getStockGrossValue();
		Amount stockTotalValidInsrAmt = ((OBGeneralCharge) genChrg).getTotalValidStockInsrAmount();

		if ((stockGrossAmt == null) || GeneralChargeUtil.isForexErrorAmount(stockTotalValidInsrAmt)
				|| GeneralChargeUtil.isForexErrorAmount(stockGrossAmt)) {
			stockInsrShortFallAmt = null;
		}
		else {
			stockInsrShortFallAmt = (stockTotalValidInsrAmt == null) ? stockGrossAmt : stockGrossAmt
					.subtract(stockTotalValidInsrAmt);
		}

		stockInsrShortFallAmt = ((stockInsrShortFallAmt != null) && (stockInsrShortFallAmt.getAmount() < 0)) ? new Amount(
				0, genChrg.getCurrencyCode())
				: stockInsrShortFallAmt;

		genChrg.setStockInsrShortfallAmount(stockInsrShortFallAmt);

		OBGeneralCharge genChrgOB = (OBGeneralCharge) genChrg;
		Amount stockGrossValue = genChrgOB.getStockGrossValue();
		Amount stockCreditorsAmt = genChrgOB.getTotalStockCreditors();
		Amount stockNetValue = genChrgOB.getStockNetValue();
		Amount stockRecoverableAmt = genChrgOB.getTotalRecoverableStockAmount();

		Amount debtorNetValue = null;
		if (genChrg.getDebtor() != null) {
			// update debtor net value
			debtorNetValue = genChrgOB.getDebtorNetValue(stockNetValue);
			debtorNetValue = (GeneralChargeUtil.isForexErrorAmount(debtorNetValue)) ? null : debtorNetValue;
			genChrg.getDebtor().setNetValue(debtorNetValue);
		}

		DefaultLogger.debug(this, ">> stockGrossAmt : " + stockGrossAmt);
		DefaultLogger.debug("GeneralChargeCalculator.update", ">> stockTotalValidInsrAmt : " + stockTotalValidInsrAmt);
		DefaultLogger.debug("GeneralChargeCalculator.update", ">> stockNetValue : " + stockNetValue);
		DefaultLogger.debug("GeneralChargeCalculator.update", ">> stockCreditorsAmt : " + stockCreditorsAmt);
		DefaultLogger.debug("GeneralChargeCalculator.update", ">> stockRecoverableAmt : " + stockRecoverableAmt);

		// update asset drawing power gross amt
		Amount dpGrossAmt = genChrgOB.getCalculatedDrawingPowerGrossAmount(stockGrossValue, stockCreditorsAmt,
				stockNetValue, debtorNetValue);
		dpGrossAmt = (GeneralChargeUtil.isForexErrorAmount(dpGrossAmt)) ? null : dpGrossAmt;
		genChrg.setDrawingPowerGrossAmount(dpGrossAmt);

		// update asset drawing power less insr gross amt
		Amount dpLessInsrGrossAmt = genChrgOB.getCalculatedDrawingPowerLessInsrGrossAmount(stockGrossValue,
				stockCreditorsAmt, stockNetValue, debtorNetValue, stockRecoverableAmt);
		dpLessInsrGrossAmt = (GeneralChargeUtil.isForexErrorAmount(dpLessInsrGrossAmt)) ? null : dpLessInsrGrossAmt;
		genChrg.setDrawingPowerLessInsrGrossAmount(dpLessInsrGrossAmt);

		DefaultLogger.debug(this, ">> stockInsrShortFallAmt : " + stockInsrShortFallAmt);
		DefaultLogger.debug("GeneralChargeCalculator.update", ">> debtor net value : "
				+ ((genChrg.getDebtor() == null) ? null : genChrg.getDebtor().getNetValue()));
		DefaultLogger.debug("GeneralChargeCalculator.update", ">> dp gross amt : "
				+ genChrg.getDrawingPowerGrossAmount());
		DefaultLogger.debug("GeneralChargeCalculator.update", ">> dp less insr gross amt : "
				+ genChrg.getDrawingPowerLessInsrGrossAmount());

	}

}