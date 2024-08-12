/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/user/bus/StdUserDAO.java,v 1.9 2006/11/17 10:04:33 wltan Exp $
 */
package com.integrosys.cms.app.user.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMember;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.notification.bus.OBNotificationRecipient;
import com.integrosys.component.user.app.bus.CommonUserSearchCriteria;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.bus.OBRoleType;
import com.integrosys.component.user.app.bus.OBRoleTypeSearchCriteria;
import com.integrosys.component.user.app.bus.OBSearchCommonUser;
import com.integrosys.component.user.app.bus.RoleTypeSearchCriteria;
import com.integrosys.component.user.app.bus.UserException;
import com.integrosys.component.bizstructure.app.bus.TeamFilteringHelperDAO;

/**
 * DAO for User-related data access.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/11/17 10:04:33 $ Tag: $Name: $
 */
public class StdUserDAO {
	/* Multiple-role related Constants */
	public static final String CPC_TEAM_TYPE_ID = "10";

	public static final String CPC_CUSTODIAN_TEAM_TYPE_ID = "9";

	private DBUtil dbUtil;

	private String ROLETYPE_TABLE;

	private String USER_TABLE;

	private String TEAM_TABLE;

	private String TEAM_MEMBERSHIP_TABLE;

	private String TEAM_MEMBER_TABLE;

	private String AUTHENTICATION_TABLE;

	private String LOGIN_AUDIT_TABLE;

	private boolean MAKER_CHECKER_SAME_USER;

	private static final String SystemHandOff = "integrosys.systemHandOff.teamId";
	
	public StdUserDAO() {
		ROLETYPE_TABLE = PropertyManager.getValue("component.user.table.roletype");
		USER_TABLE = PropertyManager.getValue("component.user.table.user");
		TEAM_TABLE = PropertyManager.getValue("component.team.table.team");
		TEAM_MEMBERSHIP_TABLE = PropertyManager.getValue("component.team.table.membership");
		TEAM_MEMBER_TABLE = PropertyManager.getValue("component.team.table.member");
		AUTHENTICATION_TABLE = PropertyManager.getValue("component.user.table.authentication");
		LOGIN_AUDIT_TABLE = PropertyManager.getValue("component.user.table.login.audit");

		MAKER_CHECKER_SAME_USER = PropertyManager.getBoolean(ICMSConstant.MAKER_CHECKER_SAME_USER,false);
	}

	/**
	 * get custodian maker by team id
	 * @param teamList
	 * @return
	 * @throws SearchDAOException
	 */
	public ArrayList getCustodianMakerByTeamID(ArrayList teamList) throws SearchDAOException {
		ArrayList userList = new ArrayList();
		String teamIDArr = "";
		for (int i = 0; i < teamList.size(); i++) {
			ITeam team = (ITeam) teamList.get(i);
			if (i != teamList.size() - 1) {
				teamIDArr += team.getTeamID() + ",";
			}
			else {
				teamIDArr += team.getTeamID();
			}
		}
		StringBuffer strBuff = new StringBuffer();
		strBuff
				.append("SELECT MEM.USER_ID,MEMBERSHIP.TEAM_ID FROM CMS_TEAM_MEMBER MEM, CMS_TEAM_MEMBERSHIP MEMBERSHIP ");
		strBuff
				.append("WHERE MEM.TEAM_MEMBERSHIP_ID = MEMBERSHIP.TEAM_MEMBERSHIP_ID AND MEMBERSHIP.TEAM_TYPE_MEMBERSHIP_ID = 2 ");
		strBuff.append("AND MEMBERSHIP.TEAM_ID IN (");
		strBuff.append(teamIDArr);
		strBuff.append(")");
		// DefaultLogger.debug(this,"sql is " + strBuff.toString());
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(strBuff.toString());
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				OBNotificationRecipient recipient = new OBNotificationRecipient();
				// recipient.setNotificationRecipientID(rs.getLong("USER_ID"));
				recipient.setUserID(new Long(rs.getLong("USER_ID")));
				recipient.setTeamID(new Long(rs.getLong("TEAM_ID")));
				userList.add(recipient);
			}
			return userList;
		}
		catch (DBConnectionException dbe) {
			throw new SearchDAOException(dbe);
		}
		catch (NoSQLStatementException ne) {
			throw new SearchDAOException(ne);
		}
		catch (SQLException se) {
			throw new SearchDAOException(se);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException(e);
			}
		}
	}

	public SearchResult searchUser(CommonUserSearchCriteria criteria) throws SearchDAOException {

		DefaultLogger.debug(this, "Accessing new StdUserDAO......");

		if (criteria == null) {
			throw new SearchDAOException("invalid parameter: CommonUserSearchCriteria is NULL");
		}

		StdUserSearchCriteria obj = (StdUserSearchCriteria) criteria.getCriteria();
		if (obj == null) {
			throw new SearchDAOException("OBCommonUserSearchCriteria - null");
		}

		// Added support for multiple-role cases
		String memshipTypeId = obj.getMemshipTypeId();

		String firstSort = criteria.getFirstSort();
		String secondSort = criteria.getSecondSort();

		int startIndex = criteria.getStartIndex();
		int nItems = criteria.getNItems();
		// String dbUsed = PropertyManager.getValue("databaseUsed");
		String sql, condition;
		condition = "FROM   " + ROLETYPE_TABLE + "  b , ( (" + TEAM_TABLE + " T LEFT OUTER JOIN "
				+ TEAM_MEMBERSHIP_TABLE + " MS  ON ( T.TEAM_ID = MS.TEAM_ID )) " + " LEFT OUTER JOIN "
				+ TEAM_MEMBER_TABLE + " M ON ( MS.TEAM_MEMBERSHIP_ID = M.TEAM_MEMBERSHIP_ID) ) " + " RIGHT OUTER JOIN "
				+ USER_TABLE + " a ON (a.USER_ID = M.USER_ID ) where a.STATUS <> '" + "D" + "' ";
//				+ " AND a.ROLETYPE_ID = b.ROLETYPE_ID ";
		if (MAKER_CHECKER_SAME_USER) {
			sql = "SELECT DISTINCT ";
			//countSQL = "SELECT COUNT(DISTINCT LOGIN_ID) ";
		}
		else {
			sql = "SELECT ";
			//countSQL = "SELECT COUNT(*) ";
		}
		sql += " LOGIN_ID, a.USER_ID, USER_NAME, DEPARTMENT, a.ROLETYPE_ID, ROLETYPE_NAME, T.TEAM_ID, T.ABBREVIATION  "
				+ condition;
		//countSQL += condition;
		//int numTotalRecords = getUserRecordCount(obj, countSQL, memshipTypeId);

		//DefaultLogger.debug(this, "<<<<< memshipTypeId: " + memshipTypeId);
		//DefaultLogger.debug(this, "<<< numTotalRecords: " + numTotalRecords);
		/*
		if (numTotalRecords == 0) {
			Vector list = new Vector();
			return new SearchResult(0, list.size(), 0, list);
		}
		 */
		ArrayList paramList = new ArrayList();
		// teamTypeId is used to identify if the user has multiple roles
		String search = searchUserSQL(obj, sql, memshipTypeId, paramList);
		StringBuffer strBuffer = new StringBuffer(search.trim());
		if ((firstSort != null) && !firstSort.equals("")) {
			strBuffer.append(" ORDER BY ");
			strBuffer.append(firstSort.trim());
			if ((secondSort != null) && !secondSort.equals("") && !secondSort.equalsIgnoreCase(firstSort)) {
				strBuffer.append(", ");
				strBuffer.append(secondSort.trim());
			}
		}
		int recordPerPage = nItems * 10;
		//TODO: Need to find the alternative syntax in oracle Anil
		/*int maxCount = (((int)(Math.floor((startIndex) / recordPerPage)) + 1) * recordPerPage) + 1 ;
		strBuffer.append(" OPTIMIZE FOR "+maxCount+" ROWS ");*/
		
		try {
			dbUtil = new DBUtil();
			println(strBuffer.toString());
			try {
				dbUtil.setSQL(strBuffer.toString());
				TeamFilteringHelperDAO.setSQLParams(paramList, dbUtil);
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query statement", e);
			}
			ResultSet rs = dbUtil.executeQuery();
			/*
			while ((startIndex-- > 0) && rs.next()) {
				;
			}
			*/
			return processResultSet(rs, nItems, startIndex, obj);
			//SearchResult searchresult = new SearchResult(startIndex, list.size(), numTotalRecords, list);
			//return searchresult;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new SearchDAOException("Exception from searchuser method ", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException("Exception from searchuser method ", e);
			}
		}
	}

	private SearchResult processResultSet(ResultSet rs, int countRequired, int startIndex, StdUserSearchCriteria obj) 
			throws SQLException {
		Vector list = new Vector();
		int count = 0;
		int recordPerPage = countRequired * 10;
		int maxCount = (((int)(Math.floor((startIndex) / recordPerPage)) + 1) * recordPerPage) + 1 ;
		
		boolean requireFiltering = (obj.getAssignmentType() == null && obj.getTeamID() > 0 );

		while (rs.next()) {
			if (count >= maxCount) break;
			
			if (!requireFiltering && (count < startIndex || count >= startIndex + countRequired)) {
				count++;
				continue;
			}

			if (requireFiltering) {
				long teamID = rs.getLong("TEAM_ID");
				if (hasAccessUserTeam(obj.getMaintainTeam(), teamID)) {
					if (count < startIndex || count >= startIndex + countRequired) {
						count++; 
						continue;
					}											
				} else {
					continue;
				}
			}
			
			OBSearchCommonUser usr = new OBSearchCommonUser();
			usr.setLoginID(rs.getString("LOGIN_ID"));
			usr.setUserID(rs.getLong("USER_ID"));
			usr.setUserName(rs.getString("USER_NAME"));
			usr.setBusinessUnitName(rs.getString("ABBREVIATION"));
			usr.setBusinessUnitID(rs.getString("TEAM_ID"));
			usr.setRoleTypeID(rs.getLong("ROLETYPE_ID"));
			usr.setRoleTypeName(rs.getString("ROLETYPE_NAME"));
			list.add(usr);

			// println("*********************RESULT SET SIZE******6**" +
			// list.size());
			/*
			if ((countRequired != 0) && (countRequired == list.size())) {
				break;
			}
			*/
			count++;
		}
		
		return new SearchResult(startIndex, list.size(), count, list);
	}

	private boolean hasAccessUserTeam(ITeam team, long teamID) throws SQLException {
		if (team.getCountryCodes() != null && team.getCountryCodes().length > 0 && 
				!TeamFilteringHelperDAO.hasAccessByCountry(team, teamID))
			return false;
		
		/*if (team.getCMSSegmentCodes() != null && team.getCMSSegmentCodes().length > 0 && 
				!TeamFilteringHelperDAO.hasAccessByCMSSegment(team, teamID))
			return false;*/
		
		if (team.getOrganisationCodes() != null && team.getOrganisationCodes().length > 0 && 
				!TeamFilteringHelperDAO.hasAccessByOrgCode(team, teamID))
			return false;		
		
		if (team.getOrgGroupCode() != null && team.getOrgGroupCode().length > 0 && 
				!TeamFilteringHelperDAO.hasAccessByOrgGroup(team, teamID))
			return false;	
		
		/*if (team.getSegmentCodes() != null && team.getSegmentCodes().length > 0 && 
				!TeamFilteringHelperDAO.hasAccessBySegment(team, teamID))
			return false;	*/
		
		return true;
	}
	/*
	private int getUserRecordCount(StdUserSearchCriteria obj, String countSQL, String memshipTypeId)
			throws SearchDAOException {
		String sql = searchUserSQL(obj, countSQL, memshipTypeId);
		DefaultLogger.debug(this, "SQL: " + sql);
		try {
			dbUtil = new DBUtil();
			// println(sql);
			try {
				dbUtil.setSQL(sql);
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query", e);
			}
			ResultSet rs = dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			int i = count;
			return i;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new SearchDAOException("Exception at getRecordCount(SearchCriteria criteria) method", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException("Exception from searchuser method ", e);
			}
		}
	}
	*/
	
	private String searchUserSQL(StdUserSearchCriteria obj, String sql, String memshipTypeId, ArrayList paramList)
			throws SearchDAOException {
		if ((sql == null) || sql.equals("")) {
			throw new SearchDAOException("invalid parameter");
		}

		String name = obj.getUserName();
		String empID = obj.getEmployeeId();
		StringBuffer strBuffer = new StringBuffer(sql);
		if ((name != null) && !name.equals("")) {
			strBuffer.append(" AND UPPER(USER_NAME) LIKE '");
			strBuffer.append(name.toUpperCase());
			strBuffer.append("%' ");
		}
        if (obj.getLoginId() != null &&!"".equals(obj.getLoginId().trim()))
        {
            strBuffer.append(" AND UPPER(LOGIN_ID) = '");
            strBuffer.append(obj.getLoginId().toUpperCase());
            strBuffer.append("' ");
        }		
        if (obj.getBranchCode() != null &&!"".equals(obj.getBranchCode().trim()))
        {
            strBuffer.append(" AND BRANCH_CODE LIKE '");
            strBuffer.append(obj.getBranchCode());
            strBuffer.append("%' ");
        }	
        if (obj.getStatus() != null &&!"".equals(obj.getStatus().trim()))
        {
            strBuffer.append(" AND a.STATUS LIKE '");
            strBuffer.append(obj.getStatus().toUpperCase());
            strBuffer.append("%' ");
        }	
		if ((empID != null) && !empID.equals("")) {
			strBuffer.append(" AND UPPER(EMPLOYEE_ID) LIKE '");
			strBuffer.append(empID.trim().toUpperCase());
			strBuffer.append("'");
		}
		if ((obj.getRoleTypeID() != null) && !obj.getRoleTypeID().equals("")) {
			strBuffer.append(" AND a.ROLETYPE_ID = ");
			strBuffer.append(obj.getRoleTypeID());
			strBuffer.append(" ");
		}
		if ((obj.getAssignmentType() != null) && !obj.getAssignmentType().equals("")) {
			if (obj.getAssignmentType().equals("U")) {
				if (MAKER_CHECKER_SAME_USER) {
					if ((ICMSConstant.MEMBERSHIP_TYPE_MAKER.equals(memshipTypeId) || ICMSConstant.MEMBERSHIP_TYPE_CHECKER
							.equals(memshipTypeId))
							&& obj.getTeamID() > 0) {
						strBuffer.append(" AND ( a.USER_ID NOT IN (SELECT USER_ID FROM " + TEAM_MEMBER_TABLE + " ) ");
						String includeUserList = constructTeamMemberListByMembershipType(obj.getMaintainTeam(),
								memshipTypeId);
						if (includeUserList != null && includeUserList.length() > 0) {
							strBuffer.append(" or ( a.user_id in ( ");
							strBuffer.append(includeUserList);
							strBuffer.append("))");
						}
						/*
						 * strBuffer.append(" select m2.user_id ");
						 * strBuffer.append(" from " + TEAM_MEMBER_TABLE +
						 * " m2, cms_team_membership tm2, cms_team_type_membership ttms2 "
						 * ); strBuffer.append(" where tm2.TEAM_ID = " +
						 * teamId);strBuffer.append(
						 * " and tm2.TEAM_MEMBERSHIP_ID = m2.TEAM_MEMBERSHIP_ID "
						 * );strBuffer.append(
						 * " and ttms2.TEAM_TYPE_MEMBERSHIP_ID = tm2.TEAM_TYPE_MEMBERSHIP_ID "
						 * ); strBuffer
						 * .append(" and ttms2.MEMBERSHIP_TYPE_ID = " +
						 * ((ICMSConstant
						 * .MEMBERSHIP_TYPE_MAKER.equals(memshipTypeId)) ?
						 * ICMSConstant.MEMBERSHIP_TYPE_CHECKER :
						 * ICMSConstant.MEMBERSHIP_TYPE_MAKER));
						 */

						/*
						 * strBuffer.append(" and not exists (select m3.user_id from "
						 * + TEAM_MEMBER_TABLE +
						 * " m3, cms_team_membership tm3, ");
						 * strBuffer.append(" cms_team_type_membership ttms3 ");
						 * strBuffer.append(" where tm3.team_id = " + teamId);
						 * strBuffer.append(
						 * " and tm3.TEAM_MEMBERSHIP_ID = m3.TEAM_MEMBERSHIP_ID "
						 * );strBuffer.append(
						 * " and tm3.TEAM_TYPE_MEMBERSHIP_ID = ttms3.TEAM_TYPE_MEMBERSHIP_ID "
						 * );
						 * strBuffer.append(" and ttms3.MEMBERSHIP_TYPE_ID = " +
						 * memshipTypeId);
						 * strBuffer.append(" and m3.user_id = m2.user_id) ");
						 */

						strBuffer.append(") ");
					}
					else {
						strBuffer.append(" AND a.USER_ID NOT IN (SELECT USER_ID FROM " + TEAM_MEMBER_TABLE + ") ");
					}
				}
				/*
				 * only for for custodian and CPC same user if (CPC_TEAM_TYPE_ID.equals(teamTypeId))
				 * {
				 * strBuffer.append(" AND ( a.USER_ID NOT IN (SELECT USER_ID FROM "
				 * + TEAM_MEMBER_TABLE + ") ");
				 * strBuffer.append(" OR (T.TEAM_TYPE_ID = " +
				 * CPC_CUSTODIAN_TEAM_TYPE_ID);strBuffer.append(
				 * " AND a.USER_ID IN ( SELECT M2.USER_ID FROM CMS_TEAM_MEMBER M2 WHERE USER_ID IN (SELECT USER_ID FROM CMS_TEAM_MEMBER M1, "
				 * );strBuffer.append(
				 * " CMS_TEAM_MEMBERSHIP MS1, CMS_TEAM T1, CMS_TEAM_TYPE_MEMBERSHIP TM1 WHERE MS1.TEAM_ID = T1.TEAM_ID AND "
				 * );strBuffer.append(
				 * " M1.TEAM_MEMBERSHIP_ID = MS1.TEAM_MEMBERSHIP_ID AND T1.TEAM_TYPE_ID = "
				 * ); strBuffer.append(CPC_CUSTODIAN_TEAM_TYPE_ID +
				 * " AND MS1.TEAM_TYPE_MEMBERSHIP_ID = TM1.TEAM_TYPE_MEMBERSHIP_ID AND "
				 * ); strBuffer.append(" TM1.MEMBERSHIP_TYPE_ID = " +
				 * memshipTypeId+
				 * ") GROUP BY M2.USER_ID HAVING count (M2.USER_ID) < 2) ) )");
				 * } else if (CPC_CUSTODIAN_TEAM_TYPE_ID.equals(teamTypeId)) {
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * strBuffer.append(" AND ( a.USER_ID NOT IN (SELECT USER_ID FROM "
				 * + TEAM_MEMBER_TABLE + ") ");
				 * strBuffer.append(" OR (T.TEAM_TYPE_ID = " +
				 * CPC_TEAM_TYPE_ID);strBuffer.append(
				 * " AND a.USER_ID IN ( SELECT M2.USER_ID FROM CMS_TEAM_MEMBER M2 WHERE USER_ID IN (SELECT USER_ID FROM CMS_TEAM_MEMBER M1, "
				 * );strBuffer.append(
				 * " CMS_TEAM_MEMBERSHIP MS1, CMS_TEAM T1, CMS_TEAM_TYPE_MEMBERSHIP TM1 WHERE MS1.TEAM_ID = T1.TEAM_ID AND "
				 * );strBuffer.append(
				 * " M1.TEAM_MEMBERSHIP_ID = MS1.TEAM_MEMBERSHIP_ID AND T1.TEAM_TYPE_ID = "
				 * ); strBuffer.append(CPC_TEAM_TYPE_ID +
				 * " AND MS1.TEAM_TYPE_MEMBERSHIP_ID = TM1.TEAM_TYPE_MEMBERSHIP_ID AND "
				 * ); strBuffer.append(" TM1.MEMBERSHIP_TYPE_ID = " +
				 * memshipTypeId+
				 * ") GROUP BY M2.USER_ID HAVING count (M2.USER_ID) < 2) ) )");
				 * }
				 */

				else {
					strBuffer.append(" AND a.USER_ID NOT IN (SELECT USER_ID FROM " + TEAM_MEMBER_TABLE + ") ");
				}
			}
			else if (obj.getAssignmentType().equals("A")) {
				strBuffer.append(" AND a.USER_ID IN (SELECT USER_ID FROM " + TEAM_MEMBER_TABLE + ") ");
			}
		} else {
			if (obj.getMaintainTeam() != null) {
				if (obj.getMaintainTeam().getCountryCodes() != null &&
						obj.getMaintainTeam().getCountryCodes().length > 0) {
					strBuffer.append(" and (select count(*) from cms_team_country_code tcc where tcc.team_id = t.team_id) <= ? ");
					paramList.add(new Integer(obj.getMaintainTeam().getCountryCodes().length));
				}
				if (obj.getMaintainTeam().getOrganisationCodes() != null &&
						obj.getMaintainTeam().getOrganisationCodes().length > 0) {
					strBuffer.append(" and (select count(*) from cms_team_organisation_code toc where toc.team_id = t.team_id) <= ? ");
					paramList.add(new Integer(obj.getMaintainTeam().getOrganisationCodes().length));
				}
				// By Abhijit R. 20 OCT 2011. For SMS Modification
				/*if (obj.getMaintainTeam().getCMSSegmentCodes() != null &&
						obj.getMaintainTeam().getCMSSegmentCodes().length > 0) {
					strBuffer.append(" and (select count(*) from CMS_TEAM_CMS_SEGMENT_CODE tcsc where tcsc.team_id = t.team_id) <= ? ");
					paramList.add(new Integer(obj.getMaintainTeam().getCMSSegmentCodes().length));
				}*/
				 
				if (obj.getMaintainTeam().getOrgGroupCode() != null &&
						obj.getMaintainTeam().getOrgGroupCode().length > 0) {
					strBuffer.append(" and (select count(*) from CMS_TEAM_ORG_GROUP_CODE togc where togc.team_id = t.team_id) <= ? ");
					paramList.add(new Integer(obj.getMaintainTeam().getOrgGroupCode().length));					
				}
				if (obj.getTeamTypeList() != null && obj.getTeamTypeList().size() > 0) {
					strBuffer.append(" and (exists (select 1 from cms_team_type_membership ttms "); 
					strBuffer.append(" where ttms.TEAM_TYPE_MEMBERSHIP_ID = ms.TEAM_TYPE_MEMBERSHIP_ID ");
					strBuffer.append(" and ttms.TEAM_TYPE_ID in ( ");
					
					for (int i = 0; i < obj.getTeamTypeList().size(); i++) {
						strBuffer.append("?");
						if (i != (obj.getTeamTypeList().size()-1))
							strBuffer.append(",");
						paramList.add(obj.getTeamTypeList().get(i));
					}
					strBuffer.append(" )) or t.team_id is null) ");
				}
			}
		}
		//START:: commented for hdfc bank for PAN INDIA, not spcific to country or region 
		/*if (obj.getTeamID() > 0) {
			strBuffer.append("  AND a.COUNTRY in (select CC.COUNTRY_CODE from CMS_TEAM_COUNTRY_CODE CC where TEAM_ID = ? )");
			paramList.add(new Long(obj.getTeamID()));
		}*/
		//END:: commented for hdfc bank for PAN INDIA, not spcific to country or region
		return strBuffer.toString();
	}

	private String constructTeamMemberListByMembershipType(ITeam team, String membershipTypeId) {
		String returnStr = "";
		ITeamMembership[] tm = team.getTeamMemberships();
		ArrayList userList = new ArrayList();
		ArrayList filterList = new ArrayList();
		for (int i = 0; i < tm.length; i++) {
			if (tm[i].getTeamTypeMembership().getMembershipType().getMembershipTypeID() == Long
					.parseLong(membershipTypeId)) {
				filterList = constructTeamMemberList(tm[i].getTeamMembers());
			}
			else {
				userList.addAll(constructTeamMemberList(tm[i].getTeamMembers()));
			}
		}
		userList.removeAll(filterList);

		for (int i = 0; i < userList.size(); i++) {
			if (i > 0)
				returnStr += ",";

			returnStr += (String) userList.get(i);
		}

		return returnStr;
	}

	private ArrayList constructTeamMemberList(ITeamMember[] teamMemberList) {
		ArrayList returnList = new ArrayList();
		if (teamMemberList != null && teamMemberList.length > 0) {
			for (int i = 0; i < teamMemberList.length; i++) {
				returnList.add(String.valueOf(teamMemberList[i].getTeamMemberUser().getUserID()));
			}
		}

		return returnList;
	}

	public SearchResult searchRoleType(RoleTypeSearchCriteria criteria) throws SearchDAOException {
		if (criteria == null) {
			throw new SearchDAOException("invalid parameter");
		}
		OBRoleTypeSearchCriteria obj = criteria.getCriteria();
		if (obj == null) {
			throw new SearchDAOException("OBRoleTypeSearchCriteria - null");
		}
		String firstSort = criteria.getFirstSort();
		String secondSort = criteria.getSecondSort();
		int startIndex = criteria.getStartIndex();
		int nItems = criteria.getNItems();
		String sql = "SELECT ROLE_TYPE_ID, ROLE_TYPE_NAME, PRIORITY, VERSION_TIME FROM " + ROLETYPE_TABLE
				+ " WHERE STATUS = '" + "A" + "' ";
		int numTotalRecords = getRoleTypeRecordCount(obj);
		if (numTotalRecords == 0) {
			return null;
		}
		String search = searchRoleTypeSQL(obj, sql);
		StringBuffer strBuffer = new StringBuffer(search.trim());
		if ((firstSort != null) && !firstSort.equals("")) {
			strBuffer.append(" ORDER BY ");
			strBuffer.append(firstSort.trim());
			if ((secondSort != null) && !secondSort.equals("") && !secondSort.equalsIgnoreCase(firstSort)) {
				strBuffer.append(", ");
				strBuffer.append(secondSort.trim());
			}
		}
		try {
			dbUtil = new DBUtil();
			// println(strBuffer.toString());
			try {
				dbUtil.setSQL(strBuffer.toString());
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query statement", e);
			}
			println("executing query...");
			ResultSet rs = dbUtil.executeQuery();
			println("after executing query...");
			while ((startIndex-- > 0) && rs.next()) {
				;
			}
			Vector list = processRoleTypeResultSet(rs, nItems);
			SearchResult searchresult = new SearchResult(startIndex, list.size(), numTotalRecords, list);
			return searchresult;
		}
		catch (Exception e) {
			throw new SearchDAOException("Exception from searchuser method ", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
			}
		}
	}

	private Vector processRoleTypeResultSet(ResultSet rs, int countRequired) throws SQLException {
		Vector list = new Vector();
		while (rs.next()) {
			OBRoleType role = new OBRoleType();
			role.setRoleTypeID(rs.getLong("ROLE_TYPE_ID"));
			role.setRoleTypeName(rs.getString("ROLE_TYPE_NAME"));
			role.setIndex(rs.getInt("PRIORITY"));
			Date dt = (Date) rs.getObject("VERSION_TIME");
			role.setVersionTime(dt.getTime());
			list.add(role);
			if ((countRequired != 0) && (countRequired == list.size())) {
				break;
			}
		}
		return list;
	}

	private int getRoleTypeRecordCount(OBRoleTypeSearchCriteria obj) throws SearchDAOException {
		String countSQL = "SELECT COUNT(*) FROM " + ROLETYPE_TABLE + " WHERE STATUS='" + "A" + "' ";
		String sql = searchRoleTypeSQL(obj, countSQL);
		try {
			dbUtil = new DBUtil();
			// println(sql);
			try {
				dbUtil.setSQL(sql);
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query", e);
			}
			ResultSet rs = dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			int i = count;
			return i;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new SearchDAOException("Exception at getRecordCount(SearchCriteria criteria) method", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
			}
		}
	}

	private String searchRoleTypeSQL(OBRoleTypeSearchCriteria obj, String sql) throws SearchDAOException {
		if ((sql == null) || sql.equals("")) {
			throw new SearchDAOException("invalid parameter");
		}
		String name = obj.getRoleTypeName();
		StringBuffer strBuffer = new StringBuffer(sql);
		if ((name != null) && !name.equals("")) {
			strBuffer.append(" AND ROLE_TYPE_NAME LIKE '");
			strBuffer.append(name);
			strBuffer.append("%'");
		}
		return strBuffer.toString();
	}

	private static void println(String s) {
		DefaultLogger.debug("UserDAO", s);
	}

	public Collection getAvailableUsers(Collection users, Date fromDate, Date toDate) throws SearchDAOException {
		if ((users == null) || (users.size() == 0)) {
			return null;
		}
		Iterator it = users.iterator();
		StringBuffer sbUsers = new StringBuffer();
		for (; it.hasNext(); sbUsers.append(",")) {
			ICommonUser user = (ICommonUser) it.next();
			sbUsers.append(user.getUserID());
		}

		sbUsers.deleteCharAt(sbUsers.length() - 1);
		String sql = "SELECT U.USER_ID FROM  " + USER_TABLE + "  U " + " WHERE U.USER_ID IN (" + sbUsers.toString()
				+ ") " + " AND U.USER_ID NOT IN ( " + " SELECT DISTINCT H.USER_ID  FROM  TEST_USER_HISTORY H "
				+ " WHERE H.STATUS <> 'D'  " + " AND ( " + " (H.FROM_DATE >= TO_DATE('"
				+ DateUtil.formatDate("dd/MM/yyyy", fromDate) + "','DD/MM/YYYY') " + " AND H.FROM_DATE <= TO_DATE('"
				+ DateUtil.formatDate("dd/MM/yyyy", toDate) + "','DD/MM/YYYY') ) " + " OR "
				+ " ( H.TO_DATE >= TO_DATE('" + DateUtil.formatDate("dd/MM/yyyy", fromDate) + "','DD/MM/YYYY')  AND "
				+ " ( H.TO_DATE <= TO_DATE('" + DateUtil.formatDate("dd/MM/yyyy", toDate) + "','DD/MM/YYYY')  OR "
				+ " H.FROM_DATE <= TO_DATE('" + DateUtil.formatDate("dd/MM/yyyy", fromDate) + "','DD/MM/YYYY') )"
				+ " ) " + " ) " + " ) ";
		// DefaultLogger.debug(this, "sql = " + sql);
		ArrayList availableUsers = new ArrayList();
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			for (ResultSet rs = dbUtil.executeQuery(); rs.next(); availableUsers.add(new Long(rs.getLong(1)))) {
				;
			}
			dbUtil.close();
			return availableUsers;
		}
		catch (Exception e) {
			throw new SearchDAOException(e);
		}
	}

	/*
	 * private String searchUserSQL(OBCommonUserSearchCriteria obj, String sql)
	 * throws SearchDAOException { if ((sql == null) || sql.equals("")) { throw
	 * new SearchDAOException("invalid parameter"); } String name =
	 * obj.getUserName(); String empID = obj.getEmployeeId(); StringBuffer
	 * strBuffer = new StringBuffer(sql); if ((name != null) &&
	 * !name.equals("")) { strBuffer.append(" AND UPPER(USER_NAME) LIKE '");
	 * strBuffer.append(name.toUpperCase()); strBuffer.append("%' "); } if
	 * ((empID != null) && !empID.equals("")) {
	 * strBuffer.append(" AND EMPLOYEE_ID LIKE '");
	 * strBuffer.append(empID.trim()); strBuffer.append("%'"); } if
	 * ((obj.getRoleTypeID() != null) && !obj.getRoleTypeID().equals("")) {
	 * strBuffer.append(" AND a.ROLETYPE_ID = ");
	 * strBuffer.append(obj.getRoleTypeID()); strBuffer.append(" "); } if
	 * ((obj.getAssignmentType() != null) &&
	 * !obj.getAssignmentType().equals("")) { if
	 * (obj.getAssignmentType().equals("U")) {
	 * strBuffer.append(" AND a.USER_ID NOT IN (SELECT USER_ID FROM " +
	 * TEAM_MEMBER_TABLE + ") "); } else if
	 * (obj.getAssignmentType().equals("A")) {
	 * strBuffer.append(" AND a.USER_ID IN (SELECT USER_ID FROM " +
	 * TEAM_MEMBER_TABLE + ") "); } } return strBuffer.toString(); }
	 */
	// get the user info for multi-level approval notification
	public Collection getUserInfo(Collection userList, Collection groupList) throws SearchDAOException {
		if ((userList == null) || (userList.size() == 0)) {
			return null;
		}
		Iterator it = userList.iterator();
		StringBuffer sbUsers = new StringBuffer();

		for (; it.hasNext(); sbUsers.append(",")) {
			sbUsers.append((String) it.next());
		}

		StringBuffer sbGroups = new StringBuffer();
		it = groupList.iterator();
		for (; it.hasNext(); sbGroups.append(",")) {
			sbGroups.append((String) it.next());
		}

		sbUsers.deleteCharAt(sbUsers.length() - 1);
		sbGroups.deleteCharAt(sbGroups.length() - 1);
		String sql = "SELECT U.USER_NAME, U.LOGIN_ID, T.TEAM_TYPE_ID FROM "
				+ USER_TABLE
				+ " U, "
				+ ROLETYPE_TABLE
				+ " R , "
				+ TEAM_TABLE
				+ " T, "
				+ TEAM_MEMBERSHIP_TABLE
				+ " MS, "
				+ TEAM_MEMBER_TABLE
				+ " M WHERE U.USER_ID IN ("
				+ sbUsers.toString()
				+ ") AND T.TEAM_ID IN ("
				+ sbGroups.toString()
				+ ") AND U.USER_ID = M.USER_ID AND M.TEAM_MEMBERSHIP_ID = MS.TEAM_MEMBERSHIP_ID AND MS.TEAM_ID = T.TEAM_ID";
		// DefaultLogger.debug(this, "sql = " + sql);
		ArrayList userInfoList = new ArrayList();
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			boolean hasFAM = false;
			while (rs.next()) {
				long team_type_id = rs.getLong("TEAM_TYPE_ID");
				if (team_type_id == ICMSConstant.TEAM_TYPE_FAM) {
					if (!hasFAM) {
						userInfoList.add("FAM");
						hasFAM = true;
					}
				}
				else {
					userInfoList.add(rs.getString("USER_NAME") + " (" + rs.getString("LOGIN_ID") + ")");
				}
			}

			dbUtil.close();
			return userInfoList;
		}
		catch (Exception e) {
			throw new SearchDAOException(e);
		}
	}

	public Date getLastLoginTime(long userID) throws UserException {
		if (userID <= 0) {
			return null;
		}
		Date lastLogin = null;
		String sql = "SELECT MAX(audit.login_time)  " + "  FROM " + USER_TABLE + " usr, " + AUTHENTICATION_TABLE
				+ "  auth, " + LOGIN_AUDIT_TABLE + "  audit " + " WHERE usr.USER_ID =" + userID
				+ "   AND usr.login_id = auth.login_id " + "   AND audit.LOGIN_ID = usr.login_id "
				+ "   AND upper(audit.LOGIN_STATUS) = 'SUCCESS' " + "   AND audit.LOGIN_TIME < auth.LAST_LOGIN_TIME ";
		// Authentication Last Login is actually Current Login Time
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			for (ResultSet rs = dbUtil.executeQuery(); rs.next(); lastLogin = rs.getTimestamp(1)) {
				;
			}
			dbUtil.close();
			return lastLogin;
		}
		catch (Exception e) {
			throw new UserException("failed to retrieve last login time for user id [" + userID + "]", e);
		}
	}
	
	public String getUserNameByUserID (long userID) throws UserException
	 {
	     
	     String sql = "SELECT usr.USER_NAME  "+
	                  "  FROM " + USER_TABLE + " usr " +
	                  " WHERE usr.USER_ID =" + userID ;
	     
	     String userName = null;
	     
	     try
	     {
	         dbUtil = new DBUtil();
	         dbUtil.setSQL(sql);
	         for(ResultSet rs = dbUtil.executeQuery();
	             rs.next();
	             userName = rs.getString(1));
	         dbUtil.close();
	         return userName;
	     }
	     catch(Exception e)
	     {
	         throw new UserException("failed to retrieve user name for user id [" + userID + "]", e);
	     }
	 }


	public List searchDormantNewUser() throws SearchDAOException {

		DefaultLogger.debug(this, "Accessing new StdUserDAO......");

		
		
		String sql="select distinct(usr.login_id) as LOGIN_ID, tr.creation_date  from cms_user usr , transaction tr where usr.login_id not in (select adt.login_id from cms_login_audit adt) and (sysdate - tr.creation_date)>30 and usr.user_id=tr.reference_id";
		
		
		StringBuffer strBuffer = new StringBuffer(sql.trim());
	
		try {
			dbUtil = new DBUtil();
			println(strBuffer.toString());
			try {
				dbUtil.setSQL(strBuffer.toString());
				
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query statement", e);
			}
			ResultSet rs = dbUtil.executeQuery();
			List list= new ArrayList();
			
			
			while (rs.next()) {
				list.add(rs.getString("LOGIN_ID"));
			}
			
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new SearchDAOException("Exception from searchuser method ", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException("Exception from searchuser method ", e);
			}
		}
		
		
	}
	
	public long getLoginUserCount() throws SearchDAOException {

		DefaultLogger.debug(this, "Accessing new StdUserDAO......");

		
		String sql= "select count(*) as count  from cms_user_session usr, cms_user s, cms_authentication ca where (usr.user_id = s.user_id) and (s.login_id = ca.login_id)";
		//String sql ="select distinct(usr.login_id) as LOGIN_ID, tr.creation_date  from cms_user usr , transaction tr where usr.login_id not in (select adt.login_id from cms_login_audit adt) and (sysdate - tr.creation_date)>30 and usr.user_id=tr.reference_id";
		
		
		StringBuffer strBuffer = new StringBuffer(sql.trim());
	
		try {
			dbUtil = new DBUtil();
			println(strBuffer.toString());
			try {
				dbUtil.setSQL(strBuffer.toString());
				
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query statement", e);
			}
			ResultSet rs = dbUtil.executeQuery();
		
			
			long  count = 0;
			while (rs.next()) {
				/*if(rs.getString("count").equals("") || rs.getString("count") == null)
				{
					count = 0;
				}
				else
				{*/
				count = Long.parseLong(rs.getString("count"));	
				//}
			}
			
			return count;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new SearchDAOException("Exception from searchuser method ", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException("Exception from searchuser method ", e);
			}
		}
		
		
	}
	
	public List getLoginUserDetail() throws SearchDAOException {

		DefaultLogger.debug(this, "Accessing new StdUserDAO......");

		
		String sql= "select ca.last_login_time, ca.last_access_time, s.login_id, s.user_id, user_name, employee_id, position, department, organisation, country from cms_user_session usr, cms_user s, cms_authentication ca where (usr.user_id = s.user_id) and (s.login_id = ca.login_id)";
		//String sql ="select distinct(usr.login_id) as LOGIN_ID, tr.creation_date  from cms_user usr , transaction tr where usr.login_id not in (select adt.login_id from cms_login_audit adt) and (sysdate - tr.creation_date)>30 and usr.user_id=tr.reference_id";
		
		
		StringBuffer strBuffer = new StringBuffer(sql.trim());
	
		try {
			dbUtil = new DBUtil();
			println(strBuffer.toString());
			try {
				dbUtil.setSQL(strBuffer.toString());
				
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query statement", e);
			}
			ResultSet rs = dbUtil.executeQuery();
			
			
			List listAll= new ArrayList();
			while (rs.next()) {
				List list= new ArrayList();
				list.add(0,rs.getString("last_login_time"));
				list.add(1,rs.getString("last_access_time"));
				list.add(2,rs.getString("login_id"));
				list.add(3,rs.getString("user_id"));
				list.add(4,rs.getString("user_name"));
				list.add(5,rs.getString("employee_id"));				
				list.add(6,rs.getString("position"));
				list.add(7,rs.getString("department"));
				list.add(8,rs.getString("organisation"));
				list.add(9,rs.getString("country"));
				
				listAll.add(list);
			}
			
			return listAll;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new SearchDAOException("Exception from searchuser method ", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException("Exception from searchuser method ", e);
			}
		}
		
		
	}
	
	public List getRmBranchCpuUser() throws SearchDAOException {

		DefaultLogger.debug(this, "Accessing new StdUserDAO..getRmBranchCpuUser method....");

		String sql= "select employee_id,team_type_membership_id from cms_user where team_type_membership_id in (1020,1001,1002,1003,1006,1007,1013)";
		
		StringBuffer strBuffer = new StringBuffer(sql.trim());
	
		try {
			dbUtil = new DBUtil();
			println(strBuffer.toString());
			try {
				dbUtil.setSQL(strBuffer.toString());
				
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query statement", e);
			}
			ResultSet rs = dbUtil.executeQuery();
			
			
			List listAll= new ArrayList();
			while (rs.next()) {
				List list= new ArrayList();
				list.add(0,rs.getString("employee_id"));
				list.add(1,rs.getString("team_type_membership_id"));
				listAll.add(list);
			}
			
			return listAll;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new SearchDAOException("Exception from getRmBranchCpuUser method ", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException("Exception from getRmBranchCpuUser method ", e);
			}
		}
	}
	
	
	public void killLoginUserDetail() throws SearchDAOException {

		DefaultLogger.debug(this, "Accessing new StdUserDAO......");

		String systemHandOffId = PropertyManager.getValue(SystemHandOff);
		String sql= "delete from cms_user_session where user_id not in (select user_id from cms_user where team_type_membership_id  in ("+systemHandOffId+"))";
		//String sql ="select distinct(usr.login_id) as LOGIN_ID, tr.creation_date  from cms_user usr , transaction tr where usr.login_id not in (select adt.login_id from cms_login_audit adt) and (sysdate - tr.creation_date)>30 and usr.user_id=tr.reference_id";
		
		
		StringBuffer strBuffer = new StringBuffer(sql.trim());
	
		try {
			dbUtil = new DBUtil();
			println(strBuffer.toString());
			try {
				dbUtil.setSQL(strBuffer.toString());
				
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query statement", e);
			}
			ResultSet rs = dbUtil.executeQuery();
			dbUtil.commit();
			
			
		}
		catch (Exception e) {
			try {
				dbUtil.rollback();
			} catch (DBConnectionException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new SearchDAOException("Exception from searchuser method ", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException("Exception from searchuser method ", e);
			}
		}
		
		
	}
	public StringBuffer BroadCastMessageDetail() throws SearchDAOException {

		DefaultLogger.debug(this, "Accessing new StdUserDAO......");

		
		String sql= "select s.login_id, s.user_id, user_name, employee_id, position, department, organisation, country from cms_user_session usr, cms_user s, cms_authentication ca where (usr.user_id = s.user_id) and (s.login_id = ca.login_id)";
		//String sql ="select distinct(usr.login_id) as LOGIN_ID, tr.creation_date  from cms_user usr , transaction tr where usr.login_id not in (select adt.login_id from cms_login_audit adt) and (sysdate - tr.creation_date)>30 and usr.user_id=tr.reference_id";
		
		StringBuffer resultString = new StringBuffer ();
		StringBuffer resultStringNew =null;
		StringBuffer strBuffer = new StringBuffer(sql.trim());
	
		try {
			dbUtil = new DBUtil();
			println(strBuffer.toString());
			try {
				dbUtil.setSQL(strBuffer.toString());
				
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query statement", e);
			}
			ResultSet rs = dbUtil.executeQuery();		
			
			while (rs.next()) {
				resultStringNew = new StringBuffer ();
				resultStringNew.append("<option value='" + rs.getString("login_id") + "'" + ">" + rs.getString("user_name") + " - " + rs.getString("employee_id") + "</option>");
				resultString.append(resultStringNew);
			}
			
			return resultString;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new SearchDAOException("Exception from searchuser method ", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException("Exception from searchuser method ", e);
			}
		}
		
		
	}
	
	public void updateDormantUser(String login_id,String table) throws SearchDAOException {

		DefaultLogger.debug(this, "Accessing new StdUserDAO......");

		
		
		String sql="update "+table+" set status='NR' where login_id='"+login_id+"' ";
		
		
		
		StringBuffer strBuffer = new StringBuffer(sql.trim());
	
		try {
			dbUtil = new DBUtil();
			println(strBuffer.toString());
			try {
				dbUtil.setSQL(strBuffer.toString());
				
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query statement", e);
			}
			ResultSet rs = dbUtil.executeQuery();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new SearchDAOException("Exception from searchuser method ", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException("Exception from searchuser method ", e);
			}
		}
		
		
	}
	//added by sachin - System hand over update
	public void updateUserHandover(String PARAM_VALUE,String login_name) throws SearchDAOException {

		DefaultLogger.debug(this, "Accessing new StdUserDAO......");

		 IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			String dateApplication="";
			for(int i=0;i<generalParamEntries.length;i++){
				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
					dateApplication=generalParamEntries[i].getParamValue();
				}
			}
		
			String sql="update CMS_GENERAL_PARAM set PARAM_VALUE= '"+PARAM_VALUE+"' , ACTIVITY_PERFORMED='"+dateApplication+"', HAND_OFF_TIME= '"+Calendar.getInstance().getTime()+"', HAND_OFF_USER= '"+login_name+"' where PARAM_CODE='USER_HAND_OFF' ";
			
		
		
		StringBuffer strBuffer = new StringBuffer(sql.trim());
	
		try {
			dbUtil = new DBUtil();
			println(strBuffer.toString());
			try {
				dbUtil.setSQL(strBuffer.toString());
				
			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query statement", e);
			}
			ResultSet rs = dbUtil.executeQuery();
			dbUtil.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new SearchDAOException("Exception from searchuser method ", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException("Exception from searchuser method ", e);
			}
		}
		
		
	}
	public boolean getUserHandOffState () throws UserException
	 {
	     
	     String sql = "SELECT PARAM_VALUE  "+
	                  "  FROM  CMS_GENERAL_PARAM  " +
	                  " WHERE PARAM_CODE='USER_HAND_OFF'" ;
	     
	     String value = null;
	     
	     try
	     {
	         dbUtil = new DBUtil();
	         dbUtil.setSQL(sql);
	         for(ResultSet rs = dbUtil.executeQuery();
	             rs.next();
	             value = rs.getString("PARAM_VALUE"));
	         dbUtil.close();
	         if(value.equals("N"))
	         {
	         return true;
	         }
	         else
	         {
	        	 return false;
	         }
	     }
	     catch(Exception e)
	     {
	         throw new UserException("failed to retrieve user hand off for user ", e);
	     }
	 }
	
	public boolean isHandOffActivityPerformed () throws UserException
	 {
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
		IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
		String dateApplication="";
		for(int i=0;i<generalParamEntries.length;i++){
			if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
				dateApplication=generalParamEntries[i].getParamValue();
			}
		}
	     String sql = "SELECT ACTIVITY_PERFORMED  "+
	                  "  FROM  CMS_GENERAL_PARAM  " +
	                  " WHERE PARAM_CODE='USER_HAND_OFF'" ;
	     
	     String value = null;
	     
	     try
	     {
	         dbUtil = new DBUtil();
	         dbUtil.setSQL(sql);
	         for(ResultSet rs = dbUtil.executeQuery();
	             rs.next();
	             value = rs.getString("ACTIVITY_PERFORMED"));
	         dbUtil.close();
	         if(value!=null){
	         if(value.equals(dateApplication))
	         {
	         return true;
	         }
	         else
	         {
	        	 return false;
	         }
	     }else{
	    	 return false;
	     }
	     }
	     catch(Exception e)
	     {
	         throw new UserException("failed to retrieve user hand off for user ", e);
	     }
	 }
	
	
	public long  getTeamByTeamTypeMembershipId(long teamTypeMembershipId) throws UserException
	 {
	     
	     String sql = "select team_id from cms_team where team_type_id in (select team_type_id from cms_team_type_membership where team_type_membership_id= '"+teamTypeMembershipId+"')" ;
	     
	     long value = ICMSConstant.LONG_INVALID_VALUE;
	     
	     try
	     {
	         dbUtil = new DBUtil();
	         dbUtil.setSQL(sql);
	         for(ResultSet rs = dbUtil.executeQuery();
	             rs.next();
	             value = rs.getLong("team_id"));
	         dbUtil.commit();
	        
	         
	         return value;
	         
	     }
	     catch(Exception e)
	     {
	         throw new UserException("failed to retrieve team id for user ", e);
	     }
	     finally {
				try {
					dbUtil.close();
				}
				catch (SQLException e) {
					throw new SearchDAOException("Exception from searchuser method ", e);
				}
			}
	 }
	
	
	public void  deleteTeamMember(long userId) throws UserException
	 {
	     
	     String sql = "delete from cms_team_member where user_id= '"+userId+"'" ;
	     
	     try
	     {
	         dbUtil = new DBUtil();
	         dbUtil.setSQL(sql);
	         ResultSet rs = dbUtil.executeQuery();
	            
	         dbUtil.commit();
	         
	         
	     }
	     catch(Exception e)
	     {
	         throw new UserException("failed to retrieve team id for user ", e);
	     }
	     finally {
				try {
					dbUtil.close();
				}
				catch (SQLException e) {
					throw new SearchDAOException("Exception from searchuser method ", e);
				}
			}
	 }
	
	public void  createTeamMember(long userId,long teamMemberShipId) throws UserException
	 {
	     
	     
	     
	     try
	     {
	    	 long teamMemberId=Long.parseLong((new SequenceManager()).getSeqNum("TEAMMEMBER_SEQ", true));
	    	 String sql = "INSERT INTO CMS_TEAM_MEMBER VALUES ("+teamMemberId+","+userId+","+teamMemberShipId+") " ;
	         dbUtil = new DBUtil();
	         dbUtil.setSQL(sql);
	         ResultSet rs = dbUtil.executeQuery();
	            
	         dbUtil.commit();
	         
	         
	     }
	     catch(Exception e)
	     {
	         throw new UserException("failed to retrieve team id for user ", e);
	     }
	     finally {
				try {
					dbUtil.close();
				}
				catch (SQLException e) {
					throw new SearchDAOException("Exception from searchuser method ", e);
				}
			}
	 }
	
	
	public long  getTeamMembershipIdByTeamTypeMembershipId(long teamTypeMembershipId) throws UserException
	 {
	     
	     String sql = "select TEAM_MEMBERSHIP_ID from CMS_TEAM_MEMBERSHIP where TEAM_TYPE_MEMBERSHIP_ID in ('"+teamTypeMembershipId+"')" ;
	     
	     long value = ICMSConstant.LONG_INVALID_VALUE;
	     
	     try
	     {
	         dbUtil = new DBUtil();
	         dbUtil.setSQL(sql);
	         for(ResultSet rs = dbUtil.executeQuery();
	             rs.next();
	             value = rs.getLong("TEAM_MEMBERSHIP_ID"));
	         dbUtil.commit();
	        
	         
	         return value;
	         
	     }
	     catch(Exception e)
	     {
	         throw new UserException("failed to retrieve team id for user ", e);
	     }
	     finally {
				try {
					dbUtil.close();
				}
				catch (SQLException e) {
					throw new SearchDAOException("Exception from searchuser method ", e);
				}
			}
	 }
	public String getUserNameByLoginID (String loginID) throws UserException
	 {
	     
	     String sql = "SELECT usr.USER_NAME  "+
	                  "  FROM " + USER_TABLE + " usr " +
	                  " WHERE usr.LOGIN_ID ='"+loginID+"'";
	     
	     String userName = null;
	     
	     try
	     {
	         dbUtil = new DBUtil();
	         dbUtil.setSQL(sql);
	         for(ResultSet rs = dbUtil.executeQuery();
	             rs.next();
	             userName = rs.getString(1));
	         dbUtil.close();
	         return userName;
	     }
	     catch(Exception e)
	     {
	         throw new UserException("failed to retrieve user name for loginID [" + loginID + "]", e);
	     }
	 }
	
	public String getLogedInUserName () 
	 {
	     
	     String sql = "SELECT USER_NAME from cms_user where USER_ID IN (select USER_ID from cms_user_session) ";
	     
	     String userName = null;
	     
	     try
	     {
	         dbUtil = new DBUtil();
	         dbUtil.setSQL(sql);
	         for(ResultSet rs = dbUtil.executeQuery();
	             rs.next();
	             userName = rs.getString(1));
	         dbUtil.close();
	         return userName;
	     }
	     catch(Exception e)
	     {
	    	 System.out.println("Exception in StdUserDAO.java=>String getLogedInUserName ()=>e=>"+e);
	        return e.getMessage(); 
	     }
	 }
}
