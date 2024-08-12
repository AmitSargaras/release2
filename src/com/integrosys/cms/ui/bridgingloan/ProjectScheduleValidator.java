/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Project Schedule Validator
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class ProjectScheduleValidator {
	public static ActionErrors validateInput(com.integrosys.cms.ui.bridgingloan.ProjectScheduleForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errMsg = null;

		try {
			DefaultLogger.debug("in ProjectScheduleValidator", "form.getEvent()=" + form.getEvent());

			String progressStage = form.getProgressStage();
			String startDate = form.getStartDate();
			String endDate = form.getEndDate();
			String actualStartDate = form.getActualStartDate();
			String actualEndDate = form.getActualEndDate();
			String remarks = form.getRemarks();

			if (!(errMsg = Validator.checkString(progressStage, true, 0, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("progressStage", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
						"100"));
			}
			if (!(errMsg = Validator.checkDate(startDate, false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("startDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkDate(endDate, false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("endDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkDate(actualStartDate, false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("actualStartDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkDate(actualEndDate, false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("actualEndDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkString(remarks, false, 0, 500)).equals(Validator.ERROR_NONE)) {
				errors.add("remarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "500"));
			}
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}
}