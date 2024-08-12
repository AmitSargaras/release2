/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/titledocument/TitleDocumentTrxDAO.java,v 1.1 2004/07/22 19:55:38 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.titledocument;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.bus.titledocument.TitleDocumentDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/07/22 19:55:38 $ Tag: $Name: $
 */
public class TitleDocumentTrxDAO {
	private DBUtil dbUtil;

	private static String SELECT_TITLEDOCTYPE_TRANSACTION = null;

	static {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT ").append(ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID).append(",");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS).append(",");
		buf.append(ICMSTrxTableConstants.TRXTBL_STAGING_REFERENCE_ID).append(",");
		buf.append(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID).append(",");
		buf.append(ICMSTrxTableConstants.TRXTBL_VERSION);
		buf.append(" from ").append(ICMSTrxTableConstants.TRX_TBL_NAME);
		buf.append(" where ").append(ICMSTrxTableConstants.TRXTBL_TRANSACTION_TYPE);
		buf.append(" = '").append(ICMSConstant.INSTANCE_COMMODITY_MAIN_TITLEDOC).append("'");

		SELECT_TITLEDOCTYPE_TRANSACTION = buf.toString();
	}

	/**
	 * Gets the latest transaction value for title document type.
	 * 
	 * @param isStaging
	 * @return
	 * @throws SearchDAOException
	 */
	public ITitleDocumentTrxValue getTitleDocumentTrxValue(boolean isStaging) throws SearchDAOException {
		String sql = constructSQL(isStaging);
		return getTitleDocumentTrxValue(sql);
	}

	/*
	 * public ITitleDocumentTrxValue getTitleDocumentTrxValue(IUnitofMeasure[]
	 * uoms, boolean isStaging) throws SearchDAOException { if (uoms == null ||
	 * uoms.length == 0) return new OBTitleDocumentTrxValue();
	 * 
	 * String sql = constructSQL(uoms, isStaging); return
	 * getTitleDocumentTrxValue(sql); }
	 */

	/**
	 * Helper method to query db.
	 * 
	 * @param sql
	 * @return
	 * @throws SearchDAOException
	 */
	private ITitleDocumentTrxValue getTitleDocumentTrxValue(String sql) throws SearchDAOException {
		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processResulSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting commodity title doc type trx value ", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (Exception e) {
				throw new SearchDAOException("Error in cleaning up DB resources.", e);
			}
		}
	}

	/**
	 * Helper method to construct SQL to get latest transaction value for title
	 * document type.
	 * @param isStaging
	 * @return
	 */
	private String constructSQL(boolean isStaging) {
		String maxIDSQL = new TitleDocumentDAO().constructGetMaxGroupIDSQL(isStaging);
		return constructSQL(maxIDSQL, isStaging);
	}

	/*
	 * private String constructSQL(IUnitofMeasure[] uoms, boolean isStaging) {
	 * String maxIDSQL = new UnitofMeasureDAO().constructGetMaxGroupIDSQL(uoms,
	 * isStaging); return constructSQL(maxIDSQL, isStaging); }
	 */

	/**
	 * Helper method to construct SQL to get transaction value and append by the
	 * innerSQL.
	 * 
	 * @param innerSQL
	 * @param isStaging
	 * @return
	 */
	private String constructSQL(String innerSQL, boolean isStaging) {

		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_TITLEDOCTYPE_TRANSACTION);
		buf.append(" and ");
		if (isStaging) {
			buf.append(ICMSTrxTableConstants.TRXTBL_STAGING_REFERENCE_ID);
		}
		else {
			buf.append(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID);
		}
		buf.append(" = (");
		buf.append(innerSQL);
		buf.append(")");

		return buf.toString();
	}

	/**
	 * Helper method to procecss the resultset.
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private ITitleDocumentTrxValue processResulSet(ResultSet rs) throws SQLException {

		OBTitleDocumentTrxValue trxVal = new OBTitleDocumentTrxValue();
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

			BigDecimal version = rs.getBigDecimal(ICMSTrxTableConstants.TRXTBL_VERSION);
			if (version != null) {
				trxVal.setVersionTime(version.longValue());
			}
		}
		return trxVal;
	}

}
