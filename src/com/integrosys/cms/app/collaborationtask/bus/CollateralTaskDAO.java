/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/CollateralTaskDAO.java,v 1.9 2005/10/17 11:15:19 hshii Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//java
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;

/**
 * DAO for Collateral Task
 * @author $Author: hshii $
 * @version $Revision: 1.9 $
 * @since $Date: 2005/10/17 11:15:19 $ Tag: $Name: $
 */

public class CollateralTaskDAO implements ICollateralTaskDAO {
	private DBUtil dbUtil = null;

	private static String SELECT_COLLATERAL_TASK_TRX = "SELECT STAGE_COLLATERAL_TASK.TASK_ID, STAGE_COLLATERAL_TASK.CMS_COLLATERAL_ID, "
			+ "STAGE_COLLATERAL_TASK.SECURITY_LOCATION, TRANSACTION.TRANSACTION_ID, "
			+ "TRANSACTION.STATUS, TRANSACTION.TRX_ORIGIN_COUNTRY FROM STAGE_COLLATERAL_TASK, TRANSACTION WHERE "
			+ "TRANSACTION.TRANSACTION_TYPE = 'COLLATERAL_TASK' AND TRANSACTION.STATUS <> 'CLOSED' AND "
			+ "TRANSACTION.STAGING_REFERENCE_ID = STAGE_COLLATERAL_TASK.TASK_ID AND STAGE_COLLATERAL_TASK.IS_DELETED = 'N'";

	private static String SELECT_COLLATERAL_TASK_TRX_COUNT = "SELECT COUNT(*) FROM STAGE_COLLATERAL_TASK, TRANSACTION WHERE "
			+ "TRANSACTION.TRANSACTION_TYPE = 'COLLATERAL_TASK' AND TRANSACTION.STATUS <> 'CLOSED' AND "
			+ "TRANSACTION.STAGING_REFERENCE_ID = STAGE_COLLATERAL_TASK.TASK_ID AND STAGE_COLLATERAL_TASK.IS_DELETED = 'N'";

	/**
	 * To get the number of collateral task that satisfy the criteria
	 * @param aCriteria of CollateralTaskSearchCriteria type
	 * @return int - the number of collateral task that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfCollateralTask(CollateralTaskSearchCriteria aCriteria) throws SearchDAOException {
		String searchString = getStagingSearchString(aCriteria);
		String sql = SELECT_COLLATERAL_TASK_TRX_COUNT;

		if (searchString != null) {
			sql = new StringBuffer().append(sql).append(" AND ").append(searchString).toString();
		}
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			rs.close();
			return count;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getNoOfSCCertificate", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getNoOfSCCertificate", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getNoOfSCCertificate", ex);
			}
		}
	}

	/**
	 * To get the list of collateral task that satisfy the criteria
	 * @param aCriteria of CollateralTaskSearchCritieria type
	 * @return CollateralTaskSearchResult[] - the list of collateral task
	 * @throws SearchDAOException
	 */
	public CollateralTaskSearchResult[] getCollateralTask(CollateralTaskSearchCriteria aCriteria)
			throws SearchDAOException {
		String searchString = getStagingSearchString(aCriteria);
		String sql = SELECT_COLLATERAL_TASK_TRX;

		if (searchString != null) {
			sql = new StringBuffer().append(sql).append(" AND ").append(searchString).toString();
		}
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				CollateralTaskSearchResult result = new CollateralTaskSearchResult();
				result.setTaskID(rs.getLong(TSKTBL_TASK_ID));
				result.setCollateralID(rs.getLong(TSKTBL_COLLATERAL_ID));
				result.setCollateralLocation(rs.getString(TSKTBL_COLLATERAL_LOCATION));
				result.setTrxID(rs.getString(TRX_ID));
				result.setTrxStatus(rs.getString(TRX_STATUS));
				result.setTrxOriginCountry(rs.getString("TRX_ORIGIN_COUNTRY"));
				resultList.add(result);
			}
			rs.close();
			return (CollateralTaskSearchResult[]) resultList.toArray(new CollateralTaskSearchResult[0]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getNoOfSCCertificate", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getNoOfSCCertificate", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getNoOfSCCertificate", ex);
			}
		}
	}

	private String getStagingSearchString(CollateralTaskSearchCriteria aCriteria) {
		StringBuffer buf = new StringBuffer();

		if (aCriteria == null) {
			return null;
		}

		boolean sqlAppended = false;
		if (aCriteria.getLimitProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			buf.append(STAGE_TSKTBL_LIMIT_PROFILE_ID_PREF);
			buf.append(" = ");
			buf.append(aCriteria.getLimitProfileID());
			sqlAppended = true;
		}

		if (aCriteria.getCollateralID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			if (sqlAppended) {
				buf.append(" AND ");
			}
			buf.append(STAGE_TSKTBL_COLLATERAL_ID_PREF);
			buf.append(" = ");
			buf.append(aCriteria.getCollateralID());
			sqlAppended = true;
		}

		if (!isEmpty(aCriteria.getCollateralLocation())) {
			if (sqlAppended) {
				buf.append(" AND ");
			}
			buf.append(STAGE_TSKTBL_COLLATERAL_LOCATION_PREF);
			buf.append(" = '");
			buf.append(aCriteria.getCollateralLocation());
			buf.append("'");
			sqlAppended = true;
		}

		if ((aCriteria.getTrxStatusList() != null) && (aCriteria.getTrxStatusList().length > 0)) {
			String[] trxStatus = aCriteria.getTrxStatusList();
			if (sqlAppended) {
				buf.append(" AND ");
			}
			buf.append(TRX_STATUS_PREF);
			buf.append(" IN (");
			for (int ii = 0; ii < trxStatus.length; ii++) {
				if (ii > 0) {
					buf.append(", ");
				}
				buf.append("'");
				buf.append(trxStatus[ii]);
				buf.append("'");
			}
			buf.append(")");
			// sqlAppended = true;
		}
		return buf.toString();
	}

	/**
	 * Utilty method to check if a string value is null or empty
	 * @param aValue - String
	 * @return boolean - true if empty and false otherwise
	 */
	private boolean isEmpty(String aValue) {
		return !((aValue != null) && (aValue.trim().length() > 0));
	}
}
