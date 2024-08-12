//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.property;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMappingDao;
import com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.collateral.CollateralValidator;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.feed.FeedConstants;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class PropertyValidator {

	private static String LOGOBJ = PropertyValidator.class.getName();

	private static String YES = ICMSConstant.YES;

	public static final double COLLATERAL_MAX_LAND_AREA = Double.parseDouble(StringUtils.repeat("9", 19));

	public static final double COLLATERAL_MAX_BUILTUP_AREA = Double.parseDouble(StringUtils.repeat("9", 26));

	public static final String COLLATERAL_MAX_LAND_AREA_STR = NumberFormat.getNumberInstance()
			.format(COLLATERAL_MAX_LAND_AREA);

	public static final String COLLATERAL_MAX_BUILTUP_AREA_STR = NumberFormat.getNumberInstance()
			.format(COLLATERAL_MAX_BUILTUP_AREA);

	public static final double MAX_NUMBER = Double.parseDouble("999999999999999");

	public static ActionErrors validateInput(PropertyForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		if(!"REST_UPDATE_PROPERTY_SECURITY".equalsIgnoreCase(aForm.getEvent()))
		errors = CollateralValidator.validateInput(aForm, locale);
		if ((aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {
			return errors;
		}	
	
		
		boolean isMandatory = false;
		if (aForm.getEvent().equals("submit") || "REST_UPDATE_PROPERTY_SECURITY".equalsIgnoreCase(aForm.getEvent())) {
			isMandatory = true;
		}

		if (aForm.getEvent().equals("submit") || aForm.getEvent().equals("approve")  || "REST_UPDATE_PROPERTY_SECURITY".equalsIgnoreCase(aForm.getEvent())) {
			validateMandatoryFields(aForm, locale, errors, isMandatory);
		}

		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
		String errorCode = null;

		int remarksMaxLength = 250;

		if (!(errorCode = Validator.checkString(aForm.getRemarkEnvRisk(), false, 0, remarksMaxLength))
				.equals(Validator.ERROR_NONE)) {
			errors.add("remarkEnvRisk", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					Integer.toString(remarksMaxLength)));
		}

		if (!(errorCode = Validator.checkString(aForm.getDescription(), false, 0, remarksMaxLength))
				.equals(Validator.ERROR_NONE)) {
			errors.add("description", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					Integer.toString(remarksMaxLength)));
		}

		/*if (!(errorCode = Validator.checkString(aForm.getRemarksProperty(), false, 0, remarksMaxLength))
				.equals(Validator.ERROR_NONE)) {
			errors.add("remarksProperty", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					Integer.toString(remarksMaxLength)));
		}*/
		
		if (!(errorCode = Validator.checkString(aForm.getRemarksProperty_v1(), false, 0, remarksMaxLength))
				.equals(Validator.ERROR_NONE)) {
			errors.add("remarksProperty_v1", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					Integer.toString(remarksMaxLength)));
		}

		if (!(errorCode = Validator.checkString(aForm.getRegistedHolder(), false, 0, remarksMaxLength))
				.equals(Validator.ERROR_NONE)) {
			errors.add("registedHolder", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					Integer.toString(remarksMaxLength)));
		}

		/*
		 * already exist duplicate check if (!(errorCode =
		 * Validator.checkString(aForm.getLotNo(), false, 0,
		 * 15)).equals(Validator.ERROR_NONE)) { errors.add("lotNo", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 15 + "")); }
		 */

		if (!(errorCode = Validator.checkAmount(aForm.getNominalValue(), false, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("nominalValue",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0", maximumAmt));
		}

		if (!(errorCode = Validator.checkString(aForm.getProjectName(), false, 0, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("projectName",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "150"));
		}

		if (!(errorCode = Validator.checkString(aForm.getUnitParcelNo(), false, 0, 10)).equals(Validator.ERROR_NONE)) {
			errors.add("unitParcelNo",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "10"));
		}

		if (!(errorCode = Validator.checkNumber(aForm.getCombinedValueAmount(), false, 0, 100, 3, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("combinedValueAmount",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100"));
		}

		if (!(errorCode = Validator.checkNumber(aForm.getValueNumber(), false, 0, Double.MAX_VALUE, 1, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("valueNumber", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode)));
		}
		/*
		 * Commented by Sandeep Shinde to remove Validation which is not
		 * required due to presence of new fields if (!(errorCode =
		 * Validator.checkString(aForm.getSecEnvRisky(), isMandatory, 0, 50))
		 * .equals(Validator.ERROR_NONE)) { errors.add("secEnvRisky", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 50 + "")); }
		 */

		if (!(errorCode = Validator.checkString(aForm.getLocationLot(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("locationLot",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
		}

		if (!(errorCode = Validator.checkString(aForm.getRestrictCondition(), false, 0, remarksMaxLength))
				.equals(Validator.ERROR_NONE)) {
			errors.add("restrictCondition", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					Integer.toString(remarksMaxLength)));
		}

		if (!(errorCode = Validator.checkString(aForm.getExpressedCondition(), false, 0, remarksMaxLength))
				.equals(Validator.ERROR_NONE)) {
			errors.add("expressedCondition", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", Integer.toString(remarksMaxLength)));
		}

		if (!(errorCode = Validator.checkString("" + aForm.getRealEstateRentalFlag(), false, 0, 1))
				.equals(Validator.ERROR_NONE)) {
			errors.add("realEstateRentalFlag",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "1"));
		}
		/*if (aForm.getIsPhysInsp() == null || "".equals(aForm.getIsPhysInsp())) {
			errors.add("isPhysInsp", new ActionMessage("error.string.mandatory"));
		}*/
		
		/*if (aForm.getIsPhysInsp_v1() == null || "".equals(aForm.getIsPhysInsp_v1())) {
			errors.add("isPhysInsp_v1", new ActionMessage("error.string.mandatory"));
		}*/
		
		if (aForm.getTsrDate() == null || "".equals(aForm.getTsrDate())) {
			errors.add("tsrDateError", new ActionMessage("error.string.mandatory"));
		} else {
			IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
			IGeneralParamEntry generalParamEntry = generalParamDao
					.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			String applicationDate = generalParamEntry.getParamValue();
			Date appDate = new Date();
			if (applicationDate != null) {
				appDate = new Date(applicationDate);
			}

			if (DateUtil.convertDate(locale, aForm.getTsrDate()).after(appDate)) {
				errors.add("tsrDate", new ActionMessage("error.future.date"));
			}
		}

		if (aForm.getTsrFrequency() == null || "".equals(aForm.getTsrFrequency())) {
			errors.add("tsrFrequencyError", new ActionMessage("error.string.mandatory"));
		}
		if (aForm.getNextTsrDate() == null || "".equals(aForm.getNextTsrDate())) {
			errors.add("nextTsrDateError", new ActionMessage("error.string.mandatory"));
		}
		if (aForm.getIsPhysInsp_v1() != null || !"".equals(aForm.getIsPhysInsp_v1())) {
			if ("true".equalsIgnoreCase(aForm.getIsPhysInsp_v1())) {
				boolean containsError = false;
				/*if (aForm.getPhysInspFreqUOM() == null || "".equals(aForm.getPhysInspFreqUOM())) {
					errors.add("physInspFreqUOM", new ActionMessage("error.string.mandatory"));
				}
				if (aForm.getDatePhyInspec() == null || "".equals(aForm.getDatePhyInspec())) {
					errors.add("datePhyInspec", new ActionMessage("error.string.mandatory"));
				}
				if (aForm.getNextPhysInspDate() == null || "".equals(aForm.getNextPhysInspDate())) {
					errors.add("nextPhysInspDateError", new ActionMessage("error.string.mandatory"));
				}
				if (!"".equals(aForm.getDatePhyInspec()) && !"".equals(aForm.getNextPhysInspDate())) {
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDatePhyInspec()))
							.after(DateUtil.convertDate(locale, aForm.getNextPhysInspDate()))) {
						errors.add("nextPhysInspDateError",
								new ActionMessage("error.date.compareDate", "This ", "Physical Verification Done on"));
					}
				}*/
				if (aForm.getPhysInspFreqUOM_v1() == null || "".equals(aForm.getPhysInspFreqUOM_v1())) {
					errors.add("physInspFreqUOM_v1", new ActionMessage("error.string.mandatory"));
				}
				if (aForm.getDatePhyInspec_v1() == null || "".equals(aForm.getDatePhyInspec_v1())) {
					errors.add("datePhyInspec_v1", new ActionMessage("error.string.mandatory"));
				}
				if (aForm.getNextPhysInspDate_v1() == null || "".equals(aForm.getNextPhysInspDate_v1())) {
					errors.add("nextPhysInspDateError_v1", new ActionMessage("error.string.mandatory"));
				}
				if (!"".equals(aForm.getDatePhyInspec_v1()) && !"".equals(aForm.getNextPhysInspDate_v1())) {
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDatePhyInspec_v1()))
							.after(DateUtil.convertDate(locale, aForm.getNextPhysInspDate_v1()))) {
						errors.add("nextPhysInspDateError_v1",
								new ActionMessage("error.date.compareDate", "This ", "Physical Verification Done on"));
					}
				}
				
			}
		}

		if (/*aForm.getCersiaRegistrationDate() != null ||*/ !("".equals(aForm.getCersiaRegistrationDate()))) {

			IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
			IGeneralParamEntry generalParamEntry = generalParamDao
					.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			String applicationDate = generalParamEntry.getParamValue();
			Date appDate = new Date();
			if (applicationDate != null) {
				appDate = new Date(applicationDate);
			}

			if (DateUtil.convertDate(locale, aForm.getCersiaRegistrationDate()).after(appDate)) {
				errors.add("cersiaDate", new ActionMessage("error.future.date"));
			}
		}
		
		//valuation 3 start
		
		if (!(errorCode = Validator.checkString(aForm.getRemarksProperty_v3(), false, 0, remarksMaxLength))
				.equals(Validator.ERROR_NONE)) {
			errors.add("remarksProperty_v3", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					Integer.toString(remarksMaxLength)));
		}
		
		if (aForm.getIsPhysInsp_v3() != null || !"".equals(aForm.getIsPhysInsp_v3())) {
			if ("true".equalsIgnoreCase(aForm.getIsPhysInsp_v3())) {
				boolean containsError = false;
				/*if (aForm.getPhysInspFreqUOM() == null || "".equals(aForm.getPhysInspFreqUOM())) {
					errors.add("physInspFreqUOM", new ActionMessage("error.string.mandatory"));
				}
				if (aForm.getDatePhyInspec() == null || "".equals(aForm.getDatePhyInspec())) {
					errors.add("datePhyInspec", new ActionMessage("error.string.mandatory"));
				}
				if (aForm.getNextPhysInspDate() == null || "".equals(aForm.getNextPhysInspDate())) {
					errors.add("nextPhysInspDateError", new ActionMessage("error.string.mandatory"));
				}
				if (!"".equals(aForm.getDatePhyInspec()) && !"".equals(aForm.getNextPhysInspDate())) {
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDatePhyInspec()))
							.after(DateUtil.convertDate(locale, aForm.getNextPhysInspDate()))) {
						errors.add("nextPhysInspDateError",
								new ActionMessage("error.date.compareDate", "This ", "Physical Verification Done on"));
					}
				}*/
				if (aForm.getPhysInspFreqUOM_v3() == null || "".equals(aForm.getPhysInspFreqUOM_v3())) {
					errors.add("physInspFreqUOM_v3", new ActionMessage("error.string.mandatory"));
				}
				if (aForm.getDatePhyInspec_v3() == null || "".equals(aForm.getDatePhyInspec_v3())) {
					errors.add("datePhyInspec_v3", new ActionMessage("error.string.mandatory"));
				}
				if (aForm.getNextPhysInspDate_v3() == null || "".equals(aForm.getNextPhysInspDate_v3())) {
					errors.add("nextPhysInspDateError_v3", new ActionMessage("error.string.mandatory"));
				}
				if (!"".equals(aForm.getDatePhyInspec_v3()) && !"".equals(aForm.getNextPhysInspDate_v3())) {
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDatePhyInspec_v3()))
							.after(DateUtil.convertDate(locale, aForm.getNextPhysInspDate_v3()))) {
						errors.add("nextPhysInspDateError_v3",
								new ActionMessage("error.date.compareDate", "This ", "Physical Verification Done on"));
					}
				}
				
			}
		}
		
		//Valuation 3 ends

		if (!(errorCode = Validator.checkString(aForm.getRemarksProperty_v2(), false, 0, remarksMaxLength))
				.equals(Validator.ERROR_NONE)) {
			errors.add("remarksProperty_v2", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					Integer.toString(remarksMaxLength)));
		}
		
		boolean isValuation2Mandatory = ICMSConstant.YES.equals(aForm.getValuation2Mandatory());	
		boolean isDeferralIdMandate = aForm.getDeferral() != null && aForm.getDeferral().trim().equals("on");
		boolean isWaiver = aForm.getWaiver() != null && aForm.getWaiver().trim().equals("on");
		if(isWaiver) {
			isValuation2Mandatory = false;
		}
		if(isDeferralIdMandate) {
			if(!AbstractCommonMapper.isEmptyOrNull(aForm.getDeferralId())){
				boolean isValid = false;
				for(String id : aForm.getDeferralIds().split(",")) {
					if(id.equals(aForm.getDeferralId())) {
						isValid = true;
						isValuation2Mandatory = false;
						break;
					}
				}
				
			}
		}
		if (aForm.getIsPhysInsp_v2() != null || !"".equals(aForm.getIsPhysInsp_v2())) {
			if ("true".equalsIgnoreCase(aForm.getIsPhysInsp_v2()) && isValuation2Mandatory) {
				if (aForm.getPhysInspFreqUOM_v2() == null || "".equals(aForm.getPhysInspFreqUOM_v2())) {
					errors.add("physInspFreqUOM_v2", new ActionMessage("error.string.mandatory"));
				}
				if (aForm.getDatePhyInspec_v2() == null || "".equals(aForm.getDatePhyInspec_v2())) {
					errors.add("datePhyInspec_v2", new ActionMessage("error.string.mandatory"));
				}
				if (aForm.getNextPhysInspDate_v2() == null || "".equals(aForm.getNextPhysInspDate_v2())) {
					errors.add("nextPhysInspDateError_v2", new ActionMessage("error.string.mandatory"));
				}
				if (!"".equals(aForm.getDatePhyInspec_v2()) && !"".equals(aForm.getNextPhysInspDate_v2())) {
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDatePhyInspec_v2()))
							.after(DateUtil.convertDate(locale, aForm.getNextPhysInspDate_v2()))) {
						errors.add("nextPhysInspDateError_v2",
								new ActionMessage("error.date.compareDate", "This ", "Physical Verification Done on"));
					}
				}
				
			}
		}
			
		PropertyValidationHelper.validateInput(aForm, locale, errors);

		return errors;
	}

	private static ActionErrors validateMandatoryFields(PropertyForm aForm, Locale locale, ActionErrors errors,
			boolean isMandatory) {
		String revalOverride="";
		revalOverride= (null == aForm.getRevalOverride()) ?"off" : aForm.getRevalOverride();
		 
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
		String errorCode = null;

		int addressLength = 40;
		Boolean totalPropAmountError_v1=false;
		if (!(errorCode = Validator.checkAmount(aForm.getSalePurchareAmount(), true, 1,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			if (aForm.getSalePurchareAmount() != null) {
				if (!aForm.getSalePurchareAmount().equals("")) {
					errors.add("salePurchareAmount",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1", maximumAmt));
				}

			}
		}

		/*
		 * Commented by Sandeep Shinde to remove Validation which is not
		 * required due to presence of new fields if (!(errorCode =
		 * Validator.checkString(aForm.getCategoryOfLandUse(), isMandatory, 0,
		 * 40)) .equals(Validator.ERROR_NONE)) { errors.add("categoryOfLandUse",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
		 * errorCode), "0", "40")); }
		 */

		if (!(errorCode = Validator.checkString(aForm.getPropertyType(), isMandatory, 0, 40))
				.equals(Validator.ERROR_NONE)) {
			errors.add("propertyType",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}

		// *********** Lines Added by Dattatray Thorat for Property - Commercial
		// Security

	/*	if (!(errorCode = Validator.checkString(aForm.getValuatorCompany(), isMandatory, 0, 30))
				.equals(Validator.ERROR_NONE)) {
			errors.add("valuatorCompany",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "30"));
		}*/
		
		if (!(errorCode = Validator.checkString(aForm.getValuatorCompany_v1(), isMandatory, 0, 30))
				.equals(Validator.ERROR_NONE)) {
			errors.add("valuatorCompany_v1",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "30"));
		}

		if (!(errorCode = Validator.checkString(aForm.getAdvocateLawyerName(), isMandatory, 0, 30))
				.equals(Validator.ERROR_NONE)) {
			errors.add("advocateLawyerName",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "30"));
		} else if (aForm.getAdvocateLawyerName().length() > 30) {
			errors.add("advocateLawyerName", new ActionMessage("error.string.length30"));
		}

		if (aForm.getMortageRegisteredRef() != null && !aForm.getMortageRegisteredRef().equals("")) {
			if (aForm.getMortageRegisteredRef().length() > 200) {
				errors.add("mortageRegisteredRef", new ActionMessage("error.string.length200"));
			} else {
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(aForm.getMortageRegisteredRef());
				if (codeFlag == true)
					errors.add("mortageRegisteredRef", new ActionMessage("error.pfLrdCreditMgrEmpId.error"));
			}
		}

		if (aForm.getPropertyLotLocation() != null && !aForm.getPropertyLotLocation().equals("")) {
			if (aForm.getPropertyLotLocation().length() > 30) {
				errors.add("propertyLotLocation", new ActionMessage("error.string.length30"));
			}
		}

		if (aForm.getOtherCity() != null && !aForm.getOtherCity().equals("")) {
			if (aForm.getOtherCity().length() > 30) {
				errors.add("otherCity", new ActionMessage("error.string.length30"));
			}
		}

		/*if (!(errorCode = Validator.checkString(aForm.getCountry(), isMandatory, 0, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("country", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}

		if (!(errorCode = Validator.checkString(aForm.getRegion(), isMandatory, 0, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("region", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}

		if (!(errorCode = Validator.checkString(aForm.getLocationState(), isMandatory, 0, 40))
				.equals(Validator.ERROR_NONE)) {
			errors.add("locationState",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}

		if (!(errorCode = Validator.checkString(aForm.getNearestCity(), isMandatory, 0, 40))
				.equals(Validator.ERROR_NONE)) {
			errors.add("nearestCity",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}*/

		if (!(errorCode = Validator.checkString(aForm.getCountry_v1(), isMandatory, 0, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("country_v1", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}

		if (!(errorCode = Validator.checkString(aForm.getRegion_v1(), isMandatory, 0, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("region_v1", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}

		if (!(errorCode = Validator.checkString(aForm.getLocationState_v1(), isMandatory, 0, 40))
				.equals(Validator.ERROR_NONE)) {
			errors.add("locationState_v1",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}

		if (!(errorCode = Validator.checkString(aForm.getNearestCity_v1(), isMandatory, 0, 40))
				.equals(Validator.ERROR_NONE)) {
			errors.add("nearestCity_v1",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}
		if (aForm.getBuiltupArea_v1() == null || aForm.getBuiltupArea_v1().equals("")) {
			errors.add("builtUpArea_v1", new ActionMessage("error.string.mandatory"));
		}
		/*if (aForm.getBuiltUpAreaUnit() == null || aForm.getBuiltUpAreaUnit().equals("")) {
			errors.add("builtUpAreaUomError", new ActionMessage("error.string.builtUpAreaUnit"));
		}*/
		
		if (aForm.getBuiltUpAreaUnit_v1() == null || aForm.getBuiltUpAreaUnit_v1().equals("")) {
			errors.add("builtUpAreaUomError_v1", new ActionMessage("error.string.builtUpAreaUnit"));
		}
		
		if (aForm.getLandUOM_v1() == null || aForm.getLandUOM_v1().equals("")) {
			errors.add("landAreaUomError_v1", new ActionMessage("error.string.landAreaUnit"));
		} else if (!(errorCode = Validator.checkNumber(aForm.getBuiltupArea_v1(), true, 1, COLLATERAL_MAX_LAND_AREA, 9,
				locale)).equals(Validator.ERROR_NONE)) {
			errors.add("builtUpArea_v1",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}

		if(!aForm.getEvent().equalsIgnoreCase("REST_UPDATE_PROPERTY_SECURITY"))
		{
		if (aForm.getTypeOfMargage() == null || aForm.getTypeOfMargage().equals("")) {
			errors.add("typeOfMargage", new ActionMessage("error.string.mandatory"));
		}
		}

	/*	if (aForm.getValuationDate() == null || aForm.getValuationDate().equals("")) {
			errors.add("valuationDate", new ActionMessage("error.string.mandatory"));
		} else {
			IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
			IGeneralParamEntry generalParamEntry = generalParamDao
					.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			String applicationDate = generalParamEntry.getParamValue();
			Date appDate = new Date();
			if (applicationDate != null) {
				appDate = new Date(applicationDate);
			}

			if (DateUtil.convertDate(locale, aForm.getValuationDate()).after(appDate)) {
				errors.add("valuationDate", new ActionMessage(FeedConstants.ERROR_FUTURE_DATE));
			}
		}*/
		
		if (aForm.getValuationDate_v1() == null || aForm.getValuationDate_v1().equals("")) {
			errors.add("valuationDate_v1", new ActionMessage("error.string.mandatory"));
		} else {
			IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
			IGeneralParamEntry generalParamEntry = generalParamDao
					.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			String applicationDate = generalParamEntry.getParamValue();
			Date appDate = new Date();
			if (applicationDate != null) {
				appDate = new Date(applicationDate);
			}

			if (DateUtil.convertDate(locale, aForm.getValuationDate_v1()).after(appDate)) {
				errors.add("valuationDate_v1", new ActionMessage("error.future.date"));
			}
		}

		if (aForm.getMorgageCreatedBy() == null || aForm.getMorgageCreatedBy().equals("")) {
			errors.add("morgageCreatedBy", new ActionMessage("error.string.mandatory"));
		}
		/*
		 * added by sachin :discription field should be mandatory
		 */
		if (!(errorCode = Validator.checkString(aForm.getDescription(), true, 0, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("description",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", Integer.toString(40)));
		}
		if(!aForm.getEvent().equalsIgnoreCase("REST_UPDATE_PROPERTY_SECURITY"))
		{
		if (!(errorCode = Validator.checkString(aForm.getSalePurchareAmount(), true, 0, 40))
				.equals(Validator.ERROR_NONE)) {
			errors.add("salePurchareAmount",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", Integer.toString(40)));
		}
		}
		if (!(errorCode = Validator.checkString(aForm.getDevGrpCo(), true, 0, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("devGrpCo",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", Integer.toString(40)));
		}
		if(!aForm.getEvent().equalsIgnoreCase("REST_UPDATE_PROPERTY_SECURITY"))
		{
		if (!(errorCode = Validator.checkString(aForm.getAmountCMV(), true, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("amountCMV",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
		}
		}
		if(aForm.getConstitution()== null || aForm.getConstitution().equals("")){
			errors.add("constitution", new ActionMessage("error.string.mandatory"));
		}
		/*
		 * commented By Sachin patil On 3rd jan 2012
		 */
		/*
		 * if(aForm.getDocumentReceived()== null ||
		 * aForm.getDocumentReceived().equals("")){
		 * errors.add("documentReceived", new
		 * ActionMessage("error.string.mandatory")); }
		 * 
		 * if(aForm.getDocumentBlock()== null ||
		 * aForm.getDocumentBlock().equals("")){ errors.add("documentBlock", new
		 * ActionMessage("error.string.mandatory")); }
		 */

		/*if (aForm.getPinCode() == null || aForm.getPinCode().equals("")) {
			errors.add("pincodeError", new ActionMessage("error.string.mandatory"));
		} else {
			IPincodeMappingDao pincodeDao = (IPincodeMappingDao) BeanHouse.get("pincodeMappingDao");
			if (pincodeDao != null && aForm.getLocationState() != null) {

				String stateId = aForm.getLocationState();
				String pincode = aForm.getPinCode();
				if (pincode.length() > 1 || pincode.length() == 1) {
					pincode = pincode.substring(0, 1);
					if (stateId != null && !pincodeDao.isPincodeMappingValid(pincode, stateId)) {
						errors.add("postcodeError", new ActionMessage("error.string.invalidMapping"));
					}
				} else {
					errors.add("postcodeError", new ActionMessage("error.string.invalidMapping"));
				}
			}

			if (aForm.getPinCode().length() > 6)
				errors.add("pinCode", new ActionMessage("error.string.length6"));

			else if (!(errorCode = Validator.checkInteger(aForm.getPinCode(), true, 1, 999999))
					.equals(Validator.ERROR_NONE)) {
				errors.add("pinCode",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "1", "999999"));
			}

			else {
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(aForm.getPinCode());
				if (codeFlag == true)
					errors.add("pinCode", new ActionMessage("error.string.invalidCharacter"));
			}

		}*/
		
		/*
		 * if (aForm.getPinCode_v1() == null || aForm.getPinCode_v1().equals("")) {
		 * errors.add("pincodeError_v1", new ActionMessage("error.string.mandatory")); }
		 * else { IPincodeMappingDao pincodeDao = (IPincodeMappingDao)
		 * BeanHouse.get("pincodeMappingDao"); if (pincodeDao != null &&
		 * aForm.getLocationState_v1() != null &&
		 * !aForm.getLocationState_v1().isEmpty()) {
		 * 
		 * String stateId = aForm.getLocationState_v1(); String pincode =
		 * aForm.getPinCode_v1(); if (pincode.length() > 1 || pincode.length() == 1) {
		 * pincode = pincode.substring(0, 1); if (stateId != null &&
		 * !pincodeDao.isPincodeMappingValid(pincode, stateId)) {
		 * errors.add("postcodeError_v1", new
		 * ActionMessage("error.string.invalidMapping")); } } else {
		 * errors.add("postcodeError_v1", new
		 * ActionMessage("error.string.invalidMapping")); } }
		 * 
		 * if (aForm.getPinCode_v1().length() > 6) errors.add("pinCode_v1", new
		 * ActionMessage("error.string.length6"));
		 * 
		 * else if (!(errorCode = Validator.checkInteger(aForm.getPinCode_v1(), true, 1,
		 * 999999)) .equals(Validator.ERROR_NONE)) { errors.add("pinCode_v1", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "1",
		 * "999999")); }
		 * 
		 * else { boolean codeFlag =
		 * ASSTValidator.isValidAlphaNumStringWithoutSpace(aForm.getPinCode_v1()); if
		 * (codeFlag == true) errors.add("pinCode_v1", new
		 * ActionMessage("error.string.invalidCharacter")); }
		 * 
		 * }
		 */

		if (aForm.getPropertyAddress() == null || aForm.getPropertyAddress().equals("")) {
			errors.add("propertyAddress", new ActionMessage("error.string.mandatory"));
		}

		// Added by Pramod Katkar for New Filed CR on 12-08-2013
		if ("Y".equalsIgnoreCase(aForm.getClaim())) {
			if (aForm.getClaimType() == null || aForm.getClaimType().equals("")) {
				errors.add("claimType", new ActionMessage("error.string.mandatory"));
			}
		}
		// End by Pramod Katkar
		if (aForm.getBinNumber() != null && !aForm.getBinNumber().equals("")) {
			if (aForm.getBinNumber().length() > 10)
				errors.add("binNumber", new ActionMessage("error.string.length10"));
		}

/*		if (!(errorCode = Validator.checkAmount(aForm.getTotalPropertyAmount(), true, 1,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("totalPropertyAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
		}
		if (!(errorCode = Validator.checkAmount(aForm.getLandArea(), true, 1,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_19_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("landAreaLengthError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_19_2_STR));
		}
		if (!(errorCode = Validator.checkAmount(aForm.getBuiltUpArea(), true, 1,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_19_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("builtupAreaLengthError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_19_2_STR));
		}*/
		/*if(!aForm.getEvent().equalsIgnoreCase("REST_UPDATE_PROPERTY_SECURITY"))
		{*/
		if (!(errorCode = Validator.checkAmount(aForm.getTotalPropertyAmount_v1(), true, 1,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("totalPropertyAmount_v1", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
			totalPropAmountError_v1 =true;
		/*}*/
		}
		if (!(errorCode = Validator.checkAmount(aForm.getLandArea_v1(), true, 1,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_19_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("landAreaLengthError_v1", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_19_2_STR));
		}
		if (!(errorCode = Validator.checkAmount(aForm.getBuiltupArea_v1(), true, 1,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_19_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("builtupAreaLengthError_v1", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_19_2_STR));
		}
		/*
		 * Commented by Sandeep Shinde to remove Validation which is not
		 * required due to presence of new fields if (!(errorCode =
		 * Validator.checkString(aForm.getLocationDistrict(), isMandatory, 0,
		 * 40)) .equals(Validator.ERROR_NONE)) { errors.add("locationDistrict",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
		 * errorCode), "0", "40")); }
		 */

		/*
		 * Commented by Sandeep Shinde to remove Validation which is not
		 * required due to presence of new fields if (!(errorCode =
		 * Validator.checkString(aForm.getLocationMukim(), isMandatory, 0, 40))
		 * .equals(Validator.ERROR_NONE)) { errors.add("locationMukim", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", "40")); }
		 */

		if (!(errorCode = Validator.checkInteger(aForm.getPostCode(), false, 0, 999999999))
				.equals(Validator.ERROR_NONE)) {
			errors.add("postCode",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), "0", "999999999"));
		}

		/*
		 * if (!(errorCode = Validator.checkString(aForm.getPostCode(),
		 * isMandatory, 0, 15)).equals(Validator.ERROR_NONE)) {
		 * errors.add("postCode", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 15 + "")); }
		 */
		String subTypeCode1 = (aForm.getSubTypeCode() == null) ? "" : aForm.getSubTypeCode();
		boolean isMandatoryForSubType_v1 = false;
		if ((isMandatory && (subTypeCode1.equals(ICMSConstant.COLTYPE_PROP_AGRICULTURAL)
				|| subTypeCode1.equals(ICMSConstant.COLTYPE_PROP_LAND_URBAN)))
				|| (StringUtils.isNotBlank(aForm.getLandUOM_v1()) && isMandatory)) {
			isMandatoryForSubType_v1 = true;
		}
		boolean isMandatoryForLandUOM_v1 = false;
		if (isMandatory && (StringUtils.isNotBlank(aForm.getLandArea_v1()))) {
			isMandatoryForLandUOM_v1 = true;
		}

		if(!"REST_UPDATE_PROPERTY_SECURITY".equalsIgnoreCase(aForm.getEvent())) {
		if (!(errorCode = Validator.checkNumber(aForm.getLandArea_v1(), isMandatoryForSubType_v1, 0.01,
				COLLATERAL_MAX_LAND_AREA, 9, locale)).equals(Validator.ERROR_NONE)) {
			String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
			if (errorMessage.equals("error.number.decimalexceeded")) {
				errorMessage = "error.number.moredecimalexceeded";
			}
			errors.add("landArea_v1", new ActionMessage(errorMessage, "0.01", COLLATERAL_MAX_LAND_AREA_STR, "8"));
		} else if (!(errorCode = Validator.checkString(aForm.getLandUOM_v1(), isMandatoryForLandUOM_v1, 1, 15))
				.equals(Validator.ERROR_NONE)) {
			errors.add("landUOM_v1",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 15 + ""));
		}

		}
		
		
		
		
		if (null != aForm.getLandArea_v1()
				&& !aForm.getLandArea_v1().trim().isEmpty()) {
			if (null == aForm.getLandUOM_v1()
					|| aForm.getLandUOM_v1().trim().isEmpty()) {
				errors.add("landUOM_v1",
						new ActionMessage("error.string.mandatory"));
			}
		}
		if (null != aForm.getLandUOM_v1()
				&& !aForm.getLandUOM_v1().trim().isEmpty()) {
			if (null == aForm.getLandArea_v1()
					|| aForm.getLandArea_v1().trim().isEmpty()) {
				errors.add("landArea_v1",
						new ActionMessage("error.string.mandatory"));
			}
		}
		
		/*
		 * if (!(aForm.getEvent().equals("approve") ||
		 * aForm.getEvent().equals("reject"))) { if (!(errorCode =
		 * Validator.checkNumber(aForm.getLandArea(), false, 1,
		 * COLLATERAL_MAX_LAND_AREA, 3, locale)) .equals(Validator.ERROR_NONE))
		 * { errors.add("landArea", new
		 * ActionMessage("error.number.moredecimalexceeded", "1",
		 * "999999999999999", "2")); } }
		 */

		/*
		 * if (!(subTypeCode1.equals(ICMSConstant.COLTYPE_PROP_AGRICULTURAL))) {
		 * if (!(errorCode = Validator.checkString(aForm.getDeveloperName(),
		 * isMandatory, 0, 150)) .equals(Validator.ERROR_NONE)) {
		 * errors.add("developerName", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", "150")); } }
		 */

		String subTypeCode = aForm.getSubTypeCode();
		/*
		 * if (!ICMSConstant.COLTYPE_PROP_LAND_VACANT.equals(subTypeCode)) { if
		 * (!(errorCode = Validator.checkNumber(aForm.getBuiltUpArea(),
		 * isMandatory, 1, 999999)) .equals(Validator.ERROR_NONE)) {
		 * errors.add("builtUpArea", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		 * "0", "999,999")); }
		 * 
		 * if (!(errorCode = Validator.checkString(aForm.getBuiltUpAreaUnit(),
		 * isMandatory, 1, 15)) .equals(Validator.ERROR_NONE)) {
		 * errors.add("builtUpAreaUnit", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 15 + "")); } }
		 */
		/*if (!(subTypeCode1.equals(ICMSConstant.COLTYPE_PROP_AGRICULTURAL)
				|| subTypeCode1.equals(ICMSConstant.COLTYPE_PROP_LAND_URBAN))) {
			String propertyCompletionStatus = "" + aForm.getPropertyCompletionStatus();
			if (propertyCompletionStatus.equals("C") || propertyCompletionStatus.trim().equals("F")) {
				if (!(errorCode = Validator.checkNumber(aForm.getBuiltUpArea(), isMandatory, 1,
						COLLATERAL_MAX_BUILTUP_AREA, 7, locale)).equals(Validator.ERROR_NONE)) {
					String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
					if (errorMessage.equals("error.number.decimalexceeded")) {
						errorMessage = "error.number.moredecimalexceeded";
					}
					errors.add("builtUpArea",
							new ActionMessage(errorMessage, "1", COLLATERAL_MAX_BUILTUP_AREA_STR, "6"));
				} else if (!(errorCode = Validator.checkString(aForm.getBuiltUpAreaUnit(), isMandatory, 1, 15))
						.equals(Validator.ERROR_NONE)) {
					errors.add("builtUpAreaUnit",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 15 + ""));
				}
			}

		}*/
		
		if (!(subTypeCode1.equals(ICMSConstant.COLTYPE_PROP_AGRICULTURAL)
				|| subTypeCode1.equals(ICMSConstant.COLTYPE_PROP_LAND_URBAN))) {
			String propertyCompletionStatus_v1 = "" + aForm.getPropertyCompletionStatus_v1();
			if (propertyCompletionStatus_v1.equals("C") || propertyCompletionStatus_v1.trim().equals("F")) {
				if (!(errorCode = Validator.checkNumber(aForm.getBuiltupArea_v1(), isMandatory, 1,
						COLLATERAL_MAX_BUILTUP_AREA, 7, locale)).equals(Validator.ERROR_NONE)) {
					String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
					if (errorMessage.equals("error.number.decimalexceeded")) {
						errorMessage = "error.number.moredecimalexceeded";
					}
					errors.add("builtUpArea_v1",
							new ActionMessage(errorMessage, "1", COLLATERAL_MAX_BUILTUP_AREA_STR, "6"));
				} else if (!(errorCode = Validator.checkString(aForm.getBuiltUpAreaUnit_v1(), isMandatory, 1, 15))
						.equals(Validator.ERROR_NONE)) {
					errors.add("builtUpAreaUnit_v1",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 15 + ""));
				}
			}

		}

		/*
		 * Commented by Sandeep Shinde to remove Validation which is not
		 * required due to presence of new fields if (!(errorCode =
		 * Validator.checkString(aForm.getTenure(), isMandatory, 0,
		 * 16)).equals(Validator.ERROR_NONE)) { errors.add("tenure", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 16 + "")); }
		 */

		if (ICMSUIConstant.PROPERTY_TENURE_LEASEHOLD.equals(aForm.getTenure())) {
			if (!(errorCode = Validator.checkNumber(aForm.getTenureYear(), isMandatory, 1, 999999))
					.equals(Validator.ERROR_NONE)) {
				errors.add("tenureYear",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "1", 999999 + ""));
			}
		} else {
			if ((aForm.getTenureYear() != null) && !aForm.getTenureYear().trim().equals("")) {
				errors.add("tenureYear", new ActionMessage("error.string.empty"));
			}
		}

		if (ICMSUIConstant.PROPERTY_TENURE_LEASEHOLD.equals(aForm.getTenure())) {
			if (!(errorCode = Validator.checkDate(aForm.getCollateralMaturityDate(), isMandatory, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("collateralMaturityDate", new ActionMessage("error.date.mandatory", "1", "256"));
			}
		}

		/*
		 * Commented by Sandeep Shinde to remove Validation which is not
		 * required due to presence of new fields if (!(errorCode =
		 * Validator.checkString(aForm.getTitleType(), isMandatory, 0,
		 * 15)).equals(Validator.ERROR_NONE)) { errors.add("titleType", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 15 + "")); }
		 */

		/*
		 * Commented by Sandeep Shinde to remove Validation which is not
		 * required due to presence of new fields if (!(errorCode =
		 * Validator.checkString(aForm.getTitleNumberPrefix(), isMandatory, 0,
		 * 10)) .equals(Validator.ERROR_NONE)) { errors.add("titleNumberPrefix",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
		 * errorCode), "0", 10 + "")); }
		 */

		// int applicableLengthForTitleNumber = (aForm.getTitleNumberPrefix() ==
		// null) ? 100 : (100 - aForm
		// .getTitleNumberPrefix().length());
		/*
		 * Commented by Sandeep Shinde to remove Validation which is not
		 * required due to presence of new fields int
		 * applicableLengthForTitleNumber = 100; if (!(errorCode =
		 * Validator.checkString(aForm.getTitleNo(), isMandatory, 0,
		 * applicableLengthForTitleNumber)) .equals(Validator.ERROR_NONE)) {
		 * errors.add("titleNo", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", String .valueOf(applicableLengthForTitleNumber))); }
		 */

		String lotNo = aForm.getLotNo() == null ? "" : aForm.getLotNo();

		if (!(errorCode = Validator.checkString(aForm.getLotNumberPrefix(), isMandatory && lotNo.length() > 0, 0, 15))
				.equals(Validator.ERROR_NONE)) {
			errors.add("lotNumberPrefix",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 15 + ""));
		}

		if (!(errorCode = Validator.checkString(aForm.getLotNo(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("lotNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "50"));
		}

		if (!(errorCode = Validator.checkString(aForm.getSectionNo(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("sectionNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "50"));
		}

		String projectName = aForm.getProjectName() == null ? "" : aForm.getProjectName().trim();
		/*
		 * Commented by Sandeep Shinde to remove Validation which is not
		 * required due to presence of new fields if (!(errorCode =
		 * Validator.checkString(aForm.getPhaseNo(), isMandatory &&
		 * projectName.length() > 0, 0, 20)) .equals(Validator.ERROR_NONE)) {
		 * errors .add("phaseNo", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 20 + "")); }
		 */

		String stdQuitRent = aForm.getStdQuitRent() == null ? "" : aForm.getStdQuitRent();
		if (!(errorCode = Validator.checkAmount(aForm.getQuitRentAmount(),
				isMandatory && stdQuitRent.equals(ICMSConstant.TRUE_VALUE), 1, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
				IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("quitRentAmount",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1", maximumAmt));
		}

		String assessment = aForm.getAssessment() == null ? "" : aForm.getAssessment();
		if (!(errorCode = Validator.checkAmount(aForm.getAssessmentRate(),
				isMandatory && assessment.equals(ICMSConstant.TRUE_VALUE), 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
				IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("assessmentRate",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0", maximumAmt));
		}

		String exchangeControl = aForm.getExchangeControl() == null ? "" : aForm.getExchangeControl();
		if (!(errorCode = Validator.checkDate(aForm.getExchangeControlDate(),
				isMandatory && exchangeControl.equals(ICMSConstant.TRUE_VALUE), locale)).equals(Validator.ERROR_NONE)) {
			errors.add("exchangeControlDate", new ActionMessage("error.date.mandatory", "1", "256"));
		}

		/*
		 * String le = aForm.getLe() == null?"":aForm.getLe() ;
		 * if(le.equals(ICMSUIConstant.PROPERTY_QUIT_RENT_YES)){ if (!(errorCode
		 * = Validator.checkDate(aForm.getLeDate(), isMandatory, locale))
		 * .equals(Validator.ERROR_NONE)) { errors.add("leDate", new
		 * ActionMessage("error.date.mandatory", "1", "256")); } }
		 */

		String masterTitle = aForm.getMasterTitle() == null ? "" : aForm.getMasterTitle();
		if (!(errorCode = Validator.checkString(aForm.getMasterTitleNo(),
				isMandatory && masterTitle.equals(ICMSConstant.FALSE_VALUE), 0, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("masterTitleNo",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 20 + ""));
		}

		boolean tmpFlag = isMandatory;
		if ("FR".equalsIgnoreCase((aForm.getTenure()))) {
			tmpFlag = false;
		}

		tmpFlag = isMandatory;
		if ("NA".equalsIgnoreCase(aForm.getSecEnvRisky()) || "N".equalsIgnoreCase(aForm.getSecEnvRisky())) {
			tmpFlag = false;
		}

		if ((null != aForm.getSecEnvRisky()) && YES.equals(aForm.getSecEnvRisky().trim())) {
			if (!(errorCode = Validator.checkDate(aForm.getDateSecurityEnv(), true, locale))
					.equals(Validator.ERROR_NONE)) {
				if ("REST_UPDATE_PROPERTY_SECURITY".equalsIgnoreCase(aForm.getEvent()))
					errors.add("envRiskyDate", new ActionMessage("error.date.mandatory"));

				else
					errors.add("dateSecurityEnv", new ActionMessage("error.date.mandatory", "1", "256"));
				
				DefaultLogger.debug(LOGOBJ, " dateSecurityEnv is mandatory= " + aForm.getDateSecurityEnv());
			}
		} else {
			if (!(errorCode = Validator.checkDate(aForm.getDateSecurityEnv(), false, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("dateSecurityEnv", new ActionMessage("error.date.mandatory", "1", "256"));
				DefaultLogger.debug(LOGOBJ, " Not valid date, dateSecurityEnv() = " + aForm.getDateSecurityEnv());
			}
		}

		// Legal Enforceability
		if (!(errorCode = Validator.checkString(aForm.getPropertyUsage(), false, 0, 25)).equals(Validator.ERROR_NONE)) {
			errors.add("propertyUsage",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "25"));
		}
		/*
		 * Commented by Sandeep Shinde to remove Validation which is not
		 * required due to presence of new fields if (!(errorCode =
		 * Validator.checkString("" + aForm.getPropertyCompletionStatus(),
		 * isMandatory, 0, 1)) .equals(Validator.ERROR_NONE)) {
		 * errors.add("propertyCompletionStatus", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", "1")); }
		 */
		/*
		 * Commented by Sandeep Shinde because if Property Completion Status is
		 * N (No), then validation error is thrown. String bb = "" +
		 * aForm.getPropertyCompletionStatus(); if (bb.trim().equals("N")) { if
		 * (!(errorCode = Validator.checkString("" +
		 * aForm.getPropertyCompletionStage(), isMandatory, 0, 1))
		 * .equals(Validator.ERROR_NONE)) {
		 * errors.add("propertyCompletionStage", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", "1")); } }
		 */

		/*
		 * Commented by Sandeep Shinde to remove Validation which is not
		 * required due to presence of new fields if (!(errorCode =
		 * Validator.checkString("" + aForm.getAbandonedProject(), isMandatory,
		 * 0, 1)) .equals(Validator.ERROR_NONE)) {
		 * errors.add("abandonedProject", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", "1")); }
		 */

		if (!(errorCode = Validator.checkString(aForm.getPropertyAddress(), false, 0, 100))
				.equals(Validator.ERROR_NONE)) {
			errors.add("propertyAddress",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "100"));
		}

		if (!(errorCode = Validator.checkString(aForm.getPropertyAddress2(), false, 0, 100))
				.equals(Validator.ERROR_NONE)) {
			errors.add("propertyAddress2",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "100"));
		}

		if (!(errorCode = Validator.checkString(aForm.getPropertyAddress3(), false, 0, 100))
				.equals(Validator.ERROR_NONE)) {
			errors.add("propertyAddress3",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "100"));
		}

		if (!(errorCode = Validator.checkString(aForm.getPropertyAddress4(), false, 0, 100))
				.equals(Validator.ERROR_NONE)) {
			errors.add("propertyAddress4",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "100"));
		}

		if (!(errorCode = Validator.checkString(aForm.getPropertyAddress5(), false, 0, 100))
				.equals(Validator.ERROR_NONE)) {
			errors.add("propertyAddress5",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "100"));
		}

		if (!(errorCode = Validator.checkString(aForm.getPropertyAddress6(), false, 0, 100))
				.equals(Validator.ERROR_NONE)) {
			errors.add("propertyAddress6",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "100"));
		}

		if (!(errorCode = Validator.checkString(aForm.getPropertyId(), false, 0, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("propertyId",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "20"));
		} else {
			boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(aForm.getPropertyId());
			if (codeFlag == true)
				errors.add("propertyId", new ActionMessage("error.string.invalidCharacter"));
		}
		/*
		 * Commented by Sandeep Shinde to remove Validation which is not
		 * required due to presence of new fields if (!(errorCode =
		 * Validator.checkString("" + aForm.getIndependentValuerFlag(),
		 * isMandatory, 0, 1)) .equals(Validator.ERROR_NONE)) {
		 * errors.add("independentValuerFlag", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", "1")); }
		 */

		if (!(errorCode = Validator.checkString("" + aForm.getStdQuitRent(), isMandatory, 0, 1))
				.equals(Validator.ERROR_NONE)) {
			errors.add("stdQuitRent",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "1"));
		}

		if (!(errorCode = Validator.checkNumber(aForm.getAssessmentPeriod(), false, 0, 9999, 0, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("assessmentPeriod",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "6"));
		} else {
			if (StringUtils.isNotBlank(aForm.getAssessmentPeriod()) && aForm.getAssessmentPeriod().indexOf(",") >= 0) {
				errors.add("assessmentPeriod", new ActionMessage("error.number.format"));
			}
		}

		if (!(errorCode = Validator.checkAmount(aForm.getAuctionPrice(), false, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("auctionPrice",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0", maximumAmt));
		}

		if (!(errorCode = Validator.checkAmount(aForm.getUnitPrice(), false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
				IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("unitPrice",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0", maximumAmt));
		}

		if (!(errorCode = Validator.checkAmount(aForm.getAmountRedeem(), false, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("amountRedeem",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0", maximumAmt));
		}

//		if (AbstractCommonMapper.isEmptyOrNull(aForm.getIsPhysInsp()))
//			errors.add("isPhysInsp", new ActionMessage("error.mandatory"));
		
		if (AbstractCommonMapper.isEmptyOrNull(aForm.getIsPhysInsp_v1()))
			errors.add("isPhysInsp_v1", new ActionMessage("error.mandatory"));

		else if (aForm.getIsPhysInsp_v1().equals("true")) {
			/*
			 * Commented by Sandeep Shinde as removing validation for physical
			 * inception boolean containsError = false; if (!(errorCode =
			 * Validator.checkInteger(aForm.getPhysInspFreq(), isMandatory, 0,
			 * 99)) .equals(Validator.ERROR_NONE)) { errors.add("physInspFreq",
			 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER,
			 * errorCode), "0", 99 + "")); containsError = true; } if
			 * (!containsError && !(errorCode =
			 * Validator.checkString(aForm.getPhysInspFreqUOM(), isMandatory, 0,
			 * 256)) .equals(Validator.ERROR_NONE)) { errors.add("physInspFreq",
			 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
			 * errorCode), "0", 256 + "")); }
			 */

		}
		
		//Start Santosh 
		/*String label=(String)CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.CATEGORY_PROP_TYPE).get("hgf");
		if(CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.CATEGORY_PROP_TYPE).keySet().contains("jghdgf"))*/
		
		//System.out.println("######>>>>>>>>>"+aForm.getPropertyTypeLabel());	
		
		//if(aForm.getPropertyTypeLabel().contains("LAND"))
			//System.out.println("<<<<<<<<<>>>>>>>>>"+aForm.getPropertyTypeLabel());	
		
		if (aForm.getLandValue_v1() == null || aForm.getLandValue_v1().equals("0") && (aForm.getPropertyTypeLabel().contains("LAND"))){
			System.out.println("00000000000000000000000  Land    mandatory");
			errors.add("landValue_v1", new ActionMessage("error.string.mandatory"));
		}
		
		if (aForm.getBuildingValue_v1() == null || aForm.getBuildingValue_v1().equals("0") && (!aForm.getPropertyTypeLabel().contains("LAND"))) {
			errors.add("buildingValue_v1", new ActionMessage("error.string.mandatory"));
		}
		
		if (aForm.getReconstructionValueOfTheBuilding_v1() == null || aForm.getReconstructionValueOfTheBuilding_v1().equals("0") && (!aForm.getPropertyTypeLabel().contains("LAND"))) {
			errors.add("reconstructionValueOfTheBuilding_v1", new ActionMessage("error.string.mandatory"));
		}
		
		if (!(errorCode = Validator.checkAmount(aForm.getLandValue_v1(), true, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			System.out.println("######<<<<LAND>>>>"+aForm.getLandValue_v1());
			errors.add("landValue_v1", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2_STR));
		}
		
		if (!(errorCode = Validator.checkAmount(aForm.getBuildingValue_v1(), true, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			System.out.println("######<<<<LAND>>>>"+aForm.getBuildingValue_v1());
			errors.add("buildingValue_v1", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2_STR));
		}
		
		if (!(errorCode = Validator.checkAmount(aForm.getReconstructionValueOfTheBuilding_v1(), true, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("reconstructionValueOfTheBuilding_v1", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2_STR));
		}
		//End Santosh

		/*
		 * Following Else-if added by Sandeep Shinde for validation of Inception
		 * Date, which is supposed to be null if Physical Inception is False
		 */
		
		if(AbstractCommonMapper.isEmptyOrNull(aForm.getLegalAuditDate()) &&
				!AbstractCommonMapper.isEmptyOrNull(aForm.getSalePurchaseDate()) 
				&& !AbstractCommonMapper.isEmptyOrNull(aForm.getSalePurchaseDate_())
				&& !aForm.getSalePurchaseDate().equals(aForm.getSalePurchaseDate_())) {
		//	Date d1 = DateUtil.clearTime(new Date());
			Date d1 = DateUtil.convertDate(aForm.getSalePurchaseDate());
			Date d2 = DateUtil.convertDate(aForm.getSalePurchaseDate_());
			double duration = (d1.getTime() - d2.getTime()) / (1000d * 60 * 60 * 24 * 365);
			if(duration > 3.0028 && "off".contentEquals(revalOverride) ) {
				errors.add("legalAuditDate", new ActionMessage("error.string.mandatory"));
			}
		}
		
		
		//on click of ADD make 7 field mandatory.
		if("true".equals(aForm.getMortgageCreationAdd())){
			if (aForm.getInterveingPeriSearchDate() == null  ||  "".equals(aForm.getInterveingPeriSearchDate())) {
				errors.add("interveingPeriSearchDate", new ActionMessage("error.string.mandatory"));
			}
			//typeOfMargage is already mandatory for all cases
		}
		
		boolean isValuation3Mandatory = !AbstractCommonMapper.isEmptyOrNull(aForm.getValcreationdate_v3()) ||
				!AbstractCommonMapper.isEmptyOrNull(aForm.getValuationDate_v3()) || !AbstractCommonMapper.
				isEmptyOrNull(aForm.getValuatorCompany_v3()) || !AbstractCommonMapper.isEmptyOrNull(aForm.getCountry_v3())
				|| !AbstractCommonMapper.isEmptyOrNull(aForm.getRegion_v3()) || !AbstractCommonMapper.isEmptyOrNull(aForm.getLocationState_v3())
				|| !AbstractCommonMapper.isEmptyOrNull(aForm.getNearestCity_v3()) || !AbstractCommonMapper.isEmptyOrNull(aForm.getPinCode_v3())
				|| !AbstractCommonMapper.isEmptyOrNull(aForm.getBuiltupArea_v3()) || (!AbstractCommonMapper.isEmptyOrNull(aForm.getLandValue_v3()) 
						&& !aForm.getLandValue_v3().equals("0")) || (!AbstractCommonMapper.isEmptyOrNull(aForm.getBuildingValue_v3()) 
								&& !aForm.getBuildingValue_v3().equals("0")) || (!AbstractCommonMapper.isEmptyOrNull(aForm.getReconstructionValueOfTheBuilding_v3())
										&& !aForm.getReconstructionValueOfTheBuilding_v3().equals("0"));
		
		//Valuation 3 start
		/*if(isValuation3Mandatory && (aForm.getValcreationdate_v3() == null || "".equals(aForm.getValcreationdate_v3()))) {
			errors.add("valCreationdate_v3", new ActionMessage("error.string.mandatory"));
		}*/
		if(isValuation3Mandatory)
		{
			if (null != aForm.getLandArea_v3()
					&& !aForm.getLandArea_v3().trim().isEmpty()) {
				if (null == aForm.getLandUOM_v3()
						|| aForm.getLandUOM_v3().trim().isEmpty()) {
					errors.add("landAreaUomError_v3",
							new ActionMessage("error.string.mandatory"));
				}
			}
			if (null != aForm.getLandUOM_v3()
					&& !aForm.getLandUOM_v3().trim().isEmpty()) {
				if (null == aForm.getLandArea_v3()
						|| aForm.getLandArea_v3().trim().isEmpty()) {
					errors.add("landArea_v3",
							new ActionMessage("error.string.mandatory"));
				}
			}
			
		}
		if (!(errorCode = Validator.checkString(aForm.getValuatorCompany_v3(), isValuation3Mandatory, 0, 30))
				.equals(Validator.ERROR_NONE)) {
			errors.add("valuatorCompany_v3",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "30"));
		}
		
		if (!(errorCode = Validator.checkString(aForm.getCountry_v3(), isValuation3Mandatory, 0, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("country_v3", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}

		if (!(errorCode = Validator.checkString(aForm.getRegion_v3(), isValuation3Mandatory, 0, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("region_v3", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}

		if (!(errorCode = Validator.checkString(aForm.getLocationState_v3(), isValuation3Mandatory, 0, 40))
				.equals(Validator.ERROR_NONE)) {
			errors.add("locationState_v3",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}

		if (!(errorCode = Validator.checkString(aForm.getNearestCity_v3(), isValuation3Mandatory, 0, 40))
				.equals(Validator.ERROR_NONE)) {
			errors.add("nearestCity_v3",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}
		if (isValuation3Mandatory && (aForm.getBuiltupArea_v3() == null || aForm.getBuiltupArea_v3().equals(""))) {
			errors.add("builtUpArea_v3", new ActionMessage("error.string.mandatory"));
		}
		if (isValuation3Mandatory && (aForm.getBuiltUpAreaUnit_v3() == null || aForm.getBuiltUpAreaUnit_v3().equals(""))) {
			errors.add("builtUpAreaUomError_v3", new ActionMessage("error.string.builtUpAreaUnit"));
		}
		
		if (isValuation3Mandatory && (aForm.getLandUOM_v3() == null || aForm.getLandUOM_v3().equals(""))) {
			errors.add("landAreaUomError_v3", new ActionMessage("error.string.landAreaUnit"));
		} else if (!(errorCode = Validator.checkNumber(aForm.getBuiltupArea_v3(), isValuation3Mandatory, 1, COLLATERAL_MAX_LAND_AREA, 9,
				locale)).equals(Validator.ERROR_NONE)) {
			errors.add("builtUpArea_v3",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}
		if (aForm.getValuationDate_v3() == null || "".equals(aForm.getValuationDate_v3())) {
			if(isValuation3Mandatory) {
				errors.add("valuationDate_v3", new ActionMessage("error.string.mandatory"));
			}
		} else {
			IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
			IGeneralParamEntry generalParamEntry = generalParamDao
					.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			String applicationDate = generalParamEntry.getParamValue();
			Date appDate = new Date();
			if (applicationDate != null) {
				appDate = new Date(applicationDate);
			}
			if (aForm.getValuationDate_v3() != null && !"".equals(aForm.getValuationDate_v3())) {
			if (DateUtil.convertDate(locale, aForm.getValuationDate_v3()).after(appDate)) {
				errors.add("valuationDate_v3", new ActionMessage("error.future.date"));
			}
			}
		}
		
		if (aForm.getPinCode_v3() == null || aForm.getPinCode_v3().equals("")) {
			if(isValuation3Mandatory) {
				errors.add("pincodeError_v3", new ActionMessage("error.string.mandatory"));
			}
		} else {
			IPincodeMappingDao pincodeDao = (IPincodeMappingDao) BeanHouse.get("pincodeMappingDao");
			if (pincodeDao != null && aForm.getLocationState_v3() != null && !aForm.getLocationState_v3().isEmpty()) {

				String stateId = aForm.getLocationState_v3();
				String pincode = aForm.getPinCode_v3();
				if (pincode.length() > 1 || pincode.length() == 1) {
					pincode = pincode.substring(0, 1);
					if (stateId != null && !pincodeDao.isPincodeMappingValid(pincode, stateId)) {
						errors.add("postcodeError_v3", new ActionMessage("error.string.invalidMapping"));
					}
				} else {
					errors.add("postcodeError_v3", new ActionMessage("error.string.invalidMapping"));
				}
			}

			if (aForm.getPinCode_v3().length() > 6)
				errors.add("pinCode_v3", new ActionMessage("error.string.length6"));

			else if (!(errorCode = Validator.checkInteger(aForm.getPinCode_v3(), true, 1, 999999))
					.equals(Validator.ERROR_NONE)) {
				errors.add("pinCode_v3",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "1", "999999"));
			}

			else {
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(aForm.getPinCode_v3());
				if (codeFlag == true)
					errors.add("pinCode_v3", new ActionMessage("error.string.invalidCharacter"));
			}

		}
		/*if(!aForm.getEvent().equalsIgnoreCase("REST_UPDATE_PROPERTY_SECURITY"))
		{*/
		if (!(errorCode = Validator.checkAmount(aForm.getTotalPropertyAmount_v3(), isValuation3Mandatory, 1,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("totalPropertyAmount_v3", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
		/*}*/
		}
		if (!(errorCode = Validator.checkAmount(aForm.getLandArea_v3(), isValuation3Mandatory, 1,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_19_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("landAreaLengthError_v3", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_19_2_STR));
		}
		if (!(errorCode = Validator.checkAmount(aForm.getBuiltupArea_v3(), isValuation3Mandatory, 1,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_19_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("builtupAreaLengthError_v3", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_19_2_STR));
		}
		
//		String subTypeCode1 = (aForm.getSubTypeCode() == null) ? "" : aForm.getSubTypeCode();
		boolean isMandatoryForSubType_v3 = false;
		if ((isMandatory && (subTypeCode1.equals(ICMSConstant.COLTYPE_PROP_AGRICULTURAL)
				|| subTypeCode1.equals(ICMSConstant.COLTYPE_PROP_LAND_URBAN)))
				|| (StringUtils.isNotBlank(aForm.getLandUOM_v3()) && isValuation3Mandatory)) {
			isMandatoryForSubType_v3 = true;
		}
		boolean isMandatoryForLandUOM_v3 = false;
		if (isValuation3Mandatory && (StringUtils.isNotBlank(aForm.getLandArea_v3()))) {
			isMandatoryForLandUOM_v3 = true;
		}
		if(!"REST_UPDATE_PROPERTY_SECURITY".equalsIgnoreCase(aForm.getEvent())) {
		if (!(errorCode = Validator.checkNumber(aForm.getLandArea_v3(), isMandatoryForSubType_v3, 0.01,
				COLLATERAL_MAX_LAND_AREA, 9, locale)).equals(Validator.ERROR_NONE)) {
			String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
			if (errorMessage.equals("error.number.decimalexceeded")) {
				errorMessage = "error.number.moredecimalexceeded";
			}
			errors.add("landArea_v3", new ActionMessage(errorMessage, "0.01", COLLATERAL_MAX_LAND_AREA_STR, "8"));
		} else if (!(errorCode = Validator.checkString(aForm.getLandUOM_v3(), isMandatoryForLandUOM_v3, 1, 15))
				.equals(Validator.ERROR_NONE)) {
			errors.add("landUOM_v3",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 15 + ""));
		}
	}
		
		if (!(subTypeCode1.equals(ICMSConstant.COLTYPE_PROP_AGRICULTURAL)
				|| subTypeCode1.equals(ICMSConstant.COLTYPE_PROP_LAND_URBAN))) {
			String propertyCompletionStatus_v3 = "" + aForm.getPropertyCompletionStatus_v3();
			if (propertyCompletionStatus_v3.equals("C") || propertyCompletionStatus_v3.trim().equals("F")) {
				if (!(errorCode = Validator.checkNumber(aForm.getBuiltupArea_v3(), isValuation3Mandatory, 1,
						COLLATERAL_MAX_BUILTUP_AREA, 7, locale)).equals(Validator.ERROR_NONE)) {
					String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
					if (errorMessage.equals("error.number.decimalexceeded")) {
						errorMessage = "error.number.moredecimalexceeded";
					}
					errors.add("builtUpArea_v3",
							new ActionMessage(errorMessage, "1", COLLATERAL_MAX_BUILTUP_AREA_STR, "6"));
				} else if (!(errorCode = Validator.checkString(aForm.getBuiltUpAreaUnit_v3(), isValuation3Mandatory, 1, 15))
						.equals(Validator.ERROR_NONE)) {
					errors.add("builtUpAreaUnit_v3",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 15 + ""));
				}
			}

		}
		if (isValuation3Mandatory && AbstractCommonMapper.isEmptyOrNull(aForm.getIsPhysInsp_v3()))
			errors.add("isPhysInsp_v3", new ActionMessage("error.mandatory"));

		/*else if (aForm.getIsPhysInsp_v3().equals("true")) {
			
			 * Commented by Sandeep Shinde as removing validation for physical
			 * inception boolean containsError = false; if (!(errorCode =
			 * Validator.checkInteger(aForm.getPhysInspFreq(), isMandatory, 0,
			 * 99)) .equals(Validator.ERROR_NONE)) { errors.add("physInspFreq",
			 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER,
			 * errorCode), "0", 99 + "")); containsError = true; } if
			 * (!containsError && !(errorCode =
			 * Validator.checkString(aForm.getPhysInspFreqUOM(), isMandatory, 0,
			 * 256)) .equals(Validator.ERROR_NONE)) { errors.add("physInspFreq",
			 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
			 * errorCode), "0", 256 + "")); }
			 

		}*/
		
		if (isValuation3Mandatory && (aForm.getLandValue_v3() == null || aForm.getLandValue_v3().equals("0") && (aForm.getPropertyTypeLabel().contains("LAND")))){
			System.out.println("00000000000000000000000  Land    mandatory");
			errors.add("landValue_v3", new ActionMessage("error.string.mandatory"));
		}
		
		if (isValuation3Mandatory && (aForm.getBuildingValue_v3() == null || aForm.getBuildingValue_v3().equals("0") && (!aForm.getPropertyTypeLabel().contains("LAND")))) {
			errors.add("buildingValue_v3", new ActionMessage("error.string.mandatory"));
		}
		
		if (isValuation3Mandatory && (aForm.getReconstructionValueOfTheBuilding_v3() == null || aForm.getReconstructionValueOfTheBuilding_v3().equals("0") && (!aForm.getPropertyTypeLabel().contains("LAND")))) {
			errors.add("reconstructionValueOfTheBuilding_v3", new ActionMessage("error.string.mandatory"));
		}
		
		if (!(errorCode = Validator.checkAmount(aForm.getLandValue_v3(), isValuation3Mandatory, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			System.out.println("######<<<<LAND>>>>"+aForm.getLandValue_v3());
			errors.add("landValue_v3", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2_STR));
		}
		
		if (!(errorCode = Validator.checkAmount(aForm.getBuildingValue_v3(), isValuation3Mandatory, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			System.out.println("######<<<<LAND>>>>"+aForm.getBuildingValue_v3());
			errors.add("buildingValue_v3", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2_STR));
		}
		
		if (!(errorCode = Validator.checkAmount(aForm.getReconstructionValueOfTheBuilding_v3(), isValuation3Mandatory, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("reconstructionValueOfTheBuilding_v3", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2_STR));
		}
		
		//Valuation 3 ends
		
		//validation if property details are not revaluated on or after 3 years on edit.
		Boolean valuation1NeedRevaluation=false;
		
		IGeneralParamDao generalParamDao1 = (IGeneralParamDao) BeanHouse.get("generalParamDao");
		IGeneralParamEntry generalParamEntry1 = generalParamDao1
				.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
		String applicationDate1 = generalParamEntry1.getParamValue();
		Date today = new Date();
		if (applicationDate1 != null) {
			today = new Date(applicationDate1);
		}
		
		DefaultLogger.debug(LOGOBJ, "today for 3 yr logic:"+today);
		if(null!=aForm.getValuationDate_v1()  && !"".equals(aForm.getValuationDate_v1())) {
		Date valCreDateV1 = DateUtil.convertDate(aForm.getValuationDate_v1());
	
	 System.out.println("SSSSSSS revalOverride:: "+revalOverride);
		long duration = (today.getTime() - valCreDateV1.getTime()) / (1000l * 60 * 60 * 24 * 365);
		if(duration > 3 && "off".equalsIgnoreCase(revalOverride)) {
			errors.add("valuation1NeedRevaluation", new ActionMessage("error.valuation1.needRevaluation"));
			valuation1NeedRevaluation=true;
		}
		}
		if(!totalPropAmountError_v1) {
		ICollateralDAO collDao = CollateralDAOFactory.getDAO();
		int count=collDao.getLimitIdByMapping(aForm.getCollateralIdProp());
		
		String partyIdAndGrade="";
		if(count>0) {
		 partyIdAndGrade = collDao.getPartyIdAndGrade(aForm.getCollateralIdProp());
		}else {
	    partyIdAndGrade = collDao.getPartyIdAndGradeFromStage(aForm.getCollateralIdProp());	
		}
		
		String valu2MandatoryAsPerMaster=ICMSConstant.NO;
		if(!partyIdAndGrade.isEmpty()) {
			String[] split = partyIdAndGrade.split("-");
			
		List<OBValuationAmountAndRating> valuationByRamRating = collDao.getValuationByRamRating(split[1]);
		String totalPropertyAmount_v1 = aForm.getTotalPropertyAmount_v1();
		if(null!=totalPropertyAmount_v1 && !totalPropertyAmount_v1.isEmpty()) {
			totalPropertyAmount_v1=totalPropertyAmount_v1.replaceAll(",", "");
		}
		valu2MandatoryAsPerMaster = collDao.isValuation2Mandatory(split[0], totalPropertyAmount_v1, valuationByRamRating);
		}
		
		aForm.setValuation2Mandatory(valu2MandatoryAsPerMaster);
	//	boolean isValuation2Mandatory = ICMSConstant.YES.equals(aForm.getValuation2Mandatory());
		boolean isValuation2Mandatory = ICMSConstant.YES.equals(valu2MandatoryAsPerMaster);	
		boolean isDeferralIdMandate = aForm.getDeferral() != null && aForm.getDeferral().trim().equals("on");
		boolean isWaiver = aForm.getWaiver() != null && aForm.getWaiver().trim().equals("on");
		if(isWaiver) {
			isValuation2Mandatory = false;
		}
		
		DefaultLogger.debug(LOGOBJ,"isDeferralIdMandate:"+isDeferralIdMandate);
		if(isDeferralIdMandate) {
			
			DefaultLogger.debug(LOGOBJ,"aForm.getDeferralId():"+aForm.getDeferralId()+" aForm.getDeferralIds():"+aForm.getDeferralIds());
			if(AbstractCommonMapper.isEmptyOrNull(aForm.getDeferralId())){
				errors.add("deferralId", new ActionMessage("error.string.mandatory"));
			} else {
				DefaultLogger.debug(LOGOBJ,"inside else");
				boolean isValid = false;
				for(String id : aForm.getDeferralIds().split(",")) {
					DefaultLogger.debug(LOGOBJ,"id:"+id);
					if(id.equals(aForm.getDeferralId())) {
						DefaultLogger.debug(LOGOBJ,"inside if aForm.getDeferralId():"+aForm.getDeferralId()+" isValid:"+isValid);
						isValid = true;
						isValuation2Mandatory = false;
						break;
					}
				}
				if(!isValid)
					errors.add("deferralId", new ActionMessage("error.property.valuation2.discrepency.type"));
			}
		}
		
		if(!isWaiver){
			if(isValuation2Mandatory)
			{
				if (null != aForm.getLandArea_v2()
						&& !aForm.getLandArea_v2().trim().isEmpty()) {
					if (null == aForm.getLandUOM_v2()
							|| aForm.getLandUOM_v2().trim().isEmpty()) {
						errors.add("landAreaUomError_v2",
								new ActionMessage("error.string.mandatory"));
					}
				}
				if (null != aForm.getLandUOM_v2()
						&& !aForm.getLandUOM_v2().trim().isEmpty()) {
					if (null == aForm.getLandArea_v2()
							|| aForm.getLandArea_v2().trim().isEmpty()) {
						errors.add("landArea_v2",
								new ActionMessage("error.string.mandatory"));
					}
				}
				
			}
		if (!(errorCode = Validator.checkString(aForm.getValuatorCompany_v2(), isValuation2Mandatory, 0, 30))
				.equals(Validator.ERROR_NONE)) {
			errors.add("valuatorCompany_v2",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "30"));
		}
		if (!(errorCode = Validator.checkString(aForm.getCountry_v2(), isValuation2Mandatory, 0, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("country_v2", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}

		if (!(errorCode = Validator.checkString(aForm.getRegion_v2(), isValuation2Mandatory, 0, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("region_v2", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}

		if (!(errorCode = Validator.checkString(aForm.getLocationState_v2(), isValuation2Mandatory, 0, 40))
				.equals(Validator.ERROR_NONE)) {
			errors.add("locationState_v2",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}

		if (!(errorCode = Validator.checkString(aForm.getNearestCity_v2(), isValuation2Mandatory, 0, 40))
				.equals(Validator.ERROR_NONE)) {
			errors.add("nearestCity_v2",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}
		if (isValuation2Mandatory && (aForm.getBuiltupArea_v2() == null || aForm.getBuiltupArea_v2().equals(""))) {
			errors.add("builtUpArea_v2", new ActionMessage("error.string.mandatory"));
		}
		if (isValuation2Mandatory && (aForm.getBuiltUpAreaUnit_v2() == null || aForm.getBuiltUpAreaUnit_v2().equals(""))) {
			errors.add("builtUpAreaUomError_v2", new ActionMessage("error.string.builtUpAreaUnit"));
		}
		
		if (isValuation2Mandatory && (aForm.getLandUOM_v2() == null || aForm.getLandUOM_v2().equals(""))) {
			errors.add("landAreaUomError_v2", new ActionMessage("error.string.landAreaUnit"));
		} else if (!(errorCode = Validator.checkNumber(aForm.getBuiltupArea_v2(), isValuation2Mandatory, 1, COLLATERAL_MAX_LAND_AREA, 9,
				locale)).equals(Validator.ERROR_NONE)) {
			errors.add("builtUpArea_v2",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "40"));
		}
		if (aForm.getValuationDate_v2() == null || aForm.getValuationDate_v2().equals("")) {
			if(isValuation2Mandatory)
				errors.add("valuationDate_v2", new ActionMessage("error.string.mandatory"));
		} else {
			IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
			IGeneralParamEntry generalParamEntry = generalParamDao
					.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			String applicationDate = generalParamEntry.getParamValue();
			Date appDate = new Date();
			if (applicationDate != null) {
				appDate = new Date(applicationDate);
			}

			if (DateUtil.convertDate(locale, aForm.getValuationDate_v2()).after(appDate)) {
				errors.add("valuationDate_v2", new ActionMessage("error.future.date"));
			}
		}
		/*
		 * if (aForm.getPinCode_v2() == null || aForm.getPinCode_v2().equals("")) {
		 * if(isValuation2Mandatory) errors.add("pincodeError_v2", new
		 * ActionMessage("error.string.mandatory")); } else { IPincodeMappingDao
		 * pincodeDao = (IPincodeMappingDao) BeanHouse.get("pincodeMappingDao"); if
		 * (pincodeDao != null && aForm.getLocationState_v2() != null &&
		 * !aForm.getLocationState_v2().isEmpty()) {
		 * 
		 * String stateId = aForm.getLocationState_v2(); String pincode =
		 * aForm.getPinCode_v2(); if (pincode.length() > 1 || pincode.length() == 1) {
		 * pincode = pincode.substring(0, 1); if (stateId != null &&
		 * !pincodeDao.isPincodeMappingValid(pincode, stateId)) {
		 * errors.add("postcodeError_v2", new
		 * ActionMessage("error.string.invalidMapping")); } } else {
		 * errors.add("postcodeError_v2", new
		 * ActionMessage("error.string.invalidMapping")); } }
		 * 
		 * if (aForm.getPinCode_v2().length() > 6) errors.add("pinCode_v2", new
		 * ActionMessage("error.string.length6"));
		 * 
		 * else if (!(errorCode = Validator.checkInteger(aForm.getPinCode_v2(), true, 1,
		 * 999999)) .equals(Validator.ERROR_NONE)) { errors.add("pinCode_v2", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "1",
		 * "999999")); }
		 * 
		 * else { boolean codeFlag =
		 * ASSTValidator.isValidAlphaNumStringWithoutSpace(aForm.getPinCode_v2()); if
		 * (codeFlag == true) errors.add("pinCode_v2", new
		 * ActionMessage("error.string.invalidCharacter")); }
		 * 
		 * }
		 */
		/*if(!aForm.getEvent().equalsIgnoreCase("REST_UPDATE_PROPERTY_SECURITY"))
		{*/
		if (!(errorCode = Validator.checkAmount(aForm.getTotalPropertyAmount_v2(), isValuation2Mandatory, 1,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("totalPropertyAmount_v2", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
		/*}*/
		}
		if (!(errorCode = Validator.checkAmount(aForm.getLandArea_v2(), isValuation2Mandatory, 1,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_19_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("landAreaLengthError_v2", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_19_2_STR));
		}
		if (!(errorCode = Validator.checkAmount(aForm.getBuiltupArea_v2(), isValuation2Mandatory, 1,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_19_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("builtupAreaLengthError_v2", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_19_2_STR));
		}
		
		boolean isMandatoryForSubType_v2 = false;
		if ((isValuation2Mandatory && (subTypeCode1.equals(ICMSConstant.COLTYPE_PROP_AGRICULTURAL)
				|| subTypeCode1.equals(ICMSConstant.COLTYPE_PROP_LAND_URBAN)))
				|| (StringUtils.isNotBlank(aForm.getLandUOM_v2()) && isMandatory)) {
			isMandatoryForSubType_v2 = true;
		}
		boolean isMandatoryForLandUOM_v2 = false;
		if (isValuation2Mandatory && (StringUtils.isNotBlank(aForm.getLandArea_v2()))) {
			isMandatoryForLandUOM_v2 = true;
		}
		
		
		if (!(errorCode = Validator.checkNumber(aForm.getLandArea_v2(), isMandatoryForSubType_v2, 0.01,
				COLLATERAL_MAX_LAND_AREA, 9, locale)).equals(Validator.ERROR_NONE)) {
			String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
			if (errorMessage.equals("error.number.decimalexceeded")) {
				errorMessage = "error.number.moredecimalexceeded";
			}
			errors.add("landArea_v2", new ActionMessage(errorMessage, "0.01", COLLATERAL_MAX_LAND_AREA_STR, "8"));
		} else if (!(errorCode = Validator.checkString(aForm.getLandUOM_v2(), isMandatoryForLandUOM_v2, 1, 15))
				.equals(Validator.ERROR_NONE)) {
			errors.add("landUOM_v2",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 15 + ""));
		}
		
			if (!(subTypeCode1.equals(ICMSConstant.COLTYPE_PROP_AGRICULTURAL)
				|| subTypeCode1.equals(ICMSConstant.COLTYPE_PROP_LAND_URBAN))) {
			String propertyCompletionStatus_v2 = "" + aForm.getPropertyCompletionStatus_v2();
			if (propertyCompletionStatus_v2.equals("C") || propertyCompletionStatus_v2.trim().equals("F")) {
				if (!(errorCode = Validator.checkNumber(aForm.getBuiltupArea_v2(), isValuation2Mandatory, 1,
						COLLATERAL_MAX_BUILTUP_AREA, 7, locale)).equals(Validator.ERROR_NONE)) {
					String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
					if (errorMessage.equals("error.number.decimalexceeded")) {
						errorMessage = "error.number.moredecimalexceeded";
					}
					errors.add("builtUpArea_v2",
							new ActionMessage(errorMessage, "1", COLLATERAL_MAX_BUILTUP_AREA_STR, "6"));
				} else if (!(errorCode = Validator.checkString(aForm.getBuiltUpAreaUnit_v2(), isValuation2Mandatory, 1, 15))
						.equals(Validator.ERROR_NONE)) {
					errors.add("builtUpAreaUnit_v2",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 15 + ""));
				}
			}

		}

		if (isValuation2Mandatory && AbstractCommonMapper.isEmptyOrNull(aForm.getIsPhysInsp_v2()))
			errors.add("isPhysInsp_v2", new ActionMessage("error.mandatory"));
			
		if (isValuation2Mandatory && (aForm.getLandValue_v2() == null || aForm.getLandValue_v2().equals("0") && (aForm.getPropertyTypeLabel().contains("LAND")))){
			System.out.println("00000000000000000000000  Land    mandatory");
			errors.add("landValue_v2", new ActionMessage("error.string.mandatory"));
		}
		
		if (isValuation2Mandatory && (aForm.getBuildingValue_v2() == null || aForm.getBuildingValue_v2().equals("0") && (!aForm.getPropertyTypeLabel().contains("LAND")))) {
			errors.add("buildingValue_v2", new ActionMessage("error.string.mandatory"));
		}
		
		if (isValuation2Mandatory && (aForm.getReconstructionValueOfTheBuilding_v2() == null || 
				aForm.getReconstructionValueOfTheBuilding_v2().equals("0") && (!aForm.getPropertyTypeLabel().contains("LAND")))) {
			errors.add("reconstructionValueOfTheBuilding_v2", new ActionMessage("error.string.mandatory"));
		}
		
		if (!(errorCode = Validator.checkAmount(aForm.getLandValue_v2(), isValuation2Mandatory, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			System.out.println("######<<<<LAND>>>>"+aForm.getLandValue_v2());
			errors.add("landValue_v2", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2_STR));
		}
		
		if (!(errorCode = Validator.checkAmount(aForm.getBuildingValue_v2(), isValuation2Mandatory, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			System.out.println("######<<<<LAND>>>>"+aForm.getBuildingValue_v2());
			errors.add("buildingValue_v2", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2_STR));
		}
		
		if (!(errorCode = Validator.checkAmount(aForm.getReconstructionValueOfTheBuilding_v2(), isValuation2Mandatory, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
			errors.add("reconstructionValueOfTheBuilding_v2", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"1", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_18_2_STR));
		}
		
		Boolean checkValuation2IsEntered = checkValuation2IsEntered(aForm);
		
		//valuation1NeedRevaluation i.e valuation 1 don't need any revaluation and valuation 2 is mandatory and it is not revaluated in 3 yrs
		Boolean valuation2NeedRevaluation=false;
		System.out.println("isValuation2Mandatory::  "+isValuation2Mandatory +  "  valuation1NeedRevaluation:: "+ valuation1NeedRevaluation);
		System.out.println(" revalOverride:: "+revalOverride);
		if(isValuation2Mandatory && !valuation1NeedRevaluation) {
			System.out.println(" getValuationDate_v2:: "+ aForm.getValuationDate_v2());
			if(null!=aForm.getValuationDate_v2()  && !"".equals(aForm.getValuationDate_v2())) {
			Date valCreDateV2 = DateUtil.convertDate(aForm.getValuationDate_v2());
			
			long duration2 = (today.getTime() - valCreDateV2.getTime()) / (1000l * 60 * 60 * 24 * 365);
			if(duration2 > 3 && "off".equalsIgnoreCase(revalOverride) ) {
				errors.add("valuation2NeedRevaluation", new ActionMessage("error.valuation2.needRevaluation"));
				valuation2NeedRevaluation=true;
				}
			}
		}
		
		if(isValuation2Mandatory && checkValuation2IsEntered && (null==aForm.getValcreationdate_v2() || "".equals(aForm.getValcreationdate_v2()))) {
			errors.add("mustAddValuation2",new ActionMessage("error.valuation2.mandatory"));
		}
		}
		}
		return errors;
	}
	
	private static String labelValue(String value) {
		System.out.println("00000000000000000000000000000000000000000 initial "+value);
		String label=null;
		ArrayList propertyTypeValue=null, propertyTypeLabel=null;
	    HashMap propertyTypeMap=CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.CATEGORY_PROP_TYPE);
	    
	    propertyTypeValue.addAll(propertyTypeMap.keySet());
		propertyTypeLabel.addAll(propertyTypeMap.values());
		
		if(propertyTypeValue.contains(value)) {
			System.out.println("00000000000000000000000000000000000000000"+(String)propertyTypeLabel.get(propertyTypeValue.indexOf(value)));
			return (String)propertyTypeLabel.get(propertyTypeValue.indexOf(value));
		}
		else {
			System.out.println("00000000000000000000000000000000000000000");
			return null;
		}
	}
	private static Boolean checkValuation2IsEntered(PropertyForm aForm) {
		Boolean isValu2MandatoryField=false;
		if(null ==aForm.getValuationDate_v2() || aForm.getValuationDate_v2().isEmpty()) {
			isValu2MandatoryField=true;
		}else if(null==aForm.getTotalPropertyAmount_v2() || aForm.getTotalPropertyAmount_v2()=="0" || aForm.getTotalPropertyAmount_v2().isEmpty()) {
			isValu2MandatoryField=true;
		}else if(null==aForm.getValuatorCompany_v2() ||  aForm.getValuatorCompany_v2().isEmpty()) {
			isValu2MandatoryField=true;
		}else if(null==aForm.getCountry_v2() ||  aForm.getCountry_v2().isEmpty()) {
			isValu2MandatoryField=true;
		}else if(null==aForm.getRegion_v2() ||  aForm.getRegion_v2().isEmpty()) {
			isValu2MandatoryField=true;
		}else if(null==aForm.getLocationState_v2() ||  aForm.getLocationState_v2().isEmpty()) {
			isValu2MandatoryField=true;
		}
		else if(null==aForm.getNearestCity_v2() ||  aForm.getNearestCity_v2().isEmpty()) {
			isValu2MandatoryField=true;
		}
		else if(null==aForm.getPinCode_v2() ||  aForm.getPinCode_v2().isEmpty()) {
			isValu2MandatoryField=true;
		}else if(null==aForm.getLandArea_v2() ||  aForm.getLandArea_v2().isEmpty()) {
			isValu2MandatoryField=true;
		}else if(null==aForm.getBuiltupArea_v2() ||  aForm.getBuiltupArea_v2().isEmpty()) {
			isValu2MandatoryField=true;
		}else if(null==aForm.getReconstructionValueOfTheBuilding_v2() ||  aForm.getReconstructionValueOfTheBuilding_v2().isEmpty() || aForm.getReconstructionValueOfTheBuilding_v2()=="0") {
			isValu2MandatoryField=true;
		}else if(null==aForm.getBuildingValue_v2() ||  aForm.getBuildingValue_v2().isEmpty() || aForm.getBuildingValue_v2()=="0") {
			isValu2MandatoryField=true;
		}else if(null ==aForm.getBuiltUpAreaUnit_v2() || aForm.getBuiltUpAreaUnit_v2().isEmpty()) {
			isValu2MandatoryField=true;
		}else if(AbstractCommonMapper.isEmptyOrNull(aForm.getIsPhysInsp_v2())) {
			isValu2MandatoryField=true;
		}else if("true".equalsIgnoreCase(aForm.getIsPhysInsp_v2()) && (aForm.getPhysInspFreqUOM_v2() == null || "".equals(aForm.getPhysInspFreqUOM_v2()))) {
			isValu2MandatoryField=true;
		}else if("true".equalsIgnoreCase(aForm.getIsPhysInsp_v2()) && (aForm.getDatePhyInspec_v2() == null || "".equals(aForm.getDatePhyInspec_v2()))) {
			isValu2MandatoryField=true;
		}else if("true".equalsIgnoreCase(aForm.getIsPhysInsp_v2()) && (aForm.getNextPhysInspDate_v2() == null || "".equals(aForm.getNextPhysInspDate_v2()))) {
			isValu2MandatoryField=true;
		}
		
		
		return isValu2MandatoryField;
		
	}
}
