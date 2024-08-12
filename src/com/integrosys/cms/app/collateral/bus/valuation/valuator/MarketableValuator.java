package com.integrosys.cms.app.collateral.bus.valuation.valuator;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.exception.ChainedException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.IValuator;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationDetailIncompleteException;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.FeedInfoModel;
import com.integrosys.cms.app.collateral.bus.valuation.model.MarketableValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.support.StockPriceCapSingleton;
import com.integrosys.cms.app.collateral.bus.valuation.support.StockPriceCappingRule;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationUtil;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MarketableValuator implements IValuator {

	private StockPriceCapSingleton stockPriceCapSingleton;

	public MarketableValuator() {
	}

	public StockPriceCapSingleton getStockPriceCapSingleton() {
		return stockPriceCapSingleton;
	}

	public void setStockPriceCapSingleton(StockPriceCapSingleton stockPriceCapSingleton) {
		this.stockPriceCapSingleton = stockPriceCapSingleton;
	}

	public boolean checkCompleteForVal(IValuationModel model, List errorDesc) {
		MarketableValuationModel mValModel = (MarketableValuationModel) model;
		List portfItems = mValModel.getPortfolioItems();

		boolean result = true;
		if (portfItems == null || portfItems.size() == 0) {
			errorDesc.add("No portfolio items found");
			result = false;
		}

		return result;
	}

	public void performValuation(IValuationModel model) throws ValuationException {
		MarketableValuationModel mValModel = (MarketableValuationModel) model;
		List portfItems = mValModel.getPortfolioItems();

		for (int i = 0; i < portfItems.size(); i++) {
			IMarketableEquity nextEquity = (IMarketableEquity) (portfItems.get(i));
			// lookup the price feed based on isin code to fetch latest unit
			// price for the equity
			updatePortfolioItemPrice(nextEquity, mValModel);
		}
		deriveFinalCmvFsv(mValModel);
	}

	public void saveOnlineValuationInfoInCollateral(ICollateral collateral, IValuationModel model) {
	}

	private void deriveFinalCmvFsv(MarketableValuationModel mValModel) throws ValuationException {
		List portfItems = mValModel.getPortfolioItems();
		Amount zeroAmt = new Amount(0, mValModel.getSecCurrency());
		// DefaultLogger.debug(this,
		// ">>>>>>>>>>>>>>>>>>. in deriveFinalCmvFsv");
		for (int i = 0; i < portfItems.size(); i++) {
			IMarketableEquity nextEquity = (IMarketableEquity) (portfItems.get(i));
			if (nextEquity.getUnitPrice() != null) {
				// DefaultLogger.debug(this,
				// ">>>>>>>>>>>>>>>>>>. nextEquity.getUnitPrice() = " +
				// nextEquity.getUnitPrice());
				Amount newAmt = AmountConversion.getConversionAmount(nextEquity.getUnitPrice(), mValModel
						.getSecCurrency());
				// DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>. in newAmt = "
				// + newAmt);
				// DefaultLogger.debug(this,
				// ">>>>>>>>>>>>>>>>>>. in nextEquity.getNoOfUnits() = " +
				// nextEquity.getNoOfUnits());
				Amount nextAmt = new Amount(newAmt.getAmount() * nextEquity.getNoOfUnits(), newAmt.getCurrencyCode());
				// DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>. in nextAmt = "
				// + nextAmt);
				try {
					zeroAmt.addToThis(nextAmt);
				}
				catch (ChainedException e) {
					throw new ValuationDetailIncompleteException("not able to add new amount [" + newAmt
							+ "] to zero amount for collateral id [" + mValModel.getCollateralId() + "]", e);
				}
			}
		}
		mValModel.setValuationDate(new Date());
		mValModel.setValOMV(zeroAmt);
	}

	public void updatePortfolioItemPrice(IMarketableEquity equity, MarketableValuationModel mValModel) {
		Map feedMap = mValModel.getFeeds();
		String secSubtype = mValModel.getSecSubtype();
		String stockCode = equity.getStockCode();
		FeedInfoModel feedInfo = (FeedInfoModel) (feedMap.get(stockCode));
		String equityType = equity.getEquityType();
		if (equityType == null) {
			equityType = "";
		}
		boolean isStock = checkIsStock(secSubtype, equityType);
		if (feedInfo != null) {
			// price capping rule will be applied
			if (isStock) {
				if (checkStockDelisted(feedInfo)) {
					// set unit price to 0 for delisted share
					equity.setUnitPrice(new Amount(0, feedInfo.getUnitPrice().getCurrencyCode()));
					equity.setUnitPriceCcyCode(feedInfo.getUnitPrice().getCurrencyCode());
				}
				else if (checkStockRelisted(feedInfo)) {
					Amount newUnitPrice = StockPriceCappingRule.getRelistPriceCap(feedInfo.getUnitPrice(), feedInfo
							.getPrevQuarterPrice());
					if (newUnitPrice != null) {
						equity.setUnitPrice(newUnitPrice);
						equity.setUnitPriceCcyCode(newUnitPrice.getCurrencyCode());
					}
				}
				else {
					String boardType = feedInfo.getBoardType();
					// Double capping =
					// StockPriceCapSingleton.getInstance().getPriceCapSetting(boardType);
					Double capping = stockPriceCapSingleton.getPriceCapSetting(boardType);
					// find the price capping setup based on boardtype
					DefaultLogger.debug(this, boardType + "||" + feedInfo.getUnitPrice() + "||"
							+ feedInfo.getPrevQuarterPrice() + "||" + capping);
					Amount newUnitPrice = StockPriceCappingRule.getPriceCap(boardType, feedInfo.getUnitPrice(),
							feedInfo.getPrevQuarterPrice(), capping);
					// DefaultLogger.debug(this,
					// ">>>>>>>>>>>>>>>>.abt to set newUnitPrice = " +
					// newUnitPrice);
					if (newUnitPrice != null) {
						equity.setUnitPrice(newUnitPrice);
						equity.setUnitPriceCcyCode(newUnitPrice.getCurrencyCode());
					}
				}
			}
			else if ("U".equals(equityType)) {
				equity.setUnitPrice(feedInfo.getUnitPrice());
				equity.setUnitPriceCcyCode(feedInfo.getUnitPrice().getCurrencyCode());
			}
		}
	}

	private boolean checkStockDelisted(FeedInfoModel feedInfo) {
		if (ICMSConstant.SHARE_STATUS_PN4.equals(feedInfo.getShareStatus())
				|| ICMSConstant.SHARE_STATUS_PN17.equals(feedInfo.getShareStatus())
				|| ICMSConstant.SHARE_STATUS_DESIGNATED.equals(feedInfo.getShareStatus()) || feedInfo.getIsSuspended()
				|| ValuationUtil.checkDateExpired(feedInfo.getExpiredDate())) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean checkStockRelisted(FeedInfoModel feedInfo) {
		if (ICMSConstant.SHARE_STATUS_PN4.equals(feedInfo.getPrevShareStatus())
				|| ICMSConstant.SHARE_STATUS_PN17.equals(feedInfo.getPrevShareStatus())) {
			if (ICMSConstant.SHARE_STATUS_NORMAL.equals(feedInfo.getShareStatus())) {
				return true;
			}
		}
		return false;
	}

	private boolean checkIsStock(String secSubtype, String equityType) {
		if (ICMSConstant.COLTYPE_MAR_MAIN_IDX_LOCAL.equals(secSubtype)
				|| ICMSConstant.COLTYPE_MAR_MAIN_IDX_FOREIGN.equals(secSubtype)
				|| ICMSConstant.COLTYPE_MAR_OTHERLISTED_LOCAL.equals(secSubtype)
				|| ICMSConstant.COLTYPE_MAR_OTHERLISTED_FOREIGN.equals(secSubtype)) {
			if ("S".equals(equityType)) {
				return true;
			}
		}
		return false;
	}

	public IValuationModel createValuationModelInstance() {
		return new MarketableValuationModel();
	}

}
