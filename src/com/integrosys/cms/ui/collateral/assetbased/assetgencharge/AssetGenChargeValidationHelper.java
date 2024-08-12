/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/AssetGenChargeValidationHelper.java,v 1.13 2005/03/10 06:52:46 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2005/03/10 06:52:46 $ Tag: $Name: $
 */

public class AssetGenChargeValidationHelper {

	private static String LOGOBJ = AssetGenChargeValidationHelper.class.getName();

	private static String YES = ICMSConstant.YES;

	public static ActionErrors validateInput(AssetGenChargeForm aForm, Locale locale, ActionErrors errors) {
		String errorCode = null;

		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		String maximumAmt_7_str = IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_7_STR;
		final double MAX_NUMBER_7 = Double.parseDouble(maximumAmt_7_str);

		boolean isMandatory = ICommonEventConstant.EVENT_SUBMIT.equals(aForm.getEvent());
		
		/*
		 * stock details are mandatory --- validation -----
		 * if(isMandatory){
			if("Y".equals(aForm.getIsStockDetailsAdded())){
				//flag is Y details are added  go ahead
			}else{
				//flag is N details are not added  add error
				errors.add("stockDetailsError",  new ActionMessage("stock.details.mandatory"));
			}
		}*/

		/*if (aForm.getFundedShare() != null) {
			if (!(errorCode = Validator.checkAmount(aForm.getFundedShare(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("fundedSharePctError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
				//DefaultLogger.debug(LOGOBJ, "aForm.getScrapValue() = " + aForm.getScrapValue());
			}
		}
		if (aForm.getCalculatedDP() != null) {
			if (!(errorCode = Validator.checkAmount(aForm.getCalculatedDP(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("calculatedDPError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
				//DefaultLogger.debug(LOGOBJ, "aForm.getScrapValue() = " + aForm.getScrapValue());
			}
		}*/
		
		
		if (aForm.getIsPhysInsp().equals("true")) {
			boolean validateError = false;
			if (!(errorCode = Validator.checkInteger(aForm.getPhysInspFreq(), true, 0, 99))
					.equals(Validator.ERROR_NONE)) {
				errors.add("physInspFreq", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						99 + ""));
				validateError = true;
			}
			if (!validateError
					&& !(errorCode = Validator.checkString(aForm.getPhysInspFreqUOM(), true, 0, 50))
							.equals(Validator.ERROR_NONE)) {
				errors.add("physInspFreq", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						50 + ""));
			}
			if (!(errorCode = Validator.checkDate(aForm.getDatePhyInspec(), true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("datePhyInspec", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
						250 + ""));
			}

		}
		else if (aForm.getIsPhysInsp().equals("false")) {
			if (!aForm.getPhysInspFreq().equals("") || !aForm.getPhysInspFreqUOM().equals("")) {
				errors.add("physInspFreq", new ActionMessage("error.string.empty"));
			}
			if (!aForm.getDatePhyInspec().equals("")) {
				errors.add("datePhyInspec", new ActionMessage("error.string.empty"));
			}
		}

		/*
		 * if (!(errorCode = Validator.checkAmount(aForm.getDepreciateRate(),
		 * true, 0, MAX_NUMBER_7, IGlobalConstant.DEFAULT_CURRENCY,
		 * locale)).equals(Validator.ERROR_NONE)) {
		 * if(errorCode.equals("lessthan")){ errorCode = "lessthan1";
		 * errors.add("depreciateRate", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
		 * "0", maximumAmt_7_str)); }else{ errors.add("depreciateRate", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
		 * "0", maximumAmt_7_str)); }DefaultLogger.error(
		 * "com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeValidationHelper"
		 * , "getDepreciateRate(): "+ aForm.getDepreciateRate()); }
		 */

		if (!(errorCode = Validator.checkDate(aForm.getCollateralMaturityDate(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("collateralMaturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
					"0", 256 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getCollateralMaturityDate(): " + aForm.getCollateralMaturityDate());
		}
		//Commented by Anil for HDFC CLIMS
		/*
		  if (!(errorCode = Validator.checkString(aForm.getSecEnvRisky(), isMandatory, 0, 50))
				.equals(Validator.ERROR_NONE)) {
			errors.add("secEnvRisky", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getSecEnvRisky(): " + aForm.getSecEnvRisky());
		}

		if ((null != aForm.getSecEnvRisky()) && YES.equals(aForm.getSecEnvRisky().trim())) {
			if (!(errorCode = Validator.checkDate(aForm.getDateSecurityEnv(), true, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("dateSecurityEnv", new ActionMessage("error.date.mandatory", "1", "256"));
				DefaultLogger.debug(LOGOBJ, " dateSecurityEnv is mandatory= " + aForm.getDateSecurityEnv());
			}
		}
		else {
			if (!(errorCode = Validator.checkDate(aForm.getDateSecurityEnv(), false, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("dateSecurityEnv", new ActionMessage("error.date.mandatory", "1", "256"));
				DefaultLogger.debug(LOGOBJ, " Not valid date, dateSecurityEnv() = " + aForm.getDateSecurityEnv());
			}
		}*/

		return errors;
	}
}
