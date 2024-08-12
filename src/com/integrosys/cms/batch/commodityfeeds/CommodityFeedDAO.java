/*
 * Created on Aug 11, 2004
 *
 */
package com.integrosys.cms.batch.commodityfeeds;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.AbstractDBUtil;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author Chen Cheng
 */
public class CommodityFeedDAO {
	private AbstractDBUtil dbUtil;

	public List getFeedsRIC() throws SearchDAOException {
		List feedList = new ArrayList();
		String feedSql = "select * from CMS_CMDT_PROFILE a where ric_type <> 'n'";
		OBCommodityFeed feed = null;
		try {
			dbUtil = getDBUtil();
			dbUtil.setSQL(feedSql);
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				feed = new OBCommodityFeed();
				feed.setRic(rs.getString("RIC"));
				feed.setProfileID(rs.getLong("PROFILE_ID"));
				feedList.add(feed);
			}
			rs.close();
			return feedList;
		}
		catch (DBConnectionException e) {
			DefaultLogger.debug(this, "In getFeeds " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "In getFeeds " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		finally {
			finalize(dbUtil);
		}
	}

	public int updateFeeds(List feedList) throws SearchDAOException {

		int noOfRecUpdated = 0;
		try {
			dbUtil = getDBUtil();
			if ((feedList != null) && (feedList.size() > 0)) {
				for (int index = 0; index < feedList.size(); index++) {
					dbUtil.setSQL(getUpdateFeedSql());
					dbUtil.setDouble(1, ((OBCommodityFeed) (feedList.get(index))).getLastPrice());
					dbUtil.setDate(2, new java.sql.Date(((OBCommodityFeed) (feedList.get(index))).getLastDate()
							.getTime()));
					dbUtil.setDouble(3, ((OBCommodityFeed) (feedList.get(index))).getCurrentPrice());
					dbUtil.setDate(4, new java.sql.Date(((OBCommodityFeed) (feedList.get(index))).getCurrentDate()
							.getTime()));
					dbUtil.setString(5, ((OBCommodityFeed) (feedList.get(index))).getCurrency());
					dbUtil.setString(6, ((OBCommodityFeed) (feedList.get(index))).getCurrency());
					dbUtil.setLong(7, ((OBCommodityFeed) (feedList.get(index))).getProfileID());
					DefaultLogger
							.info(this, "last price : " + ((OBCommodityFeed) (feedList.get(index))).getLastPrice());
					int rowsUpdated = dbUtil.executeUpdate();
					if (rowsUpdated > 0) {
						noOfRecUpdated++;
					}
					DefaultLogger.info(this, "updated row number : " + noOfRecUpdated);
				}
			}
		}
		catch (DBConnectionException e) {
			DefaultLogger.debug(this, "In getFeeds " + e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "In getFeeds " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			finalize(dbUtil);
		}
		return noOfRecUpdated;
	}

	private String getUpdateFeedSql() {
		return "update CMS_CMDT_PRICE set CLOSE_PRICE=?, CLOSE_UPDATE_DATE=?, CURRENT_PRICE=?, "
				+ "CURRENT_UPDATE_DATE=?, CLOSE_PRICE_CURRENCY=?, CURRENT_PRICE_CURRENCY=? " + "where PROFILE_ID=?";
	}

	/*
	 * private String getDummySql() { return "select * from CMS_CMDT_PRICE"; }
	 */

	/**
	 * returns the list of currencies
	 * 
	 * @return List
	 */
	public List getCurrencies() throws SearchDAOException {
		final String sql = "SELECT CUR_CRRNCY_ISO_CODE FROM SCI_CURRENCY";
		try {
			dbUtil = getDBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			List results = processResultSet(rs);
			rs.close();
			return results;
		}
		catch (SQLException e) {
			DefaultLogger.error(this, "Exception caught! " + e);
		}
		catch (DBConnectionException e) {
			DefaultLogger.error(this, "Exception caught! " + e);
		}
		catch (NoSQLStatementException e) {
			DefaultLogger.error(this, "Exception caught! " + e);
		}
		finally {
			finalize(dbUtil);
		}
		return null;
	}

	private List processResultSet(ResultSet rs) throws SQLException {
		if (rs == null) {
			return Collections.EMPTY_LIST;
		}
		List result = new ArrayList();
		while (rs.next()) {
			result.add(rs.getString(1));
		}
		return result;

	}

	/**
	 * Helper method to clean up database resources.
	 * @param dbUtil database utility object
	 * @throws com.integrosys.base.businfra.search.SearchDAOException error in
	 *         cleaning up DB resources
	 */
	private static void finalize(AbstractDBUtil dbUtil) throws SearchDAOException {
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in cleaning up DB resources: " + e.toString());
		}
	}

	private AbstractDBUtil getDBUtil() throws DBConnectionException, SQLException {
		return new DBUtil();
	}

}
