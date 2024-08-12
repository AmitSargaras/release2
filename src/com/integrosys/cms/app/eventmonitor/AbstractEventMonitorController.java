/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.eventmonitor;

import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * This abstract class will contains a business related logic that is
 * independent of any technology implementation such as EJB
 * 
 * @author $Author: vishal $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2006/01/24 07:41:16 $ Tag: $Name: $
 */
public abstract class AbstractEventMonitorController implements IEventMonitorController {

	/**
	 * SessionContext object
	 */
	protected SessionContext _context = null;

	/**
	 * Starts a monitor job
	 * @param jobClassName - the class name to be instantiated and invoked
	 * @param countryCode the country code for
	 */
	public void startMonitorJob(String jobClassName, String countryCode) {

		try {
			DefaultLogger.info(this, "Running for country " + countryCode);

			IMonitor monitor = (IMonitor) (Class.forName(jobClassName)).newInstance();
			monitor.start(countryCode, _context);

		}
		catch (InstantiationException e) {
			DefaultLogger.error(this, "Unable to instantiate " + jobClassName, e);
		}
		catch (IllegalAccessException e) {
			DefaultLogger.error(this, "Unable to instantiate " + jobClassName, e);
		}
		catch (ClassNotFoundException e) {
			DefaultLogger.error(this, "Unable to find class " + jobClassName, e);
		}
		catch (EventMonitorException e) {
			DefaultLogger.error(this, "Event monitor " + jobClassName + " encountered errors", e);
		}
		catch (Exception ee) {
			DefaultLogger.error(this, "Event monitor " + jobClassName + " encountered general errors", ee);
		}

	}

}
