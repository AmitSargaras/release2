//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.base.techinfra.validation.ValidatorConstant.ERROR_NONE;
import static com.integrosys.base.uiinfra.common.ErrorKeyMapper.AMOUNT;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.DEFAULT_CURRENCY;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_STR;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2_STR;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.NumberValidator;
import com.integrosys.cms.ui.common.UIValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class AssetGenChargeStockDetailsValidator {

	public static ActionErrors validateInput(AssetGenChargeStockDetailsForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		if (AssetGenChargeStockDetailsAction.EVENT_CREATE_CURRENT_ASSET.equals(aForm.getEvent())||
			AssetGenChargeStockDetailsAction.EVENT_EDIT_CURRENT_ASSET.equals(aForm.getEvent())
			
			|| AssetGenChargeStockDetailsAction.EVENT_CREATE_VALUE_DEBTORS.equals(aForm.getEvent())
			|| AssetGenChargeStockDetailsAction.EVENT_EDIT_VALUE_DEBTORS.equals(aForm.getEvent())
			
			
			||AssetGenChargeStockDetailsAction.EVENT_EDIT_CURRENT_ASSET_INSURANCE.equals(aForm.getEvent())) {
			return validateCurrentAssets(aForm, errors,locale);			
		}else if(AssetGenChargeStockDetailsAction.EVENT_CREATE_CURRENT_LIABILITIES.equals(aForm.getEvent())||
				AssetGenChargeStockDetailsAction.EVENT_EDIT_CURRENT_LIABILITIES.equals(aForm.getEvent())
				
				|| AssetGenChargeStockDetailsAction.EVENT_CREATE_LESS_VALUE_ADVANCES.equals(aForm.getEvent())
				|| AssetGenChargeStockDetailsAction.EVENT_EDIT_LESS_VALUE_ADVANCES.equals(aForm.getEvent())
				
				){
			return validateCurrentLiabilities(aForm, errors,locale);			
		}
		else if(AssetGenChargeStockDetailsAction.EVENT_CREATE_STOCK_DETAIL.equals(aForm.getEvent()) || 
				AssetGenChargeStockDetailsAction.EVENT_PREPARE_CREATE_CURRENT_ASSET.equals(aForm.getEvent()) ||
				AssetGenChargeStockDetailsAction.EVENT_EDIT_PREPARE_CURRENT_ASSET.equals(aForm.getEvent()) || 
				AssetGenChargeStockDetailsAction.EVENT_PREPARE_CREATE_CURRENT_LIABILITIES.equals(aForm.getEvent()) ||
				AssetGenChargeStockDetailsAction.EVENT_VIEW_CURRENT_ASSET.equals(aForm.getEvent()) ||
				AssetGenChargeStockDetailsAction.EVENT_VIEW_CURRENT_LIABILITIES.equals(aForm.getEvent()) ||
				AssetGenChargeStockDetailsAction.EVENT_EDIT_PREPARE_CURRENT_LIABILITIES.equals(aForm.getEvent()) ||
				AssetGenChargeStockDetailsAction.EVENT_EDIT_STOCK_DETAILS.equals(aForm.getEvent()) ||
				AssetGenChargeStockDetailsAction.EVENT_FORWARD_CURRENT_ASSET_LIABILITY.equals(aForm.getEvent())) {
			return validateSecurityCoverage(aForm, errors,locale);
		}
		return errors;
	}

	private static ActionErrors validateSecurityCoverage(AssetGenChargeStockDetailsForm aForm, ActionErrors errors, Locale locale) {
		String errorCode = "";
		
		DefaultLogger.debug(AssetGenChargeStockDetailsValidator.class.getName(), "Inside validateSecurityCoverage");
		
		if (!ERROR_NONE.equals(errorCode = NumberValidator.checkNumber(aForm.getCoverageAmount(), false,
				BigDecimal.ZERO, MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL, 3, locale))) {
			errors.add("coverageAmountError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0, MAXIMUM_ALLOWED_AMOUNT_25_2_STR));
		}
		
		if (!ERROR_NONE.equals(errorCode = NumberValidator.checkNumber(aForm.getAdHocCoverageAmount(), false,
				BigDecimal.ZERO, MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL, 3, locale))) {
			errors.add("adHocCoverageAmountError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0, MAXIMUM_ALLOWED_AMOUNT_25_2_STR));
		}
		
		if (!ERROR_NONE.equals(errorCode = NumberValidator.checkNumber(aForm.getCoveragePercentage(), false,
				BigDecimal.ZERO, MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL, 3, locale))) {
			errors.add("coveragePercentageError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0, MAXIMUM_ALLOWED_AMOUNT_25_2_STR));
		}
		
		return errors;
	}

	private static ActionErrors validateCurrentLiabilities(
			AssetGenChargeStockDetailsForm form, ActionErrors errors, Locale locale) {
		String errorCode = "";
		
		DefaultLogger.debug("AssetGenChargeStockDetailsValidator ", "Inside AssetGenChargeStockDetailsValidator");
		
		if (!(errorCode = Validator.checkString(form.getComponents(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("componentsError",  new ActionMessage("label.please.select.option"));
		}
		if (!(errorCode = Validator.checkNumber(form.getAmount(), true, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18, 3,locale)).equals(Validator.ERROR_NONE)) {
			errors.add("componentAmountError",  new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					"999999999999999999.99" + ""));
		}
		if("VARIABLE".equals(form.getMarginType())){
			if (!(errorCode = Validator.checkNumber(form.getMargin(), true, 0, 100.00, 3,locale)).equals(Validator.ERROR_NONE)) {
				errors.add("marginError",  new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						"100.00" + ""));
			}
		}
		
		if (!ERROR_NONE.equals(errorCode = Validator.checkAmount(form.getCoverageAmount(), false, 0,
				MAXIMUM_ALLOWED_AMOUNT_18, DEFAULT_CURRENCY, locale))) {
			errors.add("coverageAmountError",
					new ActionMessage(ErrorKeyMapper.map(AMOUNT, errorCode), "0", MAXIMUM_ALLOWED_AMOUNT_18_STR));
		}
		
		if (!ERROR_NONE.equals(errorCode = Validator.checkAmount(form.getAdHocCoverageAmount(), false, 0,
				MAXIMUM_ALLOWED_AMOUNT_18, DEFAULT_CURRENCY, locale))) {
			errors.add("adhocCoverageAmountError",
					new ActionMessage(ErrorKeyMapper.map(AMOUNT, errorCode), "0", MAXIMUM_ALLOWED_AMOUNT_18_STR));
		}

		if (!ERROR_NONE.equals(errorCode = Validator.checkNumber(form.getCoveragePercentage(), false, 0, 100.00))) {
			errors.add("coveragePercentageError", new ActionMessage(ErrorKeyMapper.map(AMOUNT, errorCode), 0, 100.00));
		}
		
		return errors;	
	}

	private static ActionErrors validateCurrentAssets(
			AssetGenChargeStockDetailsForm form, ActionErrors errors, Locale locale) {
		String errorCode = "";
		
		DefaultLogger.debug("AssetGenChargeStockDetailsValidator ", "Inside AssetGenChargeStockDetailsValidator");
		
		if (!(errorCode = Validator.checkString(form.getComponents(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("componentsError",  new ActionMessage("label.please.select.option"));
		}
		if (!(errorCode = Validator.checkNumber(form.getAmount(), true, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18, 3,locale)).equals(Validator.ERROR_NONE)) {
			errors.add("componentAmountError",  new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					"999999999999999999.99" + ""));
		}
		if("VARIABLE".equals(form.getMarginType())){
			if (!(errorCode = Validator.checkNumber(form.getMargin(), true, 0, 100.00, 3,locale)).equals(Validator.ERROR_NONE)) {
				errors.add("marginError",  new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						"100.00" + ""));
			}
		}
		if("on".equals(form.getHasInsurance())){
			if (!(errorCode = Validator.checkString(form.getInsuranceCompanyName(), true, 1, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("insuranceCompanyNameError",  new ActionMessage("label.please.select.option"));
			}
			/*if (!(errorCode = Validator.checkString(form.getInsuranceCompanyCategory(), false, 1, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("insuranceCompanyCategoryError",  new ActionMessage("label.please.select.option"));
			}*/
			if (!(errorCode = Validator.checkString(form.getInsuranceDescription(), false, 1, 200)).equals(Validator.ERROR_NONE)) {
				errors.add("insuranceDescriptionError",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "200"));
			}
			if(form.getInsuranceDescription()!=null&& ! "".equals(form.getInsuranceDescription().trim())){
				if(ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getInsuranceDescription()))
				errors.add("insuranceDescriptionError", new ActionMessage("error.string.invalidCharacter"));
			}
			if (!(errorCode = Validator.checkNumber(form.getInsuredAmount(), true, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18, 3,locale)).equals(Validator.ERROR_NONE)) {
				errors.add("insuredAmountError",  new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						"999999999999999999.99" + ""));
			}
			if (!(errorCode = Validator.checkDate(form.getEffectiveDateOfInsurance(), true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("effectiveDateOfInsuranceError",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}
			if (!(errorCode = Validator.checkDate(form.getExpiryDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("expiryDateError",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}else{
				if (!UIValidator.isFutureDate(form.getExpiryDate(), locale)) {
					errors.add("expiryDateError", new ActionMessage("error.date.expirydate", "Expiry Date"));
				}else if(!(errorCode= UIValidator.compareDateLater(form.getExpiryDate(),form.getEffectiveDateOfInsurance(), locale)).equals(Validator.ERROR_NONE)){
					errors.add("expiryDateError", new ActionMessage("error.date.expirydate.greater.than.effectivedate", "Expiry Date","Effective Date Of Insurance"));
					
				}
			}
			
			if (!(errorCode = Validator.checkNumber(form.getTotalPolicyAmount(), false, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18, 3,locale)).equals(Validator.ERROR_NONE)) {
				errors.add("totalPolicyAmountError",  new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						"999999999999999999.99" + ""));
			}
			if (!(errorCode = Validator.checkNumber(form.getInsurancePremium(), false, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18, 3,locale)).equals(Validator.ERROR_NONE)) {
				errors.add("insurancePremiumError",  new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						"999999999999999999.99" + ""));
			}
			if (!(errorCode = Validator.checkString(form.getInsurancePolicyNo(), true, 1, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("insurancePolicyNoError",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "30"));
			}
			if (!(errorCode = Validator.checkString(form.getInsuranceCoverNote(), true, 1, 200)).equals(Validator.ERROR_NONE)) {
				errors.add("insuranceCoverNoteError",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "30"));
			}
		}
		
		
		return errors;
	}
}
