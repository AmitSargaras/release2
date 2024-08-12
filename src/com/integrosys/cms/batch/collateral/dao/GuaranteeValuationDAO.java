package com.integrosys.cms.batch.collateral.dao;

import java.sql.ResultSet;
import java.util.Date;

import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-6-8
 * @Tag com.integrosys.cms.batch.collateral.GuaranteeValuationDAO.java
 */
public class GuaranteeValuationDAO extends AbstractCollateralValuationDAO {

	private static String SELECT_GUARANTEE_TRX = "SELECT VW_COLLATERAL_VALUATION.*,"
			+ "CMS_GUARANTEE.GUARANTEE_AMT AS A_GUARANTEE_AMT," + "CMS_GUARANTEE.GUARANTEE_DATE AS A_GUARANTEE_DATE,"
			+ "CMS_GUARANTEE.CURRENCY_CODE AS A_CURRENCY_CODE,"
			+ "CMS_STAGE_GUARANTEE.GUARANTEE_AMT AS S_GUARANTEE_AMT,"
			+ "CMS_STAGE_GUARANTEE.GUARANTEE_DATE AS S_GUARANTEE_DATE,"
			+ "CMS_STAGE_GUARANTEE.CURRENCY_CODE AS S_CURRENCY_CODE"
			+ " FROM VW_COLLATERAL_VALUATION,CMS_GUARANTEE,CMS_STAGE_GUARANTEE"
			+ " WHERE VW_COLLATERAL_VALUATION.S_CMS_COLLATERAL_ID = CMS_STAGE_GUARANTEE.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.A_CMS_COLLATERAL_ID = CMS_GUARANTEE.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.SECURITY_TYPE = 'GT'";

	protected String getSelectStatement() {
		return SELECT_GUARANTEE_TRX;
	}

	protected boolean isValuationReqired(ICollateral collateral) {
		IValuation val = collateral.getValuation();
		if (val == null) {
			return false;
		}
		Date nextVal = getNextRevaluationDate(collateral, val.getValuationDate());
		collateral.getValuation().setNextRevaluationDate(nextVal);
		if (nextVal == null) {
			return false;
		}
		nextVal = util.getDateWithoutTime(nextVal);
		Date currDate = util.getDateWithoutTime(new Date(System.currentTimeMillis()));
		if (nextVal.compareTo(currDate) <= 0) {
			return true;
		}
		return false;
	}

	protected void fillCollateralDetailInfo(ResultSet rs, ICollateral collateral, String prefix) throws Exception {
		IGuaranteeCollateral col = (IGuaranteeCollateral) collateral;
		col.setGuaranteeCcyCode(rs.getString(prefix + GUARANTEE_CCY));
		col.setGuaranteeAmount(util.getAmount(rs.getBigDecimal(prefix + GUARANTEE_AMOUNT), col.getGuaranteeCcyCode()));
		col.setGuaranteeDate(rs.getDate(prefix + "GUARANTEE_DATE"));
	}

	protected void synchronizeCollateralCMVFSV(ICollateralTrxValue collTrx) throws Exception {
		CollateralValuator valuator = new CollateralValuator();
		valuator.setCollateralCMVFSV(collTrx.getCollateral());
		valuator.setCollateralCMVFSV(collTrx.getStagingCollateral());
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
