//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.assetbased;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.CollateralValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class AssetBasedValidator {
	public static ActionErrors validateInput(AssetBasedForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		if(!"REST_UPDATE_AB_SA_SECURITY".equalsIgnoreCase(aForm.getEvent()))
		errors = CollateralValidator.validateInput(aForm, locale);

		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
		String errorCode = null;
		try {
			if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {
				if (!(errorCode = Validator.checkString(aForm.getRemarkEnvRisk(), false, 0, 250))
						.equals(Validator.ERROR_NONE)) {
					errors.add("remarkEnvRisk", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							"0", 250 + ""));
				}
				if (!(errorCode = Validator.checkDate(aForm.getDateSecurityEnv(), false, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("dateSecurityEnv", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"0", 250 + ""));
				}
				if (!(errorCode = Validator.checkAmount(aForm.getNominalValue(), false, 0.0,
						IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("nominalValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
							"0.0", maximumAmt));
				}
				if (!(errorCode = Validator.checkString(aForm.getDescription(), false, 0, 250))
						.equals(Validator.ERROR_NONE)) {
					errors.add("description", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							"0", 250 + ""));
				}
				AssetBasedValidationHelper.validateInput(aForm, locale, errors);
			}
			//Sachin P:Start Here 26/07/2011  check validation for asset value of collatral booking & residual scrap value
 			if (aForm.getScrapValue() != null) {
				if (!(errorCode = Validator.checkAmount(aForm.getScrapValue(), false, 0,
						IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("scrapValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
							"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
					//DefaultLogger.debug(LOGOBJ, "aForm.getScrapValue() = " + aForm.getScrapValue());
				}
			}
			if (aForm.getAssetValue() != null) {
				if (!(errorCode = Validator.checkAmount(aForm.getAssetValue(), false, 0,
						IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("assetValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
							"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
					//DefaultLogger.debug(LOGOBJ, "aForm.getScrapValue() = " + aForm.getScrapValue());
				}
			}
			//Sachin P:End Here 26/07/2011  check validation for asset value of collatral booking & residual scrap value
		}
		catch (Exception e) {
			DefaultLogger.error("", "error in assetbasedvalidator is" + e);
		}
		return errors;
	}
}
