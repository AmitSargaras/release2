/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/dataprotection/bus/DataAccessProfileDAO.java,v 1.2 2006/03/17 07:20:10 hshii Exp $
 */
package com.integrosys.cms.app.dataprotection.bus;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Contains data access logic for the data access profile table.
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.2 $
 * @since $Date: 2006/03/17 07:20:10 $ Tag: $Name: $
 */
public class DataAccessProfileDAO {
	private static String SELECT_DAP = "SELECT dap.* ,c.* FROM CMS_DATA_ACCESS dap LEFT OUTER JOIN CMS_DAP_COUNTRY c ON c.DATA_ACCESS_ID = dap.DATA_ACCESS_ID";

	/**
	 * Get all the access function records.
	 * 
	 * @return a List of access function records
	 */
	public IDataAccessProfile[] getDataAccessProfile(String type, String subtype, long roleType) {
		DBUtil dbUtil = null;
		ArrayList list = new ArrayList();

		try {
			dbUtil = new DBUtil();
			String selectSQL = getDataAccessProfileSQL(type, subtype, roleType);
			dbUtil.setSQL(selectSQL);
			setDAPParam(dbUtil, type, subtype, roleType);
			ResultSet rs = dbUtil.executeQuery();
			return processDAPResultSet(type, rs);
		}
		catch (Exception e) {
			DefaultLogger.warn(this, "Exception when querying DATA ACCESS table!", e);
			// continue to return.
		}
		finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "Exception when closing DB Util!", e);
				// Continue to return.
			}
		}
		return (IDataAccessProfile[]) list.toArray(new IDataAccessProfile[0]);
	}

	/**
	 * Helper method to get data access profile sql given its criteria.
	 * 
	 * @param type module type
	 * @param subtype module subtype
	 * @param roleType user team membership type id
	 * @return data access profile sql
	 */
	private String getDataAccessProfileSQL(String type, String subtype, long roleType) {
		StringBuffer buf = new StringBuffer(SELECT_DAP);
		buf.append(" where ");
		if (type != null) {
			buf.append(" dap.MODULE_TYPE = ? and ");
		}
		if (subtype != null) {
			buf.append(" dap.MODULE_SUBTYPE = ? and ");
		}
		if (roleType != ICMSConstant.LONG_INVALID_VALUE) {
			buf.append(" dap.TEAM_TYPE_MEMBERSHIP_ID = ? and ");
		}
		if (buf.toString().endsWith(" where ")) {
			int size = buf.length();
			buf.delete(size - 6, size);
		}
		if (buf.toString().endsWith(" and ")) {
			int size = buf.length();
			buf.delete(size - 4, size);
		}
		buf.append(" order by MODULE_TYPE, MODULE_SUBTYPE, TEAM_TYPE_MEMBERSHIP_ID, FIELD_NAME ");
		return buf.toString();
	}

	/**
	 * Helper method to prepare the sql statement.
	 * 
	 * @param dbUtil of type DBUtil
	 * @param type of type String
	 * @param subtype of type String
	 * @param roleType of type long
	 * @throws Exception on any error encountered
	 */
	private void setDAPParam(DBUtil dbUtil, String type, String subtype, long roleType) throws Exception {
		int idx = 1;
		if (type != null) {
			dbUtil.setString(idx++, type);
		}
		if (subtype != null) {
			dbUtil.setString(idx++, subtype);
		}
		if (roleType != ICMSConstant.LONG_INVALID_VALUE) {
			dbUtil.setLong(idx++, roleType);
		}
	}

	/**
	 * Helper method to process DAP result set.
	 * 
	 * @param type module type
	 * @param rs DAP records
	 * @throws Exception on any errors encountered
	 */
	private IDataAccessProfile[] processDAPResultSet(String type, ResultSet rs) throws Exception {
		HashMap rsMap = new HashMap();
		ArrayList list = new ArrayList();
		boolean isCol = (type != null) && type.equals(ICMSConstant.INSTANCE_COLLATERAL) ? true : false;

		while (rs.next()) {
			String dapID = rs.getString("DATA_ACCESS_ID");
			IDataAccessProfile dap = (IDataAccessProfile) rsMap.get(dapID);
			if (dap == null) {
				if (isCol) {
					dap = new OBCollateralMetaData();
				}
				else {
					dap = new OBDataAccessProfile();
				}
				rsMap.put(dapID, dap);
				list.add(dap);
			}
			dap.setModuleType(rs.getString("MODULE_TYPE"));
			dap.setModuleSubType(rs.getString("MODULE_SUBTYPE"));
			dap.setFieldName(rs.getString("FIELD_NAME"));
			BigDecimal roleType = rs.getBigDecimal("TEAM_TYPE_MEMBERSHIP_ID");
			dap.setTeamTypeMshipID(roleType == null ? ICMSConstant.LONG_INVALID_VALUE : roleType.longValue());

			if (rs.getString("DAP_COUNTRY_ID") != null) {
				String ctry = rs.getString("GRANTED_COUNTRY");
				String org = rs.getString("GRANTED_ORGANISATION");
				if ((ctry != null) || (org != null)) {
					IBookingLocation loc = new OBBookingLocation();
					loc.setCountryCode(ctry);
					loc.setOrganisationCode(org);
					dap.getGrantedBkgLoc().add(loc);
				}
				ctry = rs.getString("DENIED_COUNTRY");
				org = rs.getString("DENIED_ORGANISATION");
				if ((ctry != null) || (org != null)) {
					IBookingLocation loc = new OBBookingLocation();
					loc.setCountryCode(ctry);
					loc.setOrganisationCode(org);
					dap.getDeniedBkgLoc().add(loc);
				}
			}
		}
		rs.close();
		if (isCol) {
			return (ICollateralMetaData[]) list.toArray(new OBCollateralMetaData[0]);
		}
		else {
			return (IDataAccessProfile[]) list.toArray(new OBDataAccessProfile[0]);
		}
	}

	/**
	 * Check whether a function/module is accessible by multiple role by country
	 * and organisation
	 */

	public boolean isMultipleRoleAccessible(String type, String subtype, String ctryCode, String orgCode) {
		String sql = "SELECT COUNT(DISTINCT team_type_membership_id) role_count "
				+ "FROM CMS_DATA_ACCESS da, CMS_DAP_COUNTRY dc " + "WHERE module_type = ? AND module_subtype = ? "
				+ "AND da.data_access_id = dc.data_access_id "
				+ "AND (dc.GRANTED_COUNTRY IS NULL OR dc.granted_country = ?) "
				+ "AND (dc.granted_organisation IS NULL OR dc.granted_organisation = ?)";
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, type);
			dbUtil.setString(2, subtype);
			dbUtil.setString(3, ctryCode);
			dbUtil.setString(4, orgCode);

			ResultSet rs = dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt("role_count");
			rs.close();
			if (count > 1) {
				return true;
			}

		}
		catch (Exception e) {
			DefaultLogger.warn(this, "Exception when querying DATA ACCESS table!", e);
			// continue to return.
		}
		finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "Exception when closing DB Util!", e);
				// Continue to return.
			}
		}
		return false;
	}
}
