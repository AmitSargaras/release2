/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/common/StartupInit.java,v 1.2 2005/04/02 04:57:44 bxu Exp $
 */
package com.integrosys.cms.batch.common;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.startup.StartupController;
import com.integrosys.base.techinfra.util.PropertyUtil;

/**
 * Description: This class initialises the server.
 * 
 * @author $Author: bxu $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/04/02 04:57:44 $ Tag: $Name: $
 */
public class StartupInit {
	private static boolean inited = false;

	public static void init() {
		if (!inited) {
			// todo: change this!!!!!!!!!
			String property = "/startup.properties";
			StartupController.init(PropertyUtil.getInstance(property));
			PropertyManager.getInstance().startup(null);
			inited = true;
		}
	}
}
