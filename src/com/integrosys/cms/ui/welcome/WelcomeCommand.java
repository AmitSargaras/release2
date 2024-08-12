package com.integrosys.cms.ui.welcome;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.OBCommonUser;

public class WelcomeCommand extends AbstractCommand implements
		ICommonEventConstant {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER,
						"com.integrosys.component.user.app.bus.ICommonUser",
						GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM,
						"com.integrosys.component.bizstructure.app.bus.ITeam",
						GLOBAL_SCOPE },
				{ IGlobalConstant.TEAM_TYPE_MEMBERSHIP_TYPE_NAME,
						"java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_LASTLOGINTIME, "java.lang.String",
						GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER,
						"com.integrosys.component.user.app.bus.ICommonUser",
						GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM,
						"com.integrosys.component.bizstructure.app.bus.ITeam",
						GLOBAL_SCOPE },
				{ "lastLogin", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.TEAM_TYPE_MEMBERSHIP_TYPE_NAME,
						"java.lang.String", GLOBAL_SCOPE } });

	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap result = new HashMap();
		HashMap returnMap = new HashMap();
		String strLastLogin = null;

		OBCommonUser user = (OBCommonUser) map
				.get(IGlobalConstant.GLOBAL_LOS_USER);
		Locale locale = (Locale) map.get("locale");
		ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
		String teamMemTypeName = (String) map
				.get(IGlobalConstant.TEAM_TYPE_MEMBERSHIP_TYPE_NAME);
		result.put(IGlobalConstant.USER, user);
		result.put(IGlobalConstant.USER_TEAM, team);

		try {
			String lastLogin = (String) map
					.get(IGlobalConstant.GLOBAL_LASTLOGINTIME);
			DefaultLogger.debug(this, "<<<<< Last Login Time :>>>>>"
					+ lastLogin);
			if (user != null) {
				String newLastLogin = getLastLoginTime(user.getLoginID());

				DefaultLogger.debug(this, "<<<<<New Last Login Time :>>>>>"
						+ newLastLogin);
				if (newLastLogin != null) {
					strLastLogin = newLastLogin;
				} else if (lastLogin != null) {
					strLastLogin = lastLogin;
				}

			}

		} catch (Exception ex) {
			throw new CommandProcessingException(
					"failed to retrieve last login time, possible ?", ex);
		}

		result.put("lastLogin", strLastLogin);
		result.put(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_TYPE_NAME,
				teamMemTypeName);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return returnMap;

	}

	private String getLastLoginTime(String loginId) {

		DBUtil dbUtil = null;

		// String sql =
		// "select date1 from (select seq,date1,rownum aa from (SELECT row_seq seq, TO_CHAR(TRUNC(login_time,'DD-MON-YY'),'DD-MON-YYYY')  || ' '  || TO_CHAR(login_time,'HH:MI AM') date1 FROM CMS_LOGIN_AUDIT WHERE LOGIN_STATUS = 'SUCCESS' AND LOGIN_ID ='"+loginId+"' order by row_seq desc )) where aa=2 ";
	//	String sql = "select date1 from (select seq,date1,rownum aa from (SELECT row_seq seq, TO_CHAR(TO_DATE(trunc(login_time)),'DD-MON-YYYY')  || ' '  || TO_CHAR(login_time,'HH:MI AM') date1 FROM CMS_LOGIN_AUDIT WHERE LOGIN_STATUS = 'SUCCESS' AND LOGIN_ID ='"
	//			+ loginId + "' order by row_seq desc )) where aa=2 ";
		String sql = "select date1 from (select seq,date1,rownum aa from (SELECT row_seq seq, TO_CHAR(TO_DATE(SUBSTR(login_time,1,9),'DD-MM-YY'),'DD-MM-YYYY') || ' '|| TO_CHAR(login_time,'HH:MI AM') date1 FROM CMS_LOGIN_AUDIT WHERE LOGIN_STATUS = 'SUCCESS' AND LOGIN_ID ='"+loginId+"' order by row_seq desc )) where aa=2 ";

		DefaultLogger.debug(this, "--------1---------" + sql);
		String data = new String();
		ResultSet rs = null;

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);

			rs = dbUtil.executeQuery();
			if (rs.next()) {
				data = rs.getString(1);

			}
			rs.close();
			return data;
		} catch (SQLException ex) {
			throw new SearchDAOException("SQLException in init login manager",
					ex);
		} catch (Exception ex) {
			throw new SearchDAOException("Exception in init login manager", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in init login manager", ex);
			}
		}

	}
}
