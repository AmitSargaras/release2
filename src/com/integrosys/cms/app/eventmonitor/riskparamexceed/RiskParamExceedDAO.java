/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/riskparamexceed/RiskParamExceedDAO.java,v 1.16 2006/08/24 06:16:59 hmbao Exp $
 */

package com.integrosys.cms.app.eventmonitor.riskparamexceed;

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

public class RiskParamExceedDAO extends AbstractMonitorDAO implements IMonitorDAO {
	private static String SQL_STATEMENT = "";

	static {
		StringBuffer strBuf = new StringBuffer();
		strBuf
				.append("SELECT CMS_SECURITY_SUB_TYPE.SECURITY_TYPE_ID, CMS_SECURITY_SUB_TYPE.SUBTYPE_NAME, ")
				.append("CMS_SECURITY_SUB_TYPE.SECURITY_TYPE_NAME, ")
				.append("CMS_SECURITY_SUB_TYPE.SECURITY_SUB_TYPE_ID, ")
				.append("CMS_SECURITY_PARAMETER.COUNTRY_ISO_CODE, ")
				.append("CMS_SECURITY_SUB_TYPE.MAX_PERCENT, ")
				.append("CMS_SECURITY_PARAMETER.THRESHOLD_PERCENT ")
				.append("FROM CMS_SECURITY_SUB_TYPE, CMS_SECURITY_PARAMETER ")
				.append(
						"WHERE CMS_SECURITY_SUB_TYPE.SECURITY_SUB_TYPE_ID = CMS_SECURITY_PARAMETER.SECURITY_SUB_TYPE_ID ")
				.append("AND CMS_SECURITY_PARAMETER.THRESHOLD_PERCENT > CMS_SECURITY_SUB_TYPE.MAX_PERCENT ");

		SQL_STATEMENT = strBuf.toString();
	}

	public IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws MonitorDaoResultRetrievalFailureException {
		ResultSet rs = null;
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			StringBuffer sqlBuffer = new StringBuffer(SQL_STATEMENT);

			if (ruleParam.hasCountryCode()) {
				sqlBuffer.append(" AND CMS_SECURITY_PARAMETER.COUNTRY_ISO_CODE = '");
				sqlBuffer.append(ruleParam.getCountryCode());
				sqlBuffer.append("'");
			}

			DefaultLogger.debug(this, sqlBuffer.toString());
			dbUtil.setSQL(sqlBuffer.toString());
			rs = dbUtil.executeQuery();
			List results = processResultSet(rs);
			RiskParamExceedDAOResult result = new RiskParamExceedDAOResult(results);
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
	 * The following method is to be overriden by implementing classes
	 */
	private List processResultSet(ResultSet rs) throws Exception {
		ArrayList results = new ArrayList();
		if (rs != null) {
			OBRiskParamExceedInfo nn = null;
			while (rs.next()) {
				nn = new OBRiskParamExceedInfo();
				nn.setOriginatingCountry(rs.getString("COUNTRY_ISO_CODE"));
				nn.setCountry(rs.getString("COUNTRY_ISO_CODE"));
				nn.setCountryThresHold(rs.getDouble("THRESHOLD_PERCENT"));
				nn.setMaxThreshold(rs.getDouble("MAX_PERCENT"));
				nn.setSubType(rs.getString("SUBTYPE_NAME"));
				nn.setType(rs.getString("SECURITY_TYPE_NAME"));
				nn.setDetails(MonRiskParamExceed.EVENT_RISK_PARAM_EXCEED + " , " + nn.getCountry() + " , "
						+ rs.getString("SECURITY_SUB_TYPE_ID"));
				results.add(nn);
			}
		}
		DefaultLogger.debug(this, "Num of records : " + results.size());
		return results;
	}
}
