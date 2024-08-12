package com.integrosys.cms.ui.report;

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
 * $Header:
 */

/**
 * Description: Validator class for MIS Reports Checks that required values are
 * entered by the user
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/09 14:39:24 $ Tag: $Name: $
 */
public class MISReportSearchValidator {

	public static ActionErrors validateInput(CommonForm aForm, Locale locale) {

		DefaultLogger.info(MISReportSearchValidator.class.getName(), "Entering method validateInput");

		MISReportSearchForm form = (MISReportSearchForm) aForm;

		// The event name.
		String event = form.getEvent();
		// The resultant ActionErrors, if any.
		ActionErrors errors = new ActionErrors();

		if (event.equals(MISReportSearchAction.EVENT_SEARCH_REPORT)) {
			// a country must be selected
			String countryCode = form.getCountryCode();
            if (isMISReportByCentreCode()){
                validateString(form.getCentreCode(), "centreCode", errors, "error.string.centercode.mandatory");
            } else{
			    validateString(countryCode, "countryCode", errors, "error.string.country.mandatory");
            }

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
		DefaultLogger.info(MISReportSearchValidator.class.getName(), "Entering method validateDate");

		if (validateString(dateEntered, labelKey, errors, "error.string.date.mandatory")) {

			DefaultLogger.info(MISReportSearchValidator.class.getName(), "Checking date validity and range");

			Date convertedDate = DateUtil.convertDate(dateEntered);

			if (convertedDate == null) {
				errors.add("searchDate", new ActionMessage("error.date.format"));
				return;
			}

			// searchDate cannot be after current date, since no reports
			// are available
			// futher check that search range cannot be six months before
			// since reports have been archived

			// get previous day
			// allow reports to be viewed on the actual day itself
			Calendar rightNow = Calendar.getInstance();
			// rightNow.add(Calendar.DATE, -1);
			// Date prevDay = rightNow.getTime();
			Date today = rightNow.getTime();

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
		DefaultLogger.info(MISReportSearchValidator.class.getName(), "Entering method validateString");
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
		DefaultLogger.info(ConcentrationReportSearchValidator.class.getName(),
				"Entering method getSearchReportDuration");
		int days = PropertyManager.getInt(ReportConstants.REPORT_PAST_DURATION);
		DefaultLogger.debug(ConcentrationReportSearchValidator.class.getName(), "number of days" + days);
		return days;
	}


     private static boolean isMISReportByCentreCode() {
        return ReportConstants.isMISReportByCentreCode();
    }

}
