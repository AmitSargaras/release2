/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/ddndue/DDNDueDAO.java,v 1.22 2005/05/27 08:03:46 wltan Exp $
 */

package com.integrosys.cms.app.eventmonitor.ddndue;

import java.sql.Date;
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
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

/**
 * DAO to select the set of DDN which is going to due for expiry This is called
 * by the monitoring class extending from AbstractMonCommon
 */
public class DDNDueDAO extends AbstractMonitorDAO implements IMonitorDAO {

	private static final String DDN_DUE_SQL = "SELECT outerprofile.cms_lsp_lmt_profile_id, sci_le_sub_profile.lsp_short_name, "
			+ "       sci_le_sub_profile.lsp_le_id, outerprofile.llp_bca_ref_appr_date, "
			+ "       sci_le_main_profile.lmp_sgmnt_code_value AS SEGMENT, "
			+ "       due_days (cast(? as date), llp_bca_ref_appr_date, cast(? as int), cms_orig_country) AS daysdue, "
			+ "       add_days (llp_bca_ref_appr_date, cast(? as int), cms_orig_country) AS duedate, "
			+ "       cms_orig_country, cms_orig_organisation "
			+ "  FROM sci_lsp_lmt_profile outerprofile, "
			+ "       sci_le_sub_profile, "
			+ "       sci_le_main_profile, "
			+ "       lmt_profile_biz_days "
			+ " WHERE lmt_profile_biz_days.num_biz_days >= ? "
			+ "   AND lmt_profile_biz_days.cms_lsp_lmt_profile_id = outerprofile.cms_lsp_lmt_profile_id "
			+ "   AND sci_le_sub_profile.cms_le_main_profile_id = sci_le_main_profile.cms_le_main_profile_id "
			+ "   AND outerprofile.cms_tat_create_date IS NOT NULL "
			+ "   AND outerprofile.cms_customer_id = sci_le_sub_profile.cms_le_sub_profile_id "
			+ "   AND sci_le_sub_profile.cms_non_borrower_ind = 'N' "
			+ "   AND outerprofile.cms_scc_complete_ind = 'N' "
			+ "   AND outerprofile.cms_bca_renewal_ind = 'Y' "
			+ "   AND NOT EXISTS ( "
			+ "          SELECT innerprofile.cms_lsp_lmt_profile_id "
			+ "            FROM sci_lsp_lmt_profile innerprofile, cms_ddn_generated "
			+ "           WHERE innerprofile.cms_lsp_lmt_profile_id = outerprofile.cms_lsp_lmt_profile_id "
			+ "             AND cms_ddn_generated.cms_lsp_lmt_profile_id = innerprofile.cms_lsp_lmt_profile_id) "
			+ "   AND (   (    EXISTS ( "
			+ "                   SELECT 'dummy' "
			+ "                     FROM sci_lsp_appr_lmts LIMIT, cms_limit_security_map lsmap "
			+ "                    WHERE outerprofile.llp_lsp_id = LIMIT.lmt_lsp_id "
			+ "                      AND outerprofile.llp_le_id = LIMIT.lmt_le_id "
			+ "                      AND outerprofile.llp_id = LIMIT.lmt_llp_id "
			+ "                      AND LIMIT.cms_lsp_appr_lmts_id = lsmap.cms_lsp_appr_lmts_id) "
			+ "            AND NOT EXISTS ( "
			+ "                   SELECT 'dummy' "
			+ "                     FROM cms_pscc_generated, TRANSACTION t "
			+ "                    WHERE t.transaction_type = 'PSCC' "
			+ "                      AND t.reference_id = cms_pscc_generated.pscc_id "
			+ "                      AND t.status != 'CLOSED' "
			+ "                      AND cms_pscc_generated.cms_lsp_lmt_profile_id = outerprofile.cms_lsp_lmt_profile_id) "
			+ "            AND NOT EXISTS ( "
			+ "                   SELECT 'dummy' "
			+ "                     FROM cms_scc_generated "
			+ "                    WHERE cms_scc_generated.cms_lsp_lmt_profile_id = outerprofile.cms_lsp_lmt_profile_id)) "
			+ "        OR (    NOT EXISTS ( "
			+ "                   SELECT 'dummy' "
			+ "                     FROM sci_lsp_appr_lmts LIMIT, cms_limit_security_map lsmap "
			+ "                    WHERE outerprofile.llp_lsp_id = LIMIT.lmt_lsp_id "
			+ "                      AND outerprofile.llp_le_id = LIMIT.lmt_le_id "
			+ "                      AND outerprofile.llp_id = LIMIT.lmt_llp_id "
			+ "                      AND LIMIT.cms_lsp_appr_lmts_id = lsmap.cms_lsp_appr_lmts_id) "
			+ "            AND outerprofile.cms_ccc_complete_ind = 'N') " + "		) ";

	private String getSQLStatement(IRuleParam ruleParam) {
		if (ruleParam.hasCountryCode()) {
			StringBuffer strBuf = new StringBuffer();
			strBuf.append(DDN_DUE_SQL);
			strBuf.append(" AND outerprofile.cms_orig_country = ? ");
			return strBuf.toString();
		}
		else {
			return DDN_DUE_SQL;
		}

	}

	public IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws MonitorDaoResultRetrievalFailureException {

		ResultSet rs = null;
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			OBDateRuleParam param = (OBDateRuleParam) ruleParam;
			String sql = getSQLStatement(ruleParam);
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setDate(1, new Date(param.getSysDate().getTime()));
			dbUtil.setInt(2, param.getNumOfDays());
			dbUtil.setInt(3, param.getNumOfDays());
			dbUtil.setInt(4, param.getNumOfDays());

			if (ruleParam.hasCountryCode()) {
				dbUtil.setString(5, param.getCountryCode());
			}

			rs = dbUtil.executeQuery();
			List results = processResultSet(rs);
			DDNDueDAOResult result = new DDNDueDAOResult(results);
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
			OBDDNDueInfo nn = null;
			while (rs.next()) {
				nn = new OBDDNDueInfo();
				nn.setLeName(rs.getString("LSP_SHORT_NAME"));
				nn.setLeID(rs.getString("LSP_LE_ID"));
				nn.setSegment(rs.getString("SEGMENT"));
				nn.setApprovalDate(rs.getDate("LLP_BCA_REF_APPR_DATE")); //todo:
				// may
				// need
				// to
				// replace
				// this
				// with
				// approval
				// date
				nn.setDaysDue(rs.getInt("DAYSDUE"));
				nn.setDueDate(rs.getDate("DUEDATE"));
				nn.setOriginatingCountry(rs.getString("CMS_ORIG_COUNTRY"));
				nn.setOriginatingOrganisation(rs.getString("CMS_ORIG_ORGANISATION"));
				results.add(nn);
			}
		}
		return results;
	}
}