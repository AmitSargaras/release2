/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/uom/UnitofMeasureTrxDAO.java,v 1.5 2004/07/28 11:37:59 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.uom;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure;
import com.integrosys.cms.app.commodity.main.bus.uom.UnitofMeasureDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.5 $
 * @since $Date: 2004/07/28 11:37:59 $ Tag: $Name: $
 */
public class UnitofMeasureTrxDAO {
	private DBUtil dbUtil;

	private static String SELECT_UOM_TRANSACTION = null;

	static {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT ").append(ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID).append(",");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS).append(",");
		buf.append(ICMSTrxTableConstants.TRXTBL_STAGING_REFERENCE_ID).append(",");
		buf.append(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID);
		buf.append(" from ").append(ICMSTrxTableConstants.TRX_TBL_NAME);
		buf.append(" where ").append(ICMSTrxTableConstants.TRXTBL_TRANSACTION_TYPE);
		buf.append(" = '").append(ICMSConstant.INSTANCE_COMMODITY_MAIN_UOM).append("'").append(" and ");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS);
		buf.append(" != '").append(ICMSConstant.STATE_CLOSED).append("' and (");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS).append(" != '").append(ICMSConstant.STATE_CLOSED).append(
				"' and ");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS).append(" != '").append(ICMSConstant.STATE_DELETED).append("')");

		SELECT_UOM_TRANSACTION = buf.toString();
	}

	public IUnitofMeasureTrxValue getUnitofMeasureTrxValue(String cateogryCode, String productTypeCode,
			boolean isStaging) throws SearchDAOException {
		if ((cateogryCode == null) || (productTypeCode == null) || (cateogryCode.length() == 0)
				|| (productTypeCode.length() == 0)) {
			return new OBUnitofMeasureTrxValue();
		}

		String sql = constructSQL(cateogryCode, productTypeCode, isStaging);
		return getUnitofMeasureTrxValue(sql, isStaging);
	}

	public IUnitofMeasureTrxValue getUnitofMeasureTrxValue(IUnitofMeasure[] uoms, boolean isStaging)
			throws SearchDAOException {
		if ((uoms == null) || (uoms.length == 0)) {
			return new OBUnitofMeasureTrxValue();
		}

		String sql = constructSQL(uoms, isStaging);
		return getUnitofMeasureTrxValue(sql, isStaging);
	}

	private IUnitofMeasureTrxValue getUnitofMeasureTrxValue(String sql, boolean isStaging) throws SearchDAOException {
		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processResulSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting commodity uom trx value ", e);
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

	private String constructSQL(String categoryCode, String productTypeCode, boolean isStaging) {
		String maxIDSQL = new UnitofMeasureDAO().constructGetMaxGroupIDSQL(categoryCode, productTypeCode, isStaging);
		return constructSQL(maxIDSQL, isStaging);
	}

	private String constructSQL(IUnitofMeasure[] uoms, boolean isStaging) {
		String maxIDSQL = new UnitofMeasureDAO().constructGetMaxGroupIDSQL(uoms, isStaging);
		return constructSQL(maxIDSQL, isStaging);
	}

	private String constructSQL(String innerSQL, boolean isStaging) {

		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_UOM_TRANSACTION);
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

	private IUnitofMeasureTrxValue processResulSet(ResultSet rs) throws SQLException {

		OBUnitofMeasureTrxValue trxVal = new OBUnitofMeasureTrxValue();
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
