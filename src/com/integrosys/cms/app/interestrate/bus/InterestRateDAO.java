/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.util.DB2DateConverter;

/**
 * DAO for interestrate.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class InterestRateDAO implements IInterestRateDAO {
	private DBUtil dbUtil;

	// Table names
	public static final String INTEREST_RATE_TABLE = "CMS_INTEREST_RATE";

	// Column values for interestrate table.
	public static final String INT_RATE_ID = "INT_RATE_ID";

	public static final String INT_RATE_TYPE = "INT_RATE_TYPE";

	public static final String INT_RATE_DATE = "INT_RATE_DATE";

	public static final String INT_RATE_PERCENTAGE = "PERCENTAGE";

	public static final String INT_RATE_GROUP_ID = "GROUP_ID";

	public static final String INT_RATE_VERSION_TIME = "VERSION_TIME";

	public static final String INT_RATE_REF_SEQ = "INT_RATE_REF_SEQ";

	private static MessageFormat SELECT_INT_RATE_COUNT_FOR_CREATE = new MessageFormat("SELECT * FROM {0} "
			+ " WHERE INT_RATE_TYPE = \''{1}\'' AND "
			+ " TRUNC( INT_RATE_DATE ) >= TRUNC ( TIMESTAMP ( \''{2}\'' ) ) AND "
			+ " TRUNC( INT_RATE_DATE ) <= TRUNC ( TIMESTAMP ( \''{3}\'' ) ) ");

	/**
	 * Default Constructor
	 */
	public InterestRateDAO() {
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRateDAO#getInterestRateByMonth
	 */
	public IInterestRate[] getInterestRateByMonth(String intRateType, Date monthYear) throws SearchDAOException {
		String sql = constructIntRateSQL(intRateType, monthYear);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processIntRateResultSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting interest rate by month: ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get the table for actual interest rate.
	 * 
	 * @return String
	 */
	protected String getTable() {
		return INTEREST_RATE_TABLE;
	}

	/**
	 * Helper method to construct query for getting interest rate.
	 * 
	 * @return sql query
	 */
	protected String constructIntRateSQL(String intRateType, Date monthYear) {
		if (intRateType == null) {
			intRateType = "";
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(monthYear);

		cal.set(cal.get(cal.YEAR), cal.get(cal.MONTH), 1);
		Date startDate = cal.getTime();
		String startDateStr = DB2DateConverter.formatDate(startDate);
		cal.set(cal.get(cal.YEAR), cal.get(cal.MONTH) + 1, 0);
		Date endDate = cal.getTime();
		String endDateStr = DB2DateConverter.formatDate(endDate);

		String[] params = new String[] { getTable(), intRateType, startDateStr, endDateStr };
		String qryStr = SELECT_INT_RATE_COUNT_FOR_CREATE.format(params);
		DefaultLogger.debug(this, "constructIntRateSQL=" + qryStr);
		return qryStr;
	}

	/**
	 * Helper method to process the result set of interest rate.
	 * 
	 * @param rs result set
	 * @return a list of interest rate
	 * @throws SQLException on error processing the result set
	 */
	private IInterestRate[] processIntRateResultSet(ResultSet rs) throws SQLException {
		ArrayList arrList = new ArrayList();

		while (rs.next()) {
			OBInterestRate intRate = new OBInterestRate();
			intRate.setIntRateID(new Long(rs.getLong(INT_RATE_ID)));
			intRate.setIntRateType(rs.getString(INT_RATE_TYPE));
			intRate.setIntRateDate(rs.getDate(INT_RATE_DATE));
			BigDecimal intRatePercent = rs.getBigDecimal(INT_RATE_PERCENTAGE);
			if (intRatePercent != null) {
				intRate.setIntRatePercent(new Double(intRatePercent.doubleValue()));
			}

			intRate.setGroupID(rs.getLong(INT_RATE_GROUP_ID));
			intRate.setVersionTime(rs.getLong(INT_RATE_VERSION_TIME));
			arrList.add(intRate);
		}
		return (OBInterestRate[]) arrList.toArray(new OBInterestRate[0]);
	}

	/**
	 * Utility method to check if a string value is null or empty.
	 * 
	 * @param aValue string to be checked
	 * @return boolean true if empty and false otherwise
	 */
	private boolean isEmpty(String aValue) {
		if ((aValue != null) && (aValue.trim().length() > 0)) {
			return false;
		}
		else {
			return true;
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

	// inner class
	private class DAPFilterException extends Exception {
		public DAPFilterException(String msg) {
			super(msg);
		}
	}

}
