//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetspecgold;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * AssetSpecGoldValidationHelper
 * 
 * Describe this class. Purpose: AssetSpecGoldValidationHelper Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class AssetSpecGoldValidationHelper {

	private static String LOGOBJ = AssetSpecGoldValidationHelper.class
			.getName();

	private static String YES = ICMSConstant.YES;

	public static ActionErrors validateInput(AssetSpecGoldForm aForm,
			Locale locale, ActionErrors errors) {
		String errorCode = null;

		String maximumAmt = new BigDecimal(
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
		String maxWeight = new BigDecimal(
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4).toString();

		String maximumAmt_7_str = IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_7_STR;
		final double MAX_NUMBER_7 = Double.parseDouble(maximumAmt_7_str);

		boolean mandatorySubmit = false;
		if (aForm.getEvent().equals("submit")
				|| aForm.getEvent().equals("update")) {
			mandatorySubmit = true;
		}

		/*Govind S: 1. Comment for HDFC Gold security on 30/06/2011 
		if (!(errorCode = Validator.checkAmount(aForm.getPurchasePrice(),
				mandatorySubmit, 1, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
				IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("purchasePrice", new ActionMessage(ErrorKeyMapper.map(
					ErrorKeyMapper.AMOUNT, errorCode), "1", maximumAmt));
			DefaultLogger
					.error(
							"com.integrosys.cms.ui.collateral.assetbased.assetspecplant.AssetSpecGoldValidationHelper",
							"getDebtorsCMV: ");
		}

		if (!(errorCode = Validator.checkDate(aForm.getDatePurchase(), false,
				locale)).equals(Validator.ERROR_NONE)) {
			errors.add("datePurchase", new ActionMessage(ErrorKeyMapper.map(
					ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
			DefaultLogger
					.error(
							"com.integrosys.cms.ui.collateral.assetbased.assetspecplant.AssetSpecGoldValidationHelper",
							"getDatePurchase(): " + aForm.getDatePurchase());
		}*/
		/*
		 *  Gold Type Validation
		 */
		boolean lengthFlag = true;
		if (ASSTValidator.isValidAlphaNumStringWithSpace(aForm.getAssetType()))
		{
			errors.add("assetType",new ActionMessage("error.string.invalidCharacter"));
			lengthFlag = false;
		}
		if (lengthFlag && !(errorCode = Validator.checkString(aForm.getAssetType(),false, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("assetType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),"0", 50 + ""));
			DefaultLogger.debug(LOGOBJ, "getAssetType(): "
					+ aForm.getAssetType());
		}
		if (!aForm.getAssetType().equals("") && lengthFlag && aForm.getAssetType().length()<=50 && (errorCode = Validator.checkNumericString(aForm.getAssetType(),
				false, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("assetType", new ActionMessage("error.alphabet.format"));
			DefaultLogger.debug(LOGOBJ, "getAssetType(): "
					+ aForm.getAssetType());
		}
		/*
		 * Gold Grade Validation
		 */
		lengthFlag = true;
		if (ASSTValidator.isValidAlphaNumStringWithSpace(aForm.getGoldGrade()))
		{
			errors.add("goldGrade",new ActionMessage("error.string.invalidCharacter"));
			lengthFlag = false;
		}
		if (lengthFlag && !(errorCode = Validator.checkString(aForm.getGoldGrade(),false, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("goldGrade", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),"0", 50 + ""));
			DefaultLogger.debug(LOGOBJ, "getGoldGrade(): "
					+ aForm.getGoldGrade());
		}
		if (!aForm.getGoldGrade().equals("") && lengthFlag && aForm.getGoldGrade().length()<=50 && (errorCode = Validator.checkNumericString(aForm.getGoldGrade(),
				false, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("goldGrade", new ActionMessage("error.alphabet.format"));
			DefaultLogger.debug(LOGOBJ, "getGoldGrade(): "
					+ aForm.getGoldGrade());
		}
		
		/*
		 * Gold Unit Price Validation
		 */

		 if (!(errorCode = Validator.checkAmount(aForm.getGoldUnitPrice(),false, 0.001, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10_2,
				 IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
				 errors.add("goldUnitPrice", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),"0.001", maximumAmt)); 
				 DefaultLogger.debug(LOGOBJ,"aForm.getGoldUnitPrice(): " + aForm.getGoldUnitPrice());
				 
		 }
		 
		 if ((aForm.getGoldUnitPrice()!=null && !aForm.getGoldUnitPrice().equals("")) && !(errorCode = Validator.checkString(aForm.getGoldUnitPriceUOM(),true, 1, 10)) .equals(Validator.ERROR_NONE)) {
		  errors.add("goldUnitPriceUOM", new ActionMessage("label.please.select.option", "1", "10"));
		  DefaultLogger.debug(LOGOBJ, "getGoldUnitPriceUOM(): " +aForm.getGoldUnitPriceUOM()); 
		  }

		
		 /*
		 * Weight Validation
	     */ 

		if (!(errorCode = Validator
				.checkNumber(aForm.getGoldWeight(), false, 0.00001,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4, 6, locale))
				.equals(Validator.ERROR_NONE)) {
			if (Validator.ERROR_DECIMAL_EXCEEDED.equals(errorCode)) {
				errorCode = "moredecimalexceeded";
			}
			errors.add("goldWeight", new ActionMessage(ErrorKeyMapper.map(
					ErrorKeyMapper.NUMBER, errorCode), "0.00001",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4_STR, "6"));
			DefaultLogger.debug(LOGOBJ, "aForm.getGoldWeight(): "
					+ aForm.getGoldWeight());
		}
		
		if ((aForm.getGoldWeight()!=null && !aForm.getGoldWeight().equals("")) && !(errorCode = Validator.checkString(aForm.getGoldUOM(),
				true, 1, 10)).equals(Validator.ERROR_NONE)) {
			errors.add("goldUOM", new ActionMessage("label.please.select.option",
					"1", "10"));
			DefaultLogger.debug(LOGOBJ, "getGoldUOM(): " + aForm.getGoldUOM());
		}
		if(aForm.getEvent() != null && !(aForm.getEvent().equals("")) && aForm.getEvent().equals("submit")){
		if (!(errorCode = Validator.checkString(aForm.getAmountCMV(), true, 0, 50))
				.equals(Validator.ERROR_NONE)) {
	errors.add("amountCMV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
			50 + ""));
}
		}
		/*Govind S: 2. Comment for HDFC Gold security on 30/06/2011 
		/*
		if (!(errorCode = Validator.checkAmount(aForm.getPurchasePrice(),
				false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
				IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("purchasePrice", new ActionMessage(ErrorKeyMapper.map(
					ErrorKeyMapper.AMOUNT, errorCode), "0", maximumAmt));
			DefaultLogger.debug(LOGOBJ, "aForm.getPurchasePrice(): "
					+ aForm.getPurchasePrice());
		}

		if (aForm.getIsSSC().equals("false")) {
			if (aForm.getIsPhysInsp().equals("true")) {
				boolean validateError = false;
				if (!(errorCode = Validator.checkInteger(aForm
						.getPhysInspFreq(), true, 0, 99))
						.equals(Validator.ERROR_NONE)) {
					errors.add("physInspFreq", new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.STRING, errorCode), "0", 99 + ""));
					validateError = true;
				}
				if (!validateError
						&& !(errorCode = Validator.checkString(aForm
								.getPhysInspFreqUOM(), true, 0, 50))
								.equals(Validator.ERROR_NONE)) {
					errors.add("physInspFreq", new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.STRING, errorCode), "0",
							50 + ""));
				}
				if (!(errorCode = Validator.checkDate(aForm.getDatePhyInspec(),
						true, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("datePhyInspec", new ActionMessage(
							ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"0", 250 + ""));
				}
			} else if (aForm.getIsPhysInsp().equals("false")) {
				if (!aForm.getPhysInspFreq().equals("")
						|| !aForm.getPhysInspFreqUOM().equals("")) {
					errors.add("physInspFreq", new ActionMessage(
							"error.string.empty"));
				}
				if (!aForm.getDatePhyInspec().equals("")) {
					errors.add("datePhyInspec", new ActionMessage(
							"error.string.empty"));
				}
			}
		}

		if (!(errorCode = Validator.checkDate(aForm.getNextPhysInspDate(),
				false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("nextPhysInspDate", new ActionMessage(ErrorKeyMapper
					.map(ErrorKeyMapper.DATE, errorCode), "0", 250 + ""));
		}

		// Add new validation for new fields
		if (!(errorCode = Validator.checkString(aForm.getGoldGrade(), mandatorySubmit, 0,
				30)).equals(Validator.ERROR_NONE)) {
			errors.add("goldGrade", new ActionMessage(ErrorKeyMapper.map(
					ErrorKeyMapper.STRING, errorCode), "0", 30 + ""));
		}

		if (!(errorCode = Validator.checkString(aForm.getPurchaseReceiptNo(),
				false, 0, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("purchaseReceiptNo", new ActionMessage(ErrorKeyMapper
					.map(ErrorKeyMapper.STRING, errorCode), "0", 40 + ""));
		}

		if (!(errorCode = Validator.checkDate(aForm.getCertExpiryDate(), false,
				locale)).equals(Validator.ERROR_NONE)) {
			errors.add("certExpiryDate", new ActionMessage(ErrorKeyMapper.map(
					ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
			DefaultLogger
					.error(
							"com.integrosys.cms.ui.collateral.assetbased.assetspecplant.AssetSpecGoldValidationHelper",
							"getCertExpiryDate(): " + aForm.getCertExpiryDate());
		}

		if (!(errorCode = Validator.checkDate(aForm.getAuctionDate(), false,
				locale)).equals(Validator.ERROR_NONE)) {
			errors.add("auctionDate", new ActionMessage(ErrorKeyMapper.map(
					ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
			DefaultLogger
					.error(
							"com.integrosys.cms.ui.collateral.assetbased.assetspecplant.AssetSpecGoldValidationHelper",
							"getAuctionDate(): " + aForm.getAuctionDate());
		}

		if (!(errorCode = Validator.checkAmount(aForm.getAuctionPrice(), false,
				1, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
				IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("auctionPrice", new ActionMessage(ErrorKeyMapper.map(
					ErrorKeyMapper.AMOUNT, errorCode), "1", maximumAmt));
			DefaultLogger
					.error(
							"com.integrosys.cms.ui.collateral.assetbased.assetspecplant.AssetSpecGoldValidationHelper",
							"getAuctionPrice: ");
		}

		if (!(errorCode = Validator.checkString(aForm.getAuctioneer(), false,
				0, 80)).equals(Validator.ERROR_NONE)) {
			errors.add("auctioneer", new ActionMessage(ErrorKeyMapper.map(
					ErrorKeyMapper.STRING, errorCode), "0", 80 + ""));
		}

		if (!(errorCode = Validator.checkAmount(aForm.getSalesProceed(), false,
				1, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
				IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("salesProceed", new ActionMessage(ErrorKeyMapper.map(
					ErrorKeyMapper.AMOUNT, errorCode), "1", maximumAmt));
			DefaultLogger
					.error(
							"com.integrosys.cms.ui.collateral.assetbased.assetspecplant.AssetSpecGoldValidationHelper",
							"getSalesProceed: ");
		}

		if (!(errorCode = Validator.checkString(aForm.getSecEnvRisky(),
				mandatorySubmit, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("secEnvRisky", new ActionMessage(ErrorKeyMapper.map(
					ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getSecEnvRisky(): "
					+ aForm.getSecEnvRisky());
		}

		if ((null != aForm.getSecEnvRisky())
				&& YES.equals(aForm.getSecEnvRisky().trim())) {
			if (!(errorCode = Validator.checkDate(aForm.getDateSecurityEnv(),
					mandatorySubmit, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("dateSecurityEnv", new ActionMessage(
						"error.date.mandatory", "1", "256"));
				DefaultLogger.debug(LOGOBJ, " dateSecurityEnv is mandatory= "
						+ aForm.getDateSecurityEnv());
			}
		} else {
			if (!(errorCode = Validator.checkDate(aForm.getDateSecurityEnv(),
					false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("dateSecurityEnv", new ActionMessage(
						"error.date.mandatory", "1", "256"));
				DefaultLogger.debug(LOGOBJ,
						" Not valid date, dateSecurityEnv() = "
								+ aForm.getDateSecurityEnv());
			}
		}
2. End Comment for HDFC Gold security on 30/06/2011 */
		//**Add By Govind S:02/09/2011

		//Added by Pramod Katkar for New Filed CR on 5-08-2013
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
//					.equals(Validator.ERROR_NONE)) {
//				errors.add("datePhyInspec", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
//						256 + ""));
//			}
		}
//End by Pramod Katkar
		return errors;

	}
}
