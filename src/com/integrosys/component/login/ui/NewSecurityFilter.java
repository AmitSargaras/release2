package com.integrosys.component.login.ui;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.integrosys.component.login.app.InvalidSessionException;
import com.integrosys.component.login.app.SessionValidator;

/**
 * <dl>
 * <dt><b> Purpose:</b>
 * <dd>Security filter checks for the session validity when accessing protected
 * resources like JSPs and actions/servlet. Besides that it'll also try to
 * restrict non-friendly html entities which can be used for malicious purposes
 * passed in URL.
 * <p>
 * <dt><b>Version Control:</b>
 * <dd>$Revision: 1.2 $<br>
 * $Date: 2003-08-11 11:57:39 $<br>
 * $Author: goutam $<br>
 * </dl>
 */

public class NewSecurityFilter implements Filter, IPageReferences {

	private FilterConfig config;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		// Object user = getUser(httpRequest);

		String contextPath = httpRequest.getContextPath();
		String requestUri = httpRequest.getRequestURI();
		String queryString = httpRequest.getQueryString();

		// DefaultLogger.debug(this, "SecurityFilter -- " + requestUri);

		if ((queryString != null) && (!requestUri.endsWith("/cross_site_script.jsp"))) {
			// to prevent cross-site scripting at the url level
			// form params shld be checked in the respective modules of the
			// application
			// DefaultLogger.debug(this, "SecurityFilter queryString    -- " +
			// queryString);
			String filteredString = filter(queryString);
			// DefaultLogger.debug(this, "SecurityFilter filteredString -- " +
			// filteredString);
			if (!queryString.equals(filteredString)) {
				RequestDispatcher rd = request.getRequestDispatcher(CROSS_SITE_PAGE);
				rd.forward(request, response);
				return;
			}
		}

		if (requestUri.endsWith("/slogin.jsp") || requestUri.endsWith("/login.jsp") || requestUri.endsWith("/main.jsp") || requestUri.endsWith("/cms")
				|| requestUri.endsWith("/login_error.jsp") || requestUri.endsWith("/invalid_session.jsp")
				|| requestUri.endsWith("/cross_site_script.jsp") || requestUri.endsWith("/session_timeout.jsp")
				|| requestUri.endsWith("/system_error.jsp") || requestUri.endsWith("/Logout.do")
				|| requestUri.endsWith("/Login.do")) {
			// DefaultLogger.debug(this, "From Login Page");
			chain.doFilter(request, response);
		}
		else {
			// DefaultLogger.debug(this, "From Non-Login Page");
			if (httpRequest.getAttribute("isNewRequest") != null) {
				// DefaultLogger.debug(this,
				// "::::isNewRequest flag from request:::" + (String)
				// request.getAttribute("isNewRequest"));
			}
			else if ((session == null) || session.isNew()) {
				// DefaultLogger.debug(this,
				// "::::No new request flag recieved:::");
				if (session != null) {
					session.invalidate();
				}
				RequestDispatcher rd = request.getRequestDispatcher(SESSION_TIMEOUT_PAGE);
				rd.forward(request, response);
				return;
			}
			try {
				// DefaultLogger.debug(this, "checking session");
				SessionValidator sesValidator = new SessionValidator();
				SessionData sesData = new SessionData();
				if (sesData.getUserID(httpRequest) == 0) {
					RequestDispatcher rd = request.getRequestDispatcher(SESSION_TIMEOUT_PAGE);
					rd.forward(request, response);
					return;
				}
				sesValidator.isValidSession(new Long(sesData.getUserID(httpRequest)), session.getId());
				// DefaultLogger.debug(this, "identified as valid session");
				chain.doFilter(request, response);
			}
			catch (InvalidSessionException ise) {
				// DefaultLogger.debug(this,
				// "::::Multi Terminal Login Identified::::");
				try {
					// if by chance the session didn't expire, invalidate it
					// explicitly
					// try to remove the userid from user_session table
					SessionData sesData = new SessionData();
					SessionValidator sValidator = new SessionValidator();
					sValidator.invalidateSession(new Long(sesData.getUserID(httpRequest)));
					session.invalidate();
				}
				catch (Throwable thw) {
					// consume all the exceptions if due to invalidation
				}
				RequestDispatcher rd = request.getRequestDispatcher(SESSION_TIMEOUT_PAGE);
				rd.forward(request, response);
				return;
			}
			catch (Throwable t) {
				t.printStackTrace();
				RequestDispatcher rd = request.getRequestDispatcher(GENERAL_ERROR_PAGE);
				rd.forward(request, response);
				return;
			}
		}// else
	}

	/**
	 * Filter the specified message string for characters that are sensitive in
	 * HTML. This avoids potential attacks caused by including JavaScript codes
	 * in the request URL that is often reported in error messages.
	 * 
	 * @param message The message string to be filtered
	 */
	private String filter(String message) {

		if (message == null) {
			return (null);
		}

		char content[] = new char[message.length()];
		message.getChars(0, message.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);
		for (int i = 0; i < content.length; i++) {
			switch (content[i]) {
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			/*
			 * case '&': result.append("&amp;"); // huh can't filter this break;
			 */
			case '"':
				result.append("&quot;");
				break;
			default:
				result.append(content[i]);
			}
		}
		return (result.toString());

	}

	public void init(FilterConfig config) {
		this.config = config;
	}

	public FilterConfig getFilterConfig() {
		return config;
	}

	public void setFilterConfig(FilterConfig config) {
		init(config);
	}

	public void destroy() {

	}
}
