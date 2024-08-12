/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/WarehouseTrxDAO.java,v 1.4 2004/07/29 08:00:24 cchen Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.warehouse;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.commodity.main.bus.warehouse.WarehouseDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * @author $Author: cchen $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/07/29 08:00:24 $ Tag: $Name: $
 */
public class WarehouseTrxDAO {
	private DBUtil dbUtil;

	private static String SELECT_WAREHOUSE_TRANSACTION = null;

	static {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT ").append(ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID).append(",");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS).append(",");
		buf.append(ICMSTrxTableConstants.TRXTBL_STAGING_REFERENCE_ID).append(",");
		buf.append(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID);
		buf.append(" from ").append(ICMSTrxTableConstants.TRX_TBL_NAME);
		buf.append(" where ").append(ICMSTrxTableConstants.TRXTBL_TRANSACTION_TYPE);
		buf.append(" = '").append(ICMSConstant.INSTANCE_COMMODITY_MAIN_WAREHOUSE).append("' and (");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS).append(" != '").append(ICMSConstant.STATE_CLOSED).append(
				"' and ");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS).append(" != '").append(ICMSConstant.STATE_DELETED).append("')");

		SELECT_WAREHOUSE_TRANSACTION = buf.toString();
	}

	public IWarehouseTrxValue getWarehouseTrxValue(IWarehouse[] warehouses, boolean isStaging)
			throws SearchDAOException {
		if ((warehouses == null) || (warehouses.length == 0)) {
			return new OBWarehouseTrxValue();
		}
		return getWarehouseTrxValue(warehouses[0].getCountryCode(), isStaging);
	}

	public IWarehouseTrxValue getWarehouseTrxValue(String country, boolean isStaging) throws SearchDAOException {
		if ((country == null) || (country.length() == 0)) {
			return new OBWarehouseTrxValue();
		}

		String sql = constructSQL(country, isStaging);
		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processResulSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting commodity warehouse trx value ", e);
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

	private String constructSQL(String country, boolean isStaging) {

		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_WAREHOUSE_TRANSACTION);
		buf.append(" and ");
		if (isStaging) {
			buf.append(ICMSTrxTableConstants.TRXTBL_STAGING_REFERENCE_ID);
		}
		else {
			buf.append(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID);
		}
		buf.append(" = (");
		buf.append(new WarehouseDAO().constructGetGroupIDSQL(country, isStaging));
		buf.append(")");

		return buf.toString();
	}

	private IWarehouseTrxValue processResulSet(ResultSet rs) throws SQLException {

		OBWarehouseTrxValue trxVal = new OBWarehouseTrxValue();
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
