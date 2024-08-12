package com.integrosys.cms.batch.transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.AbstractDBUtil;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * common DAO used in batch for inserting record in transaction table
 * 
 * @author $Author : shphoon $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class TransactionDao {
	private AbstractDBUtil dbUtil;

	private String insertTrxSQL = "INSERT INTO TRANSACTION ( "
			+ "TRANSACTION_ID, FROM_STATE, USER_ID, TRANSACTION_TYPE, TRANSACTION_DATE, REFERENCE_ID, STATUS, "
			+ "STAGING_REFERENCE_ID, TEAM_ID, TRX_REFERENCE_ID, LEGAL_ID, CUSTOMER_ID, LIMIT_PROFILE_ID, TEAM_TYPE_ID, "
			+ "TO_GROUP_TYPE_ID, TO_GROUP_ID, TO_USER_ID ) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	private String updateTrxSQL = "UPDATE TRANSACTION SET " + "FROM_STATE = ?, STATUS = ?, STAGING_REFERENCE_ID = ?, "
			+ "TRANSACTION_DATE = ? , TRANSACTION_SUBTYPE = ? " + "WHERE TRANSACTION_TYPE = ? AND REFERENCE_ID = ? ";

	private String updateTrxRefSQL = "UPDATE TRANSACTION SET " + "STAGING_REFERENCE_ID = ? "
			+ "WHERE TRANSACTION_TYPE = ? AND REFERENCE_ID = ? ";

	private String getTransactionSql = "select staging_reference_id from TRANSACTION "
			+ "where reference_id=? and TRANSACTION_TYPE=? ";

	public int insertTrxRecord(String trxType, long refId, long stgRefId) throws SearchDAOException {
		return insertTrxRecord(trxType, refId, stgRefId, null);
	}

	public int insertTrxRecord(String trxType, long refId, long stgRefId, AbstractDBUtil parentDBUtil)
			throws SearchDAOException {
		DefaultLogger.info(this, "Entering into insert Trx Record to load DAO Method");
		int rowUpdated = 0;

		try {
			if (parentDBUtil == null) {
				dbUtil = getDBUtil();
			}
			else {

				dbUtil = parentDBUtil;
			}
			dbUtil.setSQL(insertTrxSQL);

			String seq = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_TRX, true);
			DefaultLogger.debug(this, "TRANSACTION ID pk : " + seq);
			dbUtil.setString(1, seq);

			dbUtil.setString(2, ICMSConstant.STATE_ND);
			dbUtil.setLong(3, -999999999); // user id
			dbUtil.setString(4, trxType);
			dbUtil.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			dbUtil.setLong(6, refId);
			dbUtil.setString(7, ICMSConstant.STATE_ACTIVE);
			dbUtil.setLong(8, stgRefId);
			dbUtil.setLong(9, -999999999); // team id
			dbUtil.setLong(10, -999999999);
			dbUtil.setString(11, "-999999999");
			dbUtil.setLong(12, -999999999);
			dbUtil.setLong(13, -999999999);
			dbUtil.setLong(14, -999999999); // team type id
			dbUtil.setString(15, "-999999999");
			dbUtil.setLong(16, -999999999);
			dbUtil.setLong(17, -999999999);

			rowUpdated = dbUtil.executeUpdate();

		}
		catch (DBConnectionException e) {
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			DefaultLogger.error(this, e.getMessage());
			e.printStackTrace();
		}
		finally {
			if (parentDBUtil == null) {
				finalize(dbUtil);
			}
		}
		return rowUpdated;
	}

	public int updateTrxRecord(String trxType, long refId, long stgRefId, AbstractDBUtil parentDBUtil)
			throws SearchDAOException {
		int rowUpdated = 0;

		try {
			if (parentDBUtil == null) {
				dbUtil = getDBUtil();
			}
			else {
				dbUtil = parentDBUtil;
			}

			dbUtil.setSQL(updateTrxSQL);

			dbUtil.setString(1, ICMSConstant.STATE_ND);
			dbUtil.setString(2, ICMSConstant.STATE_ACTIVE);
			dbUtil.setLong(3, stgRefId); // update the stage reference ID
			dbUtil.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			dbUtil.setString(5, ICMSConstant.BATCH_INPUT_TRX_TYPE);
			dbUtil.setString(6, trxType);
			dbUtil.setLong(7, refId);

			rowUpdated = dbUtil.executeUpdate();

		}
		catch (DBConnectionException e) {
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			DefaultLogger.error(this, e.getMessage());
			e.printStackTrace();
		}
		finally {
			if (parentDBUtil == null) {
				finalize(dbUtil);
			}
		}
		return rowUpdated;
	}

	public int updateTrxRefRecord(String trxType, long refId, long stgRefId, AbstractDBUtil parentDBUtil)
			throws SearchDAOException {
		int rowUpdated = 0;

		try {
			if (parentDBUtil == null) {
				dbUtil = getDBUtil();
			}
			else {
				dbUtil = parentDBUtil;
			}

			dbUtil.setSQL(updateTrxRefSQL);

			dbUtil.setLong(1, stgRefId); // update the stage reference ID
			dbUtil.setString(2, trxType);
			dbUtil.setLong(3, refId);

			rowUpdated = dbUtil.executeUpdate();

		}
		catch (DBConnectionException e) {
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			DefaultLogger.error(this, e.getMessage());
			e.printStackTrace();
		}
		finally {
			if (parentDBUtil == null) {
				finalize(dbUtil);
			}
		}
		return rowUpdated;
	}

	public long getStgRefIdByRefId(AbstractDBUtil dbUtil, long refID, String trxType) throws SearchDAOException {
		long stgRefId = ICMSConstant.LONG_INVALID_VALUE;
		try {
			ResultSet rs = null;
			dbUtil.setSQL(getTransactionSql);
			dbUtil.setLong(1, refID);
			dbUtil.setString(2, trxType);
			rs = dbUtil.executeQuery();
			if (rs.next()) {
				stgRefId = rs.getLong(1);
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, e.getMessage());
			e.printStackTrace();
		}
		finally {
			if (dbUtil == null) {
				finalize(dbUtil);
			}
		}

		return stgRefId;
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

	private AbstractDBUtil getDBUtil() throws DBConnectionException, SQLException {
		return new DBUtil();
	}
}
