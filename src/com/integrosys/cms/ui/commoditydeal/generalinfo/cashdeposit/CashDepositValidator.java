/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/generalinfo/cashdeposit/CashDepositValidator.java,v 1.8 2005/11/09 09:31:47 czhou Exp $
 */
package com.integrosys.cms.ui.commoditydeal.generalinfo.cashdeposit;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;

/**
 * Description
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/11/09 09:31:47 $ Tag: $Name: $
 */

public class CashDepositValidator {
	public static ActionErrors validateInput(CashDepositForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		if (!(errorCode = Validator.checkString(aForm.getTypeCashHolding(), true, 0, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("typeCashHolding", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					20 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getDepositNumber(), true, 0, 15)).equals(Validator.ERROR_NONE)) {
			errors.add("depositNumber", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					15 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getDepositCcy(), true, 0, 3)).equals(Validator.ERROR_NONE)) {
			errors.add("depositCcy", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					3 + ""));
		}
		if (!(errorCode = Validator.checkNumber(aForm.getDepositAmt(), true, 0, CommodityDealConstant.MAX_AMOUNT, 0,
				locale)).equals(Validator.ERROR_NONE)) {
			errors.add("depositAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					CommodityDealConstant.MAX_AMOUNT_STR));
		}
		if (!(errorCode = Validator.checkString(aForm.getCashLocation(), true, 0, 10)).equals(Validator.ERROR_NONE)) {
			errors.add("cashLocation", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					10 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getDepositMaturityDate(), true, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("depositMaturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
					"0", 256 + ""));
		}
		/*
		 * else { //UAT1.4, JIRA CMS-2546, Defect #12: Allow Deposit Maturity
		 * Date to be any date be it past, present, future errors =
		 * validateFutureDate(errors, aForm.getDepositMaturityDate(),
		 * "depositMaturityDate", "Deposit Maturity Date", locale); }
		 */

		return errors;
	}

	private static ActionErrors validateFutureDate(ActionErrors errors, String dateStr, String fieldName, String desc,
			Locale locale) {
		if ((dateStr != null) && (dateStr.length() > 0)) {
			Date dateObj = DateUtil.convertDate(locale, dateStr);
			if (dateObj.before(DateUtil.getDate())) {
				errors.add(fieldName, new ActionMessage("error.collateral.commodity.futuredate", desc));
			}
		}
		return errors;
	}
}
