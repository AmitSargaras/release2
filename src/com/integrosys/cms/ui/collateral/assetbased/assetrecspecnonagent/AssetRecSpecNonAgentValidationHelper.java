//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetrecspecnonagent;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class AssetRecSpecNonAgentValidationHelper {

	private static String LOGOBJ = AssetRecSpecNonAgentValidationHelper.class.getName();

	public static ActionErrors validateInput(AssetRecSpecNonAgentForm aForm, Locale locale, ActionErrors errors) {
		String errorCode = null;
		final double MAX_NUMBER = Double.parseDouble("999999999999999");
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		if (!(errorCode = Validator.checkString(aForm.getLocApprBuyer(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("locApprBuyer", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
			DefaultLogger.error(LOGOBJ, "aForm.getLocApprBuyer(): " + aForm.getLocApprBuyer());
		}

		if (!(errorCode = Validator.checkString(aForm.getProceedRecToBank(), false, 0, 20))
				.equals(Validator.ERROR_NONE)) {
			errors.add("processAgentOp", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					40 + ""));
			DefaultLogger.error(LOGOBJ, "aForm.getProceedRecToBank(): " + aForm.getProceedRecToBank());
		}

		if (!(errorCode = Validator.checkString(aForm.getChargeType(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
			errors.add("chargeType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					10 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getChargeType(): " + aForm.getChargeType());
		}

		if (!(errorCode = Validator.checkDate(aForm.getCollateralMaturityDate(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("collateralMaturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
					"0", 256 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getCollateralMaturityDate(): " + aForm.getCollateralMaturityDate());
		}

		if (!(errorCode = Validator.checkAmount(aForm.getAmtCharge(), true, 1,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("amtCharge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
					maximumAmt));
			DefaultLogger.debug(LOGOBJ, "aForm.getAmtCharge(): " + aForm.getAmtCharge());		}
		
		DefaultLogger.debug(LOGOBJ, "No  of Errors " + errors.size());

		return errors;

	}
}