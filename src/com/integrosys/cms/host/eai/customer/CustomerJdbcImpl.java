package com.integrosys.cms.host.eai.customer;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.AbstractDBUtil;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.host.eai.core.IEaiConstant;

/**
 * @author allen
 * 
 */
public class CustomerJdbcImpl {

	public final synchronized boolean performSearchByCIF(String msgRefNo, String cifNo, String cifSource)
			throws SearchDAOException {

		AbstractDBUtil dbUtil = null;
		try {

			dbUtil = getDBUtil();
			// SID SEARCHTYPE TOTALFOUND STATUS
			// ERRMSG START_TS STOP_TS SEARCH_TEXT
			// DBKEY

			String sql = "insert into SCI_SEARCH_CUST "
					+ "(SEARCH_CUST_ID, SID, CIFID, CUSTOMERNAMESHORT, CUSTOMERNAMELONG, IDNUMBER,     BIRTHDATE"
					+
					// ", ADDRESS" +
					") "
					//For Db2
//					+ " select cast(? as BIGINT)  + (next value for SEARCH_SEQ),Cast(? as varchar(20)) ,mp.lmp_le_id , mp.LMP_SHORT_NAME,mp.LMP_LONG_NAME,LMP_INC_NUM_TEXT , LMP_INC_DATE "
					//For Oracle
					+ " select cast(? as Number)  + (next value for SEARCH_SEQ),Cast(? as varchar(20)) ,mp.lmp_le_id , mp.LMP_SHORT_NAME,mp.LMP_LONG_NAME,LMP_INC_NUM_TEXT , LMP_INC_DATE "
					// + "(select cast(LRA_ADDR_LINE_1||' '||LRA_ADDR_LINE_2 as
					// varchar(200)) from SCI_LE_REG_ADDR where
					// LRA_TYPE_VALUE='REGISTERED' and
					// CMS_LE_MAIN_PROFILE_ID=mp.CMS_LE_MAIN_PROFILE_ID fetch
					// first rows only ) as Address"
					+ " from SCI_LE_MAIN_PROFILE  mp " + " where mp.lmp_le_id=? and mp.SOURCE_ID=? "
					// For DB2
//					+ " fetch first 10 rows only ";
					// For Oracle
					+ " and rownum<=10 ";

			DefaultLogger.debug(this, sql);

			dbUtil.setSQL(sql);
			dbUtil.setLong(1, createPrefix());
			dbUtil.setString(2, msgRefNo);
			dbUtil.setString(3, cifNo);
			dbUtil.setString(4, cifSource);

			int count = dbUtil.executeUpdate();

			// Result Found
			return count > 0;

		}
		catch (Exception e) {
			throw new SearchDAOException("Unable to perform performSearchByCIF: " + e.toString());
		}
		finally {
			finalize(dbUtil);
		}

	}

	/**
	 * @param SID
	 * @param searchType
	 * @throws SearchDAOException
	 */
	public final synchronized void insertSearch(String SID, String searchType, String searchTxt)
			throws SearchDAOException {
		insertSearch(SID, searchType, searchTxt, IEaiConstant.STAT_PROCESSING);
	}

	/**
	 * @param SID
	 * @param searchType
	 * @param searchTxt
	 * @param status
	 * @throws SearchDAOException
	 */
	public final synchronized void insertSearch(String SID, String searchType, String searchTxt, String status)
			throws SearchDAOException {
		DBUtil dbUtil = null;
		try {

			dbUtil = getDBUtil();
			String sql = "insert into SCI_SEARCH(SID,SEARCHTYPE,STATUS,SEARCH_TEXT,START_TS) " + "values (?,?,?,?,?)";

			dbUtil.setSQL(sql);

			dbUtil.setString(1, SID);
			dbUtil.setString(2, searchType);
			dbUtil.setString(3, status);

			dbUtil.setString(4, searchTxt);
			dbUtil.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));

			dbUtil.executeUpdate();

		}
		catch (Exception e) {
			throw new SearchDAOException("Unable to perform insertSearch: " + e.toString());
		}
		finally {
			finalize(dbUtil);
		}

	}
	
	public final synchronized void updateSearch(String SID, long totalFound, String dbkey)throws SearchDAOException 
	{
		DBUtil dbUtil = null;
		try {

			dbUtil = getDBUtil();
			// SID SEARCHTYPE TOTALFOUND STATUS
			// ERRMSG START_TS STOP_TS SEARCH_TEXT
			// DBKEY

			String sql = "update SCI_SEARCH set ,TOTALFOUND=?,,STOP_TS=?,DBKey=? " + " where SID=?";
			dbUtil.setSQL(sql);

			dbUtil.setLong(2, totalFound);

			dbUtil.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));

			dbUtil.setString(5, StringUtils.defaultString(dbkey));

			dbUtil.setString(6, SID);

			dbUtil.executeUpdate();

		}
		catch (Exception e) {
			throw new SearchDAOException("Unable to perform updateSearch: " + e.toString());
		}
		finally {
			finalize(dbUtil);
		}
	}
	
	/*public final synchronized void updateSearch(String SID, long totalFound, String status, String errMsg, String dbkey)
			throws SearchDAOException {

		DBUtil dbUtil = null;
		try {

			dbUtil = getDBUtil();
			// SID SEARCHTYPE TOTALFOUND STATUS
			// ERRMSG START_TS STOP_TS SEARCH_TEXT
			// DBKEY

			String sql = "update SCI_SEARCH set STATUS=?,TOTALFOUND=?,ERRMSG=?,STOP_TS=?,DBKey=? " + " where SID=?";
			dbUtil.setSQL(sql);

			dbUtil.setString(1, status);

			dbUtil.setLong(2, totalFound);

			dbUtil.setString(3, errMsg);

			dbUtil.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));

			dbUtil.setString(5, StringUtils.defaultString(dbkey));

			dbUtil.setString(6, SID);

			dbUtil.executeUpdate();

		}
		catch (Exception e) {
			throw new SearchDAOException("Unable to perform updateSearch: " + e.toString());
		}
		finally {
			finalize(dbUtil);
		}

	}*/

	private DBUtil getDBUtil() throws DBConnectionException, SQLException {
		return new DBUtil();
	}

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

	private long createPrefix() throws Exception {
		String prefix = DateUtil.formatTime(DateUtil.getDate(), "yyyyMMdd") + "000000";

		return Long.parseLong(prefix);
	}

}
