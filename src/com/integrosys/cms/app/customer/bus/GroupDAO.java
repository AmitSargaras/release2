/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/GroupDAO.java,v 1.6 2005/09/19 09:02:07 czhou Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * DAO for collateral.
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/09/19 09:02:07 $ Tag: $Name: $
 */
public class GroupDAO {
	private DBUtil dbUtil;

	/**
	 * Default Constructor
	 */
	public GroupDAO() {
	}

	public SearchResult searchGroup(GroupSearchCriteria criteria) throws SearchDAOException {
		long gid = 0;

		if ((criteria.getGroupID() != null) && !criteria.getGroupID().trim().equals("")) {
			gid = Long.parseLong(criteria.getGroupID());
		}
		String theSQL = getGroupLeForGroupQuery(gid, criteria.getGroupName());
		int startIndex = criteria.getStartIndex();

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(theSQL);
			ResultSet rs = dbUtil.executeQuery();
			while ((startIndex-- > 0) && rs.next()) {
				;
			}
			Collection retcol = processTrxResultSet(rs, criteria.getNItems());
			return new SearchResult(criteria.getStartIndex(), 0, retcol.size() + criteria.getStartIndex(), retcol);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting collateral types: ", e);
		}
		finally {
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

	private Collection processTrxResultSet(ResultSet rs, int countRequired) throws SQLException {
		HashMap transactions = new HashMap();
		Collection al = new ArrayList();
		while (rs.next()) {
			if (countRequired != 0) {
				if (countRequired == al.size()) {
					break;
				}
			}
			OBGroupSearchResult sr = new OBGroupSearchResult();
			sr.setParentGroupID(rs.getString("GMP_GRP_ID"));
			sr.setParentGroupName(rs.getString("PARENTGRP"));
			sr.setGroupID(rs.getString("GMP_CHILD_GRP_ID"));
			sr.setGroupName(rs.getString("GRP_NAME"));
			sr.setLegalID(rs.getString("GLL_CHILD_LE_ID"));
			sr.setLegalName(rs.getString("LECHILD"));
			sr.setLegalParentID(rs.getString("GLL_PRNT_LE_ID"));
			sr.setLegalParentName(rs.getString("PARENTLE"));
			String relstatus = rs.getString("GLL_REL_STATUS_VALUE");
			if ((relstatus != null) && !relstatus.trim().equals("")) {
				sr.setRelationshipStatus(CommonDataSingleton.getCodeCategoryLabelByValue(
						ICMSConstant.GROUP_RELATIONSHIP_CODE, relstatus));
			}
			sr.setOwnershipPercent(rs.getString("GLL_OWNSP_PERCENT"));
			al.add(sr);
		}
		return al;
	}

	private String getGroupLeForGroupQuery(long groupID, String groupName) {
		String query = "SELECT GRPMAP.GMP_GRP_ID, PROPRT.GRP_NAME AS PARENTGRP, GRPMAP.GMP_CHILD_GRP_ID, PROCHD.GRP_NAME, "
				+ "LELEMAP.GLL_PRNT_LE_ID, LE.LMP_LONG_NAME AS PARENTLE, LELEMAP.GLL_CHILD_LE_ID, "
				+ "LECHILD.LMP_LONG_NAME AS LECHILD, LELEMAP.GLL_REL_STATUS_VALUE, LELEMAP.GLL_OWNSP_PERCENT "
				+ "FROM SCI_GRP_MAP GRPMAP, SCI_GRP_PROFILE PROCHD, SCI_GRP_PROFILE PROPRT, SCI_GRP_LE_LE_MAP LELEMAP, "
				+ "SCI_LE_MAIN_PROFILE LE, SCI_LE_MAIN_PROFILE LECHILD WHERE PROCHD.GRP_ID = GRPMAP.GMP_CHILD_GRP_ID "
				+ "AND GRPMAP.GMP_GRP_ID = PROPRT.GRP_ID AND GRPMAP.GMP_CHILD_GRP_ID = LELEMAP.GLL_GRP_ID "
				+ "AND LELEMAP.GLL_PRNT_LE_ID = LE.LMP_LE_ID AND LELEMAP.GLL_CHILD_LE_ID = LECHILD.LMP_LE_ID "
				+ "AND GMP_GRP_ID IN (SELECT GMP_GRP_ID FROM SCI_GRP_MAP START WITH "
				+ getGroupIDFilter(groupID, true)
				+ getGroupNameFilter(groupName, true)
				+ " CONNECT BY PRIOR gmp_child_grp_id = gmp_grp_id) "
				+ "UNION "
				+ "SELECT GRPMAP.GMP_GRP_ID, PROPRT.GRP_NAME AS PARENTGRP, GRPMAP.GMP_CHILD_GRP_ID, PROCHD.GRP_NAME, "
				+ "LEMAP.GLE_LE_ID AS GLL_PRNT_LE_ID, LE.LMP_LONG_NAME AS PARENTLE, 0 AS GLL_CHILD_LE_ID, '' AS LECHILD, "
				+ "'' AS GLL_REL_STATUS_VALUE, 0 AS GLL_OWNSP_PERCENT FROM SCI_GRP_MAP GRPMAP, "
				+ "SCI_GRP_PROFILE PROCHD, SCI_GRP_PROFILE PROPRT, SCI_GRP_LE_MAP LEMAP, SCI_LE_MAIN_PROFILE LE "
				+ "WHERE PROCHD.GRP_ID = GRPMAP.GMP_CHILD_GRP_ID AND PROPRT.GRP_ID = GRPMAP.GMP_GRP_ID "
				+ "AND GRPMAP.GMP_CHILD_GRP_ID = LEMAP.GLE_GRP_ID AND LEMAP.GLE_LE_ID = LE.LMP_LE_ID "
				+ "AND GMP_GRP_ID IN (SELECT gmp_grp_id FROM SCI_GRP_MAP START WITH "
				+ getGroupIDFilter(groupID, true)
				+ getGroupNameFilter(groupName, true)
				+ " CONNECT BY PRIOR gmp_child_grp_id = gmp_grp_id) "
				+ "UNION "
				+ "SELECT PRO.GRP_ID AS GMP_GRP_ID, PRO.GRP_NAME AS PARENTGRP, 0 AS GMP_CHILD_GRP_ID, '' AS GRP_NAME, "
				+ "LELEMAP.GLL_PRNT_LE_ID, LE.LMP_LONG_NAME AS PARENTLE, LELEMAP.GLL_CHILD_LE_ID, "
				+ "LECHILD.LMP_LONG_NAME AS LECHILD, LELEMAP.GLL_REL_STATUS_VALUE, LELEMAP.GLL_OWNSP_PERCENT "
				+ "FROM SCI_GRP_PROFILE PRO, SCI_GRP_LE_LE_MAP LELEMAP, SCI_LE_MAIN_PROFILE LE, SCI_LE_MAIN_PROFILE LECHILD "
				+ "WHERE PRO.GRP_ID = LELEMAP.GLL_GRP_ID AND LELEMAP.GLL_PRNT_LE_ID = LE.LMP_LE_ID "
				+ "AND LELEMAP.GLL_CHILD_LE_ID = LECHILD.LMP_LE_ID "
				+ getGroupIDFilter(groupID, false)
				+ getGroupNameFilter(groupName, false)
				+ " "
				+ "UNION "
				+ "SELECT PRO.GRP_ID AS GMP_GRP_ID, PRO.GRP_NAME AS PARENTGRP, 0 AS GMP_CHILD_GRP_ID, '' AS GRP_NAME, "
				+ "LEMAP.GLE_LE_ID AS GLL_PRNT_LE_ID, LE.LMP_LONG_NAME AS PARENTLE, 0 AS GLL_CHILD_LE_ID, '' AS LECHILD, '' AS GLL_REL_STATUS_VALUE, 0 AS GLL_OWNSP_PERCENT FROM SCI_GRP_PROFILE PRO, "
				+ "SCI_GRP_LE_MAP LEMAP, SCI_LE_MAIN_PROFILE LE WHERE PRO.GRP_ID = LEMAP.GLE_GRP_ID "
				+ "AND LEMAP.GLE_LE_ID = LE.LMP_LE_ID "
				+ getGroupIDFilter(groupID, false)
				+ getGroupNameFilter(groupName, false) + " ";

		return query;
	}

	private String getGroupIDFilter(long groupID, boolean isConnect) {
		if (groupID == 0) {
			return " ";
		}
		if (isConnect) {
			return "GMP_GRP_ID = " + groupID;
		}
		else {
			return "AND PRO.GRP_ID = " + groupID;
		}
	}

	private String getGroupNameFilter(String groupName, boolean isConnect) {
		if ((groupName == null) || groupName.trim().equals("")) {
			return " ";
		}
		if (isConnect) {
			return "PROPRT.GRP_NAME LIKE '" + groupName + "%'";
		}
		else {
			return "AND PRO.GRP_NAME LIKE '" + groupName + "%'";
		}
	}
}
