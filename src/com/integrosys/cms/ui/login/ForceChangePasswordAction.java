package com.integrosys.cms.ui.login;

import com.integrosys.base.businfra.authentication.AuthenticationManager;
import com.integrosys.base.businfra.login.AuthenticationException;
import com.integrosys.base.businfra.login.IPersistentBroker;
import com.integrosys.base.businfra.login.IPolicy;
import com.integrosys.base.businfra.login.OBCredentials;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.component.login.app.*;
import com.integrosys.component.login.ui.*;
import com.integrosys.component.login.ui.LoginValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * Web controller to interface with force password change module or event.
 *
 * @author Chong Jun Yong
 * @since 12 Nov 2008
 */
public class ForceChangePasswordAction extends Action implements IPageReferences {

    private static final String CHG_LOGINPWD = "command";

    private static final Logger logger = LoggerFactory.getLogger(ForceChangePasswordAction.class);

    private AuthenticationManager authenticationManager;

    private SBPasswordManager passwordManager;

    private IPersistentBroker persistentBroker;

    public void setPersistentBroker(IPersistentBroker persistentBroker) {
        this.persistentBroker = persistentBroker;
    }

    public IPersistentBroker getPersistentBroker() {
        return persistentBroker;
    }

    public void setPasswordManager(SBPasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }

    public SBPasswordManager getPasswordManager() {
        return passwordManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                 HttpServletResponse response) throws IOException, ServletException {

        boolean isLoginSingleSignOnEnabled = PropertyManager
                .getBoolean("integrosys.login.single.signon.enabled", false);

        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Pragma", "no-cache");
        response.addIntHeader("Expires", -1);

        ActionForward fwd;
        HttpSession session = request.getSession(false);

        String command = request.getParameter(CHG_LOGINPWD);
        String expiry = null;
        request.setAttribute("isNewRequest", "1");
        if (request.getAttribute("expiry") != null && command == null) {
            expiry = (String) request.getAttribute("expiry");
            request.setAttribute("expiry", expiry);
            return actionMapping.findForward("reenter");
        }

        String pinType = LoginConstant.SME_PIN_TYPE1;

        if (!isLoginSingleSignOnEnabled) {
            try {
                fwd = verifyBeforeChange(actionMapping, request, pinType);
            }
            catch (AuthenticationException ex) {
                request.getSession().setAttribute(Globals.EXCEPTION_KEY, ex);
                return new ActionForward(SYSTEM_ERROR_PAGE, false);
            }

            if (fwd != null) {
                return fwd;
            }
        }

        if (LoginConstant.SME_PIN_TYPE1.equals(pinType) && command == null) {
            return actionMapping.findForward("reenter");// first time entry
        }

        ChangePasswordForm changeForm = (ChangePasswordForm) actionForm;
        String newPassword = changeForm.getNewPassword();
        String confirmPassword = changeForm.getConfirmNewPassword();
        String oldPassword = changeForm.getOldPassword();

        String ofaUserLoginID = (String) session.getAttribute(GlobalSessionConstant.USER_LOGIN_ID);
        String ofaRoleID = (String) session.getAttribute(GlobalSessionConstant.AUTHENTICATION_ROLE);
        String realm = (String) session.getAttribute(GlobalSessionConstant.AUTHENTICATION_REALM);

        ActionErrors errors = new ActionErrors();

        if (!isLoginSingleSignOnEnabled) {
            LoginValidator validator = new LoginValidator(realm, ofaRoleID);
            errors = validator.validatePwdChange(ofaUserLoginID, newPassword, confirmPassword, oldPassword);

            if (LoginConstant.SME_PIN_TYPE1.equals(pinType) && !errors.isEmpty()) {
                saveErrors(request, errors);
                FormUtil formUtil = new FormUtil();
                formUtil.setForm(actionMapping, request, actionForm);
                return (actionMapping.findForward("reenter"));
            }
        }

        OBCredentials credentials = new OBCredentials(ofaUserLoginID, oldPassword, pinType, realm);
        credentials.setRole(ofaRoleID);

        try {
            if (!isLoginSingleSignOnEnabled) {
                fwd = verifyBeforeChange(actionMapping, request, pinType);
                if (fwd != null) {
                    return fwd;
                }
            }

            getAuthenticationManager().changePassword(credentials, newPassword);

            FormUtil formUtil = new FormUtil();
            formUtil.clearForm(actionMapping, request);

            request.getSession().removeAttribute(GlobalSessionConstant.CHANGE_PASSWORD_IND);

            return actionMapping.findForward("proceed");
        }
        catch (AuthenticationException atex) {
            errors = new ActionErrors();
            String authErr;

            //Andy Wong, 25 Nov 2008: display AS400 error message on UI
            if (isLoginSingleSignOnEnabled) {
                String [] errorMessages = StringUtils.split(atex.getMessage().toString(), ':');
                errors.add("oldPassword", new ActionMessage("error.string.login", errorMessages[errorMessages.length-1]));
            } else {
                if (atex.getErrorCode() != null) {
                    authErr = atex.getErrorCode();
                } else {
                    authErr = LoginErrorCodes.GENERAL_LOGIN_ERROR;
                }
                errors.add("oldPassword", new ActionMessage("error.string." + authErr));
            }
            saveErrors(request, errors);
            return actionMapping.findForward("reenter");
        }
    }

    // 1. in case user access the url directly after changing for first time
    // 2. independent session management
    private ActionForward verifyBeforeChange(ActionMapping actionMapping, HttpServletRequest request, String pinType)
            throws AuthenticationException {
        HttpSession session = request.getSession(false);

        String loginId = (String) session.getAttribute(GlobalSessionConstant.USER_LOGIN_ID);
        String role = (String) session.getAttribute(GlobalSessionConstant.AUTHENTICATION_ROLE);

        OBCredentials credentials = new OBCredentials();
        credentials.setLoginId(loginId);
        credentials.setRealm((String) session.getAttribute(GlobalSessionConstant.AUTHENTICATION_REALM));
        credentials.setRole(role);

        IPolicy policy = (IPolicy) getAuthenticationManager().retrievePasswordPolicy(credentials);

        if (!policy.getForcePasswordChange() && !pwdExpWarnForSMEuser(loginId, role)) {
            return new ActionForward(SYSTEM_ERROR_PAGE, false);
        }

        return null;
    }

    public boolean pwdExpWarnForSMEuser(String loginId, String role) {
        try {
            OBLOSCustomer smeCust = new OBLOSCustomer();
            smeCust.setLoginId(loginId);
            smeCust.setRole(role);

            OBLOSCustomer cust = (OBLOSCustomer) getPersistentBroker().retrieve(smeCust, PersistentEntityType.LOS_USER);
            String passwordPolicyId = null;
            if (cust.getPasswordPolicyId() != null) {
                passwordPolicyId = cust.getPasswordPolicyId();
            } else {
                passwordPolicyId = DBConstant.DEFAULT_PWD_POLICY_ID;
            }

            IPasswordPolicy passwordPolicy = getPasswordManager().getPasswordPolicy(new Long(passwordPolicyId));

            Date todayDate = new Date();
            Date pwdSetDate = cust.getPasswordSetDate();
            int pwdAge = passwordPolicy.getPwdMaxAge();
            int warnDays = passwordPolicy.getNumberWarnDays();

            Date addedDayDate = DateUtils.addDays(pwdSetDate, pwdAge);
            Date warnDate = DateUtils.addDays(pwdSetDate, pwdAge - warnDays);

            if (todayDate.compareTo(addedDayDate) >= 0) {
                return true;
            } else if (todayDate.compareTo(warnDate) >= 0) {
                return true;
            }

            return false;
        }
        catch (Throwable t) {
            logger.warn("failed to validate password expiry warning for login id [" + loginId + "], return 'false'", t);
            return false;
        }
    }
}
