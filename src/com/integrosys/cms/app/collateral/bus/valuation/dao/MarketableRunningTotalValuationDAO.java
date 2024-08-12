package com.integrosys.cms.app.collateral.bus.valuation.dao;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.OBMarketableEquity;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.FeedInfoModel;
import com.integrosys.cms.app.collateral.bus.valuation.model.MarketableValuationModel;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class MarketableRunningTotalValuationDAO extends MarketableValuationDAO {
    private final static Logger logger = LoggerFactory.getLogger(MarketableRunningTotalValuationDAO.class);


    public boolean retrieveValuationParams(IValuationModel valModel, List errorList) throws ValuationException {
        return super.retrieveValuationParams(valModel, errorList);
    }

    public void retrieveValuationInfoForCal(IValuationModel valModel) throws ValuationException {
		retrievePortfolioOnly(valModel);
        MarketableValuationModel mValModel = (MarketableValuationModel) valModel;
        String size = (mValModel.getPortfolioItems() == null) ? "Size of portfolio items = null" : ("Size of portfolio items = " + mValModel.getPortfolioItems().size());
        logger.debug(">>>>>>>>>> Collateral ID: [" + mValModel.getCollateralId() + "]; number of portfolio items: [" + size + "]");

    }

    public void retrievePortfolioOnly(IValuationModel valModel) throws ValuationException {
        MarketableValuationModel mValModel = (MarketableValuationModel) valModel;

        final List portItemList = mValModel.getPortfolioItems();
        String query = "SELECT PORTF.ITEM_ID, PORTF.TYPE, PORTF.UNIT_PRICE, PORTF.UNIT_PRICE_CURRENCY, "
                + "PORTF.NO_OF_UNITS, PORTF.RIC, PORTF.ISIN_CODE, PORTF.STOCK_CODE "
                + "FROM CMS_PORTFOLIO_ITEM PORTF "
                + "WHERE PORTF.CMS_COLLATERAL_ID = ?";

        getJdbcTemplate().query(query, new Object[] { new Long(valModel.getCollateralId()) }, new ResultSetExtractor() {

            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {

                int count = 0;
                while (rs.next()) {
                    count++;
                    IMarketableEquity marketableEquity = new OBMarketableEquity();
                    marketableEquity.setEquityID(Long.parseLong(rs.getString("ITEM_ID")));
                    marketableEquity.setEquityType(rs.getString("TYPE"));
                    marketableEquity.setIsinCode(rs.getString("ISIN_CODE"));
                    marketableEquity.setStockCode(rs.getString("STOCK_CODE"));
                    //Start- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
                    marketableEquity.setNoOfUnits(rs.getDouble("NO_OF_UNITS"));
                    //End- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.

                    String portfUnitPrice = rs.getString("UNIT_PRICE");
                    String portfUnitPriceCur = rs.getString("UNIT_PRICE_CURRENCY");
                    if ((portfUnitPrice != null) && (portfUnitPriceCur != null)) {
                        Amount unitPrice = new Amount(Double.parseDouble(portfUnitPrice), portfUnitPriceCur);
                        marketableEquity.setUnitPrice(unitPrice);
                    }
                    portItemList.add(marketableEquity);
                }

                //logger.debug(">>>>>>>> Total number of portfolio item retrieved = " + count);
                return null;
            }
        });

        //logger.debug(">>>>>>>>>> Collateral ID: [" + mValModel.getCollateralId() + "]; number of portfolio items: [" + portItemList.size() + "]");
    }


    public void retrieveFeedPriceInfo(final Map resultMap) {
        String query = "select mktsec.TICKER, mktsec.BOARD_TYPE, mktsec.RUNNING_TOTAL_CURRENCY, " +
                "mktsec.RUNNING_DAILY_TOTAL, mktsec.RUNNING_MONTH_END_TOTAL, " +
                "mktsec.DAYS_FOR_DAILY_TOTAL, mktsec.MONTHS_FOR_MONTH_END_TOTAL, " +
                "policycap.MAX_CAP_CURRENCY, policycap.MAX_CAP_PRICE, " +
                "fd.TYPE, fd.ISIN_CODE, fd.CURRENCY, fd.UNIT_PRICE, fd.EXPIRY_DATE, fd.DATE_LAUNCHED, " +
                "crp.SHARE_STATUS, crp.IS_INT_SUSPEND " +
                "from CMS_VALUATION_MKT_SEC_SUMMARY mktsec " +
                "left outer join " +
                "    (select cap.BOARD, cap.MAX_CAP_PRICE, cap.CURRENCY MAX_CAP_CURRENCY " +
                "     from CMS_POLICY_CAP cap, CMS_POLICY_CAP_GROUP grp " +
                "     where cap.POLICY_CAP_GROUP_ID = grp.POLICY_CAP_GROUP_ID" +
                "     AND grp.BANK_ENTITY = '" + ICMSConstant.BANK_GROUP_ENTITY + "') policycap " +
                "on mktsec.BOARD_TYPE = policycap.BOARD " +
                "right outer join CMS_PRICE_FEED fd on mktsec.TICKER = fd.TICKER " +
                "left outer join CMS_CREDIT_RISK_PARAM crp on fd.feed_id = crp.FEED_ID ";

        //logger.debug(">>>>>>>>>> in MarketableRunningTotalValuationDAO.retrieveFeedPriceInfo()");

        getJdbcTemplate().query(query, new ResultSetExtractor() {

            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {

                   
                    FeedInfoModel feedInfoModel = new FeedInfoModel();
                    feedInfoModel.setStockCode(rs.getString("TICKER"));
                    feedInfoModel.setIsinCode(rs.getString("ISIN_CODE"));
                    feedInfoModel.setStockType(rs.getString("TYPE"));
                    feedInfoModel.setBoardType(rs.getString("BOARD_TYPE"));
                    feedInfoModel.setExpiredDate(rs.getDate("EXPIRY_DATE"));
                    feedInfoModel.setLaunchDate(rs.getDate("DATE_LAUNCHED"));
                    feedInfoModel.setShareStatus(rs.getString("SHARE_STATUS"));

                    String isSuspend = rs.getString("IS_INT_SUSPEND");
                    //String summaryCurrency = rs.getString("RUNNING_TOTAL_CURRENCY");
                    String runningDailyTotalStr = rs.getString("RUNNING_DAILY_TOTAL");
                    int daysForDailyTotal = rs.getInt("DAYS_FOR_DAILY_TOTAL");
                    String runningMonthEndTotalStr = rs.getString("RUNNING_MONTH_END_TOTAL");
                    int mthsForMonthlyTotal = rs.getInt("MONTHS_FOR_MONTH_END_TOTAL");
                    String capPriceCurrency = rs.getString("MAX_CAP_CURRENCY");
                    String capPriceStr = rs.getString("MAX_CAP_PRICE");
                    String currency = rs.getString("CURRENCY");
                    String unitPriceStr = rs.getString("UNIT_PRICE");

                    //stock identifier for stocks = stock code. for bonds = isin code
                    boolean isStock = (ICMSConstant.STOCK_FEED_STOCK_TYPE.equals(feedInfoModel.getStockType()));
                    String stockIdentifier = (isStock)? feedInfoModel.getStockCode() : feedInfoModel.getIsinCode();

                    if ((isSuspend != null) && ICMSConstant.TRUE_VALUE.equals(isSuspend)) {
                        feedInfoModel.setIsSuspended(true);
                    }

                    if(currency != null && runningDailyTotalStr != null && !(daysForDailyTotal<=0)) {
                        double runningDailyTotal = rs.getDouble("RUNNING_DAILY_TOTAL");
                        feedInfoModel.setDailyRunningAvg(new Amount(runningDailyTotal/daysForDailyTotal, currency));
                    }

                    if(currency != null && runningMonthEndTotalStr != null && !(mthsForMonthlyTotal<=0)) {
                        double runningMonthEndTotal = rs.getDouble("RUNNING_MONTH_END_TOTAL");
                        feedInfoModel.setMonthEndRunningAvg(new Amount(runningMonthEndTotal/mthsForMonthlyTotal, currency));
                    }

                    if(currency != null && unitPriceStr != null) {
                        double unitPrice = rs.getDouble("UNIT_PRICE");
                        feedInfoModel.setUnitPrice(new Amount(unitPrice, currency));
                    }

                    if ((capPriceCurrency != null)
                            && ((capPriceStr != null) && !"0".equals(capPriceStr) && !"-1".equals(capPriceStr))) {
                        double capPrice = rs.getDouble("MAX_CAP_PRICE");
                        feedInfoModel.setMaxPriceCap(new Amount(capPrice, capPriceCurrency));
                    }

                    resultMap.put(stockIdentifier, feedInfoModel);

                }
                return null;
            }

        });

        //logger.debug(">>>>>>>>>> in MarketableRunningTotalValuationDAO.retrieveFeedPriceInfo().resultMap.size()=" + resultMap.size());

    }


    public void retrieveBoardType(final List resultList) {
        String query = "select ENTRY_CODE from COMMON_CODE_CATEGORY_ENTRY " +
                       "where category_code = 'BOARD_TYPE' AND ACTIVE_STATUS = '1' ";

        getJdbcTemplate().query(query, new ResultSetExtractor() {
            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    String boardType = rs.getString("ENTRY_CODE");
                    resultList.add(boardType);
                }
                return null;
            }

        });
    }

    public void persistOtherInfo(IValuationModel valModel) throws ValuationException {

        String query = "update cms_portfolio_item set UNIT_PRICE=?, UNIT_PRICE_CURRENCY=? where ITEM_ID=? ";
        MarketableValuationModel mValModel = (MarketableValuationModel) valModel;
        List portfItems = mValModel.getPortfolioItems();

        for (int i = 0; i < portfItems.size(); i++) {
            IMarketableEquity pfItem = (IMarketableEquity) (portfItems.get(i));
            if(pfItem.getUnitPrice() != null) {
                ArrayList argList = new ArrayList();
                argList.add(new Double(pfItem.getUnitPrice().getAmount()));
                argList.add(pfItem.getUnitPrice().getCurrencyCode());
                argList.add(new Long(pfItem.getEquityID()));
                getJdbcTemplate().update(query, argList.toArray());
            } else {
                logger.info("Unit Price not updated for portfolio item id = " + pfItem.getEquityID());
            }
        }
    }

}
