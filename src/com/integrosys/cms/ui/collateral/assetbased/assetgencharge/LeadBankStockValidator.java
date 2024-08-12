package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.base.techinfra.validation.ValidatorConstant.ERROR_NONE;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.DEFAULT_CURRENCY;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2_STR;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.MAXIMUM_ALLOWED_VALUE_3_2_STR;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.MAXIMUM_ALLOWED_VALUE_3_2;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.NumberValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;;

public class LeadBankStockValidator {

	public static ActionErrors validateInput(CommonForm commonForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		LeadBankStockForm form = (LeadBankStockForm) commonForm;
		String errorCode;
		
		boolean isLeadBankStockBankingArr = ICMSConstant.YES.equals(form.getIsLeadBankStockBankingArr());

		if (!ERROR_NONE.equals(errorCode = NumberValidator.checkNumber(form.getDrawingPowerAsPerLeadBank(), isLeadBankStockBankingArr,
				BigDecimal.ZERO, MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL, 3, locale))) {
			errors.add("drawingPowerAsPerLeadBankError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0, MAXIMUM_ALLOWED_AMOUNT_25_2_STR));
		}
		
		 if (!(errorCode = Validator.checkNumber(form.getDrawingPowerAsPerLeadBank(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2new, 3, locale)).equals(Validator.ERROR_NONE)) {
				String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
				if (errorMessage.equals("error.number.decimalexceeded")) {
					errorMessage = "error.number.moredecimalexceeded";
				}
				errors.add("drawingPowerAsPerLeadBankError", new ActionMessage(errorMessage, "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2new, "2"));
			}

		/*if (!ERROR_NONE.equals(errorCode = Validator.checkNumber(form.getBankSharePercentage(), false,
						0, MAXIMUM_ALLOWED_VALUE_3_2))) {
			errors.add("bankSharePercentageError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), 0, 100.00));
		}*/

		
		/* if(StringUtils.isNotBlank(form.getBankSharePercentage()) &&
				!(errorCode = Validator.checkNumber(form.getBankSharePercentage(), false, 0, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_3_2)).equals(Validator.ERROR_NONE)){
			errors.add("bankSharePercentageError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_2_STR));
		}
		 */
		 
		  if (!(errorCode = Validator.checkNumber(form.getBankSharePercentage(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_2_2, 3, locale)).equals(Validator.ERROR_NONE)) {
				String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
				if (errorMessage.equals("error.number.decimalexceeded")) {
					errorMessage = "error.number.moredecimalexceeded";
				}
				errors.add("bankSharePercentageError", new ActionMessage(errorMessage, "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_2_2, "2"));
			}
		  
		  
		/*if (!ERROR_NONE.equals(errorCode = NumberValidator.checkNumber(form.getStockAmount(), false,
				BigDecimal.ZERO, MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL, 3, locale))) {
			errors.add("stockAmountError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), 0, MAXIMUM_ALLOWED_AMOUNT_25_2_STR));
		}*/
		
		  if (!(errorCode = Validator.checkNumber(form.getStockAmount(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2new, 3, locale)).equals(Validator.ERROR_NONE)) {
				String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
				if (errorMessage.equals("error.number.decimalexceeded")) {
					errorMessage = "error.number.moredecimalexceeded";
				}
				errors.add("stockAmountError", new ActionMessage(errorMessage, "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2new, "2"));
			}

	/*	if (!ERROR_NONE.equals(errorCode = NumberValidator.checkNumber(form.getDebtorAmount(), false,
				BigDecimal.ZERO, MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL, 3, locale))) {
			errors.add("debtorAmountError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0, MAXIMUM_ALLOWED_AMOUNT_25_2_STR));
		}*/
		
		 if (!(errorCode = Validator.checkNumber(form.getDebtorAmount(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2new, 3, locale)).equals(Validator.ERROR_NONE)) {
				String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
				if (errorMessage.equals("error.number.decimalexceeded")) {
					errorMessage = "error.number.moredecimalexceeded";
				}
				errors.add("debtorAmountError", new ActionMessage(errorMessage, "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2new, "2"));
			}

		/*if (!ERROR_NONE.equals(errorCode = NumberValidator.checkNumber(form.getCreditorsAmount(), false,
				BigDecimal.ZERO, MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL, 3, locale))) {
			errors.add("creditorsAmountError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0, MAXIMUM_ALLOWED_AMOUNT_25_2_STR));
		}*/
		 if (!(errorCode = Validator.checkNumber(form.getCreditorsAmount(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2new, 3, locale)).equals(Validator.ERROR_NONE)) {
				String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
				if (errorMessage.equals("error.number.decimalexceeded")) {
					errorMessage = "error.number.moredecimalexceeded";
				}
				errors.add("creditorsAmountError", new ActionMessage(errorMessage, "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2new, "2"));
			}
		 
		/*if (!ERROR_NONE.equals(errorCode = Validator.checkNumber(form.getMarginOnStock(), false,
						0, MAXIMUM_ALLOWED_VALUE_3_2,3,locale))) {
			errors.add("marginOnStockError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0, MAXIMUM_ALLOWED_VALUE_3_2_STR));
		}
		*/
		
		 if (!(errorCode = Validator.checkNumber(form.getMarginOnStock(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_2_2, 3, locale)).equals(Validator.ERROR_NONE)) {
				String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
				if (errorMessage.equals("error.number.decimalexceeded")) {
					errorMessage = "error.number.moredecimalexceeded";
				}
				errors.add("marginOnStockError", new ActionMessage(errorMessage, "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_2_2, "2"));
			}
		
		/*if (!ERROR_NONE.equals(errorCode = Validator.checkNumber(form.getMarginOnDebtor(), false,
						0, MAXIMUM_ALLOWED_VALUE_3_2,3,locale))) {
			errors.add("marginOnDebtorError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0, MAXIMUM_ALLOWED_VALUE_3_2_STR));
		}*/

		 if (!(errorCode = Validator.checkNumber(form.getMarginOnDebtor(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_2_2, 3, locale)).equals(Validator.ERROR_NONE)) {
				String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
				if (errorMessage.equals("error.number.decimalexceeded")) {
					errorMessage = "error.number.moredecimalexceeded";
				}
				errors.add("marginOnDebtorError", new ActionMessage(errorMessage, "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_2_2, "2"));
			}
		
		 
		/*if (!ERROR_NONE.equals(errorCode = Validator.checkNumber(form.getMarginOnCreditors(), 
				false, 0, MAXIMUM_ALLOWED_VALUE_3_2,3,locale))) {
			errors.add("marginOnCreditorsError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0, MAXIMUM_ALLOWED_VALUE_3_2_STR));
		}*/
		
		 if (!(errorCode = Validator.checkNumber(form.getMarginOnCreditors(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_2_2, 3, locale)).equals(Validator.ERROR_NONE)) {
				String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
				if (errorMessage.equals("error.number.decimalexceeded")) {
					errorMessage = "error.number.moredecimalexceeded";
				}
				errors.add("marginOnCreditorsError", new ActionMessage(errorMessage, "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_2_2, "2"));
			}

		return errors;
	}

}
