package com.integrosys.cms.ui.limit;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

/**
 * Created by IntelliJ IDEA. User: pooja Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class CoBorrowerValidator {

	public static ActionErrors validateInput(ViewLimitsForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		// DefaultLogger.debug(" aForm amount1", "--------->" +
		// aForm.getAmount1());
		try {

			// Amount amt = CurrencyManager.convertToAmount(locale,
			// aForm.getCurrencyCode1(), aForm.getAmount1());
			// double max = amt.getAmount();
			// DefaultLogger.debug("max", "--------->" + max);
			/*
			 * Amount amount =
			 * CurrencyManager.convertToAmount(locale,aForm.getCurrencyCode
			 * (),aForm.getAmount()); String actamt
			 * =Double.toString(amount.getAmount());
			 */
			/*
			 * if (!Validator.validateAmount(aForm.getAmount(), false, 0, max,
			 * aForm.getCurrencyCode(), locale)) { // if
			 * (!Validator.validateAmount(actamt,false,0,max,"",locale)) { //
			 * errors.add("amt", new ActionMessage("error.integer.lessthan"));
			 * errors.add("amt", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER,
			 * "heightlessthan"), "0", aForm.getAmount1())); errors.add("amt1",
			 * new ActionMessage("error.number.format"));
			 * DefaultLogger.debug(" ERROR occured in activted limit",
			 * "--------->" + errors.size()); }
			 */
		}
		catch (Exception e) {
		}
		return errors;

	}

}