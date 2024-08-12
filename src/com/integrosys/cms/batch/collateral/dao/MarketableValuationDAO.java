package com.integrosys.cms.batch.collateral.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.OBMarketableEquity;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-6-8
 * @Tag com.integrosys.cms.batch.collateral.MarketableValuationDAO.java
 */
public class MarketableValuationDAO extends AbstractCollateralValuationDAO {
	private String SELECT_MARKETABLE_TRX = "SELECT VW_COLLATERAL_VALUATION.*"
			+ " FROM VW_COLLATERAL_VALUATION,CMS_MARKETABLE_SEC,CMS_STAGE_MARKETABLE_SEC "
			+ " WHERE VW_COLLATERAL_VALUATION.S_CMS_COLLATERAL_ID = CMS_STAGE_MARKETABLE_SEC.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.A_CMS_COLLATERAL_ID = CMS_MARKETABLE_SEC.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.SECURITY_TYPE = 'MS'";

	private String SELECT_PORTFOLIO_ITEMS = "SELECT CMS_COLLATERAL_ID, ITEM_ID,TYPE,RIC,NO_OF_UNITS,STOCK_EXCHANGE,STOCK_EXCHANGE_COUNTRY,"
			+ "IS_BLACKLISTED,CMV,CMV_CURRENCY,FSV,FSV_CURRENCY,UNIT_PRICE,UNIT_PRICE_CURRENCY,STATUS,BOND_RATING"
			+ " FROM CMS_PORTFOLIO_ITEM" + " WHERE CMS_COLLATERAL_ID IN ";

	private String SELECT_STAGE_PORTFOLIO_ITEMS = "SELECT CMS_COLLATERAL_ID, ITEM_ID,TYPE,RIC,NO_OF_UNITS,STOCK_EXCHANGE,STOCK_EXCHANGE_COUNTRY,"
			+ "IS_BLACKLISTED,CMV,CMV_CURRENCY,FSV,FSV_CURRENCY,UNIT_PRICE,UNIT_PRICE_CURRENCY,STATUS,BOND_RATING"
			+ " FROM CMS_STAGE_PORTFOLIO_ITEM" + " WHERE CMS_COLLATERAL_ID IN ";

	private String UPDATE_PORTFOLIO_ITEMS = "UPDATE CMS_PORTFOLIO_ITEM"
			+ " SET CMV=?,CMV_CURRENCY=?,FSV=?,FSV_CURRENCY=?,UNIT_PRICE=?,UNIT_PRICE_CURRENCY=?,BOND_RATING=?"
			+ " WHERE CMS_COLLATERAL_ID=? AND ITEM_ID=? ";

	private String UPDATE_STAGE_PORTFOLIO_ITEMS = "UPDATE CMS_STAGE_PORTFOLIO_ITEM"
			+ " SET CMV=?,CMV_CURRENCY=?,FSV=?,FSV_CURRENCY=?,UNIT_PRICE=?,UNIT_PRICE_CURRENCY=?,BOND_RATING=?"
			+ " WHERE CMS_COLLATERAL_ID=? AND ITEM_ID=? ";

	protected String getSelectStatement() {
		return SELECT_MARKETABLE_TRX;
	}

	protected boolean isValuationReqired(ICollateral collateral) {
		IValuation val = collateral.getValuation();
		if (val == null) {
			return false;
		}
		Date nextVal = getNextRevaluationDate(collateral, val.getValuationDate());
		collateral.getValuation().setNextRevaluationDate(nextVal);
		if (nextVal == null) {
			return false;
		}
		nextVal = util.getDateWithoutTime(nextVal);
		Date currDate = util.getDateWithoutTime(new Date(System.currentTimeMillis()));
		if (nextVal.compareTo(currDate) <= 0) {
			return true;
		}
		return false;
	}

	protected void fillCollateralDependentInfo(DBUtil dbUtil, ICollateralTrxValue[] collTrxValues) throws Exception {
		fillCollateralDependentInfo(dbUtil, collTrxValues, SELECT_PORTFOLIO_ITEMS, SELECT_STAGE_PORTFOLIO_ITEMS, null);
	};

	protected void synchronizeCollateralCMVFSV(ICollateralTrxValue collTrx) throws Exception {
		CollateralValuator valuator = new CollateralValuator();
		valuator.setCollateralCMVFSV(collTrx.getCollateral());
		valuator.setCollateralCMVFSV(collTrx.getStagingCollateral());
		// synchronizeCollateralCMVFSV(collTrx.getCollateral());
		// synchronizeCollateralCMVFSV(collTrx.getStagingCollateral());
	}

	protected HashMap parseCollateralDependentInfo(ResultSet rs, String infoType) throws Exception {
		ArrayList items = null;
		HashMap itemsMap = new HashMap();
		while (rs.next()) {
			String status = rs.getString(ITEM_STATUS);
			if ((status != null) && status.equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}
			long colID = rs.getLong(DEPOSIT_COL_ID);
			OBMarketableEquity item = new OBMarketableEquity();
			item.setRIC(rs.getString(ITEM_RIC));
			item.setStockExchange(rs.getString(ITEM_STOCK_EXCHANGE));
			item.setStockExchangeCountry(rs.getString(ITEM_STOCK_EXCHANGE_CTRY));
			//Start- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
			item.setNoOfUnits(rs.getDouble(ITEM_NO_OF_UNITS));
			//End- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
			item.setEquityType(rs.getString(ITEM_TYPE));
			item.setEquityID(rs.getLong(ITEM_ID));
			item.setBondRating(rs.getString(ITEM_BOND_RATING));
			item.setCMVCcyCode(rs.getString(ITEM_CMV_CCY));
			item.setCMV(util.getAmount(rs.getBigDecimal(ITEM_CMV), item.getCMVCcyCode()));
			item.setFSVCcyCode(rs.getString(ITEM_FSV_CCY));
			item.setFSV(util.getAmount(rs.getBigDecimal(ITEM_FSV), item.getFSVCcyCode()));
			item.setUnitPriceCcyCode(rs.getString(ITEM_UNIT_PRICE_CCY));
			item.setUnitPrice(util.getAmount(rs.getBigDecimal(ITEM_UNIT_PRICE), item.getUnitPriceCcyCode()));
			String isBlackListed = rs.getString(ITEM_IS_BLACKLISTED);
			if ((isBlackListed != null) && isBlackListed.equals(ICMSConstant.TRUE_VALUE)) {
				item.setIsCollateralBlacklisted(true);
			}
			else {
				item.setIsCollateralBlacklisted(false);
			}
			items = (ArrayList) itemsMap.get(new Long(colID));
			if (items == null) {
				items = new ArrayList();
			}
			items.add(item);
			itemsMap.put(new Long(colID), items);
		}
		return itemsMap;
	}

	protected void fillCollateralDependentInfo(ICollateralTrxValue[] collTrxValues, int startIndex, int size,
			HashMap actualMap, HashMap stageMap, String type) throws Exception {
		int maxIdx = startIndex + size;
		for (int index = startIndex; index < maxIdx; index++) {
			fillCollateralDependentInfo((IMarketableCollateral) collTrxValues[index].getCollateral(), actualMap);
			fillCollateralDependentInfo((IMarketableCollateral) collTrxValues[index].getStagingCollateral(), stageMap);
		}
	}

	private void fillCollateralDependentInfo(IMarketableCollateral collateral, HashMap itemsMap) {
		ArrayList itemsArray = (ArrayList) itemsMap.get(new Long(collateral.getCollateralID()));
		IMarketableEquity[] equityArray = null;
		if (itemsArray != null) {
			equityArray = (IMarketableEquity[]) itemsArray.toArray(new IMarketableEquity[0]);
		}
		collateral.setEquityList(equityArray);
	}

	protected void updateCollateralDetailCMVFSV(ICollateralTrxValue trxVal) throws Exception {
		updateStagePortfolioCMVFSV(trxVal.getCollateral(), UPDATE_PORTFOLIO_ITEMS);
		updateStagePortfolioCMVFSV(trxVal.getStagingCollateral(), UPDATE_STAGE_PORTFOLIO_ITEMS);
	}

	private void updateStagePortfolioCMVFSV(ICollateral coll, String updateSQL) throws Exception {
		// DefaultLogger.debug(this, updateSQL);
		DBUtil aDBUtil = new DBUtil();
		aDBUtil.setSQL(updateSQL);
		IMarketableCollateral marketable = (IMarketableCollateral) coll;
		IMarketableEquity[] items = marketable.getEquityList();

		if (items == null) {
			return;
		}
		for (int j = 0; j < items.length; j++) {
			util.setDBAmount(aDBUtil, 1, 2, items[j].getCMV());
			util.setDBAmount(aDBUtil, 3, 4, items[j].getFSV());
			util.setDBAmount(aDBUtil, 5, 6, items[j].getUnitPrice());
			aDBUtil.setString(7, items[j].getBondRating());
			aDBUtil.setLong(8, coll.getCollateralID());
			aDBUtil.setLong(9, items[j].getEquityID());
			aDBUtil.executeUpdate();
		}
	}

	private Date getNextRevaluationDate(ICollateral col, Date latestValDate) {
		ICollateralParameter srp = util.getSecurityParameter(col);
		if (srp == null) {
			return null;
		}
		String timeFreq = srp.getValuationFrequencyUnit();
		int timeFreqNum = srp.getValuationFrequency();
		return util.getNextRevaluationDate(latestValDate, timeFreq, timeFreqNum);
	}
}
