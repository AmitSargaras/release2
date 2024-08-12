package com.integrosys.component.user.app.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

public class CommonUserDAO {
	private DBUtil dbUtil;
	private String ROLETYPE_TABLE = PropertyManager.getValue("component.user.table.roletype");
	private String USER_TABLE = PropertyManager.getValue("component.user.table.user");
	private String STAGE_USER_TABLE = PropertyManager.getValue("component.user.table.stage.user");
	private String AUTHENTICATION_TABLE = PropertyManager.getValue("component.user.table.authentication");
	private String TRANSACTION_TABLE = PropertyManager.getValue("component.user.table.transaction");
	private String TEAM_TABLE = PropertyManager.getValue("component.team.table.team");
	private String TEAM_MEMBERSHIP_TABLE = PropertyManager.getValue("component.team.table.membership");
	private String TEAM_MEMBER_TABLE = PropertyManager.getValue("component.team.table.member");
	private String USER_SESSION_TABLE = PropertyManager.getValue("component.user.table.session");
	private static final boolean DEBUG = true;

	public SearchResult searchUser(CommonUserSearchCriteria criteria) throws SearchDAOException {
		if (criteria == null) {
			throw new SearchDAOException("invalid parameter");
		} else {
			OBCommonUserSearchCriteria obj = criteria.getCriteria();
			if (obj == null) {
				throw new SearchDAOException("OBUswerSearchCriteria - null");
			} else {
				String firstSort = criteria.getFirstSort();
				String secondSort = criteria.getSecondSort();
				int startIndex = criteria.getStartIndex();
				int nItems = criteria.getNItems();
				String dbUsed = PropertyManager.getValue("databaseUsed");
				String sql;
				String countSQL;
				if (dbUsed != null && dbUsed.equals("DB2")) {
					sql = "SELECT distinct a.USER_ID, LOGIN_ID, USER_NAME, DEPARTMENT, a.ROLETYPE_ID, ROLETYPE_NAME, T.TEAM_ID, T.ABBREVIATION  FROM   "
							+ this.ROLETYPE_TABLE + "  b , ( (" + this.TEAM_TABLE + " T LEFT OUTER JOIN "
							+ this.TEAM_MEMBERSHIP_TABLE + " MS  ON ( T.TEAM_ID = MS.TEAM_ID )) " + " LEFT OUTER JOIN "
							+ this.TEAM_MEMBER_TABLE + " M ON ( MS.TEAM_MEMBERSHIP_ID = M.TEAM_MEMBERSHIP_ID) ) "
							+ " RIGHT OUTER JOIN " + this.USER_TABLE
							+ " a ON (a.USER_ID = M.USER_ID ) where a.STATUS <> '" + "D" + "' "
							+ " AND a.ROLETYPE_ID = b.ROLETYPE_ID ";
					countSQL = "SELECT count(*)  FROM   " + this.ROLETYPE_TABLE + "  b , ( (" + this.TEAM_TABLE
							+ " T LEFT OUTER JOIN " + this.TEAM_MEMBERSHIP_TABLE
							+ " MS  ON ( T.TEAM_ID = MS.TEAM_ID )) " + " LEFT OUTER JOIN " + this.TEAM_MEMBER_TABLE
							+ " M ON ( MS.TEAM_MEMBERSHIP_ID = M.TEAM_MEMBERSHIP_ID) ) " + " RIGHT OUTER JOIN "
							+ this.USER_TABLE + " a ON (a.USER_ID = M.USER_ID ) where a.STATUS <> '" + "D" + "' "
							+ " AND a.ROLETYPE_ID = b.ROLETYPE_ID ";
					long teamID = obj.getTeamID();
					if (teamID > 0L) {
						String ctyList = " AND a.COUNTRY in (select COUNTRY_CODE from CMS_TEAM_COUNTRY_CODE where TEAM_ID ="
								+ teamID + ")  ";
						sql = sql + ctyList;
						countSQL = countSQL + ctyList;
					}
				} else {
					sql = " SELECT distinct a.USER_ID, LOGIN_ID, USER_NAME, DEPARTMENT, a.ROLETYPE_ID, ROLETYPE_NAME, T.TEAM_ID, T.ABBREVIATION  FROM "
							+ this.USER_TABLE + " a,  " + this.ROLETYPE_TABLE + "  b , " + this.TEAM_TABLE + " T, "
							+ this.TEAM_MEMBERSHIP_TABLE + " MS, " + this.TEAM_MEMBER_TABLE + " M "
							+ " WHERE a.STATUS <> '" + "D" + "' AND a.ROLETYPE_ID = b.ROLETYPE_ID "
							+ " AND a.USER_ID = M.USER_ID (+) "
							+ " AND M.TEAM_MEMBERSHIP_ID = MS.TEAM_MEMBERSHIP_ID (+) "
							+ " AND MS.TEAM_ID = T.TEAM_ID (+) ";
					countSQL = "SELECT count(*)  FROM " + this.USER_TABLE + " a,  " + this.ROLETYPE_TABLE + "  b , "
							+ this.TEAM_TABLE + " T, " + this.TEAM_MEMBERSHIP_TABLE + " MS, " + this.TEAM_MEMBER_TABLE
							+ " M " + " WHERE a.STATUS <> '" + "D" + "' AND a.ROLETYPE_ID = b.ROLETYPE_ID "
							+ " AND a.USER_ID = M.USER_ID (+) "
							+ " AND M.TEAM_MEMBERSHIP_ID = MS.TEAM_MEMBERSHIP_ID (+) "
							+ " AND MS.TEAM_ID = T.TEAM_ID (+) ";
				}

				int numTotalRecords = this.getUserRecordCount(obj, countSQL);
				println("******************************" + numTotalRecords);
				if (numTotalRecords == 0) {
					Vector list = new Vector();
					return new SearchResult(0, list.size(), 0, list);
				} else {
					String search = this.searchUserSQL(obj, sql);
					StringBuffer strBuffer = new StringBuffer(search.trim());
					if (firstSort != null && !firstSort.equals("")) {
						strBuffer.append(" ORDER BY ");
						strBuffer.append(firstSort.trim());
						if (secondSort != null && !secondSort.equals("") && !secondSort.equalsIgnoreCase(firstSort)) {
							strBuffer.append(", ");
							strBuffer.append(secondSort.trim());
						}
					}

					try {
						this.dbUtil = new DBUtil();
						println(strBuffer.toString());

						try {
							this.dbUtil.setSQL(strBuffer.toString());
						} catch (SQLException var25) {
							throw new SearchDAOException("Could not set SQL query statement", var25);
						}

						println("executing query...");
						ResultSet rs = this.dbUtil.executeQuery();
						println("after executing query...");

						while (startIndex-- > 0 && rs.next()) {
							;
						}

						Vector list = this.processResultSet(rs, nItems);
						SearchResult var15 = new SearchResult(startIndex, list.size(), numTotalRecords, list);
						return var15;
					} catch (Exception var26) {
						var26.printStackTrace();
						throw new SearchDAOException("Exception from searchuser method ", var26);
					} finally {
						try {
							this.dbUtil.close();
						} catch (SQLException var24) {
							throw new SearchDAOException("Exception from searchuser method ", var24);
						}
					}
				}
			}
		}
	}

	private Vector processResultSet(ResultSet rs, int countRequired) throws SQLException {
		Vector list = new Vector();

		while (rs.next()) {
			OBSearchCommonUser usr = new OBSearchCommonUser();
			usr.setLoginID(rs.getString("LOGIN_ID"));
			usr.setUserID(rs.getLong("USER_ID"));
			usr.setUserName(rs.getString("USER_NAME"));
			usr.setBusinessUnitName(rs.getString("ABBREVIATION"));
			usr.setBusinessUnitID(rs.getString("TEAM_ID"));
			usr.setRoleTypeID(rs.getLong("ROLETYPE_ID"));
			usr.setRoleTypeName(rs.getString("ROLETYPE_NAME"));
			list.add(usr);
			println("*********************RESULT SET SIZE******6**" + list.size());
			if (countRequired != 0 && countRequired == list.size()) {
				break;
			}
		}

		return list;
	}

	private int getUserRecordCount(OBCommonUserSearchCriteria obj, String countSQL) throws SearchDAOException {
		String sql = this.searchUserSQL(obj, countSQL);

		int var6;
		try {
			this.dbUtil = new DBUtil();
			println(sql);

			try {
				this.dbUtil.setSQL(sql);
			} catch (SQLException var16) {
				throw new SearchDAOException("Could not set SQL query", var16);
			}

			ResultSet rs = this.dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			var6 = count;
		} catch (Exception var17) {
			var17.printStackTrace();
			throw new SearchDAOException("Exception at getRecordCount(SearchCriteria criteria) method", var17);
		} finally {
			try {
				this.dbUtil.close();
			} catch (SQLException var15) {
				throw new SearchDAOException("Exception from searchuser method ", var15);
			}
		}

		return var6;
	}

	private String searchUserSQL(OBCommonUserSearchCriteria obj, String sql) throws SearchDAOException {
		if (sql != null && !sql.equals("")) {
			String name = obj.getUserName();
			String empID = obj.getEmployeeId();
			StringBuffer strBuffer = new StringBuffer(sql);
			if (name != null && !name.equals("")) {
				strBuffer.append(" AND UPPER(USER_NAME) LIKE '");
				strBuffer.append(name.toUpperCase());
				strBuffer.append("%' ");
			}

			if (obj.getLoginId() != null && !"".equals(obj.getLoginId().trim())) {
				strBuffer.append(" AND UPPER(LOGIN_ID) LIKE '");
				strBuffer.append(obj.getLoginId().toUpperCase());
				strBuffer.append("%' ");
			}

			if (empID != null && !empID.equals("")) {
				strBuffer.append(" AND EMPLOYEE_ID LIKE '");
				strBuffer.append(empID.trim());
				strBuffer.append("%'");
			}

			if (obj.getRoleTypeID() != null && !obj.getRoleTypeID().equals("")) {
				strBuffer.append(" AND a.ROLETYPE_ID = ");
				strBuffer.append(obj.getRoleTypeID());
				strBuffer.append(" ");
			}

			if (obj.getAssignmentType() != null && !obj.getAssignmentType().equals("")) {
				if (obj.getAssignmentType().equals("U")) {
					strBuffer.append(" AND a.USER_ID NOT IN (SELECT USER_ID FROM " + this.TEAM_MEMBER_TABLE + ") ");
				} else if (obj.getAssignmentType().equals("A")) {
					strBuffer.append(" AND a.USER_ID IN (SELECT USER_ID FROM " + this.TEAM_MEMBER_TABLE + ") ");
				}
			}

			return strBuffer.toString();
		} else {
			throw new SearchDAOException("invalid parameter");
		}
	}

	public SearchResult searchRoleType(RoleTypeSearchCriteria criteria) throws SearchDAOException {
		if (criteria == null) {
			throw new SearchDAOException("invalid parameter");
		} else {
			OBRoleTypeSearchCriteria obj = criteria.getCriteria();
			if (obj == null) {
				throw new SearchDAOException("OBRoleTypeSearchCriteria - null");
			} else {
				String firstSort = criteria.getFirstSort();
				String secondSort = criteria.getSecondSort();
				int startIndex = criteria.getStartIndex();
				int nItems = criteria.getNItems();
				String sql = "SELECT ROLE_TYPE_ID, ROLE_TYPE_NAME, PRIORITY, VERSION_TIME FROM " + this.ROLETYPE_TABLE
						+ " WHERE STATUS = '" + "A" + "' ";
				int numTotalRecords = this.getRoleTypeRecordCount(obj);
				if (numTotalRecords == 0) {
					return null;
				} else {
					String search = this.searchRoleTypeSQL(obj, sql);
					StringBuffer strBuffer = new StringBuffer(search.trim());
					if (firstSort != null && !firstSort.equals("")) {
						strBuffer.append(" ORDER BY ");
						strBuffer.append(firstSort.trim());
						if (secondSort != null && !secondSort.equals("") && !secondSort.equalsIgnoreCase(firstSort)) {
							strBuffer.append(", ");
							strBuffer.append(secondSort.trim());
						}
					}

					try {
						this.dbUtil = new DBUtil();
						println(strBuffer.toString());

						try {
							this.dbUtil.setSQL(strBuffer.toString());
						} catch (SQLException var23) {
							throw new SearchDAOException("Could not set SQL query statement", var23);
						}

						println("executing query...");
						ResultSet rs = this.dbUtil.executeQuery();
						println("after executing query...");

						while (startIndex-- > 0 && rs.next()) {
							;
						}

						Vector list = this.processRoleTypeResultSet(rs, nItems);
						SearchResult var13 = new SearchResult(startIndex, list.size(), numTotalRecords, list);
						return var13;
					} catch (Exception var24) {
						throw new SearchDAOException("Exception from searchuser method ", var24);
					} finally {
						try {
							this.dbUtil.close();
						} catch (SQLException var22) {
							;
						}

					}
				}
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
			if (countRequired != 0 && countRequired == list.size()) {
				break;
			}
		}

		return list;
	}

	private int getRoleTypeRecordCount(OBRoleTypeSearchCriteria obj) throws SearchDAOException {
		String countSQL = "SELECT COUNT(*) FROM " + this.ROLETYPE_TABLE + " WHERE STATUS='" + "A" + "' ";
		String sql = this.searchRoleTypeSQL(obj, countSQL);

		int var6;
		try {
			this.dbUtil = new DBUtil();
			println(sql);

			try {
				this.dbUtil.setSQL(sql);
			} catch (SQLException var16) {
				throw new SearchDAOException("Could not set SQL query", var16);
			}

			ResultSet rs = this.dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			var6 = count;
		} catch (Exception var17) {
			var17.printStackTrace();
			throw new SearchDAOException("Exception at getRecordCount(SearchCriteria criteria) method", var17);
		} finally {
			try {
				this.dbUtil.close();
			} catch (SQLException var15) {
				;
			}

		}

		return var6;
	}

	private String searchRoleTypeSQL(OBRoleTypeSearchCriteria obj, String sql) throws SearchDAOException {
		if (sql != null && !sql.equals("")) {
			String name = obj.getRoleTypeName();
			StringBuffer strBuffer = new StringBuffer(sql);
			if (name != null && !name.equals("")) {
				strBuffer.append(" AND ROLE_TYPE_NAME LIKE '");
				strBuffer.append(name);
				strBuffer.append("%'");
			}

			return strBuffer.toString();
		} else {
			throw new SearchDAOException("invalid parameter");
		}
	}

	private static void println(String s) {
		DefaultLogger.debug("UserDAO", s);
	}

	public Collection getAvailableUsers(Collection users, Date fromDate, Date toDate) throws SearchDAOException {
		if (users != null && users.size() != 0) {
			Iterator it = users.iterator();
			StringBuffer sbUsers = new StringBuffer();

			while (it.hasNext()) {
				ICommonUser user = (ICommonUser) it.next();
				sbUsers.append(user.getUserID());
				sbUsers.append(",");
			}

			sbUsers.deleteCharAt(sbUsers.length() - 1);
			String sql = "SELECT U.USER_ID FROM  " + this.USER_TABLE + "  U " + " WHERE U.USER_ID IN ("
					+ sbUsers.toString() + ") " + " AND U.USER_ID NOT IN ( "
					+ " SELECT DISTINCT H.USER_ID  FROM  TEST_USER_HISTORY H " + " WHERE H.STATUS <> 'D'  " + " AND ( "
					+ " (H.FROM_DATE >= TO_DATE('" + DateUtil.formatDate("dd/MM/yyyy", fromDate) + "','DD/MM/YYYY') "
					+ " AND H.FROM_DATE <= TO_DATE('" + DateUtil.formatDate("dd/MM/yyyy", toDate) + "','DD/MM/YYYY') ) "
					+ " OR " + " ( H.TO_DATE >= TO_DATE('" + DateUtil.formatDate("dd/MM/yyyy", fromDate)
					+ "','DD/MM/YYYY')  AND " + " ( H.TO_DATE <= TO_DATE('" + DateUtil.formatDate("dd/MM/yyyy", toDate)
					+ "','DD/MM/YYYY')  OR " + " H.FROM_DATE <= TO_DATE('" + DateUtil.formatDate("dd/MM/yyyy", fromDate)
					+ "','DD/MM/YYYY') )" + " ) " + " ) " + " ) ";
			DefaultLogger.debug(this, "sql = " + sql);
			ArrayList availableUsers = new ArrayList();

			try {
				this.dbUtil = new DBUtil();
				this.dbUtil.setSQL(sql);
				ResultSet rs = this.dbUtil.executeQuery();

				while (rs.next()) {
					availableUsers.add(new Long(rs.getLong(1)));
				}

				this.dbUtil.close();
				return availableUsers;
			} catch (Exception var9) {
				throw new SearchDAOException(var9);
			}
		} else {
			return null;
		}
	}

	private void enableActualUser(String exclSuperUsers, Date validFromDate) throws UserException {
		String userSQL = "UPDATE " + this.USER_TABLE + "   SET status = '" + "A" + "' " + " WHERE user_id IN ( "
				+ "   SELECT user_id    " + "     FROM " + this.USER_TABLE + " usr " + "    WHERE "
				+ "\t(( usr.VALID_FROM_DATE <=  " + getDateTimeString(formatDate(validFromDate), "yyyy-MM-dd")
				+ "\t\t AND " + "\t\t (not(usr.VALID_TO_DATE <=  "
				+ getDateTimeString(formatDate(validFromDate), "yyyy-MM-dd") + ") OR usr.VALID_TO_DATE is null)) "
				+ "\tOR " + "\t   (( usr.VALID_FROM_DATE <=  "
				+ getDateTimeString(formatDate(validFromDate), "yyyy-MM-dd") + "  OR usr.VALID_FROM_DATE is null) "
				+ "\t   AND " + "\t   (not(usr.VALID_TO_DATE <=  "
				+ getDateTimeString(formatDate(validFromDate), "yyyy-MM-dd") + " )) " + "\t\t)) "
				+ "\tAND usr.STATUS = '" + "I" + "' ";
		if (exclSuperUsers != null && !"".equals(exclSuperUsers)) {
			userSQL = userSQL + "      AND usr.LOGIN_ID NOT IN (  " + exclSuperUsers + "  ) )";
		} else {
			userSQL = userSQL + "      )";
		}

		try {
			this.dbUtil = new DBUtil();

			try {
				this.dbUtil.setSQL(userSQL);
			} catch (SQLException var14) {
				throw new SearchDAOException("Could not set SQL query", var14);
			}

			this.dbUtil.executeUpdate();
		} catch (Exception var15) {
			var15.printStackTrace();
			throw new UserException("Exception at enableUserAccounts() method", var15);
		} finally {
			try {
				this.dbUtil.close();
			} catch (SQLException var13) {
				throw new UserException("Exception at enableUserAccounts() method", var13);
			}
		}

	}

	private void enableUserAuthentication(String exclSuperUsers) throws UserException {
		String userSQL = "UPDATE " + this.AUTHENTICATION_TABLE + "   SET status = '" + "ACTIVE" + "' "
				+ " WHERE LOGIN_ID IN ( " + "   SELECT LOGIN_ID    " + "     FROM " + this.USER_TABLE + " usr "
				+ "    WHERE usr.STATUS = '" + "A" + "' ";
		if (exclSuperUsers != null && !"".equals(exclSuperUsers)) {
			userSQL = userSQL + "      AND usr.LOGIN_ID NOT IN (  " + exclSuperUsers + "  ) )";
		} else {
			userSQL = userSQL + "      )";
		}

		try {
			this.dbUtil = new DBUtil();

			try {
				this.dbUtil.setSQL(userSQL);
			} catch (SQLException var13) {
				throw new SearchDAOException("Could not set SQL query", var13);
			}

			this.dbUtil.executeUpdate();
		} catch (Exception var14) {
			var14.printStackTrace();
			throw new UserException("Exception at enableUserAccounts() method", var14);
		} finally {
			try {
				this.dbUtil.close();
			} catch (SQLException var12) {
				throw new UserException("Exception at enableUserAccounts() method", var12);
			}
		}

	}

	private void enableStageUser(String exclSuperUsers, Date validFromDate) throws UserException {
		String userSQL = "UPDATE " + this.STAGE_USER_TABLE + "   SET status = '" + "A" + "' " + "where user_id in ( "
				+ "    SELECT stg.USER_ID " + "    FROM " + this.USER_TABLE + " usr, " + this.STAGE_USER_TABLE
				+ " stg, " + this.TRANSACTION_TABLE + " trx " + "    WHERE usr.USER_ID = trx.REFERENCE_ID "
				+ "      AND stg.USER_ID = trx.STAGING_REFERENCE_ID " + "      AND " + "\t(( usr.VALID_FROM_DATE <=  "
				+ getDateTimeString(formatDate(validFromDate), "yyyy-MM-dd") + "\t\t AND "
				+ "\t\t (not(usr.VALID_TO_DATE <=  " + getDateTimeString(formatDate(validFromDate), "yyyy-MM-dd")
				+ ") OR usr.VALID_TO_DATE is null)) " + "\tOR " + "\t   (( usr.VALID_FROM_DATE <=  "
				+ getDateTimeString(formatDate(validFromDate), "yyyy-MM-dd") + "  OR usr.VALID_FROM_DATE is null) "
				+ "\t   AND " + "\t   (not(usr.VALID_TO_DATE <=  "
				+ getDateTimeString(formatDate(validFromDate), "yyyy-MM-dd") + " )) " + "\t\t)) "
				+ "\tAND usr.STATUS = '" + "I" + "' ";
		if (exclSuperUsers != null && !"".equals(exclSuperUsers)) {
			userSQL = userSQL + "      AND usr.LOGIN_ID NOT IN (  " + exclSuperUsers + "  ) )";
		} else {
			userSQL = userSQL + "      )";
		}

		try {
			this.dbUtil = new DBUtil();

			try {
				this.dbUtil.setSQL(userSQL);
			} catch (SQLException var14) {
				throw new SearchDAOException("Could not set SQL query", var14);
			}

			this.dbUtil.executeUpdate();
		} catch (Exception var15) {
			var15.printStackTrace();
			throw new UserException("Exception at disableUserAccounts() method", var15);
		} finally {
			try {
				this.dbUtil.close();
			} catch (SQLException var13) {
				throw new UserException("Exception at disableUserAccounts() method", var13);
			}
		}

	}

	private void disableActualUser(String exclSuperUsers, Date validToDate) throws UserException {
		String userSQL = "UPDATE " + this.USER_TABLE + "   SET status = '" + "E" + "' " + " WHERE user_id in ( "
				+ "   SELECT user_id    " + "     FROM " + this.USER_TABLE + " usr "
				+ "    WHERE usr.VALID_TO_DATE <=  " + getDateTimeString(formatDate(validToDate), "yyyy-MM-dd")
				+ "      AND usr.STATUS <> '" + "D" + "' ";
		if (exclSuperUsers != null && !"".equals(exclSuperUsers)) {
			userSQL = userSQL + "      AND usr.LOGIN_ID NOT IN (  " + exclSuperUsers + "  ) )";
		} else {
			userSQL = userSQL + "      )";
		}

		try {
			this.dbUtil = new DBUtil();

			try {
				this.dbUtil.setSQL(userSQL);
			} catch (SQLException var14) {
				throw new SearchDAOException("Could not set SQL query", var14);
			}

			this.dbUtil.executeUpdate();
		} catch (Exception var15) {
			var15.printStackTrace();
			throw new UserException("Exception at disableUserAccounts() method", var15);
		} finally {
			try {
				this.dbUtil.close();
			} catch (SQLException var13) {
				throw new UserException("Exception at disableUserAccounts() method", var13);
			}
		}

	}

	private void disableUserAuthentication(String exclSuperUsers) throws UserException {
		String userSQL = "UPDATE " + this.AUTHENTICATION_TABLE + "   SET status = '" + "EXPIRED" + "' "
				+ " WHERE LOGIN_ID IN ( " + "   SELECT LOGIN_ID    " + "     FROM " + this.USER_TABLE + " usr "
				+ "    WHERE usr.STATUS = '" + "E" + "' ";
		if (exclSuperUsers != null && !"".equals(exclSuperUsers)) {
			userSQL = userSQL + "      AND usr.LOGIN_ID NOT IN (  " + exclSuperUsers + "  ) ) ";
		} else {
			userSQL = userSQL + "      )";
		}

		try {
			this.dbUtil = new DBUtil();

			try {
				this.dbUtil.setSQL(userSQL);
			} catch (SQLException var13) {
				throw new SearchDAOException("Could not set SQL query", var13);
			}

			this.dbUtil.executeUpdate();
		} catch (Exception var14) {
			var14.printStackTrace();
			throw new UserException("Exception at disableUserAccounts() method", var14);
		} finally {
			try {
				this.dbUtil.close();
			} catch (SQLException var12) {
				throw new UserException("Exception at disableUserAccounts() method", var12);
			}
		}

	}

	private void disableStageUser(String exclSuperUsers, Date validToDate) throws UserException {
		String userSQL = "UPDATE " + this.STAGE_USER_TABLE + "   SET status = '" + "E" + "' " + " WHERE user_id in ( "
				+ "    SELECT stg.USER_ID " + "    FROM " + this.USER_TABLE + " usr, " + this.STAGE_USER_TABLE
				+ " stg, " + this.TRANSACTION_TABLE + " trx " + "    WHERE usr.USER_ID = trx.REFERENCE_ID "
				+ "      AND stg.USER_ID = trx.STAGING_REFERENCE_ID  " + "      AND usr.VALID_TO_DATE <=  "
				+ getDateTimeString(formatDate(validToDate), "yyyy-MM-dd") + "      AND usr.STATUS <> '" + "D" + "' ";
		if (exclSuperUsers != null && !"".equals(exclSuperUsers)) {
			userSQL = userSQL + "      AND usr.LOGIN_ID NOT IN (  " + exclSuperUsers + "  ) )";
		} else {
			userSQL = userSQL + "      )";
		}

		try {
			this.dbUtil = new DBUtil();

			try {
				this.dbUtil.setSQL(userSQL);
			} catch (SQLException var14) {
				throw new SearchDAOException("Could not set SQL query", var14);
			}

			this.dbUtil.executeUpdate();
		} catch (Exception var15) {
			var15.printStackTrace();
			throw new UserException("Exception at disableUserAccounts() method", var15);
		} finally {
			try {
				this.dbUtil.close();
			} catch (SQLException var13) {
				throw new UserException("Exception at disableUserAccounts() method", var13);
			}
		}

	}

	public void enableUserAccounts(String exclSuperUsers, Date validFromDate) throws UserException {
		this.enableActualUser(exclSuperUsers, validFromDate);
		this.enableStageUser(exclSuperUsers, validFromDate);
		this.enableUserAuthentication(exclSuperUsers);
	}

	public void disableUserAccounts(String exclSuperUsers, Date validToDate) throws UserException {
		this.disableActualUser(exclSuperUsers, validToDate);
		this.disableStageUser(exclSuperUsers, validToDate);
		this.disableUserAuthentication(exclSuperUsers);
	}

	public void dormantUserAccounts(String exclSuperUsers, int daysDormant) throws UserException {
		this.dormantActualUser(exclSuperUsers, daysDormant);
		this.dormantStageUser(exclSuperUsers, daysDormant);
		this.dormantUserAuthentication(exclSuperUsers);
	}

	public void dormantActualUser(String exclSuperUsers, int daysDormant) throws UserException {
		String userSQL = " UPDATE " + this.USER_TABLE + "    SET status = '" + "R" + "' " + "  WHERE user_id IN ( "
				+ "        SELECT user_id" + "          FROM " + this.USER_TABLE + " usr, " + this.AUTHENTICATION_TABLE
				+ " auth" + "         WHERE usr.login_id = auth.login_id"
				+ " \t\t    AND (DATE(current timestamp) - DATE(auth.last_login_time)) >= " + daysDormant
				+ "      \t\tAND usr.STATUS <> '" + "D" + "' ";
		if (exclSuperUsers != null && !"".equals(exclSuperUsers)) {
			userSQL = userSQL + "      AND usr.LOGIN_ID NOT IN (  " + exclSuperUsers + "  ) ) ";
		} else {
			userSQL = userSQL + "      )";
		}

		try {
			this.dbUtil = new DBUtil();

			try {
				this.dbUtil.setSQL(userSQL);
			} catch (SQLException var14) {
				throw new SearchDAOException("Could not set SQL query", var14);
			}

			this.dbUtil.executeUpdate();
		} catch (Exception var15) {
			var15.printStackTrace();
			throw new UserException("Exception at dormantUserAccounts() method", var15);
		} finally {
			try {
				this.dbUtil.close();
			} catch (SQLException var13) {
				throw new UserException("Exception at dormantUserAccounts() method", var13);
			}
		}

	}

	public void dormantUserAuthentication(String exclSuperUsers) throws UserException {
		String userSQL = "UPDATE " + this.AUTHENTICATION_TABLE + "   SET status = '" + "DORMANT" + "' "
				+ " WHERE LOGIN_ID IN ( " + "   SELECT LOGIN_ID    " + "     FROM " + this.USER_TABLE + " usr "
				+ "    WHERE usr.STATUS = '" + "R" + "' ";
		if (exclSuperUsers != null && !"".equals(exclSuperUsers)) {
			userSQL = userSQL + "      AND usr.LOGIN_ID NOT IN (  " + exclSuperUsers + "  )) ";
		} else {
			userSQL = userSQL + "      )";
		}

		try {
			this.dbUtil = new DBUtil();

			try {
				this.dbUtil.setSQL(userSQL);
			} catch (SQLException var13) {
				throw new SearchDAOException("Could not set SQL query", var13);
			}

			this.dbUtil.executeUpdate();
		} catch (Exception var14) {
			var14.printStackTrace();
			throw new UserException("Exception at dormantUserAccounts() method", var14);
		} finally {
			try {
				this.dbUtil.close();
			} catch (SQLException var12) {
				throw new UserException("Exception at dormantUserAccounts() method", var12);
			}
		}

	}

	public void dormantStageUser(String exclSuperUsers, int daysDormant) throws UserException {
		String userSQL = "UPDATE " + this.STAGE_USER_TABLE + "    SET status = '" + "R" + "' " + " WHERE user_id in ( "
				+ "    SELECT stg.USER_ID " + "    FROM " + this.USER_TABLE + " usr, " + this.STAGE_USER_TABLE
				+ " stg, " + this.AUTHENTICATION_TABLE + " auth, " + this.TRANSACTION_TABLE + " trx "
				+ "    WHERE usr.USER_ID = trx.REFERENCE_ID " + "      AND stg.USER_ID = trx.STAGING_REFERENCE_ID  "
				+ "           AND usr.login_id = auth.login_id"
				+ " \t\t    AND (DATE(current timestamp) - DATE(auth.last_login_time)) >= " + daysDormant
				+ "      \t\tAND usr.STATUS <> '" + "D" + "' ";
		if (exclSuperUsers != null && !"".equals(exclSuperUsers)) {
			userSQL = userSQL + "      AND usr.LOGIN_ID NOT IN (  " + exclSuperUsers + "  ) ) ";
		} else {
			userSQL = userSQL + "      )";
		}

		try {
			this.dbUtil = new DBUtil();

			try {
				this.dbUtil.setSQL(userSQL);
			} catch (SQLException var14) {
				throw new SearchDAOException("Could not set SQL query", var14);
			}

			this.dbUtil.executeUpdate();
		} catch (Exception var15) {
			var15.printStackTrace();
			throw new UserException("Exception at dormantUserAccounts() method", var15);
		} finally {
			try {
				this.dbUtil.close();
			} catch (SQLException var13) {
				throw new UserException("Exception at dormantUserAccounts() method", var13);
			}
		}

	}

	public static String getDateTimeString(String dateString, String dateFormat) {
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat(dateFormat);
			Date d = sdf1.parse(dateString);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return "timestamp('" + sdf2.format(d) + "')";
		} catch (Exception var5) {
			return "";
		}
	}

	public static String formatDate(Date date) {
		try {
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			return sdf2.format(date);
		} catch (Exception var2) {
			return "";
		}
	}

	public boolean checkUserSessionExists(String loginID) throws UserException {
		String userSQL = " SELECT session_id   FROM " + this.USER_TABLE + " usr, " + this.USER_SESSION_TABLE + " ses"
				+ "  WHERE usr.user_id = ses.user_id " + "    AND usr.login_id = ? ";
		boolean exists = false;

		try {
			if (loginID == null || loginID.trim().equals("")) {
				throw new UserException("Exception at checkUserSessionExists() method : Login ID is null ");
			}

			this.dbUtil = new DBUtil();

			try {
				this.dbUtil.setSQL(userSQL);
				this.dbUtil.setString(1, loginID.toUpperCase());
			} catch (SQLException var14) {
				throw new SearchDAOException("Could not set SQL query", var14);
			}

			ResultSet rs = this.dbUtil.executeQuery();
			if (rs.next()) {
				exists = true;
			} else {
				exists = false;
			}

			if (rs != null) {
				rs.close();
			}
		} catch (Exception var15) {
			var15.printStackTrace();
			throw new UserException("Exception at checkUserSessionExists() method", var15);
		} finally {
			try {
				this.dbUtil.close();
			} catch (SQLException var13) {
				throw new UserException("Exception at checkUserSessionExists() method", var13);
			}
		}

		return exists;
	}
}