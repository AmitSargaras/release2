package com.integrosys.cms.ui.login;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.integrosys.base.businfra.login.AuthenticationException;
import com.integrosys.base.businfra.login.ICredentials;
import com.integrosys.base.businfra.login.ILoginInfo;
import com.integrosys.base.businfra.login.OBLoginInfo;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.exception.ExceptionUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranchDao;
import com.integrosys.cms.app.user.bus.StdUserDAO;
import com.integrosys.component.bizstructure.app.bus.BizStructureException;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.login.app.LoginErrorCodes;
import com.integrosys.component.login.ui.GlobalSessionConstant;
import com.integrosys.component.login.ui.LoginProcessException;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.bus.UserException;
import java.util.Date;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
public class InitCMSLoginManager extends LoginManagerAccessor implements ICMSLoginManager {

	public static final String DO_NOT_INVALIDATE_1ST_USER_SESSION = "integrosys.los.smeuser.not.invalidate.first.user.session";

	public ILoginInfo executeLoginProcess(ICredentials credentials, HttpServletRequest request,
			HttpServletResponse response, String teamMemberShipID, String membershipID) throws LoginProcessException {
		OBLoginInfo loginInfo = null;

		System.out.println("Inside InitCMSLoginManager.....");
		HttpSession session = request.getSession(false);
		try {
			if (PropertyManager.getBoolean(DO_NOT_INVALIDATE_1ST_USER_SESSION)) {

				boolean exists = getUserProxy().checkUserSessionExists(credentials.getLoginId());
				System.out.println("Inside InitCMSLoginManager...exists="+exists+" credentials.getLoginId()="+credentials.getLoginId());
				if (exists) {

					Timestamp loginTime = getLastAccessTime(credentials.getLoginId());
					long timeout = session.getMaxInactiveInterval() * 1000;
					long currentTime = DateUtil.getDate().getTime();

					if (loginTime != null && (currentTime - loginTime.getTime() < timeout)) {

						DefaultLogger.debug(this, "Before throw exception ");
						LoginProcessException lpexp = new LoginProcessException(
								"User Session Exists. You are already logged on. Please logoff the first session and try again.");
						lpexp.setErrorCode(LoginErrorCodes.USER_SESSION_EXISTS);
						throw lpexp;
					}
				}
			}

			System.out.println("Inside InitCMSLoginManager...before if... "+PropertyManager.getBoolean("integrosys." + credentials.getRealm() + ".login.enabled"));
			if (PropertyManager.getBoolean("integrosys." + credentials.getRealm() + ".login.enabled")) {
				loginInfo = (OBLoginInfo) getAuthenticationManager().authenticateAndLogin(credentials);
				System.out.println("Inside InitCMSLoginManager inside if.....getLoginId()="+loginInfo.getLoginId());
				if(loginInfo !=null){
					
					if(loginInfo.getLoginId() !=null){
				updateLogin(loginInfo.getLoginId(),credentials.getLastLoginIp(), "localhost");
					}
				}
			}
			else {
				System.out.println("Inside InitCMSLoginManager inside else.....getLoginId()="+credentials.getLoginId());
				loginInfo = new OBLoginInfo();
				loginInfo.setSessionExpiry(60000);
				loginInfo.setLoginId(credentials.getLoginId());
				loginInfo.setCompanyId(credentials.getCompanyId());
				loginInfo.setLastLoginTime(new java.util.Date());
				loginInfo.setLastLogoutTime(new java.util.Date());
			}
			session.setAttribute("contextPath", request.getContextPath());
			//Shiv 231212
			updateLoginAuthen(loginInfo.getLoginId(), "localhost",credentials.getLastLoginIp());
			
		}
		catch (AuthenticationException ex) {
			LoginProcessException lpe = new LoginProcessException(
					"fail authentication, is it correct user / password ? login id [" + credentials.getLoginId() + "]",
					ex);
			lpe.setErrorCode((ex.getErrorCode() != null) ? ex.getErrorCode() : LoginErrorCodes.GENERAL_LOGIN_ERROR);

			throw lpe;
		}
		catch (UserException ex) {
			LoginProcessException lpexp = new LoginProcessException("invalid user provided, login id ["
					+ credentials.getLoginId() + "]", ex);
			lpexp.setErrorCode(LoginErrorCodes.GENERAL_LOGIN_ERROR);
			throw lpexp;
		}
		catch (RemoteException rex) {
			ExceptionUtil.handleRemoteException(rex);
		}

		String usrLoginId = credentials.getLoginId();
		DefaultLogger.debug(this, "usrLoginId----->"+usrLoginId);
		session.setAttribute(GlobalSessionConstant.USER_LOGIN_ID, usrLoginId);
		DefaultLogger.debug(this, "Setting session check key: "
				+ com.integrosys.base.uiinfra.common.Constants.SESSION_CHECK_USER_LOGIN_ID);

		session.setAttribute(com.integrosys.base.uiinfra.common.Constants.SESSION_CHECK_USER_LOGIN_ID, usrLoginId);
		session.setAttribute(GlobalSessionConstant.LASTLOGINTIME,  getLastLoginTime(loginInfo.getLoginId()));
		session.setAttribute(GlobalSessionConstant.LASTLOGOUTTIME, loginInfo.getLastLogoutTime());
		session.setAttribute(GlobalSessionConstant.AUTHENTICATION_ROLE, credentials.getRole());
		session.setAttribute(GlobalSessionConstant.AUTHENTICATION_REALM, credentials.getRealm());
		//session.setAttribute("IP_ADDRESS", credentials.getLastLoginIp());

		ICommonUser usr = null;
		try {
			usr = (OBCommonUser) getUserProxy().getUser(usrLoginId);
			session.setAttribute(GlobalSessionConstant.LOS_USER, usr);

			//Locale ll = new Locale("EN", usr.getCountry());
			//session.setAttribute(ICommonEventConstant.GLOBAL_SCOPE + "."
			//		+ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, ll);

			DefaultLogger.debug(this, "in InitLoginManager retrieving user set in session ");
		}
		catch (EntityNotFoundException enfe) {
			LoginProcessException lpexp = new LoginProcessException("User Info not found in Database");
			lpexp.setErrorCode(LoginErrorCodes.INVALID_CREDENTIALS);
			throw lpexp;
		}
		catch (RemoteException rex) {
			ExceptionUtil.handleRemoteException(rex);
		}

		try {
			ITeam[] teams = getCmsTeamProxy().getTeamsByUserID(usr.getUserID());
			ITeamMembership[] memberships = getCmsTeamProxy().getTeamMembershipListByUserID(usr.getUserID());
			boolean makerCheckerSameUser = Boolean.valueOf(
					PropertyManager.getValue(ICMSConstant.MAKER_CHECKER_SAME_USER)).booleanValue();
			ITeamMembership membership = null;
			if (makerCheckerSameUser) {
				membership = validateMakerCheckerSelection(memberships, membershipID);
			}
			else {
				membership = validateTeamTypeMembershipRequested(memberships, teamMemberShipID);
			}
			// For HDFC bank to improve performance this piece of code is not used in hdfc bank . 06-04-2012
			//ITeam team = getSelectedTeam(membership, teams);
			//session.setAttribute(GlobalSessionConstant.TEAM, team);
			session.setAttribute(GlobalSessionConstant.TEAM, teams[0]);
			session.setAttribute(GlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_NAME, membership.getTeamTypeMembership()
					.getMembershipName());
			session.setAttribute(ICommonEventConstant.GLOBAL_SCOPE + "."
					+ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_TYPE_NAME, membership.getTeamTypeMembership()
					.getMembershipType().getMembershipTypeName());
			session.setAttribute(ICommonEventConstant.GLOBAL_SCOPE + "."
					+ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, String.valueOf(membership
					.getTeamTypeMembership().getMembershipID()));

			String MemberShipid = String.valueOf(membership.getTeamTypeMembership().getMembershipID());
			String systemHandOffId = PropertyManager.getValue("integrosys.systemHandOff.teamId");			
			String [] str = systemHandOffId.split(",");
			boolean flag = true;
			for(int i = 0;i<str.length ; i++)
			{
				if(str[i].equals(MemberShipid))
				{
					flag = false;
					break;
				}
			}
			if(flag)
			{
				StdUserDAO  stdUserDAO = new StdUserDAO();
				boolean flagHandoff  = true;
			try{
				 flagHandoff = stdUserDAO.getUserHandOffState();
			}catch(Exception e){e.printStackTrace();}
			if(flagHandoff==false){
				LoginProcessException lpexp = new LoginProcessException("User Info not found in Database");
				lpexp.setErrorCode("User_Hand_OFF");
			//	lpexp.setErrorCode("User Hand OFF in Process Please Try after some time");
				throw lpexp;	
			}
			}
			int teamCount = teams.length;
			if ((teamCount > 1) || (memberships.length > 1)) {
				session.setAttribute(ICommonEventConstant.GLOBAL_SCOPE + "." + CMSGlobalSessionConstant.MULTI_LOGIN,
						String.valueOf(teamCount));
			}
			// For HDFC bank to improve performance this piece of code is not used in hdfc bank . 06-04-2012
			//String teamType = team.getTeamType().getBusinessCode();// needs to
			String teamType = teams[0].getTeamType().getBusinessCode();// needs to
			String memType = membership.getTeamTypeMembership().getMembershipType().getMembershipTypeName();
			session.setAttribute(GlobalSessionConstant.TEAM_IDENTIFIER, teamType.toUpperCase() + memType.toUpperCase());
			DefaultLogger.debug(this, "InitLoginManager has set this team identifier in session: "
					+ teamType.toUpperCase() + memType.toUpperCase());
			try{
				if(usr!=null){

					String branchCode= usr.getEjbBranchCode();
					ISystemBankBranchDao systemBankBranchDao=(ISystemBankBranchDao) BeanHouse.get("systemBankBranchDao");
					ISystemBankBranch systemBankBranch = systemBankBranchDao.getSystemBankBranch(branchCode);
					if(systemBankBranch!=null){
						session.setAttribute(ICommonEventConstant.GLOBAL_SCOPE + ".branchObj", systemBankBranch);
					}	

				}
			}
		catch (Exception e) {
				e.printStackTrace();
			}
		
		// For HDFC bank to improve performance this piece of code is not used in hdfc bank . 06-04-2012
			/*List teamFunctionList = getTeamFunctionGrpProxy().getTeamFunctionGrpByTeamId(team.getTeamID());

			session.setAttribute(ICommonEventConstant.GLOBAL_SCOPE + "." + CMSGlobalSessionConstant.TEAM_FUNTION,
					teamFunctionList);*/

			return loginInfo;
		}
		catch (EntityNotFoundException enfe) {
			LoginProcessException lpexp = new LoginProcessException("Team Info not found in Database");
			lpexp.setErrorCode(LoginErrorCodes.USER_NOT_ASSIGNED_TEAM);
			throw lpexp;
		}
		catch (BizStructureException rex) {
			LoginProcessException lpexp = new LoginProcessException("BizStructureException while getting Team");
			lpexp.setErrorCode(LoginErrorCodes.GENERAL_LOGIN_ERROR);
			throw lpexp;
		}

	}

	private ITeam getSelectedTeam(ITeamMembership membership, ITeam[] teams) {
		for (int i = 0; i < teams.length; i++) {
			ITeamMembership[] teamMembership = teams[i].getTeamMemberships();
			for (int j = 0; j < teamMembership.length; j++) {
				if (membership.getTeamMembershipID() == teamMembership[j].getTeamMembershipID()) {
					return teams[i];
				}
			}
		}
		return null;
	}

	public ITeamMembership validateMakerCheckerSelection(ITeamMembership[] teamMemberShips, String membershipID)
			throws LoginProcessException {
		LoginProcessException lpexp = new LoginProcessException();
		if ((teamMemberShips == null) || (teamMemberShips.length == 0)) {
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}

		if (teamMemberShips.length > 1) {
			// the user has maker checker role
			if ((membershipID == null) || (membershipID.trim().length() == 0)) {
				lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_NOT_SELECTED_ERROR);
				throw lpexp;
			}

			for (int i = 0; i < teamMemberShips.length; i++) {
				long membershipIDlong = teamMemberShips[i].getTeamTypeMembership().getMembershipType()
						.getMembershipTypeID();
				String membershipIDString = new Long(membershipIDlong).toString();
				if (membershipID.equals(membershipIDString)) {
					return teamMemberShips[i];
				}
			}
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}
		return teamMemberShips[0];
	}

	public ITeamMembership validateTeamTypeMembershipRequested(ITeamMembership[] teamMemberShips,
			String teamMembershipReq) throws LoginProcessException {

		// check if Default and has multiple roles throw excp
		// if role req is not present

		LoginProcessException lpexp = new LoginProcessException();
		if ((teamMemberShips == null) || (teamMemberShips.length == 0)) {
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}

		if (teamMemberShips.length > 1) {
			if ((teamMembershipReq == null) || (teamMembershipReq.trim().length() == 0)) { // default
				lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_NOT_SELECTED_ERROR);
				throw lpexp;
			}
			for (int i = 0; i < teamMemberShips.length; i++) {
				long teamMembershipIDlong = teamMemberShips[i].getTeamTypeMembership().getMembershipID();
				String teamMembershipIDString = new Long(teamMembershipIDlong).toString();
				if (teamMembershipReq.equals(teamMembershipIDString)) {
					return teamMemberShips[i];
				}
			}
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}
		return teamMemberShips[0];
	}

	private Timestamp getLastAccessTime(String loginId) {
		String authenticationTbl = PropertyManager
				.getValue("component.user.table.authentication", "CMS_AUTHENTICATION");

		Timestamp lastAccessedTimeStamp = (Timestamp) getAuthenticationJdbcTemplate().query(
				"select auth.LAST_ACCESS_TIME from " + authenticationTbl + " auth where auth.login_id = ?",
				new Object[] { loginId }, new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getTimestamp("LAST_ACCESS_TIME");
						}

						return null;
					}
				});

		return lastAccessedTimeStamp;
	}

	public ILoginInfo executeLoginProcess(ICredentials credentials, HttpServletRequest request,
			HttpServletResponse response) throws LoginProcessException {
		return null;
	}

	public boolean requiredVerifyForcePasswordChange() {
		return isRequiredVerifyForcePasswordChange();
	}
	
private void updateLogin(String loginId,String ipAddress, String HostName){
		
		String server = PropertyManager.getValue("integrosys.server.identification");
		DBUtil dbUtil = null;
		
		String sql = "update CMS_LOGIN_AUDIT set server='"+server+"' , ip_address='"+ipAddress+ "' , host_name='"+HostName+
		"' where login_id='"+loginId+"' and logout_time is null";


		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
		
			int  rs = dbUtil.executeUpdate();
			dbUtil.commit();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in init login manager", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in init login manager", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in init login manager", ex);
			}
		}
		
	}
// Commented and Added by Dayananda For Login/Logout Audit Report Issue in Production || Fix
/*Private void updateLoginAuthen(String loginId, String HostName){
	
	DBUtil dbUtil = null;
	
	//String sql = "update CMS_AUTHENTICATION set host_name='"+HostName+
	//"' where login_id='"+loginId+"'";
	
	String sql = "update CMS_AUTHENTICATION set host_name='"+HostName+
	"', LAST_LOGIN_TIME = sysdate  where login_id='"+loginId+"'";


	try {
		dbUtil = new DBUtil();
		dbUtil.setSQL(sql);
	
		int  rs = dbUtil.executeUpdate();
		dbUtil.commit();
	}
	catch (SQLException ex) {
		throw new SearchDAOException("SQLException in init login manager", ex);
	}
	catch (Exception ex) {
		throw new SearchDAOException("Exception in init login manager", ex);
	}
	finally {
		try {
			dbUtil.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in init login manager", ex);
		}
	}
	
}*/

private void updateLoginAuthen(String loginId, String HostName,String lastLoginIp){
		System.out.println("Inside updateLoginAuthen().....");
		DBUtil dbUtil = null;
		Date dateUtl = DateUtil.getDate();
	  	IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
	    IGeneralParamEntry generalParamEntries= generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
		Date date=new Date();		    		
		if(generalParamEntries!=null){
			 date=new Date(generalParamEntries.getParamValue());
			 date.setHours(dateUtl.getHours());
 			 date.setMinutes(dateUtl.getMinutes());
 			 date.setSeconds(dateUtl.getSeconds());
		}
	

		String sql = "update CMS_AUTHENTICATION set host_name= ? , LAST_LOGIN_TIME = ? , LAST_LOGOUT_TIME = ? , LAST_LOGIN_IP = ?  where login_id= ? ";
		

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, HostName);
			dbUtil.setTimestamp(2,new Timestamp(date.getTime()));
			dbUtil.setTimestamp(3, null);
			dbUtil.setString(4, lastLoginIp);
			dbUtil.setString(5, loginId);

			int  rs = dbUtil.executeUpdate();
			dbUtil.commit();
		}
		catch (SQLException ex) {
			System.out.println("Inside updateLoginAuthen()..Error1()...");
		throw new SearchDAOException("SQLException in init login manager", ex);
	}
	catch (Exception ex) {
		System.out.println("Inside updateLoginAuthen()..Error2()...");
		throw new SearchDAOException("Exception in init login manager", ex);
	}
	finally {
		try {
			dbUtil.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in init login manager", ex);
		}
	}
	
}
//Commented and Added by Dayananda For Login/Logout Audit Report Issue in Production || Fix
private String getLastLoginTime(String loginId){
	
	DBUtil dbUtil = null;
	
	//String sql = "select date1 from (select seq,date1,rownum aa from (SELECT row_seq seq, TO_CHAR(TO_DATE(SUBSTR(login_time,1,9),'DD-MON-YY'),'DD-MON-YYYY')  || ' '  || TO_CHAR(login_time,'HH:MI AM') date1 FROM CMS_LOGIN_AUDIT WHERE LOGIN_STATUS = 'SUCCESS' AND LOGIN_ID ='"+loginId+"' order by row_seq desc )) where aa=2 ";
	String sql = "select date1 from (select seq,date1,rownum aa from (SELECT row_seq seq, TO_CHAR(TO_DATE(SUBSTR(login_time,1,9),'DD-MM-YY'),'DD-MM-YYYY') || ' '|| TO_CHAR(login_time,'HH:MI AM') date1 FROM CMS_LOGIN_AUDIT WHERE LOGIN_STATUS = 'SUCCESS' AND LOGIN_ID ='"+loginId+"' order by row_seq desc )) where aa=2 ";

	DefaultLogger.debug(this, "--------1---------"+sql);
	String data  = new String();
	ResultSet rs=null;

	try {
		dbUtil = new DBUtil();
		dbUtil.setSQL(sql);
	
		  rs = dbUtil.executeQuery();
		  if(rs.next()){
			  data= rs.getString(1);
			  
		  }
		return data;
	}
	catch (SQLException ex) {
		throw new SearchDAOException("SQLException in init login manager", ex);
	}
	catch (Exception ex) {
		throw new SearchDAOException("Exception in init login manager", ex);
	}
	finally {
		try {
			dbUtil.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in init login manager", ex);
		}
	}
	
}
}