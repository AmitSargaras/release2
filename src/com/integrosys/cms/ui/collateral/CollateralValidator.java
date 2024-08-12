package com.integrosys.cms.ui.collateral;

import static com.integrosys.base.techinfra.validation.ValidatorConstant.ERROR_NONE;
import static com.integrosys.base.uiinfra.common.ErrorKeyMapper.AMOUNT;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.DEFAULT_CURRENCY;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_STR;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2_STR;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryConstant;
import com.integrosys.cms.app.dataprotection.proxy.DataProtectionProxyFactory;
import com.integrosys.cms.app.dataprotection.proxy.IDataProtectionProxy;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.ui.collateral.assetbased.AssetBasedForm;
import com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftForm;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeForm;
import com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs.AssetPostDatedChqsForm;
import com.integrosys.cms.ui.collateral.cash.CashForm;
import com.integrosys.cms.ui.collateral.cash.cashfd.CashFdForm;
import com.integrosys.cms.ui.collateral.guarantees.GuaranteesForm;
import com.integrosys.cms.ui.collateral.insprotection.inskeyman.InsKeymanForm;
import com.integrosys.cms.ui.collateral.marketablesec.MarketableSecForm;
import com.integrosys.cms.ui.collateral.marketablesec.marksecmainlocal.MarksecMainLocalForm;
import com.integrosys.cms.ui.collateral.others.OthersForm;
import com.integrosys.cms.ui.collateral.property.PropertyForm;
import com.integrosys.cms.ui.collateral.property.propcommgeneral.PropCommGeneralForm;
import com.integrosys.cms.ui.common.NumberUtils;
import com.integrosys.cms.ui.common.NumberValidator;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.dataprotection.DataProtectionConstants;
import com.integrosys.cms.ui.feed.FeedConstants;

public class CollateralValidator {
	final static String COLLETERAL_CODE="COL0000139";
	private static String LOGOBJ = CollateralValidator.class.getName();

	public static ActionErrors validateInput(CollateralForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		Date currDate = DateUtil.getDate();
		String subTypeCode = aForm.getSubTypeCode();
		String collateralHiddenValue = null; 
		boolean mandatorySubmit = false;
		try {
			if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {

				if (aForm.getEvent().equals("submit") || aForm.getEvent().equals("update") 
						|| aForm.getEvent().equals("REST_UPDATE_AB_SA_SECURITY")
						|| aForm.getEvent().equals("REST_UPDATE_CORP_GUARANTEES_SECURITY")
						|| aForm.getEvent().equals("REST_UPDATE_INDIV_GUARANTEES_SECURITY")
						|| aForm.getEvent().equals("REST_UPDATE_GOVT_GUARANTEES_SECURITY")) {
					if (aForm.getEvent().equals("submit") || aForm.getEvent().equals("REST_UPDATE_AB_SA_SECURITY")
							|| aForm.getEvent().equals("REST_UPDATE_CORP_GUARANTEES_SECURITY")
							|| aForm.getEvent().equals("REST_UPDATE_INDIV_GUARANTEES_SECURITY")
							|| aForm.getEvent().equals("REST_UPDATE_GOVT_GUARANTEES_SECURITY"))
						mandatorySubmit = true;
					
					//try{
					/*if(null != aForm.getSpread() && !("").equals(aForm.getSpread()))
					{
						if(Validator.checkNumber(aForm.getSpread(),true, 0.0, 99.99, 3, locale).equalsIgnoreCase("decimalexceeded"))
							errors.add("spread", new ActionMessage("error.number.decimalexceeded"));
					if (Float.parseFloat(aForm.getSpread())>=100) {
						errors.add("spread", new ActionMessage("error.amount.greaterthan", "0",
								"99.99" + ""));
					}
					if (Float.parseFloat(aForm.getSpread())<0) {
						errors.add("spread", new ActionMessage("error.amount.greaterthan", "0",
								"99.99" + ""));
					}	
					}*/
					/*}
					catch(Exception e){
						errors.add("spread", new ActionMessage("error.amount.format"));
					}*/
					
					// Added By Dayananda Laishram : For CR Bill As colleteral validation on 05-May-2015 | Starts
					if (aForm instanceof AssetAircraftForm)
					{
						    AssetAircraftForm assetBasedForm = (AssetAircraftForm)aForm;
						    if(assetBasedForm != null){
						    	collateralHiddenValue = (assetBasedForm.getColCodeHiddenValue() != null && COLLETERAL_CODE.equalsIgnoreCase(assetBasedForm.getColCodeHiddenValue())) ? assetBasedForm.getColCodeHiddenValue() : null;
						    }
						    
						    if(collateralHiddenValue!=null)
						    {
						    	if(aForm.getMargin()==null || "".equals(aForm.getMargin()))
						    		aForm.setMargin("0");
						    	
						    	if(aForm.getAmountCMV()==null || "".equals(aForm.getAmountCMV()))
						    	 aForm.setAmountCMV("0");
						    	
						    	if("true".equalsIgnoreCase(((AssetBasedForm) aForm).getIsPhysInsp()))
						    		((AssetBasedForm) aForm).setIsPhysInsp("true");
						    	else
						    		((AssetBasedForm) aForm).setIsPhysInsp("false");
							    
						    }
						   
					}
					
					// Added By Dayananda Laishram : For CR Bill As colleteral validation on 05-May-2015 | Ends
					//Added by Pramod Katkar for New Filed CR on 13-08-2013
					if(aForm.getMonitorProcess()==null || aForm.getMonitorProcess().equals("")){
						errors.add("monitorProcess", new ActionMessage("error.string.mandatory", "1", "80"));
					}else if(aForm.getMonitorProcess().equalsIgnoreCase("Y")&& (aForm.getMonitorFrequency()==null || aForm.getMonitorFrequency().equals("")) ){
						errors.add("monitorFrequency", new ActionMessage("error.string.mandatory", "1", "80"));
					}
					//End by Pramod Katkar
					if (aForm instanceof CashFdForm){
					Number depositAmountValue = NumberUtils.parseNumber(aForm.getSpread(), locale);
					if (!(errorCode = Validator.checkNumber(aForm.getSpread(), true, -99.99, 99.99,
							3, locale)).equals(Validator.ERROR_NONE)) {
						if (Validator.ERROR_DECIMAL_EXCEEDED.equals(errorCode)) {
							errorCode = "moredecimalexceeded";
						}
						errors.add("spread", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "-99.99", "99.99",
								"2"));
					}
					}
					/*else  {
						if (Float.parseFloat(aForm.getSpread())>=100) {
							errors.add("spread", new ActionMessage("error.amount.greaterthan", "0",
									"99.99" + ""));
						}
						if (Float.parseFloat(aForm.getSpread())<0) {
							errors.add("spread", new ActionMessage("error.amount.greaterthan", "0",
									"99.99" + ""));
						}	
					}*/
					
					
					
					if (aForm.getIsSSC().equals("false")) {
//						if (!(errorCode = Validator.checkString(aForm.getCollateralLoc(), true, 1, 20))
//								.equals(Validator.ERROR_NONE)) {
//							errors.add("collateralLoc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "20"));
//							DefaultLogger.debug(LOGOBJ, " aForm.getCollateralLoc() = " + aForm.getCollateralLoc());
//						}
						//Sachin P:Start Here 26/07/2011  This fields is not in mandatory
						/*if (!(errorCode = Validator.checkString(aForm.getSecurityOrganization(), true, 1, 20))
								.equals(Validator.ERROR_NONE)) {
							errors.add("securityOrganization", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "20"));
							DefaultLogger.debug(LOGOBJ, "aForm.getSecurityOrganization()= "
									+ aForm.getSecurityOrganization());
						}*/
                       //Sachin P:End Here 26/07/2011  This fields is not in mandatory
					}
					boolean isSTPMandatory = PropertiesConstantHelper.isSTPRequired() &&
							PropertiesConstantHelper.isValidSTPSystem(aForm.getSourceID());
					
//					if (!(errorCode = Validator.checkString(aForm.getCollateralName(), mandatorySubmit && isSTPMandatory, 1, 40))
//							.equals(Validator.ERROR_NONE)) {
//						errors.add("collateralName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "40"));
//					}
					
					if (!(errorCode = Validator.checkString(aForm.getSourceSecuritySubType(), mandatorySubmit && isSTPMandatory, 1, 40))
							.equals(Validator.ERROR_NONE)) {
						errors.add("sourceSecuritySubType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "20"));
					}
					
					if (!AbstractCommonMapper.isEmptyOrNull(aForm.getCollateralMaturityDate())
							&& aForm.getIsSSC().equals("false")) {
						if (!(errorCode = Validator.checkDate(aForm.getCollateralMaturityDate(), false, locale))
								.equals(Validator.ERROR_NONE)) {
							errors.add("collateralMaturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "256"));
							DefaultLogger.debug(LOGOBJ, " aForm.getCollateralMaturityDate()= "
									+ aForm.getCollateralMaturityDate());
						}
					}

					/*if (!(errorCode = Validator.checkString(aForm.getCollateralStatus(), mandatorySubmit, 1, 3))
							.equals(Validator.ERROR_NONE)) {
						errors.add("collateralStatus", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "3"));
						DefaultLogger.debug(LOGOBJ, " aForm.getCollateralStatus() =" + aForm.getCollateralStatus());
					}*/
					
					if(aForm.getEvent().equals("REST_UPDATE_AB_SA_SECURITY")
							|| aForm.getEvent().equals("REST_UPDATE_CORP_GUARANTEES_SECURITY")
							|| aForm.getEvent().equals("REST_UPDATE_INDIV_GUARANTEES_SECURITY")
							|| aForm.getEvent().equals("REST_UPDATE_GOVT_GUARANTEES_SECURITY")
							|| aForm.getEvent().equals("REST_UPDATE_MARKETABLE_SECURITY")
							|| aForm.getEvent().equals("REST_UPDATE_PROPERTY_SECURITY"))
					{
						if (aForm instanceof PropertyForm || (aForm instanceof AssetBasedForm)
								|| (aForm instanceof GuaranteesForm) || (aForm instanceof OthersForm)
								|| (aForm instanceof CashForm) || (aForm instanceof InsKeymanForm)
								|| aForm instanceof MarketableSecForm || aForm instanceof CollateralRestForm) 
						{
							if((!((aForm instanceof AssetGenChargeForm) || (aForm instanceof CashFdForm )))){
								if(aForm.getMargin() == null || aForm.getMargin().equals(""))
								{
									errors.add("margin", new ActionMessage("error.string.mandatory"));
								}
								else{
									if (!(errorCode = Validator.checkNumber(aForm.getMargin(), false, 0, 100.00))
											.equals(Validator.ERROR_NONE)) {
										errors.add("margin", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
												"0", 100.00 + ""));
										DefaultLogger.debug(LOGOBJ, "aForm.getMargin() = " + aForm.getMargin());
									}
								}
							}
						}
					
					}
					else {
						
					if (aForm instanceof PropertyForm || (aForm instanceof AssetBasedForm)
							|| (aForm instanceof GuaranteesForm) || (aForm instanceof OthersForm)
							|| (aForm instanceof CashForm) || (aForm instanceof InsKeymanForm)
							|| aForm instanceof MarketableSecForm || aForm instanceof CollateralRestForm) 
					{
						if((!((aForm instanceof AssetGenChargeForm) || (aForm instanceof CashFdForm )))){
							if(aForm.getMargin() == null || aForm.getMargin().equals(""))
							{
								errors.add("haircut", new ActionMessage("error.string.mandatory"));
							}
							else{
								if (!(errorCode = Validator.checkNumber(aForm.getMargin(), false, 0, 100.00))
										.equals(Validator.ERROR_NONE)) {
									errors.add("haircut", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
											"0", 100.00 + ""));
									DefaultLogger.debug(LOGOBJ, "aForm.getMargin() = " + aForm.getMargin());
								}
							}
						}
						
						
					}
				}
					
					//Added by Pramod Katkar for New Filed CR on 13-08-2013
					if(!(aForm.getEvent().equals("REST_UPDATE_CORP_GUARANTEES_SECURITY")
							|| aForm.getEvent().equals("REST_UPDATE_INDIV_GUARANTEES_SECURITY")
							|| aForm.getEvent().equals("REST_UPDATE_GOVT_GUARANTEES_SECURITY"))) {
					if(!(aForm instanceof GuaranteesForm || aForm instanceof CashForm || aForm instanceof MarketableSecForm || aForm instanceof AssetGenChargeForm
							|| aForm instanceof AssetPostDatedChqsForm)){
						if(!(aForm instanceof PropCommGeneralForm)){
								if(aForm.getValuationAmount()==null || aForm.getValuationAmount().equals("")){
									if(collateralHiddenValue==null)// Added By Dayananda Laishram : For CR Bill As colleteral validation on 05-May-2015
										errors.add("valuationAmount", new ActionMessage("error.string.mandatory"));
								}
								else if (!(errorCode = Validator.checkAmount(aForm.getValuationAmount(), false, 0,
											IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
											.equals(Validator.ERROR_NONE)) {
										errors.add("valuationAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
												"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR));
										DefaultLogger.debug(LOGOBJ, "aForm.getValuationAmount() = " + aForm.getValuationAmount());
									}
					
								if(aForm.getValuationDate()==null || aForm.getValuationDate().equals("")){
									errors.add("valuationDate", new ActionMessage("error.string.mandatory"));
								}else if(aForm.getValuationDate()!=null || !aForm.getValuationDate().equals("")){
									IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
									IGeneralParamEntry generalParamEntry = generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
									String applicationDate = generalParamEntry.getParamValue();
									Date appDate = new Date();
									if(applicationDate!=null){
										appDate = new Date(applicationDate);
									}
									
									if (DateUtil.convertDate(locale, aForm.getValuationDate()).after(appDate)) {
										errors.add("valuationDate", new ActionMessage("error.future.date"));
									}					
						}
						}
						
								if(aForm.getNextValDate()==null || aForm.getNextValDate().equals("")){
									if(collateralHiddenValue==null){ // Added By Dayananda Laishram : For CR Bill As colleteral validation on 05-May-2015
										errors.add("nextValDate", new ActionMessage("error.string.mandatory"));
									}
									
								}else if(aForm.getValuationDate()!=null && aForm.getNextValDate()!=null){
									if(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getValuationDate())).after(
											DateUtil.convertDate(locale, aForm.getNextValDate()))){
										errors.add("nextValDate", new ActionMessage("error.date.compareDate", "Next Valuation Date ","Date of Valuation"));
									}
								}
						
								if(aForm.getCommonRevalFreq()==null || aForm.getCommonRevalFreq().equals("")
										||aForm.getCommonRevalFreq().equalsIgnoreCase("null")){
									if(collateralHiddenValue==null){ // Added By Dayananda Laishram : For CR Bill As colleteral validation on 05-May-2015
										errors.add("commonRevalFreq", new ActionMessage("error.string.mandatory"));
									}
									
								}else if(!StringUtils.isNumeric(aForm.getCommonRevalFreqNo())){
									errors.add("commonRevalFreqNo", new ActionMessage("error.amount.number.format"));
								}
								if(aForm.getTypeOfChange()==null || aForm.getTypeOfChange().equals("")){
									if(collateralHiddenValue==null){ // Added By Dayananda Laishram : For CR Bill As colleteral validation on 05-May-2015
										errors.add("typeOfChange", new ActionMessage("error.string.mandatory"));
									}
									
								}
								if(aForm.getTypeOfChange().equalsIgnoreCase("SECOND_CHARGE")){
									if(aForm.getOtherBankCharge()==null || "".equals(aForm.getOtherBankCharge()) || StringUtils.isBlank(aForm.getOtherBankCharge())){
										if(collateralHiddenValue==null){ // Added By Dayananda Laishram : For CR Bill As colleteral validation on 05-May-2015
											errors.add("otherBankCharge", new ActionMessage("error.string.mandatory"));
										}
										
									}/*else if(Integer.parseInt(aForm.getOtherBankCharge())<0){
										errors.add("otherBankCharge", new ActionMessage("error.string.minzero"));
									}else if(Integer.parseInt(aForm.getOtherBankCharge())>100){
										errors.add("otherBankCharge", new ActionMessage("error.string.maxhundred"));
									}*/else if (!(errorCode = Validator.checkAmount(aForm.getOtherBankCharge(), false, 0,
											IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_2_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
											.equals(Validator.ERROR_NONE)) {
										errors.add("otherBankCharge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
												"0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_2_STR));
										DefaultLogger.debug(LOGOBJ, "aForm.getOtherBankCharge() = " + aForm.getOtherBankCharge());
									}
								}
					}
				}
					//End by Pramod Katkar
					if (aForm instanceof AssetGenChargeForm){
					if(aForm.getTypeOfCharge()==null || aForm.getTypeOfCharge().equals("")
							||aForm.getTypeOfCharge().equalsIgnoreCase("null")){
							errors.add("typeOfCharge", new ActionMessage("error.string.mandatory"));
					}
						if(StringUtils.isEmpty(aForm.getBankingArrangement())){
							errors.add("bankingArrangementError", new ActionMessage("error.string.mandatory"));
						}
					}
					
				if(!(aForm.getEvent().equals("prepare") ||aForm.getEvent().equals("prepare_update_code") || aForm.getEvent().endsWith("prepare_update_sub")
						|| aForm.getEvent().endsWith("_prepare") )){
					if (!(errorCode = Validator.checkString(aForm.getCollateralCurrency(), true, 1, 30))
							.equals(Validator.ERROR_NONE)) {
						errors.add("collateralCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "30"));
						DefaultLogger.debug(LOGOBJ, " aForm.getCollateralCurrency() =" + aForm.getCollateralCurrency());
					}
	
					if (!(errorCode = Validator.checkString(aForm.getSecPriority(), true, 1, 80))
							.equals(Validator.ERROR_NONE)) {
						errors.add("secPriority", new ActionMessage("error.string.mandatory", "1", "80"));
					}
					
                 if(aForm instanceof PropCommGeneralForm)
                 {
					if (!(errorCode = Validator.checkString(aForm.getSecurityOrganization(), true, 1, 80))
							.equals(Validator.ERROR_NONE)) {
						errors.add("securityOrganization", new ActionMessage("error.string.mandatory", "1", "80"));
					}
                 }
				}
							
				if ((aForm.getSecCustodianType() != null) && (aForm.getSecCustodianType().trim().length() > 0)) {
					if (ICMSConstant.INTERNAL_COL_CUSTODIAN.equals(aForm.getSecCustodianType())) {
						if (!(errorCode = Validator.checkString(aForm.getSecCustodianInt(), mandatorySubmit, 0, 50))
								.equals(Validator.ERROR_NONE)) {
							errors.add("secCustodianInt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
									errorCode), "0", 50 + ""));
							DefaultLogger.debug(LOGOBJ, " aForm.getSecCustodianInt() = " + aForm.getSecCustodianInt());
						}
					}
					else {
						if (!(errorCode = Validator.checkString(aForm.getSecCustodianExt(), mandatorySubmit, 0, 50))
								.equals(Validator.ERROR_NONE)) {
							errors.add("secCustodianExt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
									errorCode), "0", 50 + ""));
							DefaultLogger.debug(LOGOBJ, " aForm.getSecCustodianExt() = " + aForm.getSecCustodianExt());
						}
					}
				}
				
				//CERSAI Validation
				errors = CollateralValidator.validateCersaiFields(aForm, locale, errors);
				
			}
		}
		}
		catch (Exception e) {
			DefaultLogger.debug(LOGOBJ, "error encountered" + e.toString());
		}
		try {
			/* Already checked in GuaranteesValidationHelper */
			/*if (!(errorCode = Validator.checkString(aForm.getLe(), mandatorySubmit, 0, 10))
					.equals(Validator.ERROR_NONE)) {
				errors.add("le", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "256"));
				DefaultLogger.debug(LOGOBJ, " Le is mandatory= " + aForm.getLe());
			}*/
			
			String le = aForm.getLe()==null?"":aForm.getLe();
			if(le.equals("Y")&&!(errorCode = Validator.checkString(aForm.getLeDate(), mandatorySubmit, 0, 15))
					.equals(Validator.ERROR_NONE)){
				errors.add("leDate", new ActionMessage("error.date.mandatory"));
			}

			if (aForm.getIsSSC().equals("false")) {
				if (!(errorCode = Validator.checkAmount(aForm.getAmtCharge(), false, 0,
						IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("amtCharge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
							"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
					DefaultLogger.debug(LOGOBJ, "aForm.getAmtCharge() =" + aForm.getAmtCharge());
				}
			}

			if (aForm.getCurrencyCharge() != null) {
				if (!(errorCode = Validator.checkString(aForm.getCurrencyCharge(), false, 1, 3))
						.equals(Validator.ERROR_NONE)) {
					errors.add("currencyCharge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "3"));
					DefaultLogger.debug(LOGOBJ, " aForm.getCurrencyCharge()= " + aForm.getCurrencyCharge());
				}
			}

			if (aForm.getConfirmChargeDate() != null) {
				if (!(errorCode = Validator.checkDate(aForm.getConfirmChargeDate(), false, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("confirmChargeDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "256"));
					DefaultLogger.debug(LOGOBJ, "aForm.getConfirmChargeDate() = " + aForm.getConfirmChargeDate());
				}
			}

			if (aForm.getPerfectionDate() != null) {
				if (!(errorCode = Validator.checkDate(aForm.getPerfectionDate(), false, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("perfectionDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "256"));
					DefaultLogger.debug(LOGOBJ, "aForm.getPerfectionDate() = " + aForm.getPerfectionDate());
				}
				else if ((aForm.getPerfectionDate() != null) && !aForm.getPerfectionDate().equals("")) {
					Date date1 = DateUtil.convertDate(locale, aForm.getPerfectionDate());
					if (date1.after(currDate)) {
						errors.add("perfectionDate", new ActionMessage("error.date.compareDate.more",
								"Security Perfection Date", "current date"));
						DefaultLogger.debug(LOGOBJ, "aForm.getPerfectionDate() = " + aForm.getPerfectionDate());
					}
				}
			}

			/*
			 * Start of code by Pratheepa on 26/05/06 to fix issue CMSSP-739
			 * Made isEditable false and hence removed validation for the
			 * subtyes which has label to display valDate
			 */

			boolean isSSC = aForm.getIsSSC().equals("true");
			boolean isValDateEditable = false;
			boolean isValEditable = true;

			if ((subTypeCode != null) && (subTypeCode.trim().length() > 0)) {
				if (subTypeCode.startsWith(ICMSConstant.SECURITY_TYPE_PROPERTY)) {
					IDataProtectionProxy dpProxy = DataProtectionProxyFactory.getProxy();
					isValDateEditable = dpProxy.isFieldAccessAllowed(ICMSConstant.INSTANCE_COLLATERAL, aForm
							.getSubTypeCode(), DataProtectionConstants.COL_PT_VAL,
							(isSSC ? ICMSConstant.TEAM_TYPE_SSC_MAKER : ICMSConstant.TEAM_TYPE_CPC_MAKER), aForm
									.getCollateralLoc(), IDataProtectionProxy.ANY_ORGANISATION);
					isValEditable = isValDateEditable;
				}
				else if (!isSSC) {
					if (subTypeCode.startsWith(ICMSConstant.SECURITY_TYPE_OTHERS)
							|| (subTypeCode.startsWith(ICMSConstant.SECURITY_TYPE_ASSET) && (!subTypeCode
									.equals(ICMSConstant.COLTYPE_ASSET_PDT_CHEQUE)))
							|| subTypeCode.equals(ICMSConstant.COLTYPE_INS_CR_DEFAULT_SWAPS)
							|| subTypeCode.equals(ICMSConstant.COLTYPE_MAR_NONLISTED_LOCAL)) {
						isValDateEditable = true;
					}
				}
			}

			DefaultLogger.debug("CollateralValidator", "<< isValDateEditable: " + isValDateEditable);
			if (isValDateEditable) {
				// End of code
				if (aForm.getEvent().equals("submit") || aForm.getEvent().equals("REST_UPDATE_AB_SA_SECURITY")|| aForm.getEvent().equals("update")) {
					if (aForm.getValDate() != null) {
						if (!(errorCode = Validator.checkDate(aForm.getValDate(), false, locale))
								.equals(Validator.ERROR_NONE)) {
							errors.add("valDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "256"));
							DefaultLogger.debug(LOGOBJ, "aForm.getValDate() = " + aForm.getValDate());
						}
						else if ((aForm.getValDate().length() > 0)
								&& DateUtil.convertDate(locale, aForm.getValDate()).after(DateUtil.getDate())) {
							errors.add("valDate", new ActionMessage("error.collateral.valuation.valuationDateEarlier",
									"1", "256"));
							DefaultLogger.debug(LOGOBJ, "aForm.getValDate() = " + aForm.getValDate());
						}
					}
				}
			}

			if (isValEditable) {

				if (aForm.getNonStdFreq() != null) {
					if (!(errorCode = Validator.checkInteger(aForm.getNonStdFreq(), false, 0, 100))
							.equals(Validator.ERROR_NONE)) {
						errors.add("nonStdFreq", new ActionMessage(
								ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", 100 + ""));
						DefaultLogger.debug(LOGOBJ, "aForm.getNonStdFreq() = " + aForm.getNonStdFreq());
					}
					if (!aForm.getNonStdFreq().trim().equals("")
							&& ((aForm.getNonStdFreqUnit() == null) || aForm.getNonStdFreqUnit().trim().equals(""))) {
						errors.add("nonStdFreqUnit", new ActionMessage("error.string.mandatory", "1", "1"));
						DefaultLogger.debug(LOGOBJ, "aForm.getNonStdFreqUnit() = " + aForm.getNonStdFreqUnit());
					}
				}

				if (!"".equals(aForm.getNonStdFreqUnit())) {
					if ((aForm.getNonStdFreq() == null) || aForm.getNonStdFreq().trim().equals("")) {
						errors.add("nonStdFreq", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "1"));
						DefaultLogger.debug(LOGOBJ, "aForm.getNonStdFreq() = " + aForm.getNonStdFreq());
					}
				}

				if (aForm.getNextRevalDate() != null) {
					if (!(errorCode = Validator.checkDate(aForm.getNextRevalDate(), false, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("nextRevalDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "256"));
						DefaultLogger.debug(LOGOBJ, "aForm.getNextRevalDate() = " + aForm.getNextRevalDate());
					}
				}

				if (aForm.getEvalDateFSV() != null) {
					if (!(errorCode = Validator.checkDate(aForm.getEvalDateFSV(), false, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("evalDateFSV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "256"));
						DefaultLogger.debug(LOGOBJ, "aForm.getEvalDateFSV() = " + aForm.getEvalDateFSV());
					}
				}

				if (!subTypeCode.startsWith(ICMSConstant.SECURITY_TYPE_CASH)) {
					if (aForm.getValBefMargin() != null) {
						if (!(errorCode = Validator.checkAmount(aForm.getValBefMargin(), false, 0,
								IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, IGlobalConstant.DEFAULT_CURRENCY, locale))
								.equals(Validator.ERROR_NONE)) {
							errors.add("valBefMargin", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
									errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
							DefaultLogger.debug(LOGOBJ, "aForm.getValBefMargin() = " + aForm.getValBefMargin());
						}
					}
				}

				if (aForm.getAmountCMV() != null && !aForm.getAmountCMV().equals("")) {
				    //Start:Uma Khot: added to allow upto decimal 4 in Security OMV for Markatable Sec-Mutual Fund Security
					if(aForm instanceof MarksecMainLocalForm){
						if (!(errorCode = Validator.checkNumber(aForm.getAmountCMV(), false, 0,
								IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_4,5, locale))
								.equals(Validator.ERROR_NONE)) {
							errors.add("amountCMV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
									"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_4));
							DefaultLogger.debug(LOGOBJ, "aForm.getAmountCMV() = " + aForm.getAmountCMV());
						}
					}
				    //End:Uma Khot: added to allow upto decimal 4 in Security OMV for Markatable Sec-Mutual Fund Security
					else{
						if (!(errorCode = Validator.checkAmount(aForm.getAmountCMV(), false, 0,
								IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
								.equals(Validator.ERROR_NONE)) {
							errors.add("amountCMV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
									"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
							DefaultLogger.debug(LOGOBJ, "aForm.getAmountCMV() = " + aForm.getAmountCMV());
						}
						else {
							errorCode = UIUtil.compareExponentialValue(aForm.getAmountCMV(), IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR);
							if(!Validator.ERROR_NONE.equals(errorCode)) {
								errors.add("amountCMV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
										"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
							}
						}
					}
				}

				if (aForm.getReservePrice() != null && !aForm.getReservePrice().equals("")) {
					if (!(errorCode = Validator.checkAmount(aForm.getReservePrice(), false, 0,
							IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, IGlobalConstant.DEFAULT_CURRENCY, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("reservePrice", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
								errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
						DefaultLogger.debug(LOGOBJ, "aForm.getReservePrice() = " + aForm.getReservePrice());
					}
				}
			}

			if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getRemarks(), false))
					.equals(Validator.ERROR_NONE)) {
				errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
				DefaultLogger.debug(LOGOBJ, "aForm.getRemarks() = " + aForm.getRemarks());
			}

			if (aForm.getNetRealisableSecValue() != null && !aForm.getNetRealisableSecValue().equals("")) {
				if (!(errorCode = Validator.checkAmount(aForm.getNetRealisableSecValue(), false, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("netRealisableSecValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
							errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_STR));
				}
			}

			if ("Y".equals(aForm.getExchangeControl())) {
				if (!(errorCode = Validator.checkDate(aForm.getExchangeControlDate(), true, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("exchangeControlDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "256"));
					DefaultLogger.debug(LOGOBJ, " aForm.getExchangeControlDate()= "
							+ aForm.getExchangeControlDate());
				}
			}
			
			//New General Field
			if(aForm.getSecurityMargin() != null && !"".equals(aForm.getSecurityMargin()))
			{
				
				if (!(errorCode = Validator.checkNumber(aForm.getSecurityMargin(), false, 0, 100.00))
						.equals(Validator.ERROR_NONE)) {
					errors.add("securityMarginError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", 100.00 + ""));
					DefaultLogger.debug(LOGOBJ, "aForm.getSecurityMargin() = " + aForm.getSecurityMargin());
				}
			}
			//End
			
		}
		catch (Exception e) {
			DefaultLogger.debug(LOGOBJ, "error encountered" + e.toString());
		}

		if (!ERROR_NONE.equals(errorCode = NumberValidator.checkNumber(aForm.getCoverageAmount(), false,
				BigDecimal.ZERO, MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL, 3, locale))) {
			errors.add("coverageAmountError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0, MAXIMUM_ALLOWED_AMOUNT_25_2_STR));
		}
		
		if (!ERROR_NONE.equals(errorCode = NumberValidator.checkNumber(aForm.getAdHocCoverageAmount(), false,
				BigDecimal.ZERO, MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL, 3, locale))) {
			errors.add("adhocCoverageAmountError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0, MAXIMUM_ALLOWED_AMOUNT_25_2_STR));
		}
		
		if (!ERROR_NONE.equals(errorCode = NumberValidator.checkNumber(aForm.getCoveragePercentage(), false,
				BigDecimal.ZERO, MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL, 3, locale))) {
			errors.add("coveragePercentageError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0, MAXIMUM_ALLOWED_AMOUNT_25_2_STR));
		}
		
		
		DefaultLogger.debug(LOGOBJ, "CollateralValidator , No of Errors..." + errors.size());
		return errors;
	}
	
	public static ActionErrors validateCersaiFields(CollateralForm aForm, Locale locale, ActionErrors errors) {
		String errorCode = null;
		Date currDate = DateUtil.getDate();
		
		if(null != errors && ICMSConstant.YES.equals(aForm.getCersaiApplicableInd())) {
			Boolean isSecurityOwnershipThirdParty = false;
			Boolean isCinForThirdPartyValidationMandatory = false;
			Boolean isCollateralCategoryImmovable = false;
			
			if (CommonCodeEntryConstant.SecurityOwnershipCodes.THIRD_PARTY.equals(aForm.getSecurityOwnership())) {
				isSecurityOwnershipThirdParty = true;
			}
			
			if(CommonCodeEntryConstant.CollateralCategoryCodes.IMMOVABLE.equals(aForm.getCollateralCategory())) {
				isCollateralCategoryImmovable = true;
			}
			
			List<String> mandatoryThirdPartyEntities = CollateralHelper.getMandatoryEntitiesForCinForThirdParty();
			if(mandatoryThirdPartyEntities.contains(aForm.getThirdPartyEntity())) {
				isCinForThirdPartyValidationMandatory = true;
			}
			
			//1 Security Ownership 
			if(isCollateralCategoryImmovable && 
					StringUtils.isBlank(aForm.getSecurityOwnership())) {
				errors.add("securityOwnershipError", new ActionMessage("error.string.mandatory"));
			}
			
			//2 Owner of Property
			if (!(errorCode = Validator.checkString(aForm.getOwnerOfProperty(), isSecurityOwnershipThirdParty, 1, 51))
					.equals(Validator.ERROR_NONE)) {
				errors.add("ownerOfPropertyError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "1", "50"));
				DefaultLogger.debug(LOGOBJ, " aForm.getOwnerOfProperty() = " + aForm.getOwnerOfProperty());
			}
			
			//3 Third Party Entity Auto Populate Name
			if(isSecurityOwnershipThirdParty && StringUtils.isBlank(aForm.getThirdPartyEntity())) {
				errors.add("thirdPartyEntityError", new ActionMessage("error.string.mandatory"));
			}
			
			//4 CIN Third party Mandatory for Pvt. Ltd. Co. / Ltd. Co. / LLP
			if("THIRD_PARTY".equalsIgnoreCase(aForm.getSecurityOwnership())) {
			
			if (!(errorCode = Validator.checkString(aForm.getCinForThirdParty(), isCinForThirdPartyValidationMandatory, 1, 30))
					.equals(Validator.ERROR_NONE)) {
				errors.add("cinForThirdPartyError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "1", "30"));
				DefaultLogger.debug(LOGOBJ, " aForm.getCinForThirdParty() = " + aForm.getCinForThirdParty());
			}
//			else {
//				String cinForThirdParty = aForm.getCinForThirdParty().trim();
//				int ind=0;
//				ind=cinForThirdParty.indexOf("-");
				
				
//				if(StringUtils.isNotBlank(cinForThirdParty) && (!ASSTValidator.isAlphaNumericStringWithHyphen(cinForThirdParty) ||
//				if(StringUtils.isNotBlank(cinForThirdParty) && (!cinForThirdParty.contains("-") ||
//						!CharUtils.isAsciiAlpha(cinForThirdParty.charAt(0)) ||
//						(cinForThirdParty.length() > 0 && !CharUtils.isAsciiNumeric(cinForThirdParty.charAt(cinForThirdParty.length()-1))))){
//					errors.add("cinForThirdPartyError", new ActionMessage("error.string.format"));
//					DefaultLogger.debug(LOGOBJ, " aForm.getCinForThirdParty() = " + aForm.getCinForThirdParty());
					
				
//				if ((StringUtils.isNotBlank(cinForThirdParty)) && ((ind == -1 ) || 
//							(!CharUtils.isAsciiAlpha(cinForThirdParty.charAt(0))) || 
//							((cinForThirdParty.length() > 0) && (!CharUtils.isAsciiNumeric(cinForThirdParty.charAt(cinForThirdParty.length() - 1))))))
//			        {
//			          errors.add("cinForThirdPartyError", new ActionMessage("error.string.format"));
//			          DefaultLogger.debug(LOGOBJ, " aForm.getCinForThirdParty() = " + aForm.getCinForThirdParty());
//			        }
				
				
//				}
//			}
			}
			//5 CERSAI Transaction Reference Number
			PropertyForm propForm=null;
			if(aForm instanceof PropCommGeneralForm) {
				 propForm = (PropCommGeneralForm)aForm;
			}
			
			boolean flags = false;
			
			if(aForm.getCersaiTransactionRefNumber() != null && !aForm.getCersaiTransactionRefNumber().isEmpty()) {
			if (!(errorCode = Validator.checkString(aForm.getCersaiTransactionRefNumber(), true, 1, 30))
					.equals(Validator.ERROR_NONE)) {
				errors.add("cersaiTransactionRefNumberError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "1", "30"));
				DefaultLogger.debug(LOGOBJ, " aForm.getCersaiTransactionRefNumber() = " + aForm.getCersaiTransactionRefNumber());
			}
			}else if("PT701".equals(aForm.getSubTypeCode()) && "true".equals(propForm.getMortgageCreationAdd())
					&& (aForm.getCersaiTransactionRefNumber()==null || aForm.getCersaiTransactionRefNumber().isEmpty())) {
				errors.add("cersaiTransactionRefNumberError", new ActionMessage("error.string.mandatory"));
				flags = true;
			}
			
		/*	if("PT701".equals(aForm.getSubTypeCode()) && propForm.getSalePurchaseDate() != null && !"".equals(propForm.getSalePurchaseDate()) && flags == false
					&& (aForm.getCersaiTransactionRefNumber()==null || aForm.getCersaiTransactionRefNumber().isEmpty())) {
				errors.add("cersaiTransactionRefNumberError", new ActionMessage("error.string.mandatory"));
			}*/
			flags = false;
			//6
			if(aForm.getCersaiSecurityInterestId() != null && !aForm.getCersaiSecurityInterestId().isEmpty()) {
			if (!(errorCode = Validator.checkString(aForm.getCersaiSecurityInterestId(), true, 1, 30))
					.equals(Validator.ERROR_NONE)) {
				errors.add("cersaiSecurityInterestIdError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "1", "30"));
				DefaultLogger.debug(LOGOBJ, " aForm.getCersaiSecurityInterestId() = " + aForm.getCersaiSecurityInterestId());
			}
			}
			
			//7
			if(aForm.getCersaiAssetId() != null && !aForm.getCersaiAssetId().isEmpty()) {
			if (!(errorCode = Validator.checkString(aForm.getCersaiAssetId(), true, 1, 30))
					.equals(Validator.ERROR_NONE)) {
				errors.add("cersaiAssetIdError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "1", "30"));
				DefaultLogger.debug(LOGOBJ, " aForm.getCersaiAssetId() = " + aForm.getCersaiAssetId());
			}
			}
			
			//8 Date of CERSAI registration
//			if (!(errorCode = Validator.checkDate(aForm.getDateOfCersaiRegisteration(), true, locale))
//					.equals(Validator.ERROR_NONE)) {
//				errors.add("dateOfCersaiRegisterationError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "256"));
//				DefaultLogger.debug(LOGOBJ, "aForm.getDateOfCersaiRegisteration() = " + aForm.getDateOfCersaiRegisteration());
//			}
//			else 
				if (StringUtils.isNotBlank(aForm.getDateOfCersaiRegisteration())) {
				Date cersaiRegisterationDate = DateUtil.convertDate(locale, aForm.getDateOfCersaiRegisteration());
				if (cersaiRegisterationDate.after(currDate)) {
					errors.add("dateOfCersaiRegisterationError", new ActionMessage("error.future.date"));
					DefaultLogger.debug(LOGOBJ, "aForm.getDateOfCersaiRegisteration() = " + aForm.getDateOfCersaiRegisteration());
				}
			}else if("PT701".equals(aForm.getSubTypeCode()) && "true".equals(propForm.getMortgageCreationAdd())
					&& (aForm.getDateOfCersaiRegisteration()==null || aForm.getDateOfCersaiRegisteration().isEmpty())) {
				errors.add("dateOfCersaiRegisterationError", new ActionMessage("error.string.mandatory"));
				flags = true;
			}
				/*if("PT701".equals(aForm.getSubTypeCode()) && propForm.getSalePurchaseDate() != null && !"".equals(propForm.getSalePurchaseDate()) && flags == false
					&& (aForm.getDateOfCersaiRegisteration()==null || aForm.getDateOfCersaiRegisteration().isEmpty())) {
				errors.add("dateOfCersaiRegisterationError", new ActionMessage("error.string.mandatory"));
			}*/
			
				flags = false;
			
			//9
				if(aForm.getCersaiId() != null && !aForm.getCersaiId().isEmpty()) {
					if (!(errorCode = Validator.checkString(aForm.getCersaiId(), true, 1, 30))
							.equals(Validator.ERROR_NONE)) {
						errors.add("cersaiIdError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
								errorCode), "1", "30"));
						DefaultLogger.debug(LOGOBJ, " aForm.getCersaiId() = " + aForm.getCersaiId());
					}
			
			}else if("PT701".equals(aForm.getSubTypeCode()) && "true".equals(propForm.getMortgageCreationAdd())
					&& (aForm.getCersaiId()==null || aForm.getCersaiId().isEmpty())) {
				errors.add("cersaiIdError", new ActionMessage("error.string.mandatory"));
				flags = true;
			}
			/*	if("PT701".equals(aForm.getSubTypeCode()) && propForm.getSalePurchaseDate() != null && !"".equals(propForm.getSalePurchaseDate()) && flags == false
					&& (aForm.getCersaiId()==null || aForm.getCersaiId().isEmpty())) {
				errors.add("cersaiIdError", new ActionMessage("error.string.mandatory"));
			}*/
				flags = false;
				
			//10 Sale Deed / Sale & Purchase Agreement Date
			if (!(errorCode = Validator.checkDate(aForm.getSaleDeedPurchaseDate(), true, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("saleDeedPurchaseDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "256"));
				DefaultLogger.debug(LOGOBJ, "aForm.getSaleDeedPurchaseDate() = " + aForm.getSaleDeedPurchaseDate());
			}
			else if (StringUtils.isNotBlank(aForm.getSaleDeedPurchaseDate())) {
				Date saleDeedPurchaseDate = DateUtil.convertDate(locale, aForm.getSaleDeedPurchaseDate());
				if (saleDeedPurchaseDate.after(currDate)) {
					errors.add("saleDeedPurchaseDateError", new ActionMessage("error.future.date"));
					DefaultLogger.debug(LOGOBJ, "aForm.getSaleDeedPurchaseDate() = " + aForm.getSaleDeedPurchaseDate());
				}
			}
			
			//11 Third Party Address
			if (!(errorCode = Validator.checkString(aForm.getThirdPartyAddress(), isSecurityOwnershipThirdParty, 1, 150))
					.equals(Validator.ERROR_NONE)) {
				errors.add("thirdPartyAddressError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "1", "150"));
				DefaultLogger.debug(LOGOBJ, " aForm.getThirdPartyAddress() = " + aForm.getThirdPartyAddress());
			}
			
			//12 Third Party State
			if(isSecurityOwnershipThirdParty && StringUtils.isBlank(aForm.getThirdPartyState())) {
				errors.add("thirdPartyStateError", new ActionMessage("error.string.mandatory"));
			}
			
			//13 Third Party City
			if(isSecurityOwnershipThirdParty && StringUtils.isBlank(aForm.getThirdPartyCity())) {
				errors.add("thirdPartyCityError", new ActionMessage("error.string.mandatory"));
			}
			
			//14 Third Party Pincode
			if (!(errorCode = Validator.checkString(aForm.getThirdPartyPincode(), isSecurityOwnershipThirdParty, 1, 6))
					.equals(Validator.ERROR_NONE)) {
				errors.add("thirdPartyPincodeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "1", "6"));
				DefaultLogger.debug(LOGOBJ, " aForm.getThirdPartyPincode() = " + aForm.getThirdPartyPincode());
			}
			else if(!StringUtils.isNumeric(aForm.getThirdPartyPincode())){
					errors.add("thirdPartyPincodeError", new ActionMessage("error.string.format"));
					DefaultLogger.debug(LOGOBJ, " aForm.getThirdPartyPincode() = " + aForm.getThirdPartyPincode());
			}
			else if(StringUtils.isNotBlank(aForm.getStatePincodeString()) && StringUtils.isNotBlank(aForm.getThirdPartyState())) {
				 Map<String, String> statePincodeMap = UIUtil.getMapFromDelimitedString(aForm.getStatePincodeString(), ",", "=");
				 if(null != statePincodeMap && !statePincodeMap.isEmpty()) {
					 String selectedStatePincode = statePincodeMap.get(aForm.getThirdPartyState());
					 if(null != selectedStatePincode && !aForm.getThirdPartyPincode().trim().startsWith(selectedStatePincode)) {
						 errors.add("thirdPartyPincodeError", new ActionMessage("error.pincode.incorrect"));
					 }
					 DefaultLogger.debug(LOGOBJ, " aForm.getThirdPartyPincode() = " + aForm.getThirdPartyPincode());
				 }
			 }
		}
		return errors;
	}
}
