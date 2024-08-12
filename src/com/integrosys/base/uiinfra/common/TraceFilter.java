package com.integrosys.base.uiinfra.common;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.StopWatch;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

public class TraceFilter implements Filter {

	public static final String TRANSACTION_TOKEN_KEY = "TRANSACTION_TOKEN_KEY";

	public static final String TOKEN_KEY = "TOKEN_KEY";

	private FilterConfig filterConfig;

	public TraceFilter() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		
		System.out.println("###############################################################################");
		System.out.println("##### [TraceFilter] CREATED");

	}

	public void destroy() {
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws java.io.IOException, javax.servlet.ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = ((HttpServletRequest) request).getSession();

		String uri = request.getRequestURI();

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		String loginID = "";
		String teamID = "";
		String eventID = "";

		try {
			ICommonUser curUser = (ICommonUser) (session.getAttribute("global." + IGlobalConstant.GLOBAL_LOS_USER));
			ITeam curTeam = (ITeam) (session.getAttribute("global." + IGlobalConstant.USER_TEAM));
			if (curUser != null) {
				loginID = curUser.getLoginID();
			}
			if (curTeam != null) {
				teamID = String.valueOf(curTeam.getAbbreviation());
			}
			if (request.getParameter("event") != null) {
				eventID = request.getParameter("event");
			}
		}
		catch (Exception ex) {
			DefaultLogger.debug("TraceFilter", "ERROR\t" + "USER\t:[\t" + loginID + "\t]\tTEAM\t:[\t" + teamID + "\t]\t" + ex.getMessage());
		}

		DefaultLogger.debug("TraceFilter", "USER\t:[\t" + loginID + "\t]\tTEAM\t:[\t" + teamID + "\t]\tSTART\t--> nextPage[\t"
				+ uri + "\t]\tevent\t[\t" + eventID + "\t] (\t0\t ms)");

		filterChain.doFilter(request, response);
		
		DefaultLogger.debug("TraceFilter", "USER\t:[\t" + loginID + "\t]\tTEAM\t:[\t" + teamID + "\t]\tEND\t--> nextPage[\t" 
				+ uri + "\t]\tevent\t[\t" + eventID + "\t] (\t" + +stopWatch.timeElapsed() + "\t ms)");

	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig cfg) {
		filterConfig = cfg;
	}
}