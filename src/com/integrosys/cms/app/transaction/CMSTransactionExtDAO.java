/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/CMSTransactionExtDAO.java,v 1.22 2005/10/15 03:31:42 whuang Exp $
 */
package com.integrosys.cms.app.transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.InvalidStatementTypeException;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.CMSConstantCla;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.dataaccess.DAO;
import com.integrosys.cms.app.common.util.dataaccess.DAOContext;
import com.integrosys.cms.app.common.util.dataaccess.DataAccessException;
import com.integrosys.cms.app.common.util.dataaccess.DataAccessPreparedStatement;
import com.integrosys.cms.app.common.util.dataaccess.IDAODescriptor;
import com.integrosys.cms.app.common.util.dataaccess.SearchingParameters;

/**
 * DAO for Transaction.
 */
public class CMSTransactionExtDAO extends DAO {

	private static final String USER_INFO_SQL = "SELECT teamtype.TEAM_TYPE_MEMBERSHIP_ID AS MEMBERSHIP_TYPE_ID, teamtype.MEMBERSHIP_NAME, team.TEAM_ID, team.ABBREVIATION AS TEAM_NAME, team.TEAM_TYPE_ID, member.USER_ID, member.USER_NAME FROM CMS_TEAM_TYPE_MEMBERSHIP teamtype,CMS_TEAM team, CMS_TEAM_MEMBERSHIP teammembership, CMS_TEAM_MEMBER teammember, CMS_USER member WHERE member.user_id = teammember.USER_ID AND teammember.TEAM_MEMBERSHIP_ID = teammembership.TEAM_MEMBERSHIP_ID AND teammembership.team_id = team.team_id AND teamtype.TEAM_TYPE_ID = team.TEAM_TYPE_ID AND member.user_id = ? AND team.team_id = ? AND teamtype.team_type_membership_id = ?";

	private static final String MEMBERSHIP_TYPE_INFO_MULTILVL_TRX_SQL = "SELECT teamtype.TEAM_TYPE_MEMBERSHIP_ID MEMBERSHIP_TYPE_ID, teamtype.MEMBERSHIP_NAME FROM CMS_TEAM_TYPE_MEMBERSHIP  teamtype WHERE teamtype.team_type_membership_id = ?";

	private static final String MEMBERSHIP_TYPE_INFO_NORMAL_TRX_SQL = "SELECT teamtype.TEAM_TYPE_MEMBERSHIP_ID MEMBERSHIP_TYPE_ID, teamtype.MEMBERSHIP_NAME FROM CMS_TEAM team, CMS_TEAM_MEMBERSHIP teammembership, CMS_TEAM_TYPE_MEMBERSHIP teamtype WHERE teamtype.TEAM_TYPE_MEMBERSHIP_ID = teammembership.TEAM_TYPE_MEMBERSHIP_ID AND team.TEAM_ID = teammembership.TEAM_ID AND team.TEAM_ID = ? AND teammembership.TEAM_TYPE_MEMBERSHIP_ID != ( SELECT teammembership.TEAM_TYPE_MEMBERSHIP_ID FROM CMS_TEAM team, CMS_TEAM_MEMBERSHIP teammembership, CMS_TEAM_MEMBER teammember, CMS_USER MEMBER WHERE team.TEAM_ID = teammembership.TEAM_ID AND teammembership.TEAM_MEMBERSHIP_ID = teammember.TEAM_MEMBERSHIP_ID AND member.USER_ID = teammember.USER_ID  AND member.USER_ID = ? AND team.TEAM_ID = ?)";

	/**
	 * Default Constructor
	 */
	public CMSTransactionExtDAO() {
	}

	public java.util.Collection searchNextRouteList(DAOContext daoCtx, SearchingParameters criteria)
			throws DataAccessException {
		ArrayList result = new ArrayList();
		IDAODescriptor das = new CMSTransactionDAODescriptor();
		DataAccessPreparedStatement stmt = daoCtx.prepareStatement(das, criteria);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					// Assemble Object
					// BEGIN

					OBCMSTrxRouteInfo nxt = new OBCMSTrxRouteInfo();
					nxt.setMemberShipTypeID(rs.getString("MEMBERSHIPTYPEID"));
					nxt.setMemberShipName(rs.getString("MEMBERSHIP_NAME"));
					nxt.setTeamID(rs.getString("TEAM_ID"));
					nxt.setTeamName(rs.getString("TEAM_NAME"));
					nxt.setUserID(rs.getString("USER_ID"));
					nxt.setUserName(rs.getString("USER_NAME"));

					// END
					result.add(nxt);
				} // END WHILE
			}// END IF
		}
		catch (SQLException e) {
			throw new DataAccessException("Failed on ResultSet", e.fillInStackTrace());
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				}
				catch (SQLException e) {
				}
			}
		}
		// END TRY-CATCH

		return result;

	}

	public int getTransactionCount(DAOContext daoCtx, SearchingParameters criteria) throws DataAccessException {
		int result = 0;
		IDAODescriptor das = new CMSTransactionDAODescriptor();
		DataAccessPreparedStatement stmt = daoCtx.prepareStatement(das, criteria);
		ResultSet rs = null;
		try {
			result = stmt.countOfQuery();
		}
		catch (DataAccessException e) {
			throw new DataAccessException("Failed on ResultSet", e.fillInStackTrace());
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				}
				catch (SQLException e) {
				}
			}
		}
		// END TRY-CATCH

		return result;

	}

	public java.util.Collection searchToDoList(DAOContext daoCtx, SearchingParameters criteria)
			throws DataAccessException {
		ArrayList result = new ArrayList();
		IDAODescriptor das = new CMSTransactionDAODescriptor();
		DataAccessPreparedStatement stmt = daoCtx.prepareStatement(das, criteria);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					// Assemble Object of To Do List
					/*
					 * OBLPTodoObject todoob = new OBLPTodoObject();
					 * todoob.setActionName(rs.getString("ACTION_NAME"));
					 * todoob.setProcessURL(rs.getString("PROCESS_URL"));
					 * todoob.setViewURL(rs.getString("VIEW_URL"));
					 * todoob.setProcessMembershipID
					 * (rs.getLong("PROCESS_MEMBERSHIP_TYPE_ID"));
					 * todoob.setActionID(rs.getString("ACTION_ID"));
					 * result.add(todoob);
					 */
					// BEGIN
					OBCMSTrxSearchResult sr = new OBCMSTrxSearchResult();
					sr.setTransactionID(rs.getString("TRANSACTION_ID"));
					sr.setReferenceID(rs.getString("REFERENCE_ID"));
					sr.setCustomerName(rs.getString("CUSTOMER_NAME"));
					sr.setFam(rs.getString("LEM_EMP_NAME"));
					sr.setLegalName(rs.getString("LEGAL_NAME"));
					sr.setLeID(rs.getString("LEGAL_ID"));
					sr.setOriginatingLocation(rs.getString("COUNTRY_NAME"));
					sr.setStatus(rs.getString("STATUS"));
					sr.setSubProfileID(rs.getString("CUSTOMER_ID"));
					sr.setTransactionDate(rs.getDate("TRANSACTION_DATE"));
					sr.setTransactionType(rs.getString("TRANSACTION_TYPE"));
					sr.setTransactionSubType(rs.getString("TRANSACTION_SUBTYPE"));
					sr.setLimitProfileID(rs.getString("LIMIT_PROFILE_ID"));
					sr.setUserState(rs.getString("USER_STATE"));
					sr.setTrxHistoryID(rs.getString("CUR_TRX_HISTORY_ID"));
					sr.setUserTransactionType(rs.getString("USER_TRX_TYPE"));
					sr.setSciLegalID(rs.getString("LMP_LE_ID"));
					sr.setSciSubprofileID(rs.getLong("LSP_ID"));
					sr.setDealNo(rs.getString("DEAL_NO"));
					sr.setActionUrls(new HashMap());
					sr.setTotrackActionUrls(new HashMap());

					sr.setSciApprovedDate(rs.getDate("LLP_BCA_REF_APPR_DATE"));
					// sr.setTransactionDate(rs.getDate("CMS_BCA_CREATE_DATE"));

					result.add(sr);
					/*
					 * if ("FAM".equals(rs.getString("LEM_EMP_TYPE_VALUE"))) {
					 * if (sr.getFam() == null) {
					 * sr.setFam(rs.getString("LEM_EMP_NAME")); } else if
					 * ("Y".equals(rs.getString("LEM_PRINCIPAL_FAM_IND"))) {
					 * sr.setFam(rs.getString("LEM_EMP_NAME")); } }
					 */

					sr.getActionUrls().put(rs.getString("USER_ACTION"), rs.getString("URL"));
					sr.getTotrackActionUrls().put("View", rs.getString("TOTRACK_URL"));

					String taskFlag = rs.getString("TASK_FLAG");
					if ("S".equals(taskFlag)) {
						sr.setSecColTask(true);
					}
					else if ("C".equals(taskFlag)) {
						sr.setCCColTask(true);
					}
					else if ("L".equals(taskFlag)) {
						sr.setLimitTask(true);
					}
					else {
						sr.setMainTask(true);
					}
					// END

				} // END WHILE
			}// END IF
		}
		catch (SQLException e) {
			throw new DataAccessException("Failed on ResultSet", e.fillInStackTrace());
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				}
				catch (SQLException e) {
				}
			}
		}
		// END TRY-CATCH

		return result;

	}

	public java.util.Collection getTransactionLogs(DAOContext daoCtx, SearchingParameters criteria)
			throws DataAccessException {
		ArrayList result = new ArrayList();
		IDAODescriptor das = new CMSTransactionDAODescriptor();
		DataAccessPreparedStatement stmt = daoCtx.prepareStatement(das, criteria);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					// Assemble Object
					// BEGIN
					OBCMSTrxHistoryLog nxt = new OBCMSTrxHistoryLog();
					nxt.setLogDate(rs.getString("LOG_DATE"));
					nxt.setLogUserName(rs.getString("USER_NAME"));
					nxt.setLogGroupName(rs.getString("TEAM_NAME"));
					nxt.setComment(rs.getString("REMARKS"));
					// END
					result.add(nxt);
				} // END WHILE
			}// END IF
		}
		catch (SQLException e) {
			throw new DataAccessException("Failed on ResultSet", e.fillInStackTrace());
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				}
				catch (SQLException e) {
				}
			}
		}
		// END TRY-CATCH

		return result;

	}

	public java.util.Collection getTransactionRouteInfos(String transactionID) throws SearchDAOException {
		DAOContext daoContext = null;
		try {
			SearchingParameters searching = new SearchingParameters(IDAODescriptor.QUERYTAG, "TransactionRouteInfo");
			searching.put("TRANSACTION_ID", transactionID);
			// DefaultLogger.debug(this, searching);
			daoContext = new DAOContext();
			Collection retcol = getTransactionRouteInfos(daoContext, searching);
			return retcol;
		}
		catch (DataAccessException e) {
			throw new SearchDAOException("Failed on Searching Transaction", e.fillInStackTrace());
		}
		finally {
			if (daoContext != null) {
				daoContext.close();
			}
		}

	}

	public java.util.Collection getTransactionRouteInfos(DAOContext daoCtx, SearchingParameters criteria)
			throws DataAccessException {
		ArrayList result = new ArrayList();
		IDAODescriptor das = new CMSTransactionDAODescriptor();
		DataAccessPreparedStatement stmt = daoCtx.prepareStatement(das, criteria);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					// Assemble Object
					// BEGIN
					OBCMSTrxRouteInfo nxt = new OBCMSTrxRouteInfo();
					nxt.setUserID(rs.getString("FROM_USER_ID"));
					nxt.setTeamID(rs.getString("FROM_GROUP_ID"));
					nxt.setTeamTypeID(rs.getString("FROM_TEAM_TYPE_ID"));
					// END
					result.add(nxt);
				} // END WHILE
			}// END IF
		}
		catch (SQLException e) {
			throw new DataAccessException("Failed on ResultSet", e.fillInStackTrace());
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				}
				catch (SQLException e) {
				}
			}
		}
		// END TRY-CATCH

		return result;

	}

	public static int authLevel(CMSTrxSearchCriteria criteria) {
		DBUtil dbUtil = null;
		int count = 0;
		String team_type = "";
		String sTeamTypeMembershipID = Long.toString(criteria.getTeamTypeMembershipID());
		String sql = "SELECT ttype.description  TEAM_TYPE  FROM cms_team_type_membership  mtype, "
				+ "cms_team_type ttype where mtype.team_type_membership_id = ? AND mtype.team_type_id = "
				+ "ttype.team_type_id";
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, sTeamTypeMembershipID);
			ResultSet rs = dbUtil.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					team_type = rs.getString("TEAM_TYPE");
					criteria.setTeamType(team_type);
					if ((team_type != null) && (team_type.length() > 0)) {
						if (// team_type.equals("FAM") ||
						team_type.equals("GAM") || team_type.equals("SCO") || team_type.equals("RCO")) {
							count = 1;
							criteria.setSearchIndicator(criteria.getSearchIndicator() + "AUTH");
						}
					}
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.error(CMSTransactionExtDAO.class, "", e);
			e.printStackTrace();
			// throw new
			// SearchDAOException("Error in getting collateral types: ", e);
		}
		finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				// throw new
				// SearchDAOException("Error in cleaning up DB resources.");
			}
		}
		return count;
		// return 1;
	}

	public static OBCMSTrxRouteInfo getUserInfo(ICMSTrxValue value) throws SearchDAOException {

		OBCMSTrxRouteInfo result = new OBCMSTrxRouteInfo();

		if (ICMSConstant.STATE_ACTIVE.equals(value.getStatus())) {
			return result;
		}

		// user info for normal transction
		long trxUserID = value.getUID();
		long trxTeamID = value.getTeamID();
		long trxMembershipTypeID = value.getTeamTypeID();

		// user info for multi-level transaction
		long trxToUserID = value.getToUserId();
		long trxToTeamID = value.getToAuthGId();
		long trxToMembershipTypeId = value.getToAuthGroupTypeId();

		// user info to use in query
		long theUserID = trxUserID;
		long theTeamID = trxTeamID;
		long theMembershipTypeID = trxMembershipTypeID;
		boolean isMultiLevelAppvTrx = false;

		if (isMultiLevelApproval(value) || isConfirmRejection(value)) {
			// is a multi-level approval transaction
			isMultiLevelAppvTrx = true;
			theUserID = trxToUserID;
			theTeamID = trxToTeamID;
			theMembershipTypeID = trxToMembershipTypeId;
		}

		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {

			dbUtil = new DBUtil();
			StringBuffer buf = new StringBuffer();
			String theSQL = null;
			if ((theUserID != ICMSConstant.LONG_INVALID_VALUE) && (theTeamID != ICMSConstant.LONG_INVALID_VALUE)
					&& (theMembershipTypeID != ICMSConstant.LONG_INVALID_VALUE)) {

				if (isMultiLevelAppvTrx) {
					buf.append(USER_INFO_SQL);
					theSQL = buf.toString();

					dbUtil.setSQL(theSQL);
					dbUtil.setLong(1, theUserID);
					dbUtil.setLong(2, theTeamID);
					dbUtil.setLong(3, theMembershipTypeID);
					rs = dbUtil.executeQuery();
					if (rs.next()) {
						result.setMemberShipTypeID(rs.getString("MEMBERSHIP_TYPE_ID"));
						result.setMemberShipName(rs.getString("MEMBERSHIP_NAME"));
						result.setTeamID(rs.getString("TEAM_ID"));
						result.setTeamName(rs.getString("TEAM_NAME"));
						result.setUserID(rs.getString("USER_ID"));
						result.setUserName(rs.getString("USER_NAME"));
					}
				}
				else {
					buf.append(MEMBERSHIP_TYPE_INFO_NORMAL_TRX_SQL);
					theSQL = buf.toString();
					dbUtil.setSQL(theSQL);
					dbUtil.setLong(1, theTeamID);
					dbUtil.setLong(2, theUserID);
					dbUtil.setLong(3, theTeamID);
					rs = dbUtil.executeQuery();
					if (rs.next()) {
						result.setMemberShipTypeID(rs.getString("MEMBERSHIP_TYPE_ID"));
						result.setMemberShipName(rs.getString("MEMBERSHIP_NAME"));
					}
					rs.close();
				}
			}
			else {

				if (isMultiLevelAppvTrx) {
					buf.append(MEMBERSHIP_TYPE_INFO_MULTILVL_TRX_SQL);
					theSQL = buf.toString();
					dbUtil.setSQL(theSQL);
					dbUtil.setLong(1, theMembershipTypeID);
					rs = dbUtil.executeQuery();
					if (rs.next()) {
						result.setMemberShipTypeID(rs.getString("MEMBERSHIP_TYPE_ID"));
						result.setMemberShipName(rs.getString("MEMBERSHIP_NAME"));
					}
					rs.close();
				}
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (DBConnectionException e) {
			e.printStackTrace();
		}
		catch (InvalidStatementTypeException e) {
			e.printStackTrace();
		}
		catch (NoSQLStatementException e) {
			e.printStackTrace();
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

		return result;
	}

	/**
	 * Helper method to check if the transaction is a multi-level approval
	 * transaction.
	 * 
	 * @param trxValue
	 * @return
	 */
	private static boolean isMultiLevelApproval(ICMSTrxValue trxValue) {
		String fromState = trxValue.getFromState();
		String toState = trxValue.getStatus();
		return CMSConstantCla.getMultiLevelApprovalStates().contains(fromState)
				|| CMSConstantCla.getMultiLevelApprovalStates().contains(toState);
	}

	/**
	 * Helper method to check if the transaction is a FAM confirm rejection
	 * transaction.
	 * 
	 * @param trxValue
	 * @return
	 */
	private static boolean isConfirmRejection(ICMSTrxValue trxValue) {
		String fromState = trxValue.getFromState();
		String toState = trxValue.getStatus();
		return ICMSConstant.STATE_PENDING_REJECT.equals(fromState) && ICMSConstant.STATE_REJECTED.equals(toState);
	}

	// For test only
	public static void main(String argv[]) throws Exception {
		OBCMSTrxValue value = new OBCMSTrxValue();
		value.setToAuthGroupTypeId(20);
		value.setToUserId(20040709005325L);
		OBCMSTrxRouteInfo uinfo = getUserInfo(value);
//		System.out.println("--" + uinfo.getLableOfUserInfo());

	}

}
