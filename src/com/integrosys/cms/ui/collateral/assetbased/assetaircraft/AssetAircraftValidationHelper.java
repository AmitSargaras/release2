//<%--This file is using for  Specific charge instead of Aircraft --%>
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetaircraft;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.feed.FeedConstants;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class AssetAircraftValidationHelper {

	private static String LOGOBJ = AssetAircraftValidationHelper.class.getName();

	private static String YES = ICMSConstant.YES;

	public static ActionErrors validateInput(AssetAircraftForm aForm, Locale locale, ActionErrors errors) {

		String errorCode = null;
		final double MAX_NUMBER = Double.parseDouble("999999999999999");
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
		boolean isMandatory = false;
		if (aForm.getEvent() != null && (aForm.getEvent().equals("submit") || aForm.getEvent().equals("REST_UPDATE_AB_SA_SECURITY"))) {
			isMandatory = true;
		}

		 if ((aForm.getNextPhysInspDate()!= null) && (aForm.getNextPhysInspDate().trim().length() != 0)){
				if(aForm.getDatePhyInspec()!=null && !"".equals(aForm.getDatePhyInspec())){
				if(DateUtil.clearTime(DateUtil.convertDate(locale,aForm.getDatePhyInspec())).after(DateUtil.clearTime(DateUtil.convertDate(locale,aForm.getNextPhysInspDate())))) {
			errors.add("collateralMaturityDate", new ActionMessage(
						"error.date.compareDate.cannotBelater", "Next Physical Inspection Date", "Last Physical Inspection Date"));
			}
			}
			}
		
			if(aForm.getEvent() != null && !(aForm.getEvent().equals("")) && aForm.getEvent().equals("submit") ){
				if (!(errorCode = Validator.checkString(aForm.getAmountCMV(), true, 0, 50))
						.equals(Validator.ERROR_NONE)) {
			errors.add("amountCMV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
				}
		
		/*if (!(errorCode = Validator.checkAmount(aForm.getPurchasePrice(), isMandatory, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("purchasePrice", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
					maximumAmt));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetSpecPlantValidationHelper",
					"getPurchasePrice(): " + aForm.getPurchasePrice());
		}

		if (!(errorCode = Validator.checkString(aForm.getProcessAgentOp(), false, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("processAgentOp", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidationHelper",
					"aForm.getProcessAgentOp(): " + aForm.getProcessAgentOp());
		}
		if (!(errorCode = Validator.checkString(aForm.getSplLegalAdvise(), false, 0, 250)).equals(Validator.ERROR_NONE)) {
			errors.add("splLegalAdvise", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					250 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidationHelper",
					"aForm.getSplLegalAdvise(): " + aForm.getSplLegalAdvise());
		}
		if (!(errorCode = Validator.checkString(aForm.getGuarantors(), false, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("guarantors", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidationHelper",
					"aForm.getGuarantors(): " + aForm.getGuarantors());
		}
		if (!(errorCode = Validator.checkString(aForm.getInsuers(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors
					.add("insuers", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							50 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidationHelper",
					"aForm.getInsuers(): " + aForm.getInsuers());
		}
		if (!(errorCode = Validator.checkDate(aForm.getRegDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("regDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidationHelper",
					"getRegDate(): " + aForm.getRegDate());
		}

		Calendar currDate = DateUtil.getCalendar();

		if (!(errorCode = Validator.checkInteger(aForm.getYearMfg(), isMandatory, 1000, 9999))
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
		}

		if (!(errorCode = Validator.checkString(aForm.getAssetType(), isMandatory, 1, 10)).equals(Validator.ERROR_NONE)) {
			errors.add("assetType", new ActionMessage("error.string.mandatory", "1", "10"));
			DefaultLogger.debug(LOGOBJ, "getAssetType(): " + aForm.getAssetType());
		}
		if (!(errorCode = Validator.checkString(aForm.getModelNo(), isMandatory, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors
					.add("modelNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							50 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getModelNo(): " + aForm.getModelNo());
		}

		if (!(errorCode = Validator.checkString(aForm.getAircraftSN(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("aircraftSN", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidationHelper",
					"getAircraftSN(): " + aForm.getAircraftSN());
		}
		if (!(errorCode = Validator.checkString(aForm.getManuName(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("manuName",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidationHelper",
					"getManuName(): " + aForm.getManuName());
		}
		if (!(errorCode = Validator.checkString(aForm.getManuWarr(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("manuWarr",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidationHelper",
					"getManuWarr(): " + aForm.getManuWarr());
		}
		if (!(errorCode = Validator.checkString(aForm.getAssignors(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("assignors", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidationHelper",
					"getAssignors(): " + aForm.getAssignors());
		}
		if (!(errorCode = Validator.checkString(aForm.getAssgOfInsurance(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("assgOfInsurance", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidationHelper",
					"getAssgOfInsurance(): " + aForm.getAssgOfInsurance());
		}
		if (!(errorCode = Validator.checkAmount(aForm.getAmtAssignment(), false, 1,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("amtAssignment", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
					maximumAmt));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidationHelper",
					"getAmtAssignment(): " + aForm.getAmtAssignment());
		}
		if (!(errorCode = Validator.checkAmount(aForm.getTradeinValue(), false, 1,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("tradeinValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
					maximumAmt));
		}
		if (!(errorCode = Validator.checkAmount(aForm.getTradeinDeposit(), false, 1,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("tradeinDeposit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
					maximumAmt));
		}
		if (!(errorCode = Validator.checkDate(aForm.getEffDateAssgIns(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("effDateAssgIns", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidationHelper",
					"getEffDateAssgIns(): " + aForm.getEffDateAssgIns());
		}
		if (!(errorCode = Validator.checkDate(aForm.getExpiryDateAssignInsure(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("expiryDateAssignInsure", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
					"0", 256 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidationHelper",
					"getExpiryDateAssignInsure(): " + aForm.getExpiryDateAssignInsure());
		}
		if (!(errorCode = Validator.checkString(aForm.getAssgOfReInsurance(), false, 0, 50))
				.equals(Validator.ERROR_NONE)) {
			errors.add("assgOfReInsurance", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", 50 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidationHelper",
					"getAssgOfReInsurance(): " + aForm.getAssgOfReInsurance());
		}*/
		/*if (!(errorCode = Validator.checkDate(aForm.getEffDateAssgReIns(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("effDateAssgReIns", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidationHelper",
					"getEffDateAssgReIns(): " + aForm.getEffDateAssgReIns());
		}
		if (!(errorCode = Validator.checkDate(aForm.getExpDateAssignReInsure(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("expDateAssignReInsure", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
					"0", 256 + ""));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidationHelper",
					"getExpDateAssignReInsure(): " + aForm.getExpDateAssignReInsure());
		}
*/
		/*if (!(errorCode = Validator.checkString(aForm.getSecEnvRisky(), isMandatory, 0, 50))
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
		}*/

		if (aForm.getIsSSC().equals("false")) {
			
			
			if ("REST_UPDATE_AB_SA_SECURITY"
					.equalsIgnoreCase(aForm.getEvent())) {
				if (aForm.getIsPhysInsp() == null
						|| "".equals(aForm.getIsPhysInsp())) {
					errors.add("isPhysicalInspection",
							new ActionMessage("error.string.mandatory"));
				}
				if (aForm.getIsPhysInsp() != null
						|| !"".equals(aForm.getIsPhysInsp())) {
					if ("true".equalsIgnoreCase(aForm.getIsPhysInsp())) {

						if (aForm.getPhysInspFreqUOM() == null
								|| "".equals(aForm.getPhysInspFreqUOM())) {
							errors.add("physicalInspectionFreq", new ActionMessage(
									"error.string.mandatory"));
						}
						
						if (aForm.getDatePhyInspec() == null
								|| "".equals(aForm.getDatePhyInspec())) {
							errors.add("lastPhysicalInspectDate",
									new ActionMessage(
											"error.string.mandatory"));
						}
						if (aForm.getNextPhysInspDate() == null
								|| "".equals(aForm.getNextPhysInspDate())) {
							errors.add("nextPhysicalInspectDate",
									new ActionMessage(
											"error.string.mandatory"));
						}
						if (!"".equals(aForm.getDatePhyInspec())
								&& !"".equals(aForm.getNextPhysInspDate())) {
							if (DateUtil
									.clearTime(DateUtil.convertDate(locale,
											aForm.getDatePhyInspec()))
									.after(DateUtil.convertDate(locale,
											aForm.getNextPhysInspDate()))) {
								errors.add("nextPhysicalInspectDate",
										new ActionMessage(
												"error.date.compareDate",
												"This ",
												"Physical Verification Done on"));
							}
						}
					}
				}
			}
			else
			{
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
						
//						if (!(errorCode = Validator.checkDate(aForm.getDatePhyInspec(), false, locale))
//								.equals(Validator.ERROR_NONE)) {
//							errors.add("datePhyInspec", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
//									256 + ""));
//						}
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
		}
		/*
		if (!(errorCode = Validator.checkDate(aForm.getNextPhysInspDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("nextPhysInspDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					250 + ""));
		}

		if (!(errorCode = Validator.checkDate(aForm.getCollateralMaturityDate(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("collateralMaturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
					"0", 256 + ""));
			DefaultLogger.debug(LOGOBJ, "aForm.getCollateralMaturityDate(): " + aForm.getCollateralMaturityDate());
		}*/

		// amount validation
		/*if (!(errorCode = Validator.checkAmount(aForm.getScrapValue(), false, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("scrapValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
					maximumAmt));
			DefaultLogger.error(
					"com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetSpecPlantValidationHelper",
					"getScrapValue(): " + aForm.getScrapValue());
		}*/

		// int validation

		/*
		 * if (!(errorCode = Validator.checkInteger(aForm.getDocPerfectAge(),
		 * false, 0,
		 * IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER)).equals(Validator
		 * .ERROR_NONE)) { errors.add("docPerfectAge", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode),
		 * "0", "99999")); DefaultLogger.error(
		 * "com.integrosys.cms.ui.collateral.assetbased.assetspecvehicles.AssetSpecVehiclesValidationHelper"
		 * , "getDocPerfectAge(): " + aForm.getDocPerfectAge()); }
		 */

		/*if (AbstractCommonMapper.isEmptyOrNull(aForm.getPubTransport())) {
			errors.add("pubTransport", new ActionMessage("error.mandatory"));
		}*/
		
		
		if(aForm.getRamId() != null && !aForm.getRamId().equals("")){
			if(aForm.getRamId().length() > 50){
				errors.add("ramId", new ActionMessage("error.string.length"));
			}
			else{
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(aForm.getRamId());
				if( codeFlag == true) {
					errors.add("ramId", new ActionMessage("error.string.invalidCharacter"));
				}
			}
		}
		
		
		if (aForm.getStartDate() !=null && !aForm.getStartDate().equals("")){
			if (!(errorCode = Validator.checkDate(aForm.getStartDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("startDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}else if (DateUtil.convertDate(locale, aForm.getStartDate()).after(new Date())) {
				errors.add("startDate", new ActionMessage(FeedConstants.ERROR_FUTURE_DATE));
			}
		}

		if (aForm.getMaturityDate() !=null && !aForm.getMaturityDate().equals("")){
			if (!(errorCode = Validator.checkDate(aForm.getMaturityDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("maturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}else if (DateUtil.convertDate(locale, aForm.getMaturityDate()).before(new Date())) {
				errors.add("maturityDate", new ActionMessage(FeedConstants.ERROR_PAST_DATE));
			}	
		}
		
		return errors;

	}
}
