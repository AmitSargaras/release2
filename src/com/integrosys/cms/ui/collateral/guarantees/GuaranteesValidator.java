package com.integrosys.cms.ui.collateral.guarantees;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.techinfra.validation.ValidatorConstant;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMappingDao;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.collateral.CollateralValidator;
import com.integrosys.cms.ui.collateral.guarantees.gtecorp3rd.GteCorp3rdAction;
import com.integrosys.cms.ui.collateral.guarantees.gtecorp3rd.GteCorp3rdForm;
import com.integrosys.cms.ui.collateral.guarantees.gtegovt.GteGovtForm;
import com.integrosys.cms.ui.collateral.guarantees.gteindiv.GteIndivForm;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class GuaranteesValidator {

	private static String LOGOBJ = GuaranteesValidator.class.getName();

	public static ActionErrors validateInput(GuaranteesForm aForm, Locale locale) {

		boolean isMandatory = false;
		if ("submit".equals(aForm.getEvent())
				|| "REST_UPDATE_CORP_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
				|| "REST_UPDATE_INDIV_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
				|| "REST_UPDATE_GOVT_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
				) {
			isMandatory = true;
		}
		
		ActionErrors errors = new ActionErrors();
		if (!("REST_UPDATE_CORP_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
				|| "REST_UPDATE_INDIV_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
				|| "REST_UPDATE_GOVT_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())))
		errors = CollateralValidator.validateInput(aForm, locale);

		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
		String errorCode = null;
		Date currDate = DateUtil.getDate();

		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getClaimDate())) {
				Date date1 = DateUtil.convertDate(locale, aForm.getClaimDate());
				if (date1.after(currDate)) {
					errors.add("claimDate", new ActionMessage("error.date.compareDate.more", "Net Worth Date",
							"current date"));
				}
			}

			if (!(errorCode = Validator.checkInteger(aForm.getClaimPeriod(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_INTEGER_9)).equals(Validator.ERROR_NONE)) {
				if (ValidatorConstant.ERROR_FORMAT.equals(errorCode)) {
					errors.add("claimPeriod", new ActionMessage("Check claimPeriod format. Valid Range is between 0 and 999999999."));
				}
				else if (ValidatorConstant.ERROR_LESS_THAN.equals(errorCode)
						|| ValidatorConstant.ERROR_GREATER_THAN.equals(errorCode)) {
					errors.add("claimPeriod", new ActionMessage("error.integer." + errorCode, Integer.toString(0),
							Integer.toString(IGlobalConstant.MAXIMUM_ALLOWED_INTEGER_9)));
				}
			}
			if(aForm.getClaimPeriod()!=null && !"".equals(aForm.getClaimPeriod())) {
				if("".equals(aForm.getClaimPeriodUnit())) {
					errors.add("claimPeriodUnit", new ActionMessage("error.string.mandatory"));
				}
			}
			if(aForm.getClaimPeriodUnit()!=null && !"".equals(aForm.getClaimPeriodUnit())) {
				if("".equals(aForm.getClaimPeriod())) {
					errors.add("claimPeriod", new ActionMessage("error.string.mandatory"));
				}
			}

			if (!(errorCode = Validator.checkDate(aForm.getCollateralMaturityDate(), isMandatory, locale))
					.equals(Validator.ERROR_NONE)) {
				String subtypeOfCorp = aForm.getSubtypeOfCorp();
				if (subtypeOfCorp == null) {
					subtypeOfCorp = "";
				}
				if (subtypeOfCorp.equals("")) {
					//errors.add("collateralMaturityDate", new ActionMessage("error.date.mandatory", "1", "256"));
				}
				else {
					if (subtypeOfCorp.equals("GteCorp3rd")) {
						if (aForm.getCollateralMaturityDate() != null) {
							if (!aForm.getCollateralMaturityDate().equals("")) {
								//errors.add("collateralMaturityDate", new ActionMessage("error.date.mandatory", "1",	"256"));
								//DefaultLogger.debug(LOGOBJ, " run to here ==================");
							}
						}
					}
				}
				DefaultLogger.debug(LOGOBJ, " aForm.getCollateralMaturityDate()= " + aForm.getCollateralMaturityDate());
			}
			
			if (!(errorCode = Validator.checkString(aForm.getAmountCMV(), true, 0, 50))
					.equals(Validator.ERROR_NONE)) {
		errors.add("amountCMV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
				50 + ""));
	}

			if (!(errorCode = Validator.checkString(aForm.getDescGuarantee(), false, 0, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("descGuarantee", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 250 + ""));
			}

			/*if (!(errorCode = Validator.checkString(aForm.getGuaRefNo(), false, 1, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("guaRefNo", new ActionMessage("error.string.mandatory", "1", "30"));
			}*/
			if (aForm.getEvent().equals("update") || aForm.getEvent().equals("submit")
					|| "REST_UPDATE_CORP_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					|| "REST_UPDATE_INDIV_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					|| "REST_UPDATE_GOVT_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					) {

				// Check Guarantee Amount
				if (aForm instanceof GteGovtForm || (aForm instanceof GteIndivForm)	|| (aForm instanceof GteCorp3rdForm)) {

				if (!(errorCode = Validator.checkAmount(aForm.getAmtGuarantee(), isMandatory, 0,
						IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("amtGuarantee", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
							"1", maximumAmt));
				}
				}
				else
				{
					if (!(errorCode = Validator.checkAmount(aForm.getAmtGuarantee(), false, 0,
							IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("amtGuarantee", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
								"1", maximumAmt));
					}
				}
				
			}
			else {
				// Check Guarantee Amount
				if (!(errorCode = Validator.checkAmount(aForm.getAmtGuarantee(), false, 0,
						IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("amtGuarantee", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
							"1", maximumAmt));
				}
			}

			/*if (isMandatory && !AbstractCommonMapper.isEmptyOrNull(aForm.getChargeType())) {
				errors.add("chargeType", new ActionMessage("error.mandatory"));
			}*/

			if (!(errorCode = Validator.checkString(aForm.getCurrencyGuarantee(), false, 1, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("currencyGuarantee", new ActionMessage("error.string.mandatory", "1", "3"));
		}

			if ("REST_UPDATE_CORP_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					|| "REST_UPDATE_INDIV_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					|| "REST_UPDATE_GOVT_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())) {
				if (aForm.getCollateralMaturityDate() != null && aForm.getDateGuarantee() != null)
					if (null != aForm.getCollateralMaturityDate() && !aForm.getCollateralMaturityDate().trim().isEmpty()
							&& null != aForm.getDateGuarantee()
							&& !aForm.getDateGuarantee().trim().isEmpty())
				{
					if (DateUtil.convertDate(locale, aForm.getDateGuarantee())
							.after(DateUtil.convertDate(locale, aForm.getCollateralMaturityDate()))) {
						errors.add("dateGuarantee", new ActionMessage("error.date.compareDate.cannotBelater",
								"Guarantee Start Date", "Security Maturity Date"));
					}
				}
			}
			
			if (aForm.getEvent().equals("update") || aForm.getEvent().equals("submit")
					|| "REST_UPDATE_CORP_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					|| "REST_UPDATE_INDIV_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					|| "REST_UPDATE_GOVT_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					) {
				if (!(errorCode = Validator.checkDate(aForm.getDateGuarantee(), false, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("dateGuarantee", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"0", 256 + ""));
				}
			}
			else {
				if (!(errorCode = Validator.checkDate(aForm.getDateGuarantee(), false, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("dateGuarantee", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"0", 256 + ""));
				}
			}

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getHoldingPeriod())) {
				if (AbstractCommonMapper.isEmptyOrNull(aForm.getHoldingPeriodTimeUnit())) {
					errors.add("holdingPeriod", new ActionMessage(
							"error.collateral.guarantee.holdingPeriodTimeRequired"));
				}
				else {
					if (!(errorCode = Validator.checkNumber(aForm.getHoldingPeriod(), false, 1, 999999, 0, locale))
							.equals(Validator.ERROR_NONE)) {
						if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
							errors.add("holdingPeriod", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
									"heightlessthan"), "1", "999999"));

						}
						else if (!errorCode.equals("mandatory") && !errorCode.equals("decimalexceeded")) {
							errors.add("holdingPeriod", new ActionMessage("error.number." + errorCode));
						}
					}
				}
			}
			else if (!AbstractCommonMapper.isEmptyOrNull(aForm.getHoldingPeriodTimeUnit())) {
				if (AbstractCommonMapper.isEmptyOrNull(aForm.getHoldingPeriod())) {
					errors.add("holdingPeriodTimeUnit", new ActionMessage(
							"error.collateral.guarantee.holdingPeriodRequired"));
				}
			}

			GuaranteesValidationHelper.validateInput(aForm, locale, errors);
			
			
			//************** Start of Lines added by Abhishek Naik for Guarantee Security *****************
			
			if(aForm.getDateGuarantee()==null || aForm.getDateGuarantee().equals("")){
				errors.add("dateGuarantee",new ActionMessage("error.string.mandatory"));
			}
			
			//************** Start of Lines added by Dattatray Thorat for Guarantee Security *****************
			/*if(aForm.getGuarantersDunsNumber()==null || aForm.getGuarantersDunsNumber().equals("")){
				errors.add("guarantersDunsNumber",new ActionMessage("error.string.mandatory"));
			}
			else */
			if("REST_UPDATE_CORP_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					|| "REST_UPDATE_INDIV_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					|| "REST_UPDATE_GOVT_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())) {
				
				if (!(errorCode = Validator.checkString(aForm.getGuarantersDunsNumber(), false, 1, 20))
						.equals(Validator.ERROR_NONE)) {
					errors.add("guarantersDunsNumber", new ActionMessage("error.string.length.exceeded"));
				}
				
				if (!(errorCode = Validator.checkString(aForm.getAssetStatement(), false, 1, 20))
						.equals(Validator.ERROR_NONE)) {
					errors.add("assetStatement", new ActionMessage("error.string.length.exceeded"));
				}
				
				if (!(errorCode = Validator.checkString(aForm.getDistrict(), false, 1, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("district", new ActionMessage("error.string.length.exceeded"));
				}
				
			} else {
				if (!(errorCode = Validator.checkString(aForm.getGuarantersDunsNumber(), false, 1, 20))
						.equals(Validator.ERROR_NONE)) {
					errors.add("guarantersDunsNumber", new ActionMessage("error.string.mandatory", "1", "20"));
				}
			}
			
			if (!(errorCode = Validator.checkString(aForm.getAssetStatement(), false, 1, 20))
					.equals(Validator.ERROR_NONE)) {
				errors.add("assetStatement", new ActionMessage("error.string.length.exceeded"));
			}
			
			if(aForm.getGuarantersPam()==null || aForm.getGuarantersPam().equals("")){
				//errors.add("guarantersPam",new ActionMessage("error.string.mandatory"));
			}else{
				if(aForm.getGuarantersPam().length()!=10){
					if("REST_UPDATE_CORP_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
							|| "REST_UPDATE_INDIV_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
							|| "REST_UPDATE_GOVT_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())) {
						errors.add("guarantersPam", new ActionMessage("error.string.length.pan10"));
					}else {
						errors.add("guarantersPam", new ActionMessage("error.string.length.pan10"));
					}
				}else{
					boolean nameFlag = ASSTValidator.isValidPanNo(aForm.getGuarantersPam());
					if( nameFlag == true)
						errors.add("guarantersPam", new ActionMessage("error.string.invalidPANFormat"));
				}	
			}
			
				/*if(aForm.getAmtGuarantee()==null || aForm.getAmtGuarantee().equals("")){
				errors.add("amtGuaranteeError",new ActionMessage("error.string.mandatory"));
			}*/

			if (aForm instanceof GteGovtForm || (aForm instanceof GteIndivForm)	|| (aForm instanceof GteCorp3rdForm)) {
				if("REST_UPDATE_CORP_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
						|| "REST_UPDATE_INDIV_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
						|| "REST_UPDATE_GOVT_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())) {
					if(aForm.getGuarantersName()==null || aForm.getGuarantersName().equals("")){
						errors.add("guarantersName",new ActionMessage("error.string.mandatory"));
					}
				}else {
					if(aForm.getGuarantersName()==null || aForm.getGuarantersName().equals("")){
						errors.add("guarantersNameError",new ActionMessage("error.string.mandatory"));
					}
				}
			}
			
			/*else if (!(errorCode = Validator.checkString(aForm.getGuarantersPam(), false, 1, 20))
					.equals(Validator.ERROR_NONE)) {
				errors.add("guarantersPam", new ActionMessage("error.string.mandatory", "1", "20"));
			}*/
			
		/*	if(aForm.getCity()==null || aForm.getCity().equals("")){
				errors.add("city",new ActionMessage("error.string.mandatory"));
			}
			
			if(aForm.getState()==null || aForm.getState().equals("")){
				errors.add("state",new ActionMessage("error.string.mandatory"));
			}
			
			if(aForm.getRegion()==null || aForm.getRegion().equals("")){
				errors.add("region",new ActionMessage("error.string.mandatory"));
			}*/
			

			if (aForm instanceof GteGovtForm || (aForm instanceof GteIndivForm)	|| (aForm instanceof GteCorp3rdForm)) {
			if(aForm.getCountry()==null || aForm.getCountry().equals("")){
				errors.add("country",new ActionMessage("error.string.mandatory"));
			}
			}
			
			if (aForm instanceof GteGovtForm || (aForm instanceof GteIndivForm)	|| (aForm instanceof GteCorp3rdForm)) {
				if(aForm.getAddressLine1()==null || aForm.getAddressLine1().equals("")){
					errors.add("addressLine1",new ActionMessage("error.string.mandatory"));
				}
				}
			
			if (aForm instanceof GteGovtForm || (aForm instanceof GteIndivForm)	|| (aForm instanceof GteCorp3rdForm)) {
				if(aForm.getGuarantersNamePrefix()==null || aForm.getGuarantersNamePrefix().equals("")){
					errors.add("guarantersNamePrefix",new ActionMessage("error.string.mandatory"));
				}
				}
			
			if (aForm instanceof GteGovtForm || (aForm instanceof GteIndivForm)	|| (aForm instanceof GteCorp3rdForm)) {
				if(aForm.getGuarantersFullName()==null || aForm.getGuarantersFullName().equals("")){
					errors.add("guarantersFullName",new ActionMessage("error.string.mandatory"));
				}
				}
			
			/*if(aForm.getRating()==null || aForm.getRating().equals("")){
				errors.add("rating",new ActionMessage("error.string.mandatory"));
			}
			else*/ 

			if("REST_UPDATE_CORP_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					|| "REST_UPDATE_INDIV_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					|| "REST_UPDATE_GOVT_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())) {
				if ((aForm.getRating()!=null && !aForm.getRating().equals(""))
						&& (!(errorCode = Validator.checkString(aForm.getRating(), false, 1, 20))
						.equals(Validator.ERROR_NONE))) {
					errors.add("rating", new ActionMessage("error.string.length.exceeded"));
				}
				
				if(aForm.getDiscriptionOfAssets()==null || "".equals(aForm.getDiscriptionOfAssets().trim())){
					errors.add("discriptionOfAssets", new ActionMessage("error.string.mandatory "));
				}
				
			}else {
				if (!(errorCode = Validator.checkString(aForm.getRating(), false, 1, 20))
						.equals(Validator.ERROR_NONE)) {
					errors.add("rating", new ActionMessage("error.string.mandatory", "1", "20"));
				}
			}
			/*if(aForm.getRecourse()==null || aForm.getRecourse().equals("")){
				errors.add("recourse",new ActionMessage("error.string.mandatory"));
			}*/
			
			if("REST_UPDATE_CORP_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					|| "REST_UPDATE_INDIV_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					|| "REST_UPDATE_GOVT_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())) {
				if ((aForm.getGuarantersName() != null && !aForm.getGuarantersName().equals(""))
						&& (!(errorCode = Validator.checkString(aForm.getGuarantersName(), false, 1, 100))
								.equals(Validator.ERROR_NONE))) {
					errors.add("guarantersName", new ActionMessage("error.Guarantor.namelength", "1", "100"));
				}
			} else {
				if ((aForm.getGuarantersName() != null && !aForm.getGuarantersName().equals(""))
						&& (!(errorCode = Validator.checkString(aForm.getGuarantersName(), false, 1, 100))
								.equals(Validator.ERROR_NONE))) {
					errors.add("guarantersNameError", new ActionMessage("error.Guarantor.namelength", "1", "100"));
				}
			}
			
			if("REST_UPDATE_CORP_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					|| "REST_UPDATE_INDIV_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					|| "REST_UPDATE_GOVT_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())) {
				if ((aForm.getGuarantersNamePrefix()!=null && !aForm.getGuarantersNamePrefix().equals("")) 
						&& (!(errorCode = Validator.checkString(aForm.getGuarantersNamePrefix(), false, 1, 50))
						.equals(Validator.ERROR_NONE))) {
					errors.add("guarantersNamePrefix", new ActionMessage("error.string.length.exceeded"));
				}
				
				if (!(errorCode = Validator.checkString(aForm.getGuaRefNo(), false, 1, 25)).equals(Validator.ERROR_NONE)) {
					errors.add("guaRefNo", new ActionMessage("error.string.length.exceeded"));
				}
			}else if ((aForm.getGuarantersNamePrefix()!=null && !aForm.getGuarantersNamePrefix().equals("")) 
					&& (!(errorCode = Validator.checkString(aForm.getGuarantersNamePrefix(), false, 1, 100))
					.equals(Validator.ERROR_NONE))) {
				errors.add("guarantersNamePrefix", new ActionMessage("error.Guarantor.namelength", "1", "100"));
			}
			
			if ((aForm.getGuarantersFullName()!=null && !aForm.getGuarantersFullName().equals("")) 
					&&(!(errorCode = Validator.checkString(aForm.getGuarantersFullName(), false, 1, 100))
					.equals(Validator.ERROR_NONE))) {
				errors.add("guarantersFullName", new ActionMessage("error.Guarantor.namelength", "1", "100"));
			}
			
			if ((aForm.getAddressLine1()!=null && !aForm.getAddressLine1().equals("")) 
					&&(!(errorCode = Validator.checkString(aForm.getAddressLine1(), false, 1, 200))
					.equals(Validator.ERROR_NONE))) {
				errors.add("addressLine1", new ActionMessage("error.address.length", "1", "200"));
			}
			
			if ((aForm.getAddressLine2()!=null && !aForm.getAddressLine2().equals("")) 
					&&(!(errorCode = Validator.checkString(aForm.getAddressLine2(), false, 1, 200))
					.equals(Validator.ERROR_NONE))) {
				errors.add("addressLine2", new ActionMessage("error.address.length", "1", "200"));
			}
			
			if ((aForm.getAddressLine3()!=null && !aForm.getAddressLine3().equals("")) 
					&&(!(errorCode = Validator.checkString(aForm.getAddressLine3(), false, 1, 200))
					.equals(Validator.ERROR_NONE))) {
				errors.add("addressLine3", new ActionMessage("error.address.length", "1", "200"));
			}
			
			if (aForm.getTelephoneAreaCode()!=null && !aForm.getTelephoneAreaCode().equals("")){
				if(aForm.getTelephoneAreaCode().trim().length()<= 15) {
				boolean error=ASSTValidator.isInteger(aForm.getTelephoneAreaCode());
				if(!error) {
					errors.add("telephoneAreaCode", new ActionMessage("error.integer.format"));
				}
				}
				else
					errors.add("telephoneAreaCode", new ActionMessage("error.telephonearea.length"));
			}
			/*if((aForm.getTelephoneAreaCode()!= null && !aForm.getTelephoneAreaCode().equals(""))
					&& !Validator.ERROR_NONE.equals
					(errorCode =Validator.checkNumber(aForm.getTelephoneAreaCode().toString().trim(),true,1, 999999999999999.D)))
			{
				errors.add("telephoneAreaCode", new ActionMessage("error.integer.format"));
			}*/
			
			if ((aForm.getDiscriptionOfAssets()!=null && !aForm.getDiscriptionOfAssets().equals("")) 
					&&(!(errorCode = Validator.checkString(aForm.getDiscriptionOfAssets(), false, 1, 200))
					.equals(Validator.ERROR_NONE))) {
				//errors.add("discriptionOfAssets", new ActionMessage("error.string.mandatory ", "1", "200"));
				errors.add("discriptionOfAssets", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "200"));
			}else if(aForm.getDiscriptionOfAssets()!=null && "".equals(aForm.getDiscriptionOfAssets().trim())){
				errors.add("discriptionOfAssets", new ActionMessage("error.string.mandatory "));
			}
			
			
			if(aForm.getTelephoneNumber()!=null && !aForm.getTelephoneNumber().equals("") && aForm.getTelephoneNumber().trim().length()> 15){
				errors.add("telephoneNumber", new ActionMessage("error.phone.length"));
				DefaultLogger.debug(GteCorp3rdAction.class, "contactNoError in length");
			}
			else if((aForm.getTelephoneNumber()!= null && !aForm.getTelephoneNumber().equals("")) && !Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(aForm.getTelephoneNumber().toString().trim(),true,15,999999999999999.D)))
			{
					errors.add("telephoneNumber", new ActionMessage("error.phone.invalid"));
					DefaultLogger.debug(GteCorp3rdAction.class, "ContactNoError number formate");
			}
			else if((aForm.getTelephoneNumber()!= null && !aForm.getTelephoneNumber().equals("")) && !Validator.ERROR_NONE.equals(errorCode =Validator.checkPhoneNumber(aForm.getTelephoneNumber().toString().trim(),true,locale)))
			{
					errors.add("telephoneNumber", new ActionMessage("error.phone.invalid"));
					DefaultLogger.debug(GteCorp3rdAction.class, "ContactNoError not acording to local");
			}
			else if(aForm.getTelephoneNumber()!=null && !aForm.getTelephoneNumber().equals("") && aForm.getTelephoneNumber().trim().length()<= 15)
			{
				boolean error=ASSTValidator.isInteger(aForm.getTelephoneNumber());	
			if(!error) {
				errors.add("telephoneNumber", new ActionMessage("error.integer.format"));
			}
			}
			/*if ( aForm.getGuaRefNo()==null || "".equals(aForm.getGuaRefNo())) {
				errors.add("guaRefNo",new ActionMessage("error.string.mandatory"));
			}*/
			
		/*	if(aForm.getRamId()==null ||aForm.getRamId().trim().equals("")){
				errors.add("ramId",new ActionMessage("error.string.mandatory"));
			}
			else*/ if (!(errorCode = Validator.checkNumber(aForm.getRamId(), false, 1, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20))
					.equals(Validator.ERROR_NONE)) {
				errors.add("ramId", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_STR));
			}
			else{
				boolean nameFlag = ASSTValidator.isValidANDName(aForm.getRamId());
				if( nameFlag == true)
					errors.add("ramId", new ActionMessage("error.string.invalidCharacter"));
			}

			if (aForm instanceof GteGovtForm || (aForm instanceof GteIndivForm)	|| (aForm instanceof GteCorp3rdForm)) {
			if(aForm.getGuarantorType()==null || aForm.getGuarantorType().trim().equals("")){
				errors.add("guarantorType",new ActionMessage("error.string.mandatory"));
			}
			}
			
		/*	if(aForm.getGuarantorNature()==null || aForm.getGuarantorNature().trim().equals("")){
				errors.add("guarantorNature",new ActionMessage("error.string.mandatory"));
			}*/
			
			if(aForm.getDistrict()!=null && !aForm.getDistrict().trim().equals("")){
				boolean nameFlag = ASSTValidator.isValidANDName(aForm.getDistrict());
				if( nameFlag == true)
					errors.add("district", new ActionMessage("error.string.invalidCharacter"));
			}
			
			boolean isRegionAvailable=false;
			if("REST_UPDATE_CORP_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					|| "REST_UPDATE_INDIV_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())
					|| "REST_UPDATE_GOVT_GUARANTEES_SECURITY".equalsIgnoreCase(aForm.getEvent())) {
				if(null!=aForm.getRegion() && !(aForm.getRegion().trim().isEmpty())) {
					isRegionAvailable=true;
				}
			}
			if(isRegionAvailable && aForm.getPinCode()!=null && !aForm.getPinCode().trim().equals("")){
				IPincodeMappingDao pincodeDao=(IPincodeMappingDao) BeanHouse.get("pincodeMappingDao");
				if(pincodeDao!=null && aForm.getState()!=null && !aForm.getState().trim().equals("")){
					
					String stateCode= aForm.getState();
					String pincode=aForm.getPinCode();
					 if(pincode.length()>1 || pincode.length()==1)
					 {
						 pincode=pincode.substring(0,1);
						 if(stateCode!=null && !pincodeDao.isPincodeMappingValid(pincode,stateCode))
						 {
							errors.add("postcodeError",new ActionMessage("error.string.invalidMapping"));
						 }
					 }
					
				}else {
					   errors.add("postcodeError",new ActionMessage("error.string.selectState")); //added the validation when user puts pin code without state
					 
				}
				if(aForm.getPinCode().length() > 6){
					errors.add("pinCode", new ActionMessage("error.string.length.pincode"));
				}	
				else if (!(errorCode = Validator.checkInteger(aForm.getPinCode(), true, 1, 999999))
						.equals(Validator.ERROR_NONE)) {
					errors.add("pinCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "1",
							"999999"));
				}
				else{
					boolean nameFlag = ASSTValidator.isValidANDName(aForm.getPinCode());
					if( nameFlag == true)
						errors.add("pinCode", new ActionMessage("error.string.invalidCharacter"));
				}	
			}
			
			boolean flag = false;
			if (!(errorCode = Validator.checkAmount(aForm.getAmountCMV(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				flag = true;
				DefaultLogger.debug(LOGOBJ, "aForm.getAmountCMV() = " + aForm.getAmountCMV());
			}
			
			if(flag == false) {
			if(!AbstractCommonMapper.isEmptyOrNull(aForm.getAmountCMV()) && !AbstractCommonMapper.isEmptyOrNull(aForm.getTotalLineLevelSecurityOMV())) {
				BigDecimal amtCMV = UIUtil.mapStringToBigDecimal(aForm.getAmountCMV());
				BigDecimal totalLineLevelSecurityOMV = UIUtil.mapStringToBigDecimal((aForm.getTotalLineLevelSecurityOMV()));
				if(amtCMV.compareTo(totalLineLevelSecurityOMV) == -1) {
					errors.add("totalLineLevelSecurityOMV", new ActionMessage("error.security.total.omv.exceeded"));
				}
			}
		}
			
			//************** End of Lines added by Dattatray Thorat for Guarantee Security *******************			
		}
		return errors;

	}
}
