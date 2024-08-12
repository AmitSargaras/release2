package com.integrosys.cms.ui.login;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.login.IAuthenticator;
import com.integrosys.base.businfra.login.ICredentials;
import com.integrosys.base.businfra.login.ILoginInfo;
import com.integrosys.base.businfra.login.IPolicy;
import com.integrosys.base.businfra.login.OBCredentials;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;
import com.integrosys.component.bizstructure.app.bus.BizStructureException;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.login.app.LoginErrorCodes;
import com.integrosys.component.login.app.SSLTokenController;
import com.integrosys.component.login.app.SessionUpdateException;
import com.integrosys.component.login.app.SessionValidatorException;
import com.integrosys.component.login.ui.FormUtil;
import com.integrosys.component.login.ui.GlobalSessionConstant;
import com.integrosys.component.login.ui.IPageReferences;
import com.integrosys.component.login.ui.LoginForm;
import com.integrosys.component.login.ui.LoginProcessException;
import com.integrosys.component.login.ui.SessionUtil;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.proxy.ICommonUserProxy;

public class SSLLoginAction extends Action implements IPageReferences {

	private ICMSLoginManager loginManager;

	private IAuthenticator authenticator;

	private ICommonUserProxy userProxy;

	private ICMSTeamProxy cmsTeamProxy;

	/**
	 * @param authenticator the authenticator to set
	 */
	public void setAuthenticator(IAuthenticator authenticator) {
		this.authenticator = authenticator;
	}

	/**
	 * @return the authenticator
	 */
	public IAuthenticator getAuthenticator() {
		return authenticator;
	}

	/**
	 * @return the loginManager
	 */
	public ICMSLoginManager getLoginManager() {
		return loginManager;
	}

	/**
	 * @return the userProxy
	 */
	public ICommonUserProxy getUserProxy() {
		return userProxy;
	}

	/**
	 * @return the bizStructureProxy
	 */
	public ICMSTeamProxy getCmsTeamProxy() {
		return cmsTeamProxy;
	}

	/**
	 * @param loginManager the loginManager to set
	 */
	public void setLoginManager(ICMSLoginManager loginManager) {
		this.loginManager = loginManager;
	}

	/**
	 * @param userProxy the userProxy to set
	 */
	public void setUserProxy(ICommonUserProxy userProxy) {
		this.userProxy = userProxy;
	}

	/**
	 * @param bizStructureProxy the bizStructureProxy to set
	 */
	public void setCmsTeamProxy(ICMSTeamProxy cmsTeamProxy) {
		this.cmsTeamProxy = cmsTeamProxy;
	}

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String eventStr = request.getParameter("event");
		DefaultLogger.debug(this, "Event String: " + eventStr);
		if ("verify".equals(eventStr)) {
			return performVerification(actionMapping, actionForm, request, response);
		}

		LoginRoleForm loginForm = (LoginRoleForm) actionForm;
		HttpSession session = request.getSession(true);
		session.invalidate();
		session = request.getSession();

		setLocale(request, request.getLocale());

		String ofaUserLoginID = null;
		if (loginForm.getUserName() != null) {
			ofaUserLoginID = loginForm.getUserName().toUpperCase().trim();
		}
		String ofaRoleID = loginForm.getRole();
		String password = loginForm.getPassword();
		String realm = loginForm.getRealm();
		String pinType = "1";
		String teamMemberShipID = loginForm.getTeamMemberShipID();
		String membershipID = loginForm.getMembershipID();

		ActionErrors errors = null;

		LoginValidator validator = new LoginValidator(realm, ofaRoleID);
		errors = validator.validateInput(ofaUserLoginID, password);
		if (errors != null) {
			Iterator itr = errors.properties();
			while (itr.hasNext()) {
				DefaultLogger.debug(this, "error itr: " + (String) itr.next());
			}
		}

		if ((errors != null) && !errors.isEmpty()) {
			saveErrors(request, errors);
			loginForm.setPassword(null);
			FormUtil formUtil = new FormUtil();
			formUtil.setForm(actionMapping, request, actionForm);
			return (actionMapping.findForward("reenter"));
		}

		OBCredentials credentials = new OBCredentials(ofaUserLoginID, password, pinType, realm);
		try {
			credentials.setRole(ofaRoleID);
			credentials.setLastLoginIp(request.getRemoteAddr());

			ILoginInfo loginInfo = getLoginManager().executeLoginProcess(credentials, request, response,
					teamMemberShipID, membershipID);
			DefaultLogger.debug(this, "ILoginInfo -" + loginInfo);

			SSLTokenController controller = new SSLTokenController();

			String token = controller.createToken(ofaUserLoginID);
			request.setAttribute("ssltoken", token);
			request.setAttribute("ofaUserLoginID", ofaUserLoginID);
			request.setAttribute("maxAgePasswordChange", String.valueOf(loginInfo.getMaxAgePasswordChange()));
			request.setAttribute("maxAgePasswordWarn", String.valueOf(loginInfo.getMaxAgePasswordWarn()));
			request.setAttribute("ofaRoleID", ofaRoleID);
			request.setAttribute("realm", realm);

			return (actionMapping.findForward("pre_verify"));
		}
		catch (LoginProcessException lpx) {
			DefaultLogger.debug(this, "Login Process Exception has occured" + lpx.getErrorCode(), lpx);
			loginForm.setPassword(null);

			errors = new ActionErrors();
			errors.add("loginError", new ActionMessage("error.string." + lpx.getErrorCode()));
			saveErrors(request, errors);

			FormUtil formUtil = new FormUtil();
			formUtil.setForm(actionMapping, request, actionForm);

			return (actionMapping.findForward("reenter"));
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Failed to execute login via SSL", e);
			throw new ServletException("Failed to execute login via SSL", e);
		}
		finally {
			credentials = null;
		}
	}

	private ActionForward performVerification(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		LoginForm loginForm = (LoginForm) actionForm;
		HttpSession session = request.getSession(true);
		session.invalidate();
		session = request.getSession();

		// set language locale
		if (loginForm.getLocale() != null) {
			if ("th".equals(loginForm.getLocale())) {
				setLocale(request, new Locale("th", "TH"));
			}
			else {
				setLocale(request, Locale.US);
			}
		}
		else {
			setLocale(request, Locale.US);
		}

		String ofaUserLoginID = request.getParameter("ofaUserLoginID");
		String maxAgePasswordWarn = request.getParameter("maxAgePasswordWarn");
		String maxAgePasswordChange = request.getParameter("maxAgePasswordChange");
		Boolean changeBool = new Boolean(maxAgePasswordChange);
		Boolean warnBool = new Boolean(maxAgePasswordWarn);
		String roleID = request.getParameter("ofaRoleID");
		String token = request.getParameter("ssltoken");
		String realm = request.getParameter("realm");

		DefaultLogger.debug(this, "ofaUserLoginID = " + ofaUserLoginID);
		DefaultLogger.debug(this, "maxAgePasswordChange = " + maxAgePasswordChange);
		DefaultLogger.debug(this, "maxAgePasswordWarn = " + maxAgePasswordWarn);
		DefaultLogger.debug(this, "ofaRoleID = " + roleID);
		DefaultLogger.debug(this, "ssltoken = " + token);

		ActionErrors errors = null;

		try {
			SSLTokenController controller = new SSLTokenController();
			controller.verifyToken(ofaUserLoginID, token);

			// next get user info into session agian
			OBCredentials credentials = new OBCredentials();
			credentials.setLoginId(ofaUserLoginID);
			credentials.setRole(roleID);
			credentials.setRealm(realm);
			ILoginInfo loginInfo = controller.getLoginInfo(credentials);

			updateSession(credentials, loginInfo, request, response);

			SessionUtil sessionUtil = new SessionUtil();
			sessionUtil.updateSession(request);

			DefaultLogger.debug(this, "Updating Persisted Session - Done");
			request.setAttribute("isNewRequest", "1");

			ActionForward fwd = null;
			if (getLoginManager().requiredVerifyForcePasswordChange()) {
				fwd = verifyForceChange(actionMapping, request, changeBool.booleanValue(), warnBool.booleanValue());
			}
			DefaultLogger.debug(this, "verifyForceChange -" + fwd);
			if (fwd != null) {
				return fwd;
			}

			String welcomeURL = PropertyManager.getValue("login.normal.welcome.url");
			String normalPort = PropertyManager.getValue("system.normal.port");

			String fullWelcomeURL = "http://" + request.getServerName() + ":" + normalPort + request.getContextPath()
					+ welcomeURL;
			DefaultLogger.debug(this, "Value of welcomeURl is:" + fullWelcomeURL);
			ActionForward fw = new ActionForward(fullWelcomeURL, true);
			return fw;
		}
		catch (SessionUpdateException suex) {
			loginForm.setPassword(null);

			errors = new ActionErrors();
			errors.add("loginError", new ActionMessage("error.string.sessionupdation"));
			saveErrors(request, errors);

			FormUtil formUtil = new FormUtil();
			formUtil.setForm(actionMapping, request, actionForm);

			return (actionMapping.findForward("reenter"));
		}
		catch (SessionValidatorException sve) {
			loginForm.setPassword(null);

			errors = new ActionErrors();
			errors.add("loginError", new ActionMessage("error.string.sessionupdation"));
			saveErrors(request, errors);

			FormUtil formUtil = new FormUtil();
			formUtil.setForm(actionMapping, request, actionForm);

			return (actionMapping.findForward("reenter"));
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Unknown Exception!", e);
			throw new ServletException("Caught Unknown Exception!");
		}
	}

	private ActionForward verifyForceChange(ActionMapping actionMapping, HttpServletRequest request,
			boolean maxAgeChange, boolean maxAgeWarn) throws LoginProcessException {
		if (!PropertyManager.getBoolean("integrosys.sme.login.enabled")) {
			return null;
		}
		OBCredentials credentials = new OBCredentials();
		HttpSession session = request.getSession(false);
		credentials.setLoginId((String) session.getAttribute(GlobalSessionConstant.USER_LOGIN_ID));
		credentials.setRealm((String) session.getAttribute(GlobalSessionConstant.AUTHENTICATION_REALM));
		credentials.setRole((String) session.getAttribute(GlobalSessionConstant.AUTHENTICATION_ROLE));

		try {
			IPolicy policy = getAuthenticator().retrievePolicy(null, credentials);

			if (maxAgeChange) {
				DefaultLogger.info("SSLLoginAction.verifyForceChange", "Max Aged -- " + maxAgeChange);
				DefaultLogger.debug(this,"Max Aged -- " + maxAgeChange);
				session.setAttribute(GlobalSessionConstant.CHANGE_PASSWORD_IND, "change_pwd");
				request.setAttribute("expiry", "maxAgeChange");
				return actionMapping.findForward("change_pwd");
			}
			else if (policy.getForceLoginIdChange()) {
				DefaultLogger.info("SSLLoginAction.verifyForceChange", "getForceLoginIdChange -- "
						+ policy.getForceLoginIdChange());
				return actionMapping.findForward("change_id");
			}
			else if (policy.getForcePasswordChange()) {
				DefaultLogger.info("SSLLoginAction.verifyForceChange", "getForcePasswordChange -- "
						+ policy.getForcePasswordChange());
				session.setAttribute(GlobalSessionConstant.CHANGE_PASSWORD_IND, "change_pwd");
				return actionMapping.findForward("change_pwd");
			}
			else if (maxAgeWarn) {
				DefaultLogger.info("SSLLoginAction.verifyForceChange", "Max Warn -- " + maxAgeWarn);
				request.setAttribute("expiry", "maxAgeWarn");
				session.setAttribute(GlobalSessionConstant.CHANGE_PASSWORD_IND, "change_pwd");
				return actionMapping.findForward("change_pwd");
			}
			else if (policy.getForceSecondPasswordChange()) {
				return actionMapping.findForward("change_pwd2");
			}
			return null;
		}
		catch (Exception ex) {
			throw new LoginProcessException("Failed during force password change verification !", ex);
		}
	}

	public void setLocale(HttpServletRequest request, Locale locale) {
		DefaultLogger.debug(this, "Setting locale " + locale.toString() + " into session");
		HttpSession session = request.getSession();
		session.setAttribute(GlobalSessionConstant.LOCALE_PARAM, locale);
		super.setLocale(request, locale);
	}

	public ILoginInfo updateSession(ICredentials credentials, ILoginInfo loginInfo, HttpServletRequest request,
			HttpServletResponse response) throws LoginProcessException {
		HttpSession session = request.getSession(false);
		String usrLoginId = credentials.getLoginId();
		session.setAttribute(GlobalSessionConstant.USER_LOGIN_ID, usrLoginId);

		DefaultLogger.debug(this, "Setting session check key: "
				+ com.integrosys.base.uiinfra.common.Constants.SESSION_CHECK_USER_LOGIN_ID);
		session.setAttribute(com.integrosys.base.uiinfra.common.Constants.SESSION_CHECK_USER_LOGIN_ID, usrLoginId);
		session.setAttribute(GlobalSessionConstant.LASTLOGINTIME, loginInfo.getLastLoginTime());
		session.setAttribute(GlobalSessionConstant.LASTLOGOUTTIME, loginInfo.getLastLogoutTime());
		session.setAttribute(GlobalSessionConstant.AUTHENTICATION_ROLE, credentials.getRole());
		session.setAttribute(GlobalSessionConstant.AUTHENTICATION_REALM, credentials.getRealm());

		ICommonUser usr = null;
		try {
			usr = (OBCommonUser) getUserProxy().getUser(usrLoginId);
			session.setAttribute(GlobalSessionConstant.LOS_USER, usr);
		}
		catch (RemoteException rex) {
			LoginProcessException lpexp = new LoginProcessException(
					"not able to execute user proxy remote interface, throwing root cause", rex.getCause());
			lpexp.setErrorCode(LoginErrorCodes.GENERAL_LOGIN_ERROR);
			throw lpexp;
		}
		catch (EntityNotFoundException enfe) {
			LoginProcessException lpexp = new LoginProcessException("failed to retrieve user information, login id ["
					+ usrLoginId + "]", enfe);
			lpexp.setErrorCode(LoginErrorCodes.INVALID_CREDENTIALS);
			throw lpexp;
		}

		try {
			DefaultLogger.debug(this, "in InitLoginManager retrieving team info ");
			ITeam team = (ITeam) getCmsTeamProxy().getTeamByUserID(usr.getUserID());
			session.setAttribute(GlobalSessionConstant.TEAM, team);
			DefaultLogger.debug(this, "in InitLoginManager retrieved team set in session ");

			ITeamMembership membership = getCmsTeamProxy().getTeamMembershipByUserID(usr.getUserID());
			session.setAttribute(GlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_NAME, membership.getTeamTypeMembership()
					.getMembershipName());

			String teamType = team.getTeamType().getBusinessCode();
			String memType = membership.getTeamTypeMembership().getMembershipType().getMembershipTypeName();

			session.setAttribute(GlobalSessionConstant.TEAM_IDENTIFIER, teamType.toUpperCase() + memType.toUpperCase());

			DefaultLogger.debug(this, "InitLoginManager has set this team identifier in session: "
					+ teamType.toUpperCase() + memType.toUpperCase());
			return loginInfo;
		}
		catch (EntityNotFoundException enfe) {
			LoginProcessException lpexp = new LoginProcessException("Team Info not found in Database", enfe);
			lpexp.setErrorCode(LoginErrorCodes.USER_NOT_ASSIGNED_TEAM);
			throw lpexp;
		}
		catch (BizStructureException rex) {
			LoginProcessException lpexp = new LoginProcessException("BizStructureException while getting Team", rex);
			lpexp.setErrorCode(LoginErrorCodes.GENERAL_LOGIN_ERROR);
			throw lpexp;
		}

	}
}
