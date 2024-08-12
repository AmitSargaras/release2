/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/insuranceexpiry/InsuranceExpiryDAO.java,v 1.23 2005/05/27 05:35:53 wltan Exp $
 */

package com.integrosys.cms.app.eventmonitor.insuranceexpiry;

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
import com.integrosys.cms.app.eventmonitor.common.OBDocumentExpiryInfo;

public class InsuranceExpiryDAO extends AbstractMonitorDAO implements IMonitorDAO {

	private String getSQLStatement(IRuleParam ruleParam) {

		StringBuffer strBuf = new StringBuffer();
		strBuf
				.append("SELECT DISTINCT CMS_CHECKLIST_ITEM.CHECKLIST_ID, CMS_CHECKLIST_ITEM.DOC_DESCRIPTION, ")
				.append("CMS_CHECKLIST_ITEM.DOC_DATE,CMS_CHECKLIST_ITEM.EXPIRY_DATE, ")
				.append("calendar_days(cast(? as date), CMS_CHECKLIST_ITEM.expiry_date ) AS daysdue, ")
				.append("cms_monitor_doc_code.document_code, ")
				.append("cms_orig_country, cms_orig_organisation,SCI_LE_MAIN_PROFILE.LMP_SGMNT_CODE_VALUE AS SEGMENT, ")
				.append("SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, SCI_LE_SUB_PROFILE.LSP_LE_ID ")
				.append("FROM CMS_CHECKLIST_ITEM, CMS_CHECKLIST, SCI_LSP_LMT_PROFILE,SCI_LE_SUB_PROFILE, ")
				.append("SCI_LSP_APPR_LMTS, SCI_LE_MAIN_PROFILE, cms_monitor_doc_code ")
				.append("WHERE CMS_CHECKLIST_ITEM.CHECKLIST_ID = CMS_CHECKLIST.CHECKLIST_ID ")
				.append("AND CMS_CHECKLIST.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_lsp_lmt_profile_id ")
				.append("AND SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID = SCI_LSP_LMT_PROFILE.cms_customer_id ")
				.append("AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID ")
				.append("AND CMS_CHECKLIST.STATUS != 'OBSOLETE' AND CMS_CHECKLIST.STATUS != 'CLOSED' ")
				.append("AND calendar_days(cast(? as date), CMS_CHECKLIST_ITEM.EXPIRY_DATE) ")
				.append(getEqualComparator())
				.append(" ? ")
				.append(
						"AND (CMS_CHECKLIST_ITEM.MONITOR_TYPE = 'PREM_REC' OR CMS_CHECKLIST_ITEM.MONITOR_TYPE = 'INS_POLICY') ");

		if (ruleParam.hasCountryCode()) {
			strBuf.append("AND cms_orig_country = ? ");
		}
		// .append(
		// "AND CMS_CHECKLIST_ITEM.DOCUMENT_CODE = cms_monitor_doc_code.document_code "
		// )
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
			OBDocumentExpiryRuleParam param = (OBDocumentExpiryRuleParam) ruleParam;
			String sql = getSQLStatement(ruleParam);
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			dbUtil.setDate(1, new Date(param.getSysDate().getTime()));
			dbUtil.setDate(2, new Date(param.getSysDate().getTime()));
			dbUtil.setInt(3, param.getNumOfDays());
			// dbUtil.setString(4, param.getDocumentCode());

			if (ruleParam.hasCountryCode()) {
				dbUtil.setString(4, param.getCountryCode());
			}

			rs = dbUtil.executeQuery();
			List results = processResultSet(rs);
			InsuranceExpiryDAOResult result = new InsuranceExpiryDAOResult(results);
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
			OBDocumentExpiryInfo nn = null;
			while (rs.next()) {
				nn = new OBDocumentExpiryInfo();
				nn.setLeID(rs.getString("LSP_LE_ID"));
				nn.setLeName(rs.getString("LSP_SHORT_NAME"));
				nn.setSegment(rs.getString("SEGMENT"));
				nn.setDaysDue(rs.getInt("DAYSDUE"));
				nn.setDocumentDate(rs.getDate("DOC_DATE"));
				nn.setDocumentType(rs.getString("DOC_DESCRIPTION"));
				nn.setExpiryDate(rs.getDate("EXPIRY_DATE"));
				nn.setOriginatingCountry(rs.getString("CMS_ORIG_COUNTRY"));
				nn.setLocale(new Locale("en", nn.getOriginatingCountry()));
				results.add(nn);
			}
		}

		return results;
	}
}
