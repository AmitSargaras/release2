/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan.fdr;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * FDR Validation
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class FDRValidator {
	public static ActionErrors validateInput(com.integrosys.cms.ui.bridgingloan.fdr.FDRForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errMsg = null;

		try {
			DefaultLogger.debug("in FDRValidator", "form.getEvent()=" + form.getEvent());

			String fdrDate = form.getFdrDate();
			String accountNo = form.getAccountNo();
			String fdrCurrency = form.getFdrCurrency();
			String fdrAmount = form.getFdrAmount();
			String referenceNo = form.getReferenceNo();
			String remarks = form.getRemarks();

			if (!(errMsg = Validator.checkDate(fdrDate, true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("fdrDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkString(accountNo, true, 0, 25)).equals(Validator.ERROR_NONE)) {
				errors
						.add("accountNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
								"25"));
			}
			if (!(errMsg = Validator.checkString(fdrCurrency, true, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("fdrCurrency",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "1", "3"));
			}
			if (!(errMsg = Validator.checkAmount(fdrAmount, true, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15,
					IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("fdrAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errMsg), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			if (!(errMsg = Validator.checkString(referenceNo, true, 0, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("referenceNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
						"30"));
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