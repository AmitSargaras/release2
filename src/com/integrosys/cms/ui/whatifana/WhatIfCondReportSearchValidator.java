package com.integrosys.cms.ui.whatifana;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.batch.reports.ReportConstants;

/*
 * Copyright Integro Technologies Pte Ltd
 */

/**
 * Description: Validator class for What-If-Conditions Reports Checks that required
 * values are entered by the user
 * 
 * @author siew kheat
 */
public class WhatIfCondReportSearchValidator {

	public static ActionErrors validateInput(CommonForm aForm, Locale locale) {

		DefaultLogger.info(WhatIfCondReportSearchValidator.class.getName(), "Entering method validateInput");

		WhatIfCondReportSearchForm form = (WhatIfCondReportSearchForm) aForm;

		// The event name.
		String event = form.getEvent();
		// The resultant ActionErrors, if any.
		ActionErrors errors = new ActionErrors();

		if (event.equals(WhatIfCondReportSearchAction.EVENT_SEARCH_REPORT)) {
			// search date for report must be entered
			String reportDate = form.getSearchDate();
			validateDate(reportDate, "searchDate", errors);
		}
		return errors;
	}

	/**
	 * Checks that entered date is not null or empty Further checks that date
	 * entered is not past current date nor before six months period Reports
	 * past current date have not been generated Reports that are older than six
	 * months are not retrievable online
	 * 
	 * @param dateEntered
	 * @param labelKey
	 * @param errors
	 */
	private static void validateDate(String dateEntered, String labelKey, ActionErrors errors) {
		DefaultLogger.info(WhatIfCondReportSearchValidator.class.getName(), "Entering method validateDate");

		if (validateString(dateEntered, labelKey, errors, "error.string.date.mandatory")) {

			DefaultLogger.info(WhatIfCondReportSearchValidator.class.getName(), "Checking date validity and range");

			Date convertedDate = DateUtil.convertDate(dateEntered);

			if (convertedDate == null) {
				errors.add("searchDate", new ActionMessage("error.date.format"));
				return;
			}

			// searchDate cannot be after current date, since no reports
			// are available
			// futher check that search range cannot be before
			// valid search duration
			// since reports have been archived

			// get previous day
			Calendar rightNow = Calendar.getInstance();
			Date today = rightNow.getTime();

			// rightNow.add(Calendar.DATE, -1);
			// Date prevDay = rightNow.getTime();

			// get earliest search date bound
			rightNow.add(Calendar.DATE, -getSearchReportDuration());
			Date markerDate = rightNow.getTime();

			// get the earliest date for searching
			Calendar earliestSearchCal = Calendar.getInstance();
			earliestSearchCal.setTime(markerDate);
			earliestSearchCal.add(Calendar.DATE, 1);
			Date earliestSearchDate = earliestSearchCal.getTime();

			if (convertedDate.after(today) || convertedDate.before(markerDate)) {
				errors.add("searchDate", new ActionMessage("error.string.reports.validDateRange", DateUtil
						.convertToDisplayDate(earliestSearchDate), DateUtil.convertToDisplayDate(today)));
			}
		}

	}

	/**
	 * Helper method that checks for null or empty strings
	 * @param s
	 * @param labelKey
	 * @param errors
	 * @return
	 */
	private static boolean validateString(String s, String labelKey, ActionErrors errors, String errorKey) {
		DefaultLogger.info(WhatIfCondReportSearchValidator.class.getName(), "Entering method validateString");
		boolean valid = Validator.validateMandatoryField(s);
		if (!valid) {
			errors.add(labelKey, new ActionMessage(errorKey));
			return false;
		}
		return true;
	}

	/**
	 * look up the number of days permitted for searching
	 * 
	 * @return
	 */
	private static int getSearchReportDuration() {
		DefaultLogger.info(WhatIfCondReportSearchValidator.class.getName(),
				"Entering method getSearchReportDuration");
		int days = PropertyManager.getInt(ReportConstants.REPORT_PAST_DURATION);
		DefaultLogger.debug(WhatIfCondReportSearchValidator.class.getName(), "number of days" + days);
		return days;
	}
}
