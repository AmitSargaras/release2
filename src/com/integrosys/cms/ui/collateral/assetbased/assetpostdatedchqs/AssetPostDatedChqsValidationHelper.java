//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class AssetPostDatedChqsValidationHelper {

	private static String LOGOBJ = AssetPostDatedChqsValidationHelper.class.getName();

	public static ActionErrors validateInput(AssetPostDatedChqsForm aForm, Locale locale, ActionErrors errors) {
		final double MAX_NUMBER = Double.parseDouble("99.999999999");
		String maximumInterestRate = IGlobalConstant.MAXIMUM_ALLOWED_INTEREST_RATE;
		String maximumAmt = IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR;
		
		DefaultLogger.debug("Asset Post Dated Cheques Validation Helper", "inside validation helper... :" + aForm);
		String errorCode = null;
		
		boolean isMandatory = false;
		if ("submit".equals(aForm.getEvent())) {
			isMandatory = true;
		}
		
		/*if (!(errorCode = Validator.checkString(aForm.getChargeType(), isMandatory, 1, 10)).equals(Validator.ERROR_NONE)) {
			errors.add("chargeType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						10 + ""));
		}*/
			
		/*if (!(errorCode = Validator.checkAmount(aForm.getAmtCharge(), isMandatory, 1,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("amtCharge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
					maximumAmt));
			DefaultLogger.debug(LOGOBJ, "aForm.getAmtCharge(): " + aForm.getAmtCharge());		}*/
		
		/*if(isMandatory&&StringUtils.isBlank(aForm.getAmtCharge())){ 
			errors.add("amtCharge", new ActionMessage("error.mandatory"));
		}*/
			
		/*if (!(errorCode = Validator.checkNumber(aForm.getInterestRate(), isMandatory, 0, MAX_NUMBER, 9, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("interestRate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
					maximumInterestRate));
			DefaultLogger.debug(LOGOBJ, "aForm.getInterestRate(): " + aForm.getInterestRate());		
		}
		else if(StringUtils.isNotBlank(aForm.getInterestRate())){
			if(aForm.getInterestRate().indexOf(",")>=0){
				errors.add("interestRate", new ActionMessage("error.number.format"));
			}
		}*/
		if(aForm.getEvent() != null && !(aForm.getEvent().equals("")) && aForm.getEvent().equals("submit")){
		if (!(errorCode = Validator.checkString(aForm.getAmountCMV(), true, 0, 50))
				.equals(Validator.ERROR_NONE)) {
	errors.add("amountCMV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
			50 + ""));
}
		}
		return errors;
	}
}
