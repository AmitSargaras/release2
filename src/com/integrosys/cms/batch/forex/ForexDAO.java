/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/forex/ForexDAO.java,v 1.10 2006/05/18 08:08:23 jitendra Exp $
 */
package com.integrosys.cms.batch.forex;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.AbstractDBUtil;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Purpose: A DAO object to upload the forex rates data into the database
 * Description:
 * 
 * @author $jtan$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 */
// : To change the code to load in batch mode using JDBC2.0
// Job can only insert 95 records as it violates Oracle max_cursor setting
// Consider addBatch() and executeBatch() in JDBC2.0
public class ForexDAO {
	// Declare database information
	public static final String FOREX_TABLE = "CMS_FOREX";

	public static final String STAGE_FOREX_TABLE = "CMS_STAGE_FOREX";

	public static final String FOREX_SEQUENCE = "CMS_FOREX_SEQ";

	public static final String CURRENCY_TABLE = "CURRENCY";

	public static final long FEED_GROUP_ID = 1007;

	// : To remove once batch is implemented
	public static final int SQL_LIMIT = 95; // db open_cursor limit

	private AbstractDBUtil dbUtil;

	/**
	 * This method inserts the rates records directly into the forex table
	 * @param rates - a list of forex value objects
	 */
	public void storeRates(ArrayList rates) throws SearchDAOException {

		DefaultLogger.info(this, "Entering method storeRates");
		OBForex rec;
		try {
			dbUtil = getDBUtil();
			SCBOracleSequencer seq = new SCBOracleSequencer();
			String sql = prepareInsertStmt();
			String stgSql = prepareStagingInsertStmt();
			// : Remove SQL_LIMIT
			for (int i = 0; i < SQL_LIMIT; i++) {
				rec = ((OBForex) rates.get(i));
				String stgId = seq.getSeqNum(FOREX_SEQUENCE);
				dbUtil.setSQL(sql);
				dbUtil.setString(1, rec.getFromCurrency());
				dbUtil.setInt(2, Integer.parseInt(seq.getSeqNum(FOREX_SEQUENCE)));
				dbUtil.setString(3, rec.getToCurrency());
				dbUtil.setDouble(4, rec.getRate());
				dbUtil.setDate(5, rec.getSQLEffectiveDate());
				dbUtil.setInt(6, rec.getUnit());
				dbUtil.setLong(7, FEED_GROUP_ID);
				dbUtil.setLong(8, Long.parseLong(stgId));
				dbUtil.setDouble(9, rec.getRate());
				dbUtil.setInt(10, rec.getUnit());
				dbUtil.executeUpdate();

				// dbUtil.close();
				// dbUtil = getDBUtil();

				dbUtil.setSQL(stgSql);
				dbUtil.setString(1, rec.getFromCurrency());
				dbUtil.setInt(2, Integer.parseInt(stgId));
				dbUtil.setString(3, rec.getToCurrency());
				dbUtil.setDouble(4, rec.getRate());
				dbUtil.setDate(5, rec.getSQLEffectiveDate());
				dbUtil.setInt(6, rec.getUnit());
				dbUtil.setLong(7, FEED_GROUP_ID);
				dbUtil.setLong(8, Long.parseLong(stgId));
				dbUtil.setDouble(9, rec.getRate());
				dbUtil.setInt(10, rec.getUnit());
				dbUtil.executeUpdate();
			}
			DefaultLogger.info(this, "Committing all records");
		}
		catch (DBConnectionException DBCE) {
			DefaultLogger.error(this, "In storeRates " + DBCE.getMessage());
			DBCE.printStackTrace();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error inserting rates data " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Updates the rates data in the forex table. The data is updated using the
	 * currency code (DB field: forex.to_Currency) as the reference. <p/>
	 * However, if the rate currency is not found in the forex table, it could
	 * be a new rate record which was not captured previously. A check will be
	 * made to ensure that it is a valid currency before it gets inserted into
	 * the forex table.
	 * 
	 * @param rates - a list of forex rates value objects
	 */
	public int updateRates(ArrayList rates) throws SearchDAOException {

		DefaultLogger.info(this, "Entering method updateRates");
		OBForex rec;
		boolean insertToHistory = false;
		int noOfRecUpdated = 0;
		try {
			SCBOracleSequencer seq = null;
			int seqId;
			dbUtil = getDBUtil();
			// : Remove SQL_LIMIT
			for (int i = 0; i < rates.size(); i++) {
				rec = ((OBForex) rates.get(i));
				String sql = prepareUpdateStmt();
				dbUtil.setSQL(sql);
				dbUtil.setString(1, rec.getFromCurrency());
				dbUtil.setDouble(2, rec.getRate());
				dbUtil.setDouble(3, rec.getRate());
				dbUtil.setDate(4, rec.getSQLEffectiveDate());
				dbUtil.setString(5, rec.getToCurrency());
				dbUtil.setLong(6, FEED_GROUP_ID);
				DefaultLogger.info(this, "Updating currency " + rec.getToCurrency());
				int count = dbUtil.executeUpdate();

				if (count > 0) {
					noOfRecUpdated = 1;
				}

				if (count == 0) {
					DefaultLogger.info(this, "Currency " + rec.getToCurrency() + " does not exist in forex table");
					DefaultLogger.info(this, "Verify that rate " + rec.getToCurrency()
							+ " exist in Master Currency table");

					String sqlCurrencyExist = "SELECT CURRENCY_ISO_CODE FROM " + CURRENCY_TABLE
							+ " WHERE CURRENCY_ISO_CODE = '" + rec.getToCurrency() + "'";

					dbUtil.setSQL(sqlCurrencyExist);
					ResultSet rs = dbUtil.executeQuery();
					boolean currencyExist = false;
					while (rs.next()) {
						currencyExist = true;
						DefaultLogger.info(this, "Currency rate " + rec.getToCurrency()
								+ " exists in Master Currency Table");
					}
					if (rs != null) {
						rs.close();
					}

					if (currencyExist) {
						// append it as a new record to the forex table
						if (seq == null) {
							seq = new SCBOracleSequencer();
						}
						String stgId = seq.getSeqNum(FOREX_SEQUENCE);
						sql = prepareInsertStmt();
						dbUtil.setSQL(sql);
						dbUtil.setString(1, rec.getFromCurrency());
						seqId = Integer.parseInt(seq.getSeqNum(FOREX_SEQUENCE));
						dbUtil.setInt(2, seqId);
						dbUtil.setString(3, rec.getToCurrency());
						dbUtil.setDouble(4, rec.getRate());
						dbUtil.setDate(5, rec.getSQLEffectiveDate());
						dbUtil.setInt(6, rec.getUnit());
						dbUtil.setLong(7, FEED_GROUP_ID);
						dbUtil.setLong(8, Long.parseLong(stgId));
						dbUtil.setDouble(9, rec.getRate());
						dbUtil.setInt(10, rec.getUnit());

						DefaultLogger.info(this, "Adding currency " + rec.getToCurrency() + " into forex table");

						int rowsInserted = dbUtil.executeUpdate();

						if (rowsInserted > 0) {
							insertToHistory = true;
							noOfRecUpdated = 1;
						}
					}
					else {
						DefaultLogger.warn(this, "CurrencyCode " + rec.getToCurrency()
								+ " does not exist in Master Currency Table" + " skip.....");
					}

				}
				else {
					insertToHistory = true;
				}

				if (insertToHistory) {
					// insert into history...
					if (seq == null) {
						seq = new SCBOracleSequencer();
					}
					String stgId = seq.getSeqNum(ICMSConstant.SEQUENCE_FEED_FOREX_HISTORY);
					seqId = Integer.parseInt(seq.getSeqNum(ICMSConstant.SEQUENCE_FEED_FOREX_HISTORY));

					dbUtil.setSQL(prepareInsertToHistoryStmt());
					dbUtil.setString(1, rec.getFromCurrency());
					dbUtil.setInt(2, seqId);
					dbUtil.setString(3, rec.getToCurrency());
					dbUtil.setDouble(4, rec.getRate());
					dbUtil.setDate(5, rec.getSQLEffectiveDate());
					dbUtil.setInt(6, rec.getUnit());
					dbUtil.setLong(7, FEED_GROUP_ID);
					dbUtil.setLong(8, Long.parseLong(stgId));
					dbUtil.setDouble(9, rec.getRate());
					dbUtil.setInt(10, rec.getUnit());
					int rowsInserted = dbUtil.executeUpdate();
					if (rowsInserted > 0) {
						noOfRecUpdated = 1;
					}
					DefaultLogger.info(this, "Inserting records in history records");
				}
			}
			// _rm.commitConnection(this);
			DefaultLogger.info(this, "Committing all records");
			DefaultLogger.info(this, "Exiting method updateRates");

		}
		catch (DBConnectionException DBCE) {
			DefaultLogger.debug(this, "In updateRates " + DBCE.getMessage());
			DBCE.printStackTrace();

		}
		catch (Exception e) {
			DefaultLogger.error(this, "In updateRates " + e.getMessage());
			e.printStackTrace();

		}
		finally {
			finalize(dbUtil);
		}
		return noOfRecUpdated;

	}

	/**
	 * Helper method to generate an update statement
	 * 
	 * @return a sql update statement
	 */
	private String prepareUpdateStmt() {

		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE " + FOREX_TABLE + " ");
		sb.append("SET SELL_CURRENCY = ? ");
		sb.append(", BUY_RATE = ? ");
		sb.append(", SELL_RATE = ? ");
		sb.append(", EFFECTIVE_DATE = ? ");
		sb.append("WHERE BUY_CURRENCY = ? AND FEED_GROUP_ID = ?");
		return sb.toString();
	}

	/**
	 * Helper method to generate an insert statement
	 * 
	 * @return a sql insert statement
	 */
	private String prepareInsertStmt() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO " + FOREX_TABLE);
		sb
				.append("(SELL_CURRENCY, FEED_ID, BUY_CURRENCY, BUY_RATE, EFFECTIVE_DATE, BUY_UNIT, FEED_GROUP_ID ,FEED_REF,SELL_RATE,SELL_UNIT) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		return sb.toString();
	}

	/**
	 * Helper method to generate an insert statement
	 * 
	 * @return a sql insert statement
	 */
	private String prepareStagingInsertStmt() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO " + STAGE_FOREX_TABLE);
		sb
				.append("(SELL_CURRENCY, FEED_ID, BUY_CURRENCY, BUY_RATE, EFFECTIVE_DATE, BUY_UNIT, FEED_GROUP_ID ,FEED_REF,SELL_RATE,SELL_UNIT) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		return sb.toString();
	}

	private String prepareInsertToHistoryStmt() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO cms_forex_history ");
		sb
				.append("(SELL_CURRENCY,HISTORY_ID, BUY_CURRENCY, BUY_RATE, EFFECTIVE_DATE, BUY_UNIT, FEED_GROUP_ID ,FEED_REF,SELL_RATE,SELL_UNIT)");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		return sb.toString();
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
	
	public String getCPSId(String currency)throws FinderException,SQLException{
		String search_CPS_ID = "select CPS_ID from "+FOREX_TABLE+" where TRIM(currency_iso_code) = '"+currency.trim()+"' ";
		ResultSet rs;
		String cpsId = null; 
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(search_CPS_ID);
			rs = dbUtil.executeQuery();
			while(rs.next()){
				cpsId = rs.getString(1);
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				dbUtil.close();
			}catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCPSId ", ex);
			}
		}
		return cpsId;
	}
}
