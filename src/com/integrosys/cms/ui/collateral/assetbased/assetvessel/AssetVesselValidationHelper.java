package com.integrosys.cms.ui.collateral.assetbased.assetvessel;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: Jitendra Date: Jun 12, 2006 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class AssetVesselValidationHelper {

	private static String LOGOBJ = AssetVesselValidationHelper.class.getName();

	private static String YES = ICMSConstant.YES;

	public static ActionErrors validateInput(AssetVesselForm aForm, Locale locale, ActionErrors errors) {

		String errorCode = null;
		final double MAX_NUMBER = Double.parseDouble("999999999999999");
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		String maximumAmt_7_str = IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_7_STR;
		final double MAX_NUMBER_7 = Double.parseDouble(maximumAmt_7_str);

		boolean isMandatoryForSubmit = ICommonEventConstant.EVENT_SUBMIT.equals(aForm.getEvent());

		if (!(errorCode = Validator.checkString(aForm.getRegNo(), false, 0, 15)).equals(Validator.ERROR_NONE)) {
			errors.add("regNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 15 + ""));
			DefaultLogger.error(LOGOBJ, "aForm.getRegNo(): " + aForm.getRegNo());
		}
		if (!(errorCode = Validator.checkAmount(aForm.getTradeInDeposit(), false, 1,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("tradeInDeposit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
					maximumAmt));
			DefaultLogger.error(LOGOBJ, "aForm.getTradeInDeposit(): " + aForm.getTradeInDeposit());
		}
		if (!(errorCode = Validator.checkAmount(aForm.getTradeInValue(), false, 1,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("tradeInValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
					maximumAmt));
			DefaultLogger.error(LOGOBJ, "aForm.getTradeInValue(): " + aForm.getTradeInValue());
		}
		if (!(errorCode = Validator.checkAmount(aForm.getRegFee(), false, 1, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
				IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("regFee", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
					maximumAmt));
			DefaultLogger.error(LOGOBJ, "aForm.getRegFee(): " + aForm.getRegFee());
		}
		if (!(errorCode = Validator.checkDate(aForm.getRegDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("regDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
			DefaultLogger.error(LOGOBJ, "aForm.getRegDate(): " + aForm.getRegDate());
		}
		if (!(errorCode = Validator.checkString(aForm.getBrand(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("brand", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
			DefaultLogger.error(LOGOBJ, "aForm.getBrand(): " + aForm.getBrand());
		}
		if (!(errorCode = Validator.checkString(aForm.getModelNo(), isMandatoryForSubmit, 0, 50))
				.equals(Validator.ERROR_NONE)) {
			errors
					.add("modelNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							50 + ""));
			DefaultLogger.error(LOGOBJ, "aForm.getModelNo(): " + aForm.getModelNo());
		}

		if (!(errorCode = Validator.checkString(aForm.getAssetType(), isMandatoryForSubmit, 1, 10))
				.equals(Validator.ERROR_NONE)) {
			errors.add("assetType", new ActionMessage("error.string.mandatory", "1", "10"));
			DefaultLogger.debug(LOGOBJ, "getAssetType(): " + aForm.getAssetType());
		}

		if (!(errorCode = Validator.checkString(aForm.getVesselBuildYear(), isMandatoryForSubmit, 1, 10))
				.equals(Validator.ERROR_NONE)) {
			errors.add("vesselBuildYear", new ActionMessage("error.string.mandatory", "1", "10"));
			DefaultLogger.debug(LOGOBJ, "getVesselBuildYear(): " + aForm.getVesselBuildYear());
		}
		else {

			Calendar cal = new GregorianCalendar();
			int year = cal.get(Calendar.YEAR);
			if (aForm.getVesselBuildYear() != null && aForm.getVesselBuildYear().trim().length() > 0) {
				int year1 = Integer.parseInt(aForm.getVesselBuildYear());
	
				DefaultLogger.debug(LOGOBJ, "year: " + year);
				DefaultLogger.debug(LOGOBJ, "year1: " + year1);
	
				if (year1 > year) {
	
					errors.add("vesselBuildYear", new ActionMessage("error.date.currentyear.later", "Build Year"));
					DefaultLogger.debug(LOGOBJ, "getVesselBuildYear(): " + aForm.getVesselBuildYear());
	
				}
			}
		}

		if (aForm.getIsSSC().equals("false")) {
			if (aForm.getIsPhysInsp().equals("true")) {
				boolean validateError = false;
				if (!(errorCode = Validator.checkInteger(aForm.getPhysInspFreq(), isMandatoryForSubmit, 0, 99))
						.equals(Validator.ERROR_NONE)) {
					errors.add("physInspFreq", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							"0", 99 + ""));
					validateError = true;
				}
				if (!validateError
						&& !(errorCode = Validator.checkString(aForm.getPhysInspFreqUOM(), isMandatoryForSubmit, 0, 50))
								.equals(Validator.ERROR_NONE)) {
					errors.add("physInspFreq", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							"0", 50 + ""));
				}
				if (!(errorCode = Validator.checkDate(aForm.getDatePhyInspec(), isMandatoryForSubmit, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("datePhyInspec", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"0", 250 + ""));
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
		}

        //Andy Wong: bypass mandatory validation for next physical inspection date, derived field
//		if (!(errorCode = Validator.checkDate(aForm.getNextPhysInspDate(), isMandatoryForSubmit, locale))
//				.equals(Validator.ERROR_NONE)) {
//			errors.add("nextPhysInspDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
//					250 + ""));
//		}

		// amount validation

		// int validation

		if (!(errorCode = Validator.checkInteger(aForm.getVesselDocPerfectAge(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER)).equals(Validator.ERROR_NONE)) {
			errors.add("vesselDocPerfectAge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode),
					"0", "99999"));
			DefaultLogger.error(LOGOBJ, "getVesselDocPerfectAge(): " + aForm.getVesselDocPerfectAge());
		}

		if (aForm.getVesselCharterPeriodUnit().equals("0")) {
			aForm.setVesselCharterPeriodUnit("");
		}
		if (!(errorCode = Validator.checkInteger(aForm.getVesselCharterPeriod(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER)).equals(Validator.ERROR_NONE)) {
			errors.add("vesselCharterPeriod", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode),
					"0", "99999"));
			DefaultLogger.error(LOGOBJ, "getVesselCharterPeriod(): " + aForm.getVesselCharterPeriod());
		}
		else if (!AbstractCommonMapper.isEmptyOrNull((aForm.getVesselCharterPeriodUnit()))
				&& AbstractCommonMapper.isEmptyOrNull(aForm.getVesselCharterPeriodUnit())) {
			errors.add("vesselCharterPeriodUnit", new ActionMessage("error.mandatory"));
			DefaultLogger.error(LOGOBJ, "getVesselCharterPeriodUnit() is empty");
		}

		if (!(errorCode = Validator.checkNumber(aForm.getVesselCharterAmt(), false, 0, MAX_NUMBER))
				.equals(Validator.ERROR_NONE)) {
			errors.add("vesselCharterAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					MAX_NUMBER + ""));
			DefaultLogger.error(LOGOBJ, "getVesselCharterAmt(): " + aForm.getVesselCharterAmt());
		}

		if (!(errorCode = Validator.checkString(aForm.getPubTransport(), isMandatoryForSubmit, 1, 20))
				.equals(Validator.ERROR_NONE)) {
			errors.add("pubTransport", new ActionMessage("error.string.mandatory", "1", "20"));
			DefaultLogger.error(LOGOBJ, "getPubTransport(): " + aForm.getPubTransport());
		}

		if (!(errorCode = Validator.checkString(aForm.getSecEnvRisky(), isMandatoryForSubmit, 0, 50))
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

		if (!(errorCode = Validator.checkAmount(aForm.getPurchasePrice(), isMandatoryForSubmit, 1,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("purchasePrice", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
					maximumAmt));
			DefaultLogger.error(LOGOBJ, "aForm.getPurchasePrice(): " + aForm.getPurchasePrice());
		}

		return errors;

	}

}
