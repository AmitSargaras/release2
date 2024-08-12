//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetspecother;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class AssetSpecOtherValidationHelper {

	private static String LOGOBJ = AssetSpecOtherValidationHelper.class.getName();

	private static String YES = ICMSConstant.YES;

	public static ActionErrors validateInput(AssetSpecOtherForm aForm, Locale locale, ActionErrors errors) {
		String errorCode;

		boolean isMandatory = ICommonEventConstant.EVENT_SUBMIT.equals(aForm.getEvent());

		if (aForm.getIsSSC().equals("false")) {
			//Added by Pramod Katkar for New Filed CR on 20-08-2013
			 if(aForm.getIsPhysInsp()==null || "".equals(aForm.getIsPhysInsp())){
				 errors.add("isPhysInsp", new ActionMessage("error.string.mandatory"));
			}
			 if(aForm.getIsPhysInsp()!=null || !"".equals(aForm.getIsPhysInsp())){
				 if("true".equalsIgnoreCase(aForm.getIsPhysInsp())){
						boolean containsError = false;
						if(aForm.getPhysInspFreqUOM()==null || "".equals(aForm.getPhysInspFreqUOM())){
							errors.add("physInspFreqUOM", new ActionMessage("error.string.mandatory"));
				}
						
//						if (!(errorCode = Validator.checkDate(aForm.getDatePhyInspec(), false, locale))
//								.equals(Validator.ERROR_NONE)) {
//							errors.add("datePhyInspec", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
//									256 + ""));
						 if(aForm.getDatePhyInspec()==null || "".equals(aForm.getDatePhyInspec())){
							 errors.add("datePhyInspec", new ActionMessage("error.string.mandatory"));
				}
						 if(aForm.getNextPhysInspDate()==null || "".equals(aForm.getNextPhysInspDate())){
							 errors.add("nextPhysInspDateError", new ActionMessage("error.string.mandatory"));
				}
						 if(!"".equals(aForm.getDatePhyInspec()) && !"".equals(aForm.getNextPhysInspDate())){
							 if(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDatePhyInspec())).after(
										DateUtil.convertDate(locale, aForm.getNextPhysInspDate()))){
									errors.add("nextPhysInspDateError", new ActionMessage("error.date.compareDate", "This ","Physical Verification Done on"));
			}
				}
				}
			}
			 
			//End by Pramod Katkar
		}

		if (!(errorCode = Validator.checkDate(aForm.getNextPhysInspDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("nextPhysInspDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					250 + ""));
		}

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
		}
		if (!(errorCode = Validator.checkString(aForm.getAmountCMV(), true, 0, 50))
				.equals(Validator.ERROR_NONE)) {
	errors.add("amountCMV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
			50 + ""));
}

		/*if (AbstractCommonMapper.isEmptyOrNull(aForm.getAssetType()) && isMandatory) {
			errors.add("assetType", new ActionMessage("error.mandatory"));
			DefaultLogger.debug(LOGOBJ, "Others Asset Type is mandatory");
		}*/
		return errors;

	}
}
