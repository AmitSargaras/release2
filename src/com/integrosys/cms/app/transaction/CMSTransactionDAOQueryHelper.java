package com.integrosys.cms.app.transaction;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;

public abstract class CMSTransactionDAOQueryHelper {
	/**
	 * appends the transaction filter condition to the where clause
	 * @param criteria
	 * @return String
	 */
	public static String getTransactionFilterQuery(CMSTrxSearchCriteria criteria, List paramList) {

		StringBuffer retQuery = new StringBuffer();
		if (criteria.getTeamID() == ICMSConstant.LONG_INVALID_VALUE) {
			String[] countries = criteria.getAllowedCountries();

			// null country code to be defaulted to CTRY_CODE_INVALID_VALUE
			// include new default value in filter
			// todo query will no longer check for null country code
			if (!CommonUtil.isEmptyArray(countries)) {
				String[] newArray = new String[countries.length + 1];
				newArray[0] = ICMSConstant.CTRY_CODE_INVALID_VALUE;
				for (int i = 0; i < countries.length; i++) {
					newArray[i + 1] = countries[i];
				}
				countries = newArray;
			}

			if (!CommonUtil.isEmptyArray(countries)) {
				retQuery.append(" AND A.TRX_ORIGIN_COUNTRY  ");
				CommonUtil.buildSQLInList(countries, retQuery, paramList);
			}

			String[] segments = criteria.getAllowedSegments();

			if (!CommonUtil.isEmptyArray(segments)) {
				String[] newArray = new String[segments.length + 1];
				newArray[0] = ICMSConstant.SEGMENT_INVALID_VALUE;
				for (int i = 0; i < segments.length; i++) {
					newArray[i + 1] = segments[i];
				}
				segments = newArray;
			}
			if (!CommonUtil.isEmptyArray(segments)) {
				retQuery.append(" AND A.TRX_SEGMENT ");
				CommonUtil.buildSQLInList(segments, retQuery, paramList);
			}

			String[] organisations = criteria.getAllowedOrganisations();

			// null org code to be defaulted to SEGMENT_INVALID_VALUE include
			// new
			// default value in filter todo query will no longer check for null
			// org
			// code
			if (!CommonUtil.isEmptyArray(organisations)) {
				String[] newArray = new String[organisations.length + 1];
				newArray[0] = ICMSConstant.ORG_CODE_INVALID_VALUE;
				for (int i = 0; i < organisations.length; i++) {
					newArray[i + 1] = organisations[i];
				}
				organisations = newArray;
			}
			if (!CommonUtil.isEmptyArray(organisations)) {
				retQuery.append(" AND A.TRX_ORIGIN_ORGANISATION ");
				CommonUtil.buildSQLInList(organisations, retQuery, paramList);
			}
		}

		if (criteria.getTeamID() != ICMSConstant.LONG_INVALID_VALUE) {
			// check by team ID is enough
			// if maker allow to access the trx, checker in the same team also
			// allow to access
			/*
			 * String filterTeamCountry =
			 * "SELECT COUNTRY_CODE FROM CMS_TEAM_COUNTRY_CODE WHERE TEAM_ID = ? "
			 * ; retQuery.append(" AND (A.TRX_ORIGIN_COUNTRY IN ( ");
			 * retQuery.append(filterTeamCountry); retQuery.append(" ) ");
			 * retQuery.append(" OR A.TRX_ORIGIN_COUNTRY = '" +
			 * ICMSConstant.CTRY_CODE_INVALID_VALUE + "' ");
			 * retQuery.append(" ) "); paramList.add(new
			 * Long(criteria.getTeamID()));
			 * 
			 * String filterTeamOrganisation =
			 * "SELECT ORGANISATION_CODE FROM CMS_TEAM_ORGANISATION_CODE WHERE TEAM_ID = ? "
			 * ; retQuery.append(" AND (A.TRX_ORIGIN_ORGANISATION IN ( ");
			 * retQuery.append(filterTeamOrganisation); retQuery.append(" ) ");
			 * retQuery.append(" OR A.TRX_ORIGIN_ORGANISATION = '" +
			 * ICMSConstant.ORG_CODE_INVALID_VALUE + "' ");
			 * retQuery.append(" ) "); paramList.add(new
			 * Long(criteria.getTeamID()));
			 * 
			 * String filterTeamSegment =
			 * "SELECT SEGMENT_CODE FROM CMS_TEAM_SEGMENT_CODE WHERE TEAM_ID = ? "
			 * ; retQuery.append(" AND (A.TRX_SEGMENT IN ( ");
			 * retQuery.append(filterTeamSegment); retQuery.append(" ) ");
			 * retQuery.append(" OR A.TRX_SEGMENT = '" +
			 * ICMSConstant.SEGMENT_INVALID_VALUE + "' ");
			 * retQuery.append(" ) "); paramList.add(new
			 * Long(criteria.getTeamID()));
			 */

			retQuery.append(" AND A.TEAM_ID = ? ");
			paramList.add(new Long(criteria.getTeamID()));
		}

		if ((criteria.getLimitProfileID() != null) && (criteria.getLimitProfileID().longValue() != 0)) {
			retQuery.append(" AND A.LIMIT_PROFILE_ID = ?");
			paramList.add(criteria.getLimitProfileID());
		}

		appendCustomerFilter(criteria, paramList, retQuery);

		return retQuery.toString();
	}
	
	//FOR BRANCH USER CR ADDED  START
	public static String getTransactionFilterQueryNew(CMSTrxSearchCriteria criteria, List paramList,long teamIdforBranchUser) {

		StringBuffer retQuery = new StringBuffer();
		if (criteria.getTeamID() == ICMSConstant.LONG_INVALID_VALUE) {
			String[] countries = criteria.getAllowedCountries();

			// null country code to be defaulted to CTRY_CODE_INVALID_VALUE
			// include new default value in filter
			// todo query will no longer check for null country code
			if (!CommonUtil.isEmptyArray(countries)) {
				String[] newArray = new String[countries.length + 1];
				newArray[0] = ICMSConstant.CTRY_CODE_INVALID_VALUE;
				for (int i = 0; i < countries.length; i++) {
					newArray[i + 1] = countries[i];
				}
				countries = newArray;
			}

			if (!CommonUtil.isEmptyArray(countries)) {
				retQuery.append(" AND A.TRX_ORIGIN_COUNTRY  ");
				CommonUtil.buildSQLInList(countries, retQuery, paramList);
			}

			String[] segments = criteria.getAllowedSegments();

			if (!CommonUtil.isEmptyArray(segments)) {
				String[] newArray = new String[segments.length + 1];
				newArray[0] = ICMSConstant.SEGMENT_INVALID_VALUE;
				for (int i = 0; i < segments.length; i++) {
					newArray[i + 1] = segments[i];
				}
				segments = newArray;
			}
			if (!CommonUtil.isEmptyArray(segments)) {
				retQuery.append(" AND A.TRX_SEGMENT ");
				CommonUtil.buildSQLInList(segments, retQuery, paramList);
			}

			String[] organisations = criteria.getAllowedOrganisations();

			// null org code to be defaulted to SEGMENT_INVALID_VALUE include
			// new
			// default value in filter todo query will no longer check for null
			// org
			// code
			if (!CommonUtil.isEmptyArray(organisations)) {
				String[] newArray = new String[organisations.length + 1];
				newArray[0] = ICMSConstant.ORG_CODE_INVALID_VALUE;
				for (int i = 0; i < organisations.length; i++) {
					newArray[i + 1] = organisations[i];
				}
				organisations = newArray;
			}
			if (!CommonUtil.isEmptyArray(organisations)) {
				retQuery.append(" AND A.TRX_ORIGIN_ORGANISATION ");
				CommonUtil.buildSQLInList(organisations, retQuery, paramList);
			}
		}

		if (criteria.getTeamID() != ICMSConstant.LONG_INVALID_VALUE) {
			// check by team ID is enough
			// if maker allow to access the trx, checker in the same team also
			// allow to access
			/*
			 * String filterTeamCountry =
			 * "SELECT COUNTRY_CODE FROM CMS_TEAM_COUNTRY_CODE WHERE TEAM_ID = ? "
			 * ; retQuery.append(" AND (A.TRX_ORIGIN_COUNTRY IN ( ");
			 * retQuery.append(filterTeamCountry); retQuery.append(" ) ");
			 * retQuery.append(" OR A.TRX_ORIGIN_COUNTRY = '" +
			 * ICMSConstant.CTRY_CODE_INVALID_VALUE + "' ");
			 * retQuery.append(" ) "); paramList.add(new
			 * Long(criteria.getTeamID()));
			 * 
			 * String filterTeamOrganisation =
			 * "SELECT ORGANISATION_CODE FROM CMS_TEAM_ORGANISATION_CODE WHERE TEAM_ID = ? "
			 * ; retQuery.append(" AND (A.TRX_ORIGIN_ORGANISATION IN ( ");
			 * retQuery.append(filterTeamOrganisation); retQuery.append(" ) ");
			 * retQuery.append(" OR A.TRX_ORIGIN_ORGANISATION = '" +
			 * ICMSConstant.ORG_CODE_INVALID_VALUE + "' ");
			 * retQuery.append(" ) "); paramList.add(new
			 * Long(criteria.getTeamID()));
			 * 
			 * String filterTeamSegment =
			 * "SELECT SEGMENT_CODE FROM CMS_TEAM_SEGMENT_CODE WHERE TEAM_ID = ? "
			 * ; retQuery.append(" AND (A.TRX_SEGMENT IN ( ");
			 * retQuery.append(filterTeamSegment); retQuery.append(" ) ");
			 * retQuery.append(" OR A.TRX_SEGMENT = '" +
			 * ICMSConstant.SEGMENT_INVALID_VALUE + "' ");
			 * retQuery.append(" ) "); paramList.add(new
			 * Long(criteria.getTeamID()));
			 */

			retQuery.append(" AND A.TEAM_ID IN (? , ?) ");
			paramList.add(new Long(criteria.getTeamID()));
			paramList.add(new Long(teamIdforBranchUser));
		}

		if ((criteria.getLimitProfileID() != null) && (criteria.getLimitProfileID().longValue() != 0)) {
			retQuery.append(" AND A.LIMIT_PROFILE_ID = ?");
			paramList.add(criteria.getLimitProfileID());
		}

		appendCustomerFilter(criteria, paramList, retQuery);

		return retQuery.toString();
	}
	
	// END

	/**
	 * Helper method to append customer filter based on the search criteria.
	 * 
	 * @param criteria - CMSTrxSearchCriteria
	 * @param conditionBuf - StringBuffer
	 */
	public static void appendCustomerFilter(CMSTrxSearchCriteria criteria, List paramList, StringBuffer conditionBuf) {
		if (StringUtils.isNotBlank(criteria.getLegalName())) {
			//conditionBuf.append(" AND UPPER(MP.LMP_LONG_NAME) LIKE '").append(criteria.getLegalName().trim().toUpperCase()).append("%' ");
//			conditionBuf.append(" AND UPPER(sp.LSP_SHORT_NAME) LIKE '").append(criteria.getLegalName().trim().toUpperCase()).append("%' ");
			conditionBuf.append(" AND UPPER(sp.LSP_SHORT_NAME) LIKE ? ");
			
			paramList.add(criteria.getLegalName().trim().toUpperCase()+"%");
			
			if ((criteria.getLegalName() != null) && (criteria.getLegalName().length() > 1)) {
//				conditionBuf.append(" AND UPPER(sp.LSP_SHORT_NAME) LIKE '").append(
//						criteria.getLegalName().trim().substring(0, 1).toUpperCase()).append("%' ");
				conditionBuf.append(" AND UPPER(sp.LSP_SHORT_NAME) LIKE ? ");
				paramList.add(criteria.getLegalName().trim().substring(0, 1).toUpperCase()+"%");
			}
		}
		
		/*if (StringUtils.isNotBlank(criteria.getLegalID())) {
			conditionBuf.append(" AND MP.LMP_LE_ID = ? ");
			paramList.add(criteria.getLegalID());
		}*/
		     if (StringUtils.isNotBlank(criteria.getLegalID())) {
			   conditionBuf.append(" AND sp.LSP_LE_ID = ? ");
			   paramList.add(criteria.getLegalID().trim());
		}

		if (StringUtils.isNotBlank(criteria.getLegalIDType())) {
			conditionBuf.append(" AND MP.SOURCE_ID = ? ");
			paramList.add(criteria.getLegalIDType());
		}

		if (StringUtils.isNotBlank(criteria.getCustomerName())) {
		    // Commented By Abhijit R : Was not able to search securites in to do list.
			/*conditionBuf.append(" AND A.legal_name LIKE '").append(
					criteria.getCustomerName().trim().toUpperCase()).append("%' ");
			if ((criteria.getCustomerName() != null) && (criteria.getCustomerName().length() > 1)) {
				conditionBuf.append(" AND A.legal_name LIKE '").append(
						criteria.getCustomerName().trim().substring(0, 1).toUpperCase()).append("%' ");
			}*/
			
//			conditionBuf.append(" AND ( upper(A.customer_name) LIKE '%").append(
//					criteria.getCustomerName().trim().toUpperCase()).append("%' or upper(a.legal_name) like '%").append(criteria.getCustomerName().toUpperCase()).append("%' )");
			
			conditionBuf.append(" AND ( upper(A.customer_name) LIKE ? or upper(a.legal_name) like ? ) ");
			
			paramList.add("%"+criteria.getCustomerName().trim().toUpperCase()+"%");
			paramList.add("%"+criteria.getCustomerName().toUpperCase()+"%");
		}
		if (StringUtils.isNotBlank(criteria.getLoginID())) {
//			conditionBuf.append(" AND a.customer_name  LIKE '").append(
//					criteria.getLoginID().trim().toUpperCase()).append("%' ");
			conditionBuf.append(" AND a.customer_name  LIKE ? ");
			
			paramList.add(criteria.getLoginID().trim().toUpperCase()+"%");
			
			if ((criteria.getLoginID() != null) && (criteria.getLoginID().length() > 1)) {
//				conditionBuf.append(" AND a.customer_name  LIKE '").append(
//						criteria.getLoginID().trim().substring(0, 1).toUpperCase()).append("%' ");
				conditionBuf.append(" AND a.customer_name  LIKE ? ");
				
				paramList.add(criteria.getLoginID().trim().substring(0, 1).toUpperCase()+"%");
			}
		}

		if (StringUtils.isNotBlank(criteria.getAaNumber())) {
//			conditionBuf.append(" AND UPPER(A.LIMIT_PROFILE_REF_NUM) LIKE '").append(
//					criteria.getAaNumber().trim().toUpperCase()).append("%' ");
			
			conditionBuf.append(" AND UPPER(A.LIMIT_PROFILE_REF_NUM) LIKE ? ");
			
			paramList.add(criteria.getAaNumber().trim().toUpperCase()+"%");
		}
		// query for last updated by field
		if (StringUtils.isNotBlank(criteria.getLastUpdatedBy())) {
//			conditionBuf.append(" AND UPPER(A.USER_INFO) LIKE '%").append(
//					criteria.getLastUpdatedBy().trim().toUpperCase()).append("%' ");
			conditionBuf.append(" AND UPPER(A.USER_INFO) LIKE ? ");
			
			paramList.add("%"+criteria.getLastUpdatedBy().trim().toUpperCase()+"%");
		}
		// check for search indicator[todo/totrack]
		if(criteria.getSearchIndicator().equalsIgnoreCase("totrack")){
		if (StringUtils.isNotBlank(criteria.getTransactionType())) {
//			conditionBuf.append(" AND trim(UPPER(B.USER_TRX_TYPE)) = trim('").append(
//					criteria.getTransactionType().toUpperCase()).append("') ");
			conditionBuf.append(" AND trim(UPPER(B.USER_TRX_TYPE)) = trim(?) ");
			
			paramList.add(criteria.getTransactionType().toUpperCase());
		}
		}
		else{
			if (StringUtils.isNotBlank(criteria.getTransactionType())) {
//				conditionBuf.append(" AND trim(UPPER(f2.USER_TRX_TYPE)) = trim('").append(
//						criteria.getTransactionType().toUpperCase()).append("') ");
				conditionBuf.append(" AND trim(UPPER(f2.USER_TRX_TYPE)) = trim(?) ");
				
				paramList.add(criteria.getTransactionType().toUpperCase());
			}
		}
		if(criteria.getSearchIndicator().equalsIgnoreCase("totrack")){
			if (StringUtils.isNotBlank(criteria.getStatus())) {
//				conditionBuf.append(" AND trim(UPPER(b.USER_STATE)) like trim('").append(
//						criteria.getStatus().replace('_',' ').toUpperCase()).append("%') ");
				conditionBuf.append(" AND trim(UPPER(b.USER_STATE)) like trim(?) ");
				
				paramList.add(criteria.getStatus().replace('_',' ').toUpperCase()+"%");
			}
		}
		else{
		if (StringUtils.isNotBlank(criteria.getStatus())) {
//			conditionBuf.append(" AND trim(UPPER(f2.USER_STATE)) like trim('").append(
//					criteria.getStatus().replace('_',' ').toUpperCase()).append("%') ");
			conditionBuf.append(" AND trim(UPPER(f2.USER_STATE)) like trim(?) ");
			
			paramList.add(criteria.getStatus().replace('_',' ').toUpperCase()+"%");
		}
		}
	}
	public static String getPendingCasesCondition(CMSTrxSearchCriteria criteria, List paramList) {
		StringBuffer conditionSQL = new StringBuffer();

		conditionSQL.append(" AND pf.CMS_ORIG_COUNTRY IN ( ");
		conditionSQL.append(" SELECT COUNTRY_CODE FROM CMS_TEAM_COUNTRY_CODE WHERE TEAM_ID = ? ");
		conditionSQL.append(" ) ");
		paramList.add(new Long(criteria.getTeamID()));

		conditionSQL.append(" AND pf.CMS_ORIG_ORGANISATION IN ( ");
		if ((criteria.getFilterByType() != null) && criteria.getFilterByType().equals(ICMSConstant.FILTER_BY_ORG)
				&& (criteria.getFilterByValue() != null) && (criteria.getFilterByValue().trim().length() > 0)) {
			conditionSQL.append(" '" + criteria.getFilterByValue() + "' ");
		}
		else {
			conditionSQL.append(" SELECT ORGANISATION_CODE FROM CMS_TEAM_ORGANISATION_CODE WHERE TEAM_ID = ? ");
			paramList.add(new Long(criteria.getTeamID()));
		}
		conditionSQL.append(" ) ");

		conditionSQL.append(" AND pf.LLP_SEGMENT_CODE_VALUE IN ( ");
		if ((criteria.getFilterByType() != null)
				&& criteria.getFilterByType().equals(ICMSConstant.FILTER_BY_CMS_SEGMENT)
				&& (criteria.getFilterByValue() != null) && (criteria.getFilterByValue().trim().length() > 0)) {
			conditionSQL.append(" '" + criteria.getFilterByValue() + "' ");
		}
		else {
			conditionSQL.append(" SELECT SEGMENT_CODE FROM CMS_TEAM_CMS_SEGMENT_CODE WHERE TEAM_ID = ? ");
			paramList.add(new Long(criteria.getTeamID()));
		}
		conditionSQL.append(" ) ");

		return conditionSQL.toString();
	}

	public static String getPendingCasesSortOrder(CMSTrxSearchCriteria criteria) {
		StringBuffer sortSQL = new StringBuffer();
		sortSQL.append(" ORDER BY ");
		if ((criteria.getSortBy() != null) && (criteria.getSortBy().trim().length() > 0)) {
			sortSQL.append(criteria.getSortBy());
		}
		else {
			sortSQL.append(" LLP_LE_ID, ORG_CODE ");
		}
		return sortSQL.toString();
	}

	public static String getPendingPerfectCreditFolderCondition(CMSTrxSearchCriteria criteria, List paramList) {
		StringBuffer conditionSqlBuf = new StringBuffer();

		conditionSqlBuf.append(" WHERE ppcf.cms_orig_country IN ( ");
		conditionSqlBuf.append(" SELECT COUNTRY_CODE FROM CMS_TEAM_COUNTRY_CODE WHERE TEAM_ID = ? )");
		paramList.add(new Long(criteria.getTeamID()));

		conditionSqlBuf.append(" AND ppcf.cms_orig_organisation IN ( ");
		conditionSqlBuf.append(" SELECT ORGANISATION_CODE FROM CMS_TEAM_ORGANISATION_CODE WHERE TEAM_ID = ? )");
		paramList.add(new Long(criteria.getTeamID()));

//		conditionSqlBuf.append(" AND EXISTS (SELECT 1 FROM sci_lsp_appr_lmts lmt, ");
//		conditionSqlBuf.append("cms_facility_master fac, cms_fac_general fg ");
//		conditionSqlBuf.append("WHERE cms_limit_profile_id = ppcf.cms_lsp_lmt_profile_id ");
//		conditionSqlBuf.append("AND lmt.cms_lsp_appr_lmts_id = fac.cms_lsp_appr_lmts_id ");
//		conditionSqlBuf.append("AND fac.id = fg.cms_fac_master_id ");
//		conditionSqlBuf.append("AND fg.fac_status_code_value <> ? ) ");

//		paramList.add(ICMSConstant.FACILITY_STATUS_CANCELLED);

		if (PropertiesConstantHelper.isFilterByApplicationType()) {
			conditionSqlBuf.append(" AND ppcf.application_type IN ( ");
			conditionSqlBuf.append(" SELECT SEGMENT_CODE FROM CMS_TEAM_SEGMENT_CODE WHERE TEAM_ID = ? )");
			paramList.add(new Long(criteria.getTeamID()));
		}

		if (StringUtils.isNotBlank(criteria.getAaNumber())) {
			conditionSqlBuf.append(" AND ppcf.host_bca_ref_num = ? ");
			paramList.add(criteria.getAaNumber());
		}

		if (StringUtils.isNotBlank(criteria.getLegalID())) {
			conditionSqlBuf.append(" AND ppcf.llp_le_id = ? ");
			paramList.add(criteria.getLegalID());
		}

		if (StringUtils.isNotBlank(criteria.getCustomerName())) {
			conditionSqlBuf.append(" AND EXISTS (SELECT 1 FROM sci_le_sub_profile sp ");
			conditionSqlBuf.append("WHERE sp.cms_le_sub_profile_id = ppcf.cms_customer_id " +
					"and sp.ulsp_short_name LIKE '").append(
					criteria.getCustomerName().trim().toUpperCase()).append("%' )");
		}

		return conditionSqlBuf.toString();
	}
}
