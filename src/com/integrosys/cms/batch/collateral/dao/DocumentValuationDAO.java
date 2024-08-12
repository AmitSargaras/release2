package com.integrosys.cms.batch.collateral.dao;

import java.sql.ResultSet;
import java.util.Date;

import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.type.document.IDocumentCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-6-2
 * @Tag com.integrosys.cms.batch.collateral.DocumentValuationDAO.java
 */
public class DocumentValuationDAO extends AbstractCollateralValuationDAO {
	private static String SELECT_DOCUMENT_TRX = " SELECT VW_COLLATERAL_VALUATION.*,"
			+ "CMS_DOCUMENT.DOC_AMT AS A_DOC_AMT," + "CMS_DOCUMENT.DOCUMENT_DATE AS A_DOCUMENT_DATE, "
			+ "CMS_DOCUMENT.DOC_AMT_CCY AS A_DOC_AMT_CCY," + "CMS_STAGE_DOCUMENT.DOC_AMT AS S_DOC_AMT,"
			+ "CMS_STAGE_DOCUMENT.DOCUMENT_DATE AS S_DOCUMENT_DATE,"
			+ "CMS_STAGE_DOCUMENT.DOC_AMT_CCY AS S_DOC_AMT_CCY"
			+ " FROM VW_COLLATERAL_VALUATION,CMS_DOCUMENT,CMS_STAGE_DOCUMENT"
			+ " WHERE VW_COLLATERAL_VALUATION.S_CMS_COLLATERAL_ID = CMS_STAGE_DOCUMENT.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.A_CMS_COLLATERAL_ID = CMS_DOCUMENT.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.SECURITY_TYPE = 'DC'";

	protected String getSelectStatement() {
		return SELECT_DOCUMENT_TRX;
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
		IDocumentCollateral coll = (IDocumentCollateral) collateral;
		coll.setDocumentAmount(util.getAmount(rs.getBigDecimal(prefix + "DOC_AMT"), rs
				.getString(prefix + "DOC_AMT_CCY")));
		coll.setDocumentDate(rs.getDate(prefix + "DOCUMENT_DATE"));
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
		String timeFreq = null;
		int timeFreqNum = 0;
		IValuation val = col.getValuation();
		if (val != null) {
			timeFreq = val.getNonRevaluationFreqUnit();
			timeFreqNum = val.getNonRevaluationFreq();
		}
		if ((timeFreqNum == ICMSConstant.INT_INVALID_VALUE) || (timeFreq == null) || (timeFreq.trim().length() == 0)) {
			timeFreq = srp.getValuationFrequencyUnit();
			timeFreqNum = srp.getValuationFrequency();
		}
		return util.getNextRevaluationDate(latestValDate, timeFreq, timeFreqNum);
	}
}
