/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/CCTaskDAO.java,v 1.7 2005/10/17 11:15:18 hshii Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//java
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * DAO for CC Task
 * @author $Author: hshii $
 * @version $Revision: 1.7 $
 * @since $Date: 2005/10/17 11:15:18 $ Tag: $Name: $
 */

public class CCTaskDAO implements ICCTaskDAO {
	private DBUtil dbUtil = null;

	private static String SELECT_CC_TASK_TRX = "SELECT STAGE_CC_TASK.TASK_ID, STAGE_CC_TASK.CMS_LSP_LMT_PROFILE_ID, "
			+ "STAGE_CC_TASK.CMS_LMP_SUB_PROFILE_ID, STAGE_CC_TASK.CMS_PLEDGOR_DTL_ID, STAGE_CC_TASK.CATEGORY, "
			+ "STAGE_CC_TASK.DMCL_CNTRY_ISO_CODE, STAGE_CC_TASK.ORG_CODE, TRANSACTION.TRANSACTION_ID, "
			+ "TRANSACTION.STATUS, TRANSACTION.TRX_ORIGIN_COUNTRY "
			+ "FROM STAGE_CC_TASK, TRANSACTION WHERE TRANSACTION.TRANSACTION_TYPE = 'CC_TASK' AND "
			+ "TRANSACTION.STATUS <> 'CLOSED' AND TRANSACTION.STAGING_REFERENCE_ID = STAGE_CC_TASK.TASK_ID AND "
			+ "STAGE_CC_TASK.IS_DELETED = 'N'";

	private static String SELECT_CC_TASK_TRX_COUNT = "SELECT COUNT(*)  FROM STAGE_CC_TASK, TRANSACTION WHERE TRANSACTION.TRANSACTION_TYPE = 'CC_TASK' AND "
			+ "TRANSACTION.STATUS <> 'CLOSED' AND TRANSACTION.STAGING_REFERENCE_ID = STAGE_CC_TASK.TASK_ID AND "
			+ "STAGE_CC_TASK.IS_DELETED = 'N'";

	/**
	 * To get the number of cc task that satisfy the criteria
	 * @param aCriteria of CCTaskSearchCriteria type
	 * @return int - the number of CC task that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfCCTask(CCTaskSearchCriteria aCriteria) throws SearchDAOException {
		String searchString = getStagingSearchString(aCriteria);
		String sql = SELECT_CC_TASK_TRX_COUNT;

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
			throw new SearchDAOException("SQLException in getNoOfCCTask", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getNoOfCCTask", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getNoOfCCTask", ex);
			}
		}
	}

	/**
	 * To get the list of cc task that satisfy the criteria
	 * @param aCriteria of CCTaskSearchCritieria type
	 * @return CCTaskSearchResult[] - the list of CC task
	 * @throws SearchDAOException
	 */
	public CCTaskSearchResult[] getCCTask(CCTaskSearchCriteria aCriteria) throws SearchDAOException {
		String searchString = getStagingSearchString(aCriteria);
		String sql = SELECT_CC_TASK_TRX;

		if (searchString != null) {
			sql = new StringBuffer().append(sql).append(" AND ").append(searchString).toString();
		}
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				CCTaskSearchResult result = new CCTaskSearchResult();
				result.setTaskID(rs.getLong(TSKTBL_TASK_ID));

				result.setCustomerCategory(rs.getString(TSKTBL_CATEGORY));
				if (ICMSConstant.CHECKLIST_PLEDGER.equals(result.getCustomerCategory())) {
					result.setCustomerID(rs.getLong(TSKTBL_PLEDGOR_ID));
				}
				else {
					result.setCustomerID(rs.getLong(TSKTBL_SUB_PROFILE_ID));
				}
				result.setDomicileCountry(rs.getString(TSKTBL_DOMICILE_CTRY));
				result.setOrgCode(rs.getString(TSKTBL_ORG_CODE));
				result.setTrxID(rs.getString(TRX_ID));
				result.setTrxStatus(rs.getString(TRX_STATUS));
				result.setTrxOriginCountry(rs.getString("TRX_ORIGIN_COUNTRY"));
				resultList.add(result);
			}
			rs.close();
			return (CCTaskSearchResult[]) resultList.toArray(new CCTaskSearchResult[0]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCCTask", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCCTask", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCCTask", ex);
			}
		}
	}

	private String getStagingSearchString(CCTaskSearchCriteria aCriteria) {
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

		if (!isEmpty(aCriteria.getCustomerCategory())) {
			if (sqlAppended) {
				buf.append(" AND ");
			}
			buf.append(STAGE_TSKTBL_CATEGORY_PREF);
			buf.append(" = '");
			buf.append(aCriteria.getCustomerCategory());
			buf.append("'");
			sqlAppended = true;

			if (aCriteria.getCustomerID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				buf.append(" AND ");
				if (ICMSConstant.CHECKLIST_PLEDGER.equals(aCriteria.getCustomerCategory())) {
					buf.append(STAGE_TSKTBL_PLEDGOR_ID_PREF);
				}
				else {
					buf.append(STAGE_TSKTBL_SUB_PROFILE_ID_PREF);
				}
				buf.append(" = ");
				buf.append(aCriteria.getCustomerID());
			}
		}

		if (!isEmpty(aCriteria.getDomicileCountry())) {
			if (sqlAppended) {
				buf.append(" AND ");
			}
			buf.append(STAGE_TSKTBL_DOMICILE_CTRY_PREF);
			buf.append(" = '");
			buf.append(aCriteria.getDomicileCountry());
			buf.append("'");
			sqlAppended = true;
		}

		if (!isEmpty(aCriteria.getOrgCode())) {
			if (sqlAppended) {
				buf.append(" AND ");
			}
			buf.append(STAGE_TSKTBL_ORG_CODE_PREF);
			buf.append(" = '");
			buf.append(aCriteria.getOrgCode());
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
