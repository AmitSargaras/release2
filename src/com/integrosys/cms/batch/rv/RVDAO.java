/*
 * Created on Jun 19, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.rv;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.OBValuation;
import com.integrosys.cms.app.common.bus.PaginationBean;
import com.integrosys.cms.app.common.bus.PaginationUtil;
import com.integrosys.cms.app.common.bus.PaginationUtilFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.UIUtil;

public class RVDAO {
	// private static final String QUERY =
	// "SELECT SECURITY_SUB_TYPE_ID,NET_REALIZABLE_VALUE,SCI_ORIG_SECURITY_CURRENCY, "+
	// "CMV,CMV_CURRENCY,FSV,FSV_CURRENCY,SCI_SECURITY_CURRENCY,CMS_COLLATERAL_ID "+
	// "FROM CMS_SECURITY "+
	// "WHERE (SCI_SECURITY_TYPE_VALUE='PT' "+
	// "OR SCI_SECURITY_TYPE_VALUE='MS' "+
	// "OR (SCI_SECURITY_TYPE_VALUE='DC' AND NET_REALIZABLE_VALUE<>0) "+
	// "OR ((SCI_SECURITY_TYPE_VALUE='AB' "+
	// "	OR SCI_SECURITY_TYPE_VALUE='CL' "+
	// "	OR SCI_SECURITY_TYPE_VALUE='CS' "+
	// "	OR SCI_SECURITY_TYPE_VALUE='GT' "+
	// "	OR SCI_SECURITY_TYPE_VALUE='IN' "+
	// "	OR SCI_SECURITY_TYPE_VALUE='OT') "+
	// "AND (NET_REALIZABLE_VALUE<>CMV))) "+
	// "AND DAYS(LAST_REMARGIN_DATE) = DAYS(CURRENT TIMESTAMP) ";

	private static final String QUERY = "SELECT SECURITY_SUB_TYPE_ID,NET_REALIZABLE_VALUE,SCI_ORIG_SECURITY_CURRENCY, "
			+ "CMV,CMV_CURRENCY,FSV,FSV_CURRENCY,SCI_SECURITY_CURRENCY,CMS_COLLATERAL_ID,RESERVE_PRICE "
			+ "FROM CMS_SECURITY " + "WHERE ( SCI_SECURITY_TYPE_VALUE='PT' " + "OR SCI_SECURITY_TYPE_VALUE='MS' "
			+ "OR SCI_SECURITY_TYPE_VALUE='DC' " + "OR SCI_SECURITY_TYPE_VALUE='AB' "
			+ "OR SCI_SECURITY_TYPE_VALUE='CL' " + "OR SCI_SECURITY_TYPE_VALUE='CS' "
			+ "OR SCI_SECURITY_TYPE_VALUE='GT' " + "OR SCI_SECURITY_TYPE_VALUE='IN' "
			//For DB2
//			+ "OR SCI_SECURITY_TYPE_VALUE='OT' ) " + "AND DAYS(LAST_REMARGIN_DATE) = DAYS(CURRENT TIMESTAMP) ";
			//For Oracle
			+ "OR SCI_SECURITY_TYPE_VALUE='OT' ) " + "AND TRUNC(LAST_REMARGIN_DATE) = TRUNC(CURRENT_TIMESTAMP) ";

	public void clearAcctRV() {
		DBUtil dbUtil = null;
		String query = "UPDATE SCI_LSP_SYS_XREF ACCT SET ACCT_RV = NULL, ACCT_RV_CCY = NULL";

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			dbUtil.executeUpdate();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			finalize(dbUtil, null);
		}

	}

	private String getSecurityQueryStr(String cty) {
		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append(QUERY);

		if (cty != null) {
			queryBuffer.append(" AND SECURITY_LOCATION = '");
			queryBuffer.append(cty);
			queryBuffer.append("'");
		}

		queryBuffer.append(" ORDER BY CMS_COLLATERAL_ID");

		DefaultLogger.debug(this, "RV-SQL: " + queryBuffer.toString());
		return queryBuffer.toString();
	}

	public long getNoOfSecurities(String cty) {

		PaginationUtil pgUtil = new PaginationUtilFactory().getPaginationUtil(PaginationUtilFactory.DBTYPE_DB2);
		String query = pgUtil.formCountQuery(getSecurityQueryStr(cty));

		DBUtil dbUtil = null;
		ResultSet rs = null;
		long result = 0;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			rs = dbUtil.executeQuery();
			if (rs.next()) {
				result = rs.getLong(1);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			finalize(dbUtil, rs);
		}
		return result;
	}

	public Map getAccountReservePriceInfo(List colList) throws Exception {
		DBUtil dbUtil = null;
		ResultSet rs = null;
		Map result = new HashMap();
		String query = "SELECT COL.CMS_COLLATERAL_ID, \n" + "       COL.FSV_CURRENCY, \n"
				+ "       CHARGE.CHARGE_DETAIL_ID, \n"
				+ "       CONVERT_AMT(CHARGE.CHARGE_AMOUNT,CHARGE.CHARGE_CURRENCY_CODE, \n"
				+ "                   COL.FSV_CURRENCY) AS CHARGE_AMT, \n" + "       ACCT.CMS_LSP_SYS_XREF_ID, \n"
				+ "       ACCT.LSX_EXT_SYS_ACCT_NUM, \n" + "       ACCT.ACCT_DELQ_IND, \n" + "       ACCT.ACCT_RV, \n"
				+ "       CONVERT_AMT(COALESCE(LMT.CMS_OUTSTANDING_AMT,0),LMT.LMT_CRRNCY_ISO_CODE, \n"
				+ "                   COL.FSV_CURRENCY) AS NET_OUTSTANDING \n" + "  FROM CMS_SECURITY COL, \n"
				+ "       CMS_CHARGE_DETAIL CHARGE, \n" + "       CMS_LIMIT_CHARGE_MAP CHARGE_MAP, \n"
				+ "       CMS_LIMIT_SECURITY_MAP SEC_MAP, \n" + "       SCI_LSP_APPR_LMTS LMT, \n"
				+ "       SCI_LSP_LMTS_XREF_MAP ACCTMAP, \n" + "       SCI_LSP_SYS_XREF ACCT \n"
				+ " WHERE col.cms_collateral_id IN " + getColIdList(colList)
				+ "   AND COL.CMS_COLLATERAL_ID = CHARGE.CMS_COLLATERAL_ID \n"
				+ "   AND CHARGE.CHARGE_DETAIL_ID = CHARGE_MAP.CHARGE_DETAIL_ID \n"
				+ "   AND CHARGE_MAP.STATUS = 'ACTIVE' \n"
				+ "   AND SEC_MAP.CMS_LSP_APPR_LMTS_ID = LMT.CMS_LSP_APPR_LMTS_ID \n"
				+ "   AND SEC_MAP.CMS_COLLATERAL_ID = COL.CMS_COLLATERAL_ID \n"
				+ "   AND SEC_MAP.CHARGE_ID = CHARGE_MAP.CHARGE_ID \n" + "   AND LMT.CMS_LIMIT_STATUS = 'ACTIVE' \n"
				+ "   AND SEC_MAP.UPDATE_STATUS_IND <> 'D' \n"
				+ "   AND LMT.CMS_LSP_APPR_LMTS_ID = ACCTMAP.CMS_LSP_APPR_LMTS_ID \n"
				+ "   AND ACCTMAP.CMS_STATUS = 'ACTIVE' \n"
				+ "   AND ACCTMAP.CMS_LSP_SYS_XREF_ID = ACCT.CMS_LSP_SYS_XREF_ID \n"
				+ " ORDER BY CHARGE.CMS_COLLATERAL_ID, CHARGE.SECURITY_RANK ";

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			rs = dbUtil.executeQuery();

			String prevColId = "";

			List curAccounts = null;
			int ind = 0;
			while (rs.next()) {
				String curColId = rs.getString("CMS_COLLATERAL_ID");
				String fsvCurrency = rs.getString("FSV_CURRENCY");
				String chargeAmt = rs.getString("CHARGE_AMT");
				String accountId = rs.getString("CMS_LSP_SYS_XREF_ID");
				String accountNo = rs.getString("LSX_EXT_SYS_ACCT_NUM");
				String delqInd = rs.getString("ACCT_DELQ_IND");
				String curRv = rs.getString("ACCT_RV");
				String netOutstanding = rs.getString("NET_OUTSTANDING");

				if (!curColId.equals(prevColId)) {
					curAccounts = new ArrayList();
					result.put(curColId, curAccounts);
					prevColId = curColId;
					ind = ind + 1;
				}

				AccountRVDetail curRVDtl = getAccountRVDetail(Long.parseLong(accountId), result);
				curRVDtl.setAccountId(Long.parseLong(accountId));
				curRVDtl.setAccountNo(accountNo);
				curRVDtl.setAccountNPL(("Y".equals(delqInd)));

				if (chargeAmt != null) {
					curRVDtl.setChargeAmt(new Amount(Double.parseDouble(chargeAmt), fsvCurrency));
				}

				if ((fsvCurrency != null) && (netOutstanding != null)) {
					curRVDtl.setOutstandingAmt(new Amount(Double.parseDouble(netOutstanding), fsvCurrency));
				}

				if (curRv != null) {
					curRVDtl.setCurRvValue(new Amount(Double.parseDouble(curRv), fsvCurrency));
				}
				else {
					curRVDtl.setCurRvValue(new Amount(0, fsvCurrency));
				}

				curAccounts.add(curRVDtl);
			}

			return result;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			finalize(dbUtil, rs);
		}
	}

	private AccountRVDetail getAccountRVDetail(long accountId, Map accountMap) {
		Object[] keyArr = accountMap.keySet().toArray();
		for (int i = 0; i < keyArr.length; i++) {
			List curAcctArr = (List) (accountMap.get(keyArr[i]));

			for (int j = 0; j < curAcctArr.size(); j++) {
				AccountRVDetail nextDtl = (AccountRVDetail) (curAcctArr.get(j));
				if (nextDtl.getAccountId() == accountId) {
					return nextDtl;
				}
			}
		}

		return new AccountRVDetail();
	}

	public String getColIdList(List colList) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");

		if (colList != null) {
			for (int i = 0; i < colList.size(); i++) {
				ICollateral nextCol = (ICollateral) (colList.get(i));
				String nextColId = String.valueOf(nextCol.getCollateralID());
				sb.append(nextColId);
				sb.append(", ");
			}
		}

		String res = sb.toString();
		if (res.endsWith(", ")) {
			res = res.substring(0, res.length() - 2);
		}
		res = res + ")";

		return res;
	}

	public String getRVColIdList(List colList) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		boolean hasColl = false;
		if (colList != null) {
			for (int i = 0; i < colList.size(); i++) {
				ICollateral nextCol = (ICollateral) (colList.get(i));
				if (ICMSConstant.SECURITY_TYPE_PROPERTY.equals(nextCol.getCollateralSubType().getTypeCode())
				// ||ICMSConstant.SECURITY_TYPE_MARKETABLE.equals(nextCol.getCollateralSubType().getTypeCode())
				) {
					String nextColId = String.valueOf(nextCol.getCollateralID());
					sb.append(nextColId);
					sb.append(", ");
					hasColl = true;
				}
			}
		}
		if (!hasColl) {
			return null;
		}
		String res = sb.toString();
		if (res.endsWith(", ")) {
			res = res.substring(0, res.length() - 2);
		}
		res = res + ")";

		return res;
	}

	public List getNextBatchSecurities(String country, long startIndex, int batchSize) throws Exception {
		List colList = getSecurityFsvCmv(country, startIndex, batchSize);
		// getReservPriceInfo(colList);
		return colList;
	}

	public void persistSecRVInfo(long secId, Amount secRv) throws Exception {
		DBUtil dbUtil = null;
		String query = "UPDATE CMS_SECURITY SET NET_REALIZABLE_VALUE = ? WHERE CMS_COLLATERAL_ID = ?";

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			if (secRv != null) {
				dbUtil.setBigDecimal(1, secRv.getAmountAsBigDecimal());
			}
			else {
				dbUtil.setString(1, null);
			}

			dbUtil.setLong(2, secId);
			dbUtil.executeUpdate();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			finalize(dbUtil, null);
		}
	}

	public void persistStagingSecRVInfo(long secId, Amount secRv) throws Exception {
		DBUtil dbUtil = null;
		String query = "UPDATE CMS_STAGE_SECURITY stg SET stg.NET_REALIZABLE_VALUE = ? WHERE stg.CMS_COLLATERAL_ID = "
				+ "( SELECT trx.STAGING_REFERENCE_ID FROM TRANSACTION trx "
				+ "WHERE trx.TRANSACTION_TYPE = 'COL' AND trx.REFERENCE_ID = ? ) ";

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			if (secRv != null) {
				dbUtil.setBigDecimal(1, secRv.getAmountAsBigDecimal());
			}
			else {
				dbUtil.setString(1, null);
			}

			dbUtil.setLong(2, secId);
			dbUtil.executeUpdate();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			finalize(dbUtil, null);
		}
	}

	public void persistAccountRVInfo(List accountInfo) throws Exception {
		DBUtil dbUtil = null;
		String query = "UPDATE SCI_LSP_SYS_XREF SET ACCT_RV = (COALESCE(ACCT_RV, 0) + CAST(? AS DECIMAL(20,2))), ACCT_RV_CCY = ? WHERE CMS_LSP_SYS_XREF_ID = ?";

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			for (int i = 0; i < accountInfo.size(); i++) {
				AccountRVDetail curRVDtl = (AccountRVDetail) (accountInfo.get(i));
				Amount realisableAmt = curRVDtl.getRealizableValue();

				if (realisableAmt == null) {
					dbUtil.setString(1, null);
					dbUtil.setString(2, null);
				}
				else {
					dbUtil.setBigDecimal(1, realisableAmt.getAmountAsBigDecimal());
					dbUtil.setString(2, realisableAmt.getCurrencyCode());
				}

				dbUtil.setLong(3, curRVDtl.getAccountId());
				dbUtil.executeUpdate();
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			finalize(dbUtil, null);
		}
	}

	private List getSecurityFsvCmv(String cty, long startIndex, int batchSize) throws Exception {
		PaginationBean pgBean = new PaginationBean(startIndex + 1, startIndex + batchSize);
		PaginationUtil pgUtil = new PaginationUtilFactory().getPaginationUtil(PaginationUtilFactory.DBTYPE_DB2);
		String query = pgUtil.formPagingQuery(getSecurityQueryStr(cty), pgBean);
		DBUtil dbUtil = null;
		ResultSet rs = null;
		List result = new ArrayList();
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			rs = dbUtil.executeQuery();

			while (rs.next()) {
				ICollateral newColl = new OBCollateral();
				newColl.setCollateralID(rs.getLong("CMS_COLLATERAL_ID"));

				String cmv = rs.getString("CMV");
				String cmvCcy = rs.getString("CMV_CURRENCY");
				if ((cmv != null) && (cmvCcy != null)) {
					newColl.setCMV(new Amount(Double.parseDouble(cmv), cmvCcy));
				}

				String fsv = rs.getString("FSV");
				String fsvCcy = rs.getString("FSV_CURRENCY");

				if ((fsv != null) && (fsvCcy != null)) {
					newColl.setFSV(new Amount(Double.parseDouble(fsv), fsvCcy));
				}

				String secCurrency = rs.getString("SCI_ORIG_SECURITY_CURRENCY");
				String netRV = rs.getString("NET_REALIZABLE_VALUE");
				String reserverPrice = rs.getString("RESERVE_PRICE");

				String subTypeCode = rs.getString("SECURITY_SUB_TYPE_ID");
				ICollateralSubType subType = new OBCollateralSubType();
				subType.setTypeCode(subTypeCode.substring(0, 2));
				subType.setSubTypeCode(subTypeCode);
				newColl.setCollateralSubType(subType);

				newColl.setSCICurrencyCode(secCurrency);

				if (netRV != null) {
					Amount netRVAmt = new Amount(UIUtil.mapStringToBigDecimal(netRV), new CurrencyCode(secCurrency));
					newColl.setNetRealisableAmount(netRVAmt);
				}
				if (reserverPrice != null) {
					Amount reserverPriceAmt = new Amount(UIUtil.mapStringToBigDecimal(reserverPrice), new CurrencyCode(
							secCurrency));
					newColl.setReservePrice(reserverPriceAmt);
				}

				result.add(newColl);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			finalize(dbUtil, rs);
		}

		return result;
	}

	private void getReservPriceInfo(List colList) throws Exception {
		DBUtil dbUtil = null;
		ResultSet rs = null;
		String subQ = getRVColIdList(colList);
		if (subQ == null) {
			return;
		}
//		for DB2
		/*String query = "SELECT 365-(days(current date)-days(V1.VALUATION_DATE)) as REMAIN_USEFUL, V1.CMS_COLLATERAL_ID, V1.VALUATION_ID, V1.VALUATION_CURRENCY, V1.RESERVE_PRICE,V1.SOURCE_TYPE "
				+ "FROM CMS_VALUATION V1 "
				+ "WHERE V1.CMS_COLLATERAL_ID IN "
				+ subQ
				+ " AND V1.SOURCE_TYPE IS NOT NULL AND V1.VALUATION_DATE IS NOT NULL "
				+ "ORDER BY V1.CMS_COLLATERAL_ID, V1.VALUATION_DATE DESC, V1.VALUATION_ID DESC ";*/
//		For Oracle
		String query = "SELECT 365-(TO_DATE(CURRENT_DATE,'DD-MM-YYYY')- TO_DATE(CAST(V1.VALUATION_DATE AS DATE),'DD-MM-YYYY')) as REMAIN_USEFUL, V1.CMS_COLLATERAL_ID, V1.VALUATION_ID, V1.VALUATION_CURRENCY, V1.RESERVE_PRICE,V1.SOURCE_TYPE "
				+ "FROM CMS_VALUATION V1 "
				+ "WHERE V1.CMS_COLLATERAL_ID IN "
				+ subQ
				+ " AND V1.SOURCE_TYPE IS NOT NULL AND V1.VALUATION_DATE IS NOT NULL "
				+ "ORDER BY V1.CMS_COLLATERAL_ID, V1.VALUATION_DATE DESC, V1.VALUATION_ID DESC ";
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);

			DefaultLogger.debug(this, "RV - getReservPriceInfo:" + query);

			String prevColId = "";
			Map valMap = new HashMap();
			rs = dbUtil.executeQuery();
			Map tmpValMap = null;
			while (rs.next()) {
				String collId = rs.getString("CMS_COLLATERAL_ID");

				if (!collId.equals(prevColId)) {
					if (!"".equals(prevColId)) {
						// process with previous coll:
						IValuation mVal = (IValuation) tmpValMap.get("M");
						IValuation sVal = (IValuation) tmpValMap.get("S");
						IValuation aVal = (IValuation) tmpValMap.get("A");
						// <0 : more than 1 year expiry period.
						double mUsefulLife = -1;
						if (mVal != null && mVal.getRemainusefullife() != null) {
							mUsefulLife = mVal.getRemainusefullife().doubleValue();
						}
						double sUsefulLife = -1;
						if (sVal != null && sVal.getRemainusefullife() != null) {
							sUsefulLife = sVal.getRemainusefullife().doubleValue();
						}
						IValuation realVal = null;
						if (mUsefulLife >= 0 && sUsefulLife >= 0) {// within one
							// year,take
							// the sys
							// val amt.
							realVal = aVal;
						}
						else if (sUsefulLife > mUsefulLife) {// source val has
							// latest val
							// record.
							realVal = sVal;
						}
						else {
							realVal = mVal;
						}
						if (sUsefulLife != -1 || mUsefulLife != -1) {
							DefaultLogger.debug(this, "Coll: " + prevColId + " -S:" + sUsefulLife + " -M:"
									+ mUsefulLife);
						}
						valMap.put(prevColId, realVal);
					}
					tmpValMap = new HashMap();
					prevColId = collId;
				}
				if (tmpValMap.size() == 3) {
					DefaultLogger.debug(this, "Coll: " + prevColId + " Has 3 types of valuation !");
					continue;
				}

				String sourceType = rs.getString("SOURCE_TYPE");
				if (tmpValMap.get(sourceType) != null) {
					continue;
				}
				IValuation newVal = new OBValuation();
				newVal.setValuationID(Long.parseLong(rs.getString("VALUATION_ID")));
				String reservePrc = rs.getString("RESERVE_PRICE");
				String valCurrency = rs.getString("VALUATION_CURRENCY");
				if ((reservePrc != null) && (valCurrency != null) && (!"-999999999".equals(reservePrc.trim()))) {
					Amount reservePrcAmt = new Amount(Double.parseDouble(reservePrc), valCurrency);
					newVal.setReservePrice(reservePrcAmt);
				}
				newVal.setRemainusefullife(Double.valueOf(rs.getString("REMAIN_USEFUL")));
				newVal.setSourceType(sourceType);
				tmpValMap.put(sourceType, newVal);
			}
			for (int i = 0; i < colList.size(); i++) {
				ICollateral nextCol = (ICollateral) (colList.get(i));
				String colId = String.valueOf(nextCol.getCollateralID());
				IValuation valForCol = (IValuation) (valMap.get(colId));
				nextCol.setValuationIntoCMS(valForCol);
			}

			valMap.clear();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			finalize(dbUtil, rs);
		}
	}

	protected void finalize(DBUtil dbUtil, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (Exception ex) {
			}
		}

		if (dbUtil != null) {
			try {
				dbUtil.close();
			}
			catch (Exception ex) {
			}
		}
	}
}
