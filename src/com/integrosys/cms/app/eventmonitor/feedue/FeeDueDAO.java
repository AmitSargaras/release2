/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collateralevaluationdue/EvaluationDueDAO.java,v 1.32 2006/08/21 02:46:34 hmbao Exp $
 */

package com.integrosys.cms.app.eventmonitor.feedue;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
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

/**
 * Gte - government linked has guarantee fee details. DAO to retrieve all fee
 * due to expire in x number of days. There can be multiple notifications for
 * the same security if there are multiple fees due at the same time
 * 
 * @author BaoHongMan
 * @version R1.5
 * @Purpose:
 * @Description:
 * @Tag 
 *      com.integrosys.cms.app.eventmonitor.collateralevaluationdue.EvaluationDueDAO
 *      .java
 * @since 2006-7-20
 */
public class FeeDueDAO extends AbstractMonitorDAO implements IMonitorDAO {

	public IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws MonitorDaoResultRetrievalFailureException {
		ResultSet rs = null;
		DBUtil dbUtil = null;

		try {
			dbUtil = new DBUtil();
			OBDateRuleParam param = (OBDateRuleParam) ruleParam;
			String sqlBuffer = getSQLStatement();
			DefaultLogger.debug(this, sqlBuffer);
			dbUtil.setSQL(sqlBuffer);
			dbUtil.setDate(1, new java.sql.Date(param.getSysDate().getTime()));
			dbUtil.setInt(2, param.getNumOfDays());
			rs = dbUtil.executeQuery();
			List results = processResultSet(rs);
			FeeDueDAOResult result = new FeeDueDAOResult(results);
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

	private String getSQLStatement() {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("select lsp.LSP_LE_ID as LE_ID, lsp.LSP_SHORT_NAME as CUST_NAME, sec.SCI_SECURITY_DTL_ID AS SECURITY_ID, ");
		sqlBuffer.append("sec.TYPE_NAME, sec.SUBTYPE_NAME, sec.SCI_SECURITY_CURRENCY as CCY,  ");
		sqlBuffer.append("cgc_fee.GUARANTEE_FEE, cgc_fee.EXP_DATE ");
		sqlBuffer.append("from cms_fee_details cgc_fee, cms_security sec, ");
		sqlBuffer.append("cms_limit_security_map lsm, sci_lsp_appr_lmts lmt, ");
		sqlBuffer.append("sci_lsp_lmt_profile aa, sci_le_sub_profile lsp ");
		sqlBuffer.append("where cgc_fee.CMS_COLLATERAL_ID = sec.CMS_COLLATERAL_ID ");
		sqlBuffer.append("and sec.CMS_COLLATERAL_ID = lsm.CMS_COLLATERAL_ID ");
		sqlBuffer.append("and lsm.CMS_LSP_APPR_LMTS_ID = lmt.CMS_LSP_APPR_LMTS_ID ");
		sqlBuffer.append("and lmt.CMS_LIMIT_PROFILE_ID = aa.CMS_LSP_LMT_PROFILE_ID ");
		sqlBuffer.append("and aa.CMS_CUSTOMER_ID = lsp.CMS_LE_SUB_PROFILE_ID ");
		sqlBuffer.append("and sec.SECURITY_LOCATION = 'MY' ");
		sqlBuffer.append("and calendar_days(cast(cgc_fee.EXP_DATE as date), cast(? as date))");
		sqlBuffer.append(getEqualComparator()).append(" ? ");
		return sqlBuffer.toString();
	}

	/**
	 * Process resultset to return a list of results.
	 * 
	 * @param ResultSet rs
	 * @return List - list of results
	 * @throws Exception
	 */
	private List processResultSet(ResultSet rs) throws Exception {
		if (rs != null) {
			ArrayList aList = new ArrayList();
			OBFeeDue info = null;
			while (rs.next()) {
				info = new OBFeeDue();
				info.setLeID(rs.getString("LE_ID"));
				info.setLeName(rs.getString("CUST_NAME"));
				info.setSecurityId(rs.getString("SECURITY_ID"));
				info.setSecType(rs.getString("TYPE_NAME"));
				info.setSubType(rs.getString("SUBTYPE_NAME"));
				info.setFeeDueDate(new Date(rs.getDate("EXP_DATE").getTime()));
				info.setGuaranteeAmout(new Amount(rs.getDouble("GUARANTEE_FEE"), rs.getString("CCY")));
				aList.add(info);
			}
			return aList;
		}
		return null;
	}

}
