package com.integrosys.cms.batch.customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CustomerUpdateDAO implements ICustomerDAOConstants {

	private static int MAX_IN_CLAUSE = 1000;

	/**
	 * a hashmap of ICollateralParameter objects with key: country code +
	 * subtype code
	 */
	private static Vector customerVector = null;

	private static int crpSize = 0;

	private static String SELECT_CUSTOMER_UPDATE_SQL = "SELECT CMS_BATCH_CIF_UPDATE_ID , LE_ID, SOURCE_ID FROM "
			+ TABLE_CUSTOMER_UPDATE + " WHERE PROCESSED_IND = 'N'";

	private static String PROCESSED_CUSTOMER_UPDATE_SQL = "UPDATE " + TABLE_CUSTOMER_UPDATE
			+ " SET PROCESSED_IND = 'Y' , TIME_PROCESSED = ? " + " WHERE CMS_BATCH_CIF_UPDATE_ID = ?";

	/**
	 * Query customer with maximum no of record set .
	 * @return
	 * @throws SearchDAOException
	 */
	public Vector getCustomerListForUpdate() throws SearchDAOException {
		// Default to 5000 Maximum
		int maxReturn = PropertyManager.getInt("customer.update.maximum", 5000);

		customerVector = new Vector();

		DBUtil dbUtil = null;

		try {
			dbUtil = getDBUtil();
			//For DB2
//			String sql = SELECT_CUSTOMER_UPDATE_SQL + " FETCH FIRST " + maxReturn + " ROWS ONLY";
			//For Oracle
			String sql = "SELECT * FROM ("+SELECT_CUSTOMER_UPDATE_SQL + " ) TEMP WHERE ROWNUM<= " + maxReturn + " ";

			Debug("getCustomerListForUpdate : " + sql);

			dbUtil.setSQL(sql);

			ResultSet rs = dbUtil.executeQuery();

			while (rs.next()) {
				OBCustomerUpdate obCustomerUpdate = new OBCustomerUpdate();

				obCustomerUpdate.setCustomerUpdateId(rs.getLong("CMS_BATCH_CIF_UPDATE_ID"));
				obCustomerUpdate.setCifId(rs.getString("LE_ID"));
				obCustomerUpdate.setSourceId(rs.getString("SOURCE_ID"));

				customerVector.add(obCustomerUpdate);
			}

			return customerVector;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Caught Exception in getCustomerListForUpdate!", e);

			throw new SearchDAOException("Caught Exception in getCustomerListForUpdate", e);
		}
		finally {
			finalize(dbUtil);
		}

	}

	public OBCustomerUpdate updateProcessedCustomer(OBCustomerUpdate obCustomerUpdate) throws Exception {
		DBUtil dbUtil = null;

		try {
			dbUtil = getDBUtil();
			String sql = PROCESSED_CUSTOMER_UPDATE_SQL;

			Debug("updateProcessedCustomer : " + sql);

			dbUtil.setSQL(sql);

			dbUtil.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			dbUtil.setLong(2, obCustomerUpdate.getCustomerUpdateId());

			int affected = dbUtil.executeUpdate();

			Debug("Number of affected rows : " + affected);

			return obCustomerUpdate;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Caught Exception in updateProcessedCustomer!", e);

			throw new SearchDAOException("Caught Exception in updateProcessedCustomer", e);
		}
		finally {
			finalize(dbUtil);
		}

	}

	private void Debug(String msg) {
		DefaultLogger.debug(this,msg);

		DefaultLogger.debug(this, msg);
	}

	private DBUtil getDBUtil() throws DBConnectionException, SQLException {
		return new DBUtil();
	}

	/**
	 * Helper method to clean up database resources.
	 * 
	 * @param dbUtil database utility object error in cleaning up DB resources
	 */
	private void finalize(DBUtil dbUtil) {
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
