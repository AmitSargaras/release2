package com.integrosys.cms.ui.checklist.annexure;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.UIValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import java.util.Locale;

/**
 * Description
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2005/10/28 09:18:16 $ Tag: $Name: $
 */

public class AnnexureCheckListFormValidator {
	public static ActionErrors validateInput(AnnexureForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		String event = aForm.getEvent();
		DefaultLogger.debug("RecurrentCheckListFormValidator.validateInput", "Inside Validation");

		if (!("submit".equals(event) || "save".equals(event))) {
			
		} else {
			if ("update_recurrent_item".equals(event)) {
				// Deferred Date
				if (!(errorCode = Validator.checkDate(aForm.getDeferredDate(), false, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("deferredDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
							256 + ""));
				}
				else {
					if (!(errorCode = Validator.checkDate(aForm.getDueDate(), false, locale)).equals(Validator.ERROR_NONE)) {
						errors.add("deferredDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
					}
					else {
						errors = UIValidator.compareIsLaterDate(errors, aForm.getDueDate(), aForm.getDeferredDate(),
								"deferredDate", "error.date.compareDate.cannotBeEarlier", "Deferred Date",
								"Document Due Date", locale, locale);
					}
				}
			}
	
			// Date Received
			if (!(errorCode = Validator.checkDate(aForm.getDateReceived(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("dateReceived", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
						256 + ""));
			}
	
	
			/*
			 * Not required. It should check for the total number of characters
			 * (done by javascript already) instead of number of lines. // Remarks
			 * if (!(errorCode =
			 * RemarksValidatorUtil.checkRemarks(aForm.getRecurrentSubItemRemarks(),
			 * false)).equals(Validator.ERROR_NONE)) {
			 * errors.add("recurrentSubItemRemarks",
			 * RemarksValidatorUtil.getErrorMessage(errorCode)); }
			 */
	
			if ("update_covenant_item".equals(event)) {
	
				// Deferred Date -- Old Codes
				/*
				 * if (!(errorCode = Validator.checkDate(aForm.getDeferredDate(),
				 * false, locale)).equals(Validator.ERROR_NONE)) {
				 * errors.add("deferredDate", new
				 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
				 * "0", 256 + "")); } else if (aForm.getDeferredDate() != null &&
				 * aForm.getDeferredDate().length() > 0) { Date dueDate = null; if
				 * (aForm.getDueDate() != null && aForm.getDueDate().length() > 0) {
				 * dueDate = DateUtil.convertDate(locale, aForm.getDueDate()); }
				 * Date deferDate = DateUtil.convertDate(locale,
				 * aForm.getDeferredDate()); if (dueDate != null && deferDate !=
				 * null && deferDate.before(dueDate)) { errors.add("deferredDate",
				 * new ActionMessage("error.date.compareDate", "Deferred Date",
				 * "Due Date")); } }
				 */
	
				if ((aForm.getDeferredDate() != null) && errorCode.equals(Validator.ERROR_NONE)) { // errorCode
																									// refers
																									// to
																									// the
																									// check
																									// on
																									// Deal
																									// Maturity
																									// Date
					if (!(errorCode = Validator.checkDate(aForm.getDueDate(), false, locale)).equals(Validator.ERROR_NONE)) {
						errors.add("deferredDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
					}
					else {
						errors = UIValidator.compareIsLaterDate(errors, aForm.getDueDate(), aForm.getDeferredDate(),
								"deferredDate", "error.date.compareDate.cannotBeEarlier", "Deferred Date", "Due Date",
								locale, locale);
					}
				}
	
	
			}
		}
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}

	/**
	 * Compare if the 2nd date is later than the 1st date.
	 * 
	 * @param errors ActionErrors
	 * @param dateStr1 1st Date String
	 * @param dateStr2 2nd Date String (to be validated to be later than 1st
	 *        date)
	 * @param fieldNameKey Key of the field that is requesting for this
	 *        validation
	 * @param param1 First parameter for the error message
	 * @param param2 Second parameter for the error message
	 * @param locale1 Locale of 1st Date
	 * @param locale2 Locale of 2nd Date
	 * @return ActionErrors, with error added to it if 2nd date is not later
	 *         than the 1st date
	 */
	/*
	 * private static ActionErrors compareIsLaterDate(ActionErrors errors,
	 * String dateStr1, String dateStr2, String fieldNameKey, String errorKey,
	 * String param1, String param2, Locale locale1, Locale locale2) {
	 * if(dateStr1 != null && dateStr1.length() > 0 && dateStr2 != null &&
	 * dateStr2.length() > 0) { Date dateObj1 = DateUtil.convertDate(locale1,
	 * dateStr1); Date dateObj2 = DateUtil.convertDate(locale2, dateStr2);
	 * 
	 * if(!(dateObj2.after(dateObj1))) { //do not use
	 * "if(dateObj2.before(dateObj1))" 'cos if its the same date, it will return
	 * false. It checks for strictly before. errors.add(fieldNameKey, new
	 * ActionMessage(errorKey, param1, param2)); } } return errors; }
	 */

}
