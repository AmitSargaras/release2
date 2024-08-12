/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.contractfinancing.advspayment;

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
public class PaymentValidator {

	public static ActionErrors validateInput(PaymentForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode;

		try {

			if (!(errorCode = Validator.checkDate(form.getReceiveDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("receiveDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}
			if (!(errorCode = Validator.checkString(form.getReceiveCurrency(), true, 1, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("receiveCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"1", "3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getReceiveAmount(), true, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("receiveAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			if (!(errorCode = Validator.checkString(form.getReceiveFrom(), false, 0, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("receiveFrom", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"30"));
			}
			if (!(errorCode = Validator.checkDate(form.getDistributeDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("distributeDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}
			if (!(errorCode = Validator.checkString(form.getDistributeCurrency(), true, 1, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("distributeCurrency", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getDistributeAmount(), true, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("distributeAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			// if (!(errorCode = Validator.checkString(form.getFdrInd(), false,
			// 1, 1)).equals(Validator.ERROR_NONE)) {
			// errors.add("frdInd", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
			// errorCode), "1", "1"));
			// }
			if (!(errorCode = Validator.checkString(form.getFdrCurrency(), false, 0, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("frdCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getFdrAmount(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("frdAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			// if (!(errorCode = Validator.checkString(form.getRepoInd(), false,
			// 1, 1)).equals(Validator.ERROR_NONE)) {
			// errors.add("repoInd", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
			// errorCode), "1", "1"));
			// }
			if (!(errorCode = Validator.checkString(form.getRepoCurrency(), false, 0, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("repoCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getRepoAmount(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("repoAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			// if (!(errorCode = Validator.checkString(form.getApgInd(), false,
			// 1, 1)).equals(Validator.ERROR_NONE)) {
			// errors.add("apgInd", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
			// errorCode), "1", "1"));
			// }
			if (!(errorCode = Validator.checkString(form.getApgCurrency(), false, 0, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("apgCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getApgAmount(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("apgAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			// if (!(errorCode = Validator.checkString(form.getTl1Ind(), false,
			// 1, 1)).equals(Validator.ERROR_NONE)) {
			// errors.add("tl1Ind", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
			// errorCode), "1", "1"));
			// }
			if (!(errorCode = Validator.checkString(form.getTl1Currency(), false, 0, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("tl1Currency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getTl1Amount(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("tl1Amount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			// if (!(errorCode = Validator.checkString(form.getTl2Ind(), false,
			// 1, 1)).equals(Validator.ERROR_NONE)) {
			// errors.add("tl2Ind", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
			// errorCode), "1", "1"));
			// }
			if (!(errorCode = Validator.checkString(form.getTl2Currency(), false, 0, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("tl2Currency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getTl2Amount(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("tl2Amount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			// if (!(errorCode = Validator.checkString(form.getTl3Ind(), false,
			// 1, 1)).equals(Validator.ERROR_NONE)) {
			// errors.add("tl3Ind", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
			// errorCode), "1", "1"));
			// }
			if (!(errorCode = Validator.checkString(form.getTl3Currency(), false, 0, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("tl3Currency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getTl3Amount(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("tl3Amount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}

			if (!(errorCode = Validator.checkString(form.getCollectionAccCurrency(), false, 0, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("collectionAccCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "0", "3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getCollectionAccAmount(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("collectionAccAmount", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.AMOUNT, errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}

			// if (!(errorCode = Validator.checkString(form.getBcInd(), false,
			// 1, 1)).equals(Validator.ERROR_NONE)) {
			// errors.add("bcInd", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
			// errorCode), "1", "1"));
			// }
			if (!(errorCode = Validator.checkString(form.getBcCurrency(), false, 0, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("bcCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getBcAmount(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("bcAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			// if (!(errorCode = Validator.checkString(form.getOthInd(), false,
			// 1, 1)).equals(Validator.ERROR_NONE)) {
			// errors.add("othInd", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
			// errorCode), "1", "1"));
			// }
			if (!(errorCode = Validator.checkString(form.getOthCurrency(), false, 0, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("othCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getOthAmount(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("othAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
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