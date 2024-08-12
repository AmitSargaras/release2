/*
 * Created on Jun 4, 2004
 *
 */
package com.integrosys.cms.app.common.util.dataaccess;

/**
 * @author heju
 * 
 */
public interface IDAODescriptor {
	public final static String EMPTY_STRING = "";

	public final static String NONE_STRING = "'NONE'"; // with ''

	public final static String NEGATIVE_ONE = "-1";

	public final static String QUERYTAG = "QUERYTAG";

	public final static String SQLDELIMTER = "?";

	public final static String TEAM_TYPE_MEMBERSHIP_ID = "TEAM_TYPE_MEMBERSHIP_ID";

	public final static String LEGAL_NAME = "LEGAL_NAME";

	public final static String LEGAL_NAME_CONDITION = "LEGAL_NAME_CONDITION";

	public final static String ALLOWED_COUNTRIES = "ALLOWED_COUNTRIES";

	public final static String ALLOWED_ORGANIZATIONS = "ALLOWED_ORGANIZATIONS";

	public final static String ALLOWED_SEGMENTS = "ALLOWED_SEGMENTS";

	public final static String TRANSACTION_TYPES = "TRANSACTION_TYPES";

	public final static String TRANSACTION_TYPES_CONDITION = "TRANSACTION_TYPES_CONDITION";

	public final static String STATUS = "STATUS";

	public final static String STATUS_CONDITION = "STATUS_CONDITION";

	public final static String LIMIT_PROFILE_ID = "LIMIT_PROFILE_ID";

	public final static String LIMIT_PROFILE_ID_CONDITION = "LIMIT_PROFILE_ID_CONDITION";

	public final static String USER_ID = "USER_ID";

	public final static String USER_ID_CONDITION = "USER_ID_CONDITION";

	public final static String USER_ID_HISTORY_CONDITION = "USER_ID_HISTORY_CONDITION";

	public final static String TO_USER_ID = "TO_USER_ID";

	public final static String TO_USER_ID_CONDITION = "TO_USER_ID_CONDITION";

	public final static String TRANSACTION_DATE = "TRANSACTION_DATE";

	public final static String ROWNUM_BEGIN = "ROWNUM_BEGIN";

	public final static String PAGE_SIZE = "PAGE_SIZE";

	public final static String NextRouteListTAG = "NextRouteList";

	public final static String TRANSACTION_ID = "TRANSACTION_ID";

	public String getQuery(String queryID) throws DataAccessException;

}
