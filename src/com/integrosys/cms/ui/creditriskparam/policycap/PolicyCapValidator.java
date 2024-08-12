/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.policycap;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class PolicyCapValidator {

	public static ActionErrors validateInput(com.integrosys.cms.ui.creditriskparam.policycap.PolicyCapForm form,
			Locale locale) {
		ActionErrors errors = new ActionErrors();

		// check drop down selection for EVENT_PREPARE page
		DefaultLogger.debug("Search Value is ", "" + form.getSearch());
		if ((form.getEvent().equals(PolicyCapAction.EVENT_MAKER_EDIT) ||
				form.getEvent().equals(PolicyCapAction.EVENT_READ))
				&& ((form.getSearch() != null) && (form.getSearch().length() > 0))) {
			String stockExchange = form.getStockExchange();
			String bankEntity = form.getBankEntity();

			String errMsg = Validator.checkString(stockExchange, true, 0, 100);

			if (!errMsg.equals(Validator.ERROR_NONE)) {
				DefaultLogger.debug("errMessage is ", "" + errMsg);
				if (errMsg.equals("mandatory")) {
					errors.add("stockExchange", new ActionMessage("error.string.mandatory", "1", "20"));
				}
			}

			errMsg = Validator.checkString(bankEntity, true, 0, 100);

			if (!errMsg.equals(Validator.ERROR_NONE)) {
				DefaultLogger.debug("errMessage is ", "" + errMsg);
				if (errMsg.equals("mandatory")) {
					errors.add("bankEntity", new ActionMessage("error.string.mandatory", "1", "20"));
				}
			}
			return errors;
		}

		// Go into maker process, no validation is required
		if (form.getEvent().equals(PolicyCapAction.EVENT_MAKER_EDIT)
				&& ((form.getSearch() == null) || (form.getSearch().length() == 0))) {

			return errors;
		}

		try {
			DefaultLogger.debug("in PolicyCapValidator", "start....");

			String[] board = form.getBoard();
			//String[] maxTradeCapNonFI = form.getMaxTradeCapNonFI();
			String[] maxCollateralCapNonFI = form.getMaxCollateralCapNonFI();
			String[] quotaCollateralCapNonFI = form.getQuotaCollateralCapNonFI();
			String[] maxCollateralCapFI = form.getMaxCollateralCapFI();
			String[] quotaCollateralCapFI = form.getQuotaCollateralCapFI();
			//String[] liquidMOA = form.getLiquidMOA();
			//String[] illiquidMOA = form.getIlliquidMOA();
			String[] maxPriceCap = form.getMaxPriceCap();
			String isBankGroupString = form.getIsBankGroup();
			boolean isBankGroup = (isBankGroupString == null) ? false : new Boolean(isBankGroupString).booleanValue();
			DefaultLogger.debug("isBankGroup is in PolicyCapValidator ..... ", "" + isBankGroup + " , "
					+ isBankGroupString);

			if ((board == null) || (board.length == 0)) {
				errors.add("board", new ActionMessage("error.string.mandatory"));
			}
			else {

				for (int i = 0; i < board.length; i++) {
					/*
					 * String errMsg =
					 * Validator.checkNumber(maxTradeCapNonFI[i], true, 0, 100,
					 * 2, locale); String propertyName = "maxTradeCapNonFI" + i;
					 * if (!errMsg.equals(Validator.ERROR_NONE)) {
					 * DefaultLogger.debug("errMessage is ", "" + errMsg); if
					 * (errMsg.equals("greaterthan") ||
					 * errMsg.equals("lessthan")) errors.add(propertyName, new
					 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					 * "heightlessthan"), "0", "100")); else if
					 * (errMsg.equals("decimalexceeded"))
					 * errors.add(propertyName, new
					 * ActionMessage("error.number.moredecimalexceeded", "", "",
					 * "1")); else errors.add(propertyName, new
					 * ActionMessage("error.number." + errMsg)); }
					 */

					boolean isErrorFound = false;
					String errMsg = Validator.checkNumber(maxCollateralCapNonFI[i], true, 0, 100, 2, locale);
					String propertyName = "maxCollateralCapNonFI" + i;
					if (!errMsg.equals(Validator.ERROR_NONE)) {
						DefaultLogger.debug("errMessage is ", "" + errMsg);
						isErrorFound = true;
						if (errMsg.equals("greaterthan") || errMsg.equals("lessthan")) {
							errors.add(propertyName, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
									"heightlessthan"), "0", "100"));
						}
						else if (errMsg.equals("decimalexceeded")) {
							errors
									.add(propertyName, new ActionMessage("error.number.moredecimalexceeded", "", "",
											"1"));
						}
						else {
							errors.add(propertyName, new ActionMessage("error.number." + errMsg));
						}
					}

					errMsg = Validator.checkNumber(quotaCollateralCapNonFI[i], true, 0, 100, 2, locale);
					propertyName = "quotaCollateralCapNonFI" + i;
					if (!errMsg.equals(Validator.ERROR_NONE)) {
						DefaultLogger.debug("errMessage is ", "" + errMsg);
						isErrorFound = true;
						if (errMsg.equals("greaterthan") || errMsg.equals("lessthan")) {
							errors.add(propertyName, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
									"heightlessthan"), "0", "100"));
						}
						else if (errMsg.equals("decimalexceeded")) {
							errors
									.add(propertyName, new ActionMessage("error.number.moredecimalexceeded", "", "",
											"1"));
						}
						else {
							errors.add(propertyName, new ActionMessage("error.number." + errMsg));
						}
					}
					if (!isErrorFound) { // no error found at
											// maxCollateralCapNonFI and
											// quotaCollateralCapNonFI
						if (Double.parseDouble(quotaCollateralCapNonFI[i]) > Double
								.parseDouble(maxCollateralCapNonFI[i])) {
							errors.add(propertyName, new ActionMessage("error.amount.not.greaterthan",
									"Quota Collateral Cap(Non FI)", "Maximum Collateral Cap(Non FI)"));
						}
					}

					if ((i < maxCollateralCapFI.length) && (maxCollateralCapFI[i] != null)) {
						isErrorFound = false;
						errMsg = Validator.checkNumber(maxCollateralCapFI[i], true, 0, 100, 2, locale);
						propertyName = "maxCollateralCapFI" + i;
						if (!errMsg.equals(Validator.ERROR_NONE)) {
							DefaultLogger.debug("errMessage is ", "" + errMsg);
							isErrorFound = true;
							if (errMsg.equals("greaterthan") || errMsg.equals("lessthan")) {
								errors.add(propertyName, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
										"heightlessthan"), "0", "100"));
							}
							else if (errMsg.equals("decimalexceeded")) {
								errors.add(propertyName, new ActionMessage("error.number.moredecimalexceeded", "", "",
										"1"));
							}
							else {
								errors.add(propertyName, new ActionMessage("error.number." + errMsg));
							}
						}
					}

					if ((i < quotaCollateralCapFI.length) && (quotaCollateralCapFI[i] != null)) {
						isErrorFound = false;
						errMsg = Validator.checkNumber(quotaCollateralCapFI[i], true, 0, 100, 2, locale);
						propertyName = "quotaCollateralCapFI" + i;
						if (!errMsg.equals(Validator.ERROR_NONE)) {
							DefaultLogger.debug("errMessage is ", "" + errMsg);
							isErrorFound = true;
							if (errMsg.equals("greaterthan") || errMsg.equals("lessthan")) {
								errors.add(propertyName, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
										"heightlessthan"), "0", "100"));
							}
							else if (errMsg.equals("decimalexceeded")) {
								errors.add(propertyName, new ActionMessage("error.number.moredecimalexceeded", "", "",
										"1"));
							}
							else {
								errors.add(propertyName, new ActionMessage("error.number." + errMsg));
							}
						}
						if (!isErrorFound) { // no error found at
												// maxCollateralCapFI and
												// quotaCollateralCapFI
							if (Double.parseDouble(quotaCollateralCapFI[i]) > Double.parseDouble(maxCollateralCapFI[i])) {
								errors.add(propertyName, new ActionMessage("error.amount.not.greaterthan",
										"Quota Collateral Cap(FI)", "Maximum Collateral Cap(FI)"));
							}
						}
					}

					// applicable only to those belongs to bank group
					if (isBankGroup) {
						/*
						errMsg = Validator.checkNumber(liquidMOA[i], true, 0, 100, 2, locale);
						propertyName = "liquidMOA" + i;
						if (!errMsg.equals(Validator.ERROR_NONE)) {
							DefaultLogger.debug("errMessage is ", "" + errMsg);
							if (errMsg.equals("greaterthan") || errMsg.equals("lessthan")) {
								errors.add(propertyName, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
										"heightlessthan"), "0", "100"));
							}
							else if (errMsg.equals("decimalexceeded")) {
								errors.add(propertyName, new ActionMessage("error.number.moredecimalexceeded", "", "",
										"1"));
							}
							else {
								errors.add(propertyName, new ActionMessage("error.number." + errMsg));
							}
						}

						errMsg = Validator.checkNumber(illiquidMOA[i], true, 0, 100, 2, locale);
						propertyName = "illiquidMOA" + i;
						if (!errMsg.equals(Validator.ERROR_NONE)) {
							DefaultLogger.debug("errMessage is ", "" + errMsg);
							if (errMsg.equals("greaterthan") || errMsg.equals("lessthan")) {
								errors.add(propertyName, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
										"heightlessthan"), "0", "100"));
							}
							else if (errMsg.equals("decimalexceeded")) {
								errors.add(propertyName, new ActionMessage("error.number.moredecimalexceeded", "", "",
										"1"));
							}
							else {
								errors.add(propertyName, new ActionMessage("error.number." + errMsg));
							}
						}
						*/

						errMsg = Validator.checkNumber(maxPriceCap[i], true, 0, 99999.99, 3, locale);
						propertyName = "maxPriceCap" + i;
						if (!errMsg.equals(Validator.ERROR_NONE)) {
							DefaultLogger.debug("errMessage is ", "" + errMsg);
							if (errMsg.equals("greaterthan") || errMsg.equals("lessthan")) {
								errors.add(propertyName, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
										"heightlessthan"), "0", "99999.99"));
							}
							else if (errMsg.equals("decimalexceeded")) {
								errors.add(propertyName, new ActionMessage("error.number.moredecimalexceeded", "", "",
										"2"));
							}
							else {
								errors.add(propertyName, new ActionMessage("error.number." + errMsg));
							}
						}
					}
				}
			}
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}
}