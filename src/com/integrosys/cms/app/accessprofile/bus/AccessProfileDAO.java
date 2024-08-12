/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/accessprofile/bus/AccessProfileDAO.java,v 1.9 2005/10/27 06:30:39 lyng Exp $
 */
package com.integrosys.cms.app.accessprofile.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;

/**
 * Contains data access logic for the access profile table.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.9 $
 * @since $Date: 2005/10/27 06:30:39 $ Tag: $Name: $
 */
public class AccessProfileDAO implements IAccessProfileDAO {
	private static String SELECT_FAP = "select * from CMS_FUNCTION_ACCESS";

	private static final String FAP_ACTION = "ACTION";

	private static final String FAP_EVENT = "EVENT";

	private static final String FAP_ROLE_TYPE = "ROLE_TYPE";

	private static final String FAP_ID = "ID";

	/**
	 * Gets the list of access profile records that matches on
	 * <code>action</code>, <code>event</code>, <code>role</code>. If and only
	 * if any of the input argument is <b><code>null</code></b>, then that
	 * argument <b>will not</b> be used as part of the retrieval condition.
	 * 
	 * @param action function action
	 * @param event function event
	 * @param role user rule
	 * @return an array of IAccessProfile objects
	 */
	public IAccessProfile[] getAccessProfiles(String action, String event, long role) {
		ArrayList list = new ArrayList();
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			constructFAPStatement(dbUtil, action, event, role);
			ResultSet rs = dbUtil.executeQuery();
			processFAPResultSet(rs, list);
		}
		catch (Exception e) {
			DefaultLogger.warn(this, "EXCEPTION when querying FUNCTION ACCESS TABLE!", e);
			// continue to return.
		}
		finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
				// Continue to return.
			}
		}
		return (IAccessProfile[]) list.toArray(new IAccessProfile[0]);
	}

	/**
	 * Get all the access function records.
	 * 
	 * @return a List of access function records
	 */
	public Collection getAccessProfileCollection() {
		DBUtil dbUtil = null;
		ArrayList list = new ArrayList();

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SELECT_FAP);
			ResultSet rs = dbUtil.executeQuery();
			processFAPResultSet(rs, list);
		}
		catch (Exception e) {
			DefaultLogger.warn(this, "EXCEPTION when querying FUNCTION ACCESS TABLE!", e);
			// continue to return.
		}
		finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
				// Continue to return.
			}
		}
		return list;
	}

	/**
	 * Helper method to process FAP result set.
	 * 
	 * @param rs FAP records
	 * @param list an array of access profile objects
	 * @throws Exception on any errors encountered
	 */
	private void processFAPResultSet(ResultSet rs, ArrayList list) throws Exception {
		while (rs.next()) {
			IAccessProfile fap = new AccessProfile();
			fap.setId(rs.getLong(FAP_ID));
			fap.setAction(rs.getString(FAP_ACTION));
			fap.setEvent(rs.getString(FAP_EVENT));
			fap.setRoleType(rs.getLong(FAP_ROLE_TYPE));
			list.add(fap);
		}
		rs.close();
	}

	/**
	 * Helper method to construct FAP Statement given the function action,
	 * event, and user role type.
	 * 
	 * @param action of type String
	 * @param event of type String
	 * @param roleType of type long
	 * @throws Exception on error preparing the fap statement
	 */
	private void constructFAPStatement(DBUtil dbUtil, String action, String event, long roleType) throws Exception {
		StringBuffer sql = new StringBuffer(SELECT_FAP);
		int firstIdx = 0, secondIdx = 0, thirdIdx = 0;

		if (action == null) {
			sql.append(" and ").append(FAP_ACTION).append(" is null");
		}
		else {
			sql.append(" and ").append(FAP_ACTION).append(" = ?");
			firstIdx = 1;
		}

		if (event == null) {
			sql.append(" and ").append(FAP_EVENT).append(" is null");
		}
		else {
			sql.append(" and ").append(FAP_EVENT).append(" = ?");
			secondIdx = firstIdx + 1;
		}
		sql.append(" and ").append(FAP_ROLE_TYPE).append(" = ?");
		thirdIdx = secondIdx + 1;

		if (firstIdx != 0) {
			dbUtil.setString(firstIdx, action);
		}
		if (secondIdx != 0) {
			dbUtil.setString(secondIdx, event);
		}
		dbUtil.setLong(thirdIdx, roleType);
	}
	
	public void setUserLastAccessTime (String loginID) {
		if (loginID == null)
			return;
		
		String sqlstmt = "UPDATE CMS_AUTHENTICATION SET LAST_ACCESS_TIME = ? WHERE LOGIN_ID = ?";
		DBUtil dbUtil = null;
		
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sqlstmt);
			//java.sql.Date date= new java.sql.Date(DateUtil.getDate().getTime());
			//date.setTime(DateUtil.getDate().getTime());
			//dbUtil.setDate(1, date, Calendar.getInstance());
			 Date d = DateUtil.getDate();
             
			// By abhijit r : need to add code from general param.
			                IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			    		//	IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			    		//	IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			                IGeneralParamEntry generalParamEntries= generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			    			Date date=new Date();
			    		/*	for(int i=0;i<generalParamEntries.length;i++){
			    				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
			    					 date=new Date(generalParamEntries[i].getParamValue());
			    				}
			    			}*/
			    			if(generalParamEntries!=null){
		    					 date=new Date(generalParamEntries.getParamValue());
		    				}
			    			
			    			//date.setTime(d.getTime());
			    			date.setHours(d.getHours());
			    			date.setMinutes(d.getMinutes());
			    			date.setSeconds(d.getSeconds());
			dbUtil.setTimestamp(1,new Timestamp(date.getTime()));
			dbUtil.setString(2, loginID);
			
			dbUtil.executeUpdate();
			
		} catch (Exception e) {
				DefaultLogger.warn(this, "EXCEPTION when update user last access time!", e);
				// continue to return.
		}
		finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
				// Continue to return.
			}
		}		
	}
}
