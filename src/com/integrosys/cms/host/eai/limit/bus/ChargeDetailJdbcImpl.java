/*
 * ChargeDetailDAO.java
 *
 * Created on May 14, 2007, 3:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.integrosys.cms.host.eai.limit.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.host.eai.limit.LimitProfileMismatchException;
import com.integrosys.cms.host.eai.limit.NoSuchLimitSecMapException;
import com.integrosys.cms.host.eai.security.NoSuchSecurityException;

/**
 * @author Chong Jun Yong
 * @since 1.0
 */
public class ChargeDetailJdbcImpl extends JdbcDaoSupport implements IChargeDetailJdbc {

	private Log logger = LogFactory.getLog(ChargeDetailJdbcImpl.class);

	private String selectLimitSecurityMap = "SELECT * FROM CMS_LIMIT_SECURITY_MAP WHERE CMS_LSP_LMT_PROFILE_ID = ? ";

	private String selectLimitChargeMap = "SELECT * FROM CMS_LIMIT_CHARGE_MAP WHERE CMS_LSP_LMT_PROFILE_ID = ? AND STATUS = 'ACTIVE' ";

	/* by limit profile id, collateral id and charge map id */
	private String selectLimitChargeIdByColIdAndChargeDetailId = "SELECT CHARGE_DETAIL_ID FROM CMS_CHARGE_DETAIL WHERE CHARGE_DETAIL_ID = ? AND STATUS = 'ACTIVE' ";

	/** Creates a new instance of ChargeDetailJdbcImpl */
	public ChargeDetailJdbcImpl() {
	}

	/**
	 * @param limitProfileId cms limit profile id
	 * @return a list of limit security map belong the limit profile
	 */
	public LimitSecurityMap[] getLimitSecurityMapByLimitProfileId(long limitProfileId) {

		List resultList = getJdbcTemplate().query(selectLimitSecurityMap, new Object[] { new Long(limitProfileId) },
				new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						LimitSecurityMap limitSecMap = new LimitSecurityMap();

						limitSecMap.setChargeId(rs.getLong("CHARGE_ID"));
						limitSecMap.setCmsCollateralId(rs.getLong("CMS_COLLATERAL_ID"));
						limitSecMap.setLimitId(rs.getLong("CMS_LSP_APPR_LMTS_ID"));
						limitSecMap.setLimitProfileId(rs.getLong("CMS_LSP_LMT_PROFILE_ID"));

						return limitSecMap;
					}

				});

		logger.info("limit sec map size is : [" + resultList.size() + " ] for limit profile id [" + limitProfileId
				+ "]");

		return (LimitSecurityMap[]) resultList.toArray(new LimitSecurityMap[0]);

	}

	/**
	 * @param limitProfileId cms limit profile id
	 * @return list of charge id of limit security map belong to the limit
	 *         profile
	 */
	public Long[] getLimitSecurityMapIdByLimitProfileId(long limitProfileId) {

		List resultList = getJdbcTemplate().query(selectLimitSecurityMap, new Object[] { new Long(limitProfileId) },
				new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new Long(rs.getLong("CHARGE_ID"));
					}

				});

		logger.info("resultList size is : [" + resultList.size() + " ] for limit profile id [" + limitProfileId + "]");

		return (Long[]) resultList.toArray(new Long[0]);
	}

	/**
	 * @param limitProfileId cms limit profile id
	 * @return list of limit charge map that belong to the limit profile
	 */
	public LimitChargeMap[] getLimitChargeMapByLimitProfileId(long limitProfileId) {

		List resultList = getJdbcTemplate().query(selectLimitChargeMap, new Object[] { new Long(limitProfileId) },
				new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						LimitChargeMap limitChargeMap = new LimitChargeMap();

						limitChargeMap.setChargeMapID(rs.getLong("LIMIT_CHARGE_MAP_ID"));
						limitChargeMap.setLimitID(rs.getLong("CMS_LSP_APPR_LMTS_ID"));
						limitChargeMap.setCoborrowerLimitID(new Long(rs.getLong("CMS_LSP_CO_BORROW_LMT_ID")));
						limitChargeMap.setChargeID(rs.getLong("CHARGE_ID"));
						limitChargeMap.setChargeDetailID(rs.getLong("CHARGE_DETAIL_ID"));
						limitChargeMap.setCollateralID(rs.getLong("CMS_COLLATERAL_ID"));
						limitChargeMap.setStatus(rs.getString("STATUS"));
						limitChargeMap.setCustomerCategory(rs.getString("CUSTOMER_CATEGORY"));
						limitChargeMap.setLimitProfileId(rs.getLong("CMS_LSP_LMT_PROFILE_ID"));

						return limitChargeMap;
					}

				});

		logger.info("limit charge map size is : [" + resultList.size() + " ] for limit profile id [" + limitProfileId
				+ "]");

		return (LimitChargeMap[]) resultList.toArray(new LimitChargeMap[0]);

	}

	public Long getCmsChargeDetailIdByOldCmsChargeDetailId(long chargeDetailId) {
		try {
			long cmsChargeDetailId = getJdbcTemplate().queryForLong(selectLimitChargeIdByColIdAndChargeDetailId,
					new Object[] { new Long(chargeDetailId) });

			return new Long(cmsChargeDetailId);
		}
		catch (IncorrectResultSizeDataAccessException ex) {
			return null;
		}
	}

	public String getSecurityCurrencyCodeByCmsSecurityId(long cmsSecurityId) {
		try {
			return (String) getJdbcTemplate().queryForObject(
					"SELECT sci_security_currency FROM cms_security WHERE cms_collateral_id = ?",
					new Object[] { new Long(cmsSecurityId) }, String.class);
		}
		catch (IncorrectResultSizeDataAccessException ex) {
			return null;
		}
	}

	public List retrieveStageChargeDetailIdListByActualChargeDetailIdList(List actualChargeDetailIds) {
		if (actualChargeDetailIds == null || actualChargeDetailIds.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List argList = new ArrayList();

		StringBuffer queryBuf = new StringBuffer();
		queryBuf.append("SELECT charge_detail_id FROM cms_stage_charge_detail WHERE ");
		queryBuf.append("cms_collateral_id IN (SELECT staging_reference_id FROM transaction WHERE ");
		queryBuf.append("reference_id IN (SELECT cms_collateral_id FROM cms_charge_detail WHERE charge_detail_id ");

		CommonUtil.buildSQLInList(actualChargeDetailIds, queryBuf, argList);

		queryBuf.append(" ) and transaction_type = ? )");

		argList.add("COL");

		return getJdbcTemplate().query(queryBuf.toString(), argList.toArray(), new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Long(rs.getLong("charge_detail_id"));
			}
		});
	}

	public long retrieveChargeIdByCmsLimitIdAndCmsSecurityIdAndCmsLimitProfileId(long cmsLimitId, long cmsSecurityId,
			final long cmsLimitProfileId, boolean isActual) throws NoSuchLimitSecMapException,
			LimitProfileMismatchException {
		StringBuffer query = new StringBuffer();
		query.append("SELECT charge_id FROM ").append(
				isActual ? "cms_limit_security_map" : "cms_stage_limit_security_map").append(" WHERE ");
		query.append("cms_lsp_appr_lmts_id = ? AND ");
		query.append("cms_collateral_id = ? AND ");
		query.append("cms_lsp_lmt_profile_id = ? AND ");
		query.append("update_status_ind <> ? ");

		try {
			long chargeId = getJdbcTemplate().queryForLong(
					query.toString(),
					new Object[] { new Long(cmsLimitId), new Long(cmsSecurityId), new Long(cmsLimitProfileId),
							ICMSConstant.HOST_STATUS_DELETE });
			return chargeId;
		}
		catch (IncorrectResultSizeDataAccessException ex) {
			if (ex.getActualSize() == 0) {
				query = new StringBuffer();
				query.append("SELECT cms_limit_profile_id, los_bca_ref_num FROM sci_lsp_appr_lmts ");
				query.append("WHERE cms_lsp_appr_lmts_id = ?");

				LimitProfile actualLimitProfile = (LimitProfile) getJdbcTemplate().queryForObject(query.toString(),
						new Object[] { new Long(cmsLimitId) }, new RowMapper() {

							public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
								LimitProfile limitProfile = new LimitProfile();
								limitProfile.setLimitProfileId(rs.getLong("cms_limit_profile_id"));
								limitProfile.setLOSAANumber(rs.getString("los_bca_ref_num"));
								return limitProfile;
							}
						});

				if (actualLimitProfile.getLimitProfileId() != cmsLimitProfileId) {
					LimitProfile expectedLimitProfile = (LimitProfile) getJdbcTemplate().queryForObject(
							"SELECT los_bca_ref_num FROM sci_lsp_lmt_profile WHERE cms_lsp_lmt_profile_id = ?",
							new Object[] { new Long(cmsLimitProfileId) }, new RowMapper() {

								public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
									LimitProfile limitProfile = new LimitProfile();
									limitProfile.setLimitProfileId(cmsLimitProfileId);
									limitProfile.setLOSAANumber(rs.getString("los_bca_ref_num"));
									return limitProfile;
								}
							});
					throw new LimitProfileMismatchException(cmsLimitId, expectedLimitProfile.getLOSAANumber(),
							actualLimitProfile.getLOSAANumber());
				}
			}

			throw new NoSuchLimitSecMapException(cmsLimitId, cmsSecurityId, cmsLimitProfileId, isActual);
		}
	}

	public long retrieveStageChargeDetailIdByActualChargeDetailId(long actualChargeDetailId) {
		StringBuffer query = new StringBuffer();
		query.append("SELECT charge_detail_id FROM cms_stage_charge_detail WHERE (cms_collateral_id, cms_ref_id) IN ");
		query.append("(SELECT staging_reference_id, cms_ref_id FROM transaction, cms_charge_detail WHERE ");
		query.append("reference_id = cms_collateral_id AND transaction_type = ? ");
		query.append("AND charge_detail_id = ? )");

		return getJdbcTemplate().queryForLong(query.toString(),
				new Object[] { ICMSConstant.INSTANCE_COLLATERAL, new Long(actualChargeDetailId) });
	}

	public long getCmsSecurityIdByLosSecurityId(String losSecurityId, String sourceId) {
		try {
			return getJdbcTemplate().queryForLong(
					"SELECT cms_collateral_id FROM cms_security WHERE los_security_dtl_id = ? AND source_id = ?",
					new Object[] { losSecurityId, sourceId });
		}
		catch (IncorrectResultSizeDataAccessException ex) {
			if (ex.getActualSize() == 0) {
				throw new NoSuchSecurityException(losSecurityId, sourceId);
			}
			else {
				throw new IncorrectResultSizeDataAccessException("LOS Security Id [" + losSecurityId + "], Source Id ["
						+ sourceId + "] getting more than 1 record in CMS, please verify.", 1, ex.getActualSize());
			}
		}
	}
}