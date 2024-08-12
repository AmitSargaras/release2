package com.integrosys.cms.ui.whatifana;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.batch.reports.OBReport;
import com.integrosys.cms.batch.reports.ReportConstants;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.report.ReportCommandAccessor;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Description: This command uses the input parameters as search criteria and
 * invokes the ReportSchedulerDAO to retrieve the list of Concentration Reports
 * The reports are filtered by report date
 * 
 * @author Siew Kheat <br>
 */
public class SearchWhatIfCondReportCommand extends ReportCommandAccessor implements ICommand, ICommonEventConstant {
	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "searchDate", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] { { "searchDate", "java.lang.String", REQUEST_SCOPE },
				{ "OBReportList", "java.util.Collection", REQUEST_SCOPE } });
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

		final ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);

		if (team == null) {
			throw new CommandProcessingException("Team information is null!");
		}

		try {
			DefaultLogger.debug(this, "Map input keyset " + map.keySet());
			// process inputs

			String enteredDate = (String) map.get("searchDate");
			DefaultLogger.debug(this, "**********enteredDate= " + enteredDate + "**********");
			Date searchDate = DateUtil.convertDate(enteredDate);
			DefaultLogger.debug(this, "**********searchDate= " + searchDate + "**********");

			OBReport[] reports = getReportRequestManager().getWhatIfCondReportList(searchDate,
					ReportConstants.ANALYSIS_CATEGORY, team.getCountryCodes(), team.getTeamType().getTeamTypeID());

			resultMap.put("searchDate", enteredDate);
			resultMap.put("OBReportList", reports);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error occurring in retrieving Concentration Reports", e);
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
