/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/settlementdetails/SettlementValidator.java,v 1.9 2004/12/01 13:47:33 wltan Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.settlementdetails;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2004/12/01 13:47:33 $ Tag: $Name: $
 */

public class SettlementValidator {
	public static ActionErrors validateInput(SettlementForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		// set maximum payment amount to the balance outstanding
		double originalPaymentAmount = (aForm.getOriginalPaymentAmount().length() != 0) ? UIUtil.mapStringToBigDecimal(
				aForm.getOriginalPaymentAmount()).doubleValue() : 0d;
		double balanceAmount = (aForm.getBalanceOutstanding().length() != 0) ? UIUtil.mapStringToBigDecimal(
				aForm.getBalanceOutstanding()).doubleValue() : CommodityDealConstant.MAX_AMOUNT_15_2;
		double maxPayment = (balanceAmount != CommodityDealConstant.MAX_AMOUNT_15_2) ? (balanceAmount + originalPaymentAmount)
				: CommodityDealConstant.MAX_AMOUNT_15_2;

		if (aForm.getEvent().equals(SettlementAction.EVENT_REFRESH)) {
			if (!(errorCode = Validator.checkDate(aForm.getPaymentDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("paymentDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
						256 + ""));
			}
			else {
				errors = validatePreviousDate(errors, aForm.getPaymentDate(), "paymentDate", "Date of Payment", locale);
			}
			if (!(errorCode = Validator.checkNumber(aForm.getPaymentAmount(), false, 0, maxPayment, 3, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("paymentAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", Double.toString(maxPayment)));
			}
		}
		else {
			if (!(errorCode = Validator.checkDate(aForm.getPaymentDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("paymentDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
						256 + ""));
			}
			else {
				errors = validatePreviousDate(errors, aForm.getPaymentDate(), "paymentDate", "Date of Payment", locale);
			}
			if (!(errorCode = Validator.checkNumber(aForm.getPaymentAmount(), true, 0, maxPayment, 3, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("paymentAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", Double.toString(maxPayment)));
			}
		}

		return errors;
	}

	private static ActionErrors validatePreviousDate(ActionErrors errors, String dateStr, String fieldName,
			String desc, Locale locale) {
		if ((dateStr != null) && (dateStr.length() > 0)) {
			Date dateObj = DateUtil.convertDate(locale, dateStr);
			if (dateObj.after(DateUtil.getDate())) {
				errors.add(fieldName, new ActionMessage("error.collateral.commodity.not.futuredate", desc));
			}
		}
		return errors;
	}
}
