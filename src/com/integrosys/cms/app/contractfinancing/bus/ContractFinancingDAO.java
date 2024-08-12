package com.integrosys.cms.app.contractfinancing.bus;

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
 * @since Mar 21, 2007 Tag: $Name$
 */
public class ContractFinancingDAO implements IContractFinancingDAO {

	private DBUtil dbUtil = null;

	// ==========================================
	// SQL Declaration
	// ==========================================
	private static final String SELECT_CF_SUMMARY = "SELECT LMT.CMS_LSP_LMT_PROFILE_ID, APPR.CMS_LSP_APPR_LMTS_ID, APPR.LMT_ID,"
			+ "       (Select CCE.ENTRY_NAME"
			+ "		 from COMMON_CODE_CATEGORY_ENTRY CCE"
			+ "		 WHERE APPR.LMT_FAC_TYPE_NUM = CCE.CATEGORY_CODE"
			+ "        AND APPR.LMT_FAC_TYPE_VALUE = CCE.ENTRY_CODE) PRODUCT_DESCRIPTION,"
			+ " CF.CONTRACT_ID, CF.CONTRACT_NUMBER, "
			+ " CF.CONTRACT_DATE, CF.EXPIRY_DATE, CF.EXTENDED_DATE, "
			+ " CF.CONTRACTED_CURRENCY, CF.CONTRACTED_AMOUNT, CF.FINANCE_PERCENT "
			+ " FROM SCI_LSP_LMT_PROFILE LMT, SCI_LSP_APPR_LMTS APPR "
			+ " LEFT OUTER JOIN "
			+ " (select CF.CMS_LSP_APPR_LMTS_ID, CF.CONTRACT_ID, CF.CONTRACT_NUMBER, "
			+ "  CF.CONTRACT_DATE, CF.EXPIRY_DATE, CF.EXTENDED_DATE, "
			+ "  CF.CONTRACTED_CURRENCY, CF.CONTRACTED_AMOUNT, CF.FINANCE_PERCENT "
			+ "  from CMS_CONTRACT_FINANCING CF, TRANSACTION TRX "
			+ "  WHERE CF.CONTRACT_ID = TRX.REFERENCE_ID "
			+ "  AND TRX.STATUS != 'CLOSED' "
			+ "  AND TRX.TRANSACTION_TYPE = 'CONTRACT_FINANCE') CF "
			+ " ON APPR.CMS_LSP_APPR_LMTS_ID = CF.CMS_LSP_APPR_LMTS_ID "
			+ " WHERE LMT.CMS_LSP_LMT_PROFILE_ID = APPR.CMS_LIMIT_PROFILE_ID"
			+ " AND LMT.CMS_BCA_STATUS = 'ACTIVE'"
			+ " AND APPR.CMS_LIMIT_STATUS = 'ACTIVE'"
			+ " AND APPR.LOAN_TYPE = 'CF'"
			+ " AND LMT.CMS_LSP_LMT_PROFILE_ID = ?" + " ORDER BY LMT_ID, CONTRACT_ID";

	private static final String SELECT_CF_LIMITDETAILS = "SELECT APPR.LMT_ID, CCE.ENTRY_NAME PRODUCT_DESCRIPTION"
			+ "  FROM (select LMT_ID, LMT_FAC_TYPE_NUM, LMT_FAC_TYPE_VALUE" + "          FROM SCI_LSP_APPR_LMTS"
			+ "		  where CMS_LSP_APPR_LMTS_ID = ? ) APPR" + "  LEFT OUTER JOIN COMMON_CODE_CATEGORY_ENTRY CCE"
			+ "    ON CCE.CATEGORY_CODE = APPR.LMT_FAC_TYPE_NUM" + "   AND CCE.ENTRY_CODE = APPR.LMT_FAC_TYPE_VALUE";

	// ==========================================
	// Constructor
	// ==========================================
	/**
	 * Default Constructor
	 */
	public ContractFinancingDAO() {
	}

	// ==========================================
	// Start of DAO Methods
	// ==========================================
	/**
	 * Get the list of contract finance summary info.
	 * @param limitProfileID of long type
	 * @return IContractFinancingSummary[] - the list of contract finance
	 *         summary
	 * @throws SearchDAOException on errors
	 */
	public IContractFinancingSummary[] getContractFinancingSummaryList(long limitProfileID) throws SearchDAOException {

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SELECT_CF_SUMMARY);
			dbUtil.setLong(1, limitProfileID);
			ResultSet rs = dbUtil.executeQuery();

			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				DefaultLogger.debug(this, "inside rs");
				OBContractFinancingSummary summary = new OBContractFinancingSummary();
				summary.setLimitProfileID(rs.getLong(CFTBL_LIMIT_PROFILE_ID));
				summary.setLimitID(rs.getLong(CFTBL_LIMIT_ID));
				summary.setSourceLimit(rs.getString(CFTBL_SOURCE_LIMIT));
				summary.setProductDescription(rs.getString(CFTBL_PRODUCT_DESCRIPTION));
				summary.setContractID(checkForInvalidValue(rs.getLong(CFTBL_CONTRACT_ID)));
				summary.setContractNumber(rs.getString(CFTBL_CONTRACT_NUMBER));
				summary.setContractDate(rs.getDate(CFTBL_CONTRACT_DATE));
				summary.setExpiryDate(rs.getDate(CFTBL_EXPIRY_DATE));
				summary.setExtendedDate(rs.getDate(CFTBL_EXTENDED_DATE));
				Amount contractAmount = new Amount(rs.getDouble(CFTBL_CONTRACT_AMOUNT), rs
						.getString(CFTBL_CONTRACT_CURRENCY));
				summary.setContractAmount(contractAmount);
				summary.setFinancePercent(rs.getFloat(CFTBL_FINANCE_PERCENT));
				resultList.add(summary);
			}
			rs.close();
			return (IContractFinancingSummary[]) resultList.toArray(new IContractFinancingSummary[0]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getContractFinancingSummaryList", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getContractFinancingSummaryList", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getContractFinancingSummaryList", ex);
			}
		}
	}

	public HashMap getLimitDetailsByLimitID(long limitPk) throws SearchDAOException {
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SELECT_CF_LIMITDETAILS);
			dbUtil.setLong(1, limitPk);
			ResultSet rs = dbUtil.executeQuery();

			HashMap hm = new HashMap();
			while (rs.next()) {
				hm.put("sourceLimit", rs.getString(CFTBL_SOURCE_LIMIT));
				hm.put("productDescription", rs.getString(CFTBL_PRODUCT_DESCRIPTION));
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
