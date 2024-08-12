/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/coverageexcess/CoverageExcessDAO.java,v 1.16 2006/08/24 06:33:22 hmbao Exp $
 */

package com.integrosys.cms.app.eventmonitor.coverageexcess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAO;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAOResult;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.MonitorDaoResultRetrievalFailureException;
import com.integrosys.cms.app.eventmonitor.OBRuleParam;
import com.integrosys.cms.app.eventmonitor.coverageshortfall.MonCoverageShortfall;

public class CoverageExcessDAO extends AbstractMonitorDAO implements IMonitorDAO {
	private String getSQLString(String comparator, boolean hasCountryCode) {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("SELECT DISTINCT z.cms_lsp_lmt_profile_id, SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, ").append(
				"SCI_LE_MAIN_PROFILE.LMP_SGMNT_CODE_VALUE AS SEGMENT, ");
		strBuf.append("SCI_LE_SUB_PROFILE.LSP_LE_ID, ");
		strBuf.append("cms_orig_country, cms_orig_organisation ");
		strBuf.append("FROM ( ");
		strBuf.append("SELECT b.CMS_LSP_LMT_PROFILE_ID, b.CMS_CUSTOMER_ID ");
		strBuf.append("FROM SCI_LSP_LMT_PROFILE b ");
		// strBuf.append("WHERE cms_bca_complete_ind = 'Y' ")
		strBuf.append("WHERE cms_req_sec_coverage IS NOT NULL ");
		strBuf.append("AND cms_req_sec_coverage > 0 ");
		strBuf.append("AND cms_act_sec_coverage IS NOT NULL ");
		strBuf.append("AND cms_act_sec_coverage > 0 ");
		strBuf.append("AND cms_act_sec_coverage ");
		strBuf.append(comparator + " ");
		strBuf.append("cms_req_sec_coverage ");
		strBuf.append("UNION ");
		strBuf.append("SELECT DISTINCT b.CMS_LSP_LMT_PROFILE_ID, b.CMS_CUSTOMER_ID ");
		strBuf.append("FROM SCI_LSP_APPR_LMTS a, ");
		strBuf.append("SCI_LSP_LMT_PROFILE b, ");
		strBuf.append("CMS_LIMIT_CHARGE_MAP c, ");
		strBuf.append("CMS_CHARGE_DETAIL d ");
		strBuf.append("WHERE a.cms_limit_profile_id = b.cms_lsp_lmt_profile_id ");
		strBuf.append("AND a.cms_lsp_appr_lmts_id = c.cms_lsp_appr_lmts_id ");
		strBuf.append("AND c.CUSTOMER_CATEGORY='MB' ");
		strBuf.append("AND c.charge_detail_id = d.charge_detail_id ");
		strBuf.append("AND d.charge_type = 'S' ");
		// strBuf.append("AND b.cms_bca_complete_ind = 'Y' ")
		strBuf.append("AND a.cms_req_sec_coverage IS NOT NULL ");
		strBuf.append("AND a.cms_req_sec_coverage > 0 ");
		strBuf.append("AND a.cms_act_sec_coverage IS NOT NULL ");
		strBuf.append("AND a.cms_act_sec_coverage > 0 ");
		strBuf.append("AND a.cms_act_sec_coverage " + comparator + " a.cms_req_sec_coverage ");
		strBuf.append("UNION ");
		strBuf.append("SELECT DISTINCT b.CMS_LSP_LMT_PROFILE_ID, cl.CMS_CUSTOMER_ID ");
		strBuf.append("FROM SCI_LSP_APPR_LMTS a,");
		strBuf.append("SCI_LSP_LMT_PROFILE b,");
		strBuf.append("SCI_LSP_CO_BORROW_LMT cl,");
		strBuf.append("CMS_LIMIT_CHARGE_MAP c,");
		strBuf.append("CMS_CHARGE_DETAIL d ");
		strBuf.append("WHERE a.cms_limit_profile_id = b.cms_lsp_lmt_profile_id ");
		strBuf.append("AND a.cms_lsp_appr_lmts_id = cl.CMS_LIMIT_ID ");
		strBuf.append("AND a.cms_lsp_appr_lmts_id = c.cms_lsp_appr_lmts_id ");
		strBuf.append("and c.CUSTOMER_CATEGORY='CB' ");
		strBuf.append("AND c.charge_detail_id = d.charge_detail_id ");
		strBuf.append("AND d.charge_type = 'S' ");
		strBuf.append("AND a.cms_req_sec_coverage IS NOT NULL ");
		strBuf.append("AND a.cms_req_sec_coverage > 0 ");
		strBuf.append("AND a.cms_act_sec_coverage IS NOT NULL ");
		strBuf.append("AND a.cms_act_sec_coverage > 0 ");
		strBuf.append("AND a.cms_act_sec_coverage " + comparator + " a.cms_req_sec_coverage");
		strBuf.append(") z, ");
		strBuf.append("SCI_LSP_LMT_PROFILE, SCI_LE_SUB_PROFILE, SCI_LE_MAIN_PROFILE ");
		strBuf.append("WHERE SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = z.cms_lsp_lmt_profile_id ");
		strBuf.append("AND SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID = z.cms_customer_id ");
		strBuf.append("AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID ");
		if (hasCountryCode) {
			strBuf.append("AND cms_orig_country= ? ");
		}
		return strBuf.toString();
	}

	public IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws MonitorDaoResultRetrievalFailureException {

		boolean hasCountryCode = ruleParam.hasCountryCode();
		OBRuleParam param = (OBRuleParam) ruleParam;
		String sql = "";
		if (param.getRuleID().equals(MonCoverageExcess.RULE_COVERAGE_EXCESS)) {
			sql = getSQLString(">", hasCountryCode);
		}
		else if (param.getRuleID().equals(MonCoverageShortfall.RULE_COVERAGE_SHORTFALL)) {
			sql = getSQLString("<", hasCountryCode);
		}
		else {
			throw new EventMonitorException("Unable to determine coverage rule, check rule ID set in ruleParam");
		}

		List argList = new ArrayList();
		if (hasCountryCode) {
			argList.add(ruleParam.getCountryCode());
		}

		List resultList = getJdbcTemplate().query(sql, argList.toArray(), new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBCoverageInfo coverageInfo = new OBCoverageInfo();

				coverageInfo.setLeID(rs.getString("LSP_LE_ID"));
				coverageInfo.setLeName(rs.getString("LSP_SHORT_NAME"));
				coverageInfo.setSegment(rs.getString("SEGMENT"));
				coverageInfo.setOriginatingCountry(rs.getString("CMS_ORIG_COUNTRY"));
				coverageInfo.setOriginatingOrganisation(rs.getString("CMS_ORIG_ORGANISATION"));
				coverageInfo.setLimitProfileID(rs.getLong("cms_lsp_lmt_profile_id"));

				return coverageInfo;
			}
		});

		return new CoverageDAOResult(resultList);

	}
}
