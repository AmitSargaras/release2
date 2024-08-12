/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/trx/DDNTrxDAO.java,v 1.3 2005/08/25 08:46:10 hshii Exp $
 */
package com.integrosys.cms.app.ddn.trx;

import java.sql.ResultSet;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * DAO for DDN transaction.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/25 08:46:10 $ Tag: $Name: $
 */
public class DDNTrxDAO {
	private DBUtil dbUtil;

	private static String SELECT_PREVIOUS_APPROVED_HISTORY = "select TRANSACTION_DATE, REFERENCE_ID, STAGING_REFERENCE_ID, REMARKS, h.STATUS, h.USER_ID, USER_NAME, h.LOGIN_ID "
			+ "from TRANS_HISTORY h, CMS_USER u where h.TRANSACTION_ID = ? and h.OPSDESC = ? and h.TRANSACTION_DATE = "
			+ "(select max (TRANSACTION_DATE) from TRANS_HISTORY h1 where TRANSACTION_ID = ? and OPSDESC = ? and TRANSACTION_DATE < "
			+ "(select max (TRANSACTION_DATE) from TRANS_HISTORY where TRANSACTION_ID = h1.TRANSACTION_ID AND OPSDESC = ?)) and h.USER_ID = u.USER_ID";

	private static String SELECT_CLOSE_APPROVED_HISTORY = "select TRANSACTION_DATE, REFERENCE_ID, STAGING_REFERENCE_ID, REMARKS, h.STATUS, h.USER_ID, USER_NAME, h.LOGIN_ID "
			+ "from TRANS_HISTORY h, CMS_USER u where h.TRANSACTION_ID = ? and h.OPSDESC = ? and h.TRANSACTION_DATE = "
			+ "(select max (TRANSACTION_DATE) from TRANS_HISTORY h1 where TRANSACTION_ID = ? and OPSDESC = ?) and h.USER_ID = u.USER_ID";

	private static String SELECT_LATEST_ACTIVE_HISTORY = "SELECT h.remarks, u.user_name, h.login_id FROM TRANS_HISTORY h, CMS_USER u WHERE h.TRANSACTION_ID = ?"
			+ "AND opsdesc = ? AND h.TRANSACTION_DATE = "
			+ "(SELECT MAX (TRANSACTION_DATE) FROM TRANS_HISTORY h1 "
			+ "WHERE TRANSACTION_ID = ? AND opsdesc = ?)" + "AND h.user_id = u.user_id";

	private static String SELECT_DDN_APPROVED_HISTORY = "SELECT h.remarks, u.user_name, h.login_id FROM TRANS_HISTORY h, CMS_USER u "
			+ "WHERE h.TRANSACTION_ID = ? AND opsdesc = ? AND h.TRANSACTION_DATE = "
			+ "(SELECT MAX (TRANSACTION_DATE) FROM TRANS_HISTORY h1 "
			+ "WHERE TRANSACTION_ID = ? AND opsdesc = ? AND transaction_subtype is null) "
			+ "AND h.user_id = u.user_id";

	/**
	 * Default Constructor
	 */
	public DDNTrxDAO() {
	}

	/**
	 * Get transaction value of previous approved transaction.
	 * 
	 * @param trxID transaction id
	 * @param action operation to get the approved transaction
	 * @return DDN transaction value
	 * @throws SearchDAOException on error searching the transaction
	 */
	public IDDNTrxValue getPrevApprovedHistoryTrxValue(String trxID, String action) throws SearchDAOException {
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SELECT_PREVIOUS_APPROVED_HISTORY);
			dbUtil.setString(1, trxID);
			dbUtil.setString(2, action);
			dbUtil.setString(3, trxID);
			dbUtil.setString(4, action);
			dbUtil.setString(5, action);
			ResultSet rs = dbUtil.executeQuery();
			OBDDNTrxValue trxVal = null;
			if (rs.next()) {
				trxVal = new OBDDNTrxValue();
				String username = rs.getString("USER_NAME");
				if (username == null) {
					username = "";
				}
				String loginid = rs.getString("LOGIN_ID");
				if (loginid == null) {
					loginid = "";
				}
				trxVal.setUserInfo(username + " ( " + loginid + " )");
				trxVal.setReferenceID(rs.getString("REFERENCE_ID"));
				trxVal.setStagingReferenceID(rs.getString("STAGING_REFERENCE_ID"));
				trxVal.setStatus(rs.getString("STATUS"));
				trxVal.setRemarks(rs.getString("REMARKS"));
			}
			rs.close();
			return trxVal;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error atgetPrevApprovedHistoryTrxValue ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get transaction value of close approved transaction.
	 * 
	 * @param trxID transaction id
	 * @param action operation to get the approved transaction
	 * @return DDN transaction value
	 * @throws SearchDAOException on error searching the transaction
	 */
	public IDDNTrxValue getCloseApprovedHistoryTrxValue(String trxID, String action) throws SearchDAOException {
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SELECT_CLOSE_APPROVED_HISTORY);
			dbUtil.setString(1, trxID);
			dbUtil.setString(2, action);
			dbUtil.setString(3, trxID);
			dbUtil.setString(4, action);
			ResultSet rs = dbUtil.executeQuery();
			OBDDNTrxValue trxVal = null;
			if (rs.next()) {
				trxVal = new OBDDNTrxValue();
				String username = rs.getString("USER_NAME");
				if (username == null) {
					username = "";
				}
				String loginid = rs.getString("LOGIN_ID");
				if (loginid == null) {
					loginid = "";
				}
				trxVal.setUserInfo(username + " ( " + loginid + " )");
				trxVal.setReferenceID(rs.getString("REFERENCE_ID"));
				trxVal.setStagingReferenceID(rs.getString("STAGING_REFERENCE_ID"));
				trxVal.setStatus(rs.getString("STATUS"));
				trxVal.setRemarks(rs.getString("REMARKS"));
			}
			rs.close();
			return trxVal;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error atgetPrevApprovedHistoryTrxValue ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get transaction value of latest active transaction.
	 * 
	 * @param trxID transaction id
	 * @param action operation to get the approved transaction
	 * @return DDN transaction value
	 * @throws SearchDAOException on error searching the transaction
	 */
	public IDDNTrxValue getLatestActiveInfo(String trxID, String action) throws SearchDAOException {
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SELECT_LATEST_ACTIVE_HISTORY);
			dbUtil.setString(1, trxID);
			dbUtil.setString(2, action);
			dbUtil.setString(3, trxID);
			dbUtil.setString(4, action);
			ResultSet rs = dbUtil.executeQuery();
			OBDDNTrxValue trxVal = null;
			if (rs.next()) {
				trxVal = new OBDDNTrxValue();
				String username = rs.getString("USER_NAME");
				if (username == null) {
					username = "";
				}
				String loginid = rs.getString("LOGIN_ID");
				if (loginid == null) {
					loginid = "";
				}
				trxVal.setUserInfo(username + " ( " + loginid + " )");
				trxVal.setRemarks(rs.getString("REMARKS"));
			}
			rs.close();
			return trxVal;
		}
		catch (Exception e) {
			throw new SearchDAOException("Exception at getLatestActiveDDNRemark", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get transaction value of Latest Generate DDN approved transaction
	 * (exclude update remarks).
	 * 
	 * @param trxID transaction id
	 * @param action operation to get the approved transaction
	 * @return DDN transaction value
	 * @throws SearchDAOException on error searching the transaction
	 */
	public IDDNTrxValue getDDNApprovedHistoryTrxValue(String trxID, String action) throws SearchDAOException {
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SELECT_DDN_APPROVED_HISTORY);
			dbUtil.setString(1, trxID);
			dbUtil.setString(2, action);
			dbUtil.setString(3, trxID);
			dbUtil.setString(4, action);
			ResultSet rs = dbUtil.executeQuery();
			OBDDNTrxValue trxVal = null;
			if (rs.next()) {
				trxVal = new OBDDNTrxValue();
				String username = rs.getString("USER_NAME");
				if (username == null) {
					username = "";
				}
				String loginid = rs.getString("LOGIN_ID");
				if (loginid == null) {
					loginid = "";
				}
				trxVal.setUserInfo(username + " ( " + loginid + " )");
				trxVal.setRemarks(rs.getString("REMARKS"));
			}
			rs.close();
			return trxVal;
		}
		catch (Exception e) {
			throw new SearchDAOException("Exception at getDDNApprovedHistoryTrxValue", e);
		}
		finally {
			finalize(dbUtil);
		}
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
}
