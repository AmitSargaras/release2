package com.integrosys.cms.host.stp.bus;

import com.integrosys.base.techinfra.dbsupport.ISequenceFormatter;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.ui.limit.CategoryCodeConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: Jerlin Ong Date: Sep 17, 2008 Time: 12:19:54
 * PM To change this template use File | Settings | File Templates.
 */
public class StpTransJdbcImpl extends JdbcDaoSupport implements IStpTransJdbc {
	//FOR DB2
//	public static final String GET_UID_SEQ_SQL = "VALUES nextval FOR " + IStpConstants.SEQUENCE_STP_TRANS_UID;
//	public static final String GET_REF_SEQ_SQL = "VALUES nextval FOR " + IStpConstants.SEQUENCE_STP_TRANS_REF;
	//FOR ORACLE
	public static final String GET_UID_SEQ_SQL = "SELECT " + IStpConstants.SEQUENCE_STP_TRANS_UID+".NEXTVAL FROM DUAL";
	public static final String GET_REF_SEQ_SQL = "SELECT " + IStpConstants.SEQUENCE_STP_TRANS_REF+".NEXTVAL FROM DUAL";

	private ISequenceFormatter sequenceFormatter;

	public ISequenceFormatter getSequenceFormatter() {
		return sequenceFormatter;
	}

	public void setSequenceFormatter(ISequenceFormatter sequenceFormatter) {
		this.sequenceFormatter = sequenceFormatter;
	}

	/**
	 * get Insurance Policy sequence information from table
	 * 
	 * @param insPolicyID long
	 */
	public List getInsPolicySequenceByInsPolicyID(Long insPolicyID) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT POLICY_SEQ_NO FROM CMS_INSURANCE_POLICY WHERE INSURANCE_POLICY_ID = ?");

		return getJdbcTemplate().query(sqlBuf.toString(), new Object[] { insPolicyID }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("POLICY_SEQ_NO");
			}
		});
	}

	/**
	 * get Collateral Facility Linkage information from table
	 * 
	 * @param chargeId Long
	 */
	public List getColLimitMapByID(Long chargeId) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT llp.LLP_BCA_REF_NUM, lmts.LMT_FAC_CODE, lmts.LMT_FAC_SEQ, llp.APPLICATION_TYPE ");
		sqlBuf.append(" FROM CMS_LIMIT_SECURITY_MAP lsp, SCI_LSP_APPR_LMTS lmts, SCI_LSP_LMT_PROFILE llp ");
		sqlBuf.append(" WHERE lsp.CMS_LSP_APPR_LMTS_ID = lmts.CMS_LSP_APPR_LMTS_ID ");
		sqlBuf.append(" AND lmts.CMS_LIMIT_PROFILE_ID = llp.CMS_LSP_LMT_PROFILE_ID ");
		sqlBuf.append(" AND lsp.CHARGE_ID = ? ");

		return getJdbcTemplate().query(sqlBuf.toString(), new Object[] { chargeId }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				List aList = new ArrayList();
				aList.add(rs.getString("LLP_BCA_REF_NUM"));
				aList.add(rs.getString("LMT_FAC_CODE"));
				aList.add(String.valueOf(rs.getLong("LMT_FAC_SEQ")));
				aList.add(rs.getString("APPLICATION_TYPE"));
				return aList;
			}
		});
	}

	/**
	 * get Facility Charge Linkage information from table
	 * 
	 * @param lmtChargeId Long
	 */
	public List getLimitChargeMapByID(Long lmtChargeId) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf
				.append("SELECT llp.LLP_BCA_REF_NUM, lmts.LMT_FAC_CODE, lmts.LMT_FAC_SEQ, llp.APPLICATION_TYPE, ccd.SECURITY_RANK ");
		sqlBuf
				.append("FROM CMS_LIMIT_CHARGE_MAP lcp, SCI_LSP_APPR_LMTS lmts, SCI_LSP_LMT_PROFILE llp, CMS_CHARGE_DETAIL ccd ");
		sqlBuf.append("WHERE lcp.CMS_LSP_APPR_LMTS_ID = lmts.CMS_LSP_APPR_LMTS_ID ");
		sqlBuf.append("AND lcp.CHARGE_DETAIL_ID = ccd.CHARGE_DETAIL_ID ");
		sqlBuf.append("AND lmts.CMS_LIMIT_PROFILE_ID = llp.CMS_LSP_LMT_PROFILE_ID ");
		sqlBuf.append("AND lcp.LIMIT_CHARGE_MAP_ID = ? ");

		return getJdbcTemplate().query(sqlBuf.toString(), new Object[] { lmtChargeId }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				List aList = new ArrayList();
				aList.add(rs.getString("LLP_BCA_REF_NUM"));
				aList.add(rs.getString("LMT_FAC_CODE"));
				aList.add(Long.toString(rs.getLong("LMT_FAC_SEQ")));
				aList.add(rs.getString("APPLICATION_TYPE"));
				aList.add(rs.getString("SECURITY_RANK"));
				return aList;
			}
		});
	}

	/**
	 * get facility officer sequence from table
	 * 
	 * @param id Long
	 */
	public List getFacilityOfficerByID(Long id) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT HOST_SEQ_NUM FROM CMS_FAC_OFFICER WHERE CMS_REF_ID = ?");

		return getJdbcTemplate().query(sqlBuf.toString(), new Object[] { id }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("HOST_SEQ_NUM");
			}
		});
	}

	/**
	 * get facility multi tier finance sequence from table
	 * 
	 * @param id Long
	 */
	public List getFacilityMultiTierFinanceByID(Long id) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT TIER_SEQ_NO FROM CMS_FAC_MULTI_TIER_FINANCING WHERE CMS_REF_ID = ?");

		return getJdbcTemplate().query(sqlBuf.toString(), new Object[] { id }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("TIER_SEQ_NO");
			}
		});
	}

	/**
	 * get facility relationship cif number from table
	 * 
	 * @param id Long
	 */
	public List getFacilityRelCifNumberByID(Long id) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT CIF_NUMBER FROM CMS_FAC_RELATIONSHIP WHERE CMS_REF_ID = ?");

		return getJdbcTemplate().query(sqlBuf.toString(), new Object[] { id }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("CIF_NUMBER");
			}
		});
	}

	/**
	 * Method to return Stp transaction unique ID
	 * 
	 * @return Trx UID
	 */
	public String getSeqNum() {
		long seq = getJdbcTemplate().queryForLong(GET_UID_SEQ_SQL);
		String seqString = "";

		try {
			seqString = getSequenceFormatter().formatSeq(String.valueOf(seq));
		}
		catch (Exception e) {
			if (getSequenceFormatter() == null) {
				IllegalStateException ise = new IllegalStateException(
						"sequence formatter required is null, please provide.");
				ise.initCause(e);
				throw ise;
			}

			logger.warn("encounter exception when doing formatting using formatter ["
					+ sequenceFormatter.getClass().getName() + "]", e);

			IllegalStateException ise = new IllegalStateException("not able to format using formatter ["
					+ sequenceFormatter.getClass().getName() + "]");
			ise.initCause(e);
			throw ise;
		}
		return seqString;
	}

	public String getTrxRefNum() {
		long seq = getJdbcTemplate().queryForLong(GET_REF_SEQ_SQL);
		String seqString = String.valueOf(seq);
		return seqString;
	}

	/**
	 * get Total Amount Sold from liquitation table
	 * 
	 * @param cmsCollateralId Long
	 */
	public List getTotalAmountSold(Long cmsCollateralId) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT SUM(TOTAL_AMT_RECOVER) AS SUM_AMT_RECOVER FROM CMS_SEC_RECOVERY_INCOME ");
		sqlBuf.append(" WHERE STATUS <> 'D' AND RECOVERY_ID IN ");
		sqlBuf.append(" (SELECT RECOVERY_ID FROM CMS_SEC_RECOVERY WHERE STATUS <> 'D' ");
		sqlBuf.append(" AND RECOVERY_TYPE = 'COL' AND LIQUIDATION_ID IN ");
		sqlBuf.append(" (SELECT LIQUIDATION_ID FROM CMS_SEC_LIQUIDATION WHERE CMS_COLLATERAL_ID = ?)) ");

		return getJdbcTemplate().query(sqlBuf.toString(), new Object[] { cmsCollateralId }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return String.valueOf(rs.getDouble("SUM_AMT_RECOVER"));
			}
		});
	}

	/**
	 * get Stp Biz Error Code From STP_COMMON_ERROR_CODE
	 */
	public Map getStpBizErrorCode() {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf
				.append("SELECT ERROR_CODE, ERROR_DESCRIPTION FROM STP_ERROR_CODE_MAP WHERE STATUS = 'ACTIVE' AND ERROR_TYPE = 'BIZ'");

		return (Map) getJdbcTemplate().query(sqlBuf.toString(), new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map StpBizErrorCodeMap = new HashMap();
				while (rs.next()) {
					String error_code = rs.getString("ERROR_CODE");
					String error_desc = rs.getString("ERROR_DESCRIPTION");
					if (StringUtils.isNotBlank(error_code)) {
						StpBizErrorCodeMap.put(error_code, error_desc);
					}
				}
				return StpBizErrorCodeMap;
			}
		});

	}

	/**
	 * get Stp Error Message Description and transaction type From
	 * STP_TRANS_ERROR, STP_TRANS and STP_MASTER_TRANS
	 * 
	 * @param transactionId String
	 */
	public List getErrorMessage(String transactionId) {
		// For Db2
		/*StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT ERR.ERROR_CODE, ERR.ERROR_DESC, ST.TRX_TYPE ");
		sqlBuf.append(" FROM STP_TRANS_ERROR ERR, STP_TRANS ST, STP_MASTER_TRANS MT ");
		sqlBuf.append(" WHERE ERR.TRX_UID = ST.TRX_UID ");
		sqlBuf.append(" AND MT.STATUS <> 'COMPLETE' ");
		sqlBuf.append(" AND (ST.RESPONSE_CODE <> 'AA' OR ST.RESPONSE_CODE IS NULL) ");
		sqlBuf
				.append(" AND ST.MASTER_TRX_ID = MT.MASTER_TRX_ID AND MT.TRANSACTION_ID = ? order by ERR.ERROR_ID DESC fetch first row only ");
*/
		// For Oracle
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT * FROM ( SELECT ERR.ERROR_CODE, ERR.ERROR_DESC, ST.TRX_TYPE ");
		sqlBuf.append(" FROM STP_TRANS_ERROR ERR, STP_TRANS ST, STP_MASTER_TRANS MT ");
		sqlBuf.append(" WHERE ERR.TRX_UID = ST.TRX_UID ");
		sqlBuf.append(" AND MT.STATUS <> 'COMPLETE' ");
		sqlBuf.append(" AND (ST.RESPONSE_CODE <> 'AA' OR ST.RESPONSE_CODE IS NULL) ");
		sqlBuf.append(" AND ST.MASTER_TRX_ID = MT.MASTER_TRX_ID AND MT.TRANSACTION_ID = ? order by ERR.ERROR_ID DESC ");
		sqlBuf.append(" ) TEMP WHERE ROWNUM<=1");
		 
		return getJdbcTemplate().query(sqlBuf.toString(), new Object[] { transactionId }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				List aList = new ArrayList();
				aList.add(rs.getString("TRX_TYPE"));
				aList.add(rs.getString("ERROR_CODE"));
				aList.add(rs.getString("ERROR_DESC"));
				return aList;
			}
		});
	}

	/**
	 * get Stp Islamic Loan Type From HOST_PRODUCT_TYPE
	 */
	public String getStpIslamicLoanType(String productType, String facilityCode) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT p.SPTF_LOAN_TYPE as SPTF_LOAN_TYPE, ");
		sqlBuf.append("p.INTEREST_BASE as INTEREST_BASE, p.CONCEPT_CODE as CONCEPT_CODE ");
		sqlBuf.append("FROM HOST_PRODUCT_TYPE p, HOST_FACILITY_TYPE f, COMMON_CODE_CATEGORY_ENTRY com ");
		sqlBuf.append("WHERE p.STATUS = 'ACTIVE' ");
		sqlBuf.append("AND p.SOURCE = 'LNPAR2' ");
		sqlBuf.append("AND p.LOAN_TYPE = ? ");
		sqlBuf.append("AND f.FACILITY_CODE = ? ");
		sqlBuf.append("AND com.CATEGORY_CODE = ? ");
		sqlBuf.append("AND com.ACTIVE_STATUS = '1' ");
		sqlBuf.append("AND p.LOAN_TYPE = com.ENTRY_CODE ");
		sqlBuf.append("AND com.REF_ENTRY_CODE = f.FACILITY_CODE ");
		sqlBuf.append("AND p.STATUS = f.STATUS ");
		sqlBuf.append("AND (f.ACCOUNT_TYPE IS NULL OR f.ACCOUNT_TYPE NOT IN ( 'O', 'D' )) ");

		return (String) getJdbcTemplate().query(sqlBuf.toString(),
				new Object[] { productType, facilityCode, CategoryCodeConstant.FACILITY_PRODUCT_MAP },
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							String sptfLoanType = rs.getString("SPTF_LOAN_TYPE");
							String interestBase = rs.getString("INTEREST_BASE");
							String conceptCode = rs.getString("CONCEPT_CODE");
							if (StringUtils.isNotBlank(sptfLoanType) && ICMSConstant.TRUE_VALUE.equals(sptfLoanType)
									&& StringUtils.isNotBlank(interestBase)) {
								if ("9".equals(interestBase)) {
									return IStpConstants.STP_ISLAMIC_LOAN_TYPE_MASTER;
								}
								else if ("8".equals(interestBase) && StringUtils.isNotBlank(conceptCode)
										&& "23".equals(conceptCode)) {
									return IStpConstants.STP_ISLAMIC_LOAN_TYPE_BBA;
								}
								else if ("8".equals(interestBase) && StringUtils.isNotBlank(conceptCode)
										&& "26".equals(conceptCode)) {
									return IStpConstants.STP_ISLAMIC_LOAN_TYPE_CORPORATE;
								}
							}
						}
						return null;
					}
				});
	}

	public String getCodeCategoryEntry(String categoryCode, String entryCode) {
		String selectSql = "SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = ? and ENTRY_CODE = ?";

		return (String) getJdbcTemplate().query(selectSql.toString(), new Object[] { categoryCode, entryCode },
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString("ENTRY_NAME");
						}
						return null;
					}
				});
	}
}
