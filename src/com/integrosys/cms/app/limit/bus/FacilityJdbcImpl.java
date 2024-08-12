package com.integrosys.cms.app.limit.bus;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.cms.app.common.StpTrxStatusReadyIndicator;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.host.stp.common.IStpConstants;

/**
 * Jdbc Routine for Facility using Spring Jdbc Framework.
 * 
 * @author Chong Jun Yong
 * 
 */
public class FacilityJdbcImpl extends JdbcDaoSupport implements IFacilityJdbc {

	public ILimit getBasicLimitInfoByCmsLimitId(long cmsLimitId, boolean isFromActualTable) {
		String limitProfileTableName = "";
		String limitTableName = "";

		if (isFromActualTable) {
			limitProfileTableName = "sci_lsp_lmt_profile";
			limitTableName = "sci_lsp_appr_lmts";
		}
		else {
			limitProfileTableName = "stage_limit_profile";
			limitTableName = "stage_limit";
		}

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select LMT_OUTER_LMT_ID, lmt_id, los_lmt_id, cms_lsp_appr_lmts_id, llp_bca_ref_num, lmt_fac_code, lmt_fac_seq, ");
		sqlBuf.append("cms_bkg_country, cms_bkg_organisation, lmt_prd_type_num, lmt_prd_type_value, ");
		sqlBuf.append("lmt.cms_req_sec_coverage, lmt.cms_drawing_limit, lmt.cms_outstanding_amt, lmt_expry_date, ");
		sqlBuf.append("lmt.lmt_tenor, lmt.lmt_tenor_basis_num, lmt.lmt_tenor_basis_value, ");
		sqlBuf.append("lmt_fac_type_num, lmt_fac_type_value, lmt_amt, lmt_crrncy_iso_code, account_type from ");
		sqlBuf.append(limitProfileTableName).append(" lp, ");
		sqlBuf.append(limitTableName).append(" lmt ");
		sqlBuf.append(" where lp.cms_lsp_lmt_profile_id = lmt.cms_limit_profile_id ");
		sqlBuf.append(" and lmt.cms_lsp_appr_lmts_id = ? ");

		ILimit limit = (ILimit) getJdbcTemplate().queryForObject(sqlBuf.toString(),
				new Object[] { new Long(cmsLimitId) }, new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						ILimit limit = new OBLimit();
						limit.setLimitRef(rs.getString("lmt_id"));
						limit.setLosLimitRef(rs.getString("los_lmt_id"));
						limit.setLimitID(rs.getLong("cms_lsp_appr_lmts_id"));
						limit.setLimitProfileReferenceNumber(rs.getString("llp_bca_ref_num"));
						limit.setApprovedLimitAmount(new Amount(rs.getBigDecimal("lmt_amt"), new CurrencyCode(rs
								.getString("lmt_crrncy_iso_code"))));
						limit.setFacilityCode(rs.getString("lmt_fac_code"));
						limit.setFacilitySequence(rs.getLong("lmt_fac_seq"));
						limit.setLimitTenor(new Long(rs.getLong("lmt_tenor")));
						limit.setLimitTenorUnitNum(rs.getString("lmt_tenor_basis_num"));
						limit.setLimitTenorUnit(rs.getString("lmt_tenor_basis_value"));
						limit.setLimitExpiryDate((rs.getTimestamp("lmt_expry_date") == null) ? null : new Date(rs
								.getTimestamp("lmt_expry_date").getTime()));
                        // Andy Wong, Dec 4, 2009: set outer limit pk
                        limit.setOuterLimitID(rs.getLong("LMT_OUTER_LMT_ID"));

						IBookingLocation bookingLocation = new OBBookingLocation(rs.getString("cms_bkg_country"), rs
								.getString("cms_bkg_organisation"));

						limit.setBookingLocation(bookingLocation);
						limit.setProductDescNum(rs.getString("lmt_prd_type_num"));
						limit.setProductDesc(rs.getString("lmt_prd_type_value"));
						limit.setFacilityDescNum(rs.getString("lmt_fac_type_num"));
						limit.setFacilityDesc(rs.getString("lmt_fac_type_value"));
						limit.setAccountType(rs.getString("account_type"));
						limit.setRequiredSecurityCoverage(rs.getString("cms_req_sec_coverage"));   //Shiv 190911

						BigDecimal drawingAmount = rs.getBigDecimal("cms_drawing_limit");
						if (drawingAmount != null) {
							limit.setDrawingLimitAmount(new Amount(drawingAmount, new CurrencyCode(rs
									.getString("lmt_crrncy_iso_code"))));
						}

						BigDecimal outstanding = rs.getBigDecimal("cms_outstanding_amt");
						if (outstanding != null) {
							limit.setOutstandingAmount(new Amount(outstanding, new CurrencyCode(rs
									.getString("lmt_crrncy_iso_code"))));
						}

						return limit;
					}
				});

		return limit;
	}

	public List getListOfBasicFacilityMasterInfoByLimitProfileId(long cmsLimitProfileId, boolean isFromActualTable) {
		String limitTableName = "";
		String facilityMasterTableName = "";

		if (isFromActualTable) {
			limitTableName = "sci_lsp_appr_lmts";
			facilityMasterTableName = "cms_facility_master";
		}
		else {
			limitTableName = "stage_limit";
			facilityMasterTableName = "cms_stg_facility_master";
		}

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select lmt_id, los_lmt_id, lmt.cms_lsp_appr_lmts_id, fac.id, lmt_fac_code, lmt_fac_seq, ");
		sqlBuf.append(" lmt_prd_type_num, lmt.lmt_prd_type_value, lmt_fac_type_num, lmt_fac_type_value, ");
		sqlBuf.append(" lmt_amt, lmt_crrncy_iso_code, lmt_bca_ref_num from ");
		sqlBuf.append(limitTableName).append(" lmt, ");
		sqlBuf.append(facilityMasterTableName).append(" fac ");
		sqlBuf.append(" where lmt.cms_lsp_appr_lmts_id = fac.cms_lsp_appr_lmts_id ");
		sqlBuf.append(" and lmt.cms_limit_profile_id = ? ");

		return getJdbcTemplate().query(sqlBuf.toString(), new Object[] { new Long(cmsLimitProfileId) },
				new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						ILimit limit = new OBLimit();
						limit.setLimitRef(rs.getString("lmt_id"));
						limit.setLosLimitRef(rs.getString("los_lmt_id"));
						limit.setLimitID(rs.getLong("cms_lsp_appr_lmts_id"));
						limit.setLimitProfileReferenceNumber(rs.getString("lmt_bca_ref_num"));
						limit.setApprovedLimitAmount(new Amount(rs.getDouble("lmt_amt"), rs
								.getString("lmt_crrncy_iso_code")));
						limit.setFacilityCode(rs.getString("lmt_fac_code"));
						limit.setFacilitySequence(rs.getLong("lmt_fac_seq"));
						limit.setProductDescNum(rs.getString("lmt_prd_type_num"));
						limit.setProductDesc(rs.getString("lmt_prd_type_value"));
						limit.setFacilityDescNum(rs.getString("lmt_fac_type_num"));
						limit.setFacilityDesc(rs.getString("lmt_fac_type_value"));

						IFacilityMaster facilityMaster = new OBFacilityMaster();
						facilityMaster.setId(rs.getLong("id"));
						facilityMaster.setLimit(limit);

						return facilityMaster;
					}
				});
	}

	public IFacilityGeneral retrieveCancelAndRejectFacilityGeneralInfoByCmsFacilityMasterId(
			final long cmsFacilityMasterId) {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT fac_status_code_value, cancel_reject_date, ");
		buf.append("cancel_reject_code_num, cancel_reject_code_value ");
		buf.append("FROM cms_fac_general WHERE cms_fac_master_id = ? ");

		return (IFacilityGeneral) getJdbcTemplate().queryForObject(buf.toString(),
				new Object[] { new Long(cmsFacilityMasterId) }, new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						IFacilityGeneral facGeneral = new OBFacilityGeneral();
						facGeneral.setFacilityStatusEntryCode(rs.getString("fac_status_code_value"));
						facGeneral.setCancelOrRejectDate((rs.getTimestamp("cancel_reject_date") == null) ? null
								: new Date(rs.getTimestamp("cancel_reject_date").getTime()));
						facGeneral.setCancelOrRejectCategoryCode(rs.getString("cancel_reject_code_num"));
						facGeneral.setCancelOrRejectEntryCode(rs.getString("cancel_reject_code_value"));
						facGeneral.setFacilityMasterId(cmsFacilityMasterId);
						return facGeneral;
					}
				});
	}

	public List getListOfCmsLimitIdByCmsLimitProfileId(long cmsLimitProfileId, boolean isFromActualTable) {
		String limitProfileTableName = "";
		String limitTableName = "";

		if (isFromActualTable) {
			limitProfileTableName = "sci_lsp_lmt_profile";
			limitTableName = "sci_lsp_appr_lmts";
		}
		else {
			limitProfileTableName = "stage_limit_profile";
			limitTableName = "stage_limit";
		}

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select cms_lsp_appr_lmts_id from ");
		sqlBuf.append(limitProfileTableName).append(" lp, ");
		sqlBuf.append(limitTableName).append(" lmt ");
		sqlBuf.append(" where lp.cms_lsp_lmt_profile_id = lmt.cms_limit_profile_id ");
		sqlBuf.append(" and lp.cms_lsp_lmt_profile_id = ? ");

		return getJdbcTemplate().query(sqlBuf.toString(), new Object[] { new Long(cmsLimitProfileId) },
				new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new Long(rs.getLong("cms_lsp_appr_lmts_id"));
					}
				});
	}

	public List getListOfCmsLimitIdByLimitProfileGroupReference(String limitProfileGrpRef, boolean isFromActualTable) {
		String limitProfileTableName = "";
		String limitTableName = "";

		if (isFromActualTable) {
			limitProfileTableName = "sci_lsp_lmt_profile";
			limitTableName = "sci_lsp_appr_lmts";
		}
		else {
			limitProfileTableName = "stage_limit_profile";
			limitTableName = "stage_limit";
		}

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select cms_lsp_appr_lmts_id from ");
		sqlBuf.append(limitProfileTableName).append(" lp, ");
		sqlBuf.append(limitTableName).append(" lmt ");
		sqlBuf.append(" where lp.cms_lsp_lmt_profile_id = lmt.cms_limit_profile_id ");
		sqlBuf.append(" and lp.llp_aa_group_num = ? ");

		return getJdbcTemplate().query(sqlBuf.toString(), new Object[] { limitProfileGrpRef }, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Long(rs.getLong("cms_lsp_appr_lmts_id"));
			}
		});
	}

	public List getListOfCmsLimitIdByLimitProfileReference(String limitProfileRef, boolean isFromActualTable) {
		String limitProfileTableName = "";
		String limitTableName = "";

		if (isFromActualTable) {
			limitProfileTableName = "sci_lsp_lmt_profile";
			limitTableName = "sci_lsp_appr_lmts";
		}
		else {
			limitProfileTableName = "stage_limit_profile";
			limitTableName = "stage_limit";
		}

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select cms_lsp_appr_lmts_id from ");
		sqlBuf.append(limitProfileTableName).append(" lp, ");
		sqlBuf.append(limitTableName).append(" lmt ");
		sqlBuf.append(" where lp.cms_lsp_lmt_profile_id = lmt.cms_limit_profile_id ");
		sqlBuf.append(" and lp.llp_bca_ref_num = ? ");

		return getJdbcTemplate().query(sqlBuf.toString(), new Object[] { limitProfileRef }, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Long(rs.getLong("cms_lsp_appr_lmts_id"));
			}
		});
	}

	public void updateLimitInfo(IFacilityMaster facilityMaster) {
		String limitTableNameActual = "";
		String limitTableNameStage = "";

		limitTableNameActual = "sci_lsp_appr_lmts";
		limitTableNameStage = "stage_limit";

		List argList = new ArrayList();
		argList.add((facilityMaster.getDrawingLimitAmount() == null) ? (BigDecimal) null : facilityMaster
				.getDrawingLimitAmount().getAmountAsBigDecimal());
		argList.add(facilityMaster.getRequiredSecurityCoverage());
		argList.add((facilityMaster.getFacilityGeneral() == null) ? null : facilityMaster.getFacilityGeneral()
				.getTerm());
		argList.add((facilityMaster.getFacilityGeneral() == null) ? null : facilityMaster.getFacilityGeneral()
				.getTermCodeCategoryCode());
		argList.add((facilityMaster.getFacilityGeneral() == null) ? null : facilityMaster.getFacilityGeneral()
				.getTermCodeEntryCode());
		argList.add((facilityMaster.getFacilityInterest() == null) ? null : facilityMaster.getFacilityInterest()
				.getInterestRate());
		argList.add((facilityMaster.getFacilityGeneral() == null || facilityMaster.getFacilityGeneral()
				.getApprovedAmount() == null) ? (BigDecimal) null : facilityMaster.getFacilityGeneral()
				.getApprovedAmount().getAmountAsBigDecimal());
		argList.add(new Long(facilityMaster.getLimit().getLimitID()));

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("UPDATE " + limitTableNameActual + " ");
		sqlBuf.append("SET cms_drawing_limit = ? ");
		sqlBuf.append(", cms_req_sec_coverage = ? ");
		sqlBuf.append(", lmt_tenor = ? ");
		sqlBuf.append(", lmt_tenor_basis_num = ? ");
		sqlBuf.append(", lmt_tenor_basis_value = ? ");
		sqlBuf.append(", interest_rate = ? ");
		sqlBuf.append(", lmt_amt = ? ");
		sqlBuf.append("WHERE cms_lsp_appr_lmts_id = ? ");
		getJdbcTemplate().update(sqlBuf.toString(), argList.toArray());

		argList.add(ICMSConstant.INSTANCE_LIMIT);

		sqlBuf = new StringBuffer();
		sqlBuf.append("UPDATE " + limitTableNameStage + " ");
		sqlBuf.append("SET cms_drawing_limit = ? ");
		sqlBuf.append(", cms_req_sec_coverage = ? ");
		sqlBuf.append(", lmt_tenor = ? ");
		sqlBuf.append(", lmt_tenor_basis_num = ? ");
		sqlBuf.append(", lmt_tenor_basis_value = ? ");
		sqlBuf.append(", interest_rate = ? ");
		sqlBuf.append(", lmt_amt = ? ");
		sqlBuf.append("WHERE cms_lsp_appr_lmts_id = (select staging_reference_id FROM transaction ");
		sqlBuf.append("WHERE reference_id = ? AND transaction_type = ? )");
		getJdbcTemplate().update(sqlBuf.toString(), argList.toArray());

		argList.clear();
	}

	public Map retrieveTrxStatusByRefIds(Long[] cmsLimitIds) {
		if (cmsLimitIds == null || cmsLimitIds.length == 0) {
			return Collections.EMPTY_MAP;
		}

		List argList = new ArrayList();

		StringBuffer buf = new StringBuffer();
		buf.append("SELECT fac_trx.from_state, fac_trx.status, fac.to_be_cancelled_ind, ");
		buf.append("facgen.fac_status_code_value fac_status, fac.cms_lsp_appr_lmts_id, ");
		buf.append("m.is_stp_ready, stp_trx.status stp_status ");
		buf.append("FROM cms_facility_master fac, cms_fac_general facgen, transaction fac_trx ");
		buf.append("LEFT OUTER JOIN cms_stp_ready_status_map m ");
		buf.append("ON fac_trx.transaction_id = m.transaction_id AND fac_trx.transaction_type = m.transaction_type ");
		buf.append("LEFT OUTER JOIN stp_master_trans stp_trx ");
		buf.append("ON fac_trx.transaction_id = stp_trx.transaction_id ");
		buf.append("WHERE fac_trx.REFERENCE_ID = fac.ID AND fac_trx.status <> 'CLOSED' ");
		buf.append("AND fac.id = facgen.cms_fac_master_id AND fac.cms_lsp_appr_lmts_id ");

		CommonUtil.buildSQLInList(cmsLimitIds, buf, argList);

		buf.append(" AND fac_trx.transaction_type = ?");
		argList.add("FACILITY");

		return (Map) getJdbcTemplate().query(buf.toString(), argList.toArray(), new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map limitIdStatusMap = new HashMap();
				while (rs.next()) {
					StpTrxStatusReadyIndicator stpTrxStatusReadyInd = new StpTrxStatusReadyIndicator();

					String stpStatus = rs.getString("stp_status");
					String status = rs.getString("status");
					String facilityStatus = rs.getString("fac_status");
					String facilityToBeCancelledInd = rs.getString("to_be_cancelled_ind");
					// String fromState = rs.getString("from_state");
					stpTrxStatusReadyInd.setOriginalTrxStatus(status);
					if (ICMSConstant.TRUE_VALUE.equals(facilityToBeCancelledInd)
							&& ICMSConstant.STATE_ACTIVE.equals(status)) {
						stpTrxStatusReadyInd.setTrxStatus("TO_BE_CANCELLED");
					}
					else if (!IStpConstants.MASTER_TRX_COMPLETE.equals(stpStatus)
							&& ICMSConstant.STATE_ACTIVE.equals(status)) {
						stpTrxStatusReadyInd.setTrxStatus(ICMSConstant.STATE_NEW);
					}
					else {
						if (ICMSConstant.FACILITY_STATUS_CANCELLED.equals(facilityStatus)
								&& ICMSConstant.STATE_ACTIVE.equals(status)) {
							stpTrxStatusReadyInd.setTrxStatus("CANCELLED");
						}
						else {
							stpTrxStatusReadyInd.setTrxStatus(status);
						}
					}

					stpTrxStatusReadyInd.setStpReadyIndicator((ICMSConstant.TRUE_VALUE.equals(rs
							.getString("is_stp_ready"))));

					limitIdStatusMap.put(new Long(rs.getLong("cms_lsp_appr_lmts_id")), stpTrxStatusReadyInd);
				}

				return limitIdStatusMap;
			}
		});
	}

	public void updateOrInsertStpReadyStatus(String transactionId, boolean isStpReady) {
		String selectSql = "SELECT COUNT(1) FROM cms_stp_ready_status_map WHERE transaction_id = ?";

		int count = getJdbcTemplate().queryForInt(selectSql, new Object[] { transactionId });
		if (count == 0) {
			String insertSql = "INSERT INTO cms_stp_ready_status_map "
					+ "(transaction_id, transaction_type, is_stp_ready, last_update_date, created_date) "
					+ "VALUES (?, ?, ?, ?, ?)";
			getJdbcTemplate().update(insertSql,
					new Object[] { transactionId, "FACILITY", ((isStpReady) ? "Y" : "N"), new Date(), new Date() });
		}
		else {
			String updateSql = "UPDATE cms_stp_ready_status_map SET last_update_date = ?, "
					+ "is_stp_ready = ? WHERE transaction_id = ? ";

			getJdbcTemplate().update(
					updateSql,
					new Object[] { new Date(), ((isStpReady) ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE),
							transactionId });
		}
	}

	public String getProductGroupByProductCode(String productType) {
		String selectSql = "SELECT PRODUCT_GROUP FROM HOST_PRODUCT_TYPE WHERE LOAN_TYPE = ?";

		return (String) getJdbcTemplate().query(selectSql.toString(), new Object[] { productType },
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString("PRODUCT_GROUP");
						}
						return null;
					}
				});
	}

	public String getDealerProductFlagByProductCode(String productType) {
		String selectSql = "SELECT DEALER_PRODUCT_IND FROM HOST_PRODUCT_TYPE WHERE LOAN_TYPE = ?";

		return (String) getJdbcTemplate().query(selectSql.toString(), new Object[] { productType },
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString("DEALER_PRODUCT_IND");
						}
						return null;
					}
				});
	}

	public String getRevolvingFlagByFacilityCode(String facilityCode) {
		String selectSql = "SELECT REVOLVING_IND FROM HOST_FACILITY_TYPE WHERE FACILITY_CODE = ?";

		return (String) getJdbcTemplate().query(selectSql.toString(), new Object[] { facilityCode },
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString("REVOLVING_IND");
						}
						return null;
					}
				});
	}

	public String getConceptCodeByProductCode(String productType) {
		String selectSql = "SELECT CONCEPT_CODE FROM HOST_PRODUCT_TYPE WHERE LOAN_TYPE = ?";

		return (String) getJdbcTemplate().query(selectSql.toString(), new Object[] { productType },
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString("CONCEPT_CODE");
						}
						return null;
					}
				});
	}

	public String getAccountTypeByFacilityCode(String facilityCode) {

		return (String) getJdbcTemplate().query("SELECT ACCOUNT_TYPE FROM HOST_FACILITY_TYPE WHERE FACILITY_CODE = ?",
				new Object[] { facilityCode }, new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString("ACCOUNT_TYPE");
						}
						return null;
					}
				});
	}

	public boolean isAcfNoExists(long cmsLimitId, String AANum, String acfNo) {

		Boolean isExist = (Boolean) getJdbcTemplate()
				.query(
						"select count(*) from CMS_FACILITY_MASTER a, SCI_LSP_APPR_LMTS b where a.CMS_LSP_APPR_LMTS_ID = b.CMS_LSP_APPR_LMTS_ID and a.ACF_NO = ? and b.CMS_LSP_APPR_LMTS_ID <> ? and b.LMT_BCA_REF_NUM = ?",
						new Object[] { acfNo, new Long(cmsLimitId), AANum }, new ResultSetExtractor() {
							public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
								if (rs.next() && rs.getInt(1) > 0) {
									return Boolean.TRUE;
								}
								return Boolean.FALSE;
							}
						});

		return isExist.booleanValue();
	}

	@Override
	public List getFacDetailBySecurityId(long securityId) {
		StringBuffer sql = new StringBuffer("select fac.facility_name,fac.lmt_id facility_id,fac.line_no,fac.facility_system ")
				.append("from SCI_LSP_APPR_LMTS fac ")
				.append("inner join CMS_LIMIT_SECURITY_MAP linkage on fac.CMS_LSP_APPR_LMTS_ID = linkage.CMS_LSP_APPR_LMTS_ID ")
				.append("inner join CMS_SECURITY col on col.CMS_COLLATERAL_ID = linkage.CMS_COLLATERAL_ID ")
				.append("where fac.cms_limit_status = 'ACTIVE' and col.CMS_COLLATERAL_ID = ?");
		
		return getJdbcTemplate().query(sql.toString(), new Object[] { securityId}, new RowMapper() {
			
			@Override
			public ILimit mapRow(ResultSet rs, int idx) throws SQLException {
				ILimit limit = new OBLimit();
				limit.setFacilityName(rs.getString("facility_name"));
				limit.setLimitID(rs.getLong("facility_id"));
				limit.setLineNo(rs.getString("line_no"));
				limit.setFacilitySystem(rs.getString("facility_system"));
				
				return limit;
			}
		});
		
	}
}
