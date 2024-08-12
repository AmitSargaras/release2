package com.integrosys.cms.app.bridgingloan.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 23, 2007 Tag: $Name$
 */
public class BridgingLoanDAO implements IBridgingLoanDAO {

	private DBUtil dbUtil = null;

	// ==========================================
	// SQL Declaration
	// ==========================================
	private static final String SELECT_BL_SUMMARY = " SELECT LMT.CMS_LSP_LMT_PROFILE_ID, APPR.CMS_LSP_APPR_LMTS_ID, APPR.LMT_ID, "
			+ "       (select CCE.ENTRY_NAME "
			+ "		 from COMMON_CODE_CATEGORY_ENTRY CCE "
			+ "		 where APPR.LMT_FAC_TYPE_NUM = CCE.CATEGORY_CODE "
			+ "        AND APPR.LMT_FAC_TYPE_VALUE = CCE.ENTRY_CODE) PRODUCT_DESCRIPTION, "
			+

			" BL.PROJECT_ID, BL.PROJECT_NUMBER, "
			+ " BL.CONTRACT_DATE, BL.ACTUAL_START_DATE, BL.ACTUAL_COMPLETION_DATE, "
			+ " BL.CONTRACTED_CURRENCY, BL.CONTRACTED_AMOUNT, BL.FINANCE_PERCENT "
			+ " FROM SCI_LSP_LMT_PROFILE LMT, SCI_LSP_APPR_LMTS APPR "
			+ " LEFT OUTER JOIN "
			+

			" (select BL.CMS_LSP_APPR_LMTS_ID, BL.PROJECT_ID, BL.PROJECT_NUMBER, "
			+ "       BL.CONTRACT_DATE, BL.ACTUAL_START_DATE, BL.ACTUAL_COMPLETION_DATE, "
			+ "       BL.CONTRACTED_CURRENCY, BL.CONTRACTED_AMOUNT, BL.FINANCE_PERCENT "
			+ "  from CMS_BRIDGING_LOAN BL, TRANSACTION TRX "
			+ "  where BL.PROJECT_ID = TRX.REFERENCE_ID "
			+ "  AND TRX.STATUS != 'CLOSE' "
			+ "  AND TRX.TRANSACTION_TYPE = 'BRIDGING_LOAN') BL "
			+

			" ON APPR.CMS_LSP_APPR_LMTS_ID = BL.CMS_LSP_APPR_LMTS_ID "
			+ " WHERE LMT.CMS_LSP_LMT_PROFILE_ID = APPR.CMS_LIMIT_PROFILE_ID "
			+ " AND LMT.CMS_BCA_STATUS = 'ACTIVE' "
			+ " AND APPR.CMS_LIMIT_STATUS = 'ACTIVE' "
			+ " AND APPR.LOAN_TYPE = 'BL' "
			+ " AND LMT.CMS_LSP_LMT_PROFILE_ID = ? " + " ORDER BY LMT_ID, PROJECT_ID";

	private static final String SELECT_BL_LIMITDETAILS = "SELECT APPR.LMT_ID, CCE.ENTRY_NAME PRODUCT_DESCRIPTION "
			+ "  FROM (select LMT_ID, LMT_FAC_TYPE_NUM, LMT_FAC_TYPE_VALUE " + "        from SCI_LSP_APPR_LMTS "
			+ "		 where CMS_LSP_APPR_LMTS_ID = ? ) APPR " + "  LEFT OUTER JOIN COMMON_CODE_CATEGORY_ENTRY CCE "
			+ "  ON CCE.CATEGORY_CODE = APPR.LMT_FAC_TYPE_NUM " + "  AND CCE.ENTRY_CODE = APPR.LMT_FAC_TYPE_VALUE";

	// ==========================================
	// Constructor
	// ==========================================
	/**
	 * Default Constructor
	 */
	public BridgingLoanDAO() {
	}

	// ==========================================
	// Start of DAO Methods
	// ==========================================
	/**
	 * Get the list of bridging loan summary info.
	 * @param limitProfileID of long type
	 * @return IBridgingLoanSummary[] - the list of bridging loan summary
	 * @throws SearchDAOException on errors
	 */
	public IBridgingLoanSummary[] getBridgingLoanSummaryList(long limitProfileID) throws SearchDAOException {
		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, SELECT_BL_SUMMARY);
			dbUtil.setSQL(SELECT_BL_SUMMARY);
			dbUtil.setLong(1, limitProfileID);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();

			while (rs.next()) {
				OBBridgingLoanSummary summary = new OBBridgingLoanSummary();
				summary.setLimitProfileID(rs.getLong(BLTBL_LIMIT_PROFILE_ID));
				summary.setLimitID(rs.getLong(BLTBL_LIMIT_ID));
				summary.setSourceLimit(rs.getString(BLTBL_SOURCE_LIMIT));
				summary.setProductDescription(rs.getString(BLTBL_PRODUCT_DESCRIPTION));
				summary.setProjectID(checkForInvalidValue(rs.getLong(BLTBL_PROJECT_ID)));
				summary.setProjectNumber(rs.getString(BLTBL_PROJECT_NUMBER));
				// summary.setOrigCountry(rs.getString(BLTBL_ORIG_COUNTRY));
				summary.setContractDate(rs.getDate(BLTBL_CONTRACT_DATE));
				summary.setActualStartDate(rs.getDate(BLTBL_ACT_START_DATE));
				summary.setActualCompletionDate(rs.getDate(BLTBL_ACT_COMPLETION_DATE));
				Amount contractAmount = new Amount(rs.getDouble(BLTBL_CONTRACT_AMOUNT), rs
						.getString(BLTBL_CONTRACT_CURRENCY));
				summary.setContractAmount(contractAmount);
				summary.setFinancePercent(rs.getFloat(BLTBL_FINANCE_PERCENT));
				resultList.add(summary);
			}
			rs.close();
			return (IBridgingLoanSummary[]) resultList.toArray(new IBridgingLoanSummary[0]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getBridgingLoanSummaryList", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getBridgingLoanSummaryList", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getBridgingLoanSummaryList", ex);
			}
		}
	}

	public HashMap getLimitDetailsByLimitID(long limitPk) throws SearchDAOException {
		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, SELECT_BL_LIMITDETAILS);
			dbUtil.setSQL(SELECT_BL_LIMITDETAILS);
			dbUtil.setLong(1, limitPk);
			ResultSet rs = dbUtil.executeQuery();

			HashMap hm = new HashMap();
			while (rs.next()) {
				hm.put("sourceLimit", rs.getString(BLTBL_SOURCE_LIMIT));
				hm.put("productDescription", rs.getString(BLTBL_PRODUCT_DESCRIPTION));
			}
			rs.close();
			return hm;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getLimitDetailsByLimitID", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getLimitDetailsByLimitID", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getLimitDetailsByLimitID", ex);
			}
		}
	}

	private static long checkForInvalidValue(long value) {
		if (value == 0) {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
		return value;
	}
}
