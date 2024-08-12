/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/debtors/DebtorsValidator.java,v 1.7 2005/08/12 11:16:03 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.debtors;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/08/12 11:16:03 $ Tag: $Name: $
 */

public class DebtorsValidator {
	public static ActionErrors validateInput(DebtorsForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		boolean isMandatory = aForm.getEvent().equals(DebtorsAction.EVENT_EDIT);

		boolean isAmountEmpty = true;

		for (int i = 0; i < aForm.getDebtorsAgeing().length; i++) {
			if (!(errorCode = Validator.checkAmount(aForm.getDebtorsAgeing()[i], false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("debtorsAgeing" + String.valueOf(i), new ActionMessage(ErrorKeyMapper.map(
						ErrorKeyMapper.AMOUNT, errorCode), "0", maximumAmt));
			}
			if (isAmountEmpty && (aForm.getDebtorsAgeing()[i] != null)
					&& (aForm.getDebtorsAgeing()[i].trim().length() > 0)) {
				isAmountEmpty = false;
			}
		}

		if (!(errorCode = Validator.checkInteger(aForm.getPeriodApplicable(), (isMandatory && true), 0, 999))
				.equals(Validator.ERROR_NONE)) {
			errors.add("periodApplicable", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					"999"));
		}
		if (isAmountEmpty) {
			if (isMandatory && (aForm.getMargin() != null) && (aForm.getMargin().trim().length() > 0)) {
				errors.add("margin", new ActionMessage("error.string.empty"));
			}
		}
		else {
			if (!(errorCode = Validator.checkInteger(aForm.getMargin(), (isMandatory && true), 0, 100))
					.equals(Validator.ERROR_NONE)) {
				errors.add("margin",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100"));
			}
		}
		if (!(errorCode = Validator.checkString(aForm.getValCurrency(), (isMandatory && true), 1, 3))
				.equals(Validator.ERROR_NONE)) {
			errors.add("valCurrency", new ActionMessage("error.string.mandatory", "1", "3"));
		}

		if (!(errorCode = Validator.checkDate(aForm.getValuationDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("valuationDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}

		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getNonStdRevalFreqNum())
				|| !AbstractCommonMapper.isEmptyOrNull(aForm.getNonStdRevalFreqUnit())) {
			if (!(errorCode = Validator.checkInteger(aForm.getNonStdRevalFreqNum(), (isMandatory && true), 1, 100))
					.equals(Validator.ERROR_NONE)) {
				errors.add("nonStdRevalFreqNum", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", 100 + ""));
			}
			if (!(errorCode = Validator.checkString(aForm.getNonStdRevalFreqUnit(), (isMandatory && true), 0, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("nonStdRevalFreqUnit", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.STRING, errorCode), "0", 3 + ""));
			}
		}

		return errors;
	}
}
