package com.integrosys.cms.batch.collateral.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.OBLimitCharge;
import com.integrosys.cms.app.collateral.bus.OBValuation;
import com.integrosys.cms.app.collateral.bus.type.insurance.ICDSItem;
import com.integrosys.cms.app.collateral.bus.type.insurance.IInsuranceCollateral;
import com.integrosys.cms.app.collateral.bus.type.insurance.OBCDSItem;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditinsurance.ICreditInsurance;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.keymaninsurance.IKeymanInsurance;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-6-8
 * @Tag com.integrosys.cms.batch.collateral.InsuranceValuationDAO.java
 */
public class InsuranceValuationDAO extends AbstractCollateralValuationDAO {

	private static String SELECT_INSURANCE_TRX = "SELECT VW_COLLATERAL_VALUATION.*,"
			+ " CMS_INSURANCE.INSURED_AMOUNT AS A_INSURED_AMOUNT,"
			+ " CMS_INSURANCE.INSURED_AMT_CURR AS A_INSURED_AMT_CURR,"
			+ " CMS_CHARGE_DETAIL.CHARGE_AMOUNT AS A_CHARGE_AMOUNT,"
			+ " CMS_CHARGE_DETAIL.CHARGE_CURRENCY_CODE AS A_CHARGE_CURRENCY_CODE,"
			+ " CMS_STAGE_INSURANCE.INSURED_AMOUNT AS S_INSURED_AMOUNT,"
			+ " CMS_STAGE_INSURANCE.INSURED_AMT_CURR AS S_INSURED_AMT_CURR,"
			+ " CMS_STAGE_CHARGE_DETAIL.CHARGE_AMOUNT AS S_CHARGE_AMOUNT,"
			+ " CMS_STAGE_CHARGE_DETAIL.CHARGE_CURRENCY_CODE AS S_CHARGE_CURRENCY_CODE"
			+ " FROM VW_COLLATERAL_VALUATION,CMS_INSURANCE,CMS_STAGE_INSURANCE,CMS_CHARGE_DETAIL,CMS_STAGE_CHARGE_DETAIL"
			+ " WHERE VW_COLLATERAL_VALUATION.A_CMS_COLLATERAL_ID = CMS_INSURANCE.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.A_CMS_COLLATERAL_ID = CMS_CHARGE_DETAIL.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.S_CMS_COLLATERAL_ID = CMS_STAGE_INSURANCE.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.S_CMS_COLLATERAL_ID = CMS_STAGE_CHARGE_DETAIL.CMS_COLLATERAL_ID"

			+ " AND ((VW_COLLATERAL_VALUATION.A_SECURITY_SUB_TYPE_ID <>" + "'" + ICMSConstant.COLTYPE_INS_CR_DERIVATIVE
			+ "'" + ")" + " OR ( VW_COLLATERAL_VALUATION.A_SECURITY_SUB_TYPE_ID NOT IN ("
			+ " SELECT ENTRY_CODE from COMMON_CODE_CATEGORY, COMMON_CODE_CATEGORY_ENTRY	 "
			+ " WHERE COMMON_CODE_CATEGORY.CATEGORY_CODE =	 COMMON_CODE_CATEGORY_ENTRY.CATEGORY_CODE "
			+ " AND COMMON_CODE_CATEGORY_ENTRY.ENTRY_NAME = " + "'" + ICMSConstant.COLTYPE_INS_CR_DERIVATIVE + "')))";

	private String SELECT_CDS_ITEM = "SELECT CMS_COLLATERAL_ID, MARGIN, VALUATION_CURRENCY, VALUATION_CMV, VALUTION_FSV, STATUS "
			+ " FROM CMS_INSURANCE_CDS " + " WHERE CMS_COLLATERAL_ID IN ";

	private String SELECT_STAGE_CDS_ITEM = "SELECT CMS_COLLATERAL_ID, MARGIN, VALUATION_CURRENCY, VALUATION_CMV, VALUTION_FSV, STATUS "
			+ " FROM CMS_STAGE_INSURANCE_CDS " + " WHERE CMS_COLLATERAL_ID IN ";

	protected String getSelectStatement() {
		return SELECT_INSURANCE_TRX;
	}

	protected void fillCollateralDetailInfo(ResultSet rs, ICollateral collateral, String prefix) throws Exception {
		IInsuranceCollateral coll = (IInsuranceCollateral) collateral;
		if (coll instanceof ICreditInsurance) {
			String ccycode = rs.getString(prefix + INSURANCE_AMT_CCY);
			((ICreditInsurance) coll).setInsuredCcyCode(ccycode);
			((ICreditInsurance) coll).setInsuredAmount(util
					.getAmount(rs.getBigDecimal(prefix + INSURANCE_AMT), ccycode));
		}
		else if (coll instanceof IKeymanInsurance) {
			String ccycode = rs.getString(prefix + INSURANCE_AMT_CCY);
			((IKeymanInsurance) coll).setInsuredCcyCode(ccycode);
			((IKeymanInsurance) coll).setInsuredAmount(util
					.getAmount(rs.getBigDecimal(prefix + INSURANCE_AMT), ccycode));
		}
		long colID = coll.getCollateralID();
		OBLimitCharge charge = new OBLimitCharge();
		charge.setChargeCcyCode(rs.getString(prefix + CHARGE_CURRENCY_CODE));
		charge.setChargeAmount(util.getAmount(rs.getBigDecimal(prefix + CHARGE_AMOUNT), charge.getChargeCcyCode()));
		charge.setCollateralID(colID);
		coll.setLimitCharges(new OBLimitCharge[] { charge });
	}

	protected boolean isValuationReqired(ICollateral collateral) {
		return true;
	}

	protected void fillCollateralDependentInfo(DBUtil dbUtil, ICollateralTrxValue[] collTrxValues) throws Exception {
		fillCollateralDependentInfo(dbUtil, collTrxValues, SELECT_CDS_ITEM, SELECT_STAGE_CDS_ITEM, null);
	};

	protected void synchronizeCollateralCMVFSV(ICollateralTrxValue collTrx) throws Exception {
		CollateralValuator valuator = new CollateralValuator();
		valuator.setCollateralCMVFSV(collTrx.getCollateral());
		valuator.setCollateralCMVFSV(collTrx.getStagingCollateral());
	}

	protected void fillCollateralDependentInfo(ICollateralTrxValue[] collTrxValues, int startIndex, int size,
			HashMap actualMap, HashMap stageMap, String type) throws Exception {
		int maxIdx = startIndex + size;
		for (int index = startIndex; index < maxIdx; index++) {
			fillCollateralDependentInfo((IInsuranceCollateral) collTrxValues[index].getCollateral(), actualMap);
			fillCollateralDependentInfo((IInsuranceCollateral) collTrxValues[index].getStagingCollateral(), stageMap);
		}
	}

	private void fillCollateralDependentInfo(IInsuranceCollateral collateral, HashMap itemsMap) {
		ArrayList itemsArray = (ArrayList) itemsMap.get(new Long(collateral.getCollateralID()));
		ICDSItem[] items = null;
		if (itemsArray != null) {
			items = (ICDSItem[]) itemsArray.toArray(new ICDSItem[0]);
		}
		collateral.setCdsItems(items);
	}

	protected HashMap parseCollateralDependentInfo(ResultSet rs, String infoType) throws Exception {
		ArrayList items = null;
		HashMap itemsMap = new HashMap();
		while (rs.next()) {
			String status = rs.getString("STATUS");
			if ((status != null) && status.equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}
			OBCDSItem item = new OBCDSItem();
			long colID = rs.getLong("CMS_COLLATERAL_ID");
			item.setStatus(status);
			OBValuation val = new OBValuation();
			val.setCurrencyCode(rs.getString("VALUATION_CURRENCY"));
			val.setCMV(util.getAmount(rs.getBigDecimal("VALUATION_CMV"), val.getCurrencyCode()));
			val.setFSV(util.getAmount(rs.getBigDecimal("VALUTION_FSV"), val.getCurrencyCode()));
			item.setValuation(val);
			item.setMargin(util.getDoubleValue(rs.getBigDecimal("MARGIN")));
			items = (ArrayList) itemsMap.get(new Long(colID));
			if (items == null) {
				items = new ArrayList();
			}
			items.add(item);
			itemsMap.put(new Long(colID), items);
		}
		return itemsMap;
	}
}
