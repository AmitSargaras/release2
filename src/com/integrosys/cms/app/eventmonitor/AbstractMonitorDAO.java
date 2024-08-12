/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/AbstractMonitorDAO.java,v 1.3 2006/08/11 02:59:09 hmbao Exp $
 */

package com.integrosys.cms.app.eventmonitor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * Abstract class to provide util methods
 */
public abstract class AbstractMonitorDAO extends JdbcDaoSupport {

	/* logger available to subclasses */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String MONITOR_DAO_COMPARATOR_KEY = "cms.eventmonitor.equalcomparator";

	/**
	 * This method exists just to facilitate testing As a '=' comparison is
	 * stringent and result is little or no test results, in devt mode it is set
	 * to >= so that more results are returned
	 */
	protected static String getEqualComparator() {
		return PropertyManager.getValue(MONITOR_DAO_COMPARATOR_KEY, "=");
	}

	public void close(ResultSet rs, DBUtil dbUtil) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (SQLException e) {
			DefaultLogger.info(this, "Unable to close dbUtil", e);
		}
	}

	protected String getMBSecCustConnStmt() {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" AND CMS_LIMIT_SECURITY_MAP.CUSTOMER_CATEGORY = 'MB'");
		sqlBuffer.append(" AND CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID = SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID");
		sqlBuffer.append(" AND SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID");
		sqlBuffer.append(" AND SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID");

		return sqlBuffer.toString();
	}

	protected String getCBSecCustConnStmt() {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" AND CMS_LIMIT_SECURITY_MAP.CUSTOMER_CATEGORY = 'CB'");
		sqlBuffer
				.append(" AND CMS_LIMIT_SECURITY_MAP.CMS_LSP_CO_BORROW_LMT_ID = SCI_LSP_CO_BORROW_LMT.CMS_LSP_CO_BORROW_LMT_ID");
		sqlBuffer.append(" AND SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = SCI_LSP_CO_BORROW_LMT.CMS_LIMIT_ID");
		sqlBuffer.append(" AND SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID");
		sqlBuffer.append(" AND SCI_LSP_CO_BORROW_LMT.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID");
		return sqlBuffer.toString();
	}
}
