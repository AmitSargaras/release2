/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/loanschedule/LoanScheduleValidator.java,v 1.7 2004/07/21 05:55:29 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency.loanschedule;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainConstant;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/07/21 05:55:29 $ Tag: $Name: $
 */
public class LoanScheduleValidator {
	public static ActionErrors validateInput(LoanScheduleForm aForm, Locale locale) {
		String errorCode = Validator.ERROR_MANDATORY;
		ActionErrors errors = new ActionErrors();

		String[] paymentDate = aForm.getPaymentDate();
		String[] principalAmt = aForm.getPrincipalAmtDue();
		String[] interestAmt = aForm.getInterestAmtDue();
		if (paymentDate != null) {
			for (int i = 0; i < paymentDate.length; i++) {
				errorCode = Validator.ERROR_MANDATORY;
				boolean isAllInput = false;
				boolean isAtLeastOneInput = false;
				if (!isEmptyOrNull(principalAmt[i]) || !isEmptyOrNull(interestAmt[i])) {
					isAtLeastOneInput = true;
				}
				if (!isEmptyOrNull(principalAmt[i]) && !isEmptyOrNull(interestAmt[i])) {
					isAllInput = true;
				}
				if (isAtLeastOneInput && !isAllInput) {
					/*
					 * if (isEmptyOrNull(paymentDate[i])) {
					 * errors.add("paymentDate"+String.valueOf(i), new
					 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE,
					 * errorCode), "0", 20 + ""));
					 * DefaultLogger.debug("LoanScheduleValidator",
					 * "<<<<<< HSHII : paymentDate Error: paymentDate"+i); }
					 */
					if (isEmptyOrNull(principalAmt[i])) {
						errors.add("principalAmtDue" + String.valueOf(i), new ActionMessage(ErrorKeyMapper.map(
								ErrorKeyMapper.NUMBER, errorCode), "0", CommodityDealConstant.MAX_AMOUNT_STR));
						DefaultLogger.debug("LoanScheduleValidator",
								"<<<<<< HSHII : principalAmtDue Error: primcipalAmtDue" + i);
					}
					if (isEmptyOrNull(interestAmt[i])) {
						errors.add("interestAmtDue" + String.valueOf(i), new ActionMessage(ErrorKeyMapper.map(
								ErrorKeyMapper.NUMBER, errorCode), "0", CommodityDealConstant.MAX_AMOUNT_STR));
						DefaultLogger.debug("LoanScheduleValidator",
								"<<<<<< HSHII : interestAmtDue Error: interestAmtDue" + i);
					}
				}
				if (!(errorCode = Validator.checkNumber(principalAmt[i], false, 0,
						CommodityMainConstant.MAX_AMOUNT_15_2, 3, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("principalAmtDue" + String.valueOf(i), new ActionMessage(ErrorKeyMapper.map(
							ErrorKeyMapper.NUMBER, errorCode), "0", CommodityMainConstant.MAX_AMOUNT_15_2_STR));
				}
				if (!(errorCode = Validator.checkNumber(interestAmt[i], false, 0,
						CommodityMainConstant.MAX_AMOUNT_15_2, 3, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("interestAmtDue" + String.valueOf(i), new ActionMessage(ErrorKeyMapper.map(
							ErrorKeyMapper.NUMBER, errorCode), "0", CommodityMainConstant.MAX_AMOUNT_15_2_STR));
				}
			}
		}
		return errors;
	}

	private static boolean isEmptyOrNull(String str) {
		if (str == null) {
			return true;
		}
		if (str.length() == 0) {
			return true;
		}
		return false;
	}
}
