package com.integrosys.cms.batch.collateral.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.CollateralDetailFactory;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralDAOConstants;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.OBValuation;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-5-31
 * @Tag com.integrosys.cms.batch.collateral.AbstractCollateralValuationDAO.java
 */
public abstract class AbstractCollateralValuationDAO implements ICollateralDAOConstants, ICollateralValuationDAO {
	protected CollateralValuationUtil util = new CollateralValuationUtil();

	private final String UPDATE_SECURITY_VALUATION = "UPDATE CMS_SECURITY SET CMV=?,CMV_CURRENCY=?,FSV=?,FSV_CURRENCY=?,VERSION_TIME=? WHERE CMS_COLLATERAL_ID= ?";

	private final String UPDATE_STAGE_SECURITY_VALUATION = "UPDATE CMS_STAGE_SECURITY SET CMV=?,CMV_CURRENCY=?,FSV=?,FSV_CURRENCY=?,VERSION_TIME=? WHERE CMS_COLLATERAL_ID= ?";

	private final String UPDATE_STAGE_VALUATION = "UPDATE CMS_STAGE_VALUATION SET VALUATION_CURRENCY=?,CMV=?,FSV =?,VALUE_BEFORE_MARGIN=?,VALUATION_DATE =? WHERE CMS_COLLATERAL_ID=?";

	private final String PREFIX_ACTUAL = "A_";

	private final String PREFIX_STAGE = "S_";

	private final String PREFIX_VALUATION = "V_";

	protected abstract String getSelectStatement();

	protected abstract boolean isValuationReqired(ICollateral collateral);

	protected abstract void synchronizeCollateralCMVFSV(ICollateralTrxValue trxVal) throws Exception;

	protected void fillCollateralDetailInfo(ResultSet rs, ICollateral collateral, String prefix) throws Exception {
	};

	protected void fillCollateralDependentInfo(DBUtil dbUtil, ICollateralTrxValue[] collTrxValues) throws Exception {
	};

	protected void updateCollateralDetailCMVFSV(ICollateralTrxValue trxVal) throws Exception {
	}

	public ICollateralTrxValue[] getCollateralTrxValues(String countryCode) throws CollateralException {
		String selectStmt = constructSelectStatement(countryCode);
		ArrayList collTrxValueList = new ArrayList();
		DBUtil dbUtil = null;
		try {
			dbUtil = util.getDBUtil();
			dbUtil.setSQL(selectStmt);
			long startT = System.currentTimeMillis();
			DefaultLogger.debug(this, " - SQL : " + selectStmt);
			ResultSet rs = dbUtil.executeQuery();
			long endT = System.currentTimeMillis();
			DefaultLogger.debug(this, "\tQuery Time : " + (endT - startT));
			startT = System.currentTimeMillis();
			collTrxValueList = parseCollateralTrxValues(rs);
			endT = System.currentTimeMillis();
			DefaultLogger.debug(this, "\tParse Time : " + (endT - startT));
			rs.close();
			ICollateralTrxValue[] trxValueArray = (ICollateralTrxValue[]) collTrxValueList
					.toArray(new ICollateralTrxValue[0]);
			startT = System.currentTimeMillis();
			fillCollateralDependentInfo(dbUtil, trxValueArray);
			endT = System.currentTimeMillis();
			DefaultLogger.debug(this, "\tQuery Child Time : " + (endT - startT));
			return getToValuationCollateralTrx(trxValueArray);
		}
		catch (Exception e) {
			throw new CollateralException("Error in getting collaterals!", e);
		}
		finally {
			util.finalize(dbUtil);
		}
	}

	public void updateCollateralValuation(ICollateralTrxValue trxVal) throws CollateralException {
		DBUtil dbUtil = null;
		try {
			dbUtil = util.getDBUtil();
			ICollateral collateral = trxVal.getStagingCollateral();

			updateCollateralValuation(dbUtil, collateral, UPDATE_STAGE_SECURITY_VALUATION);
			updateStageValuationCMVFSV(dbUtil, collateral);

			collateral = trxVal.getCollateral();
			updateCollateralValuation(dbUtil, collateral, UPDATE_SECURITY_VALUATION);

			updateCollateralDetailCMVFSV(trxVal);
		}
		catch (Exception e) {
			throw new CollateralException("failed to update collateral valuation trx value", e);
		}
		finally {
			util.finalize(dbUtil);
		}
	}

	private void updateCollateralValuation(DBUtil dbUtil, ICollateral collateral, String updateSQL) throws Exception {
		StringBuffer sqlBuffer = new StringBuffer(updateSQL);
		sqlBuffer.append(" AND VERSION_TIME ");
		long versionTime = collateral.getVersionTime();
		if (versionTime == ICMSConstant.LONG_INVALID_VALUE) {
			sqlBuffer.append(" IS NULL ");
		}
		else {
			sqlBuffer.append(" = ? ");
		}
		// DefaultLogger.debug(this, " - SQL : ");
		dbUtil.setSQL(sqlBuffer.toString());
		util.setDBAmount(dbUtil, 1, 2, collateral.getCMV());
		util.setDBAmount(dbUtil, 3, 4, collateral.getFSV());
		dbUtil.setLong(5, VersionGenerator.getVersionNumber());
		dbUtil.setLong(6, collateral.getCollateralID());
		if (versionTime != ICMSConstant.LONG_INVALID_VALUE) {
			dbUtil.setLong(7, collateral.getVersionTime());
		}
		dbUtil.executeUpdate();
	}

	private ICollateralTrxValue[] getToValuationCollateralTrx(ICollateralTrxValue[] trxValueArray) throws Exception {
		ArrayList toValList = new ArrayList();
		long startT = System.currentTimeMillis();
		int length = (trxValueArray == null ? 0 : trxValueArray.length);
		for (int index = 0; index < length; index++) {
			ICollateral oldColl = (ICollateral) AccessorUtil.deepClone(trxValueArray[index].getCollateral());
			synchronizeCollateralCMVFSV(trxValueArray[index]);
			ICollateral newColl = trxValueArray[index].getCollateral();
			if (newColl.getValuation() == null) {
				continue;
			}
			if (!util.isValChanged(newColl, oldColl)) {
				continue;
			}
			toValList.add(trxValueArray[index]);
		}
		long endT = System.currentTimeMillis();
		DefaultLogger.debug(this, "\tSet CMVFSV Time : " + (endT - startT));
		DefaultLogger.debug(this, "Num of To Val Coll : " + toValList.size());
		return (ICollateralTrxValue[]) toValList.toArray(new ICollateralTrxValue[0]);
	}

	private void updateStageValuationCMVFSV(DBUtil dbUtil, ICollateral collateral) throws Exception {
		IValuation val = collateral.getValuation();
		if (val == null) {
			return;
		}
		// DefaultLogger.debug(this, " - SQL : " + UPDATE_STAGE_VALUATION);
		dbUtil.setSQL(UPDATE_STAGE_VALUATION);
		dbUtil.setString(1, val.getCurrencyCode());
		util.setDBAmount(dbUtil, 2, val.getCMV());
		util.setDBAmount(dbUtil, 3, val.getFSV());
		util.setDBAmount(dbUtil, 4, val.getBeforeMarginValue());
		if (val.getValuationDate() != null) {
			Timestamp ts = new Timestamp(val.getValuationDate().getTime());
			dbUtil.setTimestamp(5, ts);
		}
		else {
			dbUtil.setNull(5, Types.DATE);
		}
		dbUtil.setLong(6, collateral.getCollateralID());
		dbUtil.executeUpdate();
	}

	private String constructSelectStatement(String countryCode) {
		String selectStmt = getSelectStatement();
		StringBuffer sqlBuffer = new StringBuffer(selectStmt);
		if (countryCode != null) {
			sqlBuffer.append(" AND VW_COLLATERAL_VALUATION.A_SECURITY_LOCATION = '");
			sqlBuffer.append(countryCode);
			sqlBuffer.append("'");
		}
		return sqlBuffer.toString();
	}

	private ArrayList parseCollateralTrxValues(ResultSet rs) throws Exception {
		ArrayList collTrxValueList = new ArrayList();
		int filteredCount = 0;
		if (rs != null) {
			while (rs.next()) {
				OBCollateralSubType subType = new OBCollateralSubType();
				subType.setSubTypeCode(rs.getString("A_SECURITY_SUB_TYPE_ID"));
				ICollateral actualColl = constructActualCollateral(rs, subType);
				ICollateral stageColl = constructStageCollateral(rs, subType);
				ICollateralTrxValue trxVal = constructCollateralTrxValue(rs, actualColl, stageColl);
				if (isValuationReqired(actualColl)) {
					collTrxValueList.add(trxVal);
				}
				else {
					filteredCount++;
				}
			}
		}
		DefaultLogger.debug(this, "Num of Filtered collateral : " + filteredCount);
		DefaultLogger.debug(this, "Num of collateral : " + collTrxValueList.size());
		return collTrxValueList;
	}

	private ICollateralTrxValue constructCollateralTrxValue(ResultSet rs, ICollateral actualColl, ICollateral stageColl)
			throws Exception {
		OBCollateralTrxValue trxVal = new OBCollateralTrxValue();
		trxVal.setCollateral(actualColl);
		trxVal.setStagingCollateral(stageColl);
		trxVal.setTransactionID(rs.getString("TRANSACTION_ID"));
		trxVal.setReferenceID(String.valueOf(actualColl.getCollateralID()));
		trxVal.setStagingReferenceID(String.valueOf(stageColl.getCollateralID()));
		return trxVal;
	}

	private ICollateral constructActualCollateral(ResultSet rs, OBCollateralSubType subType) throws Exception {
		ICollateral actualColl = CollateralDetailFactory.getOB(subType);
		fillCollateralInfo(rs, actualColl, PREFIX_ACTUAL);
		return actualColl;
	}

	private ICollateral constructStageCollateral(ResultSet rs, OBCollateralSubType subType) throws Exception {
		ICollateral stageColl = CollateralDetailFactory.getOB(subType);
		fillCollateralInfo(rs, stageColl, PREFIX_STAGE);
		return stageColl;
	}

	private void fillCollateralInfo(ResultSet rs, ICollateral collateral, String prefix) throws Exception {
		collateral.setCollateralLocation(rs.getString(prefix + SECURITY_LOCATION));
		String secCCY = rs.getString(prefix + SECURITY_CCY_CODE);
		collateral.setCurrencyCode(secCCY);

		collateral.setVersionTime(util.getLongValue(rs.getBigDecimal(prefix + SECURITY_VERSION_TIME)));
		collateral.setCollateralID(rs.getLong(prefix + COLLATERAL_ID));

		collateral.setCMVCcyCode(rs.getString(prefix + SECURITY_CMV_CCY));
		collateral.setCMV(util.getAmount(rs.getBigDecimal(prefix + SECURITY_CMV), collateral.getCMVCcyCode()));

		collateral.setFSVCcyCode(rs.getString(prefix + SECURITY_FSV_CCY));
		collateral.setFSV(util.getAmount(rs.getBigDecimal(prefix + SECURITY_FSV), collateral.getFSVCcyCode()));

		collateral.setMargin(util.getDoubleValue(rs.getBigDecimal(prefix + SECURITY_MARGIN)));

		collateral.setValuation(getValuation(rs, prefix + PREFIX_VALUATION, secCCY));
		fillCollateralDetailInfo(rs, collateral, prefix);
	}

	private IValuation getValuation(ResultSet rs, String prefix, String secCCY) throws SQLException {
		long valuationID = rs.getLong(prefix + VALUATION_ID);
		// DefaultLogger.debug(this, " - ValuationID : " + valuationID);
		if ((valuationID == 0) || (valuationID == ICMSConstant.LONG_INVALID_VALUE)) {
			return null;
		}
		OBValuation valuation = new OBValuation();
		valuation.setValuationID(valuationID);
		valuation.setCurrencyCode(rs.getString(prefix + VALUATION_CCY));
		Timestamp valDate = rs.getTimestamp(prefix + VALUATION_DATE);
		if (valDate != null) {
			valuation.setValuationDate(new Date(valDate.getTime()));
		}
		valuation.setValuerName(rs.getString(prefix + VALUATION_VALUER));
		valuation.setFSV(util.getAmount(rs.getBigDecimal(prefix + VALUATION_FSV), secCCY));
		valuation.setCMV(util.getAmount(rs.getBigDecimal(prefix + VALUATION_CMV), secCCY));
		valuation.setBeforeMarginValue(util.getAmount(rs.getBigDecimal(prefix + VALUATION_BEFORE_MARGIN), secCCY));
		valuation.setRevaluationFreq(rs.getInt(prefix + VALUATION_REVAL_FREQ));
		valuation.setRevaluationFreqUnit(rs.getString(prefix + VALUATION_REVAL_FREQ_UNIT));
		valuation.setCollateralID(rs.getLong(prefix + COLLATERAL_ID));
		valuation.setNonRevaluationFreq(rs.getInt(prefix + VALUATION_NON_REVAL_FREQ));
		valuation.setNonRevaluationFreqUnit(rs.getString(prefix + VALUATION_NON_REVAL_FREQ_UNIT));
		valuation.setComments(rs.getString(prefix + VALUATION_COMMENTS));
		return valuation;
	}

	protected void setCommonColValues(ICollateral col, Amount totalCMV, Amount totalFSV, String newCcyCode,
			boolean updateValDate, Date defaultValDate) {
		IValuation val = col.getValuation();
		if ((val == null) && (totalCMV != null)) {
			val = new OBValuation();
		}
		if (val != null) {
			if (val.getValuationDate() == null) {
				if ((val.getCMV() == null) && (totalCMV == null)) {
					// do nothing
				}
				else {
					val
							.setValuationDate(defaultValDate == null ? new Date(System.currentTimeMillis())
									: defaultValDate);
				}
			}
			else {
				if (updateValDate) {
					val.setValuationDate(new Date(System.currentTimeMillis()));
				}
			}
			val.setCollateralID(col.getCollateralID());
			val.setCMV(totalCMV);
			val.setFSV(totalFSV);
			val.setBeforeMarginValue(totalCMV);
			val.setCurrencyCode(newCcyCode);
			val.setAfterMarginValue(totalFSV);
			col.setValuation(val);
		}
		col.setCMV(totalCMV);
		col.setCMVCcyCode(col.getCMV() == null ? null : col.getCMV().getCurrencyCode());
		col.setFSV(totalFSV);
		col.setFSVCcyCode(col.getFSV() == null ? null : col.getFSV().getCurrencyCode());
	}

	protected void fillCollateralDependentInfo(DBUtil dbUtil, ICollateralTrxValue[] collTrxValues, String queryActual,
			String queryStage, String infoType) throws Exception {
		if ((collTrxValues == null) || (collTrxValues.length == 0)) {
			return;
		}
		double maxcount = (double) MAX_IN_CLAUSE;
		double len = collTrxValues.length;
		int size = (int) Math.ceil(len / maxcount);
		int remainder = (int) (len % maxcount);
		String actualSQL = null;
		String stageSQL = null;
		int num = 0;
		if (size > 1) {
			num = MAX_IN_CLAUSE;
			actualSQL = getPreparedSQL(num, queryActual);
			stageSQL = getPreparedSQL(num, queryStage);
		}
		for (int index = 0, startIndex = 0; index < size; index++) {
			if ((index == size - 1) && (remainder != 0)) {
				num = remainder;
				actualSQL = getPreparedSQL(num, queryActual);
				stageSQL = getPreparedSQL(num, queryStage);
			}
			dbUtil.setSQL(actualSQL);
			fillActualParameters(dbUtil, collTrxValues, startIndex, num);
			ResultSet rs = dbUtil.executeQuery();
			HashMap actualItemMap = parseCollateralDependentInfo(rs, infoType);
			rs.close();
			dbUtil.setSQL(stageSQL);
			fillStageParameters(dbUtil, collTrxValues, startIndex, num);
			rs = dbUtil.executeQuery();
			HashMap stageItemMap = parseCollateralDependentInfo(rs, infoType);
			rs.close();
			fillCollateralDependentInfo(collTrxValues, startIndex, num, actualItemMap, stageItemMap, infoType);
			startIndex += MAX_IN_CLAUSE;
		}
	}

	protected void fillCollateralDependentInfo(ICollateralTrxValue[] collTrxValues, int startIndex, int size,
			HashMap actualMap, HashMap stageMap, String infoType) throws Exception {
	}

	protected HashMap parseCollateralDependentInfo(ResultSet rs, String infoType) throws Exception {
		return new HashMap();
	}

	private String getPreparedSQL(int num, String sql) throws Exception {
		if (num < 1) {
			throw new Exception("Num of Param must more than 1");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(sql);
		for (int index = 0; index < num; index++) {
			if (index == 0) {
				buffer.append("(");
			}
			else {
				buffer.append(",");
			}
			buffer.append("?");
		}
		buffer.append(")");
		// DefaultLogger.debug(this, " - SQL: " + buffer.toString());
		return buffer.toString();
	}

	private void fillActualParameters(DBUtil dbUtil, ICollateralTrxValue[] collTrxValues, int startIndex, int numOfParam)
			throws Exception {
		for (int index = 0; index < numOfParam; index++) {
			dbUtil.setLong(index + 1, collTrxValues[startIndex + index].getCollateral().getCollateralID());
		}
	}

	private void fillStageParameters(DBUtil dbUtil, ICollateralTrxValue[] collTrxValues, int startIndex, int numOfParam)
			throws Exception {
		for (int index = 0; index < numOfParam; index++) {
			dbUtil.setLong(index + 1, collTrxValues[startIndex + index].getStagingCollateral().getCollateralID());
		}
	}
}
