/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealDao
 *
 * Created on 4:58:34 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.bus;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.predeal.PreDealException;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 23, 2007 Time: 4:58:34 PM
 */
public class PreDealDao {
	private static final String selectSearchSQL = "SELECT * FROM PREDEAL_DATA_VW ";

	private static final String searchByCounterNameSql = "WHERE UPPER ( NAME ) LIKE ? ";

	private static final String searchByStockCodeSql = "WHERE UPPER ( TICKER ) LIKE ? ";

	private static final String searchByISINCodeSql = "WHERE UPPER ( ISIN_CODE ) LIKE ? ";

	private static final String searchByRICSql = "WHERE UPPER ( RIC ) LIKE ? ";

	private static final String searchByFeedSql = "WHERE FEED_ID = ? ";

	private static final String viewEarGroupSQL = "SELECT\n"
			+ "EARMARK_ID , EARMARK_GROUP_ID , FEED_ID , CUSTOMER_NAME , SOURCE_SYSTEM , SECURITY_ID ,\n"
			+ "AA_NUMBER ,  BRANCH_NAME , BRANCH_CODE , CIF_NO , ACCOUNT_NO , EARMARK_UNITS , EARMARKING_DATE ,\n"
			+ "EARMARK_STATUS , HOLDING_IND , RELEASE_STATUS , INFO_CORRECT_IND , INFO_INCORRECT_DETAILS ,\n"
			+ "WAIVE_APPROVE_IND , VERSION_TIME , STATUS\n" + "FROM CMS_EARMARK\n" + "WHERE FEED_ID = ? ";

	private static final String COLUMN_FEED_ID = "FEED_ID";

	private static final String COLUMN_INT_SUSPEND = "IS_INT_SUSPEND";

	private static final String COLUMN_SUSPEND = "IS_SUSPENDED";

	private static final String COLUMN_SHARE_STATUS = "SHARE_STATUS";

	private static final String COLUMN_STOCK_CODE = "STOCK_CODE";

	private static final String COLUMN_TICKER = "TICKER";

	private static final String COLUMN_NAME = "NAME";

	private static final String COLUMN_RIC = "RIC";

	private static final String COLUMN_CURRENY = "CURRENCY";

	private static final String COLUMN_UNIT_PRICE = "UNIT_PRICE";

	private static final String COLUMN_LAST_BATCH_UPDATE = "LAST_BATCH_UPDATE";

	private static final String COLUMN_CMS_ACTUAL_HOLDING = "CMS_ACTUAL_HOLDING";

	private static final String COLUMN_TOTAL_NO_OF_UNITS = "TOTAL_NO_OF_UNITS";

	private static final String COLUMN_EARMARK_HOLDING = "EARMARK_HOLDING";

	private static final String COLUMN_EARMARK_CURRENT = "EARMARK_CURRENT";

	private static final String COLUMN_ISIN_CODE = "ISIN_CODE";

	private static final String COLUMN_LISTEDSHARE_QUANTITY = "LISTEDSHARE_QUANTITY";

	private static final String COLUMN_EARMARK_GROUP_ID = "EARMARK_GROUP_ID";

	private static final String COLUMN_GROUP_TYPE = "GROUP_TYPE";

	private static final String COLUMN_EARMARK_ID = "EARMARK_ID";

	private static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";

	private static final String COLUMN_SOURCE_SYSTEM = "SOURCE_SYSTEM";

	private static final String COLUMN_SECURITY_ID = "SECURITY_ID";

	private static final String COLUMN_AA_NUMBER = "AA_NUMBER";

	private static final String COLUMN_BRANCH_NAME = "BRANCH_NAME";

	private static final String COLUMN_BRANCH_CODE = "BRANCH_CODE";

	private static final String COLUMN_CIF_NO = "CIF_NO";

	private static final String COLUMN_ACCOUNT_NO = "ACCOUNT_NO";

	private static final String COLUMN_EARMARK_UNITS = "EARMARK_UNITS";

	private static final String COLUMN_EARMARK_STATUS = "EARMARK_STATUS";

	private static final String COLUMN_EARMARKING_DATE = "EARMARKING_DATE";

	private static final String COLUMN_HOLDING_IND = "HOLDING_IND";

	private static final String COLUMN_RELEASE_STATUS = "RELEASE_STATUS";

	private static final String COLUMN_INFO_CORRECT_IND = "INFO_CORRECT_IND";

	private static final String COLUMN_INFO_INCORRECT_DETAILS = "INFO_INCORRECT_DETAILS";

	private static final String COLUMN_WAIVE_APPROVE_IND = "WAIVE_APPROVE_IND";

	private static final String COLUMN_VERSION_TIME = "VERSION_TIME";

	private static final String COLUMN_STATUS = "STATUS";

	private static final String COLUMN_MAX_COLL_CAP_FI = "MAX_COLL_CAP_FI";

	private static final String COLUMN_MAX_COLL_CAP_NON_FI = "MAX_COLL_CAP_NON_FI";

	private static final String COLUMN_QUOTA_COLL_CAP_FI = "QUOTA_COLL_CAP_FI";

	private static final String COLUMN_QUOTA_COLL_CAP_NON_FI = "QUOTA_COLL_CAP_NON_FI";

	private static final String COLUMN_IS_FI = "IS_FI";

	private static final String COLUMN_BOARD_TYPE = "BOARD_TYPE";

	private static final String COLUMN_EXPIRY_DATE = "EXPIRY_DATE";

	private static final String COLUMN_GROUP_STOCK_TYPE = "GROUP_STOCK_TYPE";

	private static final String COLUMN_STOCK_EXCHANGE_NAME = "STOCK_EXCHANGE_NAME";

	private static final String COLUMN_GROUP_SUBTYPE = "GROUP_SUBTYPE";

	private static final String COLUMN_COUNTRY_ISO_CODE = "COUNTRY_ISO_CODE";

	private static final String COLUMN_PAR_VALUE = "PAR_VALUE";

	private DBUtil dbUtil;

	public Collection searchByStockCode(String stockCode) {
		ArrayList list = new ArrayList();
		DefaultLogger.debug(this, "Now searching data by Stock Code : " + stockCode);

		try {
			if ((stockCode != null) && !stockCode.trim().equals("")) {
				stockCode = stockCode.toUpperCase() + '%';
			}

			String preDealSql = selectSearchSQL + searchByStockCodeSql;

			list = search(preDealSql, stockCode);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Error in searching data by stock type : " + stockCode, e);
		}

		DefaultLogger.debug(this, "Returning collection of size : " + list.size());

		return list;
	}

	public Collection searchByIsinCode(String isinCode) {
		ArrayList list = new ArrayList();
		DefaultLogger.debug(this, "Now searching data by isin code Code : " + isinCode);

		try {
			if ((isinCode != null) && !isinCode.trim().equals("")) {
				isinCode = isinCode.toUpperCase() + '%';
			}

			String preDealSql = selectSearchSQL + searchByISINCodeSql;

			list = search(preDealSql, isinCode);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Error in searching data by isin code : " + isinCode, e);
		}

		DefaultLogger.debug(this, "Returning collection of size : " + list.size());

		return list;
	}

	public Collection searchByRIC(String ric) {
		ArrayList list = new ArrayList();
		DefaultLogger.debug(this, "Now searching data by RIC : " + ric);
		try {
			if ((ric != null) && !ric.trim().equals("")) {
				ric = ric.toUpperCase() + '%';
			}

			String preDealSql = selectSearchSQL + searchByRICSql;

			list = search(preDealSql, ric);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Error in searching data by RIC : " + ric, e);
		}

		DefaultLogger.debug(this, "Returning collection of size : " + list.size());

		return list;
	}

	public Collection searchByCounterName(String name) {
		ArrayList list = new ArrayList();

		DefaultLogger.debug(this, "Now searching data by Counter Name : " + name);
		try {
			if ((name != null) && !name.trim().equals("")) {
				name = name.toUpperCase() + '%';
			}

			String preDealSql = selectSearchSQL + searchByCounterNameSql;

			list = search(preDealSql, name);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Error in searching data by Counter Name : " + name, e);
		}
		DefaultLogger.debug(this, "Returning collection of size : " + list.size());

		return list;
	}

	public PreDealSearchRecord searchByFeedId(String feedId) throws PreDealException {
		DefaultLogger.debug(this, " searchByFeedId  feedId : " + feedId);

		try {

			dbUtil = new DBUtil();
			dbUtil.setSQL(selectSearchSQL + searchByFeedSql);
			dbUtil.setString(1, feedId);

			DefaultLogger.debug(this, "PreDeal searchByFeedId sql : " + selectSearchSQL + searchByFeedSql);

			ResultSet rs = dbUtil.executeQuery();

			if (rs.next()) {
				PreDealSearchRecord record = new PreDealSearchRecord();
				Amount updatePrice = new Amount();
				Amount parValue = new Amount();
				PreDealSearchRecord stockExchangeName = null;

				record.setFeedId(rs.getLong(COLUMN_FEED_ID));
				// record.setEarMarkGroupId (rs.getLong
				// (COLUMN_EARMARK_GROUP_ID));
				record.setInSuspended("Y".equals(rs.getString(COLUMN_INT_SUSPEND)));
				record.setSuspended("Y".equals(rs.getString(COLUMN_SUSPEND)));
				record.setShareStatus(rs.getString(COLUMN_SHARE_STATUS) == null ? "" : rs
						.getString(COLUMN_SHARE_STATUS).trim());
				record.setStockCode(rs.getString(COLUMN_TICKER));
				record.setName(rs.getString(COLUMN_NAME));
				record.setRic(rs.getString(COLUMN_RIC));
				updatePrice.setCurrencyCode(rs.getString(COLUMN_CURRENY));
				updatePrice.setAmountAsBigDecimal(rs.getBigDecimal(COLUMN_UNIT_PRICE));
				record.setUpdatePrice(updatePrice);
				record.setLastUpdateDate(rs.getDate(COLUMN_LAST_BATCH_UPDATE));
				record.setGroupTotalUnits(rs.getLong(COLUMN_TOTAL_NO_OF_UNITS));
				record.setGroupEarmarkCurrent(rs.getLong(COLUMN_EARMARK_CURRENT));
				record.setGroupEarmarkHolding(rs.getLong(COLUMN_EARMARK_HOLDING));
				record.setGroupCmsActualHolding(rs.getLong(COLUMN_CMS_ACTUAL_HOLDING));
				record.setListedShareQuantity(rs.getLong(COLUMN_LISTEDSHARE_QUANTITY));
				record.setIsin_code(rs.getString(COLUMN_ISIN_CODE));
				record.setGroupTypeCode(rs.getString(COLUMN_GROUP_TYPE));

				// No longer getting from view, but from respective Source
				// System of Policy Cap
				// determined during Earmarking creation by Maker
				// record.setMaxCollCapFi (rs.getDouble
				// (COLUMN_MAX_COLL_CAP_FI));
				// record.setMaxCollCapNonFi (rs.getDouble
				// (COLUMN_MAX_COLL_CAP_NON_FI));
				// record.setQuotaCollCapFi (rs.getDouble
				// (COLUMN_QUOTA_COLL_CAP_FI));
				// record.setQuotaCollCapNonFi (rs.getDouble
				// (COLUMN_QUOTA_COLL_CAP_NON_FI));

				record.setIsFi("Y".equals(rs.getString(COLUMN_IS_FI)));
				record.setBoardType(rs.getString(COLUMN_BOARD_TYPE));
				record.setGroupStockType(rs.getString(COLUMN_GROUP_STOCK_TYPE));
				record.setExpiryDate(rs.getDate(COLUMN_EXPIRY_DATE));
				record.setStockExchangeName(rs.getString(COLUMN_STOCK_EXCHANGE_NAME));
				record.setStockExchangeCode(rs.getString(COLUMN_GROUP_SUBTYPE));
				record.setStockExchangeCountry(rs.getString(COLUMN_COUNTRY_ISO_CODE));
				parValue.setCurrencyCode(rs.getString(COLUMN_CURRENY));
				parValue.setAmountAsBigDecimal(rs.getBigDecimal(COLUMN_PAR_VALUE));
				record.setParValue(parValue);

				// if ( stockExchangeName == null )
				// {
				// stockExchangeName = getStockExchangeName ( record.getFeedId
				// (), record ) ;
				//
				// DefaultLogger.debug ( this , "stockExchangeName : " +
				// stockExchangeName ) ;
				// }

				return record;
			}
			else {
				DefaultLogger.debug(this, "No record found  for feedId " + feedId);
				return null;
				// throw new PreDealException("No record found");
			}
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Error in searching data by Feed Id : " + feedId, e);
			throw new PreDealException(e);
		}
		finally {
			close();
		}
	}

	public SearchResult viewEarGroup(SearchCriteria criteria) {
		EarMarkSearchCirteria earMarkCriteria = (EarMarkSearchCirteria) criteria;
		Collection col = new ArrayList();
		SearchResult result;
		ResultSet rs = null;
		try {
			String sql = viewEarGroupSQL;

			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, earMarkCriteria.getFeedId());

			DefaultLogger.debug(this, "View Ear Group SQL : " + sql);
			DefaultLogger.debug(this, "earMarkCriteria.getFeedId () : " + earMarkCriteria.getFeedId());

			 rs = dbUtil.executeQuery();

			while (rs.next()) {
				OBPreDeal ob = new OBPreDeal();

				ob.setEarMarkId(new Long(rs.getLong(COLUMN_EARMARK_ID)));
				// ob.setEarMarkGroupId (rs.getLong (COLUMN_EARMARK_GROUP_ID));
				ob.setFeedId(rs.getLong(COLUMN_FEED_ID));
				ob.setCustomerName(rs.getString(COLUMN_CUSTOMER_NAME));
				ob.setSourceSystem(rs.getString(COLUMN_SOURCE_SYSTEM));
				ob.setSecurityId(rs.getString(COLUMN_SECURITY_ID));
				ob.setAaNumber(rs.getString(COLUMN_AA_NUMBER));
				ob.setBranchName(rs.getString(COLUMN_BRANCH_NAME));
				ob.setBranchCode(rs.getString(COLUMN_BRANCH_CODE));
				ob.setCifNo(rs.getString(COLUMN_CIF_NO));
				ob.setAccountNo(rs.getString(COLUMN_ACCOUNT_NO));
				ob.setEarMarkStatus(rs.getString(COLUMN_EARMARK_STATUS));
				ob.setEarMarkUnits(rs.getLong(COLUMN_EARMARK_UNITS));
				ob.setEarMarkingDate(rs.getDate(COLUMN_EARMARKING_DATE));
				ob.setHoldingInd(rs.getBoolean(COLUMN_HOLDING_IND));
				ob.setReleaseStatus(rs.getString(COLUMN_RELEASE_STATUS));
				ob.setInfoCorrectInd(rs.getBoolean(COLUMN_INFO_CORRECT_IND));
				ob.setInfoIncorrectDetails(rs.getString(COLUMN_INFO_INCORRECT_DETAILS));
				ob.setWaiveApproveInd(rs.getBoolean(COLUMN_WAIVE_APPROVE_IND));
				ob.setVersionTime(rs.getLong(COLUMN_VERSION_TIME));
				ob.setStatus(rs.getBoolean(COLUMN_STATUS));

				col.add(ob);
			}
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Erroring retrieving ear mark group data", e);
		}
		finalize(dbUtil, rs);
		result = new SearchResult(1, 1, col.size(), col);

		DefaultLogger.debug(this, "Collection size is : " + col.size());

		return result;
	}

	public SearchResult viewEarMark(SearchCriteria criteria) {
		return null;
	}

	private ArrayList search(String sql, String param) throws Exception {
		ArrayList list = new ArrayList();

		dbUtil = new DBUtil();
		dbUtil.setSQL(sql);
		dbUtil.setString(1, param);

		DefaultLogger.debug(this, "PreDeal search sql : " + sql);
		DefaultLogger.debug(this, "param : " + param);

		ResultSet rs = dbUtil.executeQuery();

		while (rs.next()) {
			PreDealSearchRecord record = new PreDealSearchRecord();
			Amount updatePrice = new Amount();
			PreDealSearchRecord stockExchangeName = null;

			record.setFeedId(rs.getLong(COLUMN_FEED_ID));
			// record.setEarMarkGroupId (rs.getLong (COLUMN_EARMARK_GROUP_ID));
			record.setInSuspended("Y".equals(rs.getString(COLUMN_INT_SUSPEND)));
			record.setSuspended("Y".equals(rs.getString(COLUMN_SUSPEND)));
			record.setShareStatus(rs.getString(COLUMN_SHARE_STATUS) == null ? "" : rs.getString(COLUMN_SHARE_STATUS)
					.trim());
			record.setStockCode(rs.getString(COLUMN_TICKER));
			record.setName(rs.getString(COLUMN_NAME));
			record.setRic(rs.getString(COLUMN_RIC));
			updatePrice.setCurrencyCode(rs.getString(COLUMN_CURRENY));
			updatePrice.setAmountAsBigDecimal(rs.getBigDecimal(COLUMN_UNIT_PRICE));
			record.setUpdatePrice(updatePrice);
			record.setLastUpdateDate(rs.getDate(COLUMN_LAST_BATCH_UPDATE));
			record.setGroupTotalUnits(rs.getLong(COLUMN_TOTAL_NO_OF_UNITS));
			record.setGroupEarmarkCurrent(rs.getLong(COLUMN_EARMARK_CURRENT));
			record.setGroupEarmarkHolding(rs.getLong(COLUMN_EARMARK_HOLDING));
			record.setGroupCmsActualHolding(rs.getLong(COLUMN_CMS_ACTUAL_HOLDING));
			record.setListedShareQuantity(rs.getLong(COLUMN_LISTEDSHARE_QUANTITY));
			record.setIsin_code(rs.getString(COLUMN_ISIN_CODE));
			record.setGroupTypeCode(rs.getString(COLUMN_GROUP_TYPE));

			// No longer getting from view, but from respective Source System of
			// Policy Cap
			// determined during Earmarking creation by Maker
			// record.setMaxCollCapFi (rs.getDouble (COLUMN_MAX_COLL_CAP_FI));
			// record.setMaxCollCapNonFi (rs.getDouble
			// (COLUMN_MAX_COLL_CAP_NON_FI));
			// record.setQuotaCollCapFi (rs.getDouble
			// (COLUMN_QUOTA_COLL_CAP_FI));
			// record.setQuotaCollCapNonFi (rs.getDouble
			// (COLUMN_QUOTA_COLL_CAP_NON_FI));
			record.setIsFi("Y".equals(rs.getString(COLUMN_IS_FI)));
			record.setBoardType(rs.getString(COLUMN_BOARD_TYPE));
			record.setGroupStockType(rs.getString(COLUMN_GROUP_STOCK_TYPE));
			record.setExpiryDate(rs.getDate(COLUMN_EXPIRY_DATE));
			record.setStockExchangeName(rs.getString(COLUMN_STOCK_EXCHANGE_NAME));
			record.setStockExchangeCode(rs.getString(COLUMN_GROUP_SUBTYPE));
			record.setStockExchangeCountry(rs.getString(COLUMN_COUNTRY_ISO_CODE));

			// if ( stockExchangeName == null )
			// {
			// stockExchangeName = getStockExchangeName ( record.getFeedId (),
			// record ) ;
			//
			// DefaultLogger.debug ( this , "stockExchangeName : " +
			// stockExchangeName ) ;
			// //DefaultLogger.debug ( this ,
			// "CommonDataSingleton.getInstance ().getCodeCategoryLabelByValue ( ShareCounterConstants.STOCK_EXCHANGE , stockExchangeName ) : "
			// + CommonDataSingleton.getInstance ().getCodeCategoryLabelByValue
			// ( ShareCounterConstants.STOCK_EXCHANGE , stockExchangeName ) ) ;
			// }

			// record.setStockExchangeName ( CommonDataSingleton.getInstance
			// ().getCodeCategoryLabelByValue (
			// ShareCounterConstants.STOCK_EXCHANGE , stockExchangeName ) ) ;

			list.add(record);
		}

		close();

		return list;
	}

	private PreDealSearchRecord getStockExchangeName(long feedId, PreDealSearchRecord record) throws Exception {
		DBUtil db = new DBUtil();
		String sql = "SELECT STOCK_EXCHANGE_NAME, GROUP_SUBTYPE, COUNTRY_ISO_CODE FROM CMS_FEED_GROUP , CMS_PRICE_FEED, CMS_STOCK_EXCHANGE WHERE CMS_PRICE_FEED.FEED_ID = ? AND CMS_FEED_GROUP.FEED_GROUP_ID = CMS_PRICE_FEED.FEED_GROUP_ID AND CMS_FEED_GROUP.GROUP_SUBTYPE = CMS_STOCK_EXCHANGE.STOCK_EXCHANGE ";
		String returnStr = null;

		db.setSQL(sql);
		db.setLong(1, feedId);

		ResultSet rs = db.executeQuery();

		if (rs.next()) {
			// returnStr = rs.getString ( 1 ) ;
			record.setStockExchangeName(rs.getString(1));
			record.setStockExchangeCode(rs.getString(2));
			record.setStockExchangeCountry(rs.getString(3));
		}

		db.close();

		return record;
	}

	private void rollback() {
		try {
			if (dbUtil != null) {
				dbUtil.rollback();
			}
		}
		catch (Exception e) {
			// do nothing
		}
	}

	private void commit() {
		try {
			if (dbUtil != null) {
				dbUtil.commit();
			}
		}
		catch (Exception e) {
			// do nothing
		}
	}

	private void close() {
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			// do nothing
		}
	}

	private void finalize(DBUtil dbUtil, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
