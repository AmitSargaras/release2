package com.integrosys.cms.ui.report;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.batch.reports.OBReport;
import com.integrosys.cms.batch.reports.ReportConstants;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Description: This command generates the list of security and cc documents and
 * returns a OBReport PDF for viewing
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/09/05 07:10:04 $ Tag: $Name: $
 */
public class MISReportGenDeficiencyRptCmd extends AbstractCommand {

	private final String DEFICIENY_REPORT_ID = "128";

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "OBReport", "com.integrosys.cms.batch.reports.OBReport", REQUEST_SCOPE },
				{ "reportType", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * Invokes the ReportSchedulerDAO and returns a list of concentration report
	 * objects selected based on date
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.info(this, "Entering method doExecute");
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String[] allowedCountries = null;

		try {
			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			if (team != null) {
				allowedCountries = team.getCountryCodes();
			}
			ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

			DefaultLogger.info(this, "@@@ llp_id : " + limitProfileOB.getLimitProfileID());

			OBReport report = null;

			final String exportFormat = getReportType();
			/*
			 * if ( limitProfileOB != null ) {
			 * 
			 * IReportRequestManager manager =
			 * ReportRequestManagerFactory.getProxyManager(); report =
			 * manager.generateOnlineReport(DEFICIENY_REPORT_ID,
			 * DateUtil.getDate(), limitProfileOB.getLimitProfileID(),
			 * exportFormat, allowedCountries ); } report.setFileName (
			 * report.getFileName()+"."+exportFormat.toLowerCase());
			 * 
			 * resultMap.put("OBReport", report); resultMap.put("reportType",
			 * exportFormat);
			 */
		}
		catch (Exception e) {
			DefaultLogger.error(this, e);
			DefaultLogger.error(this, "Error occurring in retrieving generating Reports" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private static String getReportType() {

		String format = PropertyManager.getValue(ReportConstants.DEFICIENY_REPORT_TYPE);
		if ((format == null) || (format.trim().length() == 0)) {
			throw new RuntimeException("Error loading report type from property file");
		}
		return format;
	}

}
