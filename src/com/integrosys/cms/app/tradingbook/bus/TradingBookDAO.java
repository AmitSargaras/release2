/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.DB2DateConverter;

/**
 * DAO for tradingbook.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class TradingBookDAO implements ITradingBookDAO {
	private DBUtil dbUtil;

	private static MessageFormat SELECT_CP_AGREEMENT_BY_LP_AGREE_ID = new MessageFormat(
			"SELECT M.LMP_LE_ID LE_ID, M.LMP_LONG_NAME CUST_NAME, "
					+ "M.LMP_REL_START_DATE REL_DATE, M.LMP_INC_CNTRY_ISO_CODE CNTRY_CODE, "
					+ "L.CMS_LSP_LMT_PROFILE_ID LMT_PROFILE_ID, "
					+ "A.AGREEMENT_ID AGREE_ID, A.AGREEMENT_TYPE AGREE_TYPE, "
					+ "A.BASE_CURRENCY BASE_CURR,  A.CP_RATING RATING , "
					+ "A.MIN_TRANSFER_CURRENCY MIN_CURR, A.MIN_TRANSFER_AMT MIN_AMT, "
					+ "A.CLOSING_CASH_INT_DATE CASH_INT_DATE, A.CLOSING_CASH_INT CASH_INT, A.AGREE_INT_RATE_TYPE, "
					+ "L.CMS_BCA_STATUS CMS_BCA_STATUS "
					+ "FROM SCI_LE_MAIN_PROFILE M, SCI_LSP_LMT_PROFILE L, CMS_TRADING_AGREEMENT A " + "WHERE "
					+
					// "STATUS <> '"+ICMSConstant.STATE_DELETED+"' AND "+
					"M.LMP_LE_ID = L.LLP_LE_ID " + "AND L.CMS_LSP_LMT_PROFILE_ID = A.CMS_LSP_LMT_PROFILE_ID "
					+ "AND A.CMS_LSP_LMT_PROFILE_ID = {0} " + "AND A.AGREEMENT_ID = {1} ");

	private static MessageFormat SELECT_CP_AGREEMENT_BY_AGREE_ID = new MessageFormat(
			"SELECT M.LMP_LE_ID LE_ID, M.LMP_LONG_NAME CUST_NAME, "
					+ "M.LMP_REL_START_DATE REL_DATE, M.LMP_INC_CNTRY_ISO_CODE CNTRY_CODE, "
					+ "L.CMS_LSP_LMT_PROFILE_ID LMT_PROFILE_ID, "
					+ "A.AGREEMENT_ID AGREE_ID, A.AGREEMENT_TYPE AGREE_TYPE, "
					+ "A.BASE_CURRENCY BASE_CURR,  A.CP_RATING RATING , "
					+ "A.MIN_TRANSFER_CURRENCY MIN_CURR, A.MIN_TRANSFER_AMT MIN_AMT, "
					+ "A.CLOSING_CASH_INT_DATE CASH_INT_DATE, A.CLOSING_CASH_INT CASH_INT, A.AGREE_INT_RATE_TYPE, "
					+ "L.CMS_BCA_STATUS CMS_BCA_STATUS "
					+ "FROM SCI_LE_MAIN_PROFILE M, SCI_LSP_LMT_PROFILE L, CMS_TRADING_AGREEMENT A " + "WHERE "
					+ "M.LMP_LE_ID = L.LLP_LE_ID " + "AND L.CMS_LSP_LMT_PROFILE_ID = A.CMS_LSP_LMT_PROFILE_ID "
					+ "AND A.AGREEMENT_ID = {0} ");

	private static MessageFormat SELECT_DEAL_VAL_BY_AGREEMENT = new MessageFormat("SELECT V.* "
			+ "FROM CMS_TB_DEAL D, CMS_TB_DEAL_VAL V " + "WHERE " + "D.STATUS <> \''" + ICMSConstant.STATE_DELETED
			+ "\'' AND " + "V.STATUS <> \''" + ICMSConstant.STATE_DELETED + "\'' AND "
			+ "D.CMS_DEAL_ID = V.CMS_DEAL_ID " + "AND D.AGREEMENT_ID = {0} ");

	private static MessageFormat SELECT_CASH_MARGIN = new MessageFormat("SELECT * FROM CMS_CASH_MARGIN " + " WHERE "
			+ "STATUS <> \''" + ICMSConstant.STATE_DELETED + "\'' AND "
			+ "AGREEMENT_ID = {0} AND TRUNC( TRX_DATE ) >= TRUNC ( TIMESTAMP ( \''{1}\'' ) ) AND "
			+ " TRUNC( TRX_DATE ) <= TRUNC ( TIMESTAMP ( \''{2}\'' ) ) " + " ORDER BY TRX_DATE");

	/**
	 * Default Constructor
	 */
	public TradingBookDAO() {
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookDAO#getCounterPartyAgreementSummary
	 */
	public ICPAgreementDetail getCounterPartyAgreementSummary(long limitProfileID, long agreementID)
			throws SearchDAOException {
		String sql = constructCounterPartyAgreementSummarySQL(limitProfileID, agreementID);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processCounterPartyAgreementSummaryResultSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getCounterPartyAgreementSummary: ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookDAO#getCounterPartyAgreementSummary
	 */
	public ICPAgreementDetail getCounterPartyAgreementSummary(long agreementID) throws SearchDAOException {
		String sql = constructCounterPartyAgreementSummarySQL(agreementID);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processCounterPartyAgreementSummaryResultSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getCounterPartyAgreementSummary: ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookDAO#getDealValuationByAgreementID
	 */
	public IDealValuation[] getDealValuationByAgreementID(long agreementID) throws SearchDAOException {
		String sql = constructDealValuationByAgreementIDSQL(agreementID);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processDealValuationByAgreementIDResultSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getDealValuationByAgreementID: ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookDAO#getCashMarginByAgreementID
	 */
	public ICashMargin[] getCashMarginByAgreementID(long agreementID) throws SearchDAOException {
		String sql = constructCashMarginSQL(agreementID);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processCashMarginResultSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getCashMarginByAgreementID: ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	protected String constructCounterPartyAgreementSummarySQL(long limitProfileID, long agreementID) {
		String[] params = new String[] { String.valueOf(limitProfileID), String.valueOf(agreementID) };
		String qryStr = SELECT_CP_AGREEMENT_BY_LP_AGREE_ID.format(params);
		DefaultLogger.debug(this, "constructCounterPartyAgreementSummarySQL=" + qryStr);
		return qryStr;
	}

	protected String constructCounterPartyAgreementSummarySQL(long agreementID) {
		String[] params = new String[] { String.valueOf(agreementID) };
		String qryStr = SELECT_CP_AGREEMENT_BY_AGREE_ID.format(params);
		DefaultLogger.debug(this, "constructCounterPartyAgreementSummarySQL=" + qryStr);
		return qryStr;
	}

	protected String constructDealValuationByAgreementIDSQL(long agreementID) {
		String[] params = new String[] { String.valueOf(agreementID) };
		String qryStr = SELECT_DEAL_VAL_BY_AGREEMENT.format(params);
		DefaultLogger.debug(this, "constructDealValuationByAgreementIDSQL=" + qryStr);
		return qryStr;
	}

	protected String constructCashMarginSQL(long agreementID) {
		Calendar cal = Calendar.getInstance();

		cal.set(cal.get(cal.YEAR), cal.get(cal.MONTH), 1);
		Date startDate = cal.getTime();
		String startDateStr = DB2DateConverter.formatDate(startDate);
		cal.set(cal.get(cal.YEAR), cal.get(cal.MONTH) + 1, 0);
		Date endDate = cal.getTime();
		String endDateStr = DB2DateConverter.formatDate(endDate);

		String[] params = new String[] { String.valueOf(agreementID), startDateStr, endDateStr };
		String qryStr = SELECT_CASH_MARGIN.format(params);
		DefaultLogger.debug(this, "constructCashMarginSQL=" + qryStr);
		return qryStr;
	}

	private ICPAgreementDetail processCounterPartyAgreementSummaryResultSet(ResultSet rs) throws SQLException {
		ArrayList arrList = new ArrayList();
		ICPAgreementDetail agreemt = new OBCPAgreementDetail();

		if (rs.next()) {
			agreemt.setLEID(rs.getString("LE_ID"));
			agreemt.setCustomerName(rs.getString("CUST_NAME"));
			agreemt.setRelationshipStartDate(rs.getDate("REL_DATE"));
			agreemt.setIncorpCountry(rs.getString("CNTRY_CODE"));
			agreemt.setLimitProfileID(rs.getLong("LMT_PROFILE_ID"));

			agreemt.setAgreementID(rs.getLong("AGREE_ID"));
			agreemt.setAgreementType(rs.getString("AGREE_TYPE"));
			agreemt.setBaseCurrency(rs.getString("BASE_CURR"));
			agreemt.setIntRateType(rs.getString("AGREE_INT_RATE_TYPE"));
			String minCurr = rs.getString("MIN_CURR");
			BigDecimal minAmt = rs.getBigDecimal("MIN_AMT");
			agreemt.setMinTransferAmount(getAmount(minCurr, minAmt));

			agreemt.setCPRating(rs.getString("RATING"));
			agreemt.setClosingCashInterestDate(rs.getDate("CASH_INT_DATE"));

			BigDecimal intAmt = rs.getBigDecimal("CASH_INT");
			if (intAmt != null) {
				agreemt.setClosingCashInterest(intAmt);
			}
			agreemt.setBCAStatus(rs.getString("CMS_BCA_STATUS"));
		}
		return agreemt;
	}

	private IDealValuation[] processDealValuationByAgreementIDResultSet(ResultSet rs) throws SQLException {
		ArrayList arrList = new ArrayList();

		while (rs.next()) {
			IDealValuation cashMargin = new OBDealValuation();
			cashMargin.setDealValuationID(rs.getLong("CMS_DEAL_VAL_ID"));
			cashMargin.setCMSDealID(rs.getLong("CMS_DEAL_ID"));
			String minCurr = rs.getString("MARKET_VALUE_CURRENCY");
			BigDecimal minAmt = rs.getBigDecimal("MARKET_VALUE");
			cashMargin.setMarketValue(getAmount(minCurr, minAmt));
			cashMargin.setGroupID(rs.getLong("GROUP_ID"));
			cashMargin.setStatus(rs.getString("STATUS"));
			cashMargin.setVersionTime(rs.getLong("VERSION_TIME"));

			arrList.add(cashMargin);
		}
		return (OBDealValuation[]) arrList.toArray(new OBDealValuation[0]);
	}

	private ICashMargin[] processCashMarginResultSet(ResultSet rs) throws SQLException {
		ArrayList arrList = new ArrayList();

		while (rs.next()) {
			ICashMargin cashMargin = new OBCashMargin();

			cashMargin.setCashMarginID(rs.getLong("CASH_MARGIN_ID"));
			cashMargin.setAgreementID(rs.getLong("AGREEMENT_ID"));
			cashMargin.setTrxDate(rs.getDate("TRX_DATE"));
			String minCurr = rs.getString("NAP_CURRENCY");
			BigDecimal minAmt = rs.getBigDecimal("NAP");
			cashMargin.setNAPAmount(getAmount(minCurr, minAmt));

			String ind = rs.getString("NAP_SIGN_ADD");
			if ((ind != null) && ind.trim().equals("Y")) {
				cashMargin.setNAPSignAddInd(true);
			}
			else {
				cashMargin.setNAPSignAddInd(false);
			}

			BigDecimal intAmt = rs.getBigDecimal("CASH_INTEREST");
			if (intAmt != null) {
				cashMargin.setCashInterest(intAmt);
			}
			cashMargin.setGroupID(rs.getLong("GROUP_ID"));
			cashMargin.setStatus(rs.getString("STATUS"));
			cashMargin.setVersionTime(rs.getLong("VERSION_TIME"));

			arrList.add(cashMargin);
		}
		return (OBCashMargin[]) arrList.toArray(new OBCashMargin[0]);
	}

	private Amount getAmount(String ccy, BigDecimal amt) {

		if ((amt != null) && (ccy != null)) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * Utility method to check if a string value is null or empty.
	 * 
	 * @param aValue string to be checked
	 * @return boolean true if empty and false otherwise
	 */
	private boolean isEmpty(String aValue) {
		if ((aValue != null) && (aValue.trim().length() > 0)) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Helper method to clean up database resources.
	 * 
	 * @param dbUtil database utility object
	 * @throws SearchDAOException error in cleaning up DB resources
	 */
	private void finalize(DBUtil dbUtil) throws SearchDAOException {
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in cleaning up DB resources.");
		}
	}

	// inner class
	private class DAPFilterException extends Exception {
		public DAPFilterException(String msg) {
			super(msg);
		}
	}

}
