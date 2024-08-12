package com.integrosys.cms.ui.collateral.assetbased.assetspecvehicles;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerValidator;

/**
 * Created by IntelliJ IDEA. User: Jitendra Date: Mar 25, 2007 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class AssetSpecVehiclesValidationHelper {

	private static String LOGOBJ = AssetSpecVehiclesValidationHelper.class.getName();

	private static String YES = ICMSConstant.YES;

	/** Available values for PBT / PBR indicator, ie, PBT and PBR */
	private static final String[] AVAILABLE_PBT_PBR_IND = new String[] { "PBR", "PBT" };

	public static ActionErrors validateInput(AssetSpecVehiclesForm aForm, Locale locale, ActionErrors errors) {
		String errorCode = null;
		final double MAX_NUMBER = Double.parseDouble("999999999999999");
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		// Validation for mandatory
		boolean mandatorySubmit = ICommonEventConstant.EVENT_SUBMIT.equals(aForm.getEvent());

		Calendar currDate = DateUtil.getCalendar();

		/*if (!(errorCode = Validator.checkInteger(aForm.getYearMfg(), mandatorySubmit, 1000, 9999))
				.equals(Validator.ERROR_NONE)) {
			errors.add("yearMfg", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), "1000",
					9999 + ""));
			DefaultLogger.error(LOGOBJ, "aForm.getYearMfg(): " + aForm.getYearMfg());
		}
		else if ((aForm.getYearMfg() != null) && !aForm.getYearMfg().equals("")) {

			int manufactureYear = Integer.parseInt(aForm.getYearMfg());
			int currYear = currDate.get(Calendar.YEAR);

			if (manufactureYear > currYear) {
				errors.add("yearMfg", new ActionMessage("error.date.compareDate.more", "Year of Manufacture",
						"current year"));
				DefaultLogger.debug(LOGOBJ, "aForm.getYearMfg() = " + aForm.getYearMfg());
			}
		}*/

		int minPurchasePrice = 1;
		/*if (!(errorCode = Validator.checkString(aForm.getGoodStatus(), mandatorySubmit, 1, 10))
				.equals(Validator.ERROR_NONE)) {
			errors.add("goodStatus", new ActionMessage("error.string.mandatory", "1", "10"));
			DefaultLogger.error(LOGOBJ, "getGoodStatus(): " + aForm.getGoodStatus());
		}
		else {
			minPurchasePrice = ICMSConstant.GOOD_STATUS_NEW.equals(aForm.getGoodStatus()) ? 1 : 0;
		}*/

		// Andy Wong, 14 Feb 2009: make purchase price mandatory for all types
		// of vehicle
		/*if (!(errorCode = Validator.checkAmount(aForm.getPurchasePrice(), mandatorySubmit, minPurchasePrice,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("purchasePrice", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), String
					.valueOf(minPurchasePrice), maximumAmt));
			DefaultLogger.error(LOGOBJ, "aForm.getPurchasePrice(): " + aForm.getPurchasePrice());
		}
*/
		if (aForm.getRoadTaxAmtType() != null)
			if (aForm.getRoadTaxAmtType().equals("yearly")) {
				if (!(errorCode = Validator.checkNumber(aForm.getYearlyRoadTaxAmt(), false, 1,
						IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, 3, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("yearlyRoadTaxAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
							errorCode), "1", maximumAmt));
					DefaultLogger.error(LOGOBJ, "aForm.getYearlyRoadTaxAmt(): " + aForm.getYearlyRoadTaxAmt());
				}
			}
			else if (aForm.getRoadTaxAmtType().equals("halfYearly")) {
				if (!(errorCode = Validator.checkNumber(aForm.getHalfYearlyRoadTaxAmt(), false, 1,
						IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, 3, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("halfYearlyRoadTaxAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
							errorCode), "1", maximumAmt));
					DefaultLogger.error(LOGOBJ, "aForm.getHalfYearlyRoadTaxAmt(): " + aForm.getHalfYearlyRoadTaxAmt());
				}
			}

		/*if (!(errorCode = Validator.checkString(aForm.getModelNo(), mandatorySubmit, 1, 50))
				.equals(Validator.ERROR_NONE)) {
			errors
					.add("modelNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							50 + ""));
			DefaultLogger.error(LOGOBJ, "aForm.getModelNo(): " + aForm.getModelNo());
		}*/

		if (!(errorCode = Validator.checkString(aForm.getChassisNumber(), false, 1, 25)).equals(Validator.ERROR_NONE)) {
			errors.add("chassisNumber", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					"25"));
			DefaultLogger.error(LOGOBJ, "getChassisNumber(): " + aForm.getChassisNumber());
		}else{
			boolean nameFlag = ASSTValidator.isValidANDName(aForm.getChassisNumber());
			if( nameFlag == true)
				errors.add("chassisNumber", new ActionMessage("error.string.invalidCharacter"));
		   }
		/*
		 * if (!(errorCode = Validator.checkDate(aForm.getRepossessionDate(),
		 * true, locale)).equals(Validator.ERROR_NONE)) {
		 * errors.add("repossessionDate", new
		 * ActionMessage("error.string.mandatory", "1", "20"));
		 * DefaultLogger.error(LOGOBJ, "repossessionDate(): "+
		 * aForm.getRepossessionDate()); }
		 */

		if (!(errorCode = Validator.checkDate(aForm.getCollateralMaturityDate(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("collateralMaturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
					"0", 256 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getCollateralMaturityDate(): " + aForm.getCollateralMaturityDate());
		}

		if (!(errorCode = Validator.checkString(aForm.getRegNo(), false, 0, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("regNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getRegNo(): " + aForm.getRegNo());
		}else{
			boolean nameFlag = ASSTValidator.isValidANDName(aForm.getRegNo());
			if( nameFlag == true)
				errors.add("regNo", new ActionMessage("error.string.invalidCharacter"));
		   }
		
		if (!(errorCode = Validator.checkString(aForm.getRegDate(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("regDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getRegDate(): " + aForm.getRegDate());
		}
		
		if (!(errorCode = Validator.checkString(aForm.getEngineNo(), false, 0, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("engineNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getEngineNo(): " + aForm.getRegDate());
		}else{
			boolean nameFlag = ASSTValidator.isValidANDName(aForm.getEngineNo());
			if( nameFlag == true)
				errors.add("engineNo", new ActionMessage("error.string.invalidCharacter"));
		   }
		if(aForm.getEvent() != null && !(aForm.getEvent().equals("")) && aForm.getEvent().equals("submit")){
		if (!(errorCode = Validator.checkString(aForm.getAmountCMV(), true, 0, 50))
						.equals(Validator.ERROR_NONE)) {
			errors.add("amountCMV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		}
		
		if (!(errorCode = Validator.checkString(aForm.getLogBookNumber(), false, 0, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("logBookNumber", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getLogBookNumber(): " + aForm.getLogBookNumber());
		}else{
			boolean nameFlag = ASSTValidator.isValidANDName(aForm.getLogBookNumber());
			if( nameFlag == true)
				errors.add("logBookNumber", new ActionMessage("error.string.invalidCharacter"));
		   }
		
		
		if(aForm.getPlist()!=null && !"".equals(aForm.getPlist())){
			
			 if (!(errorCode = Validator.checkNumber(aForm.getPlist(), false, 0, 999999999999999999.99, 3,
					locale)).equals(Validator.ERROR_NONE)) {
				errors.add("plistError", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", "999999999999999999.99"));
			   }
			}	
		
		
		/*if (aForm.getPlist()!=null && !"".equals(aForm.getPlist())){
			
			boolean nameFlag = ASSTValidator.isValidANDName(aForm.getPlist());
			if( nameFlag == true)
				errors.add("plistError", new ActionMessage("error.string.invalidCharacter"));
			}*/
		
		if(aForm.getPurchasePrice()!=null && !"".equals(aForm.getPurchasePrice())){
		
		 if (!(errorCode = Validator.checkNumber(aForm.getPurchasePrice(), false, 0, 999999999999999999.99, 3,
				locale)).equals(Validator.ERROR_NONE)) {
			errors.add("purchasePriceError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
					"0", "999999999999999999.99"));
		   }
		}
		
      /* if (aForm.getPurchasePrice()!=null && !"".equals(aForm.getPurchasePrice())){
			
			boolean nameFlag = ASSTValidator.isValidANDName(aForm.getPurchasePrice());
			if( nameFlag == true)
				errors.add("purchasePriceError", new ActionMessage("error.string.invalidCharacter"));
			}*/
       
       if (aForm.getVehColor()!=null && !"".equals(aForm.getVehColor())){
			
			boolean nameFlag = ASSTValidator.isValidANDName(aForm.getVehColor());
			if( nameFlag == true)
				errors.add("vehColorError", new ActionMessage("error.string.invalidCharacter"));
			}
       
       
       if (aForm.getPhysInspFreq()!=null && !"".equals(aForm.getPhysInspFreq())){
			
			boolean nameFlag = ASSTValidator.isValidANDName(aForm.getPhysInspFreq());
			if( nameFlag == true)
				errors.add("physInspFreqError", new ActionMessage("error.string.invalidCharacter"));
			}
		/*if (aForm.getInvoiceDate() == null || aForm.getInvoiceDate().equals("")) {
			errors.add("invoiceDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getInvoiceDate(): " + aForm.getRegDate());
		}*/
		
		

		/*if (!(errorCode = Validator.checkString(aForm.getBrand(), mandatorySubmit, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("brand", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getBrand(): " + aForm.getBrand());
		}*/

		/*if (!(errorCode = Validator.checkString(aForm.getSecEnvRisky(), mandatorySubmit, 0, 50))
				.equals(Validator.ERROR_NONE)) {
			errors.add("secEnvRisky", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getSecEnvRisky(): " + aForm.getSecEnvRisky());
		}*/

		// Validation for Asset Based Vehicles by Saritha

		/*
		 * if (!(errorCode = Validator.checkString(aForm.getIindicator(),
		 * mandatorySubmit, 1, 20)) .equals(Validator.ERROR_NONE)) {
		 * errors.add("iindicator", new ActionMessage("error.string.mandatory",
		 * "1", "20")); DefaultLogger.error(LOGOBJ, "getIindicator(): " +
		 * aForm.getIindicator()); }
		 */

		/*
		 * if (!(errorCode = Validator.checkString(aForm.getCollateralfee(),
		 * mandatorySubmit, 1, 20)) .equals(Validator.ERROR_NONE)) {
		 * errors.add("collateralfee", new
		 * ActionMessage("error.string.mandatory", "1", "20"));
		 * DefaultLogger.error(LOGOBJ, "getCollateralfee(): " +
		 * aForm.getCollateralfee()); }
		 */

		/*
		 * if (!(errorCode = Validator.checkString(aForm.getDptradein(),
		 * mandatorySubmit, 1, 20)) .equals(Validator.ERROR_NONE)) {
		 * errors.add("dptradein", new ActionMessage("error.string.mandatory",
		 * "1", "20")); DefaultLogger.error(LOGOBJ, "getDptradein(): " +
		 * aForm.getDptradein()); }
		 * 
		 * if (!(errorCode = Validator.checkString(aForm.getDpcash(),
		 * mandatorySubmit, 1, 20)) .equals(Validator.ERROR_NONE)) {
		 * errors.add("dpcash", new ActionMessage("error.string.mandatory", "1",
		 * "20")); DefaultLogger.error(LOGOBJ, "getDpcash(): " +
		 * aForm.getDpcash()); }
		 */

		/*
		 * if (!(errorCode = Validator.checkString(aForm.getFcharges(),
		 * mandatorySubmit, 1, 20)) .equals(Validator.ERROR_NONE)) {
		 * errors.add("fcharges", new ActionMessage("error.string.mandatory",
		 * "1", "20")); DefaultLogger.error(LOGOBJ, "getFcharges(): " +
		 * aForm.getFcharges()); }
		 */

		/*
		 * if (!(errorCode = Validator.checkString(aForm.getPlist(),
		 * mandatorySubmit, 1, 20)).equals(Validator.ERROR_NONE)) {
		 * errors.add("plist", new ActionMessage("error.string.mandatory", "1",
		 * "20")); DefaultLogger.error(LOGOBJ, "getPlist(): " +
		 * aForm.getPlist()); }
		 */

	/*	if (!(errorCode = Validator.checkString(aForm.getPubTransport(), mandatorySubmit, 1, 20))
				.equals(Validator.ERROR_NONE)) {
			errors.add("pubTransport", new ActionMessage("error.string.mandatory", "1", "20"));
			DefaultLogger.error(LOGOBJ, "getPubTransport(): " + aForm.getPubTransport());
		}*/
		// for 2nd column

		/*if ((null != aForm.getSecEnvRisky()) && YES.equals(aForm.getSecEnvRisky().trim())) {
			if (!(errorCode = Validator.checkDate(aForm.getDateSecurityEnv(), mandatorySubmit, locale))
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

		if (!(errorCode = Validator.checkString(aForm.getAssetType(), mandatorySubmit, 1, 20))
				.equals(Validator.ERROR_NONE)) {
			errors.add("assetType", new ActionMessage("error.string.mandatory", "1", "20"));
			DefaultLogger.error(LOGOBJ, "getAssetType(): " + aForm.getAssetType());
		}

		if (!(errorCode = Validator.checkString(aForm.getHorsePower(), false, 1, 20)).equals(Validator.ERROR_NONE)) {
			errors
					.add("horsePower", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							"20"));
			DefaultLogger.error(LOGOBJ, "hp(): " + aForm.getHorsePower());
		}*/

		// Other validation
		/*if (!(errorCode = Validator.checkAmount(aForm.getRegFee(), false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
				IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("regFee", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
					maximumAmt));
			DefaultLogger.error(LOGOBJ, "aForm.getRegFee(): " + aForm.getRegFee());
		}
*/
	/*
	 * closed on 6/7/2011, for  Vehicle security 
	 * 
	 * 
	 * 	if (!(errorCode = Validator.checkDate(aForm.getRegDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("regDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
			DefaultLogger.error(LOGOBJ, "aForm.getRegDate(): " + aForm.getRegDate());
		}

		if (!(errorCode = Validator.checkAmount(aForm.getDptradein(), false, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("dptradein", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
					maximumAmt));
			DefaultLogger.error(LOGOBJ, "aForm.getDptradein(): " + aForm.getDptradein());
		}

		if (!(errorCode = Validator.checkAmount(aForm.getDpcash(), false, 1, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
				IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("dpcash", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
					maximumAmt));
			DefaultLogger.error(LOGOBJ, "aForm.getDpcash(): " + aForm.getDpcash());
		}*/

		/*
		 * boolean isMandatory = errors.isEmpty() &&
		 * StringUtils.isNotBlank(aForm.getDptradein()); if (!(errorCode =
		 * Validator.checkAmount(aForm.getTradeinValue(), isMandatory, 0,
		 * IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
		 * IGlobalConstant.DEFAULT_CURRENCY,
		 * locale)).equals(Validator.ERROR_NONE)) { errors.add("tradeinValue",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
		 * errorCode), "0", maximumAmt)); DefaultLogger.error(LOGOBJ,
		 * "aForm.getTradeinValue(): " + aForm.getTradeinValue()); }
		 */

		// Andy Wong, 14 Feb 2009: validate ehak milik to only allow numeric
	/*
	 * closed on 6/7/2011, for  Vehicle security 
	 * 
	 * 
	 * 	if (!(errorCode = Validator.checkNumber(aForm.getEHakMilikNumber(), false, 0, MAX_NUMBER))
				.equals(Validator.ERROR_NONE)) {
			errors.add("EHakMilikNumber", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					MAX_NUMBER + ""));
		}

		if (!(errorCode = Validator.checkAmount(aForm.getFcharges(), false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
				IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("fcharges", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
					maximumAmt));
			DefaultLogger.error(LOGOBJ, "aForm.getFcharges(): " + aForm.getFcharges());
		}
*/		/*
		 * if (!(errorCode = Validator.checkAmount(aForm.getPlist(), false, 1,
		 * IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
		 * IGlobalConstant.DEFAULT_CURRENCY,
		 * locale)).equals(Validator.ERROR_NONE)) { errors.add("plist", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
		 * "1", maximumAmt)); DefaultLogger.error(LOGOBJ, "aForm.getPlist(): "+
		 * aForm.getPlist()); }
		 */
/*
 * 
 * closed on 6/7/2011, for  Vehicle security 
 * 
 * 
		if (!(errorCode = Validator.checkNumber(aForm.getPlist(), mandatorySubmit, 1,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, 3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("plist",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1", maximumAmt));
			DefaultLogger.error(LOGOBJ, "aForm.getPlist(): " + aForm.getPlist());
		}

		// Mandatory if asset is not New
		// if (!("N".equals(aForm.getGoodStatus()))){

		if (!(errorCode = Validator.checkAmount(aForm.getScrapValue(), mandatorySubmit, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			if (!(aForm.getScrapValue() == null || aForm.getScrapValue().equals(""))) {
				errors.add("scrapValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
						maximumAmt));
			}
			DefaultLogger.error(LOGOBJ, "getScrapValue(): " + aForm.getScrapValue());
		}

		if (!(errorCode = Validator.checkAmount(aForm.getAssetValue(), false, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("assetValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
					maximumAmt));
			DefaultLogger.error(LOGOBJ, "getAssetValue(): " + aForm.getAssetValue());
		}

		// amount validation
		if (!(errorCode = Validator.checkAmount(aForm.getCoe(), false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
				IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("coe", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1", maximumAmt));
			DefaultLogger.error(LOGOBJ, "coe(): " + aForm.getCoe());
		}

		if (!(errorCode = Validator.checkAmount(aForm.getSalesProceed(), false, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("salesProceed", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
					maximumAmt));
			DefaultLogger.error(LOGOBJ, "getSalesProceed(): " + aForm.getSalesProceed());
		}

		if (!(errorCode = Validator.checkInteger(aForm.getRepossessionAge(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER)).equals(Validator.ERROR_NONE)) {
			errors.add("repossessionAge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER + ""));
			DefaultLogger.error(LOGOBJ, "getRepossessionAge(): " + aForm.getRepossessionAge());
		}

		if (!(errorCode = Validator.checkInteger(aForm.getDocPerfectAge(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER)).equals(Validator.ERROR_NONE)) {
			errors.add("docPerfectAge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER + ""));
			DefaultLogger.error(LOGOBJ, "getDocPerfectAge(): " + aForm.getDocPerfectAge());
		}

		if (StringUtils.isNotBlank(aForm.getEngineCapacity())) {
			if (aForm.getEngineCapacity().length() > 15) {
				errors.add("engineCapacity", new ActionMessage("error.integer.greaterthan", "0", StringUtils.repeat(
						"9", 15)));
			}
			else {
				try {
					Long.parseLong(aForm.getEngineCapacity());
				}
				catch (NumberFormatException ex) {
					errors.add("engineCapacity", new ActionMessage("error.integer.format"));
				}
			}
		}*/

		/*
		 * closed on 6/7/2011, for  Vehicle security  
		 * 
		 * if (aForm.getIsSSC().equals("false")) {
			if (aForm.getIsPhysInsp().equals("true")) {
				boolean validateError = false;
				if (!(errorCode = Validator.checkInteger(aForm.getPhysInspFreq(), mandatorySubmit, 0, 99))
						.equals(Validator.ERROR_NONE)) {
					errors.add("physInspFreq", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							"0", 99 + ""));
					validateError = true;
				}
				if (!validateError
						&& !(errorCode = Validator.checkString(aForm.getPhysInspFreqUOM(), true, 0, 50))
								.equals(Validator.ERROR_NONE)) {
					errors.add("physInspFreq", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							"0", 50 + ""));
				}

				if (!(errorCode = Validator.checkDate(aForm.getDatePhyInspec(), mandatorySubmit, locale))
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
		else {
			if (!(errorCode = Validator.checkString(aForm.getDescription(), mandatorySubmit, 1, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("description", new ActionMessage("error.string.mandatory", "1", errorCode));
				DefaultLogger.error(LOGOBJ, "getDescription(): " + aForm.getDescription());
			}
		}*/

		// boolean
		// isTradeInInfoMandatory=(!AbstractCommonMapper.isEmptyOrNull(aForm.getDptradein())
		// && mandatorySubmit);
	/*
	 *closed on 6/7/2011, for  Vehicle security  
	 * 
	 * 	boolean isTradeInInfoMandatory = (StringUtils.isNotBlank(aForm.getTradeInDeposit()) && mandatorySubmit);

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
		/*
		 * if (!(errorCode = Validator.checkAmount(aForm.getTradeInDeposit(),
		 * isTradeInInfoMandatory, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
		 * IGlobalConstant.DEFAULT_CURRENCY,
		 * locale)).equals(Validator.ERROR_NONE)) { errors.add("tradeInDeposit",
		 * new ActionMessage("error.string.mandatory", "1", errorCode)); }
		 */

		// Andy Wong, 19 Jan 2009: Sibs validation for date
		/*
		 * closed on 6/7/2011, for  Vehicle security 
		 * 
		 * if (StringUtils.isNotBlank(aForm.getRegDate())) {
			Date tempDate = DateUtil.convertDate(locale, aForm.getRegDate());
			if (tempDate.after(new Date())) {
				errors.add("regDate", new ActionMessage("error.date.compareDate.greater", "Registration Date",
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
		}*/

		// Andy Wong, 18 Feb 2009: validate Repossession Date cannot be later
		// than today date
		/*
		 * closed on 6/7/2011, for  Vehicle security 
		 * 
		 * if (StringUtils.isNotBlank(aForm.getRepossessionDate())) {
			Date tempDate = DateUtil.convertDate(locale, aForm.getRepossessionDate());
			if (tempDate.after(new Date())) {
				errors.add("repossessionDate", new ActionMessage("error.date.compareDate.greater",
						"Date of Repossession", "Today Date"));
			}
		}

		if (StringUtils.isNotBlank(aForm.getRegDate()) && StringUtils.isNotBlank(aForm.getRoadTaxExpiryDate())) {
			Date tempDate = DateUtil.convertDate(locale, aForm.getRegDate());
			Date tempDate2 = DateUtil.convertDate(locale, aForm.getRoadTaxExpiryDate());

			if (tempDate.after(tempDate2)) {
				errors.add("regDate", new ActionMessage("error.date.compareDate.greater", "Registration Date",
						"Road Tax Expiry Date"));
			}
		}

		if (ArrayUtils.indexOf(AVAILABLE_PBT_PBR_IND, aForm.getPbtIndicator()) != ArrayUtils.INDEX_NOT_FOUND) {
			if (StringUtils.isBlank(aForm.getPbrPbtPeriod())) {
				errors.add("pbrPbtPeriod", new ActionMessage("error.string.mandatory"));
			}
		}

		if (StringUtils.isNotBlank(aForm.getPbrPbtPeriod())) {
			errorCode = Validator.checkInteger(aForm.getPbrPbtPeriod(), false, 0, 999);
			if (!Validator.ERROR_NONE.equals(errorCode)) {
				errors.add("pbrPbtPeriod", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode),
						"1", "999"));
			}
		}
*/
	//added by Rajib	
		if (aForm.getChassisNumber() == null
				|| "".equals(aForm.getChassisNumber().trim())) {
			/*errors.add("chassisNumberError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(ManualInputCustomerValidator.class,
					"chassisNumberError");*/
		}
		
		if (aForm.getEngineNo() == null
				|| "".equals(aForm.getEngineNo().trim())) {
			/*errors.add("engineNoError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(ManualInputCustomerValidator.class,
					"engineNoError");*/
		}
		
		if (aForm.getRegNo() == null
				|| "".equals(aForm.getRegNo().trim())) {
			/*errors.add("regNoError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(ManualInputCustomerValidator.class,
					"regNoError");*/
		}
		if (aForm.getLogBookNumber() == null
				|| "".equals(aForm.getLogBookNumber().trim())) {
			/*errors.add("logBookNumberError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(ManualInputCustomerValidator.class,
					"logBookNumberError");*/
		}
		if (aForm.getRegDate() == null
				|| "".equals(aForm.getRegDate().trim())) {
			/*errors.add("regDateError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(ManualInputCustomerValidator.class,
					"regDateError");*/
		}
		
		 if (StringUtils.isNotBlank(aForm.getRegDate())
	                && DateUtil.convertDate(locale, aForm.getRegDate()).after(new Date())) {
	            errors.add("regDateError", new ActionMessage("error.date.compareDate.cannotBeEarlier",
	                    "Registration Date", "Today Date"));
	        }
		 
		 if (StringUtils.isNotBlank(aForm.getCollateralMaturityDate())
	                && DateUtil.convertDate(locale, aForm.getCollateralMaturityDate()).before(new Date())) {
	            errors.add("collateralMaturityDateError", new ActionMessage("error.date.compareDate.cannotBelater",
	                    "Security Maturity Date", "Today Date"));
	        }
		 
		 if (StringUtils.isNotBlank(aForm.getDateSecurityEnv())
	                && DateUtil.convertDate(locale, aForm.getDateSecurityEnv()).after(new Date())) {
	            errors.add("dateSecurityEnvError", new ActionMessage("error.date.compareDate.cannotBeEarlier",
	                    "Date Security Confirmed as Environmentally Risky", "Today Date"));
	        }
		 if (StringUtils.isNotBlank(aForm.getDatePurchase())
	                && DateUtil.convertDate(locale, aForm.getDatePurchase()).after(new Date())) {
	            errors.add("datePurchaseError", new ActionMessage("error.date.compareDate.cannotBeEarlier",
	                    "Date of Purchase ", "Today Date"));
	        }
		
		 if (aForm.getStartDate() == null
					|| "".equals(aForm.getStartDate().trim())) {
				/*errors.add("startDateError", new ActionMessage(
						"error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class,
						"startDateError");*/
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
		 
		 if (StringUtils.isNotBlank(aForm.getStartDate())
	                && DateUtil.convertDate(locale, aForm.getStartDate()).after(new Date())) {
	            errors.add("startDateError", new ActionMessage("error.date.compareDate.cannotBeEarlier",
	                    "Start Date", "Today Date"));
	        }
			 
		 if (aForm.getCollateralMaturityDate() == null
					|| "".equals(aForm.getCollateralMaturityDate().trim())) {
				/*errors.add("collateralMaturityDateError", new ActionMessage(
						"error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class,
						"collateralMaturityDateError");*/
			} 
		
		 if ( aForm.getCollateralMaturityDate().trim().length() > 0) {
				if (aForm.getStartDate().trim().length() > 0){	
		 if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getCollateralMaturityDate())).before(
					DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getStartDate())))) {
					errors.add("collateralMaturityDateStartDateError", new ActionMessage("error.date.compareDate.cannotBelater", "Security Maturity Date",
						"Start Date"));
				   }
		         }
				}
		 
		 if ((aForm.getDatePurchase()!= null) && (aForm.getDatePurchase().trim().length() != 0)){
				if(aForm.getYearMfg()!=null && !"".equals(aForm.getYearMfg())){
					int m=1900+DateUtil.convertDate(locale,aForm.getDatePurchase()).getYear();
					int n=Integer.parseInt(aForm.getYearMfg());
				if(m<n) {
			errors.add("datePurchaseError", new ActionMessage(
						"error.date.compareDate.cannotBelater", "Date of Purchase", "Year of Manufacture "));
			}
			}
			}
		 
		 if ((aForm.getStartDate()!= null) && (aForm.getStartDate().trim().length() != 0)){
				if(aForm.getCollateralMaturityDate()!=null && !"".equals(aForm.getCollateralMaturityDate())){
				if(DateUtil.clearTime(DateUtil.convertDate(locale,aForm.getStartDate())).after(DateUtil.clearTime(DateUtil.convertDate(locale,aForm.getCollateralMaturityDate())))) {
			errors.add("collateralMaturityDate", new ActionMessage(
						"error.date.compareDate.cannotBelater", "Security Maturity Date", "Start Date "));
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

		/*Date d = DateUtil.getDate();
		if ((aForm.getRegDate() != null) && (aForm.getRegDate().trim().length() != 0)) {
			int a = d.compareTo(DateUtil.convertDate(locale, aForm.getRegDate()));
			
			if (a < 0) {
				errors.add("regDateError", new ActionMessage("error.date.compareDate.more", "Registration Date ",
				"Current Date"));
			}
			
			
		
		}
		
		 
		if ((aForm.getDateSecurityEnv() != null) && (aForm.getDateSecurityEnv().trim().length() != 0)) {
			int a = d.compareTo(DateUtil.convertDate(locale, aForm.getCollateralMaturityDate()));
			
			if (a > 0) {
				errors.add("collateralMaturityDateError", new ActionMessage("error.date.compareDate.more", "Registration Date ",
				"Current Date"));
			}
		
		}
		
		if ((aForm.getDateSecurityEnv() != null) && (aForm.getDateSecurityEnv().trim().length() != 0)) {
			int a = d.compareTo(DateUtil.convertDate(locale, aForm.getDateSecurityEnv()));
			
			if (a <0) {
				errors.add("dateSecurityEnvError", new ActionMessage("error.date.compareDate.more", "Date Security Confirmed as Environmentally Risky",
				"Current Date"));
			}
		
		}
		*/
		
		 if(aForm.getAssetCollateralBookingVal()!=null && !"".equals(aForm.getAssetCollateralBookingVal())){
			 if (!(errorCode = Validator.checkNumericString(UIUtil.removeComma(aForm.getAssetCollateralBookingVal()), false, 0, 20)).equals(Validator.ERROR_NONE)) {
					errors.add("assetCollateralBookingVal", new ActionMessage(
							ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", "999999999999999999"));
				   }
			}
		
		//added by Rajib... end....		
		
		DefaultLogger.error(LOGOBJ, "No of Errors  " + errors.size());
		return errors;

	}
}
