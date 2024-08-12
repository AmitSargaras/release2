/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/guarantornotice/GuarantorNoticeDAO.java,v 1.21 2005/10/17 10:16:42 jychong Exp $
 */

package com.integrosys.cms.app.eventmonitor.guarantornotice;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAOResult;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.MonitorDaoResultRetrievalFailureException;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

public class GuarantorNoticeDAO extends AbstractMonitorDAO implements IMonitorDAO {

	private String getSQLStatement(IRuleParam ruleParam) {

		String theSQL = "SELECT DISTINCT cms_guarantee.cms_collateral_id, cms_guarantee.guarantee_date, "
				+ "                cms_guarantee.guarantee_amt, cms_guarantee.currency_code, "
				+ "                cms_security.cmv_currency, cms_security.cmv, "
				+ "                cms_security.type_name, cms_security.subtype_name, "
				+ "                cms_security.security_sub_type_id, cms_orig_country, "
				+ "                cms_orig_organisation, sci_le_sub_profile.lsp_short_name, "
				+ "                sci_le_sub_profile.lsp_le_id, "
				+ "                sci_le_main_profile.lmp_sgmnt_code_value AS SEGMENT, "
				+ "                calendar_days " + "                     (cast(? as date), "
				+ "                      cms_security.security_maturity_date " + "                     ) AS daysdue "
				+ "           FROM cms_guarantee, " + "                cms_security, " + "	  			 sci_le_main_profile, "
				+ "                sci_le_sub_profile, " + "                sci_lsp_lmt_profile, "
				+ "                sci_lsp_appr_lmts, " + "                cms_limit_security_map "
				+ "          WHERE sci_lsp_lmt_profile.cms_bca_status <> 'DELETED' "
				+ "            AND cms_guarantee.cms_collateral_id = "
				+ "                                                cms_security.cms_collateral_id "
				+ "            AND cms_security.type_name = 'Guarantees' "
				+ "            AND cms_security.cms_collateral_id = "
				+ "                                      cms_limit_security_map.cms_collateral_id "
				+ "            AND cms_limit_security_map.cms_lsp_appr_lmts_id = "
				+ "                                        sci_lsp_appr_lmts.cms_lsp_appr_lmts_id "
				+ "            AND sci_lsp_lmt_profile.cms_lsp_lmt_profile_id = "
				+ "                                        sci_lsp_appr_lmts.cms_limit_profile_id "
				+ "            AND sci_le_sub_profile.cms_le_sub_profile_id = "
				+ "                                           sci_lsp_lmt_profile.cms_customer_id "
				+ "            AND sci_le_sub_profile.cms_le_main_profile_id = "
				+ "                                    sci_le_main_profile.cms_le_main_profile_id	"
				+ "			 AND calendar_days (cast(? as date), "
				+ "                               cms_guarantee.guarantee_date) ";

		StringBuffer strBuf = new StringBuffer();

		strBuf.append(theSQL).append(getEqualComparator()).append(" ? ");

		if (ruleParam.hasCountryCode()) {
			strBuf.append("AND cms_security.security_location = ? ");
		}
		// .append(
		// "AND SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = CMS_SCC_GENERATED.CMS_LSP_LMT_PROFILE_ID "
		// );
		// .append("AND sci_lsp_lmt_profile.CMS_SCC_COMPLETE_IND = 'Y' ");
		return strBuf.toString();

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
			dbUtil.setDate(2, new Date(param.getSysDate().getTime()));
			dbUtil.setInt(3, param.getNumOfDays());

			if (ruleParam.hasCountryCode()) {
				dbUtil.setString(4, ruleParam.getCountryCode());
			}

			rs = dbUtil.executeQuery();
			List results = processResultSet(rs);
			GuarantorNoticeDAOResult result = new GuarantorNoticeDAOResult(results);
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
			OBGuarantorNoticeInfo nn = null;
			while (rs.next()) {
				nn = new OBGuarantorNoticeInfo();
				nn.setLeID(rs.getString("LSP_LE_ID"));
				nn.setLeName(rs.getString("LSP_SHORT_NAME"));
				nn.setSegment(rs.getString("SEGMENT"));
				nn.setOriginatingCountry(rs.getString("CMS_ORIG_COUNTRY"));
				nn.setOriginatingOrganisation(rs.getString("CMS_ORIG_ORGANISATION"));
				nn.setSubType(rs.getString("SECURITY_SUB_TYPE_ID"));
				nn.setDaysDue(rs.getInt("DAYSDUE"));
				nn.setGuaranteeDate(rs.getDate("GUARANTEE_DATE"));
				String ccy = rs.getString("CURRENCY_CODE");
				String amount = rs.getString("GUARANTEE_AMT");

				if ((ccy != null) && !"".equals(ccy) && (amount != null)) {
					nn.setGuaranteeAmount(new Amount(Double.valueOf(amount).doubleValue(), ccy));
				}
				else {
					nn.setGuaranteeAmount(new Amount(0, ""));
				}
				results.add(nn);
			}
		}

		return results;
	}
}
