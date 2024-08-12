/*
 * Copyright 2000-2008 Integro Technologies Pte Ltd
 * 
 * http://www.integrosys.com/
 */
package com.integrosys.base.techinfra.test;

import java.io.FileNotFoundException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.struts.action.Action;

import junit.framework.TestCase;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.impl.ModuleConfigImpl;
import org.apache.struts.mock.MockServletConfig;
import org.apache.struts.mock.MockServletContext;
import org.springframework.util.Log4jConfigurer;

/**
 * <p>
 * Same as {@link CustomHibernateTestCase}, this test case required to input
 * struts-config.xml files (can be multiple) to initialize the
 * {@link StrutsMockActionServlet}
 * 
 * <p>
 * Sub class should override getStrutsConfigPath method to provide the struts
 * config file path. The path can be caconical name after /public_html/ folder,
 * eg. /WEB-INF/struts/struts-config.xml
 * 
 * <p>
 * Sub class can also override postProcessActionServlet method to provide more
 * info required for individual TestCase.
 * 
 * @author Chong Jun Yong
 * @since 1.1
 * @see #getStrutsConfigPath
 * @see #postProcessActionServlet
 */
public abstract class CustomStrutsWebTestCase extends TestCase {
	private StrutsMockActionServlet actionServlet;

	private ModuleConfigImpl moduleConfigImpl;

	public void setUp() throws ServletException {
		try {
			Log4jConfigurer.initLogging("classpath:logging.xml");
		}
		catch (FileNotFoundException fnfe) {
			fail("not able to load logging.xml, please check whether this file is in classpath");
		}

		MockServletConfig servletConfig = new MockServletConfig();
		servletConfig.addInitParameter("config", getStrutsConfigPath());

		actionServlet = new StrutsMockActionServlet(new MockServletContext(), servletConfig);
		postProcessActionServlet(actionServlet);
		actionServlet.init();

		this.moduleConfigImpl = (ModuleConfigImpl) getServletContext().getAttribute("org.apache.struts.action.MODULE");
	}

	/**
	 * <p>
	 * This should return sturts-config-{module}.xml path, separated by comma if
	 * multiple struts config xml files required
	 * 
	 * <p>
	 * Example. /WEB-INF/struts/struts-config.xml,
	 * /WEB-INF/sturts/struts-config-user.xml
	 */
	protected abstract String getStrutsConfigPath();

	/**
	 * @return the module config constructed by the struts engine, can be used
	 *         to retrieve the action configured in the struts-config.xml which
	 *         is return in {@link #getStrutsConfigPath()}
	 */
	public ModuleConfigImpl getModuleConfigImpl() {
		return this.moduleConfigImpl;
	}

	/**
	 * @return the action servlet instantiated by this test case, can be used to
	 *         set in the {@link Action#setServlet(ActionServlet)}, then execute
	 *         the action.
	 */
	public ActionServlet getActionServlet() {
		return this.actionServlet;
	}

	public ServletContext getServletContext() {
		if (actionServlet == null) {
			throw new IllegalStateException("'actionServlet' not yet initialized. are you running junit test?");
		}

		return actionServlet.getServletContext();
	}

	/**
	 * post process of actionServlet before calling init() method on it
	 * 
	 * @param actionServlet instance of StrutsMockActionServlet
	 */
	protected void postProcessActionServlet(StrutsMockActionServlet actionServlet) {
	}
}