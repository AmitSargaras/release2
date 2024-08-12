package com.integrosys.cms.app.customer.bus;

import org.apache.commons.lang.Validate;

import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public abstract class CustomerDAOFilterHelper {

	private static final String COUNTRY_BY_TEAM_FILTER_SQL = " (SELECT country_code FROM cms_team_country_code WHERE team_id = ? ) \n";

	private static final String ORGANISATION_BY_TEAM_FILTER_SQL = " (SELECT organisation_code FROM cms_team_organisation_code WHERE team_id = ?) \n";

	public static String getLimitProfileDAPFiltersQueryByTeam(final ITeam team, SQLParameter sqlParams) {
		Validate.notNull(team, "ITeam team must not be null.");

		long teamId = team.getTeamID();

		String filterQuery = "AND (sci_lsp_lmt_profile.cms_orig_country IS NULL \n"
				+ "		OR sci_lsp_lmt_profile.cms_orig_country IN  \n" + COUNTRY_BY_TEAM_FILTER_SQL + ")\n"
				+ "AND (sci_lsp_lmt_profile.cms_orig_organisation IS NULL \n"
				+ "		OR sci_lsp_lmt_profile.cms_orig_organisation IN \n" + ORGANISATION_BY_TEAM_FILTER_SQL + ")\n";

		sqlParams.addLong(new Long(teamId));
		sqlParams.addLong(new Long(teamId));

		return filterQuery;
	}

	public static String getLimitAndSecurityDAPFiltersQueryByTeam(final ITeam team, SQLParameter sqlParams,
			boolean isIncludeNonBorrowerFilter) {
		Validate.notNull(team, "ITeam team must not be null.");

		StringBuffer buf = new StringBuffer();

		long teamId = team.getTeamID();

		buf.append(" AND (sci_lsp_lmt_profile.cms_lsp_lmt_profile_id ");
		buf.append(" IN (Select lmtprofile from borrower_location_view where ");
		buf.append("(cms_orig_country IN ");
		buf.append(COUNTRY_BY_TEAM_FILTER_SQL);
		sqlParams.addLong(new Long(teamId));

		buf.append(" AND prof_org IN ");
		buf.append(ORGANISATION_BY_TEAM_FILTER_SQL);
		sqlParams.addLong(new Long(teamId));

		buf.append(") OR ( limit_country IN ");
		buf.append(COUNTRY_BY_TEAM_FILTER_SQL);
		sqlParams.addLong(new Long(teamId));

		buf.append(" AND limit_org IN ");
		buf.append(ORGANISATION_BY_TEAM_FILTER_SQL);
		sqlParams.addLong(new Long(teamId));

		buf.append(") OR (security_location IN ");
		buf.append(COUNTRY_BY_TEAM_FILTER_SQL);
		sqlParams.addLong(new Long(teamId));

		buf.append(" AND security_organisation IN ");
		buf.append(ORGANISATION_BY_TEAM_FILTER_SQL);
		sqlParams.addLong(new Long(teamId));

		buf.append(") OR (co_bo_lmt_loc IN ");
		buf.append(COUNTRY_BY_TEAM_FILTER_SQL);
		sqlParams.addLong(new Long(teamId));

		buf.append(" AND co_bo_lmt_org IN ");
		buf.append(ORGANISATION_BY_TEAM_FILTER_SQL);
		sqlParams.addLong(new Long(teamId));

		buf.append(" ) ) ");

		if (isIncludeNonBorrowerFilter) {
			buf.append(getNonBorrowerDAPFiltersQueryByTeam(team, sqlParams));
		}

		buf.append(" ) ");

		return buf.toString();
	}

	public static String getNonBorrowerDAPFiltersQueryByTeam(final ITeam team, SQLParameter sqlParams) {
		Validate.notNull(team, "ITeam team must not be null.");

		StringBuffer buf = new StringBuffer();

		long teamId = team.getTeamID();

		buf.append(" OR ( sci_le_sub_profile.cms_non_borrower_ind = 'Y' ");
		buf.append(" AND sci_le_sub_profile.cms_le_sub_profile_id ");
		buf.append(" IN ( Select sub_pf_id from co_borrower_location_view where ");
		buf.append("(cus_loc IN ");
		buf.append(COUNTRY_BY_TEAM_FILTER_SQL);
		sqlParams.addLong(new Long(teamId));

		buf.append(") OR ( lmt_loc IN");
		buf.append(COUNTRY_BY_TEAM_FILTER_SQL);
		sqlParams.addLong(new Long(teamId));

		buf.append(" AND lmt_org IN ");
		buf.append(ORGANISATION_BY_TEAM_FILTER_SQL);
		sqlParams.addLong(new Long(teamId));

		buf.append(" ) ) )");

		return buf.toString();
	}

	/*
	 * @deprecated Use getLimitAndSecurityDAPFiltersQueryByTeam
	 */
	public static String getLimitSecurityDAPFilterQueryByCountriesAndOrganisations(String[] countries,
			String[] organisations, SQLParameter sqlParams, boolean isIncludeNonBorrowerFilter) {
		StringBuffer buf = new StringBuffer();

		buf.append(" AND ( sci_lsp_lmt_profile.cms_lsp_lmt_profile_id ");
		buf.append(" IN ( SELECT lmtprofile from borrower_location_view WHERE ");
		buf.append("(cms_orig_country ");
		CommonUtil.buildSQLInList(countries, buf, sqlParams.asList());

		buf.append(" AND prof_org ");
		CommonUtil.buildSQLInList(organisations, buf, sqlParams.asList());

		buf.append(") OR ( limit_country ");
		CommonUtil.buildSQLInList(countries, buf, sqlParams.asList());

		buf.append(" AND limit_org ");
		CommonUtil.buildSQLInList(organisations, buf, sqlParams.asList());

		buf.append(") OR (security_location ");
		CommonUtil.buildSQLInList(countries, buf, sqlParams.asList());

		buf.append(" AND security_organisation ");
		CommonUtil.buildSQLInList(organisations, buf, sqlParams.asList());

		buf.append(" OR collab_cc_country ");
		CommonUtil.buildSQLInList(countries, buf, sqlParams.asList());

		buf.append(") OR (co_bo_lmt_loc ");
		CommonUtil.buildSQLInList(countries, buf, sqlParams.asList());

		buf.append(" AND co_bo_lmt_org ");
		CommonUtil.buildSQLInList(organisations, buf, sqlParams.asList());

		buf.append(" ) ) ");

		if (isIncludeNonBorrowerFilter) {
			buf.append(getNonBorrowerDAPFilterQueryByCountriesAndOrgnisations(countries, organisations, sqlParams));
		}

		buf.append(" ) ");

		return buf.toString();
	}

	/*
	 * @deprecated Use getNonBorrowerDAPFiltersQueryByTeam
	 */
	public static String getNonBorrowerDAPFilterQueryByCountriesAndOrgnisations(String[] countries,
			String[] organisations, SQLParameter sqlParams) {
		StringBuffer buf = new StringBuffer();

		buf.append(" OR ( sci_lsp_sub_profile.cms_non_borrower_ind = 'Y' ");
		buf.append(" AND sci_le_sub_profile.cms_le_sub_profile_id");
		buf.append(" IN ( SELECT sub_pf_id FROM co_borrower_location_view WHERE ");

		buf.append("(cus_loc ");
		CommonUtil.buildSQLInList(countries, buf, sqlParams.asList());

		buf.append(" AND cus_orga ");
		CommonUtil.buildSQLInList(organisations, buf, sqlParams.asList());

		buf.append(") OR ( lmt_loc ");
		CommonUtil.buildSQLInList(countries, buf, sqlParams.asList());

		buf.append(" AND lmt_org ");
		CommonUtil.buildSQLInList(organisations, buf, sqlParams.asList());

		buf.append(" ) ) )");

		return buf.toString();
	}
}