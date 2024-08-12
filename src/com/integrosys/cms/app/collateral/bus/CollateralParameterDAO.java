/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/CollateralParameterDAO.java,v 1.11 2005/09/19 09:02:07 czhou Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * DAO for security parameter.
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2005/09/19 09:02:07 $ Tag: $Name: $
 */
public class CollateralParameterDAO implements ICollateralParameterDAO {
	private DBUtil dbUtil;

	private static String SELECT_COLLATERAL_PARAM = "select ID, CMS_SECURITY_PARAMETER.GROUP_ID, COUNTRY_ISO_CODE, CMS_SECURITY_PARAMETER.SECURITY_SUB_TYPE_ID, "
			+ "CMS_SECURITY_PARAMETER.VERSION_TIME, CMS_SECURITY_PARAMETER.STATUS, THRESHOLD_PERCENT, "
			+ "VALUATION_FREQUENCY_UNIT, VALUATION_FREQUENCY, CMS_SECURITY_SUB_TYPE.SUBTYPE_NAME, "
			+ "CMS_SECURITY_SUB_TYPE.SUBTYPE_DESCRIPTION, CMS_SECURITY_SUB_TYPE.SECURITY_TYPE_NAME, "
			+ "CMS_SECURITY_SUB_TYPE.MAX_PERCENT from CMS_SECURITY_PARAMETER, CMS_SECURITY_SUB_TYPE "
			+ "where CMS_SECURITY_PARAMETER.SECURITY_SUB_TYPE_ID = CMS_SECURITY_SUB_TYPE.SECURITY_SUB_TYPE_ID";

	private static String SELECT_COLPARAM_MAX_GROUP_ID = "select max (CMS_SECURITY_PARAMETER.GROUP_ID) from CMS_SECURITY_PARAMETER, CMS_SECURITY_SUB_TYPE "
			+ "where CMS_SECURITY_PARAMETER.SECURITY_SUB_TYPE_ID = CMS_SECURITY_SUB_TYPE.SECURITY_SUB_TYPE_ID";

	// Cynthia: updated to include where condition
	// search found no other usage of this except in "getCRP"
	private static String SELECT_CRP = "select THRESHOLD_PERCENT from CMS_SECURITY_PARAMETER "
			+ "where COUNTRY_ISO_CODE = ? and SECURITY_SUB_TYPE_ID = ?";

	// table name and column names for security parameter.
	public static final String COLPARAM_TABLE = getColParamTableName();

	public static final String COLPARAM_ID = "ID";

	public static final String COLPARAM_GROUP_ID = "GROUP_ID";

	public static final String COLPARAM_COUNTRY = "COUNTRY_ISO_CODE";

	public static final String COLPARAM_SUBTYPE = "SECURITY_SUB_TYPE_ID";

	public static final String COLPARAM_THRESHOLD = "THRESHOLD_PERCENT";

	public static final String COLPARAM_VAL_FREQ_UNIT = "VALUATION_FREQUENCY_UNIT";

	public static final String COLPARAM_VAL_FREQ = "VALUATION_FREQUENCY";

	public static final String COLPARAM_VERSION_TIME = "VERSION_TIME";

	public static final String COLPARAM_STATUS = "STATUS";

	// table name and column name for security sub type.
	protected static final String COL_SUBTYPE_TABLE = CollateralDAO.COL_SUBTYPE_TABLE;

	protected static final String COL_TYPE_CODE = CollateralDAO.COL_TYPE_CODE;

	protected static final String COL_SUBTYPE_CODE = CollateralDAO.COL_SUBTYPE_CODE;

	protected static final String COL_SUBTYPE_NAME = CollateralDAO.COL_SUBTYPE_NAME;

	protected static final String COL_SUBTYPE_DESC = CollateralDAO.COL_SUBTYPE_DESC;

	protected static final String COL_SUBTYPE_MAX = CollateralDAO.COL_SUBTYPE_MAX_PERCENT;

	protected static final String COL_TYPE_NAME = CollateralDAO.COL_TYPE_NAME;

	/**
	 * Default Constructor
	 */
	public CollateralParameterDAO() {
	}

	/**
	 * Get collateral parameter table name.
	 * 
	 * @return table name of security parameter
	 */
	protected static String getColParamTableName() {
		return "CMS_SECURITY_PARAMETER";
	}

	/**
	 * Get security parameter based on the country code and security type code.
	 * 
	 * @param country country code
	 * @param colType security type code
	 * @return a list of security parameters
	 * @throws SearchDAOException on error searching the security parameters
	 */
	public ICollateralParameter[] getCollateralParameters(String country, String colType) throws SearchDAOException {
		String sql = constructColParamSQL(country, colType);

		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processColParamResultSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting collateral types: ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get security parameter based on the country code and security type code.
	 * 
	 * @param groupID group id
	 * @return a list of security parameters
	 * @throws SearchDAOException on error searching the security parameters
	 */
	public ICollateralParameter[] getCollateralParameters(long groupID) throws SearchDAOException {
		String sql = constructColParamByGroupIDSQL(groupID);

		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processColParamResultSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting collateral types: ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get CRP value given the country code and security subtype code.
	 * 
	 * @param countryCode country code
	 * @param subTypeCode security subtype code
	 * @return crp value
	 * @throws SearchDAOException on error getting the crp value
	 */
	public double getCRP(String countryCode, String subTypeCode) throws SearchDAOException {
		// String sql = constructCRPSQL (countryCode, subTypeCode);

		try {
			// Cynthia: updated under instructions of Xu Bin for sake of
			// performance
			String sql = SELECT_CRP;
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, countryCode); // Cynthia: updated 'cos using
												// prepared statments instead
			dbUtil.setString(2, subTypeCode);
			ResultSet rs = dbUtil.executeQuery();
			if (rs.next()) {
				return rs.getDouble(COLPARAM_THRESHOLD);
			}
			return 0;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting CRP!", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Helper method to construct query for crp value.
	 * 
	 * @param countryCode country code
	 * @param subTypeCode security subtype code
	 * @return sql query
	 */
	/*
	 * //Cynthia: Updated, no longer required. This has been converted into a
	 * static String. see SELECT_CRP. private String constructCRPSQL (String
	 * countryCode, String subTypeCode) { StringBuffer buf = new StringBuffer();
	 * buf.append (SELECT_CRP); buf.append (" where "); buf.append
	 * (COLPARAM_COUNTRY); buf.append (" = '"); buf.append (countryCode);
	 * buf.append ("' and "); buf.append (COLPARAM_SUBTYPE); buf.append
	 * (" = '"); buf.append (subTypeCode); buf.append ("'"); return
	 * buf.toString(); }
	 */

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
		buf.append(SELECT_COLLATERAL_PARAM);
		buf.append(" and ");
		buf.append(COLPARAM_TABLE);
		buf.append(".");
		buf.append(COLPARAM_GROUP_ID);
		buf.append(" = (");
		buf.append(SELECT_COLPARAM_MAX_GROUP_ID);
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
		buf.append(COLPARAM_TABLE);
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
		buf.append(SELECT_COLLATERAL_PARAM);
		buf.append(" and ");
		buf.append(COLPARAM_TABLE);
		buf.append(".");
		buf.append(COLPARAM_GROUP_ID);
		buf.append(" = ");
		buf.append(groupID);
		buf.append(" order by ");
		buf.append(COLPARAM_TABLE);
		buf.append(".");
		buf.append(COLPARAM_SUBTYPE);
		return buf.toString();
	}

	/**
	 * Helper method to process the result set of security parameter.
	 * 
	 * @param rs result set
	 * @return a list of collateral parameters
	 * @throws SQLException on error processing the result set
	 */
	private ICollateralParameter[] processColParamResultSet(ResultSet rs) throws SQLException {
		ArrayList arrList = new ArrayList();

		while (rs.next()) {
			OBCollateralParameter colParam = new OBCollateralParameter();
			colParam.setId(rs.getLong(COLPARAM_ID));
			colParam.setGroupId(rs.getLong(COLPARAM_GROUP_ID));
			colParam.setCountryIsoCode(rs.getString(COLPARAM_COUNTRY));
			colParam.setSecuritySubTypeId(rs.getString(COLPARAM_SUBTYPE));
			BigDecimal thresholdBD = (BigDecimal) rs.getObject(COLPARAM_THRESHOLD);
			double threshold = ICMSConstant.DOUBLE_INVALID_VALUE;
			if (thresholdBD != null) {
				threshold = thresholdBD.doubleValue();
			}

			colParam.setThresholdPercent(threshold);
			colParam.setValuationFrequencyUnit(rs.getString(COLPARAM_VAL_FREQ_UNIT));
			colParam.setValuationFrequency(rs.getInt(COLPARAM_VAL_FREQ));
			colParam.setMaxValue(rs.getDouble(COL_SUBTYPE_MAX));
			colParam.setSubTypeCode(colParam.getSecuritySubTypeId());
			colParam.setSubTypeDesc(rs.getString(COL_SUBTYPE_DESC));
			colParam.setSubTypeName(rs.getString(COL_SUBTYPE_NAME));
			colParam.setTypeName(rs.getString(COL_TYPE_NAME));
			colParam.setVersionTime(rs.getLong(COLPARAM_VERSION_TIME));
			colParam.setStatus(rs.getString(COLPARAM_STATUS));
			arrList.add(colParam);
		}
		return (OBCollateralParameter[]) arrList.toArray(new OBCollateralParameter[0]);
	}

	/**
	 * Helper method to clean up database resources.
	 * 
	 * @param dbUtil database utility object
	 * @throws SearchDAOException error in cleaning up DB resources
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
