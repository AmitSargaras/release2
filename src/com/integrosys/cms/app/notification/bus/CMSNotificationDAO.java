/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/notification/bus/CMSNotificationDAO.java,v 1.19 2006/11/08 14:02:07 hmbao Exp $
 */
package com.integrosys.cms.app.notification.bus;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.bus.PaginationBean;
import com.integrosys.cms.app.common.bus.PaginationResult;
import com.integrosys.cms.app.common.bus.PaginationUtil;
import com.integrosys.cms.app.common.bus.PaginationUtilFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;

import com.integrosys.component.notification.bus.Helper;
import com.integrosys.component.notification.bus.INotification;
import com.integrosys.component.notification.bus.OBNotification;
import com.integrosys.component.notification.bus.OBNotificationType;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2006/11/08 14:02:07 $ Tag: $Name: $
 */

public class CMSNotificationDAO {

	private static final String DATASOURCE_JNDI_KEY = "dbconfig.weblogic.datasource.jndiname";

	public void updateNotification(OBCMSNotification ob) throws CMSNotificationException {
		updateNotificationMsg(ob.getNotificationID(), ob.getNotificationMessage());
		softDeleteOldNotification(ob.getNotificationID(), ob.getDetails());
	}

	private void updateNotificationMsg(long notifyID, String msgStr) throws CMSNotificationException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			InitialContext context = new InitialContext();
			DataSource ds = (DataSource) (context.lookup(PropertyManager.getValue(DATASOURCE_JNDI_KEY)));
			con = ds.getConnection();
			ps = con.prepareStatement("update notification set message_object = ? where notification_id = ?");
			byte[] arr = msgStr.getBytes();
			ByteArrayInputStream ba = new ByteArrayInputStream(arr);
			ps.setAsciiStream(1, ba, arr.length);
			ps.setLong(2, notifyID);
			ps.executeUpdate();
		}
		catch (Exception ex) {
			throw new CMSNotificationException(ex);
		}
		finally {
			try {
				ps.close();
				con.close();
			}
			catch (Exception ex) {
			}
		}
	}

	private void softDeleteOldNotification(long notificationID, String details) throws CMSNotificationException {
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL("update notification set status='D' where details = ? and notification_id <> ?");
			dbUtil.setString(1, details);
			dbUtil.setLong(2, notificationID);
			dbUtil.executeUpdate();
		}
		catch (Exception e) {
			throw new CMSNotificationException(e);
		}
		finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				throw new CMSNotificationException(e);
			}
		}
	}

	/**
	 * get number of notification by team id
	 * 
	 * @param id
	 * @return
	 * @throws CMSNotificationException
	 */
	public int countNotifications(long teamID, long userID) throws CMSNotificationException {
		DefaultLogger.debug(this, "TeamID :" + teamID + " - UserID : " + userID);
		int notificationsCount = 0;
		DBUtil dbUtil = null;
		String query = "SELECT COUNT(NR.NOTIFICATION_ID) TOTAL_COUNT "
				+ "FROM NOTIFICATION N, NOTIFICATION_RECIPIENT nr " + "WHERE N.NOTIFICATION_ID = NR.NOTIFICATION_ID "
				+ "AND N.details IS NOT NULL " + "AND N.status = 'A' " + "AND NR.GROUP_ID = ? "
				// For Db2
//				+ "AND DAYS(N.EXPIRY_DATE) >= DAYS(CURRENT TIMESTAMP) " + "AND NR.USER_ID IN (?,"
				// For Oracle
				+ "AND TRUNC(N.EXPIRY_DATE) >= TRUNC(CURRENT_TIMESTAMP) " + "AND NR.USER_ID IN (?,"
				+ ICMSConstant.LONG_INVALID_VALUE + ") " + "AND N.NOTIFICATION_ID NOT IN "
				+ "(SELECT NOTIFICATION_ID FROM USER_DELETED_NOTIFICATION WHERE USER_ID = ?) ";
		DefaultLogger.debug(this, "NotiQuery: " + query);
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			dbUtil.setLong(1, teamID);
			dbUtil.setLong(2, userID);
			dbUtil.setLong(3, userID);

			ResultSet rs = dbUtil.executeQuery();
			if (rs.next()) {
				notificationsCount = rs.getInt("TOTAL_COUNT");
			}
			rs.close();
			DefaultLogger.debug(this, "<<<<<<<<< notificationsCount: " + notificationsCount);
			return notificationsCount;
		}
		catch (Exception e) {
			throw new CMSNotificationException(e);
		}
		finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				throw new CMSNotificationException(e);
			}
		}
	}

	/**
	 * get notifications by team id
	 * @param teamID
	 * @return
	 * @throws CMSNotificationException
	 */
	public ArrayList getNotificationsByTeamID(long userID, long teamID, String status, String legalName, long leID)
			throws CMSNotificationException {
		DefaultLogger.debug(this, "TeamID :" + teamID + " - UserID : " + userID);
		ArrayList notificationList = new ArrayList();
		DBUtil dbUtil = null;
		String query = "";
		// CR-120 Search Notification
		if ((status != null) && status.equals(ICMSConstant.STATE_NOTIFICATION_DELETED)) {
			query = "SELECT n.* FROM NOTIFICATION n, USER_DELETED_NOTIFICATION un "
					// For DB2
//					+ "WHERE DAYS(N.EXPIRY_DATE) >= DAYS(CURRENT TIMESTAMP) "
					// For Oracle
					+ "WHERE TRUNC(N.EXPIRY_DATE) >= TRUNC(CURRENT_TIMESTAMP) "
					+ "AND UN.NOTIFICATION_ID = N.NOTIFICATION_ID AND UN.USER_ID = ? " + "AND n.status = 'A' ";
		}
		else {
			query = "SELECT N.* FROM NOTIFICATION N, NOTIFICATION_RECIPIENT nr "
					+ "WHERE  N.NOTIFICATION_ID = NR.NOTIFICATION_ID " + "AND N.details IS NOT NULL "
					+ "AND N.status = 'A' " + "AND NR.GROUP_ID = ? "
					// For Db2
//					+ "AND DAYS(N.EXPIRY_DATE) >= DAYS(CURRENT TIMESTAMP) " + "AND NR.USER_ID IN (?,"
					// For Oracle
					+ "AND TRUNC(N.EXPIRY_DATE) >= TRUNC(CURRENT_TIMESTAMP) " + "AND NR.USER_ID IN (?,"
					+ ICMSConstant.LONG_INVALID_VALUE + ") " + "AND N.NOTIFICATION_ID NOT IN "
					+ "(SELECT NOTIFICATION_ID FROM USER_DELETED_NOTIFICATION WHERE USER_ID = ?) ";
		}
		if ((legalName != null) && !legalName.equals("")) {
			query += " AND UPPER(N.LE_NAME) LIKE '" + legalName.trim().toUpperCase() + "%' ";
		}
		if ((leID != 0) && (leID != ICMSConstant.LONG_INVALID_VALUE)) {
			query += " AND N.LE_ID = " + leID;
		}
		query += " ORDER BY CREATION_DATE DESC";

		DefaultLogger.debug(this, query);
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			if ((status != null) && status.equals(ICMSConstant.STATE_NOTIFICATION_DELETED)) {
				dbUtil.setLong(1, userID);
			}
			else {
				dbUtil.setLong(1, teamID);
				dbUtil.setLong(2, userID);
				dbUtil.setLong(3, userID);
			}

			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				OBNotification notification = this.mapNotificationSummary(rs);
				notificationList.add(notification);
			}
			rs.close();
			return notificationList;
		}
		catch (Exception e) {
			throw new CMSNotificationException(e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new CMSNotificationException(e);
			}
		}
	}

	public PaginationResult getNotificationsByTeamID(long userID, long teamID, String status, String legalName,
			long leID, PaginationBean pgBean) throws CMSNotificationException {
		DefaultLogger.debug(this, "TeamID :" + teamID + " - UserID : " + userID);
		ArrayList notificationList = new ArrayList();
		DBUtil dbUtil = null;
		String query = "";
		String paginQuery = null;
		String countQuery = null;
		// CR-120 Search Notification
		if ((status != null) && status.equals(ICMSConstant.STATE_NOTIFICATION_DELETED)) {
			query = "SELECT n.* FROM NOTIFICATION n, USER_DELETED_NOTIFICATION un "
					// For DB2
//					+ "WHERE DAYS(N.EXPIRY_DATE) >= DAYS(CURRENT TIMESTAMP) "
					// For Oracle
					+ "WHERE TRUNC(N.EXPIRY_DATE) >= TRUNC(CURRENT_TIMESTAMP) "
					+ "AND UN.NOTIFICATION_ID = N.NOTIFICATION_ID AND UN.USER_ID = ? " + "AND n.status = 'A' ";
		}
		else {
			query = "SELECT N.* FROM NOTIFICATION N, NOTIFICATION_RECIPIENT nr "
					+ "WHERE  N.NOTIFICATION_ID = NR.NOTIFICATION_ID " + "AND N.details IS NOT NULL "
					+ "AND N.status = 'A' " + "AND NR.GROUP_ID = ? "
					// For Db2
//					+ "AND DAYS(N.EXPIRY_DATE) >= DAYS(CURRENT TIMESTAMP) " + "AND NR.USER_ID IN (?,"
					// For Oracle
					+ "AND TRUNC(N.EXPIRY_DATE) >= TRUNC(CURRENT_TIMESTAMP) " + "AND NR.USER_ID IN (?,"
					+ ICMSConstant.LONG_INVALID_VALUE + ") " + "AND N.NOTIFICATION_ID NOT IN "
					+ "(SELECT NOTIFICATION_ID FROM USER_DELETED_NOTIFICATION WHERE USER_ID = ?) ";
		}
		if ((legalName != null) && !legalName.equals("")) {
			query += " AND UPPER(N.LE_NAME) LIKE '" + legalName.trim().toUpperCase() + "%' ";
		}
		if ((leID != 0) && (leID != ICMSConstant.LONG_INVALID_VALUE)) {
			query += " AND N.LE_ID = '" + leID + "'";
		}
		query += " ORDER BY CREATION_DATE DESC";
		//Changing the pagination util type for oracle
		PaginationUtil pgUtil = new PaginationUtilFactory().getPaginationUtil(PaginationUtilFactory.DBTYPE_ORACLE);
		paginQuery = pgUtil.formPagingQuery(query, pgBean);

		DefaultLogger.debug(this, query);
		try {
			dbUtil = new DBUtil();
			PaginationResult res = new PaginationResult();
			// first time we will retrieve the count
			if (pgBean.getTotalCount() < 0) {
				long totalCount = 0;
				countQuery = pgUtil.formCountQuery(query);
				dbUtil.setSQL(countQuery);
				if ((status != null) && status.equals(ICMSConstant.STATE_NOTIFICATION_DELETED)) {
					dbUtil.setLong(1, userID);
				}
				else {
					dbUtil.setLong(1, teamID);
					dbUtil.setLong(2, userID);
					dbUtil.setLong(3, userID);
				}

				ResultSet rs = dbUtil.executeQuery();
				if (rs.next()) {
					totalCount = rs.getLong(1);
				}
				rs.close();
				res.setCount(totalCount);
			}
			else {
				res.setCount(pgBean.getTotalCount());
			}
			dbUtil.setSQL(paginQuery);
			if ((status != null) && status.equals(ICMSConstant.STATE_NOTIFICATION_DELETED)) {
				dbUtil.setLong(1, userID);
			}
			else {
				dbUtil.setLong(1, teamID);
				dbUtil.setLong(2, userID);
				dbUtil.setLong(3, userID);
			}

			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				OBNotification notification = this.mapNotificationSummary(rs);
				notificationList.add(notification);
			}
			rs.close();
			res.setResultList(notificationList);
			return res;
		}
		catch (Exception e) {
			throw new CMSNotificationException(e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new CMSNotificationException(e);
			}
		}
	}

	/**
	 * get notification by id
	 * @param notificationID
	 * @param status
	 * @return
	 * @throws CMSNotificationException
	 */
	public INotification getNotificationByID(long notificationID) throws CMSNotificationException {
		INotification notification = null;
		DBUtil dbUtil = null;
		String query = "SELECT * FROM NOTIFICATION WHERE NOTIFICATION_ID = ?";
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			dbUtil.setLong(1, notificationID);
			ResultSet rs = dbUtil.executeQuery();
			if (rs.next()) {
				notification = this.mapNotification(rs);
			}
			rs.close();
			return notification;
		}
		catch (Exception e) {
			throw new CMSNotificationException(e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new CMSNotificationException(e);
			}
		}
	}

	/**
	 * update notification status
	 * @param notifyIDArray
	 * @param status
	 * @throws CMSNotificationException
	 */
	public void updateNotificationStatus(String notifyIDArray, String status) throws CMSNotificationException {
		DBUtil dbUtil = null;
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("UPDATE NOTIFICATION SET NOTIFICATION_STATUS = ?, ");
		strBuffer.append("LAST_UPDATE_DATE = ? ");
		strBuffer.append("WHERE NOTIFICATION_ID IN (");
		strBuffer.append(notifyIDArray + ")");
		DefaultLogger.debug(this, strBuffer.toString());
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(strBuffer.toString());
			dbUtil.setString(1, status);
			dbUtil.setTimestamp(2, new Timestamp(DateUtil.getDate().getTime()));
			dbUtil.executeUpdate();
		}
		catch (Exception se) {
			throw new CMSNotificationException(se);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new CMSNotificationException(e);
			}
		}
	}

	/**
	 * delete notification by batch job
	 * @throws CMSNotificationException
	 */
	public void deleteNotification() throws CMSNotificationException {
		DBUtil dbUtil = null;
		// For Db2
//		String query1 = "DELETE FROM NOTIFICATION_RECIPIENT NR WHERE NR.NOTIFICATION_ID IN (SELECT N.NOTIFICATION_ID FROM NOTIFICATION N WHERE N.NOTIFICATION_STATUS = ? AND (DAYS(CURRENT TIMESTAMP) - DAYS(N.LAST_UPDATE_DATE)) >= 3)";
//		String query2 = "DELETE FROM NOTIFICATION WHERE NOTIFICATION_STATUS = ? AND (DAYS(CURRENT TIMESTAMP) - DAYS(LAST_UPDATE_DATE)) >= 3";
		// For Oracle
		String query1 = "DELETE FROM NOTIFICATION_RECIPIENT NR WHERE NR.NOTIFICATION_ID IN (SELECT N.NOTIFICATION_ID FROM NOTIFICATION N WHERE N.NOTIFICATION_STATUS = ? AND (TRUNC(CURRENT_TIMESTAMP) - TRUNC(N.LAST_UPDATE_DATE)) >= 3)";
		String query2 = "DELETE FROM NOTIFICATION WHERE NOTIFICATION_STATUS = ? AND (TRUNC(CURRENT_TIMESTAMP) - TRUNC(LAST_UPDATE_DATE)) >= 3";

		DefaultLogger.debug(this, query1);
		DefaultLogger.debug(this, query2);
		try {
			dbUtil = new DBUtil();
			// delete child table
			dbUtil.setSQL(query1);
			dbUtil.setString(1, ICMSConstant.STATE_NOTIFICATION_DELETED);
			dbUtil.executeUpdate();
			// delete parent table
			dbUtil.setSQL(query2);
			dbUtil.setString(1, ICMSConstant.STATE_NOTIFICATION_DELETED);
			dbUtil.executeUpdate();
		}
		catch (Exception se) {
			throw new CMSNotificationException(se);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new CMSNotificationException(e);
			}
		}
	}

	/**
	 * put notification into delete list
	 * @param notification
	 * @throws SQLException
	 */
	public void deleteNotification(OBCMSNotification notification) throws CMSNotificationException {
		DBUtil dbUtil = null;
		String query = "INSERT INTO USER_DELETED_NOTIFICATION VALUES (?,?,?)";
		long userID = notification.getUserID();
		Timestamp deletedDate = new Timestamp(System.currentTimeMillis());
		String[] notificationIDs = notification.getNotificationIDs();
		if ((notificationIDs != null) && (notificationIDs.length != 0)) {
			try {
				dbUtil = new DBUtil();
				for (int i = 0; i < notificationIDs.length; i++) {
					dbUtil.setSQL(query);
					dbUtil.setLong(1, userID);
					dbUtil.setLong(2, Long.parseLong(notificationIDs[i]));
					dbUtil.setTimestamp(3, deletedDate);
					dbUtil.executeUpdate();
				}
			}
			catch (Exception se) {
				throw new CMSNotificationException(se);
			}
			finally {
				try {
					dbUtil.close();
				}
				catch (SQLException e) {
					throw new CMSNotificationException(e);
				}
			}
		}
	}

	/**
	 * get notification receipients team id list
	 * @param teamTypeIDList String[]
	 * @param countryList String[]
	 * @param segment String
	 * @return teamIDList Long[]
	 * @throws CMSNotificationException
	 */
	public Long[] getReceipientsTeamIDList(String[] teamTypeIDList, String[] countryList, String segment)
			throws CMSNotificationException {
		DBUtil dbUtil = null;
		StringBuffer query = new StringBuffer(
				"SELECT DISTINCT tms.team_id "
						+ "FROM cms_team_type_membership ttms, cms_team_country_code tcc, cms_team_membership tms ");
		if (PropertiesConstantHelper.requireBizSegment()) {
			query.append( "LEFT OUTER JOIN cms_team_segment_code tsc ON tms.team_id = tsc.team_id  ");
		}
			query.append("WHERE ttms.team_type_membership_id = tms.team_type_membership_id ");
			query.append( "AND tms.team_id = tcc.team_id ");

		ArrayList params = new ArrayList();
		if ((teamTypeIDList != null) && (teamTypeIDList.length > 0)) {
			query.append(" AND ttms.team_type_id ");
			CommonUtil.buildSQLInList(teamTypeIDList, query, params);
		}
		if ((countryList != null) && (countryList.length > 0) && (countryList[0] != null)) {
			query.append(" AND tcc.country_code ");
			CommonUtil.buildSQLInList(countryList, query, params);
		}
		if ((segment != null) && (segment.trim().length() > 0)) {
			query.append(" AND tsc.segment_code = ? ");
			params.add(segment);
		}

		String sql = query.toString();
		DefaultLogger.debug(this, sql);
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList arrList = new ArrayList();
			while (rs.next()) {
				Long teamID = new Long(rs.getLong("TEAM_ID"));
				arrList.add(teamID);
			}
			rs.close();
			return (Long[]) arrList.toArray(new Long[0]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CMSNotificationException(e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new CMSNotificationException(e);
			}
		}
	}

	/**
	 * map notification summary from db table
	 * @param rs
	 * @return
	 */
	private OBNotification mapNotificationSummary(ResultSet rs) throws SQLException {
		OBNotification notification = new OBNotification();
		notification.setNotificationID(rs.getLong("NOTIFICATION_ID"));
		notification.setCreationDate(rs.getTimestamp("CREATION_DATE"));

		// set notification type
		OBNotificationType nType = new OBNotificationType();
		nType.setNotificationTitle(rs.getString("TITLE"));
		notification.setNotificationTypeData(nType);
		return notification;
	}

	/**
	 * map notification from db table
	 * @param rs
	 * @return
	 */
	private OBNotification mapNotification(ResultSet rs) throws SQLException {
		OBNotification notification = new OBNotification();
		notification.setNotificationID(rs.getLong("NOTIFICATION_ID"));
		notification.setCreationDate(rs.getTimestamp("CREATION_DATE"));
		notification.setEffectiveDate(rs.getDate("EFFECTIVE_DATE"));
		notification.setExpiryDate(rs.getDate("EXPIRY_DATE"));

		// // merging the message parts into one
		StringBuffer buf = new StringBuffer();
		String msgPart = rs.getString("MESSAGE");
		if (msgPart != null) {
			buf.append(msgPart);
		}
		msgPart = rs.getString("MESSAGE_PART_2");
		if (msgPart != null) {
			buf.append(msgPart);
		}
		msgPart = rs.getString("MESSAGE_PART_3");
		if (msgPart != null) {
			buf.append(msgPart);
		}
		msgPart = rs.getString("MESSAGE_PART_4");
		if (msgPart != null) {
			buf.append(msgPart);
		}
		msgPart = rs.getString("MESSAGE_PART_5");
		if (msgPart != null) {
			buf.append(msgPart);
		}
		String msgStr = buf.toString();
		if ((msgStr == null) || msgStr.trim().equals("")) {
			msgStr = getNotificationMsg(rs);
		}
		notification.setNotificationMessage(msgStr);

		notification.setOriginatorID(rs.getLong("ORIGINATOR_ID"));
		notification.setUrl(rs.getString("URL"));
		// set notification state
		// String notificationState = rs.getString("NOTIFICATION_STATE");
		// NotificationStateEnum state =
		// NotificationStateEnum.parse(notificationState.trim());
		// notification.setNotificationState(state);
		notification.setPastNotificationID(rs.getLong("PAST_NOTIFICATION_ID"));
		// notification.setMessageObject(rs.getString("MESSAGE_OBJECT"));
		// set notification type
		OBNotificationType nType = new OBNotificationType();
		nType.setNotificationTypeID(rs.getLong("NOTIFICATION_TYPE_ID"));
		nType.setNotificationTypeCode(rs.getString("NOTIFICATION_TYPE_CODE"));
		nType.setNotificationTypeName(rs.getString("NOTIFICATION_TYPE_NAME"));
		nType.setNotificationTitle(rs.getString("TITLE"));
		nType.setRecurring(rs.getBoolean("IS_RECURRING"));
		nType.setSeverity(rs.getInt("SEVERITY"));
		String channels[] = Helper.decodeChannels(rs.getString("CHANNELS"));
		nType.setNotificatonChannels(channels);
		notification.setNotificationTypeData(nType);
		return notification;
	}

	public String getNotificationMsg(ResultSet rs) throws SQLException {
		try {
			StringBuffer msgBuffer = new StringBuffer();
			java.sql.Clob cLobMsg = (java.sql.Clob) rs.getObject("MESSAGE_OBJECT");
			if (cLobMsg != null) {
				BufferedReader clobBuff = new BufferedReader(cLobMsg.getCharacterStream());
				String strClob = clobBuff.readLine();
				while (strClob != null) {
					msgBuffer.append(strClob);
					// sbResult.append("&");
					strClob = clobBuff.readLine();
				}
			}
			return msgBuffer.toString();
		}
		catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}

	// private Writer getWriter(java.sql.Clob lob) throws SQLException {
	// Writer aWriter = null;
	// try {
	// aWriter = (Writer)
	// lob.getClass().getMethod("getCharacterOutputStream",null
	// ).invoke(lob,null);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return (Writer)aWriter;
	// }
}
