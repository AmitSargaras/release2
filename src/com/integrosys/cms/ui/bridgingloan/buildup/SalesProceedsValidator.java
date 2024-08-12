package com.integrosys.cms.ui.bridgingloan.buildup;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Sales Proceeds Validation
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class SalesProceedsValidator {
	public static ActionErrors validateInput(com.integrosys.cms.ui.bridgingloan.buildup.SalesProceedsForm form,
			Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errMsg = null;

		try {
			DefaultLogger.debug("in SalesProceedsValidator", "start....");
			DefaultLogger.debug("in SalesProceedsValidator", "form.getEvent()=" + form.getEvent());

			String proceedsDate = form.getProceedsDate();
			String purpose = form.getPurpose(); // Purpose of Proceeds
			String purposePercent = form.getPurposePercent(); // Purpose of
																// Proceeds
																// Percentage
			String bankName = form.getBankName();
			String chequeNo = form.getChequeNo();
			String receiveCurrency = form.getReceiveCurrency();
			String receiveAmount = form.getReceiveAmount();
			String status = form.getStatus();
			String remarks = form.getRemarks();
			String distributeDate = form.getDistributeDate();
			String distributeCurrency = form.getDistributeCurrency();
			String distributeAmount = form.getDistributeAmount();
			// String tL1Ind = form.getIsToTL1(); //TL/AC or TL
			String tL1Currency = form.getTL1Currency();
			String tL1Amount = form.getTL1Amount();
			// String odInd = form.getIsToOD(); //OD/AC or OD
			String odCurrency = form.getOdCurrency();
			String odAmount = form.getOdAmount();
			// String fdrInd = form.getIsToFDR(); //Sinking FUND-FDR
			String fdrCurrency = form.getFdrCurrency();
			String fdrAmount = form.getFdrAmount();
			// String hdaInd = form.getIsToHDA(); //Credit HDA
			String hdaCurrency = form.getHdaCurrency();
			String hdaAmount = form.getHdaAmount();
			// String othersInd = form.getIsToOthers();
			String othersAccount = form.getOthersAccount();
			String othersCurrency = form.getOthersCurrency();
			String othersAmount = form.getOthersAmount();

			if (!(errMsg = Validator.checkDate(proceedsDate, true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("proceedsDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkString(purpose, true, 0, 10)).equals(Validator.ERROR_NONE)) {
				errors.add("purpose", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "10"));
			}
			if (!(errMsg = Validator.checkNumber(purposePercent, false, 0, 999.9)).equals(Validator.ERROR_NONE)) {
				errors.add("purposePercent", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errMsg), "0",
						"999.9"));
			}
			if (!(errMsg = Validator.checkString(bankName, true, 0, 100)).equals(Validator.ERROR_NONE)) {
				errors
						.add("bankName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
								"100"));
			}
			if (!(errMsg = Validator.checkString(chequeNo, true, 0, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("chequeNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "50"));
			}
			if (!(errMsg = Validator.checkString(receiveCurrency, true, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("receiveCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "1",
						"3"));
			}
			if (!(errMsg = Validator.checkAmount(receiveAmount, true, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15,
					IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("receiveAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errMsg), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			if (!(errMsg = Validator.checkString(status, true, 0, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("status", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "3"));
			}
			if (!(errMsg = Validator.checkString(remarks, false, 0, 500)).equals(Validator.ERROR_NONE)) {
				errors.add("remarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "500"));
			}

			if (!(errMsg = Validator.checkDate(distributeDate, false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("distributeDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkString(distributeCurrency, false, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("distributeCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg),
						"1", "3"));
			}
			if (!(errMsg = Validator.checkAmount(distributeAmount, false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("distributeAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errMsg),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			// if (!(errMsg = Validator.checkString(tL1Ind, false, 0,
			// 1)).equals(Validator.ERROR_NONE)) {
			// errors.add("tL1Ind", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg),
			// "0", "1"));
			// }
			if (!(errMsg = Validator.checkString(tL1Currency, false, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("tL1Currency",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "1", "3"));
			}
			if (!(errMsg = Validator.checkAmount(tL1Amount, false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15,
					IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("tL1Amount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errMsg), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			// if (!(errMsg = Validator.checkString(odInd, false, 0,
			// 1)).equals(Validator.ERROR_NONE)) {
			// errors.add("odInd", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg),
			// "0", "1"));
			// }
			if (!(errMsg = Validator.checkString(odCurrency, false, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors
						.add("odCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "1",
								"3"));
			}
			if (!(errMsg = Validator.checkAmount(odAmount, false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15,
					IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("odAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errMsg), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			// if (!(errMsg = Validator.checkString(fdrInd, false, 0,
			// 1)).equals(Validator.ERROR_NONE)) {
			// errors.add("fdrInd", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg),
			// "0", "1"));
			// }
			if (!(errMsg = Validator.checkString(fdrCurrency, false, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("fdrCurrency",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "1", "3"));
			}
			if (!(errMsg = Validator.checkAmount(fdrAmount, false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15,
					IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("fdrAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errMsg), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			// if (!(errMsg = Validator.checkString(hdaInd, false, 0,
			// 1)).equals(Validator.ERROR_NONE)) {
			// errors.add("hdaInd", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg),
			// "0", "1"));
			// }
			if (!(errMsg = Validator.checkString(hdaCurrency, false, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("hdaCurrency",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "1", "3"));
			}
			if (!(errMsg = Validator.checkAmount(hdaAmount, false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15,
					IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("hdaAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errMsg), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			// if (!(errMsg = Validator.checkString(othersInd, false, 0,
			// 1)).equals(Validator.ERROR_NONE)) {
			// errors.add("othersInd", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg),
			// "0", "1"));
			// }
			if (!(errMsg = Validator.checkString(othersAccount, false, 0, 25)).equals(Validator.ERROR_NONE)) {
				errors.add("othersAccount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
						"25"));
			}
			if (!(errMsg = Validator.checkString(othersCurrency, false, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("othersCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "1",
						"3"));
			}
			if (!(errMsg = Validator.checkAmount(othersAmount, false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15,
					IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("othersAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errMsg), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}
}