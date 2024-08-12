/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/RunEventMonitor.java,v 1.11 2006/10/20 06:50:13 hmbao Exp $
 */

package com.integrosys.cms.app.eventmonitor;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.batch.reports.ReportConstants;
import com.integrosys.cms.batch.reports.ReportScheduler;

import java.util.HashMap;
import java.util.StringTokenizer;

public class RunEventMonitor {

	private void runBatchJob(String[] args) {
		try {
			setUp();
			getEventMonitorController().startMonitorJob(args);
		}
		catch (Exception e) {
			DefaultLogger.error(this, e);
		}
	}

	private void runReport(HashMap paramMap) {//String scope, String country, String centre, String reportDate, String reportMasterID) {
		//DefaultLogger.info(this, "<<<<<<<<<<< start runReport : " + args);
		try {
			setUp();
            SBEventMonitorController controller = getEventMonitorController();
			controller.startReportJob(paramMap);

        }
		catch (Exception e) {
			DefaultLogger.error(this, "Run report job failed.... ");
			DefaultLogger.error(this, e);
		}
	}

    private void runAdhocReport() {
		DefaultLogger.info(this, "<<<<<<<<<<< start runAdhocReport ");
		try {
			setUp();
			SBEventMonitorController controller = getEventMonitorController();
			controller.startAdhocReport();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Run report job failed.... ");
			DefaultLogger.error(this, e);
		}
	}

	private void runReportSQLSearcher(String[] args) {
		DefaultLogger.info(this, "<<<<<<<<<<< start runReportSQLSearcher ");
		try {
			setUp();
			SBEventMonitorController controller = getEventMonitorController();
			controller.startReportSQLSearcher(args);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Run report job failed.... ");
			DefaultLogger.error(this, e);
		}

	}

	private void setUp() throws Exception {
		// DefaultLogger.debug(this, "Initialize startup");
		//
		// String property = "/startup.properties";
		// StartupController.init(PropertyUtil.getInstance(property));
		// PropertyManager.getInstance().startup(null);
	}

	/**
	 * Get the SB for EventMonitor Controller
	 * 
	 * @return SBEventMonitorController
	 * @throws Exception on errors
	 */
	private SBEventMonitorController getEventMonitorController() throws Exception {
		SBEventMonitorController controller = (SBEventMonitorController) BeanController.getEJB(
				ICMSJNDIConstant.SB_EVENT_MONITOR_CONTROLLER_JNDI, SBEventMonitorControllerHome.class.getName());

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
	public void Main(String args[]) {
		RunEventMonitor rt = new RunEventMonitor();
		String jobName = "Unknown Batch";
		String ctyCode = "Global";
		if (args.length > 0) {
			jobName = getJobName(args[0]);
			ctyCode = getCtyName(args);
		}
		DefaultLogger.debug(this, "Job Name : <" + jobName + ">");
		DefaultLogger.debug(this, " - Country : <" + ctyCode + ">");
		DefaultLogger.debug(this, " - Start Time : <" + DateUtil.getDate() + ">");
		if ((args.length > 0) && args[0].equals("com.integrosys.cms.batch.reports.ReportScheduler")) {
			//String[] params = getReportParameters(args);
			//rt.runReport(params[0], params[1], params[2], params[3]);
            rt.runReport(getReportParameters(args));
        }
		else if ((args.length > 0) && args[0].equals("com.integrosys.cms.batch.reports.AdhocReportInvoker")) {
			rt.runAdhocReport();
		}
		else if ((args.length > 0) && args[0].equals("com.integrosys.cms.batch.reports.ReportSQLSearcher")) {
			rt.runReportSQLSearcher(parseSearchReportSQLParameters(args));
		}
		else if (args.length > 0) {
			rt.runBatchJob(args);
		}
		DefaultLogger.debug(this, " - End Time : <" + DateUtil.getDate() + ">");
	}


    /* //------- Previous Version: when calling runReport using individual parameter instead of HashMap ---------
    private String[] getReportParameters(String args[]) {
		String scope = null;
		String date = null;
		String param = null;
		String reportMasterID = null;
		if (args.length > 1) {
			if (args[1].equals(ReportConstants.REPORT_CONFIG_BY_ID)) {
				try {
					reportMasterID = args[2];
					if (args.length == 5) {
						date = args[3];
						param = args[4];
					}
					else if (args.length == 4) {
						param = args[3];
					}
				}
				catch (NumberFormatException e) {
					DefaultLogger.info(RunEventMonitor.class.getName(), "Report master ID must be a number");
					System.exit(1);
				}
			}
			else {
				DefaultLogger.debug(RunEventMonitor.class.getName(), "Generating Report by Scope: " + args[1]);

				if ((args.length == 1) || (args.length > 4)) {
					DefaultLogger.debug(RunEventMonitor.class.getName(),
							"Error: No scope specified for report generation, aborting\n\n");
					System.exit(1);
				}

				if (args.length == 2) {
					scope = args[1];
					if (!scope.equalsIgnoreCase(ReportConstants.GLOBAL_SCOPE)) {
						DefaultLogger.debug(RunEventMonitor.class.getName(), "Expecting Global scope");
						System.exit(1);
					}
				}

				if (args.length == 3) {
					scope = args[1];
					param = args[2];
					boolean validScope = false;
					String[] scopeTypes = ReportScheduler.scopeTypes;
					for (int i = 0; i < scopeTypes.length; i++) {
						DefaultLogger.debug("scope is ....", scope);
						if (scope.equalsIgnoreCase(scopeTypes[i])) {
							validScope = true;
							DefaultLogger.debug("validScope is ....", validScope + "");
							break;
						}
					}
					if (!validScope) {
						DefaultLogger.error(RunEventMonitor.class.getName(), "Unidentified scope entered");
						System.exit(1);
					}
				}

				// MIS Report and Country Report
				if (args.length == 4) {
					scope = args[1];
					date = args[2];
					param = args[3];
					if (!ReportScheduler.isValidForMIS(scope) && !ReportScheduler.isValidForCountry(scope)) {
						DefaultLogger.error(RunEventMonitor.class.getName(), "Expected category: "
								+ ReportConstants.MIS_CATEGORY + " OR " + ReportConstants.SYSTEM_CATEGORY + " OR "
								+ ReportConstants.DOC_CATEGORY + " OR " + ReportConstants.COUNTRY_REPORT);
						System.exit(1);
					}
					DefaultLogger.debug(RunEventMonitor.class.getName(),
							"Generating MIS Report and Country Report .... ");
				}
			}
		}
		else {
			DefaultLogger.debug(RunEventMonitor.class.getName(),
					"Error: No report master ID specified for report generation, aborting\n\n");
			System.exit(1);
		}
		return new String[] { scope, param, date, reportMasterID };
	}*/


    private HashMap getReportParameters(String args[]) {
        DefaultLogger.info(this, "<<<<<<<<<<< start runReport : " + args);
        HashMap paramMap = new HashMap();

        //------------- Translate the arugments to HashMap -------------------
        for (int i = 0; i < args.length; i++) {
            StringTokenizer st = new StringTokenizer(args[i], "=");
            if (st.countTokens() < 2) {
                // ignore this argument
            }
            else { // at least 2 arg
                String key = st.nextToken();
                String value = st.nextToken();
                paramMap.put(key, value);
            }
        }

        //------------- Perform validation on inputs -------------------
        String scope = (String)paramMap.get(ReportConstants.KEY_SCOPE);
        if(scope == null) {
            DefaultLogger.debug(RunEventMonitor.class.getName(), "No scope define, aborting \n\n");
			System.exit(1);
        }

        boolean validScope = false;
        String[] scopeTypes = ReportScheduler.getAllValidScopeTypes();
        for (int i = 0; i < scopeTypes.length; i++) {
            DefaultLogger.debug("scope is ....", scope);
            if (scope.equalsIgnoreCase(scopeTypes[i])) {
                validScope = true;
                DefaultLogger.debug("validScope is ....", validScope + "");
                break;
            }
        }
        if (!validScope) {
            DefaultLogger.error(RunEventMonitor.class.getName(), "Unidentified scope entered");
            System.exit(1);
        }

        if(scope.equals(ReportConstants.REPORT_CONFIG_BY_ID)) {
            DefaultLogger.debug(RunEventMonitor.class.getName(), "Generating Report by ID: " + args[1]);
            String masterID = (String)paramMap.get(ReportConstants.KEY_VALUE);
            try {
                Integer.parseInt(masterID);         //just to validate if report master id is a number
            }catch (NumberFormatException e) {
					DefaultLogger.info(RunEventMonitor.class.getName(), "Report master ID must be a number");
					System.exit(1);
		    }

            return paramMap;
        }

        if(scope.equalsIgnoreCase(ReportConstants.GLOBAL_SCOPE)) {
            if(paramMap.size() > 1) {
                DefaultLogger.info(RunEventMonitor.class.getName(), "Global Scope should not have other parameters");
                System.exit(1);
            }

            return paramMap;
        }


        //All other scopes: MIS, SYS, Country, Region, Exchange - should have at least 1 variable
        if(paramMap.get(ReportConstants.KEY_VALUE) == null) {
            DefaultLogger.info(RunEventMonitor.class.getName(), "No value defined for the stated scope: " + scope);
            System.exit(1);
        }

        return paramMap;
    }



    private String[] parseSearchReportSQLParameters(String args[]) {
		if ((args == null) || (args.length < 5)) {
			String errorMsg = "Parameters: ReportSQLSearcher <MIN REPORT MASTER ID> <MAX REPORT MASTER ID> <FILE NAME OF RESULT (1 word)> "
					+ "<1 OR MORE TABLE/FUNCTION (separated by space) etc TO SEARCH FOR> \n"
					+ "e.g. ReportSQLSearcher 1 50 cms_cmdt_deal cms_user"
					+ "current (as of R1.4.1) min report master id=1; max report master id = 150";

			DefaultLogger.error("com.integrosys.cms.app.eventmonitor.RunEventMonitor.parseSearchReportSQLParameters",
					errorMsg);
		}

		int argsLen = args.length - 1;
		String[] newArgs = new String[argsLen];
		for (int i = 1; i < args.length; i++) {
			newArgs[i - 1] = args[i];
		}
		return newArgs;
	}

	private String getJobName(String className) {
		if (className == null) {
			return "Unknown Batch";
		}
		int index = className.lastIndexOf('.');
		return className.substring(index + 1);
	}

	private String getCtyName(String[] args) {
		if ((args != null) && (args.length > 1)) {
			if (args[0].indexOf("Report") != -1) {
				if ("MIS".equals(args[1]) || "SYS".equals(args[1]) || "DOC".equals(args[1])
						|| "Country".equals(args[1])) {
					if (args.length == 3) {
						return args[args.length - 1];
					}
				}
			}
			else {
				return args[1];
			}
		}
		return "Global";
	}
}
