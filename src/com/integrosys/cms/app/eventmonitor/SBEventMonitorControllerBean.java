/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.eventmonitor;

import java.util.HashMap;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.batch.reports.AdhocReportInvoker;
import com.integrosys.cms.batch.reports.ReportScheduler;

/**
 * Session bean implementation of the services provided by the Event Montitor
 * Controller.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/09/05 06:26:57 $ Tag: $Name: $
 */
public class SBEventMonitorControllerBean extends AbstractEventMonitorController implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext context = null;

	/**
	 * Default constructor.
	 */
	public SBEventMonitorControllerBean() {
	}

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(SessionContext sc) {
		context = sc;
		_context = sc;
	}

	public void startMonitorJob(String[] args) {
		if ((args == null) || (args.length < 1)) {
			DefaultLogger.debug(this, " - Invalid args.");
			return;
		}
		DefaultLogger.debug(this, " - startMonitorJob -");

		try {
			String jobClassName = args[0];

			IMonitor monitor = (IMonitor) (Class.forName(jobClassName)).newInstance();
			switch (args.length) {
			case 1:
				monitor.start(null, _context);
				break;
			case 2:
				monitor.start(args[1], _context);
				break;
			default:
				String[] jobParams = new String[args.length - 1];

				// System.arraycopy(jobParams, 0, args, 1, args.length - 1);

				if ((args != null) && (args.length > 0)) {

					for (int i = 0; i < args.length - 1; i++) {
						jobParams[i] = args[i + 1];
						DefaultLogger.debug(this, "jobParams=" + jobParams[i]);
					}
				}

				((AbstractMonitorAdapter) monitor).start(jobParams, _context);
				break;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, e);
			throw new EJBException(e);
		}
	}

	// public void startReportJob(String scope, String country, String centre,
	// String date, String reportMasterID) {
	public void startReportJob(HashMap paramMap) {
		try {
			DefaultLogger.debug(this, "SBBatchMonitorControllerBean startReportJob - running ");
			ReportScheduler scheduler = new ReportScheduler();
			scheduler.startReportGeneration(paramMap);
		}
		catch (Exception ee) {
			DefaultLogger.error(this, "Event monitor startReportJob encountered general errors", ee);
		}
	}

	public void startAdhocReport() {
		try {
			DefaultLogger.debug(this, "SBBatchMonitorControllerBean startAdhocReport - running ");
			AdhocReportInvoker adhoc = new AdhocReportInvoker();
			adhoc.execute(null);
		}
		catch (Exception ee) {
			DefaultLogger.error(this, "Event monitor startAdhocReport encountered general errors", ee);
		}
	}

	public void startReportSQLSearcher(String[] args) {
		try {
			DefaultLogger.debug(this, "SBBatchMonitorControllerBean startReportSQLSearcher - running ");
			// ReportSQLSearcher searcher = new ReportSQLSearcher(args);
			// searcher.runSearch(context);
		}
		catch (Exception ee) {
			DefaultLogger.error(this, "Event monitor startReportSQLSearcher encountered general errors", ee);
		}
	}

}
