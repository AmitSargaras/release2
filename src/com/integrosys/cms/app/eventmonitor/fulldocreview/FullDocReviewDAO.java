/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/fulldocreview/FullDocReviewDAO.java,v 1.19 2005/11/30 09:49:19 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.fulldocreview;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAOResult;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.MonitorDaoResultRetrievalFailureException;
import com.integrosys.cms.app.eventmonitor.VelocityNotificationUtil;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;
import com.integrosys.cms.app.eventmonitor.common.OBFacilityInfo;

public class FullDocReviewDAO extends AbstractMonitorDAO implements IMonitorDAO {

	// todo: change >= to =
	// settle the last grade - need to get the last grade
	private static final String FULL_DOC_REV_SQL = "SELECT DISTINCT sci_le_sub_profile.lsp_le_id,cms_orig_country, cms_orig_organisation, "
			+ "                sci_le_sub_profile.lsp_short_name, "
			+ "                g1.lcg_le_id, "
			+ "                g1.lcg_crdt_grade_code_value AS currentgrade, "
			+ "                g1.lcg_crdt_grade_start_date AS currentgradedate, "
			+ "                CALENDAR_DAYS(CAST(? as date), CAST(g1.lcg_crdt_grade_start_date as date)) AS daysdue , "
			+ "                g1.last_update_date AS lastUpdateDate, "
			+ "                sci_le_main_profile.lmp_sgmnt_code_value AS SEGMENT, "
			+ "                SCI_LSP_APPR_LMTS.LMT_ID,"
			+ "				 COMMON_CODE_CATEGORY_ENTRY.ENTRY_NAME,"
			+ "                SCI_LSP_LMTS_XREF_MAP.LXM_ID "
			+ "           FROM sci_le_credit_grade g1, "
			+ "                sci_lsp_lmt_profile, "
			+ "                sci_le_sub_profile, "
			+ "                sci_le_main_profile, "
			+ "				 SCI_LSP_APPR_LMTS,"
			+ "				 COMMON_CODE_CATEGORY_ENTRY,"
			+ "				 SCI_LSP_LMTS_XREF_MAP "
			+ "          WHERE sci_lsp_lmt_profile.cms_customer_id = sci_le_sub_profile.cms_le_sub_profile_id "
			+ "            AND sci_le_sub_profile.cms_le_main_profile_id = sci_le_main_profile.cms_le_main_profile_id "
			+ "            AND sci_le_main_profile.cms_le_main_profile_id = g1.cms_main_profile_id "
			+ "			 AND SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID "
			+ " 			 AND SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = SCI_LSP_LMTS_XREF_MAP.CMS_LSP_APPR_LMTS_ID "
			+ " 			 AND SCI_LSP_APPR_LMTS.LMT_PRD_TYPE_NUM = COMMON_CODE_CATEGORY_ENTRY.CATEGORY_CODE "
			+ " 			 AND SCI_LSP_APPR_LMTS.LMT_PRD_TYPE_VALUE = COMMON_CODE_CATEGORY_ENTRY.ENTRY_CODE "
			+ "            AND sci_lsp_lmt_profile.cms_full_doc_review_ind = 'N' "
			+ "            AND g1.lcg_crdt_grade_code_num = '19' "
			+ "            AND g1.lcg_crdt_grade_code_value IN ('12', '13', '14') "
			+ "            AND sci_lsp_lmt_profile.cms_bca_status = 'ACTIVE' "
			+ "            AND DAYS(cast(? as timestamp)) >= DAYS(g1.lcg_crdt_grade_start_date) "
			+ "            AND 1 >= "
			+ "                   (SELECT COUNT (DISTINCT g2.lcg_crdt_grade_start_date) "
			+ "                      FROM sci_le_credit_grade g2 "
			+ "                     WHERE g2.lcg_le_id = g1.lcg_le_id "
			+ "                       AND g2.lcg_crdt_grade_start_date >= g1.lcg_crdt_grade_start_date) ";

	private String getSQLStatement(IRuleParam ruleParam) {
		if (ruleParam.hasCountryCode()) {
			StringBuffer strBuf = new StringBuffer();
			strBuf.append(FULL_DOC_REV_SQL);
			strBuf.append(" AND sci_lsp_lmt_profile.cms_orig_country = ? ");
			return strBuf.toString();
		}
		else {
			return FULL_DOC_REV_SQL;
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
			dbUtil.setDate(1, new Date(param.getSysDate().getTime()));
			dbUtil.setDate(2, new Date(param.getSysDate().getTime()));

			if (ruleParam.hasCountryCode()) {
				dbUtil.setString(3, ruleParam.getCountryCode());
			}

			rs = dbUtil.executeQuery();
			List results = processResultSet(rs);
			FullDocReviewDAOResult result = new FullDocReviewDAOResult(results);
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
	 * 
	 * @param ResultSet rs
	 * @return List - list of results
	 * @throws Exception
	 */
	private List processResultSet(ResultSet rs) throws Exception {
		HashMap leMap = new HashMap();
		if (rs != null) {
			OBFullDocReviewInfo nn = null;
			while (rs.next()) {
				String leID = rs.getString("LSP_LE_ID");
				nn = (OBFullDocReviewInfo) leMap.get(leID);
				if (nn == null) {
					nn = new OBFullDocReviewInfo();
					nn.setLeID(leID);
					nn.setLeName(rs.getString("LSP_SHORT_NAME"));
					nn.setSegment(rs.getString("SEGMENT"));
					nn.setOriginatingCountry(rs.getString("CMS_ORIG_COUNTRY"));
					nn.setOriginatingOrganisation(rs.getString("CMS_ORIG_ORGANISATION"));
					nn.setPreviousCreditGrade("-");
					nn.setCurrentCreditGrade(rs.getString("CURRENTGRADE"));
					nn.setDaysDue(rs.getInt("DAYSDUE"));
					Date lastUpdateDate = rs.getDate("LASTUPDATEDATE");
					if (lastUpdateDate != null) {
						nn.setDateDownGraded(lastUpdateDate);
					}
					else {
						nn.setDateDownGraded(rs.getDate("CURRENTGRADEDATE"));
					}
					leMap.put(leID, nn);
				}
				String facID = rs.getString("LMT_ID");
				if (facID == null) {
					continue;
				}
				HashMap facMap = nn.getFacilityMap();
				OBFacilityInfo obFac = (OBFacilityInfo) facMap.get(facID);
				if (obFac == null) {
					obFac = new OBFacilityInfo();
					obFac.setFacilityID(facID);
					obFac.setFacilityDesc(rs.getString("ENTRY_NAME"));
					facMap.put(facID, obFac);
				}
				if (obFac == null) {
					continue;
				}
				String accID = rs.getString("LXM_ID");
				HashMap accIDMap = obFac.getAccIDMap();
				if (accIDMap.get(accID) == null) {
					accIDMap.put(accID, accID);
				}
			}
		}
		return VelocityNotificationUtil.convertMap2List(leMap);
	}
}
