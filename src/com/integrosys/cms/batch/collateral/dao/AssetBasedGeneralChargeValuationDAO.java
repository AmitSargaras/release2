package com.integrosys.cms.batch.collateral.dao;

import java.sql.ResultSet;
import java.util.HashMap;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBDebtor;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBFixedAssetOthers;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBStock;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-6-8
 * @Tag 
 *      com.integrosys.cms.batch.collateral.AssetBasedGeneralChargeValuationDAO.java
 */
public class AssetBasedGeneralChargeValuationDAO extends AbstractCollateralValuationDAO {

	private String SELECT_ASSET_BASED_GENERAL_CHARGE_TRX = "SELECT VW_COLLATERAL_VALUATION.*,CMS_ASSET.BANK_SHARE_PCT AS A_BANK_SHARE_PCT,CMS_STAGE_ASSET.BANK_SHARE_PCT AS S_BANK_SHARE_PCT"
			+ " FROM VW_COLLATERAL_VALUATION,CMS_STAGE_ASSET,CMS_ASSET"
			+ " WHERE VW_COLLATERAL_VALUATION.S_CMS_COLLATERAL_ID = CMS_STAGE_ASSET.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.A_CMS_COLLATERAL_ID = CMS_ASSET.CMS_COLLATERAL_ID"
			+ " AND (VW_COLLATERAL_VALUATION.A_SECURITY_SUB_TYPE_ID = "
			+ "'"
			+ ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE
			+ "'"
			+ " OR VW_COLLATERAL_VALUATION.A_SECURITY_SUB_TYPE_ID IN ("
			+ " SELECT ENTRY_CODE from COMMON_CODE_CATEGORY, COMMON_CODE_CATEGORY_ENTRY	 "
			+ " WHERE COMMON_CODE_CATEGORY.CATEGORY_CODE =	 COMMON_CODE_CATEGORY_ENTRY.CATEGORY_CODE "
			+ " AND COMMON_CODE_CATEGORY_ENTRY.ENTRY_NAME = "
			+ "'"
			+ ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE
			+ "') "
			+ ")" + " AND VW_COLLATERAL_VALUATION.SECURITY_TYPE = 'AB'";

	private String SELECT_AB_GC_STOCK = "SELECT CMS_COLLATERAL_ID,STATUS, STOCK_ID, VALUATION_CCY, GROSS_VALUE, NET_VALUE FROM CMS_ASST_GC_STOCK WHERE CMS_COLLATERAL_ID IN ";

	private String SELECT_STAGE_AB_GC_STOCK = "SELECT CMS_COLLATERAL_ID,STATUS, STOCK_ID, VALUATION_CCY, GROSS_VALUE, NET_VALUE FROM CMS_STAGE_ASST_GC_STOCK WHERE CMS_COLLATERAL_ID IN ";

	private String SELECT_AB_FAO = "SELECT CMS_COLLATERAL_ID,STATUS, FXASST_OTHR_ID, VALUATION_CCY, GROSS_VALUE, NET_VALUE FROM CMS_ASST_GC_FXASST_OTHR WHERE CMS_COLLATERAL_ID IN ";

	private String SELECT_STAGE_AB_FAO = "SELECT CMS_COLLATERAL_ID,STATUS, FXASST_OTHR_ID, VALUATION_CCY, GROSS_VALUE, NET_VALUE FROM CMS_STAGE_ASST_GC_FXASST_OTHR WHERE CMS_COLLATERAL_ID IN ";

	private String SELECT_AB_DEBTOR = "SELECT CMS_COLLATERAL_ID,GROSS_VALUE, DEBT_AMT_CCY, NET_VALUE FROM CMS_ASST_GC_DEBTOR WHERE CMS_COLLATERAL_ID IN ";

	private String SELECT_STAGE_AB_DEBTOR = "SELECT CMS_COLLATERAL_ID,GROSS_VALUE, DEBT_AMT_CCY, NET_VALUE FROM CMS_STAGE_ASST_GC_DEBTOR WHERE CMS_COLLATERAL_ID IN ";

	private String TYPE_STOCK = "STOCK";

	private String TYPE_FAO = "FAO";

	private String TYPE_DEBTOR = "DEBTOR";

	protected String getSelectStatement() {
		return SELECT_ASSET_BASED_GENERAL_CHARGE_TRX;
	}

	protected boolean isValuationReqired(ICollateral collateral) {
		return true;
	}

	protected void synchronizeCollateralCMVFSV(ICollateralTrxValue collTrx) throws Exception {
		CollateralValuator valuator = new CollateralValuator();
		valuator.setCollateralCMVFSV(collTrx.getCollateral());
		valuator.setCollateralCMVFSV(collTrx.getStagingCollateral());
	}

	protected void fillCollateralDependentInfo(DBUtil dbUtil, ICollateralTrxValue[] collTrxValues) throws Exception {
		fillCollateralDependentInfo(dbUtil, collTrxValues, SELECT_AB_GC_STOCK, SELECT_STAGE_AB_GC_STOCK, TYPE_STOCK);
		fillCollateralDependentInfo(dbUtil, collTrxValues, SELECT_AB_FAO, SELECT_STAGE_AB_FAO, TYPE_FAO);
		fillCollateralDependentInfo(dbUtil, collTrxValues, SELECT_AB_DEBTOR, SELECT_STAGE_AB_DEBTOR, TYPE_DEBTOR);
	};

	protected void fillCollateralDetailInfo(ResultSet rs, ICollateral collateral, String prefix) throws Exception {
		IGeneralCharge gcColl = (IGeneralCharge) collateral;
		gcColl.setBankShare(util.getDoubleValue(rs.getBigDecimal(prefix + "BANK_SHARE_PCT")));
	}

	protected void fillCollateralDependentInfo(ICollateralTrxValue[] collTrxValues, int startIndex, int size,
			HashMap actualMap, HashMap stageMap, String type) throws Exception {
		int maxIdx = startIndex + size;
		for (int index = startIndex; index < maxIdx; index++) {
			fillCollateralDependentInfo((IGeneralCharge) collTrxValues[index].getCollateral(), actualMap, type);
			fillCollateralDependentInfo((IGeneralCharge) collTrxValues[index].getStagingCollateral(), stageMap, type);
		}
	}

	private void fillCollateralDependentInfo(IGeneralCharge coll, HashMap itemMap, String type) throws Exception {
		if (TYPE_STOCK.equals(type)) {
			HashMap stockMap = (HashMap) itemMap.get(new Long(coll.getCollateralID()));
			coll.setStocks(stockMap);
		}
		else if (TYPE_FAO.equals(type)) {
			HashMap faoMap = (HashMap) itemMap.get(new Long(coll.getCollateralID()));
			coll.setFixedAssetOthers(faoMap);
		}
		else if (TYPE_DEBTOR.equals(type)) {
			OBDebtor debtor = (OBDebtor) itemMap.get(new Long(coll.getCollateralID()));
			coll.setDebtor(debtor);
		}
		else {
			throw new Exception("Unknown Type.");
		}
	}

	protected HashMap parseCollateralDependentInfo(ResultSet rs, String type) throws Exception {
		if (TYPE_STOCK.equals(type)) {
			return parseGeneralChargeStocks(rs);
		}
		else if (TYPE_FAO.equals(type)) {
			return parseGeneralChargeFaos(rs);
		}
		else if (TYPE_DEBTOR.equals(type)) {
			return parseGeneralChargeDebors(rs);
		}
		else {
			throw new Exception("Unknown Type.");
		}
	}

	private HashMap parseGeneralChargeStocks(ResultSet rs) throws Exception {
		HashMap itemsMap = new HashMap();
		HashMap stockMap = null;
		while (rs.next()) {
			String status = rs.getString("STATUS");
			if ((status != null) && status.equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}
			long colID = rs.getLong("CMS_COLLATERAL_ID");
			OBStock stock = new OBStock();
			stock.setStockID(rs.getString("STOCK_ID"));
			stock.setStatus(status);
			stock.setValuationCurrency(rs.getString("VALUATION_CCY"));
			stock.setGrossValue(util.getAmount(rs.getBigDecimal("GROSS_VALUE"), stock.getValuationCurrency()));
			stock.setNetValue(util.getAmount(rs.getBigDecimal("NET_VALUE"), stock.getValuationCurrency()));
			stockMap = (HashMap) itemsMap.get(new Long(colID));
			if (stockMap == null) {
				stockMap = new HashMap();
			}
			stockMap.put(stock.getStockID(), stock);
			itemsMap.put(new Long(colID), stockMap);
		}
		return itemsMap;
	}

	private HashMap parseGeneralChargeFaos(ResultSet rs) throws Exception {
		HashMap itemsMap = new HashMap();
		HashMap faoMap = null;
		while (rs.next()) {
			String status = rs.getString("STATUS");
			if ((status != null) && status.equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}
			long colID = rs.getLong("CMS_COLLATERAL_ID");
			OBFixedAssetOthers fao = new OBFixedAssetOthers();
			fao.setFAOID(rs.getString("FXASST_OTHR_ID"));
			fao.setStatus(status);
			fao.setValuationCurrency(rs.getString("VALUATION_CCY"));
			fao.setGrossValue(util.getAmount(rs.getBigDecimal("GROSS_VALUE"), fao.getValuationCurrency()));
			fao.setNetValue(util.getAmount(rs.getBigDecimal("NET_VALUE"), fao.getValuationCurrency()));
			faoMap = (HashMap) itemsMap.get(new Long(colID));
			if (faoMap == null) {
				faoMap = new HashMap();
			}
			faoMap.put(fao.getFAOID(), fao);
			itemsMap.put(new Long(colID), faoMap);
		}
		return itemsMap;

	}

	private HashMap parseGeneralChargeDebors(ResultSet rs) throws Exception {
		HashMap itemsMap = new HashMap();
		while (rs.next()) {
			long colID = rs.getLong("CMS_COLLATERAL_ID");
			OBDebtor debtor = new OBDebtor();
			debtor.setGrossValue(util.getAmount(rs.getBigDecimal("GROSS_VALUE"), rs.getString("DEBT_AMT_CCY")));
			debtor.setNetValue(util.getAmount(rs.getBigDecimal("NET_VALUE"), rs.getString("DEBT_AMT_CCY")));
			itemsMap.put(new Long(colID), debtor);
		}
		return itemsMap;
	}
}
