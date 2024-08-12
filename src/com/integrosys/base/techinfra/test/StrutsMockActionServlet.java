package com.integrosys.base.techinfra.test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.struts.config.ModuleConfig;
import org.apache.struts.mock.MockActionServlet;

/**
 * SimpleMockActionServlet will skip the init of reading web.xml
 * 
 * This will only initialize the resources, form, mapping and action. Provide
 * servlet config with initParameter 'config', which should include base
 * struts-config.xml and module struts config xml file.
 * 
 * eg. /WEB-INF/struts/struts-config.xml, /WEB-INF/struts/struts-config-user.xml
 * 
 * Multiple struts config xml need to be separated by comma, ','
 */
public class StrutsMockActionServlet extends MockActionServlet {
	public StrutsMockActionServlet(ServletContext context, ServletConfig config) {
		super(context, config);
	}

	public void init() throws ServletException {
		initInternal();
		config = getServletConfig().getInitParameter("config");
		ModuleConfig moduleConfig = initModuleConfig("", config);
		initModuleFormBeans(moduleConfig);
		initModuleForwards(moduleConfig);
		initModuleActions(moduleConfig);
	}
}