/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/PrintDiaryItemCommand.java,v 1.4 2006/09/05 07:09:26 hshii Exp $
 */
package com.integrosys.cms.ui.diaryitem;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.diary.bus.DiaryItemSearchCriteria;
import com.integrosys.cms.batch.reports.ReportConstants;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * This command wraps the print request to be sent to RAS for processing
 * $Author: hshii $
 * @version $Revision: 1.4 $
 * @since $Date: 2006/09/05 07:09:26 $ Tag: $Name: $
 */
public class PrintDiaryItemCommand extends DiaryItemsCommand {

	private static final String DIARY_REPORT_ID = "147";

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "DiaryItemSearchCriteria", "com.integrosys.cms.app.diary.bus.DiaryItemSearchCriteria", FORM_SCOPE },
				{ "frompage", "java.lang.String", REQUEST_SCOPE }, { "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "customerIndex", "java.lang.String", REQUEST_SCOPE },
				{ "countryFilter", "java.lang.String", REQUEST_SCOPE },
				{ "startExpDate", "java.lang.String", REQUEST_SCOPE },
				{ "session.frompage", "java.lang.String", SERVICE_SCOPE },
				{ "session.startIndex", "java.lang.String", SERVICE_SCOPE },
				{ "session.customerIndex", "java.lang.String", SERVICE_SCOPE },
				{ "session.countryFilter", "java.lang.String", SERVICE_SCOPE },
				{ "searchLeID", "java.lang.String", REQUEST_SCOPE },
				{ "searchCustomerName", "java.lang.String", REQUEST_SCOPE },
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

		try {

			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			long teamTypeId = team.getTeamType().getTeamTypeID();

			String countryFilter = (String) map.get("countryFilter");
			String sessCountryFilter = (String) map.get("session.countryFilter");

			String searchLeID = (String) map.get("searchLeID");
			String searchCustomerName = (String) map.get("searchCustomerName");

			long lLegalID = ICMSConstant.LONG_INVALID_VALUE;

			try {
				lLegalID = Long.parseLong(searchLeID);
			}
			catch (Exception e) {
				lLegalID = ICMSConstant.LONG_INVALID_VALUE;
			}

			DiaryItemSearchCriteria criteria = (DiaryItemSearchCriteria) map.get("DiaryItemSearchCriteria");

			String[] allowedCountries;

			if (countryFilter != null) {
				allowedCountries = new String[] { countryFilter };
			}
			else if (sessCountryFilter != null) {
				allowedCountries = new String[] { sessCountryFilter };
			}
			else {
				allowedCountries = team.getCountryCodes();
			}

			if ((criteria.getCustomerIndex() != null) && DiaryItemHelper.isNull(searchCustomerName)) {
				searchCustomerName = criteria.getCustomerIndex();
			}

			final String exportFormat = getReportType();
			/*
			 * IReportRequestManager manager =
			 * ReportRequestManagerFactory.getProxyManager(); OBReport report =
			 * manager.generateOnlineReport(DIARY_REPORT_ID,
			 * criteria.getStartExpDate(), criteria.getEndExpDate(), teamTypeId,
			 * allowedCountries, exportFormat, lLegalID, searchCustomerName );
			 * 
			 * report.setFileName (
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

		String format = PropertyManager.getValue(ReportConstants.DIARY_REPORT_TYPE);
		if ((format == null) || (format.trim().length() == 0)) {
			throw new RuntimeException("Error loading report type from property file");
		}

		DefaultLogger.debug("", "ReportFormat: " + format);

		return format;
	}

}
