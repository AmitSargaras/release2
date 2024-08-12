//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetrecgenagent;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class AssetRecGenAgentValidationHelper {

	private static String LOGOBJ = AssetRecGenAgentValidationHelper.class.getName();

	public static ActionErrors validateInput(AssetRecGenAgentForm aForm, Locale locale, ActionErrors errors) {
		DefaultLogger.debug("AssetRecGenAgentValidationHelper", "inside validation helper... :" + aForm);
		String errorCode = null;
		final double MAX_NUMBER = Double.parseDouble("999999999999999");
		if (!(errorCode = Validator.checkString(aForm.getProceedRecToBank(), false, 0, 15))
				.equals(Validator.ERROR_NONE)) {
			errors.add("processAgentOp", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					15 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetgenagent.AssetgenagentValidationHelper",
					"aForm.getProceedRecToBank(): " + aForm.getProceedRecToBank());
		}
		if (aForm.getTypeOfInvoice().equals("General")) {
			if (!(errorCode = Validator.checkString(aForm.getAgentName(), true, 0, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("processAgentOp", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 50 + ""));
				DefaultLogger.error(
						"com.integrosys.cms.ui.collateral.assetbased.assetgenagent.AssetgenagentValidationHelper",
						"aForm.getAgentName(): " + aForm.getAgentName());
			}
		}
		else {
			if (!(errorCode = Validator.checkString(aForm.getAgentName(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("processAgentOp", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 50 + ""));
				DefaultLogger.error(
						"com.integrosys.cms.ui.collateral.assetbased.assetgenagent.AssetgenagentValidationHelper",
						"aForm.getAgentName(): " + aForm.getAgentName());
			}
		}
		if (!(errorCode = Validator.checkString(aForm.getProceedRecControlledAgent(), false, 0, 50))
				.equals(Validator.ERROR_NONE)) {
			errors.add("processAgentOp", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetgenagent.AssetgenagentValidationHelper",
					"aForm.getProceedRecControlledAgent(): " + aForm.getProceedRecControlledAgent());
		}
		if (aForm.getIsSSC().equals("false")) {
			if (!(errorCode = Validator.checkString(aForm.getChargeType(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
				errors.add("chargeType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						10 + ""));
			}
		}

		if (!(errorCode = Validator.checkDate(aForm.getCollateralMaturityDate(), true, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("collateralMaturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
					"0", 256 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getCollateralMaturityDate(): " + aForm.getCollateralMaturityDate());
		}
		return errors;

	}
}
