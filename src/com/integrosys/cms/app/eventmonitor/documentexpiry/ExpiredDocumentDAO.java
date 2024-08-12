/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.eventmonitor.documentexpiry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.integrosys.cms.app.eventmonitor.IMonitorDAOResult;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.MonitorDaoResultRetrievalFailureException;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

/**
 * DAO for checklist item monitor.. Doc expiry date - system date = xx days (xx
 * should be -ve)
 * @author $Author: lini $
 * @version $Revision: 1.21 $
 * @since $Date: 2006/11/15 10:07:02 $ Tag: $Name: $
 */

public class ExpiredDocumentDAO extends AbstractDocExpiryDAO {

	private String getCheckListItemSQL() {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT s.doc_item_id, ");
		buf.append("       s.doc_item_ref, ");
		buf.append("       citem.doc_description, ");
		buf.append("       citem.status, ");
		buf.append("       citem.doc_date, ");
		buf.append("       citem.document_code, ");
		buf.append("       citem.defer_extended_date, ");
		buf.append("       stage_checklist.category, ");
		buf.append("       citem.defer_expiry_date, ");
		buf.append("       citem.doc_reference, ");
		buf.append("       citem.expiry_date, ");
		buf.append("       '' AS lsp_short_name, ");
		buf.append("       '' AS lsp_le_id, ");
		buf.append("       doc_orig_country AS cms_orig_country, ");
		buf.append("       '' AS cms_orig_organisation, ");
		buf.append("       '' AS segment, ");
		buf.append("       calendar_days(DATE(CAST(? as TIMESTAMP)) , DATE(citem.expiry_date)) AS daysdue, ");
		buf.append("       'dummy' AS security_location, ");
		buf.append("       '' AS sci_security_dtl_id, ");
		buf.append("       '' AS subtype_name, ");
		buf.append("       CAST(NULL AS TIMESTAMP)  AS security_maturity_date ");
		buf.append("  FROM stage_checklist, ");
		buf.append("       stage_checklist_item s, ");
		buf.append("       cms_checklist_item citem, ");
		buf.append("       transaction t ");
		buf.append(" WHERE s.checklist_id = stage_checklist.checklist_id ");
		buf.append("   AND t.transaction_type = 'CHECKLIST' ");
		buf.append("   AND t.status NOT IN ('OBSOLETE','CLOSED','PENDING_CREATE') ");
		buf.append("   AND t.staging_reference_id = s.checklist_id ");
		buf.append("   AND t.reference_id = citem.checklist_id ");
		buf.append("   AND citem.expiry_date IS NOT NULL ");
		buf.append("   AND citem.is_deleted = 'N' ");
		buf.append("   AND s.doc_item_ref = citem.doc_item_ref ");
		//buf.append("   AND citem.status = 'COMPLETED' ");
		buf.append("   AND calendar_days(DATE(CAST(? as TIMESTAMP)) , DATE(citem.expiry_date)) >= ? ");

		return buf.toString();
	}

	/**
	 * Search for a list of document items based on the criteria
	 * @param ruleParam - rule parameter
	 * @return SearchResult - the object that contains a list of call document
	 *         items that satisfy the search criteria
	 * @throws com.integrosys.cms.app.checklist.bus.CheckListException if errors
	 */
	public IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws MonitorDaoResultRetrievalFailureException {
		OBDateRuleParam param = (OBDateRuleParam) ruleParam;

		StringBuffer buf = new StringBuffer(getCheckListItemSQL());

		List params = new ArrayList();
		params.add(new Date());
		params.add(new Date());
		params.add(new Integer(param.getNumOfDays()));

		if (ruleParam.hasCountryCode()) {
			buf.append(" AND stage_checklist.DOC_ORIG_COUNTRY = ?");
			params.add(ruleParam.getCountryCode());
		}

		List daoResultList = (List) getJdbcTemplate().query(buf.toString(), params.toArray(), new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return processResultSet(rs);
			}
		});

		DocExpiryDAOResult result = new DocExpiryDAOResult(daoResultList);
		return result;
	}
}
