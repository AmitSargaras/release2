/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/cleantypebfldue/CleanTypeBFLDueDAO.java,v 1.23 2005/12/06 07:11:16 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.cleantypebfldue;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAOResult;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.MonitorDaoResultRetrievalFailureException;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

/**
 * System date - date of draft bfl > = xx days and clean type BFL not issued
 */
public class CleanTypeBFLDueDAO extends AbstractMonitorDAO implements IMonitorDAO {

	private static final String DUE_DAYS_SQL = " GET_DUE_DAYS_CLEANBFL ((CASE outerprofile.CMS_BCA_RENEWAL_IND WHEN 'Y' THEN 'RENEWAL' WHEN 'N' THEN 'NEW' ELSE 'NEW' END),"
			+ "	  sci_le_main_profile.lmp_sgmnt_code_value,"
			+ "	  outerprofile.cms_orig_country,"
			+ "	  outerprofile.cms_bca_local_ind)";

	private static final String CLEANTYPE_BFL_DUE_SQL = "SELECT outerprofile.cms_lsp_lmt_profile_id, sci_le_sub_profile.lsp_short_name, "
			+ "sci_le_sub_profile.lsp_le_id, outerprofile.llp_bca_ref_appr_date, "
			+ "sci_le_main_profile.lmp_sgmnt_code_value AS SEGMENT, "
			+ "DUE_DAYS(CAST(? AS DATE), date(vw_tat_entry.tat_time_stamp) + (cast((SELECT parameter_value FROM bus_param WHERE parameter_pk=2) as int)/24) days, "
			+ "0, cms_orig_country) AS daysdue, "
			+ "ADD_DAYS(date(vw_tat_entry.tat_time_stamp) + (cast((SELECT parameter_value FROM bus_param WHERE parameter_pk=2) as int)/24) days, "
			+ "0, cms_orig_country) AS duedate, "
			+ "cms_orig_country, cms_orig_organisation, vw_tat_entry.tat_time_stamp "
			+ "FROM sci_lsp_lmt_profile outerprofile, "
			+ "vw_tat_entry, "
			+ "sci_le_sub_profile, "
			+ "sci_le_main_profile "
			+ "WHERE outerprofile.cms_lsp_lmt_profile_id = vw_tat_entry.limit_profile_id "
			+ "AND outerprofile.cms_customer_id = sci_le_sub_profile.cms_le_sub_profile_id "
			+ "AND sci_le_sub_profile.cms_le_main_profile_id = sci_le_main_profile.cms_le_main_profile_id "
			+ "AND days(CAST(? AS DATE)) - days(vw_tat_entry.tat_time_stamp) >= (cast((SELECT parameter_value FROM bus_param WHERE parameter_pk=2) as int)/24)  "
			+ "AND vw_tat_entry.tat_service_code = '"
			+ ICMSConstant.TAT_CODE_ACK_REC_DRAFT_BFL
			+ "' "
			+ "AND Outerprofile.Cms_Bca_Status = 'ACTIVE' "
			+ "AND Outerprofile.Cms_Tat_Create_Date IS NOT NULL "
			+ "AND Outerprofile.Cms_Bfl_Required_Ind = 'Y' "
			+ "AND NOT EXISTS ( "
			+ "SELECT 1 "
			+ "FROM sci_lsp_lmt_profile innerprofile, vw_tat_entry "
			+ "WHERE innerprofile.cms_lsp_lmt_profile_id = outerprofile.cms_lsp_lmt_profile_id "
			+ "AND innerprofile.cms_lsp_lmt_profile_id = vw_tat_entry.limit_profile_id "
			+ "AND vw_tat_entry.tat_service_code = '" + ICMSConstant.TAT_CODE_ISSUE_CLEAN_BFL + "') ";

	private String getSQLStatement(IRuleParam ruleParam) {
		if (ruleParam.hasCountryCode()) {
			StringBuffer strBuf = new StringBuffer();
			strBuf.append(CLEANTYPE_BFL_DUE_SQL);
			strBuf.append(" AND outerprofile.cms_orig_country = ? ");
			return strBuf.toString();
		}
		else {
			return CLEANTYPE_BFL_DUE_SQL;
		}
	}

	public IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws MonitorDaoResultRetrievalFailureException {

		ResultSet rs = null;
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			String sql = getSQLStatement(ruleParam);
			// DefaultLogger.debug(this, "getInitialSet sql " + sql);

			OBDateRuleParam param = (OBDateRuleParam) ruleParam;
			dbUtil.setSQL(sql);

			dbUtil.setDate(1, new java.sql.Date(param.getSysDate().getTime()));
			dbUtil.setDate(2, new java.sql.Date(param.getSysDate().getTime()));

			if (ruleParam.hasCountryCode()) {
				dbUtil.setString(3, param.getCountryCode());
			}

			rs = dbUtil.executeQuery();
			List results = processResultSet(rs);

			CleanTypeBFLDueDAOResult result = new CleanTypeBFLDueDAOResult(results);
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
			OBCleanTypeBFLDueInfo nn = null;
			while (rs.next()) {
				nn = new OBCleanTypeBFLDueInfo();
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
