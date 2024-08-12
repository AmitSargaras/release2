package com.integrosys.cms.batch.collateral.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.OBPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashDeposit;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-6-8
 * @Tag com.integrosys.cms.batch.collateral.AssetBasedPdcValuationDAO.java
 */
public class AssetBasedPdcValuationDAO extends AbstractCollateralValuationDAO {
	private static String COLTYPE_ASSET_PDT_CHEQUE = "SELECT VW_COLLATERAL_VALUATION.*"
			+ " FROM VW_COLLATERAL_VALUATION,CMS_ASSET,CMS_STAGE_ASSET"
			+ " WHERE VW_COLLATERAL_VALUATION.S_CMS_COLLATERAL_ID = CMS_STAGE_ASSET.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.A_CMS_COLLATERAL_ID = CMS_ASSET.CMS_COLLATERAL_ID"
			+ " AND (VW_COLLATERAL_VALUATION.A_SECURITY_SUB_TYPE_ID =" + "'" + ICMSConstant.COLTYPE_ASSET_PDT_CHEQUE
			+ "'" + " OR VW_COLLATERAL_VALUATION.A_SECURITY_SUB_TYPE_ID IN ("
			+ " SELECT ENTRY_CODE from COMMON_CODE_CATEGORY, COMMON_CODE_CATEGORY_ENTRY	 "
			+ " WHERE COMMON_CODE_CATEGORY.CATEGORY_CODE =	 COMMON_CODE_CATEGORY_ENTRY.CATEGORY_CODE "
			+ " AND COMMON_CODE_CATEGORY_ENTRY.ENTRY_NAME = " + "'" + ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE + "') "
			+ ")" + " AND VW_COLLATERAL_VALUATION.SECURITY_TYPE = 'AB'";

	private String SELECT_ASSET_BASED_PDC = "SELECT CMS_COLLATERAL_ID, CHEQUE_AMOUNT_CURRENCY, CHEQUE_AMOUNT,"
			+ "VALUE_BEFORE_MARGIN, VALUATION_CURRENCY, VALUATION_DATE, MARGIN,STATUS " + " FROM CMS_ASSET_PDC"
			+ " WHERE CMS_COLLATERAL_ID IN ";

	private String SELECT_STAGE_ASSET_BASED_PDC = "SELECT CMS_COLLATERAL_ID, CHEQUE_AMOUNT_CURRENCY, CHEQUE_AMOUNT,"
			+ "VALUE_BEFORE_MARGIN, VALUATION_CURRENCY, VALUATION_DATE, MARGIN,STATUS " + " FROM CMS_STAGE_ASSET_PDC"
			+ " WHERE CMS_COLLATERAL_ID  IN ";

	protected String getSelectStatement() {
		return COLTYPE_ASSET_PDT_CHEQUE;
	}

	protected boolean isValuationReqired(ICollateral collateral) {
		IAssetPostDatedCheque coll = (IAssetPostDatedCheque) collateral;
		IPostDatedCheque[] cheques = coll.getPostDatedCheques();
		if ((cheques == null) || (cheques.length == 0)) {
			return false;
		}
		ICollateralParameter srp = util.getSecurityParameter(collateral);
		if (srp == null) {
			return false;
		}
		ArrayList chequesList = new ArrayList();
		for (int i = 0; i < cheques.length; i++) {
			Date nextVal = getNextRevaluationDate(coll, cheques[i].getValuationDate(), srp);
			if (nextVal == null) {
				continue;
			}
			nextVal = util.getDateWithoutTime(nextVal);
			Date currDate = util.getDateWithoutTime(new Date(System.currentTimeMillis()));
			if (nextVal.equals(currDate)) {
				chequesList.add(cheques[i]);
			}
		}
		if (chequesList.size() == 0) {
			DefaultLogger.debug(this, "What should I do here ?");
		}
		IPostDatedCheque[] pdc = (IPostDatedCheque[]) chequesList.toArray(new OBPostDatedCheque[0]);
		coll.setPostDatedCheques(pdc);
		return true;
	}

	protected void fillCollateralDependentInfo(DBUtil dbUtil, ICollateralTrxValue[] collTrxValues) throws Exception {
		fillCollateralDependentInfo(dbUtil, collTrxValues, SELECT_ASSET_BASED_PDC, SELECT_STAGE_ASSET_BASED_PDC, null);
	};

	protected void synchronizeCollateralCMVFSV(ICollateralTrxValue collTrx) throws Exception {
		CollateralValuator valuator = new CollateralValuator();
		valuator.setCollateralCMVFSV(collTrx.getCollateral());
		valuator.setCollateralCMVFSV(collTrx.getStagingCollateral());
	}

	protected HashMap parseCollateralDependentInfo(ResultSet rs, String infoType) throws Exception {
		ArrayList items = null;
		HashMap itemsMap = new HashMap();
		while (rs.next()) {
			String status = rs.getString(PDC_STATUS);
			if ((status != null) && status.equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}
			long colID = rs.getLong(DEPOSIT_COL_ID);
			OBPostDatedCheque item = new OBPostDatedCheque();
			item.setChequeCcyCode(rs.getString(PDC_AMOUNT_CCY));
			item.setChequeAmount(util.getAmount(rs.getBigDecimal(PDC_AMOUNT), item.getChequeCcyCode()));
			String valCcy = rs.getString(PDC_VALUATION_CCY);
			item.setBeforeMarginValue(util.getAmount(rs.getBigDecimal(PDC_VALUE_BEFORE_MARGIN), valCcy));
			item.setMargin(util.getDoubleValue(rs.getBigDecimal(PDC_MARGIN)));
			item.setValuationCcyCode(valCcy);
			item.setValuationDate(rs.getDate(PDC_VALUATION_DATE));

			items = (ArrayList) itemsMap.get(new Long(colID));
			if (items == null) {
				items = new ArrayList();
			}
			items.add(item);
			itemsMap.put(new Long(colID), items);
		}
		return itemsMap;
	}

	protected void fillCollateralDependentInfo(ICollateralTrxValue[] collTrxValues, int startIndex, int size,
			HashMap actualMap, HashMap stageMap, String type) throws Exception {
		int maxIdx = startIndex + size;
		for (int index = startIndex; index < maxIdx; index++) {
			fillCollateralDependentInfo((IAssetPostDatedCheque) collTrxValues[index].getCollateral(), actualMap);
			fillCollateralDependentInfo((IAssetPostDatedCheque) collTrxValues[index].getStagingCollateral(), stageMap);
		}
	}

	private void fillCollateralDependentInfo(IAssetPostDatedCheque coll, HashMap itemMap) {
		ArrayList itemsArray = (ArrayList) itemMap.get(new Long(coll.getCollateralID()));
		IPostDatedCheque[] pdcs = null;
		if (itemsArray != null) {
			pdcs = (IPostDatedCheque[]) itemsArray.toArray(new OBCashDeposit[0]);
		}
		coll.setPostDatedCheques(pdcs);
	}

	private Date getNextRevaluationDate(ICollateral col, Date latestValDate, ICollateralParameter srp) {
		String timeFreq = null;
		int timeFreqNum = 0;
		IValuation val = col.getValuation();
		if (val != null) {
			timeFreq = val.getNonRevaluationFreqUnit();
			timeFreqNum = val.getNonRevaluationFreq();
			if ((timeFreqNum == ICMSConstant.INT_INVALID_VALUE) || (timeFreq == null)) {
				timeFreq = srp.getValuationFrequencyUnit();
				timeFreqNum = srp.getValuationFrequency();
			}
		}
		return util.getNextRevaluationDate(latestValDate, timeFreq, timeFreqNum);
	}
}
