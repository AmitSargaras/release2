/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/price/CommodityPriceDAO.java,v 1.11 2006/10/12 03:07:01 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.price;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateralDAOConstants;
import com.integrosys.cms.app.commodity.main.bus.profile.OBProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * DAO for commodity price.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2006/10/12 03:07:01 $ Tag: $Name: $
 */
public class CommodityPriceDAO implements ICommodityPriceDAOConstants {
	private DBUtil dbUtil;

	private static String SELECT_COMMODITY_PRICE_PROFILE = null;

	private static String SELECT_PRICE_GROUP_ID = null;

	private static String SELECT_STAGE_PRICE_GROUP_ID = null;

	private static String SELECT_FEED_GROUP = null;

	private static String MAX_PRICE_GROUP_ID = "MAX_GRP_ID";

	private static String SELECT_CLOSE_PRICE = "SELECT p.ClOSE_PRICE_CURRENCY, p.CLOSE_PRICE "
			+ "FROM CMS_CMDT_PRICE p WHERE p.profile_id = ?";

	private static String SELECT_SYSDATE_CURRENT_PRICE = "SELECT p.CURRENT_PRICE_CURRENCY, p.CURRENT_PRICE "
			+ "FROM CMS_CMDT_PRICE p WHERE p.profile_id = ? "
			//For DB2
//			+ "AND (p.CURRENT_UPDATE_DATE BETWEEN TRUNC(CURRENT TIMESTAMP) AND TRUNC(CURRENT TIMESTAMP) + 1 day - 1 second)";
			//For Oracle
			+ "AND (p.CURRENT_UPDATE_DATE BETWEEN TRUNC(CURRENT_TIMESTAMP) AND TRUNC(CURRENT_TIMESTAMP) + 1 day - 1 second)";

	private static String SELECT_CURRENT_PRICE = "SELECT p.CURRENT_PRICE_CURRENCY, p.CURRENT_PRICE "
			+ "FROM CMS_CMDT_PRICE p WHERE p.profile_id = ? ";

	private static String SELECT_CURRENT_PRICE_FIRST_UPDATE_DATE = "SELECT CURR_PRI_FIRST_UDATE FIRST_UDATE FROM CMS_CMDT_PRICE WHERE PROFILE_ID =?";

	/*
	 * Exact same query as "SELECT_CURRENT_PRICE_FIRST_UPDATE_DATE" except that
	 * this compare against the close price instead of current price (see the
	 * last "Select Max(sp3.group_id) ... " query).
	 * 
	 * Reason for having this as a separate static string instead of appending
	 * the different parts in the method is because of performance reason.
	 */
	private static String SELECT_CLOSE_PRICE_FIRST_UPDATE_DATE = "SELECT CLOSE_PRI_FIRST_UDATE FIRST_UDATE FROM CMS_CMDT_PRICE WHERE PROFILE_ID =?";

	static {
		StringBuffer buf = new StringBuffer();
		SELECT_COMMODITY_PRICE_PROFILE = "SELECT CMS_CMDT_PROFILE.PROFILE_ID, "
				+ "CMS_CMDT_PROFILE.STATUS, "
				+ "CMS_CMDT_PROFILE.PRODUCT_SUB_TYPE, "
				+ "CMS_CMDT_PROFILE.COUNTRY_AREA, "
				+ "CMS_CMDT_PROFILE.RIC, "
				+ "CMS_CMDT_PROFILE.PRICE_TYPE, "
				+ "CMS_CMDT_PROFILE.CATEGORY, "
				+ "CMS_CMDT_PROFILE.PRODUCT_TYPE, "
				+ "CMS_CMDT_PRICE.COMMODITY_PRICE_ID "
				+ "FROM CMS_CMDT_PROFILE LEFT OUTER JOIN CMS_CMDT_PRICE ON CMS_CMDT_PRICE.PROFILE_ID = CMS_CMDT_PROFILE.PROFILE_ID";

		buf = new StringBuffer();
		buf.append("select max(");
		buf.append(PRICE_GROUP_ID);
		buf.append(") as ");
		buf.append(MAX_PRICE_GROUP_ID);
		buf.append(" from ");
		buf.append(PRICE_STAGE_TABLE);

		SELECT_STAGE_PRICE_GROUP_ID = buf.toString();

		buf = new StringBuffer();
		buf.append("select max(");
		buf.append(PRICE_GROUP_ID);
		buf.append(") as ");
		buf.append(MAX_PRICE_GROUP_ID);
		buf.append(" from ");
		buf.append(PRICE_TABLE);
		buf.append(" where ");

		SELECT_PRICE_GROUP_ID = buf.toString();

		buf = new StringBuffer();
		buf.append("select ");
		buf.append(ICollateralDAOConstants.FEED_GROUP_ID);
		buf.append(" from ");
		buf.append(ICollateralDAOConstants.FEED_GROUP_TABLE);
		buf.append(" where ");
		buf.append(ICollateralDAOConstants.FEED_GROUP_TYPE);
		buf.append(" = ?");

		SELECT_FEED_GROUP = buf.toString();
	}

	/**
	 * Default Constructor
	 */
	public CommodityPriceDAO() {
	}

	/**
	 * Get commodity price profile given the category and product type code.
	 * 
	 * @param catCode commodity category code
	 * @param prodCode commodity product type code
	 * @return a list of commodity price
	 * @throws SearchDAOException on error searching the profile for commodity
	 *         price
	 */
	public ICommodityPrice[] getCommodityPriceProfile(String catCode, String prodCode, String ricType)
			throws SearchDAOException {
		String sql = constructCommodityPriceProfileSQL(catCode, prodCode, ricType);

		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processCommodityPriceProfileResulSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting commodity price's profile ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get feed group id.
	 * 
	 * @param feedType feed group type
	 * @return feed group id
	 * @throws SearchDAOException on error getting the feed group id
	 */
	public long getFeedGroupID(String feedType) throws SearchDAOException {
		String sql = SELECT_FEED_GROUP;

		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setString(1, feedType);
			ResultSet rs = dbUtil.executeQuery();
			if (rs.next()) {
				return rs.getLong(ICollateralDAOConstants.FEED_GROUP_ID);
			}
			return ICMSConstant.LONG_INVALID_VALUE;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting feed group id!", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get staging group id given a list of prices.
	 * 
	 * @param prices a list of ICommodityPrice objects
	 * @return price's group id
	 * @throws SearchDAOException on error searching the profile for commodity
	 *         price
	 */
	public long getCommodityPriceGroupID(ICommodityPrice[] prices, boolean isStaging) throws SearchDAOException {
		if ((prices == null) || (prices.length == 0)) {
			return ICMSConstant.LONG_INVALID_VALUE;
		}

		String sql = null;
		if (isStaging) {
			sql = constructStageCommodityPriceGroupIDSQL(prices);
		}
		else {
			sql = constructActualCommodityPriceGroupIDSQL(prices);
		}

		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processCommodityPriceGroupIDResultSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting staging group id: ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Helper method to construct query for getting commodity price's profile
	 * list.
	 * 
	 * @param catCode commodity category code
	 * @param prodCode commodity product type code
	 * @return sql query
	 */
	protected String constructCommodityPriceProfileSQL(String catCode, String prodCode, String ricType) {
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_COMMODITY_PRICE_PROFILE);
		buf.append(" and ");
		buf.append(PROFILE_COMMODITY_CATEGORY);
		if (catCode == null) {
			buf.append(" is null and ");
		}
		else {
			buf.append(" = '");
			buf.append(catCode);
			buf.append("' and ");
		}

		buf.append(PROFILE_PRODUCT_TYPE);
		if (prodCode == null) {
			buf.append(" is null");
		}
		else {
			buf.append(" = '");
			buf.append(prodCode);
			buf.append("'");
		}

		buf.append(" and ");
		buf.append(PROFILE_RIC_TYPE);
		if ("n".equals(ricType)) {
			buf.append(" = 'n'");
		}
		else {
			buf.append(" <> 'n'");
		}
		return buf.toString();
	}

	/**
	 * Helper method to construct query for getting staging price's group id
	 * given a list of commodity prices.
	 * 
	 * @param prices a list of ICommodityPrice objects
	 * @return sql query
	 */
	public String constructStageCommodityPriceGroupIDSQL(ICommodityPrice[] prices) {
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_STAGE_PRICE_GROUP_ID);
		buf.append(" where ");
		buf.append(PRICE_PROFILE_ID);
		buf.append(" in ");
		buf.append(getSQLList(prices));
		return buf.toString();
	}

	/**
	 * Helper method to construct query for getting price's group id given a
	 * list of prices.
	 * 
	 * @param prices a list of ICommodityPrice objects
	 * @return sql query
	 */
	public String constructActualCommodityPriceGroupIDSQL(ICommodityPrice[] prices) {
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_PRICE_GROUP_ID);
		buf.append(" where ");
		buf.append(PRICE_PROFILE_ID);
		buf.append(" in ");
		buf.append(getSQLList(prices));
		return buf.toString();
	}

	/**
	 * Gets the closed price and currency from commodity price feed.
	 * 
	 * @param profileId id of a commodity profile
	 * @return Amount object containing the latest price from the commodity
	 *         price feed and its currency
	 * @throws SearchDAOException
	 */
	public Amount getClosePrice(long profileId) throws SearchDAOException {
		// This method was created for CR141, but is no longer in use.
		// CR141 is now using "getCurrentPrice".

		String sql = SELECT_CLOSE_PRICE;

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, profileId);
			ResultSet rs = dbUtil.executeQuery();

			// process the return results
			Amount closePrice = null;
			while (rs.next()) {
				String currency = rs.getString("ClOSE_PRICE_CURRENCY");
				BigDecimal price = rs.getBigDecimal("CLOSE_PRICE");
				closePrice = new Amount(price, new CurrencyCode(currency));
			}
			return closePrice;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting close price for profile id: " + profileId, e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Gets the latest price (current price column) from commodity price feed
	 * only when update date of the price (column current_update_date) is
	 * today's date.
	 * 
	 * @param profileId id of a commodity profile
	 * @return Amount object containing the latest price from the commodity
	 *         price feed and its currency
	 * @throws SearchDAOException
	 */
	public Amount getTodayCurrentPrice(long profileId) throws SearchDAOException {
		// This method was created for CR141, but is no longer in use.
		// CR141 is now using "getCurrentPrice".
		// See UAT1.4 JIRA CMS-2485

		String sql = SELECT_SYSDATE_CURRENT_PRICE;

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, profileId);
			ResultSet rs = dbUtil.executeQuery();

			// process the return results
			Amount currentPrice = null;
			while (rs.next()) {
				String currency = rs.getString("CURRENT_PRICE_CURRENCY");
				BigDecimal price = rs.getBigDecimal("CURRENT_PRICE");
				currentPrice = new Amount(price, new CurrencyCode(currency));
			}
			return currentPrice;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting current price for profile id: " + profileId, e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Gets the current price and its currency from commodity price feed table.
	 * 
	 * @param profileId id of a commodity profile
	 * @return Amount object containing the latest price from the commodity
	 *         price feed and its currency
	 * @throws SearchDAOException
	 */
	public Amount getCurrentPrice(long profileId) throws SearchDAOException {

		String sql = SELECT_CURRENT_PRICE;

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, profileId);
			ResultSet rs = dbUtil.executeQuery();

			// process the return results
			Amount currentPrice = null;
			while (rs.next()) {
				String currency = rs.getString("CURRENT_PRICE_CURRENCY");
				BigDecimal price = rs.getBigDecimal("CURRENT_PRICE");
				currentPrice = new Amount(price, new CurrencyCode(currency));
			}
			return currentPrice;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting current price for profile id: " + profileId, e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * The date of when the price is first updated (to current price) is
	 * retreived. Currently, its only meant to be used in conjunction with
	 * getLatestPrice() (see above).
	 * 
	 * E.g: Date | Price ---------------- 10/11 | 12.00 11/11 | 11.00 12/11 |
	 * 11.00 13/11 | 12.00 14/11 | 12.00 15/11 | 12.00 <-- assuming this is
	 * today
	 * 
	 * The date retrieved will be 13/11, since that is when it was first updated
	 * to today's current price. (Currently when the price stays the same, it is
	 * assumed that there is NO update.)
	 * 
	 * @param profileId id of a commodity profile
	 * @return first update date of current price
	 */
	public Date getCurrentPriceFirstUpdateDate(long profileId) throws SearchDAOException {
		return getPriceFirstUpdateDate(profileId, SELECT_CURRENT_PRICE_FIRST_UPDATE_DATE);
	}

	/**
	 * The date of when the price is first updated (to current close price) is
	 * retreived. Returns null if the current close price is updated by batch
	 * job. (if close price is updated by batch job, the most recent record in
	 * the staging table will also not match the current close price, and as a
	 * result, the 2nd select query "SELECT MIN(sp2.group_id) ..." will return
	 * no rows), thus returning null to the application.
	 * 
	 * See getCurrentPriceFirstUpdateDate() method for the date retrieved logic.
	 * 
	 * @param profileId id of a commodity profile
	 * @return first update date of closed price
	 * @throws SearchDAOException execption during search
	 */
	public Date getClosePriceFirstUpdateDate(long profileId) throws SearchDAOException {
		return getPriceFirstUpdateDate(profileId, SELECT_CLOSE_PRICE_FIRST_UPDATE_DATE);
	}

	/**
	 * Helper method for getCurrentPriceFirstUpdateDate() &
	 * getClosePriceFirstUpdateDate() methods.
	 * 
	 * See getCurrentPriceFirstUpdateDate() method for the date retrieved logic.
	 * 
	 * @param profileId id of a commodity profile
	 * @param sql the sql to execute
	 * @return first update date of price (current or close, depending on the
	 *         sql passed in)
	 * @throws SearchDAOException execption during search
	 */
	protected Date getPriceFirstUpdateDate(long profileId, String sql) throws SearchDAOException {
		DefaultLogger.debug(this, "ProfileID : " + profileId);
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, profileId);
			ResultSet rs = dbUtil.executeQuery();

			// process the return results
			Date firstUpdateDate = null;
			while (rs.next()) {
				// reason for doing this, refer to java.sql.Timestamp javadoc
				Timestamp aTimestamp = rs.getTimestamp("FIRST_UDATE");
				if (aTimestamp != null) {
					firstUpdateDate = new Date(aTimestamp.getTime());
				}
				else {
					DefaultLogger.debug(this, "firstUpdateDate==null");
				}
			}
			return firstUpdateDate;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting first date for price for profile id: " + profileId, e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Helper method to process the result set of commodity price's profile.
	 * 
	 * @param rs result set
	 * @return a list of commodity prices
	 * @throws SQLException on error processing the result set
	 */
	private ICommodityPrice[] processCommodityPriceProfileResulSet(ResultSet rs) throws SQLException {
		ArrayList arrList = new ArrayList();

		while (rs.next()) {
			OBProfile profile = new OBProfile();
			profile.setProfileID(rs.getLong(PROFILE_PROFILE_ID));
			profile.setStatus(rs.getString(PROFILE_STATUS));
			profile.setReuterSymbol(rs.getString(PROFILE_RIC));
			profile.setCountryArea(rs.getString(PROFILE_COUNTRY_CODE));
			profile.setPriceType(rs.getString(PROFILE_PRICE_TYPE));
			profile.setCategory(rs.getString(PROFILE_COMMODITY_CATEGORY));
			profile.setProductType(rs.getString(PROFILE_PRODUCT_TYPE));
			profile.setProductSubType(rs.getString(PROFILE_PRODUCT_SUBTYPE));

			OBCommodityPrice price = new OBCommodityPrice();
			price.setProfileID(profile.getProfileID());
			price.setStatus(profile.getStatus());
			BigDecimal priceID = rs.getBigDecimal(PRICE_ID);
			if (priceID != null) {
				price.setCommodityPriceID(priceID.longValue());
			}
			price.setCommodityProfile(profile);
			arrList.add(price);
		}
		return (OBCommodityPrice[]) arrList.toArray(new OBCommodityPrice[0]);
	}

	/**
	 * Helper method to process the result set of commodity price group id.
	 * 
	 * @param rs result set
	 * @return staging group id
	 * @throws SQLException on error processing the result set
	 */
	private long processCommodityPriceGroupIDResultSet(ResultSet rs) throws SQLException {
		if (rs.next()) {
			BigDecimal bd = rs.getBigDecimal(MAX_PRICE_GROUP_ID);
			if (bd != null) {
				return bd.longValue();
			}
		}

		return ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Helper method to convert the array to a sql friendly string ( in Clause).
	 * 
	 * @param prices a list of ICommodityPrice objects
	 * @return String in clause sql
	 */
	private String getSQLList(ICommodityPrice[] prices) {
		if ((prices == null) || (prices.length == 0)) {
			return null;
		}

		StringBuffer buf = new StringBuffer();
		buf.append("(");

		for (int i = 0; i < prices.length; i++) {
			// buf.append("'");
			buf.append(prices[i].getProfileID());
			// buf.append("'");
			if (i != prices.length - 1) {
				buf.append(",");
			}
			else {
				buf.append(")");
			}
		}
		return buf.toString();
	}

	/**
	 * Helper method to clean up database resources.
	 * 
	 * @param dbUtil database utility object
	 * @throws SearchDAOException error in cleaning up DB resources
	 */
	private void finalize(DBUtil dbUtil) throws SearchDAOException {
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in cleaning up DB resources.");
		}
	}

	public boolean isRICTypeTransferable(long profileId) throws SearchDAOException {
		String sql = "select distinct commodity_price_id from cms_stage_cmdt_price where profile_id=?";
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, profileId);
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				DefaultLogger.debug(this, "Find price with profile : " + profileId);
				return false;
			}
			DefaultLogger.debug(this, "Cannot find price with profile : " + profileId);
			return true;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error isRICTypeTransferable: " + profileId, e);
		}
		finally {
			finalize(dbUtil);
		}
	}
}
