package com.integrosys.base.uiinfra.common;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.ibm.websphere.servlet.session.IBMSessionListener;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.component.login.ui.GlobalSessionConstant;

public class SessionListener implements IBMSessionListener, HttpSessionAttributeListener {
	String debugFlag;
	private static long sessionCount = 0;

	public static long getSessionCount() {
		return (sessionCount);
	}

	protected SessionJdbcDao getSessionJdbcDao() {
		return (SessionJdbcDao) BeanHouse.get("sessionJdbcDao");
	}

	public SessionListener() {

		System.out.println("###############################################################################");
		System.out.println("##### [SessionListener] CREATED");
		String debugFlag = PropertyManager.getValue("sessionlistener.debug", "false");
		System.out.println("##### [SessionListener] debug flag set to: " + debugFlag);
		String countUsers = PropertyManager.getValue("sessionlistener.countusers", "false");
		System.out.println("##### [SessionListener] countUsers flag set to: " + countUsers);
		int maxUsers = PropertyManager.getInt("sessionlistener.maxusers", 100);
		System.out.println("##### [SessionListener] maxUsers flag set to: " + maxUsers);
	}

	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		String id = session.getId();

		String loginId = (String) session.getAttribute(GlobalSessionConstant.USER_LOGIN_ID);

		String debugFlag = PropertyManager.getValue("sessionlistener.debug", "false");
		if (debugFlag.equals("true")) {
			DefaultLogger.debug(this, "\n");
			DefaultLogger.debug(this, "##### [SessionListener]--------------- Session created --------------");
			DefaultLogger.debug(this, "		----- Login ID: " + loginId);		
			DefaultLogger.debug(this, "##### Created session_id : " + id);
		}

	}

	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		String id = session.getId();

		String loginId = (String) session.getAttribute(GlobalSessionConstant.USER_LOGIN_ID);

		String debugFlag = PropertyManager.getValue("sessionlistener.debug", "false");
		if (debugFlag.equals("true")) {
			DefaultLogger.debug(this, "\n");
			DefaultLogger.debug(this, "##### [SessionListener]--------------- Session destroy starts --------------");
			DefaultLogger.debug(this, "		----- Login ID: " + loginId);
			DefaultLogger.debug(this, "##### Destroying session_id : " + id);
		}

		if (loginId != null) {
			//Fix for Websphere - force an early remove of OFA global.USER_LOGIN_ID to reduce counter early
			session.removeAttribute(GlobalSessionConstant.USER_LOGIN_ID);

			// clearUserSessionByLoginId(loginId);
			getSessionJdbcDao().clearSessionBySessionId(id);

			if (debugFlag.equals("true")) {
				DefaultLogger.debug(this, "		        ----- user session cleared -----");
			}
		}

		if (debugFlag.equals("true")) {
			DefaultLogger.debug(this, "##### [SessionListener]--------------- Session destroy ends --------------");
		}

	}

	private void clearUserSessionByLoginId(String loginId) {
		getSessionJdbcDao().clearSessionByLoginId(loginId);
	}

	// this is required by IBMSessionListener interface
	public void sessionRemovedFromCache(String sessionId) {

		String debugFlag = PropertyManager.getValue("sessionlistener.debug", "false");
		if (debugFlag.equals("true")) {
			DefaultLogger.debug(this, "\n");
			DefaultLogger.debug(this, "##### [SessionListener]--------------- sessionRemovedFromCache starts ---------------");
			DefaultLogger.debug(this, "##### [SessionListener] Deleting session_id : " + sessionId);
		}

		getSessionJdbcDao().clearSessionBySessionId(sessionId);

		if (debugFlag.equals("true")) {
			DefaultLogger.debug(this, "##### [SessionListener]--------------- sessionRemovedFromCache ends ---------------");
		}

	}

	public void attributeAdded(HttpSessionBindingEvent sbe) {	
	    HttpSession session = sbe.getSession();

		String debugFlag = PropertyManager.getValue("sessionlistener.debug", "false");
		if (debugFlag.equals("true")) {
			System.out.println("##### [SessionListener] Attribute added, session " + session.getId() + ": " + sbe.getName() + "=" + sbe.getValue() + " on [" + new java.util.Date() + "]");
		}

		if (sbe.getName().equals(GlobalSessionConstant.USER_LOGIN_ID)) {
			//an OFA login is completed since global.USER_LOGIN_ID is added to session
			String countUsers = PropertyManager.getValue("sessionlistener.countusers", "false");
			if (countUsers.equals("true")) {
				synchronized (this) {
					sessionCount++;
					//session.getServletContext().setAttribute("Online_Users", new Long(sessionCount));
				}				

				if (sessionCount > PropertyManager.getInt("sessionlistener.maxusers", 100)) {
					//this will fail login for those exceeded quota
					//note that user can open a browser and do nothing, i.e. they will not be blocked if session count did not exceed limit at that point in time
					session.invalidate();
				}
			}
			if (debugFlag.equals("true") && countUsers.equals("true")) {
				System.out.println("##### [SessionListener] attributeAdded -> sessionCount: " + sessionCount);
				System.out.println("\n");
			}
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent sbe) {
	    HttpSession session = sbe.getSession();

		String debugFlag = PropertyManager.getValue("sessionlistener.debug", "false");
		if (debugFlag.equals("true")) {
			System.out.println("##### [SessionListener] Attribute removed, session " + session.getId() + ": " + sbe.getName() + "=" + sbe.getValue() + " on [" + new java.util.Date() + "]");
		}

		if (sbe.getName().equals(GlobalSessionConstant.USER_LOGIN_ID)) {
			//it is an OFA session since global.USER_LOGIN_ID is found
			String countUsers = PropertyManager.getValue("sessionlistener.countusers", "false");
			if (countUsers.equals("true")) {
				synchronized (this) {
					if (sessionCount > 0) sessionCount--;
				}
			}
			if (debugFlag.equals("true") && countUsers.equals("true")) {
				System.out.println("##### [SessionListener] attributeRemoved -> sessionCount: " + sessionCount);
				System.out.println("\n");
			}
		}
	}

	public void attributeReplaced(HttpSessionBindingEvent sbe) {

	}

}
