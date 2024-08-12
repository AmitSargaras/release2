package com.integrosys.cms.ui.common;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.common.IMultiLoginController;
import com.integrosys.component.login.app.InvalidSessionException;
import com.integrosys.component.login.app.SessionValidator;
import com.integrosys.component.login.app.SessionValidatorException;
import com.integrosys.component.login.ui.SessionData;

/**
 * This class represents a controller that can be used to prevent multi login
 * 
 */
public class CMSMultiLoginController implements IMultiLoginController, ICommonEventConstant {

	private static Logger logger = LoggerFactory.getLogger(CMSMultiLoginController.class);

	/**
	 * Default Constructor
	 */
	public CMSMultiLoginController() {
	}

	/**
	 * Method to validate the session to prevent multi login
	 * 
	 * @param request is the HttpServletRequest object
	 * @return String containing the findForward name. If the validation is
	 *         successful, null will be returned.
	 */
	public String validate(HttpServletRequest request) throws ServletException {
		HttpSession session = request.getSession(false);

		try {
			SessionValidator sesValidator = new SessionValidator();
			SessionData sesData = new SessionData();
			if (sesData.getUserID(request) == 0) {
				logger.warn("Session Timeout because userID is not in request or session.");
				return INVALID_SESSION_EXCEPTION;
			}
			sesValidator.isValidSession(new Long(sesData.getUserID(request)), session.getId());
			logger.info("Identified as valid session.");
			return null;
		}
		catch (InvalidSessionException e) {
			session.invalidate();
			return MULTI_LOGIN_EXCEPTION;
		}
		catch (SessionValidatorException e) {
			return INVALID_SESSION_EXCEPTION;
		}
		catch (Throwable t) {
			logger.error("uncategorized error when validating user session, return 'command_error'", t);
			return COMMAND_EXCEPTION;
		}
	}
}