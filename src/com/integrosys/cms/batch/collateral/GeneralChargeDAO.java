package com.integrosys.cms.batch.collateral;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.AbstractDBUtil;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.OBInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IDebtor;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IFixedAssetOthers;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGenChargeMapEntry;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IStock;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBDebtor;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBFixedAssetOthers;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGenChargeMapEntry;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBStock;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * DAO for Asset Based General Charge
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2006/07/17 07:24:29 $ Tag: $Name: $
 */
public class GeneralChargeDAO {

	// private String dummy = "";

	private static final String CHARGE_SQL_NO_STOCK =
	// gc with no stock, but with or without debtor
	"SELECT DISTINCT  asst.cms_collateral_id, sec.sci_security_currency, sci_security_dtl_id, "
			+ "                CAST (NULL AS SMALLINT) stock_insr_grace_period, "
			+ "                CAST (NULL AS CHAR(3)) stock_insr_grace_period_freq, "
			+ "                CAST (NULL AS DECIMAL(19,0)) cms_asst_gc_stock_id,  "
			+ "                asst.bank_share_pct, sec.margin crp_margin,  "
			+ "                CAST (NULL AS VARCHAR (20)) stock_id, "
			+ "                CAST (NULL AS CHAR (3)) stock_ccy,  "
			+ "                CAST (NULL AS DECIMAL(15,2)) stock_gross_value, "
			+ "                CAST (NULL AS VARCHAR (20)) stock_status, "
			+ "                CAST (NULL AS DECIMAL(15,2)) stock_creditor_amt, "
			+ "                CAST (NULL AS DECIMAL(15,2)) stock_net_value, "
			+ "                CAST (NULL AS DECIMAL (19, 0)) insurance_policy_id, "
			+ "                CAST (NULL AS VARCHAR (20)) insr_id,  "
			+ "                CAST (NULL AS VARCHAR (50)) insr_policy_no, "
			+ "                CURRENT_TIMESTAMP AS expiry_date,  "
			+ "                CAST (NULL AS CHAR (3)) insr_ccy, "
			+ "                CAST (NULL AS DECIMAL(15,2)) insured_amt,  "
			+ "                CAST (NULL AS VARCHAR (20)) insr_status, "
			+ "                CAST (NULL AS DECIMAL(19,0)) cms_asst_gc_insr_stk_map_id, "
			+ "                CAST (NULL AS VARCHAR (20)) map_status,  "
			+ "                dbt.cms_asst_gc_debtor_id, dbt.debt_amt_ccy, "
			+ "                asst.margin_debtor, dbt.mth_1_debt_amt, dbt.mth_2_debt_amt, dbt.mth_3_debt_amt, "
			+ "                dbt.mth_4_debt_amt, dbt.mth_5_debt_amt, dbt.mth_6_debt_amt, dbt.mth_7_debt_amt, "
			+ "                dbt.mth_8_debt_amt, dbt.mth_9_debt_amt, dbt.mth_10_debt_amt, dbt.mth_11_debt_amt, "
			+ "                dbt.mth_12_debt_amt, dbt.mth_more_than_12_debt_amt, dbt.applicable_period, "
			+ "                dbt.applicable_period_unit "
			+ "           FROM cms_asset asst LEFT OUTER JOIN cms_asst_gc_debtor dbt  "
			+ "           ON asst.cms_collateral_id = dbt.cms_collateral_id, " + "                cms_security sec "
			+ "          WHERE sec.security_sub_type_id = 'AB100' "
			+ "            AND sec.cms_collateral_id = asst.cms_collateral_id " + "            AND NOT EXISTS ( "
			+ "                    SELECT cms_collateral_id " + "                      FROM cms_asst_gc_stock stk "
			+ "                     WHERE stk.cms_collateral_id = asst.cms_collateral_id "
			+ "                           AND stk.status <> 'DELETED')";

	// for testing - start
	// " AND sec.sci_security_dtl_id = 23148317 " +
	// for testing - end
	// "UNION " +
	// gc with stock with no linkage

	private static final String CHARGE_SQL_NO_LINKAGE = "SELECT DISTINCT asst.cms_collateral_id, sec.sci_security_currency, sec.sci_security_dtl_id, "
			+ "                asst.stock_insr_grace_period, asst.stock_insr_grace_period_freq, "
			+ "                stk.cms_asst_gc_stock_id, asst.bank_share_pct, sec.margin crp_margin, "
			+ "                stk.stock_id stock_id, stk.valuation_ccy stock_ccy, "
			+ "                stk.gross_value stock_gross_value, stk.status stock_status, "
			+ "                stk.owe_creditor_amt stock_creditor_amt, stk.net_value stock_net_value, "
			+ "                CAST (NULL AS DECIMAL(19,0)) insurance_policy_id, "
			+ "                CAST (NULL AS VARCHAR (20)) insr_id,  "
			+ "                CAST (NULL AS VARCHAR (50)) insr_policy_no, "
			+ "                CURRENT_TIMESTAMP AS expiry_date,  "
			+ "                CAST (NULL AS CHAR (3)) insr_ccy, "
			+ "                CAST (NULL AS DECIMAL(15,2)) insured_amt,  "
			+ "                CAST (NULL AS VARCHAR (20)) insr_status, "
			+ "                CAST (NULL AS DECIMAL(19,0)) cms_asst_gc_insr_stk_map_id, "
			+ "                CAST (NULL AS VARCHAR (20)) map_status,  "
			+ "                dbt.cms_asst_gc_debtor_id, dbt.debt_amt_ccy, "
			+ "                asst.margin_debtor, dbt.mth_1_debt_amt, dbt.mth_2_debt_amt, dbt.mth_3_debt_amt, "
			+ "                dbt.mth_4_debt_amt, dbt.mth_5_debt_amt, dbt.mth_6_debt_amt, dbt.mth_7_debt_amt, "
			+ "                dbt.mth_8_debt_amt, dbt.mth_9_debt_amt, dbt.mth_10_debt_amt, dbt.mth_11_debt_amt, "
			+ "                dbt.mth_12_debt_amt, dbt.mth_more_than_12_debt_amt, dbt.applicable_period, "
			+ "                dbt.applicable_period_unit "
			+ "           FROM cms_asset asst LEFT OUTER JOIN cms_asst_gc_debtor dbt  "
			+ "               ON asst.cms_collateral_id = dbt.cms_collateral_id, "
			+ "                cms_security sec, cms_asst_gc_stock stk "
			+ "          WHERE sec.security_sub_type_id = 'AB100' "
			+ "            AND sec.cms_collateral_id = asst.cms_collateral_id "
			+ "            AND asst.cms_collateral_id = stk.cms_collateral_id "
			+ "            AND stk.status <> 'DELETED' "
			+ "            AND NOT EXISTS ( "
			+ "                  SELECT 1 "
			+ "                    FROM cms_asst_gc_insr_stk_map MAP "
			+ "                   WHERE MAP.cms_collateral_id = asst.cms_collateral_id "
			+ "                     AND MAP.stock_id = stk.stock_id "
			+ "                     AND MAP.status <> 'DELETED')";

	// for testing - start
	// " AND sec.sci_security_dtl_id = 23148317 " +
	// for testing - end
	// "UNION " +
	// gc with stock with linkage
	private static final String CHARGE_SQL_LINKAGE = "SELECT DISTINCT asst.cms_collateral_id, sec.sci_security_currency,"
			+ "  sec.sci_security_dtl_id, asst.stock_insr_grace_period,"
			+ "  asst.stock_insr_grace_period_freq, stk.cms_asst_gc_stock_id,"
			+ "  asst.bank_share_pct, sec.margin crp_margin,"
			+ "  stk.stock_id stock_id, stk.valuation_ccy stock_ccy,"
			+ "  stk.gross_value stock_gross_value, stk.status stock_status,"
			+ "  stk.owe_creditor_amt stock_creditor_amt,"
			+ "  stk.net_value stock_net_value, insr.insurance_policy_id,"
			+ "  insr.insr_id, insr.policy_no insr_policy_no, insr.expiry_date,"
			+ "  insr.currency_code insr_ccy, insr.insured_amt,"
			+ "  insr.status insr_status, MAP.cms_asst_gc_insr_stk_map_id,"
			+ "  MAP.status map_status, dbt.cms_asst_gc_debtor_id,"
			+ "  dbt.debt_amt_ccy, asst.margin_debtor, dbt.mth_1_debt_amt,"
			+ "  dbt.mth_2_debt_amt, dbt.mth_3_debt_amt, dbt.mth_4_debt_amt,"
			+ "  dbt.mth_5_debt_amt, dbt.mth_6_debt_amt, dbt.mth_7_debt_amt,"
			+ "  dbt.mth_8_debt_amt, dbt.mth_9_debt_amt, dbt.mth_10_debt_amt,"
			+ "  dbt.mth_11_debt_amt, dbt.mth_12_debt_amt,"
			+ "  dbt.mth_more_than_12_debt_amt, dbt.applicable_period,"
			+ "  dbt.applicable_period_unit "
			+ " FROM cms_asset asst LEFT OUTER JOIN cms_asst_gc_debtor dbt"
			+ " ON asst.cms_collateral_id = dbt.cms_collateral_id ,"
			+ "  cms_security sec,"
			+ "  cms_asst_gc_stock stk,"
			+ "  cms_insurance_policy insr,"
			+ "  cms_asst_gc_insr_stk_map MAP "
			+ " WHERE sec.security_sub_type_id = 'AB100'"
			+ "  AND sec.cms_collateral_id = asst.cms_collateral_id"
			+ "  AND asst.cms_collateral_id = stk.cms_collateral_id"
			+ "  AND asst.cms_collateral_id = insr.cms_collateral_id"
			+ "  AND asst.cms_collateral_id = MAP.cms_collateral_id"
			+ "  AND MAP.insr_id = insr.insr_id"
			+ "  AND MAP.stock_id = stk.stock_id"
			+ "  AND (insr.insurance_category IS NULL OR insr.insurance_category = 'STK')"
			+ "  AND stk.status <> 'DELETED'" + "  AND insr.status <> 'DELETED'" + "  AND MAP.status <> 'DELETED'";

	// for testing - start
	// " AND sec.sci_security_dtl_id = 23148317 " +
	// for testing - end
	private static final String CHARGE_SQL_ORDERBY = "ORDER BY cms_collateral_id, stock_id, insr_id";

	private static final String GET_GEN_CHRG_FAO_SQL = "SELECT DISTINCT asst.cms_collateral_id, sec.sci_security_currency,"
			+ "  sec.sci_security_dtl_id, fao.cms_asst_gc_fao_id,"
			+ "  fao.fxasst_othr_id, fao.valuation_ccy fao_ccy,"
			+ "  fao.gross_value fao_gross_value, fao.status fao_status,"
			+ "  fao.net_value fao_net_value "
			+ "FROM cms_security sec,"
			+ "  cms_asset asst,"
			+ "  cms_asst_gc_fxasst_othr fao "
			+ "WHERE sec.security_sub_type_id = 'AB100'"
			+ "  AND sec.cms_collateral_id = asst.cms_collateral_id"
			+ "  AND asst.cms_collateral_id = fao.cms_collateral_id"
			+ "  AND (fao.status IS NULL OR fao.status != 'DELETED') ";

	// " AND sec.sci_security_dtl_id = 23148317 " +
	private static final String GET_GEN_CHRG_FAO_SQL_ORDERBY = " ORDER BY asst.cms_collateral_id, fao.fxasst_othr_id";

	private static final String UPDATE_ASST = " UPDATE CMS_ASSET"
			+ "   SET prev_stk_insr_shortfall_amt = curr_stk_insr_shortfall_amt, "
			+ "       curr_stk_insr_shortfall_amt = ?," + "       drwg_pwr_less_insr_gross_amt = ?,"
			+ "       drwg_pwr_gross_amt = ? " + " WHERE cms_collateral_id = ?";

	private static final String UPDATE_STK_RECOVERABLE_AMT = "UPDATE cms_asst_gc_stock"
			+ "   SET recoverable_amt_ccy = ?," + "       recoverable_amt = ?" + " WHERE cms_asst_gc_stock_id = ?";

	private static final String UPDATE_DEBTOR = "UPDATE cms_asst_gc_debtor" + "   SET net_value = ?,"
			+ "       net_value_ccy = ?" + " WHERE cms_asst_gc_debtor_id = ?";

	/**
	 * Get list of valid general charge with only stock, stock insr and stock
	 * insr linkage populated.
	 * 
	 * @throws SearchDAOException on error getting the list of general charge
	 */
	public IGeneralCharge[] getGeneralCharge(String countryCode) throws SearchDAOException {
		AbstractDBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			// get asst, stock, stock insr mappings and debtor details where
			// applicable
			// existing 1.3
			dbUtil = getDBUtil();
			String sql = getGeneralChargeSQL(countryCode);

			// Debug("getGeneralCharge " + sql);

			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			Map asstMap = processResultSet(rs);

			// get fao and fao insr mappings where applicable
			// post 1.3
			dbUtil = getDBUtil();

			String tempSql = getGeneralChargeFaoSQL(countryCode);

			dbUtil.setSQL(tempSql);
			// Debug("getGeneralChargeFaoSQL  " + tempSql);

			rs = dbUtil.executeQuery();
			asstMap = populateFAO(asstMap, rs);

			// testing only
			// printGC(asstMap);

			return ((asstMap != null) && (asstMap.size() > 0)) ? (IGeneralCharge[]) asstMap.values().toArray(
					new IGeneralCharge[asstMap.size()]) : new IGeneralCharge[0];

		}
		catch (Exception e) {
			throw new SearchDAOException("Failure occurred while getting list of valid general charge.", e);
		}
		finally {
			close(rs, dbUtil);
		}
	}

	/**
	 * Update stock.recoverableAmt and asst.stockInsrShortFallAmt for each
	 * general charge in the list.
	 * 
	 * @param genChrgList - List of IGeneralCharge
	 * @throws SearchDAOException on error updating the values
	 */
	public void persist(IGeneralCharge[] genChrgList) throws Exception {
		if ((genChrgList == null) || (genChrgList.length == 0)) {
			return;
		}

		AbstractDBUtil asst_dbUtil = null;
		AbstractDBUtil debtor_dbUtil = null;
		AbstractDBUtil recoverable_dbUtil = null;

		try {
			asst_dbUtil = getDBUtil();
			asst_dbUtil.setSQL(UPDATE_ASST);

			// share a common dbutil to update debtor net value
			debtor_dbUtil = getDBUtil();
			debtor_dbUtil.setSQL(UPDATE_DEBTOR);

			// share a common dbutil to update stock recoverable amt
			recoverable_dbUtil = getDBUtil();
			recoverable_dbUtil.setSQL(UPDATE_STK_RECOVERABLE_AMT);

			for (int idx = 0; idx < genChrgList.length; idx++) {

				if ((genChrgList[idx] != null)
						&& (genChrgList[idx].getCollateralID() != ICMSConstant.LONG_INVALID_VALUE)) {

					// asset
					long collateral_id = genChrgList[idx].getCollateralID();

					Amount shortFallAmt = genChrgList[idx].getStockInsrShortfallAmount();
					BigDecimal shortFallAmtBD = (isValidAmount(shortFallAmt)) ? shortFallAmt.getAmountAsBigDecimal()
							: null;

					Amount dpGrossAmt = genChrgList[idx].getDrawingPowerGrossAmount();
					BigDecimal dpGrossAmtBD = (isValidAmount(dpGrossAmt)) ? dpGrossAmt.getAmountAsBigDecimal() : null;

					Amount dpLessInsrGrossAmt = genChrgList[idx].getDrawingPowerLessInsrGrossAmount();
					BigDecimal dpLessInsrGrossAmtBD = (isValidAmount(dpLessInsrGrossAmt)) ? dpLessInsrGrossAmt
							.getAmountAsBigDecimal() : null;

					DefaultLogger.debug("GeneralChargeDAO.persist", ">>>>>>>  persist collateralId : " + collateral_id);
					// update asst stock insr shortfall & drawing power amts
					asst_dbUtil.setBigDecimal(1, getDb2Decimal(shortFallAmtBD));
					asst_dbUtil.setBigDecimal(2, getDb2Decimal(dpGrossAmtBD));
					asst_dbUtil.setBigDecimal(3, getDb2Decimal(dpLessInsrGrossAmtBD));
					asst_dbUtil.setLong(4, collateral_id);
					asst_dbUtil.executeUpdate();

					// debtor
					if (genChrgList[idx].getDebtor() != null) {
						IDebtor debtor = genChrgList[idx].getDebtor();
						Amount debtorNetAmt = debtor.getNetValue();

						BigDecimal debtorNetAmtBD = null;
						String debtorNetAmtCcyCodeStr = null;
						if (isValidAmount(debtorNetAmt)) {
							debtorNetAmtBD = debtorNetAmt.getAmountAsBigDecimal();
							debtorNetAmtCcyCodeStr = debtorNetAmt.getCurrencyCode();
						}

						DefaultLogger.debug("GeneralChargeDAO.persist", ">> persist debtor ");
						// update debtor net value
						debtor_dbUtil.setBigDecimal(1, getDb2Decimal(debtorNetAmtBD));
						debtor_dbUtil.setString(2, debtorNetAmtCcyCodeStr);
						debtor_dbUtil.setLong(3, debtor.getAssetGCDebtorID());
						debtor_dbUtil.executeUpdate();

					}

					// update stock recoverable amt
					// iterate thru list of stock
					if (genChrgList[idx].getStocks() != null) {
						Iterator stockItr = genChrgList[idx].getStocks().values().iterator();
						while (stockItr.hasNext()) {
							IStock stock = (IStock) stockItr.next();

							long cms_stock_id = stock.getAssetGCStockID();
							if (cms_stock_id != ICMSConstant.LONG_INVALID_VALUE) {

								DefaultLogger.debug("GeneralChargeDAO.persist", ">> persist stock ID : "
										+ stock.getID());

								Amount recoverableAmt = stock.getRecoverableAmount();
								boolean isValid = isValidAmount(recoverableAmt);
								BigDecimal recoverableAmtBD = (isValid) ? recoverableAmt.getAmountAsBigDecimal() : null;
								String recoverableAmtCCY = (isValid) ? recoverableAmt.getCurrencyCode() : null;

								// update stock recoverable amt
								recoverable_dbUtil.setString(1, recoverableAmtCCY);
								recoverable_dbUtil.setBigDecimal(2, getDb2Decimal(recoverableAmtBD));
								recoverable_dbUtil.setLong(3, cms_stock_id);
								recoverable_dbUtil.executeUpdate();

							}
						}
					} // end of recoverable

				}
			}
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		finally {
			close(null, asst_dbUtil);
			close(null, debtor_dbUtil);
			close(null, recoverable_dbUtil);
		}
	}

	/**
	 * Helper method to check if this is a valid amount.
	 * 
	 * @param amt - Amount
	 * @return boolean
	 */
	private boolean isValidAmount(Amount amt) {

		return ((amt != null) && (amt.getAmountAsBigDecimal() != null) && (amt.getCurrencyCode() != null) && !amt
				.getCurrencyCode().equals(ICMSConstant.CURRENCYCODE_INVALID_VALUE));

	}

	/**
	 * Helper method to populate the general charge found in map with the fao
	 * details resultset.
	 * 
	 * @param asstMap - Map
	 * @param rs - ResultSet
	 * @throws SQLException on error processing the resultset
	 */
	private static Map populateFAO(Map asstMap, ResultSet rs) throws SQLException {
		if ((asstMap == null) || (asstMap.size() == 0) || (rs == null)) {
			return null;
		}

		ArrayList collateralContainingFAOList = new ArrayList();
		HashMap faoMap = null;
		String prev_fao_id = null;
		String fao_id = null;
		Long collateral_id = null;
		IGeneralCharge asst = null;

		DefaultLogger.debug("populateFAO", ">>>> start");

		// iterate thru the fao details result set
		while (rs.next()) {
			collateral_id = new Long(rs.getLong("cms_collateral_id"));

			DefaultLogger.debug("populateFAO", ">>>>>>>>>> cms_collateral_id : " + collateral_id
					+ "    sci_sec_dtl_id : " + rs.getLong("sci_security_dtl_id"));

			// get asset from map
			asst = (IGeneralCharge) asstMap.get(collateral_id);
			if (asst == null) {
				// skip current record if general charge not found
				continue;
			}

			// populate fao grace period

			// newly adding fao to general charge
			if (!collateralContainingFAOList.contains(collateral_id)) {
				faoMap = new HashMap();
				asst.setFixedAssetOthers(faoMap);
				collateralContainingFAOList.add(collateral_id);
			}

			// keep track of previous fao id
			prev_fao_id = fao_id;
			fao_id = rs.getString("fxasst_othr_id");

			// new fao
			if ((fao_id != null) && (fao_id.length() > 0) && !faoMap.containsKey(fao_id)) {
				faoMap.put(fao_id, createFixedAssetOthers(rs));
				// DefaultLogger.debug("populateFAO", ">>>>>>>>>>
				// cms_collateral_id : " + collateral_id + "---- new fao : " +
				// fao_id);
			}
		}

		// testing only
		// printGC(asstMap);

		return asstMap;

	}

	/**
	 * Helper method to process the resultset to get a map of general charge
	 * with stock, debtor and stock insr mapping details.
	 * 
	 * @param rs - ResultSet
	 * @throws SQLException on error processing the resultset
	 */
	private static Map processResultSet(ResultSet rs) throws SQLException {
		// get ResultSet with list of asst with stock and, optionally, insr tied
		// to stocks
		HashMap asstMap = new HashMap();
		HashMap stockMap = null;
		HashMap insrMap = null;
		HashMap stockInsrMap = null;
		ArrayList stockInsrMappingList = null;
		String prev_stock_id = null;
		String stock_id = null;

		DefaultLogger.debug("processResultSet", ">>>> start");
		while (rs.next()) {
			Long collateral_id = new Long(rs.getLong("cms_collateral_id"));
			// new asset
			if (!asstMap.containsKey(collateral_id)) {
				DefaultLogger.debug("processResultSet", ">>>>>>>>>> cms_collateral_id : " + collateral_id
						+ "    sci_sec_dtl_id : " + rs.getLong("sci_security_dtl_id"));

				// cater for cases where there are only one stock
				// set existing stockinsrmapping list for stock id
				// into map is was added to previous collateral
				if ((stock_id != null) && (stockInsrMappingList != null) && (stockInsrMappingList.size() > 0)) {
					// DefaultLogger.debug("processResultSet", ">>>>>>>>>> ----
					// ----- saving stock insr map for previous security : " +
					// stock_id);
					stockInsrMap.put(stock_id, stockInsrMappingList);
					stockInsrMappingList = new ArrayList();
				}

				prev_stock_id = null;
				stock_id = null;

				IGeneralCharge asst = createGeneralCharge(rs);
				asstMap.put(collateral_id, asst);

				stockMap = new HashMap();
				insrMap = new HashMap();
				stockInsrMap = new HashMap();
				stockInsrMappingList = new ArrayList();

				asst.setStocks(stockMap);
				asst.setInsurance(insrMap);
				asst.set_Stock_Insurance_Map(stockInsrMap);
			}

			// keep track of previous stock id
			prev_stock_id = stock_id;
			stock_id = rs.getString("STOCK_ID");

			// sec may not hv any stock
			// check for invalid stock_id
			if ((stock_id != null) && (stock_id.length() > 0) && !stockMap.containsKey(stock_id)) {

				stockMap.put(stock_id, createStock(rs));
				// DefaultLogger.debug("processResultSet", ">>>>>>>>>>
				// cms_collateral_id : " + collateral_id + "---- new stock : " +
				// stock_id);

				// if new stock detected, set existing stockinsrmapping list for
				// previous stock id
				if ((prev_stock_id != null) && (stockInsrMappingList != null) && (stockInsrMappingList.size() > 0)) {
					// DefaultLogger.debug("processResultSet", ">>>>>>>>>>
					// cms_collateral_id : " + collateral_id + "---- -----
					// saving stock insr map for : " + prev_stock_id);
					stockInsrMap.put(prev_stock_id, stockInsrMappingList);
					stockInsrMappingList = new ArrayList();
				}
			}

			String insr_id = rs.getString("INSR_ID");
			// stock may not be linked to any insr
			// check for invalid insr_id
			if ((insr_id != null) && (insr_id.length() > 0)) {

				// new insr
				if (!insrMap.containsKey(insr_id)) {
					insrMap.put(insr_id, createStockInsurance(rs));
				}

				String mapping_id = rs.getString("cms_asst_gc_insr_stk_map_id");
				// insr may not have linkage to stock
				if ((mapping_id != null) && (mapping_id.length() > 0)) {
					// create the mapping object and add to list
					IGenChargeMapEntry insrMapEntry = createStockInsrMapEntry(rs);

					// DefaultLogger.debug("processResultSet", ">>>>>>>>>>
					// cms_collateral_id : " + collateral_id + "---- new insr
					// map : " + insr_id);
					// DefaultLogger.debug("processResultSet", ">>>>>>>>>>
					// cms_collateral_id : " + collateral_id + "---- new insr
					// map is null ? : " + (insrMapEntry == null));

					stockInsrMappingList.add(insrMapEntry);
				}
			}
		}

		// to handle stock insr mapping in the last iteration
		if ((stockInsrMappingList != null) && (stockInsrMappingList.size() > 0)) {
			// DefaultLogger.debug("processResultSet", ">>>>>>>>>> ---- new
			// stock insr map for : " + stock_id);
			stockInsrMap.put(stock_id, stockInsrMappingList);
			stockInsrMappingList = null;
		}

		DefaultLogger.debug("processResultSet", ">>>> end    number of asst : " + asstMap.size());

		// testing only
		// printGC(asstMap);

		return asstMap;
	}

	/**
	 * Helper method to create a new general charge including debtor details
	 * 
	 * @param rs - ResultSet
	 */
	private static IGeneralCharge createGeneralCharge(ResultSet rs) throws SQLException {
		OBGeneralCharge newGenChrg = new OBGeneralCharge();
		newGenChrg.setCollateralID(rs.getLong("cms_collateral_id"));
		newGenChrg.setCurrencyCode(rs.getString("sci_security_currency"));
		newGenChrg.setMargin(rs.getDouble("crp_margin"));

		String bankSharePctStr = rs.getString("bank_share_pct");
		if ((bankSharePctStr != null) && (bankSharePctStr.length() > 0)) {
			newGenChrg.setBankShare(Double.parseDouble(bankSharePctStr));
		}

		newGenChrg.setStockInsrGracePeriod(rs.getInt("stock_insr_grace_period"));
		newGenChrg.setStockInsrGracePeriodFreq(rs.getString("stock_insr_grace_period_freq"));

		// debtor details
		BigDecimal marginBD = rs.getBigDecimal("margin_debtor");
		newGenChrg
				.setDebtorMargin(((marginBD == null) || (marginBD.doubleValue() < 0)) ? ICMSConstant.DOUBLE_INVALID_VALUE
						: marginBD.doubleValue());
		newGenChrg.setDebtor(createDebtor(rs));

		return newGenChrg;
	}

	/**
	 * Helper method to create a new debtor
	 * 
	 * @param rs - ResultSet
	 */
	private static IDebtor createDebtor(ResultSet rs) throws SQLException {
		OBDebtor newDebtor = null;
		long debtor_id = rs.getLong("cms_asst_gc_debtor_id");
		if (debtor_id > 0) {
			newDebtor = new OBDebtor();
			newDebtor.setAssetGCDebtorID(debtor_id);

			String debtAmtCcyCodeStr = rs.getString("debt_amt_ccy");
			CurrencyCode debtAmtCcyCode = new CurrencyCode(debtAmtCcyCodeStr);

			// applicable period
			newDebtor.setApplicablePeriodUnit(rs.getString("applicable_period_unit"));
			newDebtor.setApplicablePeriod(rs.getInt("applicable_period"));

			// month 1 debt
			BigDecimal debtAmtBD = rs.getBigDecimal("mth_1_debt_amt");
			Amount debtAmt = (debtAmtBD == null) ? null : new Amount(debtAmtBD, debtAmtCcyCode);
			newDebtor.setMonth1DebtAmount(debtAmt);

			// month 2 debt
			debtAmtBD = rs.getBigDecimal("mth_2_debt_amt");
			debtAmt = (debtAmtBD == null) ? null : new Amount(debtAmtBD, debtAmtCcyCode);
			newDebtor.setMonth2DebtAmount(debtAmt);

			// month 3 debt
			debtAmtBD = rs.getBigDecimal("mth_3_debt_amt");
			debtAmt = (debtAmtBD == null) ? null : new Amount(debtAmtBD, debtAmtCcyCode);
			newDebtor.setMonth3DebtAmount(debtAmt);

			// month 4 debt
			debtAmtBD = rs.getBigDecimal("mth_4_debt_amt");
			debtAmt = (debtAmtBD == null) ? null : new Amount(debtAmtBD, debtAmtCcyCode);
			newDebtor.setMonth4DebtAmount(debtAmt);

			// month 5 debt
			debtAmtBD = rs.getBigDecimal("mth_5_debt_amt");
			debtAmt = (debtAmtBD == null) ? null : new Amount(debtAmtBD, debtAmtCcyCode);
			newDebtor.setMonth5DebtAmount(debtAmt);

			// month 6 debt
			debtAmtBD = rs.getBigDecimal("mth_6_debt_amt");
			debtAmt = (debtAmtBD == null) ? null : new Amount(debtAmtBD, debtAmtCcyCode);
			newDebtor.setMonth6DebtAmount(debtAmt);

			// month 7 debt
			debtAmtBD = rs.getBigDecimal("mth_7_debt_amt");
			debtAmt = (debtAmtBD == null) ? null : new Amount(debtAmtBD, debtAmtCcyCode);
			newDebtor.setMonth7DebtAmount(debtAmt);

			// month 8 debt
			debtAmtBD = rs.getBigDecimal("mth_8_debt_amt");
			debtAmt = (debtAmtBD == null) ? null : new Amount(debtAmtBD, debtAmtCcyCode);
			newDebtor.setMonth8DebtAmount(debtAmt);

			// month 9 debt
			debtAmtBD = rs.getBigDecimal("mth_9_debt_amt");
			debtAmt = (debtAmtBD == null) ? null : new Amount(debtAmtBD, debtAmtCcyCode);
			newDebtor.setMonth9DebtAmount(debtAmt);

			// month 10 debt
			debtAmtBD = rs.getBigDecimal("mth_10_debt_amt");
			debtAmt = (debtAmtBD == null) ? null : new Amount(debtAmtBD, debtAmtCcyCode);
			newDebtor.setMonth10DebtAmount(debtAmt);

			// month 11 debt
			debtAmtBD = rs.getBigDecimal("mth_11_debt_amt");
			debtAmt = (debtAmtBD == null) ? null : new Amount(debtAmtBD, debtAmtCcyCode);
			newDebtor.setMonth11DebtAmount(debtAmt);

			// month 12 debt
			debtAmtBD = rs.getBigDecimal("mth_12_debt_amt");
			debtAmt = (debtAmtBD == null) ? null : new Amount(debtAmtBD, debtAmtCcyCode);
			newDebtor.setMonth12DebtAmount(debtAmt);

			// more than 12 month debt
			debtAmtBD = rs.getBigDecimal("mth_more_than_12_debt_amt");
			debtAmt = (debtAmtBD == null) ? null : new Amount(debtAmtBD, debtAmtCcyCode);
			newDebtor.setMonthMoreThan12DebtAmount(debtAmt);
		}
		return newDebtor;
	}

	/**
	 * Helper method to create a new fao
	 * 
	 * @param rs - ResultSet
	 */
	private static IFixedAssetOthers createFixedAssetOthers(ResultSet rs) throws SQLException {
		OBFixedAssetOthers newFAO = new OBFixedAssetOthers();
		newFAO.setAssetGCFixedAssetOthersID(rs.getLong("cms_asst_gc_fao_id"));
		newFAO.setFAOID(rs.getString("fxasst_othr_id"));
		String status = rs.getString("fao_status");
		if (status != null) {
			newFAO.setStatus(status);
		}

		String ccy = rs.getString("fao_ccy");
		CurrencyCode faoCCY = (ccy != null) ? new CurrencyCode(ccy) : null;
		BigDecimal grossValueBD = rs.getBigDecimal("fao_gross_value");
		BigDecimal netvalueBD = rs.getBigDecimal("fao_net_value");

		if (faoCCY != null) {
			newFAO.setGrossValue((grossValueBD != null) ? new Amount(grossValueBD, faoCCY) : null);
			newFAO.setNetValue((netvalueBD != null) ? new Amount(netvalueBD, faoCCY) : null);
		}

		return newFAO;
	}

	/**
	 * Helper method to create a new stock
	 * 
	 * @param rs - ResultSet
	 */
	private static IStock createStock(ResultSet rs) throws SQLException {
		OBStock newStock = new OBStock();
		newStock.setAssetGCStockID(rs.getLong("cms_asst_gc_stock_id"));
		newStock.setStockID(rs.getString("stock_id"));
		String status = rs.getString("stock_status");
		if (status != null) {
			newStock.setStatus(status);
		}

		String ccy = rs.getString("stock_ccy");
		CurrencyCode stockCCY = (ccy != null) ? new CurrencyCode(ccy) : null;
		BigDecimal grossValueBD = rs.getBigDecimal("stock_gross_value");
		BigDecimal netvalueBD = rs.getBigDecimal("stock_net_value");
		BigDecimal creditorAmtBD = rs.getBigDecimal("stock_creditor_amt");

		if (stockCCY != null) {
			newStock.setGrossValue((grossValueBD != null) ? new Amount(grossValueBD, stockCCY) : null);
			newStock.setNetValue((netvalueBD != null) ? new Amount(netvalueBD, stockCCY) : null);
			newStock.setCreditorAmt((creditorAmtBD != null) ? new Amount(creditorAmtBD, stockCCY) : null);
		}

		return newStock;
	}

	/**
	 * Helper method to create a new insurance.
	 * 
	 * @param rs - ResultSet
	 */
	private static IInsurancePolicy createStockInsurance(ResultSet rs) throws SQLException {
		IInsurancePolicy newInsr = createInsurance(rs);
		newInsr.setCategory(IInsurancePolicy.STOCK);
		return newInsr;
	}

	// /**
	// * Helper method to create a new insurance.
	// *
	// * @param rs -
	// * ResultSet
	// */
	// private static IInsurancePolicy createFixedAssetOthersInsurance(ResultSet
	// rs)
	// throws SQLException {
	// IInsurancePolicy newInsr = createInsurance(rs);
	// newInsr.setCategory(IInsurancePolicy.FAO);
	// return newInsr;
	// }

	/**
	 * Helper method to create a new insurance.
	 * 
	 * @param rs - ResultSet
	 */
	private static IInsurancePolicy createInsurance(ResultSet rs) throws SQLException {
		OBInsurancePolicy newInsrInfo = new OBInsurancePolicy();
		newInsrInfo.setInsurancePolicyID(rs.getLong("insurance_policy_id"));
		newInsrInfo.setRefID(rs.getString("insr_id"));
		newInsrInfo.setPolicyNo(rs.getString("insr_policy_no"));
		java.sql.Date expiryDate = rs.getDate("expiry_date");
		newInsrInfo.setExpiryDate((expiryDate == null) ? null : new java.util.Date(expiryDate.getTime()));

		String status = rs.getString("insr_status");
		if (status != null) {
			newInsrInfo.setStatus(status);
		}

		String ccy = rs.getString("insr_ccy");
		CurrencyCode insrCCY = (ccy != null) ? new CurrencyCode(ccy) : null;
		BigDecimal insuredAmtDB = rs.getBigDecimal("insured_amt");
		newInsrInfo.setInsuredAmount(((insrCCY != null) && (insuredAmtDB != null)) ? new Amount(insuredAmtDB, insrCCY)
				: null);

		return newInsrInfo;
	}

	/**
	 * Helper method to create a new stock insr map entry.
	 * 
	 * @param rs - ResultSet
	 */
	private static IGenChargeMapEntry createStockInsrMapEntry(ResultSet rs) throws SQLException {
		IGenChargeMapEntry newMapEntry = createInsrMapEntry(rs);
		newMapEntry.setMapEntryID(rs.getLong("cms_asst_gc_insr_stk_map_id"));
		newMapEntry.setEntryValueID(rs.getString("stock_id"));
		return newMapEntry;
	}

	// /**
	// * Helper method to create a new fao insr map entry.
	// *
	// * @param rs -
	// * ResultSet
	// */
	// private static IGenChargeMapEntry createFixedAssetOthersInsrMapEntry(
	// ResultSet rs) throws SQLException {
	// IGenChargeMapEntry newMapEntry = createInsrMapEntry(rs);
	// newMapEntry.setMapEntryID(rs.getLong("cms_asst_gc_insr_fao_map_id"));
	// newMapEntry.setEntryValueID(rs.getString("fxasst_othr_id"));
	// return newMapEntry;
	// }

	/**
	 * Helper method to create a new insr map entry.
	 * 
	 * @param rs - ResultSet
	 */
	private static IGenChargeMapEntry createInsrMapEntry(ResultSet rs) throws SQLException {
		OBGenChargeMapEntry newMapEntry = new OBGenChargeMapEntry();
		newMapEntry.setInsuranceID(rs.getString("insr_id"));
		String status = rs.getString("map_status");
		if (status != null) {
			newMapEntry.setStatus(status);
		}
		return newMapEntry;
	}

	/**
	 * Helper method to close a given resultset and dbutil.
	 * 
	 * @param rs - ResultSet
	 * @param dbUtil - RMDBUtil
	 */
	private static void close(ResultSet rs, AbstractDBUtil dbUtil) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				DefaultLogger.error("close", "Unable to close resultset.");
			}
		}
		if (dbUtil != null) {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				DefaultLogger.error("close", "Unable to close dbUtil.");
			}
		}
	}

	// /**
	// * Helper method to print details of the general charge contained in the
	// * map.
	// *
	// * @param asstMap -
	// * HashMap with collateral_id as key and general charge as value
	// */
	// private static void printGC(Map asstMap) {
	// Iterator asstItr = asstMap.values().iterator();
	// while (asstItr.hasNext()) {
	// printDetails((IGeneralCharge) asstItr.next());
	// }
	// }

	/**
	 * Helper method to print details of the general charge
	 * 
	 * @param gc - IGeneralCharge
	 */
	private static void printDetails(IGeneralCharge gc) {
		DefaultLogger.debug("printDetails", ">>>>>>>>>>>> >>>>>> collateral_id : " + gc.getCollateralID());
		DefaultLogger.debug("printDetails", ">>>>>>>>>>>> stk insr grace period : " + gc.getStockInsrGracePeriod()
				+ "        freq : " + gc.getStockInsrGracePeriodFreq());

		Map insrMap = gc.getInsurance();
		if (insrMap != null) {
			Iterator insrItr = insrMap.keySet().iterator();
			DefaultLogger.debug("printDetails", ">>>>>>>>>>>> INSR");
			while (insrItr.hasNext()) {
				String insr_id = (String) insrItr.next();
				DefaultLogger.debug("printDetails", "insr id : " + insr_id + "     category : "
						+ ((IInsurancePolicy) insrMap.get(insr_id)).getCategory() + "        expiry : "
						+ ((IInsurancePolicy) insrMap.get(insr_id)).getExpiryDate());
			}
		}

		Map stockMap = gc.getStocks();
		if (stockMap != null) {
			Iterator stockItr = stockMap.keySet().iterator();
			DefaultLogger.debug("printDetails", ">>>>>>>>>>>> STOCKS");
			while (stockItr.hasNext()) {
				DefaultLogger.debug("printDetails", "stock id : " + stockItr.next());
			}
		}

		Map stockInsrMap = gc.get_Stock_Insurance_Map();
		if (stockInsrMap != null) {
			Iterator stockInsrItr = stockInsrMap.keySet().iterator();
			DefaultLogger.debug("printDetails", ">>>>>>>>>>>> STK MAPPING");
			while (stockInsrItr.hasNext()) {
				String stockID = (String) stockInsrItr.next();
				Iterator mappingItr = ((List) stockInsrMap.get(stockID)).iterator();
				while (mappingItr.hasNext()) {
					DefaultLogger.debug("printDetails", "mapping - stock id : " + stockID + "     insr id : "
							+ ((IGenChargeMapEntry) mappingItr.next()).getInsuranceID());
				}
			}
		}

		Map faoMap = gc.getFixedAssetOthers();
		if (faoMap != null) {
			Iterator faoItr = faoMap.keySet().iterator();
			DefaultLogger.debug("printDetails", ">>>>>>>>>>>> FAO");
			while (faoItr.hasNext()) {
				DefaultLogger.debug("printDetails", "fao id : " + faoItr.next());
			}
		}

		Map faoInsrMap = gc.get_FixedAssetOthers_Insurance_Map();
		if (faoInsrMap != null) {
			Iterator faoInsrItr = faoInsrMap.keySet().iterator();
			DefaultLogger.debug("printDetails", ">>>>>>>>>>>> FAO MAPPING");
			while (faoInsrItr.hasNext()) {
				String faoID = (String) faoInsrItr.next();
				Iterator mappingItr = ((List) faoInsrMap.get(faoID)).iterator();
				while (mappingItr.hasNext()) {
					DefaultLogger.debug("printDetails", "mapping - fao id : " + faoID + "     insr id : "
							+ ((IGenChargeMapEntry) mappingItr.next()).getInsuranceID());
				}
			}
		}
		DefaultLogger.debug("printDetails", ">>>>>>>>>>>> >>>>>> end ");
	}

	private String getGeneralChargeSQL(String countryCode) {
		String ctyCon = "";
		if (countryCode != null) {
			StringBuffer ctyBuffer = new StringBuffer();
			ctyBuffer.append(" AND sec.SECURITY_LOCATION = '");
			ctyBuffer.append(countryCode);
			ctyBuffer.append("'");
			ctyCon = ctyBuffer.toString();
		}
		StringBuffer genChargeSQL = new StringBuffer();
		genChargeSQL.append(CHARGE_SQL_NO_STOCK);
		genChargeSQL.append(ctyCon);
		genChargeSQL.append(" UNION ");
		genChargeSQL.append(CHARGE_SQL_NO_LINKAGE);
		genChargeSQL.append(ctyCon);
		genChargeSQL.append(" UNION ");
		genChargeSQL.append(CHARGE_SQL_LINKAGE);
		genChargeSQL.append(ctyCon);
		genChargeSQL.append(CHARGE_SQL_ORDERBY);
		return genChargeSQL.toString();
	}

	private String getGeneralChargeFaoSQL(String countryCode) {
		String ctyCon = "";
		if (countryCode != null) {
			StringBuffer ctyBuffer = new StringBuffer();
			ctyBuffer.append(" AND sec.SECURITY_LOCATION = '");
			ctyBuffer.append(countryCode);
			ctyBuffer.append("'");
			ctyCon = ctyBuffer.toString();
		}
		StringBuffer genChargeSQL = new StringBuffer();
		genChargeSQL.append(GET_GEN_CHRG_FAO_SQL);
		genChargeSQL.append(ctyCon);
		genChargeSQL.append(GET_GEN_CHRG_FAO_SQL_ORDERBY);
		return genChargeSQL.toString();
	}

	private AbstractDBUtil getDBUtil() throws DBConnectionException, SQLException {
		return new DBUtil();
	}

	public BigDecimal getDb2Decimal(BigDecimal param) {
		if (param != null) {
			return param.setScale(2, BigDecimal.ROUND_UP);
		}
		return null;

	}

	private static void Debug(String msg) {
		// System.out.println("" + msg);
		// DefaultLogger.debug(this,msg) ;
	}
}