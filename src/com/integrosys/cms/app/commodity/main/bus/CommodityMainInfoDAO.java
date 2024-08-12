/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/CommodityMainInfoDAO.java,v 1.6 2004/07/21 02:39:57 cchen Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Mar 30, 2004 Time:
 * 11:40:16 AM To change this template use File | Settings | File Templates.
 */
public class CommodityMainInfoDAO {
	private DBUtil dbUtil;

	public CommodityMainInfoDAO() {
	}

	public SearchResult searchCommodityMainInfosPersist(CommodityMainInfoSearchCriteria criteria) throws SQLException {
		DefaultLogger.debug(this, "executing : searchCommodityMainInfosPersist(CommodityMainInfoSearchCriteria) ");
		SearchResult result = null;
		try {

			return result;

		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SQLException("SQLException in getCCTask : " + ex);
			}
		}
	}

	public long getGroupIDForType(String infoType) throws SQLException {
		long groupID = -1;
		try {
			String tableName = "";
			String sql = "";
			if (infoType.equals(ICommodityMainInfo.INFO_TYPE_TITLEDOC)) {
				tableName = "CMS_TITLEDOC_TYPE";
				sql = " select distinct GROUP_ID as GROUP_ID " + " from  " + tableName
						+ " where STATUS='A' or STATUS='ACTIVE'";
			}
			else if (infoType.equals(ICommodityMainInfo.INFO_TYPE_WAREHOUSE)) {
				tableName = "CMS_CMDT_WRHSE";
				sql = " select distinct GROUP_ID as GROUP_ID " + " from  " + tableName
						+ " where STATUS='A' or STATUS='ACTIVE'";
			}
			else if (infoType.equals(ICommodityMainInfo.INFO_TYPE_PROFILE)) {
				tableName = "CMS_CMDT_PROFILE";
				sql = " select distinct GROUP_ID as GROUP_ID " + " from  " + tableName
						+ " where STATUS='A' or STATUS='ACTIVE'";
			}
			else if (infoType.equals(ICommodityMainInfo.INFO_TYPE_PROFILE_STAGING)) {
				tableName = "CMS_STAGE_CMDT_PROFILE";
				sql = " select distinct GROUP_ID as GROUP_ID " + " from  " + tableName;
			}// add more types here like price, unit etc.
			else {
				throw new SQLException("Invalid Info type '" + infoType + "'");
			}

			dbUtil = new DBUtil();

			dbUtil.setSQL(sql);

			ResultSet rs = dbUtil.executeQuery();

			if (rs.next()) {
				groupID = rs.getLong("GROUP_ID");
			}

		}
		catch (Exception ex) {
			throw new SQLException("SQLException in getCCTask : " + ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SQLException("SQLException in getCCTask : " + ex);
			}
		}
		return groupID;
	}

	public long getGroupIDForWarehouseByCountry(String countryCode) throws SQLException {
		long groupID = -1;
		try {
			String tableName = "CMS_CMDT_WRHSE";

			String sql = " select distinct GROUP_ID as GROUP_ID " + " from  " + tableName
					+ " where (STATUS='A' or STATUS='ACTIVE') and COUNTRY_CODE='" + countryCode + "'";

			dbUtil = new DBUtil();

			dbUtil.setSQL(sql);

			ResultSet rs = dbUtil.executeQuery();

			if (rs.next()) {
				groupID = rs.getLong("GROUP_ID");
			}

		}
		catch (Exception ex) {
			throw new SQLException("SQLException in getCCTask : " + ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SQLException("SQLException in getCCTask : " + ex);
			}
		}
		return groupID;
	}

	public long getGroupIDForStagingWarehouseByCountry(String countryCode) throws SQLException {
		long groupID = -1;
		try {
			String tableName = "CMS_STAGE_CMDT_WRHSE";

			String sql = " select distinct GROUP_ID as GROUP_ID " + " from  " + tableName
					+ " where (STATUS='A' or STATUS='ACTIVE') and COUNTRY_CODE='" + countryCode + "'";

			dbUtil = new DBUtil();

			dbUtil.setSQL(sql);

			ResultSet rs = dbUtil.executeQuery();

			if (rs.next()) {
				groupID = rs.getLong("GROUP_ID");
			}

			DefaultLogger.error(this, "ddddddddddddddddddddddddddddddddddd");
			DefaultLogger.error(this, "ddddddddddddddddddddddddddddddddddd");
			DefaultLogger.error(this, "ddddddddddddddddddddddddddddddddddd");
			DefaultLogger.error(this, "ddddddddddddddddddddddddddddddddddd");
			DefaultLogger.error(this, "ddddddddddddddddddddddddddddddddddd");
			DefaultLogger.error(this, "ddddddddddddddddddddddddddddddddddd");
			DefaultLogger.error(this, "ddddddddddddddddddddddddddddddddddd");
			DefaultLogger.error(this, "" + groupID);
			DefaultLogger.error(this, "ddddddddddddddddddddddddddddddddddd");
			DefaultLogger.error(this, "ddddddddddddddddddddddddddddddddddd");
			DefaultLogger.error(this, "ddddddddddddddddddddddddddddddddddd");
			DefaultLogger.error(this, "ddddddddddddddddddddddddddddddddddd");
			DefaultLogger.error(this, "ddddddddddddddddddddddddddddddddddd");
			DefaultLogger.error(this, "ddddddddddddddddddddddddddddddddddd");
			DefaultLogger.error(this, "ddddddddddddddddddddddddddddddddddd");
			DefaultLogger.error(this, "ddddddddddddddddddddddddddddddddddd");

		}
		catch (Exception ex) {
			throw new SQLException("SQLException in getCCTask : " + ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SQLException("SQLException in getCCTask : " + ex);
			}
		}

		return groupID;
	}

	public Map getAllProductSubTypesByCategoryAndProductType(String categoryCode, String prodyctType)
			throws SQLException {
		HashMap subTypeMap = null;
		try {
			String sql = " select distinct " + PROFILE_ID + ", " + PROFILE_PRODUCT_SUB_TYPE + " from  "
					+ COMMODITY_PROFILE_TABLE_NAME + " where (STATUS='A' or STATUS='ACTIVE') and " + " CATEGORY='"
					+ categoryCode + "' and PRODUCT_TYPE='" + prodyctType + "'";

			dbUtil = new DBUtil();

			dbUtil.setSQL(sql);

			ResultSet rs = dbUtil.executeQuery();

			subTypeMap = new HashMap();
			while (rs.next()) {
				subTypeMap.put(rs.getString(PROFILE_ID), rs.getString(PROFILE_PRODUCT_SUB_TYPE));
			}

		}
		catch (Exception ex) {
			throw new SQLException("SQLException in getCCTask : " + ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SQLException("SQLException in getCCTask : " + ex);
			}

		}
		return subTypeMap;
	}

	public static final String COMMODITY_PROFILE_TABLE_NAME = "CMS_CMDT_PROFILE";

	public static final String PROFILE_ID = "PROFILE_ID";

	public static final String PROFILE_PRODUCT_SUB_TYPE = "PRODUCT_SUB_TYPE";

}
