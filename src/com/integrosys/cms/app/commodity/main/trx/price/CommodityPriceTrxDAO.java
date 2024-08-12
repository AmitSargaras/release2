/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/price/CommodityPriceTrxDAO.java,v 1.3 2006/10/12 03:14:00 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.price;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.bus.price.CommodityPriceDAO;
import com.integrosys.cms.app.commodity.main.bus.price.ICommodityPrice;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * DAO for commodity price transaction.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/10/12 03:14:00 $ Tag: $Name: $
 */
public class CommodityPriceTrxDAO implements ICommodityPriceTrxDAOConstants {
	private DBUtil dbUtil;

	private String getTrxSmt(String trxType) {
		if (!ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE_NON_RIC.equals(trxType)
				&& ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE.equals(trxType)) {
			;
			trxType = ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE;
		}
		StringBuffer buf = new StringBuffer();
		buf = new StringBuffer();
		buf.append("select ");
		buf.append(TRX_TRX_ID);
		buf.append(", ");
		buf.append(TRX_STATUS);
		buf.append(", ");
		buf.append(TRX_STAGE_REF_ID);
		buf.append(", ");
		buf.append(TRX_REF_ID);
		buf.append(" from ");
		buf.append(TRX_TABLE);
		buf.append(" where ");
		buf.append(TRX_TRX_TYPE);
		buf.append(" = '");
		buf.append(trxType);
		buf.append("' and ");
		buf.append(TRX_STATUS);
		buf.append(" <> '");
		buf.append(ICMSConstant.STATE_CLOSED);
		buf.append("'");

		return buf.toString();
	}

	/**
	 * Default Constructor
	 */
	public CommodityPriceTrxDAO() {
	}

	/**
	 * Get commodity price trx value given a list of prices.
	 * 
	 * @param prices commodity price objects
	 * @param isStaging indicator to determine it is from staging or actual
	 *        caller
	 * @return Commodity price trx value
	 * @throws SearchDAOException on error searching the commodity price trx
	 *         value
	 */
	public ICommodityPriceTrxValue getCommodityPriceTrxValue(ICommodityPrice[] prices, String trxType, boolean isStaging)
			throws SearchDAOException {
		if ((prices == null) || (prices.length == 0)) {
			return new OBCommodityPriceTrxValue();
		}

		String sql = constructCommodityPriceTrxValueSQL(prices, trxType, isStaging);

		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processCommodityPriceTrxValueResulSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting commodity price trx value ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Helper method to construct query for getting commodity price transaction
	 * value
	 * 
	 * @param prices commo commodity category code
	 * @param isStaging indicator to determine it is from staging or actual
	 *        caller
	 * @return sql query
	 */
	protected String constructCommodityPriceTrxValueSQL(ICommodityPrice[] prices, String trxType, boolean isStaging) {
		String sql = null;
		if (isStaging) {
			sql = new CommodityPriceDAO().constructStageCommodityPriceGroupIDSQL(prices);
		}
		else {
			sql = new CommodityPriceDAO().constructActualCommodityPriceGroupIDSQL(prices);
		}

		StringBuffer buf = new StringBuffer();
		buf.append(getTrxSmt(trxType));
		buf.append(" and ");
		if (isStaging) {
			buf.append(TRX_STAGE_REF_ID);
		}
		else {
			buf.append(TRX_REF_ID);
		}
		buf.append(" = ( ");
		buf.append(sql);
		buf.append(" )");
		return buf.toString();
	}

	/**
	 * Helper method to process the result set of commodity price transaction.
	 * 
	 * @param rs result set
	 * @return commodity price transaction value
	 * @throws SQLException on error processing the result set
	 */
	private ICommodityPriceTrxValue processCommodityPriceTrxValueResulSet(ResultSet rs) throws SQLException {
		OBCommodityPriceTrxValue trxVal = new OBCommodityPriceTrxValue();

		if (rs.next()) {
			trxVal.setTransactionID(String.valueOf(rs.getLong(TRX_TRX_ID)));
			trxVal.setStatus(rs.getString(TRX_STATUS));

			BigDecimal refID = rs.getBigDecimal(TRX_REF_ID);
			if (refID != null) {
				trxVal.setReferenceID(String.valueOf(refID.longValue()));
			}

			BigDecimal stageRefID = rs.getBigDecimal(TRX_STAGE_REF_ID);
			if (stageRefID != null) {
				trxVal.setStagingReferenceID(String.valueOf(stageRefID.longValue()));
			}
		}
		return trxVal;
	}

	/**
	 * Helper method to clean up database resources.
	 * 
	 * @param dbUtil database utility object
	 * @throws SearchDAOException error in cleaning up DB resources
	 */
	private void finalize(DBUtil dbUtil) throws SearchDAOException {
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in cleaning up DB resources.");
		}
	}
}
