/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/commodity/DealValuationDAO.java,v 1.30 2006/05/22 02:54:57 hmbao Exp $
 */
package com.integrosys.cms.batch.commodity;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateralDAOConstants;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.OBCollateralParameter;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBCommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBHedgingContractInfo;
import com.integrosys.cms.app.commodity.common.ConversionKey;
import com.integrosys.cms.app.commodity.common.PriceDifferential;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.QuantityConversionRate;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.common.UOMWrapperFactory;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealSearchCriteria;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDealDAOConstants;
import com.integrosys.cms.app.commodity.deal.bus.OBCommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.PriceType;
import com.integrosys.cms.app.commodity.deal.bus.cash.IDealCashDeposit;
import com.integrosys.cms.app.commodity.deal.bus.cash.OBDealCashDeposit;
import com.integrosys.cms.app.commodity.deal.bus.finance.IHedgePriceExtension;
import com.integrosys.cms.app.commodity.deal.bus.finance.IReceiptRelease;
import com.integrosys.cms.app.commodity.deal.bus.finance.OBHedgePriceExtension;
import com.integrosys.cms.app.commodity.deal.bus.finance.OBReceiptRelease;
import com.integrosys.cms.app.commodity.main.bus.price.ICommodityPrice;
import com.integrosys.cms.app.commodity.main.bus.price.ICommodityPriceDAOConstants;
import com.integrosys.cms.app.commodity.main.bus.price.OBCommodityPrice;
import com.integrosys.cms.app.commodity.main.bus.profile.OBProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * DAO for commodity deal.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.30 $
 * @since $Date: 2006/05/22 02:54:57 $ Tag: $Name: $
 */
public class DealValuationDAO implements ICommodityDealDAOConstants {

	private static int MAX_IN_CLAUSE = 1000;

	/**
	 * a hashmap of ICollateralParameter objects with key: country code +
	 * subtype code
	 */
	private static HashMap crpMap = new HashMap();

	private static int crpSize = 0;

	private static HashMap colMap = new HashMap();

	private static HashMap priceMap = new HashMap();

	private final static String SELECT_CRP = " select THRESHOLD_PERCENT, VALUATION_FREQUENCY,"
			+ "        VALUATION_FREQUENCY_UNIT, COUNTRY_ISO_CODE, " + "        SECURITY_SUB_TYPE_ID "
			+ " from CMS_SECURITY_PARAMETER";

	private final static String SELECT_DEAL = "SELECT CMS_CMDT_DEAL.DEAL_ID, CNTR_PRICE_TYPE, CLOSE_PRICE_CURRENCY, CLOSE_PRICE, CLOSE_UPDATE_DATE,"
			+ "       RIC, DEAL_CMV_CCY, DEAL_CMV, DEAL_FSV_CCY, DEAL_FSV, ORIG_FACE_AMT_CCY, ORIG_FACE_AMT,"
			+ "       ACT_QTY, CNTR_QTY_UOM_ID, ACT_PRICE, ACT_PRICE_CCY, ACT_MKT_PRICE_DATE, CASH_MARGIN_PCT,"
			+ "       ACT_COMMON_DIFF, ACT_COMMON_DIFF_SIGN, ACT_COMMON_DIFF_CCY, ACT_EOD_CUST_DIFF,"
			+ "       ACT_EOD_CUST_DIFF_SIGN, ACT_EOD_CUST_DIFF_CCY, HEDGE_PRICE, HEDGE_PRICE_CCY, HEDGE_QTY,"
			+ "       CNTR_QTY_UOM_ID, CNTR_RIC, IS_ATTAIN_ENFORCE, CNTR_MKT_UOM_ID, CNTR_MKT_UOM_CONVERT_QTY,"
			+ "       CASH_DEPOSIT_ID, DEPOSIT_TYPE, DEPOSIT_CCY, DEPOSIT_AMT, CMS_DEAL_CASH.STATUS,"
			+ "       SECURITY_LOCATION, SECURITY_SUB_TYPE_ID, MARGIN_PCT "
			+ "  FROM cms_cmdt_deal LEFT OUTER JOIN cms_hedge_cntr "
			+ "                           ON cms_cmdt_deal.HEDGE_CNTR_ID = cms_hedge_cntr.HEDGECONTRACT_ID "
			+ "                       LEFT OUTER JOIN cms_cmdt_price  "
			+ "                           ON cms_cmdt_deal.CNTR_PROFILE_ID = cms_cmdt_price.PROFILE_ID "
			+ "                       LEFT OUTER JOIN cms_deal_cash "
			+ "                           ON cms_cmdt_deal.DEAL_ID = cms_deal_cash.DEAL_ID,  "
			+ "       cms_security, cms_cmdt_profile  "
			+ " WHERE cms_cmdt_deal.CMS_COLLATERAL_ID = cms_security.CMS_COLLATERAL_ID "
			+ "   AND cms_cmdt_deal.CNTR_PROFILE_ID = cms_cmdt_profile.PROFILE_ID "
			+ "   AND cms_cmdt_deal.STATUS <> 'CLOSED' ";

	private final static String SELECT_STAGE_DEAL = "SELECT CMS_STAGE_CMDT_DEAL.DEAL_ID, CNTR_PRICE_TYPE, CLOSE_PRICE_CURRENCY, CLOSE_PRICE,"
			+ "       CLOSE_UPDATE_DATE, RIC, DEAL_CMV_CCY, DEAL_CMV, DEAL_FSV_CCY, DEAL_FSV, ORIG_FACE_AMT_CCY,"
			+ "       ORIG_FACE_AMT, ACT_QTY, CNTR_QTY_UOM_ID, ACT_PRICE, ACT_PRICE_CCY, ACT_MKT_PRICE_DATE,"
			+ "       CASH_MARGIN_PCT, ACT_COMMON_DIFF, ACT_COMMON_DIFF_SIGN, ACT_COMMON_DIFF_CCY, "
			+ "       ACT_EOD_CUST_DIFF, ACT_EOD_CUST_DIFF_SIGN, ACT_EOD_CUST_DIFF_CCY, HEDGE_PRICE, "
			+ "       HEDGE_PRICE_CCY, HEDGE_QTY, CNTR_QTY_UOM_ID, CNTR_RIC, IS_ATTAIN_ENFORCE, CNTR_MKT_UOM_ID, "
			+ "       CNTR_MKT_UOM_CONVERT_QTY, CASH_DEPOSIT_ID, DEPOSIT_TYPE, DEPOSIT_CCY, DEPOSIT_AMT, "
			+ "       CMS_STAGE_DEAL_CASH.STATUS, SECURITY_LOCATION, SECURITY_SUB_TYPE_ID, MARGIN_PCT "
			+ " FROM cms_stage_cmdt_deal  LEFT OUTER JOIN cms_hedge_cntr  "
			+ "                               ON cms_stage_cmdt_deal.hedge_cntr_id = cms_hedge_cntr.hedgecontract_id "
			+ "                           LEFT OUTER JOIN cms_cmdt_price "
			+ "                               ON cms_stage_cmdt_deal.cntr_profile_id = cms_cmdt_price.profile_id "
			+ "                           LEFT OUTER JOIN cms_cmdt_profile "
			+ "                               ON cms_stage_cmdt_deal.cntr_profile_id = cms_cmdt_profile.profile_id "
			+ "                           LEFT OUTER JOIN cms_stage_deal_cash "
			+ "                               ON cms_stage_cmdt_deal.deal_id = cms_stage_deal_cash.deal_id,"
			+ "       cms_security "
			+ " WHERE cms_stage_cmdt_deal.cms_collateral_id = cms_security.cms_collateral_id "
			+ "    AND cms_stage_cmdt_deal.cntr_profile_id = cms_cmdt_profile.profile_id "
			+ "    AND cms_stage_cmdt_deal.status <> 'CLOSED'";

	private final static String SELECT_HEDGE_EXT = "select DEAL_ID, END_DATE, HEDGE_EXT_ID, STATUS from CMS_HEDGE_EXT ";

	private final static String SELECT_STAGE_HEDGE_EXT = "select DEAL_ID, END_DATE, HEDGE_EXT_ID, STATUS from CMS_STAGE_HEDGE_EXT";

	private final static String SELECT_RELEASES = "select RELEASE_QTY, RELEASE_QTY_UOM_ID, DEAL_ID, STATUS from CMS_RCPT_RELEASE";

	private final static String SELECT_STAGE_RELEASES = "select RELEASE_QTY, RELEASE_QTY_UOM_ID, DEAL_ID, STATUS from CMS_STAGE_RCPT_RELEASE";

	private final static String UPDATE_DEAL = "update CMS_CMDT_DEAL "
			+ " set DEAL_CMV = ?, DEAL_CMV_CCY = ?, DEAL_FSV = ?, DEAL_FSV_CCY = ?, ACT_PRICE =?, ACT_PRICE_CCY = ?, "
			+ " ACT_MKT_PRICE_DATE = ?, CNTR_RIC = ?, VERSION_TIME = ?  " + " where DEAL_ID = ?";

	private final static String UPDATE_STAGE_DEAL = " update CMS_STAGE_CMDT_DEAL "
			+ " set DEAL_CMV = ?, DEAL_CMV_CCY = ?, DEAL_FSV = ?, DEAL_FSV_CCY = ?, ACT_PRICE =?, ACT_PRICE_CCY = ?, "
			+ " ACT_MKT_PRICE_DATE = ?, CNTR_RIC = ?, VERSION_TIME = ?  " + " where DEAL_ID = ?";

	private static String COLPARAM_THRESHOLD = ICollateralDAOConstants.COLPARAM_THRESHOLD;

	private static String COLPARAM_VAL_FREQ = ICollateralDAOConstants.COLPARAM_VAL_FREQ;

	private static String COLPARAM_VAL_FREQ_UNIT = ICollateralDAOConstants.COLPARAM_VAL_FREQ_UNIT;

	private static String COLPARAM_COUNTRY = ICollateralDAOConstants.COLPARAM_COUNTRY;

	private static String COLPARAM_SUBTYPE = ICollateralDAOConstants.COLPARAM_SUBTYPE;

	// private static String SECURITY_TABLE =
	// ICollateralDAOConstants.SECURITY_TABLE;
	private static String SECURITY_LOCATION = ICollateralDAOConstants.SECURITY_LOCATION;

	private static String SECURITY_SUBTYPE_CODE = ICollateralDAOConstants.SECURITY_SUBTYPE_CODE;

	// private static String COLLATERAL_ID =
	// ICollateralDAOConstants.COLLATERAL_ID;

	private final static String SELECT_CUSTOMER_COMMODITY_DEAL = "SELECT CMS_CMDT_DEAL.DEAL_ID, CNTR_PRICE_TYPE, CLOSE_PRICE_CURRENCY, CLOSE_PRICE, CLOSE_UPDATE_DATE,"
			+ "       RIC, DEAL_CMV_CCY, DEAL_CMV, DEAL_FSV_CCY, DEAL_FSV, ORIG_FACE_AMT_CCY, ORIG_FACE_AMT,"
			+ "       ACT_QTY, CNTR_QTY_UOM_ID, ACT_PRICE, ACT_PRICE_CCY, ACT_MKT_PRICE_DATE, CASH_MARGIN_PCT,"
			+ "       ACT_COMMON_DIFF, ACT_COMMON_DIFF_SIGN, ACT_COMMON_DIFF_CCY, ACT_EOD_CUST_DIFF,"
			+ "       ACT_EOD_CUST_DIFF_SIGN, ACT_EOD_CUST_DIFF_CCY, HEDGE_PRICE, HEDGE_PRICE_CCY, HEDGE_QTY,"
			+ "       CNTR_QTY_UOM_ID, CNTR_RIC, IS_ATTAIN_ENFORCE, CNTR_MKT_UOM_ID, CNTR_MKT_UOM_CONVERT_QTY,"
			+ "       CASH_DEPOSIT_ID, DEPOSIT_TYPE, DEPOSIT_CCY, DEPOSIT_AMT, CMS_DEAL_CASH.STATUS,"
			+ "       SECURITY_LOCATION, SECURITY_SUB_TYPE_ID, MARGIN_PCT"
			+ "  FROM cms_cmdt_deal LEFT OUTER JOIN cms_deal_cash"
			+ "                       ON cms_cmdt_deal.deal_id = cms_deal_cash.deal_id"
			+ "                    LEFT OUTER JOIN cms_hedge_cnt"
			+ "                       ON cms_cmdt_deal.hedge_cntr_id = cms_hedge_cntr.hedgecontract_id"
			+ "                    LEFT OUTER JOIN cms_cmdt_price"
			+ "                       ON cms_cmdt_deal.cntr_profile_id = cms_cmdt_price.profile_id,"
			+ "       cms_security,"
			+ "       cms_cmdt_profile,"
			+ "       sci_lsp_appr_lmts,"
			+ "       sci_lsp_lmt_profile,"
			+ "       cms_limit_security_map"
			+ " WHERE cms_cmdt_deal.cms_lsp_appr_lmts_id = sci_lsp_appr_lmts.cms_lsp_appr_lmts_id"
			+ "   AND sci_lsp_appr_lmts.cms_limit_profile_id = sci_lsp_lmt_profile.cms_lsp_lmt_profile_id"
			+ "   AND cms_cmdt_deal.cms_lsp_appr_lmts_id = cms_limit_security_map.cms_lsp_appr_lmts_id"
			+ "   AND cms_cmdt_deal.cms_collateral_id = cms_limit_security_map.cms_collateral_id"
			+ "   AND cms_cmdt_deal.cms_collateral_id = cms_security.cms_collateral_id"
			+ "   AND cms_cmdt_deal.cntr_profile_id = cms_cmdt_profile.profile_id"
			+ "   AND sci_lsp_lmt_profile.cms_customer_id = ?"
			+ "   AND sci_lsp_lmt_profile.cms_lsp_lmt_profile_id = ?" + "   AND cms_cmdt_deal.status <> 'CLOSED'";

	/**
	 * Get the CRP given the country code and set it to the crpMap with key
	 * country code + subtype code.
	 * 
	 * @throws SearchDAOException on error getting the crp
	 */
	public void setCRPHashMap(HashMap crpMap) throws SearchDAOException {
		String sql = SELECT_CRP;
		DBUtil dbUtil = null;
		try {
			dbUtil = getDBUtil();
			dbUtil.setSQL(sql);
			Debug("setCRPHashMap" + sql);
			ResultSet rs = dbUtil.executeQuery();

			while (rs.next()) {
				ICollateralParameter crp = new OBCollateralParameter();
				crp.setThresholdPercent(rs.getDouble(COLPARAM_THRESHOLD));
				crp.setValuationFrequency(rs.getInt(COLPARAM_VAL_FREQ));
				crp.setValuationFrequencyUnit(rs.getString(COLPARAM_VAL_FREQ_UNIT));
				crp.setCountryIsoCode(rs.getString(COLPARAM_COUNTRY));
				crp.setSecuritySubTypeId(rs.getString(COLPARAM_SUBTYPE));
				crpMap.put(crp.getCountryIsoCode() + crp.getSecuritySubTypeId(), crp);
			}
			rs.close();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting CRP!", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get actual commodity deals to be valuation. It excludes CLOSED deals.
	 * 
	 * @return ICommodityDeal[]
	 * @throws SearchDAOException on error getting the deals
	 */
	public ICommodityDeal[] getCommodityDeals(String countryCode) throws SearchDAOException {
		colMap = new HashMap();
		DBUtil dbUtil = null;
		try {
			dbUtil = getDBUtil();
			StringBuffer sqlBuffer = new StringBuffer(SELECT_DEAL);

			if (countryCode != null) {
				sqlBuffer.append(" AND cms_security.SECURITY_LOCATION='");
				sqlBuffer.append(countryCode);
				sqlBuffer.append("' ");
			}

			// DefaultLogger.debug(this, "getCommodityDeals SQL: " +
			// sqlBuffer.toString());
			Debug("getCommodityDeals " + sqlBuffer.toString());

			dbUtil.setSQL(sqlBuffer.toString());
			ResultSet rs = dbUtil.executeQuery();
			HashMap dealMap = new HashMap();
			processCommodityDeals(rs, dealMap);
			getHedgeExtension(dealMap);
			getReceiptReleases(dealMap);
			return (ICommodityDeal[]) dealMap.values().toArray(new ICommodityDeal[0]);
		}
		catch (Exception e) {
			throw new SearchDAOException("Caught Exception in getCommodityDeals", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get the commodity deals for a customer to be valuated. It excludes CLOSED
	 * deals.
	 * 
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return ICommodityDeal[]
	 * @throws SearchDAOException on error getting the deals
	 */
	public ICommodityDeal[] getCustomerCommodityDeals(CommodityDealSearchCriteria criteria) throws SearchDAOException {
		colMap = new HashMap();
		DBUtil dbUtil = null;
		try {
			dbUtil = getDBUtil();
			String sql = SELECT_CUSTOMER_COMMODITY_DEAL;
			Debug("getCustomerCommodityDeals" + sql);
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, criteria.getCustomerID());
			dbUtil.setLong(2, criteria.getLimitProfileID());
			ResultSet rs = dbUtil.executeQuery();
			HashMap dealMap = new HashMap();
			processCommodityDeals(rs, dealMap);
			getHedgeExtension(dealMap);
			getReceiptReleases(dealMap);
			return (ICommodityDeal[]) dealMap.values().toArray(new ICommodityDeal[0]);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in getCustomerCommodityDeals!", e);
			throw new SearchDAOException("Caught Exception in getCustomerCommodityDeals", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get all deal warehouse receipts released
	 * 
	 * @param dealMap a hashmap of ICommodityDeal with deal id as its key
	 * @throws SearchDAOException on error getting the receipts released
	 */
	private void getReceiptReleases(HashMap dealMap) throws SearchDAOException {
		String[] sqls = constructReceiptReleasesSQL(dealMap);
		DBUtil dbUtil = null;
		try {
			dbUtil = getDBUtil();
			for (int i = 0; i < sqls.length; i++) {
				Debug("getReceiptReleases" + sqls[i]);
				dbUtil.setSQL(sqls[i]);
				ResultSet rs = dbUtil.executeQuery();
				processReceiptReleases(rs, dealMap);
			}
			dbUtil.close();
			dbUtil = null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting receipt releases!", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get all hedge price extensions
	 * 
	 * @param dealMap a hashmap of ICommodityDeal with deal id as its key
	 * @throws SearchDAOException on error getting the extensions
	 */
	private void getHedgeExtension(HashMap dealMap) throws SearchDAOException {
		String[] sqls = constructHedgeExtensionSQL(dealMap);
		DBUtil dbUtil = null;
		try {
			dbUtil = getDBUtil();
			for (int i = 0; i < sqls.length; i++) {
				Debug("getHedgeExtension" + sqls[i]);
				dbUtil.setSQL(sqls[i]);
				ResultSet rs = dbUtil.executeQuery();
				processHedgeExtension(rs, dealMap);
			}
			dbUtil.close();
			dbUtil = null;
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in getting hedge extendsions!", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get all staging hedge price extensions.
	 * 
	 * @param dealMap a hashmap of ICommodityDeal with deal id as its key
	 * @throws SearchDAOException on error getting the extensions
	 */
	private void getStageHedgeExtension(HashMap dealMap) throws SearchDAOException {
		String[] sqls = constructStageHedgeExtensionSQL(dealMap);
		DBUtil dbUtil = null;
		try {
			dbUtil = getDBUtil();
			for (int i = 0; i < sqls.length; i++) {
				Debug("getStageHedgeExtension" + sqls[i]);
				dbUtil.setSQL(sqls[i]);
				ResultSet rs = dbUtil.executeQuery();
				processHedgeExtension(rs, dealMap);
			}
			dbUtil.close();
			dbUtil = null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting hedge extendsions!", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get all staging WR releases.
	 * 
	 * @param dealMap a hashmap of ICommodityDeal with deal id as its key
	 * @throws SearchDAOException on error getting the releases
	 */
	private void getStageReceiptReleases(HashMap dealMap) throws SearchDAOException {
		String[] sqls = constructStageReceiptReleasesSQL(dealMap);
		DBUtil dbUtil = null;
		try {
			dbUtil = getDBUtil();
			for (int i = 0; i < sqls.length; i++) {
				Debug("getStageReceiptReleases" + sqls[i]);
				dbUtil.setSQL(sqls[i]);
				ResultSet rs = dbUtil.executeQuery();
				processReceiptReleases(rs, dealMap);
			}
			dbUtil.close();
			dbUtil = null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting staging receipt releases!", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get staging commodity deals to be valuation. It excludes CLOSED deals.
	 * 
	 * @return ICommodityDeal[]
	 * @throws SearchDAOException on error getting the deals
	 */
	public ICommodityDeal[] getStageCommodityDeals(String[] stageIDs) throws SearchDAOException {
		if ((stageIDs == null) || (stageIDs.length == 0)) {
			return null;
		}

		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_STAGE_DEAL);
		buf.append(" and ");
		buf.append(DEAL_STAGE_TABLE);
		buf.append(".");
		buf.append(DEAL_ID);
		buf.append(" in ");

		String[] sqls = getSQLStatements(buf.toString(), stageIDs);
		HashMap dealMap = new HashMap();
		DBUtil dbUtil = null;

		try {
			dbUtil = getDBUtil();
			for (int i = 0; i < sqls.length; i++) {
				Debug("getStageCommodityDeals" + sqls[i]);
				dbUtil.setSQL(sqls[i]);
				ResultSet rs = dbUtil.executeQuery();
				processCommodityDeals(rs, dealMap);
			}
			dbUtil.close();
			dbUtil = null;
			getStageHedgeExtension(dealMap);
			getStageReceiptReleases(dealMap);
			return (ICommodityDeal[]) dealMap.values().toArray(new ICommodityDeal[0]);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting staging deals!", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get calculated FSV amount given the deal object.
	 * 
	 * @param deal of type ICommodityDeal
	 * @return Amount
	 * @throws SearchDAOException on error calculating the deal's FSV
	 */
	public Amount getCalculatedFSVAmt(ICommodityDeal deal) throws SearchDAOException {
		if (crpSize == 0) {
			setCRPHashMap(crpMap);
			crpSize = crpMap.size();
		}

		ICommodityCollateral col = (ICommodityCollateral) colMap.get(new Long(deal.getCommodityDealID()));
		ICollateralParameter crp = (ICollateralParameter) crpMap.get(col.getCollateralLocation()
				+ col.getCollateralSubType().getSubTypeCode());
		double crpMargin = 0d;
		if (crp != null) {
			crpMargin = crp.getThresholdPercent();
		}

		double hedgeMargin = 0d;
		if (col.getHedgingContractInfos() != null) {
			hedgeMargin = col.getHedgingContractInfos()[0].getMargin();
		}

		Amount fsv = deal.getCalculatedFSVAmt(crpMargin, hedgeMargin);

		return fsv;
	}

	/**
	 * Get commodity price given the deal.
	 * 
	 * @param deal of type ICommodityDeal
	 * @return ICommodityPrice
	 */
	public ICommodityPrice getCommodityPrice(ICommodityDeal deal) {
		return (ICommodityPrice) priceMap.get(new Long(deal.getCommodityDealID()));
	}

	/**
	 * Update cmv/fsv of commodity deal.
	 * 
	 * @param deal commodity deal an instruction to commit the transaction
	 * @throws SearchDAOException on error updating the deal
	 */
	public void updateCommodityDeal(ICommodityDeal deal) throws SearchDAOException {
		DBUtil dbUtil = null;
		String sql = UPDATE_DEAL;
		try {
			dbUtil = getDBUtil();
			dbUtil.setSQL(sql);

			Debug("updateCommodityDeal" + sql);

			setDBAmount(dbUtil, 1, 2, deal.getCMV());
			setDBAmount(dbUtil, 3, 4, deal.getFSV());
			setDBAmount(dbUtil, 5, 6, deal.getActualPrice());
			if (deal.getActualMarketPriceDate() != null) {
				dbUtil.setDate(7, new java.sql.Date(deal.getActualMarketPriceDate().getTime()));
			}
			else {
				dbUtil.setNull(7, Types.DATE);
			}
			dbUtil.setString(8, deal.getContractRIC());
			dbUtil.setLong(9, VersionGenerator.getVersionNumber());
			dbUtil.setLong(10, deal.getCommodityDealID());
			int result = dbUtil.executeUpdate();
			DefaultLogger.info(this, "Actual Row updated:" + result);
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in updating deals!", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Update cmv/fsv of staging commodity deal.
	 * 
	 * @param deal commodity deal an instruction to commit the transaction
	 * @throws SearchDAOException on error updating the deal
	 */
	public void updateStageCommodityDeal(ICommodityDeal deal) throws SearchDAOException {
		DBUtil dbUtil = null;
		String sql = UPDATE_STAGE_DEAL;

		try {
			dbUtil = getDBUtil();
			dbUtil.setSQL(sql);

			Debug("updateStageCommodityDeal" + sql);

			setDBAmount(dbUtil, 1, 2, deal.getCMV());
			setDBAmount(dbUtil, 3, 4, deal.getFSV());
			setDBAmount(dbUtil, 5, 6, deal.getActualPrice());
			if (deal.getActualMarketPriceDate() != null) {
				dbUtil.setDate(7, new java.sql.Date(deal.getActualMarketPriceDate().getTime()));
			}
			else {
				dbUtil.setNull(7, Types.DATE);
			}
			dbUtil.setString(8, deal.getContractRIC());
			dbUtil.setLong(9, VersionGenerator.getVersionNumber());
			dbUtil.setLong(10, deal.getCommodityDealID());
			int result = dbUtil.executeUpdate();
			DefaultLogger.info(this, "Stage Row updated:" + result);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in updating staging deals!", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Helper method to process the result set of commodity deals.
	 * 
	 * @param rs result set
	 * @param dealMap a hashmap of ICommodityDeal objects with deal id as its
	 *        key
	 * @throws SQLException on error processing the result set
	 */
	private void processCommodityDeals(ResultSet rs, HashMap dealMap) throws SQLException {
		if (dealMap == null) {
			dealMap = new HashMap();
		}

		HashMap cashMap = new HashMap();

		int countNumOfResult = 0;
		while (rs.next()) {
			Long dealID = new Long(rs.getLong(DEAL_ID));
			countNumOfResult++;
			DefaultLogger.debug(this, "**<< dealID retrieved from db: " + dealID.longValue());
			ICommodityDeal deal = (ICommodityDeal) dealMap.get(dealID);
			if (deal == null) {
				deal = new OBCommodityDeal();
				deal.setCommodityDealID(dealID.longValue());
				deal.setCMV(getAmount(rs.getBigDecimal(DEAL_CMV), rs.getString(DEAL_CMV_CCY)));
				deal.setFSV(getAmount(rs.getBigDecimal(DEAL_FSV), rs.getString(DEAL_FSV_CCY)));
				deal.setCashMarginPct(getDoubleValue(rs.getBigDecimal(DEAL_CASH_MARGIN)));
				deal.setOrigFaceValue(getAmount(rs.getBigDecimal(DEAL_FACE_VALUE), rs.getString(DEAL_FACE_VALUE_CCY)));
				deal
						.setActualPrice(getAmount(rs.getBigDecimal(DEAL_MARKET_PRICE), rs
								.getString(DEAL_MARKET_PRICE_CCY)));
				deal.setActualMarketPriceDate(rs.getDate(DEAL_MARKET_PRICE_DATE));
				deal.setActualCommonDifferential(getPriceDifferential(rs.getString(DEAL_ACT_DIFF_SIGN), rs
						.getBigDecimal(DEAL_ACT_DIFF), rs.getString(DEAL_ACT_DIFF_CCY)));
				deal.setActualEODCustomerDifferential(getPriceDifferential(rs.getString(DEAL_CUST_DIFF_SIGN), rs
						.getBigDecimal(DEAL_CUST_DIFF), rs.getString(DEAL_CUST_DIFF_CCY)));
				deal.setActualQuantity(getQuantity(rs.getBigDecimal(DEAL_ACT_QTY), rs.getString(DEAL_CNTR_QTY_UOM)));
				deal.setIsEnforceAttained(rs.getString(DEAL_ENFORCIBILITY));
				deal.setHedgePrice(getAmount(rs.getBigDecimal(DEAL_HEDGE_PRICE), rs.getString(DEAL_HEDGE_PRICE_CCY)));
				deal.setHedgeQuantity(getQuantity(rs.getBigDecimal(DEAL_HEDGE_QTY), rs.getString(DEAL_CNTR_QTY_UOM)));
				deal.setContractMarketUOMConversionRate(getQuantityConversionRate(rs.getString(DEAL_CNTR_MKT_UOM_ID),
						rs.getString(DEAL_CNTR_QTY_UOM), rs.getBigDecimal(DEAL_CNTR_MKT_UOM_RATE)));
				deal.setContractPriceType(getPriceType(rs.getString(DEAL_CNTR_PRICE_TYPE)));
				deal.setContractRIC(rs.getString(DEAL_CNTR_RIC));

				OBProfile profile = new OBProfile();
				profile.setReuterSymbol(rs.getString(ICommodityPriceDAOConstants.PROFILE_RIC));

				ICommodityPrice price = new OBCommodityPrice();
				price.setCommodityProfile(profile);

				if ((deal.getContractPriceType() != null)
						&& (deal.getContractPriceType().getName().equals(PriceType.FLOATING_FUTURES_PRICE.getName())
								|| deal.getContractPriceType().getName().equals(PriceType.EOD_PRICE.getName()) || deal
								.getContractPriceType().getName().equals(PriceType.NON_RIC_PRICE.getName()))) {
					price.setClosePrice(getAmount(rs.getBigDecimal(ICommodityPriceDAOConstants.PRICE_CLOSE_PRICE), rs
							.getString(ICommodityPriceDAOConstants.PRICE_CLOSE_PRICE_CCY_CODE)));
					price.setCloseUpdateDate(rs.getDate(ICommodityPriceDAOConstants.PRICE_CLOSE_UPDATE_DATE));
				}
				priceMap.put(dealID, price);

				OBCollateralSubType st = new OBCollateralSubType();
				st.setSubTypeCode(rs.getString(SECURITY_SUBTYPE_CODE));
				OBCommodityCollateral col = new OBCommodityCollateral();
				col.setCollateralSubType(st);
				col.setCollateralLocation(rs.getString(SECURITY_LOCATION));
				OBHedgingContractInfo hedge = new OBHedgingContractInfo();
				hedge.setMargin(rs.getInt(HEDGE_CNTR_MARGIN));
				col.setHedgingContractInfos(new OBHedgingContractInfo[] { hedge });
				colMap.put(dealID, col);
				dealMap.put(dealID, deal);
			}

			ArrayList cashList = (ArrayList) cashMap.get(dealID);
			if (cashList == null) {
				cashList = new ArrayList();
			}

			BigDecimal cashID = rs.getBigDecimal(CASH_ID);
			cashMap.put(dealID, cashList);
			if (cashID != null) {
				String status = rs.getString(CASH_STATUS);
				if ((status != null) && status.equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				OBDealCashDeposit cash = new OBDealCashDeposit();
				cash.setStatus(status);
				cash.setCashDepositID(cashID.longValue());
				cash.setDepositType(rs.getString(CASH_DEPOSIT_TYPE));
				cash.setAmount(getAmount(rs.getBigDecimal(CASH_DEPOSIT_AMT), rs.getString(CASH_DEPOSIT_CCY)));
				cashList.add(cash);
			}
		}
		DefaultLogger.debug(this, "- Total Number of Results retreived/processed from db: " + countNumOfResult);
		rs.close();

		Iterator i = dealMap.keySet().iterator();
		while (i.hasNext()) {
			Long dealID = (Long) i.next();
			ICommodityDeal deal = (ICommodityDeal) dealMap.get(dealID);
			ArrayList cashList = (ArrayList) cashMap.get(dealID);
			deal.setCashDeposit((IDealCashDeposit[]) cashList.toArray(new IDealCashDeposit[0]));
		}
	}

	/**
	 * Helper method to process the result set of hedging extensions.
	 * 
	 * @param rs result set
	 * @param dealMap a HashMap of ICommodityDeal objects
	 * @throws SQLException on error processing the result set
	 */
	private void processHedgeExtension(ResultSet rs, HashMap dealMap) throws SQLException {
		ArrayList extList = new ArrayList();
		HashMap extMap = new HashMap();

		while (rs.next()) {
			String status = rs.getString(HEDGE_EXT_STATUS);
			if ((status != null) && status.equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			Long dealID = new Long(rs.getLong(HEDGE_EXT_DEAL_ID));

			OBHedgePriceExtension ext = new OBHedgePriceExtension();
			ext.setEndDate(rs.getDate(HEDGE_EXT_END_DATE));
			ext.setExtensionID(rs.getLong(HEDGE_EXT_ID));
			ext.setStatus(status);
			extList = (ArrayList) extMap.get(dealID);
			if (extList == null) {
				extList = new ArrayList();
			}
			extList.add(ext);
			extMap.put(dealID, extList);
		}
		rs.close();

		Iterator i = dealMap.keySet().iterator();

		while (i.hasNext()) {
			Long dealID = (Long) i.next();
			ICommodityDeal deal = (ICommodityDeal) dealMap.get(dealID);
			extList = (ArrayList) extMap.get(dealID);
			IHedgePriceExtension[] ext = null;
			if (extList != null) {
				ext = (IHedgePriceExtension[]) extList.toArray(new IHedgePriceExtension[0]);
				deal.setHedgePriceExtension(ext);
			}
			dealMap.put(dealID, deal);
		}
	}

	/**
	 * Helper method to process the result set of warehouse receipts released
	 * 
	 * @param rs result set
	 * @param dealMap a HashMap of ICommodityDeal objects
	 * @throws SQLException on error processing the result set
	 */
	private void processReceiptReleases(ResultSet rs, HashMap dealMap) throws SQLException {
		ArrayList releaseList = new ArrayList();
		HashMap releaseMap = new HashMap();

		while (rs.next()) {
			String status = rs.getString(RELEASE_STATUS);
			if ((status != null) && status.equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			Long dealID = new Long(rs.getLong(RELEASE_DEAL_ID));

			OBReceiptRelease release = new OBReceiptRelease();
			release.setReleasedQty(getQuantity(rs.getBigDecimal(RELEASE_QTY), rs.getString(RELEASE_QTY_UOM)));
			release.setStatus(status);
			releaseList = (ArrayList) releaseMap.get(dealID);
			if (releaseList == null) {
				releaseList = new ArrayList();
			}
			releaseList.add(release);
			releaseMap.put(dealID, releaseList);
		}
		rs.close();

		Iterator i = dealMap.keySet().iterator();

		while (i.hasNext()) {
			Long dealID = (Long) i.next();
			ICommodityDeal deal = (ICommodityDeal) dealMap.get(dealID);
			releaseList = (ArrayList) releaseMap.get(dealID);
			IReceiptRelease[] releases = null;
			if (releaseList != null) {
				releases = (IReceiptRelease[]) releaseList.toArray(new IReceiptRelease[0]);
				deal.setReceiptReleases(releases);
			}
			dealMap.put(dealID, deal);
		}
	}

	/**
	 * Helper method to construct sql for hedge extensions.
	 * 
	 * @param dealMap a hashMap of ICommodityDeal with deal id as its key
	 * @return sqls
	 */
	private String[] constructHedgeExtensionSQL(HashMap dealMap) {
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_HEDGE_EXT);
		buf.append(" where ");
		buf.append(HEDGE_EXT_DEAL_ID);
		buf.append(" in ");

		String[] sqls = getSQLStatements(buf.toString(), (ICommodityDeal[]) dealMap.values().toArray(
				new ICommodityDeal[0]));
		return sqls;
	}

	/**
	 * Helper method to construct sql for receipt releases.
	 * 
	 * @param dealMap a hashMap of ICommodityDeal with deal id as its key
	 * @return sqls
	 */
	private String[] constructReceiptReleasesSQL(HashMap dealMap) {
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_RELEASES);
		buf.append(" where ");
		buf.append(RELEASE_DEAL_ID);
		buf.append(" in ");

		String[] sqls = getSQLStatements(buf.toString(), (ICommodityDeal[]) dealMap.values().toArray(
				new ICommodityDeal[0]));
		return sqls;
	}

	/**
	 * Helper method to construct sql for hedge extensions.
	 * 
	 * @param dealMap a hashMap of ICommodityDeal with deal id as its key
	 * @return sqls
	 */
	private String[] constructStageHedgeExtensionSQL(HashMap dealMap) {
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_STAGE_HEDGE_EXT);
		buf.append(" where ");
		buf.append(HEDGE_EXT_DEAL_ID);
		buf.append(" in ");

		String[] sqls = getSQLStatements(buf.toString(), (ICommodityDeal[]) dealMap.values().toArray(
				new ICommodityDeal[0]));
		return sqls;
	}

	/**
	 * Helper method to construct sql for staging WR releases.
	 * 
	 * @param dealMap a hashMap of ICommodityDeal with deal id as its key
	 * @return sqls
	 */
	private String[] constructStageReceiptReleasesSQL(HashMap dealMap) {
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_STAGE_RELEASES);
		buf.append(" where ");
		buf.append(RELEASE_DEAL_ID);
		buf.append(" in ");

		String[] sqls = getSQLStatements(buf.toString(), (ICommodityDeal[]) dealMap.values().toArray(
				new ICommodityDeal[0]));
		return sqls;
	}

	/**
	 * Helper method to get the double value of the BigDecimal.
	 * 
	 * @param value of type BigDecimal
	 * @return ICMSConstant.DOUBLE_INVALID_VALUE if the BigDecimal is null
	 */
	private double getDoubleValue(BigDecimal value) {
		if (value == null) {
			return ICMSConstant.DOUBLE_INVALID_VALUE;
		}
		else {
			return value.doubleValue();
		}
	}

	/**
	 * Helper method to get amount object given the amount value and its
	 * currency.
	 * 
	 * @param amt amount value
	 * @param ccy currency code
	 * @return Amount object
	 */
	private Amount getAmount(BigDecimal amt, String ccy) {
		if ((amt != null) && (ccy != null)) {
			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * Helper method to get quantity object given the qty and its measurement
	 * unit.
	 * 
	 * @param qty quantity value
	 * @param uom unit of measurement
	 * @return Quantity object
	 */
	private Quantity getQuantity(BigDecimal qty, String uom) {
		if ((qty != null) && (uom != null)) {
			UOMWrapper unit = UOMWrapperFactory.getInstance().valueOf(uom);
			return new Quantity(qty, unit);
		}
		return null;
	}

	/**
	 * Helper method to get price type object given its code.
	 * 
	 * @param priceType price type code
	 * @return PriceType object
	 */
	private PriceType getPriceType(String priceType) {
		if (priceType != null) {
			return PriceType.valueOf(priceType.trim());
		}
		return null;
	}

	/**
	 * Helper method to get price differential givent the sign and its price.
	 * 
	 * @param sign differential sign
	 * @param amt differential price
	 * @param ccy price currency
	 * @return PriceDifferential object
	 */
	private PriceDifferential getPriceDifferential(String sign, BigDecimal amt, String ccy) {
		if ((sign != null) && (amt != null)) {
			return new PriceDifferential(new Amount(amt, new CurrencyCode(ccy)), sign);
		}
		return null;
	}

	/**
	 * Helper method to get quantity conversion rate.
	 * 
	 * @param fromUOM uom to be converted
	 * @param toUOM uom to convert to
	 * @param uomRate new uom rate
	 * @return quantity conversion rate
	 */
	private QuantityConversionRate getQuantityConversionRate(String fromUOM, String toUOM, BigDecimal uomRate) {
		if ((fromUOM != null) && (toUOM != null) && (uomRate != null)) {
			ConversionKey key = new ConversionKey(fromUOM, toUOM);
			return new QuantityConversionRate(key, uomRate.doubleValue());
		}
		return null;
	}

	/**
	 * Helper method to set amount into statement.
	 * 
	 * @param dbUtil of type RMDBUtil
	 * @param amtIdx index of amount value
	 * @param ccyIdx index of currency code
	 * @param amt of type Amount
	 * @throws Exception on any errors encountereds
	 */
	private void setDBAmount(DBUtil dbUtil, int amtIdx, int ccyIdx, Amount amt) throws Exception {
		if (amt != null) {
			dbUtil.setBigDecimal(amtIdx, amt.getAmountAsBigDecimal());
			dbUtil.setString(ccyIdx, amt.getCurrencyCode());
		}
		else {
			dbUtil.setNull(amtIdx, Types.DOUBLE);
			dbUtil.setString(ccyIdx, null);
		}
	}

	/**
	 * Helper method to get a list of sql statements with IN clause appended.
	 * 
	 * @param mainSQL the main sql statement
	 * @param deals a list of deals
	 * @return properr constructed SQL with IN clauses
	 */
	public static String[] getSQLStatements(String mainSQL, ICommodityDeal[] deals) {
		double maxcount = (double) MAX_IN_CLAUSE;
		double len = deals.length;
		int size = (int) Math.ceil(len / maxcount);

		StringBuffer buf;
		String[] sql = new String[size];
		int j = 0;

		for (int i = 0; i < size; i++) {
			buf = new StringBuffer();
			for (; j < len; j++) {
				if (deals[j] != null) {
					// buf.append("'");
					buf.append(deals[j].getCommodityDealID());
					// buf.append("'");
				}
				if ((j != maxcount * (i + 1) - 1) && (j != len - 1)) {
					if (deals[j] != null) {
						buf.append(",");
					}
				}
				else {
					j = (int) maxcount * (i + 1);
					break;
				}
			}
			String str = buf.toString();
			if (str.length() == 0) {
				sql[i] = mainSQL + "('')";
			}
			else {
				int strlen = str.length();
				if (str.substring(strlen - 1).equals(",")) {
					str = str.substring(0, strlen - 1);
				}
				sql[i] = mainSQL + "(" + str + ")";
			}
		}
		return sql;
	}

	/**
	 * Helper method to get a list of sql statements with IN clause appended.
	 * 
	 * @param mainSQL the main sql statement
	 * @param strs a list of IN clause list
	 * @return properr constructed SQL with IN clauses
	 */
	public static String[] getSQLStatements(String mainSQL, String[] strs) {
		double maxcount = (double) MAX_IN_CLAUSE;
		double len = strs.length;
		int size = (int) Math.ceil(len / maxcount);

		StringBuffer buf;
		String[] sql = new String[size];
		int j = 0;
		for (int i = 0; i < size; i++) {
			buf = new StringBuffer();
			for (; j < len; j++) {
				if ((strs[j] != null) && (strs[j].length() != 0)) {
					// buf.append("'");
					buf.append(strs[j]);
					// buf.append("'");
				}
				if ((j != maxcount * (i + 1) - 1) && (j != len - 1)) {
					if ((strs[j] != null) && (strs[j].length() != 0)) {
						buf.append(",");
					}
				}
				else {
					j = (int) maxcount * (i + 1);
					break;
				}
			}
			String str = buf.toString();
			if (str.length() == 0) {
				sql[i] = mainSQL + "('')";
			}
			else {
				int strlen = str.length();
				if (str.substring(strlen - 1).equals(",")) {
					str = str.substring(0, strlen - 1);
				}
				sql[i] = mainSQL + "(" + str + ")";
			}
		}
		return sql;
	}

	/**
	 * Helper method to clean up database resources.
	 * 
	 * @param dbUtil database utility object error in cleaning up DB resources
	 */
	private void finalize(DBUtil dbUtil) {
		try {
			if (dbUtil != null) {
				dbUtil.close();
				dbUtil = null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DBUtil getDBUtil() throws DBConnectionException, SQLException {
		return new DBUtil();
	}

	private void Debug(String msg) {
		System.out.println("" + msg);
		// DefaultLogger.debug(this,msg) ;
	}
}
