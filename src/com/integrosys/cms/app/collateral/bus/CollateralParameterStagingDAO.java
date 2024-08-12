/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/CollateralParameterStagingDAO.java,v 1.9 2005/09/19 09:02:07 czhou Exp $
 */
package com.integrosys.cms.app.collateral.bus;

/**
 * DAO for staging security parameter.
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/09/19 09:02:07 $ Tag: $Name: $
 */
public class CollateralParameterStagingDAO extends CollateralParameterDAO {
	private static String SELECT_COLPARAM_STAGING = "select ID, CMS_STAGE_SECURITY_PARAMETER.GROUP_ID, COUNTRY_ISO_CODE, "
			+ "CMS_STAGE_SECURITY_PARAMETER.SECURITY_SUB_TYPE_ID, CMS_STAGE_SECURITY_PARAMETER.VERSION_TIME, "
			+ "CMS_STAGE_SECURITY_PARAMETER.STATUS, THRESHOLD_PERCENT, VALUATION_FREQUENCY_UNIT, VALUATION_FREQUENCY, "
			+ "CMS_SECURITY_SUB_TYPE.SUBTYPE_NAME, CMS_SECURITY_SUB_TYPE.SUBTYPE_DESCRIPTION, "
			+ "CMS_SECURITY_SUB_TYPE.SECURITY_TYPE_NAME, CMS_SECURITY_SUB_TYPE.MAX_PERCENT "
			+ "from CMS_STAGE_SECURITY_PARAMETER, CMS_SECURITY_SUB_TYPE "
			+ "where CMS_STAGE_SECURITY_PARAMETER.SECURITY_SUB_TYPE_ID = CMS_SECURITY_SUB_TYPE.SECURITY_SUB_TYPE_ID";

	private static String SELECT_COLPARAM_STAGING_MAX_GROUP_ID = "select max (CMS_STAGE_SECURITY_PARAMETER.GROUP_ID) from CMS_STAGE_SECURITY_PARAMETER, CMS_SECURITY_SUB_TYPE "
			+ "where CMS_STAGE_SECURITY_PARAMETER.SECURITY_SUB_TYPE_ID = CMS_SECURITY_SUB_TYPE.SECURITY_SUB_TYPE_ID";

	// table name and column names for security parameter.
	public static final String COLPARAM_STAGING_TABLE = "CMS_STAGE_SECURITY_PARAMETER";

	/**
	 * Default Constructor
	 */
	public CollateralParameterStagingDAO() {
		super();
	}

	/**
	 * Helper method to construct query for security parameter.
	 * 
	 * @return sql query
	 */
	protected String constructColParamSQL(String country, String colType) {
		if (country == null) {
			country = "";
		}
		if (colType == null) {
			colType = "";
		}

		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_COLPARAM_STAGING);
		buf.append(" and ");
		buf.append(COLPARAM_STAGING_TABLE);
		buf.append(".");
		buf.append(COLPARAM_GROUP_ID);
		buf.append(" = (");
		buf.append(SELECT_COLPARAM_STAGING_MAX_GROUP_ID);
		buf.append(" and ");
		buf.append(COLPARAM_COUNTRY);
		buf.append(" = '");
		buf.append(country);
		buf.append("' and ");
		buf.append(COL_TYPE_CODE);
		buf.append(" = '");
		buf.append(colType);
		buf.append("'");
		buf.append(")");
		buf.append(" and ");
		buf.append(COLPARAM_COUNTRY);
		buf.append(" = '");
		buf.append(country);
		buf.append("' and ");
		buf.append(COL_TYPE_CODE);
		buf.append(" = '");
		buf.append(colType);
		buf.append("' order by ");
		buf.append(COLPARAM_STAGING_TABLE);
		buf.append(".");
		buf.append(COLPARAM_SUBTYPE);

		return buf.toString();
	}

	/**
	 * Helper method to construct query for security parameter by group id.
	 * 
	 * @return sql query
	 */
	protected String constructColParamByGroupIDSQL(long groupID) {
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_COLPARAM_STAGING);
		buf.append(" and ");
		buf.append(COLPARAM_STAGING_TABLE);
		buf.append(".");
		buf.append(COLPARAM_GROUP_ID);
		buf.append(" = ");
		buf.append(groupID);
		buf.append(" order by ");
		buf.append(COLPARAM_STAGING_TABLE);
		buf.append(".");
		buf.append(COLPARAM_SUBTYPE);
		return buf.toString();
	}
}
