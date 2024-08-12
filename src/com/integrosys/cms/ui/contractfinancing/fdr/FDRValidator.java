/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.contractfinancing.fdr;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 2007/Mar/09 $ Tag: $Name: $
 */
public class FDRValidator {

	public static ActionErrors validateInput(FDRForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode;

		try {
			if (!(errorCode = Validator.checkDate(form.getFdrDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("fdrDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}
			if (!(errorCode = Validator.checkString(form.getAccountNo(), true, 1, 25)).equals(Validator.ERROR_NONE)) {
				errors.add("accountNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"25"));
			}
			if (!(errorCode = Validator.checkString(form.getFdrCurrency(), true, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("fdrCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getFdrAmount(), true, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("fdrAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			if (!(errorCode = Validator.checkString(form.getReferenceNo(), true, 1, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("referenceNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"100"));
			}
			if (!(errorCode = Validator.checkString(form.getRemarks(), false, 0, 500)).equals(Validator.ERROR_NONE)) {
				errors.add("remarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"500"));
			}

			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}
}