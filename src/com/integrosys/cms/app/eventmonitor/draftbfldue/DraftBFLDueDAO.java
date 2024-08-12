/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/draftbfldue/DraftBFLDueDAO.java,v 1.21 2005/05/27 05:35:53 wltan Exp $
 */

package com.integrosys.cms.app.eventmonitor.draftbfldue;

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
 * System date - bca receipt date >= xx days and draft bfl not issued
 */
public class DraftBFLDueDAO extends AbstractMonitorDAO implements IMonitorDAO {

	private static final String DUE_DAYS_SQL = "  CAST (GET_DUE_DAYS_DRAFTBFL ((CASE outerprofile.CMS_BCA_RENEWAL_IND  WHEN 'Y' THEN 'RENEWAL' WHEN 'N' THEN 'NEW' ELSE 'NEW' END), "
			+ "               sci_le_main_profile.lmp_sgmnt_code_value,"
			+ "               outerprofile.cms_orig_country,"
			+ "               outerprofile.cms_bca_local_ind ) AS INT)";

	private static final String DRAFT_BFL_DUE_SQL = "SELECT outerprofile.cms_lsp_lmt_profile_id, sci_le_sub_profile.lsp_short_name,"
			+ "       sci_le_sub_profile.lsp_le_id, outerprofile.llp_bca_ref_appr_date,"
			+ "       DUE_DAYS(cast(? as date), cast(outerprofile.llp_bca_ref_appr_date as date), "
			+ DUE_DAYS_SQL
			+ ", outerprofile.cms_orig_country) AS daysdue,"
			+ "       ADD_DAYS(cast(outerprofile.llp_bca_ref_appr_date as date),"
			+ DUE_DAYS_SQL
			+ ", outerprofile.cms_orig_country) AS duedate,"
			+ "       outerprofile.cms_orig_country, outerprofile.cms_orig_organisation,"
			+ "       sci_le_main_profile.lmp_sgmnt_code_value AS SEGMENT"
			+ "  FROM sci_lsp_lmt_profile outerprofile,"
			+ "       sci_le_sub_profile,"
			+ "       sci_le_main_profile,"
			+ "       lmt_profile_biz_days"
			+ " WHERE"
			+ "   outerprofile.cms_customer_id = sci_le_sub_profile.cms_le_sub_profile_id"
			+ "   AND sci_le_sub_profile.cms_le_main_profile_id = sci_le_main_profile.cms_le_main_profile_id"
			+ "   AND outerprofile.llp_le_id = sci_le_sub_profile.lsp_le_id"
			+ "   AND outerprofile.llp_lsp_id = sci_le_sub_profile.lsp_id"
			+ "   AND outerprofile.cms_bca_status = 'ACTIVE'"
			+ "   AND outerprofile.cms_tat_create_date IS NOT NULL"
			+ "   AND outerprofile.cms_bfl_required_ind = 'Y'"
			+ "   AND lmt_profile_biz_days.cms_lsp_lmt_profile_id = outerprofile.cms_lsp_lmt_profile_id"
			+
			// precomputed - no of biz days between sysdate and
			// llp_bca_ref_appr_date
			"   AND lmt_profile_biz_days.num_biz_days >="
			+ DUE_DAYS_SQL
			+ "   AND NOT EXISTS ("
			+ "          SELECT innerprofile.cms_lsp_lmt_profile_id"
			+ "            FROM sci_lsp_lmt_profile innerprofile, vw_tat_entry"
			+ "           WHERE innerprofile.cms_lsp_lmt_profile_id = outerprofile.cms_lsp_lmt_profile_id"
			+ "             AND innerprofile.cms_lsp_lmt_profile_id = vw_tat_entry.limit_profile_id"
			+ "             AND vw_tat_entry.tat_service_code in ('ISSUE_DRAFT_BFL', 'SPECIAL_ISSUE_CLEAN_BFL')"
			+ "		)";

	public String getSQLStatement(IRuleParam ruleParam) {
		if (ruleParam.hasCountryCode()) {
			StringBuffer strBuf = new StringBuffer();
			strBuf.append(DRAFT_BFL_DUE_SQL);
			strBuf.append(" AND outerprofile.cms_orig_country = ? ");
			return strBuf.toString();
		}
		else {
			return DRAFT_BFL_DUE_SQL;
		}
	}

	public IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws MonitorDaoResultRetrievalFailureException {

		ResultSet rs = null;
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			OBDateRuleParam param = (OBDateRuleParam) ruleParam;
			String sql = getSQLStatement(ruleParam);
			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setDate(1, new java.sql.Date(param.getSysDate().getTime()));

			if (ruleParam.hasCountryCode()) {
				dbUtil.setString(2, ruleParam.getCountryCode());
			}

			rs = dbUtil.executeQuery();
			List results = processResultSet(rs);
			DraftBFLDueDAOResult result = new DraftBFLDueDAOResult(results);
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
		OBBFLDueInfo nn = null;
		if (rs == null) {
			DefaultLogger.debug(this, "rs is null");
		}

		if (rs != null) {
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

	public static void main(String[] args) {
		IRuleParam param = new OBDateRuleParam();
//		System.out.println(">>>>>>>>> no ctry : " + new DraftBFLDueDAO().getSQLStatement(param));
	}
}
