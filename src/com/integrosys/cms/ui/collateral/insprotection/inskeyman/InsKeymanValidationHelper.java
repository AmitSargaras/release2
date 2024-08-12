/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insprotection/inskeyman/InsKeymanValidationHelper.java,v 1.2 2003/07/25 10:29:37 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.insprotection.inskeyman;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class InsKeymanValidationHelper {

	public static ActionErrors validateInput(InsKeymanForm aForm, Locale locale, ActionErrors errors) {
		String errorCode = null;
		final double MAX_NUMBER = Double.parseDouble("999999999999999");
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		String maximumAmt_7_str = IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_7_STR;
		final double MAX_NUMBER_7 = Double.parseDouble(maximumAmt_7_str);
		if (aForm.getEvent().equals("submit") || aForm.getEvent().equals("update")) {
			boolean isMandatory = false;
			if ("submit".equals(aForm.getEvent())) {
				isMandatory = true;
			}

			if (!(errorCode = Validator.checkString(aForm.getInsureName(), isMandatory, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("insureName", new ActionMessage("error.string.mandatory", "1", "50"));
				// DefaultLogger.debug(LOGOBJ, "getLe(): " + aForm.getLe());
			}
			
			/*Govind S: Commented Insurence(Life Insurence) security 06/07/2011
			/*if (!(errorCode = Validator.checkString(aForm.getInsurType(), isMandatory, 1, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("insurType", new ActionMessage("error.string.mandatory", "1", "10"));
				// DefaultLogger.debug(LOGOBJ, "getLe(): " + aForm.getLe());
			}*/

			/*if (!(errorCode = Validator.checkString(aForm.getInsuredAmt(), true, 1, 17)).equals(Validator.ERROR_NONE)) {
				errors.add("insuredAmt", new ActionMessage("error.string.mandatory", "1", "17"));
				// DefaultLogger.debug(LOGOBJ, "getLe(): " + aForm.getLe());
			}
			if (!(errorCode = Validator.checkAmount(aForm.getInsuredAmt(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_11_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("insuredAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_11_2_STR));
				// DefaultLogger.debug(LOGOBJ, "aForm.getOlv(): " +
				// aForm.getOlv());
			}*/
			
			String errorMessageDecimalExceeded = "error.number.decimalexceeded";
			/*if (!(errorCode = Validator.checkNumber(aForm.getInsuredAmt(), false, 1, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2, 3, locale))
					.equals(Validator.ERROR_NONE)) {
				String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
				if(errorMessage.equals(errorMessageDecimalExceeded)){
					errorMessage = "error.number.moredecimalexceeded";
				}
				errors.add("insuredAmt", new ActionMessage(errorMessage,"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR,"2"));
			}*/
			if(aForm.getEvent() != null && !(aForm.getEvent().equals("")) && aForm.getEvent().equals("submit")){
				if (!(errorCode = Validator.checkString(aForm.getAmountCMV(), true, 0, 50))
						.equals(Validator.ERROR_NONE)) {
			errors.add("amountCMV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
				}
			if (!(errorCode = Validator.checkDate(aForm.getEffDateInsurance(), isMandatory, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("effDateInsurance", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"0", 256 + ""));
				// DefaultLogger.debug(LOGOBJ,
				// "aForm.getCollateralMaturityDate(): " +
				// aForm.getCollateralMaturityDate());
			}
			if ((aForm.getExpiryDateInsure() != null) && (aForm.getExpiryDateInsure().trim().length() != 0)){
				if(aForm.getEffDateInsurance()!=null && !"".equals(aForm.getEffDateInsurance())){
				if(DateUtil.clearTime(DateUtil.convertDate(locale,aForm.getExpiryDateInsure())).before(DateUtil.clearTime(DateUtil.convertDate(locale,aForm.getEffDateInsurance())))) {
			errors.add("expiryDateInsureError", new ActionMessage(
						"error.date.compareDate.cannotBeless", "Insurance Maturity Date", "Effective Date of Insurance"));
			}
				}
			}	
			
			//Added by Pramod Katkar for New Filed CR on 7-08-2013
			 if(aForm.getIsPhysInsp()==null || "".equals(aForm.getIsPhysInsp())){
				 errors.add("isPhysInsp", new ActionMessage("error.string.mandatory"));
			 }
			 if(aForm.getIsPhysInsp()!=null || !"".equals(aForm.getIsPhysInsp())){
				 if("true".equalsIgnoreCase(aForm.getIsPhysInsp())){
						boolean containsError = false;
						if(aForm.getPhysInspFreqUOM()==null || "".equals(aForm.getPhysInspFreqUOM())){
							errors.add("physInspFreqUOM", new ActionMessage("error.string.mandatory"));
						}
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
			
			
			

            //Andy Wong, 11 Feb 2009: remove insurance policy no mandatory check for Life Insurance
//			if (!(errorCode = Validator.checkString(aForm.getPolicyNo(), isMandatory, 1, 30)).equals(Validator.ERROR_NONE)) {
//				errors.add("policyNo", new ActionMessage("error.string.mandatory", "1", "30"));
//				// DefaultLogger.debug(LOGOBJ, "getLe(): " + aForm.getLe());
//			}

		}
		return errors;

	}
}
