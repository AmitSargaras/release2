package com.integrosys.cms.app.collateral.bus.valuation.dao;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.OBMarketableEquity;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.FeedInfoModel;
import com.integrosys.cms.app.collateral.bus.valuation.model.MarketableValuationModel;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MarketableValuationDAO extends GenericValuationDAO implements IMarketableValuationDAO {


    public boolean retrieveValuationParams(IValuationModel valModel, List errorList) throws ValuationException {
		if (super.retrieveValuationParams(valModel, errorList)) {
			retrieveFeedsInfo(valModel);
			return true;
		}
		else {
			return false;
		}
	}

    //=============================== Used by StockPriceCapSingleton =========================================//
//    public void retrieveValuationInfoForCal(IValuationModel valModel) throws ValuationException {
//		retrievePortfolioWithFeeds(valModel);
//	}

	public void retrievePriceCapSetup(final Map resultMap) throws ValuationException {
		String query = "SELECT BOARD, MAX_CAP_PRICE FROM CMS_POLICY_CAP c, CMS_POLICY_CAP_group g "
				+ "WHERE c.POLICY_CAP_GROUP_ID = g.POLICY_CAP_GROUP_ID " + "AND BANK_ENTITY = '"
				+ ICMSConstant.BANK_GROUP_ENTITY + "'";

		getJdbcTemplate().query(query, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					String boardType = rs.getString("BOARD");
					String capPrice = rs.getString("MAX_CAP_PRICE");
                    //DefaultLogger.debug(this, ">>>>>>>>>>> boardType = " + boardType + " capPrice = " + capPrice);
                    if ((capPrice != null) && !"0".equals(capPrice.trim())) {
						//resultMap.put(boardType.trim(), new Double(capPrice.trim()));
                        resultMap.put(boardType, new Double(capPrice.trim())); //trim should all be at point-of-entry!
                    }
				}
				return null;
			}

		});
	}

	public void retrieveFeedsInfo(IValuationModel valModel) throws ValuationException {
		MarketableValuationModel mValModel = (MarketableValuationModel) valModel;

		final Map resultMap = mValModel.getFeeds();

        String query = "SELECT FEED.RIC, FEED.ISIN_CODE, FEED.STOCK_CODE, "
				+ "FEED.UNIT_PRICE AS FEEDPRICE, FEED.PREV_QUARTER_PRICE, FEED.CURRENCY, FEED.EXPIRY_DATE, "
				+ "PARAM.BOARD_TYPE, PARAM.SHARE_STATUS, PARAM.PREV_SHARE_STATUS, PARAM.IS_INT_SUSPEND "
				+ "FROM CMS_PRICE_FEED FEED, CMS_PORTFOLIO_ITEM PFITM, CMS_CREDIT_RISK_PARAM PARAM "
				+ "WHERE PFITM.CMS_COLLATERAL_ID = ? AND PFITM.STOCK_CODE = FEED.STOCK_CODE AND "
				+ "FEED.FEED_ID = PARAM.FEED_ID ";

		getJdbcTemplate().query(query, new Object[] { new Long(valModel.getCollateralId()) }, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					FeedInfoModel feedInfoModel = new FeedInfoModel();
					feedInfoModel.setRicCode(rs.getString("RIC"));
					String isinCode = rs.getString("ISIN_CODE");
                    String stockCode = rs.getString("STOCK_CODE");

					feedInfoModel.setIsinCode(isinCode);
                    feedInfoModel.setStockCode(stockCode);
                    String currencyCode = rs.getString("CURRENCY");
					String prevDayPrice = rs.getString("FEEDPRICE");
					String prevQuarterPrice = rs.getString("PREV_QUARTER_PRICE");

					if ((currencyCode != null) && (prevDayPrice != null)) {
						Amount curPrice = new Amount(Double.parseDouble(prevDayPrice), currencyCode);
						feedInfoModel.setUnitPrice(curPrice);
					}

					if ((currencyCode != null) && (prevQuarterPrice != null)) {
						Amount prevPrice = new Amount(Double.parseDouble(prevQuarterPrice), currencyCode);
						feedInfoModel.setPrevQuarterPrice(prevPrice);
					}

                    //trim should be at point of entry!!!
                    //feedInfoModel.setBoardType(rs.getString("BOARD_TYPE").trim());
                    feedInfoModel.setBoardType(rs.getString("BOARD_TYPE"));
                    feedInfoModel.setShareStatus(rs.getString("SHARE_STATUS"));
					feedInfoModel.setPrevShareStatus(rs.getString("PREV_SHARE_STATUS"));
					feedInfoModel.setExpiredDate(rs.getDate("EXPIRY_DATE"));

					String isSuspend = rs.getString("IS_INT_SUSPEND");
					if ((isSuspend != null) && "Y".equals(isSuspend)) {
						feedInfoModel.setIsSuspended(true);
					}

					resultMap.put(stockCode, feedInfoModel);
				}
				return null;
			}
		});
	}

	protected void retrievePortfolioWithFeeds(IValuationModel valModel) throws ValuationException {
		MarketableValuationModel mValModel = (MarketableValuationModel) valModel;

		final List portItemList = mValModel.getPortfolioItems();
		final Map resultMap = mValModel.getFeeds();
		String query = "SELECT PORTF.ITEM_ID, PORTF.TYPE, PORTF.UNIT_PRICE, PORTF.UNIT_PRICE_CURRENCY, "
				+ "PORTF.NO_OF_UNITS, PORTF.RIC IRIC, PORTF.ISIN_CODE IISIN, PORTF.STOCK_CODE, "
                + "FEED.FEED_ID, FEED.RIC FRIC, FEED.ISIN_CODE FISIN, "
				+ "FEED.UNIT_PRICE AS FEEDPRICE, FEED.PREV_QUARTER_PRICE, FEED.CURRENCY, FEED.EXPIRY_DATE, "
				+ "PARAM.BOARD_TYPE, PARAM.SHARE_STATUS, PARAM.PREV_SHARE_STATUS, PARAM.IS_INT_SUSPEND "
				+ "FROM CMS_PORTFOLIO_ITEM PORTF LEFT OUTER JOIN CMS_PRICE_FEED FEED ON PORTF.STOCK_CODE = FEED.STOCK_CODE "
				+ "LEFT OUTER JOIN CMS_CREDIT_RISK_PARAM PARAM ON FEED.FEED_ID = PARAM.FEED_ID "
				+ "WHERE PORTF.CMS_COLLATERAL_ID = ?";

		getJdbcTemplate().query(query, new Object[] { new Long(valModel.getCollateralId()) }, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					IMarketableEquity marketableEquity = new OBMarketableEquity();
					marketableEquity.setEquityID(Long.parseLong(rs.getString("ITEM_ID")));
					marketableEquity.setEquityType(rs.getString("TYPE"));
					String portfUnitPrice = rs.getString("UNIT_PRICE");
					String portfUnitPriceCur = rs.getString("UNIT_PRICE_CURRENCY");
					if ((portfUnitPrice != null) && (portfUnitPriceCur != null)) {
						Amount unitPrice = new Amount(Double.parseDouble(portfUnitPrice), portfUnitPriceCur);
						marketableEquity.setUnitPrice(unitPrice);
					}
					//Start- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
					marketableEquity.setNoOfUnits(rs.getDouble("NO_OF_UNITS"));
					//End- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
					//marketableEquity.setRIC(rs.getString("IRIC"));
					//marketableEquity.setIsinCode(rs.getString("IISIN"));
                    String stockCode = rs.getString("STOCK_CODE");
                    marketableEquity.setStockCode(stockCode);
                    portItemList.add(marketableEquity);
					String feedId = rs.getString("FEED_ID");
					if (feedId != null) {
						FeedInfoModel feedInfoModel = new FeedInfoModel();
						//feedInfoModel.setRicCode(rs.getString("FRIC"));
						//feedInfoModel.setIsinCode(rs.getString("FISIN"));
                        feedInfoModel.setStockCode(stockCode);
                        String currencyCode = rs.getString("CURRENCY");
						String prevDayPrice = rs.getString("FEEDPRICE");
						String prevQuarterPrice = rs.getString("PREV_QUARTER_PRICE");
						if ((currencyCode != null) && (prevDayPrice != null)) {
							Amount curPrice = new Amount(Double.parseDouble(prevDayPrice), currencyCode);
							feedInfoModel.setUnitPrice(curPrice);
						}
						if ((currencyCode != null) && (prevQuarterPrice != null)) {
							Amount prevPrice = new Amount(Double.parseDouble(prevQuarterPrice), currencyCode);
							feedInfoModel.setPrevQuarterPrice(prevPrice);
						}
                        //should trim when input not at retrieval
                        //feedInfoModel.setBoardType(rs.getString("BOARD_TYPE").trim());
                        feedInfoModel.setBoardType(rs.getString("BOARD_TYPE"));
						feedInfoModel.setShareStatus(rs.getString("SHARE_STATUS"));
						feedInfoModel.setPrevShareStatus(rs.getString("PREV_SHARE_STATUS"));
						feedInfoModel.setExpiredDate(rs.getDate("EXPIRY_DATE"));

						String isSuspend = rs.getString("IS_INT_SUSPEND");
						if ((isSuspend != null) && "Y".equals(isSuspend)) {
							feedInfoModel.setIsSuspended(true);
						}

						resultMap.put(stockCode, feedInfoModel);
					}
				}
				return null;
			}
		});

	}


    public void retrieveFeedPriceInfo(Map resultMap) {
        //do nothing - used only in the subclass
    }


    public void retrieveBoardType(final List resultList){
        //do nothing - used only in the subclass
    }
}
