package com.integrosys.cms.ui.login;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.login.IAuthenticator;
import com.integrosys.base.businfra.login.ILoginInfo;
import com.integrosys.base.businfra.login.IPolicy;
import com.integrosys.base.businfra.login.OBCredentials;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.proxy.IGeneralParamProxy;
import com.integrosys.component.login.app.LoginErrorCodes;
import com.integrosys.component.login.app.SessionUpdateException;
import com.integrosys.component.login.app.SessionValidatorException;
import com.integrosys.component.login.ui.FormUtil;
import com.integrosys.component.login.ui.GlobalSessionConstant;
import com.integrosys.component.login.ui.IPageReferences;
import com.integrosys.component.login.ui.LoginProcessException;
import com.integrosys.component.login.ui.SessionUtil;
import com.integrosys.component.user.app.constant.UserConstant;

public class LoginAction extends Action implements IPageReferences {

	private ICMSLoginManager loginManager;

	private IAuthenticator authenticator;
	
	private static final String HEADER_X_FORWARDED_FOR = "X-FORWARDED-FOR";
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

	public void setLoginManager(ICMSLoginManager loginManager) {
		this.loginManager = loginManager;
	}

	public ICMSLoginManager getLoginManager() {
		return loginManager;
	}

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		boolean isLoginSingleSignOnEnabled = PropertyManager
				.getBoolean("integrosys.login.single.signon.enabled", false);

		LoginRoleForm loginForm = (LoginRoleForm) actionForm;
		HttpSession session = request.getSession(true); 
		
		String ipAddress="";
		
		
		ipAddress=(String)session.getAttribute("addressIp");
		DefaultLogger.info(this, "-------------------------------IP Address from JSP------------------------- :"+ipAddress);
		
		session.invalidate();
		session = request.getSession();

		setLocale(request, request.getLocale());
		String ofaUserLoginID = null;
		if (loginForm.getUserName() != null) {
			ofaUserLoginID = loginForm.getUserName().toUpperCase().trim();
		}

		String ofaRoleID = loginForm.getRole();
		if (ofaRoleID == null) {
			ofaRoleID = "losuser";
		}

		String realm = loginForm.getRealm();
		if (realm == null) {
			realm = "sme";
		}

		String password = loginForm.getPassword();
/*		System.out.println("Inside LoginAction encrypted password : "+password);
		password=AES_EncryptionDecryption.decrypt(password);
		System.out.println("Inside LoginAction decrypted password : "+password);*/
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
			
			// Modified on 12-OCT-2012 By Abhijit R
			// Due to NLB setting request.getRemoteAddr() returns ip address of NLB server instead of client machine
			// Resolution : Trying to get client machine ip address by using InetAddress object
			// lines commented start:
			// credentials.setLastLoginIp(request.getRemoteAddr());
			// lines commented stop:
			
			
			// code change starts:
			InetAddress thisIp =InetAddress.getLocalHost();
			if(thisIp!=null){
				DefaultLogger.info(this, "-------------------------------IP Address from JAVA------------------------- :"+thisIp.getHostAddress());
				//credentials.setLastLoginIp(thisIp.getHostAddress());
			}
			
			ipAddress = getRemoteAddress(request);
			DefaultLogger.info(this,"-------------IP ADDRESS FROM X-FORWARDED-FOR PARAM:::"+ipAddress);
			if(ipAddress!=null){
				credentials.setLastLoginIp(ipAddress);
			}else{
			credentials.setLastLoginIp(request.getRemoteAddr());
			}
			
			// Code change ends.
			
			
			
			
			
			
			ILoginInfo loginInfo = getLoginManager().executeLoginProcess(credentials, request, response,teamMemberShipID, membershipID);
			DefaultLogger.debug(this, "ILoginInfo -" + loginInfo);

			SessionUtil sessionUtil = new SessionUtil();
			sessionUtil.updateSession(request);

			request.setAttribute("isNewRequest", "1");

			ActionForward fwd = null;
			if (getLoginManager().requiredVerifyForcePasswordChange()) {
				fwd = verifyForceChange(actionMapping, request, loginInfo.getMaxAgePasswordChange(), loginInfo
						.getMaxAgePasswordWarn());
			}

			DefaultLogger.info(this, "verifyForceChange -" + fwd);
			//Setting Max Inactive Interval i.e session timeout through general param	-- Anil Pandey		
			IGeneralParamProxy generalParamProxy  =(IGeneralParamProxy)BeanHouse.get("generalParamProxy");
			IGeneralParamEntry generalParamEntry = generalParamProxy.getGeneralParamEntryByParamCodeActual(IGeneralParamEntry.PARAM_SESSION_TIMEOUT);
			
			if(generalParamEntry!=null){
				int maxInactiveInterval=30;
				maxInactiveInterval= Integer.parseInt(generalParamEntry.getParamValue());
				session.setMaxInactiveInterval(maxInactiveInterval*60);
			}else{
				// by default MaxInactiveInterval will be 30 minutes(Defined in web.xml)
			}
			

			return (fwd == null) ? actionMapping.findForward("welcome") : fwd;

		}
		catch (LoginProcessException lpx) {
			DefaultLogger.warn(this, "Error encountered when initiate login process, error code [" + lpx.getErrorCode()
					+ "] " + lpx);
			errors = new ActionErrors();

			if (LoginErrorCodes.ACCT_EXPIRED.equals(lpx.getErrorCode())) {
				request.setAttribute("expiry", "maxAgeChange");
				// Andy Wong, 17 Nov 2008: set user login into session to
				// display in change password page
				session.setAttribute(GlobalSessionConstant.USER_LOGIN_ID, credentials.getLoginId());
				session.setAttribute(GlobalSessionConstant.CHANGE_PASSWORD_IND, "change_pwd");
				return actionMapping.findForward("change_pwd");
			}
            if(lpx.getErrorCode().equals("User_Hand_OFF"))
            {
            	errors.add("loginError", new ActionMessage("error.string.user.hand.off"));
            	//errors.add("loginError", new ActionMessage("User Hand OFF in Process Please Login after some time"));
            }
			// Andy Wong, 1 Dec 2008: display AS400 message directly if error
			// code/desc not captured
			if (isLoginSingleSignOnEnabled && LoginErrorCodes.GENERAL_LOGIN_ERROR.equals(lpx.getErrorCode())) {
				String[] errorMessages = StringUtils.split(lpx.getMessage().toString(), ':');
				errors.add("loginError", new ActionMessage("error.string.login",
						errorMessages[errorMessages.length - 1]));
			}
			else {
				errors.add("loginError", new ActionMessage("error.string." + lpx.getErrorCode()));
			}

			loginForm.setPassword(null);
			saveErrors(request, errors);

			FormUtil formUtil = new FormUtil();
			formUtil.setForm(actionMapping, request, actionForm);

			return (actionMapping.findForward("reenter"));
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
		catch (Exception excpt) {
			//any other login or session exception will come here
			loginForm.setPassword(null);

			errors = new ActionErrors();
			errors.add("loginError", new ActionMessage("error.string.sessionupdation"));
			saveErrors(request, errors);

			FormUtil formUtil = new FormUtil();
			formUtil.setForm(actionMapping, request, actionForm);

			return (actionMapping.findForward("reenter"));
		}
		finally {
			credentials = null;
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

		// Exclude Super Users from Changing Passwords due to expiry according
		// to policy
		String excludeSuperUsers = PropertyManager.getValue(UserConstant.SUPER_USERS);
		if ((excludeSuperUsers == null)
				|| ((excludeSuperUsers != null) && !(excludeSuperUsers.indexOf(credentials.getLoginId()) >= 0))) {
			try {
				IPolicy policy = getAuthenticator().retrievePolicy(null, credentials);

				if (maxAgeChange) {
					DefaultLogger.debug(this, "Max Aged -- " + maxAgeChange);
					request.setAttribute("expiry", "maxAgeChange");
					session.setAttribute(GlobalSessionConstant.CHANGE_PASSWORD_IND, "change_pwd");
					return actionMapping.findForward("change_pwd");
				}
				else if (policy.getForceLoginIdChange()) {
					DefaultLogger.debug(this, "getForceLoginIdChange -- " + policy.getForceLoginIdChange());
					return actionMapping.findForward("change_id");
				}
				else if (policy.getForcePasswordChange()) {
					DefaultLogger.debug(this, "getForcePasswordChange -- " + policy.getForcePasswordChange());
					session.setAttribute(GlobalSessionConstant.CHANGE_PASSWORD_IND, "change_pwd");
					return actionMapping.findForward("change_pwd");
				}
				else if (maxAgeWarn) {
					DefaultLogger.debug(this, "Max Warn -- " + maxAgeWarn);
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
				throw new LoginProcessException("fail to retrieve password policy", ex);
			}
		}

		return null;
	}

	public void setLocale(HttpServletRequest request, Locale locale) {
		DefaultLogger.info(this, "Setting locale " + locale.toString() + " into session");

		HttpSession session = request.getSession();
		session.setAttribute(GlobalSessionConstant.LOCALE_PARAM, locale);
		super.setLocale(request, locale);
	}

	public String getRemoteAddress(HttpServletRequest request) {
		DefaultLogger.info(this, "inside method getRemoteAddress():--------");
		String remoteAddr = request.getRemoteAddr();
		String x;
		String header = request.getHeader(HEADER_X_FORWARDED_FOR);
		DefaultLogger.info(this, "header:::"+header);
		x = header;
		if (x != null && !x.contains("null") && !"(null)".equals(x) && !"".equalsIgnoreCase(x) && !"null".equalsIgnoreCase(x)) {
			remoteAddr = x;
			DefaultLogger.info(this, "remoteAddr >> 01 :--------"+remoteAddr);
			int idx = remoteAddr.indexOf(',');
			if (idx > -1) {
				remoteAddr = remoteAddr.substring(0, idx);
			}
			DefaultLogger.info(this, "remoteAddr >> 02 :--------"+remoteAddr);
		}
		return remoteAddr;
	}
}
