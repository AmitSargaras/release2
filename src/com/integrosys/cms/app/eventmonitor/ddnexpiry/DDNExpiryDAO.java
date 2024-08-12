/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/ddnexpiry/DDNExpiryDAO.java,v 1.23 2005/05/27 07:57:56 wltan Exp $
 */

package com.integrosys.cms.app.eventmonitor.ddnexpiry;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAOResult;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.MonitorDaoResultRetrievalFailureException;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;
import com.integrosys.cms.app.eventmonitor.ddndue.OBDDNDueInfo;

/**
 * This same DAO is used for checking both before expiry and after expiry For
 * before expiry, the numOfDays in the OBDateRuleParam should be set to a
 * negative number
 * 
 * System date - ddn expiry date = xx days and SCC not issued
 */
public class DDNExpiryDAO extends AbstractMonitorDAO implements IMonitorDAO {

	private static final String DDN_EXPIRY_SQL = "SELECT ddn_id, sci_le_sub_profile.lsp_short_name,"
			+ "       sci_le_sub_profile.lsp_le_id,"
			+ "       sci_lsp_lmt_profile.llp_bca_ref_appr_date AS approvaldate,"
			+ "       lmt_profile_biz_days.num_biz_days AS daysdue,"
			+ // precomputed - no of biz days between sysdate and
			// llp_bca_ref_appr_date
			"       sci_le_main_profile.lmp_sgmnt_code_value AS SEGMENT,"
			//For DB2
//			+ "       current timestamp AS duedate, cms_orig_country, cms_orig_organisation"
			//For Oracle
			+ "       current_timestamp AS duedate, cms_orig_country, cms_orig_organisation"
			+ "  FROM cms_ddn_generated," + "       sci_le_sub_profile," + "       sci_lsp_lmt_profile,"
			+ "       sci_le_main_profile," + "       lmt_profile_biz_days"
			+ " WHERE cms_ddn_generated.cms_lsp_lmt_profile_id = sci_lsp_lmt_profile.cms_lsp_lmt_profile_id"
			+ "   AND sci_lsp_lmt_profile.cms_customer_id = sci_le_sub_profile.cms_le_sub_profile_id"
			+ "   AND sci_le_sub_profile.cms_le_main_profile_id = sci_le_main_profile.cms_le_main_profile_id"
			+ "   AND lmt_profile_biz_days.cms_lsp_lmt_profile_id = sci_lsp_lmt_profile.cms_lsp_lmt_profile_id"
			+ "   AND sci_lsp_lmt_profile.cms_tat_create_date IS NOT NULL"
			+ "   AND sci_le_sub_profile.cms_non_borrower_ind = 'N'"
			+ "   AND sci_lsp_lmt_profile.cms_scc_complete_ind = 'N'"
			+ "   AND sci_lsp_lmt_profile.cms_bca_renewal_ind = 'Y'"
			+ "   AND business_days(cast(? as date), cms_ddn_generated.deferred_to, cms_orig_country) "
			+ getEqualComparator()
			+ " ?"
			+ "   AND cms_ddn_generated.is_scc_issued = 'N'"
			+ "   AND (   (    EXISTS ("
			+ "                  SELECT 1"
			+ "                    FROM sci_lsp_appr_lmts LIMIT,"
			+ "                         cms_limit_security_map lsmap"
			+ "                   WHERE sci_lsp_lmt_profile.llp_lsp_id = LIMIT.lmt_lsp_id"
			+ "                     AND sci_lsp_lmt_profile.llp_le_id = LIMIT.lmt_le_id"
			+ "                     AND sci_lsp_lmt_profile.llp_id = LIMIT.lmt_llp_id"
			+ "                     AND LIMIT.cms_lsp_appr_lmts_id = lsmap.cms_lsp_appr_lmts_id)"
			+ "           AND NOT EXISTS ("
			+ "                  SELECT 1"
			+ "                    FROM cms_pscc_generated, TRANSACTION t"
			+ "                   WHERE t.transaction_type = 'PSCC'"
			+ "                     AND t.reference_id = cms_pscc_generated.pscc_id"
			+ "                     AND t.status != 'CLOSED'"
			+ "                     AND cms_pscc_generated.cms_lsp_lmt_profile_id = sci_lsp_lmt_profile.cms_lsp_lmt_profile_id)"
			+ "           AND NOT EXISTS ("
			+ "                  SELECT 1"
			+ "                    FROM cms_scc_generated"
			+ "                   WHERE cms_scc_generated.cms_lsp_lmt_profile_id = sci_lsp_lmt_profile.cms_lsp_lmt_profile_id)"
			+ "          )"
			+ "       OR (    NOT EXISTS ("
			+ "                  SELECT 1"
			+ "                    FROM sci_lsp_appr_lmts LIMIT,"
			+ "                         cms_limit_security_map lsmap"
			+ "                   WHERE sci_lsp_lmt_profile.llp_lsp_id = LIMIT.lmt_lsp_id"
			+ "                     AND sci_lsp_lmt_profile.llp_le_id = LIMIT.lmt_le_id"
			+ "                     AND sci_lsp_lmt_profile.llp_id = LIMIT.lmt_llp_id"
			+ "                     AND LIMIT.cms_lsp_appr_lmts_id = lsmap.cms_lsp_appr_lmts_id)"
			+ "           AND sci_lsp_lmt_profile.cms_ccc_complete_ind = 'N'" + "          )" + "      )";

	private String getSQLStatement(IRuleParam ruleParam) {
		if (ruleParam.hasCountryCode()) {
			StringBuffer strBuf = new StringBuffer();
			strBuf.append(DDN_EXPIRY_SQL);
			strBuf.append(" AND outerprofile.cms_orig_country = ? ");
			return strBuf.toString();
		}
		else {
			return DDN_EXPIRY_SQL;
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

			if (ruleParam.hasCountryCode()) {
				dbUtil.setString(3, param.getCountryCode());
			}

			rs = dbUtil.executeQuery();
			List results = processResultSet(rs);

			DDNExpiryDAOResult result = new DDNExpiryDAOResult(results);
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
				// nn.setApprovalDate(rs.getDate("LLP_BCA_REF_APPR_DATE"));
				nn.setApprovalDate(rs.getDate("APPROVALDATE"));
				nn.setDaysDue(rs.getInt("DAYSDUE"));
				nn.setDueDate(rs.getDate("DUEDATE"));
				nn.setOriginatingCountry(rs.getString("CMS_ORIG_COUNTRY"));
				nn.setOriginatingOrganisation(rs.getString("CMS_ORIG_ORGANISATION"));
				nn.setLocale(new Locale("en", nn.getOriginatingCountry()));
				results.add(nn);
			}
		}
		return results;
	}
}
