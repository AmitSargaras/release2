package com.integrosys.cms.app.collateral.bus.valuation.valuator;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.exception.ChainedException;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.IValuator;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationDetailIncompleteException;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.FeedInfoModel;
import com.integrosys.cms.app.collateral.bus.valuation.model.MarketableValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.support.IMarketableProfileSingleton;
import com.integrosys.cms.app.collateral.bus.valuation.support.MktFeedSingleton;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationUtil;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

public class MarketableRunningTotalValuator implements IValuator {

	private final static Logger logger = LoggerFactory.getLogger(MarketableRunningTotalValuator.class);

	private IMarketableProfileSingleton marketableProfileSingleton;

	private static final String BASE_CURRENCY = PropertyManager.getValue("baseExchangeCcy");

	private static final String PROP_KEY_DAILY_RUNNING_CAP = "valuation.mktsec.dailyrunningcappercent.";

	private static final String PROP_KEY_MTHLY_RUNNING_CAP = "valuation.mktsec.mthlyrunningcappercent.";

	private static final String PROP_KEY_PER_TIMES = "valuation.mktsec.pertimes.";

	private static final String NEW_STOCK_CAP = PropertyManager.getValue("valuation.mktsec.newstockcappercent");

	private static final String NEW_STOCK_DURATION = PropertyManager.getValue("valuation.mktsec.newstockduration");

	private static final String NEW_STOCK_DURATION_UOM = PropertyManager
			.getValue("valuation.mktsec.newstockdurationuom");

	private static final String UNDEFINED_AMT_TO_USE = FeedInfoModel.UNDEFINED_AMT_TO_USE;

	private static final String AMT_TO_USE_DAILY_RUNNING_TOTAL = "AMT_TO_USE_DAILY_RUNNING_TOTAL";

	private static final String AMT_TO_USE_MTHLY_RUNNING_TOTAL = "AMT_TO_USE_MTHLY_RUNNING_TOTAL";

	private static final String AMT_TO_USE_STOCK_UNIT_PRICE = "AMT_TO_USE_STOCK_UNIT_PRICE";

	private static final String AMT_TO_USE_NEW_STOCK_CAP = "AMT_TO_USE_NEW_STOCK_CAP";

	private static final String AMT_TO_USE_MAX_PRICE_CAP = "AMT_TO_USE_MAX_PRICE_CAP";

	private static final String AMT_TO_USE_PER = "AMT_TO_USE_PER";

	private static final String AMT_TO_USE_DELISTED = "AMT_TO_USE_DELISTED";

	private static final String AMT_TO_USE_UNIT_TRUST = "AMT_TO_USE_UNIT_TRUST";

	private static final String AMT_TO_USE_GVT = "AMT_TO_USE_GVT_UNIT_PRICE";

	private static final String AMT_TO_USE_BOND = "AMT_TO_USE_BOND_UNIT_PRICE";

	private static final String UNABLE_TO_DETERMINE_AMT_FOR_VALUATION = "Unable to determine amount to use for valuation - please check setup!";

	private static final String EQUITY_TYPE_GOV = "GOV";

	private static final String EQUITY_TYPE_BOND = "BOND";

	private static boolean capSetupDone = false;

	private static HashMap dailyCappingMap = new HashMap();

	private static HashMap mthlyCappingMap = new HashMap();

	private static HashMap perCappingMap = new HashMap();

	public IMarketableProfileSingleton getMarketableProfileSingleton() {
		return marketableProfileSingleton;
	}

	public void setMarketableProfileSingleton(IMarketableProfileSingleton marketableProfileSingleton) {
		this.marketableProfileSingleton = marketableProfileSingleton;
	}

	public MarketableRunningTotalValuator() {
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

		retrieveCappingSetup();

		Amount totalAmt = new Amount(0, mValModel.getSecCurrency());
		for (int i = 0; i < portfItems.size(); i++) {
			IMarketableEquity pfItem = (IMarketableEquity) (portfItems.get(i));
			boolean hasValidPriceInfo = setPortfolioItemValuationUnitPrice(mValModel, pfItem);
			if (hasValidPriceInfo && pfItem.getUnitPrice() != null) {
				Amount pfItemCMV = new Amount(pfItem.getUnitPrice().getAmount() * pfItem.getNoOfUnits(), pfItem
						.getUnitPrice().getCurrencyCode());
				totalAmt = addAmount(pfItem, totalAmt, pfItemCMV);
			}
		}

		mValModel.setValuationDate(new Date());
		mValModel.setValOMV(totalAmt);
	}

	public void saveOnlineValuationInfoInCollateral(ICollateral collateral, IValuationModel model) {
	}

	public boolean setPortfolioItemValuationUnitPrice(MarketableValuationModel mValModel, IMarketableEquity pfItem) {
		String valuationType = checkValuationType(mValModel, pfItem);
		long pfId = pfItem.getEquityID();

		if (EQUITY_TYPE_GOV.equals(valuationType)) {
			logger.debug("Portfolio item id [" + pfId + "] " + "Government Mkt Sec used [" + AMT_TO_USE_GVT
					+ "] Amount = " + pfItem.getUnitPrice());
			return (pfItem.getUnitPrice() != null); // unit price should already
			// be available
		}

		Map feedMap = marketableProfileSingleton.getData();
		if (feedMap == null || feedMap.isEmpty()) {
			logger.warn("No feed info !!! Feed table is empty!! Unable to perform valuation!");
			return false;
		}

		String stockIdentifier = (EQUITY_TYPE_BOND.equals(valuationType)) ? pfItem.getIsinCode() : pfItem
				.getStockCode();
		FeedInfoModel feed = (FeedInfoModel) (feedMap.get(stockIdentifier));
		if (feed == null) {
			logger.warn("No feed info found for portfolio item with stock identifier " + stockIdentifier);
			return false;
		}

		if (UNDEFINED_AMT_TO_USE.equals(feed.getAmtToUseStr())) {
			if (ICMSConstant.EQUITY_TYPE_STOCK.equals(valuationType)) {
				setAmtToUseForStock(feed);
			}
			else if (ICMSConstant.EQUITY_TYPE_UNIT_TRUST.equals(valuationType)) {
				feed.setAmtToUseStr(AMT_TO_USE_UNIT_TRUST);
				feed.setAmtToUseForValuation(feed.getUnitPrice());
			}
			else if (EQUITY_TYPE_BOND.equals(valuationType)) {
				feed.setAmtToUseStr(AMT_TO_USE_BOND);
				feed.setAmtToUseForValuation(feed.getUnitPrice());
			}
			else {
				logger.warn("Unable to determine valuation type - skipping this portfolio item [" + pfId + "]");
				return false;
			}

			// if its still undefine, means data not setup
			if (UNDEFINED_AMT_TO_USE.equals(feed.getAmtToUseStr())) {
				logger.warn(UNABLE_TO_DETERMINE_AMT_FOR_VALUATION + " portfolio item id [" + pfId + "] "
						+ "Stock identifier = [" + stockIdentifier + "]");
				feed.setAmtToUseStr(UNABLE_TO_DETERMINE_AMT_FOR_VALUATION);
				return false;
			}
		}

		pfItem.setUnitPrice(feed.getAmtToUseForValuation());
		logger.debug("Portfolio item id [" + pfId + "] " + "Stock Identifier [" + stockIdentifier + "] used ["
				+ feed.getAmtToUseStr() + "] Amount = " + feed.getAmtToUseForValuation());
		return true;

	}

	private String checkValuationType(MarketableValuationModel valModel, IMarketableEquity pfItem) {
		String secSubtype = valModel.getSecSubtype();
		String equityType = pfItem.getEquityType();

		if (ICMSConstant.COLTYPE_MAR_MAIN_IDX_LOCAL.equals(secSubtype)
				|| ICMSConstant.COLTYPE_MAR_MAIN_IDX_FOREIGN.equals(secSubtype)
				|| ICMSConstant.COLTYPE_MAR_OTHERLISTED_LOCAL.equals(secSubtype)
				|| ICMSConstant.COLTYPE_MAR_OTHERLISTED_FOREIGN.equals(secSubtype)) {
			if (ICMSConstant.EQUITY_TYPE_STOCK.equals(equityType)) {
				logger.debug("######## IS STOCK");
				return ICMSConstant.EQUITY_TYPE_STOCK;
			}
		}

		if (ICMSConstant.EQUITY_TYPE_UNIT_TRUST.equals(equityType)) {
			logger.debug("######## IS UNIT TRUST");
			return ICMSConstant.EQUITY_TYPE_UNIT_TRUST;
		}

		if (ICMSConstant.COLTYPE_MAR_GOVT_LOCAL.equals(secSubtype)
				|| ICMSConstant.COLTYPE_MAR_GOVT_FOREIGN_SAMECCY.equals(secSubtype)
				|| ICMSConstant.COLTYPE_MAR_GOVT_FOREIGN_DIFFCCY.equals(secSubtype)) {
			logger.debug("######## IS Government");
			return EQUITY_TYPE_GOV;
		}

		if (ICMSConstant.COLTYPE_MAR_BONDS_LOCAL.equals(secSubtype)
				|| ICMSConstant.COLTYPE_MAR_BONDS_FOREIGN.equals(secSubtype)) {
			logger.debug("######## IS Bond");
			return EQUITY_TYPE_BOND;
		}

		logger.debug("######## Valuation for sec subtype [" + secSubtype + "] and equity type [" + equityType
				+ "] not supported!!");
		return null;
	}

	private void setAmtToUseForStock(FeedInfoModel feed) {

		String boardType = feed.getBoardType();
		Double dailyCap = (Double) dailyCappingMap.get(boardType);
		Double monthlyCap = (Double) mthlyCappingMap.get(boardType);
		Double perCap = (Double) perCappingMap.get(boardType);

		double dailyRunningCap = (dailyCap == null) ? 1 : dailyCap.doubleValue(); // already
		// in
		// percent
		double mthlyRunningCap = (monthlyCap == null) ? 1 : monthlyCap.doubleValue(); // already
		// in
		// percent
		double perTimes = (perCap == null) ? 1 : perCap.doubleValue();
		double newStockCap = (NEW_STOCK_CAP == null) ? 1 : Double.parseDouble(NEW_STOCK_CAP) / 100;

		if (isStockDelisted(feed)) {
			logger.debug("****** DELISTED");
			Amount zeroAmt = new Amount(0, BASE_CURRENCY);
			feed.setAmtToUseStr(AMT_TO_USE_DELISTED);
			feed.setAmtToUseForValuation(zeroAmt);
			return;
		}

		if (!isCappingApplicable(feed)) {
			logger.debug("****** Capping Not Applicable - Non Stock");
			feed.setAmtToUseStr(AMT_TO_USE_STOCK_UNIT_PRICE);
			feed.setAmtToUseForValuation(feed.getUnitPrice());
			return;
		}

		if (isNewStock(feed)) {
			logger.debug("****** NEW STOCK");
			Amount unitPrice = feed.getUnitPrice();
			Amount cappedNewStockPrice = new Amount(unitPrice.getAmount() * newStockCap, unitPrice.getCurrencyCode());
			feed.setAmtToUseStr(AMT_TO_USE_NEW_STOCK_CAP);
			feed.setAmtToUseForValuation(cappedNewStockPrice);
			return;
		}

		Amount lowestAmt = null;
		Amount dailyRunningAvg = feed.getDailyRunningAvg();
		if (dailyRunningAvg != null) {
			Amount cappedDailyAvg = new Amount(dailyRunningAvg.getAmount() * dailyRunningCap, dailyRunningAvg
					.getCurrencyCode());
			logger.debug(">>>>>>>>> cappedDailyAvg = " + cappedDailyAvg);
			lowestAmt = compareLowest(feed, lowestAmt, cappedDailyAvg, AMT_TO_USE_DAILY_RUNNING_TOTAL);
		}

		Amount mthEndRunningAvg = feed.getMonthEndRunningAvg();
		if (mthEndRunningAvg != null) {
			Amount cappedMthEndAvg = new Amount(mthEndRunningAvg.getAmount() * mthlyRunningCap, mthEndRunningAvg
					.getCurrencyCode());
			logger.debug(">>>>>>>>>> cappedMthEndAvg = " + cappedMthEndAvg);
			lowestAmt = compareLowest(feed, lowestAmt, cappedMthEndAvg, AMT_TO_USE_MTHLY_RUNNING_TOTAL);
		}

		Amount perAmount = feed.getPerAmount();
		if (perAmount != null) {
			Amount cappedPer = new Amount(perAmount.getAmount() * perTimes, perAmount.getCurrencyCode());
			lowestAmt = compareLowest(feed, lowestAmt, cappedPer, AMT_TO_USE_PER);
		}

		Amount maxPriceCap = feed.getMaxPriceCap();
		if (maxPriceCap != null) {
			lowestAmt = compareLowest(feed, lowestAmt, maxPriceCap, AMT_TO_USE_MAX_PRICE_CAP);
		}

	}

	private boolean isStockDelisted(FeedInfoModel feedInfo) {
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

	private boolean isCappingApplicable(FeedInfoModel feed) {
		if (ICMSConstant.STOCK_FEED_STOCK_TYPE.equals(feed.getStockType())) {
			return true; // capping only apply to stocks
		}
		return false; // capping does not apply to warrents/loan stocks etc
	}

	// Default is set to 6 months
	private boolean isNewStock(FeedInfoModel feed) {
		int newStockDuration = (NEW_STOCK_DURATION == null) ? 6 : Integer.parseInt(NEW_STOCK_DURATION);
		int newStockDurationUOM = (NEW_STOCK_DURATION_UOM == null) ? Calendar.MONTH : Integer
				.parseInt(NEW_STOCK_DURATION_UOM);

		if (feed.getLaunchDate() == null) { // how about if launch date is
			// future date?
			return false;
		}

		// CommonUtil will not work properly for months if the dates contain
		// time (when its the same day)
		long monthsSinceLaunch = CommonUtil.dateDiff(CommonUtil.getCurrentDate(), feed.getLaunchDate(),
				newStockDurationUOM);
		return (monthsSinceLaunch <= newStockDuration);
	}

	private Amount compareLowest(FeedInfoModel feed, Amount lowestAmt, Amount compareAmt, String compareAmtDescription) {
		if (lowestAmt == null) { // initialise lowestAmt if its null
			feed.setAmtToUseStr(compareAmtDescription);
			feed.setAmtToUseForValuation(compareAmt);
			return compareAmt;
		}

		int result = CommonUtil.compareAmount(lowestAmt, compareAmt);
		if (result == CommonUtil.LESS) {
			return lowestAmt;
		}
		else {
			feed.setAmtToUseStr(compareAmtDescription);
			feed.setAmtToUseForValuation(compareAmt);
			return compareAmt;
		}
	}

	private Amount addAmount(IMarketableEquity pfItem, Amount totalAmt, Amount newAmt) {
		if (totalAmt == null) {
			totalAmt = newAmt;
			return totalAmt;
		}

		if (newAmt == null) {
			return totalAmt;
		}

		Amount convertedAmt = newAmt;
		if (!(newAmt.getCurrencyCode().equals(totalAmt.getCurrencyCode()))) {
			if (newAmt.getAmount() != 0) { // if its 0, make no diff what
				// currency it is in
				convertedAmt = AmountConversion.getConversionAmount(newAmt, totalAmt.getCurrencyCode());
			}
		}

		try {
			return totalAmt.add(convertedAmt);
		}
		catch (ChainedException e) {
			throw new ValuationDetailIncompleteException("not able to add new amount [" + newAmt
					+ "] to zero amount for portfolio item id [" + pfItem.getEquityID() + "]", e);
		}

	}

	// ============================= Helper method for caps
	// ============================//
	private void retrieveCappingSetup() {
		if (capSetupDone) {
			return;
		}

		getBoardCapInfo();
		capSetupDone = true;
	}

	private void getBoardCapInfo() {
		List boardList = ((MktFeedSingleton) marketableProfileSingleton).getBoardList();

		for (int i = 0; i < boardList.size(); i++) {
			String board = (String) boardList.get(i);
			String dailyPropKey = PROP_KEY_DAILY_RUNNING_CAP + board;
			String dailyBoardCap = PropertyManager.getValue(dailyPropKey);
			if (dailyBoardCap != null) {
				double dailyCapPercent = Double.parseDouble(dailyBoardCap) / 100;
				dailyCappingMap.put(board, new Double(dailyCapPercent));
			}

			String mthlyPropKey = PROP_KEY_MTHLY_RUNNING_CAP + board;
			String mthlyBoardCap = PropertyManager.getValue(mthlyPropKey);
			if (mthlyBoardCap != null) {
				double mthlyCapPercent = Double.parseDouble(mthlyBoardCap) / 100;
				mthlyCappingMap.put(board, new Double(mthlyCapPercent));
			}

			String perPropKey = PROP_KEY_PER_TIMES + board;
			String perBoardCap = PropertyManager.getValue(perPropKey);
			if (perBoardCap != null) {
				// this is x times, no need to convert to percent
				perCappingMap.put(board, new Double(perBoardCap));
			}
		}
	}

	public IValuationModel createValuationModelInstance() {
		return new MarketableValuationModel();
	}

}
