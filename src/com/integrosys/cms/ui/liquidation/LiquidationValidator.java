/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/liquidation/LiquidationValidator.java,v 1 2007/02/08 Lini Exp $
 */
package com.integrosys.cms.ui.liquidation;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.UIValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Describe this class. Purpose: Validation for Liquidation Description:
 * Validate the percentage that user key in
 * 
 * @author $Author: lini$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/08$ Tag: $Name$
 */

public class LiquidationValidator {

	/**
	 * Description : Check the input value
	 * 
	 * @param form is of type CommonForm
	 * @param locale of type Locale
	 * @return errors with error messages
	 */

	public static ActionErrors validateInput(LiquidationForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String event = form.getEvent();
		String errorCode = null;

		if (LiquidationAction.EV_ADD_RECOVERY_EXPENSE.equals(event)
				|| LiquidationAction.EV_EDIT_RECOVERY_EXPENSE.equals(event)) {

			if (!(errorCode = Validator.checkString(form.getExpenseRemarks(), false, 1, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("expenseRemarksError", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.STRING, errorCode), "1", "250"));
			}
			if (!(Validator.checkString(form.getExpenseType(), true, 1, 15)).equals(Validator.ERROR_NONE)) {
				errors.add("expenseTypeError", new ActionMessage("label.please.select.option"));
			}

			if (!(errorCode = UIValidator.checkNumber(form.getExpenseAmt(), true, 0.0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2, 3, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("expenseAmtError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR));
			}

			if (!(errorCode = Validator.checkDate(form.getDateOfExpense(), true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("dateOfExpenseError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"1", "10"));
			}
		}
		if (LiquidationAction.EV_ADD_RECOVERY.equals(event) || LiquidationAction.EV_EDIT_RECOVERY.equals(event)) {

//			System.out.println("event = " + event);
			if (!(Validator.checkString(form.getRecoveryType(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
				errors.add("recoveryTypeError", new ActionMessage("label.please.select.option"));
			}
			if (!(errorCode = Validator.checkString(form.getRecoveryRemarks(), false, 1, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("recoveryRemarksError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "1", "250"));
			}

		}
		if (LiquidationAction.EV_ADD_RECOVERY_INCOME.equals(event)
				|| LiquidationAction.EV_EDIT_RECOVERY_INCOME.equals(event)) {

			if (!(errorCode = Validator.checkDate(form.getDateAmtRecovered(), true, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("dateAmtRecoveredError", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.DATE, errorCode), "1", "10"));
			}
			if (!(errorCode = Validator.checkString(form.getAmtRecoveryRemarks(), false, 1, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("amtRecoveryRemarksError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "1", "250"));
			}
			if (!(errorCode = Validator.checkAmount(form.getAmtRecovered(), true, 2l,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2, form.getAmtRecoveredCurrency(), locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("amtRecoveredError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR));
			}
		}

		if (LiquidationAction.EVENT_LIST.equals(event) || "checker_view".equals(event)) {

		}
		else if (LiquidationAction.EV_MKR_EDIT_LIQ_CONFIRM.equals(event)) {

		}
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());

		return errors;

	}

}
