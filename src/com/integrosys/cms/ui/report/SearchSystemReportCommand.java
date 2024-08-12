package com.integrosys.cms.ui.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.batch.reports.OBReport;
import com.integrosys.cms.batch.reports.ReportConstants;
import com.integrosys.cms.ui.common.CountryList;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

/**
 * Description: This command uses the input parameters as search criteria and
 * invokes the ReportSchedulerDAO to retrieve the list of MIS Reports The
 * reports are filtered using the report date and country
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/05/04 09:30:46 $ Tag: $Name: $
 */
public class SearchSystemReportCommand extends ReportCommandAccessor implements ICommand, ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "searchDate", "java.lang.String", REQUEST_SCOPE },
				{ "countryCode", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "OBReportList", "java.util.Collection", REQUEST_SCOPE },
				{ "countryName", "java.lang.String", REQUEST_SCOPE },
				{ "searchDate", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * Invokes the ReportSchedulerDAO and returns a list of MIS report objects
	 * selected based on country and date
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.info(this, "Entering method doExecute");
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		try {
			DefaultLogger.debug(this, "Map input keyset " + map.keySet());
			// process inputs
			String selectedCountry = (String) map.get("countryCode");
			String enteredDate = (String) map.get("searchDate");
			DefaultLogger.debug(this, "**********selectedCountry= " + selectedCountry + "**********");
			Date searchDate = DateUtil.convertDate(enteredDate);
			DefaultLogger.debug(this, "**********searchDate= " + searchDate + "**********");

			CountryList countries = CountryList.getInstance();
			String countryName = countries.getCountryName(selectedCountry);

			String[] reportCategoryIds = this.getReportCategoryIds();

			DefaultLogger.debug(this, "@@@@@@@" + selectedCountry + " " + searchDate + " "
					+ ReportConstants.SYSTEM_CATEGORY);

			OBReport[] reports = getReportRequestManager().getMISReportsByCountryAndDate(selectedCountry, null,
					searchDate, reportCategoryIds,null);

			resultMap.put("countryName", countryName);
			resultMap.put("searchDate", enteredDate);
			resultMap.put("OBReportList", reports);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error occurring in retrieving MIS Reports" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	/**
	 * to build up Report CategoryId
	 * @return String
	 */

	private String[] getReportCategoryIds() {
		List list = new ArrayList();
		// list.add(ReportConstants.MIS_CATEGORY);

		list.add(ReportConstants.SYSTEM_CATEGORY);
		return (String[]) list.toArray(new String[0]);

	}

}
