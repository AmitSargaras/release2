/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Settlement Validation
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class SettlementValidator {
	public static ActionErrors validateInput(com.integrosys.cms.ui.bridgingloan.advspayment.SettlementForm form,
			Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errMsg = null;

		try {
			DefaultLogger.debug("in SettlementValidator", "start....");
			DefaultLogger.debug("in SettlementValidator", "form.getEvent()=" + form.getEvent());

			String settlementDate = form.getSettlementDate();
			String settledCurrency = form.getSettledCurrency();
			String settledAmount = form.getSettledAmount();
			String outstandingCurrency = form.getOutstandingCurrency();
			String outstandingAmount = form.getOutstandingAmount();
			String remarks = form.getRemarks();

			if (!(errMsg = Validator.checkDate(settlementDate, true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("settlementDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkString(settledCurrency, true, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("settledCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "1",
						"3"));
			}
			if (!(errMsg = Validator.checkAmount(settledAmount, true, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15,
					IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("settledAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errMsg), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			if (!(errMsg = Validator.checkString(outstandingCurrency, true, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("outstandingCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg),
						"1", "3"));
			}
			if (!(errMsg = Validator.checkAmount(outstandingAmount, true, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("outstandingAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errMsg),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
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