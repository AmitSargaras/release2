package com.integrosys.cms.ui.checklist.recurrent;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Description
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/10/28 09:18:16 $ Tag: $Name: $
 */

public class CovenantFormValidator {
	public static ActionErrors validateInput(RecurrentCheckListForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		// String event = aForm.getEvent();
		int min = 0, max = 0;
		DefaultLogger.debug("CovenantFormValidator.validateInput", "Inside Validation");
		// if("add_recurrent".equals(event)) {

		// Document Description
		min = 0;
		max = 250;
		String checkedDescription="";
		if (aForm.getIsParameterizedDesc().equals("false")) checkedDescription=aForm.getCovenantItemDesc();
		else checkedDescription=aForm.getCovenantSelect();
		if (!(errorCode = Validator.checkString(checkedDescription, true, min, max))
				.equals(Validator.ERROR_NONE)) {
			errors.add("recurrentItemDesc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					String.valueOf(min), String.valueOf(max)));
		}
		// if not one off, then nid to check for freq...
		if (!"true".equals(aForm.getOneOff())
				&& ((aForm.getFrequency() == null) || "".equals(aForm.getFrequency())
						|| (aForm.getFrequencyUnit() == null) || "".equals(aForm.getFrequencyUnit()))) {
			min = 0;
			max = 999;
			if (!(errorCode = Validator.checkInteger(aForm.getFrequency(), true, min, max))
					.equals(Validator.ERROR_NONE)) {
				errors.add("frequency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), String
						.valueOf(min), String.valueOf(max)));
			}
			min = 0;
			max = 20;
			if (!(errorCode = Validator.checkString(aForm.getFrequencyUnit(), true, min, max))
					.equals(Validator.ERROR_NONE)) {
				errors.add("frequencyUnit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						String.valueOf(min), String.valueOf(max)));
				// DefaultLogger.debug("validate..", "freqUnit");
			}
		}

		// Frequency
		min = 0;
		max = 999;
		if (!(errorCode = Validator.checkInteger(aForm.getFrequency(), false, min, max)).equals(Validator.ERROR_NONE)) {
			errors.add("frequency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), String
					.valueOf(min), String.valueOf(max)));
		}

		// Frequency Unit
		min = 0;
		max = 20;
		if (!(errorCode = Validator.checkString(aForm.getFrequencyUnit(), false, min, max))
				.equals(Validator.ERROR_NONE)) {
			errors.add("frequencyUnit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), String
					.valueOf(min), String.valueOf(max)));
		}

		// Document End Date
		// boolean isDocEndDateRequired = false;
		// if (aForm.getGracePeriod() != null &&
		// !aForm.getGracePeriod().equals("")) {
		// isDocEndDateRequired = true;
		// }

		/*  validation for docEndDate move down
		    if (!(errorCode = Validator.checkDate(aForm.getDocEndDate(), true, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("docEndDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
		}*/



		// Grace Period
		min = 0;
		max = 999;
		boolean isGracePeriodRequired = false;
		if ((aForm.getGracePeriodUnit() != null) && !aForm.getGracePeriodUnit().equals("")) {
			isGracePeriodRequired = true;
		}
		if (!(errorCode = Validator.checkInteger(aForm.getGracePeriod(), isGracePeriodRequired, min, max))
				.equals(Validator.ERROR_NONE)) {
//			errors.add("gracePeriod", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), String
//					.valueOf(min), String.valueOf(max)));
            errors.add("gracePeriod", new ActionMessage("error.gracePeriod.incomplete", ""));
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
        //
        // DUE DATE can be anytime after the END DATE if GRACE PERIOD is not specified


//		if (!(errorCode = Validator.checkDate(aForm.getDueDate(), true,
//		locale)).equals(Validator.ERROR_NONE)) {
//		    if(aForm.getDueDate().length()>0){
//                errors.add("dueDate", new ActionMessage("error.date.format"));
//            } else { errors.add("dueDate", new ActionMessage("error.date.mandatory")); }
//        }

//		if (aForm.getDueDate().length()>0){
//            Date d = DateUtil.getDate();
//            int a = d.compareTo(DateUtil.convertDate(locale,aForm.getDueDate()));
//		    DefaultLogger.debug("**DUEDATE VALIDATION**","Eroororr date " + a);
//            if ( a>0 ) {
//                errors.add("dueDate", new ActionMessage("error.date.compareDate","Due Date","Current Date"));
//            }
//		}

        if (!(errorCode = Validator.checkDate(aForm.getDocEndDate(), true, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("docEndDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
		}else if(aForm.getDueDate() != null && aForm.getDueDate().trim().length() > 0){
            Date endDate = DateUtil.convertDate(locale, aForm.getDocEndDate());
            Calendar proposedDueDateCal = new GregorianCalendar();
            proposedDueDateCal.setTime(endDate);
            String gracePeriodUnit = aForm.getGracePeriodUnit();
            int gracePeriod = 0;
            try {
                gracePeriod = Integer.parseInt(aForm.getGracePeriod());
            } catch (NumberFormatException nfe) {
            }
            if (gracePeriod != 0) {
                if ("D".equals(gracePeriodUnit)) {
                    proposedDueDateCal.add(Calendar.DAY_OF_MONTH, gracePeriod);
                } else if ("W".equals(gracePeriodUnit)) {
                    proposedDueDateCal.add(Calendar.DAY_OF_MONTH, 7 * gracePeriod);
                } else if ("M".equals(gracePeriodUnit)) {
                    proposedDueDateCal.add(Calendar.MONTH, gracePeriod);
                } else if ("Y".equals(gracePeriodUnit)) {
                    proposedDueDateCal.add(Calendar.YEAR, gracePeriod);
                }
            }
            if (!(errorCode = Validator.checkDate(aForm.getDueDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			    errors.add("dueDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
		    }else if(aForm.getDueDate() != null && aForm.getDueDate().trim().length() > 0){
                Date dueDate = DateUtil.convertDate(locale, aForm.getDueDate());
                Date proposedDueDate = proposedDueDateCal.getTime();

            // IF Grace Period is not empty, make sure that DUE DATE adhere to DUE DATE = END DATE + GRACE PERIOD
                if ((aForm.getGracePeriod() != null) && !aForm.getGracePeriod().equals("")) {
        //                && !aForm.getGracePeriod().equals("0") && aForm.getGracePeriodUnit() != null
        //                && !aForm.getGracePeriodUnit().equals("")) {

                    if (proposedDueDate.compareTo(dueDate) != 0) {
                        errors.add("dueDate", new ActionMessage("error.date.dueDate.inconsistent",""));
                    }
                } else if (dueDate.compareTo(endDate) < 0) {
                    errors.add("dueDate", new ActionMessage("error.date.compareDate.cannotBeEarlier", "Due Date", "End Date"));
                }
            }
            
            // Last Document Entry Date
            if (!(errorCode = Validator.checkDate(aForm.getLastDocEntryDate(), false, locale)).equals(Validator.ERROR_NONE)) {
                errors.add("lastDocEntryDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
            }
            else if ((aForm.getLastDocEntryDate() != null) && (aForm.getLastDocEntryDate().trim().length() > 0)
                        && (aForm.getDocEndDate() != null) && (aForm.getDocEndDate().trim().length() > 0)) {
                //Date endDate = DateUtil.convertDate(locale, aForm.getDocEndDate());
                // for cms 1723, add 1 month to end date before comparison
                Calendar c = new GregorianCalendar();
                c.setTime(endDate);
                String freqUnit = null;
                int freq = 0;
                if (aForm.getFrequency() != null) {
                    freqUnit = aForm.getFrequencyUnit();
                    try {
                        freq = Integer.parseInt(aForm.getFrequency());
                    }
                    catch (NumberFormatException nfe) {
                    }
                }
                if ("D".equals(freqUnit) && (freq != 0)) {
                    c.add(Calendar.DAY_OF_MONTH, freq);
                }
                else if ("W".equals(freqUnit) && (freq != 0)) {
                    c.add(Calendar.DAY_OF_MONTH, 7 * freq);
                }
                else if ("M".equals(freqUnit) && (freq != 0)) {
                    c.add(Calendar.MONTH, freq);
                }
                else if ("Y".equals(freqUnit) && (freq != 0)) {
                    c.add(Calendar.YEAR, freq);
                }
                endDate = c.getTime();
                // for cms 1723
                Date lastDocEntryDate = DateUtil.convertDate(locale, aForm.getLastDocEntryDate());
                if (endDate.compareTo(lastDocEntryDate) > 0) {
                    errors.add("docEndDate", new ActionMessage("error.date.compareDate", "Last Covenant Entry Date",
                            "Covenant End Date and frequency duration"));
                }
            }
        }
		// Remarks
		/*
		 * min=0; max=2000; if (!(errorCode =
		 * Validator.checkString(aForm.getCovenantItemRemarks(), false, min,
		 * max)).equals(Validator.ERROR_NONE)) {
		 * errors.add("covenantItemRemarks", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * String.valueOf(min), String.valueOf(max))); }
		 */

		/*
		 * Not required. It should check for the total number of characters
		 * (done by javascript already) instead of number of lines. // Remarks
		 * //Updated to new validation (checking for number of lines instead of
		 * just number of characters). JIRA CMS-2483, Defect #71 if (!(errorCode
		 * = RemarksValidatorUtil.checkRemarks(aForm.getCovenantItemRemarks(),
		 * false)).equals(Validator.ERROR_NONE)) {
		 * errors.add("covenantItemRemarks",
		 * RemarksValidatorUtil.getErrorMessage(errorCode)); }
		 */

		// }
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
