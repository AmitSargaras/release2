//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.others;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;


import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.CollateralValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class OthersValidator {

	public static final double COLLATERAL_MAX_LAND_AREA = Double
			.parseDouble("999999999999999");

	public static final double MAX_NUMBER = Double
			.parseDouble("999999999999999");

	public static ActionErrors validateInput(OthersForm aForm, Locale locale) {
		ActionErrors errors = CollateralValidator.validateInput(aForm, locale);
		String maximumAmt = new BigDecimal(
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		String errorCode = null;

		boolean isMandatory = false;
		if ("submit".equals(aForm.getEvent())) {
			isMandatory = true;
		}

		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals(
				"reject"))) {
			if (!(errorCode = Validator.checkString(aForm.getSecEnvRisky(),
					false, 0, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("secEnvRisky", new ActionMessage(ErrorKeyMapper.map(
						ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
				DefaultLogger.debug("Others Validator",
						"-------------- >>>>>>> Error at secEnvRisky");
			}
			Date dt =new Date();
			DateFormat dtval=new SimpleDateFormat("dd/MM/yyyy");
			if (aForm.getDateSecurityEnv().trim().length() > 0) {
							
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDateSecurityEnv())).after(
						DateUtil.clearTime(DateUtil.convertDate(locale, dtval.format(dt))))) {
						errors.add("dateSecurityEnvError", new ActionMessage("error.date.compareDate.cannotBeEarlier", "Date Security Confirmed as Environmentally Risky ",
							"Today Date"));
					}
			}
			
			
			
			

			if (!(errorCode = Validator.checkString(aForm.getRemarkEnvRisk(),
					false, 0, 250)).equals(Validator.ERROR_NONE)) {
				errors.add("remarkEnvRisk", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.STRING, errorCode), "0", 250 + ""));
				DefaultLogger.debug("Others Validator",
						"-------------- >>>>>>> Error at remarkEnvRisk");
			}

			if (!(errorCode = Validator.checkString(aForm.getDescription(),
					false, 0, 250)).equals(Validator.ERROR_NONE)) {
				errors.add("description", new ActionMessage(ErrorKeyMapper.map(
						ErrorKeyMapper.STRING, errorCode), "0", 250 + ""));
				DefaultLogger.debug("Others Validator",
						"-------------- >>>>>>> Error at description");
			}

			if (!(errorCode = Validator.checkDate(aForm.getDateSecurityEnv(),
					false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("dateSecurityEnv", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
				DefaultLogger.debug("OthersValidator",
						"-------------- >>>>>>> Error at dateSecurityEnv");
			}

			//Added by Pramod Katkar for New Filed CR on 8-08-2013
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
			// }

			if (!(errorCode = Validator.checkAmount(aForm.getMinimalValue(),
					false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
					IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("minimalValue",
						new ActionMessage(ErrorKeyMapper.map(
								ErrorKeyMapper.AMOUNT, errorCode), "0",
								maximumAmt));
				DefaultLogger.debug("OthersValidator",
						"-------------- >>>>>>> Error at minimalValue");
			}

			OthersValidationHelper.validateInput(aForm, locale, errors);
		}
		return errors;

	}
}
