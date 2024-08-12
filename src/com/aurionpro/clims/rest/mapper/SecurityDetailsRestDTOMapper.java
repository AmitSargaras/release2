package com.aurionpro.clims.rest.mapper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.aurionpro.clims.rest.dto.ABEnquiryResponseDTO;
import com.aurionpro.clims.rest.dto.ABSpecEnqDetailsResponseDTO;
import com.aurionpro.clims.rest.dto.ABSpecRestRequestDTO;
import com.aurionpro.clims.rest.dto.AddtionalDocumentFacilityDetailsRestDTO;
import com.aurionpro.clims.rest.dto.CollateralDeleteEnquiryRestRequestDTO;
import com.aurionpro.clims.rest.dto.CollateralDetailslRestRequestDTO;
import com.aurionpro.clims.rest.dto.CollateralEnqiryDetailsResponseDTO;
import com.aurionpro.clims.rest.dto.CollateralRestRequestDTO;
import com.aurionpro.clims.rest.dto.InsuranceDetailRestResponseDTO;
import com.aurionpro.clims.rest.dto.InsurancePolicyRestRequestDTO;
import com.aurionpro.clims.rest.dto.PropertyRestRequestDTO;
import com.aurionpro.clims.rest.dto.StockLineRestRequestDTO;
import com.aurionpro.clims.rest.dto.StockRestRequestDTO;
import com.integrosys.base.businfra.contact.OBAddress;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.collateral.bus.CollateralDAO;
import com.integrosys.cms.app.collateral.bus.IAddtionalDocumentFacilityDetails;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.OBAddtionalDocumentFacilityDetails;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.OBInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.OBLimitCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.ISpecificChargeAircraft;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.OBSpecificChargeAircraft;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.OBGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.OBMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.IMarketableEquityLineDetail;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.OBMarketableEquityLineDetail;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedlocal.IOtherListedLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedlocal.OBOtherListedLocal;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.OBPropertyCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.collateral.CollateralRestForm;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;
import com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftForm;
import com.integrosys.cms.ui.collateral.guarantees.gtecorp3rd.GteCorp3rdForm;
import com.integrosys.cms.ui.collateral.guarantees.gtegovt.GteGovtForm;
import com.integrosys.cms.ui.collateral.guarantees.gteindiv.GteIndivForm;
import com.integrosys.cms.ui.collateral.insurancepolicy.InsurancePolicyForm;
import com.integrosys.cms.ui.collateral.marketablesec.PortItemForm;
import com.integrosys.cms.ui.collateral.marketablesec.linedetail.MarketableEquityLineDetailForm;
import com.integrosys.cms.ui.collateral.property.PropertyRestForm;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

public class SecurityDetailsRestDTOMapper {
	
	final static String COLLETERAL_CODE="COL0000139";
	SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat ddMMyyDateformat = new SimpleDateFormat("dd-MM-yy");
	SimpleDateFormat cautionlistDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat ddMMMyyyyDateformat = new SimpleDateFormat("dd/MMM/yyyy");
	ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
	IOtherBankProxyManager otherBankProxyManager = (IOtherBankProxyManager)BeanHouse.get("otherBankProxyManager");
	
	CollateralDAO pinDao = new CollateralDAO(); 
	public CollateralRestRequestDTO getRequestDTOWithActualValues(
			CollateralRestRequestDTO collateralreq,String collateralCategory,String cersai, String ColSubTypeId, ICollateralTrxValue itrxValue) {
		 Date currDate = DateUtil.getDate();
		 Locale locale = Locale.getDefault();
		MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();
		List<CollateralDetailslRestRequestDTO> secBodyReqList = new LinkedList<CollateralDetailslRestRequestDTO>();
		CollateralDetailslRestRequestDTO reqobj = collateralreq.getBodyDetails().get(0);
		 if(null != collateralreq.getBodyDetails().get(0).getSecPriority() && !collateralreq.getBodyDetails().get(0).getSecPriority().trim().isEmpty())
		    {
			 if(!("Y".equalsIgnoreCase(collateralreq.getBodyDetails().get(0).getSecPriority().trim()) || "N".equalsIgnoreCase(collateralreq.getBodyDetails().get(0).getSecPriority().trim()))){
					errors.add("securityPriority",new ActionMessage("error.securityPriority.invalid"));
				}
		    }
		 else{
				errors.add("securityPriority",new ActionMessage("error.securityPriority.mandatory"));
			}
	
		 if(null != collateralreq.getBodyDetails().get(0).getMonitorProcess() && !collateralreq.getBodyDetails().get(0).getMonitorProcess().trim().isEmpty()){
	    		if(collateralreq.getBodyDetails().get(0).getMonitorProcess().equals("Y"))
	    			 reqobj.setMonitorProcess("Y");
	    		else if(collateralreq.getBodyDetails().get(0).getMonitorProcess().equals("N"))
	    		{
	    			reqobj.setMonitorProcess("N");
	    			reqobj.setMonitorFrequency("");
	    		}
	    		 else
	    		 {
	    			 errors.add("monitorProcess", new ActionMessage("error.invalid.monitorProcess"));
	    		 }
	    	}
		 else
		 {
			 errors.add("monitorProcess", new ActionMessage("error.string.mandatory"));
		 }
		 
		 if(null != collateralreq.getBodyDetails().get(0).getMonitorProcess() && "Y".equals(collateralreq.getBodyDetails().get(0).getMonitorProcess())){ 
		 if(null != collateralreq.getBodyDetails().get(0).getMonitorFrequency() && !collateralreq.getBodyDetails().get(0).getMonitorFrequency().trim().isEmpty())
		    {
			 try {
				 Object obj = masterObj.getObjectByEntityName("entryCode", collateralreq.getBodyDetails().get(0).getMonitorFrequency().trim(),"monitorFrequency",errors,"FREQUENCY");
				 if(!(obj instanceof ActionErrors)){
					 reqobj.setMonitorFrequency(((ICommonCodeEntry)obj).getEntryCode());
					}
				 else
				 {
					 errors.add("monitorFrequency",new ActionMessage("error.invalid.monitorFrequency")); 
				 }
			 }
			 catch(Exception e){
					DefaultLogger.error(this, "monitorFrequency"+e.getMessage());
					errors.add("monitorFrequency",new ActionMessage("error.invalid.monitorFrequency"));
				}
			
		    }
		 else
		 {
			 errors.add("monitorFrequency",new ActionMessage("error.string.mandatory"));
		 }
		 }
		 
		 if(collateralreq.getBodyDetails().get(0).getSecurityOrganization() !=null && !collateralreq.getBodyDetails().get(0).getSecurityOrganization().trim().isEmpty()){
			 String secLocFromSecurity = collateralDAO.getSecLocFromSecurity(collateralreq.getBodyDetails().get(0).getSecurityId());

			 if(null != secLocFromSecurity)
			 {
			 boolean checkSecBranchCount = collateralDAO.checkSecBranchCount(collateralreq.getBodyDetails().get(0).getSecurityOrganization(),secLocFromSecurity);
			 if(checkSecBranchCount)
				 reqobj.setSecurityOrganization(collateralreq.getBodyDetails().get(0).getSecurityOrganization());
			 else
				 errors.add("securityOrganization",new ActionMessage("error.invalid.field.value"));
			 }
			}else{
				if("PT701".equalsIgnoreCase(ColSubTypeId))
				errors.add("securityOrganization",new ActionMessage("error.securityOrganization.mandatory"));
			}
		 
		 if(collateralreq.getBodyDetails().get(0).getCollateralCurrency() !=null && !collateralreq.getBodyDetails().get(0).getCollateralCurrency().trim().isEmpty()){
				Object obj = masterObj.getObjectforMasterRelatedCode("actualForexFeedEntry", collateralreq.getBodyDetails().get(0).getCollateralCurrency().trim(),"collateralCurrency",errors);
				if(!(obj instanceof ActionErrors)){
					reqobj.setCollateralCurrency(((IForexFeedEntry)obj).getCurrencyIsoCode());
				}
				else
				 {
					 errors.add("collateralCurrency",new ActionMessage("error.invalid.field.value")); 
				 }
				if(null != collateralreq.getBodyDetails().get(0).getExchangeRateINR() && !collateralreq.getBodyDetails().get(0).getExchangeRateINR().trim().isEmpty())
				{
				if(!"INR".equals(collateralreq.getBodyDetails().get(0).getCollateralCurrency())) {
					if (!(Validator.checkNumber(collateralreq.getBodyDetails().get(0).getExchangeRateINR(), true, 0, 9999999999.9999)).equals(Validator.ERROR_NONE)) {
						errors.add("exchangeRateINR",new ActionMessage("error.invalid.field.value")); 
					}
				}
				}
			}else{
				errors.add("collateralCurrency",new ActionMessage("error.collateralCurrency.mandatory"));
			}
		 
		 if("AB109".equalsIgnoreCase(ColSubTypeId) || "PT701".equalsIgnoreCase(ColSubTypeId)) {
		 if(null != collateralreq.getBodyDetails().get(0).getNextValDate() && !collateralreq.getBodyDetails().get(0).getNextValDate().trim().isEmpty()){
				
			 if(isValidDate(collateralreq.getBodyDetails().get(0).getNextValDate().trim()))
				 reqobj.setNextValDate(collateralreq.getBodyDetails().get(0).getNextValDate().trim());
			 else
				 errors.add("nextValDate",new ActionMessage("error.date.format"));
			}else{
				errors.add("nextValDate",new ActionMessage("error.string.mandatory"));
			}
		 
		 if(null != collateralreq.getBodyDetails().get(0).getTypeOfChange() && !"".equals(collateralreq.getBodyDetails().get(0).getTypeOfChange()))
		    {
			 try {
				 Object obj = masterObj.getObjectByEntityName("entryCode", collateralreq.getBodyDetails().get(0).getTypeOfChange().trim(),"typeOfChange",errors,"TYPE_CHARGE");
				 if(!(obj instanceof ActionErrors)){
					 reqobj.setTypeOfChange(((ICommonCodeEntry)obj).getEntryCode());
					}
				 else
				 {
					 errors.add("typeOfChange",new ActionMessage("error.invalid.field.value")); 
				 }
			 }
			 catch(Exception e){
					DefaultLogger.error(this, "typeOfChange - "+e.getMessage());
					errors.add("typeOfChange",new ActionMessage("error.invalid.typeOfChange"));
				}
		    }
		 else
		 {
			 errors.add("typeOfChange",new ActionMessage("error.string.mandatory"));
		 }
		 if(null != collateralreq.getBodyDetails().get(0).getTypeOfChange() && "SECOND_CHARGE".equals(collateralreq.getBodyDetails().get(0).getTypeOfChange())){ 
			 if(null != collateralreq.getBodyDetails().get(0).getOtherBankCharge() && !collateralreq.getBodyDetails().get(0).getOtherBankCharge().trim().isEmpty())
			    {
					 if(ASSTValidator.isNumeric(collateralreq.getBodyDetails().get(0).getOtherBankCharge()) && collateralreq.getBodyDetails().get(0).getOtherBankCharge().length() <= 5)
						 reqobj.setOtherBankCharge(collateralreq.getBodyDetails().get(0).getOtherBankCharge().trim());
					 else
						 errors.add("otherBankCharge",new ActionMessage("error.invalid.field.value"));
						 
				}
			else
				 {
					 errors.add("otherBankCharge",new ActionMessage("error.string.mandatory"));
				 }
			 }
		 else 
			 {
			 reqobj.setOtherBankCharge("");
			 }
		 if(null != collateralreq.getBodyDetails().get(0).getCommonRevalFreq() && !collateralreq.getBodyDetails().get(0).getCommonRevalFreq().trim().isEmpty())
		    {
			 try {
				 Object obj = masterObj.getObjectByEntityName("entryCode", collateralreq.getBodyDetails().get(0).getCommonRevalFreq().trim(),"commonRevalFreq",errors,"FREQUENCY");
				 if(!(obj instanceof ActionErrors)){
					 reqobj.setCommonRevalFreq(((ICommonCodeEntry)obj).getEntryCode());
					}
				 else
				 {
					 errors.add("commonRevalFreq",new ActionMessage("error.invalid.field.value")); 
				 }
			 }
			 catch(Exception e){
					DefaultLogger.error(this, "commonRevalFreq"+e.getMessage());
					errors.add("commonRevalFreq",new ActionMessage("error.invalid.commonRevalFreq"));
				}
			
		    }
		 else
		 {
			 errors.add("commonRevalFreq",new ActionMessage("error.string.mandatory"));
		 }
		 if("AB109".equalsIgnoreCase(ColSubTypeId)) {
			 
		 if(null != collateralreq.getBodyDetails().get(0).getValuationDate() && !collateralreq.getBodyDetails().get(0).getValuationDate().trim().isEmpty()){
			 if(isValidDate(collateralreq.getBodyDetails().get(0).getValuationDate().trim()))
				 reqobj.setValuationDate(collateralreq.getBodyDetails().get(0).getValuationDate().trim());
			 else
				 errors.add("valuationDate",new ActionMessage("error.date.format"));
			}else{
				errors.add("valuationDate",new ActionMessage("error.string.mandatory"));
			}}
		 
		 
		 
		 }
		 
		 
		
		 String CustName="";
		 String CustEntity="";
		 String[]  nameAndEntity = collateralDAO.getCustomerNameAndEntity(collateralreq.getBodyDetails().get(0).getSecurityId());
			if(null != nameAndEntity && nameAndEntity.length > 0)
			{
				CustName=nameAndEntity[1] ;
				CustEntity=nameAndEntity[0] ;
				
			}
		 
		 boolean isCersaiThirdPartyFieldsEnabled= false;
		 boolean isCersaiEnabled = false;
		 
			 if(null != cersai && "Y".equalsIgnoreCase(cersai))
				 isCersaiEnabled= true;
			 if(null != collateralCategory && "IMMOVABLE".equalsIgnoreCase(collateralCategory))
				 isCersaiThirdPartyFieldsEnabled =true;
			if(null != cersai && !cersai.trim().isEmpty())
			 {
				 reqobj.setCersaiApplicableInd(cersai); 
			 }
			 if(null != collateralCategory && !collateralCategory.trim().isEmpty())
			 {
				 reqobj.setCollateralCategory(collateralCategory); 
			 }
		
		 if(isCersaiEnabled)
		 {
			 if(isCersaiThirdPartyFieldsEnabled)
			 {
				 if(null != collateralreq.getBodyDetails().get(0).getSecurityOwnership() && !collateralreq.getBodyDetails().get(0).getSecurityOwnership().trim().isEmpty())
				 {
					 try {
						 Object obj = masterObj.getObjectByEntityName("entryCode", collateralreq.getBodyDetails().get(0).getSecurityOwnership().trim(),"securityOwnership",errors,"SECURITY_OWNERSHIP");
						 if(!(obj instanceof ActionErrors)){
							 reqobj.setSecurityOwnership(((ICommonCodeEntry)obj).getEntryCode());
							}
						 else
						 {
							 errors.add("securityOwnership",new ActionMessage("error.invalid.field.value")); 
						 }
					 }
					 catch(Exception e){
							DefaultLogger.error(this, "securityOwnership - "+e.getMessage());
							errors.add("securityOwnership",new ActionMessage("error.invalid.securityOwnership"));
						}
				 }
				 else
				 {
					 errors.add("securityOwnership",new ActionMessage("error.mandatory.securityOwnership"));
				 }
				 
				if (null != collateralreq.getBodyDetails().get(0)
						.getSecurityOwnership()
						&& !collateralreq.getBodyDetails().get(0)
								.getSecurityOwnership().trim().isEmpty())
				 {
					if ("THIRD_PARTY".equalsIgnoreCase(collateralreq.getBodyDetails().get(0).getSecurityOwnership())){
						if(null == collateralreq.getBodyDetails().get(0).getOwnerOfProperty()
								|| collateralreq.getBodyDetails().get(0).getOwnerOfProperty().trim().isEmpty()){
							errors.add("ownerOfProperty",new ActionMessage("error.mandatory.ownerOfProperty"));
							}else if(null != collateralreq.getBodyDetails().get(0).getOwnerOfProperty()
										&& !collateralreq.getBodyDetails().get(0).getOwnerOfProperty().trim().isEmpty()
										&& collateralreq.getBodyDetails().get(0).getOwnerOfProperty().trim().length() > 50) {
									errors.add("ownerOfProperty",new ActionMessage("error.field.length.exceeded", "50"));
								}else{
									reqobj.setOwnerOfProperty(collateralreq.getBodyDetails().get(0).getOwnerOfProperty().trim());
									}
								
								if(null != collateralreq.getBodyDetails().get(0)
										.getCinForThirdParty()
								&& !collateralreq.getBodyDetails().get(0)
										.getCinForThirdParty().trim().isEmpty())	
								{
									if(collateralreq.getBodyDetails().get(0).getCinForThirdParty().length() > 30)
									 {
										 errors.add("cinForThirdParty",new ActionMessage("error.field.length.exceeded", "30"));
									 }
									 else
									 {
									 reqobj.setCinForThirdParty(collateralreq.getBodyDetails().get(0).getCinForThirdParty().trim());
									 }
								}
								else if(null != collateralreq.getBodyDetails().get(0)
										.getCinForThirdParty()
								&& collateralreq.getBodyDetails().get(0)
										.getCinForThirdParty().trim().isEmpty())
								{
									reqobj.setCinForThirdParty("");
								}
								
								if(null != collateralreq.getBodyDetails().get(0)
										.getThirdPartyEntity()
								&& !collateralreq.getBodyDetails().get(0)
										.getThirdPartyEntity().trim().isEmpty())	
								{
									try {
										 Object obj = masterObj.getObjectByEntityName("entryCode", collateralreq.getBodyDetails().get(0).getThirdPartyEntity().trim(),"thirdPartyEntity",errors,"Entity");
										 if(!(obj instanceof ActionErrors)){
											 reqobj.setThirdPartyEntity(((ICommonCodeEntry)obj).getEntryCode());
										 } else {
											 errors.add("thirdPartyEntity",new ActionMessage("error.invalid.thirdPartyEntity"));
											}
									 }
									 catch(Exception e){
											DefaultLogger.error(this, "thirdPartyEntity - "+e.getMessage());
											errors.add("thirdPartyEntity",new ActionMessage("error.invalid.thirdPartyEntity"));
										}
								 }
								else
								{
									errors.add("thirdPartyEntity",new ActionMessage("error.mandatory.thirdPartyEntity"));
								}
								

								if (null != collateralreq.getBodyDetails().get(0).getThirdPartyAddress()
										&& !collateralreq.getBodyDetails().get(0).getThirdPartyAddress().trim().isEmpty()) {
									
									if(collateralreq.getBodyDetails().get(0).getThirdPartyAddress().length() > 150) {
										errors.add("thirdPartyAddress",new ActionMessage("error.field.length.exceeded", "150"));
									}else{
										reqobj.setThirdPartyAddress(collateralreq.getBodyDetails().get(0).getThirdPartyAddress().trim());
									}
								} else {
									errors.add("thirdPartyAddress", new ActionMessage("error.mandatory.thirdPartyAddress"));
								}

								if (null != collateralreq.getBodyDetails().get(0).getThirdPartyState()
										&& !collateralreq.getBodyDetails().get(0).getThirdPartyState().trim().isEmpty()) {

									Object obj = masterObj.getObjectforMasterRelatedCode("actualState",
											collateralreq.getBodyDetails().get(0).getThirdPartyState().trim(), "thirdPartyState",
											errors);
									if (!(obj instanceof ActionErrors)) {
										reqobj.setThirdPartyState(((IState) obj).getIdState() + "");
										collateralreq.getBodyDetails().get(0)
												.setThirdPartyState(Long.toString(((IState) obj).getIdState()));
									} else {
										errors.add("thirdPartyState", new ActionMessage("error.invalid.thirdPartyState"));
									}
								} else {
									errors.add("thirdPartyState", new ActionMessage("error.mandatory.thirdPartyState"));
								}

								if (null != collateralreq.getBodyDetails().get(0).getThirdPartyCity()
										&& !collateralreq.getBodyDetails().get(0).getThirdPartyCity().trim().isEmpty()) {

									Object obj = masterObj.getObjectforMasterRelatedCode("actualCity",
											collateralreq.getBodyDetails().get(0).getThirdPartyCity().trim(), "thirdPartyCity", errors);
									if (!(obj instanceof ActionErrors)) {
										// check if given city is present in list of state
										if (collateralreq.getBodyDetails().get(0).getThirdPartyState().trim()
												.equalsIgnoreCase(Long.toString(((ICity) obj).getStateId().getIdState()))) {
											collateralreq.getBodyDetails().get(0)
													.setThirdPartyCity(Long.toString(((ICity) obj).getIdCity()));
											reqobj.setThirdPartyCity(((ICity) obj).getIdCity() + "");
										} else {
											System.out.println("Given City is not Present in the state list of State");
											errors.add("thirdPartyCity", new ActionMessage("error.invalid.field.value"));
										}

									} else {
										errors.add("thirdPartyCity", new ActionMessage("error.invalid.thirdPartyCity"));
									}
								} else {
									errors.add("thirdPartyCity", new ActionMessage("error.mandatory.thirdPartyCity"));
								}

								if (null != collateralreq.getBodyDetails().get(0).getThirdPartyPincode()
										&& !collateralreq.getBodyDetails().get(0).getThirdPartyPincode().trim().isEmpty()) {
									
									if(!StringUtils.isNumeric(collateralreq.getBodyDetails().get(0).getThirdPartyPincode())){
										errors.add("thirdPartyPincode", new ActionMessage("error.string.format"));
									}else {
										HashMap<String, String> pincodeMap = null;
										try {
										pincodeMap = (HashMap<String, String>) pinDao.getActiveStatePinCodeMap1();
										String pincodesStr = UIUtil.getDelimitedStringFromMap(pincodeMap, ",", "=");
										Map<String, String> statePincodeMap = UIUtil.getMapFromDelimitedString(pincodesStr, ",", "=");
										 if(null != statePincodeMap && !statePincodeMap.isEmpty()) {
											 String selectedStatePincode = statePincodeMap.get(collateralreq.getBodyDetails().get(0).getThirdPartyState());
											 if(null != selectedStatePincode && !collateralreq.getBodyDetails().get(0).getThirdPartyPincode().trim().startsWith(selectedStatePincode)) {
												 errors.add("thirdPartyPincode", new ActionMessage("error.pincode.incorrect"));
											 }
										 }
										}catch(Exception e) {
											System.out.println("Exception for thirdPartyPincode in securityrestDTOMapper.java..=> e=>"+e);
										}
									}
									reqobj.setThirdPartyPincode(collateralreq.getBodyDetails().get(0).getThirdPartyPincode().trim());
								} else {
									errors.add("thirdPartyPincode", new ActionMessage("error.mandatory.thirdPartyPincode"));
								}
							
							}
					else if("BORROWER".equalsIgnoreCase(collateralreq.getBodyDetails().get(0).getSecurityOwnership())) 
					{
						reqobj.setOwnerOfProperty(CustName);
						reqobj.setThirdPartyEntity(CustEntity);
						reqobj.setCinForThirdParty("");
						reqobj.setThirdPartyAddress("");
						reqobj.setThirdPartyState("");
						reqobj.setThirdPartyCity("");
						reqobj.setThirdPartyPincode("");
					}
				 }
			 }
			 else
			 {
				 reqobj.setSecurityOwnership("");
				 reqobj.setOwnerOfProperty("");
				 reqobj.setThirdPartyEntity("");
				 reqobj.setCinForThirdParty("");
				 reqobj.setThirdPartyAddress("");
				 reqobj.setThirdPartyState("");
				 reqobj.setThirdPartyCity("");
				 reqobj.setThirdPartyPincode("");
			 }
			 
			if ("PT701".equalsIgnoreCase(ColSubTypeId)) {
				if (null != collateralreq.getBodyDetails().get(0).getPropertyDetailsList().get(0)
						.getMortgageCreExtDateAdd()
						&& !collateralreq.getBodyDetails().get(0).getPropertyDetailsList().get(0)
								.getMortgageCreExtDateAdd().isEmpty()
						&& "Y".equals(collateralreq.getBodyDetails().get(0).getPropertyDetailsList().get(0)
								.getMortgageCreExtDateAdd())) {
					if (null != collateralreq.getBodyDetails().get(0).getCersaiTransactionRefNumber() && !collateralreq
							.getBodyDetails().get(0).getCersaiTransactionRefNumber().trim().isEmpty()) {
						if (collateralreq.getBodyDetails().get(0).getCersaiTransactionRefNumber().length() > 30) {
							errors.add("cersaiTransactionRefNumber",
									new ActionMessage("error.field.length.exceeded", "30"));
						} else {
							reqobj.setCersaiTransactionRefNumber(
									collateralreq.getBodyDetails().get(0).getCersaiTransactionRefNumber().trim());
						}
					} else {
						reqobj.setCersaiTransactionRefNumber("");
					}
				
					 if (null != collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration()
								&& !collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration().trim().isEmpty()) {
							if (isValidDate(collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration())) {
								Date dateOfCersaiReg = DateUtil.convertDate(locale, collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration());
								if (dateOfCersaiReg.after(currDate)) 
									errors.add("dateOfCersaiRegisteration", new ActionMessage("error.future.date"));
								else
									reqobj.setDateOfCersaiRegisteration(collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration());
								} else {
								errors.add("dateOfCersaiRegisteration", new ActionMessage("error.date.format"));
								}
							} else {
							reqobj.setDateOfCersaiRegisteration("");
							}
						 
						 if(null != collateralreq.getBodyDetails().get(0).getCersaiId() && !collateralreq.getBodyDetails().get(0).getCersaiId().trim().isEmpty())
						    {
							 if( collateralreq.getBodyDetails().get(0).getCersaiId().length() > 30 ) {
								 errors.add("cersaiId",new ActionMessage("error.field.length.exceeded", "30"));
							 }else {
								 reqobj.setCersaiId(collateralreq.getBodyDetails().get(0).getCersaiId().trim());
							 }
							
						    }
						 else{
							 reqobj.setCersaiId("");
							}
				
				}
			} else {

				if (null != collateralreq.getBodyDetails().get(0).getCersaiTransactionRefNumber()
						&& !collateralreq.getBodyDetails().get(0).getCersaiTransactionRefNumber().trim().isEmpty()) {
					if (collateralreq.getBodyDetails().get(0).getCersaiTransactionRefNumber().length() > 30) {
						errors.add("cersaiTransactionRefNumber",
								new ActionMessage("error.field.length.exceeded", "30"));
					} else {
						reqobj.setCersaiTransactionRefNumber(
								collateralreq.getBodyDetails().get(0).getCersaiTransactionRefNumber().trim());
					}
				} else {
					reqobj.setCersaiTransactionRefNumber("");
				}
				
				
				 if (null != collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration()
							&& !collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration().trim().isEmpty()) {
						if (isValidDate(collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration())) {
							Date dateOfCersaiReg = DateUtil.convertDate(locale, collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration());
							if (dateOfCersaiReg.after(currDate)) 
								errors.add("dateOfCersaiRegisteration", new ActionMessage("error.future.date"));
							else
								reqobj.setDateOfCersaiRegisteration(collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration());
							} else {
							errors.add("dateOfCersaiRegisteration", new ActionMessage("error.date.format"));
							}
						} else {
						reqobj.setDateOfCersaiRegisteration("");
						}
					 
					 if(null != collateralreq.getBodyDetails().get(0).getCersaiId() && !collateralreq.getBodyDetails().get(0).getCersaiId().trim().isEmpty())
					    {
						 if( collateralreq.getBodyDetails().get(0).getCersaiId().length() > 30 ) {
							 errors.add("cersaiId",new ActionMessage("error.field.length.exceeded", "30"));
						 }else {
							 reqobj.setCersaiId(collateralreq.getBodyDetails().get(0).getCersaiId().trim());
						 }
						
					    }
					 else{
						 reqobj.setCersaiId("");
						}

			}
			  
			 if(null != collateralreq.getBodyDetails().get(0).getCersaiSecurityInterestId() &&!collateralreq.getBodyDetails().get(0).getCersaiSecurityInterestId().trim().isEmpty())
			    {
				 if(collateralreq.getBodyDetails().get(0).getCersaiSecurityInterestId().length() > 30)
				 {
					 errors.add("cersaiSecurityInterestId",new ActionMessage("error.field.length.exceeded", "30"));
				 }
				 else
				 {
				 reqobj.setCersaiSecurityInterestId(collateralreq.getBodyDetails().get(0).getCersaiSecurityInterestId().trim());
			    }
			    }
			 else if(null != collateralreq.getBodyDetails().get(0).getCersaiSecurityInterestId() && collateralreq.getBodyDetails().get(0).getCersaiSecurityInterestId().trim().isEmpty())
			 {
				 reqobj.setCersaiSecurityInterestId("");
			 }
			 if(null != collateralreq.getBodyDetails().get(0).getCersaiAssetId() && !collateralreq.getBodyDetails().get(0).getCersaiAssetId().trim().isEmpty())
			    {
				 if(collateralreq.getBodyDetails().get(0).getCersaiAssetId().length() > 30)
				 {
					 errors.add("cersaiAssetId",new ActionMessage("error.field.length.exceeded", "30"));
				 }
				 else
				 {
				 reqobj.setCersaiAssetId(collateralreq.getBodyDetails().get(0).getCersaiAssetId().trim());
			    }
			    }
			 else if(null != collateralreq.getBodyDetails().get(0).getCersaiAssetId() && collateralreq.getBodyDetails().get(0).getCersaiAssetId().trim().isEmpty()){
				 reqobj.setCersaiAssetId("");
				}
			 
			
			 
			 
			 if (null != collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration()
					&& !collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration().trim().isEmpty()) {
				if (isValidDate(collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration())) {
					Date dateOfCersaiReg = DateUtil.convertDate(locale, collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration());
					if (dateOfCersaiReg.after(currDate)) 
						errors.add("dateOfCersaiRegisteration", new ActionMessage("error.future.date"));
					else
						reqobj.setDateOfCersaiRegisteration(collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration());
					} else {
					errors.add("dateOfCersaiRegisteration", new ActionMessage("error.date.format"));
					}
				} else {
				reqobj.setDateOfCersaiRegisteration("");
				}
			 
			 if(null != collateralreq.getBodyDetails().get(0).getCersaiId() && !collateralreq.getBodyDetails().get(0).getCersaiId().trim().isEmpty())
			    {
				 if( collateralreq.getBodyDetails().get(0).getCersaiId().length() > 30 ) {
					 errors.add("cersaiId",new ActionMessage("error.field.length.exceeded", "30"));
				 }else {
					 reqobj.setCersaiId(collateralreq.getBodyDetails().get(0).getCersaiId().trim());
				 }
				
			    }
			 else{
				 reqobj.setCersaiId("");
				}
			if (null != collateralreq.getBodyDetails().get(0).getSaleDeedPurchaseDate()
					&& !collateralreq.getBodyDetails().get(0).getSaleDeedPurchaseDate().trim().isEmpty()) {
				if (isValidDate(collateralreq.getBodyDetails().get(0).getSaleDeedPurchaseDate())) {
					Date saleDeedPurchaseDate = DateUtil.convertDate(locale, collateralreq.getBodyDetails().get(0).getSaleDeedPurchaseDate());
					if (saleDeedPurchaseDate.after(currDate)) 
						errors.add("saleDeedPurchaseDateError", new ActionMessage("error.future.date"));
					else
						reqobj.setSaleDeedPurchaseDate(collateralreq.getBodyDetails().get(0).getSaleDeedPurchaseDate().trim());
				} else {
					errors.add("saleDeedPurchaseDate", new ActionMessage("error.date.format"));
				}
			} else {
				errors.add("saleDeedPurchaseDate", new ActionMessage("error.string.mandatory"));
			}
		}
		else
		{
			 reqobj.setSecurityOwnership("");
			 reqobj.setOwnerOfProperty("");
			 reqobj.setThirdPartyEntity("");
			 reqobj.setCinForThirdParty("");
			 reqobj.setThirdPartyAddress("");
			 reqobj.setThirdPartyCity("");
			 reqobj.setThirdPartyState("");
			 reqobj.setThirdPartyPincode("");
			 reqobj.setCersaiApplicableInd("");
			 reqobj.setCersaiAssetId("");
			 reqobj.setCersaiSecurityInterestId("");
			 reqobj.setCersaiTransactionRefNumber("");
			 reqobj.setDateOfCersaiRegisteration("");
			 reqobj.setCersaiId("");
			 reqobj.setSaleDeedPurchaseDate("");
		}
		 
		 if(null != collateralreq.getBodyDetails().get(0).getMargin() && !collateralreq.getBodyDetails().get(0).getMargin().trim().isEmpty()){
			 
			 
			if(!ASSTValidator.isNumeric(collateralreq.getBodyDetails().get(0).getMargin())) {
				 errors.add("margin", new ActionMessage("error.margin.invalid"));
				}
			else  if(collateralreq.getBodyDetails().get(0).getMargin().contains(".")) {
				 errors.add("margin", new ActionMessage("error.margin.invalid"));
				}
			 else  if(Integer.parseInt(collateralreq.getBodyDetails().get(0).getMargin()) > 100) {
				 errors.add("margin", new ActionMessage("error.margin.invalid"));
				}
			 else {
				 reqobj.setMargin(collateralreq.getBodyDetails().get(0).getMargin());
			 }
		 }else
		 {
			 errors.add("margin", new ActionMessage("error.string.mandatory"));
			 
		 }
		 String errorCodes = null;
		 if(null != collateralreq.getBodyDetails().get(0).getCmv() && !collateralreq.getBodyDetails().get(0).getCmv().trim().isEmpty()){
			 reqobj.setCmv(collateralreq.getBodyDetails().get(0).getCmv().trim());

		} else {
			if (!"PT701".equalsIgnoreCase(ColSubTypeId))
				errors.add("cmv (Security OMV)", new ActionMessage("error.string.mandatory"));
		}
		
		 if("AB109".equalsIgnoreCase(ColSubTypeId)) {
		 //AB SA
		 List<ABSpecRestRequestDTO> abSpecRestRequestDTOList = collateralreq.getBodyDetails().get(0).getAbSpecRestRequestDTOList();
		 ABSpecRestRequestDTO assetBasedRequestDTO = new ABSpecRestRequestDTO();
		 if(null != abSpecRestRequestDTOList && !abSpecRestRequestDTOList.isEmpty())
		 {
			 ABSpecRestRequestDTO abSpecRestRequestDTO = abSpecRestRequestDTOList.get(0);
			 if(null != abSpecRestRequestDTO.getRamId() && !abSpecRestRequestDTO.getRamId().trim().isEmpty()){
				 if(abSpecRestRequestDTO.getRamId().length() <= 15)
				 assetBasedRequestDTO.setRamId(abSpecRestRequestDTO.getRamId());
				 else
					 errors.add("ramId",new ActionMessage("error.field.length.exceeded", "15")); 
			 }else if(null != abSpecRestRequestDTO.getRamId() && abSpecRestRequestDTO.getRamId().trim().isEmpty())
			 {
				 assetBasedRequestDTO.setRamId("");
			 }
			 
		
			 if(null != abSpecRestRequestDTO.getStartDate() && !abSpecRestRequestDTO.getStartDate().trim().isEmpty()){

					if(isValidDate(abSpecRestRequestDTO.getStartDate()))
						assetBasedRequestDTO.setStartDate(abSpecRestRequestDTO.getStartDate());
					 else
						 errors.add("startDate",new ActionMessage("error.date.format"));
				
				 
			 }else if(null != abSpecRestRequestDTO.getStartDate() && abSpecRestRequestDTO.getStartDate().trim().isEmpty())
			 {
				 assetBasedRequestDTO.setStartDate("");
			 }
			 
			 if(null != abSpecRestRequestDTO.getMaturityDate() && !abSpecRestRequestDTO.getMaturityDate().trim().isEmpty()){
					if(isValidDate(abSpecRestRequestDTO.getMaturityDate()))
							assetBasedRequestDTO.setMaturityDate(abSpecRestRequestDTO.getMaturityDate().trim());
					 else
						 errors.add("maturityDate",new ActionMessage("error.date.format"));
			 }
			 else if(null != abSpecRestRequestDTO.getMaturityDate() && abSpecRestRequestDTO.getMaturityDate().trim().isEmpty()){
			 
				 assetBasedRequestDTO.setMaturityDate("");
			 }
			 boolean isInspAllowed = false;
			 if(null != abSpecRestRequestDTO.getIsPhysicalInspection() && !abSpecRestRequestDTO.getIsPhysicalInspection().trim().isEmpty()){
				 if("true".equalsIgnoreCase(abSpecRestRequestDTO.getIsPhysicalInspection()) || "false".equalsIgnoreCase(abSpecRestRequestDTO.getIsPhysicalInspection())) {
					 assetBasedRequestDTO.setIsPhysicalInspection(abSpecRestRequestDTO.getIsPhysicalInspection());
				 isInspAllowed=true;
				 }else {
					 errors.add("isPhysicalInspection",new ActionMessage("error.invalid.field.value"));
				 }
			 }else
			 {
				 errors.add("isPhysicalInspection",new ActionMessage("error.string.mandatory"));
			 }
			 
			 if(null != abSpecRestRequestDTO.getEnvRiskyStatus() && !abSpecRestRequestDTO.getEnvRiskyStatus().trim().isEmpty()){
				 try {
					 Object obj = masterObj.getObjectByEntityName("entryCode", abSpecRestRequestDTO.getEnvRiskyStatus().trim(),"envRiskyStatus",errors,"12");
					 if(!(obj instanceof ActionErrors)){
						 assetBasedRequestDTO.setEnvRiskyStatus(((ICommonCodeEntry)obj).getEntryCode());
						}
					 else
					 {
						 errors.add("envRiskyStatus",new ActionMessage("error.invalid.field.value")); 
					 }
				 }
				 catch(Exception e){
						DefaultLogger.error(this, "envRiskyStatus"+e.getMessage());
						errors.add("envRiskyStatus",new ActionMessage("error.invalid.isPhysicalInspection"));
					}
			    }
			 else
			 {
				 errors.add("envRiskyStatus",new ActionMessage("error.string.mandatory"));
			 }
			 
			
			 if(isInspAllowed) {
			 if(null != abSpecRestRequestDTO.getPhysicalInspectionFreq() && !abSpecRestRequestDTO.getPhysicalInspectionFreq().trim().isEmpty()){
				 try {
					 Object obj = masterObj.getObjectByEntityName("entryCode", abSpecRestRequestDTO.getPhysicalInspectionFreq().trim(),"physicalInspectionFreq",errors,"FREQUENCY");
					 if(!(obj instanceof ActionErrors)){
						 assetBasedRequestDTO.setPhysicalInspectionFreq(((ICommonCodeEntry)obj).getEntryCode());
						} else{
						 errors.add("physicalInspectionFreq",new ActionMessage("error.invalid.field.value")); 
						 }
					 }catch(Exception e){
						DefaultLogger.error(this, "physicalInspectionFreq"+e.getMessage());
						errors.add("physicalInspectionFreq",new ActionMessage("error.invalid.physicalInspectionFreq"));
						}
				 } else {
					 errors.add("physicalInspectionFreq",new ActionMessage("error.string.mandatory"));
				 }
			 }else {
				 assetBasedRequestDTO.setPhysicalInspectionFreq("");
			 }
			 
			 
			 
				if (isInspAllowed) {
					if (null != abSpecRestRequestDTO.getLastPhysicalInspectDate() && !abSpecRestRequestDTO.getLastPhysicalInspectDate().trim().isEmpty()) {
						if (isValidDate(abSpecRestRequestDTO.getLastPhysicalInspectDate()))
							assetBasedRequestDTO.setLastPhysicalInspectDate(abSpecRestRequestDTO.getLastPhysicalInspectDate());
						else
							errors.add("lastPhysicalInspectDate", new ActionMessage("error.date.format"));
					} else {
						errors.add("lastPhysicalInspectDate", new ActionMessage("error.string.mandatory"));
						}
					} else {
					assetBasedRequestDTO.setLastPhysicalInspectDate("");
				}
			 
				if (isInspAllowed) {
					if (null != abSpecRestRequestDTO.getNextPhysicalInspectDate() && !abSpecRestRequestDTO.getNextPhysicalInspectDate().trim().isEmpty()) {
						if (isValidDate(abSpecRestRequestDTO.getNextPhysicalInspectDate()))
							assetBasedRequestDTO.setNextPhysicalInspectDate(abSpecRestRequestDTO.getNextPhysicalInspectDate());
						else
							errors.add("nextPhysicalInspectDate", new ActionMessage("error.date.format"));
					} else {
						errors.add("nextPhysicalInspectDate", new ActionMessage("error.string.mandatory"));
					}
				} else {
					assetBasedRequestDTO.setNextPhysicalInspectDate("");
				}
			 
			 if(null != abSpecRestRequestDTO.getGoodStatus() && !abSpecRestRequestDTO.getGoodStatus().trim().isEmpty()){
					 try {
						 Object obj = masterObj.getObjectByEntityName("entryCode", abSpecRestRequestDTO.getGoodStatus().trim(),"goodStatus",errors,"GOODS_STATUS");
						 if(!(obj instanceof ActionErrors)){
							 assetBasedRequestDTO.setGoodStatus(((ICommonCodeEntry)obj).getEntryCode());
							}
						 else
						 {
							 errors.add("goodStatus",new ActionMessage("error.invalid.field.value")); 
						 }
					 }
					 catch(Exception e){
							DefaultLogger.error(this, "goodStatus"+e.getMessage());
							errors.add("goodStatus",new ActionMessage("error.invalid.goodStatus"));
						}
				    }
				 else
				 {
					 assetBasedRequestDTO.setGoodStatus("");
				 }
			 if(null != abSpecRestRequestDTO.getScrapValue() && !abSpecRestRequestDTO.getScrapValue().trim().isEmpty()){
				 if(abSpecRestRequestDTO.getScrapValue().length() <= 15)
				 assetBasedRequestDTO.setScrapValue(abSpecRestRequestDTO.getScrapValue());
				 else
				 errors.add("scrapValue",new ActionMessage("error.field.length.exceeded", "15"));
			 }else
			 {
				 assetBasedRequestDTO.setScrapValue("");
			 }
			 
			 if(null != abSpecRestRequestDTO.getEnvRiskyStatus() && !abSpecRestRequestDTO.getEnvRiskyStatus().trim().isEmpty()){
				 assetBasedRequestDTO.setEnvRiskyStatus(abSpecRestRequestDTO.getEnvRiskyStatus());
			 }else
			 {
				 assetBasedRequestDTO.setEnvRiskyStatus("");
			 }
			 
			 if(null != abSpecRestRequestDTO.getEnvRiskyDate() && !abSpecRestRequestDTO.getEnvRiskyDate().trim().isEmpty())
				 assetBasedRequestDTO.setEnvRiskyDate(abSpecRestRequestDTO.getEnvRiskyDate());
			 else if(null != abSpecRestRequestDTO.getEnvRiskyDate() && abSpecRestRequestDTO.getEnvRiskyDate().trim().isEmpty())
			 {
				 assetBasedRequestDTO.setEnvRiskyDate("");
			 }
			 
			 if(null != abSpecRestRequestDTO.getRemarks() && !abSpecRestRequestDTO.getRemarks().trim().isEmpty()){
				 assetBasedRequestDTO.setRemarks(abSpecRestRequestDTO.getRemarks());
			 }else
			 {
				 assetBasedRequestDTO.setRemarks("");
			 }
			 
		 }
	}
		
		 if("GT405".equalsIgnoreCase(ColSubTypeId)) {
			 if (collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList() != null
						&& !collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().isEmpty()) {


					// For Country
					 if (null != collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCountry()
								&& !collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCountry()
								.trim().isEmpty()) {
							Object obj = masterObj.getObjectforMasterRelatedCode("actualCountry",
									collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCountry().trim(), "country",
									errors);
							if (!(obj instanceof ActionErrors)) {
								collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).setCountry(Long.toString(((ICountry) obj).getIdCountry()));
								// For Region
								if (null != collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRegion()
										&& !collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRegion().trim().isEmpty()) {

									Object objReg = masterObj.getObjectforMasterRelatedCode("actualRegion",
											collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRegion().trim(), "region",
											errors);
									if (!(objReg instanceof ActionErrors)) {
										collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).setRegion(Long.toString(((IRegion) objReg).getIdRegion()));
										//check if region is present in list of country
										if(collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCountry().trim().
										equalsIgnoreCase(Long.toString(((IRegion) objReg).getCountryId().getIdCountry()))){
											// For State
											if (null != collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getState()
													&& !collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getState().trim().isEmpty()) {
												Object objState = masterObj.getObjectforMasterRelatedCode("actualState",
														collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getState().trim(), "state",
														errors);
												if (!(objState instanceof ActionErrors)) {
													collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).setState(Long.toString(((IState) objState).getIdState()));
													
													//check if state is present in list of region
													if(collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRegion().trim().
													equalsIgnoreCase(Long.toString(((IState) objState).getRegionId().getIdRegion()))){
														//For City
														if (null != collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCity()
																	&& !collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCity().trim().isEmpty()) {

															Object objCity = masterObj.getObjectforMasterRelatedCode("actualCity",
																	collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCity().trim(), "city",
																	errors);
															if (!(objCity instanceof ActionErrors)) {
																//check if given city is present in list of state
																if(collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getState().trim().
																equalsIgnoreCase(Long.toString(((ICity) objCity).getStateId().getIdState()))){
																	collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).setCity(Long.toString(((ICity) objCity).getIdCity()));
																}else {
																	System.out.println("Given City is not Present in the state list of State");
																	errors.add("city", new ActionMessage("error.invalid.field.value"));
																}
															
															
															} else {
																errors.add("city", new ActionMessage("error.invalid.field.value"));
															}
														}
														
													} else {
														System.out.println("Given State is not Present in the state list of Region");
														errors.add("state", new ActionMessage("error.invalid.field.value"));
													}
													
												} else {
													errors.add("state", new ActionMessage("error.invalid.field.value"));
												}
											}
										}else {
											System.out.println("Given Region is not Present in the list of Country");
											errors.add("region", new ActionMessage("error.invalid.field.value"));
										}
										
									} else {
										errors.add("region", new ActionMessage("error.invalid.field.value"));
									}
								
								}
							} else {
								errors.add("country", new ActionMessage("error.invalid.field.value"));
							}
						} else {
							errors.add("country", new ActionMessage("error.string.mandatory"));
						} 
					 
					// For Guarantor Type
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
							.getGuarantorType()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
									.getGuarantorType().trim().isEmpty()) {
						try {
							Object obj = masterObj
									.getObjectByEntityName(
											"entryCode", collateralreq.getBodyDetails().get(0)
													.getGuaranteeCorpRestRequestDTOList().get(0).getGuarantorType().trim(),
											"guarantorType", errors, "GUARANTOR_TYPE");
							if (!(obj instanceof ActionErrors)) {
								collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.setGuarantorType(((ICommonCodeEntry) obj).getEntryCode());
							}
						} catch (Exception e) {
							DefaultLogger.error(this, "guarantorType" + e.getMessage());
							errors.add("guarantorType", new ActionMessage("error.invalid.guarantorType"));
						}
					} else {
						errors.add("guarantorType", new ActionMessage("error.string.mandatory"));
					}

					// For Guarantor Nature
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
							.getGuarantorNature()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
									.getGuarantorNature().trim().isEmpty()) {
						try {
							Object obj = masterObj
									.getObjectByEntityName("entryCode",
											collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
													.getGuarantorNature().trim(),
											"guarantorNature", errors, "GUARANTOR_NATURE");
							if (!(obj instanceof ActionErrors)) {
								collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.setGuarantorNature(((ICommonCodeEntry) obj).getEntryCode());
							}
						} catch (Exception e) {
							DefaultLogger.error(this, "guarantorNature" + e.getMessage());
							errors.add("guarantorNature", new ActionMessage("error.invalid.guarantorNature"));
						}
					}
					else if(null != collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
							.getGuarantorNature()
							&& collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
									.getGuarantorNature().trim().isEmpty())
					{
						collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
						.setGuarantorNature("");
					}
					// For Charge Type
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
							.getChargeType()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
									.getChargeType().trim().isEmpty()) {
						try {
							Object obj = masterObj
									.getObjectByEntityName(
											"entryCode", collateralreq.getBodyDetails().get(0)
													.getGuaranteeCorpRestRequestDTOList().get(0).getChargeType().trim(),
											"chargeType", errors, "CHARGE_TYPE");
							if (!(obj instanceof ActionErrors)) {
								collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.setChargeType(((ICommonCodeEntry) obj).getEntryCode());
							}
						} catch (Exception e) {
							DefaultLogger.error(this, "chargeType" + e.getMessage());
							errors.add("chargeType", new ActionMessage("error.invalid.chargeType"));
						}
					}
					else if(null != collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
							.getChargeType()
							&& collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
									.getChargeType().trim().isEmpty())
					{
						collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
						.setChargeType("");
					}
					// For Holding Period unit
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
							.getClaimPeriodUnit()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
									.getClaimPeriodUnit().trim().isEmpty()) {
						try {
							Object obj = masterObj
									.getObjectByEntityName(
											"entryCode", collateralreq.getBodyDetails().get(0)
													.getGuaranteeCorpRestRequestDTOList().get(0).getClaimPeriodUnit().trim(),
											"claimPeriodUnit", errors, "FREQ_TYPE");
							if (!(obj instanceof ActionErrors)) {
								collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.setClaimPeriodUnit(((ICommonCodeEntry) obj).getEntryCode());
							}
						} catch (Exception e) {
							DefaultLogger.error(this, "claimPeriodUnit" + e.getMessage());
							errors.add("claimPeriodUnit", new ActionMessage("error.invalid.claimPeriodUnit"));
						}
					} else if (null != collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
							.getClaimPeriod()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
									.getClaimPeriod().trim().isEmpty()) {
						errors.add("claimPeriodUnit", new ActionMessage("error.string.mandatory"));
					}
					//check date format
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimDate()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimDate()
									.trim().isEmpty()) {
						if(!isValidDate(collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimDate()))
							errors.add("claimDate",new ActionMessage("error.date.format"));
					}
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getDateGuarantee()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getDateGuarantee()
									.trim().isEmpty()) {
						if(!isValidDate(collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getDateGuarantee()))
							errors.add("dateGuarantee",new ActionMessage("error.date.format"));
					}
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCollateralMaturityDate()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCollateralMaturityDate()
									.trim().isEmpty()) {
						if(!isValidDate(collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCollateralMaturityDate()))
							errors.add("collateralMaturityDate",new ActionMessage("error.date.format"));
					}
					
					if(null != collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRecourse()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRecourse().trim().isEmpty()){
			    		if(collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRecourse().equals("Y"))
			    			collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).setRecourse("Y");
			    		else if(collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRecourse().equals("N"))
			    			collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).setRecourse("N");
			    		else 
			    			errors.add("recourse", new ActionMessage("error.invalid.recourse"));
			    	}
					else if(null != collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRecourse()
							&& collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRecourse().trim().isEmpty())
					{
						collateralreq.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).setRecourse("");
					}
			}
		 } else if("GT408".equalsIgnoreCase(ColSubTypeId)) {
			 if (collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList() != null
						&& !collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().isEmpty()) {

					// For Country
					 if (null != collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCountry()
								&& !collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCountry()
								.trim().isEmpty()) {
							Object obj = masterObj.getObjectforMasterRelatedCode("actualCountry",
									collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCountry().trim(), "country",
									errors);
							if (!(obj instanceof ActionErrors)) {
								collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).setCountry(Long.toString(((ICountry) obj).getIdCountry()));
								// For Region
								if (null != collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRegion()
										&& !collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRegion().trim().isEmpty()) {

									Object objReg = masterObj.getObjectforMasterRelatedCode("actualRegion",
											collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRegion().trim(), "region",
											errors);
									if (!(objReg instanceof ActionErrors)) {
										collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).setRegion(Long.toString(((IRegion) objReg).getIdRegion()));
										//check if region is present in list of country
										if(collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCountry().trim().
										equalsIgnoreCase(Long.toString(((IRegion) objReg).getCountryId().getIdCountry()))){
											// For State
											if (null != collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getState()
													&& !collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getState().trim().isEmpty()) {
												Object objState = masterObj.getObjectforMasterRelatedCode("actualState",
														collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getState().trim(), "state",
														errors);
												if (!(objState instanceof ActionErrors)) {
													collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).setState(Long.toString(((IState) objState).getIdState()));
													
													//check if state is present in list of region
													if(collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRegion().trim().
													equalsIgnoreCase(Long.toString(((IState) objState).getRegionId().getIdRegion()))){
														//For City
														if (null != collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCity()
																	&& !collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCity().trim().isEmpty()) {

															Object objCity = masterObj.getObjectforMasterRelatedCode("actualCity",
																	collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCity().trim(), "city",
																	errors);
															if (!(objCity instanceof ActionErrors)) {
																//check if given city is present in list of state
																if(collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getState().trim().
																equalsIgnoreCase(Long.toString(((ICity) objCity).getStateId().getIdState()))){
																	collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).setCity(Long.toString(((ICity) objCity).getIdCity()));
																}else {
																	System.out.println("Given City is not Present in the state list of State");
																	errors.add("city", new ActionMessage("error.invalid.field.value"));
																}
															
															
															} else {
																errors.add("city", new ActionMessage("error.invalid.field.value"));
															}
														}
														
													} else {
														System.out.println("Given State is not Present in the state list of Region");
														errors.add("state", new ActionMessage("error.invalid.field.value"));
													}
													
												} else {
													errors.add("state", new ActionMessage("error.invalid.field.value"));
												}
											}
										}else {
											System.out.println("Given Region is not Present in the list of Country");
											errors.add("region", new ActionMessage("error.invalid.field.value"));
										}
										
									} else {
										errors.add("region", new ActionMessage("error.invalid.field.value"));
									}
								
								}
							} else {
								errors.add("country", new ActionMessage("error.invalid.field.value"));
							}
						} else {
							errors.add("country", new ActionMessage("error.string.mandatory"));
						} 
					 
					// For Guarantor Type
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
							.getGuarantorType()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
									.getGuarantorType().trim().isEmpty()) {
						try {
							Object obj = masterObj
									.getObjectByEntityName(
											"entryCode", collateralreq.getBodyDetails().get(0)
													.getGuaranteeIndRestRequestDTOList().get(0).getGuarantorType().trim(),
											"guarantorType", errors, "GUARANTOR_TYPE");
							if (!(obj instanceof ActionErrors)) {
								collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.setGuarantorType(((ICommonCodeEntry) obj).getEntryCode());
							}
						} catch (Exception e) {
							DefaultLogger.error(this, "guarantorType" + e.getMessage());
							errors.add("guarantorType", new ActionMessage("error.invalid.guarantorType"));
						}
					} else {
						errors.add("guarantorType", new ActionMessage("error.string.mandatory"));
					}

					// For Guarantor Nature
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
							.getGuarantorNature()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
									.getGuarantorNature().trim().isEmpty()) {
						try {
							Object obj = masterObj
									.getObjectByEntityName("entryCode",
											collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
													.getGuarantorNature().trim(),
											"guarantorNature", errors, "GUARANTOR_NATURE");
							if (!(obj instanceof ActionErrors)) {
								collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.setGuarantorNature(((ICommonCodeEntry) obj).getEntryCode());
							}
						} catch (Exception e) {
							DefaultLogger.error(this, "guarantorNature" + e.getMessage());
							errors.add("guarantorNature", new ActionMessage("error.invalid.guarantorNature"));
						}
					}
					else if(null != collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
							.getGuarantorNature()
							&& collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
									.getGuarantorNature().trim().isEmpty())
					{
						collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
						.setGuarantorNature("");
					}

					// For Charge Type
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
							.getChargeType()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
									.getChargeType().trim().isEmpty()) {
						try {
							Object obj = masterObj
									.getObjectByEntityName(
											"entryCode", collateralreq.getBodyDetails().get(0)
													.getGuaranteeIndRestRequestDTOList().get(0).getChargeType().trim(),
											"chargeType", errors, "CHARGE_TYPE");
							if (!(obj instanceof ActionErrors)) {
								collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.setChargeType(((ICommonCodeEntry) obj).getEntryCode());
							}
						} catch (Exception e) {
							DefaultLogger.error(this, "chargeType" + e.getMessage());
							errors.add("chargeType", new ActionMessage("error.invalid.chargeType"));
						}
					}
					else if(null != collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
							.getChargeType()
							&& collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
									.getChargeType().trim().isEmpty())
					{
						collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
						.setChargeType("");
					}

					// For Holding Period unit
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
							.getClaimPeriodUnit()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
									.getClaimPeriodUnit().trim().isEmpty()) {
						try {
							Object obj = masterObj
									.getObjectByEntityName(
											"entryCode", collateralreq.getBodyDetails().get(0)
													.getGuaranteeIndRestRequestDTOList().get(0).getClaimPeriodUnit().trim(),
											"claimPeriodUnit", errors, "FREQ_TYPE");
							if (!(obj instanceof ActionErrors)) {
								collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.setClaimPeriodUnit(((ICommonCodeEntry) obj).getEntryCode());
							}
						} catch (Exception e) {
							DefaultLogger.error(this, "claimPeriodUnit" + e.getMessage());
							errors.add("claimPeriodUnit", new ActionMessage("error.invalid.claimPeriodUnit"));
						}
					} else if (null != collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
							.getClaimPeriod()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
									.getClaimPeriod().trim().isEmpty()) {
						errors.add("claimPeriodUnit", new ActionMessage("error.string.mandatory"));
					}
					
					//check date format
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimDate()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimDate()
									.trim().isEmpty()) {
						if(!isValidDate(collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimDate()))
							errors.add("claimDate",new ActionMessage("error.date.format"));
					}
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getDateGuarantee()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getDateGuarantee()
									.trim().isEmpty()) {
						if(!isValidDate(collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getDateGuarantee()))
							errors.add("dateGuarantee",new ActionMessage("error.date.format"));
					}
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCollateralMaturityDate()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCollateralMaturityDate()
									.trim().isEmpty()) {
						if(!isValidDate(collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCollateralMaturityDate()))
							errors.add("collateralMaturityDate",new ActionMessage("error.date.format"));
					}
					
					if(null != collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRecourse()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRecourse().trim().isEmpty()){
			    		if(collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRecourse().equals("Y"))
			    			collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).setRecourse("Y");
			    		else if(collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRecourse().equals("N"))
			    			collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).setRecourse("N");
			    		else 
			    			errors.add("recourse", new ActionMessage("error.invalid.recourse"));
			    	}
					else if(null != collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRecourse()
							&& collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRecourse().trim().isEmpty())
					{
						collateralreq.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).setRecourse("");
					}
			}
		 } else if("GT406".equalsIgnoreCase(ColSubTypeId)) {

			 if (collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList() != null
						&& !collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().isEmpty()) {

					// For Country
					 if (null != collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCountry()
								&& !collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCountry()
								.trim().isEmpty()) {
							Object obj = masterObj.getObjectforMasterRelatedCode("actualCountry",
									collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCountry().trim(), "country",
									errors);
							if (!(obj instanceof ActionErrors)) {
								collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).setCountry(Long.toString(((ICountry) obj).getIdCountry()));
								// For Region
								if (null != collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRegion()
										&& !collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRegion().trim().isEmpty()) {

									Object objReg = masterObj.getObjectforMasterRelatedCode("actualRegion",
											collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRegion().trim(), "region",
											errors);
									if (!(objReg instanceof ActionErrors)) {
										collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).setRegion(Long.toString(((IRegion) objReg).getIdRegion()));
										//check if region is present in list of country
										if(collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCountry().trim().
										equalsIgnoreCase(Long.toString(((IRegion) objReg).getCountryId().getIdCountry()))){
											// For State
											if (null != collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getState()
													&& !collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getState().trim().isEmpty()) {
												Object objState = masterObj.getObjectforMasterRelatedCode("actualState",
														collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getState().trim(), "state",
														errors);
												if (!(objState instanceof ActionErrors)) {
													collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).setState(Long.toString(((IState) objState).getIdState()));
													
													//check if state is present in list of region
													if(collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRegion().trim().
													equalsIgnoreCase(Long.toString(((IState) objState).getRegionId().getIdRegion()))){
														//For City
														if (null != collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCity()
																	&& !collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCity().trim().isEmpty()) {

															Object objCity = masterObj.getObjectforMasterRelatedCode("actualCity",
																	collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCity().trim(), "city",
																	errors);
															if (!(objCity instanceof ActionErrors)) {
																//check if given city is present in list of state
																if(collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getState().trim().
																equalsIgnoreCase(Long.toString(((ICity) objCity).getStateId().getIdState()))){
																	collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).setCity(Long.toString(((ICity) objCity).getIdCity()));
																}else {
																	System.out.println("Given City is not Present in the state list of State");
																	errors.add("city", new ActionMessage("error.invalid.field.value"));
																}
															
															
															} else {
																errors.add("city", new ActionMessage("error.invalid.field.value"));
															}
														}
														
													} else {
														System.out.println("Given State is not Present in the state list of Region");
														errors.add("state", new ActionMessage("error.invalid.field.value"));
													}
													
												} else {
													errors.add("state", new ActionMessage("error.invalid.field.value"));
												}
											}
										}else {
											System.out.println("Given Region is not Present in the list of Country");
											errors.add("region", new ActionMessage("error.invalid.field.value"));
										}
										
									} else {
										errors.add("region", new ActionMessage("error.invalid.field.value"));
									}
								
								}
							} else {
								errors.add("country", new ActionMessage("error.invalid.field.value"));
							}
						} else {
							errors.add("country", new ActionMessage("error.string.mandatory"));
						} 
					 
					// For Guarantor Type
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
							.getGuarantorType()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
									.getGuarantorType().trim().isEmpty()) {
						try {
							Object obj = masterObj
									.getObjectByEntityName(
											"entryCode", collateralreq.getBodyDetails().get(0)
													.getGuaranteeGovtRestRequestDTOList().get(0).getGuarantorType().trim(),
											"guarantorType", errors, "GUARANTOR_TYPE");
							if (!(obj instanceof ActionErrors)) {
								collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.setGuarantorType(((ICommonCodeEntry) obj).getEntryCode());
							}
						} catch (Exception e) {
							DefaultLogger.error(this, "guarantorType" + e.getMessage());
							errors.add("guarantorType", new ActionMessage("error.invalid.guarantorType"));
						}
					} else {
						errors.add("guarantorType", new ActionMessage("error.string.mandatory"));
					}

					// For Guarantor Nature
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
							.getGuarantorNature()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
									.getGuarantorNature().trim().isEmpty()) {
						try {
							Object obj = masterObj
									.getObjectByEntityName("entryCode",
											collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
													.getGuarantorNature().trim(),
											"guarantorNature", errors, "GUARANTOR_NATURE");
							if (!(obj instanceof ActionErrors)) {
								collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.setGuarantorNature(((ICommonCodeEntry) obj).getEntryCode());
							}
						} catch (Exception e) {
							DefaultLogger.error(this, "guarantorNature" + e.getMessage());
							errors.add("guarantorNature", new ActionMessage("error.invalid.guarantorNature"));
						}
					}
					else if(null != collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
							.getGuarantorNature()
							&& collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
									.getGuarantorNature().trim().isEmpty())
					{
						collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
						.setGuarantorNature("");
					}
					// For Charge Type
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
							.getChargeType()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
									.getChargeType().trim().isEmpty()) {
						try {
							Object obj = masterObj
									.getObjectByEntityName(
											"entryCode", collateralreq.getBodyDetails().get(0)
													.getGuaranteeGovtRestRequestDTOList().get(0).getChargeType().trim(),
											"chargeType", errors, "CHARGE_TYPE");
							if (!(obj instanceof ActionErrors)) {
								collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.setChargeType(((ICommonCodeEntry) obj).getEntryCode());
							}
						} catch (Exception e) {
							DefaultLogger.error(this, "chargeType" + e.getMessage());
							errors.add("chargeType", new ActionMessage("error.invalid.chargeType"));
						}
					}
					else if(null != collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
							.getChargeType()
							&& collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
									.getChargeType().trim().isEmpty())
					{
						collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
						.setChargeType("");
					}
					// For Holding Period unit
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
							.getClaimPeriodUnit()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
									.getClaimPeriodUnit().trim().isEmpty()) {
						try {
							Object obj = masterObj
									.getObjectByEntityName(
											"entryCode", collateralreq.getBodyDetails().get(0)
													.getGuaranteeGovtRestRequestDTOList().get(0).getClaimPeriodUnit().trim(),
											"claimPeriodUnit", errors, "FREQ_TYPE");
							if (!(obj instanceof ActionErrors)) {
								collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.setClaimPeriodUnit(((ICommonCodeEntry) obj).getEntryCode());
							}
						} catch (Exception e) {
							DefaultLogger.error(this, "claimPeriodUnit" + e.getMessage());
							errors.add("claimPeriodUnit", new ActionMessage("error.invalid.claimPeriodUnit"));
						}
					} else if (null != collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
							.getClaimPeriod()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
									.getClaimPeriod().trim().isEmpty()) {
						errors.add("claimPeriodUnit", new ActionMessage("error.string.mandatory"));
					}
					
					//check date format
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimDate()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimDate()
									.trim().isEmpty()) {
						if(!isValidDate(collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimDate()))
							errors.add("claimDate",new ActionMessage("error.date.format"));
					}
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getDateGuarantee()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getDateGuarantee()
									.trim().isEmpty()) {
						if(!isValidDate(collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getDateGuarantee()))
							errors.add("dateGuarantee",new ActionMessage("error.date.format"));
					}
					if (null != collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCollateralMaturityDate()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCollateralMaturityDate()
									.trim().isEmpty()) {
						if(!isValidDate(collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCollateralMaturityDate()))
							errors.add("collateralMaturityDate",new ActionMessage("error.date.format"));
					}
					
					if(null != collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRecourse()
							&& !collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRecourse().trim().isEmpty()){
			    		if(collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRecourse().equals("Y"))
			    			collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).setRecourse("Y");
			    		else if(collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRecourse().equals("N"))
			    			collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).setRecourse("N");
			    		else 
			    			errors.add("recourse", new ActionMessage("error.invalid.recourse"));
			    	}
					else if(null != collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRecourse()
							&& collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRecourse().trim().isEmpty())
					{
						collateralreq.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).setRecourse("");
					}
			}
		 }
		 if("PT701".equalsIgnoreCase(ColSubTypeId)) {
			 List<PropertyRestRequestDTO> propertyRestRequestList = collateralreq.getBodyDetails().get(0).getPropertyDetailsList();
			 PropertyRestRequestDTO propertyRestRequestSetDTO = new PropertyRestRequestDTO();
			 if(null != propertyRestRequestList && !propertyRestRequestList.isEmpty())
			 {
				 PropertyRestRequestDTO propertyRestRequesGettDTO = propertyRestRequestList.get(0);
				 boolean isDatePresent = false;
					
				 propertyRestRequestSetDTO=propertyRestRequestList.get(0);
				 if(null != propertyRestRequesGettDTO.getIsAdDocFac() && !propertyRestRequesGettDTO.getIsAdDocFac().trim().isEmpty())
				 {
					 if("Y".equalsIgnoreCase(propertyRestRequesGettDTO.getIsAdDocFac()) || "N".equalsIgnoreCase(propertyRestRequesGettDTO.getIsAdDocFac()))
					 propertyRestRequestSetDTO.setIsAdDocFac(propertyRestRequesGettDTO.getIsAdDocFac().trim());
					 else
						 errors.add("isAdDocFac",new ActionMessage("error.invalid.field.value"));
				 }
				 else
					 errors.add("isAdDocFac",new ActionMessage("error.string.mandatory"));
				 if(null != propertyRestRequesGettDTO.getPropertyId() && !propertyRestRequesGettDTO.getPropertyId().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setPropertyId(propertyRestRequesGettDTO.getPropertyId().trim());
				    }
				 else if(null != propertyRestRequesGettDTO.getPropertyId() && propertyRestRequesGettDTO.getPropertyId().trim().isEmpty())
				 {
					 propertyRestRequestSetDTO.setPropertyId("");
					}
	if(null != propertyRestRequesGettDTO.getDescription() && !propertyRestRequesGettDTO.getDescription().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setDescription(propertyRestRequesGettDTO.getDescription().trim());
				    }
				 else{
					 propertyRestRequestSetDTO.setDescription("");
					}
	if(null != propertyRestRequesGettDTO.getPropertyType() && !propertyRestRequesGettDTO.getPropertyType().trim().isEmpty())
				    {
		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getPropertyType().trim(),"propertyType",errors,"PROPERTY_TYPE");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setPropertyType(((ICommonCodeEntry)obj).getEntryCode());
				 propertyRestRequesGettDTO.setPropertyTypeLabel(((ICommonCodeEntry)obj).getEntryName());
				}
			 else
			 {
				 errors.add("propertyType",new ActionMessage("error.invalid.field.value")); 
			 }
			 
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "propertyType - "+e.getMessage());
				errors.add("propertyType",new ActionMessage("error.invalid.propertyType"));
			}
		//			 propertyRestRequestSetDTO.setPropertyType(propertyRestRequesGettDTO.getPropertyType().trim());
				    }
				 else{
					 propertyRestRequestSetDTO.setPropertyType("");
					 propertyRestRequesGettDTO.setPropertyTypeLabel("");
					}
	if(null != propertyRestRequesGettDTO.getPropertyUsage() && !propertyRestRequesGettDTO.getPropertyUsage().trim().isEmpty())
				    {
		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getPropertyUsage().trim(),"propertyUsage",errors,"PROPERTY_USAGE");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setPropertyUsage(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("propertyUsage",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "propertyUsage - "+e.getMessage());
				errors.add("propertyUsage",new ActionMessage("error.invalid.propertyUsage"));
			}
		//			 propertyRestRequestSetDTO.setPropertyType(propertyRestRequesGettDTO.getPropertyType().trim());
				    }
				 else{
					 propertyRestRequestSetDTO.setPropertyUsage("");
					}
				
				if (null != propertyRestRequesGettDTO.getPreviousMortCreationDate()
						&& !propertyRestRequesGettDTO.getPreviousMortCreationDate().trim().isEmpty()) {
					if (isValidDate(propertyRestRequesGettDTO.getPreviousMortCreationDate())) {
						isDatePresent = collateralDAO.checkPreviousMortCreationDate(collateralreq.getBodyDetails().get(0).getSecurityId(),
						propertyRestRequesGettDTO.getPreviousMortCreationDate());
						if (isDatePresent) {
							propertyRestRequestSetDTO.setPreviousMortCreationDate(propertyRestRequesGettDTO.getPreviousMortCreationDate().trim());
						} else {
							errors.add("previousMortCreationDate- "+ propertyRestRequesGettDTO.getPreviousMortCreationDate(), new ActionMessage("error.invalid.field.value"));
						}
					} else {
						errors.add("previousMortCreationDate", new ActionMessage("error.date.format"));
					}
				} else {
					propertyRestRequestSetDTO.setPreviousMortCreationDate("");
				}
				
				
				boolean ismorCreAdd = false;
				if(null != propertyRestRequesGettDTO.getMortgageCreExtDateAdd() && !propertyRestRequesGettDTO.getMortgageCreExtDateAdd().trim().isEmpty())
							{
								if ("Y".equalsIgnoreCase(propertyRestRequesGettDTO
										.getMortgageCreExtDateAdd())
										|| "N".equalsIgnoreCase(propertyRestRequesGettDTO
												.getMortgageCreExtDateAdd()))
									propertyRestRequestSetDTO.setMortgageCreExtDateAdd(
											propertyRestRequesGettDTO
													.getMortgageCreExtDateAdd().trim());
								else
									errors.add("mortgageCreExtDateAdd",
											new ActionMessage("error.invalid.field.value"));
								if ("Y".equalsIgnoreCase(propertyRestRequesGettDTO
										.getMortgageCreExtDateAdd())) {
									ismorCreAdd = true;
								}
							}
							 else{
								 errors.add("mortgageCreExtDateAdd",new ActionMessage("error.string.mandatory"));
								}
				if(ismorCreAdd) {
					if(null == collateralreq.getBodyDetails().get(0).getCersaiTransactionRefNumber() || collateralreq.getBodyDetails().get(0).getCersaiTransactionRefNumber().trim().isEmpty()) {
						errors.add("cersaiTransactionRefNumber",new ActionMessage("error.string.mandatory"));	
					}
					if(null == collateralreq.getBodyDetails().get(0).getCersaiId() || collateralreq.getBodyDetails().get(0).getCersaiId().trim().isEmpty()) {
						errors.add("cersaiId",new ActionMessage("error.string.mandatory"));	
					}
					if(null == collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration() || collateralreq.getBodyDetails().get(0).getDateOfCersaiRegisteration().trim().isEmpty()) {
						errors.add("dateOfCersaiRegisteration",new ActionMessage("error.string.mandatory"));	
					}
				}
				
	if(!isDatePresent || ismorCreAdd){
			if(null != propertyRestRequesGettDTO.getSalePurchaseValue() && !propertyRestRequesGettDTO.getSalePurchaseValue().trim().isEmpty()){
					propertyRestRequestSetDTO.setSalePurchaseValue(propertyRestRequesGettDTO.getSalePurchaseValue().trim());
				}else {
						errors.add("salePurchaseValue",new ActionMessage("error.string.mandatory"));	
					}
		}	
	
	if(null != propertyRestRequesGettDTO.getDateOfReceiptTitleDeed() && !propertyRestRequesGettDTO.getDateOfReceiptTitleDeed().trim().isEmpty())
				    {
					if(isValidDate(propertyRestRequesGettDTO.getDateOfReceiptTitleDeed()))
						propertyRestRequesGettDTO.setDateOfReceiptTitleDeed(propertyRestRequesGettDTO.getDateOfReceiptTitleDeed());
					 else
						 errors.add("dateOfReceiptTitleDeed",new ActionMessage("error.date.format"));		
				    } else{
					 propertyRestRequestSetDTO.setDateOfReceiptTitleDeed("");
					}
	
	if (null != propertyRestRequesGettDTO.getSalePurchaseDate() && !propertyRestRequesGettDTO.getSalePurchaseDate().trim().isEmpty()) {
		if (isValidDate(propertyRestRequesGettDTO.getSalePurchaseDate())) {
			Date salePurchaseDate = DateUtil.convertDate(locale, propertyRestRequesGettDTO.getSalePurchaseDate());
			if (salePurchaseDate.after(currDate)) 
				errors.add("salePurchaseDate", new ActionMessage("error.future.date"));
			else
				propertyRestRequesGettDTO.setSalePurchaseDate(propertyRestRequesGettDTO.getSalePurchaseDate());
			} else {
			errors.add("salePurchaseDate", new ActionMessage("error.date.format"));
			}
		} else {
			propertyRestRequestSetDTO.setSalePurchaseDate("");
		}
	
	
	
	if(ismorCreAdd) {
		if(null != propertyRestRequesGettDTO.getLegalAuditDate() && !propertyRestRequesGettDTO.getLegalAuditDate().trim().isEmpty()){
			if(isValidDate(propertyRestRequesGettDTO.getLegalAuditDate()))
				propertyRestRequesGettDTO.setLegalAuditDate(propertyRestRequesGettDTO.getLegalAuditDate());
			else
				errors.add("legalAuditDate",new ActionMessage("error.date.format"));
			}
		}
	
	
	if(ismorCreAdd){
	if(null != propertyRestRequesGettDTO.getInterveingPeriSearchDate() && !propertyRestRequesGettDTO.getInterveingPeriSearchDate().trim().isEmpty())
				    {
		if(isValidDate(propertyRestRequesGettDTO.getInterveingPeriSearchDate()))
			propertyRestRequesGettDTO.setInterveingPeriSearchDate(propertyRestRequesGettDTO.getInterveingPeriSearchDate());
		 else
			 errors.add("interveingPeriSearchDate",new ActionMessage("error.date.format"));
		}else{
				errors.add("interveingPeriSearchDate",new ActionMessage("error.string.mandatory"));
			
			}
	}else {

		if(null != propertyRestRequesGettDTO.getInterveingPeriSearchDate() && !propertyRestRequesGettDTO.getInterveingPeriSearchDate().trim().isEmpty())
					    {
			if(isValidDate(propertyRestRequesGettDTO.getInterveingPeriSearchDate()))
				propertyRestRequesGettDTO.setInterveingPeriSearchDate(propertyRestRequesGettDTO.getInterveingPeriSearchDate());
			 else
				 errors.add("interveingPeriSearchDate",new ActionMessage("error.date.format"));
			}else{					
				propertyRestRequesGettDTO.setInterveingPeriSearchDate("");
				}
		
	}
	
	
	if(!isDatePresent || ismorCreAdd) {
		if(null != propertyRestRequesGettDTO.getTypeOfMargage() && !propertyRestRequesGettDTO.getTypeOfMargage().trim().isEmpty()){
			try {
				Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getTypeOfMargage().trim(),"typeOfMargage",errors,"MORTGAGE_TYPE");
				if(!(obj instanceof ActionErrors)){
					propertyRestRequesGettDTO.setTypeOfMargage(((ICommonCodeEntry)obj).getEntryCode());
					}else{
						errors.add("typeOfMargage",new ActionMessage("error.invalid.field.value"));
						}
				}catch(Exception e){
					DefaultLogger.error(this, "typeOfMargage - "+e.getMessage());
					errors.add("typeOfMargage",new ActionMessage("error.invalid.typeOfMargage"));
					}
			}else {
				errors.add("typeOfMargage",new ActionMessage("error.string.mandatory"));
			}
		}
	
	if (isDatePresent && !ismorCreAdd) {
		propertyRestRequestSetDTO.setSalePurchaseValue("");
		reqobj.setCersaiId("");
		reqobj.setCersaiTransactionRefNumber("");
		reqobj.setDateOfCersaiRegisteration("");
		propertyRestRequestSetDTO.setLegalAuditDate("");
		propertyRestRequestSetDTO.setInterveingPeriSearchDate("");
		propertyRestRequestSetDTO.setTypeOfMargage("");

	}
	
	if(null != propertyRestRequesGettDTO.getDocumentReceived() && !propertyRestRequesGettDTO.getDocumentReceived().trim().isEmpty())
				    {
					if("Y".equalsIgnoreCase(propertyRestRequesGettDTO.getDocumentReceived().trim()) || "N".equalsIgnoreCase(propertyRestRequesGettDTO.getDocumentReceived().trim()))
					 propertyRestRequestSetDTO.setDocumentReceived(propertyRestRequesGettDTO.getDocumentReceived().trim());
					else
					{
						errors.add("documentReceived",new ActionMessage("error.invalid.documentReceived"));
					}
				    }
				 else{
					 propertyRestRequestSetDTO.setDocumentReceived("");
					}
	if(null != propertyRestRequesGettDTO.getMorgageCreatedBy() && !propertyRestRequesGettDTO.getMorgageCreatedBy().trim().isEmpty())
				    {

		Object obj = masterObj.getObjectforSysMasterBank("actualOBSystemBank", propertyRestRequesGettDTO.getMorgageCreatedBy().trim(),"morgageCreatedBy",errors);
		if(!(obj instanceof ActionErrors)){
			propertyRestRequesGettDTO.setMorgageCreatedBy(Long.toString(((ISystemBank)obj).getId()));
		}
		Object obj1 = masterObj.getObjectforSysMasterBank("actualOtherBank", propertyRestRequesGettDTO.getMorgageCreatedBy().trim(),"morgageCreatedBy",errors);
		if(!(obj1 instanceof ActionErrors)){
			propertyRestRequesGettDTO.setMorgageCreatedBy(Long.toString(((IOtherBank)obj1).getId()));
		}
		if( (obj1 instanceof ActionErrors) && (obj instanceof ActionErrors))
		 {
			 errors.add("morgageCreatedBy",new ActionMessage("error.invalid.field.value")); 
		 }
				//	 propertyRestRequestSetDTO.setMorgageCreatedBy(propertyRestRequesGettDTO.getMorgageCreatedBy().trim());
				    }
				 else{
					 propertyRestRequestSetDTO.setMorgageCreatedBy("");
					}
	if(null != propertyRestRequesGettDTO.getBinNumber() && !propertyRestRequesGettDTO.getBinNumber().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setBinNumber(propertyRestRequesGettDTO.getBinNumber().trim());
				    }
	else if(null != propertyRestRequesGettDTO.getBinNumber() && propertyRestRequesGettDTO.getBinNumber().trim().isEmpty())
	{
					 propertyRestRequestSetDTO.setBinNumber("");
					}
	if(null != propertyRestRequesGettDTO.getDocumentBlock() && !propertyRestRequesGettDTO.getDocumentBlock().trim().isEmpty())
				{
		if ("on".equalsIgnoreCase(propertyRestRequesGettDTO.getDocumentBlock().trim())
				|| "off".equalsIgnoreCase(propertyRestRequesGettDTO.getDocumentBlock().trim()))
			propertyRestRequestSetDTO.setDocumentBlock(propertyRestRequesGettDTO.getDocumentBlock().trim());
		else
			errors.add("documentBlock", new ActionMessage("error.invalid.value"));
				}
	else if(null != propertyRestRequesGettDTO.getDocumentBlock() && propertyRestRequesGettDTO.getDocumentBlock().trim().isEmpty())
    {
	 propertyRestRequestSetDTO.setDocumentBlock("");
    }
if(null != propertyRestRequesGettDTO.getClaim() && !propertyRestRequesGettDTO.getClaim().trim().isEmpty())
				    {
		if("Y".equalsIgnoreCase(propertyRestRequesGettDTO.getClaim().trim()) || "N".equalsIgnoreCase(propertyRestRequesGettDTO.getClaim().trim()))
			 propertyRestRequestSetDTO.setClaim(propertyRestRequesGettDTO.getClaim().trim());
			else
			{
				errors.add("claim",new ActionMessage("error.invalid.claim"));
			}
				    }
				 else{
					 errors.add("claim",new ActionMessage("error.string.mandatory"));
					}
	if(null != propertyRestRequesGettDTO.getClaim() && !propertyRestRequesGettDTO.getClaim().trim().isEmpty())
	{
		if("Y".equalsIgnoreCase(propertyRestRequesGettDTO.getClaim().trim()))
		{
	if(null != propertyRestRequesGettDTO.getClaimType() && !propertyRestRequesGettDTO.getClaimType().trim().isEmpty())
		
				    {
		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getClaimType().trim(),"claimType",errors,"CLAIM_TYPE");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setClaimType(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("claimType",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "claimType - "+e.getMessage());
				errors.add("claimType",new ActionMessage("error.invalid.claimType"));
			}
				    }
				 else{
					 propertyRestRequestSetDTO.setClaimType("");
					}
		}
		else if("N".equalsIgnoreCase(propertyRestRequesGettDTO.getClaim().trim()))
		{
			propertyRestRequestSetDTO.setClaimType("");
		}
	
	}
	System.out.println("mortageRegisteredRef---1916>"+propertyRestRequesGettDTO.getMortageRegisteredRef());
	DefaultLogger.debug(this, "mortageRegisteredRef---1917>"+propertyRestRequesGettDTO.getMortageRegisteredRef());
	if(null != propertyRestRequesGettDTO.getMortageRegisteredRef() && !propertyRestRequesGettDTO.getMortageRegisteredRef().trim().isEmpty())
				    {
		System.out.println("mortageRegisteredRef--->1920"+propertyRestRequesGettDTO.getMortageRegisteredRef());
		DefaultLogger.debug(this, "mortageRegisteredRef--->1921"+propertyRestRequesGettDTO.getMortageRegisteredRef());
					 propertyRestRequestSetDTO.setMortageRegisteredRef(propertyRestRequesGettDTO.getMortageRegisteredRef().trim());
				    }
	else if(null != propertyRestRequesGettDTO.getMortageRegisteredRef() && propertyRestRequesGettDTO.getMortageRegisteredRef().trim().isEmpty())
    {
			propertyRestRequestSetDTO.setMortageRegisteredRef("");
    }
	if(null != propertyRestRequesGettDTO.getAdvocateLawyerName() && !propertyRestRequesGettDTO.getAdvocateLawyerName().trim().isEmpty())
				    {
		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getAdvocateLawyerName().trim(),"advocateLawyerName",errors,"ADV_NAME");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setAdvocateLawyerName(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("advocateLawyerName",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "advocateLawyerName - "+e.getMessage());
				errors.add("advocateLawyerName",new ActionMessage("error.invalid.advocateLawyerName"));
			}
				    }
				 else{
					 propertyRestRequestSetDTO.setAdvocateLawyerName("");
					}
	if(null != propertyRestRequesGettDTO.getDevGrpCo() && !propertyRestRequesGettDTO.getDevGrpCo().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setDevGrpCo(propertyRestRequesGettDTO.getDevGrpCo().trim());
				    }
				 else{
					 propertyRestRequestSetDTO.setDevGrpCo("");
					}
	if(null != propertyRestRequesGettDTO.getProjectName() && !propertyRestRequesGettDTO.getProjectName().trim().isEmpty())
				    {
		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getProjectName().trim(),"projectName",errors,"DEALER");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setProjectName(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("projectName",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "projectName - "+e.getMessage());
				errors.add("projectName",new ActionMessage("error.invalid.field.value"));
			}
				    }
	else if(null != propertyRestRequesGettDTO.getProjectName() && propertyRestRequesGettDTO.getProjectName().trim().isEmpty())
	{
					 propertyRestRequestSetDTO.setProjectName("");
					}
	if(null != propertyRestRequesGettDTO.getLotNumberPrefix() && !propertyRestRequesGettDTO.getLotNumberPrefix().trim().isEmpty())
				    {

		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getLotNumberPrefix().trim(),"lotNumberPrefix",errors,"LOT_NO");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setLotNumberPrefix(((ICommonCodeEntry)obj).getEntryCode());
				if(null == propertyRestRequesGettDTO.getLotNo() || propertyRestRequesGettDTO.getLotNo().trim().isEmpty()) {
					 errors.add("lotNo",new ActionMessage("error.string.mandatory"));
				}
			 }
			 else
			 {
				 errors.add("lotNumberPrefix",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "lotNumberPrefix - "+e.getMessage());
				errors.add("lotNumberPrefix",new ActionMessage("error.invalid.field.value"));
			}
				    
				    }
				 else{
					 propertyRestRequestSetDTO.setLotNumberPrefix("");
					}
	if(null != propertyRestRequesGettDTO.getLotNo() && !propertyRestRequesGettDTO.getLotNo().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setLotNo(propertyRestRequesGettDTO.getLotNo().trim());
				    }
				 else{
					 propertyRestRequestSetDTO.setLotNo("");
					}
	if(null != propertyRestRequesGettDTO.getPropertyLotLocation() && !propertyRestRequesGettDTO.getPropertyLotLocation().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setPropertyLotLocation(propertyRestRequesGettDTO.getPropertyLotLocation().trim());
				    }
	else if(null != propertyRestRequesGettDTO.getPropertyLotLocation() && propertyRestRequesGettDTO.getPropertyLotLocation().trim().isEmpty())
    {
	 propertyRestRequestSetDTO.setPropertyLotLocation("");
    }
	if(null != propertyRestRequesGettDTO.getOtherCity() && !propertyRestRequesGettDTO.getOtherCity().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setOtherCity(propertyRestRequesGettDTO.getOtherCity().trim());
				    }
	else if(null != propertyRestRequesGettDTO.getOtherCity() && propertyRestRequesGettDTO.getOtherCity().trim().isEmpty())
    {
	 propertyRestRequestSetDTO.setOtherCity("");
    }
	if(null != propertyRestRequesGettDTO.getPropertyAddress() && !propertyRestRequesGettDTO.getPropertyAddress().trim().isEmpty())
				    {
		
					 propertyRestRequestSetDTO.setPropertyAddress(propertyRestRequesGettDTO.getPropertyAddress().trim());
				    }
				 else{
					 propertyRestRequestSetDTO.setPropertyAddress("");
					}
	if(null != propertyRestRequesGettDTO.getPropertyAddress2() && !propertyRestRequesGettDTO.getPropertyAddress2().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setPropertyAddress2(propertyRestRequesGettDTO.getPropertyAddress2().trim());
				    }
	else if(null != propertyRestRequesGettDTO.getPropertyAddress2() && propertyRestRequesGettDTO.getPropertyAddress2().trim().isEmpty())
    {
	 propertyRestRequestSetDTO.setPropertyAddress2("");
    }
	if(null != propertyRestRequesGettDTO.getPropertyAddress3() && !propertyRestRequesGettDTO.getPropertyAddress3().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setPropertyAddress3(propertyRestRequesGettDTO.getPropertyAddress3().trim());
				    }
	else if(null != propertyRestRequesGettDTO.getPropertyAddress3() && propertyRestRequesGettDTO.getPropertyAddress3().trim().isEmpty())
    {
	 propertyRestRequestSetDTO.setPropertyAddress3("");
    }
	if(null != propertyRestRequesGettDTO.getPropertyAddress4() && !propertyRestRequesGettDTO.getPropertyAddress4().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setPropertyAddress4(propertyRestRequesGettDTO.getPropertyAddress4().trim());
				    }
	else if(null != propertyRestRequesGettDTO.getPropertyAddress4() && propertyRestRequesGettDTO.getPropertyAddress4().trim().isEmpty())
    {
	 propertyRestRequestSetDTO.setPropertyAddress4("");
    }
	if(null != propertyRestRequesGettDTO.getPropertyAddress5() && !propertyRestRequesGettDTO.getPropertyAddress5().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setPropertyAddress5(propertyRestRequesGettDTO.getPropertyAddress5().trim());
				    }
	else if(null != propertyRestRequesGettDTO.getPropertyAddress5() && propertyRestRequesGettDTO.getPropertyAddress5().trim().isEmpty())
    {
	 propertyRestRequestSetDTO.setPropertyAddress5("");
    }
	if(null != propertyRestRequesGettDTO.getPropertyAddress6() && !propertyRestRequesGettDTO.getPropertyAddress6().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setPropertyAddress6(propertyRestRequesGettDTO.getPropertyAddress6().trim());
				    }
	else if(null != propertyRestRequesGettDTO.getPropertyAddress6() && propertyRestRequesGettDTO.getPropertyAddress6().trim().isEmpty())
    {
	 propertyRestRequestSetDTO.setPropertyAddress6("");
    }
	if(null != propertyRestRequesGettDTO.getDescription() && !propertyRestRequesGettDTO.getDescription().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setDescription(propertyRestRequesGettDTO.getDescription().trim());
				    }
				 else{
					 propertyRestRequestSetDTO.setDescription("");
					}
	if(null != propertyRestRequesGettDTO.getEnvRiskyStatus() && !propertyRestRequesGettDTO.getEnvRiskyStatus().trim().isEmpty())
				    {
		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getEnvRiskyStatus().trim(),"envRiskyStatus",errors,"12");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setEnvRiskyStatus(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("envRiskyStatus",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "envRiskyStatus"+e.getMessage());
				errors.add("envRiskyStatus",new ActionMessage("error.invalid.envRiskyStatus"));
			}
	    }
	else if(null != propertyRestRequesGettDTO.getEnvRiskyStatus() && propertyRestRequesGettDTO.getEnvRiskyStatus().trim().isEmpty())
	{
					 propertyRestRequestSetDTO.setEnvRiskyStatus("");
					}
	if(null != propertyRestRequesGettDTO.getEnvRiskyDate() && !propertyRestRequesGettDTO.getEnvRiskyDate().trim().isEmpty())
				{

					if (isValidDate(
							propertyRestRequesGettDTO.getEnvRiskyDate()))
						propertyRestRequesGettDTO.setEnvRiskyDate(
								propertyRestRequesGettDTO.getEnvRiskyDate());
					else
						errors.add("envRiskyDate", new ActionMessage(
								"error.date.format"));
					propertyRestRequestSetDTO.setEnvRiskyDate(
							propertyRestRequesGettDTO.getEnvRiskyDate().trim());
				}
				 else{
					 propertyRestRequestSetDTO.setEnvRiskyDate("");
					}
	if(null != propertyRestRequesGettDTO.getTsrDate() && !propertyRestRequesGettDTO.getTsrDate().trim().isEmpty())
				{
					if (isValidDate(
							propertyRestRequesGettDTO.getTsrDate()))
						propertyRestRequesGettDTO.setTsrDate(
								propertyRestRequesGettDTO.getTsrDate());
					else
						errors.add("tsrDate", new ActionMessage(
								"error.date.format"));
				}
				 else{
					 propertyRestRequestSetDTO.setTsrDate("");
					}
	if(null != propertyRestRequesGettDTO.getTsrFrequency() && !propertyRestRequesGettDTO.getTsrFrequency().trim().isEmpty())
				    {
		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getTsrFrequency().trim(),"tsrFrequency",errors,"FREQUENCY");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setTsrFrequency(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("tsrFrequency",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "tsrFrequency"+e.getMessage());
				errors.add("tsrFrequency",new ActionMessage("error.invalid.tsrFrequency"));
			}
	    }
				 else{
					 propertyRestRequestSetDTO.setTsrFrequency("");
					}
	if(null != propertyRestRequesGettDTO.getNextTsrDate() && !propertyRestRequesGettDTO.getNextTsrDate().trim().isEmpty())
				{
					if (isValidDate(propertyRestRequesGettDTO.getNextTsrDate()))
						propertyRestRequesGettDTO.setNextTsrDate(
								propertyRestRequesGettDTO.getNextTsrDate());
					else
						errors.add("nextTsrDate", new ActionMessage(
								"error.date.format"));
				}
				 else{
					 propertyRestRequestSetDTO.setNextTsrDate("");
					}
	
	if(ismorCreAdd && !isDatePresent) {
	if(null != propertyRestRequesGettDTO.getCersiaRegistrationDate() && !propertyRestRequesGettDTO.getCersiaRegistrationDate().trim().isEmpty())
		{
		if (isValidDate(propertyRestRequesGettDTO.getCersiaRegistrationDate()))
		propertyRestRequesGettDTO.setCersiaRegistrationDate(
		propertyRestRequesGettDTO.getCersiaRegistrationDate());
					else
						errors.add("cersiaRegistrationDate", new ActionMessage("error.date.format"));
		
				    }}
				 else{
					 propertyRestRequestSetDTO.setCersiaRegistrationDate("");
					}
	if(null != propertyRestRequesGettDTO.getConstitution() && !propertyRestRequesGettDTO.getConstitution().trim().isEmpty())
				    {
		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getConstitution().trim(),"constitution",errors,"Entity");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setConstitution(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("constitution",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "constitution"+e.getMessage());
				errors.add("constitution",new ActionMessage("error.invalid.constitution"));
			}
	    }
				 else{
					 propertyRestRequestSetDTO.setConstitution("");
					}
	if(null != propertyRestRequesGettDTO.getEnvRiskyRemarks() && !propertyRestRequesGettDTO.getEnvRiskyRemarks().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setEnvRiskyRemarks(propertyRestRequesGettDTO.getEnvRiskyRemarks().trim());
				    }
	else if(null != propertyRestRequesGettDTO.getEnvRiskyRemarks() && propertyRestRequesGettDTO.getEnvRiskyRemarks().trim().isEmpty())
    {
	 propertyRestRequestSetDTO.setEnvRiskyRemarks("");
    }
if(null != propertyRestRequesGettDTO.getRevalOverride() && !propertyRestRequesGettDTO.getRevalOverride().trim().isEmpty())
				    {
	if("on".equalsIgnoreCase(propertyRestRequesGettDTO.getRevalOverride().trim()) || "off".equalsIgnoreCase(propertyRestRequesGettDTO.getRevalOverride().trim()))
					 propertyRestRequestSetDTO.setRevalOverride(propertyRestRequesGettDTO.getRevalOverride().trim());
	else
		errors.add("revalOverride",new ActionMessage("error.invalid.field.value"));
				    }
				 else{
					 propertyRestRequestSetDTO.setRevalOverride("");
					}
	if(null != propertyRestRequesGettDTO.getRevaluation_v1_add() && !propertyRestRequesGettDTO.getRevaluation_v1_add().trim().isEmpty())
				    {

		if("Y".equalsIgnoreCase(propertyRestRequesGettDTO.getRevaluation_v1_add().trim()) || "N".equalsIgnoreCase(propertyRestRequesGettDTO.getRevaluation_v1_add().trim()))
			 propertyRestRequestSetDTO.setRevaluation_v1_add(propertyRestRequesGettDTO.getRevaluation_v1_add().trim());
			else
			{
				errors.add("revaluation_v1_add",new ActionMessage("error.invalid.value.YN"));
			}
				    }
				 /*else{
					 errors.add("revaluation_v1_add",new ActionMessage("error.string.mandatory"));
					}*/
	if(null != propertyRestRequesGettDTO.getValuationDate_v1() && !propertyRestRequesGettDTO.getValuationDate_v1().trim().isEmpty())
				    {
		if (isValidDate(propertyRestRequesGettDTO.getValuationDate_v1()))
			propertyRestRequesGettDTO.setValuationDate_v1(
					propertyRestRequesGettDTO.getValuationDate_v1());
		else
			errors.add("valuationDate_v1", new ActionMessage(
					"error.date.format"));		
					 propertyRestRequestSetDTO.setValuationDate_v1(propertyRestRequesGettDTO.getValuationDate_v1().trim());
				    }
				 else{
						 errors.add("valuationDate_v1",new ActionMessage("error.string.mandatory"));
					}
	if(null != propertyRestRequesGettDTO.getValuatorCompany_v1() && !propertyRestRequesGettDTO.getValuatorCompany_v1().trim().isEmpty())
				    {
		Object obj = masterObj.getObjectforMasterRelatedCode("actualOBValuationAgency", propertyRestRequesGettDTO.getValuatorCompany_v1(),"valuatorCompany_v1",errors);
		if(!(obj instanceof ActionErrors)){
			propertyRestRequesGettDTO.setValuatorCompany_v1(((IValuationAgency)obj).getId()+"");
		}
		else
		{
			errors.add("valuatorCompany_v1", new ActionMessage("error.invalid.field.value"));
		}
				    }
				 else{
					 errors.add("valuatorCompany_v1",new ActionMessage("error.string.mandatory"));
					}
	if(null != propertyRestRequesGettDTO.getCategoryOfLandUse_v1() && !propertyRestRequesGettDTO.getCategoryOfLandUse_v1().trim().isEmpty())
				    {
		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getCategoryOfLandUse_v1().trim(),"categoryOfLandUse_v1",errors,"LAND_USE_CAT");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setCategoryOfLandUse_v1(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("categoryOfLandUse_v1",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "categoryOfLandUse_v1 - "+e.getMessage());
				errors.add("categoryOfLandUse_v1",new ActionMessage("error.invalid.field.value"));
			}
				    }
	else if(null != propertyRestRequesGettDTO.getCategoryOfLandUse_v1() && propertyRestRequesGettDTO.getCategoryOfLandUse_v1().trim().isEmpty())
	{
					 propertyRestRequestSetDTO.setCategoryOfLandUse_v1("");
					}
	if(null != propertyRestRequesGettDTO.getDeveloperName_v1() && !propertyRestRequesGettDTO.getDeveloperName_v1().trim().isEmpty())
				    {
		if(propertyRestRequesGettDTO.getDeveloperName_v1().length() <= 150)
					 propertyRestRequestSetDTO.setDeveloperName_v1(propertyRestRequesGettDTO.getDeveloperName_v1().trim());
		else
			errors.add("developerName_v1",new ActionMessage("error.field.length.exceeded", "150"));
				    }
	else if(null != propertyRestRequesGettDTO.getDeveloperName_v1() && propertyRestRequesGettDTO.getDeveloperName_v1().trim().isEmpty())
    {
	 propertyRestRequestSetDTO.setDeveloperName_v1("");
    }
	if(null != propertyRestRequesGettDTO.getCountry_v1() && !propertyRestRequesGettDTO.getCountry_v1().trim().isEmpty())
				    {
		// For Country
		 
				Object obj = masterObj.getObjectforMasterRelatedCode("actualCountry",
						propertyRestRequesGettDTO.getCountry_v1(), "country_v1",
						errors);
				if (!(obj instanceof ActionErrors)) {
					propertyRestRequesGettDTO.setCountry_v1(Long.toString(((ICountry) obj).getIdCountry()));
					// For Region
					if (null != propertyRestRequesGettDTO.getRegion_v1()
							&& !propertyRestRequesGettDTO.getRegion_v1().trim().isEmpty()) {

						Object objReg = masterObj.getObjectforMasterRelatedCode("actualRegion",
								propertyRestRequesGettDTO.getRegion_v1().trim(), "region_v1",
								errors);
						if (!(objReg instanceof ActionErrors)) {
							propertyRestRequesGettDTO.setRegion_v1(Long.toString(((IRegion) objReg).getIdRegion()));
							//check if region is present in list of country
							if(propertyRestRequesGettDTO.getCountry_v1().trim().
							equalsIgnoreCase(Long.toString(((IRegion) objReg).getCountryId().getIdCountry()))){
								// For State
								if (null != propertyRestRequesGettDTO.getLocationState_v1()
										&& !propertyRestRequesGettDTO.getLocationState_v1().trim().isEmpty()) {
									Object objState = masterObj.getObjectforMasterRelatedCode("actualState",
											propertyRestRequesGettDTO.getLocationState_v1().trim(), "locationState_v1",
											errors);
									if (!(objState instanceof ActionErrors)) {
										propertyRestRequesGettDTO.setLocationState_v1(Long.toString(((IState) objState).getIdState()));
										
										//check if state is present in list of region
										if(propertyRestRequesGettDTO.getRegion_v1().trim().
										equalsIgnoreCase(Long.toString(((IState) objState).getRegionId().getIdRegion()))){
											//For City
											if (null != propertyRestRequesGettDTO.getNearestCity_v1()
														&& !propertyRestRequesGettDTO.getNearestCity_v1().trim().isEmpty()) {

												Object objCity = masterObj.getObjectforMasterRelatedCode("actualCity",
														propertyRestRequesGettDTO.getNearestCity_v1().trim(), "nearestCity_v1",
														errors);
												if (!(objCity instanceof ActionErrors)) {
													//check if given city is present in list of state
													if(propertyRestRequesGettDTO.getLocationState_v1().trim().
													equalsIgnoreCase(Long.toString(((ICity) objCity).getStateId().getIdState()))){
														propertyRestRequesGettDTO.setNearestCity_v1(Long.toString(((ICity) objCity).getIdCity()));
													}else {
														System.out.println("Given City is not Present in the state list of State");
														errors.add("nearestCity_v1", new ActionMessage("error.invalid.field.value"));
													}
												
												
												} else {
													errors.add("nearestCity_v1", new ActionMessage("error.invalid.field.value"));
												}
											}
											else
											{
												errors.add("nearestCity_v1", new ActionMessage("error.string.mandatory"));
											}
											
										} else {
											System.out.println("Given State is not Present in the state list of Region");
											errors.add("locationState_v1", new ActionMessage("error.invalid.field.value"));
										}
										
									} else {
										errors.add("locationState_v1", new ActionMessage("error.invalid.field.value"));
									}
								}
								else
								{
									errors.add("locationState_v1", new ActionMessage("error.string.mandatory"));
								}
							}else {
								System.out.println("Given Region is not Present in the list of Country");
								errors.add("region_v1", new ActionMessage("error.invalid.field.value"));
							}
							
						} else {
							errors.add("region_v1", new ActionMessage("error.invalid.field.value"));
						}
					
					}
					else
					{
						errors.add("region_v1", new ActionMessage("error.string.mandatory"));
					}
				} else {
					errors.add("country_v1", new ActionMessage("error.invalid.field.value"));
				}
			} else {
				errors.add("country_v1", new ActionMessage("error.string.mandatory"));
			}
		
					
	if(null != propertyRestRequesGettDTO.getPinCode_v1() && !propertyRestRequesGettDTO.getPinCode_v1().trim().isEmpty())
				    {
			HashMap<String, String> pincodeMap = null;
			try {
			pincodeMap = (HashMap<String, String>) pinDao.getActiveStatePinCodeMap1();
			String pincodesStr = UIUtil.getDelimitedStringFromMap(pincodeMap, ",", "=");
			Map<String, String> statePincodeMap = UIUtil.getMapFromDelimitedString(pincodesStr, ",", "=");
			 if(null != statePincodeMap && !statePincodeMap.isEmpty()) {
				 String selectedStatePincode = statePincodeMap.get(propertyRestRequesGettDTO.getLocationState_v1());
				 if(null != selectedStatePincode && !propertyRestRequesGettDTO.getPinCode_v1().trim().startsWith(selectedStatePincode)) {
					 errors.add("pinCode_v1", new ActionMessage("error.pincode.incorrect"));
				 }
			 }
			}catch(Exception e) {
				System.out.println("Exception for pinCode_v1 in securityrestDTOMapper.java..=> e=>"+e);
			}
					 propertyRestRequestSetDTO.setPinCode_v1(propertyRestRequesGettDTO.getPinCode_v1().trim());
				    }
				 else{
					 errors.add("pinCode_v1",new ActionMessage("error.string.mandatory"));
					}
	if(null != propertyRestRequesGettDTO.getLandArea_v1() && !propertyRestRequesGettDTO.getLandArea_v1().trim().isEmpty())
				    {

		if(ASSTValidator.isNumeric(propertyRestRequesGettDTO.getLandArea_v1()))
			propertyRestRequestSetDTO.setLandArea_v1(propertyRestRequesGettDTO.getLandArea_v1().trim());
		else
			errors.add("landArea_v1", new ActionMessage("error.string.invalidNumbers"));
	    
				    }
				 else{
					 errors.add("landArea_v1",new ActionMessage("error.string.mandatory"));
					}
	if(null != propertyRestRequesGettDTO.getLandAreaUOM_v1() && !propertyRestRequesGettDTO.getLandAreaUOM_v1().trim().isEmpty())
				    {

		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getLandAreaUOM_v1().trim(),"landAreaUOM_v1",errors,"AREA_UOM");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setLandAreaUOM_v1(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("landAreaUOM_v1",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "landAreaUOM_v1 - "+e.getMessage());
				errors.add("landAreaUOM_v1",new ActionMessage("error.invalid.field.value"));
			}
				    
				    }
				 else{
					 errors.add("landAreaUOM_v1",new ActionMessage("error.string.mandatory"));
					}
	if(null != propertyRestRequesGettDTO.getBuiltupArea_v1() && !propertyRestRequesGettDTO.getBuiltupArea_v1().trim().isEmpty())
				    {
						{
							if(ASSTValidator.isNumeric(propertyRestRequesGettDTO.getBuiltupArea_v1()))
								propertyRestRequestSetDTO.setBuiltupArea_v1(propertyRestRequesGettDTO.getBuiltupArea_v1().trim());
							else
								errors.add("builtupArea_v1", new ActionMessage("error.string.invalidNumbers"));
						   }
				    }
				 else{
					 errors.add("builtupArea_v1",new ActionMessage("error.string.mandatory"));
					}
	if(null != propertyRestRequesGettDTO.getBuiltupAreaUOM_v1() && !propertyRestRequesGettDTO.getBuiltupAreaUOM_v1().trim().isEmpty())
				    {
		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getBuiltupAreaUOM_v1().trim(),"builtupAreaUOM_v1",errors,"AREA_UOM");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setBuiltupAreaUOM_v1(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("builtupAreaUOM_v1",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "builtupAreaUOM_v1 - "+e.getMessage());
				errors.add("builtupAreaUOM_v1",new ActionMessage("error.invalid.field.value"));
			}
				    }
				 else{
					 errors.add("builtupAreaUOM_v1",new ActionMessage("error.string.mandatory"));
					}
	boolean isValidBuildValueV1 = false;
	boolean isValidLandValueV1 = false;
	if(null != propertyRestRequesGettDTO.getLandValue_v1() && !propertyRestRequesGettDTO.getLandValue_v1().trim().isEmpty())
				    {
		{
		if(ASSTValidator.isNumeric(propertyRestRequesGettDTO.getLandValue_v1()))
		{
			propertyRestRequestSetDTO.setLandValue_v1(propertyRestRequesGettDTO.getLandValue_v1().trim());
			isValidLandValueV1 = true;
		}
		else
			errors.add("landValue_v1", new ActionMessage("error.string.invalidNumbers"));
	    }
    }
				 else{
					 errors.add("landValue_v1",new ActionMessage("error.string.mandatory"));
					}
	if(null != propertyRestRequesGettDTO.getBuildingValue_v1() && !propertyRestRequesGettDTO.getBuildingValue_v1().trim().isEmpty())
				    {
		{
		if(ASSTValidator.isNumeric(propertyRestRequesGettDTO.getBuildingValue_v1()))
		{
			propertyRestRequestSetDTO.setBuildingValue_v1(propertyRestRequesGettDTO.getBuildingValue_v1().trim());
			isValidBuildValueV1 = true;
		}
		else
			errors.add("buildingValue_v1", new ActionMessage("error.string.invalidNumbers"));
	    }
				    }
				 else{
					 errors.add("buildingValue_v1",new ActionMessage("error.string.mandatory"));
					}
	
	
	if(isValidLandValueV1 && isValidBuildValueV1)
	{
	if(null != propertyRestRequestSetDTO.getBuildingValue_v1()
			&& !propertyRestRequestSetDTO.getBuildingValue_v1()
			.trim().isEmpty() && null != propertyRestRequestSetDTO.getLandValue_v1()
			&& !propertyRestRequestSetDTO.getLandValue_v1().trim()
					.isEmpty())
	{
		propertyRestRequestSetDTO.setTotalPropertyAmount_v1(totalValue(propertyRestRequestSetDTO.getLandValue_v1(),propertyRestRequestSetDTO.getBuildingValue_v1()));
	}
	}
	
	if(null != propertyRestRequesGettDTO.getReconstructionValueOfTheBuilding_v1() && !propertyRestRequesGettDTO.getReconstructionValueOfTheBuilding_v1().trim().isEmpty())
				    {
		{
			if(ASSTValidator.isNumeric(propertyRestRequesGettDTO.getReconstructionValueOfTheBuilding_v1()))
				propertyRestRequestSetDTO.setReconstructionValueOfTheBuilding_v1(propertyRestRequesGettDTO.getReconstructionValueOfTheBuilding_v1().trim());
			else
				errors.add("reconstructionValueOfTheBuilding_v1", new ActionMessage("error.string.invalidNumbers"));
		    }
				    }
				 else{
					 errors.add("reconstructionValueOfTheBuilding_v1",new ActionMessage("error.string.mandatory"));
					}
	if(null != propertyRestRequesGettDTO.getPropertyCompletionStatus_v1() && !propertyRestRequesGettDTO.getPropertyCompletionStatus_v1().trim().isEmpty())
				    {

		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getPropertyCompletionStatus_v1().trim(),"propertyCompletionStatus_v1",errors,"PROPERTY_COMPLETION_STATUS");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setPropertyCompletionStatus_v1(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("propertyCompletionStatus_v1",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "propertyCompletionStatus_v1 - "+e.getMessage());
				errors.add("propertyCompletionStatus_v1",new ActionMessage("error.invalid.field.value"));
			}
				    
				    }
	else if(null != propertyRestRequesGettDTO.getPropertyCompletionStatus_v1() && propertyRestRequesGettDTO.getPropertyCompletionStatus_v1().trim().isEmpty())
    {
					 propertyRestRequestSetDTO.setPropertyCompletionStatus_v1("");
					}
	if(null != propertyRestRequesGettDTO.getIsPhysicalInspection_v1() && !propertyRestRequesGettDTO.getIsPhysicalInspection_v1().trim().isEmpty())
				    {
					 if("true".equalsIgnoreCase(propertyRestRequesGettDTO.getIsPhysicalInspection_v1()) || "false".equalsIgnoreCase(propertyRestRequesGettDTO.getIsPhysicalInspection_v1()))
						 propertyRestRequestSetDTO.setIsPhysicalInspection_v1(propertyRestRequesGettDTO.getIsPhysicalInspection_v1().trim());
					 else
						 errors.add("isPhysicalInspection_v1", new ActionMessage("error.invalid.field.value"));
				    }
				 else{
					 errors.add("isPhysicalInspection_v1",new ActionMessage("error.string.mandatory"));
					}
	
	if(null != propertyRestRequesGettDTO.getIsPhysicalInspection_v1() && !propertyRestRequesGettDTO.getIsPhysicalInspection_v1().trim().isEmpty())
    {
	 if("true".equalsIgnoreCase(propertyRestRequesGettDTO.getIsPhysicalInspection_v1()))
	 {
		 if(null != propertyRestRequesGettDTO.getLastPhysicalInspectDate_v1() && !propertyRestRequesGettDTO.getLastPhysicalInspectDate_v1().trim().isEmpty())
			{
			if (isValidDate(propertyRestRequesGettDTO
					.getLastPhysicalInspectDate_v1()))
				propertyRestRequesGettDTO.setLastPhysicalInspectDate_v1(
						propertyRestRequesGettDTO
								.getLastPhysicalInspectDate_v1());
			else
				errors.add("lastPhysicalInspectDate_v1",
						new ActionMessage(
								"error.date.format"));
			}else
			{
				errors.add("lastPhysicalInspectDate_v1",
						new ActionMessage(
								"error.string.mandatory"));
			}
		 if(null != propertyRestRequesGettDTO.getNextPhysicalInspectDate_v1() && !propertyRestRequesGettDTO.getNextPhysicalInspectDate_v1().trim().isEmpty())
		    {
			 if (isValidDate(propertyRestRequesGettDTO
						.getNextPhysicalInspectDate_v1()))
					propertyRestRequesGettDTO.setNextPhysicalInspectDate_v1(
							propertyRestRequesGettDTO
									.getNextPhysicalInspectDate_v1());
				else
					errors.add("nextPhysicalInspectDate_v1",
							new ActionMessage(
									"error.date.format"));
		    }
		 else
			 errors.add("nextPhysicalInspectDate_v1",
						new ActionMessage(
								"error.string.mandatory"));
		 
		 if(null != propertyRestRequesGettDTO.getPhysicalInspectionFreqUnit_v1() && !propertyRestRequesGettDTO.getPhysicalInspectionFreqUnit_v1().trim().isEmpty())
		 {


			 try {
				 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getPhysicalInspectionFreqUnit_v1().trim(),"physicalInspectionFreqUnit_v1",errors,"FREQUENCY");
				 if(!(obj instanceof ActionErrors)){
					 propertyRestRequesGettDTO.setPhysicalInspectionFreqUnit_v1(((ICommonCodeEntry)obj).getEntryCode());
					}
				 else
				 {
					 errors.add("physicalInspectionFreqUnit_v1",new ActionMessage("error.invalid.field.value")); 
				 }
			 }
			 catch(Exception e){
					DefaultLogger.error(this, "physicalInspectionFreqUnit_v1"+e.getMessage());
					errors.add("physicalInspectionFreqUnit_v1",new ActionMessage("error.invalid.field.value"));
				}
					    
		 }
		 else
		 {
			 errors.add("physicalInspectionFreqUnit_v1",
						new ActionMessage(
								"error.string.mandatory"));
		 }
	 }
	  if("false".equalsIgnoreCase(propertyRestRequesGettDTO.getIsPhysicalInspection_v1()))
	  {
		  propertyRestRequesGettDTO.setLastPhysicalInspectDate_v1("");
		  propertyRestRequesGettDTO.setNextPhysicalInspectDate_v1("");
		  propertyRestRequesGettDTO.setPhysicalInspectionFreqUnit_v1("");
	  }
	
    }
	
	
	
	if(null != propertyRestRequesGettDTO.getRemarksProperty_v1() && !propertyRestRequesGettDTO.getRemarksProperty_v1().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setRemarksProperty_v1(propertyRestRequesGettDTO.getRemarksProperty_v1().trim());
				    }
	else if(null != propertyRestRequesGettDTO.getRemarksProperty_v1() && propertyRestRequesGettDTO.getRemarksProperty_v1().trim().isEmpty())
    {
					propertyRestRequestSetDTO.setRemarksProperty_v1("");
    }
if(null != propertyRestRequesGettDTO.getWaiver() && !propertyRestRequesGettDTO.getWaiver().trim().isEmpty())
				    {
	if ("on".equalsIgnoreCase(propertyRestRequesGettDTO.getWaiver().trim())
			|| "off".equalsIgnoreCase(propertyRestRequesGettDTO.getWaiver().trim()))
					 propertyRestRequestSetDTO.setWaiver(propertyRestRequesGettDTO.getWaiver().trim());
	else
		errors.add("waiver", new ActionMessage("error.invalid.value"));
				    }
else if(null != propertyRestRequesGettDTO.getWaiver() && propertyRestRequesGettDTO.getWaiver().trim().isEmpty())
{
 propertyRestRequestSetDTO.setWaiver("");
}
    
	if(null != propertyRestRequesGettDTO.getDeferral() && !propertyRestRequesGettDTO.getDeferral().trim().isEmpty())
				    {
		if ("on".equalsIgnoreCase(propertyRestRequesGettDTO.getDeferral().trim())
				|| "off".equalsIgnoreCase(propertyRestRequesGettDTO.getDeferral().trim()))
					 propertyRestRequestSetDTO.setDeferral(propertyRestRequesGettDTO.getDeferral().trim());
		else
			errors.add("deferral", new ActionMessage("error.invalid.value"));
				    }
	else if(null != propertyRestRequesGettDTO.getDeferral() || propertyRestRequesGettDTO.getDeferral().trim().isEmpty()){
		propertyRestRequestSetDTO.setDeferral("");
		}
	
	if(null != propertyRestRequesGettDTO.getDeferral() && !propertyRestRequesGettDTO.getDeferral().trim().isEmpty()
			&& ("on".equalsIgnoreCase(propertyRestRequesGettDTO.getDeferral().trim()) )) {
		if(null != propertyRestRequesGettDTO.getDeferralId() && !propertyRestRequesGettDTO.getDeferralId().trim().isEmpty()){
			propertyRestRequestSetDTO.setDeferralId(propertyRestRequesGettDTO.getDeferralId().trim());
			}
		} else {
			propertyRestRequestSetDTO.setDeferralId("");
			}
	
	
	if(null != propertyRestRequesGettDTO.getRevaluation_v2_add() && !propertyRestRequesGettDTO.getRevaluation_v2_add().trim().isEmpty())
				    {

		if("Y".equalsIgnoreCase(propertyRestRequesGettDTO.getRevaluation_v2_add().trim()) || "N".equalsIgnoreCase(propertyRestRequesGettDTO.getRevaluation_v2_add().trim()))
			 propertyRestRequestSetDTO.setRevaluation_v2_add(propertyRestRequesGettDTO.getRevaluation_v2_add().trim());
			else
			{
				errors.add("revaluation_v2_add",new ActionMessage("error.invalid.value.YN"));
			}
				    }
				 /*else{
					 errors.add("revaluation_v2_add",new ActionMessage("error.string.mandatory"));
					}*/
	if(null != propertyRestRequesGettDTO.getValuationDate_v2() && !propertyRestRequesGettDTO.getValuationDate_v2().trim().isEmpty())
				    {
		if (isValidDate(propertyRestRequesGettDTO.getValuationDate_v2()))
			propertyRestRequesGettDTO.setValuationDate_v2(
					propertyRestRequesGettDTO.getValuationDate_v2());
		else
			errors.add("valuationDate_v2", new ActionMessage(
					"error.date.format"));		
					// propertyRestRequestSetDTO.setValuationDate_v2(propertyRestRequesGettDTO.getValuationDate_v2().trim());
				    }
				/* else{
					 if(null != propertyRestRequesGettDTO.getRevaluation_v2_add() && !propertyRestRequesGettDTO.getRevaluation_v2_add().trim().isEmpty())
						{
							if(propertyRestRequesGettDTO.getRevaluation_v2_add().equalsIgnoreCase("Y"))
							{
								errors.add("valuationDate_v2",new ActionMessage("error.string.mandatory"));
							}
						}
					 else
						 propertyRestRequestSetDTO.setValuationDate_v2("");
					}*/
	if(null != propertyRestRequesGettDTO.getValuatorCompany_v2() && !propertyRestRequesGettDTO.getValuatorCompany_v2().trim().isEmpty())
				    {

		Object obj = masterObj.getObjectforMasterRelatedCode("actualOBValuationAgency", propertyRestRequesGettDTO.getValuatorCompany_v2(),"valuatorCompany_v2",errors);
		if(!(obj instanceof ActionErrors)){
			propertyRestRequesGettDTO.setValuatorCompany_v2(((IValuationAgency)obj).getId()+"");
		}
		else
		{
			errors.add("valuatorCompany_v2", new ActionMessage("error.invalid.field.value"));
		}
				    
				    }
				 else{
					 propertyRestRequestSetDTO.setValuatorCompany_v2("");
					}
	if(null != propertyRestRequesGettDTO.getCategoryOfLandUse_v2() && !propertyRestRequesGettDTO.getCategoryOfLandUse_v2().trim().isEmpty())
				    {


		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getCategoryOfLandUse_v2().trim(),"categoryOfLandUse_v2",errors,"LAND_USE_CAT");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setCategoryOfLandUse_v2(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("categoryOfLandUse_v2",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "categoryOfLandUse_v2 - "+e.getMessage());
				errors.add("categoryOfLandUse_v2",new ActionMessage("error.invalid.field.value"));
			}
				    
				    
				    }
	else if(null != propertyRestRequesGettDTO.getCategoryOfLandUse_v2() && propertyRestRequesGettDTO.getCategoryOfLandUse_v2().trim().isEmpty())
    {
					 propertyRestRequestSetDTO.setCategoryOfLandUse_v2("");
					}
	if(null != propertyRestRequesGettDTO.getDeveloperName_v2() && !propertyRestRequesGettDTO.getDeveloperName_v2().trim().isEmpty())
				    {
					if (propertyRestRequesGettDTO.getDeveloperName_v2()
							.length() <= 150)
						propertyRestRequestSetDTO
								.setDeveloperName_v2(propertyRestRequesGettDTO
										.getDeveloperName_v2().trim());
					else
						errors.add("developerName_v2", new ActionMessage(
								"error.field.length.exceeded", "150"));
				    }
	else if(null != propertyRestRequesGettDTO.getDeveloperName_v2() && propertyRestRequesGettDTO.getDeveloperName_v2().trim().isEmpty())
    {
	 propertyRestRequestSetDTO.setDeveloperName_v2("");
    }
	if(null != propertyRestRequesGettDTO.getCountry_v2() && !propertyRestRequesGettDTO.getCountry_v2().trim().isEmpty())
    {
// For Country

Object obj = masterObj.getObjectforMasterRelatedCode("actualCountry",
		propertyRestRequesGettDTO.getCountry_v2(), "country_v2",
		errors);
if (!(obj instanceof ActionErrors)) {
	propertyRestRequesGettDTO.setCountry_v2(Long.toString(((ICountry) obj).getIdCountry()));
	// For Region
	if (null != propertyRestRequesGettDTO.getRegion_v2()
			&& !propertyRestRequesGettDTO.getRegion_v2().trim().isEmpty()) {

		Object objReg = masterObj.getObjectforMasterRelatedCode("actualRegion",
				propertyRestRequesGettDTO.getRegion_v2().trim(), "region_v2",
				errors);
		if (!(objReg instanceof ActionErrors)) {
			propertyRestRequesGettDTO.setRegion_v2(Long.toString(((IRegion) objReg).getIdRegion()));
			//check if region is present in list of country
			if(propertyRestRequesGettDTO.getCountry_v2().trim().
			equalsIgnoreCase(Long.toString(((IRegion) objReg).getCountryId().getIdCountry()))){
				// For State
				if (null != propertyRestRequesGettDTO.getLocationState_v2()
						&& !propertyRestRequesGettDTO.getLocationState_v2().trim().isEmpty()) {
					Object objState = masterObj.getObjectforMasterRelatedCode("actualState",
							propertyRestRequesGettDTO.getLocationState_v2().trim(), "locationState_v2",
							errors);
					if (!(objState instanceof ActionErrors)) {
						propertyRestRequesGettDTO.setLocationState_v2(Long.toString(((IState) objState).getIdState()));
						
						//check if state is present in list of region
						if(propertyRestRequesGettDTO.getRegion_v2().trim().
						equalsIgnoreCase(Long.toString(((IState) objState).getRegionId().getIdRegion()))){
							//For City
							if (null != propertyRestRequesGettDTO.getNearestCity_v2()
										&& !propertyRestRequesGettDTO.getNearestCity_v2().trim().isEmpty()) {

								Object objCity = masterObj.getObjectforMasterRelatedCode("actualCity",
										propertyRestRequesGettDTO.getNearestCity_v2().trim(), "nearestCity_v2",
										errors);
								if (!(objCity instanceof ActionErrors)) {
									//check if given city is present in list of state
									if(propertyRestRequesGettDTO.getLocationState_v2().trim().
									equalsIgnoreCase(Long.toString(((ICity) objCity).getStateId().getIdState()))){
										propertyRestRequesGettDTO.setNearestCity_v2(Long.toString(((ICity) objCity).getIdCity()));
									}else {
										System.out.println("Given City is not Present in the state list of State");
										errors.add("nearestCity_v2", new ActionMessage("error.invalid.field.value"));
									}
								
								
								} else {
									errors.add("nearestCity_v2", new ActionMessage("error.invalid.field.value"));
								}
							}
							else
							{
								errors.add("nearestCity_v2", new ActionMessage("error.string.mandatory"));
							}
							
						} else {
							System.out.println("Given State is not Present in the state list of Region");
							errors.add("locationState_v2", new ActionMessage("error.invalid.field.value"));
						}
						
					} else {
						errors.add("locationState_v2", new ActionMessage("error.invalid.field.value"));
					}
				}
				else
				{
					errors.add("locationState_v2", new ActionMessage("error.string.mandatory"));
				}
			}else {
				System.out.println("Given Region is not Present in the list of Country");
				errors.add("region_v2", new ActionMessage("error.invalid.field.value"));
			}
			
		} else {
			errors.add("region_v2", new ActionMessage("error.invalid.field.value"));
		}
	
	}
	else
	{
		errors.add("region_v2", new ActionMessage("error.string.mandatory"));
	}
} else {
	errors.add("country_v2", new ActionMessage("error.invalid.field.value"));
}
				} /*else {
					if (null != propertyRestRequesGettDTO
							.getRevaluation_v2_add()
							&& !propertyRestRequesGettDTO
									.getRevaluation_v2_add().trim().isEmpty()) {
						if (propertyRestRequesGettDTO.getRevaluation_v2_add()
								.equalsIgnoreCase("Y"))
							errors.add("country_v2", new ActionMessage(
									"error.string.mandatory"));
					}
				}*/
	if(null != propertyRestRequesGettDTO.getPinCode_v2() && !propertyRestRequesGettDTO.getPinCode_v2().trim().isEmpty())
				    {
		if(null == propertyRestRequesGettDTO.getCountry_v2() || propertyRestRequesGettDTO.getCountry_v2().trim().isEmpty()) {
			 errors.add("country_v2", new ActionMessage("error.string.mandatory"));
			 }else if(null == propertyRestRequesGettDTO.getRegion_v2() || propertyRestRequesGettDTO.getRegion_v2().trim().isEmpty()){
				 errors.add("region_v2", new ActionMessage("error.string.mandatory"));
			 }else if(null == propertyRestRequesGettDTO.getLocationState_v2() || propertyRestRequesGettDTO.getLocationState_v2().trim().isEmpty()) {
				 errors.add("state_v2", new ActionMessage("error.string.mandatory"));
			 }else if(null == propertyRestRequesGettDTO.getNearestCity_v2() || propertyRestRequesGettDTO.getNearestCity_v2().trim().isEmpty()) {
				 errors.add("city_v2", new ActionMessage("error.string.mandatory"));
			 }else {
				 HashMap<String, String> pincodeMap = null;
					try {
					pincodeMap = (HashMap<String, String>) pinDao.getActiveStatePinCodeMap1();
					String pincodesStr = UIUtil.getDelimitedStringFromMap(pincodeMap, ",", "=");
					Map<String, String> statePincodeMap = UIUtil.getMapFromDelimitedString(pincodesStr, ",", "=");
					 if(null != statePincodeMap && !statePincodeMap.isEmpty()) {
						 String selectedStatePincode = statePincodeMap.get(propertyRestRequesGettDTO.getLocationState_v2());
						 if(null != selectedStatePincode && !propertyRestRequesGettDTO.getPinCode_v2().trim().startsWith(selectedStatePincode)) {
							 errors.add("pinCode_v2", new ActionMessage("error.pincode.incorrect"));
						 }
					 }
					}catch(Exception e) {
						System.out.println("Exception for pinCode_v2 in securityrestDTOMapper.java..=> e=>"+e);
					}
				 propertyRestRequestSetDTO.setPinCode_v2(propertyRestRequesGettDTO.getPinCode_v2().trim());
				 }
		}else{
			propertyRestRequestSetDTO.setPinCode_v2("");
			}
	if(null != propertyRestRequesGettDTO.getLandArea_v2() && !propertyRestRequesGettDTO.getLandArea_v2().trim().isEmpty())
				    {
		{
			if(ASSTValidator.isNumeric(propertyRestRequesGettDTO.getLandArea_v2()))
				propertyRestRequestSetDTO.setLandArea_v2(propertyRestRequesGettDTO.getLandArea_v2().trim());
			else
				errors.add("landArea_v2", new ActionMessage("error.string.invalidNumbers"));
		    }
				    }
				 else{
					 propertyRestRequestSetDTO.setLandArea_v2("");
					}
	if(null != propertyRestRequesGettDTO.getLandAreaUOM_v2() && !propertyRestRequesGettDTO.getLandAreaUOM_v2().trim().isEmpty())
				    {

		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getLandAreaUOM_v2().trim(),"landAreaUOM_v2",errors,"AREA_UOM");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setLandAreaUOM_v2(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("landAreaUOM_v2",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "landAreaUOM_v2 - "+e.getMessage());
				errors.add("landAreaUOM_v2",new ActionMessage("error.invalid.field.value"));
			}
				    
				    }
				 else{
					 propertyRestRequestSetDTO.setLandAreaUOM_v2("");
					}
	if(null != propertyRestRequesGettDTO.getBuiltupArea_v2() && !propertyRestRequesGettDTO.getBuiltupArea_v2().trim().isEmpty())
				    {
		{
			if(ASSTValidator.isNumeric(propertyRestRequesGettDTO.getBuiltupArea_v2()))
				propertyRestRequestSetDTO.setBuiltupArea_v2(propertyRestRequesGettDTO.getBuiltupArea_v2().trim());
			else
				errors.add("builtupArea_v2", new ActionMessage("error.string.invalidNumbers"));
		    }
				    }
				 else{
					 propertyRestRequestSetDTO.setBuiltupArea_v2("");
					}
	if(null != propertyRestRequesGettDTO.getBuiltupAreaUOM_v2() && !propertyRestRequesGettDTO.getBuiltupAreaUOM_v2().trim().isEmpty())
				    {

		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getBuiltupAreaUOM_v2().trim(),"builtupAreaUOM_v2",errors,"AREA_UOM");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setBuiltupAreaUOM_v2(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("builtupAreaUOM_v2",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "builtupAreaUOM_v2 - "+e.getMessage());
				errors.add("builtupAreaUOM_v2",new ActionMessage("error.invalid.field.value"));
			}
				    }
				 else{
					 propertyRestRequestSetDTO.setBuiltupAreaUOM_v2("");
					}
	
	boolean isValidBuildValueV2 =false;
	boolean isValidLandValueV2= false;
	if(null != propertyRestRequesGettDTO.getLandValue_v2() && !propertyRestRequesGettDTO.getLandValue_v2().trim().isEmpty())
				    {
		{
		if(ASSTValidator.isNumeric(propertyRestRequesGettDTO.getLandValue_v2()))
		{
			propertyRestRequestSetDTO.setLandValue_v2(propertyRestRequesGettDTO.getLandValue_v2().trim());
			isValidLandValueV2= true;
		}
		else
			errors.add("landValue_v2", new ActionMessage("error.string.invalidNumbers"));
	    }
    }
				 else{
					 propertyRestRequestSetDTO.setLandValue_v2("");
					}
	if(null != propertyRestRequesGettDTO.getBuildingValue_v2() && !propertyRestRequesGettDTO.getBuildingValue_v2().trim().isEmpty())
				    {

		{
		if(ASSTValidator.isNumeric(propertyRestRequesGettDTO.getBuildingValue_v2()))
		{
			propertyRestRequestSetDTO.setBuildingValue_v2(propertyRestRequesGettDTO.getBuildingValue_v2().trim());
			isValidBuildValueV2=true;
		}
		else
			errors.add("buildingValue_v2", new ActionMessage("error.string.invalidNumbers"));
	    }
				    }
				 else{
					 propertyRestRequestSetDTO.setBuildingValue_v2("");
					}
	if(isValidLandValueV2 && isValidBuildValueV2)
	{
	if(null != propertyRestRequestSetDTO.getBuildingValue_v2()
			&& !propertyRestRequestSetDTO.getBuildingValue_v2()
			.trim().isEmpty() && null != propertyRestRequestSetDTO.getLandValue_v2()
			&& !propertyRestRequestSetDTO.getLandValue_v2().trim()
					.isEmpty())
	{
		propertyRestRequestSetDTO.setTotalPropertyAmount_v2(totalValue(propertyRestRequestSetDTO.getLandValue_v2(),propertyRestRequestSetDTO.getBuildingValue_v2()));
	}
	}
	if(null != propertyRestRequesGettDTO.getReconstructionValueOfTheBuilding_v2() && !propertyRestRequesGettDTO.getReconstructionValueOfTheBuilding_v2().trim().isEmpty())
				    {
		{
			if(ASSTValidator.isNumeric(propertyRestRequesGettDTO.getReconstructionValueOfTheBuilding_v2()))
				propertyRestRequestSetDTO.setReconstructionValueOfTheBuilding_v2(propertyRestRequesGettDTO.getReconstructionValueOfTheBuilding_v2().trim());
			else
				errors.add("reconstructionValueOfTheBuilding_v2", new ActionMessage("error.string.invalidNumbers"));
		    }
				    }
				 else{
					 propertyRestRequestSetDTO.setReconstructionValueOfTheBuilding_v2("");
					}
	if(null != propertyRestRequesGettDTO.getPropertyCompletionStatus_v2() && !propertyRestRequesGettDTO.getPropertyCompletionStatus_v2().trim().isEmpty())
				    {
		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getPropertyCompletionStatus_v2().trim(),"propertyCompletionStatus_v2",errors,"PROPERTY_COMPLETION_STATUS");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setPropertyCompletionStatus_v2(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("propertyCompletionStatus_v2",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "propertyCompletionStatus_v2 - "+e.getMessage());
				errors.add("propertyCompletionStatus_v2",new ActionMessage("error.invalid.field.value"));
			}
				    }
	else if(null != propertyRestRequesGettDTO.getPropertyCompletionStatus_v2() && propertyRestRequesGettDTO.getPropertyCompletionStatus_v2().trim().isEmpty())
    {
					 propertyRestRequestSetDTO.setPropertyCompletionStatus_v2("");
					}
	
				if (null != propertyRestRequesGettDTO
						.getIsPhysicalInspection_v2()
						&& !propertyRestRequesGettDTO
								.getIsPhysicalInspection_v2().trim()
								.isEmpty()) {
					if ("true"
							.equalsIgnoreCase(propertyRestRequesGettDTO
									.getIsPhysicalInspection_v2())
							|| "false"
									.equalsIgnoreCase(propertyRestRequesGettDTO
											.getIsPhysicalInspection_v2()))
						propertyRestRequestSetDTO.setIsPhysicalInspection_v2(
								propertyRestRequesGettDTO
										.getIsPhysicalInspection_v2().trim());
					else
						errors.add("isPhysicalInspection_v2",
								new ActionMessage("error.invalid.field.value"));
				} /*else {
					errors.add("isPhysicalInspection_v2",
							new ActionMessage("error.string.mandatory"));
				}
*/
				if (null != propertyRestRequesGettDTO
						.getIsPhysicalInspection_v2()
						&& !propertyRestRequesGettDTO
								.getIsPhysicalInspection_v2().trim()
								.isEmpty()) {
					if ("true".equalsIgnoreCase(propertyRestRequesGettDTO
							.getIsPhysicalInspection_v2())) {
						if (null != propertyRestRequesGettDTO
								.getLastPhysicalInspectDate_v2()
								&& !propertyRestRequesGettDTO
										.getLastPhysicalInspectDate_v2().trim()
										.isEmpty()) {
							if (isValidDate(propertyRestRequesGettDTO
									.getLastPhysicalInspectDate_v2()))
								propertyRestRequesGettDTO
										.setLastPhysicalInspectDate_v2(
												propertyRestRequesGettDTO
														.getLastPhysicalInspectDate_v2());
							else
								errors.add("lastPhysicalInspectDate_v2",
										new ActionMessage(
												"error.date.format"));
						}/* else {
							errors.add("lastPhysicalInspectDate_v2",
									new ActionMessage(
											"error.string.mandatory"));
						}*/
						if (null != propertyRestRequesGettDTO
								.getNextPhysicalInspectDate_v2()
								&& !propertyRestRequesGettDTO
										.getNextPhysicalInspectDate_v2().trim()
										.isEmpty()) {
							if (isValidDate(propertyRestRequesGettDTO
									.getNextPhysicalInspectDate_v2()))
								propertyRestRequesGettDTO
										.setNextPhysicalInspectDate_v2(
												propertyRestRequesGettDTO
														.getNextPhysicalInspectDate_v2());
							else
								errors.add("nextPhysicalInspectDate_v2",
										new ActionMessage(
												"error.date.format"));
						} 
							/*else errors.add("nextPhysicalInspectDate_v2",
									new ActionMessage(
											"error.string.mandatory"));*/

						if (null != propertyRestRequesGettDTO
								.getPhysicalInspectionFreqUnit_v2()
								&& !propertyRestRequesGettDTO
										.getPhysicalInspectionFreqUnit_v2()
										.trim().isEmpty()) {

							try {
								Object obj = masterObj.getObjectByEntityName(
										"entryCode",
										propertyRestRequesGettDTO
												.getPhysicalInspectionFreqUnit_v2()
												.trim(),
										"physicalInspectionFreqUnit_v2", errors,
										"FREQUENCY");
								if (!(obj instanceof ActionErrors)) {
									propertyRestRequesGettDTO
											.setPhysicalInspectionFreqUnit_v2(
													((ICommonCodeEntry) obj)
															.getEntryCode());
								} else {
									errors.add("physicalInspectionFreqUnit_v2",
											new ActionMessage(
													"error.invalid.field.value"));
								}
							} catch (Exception e) {
								DefaultLogger.error(this,
										"physicalInspectionFreqUnit_v2"
												+ e.getMessage());
								errors.add("physicalInspectionFreqUnit_v2",
										new ActionMessage(
												"error.invalid.field.value"));
							}

						}/* else {
							errors.add("physicalInspectionFreqUnit_v2",
									new ActionMessage(
											"error.string.mandatory"));
						}*/
					}
					if ("false".equalsIgnoreCase(propertyRestRequesGettDTO
							.getIsPhysicalInspection_v2())) {
						propertyRestRequesGettDTO
								.setLastPhysicalInspectDate_v2("");
						propertyRestRequesGettDTO
								.setNextPhysicalInspectDate_v2("");
						propertyRestRequesGettDTO
								.setPhysicalInspectionFreqUnit_v2("");
					}

				}
	
	if(null != propertyRestRequesGettDTO.getRemarksProperty_v2() && !propertyRestRequesGettDTO.getRemarksProperty_v2().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setRemarksProperty_v2(propertyRestRequesGettDTO.getRemarksProperty_v2().trim());
				    }
	else if(null != propertyRestRequesGettDTO.getRemarksProperty_v2() && propertyRestRequesGettDTO.getRemarksProperty_v2().trim().isEmpty())
    {
	 propertyRestRequestSetDTO.setRemarksProperty_v2("");
    }
	if(null != propertyRestRequesGettDTO.getRevaluation_v3_add() && !propertyRestRequesGettDTO.getRevaluation_v3_add().trim().isEmpty())
				    {

		if("Y".equalsIgnoreCase(propertyRestRequesGettDTO.getRevaluation_v3_add().trim()) || "N".equalsIgnoreCase(propertyRestRequesGettDTO.getRevaluation_v3_add().trim()))
			 propertyRestRequestSetDTO.setRevaluation_v3_add(propertyRestRequesGettDTO.getRevaluation_v3_add().trim());
			else
			{
				errors.add("revaluation_v3_add",new ActionMessage("error.invalid.value.YN"));
			}
				    }
				/* else{
					 errors.add("revaluation_v3_add",new ActionMessage("error.string.mandatory"));
					}*/
	if(null != propertyRestRequesGettDTO.getValuationDate_v3() && !propertyRestRequesGettDTO.getValuationDate_v3().trim().isEmpty())
				    {
		if (isValidDate(propertyRestRequesGettDTO.getValuationDate_v3()))
			propertyRestRequesGettDTO.setValuationDate_v3(
					propertyRestRequesGettDTO.getValuationDate_v3());
		else
			errors.add("valuationDate_v3", new ActionMessage(
					"error.date.format"));		
				    }
				 else{
					 propertyRestRequestSetDTO.setValuationDate_v3("");
					}
	if(null != propertyRestRequesGettDTO.getValuatorCompany_v3() && !propertyRestRequesGettDTO.getValuatorCompany_v3().trim().isEmpty())
				    {

		Object obj = masterObj.getObjectforMasterRelatedCode("actualOBValuationAgency", propertyRestRequesGettDTO.getValuatorCompany_v3(),"valuatorCompany_v3",errors);
		if(!(obj instanceof ActionErrors)){
			propertyRestRequesGettDTO.setValuatorCompany_v3(((IValuationAgency)obj).getId()+"");
		}
		else
		{
			errors.add("valuatorCompany_v3", new ActionMessage("error.invalid.field.value"));
		}
				    }
				 else{
					 propertyRestRequestSetDTO.setValuatorCompany_v3("");
					}
	if(null != propertyRestRequesGettDTO.getCategoryOfLandUse_v3() && !propertyRestRequesGettDTO.getCategoryOfLandUse_v3().trim().isEmpty())
				    {

		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getCategoryOfLandUse_v3().trim(),"categoryOfLandUse_v3",errors,"LAND_USE_CAT");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setCategoryOfLandUse_v3(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("categoryOfLandUse_v3",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "categoryOfLandUse_v3 - "+e.getMessage());
				errors.add("categoryOfLandUse_v3",new ActionMessage("error.invalid.field.value"));
			}
				    
				    }
	else if(null != propertyRestRequesGettDTO.getCategoryOfLandUse_v3() && propertyRestRequesGettDTO.getCategoryOfLandUse_v3().trim().isEmpty())
    {
					 propertyRestRequestSetDTO.setCategoryOfLandUse_v3("");
					}
	if(null != propertyRestRequesGettDTO.getDeveloperName_v3() && !propertyRestRequesGettDTO.getDeveloperName_v3().trim().isEmpty())
				    {
					if (propertyRestRequesGettDTO.getDeveloperName_v3()
							.length() <= 150)
						propertyRestRequestSetDTO
								.setDeveloperName_v3(propertyRestRequesGettDTO
										.getDeveloperName_v3().trim());
					else
						errors.add("developerName_v3", new ActionMessage(
								"error.field.length.exceeded", "150"));
					}
	else if(null != propertyRestRequesGettDTO.getDeveloperName_v3() && propertyRestRequesGettDTO.getDeveloperName_v3().trim().isEmpty())
    {
	 propertyRestRequestSetDTO.setDeveloperName_v3("");
    }
	if(null != propertyRestRequesGettDTO.getCountry_v3() && !propertyRestRequesGettDTO.getCountry_v3().trim().isEmpty())
    {
// For Country

Object obj = masterObj.getObjectforMasterRelatedCode("actualCountry",
		propertyRestRequesGettDTO.getCountry_v3(), "country_v3",
		errors);
if (!(obj instanceof ActionErrors)) {
	propertyRestRequesGettDTO.setCountry_v3(Long.toString(((ICountry) obj).getIdCountry()));
	// For Region
	if (null != propertyRestRequesGettDTO.getRegion_v3()
			&& !propertyRestRequesGettDTO.getRegion_v3().trim().isEmpty()) {

		Object objReg = masterObj.getObjectforMasterRelatedCode("actualRegion",
				propertyRestRequesGettDTO.getRegion_v3().trim(), "region_v3",
				errors);
		if (!(objReg instanceof ActionErrors)) {
			propertyRestRequesGettDTO.setRegion_v3(Long.toString(((IRegion) objReg).getIdRegion()));
			//check if region is present in list of country
			if(propertyRestRequesGettDTO.getCountry_v3().trim().
			equalsIgnoreCase(Long.toString(((IRegion) objReg).getCountryId().getIdCountry()))){
				// For State
				if (null != propertyRestRequesGettDTO.getLocationState_v3()
						&& !propertyRestRequesGettDTO.getLocationState_v3().trim().isEmpty()) {
					Object objState = masterObj.getObjectforMasterRelatedCode("actualState",
							propertyRestRequesGettDTO.getLocationState_v3().trim(), "locationState_v3",
							errors);
					if (!(objState instanceof ActionErrors)) {
						propertyRestRequesGettDTO.setLocationState_v3(Long.toString(((IState) objState).getIdState()));
						
						//check if state is present in list of region
						if(propertyRestRequesGettDTO.getRegion_v3().trim().
						equalsIgnoreCase(Long.toString(((IState) objState).getRegionId().getIdRegion()))){
							//For City
							if (null != propertyRestRequesGettDTO.getNearestCity_v3()
										&& !propertyRestRequesGettDTO.getNearestCity_v3().trim().isEmpty()) {

								Object objCity = masterObj.getObjectforMasterRelatedCode("actualCity",
										propertyRestRequesGettDTO.getNearestCity_v3().trim(), "nearestCity_v3",
										errors);
								if (!(objCity instanceof ActionErrors)) {
									//check if given city is present in list of state
									if(propertyRestRequesGettDTO.getLocationState_v3().trim().
									equalsIgnoreCase(Long.toString(((ICity) objCity).getStateId().getIdState()))){
										propertyRestRequesGettDTO.setNearestCity_v3(Long.toString(((ICity) objCity).getIdCity()));
									}else {
										System.out.println("Given City is not Present in the state list of State");
										errors.add("nearestCity_v3", new ActionMessage("error.invalid.field.value"));
									}
								
								
								} else {
									errors.add("nearestCity_v3", new ActionMessage("error.invalid.field.value"));
								}
							}
							else
							{
								errors.add("nearestCity_v3", new ActionMessage("error.string.mandatory"));
							}
							
						} else {
							System.out.println("Given State is not Present in the state list of Region");
							errors.add("locationState_v3", new ActionMessage("error.invalid.field.value"));
						}
						
					} else {
						errors.add("locationState_v3", new ActionMessage("error.invalid.field.value"));
					}
				}
				else
				{
					errors.add("locationState_v3", new ActionMessage("error.string.mandatory"));
				}
			}else {
				System.out.println("Given Region is not Present in the list of Country");
				errors.add("region_v3", new ActionMessage("error.invalid.field.value"));
			}
			
		} else {
			errors.add("region_v3", new ActionMessage("error.invalid.field.value"));
		}
	
	}
	else
	{
		errors.add("region_v3", new ActionMessage("error.string.mandatory"));
	}
}/* else {
	errors.add("country_v3", new ActionMessage("error.invalid.field.value"));
}*/
} else {
	if(null != propertyRestRequesGettDTO.getRevaluation_v3_add() && !propertyRestRequesGettDTO.getRevaluation_v3_add().trim().isEmpty())
errors.add("country_v3", new ActionMessage("error.string.mandatory"));
}
	if(null != propertyRestRequesGettDTO.getPinCode_v3() && !propertyRestRequesGettDTO.getPinCode_v3().trim().isEmpty())
				    {
		HashMap<String, String> pincodeMap = null;
		try {
		pincodeMap = (HashMap<String, String>) pinDao.getActiveStatePinCodeMap1();
		String pincodesStr = UIUtil.getDelimitedStringFromMap(pincodeMap, ",", "=");
		Map<String, String> statePincodeMap = UIUtil.getMapFromDelimitedString(pincodesStr, ",", "=");
		 if(null != statePincodeMap && !statePincodeMap.isEmpty()) {
			 String selectedStatePincode = statePincodeMap.get(propertyRestRequesGettDTO.getLocationState_v3());
			 if(null != selectedStatePincode && !propertyRestRequesGettDTO.getPinCode_v3().trim().startsWith(selectedStatePincode)) {
				 errors.add("pinCode_v3", new ActionMessage("error.pincode.incorrect"));
			 }
		 }
		}catch(Exception e) {
			System.out.println("Exception for pinCode_v3 in securityrestDTOMapper.java..=> e=>"+e);
		}
					 propertyRestRequestSetDTO.setPinCode_v3(propertyRestRequesGettDTO.getPinCode_v3().trim());
				    }
				 else{
					 propertyRestRequestSetDTO.setPinCode_v3("");
					}
	if(null != propertyRestRequesGettDTO.getLandArea_v3() && !propertyRestRequesGettDTO.getLandArea_v3().trim().isEmpty())
				    {

		if(ASSTValidator.isNumeric(propertyRestRequesGettDTO.getLandArea_v3()))
			propertyRestRequestSetDTO.setLandArea_v3(propertyRestRequesGettDTO.getLandArea_v3().trim());
		else
			errors.add("landArea_v3", new ActionMessage("error.string.invalidNumbers"));
	    
				    }
				 else{
					 propertyRestRequestSetDTO.setLandArea_v3("");
					}
	if(null != propertyRestRequesGettDTO.getLandAreaUOM_v3() && !propertyRestRequesGettDTO.getLandAreaUOM_v3().trim().isEmpty())
				    {
		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getLandAreaUOM_v3().trim(),"landAreaUOM_v3",errors,"AREA_UOM");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setLandAreaUOM_v3(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("landAreaUOM_v3",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "landAreaUOM_v3 - "+e.getMessage());
				errors.add("landAreaUOM_v3",new ActionMessage("error.invalid.field.value"));
			}
				    }
				 else{
					 propertyRestRequestSetDTO.setLandAreaUOM_v3("");
					}
	if(null != propertyRestRequesGettDTO.getBuiltupArea_v3() && !propertyRestRequesGettDTO.getBuiltupArea_v3().trim().isEmpty())
				    {
				{
				if(ASSTValidator.isNumeric(propertyRestRequesGettDTO.getBuiltupArea_v3()))
					propertyRestRequestSetDTO.setBuiltupArea_v3(propertyRestRequesGettDTO.getBuiltupArea_v3().trim());
				else
					errors.add("builtupArea_v3", new ActionMessage("error.string.invalidNumbers"));
			    }
				    }
				 else{
					 propertyRestRequestSetDTO.setBuiltupArea_v3("");
					}
	if(null != propertyRestRequesGettDTO.getBuiltupAreaUOM_v3() && !propertyRestRequesGettDTO.getBuiltupAreaUOM_v3().trim().isEmpty())
				    {
		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getBuiltupAreaUOM_v3().trim(),"builtupAreaUOM_v3",errors,"AREA_UOM");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setBuiltupAreaUOM_v3(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("builtupAreaUOM_v3",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "builtupAreaUOM_v3 - "+e.getMessage());
				errors.add("builtupAreaUOM_v3",new ActionMessage("error.invalid.field.value"));
			}
				    }
				 else{
					 propertyRestRequestSetDTO.setBuiltupAreaUOM_v3("");
					}
	boolean isValidLandValueV3 = false;
	if(null != propertyRestRequesGettDTO.getLandValue_v3() && !propertyRestRequesGettDTO.getLandValue_v3().trim().isEmpty())
				    {
						{
						if(ASSTValidator.isNumeric(propertyRestRequesGettDTO.getLandValue_v3()))
						{
							propertyRestRequestSetDTO.setLandValue_v3(propertyRestRequesGettDTO.getLandValue_v3().trim());
							isValidLandValueV3 = true;
						}
						else
							errors.add("landValue_v3", new ActionMessage("error.string.invalidNumbers"));
					    }
				    }
				 else{
					 propertyRestRequestSetDTO.setLandValue_v3("");
					}
	boolean isValidBuildValueV3 = false;
	if(null != propertyRestRequesGettDTO.getBuildingValue_v3() && !propertyRestRequesGettDTO.getBuildingValue_v3().trim().isEmpty())
				    {

		{
		if(ASSTValidator.isNumeric(propertyRestRequesGettDTO.getBuildingValue_v3()))
		{
			propertyRestRequestSetDTO.setBuildingValue_v3(propertyRestRequesGettDTO.getBuildingValue_v3().trim());
			isValidBuildValueV3 = true;
		}
		else
			errors.add("buildingValue_v3", new ActionMessage("error.string.invalidNumbers"));
	    }
    
				    
				    }
				 else{
					 propertyRestRequestSetDTO.setBuildingValue_v3("");
					}
	if(null != propertyRestRequesGettDTO.getReconstructionValueOfTheBuilding_v3() && !propertyRestRequesGettDTO.getReconstructionValueOfTheBuilding_v3().trim().isEmpty())
				    {
		{
			if(ASSTValidator.isNumeric(propertyRestRequesGettDTO.getReconstructionValueOfTheBuilding_v3()))
				propertyRestRequestSetDTO.setReconstructionValueOfTheBuilding_v3(propertyRestRequesGettDTO.getReconstructionValueOfTheBuilding_v3().trim());
			else
				errors.add("reconstructionValueOfTheBuilding_v3", new ActionMessage("error.string.invalidNumbers"));
		    }
				    }
				 else{
					 propertyRestRequestSetDTO.setReconstructionValueOfTheBuilding_v3("");
					}
	if(isValidLandValueV3 && isValidBuildValueV3)
	{
	if(null != propertyRestRequestSetDTO.getBuildingValue_v3()
			&& !propertyRestRequestSetDTO.getBuildingValue_v3()
			.trim().isEmpty() && null != propertyRestRequestSetDTO.getLandValue_v3()
			&& !propertyRestRequestSetDTO.getLandValue_v3().trim()
					.isEmpty())
	{
		propertyRestRequestSetDTO.setTotalPropertyAmount_v3(totalValue(propertyRestRequestSetDTO.getLandValue_v3(),propertyRestRequestSetDTO.getBuildingValue_v3()));
	}
	}
	if(null != propertyRestRequesGettDTO.getPropertyCompletionStatus_v3() && !propertyRestRequesGettDTO.getPropertyCompletionStatus_v3().trim().isEmpty())
				    {
		 try {
			 Object obj = masterObj.getObjectByEntityName("entryCode", propertyRestRequesGettDTO.getPropertyCompletionStatus_v3().trim(),"propertyCompletionStatus_v3",errors,"PROPERTY_COMPLETION_STATUS");
			 if(!(obj instanceof ActionErrors)){
				 propertyRestRequesGettDTO.setPropertyCompletionStatus_v3(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("propertyCompletionStatus_v3",new ActionMessage("error.invalid.field.value")); 
			 }
		 }
		 catch(Exception e){
				DefaultLogger.error(this, "propertyCompletionStatus_v3 - "+e.getMessage());
				errors.add("propertyCompletionStatus_v3",new ActionMessage("error.invalid.field.value"));
			}
				    }
	else if(null != propertyRestRequesGettDTO.getPropertyCompletionStatus_v3() && propertyRestRequesGettDTO.getPropertyCompletionStatus_v3().trim().isEmpty())
    {
					 propertyRestRequestSetDTO.setPropertyCompletionStatus_v3("");
					}
	
				if (null != propertyRestRequesGettDTO
						.getIsPhysicalInspection_v3()
						&& !propertyRestRequesGettDTO
								.getIsPhysicalInspection_v3().trim()
								.isEmpty()) {
					if ("true"
							.equalsIgnoreCase(propertyRestRequesGettDTO
									.getIsPhysicalInspection_v3())
							|| "false"
									.equalsIgnoreCase(propertyRestRequesGettDTO
											.getIsPhysicalInspection_v3()))
						propertyRestRequestSetDTO.setIsPhysicalInspection_v3(
								propertyRestRequesGettDTO
										.getIsPhysicalInspection_v3().trim());
					else
						errors.add("isPhysicalInspection_v3",
								new ActionMessage("error.invalid.field.value"));
				} /*else {
					errors.add("isPhysicalInspection_v3",
							new ActionMessage("error.string.mandatory"));
				}
*/
				if (null != propertyRestRequesGettDTO
						.getIsPhysicalInspection_v3()
						&& !propertyRestRequesGettDTO
								.getIsPhysicalInspection_v3().trim()
								.isEmpty()) {
					if ("true".equalsIgnoreCase(propertyRestRequesGettDTO
							.getIsPhysicalInspection_v3())) {
						if (null != propertyRestRequesGettDTO
								.getLastPhysicalInspectDate_v3()
								&& !propertyRestRequesGettDTO
										.getLastPhysicalInspectDate_v3().trim()
										.isEmpty()) {
							if (isValidDate(propertyRestRequesGettDTO
									.getLastPhysicalInspectDate_v3()))
								propertyRestRequesGettDTO
										.setLastPhysicalInspectDate_v3(
												propertyRestRequesGettDTO
														.getLastPhysicalInspectDate_v3());
							else
								errors.add("lastPhysicalInspectDate_v3",
										new ActionMessage(
												"error.date.format"));
						} /*else {
							errors.add("lastPhysicalInspectDate_v3",
									new ActionMessage(
											"error.string.mandatory"));
						}*/
						if (null != propertyRestRequesGettDTO
								.getNextPhysicalInspectDate_v3()
								&& !propertyRestRequesGettDTO
										.getNextPhysicalInspectDate_v3().trim()
										.isEmpty()) {
							if (isValidDate(propertyRestRequesGettDTO
									.getNextPhysicalInspectDate_v3()))
								propertyRestRequesGettDTO
										.setNextPhysicalInspectDate_v3(
												propertyRestRequesGettDTO
														.getNextPhysicalInspectDate_v3());
							else
								errors.add("nextPhysicalInspectDate_v3",
										new ActionMessage(
												"error.date.format"));
						}/* else
							errors.add("nextPhysicalInspectDate_v3",
									new ActionMessage(
											"error.string.mandatory"));*/

						if (null != propertyRestRequesGettDTO
								.getPhysicalInspectionFreqUnit_v3()
								&& !propertyRestRequesGettDTO
										.getPhysicalInspectionFreqUnit_v3()
										.trim().isEmpty()) {

							try {
								Object obj = masterObj.getObjectByEntityName(
										"entryCode",
										propertyRestRequesGettDTO
												.getPhysicalInspectionFreqUnit_v3()
												.trim(),
										"physicalInspectionFreqUnit_v3", errors,
										"FREQUENCY");
								if (!(obj instanceof ActionErrors)) {
									propertyRestRequesGettDTO
											.setPhysicalInspectionFreqUnit_v3(
													((ICommonCodeEntry) obj)
															.getEntryCode());
								} else {
									errors.add("physicalInspectionFreqUnit_v3",
											new ActionMessage(
													"error.invalid.field.value"));
								}
							} catch (Exception e) {
								DefaultLogger.error(this,
										"physicalInspectionFreqUnit_v3"
												+ e.getMessage());
								errors.add("physicalInspectionFreqUnit_v3",
										new ActionMessage(
												"error.invalid.field.value"));
							}

						} /*else {
							errors.add("physicalInspectionFreqUnit_v3",
									new ActionMessage(
											"error.string.mandatory"));
						}*/
					}
					if ("false".equalsIgnoreCase(propertyRestRequesGettDTO
							.getIsPhysicalInspection_v3())) {
						propertyRestRequesGettDTO
								.setLastPhysicalInspectDate_v3("");
						propertyRestRequesGettDTO
								.setNextPhysicalInspectDate_v3("");
						propertyRestRequesGettDTO
								.setPhysicalInspectionFreqUnit_v3("");
					}

				}
	
	if(null != propertyRestRequesGettDTO.getRemarksProperty_v3() && !propertyRestRequesGettDTO.getRemarksProperty_v3().trim().isEmpty())
				    {
					 propertyRestRequestSetDTO.setRemarksProperty_v3(propertyRestRequesGettDTO.getRemarksProperty_v3().trim());
				    }
	else if(null != propertyRestRequesGettDTO.getRemarksProperty_v3() && propertyRestRequesGettDTO.getRemarksProperty_v3().trim().isEmpty())
    {
					propertyRestRequestSetDTO.setRemarksProperty_v3("");
    }

	
	
			 }else
			 {
				 errors.add("propertyDetailsList", new ActionMessage("error.string.mandatory")); 
			 }
			 propertyRestRequestList.add(0, propertyRestRequestSetDTO);
			 reqobj.setPropertyDetailsList(propertyRestRequestList);
			 
			 
		 }
		 
		 if("MS605".equalsIgnoreCase(ColSubTypeId)) {
			 List<StockRestRequestDTO> stockDetailsList = collateralreq.getBodyDetails().get(0).getStockDetailsList();
		 if (stockDetailsList != null
				 && !stockDetailsList.isEmpty()) {
			
			 String errorCode = null;
			 for (int i = 0;i<stockDetailsList.size();i++){
				 
				 if(null == stockDetailsList.get(i).getStockSecUniqueId()
						 || stockDetailsList.get(i).getStockSecUniqueId().trim().isEmpty() )
				 {
					 errors.add("stockSecUniqueId", new ActionMessage("error.string.mandatory")); 
				 }

				 if(null != stockDetailsList.get(i).getActionFlag()
						 && !stockDetailsList.get(i).getActionFlag().trim().isEmpty() )
				 {
					 if(!("A".equalsIgnoreCase(stockDetailsList.get(i).getActionFlag()) 
							 || "U".equalsIgnoreCase(stockDetailsList.get(i).getActionFlag())
							 || "D".equalsIgnoreCase(stockDetailsList.get(i).getActionFlag()))){
						 //if(Double.parseDouble(ll)>9999999999.99d)
						 errors.add("actionFlag",new ActionMessage("error.invalid.field.value"));
					 }
					 else
					 {

							if("A".equalsIgnoreCase(stockDetailsList.get(i).getActionFlag()))
							{
								if(null != stockDetailsList.get(i).getStockSecUniqueId() &&
										!stockDetailsList.get(i).getStockSecUniqueId().trim().isEmpty())
								{
									boolean isUniqueIdPresent = collateralDAO.checkUniqueStockId(stockDetailsList.get(i).getStockSecUniqueId().trim(),collateralreq.getBodyDetails().get(0).getSecurityId());
									if(isUniqueIdPresent)
									{
										errors.add("StockSecUniqueId-"+stockDetailsList.get(i).getStockSecUniqueId(),new ActionMessage("StockSecUniqueId is duplicate"));
									}
									else
									{
										if(stockDetailsList.get(i).getStockSecUniqueId().length() <=22)
											stockDetailsList.get(i).setStockSecUniqueId(stockDetailsList.get(i).getStockSecUniqueId());
										else
											errors.add("StockSecUniqueId-"+stockDetailsList.get(i).getStockSecUniqueId(),new ActionMessage("StockSecUniqueId length must not exced"));	
									}
								}
								else
								{
									errors.add("StockSecUniqueId",new ActionMessage("StockSecUniqueId is mandatory"));
								}
							}
							
							if("U".equalsIgnoreCase(stockDetailsList.get(i).getActionFlag()))
							{
								if(null != stockDetailsList.get(i).getStockSecUniqueId() &&
										!stockDetailsList.get(i).getStockSecUniqueId().trim().isEmpty())
								{
									boolean isUniqueIdPresent = collateralDAO.checkUniqueStockId(stockDetailsList.get(i).getStockSecUniqueId().trim(),collateralreq.getBodyDetails().get(0).getSecurityId());
									if(isUniqueIdPresent)
									{
										errors.add("StockSecUniqueId-"+stockDetailsList.get(i).getStockSecUniqueId(),new ActionMessage("StockSecUniqueId is duplicate"));
									}
									else
									{
										if(stockDetailsList.get(i).getStockSecUniqueId().length() <=25)
											stockDetailsList.get(i).setStockSecUniqueId(stockDetailsList.get(i).getStockSecUniqueId());
										else
											errors.add("StockSecUniqueId-"+stockDetailsList.get(i).getStockSecUniqueId(),new ActionMessage("StockSecUniqueId length must not exced"));	
									}
								}
								else
								{
									errors.add("StockSecUniqueId",new ActionMessage("StockSecUniqueId is mandatory"));
								}
								
								if (null == (stockDetailsList.get(i).getClimsItemId())
		                                || stockDetailsList.get(i).getClimsItemId()
		                                .trim().isEmpty()) {
									errors.add("ClimsItemId", new ActionMessage("error.string.mandatory"));
								} else 
								{
									if (!ASSTValidator.isNumeric(stockDetailsList.get(i).getClimsItemId())) {
										errors.add("ClimsItemId", new ActionMessage("error.invalid.field.value"));
									} else {
										IOtherListedLocal markObj = (IOtherListedLocal) itrxValue.getCollateral();
										IMarketableEquity[] equitylList = markObj.getEquityList();
										boolean flag = false;
										for (int e = 0; e < equitylList.length; e++) {
											long equityId = 0;
											equityId = equitylList[e].getEquityID();
											if (String.valueOf(equityId).equals(stockDetailsList.get(i).getClimsItemId())) {
												flag = true;
											}
										}
										if (flag == false) {
											errors.add("ClimsItemId", new ActionMessage("error.invalid.field.value"));
										}
									}
								}
								
							}

						
					 }
				 }
				 else
				 {
					 errors.add("actionFlag", new ActionMessage("error.string.mandatory")); 
				 }
				 
				 if(null != stockDetailsList.get(i).getStockExchange()
						 && !stockDetailsList.get(i).getStockExchange().trim().isEmpty() )
				 {
					 boolean flag1 = false;
						flag1 = collateralDAO.checkStockExchange(stockDetailsList.get(i).getStockExchange());
						if (!flag1) {
							errors.add("stockExchange", new ActionMessage("error.invalid"));
						}
				 }
				 else
				 {
					 errors.add("stockExchange", new ActionMessage("error.string.mandatory")); 
				 }
				 
				 if(null != stockDetailsList.get(i).getNoOfUnit()
						 && !stockDetailsList.get(i).getNoOfUnit().trim().isEmpty() )
				 {
					 if(!(Validator.ERROR_NONE).equals(Validator.checkNumber(stockDetailsList.get(i).getNoOfUnit(), false, 0, 9999999999.9999,5,locale))){
						 //if(Double.parseDouble(ll)>9999999999.99d)
						 errors.add("noOfUnit",new ActionMessage("error.amount.greaterthan","0","9999999999.9999"));
					 }
				 }
				 else
				 {
					 errors.add("noOfUnit", new ActionMessage("error.string.mandatory")); 
				 }

				 if(null != stockDetailsList.get(i).getIsinCode()
						 && !stockDetailsList.get(i).getIsinCode().trim().isEmpty() )
				 {
					 if (!(errorCode = Validator.checkString(stockDetailsList.get(i).getIsinCode(), true, 1, 30)).equals(Validator.ERROR_NONE)) {
						 errors.add("isinCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
								 50 + ""));
					 }
					 else  {
						 if(null != stockDetailsList.get(i).getStockExchange() && !stockDetailsList.get(i).getStockExchange().trim().isEmpty()) {
						 int count = pinDao.getIsinCodeCount(stockDetailsList.get(i).getIsinCode() , stockDetailsList.get(i).getStockExchange().trim());
						 if(count == 0) {
							 errors.add("isinCode", new ActionMessage("error.isincode.invalid")); 
						 }
						 }
					 }
				 }
				 else
				 {
					 errors.add("isinCode", new ActionMessage("error.string.mandatory")); 
				 }
				 if(null != stockDetailsList.get(i).getIsserIdentType()
						 && !stockDetailsList.get(i).getIsserIdentType().trim().isEmpty() )
				 { 
					 if(!("promoters".equalsIgnoreCase(stockDetailsList.get(i).getIsserIdentType()) 
							 || "groupcompany".equalsIgnoreCase(stockDetailsList.get(i).getIsserIdentType())
							 || "others".equalsIgnoreCase(stockDetailsList.get(i).getIsserIdentType()))){
						 //if(Double.parseDouble(ll)>9999999999.99d)
						 errors.add("isserIdentType",new ActionMessage("error.invalid.field.value"));
					 }
					 else {
						 if("others".equalsIgnoreCase(stockDetailsList.get(i).getIsserIdentType()))

						 {
							 if(null != stockDetailsList.get(i).getIndexName()
									 && !stockDetailsList.get(i).getIndexName().trim().isEmpty() )
							 {
								 if (!(errorCode = Validator.checkString(stockDetailsList.get(i).getIndexName(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
									 errors.add("indexName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
											 50 + ""));
									 DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator", "... indexName...");
								 }
							 }
						 }

					 }

				 }
				 /*else if (null != stockDetailsList.get(i).getIsserIdentType()
						 && stockDetailsList.get(i).getIsserIdentType().trim().isEmpty() )
				 {
					 stockDetailsList.get(i).setIsserIdentType("");
				 }*/
				 if(null != stockDetailsList.get(i).getNominalValue()
						 && !stockDetailsList.get(i).getNominalValue().trim().isEmpty() )
				 {
					 if (!(errorCode = Validator.checkNumber(stockDetailsList.get(i).getNominalValue(), true, 0.0, Double.parseDouble("99999999999.99"),
							 3, locale)).equals(Validator.ERROR_NONE)) {
						 errors.add("nominalValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
								 "99999999999.99" + ""));
						 DefaultLogger.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator",
								 " Error... nominalValue...");
					 }
				 }
				 if(null != stockDetailsList.get(i).getCertNo()
						 && !stockDetailsList.get(i).getCertNo().trim().isEmpty() )
				 {
					 if (!(errorCode = Validator.checkString(stockDetailsList.get(i).getCertNo(), false, 0, 20)).equals(Validator.ERROR_NONE)) {
						 errors.add("certNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
								 20 + ""));
						 DefaultLogger
						 .debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator", "... certNo...");
					 }
				 }

				 if(null != stockDetailsList.get(i).getIssuerName()
						 && !stockDetailsList.get(i).getIssuerName().trim().isEmpty() )
				 {
					 if (!(errorCode = Validator.checkString(stockDetailsList.get(i).getIssuerName(), true, 0, 50))
							 .equals(Validator.ERROR_NONE)) {
						 errors.add("issuerName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
								 50 + ""));
					 }


				 }
				 if( stockDetailsList.get(i).getLineDetailsList()!=null
							&& !stockDetailsList.get(i).getLineDetailsList().isEmpty() 
							&& stockDetailsList.get(i).getLineDetailsList().size()>0 )
					{
						
						for (int l = 0;l<stockDetailsList.get(i).getLineDetailsList().size();l++)
						{
							
							if("U".equalsIgnoreCase(stockDetailsList.get(i).getLineDetailsList().get(l).getActionFlag())
							    &&  "U".equalsIgnoreCase(stockDetailsList.get(i).getActionFlag()))
							{
								if(null != stockDetailsList.get(i).getLineDetailsList().get(l).getClimsLineId()
										 && !stockDetailsList.get(i).getLineDetailsList().get(l).getClimsLineId().trim().isEmpty() )
								{  
									IOtherListedLocal markObj = (IOtherListedLocal) itrxValue.getCollateral();
									IMarketableEquity[] equitylList = markObj.getEquityList();
									long lineEquityId = 0;
								for (int e = 0;e< equitylList.length;e++)
								{
									IMarketableEquityLineDetail[] lineArray = equitylList[e].getLineDetails();
									if(lineArray!=null) {
										for(IMarketableEquityLineDetail lineList : lineArray)
										{
											if(null !=lineList.getLineEquityUniqueID()
													&& !lineList.getLineEquityUniqueID().trim().isEmpty())
											{
												lineEquityId =lineList.getLineDetailId();
												if(!(String.valueOf(lineEquityId)).equals(stockDetailsList.get(i).getLineDetailsList().get(l).getClimsLineId()))
												{
													errors.add("ClimsLineId",new ActionMessage("ClimsLineId is invalid"));
											}
											}
											
										}
										
									}
										
								}	
								}
								else {
									errors.add("ClimsLineId",new ActionMessage("ClimsLineId is mandatory"));
								}
								
							}
							else if("U".equalsIgnoreCase(stockDetailsList.get(i).getLineDetailsList().get(l).getActionFlag())
								    &&  !"U".equalsIgnoreCase(stockDetailsList.get(i).getActionFlag()))
							{
								errors.add("actionFlag",new ActionMessage("Line Action Flag cannot be 'U' when Stock Action Flag is not 'U'."));
							}
							if(null == stockDetailsList.get(i).getLineDetailsList().get(l).getLineUniqueId()
									 || stockDetailsList.get(i).getLineDetailsList().get(l).getLineUniqueId().trim().isEmpty() )
							 {
								 errors.add("LineUniqueId", new ActionMessage("error.string.mandatory")); 
							 }

							 if(null != stockDetailsList.get(i).getLineDetailsList().get(l).getActionFlag()
									 && !stockDetailsList.get(i).getLineDetailsList().get(l).getActionFlag().trim().isEmpty() )
							 {
								 if(!("A".equalsIgnoreCase(stockDetailsList.get(i).getLineDetailsList().get(l).getActionFlag()) 
										 || "U".equalsIgnoreCase(stockDetailsList.get(i).getLineDetailsList().get(l).getActionFlag())
										 || "D".equalsIgnoreCase(stockDetailsList.get(i).getLineDetailsList().get(l).getActionFlag()))){
									 //if(Double.parseDouble(ll)>9999999999.99d)
									 errors.add("actionFlag",new ActionMessage("error.invalid.field.value"));
								 }
								 else 
								 {

										if("A".equalsIgnoreCase(stockDetailsList.get(i).getLineDetailsList().get(l).getActionFlag()))
										{
											if(null != stockDetailsList.get(i).getLineDetailsList().get(l).getLineUniqueId() &&
													!stockDetailsList.get(i).getLineDetailsList().get(l).getLineUniqueId().trim().isEmpty())
											{
												boolean isUniqueIdPresent = collateralDAO.checkUniqueStockLineId(stockDetailsList.get(i).getLineDetailsList().get(l).getLineUniqueId().trim(),collateralreq.getBodyDetails().get(0).getSecurityId());
												if(isUniqueIdPresent)
												{
													errors.add("LineSecUniqueId-"+stockDetailsList.get(i).getLineDetailsList().get(l).getLineUniqueId(),new ActionMessage("LineSecUniqueId is duplicate"));
												}
												else
												{
													if(stockDetailsList.get(i).getLineDetailsList().get(l).getLineUniqueId().length() <=22)
														stockDetailsList.get(i).getLineDetailsList().get(l).setLineUniqueId(stockDetailsList.get(i).getLineDetailsList().get(l).getLineUniqueId());
													else
														errors.add("LineSecUniqueId-"+stockDetailsList.get(i).getLineDetailsList().get(l).getLineUniqueId(),new ActionMessage("LineSecUniqueId length must not exced"));	
												}
											}
											else
											{
												errors.add("LineSecUniqueId",new ActionMessage("LineSecUniqueId is mandatory"));
											}
										}
									
								 }
							 }
							 else
							 {
								 errors.add("actionFlag", new ActionMessage("error.string.mandatory")); 
							 }
							 if(null != stockDetailsList.get(i).getLineDetailsList().get(l).getFasNumber()
									 || stockDetailsList.get(i).getLineDetailsList().get(l).getFasNumber().trim().isEmpty() )
							 {
								 if (!(errorCode = Validator.checkString(stockDetailsList.get(i).getLineDetailsList().get(l).getFasNumber(), false, 1, 20)).equals(Validator.ERROR_NONE)) {
									 errors.add("fasNumber", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "20"));
								 }								
							 }
							 try
							 {
								 List resultlist = collateralDAO.getstockLinelList(collateralreq.getBodyDetails().get(0).getSecurityId());
								 IMarketableEquityLineDetail obLineDetail = (OBMarketableEquityLineDetail) resultlist.get(0);

								 if(null != stockDetailsList.get(i).getLineDetailsList().get(l).getFacilityName()
										 && !stockDetailsList.get(i).getLineDetailsList().get(l).getFacilityName().trim().isEmpty() )
								 {

									 if(null != obLineDetail && ! obLineDetail.getFacilityName().equals(stockDetailsList.get(i).getLineDetailsList().get(l).getFacilityName()))
									 {
										 errors.add("facilityName", new ActionMessage("error.invalid")); 
									 }
								 }
								 else
								 {
									 errors.add("facilityName", new ActionMessage("error.string.mandatory")); 
								 }
								 if(null != stockDetailsList.get(i).getLineDetailsList().get(l).getFacilityId()
										 && ! stockDetailsList.get(i).getLineDetailsList().get(l).getFacilityId().trim().isEmpty() )
								 {

									 if(null != obLineDetail && ! obLineDetail.getFacilityId().equals(stockDetailsList.get(i).getLineDetailsList().get(l).getFacilityId()))
									 {
										 errors.add("facilityId", new ActionMessage("error.invalid")); 
									 }
								 }
								 else
								 {
									 errors.add("facilityId", new ActionMessage("error.string.mandatory")); 
								 }
								 if(null != stockDetailsList.get(i).getLineDetailsList().get(l).getLineNumber()
										 && !stockDetailsList.get(i).getLineDetailsList().get(l).getLineNumber().trim().isEmpty() )
								 {

									 if(null != obLineDetail && ! obLineDetail.getLineNumber().equals(stockDetailsList.get(i).getLineDetailsList().get(l).getLineNumber()))
									 {
										 errors.add("lineNumber", new ActionMessage("error.invalid")); 
									 }
								 }
								 else
								 {
									 errors.add("lineNumber", new ActionMessage("error.string.mandatory")); 
								 }
							 }
								catch(Exception e)
								{
									e.printStackTrace();
								}
							 if(null != stockDetailsList.get(i).getLineDetailsList().get(l).getRemarks()
									 && ! stockDetailsList.get(i).getLineDetailsList().get(l).getRemarks().trim().isEmpty() )
							 {
								 if (!(errorCode = Validator.checkString(stockDetailsList.get(i).getLineDetailsList().get(l).getRemarks(), false, 1, 100)).equals(Validator.ERROR_NONE)) {
										errors.add("remarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "100"));
									}							
							 }
						}
					}
			 }
		 }
		 }
		 
		reqobj.setErrors(errors);
		secBodyReqList.add(reqobj);
		collateralreq.setBodyDetails(secBodyReqList);
		return collateralreq;
	}
	public Object getActualFromDTORest(CollateralRestRequestDTO requestDTO,ICollateral col, String ColSubTypeId,boolean isInsurance) throws ParseException {
		Locale locale = Locale.getDefault();
		ICollateral collateral = null;
		ISpecificChargeAircraft iObj = null;
		IPropertyCollateral prop = null;
		IOtherListedLocal markObj=null;
		if(col != null)
			collateral= col;
		else
			collateral = new OBCollateral();
		
		if(collateral instanceof OBSpecificChargeAircraft)
		{
			 iObj = (ISpecificChargeAircraft) collateral;
		}
		else if (collateral instanceof OBPropertyCollateral)
		{
			prop=(IPropertyCollateral)collateral;
		}
		else if(collateral instanceof OBGuaranteeCollateral) {
			collateral = (IGuaranteeCollateral) collateral ; 
		}
		else if(collateral instanceof OBOtherListedLocal)
		{
			markObj = (IOtherListedLocal) collateral;
		}
		/*ISpecificChargeAircraft iObj = collateral;*/
		
		
		
		if(null != requestDTO.getBodyDetails().get(0).getMonitorProcess() && !requestDTO.getBodyDetails().get(0).getMonitorProcess().trim().isEmpty())
	    {
			if(requestDTO.getBodyDetails().get(0).getMonitorProcess().equalsIgnoreCase("Y"))
			{
				collateral.setMonitorProcess("Y");
			}
			if(requestDTO.getBodyDetails().get(0).getMonitorProcess().equalsIgnoreCase("N"))
			{
				collateral.setMonitorProcess("N");
			}
	    }
		
		if(requestDTO.getBodyDetails().get(0).getMonitorFrequency()!=null && !requestDTO.getBodyDetails().get(0).getMonitorFrequency().trim().isEmpty()){
			collateral.setMonitorFrequency(requestDTO.getBodyDetails().get(0).getMonitorFrequency());
    	}
    	else{
    		collateral.setMonitorFrequency("");
    	}
		
		Date stageDate;
		
		if(null != requestDTO.getBodyDetails().get(0).getSecPriority() && !requestDTO.getBodyDetails().get(0).getSecPriority().trim().isEmpty())
		    {
			 collateral.setSecPriority(requestDTO.getBodyDetails().get(0).getSecPriority());
		    }
		 if(null != requestDTO.getBodyDetails().get(0).getSecurityOrganization() && !requestDTO.getBodyDetails().get(0).getSecurityOrganization().trim().isEmpty())
		    {
			 collateral.setSecurityOrganization(requestDTO.getBodyDetails().get(0).getSecurityOrganization().trim());
		    }
		 
		    if(null != requestDTO.getBodyDetails().get(0).getCollateralCurrency() && !requestDTO.getBodyDetails().get(0).getCollateralCurrency().trim().isEmpty())
		    {
		    	collateral.setCurrencyCode(requestDTO.getBodyDetails().get(0).getCollateralCurrency().trim());
		    }
		    if(null != requestDTO.getBodyDetails().get(0).getValuationAmount() && !requestDTO.getBodyDetails().get(0).getValuationAmount().trim().isEmpty())
		    {
		    	collateral.setValuationAmount(requestDTO.getBodyDetails().get(0).getValuationAmount().trim());
		    }
		    if(!COLLETERAL_CODE.equalsIgnoreCase(collateral.getCollateralCode()))
		    {
		    try {
				if(null != requestDTO.getBodyDetails().get(0).getValuationDate() && !requestDTO.getBodyDetails().get(0).getValuationDate().trim().isEmpty())
				{
					collateral.setValuationDate(ddMMMyyyyDateformat.parse(requestDTO.getBodyDetails().get(0).getValuationDate().trim()));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		    }
		    try {
				if(null != requestDTO.getBodyDetails().get(0).getNextValDate() && !requestDTO.getBodyDetails().get(0).getNextValDate().trim().isEmpty())
				{
					collateral.setNextValDate(ddMMMyyyyDateformat.parse(requestDTO.getBodyDetails().get(0).getNextValDate()));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		    if(null != requestDTO.getBodyDetails().get(0).getTypeOfChange() && !requestDTO.getBodyDetails().get(0).getTypeOfChange().trim().isEmpty())
		    {
		    	collateral.setTypeOfChange(requestDTO.getBodyDetails().get(0).getTypeOfChange());
		    }
		 
		    String typeOfChange = requestDTO.getBodyDetails().get(0).getTypeOfChange();
		if (null != typeOfChange && "SECOND_CHARGE".equals(typeOfChange)) {
			String num = requestDTO.getBodyDetails().get(0).getOtherBankCharge()
					.trim();
			if (num != null && !num.isEmpty() && !StringUtils.isBlank(num)) {
				float numCal = Float.parseFloat(num);
				if (numCal % 1 == 0) {
					num = num.replaceFirst("^0+(?!$)", "");
				}
				collateral.setOtherBankCharge(num);
			}
		}else
		{
			collateral.setOtherBankCharge("");	
		}

		    if(null != requestDTO.getBodyDetails().get(0).getCommonRevalFreq() && !requestDTO.getBodyDetails().get(0).getCommonRevalFreq().trim().isEmpty())
		    {
		    	collateral.setCommonRevalFreq(requestDTO.getBodyDetails().get(0).getCommonRevalFreq());
		    }
		    if(null != requestDTO.getBodyDetails().get(0).getCollateralCurrency() && !requestDTO.getBodyDetails().get(0).getCollateralCurrency().trim().isEmpty())
		    {
		    if (!ICMSConstant.COLTYPE_NOCOLLATERAL.equals(collateral.getCollateralSubType().getSubTypeCode())) {
				collateral.setCurrencyCode(requestDTO.getBodyDetails().get(0).getCollateralCurrency());
			}
		    }
		//CERSAI 
		    if(null != requestDTO.getBodyDetails().get(0).getOwnerOfProperty() && !requestDTO.getBodyDetails().get(0).getOwnerOfProperty().trim().isEmpty())
		    {
		    	collateral.setOwnerOfProperty(requestDTO.getBodyDetails().get(0).getOwnerOfProperty());
		    }
		    if(null != requestDTO.getBodyDetails().get(0).getCinForThirdParty() && !requestDTO.getBodyDetails().get(0).getCinForThirdParty().trim().isEmpty())
		    {
		    	collateral.setCinForThirdParty(requestDTO.getBodyDetails().get(0).getCinForThirdParty());
		    }
		    else
		    {
		    	collateral.setCinForThirdParty("");
		    }
		    if(null != requestDTO.getBodyDetails().get(0).getCersaiTransactionRefNumber() && !requestDTO.getBodyDetails().get(0).getCersaiTransactionRefNumber().trim().isEmpty())
		    {
		    	collateral.setCersaiTransactionRefNumber(requestDTO.getBodyDetails().get(0).getCersaiTransactionRefNumber());
		    }
		    
		    if(null != requestDTO.getBodyDetails().get(0).getCersaiSecurityInterestId() && !requestDTO.getBodyDetails().get(0).getCersaiSecurityInterestId().trim().isEmpty())
		    {
		    	collateral.setCersaiSecurityInterestId(requestDTO.getBodyDetails().get(0).getCersaiSecurityInterestId());
		    }
		    else if(null != requestDTO.getBodyDetails().get(0).getCersaiSecurityInterestId() && requestDTO.getBodyDetails().get(0).getCersaiSecurityInterestId().trim().isEmpty())
		    {
		    	collateral.setCersaiSecurityInterestId("");
		    }
		    if(null != requestDTO.getBodyDetails().get(0).getCersaiAssetId() && !requestDTO.getBodyDetails().get(0).getCersaiAssetId().trim().isEmpty())
		    {
		    	collateral.setCersaiAssetId(requestDTO.getBodyDetails().get(0).getCersaiAssetId());
		    }
		    else if(null != requestDTO.getBodyDetails().get(0).getCersaiAssetId() && requestDTO.getBodyDetails().get(0).getCersaiAssetId().trim().isEmpty())
		    {
		    	collateral.setCersaiAssetId("");
		    }
		    if(null != requestDTO.getBodyDetails().get(0).getCersaiId() && !requestDTO.getBodyDetails().get(0).getCersaiId().trim().isEmpty())
		    {
		    	collateral.setCersaiId(requestDTO.getBodyDetails().get(0).getCersaiId());
		    }
		    if(null != requestDTO.getBodyDetails().get(0).getThirdPartyAddress() && !requestDTO.getBodyDetails().get(0).getThirdPartyAddress().trim().isEmpty())
		    {
		    	collateral.setThirdPartyAddress(requestDTO.getBodyDetails().get(0).getThirdPartyAddress());
		    }
		    if(null != requestDTO.getBodyDetails().get(0).getThirdPartyPincode() && !requestDTO.getBodyDetails().get(0).getThirdPartyPincode().trim().isEmpty())
		    {
		    	collateral.setThirdPartyPincode(requestDTO.getBodyDetails().get(0).getThirdPartyPincode());
		    }
		    if(null != requestDTO.getBodyDetails().get(0).getDateOfCersaiRegisteration() && !requestDTO.getBodyDetails().get(0).getDateOfCersaiRegisteration().trim().isEmpty())
		    {
		    	collateral.setDateOfCersaiRegisteration(DateUtil.convertDate(requestDTO.getBodyDetails().get(0).getDateOfCersaiRegisteration()));
		    }
		    
		    if(null != requestDTO.getBodyDetails().get(0).getSaleDeedPurchaseDate() && !requestDTO.getBodyDetails().get(0).getSaleDeedPurchaseDate().trim().isEmpty())
		    {
		    	collateral.setSaleDeedPurchaseDate(DateUtil.convertDate(requestDTO.getBodyDetails().get(0).getSaleDeedPurchaseDate()));
		    }
		    if(null != requestDTO.getBodyDetails().get(0).getThirdPartyEntity() && !requestDTO.getBodyDetails().get(0).getThirdPartyEntity().trim().isEmpty())
		    {
		    	collateral.setThirdPartyEntity(requestDTO.getBodyDetails().get(0).getThirdPartyEntity());
		    }
		    if(null != requestDTO.getBodyDetails().get(0).getSecurityOwnership() && !requestDTO.getBodyDetails().get(0).getSecurityOwnership().trim().isEmpty())
		    {
		    	collateral.setSecurityOwnership(requestDTO.getBodyDetails().get(0).getSecurityOwnership());
		    }
		    if(null != requestDTO.getBodyDetails().get(0).getThirdPartyState() && !requestDTO.getBodyDetails().get(0).getThirdPartyState().trim().isEmpty())
		    {
		    	collateral.setThirdPartyState(requestDTO.getBodyDetails().get(0).getThirdPartyState());
		    }
		    if(null != requestDTO.getBodyDetails().get(0).getThirdPartyCity() && !requestDTO.getBodyDetails().get(0).getThirdPartyCity().trim().isEmpty())
		    {
		    	collateral.setThirdPartyCity(requestDTO.getBodyDetails().get(0).getThirdPartyCity());
		    }
		    
		    if(null != requestDTO.getBodyDetails().get(0).getMargin() && !requestDTO.getBodyDetails().get(0).getMargin().trim().isEmpty())
		    {
		    	collateral.setMargin(Double.parseDouble(requestDTO.getBodyDetails().get(0).getMargin()));
		    }
		    
		    if (!AbstractCommonMapper.isEmptyOrNull(requestDTO.getBodyDetails().get(0).getCmv())) {
				Amount amt = new Amount(UIUtil.mapStringToBigDecimal(requestDTO.getBodyDetails().get(0).getCmv()),
						new CurrencyCode(collateral.getCurrencyCode()));

				collateral.setCMV(amt);
				collateral.setCMVCcyCode(collateral.getCurrencyCode());
			}
			else {
				collateral.setCMV(null);
			}
		    
		    
			
		    if (collateral instanceof OBSpecificChargeAircraft) {
		    	if ("AB109".equalsIgnoreCase(ColSubTypeId)) {
		    	List<ABSpecRestRequestDTO> abSpecRestRequestDTOList = null;
			    if (null != requestDTO.getBodyDetails().get(0).getAbSpecRestRequestDTOList() ) {
			    	abSpecRestRequestDTOList  = requestDTO.getBodyDetails().get(0).getAbSpecRestRequestDTOList();
				}
			 if(null != abSpecRestRequestDTOList && !abSpecRestRequestDTOList.isEmpty())
			 {
				 ABSpecRestRequestDTO abSpecRestRequestDTO = abSpecRestRequestDTOList.get(0);
				 
				 
				 if (null!=abSpecRestRequestDTO.isPhysicalInspection()  && !abSpecRestRequestDTO.isPhysicalInspection().trim().isEmpty())
					 if(("true").equalsIgnoreCase(abSpecRestRequestDTO.isPhysicalInspection()))
					 iObj.setIsPhysicalInspection(true);
					 else if(("false").equalsIgnoreCase(abSpecRestRequestDTO.isPhysicalInspection()))
					iObj.setIsPhysicalInspection(false);
					 
				 if (null!=abSpecRestRequestDTO.getLastPhysicalInspectDate()  && !abSpecRestRequestDTO.getLastPhysicalInspectDate().trim().isEmpty())
					 iObj.setLastPhysicalInspectDate(ddMMMyyyyDateformat.parse(abSpecRestRequestDTO.getLastPhysicalInspectDate().trim()));
				 
				 if (null!=abSpecRestRequestDTO.getNextPhysicalInspectDate()  && !abSpecRestRequestDTO.getNextPhysicalInspectDate().trim().isEmpty())
					 iObj.setNextPhysicalInspectDate(ddMMMyyyyDateformat.parse(abSpecRestRequestDTO.getNextPhysicalInspectDate().trim()));
				 
				 if (null!=abSpecRestRequestDTO.getStartDate()  && !abSpecRestRequestDTO.getStartDate().trim().isEmpty())
					 iObj.setStartDate(ddMMMyyyyDateformat.parse(abSpecRestRequestDTO.getStartDate().trim()));
				 else if(null!=abSpecRestRequestDTO.getStartDate()  && abSpecRestRequestDTO.getStartDate().trim().isEmpty())
					 iObj.setStartDate(null);
				 if (null!=abSpecRestRequestDTO.getMaturityDate()  && !abSpecRestRequestDTO.getMaturityDate().trim().isEmpty())
					 iObj.setMaturityDate(ddMMMyyyyDateformat.parse(abSpecRestRequestDTO.getMaturityDate().trim()));
				 else if (null!=abSpecRestRequestDTO.getMaturityDate()  && abSpecRestRequestDTO.getMaturityDate().trim().isEmpty())
					 iObj.setMaturityDate(null);
				 if (null!=abSpecRestRequestDTO.getPhysicalInspectionFreq()  && !abSpecRestRequestDTO.getPhysicalInspectionFreq().trim().isEmpty())
					 iObj.setPhysicalInspectionFreqUnit(abSpecRestRequestDTO.getPhysicalInspectionFreq().trim());
				 
				 if (null!=abSpecRestRequestDTO.getGoodStatus()  && !abSpecRestRequestDTO.getGoodStatus().trim().isEmpty())
					 iObj.setGoodStatus(abSpecRestRequestDTO.getGoodStatus().trim());
				 else if(null!=abSpecRestRequestDTO.getGoodStatus()  && abSpecRestRequestDTO.getGoodStatus().trim().isEmpty())
					 iObj.setGoodStatus("");
				 if (null!=abSpecRestRequestDTO.getRamId()  && !abSpecRestRequestDTO.getRamId().trim().isEmpty())
					 iObj.setRamId(abSpecRestRequestDTO.getRamId().trim());
				 else if (null!=abSpecRestRequestDTO.getRamId()  && abSpecRestRequestDTO.getRamId().trim().isEmpty())
					 iObj.setRamId("");
				 
				 if (null!=abSpecRestRequestDTO.getScrapValue()  && !abSpecRestRequestDTO.getScrapValue().trim().isEmpty())
				 {
				 Amount scrapValue = new Amount(UIUtil.mapStringToBigDecimal(abSpecRestRequestDTO.getScrapValue()),
							new CurrencyCode(collateral.getCurrencyCode()));

					 iObj.setScrapValue(scrapValue);
				 }
				 else if(null!=abSpecRestRequestDTO.getScrapValue()  && abSpecRestRequestDTO.getScrapValue().trim().isEmpty())
					 iObj.setScrapValue(null);
				 if (null!=abSpecRestRequestDTO.getEnvRiskyStatus()  && !abSpecRestRequestDTO.getEnvRiskyStatus().trim().isEmpty())
					 iObj.setEnvRiskyStatus(abSpecRestRequestDTO.getEnvRiskyStatus().trim());
				 
				 if (null!=abSpecRestRequestDTO.getEnvRiskyDate()  && !abSpecRestRequestDTO.getEnvRiskyDate().trim().isEmpty())
					 iObj.setEnvRiskyDate(ddMMMyyyyDateformat.parse(abSpecRestRequestDTO.getEnvRiskyDate().trim()));
				 else if(null!=abSpecRestRequestDTO.getEnvRiskyDate()  && abSpecRestRequestDTO.getEnvRiskyDate().trim().isEmpty())
					 iObj.setEnvRiskyDate(null);
				 if (null!=abSpecRestRequestDTO.getRemarks()  && !abSpecRestRequestDTO.getRemarks().trim().isEmpty())
					 iObj.setRemarks(abSpecRestRequestDTO.getRemarks().trim());
				 else if ((null!=abSpecRestRequestDTO.getRemarks()  && abSpecRestRequestDTO.getRemarks().trim().isEmpty()))
					 iObj.setRemarks("");
			 }
	}
		    }
		    
		    if (collateral instanceof OBPropertyCollateral)
		    {
		    	if ("PT701".equalsIgnoreCase(ColSubTypeId))
		    	{
		    	prop = setValuesInColPropObj(requestDTO, ColSubTypeId, locale, prop);
				 PropertyRestRequestDTO propertyRestRequestDTO = requestDTO.getBodyDetails().get(0).getPropertyDetailsList().get(0);
			    
				 if(null != requestDTO.getBodyDetails().get(0).getCmv() && !requestDTO.getBodyDetails().get(0).getCmv().trim().isEmpty())
				 {
					 if (!AbstractCommonMapper.isEmptyOrNull(requestDTO.getBodyDetails().get(0).getCmv())) {
							Amount amt = new Amount(UIUtil.mapStringToBigDecimal(requestDTO.getBodyDetails().get(0).getCmv()),
									new CurrencyCode(collateral.getCurrencyCode()));

							collateral.setCMV(amt);
							collateral.setCMVCcyCode(collateral.getCurrencyCode());
						}
						else {
							collateral.setCMV(null);
						}
		    	}
				 else
				 {
					 collateral.setCMV(calculatLowestSecurityOMV(propertyRestRequestDTO,prop));
				 }
		    	}
		    	collateral.setValuationAmount(calculateLoanableAmount1(requestDTO,collateral));
		    }
		    
		  
		    
		    if ("PT701".equalsIgnoreCase(ColSubTypeId))
		    {
		    	PropertyRestRequestDTO propertyRestRequestDTO = requestDTO.getBodyDetails().get(0).getPropertyDetailsList().get(0);
		    	
		    	  
				    if(null != propertyRestRequestDTO.getIsAdDocFac() && "Y".equalsIgnoreCase(propertyRestRequestDTO.getIsAdDocFac()))
				    {
				    	List<AddtionalDocumentFacilityDetailsRestDTO> addDocFacDetailsList = propertyRestRequestDTO.getAddDocFacDetailsList();
				    	for (AddtionalDocumentFacilityDetailsRestDTO addDocFacDettDTO : addDocFacDetailsList) {
				    		IAddtionalDocumentFacilityDetails docFacObj = new OBAddtionalDocumentFacilityDetails();
			    			if(null != addDocFacDettDTO.getDocFacilityCategory() && !addDocFacDettDTO.getDocFacilityCategory().trim().isEmpty())
			    			{
			    				docFacObj.setDocFacilityCategory(addDocFacDettDTO.getDocFacilityCategory());
			    			}
			    			if(null != addDocFacDettDTO.getUniqueAddDocFacDetID() && !addDocFacDettDTO.getUniqueAddDocFacDetID().trim().isEmpty())
			    			{
			    				docFacObj.setUniqueAddDocFacDetID(addDocFacDettDTO.getUniqueAddDocFacDetID());
			    			}
			    			if(null != addDocFacDettDTO.getDocFacilityTotalAmount() && !addDocFacDettDTO.getDocFacilityTotalAmount().trim().isEmpty())
			    			{
			    				docFacObj.setDocFacilityAmount(addDocFacDettDTO.getDocFacilityTotalAmount());
			    			}
			    			if(null != addDocFacDettDTO.getDocFacilityType() && !addDocFacDettDTO.getDocFacilityType().trim().isEmpty())
			    			{
			    				docFacObj.setDocFacilityType(addDocFacDettDTO.getDocFacilityType());
			    			}
			    			docFacObj.setAddFacDocStatus("SUCCESS");
				    		if("A".equalsIgnoreCase(addDocFacDettDTO.getActionFlag()))
				    		{
				    			addDocumentFacilityDetails(collateral, docFacObj);
				    		}
				    		if("U".equalsIgnoreCase(addDocFacDettDTO.getActionFlag()))
				    		{
				    			if(null != addDocFacDettDTO.getAddDocFacDetID() && !addDocFacDettDTO.getAddDocFacDetID().trim().isEmpty())
				    			{
				    				docFacObj.setAddDocFacDetID(Long.parseLong(addDocFacDettDTO.getAddDocFacDetID()));
				    			}
				    			updateDocumentFacilityDetails(collateral, docFacObj);
				    		}
						}
				    }
		    }
		    if(isInsurance)
		    {
		    	if ("PT701".equalsIgnoreCase(ColSubTypeId) || ("AB109".equalsIgnoreCase(ColSubTypeId))) {
			 List<InsurancePolicyRestRequestDTO> insList = requestDTO.getBodyDetails().get(0).getInsurancePolicyList();
			    IInsurancePolicy[] insPolArr = col.getInsurancePolicies();
				   if(null != insList && !insList.isEmpty()) {
				    for (int i = 0; i < insList.size(); i++) {
				    	InsurancePolicyRestRequestDTO insurancePolicyRestRequestDTO = insList.get(i);
				    	if("A".equalsIgnoreCase(insurancePolicyRestRequestDTO.getActionFlag()))
				    	{
				    		IInsurancePolicy inspolicy = new OBInsurancePolicy();
				    	if("AWAITING".equalsIgnoreCase(insurancePolicyRestRequestDTO.getInsuranceStatus()))
						{
				    		if(null != insurancePolicyRestRequestDTO.getOriginalTargetDate() && !insurancePolicyRestRequestDTO.getOriginalTargetDate().trim().isEmpty())
					    	{
					    		inspolicy.setOriginalTargetDate(ddMMMyyyyDateformat.parse(insurancePolicyRestRequestDTO.getOriginalTargetDate()));
					    	}
						}
				    	if("PENDING_WAIVER".equalsIgnoreCase(insurancePolicyRestRequestDTO.getInsuranceStatus()))
						{
				    		if(null != insurancePolicyRestRequestDTO.getCreditApprover() && !insurancePolicyRestRequestDTO.getCreditApprover().trim().isEmpty())
					    	{
				    			
					    		inspolicy.setCreditApprover(insurancePolicyRestRequestDTO.getCreditApprover());
					    	}
				    		if(null != insurancePolicyRestRequestDTO.getWaivedDate() && !insurancePolicyRestRequestDTO.getWaivedDate().trim().isEmpty())
					    	{
					    		inspolicy.setWaivedDate(ddMMMyyyyDateformat.parse(insurancePolicyRestRequestDTO.getWaivedDate()));
					    	}
						}
				    	if("PENDING_DEFER".equalsIgnoreCase(insurancePolicyRestRequestDTO.getInsuranceStatus()))
						{
				    		if(null != insurancePolicyRestRequestDTO.getOriginalTargetDate() && !insurancePolicyRestRequestDTO.getOriginalTargetDate().trim().isEmpty())
					    	{
					    		inspolicy.setOriginalTargetDate(ddMMMyyyyDateformat.parse(insurancePolicyRestRequestDTO.getOriginalTargetDate()));
					    	}
				    		if(null != insurancePolicyRestRequestDTO.getDateDeferred() && !insurancePolicyRestRequestDTO.getDateDeferred().trim().isEmpty())
					    	{
					    		inspolicy.setDateDeferred(ddMMMyyyyDateformat.parse(insurancePolicyRestRequestDTO.getDateDeferred()));
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getNextDueDate() && !insurancePolicyRestRequestDTO.getNextDueDate().trim().isEmpty())
					    	{
					    		inspolicy.setNextDueDate(ddMMMyyyyDateformat.parse(insurancePolicyRestRequestDTO.getNextDueDate()));
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getCreditApprover() && !insurancePolicyRestRequestDTO.getCreditApprover().trim().isEmpty())
					    	{
					    		inspolicy.setCreditApprover(insurancePolicyRestRequestDTO.getCreditApprover());
					    	}
				    		
						}
				    	if("UPDATE_RECEIVED".equalsIgnoreCase(insurancePolicyRestRequestDTO.getInsuranceStatus()) || "PENDING_RECEIVED".equalsIgnoreCase(insurancePolicyRestRequestDTO.getInsuranceStatus()))
						{
				    		if(null != insurancePolicyRestRequestDTO.getPolicyNo() && !insurancePolicyRestRequestDTO.getPolicyNo().trim().isEmpty())
					    	{
					    		inspolicy.setPolicyNo(insurancePolicyRestRequestDTO.getPolicyNo());
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getCoverNoteNumber() &&!insurancePolicyRestRequestDTO.getCoverNoteNumber().trim().isEmpty())
					    	{
					    		inspolicy.setCoverNoteNumber(insurancePolicyRestRequestDTO.getCoverNoteNumber());
					    	}
					    	else if(null != insurancePolicyRestRequestDTO.getCoverNoteNumber() && insurancePolicyRestRequestDTO.getCoverNoteNumber().trim().isEmpty())
					    	{
					    		inspolicy.setCoverNoteNumber("");
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getInsuranceCompanyName() && !insurancePolicyRestRequestDTO.getInsuranceCompanyName().trim().isEmpty())
					    	{
					    		inspolicy.setInsurerName(insurancePolicyRestRequestDTO.getInsuranceCompanyName());
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getCurrencyCode() && !insurancePolicyRestRequestDTO.getCurrencyCode().trim().isEmpty())
					    	{
					    		inspolicy.setCurrencyCode(insurancePolicyRestRequestDTO.getCurrencyCode());
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getTypeOfPerils1() && !insurancePolicyRestRequestDTO.getTypeOfPerils1().trim().isEmpty())
					    	{
					    		inspolicy.setTypeOfPerils1(insurancePolicyRestRequestDTO.getTypeOfPerils1());
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getInsurableAmount() && !insurancePolicyRestRequestDTO.getInsurableAmount().trim().isEmpty())
					    	{
					    		inspolicy.setInsurableAmount(UIUtil.mapStringToAmount(locale, insurancePolicyRestRequestDTO.getCurrencyCode(), insurancePolicyRestRequestDTO.getInsurableAmount(), null));
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getInsuredAmount() && !insurancePolicyRestRequestDTO.getInsuredAmount().trim().isEmpty())
					    	{
					    		inspolicy.setInsuredAmount(UIUtil.mapStringToAmount(locale, insurancePolicyRestRequestDTO.getCurrencyCode(), insurancePolicyRestRequestDTO.getInsuredAmount(), null));
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getReceivedDate() && !insurancePolicyRestRequestDTO.getReceivedDate().trim().isEmpty())
					    	{
					    		inspolicy.setReceivedDate(ddMMMyyyyDateformat.parse(insurancePolicyRestRequestDTO.getReceivedDate()));
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getEffectiveDate() && !insurancePolicyRestRequestDTO.getEffectiveDate().trim().isEmpty())
					    	{
					    		inspolicy.setEffectiveDate(ddMMMyyyyDateformat.parse(insurancePolicyRestRequestDTO.getEffectiveDate()));
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getExpiryDate() && !insurancePolicyRestRequestDTO.getExpiryDate().trim().isEmpty())
					    	{
					    		inspolicy.setExpiryDate(ddMMMyyyyDateformat.parse(insurancePolicyRestRequestDTO.getExpiryDate()));
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getInsurancePremium())
					    	{
					    		inspolicy.setInsurancePremium(UIUtil.mapStringToAmount(locale, insurancePolicyRestRequestDTO.getCurrencyCode(), insurancePolicyRestRequestDTO.getInsurancePremium(), null));
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getNonScheme_Scheme() && !insurancePolicyRestRequestDTO.getNonScheme_Scheme().trim().isEmpty())
					    	{
					    		inspolicy.setNonSchemeScheme(insurancePolicyRestRequestDTO.getNonScheme_Scheme());
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getAddress() && !insurancePolicyRestRequestDTO.getAddress().trim().isEmpty())
					    	{
					    		OBAddress address = (OBAddress) inspolicy.getAddress();
								if (address == null) {
									address = new OBAddress();
								}
								address.setAddress(insurancePolicyRestRequestDTO.getAddress());
								inspolicy.setAddress(address);
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getRemark1() && !insurancePolicyRestRequestDTO.getRemark1().trim().isEmpty())
					    	{
					    		inspolicy.setRemark1(insurancePolicyRestRequestDTO.getRemark1());
					    	}
					    	else if(null != insurancePolicyRestRequestDTO.getRemark1() && insurancePolicyRestRequestDTO.getRemark1().trim().isEmpty())
					    	{
					    		inspolicy.setRemark1("");
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getRemark2() && !insurancePolicyRestRequestDTO.getRemark2().trim().isEmpty())
					    	{
					    		inspolicy.setRemark2(insurancePolicyRestRequestDTO.getRemark2());
					    	}
					    	else if(null != insurancePolicyRestRequestDTO.getRemark2() && insurancePolicyRestRequestDTO.getRemark2().trim().isEmpty())
					    	{
					    		inspolicy.setRemark2("");
					    	}
					    	if(null != insurancePolicyRestRequestDTO.getInsuredAgainst() && !insurancePolicyRestRequestDTO.getInsuredAgainst().trim().isEmpty())
					    	{
					    		inspolicy.setInsuredAgainst(insurancePolicyRestRequestDTO.getInsuredAgainst());
					    	}
						}
				    	inspolicy.setAcType("Loan");
				    	inspolicy.setAutoDebit("N");
				    	if(null != insurancePolicyRestRequestDTO.getInsuranceUniqueID() && !insurancePolicyRestRequestDTO.getInsuranceUniqueID().trim().isEmpty())
				    	{
				    		inspolicy.setUniqueInsuranceId(insurancePolicyRestRequestDTO.getInsuranceUniqueID());
				    	}
				    	if(null != insurancePolicyRestRequestDTO.getInsuranceStatus() && !insurancePolicyRestRequestDTO.getInsuranceStatus().trim().isEmpty())
				    	{
				    			inspolicy.setInsuranceStatus(insurancePolicyRestRequestDTO.getInsuranceStatus());
				    	}
				    	addInsurance(collateral,inspolicy);
					}
				    	else if("U".equalsIgnoreCase(insurancePolicyRestRequestDTO.getActionFlag()))
				    	{
				    		 if(insPolArr != null && insPolArr.length != 0)
							   {
						    	for (int m = 0; m < insPolArr.length; m++) {
						    		IInsurancePolicy inspolicy = insPolArr[m];
						    		long insurancePolicyID = inspolicy.getInsurancePolicyID();
						    		if(insurancePolicyRestRequestDTO.getInsurancePolicyID().trim().equals(insurancePolicyID+""))
						    		{
						    			if("AWAITING".equalsIgnoreCase(insurancePolicyRestRequestDTO.getInsuranceStatus()))
										{
								    		if(null != insurancePolicyRestRequestDTO.getOriginalTargetDate() && !insurancePolicyRestRequestDTO.getOriginalTargetDate().trim().isEmpty())
									    	{
									    		inspolicy.setOriginalTargetDate(ddMMMyyyyDateformat.parse(insurancePolicyRestRequestDTO.getOriginalTargetDate()));
									    	}
										}
								    	if("PENDING_WAIVER".equalsIgnoreCase(insurancePolicyRestRequestDTO.getInsuranceStatus()))
										{
								    		if(null != insurancePolicyRestRequestDTO.getCreditApprover() && !insurancePolicyRestRequestDTO.getCreditApprover().trim().isEmpty())
									    	{
									    		inspolicy.setCreditApprover(insurancePolicyRestRequestDTO.getCreditApprover());
									    	}
								    		if(null != insurancePolicyRestRequestDTO.getWaivedDate() && !insurancePolicyRestRequestDTO.getWaivedDate().trim().isEmpty())
									    	{
									    		inspolicy.setWaivedDate(ddMMMyyyyDateformat.parse(insurancePolicyRestRequestDTO.getWaivedDate()));
									    	}
										}
								    	if("PENDING_DEFER".equalsIgnoreCase(insurancePolicyRestRequestDTO.getInsuranceStatus()))
										{
								    		if(null != insurancePolicyRestRequestDTO.getOriginalTargetDate() && !insurancePolicyRestRequestDTO.getOriginalTargetDate().trim().isEmpty())
									    	{
									    		inspolicy.setOriginalTargetDate(ddMMMyyyyDateformat.parse(insurancePolicyRestRequestDTO.getOriginalTargetDate()));
									    	}
								    		if(null != insurancePolicyRestRequestDTO.getDateDeferred() && !insurancePolicyRestRequestDTO.getDateDeferred().trim().isEmpty())
									    	{
									    		inspolicy.setDateDeferred(ddMMMyyyyDateformat.parse(insurancePolicyRestRequestDTO.getDateDeferred()));
									    	}
									    	if(null != insurancePolicyRestRequestDTO.getNextDueDate() && !insurancePolicyRestRequestDTO.getNextDueDate().trim().isEmpty())
									    	{
									    		inspolicy.setNextDueDate(ddMMMyyyyDateformat.parse(insurancePolicyRestRequestDTO.getNextDueDate()));
									    	}
									    	if(null != insurancePolicyRestRequestDTO.getCreditApprover() && !insurancePolicyRestRequestDTO.getCreditApprover().trim().isEmpty())
									    	{
									    		inspolicy.setCreditApprover(insurancePolicyRestRequestDTO.getCreditApprover());
									    	}
								    		
										}
								    	if("UPDATE_RECEIVED".equalsIgnoreCase(insurancePolicyRestRequestDTO.getInsuranceStatus()) || "PENDING_RECEIVED".equalsIgnoreCase(insurancePolicyRestRequestDTO.getInsuranceStatus()))
										{
								    		if(null != insurancePolicyRestRequestDTO.getPolicyNo() && !insurancePolicyRestRequestDTO.getPolicyNo().trim().isEmpty())
									    	{
									    		inspolicy.setPolicyNo(insurancePolicyRestRequestDTO.getPolicyNo());
									    	}
									    	if(null != insurancePolicyRestRequestDTO.getCoverNoteNumber() &&!insurancePolicyRestRequestDTO.getCoverNoteNumber().trim().isEmpty())
									    	{
									    		inspolicy.setCoverNoteNumber(insurancePolicyRestRequestDTO.getCoverNoteNumber());
									    	}
									    	else if(null != insurancePolicyRestRequestDTO.getCoverNoteNumber() && insurancePolicyRestRequestDTO.getCoverNoteNumber().trim().isEmpty())
										    	{
										    		inspolicy.setCoverNoteNumber("");
										    	}
									    	if(null != insurancePolicyRestRequestDTO.getInsuranceCompanyName() && !insurancePolicyRestRequestDTO.getInsuranceCompanyName().trim().isEmpty())
									    	{
									    		inspolicy.setInsurerName(insurancePolicyRestRequestDTO.getInsuranceCompanyName());
									    	}
									    	if(null != insurancePolicyRestRequestDTO.getCurrencyCode() && !insurancePolicyRestRequestDTO.getCurrencyCode().trim().isEmpty())
									    	{
									    		inspolicy.setCurrencyCode(insurancePolicyRestRequestDTO.getCurrencyCode());
									    	}
									    	if(null != insurancePolicyRestRequestDTO.getTypeOfPerils1() && !insurancePolicyRestRequestDTO.getTypeOfPerils1().trim().isEmpty())
									    	{
									    		inspolicy.setTypeOfPerils1(insurancePolicyRestRequestDTO.getTypeOfPerils1());
									    	}
									    	if(null != insurancePolicyRestRequestDTO.getInsurableAmount() && !insurancePolicyRestRequestDTO.getInsurableAmount().trim().isEmpty())
									    	{
									    		inspolicy.setInsurableAmount(UIUtil.mapStringToAmount(locale, insurancePolicyRestRequestDTO.getCurrencyCode(), insurancePolicyRestRequestDTO.getInsurableAmount(), null));
									    	}
									    	if(null != insurancePolicyRestRequestDTO.getInsuredAmount() && !insurancePolicyRestRequestDTO.getInsuredAmount().trim().isEmpty())
									    	{
									    		inspolicy.setInsuredAmount(UIUtil.mapStringToAmount(locale, insurancePolicyRestRequestDTO.getCurrencyCode(), insurancePolicyRestRequestDTO.getInsuredAmount(), null));
									    	}
									    	if(null != insurancePolicyRestRequestDTO.getReceivedDate() && !insurancePolicyRestRequestDTO.getReceivedDate().trim().isEmpty())
									    	{
									    		inspolicy.setReceivedDate(ddMMMyyyyDateformat.parse(insurancePolicyRestRequestDTO.getReceivedDate()));
									    	}
									    	if(null != insurancePolicyRestRequestDTO.getEffectiveDate() && !insurancePolicyRestRequestDTO.getEffectiveDate().trim().isEmpty())
									    	{
									    		inspolicy.setEffectiveDate(ddMMMyyyyDateformat.parse(insurancePolicyRestRequestDTO.getEffectiveDate()));
									    	}
									    	if(null != insurancePolicyRestRequestDTO.getExpiryDate() && !insurancePolicyRestRequestDTO.getExpiryDate().trim().isEmpty())
									    	{
									    		inspolicy.setExpiryDate(ddMMMyyyyDateformat.parse(insurancePolicyRestRequestDTO.getExpiryDate()));
									    	}
									    	if(null != insurancePolicyRestRequestDTO.getInsurancePremium())
									    	{
									    		inspolicy.setInsurancePremium(UIUtil.mapStringToAmount(locale, insurancePolicyRestRequestDTO.getCurrencyCode(), insurancePolicyRestRequestDTO.getInsurancePremium(), null));
									    	}
									    	if(null != insurancePolicyRestRequestDTO.getNonScheme_Scheme() && !insurancePolicyRestRequestDTO.getNonScheme_Scheme().trim().isEmpty())
									    	{
									    		inspolicy.setNonSchemeScheme(insurancePolicyRestRequestDTO.getNonScheme_Scheme());
									    	}
									    	if(null != insurancePolicyRestRequestDTO.getAddress() && !insurancePolicyRestRequestDTO.getAddress().trim().isEmpty())
									    	{
									    		OBAddress address = (OBAddress) inspolicy.getAddress();
												if (address == null) {
													address = new OBAddress();
												}
												address.setAddress(insurancePolicyRestRequestDTO.getAddress());
												inspolicy.setAddress(address);
									    	}
									    	if(null != insurancePolicyRestRequestDTO.getAddress() && insurancePolicyRestRequestDTO.getAddress().trim().isEmpty())
									    	{
												inspolicy.setAddress(null);
									    	}
									    	
									    	if(null != insurancePolicyRestRequestDTO.getRemark1() && !insurancePolicyRestRequestDTO.getRemark1().trim().isEmpty())
									    	{
									    		inspolicy.setRemark1(insurancePolicyRestRequestDTO.getRemark1());
									    	}
									    	else if(null != insurancePolicyRestRequestDTO.getRemark1() && insurancePolicyRestRequestDTO.getRemark1().trim().isEmpty())
									    	{
									    		inspolicy.setRemark1("");
									    	}
									    	if(null != insurancePolicyRestRequestDTO.getRemark2() && !insurancePolicyRestRequestDTO.getRemark2().trim().isEmpty())
									    	{
									    		inspolicy.setRemark2(insurancePolicyRestRequestDTO.getRemark2());
									    	}
									    	else if(null != insurancePolicyRestRequestDTO.getRemark2() && insurancePolicyRestRequestDTO.getRemark2().trim().isEmpty())
									    	{
									    		inspolicy.setRemark2("");
									    	}
									    	if(null != insurancePolicyRestRequestDTO.getInsuredAgainst() && !insurancePolicyRestRequestDTO.getInsuredAgainst().trim().isEmpty())
									    	{
									    		inspolicy.setInsuredAgainst(insurancePolicyRestRequestDTO.getInsuredAgainst());
									    	}
									    	else if(null != insurancePolicyRestRequestDTO.getInsuredAgainst() && insurancePolicyRestRequestDTO.getInsuredAgainst().trim().isEmpty())
									    	{
									    		inspolicy.setInsuredAgainst("");
									    	}
										}
								    	if(null != insurancePolicyRestRequestDTO.getInsuranceUniqueID() && !insurancePolicyRestRequestDTO.getInsuranceUniqueID().trim().isEmpty())
								    	{
								    		inspolicy.setUniqueInsuranceId(insurancePolicyRestRequestDTO.getInsuranceUniqueID());
								    	}
								    	if(null != insurancePolicyRestRequestDTO.getInsuranceStatus() && !insurancePolicyRestRequestDTO.getInsuranceStatus().trim().isEmpty())
								    	{
								    		if("DELETED".equalsIgnoreCase(insurancePolicyRestRequestDTO.getInsuranceStatus()))
								    			inspolicy.setStatus(insurancePolicyRestRequestDTO.getInsuranceStatus());
								    		else
								    			inspolicy.setInsuranceStatus(insurancePolicyRestRequestDTO.getInsuranceStatus());
								    	}
								    	updateInsurance(collateral,inspolicy);
						    		}
						    		
						    		
								}
							   }
				    	}
				   }
				    }
		    }
		    }
				 //set Guarantee collateral values
					if (collateral instanceof OBGuaranteeCollateral) {

						if ("GT405".equalsIgnoreCase(ColSubTypeId)) {
							if (requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList() != null
									&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().isEmpty()) {

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRamId()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRamId()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setRamId(
											requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRamId());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRamId()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRamId()
										.trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setRamId("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuaRefNo()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuaRefNo()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setReferenceNo(
											requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuaRefNo());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuaRefNo()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuaRefNo()
										.trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setReferenceNo("");
								}
								
								if(!"".equals(collateral.getCurrencyCode())){
									((IGuaranteeCollateral) collateral).setGuaranteeCcyCode(collateral.getCurrencyCode());
								}else if(!"".equals(collateral.getSCICurrencyCode())){
									((IGuaranteeCollateral) collateral).setGuaranteeCcyCode(collateral.getSCICurrencyCode());
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getAmtGuarantee()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getAmtGuarantee().trim().isEmpty()) {
									Amount amt = null;
									try {
										amt = CurrencyManager.convertToAmount(locale,
												((IGuaranteeCollateral) collateral).getGuaranteeCcyCode(), requestDTO.getBodyDetails()
														.get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAmtGuarantee());
									} catch (Exception e) {
										e.printStackTrace();
									}
									((IGuaranteeCollateral) collateral).setGuaranteeAmount(amt);
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getDateGuarantee()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getDateGuarantee().trim().isEmpty()) {

									Date date = CollateralMapper.compareDate(locale,
											((IGuaranteeCollateral) collateral).getGuaranteeDate(), requestDTO.getBodyDetails().get(0)
													.getGuaranteeCorpRestRequestDTOList().get(0).getDateGuarantee());
									((IGuaranteeCollateral) collateral).setGuaranteeDate(date);
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimPeriod()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimPeriod()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setClaimPeriod(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getClaimPeriod());
								}else {
									((IGuaranteeCollateral) collateral).setClaimPeriod("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getClaimPeriodUnit()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getClaimPeriodUnit().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setClaimPeriodUnit(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getClaimPeriodUnit());
								}else {
									((IGuaranteeCollateral) collateral).setClaimPeriodUnit("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimDate()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimDate()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setClaimDate(UIUtil.mapFormString_OBDate(locale,
											((IGuaranteeCollateral) collateral).getClaimDate(), requestDTO.getBodyDetails().get(0)
													.getGuaranteeCorpRestRequestDTOList().get(0).getClaimDate()));
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimDate()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimDate()
										.trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setClaimDate(null);
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getTelephoneNumber()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getTelephoneNumber().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setTelephoneNumber(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getTelephoneNumber());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getTelephoneNumber()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getTelephoneNumber().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setTelephoneNumber("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getGuarantersDunsNumber()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getGuarantersDunsNumber().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantersDunsNumber(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getGuarantersDunsNumber());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getGuarantersDunsNumber()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getGuarantersDunsNumber().trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setGuarantersDunsNumber("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getGuarantersPan()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getGuarantersPan().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantersPam(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getGuarantersPan());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getGuarantersPan()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getGuarantersPan().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantersPam("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getGuarantersName()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getGuarantersName().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantersName(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getGuarantersName());
								}else {
									((IGuaranteeCollateral) collateral).setGuarantersName("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getGuarantersNamePrefix()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getGuarantersNamePrefix().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantersNamePrefix(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getGuarantersNamePrefix());
								}else {
									((IGuaranteeCollateral) collateral).setGuarantersNamePrefix("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getGuarantersFullName()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getGuarantersFullName().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantersFullName(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getGuarantersFullName());
								}else {
									((IGuaranteeCollateral) collateral).setGuarantersFullName("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getAddressLine1()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getAddressLine1().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAddressLine1(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getAddressLine1());
								}else {
									((IGuaranteeCollateral) collateral).setAddressLine1("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getAddressLine2()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getAddressLine2().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAddressLine2(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getAddressLine2());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getAddressLine2()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getAddressLine2().trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setAddressLine2("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getAddressLine3()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getAddressLine3().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAddressLine3(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getAddressLine3());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getAddressLine3()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getAddressLine3().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAddressLine3("");
								}

								//For country
								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCountry()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCountry()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setCountry(
											requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCountry());
									
									//For Region
									if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRegion()
											&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRegion()
													.trim().isEmpty()) {
										((IGuaranteeCollateral) collateral).setRegion(
												requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRegion());
										
										//For State
										if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getState()
												&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getState()
														.trim().isEmpty()) {
											((IGuaranteeCollateral) collateral).setState(
													requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getState());
											
											//For City
											if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCity()
													&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCity().trim()
															.isEmpty()) {
												((IGuaranteeCollateral) collateral).setCity(
														requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCity());
											} else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCity()
													&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCity().trim()
													.isEmpty()){
												((IGuaranteeCollateral) collateral).setCity("");
											}
										} else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getState()
												&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getState()
												.trim().isEmpty()){
											((IGuaranteeCollateral) collateral).setState("");
											((IGuaranteeCollateral) collateral).setCity("");
										}
									} else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRegion()
											&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRegion()
											.trim().isEmpty()){
										((IGuaranteeCollateral) collateral).setRegion("");
										((IGuaranteeCollateral) collateral).setState("");
										((IGuaranteeCollateral) collateral).setCity("");
									}
								}else {
									((IGuaranteeCollateral) collateral).setCountry("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getDistrict()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getDistrict()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setDistrict(
											requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getDistrict());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getDistrict()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getDistrict()
										.trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setDistrict("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getPinCode()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getPinCode()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setPinCode(
											requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getPinCode());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getPinCode()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getPinCode()
										.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setPinCode("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getTelephoneAreaCode()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getTelephoneAreaCode().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setTelephoneAreaCode(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getTelephoneAreaCode());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getTelephoneAreaCode()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getTelephoneAreaCode().trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setTelephoneAreaCode("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRating()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRating()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setRating(
											requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRating());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRating()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRating()
										.trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setRating("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRecourse()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRecourse()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setRecourse(
											requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRecourse());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRecourse()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRecourse()
										.trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setRecourse("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getDiscriptionOfAssets()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getDiscriptionOfAssets().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setDiscriptionOfAssets(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getDiscriptionOfAssets());
								}else {
									((IGuaranteeCollateral) collateral).setDiscriptionOfAssets("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getAssetStatement()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getAssetStatement().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAssetStatement(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getAssetStatement());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getAssetStatement()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getAssetStatement().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAssetStatement("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getAssetStatement()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getAssetStatement().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAssetStatement(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getAssetStatement());
								}else {
									((IGuaranteeCollateral) collateral).setAssetStatement("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getGuarantorType()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getGuarantorType().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantorType(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getGuarantorType());
								}else {
									((IGuaranteeCollateral) collateral).setGuarantorType("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getGuarantorNature()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getGuarantorNature().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantorNature(requestDTO.getBodyDetails().get(0)
											.getGuaranteeCorpRestRequestDTOList().get(0).getGuarantorNature());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
										.getGuarantorNature()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
												.getGuarantorNature().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantorNature("");
								}

								/*if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getChargeType()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getChargeType().trim().isEmpty()) {
									 collateral.setChargeType(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getChargeType());
								}*/
								if (!ICMSConstant.COLTYPE_NOCOLLATERAL.equals(collateral.getCollateralSubType().getSubTypeCode())) {
										collateral = (OBCollateral) formToOBUpdateLimitCharge(collateral, requestDTO, locale,ColSubTypeId);
								}
								
								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCollateralMaturityDate()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCollateralMaturityDate().trim().isEmpty()) {
									collateral.setCollateralMaturityDate(UIUtil.mapFormString_OBDate(locale,((IGuaranteeCollateral) collateral).getClaimDate(), 
											requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCollateralMaturityDate()));
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCollateralMaturityDate()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCollateralMaturityDate().trim().isEmpty()) {
									collateral.setCollateralMaturityDate(null);
								}
							}
						} else if ("GT408".equalsIgnoreCase(ColSubTypeId)){

							if (requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList() != null
									&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().isEmpty()) {

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRamId()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRamId()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setRamId(
											requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRamId());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRamId()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRamId()
										.trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setRamId("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuaRefNo()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuaRefNo()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setReferenceNo(
											requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuaRefNo());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuaRefNo()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuaRefNo()
										.trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setReferenceNo("");
								}
								
								if(!"".equals(collateral.getCurrencyCode())){
									((IGuaranteeCollateral) collateral).setGuaranteeCcyCode(collateral.getCurrencyCode());
								}else if(!"".equals(collateral.getSCICurrencyCode())){
									((IGuaranteeCollateral) collateral).setGuaranteeCcyCode(collateral.getSCICurrencyCode());
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getAmtGuarantee()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getAmtGuarantee().trim().isEmpty()) {
									Amount amt = null;
									try {
										amt = CurrencyManager.convertToAmount(locale,
												((IGuaranteeCollateral) collateral).getGuaranteeCcyCode(), requestDTO.getBodyDetails()
														.get(0).getGuaranteeIndRestRequestDTOList().get(0).getAmtGuarantee());
									} catch (Exception e) {
										e.printStackTrace();
									}
									((IGuaranteeCollateral) collateral).setGuaranteeAmount(amt);
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getDateGuarantee()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getDateGuarantee().trim().isEmpty()) {

									Date date = CollateralMapper.compareDate(locale,
											((IGuaranteeCollateral) collateral).getGuaranteeDate(), requestDTO.getBodyDetails().get(0)
													.getGuaranteeIndRestRequestDTOList().get(0).getDateGuarantee());
									((IGuaranteeCollateral) collateral).setGuaranteeDate(date);
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimPeriod()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimPeriod()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setClaimPeriod(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getClaimPeriod());
								}else {
									((IGuaranteeCollateral) collateral).setClaimPeriod("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getClaimPeriodUnit()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getClaimPeriodUnit().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setClaimPeriodUnit(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getClaimPeriodUnit());
								}else {
									((IGuaranteeCollateral) collateral).setClaimPeriodUnit("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimDate()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimDate()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setClaimDate(UIUtil.mapFormString_OBDate(locale,
											((IGuaranteeCollateral) collateral).getClaimDate(), requestDTO.getBodyDetails().get(0)
													.getGuaranteeIndRestRequestDTOList().get(0).getClaimDate()));
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimDate()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimDate()
										.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setClaimDate(null);
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getTelephoneNumber()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getTelephoneNumber().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setTelephoneNumber(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getTelephoneNumber());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getTelephoneNumber()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getTelephoneNumber().trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setTelephoneNumber("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getGuarantersDunsNumber()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getGuarantersDunsNumber().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantersDunsNumber(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getGuarantersDunsNumber());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getGuarantersDunsNumber()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getGuarantersDunsNumber().trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setGuarantersDunsNumber("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getGuarantersPan()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getGuarantersPan().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantersPam(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getGuarantersPan());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getGuarantersPan()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getGuarantersPan().trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setGuarantersPam("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getGuarantersName()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getGuarantersName().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantersName(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getGuarantersName());
								}else {
									((IGuaranteeCollateral) collateral).setGuarantersName("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getGuarantersNamePrefix()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getGuarantersNamePrefix().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantersNamePrefix(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getGuarantersNamePrefix());
								}else {
									((IGuaranteeCollateral) collateral).setGuarantersNamePrefix("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getGuarantersFullName()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getGuarantersFullName().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantersFullName(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getGuarantersFullName());
								}else {
									((IGuaranteeCollateral) collateral).setGuarantersFullName("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getAddressLine1()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getAddressLine1().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAddressLine1(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getAddressLine1());
								}else {
									((IGuaranteeCollateral) collateral).setAddressLine1("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getAddressLine2()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getAddressLine2().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAddressLine2(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getAddressLine2());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getAddressLine2()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getAddressLine2().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAddressLine2("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getAddressLine3()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getAddressLine3().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAddressLine3(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getAddressLine3());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getAddressLine3()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getAddressLine3().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAddressLine3("");
								}

								//For country
								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCountry()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCountry()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setCountry(
											requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCountry());
									
									//For Region
									if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRegion()
											&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRegion()
													.trim().isEmpty()) {
										((IGuaranteeCollateral) collateral).setRegion(
												requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRegion());
										
										//For State
										if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getState()
												&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getState()
														.trim().isEmpty()) {
											((IGuaranteeCollateral) collateral).setState(
													requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getState());
											
											//For City
											if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCity()
													&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCity().trim()
															.isEmpty()) {
												((IGuaranteeCollateral) collateral).setCity(
														requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCity());
											} else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCity()
													&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCity().trim()
													.isEmpty()){
												((IGuaranteeCollateral) collateral).setCity("");
											}
										} else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getState()
												&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getState()
												.trim().isEmpty()){
											((IGuaranteeCollateral) collateral).setState("");
											((IGuaranteeCollateral) collateral).setCity("");
										}
									} else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRegion()
											&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRegion()
											.trim().isEmpty()){
										((IGuaranteeCollateral) collateral).setRegion("");
										((IGuaranteeCollateral) collateral).setState("");
										((IGuaranteeCollateral) collateral).setCity("");
									}
								}else {
									((IGuaranteeCollateral) collateral).setCountry("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getDistrict()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getDistrict()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setDistrict(
											requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getDistrict());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getDistrict()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getDistrict()
										.trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setDistrict("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getPinCode()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getPinCode()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setPinCode(
											requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getPinCode());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getPinCode()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getPinCode()
										.trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setPinCode("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getTelephoneAreaCode()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getTelephoneAreaCode().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setTelephoneAreaCode(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getTelephoneAreaCode());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getTelephoneAreaCode()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getTelephoneAreaCode().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setTelephoneAreaCode("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRating()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRating()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setRating(
											requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRating());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRating()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRating()
										.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setRating("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRecourse()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRecourse()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setRecourse(
											requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRecourse());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRecourse()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRecourse()
										.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setRecourse("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getDiscriptionOfAssets()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getDiscriptionOfAssets().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setDiscriptionOfAssets(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getDiscriptionOfAssets());
								}else {
									((IGuaranteeCollateral) collateral).setDiscriptionOfAssets("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getAssetStatement()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getAssetStatement().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAssetStatement(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getAssetStatement());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getAssetStatement()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getAssetStatement().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAssetStatement("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getAssetStatement()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getAssetStatement().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAssetStatement(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getAssetStatement());
								}else {
									((IGuaranteeCollateral) collateral).setAssetStatement("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getGuarantorType()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getGuarantorType().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantorType(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getGuarantorType());
								}else {
									((IGuaranteeCollateral) collateral).setGuarantorType("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getGuarantorNature()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getGuarantorNature().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantorNature(requestDTO.getBodyDetails().get(0)
											.getGuaranteeIndRestRequestDTOList().get(0).getGuarantorNature());
								}else if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
										.getGuarantorNature()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
												.getGuarantorNature().trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setGuarantorNature("");
								}

								/*if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getChargeType()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getChargeType().trim().isEmpty()) {
									 collateral.setChargeType(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getChargeType());
								}*/
								if (!ICMSConstant.COLTYPE_NOCOLLATERAL.equals(collateral.getCollateralSubType().getSubTypeCode())) {
										collateral = (OBCollateral) formToOBUpdateLimitCharge(collateral, requestDTO, locale,ColSubTypeId);
								}
								
								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCollateralMaturityDate()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCollateralMaturityDate().trim().isEmpty()) {
									collateral.setCollateralMaturityDate(UIUtil.mapFormString_OBDate(locale,((IGuaranteeCollateral) collateral).getClaimDate(), 
											requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCollateralMaturityDate()));
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCollateralMaturityDate()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCollateralMaturityDate().trim().isEmpty()){
									collateral.setCollateralMaturityDate(null);
								}
							}
						
						} else if ("GT406".equalsIgnoreCase(ColSubTypeId)){

							if (requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList() != null
									&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().isEmpty()) {

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRamId()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRamId()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setRamId(
											requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRamId());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRamId()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRamId()
										.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setRamId("0");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuaRefNo()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuaRefNo()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setReferenceNo(
											requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuaRefNo());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuaRefNo()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuaRefNo()
										.trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setReferenceNo("");
								}	

								if(!"".equals(collateral.getCurrencyCode())){
									((IGuaranteeCollateral) collateral).setGuaranteeCcyCode(collateral.getCurrencyCode());
								}else if(!"".equals(collateral.getSCICurrencyCode())){
									((IGuaranteeCollateral) collateral).setGuaranteeCcyCode(collateral.getSCICurrencyCode());
								}
								
								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getAmtGuarantee()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getAmtGuarantee().trim().isEmpty()) {
									Amount amt = null;
									try {
										amt = CurrencyManager.convertToAmount(locale,
												((IGuaranteeCollateral) collateral).getGuaranteeCcyCode(), requestDTO.getBodyDetails()
														.get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAmtGuarantee());
									} catch (Exception e) {
										e.printStackTrace();
									}
									((IGuaranteeCollateral) collateral).setGuaranteeAmount(amt);
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getDateGuarantee()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getDateGuarantee().trim().isEmpty()) {

									Date date = CollateralMapper.compareDate(locale,
											((IGuaranteeCollateral) collateral).getGuaranteeDate(), requestDTO.getBodyDetails().get(0)
													.getGuaranteeGovtRestRequestDTOList().get(0).getDateGuarantee());
									((IGuaranteeCollateral) collateral).setGuaranteeDate(date);
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimPeriod()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimPeriod()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setClaimPeriod(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getClaimPeriod());
								}else {
									((IGuaranteeCollateral) collateral).setClaimPeriod("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getClaimPeriodUnit()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getClaimPeriodUnit().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setClaimPeriodUnit(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getClaimPeriodUnit());
								}else {
									((IGuaranteeCollateral) collateral).setClaimPeriodUnit("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimDate()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimDate()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setClaimDate(UIUtil.mapFormString_OBDate(locale,
											((IGuaranteeCollateral) collateral).getClaimDate(), requestDTO.getBodyDetails().get(0)
													.getGuaranteeGovtRestRequestDTOList().get(0).getClaimDate()));
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimDate()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimDate()
										.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setClaimDate(null);
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getTelephoneNumber()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getTelephoneNumber().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setTelephoneNumber(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getTelephoneNumber());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getTelephoneNumber()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getTelephoneNumber().trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setTelephoneNumber("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getGuarantersDunsNumber()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getGuarantersDunsNumber().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantersDunsNumber(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getGuarantersDunsNumber());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getGuarantersDunsNumber()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getGuarantersDunsNumber().trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setGuarantersDunsNumber("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getGuarantersPan()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getGuarantersPan().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantersPam(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getGuarantersPan());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getGuarantersPan()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getGuarantersPan().trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setGuarantersPam("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getGuarantersName()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getGuarantersName().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantersName(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getGuarantersName());
								}else {
									((IGuaranteeCollateral) collateral).setGuarantersName("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getGuarantersNamePrefix()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getGuarantersNamePrefix().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantersNamePrefix(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getGuarantersNamePrefix());
								}else {
									((IGuaranteeCollateral) collateral).setGuarantersNamePrefix("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getGuarantersFullName()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getGuarantersFullName().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantersFullName(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getGuarantersFullName());
								}else {
									((IGuaranteeCollateral) collateral).setGuarantersFullName("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getAddressLine1()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getAddressLine1().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAddressLine1(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getAddressLine1());
								}else {
									((IGuaranteeCollateral) collateral).setAddressLine1("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getAddressLine2()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getAddressLine2().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAddressLine2(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getAddressLine2());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getAddressLine2()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getAddressLine2().trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setAddressLine2("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getAddressLine3()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getAddressLine3().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAddressLine3(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getAddressLine3());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getAddressLine3()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getAddressLine3().trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setAddressLine3("");
								}

								//For country
								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCountry()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCountry()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setCountry(
											requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCountry());
									
									//For Region
									if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRegion()
											&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRegion()
													.trim().isEmpty()) {
										((IGuaranteeCollateral) collateral).setRegion(
												requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRegion());
										
										//For State
										if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getState()
												&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getState()
														.trim().isEmpty()) {
											((IGuaranteeCollateral) collateral).setState(
													requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getState());
											
											//For City
											if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCity()
													&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCity().trim()
															.isEmpty()) {
												((IGuaranteeCollateral) collateral).setCity(
														requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCity());
											} else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCity()
													&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCity().trim()
													.isEmpty()){
												((IGuaranteeCollateral) collateral).setCity("");
											}
										} else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getState()
												&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getState()
												.trim().isEmpty()){
											((IGuaranteeCollateral) collateral).setState("");
											((IGuaranteeCollateral) collateral).setCity("");
										}
									} else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRegion()
											&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRegion()
											.trim().isEmpty()){
										((IGuaranteeCollateral) collateral).setRegion("");
										((IGuaranteeCollateral) collateral).setState("");
										((IGuaranteeCollateral) collateral).setCity("");
									}
								}else {
									((IGuaranteeCollateral) collateral).setCountry("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getDistrict()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getDistrict()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setDistrict(
											requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getDistrict());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getDistrict()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getDistrict()
										.trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setDistrict("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getPinCode()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getPinCode()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setPinCode(
											requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getPinCode());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getPinCode()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getPinCode()
										.trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setPinCode("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getTelephoneAreaCode()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getTelephoneAreaCode().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setTelephoneAreaCode(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getTelephoneAreaCode());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getTelephoneAreaCode()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getTelephoneAreaCode().trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setTelephoneAreaCode("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRating()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRating()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setRating(
											requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRating());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRating()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRating()
										.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setRating("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRecourse()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRecourse()
												.trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setRecourse(
											requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRecourse());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRecourse()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRecourse()
										.trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setRecourse("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getDiscriptionOfAssets()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getDiscriptionOfAssets().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setDiscriptionOfAssets(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getDiscriptionOfAssets());
								}else {
									((IGuaranteeCollateral) collateral).setDiscriptionOfAssets("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getAssetStatement()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getAssetStatement().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAssetStatement(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getAssetStatement());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getAssetStatement()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getAssetStatement().trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setAssetStatement("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getAssetStatement()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getAssetStatement().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setAssetStatement(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getAssetStatement());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getAssetStatement()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getAssetStatement().trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setAssetStatement("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getGuarantorType()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getGuarantorType().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantorType(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getGuarantorType());
								}else {
									((IGuaranteeCollateral) collateral).setGuarantorType("");
								}

								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getGuarantorNature()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getGuarantorNature().trim().isEmpty()) {
									((IGuaranteeCollateral) collateral).setGuarantorNature(requestDTO.getBodyDetails().get(0)
											.getGuaranteeGovtRestRequestDTOList().get(0).getGuarantorNature());
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
										.getGuarantorNature()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
												.getGuarantorNature().trim().isEmpty()){
									((IGuaranteeCollateral) collateral).setGuarantorNature("");
								}

								/*if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getChargeType()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getChargeType().trim().isEmpty()) {
									 collateral.setChargeType(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getChargeType());
								}*/
								if (!ICMSConstant.COLTYPE_NOCOLLATERAL.equals(collateral.getCollateralSubType().getSubTypeCode())) {
										collateral = (OBCollateral) formToOBUpdateLimitCharge(collateral, requestDTO, locale,ColSubTypeId);
								}
								
								if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCollateralMaturityDate()
										&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCollateralMaturityDate().trim().isEmpty()) {
									collateral.setCollateralMaturityDate(UIUtil.mapFormString_OBDate(locale,((IGuaranteeCollateral) collateral).getClaimDate(), 
											requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCollateralMaturityDate()));
								}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCollateralMaturityDate()
										&& requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCollateralMaturityDate().trim().isEmpty()) {
									collateral.setCollateralMaturityDate(null);
								}
							}
						
						}
						
					}
					    
		    
			
					if(collateral instanceof OBOtherListedLocal)
					{
						if ("MS605".equalsIgnoreCase(ColSubTypeId))
						{
							markObj = (IOtherListedLocal) collateral;


							IMarketableEquity eqObj = null;
							IMarketableEquity[] equitylList = markObj.getEquityList();
							List<IMarketableEquity> equityListval = new ArrayList<IMarketableEquity>(Arrays.asList(equitylList)) ;
							List<IMarketableEquity> list = new ArrayList<IMarketableEquity>();
							 List<StockRestRequestDTO> stockDetailsList = requestDTO.getBodyDetails().get(0).getStockDetailsList();
							if (stockDetailsList!= null
									&& !requestDTO.getBodyDetails().get(0)
									.getStockDetailsList().isEmpty()) {

								for (int i = 0;i<stockDetailsList.size();i++)
								{		
									if(null != stockDetailsList.get(i).getActionFlag() 
											&& !"".equals(stockDetailsList.get(i).getActionFlag())
											&& "A".equals(stockDetailsList.get(i).getActionFlag()))
									{
										eqObj = new OBMarketableEquity();
										if(null != stockDetailsList.get(i).getIsinCode() 
												&& !"".equals(stockDetailsList.get(i).getIsinCode()))
										{
											eqObj.setIsinCode(stockDetailsList.get(i).getIsinCode());
										}
										if(null != stockDetailsList.get(i).getStockExchange()
												&& !"".equals(stockDetailsList.get(i).getStockExchange()))
										{
											eqObj.setStockExchange(stockDetailsList.get(i).getStockExchange());
										}
										if(null != stockDetailsList.get(i).getIsserIdentType() 
												&& !"".equals(stockDetailsList.get(i).getIsserIdentType()))
										{
											eqObj.setIssuerIdType(stockDetailsList.get(i).getIsserIdentType());
										}
										else if(null != stockDetailsList.get(i).getIsserIdentType() 
												&& "".equals(stockDetailsList.get(i).getIsserIdentType()))
										{
											eqObj.setIssuerIdType("");
										}
										if("others".equalsIgnoreCase(stockDetailsList.get(i).getIsserIdentType()))
										{
											if(null != stockDetailsList.get(i).getIndexName() 
													&& !"".equals(stockDetailsList.get(i).getIndexName()))
											{
												eqObj.setNameOfIndex(stockDetailsList.get(i).getIndexName());
											}else if (null != stockDetailsList.get(i).getIndexName() 
													&& "".equals(stockDetailsList.get(i).getIndexName()))
											{
												eqObj.setNameOfIndex("");
											}
										}
										if(null != stockDetailsList.get(i).getNoOfUnit() 
												&& !"".equals(stockDetailsList.get(i).getNoOfUnit()))
										{
											eqObj.setNoOfUnits(Double.parseDouble(stockDetailsList.get(i).getNoOfUnit()));
										}
										if(null != stockDetailsList.get(i).getNominalValue() 
												&& !"".equals(stockDetailsList.get(i).getNominalValue()))
										{				    	
											eqObj.setNominalValue(UIUtil.mapStringToAmount(locale, stockDetailsList.get(i).getNominalValue(), 
													stockDetailsList.get(i).getNominalValue(), null));
										}
										else if(null != stockDetailsList.get(i).getNominalValue() 
												&& "".equals(stockDetailsList.get(i).getNominalValue()))
										{
											eqObj.setNominalValue(null);
										}
										if(null != stockDetailsList.get(i).getCertNo() 
												&& !"".equals(stockDetailsList.get(i).getCertNo()))
										{
											eqObj.setCertificateNo(stockDetailsList.get(i).getCertNo());
										}
										else if(null != stockDetailsList.get(i).getCertNo() 
												&& "".equals(stockDetailsList.get(i).getCertNo()))
										{
											eqObj.setCertificateNo("");
										}
										if(null != stockDetailsList.get(i).getIssuerName()
												&& !"".equals(stockDetailsList.get(i).getIssuerName()))
										{
											eqObj.setIssuerName(stockDetailsList.get(i).getIssuerName());
										}
										else if(null != stockDetailsList.get(i).getIssuerName()
												&& "".equals(stockDetailsList.get(i).getIssuerName()))
										{
											eqObj.setIssuerName("");
										}
										if(null != stockDetailsList.get(i).getStockSecUniqueId()
												&& !"".equals(stockDetailsList.get(i).getStockSecUniqueId()))
										{
											eqObj.setEquityUniqueID(stockDetailsList.get(i).getStockSecUniqueId());
										}

										if( stockDetailsList.get(i).getLineDetailsList() != null
												&& !stockDetailsList.get(i).getLineDetailsList().isEmpty())
										{

											for (int li = 0;li<stockDetailsList.get(i).getLineDetailsList().size();li++)
											{
												if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getActionFlag()
														&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getActionFlag())
														&& "A".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getActionFlag()))
												{

													IMarketableEquityLineDetail obLineDetail = new OBMarketableEquityLineDetail();
													if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityName()
															&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityName()))
													{
														obLineDetail.setFacilityName(stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityName());	 
													}
													if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityId()
															&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityId()))
													{
														obLineDetail.setFacilityId(stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityId());	 
													}

													if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getLineNumber()
															&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getLineNumber()))
													{
														obLineDetail.setLineNumber(stockDetailsList.get(i).getLineDetailsList().get(li).getLineNumber());	 
													}

													if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getFasNumber()
															&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getFasNumber()))
													{
														obLineDetail.setFasNumber(stockDetailsList.get(i).getLineDetailsList().get(li).getFasNumber());	 
													}
													else if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getFasNumber()
															&& "".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getFasNumber()))
													{
														obLineDetail.setFasNumber("");
													}
													if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getSerialNumber()
															&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getSerialNumber()))
													{
														obLineDetail.setSerialNumber(stockDetailsList.get(i).getLineDetailsList().get(li).getSerialNumber());	 
													}

													if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getLtv()
															&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getLtv()))
													{
														obLineDetail.setLtv(Double.parseDouble(stockDetailsList.get(i).getLineDetailsList().get(li).getLtv()));	 
													}
													else if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getLtv()
															&& "".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getLtv()))
													{
														obLineDetail.setLtv(null);
													}
													if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks()
															&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks()))
													{
														obLineDetail.setRemarks(stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks());	 
													}
													else if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks()
															&& "".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks()))
													{
														obLineDetail.setRemarks(stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks());
													}
													if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getLineUniqueId()
															&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getLineUniqueId()))
													{
														obLineDetail.setLineEquityUniqueID(stockDetailsList.get(i).getLineDetailsList().get(li).getLineUniqueId());	 
													}
													eqObj= addMarketCollLine(eqObj,obLineDetail);
												}
											}

										}
										markObj=addMarketColl(markObj,eqObj);
									}

									if(null != stockDetailsList.get(i).getActionFlag() 
											&& !"".equals(stockDetailsList.get(i).getActionFlag())
											&& "U".equals(stockDetailsList.get(i).getActionFlag()))
									{
										//IMarketableEquity[] equitylListNew = markObj.getEquityList();
										for (int e = 0;e< equitylList.length;e++)

										{
											
											if(equitylList[e].getEquityID() != 0  && (String.valueOf(equitylList[e].getEquityID()).equals(stockDetailsList.get(i).getClimsItemId())))
											{
												eqObj = equitylList[e];
												if(null != stockDetailsList.get(i).getIsinCode() 
														&& !"".equals(stockDetailsList.get(i).getIsinCode()))
												{
													eqObj.setIsinCode(stockDetailsList.get(i).getIsinCode());
												}
												if(null != stockDetailsList.get(i).getStockExchange()
														&& !"".equals(stockDetailsList.get(i).getStockExchange()))
												{
													eqObj.setStockExchange(stockDetailsList.get(i).getStockExchange());
												}
												if(null != stockDetailsList.get(i).getIsserIdentType() 
														&& !"".equals(stockDetailsList.get(i).getIsserIdentType()))
												{
													eqObj.setIssuerIdType(stockDetailsList.get(i).getIsserIdentType());
												}
												else if(null != stockDetailsList.get(i).getIsserIdentType() 
														&& "".equals(stockDetailsList.get(i).getIsserIdentType()))
												{
													eqObj.setIssuerIdType("");
												}
												if(null != stockDetailsList.get(i).getIndexName() 
														&& !"".equals(stockDetailsList.get(i).getIndexName()))
												{
													eqObj.setNameOfIndex(stockDetailsList.get(i).getIndexName());
												}else if (null != stockDetailsList.get(i).getIndexName() 
														&& "".equals(stockDetailsList.get(i).getIndexName()))
												{
													eqObj.setNameOfIndex("");
												}
												if(null != stockDetailsList.get(i).getNoOfUnit() 
														&& !"".equals(stockDetailsList.get(i).getNoOfUnit()))
												{
													eqObj.setNoOfUnits(Double.parseDouble(stockDetailsList.get(i).getNoOfUnit()));
												}
												if(null != stockDetailsList.get(i).getNominalValue() 
														&& !"".equals(stockDetailsList.get(i).getNominalValue()))
												{				    	
													eqObj.setNominalValue(UIUtil.mapStringToAmount(locale, stockDetailsList.get(i).getNominalValue(), 
															stockDetailsList.get(i).getNominalValue(), null));
												}
												else if(null != stockDetailsList.get(i).getNominalValue() 
														&& "".equals(stockDetailsList.get(i).getNominalValue()))
												{
													eqObj.setNominalValue(null);
												}
												if(null != stockDetailsList.get(i).getCertNo() 
														&& !"".equals(stockDetailsList.get(i).getCertNo()))
												{
													equitylList[e].setCertificateNo(stockDetailsList.get(i).getCertNo());
												}
												else if(null != stockDetailsList.get(i).getCertNo() 
														&& "".equals(stockDetailsList.get(i).getCertNo()))
												{
													eqObj.setCertificateNo("");
												}
												if(null != stockDetailsList.get(i).getIssuerName()
														&& !"".equals(stockDetailsList.get(i).getIssuerName()))
												{
													eqObj.setIssuerName(stockDetailsList.get(i).getIssuerName());
												}
												else if(null != stockDetailsList.get(i).getIssuerName()
														&& "".equals(stockDetailsList.get(i).getIssuerName()))
												{
													eqObj.setIssuerName("");
												}
												if(null != stockDetailsList.get(i).getStockSecUniqueId()
														&& !"".equals(stockDetailsList.get(i).getStockSecUniqueId()))
												{
													eqObj.setEquityUniqueID(stockDetailsList.get(i).getStockSecUniqueId());
												}


												if( stockDetailsList.get(i).getLineDetailsList() != null
														&& !stockDetailsList.get(i).getLineDetailsList().isEmpty())
												{

													for (int li = 0;li<stockDetailsList.get(i).getLineDetailsList().size();li++)
													{
														if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getActionFlag()
																&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getActionFlag())
																&& "A".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getActionFlag()))
														{

															IMarketableEquityLineDetail obLineDetail = new OBMarketableEquityLineDetail();
															if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityName()
																	&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityName()))
															{
																obLineDetail.setFacilityName(stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityName());	 
															}
															if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityId()
																	&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityId()))
															{
																obLineDetail.setFacilityId(stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityId());	 
															}

															if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getLineNumber()
																	&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getLineNumber()))
															{
																obLineDetail.setLineNumber(stockDetailsList.get(i).getLineDetailsList().get(li).getLineNumber());	 
															}

															if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getFasNumber()
																	&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getFasNumber()))
															{
																obLineDetail.setFasNumber(stockDetailsList.get(i).getLineDetailsList().get(li).getFasNumber());	 
															}
															else if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getFasNumber()
																	&& "".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getFasNumber()))
															{
																obLineDetail.setFasNumber("");
															}
															if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getSerialNumber()
																	&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getSerialNumber()))
															{
																obLineDetail.setSerialNumber(stockDetailsList.get(i).getLineDetailsList().get(li).getSerialNumber());	 
															}

															if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getLtv()
																	&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getLtv()))
															{
																obLineDetail.setLtv(Double.parseDouble(stockDetailsList.get(i).getLineDetailsList().get(li).getLtv()));	 
															}
															else if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getLtv()
																	&& "".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getLtv()))
															{
																obLineDetail.setLtv(null);
															}
															if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks()
																	&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks()))
															{
																obLineDetail.setRemarks(stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks());	 
															}
															else if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks()
																	&& "".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks()))
															{
																obLineDetail.setRemarks(stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks());
															}
															if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getLineUniqueId()
																	&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getLineUniqueId()))
															{
																obLineDetail.setLineEquityUniqueID(stockDetailsList.get(i).getLineDetailsList().get(li).getLineUniqueId());	 
															}
															eqObj= addMarketCollLine(eqObj,obLineDetail);
														}
														
														if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getActionFlag()
																&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getActionFlag())
																&& "U".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getActionFlag()))
														{
															IMarketableEquityLineDetail[] lineArray = equitylList[e].getLineDetails();
															
															
															for (int m = 0;m< lineArray.length;m++)

															{
																IMarketableEquityLineDetail obLineDetail = lineArray[m];
																if(null != obLineDetail.getLineEquityUniqueID()  && (String.valueOf(obLineDetail.getLineEquityUniqueID()).equals(stockDetailsList.get(i).getLineDetailsList().get(li).getLineUniqueId())))
																{
																	
															
															
															
															if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityName()
																	&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityName()))
															{
																obLineDetail.setFacilityName(stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityName());	 
															}
															if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityId()
																	&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityId()))
															{
																obLineDetail.setFacilityId(stockDetailsList.get(i).getLineDetailsList().get(li).getFacilityId());	 
															}

															if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getLineNumber()
																	&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getLineNumber()))
															{
																obLineDetail.setLineNumber(stockDetailsList.get(i).getLineDetailsList().get(li).getLineNumber());	 
															}

															if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getFasNumber()
																	&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getFasNumber()))
															{
																obLineDetail.setFasNumber(stockDetailsList.get(i).getLineDetailsList().get(li).getFasNumber());	 
															}
															else if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getFasNumber()
																	&& "".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getFasNumber()))
															{
																obLineDetail.setFasNumber("");
															}
															if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getSerialNumber()
																	&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getSerialNumber()))
															{
																obLineDetail.setSerialNumber(stockDetailsList.get(i).getLineDetailsList().get(li).getSerialNumber());	 
															}

															if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getLtv()
																	&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getLtv()))
															{
																obLineDetail.setLtv(Double.parseDouble(stockDetailsList.get(i).getLineDetailsList().get(li).getLtv()));	 
															}
															else if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getLtv()
																	&& "".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getLtv()))
															{
																obLineDetail.setLtv(null);
															}
															if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks()
																	&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks()))
															{
																obLineDetail.setRemarks(stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks());	 
															}
															else if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks()
																	&& "".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks()))
															{
																obLineDetail.setRemarks(stockDetailsList.get(i).getLineDetailsList().get(li).getRemarks());
															}
															if(null != stockDetailsList.get(i).getLineDetailsList().get(li).getLineUniqueId()
																	&& !"".equals(stockDetailsList.get(i).getLineDetailsList().get(li).getLineUniqueId()))
															{
																obLineDetail.setLineEquityUniqueID(stockDetailsList.get(i).getLineDetailsList().get(li).getLineUniqueId());	 
															}
//															eqObj= addMarketCollLine(eqObj,obLineDetail);
														}}}
													}

												}
											}
										}

									}

								}
							}
						}
					}	
		return collateral;
		}
	public IPropertyCollateral setValuesInColPropObj(CollateralRestRequestDTO requestDTO,
			String ColSubTypeId, Locale locale,
			IPropertyCollateral prop) {
		
			 List<PropertyRestRequestDTO> propertyRestRequestList = requestDTO.getBodyDetails().get(0).getPropertyDetailsList();
			 
			 for (PropertyRestRequestDTO propertyRestRequestDTO : propertyRestRequestList) {
				 if(null != propertyRestRequestDTO.getPropertyId() && !propertyRestRequestDTO.getPropertyId().trim().isEmpty())
				    {
					 prop.setPropertyId(propertyRestRequestDTO.getPropertyId().trim());
					}
				 else if(null != propertyRestRequestDTO.getPropertyId() && propertyRestRequestDTO.getPropertyId().trim().isEmpty())
				 {
					 prop.setPropertyId("");
				 }
if(null != propertyRestRequestDTO.getDescription() && !propertyRestRequestDTO.getDescription().trim().isEmpty())
				    {
					 prop.setDescription(propertyRestRequestDTO.getDescription().trim());
					}
if(null != propertyRestRequestDTO.getPropertyType() && !propertyRestRequestDTO.getPropertyType().trim().isEmpty())
				    {
					 prop.setPropertyType(propertyRestRequestDTO.getPropertyType().trim());
					}
if(null != propertyRestRequestDTO.getPropertyUsage() && !propertyRestRequestDTO.getPropertyUsage().trim().isEmpty())
				    {
					 prop.setPropertyUsage(propertyRestRequestDTO.getPropertyUsage().trim());
					}
else if(null != propertyRestRequestDTO.getPropertyUsage() && propertyRestRequestDTO.getPropertyUsage().trim().isEmpty())
					{
	prop.setPropertyUsage("");
					}
if(null != propertyRestRequestDTO.getSalePurchaseValue() && !propertyRestRequestDTO.getSalePurchaseValue().trim().isEmpty())
				    {
		if(propertyRestRequestDTO.getSalePurchaseValue().equals("") && (prop.getSalePurchaseValue() != null)
				&& (prop.getSalePurchaseValue().getAmount() > 0))
			prop.setSalePurchaseValue(getAmount(locale, prop, "0"));
		else if(null != propertyRestRequestDTO.getSalePurchaseValue() && !propertyRestRequestDTO.getSalePurchaseValue().trim().isEmpty())
					 prop.setSalePurchaseValue(getAmount(locale, prop, propertyRestRequestDTO.getSalePurchaseValue()));
					}
if(null != propertyRestRequestDTO.getDateOfReceiptTitleDeed() && !propertyRestRequestDTO.getDateOfReceiptTitleDeed().trim().isEmpty())
				    {
					 prop.setDateOfReceiptTitleDeed(DateUtil.convertDate(propertyRestRequestDTO.getDateOfReceiptTitleDeed().trim()));
					}
else if(null != propertyRestRequestDTO.getDateOfReceiptTitleDeed() && propertyRestRequestDTO.getDateOfReceiptTitleDeed().trim().isEmpty())
{
 prop.setDateOfReceiptTitleDeed(null);
}
if(null != propertyRestRequestDTO.getPreviousMortCreationDate() && !propertyRestRequestDTO.getPreviousMortCreationDate().trim().isEmpty())
				    {
					 prop.setPreviousMortCreationDate(DateUtil.convertDate(propertyRestRequestDTO.getPreviousMortCreationDate().trim()));
					}
if(null != propertyRestRequestDTO.getSalePurchaseDate() && !propertyRestRequestDTO.getSalePurchaseDate().trim().isEmpty())
				    {
					 prop.setSalePurchaseDate(DateUtil.convertDate(propertyRestRequestDTO.getSalePurchaseDate().trim()));
					}else if(null != propertyRestRequestDTO.getSalePurchaseDate() && 
							propertyRestRequestDTO.getSalePurchaseDate().trim().isEmpty()) {
						prop.setSalePurchaseDate(null);
					}
/*if(null != propertyRestRequestDTO.getMortgageCreExtDateAdd() && !propertyRestRequestDTO.getMortgageCreExtDateAdd().trim().isEmpty())
				    {
					 prop.setMortgageCreExtDateAdd(propertyRestRequestDTO.getMortgageCreExtDateAdd().trim());
					}*/
if(null != propertyRestRequestDTO.getLegalAuditDate() && !propertyRestRequestDTO.getLegalAuditDate().trim().isEmpty())
				    {
					 prop.setLegalAuditDate(DateUtil.convertDate(propertyRestRequestDTO.getLegalAuditDate().trim()));
					}
if(null != propertyRestRequestDTO.getInterveingPeriSearchDate() && !propertyRestRequestDTO.getInterveingPeriSearchDate().trim().isEmpty())
				    {
					 prop.setInterveingPeriSearchDate(DateUtil.convertDate(propertyRestRequestDTO.getInterveingPeriSearchDate().trim()));
					}
if(null != propertyRestRequestDTO.getTypeOfMargage() && !propertyRestRequestDTO.getTypeOfMargage().trim().isEmpty())
				    {
					 prop.setTypeOfMargage(propertyRestRequestDTO.getTypeOfMargage().trim());
					}
if(null != propertyRestRequestDTO.getDocumentReceived() && !propertyRestRequestDTO.getDocumentReceived().trim().isEmpty())
				    {
					 prop.setDocumentReceived(propertyRestRequestDTO.getDocumentReceived().trim());
					}
else if(null != propertyRestRequestDTO.getDocumentReceived() && propertyRestRequestDTO.getDocumentReceived().trim().isEmpty())
{
	prop.setDocumentReceived("");
}
if(null != propertyRestRequestDTO.getMorgageCreatedBy() && !propertyRestRequestDTO.getMorgageCreatedBy().trim().isEmpty())
				    {
					 prop.setMorgageCreatedBy(propertyRestRequestDTO.getMorgageCreatedBy().trim());
					}
if(null != propertyRestRequestDTO.getBinNumber() && !propertyRestRequestDTO.getBinNumber().trim().isEmpty())
				    {
					 prop.setBinNumber(propertyRestRequestDTO.getBinNumber().trim());
					}
else if(null != propertyRestRequestDTO.getBinNumber() && propertyRestRequestDTO.getBinNumber().trim().isEmpty())
{
 prop.setBinNumber("");
}

if(null != propertyRestRequestDTO.getDocumentBlock() && !propertyRestRequestDTO.getDocumentBlock().trim().isEmpty())
				    {
					 prop.setDocumentBlock(propertyRestRequestDTO.getDocumentBlock().trim());
					}
else if(null != propertyRestRequestDTO.getDocumentBlock() && propertyRestRequestDTO.getDocumentBlock().trim().isEmpty())
{
 prop.setDocumentBlock("");
}

if(null != propertyRestRequestDTO.getClaim() && !propertyRestRequestDTO.getClaim().trim().isEmpty())
				    {
					 prop.setClaim(propertyRestRequestDTO.getClaim().trim());
					}
if(null != propertyRestRequestDTO.getClaimType() && !propertyRestRequestDTO.getClaimType().trim().isEmpty())
				    {
					 prop.setClaimType(propertyRestRequestDTO.getClaimType().trim());
					}
if(null != propertyRestRequestDTO.getMortageRegisteredRef() && !propertyRestRequestDTO.getMortageRegisteredRef().trim().isEmpty())
				    {
					System.out.println("mortageRegisteredRef--->6086"+propertyRestRequestDTO.getMortageRegisteredRef());
					DefaultLogger.debug(this, "mortageRegisteredRef---6087>"+propertyRestRequestDTO.getMortageRegisteredRef());
					 prop.setMortageRegisteredRef(propertyRestRequestDTO.getMortageRegisteredRef().trim());
					}
else if(null != propertyRestRequestDTO.getMortageRegisteredRef() && propertyRestRequestDTO.getMortageRegisteredRef().trim().isEmpty())
{
 prop.setMortageRegisteredRef("");
}

if(null != propertyRestRequestDTO.getAdvocateLawyerName() && !propertyRestRequestDTO.getAdvocateLawyerName().trim().isEmpty())
				    {
					 prop.setAdvocateLawyerName(propertyRestRequestDTO.getAdvocateLawyerName().trim());
					}
if(null != propertyRestRequestDTO.getDevGrpCo() && !propertyRestRequestDTO.getDevGrpCo().trim().isEmpty())
				    {
					 prop.setDevGrpCo(propertyRestRequestDTO.getDevGrpCo().trim());
					}
if(null != propertyRestRequestDTO.getProjectName() && !propertyRestRequestDTO.getProjectName().trim().isEmpty())
				    {
					 prop.setProjectName(propertyRestRequestDTO.getProjectName().trim());
					}
else if(null != propertyRestRequestDTO.getProjectName() && propertyRestRequestDTO.getProjectName().trim().isEmpty())
{
 prop.setProjectName("");
}

if(null != propertyRestRequestDTO.getLotNumberPrefix() && !propertyRestRequestDTO.getLotNumberPrefix().trim().isEmpty())
				    {
					 prop.setLotNumberPrefix(propertyRestRequestDTO.getLotNumberPrefix().trim());
					}
if(null != propertyRestRequestDTO.getLotNo() && !propertyRestRequestDTO.getLotNo().trim().isEmpty())
				    {
					 prop.setLotNo(propertyRestRequestDTO.getLotNo().trim());
					}
if(null != propertyRestRequestDTO.getPropertyLotLocation() && !propertyRestRequestDTO.getPropertyLotLocation().trim().isEmpty())
				    {
					 prop.setPropertyLotLocation(propertyRestRequestDTO.getPropertyLotLocation().trim());
					}
else if(null != propertyRestRequestDTO.getPropertyLotLocation() && propertyRestRequestDTO.getPropertyLotLocation().trim().isEmpty())
{
 prop.setPropertyLotLocation("");
}
if(null != propertyRestRequestDTO.getOtherCity() && !propertyRestRequestDTO.getOtherCity().trim().isEmpty())
				    {
					 prop.setOtherCity(propertyRestRequestDTO.getOtherCity().trim());
					}
else if(null != propertyRestRequestDTO.getOtherCity() && propertyRestRequestDTO.getOtherCity().trim().isEmpty())
{
 prop.setOtherCity("");
}
if(null != propertyRestRequestDTO.getPropertyAddress() && !propertyRestRequestDTO.getPropertyAddress().trim().isEmpty())
				    {
					 prop.setPropertyAddress(propertyRestRequestDTO.getPropertyAddress().trim());
					}
if(null != propertyRestRequestDTO.getPropertyAddress2() && !propertyRestRequestDTO.getPropertyAddress2().trim().isEmpty())
				    {
					 prop.setPropertyAddress2(propertyRestRequestDTO.getPropertyAddress2().trim());
					}
else if(null != propertyRestRequestDTO.getPropertyAddress2() && propertyRestRequestDTO.getPropertyAddress2().trim().isEmpty())
{
 prop.setPropertyAddress2("");
}

if(null != propertyRestRequestDTO.getPropertyAddress3() && !propertyRestRequestDTO.getPropertyAddress3().trim().isEmpty())
				    {
					 prop.setPropertyAddress3(propertyRestRequestDTO.getPropertyAddress3().trim());
					}
else if(null != propertyRestRequestDTO.getPropertyAddress3() && propertyRestRequestDTO.getPropertyAddress3().trim().isEmpty())
{
 prop.setPropertyAddress3("");
}

if(null != propertyRestRequestDTO.getPropertyAddress4() && !propertyRestRequestDTO.getPropertyAddress4().trim().isEmpty())
				    {
					 prop.setPropertyAddress4(propertyRestRequestDTO.getPropertyAddress4().trim());
					}
else if(null != propertyRestRequestDTO.getPropertyAddress4() && propertyRestRequestDTO.getPropertyAddress4().trim().isEmpty())
{
 prop.setPropertyAddress4("");
}

if(null != propertyRestRequestDTO.getPropertyAddress5() && !propertyRestRequestDTO.getPropertyAddress5().trim().isEmpty())
				    {
					 prop.setPropertyAddress5(propertyRestRequestDTO.getPropertyAddress5().trim());
					}
else if(null != propertyRestRequestDTO.getPropertyAddress5() && propertyRestRequestDTO.getPropertyAddress5().trim().isEmpty())
{
 prop.setPropertyAddress5("");
}

if(null != propertyRestRequestDTO.getPropertyAddress6() && !propertyRestRequestDTO.getPropertyAddress6().trim().isEmpty())
				    {
					 prop.setPropertyAddress6(propertyRestRequestDTO.getPropertyAddress6().trim());
					}
else if(null != propertyRestRequestDTO.getPropertyAddress6() && propertyRestRequestDTO.getPropertyAddress6().trim().isEmpty())
{
 prop.setPropertyAddress6("");
}
	
if(null != propertyRestRequestDTO.getDescription() && !propertyRestRequestDTO.getDescription().trim().isEmpty())
				    {
					 prop.setDescription(propertyRestRequestDTO.getDescription().trim());
					}
if(null != propertyRestRequestDTO.getEnvRiskyStatus() && !propertyRestRequestDTO.getEnvRiskyStatus().trim().isEmpty())
				    {
					 prop.setEnvRiskyStatus(propertyRestRequestDTO.getEnvRiskyStatus().trim());
					}
else if(null != propertyRestRequestDTO.getEnvRiskyStatus() && propertyRestRequestDTO.getEnvRiskyStatus().trim().isEmpty())
{
 prop.setEnvRiskyStatus("");
}
if(null != propertyRestRequestDTO.getEnvRiskyDate() && !propertyRestRequestDTO.getEnvRiskyDate().trim().isEmpty())
				    {
					 prop.setEnvRiskyDate(DateUtil.convertDate(propertyRestRequestDTO.getEnvRiskyDate().trim()));
					}
if(null != propertyRestRequestDTO.getTsrDate() && !propertyRestRequestDTO.getTsrDate().trim().isEmpty())
				    {
					 prop.setTsrDate(DateUtil.convertDate(propertyRestRequestDTO.getTsrDate().trim()));
					}
if(null != propertyRestRequestDTO.getTsrFrequency() && !propertyRestRequestDTO.getTsrFrequency().trim().isEmpty())
				    {
					 prop.setTsrFrequency(propertyRestRequestDTO.getTsrFrequency().trim());
					}
if(null != propertyRestRequestDTO.getNextTsrDate() && !propertyRestRequestDTO.getNextTsrDate().trim().isEmpty())
				    {
					 prop.setNextTsrDate(DateUtil.convertDate(propertyRestRequestDTO.getNextTsrDate().trim()));
					}
if(null != propertyRestRequestDTO.getCersiaRegistrationDate() && !propertyRestRequestDTO.getCersiaRegistrationDate().trim().isEmpty())
				    {
					 prop.setCersiaRegistrationDate(DateUtil.convertDate(propertyRestRequestDTO.getCersiaRegistrationDate().trim()));
					}
if(null != propertyRestRequestDTO.getConstitution() && !propertyRestRequestDTO.getConstitution().trim().isEmpty())
				    {
					 prop.setConstitution(propertyRestRequestDTO.getConstitution().trim());
					}
if(null != propertyRestRequestDTO.getEnvRiskyRemarks() && !propertyRestRequestDTO.getEnvRiskyRemarks().trim().isEmpty())
				    {
					 prop.setEnvRiskyRemarks(propertyRestRequestDTO.getEnvRiskyRemarks().trim());
					}
else if(null != propertyRestRequestDTO.getEnvRiskyRemarks() && !propertyRestRequestDTO.getEnvRiskyRemarks().trim().isEmpty())
{
 prop.setEnvRiskyRemarks("");
}
if(null != propertyRestRequestDTO.getRevalOverride() && !propertyRestRequestDTO.getRevalOverride().trim().isEmpty())
				    {
					 prop.setRevalOverride(propertyRestRequestDTO.getRevalOverride().trim());
					}
/*if(null != propertyRestRequestDTO.getRevaluation_v1_add() && !propertyRestRequestDTO.getRevaluation_v1_add().trim().isEmpty())
				    {
					 prop.setRevaluation_v1_add(propertyRestRequestDTO.getRevaluation_v1_add().trim());
					}*/
if(null != propertyRestRequestDTO.getValuationDate_v1() && !propertyRestRequestDTO.getValuationDate_v1().trim().isEmpty())
				    {
					 prop.setValuationDate_v1(DateUtil.convertDate(propertyRestRequestDTO.getValuationDate_v1().trim()));
					}
if(null != propertyRestRequestDTO.getValuatorCompany_v1() && !propertyRestRequestDTO.getValuatorCompany_v1().trim().isEmpty())
				    {
					 prop.setValuatorCompany_v1(propertyRestRequestDTO.getValuatorCompany_v1().trim());
					}
if(null != propertyRestRequestDTO.getCategoryOfLandUse_v1() && !propertyRestRequestDTO.getCategoryOfLandUse_v1().trim().isEmpty())
				    {
					 prop.setCategoryOfLandUse_v1(propertyRestRequestDTO.getCategoryOfLandUse_v1().trim());
					}
else if(null != propertyRestRequestDTO.getCategoryOfLandUse_v1() && propertyRestRequestDTO.getCategoryOfLandUse_v1().trim().isEmpty())
{
 prop.setCategoryOfLandUse_v1("");
}
if(null != propertyRestRequestDTO.getDeveloperName_v1() && !propertyRestRequestDTO.getDeveloperName_v1().trim().isEmpty())
				    {
					 prop.setDeveloperName_v1(propertyRestRequestDTO.getDeveloperName_v1().trim());
					}
else if(null != propertyRestRequestDTO.getDeveloperName_v1() && propertyRestRequestDTO.getDeveloperName_v1().trim().isEmpty())
{
 prop.setDeveloperName_v1("");
}
if(null != propertyRestRequestDTO.getCountry_v1() && !propertyRestRequestDTO.getCountry_v1().trim().isEmpty())
				    {
					 prop.setCountry_v1(propertyRestRequestDTO.getCountry_v1().trim());
					}
if(null != propertyRestRequestDTO.getRegion_v1() && !propertyRestRequestDTO.getRegion_v1().trim().isEmpty())
				    {
					 prop.setRegion_v1(propertyRestRequestDTO.getRegion_v1().trim());
					}
if(null != propertyRestRequestDTO.getLocationState_v1() && !propertyRestRequestDTO.getLocationState_v1().trim().isEmpty())
				    {
					 prop.setLocationState_v1(propertyRestRequestDTO.getLocationState_v1().trim());
					}
if(null != propertyRestRequestDTO.getNearestCity_v1() && !propertyRestRequestDTO.getNearestCity_v1().trim().isEmpty())
				    {
					 prop.setNearestCity_v1(propertyRestRequestDTO.getNearestCity_v1().trim());
					}
if(null != propertyRestRequestDTO.getPinCode_v1() && !propertyRestRequestDTO.getPinCode_v1().trim().isEmpty())
				    {
					 prop.setPinCode_v1(propertyRestRequestDTO.getPinCode_v1().trim());
					}

if(null != propertyRestRequestDTO.getLandAreaUOM_v1() && !propertyRestRequestDTO.getLandAreaUOM_v1().trim().isEmpty())
				    {
					 prop.setLandAreaUOM_v1(propertyRestRequestDTO.getLandAreaUOM_v1().trim());
					}
if(null != propertyRestRequestDTO.getLandAreaUOM_v1() && !propertyRestRequestDTO.getLandAreaUOM_v1().trim().isEmpty() && null != propertyRestRequestDTO.getLandArea_v1() && !propertyRestRequestDTO.getLandArea_v1().trim().isEmpty())
prop.setInSqftLandArea_v1(convertToSqfeet(propertyRestRequestDTO.getLandArea_v1(),propertyRestRequestDTO.getLandAreaUOM_v1()));

if(null != propertyRestRequestDTO.getBuiltupArea_v1() && !propertyRestRequestDTO.getBuiltupArea_v1().trim().isEmpty())
				    {
					 try {
						prop.setBuiltupArea_v1(MapperUtil.mapStringToDouble(propertyRestRequestDTO.getBuiltupArea_v1().trim(),locale));
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					}
if(null != propertyRestRequestDTO.getBuiltupAreaUOM_v1() && !propertyRestRequestDTO.getBuiltupAreaUOM_v1().trim().isEmpty())
				    {
					 prop.setBuiltupAreaUOM_v1(propertyRestRequestDTO.getBuiltupAreaUOM_v1().trim());
					}
if(null != propertyRestRequestDTO.getBuiltupAreaUOM_v1() && !propertyRestRequestDTO.getBuiltupAreaUOM_v1().trim().isEmpty() && null != propertyRestRequestDTO.getBuiltupArea_v1() && !propertyRestRequestDTO.getBuiltupArea_v1().trim().isEmpty())
prop.setInSqftBuiltupArea_v1(convertToSqfeet(propertyRestRequestDTO.getBuiltupArea_v1(),propertyRestRequestDTO.getBuiltupAreaUOM_v1()));

if(null != propertyRestRequestDTO.getPropertyCompletionStatus_v1() && !propertyRestRequestDTO.getPropertyCompletionStatus_v1().trim().isEmpty())
				    {
					 prop.setPropertyCompletionStatus_v1(propertyRestRequestDTO.getPropertyCompletionStatus_v1().charAt(0));
					}
if(null != propertyRestRequestDTO.getIsPhysicalInspection_v1() && !propertyRestRequestDTO.getIsPhysicalInspection_v1().trim().isEmpty())
				    {
					 prop.setIsPhysicalInspection_v1(Boolean.valueOf(propertyRestRequestDTO.getIsPhysicalInspection_v1().trim()).booleanValue());
					}
//check isphyInsp true condtion
if(null != propertyRestRequestDTO.getLastPhysicalInspectDate_v1() && !propertyRestRequestDTO.getLastPhysicalInspectDate_v1().trim().isEmpty())
				    {
					 prop.setLastPhysicalInspectDate_v1(DateUtil.convertDate(propertyRestRequestDTO.getLastPhysicalInspectDate_v1()));
					}
if(null != propertyRestRequestDTO.getNextPhysicalInspectDate_v1() && !propertyRestRequestDTO.getNextPhysicalInspectDate_v1().trim().isEmpty())
				    {
					 prop.setNextPhysicalInspectDate_v1(DateUtil.convertDate(propertyRestRequestDTO.getNextPhysicalInspectDate_v1().trim()));
					}
if(null != propertyRestRequestDTO.getPhysicalInspectionFreqUnit_v1() && !propertyRestRequestDTO.getPhysicalInspectionFreqUnit_v1().trim().isEmpty())
				    {
					 prop.setPhysicalInspectionFreqUnit_v1(propertyRestRequestDTO.getPhysicalInspectionFreqUnit_v1().trim());
					}
if(null != propertyRestRequestDTO.getRemarksProperty_v1() && !propertyRestRequestDTO.getRemarksProperty_v1().trim().isEmpty())
				    {
					 prop.setRemarksProperty_v1(propertyRestRequestDTO.getRemarksProperty_v1().trim());
					}
else if(null != propertyRestRequestDTO.getRemarksProperty_v1() && propertyRestRequestDTO.getRemarksProperty_v1().trim().isEmpty())
{
 prop.setRemarksProperty_v1("");
}
if(null != propertyRestRequestDTO.getWaiver() && !propertyRestRequestDTO.getWaiver().trim().isEmpty())
				    {
					 prop.setWaiver(propertyRestRequestDTO.getWaiver().trim());
					}
else if(null != propertyRestRequestDTO.getWaiver() && !propertyRestRequestDTO.getWaiver().trim().isEmpty())
{
 prop.setWaiver("");
}
if(null != propertyRestRequestDTO.getDeferral() && !propertyRestRequestDTO.getDeferral().trim().isEmpty())
				    {
					 prop.setDeferral(propertyRestRequestDTO.getDeferral().trim());
					}
else if(null != propertyRestRequestDTO.getDeferral() && propertyRestRequestDTO.getDeferral().trim().isEmpty())
{
 prop.setDeferral("");
}
if(null != propertyRestRequestDTO.getDeferralId() && !propertyRestRequestDTO.getDeferralId().trim().isEmpty())
				    {
					 prop.setDeferralId(propertyRestRequestDTO.getDeferralId().trim());
					}
else if(null != propertyRestRequestDTO.getDeferralId() && propertyRestRequestDTO.getDeferralId().trim().isEmpty())
{
 prop.setDeferralId("");
}

/*if(null != propertyRestRequestDTO.getRevaluation_v2_add() && !propertyRestRequestDTO.getRevaluation_v2_add().trim().isEmpty())
				    {
					 prop.setRevaluation_v2_add(propertyRestRequestDTO.getRevaluation_v2_add().trim());
					}*/
if(null != propertyRestRequestDTO.getValuationDate_v2() && !propertyRestRequestDTO.getValuationDate_v2().trim().isEmpty())
				    {
					 prop.setValuationDate_v2(DateUtil.convertDate(propertyRestRequestDTO.getValuationDate_v2().trim()));
					}
if(null != propertyRestRequestDTO.getValuatorCompany_v2() && !propertyRestRequestDTO.getValuatorCompany_v2().trim().isEmpty())
				    {
					 prop.setValuatorCompany_v2(propertyRestRequestDTO.getValuatorCompany_v2().trim());
					}
if(null != propertyRestRequestDTO.getCategoryOfLandUse_v2() && !propertyRestRequestDTO.getCategoryOfLandUse_v2().trim().isEmpty())
				    {
					 prop.setCategoryOfLandUse_v2(propertyRestRequestDTO.getCategoryOfLandUse_v2().trim());
					}
else if(null != propertyRestRequestDTO.getCategoryOfLandUse_v2() && propertyRestRequestDTO.getCategoryOfLandUse_v2().trim().isEmpty())
{
 prop.setCategoryOfLandUse_v2("");
}
if(null != propertyRestRequestDTO.getDeveloperName_v2() && !propertyRestRequestDTO.getDeveloperName_v2().trim().isEmpty())
				    {
					 prop.setDeveloperName_v2(propertyRestRequestDTO.getDeveloperName_v2().trim());
					}
else if(null != propertyRestRequestDTO.getDeveloperName_v2() && propertyRestRequestDTO.getDeveloperName_v2().trim().isEmpty())
{
 prop.setDeveloperName_v2("");
}
if(null != propertyRestRequestDTO.getCountry_v2() && !propertyRestRequestDTO.getCountry_v2().trim().isEmpty())
				    {
					 prop.setCountry_v2(propertyRestRequestDTO.getCountry_v2().trim());
					}
if(null != propertyRestRequestDTO.getRegion_v2() && !propertyRestRequestDTO.getRegion_v2().trim().isEmpty())
				    {
					 prop.setRegion_v2(propertyRestRequestDTO.getRegion_v2().trim());
					}
if(null != propertyRestRequestDTO.getLocationState_v2() && !propertyRestRequestDTO.getLocationState_v2().trim().isEmpty())
				    {
					 prop.setLocationState_v2(propertyRestRequestDTO.getLocationState_v2().trim());
					}
if(null != propertyRestRequestDTO.getNearestCity_v2() && !propertyRestRequestDTO.getNearestCity_v2().trim().isEmpty())
				    {
					 prop.setNearestCity_v2(propertyRestRequestDTO.getNearestCity_v2().trim());
					}
if(null != propertyRestRequestDTO.getPinCode_v2() && !propertyRestRequestDTO.getPinCode_v2().trim().isEmpty())
				    {
					 prop.setPinCode_v2(propertyRestRequestDTO.getPinCode_v2().trim());
					}
if(null != propertyRestRequestDTO.getBuiltupAreaUOM_v2() && !propertyRestRequestDTO.getBuiltupAreaUOM_v2().trim().isEmpty() && null != propertyRestRequestDTO.getBuiltupArea_v2() && !propertyRestRequestDTO.getBuiltupArea_v2().trim().isEmpty())
prop.setInSqftBuiltupArea_v2(convertToSqfeet(propertyRestRequestDTO.getBuiltupArea_v2(),propertyRestRequestDTO.getBuiltupAreaUOM_v2()));

if(null != propertyRestRequestDTO.getLandAreaUOM_v2() && !propertyRestRequestDTO.getLandAreaUOM_v2().trim().isEmpty())
				    {
					 prop.setLandAreaUOM_v2(propertyRestRequestDTO.getLandAreaUOM_v2().trim());
					}

if(null != propertyRestRequestDTO.getLandAreaUOM_v2() && !propertyRestRequestDTO.getLandAreaUOM_v2().trim().isEmpty() && null != propertyRestRequestDTO.getLandArea_v2() && !propertyRestRequestDTO.getLandArea_v2().trim().isEmpty())
prop.setInSqftLandArea_v2(convertToSqfeet(propertyRestRequestDTO.getLandArea_v2(),propertyRestRequestDTO.getLandAreaUOM_v2()));


if(null != propertyRestRequestDTO.getBuiltupAreaUOM_v2() && !propertyRestRequestDTO.getBuiltupAreaUOM_v2().trim().isEmpty())
				    {
					 prop.setBuiltupAreaUOM_v2(propertyRestRequestDTO.getBuiltupAreaUOM_v2().trim());
					}

if(null != propertyRestRequestDTO.getPropertyCompletionStatus_v2() && !propertyRestRequestDTO.getPropertyCompletionStatus_v2().trim().isEmpty())
				    {
					 prop.setPropertyCompletionStatus_v2(propertyRestRequestDTO.getPropertyCompletionStatus_v2().charAt(0));
					}


if(null != propertyRestRequestDTO.getIsPhysicalInspection_v2() && !propertyRestRequestDTO.getIsPhysicalInspection_v2().trim().isEmpty())
				    {
					prop.setIsPhysicalInspection_v2(Boolean.valueOf(propertyRestRequestDTO.getIsPhysicalInspection_v2().trim()).booleanValue());
					}
if(null != propertyRestRequestDTO.getLastPhysicalInspectDate_v2() && !propertyRestRequestDTO.getLastPhysicalInspectDate_v2().trim().isEmpty())
				    {
					 prop.setLastPhysicalInspectDate_v2(DateUtil.convertDate(propertyRestRequestDTO.getLastPhysicalInspectDate_v2().trim()));
					}
if(null != propertyRestRequestDTO.getNextPhysicalInspectDate_v2() && !propertyRestRequestDTO.getNextPhysicalInspectDate_v2().trim().isEmpty())
				    {
					 prop.setNextPhysicalInspectDate_v2(DateUtil.convertDate(propertyRestRequestDTO.getNextPhysicalInspectDate_v2().trim()));
					}
if(null != propertyRestRequestDTO.getPhysicalInspectionFreqUnit_v2() && !propertyRestRequestDTO.getPhysicalInspectionFreqUnit_v2().trim().isEmpty())
				    {
					 prop.setPhysicalInspectionFreqUnit_v2(propertyRestRequestDTO.getPhysicalInspectionFreqUnit_v2().trim());
					}
if(null != propertyRestRequestDTO.getRemarksProperty_v2() && !propertyRestRequestDTO.getRemarksProperty_v2().trim().isEmpty())
				    {
					 prop.setRemarksProperty_v2(propertyRestRequestDTO.getRemarksProperty_v2().trim());
					}
else if(null != propertyRestRequestDTO.getRemarksProperty_v2() && propertyRestRequestDTO.getRemarksProperty_v2().trim().isEmpty())
{
 prop.setRemarksProperty_v2("");
}
/*if(null != propertyRestRequestDTO.getRevaluation_v3_add() && !propertyRestRequestDTO.getRevaluation_v3_add().trim().isEmpty())
				    {
					 prop.setRevaluation_v3_add(propertyRestRequestDTO.getRevaluation_v3_add().trim());
					}*/
if(null != propertyRestRequestDTO.getValuationDate_v3() && !propertyRestRequestDTO.getValuationDate_v3().trim().isEmpty())
				    {
					 prop.setValuationDate_v3(DateUtil.convertDate(propertyRestRequestDTO.getValuationDate_v3().trim()));
					}
if(null != propertyRestRequestDTO.getValuatorCompany_v3() && !propertyRestRequestDTO.getValuatorCompany_v3().trim().isEmpty())
				    {
					 prop.setValuatorCompany_v3(propertyRestRequestDTO.getValuatorCompany_v3().trim());
					}
if(null != propertyRestRequestDTO.getCategoryOfLandUse_v3() && !propertyRestRequestDTO.getCategoryOfLandUse_v3().trim().isEmpty())
				    {
					 prop.setCategoryOfLandUse_v3(propertyRestRequestDTO.getCategoryOfLandUse_v3().trim());
					}
else if(null != propertyRestRequestDTO.getCategoryOfLandUse_v3() && propertyRestRequestDTO.getCategoryOfLandUse_v3().trim().isEmpty())
{
 prop.setCategoryOfLandUse_v3("");
}
if(null != propertyRestRequestDTO.getDeveloperName_v3() && !propertyRestRequestDTO.getDeveloperName_v3().trim().isEmpty())
				    {
					 prop.setDeveloperName_v3(propertyRestRequestDTO.getDeveloperName_v3().trim());
					}
else if(null != propertyRestRequestDTO.getDeveloperName_v3() && propertyRestRequestDTO.getDeveloperName_v3().trim().isEmpty())
{
 prop.setDeveloperName_v3("");
}
if(null != propertyRestRequestDTO.getCountry_v3() && !propertyRestRequestDTO.getCountry_v3().trim().isEmpty())
				    {
					 prop.setCountry_v3(propertyRestRequestDTO.getCountry_v3().trim());
					}
if(null != propertyRestRequestDTO.getRegion_v3() && !propertyRestRequestDTO.getRegion_v3().trim().isEmpty())
				    {
					 prop.setRegion_v3(propertyRestRequestDTO.getRegion_v3().trim());
					}
if(null != propertyRestRequestDTO.getLocationState_v3() && !propertyRestRequestDTO.getLocationState_v3().trim().isEmpty())
				    {
					 prop.setLocationState_v3(propertyRestRequestDTO.getLocationState_v3().trim());
					}
if(null != propertyRestRequestDTO.getNearestCity_v3() && !propertyRestRequestDTO.getNearestCity_v3().trim().isEmpty())
				    {
					 prop.setNearestCity_v3(propertyRestRequestDTO.getNearestCity_v3().trim());
					}
if(null != propertyRestRequestDTO.getPinCode_v3() && !propertyRestRequestDTO.getPinCode_v3().trim().isEmpty())
				    {
					 prop.setPinCode_v3(propertyRestRequestDTO.getPinCode_v3().trim());
					}

if(null != propertyRestRequestDTO.getLandAreaUOM_v3() && !propertyRestRequestDTO.getLandAreaUOM_v3().trim().isEmpty())
				    {
					 prop.setLandAreaUOM_v3(propertyRestRequestDTO.getLandAreaUOM_v3().trim());
					}
if(null != propertyRestRequestDTO.getLandAreaUOM_v3() && !propertyRestRequestDTO.getLandAreaUOM_v3().trim().isEmpty() && null != propertyRestRequestDTO.getLandArea_v3() && !propertyRestRequestDTO.getLandArea_v3().trim().isEmpty())
	prop.setInSqftLandArea_v3(convertToSqfeet(propertyRestRequestDTO.getLandArea_v3(),propertyRestRequestDTO.getLandAreaUOM_v3()));


if(null != propertyRestRequestDTO.getBuiltupAreaUOM_v3() && !propertyRestRequestDTO.getBuiltupAreaUOM_v3().trim().isEmpty())
				    {
					 prop.setBuiltupAreaUOM_v3(propertyRestRequestDTO.getBuiltupAreaUOM_v3().trim());
					}
if(null != propertyRestRequestDTO.getBuiltupAreaUOM_v3() && !propertyRestRequestDTO.getBuiltupAreaUOM_v3().trim().isEmpty() && null != propertyRestRequestDTO.getBuiltupArea_v3() && !propertyRestRequestDTO.getBuiltupArea_v3().trim().isEmpty())
		prop.setInSqftBuiltupArea_v3(convertToSqfeet(propertyRestRequestDTO.getBuiltupArea_v3(),propertyRestRequestDTO.getBuiltupAreaUOM_v3()));

if(null != propertyRestRequestDTO.getPropertyCompletionStatus_v3() && !propertyRestRequestDTO.getPropertyCompletionStatus_v3().trim().isEmpty())
				    {
					 prop.setPropertyCompletionStatus_v3(propertyRestRequestDTO.getPropertyCompletionStatus_v3().trim().charAt(0));
					}
if(null != propertyRestRequestDTO.getIsPhysicalInspection_v3() && !propertyRestRequestDTO.getIsPhysicalInspection_v3().trim().isEmpty())
				    {
		prop.setIsPhysicalInspection_v3(Boolean.valueOf(propertyRestRequestDTO.getIsPhysicalInspection_v3().trim()).booleanValue());
					}
if(null != propertyRestRequestDTO.getLastPhysicalInspectDate_v3() && !propertyRestRequestDTO.getLastPhysicalInspectDate_v3().trim().isEmpty())
				    {
					 prop.setLastPhysicalInspectDate_v3(DateUtil.convertDate(propertyRestRequestDTO.getLastPhysicalInspectDate_v3().trim()));
					}
if(null != propertyRestRequestDTO.getNextPhysicalInspectDate_v3() && !propertyRestRequestDTO.getNextPhysicalInspectDate_v3().trim().isEmpty())
				    {
					 prop.setNextPhysicalInspectDate_v3(DateUtil.convertDate(propertyRestRequestDTO.getNextPhysicalInspectDate_v3().trim()));
					}
if(null != propertyRestRequestDTO.getPhysicalInspectionFreqUnit_v3() && !propertyRestRequestDTO.getPhysicalInspectionFreqUnit_v3().trim().isEmpty())
				    {
					 prop.setPhysicalInspectionFreqUnit_v3(propertyRestRequestDTO.getPhysicalInspectionFreqUnit_v3().trim());
					}
if(null != propertyRestRequestDTO.getRemarksProperty_v3() && !propertyRestRequestDTO.getRemarksProperty_v3().trim().isEmpty())
				    {
					 prop.setRemarksProperty_v3(propertyRestRequestDTO.getRemarksProperty_v3().trim());
					}
else if(null != propertyRestRequestDTO.getRemarksProperty_v3() && propertyRestRequestDTO.getRemarksProperty_v3().trim().isEmpty())
{
 prop.setRemarksProperty_v3("");
}
			try {
				
				if (null != propertyRestRequestDTO.getLandValue_v3()
						&& !propertyRestRequestDTO.getLandValue_v3().trim()
								.isEmpty()) {
					prop.setLandValue_v3(MapperUtil.mapStringToDouble(
							propertyRestRequestDTO.getLandValue_v3().trim(),
							locale));
				}
				if (null != propertyRestRequestDTO.getBuildingValue_v3()
						&& !propertyRestRequestDTO.getBuildingValue_v3()
								.trim().isEmpty()) {
					prop.setBuildingValue_v3(
							MapperUtil.mapStringToDouble(
									propertyRestRequestDTO
											.getBuildingValue_v3().trim(),
									locale));
				}
				if (null != propertyRestRequestDTO
						.getReconstructionValueOfTheBuilding_v3()
						&& !propertyRestRequestDTO
								.getReconstructionValueOfTheBuilding_v3()
								.trim().isEmpty()) {
					prop.setReconstructionValueOfTheBuilding_v3(MapperUtil
							.mapStringToDouble(propertyRestRequestDTO
									.getReconstructionValueOfTheBuilding_v3()
									.trim(), locale));
				}
				if (null != propertyRestRequestDTO.getLandArea_v1()
						&& !propertyRestRequestDTO.getLandArea_v1()
								.trim().isEmpty()) {
					prop.setLandArea_v1(
							MapperUtil.mapStringToDouble(
									propertyRestRequestDTO
											.getLandArea_v1().trim(),
									locale));
				}
				if (null != propertyRestRequestDTO.getBuildingValue_v1()
						&& !propertyRestRequestDTO.getBuildingValue_v1()
								.trim().isEmpty()) {
					prop.setBuildingValue_v1(
							MapperUtil.mapStringToDouble(
									propertyRestRequestDTO
											.getBuildingValue_v1().trim(),
									locale));
				}
				if (null != propertyRestRequestDTO
						.getReconstructionValueOfTheBuilding_v1()
						&& !propertyRestRequestDTO
								.getReconstructionValueOfTheBuilding_v1()
								.trim().isEmpty()) {
					prop.setReconstructionValueOfTheBuilding_v1(MapperUtil
							.mapStringToDouble(propertyRestRequestDTO
									.getReconstructionValueOfTheBuilding_v1()
									.trim(), locale));
				}
				if (null != propertyRestRequestDTO.getLandArea_v1()
						&& !propertyRestRequestDTO.getLandArea_v1().trim()
								.isEmpty()) {
					prop.setLandArea_v1(MapperUtil.mapStringToDouble(
							propertyRestRequestDTO.getLandArea_v1().trim(),
							locale));
				}
				if (null != propertyRestRequestDTO.getLandValue_v1()
						&& !propertyRestRequestDTO.getLandValue_v1().trim()
								.isEmpty()) {
					prop.setLandValue_v1(MapperUtil.mapStringToDouble(
							propertyRestRequestDTO.getLandValue_v1().trim(),
							locale));
				}
				if (null != propertyRestRequestDTO.getLandArea_v2()
						&& !propertyRestRequestDTO.getLandArea_v2().trim()
								.isEmpty()) {
					prop.setLandArea_v2(MapperUtil.mapStringToDouble(
							propertyRestRequestDTO.getLandArea_v2().trim(),
							locale));
				}
				if (null != propertyRestRequestDTO.getBuiltupArea_v2()
						&& !propertyRestRequestDTO.getBuiltupArea_v2()
								.trim().isEmpty()) {
					prop.setBuiltupArea_v2(
							MapperUtil.mapStringToDouble(
									propertyRestRequestDTO
											.getBuiltupArea_v2().trim(),
									locale));
				}
				if (null != propertyRestRequestDTO.getLandValue_v2()
						&& !propertyRestRequestDTO.getLandValue_v2().trim()
								.isEmpty()) {
					prop.setLandValue_v2(MapperUtil.mapStringToDouble(
							propertyRestRequestDTO.getLandValue_v2().trim(),
							locale));
				}
				if (null != propertyRestRequestDTO.getBuildingValue_v2()
						&& !propertyRestRequestDTO.getBuildingValue_v2()
								.trim().isEmpty()) {
					prop.setBuildingValue_v2(
							MapperUtil.mapStringToDouble(
									propertyRestRequestDTO
											.getBuildingValue_v2().trim(),
									locale));
				}
				if (null != propertyRestRequestDTO
						.getReconstructionValueOfTheBuilding_v2()
						&& !propertyRestRequestDTO
								.getReconstructionValueOfTheBuilding_v2()
								.trim().isEmpty()) {
					prop.setReconstructionValueOfTheBuilding_v2(MapperUtil
							.mapStringToDouble(propertyRestRequestDTO
									.getReconstructionValueOfTheBuilding_v2()
									.trim(), locale));
				}
				if (null != propertyRestRequestDTO.getBuiltupArea_v3()
						&& !propertyRestRequestDTO.getBuiltupArea_v3()
								.trim().isEmpty()) {
					prop.setBuiltupArea_v3(
							MapperUtil.mapStringToDouble(
									propertyRestRequestDTO
											.getBuiltupArea_v3().trim(),
									locale));
				}
				if (null != propertyRestRequestDTO.getLandArea_v3()
						&& !propertyRestRequestDTO.getLandArea_v3().trim()
								.isEmpty()) {
					prop.setLandArea_v3(MapperUtil.mapStringToDouble(
							propertyRestRequestDTO.getLandArea_v3().trim(),
							locale));
				}
				
				if(null != propertyRestRequestDTO.getTotalPropertyAmount_v1()
						&& !propertyRestRequestDTO.getTotalPropertyAmount_v1()
						.trim().isEmpty())
				{
					Amount cmvAmt = null;
					 
					 cmvAmt = new Amount(UIUtil.mapStringToBigDecimal(propertyRestRequestDTO.getTotalPropertyAmount_v1()),
								new CurrencyCode("INR"));
					prop.setTotalPropertyAmount_v1(cmvAmt);
				}
				
				if(null != propertyRestRequestDTO.getTotalPropertyAmount_v2()
						&& !propertyRestRequestDTO.getTotalPropertyAmount_v2()
						.trim().isEmpty())
				{
					Amount cmvAmt = null;
					 
					 cmvAmt = new Amount(UIUtil.mapStringToBigDecimal(propertyRestRequestDTO.getTotalPropertyAmount_v2()),
								new CurrencyCode("INR"));
					prop.setTotalPropertyAmount_v2(cmvAmt);
				}
				
				if(null != propertyRestRequestDTO.getTotalPropertyAmount_v3()
						&& !propertyRestRequestDTO.getTotalPropertyAmount_v3()
						.trim().isEmpty())
				{
					Amount cmvAmt = null;
					 
					 cmvAmt = new Amount(UIUtil.mapStringToBigDecimal(propertyRestRequestDTO.getTotalPropertyAmount_v3()),
								new CurrencyCode("INR"));
					prop.setTotalPropertyAmount_v3(cmvAmt);
				}
				String version1=collateralDAO.getVersion(prop.getCollateralID(),1);
		     	String version2=collateralDAO.getVersion(prop.getCollateralID(),2);
		     	String version3=collateralDAO.getVersion(prop.getCollateralID(),3);
		     	
		     	int version1Int = 0;
		     	int version3Int = 0;
		     	int version2Int = 0;
		     	if(null != version1 && ASSTValidator.isNumeric(version1))
		     	version1Int = Integer.parseInt(version1);
		     	
		     	if(null != version2 && ASSTValidator.isNumeric(version2))
		     	version2Int = Integer.parseInt(version2);
		     	
		     	if(null != version3 && ASSTValidator.isNumeric(version3))
		     	version3Int = Integer.parseInt(version3);
		     	
		     	Date date = new Date();
				if(null != propertyRestRequestDTO.getRevaluation_v1_add() && !propertyRestRequestDTO.getRevaluation_v1_add().trim().isEmpty())
				{
					if(propertyRestRequestDTO.getRevaluation_v1_add().equalsIgnoreCase("Y"))
					{
						if(version1 != null)
							prop.setVersion1(Integer.toString(version1Int+1));
						else
							prop.setVersion1(Integer.toString(version1Int));
						prop.setValcreationdate_v1(date);
						prop.setVal1_id("");
					}else if(propertyRestRequestDTO.getRevaluation_v1_add().equalsIgnoreCase("N")) {
						prop.setVersion1(Integer.toString(version1Int));
					}
				}else {
					prop.setVersion1(Integer.toString(version1Int));
				}
				
				if(null != propertyRestRequestDTO.getRevaluation_v2_add() && !propertyRestRequestDTO.getRevaluation_v2_add().trim().isEmpty())
				{
					if(propertyRestRequestDTO.getRevaluation_v2_add().equalsIgnoreCase("Y"))
					{
						if(version1 != null)
							prop.setVersion2(Integer.toString(version2Int+1));
						else
							prop.setVersion2(Integer.toString(version2Int));
						prop.setValcreationdate_v2(date);
						prop.setVal2_id("");
					}else if(propertyRestRequestDTO.getRevaluation_v2_add().equalsIgnoreCase("N")) {
						prop.setVersion2(Integer.toString(version2Int));
					}
				}else {
					prop.setVersion2(Integer.toString(version2Int));
				}
				
				if(null != propertyRestRequestDTO.getRevaluation_v3_add() && !propertyRestRequestDTO.getRevaluation_v3_add().trim().isEmpty())
				{
					if(propertyRestRequestDTO.getRevaluation_v3_add().equalsIgnoreCase("Y"))
					{
						if(version1 != null)
							prop.setVersion3(Integer.toString(version3Int+1));
						else
							prop.setVersion3(Integer.toString(version3Int));
						prop.setValcreationdate_v3(date);
						prop.setVal3_id("");
					}else if(propertyRestRequestDTO.getRevaluation_v3_add().equalsIgnoreCase("N")) {
						prop.setVersion3(Integer.toString(version3Int));
					}
				}else {
					prop.setVersion3(Integer.toString(version3Int));
				}
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		return prop;
		
	}
	
	public static void addInsurance(ICollateral iCol, IInsurancePolicy insurance) {
		IInsurancePolicy[] existingArray = iCol.getInsurancePolicies();
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}

		IInsurancePolicy[] newArray = new IInsurancePolicy[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = insurance;

		iCol.setInsurancePolicies(newArray);
	}
	
	public static void updateInsurance(ICollateral iCol, IInsurancePolicy insurance) {
		IInsurancePolicy[] existingArray = iCol.getInsurancePolicies();
		List<IInsurancePolicy> list = new ArrayList<IInsurancePolicy>();
		for (int i = 0; i < existingArray.length; i++) {
			IInsurancePolicy insurancePolicy = existingArray[i];
			if(insurancePolicy.getInsurancePolicyID() == insurance.getInsurancePolicyID())
			{
				list.add(insurance);
			}
			else
			{
				list.add(existingArray[i]);
			}
		}
		IInsurancePolicy[] newArray = list.toArray(new IInsurancePolicy[0]);
		iCol.setInsurancePolicies(newArray);
	}
	public static Date compareDate(Locale locale, Date dateOrigin, String dateStage) {

		Date returnDate = DateUtil.convertDate(locale, dateStage);

		if (dateOrigin != null) {
			String originalDate = DateUtil.formatDate(locale, dateOrigin);
			if (originalDate.equals(dateStage)) {
				returnDate = dateOrigin;
			}
		}
		return returnDate;
	}

	public CollateralRestForm getFormFromDTORest(CollateralRestRequestDTO requestDTO, String ColSubTypeId) {
		
		CollateralRestForm form= new CollateralRestForm(); 
		
			    if(null != requestDTO.getBodyDetails().get(0).getSecPriority() && !requestDTO.getBodyDetails().get(0).getSecPriority().trim().isEmpty())
			    {
			    	form.setSecPriority(requestDTO.getBodyDetails().get(0).getSecPriority());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getCollateralCurrency() && !requestDTO.getBodyDetails().get(0).getCollateralCurrency().trim().isEmpty())
			    {
			    	form.setCollateralCurrency(requestDTO.getBodyDetails().get(0).getCollateralCurrency());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getSecPriority() || "".equals(requestDTO.getBodyDetails().get(0).getSecPriority()))
			    {
			    	form.setSecPriority(requestDTO.getBodyDetails().get(0).getSecPriority());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getMonitorFrequency() && !requestDTO.getBodyDetails().get(0).getMonitorFrequency().trim().isEmpty())
			    {
			    	form.setMonitorFrequency(requestDTO.getBodyDetails().get(0).getMonitorFrequency());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getMonitorProcess() && !requestDTO.getBodyDetails().get(0).getMonitorProcess().trim().isEmpty())
			    {
			    	form.setMonitorProcess(requestDTO.getBodyDetails().get(0).getMonitorProcess());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getSecurityOrganization() && !requestDTO.getBodyDetails().get(0).getSecurityOrganization().trim().isEmpty())
			    {
			    	form.setSecurityOrganization(requestDTO.getBodyDetails().get(0).getSecurityOrganization());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getSecurityOrganization() && !requestDTO.getBodyDetails().get(0).getSecurityOrganization().trim().isEmpty())
			    {
			    	form.setSecurityOrganization(requestDTO.getBodyDetails().get(0).getSecurityOrganization());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getValuationAmount() && !requestDTO.getBodyDetails().get(0).getValuationAmount().trim().isEmpty())
			    {
			    	form.setValuationAmount(requestDTO.getBodyDetails().get(0).getValuationAmount());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getValuationDate() && !requestDTO.getBodyDetails().get(0).getValuationDate().trim().isEmpty())
			    {
			    	form.setValuationDate(requestDTO.getBodyDetails().get(0).getValuationDate());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getCommonRevalFreq() && !requestDTO.getBodyDetails().get(0).getCommonRevalFreq().trim().isEmpty())
			    {
			    	form.setCommonRevalFreq(requestDTO.getBodyDetails().get(0).getCommonRevalFreq());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getNextValDate() && !requestDTO.getBodyDetails().get(0).getNextValDate().trim().isEmpty())
			    {
			    	form.setNextValDate(requestDTO.getBodyDetails().get(0).getNextValDate());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getNextValDate() && !requestDTO.getBodyDetails().get(0).getNextValDate().trim().isEmpty())
			    {
			    	form.setNextValDate(requestDTO.getBodyDetails().get(0).getNextValDate());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getTypeOfChange() && !requestDTO.getBodyDetails().get(0).getTypeOfChange().trim().isEmpty())
			    {
			    	form.setTypeOfChange(requestDTO.getBodyDetails().get(0).getTypeOfChange());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getOtherBankCharge() && !requestDTO.getBodyDetails().get(0).getOtherBankCharge().trim().isEmpty())
			    {
			    	form.setOtherBankCharge(requestDTO.getBodyDetails().get(0).getOtherBankCharge());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getCollateralCategory() && !requestDTO.getBodyDetails().get(0).getCollateralCategory().trim().isEmpty())
			    {
			    	form.setCollateralCategory(requestDTO.getBodyDetails().get(0).getCollateralCategory());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getCersaiApplicableInd() && !requestDTO.getBodyDetails().get(0).getCersaiApplicableInd().trim().isEmpty())
			    {
			    	form.setCersaiApplicableInd(requestDTO.getBodyDetails().get(0).getCersaiApplicableInd());
			    }
			    
			    //Cersai
			    if(null != requestDTO.getBodyDetails().get(0).getSecurityOwnership() && !requestDTO.getBodyDetails().get(0).getSecurityOwnership().trim().isEmpty())
			    {
			    	form.setSecurityOwnership(requestDTO.getBodyDetails().get(0).getSecurityOwnership());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getSecurityOwnership() && !requestDTO.getBodyDetails().get(0).getSecurityOwnership().trim().isEmpty())
			    {
			    	form.setSecurityOwnership(requestDTO.getBodyDetails().get(0).getSecurityOwnership());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getOwnerOfProperty() && !requestDTO.getBodyDetails().get(0).getOwnerOfProperty().trim().isEmpty())
			    {
			    	form.setOwnerOfProperty(requestDTO.getBodyDetails().get(0).getOwnerOfProperty());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getThirdPartyEntity() && !requestDTO.getBodyDetails().get(0).getThirdPartyEntity().trim().isEmpty())
			    {
			    	form.setThirdPartyEntity(requestDTO.getBodyDetails().get(0).getThirdPartyEntity());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getCinForThirdParty() && !requestDTO.getBodyDetails().get(0).getCinForThirdParty().trim().isEmpty())
			    {
			    	form.setCinForThirdParty(requestDTO.getBodyDetails().get(0).getCinForThirdParty());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getCersaiTransactionRefNumber() && !requestDTO.getBodyDetails().get(0).getCersaiTransactionRefNumber().trim().isEmpty())
			    {
			    	form.setCersaiTransactionRefNumber(requestDTO.getBodyDetails().get(0).getCersaiTransactionRefNumber());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getCersaiSecurityInterestId() && !requestDTO.getBodyDetails().get(0).getCersaiSecurityInterestId().trim().isEmpty())
			    {
			    	form.setCersaiSecurityInterestId(requestDTO.getBodyDetails().get(0).getCersaiSecurityInterestId());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getCersaiAssetId() && !requestDTO.getBodyDetails().get(0).getCersaiAssetId().trim().isEmpty())
			    {
			    	form.setCersaiAssetId(requestDTO.getBodyDetails().get(0).getCersaiAssetId());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getDateOfCersaiRegisteration() && !requestDTO.getBodyDetails().get(0).getDateOfCersaiRegisteration().trim().isEmpty())
			    {
			    	form.setDateOfCersaiRegisteration(requestDTO.getBodyDetails().get(0).getDateOfCersaiRegisteration());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getCersaiId() && !requestDTO.getBodyDetails().get(0).getCersaiId().trim().isEmpty())
			    {
			    	form.setCersaiId(requestDTO.getBodyDetails().get(0).getCersaiId());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getSaleDeedPurchaseDate() && !requestDTO.getBodyDetails().get(0).getSaleDeedPurchaseDate().trim().isEmpty())
			    {
			    	form.setSaleDeedPurchaseDate(requestDTO.getBodyDetails().get(0).getSaleDeedPurchaseDate());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getThirdPartyAddress() && !requestDTO.getBodyDetails().get(0).getThirdPartyAddress().trim().isEmpty())
			    {
			    	form.setThirdPartyAddress(requestDTO.getBodyDetails().get(0).getThirdPartyAddress());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getThirdPartyState() && !requestDTO.getBodyDetails().get(0).getThirdPartyState().trim().isEmpty())
			    {
			    	form.setThirdPartyState(requestDTO.getBodyDetails().get(0).getThirdPartyState());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getThirdPartyCity() && !requestDTO.getBodyDetails().get(0).getThirdPartyCity().trim().isEmpty())
			    {
			    	form.setThirdPartyCity(requestDTO.getBodyDetails().get(0).getThirdPartyCity());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getThirdPartyPincode() && !requestDTO.getBodyDetails().get(0).getThirdPartyPincode().trim().isEmpty())
			    {
			    	form.setThirdPartyPincode(requestDTO.getBodyDetails().get(0).getThirdPartyPincode());
			    }
			    else
			    {
			    	form.setThirdPartyPincode("");
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getCmv() && !requestDTO.getBodyDetails().get(0).getCmv().trim().isEmpty())
			    {
			    	form.setAmountCMV(requestDTO.getBodyDetails().get(0).getCmv());
			    }
			    if(null != requestDTO.getBodyDetails().get(0).getMargin() && !requestDTO.getBodyDetails().get(0).getMargin().trim().isEmpty())
			    {
			    	form.setMargin(requestDTO.getBodyDetails().get(0).getMargin());
			    }
			  	  
			    //set form event
			    if ("AB109".equalsIgnoreCase(ColSubTypeId))
			    	form.setEvent("REST_UPDATE_AB_SA_SECURITY");
			    else if ("GT405".equalsIgnoreCase(ColSubTypeId)) 
					form.setEvent("REST_UPDATE_CORP_GUARANTEES_SECURITY");
			    else if ("GT408".equalsIgnoreCase(ColSubTypeId))
					form.setEvent("REST_UPDATE_INDIV_GUARANTEES_SECURITY");
			    else if ("GT406".equalsIgnoreCase(ColSubTypeId))
					form.setEvent("REST_UPDATE_GOVT_GUARANTEES_SECURITY");
			    else if ("PT701".equalsIgnoreCase(ColSubTypeId))
					form.setEvent("REST_UPDATE_PROPERTY_SECURITY");
			    else if ("MS605".equalsIgnoreCase(ColSubTypeId))
					form.setEvent("REST_UPDATE_MARKETABLE_SECURITY");
		
		return form;
		
	}

	public AssetAircraftForm getAssetBasedFormUsingReq(CollateralRestRequestDTO requestDTO) {
		
		
		AssetAircraftForm form = new AssetAircraftForm();
		/*if(requestDTO.getBodyDetails().get(0).getAbSpecRestRequestDTOList() != null && !requestDTO.getBodyDetails().get(0).getAbSpecRestRequestDTOList().isEmpty())
		  {
		  if(null != requestDTO.getBodyDetails().get(0).getAbSpecRestRequestDTOList().get(0).getRamId() && !requestDTO.getBodyDetails().get(0).getAbSpecRestRequestDTOList().get(0).getRamId().trim().isEmpty())
		    {
		    	form.setRamId(requestDTO.getBodyDetails().get(0).getAbSpecRestRequestDTOList().get(0).getRamId());
		    }
		  }
		  */
		  List<ABSpecRestRequestDTO> abSpecRestRequestDTOList = requestDTO.getBodyDetails().get(0).getAbSpecRestRequestDTOList();
			 
			 if(null != abSpecRestRequestDTOList && !abSpecRestRequestDTOList.isEmpty())
			 {
				 ABSpecRestRequestDTO abSpecRestRequestDTO = abSpecRestRequestDTOList.get(0);
				 form.setIsSSC("false");
				 if(null != abSpecRestRequestDTO.getRamId() && !abSpecRestRequestDTO.getRamId().trim().isEmpty())
				  {
					 form.setRamId(abSpecRestRequestDTO.getRamId());
				  }
				 if(null != abSpecRestRequestDTO.getStartDate() && !abSpecRestRequestDTO.getStartDate().trim().isEmpty())
				  {
					 
						form.setStartDate(abSpecRestRequestDTO.getStartDate());
					
				  }
				 if(null != abSpecRestRequestDTO.getMaturityDate() && !abSpecRestRequestDTO.getMaturityDate().trim().isEmpty())
				  {
					 form.setMaturityDate(abSpecRestRequestDTO.getMaturityDate());
				  }
				 if(null != abSpecRestRequestDTO.getIsPhysicalInspection() && !abSpecRestRequestDTO.getIsPhysicalInspection().trim().isEmpty())
				  {
					 form.setIsPhysInsp(abSpecRestRequestDTO.getIsPhysicalInspection());
				  }
				 if(null != abSpecRestRequestDTO.getPhysicalInspectionFreq() && !abSpecRestRequestDTO.getPhysicalInspectionFreq().trim().isEmpty())
				  {
					 form.setPhysInspFreqUOM(abSpecRestRequestDTO.getPhysicalInspectionFreq());
				  }
				 if(null != abSpecRestRequestDTO.getLastPhysicalInspectDate() && !abSpecRestRequestDTO.getLastPhysicalInspectDate().trim().isEmpty())
				  {
					 form.setDatePhyInspec(abSpecRestRequestDTO.getLastPhysicalInspectDate());
				  }
				 if(null != abSpecRestRequestDTO.getNextPhysicalInspectDate() && !abSpecRestRequestDTO.getNextPhysicalInspectDate().trim().isEmpty())
				  {
					 form.setNextPhysInspDate(abSpecRestRequestDTO.getNextPhysicalInspectDate());
				  }
				 if(null != abSpecRestRequestDTO.getGoodStatus() && !abSpecRestRequestDTO.getGoodStatus().trim().isEmpty())
				  {
					 form.setGoodStatus(abSpecRestRequestDTO.getGoodStatus());
				  }
				 if(null != abSpecRestRequestDTO.getScrapValue() && !abSpecRestRequestDTO.getScrapValue().trim().isEmpty())
				  {
					 form.setScrapValue(abSpecRestRequestDTO.getScrapValue());
				  }
				 if(null != abSpecRestRequestDTO.getEnvRiskyStatus() && !abSpecRestRequestDTO.getEnvRiskyStatus().trim().isEmpty())
				  {
					 form.setSecEnvRisky(abSpecRestRequestDTO.getEnvRiskyStatus());
				  }
				 if(null != abSpecRestRequestDTO.getNextPhysicalInspectDate() && !abSpecRestRequestDTO.getNextPhysicalInspectDate().trim().isEmpty())
				  {
					 form.setDateSecurityEnv(abSpecRestRequestDTO.getNextPhysicalInspectDate());
				  }
				 if(null != abSpecRestRequestDTO.getRemarks() && !abSpecRestRequestDTO.getRemarks().trim().isEmpty())
				  {
					 form.setRemarkEnvRisk(abSpecRestRequestDTO.getRemarks());
				  }
			 }
			 
			 return form;
	}
	
	public InsurancePolicyRestRequestDTO getInsuranceRestFromReq(InsurancePolicyRestRequestDTO insReqDto,String securityId) {
		ActionErrors errors = new ActionErrors();
		
		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		if(null != insReqDto.getActionFlag() && !insReqDto.getActionFlag().trim().isEmpty())
		{
			if("A".equalsIgnoreCase(insReqDto.getActionFlag()))
			{
				insReqDto.setActionFlag("A");
				if(null != insReqDto.getInsuranceUniqueID() && !insReqDto.getInsuranceUniqueID().trim().isEmpty())
				{
					boolean isUniqueIdPresent = collateralDAO.checkUniqueInsuranceId(insReqDto.getInsuranceUniqueID().trim(),securityId);
					if(isUniqueIdPresent)
					{
						errors.add("insuranceUniqueID-"+insReqDto.getInsuranceUniqueID(),new ActionMessage("insuranceUniqueID is duplicate"));
					}
					else
					{
						if(insReqDto.getInsuranceUniqueID().length() <=25)
						insReqDto.setInsuranceUniqueID(insReqDto.getInsuranceUniqueID());
						else
							errors.add("insuranceUniqueID-"+insReqDto.getInsuranceUniqueID(),new ActionMessage("error.field.length.exceeded", "25"));	
					}
				}
				else
				{
					errors.add("insuranceUniqueID",new ActionMessage("insuranceUniqueID is mandatory"));
				}
				if(null != insReqDto.getInsuranceStatus() && !insReqDto.getInsuranceStatus().trim().isEmpty())
				{
					if("DEFERRED".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
						insReqDto.setInsuranceStatus("PENDING_DEFER");
					else if("WAIVED".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
						insReqDto.setInsuranceStatus("PENDING_WAIVER");
					else if("AWAITING".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
						insReqDto.setInsuranceStatus("AWAITING");
					else if("RECEIVED".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
						insReqDto.setInsuranceStatus("PENDING_RECEIVED");
					else
						errors.add("insuranceStatus",new ActionMessage("insuranceStatus value is invalid"));
				}
				
			}
			else if("U".equalsIgnoreCase(insReqDto.getActionFlag()))
			{
				insReqDto.setActionFlag("U");
				String insuranceStatusdb;
				boolean insuranceStatusallowed = false;
				if(null != insReqDto.getInsuranceUniqueID() && !insReqDto.getInsuranceUniqueID().trim().isEmpty())
				{
					boolean isUniqueIdPresent = collateralDAO.checkUniqueInsuranceId(insReqDto.getInsuranceUniqueID().trim(),securityId);
					if(isUniqueIdPresent)
					{
						errors.add("insuranceUniqueID-"+insReqDto.getInsuranceUniqueID(),new ActionMessage("insuranceUniqueID is duplicate"));
					}
					else
					{
						if(insReqDto.getInsuranceUniqueID().length() <=25)
						insReqDto.setInsuranceUniqueID(insReqDto.getInsuranceUniqueID());
						else
							errors.add("insuranceUniqueID-"+insReqDto.getInsuranceUniqueID(),new ActionMessage("error.field.length.exceeded", "25"));	
					}
				}
				
				if(null != insReqDto.getInsurancePolicyID() && !insReqDto.getInsurancePolicyID().trim().isEmpty())
				{
					boolean isInsurancePresent = collateralDAO.checkInsurancePolicyId(insReqDto.getInsurancePolicyID(),securityId);
					if(!isInsurancePresent)
					{
						errors.add("insurancePolicyID",new ActionMessage("insurancePolicyID is invalid"));
					}
					else
					{
						insReqDto.setInsurancePolicyID(insReqDto.getInsurancePolicyID());
						insuranceStatusdb= collateralDAO.getInsuranceStatus(insReqDto.getInsurancePolicyID());
						if("DEFERRED".equalsIgnoreCase(insuranceStatusdb) && ( 
								"DEFERRED".equalsIgnoreCase(insReqDto.getInsuranceStatus()) || 
								"RECEIVED".equalsIgnoreCase(insReqDto.getInsuranceStatus()) ||
								"WAIVED".equalsIgnoreCase(insReqDto.getInsuranceStatus()) || 
								"DELETED".equalsIgnoreCase(insReqDto.getInsuranceStatus())
								))
						{
							insuranceStatusallowed = true;
						}
						else if("RECEIVED".equalsIgnoreCase(insuranceStatusdb) && (
								"RECEIVED".equalsIgnoreCase(insReqDto.getInsuranceStatus()) ||
								"DELETED".equalsIgnoreCase(insReqDto.getInsuranceStatus()) 
								))
						{
							insuranceStatusallowed =true;
						}
						else if("AWAITING".equalsIgnoreCase(insuranceStatusdb))
						{
							insuranceStatusallowed =true;
						}
						else if("WAIVED".equalsIgnoreCase(insuranceStatusdb))
						{
							insuranceStatusallowed =false;
						}
					}
					
					
				}
				else
				{
					errors.add("insurancePolicyID",new ActionMessage("insurancePolicyID is mandatory for update Insurance"));
				}
				if(insuranceStatusallowed)
				{
				if(null != insReqDto.getInsuranceStatus() && !insReqDto.getInsuranceStatus().trim().isEmpty())
				{
					if("DEFERRED".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
						insReqDto.setInsuranceStatus("PENDING_DEFER");
					else if("WAIVED".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
						insReqDto.setInsuranceStatus("PENDING_WAIVER");
					else if("AWAITING".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
						insReqDto.setInsuranceStatus("AWAITING");
					else if("RECEIVED".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
						insReqDto.setInsuranceStatus("UPDATE_RECEIVED");
					else if("DELETED".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
						insReqDto.setInsuranceStatus("DELETED");
					else
						errors.add("insuranceStatus",new ActionMessage("insuranceStatus value is invalid"));
				}
				else
				{
					errors.add("insuranceStatus",new ActionMessage("Insurance Status is mandatory"));
				}
			}
				else
				{
					errors.add("insuranceStatus-"+insReqDto.getInsuranceStatus(),new ActionMessage("insuranceStatus provided for this insurancePolicyID is incorrect"));
				}
				
			}
			else
			{
				errors.add("actionFlag",new ActionMessage("error.invalid"));
			}
		}
		else
		{
			errors.add("actionFlag",new ActionMessage("error.string.mandatory"));
		}
		
		if(null != insReqDto.getInsuranceStatus() && !insReqDto.getInsuranceStatus().trim().isEmpty())
		{
			if("UPDATE_RECEIVED".equalsIgnoreCase(insReqDto.getInsuranceStatus()) || "PENDING_RECEIVED".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
			{
				if(null != insReqDto.getPolicyNo() && !insReqDto.getPolicyNo().trim().isEmpty())
				{
					if(insReqDto.getPolicyNo().length() <= 30)
					insReqDto.setPolicyNo(insReqDto.getPolicyNo());
					else
						errors.add("policyNo",new ActionMessage("error.field.length.exceeded", "30"));
				}
				
				if(null != insReqDto.getRemark1() && !insReqDto.getRemark1().trim().isEmpty())
				{
					if(insReqDto.getRemark1().length() <= 50)
					insReqDto.setRemark1(insReqDto.getRemark1());
					else
						errors.add("remark1",new ActionMessage("error.field.length.exceeded", "50"));
				}
				
				if(null != insReqDto.getCoverNoteNumber() && !insReqDto.getCoverNoteNumber().trim().isEmpty())
				{
					if(insReqDto.getCoverNoteNumber().length() <= 20)
					insReqDto.setCoverNoteNumber(insReqDto.getCoverNoteNumber());
					else
						errors.add("coverNoteNumber",new ActionMessage("error.field.length.exceeded", "20"));
				}
				
				
				if(null != insReqDto.getRemark2() && !insReqDto.getRemark2().trim().isEmpty())
				{
					if(insReqDto.getRemark2().length() <= 50)
					insReqDto.setRemark2(insReqDto.getRemark2());
					else
						errors.add("remark2",new ActionMessage("error.field.length.exceeded", "50"));
				}
				
				if(null != insReqDto.getInsuranceCompanyName() && !insReqDto.getInsuranceCompanyName().trim().isEmpty())
				{


					Object obj = masterObj.getObjectforMasterRelatedCode("actualInsuranceCoverage", insReqDto.getInsuranceCompanyName(),"insuranceCompanyName",errors);
					if(!(obj instanceof ActionErrors)){
						insReqDto.setInsuranceCompanyName(((IInsuranceCoverage)obj).getId()+"");
					}
					 else
					 {
						 errors.add("insuranceCompanyName",new ActionMessage("error.invalid.field.value")); 
					 }
				}else{
					errors.add("insuranceCompanyName",new ActionMessage("insuranceCompanyName is mandatory for RECIEVED insurance"));
				}
				if(null != insReqDto.getTypeOfPerils1() && !insReqDto.getTypeOfPerils1().trim().isEmpty())
				{

					 try {
						 Object obj = masterObj.getObjectByEntityName("entryCode", insReqDto.getTypeOfPerils1().trim(),"typeOfPerils1",errors,"INSURANCE_COMPANY_CATEGORY");
						 if(!(obj instanceof ActionErrors)){
							 insReqDto.setTypeOfPerils1(((ICommonCodeEntry)obj).getEntryCode());
							}
						 else
						 {
							 errors.add("typeOfPerils1",new ActionMessage("error.invalid.field.value")); 
						 }
					 }
					 catch(Exception e){
							DefaultLogger.error(this, "typeOfPerils1 - "+e.getMessage());
							errors.add("typeOfPerils1",new ActionMessage("error.invalid.typeOfPerils1"));
						}
				}
			/*	else
				{
					errors.add("typeOfPerils1",new ActionMessage("typeOfPerils1 is mandatory for recieved insurance"));
				}*/
				
				if(null != insReqDto.getCurrencyCode() && !insReqDto.getCurrencyCode().trim().isEmpty())
				{

					Object obj = masterObj.getObjectforMasterRelatedCode("actualForexFeedEntry", insReqDto.getCurrencyCode().trim(),"currencyCode",errors);
					if(!(obj instanceof ActionErrors)){
						insReqDto.setCurrencyCode(((IForexFeedEntry)obj).getCurrencyIsoCode());
					}
				}
				if(null != insReqDto.getInsurableAmount() && !insReqDto.getInsurableAmount().trim().isEmpty())
				{
					if(ASSTValidator.isNumeric(insReqDto.getInsurableAmount()))
					insReqDto.setInsurableAmount(insReqDto.getInsurableAmount());
					else
						errors.add("insurableAmount-"+insReqDto.getInsurableAmount(),new ActionMessage("insurableAmount must be a number"));
				}
				/*else
				{
					errors.add("insurableAmount",new ActionMessage("insurableAmount is mandatory for recieved insurance"));
				}*/
				if(null != insReqDto.getInsuredAmount() && !insReqDto.getInsuredAmount().trim().isEmpty())
				{
					if(ASSTValidator.isNumeric(insReqDto.getInsuredAmount()))
					insReqDto.setInsuredAmount(insReqDto.getInsuredAmount());
					else
						errors.add("insuredAmount-"+insReqDto.getInsuredAmount(),new ActionMessage("insuredAmount must be a number"));
				}
				/*else
				{
					errors.add("insuredAmount",new ActionMessage("insuredAmount is mandatory for recieved insurance"));
				}*/
				if(null != insReqDto.getReceivedDate() && !insReqDto.getReceivedDate().trim().isEmpty())
				{
					if(isValidDate(insReqDto.getReceivedDate()))
						insReqDto.setReceivedDate(insReqDto.getReceivedDate());
					 else
						 errors.add("receivedDate",new ActionMessage("error.date.format"));
				}
				/*else
				{
					errors.add("receivedDate",new ActionMessage("receivedDate is mandatory for recieved insurance"));
				}*/
				if(null != insReqDto.getEffectiveDate() && !insReqDto.getEffectiveDate().trim().isEmpty())
				{
					if(isValidDate(insReqDto.getEffectiveDate()))
						insReqDto.setEffectiveDate(insReqDto.getEffectiveDate());
					 else
						 errors.add("effectiveDate",new ActionMessage("error.date.format"));
				}
				/*else
				{
					errors.add("effectiveDate",new ActionMessage("effectiveDate is mandatory for recieved insurance"));
				}*/
				if(null != insReqDto.getExpiryDate() && !insReqDto.getExpiryDate().trim().isEmpty())
				{
					if(isValidDate(insReqDto.getExpiryDate()))
						insReqDto.setExpiryDate(insReqDto.getExpiryDate());
					 else
						 errors.add("expiryDate",new ActionMessage("error.date.format"));
				}
				/*else
				{
					errors.add("expiryDate",new ActionMessage("expiryDate is mandatory for recieved insurance"));
				}*/
				if(null != insReqDto.getInsurancePremium() && !insReqDto.getInsurancePremium().trim().isEmpty())
				{
					if(ASSTValidator.isNumeric(insReqDto.getInsurancePremium()))
					insReqDto.setInsurancePremium(insReqDto.getInsurancePremium());
					else
						errors.add("insurancePremium-"+insReqDto.getInsurancePremium(),new ActionMessage("insurancePremium must be a number"));
				}
				/*else
				{
					errors.add("insurancePremium",new ActionMessage("insurancePremium is mandatory for recieved insurance"));
				}*/
				if(null != insReqDto.getNonScheme_Scheme() && !insReqDto.getNonScheme_Scheme().trim().isEmpty())
				{
					if("Yes".equalsIgnoreCase(insReqDto.getNonScheme_Scheme()) || "No".equalsIgnoreCase(insReqDto.getNonScheme_Scheme()))
					insReqDto.setNonScheme_Scheme(insReqDto.getNonScheme_Scheme());
					else
						errors.add("nonScheme_Scheme",new ActionMessage("nonScheme_Scheme value is invalid"));
				}
				
				
				
			}
			if("PENDING_DEFER".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
			{
				if(null != insReqDto.getOriginalTargetDate() && !insReqDto.getOriginalTargetDate().trim().isEmpty())
				{
					if(isValidDate(insReqDto.getOriginalTargetDate()))
						insReqDto.setOriginalTargetDate(insReqDto.getOriginalTargetDate());
					 else
						 errors.add("originalTargetDate",new ActionMessage("error.date.format"));
					//insReqDto.setOriginalTargetDate(insReqDto.getOriginalTargetDate());
				}
				/*else
				{
					errors.add("originalTargetDate",new ActionMessage("originalTargetDate is mandatory for deferred insurance"));
				}
				*/
				if(null != insReqDto.getDateDeferred() && !insReqDto.getDateDeferred().trim().isEmpty())
				{
					if(isValidDate(insReqDto.getDateDeferred()))
						insReqDto.setDateDeferred(insReqDto.getDateDeferred());
					 else
						 errors.add("dateDeferred",new ActionMessage("error.date.format"));
					//insReqDto.setDateDeferred(insReqDto.getDateDeferred());
				}
				/*else
				{
					errors.add("dateDeferred",new ActionMessage("dateDeferred is mandatory for deferred insurance"));
				}
				*/
				if(null != insReqDto.getNextDueDate() && !insReqDto.getNextDueDate().trim().isEmpty())
				{
					if(isValidDate(insReqDto.getNextDueDate()))
						insReqDto.setNextDueDate(insReqDto.getNextDueDate());
					 else
						 errors.add("nextDueDate",new ActionMessage("error.date.format"));
					//insReqDto.setNextDueDate(insReqDto.getNextDueDate());
				}
				/*else
				{
					errors.add("nextDueDate",new ActionMessage("nextDueDate is mandatory for deferred insurance"));
				}*/
				
				if(null != insReqDto.getCreditApprover() && !insReqDto.getCreditApprover().trim().isEmpty())
				{
					Object obj = masterObj.getObjectforMasterRelatedCodeWaiver("actualCreditApproval", insReqDto.getCreditApprover(),"creditApprover",errors);
					if(!(obj instanceof ActionErrors)){
						insReqDto.setCreditApprover(((ICreditApproval)obj).getApprovalCode());
					}
					else
					 {
						 errors.add("creditApprover",new ActionMessage("error.invalid.field.value")); 
					 }
				}
				/*else
				{
					errors.add("creditApprover",new ActionMessage("creditApprover is mandatory for Waived insurance"));
				}*/
			}
			if("AWAITING".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
			{
				if(null != insReqDto.getOriginalTargetDate() && !insReqDto.getOriginalTargetDate().trim().isEmpty())
				{
					if(isValidDate(insReqDto.getOriginalTargetDate()))
						insReqDto.setOriginalTargetDate(insReqDto.getOriginalTargetDate());
					 else
						 errors.add("originalTargetDate",new ActionMessage("error.date.format"));
				}
				/*else
				{
					errors.add("originalTargetDate",new ActionMessage("originalTargetDate is mandatory for Pending/Awaiting insurance"));
				}*/
			}
			if("PENDING_WAIVER".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
			{
				if(null != insReqDto.getCreditApprover() && !insReqDto.getCreditApprover().trim().isEmpty())
				{
					Object obj = masterObj.getObjectforMasterRelatedCode("actualCreditApproval", insReqDto.getCreditApprover(),"creditApprover",errors);
					if(!(obj instanceof ActionErrors)){
						insReqDto.setCreditApprover(((ICreditApproval)obj).getApprovalCode());
					}
					else
					 {
						 errors.add("creditApprover",new ActionMessage("error.invalid.field.value")); 
					 }
				}
				/*else
				{
					errors.add("creditApprover",new ActionMessage("creditApprover is mandatory for Waived insurance"));
				}*/
				if(null != insReqDto.getWaivedDate() && !insReqDto.getWaivedDate().trim().isEmpty())
				{
					if(isValidDate(insReqDto.getWaivedDate()))
						insReqDto.setWaivedDate(insReqDto.getWaivedDate());
					 else
						 errors.add("waivedDate",new ActionMessage("error.date.format"));
					//insReqDto.setWaivedDate(insReqDto.getWaivedDate());
				}
				/*else
				{
					errors.add("waivedDate",new ActionMessage("waivedDate is mandatory for Waived insurance"));
				}*/
			}
		}
		else
		{
			errors.add("insuranceStatus",new ActionMessage("error.string.mandatory"));
		}
		
		
		
		insReqDto.setErrors(errors);
		return insReqDto;
		
	}
	
	public InsurancePolicyForm getInsuranceFormFromReq(InsurancePolicyRestRequestDTO insReqDto) {
		InsurancePolicyForm form = new InsurancePolicyForm();
		Locale locale = Locale.getDefault();
		//event
		if("UPDATE_RECEIVED".equalsIgnoreCase(insReqDto.getInsuranceStatus()) || "PENDING_RECEIVED".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
		{
			form.setEvent("maker_submit_insurance_received");

    		if(null != insReqDto.getPolicyNo() && !insReqDto.getPolicyNo().trim().isEmpty())
	    	{
    			form.setInsPolicyNum(insReqDto.getPolicyNo());
	    	}
	    	if(null != insReqDto.getCoverNoteNumber() &&!insReqDto.getCoverNoteNumber().trim().isEmpty())
	    	{
	    		form.setCoverNoteNumber(insReqDto.getCoverNoteNumber());
	    	}
	    	if(null != insReqDto.getInsuranceCompanyName() && !insReqDto.getInsuranceCompanyName().trim().isEmpty())
	    	{
	    		form.setInsurerName(insReqDto.getInsuranceCompanyName());
	    	}
	    	if(null != insReqDto.getTypeOfPerils1() && !insReqDto.getTypeOfPerils1().trim().isEmpty())
	    	{
	    		form.setTypeOfPerils1(insReqDto.getTypeOfPerils1());
	    	}
	    	if(null != insReqDto.getInsurableAmount() && !insReqDto.getInsurableAmount().trim().isEmpty())
	    	{
	    		form.setInsurableAmt(insReqDto.getInsurableAmount());
	    	}
	    	if(null != insReqDto.getInsuredAmount() && !insReqDto.getInsuredAmount().trim().isEmpty())
	    	{
	    		form.setInsuredAmt(insReqDto.getInsuredAmount());
	    	}
	    	if(null != insReqDto.getReceivedDate() && !insReqDto.getReceivedDate().trim().isEmpty())
	    	{
	    		form.setReceivedDate(insReqDto.getReceivedDate());
	    	}
	    	if(null != insReqDto.getEffectiveDate() && !insReqDto.getEffectiveDate().trim().isEmpty())
	    	{
	    		form.setEffectiveDateIns(insReqDto.getEffectiveDate());
	    	}
	    	if(null != insReqDto.getExpiryDate() && !insReqDto.getExpiryDate().trim().isEmpty())
	    	{
	    		form.setExpiryDateIns(insReqDto.getExpiryDate());
	    	}
	    	if(null != insReqDto.getInsurancePremium() && !insReqDto.getInsurancePremium().trim().isEmpty() )
	    	{
	    	form.setInsurancePremium(insReqDto.getInsurancePremium());
	    	}
	    	if(null != insReqDto.getNonScheme_Scheme() && !insReqDto.getNonScheme_Scheme().trim().isEmpty())
	    	{
	    		form.setNonSchemeScheme(insReqDto.getNonScheme_Scheme());
	    	}
	    	if(null != insReqDto.getAddress() && !insReqDto.getAddress().trim().isEmpty())
	    	{
				form.setInsuredAddress(insReqDto.getAddress());
	    	}
	    	if(null != insReqDto.getRemark1() && !insReqDto.getRemark1().trim().isEmpty())
	    	{
	    		form.setRemark1(insReqDto.getRemark1());
	    	}
	    	if(null != insReqDto.getInsuredAgainst() && !insReqDto.getInsuredAgainst().trim().isEmpty())
	    	{
	    		form.setInsuredAgainst(insReqDto.getInsuredAgainst());
	    	}
	    	if(null != insReqDto.getInsuredAgainst() && !insReqDto.getInsuredAgainst().trim().isEmpty())
	    	{
	    		form.setInsuredAgainst(insReqDto.getInsuredAgainst());
	    	}
	    	if(null != insReqDto.getCurrencyCode() && !insReqDto.getCurrencyCode().trim().isEmpty())
	    	{
	    		form.setInsPolicyCurrency(insReqDto.getCurrencyCode());
	    	}
			
		}
		else if("PENDING_DEFER".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
		{
			form.setEvent("maker_submit_insurance_deferred");

    		if(null != insReqDto.getOriginalTargetDate() && !insReqDto.getOriginalTargetDate().trim().isEmpty())
	    	{
    			form.setOriginalTargetDate(insReqDto.getOriginalTargetDate());
	    	}
    		if(null != insReqDto.getDateDeferred() && !insReqDto.getDateDeferred().trim().isEmpty())
	    	{
    			form.setDateDeferred(insReqDto.getDateDeferred());
	    	}
	    	if(null != insReqDto.getNextDueDate() && !insReqDto.getNextDueDate().trim().isEmpty())
	    	{
	    		form.setNextDueDate(insReqDto.getNextDueDate());
	    	}
	    	if(null != insReqDto.getCreditApprover() && !insReqDto.getCreditApprover().trim().isEmpty())
	    	{
	    		form.setCreditApprover(insReqDto.getCreditApprover());
	    	}
    		
		
		}	
		else if("AWAITING".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
		{
			form.setEvent("maker_submit_insurance_pending");
			if(null != insReqDto.getOriginalTargetDate() && !insReqDto.getOriginalTargetDate().trim().isEmpty())
	    	{
	    		form.setOriginalTargetDate(insReqDto.getOriginalTargetDate());
	    	}
		}
		else if("PENDING_WAIVER".equalsIgnoreCase(insReqDto.getInsuranceStatus()))
		{
			form.setEvent("maker_submit_insurance_waived");

    		if(null != insReqDto.getCreditApprover() && !insReqDto.getCreditApprover().trim().isEmpty())
	    	{
    			form.setCreditApprover(insReqDto.getCreditApprover());
	    	}
    		if(null != insReqDto.getWaivedDate() && !insReqDto.getWaivedDate().trim().isEmpty())
	    	{
    			form.setWaivedDate(insReqDto.getWaivedDate());
	    	}
		}
		else
		{
		form.setEvent("");
		}
		return form;
		
	}
	
	public Object getFormFromDTORestForCorporateGuarantee(CollateralRestRequestDTO requestDTO) {

		GteCorp3rdForm aForm = new GteCorp3rdForm();

		if (requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList() != null
				&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().isEmpty()) {

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRamId()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRamId().trim()
							.isEmpty()) {
				aForm.setRamId(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRamId());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuaRefNo()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuaRefNo().trim()
							.isEmpty()) {
				aForm.setGuaRefNo(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuaRefNo());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAmtGuarantee()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAmtGuarantee()
							.trim().isEmpty()) {
				aForm.setAmtGuarantee(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAmtGuarantee());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getDateGuarantee()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getDateGuarantee()
							.trim().isEmpty()) {
				aForm.setDateGuarantee(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getDateGuarantee());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimPeriod()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimPeriod()
							.trim().isEmpty()) {
				aForm.setClaimPeriod(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimPeriod());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimPeriodUnit()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimPeriodUnit()
							.trim().isEmpty()) {
				aForm.setClaimPeriodUnit(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
						.getClaimPeriodUnit());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimDate()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimDate()
							.trim().isEmpty()) {
				aForm.setClaimDate(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getClaimDate());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getTelephoneNumber()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getTelephoneNumber()
							.trim().isEmpty()) {
				aForm.setTelephoneNumber(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
						.getTelephoneNumber());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
					.getGuarantersDunsNumber()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
							.getGuarantersDunsNumber().trim().isEmpty()) {
				aForm.setGuarantersDunsNumber(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList()
						.get(0).getGuarantersDunsNumber());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuarantersPan()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuarantersPan()
							.trim().isEmpty()) {
				aForm.setGuarantersPam(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuarantersPan());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuarantersName()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuarantersName()
							.trim().isEmpty()) {
				aForm.setGuarantersName(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
						.getGuarantersName());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
					.getGuarantersNamePrefix()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
							.getGuarantersNamePrefix().trim().isEmpty()) {
				aForm.setGuarantersNamePrefix(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList()
						.get(0).getGuarantersNamePrefix());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
					.getGuarantersFullName()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
							.getGuarantersFullName().trim().isEmpty()) {
				aForm.setGuarantersFullName(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
						.getGuarantersFullName());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAddressLine1()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAddressLine1()
							.trim().isEmpty()) {
				aForm.setAddressLine1(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAddressLine1());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAddressLine2()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAddressLine2()
							.trim().isEmpty()) {
				aForm.setAddressLine2(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAddressLine2());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAddressLine3()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAddressLine3()
							.trim().isEmpty()) {
				aForm.setAddressLine3(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAddressLine3());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCity()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCity().trim()
							.isEmpty()) {
				aForm.setCity(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCity());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getState()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getState().trim()
							.isEmpty()) {
				aForm.setState(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getState());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRegion()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRegion().trim()
							.isEmpty()) {
				aForm.setRegion(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRegion());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCountry()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCountry().trim()
							.isEmpty()) {
				aForm.setCountry(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getCountry());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getDistrict()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getDistrict().trim()
							.isEmpty()) {
				aForm.setDistrict(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getDistrict());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getPinCode()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getPinCode().trim()
							.isEmpty()) {
				aForm.setPinCode(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getPinCode());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
					.getTelephoneAreaCode()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
							.getTelephoneAreaCode().trim().isEmpty()) {
				aForm.setTelephoneAreaCode(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
						.getTelephoneAreaCode());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRating()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRating().trim()
							.isEmpty()) {
				aForm.setRating(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRating());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRecourse()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRecourse().trim()
							.isEmpty()) {
				aForm.setRecourse(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getRecourse());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
					.getDiscriptionOfAssets()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
							.getDiscriptionOfAssets().trim().isEmpty()) {
				aForm.setDiscriptionOfAssets(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
						.getDiscriptionOfAssets());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAssetStatement()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAssetStatement()
							.trim().isEmpty()) {
				aForm.setAssetStatement(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
						.getAssetStatement());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAssetStatement()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAssetStatement()
							.trim().isEmpty()) {
				aForm.setAssetStatement(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
						.getAssetStatement());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuarantorType()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuarantorType()
							.trim().isEmpty()) {
				aForm.setGuarantorType(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuarantorType());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuarantorNature()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getGuarantorNature()
							.trim().isEmpty()) {
				aForm.setGuarantorNature(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
						.getGuarantorNature());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getChargeType()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getChargeType()
							.trim().isEmpty()) {
				aForm.setChargeType(
						requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getChargeType());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
					.getCollateralMaturityDate()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
							.getCollateralMaturityDate().trim().isEmpty()) {
				aForm.setCollateralMaturityDate(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList()
						.get(0).getCollateralMaturityDate());
			}
			
			if(null != requestDTO.getBodyDetails().get(0).getCmv() && !requestDTO.getBodyDetails().get(0).getCmv().trim().isEmpty())
		    {
				aForm.setAmountCMV(requestDTO.getBodyDetails().get(0).getCmv());
		    }
		}

		return aForm;
	}
	
	public Object getFormFromDTORestForIndividualGuarantee(CollateralRestRequestDTO requestDTO) {


		GteIndivForm aForm = new GteIndivForm();

		if (requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList() != null
				&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().isEmpty()) {

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRamId()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRamId().trim()
							.isEmpty()) {
				aForm.setRamId(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRamId());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuaRefNo()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuaRefNo().trim()
							.isEmpty()) {
				aForm.setGuaRefNo(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuaRefNo());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getAmtGuarantee()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getAmtGuarantee()
							.trim().isEmpty()) {
				aForm.setAmtGuarantee(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getAmtGuarantee());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getDateGuarantee()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getDateGuarantee()
							.trim().isEmpty()) {
				aForm.setDateGuarantee(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getDateGuarantee());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimPeriod()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimPeriod()
							.trim().isEmpty()) {
				aForm.setClaimPeriod(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimPeriod());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimPeriodUnit()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimPeriodUnit()
							.trim().isEmpty()) {
				aForm.setClaimPeriodUnit(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
						.getClaimPeriodUnit());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimDate()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimDate()
							.trim().isEmpty()) {
				aForm.setClaimDate(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getClaimDate());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getTelephoneNumber()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getTelephoneNumber()
							.trim().isEmpty()) {
				aForm.setTelephoneNumber(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
						.getTelephoneNumber());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
					.getGuarantersDunsNumber()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
							.getGuarantersDunsNumber().trim().isEmpty()) {
				aForm.setGuarantersDunsNumber(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList()
						.get(0).getGuarantersDunsNumber());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuarantersPan()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuarantersPan()
							.trim().isEmpty()) {
				aForm.setGuarantersPam(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuarantersPan());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuarantersName()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuarantersName()
							.trim().isEmpty()) {
				aForm.setGuarantersName(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
						.getGuarantersName());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
					.getGuarantersNamePrefix()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
							.getGuarantersNamePrefix().trim().isEmpty()) {
				aForm.setGuarantersNamePrefix(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList()
						.get(0).getGuarantersNamePrefix());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
					.getGuarantersFullName()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
							.getGuarantersFullName().trim().isEmpty()) {
				aForm.setGuarantersFullName(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
						.getGuarantersFullName());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getAddressLine1()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getAddressLine1()
							.trim().isEmpty()) {
				aForm.setAddressLine1(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getAddressLine1());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getAddressLine2()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getAddressLine2()
							.trim().isEmpty()) {
				aForm.setAddressLine2(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getAddressLine2());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getAddressLine3()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getAddressLine3()
							.trim().isEmpty()) {
				aForm.setAddressLine3(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getAddressLine3());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCity()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCity().trim()
							.isEmpty()) {
				aForm.setCity(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCity());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getState()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getState().trim()
							.isEmpty()) {
				aForm.setState(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getState());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRegion()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRegion().trim()
							.isEmpty()) {
				aForm.setRegion(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRegion());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCountry()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCountry().trim()
							.isEmpty()) {
				aForm.setCountry(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getCountry());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getDistrict()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getDistrict().trim()
							.isEmpty()) {
				aForm.setDistrict(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getDistrict());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getPinCode()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getPinCode().trim()
							.isEmpty()) {
				aForm.setPinCode(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getPinCode());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
					.getTelephoneAreaCode()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
							.getTelephoneAreaCode().trim().isEmpty()) {
				aForm.setTelephoneAreaCode(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
						.getTelephoneAreaCode());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRating()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRating().trim()
							.isEmpty()) {
				aForm.setRating(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRating());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRecourse()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRecourse().trim()
							.isEmpty()) {
				aForm.setRecourse(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getRecourse());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
					.getDiscriptionOfAssets()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
							.getDiscriptionOfAssets().trim().isEmpty()) {
				aForm.setDiscriptionOfAssets(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
						.getDiscriptionOfAssets());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getAssetStatement()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getAssetStatement()
							.trim().isEmpty()) {
				aForm.setAssetStatement(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
						.getAssetStatement());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getAssetStatement()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getAssetStatement()
							.trim().isEmpty()) {
				aForm.setAssetStatement(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
						.getAssetStatement());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuarantorType()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuarantorType()
							.trim().isEmpty()) {
				aForm.setGuarantorType(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuarantorType());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuarantorNature()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getGuarantorNature()
							.trim().isEmpty()) {
				aForm.setGuarantorNature(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
						.getGuarantorNature());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getChargeType()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getChargeType()
							.trim().isEmpty()) {
				aForm.setChargeType(
						requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getChargeType());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
					.getCollateralMaturityDate()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0)
							.getCollateralMaturityDate().trim().isEmpty()) {
				aForm.setCollateralMaturityDate(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList()
						.get(0).getCollateralMaturityDate());
			}
			
			if(null != requestDTO.getBodyDetails().get(0).getCmv() && !requestDTO.getBodyDetails().get(0).getCmv().trim().isEmpty())
		    {
				aForm.setAmountCMV(requestDTO.getBodyDetails().get(0).getCmv());
		    }
		}

		return aForm;
	
	}
	
	public Object getFormFromDTORestForGovernmentGuarantee(CollateralRestRequestDTO requestDTO) {


		GteGovtForm aForm = new GteGovtForm();

		if (requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList() != null
				&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().isEmpty()) {

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRamId()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRamId().trim()
							.isEmpty()) {
				aForm.setRamId(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRamId());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuaRefNo()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuaRefNo().trim()
							.isEmpty()) {
				aForm.setGuaRefNo(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuaRefNo());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAmtGuarantee()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAmtGuarantee()
							.trim().isEmpty()) {
				aForm.setAmtGuarantee(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAmtGuarantee());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getDateGuarantee()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getDateGuarantee()
							.trim().isEmpty()) {
				aForm.setDateGuarantee(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getDateGuarantee());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimPeriod()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimPeriod()
							.trim().isEmpty()) {
				aForm.setClaimPeriod(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimPeriod());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimPeriodUnit()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimPeriodUnit()
							.trim().isEmpty()) {
				aForm.setClaimPeriodUnit(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
						.getClaimPeriodUnit());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimDate()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimDate()
							.trim().isEmpty()) {
				aForm.setClaimDate(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getClaimDate());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getTelephoneNumber()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getTelephoneNumber()
							.trim().isEmpty()) {
				aForm.setTelephoneNumber(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
						.getTelephoneNumber());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
					.getGuarantersDunsNumber()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
							.getGuarantersDunsNumber().trim().isEmpty()) {
				aForm.setGuarantersDunsNumber(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList()
						.get(0).getGuarantersDunsNumber());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuarantersPan()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuarantersPan()
							.trim().isEmpty()) {
				aForm.setGuarantersPam(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuarantersPan());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuarantersName()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuarantersName()
							.trim().isEmpty()) {
				aForm.setGuarantersName(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
						.getGuarantersName());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
					.getGuarantersNamePrefix()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
							.getGuarantersNamePrefix().trim().isEmpty()) {
				aForm.setGuarantersNamePrefix(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList()
						.get(0).getGuarantersNamePrefix());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
					.getGuarantersFullName()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
							.getGuarantersFullName().trim().isEmpty()) {
				aForm.setGuarantersFullName(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
						.getGuarantersFullName());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAddressLine1()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAddressLine1()
							.trim().isEmpty()) {
				aForm.setAddressLine1(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAddressLine1());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAddressLine2()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAddressLine2()
							.trim().isEmpty()) {
				aForm.setAddressLine2(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAddressLine2());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAddressLine3()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAddressLine3()
							.trim().isEmpty()) {
				aForm.setAddressLine3(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAddressLine3());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCity()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCity().trim()
							.isEmpty()) {
				aForm.setCity(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCity());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getState()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getState().trim()
							.isEmpty()) {
				aForm.setState(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getState());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRegion()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRegion().trim()
							.isEmpty()) {
				aForm.setRegion(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRegion());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCountry()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCountry().trim()
							.isEmpty()) {
				aForm.setCountry(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getCountry());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getDistrict()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getDistrict().trim()
							.isEmpty()) {
				aForm.setDistrict(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getDistrict());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getPinCode()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getPinCode().trim()
							.isEmpty()) {
				aForm.setPinCode(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getPinCode());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
					.getTelephoneAreaCode()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
							.getTelephoneAreaCode().trim().isEmpty()) {
				aForm.setTelephoneAreaCode(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
						.getTelephoneAreaCode());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRating()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRating().trim()
							.isEmpty()) {
				aForm.setRating(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRating());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRecourse()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRecourse().trim()
							.isEmpty()) {
				aForm.setRecourse(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getRecourse());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
					.getDiscriptionOfAssets()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
							.getDiscriptionOfAssets().trim().isEmpty()) {
				aForm.setDiscriptionOfAssets(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
						.getDiscriptionOfAssets());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAssetStatement()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAssetStatement()
							.trim().isEmpty()) {
				aForm.setAssetStatement(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
						.getAssetStatement());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAssetStatement()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getAssetStatement()
							.trim().isEmpty()) {
				aForm.setAssetStatement(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
						.getAssetStatement());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuarantorType()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuarantorType()
							.trim().isEmpty()) {
				aForm.setGuarantorType(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuarantorType());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuarantorNature()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getGuarantorNature()
							.trim().isEmpty()) {
				aForm.setGuarantorNature(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
						.getGuarantorNature());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getChargeType()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getChargeType()
							.trim().isEmpty()) {
				aForm.setChargeType(
						requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getChargeType());
			}

			if (null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
					.getCollateralMaturityDate()
					&& !requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0)
							.getCollateralMaturityDate().trim().isEmpty()) {
				aForm.setCollateralMaturityDate(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList()
						.get(0).getCollateralMaturityDate());
			}
			
			if(null != requestDTO.getBodyDetails().get(0).getCmv() && !requestDTO.getBodyDetails().get(0).getCmv().trim().isEmpty())
		    {
				aForm.setAmountCMV(requestDTO.getBodyDetails().get(0).getCmv());
		    }
		}

		return aForm;
	}
	
	private ICollateral formToOBUpdateLimitCharge(ICollateral iCollateral, CollateralRestRequestDTO requestDTO, Locale locale, String ColSubTypeId){

		ILimitCharge[] limitCharge = iCollateral.getLimitCharges();
		boolean updated = false;
		if (!SecuritySubTypeUtil.canCollateralMaintainMultipleCharge(iCollateral)) {
			try {
				ILimitCharge objLimit = null;
				if (limitCharge != null) {
					if (limitCharge.length > 0) {
						objLimit = limitCharge[0];
						if (objLimit == null) {
							objLimit = new OBLimitCharge();
							objLimit.setChargeCcyCode(iCollateral.getCurrencyCode());
						}
					}
					else {
						limitCharge = new OBLimitCharge[1];
						objLimit = new OBLimitCharge();
						objLimit.setChargeCcyCode(iCollateral.getCurrencyCode());
					}
				}
				else {
					limitCharge = new OBLimitCharge[1];
					objLimit = new OBLimitCharge();
					objLimit.setChargeCcyCode(iCollateral.getCurrencyCode());
				}
	
				/*if (!requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getNatureOfCharge().equals(objLimit.getNatureOfCharge())) {
					updated = true;
					objLimit.setNatureOfCharge(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getNatureOfCharge());
				}*/
				if (objLimit.getChargeCcyCode() == null) {
					objLimit.setChargeCcyCode(iCollateral.getCurrencyCode());
				}
	
				/*if (AbstractCommonMapper.isEmptyOrNull(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAmtCharge()) && (objLimit.getChargeAmount() != null)
						&& (objLimit.getChargeAmount().getAmount() >= 0)) {
					updated = true;
					objLimit.setChargeAmount(CurrencyManager.convertToAmount(locale, objLimit.getChargeCcyCode(), "0"));
				}
				else if (!AbstractCommonMapper.isEmptyOrNull(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getAmtCharge())) {
					updated = true;
					objLimit.setChargeAmount(CurrencyManager.convertToAmount(locale, objLimit.getChargeCcyCode(), requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0)
							.getAmtCharge()));
				}*/
	
				/*if (!AbstractCommonMapper.isEmptyOrNull(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getLegalChargeDate())) {
					updated = true;
					stageDate = compareDate(locale, objLimit.getLegalChargeDate(), requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getLegalChargeDate());
					objLimit.setLegalChargeDate(stageDate);
				}
				else {
					objLimit.setLegalChargeDate(null);
				}*/
	
				if("GT405".equalsIgnoreCase(ColSubTypeId)) {
					if (!AbstractCommonMapper.isEmptyOrNull(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getChargeType())) {
						updated = true;
						objLimit.setChargeType(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getChargeType());
					}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getChargeType() && requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getChargeType().trim().isEmpty()){
						objLimit.setChargeType("");
					}
				}else if("GT408".equalsIgnoreCase(ColSubTypeId)) {
					if (!AbstractCommonMapper.isEmptyOrNull(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getChargeType())) {
						updated = true;
						objLimit.setChargeType(requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getChargeType());
					}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getChargeType() && requestDTO.getBodyDetails().get(0).getGuaranteeIndRestRequestDTOList().get(0).getChargeType().trim().isEmpty()){
						objLimit.setChargeType("");
					}
				}else if("GT406".equalsIgnoreCase(ColSubTypeId)) {
					if (!AbstractCommonMapper.isEmptyOrNull(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getChargeType())) {
						updated = true;
						objLimit.setChargeType(requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getChargeType());
					}else if(null != requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getChargeType() && requestDTO.getBodyDetails().get(0).getGuaranteeGovtRestRequestDTOList().get(0).getChargeType().trim().isEmpty()){
						objLimit.setChargeType("");
					}
				}
	
				/*if (!AbstractCommonMapper.isEmptyOrNull(requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getConfirmChargeDate())) {
					updated = true;
					stageDate = compareDate(locale, objLimit.getChargeConfirmationDate(), requestDTO.getBodyDetails().get(0).getGuaranteeCorpRestRequestDTOList().get(0).getConfirmChargeDate());
					objLimit.setChargeConfirmationDate(stageDate);
				}
				else {
					objLimit.setChargeConfirmationDate(null);
				}*/
	
				limitCharge[0] = objLimit;
	
				if (updated) {
					if (iCollateral.getCurrentCollateralLimits() != null
							&& iCollateral.getCurrentCollateralLimits().length != 0) {
						limitCharge[0].setLimitMaps(iCollateral.getCurrentCollateralLimits());
					}
	
					iCollateral.setLimitCharges(limitCharge);
				}
			} catch(Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "inside formToOBUpdateLimitCharge()"+e.getMessage());
			}
		}
		return iCollateral;
		
	}
	
	
	
	
	public Object getPropFormUsingReq(CollateralRestRequestDTO requestDTO,String deferralIds) {
		PropertyRestForm propRestForm= new PropertyRestForm();
		List<PropertyRestRequestDTO> propertyRestRequestList = requestDTO.getBodyDetails().get(0).getPropertyDetailsList();
		if(null != propertyRestRequestList && !propertyRestRequestList.isEmpty())
		{
		for (PropertyRestRequestDTO propertyRestRequestDTO : propertyRestRequestList) {
			if (null != propertyRestRequestDTO.getPropertyId()
					&& !propertyRestRequestDTO.getPropertyId().trim()
							.isEmpty()) {
				propRestForm.setPropertyId(
						propertyRestRequestDTO.getPropertyId().trim());
			}
			if (null != requestDTO.getBodyDetails().get(0).getSecurityId()
					&& !requestDTO.getBodyDetails().get(0).getSecurityId().trim()
							.isEmpty()) {
				propRestForm.setCollateralIdProp(
						requestDTO.getBodyDetails().get(0).getSecurityId().trim());
			}
			
			if (null != propertyRestRequestDTO.getDescription()
					&& !propertyRestRequestDTO.getDescription().trim()
							.isEmpty()) {
				propRestForm.setDescription(
						propertyRestRequestDTO.getDescription().trim());
			}
			if (null != propertyRestRequestDTO.getPropertyType()
					&& !propertyRestRequestDTO.getPropertyType().trim()
							.isEmpty()) {
				propRestForm.setPropertyType(
						propertyRestRequestDTO.getPropertyType().trim());
			}
			if (null != propertyRestRequestDTO.getPropertyTypeLabel()
					&& !propertyRestRequestDTO.getPropertyTypeLabel().trim()
							.isEmpty()) {
				propRestForm.setPropertyTypeLabel(
						propertyRestRequestDTO.getPropertyTypeLabel().trim());
			}
			else
			{
				propRestForm.setPropertyTypeLabel("");
			}
			if (null != propertyRestRequestDTO.getPropertyUsage()
					&& !propertyRestRequestDTO.getPropertyUsage().trim()
							.isEmpty()) {
				propRestForm.setPropertyUsage(
						propertyRestRequestDTO.getPropertyUsage().trim());
			}
			if (null != propertyRestRequestDTO.getSalePurchaseValue()
					&& !propertyRestRequestDTO.getSalePurchaseValue().trim()
							.isEmpty()) {
				propRestForm.setSalePurchareAmount(
						propertyRestRequestDTO.getSalePurchaseValue().trim());
			}
			if (null != propertyRestRequestDTO.getDateOfReceiptTitleDeed()
					&& !propertyRestRequestDTO.getDateOfReceiptTitleDeed()
							.trim().isEmpty()) {
				propRestForm.setDateOfReceiptTitleDeed(propertyRestRequestDTO
						.getDateOfReceiptTitleDeed().trim());
			}
			if (null != propertyRestRequestDTO.getPreviousMortCreationDate()
					&& !propertyRestRequestDTO.getPreviousMortCreationDate()
							.trim().isEmpty()) {
				propRestForm.setPreviousMortCreationDate(propertyRestRequestDTO
						.getPreviousMortCreationDate().trim());
				propRestForm.setSalePurchaseDate_(
						propertyRestRequestDTO.getPreviousMortCreationDate().trim());
			}
			if (null != propertyRestRequestDTO.getSalePurchaseDate()
					&& !propertyRestRequestDTO.getSalePurchaseDate().trim()
							.isEmpty()) {
				propRestForm.setSalePurchaseDate(
						propertyRestRequestDTO.getSalePurchaseDate().trim());
			}
			if (null != propertyRestRequestDTO.getMortgageCreExtDateAdd()
					&& !propertyRestRequestDTO.getMortgageCreExtDateAdd().trim()
							.isEmpty()) {
				propRestForm.setMortgageCreationAdd(propertyRestRequestDTO
						.getMortgageCreExtDateAdd().trim());
			}
			if (null != propertyRestRequestDTO.getLegalAuditDate()
					&& !propertyRestRequestDTO.getLegalAuditDate().trim()
							.isEmpty()) {
				propRestForm.setLegalAuditDate(
						propertyRestRequestDTO.getLegalAuditDate().trim());
			}
			if (null != propertyRestRequestDTO.getInterveingPeriSearchDate()
					&& !propertyRestRequestDTO.getInterveingPeriSearchDate()
							.trim().isEmpty()) {
				propRestForm.setInterveingPeriSearchDate(propertyRestRequestDTO
						.getInterveingPeriSearchDate().trim());
			}
			if (null != propertyRestRequestDTO.getTypeOfMargage()
					&& !propertyRestRequestDTO.getTypeOfMargage().trim()
							.isEmpty()) {
				propRestForm.setTypeOfMargage(
						propertyRestRequestDTO.getTypeOfMargage().trim());
			}
			if (null != propertyRestRequestDTO.getDocumentReceived()
					&& !propertyRestRequestDTO.getDocumentReceived().trim()
							.isEmpty()) {
				propRestForm.setDocumentReceived(
						propertyRestRequestDTO.getDocumentReceived().trim());
			}
			if (null != propertyRestRequestDTO.getMorgageCreatedBy()
					&& !propertyRestRequestDTO.getMorgageCreatedBy().trim()
							.isEmpty()) {
				propRestForm.setMorgageCreatedBy(
						propertyRestRequestDTO.getMorgageCreatedBy().trim());
			}
			if (null != propertyRestRequestDTO.getBinNumber()
					&& !propertyRestRequestDTO.getBinNumber().trim()
							.isEmpty()) {
				propRestForm.setBinNumber(
						propertyRestRequestDTO.getBinNumber().trim());
			}
			if (null != propertyRestRequestDTO.getDocumentBlock()
					&& !propertyRestRequestDTO.getDocumentBlock().trim()
							.isEmpty()) {
				propRestForm.setDocumentBlock(
						propertyRestRequestDTO.getDocumentBlock().trim());
			}
			if (null != deferralIds
					&& !deferralIds.trim()
							.isEmpty()) {
				propRestForm.setDeferralIds(
						deferralIds.trim());
			}
			if (null != propertyRestRequestDTO.getClaim()
					&& !propertyRestRequestDTO.getClaim().trim().isEmpty()) {
				propRestForm.setClaim(propertyRestRequestDTO.getClaim().trim());
			}
			if (null != propertyRestRequestDTO.getClaimType()
					&& !propertyRestRequestDTO.getClaimType().trim()
							.isEmpty()) {
				propRestForm.setClaimType(
						propertyRestRequestDTO.getClaimType().trim());
			}
			if (null != propertyRestRequestDTO.getMortageRegisteredRef()
					&& !propertyRestRequestDTO.getMortageRegisteredRef().trim()
							.isEmpty()) {
				propRestForm.setMortageRegisteredRef(propertyRestRequestDTO
						.getMortageRegisteredRef().trim());
			}
			if (null != propertyRestRequestDTO.getAdvocateLawyerName()
					&& !propertyRestRequestDTO.getAdvocateLawyerName().trim()
							.isEmpty()) {
				propRestForm.setAdvocateLawyerName(
						propertyRestRequestDTO.getAdvocateLawyerName().trim());
			}
			if (null != propertyRestRequestDTO.getDevGrpCo()
					&& !propertyRestRequestDTO.getDevGrpCo().trim().isEmpty()) {
				propRestForm.setDevGrpCo(
						propertyRestRequestDTO.getDevGrpCo().trim());
			}
			if (null != propertyRestRequestDTO.getProjectName()
					&& !propertyRestRequestDTO.getProjectName().trim()
							.isEmpty()) {
				propRestForm.setProjectName(
						propertyRestRequestDTO.getProjectName().trim());
			}
			if (null != propertyRestRequestDTO.getLotNumberPrefix()
					&& !propertyRestRequestDTO.getLotNumberPrefix().trim()
							.isEmpty()) {
				propRestForm.setLotNumberPrefix(
						propertyRestRequestDTO.getLotNumberPrefix().trim());
			}
			if (null != propertyRestRequestDTO.getLotNo()
					&& !propertyRestRequestDTO.getLotNo().trim().isEmpty()) {
				propRestForm.setLotNo(propertyRestRequestDTO.getLotNo().trim());
			}
			if (null != propertyRestRequestDTO.getPropertyLotLocation()
					&& !propertyRestRequestDTO.getPropertyLotLocation().trim()
							.isEmpty()) {
				propRestForm.setPropertyLotLocation(
						propertyRestRequestDTO.getPropertyLotLocation().trim());
			}
			if (null != propertyRestRequestDTO.getOtherCity()
					&& !propertyRestRequestDTO.getOtherCity().trim()
							.isEmpty()) {
				propRestForm.setOtherCity(
						propertyRestRequestDTO.getOtherCity().trim());
			}
			if (null != propertyRestRequestDTO.getPropertyAddress()
					&& !propertyRestRequestDTO.getPropertyAddress().trim()
							.isEmpty()) {
				propRestForm.setPropertyAddress(
						propertyRestRequestDTO.getPropertyAddress().trim());
			}
			if (null != propertyRestRequestDTO.getPropertyAddress2()
					&& !propertyRestRequestDTO.getPropertyAddress2().trim()
							.isEmpty()) {
				propRestForm.setPropertyAddress2(
						propertyRestRequestDTO.getPropertyAddress2().trim());
			}
			if (null != propertyRestRequestDTO.getPropertyAddress3()
					&& !propertyRestRequestDTO.getPropertyAddress3().trim()
							.isEmpty()) {
				propRestForm.setPropertyAddress3(
						propertyRestRequestDTO.getPropertyAddress3().trim());
			}
			if (null != propertyRestRequestDTO.getPropertyAddress4()
					&& !propertyRestRequestDTO.getPropertyAddress4().trim()
							.isEmpty()) {
				propRestForm.setPropertyAddress4(
						propertyRestRequestDTO.getPropertyAddress4().trim());
			}
			if (null != propertyRestRequestDTO.getPropertyAddress5()
					&& !propertyRestRequestDTO.getPropertyAddress5().trim()
							.isEmpty()) {
				propRestForm.setPropertyAddress5(
						propertyRestRequestDTO.getPropertyAddress5().trim());
			}
			if (null != propertyRestRequestDTO.getPropertyAddress6()
					&& !propertyRestRequestDTO.getPropertyAddress6().trim()
							.isEmpty()) {
				propRestForm.setPropertyAddress6(
						propertyRestRequestDTO.getPropertyAddress6().trim());
			}
			if (null != propertyRestRequestDTO.getDescription()
					&& !propertyRestRequestDTO.getDescription().trim()
							.isEmpty()) {
				propRestForm.setDescription(
						propertyRestRequestDTO.getDescription().trim());
			}
			if (null != propertyRestRequestDTO.getEnvRiskyStatus()
					&& !propertyRestRequestDTO.getEnvRiskyStatus().trim()
							.isEmpty()) {
				propRestForm.setSecEnvRisky(
						propertyRestRequestDTO.getEnvRiskyStatus().trim());
			}
			if (null != propertyRestRequestDTO.getEnvRiskyDate()
					&& !propertyRestRequestDTO.getEnvRiskyDate().trim()
							.isEmpty()) {
				propRestForm.setDateSecurityEnv(
						propertyRestRequestDTO.getEnvRiskyDate().trim());
			}
			if (null != propertyRestRequestDTO.getTsrDate()
					&& !propertyRestRequestDTO.getTsrDate().trim().isEmpty()) {
				propRestForm
						.setTsrDate(propertyRestRequestDTO.getTsrDate().trim());
			}
			if (null != propertyRestRequestDTO.getTsrFrequency()
					&& !propertyRestRequestDTO.getTsrFrequency().trim()
							.isEmpty()) {
				propRestForm.setTsrFrequency(
						propertyRestRequestDTO.getTsrFrequency().trim());
			}
			if (null != propertyRestRequestDTO.getNextTsrDate()
					&& !propertyRestRequestDTO.getNextTsrDate().trim()
							.isEmpty()) {
				propRestForm.setNextTsrDate(
						propertyRestRequestDTO.getNextTsrDate().trim());
			}
			if (null != propertyRestRequestDTO.getCersiaRegistrationDate()
					&& !propertyRestRequestDTO.getCersiaRegistrationDate()
							.trim().isEmpty()) {
				propRestForm.setCersiaRegistrationDate(propertyRestRequestDTO
						.getCersiaRegistrationDate().trim());
			}
			else
			{
				propRestForm.setCersiaRegistrationDate("");
			}
			if (null != propertyRestRequestDTO.getConstitution()
					&& !propertyRestRequestDTO.getConstitution().trim()
							.isEmpty()) {
				propRestForm.setConstitution(
						propertyRestRequestDTO.getConstitution().trim());
			}
			if (null != propertyRestRequestDTO.getEnvRiskyRemarks()
					&& !propertyRestRequestDTO.getEnvRiskyRemarks().trim()
							.isEmpty()) {
				propRestForm.setRemarkEnvRisk(
						propertyRestRequestDTO.getEnvRiskyRemarks().trim());
			}
			if (null != propertyRestRequestDTO.getRevalOverride()
					&& !propertyRestRequestDTO.getRevalOverride().trim()
							.isEmpty()) {
				propRestForm.setRevalOverride(
						propertyRestRequestDTO.getRevalOverride().trim());
			}
			/*if (null != propertyRestRequestDTO.getRevaluation_v1_add()
					&& !propertyRestRequestDTO.getRevaluation_v1_add().trim()
							.isEmpty()) {
				propRestForm.setRevaluation_v1_add(
						propertyRestRequestDTO.getRevaluation_v1_add().trim());
			}*/
			if (null != propertyRestRequestDTO.getValuationDate_v1()
					&& !propertyRestRequestDTO.getValuationDate_v1().trim()
							.isEmpty()) {
				propRestForm.setValuationDate_v1(
						propertyRestRequestDTO.getValuationDate_v1().trim());
			}
			if (null != propertyRestRequestDTO.getValuatorCompany_v1()
					&& !propertyRestRequestDTO.getValuatorCompany_v1().trim()
							.isEmpty()) {
				propRestForm.setValuatorCompany_v1(
						propertyRestRequestDTO.getValuatorCompany_v1().trim());
			}
			if (null != propertyRestRequestDTO.getCategoryOfLandUse_v1()
					&& !propertyRestRequestDTO.getCategoryOfLandUse_v1().trim()
							.isEmpty()) {
				propRestForm.setCategoryOfLandUse_v1(propertyRestRequestDTO
						.getCategoryOfLandUse_v1().trim());
			}
			if(null != propertyRestRequestDTO.getTotalPropertyAmount_v1()
					&& !propertyRestRequestDTO.getTotalPropertyAmount_v1().trim()
					.isEmpty())
			{
				propRestForm.setTotalPropertyAmount_v1(propertyRestRequestDTO.getTotalPropertyAmount_v1());
			}
			if (null != propertyRestRequestDTO.getDeveloperName_v1()
					&& !propertyRestRequestDTO.getDeveloperName_v1().trim()
							.isEmpty()) {
				propRestForm.setDeveloperName_v1(
						propertyRestRequestDTO.getDeveloperName_v1().trim());
			}
			if (null != propertyRestRequestDTO.getCountry_v1()
					&& !propertyRestRequestDTO.getCountry_v1().trim()
							.isEmpty()) {
				propRestForm.setCountry_v1(
						propertyRestRequestDTO.getCountry_v1().trim());
			}
			if (null != propertyRestRequestDTO.getRegion_v1()
					&& !propertyRestRequestDTO.getRegion_v1().trim()
							.isEmpty()) {
				propRestForm.setRegion_v1(
						propertyRestRequestDTO.getRegion_v1().trim());
			}
			if (null != propertyRestRequestDTO.getLocationState_v1()
					&& !propertyRestRequestDTO.getLocationState_v1().trim()
							.isEmpty()) {
				propRestForm.setLocationState_v1(
						propertyRestRequestDTO.getLocationState_v1().trim());
			}
			if (null != propertyRestRequestDTO.getNearestCity_v1()
					&& !propertyRestRequestDTO.getNearestCity_v1().trim()
							.isEmpty()) {
				propRestForm.setNearestCity_v1(
						propertyRestRequestDTO.getNearestCity_v1().trim());
			}
			if (null != propertyRestRequestDTO.getPinCode_v1()
					&& !propertyRestRequestDTO.getPinCode_v1().trim()
							.isEmpty()) {
				propRestForm.setPinCode_v1(
						propertyRestRequestDTO.getPinCode_v1().trim());
			}
			if (null != propertyRestRequestDTO.getLandArea_v1()
					&& !propertyRestRequestDTO.getLandArea_v1().trim()
							.isEmpty()) {
				propRestForm.setLandArea_v1(
						propertyRestRequestDTO.getLandArea_v1().trim());
			}
			if (null != propertyRestRequestDTO.getLandAreaUOM_v1()
					&& !propertyRestRequestDTO.getLandAreaUOM_v1().trim()
							.isEmpty()) {
				propRestForm.setLandUOM_v1(
						propertyRestRequestDTO.getLandAreaUOM_v1().trim());
			}
			if (null != propertyRestRequestDTO.getBuiltupArea_v1()
					&& !propertyRestRequestDTO.getBuiltupArea_v1().trim()
							.isEmpty()) {
				propRestForm.setBuiltupArea_v1(
						propertyRestRequestDTO.getBuiltupArea_v1().trim());
			}
			if (null != propertyRestRequestDTO.getBuiltupAreaUOM_v1()
					&& !propertyRestRequestDTO.getBuiltupAreaUOM_v1().trim()
							.isEmpty()) {
				propRestForm.setBuiltUpAreaUnit_v1(
						propertyRestRequestDTO.getBuiltupAreaUOM_v1().trim());
			}
			if (null != propertyRestRequestDTO.getLandValue_v1()
					&& !propertyRestRequestDTO.getLandValue_v1().trim()
							.isEmpty()) {
				propRestForm.setLandValue_v1(
						propertyRestRequestDTO.getLandValue_v1().trim());
			}
			if (null != propertyRestRequestDTO.getBuildingValue_v1()
					&& !propertyRestRequestDTO.getBuildingValue_v1().trim()
							.isEmpty()) {
				propRestForm.setBuildingValue_v1(
						propertyRestRequestDTO.getBuildingValue_v1().trim());
			}
			if (null != propertyRestRequestDTO
					.getReconstructionValueOfTheBuilding_v1()
					&& !propertyRestRequestDTO
							.getReconstructionValueOfTheBuilding_v1().trim()
							.isEmpty()) {
				propRestForm.setReconstructionValueOfTheBuilding_v1(
						propertyRestRequestDTO
								.getReconstructionValueOfTheBuilding_v1()
								.trim());
			}
			if (null != propertyRestRequestDTO.getPropertyCompletionStatus_v1()
					&& !propertyRestRequestDTO.getPropertyCompletionStatus_v1()
							.trim().isEmpty()) {
				propRestForm
						.setPropertyCompletionStatus_v1(propertyRestRequestDTO
								.getPropertyCompletionStatus_v1().charAt(0));
			}
			if (null != propertyRestRequestDTO.getIsPhysicalInspection_v1()
					&& !propertyRestRequestDTO.getIsPhysicalInspection_v1()
							.trim().isEmpty()) {
				propRestForm.setIsPhysInsp_v1(propertyRestRequestDTO
						.getIsPhysicalInspection_v1().trim());
			}
			if (null != propertyRestRequestDTO.getLastPhysicalInspectDate_v1()
					&& !propertyRestRequestDTO.getLastPhysicalInspectDate_v1()
							.trim().isEmpty()) {
				propRestForm
						.setDatePhyInspec_v1(propertyRestRequestDTO
								.getLastPhysicalInspectDate_v1().trim());
			}
			if (null != propertyRestRequestDTO.getNextPhysicalInspectDate_v1()
					&& !propertyRestRequestDTO.getNextPhysicalInspectDate_v1()
							.trim().isEmpty()) {
				propRestForm
						.setNextPhysInspDate_v1(propertyRestRequestDTO
								.getNextPhysicalInspectDate_v1().trim());
			}
			if (null != propertyRestRequestDTO
					.getPhysicalInspectionFreqUnit_v1()
					&& !propertyRestRequestDTO
							.getPhysicalInspectionFreqUnit_v1().trim()
							.isEmpty()) {
				propRestForm
						.setPhysInspFreqUOM_v1(propertyRestRequestDTO
								.getPhysicalInspectionFreqUnit_v1().trim());
			}
			if (null != propertyRestRequestDTO.getRemarksProperty_v1()
					&& !propertyRestRequestDTO.getRemarksProperty_v1().trim()
							.isEmpty()) {
				propRestForm.setRemarksProperty_v1(
						propertyRestRequestDTO.getRemarksProperty_v1().trim());
			}
			if (null != propertyRestRequestDTO.getWaiver()
					&& !propertyRestRequestDTO.getWaiver().trim().isEmpty()) {
				propRestForm
						.setWaiver(propertyRestRequestDTO.getWaiver().trim());
			}
			if (null != propertyRestRequestDTO.getDeferral()
					&& !propertyRestRequestDTO.getDeferral().trim().isEmpty()) {
				propRestForm.setDeferral(
						propertyRestRequestDTO.getDeferral().trim());
			}
			if (null != propertyRestRequestDTO.getDeferralId()
					&& !propertyRestRequestDTO.getDeferralId().trim()
							.isEmpty()) {
				propRestForm.setDeferralId(
						propertyRestRequestDTO.getDeferralId().trim());
			}
			/*if (null != propertyRestRequestDTO.getRevaluation_v2_add()
					&& !propertyRestRequestDTO.getRevaluation_v2_add().trim()
							.isEmpty()) {
				propRestForm.setRevaluation_v2_add(
						propertyRestRequestDTO.getRevaluation_v2_add().trim());
			}*/
			if (null != propertyRestRequestDTO.getValuationDate_v2()
					&& !propertyRestRequestDTO.getValuationDate_v2().trim()
							.isEmpty()) {
				propRestForm.setValuationDate_v2(
						propertyRestRequestDTO.getValuationDate_v2().trim());
			}
			if(null != propertyRestRequestDTO.getTotalPropertyAmount_v2()
					&& !propertyRestRequestDTO.getTotalPropertyAmount_v2().trim()
					.isEmpty())
			{
				propRestForm.setTotalPropertyAmount_v2(propertyRestRequestDTO.getTotalPropertyAmount_v2());
			}
			if (null != propertyRestRequestDTO.getValuatorCompany_v2()
					&& !propertyRestRequestDTO.getValuatorCompany_v2().trim()
							.isEmpty()) {
				propRestForm.setValuatorCompany_v2(
						propertyRestRequestDTO.getValuatorCompany_v2().trim());
			}
			if (null != propertyRestRequestDTO.getCategoryOfLandUse_v2()
					&& !propertyRestRequestDTO.getCategoryOfLandUse_v2().trim()
							.isEmpty()) {
				propRestForm.setCategoryOfLandUse_v2(propertyRestRequestDTO
						.getCategoryOfLandUse_v2().trim());
			}
			if (null != propertyRestRequestDTO.getDeveloperName_v2()
					&& !propertyRestRequestDTO.getDeveloperName_v2().trim()
							.isEmpty()) {
				propRestForm.setDeveloperName_v2(
						propertyRestRequestDTO.getDeveloperName_v2().trim());
			}
			if (null != propertyRestRequestDTO.getCountry_v2()
					&& !propertyRestRequestDTO.getCountry_v2().trim()
							.isEmpty()) {
				propRestForm.setCountry_v2(
						propertyRestRequestDTO.getCountry_v2().trim());
			}
			if (null != propertyRestRequestDTO.getRegion_v2()
					&& !propertyRestRequestDTO.getRegion_v2().trim()
							.isEmpty()) {
				propRestForm.setRegion_v2(
						propertyRestRequestDTO.getRegion_v2().trim());
			}
			if (null != propertyRestRequestDTO.getLocationState_v2()
					&& !propertyRestRequestDTO.getLocationState_v2().trim()
							.isEmpty()) {
				propRestForm.setLocationState_v2(
						propertyRestRequestDTO.getLocationState_v2().trim());
			}
			if (null != propertyRestRequestDTO.getNearestCity_v2()
					&& !propertyRestRequestDTO.getNearestCity_v2().trim()
							.isEmpty()) {
				propRestForm.setNearestCity_v2(
						propertyRestRequestDTO.getNearestCity_v2().trim());
			}
			if (null != propertyRestRequestDTO.getPinCode_v2()
					&& !propertyRestRequestDTO.getPinCode_v2().trim()
							.isEmpty()) {
				propRestForm.setPinCode_v2(
						propertyRestRequestDTO.getPinCode_v2().trim());
			}
			if (null != propertyRestRequestDTO.getLandArea_v2()
					&& !propertyRestRequestDTO.getLandArea_v2().trim()
							.isEmpty()) {
				propRestForm.setLandArea_v2(
						propertyRestRequestDTO.getLandArea_v2().trim());
			}
			if (null != propertyRestRequestDTO.getLandAreaUOM_v2()
					&& !propertyRestRequestDTO.getLandAreaUOM_v2().trim()
							.isEmpty()) {
				propRestForm.setLandUOM_v2(
						propertyRestRequestDTO.getLandAreaUOM_v2().trim());
			}
			if (null != propertyRestRequestDTO.getBuiltupArea_v2()
					&& !propertyRestRequestDTO.getBuiltupArea_v2().trim()
							.isEmpty()) {
				propRestForm.setBuiltupArea_v2(
						propertyRestRequestDTO.getBuiltupArea_v2().trim());
			}
			if (null != propertyRestRequestDTO.getBuiltupAreaUOM_v2()
					&& !propertyRestRequestDTO.getBuiltupAreaUOM_v2().trim()
							.isEmpty()) {
				propRestForm.setBuiltUpAreaUnit_v2(
						propertyRestRequestDTO.getBuiltupAreaUOM_v2().trim());
			}
			if (null != propertyRestRequestDTO.getLandValue_v2()
					&& !propertyRestRequestDTO.getLandValue_v2().trim()
							.isEmpty()) {
				propRestForm.setLandValue_v2(
						propertyRestRequestDTO.getLandValue_v2().trim());
			}
			if (null != propertyRestRequestDTO.getBuildingValue_v2()
					&& !propertyRestRequestDTO.getBuildingValue_v2().trim()
							.isEmpty()) {
				propRestForm.setBuildingValue_v2(
						propertyRestRequestDTO.getBuildingValue_v2().trim());
			}
			if (null != propertyRestRequestDTO
					.getReconstructionValueOfTheBuilding_v2()
					&& !propertyRestRequestDTO
							.getReconstructionValueOfTheBuilding_v2().trim()
							.isEmpty()) {
				propRestForm.setReconstructionValueOfTheBuilding_v2(
						propertyRestRequestDTO
								.getReconstructionValueOfTheBuilding_v2()
								.trim());
			}
			if (null != propertyRestRequestDTO.getPropertyCompletionStatus_v2()
					&& !propertyRestRequestDTO.getPropertyCompletionStatus_v2()
							.trim().isEmpty()) {
				propRestForm
						.setPropertyCompletionStatus_v2(propertyRestRequestDTO
								.getPropertyCompletionStatus_v2().charAt(0));
			}
			if (null != propertyRestRequestDTO.getIsPhysicalInspection_v2()
					&& !propertyRestRequestDTO.getIsPhysicalInspection_v2()
							.trim().isEmpty()) {
				propRestForm.setIsPhysInsp_v2(propertyRestRequestDTO
						.getIsPhysicalInspection_v2().trim());
			}
			if (null != propertyRestRequestDTO.getLastPhysicalInspectDate_v2()
					&& !propertyRestRequestDTO.getLastPhysicalInspectDate_v2()
							.trim().isEmpty()) {
				propRestForm
						.setDatePhyInspec_v2(propertyRestRequestDTO
								.getLastPhysicalInspectDate_v2().trim());
			}
			if (null != propertyRestRequestDTO.getNextPhysicalInspectDate_v2()
					&& !propertyRestRequestDTO.getNextPhysicalInspectDate_v2()
							.trim().isEmpty()) {
				propRestForm
						.setNextPhysInspDate_v2(propertyRestRequestDTO
								.getNextPhysicalInspectDate_v2().trim());
			}
			if (null != propertyRestRequestDTO
					.getPhysicalInspectionFreqUnit_v2()
					&& !propertyRestRequestDTO
							.getPhysicalInspectionFreqUnit_v2().trim()
							.isEmpty()) {
				propRestForm
						.setPhysInspFreqUOM_v2(propertyRestRequestDTO
								.getPhysicalInspectionFreqUnit_v2().trim());
			}
			if (null != propertyRestRequestDTO.getRemarksProperty_v2()
					&& !propertyRestRequestDTO.getRemarksProperty_v2().trim()
							.isEmpty()) {
				propRestForm.setRemarksProperty_v2(
						propertyRestRequestDTO.getRemarksProperty_v2().trim());
			}
			/*if (null != propertyRestRequestDTO.getRevaluation_v3_add()
					&& !propertyRestRequestDTO.getRevaluation_v3_add().trim()
							.isEmpty()) {
				propRestForm.setRevaluation_v3_add(
						propertyRestRequestDTO.getRevaluation_v3_add().trim());
			}*/
			if (null != propertyRestRequestDTO.getValuationDate_v3()
					&& !propertyRestRequestDTO.getValuationDate_v3().trim()
							.isEmpty()) {
				propRestForm.setValuationDate_v3(
						propertyRestRequestDTO.getValuationDate_v3().trim());
			}
			if(null != propertyRestRequestDTO.getTotalPropertyAmount_v3()
					&& !propertyRestRequestDTO.getTotalPropertyAmount_v3().trim()
					.isEmpty())
			{
				propRestForm.setTotalPropertyAmount_v3(propertyRestRequestDTO.getTotalPropertyAmount_v3());
			}
			if (null != propertyRestRequestDTO.getValuatorCompany_v3()
					&& !propertyRestRequestDTO.getValuatorCompany_v3().trim()
							.isEmpty()) {
				propRestForm.setValuatorCompany_v3(
						propertyRestRequestDTO.getValuatorCompany_v3().trim());
			}
			if (null != propertyRestRequestDTO.getCategoryOfLandUse_v3()
					&& !propertyRestRequestDTO.getCategoryOfLandUse_v3().trim()
							.isEmpty()) {
				propRestForm.setCategoryOfLandUse_v3(propertyRestRequestDTO
						.getCategoryOfLandUse_v3().trim());
			}
			if (null != propertyRestRequestDTO.getDeveloperName_v3()
					&& !propertyRestRequestDTO.getDeveloperName_v3().trim()
							.isEmpty()) {
				propRestForm.setDeveloperName_v3(
						propertyRestRequestDTO.getDeveloperName_v3().trim());
			}
			if (null != propertyRestRequestDTO.getCountry_v3()
					&& !propertyRestRequestDTO.getCountry_v3().trim()
							.isEmpty()) {
				propRestForm.setCountry_v3(
						propertyRestRequestDTO.getCountry_v3().trim());
			}
			if (null != propertyRestRequestDTO.getRegion_v3()
					&& !propertyRestRequestDTO.getRegion_v3().trim()
							.isEmpty()) {
				propRestForm.setRegion_v3(
						propertyRestRequestDTO.getRegion_v3().trim());
			}
			if (null != propertyRestRequestDTO.getLocationState_v3()
					&& !propertyRestRequestDTO.getLocationState_v3().trim()
							.isEmpty()) {
				propRestForm.setLocationState_v3(
						propertyRestRequestDTO.getLocationState_v3().trim());
			}
			if (null != propertyRestRequestDTO.getNearestCity_v3()
					&& !propertyRestRequestDTO.getNearestCity_v3().trim()
							.isEmpty()) {
				propRestForm.setNearestCity_v3(
						propertyRestRequestDTO.getNearestCity_v3().trim());
			}
			if (null != propertyRestRequestDTO.getPinCode_v3()
					&& !propertyRestRequestDTO.getPinCode_v3().trim()
							.isEmpty()) {
				propRestForm.setPinCode_v3(
						propertyRestRequestDTO.getPinCode_v3().trim());
			}
			if (null != propertyRestRequestDTO.getLandArea_v3()
					&& !propertyRestRequestDTO.getLandArea_v3().trim()
							.isEmpty()) {
				propRestForm.setLandArea_v3(
						propertyRestRequestDTO.getLandArea_v3().trim());
			}
			if (null != propertyRestRequestDTO.getLandAreaUOM_v3()
					&& !propertyRestRequestDTO.getLandAreaUOM_v3().trim()
							.isEmpty()) {
				propRestForm.setLandUOM_v3(
						propertyRestRequestDTO.getLandAreaUOM_v3().trim());
			}
			if (null != propertyRestRequestDTO.getBuiltupArea_v3()
					&& !propertyRestRequestDTO.getBuiltupArea_v3().trim()
							.isEmpty()) {
				propRestForm.setBuiltupArea_v3(
						propertyRestRequestDTO.getBuiltupArea_v3().trim());
			}
			if (null != propertyRestRequestDTO.getBuiltupAreaUOM_v3()
					&& !propertyRestRequestDTO.getBuiltupAreaUOM_v3().trim()
							.isEmpty()) {
				propRestForm.setBuiltUpAreaUnit_v3(
						propertyRestRequestDTO.getBuiltupAreaUOM_v3().trim());
			}
			if (null != propertyRestRequestDTO.getLandValue_v3()
					&& !propertyRestRequestDTO.getLandValue_v3().trim()
							.isEmpty()) {
				propRestForm.setLandValue_v3(
						propertyRestRequestDTO.getLandValue_v3().trim());
			}
			if (null != propertyRestRequestDTO.getBuildingValue_v3()
					&& !propertyRestRequestDTO.getBuildingValue_v3().trim()
							.isEmpty()) {
				propRestForm.setBuildingValue_v3(
						propertyRestRequestDTO.getBuildingValue_v3().trim());
			}
			if (null != propertyRestRequestDTO
					.getReconstructionValueOfTheBuilding_v3()
					&& !propertyRestRequestDTO
							.getReconstructionValueOfTheBuilding_v3().trim()
							.isEmpty()) {
				propRestForm.setReconstructionValueOfTheBuilding_v3(
						propertyRestRequestDTO
								.getReconstructionValueOfTheBuilding_v3()
								.trim());
			}
			if (null != propertyRestRequestDTO.getPropertyCompletionStatus_v3()
					&& !propertyRestRequestDTO.getPropertyCompletionStatus_v3()
							.trim().isEmpty()) {
				propRestForm
						.setPropertyCompletionStatus_v3(propertyRestRequestDTO
								.getPropertyCompletionStatus_v3().charAt(0));
			}
			if (null != propertyRestRequestDTO.getIsPhysicalInspection_v3()
					&& !propertyRestRequestDTO.getIsPhysicalInspection_v3()
							.trim().isEmpty()) {
				propRestForm.setIsPhysInsp_v3(propertyRestRequestDTO
						.getIsPhysicalInspection_v3().trim());
			}
			if (null != propertyRestRequestDTO.getLastPhysicalInspectDate_v3()
					&& !propertyRestRequestDTO.getLastPhysicalInspectDate_v3()
							.trim().isEmpty()) {
				propRestForm
						.setDatePhyInspec_v3(propertyRestRequestDTO
								.getLastPhysicalInspectDate_v3().trim());
			}
			if (null != propertyRestRequestDTO.getNextPhysicalInspectDate_v3()
					&& !propertyRestRequestDTO.getNextPhysicalInspectDate_v3()
							.trim().isEmpty()) {
				propRestForm
						.setNextPhysInspDate_v3(propertyRestRequestDTO
								.getNextPhysicalInspectDate_v3().trim());
			}
			if (null != propertyRestRequestDTO
					.getPhysicalInspectionFreqUnit_v3()
					&& !propertyRestRequestDTO
							.getPhysicalInspectionFreqUnit_v3().trim()
							.isEmpty()) {
				propRestForm
						.setPhysInspFreqUOM_v3(propertyRestRequestDTO
								.getPhysicalInspectionFreqUnit_v3().trim());
			}
			if (null != propertyRestRequestDTO.getRemarksProperty_v3()
					&& !propertyRestRequestDTO.getRemarksProperty_v3().trim()
							.isEmpty()) {
				propRestForm.setRemarksProperty_v3(
						propertyRestRequestDTO.getRemarksProperty_v3().trim());
			}

		}
		
		
	}	
		
		return propRestForm;
		
	}

	private static Amount getAmount(Locale locale, IPropertyCollateral iObj, String defVal) {
		try {
			return CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), defVal);
		}
		catch (Exception e) {
			return null;
		}	
		
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
	 
	 public PortItemForm getFormFromDTORestStock(StockRestRequestDTO requestDTO ) 
		{
			
			//AssetAircraftForm form= new AssetAircraftForm(); 
			PortItemForm form1 = new PortItemForm();



			if(form1 instanceof PortItemForm)
			{

				if(null != requestDTO.getStockExchange()
						&& !requestDTO.getStockExchange().trim().isEmpty())
				{
					form1.setStockExchange(requestDTO.getStockExchange());
				}

				if(null != requestDTO.getIsinCode()
						&& !requestDTO.getIsinCode().trim().isEmpty())
				{
					form1.setIsinCode(requestDTO.getIsinCode());
				}

				if(null != requestDTO.getIsserIdentType()
						&& !requestDTO.getIsserIdentType().trim().isEmpty())
				{
					form1.setIsserIdentType(requestDTO.getIsserIdentType());
				}

				if(null != requestDTO.getNoOfUnit()
						&& !requestDTO.getNoOfUnit().trim().isEmpty())
				{
					form1.setNoOfUnit(requestDTO.getNoOfUnit());
				}

				if(null != requestDTO.getNominalValue()
						&& !requestDTO.getNominalValue().trim().isEmpty())
				{
					form1.setNominalValue(requestDTO.getNominalValue());
				}
				if(null != requestDTO.getCertNo()
						&& !requestDTO.getCertNo().trim().isEmpty())
				{
					form1.setCertNo(requestDTO.getCertNo());
				}
				if(null != requestDTO.getIssuerName()
						&& !requestDTO.getIssuerName().trim().isEmpty())
				{
					form1.setIssuerName(requestDTO.getIssuerName());
				}

				/*if(requestDTO.getStockLineDetailsList() != null 
						  && !requestDTO.getStockLineDetailsList().isEmpty())
				  {
					  if(null != requestDTO.getStockLineDetailsList().get(0).get
							  && !requestDTO.getIssuerName().trim().isEmpty())
					  {
						  form1.setIssuerName(requestDTO.getIssuerName());
					  }
				  }*/

				return form1;
			}
			return form1;
		}
		public MarketableEquityLineDetailForm getFormFromDTORestStockLine(StockLineRestRequestDTO stockLineRestRequestDTO )
		{

			MarketableEquityLineDetailForm form1 = new MarketableEquityLineDetailForm();


			if(null != stockLineRestRequestDTO.getFacilityName()
					&& !stockLineRestRequestDTO.getFacilityName().trim().isEmpty())
			{
				form1.setFacilityName(stockLineRestRequestDTO.getFacilityName());
			}
			if(null != stockLineRestRequestDTO.getFacilityId()
					&& !stockLineRestRequestDTO.getFacilityId().trim().isEmpty())
			{
				form1.setFacilityId(stockLineRestRequestDTO.getFacilityId());
			}
			
			if(null != stockLineRestRequestDTO.getLineNumber()
					&& !stockLineRestRequestDTO.getLineNumber().trim().isEmpty())
			{
				form1.setLineNumber(stockLineRestRequestDTO.getLineNumber());
			}
			if(null != stockLineRestRequestDTO.getFasNumber()
					&& !stockLineRestRequestDTO.getFasNumber().trim().isEmpty())
			{
				form1.setFasNumber(stockLineRestRequestDTO.getFasNumber());
			}
			
			if(null != stockLineRestRequestDTO.getSerialNumber()
					&& !stockLineRestRequestDTO.getSerialNumber().trim().isEmpty())
			{
				form1.setSerialNumber(stockLineRestRequestDTO.getSerialNumber());
			}
			if(null != stockLineRestRequestDTO.getLtv()
					&& !stockLineRestRequestDTO.getLtv().trim().isEmpty())
			{
				form1.setLtv(stockLineRestRequestDTO.getLtv());
			}
			
			if(null != stockLineRestRequestDTO.getRemarks()
					&& !stockLineRestRequestDTO.getRemarks().trim().isEmpty())
			{
				form1.setRemarks(stockLineRestRequestDTO.getRemarks());
			}

			return form1;

		}

		public static IOtherListedLocal addMarketColl(IOtherListedLocal iColother, IMarketableEquity marketObj) {
			IMarketableEquity[] existingArray = iColother.getEquityList();
			int arrayLength = 0;
			if (existingArray != null) {
				arrayLength = existingArray.length;
			}

			IMarketableEquity[] newArray = new IMarketableEquity[arrayLength + 1];
			if (existingArray != null) {
				System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
			}
			newArray[arrayLength] = marketObj;

			iColother.setEquityList(newArray);
			return iColother;
		}
		
		
		public static IMarketableEquity addMarketCollLine(IMarketableEquity iColother, IMarketableEquityLineDetail marketObj) {
			IMarketableEquityLineDetail[] existingArray = iColother.getLineDetails();
			int arrayLength = 0;
			if (existingArray != null) {
				arrayLength = existingArray.length;
			}

			IMarketableEquityLineDetail[] newArray = new IMarketableEquityLineDetail[arrayLength + 1];
			if (existingArray != null) {
				System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
			}
			newArray[arrayLength] = marketObj;

			iColother.setLineDetails(newArray);
			return iColother;
		}
		
		 public static double convertToSqfeet(String area,String areaUnit){
			
			double area1=Double.parseDouble(area);  
			double inSqft = 0;
			if(null != area && !area.trim().isEmpty() && null != areaUnit && !areaUnit.trim().isEmpty())
			{
				if("ACRES".equals(areaUnit)){
					inSqft=area1*43560;
		     	}	
		     	else if("ARE".equals(areaUnit)){
		     		inSqft=area1*1076.39;
		     	}
		     	else if("BIGHA".equals(areaUnit)){
		     		inSqft=area1*17452.0069;
		     	}
		     	else if("BISWA".equals(areaUnit)){
		     		inSqft=area1*357142.857;
		     	}
		     	else if("BISWANI".equals(areaUnit)){
		     		inSqft=area1*17857.142;
		     	}
		     	else if("CENTS".equals(areaUnit)){
		     		inSqft=area1*435.54;
		     	}
		     	else if("CHITTAK".equals(areaUnit)){
		     		inSqft=area1*45;
		     	} 
		     	else if("DECIMAL".equals(areaUnit)){
		     		inSqft=area1*436;
		     	}
		     	else if("GROUND".equals(areaUnit)){
		     		inSqft=area1*2400.3840;
		     	}
		     	else if("GUNTHA".equals(areaUnit)){
		     		inSqft=area1*1089.0873;
		     	}
		     	else if("HQT".equals(areaUnit)){
		     		inSqft=area1*107639;
		     	}
		     	else if("KANAL".equals(areaUnit)){
		     		inSqft=area1*5445;
		     	}
		     	/* else if("Killa".equals(areaUnit)){
		     		inSqft=areaValue*43560;
		     	} */
		     	else if("KOTTA".equals(areaUnit)){
		     		inSqft=area1*720;
		     	}
		     	else if("MARLA".equals(areaUnit)){
		     		inSqft=area1*272.251;
		     	}
		     	else if("SATAK".equals(areaUnit)){
		     		inSqft=area1*458;
		     	}
		     	else if("SQFT".equals(areaUnit)){
		     		inSqft=area1*1;
		     	}
		     	else if("SQM".equals(areaUnit)){
		     		inSqft=area1*10.7639;
		     	}
		     	else if("SQY".equals(areaUnit)){
		     		inSqft=area1*9;
		     	}
			}
			return inSqft;
			
			 
		 }
		 
		 public AddtionalDocumentFacilityDetailsRestDTO getAddDocFacDetReq(AddtionalDocumentFacilityDetailsRestDTO facDocDTO,String securityId) {
			 ActionErrors errors = new ActionErrors();
			 MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
			 if(null != facDocDTO.getActionFlag() && !facDocDTO.getActionFlag().trim().isEmpty())
				{
					if("A".equalsIgnoreCase(facDocDTO.getActionFlag()))
					{
						facDocDTO.setActionFlag("A");
						if(null != facDocDTO.getUniqueAddDocFacDetID() && !facDocDTO.getUniqueAddDocFacDetID().trim().isEmpty())
				{
					boolean isUniqueIdPresent = collateralDAO.checkuniqueAddDocFacDetailsId(facDocDTO.getUniqueAddDocFacDetID().trim(),securityId);
					if(isUniqueIdPresent)
					{
						errors.add("uniqueAddDocFacDetID-"+facDocDTO.getUniqueAddDocFacDetID(), new ActionMessage("uniqueAddDocFacDetID is duplicate"));
					} else if (!ASSTValidator.isNumeric(facDocDTO.getUniqueAddDocFacDetID().trim())
							|| facDocDTO.getUniqueAddDocFacDetID().trim().length() > 25){
						errors.add("uniqueAddDocFacDetID", new ActionMessage("error.invalid.value"));
					} else {
						facDocDTO.setUniqueAddDocFacDetID(facDocDTO.getUniqueAddDocFacDetID());
					}
				}else{
					errors.add("uniqueAddDocFacDetID",new ActionMessage("uniqueAddDocFacDetID is mandatory"));
					}
			   }
					else if("U".equalsIgnoreCase(facDocDTO.getActionFlag()))
					{
						facDocDTO.setActionFlag("U");
						if(null != facDocDTO.getAddDocFacDetID() && !facDocDTO.getAddDocFacDetID().trim().isEmpty())
						{
							boolean isAdhocPresent = collateralDAO.checkAddDocFacDetailsId(facDocDTO.getAddDocFacDetID(),securityId);
							if(!isAdhocPresent)
							{
								errors.add("addDocFacDetID",new ActionMessage("addDocFacDetID is invalid"));
							}
							else
							{
								facDocDTO.setAddDocFacDetID(facDocDTO.getAddDocFacDetID());
							}
							
						}
						else
						{
							errors.add("addDocFacDetID",new ActionMessage("addDocFacDetID is mandatory for update"));
						}
				}
					else
					{
						errors.add("actionFlag",new ActionMessage("Invalid Action Flag value"));
					}
				}
			 else
				{
					errors.add("actionFlag",new ActionMessage("Action Flag is mandatory"));
				}
			 
			 
			 if(null != facDocDTO.getDocFacilityCategory() && !facDocDTO.getDocFacilityCategory().trim().isEmpty())
			 {

				 try {
					 Object obj = masterObj.getObjectByEntityName("entryCode", facDocDTO.getDocFacilityCategory().trim(),"docFacilityCategory",errors,"FACILITY_CATEGORY");
					 if(!(obj instanceof ActionErrors)){
						 facDocDTO.setDocFacilityCategory(((ICommonCodeEntry)obj).getEntryCode());
						}
					 else
					 {
						 errors.add("docFacilityCategory",new ActionMessage("error.invalid.field.value")); 
					 }
				 }
				 catch(Exception e){
						DefaultLogger.error(this, "docFacilityCategory"+e.getMessage());
						errors.add("docFacilityCategory",new ActionMessage("error.invalid.field.value"));
					}
			 }
			 else
			 {
				 errors.add("docFacilityCategory",new ActionMessage("error.string.mandatory"));
			 }
			 
			 if(null != facDocDTO.getDocFacilityType() && !facDocDTO.getDocFacilityType().trim().isEmpty())
			 {
				 try {
					 Object obj = masterObj.getObjectByEntityName("entryCode", facDocDTO.getDocFacilityType().trim(),"docFacilityType",errors,"FACILITY_TYPE");
					 if(!(obj instanceof ActionErrors)){
						 facDocDTO.setDocFacilityType(((ICommonCodeEntry)obj).getEntryCode());
						}
					 else
					 {
						 errors.add("docFacilityType",new ActionMessage("error.invalid.field.value")); 
					 }
				 }
				 catch(Exception e){
						DefaultLogger.error(this, "docFacilityType"+e.getMessage());
						errors.add("docFacilityType",new ActionMessage("error.invalid.field.value"));
					}
			 }
			 else
			 {
				 errors.add("docFacilityType",new ActionMessage("error.string.mandatory"));
			 }
			 if(null != facDocDTO.getDocFacilityTotalAmount() && !facDocDTO.getDocFacilityTotalAmount().trim().isEmpty())
			 {
				 String errorCode;
				if (!(errorCode = Validator.checkAmount(facDocDTO.getDocFacilityTotalAmount(), true, 0,
							IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, IGlobalConstant.DEFAULT_CURRENCY, Locale.getDefault()))
							.equals(Validator.ERROR_NONE)) {
						errors.add("docFacilityTotalAmount",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
								"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
					}
			 }
			 else
			 {
				 errors.add("docFacilityTotalAmount",new ActionMessage("error.string.mandatory"));
			 }
			 
			 
				
				
			 facDocDTO.setErrors(errors);
			return facDocDTO;
			 
		 }
		 
		 public static void addDocumentFacilityDetails(ICollateral iCol, IAddtionalDocumentFacilityDetails facDocDetailObj) {
				IAddtionalDocumentFacilityDetails[] existingArray = iCol.getAdditonalDocFacDetails();
				int arrayLength = 0;
				if (existingArray != null) {
					arrayLength = existingArray.length;
				}

				IAddtionalDocumentFacilityDetails[] newArray = new IAddtionalDocumentFacilityDetails[arrayLength + 1];
				if (existingArray != null) {
					System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
				}
				newArray[arrayLength] = facDocDetailObj;

				iCol.setAdditonalDocFacDetails(newArray);
			}
		 
		 public static void updateDocumentFacilityDetails(ICollateral iCol, IAddtionalDocumentFacilityDetails facDocDetailObj) {
			 IAddtionalDocumentFacilityDetails[] existingArray = iCol.getAdditonalDocFacDetails();
				List<IAddtionalDocumentFacilityDetails> list = new ArrayList<IAddtionalDocumentFacilityDetails>();
				for (int i = 0; i < existingArray.length; i++) {
					IAddtionalDocumentFacilityDetails ob = existingArray[i];
					if(ob.getAddDocFacDetID() == facDocDetailObj.getAddDocFacDetID())
					{
						list.add(facDocDetailObj);
					}
					else
					{
						list.add(existingArray[i]);
					}
				}
				IAddtionalDocumentFacilityDetails[] newArray = list.toArray(new IAddtionalDocumentFacilityDetails[0]);
				iCol.setAdditonalDocFacDetails(newArray);
		}
		 
		 public static String totalValue(String landValue,String buildingValue){
			 /*Amount totalAmount = new Amount();*/
             BigDecimal totalAmount;
//             double parselandValue = Double.parseDouble(landValue);
//             double parsebuildingValue = Double.parseDouble(buildingValue);
             BigDecimal parselandValue = new BigDecimal(landValue);
             BigDecimal parsebuildingValue = new BigDecimal(buildingValue);

             totalAmount=parselandValue.add(parsebuildingValue);


             Amount landValueAmt = new Amount(UIUtil.mapStringToBigDecimal(totalAmount.toString()),
                        new CurrencyCode("INR"));


             return totalAmount+"";
		 }
		 
		 public static Amount calculatLowestSecurityOMV(PropertyRestRequestDTO propertyRestRequestDTO, IPropertyCollateral prop){
			 long cmv = 0;
			
			 long parseBuildingValue1;
			 long parseBuildingValue2;
			 long parseBuildingValue3;
			 long parseLandValue1;
			 long parseLandValue2;
			 long parseLandValue3;
		if (null != propertyRestRequestDTO.getBuildingValue_v1()
				&&!propertyRestRequestDTO.getBuildingValue_v1().trim().isEmpty()) {
			parseBuildingValue1 = Long.parseLong(propertyRestRequestDTO.getBuildingValue_v1());
		}
		else
		{
			parseBuildingValue1 = 0;
		}
		if (null != propertyRestRequestDTO.getBuildingValue_v2()
				&&!propertyRestRequestDTO.getBuildingValue_v2().trim().isEmpty()) {
			parseBuildingValue2 = Long.parseLong(propertyRestRequestDTO.getBuildingValue_v2());
		}
		else{
			parseBuildingValue2 = 0;
		}
		if (null != propertyRestRequestDTO.getBuildingValue_v3()
				&&!propertyRestRequestDTO.getBuildingValue_v3().trim().isEmpty()) {
			parseBuildingValue3 = Long.parseLong(propertyRestRequestDTO.getBuildingValue_v3());
		}else
		{
			parseBuildingValue3 = 0;
		}
		
		if (null != propertyRestRequestDTO.getLandValue_v1()
				&& !propertyRestRequestDTO.getLandValue_v1().trim().isEmpty()) {
			parseLandValue1 = Long.parseLong(propertyRestRequestDTO.getLandValue_v1());
		}
		else
		{
			parseLandValue1 = 0;
		}
			
		if (null != propertyRestRequestDTO.getLandValue_v2()
				&& !propertyRestRequestDTO.getLandValue_v2().trim().isEmpty()) {
			parseLandValue2 = Long.parseLong(propertyRestRequestDTO.getLandValue_v2());
		}
		else
		{
			parseLandValue2 = 0;
		}
			
		if (null != propertyRestRequestDTO.getLandValue_v3()
				&& !propertyRestRequestDTO.getLandValue_v3().trim().isEmpty()) {
			parseLandValue3 = Long.parseLong(propertyRestRequestDTO.getLandValue_v3());
		}
		else
		{
			parseLandValue3 = 0;
		}
			long parseVersion1 = Long.parseLong(prop.getVersion1());
			long parseVersion2 = Long.parseLong(prop.getVersion2());
			long parseVersion3 = Long.parseLong(prop.getVersion3());
			
			
			String waiver = prop.getWaiver();
			String deferral = prop.getDeferral();
			
			long total1 = parseLandValue1+parseBuildingValue1;
			long total2 = parseLandValue2+parseBuildingValue2;
			long total3 = parseLandValue3+parseBuildingValue3;
			
			long maxvalue;
			if("on".equalsIgnoreCase(waiver) || "on".equalsIgnoreCase(deferral))
				maxvalue= Math.max(parseVersion1,parseVersion3);
			else
				maxvalue= Math.max(Math.max(parseVersion1,parseVersion2),parseVersion3);
			
			boolean val1IsAdded=false;
			boolean val2IsAdded=false;
			boolean val3IsAdded=false;
			
			if(maxvalue==parseVersion1 && total1>0){
				val1IsAdded=true;
			}
			if (maxvalue==parseVersion2 && "off".equalsIgnoreCase(waiver) && "off".equalsIgnoreCase(deferral) && total2>0){
					val2IsAdded=true;
				//	alert("inside condition2");

				}
			if (maxvalue==parseVersion3 && total3>0){
					val3IsAdded=true;
				//	alert("inside condition2");
				
				}
				
			String val="";
			if(val1IsAdded && val2IsAdded && val3IsAdded ) {
				if(total1==total2 && total1==total3) {
					val= "1";
				}
				else if(total1>total2)  {
					if(total2<total3)  {
					
					val= "2";
					}else if(total2==total3){
						val= "2";
					}else if (total2>total3)  {
						
						val= "3";
						}
				}
				else if(total1==total2)  {
					if(total1>total3)  {
					
					val= "3";
					}else if(total1==total3){
						val= "1";
					}else if (total1<total3)  {
						
						val= "1";
						}
				}
				else if(total1<total2)  {
					if(total1<total3)  {
					
					val= "1";
					}else if(total1==total3){
						val= "1";
					}else if (total1>total3)  {
						
						val= "3";
						}
				}
			}
			else if(val1IsAdded && val2IsAdded) {
				if(total1==total2) {
					val= "1";
				}	
				else if(total1<total2)  {
					
					val= "1";
					
				}else if (total2<total1)  {
					
					val= "2";
					}
			}else if(val1IsAdded && val3IsAdded) {
				if(total1==total3) {
					val= "1";
				}	
				else if(total1<total3)  {
					
					val= "1";
					
				}else if (total3<total1)  {
					
					val= "3";
					}
				
			}else if(val2IsAdded && val3IsAdded) {
				if(total2==total3) {
					val= "2";
				}	
				else if(total2<total3)  {
					
					val= "2";
					
				}else if (total3<total2)  {
					
					val= "3";
					}
				
			}else if(val1IsAdded) {
				val= "1";
			}else if(val2IsAdded) {
				val= "2";
			}else if(val3IsAdded) {
				val= "3";
			}
			if(val=="1"){
				cmv = total1;
			}
			else if(val=="2")
				cmv = total2;
			else if(val=="3")
				cmv = total3;
			
			
			 Amount cmvAmt = null;
			 
			 cmvAmt = new Amount(UIUtil.mapStringToBigDecimal(cmv+""),
						new CurrencyCode("INR"));
			
			return cmvAmt;
			
			
			 
		 }
		 
		 public static String calculateLoanableAmount1(CollateralRestRequestDTO requestDTO,ICollateral collateral){
			 double loanableAmt = 0;
			 double exchangeRateINR = 0;
			 double cmv=0;
			 double margin  = 0;
			 
			 String cmv1 = "";
			 
			 CollateralDetailslRestRequestDTO collateralDetailslRestRequestDTO = requestDTO.getBodyDetails().get(0);
			 
			 try {
				cmv1 = CurrencyManager.convertToString(Locale.getDefault(),collateral.getCMV());
			
			 cmv = MapperUtil.mapStringToDouble(cmv1, Locale.getDefault()) ;
			 margin = MapperUtil.mapStringToDouble(collateralDetailslRestRequestDTO.getMargin(), Locale.getDefault());
			 }
			 catch (Exception e) {
					e.printStackTrace();
				}
			 if(!"INR".equalsIgnoreCase(collateral.getCurrencyCode()))
			 {
				 if(collateralDetailslRestRequestDTO.getExchangeRateINR() != null && !collateralDetailslRestRequestDTO.getExchangeRateINR().trim().isEmpty())
				 {
					 exchangeRateINR = Long.getLong(collateralDetailslRestRequestDTO.getExchangeRateINR());
				 }
				 loanableAmt = (cmv * exchangeRateINR) - ((cmv * margin * exchangeRateINR) / 100);
			 }
			 else
			 {
				 loanableAmt = cmv - (cmv * margin)/100; 
			 }
			 return loanableAmt+"";
		 }
		 
		 
		 
		 public Object getActualForDeleteFromDTORest(CollateralDeleteEnquiryRestRequestDTO colDelRestRequestDTO,ICollateral col) throws ParseException {
			
			 col.setStatus("DELETED");
			 col.setCollateralStatus(ICMSConstant.HOST_COL_STATUS_DELETED);
			 
			return col;
		 }
		 
	public CollateralEnqiryDetailsResponseDTO getCommonRespForColObj(CollateralEnqiryDetailsResponseDTO res, ICollateral col,String ColSubTypeId) throws ParseException {
		MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();
		res.setSecurityId(Long.toString(col.getCollateralID()));
		if (null != col.getSCIReferenceNote()
				&& !col.getSCIReferenceNote().trim().isEmpty()) {
			res.setSciReferenceNote(col.getSCIReferenceNote());
		}
		if (null != col.getCollateralType().getTypeName()
				&& !col.getCollateralType().getTypeName().trim().isEmpty()) {
			res.setCollateralType(col.getCollateralType().getTypeName());
		}
		if (null != col.getCollateralSubType().getSubTypeDesc()
				&& !col.getCollateralSubType().getSubTypeDesc().trim().isEmpty()) {
			res.setCollateralSubType(col.getCollateralSubType().getSubTypeDesc());
		}
		if (null != col.getCurrencyCode()
				&& !col.getCurrencyCode().trim().isEmpty()) {

			Object obj = masterObj.getObjectforMasterRelatedCode(
					"actualForexFeedEntry",
					col.getCurrencyCode().trim(),
					"collateralCurrency", errors);
			if (!(obj instanceof ActionErrors)) {
				res.setCollateralCurrency(
						((IForexFeedEntry) obj).getCurrencyDescription());
				res.setCollateralCurrencyCode(
						((IForexFeedEntry) obj).getCurrencyIsoCode());
			} 
		}
		if (null != col.getSecPriority()
				&& !col.getSecPriority().trim().isEmpty()) {
			res.setSecPriority(col.getSecPriority());
		}
		if (null != col.getMonitorProcess()
				&& !col.getMonitorProcess().trim().isEmpty()) {
			res.setMonitorProcess(col.getMonitorProcess());
		}
		if (null != col.getMonitorFrequency()
				&& !col.getMonitorFrequency().trim().isEmpty()) {
			 try {
				 Object obj = masterObj.getObjectByEntityName("entryCode", col.getMonitorFrequency().trim(),"monitorFrequency",errors,"FREQUENCY");
				 if(!(obj instanceof ActionErrors)){
					 res.setMonitorFrequency(((ICommonCodeEntry)obj).getEntryName());
					 res.setMonitorFrequencyCode(((ICommonCodeEntry)obj).getEntryCode());
					}
			 }
			 catch(Exception e){
				DefaultLogger.error(this, "monitorFrequency"+e.getMessage());
				e.printStackTrace();
				}
		}
		//Custodian1 Custodian2 pending
		/*if (null != col.getCollateralCustodian()
				&& !col.getSecurityOwnership().trim().isEmpty()) {
			res.setSecurityOwnership(col.getSecurityOwnership());
		}
		if (null != col.getCollateralCustodianType()
				&& !col.getCollateralCustodianType().trim().isEmpty()) {
			res.setCollateralCustodianType(col.getCollateralCustodianType());
		}*/
		if (null != col.getCollateralLocation()
				&& !col.getCollateralLocation().trim().isEmpty()) {
			res.setCollateralLocation(col.getCollateralLocation());
		}
		if (null != col.getCollateralCode()
				&& !col.getCollateralCode().trim().isEmpty()) {
			res.setCollateralCode(col.getCollateralCode());
		}
		if (null != col.getSecurityOrganization()
				&& !col.getSecurityOrganization().trim().isEmpty()) {
			res.setSecurityOrganization(col.getSecurityOrganization());
		}
		if (null != col.getValuationAmount()
				&& !col.getValuationAmount().trim().isEmpty()) {
			res.setValuationAmount(col.getValuationAmount());
		}
		if (null != col.getValuationDate()) {
			res.setValuationDate(ddMMMyyyyDateformat.format(col.getValuationDate()));
		}
		if (null != col.getCommonRevalFreq()
				&& !col.getCommonRevalFreq().trim().isEmpty()) {
			res.setCommonRevalFreq(col.getCommonRevalFreq());
		}
		
		if (null != col.getNextValDate()) {
			res.setNextValDate(ddMMMyyyyDateformat.format(col.getNextValDate()));
		}
		if (null != col.getTypeOfChange()
				&& !col.getTypeOfChange().trim().isEmpty()) {
			

			 try {
				 Object obj = masterObj.getObjectByEntityName("entryCode", col.getTypeOfChange().trim(),"typeOfChange",errors,"TYPE_CHARGE");
				 if(!(obj instanceof ActionErrors)){
					 res.setTypeOfChange(((ICommonCodeEntry)obj).getEntryName());
					 res.setTypeOfChangeCode(((ICommonCodeEntry)obj).getEntryCode());
					}
			 }
			 catch(Exception e){
					DefaultLogger.error(this, "typeOfChange - "+e.getMessage());
					e.printStackTrace();
				}
		}
		
		if (null != col.getOtherBankCharge()
				&& !col.getOtherBankCharge().trim().isEmpty()) {
			res.setOtherBankCharge(col.getOtherBankCharge());
		}
		if (null != col.getSecurityOwnership()
				&& !col.getSecurityOwnership().trim().isEmpty()) {

			 try {
				 Object obj = masterObj.getObjectByEntityName("entryCode", col.getSecurityOwnership().trim(),"securityOwnership",errors,"SECURITY_OWNERSHIP");
				 if(!(obj instanceof ActionErrors)){
					 res.setSecurityOwnership(((ICommonCodeEntry)obj).getEntryName());
					 res.setSecurityOwnershipCode(((ICommonCodeEntry)obj).getEntryCode());
					}
				
			 }
			 catch(Exception e){
					DefaultLogger.error(this, "securityOwnership - "+e.getMessage());
					e.printStackTrace();
				}
		 
			
		}
		if (null != col.getThirdPartyEntity()
				&& !col.getThirdPartyEntity().trim().isEmpty()) {

			try {
				 Object obj = masterObj.getObjectByEntityName("entryCode", col.getThirdPartyEntity().trim(),"thirdPartyEntity",errors,"Entity");
				 if(!(obj instanceof ActionErrors)){
					 res.setThirdPartyEntity(((ICommonCodeEntry)obj).getEntryName());
					 res.setThirdPartyEntityCode(((ICommonCodeEntry)obj).getEntryCode());
				 }
			 }
			 catch(Exception e){
					DefaultLogger.error(this, "thirdPartyEntity - "+e.getMessage());
					e.printStackTrace();
				}
		 
		}
		if (null != col.getCinForThirdParty()
				&& !col.getCinForThirdParty().trim().isEmpty()) {
			res.setCinForThirdParty(col.getCinForThirdParty());
		}
		
		if (null != col.getOwnerOfProperty()
				&& !col.getOwnerOfProperty().trim().isEmpty()) {
			res.setOwnerOfProperty(col.getOwnerOfProperty());
		}
		if (null != col.getCersaiTransactionRefNumber()
				&& !col.getCersaiTransactionRefNumber().trim().isEmpty()) {
			res.setCersaiTransactionRefNumber(col.getCersaiTransactionRefNumber());
		}
		if (null != col.getCersaiSecurityInterestId()
				&& !col.getCersaiSecurityInterestId().trim().isEmpty()) {
			res.setCersaiSecurityInterestId(col.getCersaiSecurityInterestId());
		}
		if (null != col.getCersaiAssetId()
				&& !col.getCersaiAssetId().trim().isEmpty()) {
			res.setCersaiAssetId(col.getCersaiAssetId());
		}
		if (null != col.getDateOfCersaiRegisteration()){
			res.setDateOfCersaiRegisteration(ddMMMyyyyDateformat.format(col.getDateOfCersaiRegisteration()));
		}
		if (null != col.getCersaiId()
				&& !col.getCersaiId().trim().isEmpty()) {
			res.setCersaiId(col.getCersaiId());
		}
		if (null != col.getSaleDeedPurchaseDate()) {
			res.setSaleDeedPurchaseDate(ddMMMyyyyDateformat.format(col.getSaleDeedPurchaseDate()));
		}
		if (null != col.getThirdPartyAddress()
				&& !col.getThirdPartyAddress().trim().isEmpty()) {
			res.setThirdPartyAddress(col.getThirdPartyAddress());
		}
		if (null != col.getThirdPartyState()
				&& !col.getThirdPartyState().trim().isEmpty()) {
			Object obj = masterObj.getObjectforMaster("actualState",
					col.getThirdPartyState().trim());
			if (!(obj instanceof ActionErrors)) {
				res.setThirdPartyStateId(((IState) obj).getStateCode() + "");
				res.setThirdPartyState(((IState) obj).getStateName());
				
			} else {
				errors.add("thirdPartyState", new ActionMessage("error.invalid.thirdPartyState"));
			}
		
			
		}
		if (null != col.getThirdPartyCity()
				&& !col.getThirdPartyCity().trim().isEmpty()) {


			Object obj = masterObj.getObjectforMaster("actualCity",
					col.getThirdPartyCity().trim());
			if (!(obj instanceof ActionErrors)) {
				
				res.setThirdPartyCityId(((ICity) obj).getCityCode() + "");
				res.setThirdPartyCity(((ICity) obj).getCityName());
				}
		}
		if (null != col.getThirdPartyPincode()
				&& !col.getThirdPartyPincode().trim().isEmpty()) {
			res.setThirdPartyPincode(col.getThirdPartyPincode());
		}
		if (null != col.getThirdPartyAddress()
				&& !col.getThirdPartyAddress().trim().isEmpty()) {
			res.setThirdPartyAddress(col.getThirdPartyAddress());
		}
		if(col.getMargin() >= 0)
		res.setMargin(Double.toString(col.getMargin()));
		if (null != col.getCMV()) {
			res.setCmv(Double.toString(col.getCMV().getAmount()));
		}
		return res;

	}
	
	public ABEnquiryResponseDTO getAbSpecificAssetRespForColObj(ABSpecEnqDetailsResponseDTO res, ICollateral col,String ColSubTypeId) throws ParseException {
		ABEnquiryResponseDTO colRes = new ABEnquiryResponseDTO();
		MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();
		ISpecificChargeAircraft iObj = null;
		if(col instanceof OBSpecificChargeAircraft)
		{
			 iObj = (ISpecificChargeAircraft) col;
		}
		
		if (null != iObj.getRamId()
				&& !iObj.getRamId().trim().isEmpty()) {
			res.setRamId(iObj.getRamId());
		}
		if (null != iObj.getStartDate()
				&& !iObj.getStartDate().toString().trim().isEmpty()) {
			res.setStartDate(ddMMMyyyyDateformat.format(iObj.getStartDate()));
		}
		if (null != iObj.getMaturityDate()
				&& !iObj.getMaturityDate().toString().trim().isEmpty()) {
			res.setMaturityDate(ddMMMyyyyDateformat.format(iObj.getMaturityDate()));
		}
		if (null != iObj.getRamId()
				&& !iObj.getRamId().trim().isEmpty()) {
			res.setRamId(iObj.getRamId());
		}
		
		boolean phyIns = iObj.getIsPhysicalInspection();
		if(phyIns)
			res.setIsPhysicalInspection("Yes");
		else
			res.setIsPhysicalInspection("No");
		
		
		if (null != iObj.getPhysicalInspectionFreqUnit()
				&& !iObj.getPhysicalInspectionFreqUnit().trim().isEmpty()) {



			 try {
				 Object obj = masterObj.getObjectByEntityName("entryCode", iObj.getPhysicalInspectionFreqUnit().trim(),"physicalInspectionFreqUnit_v1",errors,"FREQUENCY");
				 if(!(obj instanceof ActionErrors)){
					 res.setPhysicalInspectionFreq(((ICommonCodeEntry)obj).getEntryName());
					 res.setPhysicalInspectionFreqCode(((ICommonCodeEntry)obj).getEntryCode());
					}
			 }
			 catch(Exception e){
					DefaultLogger.error(this, "physicalInspectionFreqUnit_v1"+e.getMessage());
					e.printStackTrace();
				}
					    
		 
		}
		if (null != iObj.getLastPhysicalInspectDate()
				&& !iObj.getLastPhysicalInspectDate().toString().trim().isEmpty()) {
			res.setLastPhysicalInspectDate(ddMMMyyyyDateformat.format(iObj.getLastPhysicalInspectDate()));
		}
		if (null != iObj.getNextPhysicalInspectDate()
				&& !iObj.getNextPhysicalInspectDate().toString().trim().isEmpty()) {
			res.setNextPhysicalInspectDate(ddMMMyyyyDateformat.format(iObj.getNextPhysicalInspectDate()));
		}
		if (null != iObj.getGoodStatus()
				&& !iObj.getGoodStatus().trim().isEmpty()) {

			 Object obj = masterObj.getObjectByEntityName("entryCode", iObj.getGoodStatus().trim(),"goodStatus",errors,"GOODS_STATUS");
			 if(!(obj instanceof ActionErrors)){
				 res.setGoodStatus(((ICommonCodeEntry)obj).getEntryName());
				 res.setGoodStatusCode(((ICommonCodeEntry)obj).getEntryCode());
				}
		}
		if (null != iObj.getScrapValue()
				&& !iObj.getScrapValue().toString().trim().isEmpty()) {
			res.setScrapValue(Double.toString(iObj.getScrapValue().getAmount()));
		}
		if (null != iObj.getEnvRiskyStatus()
				&& !iObj.getEnvRiskyStatus().trim().isEmpty()) {

			 Object obj = masterObj.getObjectByEntityName("entryCode", iObj.getEnvRiskyStatus().trim(),"envRiskyStatus",errors,"12");
			 if(!(obj instanceof ActionErrors)){
				 res.setEnvRiskyStatus(((ICommonCodeEntry)obj).getEntryCode());
				}
			 else
			 {
				 errors.add("envRiskyStatus",new ActionMessage("error.invalid.field.value")); 
			 }
		 
		}
		if (null != iObj.getEnvRiskyDate()
				&& !iObj.getEnvRiskyDate().toString().trim().isEmpty()) {
			res.setEnvRiskyDate(ddMMMyyyyDateformat.format(iObj.getEnvRiskyDate()));
		}
		if (null != iObj.getRemarks()
				&& !iObj.getRemarks().trim().isEmpty()) {
			res.setRemarks(iObj.getRemarks());
		}
		List<ABSpecEnqDetailsResponseDTO> bodyDetails = new ArrayList<ABSpecEnqDetailsResponseDTO>();
		bodyDetails.add(res);
		colRes.setBodyDetails(bodyDetails);
		return colRes;
	}
		 
	public List getInsuranceForColObj(ABSpecEnqDetailsResponseDTO res,
			ICollateral col, String ColSubTypeId) throws ParseException {
		List<InsuranceDetailRestResponseDTO> insuranceResponseList = new ArrayList<InsuranceDetailRestResponseDTO>();
		MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();
		
		IInsurancePolicy iPol;
		IInsurancePolicy[] insurancePolicies = col.getInsurancePolicies();
		if (null != insurancePolicies && insurancePolicies.length != 0) {
			for (int i = 0; i < insurancePolicies.length; i++) {
				iPol = insurancePolicies[i];
				InsuranceDetailRestResponseDTO insRes = new InsuranceDetailRestResponseDTO();
				if ("ACTIVE".equalsIgnoreCase(iPol.getStatus())) {
					insRes.setInsurancePolicyID(
							Long.toString(iPol.getInsurancePolicyID()));
					if (null != iPol.getInsuranceStatus()
							&& !iPol.getInsuranceStatus().trim().isEmpty()) {
						insRes.setInsuranceStatus(iPol.getInsuranceStatus());
					}
					if ("RECIEVED"
							.equalsIgnoreCase(iPol.getInsuranceStatus())) {
						if (null != iPol.getPolicyNo()
								&& !iPol.getPolicyNo().trim().isEmpty()) {
							insRes.setPolicyNo(iPol.getPolicyNo());
						}
						if (null != iPol.getCoverNoteNumber() && !iPol
								.getCoverNoteNumber().trim().isEmpty()) {
							insRes.setCoverNoteNumber(
									iPol.getCoverNoteNumber());
						}
						if (null != iPol.getInsuranceCompanyName() && !iPol
								.getInsuranceCompanyName().trim().isEmpty()) {
							Object obj = masterObj.getObjectforMasterRelatedCode("actualInsuranceCoverage", iPol.getInsuranceCompanyName(),"insuranceCompanyName",errors);
							if(!(obj instanceof ActionErrors)){
								insRes.setInsuranceCompanyNameId(((IInsuranceCoverage)obj).getId()+"");
								insRes.setInsuranceCompanyName(((IInsuranceCoverage)obj).getCompanyName());
							}
						}
						if (null != iPol.getCurrencyCode()
								&& !iPol.getCurrencyCode().trim().isEmpty()) {


							Object obj = masterObj.getObjectforMasterRelatedCode(
									"actualForexFeedEntry",
									iPol.getCurrencyCode().trim(),
									"collateralCurrency", errors);
							if (!(obj instanceof ActionErrors)) {
								res.setCollateralCurrency(
										((IForexFeedEntry) obj).getCurrencyDescription().trim());
								res.setCollateralCurrencyCode(
										((IForexFeedEntry) obj).getCurrencyIsoCode());
							} 
						
						}
						if (null != iPol.getTypeOfPerils1()
								&& !iPol.getTypeOfPerils1().trim().isEmpty()) {
								 Object obj = masterObj.getObjectByEntityName("entryCode", iPol.getTypeOfPerils1().trim(),"typeOfPerils1",errors,"INSURANCE_COMPANY_CATEGORY");
								 if(!(obj instanceof ActionErrors)){
									 insRes.setTypeOfPerils1(((ICommonCodeEntry)obj).getEntryName());
									 insRes.setTypeOfPerils1Code(((ICommonCodeEntry)obj).getEntryCode());
									}
						}
						if (null != iPol.getInsurableAmount()
								&& !iPol.getInsurableAmount().toString().trim()
										.isEmpty()) {
							insRes.setInsurableAmount(
									iPol.getInsurableAmount().toString());
						}
						if (null != iPol.getInsuredAmount()
								&& !iPol.getInsuredAmount().toString().trim()
										.isEmpty()) {
							insRes.setInsuredAmount(
									iPol.getInsuredAmount().toString());
						}
						if (null != iPol.getReceivedDate()
								&& !iPol.getReceivedDate().toString().trim()
										.isEmpty()) {
							insRes.setReceivedDate(ddMMMyyyyDateformat.format(iPol.getReceivedDate()));
						
						}
						if (null != iPol.getEffectiveDate()
								&& !iPol.getEffectiveDate().toString().trim()
										.isEmpty()) {
							insRes.setEffectiveDate(ddMMMyyyyDateformat.format(iPol.getEffectiveDate()));
						}
						if (null != iPol.getInsurancePremium()
								&& !iPol.getInsurancePremium().toString().trim()
										.isEmpty()) {
							insRes.setInsurancePremium(
									iPol.getInsurancePremium().toString());
						}
						if (null != iPol.getNonSchemeScheme() && !iPol
								.getNonSchemeScheme().trim().isEmpty()) {
							insRes.setNonScheme_Scheme(
									iPol.getNonSchemeScheme());
						}
						if (null != iPol.getAddress() && !iPol.getAddress()
								.toString().trim().isEmpty()) {
							insRes.setAddress(iPol.getAddress().toString());
						}
						if (null != iPol.getNonSchemeScheme() && !iPol
								.getNonSchemeScheme().trim().isEmpty()) {
							insRes.setNonScheme_Scheme(
									iPol.getNonSchemeScheme());
						}
						if (null != iPol.getRemark1() && !iPol.getRemark1()
								.toString().trim().isEmpty()) {
							insRes.setRemark1(iPol.getRemark1());
						}
						if (null != iPol.getRemark2()
								&& !iPol.getRemark2().trim().isEmpty()) {
							insRes.setRemark2(iPol.getRemark2());
						}
						if (null != iPol.getInsuredAgainst()
								&& !iPol.getInsuredAgainst().trim().isEmpty()) {
							insRes.setInsuredAgainst(iPol.getInsuredAgainst());
						}
					} else if ("WAIVED"
							.equalsIgnoreCase(iPol.getInsuranceStatus())) {
						if (null != iPol.getWaivedDate() && !iPol
								.getWaivedDate().toString().trim().isEmpty()) {
							insRes.setWaivedDate(
									iPol.getWaivedDate().toString());
						}
						if (null != iPol.getCreditApprover()
								&& !iPol.getCreditApprover().trim().isEmpty()) {

							Object obj = masterObj.getObjectforMasterRelatedCodeWaiver("actualCreditApproval", iPol.getCreditApprover(),"creditApprover",errors);
							if(!(obj instanceof ActionErrors)){
								insRes.setCreditApprover(((ICreditApproval)obj).getApprovalName());
								insRes.setCreditApproverCode(((ICreditApproval)obj).getApprovalCode());
							}
						}
					} else if ("AWAITING"
							.equalsIgnoreCase(iPol.getInsuranceStatus())) {
						if (null != iPol.getOriginalTargetDate()
								&& !iPol.getOriginalTargetDate().toString()
										.trim().isEmpty()) {
							insRes.setOriginalTargetDate(ddMMMyyyyDateformat.format(iPol.getOriginalTargetDate()));
						}
					} else if ("DEFERRED"
							.equalsIgnoreCase(iPol.getInsuranceStatus())) {
						if (null != iPol.getOriginalTargetDate()
								&& !iPol.getOriginalTargetDate().toString()
										.trim().isEmpty()) {
							insRes.setOriginalTargetDate(ddMMMyyyyDateformat.format(iPol.getOriginalTargetDate()));
						}
						if (null != iPol.getDateDeferred()
								&& !iPol.getDateDeferred().toString().trim()
										.isEmpty()) {
							insRes.setDateDeferred(
									iPol.getDateDeferred().toString());
						}
						if (null != iPol.getNextDueDate() && !iPol
								.getNextDueDate().toString().trim().isEmpty()) {
							insRes.setNextDueDate(ddMMMyyyyDateformat.format(iPol.getNextDueDate()));
						}
						if (null != iPol.getCreditApprover()
								&& !iPol.getCreditApprover().trim().isEmpty()) {
							Object obj = masterObj.getObjectforMasterRelatedCode("actualCreditApproval", iPol.getCreditApprover(),"creditApprover",errors);
							if(!(obj instanceof ActionErrors)){
								insRes.setCreditApprover(((ICreditApproval)obj).getApprovalName());
							}
							if(!(obj instanceof ActionErrors)){
								insRes.setCreditApproverCode(((ICreditApproval)obj).getApprovalCode());
							}
							
							
						}
					}
					insuranceResponseList.add(insRes);
				}
			}
		}
		return insuranceResponseList;
	}
		 
	public  ActionErrors validateStockRequest(StockRestRequestDTO stockRestRequestDTO, ActionErrors errorCommon, String securityId) {
		
		
		
		if(null != stockRestRequestDTO.getActionFlag() && !stockRestRequestDTO.getActionFlag().trim().isEmpty())
		{
			if("A".equalsIgnoreCase(stockRestRequestDTO.getActionFlag()))
			{
				if(null != stockRestRequestDTO.getStockSecUniqueId() &&
						!stockRestRequestDTO.getStockSecUniqueId().trim().isEmpty())
				{
					boolean isUniqueIdPresent = collateralDAO.checkUniqueStockId(stockRestRequestDTO.getStockSecUniqueId().trim(),securityId);
					if(isUniqueIdPresent)
					{
						errorCommon.add("StockSecUniqueId-"+stockRestRequestDTO.getStockSecUniqueId(),new ActionMessage("StockSecUniqueId is duplicate"));
					}
					else
					{
						if(stockRestRequestDTO.getStockSecUniqueId().length() <=25)
							stockRestRequestDTO.setStockSecUniqueId(stockRestRequestDTO.getStockSecUniqueId());
						else
							errorCommon.add("StockSecUniqueId-"+stockRestRequestDTO.getStockSecUniqueId(),new ActionMessage("StockSecUniqueId length must not exced"));	
					}
				}
				else
				{
					errorCommon.add("StockSecUniqueId",new ActionMessage("StockSecUniqueId is mandatory"));
				}
			}
			
			if("U".equalsIgnoreCase(stockRestRequestDTO.getActionFlag()))
			{
				if(null != stockRestRequestDTO.getStockSecUniqueId() &&
						!stockRestRequestDTO.getStockSecUniqueId().trim().isEmpty())
				{
					boolean isUniqueIdPresent = collateralDAO.checkUniqueStockId(stockRestRequestDTO.getStockSecUniqueId().trim(),securityId);
					if(isUniqueIdPresent)
					{
						errorCommon.add("StockSecUniqueId-"+stockRestRequestDTO.getStockSecUniqueId(),new ActionMessage("StockSecUniqueId is duplicate"));
					}
					else
					{
						if(stockRestRequestDTO.getStockSecUniqueId().length() <=25)
							stockRestRequestDTO.setStockSecUniqueId(stockRestRequestDTO.getStockSecUniqueId());
						else
							errorCommon.add("StockSecUniqueId-"+stockRestRequestDTO.getStockSecUniqueId(),new ActionMessage("StockSecUniqueId length must not exced"));	
					}
				}
				else
				{
					errorCommon.add("StockSecUniqueId",new ActionMessage("StockSecUniqueId is mandatory"));
				}
				
				if(null != stockRestRequestDTO.getClimsItemId() &&
						!stockRestRequestDTO.getClimsItemId().trim().isEmpty())
				{
					if(stockRestRequestDTO.getClimsItemId().length() <=25)
						stockRestRequestDTO.setClimsItemId(stockRestRequestDTO.getClimsItemId());
					else
						errorCommon.add("climsItemId-"+stockRestRequestDTO.getClimsItemId(),new ActionMessage("climsItemId length must not exced"));
				}
				else
				{
					errorCommon.add("climsItemId",new ActionMessage("ClimsItemId is mandatory"));
				}
				
			}

		}
		else
		{
			errorCommon.add("ActionFlag",new ActionMessage("ActionFlag is mandatory"));
		}
		
		
		if(null != stockRestRequestDTO.getStockExchange() && !stockRestRequestDTO.getStockExchange().trim().isEmpty())
		{
			boolean isStockExchangePresent = collateralDAO.checkStockExchange(stockRestRequestDTO.getStockExchange().trim());
			if(isStockExchangePresent)
			{
				errorCommon.add("StockExchange-"+stockRestRequestDTO.getStockSecUniqueId(),new ActionMessage("StockSecUniqueId is duplicate"));
			}
			else
			{
				if(stockRestRequestDTO.getStockSecUniqueId().length() <=25)
					stockRestRequestDTO.setStockSecUniqueId(stockRestRequestDTO.getStockSecUniqueId());
				else
					errorCommon.add("StockSecUniqueId-"+stockRestRequestDTO.getStockSecUniqueId(),new ActionMessage("StockSecUniqueId length must not exced"));	
			}

		}
		else
		{
			errorCommon.add("StockExchange",new ActionMessage("StockExchange is mandatory"));
		}
		
		return errorCommon;
		
	}
}

