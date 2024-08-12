/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/RunBatchMonitor.java,v 1.1 2005/11/20 09:21:44 dli Exp $
 */

package com.integrosys.cms.app.eventmonitor;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.startup.StartupController;
import com.integrosys.base.techinfra.util.PropertyUtil;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

public class RunBatchMonitor {

	private void run(String className, String countryCode) {
		try {
			DefaultLogger.info(this, "start " + className + ":Country:" + countryCode);
			setUp();
			SBEventMonitorController controller = getEventMonitorController();

			controller.startMonitorJob(className, countryCode);
			DefaultLogger.info(this, "end " + className);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Job failed");
			DefaultLogger.error(this, e);

		}
	}

	private void setUp() throws Exception {
		DefaultLogger.debug(this, "Initialize startup");

		String property = "/startup.properties";
		StartupController.init(PropertyUtil.getInstance(property));
		PropertyManager.getInstance().startup(null);

	}

	/**
	 * Get the SB for EventMonitor Controller
	 * 
	 * @return SBEventMonitorController
	 * @throws Exception on errors
	 */
	private SBEventMonitorController getEventMonitorController() throws Exception {
		SBEventMonitorController controller = (SBEventMonitorController) BeanController.getEJB(
				ICMSJNDIConstant.SB_BATCH_MONITOR_CONTROLLER_JNDI, SBEventMonitorControllerHome.class.getName());

		if (null != controller) {
			return controller;
		}
		else {
			throw new Exception("SBEventMonitorController is null!");
		}
	}

	/**
	 * Main method to use for batch jobs
	 */
	public static void main(String args[]) {
		RunBatchMonitor rt = new RunBatchMonitor();

		if ((args.length == 0) || (args.length > 2)) {
			// number of parameters should be 1 or 2
//			System.out.println("Usage: RunEventMonitor <EventMonitor class name> [2-char ISO country code]");
//			System.out.println("-- <> denotes mandatory parameter");
//			System.out.println("-- [] denotes optional parameter");
		}
		else if (args.length == 1) {
			rt.run(args[0], null);
		}
		else if (args.length == 2) {
			rt.run(args[0], args[1]);
		}
	}
}
