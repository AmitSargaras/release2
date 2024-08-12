package com.integrosys.cms.ui.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.batch.reports.OBReport;
import com.integrosys.cms.batch.reports.ReportConstants;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

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
 * @version $Revision: 1.8 $
 * @since $Date: 2006/04/27 10:40:00 $ Tag: $Name: $
 */
public class SearchMISReportCommand extends ReportCommandAccessor implements ICommand, ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
                { "searchDate", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "countryCode", "java.lang.String", REQUEST_SCOPE }, 
				{ "centreCode", "java.lang.String", REQUEST_SCOPE },
          });
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
				{ "countryCode", "java.lang.String", REQUEST_SCOPE },
				{ "searchDate", "java.lang.String", REQUEST_SCOPE },
				{ "centreCode", "java.lang.String", REQUEST_SCOPE },
        });
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
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			String selectedCountry = (String) map.get("countryCode");
			String enteredDate = (String) map.get("searchDate");
			String centreCode = (String) map.get("centreCode");
            Date searchDate = DateUtil.convertDate(locale, enteredDate);


            DefaultLogger.debug(this, "**********selectedCountry= " + selectedCountry + "**********");
			DefaultLogger.debug(this, "**********centreCode= " + centreCode + "**********");			
			DefaultLogger.debug(this, "test date:" + DateUtil.parseDate("dd/MM/yyyy", enteredDate));
			DefaultLogger.debug(this, "**********searchDate= " + searchDate.getTime() + "**********");

			CountryList countries = CountryList.getInstance();
			String countryName = countries.getCountryName(selectedCountry);

			DefaultLogger.debug(this, "country:" + selectedCountry + " date : " + searchDate + " cat:"
					                  + ReportConstants.MIS_CATEGORY + "locale" + locale);

			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);

			String[] reportCategoryIds = this.getReportCategoryIds();

			OBReport[] reports = getReportRequestManager().getMISReportsByCountryAndDate(selectedCountry,
					team.getOrganisationCodes(), searchDate, reportCategoryIds, centreCode);

			resultMap.put("centreCode", centreCode);
			resultMap.put("countryName", countryName);
			resultMap.put("countryCode", selectedCountry);
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
	 * Helper method is used to get List of Report CategoryId for the Business
	 * report
	 * @return String Array
	 */

	private String[] getReportCategoryIds() {
		List list = new ArrayList();
		list.add(ReportConstants.MIS_CATEGORY);
		list.add(ReportConstants.PREDEAL_CATEGORY);
		list.add(ReportConstants.BRIDGING_LOAN_CATEGORY);
		list.add(ReportConstants.CONTRACT_FINANCING_CATEGORY);
		return (String[]) list.toArray(new String[0]);

	}

}
