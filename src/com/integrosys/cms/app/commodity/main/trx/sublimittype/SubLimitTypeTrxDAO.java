/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/sublimittype/SubLimitTypeTrxDAO.java,v 1.1 2005/10/06 05:08:56 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.sublimittype;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.SubLimitTypeDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-21
 * @Tag 
 *      com.integrosys.cms.app.commodity.main.bus.sublimittype.SubLimitTypeTrxDAO
 *      .java
 */
public class SubLimitTypeTrxDAO {
	private DBUtil dbUtil;

	private static String SELECT_SLT_TRANSACTION = null;

	static {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT ").append(ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID).append(",");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS).append(",");
		buf.append(ICMSTrxTableConstants.TRXTBL_STAGING_REFERENCE_ID).append(",");
		buf.append(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID);
		buf.append(" from ").append(ICMSTrxTableConstants.TRX_TBL_NAME);
		buf.append(" where ").append(ICMSTrxTableConstants.TRXTBL_TRANSACTION_TYPE);
		buf.append(" = '").append(ICMSConstant.INSTANCE_COMMODITY_MAIN_SUBLIMITTYPE).append("' and (");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS).append(" != '").append(ICMSConstant.STATE_CLOSED).append(
				"' and ");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS).append(" != '").append(ICMSConstant.STATE_DELETED).append("')");

		SELECT_SLT_TRANSACTION = buf.toString();
	}

	public ISubLimitTypeTrxValue getSubLimitTypeTrxValue(boolean isStaging) throws SearchDAOException {
		String sql = constructSQL(isStaging);
		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, "getSubLimitTypeTrxValue - SQL : " + sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processResulSet(rs);
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in getting commodity profile trx value ", e);
		}
		finally {
			if (dbUtil != null) {
				try {
					dbUtil.close();
				}
				catch (SQLException e) {
					throw new SearchDAOException("Error in cleaning up DB resources.", e);
				}
			}
		}
	}

	private String constructSQL(boolean isStaging) {
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_SLT_TRANSACTION);
		buf.append(" and ");
		if (isStaging) {
			buf.append(ICMSTrxTableConstants.TRXTBL_STAGING_REFERENCE_ID);
		}
		else {
			buf.append(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID);
		}
		buf.append(" = (");
		buf.append(new SubLimitTypeDAO().constructGetGroupIDSQL(isStaging));
		buf.append(")");
		return buf.toString();
	}

	private ISubLimitTypeTrxValue processResulSet(ResultSet rs) throws SQLException {
		OBSubLimitTypeTrxValue trxVal = new OBSubLimitTypeTrxValue();
		if (rs.next()) {
			trxVal.setTransactionID(String.valueOf(rs.getLong(ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID)));
			trxVal.setStatus(rs.getString(ICMSTrxTableConstants.TRXTBL_STATUS));

			BigDecimal refID = rs.getBigDecimal(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID);
			if (refID != null) {
				trxVal.setReferenceID(String.valueOf(refID.longValue()));
			}

			BigDecimal stageRefID = rs.getBigDecimal(ICMSTrxTableConstants.TRXTBL_STAGING_REFERENCE_ID);
			if (stageRefID != null) {
				trxVal.setStagingReferenceID(String.valueOf(stageRefID.longValue()));
			}
		}
		return trxVal;
	}
}
