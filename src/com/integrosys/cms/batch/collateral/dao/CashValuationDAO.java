package com.integrosys.cms.batch.collateral.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashDeposit;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-6-8
 * @Tag com.integrosys.cms.batch.collateral.CashValuationDAO.java
 */
public class CashValuationDAO extends AbstractCollateralValuationDAO {
	private String SELECT_CASH_TRX = "SELECT VW_COLLATERAL_VALUATION.*"
			+ " FROM VW_COLLATERAL_VALUATION,CMS_CASH,CMS_STAGE_CASH"
			+ " WHERE VW_COLLATERAL_VALUATION.S_CMS_COLLATERAL_ID = CMS_STAGE_CASH.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.A_CMS_COLLATERAL_ID = CMS_CASH.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.SECURITY_TYPE = 'CS'";

	private String SELECT_CASH_DEPOSIT = "SELECT CMS_COLLATERAL_ID,DEPOSIT_AMOUNT,DEPOSIT_AMOUNT_CURRENCY,STATUS "
			+ " FROM CMS_CASH_DEPOSIT" + " WHERE CMS_COLLATERAL_ID IN ";

	private String SELECT_STAGE_CASH_DEPOSIT = "SELECT CMS_COLLATERAL_ID,DEPOSIT_AMOUNT,DEPOSIT_AMOUNT_CURRENCY,STATUS "
			+ " FROM CMS_STAGE_CASH_DEPOSIT" + " WHERE CMS_COLLATERAL_ID IN ";

	protected String getSelectStatement() {
		return SELECT_CASH_TRX;
	}

	protected boolean isValuationReqired(ICollateral collateral) {
		IValuation val = collateral.getValuation();
		if (val == null) {
			return false;
		}
		Date nextVal = getNextRevaluationDate(collateral, val.getValuationDate());
		if (nextVal == null) {
			return false;
		}
		collateral.getValuation().setNextRevaluationDate(nextVal);
		nextVal = util.getDateWithoutTime(nextVal);
		Date currDate = util.getDateWithoutTime(new Date(System.currentTimeMillis()));
		// minus is to cater for last failed valuation.
		if (nextVal.compareTo(currDate) <= 0) {
			return true;
		}
		return false;
	}

	protected void fillCollateralDependentInfo(DBUtil dbUtil, ICollateralTrxValue[] collTrxValues) throws Exception {
		fillCollateralDependentInfo(dbUtil, collTrxValues, SELECT_CASH_DEPOSIT, SELECT_STAGE_CASH_DEPOSIT, null);
	};

	protected void synchronizeCollateralCMVFSV(ICollateralTrxValue collTrx) throws Exception {
		CollateralValuator valuator = new CollateralValuator();
		valuator.setCollateralCMVFSV(collTrx.getCollateral());
	}

	protected HashMap parseCollateralDependentInfo(ResultSet rs, String infoType) throws Exception {
		ArrayList items = null;
		HashMap itemsMap = new HashMap();
		while (rs.next()) {
			String status = rs.getString(DEPOSIT_STATUS);
			if ((status != null) && status.equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}
			long colID = rs.getLong(DEPOSIT_COL_ID);
			OBCashDeposit item = new OBCashDeposit();
			item.setDepositCcyCode(rs.getString(DEPOSIT_AMOUNT_CCY));
			item.setDepositAmount(util.getAmount(rs.getBigDecimal(DEPOSIT_AMOUNT), item.getDepositCcyCode()));
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
			fillCollateralDependentInfo((ICashCollateral) collTrxValues[index].getCollateral(), actualMap);
			fillCollateralDependentInfo((ICashCollateral) collTrxValues[index].getStagingCollateral(), stageMap);
		}
	}

	private void fillCollateralDependentInfo(ICashCollateral coll, HashMap itemMap) {
		ArrayList itemsArray = (ArrayList) itemMap.get(new Long(coll.getCollateralID()));
		ICashDeposit[] deposits = null;
		if (itemsArray != null) {
			deposits = (ICashDeposit[]) itemsArray.toArray(new OBCashDeposit[0]);
		}
		coll.setDepositInfo(deposits);
	}

	private Date getNextRevaluationDate(ICollateral col, Date latestValDate) {
		ICollateralParameter srp = util.getSecurityParameter(col);
		if (srp == null) {
			return null;
		}
		String timeFreq = srp.getValuationFrequencyUnit();
		int timeFreqNum = srp.getValuationFrequency();
		return util.getNextRevaluationDate(latestValDate, timeFreq, timeFreqNum);
	}
}