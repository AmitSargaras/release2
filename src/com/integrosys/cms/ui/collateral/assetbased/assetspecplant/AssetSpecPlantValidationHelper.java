//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetspecplant;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerValidator;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class AssetSpecPlantValidationHelper {

	private static String LOGOBJ = AssetSpecPlantValidationHelper.class.getName();

	private static String YES = ICMSConstant.YES;

	public static ActionErrors validateInput(AssetSpecPlantForm aForm, Locale locale, ActionErrors errors) {
		String errorCode = null;
		final double MAX_NUMBER = Double.parseDouble("999999999999999");
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		String maximumAmt_7_str = IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_7_STR;
		final double MAX_NUMBER_7 = Double.parseDouble(maximumAmt_7_str);

		DefaultLogger.debug(LOGOBJ, "Inside the AssetSpecPlantValidationHelper ");

		boolean isMandatory = ICommonEventConstant.EVENT_SUBMIT.equals(aForm.getEvent());

		// Validation for mandotory

		if (!(errorCode = Validator.checkString(aForm.getRegistrationCardNo(), false, 1, 20))
				.equals(Validator.ERROR_NONE)) {
			errors.add("registrationCardNo", new ActionMessage("error.string.mandatory", "1", "20"));
			DefaultLogger.debug(LOGOBJ, "getRegistrationCardNo(): " + aForm.getRegistrationCardNo());
		}

		/*
		 * if (!(errorCode = Validator.checkString(aForm.getOlv(), true, 1,
		 * 20)).equals(Validator.ERROR_NONE)) { errors.add("olv", new
		 * ActionMessage("error.string.mandatory", "1", "20"));
		 * DefaultLogger.debug(LOGOBJ, "getOlv(): " + aForm.getOlv()); }
		 */

		/*if (!(errorCode = Validator.checkString(aForm.getBrand(), isMandatory, 1, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("brand", new ActionMessage("error.string.mandatory", "1", "20"));
			DefaultLogger.debug(LOGOBJ, "getBrand(): " + aForm.getBrand());
		}*/
		/*if (!(errorCode = Validator.checkString(aForm.getAssetType(), isMandatory, 1, 10)).equals(Validator.ERROR_NONE)) {
			errors.add("assetType", new ActionMessage("error.string.mandatory", "1", "10"));
			DefaultLogger.debug(LOGOBJ, "getAssetType(): " + aForm.getAssetType());
		}*/
		/*if (!(errorCode = Validator.checkString(aForm.getEquipmf(), isMandatory, 1, 10)).equals(Validator.ERROR_NONE)) {
			errors.add("equipmf", new ActionMessage("error.string.mandatory", "1", "10"));
			DefaultLogger.debug(LOGOBJ, "getEquipmf(): " + aForm.getEquipmf());
		}*/
		/*if (!(errorCode = Validator.checkString(aForm.getEquipriskgrading(), isMandatory, 1, 10))
				.equals(Validator.ERROR_NONE)) {
			errors.add("equipriskgrading", new ActionMessage("error.string.mandatory", "1", "10"));
			DefaultLogger.debug(LOGOBJ, "getEquipriskgrading(): " + aForm.getEquipriskgrading());
		}*/
		/*if (!(errorCode = Validator.checkString(aForm.getEquipcode(), isMandatory, 1, 10)).equals(Validator.ERROR_NONE)) {
			errors.add("equipcode", new ActionMessage("error.string.mandatory", "1", "10"));
			DefaultLogger.debug(LOGOBJ, "getEquipcode(): " + aForm.getEquipcode());
		}*/

		if (!(errorCode = Validator.checkAmount(aForm.getOlv(), false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
				IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("olv", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0", maximumAmt));
			DefaultLogger.debug(LOGOBJ, "aForm.getOlv(): " + aForm.getOlv());
		}

		/*Calendar currDate = DateUtil.getCalendar();

		if (!(errorCode = Validator.checkInteger(aForm.getYearOfManufacture(), isMandatory, 1000, 9999))
				.equals(Validator.ERROR_NONE)) {
			errors.add("yearOfManufacture", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode),
					"1000", 9999 + ""));
			DefaultLogger.error(LOGOBJ, "aForm.getYearOfManufacture(): " + aForm.getYearOfManufacture());
		}
		else if ((aForm.getYearOfManufacture() != null) && !aForm.getYearOfManufacture().equals("")) {

			int manufactureYear = Integer.parseInt(aForm.getYearOfManufacture());
			int currYear = currDate.get(Calendar.YEAR);

			if (manufactureYear > currYear) {
				errors.add("yearOfManufacture", new ActionMessage("error.date.compareDate.more", "Year of Manufacture",
						"current year"));
				DefaultLogger.debug(LOGOBJ, "aForm.getYearOfManufacture() = " + aForm.getYearOfManufacture());
			}
		}*/

		boolean isNew = false;
		int minPurchasePrice = 0;

		if (!(errorCode = Validator.checkString(aForm.getGoodStatus(), false, 0, 10))
				.equals(Validator.ERROR_NONE)) {
			errors.add("goodStatus", new ActionMessage("error.string.mandatory", "1", "10"));
			DefaultLogger.error(LOGOBJ, "getGoodStatus(): " + aForm.getGoodStatus());
		}
		else {
			isNew = ICMSConstant.GOOD_STATUS_NEW.equals(aForm.getGoodStatus()) ? true : false;
			minPurchasePrice = ICMSConstant.GOOD_STATUS_NEW.equals(aForm.getGoodStatus()) ? 1 : 0;
		}

		/*
		 * if (!(errorCode = Validator.checkAmount(aForm.getPurchasePrice(),
		 * isNew, minPurchasePrice, MAX_NUMBER,
		 * IGlobalConstant.DEFAULT_CURRENCY,
		 * locale)).equals(Validator.ERROR_NONE)) { errors.add("purchasePrice",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
		 * errorCode), String .valueOf(minPurchasePrice), MAX_NUMBER + ""));
		 * DefaultLogger.debug(LOGOBJ, "aForm.getPurchasePrice(): " +
		 * aForm.getPurchasePrice()); }
		 */
		String errorMessageDecimalExceeded = "error.number.decimalexceeded";
		if (!(errorCode = Validator.checkNumber(aForm.getPurchasePrice(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
			if (errorMessage.equals(errorMessageDecimalExceeded)) {
				errorMessage = "error.number.moredecimalexceeded";
			}
			errors.add("purchasePrice", new ActionMessage(errorMessage, "1",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2_STR, "2"));
		}
		
		/*if (!(errorCode = Validator.checkNumber(aForm.getAmountCMV(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
			if (errorMessage.equals(errorMessageDecimalExceeded)) {
				errorMessage = "error.number.moredecimalexceeded";
			}
			errors.add("amountCMV", new ActionMessage(errorMessage, "1",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2_STR, "2"));
		}*/
		/*
		 * not required if (!(errorCode =
		 * Validator.checkString(aForm.getPurchasePrice(), true, 1,
		 * 13)).equals(Validator.ERROR_NONE)) { errors.add("purchasePrice", new
		 * ActionMessage("error.string.mandatory", "1", "13")); }
		 */
		/*if (!(errorCode = Validator.checkString(aForm.getModelNo(), isMandatory, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors
					.add("modelNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							50 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getModelNo(): " + aForm.getModelNo());
		}*/

		/*if (!(errorCode = Validator.checkString(aForm.getSecEnvRisky(), isMandatory, 0, 50))
				.equals(Validator.ERROR_NONE)) {
			errors.add("secEnvRisky", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getSecEnvRisky(): " + aForm.getSecEnvRisky());
		}*/

		/*
		 * if (!(errorCode = Validator.checkInteger(aForm.getDocPerfectAge(),
		 * true, 0,
		 * IGlobalConstant.MAXIMUM_ALLOWED_INTEGER)).equals(Validator.ERROR_NONE
		 * )) { errors.add("docPerfectAge", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode),
		 * "0", IGlobalConstant.MAXIMUM_ALLOWED_INTEGER + ""));
		 * DefaultLogger.debug(LOGOBJ, "getDocPerfectAge(): " +
		 * aForm.getDocPerfectAge()); }
		 */

		// for 2nd column
		if (!(errorCode = Validator.checkDate(aForm.getDatePurchase(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("datePurchase", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
			DefaultLogger.debug(LOGOBJ, "getDatePurchase(): " + aForm.getDatePurchase());
		}

		// Other validation

		if (!(errorCode = Validator.checkDate(aForm.getNextPhysInspDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("nextPhysInspDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					250 + ""));
			DefaultLogger.debug(LOGOBJ, "getNextPhysInspDate(): " + aForm.getNextPhysInspDate());
		}

		if (!(errorCode = Validator.checkDate(aForm.getRegistrationDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("registrationDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getRegistrationDate(): " + aForm.getRegistrationDate());
		}

		// Amount Validation
		/*
		 * if (!(errorCode = Validator.checkAmount(aForm.getOlv(), false, 0,
		 * IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
		 * IGlobalConstant.DEFAULT_CURRENCY,
		 * locale)).equals(Validator.ERROR_NONE)){ errors.add("olv", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
		 * "0", maximumAmt)); DefaultLogger.debug(LOGOBJ, "aForm.getOlv(): " +
		 * aForm.getOlv()); }
		 */

		/*
		 * if (!(errorCode = Validator.checkAmount(aForm.getRegistrationFee(),
		 * false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
		 * IGlobalConstant.DEFAULT_CURRENCY, locale))
		 * .equals(Validator.ERROR_NONE)) { errors.add("registrationFee", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
		 * "0", "99999999999999999.99")); DefaultLogger.debug(LOGOBJ,
		 * "aForm.getRegistrationFee(): " + aForm.getRegistrationFee()); }
		 */

		if (!(errorCode = Validator.checkNumber(aForm.getRegistrationFee(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_17_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
			if (errorMessage.equals(errorMessageDecimalExceeded)) {
				errorMessage = "error.number.moredecimalexceeded";
			}
			errors.add("registrationFee", new ActionMessage(errorMessage, "0",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_17_2_STR, "2"));
		}

		if (!(errorCode = Validator.checkNumber(aForm.getScrapValue(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_17_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
			if (errorMessage.equals(errorMessageDecimalExceeded)) {
				errorMessage = "error.number.moredecimalexceeded";
			}
			errors.add("scrapValue", new ActionMessage(errorMessage, "0",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_17_2_STR, "2"));
		}

		if (!(errorCode = Validator.checkNumber(aForm.getDptradein(), false, 1,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
			if (errorMessage.equals(errorMessageDecimalExceeded)) {
				errorMessage = "error.number.moredecimalexceeded";
			}
			errors.add("dptradein", new ActionMessage(errorMessage, "0",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2_STR, "2"));
		}
		 if (aForm.getRamId() == null
					|| "".equals(aForm.getRamId().trim())) {
				/*errors.add("ramIdError", new ActionMessage(
						"error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class,
						"ramIdError");*/
			}else{
				boolean nameFlag = ASSTValidator.isValidANDName(aForm.getRamId());
				if( nameFlag == true)
					errors.add("ramIdError", new ActionMessage("error.string.invalidCharacter"));
			   } 
		 if (aForm.getCollateralMaturityDate()== null
					|| "".equals(aForm.getCollateralMaturityDate().trim())) {
				/*errors.add("collateralMaturityDate", new ActionMessage(
						"error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class,
						"collateralMaturityDate");*/
			}else{ 
				
				if (StringUtils.isNotBlank(aForm.getCollateralMaturityDate())) {
			      
				Date tempDate = DateUtil.convertDate(locale, aForm.getCollateralMaturityDate());
				if (tempDate.before(new Date())) {
					errors.add("collateralMaturityDate1", new ActionMessage("error.date.compareDate.cannotBelater", "Maturity Date",
							"Today Date"));
				}
			}
		 
			if ((aForm.getCollateralMaturityDate() != null) && (aForm.getCollateralMaturityDate().trim().length() != 0)){
				if(aForm.getInvoiceDate()!=null && !"".equals(aForm.getInvoiceDate())){
				if(DateUtil.clearTime(DateUtil.convertDate(locale,aForm.getInvoiceDate())).after(DateUtil.clearTime(DateUtil.convertDate(locale,aForm.getCollateralMaturityDate())))) {
			errors.add("collateralMaturityDate2", new ActionMessage(
					"error.date.compareDate.greater", "Start Date", "Maturity Date"));
			   }
			  }
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
		 * "0", maximumAmt_7_str)); } DefaultLogger.debug(LOGOBJ,
		 * "aForm.getDepreciateRate(): " + aForm.getDepreciateRate()); }
		 */

		if (!(errorCode = Validator.checkNumber(aForm.getSalesProceed(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_17_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
			if (errorMessage.equals(errorMessageDecimalExceeded)) {
				errorMessage = "error.number.moredecimalexceeded";
			}
			errors.add("salesProceed", new ActionMessage(errorMessage, "0",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_17_2_STR, "2"));
		}

		/*
		 * if (!(errorCode = Validator.checkAmount(aForm.getSalesProceed(),
		 * false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
		 * IGlobalConstant.DEFAULT_CURRENCY, locale))
		 * .equals(Validator.ERROR_NONE)) { errors.add("salesProceed", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
		 * "0", maximumAmt)); DefaultLogger.debug(
		 * "com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetSpecPlantValidationHelper"
		 * , "getSalesProceed(): " + aForm.getSalesProceed()); }
		 */

		/*
		 * if (!(errorCode = Validator.checkAmount(aForm.getScrapValue(), false,
		 * 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
		 * IGlobalConstant.DEFAULT_CURRENCY, locale))
		 * .equals(Validator.ERROR_NONE)) { errors.add("scrapValue", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
		 * "1", maximumAmt)); DefaultLogger.error(LOGOBJ, "getScrapValue(): " +
		 * aForm.getScrapValue()); }
		 */

		if (!(errorCode = Validator.checkAmount(aForm.getAssetValue(), false, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("assetValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
					maximumAmt));
			DefaultLogger.error(LOGOBJ, "getAssetValue(): " + aForm.getAssetValue());
		}
		
		if (!(errorCode = Validator.checkInteger(aForm.getRepossessionAge(), false, 0, 99))
				.equals(Validator.ERROR_NONE)) {
			errors.add("repossessionAge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					99 + ""));
			DefaultLogger.error(LOGOBJ, "getRepossessionAge(): " + aForm.getRepossessionAge());
		}
		
		if (!(errorCode = Validator.checkInteger(aForm.getDocPerfectAge(), false, 0, 99))
				.equals(Validator.ERROR_NONE)) {
			errors.add("docPerfectAge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					99 + ""));
			DefaultLogger.error(LOGOBJ, "getDocPerfectAge(): " + aForm.getDocPerfectAge());
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
					
//					if (!(errorCode = Validator.checkDate(aForm.getDatePhyInspec(), false, locale))
//							.equals(Validator.ERROR_NONE)) {
//						errors.add("datePhyInspec", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
//								256 + ""));
//					}
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

		if (!(errorCode = Validator.checkAmount(aForm.getQuantity(), false, 1, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
				IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("quantity", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
					maximumAmt));
			DefaultLogger.error(LOGOBJ, "aForm.getQuantity(): " + aForm.getQuantity());
		}

		System.out.println("amountCMV:aForm.getEvent()=====>"+aForm.getEvent());
		System.out.println("AmountCMV:aForm.getAmountCMV()=====>"+aForm.getAmountCMV());
		System.out.println("Validator.checkString(aForm.getAmountCMV(), true, 0, 50)=====>"+Validator.checkString(aForm.getAmountCMV(), true, 1, 50));
		if(aForm.getEvent() != null && !(aForm.getEvent().equals("")) && aForm.getEvent().equals("submit")){
			if (!(errorCode = Validator.checkString(aForm.getAmountCMV(), true, 0, 50))
							.equals(Validator.ERROR_NONE)) {
				System.out.println("errorCode====>"+errorCode);
				errors.add("amountCMV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						50 + ""));
			}
			}
		/*
		 * if (!(errorCode = Validator.checkAmount(aForm.getDptradein(), false,
		 * 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
		 * IGlobalConstant.DEFAULT_CURRENCY, locale))
		 * .equals(Validator.ERROR_NONE)) { errors.add("dptradein", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
		 * "0", maximumAmt)); DefaultLogger.error(LOGOBJ,
		 * "aForm.getDptradein(): " + aForm.getDptradein()); }
		 */

		/*
		 * if (!(errorCode = Validator.checkAmount(aForm.getDpcash(), false, 0,
		 * IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
		 * IGlobalConstant.DEFAULT_CURRENCY,
		 * locale)).equals(Validator.ERROR_NONE)) { errors.add("dpcash", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
		 * "0", maximumAmt)); DefaultLogger.error(LOGOBJ, "aForm.getDpcash(): "
		 * + aForm.getDpcash()); }
		 */

		if (!(errorCode = Validator.checkNumber(aForm.getDpcash(), false, 1,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
			if (errorMessage.equals(errorMessageDecimalExceeded)) {
				errorMessage = "error.number.moredecimalexceeded";
			}
			errors.add("dpcash", new ActionMessage(errorMessage, "1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2_STR,
					"2"));
		}

		/*boolean isDownPaymentTradeInMandatory = false;
		if (!(errors.get("dptradein").hasNext())) {
			isDownPaymentTradeInMandatory = !(AbstractCommonMapper.isEmptyOrNull(aForm.getDptradein()) || Double
					.parseDouble(mapModifiedStringToDefaultString(aForm.getDptradein())) == 0);
		}

		if (!(errorCode = Validator.checkNumber(aForm.getTradeinValue(), isDownPaymentTradeInMandatory, 1,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
			if (errorMessage.equals(errorMessageDecimalExceeded)) {
				errorMessage = "error.number.moredecimalexceeded";
			}
			errors.add("tradeinValue", new ActionMessage(errorMessage, "1",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2_STR, "2"));
		}*/

		/*
		 * if (!(errorCode = Validator.checkAmount(aForm.getTradeinValue(),
		 * isMandatory, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
		 * IGlobalConstant.DEFAULT_CURRENCY, locale))
		 * .equals(Validator.ERROR_NONE)) { errors.add("tradeinValue", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
		 * "0", maximumAmt)); DefaultLogger.error(LOGOBJ,
		 * "aForm.getTradeinValue(): " + aForm.getTradeinValue()); }
		 */

		/*if (!(errorCode = Validator.checkString(aForm.getPubTransport(), isMandatory, 1, 2))
				.equals(Validator.ERROR_NONE)) {
			errors.add("pubTransport",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "2"));
			DefaultLogger.debug(LOGOBJ, "aForm.getPubTransport(): " + aForm.getPubTransport());
		}*/

		/*if (!(errorCode = Validator.checkString(aForm.getRegistrationNo(), isMandatory, 1, 25))
				.equals(Validator.ERROR_NONE)) {
			errors.add("registrationNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					"25"));
			DefaultLogger.debug(LOGOBJ, "aForm.registrationNo(): " + aForm.getRegistrationNo());
		}*/

		if (!(errorCode = Validator.checkString(aForm.getInvoiceNumber(), false, 0, 15)).equals(Validator.ERROR_NONE)) {
			errors.add("invoiceNumber", new ActionMessage("error.string.heightlessthan", "0", "15"));
		}

		if (!(errorCode = Validator.checkString(aForm.getYard(), false, 0, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("yard", new ActionMessage("error.string.heightlessthan", "0", "20"));
		}

		if (!(errorCode = Validator.checkString(aForm.getRlSerialNumber(), false, 0, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("rlSerialNumber", new ActionMessage("error.string.heightlessthan", "0", "20"));
		}

		// Andy Wong, 19 Jan 2009: Sibs validation for date
		if (StringUtils.isNotBlank(aForm.getRegistrationDate())) {
			Date tempDate = DateUtil.convertDate(locale, aForm.getRegistrationDate());
			if (tempDate.after(new Date())) {
				errors.add("registrationDate", new ActionMessage("error.date.compareDate.greater", "Registration Date",
						"Today Date"));
			}
		}

		if (StringUtils.isNotBlank(aForm.getDatePurchase())) {
			Date tempDate = DateUtil.convertDate(locale, aForm.getDatePurchase());
			if (tempDate.after(new Date())) {
				errors.add("datePurchase", new ActionMessage("error.date.compareDate.greater", "Date of Purchase",
						"Today Date"));
			}
		}

		if (StringUtils.isNotBlank(aForm.getChattelSoldDate())) {
			Date tempDate = DateUtil.convertDate(locale, aForm.getChattelSoldDate());
			if (tempDate.after(new Date())) {
				errors.add("chattelSoldDate", new ActionMessage("error.date.compareDate.greater", "Date Chattel Sold",
						"Today Date"));
			}
		}

	/*	boolean isTradeInInfoMandatory = (StringUtils.isNotBlank(aForm.getTradeInDeposit()));

		if (!(errorCode = Validator.checkString(aForm.getTradeInMake(), isTradeInInfoMandatory, 1, 100))
				.equals(Validator.ERROR_NONE)) {
			errors.add("make", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", errorCode));
		}
		if (!(errorCode = Validator.checkString(aForm.getTradeInModel(), isTradeInInfoMandatory, 1, 50))
				.equals(Validator.ERROR_NONE)) {
			errors
					.add("model", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							errorCode));
		}
		if (!(errorCode = Validator.checkInteger(aForm.getTradeInYearOfManufacture(), isTradeInInfoMandatory, 1000,
				9999)).equals(Validator.ERROR_NONE)) {
			errors.add("tradeInYearOfManufacture", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER,
					errorCode), "1", errorCode));
		}
		if (!(errorCode = Validator.checkString(aForm.getTradeInRegistrationNo(), isTradeInInfoMandatory, 1, 25))
				.equals(Validator.ERROR_NONE)) {
			errors.add("tradeInRegistrationNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"1", errorCode));
		}
		if (!(errorCode = Validator.checkAmount(aForm.getTradeInValue(), isTradeInInfoMandatory, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("tradeInValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
					errorCode));
		}*/
		
		/*if (!(errorCode = Validator.checkNumber(aForm.getPlist(), isMandatory, 1, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, 3,
				locale)).equals(Validator.ERROR_NONE)) {
			errors.add("plist",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1", maximumAmt));
			DefaultLogger.error(LOGOBJ, "aForm.getPlist(): " + aForm.getPlist());
		}*/

		DefaultLogger.debug(LOGOBJ, "No of Erros = " + errors.size());
		return errors;

	}

	public static String mapModifiedStringToDefaultString(String amount) {
		String result = null;
		BigDecimal temp = UIUtil.mapStringToBigDecimal(amount);
		if (temp != null)
			result = temp.toString();
		return result;
	}
}
