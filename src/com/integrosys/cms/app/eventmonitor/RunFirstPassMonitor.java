/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/RunFirstPassMonitor.java,v 1.1 2006/01/24 08:00:05 vishal Exp $
 */

package com.integrosys.cms.app.eventmonitor;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.startup.StartupController;
import com.integrosys.base.techinfra.util.PropertyUtil;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.batch.collateralthreshold.CollateralThresholdMain;

public class RunFirstPassMonitor extends Thread {

	Long[] collateralIDList = null;

	CollateralThresholdMain collateralThresholdMain = null;

	public RunFirstPassMonitor(Long[] collateralIDList, CollateralThresholdMain collateralThresholdMain) {
		this.collateralIDList = collateralIDList;
		this.collateralThresholdMain = collateralThresholdMain;
	}

	public void run() {
		try {

			SBEventMonitorFirstPass controller = getEventMonitorFirstPass();

			controller.startCollateralThresholdJob(collateralIDList);
			// collateralThresholdMain.decrease();

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
	private SBEventMonitorFirstPass getEventMonitorFirstPass() throws Exception {
		SBEventMonitorFirstPass controller = (SBEventMonitorFirstPass) BeanController.getEJB(
				ICMSJNDIConstant.SB_EVENT_MONITOR_FIRSTPASS_JNDI, SBEventMonitorFirstPassHome.class.getName());

		if (null != controller) {
			return controller;
		}
		else {
			throw new Exception("SBEventMonitorController is null!");
		}
	}

	// /**
	// * Main method to use for batch jobs
	// */
	// public static void main(String args[]) {
	// RunFirstPassMonitor rt = new RunFirstPassMonitor();
	//
	// if (args.length == 0 || args.length > 2) {
	// // number of parameters should be 1 or 2
	// System.out.println(
	// "Usage: RunEventMonitor <EventMonitor class name> [2-char ISO country code]"
	// );
	// System.out.println("-- <> denotes mandatory parameter");
	// System.out.println("-- [] denotes optional parameter");
	// } else if (args.length == 1) {
	// rt.run(args[0], null);
	// } else if (args.length == 2) {
	// rt.run(args[0], args[1]);
	// }
	// }
}
