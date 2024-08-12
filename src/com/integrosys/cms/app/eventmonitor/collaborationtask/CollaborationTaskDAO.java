/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collaborationtask/CollaborationTaskDAO.java,v 1.17 2005/05/27 08:07:03 jychong Exp $
 */

package com.integrosys.cms.app.eventmonitor.collaborationtask;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAOResult;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.MonitorDaoResultRetrievalFailureException;

/**
 * Monitors collaboration tasks Trigger event when BCA origination location <>
 * security location
 */
public class CollaborationTaskDAO extends AbstractMonitorDAO implements IMonitorDAO {

	private String getSQLStatement(IRuleParam ruleParam) {
		StringBuffer strBuf = new StringBuffer();

		strBuf.append("SELECT DISTINCT SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID, ").append(
				"SCI_LE_SUB_PROFILE.LSP_LE_ID,SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, ").append(
				"CMS_SECURITY.CMS_COLLATERAL_ID, ")
				.append("CMS_SECURITY.SCI_SECURITY_DTL_ID AS security_id, task_id, ").append(
						"CMS_SECURITY.SECURITY_SUB_TYPE_ID,CMS_SECURITY.security_location, ").append(
						"SCI_LE_MAIN_PROFILE.LMP_SGMNT_CODE_VALUE AS SEGMENT, ").append(
						"SCI_LSP_LMT_PROFILE.cms_orig_country, ").append("SCI_LSP_LMT_PROFILE.cms_orig_organisation ")
				.append("FROM SCI_LSP_LMT_PROFILE, SCI_LSP_APPR_LMTS, CMS_LIMIT_SECURITY_MAP, ").append(
						"CMS_SECURITY, CMS_COLLATERAL_TASK, SCI_LE_SUB_PROFILE, SCI_LE_MAIN_PROFILE ").append(
						"WHERE SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID ")
				.append("AND SCI_LSP_LMT_PROFILE.cms_customer_id = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID ").append(
						"AND SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID ")
				.append("AND CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID = CMS_SECURITY.cms_collateral_id ").append(
						"AND CMS_SECURITY.SECURITY_LOCATION <> SCI_LSP_LMT_PROFILE.CMS_ORIG_COUNTRY ").append(
						"AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID ")
				.append("AND SCI_LSP_LMT_PROFILE.CMS_TAT_CREATE_DATE IS NOT NULL ").append(
						"AND CMS_SECURITY.CMS_COLLATERAL_ID = CMS_COLLATERAL_TASK.CMS_COLLATERAL_ID ");

		if (ruleParam.hasCountryCode()) {
			strBuf.append("AND CMS_ORIG_COUNTRY = ?  ");
		}

		return strBuf.toString();
	}

	public IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws MonitorDaoResultRetrievalFailureException {

		ResultSet rs = null;
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			String sql = getSQLStatement(ruleParam);
			// asDefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);

			if (ruleParam.hasCountryCode()) {
				dbUtil.setString(1, ruleParam.getCountryCode());
			}

			rs = dbUtil.executeQuery();
			List results = processResultSet(rs);

			CollaborationTaskDAOResult result = new CollaborationTaskDAOResult(results);
			return result;

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Exception from getInitialSet method ", e);
		}
		finally {
			close(rs, dbUtil);
		}
	}

	/**
	 * Process resultset to return a list of results.
	 * @param ResultSet rs
	 * @return List - list of results
	 * @throws Exception
	 */
	private List processResultSet(ResultSet rs) throws Exception {
		ArrayList results = new ArrayList();
		if (rs != null) {
			OBCollaborationTaskInfo nn = null;
			while (rs.next()) {
				nn = new OBCollaborationTaskInfo();
				nn.setLeID(rs.getString("LSP_LE_ID"));
				nn.setLeName(rs.getString("LSP_SHORT_NAME"));
				nn.setSegment(rs.getString("SEGMENT"));
				nn.setSecurityLocation(rs.getString("SECURITY_LOCATION"));
				nn.setSecuritySubTypeID(rs.getString("SECURITY_SUB_TYPE_ID"));
				nn.setSecurityID(rs.getString("SECURITY_ID"));
				nn.setOriginatingCountry(rs.getString("CMS_ORIG_COUNTRY"));
				nn.setOriginatingOrganisation(rs.getString("CMS_ORIG_ORGANISATION"));
				results.add(nn);
			}
		}
		return results;
	}
}
