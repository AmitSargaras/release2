package com.integrosys.cms.batch.reports;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.startup.StartupController;
import com.integrosys.base.techinfra.util.PropertyUtil;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

/**
 * Description: This class initialises the weblogic server
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/01 07:43:05 $ Tag: $Name: $
 */
public class ServerInit {
	public static void init() {
		String property = "/startup.properties";
		StartupController.init(PropertyUtil.getInstance(property));
		PropertyManager.getInstance().startup(null);
	}

}
