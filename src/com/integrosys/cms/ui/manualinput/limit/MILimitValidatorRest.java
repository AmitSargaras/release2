/*
 * Created on 2007-2-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.aurionpro.clims.rest.dto.FacilityBodyRestRequestDTO;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.IBankingArrangementFacExclusionJdbc;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.fileUpload.bus.FileUploadJdbcImpl;
import com.integrosys.cms.app.fileUpload.bus.OBReleaselinedetailsFile;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.IUdfDao;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.bankingArrangementFacExclusion.IBankingArrangementFacExclusionConstant;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class MILimitValidatorRest {
	public static ActionErrors validateMILimit(ActionForm aForm, Locale locale, FacilityBodyRestRequestDTO facilityRequestDTO) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		Date currDate = DateUtil.getDate();
		int length = 0 ,numeric = 0 ;
		boolean isReqBankArrFacExc = true;
		try {
			LmtDetailForm lmtForm = (LmtDetailForm) aForm;

				/*if(lmtForm!=null && "WS_FAC_EDIT".equalsIgnoreCase(lmtForm.getEvent())){
					if(lmtForm.getClimsFacilityId()==null || lmtForm.getClimsFacilityId().trim().isEmpty()){
						errors.add("climsFacilityIdError",new ActionMessage("error.string.mandatory"));
					}
				}*/

				if (!(errorCode = Validator.checkString(lmtForm.getFacilityCat(), true, 1, 40)).equals(Validator.ERROR_NONE)) { 
					errors.add("facilityCat", new ActionMessage("error.string.mandatory", "1", "40")); 
				}

				if (!(errorCode = Validator.checkString(lmtForm.getFacilityName(), true, 1, 100)).equals(Validator.ERROR_NONE)) { 
					errors.add("facilityName", new ActionMessage("error.invalid", "1", "100")); 
					isReqBankArrFacExc = false;
				}else{
					MILimitUIHelper helper = new MILimitUIHelper();
					SBMILmtProxy proxy = helper.getSBMILmtProxy();
					ArrayList sysIDs = (ArrayList) proxy.getSystemID(lmtForm.getFacilitySystem(), lmtForm.getCustomerID());

					if (sysIDs.size() <= 0) { 
						errors.add("facilitySystemID", new ActionMessage("error.facility.not.attached")); 
						isReqBankArrFacExc = false;
					}
				}


				if (!(errorCode = Validator.checkString(lmtForm.getGrade(), true, 1, 40)).equals(Validator.ERROR_NONE)) { 
					errors.add("facilityGrade", new ActionMessage("error.string.mandatory")); 
				}
				if(lmtForm.getLimitType().equals("Yes")) {
					if (!(errorCode = Validator.checkString(lmtForm.getGuarantee(), true, 1, 40)).equals(Validator.ERROR_NONE)) { 
						errors.add("guarantee", new ActionMessage("error.string.mandatory")); 
					}
				}
				if(lmtForm.getLimitType().equals("Yes")  && "Yes".equals(lmtForm.getGuarantee())) {
					lmtForm.setSubFacilityName("");
					if (!(errorCode = Validator.checkString(lmtForm.getSubPartyName(), true, 1, 200)).equals(Validator.ERROR_NONE)) { 
						errors.add("subPartyName", new ActionMessage("error.string.mandatory")); 
					}
					if (!(errorCode = Validator.checkString(lmtForm.getLiabilityID(), true, 1, 200)).equals(Validator.ERROR_NONE)) { 
						errors.add("liabilityID", new ActionMessage("error.string.mandatory")); 
					}
				}
				if(lmtForm.getLimitType().equals("Yes")  && "No".equals(lmtForm.getGuarantee())) {
					lmtForm.setSubPartyName("");
					if (!(errorCode = Validator.checkString(lmtForm.getSubFacilityName(), true, 1, 200)).equals(Validator.ERROR_NONE)) { 
						errors.add("facilityNameGuar", new ActionMessage("error.string.mandatory")); 
					}
				}
				
				/*if(null != facilityRequestDTO.getFacilityCategoryId() && !facilityRequestDTO.getFacilityCategoryId().trim().isEmpty())
				{
					if(! facilityRequestDTO.getFacilityCategoryId().equals(lmtForm.getFacilityCat()))
					{
						errors.add("facilityCat", new ActionMessage("error.invalid"));
					}
				}
				if(null != facilityRequestDTO.getFacilityTypeId() && !facilityRequestDTO.getFacilityTypeId().trim().isEmpty())
				{
					if(! facilityRequestDTO.getFacilityTypeId().equals(lmtForm.getFacilityType()))
					{
						errors.add("facilityTypeId", new ActionMessage("error.invalid"));
					}
				}*/

				if(lmtForm.getIsAdhoc()!= null && !lmtForm.getIsAdhoc().equals("") && !lmtForm.getIsAdhoc().equals("N")){
					//				 if(AbstractCommonMapper.isEmptyOrNull(lmtForm.getIsAdhocToSum())){
					//					 errors.add("isAdhocToSum", new ActionMessage("error.string.mandatory"));
					//				 }else{
					if (!(errorCode =Validator.checkAmount(lmtForm.getAdhocLmtAmount(), true,1, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_22_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("adhocLmtAmount", new ActionMessage(ErrorKeyMapper
								.map(ErrorKeyMapper.NUMBER, errorCode), "1",
								IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_22_2_STR));
					}else {
						boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(UIUtil.removeComma(lmtForm.getAdhocLmtAmount()));
						if( codeFlag == true) {
							errors.add("adhocLmtAmount", new ActionMessage("error.string.invalidCharacter"));
						}
					}
					//				 }
				}else{
					lmtForm.setAdhocLmtAmount("0");
				}
				
				//StringUtils.isNotBlank(lmtForm.getTenor()) && StringUtils.isBlank(lmtForm.getTenorUnit())
				
				if(StringUtils.isNotBlank(lmtForm.getTenor()) && StringUtils.isBlank(lmtForm.getTenorUnit())) {
					errors.add("tenor", new ActionMessage("error.string.mandatory"));
				}else if(StringUtils.isBlank(lmtForm.getTenor()) && StringUtils.isNotBlank(lmtForm.getTenorUnit())) {
					errors.add("tenorVal", new ActionMessage("error.string.mandatory"));
				}
				else if(StringUtils.isNotBlank(lmtForm.getTenor()) &&
						!(errorCode = Validator.checkNumber(lmtForm.getTenor(), false, 0, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_3)).equals(Validator.ERROR_NONE)){
					errors.add("tenorVal", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_3_STR));
				}
				else if(StringUtils.isNotBlank(lmtForm.getTenor()) && lmtForm.getTenor().contains("."))
				{
					errors.add("tenorVal", new ActionMessage("error.tenor.three.digit"));
				}
				if (!(errorCode = Validator.checkString(lmtForm.getTenorDesc(), false, 1, 100)).equals(Validator.ERROR_NONE)) {
					errors.add("tenorDesc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "100"));
				}
				

				/* ILimitProfile profile;
			    ILimitProxy limitProxy = LimitProxyFactory.getProxy();
				profile = limitProxy.getLimitProfile(Long.parseLong(lmtForm.getLimitProfileID()));
				ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
				//Fetching Party Details and set to the context 
				ICMSCustomer cust = custProxy.getCustomerByCIFSource(profile.getLEReference(),null);
				ILimitProxy proxyLimit = null;
				proxyLimit= LimitProxyFactory.getProxy();
				String currentFacSanc = proxyLimit.getTotalAmountByFacType(lmtForm.getLimitProfileID(),lmtForm.getFacilityType());
				Double totalFacSanctionedAmount = Double.parseDouble(currentFacSanc);
				Double totalPartySanctionedAmount = 0d;
				if(lmtForm.getFacilityType()!=null && lmtForm.getFacilityType().equalsIgnoreCase("FUNDED"))
				{
				String currentPartySanc =cust.getTotalFundedLimit();
				 totalPartySanctionedAmount = Double.parseDouble(currentPartySanc);
				}
				else if(lmtForm.getFacilityType()!=null && lmtForm.getFacilityType().equalsIgnoreCase("NON_FUNDED")){
					String currentPartySanc =cust.getTotalNonFundedLimit();
					 totalPartySanctionedAmount = Double.parseDouble(currentPartySanc);
				}
				else if(lmtForm.getFacilityType()!=null && lmtForm.getFacilityType().equalsIgnoreCase("MEMO_EXPOSURE")){
					String currentPartySanc =cust.getMemoExposure();
					 totalPartySanctionedAmount = Double.parseDouble(currentPartySanc);
				}

				Double finalFacAmount = totalFacSanctionedAmount + Double.parseDouble(lmtForm.getSanctionedLimit());

			 if(finalFacAmount>totalPartySanctionedAmount)
			 {
				 errors.add("requiredSecCov", new ActionMessage("error.amount.not.greaterthan", "Sanctioned Amount", "Total "+lmtForm.getFacilityType()+" Amount"));
			 }*/

				BigDecimal requiredSecCov =new BigDecimal("0");
				BigDecimal tempRequiredSecCov =new BigDecimal("0");
				BigDecimal releasableAmount = new BigDecimal("0");
				BigDecimal totalReleasedAmount =new BigDecimal("0");
				boolean flag = false;
				DefaultLogger.debug(lmtForm," FacilityType ==1===> "+lmtForm.getFacilityType() );
				DefaultLogger.debug(lmtForm,"Required Security Coverage [Note :- check is not null/empty then flag = true] ==2===> "+lmtForm.getRequiredSecCov());
				if (!(errorCode =Validator.checkAmount(lmtForm.getRequiredSecCov(), true, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE))   {
					errors.add("requiredSecCov", new ActionMessage(
							ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2_STR));
				}

				if (!AbstractCommonMapper.isEmptyOrNull(lmtForm.getRequiredSecCov())) {
					if (!(errorCode =Validator.checkAmount(lmtForm.getRequiredSecCov(), false,0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
							.equals(Validator.ERROR_NONE))   {
						errors.add("requiredSecCov", new ActionMessage(
								ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2_STR));

					}
					flag = true;
				}
				//			 if (!AbstractCommonMapper.isEmptyOrNull(lmtForm.getRequiredSecCov())) {
				//			 if(ASSTValidator.isValidAlphaNumStringWithSpace(lmtForm.getRequiredSecCov())) {
				//					errors.add("requiredSecCov", new ActionMessage("error.string.invalidCharacter"));
				//			 }
				//			 }
				//			 if (!AbstractCommonMapper.isEmptyOrNull(lmtForm.getReleasableAmount())) {
				//				 if(ASSTValidator.isValidAlphaNumStringWithSpace(lmtForm.getReleasableAmount())) {
				//						errors.add("releasableAmount", new ActionMessage("error.string.invalidCharacter"));
				//				 }
				//			 }

				BigDecimal exchangeRate = null;
				if(!AbstractCommonMapper.isEmptyOrNull(lmtForm.getCurrencyCode()) && !AbstractCommonMapper.isEmptyOrNull(lmtForm.getRequiredSecCov())){
					IForexFeedProxy frxPxy = (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
					exchangeRate = frxPxy.getExchangeRateWithINR(lmtForm.getCurrencyCode().trim());
					DefaultLogger.debug(lmtForm,"exchangeRate [CurrencyCode() of Required Security Coverage ]==3===> "+ lmtForm.getCurrencyCode()+" / " +exchangeRate);
				}
				//			if (!(errorCode = Validator.checkNumber(lmtForm.getReleasableAmount(), true, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, 2, locale)).equals(Validator.ERROR_NONE)) {
				//					errors.add("releasableAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0"));
				//					//releasableAmount = Double.parseDouble(lmtForm.getReleasableAmount());
				//					//requiredSecCov = Double.parseDouble(lmtForm.getRequiredSecCov());
				//					
				//			}

				boolean totalReleasedAmountFlag = false; //Flag is maintained so that at a time only one validation can be done one Released Amount

				if(flag){
					DefaultLogger.debug(lmtForm,"[flag = true] and [Required Security Coverage()/CurrencyCode]==4===> "+ lmtForm.getRequiredSecCov()+" / "+lmtForm.getCurrencyCode()+" " +exchangeRate);
					if(!lmtForm.getRequiredSecCov().trim().equals("") && !AbstractCommonMapper.isEmptyOrNull(lmtForm.getCurrencyCode())){
						DefaultLogger.debug(lmtForm,"[Required Security Coverage() / AdhocLmtAmount] ==5===> "+ lmtForm.getRequiredSecCov()+" / "+lmtForm.getAdhocLmtAmount());
						//			 			requiredSecCov = new BigDecimal(lmtForm.getRequiredSecCov().replaceAll(",", "")).add(new BigDecimal(lmtForm.getAdhocLmtAmount().replaceAll(",", "")));
						requiredSecCov = new BigDecimal(lmtForm.getRequiredSecCov().replaceAll(",", ""));

						tempRequiredSecCov = requiredSecCov.multiply(exchangeRate);
						DefaultLogger.debug(lmtForm,"tempRequiredSecCov = [requiredSecCov.multiply(exchangeRate)] ==6===> "+ tempRequiredSecCov);
					}

					if(lmtForm.getReleasableAmount() !=null && !lmtForm.getReleasableAmount().trim().equals("")){
						releasableAmount = new BigDecimal(lmtForm.getReleasableAmount().replaceAll(",", ""));
						DefaultLogger.debug(lmtForm,"releasableAmount[lmtForm.getReleasableAmount()] ==7===> "+ releasableAmount);
					}

					if(lmtForm.getTotalReleasedAmount()!=null && !lmtForm.getTotalReleasedAmount().trim().equals("")){
						totalReleasedAmount = new BigDecimal(lmtForm.getTotalReleasedAmount().replaceAll(",", ""));
						DefaultLogger.debug(lmtForm,"totalReleasedAmount[lmtForm.getTotalReleasedAmount()] ==8===> "+ totalReleasedAmount);
					}

					if( totalReleasedAmount.compareTo(releasableAmount) > 0){
						DefaultLogger.debug(lmtForm,"[Released Amount > releasableAmount] ==9===> "+totalReleasedAmount +" > " +releasableAmount);
						if(!"WS_FAC_EDIT".equalsIgnoreCase(lmtForm.getEvent()) && !"WS_FAC_CREATE".equalsIgnoreCase(lmtForm.getEvent())){
							errors.add("totalReleasedAmount", new ActionMessage("error.amount.not.greaterthan", "Released Amount", "Releasable Amount"));
						}
						totalReleasedAmountFlag=true;
					}
				}else{
					DefaultLogger.debug(lmtForm,"flag = false ==10===> "+flag);
					if(lmtForm.getReleasableAmount()!=null && !lmtForm.getReleasableAmount().trim().equals("")){
						releasableAmount = new BigDecimal(lmtForm.getReleasableAmount().replaceAll(",", ""));
						DefaultLogger.debug(lmtForm,"[releasableAmount] ==11===> "+releasableAmount);
					}

					if(lmtForm.getTotalReleasedAmount()!=null && !lmtForm.getTotalReleasedAmount().trim().equals("")){
						totalReleasedAmount = new BigDecimal(lmtForm.getTotalReleasedAmount().replaceAll(",", ""));
						DefaultLogger.debug(lmtForm,"[totalReleasedAmount] ==12===> "+totalReleasedAmount);
					}
					if( totalReleasedAmount.compareTo(releasableAmount) > 0){
						DefaultLogger.debug(lmtForm,"[Released Amount > releasableAmount] ==13===> "+totalReleasedAmount +" > " +releasableAmount);
						if(!"WS_FAC_EDIT".equalsIgnoreCase(lmtForm.getEvent()) && !"WS_FAC_CREATE".equalsIgnoreCase(lmtForm.getEvent())){
							errors.add("totalReleasedAmount", new ActionMessage("error.amount.not.greaterthan", "Released Amount", "Releasable Amount"));
						}
						totalReleasedAmountFlag=true;
					}
				}

				DefaultLogger.debug(lmtForm,"Flag Checking END ==14===> ");
				if (!(errorCode =Validator.checkAmount(lmtForm.getReleasableAmount(), false,0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)){
					errors.add("releasableAmount", new ActionMessage(
							ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2_STR));
					releasableAmount = new BigDecimal(lmtForm.getReleasableAmount());
					requiredSecCov = new BigDecimal(lmtForm.getRequiredSecCov());
				}else if( releasableAmount.compareTo(requiredSecCov) > 0){
					DefaultLogger.debug(lmtForm,"[Released Amount > Sanctioned Amount ] ==15===> "+releasableAmount +" > " +requiredSecCov);
					errors.add("releasableAmount", new ActionMessage("error.amount.not.greaterthan", "Releasable Amount", "Sanctioned Amount"));
				}

				// Based on the requirement shared by bank (Ganesh)on dt. 21-Jun-2016, Below validation has been commented.

				//Start:Added to support the validation "Sum of released amount of main limit and sub limit should not exceed the sanction amount of main limit."
				/*LimitDAO limitDAO=new LimitDAO();
			if(!totalReleasedAmountFlag){
				if(!"WS_FAC_EDIT".equalsIgnoreCase(lmtForm.getEvent()) && !"WS_FAC_CREATE".equalsIgnoreCase(lmtForm.getEvent())){	
					if(flag && lmtForm.getLimitType().equals("No")){
						List<BigDecimal> releasedAmount=limitDAO.getReleasedAmountOfSubLimit(lmtForm.getLimitRef(),"");
						if(releasedAmount.size()>0){
							BigDecimal subLimitReleasedAmountSum = new BigDecimal("0");

						//List<BigDecimal> sanctionedAndReleasedAmount=limitDAO.getSanctionedAndReleasedAmountOfMainLimit(lmtForm.getLimitId());
						BigDecimal mainLimitSanctionedAmount = new BigDecimal(lmtForm.getRequiredSecCov());
						BigDecimal mainLimitReleasedAmount =  new BigDecimal(lmtForm.getTotalReleasedAmount());
						for(BigDecimal amount:releasedAmount){
							subLimitReleasedAmountSum=subLimitReleasedAmountSum .add(amount);
						}

						BigDecimal sumOfReleasedAmount= mainLimitReleasedAmount.add(subLimitReleasedAmountSum);
						if( sumOfReleasedAmount.compareTo(mainLimitSanctionedAmount) > 0){
							DefaultLogger.debug(lmtForm,"[sumOfReleasedAmount > mainLimitSanctionedAmount ] ==15===> "+sumOfReleasedAmount +" > " +mainLimitSanctionedAmount);
								errors.add("totalReleasedAmount", new ActionMessage("error.amount.not.greaterthan", "Sum of Released Amount of MainLimit and SubLimit", "Sanctioned Amount of MainLimit"));
						}
						}
					}
					else if(flag && lmtForm.getLimitType().equals("Yes") && !lmtForm.getMainFacilityId().isEmpty()){
						List<BigDecimal> releasedAmount=limitDAO.getReleasedAmountOfSubLimit(lmtForm.getMainFacilityId(),lmtForm.getLimitRef());
						BigDecimal subLimitReleasedAmountSum = new BigDecimal("0");
						List<BigDecimal> sanctionedAndReleasdAmount=limitDAO.getSanctionedAndReleasedAmountOfMainLimit(lmtForm.getMainFacilityId());
						BigDecimal mainLimitSanctionedAmount = sanctionedAndReleasdAmount.get(0);
						BigDecimal mainLimitReleasedAmount =sanctionedAndReleasdAmount.get(1);

						for(BigDecimal amount:releasedAmount){
							subLimitReleasedAmountSum=subLimitReleasedAmountSum .add(amount);
						}

						BigDecimal sumOfReleasedAmount= mainLimitReleasedAmount.add(subLimitReleasedAmountSum).add(new BigDecimal((lmtForm.getTotalReleasedAmount()==null || lmtForm.getTotalReleasedAmount().isEmpty())?"0":lmtForm.getTotalReleasedAmount()));
						if( sumOfReleasedAmount.compareTo(mainLimitSanctionedAmount) > 0){
							DefaultLogger.debug(lmtForm,"[sumOfReleasedAmount > mainLimitSanctionedAmount ] ==15===> "+sumOfReleasedAmount +" > " +mainLimitSanctionedAmount);
								errors.add("totalReleasedAmount", new ActionMessage("error.amount.not.greaterthan", "Sum of Released Amount of MainLimit and SubLimit", "Sanctioned Amount of MainLimit"));
						}

					}
				}
			}*/
				//end

				if (!(errorCode = Validator.checkString(lmtForm.getGrade(), true, 1, 10)).equals(Validator.ERROR_NONE)) { 
					errors.add("grade", new ActionMessage("error.string.mandatory", "1", "40")); 
				}

				//			if (!AbstractCommonMapper.isEmptyOrNull(lmtForm.getRequiredSecCov())) {
				//				if (!(errorCode = Validator.checkNumber(lmtForm.getRequiredSecCov(), false, 0, 999999999999.D))
				//						.equals(Validator.ERROR_NONE)) {
				//
				//					errors.add("requiredSecCov", new ActionMessage(
				//							ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "999999999999.D"));
				//				}
				//			}


				//Shiv 011111

				// commented by bharat to temporary remove totalsancamount validation	on 07/05/2012
				/*
				 * Below code commented on 02-FEB-2016 : As per the confirmation provided by bank this validation should be ignored 
				 * to handle existing CLIMS cases.
				 * 
				 *  Refer mail with subject : "CAM CLIMS Interface - CLIMS vailidation for existing cases for facility/sanctioned amount"
				 * 
				 *
				 */
				
				/*if(flag && lmtForm.getLimitType().equals("No") )
				{
					MILimitUIHelper helper = new MILimitUIHelper();
					SBMILmtProxy proxy = helper.getSBMILmtProxy();
					List lmtList = proxy.getLimitSummaryListByAA("");
					List lmtListFormated = helper.formatLimitListView(lmtList, locale);
					
					BigDecimal funded = new BigDecimal("0");
					BigDecimal nonFunded = new BigDecimal("0");
					BigDecimal memoExposure = new BigDecimal("0");
					BigDecimal convertedAmount = new BigDecimal("0");
					BigDecimal exchangeRate1 = new BigDecimal("1");
					
					for(int i = 0; i < lmtListFormated.size(); i++){
						LimitListSummaryItem lstSummaryItem = (LimitListSummaryItem) lmtListFormated.get(i);
						if(!AbstractCommonMapper.isEmptyOrNull(lstSummaryItem.getCurrencyCode())){
							 IForexFeedProxy frxPxy = (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
							 exchangeRate1 = frxPxy.getExchangeRateWithINR(lstSummaryItem.getCurrencyCode().trim());
						}
						if(lstSummaryItem.getFacilityTypeCode().equalsIgnoreCase("FUNDED") && "No".equalsIgnoreCase(lstSummaryItem.getIsSubLimit())){
							convertedAmount = exchangeRate1.multiply(new BigDecimal(lstSummaryItem.getActualSecCoverage()));
							funded = funded.add(convertedAmount);
						}
						if(lstSummaryItem.getFacilityTypeCode().equalsIgnoreCase("NON_FUNDED") && "No".equalsIgnoreCase(lstSummaryItem.getIsSubLimit())){
							convertedAmount = exchangeRate1.multiply(new BigDecimal(lstSummaryItem.getActualSecCoverage()));
							nonFunded = nonFunded.add(convertedAmount);
						}
						if(lstSummaryItem.getFacilityTypeCode().equalsIgnoreCase("MEMO_EXPOSURE") && "No".equalsIgnoreCase(lstSummaryItem.getIsSubLimit())){
							convertedAmount = exchangeRate1.multiply(new BigDecimal(lstSummaryItem.getActualSecCoverage()));
							memoExposure = memoExposure.add(convertedAmount);
						}
					}
					
					BigDecimal totFunded = new BigDecimal("0");
					BigDecimal totNonFunded = new BigDecimal("0");
					BigDecimal totMemoExposure = new BigDecimal("0");
					if(cust.getTotalFundedLimit() != null)
					{
						totFunded = new BigDecimal(cust.getTotalFundedLimit()).subtract(funded);
					}
					if(cust.getTotalNonFundedLimit() != null){
						totNonFunded = new BigDecimal(cust.getTotalNonFundedLimit()).subtract(nonFunded);
					}
					if(cust.getMemoExposure() != null){
						totMemoExposure = new BigDecimal(cust.getMemoExposure()).subtract(memoExposure);
					}
					lmtForm.setFundedAmount(totFunded.toString());
					lmtForm.setNonFundedAmount(totNonFunded.toString());
					lmtForm.setMemoExposer(totMemoExposure.toString());


				}*/
				
				if(flag && lmtForm.getLimitType().equals("No")){

					DefaultLogger.debug(lmtForm,"Old Flag is true(Flag checked again) ==16===> Flag = true");
					
					
					
					BigDecimal funded = new BigDecimal(lmtForm.getFundedAmount().replaceAll(",", ""));
					BigDecimal nonFunded = new BigDecimal(lmtForm.getNonFundedAmount().replaceAll(",", ""));
					BigDecimal memoExposer = new BigDecimal(lmtForm.getMemoExposer().replaceAll(",", ""));
					DefaultLogger.debug(lmtForm,"If Funded then [tempRequiredSecCov > funded ] ==18===> "+tempRequiredSecCov +" > " +funded);
					DefaultLogger.debug(lmtForm," Funded / NON_FUNDED / MEMO_EXPOSURE ==17===> "+funded +" / " +nonFunded +" / "+memoExposer );	
					DefaultLogger.debug(lmtForm, "(AbstractCommonMapper.isEmptyOrNull(lmtForm.getIsAdhoc()) || lmtForm.getIsAdhoc().equals(N))===="+(AbstractCommonMapper.isEmptyOrNull(lmtForm.getIsAdhoc()) || lmtForm.getIsAdhoc().equals("N")));

					DefaultLogger.debug(lmtForm, "lmtForm.getIsAdhoc() + / +lmtForm.getIsAdhoc()===="+lmtForm.getIsAdhoc() +" / "+lmtForm.getIsAdhoc());
					BigDecimal tempRequiredSecCovTemp =new BigDecimal("0");
					if (tempRequiredSecCov.compareTo(tempRequiredSecCovTemp) > 0)
					{
						if( tempRequiredSecCov.compareTo(funded) > 0
								&& (lmtForm.getFacilityType().equalsIgnoreCase("Funded") || lmtForm.getFacilityType().equalsIgnoreCase("FUNDED")) 
								&& (AbstractCommonMapper.isEmptyOrNull(lmtForm.getIsAdhoc()) || lmtForm.getIsAdhoc().equals("N")) 
								&& (AbstractCommonMapper.isEmptyOrNull(lmtForm.getIsAdhocToSum()) || lmtForm.getIsAdhocToSum().equals("N"))
								){
							DefaultLogger.debug(lmtForm,"If Funded then [tempRequiredSecCov > funded ] ==18===> "+tempRequiredSecCov +" > " +funded);
							errors.add("requiredSecCov", new ActionMessage("error.amount.not.greaterthan", "Sanctioned Amount", "Total Funded Amount"));
						}

						if( tempRequiredSecCov.compareTo(funded) > 0
								&& (lmtForm.getFacilityType().equalsIgnoreCase("Funded") || lmtForm.getFacilityType().equalsIgnoreCase("FUNDED"))
								&& ( (lmtForm.getIsAdhoc()!= null) &&  (lmtForm.getIsAdhoc().equals("Y") ||  lmtForm.getIsAdhoc().equals("true") || lmtForm.getIsAdhoc().equals("on")) )
								&& ((lmtForm.getIsAdhocToSum()!= null) && (lmtForm.getIsAdhocToSum().equals("Y") || lmtForm.getIsAdhocToSum().equals("on")) )
								){
							DefaultLogger.debug(lmtForm,"If Funded then [tempRequiredSecCov > funded ] ==18===> "+tempRequiredSecCov +" > " +funded);
							errors.add("requiredSecCov", new ActionMessage("error.amount.not.greaterthan", "Sanctioned Amount", "Total Funded Amount"));
						}

						if( tempRequiredSecCov.compareTo(nonFunded) > 0 
								&& (lmtForm.getFacilityType().equalsIgnoreCase("Non Funded") || lmtForm.getFacilityType().equalsIgnoreCase("NON_FUNDED") )
								&& (AbstractCommonMapper.isEmptyOrNull(lmtForm.getIsAdhoc()) || lmtForm.getIsAdhoc().equals("N")) 
								&& (AbstractCommonMapper.isEmptyOrNull(lmtForm.getIsAdhocToSum()) || lmtForm.getIsAdhocToSum().equals("N"))
								) {
							DefaultLogger.debug(lmtForm,"If NON_FUNDED then [tempRequiredSecCov > nonFunded ] ==19===> "+tempRequiredSecCov +" > " +nonFunded);
							errors.add("requiredSecCov", new ActionMessage("error.amount.not.greaterthan", "Sanctioned Amount", "Total Non Funded Amount"));
						}		

						if( tempRequiredSecCov.compareTo(nonFunded) > 0
								&& (lmtForm.getFacilityType().equalsIgnoreCase("Non Funded") || lmtForm.getFacilityType().equalsIgnoreCase("NON_FUNDED") )
								&& ((lmtForm.getIsAdhoc()!= null) &&  (lmtForm.getIsAdhoc().equals("Y") ||  lmtForm.getIsAdhoc().equals("true")  || lmtForm.getIsAdhoc().equals("on")))
								&& ((lmtForm.getIsAdhocToSum()!= null) &&   (lmtForm.getIsAdhocToSum().equals("Y")  || lmtForm.getIsAdhocToSum().equals("on")))
								) {
							DefaultLogger.debug(lmtForm,"If NON_FUNDED then [tempRequiredSecCov > nonFunded ] ==19===> "+tempRequiredSecCov +" > " +nonFunded);
							errors.add("requiredSecCov", new ActionMessage("error.amount.not.greaterthan", "Sanctioned Amount", "Total Non Funded Amount"));
						}	

						if( tempRequiredSecCov.compareTo(memoExposer) > 0
								&& (lmtForm.getFacilityType().equalsIgnoreCase("Memo Exposure") || lmtForm.getFacilityType().equalsIgnoreCase("MEMO_EXPOSURE"))
								&& (AbstractCommonMapper.isEmptyOrNull(lmtForm.getIsAdhoc()) || lmtForm.getIsAdhoc().equals("N"))
								&& (AbstractCommonMapper.isEmptyOrNull(lmtForm.getIsAdhocToSum()) || lmtForm.getIsAdhocToSum().equals("N"))
								){
							DefaultLogger.debug(lmtForm,"If MEMO_EXPOSURE then [tempRequiredSecCov > memoExposer ] ==20===> "+tempRequiredSecCov +" > " +memoExposer);
							errors.add("requiredSecCov", new ActionMessage("error.amount.not.greaterthan", "Sanctioned Amount", "Total Memo Exposer Amount"));
						}

						if( tempRequiredSecCov.compareTo(memoExposer) > 0 
								&& (lmtForm.getFacilityType().equalsIgnoreCase("Memo Exposure") || lmtForm.getFacilityType().equalsIgnoreCase("MEMO_EXPOSURE"))
								&& ( (lmtForm.getIsAdhoc()!= null) && (lmtForm.getIsAdhoc().equals("Y") ||  lmtForm.getIsAdhoc().equals("true")  || lmtForm.getIsAdhoc().equals("on")))
								&& ((lmtForm.getIsAdhocToSum()!= null) && (lmtForm.getIsAdhocToSum().equals("Y") || lmtForm.getIsAdhocToSum().equals("on")) )
								){
							DefaultLogger.debug(lmtForm,"If MEMO_EXPOSURE then [tempRequiredSecCov > memoExposer ] ==20===> "+tempRequiredSecCov +" > " +memoExposer);
							errors.add("requiredSecCov", new ActionMessage("error.amount.not.greaterthan", "Sanctioned Amount", "Total Memo Exposer Amount"));
						}
					}
				}
				//start:added else loop for below validation(Individually sanction amount of each sub limit should not exceed sanction amount of main limit.)
				else if(flag && lmtForm.getLimitType().equals("Yes") && "No".equals(lmtForm.getGuarantee()) && !lmtForm.getMainFacilityId().isEmpty()){

					DefaultLogger.debug(lmtForm,"Old Flag is true(Flag checked again) ==16===> Flag = true");
					//  BigDecimal funded = new BigDecimal(lmtForm.getFundedAmount().replaceAll(",", ""));
					// BigDecimal nonFunded = new BigDecimal(lmtForm.getNonFundedAmount().replaceAll(",", ""));
					//BigDecimal memoExposer = new BigDecimal(lmtForm.getMemoExposer().replaceAll(",", ""));
					//DefaultLogger.debug(lmtForm,"If Funded then [tempRequiredSecCov > funded ] ==18===> "+tempRequiredSecCov +" > " +funded);
					//DefaultLogger.debug(lmtForm," Funded / NON_FUNDED / MEMO_EXPOSURE ==17===> "+funded +" / " +nonFunded +" / "+memoExposer );	
					//DefaultLogger.debug(lmtForm, "(AbstractCommonMapper.isEmptyOrNull(lmtForm.getIsAdhoc()) || lmtForm.getIsAdhoc().equals(N))===="+(AbstractCommonMapper.isEmptyOrNull(lmtForm.getIsAdhoc()) || lmtForm.getIsAdhoc().equals("N")));

					DefaultLogger.debug(lmtForm, "lmtForm.getIsAdhoc() + / +lmtForm.getIsAdhoc()===="+lmtForm.getIsAdhoc() +" / "+lmtForm.getIsAdhoc());
					BigDecimal tempRequiredSecCovTemp =new BigDecimal("0");

					LimitDAO limitDao=new LimitDAO();
					BigDecimal mainLimitSanctionAmount=limitDao.getSanctionedAmount(lmtForm.getMainFacilityId());
					if (tempRequiredSecCov.compareTo(tempRequiredSecCovTemp) > 0)
					{
						BigDecimal mainLimitSanctionAmountInInr= mainLimitSanctionAmount.multiply(exchangeRate);
						if( tempRequiredSecCov.compareTo(mainLimitSanctionAmountInInr) > 0){
							DefaultLogger.debug(lmtForm,"If SubLimit then [tempRequiredSecCov > mainLimitSanctionAmountInInr ] ==18===> "+tempRequiredSecCov +" > " +mainLimitSanctionAmountInInr);
							errors.add("requiredSecCov", new ActionMessage("error.amount.not.greaterthan", "Sub Limit Sanctioned Amount", "Main Limit Sanctioned Amount"));
						}


					}
				}  // end

				float fltSecCov = 0.0f;
				String secId = "";
				//	DefaultLogger.debug(lmtForm,"[lmtForm.getSecurityCoverage().length ] > 0 ==21===> "+lmtForm.getSecurityCoverage().length );
				if(lmtForm.getSecurityCoverage()!= null && lmtForm.getSecurityCoverage().length > 0 ){
					boolean err = false;
					boolean err1 = false;
					//DefaultLogger.debug(lmtForm,"Loop for [lmtForm.getSecurityCoverage().length ] ==22===> "+lmtForm.getSecurityCoverage().length );
					for(int i1=0; i1 < lmtForm.getSecurityCoverage().length; i1++){
						fltSecCov = 0.0f;					
						if (!(errorCode = Validator.checkNumber(lmtForm.getSecurityCoverage()[i1], true,0.01,IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE ,3 ,locale))
								.equals(Validator.ERROR_NONE)) {
							err = true;
							DefaultLogger.debug(lmtForm,"i1 ==23===> "+lmtForm.getSecurityCoverage()[i1] );
						}
						//--------------------------------commented by bharat------start----------------------------------------------------------
						/* security coverage % for security except property should not exceed 100%
						 * this portion is commented to disable above functionality.
						 * validation is added so that security coverage % range for all security is 0.01 to 999
						 * */
						/*	else{
						MILimitUIHelper helper = new MILimitUIHelper();

						SBMILmtProxy proxy = helper.getSBMILmtProxy();
						List lmtList = proxy.getLimitSummaryListByAA(lmtForm.getLimitProfileID());
						List lmtListFormated = helper.formatLimitListView(lmtList, locale);

						for(int i = 0; i < lmtListFormated.size(); i++){
							LimitListSummaryItem lstSummaryItem = (LimitListSummaryItem) lmtListFormated.get(i);
							List secList = lstSummaryItem.getSecItemList();
							if ((secList != null)) {
								for(int j = 0; j < secList.size(); j++ ){
									LimitListSecItemBase secItem = (LimitListSecItemBase) (secList.get(j));
									if(secItem.getSecTypeName().equals(lmtForm.getSecurityTypeSubType()[i1].split(",")[0]) &&
											secItem.getSecSubtypeName().equals(lmtForm.getSecurityTypeSubType()[i1].split(",")[1])&&
											secItem.getSecurityId().equals(lmtForm.getSecurityTypeSubType()[i1].split(",")[2]) &&
											!lstSummaryItem.getLimitId().trim().substring(0, 17).equals(lmtForm.getLimitRef())){
										if(secItem.getLmtSecurityCoverage() == null || secItem.getLmtSecurityCoverage().equals("")){
											secItem.setLmtSecurityCoverage("0");
										}
										fltSecCov = fltSecCov + Float.parseFloat(secItem.getLmtSecurityCoverage());
										float tot1 = fltSecCov+ Float.parseFloat(lmtForm.getSecurityCoverage()[i1]);
										if(tot1 > 100.0f){
											secId = secItem.getSecurityId();
										}
									}
								}
							}
						}
					}*/
						/*	float tot = fltSecCov+ Float.parseFloat(lmtForm.getSecurityCoverage()[i1]);
					if(tot > 100.0f){
						err1 = true;
					}*/
					}
					/*if(err1) {
					errors.add("securityCoverage", new ActionMessage("error.number.securityCoverage", secId));
				}*/
					//----------------------------commented by bharat-----end-----------------------------			
					if(err) {
						errors.add("securityCoverage", new ActionMessage("error.number.lessthan", "0.01", "999"));
					}
				}


				LimitDAO limitDAO=new LimitDAO();

				ResourceBundle bundle = ResourceBundle.getBundle("ofa");
				String fcubsSystem = bundle.getString("fcubs.systemName");
				String ubsSystem = bundle.getString("ubs.systemName");

				if(!"WS_FAC_EDIT".equalsIgnoreCase(lmtForm.getEvent()) && !"WS_FAC_CREATE".equalsIgnoreCase(lmtForm.getEvent()) && !"create".equals(lmtForm.getEvent())){

					if(lmtForm.getLimitType().equals("Yes") && (fcubsSystem.equals(lmtForm.getFacilitySystem()) || ubsSystem.equals(lmtForm.getFacilitySystem()))){

						String system = "'"+fcubsSystem+"'"+","+"'"+ubsSystem+"'";

						BigDecimal subLimitReleasedAmt=new BigDecimal("0");
						if(null!=lmtForm.getLimitRef()){
							subLimitReleasedAmt=limitDAO.getSubLimitReleasedAmt(lmtForm.getLimitRef(),system);
						}

						BigDecimal mainLimitReleasedAmt = new BigDecimal("0");

						String releasedAmountMainLimit = lmtForm.getTotalReleasedAmount().replace(",","");
						mainLimitReleasedAmt=new BigDecimal((releasedAmountMainLimit==null || releasedAmountMainLimit.isEmpty())?"0":releasedAmountMainLimit);

						if( mainLimitReleasedAmt.compareTo(subLimitReleasedAmt) < 0){
							errors.add("totalReleasedAmount", new ActionMessage("error.date.compareDate.cannotBeless", "Released Amount", "it's sub line(s) Released Amount"));
						}
					}
				}

			

			//SCCOD validation//
			String scodLineNoList=PropertyManager.getValue("scod.linenocode.name");

			boolean scodB = false;
			if(scodLineNoList != null && !scodLineNoList.equals("")) {
				String[] scodlinelist = scodLineNoList.split(",");
				for(int i=0; i< scodlinelist.length; i++ ) {
					if(lmtForm.getLineNo()!=null && lmtForm.getLineNo().equals(scodlinelist[i])) {
						scodB = true;
					}
				}
			}

			if("ET".equals(lmtForm.getFacilitySystem())){
				scodB =false;
			}

			//			if(lmtForm.getLineNo()!=null && scodLineNoList.contains(lmtForm.getLineNo()) || "WS_SCOD_FAC_EDIT".equalsIgnoreCase(lmtForm.getEvent()) 
			//					|| "WS_SCOD_FAC_EDIT_INITIAL".equalsIgnoreCase(lmtForm.getEvent())){

			/*if(scodB == true || "WS_SCOD_FAC_EDIT".equalsIgnoreCase(lmtForm.getEvent()) 
					|| "WS_SCOD_FAC_EDIT_INITIAL".equalsIgnoreCase(lmtForm.getEvent())){

				if(!"".equals(lmtForm.getProjectFinance()) && !"".equals(lmtForm.getProjectLoan()) && lmtForm.getProjectFinance() != null && lmtForm.getProjectLoan() != null){
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DATE, -1);

					if("Y".equals(lmtForm.getProjectFinance()) || "Y".equals(lmtForm.getProjectLoan())){
						if("".equals(lmtForm.getScodDate()) || lmtForm.getScodDate() == null) {
							errors.add("scodDate", new ActionMessage("error.string.mandatory")); 
						}else if ((lmtForm.getScodDate().length() > 0)
								&& DateUtil.convertDate(locale, lmtForm.getScodDate()).before(calendar.getTime()))
						{				
							if("WS_SCOD_FAC_EDIT_INITIAL".equalsIgnoreCase(lmtForm.getEvent()) || "create".equals(lmtForm.getEvent())){ 
								errors.add("scodDate", new ActionMessage("error.date.before.notallowed"));

							}
						}	else if ((lmtForm.getScodDate().length() > 0)
							&& DateUtil.convertDate(locale, lmtForm.getScodDate()).before(DateUtil.getDate()))
				 {
					 errors.add("scodDate", new ActionMessage("error.date.future.notallowed"));
				 }	

					}



					if(!"".equals(lmtForm.getScodDate()) && lmtForm.getScodDate() != null) {

						if("Y".equals(lmtForm.getProjectFinance()) || "Y".equals(lmtForm.getProjectLoan())){
							if("".equals(lmtForm.getRemarksSCOD()) || lmtForm.getRemarksSCOD() == null) {
								errors.add("remarksSCOD", new ActionMessage("error.string.mandatory")); 
							}
						}
					}

					if(!"1".equals(lmtForm.getExstAssetClass()) 
							&& ("".equals(lmtForm.getExstAssClassDate())||lmtForm.getExstAssClassDate() == null) 
							&& ("".equals(lmtForm.getLelvelDelaySCOD()) || lmtForm.getLelvelDelaySCOD() ==null)
							&& !"WS_SCOD_FAC_EDIT_INITIAL".equals(lmtForm.getEvent())){
						errors.add("exstAssClassDate", new ActionMessage("error.string.mandatory")); 
					}

					if(!"1".equals(lmtForm.getExstAssetClass()) && !"create".equals(lmtForm.getEvent()) 
							&& ("".equals(lmtForm.getExstAssClassDate())||lmtForm.getExstAssClassDate() == null) 
							&& ("".equals(lmtForm.getLelvelDelaySCOD()) || lmtForm.getLelvelDelaySCOD() ==null)
							&& "WS_SCOD_FAC_EDIT_INITIAL".equals(lmtForm.getEvent())){
						errors.add("exstAssClassDate", new ActionMessage("error.string.mandatory")); 
					}

					if("Y".equals(lmtForm.getProjectFinance()) || "Y".equals(lmtForm.getProjectLoan())){
						if("".equals(lmtForm.getInfaProjectFlag()) || lmtForm.getInfaProjectFlag() == null) {
							errors.add("infaProjectFlag", new ActionMessage("error.string.mandatory")); 
						}
					}

					if("".equals(lmtForm.getScodDate()) || lmtForm.getScodDate() == null) {

						if("Y".equals(lmtForm.getProjectFinance()) || "Y".equals(lmtForm.getProjectLoan())){
							if("".equals(lmtForm.getRemarksSCOD()) || lmtForm.getRemarksSCOD() == null) {
								errors.add("remarksSCOD", new ActionMessage("error.string.mandatory")); 
							}
						}

						if("Y".equals(lmtForm.getProjectFinance()) || "Y".equals(lmtForm.getProjectLoan())){
				if("".equals(lmtForm.getInfaProjectFlag()) || lmtForm.getInfaProjectFlag() == null) {
				errors.add("infaProjectFlag", new ActionMessage("error.string.mandatory")); 
				}
			}

						if(!"1".equals(lmtForm.getRevisedAssetClass()) && lmtForm.getRevisedAssetClass() != null 
					&& ("".equals(lmtForm.getLelvelDelaySCOD()) || lmtForm.getLelvelDelaySCOD() == null)
					&& (!"1".equals(lmtForm.getLelvelDelaySCOD()) && !"2".equals(lmtForm.getLelvelDelaySCOD()) 
							&& !"3".equals(lmtForm.getLelvelDelaySCOD())) && !"create".equals(lmtForm.getEvent()) 
					&& !"WS_SCOD_FAC_EDIT_INITIAL".equals(lmtForm.getEvent())){
				errors.add("revsdAssClassDate", new ActionMessage("error.string.mandatory")); 
			}
						 
						if(!"1".equals(lmtForm.getExstAssetClass()) && lmtForm.getExstAssetClass() != null 
					&& ("".equals(lmtForm.getLelvelDelaySCOD()) || lmtForm.getLelvelDelaySCOD() == null) 
				    && "create".equals(lmtForm.getEvent()) 
					&& !"WS_SCOD_FAC_EDIT_INITIAL".equals(lmtForm.getEvent())){
				errors.add("exstAssClassDate", new ActionMessage("error.string.mandatory")); 
			}

						if(("".equals(lmtForm.getWhlmreupSCOD()) || lmtForm.getWhlmreupSCOD() == null) 
					&& !"create".equals(lmtForm.getEvent()) 
					&& !"WS_SCOD_FAC_EDIT_INITIAL".equals(lmtForm.getEvent())){
				errors.add("whlmreupSCOD", new ActionMessage("error.string.mandatory"));
			}



					}else {

						if(!"create".equals(lmtForm.getEvent()) &&  !"WS_SCOD_FAC_EDIT_INITIAL".equals(lmtForm.getEvent())){
							if(lmtForm.getWhlmreupSCOD() != null && !lmtForm.getWhlmreupSCOD().equals("")) {
								if(lmtForm.getWhlmreupSCOD().equals("Y") && lmtForm.getProjectDelayedFlag().equals("N")) {
									lmtForm.setLelvelDelaySCOD("");
								}else if(lmtForm.getWhlmreupSCOD().equals("Y") && lmtForm.getProjectDelayedFlag().equals("Y")) {
						lmtForm.setActualCommOpsDate("");
					}
							}
						}
						if("".equals(lmtForm.getProjectFinance()) || lmtForm.getProjectFinance() == null) {
							errors.add("projectFinance", new ActionMessage("error.string.mandatory")); 
						}
						if("".equals(lmtForm.getProjectLoan()) || lmtForm.getProjectLoan() == null) {
							errors.add("projectLoan", new ActionMessage("error.string.mandatory"));		
						}
						if(!"1".equals(lmtForm.getExstAssetClass()) && lmtForm.getExstAssetClass() != null 
						&& ("".equals(lmtForm.getLelvelDelaySCOD()) || lmtForm.getLelvelDelaySCOD() == null) 
						&& (!"1".equals(lmtForm.getLelvelDelaySCOD()) && !"2".equals(lmtForm.getLelvelDelaySCOD()) 
								&& !"3".equals(lmtForm.getLelvelDelaySCOD())) && "create".equals(lmtForm.getEvent()) 
						&& !"WS_SCOD_FAC_EDIT_INITIAL".equals(lmtForm.getEvent())){
					errors.add("exstAssClassDate", new ActionMessage("error.string.mandatory")); 
				}

						if(!"1".equals(lmtForm.getExstAssetClass()) && "create".equals(lmtForm.getEvent()) 
						&& ("".equals(lmtForm.getExstAssClassDate())||lmtForm.getExstAssClassDate() == null) 
						&& ("".equals(lmtForm.getLelvelDelaySCOD()) || lmtForm.getLelvelDelaySCOD() ==null)
						&& !"WS_SCOD_FAC_EDIT_INITIAL".equals(lmtForm.getEvent())){
					errors.add("exstAssClassDate", new ActionMessage("error.string.mandatory")); 
				}

				if(!"1".equals(lmtForm.getExstAssetClass()) && !"create".equals(lmtForm.getEvent()) 
						&& ("".equals(lmtForm.getExstAssClassDate())||lmtForm.getExstAssClassDate() == null) 
						&& ("".equals(lmtForm.getLelvelDelaySCOD()) || lmtForm.getLelvelDelaySCOD() ==null)
						&& "WS_SCOD_FAC_EDIT_INITIAL".equals(lmtForm.getEvent())){
					errors.add("exstAssClassDate", new ActionMessage("error.string.mandatory")); 
				}

						if(!"1".equals(lmtForm.getRevisedAssetClass()) && lmtForm.getRevisedAssetClass() != null 
								&& ("".equals(lmtForm.getRevsdAssClassDate())||lmtForm.getRevsdAssClassDate() == null)
								&& ("".equals(lmtForm.getLelvelDelaySCOD()) || lmtForm.getLelvelDelaySCOD() ==null) && !"create".equals(lmtForm.getEvent()) 
								&& !"WS_SCOD_FAC_EDIT_INITIAL".equals(lmtForm.getEvent()) && (lmtForm.getProjectDelayedFlag() != null && !"".equals(lmtForm.getProjectDelayedFlag()))){	
							errors.add("revsdAssClassDate", new ActionMessage("error.string.mandatory")); 
						}

						if(("".equals(lmtForm.getRevisedAssetClass()) || lmtForm.getRevisedAssetClass() == null)
								&& ("".equals(lmtForm.getLelvelDelaySCOD()) || lmtForm.getLelvelDelaySCOD() ==null) && !"create".equals(lmtForm.getEvent()) 
								&& !"WS_SCOD_FAC_EDIT_INITIAL".equals(lmtForm.getEvent()) && (lmtForm.getProjectDelayedFlag() != null && !"".equals(lmtForm.getProjectDelayedFlag()))){
							errors.add("revisedAssetClass", new ActionMessage("error.string.mandatory"));
						}
					}

				}else if("".equals(lmtForm.getProjectFinance()) || lmtForm.getProjectFinance() == null) {
					if(!"WS_SCOD_FAC_EDIT".equalsIgnoreCase(lmtForm.getEvent())){
						errors.add("projectFinance", new ActionMessage("error.string.mandatory")); 
					}
				}
				if("".equals(lmtForm.getProjectLoan()) || lmtForm.getProjectLoan() == null) {
					if(!"WS_SCOD_FAC_EDIT".equalsIgnoreCase(lmtForm.getEvent())){
						errors.add("projectLoan", new ActionMessage("error.string.mandatory"));	
					}
				}

				if("N".equals(lmtForm.getProjectLoan()) && "N".equals(lmtForm.getProjectFinance())){
					if(!"WS_SCOD_FAC_EDIT".equalsIgnoreCase(lmtForm.getEvent())){
						errors.add("projectLoan", new ActionMessage("error.projectLoanFinance.mandatory"));
						errors.add("projectFinance", new ActionMessage("error.projectLoanFinance.mandatory"));
					}
				}


				if(("Y".equals(lmtForm.getWhlmreupSCOD()) && lmtForm.getWhlmreupSCOD() != null 
					&& (!"create".equals(lmtForm.getEvent()) && !"WS_SCOD_FAC_EDIT_INITIAL".equals(lmtForm.getEvent())))){

				if((("".equals(lmtForm.getActualCommOpsDate()) || lmtForm.getActualCommOpsDate()== null) 
						&& ("".equals(lmtForm.getLelvelDelaySCOD()) || lmtForm.getLelvelDelaySCOD() == null))) {
					if(lmtForm.getWhlmreupSCOD().equals("Y") && lmtForm.getProjectDelayedFlag().equals("N")) {
						errors.add("actualCommOpsDate", new ActionMessage("error.actualDateOrDelay.mandatory"));
					}else if(lmtForm.getWhlmreupSCOD().equals("Y") && lmtForm.getProjectDelayedFlag().equals("Y")){
						errors.add("lelvelDelaySCOD", new ActionMessage("error.actualDateOrDelay.mandatory"));
					}
				}else if (lmtForm.getActualCommOpsDate()!=null)
			 {
					if((lmtForm.getActualCommOpsDate().length() > 0)
							&& DateUtil.convertDate(locale, lmtForm.getActualCommOpsDate()).before(DateUtil.getDate())) {
				 errors.add("actualCommOpsDate", new ActionMessage("error.date.before.notallowed"));
					}
			 }			
			}

				if(("Y".equals(lmtForm.getWhlmreupSCOD()) && lmtForm.getWhlmreupSCOD() != null 
						&& (!"create".equals(lmtForm.getEvent()) && !"WS_SCOD_FAC_EDIT_INITIAL".equals(lmtForm.getEvent())))){
					if("".equals(lmtForm.getLelvelDelaySCOD()) || lmtForm.getLelvelDelaySCOD() == null) {
						if(lmtForm.getWhlmreupSCOD().equals("Y") && lmtForm.getProjectDelayedFlag().equals("Y")){
							errors.add("lelvelDelaySCOD", new ActionMessage("error.string.mandatory"));
						}
					}
				}

				if(!"WS_SCOD_FAC_EDIT_INITIAL".equals(lmtForm.getEvent()) && 
						("".equals(lmtForm.getWhlmreupSCOD()) || lmtForm.getWhlmreupSCOD() == null) 
						&& (!"create".equals(lmtForm.getEvent())) && (lmtForm.getProjectDelayedFlag() != null && !"".equals(lmtForm.getProjectDelayedFlag()))){
					errors.add("whlmreupSCOD", new ActionMessage("error.string.mandatory"));	
				}

				//SCOD LEVEL 1
				if(!"".equals(lmtForm.getLelvelDelaySCOD()) && lmtForm.getLelvelDelaySCOD() != null 
						&& "1".equals(lmtForm.getLelvelDelaySCOD()) && !"create".equals(lmtForm.getEvent())) {
					if((!"".equals(lmtForm.getScodDate()) && lmtForm.getScodDate() != null) || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {
						if((!"".equals(lmtForm.getEscodLevelOneDelayDate()) && lmtForm.getEscodLevelOneDelayDate() != null ) || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {		
							if("Y".equals(lmtForm.getInfaProjectFlag())){
								Calendar date1 = Calendar.getInstance();
								date1.setTime(DateUtil.convertDate(locale, lmtForm.getEscodLevelOneDelayDate()));
								Calendar date2 = Calendar.getInstance();
								Calendar date3 = Calendar.getInstance();
								date2.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
								date3.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
								date3.add(Calendar.DATE,1);
								date2.add(Calendar.YEAR,2);
								date2.add(Calendar.DATE,-1);
								if(date1.after(date2)) {
									errors.add("escodLevelOneDelayDate", new ActionMessage("error.escodLevelOneDelayDate.dateValidationInfra"));
								}else if ((lmtForm.getEscodLevelOneDelayDate().length() > 0)
										&& DateUtil.convertDate(locale, lmtForm.getEscodLevelOneDelayDate()).before(date3.getTime()))
								{
									errors.add("escodLevelOneDelayDate", new ActionMessage("error.date.before.notallowed.scodDate"));
								}
							}
						}
						if((!"".equals(lmtForm.getEscodLevelOneDelayDate()) && lmtForm.getEscodLevelOneDelayDate() != null )  || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {
							if("N".equals(lmtForm.getInfaProjectFlag())){
								Calendar date1 = Calendar.getInstance();
								date1.setTime(DateUtil.convertDate(locale, lmtForm.getEscodLevelOneDelayDate()));
								Calendar date2 = Calendar.getInstance();
								date2.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
								date2.add(Calendar.YEAR,1);
								date2.add(Calendar.DATE,-1);
								Calendar date3 = Calendar.getInstance();
								date3.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
								date3.add(Calendar.DATE,1);
								if(date1.after(date2)) {
									errors.add("escodLevelOneDelayDate", new ActionMessage("error.escodLevelOneDelayDate.dateValidationNonInfra"));
								}else if ((lmtForm.getEscodLevelOneDelayDate().length() > 0)
										&& DateUtil.convertDate(locale, lmtForm.getEscodLevelOneDelayDate()).before(date3.getTime()))
								{
									errors.add("escodLevelOneDelayDate", new ActionMessage("error.date.before.notallowed.scodDate"));
								}
							}
						}
						if("".equals(lmtForm.getEscodLevelOneDelayDate()) || lmtForm.getEscodLevelOneDelayDate() == null) {
							errors.add("escodLevelOneDelayDate", new ActionMessage("error.string.mandatory")); 	
						}
					}
				}

				if(("".equals(lmtForm.getRevisedAssetClassL1()) || lmtForm.getRevisedAssetClassL1() == null)
						&& ("1".equals(lmtForm.getLelvelDelaySCOD()))){
					errors.add("revisedAssetClassL1", new ActionMessage("error.string.mandatory")); 
				}
				if(!"1".equals(lmtForm.getRevisedAssetClassL1()) && !"create".equals(lmtForm.getEvent()) 
						&& ("".equals(lmtForm.getRevsdAssClassDateL1())||lmtForm.getRevsdAssClassDateL1() == null) 
						&& "1".equals(lmtForm.getLelvelDelaySCOD())){
					errors.add("revsdAssClassDateL1", new ActionMessage("error.string.mandatory")); 
				}
				if(("".equals(lmtForm.getReasonLevelOneDelay()) || lmtForm.getReasonLevelOneDelay() == null)
						&& ("1".equals(lmtForm.getLelvelDelaySCOD()))){
					errors.add("reasonLevelOneDelay", new ActionMessage("error.string.mandatory")); 
				}
				//SCOD LEVEL 2
				if(!"".equals(lmtForm.getLelvelDelaySCOD()) && lmtForm.getLelvelDelaySCOD() != null 
						&& "2".equals(lmtForm.getLelvelDelaySCOD()) && !"create".equals(lmtForm.getEvent())) {
					if((!"".equals(lmtForm.getScodDate()) && lmtForm.getScodDate() != null) || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {
						if((!"".equals(lmtForm.getEscodLevelSecondDelayDate()) && lmtForm.getEscodLevelSecondDelayDate() != null ) || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {
							if("Y".equals(lmtForm.getInfaProjectFlag())) {
								Calendar date1 = Calendar.getInstance();
								date1.setTime(DateUtil.convertDate(locale, lmtForm.getEscodLevelSecondDelayDate()));
								Calendar date2 = Calendar.getInstance();
								date2.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
								date2.add(Calendar.YEAR,4);
								Calendar date3 = Calendar.getInstance();
								date3.add(Calendar.DATE,1);
								date3.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
								date2.add(Calendar.DATE,-1);
								if(date1.after(date2)) {
									errors.add("escodLevelSecondDelayDate", new ActionMessage("error.escodLevelSecondDelayDate.dateValidationInfra"));
								}else if ((lmtForm.getEscodLevelSecondDelayDate().length() > 0)
										&& DateUtil.convertDate(locale, lmtForm.getEscodLevelSecondDelayDate()).before(date3.getTime()))
								{
									errors.add("escodLevelSecondDelayDate", new ActionMessage("error.date.before.notallowed.scodDate"));
								}else if ((lmtForm.getEscodLevelSecondDelayDate().length() > 0)
										&& DateUtil.convertDate(locale, lmtForm.getEscodLevelSecondDelayDate()).equals(date3.getTime()))
								{
									errors.add("escodLevelSecondDelayDate", new ActionMessage("error.date.before.notallowed.scodDate"));
								}
							}
						}

						if((!"".equals(lmtForm.getEscodLevelSecondDelayDate()) && lmtForm.getEscodLevelSecondDelayDate() != null )  || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {
							if("N".equals(lmtForm.getInfaProjectFlag())){
								Calendar date1 = Calendar.getInstance();
								date1.setTime(DateUtil.convertDate(locale, lmtForm.getEscodLevelSecondDelayDate()));
								Calendar date2 = Calendar.getInstance();
								date2.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
								date2.add(Calendar.YEAR,2);
								date2.add(Calendar.DATE,-1);
								Calendar date3 = Calendar.getInstance();
								date3.add(Calendar.DATE,1);
								date3.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
								if(date1.after(date2)) {
									errors.add("escodLevelSecondDelayDate", new ActionMessage("error.escodLevelSecondDelayDate.dateValidationNonInfra"));
								}else if ((lmtForm.getEscodLevelSecondDelayDate().length() > 0)
										&& DateUtil.convertDate(locale, lmtForm.getEscodLevelSecondDelayDate()).before(date3.getTime()))
								{
									errors.add("escodLevelSecondDelayDate", new ActionMessage("error.date.before.notallowed.scodDate"));
								}else if ((lmtForm.getEscodLevelSecondDelayDate().length() > 0)
										&& DateUtil.convertDate(locale, lmtForm.getEscodLevelSecondDelayDate()).equals(date3.getTime()))
								{
									errors.add("escodLevelSecondDelayDate", new ActionMessage("error.date.before.notallowed.scodDate"));
								}
							}
						}

						if("".equals(lmtForm.getEscodLevelSecondDelayDate()) || lmtForm.getEscodLevelSecondDelayDate() == null) {
							errors.add("escodLevelSecondDelayDate", new ActionMessage("error.string.mandatory")); 	
						}
					}
				}

				if(("".equals(lmtForm.getRevisedAssetClass_L2()) || lmtForm.getRevisedAssetClass_L2() == null) 
						&& ("2".equals(lmtForm.getLelvelDelaySCOD()))){
					errors.add("revisedAssetClass_L2", new ActionMessage("error.string.mandatory")); 
				}
				if(!"1".equals(lmtForm.getRevisedAssetClass_L2()) && !"create".equals(lmtForm.getEvent()) 
						&& ("".equals(lmtForm.getRevsdAssClassDate_L2())||lmtForm.getRevsdAssClassDate_L2() == null) 
						&& "2".equals(lmtForm.getLelvelDelaySCOD())){
					errors.add("revsdAssClassDate_L2", new ActionMessage("error.string.mandatory")); 
				}
				if(("".equals(lmtForm.getReasonLevelTwoDelay()) || lmtForm.getReasonLevelTwoDelay() == null)
						&& ("2".equals(lmtForm.getLelvelDelaySCOD()))){
					errors.add("reasonLevelTwoDelay", new ActionMessage("error.string.mandatory")); 
				}
				//SCOD DELAY LEVEL 3
				if(!"".equals(lmtForm.getLelvelDelaySCOD()) && lmtForm.getLelvelDelaySCOD() != null 
						&& "3".equals(lmtForm.getLelvelDelaySCOD()) && !"create".equals(lmtForm.getEvent())) {
					if((!"".equals(lmtForm.getScodDate()) && lmtForm.getScodDate() != null) || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {
						if((!"".equals(lmtForm.getEscodLevelThreeDelayDate()) && lmtForm.getEscodLevelThreeDelayDate() != null) || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {
							if("Y".equals(lmtForm.getInfaProjectFlag())) {
								Calendar date1 = Calendar.getInstance();
								date1.setTime(DateUtil.convertDate(locale, lmtForm.getEscodLevelThreeDelayDate()));
								Calendar date2 = Calendar.getInstance();
								date2.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
								date2.add(Calendar.YEAR,4);
								Calendar date3 = Calendar.getInstance();
								date3.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
								date3.add(Calendar.DATE,1);
								//date2.add(Calendar.DATE,-1);
								if(date1.before(date2)) {
									errors.add("escodLevelThreeDelayDate", new ActionMessage("error.escodLevelThreeDelayDate.dateValidationInfra"));
								}else if ((lmtForm.getEscodLevelThreeDelayDate().length() > 0)
										&& DateUtil.convertDate(locale, lmtForm.getEscodLevelThreeDelayDate()).before(date3.getTime()))
								{
									errors.add("escodLevelThreeDelayDate", new ActionMessage("error.date.before.notallowed.scodDate"));
								}
							}
						}

						if((!"".equals(lmtForm.getEscodLevelThreeDelayDate()) && lmtForm.getEscodLevelThreeDelayDate() != null ) || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {
							if("N".equals(lmtForm.getInfaProjectFlag())){
								Calendar date1 = Calendar.getInstance();
								date1.setTime(DateUtil.convertDate(locale, lmtForm.getEscodLevelThreeDelayDate()));
								Calendar date2 = Calendar.getInstance();
								date2.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
								date2.add(Calendar.YEAR,2);
								Calendar date3 = Calendar.getInstance();
								date3.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
								date3.add(Calendar.DATE,1);
								//date2.add(Calendar.DATE,1);
								if(date1.before(date2)) {
									errors.add("escodLevelThreeDelayDate", new ActionMessage("error.escodLevelThreeDelayDate.dateValidationNonInfra"));
								}else if ((lmtForm.getEscodLevelThreeDelayDate().length() > 0)
										&& DateUtil.convertDate(locale, lmtForm.getEscodLevelThreeDelayDate()).before(date3.getTime()))
								{
									errors.add("escodLevelThreeDelayDate", new ActionMessage("error.date.before.notallowed.scodDate"));
								}
							}
						}

						if("".equals(lmtForm.getEscodLevelThreeDelayDate()) || lmtForm.getEscodLevelThreeDelayDate() == null) {
							errors.add("escodLevelThreeDelayDate", new ActionMessage("error.string.mandatory")); 	
						}
					}
				}

				if(("".equals(lmtForm.getRevisedAssetClass_L3()) || lmtForm.getRevisedAssetClass_L3() == null) 
						&& ("3".equals(lmtForm.getLelvelDelaySCOD()))){
					errors.add("revisedAssetClass_L3", new ActionMessage("error.string.mandatory")); 
				}
				if(!"1".equals(lmtForm.getRevisedAssetClass_L3()) && !"create".equals(lmtForm.getEvent()) 
						&& ("".equals(lmtForm.getRevsdAssClassDate_L3())||lmtForm.getRevsdAssClassDate_L3() == null) 
						&& "3".equals(lmtForm.getLelvelDelaySCOD())){
					errors.add("revsdAssClassDate_L3", new ActionMessage("error.string.mandatory")); 
				}
				if(("".equals(lmtForm.getReasonLevelThreeDelay()) || lmtForm.getReasonLevelThreeDelay() == null)
						&& ("3".equals(lmtForm.getLelvelDelaySCOD()))){
					errors.add("reasonLevelThreeDelay", new ActionMessage("error.string.mandatory")); 
				}

				//SCOD LEVEL 1
				if(!"".equals(lmtForm.getLelvelDelaySCOD()) && lmtForm.getLelvelDelaySCOD() != null 
						&& "1".equals(lmtForm.getLelvelDelaySCOD()) && !"create".equals(lmtForm.getEvent())) {
					if((!"".equals(lmtForm.getScodDate()) && lmtForm.getScodDate() != null) || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {
						if((!"".equals(lmtForm.getRevsedESCODStpDate()) && lmtForm.getRevsedESCODStpDate() != null && "Y".equals(lmtForm.getInfaProjectFlag())) || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {
							Calendar date1 = Calendar.getInstance();
							date1.setTime(DateUtil.convertDate(locale, lmtForm.getRevsedESCODStpDate()));
							Calendar date2 = Calendar.getInstance();
							Calendar date3 = Calendar.getInstance();
							date2.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
							date3.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
							date3.add(Calendar.DATE,1);
							date2.add(Calendar.YEAR,2);
							date2.add(Calendar.DATE,-1);
							if(date1.after(date2)) {
								errors.add("revsedESCODStpDate", new ActionMessage("error.rescodLevelOneDelayDate.dateValidationInfra"));
							}else if ((lmtForm.getRevsedESCODStpDate().length() > 0)
									&& DateUtil.convertDate(locale, lmtForm.getRevsedESCODStpDate()).before(date3.getTime()))
							{
								errors.add("revsedESCODStpDate", new ActionMessage("error.redate.before.notallowed.escodDate"));
							}			
						}else if((!"".equals(lmtForm.getRevsedESCODStpDate()) && lmtForm.getRevsedESCODStpDate() != null && "N".equals(lmtForm.getInfaProjectFlag())) || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {
							Calendar date1 = Calendar.getInstance();
							date1.setTime(DateUtil.convertDate(locale, lmtForm.getRevsedESCODStpDate()));
							Calendar date2 = Calendar.getInstance();
							date2.setTime(DateUtil.convertDate(locale, lmtForm.getScodDate()));
							date2.add(Calendar.YEAR,1);
							date2.add(Calendar.DATE,-1);
							if(date1.after(date2)) {
								errors.add("revsedESCODStpDate", new ActionMessage("error.rescodLevelOneDelayDate.dateValidationNonInfra"));
							}else if ((lmtForm.getRevsedESCODStpDate().length() > 0)
									&& DateUtil.convertDate(locale, lmtForm.getRevsedESCODStpDate()).before(DateUtil.convertDate(locale, lmtForm.getScodDate())))
							{
								errors.add("revsedESCODStpDate", new ActionMessage("error.redate.before.notallowed.escodDate"));
							}
						}
					}
				}


				//SCOD LEVEL 2
				if(!"".equals(lmtForm.getLelvelDelaySCOD()) && lmtForm.getLelvelDelaySCOD() != null 
						&& "2".equals(lmtForm.getLelvelDelaySCOD()) && !"create".equals(lmtForm.getEvent()) && null != lmtForm.getRevsedESCODStpDateL2()) {
					if((!"".equals(lmtForm.getScodDate()) && lmtForm.getScodDate() != null) || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {
						if((!"".equals(lmtForm.getRevsedESCODStpDateL2()) && lmtForm.getRevsedESCODStpDateL2() != null ) || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {
							if("Y".equals(lmtForm.getInfaProjectFlag())){
								Calendar date1 = Calendar.getInstance();
								date1.setTime(DateUtil.convertDate(locale, lmtForm.getRevsedESCODStpDateL2()));
								Calendar date2 = Calendar.getInstance();
								date2.setTime(DateUtil.convertDate(locale, lmtForm.getEscodLevelSecondDelayDate()));
								date2.add(Calendar.YEAR,1);
								Calendar date3 = Calendar.getInstance();						
								date3.setTime(DateUtil.convertDate(locale, lmtForm.getEscodLevelSecondDelayDate()));
								date3.add(Calendar.DATE,1);
								Calendar date4 = Calendar.getInstance();
								date4.setTime(DateUtil.convertDate(locale, lmtForm.getEscodLevelSecondDelayDate()));
								date4.add(Calendar.YEAR,2);
								date2.add(Calendar.DATE,-1);
								date4.add(Calendar.DATE,-1);
								if(date1.after(date4) && "Y".equals(lmtForm.getReasonBeyondPromoterLevel2Flag()) && "Y".equals(lmtForm.getLegalOrArbitrationLevel2Flag())) {
									errors.add("revsedESCODStpDateL2", new ActionMessage("error.rescodLevelSecondDelayDate.dateValidationInfraRsnbynd"));
								}
								else if(date1.after(date4) && "Y".equals(lmtForm.getReasonBeyondPromoterLevel2Flag()) && "N".equals(lmtForm.getLegalOrArbitrationLevel2Flag())) {
									errors.add("revsedESCODStpDateL2", new ActionMessage("error.rescodLevelSecondDelayDate.dateValidationInfraRsnbynd"));
								}else if(date1.after(date2) && "Y".equals(lmtForm.getLegalOrArbitrationLevel2Flag()) && "N".equals(lmtForm.getReasonBeyondPromoterLevel2Flag())){
									errors.add("revsedESCODStpDateL2", new ActionMessage("error.rescodLevelSecondDelayDate.dateValidationInfraLglArb"));
								}else if ((lmtForm.getRevsedESCODStpDateL2().length() > 0)
										&& DateUtil.convertDate(locale, lmtForm.getRevsedESCODStpDateL2()).before(date3.getTime()))
								{
									errors.add("revsedESCODStpDateL2", new ActionMessage("error.redate.before.notallowed.escodDate"));
								}
							}
						}

						if((!"".equals(lmtForm.getRevsedESCODStpDateL2()) && lmtForm.getRevsedESCODStpDateL2() != null) || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {

							if( "N".equals(lmtForm.getInfaProjectFlag())) {
								Calendar date1 = Calendar.getInstance();
								date1.setTime(DateUtil.convertDate(locale, lmtForm.getRevsedESCODStpDateL2()));
								Calendar date2 = Calendar.getInstance();
								date2.setTime(DateUtil.convertDate(locale, lmtForm.getEscodLevelSecondDelayDate()));
								date2.add(Calendar.YEAR,1);
								date2.add(Calendar.DATE,-1);
								Calendar date3 = Calendar.getInstance();						
								date3.setTime(DateUtil.convertDate(locale, lmtForm.getEscodLevelSecondDelayDate()));
								date3.add(Calendar.DATE,1);
								if(date1.after(date2)) {
									errors.add("revsedESCODStpDateL2", new ActionMessage("error.rescodLevelSecondDelayDate.dateValidationNonInfra"));
								}else if ((lmtForm.getRevsedESCODStpDateL2().length() > 0)
										&& DateUtil.convertDate(locale, lmtForm.getRevsedESCODStpDateL2()).before(date3.getTime()))
								{
									errors.add("revsedESCODStpDateL2", new ActionMessage("error.redate.before.notallowed.escodDate"));
								}
							}
						}
					}

					if("".equals(lmtForm.getRevsedESCODStpDateL2()) || lmtForm.getRevsedESCODStpDateL2() == null) {
					errors.add("revsedESCODStpDateL2", new ActionMessage("error.string.mandatory")); 	
				}
				}

				//SCOD LEVEL 3
				if(!"".equals(lmtForm.getLelvelDelaySCOD()) && lmtForm.getLelvelDelaySCOD() != null 
						&& "3".equals(lmtForm.getLelvelDelaySCOD()) && !"create".equals(lmtForm.getEvent())  && null != lmtForm.getRevsedESCODStpDateL3()) {
					if((!"".equals(lmtForm.getScodDate()) && lmtForm.getScodDate() != null) || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {
						if((  null != lmtForm.getRevsedESCODStpDateL3()&& !"".equals(lmtForm.getRevsedESCODStpDateL3())) || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {
							if("Y".equals(lmtForm.getInfaProjectFlag())){
								Calendar date1 = Calendar.getInstance();
								date1.setTime(DateUtil.convertDate(locale, lmtForm.getRevsedESCODStpDateL3()));
								Calendar date2 = Calendar.getInstance();
								date2.setTime(DateUtil.convertDate(locale, lmtForm.getEscodLevelThreeDelayDate()));
								date2.add(Calendar.YEAR,1);
								Calendar date3 = Calendar.getInstance();
								date3.setTime(DateUtil.convertDate(locale, lmtForm.getEscodLevelThreeDelayDate()));
								date3.add(Calendar.DATE,1);
								Calendar date4 = Calendar.getInstance();
								date4.setTime(DateUtil.convertDate(locale, lmtForm.getEscodLevelThreeDelayDate()));
								date4.add(Calendar.YEAR,2);
								//date2.add(Calendar.DATE,1);
								//date4.add(Calendar.DATE,1);
								if(date1.before(date4) && "Y".equals(lmtForm.getReasonBeyondPromoterLevel3Flag())) {
									errors.add("revsedESCODStpDateL3", new ActionMessage("error.rescodLevelThreeDelayDate.dateValidationInfraRsnbynd"));
								}else if(date1.before(date2) && "Y".equals(lmtForm.getLegalOrArbitrationLevel3Flag())){
									errors.add("revsedESCODStpDateL3", new ActionMessage("error.rescodLevelThreeDelayDate.dateValidationInfraLglArb"));
								}else if ((lmtForm.getRevsedESCODStpDateL3().length() > 0)
										&& DateUtil.convertDate(locale, lmtForm.getRevsedESCODStpDateL3()).before(date3.getTime()))
								{
									errors.add("revsedESCODStpDateL3", new ActionMessage("error.redate.before.notallowed.escodDate"));
								}
							}
						}
						if((!"".equals(lmtForm.getRevsedESCODStpDateL3()) && lmtForm.getRevsedESCODStpDateL3() != null ) || "WS_SCOD_FAC_EDIT".equals(lmtForm.getEvent())) {

							if("N".equals(lmtForm.getInfaProjectFlag())){
								Calendar date1 = Calendar.getInstance();
								date1.setTime(DateUtil.convertDate(locale, lmtForm.getRevsedESCODStpDateL3()));
								Calendar date2 = Calendar.getInstance();
								date2.setTime(DateUtil.convertDate(locale, lmtForm.getEscodLevelThreeDelayDate()));
								date2.add(Calendar.YEAR,1);
								//date2.add(Calendar.DATE,1);
								Calendar date3 = Calendar.getInstance();
								date3.setTime(DateUtil.convertDate(locale, lmtForm.getEscodLevelThreeDelayDate()));
								date3.add(Calendar.DATE,1);
								if(date1.before(date2)) {
									errors.add("revsedESCODStpDateL3", new ActionMessage("error.rescodLevelThreeDelayDate.dateValidationNonInfra"));
								}else if ((lmtForm.getRevsedESCODStpDateL3().length() > 0)
										&& DateUtil.convertDate(locale, lmtForm.getRevsedESCODStpDateL3()).before(date3.getTime()))
								{
									errors.add("revsedESCODStpDateL3", new ActionMessage("error.redate.before.notallowed.escodDate"));
								}
							}

						}
					}

					if("".equals(lmtForm.getRevsedESCODStpDateL3()) || lmtForm.getRevsedESCODStpDateL3() == null) {
					errors.add("revsedESCODStpDateL3", new ActionMessage("error.string.mandatory")); 	
				}
				}

				if("2".equals(lmtForm.getLelvelDelaySCOD()) && "Y".equals(lmtForm.getLegalOrArbitrationLevel2Flag())) {
					if("".equals(lmtForm.getLeaglOrArbiProceed())){
						errors.add("leaglOrArbiProceed", new ActionMessage("error.string.mandatory")); 
					}

				}

				if("2".equals(lmtForm.getLelvelDelaySCOD()) && "Y".equals(lmtForm.getReasonBeyondPromoterLevel2Flag())) {
					if("".equals(lmtForm.getDetailsRsnByndPro())) {
						errors.add("detailsRsnByndPro", new ActionMessage("error.string.mandatory"));
					}
				}
				if("Y".equals(lmtForm.getInfaProjectFlag())) {
					if("2".equals(lmtForm.getLelvelDelaySCOD()) && "N".equals(lmtForm.getReasonBeyondPromoterLevel2Flag()) && "N".equals(lmtForm.getLegalOrArbitrationLevel2Flag())
							&& "N".equals(lmtForm.getChngOfOwnPrjFlagInfraLevel2()) && "N".equals(lmtForm.getChngOfProjScopeInfraLevel2())) {
						errors.add("reasonForRevisionInfraDelayLvl2", new ActionMessage("error.reaForRevisonLvlDelay.mandatory"));
					}
				}else {

					if("2".equals(lmtForm.getLelvelDelaySCOD()) && "N".equals(lmtForm.getChngOfOwnPrjFlagNonInfraLevel2()) && "N".equals(lmtForm.getChngOfProjScopeNonInfraLevel2())) {
						errors.add("reasonForRevisionNonInfraDelayLvl2", new ActionMessage("error.reaForRevisonLvlDelay.mandatory"));
					}
				}

				if("2".equals(lmtForm.getLelvelDelaySCOD()) && lmtForm.getReasonLevelTwoDelay() != null && !"".equals(lmtForm.getReasonLevelTwoDelay())) {
					if(lmtForm.getReasonLevelTwoDelay().length() > 200) {
						errors.add("reasonLevelTwoDelay", new ActionMessage("error.lengthofreason.exceed"));
					}

				}

				if("2".equals(lmtForm.getLelvelDelaySCOD()) && lmtForm.getLeaglOrArbiProceed() != null && !"".equals(lmtForm.getLeaglOrArbiProceed())) {
					if(lmtForm.getLeaglOrArbiProceed().length() > 4000) {
						errors.add("leaglOrArbiProceed", new ActionMessage("error.lengthOfLegOrbReason.exceed"));
					}
				}

				if("2".equals(lmtForm.getLelvelDelaySCOD()) && lmtForm.getDetailsRsnByndPro() != null && !"".equals(lmtForm.getDetailsRsnByndPro())) {
					if(lmtForm.getDetailsRsnByndPro().length() > 4000) {
						errors.add("detailsRsnByndPro", new ActionMessage("error.lengthOfBeyondReason.exceed"));
					}
				}

				if("3".equals(lmtForm.getLelvelDelaySCOD()) && lmtForm.getDetailsRsnByndProLevel3() != null && !"".equals(lmtForm.getDetailsRsnByndProLevel3())) {
					if(lmtForm.getDetailsRsnByndProLevel3().length() > 4000) {
						errors.add("detailsRsnByndProLevel3", new ActionMessage("error.lengthOfBeyondReason.exceed"));
					}
				}

				if("3".equals(lmtForm.getLelvelDelaySCOD()) && lmtForm.getLeaglOrArbiProceedLevel3() != null && !"".equals(lmtForm.getLeaglOrArbiProceedLevel3())) {
					if(lmtForm.getLeaglOrArbiProceedLevel3().length() > 4000) {
						errors.add("leaglOrArbiProceedLevel3", new ActionMessage("error.lengthOfLegOrbReason.exceed"));
					}
				}

				if("3".equals(lmtForm.getLelvelDelaySCOD()) && lmtForm.getReasonLevelThreeDelay() != null && !"".equals(lmtForm.getReasonLevelThreeDelay())) {
					if(lmtForm.getReasonLevelThreeDelay().length() > 200) {
						errors.add("reasonLevelThreeDelay", new ActionMessage("error.lengthofreason.exceed"));
					}

				}


				if("Y".equals(lmtForm.getInfaProjectFlag())) {
					if("3".equals(lmtForm.getLelvelDelaySCOD()) && "N".equals(lmtForm.getReasonBeyondPromoterLevel3Flag()) && "N".equals(lmtForm.getLegalOrArbitrationLevel3Flag())
							&& "N".equals(lmtForm.getChngOfOwnPrjFlagInfraLevel3()) && "N".equals(lmtForm.getChngOfProjScopeInfraLevel3())) {
						errors.add("reasonForRevisionInfraDelayLvl3", new ActionMessage("error.reaForRevisonLvlDelay.mandatory"));
					}
				}else {

					if("3".equals(lmtForm.getLelvelDelaySCOD()) && "N".equals(lmtForm.getChngOfOwnPrjFlagNonInfraLevel3()) && "N".equals(lmtForm.getChngOfProjScopeNonInfraLevel3())) {
						errors.add("reasonForRevisionNonInfraDelayLvl3", new ActionMessage("error.reaForRevisonLvlDelay.mandatory"));
					}
				}

				if("3".equals(lmtForm.getLelvelDelaySCOD()) && "Y".equals(lmtForm.getLegalOrArbitrationLevel3Flag())) {
					if("".equals(lmtForm.getLeaglOrArbiProceedLevel3())){
						errors.add("leaglOrArbiProceedLevel3", new ActionMessage("error.string.mandatory")); 
					}

				}

				if("3".equals(lmtForm.getLelvelDelaySCOD()) && "Y".equals(lmtForm.getReasonBeyondPromoterLevel3Flag())) {
					if("".equals(lmtForm.getDetailsRsnByndProLevel3())) {
						errors.add("detailsRsnByndProLevel3", new ActionMessage("error.string.mandatory"));
					}
				}


			}*/
			boolean isFieldExcluded = false;
			if(isReqBankArrFacExc) {
				IBankingArrangementFacExclusionJdbc exclusionJdbc = (IBankingArrangementFacExclusionJdbc) BeanHouse.get("bankingArrangementFacExclusionJdbc");
				isFieldExcluded = exclusionJdbc.isExcluded(lmtForm.getFacilitySystem(),
						lmtForm.getFacilityCat(), lmtForm.getFacilityName(), true);
			}

			if(StringUtils.isEmpty(lmtForm.getBankingArrangement())) {
				errors.add("bankingArrangement", new ActionMessage("error.string.mandatory"));
			}
			else if(isReqBankArrFacExc) {
				if(!isFieldExcluded) {
					if(IBankingArrangementFacExclusionConstant.NA.equals(lmtForm.getBankingArrangement())) {
						errors.add("bankingArrangement", 
								new ActionMessage("error.limit.bankingArrangement.na"));
					}
				}
			}

			if(StringUtils.isEmpty(lmtForm.getClauseAsPerPolicy())) {
				errors.add("clauseAsPerPolicy", new ActionMessage("error.string.mandatory"));
			}
			else if(isReqBankArrFacExc) {
				if(isFieldExcluded) {
					if(IBankingArrangementFacExclusionConstant.NA.equals(lmtForm.getBankingArrangement()) 
							&& !IBankingArrangementFacExclusionConstant.NA.equals(lmtForm.getClauseAsPerPolicy())) {
						errors.add("clauseAsPerPolicy", new ActionMessage("error.limit.clauseAsPerPolicy.na"));
					}
				}
				else {
					if(IBankingArrangementFacExclusionConstant.NA.equals(lmtForm.getClauseAsPerPolicy())) {
						errors.add("clauseAsPerPolicy", new ActionMessage("error.limit.clauseAsPerPolicy.na"));
					}
				}
			}

			if(StringUtils.isBlank(lmtForm.getIsDPRequired())) {
				errors.add("isDpRequired", new ActionMessage("error.string.mandatory"));
			}


		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return errors;
	}

	public static ActionErrors validateXRef(ActionForm aForm, Locale locale,FacilityBodyRestRequestDTO facilityRequestDTO) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		SimpleDateFormat DateFormat = new SimpleDateFormat("dd/MMM/yyyy");//ss
		//DateFormat.setLenient(false);
		try {
			XRefDetailForm xrefForm = (XRefDetailForm) aForm;
			String fcunsSystem=PropertyManager.getValue("fcubs.systemName");
			String ubslimitSystem=PropertyManager.getValue("ubs.systemName");
			
			IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
			
			IGeneralParamGroup generalParamGroup = generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[] generalParamEntries = generalParamGroup.getFeedEntries();
			Date applicationDate = new Date();
			for (int i = 0; i < generalParamEntries.length; i++) {
				if (generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")) {
					applicationDate = new Date(generalParamEntries[i].getParamValue());
					}
				}
			DateFormat df1 = new SimpleDateFormat("dd/MMM/yyyy");
			String appDate=df1.format(applicationDate);
			Date applicationDate1=df1.parse(appDate);
			
			
			if(EventConstant.EVENT_UPDATE_STATUS_UBS.equals(xrefForm.getEvent()) ){
				if("PENDING".equals(xrefForm.getStatus())){
					 errors.add("status", new ActionMessage("error.string.mandatory")); 
				}
				
				if(null!=xrefForm.getCoreStpRejectedReason() && xrefForm.getCoreStpRejectedReason().length()> 250){
					 errors.add("coreStpRejectedReason", new ActionMessage("error.limit.coreStpRejectReason")); 
				}
				
				if("NEW".equals(xrefForm.getLineAction()) && ICMSConstant.FCUBS_STATUS_PENDING_SUCCESS.equals(xrefForm.getStatus())){
					
				if(ASSTValidator.isValidAlphaNumStringWithSpace(xrefForm.getSerialNo())) {
						errors.add("serialNo", new ActionMessage("error.string.invalidCharacter"));
					}
					
				else if (!(errorCode = Validator.checkNumber(xrefForm.getSerialNo(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("serialNo", new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.NUMBER, errorCode), "1",
							IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10_STR));
				}
				}
			}/*else if (EventConstant.EVENT_CREATE_TS.equals(xrefForm.getEvent())
					|| EventConstant.EVENT_SUBMIT_TS.equals(xrefForm.getEvent())) {
				if("".equals(xrefForm.getFacilitySystemID())){
					 errors.add("facilitySystemID", new ActionMessage("error.string.mandatory")); 
				}
				
				
				if("N".equals(xrefForm.getSendToFile()) && "".equals(xrefForm.getSerialNo())){
					 errors.add("serialNo", new ActionMessage("error.string.mandatory")); 
				}else{
					if(ASSTValidator.isValidAlphaNumStringWithoutSpace(xrefForm.getSerialNo())) {
						errors.add("serialNo", new ActionMessage("error.string.invalidCharacter"));
					}
				}
				if("updateStatus".equals(xrefForm.getFromEvent()) && "".equals(xrefForm.getSerialNo()) && "N".equals(xrefForm.getSendToFile())) {
					 errors.add("serialNo", new ActionMessage("error.string.mandatory")); 
				}else if("updateStatus".equals(xrefForm.getFromEvent()) && "".equals(xrefForm.getSerialNo()) && "PENDING_SUCCESS".equals(xrefForm.getStatus())) {
					 errors.add("serialNo", new ActionMessage("error.string.mandatory")); 
				}
				if(null!=xrefForm.getCoreStpRejectedReason() && xrefForm.getCoreStpRejectedReason().length()> 200){
					 errors.add("coreStpRejectedReason", new ActionMessage("error.limit.psrCoreStpRejectReason")); 
				}
				if(null!=xrefForm.getLimitRemarks() && xrefForm.getLimitRemarks().length()> 200){
					 errors.add("limitRemarks", new ActionMessage("error.limit.psrCoreStpRejectReason")); 
				}
				if(null!=xrefForm.getLimitRemarks() && xrefForm.getLimitRemarks().contains("|")){
					errors.add("limitRemarks", new ActionMessage("error.string.invalidCharacter"));
				}
				if("".equals(xrefForm.getTenure())){
					 errors.add("tenure", new ActionMessage("error.string.mandatory")); 
				}else if(ASSTValidator.isValidAlphaNumStringWithoutSpace(xrefForm.getTenure())) {
					errors.add("tenure", new ActionMessage("error.string.invalidCharacter"));
				}else if (!(errorCode = Validator.checkNumber(xrefForm.getTenure(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10))
					.equals(Validator.ERROR_NONE)) {
				errors.add("tenure", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.NUMBER, errorCode), "1",
						IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10_STR));
				}
				if(("".equals(xrefForm.getSellDownPeriod()) || null==xrefForm.getSellDownPeriod()) && "999".equals(xrefForm.getLineNo())){
					 errors.add("sellDownPeriod", new ActionMessage("error.string.mandatory")); 
				}
								
				if (!("".equals(xrefForm.getSellDownPeriod())) && !(isNumeric(xrefForm.getSellDownPeriod()))) {
					errors.add("sellDownPeriod", new ActionMessage("error.string.invalidNumbers"));
				}
				if (!("".equals(xrefForm.getLiabilityId())) && !(isAlphaNumeric(xrefForm.getLiabilityId()))) {
					errors.add("liabilityId", new ActionMessage("error.string.invalidCharacter"));
				}
				
				if(!AbstractCommonMapper.isEmptyOrNull(xrefForm.getUtilizedAmount())){
					//if(ASSTValidator.isValidAlphaNumStringWithSpace(xrefForm.getUtilizedAmount())) {
					if(!(ASSTValidator.isValidDecimalNumberWithComma(UIUtil.removeComma(xrefForm.getUtilizedAmount())))) {
						errors.add("utilizedAmount", new ActionMessage("error.string.invalidCharacter"));
					}
				}
				
						if (!(errorCode = Validator.checkAmount(xrefForm.getUtilizedAmount().replaceAll(",", ""), false, 0,
								IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2,
								IGlobalConstant.DEFAULT_CURRENCY, locale))
								.equals(Validator.ERROR_NONE)) {
							errors.add("utilizedAmount", new ActionMessage(ErrorKeyMapper
									.map(ErrorKeyMapper.NUMBER, errorCode), "0",
									IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
						}
				

						if (!(errorCode = Validator.checkAmount(xrefForm.getReleasedAmount().replaceAll(",", ""), true, 0,
										IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2,
										IGlobalConstant.DEFAULT_CURRENCY, locale))
										.equals(Validator.ERROR_NONE)) {
									errors.add("releasedAmount", new ActionMessage(ErrorKeyMapper
											.map(ErrorKeyMapper.NUMBER, errorCode), "0",
											IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
						}else if(!(ASSTValidator.isValidDecimalNumberWithComma((xrefForm.getReleasedAmount().replaceAll(",", ""))))) {
							errors.add("releasedAmount", new ActionMessage("error.string.invalidCharacter"));
						}
				
				if("".equals(xrefForm.getStatus()) && EventConstant.EVENT_SUBMIT_TS.equals(xrefForm.getEvent())) {
					errors.add("status", new ActionMessage("error.string.mandatory"));
				}
				if("".equals(xrefForm.getReleaseDate())){
					 errors.add("releaseDate", new ActionMessage("error.string.mandatory")); 
				}
				if("".equals(xrefForm.getDateOfReset())){
					 errors.add("dateOfReset", new ActionMessage("error.string.mandatory")); 
				}				
							
				
				if("NEW".equals(xrefForm.getLineAction()) && ICMSConstant.FCUBS_STATUS_PENDING_SUCCESS.equals(xrefForm.getStatus())){
					
					if(ASSTValidator.isValidAlphaNumStringWithSpace(xrefForm.getSerialNo())) {
							errors.add("serialNo", new ActionMessage("error.string.invalidCharacter"));
						}
						
					else if (!(errorCode = Validator.checkNumber(xrefForm.getSerialNo(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10))
							.equals(Validator.ERROR_NONE)) {
						errors.add("serialNo", new ActionMessage(ErrorKeyMapper
								.map(ErrorKeyMapper.NUMBER, errorCode), "1",
								IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10_STR));
					}
					}
			
			}*/else{
			
			if(null!=xrefForm.getEvent() && ! EventConstant.EVENT_EDIT_RELEASED_LINE_DETAILS.equals(xrefForm.getEvent())|| EventConstant.EVENT_UPDATE_STATUS_TS.equals(xrefForm.getEvent())) {
				
		
			if(ASSTValidator.isValidAlphaNumStringWithSpace(xrefForm.getSerialNo())) {
				errors.add("serialNo", new ActionMessage("error.string.invalidCharacter"));
			}
			else if (!(errorCode = Validator.checkNumber(xrefForm.getSerialNo(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2))
					.equals(Validator.ERROR_NONE)) {
				errors.add("serialNo", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.NUMBER, errorCode), "1",
						IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_STR));
			}
			if (!(errorCode = Validator.checkString(xrefForm.getFacilitySystemID(), true, 1, 100)).equals(Validator.ERROR_NONE)) { 
				 errors.add("facilitySystemID", new ActionMessage("error.string.mandatory")); 
			}
			
			if (!(errorCode = Validator.checkString(xrefForm.getLiabBranch(), true, 1, 100)).equals(Validator.ERROR_NONE)) { 
				 errors.add("liabBranch", new ActionMessage("error.string.mandatory")); 
			}
			
			if(fcunsSystem.equals(xrefForm.getFacilitySystem()) || ubslimitSystem.equals(xrefForm.getFacilitySystem())){
//			if(!"create_ubs".equals(xrefForm.getEvent())  && !"NEW".equals(xrefForm.getAction())){
//			if (!(errorCode = Validator.checkNumber(xrefForm.getSerialNo(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10))
//					.equals(Validator.ERROR_NONE)) {
//				errors.add("serialNo", new ActionMessage(ErrorKeyMapper
//						.map(ErrorKeyMapper.NUMBER, errorCode), "1",
//						IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10_STR));
//			}
//			}
				
				if(("create_ubs".equals(xrefForm.getEvent())  && "N".equals(xrefForm.getSendToFile())) || ("submit_ubs".equals(xrefForm.getEvent())  && "N".equals(xrefForm.getSendToFile()) && "NEW".equals(xrefForm.getLineAction()))
				|| ("submit_ubs_rejected".equals(xrefForm.getEvent())  && "N".equals(xrefForm.getSendToFile()) && "NEW".equals(xrefForm.getLineAction()))){
				if (!(errorCode = Validator.checkNumber(xrefForm.getSerialNo(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("serialNo", new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.NUMBER, errorCode), "1",
							IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10_STR));
				}
				}
			}else{

				if (!(errorCode = Validator.checkNumber(xrefForm.getSerialNo(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("serialNo", new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.NUMBER, errorCode), "1",
							IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10_STR));
				}
				
			}
			
			if(ASSTValidator.isValidAlphaNumStringWithSpace((xrefForm.getReleasedAmount().replaceAll(",", "")))) {
				errors.add("releasedAmount", new ActionMessage("error.string.invalidCharacter"));
			}
			
					if (!(errorCode = Validator.checkAmount(xrefForm.getReleasedAmount().replaceAll(",", ""), true, 0,
							IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2,
							IGlobalConstant.DEFAULT_CURRENCY, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("releasedAmount", new ActionMessage(ErrorKeyMapper
								.map(ErrorKeyMapper.NUMBER, errorCode), "0",
								IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2_STR));
					}
					if(!AbstractCommonMapper.isEmptyOrNull(xrefForm.getUtilizedAmount())){
						//if(ASSTValidator.isValidAlphaNumStringWithSpace(xrefForm.getUtilizedAmount())) {
						if(ASSTValidator.isValidAlphaNumStringWithSpace(UIUtil.removeComma(xrefForm.getUtilizedAmount()))) {
							errors.add("utilizedAmount", new ActionMessage("error.string.invalidCharacter"));
						}
					}
					
							if (!(errorCode = Validator.checkAmount(xrefForm.getUtilizedAmount().replaceAll(",", ""), false, 0,
									IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2,
									IGlobalConstant.DEFAULT_CURRENCY, locale))
									.equals(Validator.ERROR_NONE)) {
								errors.add("utilizedAmount", new ActionMessage(ErrorKeyMapper
										.map(ErrorKeyMapper.NUMBER, errorCode), "0",
										IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2_STR));
							}
					 
			
							
			 /*if (xrefForm.getReleasedAmount() != null && !("".equals(xrefForm.getReleasedAmount().replaceAll(",", "").trim())))
			 {
				 if (xrefForm.getUtilizedAmount() != null && !("".equals(xrefForm.getUtilizedAmount().replaceAll(",", "").trim())))
				 {
			 BigDecimal	releasedAmount = new BigDecimal(xrefForm.getReleasedAmount().replaceAll(",", ""));
			 BigDecimal utilizedAmount = new BigDecimal(xrefForm.getUtilizedAmount().replaceAll(",", ""));
			 
				if(utilizedAmount.compareTo(releasedAmount) > 0){
					DefaultLogger.debug(xrefForm," [utilizedAmount > releasedAmount] ==24===> "+utilizedAmount +" > "+ releasedAmount);
						errors.add("utilizedAmount", new ActionMessage("error.amount.not.greaterthan", "utilized Amount", "released Amount"));
				}
				 }
			 }*/
							
					if (xrefForm.getReleaseDate() != null && !xrefForm.getReleaseDate().toString().trim().isEmpty()) {
						if (isValidDate(xrefForm.getReleaseDate().toString().trim()))
							xrefForm.setReleaseDate(xrefForm.getReleaseDate().toString().trim());
						else
							errors.add("releasedDateErr", new ActionMessage("error.date.format0"));
					} else {
						xrefForm.setReleaseDate("");
					}
			 
					if ((xrefForm.getReleaseDate().length() > 0)
							&& isValidDate(xrefForm.getReleaseDate().toString().trim())
							&& DateUtil.convertDate(locale, xrefForm.getReleaseDate()).after(DateUtil.getDate())) {
						errors.add("releaseDateFutureErr", new ActionMessage("error.date.future.notallowed"));
					}

					
					if (null != xrefForm.getDateOfReset() && !xrefForm.getDateOfReset().toString().trim().isEmpty()) {
						if (isValidDate(xrefForm.getDateOfReset().toString().trim()))
							xrefForm.setDateOfReset(xrefForm.getDateOfReset().toString().trim());
						else
							errors.add("LmtExpiryDateErr", new ActionMessage("error.date.format0"));
					} else {
						errors.add("LmtExpiryDate", new ActionMessage("error.date.mandatory"));
					}
			 
			 
					if ((xrefForm.getDateOfReset().length() > 0)
							&& isValidDate(xrefForm.getDateOfReset().toString().trim())
							&& DateUtil.convertDate(locale, xrefForm.getDateOfReset()).before(DateUtil.getDate())) {
						errors.add("LmtExpiryDatePastErr", new ActionMessage("error.date.past"));
					}
			 
			 
				 if (!(errorCode = Validator.checkString(xrefForm.getIsPrioritySector(), true, 1, 40))
							.equals(Validator.ERROR_NONE)) {
						errors.add("isPrioritySector", new ActionMessage("error.string.mandatory"));
					}
				 if(xrefForm.getIsPrioritySector()!=null && xrefForm.getIsPrioritySector().equalsIgnoreCase("Blank")){
						xrefForm.setIsPrioritySector("");
					}
				 
				 else if(xrefForm.getIsPrioritySector()!=null){
			 if (!(errorCode = Validator.checkString(xrefForm.getPrioritySector(), true, 1, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("prioritySector", new ActionMessage("error.string.mandatory", "1", "40"));
				}
			 }
			
			
			 if (!(errorCode = Validator.checkString(xrefForm.getIsCapitalMarketExposer(), true, 1, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isCapitalMarketExposer", new ActionMessage("error.string.mandatory", "1", "40"));
				}
			 if (!(errorCode = Validator.checkString(xrefForm.getIsRealEstateExposer(), true, 1, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isRealEstateExposer", new ActionMessage("error.string.mandatory", "1", "40"));
				}
			 else if(xrefForm.getIsRealEstateExposer().equals("Yes")){
				 if(xrefForm.getEstateType() == null || xrefForm.getEstateType().equals("")){
					 errors.add("commRealEstateType", new ActionMessage("error.string.mandatory", "1", "40"));
				 }else{
					 if(xrefForm.getEstateType().equalsIgnoreCase("Commercial Real estate")){
						 if (!(errorCode = Validator.checkString(xrefForm.getCommRealEstateType(), true, 1, 40))
									.equals(Validator.ERROR_NONE)) {
								errors.add("commRealEstateType", new ActionMessage("error.string.mandatory", "1", "40"));
							}
					 }
				 }
			 }
						
			 if (!(errorCode = Validator.checkString(xrefForm.getInterestRateType(), true, 1, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("intrestRateType", new ActionMessage("error.string.mandatory"));
				}else {
						if (xrefForm.getInterestRateType().equals("fixed") && ((xrefForm.getIntRateFix().equals("")) || (xrefForm.getIntRateFix().equals("0")))){
						errors.add("intrestRate", new ActionMessage("error.string.mandatory"));
					}else if(xrefForm.getInterestRateType().equals("fixed") && !xrefForm.getIntRateFix().equals("")){
						if (!(errorCode = Validator.checkNumber(xrefForm.getIntRateFix(), false , 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_2_2,2,locale))
								.equals(Validator.ERROR_NONE)) {
							errors.add("intrestRate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100"));
						}
					}
					if(ASSTValidator.isValidAlphaNumStringWithSpace(xrefForm.getIntRateFloatingMargin())) {
						errors.add("intRateFloatingMargin", new ActionMessage("error.string.invalidCharacter"));
					}
					if(ASSTValidator.isValidAlphaNumStringWithSpace(xrefForm.getIntRateFix())) {
						errors.add("intrestRate", new ActionMessage("error.string.invalidCharacter"));
					}
					
					if(xrefForm.getInterestRateType().equals("floating") &&	!xrefForm.getIntRateFloatingMargin().equals("") ){
						if (!(errorCode = Validator.checkNumber(xrefForm.getIntRateFloatingMargin(), false,0 ,IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_2_2 ,2 ,locale))
								.equals(Validator.ERROR_NONE)) {

							errors.add("margin", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100"));
						}
					}
					
					if(ASSTValidator.isValidAlphaNumStringWithSpace(xrefForm.getIntRateFloatingMargin())) {
						errors.add("margin", new ActionMessage("error.string.invalidCharacter"));
					}
					
					/*if(("floating").equals(xrefForm.getInterestRateType())) {
						if(xrefForm.getIntRateFloatingType().trim().isEmpty() || 
							xrefForm.getIntRateFloatingRange().trim().isEmpty()  || null == xrefForm.getIntRateFloatingMarginFlag() || 
							xrefForm.getIntRateFloatingMarginFlag().trim().isEmpty() || null == xrefForm.getIntRateFloatingMargin() ||
							xrefForm.getIntRateFloatingMargin().trim().isEmpty() ){
						errors.add("interestRateType", new ActionMessage("error.string.mandatory"));
					}
					if(xrefForm.getInterestRateType().equals("floating") &&	!xrefForm.getIntRateFloatingMargin().equals("") ){
						if (!(errorCode = Validator.checkNumber(xrefForm.getIntRateFloatingMargin(), false,0 ,IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE ,3 ,locale))
								.equals(Validator.ERROR_NONE)) {

							errors.add("intRateFloatingMargin", new ActionMessage(
									ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100"));
						}
					}
				}*/
				}
	 
			 
			 if (null != xrefForm.getUploadDate() && !xrefForm.getUploadDate().toString().trim().isEmpty()) {
					if (isValidDate(xrefForm.getUploadDate().toString().trim()))
						xrefForm.setUploadDate(xrefForm.getUploadDate().toString().trim());
					else
						errors.add("uploadDateErr", new ActionMessage("error.date.format0"));
				} else {
					xrefForm.setUploadDate("");
				}
		 
				if ((xrefForm.getUploadDate().length() > 0)
						&& isValidDate(xrefForm.getUploadDate().toString().trim())
						&& DateUtil.convertDate(locale, xrefForm.getUploadDate()).after(DateUtil.getDate())) {
					errors.add("uploadDateFutureErr", new ActionMessage("error.date.future.notallowed"));
				}
			 
			 
//			 if(null != xrefForm.getUploadDate() && !xrefForm.getUploadDate().toString().trim().isEmpty()){ 
//				 try {
//					 DateFormat.parse(xrefForm.getUploadDate().toString().trim());
//					 xrefForm.setUploadDate(xrefForm.getUploadDate().trim());
//					 if ((xrefForm.getUploadDate().length() > 0)
//							 && DateUtil.convertDate(locale, xrefForm.getUploadDate()).after(DateUtil.getDate()))
//					 {
//						 errors.add("uploadDate", new ActionMessage("error.date.future.notallowed"));
//					 }
//				 } catch (ParseException e) {
//					 errors.add("uploadDate",new ActionMessage("error.date.format0"));
//				 }
//
//			 }else{
//					xrefForm.setUploadDate("");
//				}
			 if(fcunsSystem.equals(xrefForm.getFacilitySystem()) || ubslimitSystem.equals(xrefForm.getFacilitySystem())){
			 //main line code mandatory for subline
				 
				 if("Yes".equals(xrefForm.getLimitType()))
				/*if(null==xrefForm.getIsDayLightLimitAvailable() || "".equals(xrefForm.getIsDayLightLimitAvailable())){
						 errors.add("mainLineCode", new ActionMessage("error.date.mandatory"));  
				 }*/
				 if(null==xrefForm.getMainLineCode() || "".equals(xrefForm.getMainLineCode())){
					 errors.add("mainLineCode", new ActionMessage("error.mandatory"));  
				 }
				 if (null==xrefForm.getLiabBranch() || "".equals(xrefForm.getLiabBranch())) {
					  errors.add("liabBranch", new ActionMessage("error.date.mandatory")); 
					  }
				 
				 
				 if (null != xrefForm.getLimitStartDate() && !xrefForm.getLimitStartDate().toString().trim().isEmpty()) {
					 if (isValidDate(xrefForm.getLimitStartDate().toString().trim()))
						 xrefForm.setLimitStartDate(xrefForm.getLimitStartDate().toString().trim());
					 else
						 errors.add("lmtStartDateErr", new ActionMessage("error.date.format0"));
					 } else {
						 errors.add("lmtStartDate", new ActionMessage("error.date.mandatory")); 
						 }
				 
				 
				 
				 if ((xrefForm.getLimitStartDate().length() > 0)
						 && isValidDate(xrefForm.getLimitStartDate().toString().trim())
						 && DateUtil.convertDate(locale, xrefForm.getLimitStartDate()).after(applicationDate1)){
					 
					 errors.add("lmtStartDateFutureErr", new ActionMessage("error.date.future"));
					 }
				
				 
			 if ((xrefForm.getAvailable() == null) || (xrefForm.getAvailable().equals(""))) {
					errors.add("available", new ActionMessage("error.date.mandatory"));
				}
			 
			 if ((xrefForm.getFreeze() == null) || (xrefForm.getFreeze().equals(""))) {
					errors.add("freeze", new ActionMessage("error.date.mandatory"));
				}
			 
			 if ((xrefForm.getRevolvingLine() == null) || (xrefForm.getRevolvingLine().equals(""))) {
					errors.add("revolvingLine", new ActionMessage("error.date.mandatory"));
				}
						 
			 if ((xrefForm.getScmFlag() == null) || (xrefForm.getScmFlag().equals(""))) {
					errors.add("scmFlag", new ActionMessage("error.date.mandatory"));
				}else {
					String borrowScmFlag = validateScmFlag(xrefForm.getCustomerID());
					if(borrowScmFlag.equalsIgnoreCase("No") && xrefForm.getScmFlag().equalsIgnoreCase("Yes")) {
						errors.add("scmFlag", new ActionMessage("error.borrowerScmFlag.change"));
					}
				}
			 
//			 if(xrefForm.getLastavailableDate()!=null && !xrefForm.getLastavailableDate().toString().trim().isEmpty()){ //ss
//					try {
//						DateFormat.parse(xrefForm.getLastavailableDate().toString().trim());
//						xrefForm.setLastavailableDate(xrefForm.getLastavailableDate().trim());
//					} catch (ParseException e) {
//						errors.add("lastAvailDate",new ActionMessage("error.wsdl.relationshipDate.invalid.format"));
//					}
//					
//				}else{
//					xrefForm.setLastavailableDate("");
//				}
			 
			 if (null != xrefForm.getLastavailableDate()  && !xrefForm.getLastavailableDate().toString().trim().isEmpty()) {
					if (isValidDate(xrefForm.getLastavailableDate().toString().trim()))
						xrefForm.setLastavailableDate(xrefForm.getLastavailableDate().toString().trim());
					else
						errors.add("lastAvailDateErr", new ActionMessage("error.date.format0"));
				} else {
					xrefForm.setLastavailableDate("");
				}
			 
			 
			 
			/* if ((xrefForm.getSegment() == null) || (xrefForm.getSegment().equals(""))) {
					errors.add("segment", new ActionMessage("error.date.mandatory"));
				}*/
			/* 
			 if ((xrefForm.getRuleId() == null) || (xrefForm.getRuleId().equals(""))) {
					errors.add("ruleId", new ActionMessage("error.date.mandatory"));
				}
			 
			 if ((xrefForm.getUncondiCancl() == null) || (xrefForm.getUncondiCancl().equals(""))) {
					errors.add("uncondiCanclCommit", new ActionMessage("error.date.mandatory")); //ss
				}*/
			 
			 if (!(errorCode = Validator.checkString(xrefForm.getCloseFlag(), true, 1, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("Closeflag", new ActionMessage("error.string.mandatory", "1", "40"));
				}
			 
			 if (!(errorCode = Validator.checkString(xrefForm.getLimitRestriction(), false, 1, 1000))
					 .equals(Validator.ERROR_NONE)) {
					 errors.add("limitCustomerRestrict", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					 "1000"));
			}
			 
			 if (!(errorCode = Validator.checkString(xrefForm.getInternalRemarks(), false, 1, 150))
					 .equals(Validator.ERROR_NONE)) {
					 errors.add("internalRemarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					 "1000"));
			}
			/*if ((xrefForm.getInternalRemarks() != null) || (!xrefForm.getInternalRemarks().equals(""))) {
				if(xrefForm.getInternalRemarks().contains(","))
					 errors.add("internalRemarks", new ActionMessage("error.string.internalRemarks"));
			}*/
			if(ASSTValidator.isValidInternalRemarks(xrefForm.getInternalRemarks())) {
				errors.add("internalRemarks", new ActionMessage("error.string.invalidCharacter"));
			}
			
			if (null != xrefForm.getLimitTenorDays() && !"".equalsIgnoreCase(xrefForm.getLimitTenorDays())) {
				if (!(StringUtils.isNumeric(xrefForm.getLimitTenorDays()))) {
              		 errors.add("limitTenorDays", new ActionMessage("error.amount.format"));
				}
				
				if (!(errorCode = Validator.checkNumber(xrefForm.getLimitTenorDays(), false, 0, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE, 3, locale)).equals(Validator.ERROR_NONE)) {
								errors.add("limiIndays", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "999"));
								}
			}
			
			/* if("Yes".equalsIgnoreCase(xrefForm.getIntradayLimitFlag()))
			 {
			 if (! (errorCode = Validator.checkDate(xrefForm.getIntradayLimitExpiryDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				  errors.add("intradayLimitExpiryDateError", new ActionMessage("error.date.mandatory", "1", "256")); 
				  }	
			 }*/
			/*if(xrefForm.getDayLightLimit()!=null && !xrefForm.getDayLightLimit().trim().isEmpty())
			 {
			 if (null==xrefForm.getIntradayLimitExpiryDate() || "".equals(xrefForm.getIntradayLimitExpiryDate())) {
				  errors.add("intradayLimitExpiryDate", new ActionMessage("error.string.mandatory")); 
				  }
			 }*/
			
			/*if(null != xrefForm.getIntradayLimitExpiryDate() && !xrefForm.getIntradayLimitExpiryDate().trim().isEmpty()) {
				if(null != xrefForm.getDayLightLimit() && !xrefForm.getDayLightLimit().trim().isEmpty()) {
					if(!"Yes".equalsIgnoreCase(xrefForm.getIsDayLightLimitAvailable())) {
						errors.add("dayLightLmtAvail", new ActionMessage("error.not.valid.field.value"));
					}
					
				}
			}*/
			
			
			if(null != xrefForm.getIntradayLimitExpiryDate() && !xrefForm.getIntradayLimitExpiryDate().toString().trim().isEmpty()){
				if (isValidDate(xrefForm.getIntradayLimitExpiryDate().toString().trim()))
					xrefForm.setIntradayLimitExpiryDate(xrefForm.getIntradayLimitExpiryDate().toString().trim());
				else
					errors.add("intradayLimitExpDateErr", new ActionMessage("error.date.format0"));
			}else{
				xrefForm.setIntradayLimitExpiryDate("");
				}
			 
			 if (null != xrefForm.getIntradayLimitExpiryDate()
					 && (xrefForm.getIntradayLimitExpiryDate().length() > 0)
					 && isValidDate(xrefForm.getIntradayLimitExpiryDate().toString().trim())
					 && DateUtil.convertDate(locale, xrefForm.getIntradayLimitExpiryDate()).before(applicationDate1)) {
				 errors.add("intradayLimitPastDateError", new ActionMessage("error.date.before.notallowed"));
				 }
			 
			 /*if("Yes".equalsIgnoreCase(xrefForm.getIntradayLimitFlag()))
			 {
			 if (null==xrefForm.getDayLightLimit() || "".equals(xrefForm.getDayLightLimit())) {
				  errors.add("dayLightLimitError", new ActionMessage("error.string.mandatory")); 
				  }
			 }*/
			 /*if(xrefForm.getIntradayLimitExpiryDate()!=null && !xrefForm.getIntradayLimitExpiryDate().trim().isEmpty()) //ss
			 {
			 if (null==xrefForm.getDayLightLimit() || "".equals(xrefForm.getDayLightLimit())) {
				  errors.add("dayLightLimitError", new ActionMessage("error.string.mandatory")); 
				  }
			 }*/
			 
			 Calendar calendar = Calendar.getInstance();
			 calendar.add(Calendar.DATE, -1);
			 
			 if(null !=xrefForm.getIdlEffectiveFromDate() ) {
			 if ( (xrefForm.getIdlEffectiveFromDate().length() > 0) ){
				 Calendar date3 = Calendar.getInstance();
					date3.setTime(DateUtil.convertDate(locale, xrefForm.getIdlEffectiveFromDate()));
					date3.add(Calendar.DATE,1);
					xrefForm.setIdlExpiryDate(DateUtil.formatDate(locale,date3.getTime()));
			 }
			 
			 
			 if ((xrefForm.getIdlEffectiveFromDate().length() > 0)
						&& DateUtil.convertDate(locale, xrefForm.getIdlEffectiveFromDate()).before(calendar.getTime()))
			 {
				 errors.add("idlEffectiveFromDate", new ActionMessage("error.date.before.notallowed"));
			 }
			 }
			 if (("".equals(xrefForm.getIdlEffectiveFromDate()) || null == (xrefForm.getIdlEffectiveFromDate())) && (null != xrefForm.getIdlAmount() && !"".equals(xrefForm.getIdlAmount()))) {
					errors.add("idlEffectiveFromDate", new ActionMessage("error.date.mandatory"));
				}
			 
			 
			 String customerId = xrefForm.getCustomerID();
			 LimitDAO limitDAO=new LimitDAO();
			 if(null != xrefForm.getIdlExpiryDate() && !"".equals(xrefForm.getIdlExpiryDate())) {
			 String camExtensionDate = limitDAO.getCamExtensionDateMethod(customerId);
			 System.out.println("camExtensionDate=>"+camExtensionDate+"......xrefForm.getIdlExpiryDate()=>"+xrefForm.getIdlExpiryDate());
						Date d2 = DateUtil.convertDate(locale, xrefForm.getIdlExpiryDate());
						Date d1 = DateUtil.convertDate(locale, camExtensionDate);
						int a = d1.compareTo(d2);
						
						if (a < 0) {
							errors.add("idlExpiryDate", new ActionMessage("error.date.compareDate.greater",
									"IDL Expiry Date" ,"Extension Date" ));
						}
						
			 }
			 
			 if(null != xrefForm.getIdlAmount() && ASSTValidator.isValidAlphaNumStringWithSpace((xrefForm.getIdlAmount().replaceAll(",", "")))) {
					errors.add("idlAmount", new ActionMessage("error.string.invalidCharacter"));
				}
				
			 if((null != xrefForm.getIdlAmount() && !"".equals(xrefForm.getIdlAmount())) || (null!=xrefForm.getIdlEffectiveFromDate()  && xrefForm.getIdlEffectiveFromDate().length() > 0)) {
						if (!(errorCode = Validator.checkAmount(xrefForm.getIdlAmount().replaceAll(",", ""), true, 0,
								IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_40_2,
								IGlobalConstant.DEFAULT_CURRENCY, locale))
								.equals(Validator.ERROR_NONE)) {
							errors.add("idlAmount", new ActionMessage(ErrorKeyMapper
									.map(ErrorKeyMapper.NUMBER, errorCode), "0",
									IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_40_2_STR));
						}
			 }
			 
			 if((null != xrefForm.getIdlAmount() && !"".equals(xrefForm.getIdlAmount())) || (null!=xrefForm.getIdlEffectiveFromDate())){
				 BigDecimal idlAmt = new BigDecimal(xrefForm.getIdlAmount().replaceAll(",", ""));
				 BigDecimal releasableAmt = new BigDecimal(facilityRequestDTO.getReleasableAmount());
				 System.out.println("MiLimitValidatorRest.java=>idlAmt=>"+idlAmt+".....releasableAmt=>"+releasableAmt);
				 if( idlAmt.compareTo(releasableAmt) > 0){
							errors.add("idlAmount", new ActionMessage("error.amount.not.greaterthan", "IDL Amount", "Releasable Amount"));
			 }
			 }	
			 
			 
			 
			 if(null != xrefForm.getIntradayLimitExpiryDate() && !xrefForm.getIntradayLimitExpiryDate().toString().trim().isEmpty() && isValidDate(xrefForm.getIntradayLimitExpiryDate().toString().trim())) {
				 if(null==xrefForm.getDayLightLimit() || xrefForm.getDayLightLimit().trim().isEmpty()) {
					 errors.add("dayLightLimit", new ActionMessage("error.string.mandatory")); 
				 }
			 }
			 
			 if(null!=xrefForm.getDayLightLimit() && ASSTValidator.isValidAlphaNumStringWithSpace(xrefForm.getDayLightLimit().replaceAll(",", ""))) {
					errors.add("dayLightLimitSpecialCharError", new ActionMessage("error.string.invalidCharacter"));
				 }
			 else if (null!=xrefForm.getDayLightLimit() && xrefForm.getDayLightLimit().length() > 10) 
			 {
				 errors.add("dayLightLimitLengthError", new ActionMessage("error.string.length.exceeded")); 
		      }
			 
			 else if (null!=xrefForm.getDayLightLimit() && xrefForm.getDayLightLimit().length() > 0) {
				 int count=0;
				 for(int i=0;i<xrefForm.getDayLightLimit().length();i++)
				 {
					 String dayLightLimit = null;
					 char alpha = '\u0000' ;
					 dayLightLimit = xrefForm.getDayLightLimit();
					 alpha = dayLightLimit.charAt(i);
					 count++;
					 if ((alpha >= 'a' && alpha <= 'z') || (alpha >= 'A' && alpha <= 'Z'))
					 {
						 errors.add("dayLightLimitNumericError", new ActionMessage("error.number.format")); 
						 break;
					 }
					 else if(alpha == '.')
					 {
						 if(xrefForm.getDayLightLimit().length()-count>2)
						 {
							 errors.add("dayLightLimitDecimalExceed", new ActionMessage("error.dayLightLimit.decimalExceed")); 
							 break;
						 } 
					 }
				 }
			 }
		}
		}//main if	 
				//Start Santosh UBS-LIMIT UPLOAD	
				IUdfDao udfDao = (IUdfDao)BeanHouse.get("udfDao");
				List udfMandatoryList=udfDao.getUdfByMandatory("3");
				List udfNumericList=udfDao.getUdfByFieldTypeId("3",7);
				if(null!=xrefForm.getUdfAllowed() && !"".equalsIgnoreCase(xrefForm.getUdfAllowed())) {
					String[] udfAllowedList = null;
					if(xrefForm.getUdfAllowed().contains(",")) {
					udfAllowedList = xrefForm.getUdfAllowed().split(",");
					
					String[] udfAllowedListTemp = null;
					List udfByModuleId = udfDao.getUdfByModuleId("3");
					
					String enabledUdfAllowed="";
					for(String udfSelected :udfAllowedList) {
						if(null!=udfByModuleId && udfByModuleId.size()>0) {
							for(int k=0; k<udfByModuleId.size() ; k++) {
							IUdf udf=(IUdf)udfByModuleId.get(k);
							if("ACTIVE".equals(udf.getStatus())) {
								int sequence = udf.getSequence();
								
							if(String.valueOf(sequence).equals(udfSelected)) {
								enabledUdfAllowed=udfSelected+","+enabledUdfAllowed;
								
							}
							
							
							}
							}
						}
					}
					
					System.out.println("enabledUdfAllowed 1  :"+enabledUdfAllowed);
					if(enabledUdfAllowed.endsWith(",")) {
						enabledUdfAllowed=enabledUdfAllowed.substring(0, enabledUdfAllowed.length()-1);
						System.out.println("enabledUdfAllowed inside if:"+enabledUdfAllowed);
					}
					
					if(!enabledUdfAllowed.isEmpty()) {
						udfAllowedList = enabledUdfAllowed.split(",");
					}
					}
					else {
						udfAllowedList = new String[1];
					udfAllowedList[0] = xrefForm.getUdfAllowed();
					}
					
					List udfByModuleFieldTypeId = udfDao.getUdfByFieldTypeId("3",4);
					if(null!=udfByModuleFieldTypeId) {
					for(int m=0;m<udfByModuleFieldTypeId.size();m++) {
						IUdf iUdf=(IUdf)udfByModuleFieldTypeId.get(m);
						
						for(String udfAllowed: udfAllowedList) {
							if(udfAllowed.equals(String.valueOf(iUdf.getSequence()))) {
								
								String[] split = iUdf.getOptions().split(",");
							
								
								if(iUdf.getSequence()== 1) { 
									String udfObj= xrefForm.getUdf1();
									if(!(udfObj == null || udfObj.trim().equals(""))){
									for(int l=0;l<split.length;l++) {
										String optionData = split[l].trim();
									if(optionData.equals(udfObj)) {
										break;
									}else if (l==split.length-1) {
										xrefForm.setUdf1(null);
									}
									}
								}
						}else if(iUdf.getSequence()== 2) { 
							String udfObj= xrefForm.getUdf2();
							if(!(udfObj == null || udfObj.trim().equals(""))){
							for(int l=0;l<split.length;l++) {
								String optionData = split[l].trim();
							if(optionData.equals(udfObj)) {
								break;
							}else if (l==split.length-1) {
								xrefForm.setUdf2(null);
							}
						}
						}
				}else if(iUdf.getSequence()== 3) { 
					String udfObj= xrefForm.getUdf3();
					if(!(udfObj == null || udfObj.trim().equals(""))){
					for(int l=0;l<split.length;l++) {
						String optionData = split[l].trim();
					if(optionData.equals(udfObj)) {
						break;
					}else if (l==split.length-1) {
						xrefForm.setUdf3(null);
					}
				}
				}
		}else if(iUdf.getSequence()== 4) { 
					String udfObj= xrefForm.getUdf4();
					if(!(udfObj == null || udfObj.trim().equals(""))){
					for(int l=0;l<split.length;l++) {
						String optionData = split[l].trim();
					if(optionData.equals(udfObj)) {
						break;
					}else if (l==split.length-1) {
						xrefForm.setUdf4(null);
					}
				}
				}
		}else if(iUdf.getSequence()== 5) { 
					String udfObj= xrefForm.getUdf5();
					if(!(udfObj == null || udfObj.trim().equals(""))){
					for(int l=0;l<split.length;l++) {
						String optionData = split[l].trim();
					if(optionData.equals(udfObj)) {
						break;
					}else if (l==split.length-1) {
						xrefForm.setUdf5(null);
					}
				}
				}
		}else if(iUdf.getSequence()== 6) { 
					String udfObj= xrefForm.getUdf6();
					if(!(udfObj == null || udfObj.trim().equals(""))){
					for(int l=0;l<split.length;l++) {
						String optionData = split[l].trim();
					if(optionData.equals(udfObj)) {
						break;
					}else if (l==split.length-1) {
						xrefForm.setUdf6(null);
					}
				}
				}
		}else if(iUdf.getSequence()== 7) { 
					String udfObj= xrefForm.getUdf7();
					if(!(udfObj == null || udfObj.trim().equals(""))){
					for(int l=0;l<split.length;l++) {
						String optionData = split[l].trim();
					if(optionData.equals(udfObj)) {
						break;
					}else if (l==split.length-1) {
						xrefForm.setUdf7(null);
					}
				}
				}
		}else if(iUdf.getSequence()== 8) { 
					String udfObj= xrefForm.getUdf8();
					if(!(udfObj == null || udfObj.trim().equals(""))){
					for(int l=0;l<split.length;l++) {
						String optionData = split[l].trim();
					if(optionData.equals(udfObj)) {
						break;
					}else if (l==split.length-1) {
						xrefForm.setUdf8(null);
					}
				}
				}
		}else if(iUdf.getSequence()== 9) { 
					String udfObj= xrefForm.getUdf9();
					if(!(udfObj == null || udfObj.trim().equals(""))){
					for(int l=0;l<split.length;l++) {
						String optionData = split[l].trim();
					if(optionData.equals(udfObj)) {
						break;
					}else if (l==split.length-1) {
						xrefForm.setUdf9(null);
					}
				}
				}
		}else if(iUdf.getSequence()== 10) { 
					String udfObj= xrefForm.getUdf10();
					if(!(udfObj == null || udfObj.trim().equals(""))){
					for(int l=0;l<split.length;l++) {
						String optionData = split[l].trim();
					if(optionData.equals(udfObj)) {
						break;
					}else if (l==split.length-1) {
						xrefForm.setUdf10(null);
					}
				}
				}
		}else if(iUdf.getSequence()== 11) { 
					String udfObj= xrefForm.getUdf11();
					if(!(udfObj == null || udfObj.trim().equals(""))){
					for(int l=0;l<split.length;l++) {
						String optionData = split[l].trim();
					if(optionData.equals(udfObj)) {
						break;
					}else if (l==split.length-1) {
						xrefForm.setUdf11(null);
					}
				}
				}
		}else if(iUdf.getSequence()== 12) { 
					String udfObj= xrefForm.getUdf12();
					if(!(udfObj == null || udfObj.trim().equals(""))){
					for(int l=0;l<split.length;l++) {
						String optionData = split[l].trim();
					if(optionData.equals(udfObj)) {
						break;
					}else if (l==split.length-1) {
						xrefForm.setUdf12(null);
					}
				}
				}
		}else if(iUdf.getSequence()== 13) { 
					String udfObj= xrefForm.getUdf13();
					if(!(udfObj == null || udfObj.trim().equals(""))){
					for(int l=0;l<split.length;l++) {
						String optionData = split[l].trim();
					if(optionData.equals(udfObj)) {
						break;
					}else if (l==split.length-1) {
						xrefForm.setUdf13(null);
					}
				}
				}
		}else if(iUdf.getSequence()== 14) { 
					String udfObj= xrefForm.getUdf14();
					if(!(udfObj == null || udfObj.trim().equals(""))){
					for(int l=0;l<split.length;l++) {
						String optionData = split[l].trim();
					if(optionData.equals(udfObj)) {
						break;
					}else if (l==split.length-1) {
						xrefForm.setUdf14(null);
					}
				}
				}
		}else if(iUdf.getSequence()== 15) { 
					String udfObj= xrefForm.getUdf15();
					if(!(udfObj == null || udfObj.trim().equals(""))){
					for(int l=0;l<split.length;l++) {
						String optionData = split[l].trim();
					if(optionData.equals(udfObj)) {
						break;
					}else if (l==split.length-1) {
						xrefForm.setUdf15(null);
					}
				}
				}
		}else if(iUdf.getSequence()== 16) { 
			String udfObj= xrefForm.getUdf16();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
				break;
			}else if (l==split.length-1) {
				xrefForm.setUdf16(null);
			}
		}
		}
}else if(iUdf.getSequence()== 17) { 
			String udfObj= xrefForm.getUdf17();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
				break;
			}else if (l==split.length-1) {
				xrefForm.setUdf17(null);
			}
		}
		}
}else if(iUdf.getSequence()== 18) { 
			String udfObj= xrefForm.getUdf18();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
				break;
			}else if (l==split.length-1) {
				xrefForm.setUdf18(null);
			}
		}
		}
}else if(iUdf.getSequence()== 19) { 
			String udfObj= xrefForm.getUdf19();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
				break;
			}else if (l==split.length-1) {
				xrefForm.setUdf19(null);
			}
		}
		}
}else if(iUdf.getSequence()== 20) { 
			String udfObj= xrefForm.getUdf20();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
				break;
			}else if (l==split.length-1) {
				xrefForm.setUdf20(null);
			}
		}
		}
}else if(iUdf.getSequence()== 21) { 
			String udfObj= xrefForm.getUdf21();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
				break;
			}else if (l==split.length-1) {
				xrefForm.setUdf21(null);
			}
		}
		}
}		else if(iUdf.getSequence()== 22) { 
	String udfObj= xrefForm.getUdf22();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
		String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
		break;
	}else if (l==split.length-1) {
		xrefForm.setUdf22(null);
	}
}
}
}else if(iUdf.getSequence()== 23) { 
	String udfObj= xrefForm.getUdf23();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
		String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
		break;
	}else if (l==split.length-1) {
		xrefForm.setUdf23(null);
	}
}
}
}else if(iUdf.getSequence()== 24) { 
	String udfObj= xrefForm.getUdf24();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
		String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
		break;
	}else if (l==split.length-1) {
		xrefForm.setUdf24(null);
	}
}
}
}else if(iUdf.getSequence()== 25) { 
	String udfObj= xrefForm.getUdf25();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
		String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
		break;
	}else if (l==split.length-1) {
		xrefForm.setUdf25(null);
	}
}
}
}else if(iUdf.getSequence()== 26) { 
String udfObj= xrefForm.getUdf26();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf26(null);
}
}
}
}else if(iUdf.getSequence()== 27) { 
String udfObj= xrefForm.getUdf27();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf27(null);
}
}
}
}else if(iUdf.getSequence()== 28) { 
String udfObj= xrefForm.getUdf28();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf28(null);
}
}
}
}else if(iUdf.getSequence()== 29) { 
String udfObj= xrefForm.getUdf29();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf29(null);
}
}
}
}else if(iUdf.getSequence()== 30) { 
String udfObj= xrefForm.getUdf30();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf30(null);
}
}
}
}else if(iUdf.getSequence()== 31) { 
	String udfObj= xrefForm.getUdf31();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
		String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
		break;
	}else if (l==split.length-1) {
		xrefForm.setUdf31(null);
	}
}
}
}		else if(iUdf.getSequence()== 32) { 
String udfObj= xrefForm.getUdf32();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf32(null);
}
}
}
}else if(iUdf.getSequence()== 33) { 
String udfObj= xrefForm.getUdf33();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf33(null);
}
}
}
}else if(iUdf.getSequence()== 34) { 
String udfObj= xrefForm.getUdf34();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf34(null);
}
}
}
}else if(iUdf.getSequence()== 35) { 
String udfObj= xrefForm.getUdf35();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf35(null);
}
}
}
}else if(iUdf.getSequence()== 36) { 
String udfObj= xrefForm.getUdf36();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf36(null);
}
}
}
}else if(iUdf.getSequence()== 37) { 
String udfObj= xrefForm.getUdf37();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf37(null);
}
}
}
}else if(iUdf.getSequence()== 38) { 
String udfObj= xrefForm.getUdf38();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf38(null);
}
}
}
}else if(iUdf.getSequence()== 39) { 
String udfObj= xrefForm.getUdf39();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf39(null);
}
}
}
}else if(iUdf.getSequence()== 40) { 
String udfObj= xrefForm.getUdf40();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf40(null);
}
}
}
}else if(iUdf.getSequence()== 41) { 
	String udfObj= xrefForm.getUdf41();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
		String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
		break;
	}else if (l==split.length-1) {
		xrefForm.setUdf41(null);
	}
}
}
}		else if(iUdf.getSequence()== 42) { 
String udfObj= xrefForm.getUdf42();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf42(null);
}
}
}
}else if(iUdf.getSequence()== 43) { 
String udfObj= xrefForm.getUdf43();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf43(null);
}
}
}
}else if(iUdf.getSequence()== 44) { 
String udfObj= xrefForm.getUdf44();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf44(null);
}
}
}
}else if(iUdf.getSequence()== 45) { 
String udfObj= xrefForm.getUdf45();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf45(null);
}
}
}
}else if(iUdf.getSequence()== 46) { 
String udfObj= xrefForm.getUdf46();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf46(null);
}
}
}
}else if(iUdf.getSequence()== 47) { 
String udfObj= xrefForm.getUdf47();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf47(null);
}
}
}
}else if(iUdf.getSequence()== 48) { 
String udfObj= xrefForm.getUdf48();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf48(null);
}
}
}
}else if(iUdf.getSequence()== 49) { 
String udfObj= xrefForm.getUdf49();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf49(null);
}
}
}
}else if(iUdf.getSequence()== 50) { 
String udfObj= xrefForm.getUdf50();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf50(null);
}
}
}
}else if(iUdf.getSequence()== 51) { 
	String udfObj= xrefForm.getUdf51();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
		String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
		break;
	}else if (l==split.length-1) {
		xrefForm.setUdf51(null);
	}
}
}
}		else if(iUdf.getSequence()== 52) { 
String udfObj= xrefForm.getUdf52();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf52(null);
}
}
}
}else if(iUdf.getSequence()== 53) { 
String udfObj= xrefForm.getUdf53();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf53(null);
}
}
}
}else if(iUdf.getSequence()== 54) { 
String udfObj= xrefForm.getUdf54();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf54(null);
}
}
}
}else if(iUdf.getSequence()== 55) { 
String udfObj= xrefForm.getUdf55();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf55(null);
}
}
}
}else if(iUdf.getSequence()== 56) { 
String udfObj= xrefForm.getUdf56();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf56(null);
}
}
}
}else if(iUdf.getSequence()== 57) { 
String udfObj= xrefForm.getUdf57();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf57(null);
}
}
}
}else if(iUdf.getSequence()== 58) { 
String udfObj= xrefForm.getUdf58();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf58(null);
}
}
}
}else if(iUdf.getSequence()== 59) { 
String udfObj= xrefForm.getUdf59();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf59(null);
}
}
}
}else if(iUdf.getSequence()== 60) { 
String udfObj= xrefForm.getUdf60();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf60(null);
}
}
}
}else if(iUdf.getSequence()== 61) { 
	String udfObj= xrefForm.getUdf61();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
		String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
		break;
	}else if (l==split.length-1) {
		xrefForm.setUdf61(null);
	}
}
}
}		else if(iUdf.getSequence()== 62) { 
String udfObj= xrefForm.getUdf62();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf62(null);
}
}
}
}else if(iUdf.getSequence()== 63) { 
String udfObj= xrefForm.getUdf63();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf63(null);
}
}
}
}else if(iUdf.getSequence()== 64) { 
String udfObj= xrefForm.getUdf64();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf64(null);
}
}
}
}else if(iUdf.getSequence()== 65) { 
String udfObj= xrefForm.getUdf65();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf65(null);
}
}
}
}else if(iUdf.getSequence()== 66) { 
String udfObj= xrefForm.getUdf66();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf66(null);
}
}
}
}else if(iUdf.getSequence()== 67) { 
String udfObj= xrefForm.getUdf67();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf67(null);
}
}
}
}else if(iUdf.getSequence()== 68) { 
String udfObj= xrefForm.getUdf68();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf68(null);
}
}
}
}else if(iUdf.getSequence()== 69) { 
String udfObj= xrefForm.getUdf69();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf69(null);
}
}
}
}else if(iUdf.getSequence()== 70) { 
String udfObj= xrefForm.getUdf70();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf70(null);
}
}
}
}else if(iUdf.getSequence()== 71) { 
	String udfObj= xrefForm.getUdf71();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
		String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
		break;
	}else if (l==split.length-1) {
		xrefForm.setUdf71(null);
	}
}
}
}		else if(iUdf.getSequence()== 72) { 
String udfObj= xrefForm.getUdf72();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf72(null);
}
}
}
}else if(iUdf.getSequence()== 73) { 
String udfObj= xrefForm.getUdf73();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf73(null);
}
}
}
}else if(iUdf.getSequence()== 74) { 
String udfObj= xrefForm.getUdf74();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf74(null);
}
}
}
}else if(iUdf.getSequence()== 75) { 
String udfObj= xrefForm.getUdf75();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf75(null);
}
}
}
}else if(iUdf.getSequence()== 76) { 
String udfObj= xrefForm.getUdf76();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf76(null);
}
}
}
}else if(iUdf.getSequence()== 77) { 
String udfObj= xrefForm.getUdf77();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf77(null);
}
}
}
}else if(iUdf.getSequence()== 78) { 
String udfObj= xrefForm.getUdf78();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf78(null);
}
}
}
}else if(iUdf.getSequence()== 79) { 
String udfObj= xrefForm.getUdf79();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf79(null);
}
}
}
}else if(iUdf.getSequence()== 80) { 
String udfObj= xrefForm.getUdf80();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf80(null);
}
}
}
}else if(iUdf.getSequence()== 81) { 
	String udfObj= xrefForm.getUdf81();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
		String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
		break;
	}else if (l==split.length-1) {
		xrefForm.setUdf81(null);
	}
}
}
}		else if(iUdf.getSequence()== 82) { 
String udfObj= xrefForm.getUdf82();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf82(null);
}
}
}
}else if(iUdf.getSequence()== 83) { 
String udfObj= xrefForm.getUdf83();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf83(null);
}
}
}
}else if(iUdf.getSequence()== 84) { 
String udfObj= xrefForm.getUdf84();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf84(null);
}
}
}
}else if(iUdf.getSequence()== 85) { 
String udfObj= xrefForm.getUdf85();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf85(null);
}
}
}
}else if(iUdf.getSequence()== 86) { 
String udfObj= xrefForm.getUdf86();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf86(null);
}
}
}
}else if(iUdf.getSequence()== 87) { 
String udfObj= xrefForm.getUdf87();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf87(null);
}
}
}
}else if(iUdf.getSequence()== 88) { 
String udfObj= xrefForm.getUdf88();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf88(null);
}
}
}
}else if(iUdf.getSequence()== 89) { 
String udfObj= xrefForm.getUdf89();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf89(null);
}
}
}
}else if(iUdf.getSequence()== 90) { 
String udfObj= xrefForm.getUdf90();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf90(null);
}
}
}
}else if(iUdf.getSequence()== 91) { 
	String udfObj= xrefForm.getUdf91();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
		String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
		break;
	}else if (l==split.length-1) {
		xrefForm.setUdf91(null);
	}
}
}
}		else if(iUdf.getSequence()== 92) { 
String udfObj= xrefForm.getUdf92();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf92(null);
}
}
}
}else if(iUdf.getSequence()== 93) { 
String udfObj= xrefForm.getUdf93();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf93(null);
}
}
}
}else if(iUdf.getSequence()== 94) { 
String udfObj= xrefForm.getUdf94();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf94(null);
}
}
}
}else if(iUdf.getSequence()== 95) { 
String udfObj= xrefForm.getUdf95();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf95(null);
}
}
}
}else if(iUdf.getSequence()== 96) { 
String udfObj= xrefForm.getUdf96();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf96(null);
}
}
}
}else if(iUdf.getSequence()== 97) { 
String udfObj= xrefForm.getUdf97();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf97(null);
}
}
}
}else if(iUdf.getSequence()== 98) { 
String udfObj= xrefForm.getUdf98();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf98(null);
}
}
}
}else if(iUdf.getSequence()== 99) { 
String udfObj= xrefForm.getUdf99();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf99(null);
}
}
}
}else if(iUdf.getSequence()== 100) { 
String udfObj= xrefForm.getUdf100();
if(!(udfObj == null || udfObj.trim().equals(""))){
for(int l=0;l<split.length;l++) {
String optionData = split[l].trim();
if(optionData.equals(udfObj)) {
break;
}else if (l==split.length-1) {
xrefForm.setUdf100(null);
}
}
}
}else if(iUdf.getSequence()== 101) { 
	String udfObj= xrefForm.getUdf101();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
	String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
	break;
	}else if (l==split.length-1) {
	xrefForm.setUdf101(null);
	}
	}
	}
}	else if(iUdf.getSequence()== 102) { 
	String udfObj= xrefForm.getUdf102();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
	String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
	break;
	}else if (l==split.length-1) {
	xrefForm.setUdf102(null);
	}
	}
	}
}else if(iUdf.getSequence()== 103) { 
	String udfObj= xrefForm.getUdf103();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
	String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
	break;
	}else if (l==split.length-1) {
	xrefForm.setUdf103(null);
	}
	}
	}
}	else if(iUdf.getSequence()== 104) { 
	String udfObj= xrefForm.getUdf104();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
	String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
	break;
	}else if (l==split.length-1) {
	xrefForm.setUdf104(null);
	}
	}
	}
}	else if(iUdf.getSequence()== 105) { 
	String udfObj= xrefForm.getUdf105();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
	String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
	break;
	}else if (l==split.length-1) {
	xrefForm.setUdf105(null);
	}
	}
	}
}	else if(iUdf.getSequence()== 106) { 
	String udfObj= xrefForm.getUdf106();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
	String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
	break;
	}else if (l==split.length-1) {
	xrefForm.setUdf106(null);
	}
	}
	}
}	else if(iUdf.getSequence()== 107) { 
	String udfObj= xrefForm.getUdf107();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
	String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
	break;
	}else if (l==split.length-1) {
	xrefForm.setUdf107(null);
	}
	}
	}
} else if(iUdf.getSequence()== 108) { 
	String udfObj= xrefForm.getUdf108();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
	String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
	break;
	}else if (l==split.length-1) {
	xrefForm.setUdf108(null);
	}
	}
	}
}	else if(iUdf.getSequence()== 109) { 
	String udfObj= xrefForm.getUdf109();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
	String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
	break;
	}else if (l==split.length-1) {
	xrefForm.setUdf109(null);
	}
	}
	}
}	else if(iUdf.getSequence()== 110) { 
	String udfObj= xrefForm.getUdf110();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
	String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
	break;
	}else if (l==split.length-1) {
	xrefForm.setUdf110(null);
	}
	}
	}
}	else if(iUdf.getSequence()== 111) { 
	String udfObj= xrefForm.getUdf111();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
	String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
	break;
	}else if (l==split.length-1) {
	xrefForm.setUdf111(null);
	}
	}
	}
}	else if(iUdf.getSequence()== 112) { 
	String udfObj= xrefForm.getUdf112();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
	String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
	break;
	}else if (l==split.length-1) {
	xrefForm.setUdf112(null);
	}
	}
	}
}	else if(iUdf.getSequence()== 113) { 
	String udfObj= xrefForm.getUdf113();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
	String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
	break;
	}else if (l==split.length-1) {
	xrefForm.setUdf113(null);
	}
	}
	}
}	else if(iUdf.getSequence()== 114) { 
	String udfObj= xrefForm.getUdf114();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
	String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
	break;
	}else if (l==split.length-1) {
	xrefForm.setUdf114(null);
	}
	}
	}
}	else if(iUdf.getSequence()== 115) { 
	String udfObj= xrefForm.getUdf115();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
	String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
	break;
	}else if (l==split.length-1) {
	xrefForm.setUdf115(null);
	}
	}
	}
}		
								
else if(iUdf.getSequence()== 116) { 
	String udfObj= xrefForm.getUdf116();
	if(!(udfObj == null || udfObj.trim().equals(""))){
	for(int l=0;l<split.length;l++) {
	String optionData = split[l].trim();
	if(optionData.equals(udfObj)) {
	break;
	}else if (l==split.length-1) {
	xrefForm.setUdf116(null);
	}
	}
	}
}		
						
					}
						}
					}
					}
					
					int count=0;
					for(String udfSelected : udfAllowedList) {
						
						switch (Integer.parseInt(udfSelected)) {
						case 1:
							if( xrefForm.getUdf1()==null||xrefForm.getUdf1().trim().equals("")){ count++; } break;
							case 2:
							if( xrefForm.getUdf2()==null||xrefForm.getUdf2().trim().equals("")){ count++; } break;
							case 3:
							if( xrefForm.getUdf3()==null||xrefForm.getUdf3().trim().equals("")){ count++; } break;
							case 4:
							if( xrefForm.getUdf4()==null||xrefForm.getUdf4().trim().equals("")){ count++; } break;
							case 5:
							if( xrefForm.getUdf5()==null||xrefForm.getUdf5().trim().equals("")){ count++; } break;
							case 6:
							if( xrefForm.getUdf6()==null||xrefForm.getUdf6().trim().equals("")){ count++; } break;
							case 7:
							if( xrefForm.getUdf7()==null||xrefForm.getUdf7().trim().equals("")){ count++; } break;
							case 8:
							if( xrefForm.getUdf8()==null||xrefForm.getUdf8().trim().equals("")){ count++; } break;
							case 9:
							if( xrefForm.getUdf9()==null||xrefForm.getUdf9().trim().equals("")){ count++; } break;
							case 10:
							if( xrefForm.getUdf10()==null||xrefForm.getUdf10().trim().equals("")){ count++; } break;
							case 11:
							if( xrefForm.getUdf11()==null||xrefForm.getUdf11().trim().equals("")){ count++; } break;
							case 12:
							if( xrefForm.getUdf12()==null||xrefForm.getUdf12().trim().equals("")){ count++; } break;
							case 13:
							if( xrefForm.getUdf13()==null||xrefForm.getUdf13().trim().equals("")){ count++; } break;
							case 14:
							if( xrefForm.getUdf14()==null||xrefForm.getUdf14().trim().equals("")){ count++; } break;
							case 15:
							if( xrefForm.getUdf15()==null||xrefForm.getUdf15().trim().equals("")){ count++; } break;
							case 16:
							if( xrefForm.getUdf16()==null||xrefForm.getUdf16().trim().equals("")){ count++; } break;
							case 17:
							if( xrefForm.getUdf17()==null||xrefForm.getUdf17().trim().equals("")){ count++; } break;
							case 18:
							if( xrefForm.getUdf18()==null||xrefForm.getUdf18().trim().equals("")){ count++; } break;
							case 19:
							if( xrefForm.getUdf19()==null||xrefForm.getUdf19().trim().equals("")){ count++; } break;
							case 20:
							if( xrefForm.getUdf20()==null||xrefForm.getUdf20().trim().equals("")){ count++; } break;
							case 21:
							if( xrefForm.getUdf21()==null||xrefForm.getUdf21().trim().equals("")){ count++; } break;
							case 22:
							if( xrefForm.getUdf22()==null||xrefForm.getUdf22().trim().equals("")){ count++; } break;
							case 23:
							if( xrefForm.getUdf23()==null||xrefForm.getUdf23().trim().equals("")){ count++; } break;
							case 24:
							if( xrefForm.getUdf24()==null||xrefForm.getUdf24().trim().equals("")){ count++; } break;
							case 25:
							if( xrefForm.getUdf25()==null||xrefForm.getUdf25().trim().equals("")){ count++; } break;
							case 26:
							if( xrefForm.getUdf26()==null||xrefForm.getUdf26().trim().equals("")){ count++; } break;
							case 27:
							if( xrefForm.getUdf27()==null||xrefForm.getUdf27().trim().equals("")){ count++; } break;
							case 28:
							if( xrefForm.getUdf28()==null||xrefForm.getUdf28().trim().equals("")){ count++; } break;
							case 29:
							if( xrefForm.getUdf29()==null||xrefForm.getUdf29().trim().equals("")){ count++; } break;
							case 30:
							if( xrefForm.getUdf30()==null||xrefForm.getUdf30().trim().equals("")){ count++; } break;
							case 31:
							if( xrefForm.getUdf31()==null||xrefForm.getUdf31().trim().equals("")){ count++; } break;
							case 32:
							if( xrefForm.getUdf32()==null||xrefForm.getUdf32().trim().equals("")){ count++; } break;
							case 33:
							if( xrefForm.getUdf33()==null||xrefForm.getUdf33().trim().equals("")){ count++; } break;
							case 34:
							if( xrefForm.getUdf34()==null||xrefForm.getUdf34().trim().equals("")){ count++; } break;
							case 35:
							if( xrefForm.getUdf35()==null||xrefForm.getUdf35().trim().equals("")){ count++; } break;
							case 36:
							if( xrefForm.getUdf36()==null||xrefForm.getUdf36().trim().equals("")){ count++; } break;
							case 37:
							if( xrefForm.getUdf37()==null||xrefForm.getUdf37().trim().equals("")){ count++; } break;
							case 38:
							if( xrefForm.getUdf38()==null||xrefForm.getUdf38().trim().equals("")){ count++; } break;
							case 39:
							if( xrefForm.getUdf39()==null||xrefForm.getUdf39().trim().equals("")){ count++; } break;
							case 40:
							if( xrefForm.getUdf40()==null||xrefForm.getUdf40().trim().equals("")){ count++; } break;
							case 41:
							if( xrefForm.getUdf41()==null||xrefForm.getUdf41().trim().equals("")){ count++; } break;
							case 42:
							if( xrefForm.getUdf42()==null||xrefForm.getUdf42().trim().equals("")){ count++; } break;
							case 43:
							if( xrefForm.getUdf43()==null||xrefForm.getUdf43().trim().equals("")){ count++; } break;
							case 44:
							if( xrefForm.getUdf44()==null||xrefForm.getUdf44().trim().equals("")){ count++; } break;
							case 45:
							if( xrefForm.getUdf45()==null||xrefForm.getUdf45().trim().equals("")){ count++; } break;
							case 46:
							if( xrefForm.getUdf46()==null||xrefForm.getUdf46().trim().equals("")){ count++; } break;
							case 47:
							if( xrefForm.getUdf47()==null||xrefForm.getUdf47().trim().equals("")){ count++; } break;
							case 48:
							if( xrefForm.getUdf48()==null||xrefForm.getUdf48().trim().equals("")){ count++; } break;
							case 49:
							if( xrefForm.getUdf49()==null||xrefForm.getUdf49().trim().equals("")){ count++; } break;
							case 50:
							if( xrefForm.getUdf50()==null||xrefForm.getUdf50().trim().equals("")){ count++; } break;
							case 51:if( xrefForm.getUdf51()==null||xrefForm.getUdf51().trim().equals("")){ count++; } break;
							case 52:if( xrefForm.getUdf52()==null||xrefForm.getUdf52().trim().equals("")){ count++; } break;
							case 53:if( xrefForm.getUdf53()==null||xrefForm.getUdf53().trim().equals("")){ count++; } break;
							case 54:if( xrefForm.getUdf54()==null||xrefForm.getUdf54().trim().equals("")){ count++; } break;
							case 55:if( xrefForm.getUdf55()==null||xrefForm.getUdf55().trim().equals("")){ count++; } break;
							case 56:if( xrefForm.getUdf56()==null||xrefForm.getUdf56().trim().equals("")){ count++; } break;
							case 57:if( xrefForm.getUdf57()==null||xrefForm.getUdf57().trim().equals("")){ count++; } break;
							case 58:if( xrefForm.getUdf58()==null||xrefForm.getUdf58().trim().equals("")){ count++; } break;
							case 59:if( xrefForm.getUdf59()==null||xrefForm.getUdf59().trim().equals("")){ count++; } break;
							case 60:if( xrefForm.getUdf60()==null||xrefForm.getUdf60().trim().equals("")){ count++; } break;
							case 61:if( xrefForm.getUdf61()==null||xrefForm.getUdf61().trim().equals("")){ count++; } break;
							case 62:if( xrefForm.getUdf62()==null||xrefForm.getUdf62().trim().equals("")){ count++; } break;
							case 63:if( xrefForm.getUdf63()==null||xrefForm.getUdf63().trim().equals("")){ count++; } break;
							case 64:if( xrefForm.getUdf64()==null||xrefForm.getUdf64().trim().equals("")){ count++; } break;
							case 65:if( xrefForm.getUdf65()==null||xrefForm.getUdf65().trim().equals("")){ count++; } break;
							case 66:if( xrefForm.getUdf66()==null||xrefForm.getUdf66().trim().equals("")){ count++; } break;
							case 67:if( xrefForm.getUdf67()==null||xrefForm.getUdf67().trim().equals("")){ count++; } break;
							case 68:if( xrefForm.getUdf68()==null||xrefForm.getUdf68().trim().equals("")){ count++; } break;
							case 69:if( xrefForm.getUdf69()==null||xrefForm.getUdf69().trim().equals("")){ count++; } break;
							case 70:if( xrefForm.getUdf70()==null||xrefForm.getUdf70().trim().equals("")){ count++; } break;
							case 71:if( xrefForm.getUdf71()==null||xrefForm.getUdf71().trim().equals("")){ count++; } break;
							case 72:if( xrefForm.getUdf72()==null||xrefForm.getUdf72().trim().equals("")){ count++; } break;
							case 73:if( xrefForm.getUdf73()==null||xrefForm.getUdf73().trim().equals("")){ count++; } break;
							case 74:if( xrefForm.getUdf74()==null||xrefForm.getUdf74().trim().equals("")){ count++; } break;
							case 75:if( xrefForm.getUdf75()==null||xrefForm.getUdf75().trim().equals("")){ count++; } break;
							case 76:if( xrefForm.getUdf76()==null||xrefForm.getUdf76().trim().equals("")){ count++; } break;
							case 77:if( xrefForm.getUdf77()==null||xrefForm.getUdf77().trim().equals("")){ count++; } break;
							case 78:if( xrefForm.getUdf78()==null||xrefForm.getUdf78().trim().equals("")){ count++; } break;
							case 79:if( xrefForm.getUdf79()==null||xrefForm.getUdf79().trim().equals("")){ count++; } break;
							case 80:if( xrefForm.getUdf80()==null||xrefForm.getUdf80().trim().equals("")){ count++; } break;
							case 81:if( xrefForm.getUdf81()==null||xrefForm.getUdf81().trim().equals("")){ count++; } break;
							case 82:if( xrefForm.getUdf82()==null||xrefForm.getUdf82().trim().equals("")){ count++; } break;
							case 83:if( xrefForm.getUdf83()==null||xrefForm.getUdf83().trim().equals("")){ count++; } break;
							case 84:if( xrefForm.getUdf84()==null||xrefForm.getUdf84().trim().equals("")){ count++; } break;
							case 85:if( xrefForm.getUdf85()==null||xrefForm.getUdf85().trim().equals("")){ count++; } break;
							case 86:if( xrefForm.getUdf86()==null||xrefForm.getUdf86().trim().equals("")){ count++; } break;
							case 87:if( xrefForm.getUdf87()==null||xrefForm.getUdf87().trim().equals("")){ count++; } break;
							case 88:if( xrefForm.getUdf88()==null||xrefForm.getUdf88().trim().equals("")){ count++; } break;
							case 89:if( xrefForm.getUdf89()==null||xrefForm.getUdf89().trim().equals("")){ count++; } break;
							case 90:if( xrefForm.getUdf90()==null||xrefForm.getUdf90().trim().equals("")){ count++; } break;
							case 91:if( xrefForm.getUdf91()==null||xrefForm.getUdf91().trim().equals("")){ count++; } break;
							case 92:if( xrefForm.getUdf92()==null||xrefForm.getUdf92().trim().equals("")){ count++; } break;
							case 93:if( xrefForm.getUdf93()==null||xrefForm.getUdf93().trim().equals("")){ count++; } break;
							case 94:if( xrefForm.getUdf94()==null||xrefForm.getUdf94().trim().equals("")){ count++; } break;
							case 95:if( xrefForm.getUdf95()==null||xrefForm.getUdf95().trim().equals("")){ count++; } break;
							case 96:if( xrefForm.getUdf96()==null||xrefForm.getUdf96().trim().equals("")){ count++; } break;
							case 97:if( xrefForm.getUdf97()==null||xrefForm.getUdf97().trim().equals("")){ count++; } break;
							case 98:if( xrefForm.getUdf98()==null||xrefForm.getUdf98().trim().equals("")){ count++; } break;
							case 99:if( xrefForm.getUdf99()==null||xrefForm.getUdf99().trim().equals("")){ count++; } break;
							case 100:if( xrefForm.getUdf100()==null||xrefForm.getUdf100().trim().equals("")){ count++; } break;
							
							case 101:if( xrefForm.getUdf101()==null||xrefForm.getUdf101().trim().equals("")){ count++; } break;
							case 102:if( xrefForm.getUdf102()==null||xrefForm.getUdf102().trim().equals("")){ count++; } break;
							case 103:if( xrefForm.getUdf103()==null||xrefForm.getUdf103().trim().equals("")){ count++; } break;
							case 104:if( xrefForm.getUdf104()==null||xrefForm.getUdf104().trim().equals("")){ count++; } break;
							case 105:if( xrefForm.getUdf105()==null||xrefForm.getUdf105().trim().equals("")){ count++; } break;
							case 106:if( xrefForm.getUdf106()==null||xrefForm.getUdf106().trim().equals("")){ count++; } break;
							case 107:if( xrefForm.getUdf107()==null||xrefForm.getUdf107().trim().equals("")){ count++; } break;
							case 108:if( xrefForm.getUdf108()==null||xrefForm.getUdf108().trim().equals("")){ count++; } break;
							case 109:if( xrefForm.getUdf109()==null||xrefForm.getUdf109().trim().equals("")){ count++; } break;
							case 110:if( xrefForm.getUdf110()==null||xrefForm.getUdf110().trim().equals("")){ count++; } break;
							case 111:if( xrefForm.getUdf111()==null||xrefForm.getUdf111().trim().equals("")){ count++; } break;
							case 112:if( xrefForm.getUdf112()==null||xrefForm.getUdf112().trim().equals("")){ count++; } break;
							case 113:if( xrefForm.getUdf113()==null||xrefForm.getUdf113().trim().equals("")){ count++; } break;
							case 114:if( xrefForm.getUdf114()==null||xrefForm.getUdf114().trim().equals("")){ count++; } break;
							case 115:if( xrefForm.getUdf115()==null||xrefForm.getUdf115().trim().equals("")){ count++; } break;
						
							
							default:
								break;
						}
					}
					if(count>0) {
						errors.add("1udfError", new ActionMessage("error.string.nonmandatory.XRefUdf" , ""));
					}
				}
				if(udfMandatoryList!=null){
					for(int udf=0;udf<udfMandatoryList.size();udf++){
						IUdf iUdf=(IUdf)udfMandatoryList.get(udf);
						switch (iUdf.getSequence()) {
							case 1:
							if( xrefForm.getUdf1()==null||xrefForm.getUdf1().trim().equals("")){ errors.add("1udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 2:
							if( xrefForm.getUdf2()==null||xrefForm.getUdf2().trim().equals("")){ errors.add("2udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 3:
							if( xrefForm.getUdf3()==null||xrefForm.getUdf3().trim().equals("")){ errors.add("3udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 4:
							if( xrefForm.getUdf4()==null||xrefForm.getUdf4().trim().equals("")){ errors.add("4udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 5:
							if( xrefForm.getUdf5()==null||xrefForm.getUdf5().trim().equals("")){ errors.add("5udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 6:
							if( xrefForm.getUdf6()==null||xrefForm.getUdf6().trim().equals("")){ errors.add("6udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 7:
							if( xrefForm.getUdf7()==null||xrefForm.getUdf7().trim().equals("")){ errors.add("7udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 8:
							if( xrefForm.getUdf8()==null||xrefForm.getUdf8().trim().equals("")){ errors.add("8udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 9:
							if( xrefForm.getUdf9()==null||xrefForm.getUdf9().trim().equals("")){ errors.add("9udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 10:
							if( xrefForm.getUdf10()==null||xrefForm.getUdf10().trim().equals("")){ errors.add("10udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 11:
							if( xrefForm.getUdf11()==null||xrefForm.getUdf11().trim().equals("")){ errors.add("11udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 12:
							if( xrefForm.getUdf12()==null||xrefForm.getUdf12().trim().equals("")){ errors.add("12udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 13:
							if( xrefForm.getUdf13()==null||xrefForm.getUdf13().trim().equals("")){ errors.add("13udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 14:
							if( xrefForm.getUdf14()==null||xrefForm.getUdf14().trim().equals("")){ errors.add("14udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 15:
							if( xrefForm.getUdf15()==null||xrefForm.getUdf15().trim().equals("")){ errors.add("15udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 16:
							if( xrefForm.getUdf16()==null||xrefForm.getUdf16().trim().equals("")){ errors.add("16udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 17:
							if( xrefForm.getUdf17()==null||xrefForm.getUdf17().trim().equals("")){ errors.add("17udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 18:
							if( xrefForm.getUdf18()==null||xrefForm.getUdf18().trim().equals("")){ errors.add("18udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 19:
							if( xrefForm.getUdf19()==null||xrefForm.getUdf19().trim().equals("")){ errors.add("19udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 20:
							if( xrefForm.getUdf20()==null||xrefForm.getUdf20().trim().equals("")){ errors.add("20udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 21:
							if( xrefForm.getUdf21()==null||xrefForm.getUdf21().trim().equals("")){ errors.add("21udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 22:
							if( xrefForm.getUdf22()==null||xrefForm.getUdf22().trim().equals("")){ errors.add("22udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 23:
							if( xrefForm.getUdf23()==null||xrefForm.getUdf23().trim().equals("")){ errors.add("23udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 24:
							if( xrefForm.getUdf24()==null||xrefForm.getUdf24().trim().equals("")){ errors.add("24udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 25:
							if( xrefForm.getUdf25()==null||xrefForm.getUdf25().trim().equals("")){ errors.add("25udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 26:
							if( xrefForm.getUdf26()==null||xrefForm.getUdf26().trim().equals("")){ errors.add("26udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 27:
							if( xrefForm.getUdf27()==null||xrefForm.getUdf27().trim().equals("")){ errors.add("27udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 28:
							if( xrefForm.getUdf28()==null||xrefForm.getUdf28().trim().equals("")){ errors.add("28udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 29:
							if( xrefForm.getUdf29()==null||xrefForm.getUdf29().trim().equals("")){ errors.add("29udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 30:
							if( xrefForm.getUdf30()==null||xrefForm.getUdf30().trim().equals("")){ errors.add("30udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 31:
							if( xrefForm.getUdf31()==null||xrefForm.getUdf31().trim().equals("")){ errors.add("31udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 32:
							if( xrefForm.getUdf32()==null||xrefForm.getUdf32().trim().equals("")){ errors.add("32udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 33:
							if( xrefForm.getUdf33()==null||xrefForm.getUdf33().trim().equals("")){ errors.add("33udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 34:
							if( xrefForm.getUdf34()==null||xrefForm.getUdf34().trim().equals("")){ errors.add("34udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 35:
							if( xrefForm.getUdf35()==null||xrefForm.getUdf35().trim().equals("")){ errors.add("35udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 36:
							if( xrefForm.getUdf36()==null||xrefForm.getUdf36().trim().equals("")){ errors.add("36udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 37:
							if( xrefForm.getUdf37()==null||xrefForm.getUdf37().trim().equals("")){ errors.add("37udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 38:
							if( xrefForm.getUdf38()==null||xrefForm.getUdf38().trim().equals("")){ errors.add("38udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 39:
							if( xrefForm.getUdf39()==null||xrefForm.getUdf39().trim().equals("")){ errors.add("39udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 40:
							if( xrefForm.getUdf40()==null||xrefForm.getUdf40().trim().equals("")){ errors.add("40udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 41:
							if( xrefForm.getUdf41()==null||xrefForm.getUdf41().trim().equals("")){ errors.add("41udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 42:
							if( xrefForm.getUdf42()==null||xrefForm.getUdf42().trim().equals("")){ errors.add("42udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 43:
							if( xrefForm.getUdf43()==null||xrefForm.getUdf43().trim().equals("")){ errors.add("43udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 44:
							if( xrefForm.getUdf44()==null||xrefForm.getUdf44().trim().equals("")){ errors.add("44udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 45:
							if( xrefForm.getUdf45()==null||xrefForm.getUdf45().trim().equals("")){ errors.add("45udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 46:
							if( xrefForm.getUdf46()==null||xrefForm.getUdf46().trim().equals("")){ errors.add("46udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 47:
							if( xrefForm.getUdf47()==null||xrefForm.getUdf47().trim().equals("")){ errors.add("47udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 48:
							if( xrefForm.getUdf48()==null||xrefForm.getUdf48().trim().equals("")){ errors.add("48udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 49:
							if( xrefForm.getUdf49()==null||xrefForm.getUdf49().trim().equals("")){ errors.add("49udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 50:
							if( xrefForm.getUdf50()==null||xrefForm.getUdf50().trim().equals("")){ errors.add("50udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 51:if( xrefForm.getUdf51()==null||xrefForm.getUdf51().trim().equals("")){ errors.add("51udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 52:if( xrefForm.getUdf52()==null||xrefForm.getUdf52().trim().equals("")){ errors.add("52udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 53:if( xrefForm.getUdf53()==null||xrefForm.getUdf53().trim().equals("")){ errors.add("53udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 54:if( xrefForm.getUdf54()==null||xrefForm.getUdf54().trim().equals("")){ errors.add("54udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 55:if( xrefForm.getUdf55()==null||xrefForm.getUdf55().trim().equals("")){ errors.add("55udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 56:if( xrefForm.getUdf56()==null||xrefForm.getUdf56().trim().equals("")){ errors.add("56udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 57:if( xrefForm.getUdf57()==null||xrefForm.getUdf57().trim().equals("")){ errors.add("57udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 58:if( xrefForm.getUdf58()==null||xrefForm.getUdf58().trim().equals("")){ errors.add("58udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 59:if( xrefForm.getUdf59()==null||xrefForm.getUdf59().trim().equals("")){ errors.add("59udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 60:if( xrefForm.getUdf60()==null||xrefForm.getUdf60().trim().equals("")){ errors.add("60udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 61:if( xrefForm.getUdf61()==null||xrefForm.getUdf61().trim().equals("")){ errors.add("61udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 62:if( xrefForm.getUdf62()==null||xrefForm.getUdf62().trim().equals("")){ errors.add("62udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 63:if( xrefForm.getUdf63()==null||xrefForm.getUdf63().trim().equals("")){ errors.add("63udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 64:if( xrefForm.getUdf64()==null||xrefForm.getUdf64().trim().equals("")){ errors.add("64udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 65:if( xrefForm.getUdf65()==null||xrefForm.getUdf65().trim().equals("")){ errors.add("65udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 66:if( xrefForm.getUdf66()==null||xrefForm.getUdf66().trim().equals("")){ errors.add("66udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 67:if( xrefForm.getUdf67()==null||xrefForm.getUdf67().trim().equals("")){ errors.add("67udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 68:if( xrefForm.getUdf68()==null||xrefForm.getUdf68().trim().equals("")){ errors.add("68udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 69:if( xrefForm.getUdf69()==null||xrefForm.getUdf69().trim().equals("")){ errors.add("69udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 70:if( xrefForm.getUdf70()==null||xrefForm.getUdf70().trim().equals("")){ errors.add("70udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 71:if( xrefForm.getUdf71()==null||xrefForm.getUdf71().trim().equals("")){ errors.add("71udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 72:if( xrefForm.getUdf72()==null||xrefForm.getUdf72().trim().equals("")){ errors.add("72udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 73:if( xrefForm.getUdf73()==null||xrefForm.getUdf73().trim().equals("")){ errors.add("73udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 74:if( xrefForm.getUdf74()==null||xrefForm.getUdf74().trim().equals("")){ errors.add("74udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 75:if( xrefForm.getUdf75()==null||xrefForm.getUdf75().trim().equals("")){ errors.add("75udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 76:if( xrefForm.getUdf76()==null||xrefForm.getUdf76().trim().equals("")){ errors.add("76udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 77:if( xrefForm.getUdf77()==null||xrefForm.getUdf77().trim().equals("")){ errors.add("77udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 78:if( xrefForm.getUdf78()==null||xrefForm.getUdf78().trim().equals("")){ errors.add("78udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 79:if( xrefForm.getUdf79()==null||xrefForm.getUdf79().trim().equals("")){ errors.add("79udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 80:if( xrefForm.getUdf80()==null||xrefForm.getUdf80().trim().equals("")){ errors.add("80udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 81:if( xrefForm.getUdf81()==null||xrefForm.getUdf81().trim().equals("")){ errors.add("81udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 82:if( xrefForm.getUdf82()==null||xrefForm.getUdf82().trim().equals("")){ errors.add("82udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 83:if( xrefForm.getUdf83()==null||xrefForm.getUdf83().trim().equals("")){ errors.add("83udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 84:if( xrefForm.getUdf84()==null||xrefForm.getUdf84().trim().equals("")){ errors.add("84udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 85:if( xrefForm.getUdf85()==null||xrefForm.getUdf85().trim().equals("")){ errors.add("85udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 86:if( xrefForm.getUdf86()==null||xrefForm.getUdf86().trim().equals("")){ errors.add("86udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 87:if( xrefForm.getUdf87()==null||xrefForm.getUdf87().trim().equals("")){ errors.add("87udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 88:if( xrefForm.getUdf88()==null||xrefForm.getUdf88().trim().equals("")){ errors.add("88udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 89:if( xrefForm.getUdf89()==null||xrefForm.getUdf89().trim().equals("")){ errors.add("89udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 90:if( xrefForm.getUdf90()==null||xrefForm.getUdf90().trim().equals("")){ errors.add("90udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 91:if( xrefForm.getUdf91()==null||xrefForm.getUdf91().trim().equals("")){ errors.add("91udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 92:if( xrefForm.getUdf92()==null||xrefForm.getUdf92().trim().equals("")){ errors.add("92udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 93:if( xrefForm.getUdf93()==null||xrefForm.getUdf93().trim().equals("")){ errors.add("93udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 94:if( xrefForm.getUdf94()==null||xrefForm.getUdf94().trim().equals("")){ errors.add("94udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 95:if( xrefForm.getUdf95()==null||xrefForm.getUdf95().trim().equals("")){ errors.add("95udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 96:if( xrefForm.getUdf96()==null||xrefForm.getUdf96().trim().equals("")){ errors.add("96udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 97:if( xrefForm.getUdf97()==null||xrefForm.getUdf97().trim().equals("")){ errors.add("97udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 98:if( xrefForm.getUdf98()==null||xrefForm.getUdf98().trim().equals("")){ errors.add("98udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 99:if( xrefForm.getUdf99()==null||xrefForm.getUdf99().trim().equals("")){ errors.add("99udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 100:if( xrefForm.getUdf100()==null||xrefForm.getUdf100().trim().equals("")){ errors.add("100udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							
							case 101:if( xrefForm.getUdf101()==null||xrefForm.getUdf101().trim().equals("")){ errors.add("101udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 102:if( xrefForm.getUdf102()==null||xrefForm.getUdf102().trim().equals("")){ errors.add("102udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 103:if( xrefForm.getUdf103()==null||xrefForm.getUdf103().trim().equals("")){ errors.add("103udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 104:if( xrefForm.getUdf104()==null||xrefForm.getUdf104().trim().equals("")){ errors.add("104udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 105:if( xrefForm.getUdf105()==null||xrefForm.getUdf105().trim().equals("")){ errors.add("105udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 106:if( xrefForm.getUdf106()==null||xrefForm.getUdf106().trim().equals("")){ errors.add("106udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 107:if( xrefForm.getUdf107()==null||xrefForm.getUdf107().trim().equals("")){ errors.add("107udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 108:if( xrefForm.getUdf108()==null||xrefForm.getUdf108().trim().equals("")){ errors.add("108udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							
							case 109:if( xrefForm.getUdf109()==null||xrefForm.getUdf109().trim().equals("")){ errors.add("109udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 110:if( xrefForm.getUdf110()==null||xrefForm.getUdf110().trim().equals("")){ errors.add("110udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 111:if( xrefForm.getUdf111()==null||xrefForm.getUdf111().trim().equals("")){ errors.add("111udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 112:if( xrefForm.getUdf112()==null||xrefForm.getUdf112().trim().equals("")){ errors.add("112udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 113:if( xrefForm.getUdf113()==null||xrefForm.getUdf113().trim().equals("")){ errors.add("113udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 114:if( xrefForm.getUdf114()==null||xrefForm.getUdf114().trim().equals("")){ errors.add("114udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							case 115:if( xrefForm.getUdf115()==null||xrefForm.getUdf115().trim().equals("")){ errors.add("115udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
							
							default:
							break;
						}
					}
				}
				
				if(udfNumericList!=null){
					for(int udf=0;udf<udfNumericList.size();udf++){
						IUdf iUdf=(IUdf)udfNumericList.get(udf);
						switch (iUdf.getSequence()) {
							case 1:
							if(! (xrefForm.getUdf1()==null||xrefForm.getUdf1().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf1().toString().trim(),false,0,999999999999999.D))){ errors.add("1udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 2:															            	     
							if(!( xrefForm.getUdf2()==null||xrefForm.getUdf2().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf2().toString().trim(),false,0,999999999999999.D))){ errors.add("2udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 3:															            	     
							if(!( xrefForm.getUdf3()==null||xrefForm.getUdf3().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf3().toString().trim(),false,0,999999999999999.D))){ errors.add("3udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;	
							case 4:															            	     
							if(!( xrefForm.getUdf4()==null||xrefForm.getUdf4().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf4().toString().trim(),false,0,999999999999999.D))){ errors.add("4udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 5:																			            
							if(!( xrefForm.getUdf5()==null||xrefForm.getUdf5().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf5().toString().trim(),false,0,999999999999999.D))){ errors.add("5udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 6:																			            
							if(!( xrefForm.getUdf6()==null||xrefForm.getUdf6().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf6().toString().trim(),false,0,999999999999999.D))){ errors.add("6udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 7:																			            
							if(!( xrefForm.getUdf7()==null||xrefForm.getUdf7().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf7().toString().trim(),false,0,999999999999999.D))){ errors.add("7udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 8:																			            
							if(!( xrefForm.getUdf8()==null||xrefForm.getUdf8().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf8().toString().trim(),false,0,999999999999999.D))){ errors.add("8udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 9:																			            
							if(!( xrefForm.getUdf9()==null||xrefForm.getUdf9().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf9().toString().trim(),false,0,999999999999999.D))){ errors.add("9udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 10:
							if(!( xrefForm.getUdf10()==null||xrefForm.getUdf10().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf10().toString().trim(),false,0,999999999999999.D))){ errors.add("10udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 11:																				               
							if(!( xrefForm.getUdf11()==null||xrefForm.getUdf11().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf11().toString().trim(),false,0,999999999999999.D))){ errors.add("11udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 12:																				               
							if(!( xrefForm.getUdf12()==null||xrefForm.getUdf12().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf12().toString().trim(),false,0,999999999999999.D))){ errors.add("12udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 13:																				               
							if(!( xrefForm.getUdf13()==null||xrefForm.getUdf13().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf13().toString().trim(),false,0,999999999999999.D))){ errors.add("13udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 14:																				               
							if(!( xrefForm.getUdf14()==null||xrefForm.getUdf14().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf14().toString().trim(),false,0,999999999999999.D))){ errors.add("14udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 15:																				               
							if(!( xrefForm.getUdf15()==null||xrefForm.getUdf15().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf15().toString().trim(),false,0,999999999999999.D))){ errors.add("15udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 16:																				               
							if(!( xrefForm.getUdf16()==null||xrefForm.getUdf16().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf16().toString().trim(),false,0,999999999999999.D))){ errors.add("16udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 17:																				               
							if(!( xrefForm.getUdf17()==null||xrefForm.getUdf17().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf17().toString().trim(),false,0,999999999999999.D))){ errors.add("17udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 18:																				               
							if(!( xrefForm.getUdf18()==null||xrefForm.getUdf18().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf18().toString().trim(),false,0,999999999999999.D))){ errors.add("18udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 19:																				               
							if(!( xrefForm.getUdf19()==null||xrefForm.getUdf19().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf19().toString().trim(),false,0,999999999999999.D))){ errors.add("19udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 20:																				               
							if(!( xrefForm.getUdf20()==null||xrefForm.getUdf20().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf20().toString().trim(),false,0,999999999999999.D))){ errors.add("20udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 21:																				               
							if(!( xrefForm.getUdf21()==null||xrefForm.getUdf21().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf21().toString().trim(),false,0,999999999999999.D))){ errors.add("21udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 22:																				               
							if(!( xrefForm.getUdf22()==null||xrefForm.getUdf22().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf22().toString().trim(),false,0,999999999999999.D))){ errors.add("22udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 23:																				               
							if(!( xrefForm.getUdf23()==null||xrefForm.getUdf23().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf23().toString().trim(),false,0,999999999999999.D))){ errors.add("23udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 24:																				               
							if(!( xrefForm.getUdf24()==null||xrefForm.getUdf24().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf24().toString().trim(),false,0,999999999999999.D))){ errors.add("24udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 25:																				               
							if(!( xrefForm.getUdf25()==null||xrefForm.getUdf25().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf25().toString().trim(),false,0,999999999999999.D))){ errors.add("25udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 26:																				               
							if(!( xrefForm.getUdf26()==null||xrefForm.getUdf26().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf26().toString().trim(),false,0,999999999999999.D))){ errors.add("26udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 27:																				               
							if(!( xrefForm.getUdf27()==null||xrefForm.getUdf27().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf27().toString().trim(),false,0,999999999999999.D))){ errors.add("27udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 28:																				               
							if(!( xrefForm.getUdf28()==null||xrefForm.getUdf28().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf28().toString().trim(),false,0,999999999999999.D))){ errors.add("28udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 29:																				               
							if(!( xrefForm.getUdf29()==null||xrefForm.getUdf29().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf29().toString().trim(),false,0,999999999999999.D))){ errors.add("29udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 30:																				               
							if(!( xrefForm.getUdf30()==null||xrefForm.getUdf30().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf30().toString().trim(),false,0,999999999999999.D))){ errors.add("30udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 31:																				               
							if(!( xrefForm.getUdf31()==null||xrefForm.getUdf31().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf31().toString().trim(),false,0,999999999999999.D))){ errors.add("31udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 32:																				               
							if(!( xrefForm.getUdf32()==null||xrefForm.getUdf32().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf32().toString().trim(),false,0,999999999999999.D))){ errors.add("32udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 33:																				               
							if(!( xrefForm.getUdf33()==null||xrefForm.getUdf33().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf33().toString().trim(),false,0,999999999999999.D))){ errors.add("33udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 34:																				               
							if(!( xrefForm.getUdf34()==null||xrefForm.getUdf34().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf34().toString().trim(),false,0,999999999999999.D))){ errors.add("34udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 35:																				               
							if(!( xrefForm.getUdf35()==null||xrefForm.getUdf35().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf35().toString().trim(),false,0,999999999999999.D))){ errors.add("35udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 36:																				               
							if(!( xrefForm.getUdf36()==null||xrefForm.getUdf36().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf36().toString().trim(),false,0,999999999999999.D))){ errors.add("36udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 37:																				               
							if(!( xrefForm.getUdf37()==null||xrefForm.getUdf37().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf37().toString().trim(),false,0,999999999999999.D))){ errors.add("37udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 38:																				               
							if(!( xrefForm.getUdf38()==null||xrefForm.getUdf38().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf38().toString().trim(),false,0,999999999999999.D))){ errors.add("38udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 39:																				               
							if(!( xrefForm.getUdf39()==null||xrefForm.getUdf39().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf39().toString().trim(),false,0,999999999999999.D))){ errors.add("39udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 40:																				               
							if(!( xrefForm.getUdf40()==null||xrefForm.getUdf40().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf40().toString().trim(),false,0,999999999999999.D))){ errors.add("40udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 41:																				               
							if(!( xrefForm.getUdf41()==null||xrefForm.getUdf41().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf41().toString().trim(),false,0,999999999999999.D))){ errors.add("41udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 42:																				               
							if(!( xrefForm.getUdf42()==null||xrefForm.getUdf42().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf42().toString().trim(),false,0,999999999999999.D))){ errors.add("42udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 43:																				               
							if(!( xrefForm.getUdf43()==null||xrefForm.getUdf43().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf43().toString().trim(),false,0,999999999999999.D))){ errors.add("43udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 44:																				               
							if(!( xrefForm.getUdf44()==null||xrefForm.getUdf44().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf44().toString().trim(),false,0,999999999999999.D))){ errors.add("44udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 45:																				               
							if(!( xrefForm.getUdf45()==null||xrefForm.getUdf45().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf45().toString().trim(),false,0,999999999999999.D))){ errors.add("45udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 46:																				               
							if(!( xrefForm.getUdf46()==null||xrefForm.getUdf46().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf46().toString().trim(),false,0,999999999999999.D))){ errors.add("46udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 47:																				               
							if(!( xrefForm.getUdf47()==null||xrefForm.getUdf47().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf47().toString().trim(),false,0,999999999999999.D))){ errors.add("47udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 48:																				               
							if(!( xrefForm.getUdf48()==null||xrefForm.getUdf48().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf48().toString().trim(),false,0,999999999999999.D))){ errors.add("48udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 49:																				               
							if(!( xrefForm.getUdf49()==null||xrefForm.getUdf49().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf49().toString().trim(),false,0,999999999999999.D))){ errors.add("49udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 50:																				               
							if( !(xrefForm.getUdf50()==null||xrefForm.getUdf50().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf50().toString().trim(),false,0,999999999999999.D))){ errors.add("50udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 51:if(! (xrefForm.getUdf51()==null||xrefForm.getUdf51().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf51().toString().trim(),false,0,999999999999999.D))){ errors.add("51udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 52:if(!( xrefForm.getUdf52()==null||xrefForm.getUdf52().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf52().toString().trim(),false,0,999999999999999.D))){ errors.add("52udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 53:if(!( xrefForm.getUdf53()==null||xrefForm.getUdf53().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf53().toString().trim(),false,0,999999999999999.D))){ errors.add("53udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;	
							case 54:if(!( xrefForm.getUdf54()==null||xrefForm.getUdf54().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf54().toString().trim(),false,0,999999999999999.D))){ errors.add("54udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 55:if(!( xrefForm.getUdf55()==null||xrefForm.getUdf55().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf55().toString().trim(),false,0,999999999999999.D))){ errors.add("55udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 56:if(!( xrefForm.getUdf56()==null||xrefForm.getUdf56().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf56().toString().trim(),false,0,999999999999999.D))){ errors.add("56udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 57:if(!( xrefForm.getUdf57()==null||xrefForm.getUdf57().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf57().toString().trim(),false,0,999999999999999.D))){ errors.add("57udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 58:if(!( xrefForm.getUdf58()==null||xrefForm.getUdf58().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf58().toString().trim(),false,0,999999999999999.D))){ errors.add("58udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 59:if(!( xrefForm.getUdf59()==null||xrefForm.getUdf59().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf59().toString().trim(),false,0,999999999999999.D))){ errors.add("59udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 60:if(!( xrefForm.getUdf60()==null||xrefForm.getUdf60().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf60().toString().trim(),false,0,999999999999999.D))){ errors.add("60udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 61:if(!( xrefForm.getUdf61()==null||xrefForm.getUdf61().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf61().toString().trim(),false,0,999999999999999.D))){ errors.add("61udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 62:if(!( xrefForm.getUdf62()==null||xrefForm.getUdf62().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf62().toString().trim(),false,0,999999999999999.D))){ errors.add("62udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 63:if(!( xrefForm.getUdf63()==null||xrefForm.getUdf63().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf63().toString().trim(),false,0,999999999999999.D))){ errors.add("63udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 64:if(!( xrefForm.getUdf64()==null||xrefForm.getUdf64().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf64().toString().trim(),false,0,999999999999999.D))){ errors.add("64udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 65:if(!( xrefForm.getUdf65()==null||xrefForm.getUdf65().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf65().toString().trim(),false,0,999999999999999.D))){ errors.add("65udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 66:if(!( xrefForm.getUdf66()==null||xrefForm.getUdf66().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf66().toString().trim(),false,0,999999999999999.D))){ errors.add("66udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 67:if(!( xrefForm.getUdf67()==null||xrefForm.getUdf67().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf67().toString().trim(),false,0,999999999999999.D))){ errors.add("67udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 68:if(!( xrefForm.getUdf68()==null||xrefForm.getUdf68().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf68().toString().trim(),false,0,999999999999999.D))){ errors.add("68udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 69:if(!( xrefForm.getUdf69()==null||xrefForm.getUdf69().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf69().toString().trim(),false,0,999999999999999.D))){ errors.add("69udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 70:if(!( xrefForm.getUdf70()==null||xrefForm.getUdf70().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf70().toString().trim(),false,0,999999999999999.D))){ errors.add("70udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 71:if(!( xrefForm.getUdf71()==null||xrefForm.getUdf71().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf71().toString().trim(),false,0,999999999999999.D))){ errors.add("71udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 72:if(!( xrefForm.getUdf72()==null||xrefForm.getUdf72().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf72().toString().trim(),false,0,999999999999999.D))){ errors.add("72udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 73:if(!( xrefForm.getUdf73()==null||xrefForm.getUdf73().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf73().toString().trim(),false,0,999999999999999.D))){ errors.add("73udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 74:if(!( xrefForm.getUdf74()==null||xrefForm.getUdf74().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf74().toString().trim(),false,0,999999999999999.D))){ errors.add("74udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 75:if(!( xrefForm.getUdf75()==null||xrefForm.getUdf75().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf75().toString().trim(),false,0,999999999999999.D))){ errors.add("75udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 76:if(!( xrefForm.getUdf76()==null||xrefForm.getUdf76().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf76().toString().trim(),false,0,999999999999999.D))){ errors.add("76udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 77:if(!( xrefForm.getUdf77()==null||xrefForm.getUdf77().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf77().toString().trim(),false,0,999999999999999.D))){ errors.add("77udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 78:if(!( xrefForm.getUdf78()==null||xrefForm.getUdf78().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf78().toString().trim(),false,0,999999999999999.D))){ errors.add("78udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 79:if(!( xrefForm.getUdf79()==null||xrefForm.getUdf79().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf79().toString().trim(),false,0,999999999999999.D))){ errors.add("79udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 80:if(!( xrefForm.getUdf80()==null||xrefForm.getUdf80().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf80().toString().trim(),false,0,999999999999999.D))){ errors.add("80udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 81:if(!( xrefForm.getUdf81()==null||xrefForm.getUdf81().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf81().toString().trim(),false,0,999999999999999.D))){ errors.add("81udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 82:if(!( xrefForm.getUdf82()==null||xrefForm.getUdf82().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf82().toString().trim(),false,0,999999999999999.D))){ errors.add("82udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 83:if(!( xrefForm.getUdf83()==null||xrefForm.getUdf83().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf83().toString().trim(),false,0,999999999999999.D))){ errors.add("83udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 84:if(!( xrefForm.getUdf84()==null||xrefForm.getUdf84().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf84().toString().trim(),false,0,999999999999999.D))){ errors.add("84udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 85:if(!( xrefForm.getUdf85()==null||xrefForm.getUdf85().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf85().toString().trim(),false,0,999999999999999.D))){ errors.add("85udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 86:if(!( xrefForm.getUdf86()==null||xrefForm.getUdf86().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf86().toString().trim(),false,0,999999999999999.D))){ errors.add("86udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 87:if(!( xrefForm.getUdf87()==null||xrefForm.getUdf87().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf87().toString().trim(),false,0,999999999999999.D))){ errors.add("87udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 88:if(!( xrefForm.getUdf88()==null||xrefForm.getUdf88().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf88().toString().trim(),false,0,999999999999999.D))){ errors.add("88udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 89:if(!( xrefForm.getUdf89()==null||xrefForm.getUdf89().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf89().toString().trim(),false,0,999999999999999.D))){ errors.add("89udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 90:if(!( xrefForm.getUdf90()==null||xrefForm.getUdf90().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf90().toString().trim(),false,0,999999999999999.D))){ errors.add("90udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 91:if(!( xrefForm.getUdf91()==null||xrefForm.getUdf91().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf91().toString().trim(),false,0,999999999999999.D))){ errors.add("91udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 92:if(!( xrefForm.getUdf92()==null||xrefForm.getUdf92().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf92().toString().trim(),false,0,999999999999999.D))){ errors.add("92udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 93:if(!( xrefForm.getUdf93()==null||xrefForm.getUdf93().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf93().toString().trim(),false,0,999999999999999.D))){ errors.add("93udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 94:if(!( xrefForm.getUdf94()==null||xrefForm.getUdf94().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf94().toString().trim(),false,0,999999999999999.D))){ errors.add("94udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 95:if(!( xrefForm.getUdf95()==null||xrefForm.getUdf95().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf95().toString().trim(),false,0,999999999999999.D))){ errors.add("95udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 96:if(!( xrefForm.getUdf96()==null||xrefForm.getUdf96().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf96().toString().trim(),false,0,999999999999999.D))){ errors.add("96udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 97:if(!( xrefForm.getUdf97()==null||xrefForm.getUdf97().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf97().toString().trim(),false,0,999999999999999.D))){ errors.add("97udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 98:if(!( xrefForm.getUdf98()==null||xrefForm.getUdf98().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf98().toString().trim(),false,0,999999999999999.D))){ errors.add("98udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 99:if(!( xrefForm.getUdf99()==null||xrefForm.getUdf99().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf99().toString().trim(),false,0,999999999999999.D))){ errors.add("99udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 100:if( !(xrefForm.getUdf100()==null||xrefForm.getUdf100().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf100().toString().trim(),false,0,999999999999999.D))){ errors.add("100udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 101:if( !(xrefForm.getUdf101()==null||xrefForm.getUdf101().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf101().toString().trim(),false,0,999999999999999.D))){ errors.add("101udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 102:if( !(xrefForm.getUdf102()==null||xrefForm.getUdf102().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf102().toString().trim(),false,0,999999999999999.D))){ errors.add("102udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 103:if( !(xrefForm.getUdf103()==null||xrefForm.getUdf103().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf103().toString().trim(),false,0,999999999999999.D))){ errors.add("103udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 104:if( !(xrefForm.getUdf104()==null||xrefForm.getUdf104().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf104().toString().trim(),false,0,999999999999999.D))){ errors.add("104udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 105:if( !(xrefForm.getUdf105()==null||xrefForm.getUdf105().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf105().toString().trim(),false,0,999999999999999.D))){ errors.add("105udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 106:if( !(xrefForm.getUdf106()==null||xrefForm.getUdf106().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf106().toString().trim(),false,0,999999999999999.D))){ errors.add("106udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 107:if( !(xrefForm.getUdf107()==null||xrefForm.getUdf107().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf107().toString().trim(),false,0,999999999999999.D))){ errors.add("107udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 108:if( !(xrefForm.getUdf108()==null||xrefForm.getUdf108().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf108().toString().trim(),false,0,999999999999999.D))){ errors.add("108udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 109:if( !(xrefForm.getUdf109()==null||xrefForm.getUdf109().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf109().toString().trim(),false,0,999999999999999.D))){ errors.add("109udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 110:if( !(xrefForm.getUdf110()==null||xrefForm.getUdf110().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf110().toString().trim(),false,0,999999999999999.D))){ errors.add("110udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 111:if( !(xrefForm.getUdf111()==null||xrefForm.getUdf111().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf111().toString().trim(),false,0,999999999999999.D))){ errors.add("111udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 112:if( !(xrefForm.getUdf112()==null||xrefForm.getUdf112().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf112().toString().trim(),false,0,999999999999999.D))){ errors.add("112udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 113:if( !(xrefForm.getUdf113()==null||xrefForm.getUdf113().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf113().toString().trim(),false,0,999999999999999.D))){ errors.add("113udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 114:if( !(xrefForm.getUdf114()==null||xrefForm.getUdf114().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf114().toString().trim(),false,0,999999999999999.D))){ errors.add("114udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
							case 115:if( !(xrefForm.getUdf115()==null||xrefForm.getUdf115().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf115().toString().trim(),false,0,999999999999999.D))){ errors.add("115udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
						
							default:
								break;
						}
					}
				}	
			//End Santosh UBS-LIMIT UPLOAD		
		
				// UDF -2 Validation
				
				validateUdf_2(udfDao,xrefForm, errors , errorCode);
			// End UBS-LIMIT -2	
		
			}
			}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return errors;
	}



public static void validateUdf_2(IUdfDao udfDao, XRefDetailForm xrefForm , ActionErrors errors , String errorCode )
{
	List udfMandatoryList_2=udfDao.getUdfByMandatory("4");
	List udfNumericList_2=udfDao.getUdfByFieldTypeId("4",7);
	if(null!=xrefForm.getUdfAllowed_2() && !"".equalsIgnoreCase(xrefForm.getUdfAllowed_2())) {
		
		String[] udfAllowedList = null;
		if(xrefForm.getUdfAllowed_2().contains(",")) {
		udfAllowedList = xrefForm.getUdfAllowed_2().split(",");
		
		String[] udfAllowedListTemp = null;
		List udfByModuleId_2 = udfDao.getUdfByModuleId("4");
		
		String enabledUdfAllowed="";
		for(String udfSelected :udfAllowedList) {
			if(null!=udfByModuleId_2 && udfByModuleId_2.size()>0) {
				for(int k=0; k<udfByModuleId_2.size() ; k++) {
				IUdf udf=(IUdf)udfByModuleId_2.get(k);
				if("ACTIVE".equals(udf.getStatus())) {
					int sequence = (udf.getSequence()+115);
					
				if(String.valueOf(sequence).equals(udfSelected)) {
					enabledUdfAllowed=udfSelected+","+enabledUdfAllowed;
					
				}
				
				
				}
				}
			}
		}
		
		System.out.println("enabledUdfAllowed 1  :"+enabledUdfAllowed);
		if(enabledUdfAllowed.endsWith(",")) {
			enabledUdfAllowed=enabledUdfAllowed.substring(0, enabledUdfAllowed.length()-1);
			System.out.println("enabledUdfAllowed inside if:"+enabledUdfAllowed);
		}
		
		if(!enabledUdfAllowed.isEmpty()) {
			udfAllowedList = enabledUdfAllowed.split(",");
		}
		}
		else {
			udfAllowedList = new String[1];
		udfAllowedList[0] = xrefForm.getUdfAllowed_2();
		}
		
		List udfByModuleFieldTypeId_2 = udfDao.getUdfByFieldTypeId("4",4);
		if(null!=udfByModuleFieldTypeId_2) {
		for(int m=0;m<udfByModuleFieldTypeId_2.size();m++) {
			IUdf iUdf=(IUdf)udfByModuleFieldTypeId_2.get(m);
			
			for(String udfAllowed: udfAllowedList) {
				
				if(udfAllowed.equals(String.valueOf( (iUdf.getSequence()+115)  ))) {
					
					String[] split = iUdf.getOptions().split(",");
				
					if((iUdf.getSequence() + 115)== 116) { 
						String udfObj= xrefForm.getUdf116();
						if(!(udfObj == null || udfObj.trim().equals(""))){
						for(int l=0;l<split.length;l++) {
							String optionData = split[l].trim();
						if(optionData.equals(udfObj)) {
							break;
						}else if (l==split.length-1) {
							xrefForm.setUdf116(null);
						}
					}
					}
			}else if((iUdf.getSequence() + 115)== 117) { 
						String udfObj= xrefForm.getUdf117();
						if(!(udfObj == null || udfObj.trim().equals(""))){
						for(int l=0;l<split.length;l++) {
							String optionData = split[l].trim();
						if(optionData.equals(udfObj)) {
							break;
						}else if (l==split.length-1) {
							xrefForm.setUdf117(null);
						}
					}
					}
			}else if((iUdf.getSequence() + 115)== 118) { 
						String udfObj= xrefForm.getUdf118();
						if(!(udfObj == null || udfObj.trim().equals(""))){
						for(int l=0;l<split.length;l++) {
							String optionData = split[l].trim();
						if(optionData.equals(udfObj)) {
							break;
						}else if (l==split.length-1) {
							xrefForm.setUdf118(null);
						}
					}
					}
			}else if((iUdf.getSequence() + 115)== 119) { 
						String udfObj= xrefForm.getUdf119();
						if(!(udfObj == null || udfObj.trim().equals(""))){
						for(int l=0;l<split.length;l++) {
							String optionData = split[l].trim();
						if(optionData.equals(udfObj)) {
							break;
						}else if (l==split.length-1) {
							xrefForm.setUdf119(null);
						}
					}
					}
			}else if((iUdf.getSequence() + 115)== 120) { 
						String udfObj= xrefForm.getUdf120();
						if(!(udfObj == null || udfObj.trim().equals(""))){
						for(int l=0;l<split.length;l++) {
							String optionData = split[l].trim();
						if(optionData.equals(udfObj)) {
							break;
						}else if (l==split.length-1) {
							xrefForm.setUdf120(null);
						}
					}
					}
			}else if((iUdf.getSequence() + 115)== 121) { 
						String udfObj= xrefForm.getUdf121();
						if(!(udfObj == null || udfObj.trim().equals(""))){
						for(int l=0;l<split.length;l++) {
							String optionData = split[l].trim();
						if(optionData.equals(udfObj)) {
							break;
						}else if (l==split.length-1) {
							xrefForm.setUdf121(null);
						}
					}
					}
			}		else if((iUdf.getSequence() + 115)== 122) { 
				String udfObj= xrefForm.getUdf122();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
					String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
					break;
				}else if (l==split.length-1) {
					xrefForm.setUdf122(null);
				}
			}
			}
			}else if((iUdf.getSequence() + 115)== 123) { 
				String udfObj= xrefForm.getUdf123();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
					String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
					break;
				}else if (l==split.length-1) {
					xrefForm.setUdf123(null);
				}
			}
			}
			}else if((iUdf.getSequence() + 115)== 124) { 
				String udfObj= xrefForm.getUdf124();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
					String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
					break;
				}else if (l==split.length-1) {
					xrefForm.setUdf124(null);
				}
			}
			}
			}else if((iUdf.getSequence() + 115)== 125) { 
				String udfObj= xrefForm.getUdf125();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
					String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
					break;
				}else if (l==split.length-1) {
					xrefForm.setUdf125(null);
				}
			}
			}
			}else if((iUdf.getSequence() + 115)== 126) { 
			String udfObj= xrefForm.getUdf126();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf126(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 127) { 
			String udfObj= xrefForm.getUdf127();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf127(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 128) { 
			String udfObj= xrefForm.getUdf128();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf128(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 129) { 
			String udfObj= xrefForm.getUdf129();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf129(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 130) { 
			String udfObj= xrefForm.getUdf130();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf130(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 131) { 
				String udfObj= xrefForm.getUdf131();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
					String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
					break;
				}else if (l==split.length-1) {
					xrefForm.setUdf131(null);
				}
			}
			}
			}		else if((iUdf.getSequence() + 115)== 132) { 
			String udfObj= xrefForm.getUdf132();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf132(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 133) { 
			String udfObj= xrefForm.getUdf133();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf133(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 134) { 
			String udfObj= xrefForm.getUdf134();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf134(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 135) { 
			String udfObj= xrefForm.getUdf135();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf135(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 136) { 
			String udfObj= xrefForm.getUdf136();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf136(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 137) { 
			String udfObj= xrefForm.getUdf137();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf137(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 138) { 
			String udfObj= xrefForm.getUdf138();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf138(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 139) { 
			String udfObj= xrefForm.getUdf139();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf139(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 140) { 
			String udfObj= xrefForm.getUdf140();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf140(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 141) { 
				String udfObj= xrefForm.getUdf141();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
					String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
					break;
				}else if (l==split.length-1) {
					xrefForm.setUdf141(null);
				}
			}
			}
			}		else if((iUdf.getSequence() + 115)== 142) { 
			String udfObj= xrefForm.getUdf142();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf142(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 143) { 
			String udfObj= xrefForm.getUdf143();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf143(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 144) { 
			String udfObj= xrefForm.getUdf144();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf144(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 145) { 
			String udfObj= xrefForm.getUdf145();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf145(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 146) { 
			String udfObj= xrefForm.getUdf146();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf146(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 147) { 
			String udfObj= xrefForm.getUdf147();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf147(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 148) { 
			String udfObj= xrefForm.getUdf148();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf148(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 149) { 
			String udfObj= xrefForm.getUdf149();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf149(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 150) { 
			String udfObj= xrefForm.getUdf150();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf150(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 151) { 
				String udfObj= xrefForm.getUdf151();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
					String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
					break;
				}else if (l==split.length-1) {
					xrefForm.setUdf151(null);
				}
			}
			}
			}		else if((iUdf.getSequence() + 115)== 152) { 
			String udfObj= xrefForm.getUdf152();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf152(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 153) { 
			String udfObj= xrefForm.getUdf153();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf153(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 154) { 
			String udfObj= xrefForm.getUdf154();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf154(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 155) { 
			String udfObj= xrefForm.getUdf155();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf155(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 156) { 
			String udfObj= xrefForm.getUdf156();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf156(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 157) { 
			String udfObj= xrefForm.getUdf157();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf157(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 158) { 
			String udfObj= xrefForm.getUdf158();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf158(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 159) { 
			String udfObj= xrefForm.getUdf159();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf159(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 160) { 
			String udfObj= xrefForm.getUdf160();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf160(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 161) { 
				String udfObj= xrefForm.getUdf161();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
					String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
					break;
				}else if (l==split.length-1) {
					xrefForm.setUdf161(null);
				}
			}
			}
			}		else if((iUdf.getSequence() + 115)== 162) { 
			String udfObj= xrefForm.getUdf162();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf162(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 163) { 
			String udfObj= xrefForm.getUdf163();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf163(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 164) { 
			String udfObj= xrefForm.getUdf164();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf164(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 165) { 
			String udfObj= xrefForm.getUdf165();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf165(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 166) { 
			String udfObj= xrefForm.getUdf166();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf166(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 167) { 
			String udfObj= xrefForm.getUdf167();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf167(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 168) { 
			String udfObj= xrefForm.getUdf168();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf168(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 169) { 
			String udfObj= xrefForm.getUdf169();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf169(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 170) { 
			String udfObj= xrefForm.getUdf170();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf170(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 171) { 
				String udfObj= xrefForm.getUdf171();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
					String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
					break;
				}else if (l==split.length-1) {
					xrefForm.setUdf171(null);
				}
			}
			}
			}		else if((iUdf.getSequence() + 115)== 172) { 
			String udfObj= xrefForm.getUdf172();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf172(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 173) { 
			String udfObj= xrefForm.getUdf173();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf173(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 174) { 
			String udfObj= xrefForm.getUdf174();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf174(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 175) { 
			String udfObj= xrefForm.getUdf175();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf175(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 176) { 
			String udfObj= xrefForm.getUdf176();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf176(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 177) { 
			String udfObj= xrefForm.getUdf177();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf177(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 178) { 
			String udfObj= xrefForm.getUdf178();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf178(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 179) { 
			String udfObj= xrefForm.getUdf179();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf179(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 180) { 
			String udfObj= xrefForm.getUdf180();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf180(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 181) { 
				String udfObj= xrefForm.getUdf181();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
					String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
					break;
				}else if (l==split.length-1) {
					xrefForm.setUdf181(null);
				}
			}
			}
			}		else if((iUdf.getSequence() + 115)== 182) { 
			String udfObj= xrefForm.getUdf182();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf182(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 183) { 
			String udfObj= xrefForm.getUdf183();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf183(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 184) { 
			String udfObj= xrefForm.getUdf184();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf184(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 185) { 
			String udfObj= xrefForm.getUdf185();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf185(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 186) { 
			String udfObj= xrefForm.getUdf186();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf186(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 187) { 
			String udfObj= xrefForm.getUdf187();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf187(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 188) { 
			String udfObj= xrefForm.getUdf188();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf188(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 189) { 
			String udfObj= xrefForm.getUdf189();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf189(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 190) { 
			String udfObj= xrefForm.getUdf190();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf190(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 191) { 
				String udfObj= xrefForm.getUdf191();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
					String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
					break;
				}else if (l==split.length-1) {
					xrefForm.setUdf191(null);
				}
			}
			}
			}		else if((iUdf.getSequence() + 115)== 192) { 
			String udfObj= xrefForm.getUdf192();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf192(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 193) { 
			String udfObj= xrefForm.getUdf193();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf193(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 194) { 
			String udfObj= xrefForm.getUdf194();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf194(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 195) { 
			String udfObj= xrefForm.getUdf195();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf195(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 196) { 
			String udfObj= xrefForm.getUdf196();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf196(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 197) { 
			String udfObj= xrefForm.getUdf197();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf197(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 198) { 
			String udfObj= xrefForm.getUdf198();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf198(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 199) { 
			String udfObj= xrefForm.getUdf199();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf199(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 200) { 
			String udfObj= xrefForm.getUdf200();
			if(!(udfObj == null || udfObj.trim().equals(""))){
			for(int l=0;l<split.length;l++) {
			String optionData = split[l].trim();
			if(optionData.equals(udfObj)) {
			break;
			}else if (l==split.length-1) {
			xrefForm.setUdf200(null);
			}
			}
			}
			}else if((iUdf.getSequence() + 115)== 201) { 
				String udfObj= xrefForm.getUdf201();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
				break;
				}else if (l==split.length-1) {
				xrefForm.setUdf201(null);
				}
				}
				}
			}	else if((iUdf.getSequence() + 115)== 202) { 
				String udfObj= xrefForm.getUdf202();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
				break;
				}else if (l==split.length-1) {
				xrefForm.setUdf202(null);
				}
				}
				}
			}else if((iUdf.getSequence() + 115)== 203) { 
				String udfObj= xrefForm.getUdf203();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
				break;
				}else if (l==split.length-1) {
				xrefForm.setUdf203(null);
				}
				}
				}
			}	else if((iUdf.getSequence() + 115)== 204) { 
				String udfObj= xrefForm.getUdf204();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
				break;
				}else if (l==split.length-1) {
				xrefForm.setUdf204(null);
				}
				}
				}
			}	else if((iUdf.getSequence() + 115)== 205) { 
				String udfObj= xrefForm.getUdf205();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
				break;
				}else if (l==split.length-1) {
				xrefForm.setUdf205(null);
				}
				}
				}
			}	else if((iUdf.getSequence() + 115)== 206) { 
				String udfObj= xrefForm.getUdf206();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
				break;
				}else if (l==split.length-1) {
				xrefForm.setUdf206(null);
				}
				}
				}
			}	else if((iUdf.getSequence() + 115)== 207) { 
				String udfObj= xrefForm.getUdf207();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
				break;
				}else if (l==split.length-1) {
				xrefForm.setUdf207(null);
				}
				}
				}
			} else if((iUdf.getSequence() + 115)== 208) { 
				String udfObj= xrefForm.getUdf208();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
				break;
				}else if (l==split.length-1) {
				xrefForm.setUdf208(null);
				}
				}
				}
			}	else if((iUdf.getSequence() + 115)== 209) { 
				String udfObj= xrefForm.getUdf209();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
				break;
				}else if (l==split.length-1) {
				xrefForm.setUdf209(null);
				}
				}
				}
			}	else if((iUdf.getSequence() + 115)== 210) { 
				String udfObj= xrefForm.getUdf210();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
				break;
				}else if (l==split.length-1) {
				xrefForm.setUdf210(null);
				}
				}
				}
			}	else if((iUdf.getSequence() + 115)== 211) { 
				String udfObj= xrefForm.getUdf211();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
				break;
				}else if (l==split.length-1) {
				xrefForm.setUdf211(null);
				}
				}
				}
			}	else if((iUdf.getSequence() + 115)== 212) { 
				String udfObj= xrefForm.getUdf212();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
				break;
				}else if (l==split.length-1) {
				xrefForm.setUdf212(null);
				}
				}
				}
			}	else if((iUdf.getSequence() + 115)== 213) { 
				String udfObj= xrefForm.getUdf213();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
				break;
				}else if (l==split.length-1) {
				xrefForm.setUdf213(null);
				}
				}
				}
			}	else if((iUdf.getSequence() + 115)== 214) { 
				String udfObj= xrefForm.getUdf214();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
				break;
				}else if (l==split.length-1) {
				xrefForm.setUdf214(null);
				}
				}
				}
			}	else if((iUdf.getSequence() + 115)== 215) { 
				String udfObj= xrefForm.getUdf215();
				if(!(udfObj == null || udfObj.trim().equals(""))){
				for(int l=0;l<split.length;l++) {
				String optionData = split[l].trim();
				if(optionData.equals(udfObj)) {
				break;
				}else if (l==split.length-1) {
				xrefForm.setUdf215(null);
				}
				}
				}
			}	
												

			
		}
			}
		}
		}
		
		int count=0;
		for(String udfSelected : udfAllowedList) {
			
			switch (Integer.parseInt(udfSelected)) {
		

			case 116:
			if( xrefForm.getUdf116()==null||xrefForm.getUdf116().trim().equals("")){ count++; } break;
			case 117:
			if( xrefForm.getUdf117()==null||xrefForm.getUdf117().trim().equals("")){ count++; } break;
			case 118:
			if( xrefForm.getUdf118()==null||xrefForm.getUdf118().trim().equals("")){ count++; } break;
			case 119:
			if( xrefForm.getUdf119()==null||xrefForm.getUdf119().trim().equals("")){ count++; } break;
			case 120:
			if( xrefForm.getUdf120()==null||xrefForm.getUdf120().trim().equals("")){ count++; } break;
			case 121:
			if( xrefForm.getUdf121()==null||xrefForm.getUdf121().trim().equals("")){ count++; } break;
			case 122:
			if( xrefForm.getUdf122()==null||xrefForm.getUdf122().trim().equals("")){ count++; } break;
			case 123:
			if( xrefForm.getUdf123()==null||xrefForm.getUdf123().trim().equals("")){ count++; } break;
			case 124:
			if( xrefForm.getUdf124()==null||xrefForm.getUdf124().trim().equals("")){ count++; } break;
			case 125:
			if( xrefForm.getUdf125()==null||xrefForm.getUdf125().trim().equals("")){ count++; } break;
			case 126:
			if( xrefForm.getUdf126()==null||xrefForm.getUdf126().trim().equals("")){ count++; } break;
			case 127:
			if( xrefForm.getUdf127()==null||xrefForm.getUdf127().trim().equals("")){ count++; } break;
			case 128:
			if( xrefForm.getUdf128()==null||xrefForm.getUdf128().trim().equals("")){ count++; } break;
			case 129:
			if( xrefForm.getUdf129()==null||xrefForm.getUdf129().trim().equals("")){ count++; } break;
			case 130:
			if( xrefForm.getUdf130()==null||xrefForm.getUdf130().trim().equals("")){ count++; } break;
			case 131:
			if( xrefForm.getUdf131()==null||xrefForm.getUdf131().trim().equals("")){ count++; } break;
			case 132:
			if( xrefForm.getUdf132()==null||xrefForm.getUdf132().trim().equals("")){ count++; } break;
			case 133:
			if( xrefForm.getUdf133()==null||xrefForm.getUdf133().trim().equals("")){ count++; } break;
			case 134:
			if( xrefForm.getUdf134()==null||xrefForm.getUdf134().trim().equals("")){ count++; } break;
			case 135:
			if( xrefForm.getUdf135()==null||xrefForm.getUdf135().trim().equals("")){ count++; } break;
			case 136:
			if( xrefForm.getUdf136()==null||xrefForm.getUdf136().trim().equals("")){ count++; } break;
			case 137:
			if( xrefForm.getUdf137()==null||xrefForm.getUdf137().trim().equals("")){ count++; } break;
			case 138:
			if( xrefForm.getUdf138()==null||xrefForm.getUdf138().trim().equals("")){ count++; } break;
			case 139:
			if( xrefForm.getUdf139()==null||xrefForm.getUdf139().trim().equals("")){ count++; } break;
			case 140:
			if( xrefForm.getUdf140()==null||xrefForm.getUdf140().trim().equals("")){ count++; } break;
			case 141:
			if( xrefForm.getUdf141()==null||xrefForm.getUdf141().trim().equals("")){ count++; } break;
			case 142:
			if( xrefForm.getUdf142()==null||xrefForm.getUdf142().trim().equals("")){ count++; } break;
			case 143:
			if( xrefForm.getUdf143()==null||xrefForm.getUdf143().trim().equals("")){ count++; } break;
			case 144:
			if( xrefForm.getUdf144()==null||xrefForm.getUdf144().trim().equals("")){ count++; } break;
			case 145:
			if( xrefForm.getUdf145()==null||xrefForm.getUdf145().trim().equals("")){ count++; } break;
			case 146:
			if( xrefForm.getUdf146()==null||xrefForm.getUdf146().trim().equals("")){ count++; } break;
			case 147:
			if( xrefForm.getUdf147()==null||xrefForm.getUdf147().trim().equals("")){ count++; } break;
			case 148:
			if( xrefForm.getUdf148()==null||xrefForm.getUdf148().trim().equals("")){ count++; } break;
			case 149:
			if( xrefForm.getUdf149()==null||xrefForm.getUdf149().trim().equals("")){ count++; } break;
			case 150:
			if( xrefForm.getUdf150()==null||xrefForm.getUdf150().trim().equals("")){ count++; } break;
			
			case 151:if( xrefForm.getUdf151()==null||xrefForm.getUdf151().trim().equals("")){ count++; } break;
			case 152:if( xrefForm.getUdf152()==null||xrefForm.getUdf152().trim().equals("")){ count++; } break;
			case 153:if( xrefForm.getUdf153()==null||xrefForm.getUdf153().trim().equals("")){ count++; } break;
			case 154:if( xrefForm.getUdf154()==null||xrefForm.getUdf154().trim().equals("")){ count++; } break;
			case 155:if( xrefForm.getUdf155()==null||xrefForm.getUdf155().trim().equals("")){ count++; } break;
			case 156:if( xrefForm.getUdf156()==null||xrefForm.getUdf156().trim().equals("")){ count++; } break;
			case 157:if( xrefForm.getUdf157()==null||xrefForm.getUdf157().trim().equals("")){ count++; } break;
			case 158:if( xrefForm.getUdf158()==null||xrefForm.getUdf158().trim().equals("")){ count++; } break;
			case 159:if( xrefForm.getUdf159()==null||xrefForm.getUdf159().trim().equals("")){ count++; } break;
			case 160:if( xrefForm.getUdf160()==null||xrefForm.getUdf160().trim().equals("")){ count++; } break;
			case 161:if( xrefForm.getUdf161()==null||xrefForm.getUdf161().trim().equals("")){ count++; } break;
			case 162:if( xrefForm.getUdf162()==null||xrefForm.getUdf162().trim().equals("")){ count++; } break;
			case 163:if( xrefForm.getUdf163()==null||xrefForm.getUdf163().trim().equals("")){ count++; } break;
			case 164:if( xrefForm.getUdf164()==null||xrefForm.getUdf164().trim().equals("")){ count++; } break;
			case 165:if( xrefForm.getUdf165()==null||xrefForm.getUdf165().trim().equals("")){ count++; } break;
			case 166:if( xrefForm.getUdf166()==null||xrefForm.getUdf166().trim().equals("")){ count++; } break;
			case 167:if( xrefForm.getUdf167()==null||xrefForm.getUdf167().trim().equals("")){ count++; } break;
			case 168:if( xrefForm.getUdf168()==null||xrefForm.getUdf168().trim().equals("")){ count++; } break;
			case 169:if( xrefForm.getUdf169()==null||xrefForm.getUdf169().trim().equals("")){ count++; } break;
			case 170:if( xrefForm.getUdf170()==null||xrefForm.getUdf170().trim().equals("")){ count++; } break;
			case 171:if( xrefForm.getUdf171()==null||xrefForm.getUdf171().trim().equals("")){ count++; } break;
			case 172:if( xrefForm.getUdf172()==null||xrefForm.getUdf172().trim().equals("")){ count++; } break;
			case 173:if( xrefForm.getUdf173()==null||xrefForm.getUdf173().trim().equals("")){ count++; } break;
			case 174:if( xrefForm.getUdf174()==null||xrefForm.getUdf174().trim().equals("")){ count++; } break;
			case 175:if( xrefForm.getUdf175()==null||xrefForm.getUdf175().trim().equals("")){ count++; } break;
			case 176:if( xrefForm.getUdf176()==null||xrefForm.getUdf176().trim().equals("")){ count++; } break;
			case 177:if( xrefForm.getUdf177()==null||xrefForm.getUdf177().trim().equals("")){ count++; } break;
			case 178:if( xrefForm.getUdf178()==null||xrefForm.getUdf178().trim().equals("")){ count++; } break;
			case 179:if( xrefForm.getUdf179()==null||xrefForm.getUdf179().trim().equals("")){ count++; } break;
			case 180:if( xrefForm.getUdf180()==null||xrefForm.getUdf180().trim().equals("")){ count++; } break;
			case 181:if( xrefForm.getUdf181()==null||xrefForm.getUdf181().trim().equals("")){ count++; } break;
			case 182:if( xrefForm.getUdf182()==null||xrefForm.getUdf182().trim().equals("")){ count++; } break;
			case 183:if( xrefForm.getUdf183()==null||xrefForm.getUdf183().trim().equals("")){ count++; } break;
			case 184:if( xrefForm.getUdf184()==null||xrefForm.getUdf184().trim().equals("")){ count++; } break;
			case 185:if( xrefForm.getUdf185()==null||xrefForm.getUdf185().trim().equals("")){ count++; } break;
			case 186:if( xrefForm.getUdf186()==null||xrefForm.getUdf186().trim().equals("")){ count++; } break;
			case 187:if( xrefForm.getUdf187()==null||xrefForm.getUdf187().trim().equals("")){ count++; } break;
			case 188:if( xrefForm.getUdf188()==null||xrefForm.getUdf188().trim().equals("")){ count++; } break;
			case 189:if( xrefForm.getUdf189()==null||xrefForm.getUdf189().trim().equals("")){ count++; } break;
			case 190:if( xrefForm.getUdf190()==null||xrefForm.getUdf190().trim().equals("")){ count++; } break;
			case 191:if( xrefForm.getUdf191()==null||xrefForm.getUdf191().trim().equals("")){ count++; } break;
			case 192:if( xrefForm.getUdf192()==null||xrefForm.getUdf192().trim().equals("")){ count++; } break;
			case 193:if( xrefForm.getUdf193()==null||xrefForm.getUdf193().trim().equals("")){ count++; } break;
			case 194:if( xrefForm.getUdf194()==null||xrefForm.getUdf194().trim().equals("")){ count++; } break;
			case 195:if( xrefForm.getUdf195()==null||xrefForm.getUdf195().trim().equals("")){ count++; } break;
			case 196:if( xrefForm.getUdf196()==null||xrefForm.getUdf196().trim().equals("")){ count++; } break;
			case 197:if( xrefForm.getUdf197()==null||xrefForm.getUdf197().trim().equals("")){ count++; } break;
			case 198:if( xrefForm.getUdf198()==null||xrefForm.getUdf198().trim().equals("")){ count++; } break;
			case 199:if( xrefForm.getUdf199()==null||xrefForm.getUdf199().trim().equals("")){ count++; } break;
			case 200:if( xrefForm.getUdf200()==null||xrefForm.getUdf200().trim().equals("")){ count++; } break;
			
			case 201:if( xrefForm.getUdf201()==null||xrefForm.getUdf201().trim().equals("")){ count++; } break;
			case 202:if( xrefForm.getUdf202()==null||xrefForm.getUdf202().trim().equals("")){ count++; } break;
			case 203:if( xrefForm.getUdf203()==null||xrefForm.getUdf203().trim().equals("")){ count++; } break;
			case 204:if( xrefForm.getUdf204()==null||xrefForm.getUdf204().trim().equals("")){ count++; } break;
			case 205:if( xrefForm.getUdf205()==null||xrefForm.getUdf205().trim().equals("")){ count++; } break;
			case 206:if( xrefForm.getUdf206()==null||xrefForm.getUdf206().trim().equals("")){ count++; } break;
			case 207:if( xrefForm.getUdf207()==null||xrefForm.getUdf207().trim().equals("")){ count++; } break;
			case 208:if( xrefForm.getUdf208()==null||xrefForm.getUdf208().trim().equals("")){ count++; } break;
			case 209:if( xrefForm.getUdf209()==null||xrefForm.getUdf209().trim().equals("")){ count++; } break;
			case 210:if( xrefForm.getUdf210()==null||xrefForm.getUdf210().trim().equals("")){ count++; } break;
			case 211:if( xrefForm.getUdf211()==null||xrefForm.getUdf211().trim().equals("")){ count++; } break;
			case 212:if( xrefForm.getUdf212()==null||xrefForm.getUdf212().trim().equals("")){ count++; } break;
			case 213:if( xrefForm.getUdf213()==null||xrefForm.getUdf213().trim().equals("")){ count++; } break;
			case 214:if( xrefForm.getUdf214()==null||xrefForm.getUdf214().trim().equals("")){ count++; } break;
			case 215:if( xrefForm.getUdf215()==null||xrefForm.getUdf215().trim().equals("")){ count++; } break;
		
			
				default:
					break;
			}
		}
		if(count>0) {
			errors.add("1udfError", new ActionMessage("error.string.nonmandatory.XRefUdf" , ""));
		}
	}
	if(udfMandatoryList_2!=null){
		for(int udf=0;udf<udfMandatoryList_2.size();udf++){
			IUdf iUdf=(IUdf)udfMandatoryList_2.get(udf);
			switch (iUdf.getSequence()+115) {
			
			
			case 116:
						if( xrefForm.getUdf116()==null||xrefForm.getUdf116().trim().equals("")){ errors.add("116udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 117:
						if( xrefForm.getUdf117()==null||xrefForm.getUdf117().trim().equals("")){ errors.add("117udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 118:
						if( xrefForm.getUdf118()==null||xrefForm.getUdf118().trim().equals("")){ errors.add("118udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 119:
						if( xrefForm.getUdf119()==null||xrefForm.getUdf119().trim().equals("")){ errors.add("119udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 120:
						if( xrefForm.getUdf120()==null||xrefForm.getUdf120().trim().equals("")){ errors.add("120udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 121:
						if( xrefForm.getUdf121()==null||xrefForm.getUdf121().trim().equals("")){ errors.add("121udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 122:
						if( xrefForm.getUdf122()==null||xrefForm.getUdf122().trim().equals("")){ errors.add("122udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 123:
						if( xrefForm.getUdf123()==null||xrefForm.getUdf123().trim().equals("")){ errors.add("123udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 124:
						if( xrefForm.getUdf124()==null||xrefForm.getUdf124().trim().equals("")){ errors.add("124udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 125:
						if( xrefForm.getUdf125()==null||xrefForm.getUdf125().trim().equals("")){ errors.add("125udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 126:
						if( xrefForm.getUdf126()==null||xrefForm.getUdf126().trim().equals("")){ errors.add("126udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 127:
						if( xrefForm.getUdf127()==null||xrefForm.getUdf127().trim().equals("")){ errors.add("127udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 128:
						if( xrefForm.getUdf128()==null||xrefForm.getUdf128().trim().equals("")){ errors.add("128udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 129:
						if( xrefForm.getUdf129()==null||xrefForm.getUdf129().trim().equals("")){ errors.add("129udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 130:
						if( xrefForm.getUdf130()==null||xrefForm.getUdf130().trim().equals("")){ errors.add("130udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 131:
						if( xrefForm.getUdf131()==null||xrefForm.getUdf131().trim().equals("")){ errors.add("131udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 132:
						if( xrefForm.getUdf132()==null||xrefForm.getUdf132().trim().equals("")){ errors.add("132udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 133:
						if( xrefForm.getUdf133()==null||xrefForm.getUdf133().trim().equals("")){ errors.add("133udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 134:
						if( xrefForm.getUdf134()==null||xrefForm.getUdf134().trim().equals("")){ errors.add("134udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 135:
						if( xrefForm.getUdf135()==null||xrefForm.getUdf135().trim().equals("")){ errors.add("135udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 136:
						if( xrefForm.getUdf136()==null||xrefForm.getUdf136().trim().equals("")){ errors.add("136udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 137:
						if( xrefForm.getUdf137()==null||xrefForm.getUdf137().trim().equals("")){ errors.add("137udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 138:
						if( xrefForm.getUdf138()==null||xrefForm.getUdf138().trim().equals("")){ errors.add("138udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 139:
						if( xrefForm.getUdf139()==null||xrefForm.getUdf139().trim().equals("")){ errors.add("139udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 140:
						if( xrefForm.getUdf140()==null||xrefForm.getUdf140().trim().equals("")){ errors.add("140udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 141:
						if( xrefForm.getUdf141()==null||xrefForm.getUdf141().trim().equals("")){ errors.add("141udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 142:
						if( xrefForm.getUdf142()==null||xrefForm.getUdf142().trim().equals("")){ errors.add("142udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 143:
						if( xrefForm.getUdf143()==null||xrefForm.getUdf143().trim().equals("")){ errors.add("143udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 144:
						if( xrefForm.getUdf144()==null||xrefForm.getUdf144().trim().equals("")){ errors.add("144udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 145:
						if( xrefForm.getUdf145()==null||xrefForm.getUdf145().trim().equals("")){ errors.add("145udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 146:
						if( xrefForm.getUdf146()==null||xrefForm.getUdf146().trim().equals("")){ errors.add("146udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 147:
						if( xrefForm.getUdf147()==null||xrefForm.getUdf147().trim().equals("")){ errors.add("147udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 148:
						if( xrefForm.getUdf148()==null||xrefForm.getUdf148().trim().equals("")){ errors.add("148udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 149:
						if( xrefForm.getUdf149()==null||xrefForm.getUdf149().trim().equals("")){ errors.add("149udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 150:
						if( xrefForm.getUdf150()==null||xrefForm.getUdf150().trim().equals("")){ errors.add("150udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 151:if( xrefForm.getUdf151()==null||xrefForm.getUdf151().trim().equals("")){ errors.add("151udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 152:if( xrefForm.getUdf152()==null||xrefForm.getUdf152().trim().equals("")){ errors.add("152udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 153:if( xrefForm.getUdf153()==null||xrefForm.getUdf153().trim().equals("")){ errors.add("153udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 154:if( xrefForm.getUdf154()==null||xrefForm.getUdf154().trim().equals("")){ errors.add("154udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 155:if( xrefForm.getUdf155()==null||xrefForm.getUdf155().trim().equals("")){ errors.add("155udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 156:if( xrefForm.getUdf156()==null||xrefForm.getUdf156().trim().equals("")){ errors.add("156udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 157:if( xrefForm.getUdf157()==null||xrefForm.getUdf157().trim().equals("")){ errors.add("157udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 158:if( xrefForm.getUdf158()==null||xrefForm.getUdf158().trim().equals("")){ errors.add("158udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 159:if( xrefForm.getUdf159()==null||xrefForm.getUdf159().trim().equals("")){ errors.add("159udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 160:if( xrefForm.getUdf160()==null||xrefForm.getUdf160().trim().equals("")){ errors.add("160udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 161:if( xrefForm.getUdf161()==null||xrefForm.getUdf161().trim().equals("")){ errors.add("161udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 162:if( xrefForm.getUdf162()==null||xrefForm.getUdf162().trim().equals("")){ errors.add("162udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 163:if( xrefForm.getUdf163()==null||xrefForm.getUdf163().trim().equals("")){ errors.add("163udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 164:if( xrefForm.getUdf164()==null||xrefForm.getUdf164().trim().equals("")){ errors.add("164udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 165:if( xrefForm.getUdf165()==null||xrefForm.getUdf165().trim().equals("")){ errors.add("165udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 166:if( xrefForm.getUdf166()==null||xrefForm.getUdf166().trim().equals("")){ errors.add("166udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 167:if( xrefForm.getUdf167()==null||xrefForm.getUdf167().trim().equals("")){ errors.add("167udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 168:if( xrefForm.getUdf168()==null||xrefForm.getUdf168().trim().equals("")){ errors.add("168udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 169:if( xrefForm.getUdf169()==null||xrefForm.getUdf169().trim().equals("")){ errors.add("169udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 170:if( xrefForm.getUdf170()==null||xrefForm.getUdf170().trim().equals("")){ errors.add("170udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 171:if( xrefForm.getUdf171()==null||xrefForm.getUdf171().trim().equals("")){ errors.add("171udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 172:if( xrefForm.getUdf172()==null||xrefForm.getUdf172().trim().equals("")){ errors.add("172udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 173:if( xrefForm.getUdf173()==null||xrefForm.getUdf173().trim().equals("")){ errors.add("173udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 174:if( xrefForm.getUdf174()==null||xrefForm.getUdf174().trim().equals("")){ errors.add("174udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 175:if( xrefForm.getUdf175()==null||xrefForm.getUdf175().trim().equals("")){ errors.add("175udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 176:if( xrefForm.getUdf176()==null||xrefForm.getUdf176().trim().equals("")){ errors.add("176udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 177:if( xrefForm.getUdf177()==null||xrefForm.getUdf177().trim().equals("")){ errors.add("177udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 178:if( xrefForm.getUdf178()==null||xrefForm.getUdf178().trim().equals("")){ errors.add("178udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 179:if( xrefForm.getUdf179()==null||xrefForm.getUdf179().trim().equals("")){ errors.add("179udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 180:if( xrefForm.getUdf180()==null||xrefForm.getUdf180().trim().equals("")){ errors.add("180udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 181:if( xrefForm.getUdf181()==null||xrefForm.getUdf181().trim().equals("")){ errors.add("181udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 182:if( xrefForm.getUdf182()==null||xrefForm.getUdf182().trim().equals("")){ errors.add("182udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 183:if( xrefForm.getUdf183()==null||xrefForm.getUdf183().trim().equals("")){ errors.add("183udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 184:if( xrefForm.getUdf184()==null||xrefForm.getUdf184().trim().equals("")){ errors.add("184udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 185:if( xrefForm.getUdf185()==null||xrefForm.getUdf185().trim().equals("")){ errors.add("185udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 186:if( xrefForm.getUdf186()==null||xrefForm.getUdf186().trim().equals("")){ errors.add("186udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 187:if( xrefForm.getUdf187()==null||xrefForm.getUdf187().trim().equals("")){ errors.add("187udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 188:if( xrefForm.getUdf188()==null||xrefForm.getUdf188().trim().equals("")){ errors.add("188udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 189:if( xrefForm.getUdf189()==null||xrefForm.getUdf189().trim().equals("")){ errors.add("189udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 190:if( xrefForm.getUdf190()==null||xrefForm.getUdf190().trim().equals("")){ errors.add("190udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 191:if( xrefForm.getUdf191()==null||xrefForm.getUdf191().trim().equals("")){ errors.add("191udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 192:if( xrefForm.getUdf192()==null||xrefForm.getUdf192().trim().equals("")){ errors.add("192udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 193:if( xrefForm.getUdf193()==null||xrefForm.getUdf193().trim().equals("")){ errors.add("193udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 194:if( xrefForm.getUdf194()==null||xrefForm.getUdf194().trim().equals("")){ errors.add("194udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 195:if( xrefForm.getUdf195()==null||xrefForm.getUdf195().trim().equals("")){ errors.add("195udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 196:if( xrefForm.getUdf196()==null||xrefForm.getUdf196().trim().equals("")){ errors.add("196udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 197:if( xrefForm.getUdf197()==null||xrefForm.getUdf197().trim().equals("")){ errors.add("197udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 198:if( xrefForm.getUdf198()==null||xrefForm.getUdf198().trim().equals("")){ errors.add("198udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 199:if( xrefForm.getUdf199()==null||xrefForm.getUdf199().trim().equals("")){ errors.add("199udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 200:if( xrefForm.getUdf200()==null||xrefForm.getUdf200().trim().equals("")){ errors.add("200udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						
						case 201:if( xrefForm.getUdf201()==null||xrefForm.getUdf201().trim().equals("")){ errors.add("201udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 202:if( xrefForm.getUdf202()==null||xrefForm.getUdf202().trim().equals("")){ errors.add("202udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 203:if( xrefForm.getUdf203()==null||xrefForm.getUdf203().trim().equals("")){ errors.add("203udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 204:if( xrefForm.getUdf204()==null||xrefForm.getUdf204().trim().equals("")){ errors.add("204udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 205:if( xrefForm.getUdf205()==null||xrefForm.getUdf205().trim().equals("")){ errors.add("205udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 206:if( xrefForm.getUdf206()==null||xrefForm.getUdf206().trim().equals("")){ errors.add("206udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 207:if( xrefForm.getUdf207()==null||xrefForm.getUdf207().trim().equals("")){ errors.add("207udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 208:if( xrefForm.getUdf208()==null||xrefForm.getUdf208().trim().equals("")){ errors.add("208udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						
						case 209:if( xrefForm.getUdf209()==null||xrefForm.getUdf209().trim().equals("")){ errors.add("209udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 210:if( xrefForm.getUdf210()==null||xrefForm.getUdf210().trim().equals("")){ errors.add("210udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 211:if( xrefForm.getUdf211()==null||xrefForm.getUdf211().trim().equals("")){ errors.add("211udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 212:if( xrefForm.getUdf212()==null||xrefForm.getUdf212().trim().equals("")){ errors.add("212udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 213:if( xrefForm.getUdf213()==null||xrefForm.getUdf213().trim().equals("")){ errors.add("213udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 214:if( xrefForm.getUdf214()==null||xrefForm.getUdf214().trim().equals("")){ errors.add("214udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						case 215:if( xrefForm.getUdf215()==null||xrefForm.getUdf215().trim().equals("")){ errors.add("215udfError", new ActionMessage("error.string.mandatory.XRefUdf" , iUdf.getFieldName())); } break;
						
				default:
				break;
			}
		}
	}
	
	if(udfNumericList_2!=null){
		for(int udf=0;udf<udfNumericList_2.size();udf++){
			IUdf iUdf=(IUdf)udfNumericList_2.get(udf);
			switch (iUdf.getSequence()+115) {
				

			case 116:																				               
			if(!( null == xrefForm.getUdf116() ||xrefForm.getUdf116().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf116().toString().trim(),false,0,999999999999999.D))){ errors.add("116udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 117:																				               
			if(!( null == xrefForm.getUdf117() ||xrefForm.getUdf117().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf117().toString().trim(),false,0,999999999999999.D))){ errors.add("117udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 118:																				               
			if(!( null == xrefForm.getUdf118() ||xrefForm.getUdf118().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf118().toString().trim(),false,0,999999999999999.D))){ errors.add("118udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 119:																				               
			if(!( null == xrefForm.getUdf119() ||xrefForm.getUdf119().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf119().toString().trim(),false,0,999999999999999.D))){ errors.add("119udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 120:																				               
			if(!( null == xrefForm.getUdf120() ||xrefForm.getUdf120().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf120().toString().trim(),false,0,999999999999999.D))){ errors.add("120udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 121:																				               
			if(!( null == xrefForm.getUdf121() ||xrefForm.getUdf121().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf121().toString().trim(),false,0,999999999999999.D))){ errors.add("121udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 122:																				               
			if(!( null == xrefForm.getUdf122() ||xrefForm.getUdf122().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf122().toString().trim(),false,0,999999999999999.D))){ errors.add("122udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 123:																				               
			if(!( null == xrefForm.getUdf123() ||xrefForm.getUdf123().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf123().toString().trim(),false,0,999999999999999.D))){ errors.add("123udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 124:																				               
			if(!( null == xrefForm.getUdf124() ||xrefForm.getUdf124().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf124().toString().trim(),false,0,999999999999999.D))){ errors.add("124udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 125:																				               
			if(!( null == xrefForm.getUdf125() ||xrefForm.getUdf125().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf125().toString().trim(),false,0,999999999999999.D))){ errors.add("125udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 126:																				               
			if(!( null == xrefForm.getUdf126() ||xrefForm.getUdf126().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf126().toString().trim(),false,0,999999999999999.D))){ errors.add("126udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 127:																				               
			if(!( null == xrefForm.getUdf127() ||xrefForm.getUdf127().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf127().toString().trim(),false,0,999999999999999.D))){ errors.add("127udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 128:																				               
			if(!( null == xrefForm.getUdf128() ||xrefForm.getUdf128().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf128().toString().trim(),false,0,999999999999999.D))){ errors.add("128udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 129:																				               
			if(!( null == xrefForm.getUdf129() ||xrefForm.getUdf129().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf129().toString().trim(),false,0,999999999999999.D))){ errors.add("129udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 130:																				               
			if(!( null == xrefForm.getUdf130() ||xrefForm.getUdf130().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf130().toString().trim(),false,0,999999999999999.D))){ errors.add("130udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 131:																				               
			if(!( null == xrefForm.getUdf131() ||xrefForm.getUdf131().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf131().toString().trim(),false,0,999999999999999.D))){ errors.add("131udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 132:																				               
			if(!( null == xrefForm.getUdf132() ||xrefForm.getUdf132().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf132().toString().trim(),false,0,999999999999999.D))){ errors.add("132udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 133:																				               
			if(!( null == xrefForm.getUdf133() ||xrefForm.getUdf133().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf133().toString().trim(),false,0,999999999999999.D))){ errors.add("133udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 134:																				               
			if(!( null == xrefForm.getUdf134() ||xrefForm.getUdf134().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf134().toString().trim(),false,0,999999999999999.D))){ errors.add("134udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 135:																				               
			if(!( null == xrefForm.getUdf135() ||xrefForm.getUdf135().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf135().toString().trim(),false,0,999999999999999.D))){ errors.add("135udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 136:																				               
			if(!( null == xrefForm.getUdf136() ||xrefForm.getUdf136().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf136().toString().trim(),false,0,999999999999999.D))){ errors.add("136udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 137:																				               
			if(!( null == xrefForm.getUdf137() ||xrefForm.getUdf137().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf137().toString().trim(),false,0,999999999999999.D))){ errors.add("137udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 138:																				               
			if(!( null == xrefForm.getUdf138() ||xrefForm.getUdf138().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf138().toString().trim(),false,0,999999999999999.D))){ errors.add("138udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 139:																				               
			if(!( null == xrefForm.getUdf139() ||xrefForm.getUdf139().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf139().toString().trim(),false,0,999999999999999.D))){ errors.add("139udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 140:																				               
			if(!( null == xrefForm.getUdf140() ||xrefForm.getUdf140().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf140().toString().trim(),false,0,999999999999999.D))){ errors.add("140udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 141:																				               
			if(!( null == xrefForm.getUdf141() ||xrefForm.getUdf141().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf141().toString().trim(),false,0,999999999999999.D))){ errors.add("141udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 142:																				               
			if(!( null == xrefForm.getUdf142() ||xrefForm.getUdf142().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf142().toString().trim(),false,0,999999999999999.D))){ errors.add("142udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 143:																				               
			if(!( null == xrefForm.getUdf143() ||xrefForm.getUdf143().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf143().toString().trim(),false,0,999999999999999.D))){ errors.add("143udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 144:																				               
			if(!( null == xrefForm.getUdf144() ||xrefForm.getUdf144().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf144().toString().trim(),false,0,999999999999999.D))){ errors.add("144udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 145:																				               
			if(!( null == xrefForm.getUdf145() ||xrefForm.getUdf145().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf145().toString().trim(),false,0,999999999999999.D))){ errors.add("145udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 146:																				               
			if(!( null == xrefForm.getUdf146() ||xrefForm.getUdf146().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf146().toString().trim(),false,0,999999999999999.D))){ errors.add("146udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 147:																				               
			if(!( null == xrefForm.getUdf147() ||xrefForm.getUdf147().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf147().toString().trim(),false,0,999999999999999.D))){ errors.add("147udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 148:																				               
			if(!( null == xrefForm.getUdf148() ||xrefForm.getUdf148().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf148().toString().trim(),false,0,999999999999999.D))){ errors.add("148udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 149:																				               
			if(!( null == xrefForm.getUdf149() ||xrefForm.getUdf149().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf149().toString().trim(),false,0,999999999999999.D))){ errors.add("149udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 150:																				               
			if( !( null ==xrefForm.getUdf150() ||xrefForm.getUdf150().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf150().toString().trim(),false,0,999999999999999.D))){ errors.add("150udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 151:if(! ( null == xrefForm.getUdf151() ||xrefForm.getUdf151().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf151().toString().trim(),false,0,999999999999999.D))){ errors.add("151udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 152:if(!( null == xrefForm.getUdf152() ||xrefForm.getUdf152().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf152().toString().trim(),false,0,999999999999999.D))){ errors.add("152udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 153:if(!( null == xrefForm.getUdf153() ||xrefForm.getUdf153().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf153().toString().trim(),false,0,999999999999999.D))){ errors.add("153udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;	
			case 154:if(!( null == xrefForm.getUdf154() ||xrefForm.getUdf154().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf154().toString().trim(),false,0,999999999999999.D))){ errors.add("154udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 155:if(!( null == xrefForm.getUdf155() ||xrefForm.getUdf155().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf155().toString().trim(),false,0,999999999999999.D))){ errors.add("155udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 156:if(!( null == xrefForm.getUdf156() ||xrefForm.getUdf156().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf156().toString().trim(),false,0,999999999999999.D))){ errors.add("156udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 157:if(!( null == xrefForm.getUdf157() ||xrefForm.getUdf157().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf157().toString().trim(),false,0,999999999999999.D))){ errors.add("157udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 158:if(!( null == xrefForm.getUdf158() ||xrefForm.getUdf158().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf158().toString().trim(),false,0,999999999999999.D))){ errors.add("158udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 159:if(!( null == xrefForm.getUdf159() ||xrefForm.getUdf159().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf159().toString().trim(),false,0,999999999999999.D))){ errors.add("159udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 160:if(!( null == xrefForm.getUdf160() ||xrefForm.getUdf160().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf160().toString().trim(),false,0,999999999999999.D))){ errors.add("160udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 161:if(!( null == xrefForm.getUdf161() ||xrefForm.getUdf161().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf161().toString().trim(),false,0,999999999999999.D))){ errors.add("161udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 162:if(!( null == xrefForm.getUdf162() ||xrefForm.getUdf162().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf162().toString().trim(),false,0,999999999999999.D))){ errors.add("162udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 163:if(!( null == xrefForm.getUdf163() ||xrefForm.getUdf163().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf163().toString().trim(),false,0,999999999999999.D))){ errors.add("163udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 164:if(!( null == xrefForm.getUdf164() ||xrefForm.getUdf164().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf164().toString().trim(),false,0,999999999999999.D))){ errors.add("164udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 165:if(!( null == xrefForm.getUdf165() ||xrefForm.getUdf165().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf165().toString().trim(),false,0,999999999999999.D))){ errors.add("165udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 166:if(!( null == xrefForm.getUdf166() ||xrefForm.getUdf166().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf166().toString().trim(),false,0,999999999999999.D))){ errors.add("166udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 167:if(!( null == xrefForm.getUdf167() ||xrefForm.getUdf167().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf167().toString().trim(),false,0,999999999999999.D))){ errors.add("167udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 168:if(!( null == xrefForm.getUdf168() ||xrefForm.getUdf168().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf168().toString().trim(),false,0,999999999999999.D))){ errors.add("168udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 169:if(!( null == xrefForm.getUdf169() ||xrefForm.getUdf169().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf169().toString().trim(),false,0,999999999999999.D))){ errors.add("169udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 170:if(!( null == xrefForm.getUdf170() ||xrefForm.getUdf170().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf170().toString().trim(),false,0,999999999999999.D))){ errors.add("170udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 171:if(!( null == xrefForm.getUdf171() ||xrefForm.getUdf171().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf171().toString().trim(),false,0,999999999999999.D))){ errors.add("171udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 172:if(!( null == xrefForm.getUdf172() ||xrefForm.getUdf172().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf172().toString().trim(),false,0,999999999999999.D))){ errors.add("172udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 173:if(!( null == xrefForm.getUdf173() ||xrefForm.getUdf173().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf173().toString().trim(),false,0,999999999999999.D))){ errors.add("173udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 174:if(!( null == xrefForm.getUdf174() ||xrefForm.getUdf174().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf174().toString().trim(),false,0,999999999999999.D))){ errors.add("174udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 175:if(!( null == xrefForm.getUdf175() ||xrefForm.getUdf175().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf175().toString().trim(),false,0,999999999999999.D))){ errors.add("175udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 176:if(!( null == xrefForm.getUdf176() ||xrefForm.getUdf176().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf176().toString().trim(),false,0,999999999999999.D))){ errors.add("176udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 177:if(!( null == xrefForm.getUdf177() ||xrefForm.getUdf177().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf177().toString().trim(),false,0,999999999999999.D))){ errors.add("177udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 178:if(!( null == xrefForm.getUdf178() ||xrefForm.getUdf178().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf178().toString().trim(),false,0,999999999999999.D))){ errors.add("178udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 179:if(!( null == xrefForm.getUdf179() ||xrefForm.getUdf179().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf179().toString().trim(),false,0,999999999999999.D))){ errors.add("179udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 180:if(!( null == xrefForm.getUdf180() ||xrefForm.getUdf180().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf180().toString().trim(),false,0,999999999999999.D))){ errors.add("180udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 181:if(!( null == xrefForm.getUdf181() ||xrefForm.getUdf181().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf181().toString().trim(),false,0,999999999999999.D))){ errors.add("181udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 182:if(!( null == xrefForm.getUdf182() ||xrefForm.getUdf182().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf182().toString().trim(),false,0,999999999999999.D))){ errors.add("182udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 183:if(!( null == xrefForm.getUdf183() ||xrefForm.getUdf183().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf183().toString().trim(),false,0,999999999999999.D))){ errors.add("183udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 184:if(!( null == xrefForm.getUdf184() ||xrefForm.getUdf184().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf184().toString().trim(),false,0,999999999999999.D))){ errors.add("184udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 185:if(!( null == xrefForm.getUdf185() ||xrefForm.getUdf185().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf185().toString().trim(),false,0,999999999999999.D))){ errors.add("185udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 186:if(!( null == xrefForm.getUdf186() ||xrefForm.getUdf186().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf186().toString().trim(),false,0,999999999999999.D))){ errors.add("186udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 187:if(!( null == xrefForm.getUdf187() ||xrefForm.getUdf187().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf187().toString().trim(),false,0,999999999999999.D))){ errors.add("187udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 188:if(!( null == xrefForm.getUdf188() ||xrefForm.getUdf188().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf188().toString().trim(),false,0,999999999999999.D))){ errors.add("188udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 189:if(!( null == xrefForm.getUdf189() ||xrefForm.getUdf189().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf189().toString().trim(),false,0,999999999999999.D))){ errors.add("189udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 190:if(!( null == xrefForm.getUdf190() ||xrefForm.getUdf190().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf190().toString().trim(),false,0,999999999999999.D))){ errors.add("190udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 191:if(!( null == xrefForm.getUdf191() ||xrefForm.getUdf191().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf191().toString().trim(),false,0,999999999999999.D))){ errors.add("191udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 192:if(!( null == xrefForm.getUdf192() ||xrefForm.getUdf192().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf192().toString().trim(),false,0,999999999999999.D))){ errors.add("192udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 193:if(!( null == xrefForm.getUdf193() ||xrefForm.getUdf193().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf193().toString().trim(),false,0,999999999999999.D))){ errors.add("193udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 194:if(!( null == xrefForm.getUdf194() ||xrefForm.getUdf194().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf194().toString().trim(),false,0,999999999999999.D))){ errors.add("194udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 195:if(!( null == xrefForm.getUdf195() ||xrefForm.getUdf195().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf195().toString().trim(),false,0,999999999999999.D))){ errors.add("195udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 196:if(!( null == xrefForm.getUdf196() ||xrefForm.getUdf196().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf196().toString().trim(),false,0,999999999999999.D))){ errors.add("196udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 197:if(!( null == xrefForm.getUdf197() ||xrefForm.getUdf197().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf197().toString().trim(),false,0,999999999999999.D))){ errors.add("197udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 198:if(!( null == xrefForm.getUdf198() ||xrefForm.getUdf198().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf198().toString().trim(),false,0,999999999999999.D))){ errors.add("198udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 199:if(!( null == xrefForm.getUdf199() ||xrefForm.getUdf199().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf199().toString().trim(),false,0,999999999999999.D))){ errors.add("199udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 200:if( !( null  == xrefForm.getUdf200() ||xrefForm.getUdf200().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf200().toString().trim(),false,0,999999999999999.D))){ errors.add("200udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 201:if( !( null  == xrefForm.getUdf201() ||xrefForm.getUdf201().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf201().toString().trim(),false,0,999999999999999.D))){ errors.add("201udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 202:if( !( null  == xrefForm.getUdf202() ||xrefForm.getUdf202().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf202().toString().trim(),false,0,999999999999999.D))){ errors.add("202udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 203:if( !( null  == xrefForm.getUdf203() ||xrefForm.getUdf203().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf203().toString().trim(),false,0,999999999999999.D))){ errors.add("203udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 204:if( !( null  == xrefForm.getUdf204() ||xrefForm.getUdf204().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf204().toString().trim(),false,0,999999999999999.D))){ errors.add("204udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 205:if( !( null  == xrefForm.getUdf205() ||xrefForm.getUdf205().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf205().toString().trim(),false,0,999999999999999.D))){ errors.add("205udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 206:if( !( null  == xrefForm.getUdf206() ||xrefForm.getUdf206().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf206().toString().trim(),false,0,999999999999999.D))){ errors.add("206udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 207:if( !( null  == xrefForm.getUdf207() ||xrefForm.getUdf207().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf207().toString().trim(),false,0,999999999999999.D))){ errors.add("207udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 208:if( !( null  == xrefForm.getUdf208() ||xrefForm.getUdf208().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf208().toString().trim(),false,0,999999999999999.D))){ errors.add("208udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 209:if( !( null  == xrefForm.getUdf209() ||xrefForm.getUdf209().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf209().toString().trim(),false,0,999999999999999.D))){ errors.add("209udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 210:if( !( null  == xrefForm.getUdf210() ||xrefForm.getUdf210().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf210().toString().trim(),false,0,999999999999999.D))){ errors.add("210udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 211:if( !( null  == xrefForm.getUdf211() ||xrefForm.getUdf211().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf211().toString().trim(),false,0,999999999999999.D))){ errors.add("211udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 212:if( !( null  == xrefForm.getUdf212() ||xrefForm.getUdf212().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf212().toString().trim(),false,0,999999999999999.D))){ errors.add("212udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 213:if( !( null  == xrefForm.getUdf213() ||xrefForm.getUdf213().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf213().toString().trim(),false,0,999999999999999.D))){ errors.add("213udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 214:if( !( null  == xrefForm.getUdf214() ||xrefForm.getUdf214().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf214().toString().trim(),false,0,999999999999999.D))){ errors.add("214udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
			case 215:if( !( null  == xrefForm.getUdf215() ||xrefForm.getUdf215().trim().equals(""))){ if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(xrefForm.getUdf215().toString().trim(),false,0,999999999999999.D))){ errors.add("215udfError", new ActionMessage("error.integer.udf" , iUdf.getFieldName())); } } break;
		
			
				default:
					break;
			}
		}
	}	
}
	public static ActionErrors validateLimitSecMap(ActionForm aForm, Locale locale) {
		return null;
	}
	
	public static boolean isNumeric(String s){
		if(null!=s && !s.isEmpty()){
	    String pattern= "^[0-9]*$";
	        if(s.matches(pattern)){
	            return true;
	        }
	        
		}
		
	    return false;   
	}
	
	public static boolean isAlphaNumeric(String s){
		if(null!=s && !s.isEmpty()){
	    String pattern= "^[a-zA-Z0-9]*$";
	        if(s.matches(pattern)){
	            return true;
	        }
	        
		}
		
	    return false;   
	}
	
	public static String validateScmFlag(String s){
		String borrowerScmFlag = null;
		if(null!=s && !s.isEmpty()){
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			try {
				borrowerScmFlag = (String) proxy.getBorrowerScmFlag(s);
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			}
		
	    return borrowerScmFlag;   
	}

	public static ActionErrors validateXRefLine(XRefDetailForm xrefForm, Locale default1, ActionErrors errors) {
		// TODO Auto-generated method stub
		String errorCode = null;
		String failReason="";
		//ActionErrors errors = new ActionErrors();
		
		FileUploadJdbcImpl fileUpload=new FileUploadJdbcImpl();
		OBReleaselinedetailsFile obj = new OBReleaselinedetailsFile();
		
		if (null != xrefForm.getFacilitySystemID() && ! xrefForm.getFacilitySystemID().trim().isEmpty()) {
			obj.setSystemID(( xrefForm.getFacilitySystemID().trim()));
		} else {
			obj.setSystemID(""); 
		}
		if (null != xrefForm.getLineNo() && ! xrefForm.getLineNo().trim().isEmpty()) {
			obj.setLineNo(( xrefForm.getLineNo().trim()));
		} else {
			obj.setLineNo(""); 
		}
		if (null != xrefForm.getSerialNo() && ! xrefForm.getSerialNo().trim().isEmpty()) {
			obj.setSerialNo(( xrefForm.getSerialNo().trim()));
		} else {
			obj.setSerialNo(""); 
		}
		if (null != xrefForm.getLiabBranch() && ! xrefForm.getLiabBranch().trim().isEmpty()) {
			obj.setLiabBranch(( xrefForm.getLiabBranch().trim()));
		} else {
			obj.setLiabBranch(""); 
		}
		
		int	facilityIdCount=0;
		facilityIdCount=fileUpload.getReleaselinedetailsFacilityIdCount(obj.getSystemID());
		
		int serialIdCount=0;
		serialIdCount=fileUpload.getReleaselinedetailsSerialNoCount(obj.getSerialNo());
		
		int lineNoCount=0;
		lineNoCount=fileUpload.getReleaselinedetailsLineNoCount(obj.getLineNo());
		
		int liabBranchCount=0;
		liabBranchCount=fileUpload.getReleaselinedetailsLiabBranchCount(obj.getLiabBranch());
		
		String rldStatus="";
		rldStatus=fileUpload.getReleaselinedetailsStatus(obj);
		
		String limitDetailsStatus="";
		limitDetailsStatus=fileUpload.getReleaselinedetailsLimitDetailsStatus(obj);
		
		String limitTrxStatus="";
		limitTrxStatus=fileUpload.getFacilityTransactionStatus(obj);
		
		String partyStatus="";
		partyStatus=fileUpload.getReleaselinedetailsPartyStatus(obj);
		
		String partyName="";
		partyName=fileUpload.getReleaselinedetailsPartyName(obj);
		
		if(facilityIdCount > 0 && serialIdCount > 0 && lineNoCount > 0 && liabBranchCount > 0)
		{

			if(!("PENDING".equalsIgnoreCase(rldStatus)) && !("".equalsIgnoreCase(rldStatus))) {

				if(!("PENDING".equalsIgnoreCase(limitDetailsStatus)) && !("".equalsIgnoreCase(limitDetailsStatus))) {

					if(!("INACTIVE".equalsIgnoreCase(partyStatus)) && !("".equalsIgnoreCase(partyStatus))) 
					{
						if(!("PENDING_UPDATE".equalsIgnoreCase(limitTrxStatus))) {

						}
						else {

							failReason=" Transaction is in pending for authorisation of respective Facility for Party '"+partyName+"'.";

						}

					}

					else {

						if(partyStatus=="") {
							failReason="Party not found.";
						}else {
							failReason="'"+partyName+"' Party is not ACTIVE.";
						}

					}


				}
				else {

					if(limitDetailsStatus=="") {
						failReason="Party not found in Limit Detalis.";
					}else {
						failReason="Limit Detalis is PENDING of Party '"+partyName+"'.";
					}

				}

			}
			else {

				if(rldStatus=="") {
					failReason="Record Not Found Against(System ID,Serial Number,Line Number,Liab Branch) this combination.";
				}
				else {
					failReason="Release Line Detalis is PENDING of Party '"+partyName+"'.";
				}

			}

		}
		else {
			if(facilityIdCount <= 0) {
				failReason="Failed due to System Id is not exist.";
			}
			if(serialIdCount <= 0) {
				failReason=failReason+"Failed due to Serial Number is not exist.";
			}
			if(lineNoCount <= 0) {
				failReason=failReason+"Failed due to Line Number is not exist.";
			}
			if(liabBranchCount <= 0) {
				failReason=failReason+"Failed due to Liab Branch is not exist.";
			}
			//errors.add("projectLoan", new ActionMessage("error.projectLoanFinance.mandatory"));

		}
		if(!"".equals(failReason))
		{
			errors.add("LineList", new ActionMessage(failReason));
		}
		
		return errors;
	}
	
	 public static boolean isValidDate(String inDate) {
		 SimpleDateFormat ddMMMyyyyDateformat = new SimpleDateFormat("dd/MMM/yyyy");
		 ddMMMyyyyDateformat.setLenient(false);
	        try {
	        	ddMMMyyyyDateformat.parse(inDate.trim());
	        	if(inDate.length() == 11)
		        	return true;
	        } catch (ParseException pe) {
	            return false;
	        }
	        return false;
	    }
	}
