/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * DAO for Liquidation.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class LiquidationDAO  extends JdbcDaoSupport {
	private DBUtil dbUtil;

	private static final String SELECT_TRXID = "select TRANSACTION_ID from TRANSACTION";

	// Table names
	public static final String NPL_TABLE = "CMS_NPL";

	// Column values for liquidation table.
	public static final String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";

	public static final String CMS_NPL_ID = "CMS_NPL_ID";

	public static final String NPL_LMT_ID = "NPL_LMT_ID";

	public static final String NPL_BCA_REF_NUM = "NPL_BCA_REF_NUM";

	public static final String DELINQUENT = "DELINQUENT";

	public static final String ACCOUNT_STATUS = "ACCOUNT_STATUS";

	public static final String FACILITY_TYPE = "FACILITY_TYPE";

	public static final String PART_PAYMENT_RECEIVED = "PART_PAYMENT_RECEIVED";

	public static final String DATE_CURRENT_DOUBTFUL = "DATE_CURRENT_DOUBTFUL";

	public static final String DATE_FIRST_DOUBTFUL = "DATE_FIRST_DOUBTFUL";

	public static final String DATE_REGULARISED = "DATE_REGULARISED";

	public static final String SUSPENSE_INTEREST = "SUSPENSE_INTEREST";

	public static final String SUSPENSE_INT_WRITTEN_OFF = "SUSPENSE_INT_WRITTEN_OFF";

	public static final String DATE_JUDGEMENT = "DATE_JUDGEMENT";

	public static final String JUDGEMENT_SUM = "JUDGEMENT_SUM";

	public static final String DATE_WRITE_OFF = "DATE_WRITE_OFF";

	public static final String CIVIL_SUIT_NO = "CIVIL_SUIT_NO";

	public static final String MONTHS_INSTAL_ARREARS = "MONTHS_INSTAL_ARREARS";

	public static final String MONTHS_INTEREST_ARREARS = "MONTHS_INTEREST_ARREARS";

	public static final String DATE_DISPOSED = "DATE_DISPOSED";

	public static final String STATUS = "STATUS";

	public static final String DATE_REPOSSESED = "DATE_REPOSSESED";

	public static final String VERSION_TIME = "VERSION_TIME";

	public static final String NPL_DATE = "NPL_DATE";

	public static final String CAR_CODE = "CAR_CODE";

	public static final String CAR_CODE_STAT_DATE = "CAR_CODE_STAT_DATE";

	public static final String SPECIFIC_PROVISION = "SPECIFIC_PROVISION";

	/**
	 * Default Constructor
	 */
	public LiquidationDAO() {
	}

	/**
	 * Helper method to construct where clause sql based on transaction
	 * reference id and transaction type.
	 * 
	 * @param trxRefID parent transaction id
	 * @param trxType transaction type
	 * @return String representation of transction id
	 */
	private String constructByTrxRefIDSQL(String trxRefID, String trxType) {
		StringBuffer buf = new StringBuffer();
		buf.append(" where ");
		buf.append(ICMSTrxTableConstants.TRXTBL_TRX_REFERENCE_ID);
		buf.append(" = ");
		buf.append(trxRefID);
		buf.append(" and ");
		buf.append(ICMSTrxTableConstants.TRXTBL_TRANSACTION_TYPE);
		buf.append(" = '");
		buf.append(trxType);
		buf.append("'");
		buf.append(" and ");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS);
		buf.append(" <> '");
		buf.append(ICMSConstant.STATE_CANCELLED);
		buf.append("' and ");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS);
		buf.append(" <> '");
		buf.append(ICMSConstant.STATE_CLOSED);
		buf.append("'");
		return buf.toString();
	}

	public String getTrxIDByTrxRefID(String trxRefID, String trxType) throws Exception {
		String sql = SELECT_TRXID + constructByTrxRefIDSQL(trxRefID, trxType);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
//			System.out.println("getTrxIDByTrxRefID sql " + sql);
			ResultSet rs = dbUtil.executeQuery();
			String trxID = (rs.next()) ? rs.getString(ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID) : null;
			rs.close();
			return trxID;
		}
		catch (Exception e) {
			throw new Exception(e.toString());
		}
		finally {
			finalize(dbUtil);
		}
	}

	private String constructByColIDSQL(String colID, String trxType) {
		StringBuffer buf = new StringBuffer();
		buf.append(" where ");
		buf.append(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID);
		buf.append(" = ");
		buf.append(colID);
		buf.append(" and ");
		buf.append(ICMSTrxTableConstants.TRXTBL_TRANSACTION_TYPE);
		buf.append(" = '");
		buf.append(trxType);
		buf.append("'");
		buf.append(" and ");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS);
		buf.append(" <> '");
		buf.append(ICMSConstant.STATE_CANCELLED);
		buf.append("' and ");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS);
		buf.append(" <> '");
		buf.append(ICMSConstant.STATE_CLOSED);
		buf.append("'");
		return buf.toString();
	}

	public String getTrxIDByColID(String colID, String trxType) throws Exception {
		String sql = SELECT_TRXID + constructByColIDSQL(colID, trxType);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
//			System.out.println("getTrxIDByColID sql " + sql);
			ResultSet rs = dbUtil.executeQuery();
			String trxID = (rs.next()) ? rs.getString(ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID) : null;
			rs.close();
			return trxID;
		}
		catch (Exception e) {
			throw new Exception(e.toString());
		}
		finally {
			finalize(dbUtil);
		}
	}

	public Collection getNPLInfo(long collateralID) throws SearchDAOException {
		String sql = constructNPLInfoSQL();
//		System.out.println("getNPLInfo(" + collateralID + ") : " + sql);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, collateralID);
			ResultSet rs = dbUtil.executeQuery();
			return processNPLInfoSet(rs);
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in getting NPL Info ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	public Collection getFacilityTypeInfo(String accountNo) throws SearchDAOException {
		String sql = constructFacilityInfoSQL();
//		System.out.println("getFacilityTypeInfo(" + accountNo + ") : " + sql);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, accountNo);
			ResultSet rs = dbUtil.executeQuery();
			return processFacilityInfoSet(rs);
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in getting Facility Info ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Helper method to construct query for getting liquidation.
	 * 
	 * @return sql query
	 */
	protected String constructNPLInfoSQL() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer
				.append(
						" SELECT npl.CMS_NPL_ID, npl.ACCOUNT_NUMBER, npl.NPL_LMT_ID, npl.NPL_BCA_REF_NUM, npl.DELINQUENT, npl.ACCOUNT_STATUS,npl.FACILITY_TYPE,")
				.append("  npl.PART_PAYMENT_RECEIVED, npl.DATE_CURRENT_DOUBTFUL, ")
				// npl.DATE_FIRST_DOUBTFUL, npl.CIVIL_SUIT_NO
				.append("  npl.DATE_REGULARISED, ")
				// npl.DATE_REPOSSESED, npl.DATE_DISPOSED
				.append(
						"  npl.SUSPENSE_INTEREST,npl.SUSPENSE_INT_WRITTEN_OFF, npl.DATE_JUDGEMENT, npl.JUDGEMENT_SUM,npl.DATE_WRITE_OFF,")
				.append(
						"  npl.MONTHS_INSTAL_ARREARS, npl.MONTHS_INTEREST_ARREARS, npl.STATUS, npl.VERSION_TIME, npl.NPL_DATE, npl.CAR_CODE, ")
				.append("  npl.CAR_CODE_STAT_DATE, npl.SPECIFIC_PROVISION ").append("  FROM " + NPL_TABLE + " npl,")
				.append("          SCI_LSP_SYS_XREF xref,    ").append("          SCI_LSP_LMTS_XREF_MAP map, ").append(
						"          SCI_LSP_APPR_LMTS lmt,     ").append("          CMS_LIMIT_SECURITY_MAP lsm ")
				.append(" where map.CMS_LSP_SYS_XREF_ID = xref.CMS_LSP_SYS_XREF_ID ").append(
						"   and lmt.CMS_LSP_APPR_LMTS_ID = map.CMS_LSP_APPR_LMTS_ID ").append(
						"   and lsm.CMS_LSP_APPR_LMTS_ID = lmt.CMS_LSP_APPR_LMTS_ID ").append(
						"   and lsm.CMS_COLLATERAL_ID = ? ").append(
						"   and npl.ACCOUNT_NUMBER = xref.LSX_EXT_SYS_ACCT_NUM     ").append(
						"   and (npl.SOURCE_ID = xref.LSX_EXT_SYS_CODE_VALUE       ").append(
						"        or npl.SOURCE_ID in (select entry_code            ").append(
						"		                       from common_code_category_entry ").append(
						"							   where category_code = '" + ICMSConstant.SOURCE_SYSTEM_MARS_SP + "'))");

		return strBuffer.toString();
	}

	/**
	 * Helper method to construct query for getting facility.
	 * 
	 * @return sql query
	 */
	protected String constructFacilityInfoSQL() {

		StringBuffer strBuffer = new StringBuffer();

		strBuffer.append(" SELECT lmt.LMT_FAC_TYPE_NUM, lmt.LMT_FAC_TYPE_VALUE ").append("  FROM ").append(
				"  SCI_LSP_SYS_XREF xref,  ").append("  SCI_LSP_LMTS_XREF_MAP map, ")
				.append("  SCI_LSP_APPR_LMTS lmt ").append(
						"  where map.CMS_LSP_SYS_XREF_ID = xref.CMS_LSP_SYS_XREF_ID  ").append(
						"  and lmt.CMS_LSP_APPR_LMTS_ID = map.CMS_LSP_APPR_LMTS_ID ").append(
						"  and xref.LSX_EXT_SYS_ACCT_NUM = ? ");
		return strBuffer.toString();
	}

	/**
	 * Helper method to process the result set of NPL Info.
	 * 
	 * @param rs result set
	 * @return a list of liquidation
	 * @throws java.sql.SQLException on error processing the result set
	 */
	private Collection processNPLInfoSet(ResultSet rs) throws SQLException {
		ArrayList nplColl = new ArrayList();

		while (rs.next()) {
			OBNPLInfo npl = new OBNPLInfo();
			npl.setAccountNo(rs.getString(ACCOUNT_NUMBER));
			npl.setNplID(new Long(rs.getLong(CMS_NPL_ID)));
			npl.setLimitID(rs.getString(NPL_LMT_ID));
			npl.setBcaRefNum(rs.getString(NPL_BCA_REF_NUM));
			npl.setDelinquent(rs.getString(DELINQUENT));
			npl.setAccountStatus(rs.getString(ACCOUNT_STATUS));
			npl.setFacilityType(rs.getString(FACILITY_TYPE));
			npl.setPartPaymentReceived(rs.getLong(PART_PAYMENT_RECEIVED));
			npl.setLatestDateDoubtful(rs.getDate(DATE_CURRENT_DOUBTFUL));
			// npl.setFirstDateDoubtful(rs.getDate(DATE_FIRST_DOUBTFUL));
			npl.setDateNPLRegularised(rs.getDate(DATE_REGULARISED));
			npl.setInterestinSuspense(rs.getLong(SUSPENSE_INTEREST));
			npl.setAmountWrittenOff(rs.getLong(SUSPENSE_INT_WRITTEN_OFF));
			npl.setJudgementDate(rs.getDate(DATE_JUDGEMENT));
			npl.setJudgementSum(rs.getLong(JUDGEMENT_SUM));
			npl.setDateWriteOff(rs.getDate(DATE_WRITE_OFF));
			// npl.setCivilSuitNo(rs.getString(CIVIL_SUIT_NO));
			npl.setMonthsInstalmentsArrears(rs.getLong(MONTHS_INSTAL_ARREARS));
			npl.setMonthsInterestArrears(rs.getLong(MONTHS_INTEREST_ARREARS));
			// npl.setDateVehicleDisposed(rs.getDate(DATE_DISPOSED));
			npl.setStatus(rs.getString(STATUS));
			// npl.setDateRepossesed(rs.getDate(DATE_REPOSSESED));
			npl.setVersionTime(rs.getLong(VERSION_TIME));
			npl.setNplDate(rs.getDate(NPL_DATE));
			npl.setCarCode(rs.getString(CAR_CODE));
			npl.setCarDate(rs.getDate(CAR_CODE_STAT_DATE));
			npl.setSpecProvisionChrgAcc(rs.getLong(SPECIFIC_PROVISION));

			nplColl.add(npl);
		}
		return nplColl;
	}

	/**
	 * Helper method to process the result set of Facility Info.
	 * 
	 * @param rs result set
	 * @return a list of facility
	 * @throws java.sql.SQLException on error processing the result set
	 */
	private Collection processFacilityInfoSet(ResultSet rs) throws SQLException {
		ArrayList facColl = new ArrayList();

		while (rs.next()) {
			ArrayList data = new ArrayList();
			String facilityNo = rs.getString("LMT_FAC_TYPE_NUM");
			String facilityValue = rs.getString("LMT_FAC_TYPE_VALUE");

			data.add(facilityNo);
			data.add(facilityValue);

			facColl.add(data);
		}
		return facColl;
	}

	/**
	 * Helper method to clean up database resources.
	 * 
	 * @param dbUtil database utility object
	 * @throws com.integrosys.base.businfra.search.SearchDAOException error in
	 *         cleaning up DB resources
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

}
