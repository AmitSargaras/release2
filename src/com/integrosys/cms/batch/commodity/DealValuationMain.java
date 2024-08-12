/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/commodity/DealValuationMain.java,v 1.27 2006/09/20 12:29:21 hshii Exp $
 */
package com.integrosys.cms.batch.commodity;

import java.util.Date;

import javax.ejb.SessionContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealSearchCriteria;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.OBCommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.PriceType;
import com.integrosys.cms.app.commodity.deal.proxy.CommodityDealProxyFactory;
import com.integrosys.cms.app.commodity.deal.proxy.ICommodityDealProxy;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.commodity.deal.trx.OBCommodityDealTrxValue;
import com.integrosys.cms.app.commodity.main.bus.price.ICommodityPrice;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorAdapter;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.batch.common.BatchJobTrxUtil;
import com.integrosys.cms.batch.common.StartupInit;

/**
 * A batch program to perform deal valuation computation.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.27 $
 * @since $Date: 2006/09/20 12:29:21 $ Tag: $Name: $
 */
public class DealValuationMain extends AbstractMonitorAdapter {
	public void start(String countryCode, SessionContext context) throws EventMonitorException {
		DefaultLogger.debug(this, "- Start Job -");
		try {
			doWork(countryCode, context);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
			throw new EventMonitorException(e);
		}
	}

	public DealValuationMain() {
		StartupInit.init();
	}

	public void doWork(String countryCode, SessionContext context) throws Exception {
		BatchJobTrxUtil trxUtil = new BatchJobTrxUtil(context);
		try {
			trxUtil.beginUserTrx();
			DealValuationDAO dao = new DealValuationDAO();
			ICommodityDeal[] deals = dao.getCommodityDeals(countryCode);
			trxUtil.commitUserTrx();
			if ((deals == null) || (deals.length == 0)) {
				DefaultLogger.info(this, "No Deals to be valuated.");
				return;
			}
			reValuateDeals(dao, deals, trxUtil);
		}
		catch (Exception e) {
			throw e;
		}
	}

	public void revaluateCustomerDeals(CommodityDealSearchCriteria criteria, SessionContext context) throws Exception {

		try {
			DealValuationDAO dao = new DealValuationDAO();
			ICommodityDeal[] deals = dao.getCustomerCommodityDeals(criteria);
			if ((deals == null) || (deals.length == 0)) {
				DefaultLogger.info(this, "No Deals to be valuated.");
				return;
			}
			DefaultLogger.debug(this, "Revaluating Deals.");
			reValuateDeals(dao, deals, null);
		}
		catch (Exception e) {
			throw e;
		}
	}

	private void reValuateDeals(DealValuationDAO dao, ICommodityDeal deals[], BatchJobTrxUtil trxUtil) throws Exception {
		int size = 0;
		if ((deals == null) || ((size = deals.length) == 0)) {
			return;
		}
		if (trxUtil != null) {
			trxUtil.beginUserTrx();
		}
		ICommodityDealProxy proxy = CommodityDealProxyFactory.getProxy();
		ICommodityDealTrxValue[] dealTrx = new OBCommodityDealTrxValue[size];
		String[] stageDealIDs = new String[size];
		for (int i = 0; i < size; i++) {
			dealTrx[i] = proxy.getCommodityDealTrxValue(null, deals[i].getCommodityDealID(), false, false);
			stageDealIDs[i] = dealTrx[i].getStagingReferenceID();
		}

		ICommodityDeal[] stageDeals = dao.getStageCommodityDeals(stageDealIDs);
		stageDeals = sortDeals(stageDeals, stageDealIDs);
		if (trxUtil != null) {
			trxUtil.commitUserTrx();
		}
		if ((stageDeals == null) || (stageDeals.length == 0)) {
			throw new Exception("Staging Deals are null!!!!");
		}
		for (int i = 0; i < size; i++) {
			try {
				// double check in case staging data is missing
				if (!isValidDeal(deals[i], stageDeals[i], dealTrx[i])) {
					DefaultLogger.error(this, "Invalid Actual and Staging deal transaction!");
					DefaultLogger.info(this, "Skip the valuation....!!!!!!!!!!!!!!!!");
					continue;
				}
				if (trxUtil != null) {
					trxUtil.beginUserTrx();
				}
				boolean isValReq = false;
				DefaultLogger.debug(this, " checking for actual: " + deals[i].getCommodityDealID());
				if (setValuationRequired(deals[i], dao)) {
					DefaultLogger.debug(this, "updating actual: " + deals[i].getCommodityDealID());
					dao.updateCommodityDeal(deals[i]);
					isValReq = true;
				}

				DefaultLogger.debug(this, "checking for stage: " + stageDeals[i].getCommodityDealID());
				if (setValuationRequired(stageDeals[i], dao)) {
					DefaultLogger.debug(this, "Updating stage: " + stageDeals[i].getCommodityDealID());
					dao.updateStageCommodityDeal(stageDeals[i]);
					isValReq = true;
				}

				if (isValReq) {
					DefaultLogger.debug(this, "Updating transaction for valuation");
					dealTrx[i].setCommodityDeal(deals[i]);
					dealTrx[i].setStagingCommodityDeal(stageDeals[i]);
					proxy.systemValuateCommodityDeal(dealTrx[i]);
				}
				else {
					DefaultLogger.debug(this, "no valuation is required for the deal:" + deals[i].getCommodityDealID());
				}
				if (trxUtil != null) {
					trxUtil.commitUserTrx();
				}
			}
			catch (Exception e) {
				DefaultLogger.debug(this, " Rolling back..." + deals[i].getCommodityDealID());
				if (trxUtil != null) {
					trxUtil.rollbackUserTrx();
				}
			}
		}
	}

	private boolean isValidDeal(ICommodityDeal actual, ICommodityDeal stage, ICommodityDealTrxValue trx) {
		long refID = Long.parseLong(trx.getReferenceID());
		long stageRefID = Long.parseLong(trx.getStagingReferenceID());

		long actualID = ICMSConstant.LONG_INVALID_VALUE, stageID = ICMSConstant.LONG_INVALID_VALUE;
		if (actual != null) {
			actualID = actual.getCommodityDealID();
		}

		if (stage != null) {
			stageID = stage.getCommodityDealID();
		}

		DefaultLogger.info("DealValuationMain.isValidDeal", "Transaction and actual deal id:" + refID + " (" + actualID
				+ "); Trx and staging deal id:" + stageRefID + " (" + stageID + ")");
		if (refID != actualID) {
			return false;
		}
		if (stageRefID != stageID) {
			DefaultLogger.debug("DealValuationMain.isValidDeal", "\nactual obj: " + actual + "\nstage obj: " + stage);
			return false;
		}
		return true;
	}

	private ICommodityDeal[] sortDeals(ICommodityDeal[] deals, String[] dealIDs) {
		if ((deals == null) || (deals.length == 0)) {
			return null;
		}

		ICommodityDeal[] dealArr = new OBCommodityDeal[dealIDs.length];
		for (int i = 0; i < dealIDs.length; i++) {
			long dealid = Long.parseLong(dealIDs[i]);
			for (int j = 0; j < deals.length; j++) {
				if (deals[j].getCommodityDealID() == dealid) {
					dealArr[i] = deals[j];
					break;
				}
			}
		}
		return dealArr;
	}

	private boolean isAmtChanged(Amount oldAmt, Amount newAmt) {
		if ((oldAmt == null) && (newAmt == null)) {
			return false;
		}
		if ((oldAmt != null) && (newAmt != null) && oldAmt.equals(newAmt)) {
			return false;
		}
		return true;
	}

	private boolean isStringChanged(String oldStr, String newStr) {
		if ((oldStr == null) && (newStr == null)) {
			return false;
		}

		if ((oldStr != null) && (newStr != null) && oldStr.equals(newStr)) {
			return false;
		}

		return true;
	}

	private boolean isDateChanged(Date oldDate, Date newDate) {
		if ((oldDate == null) && (newDate == null)) {
			return false;
		}

		if ((oldDate != null) && (newDate != null) && oldDate.equals(newDate)) {
			return false;
		}

		return true;
	}

	private boolean setValuationRequired(ICommodityDeal deal, DealValuationDAO dao) throws Exception {
		boolean changed = false;
		if ((deal.getContractPriceType() != null)
				&& (deal.getContractPriceType().getName().equals(PriceType.EOD_PRICE.getName())
						|| deal.getContractPriceType().getName().equals(PriceType.FLOATING_FUTURES_PRICE.getName()) || deal
						.getContractPriceType().getName().equals(PriceType.NON_RIC_PRICE.getName()))) {
			ICommodityPrice newPrice = dao.getCommodityPrice(deal);
			DefaultLogger.info(this, " old price:" + deal.getActualPrice() + " new price:" + newPrice.getClosePrice());
			DefaultLogger.info(this, " old RIC:" + deal.getContractRIC() + " new RIC:"
					+ newPrice.getCommodityProfile().getReuterSymbol());
			DefaultLogger.info(this, " old date:" + deal.getActualMarketPriceDate() + " new Date:"
					+ newPrice.getCloseUpdateDate());

			changed = isAmtChanged(deal.getActualPrice(), newPrice.getClosePrice());
			if (!changed) {
				changed = isStringChanged(deal.getContractRIC(), newPrice.getCommodityProfile().getReuterSymbol());
			}

			if (!changed) {
				changed = isDateChanged(deal.getActualMarketPriceDate(), newPrice.getCloseUpdateDate());
			}
			if (changed) {
				deal.setActualPrice(newPrice.getClosePrice());
				deal.setContractRIC(newPrice.getCommodityProfile().getReuterSymbol());
				deal.setActualMarketPriceDate(newPrice.getCloseUpdateDate());
			}
		}

		Amount newCMV = null;
		Amount newFSV = null;
		try {
			newCMV = deal.getCalculatedCMVAmt();
			newFSV = dao.getCalculatedFSVAmt(deal);
		}
		catch (Exception e) {
			DefaultLogger.warn(this, "Exception getting cmv/fsv, valuation will be ignored! > " + e.toString());
			return false;
		}

		DefaultLogger.info(this, " old cmv:" + deal.getCMV() + " new cmv:" + newCMV);
		DefaultLogger.info(this, " old fsv:" + deal.getFSV() + " new fsv:" + newFSV);

		if (!changed) {
			changed = isAmtChanged(deal.getCMV(), newCMV);
		}

		if (!changed) {
			changed = isAmtChanged(deal.getFSV(), newFSV);
		}

		if (changed) {
			deal.setCMV(newCMV);
			deal.setFSV(newFSV);
		}

		return changed;
	}
}