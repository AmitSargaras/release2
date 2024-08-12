/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.eventmonitor.documentexpiry;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.eventmonitor.IMonitorDAOResult;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.MonitorDaoResultRetrievalFailureException;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

/**
 * DAO for checklist item monitor.. Doc expiry date - system date = xx days (xx
 * should be -ve)
 * @author $Author: hmbao $
 * @version $Revision: 1.25 $
 * @since $Date: 2006/08/11 02:56:20 $ Tag: $Name: $
 */

public class ExpiredCCDocumentDAO extends AbstractDocExpiryDAO {
	private DBUtil dbUtil;

	private String getSQLStatement(IRuleParam ruleParam) {

		String theSQL = " SELECT s.doc_item_id, s.doc_item_ref, citem.doc_description, citem.status, citem.doc_date, "
				+ "		citem.document_code, citem.defer_extended_date, stage_checklist.CATEGORY, "
				+ "		citem.defer_expiry_date, citem.doc_reference, "
				+ "       citem.expiry_date, sci_le_sub_profile.lsp_short_name, "
				+ "       sci_le_sub_profile.lsp_le_id, cms_orig_country, cms_orig_organisation, "
				+ "       sci_le_main_profile.lmp_sgmnt_code_value AS SEGMENT, "
				+ "       calendar_days (cast(? as date), citem.expiry_date) AS daysdue, "
				+ "       stage_checklist.doc_orig_country AS security_location, "
				+ "       '' AS sci_security_dtl_id, "
				+ "		'' as subtype_name, "
				+ "		'' as security_maturity_date "
				+ "  FROM stage_checklist_item s LEFT OUTER JOIN CMS_CUST_DOC_ITEM cust ON cust.CHECKLIST_ITEM_REF_ID = s.DOC_ITEM_REF, "
				+ "       cms_checklist_item citem, " + "       TRANSACTION t, " + "       sci_lsp_lmt_profile, "
				+ "       sci_le_sub_profile, " + "       sci_le_main_profile, " + "       stage_checklist "
				+ " WHERE s.checklist_id = stage_checklist.checklist_id " + getCklBCACustConnStmt()
				+ "   AND t.transaction_type = 'CHECKLIST' "
				+ "   AND t.status NOT IN ('OBSOLETE', 'CLOSED', 'PENDING_CREATE') "
				+ "   AND stage_checklist.CATEGORY = 'CC' " + "   AND t.staging_reference_id = s.checklist_id "
				+ "   AND t.reference_id = citem.checklist_id " + "   AND citem.expiry_date IS NOT NULL "
				+ "   AND s.doc_item_ref = citem.doc_item_ref "
				+ "   AND (cust.status <> 'PERM_UPLIFTED' OR cust.status IS NULL) "
				+ "   AND citem.status = 'COMPLETED' "
				+ "   AND calendar_days (cast(? as date), citem.expiry_date) >= ?";

		StringBuffer strBuf = new StringBuffer();
		strBuf.append(theSQL);

		if (ruleParam.hasCountryCode()) {
			strBuf.append(" AND stage_checklist.DOC_ORIG_COUNTRY = ? ");
		}

		return strBuf.toString();

	}

	/**
	 * Search for a list of document items based on the criteria
	 * @param days- no of days
	 * @return SearchResult - the object that contains a list of call document
	 *         items that satisfy the search criteria
	 * @throws com.integrosys.cms.app.checklist.bus.CheckListException if errors
	 */
	public IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws MonitorDaoResultRetrievalFailureException {
		String sql = getSQLStatement(ruleParam);
		ResultSet rs = null;
		try {
			OBDateRuleParam param = (OBDateRuleParam) ruleParam;
			dbUtil = new DBUtil();
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
			DocExpiryDAOResult result = new DocExpiryDAOResult(results);
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
}
