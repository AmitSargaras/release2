/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/bflacceptanceexpiry/BFLAcceptanceExpiryDAO.java,v 1.27 2005/05/27 05:40:47 wltan Exp $
 */

package com.integrosys.cms.app.eventmonitor.bflacceptanceexpiry;

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
import com.integrosys.cms.app.eventmonitor.draftbfldue.OBBFLDueInfo;

/**
 * If (System date - TAT expiry date) >= xx days and bfl accepted not stamped
 * TAT expiry date based on BCA received date and lookup TAT parameter table
 */
public class BFLAcceptanceExpiryDAO extends AbstractMonitorDAO implements IMonitorDAO {

	private static final String DUE_DAYS_SQL = "get_due_days_bflacceptance ((CASE outerprofile.CMS_BCA_RENEWAL_IND WHEN 'Y' THEN 'RENEWAL' WHEN 'N' THEN 'NEW' ELSE 'NEW' END), "
			+ "    sci_le_main_profile.lmp_sgmnt_code_value, "
			+ "    outerprofile.cms_orig_country, "
			+ "    outerprofile.cms_bca_local_ind )";

	private static final String BFL_ACCPT_EXPIRY_SQL = "SELECT outerprofile.cms_lsp_lmt_profile_id, sci_le_sub_profile.lsp_short_name, "
			+ "       sci_le_sub_profile.lsp_le_id, outerprofile.llp_bca_ref_appr_date, "
			+ "       sci_le_main_profile.lmp_sgmnt_code_value AS SEGMENT, "
			+ "       due_days (cast(? as date), outerprofile.llp_bca_ref_appr_date, "
			+ DUE_DAYS_SQL
			+ ", outerprofile.cms_orig_country ) AS daysdue, "
			+ "       add_days (outerprofile.llp_bca_ref_appr_date, "
			+ DUE_DAYS_SQL
			+ ", outerprofile.cms_orig_country ) AS duedate, "
			+ "       outerprofile.cms_orig_country, outerprofile.cms_orig_organisation "
			+ "  FROM sci_lsp_lmt_profile outerprofile, "
			+ "       sci_le_sub_profile, "
			+ "       sci_le_main_profile, "
			+ "       lmt_profile_biz_days "
			+ " WHERE outerprofile.cms_customer_id = sci_le_sub_profile.cms_le_sub_profile_id "
			+ "   AND sci_le_sub_profile.cms_le_main_profile_id = sci_le_main_profile.cms_le_main_profile_id "
			+ "   AND outerprofile.llp_le_id = sci_le_sub_profile.lsp_le_id "
			+ "   AND outerprofile.llp_lsp_id = sci_le_sub_profile.lsp_id "
			+ "   AND outerprofile.cms_tat_create_date IS NOT NULL "
			+ "   AND outerprofile.cms_bfl_required_ind = 'Y' "
			+ "   AND lmt_profile_biz_days.cms_lsp_lmt_profile_id = outerprofile.cms_lsp_lmt_profile_id "
			+
			// precomputed - no of biz days between sysdate and
			// llp_bca_ref_appr_date
			"   AND lmt_profile_biz_days.num_biz_days >=  "
			+ DUE_DAYS_SQL
			+ "   AND NOT EXISTS ( "
			+ "          SELECT innerprofile.cms_lsp_lmt_profile_id "
			+ "            FROM sci_lsp_lmt_profile innerprofile, vw_tat_entry "
			+ "           WHERE innerprofile.cms_lsp_lmt_profile_id = outerprofile.cms_lsp_lmt_profile_id "
			+ "             AND vw_tat_entry.limit_profile_id = innerprofile.cms_lsp_lmt_profile_id "
			+ "             AND vw_tat_entry.tat_service_code = 'CUSTOMER_ACCEPT_BFL' " + "   )";

	private String getSQLStatement(IRuleParam ruleParam) {
		if (ruleParam.hasCountryCode()) {
			StringBuffer strBuf = new StringBuffer();
			strBuf.append(BFL_ACCPT_EXPIRY_SQL);
			strBuf.append(" AND outerprofile.cms_orig_country = ? ");
			return strBuf.toString();
		}
		else {
			return BFL_ACCPT_EXPIRY_SQL;
		}
	}

	public IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws MonitorDaoResultRetrievalFailureException {

		ResultSet rs = null;
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			String sql = getSQLStatement(ruleParam);
			// DefaultLogger.debug(this, sql);

			OBDateRuleParam param = (OBDateRuleParam) ruleParam;

			dbUtil.setSQL(sql);
			dbUtil.setDate(1, new Date(param.getSysDate().getTime()));

			if (ruleParam.hasCountryCode()) {
				dbUtil.setString(2, param.getCountryCode());
			}

			rs = dbUtil.executeQuery();
			List results = processResultSet(rs);
			BFLAcceptanceExpiryDAOResult result = new BFLAcceptanceExpiryDAOResult(results);

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
			OBBFLDueInfo nn = null;
			while (rs.next()) {
				nn = new OBBFLDueInfo();
				nn.setLeName(rs.getString("LSP_SHORT_NAME"));
				nn.setLeID(rs.getString("LSP_LE_ID"));
				nn.setSegment(rs.getString("SEGMENT"));
				nn.setReceiptDate(rs.getDate("LLP_BCA_REF_APPR_DATE"));
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
