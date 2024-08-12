package com.integrosys.cms.ui.checklist.recurrent;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import java.util.Date;
import java.util.Locale;

/**
 * Description
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2005/10/28 09:18:16 $ Tag: $Name: $
 */

public class RecurrentCheckListFormValidator {
	public static ActionErrors validateInput(RecurrentCheckListForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		String event = aForm.getEvent();
		int min = 0, max = 0;
		DefaultLogger.debug("RecurrentCheckListFormValidator.validateInput", "Inside Validation");
		// if("add_recurrent".equals(event)) {

		if ("submit".equals(event) || "save".equals(event)) {
			if (aForm.getNoCovenant().equals("1") && aForm.getNoRecurrent().equals("1")) {
				errors.add("noCovenantRecurrent", new ActionMessage("error.no.covenant.recurrent"));
			}
		} else {
			// Document Description
			min = 0;
			max = 250;
			String checkedDescription="";
			checkedDescription=aForm.getRecurrentItemDesc();
			if (!(errorCode = Validator.checkString(checkedDescription, true, min, max)).equals(Validator.ERROR_NONE)) {
				errors.add("recurrentItemDesc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						String.valueOf(min), String.valueOf(max)));
			}
	
			// if one off is false, frequency inputs are mandatory
			if ("false".equals(aForm.getOneOff())) {
				// Frequency
				min = 0;
				max = 999;
				if (!(errorCode = Validator.checkInteger(aForm.getFrequency(), true, min, max))
						.equals(Validator.ERROR_NONE)) {
					errors.add("frequency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), String
							.valueOf(min), String.valueOf(max)));
				}
	
				// Frequency Unit
				min = 0;
				max = 20;
				if (!(errorCode = Validator.checkString(aForm.getFrequencyUnit(), true, min, max))
						.equals(Validator.ERROR_NONE)) {
					errors.add("frequencyUnit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							String.valueOf(min), String.valueOf(max)));
				}
			}
	
			// Document End Date
			// boolean isDocEndDateRequired = false;
			// if (aForm.getGracePeriod() != null &&
			// !aForm.getGracePeriod().equals("")) {
			// isDocEndDateRequired = true;
			// }
			if (!(errorCode = Validator.checkDate(aForm.getDocEndDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("docEndDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}
	
			// Grace Period
			min = 0;
			max = 999;
			boolean isGracePeriodRequired = false;
			if ((aForm.getGracePeriodUnit() != null) && !aForm.getGracePeriodUnit().equals("")) {
				isGracePeriodRequired = true;
			}
			if (!(errorCode = Validator.checkInteger(aForm.getGracePeriod(), isGracePeriodRequired, min, max))
					.equals(Validator.ERROR_NONE)) {
				errors.add("gracePeriod", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), String
						.valueOf(min), String.valueOf(max)));
			}
	
			// Grace Period Unit
			min = 0;
			max = 20;
			boolean isGracePeriodUnitRequired = false;
			if ((aForm.getGracePeriod() != null) && !aForm.getGracePeriod().equals("")) {
				isGracePeriodUnitRequired = true;
			}
			if (!(errorCode = Validator.checkString(aForm.getGracePeriodUnit(), isGracePeriodUnitRequired, min, max))
					.equals(Validator.ERROR_NONE)) {
				errors.add("gracePeriodUnit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						String.valueOf(min), String.valueOf(max)));
			}
	
			// Due Date
			// Commented out because it is a system derived field, thus no
			// user-input validation required
			/*
			 * if (!(errorCode = Validator.checkDate(aForm.getDueDate(), true,
			 * locale)).equals(Validator.ERROR_NONE)) {
			 * if(aForm.getDueDate().length()>0){ errors.add("dueDate", new
			 * ActionMessage("error.date.format")); }else{ errors.add("dueDate", new
			 * ActionMessage("error.date.mandatory")); } }
			 */
			// Commented off to fix bug CMS-123
			/*
			 * if(aForm.getDueDate().length()>0){ Date d = DateUtil.getDate(); int a
			 * = d.compareTo(DateUtil.convertDate(locale,aForm.getDueDate()));
			 * DefaultLogger
			 * .debug("vaidation ***********************************","Eroororr date "
			 * + a); if(a>0){ errors.add("dueDate", new
			 * ActionMessage("error.date.compareDate","Due Date","Current Date")); }
			 * }
			 */
	
			// Last Document Entry Date
			if (!(errorCode = Validator.checkDate(aForm.getLastDocEntryDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("lastDocEntryDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
						256 + ""));
			}
			else {
				if ((aForm.getLastDocEntryDate() != null) && (aForm.getLastDocEntryDate().length() > 0)
						&& (aForm.getDocEndDate() != null) && (aForm.getDocEndDate().length() > 0)) {
					Date endDate = DateUtil.convertDate(locale, aForm.getDocEndDate());
					Date lastDocEntryDate = DateUtil.convertDate(locale, aForm.getLastDocEntryDate());
					if (endDate.compareTo(lastDocEntryDate) > 0) {
						errors.add("docEndDate", new ActionMessage("error.date.compareDate.greater", "Document End Date",
								"Last Document Entry Date"));
					}
				}
			}
			/*
			 * // Remarks min=0; max=250; if (!(errorCode =
			 * Validator.checkString(aForm.getRecurrentItemRemarks(), false, min,
			 * max)).equals(Validator.ERROR_NONE)) {
			 * errors.add("recurrentItemRemarks", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
			 * String.valueOf(min), String.valueOf(max))); }
			 */
	
			/*
			 * Not required. It should check for the total number of characters
			 * (done by javascript already) instead of number of lines. // Remarks
			 * //Updated to new validation (checking for number of lines instead of
			 * just number of characters). JIRA CMS-2483, Defect #71 if (!(errorCode
			 * = RemarksValidatorUtil.checkRemarks(aForm.getRecurrentItemRemarks(),
			 * false)).equals(Validator.ERROR_NONE)) {
			 * errors.add("recurrentItemRemarks",
			 * RemarksValidatorUtil.getErrorMessage(errorCode)); }
			 */
	
			// }
		}
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
