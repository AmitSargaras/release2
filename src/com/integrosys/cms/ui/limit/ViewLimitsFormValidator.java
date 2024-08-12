package com.integrosys.cms.ui.limit;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Created by IntelliJ IDEA. User: pooja Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class ViewLimitsFormValidator {

	public static ActionErrors validateInput(ViewLimitsForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		DefaultLogger.debug(" aForm req sec coverage", "--------->" + aForm.getRequiredSecurityCoverage());
		// DefaultLogger.debug(" aForm amount1", "--------->" +
		// aForm.getAmount1());
		try {

			DefaultLogger.debug("inside try", "try");
			/*
			 * Amount amt = CurrencyManager.convertToAmount(locale,
			 * aForm.getCurrencyCode1(), aForm.getAmount1()); double max =
			 * amt.getAmount(); DefaultLogger.debug("amt currency",
			 * amt.getCurrencyCode()); DefaultLogger.debug("max", "--------->" +
			 * max); DefaultLogger.debug(" and aForm amount", "--------->" +
			 * aForm.getAmount()); if
			 * (!Validator.validateAmount(aForm.getAmount(), true, 0, max,
			 * aForm.getCurrencyCode(), locale)) { // errors.add("amt", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER,
			 * "heightlessthan"), "0", aForm.getAmount1())); errors.add("amt1",
			 * new ActionMessage("error.number.format"));
			 * DefaultLogger.debug(" ERROR occured in activted limit",
			 * "--------->" + errors.size()); } if
			 * (aForm.getRequiredSecurityCoverage() != null) { String errMsg =
			 * Validator.checkNumber(aForm.getRequiredSecurityCoverage(), false,
			 * 0, 999, 0, locale); if (!errMsg.equals(Validator.ERROR_NONE)) {
			 * DefaultLogger.debug("errMessage is ", "" + errMsg); if
			 * (errMsg.equals("greaterthan") || errMsg.equals("lessthan"))
			 * errors.add("secCov", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
			 * "heightlessthan"), "0", "999")); else errors.add("secCov", new
			 * ActionMessage("error.number." + errMsg));
			 * DefaultLogger.debug(" ERROR occured in getRequiredSecurityCoverage"
			 * , "--------->" + errors.size()); }
			 * 
			 * DefaultLogger.debug(" Total Errors", "--------->" +
			 * errors.size()); }
			 */
		}
		catch (Exception e) {
		}
		return errors;

	}

}