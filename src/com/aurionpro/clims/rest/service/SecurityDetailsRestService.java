package com.aurionpro.clims.rest.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aurionpro.clims.rest.common.ValidationErrorDetailsDTO;
import com.aurionpro.clims.rest.common.ValidationUtilityRest;
import com.aurionpro.clims.rest.constants.ResponseConstants;
import com.aurionpro.clims.rest.constants.WholeSaleApi_Constant;
import com.aurionpro.clims.rest.dto.ABEnquiryResponseDTO;
import com.aurionpro.clims.rest.dto.ABSpecEnqDetailsResponseDTO;
import com.aurionpro.clims.rest.dto.AddtionalDocumentFacilityDetailsResponseDTO;
import com.aurionpro.clims.rest.dto.AddtionalDocumentFacilityDetailsRestDTO;
import com.aurionpro.clims.rest.dto.BodyRestResponseDTO;
import com.aurionpro.clims.rest.dto.CollateralDelEnqDetailslRequestDTO;
import com.aurionpro.clims.rest.dto.CollateralDeleteEnquiryRestRequestDTO;
import com.aurionpro.clims.rest.dto.CollateralDetailslRestRequestDTO;
import com.aurionpro.clims.rest.dto.CollateralEnqiryDetailsResponseDTO;
import com.aurionpro.clims.rest.dto.CollateralRestRequestDTO;
import com.aurionpro.clims.rest.dto.CollateralRestResponseDTO;
import com.aurionpro.clims.rest.dto.HeaderDetailRestResponseDTO;
import com.aurionpro.clims.rest.dto.InsuranceDetailRestResponseDTO;
import com.aurionpro.clims.rest.dto.InsurancePolicyRestRequestDTO;
import com.aurionpro.clims.rest.dto.ResponseMessageDetailDTO;
import com.aurionpro.clims.rest.dto.StockDetailRestResponseDTO;
import com.aurionpro.clims.rest.dto.StockLineDetailResponseDTO;
import com.aurionpro.clims.rest.dto.StockRestRequestDTO;
import com.aurionpro.clims.rest.mapper.CollateralEnquiryDTOMapper;
import com.aurionpro.clims.rest.mapper.SecurityDetailsRestDTOMapper;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateDAOFactory;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.IDocumentDAO;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.IAddtionalDocumentFacilityDetails;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.OBInsurancePolicyColl;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.ISpecificChargeAircraft;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.OBSpecificChargeAircraft;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.OBGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.IMarketableEquityLineDetail;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedlocal.IOtherListedLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedlocal.OBOtherListedLocal;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation1;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation1Dao;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation2;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation2Dao;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation3;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation3Dao;
import com.integrosys.cms.app.collateral.bus.type.property.OBPropertyCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.PropertyValuation1;
import com.integrosys.cms.app.collateral.bus.type.property.PropertyValuation2;
import com.integrosys.cms.app.collateral.bus.type.property.PropertyValuation3;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.discrepency.bus.IDiscrepancyJdbc;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.ws.common.CLIMSWebService;
import com.integrosys.cms.app.ws.jax.common.CMSException;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.collateral.CollateralHelper;
import com.integrosys.cms.ui.collateral.CollateralRestForm;
import com.integrosys.cms.ui.collateral.CollateralValidator;
import com.integrosys.cms.ui.collateral.IInsuranceGCDao;
import com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftForm;
import com.integrosys.cms.ui.collateral.assetbased.assetaircraft.AssetAircraftValidator;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGCJdbc;
import com.integrosys.cms.ui.collateral.guarantees.gtecorp3rd.GteCorp3rdForm;
import com.integrosys.cms.ui.collateral.guarantees.gtecorp3rd.GteCorp3rdValidator;
import com.integrosys.cms.ui.collateral.guarantees.gtegovt.GteGovtForm;
import com.integrosys.cms.ui.collateral.guarantees.gtegovt.GteGovtValidator;
import com.integrosys.cms.ui.collateral.guarantees.gteindiv.GteIndivForm;
import com.integrosys.cms.ui.collateral.guarantees.gteindiv.GteIndivValidator;
import com.integrosys.cms.ui.collateral.insurancepolicy.InsurancePolicyForm;
import com.integrosys.cms.ui.collateral.insurancepolicy.InsurancePolicyValidator;
import com.integrosys.cms.ui.collateral.marketablesec.PortItemForm;
import com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator;
import com.integrosys.cms.ui.collateral.marketablesec.linedetail.MarketableEquityLineDetailForm;
import com.integrosys.cms.ui.collateral.marketablesec.linedetail.MarketableEquityLineDetailValidator;
import com.integrosys.cms.ui.collateral.property.PropertyRestForm;
import com.integrosys.cms.ui.collateral.property.PropertyValidator;



public class SecurityDetailsRestService {
	
	private boolean isMaintainChecklistWithoutApproval = true;
	@Autowired
	@Qualifier("securityDetailsRestDTOMapper")
	private SecurityDetailsRestDTOMapper securityDetailsRestDTOMapper;
	
	
	public void setSecurityDetailsRestDTOMapper(SecurityDetailsRestDTOMapper securityDetailsRestDTOMapper) {
		this.securityDetailsRestDTOMapper = securityDetailsRestDTOMapper;
	}
	
	CollateralEnquiryDTOMapper collateralEnquiryDTOMapper = new CollateralEnquiryDTOMapper();
	
	
	public void setCollateralEnquiryDTOMapper(CollateralEnquiryDTOMapper collateralEnquiryDTOMapper) {
		this.collateralEnquiryDTOMapper = collateralEnquiryDTOMapper;
	}
	
	
	
	
	public CollateralRestResponseDTO  updateSecurityDetails(CollateralRestRequestDTO collateralRestRequestDTO, String pathInfo) {
		
		
		BodyRestResponseDTO bodyObj= new  BodyRestResponseDTO();
		
		CollateralRestResponseDTO securityResponse = new CollateralRestResponseDTO();
		
		
		HeaderDetailRestResponseDTO headerObj= new  HeaderDetailRestResponseDTO();
		List<HeaderDetailRestResponseDTO> ccHeaderResponse = new LinkedList<HeaderDetailRestResponseDTO>();
	
		CollateralRestRequestDTO collateralRestReqInstance = new CollateralRestRequestDTO();	
		
		List<BodyRestResponseDTO> BodyRestResponseDTOList = new ArrayList<BodyRestResponseDTO>();
		List<ResponseMessageDetailDTO> responseMessageDetailDTOList = new ArrayList<ResponseMessageDetailDTO>();
	    
	    List<ValidationErrorDetailsDTO> validationErrorDetailsDTOList;
	    headerObj.setRequestId(collateralRestRequestDTO.getHeaderDetails().get(0).getRequestId());
		headerObj.setChannelCode(collateralRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
		ccHeaderResponse.add(headerObj);
		securityResponse.setHeaderDetails(ccHeaderResponse);
		ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
		IInsuranceGCDao insuranceGCDao = (IInsuranceGCDao) BeanHouse.get("insuranceGcDao");
		DefaultLogger.debug(this, "inside updateSecurityDetails");
		
		CollateralDetailslRestRequestDTO securityRequest = collateralRestRequestDTO.getBodyDetails().get(0);
		String securityId = "";
		
		if(securityRequest.getSecurityId()!=null && !securityRequest.getSecurityId().trim().isEmpty()){
			if(ASSTValidator.isNumeric(securityRequest.getSecurityId()))
			{
			securityId = securityRequest.getSecurityId().trim();
			boolean securitypresent = collateralDAO.checkSecurityId(securityId);
				if(securityId.length() > 19)
				{
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(ResponseConstants.SEC_ID_LEN);
					rmd.setResponseMessage(ResponseConstants.SEC_ID_LEN_MESSAGE);
					responseMessageDetailDTOList.add(rmd);
				}
				
				if(!securitypresent)
				{
					 	ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					    rmd.setResponseCode(ResponseConstants.SEC_STATUS_INVALID);
					    rmd.setResponseMessage(ResponseConstants.SEC_STATUS_INVALID_MESSAGE);
					    responseMessageDetailDTOList.add(rmd);
				}
				
				if(securitypresent && !isValidRequest(pathInfo,securityId,collateralDAO)) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
				    rmd.setResponseCode(ResponseConstants.SEC_ID_PATH_INVALID);
				    rmd.setResponseMessage(ResponseConstants.SEC_ID_PATH_INVALID_MESSAGE);
				    responseMessageDetailDTOList.add(rmd);
				}
				
			}
			else
			{
				ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
			    rmd.setResponseCode(ResponseConstants.SEC_ID_NUM);
			    rmd.setResponseMessage(ResponseConstants.SEC_ID_NUM_MESSAGE);
			    responseMessageDetailDTOList.add(rmd);}
		}
		else
		{
			ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
		    rmd.setResponseCode(ResponseConstants.SEC_MISSING);
		    rmd.setResponseMessage(ResponseConstants.SEC_MISSING_MESSAGE);
		    responseMessageDetailDTOList.add(rmd);
		}

		
		
		if(responseMessageDetailDTOList.size() > 0) {
			bodyObj.setSecurityId(securityRequest.getSecurityId());
			bodyObj.setResponseStatus("FAILED");
			bodyObj.setResponseMessageList(responseMessageDetailDTOList);
		    BodyRestResponseDTOList.add(bodyObj);
		    securityResponse.setBodyDetails(BodyRestResponseDTOList);	
			return securityResponse;
		}
		
		
		String ColSubTypeId = collateralDAO.getSubtypeCodeFromSecurity(securityId);
		ICollateralTrxValue itrxValue = new OBCollateralTrxValue();
		OBTrxContext trxContext  = new OBTrxContext();
		try {
			itrxValue = CollateralProxyFactory.getProxy().getCollateralTrxValue(trxContext, Long.parseLong(securityId));
		} catch (CollateralException e1) {
			e1.printStackTrace();
		}
		
	
		String deferralIds = "";
		if("PT701".equalsIgnoreCase(ColSubTypeId))
		{
		String subProfile= collateralDAO.getSubProfileIdFromSecurityId(securityId);
		IDiscrepancyJdbc discrepencyJdbc = (IDiscrepancyJdbc) BeanHouse.get("discrepencyJdbc");
		
		List<Long> deferralList = discrepencyJdbc.getDeferralIdsForValuation2(Long.parseLong(subProfile));
	
		StringBuffer deferralBuff = new StringBuffer("");
		for(int i = 0; i < deferralList.size(); i++) {
			DefaultLogger.debug(this, "deferralList.get(i):"+deferralList.get(i));
			deferralBuff.append(deferralList.get(i));
			if(i < (deferralList.size() - 1)) {
				deferralBuff.append(",");
			}
		}
		deferralIds = deferralBuff.toString();
		}
		 boolean isInsurance = false;
		 String cersai="",collateralCategory ="", insurance="";
		 
		 String[]  cersaiAndCat = collateralDAO.getColCatAndCersaiFlag(securityId);
		 
		 
			if(null != cersaiAndCat && cersaiAndCat.length > 0)
			{
				collateralCategory=cersaiAndCat[0] ;
				cersai=cersaiAndCat[1] ;
				insurance= cersaiAndCat[2] ;
			}
			
			 if(null != insurance && "on".equalsIgnoreCase(insurance))
				 isInsurance=true;
		collateralRestReqInstance= securityDetailsRestDTOMapper.getRequestDTOWithActualValues(collateralRestRequestDTO,collateralCategory,cersai,ColSubTypeId,itrxValue);
		ActionErrors insurancePolicyError = null;
		ActionErrors requestErrors = collateralRestReqInstance.getBodyDetails().get(0).getErrors();
		HashMap map = new HashMap();
		if(requestErrors.size()>0){
			map.put("1", requestErrors);
			validationErrorDetailsDTOList = ValidationUtilityRest.handleError(map, CLIMSWebService.UPDATE_SECURITY);
			for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
			    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
			    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
			    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
			    responseMessageDetailDTOList.add(rmd);
			}
		}
		List<InsurancePolicyRestRequestDTO> insListNew = new ArrayList<InsurancePolicyRestRequestDTO>();
		if(isInsurance)
		{
		 List<InsurancePolicyRestRequestDTO> insList = collateralRestRequestDTO.getBodyDetails().get(0).getInsurancePolicyList();
		 //List<InsurancePolicyRestRequestDTO> insListNew = new ArrayList<InsurancePolicyRestRequestDTO>();
		 HashMap insPlcyMap = new HashMap();
			   if(null != insList && !insList.isEmpty()) {
			    for (int i = 0; i < insList.size(); i++) {
			    	ActionErrors insuranceErrorListCps = new ActionErrors();
			    	InsurancePolicyRestRequestDTO insurancePolicyRestRequestDTO = securityDetailsRestDTOMapper.getInsuranceRestFromReq(insList.get(i),securityId);
			    	String unqSecurityId = ""; 
			    	insuranceErrorListCps = insurancePolicyRestRequestDTO.getErrors();
			    	if(null != insuranceErrorListCps)
			    	insPlcyMap.put(i+unqSecurityId, insuranceErrorListCps);
			    	insListNew.add(insurancePolicyRestRequestDTO);
			    	
				}
				if(null != insPlcyMap)
				{
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(insPlcyMap, CLIMSWebService.UPDATE_SECURITY);
				for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
				    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
				    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
				    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
				    responseMessageDetailDTOList.add(rmd);
				}
				}
				if(responseMessageDetailDTOList.size() > 0) {
					bodyObj.setSecurityId(securityId);
					bodyObj.setResponseStatus("FAILED");
					bodyObj.setResponseMessageList(responseMessageDetailDTOList);
				    BodyRestResponseDTOList.add(bodyObj);
				    securityResponse.setBodyDetails(BodyRestResponseDTOList);
					return securityResponse;
				}
			    }
			   collateralRestRequestDTO.getBodyDetails().get(0).setInsurancePolicyList(insListNew);
			   HashMap insuranceMap = new HashMap();
			   boolean validStatus= false;
			   if(null != insListNew && !insListNew.isEmpty()) 
			   {
				    for (int i = 0; i < insListNew.size(); i++) {
				    	InsurancePolicyRestRequestDTO insRestRequestDTO	= insListNew.get(i);
					if ("PENDING_DEFER".equalsIgnoreCase(
							insRestRequestDTO.getInsuranceStatus())
							|| "PENDING_WAIVER".equalsIgnoreCase(
									insRestRequestDTO.getInsuranceStatus())
							|| "AWAITING".equalsIgnoreCase(
									insRestRequestDTO.getInsuranceStatus())
							|| "UPDATE_RECEIVED".equalsIgnoreCase(
									insRestRequestDTO.getInsuranceStatus())
							|| "PENDING_RECEIVED".equalsIgnoreCase(
									insRestRequestDTO.getInsuranceStatus()))
						validStatus = true;
					InsurancePolicyForm InsPolicyForm = null;
					if(validStatus)
					{
					InsPolicyForm = securityDetailsRestDTOMapper.getInsuranceFormFromReq(insListNew.get(i));
				    	//ActionErrors insurancePolicyError = new ActionErrors();
				    	insurancePolicyError = InsurancePolicyValidator.validateInput(InsPolicyForm,Locale.getDefault());
				    	String unqSecurityId = ""; 
				    	insuranceMap.put(i+unqSecurityId, insurancePolicyError);
					}
					}
			   }
		
	
		if(null != insurancePolicyError) {
		validationErrorDetailsDTOList = ValidationUtilityRest.handleError(insuranceMap, CLIMSWebService.UPDATE_SECURITY);
		for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
		    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
		    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
		    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
		    responseMessageDetailDTOList.add(rmd);
		}
		}
		
		}
		
		
		if(responseMessageDetailDTOList.size() > 0) {
			bodyObj.setSecurityId(securityId);
			bodyObj.setResponseStatus("FAILED");
			bodyObj.setResponseMessageList(responseMessageDetailDTOList);
		    BodyRestResponseDTOList.add(bodyObj);
		    securityResponse.setBodyDetails(BodyRestResponseDTOList);
			return securityResponse;
		}
		CollateralRestForm colForm = (CollateralRestForm) securityDetailsRestDTOMapper.getFormFromDTORest(collateralRestReqInstance,ColSubTypeId);
		ActionErrors errorLists =CollateralValidator.validateInput(colForm, Locale.getDefault()); 
		HashMap newMap1 = new HashMap();		
		if(errorLists.size()>0){
			newMap1.put("1", errorLists);
			validationErrorDetailsDTOList = ValidationUtilityRest.handleError(newMap1,CLIMSWebService.UPDATE_SECURITY);
			for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
			    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
			    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
			    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
			    responseMessageDetailDTOList.add(rmd);
			}
		}
		
		if ("AB109".equalsIgnoreCase(ColSubTypeId))
		{
		AssetAircraftForm form = securityDetailsRestDTOMapper.getAssetBasedFormUsingReq(collateralRestRequestDTO);
		form.setEvent("REST_UPDATE_AB_SA_SECURITY");
		ActionErrors errorList = AssetAircraftValidator.validateInput((AssetAircraftForm) form, Locale.getDefault());
		HashMap newMap = new HashMap();		
		if(errorList.size()>0){
			newMap.put("1", errorList);
			validationErrorDetailsDTOList = ValidationUtilityRest.handleError(newMap,CLIMSWebService.UPDATE_SECURITY);
			for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
			    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
			    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
			    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
			    responseMessageDetailDTOList.add(rmd);
			}
		}
		
		}
		else if ("GT405".equalsIgnoreCase(ColSubTypeId)) {
			GteCorp3rdForm form = (GteCorp3rdForm) securityDetailsRestDTOMapper.getFormFromDTORestForCorporateGuarantee(collateralRestRequestDTO);
			form.setEvent("REST_UPDATE_CORP_GUARANTEES_SECURITY");
			ActionErrors errorList = GteCorp3rdValidator.validateInput((GteCorp3rdForm) form, Locale.getDefault());
			HashMap newMap = new HashMap();
			if (errorList.size() > 0) {
				newMap.put("1", errorList);
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(newMap, CLIMSWebService.UPDATE_SECURITY);
				for (ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
					rmd.setResponseMessage(validationErrorDetailsDTO.getField());
					responseMessageDetailDTOList.add(rmd);
				}
			}

		} else if ("GT408".equalsIgnoreCase(ColSubTypeId)) {
			GteIndivForm form = (GteIndivForm) securityDetailsRestDTOMapper.getFormFromDTORestForIndividualGuarantee(collateralRestRequestDTO);
			form.setEvent("REST_UPDATE_INDIV_GUARANTEES_SECURITY");
			ActionErrors errorList = GteIndivValidator.validateInput((GteIndivForm) form, Locale.getDefault());
			HashMap newMap = new HashMap();
			if (errorList.size() > 0) {
				newMap.put("1", errorList);
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(newMap, CLIMSWebService.UPDATE_SECURITY);
				for (ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
					rmd.setResponseMessage(validationErrorDetailsDTO.getField());
					responseMessageDetailDTOList.add(rmd);
				}
			}

		} else if ("GT406".equalsIgnoreCase(ColSubTypeId)) {
			GteGovtForm form = (GteGovtForm) securityDetailsRestDTOMapper.getFormFromDTORestForGovernmentGuarantee(collateralRestRequestDTO);
			form.setEvent("REST_UPDATE_GOVT_GUARANTEES_SECURITY");
			ActionErrors errorList = GteGovtValidator.validateInput((GteGovtForm) form, Locale.getDefault());
			HashMap newMap = new HashMap();
			if (errorList.size() > 0) {
				newMap.put("1", errorList);
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(newMap, CLIMSWebService.UPDATE_SECURITY);
				for (ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
					rmd.setResponseMessage(validationErrorDetailsDTO.getField());
					responseMessageDetailDTOList.add(rmd);
				}
			}
		} else if ("PT701".equalsIgnoreCase(ColSubTypeId)) {
			PropertyRestForm propForm= (PropertyRestForm) securityDetailsRestDTOMapper.getPropFormUsingReq(collateralRestRequestDTO,deferralIds);
			propForm.setEvent("REST_UPDATE_PROPERTY_SECURITY");
			ActionErrors errorList = PropertyValidator.validateInput(propForm, Locale.getDefault());
			HashMap newMap = new HashMap();
			if (errorList.size() > 0) {
				newMap.put("1", errorList);
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(newMap, CLIMSWebService.UPDATE_SECURITY);
				for (ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
					rmd.setResponseMessage(validationErrorDetailsDTO.getField());
					responseMessageDetailDTOList.add(rmd);
				}
			}
			String isAdDocFac = collateralRestRequestDTO.getBodyDetails().get(0).getPropertyDetailsList().get(0).getIsAdDocFac();
			if(null != isAdDocFac && "Y".equalsIgnoreCase(isAdDocFac))
			{
			List<AddtionalDocumentFacilityDetailsRestDTO> adDocFacDetList = collateralRestRequestDTO.getBodyDetails().get(0).getPropertyDetailsList().get(0).getAddDocFacDetailsList();
				 HashMap addocFacMap = new HashMap();
				   if(null != adDocFacDetList && !adDocFacDetList.isEmpty()) {
				    for (int i = 0; i < adDocFacDetList.size(); i++) {
				    	ActionErrors errorListCps = new ActionErrors();
				    	AddtionalDocumentFacilityDetailsRestDTO addDocFacDetReq = securityDetailsRestDTOMapper.getAddDocFacDetReq(adDocFacDetList.get(i),securityId);
				    	String unqSecurityId = ""; 
				    	errorListCps = addDocFacDetReq.getErrors();
				    	if(null != errorListCps)
				    		addocFacMap.put(i+unqSecurityId, errorListCps);
					}
				    if(null != addocFacMap && !addocFacMap.isEmpty())
					   {
						   validationErrorDetailsDTOList = ValidationUtilityRest.handleError(addocFacMap, CLIMSWebService.UPDATE_SECURITY);
							for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
							    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
							    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
							    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
							    responseMessageDetailDTOList.add(rmd);
					   }
				    }
				   }
			}
		}else if ("MS605".equalsIgnoreCase(ColSubTypeId))
		{

			 List<StockRestRequestDTO> stockDetailsList = collateralRestRequestDTO.getBodyDetails().get(0).getStockDetailsList(); 
			if( null != stockDetailsList && !stockDetailsList.isEmpty() 
					&& stockDetailsList.size()>0 )
			{
				HashMap newMap = new HashMap();
				ActionErrors errorCommon =new ActionErrors();
				for (int i = 0;i<stockDetailsList.size();i++)
				{

					//errorCommon = securityDetailsRestDTOMapper.validateStockRequest(stockDetailsList.get(i),errorCommon,securityId);
					

					//if(errorCommon.size() <= 0){
						PortItemForm form = (PortItemForm) securityDetailsRestDTOMapper.getFormFromDTORestStock(stockDetailsList.get(i));
						form.setEvent("REST_UPDATE_MARKETABLE_SECURITY");
						ActionErrors errorList = PortItemValidator.validateInput((PortItemForm) form, Locale.getDefault());


						if(errorList.size()>0){
							newMap.put("1", errorList);

						}
						if( stockDetailsList.get(i).getLineDetailsList()!=null
								&& !stockDetailsList.get(i).getLineDetailsList().isEmpty() 
								&& stockDetailsList.get(i).getLineDetailsList().size()>0 )
						{
							HashMap newMapLine = new HashMap();
							ActionErrors errorCommonline =new ActionErrors();

							for (int l = 0;l<stockDetailsList.get(i).getLineDetailsList().size();l++)
							{

								
								//if(errorCommonline.size() <= 0){
									MarketableEquityLineDetailForm formLine = (MarketableEquityLineDetailForm) securityDetailsRestDTOMapper.getFormFromDTORestStockLine(stockDetailsList.get(i).getLineDetailsList().get(l));
									formLine.setEvent("REST_UPDATE_MARKETABLE_LINE_SECURITY");
									ActionErrors errorListLine = MarketableEquityLineDetailValidator.validateInput((MarketableEquityLineDetailForm) formLine, Locale.getDefault());
									if(errorListLine.size()>0){
										newMapLine.put("1", errorListLine);

									}
								/*}
								else
								{
									newMapLine.put("1", errorCommonline);
								}*/
							}

							validationErrorDetailsDTOList = ValidationUtilityRest.handleError(newMapLine,CLIMSWebService.UPDATE_SECURITY);
							for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
								ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
								rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
								rmd.setResponseMessage(validationErrorDetailsDTO.getField());
								responseMessageDetailDTOList.add(rmd);
							}

						}

					/*}
					else
					{
						newMap.put("1", errorCommon);
					}*/
				}
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(newMap,CLIMSWebService.UPDATE_SECURITY);
				for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
					rmd.setResponseMessage(validationErrorDetailsDTO.getField());
					responseMessageDetailDTOList.add(rmd);
				}
			}

		}
		
		if(responseMessageDetailDTOList.size() > 0) {
			bodyObj.setSecurityId(securityId);
			bodyObj.setResponseStatus("FAILED");
			bodyObj.setResponseMessageList(responseMessageDetailDTOList);
		    BodyRestResponseDTOList.add(bodyObj);
		    securityResponse.setBodyDetails(BodyRestResponseDTOList);	
			return securityResponse;
		}
		
		List<ResponseMessageDetailDTO> responseMessageList = new LinkedList<ResponseMessageDetailDTO>();
		ICollateralProxy proxy= CollateralProxyFactory.getProxy(); 
		
		ICollateralTrxValue itrxResult = new OBCollateralTrxValue();
		
	
		
		String colId =collateralRestRequestDTO.getBodyDetails().get(0).getSecurityId();
		Long colID=Long.parseLong(colId);
		ICollateral col = null;
		try {
			 col = CollateralProxyFactory.getProxy().getCollateral(colID, true);
		
		ICollateral colNew = new OBCollateral();
		
		
		if(col instanceof OBSpecificChargeAircraft)
		{
			colNew = (ISpecificChargeAircraft) col;
		}
		else if(col instanceof OBPropertyCollateral)
		{
			colNew = (IPropertyCollateral) col ; 
		}
		else if(col instanceof OBGuaranteeCollateral)
		{
			colNew = (IGuaranteeCollateral) col ; 
		}
		else if(col instanceof OBOtherListedLocal)
		{
			colNew =  (IOtherListedLocal) col ; 
		}
		
		if(col != null)
		{
			try {
				colNew = (ICollateral) securityDetailsRestDTOMapper.getActualFromDTORest(collateralRestReqInstance, col,ColSubTypeId,isInsurance);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	
		itrxValue = CollateralProxyFactory.getProxy().getCollateralTrxValue(trxContext, colID);
			
		if(null != itrxValue)
		{
			//String liquidationIsNPL = CollateralProxyFactory.getProxy().getLiquidationIsNPL(collateralId);
			if (ICMSConstant.STATE_PENDING_PERFECTION.equals(itrxValue.getStatus())
					&& (ICMSConstant.STATE_PENDING_CREATE.equals(itrxValue.getFromState()) || ICMSConstant.PENDING_UPDATE
							.equals(itrxValue.getFromState()))) {
				if (itrxValue.getCollateral() != null
						&& itrxValue.getCollateral().getCMV() != null
						&& (itrxValue.getStagingCollateral().getCMV() == null || itrxValue.getStagingCollateral()
								.getCMV().getAmount() <= 0)) {
					itrxValue.getStagingCollateral().setCMV(itrxValue.getCollateral().getCMV());
				}
			}
			if ((!itrxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE))) {
				ResponseMessageDetailDTO responseMessageDetailDTO =new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode("SEC0003");
				responseMessageDetailDTO.setResponseMessage("Cannot update security, Kindly check status");
				responseMessageList.add(responseMessageDetailDTO);
				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);
				BodyRestResponseDTOList.add(bodyObj);
				/*headerObj.setRequestId(collateralRestRequestDTO.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(collateralRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
				ccHeaderResponse.add(headerObj);
				securityResponse.setHeaderDetails(ccHeaderResponse);*/
				securityResponse.setBodyDetails(BodyRestResponseDTOList);
				return securityResponse;
			}
			List<InsuranceDetailRestResponseDTO> insuranceResponseList =new ArrayList<InsuranceDetailRestResponseDTO>();
			List<AddtionalDocumentFacilityDetailsResponseDTO> addDocRespList =new ArrayList<AddtionalDocumentFacilityDetailsResponseDTO>();
			if((itrxValue.getCollateral()) instanceof OBSpecificChargeAircraft)
			{
					IInsurancePolicy[] insurancePlantList = colNew.getInsurancePolicies();
					
					//IInsurancePolicy[] insurancePlantList2=insurancePlantList;
					List<IInsurancePolicy> iInsurancePolicy=new ArrayList<IInsurancePolicy>();
					
					
					for(int i=0;i<insurancePlantList.length;i++){
						
						if("PENDING_RECEIVED".equals(insurancePlantList[i].getInsuranceStatus())){
							//OBInsurancePolicyColl  existingOBInsurancePolicy=(OBInsurancePolicyColl) insurancePlantList[i];
							
							OBInsurancePolicyColl newOBInsurancePolicy= new OBInsurancePolicyColl();
							newOBInsurancePolicy.setOriginalTargetDate(insurancePlantList[i].getExpiryDate());
							newOBInsurancePolicy.setLastApproveBy(insurancePlantList[i].getLastApproveBy());
							newOBInsurancePolicy.setLastApproveOn(insurancePlantList[i].getLastApproveOn());
							newOBInsurancePolicy.setLastUpdatedBy(insurancePlantList[i].getLastUpdatedBy());
							newOBInsurancePolicy.setLastUpdatedOn(insurancePlantList[i].getLastUpdatedOn());
						/*	newOBInsurancePolicy.setParentId(insurancePlantList[i].getParentId());
							*/
							
							//newOBInsurancePolicy.set
							newOBInsurancePolicy.setStatus("ACTIVE");
							newOBInsurancePolicy.setCmsCollateralId(itrxValue.getStagingCollateral().getCollateralID());
							newOBInsurancePolicy.setInsuranceStatus(ICMSConstant.STATE_ITEM_AWAITING);
							
							IInsurancePolicy createInsurancePolicy = insuranceGCDao.createAndUpdateInsurancePolicy("stageInsurancepolicy",newOBInsurancePolicy);
							
							iInsurancePolicy.add(createInsurancePolicy);
							
						}				
						if("PENDING_RECEIVED".equals(insurancePlantList[i].getInsuranceStatus()) || "UPDATE_RECEIVED".equals(insurancePlantList[i].getInsuranceStatus())){
							insurancePlantList[i].setInsuranceStatus(ICMSConstant.STATE_ITEM_RECEIVED);
						}
						else if(ICMSConstant.STATE_ITEM_PENDING_WAIVER.equals(insurancePlantList[i].getInsuranceStatus())){
							insurancePlantList[i].setInsuranceStatus(ICMSConstant.STATE_ITEM_WAIVED);
						}
						else if(ICMSConstant.STATE_ITEM_PENDING_DEFERRAL.equals(insurancePlantList[i].getInsuranceStatus())){
							insurancePlantList[i].setInsuranceStatus(ICMSConstant.STATE_ITEM_DEFERRED);
						}
						else if(ICMSConstant.STATE_ITEM_PENDING_UPDATE.equals(insurancePlantList[i].getInsuranceStatus())){
							insurancePlantList[i].setInsuranceStatus(ICMSConstant.STATE_ITEM_AWAITING);
						}else{
							insurancePlantList[i].setInsuranceStatus(insurancePlantList[i].getInsuranceStatus());
						}
						iInsurancePolicy.add(insurancePlantList[i]);
						/*InsuranceDetailRestResponseDTO insuranceDetailRestResponseDTO= new InsuranceDetailRestResponseDTO();
						insuranceDetailRestResponseDTO.setInsurancePolicyID(Long.toString(insurancePlantList[i].getInsurancePolicyID()));
						insuranceDetailRestResponseDTO.setInsuranceUniqueID(insurancePlantList[i].getUniqueInsuranceId());
						insuranceResponseList.add(insuranceDetailRestResponseDTO);*/
					}
					//itrxValue.getStagingCollateral().setInsurancePolicies(insurancePlantList);
					
					IInsurancePolicy[] iInsurancePolicyTemp = new IInsurancePolicy[iInsurancePolicy.size()];
					itrxValue.getStagingCollateral().setInsurancePolicies(iInsurancePolicy.toArray(iInsurancePolicyTemp));
					
					
					
					IAddtionalDocumentFacilityDetails[] addDocFacDetPlantList = itrxValue.getStagingCollateral().getAdditonalDocFacDetails();
					
					List<IAddtionalDocumentFacilityDetails> iaddDocFacDetList=new ArrayList();
					
					
					for(int i=0;i<addDocFacDetPlantList.length;i++){
						if("PENDING_EDIT".equals(addDocFacDetPlantList[i].getAddFacDocStatus()) 
								|| "PENDING_UPDATE".equals(addDocFacDetPlantList[i].getAddFacDocStatus())){
							addDocFacDetPlantList[i].setAddFacDocStatus("SUCCESS");
						}
						iaddDocFacDetList.add(addDocFacDetPlantList[i]);
					}
					
					IAddtionalDocumentFacilityDetails[] iAddDocFacDetTemp = new IAddtionalDocumentFacilityDetails[iaddDocFacDetList.size()];
					itrxValue.getStagingCollateral().setAdditonalDocFacDetails(iaddDocFacDetList.toArray(iAddDocFacDetTemp));
				}
			else if (colNew instanceof OBPropertyCollateral)
			{
				IPropertyCollateral prop=(IPropertyCollateral)colNew;
				System.out.println(" getMortageRegisteredRef in prop obj ->L744"+prop.getMortageRegisteredRef());
			}
			itrxResult = proxy.updateCollateralWithApprovalThroughREST(trxContext, itrxValue, colNew);
			if(itrxResult != null){
				String secType = "O";
				String secSubType = "O";
				IInsurancePolicy[] insurancePolicies;
				boolean no_template;
				
				insurancePolicies = itrxResult.getCollateral().getInsurancePolicies();
				
				IAddtionalDocumentFacilityDetails[] addDocFacDet;
				
				addDocFacDet=itrxResult.getCollateral().getAdditonalDocFacDetails();
				if(null != addDocFacDet && addDocFacDet.length > 0)
				{
				for (int j = 0; j < addDocFacDet.length; j++) {
					AddtionalDocumentFacilityDetailsResponseDTO addDocFacResp= new AddtionalDocumentFacilityDetailsResponseDTO();
					addDocFacResp.setAddDocFacDetID(Long.toString(addDocFacDet[j].getAddDocFacDetID()));
					addDocFacResp.setUniqueAddDocFacDetID(addDocFacDet[j].getUniqueAddDocFacDetID());
					addDocRespList.add(addDocFacResp);
				}
				}
				if(null != insurancePolicies && insurancePolicies.length > 0)
				{
				for (int j = 0; j < insurancePolicies.length; j++) {
					InsuranceDetailRestResponseDTO insuranceDetailRestResponseDTO= new InsuranceDetailRestResponseDTO();
					insuranceDetailRestResponseDTO.setInsurancePolicyID(Long.toString(insurancePolicies[j].getInsurancePolicyID()));
					insuranceDetailRestResponseDTO.setInsuranceUniqueID(insurancePolicies[j].getUniqueInsuranceId());
					insuranceResponseList.add(insuranceDetailRestResponseDTO);
				}
				}
				
				if (itrxValue.getCollateral() instanceof IPropertyCollateral) {
					IPropertyCollateral iPropertyCollateral=(IPropertyCollateral)itrxResult.getCollateral();
					IPropertyValuation1Dao iPropertyValuation1Dao = (IPropertyValuation1Dao)BeanHouse.get("propertyValuation1Dao");
					System.out.println("SecurityDetailsRestService.java>> Line number 776>>iPropertyValuation1Dao");
					DefaultLogger.debug(this, "iPropertyValuation1Dao:"+iPropertyValuation1Dao);
					//Update or create valuation entry if exist
				//	if(!(null==iPropertyCollateral.getValuationDate_v1() || "".equals(iPropertyCollateral.getValuationDate_v1()))) {
					if( (null==iPropertyCollateral.getVal1_id() || "".equals(iPropertyCollateral.getVal1_id()))) {
						//If valuation1 not present then create
						PropertyValuation1 valuation1=setValuation1(iPropertyCollateral,"Create");
						IPropertyValuation1 createObj=iPropertyValuation1Dao.createPropertyValuation1(valuation1);
						//update id into main and stage table.
						collateralDAO.updateLatestValId(""+iPropertyCollateral.getCollateralID(), ""+createObj.getId(), "VAL1_ID");
						
					}else {
							//updating all property currently
						PropertyValuation1 valuation1=setValuation1(iPropertyCollateral,"Update");
						iPropertyValuation1Dao.updatePropertyValuation1(valuation1);	
					}
					
					IPropertyValuation2Dao iPropertyValuation2Dao = (IPropertyValuation2Dao)BeanHouse.get("propertyValuation2Dao");
					
					if(null==iPropertyCollateral.getVal2_id() || "".equals(iPropertyCollateral.getVal2_id())) {
						if (null!=iPropertyCollateral.getValuationDate_v2()) {
						PropertyValuation2 valuation2= setValuation2(iPropertyCollateral,"Create");
						IPropertyValuation2 createObj = iPropertyValuation2Dao.createPropertyValuation2(valuation2);
						collateralDAO.updateLatestValId(""+iPropertyCollateral.getCollateralID(), ""+createObj.getId(), "VAL2_ID");
						}
					}else {
						PropertyValuation2 valuation2=setValuation2(iPropertyCollateral,"Update");
						iPropertyValuation2Dao.updatePropertyValuation2(valuation2);	
					}
					
					//valuation 3 update or create start
					
					IPropertyValuation3Dao iPropertyValuation3Dao = (IPropertyValuation3Dao)BeanHouse.get("propertyValuation3Dao");
					
					DefaultLogger.debug(this, "iPropertyValuation3Dao:"+iPropertyValuation3Dao);
					//Update or create valuation entry if exist
				//	if(!(null==iPropertyCollateral.getValuationDate_v3() || "".equals(iPropertyCollateral.getValuationDate_v3()))) {
					if( (null==iPropertyCollateral.getVal3_id() || "".equals(iPropertyCollateral.getVal3_id()))) {
						if (null!=iPropertyCollateral.getValuationDate_v3()) {
						//If valuation3 not present then create
						PropertyValuation3 valuation3=setValuation3(iPropertyCollateral,"Create");
						IPropertyValuation3 createObj=iPropertyValuation3Dao.createPropertyValuation3(valuation3);
						//update id into main and stage table.
						collateralDAO.updateLatestValId(""+iPropertyCollateral.getCollateralID(), ""+createObj.getId(), "VAL3_ID");
						}
					}else {
							//updating all property currently
						PropertyValuation3 valuation3=setValuation3(iPropertyCollateral,"Update");
						iPropertyValuation3Dao.updatePropertyValuation3(valuation3);	
					}	
					
					//valuation 3 update or start end
					
					//String valuationNo = CollateralHelper.findCorrectValuation(iPropertyCollateral);
					//String valuationNo = CollateralHelper.findCorrectValuationNew(iPropertyCollateral);
					
					boolean isWaiverOrDeferral = (iPropertyCollateral.getWaiver() != null &&  iPropertyCollateral.getWaiver().trim().equals("on")) ||
							(iPropertyCollateral.getDeferral() != null &&  iPropertyCollateral.getDeferral().trim().equals("on"));
					String maxVersion="";
					if(isWaiverOrDeferral) {
						 maxVersion = collateralDAO.getVersion(iPropertyCollateral.getCollateralID(),13);	
					}else {
						 maxVersion = collateralDAO.getVersion(iPropertyCollateral.getCollateralID(),123);	
					}
					
					String valuationNo = CollateralHelper.findCorrectValuationNew(iPropertyCollateral,maxVersion,isWaiverOrDeferral);
				
					System.out.println("SecurityDetailsRestService.java>> Line number 843>>valuationNo>>>"+valuationNo+".....iPropertyCollateral.getCollateralID()>>>"+iPropertyCollateral.getCollateralID()+"...iPropertyCollateral.getMortageRegisteredRef()>>>"+iPropertyCollateral.getMortageRegisteredRef()+"....iPropertyCollateral.getDevGrpCo()>>>>"+iPropertyCollateral.getDevGrpCo());
					if(!valuationNo.isEmpty()) {
						System.out.println("SecurityDetailsRestService.java>> Line number 845>>valuationNo>>>"+valuationNo+"......iPropertyCollateral.getMortageRegisteredRef()>>>"+iPropertyCollateral.getMortageRegisteredRef()+"....iPropertyCollateral.getDevGrpCo()>>>>"+iPropertyCollateral.getDevGrpCo());
						
					int ret = collateralDAO.syncPropertyValuation(iPropertyCollateral.getCollateralID(), valuationNo, true);
					System.out.println("SecurityDetailsRestService.java>> Line number 848>>valuationNo>>>"+valuationNo+"...ret>>"+ret+"...iPropertyCollateral.getMortageRegisteredRef()>>>"+iPropertyCollateral.getMortageRegisteredRef()+"....iPropertyCollateral.getDevGrpCo()>>>>"+iPropertyCollateral.getDevGrpCo());
					
					if(ret > 0) {
						System.out.println("SecurityDetailsRestService.java>> Line number 851>>valuationNo>>>"+valuationNo+"...ret is greater than 0 >>"+ret+"...iPropertyCollateral.getMortageRegisteredRef()>>>"+iPropertyCollateral.getMortageRegisteredRef()+"....iPropertyCollateral.getDevGrpCo()>>>>"+iPropertyCollateral.getDevGrpCo());
						
						collateralDAO.syncPropertyValuation(iPropertyCollateral.getCollateralID(), valuationNo, false);
					}
					}
					System.out.println("SecurityDetailsRestService.java>> Line number 856>>valuationNo>>>"+valuationNo+"...iPropertyCollateral.getMortageRegisteredRef()>>>"+iPropertyCollateral.getMortageRegisteredRef()+"....iPropertyCollateral.getDevGrpCo()>>>>"+iPropertyCollateral.getDevGrpCo());
					
					//update/insert into table cms_property_mortgage_det for previous mortgage date.
					if(null!= iPropertyCollateral.getSalePurchaseDate()) {
					Date salePurchaseDate = iPropertyCollateral.getSalePurchaseDate();
					String str=DateUtil.formatDate("dd-MMM-yy",salePurchaseDate);
					DefaultLogger.debug(this,"str:"+str+" to caps:"+str.toUpperCase());
					
					int count=collateralDAO.CheckPreviousMortData(iPropertyCollateral.getCollateralID(),str.toUpperCase());
					if(count >0) {
						collateralDAO.updatePreviousMortagageData(iPropertyCollateral);
					}else {
						//if not present then only create the same.
						String id=new SimpleDateFormat("yyyyMMdd").format(new Date())+String.format("%09d", collateralDAO.getNextSequnceNumber("CMS_PROP_MORTGAGE_DET_SEQ"));
						collateralDAO.createPreviuosMortgageData(iPropertyCollateral, id);
			
					 }
					}
					//make the flag false
				/*	if("true".equals(iPropertyCollateral.getMortgageCreationAdd()) || "preMortDdTrue".equals(iPropertyCollateral.getMortgageCreationAdd())){*/
					if("preMortDdTrue".equals(iPropertyCollateral.getMortgageCreationAdd())){
					 collateralDAO.updateLatestValId(""+iPropertyCollateral.getCollateralID(), "true", "MORTGAGECREATIONADD");	
					}
				}
				
				ICheckListTrxValue checkListTrxVal = null;
				String custCategory = "MAIN_BORROWER";
				String applicationType = "COM";
				long collateralID = 0L;
				
				//ICollateralCheckListOwner owner = (OBCollateralCheckListOwner) map.get("ownerObj");
				ICheckList checkList = null;
				
				ICheckListProxyManager checklistProxyManager =(ICheckListProxyManager)BeanHouse.get("checklistProxy");
				ICheckListTemplateProxyManager checklistTemplateProxyManager =(ICheckListTemplateProxyManager)BeanHouse.get("templateProxy");
				//bodyObjRes.setSecurityId(String.valueOf(colNew.getCollateralID()));
				ResponseMessageDetailDTO responseMessageDetailDTO =new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode("SEC000000002");
				responseMessageDetailDTO.setResponseMessage("Security is updated successfully");
				responseMessageList.add(responseMessageDetailDTO);
				bodyObj.setResponseStatus("SUCCESS");
				bodyObj.setSecurityId(securityId);
				bodyObj.setResponseMessageList(responseMessageList);
				if(null!=insuranceResponseList && !insuranceResponseList.isEmpty())
				bodyObj.setInsuranceResponseList(insuranceResponseList);
				
				if(null!=addDocRespList && !addDocRespList.isEmpty())
					bodyObj.setAddDocFacDetResList(addDocRespList);
					
				BodyRestResponseDTOList.add(bodyObj);
				securityResponse.setBodyDetails(BodyRestResponseDTOList);
				
				
				if(col instanceof OBSpecificChargeAircraft)
				{
				try
				{
					System.out.println("In SecurityDetailsRestDTOMapper- to execute MaintainOtherCheckListCommand related code");
			        	
				long checkListID= -999999999l;
				ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
				long limitProfileID =itrxResult.getCollateral().getCollateralLimits()[0].getCmsLimitProfileId();
				ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(limitProfileID, collateralID, custCategory,
						applicationType);
				String checkListId=dao.getChecklistId("O",limitProfileID);
				if(null!=checkListId){
					 checkListID = Long.parseLong(checkListId);
				}
				int wip =0;
				if (ICMSConstant.HAS_PENDING_CHECKLIST_TRX == wip) {
					//resultMap.put("wip", "wip");
					//Check what id needed to de done in this case
				}
				else {
					if (checkListID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {

						checkList = checklistProxyManager.getDefaultCAMCheckList(owner,"IN", secType, secSubType, "", "", "");
					}
					else {
						checkListTrxVal = checklistProxyManager.getCheckList(checkListID);
						checkList = checkListTrxVal.getCheckList();


						if (checkList.getTemplateID() <= 0) {
							DefaultLogger.warn(this, "There is template id for checklist id [" + checkListID
									+ "], retrieving template id");

							ITemplate template = checklistTemplateProxyManager.getCollateralTemplate(secType,
									secSubType, "IN");
							if (template != null) {
								checkList.setTemplateID(template.getTemplateID());
							}
						}

						
					}

					if (checkList.getCheckListItemList() != null) {
						Arrays.sort(checkList.getCheckListItemList());
					}

					String checkListStatus = checkList.getCheckListStatus();
					// perform sorting only if checklist status is not NEW
					if ((checkListStatus == null)
							|| ((checkListStatus != null) && !checkListStatus.equals(ICMSConstant.STATE_CHECKLIST_NEW))) {
						ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
						checkList.setCheckListItemList(sortedItems);
					}

					String dispatchToMaintain = ("Y".equals(checkList.getDisableCollaborationInd())) ? "Y" : "N";
					checkList.setDisableCollaborationInd(dispatchToMaintain);

					
				}

				ArrayList outputDocIds = null;
				if (checkList != null) {
					ArrayList docNos = new ArrayList();
					ICheckListItem[] itemList = checkList.getCheckListItemList();
					for (int count = 0; count < itemList.length; count++) {
						ICheckListItem item = itemList[count];
						long docNoLong = item.getCheckListItemRef();
						String docNo = String.valueOf(docNoLong);
						docNos.add(docNo);
					}
					outputDocIds = checklistProxyManager.getDocumentIdsForCheckList(docNos);
				}
				
			
				}
			catch (TemplateNotSetupException e) {
				
				no_template = true;
			}
			catch (CheckListTemplateException ex) {
				throw new CommandProcessingException("fail to retrieve checklist template of security, type [" + secType
						+ "], subtype [" + secSubType + "]", ex);
			}
			catch (CheckListException ex) {
				throw new CommandProcessingException("fail to maintain security checklist", ex);
			}
				System.out.println("In SecurityDetailsRestDTOMapper, Going out MaintainOtherCheckListCommand doExecute() related code");
				System.out.println("In SecurityDetailsRestDTOMapper, to execute SubmitOtherCheckListCommandIP doExecute() related code");
				
				ICheckListItem temp[] = checkList.getCheckListItemList();
				ICheckList checkList2=new OBCheckList();
				List insuranceList=new ArrayList();
				IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
				long collateralID1 = itrxValue.getCollateral().getCollateralID();
				SearchResult allInsuranceList= (SearchResult)insuranceGCJdbc.getAllInsurancePolicy(collateralID1);
				insuranceList=(List) allInsuranceList.getResultList(); 
				List aCheckListItemList1=new ArrayList<ICheckListItem>();
				
				boolean isInsuPresentInChecklist1=false;
				boolean isInsuPresentInChecklist2=false;
				if(null == checkListTrxVal){
					
					//	String checklistStatus="IN_PROGRESS";
						if(null!=insuranceList){
						for(int i=0;i<insuranceList.size();i++){
							IInsurancePolicy actualObj=(IInsurancePolicy)insuranceList.get(i);
							if(!"DELETED".equals(actualObj.getStatus())){
							OBCheckListItem OBCheckListItem=new OBCheckListItem();
							
							OBItem obItem=new OBItem();
							obItem.setItemCode("DOC"+actualObj.getRefID());
							obItem.setItemDesc(ICMSConstant.INSURANCE_POLICY2+"-"+itrxValue.getCollateral().getCollateralType().getTypeName()+itrxValue.getCollateral().getCollateralID()+"-"+actualObj.getRefID());
							OBCheckListItem.setItem(obItem);
							OBCheckListItem.setItemStatus(actualObj.getInsuranceStatus());
							OBCheckListItem.setInsuranceId(String.valueOf(actualObj.getRefID()));
							
							OBCheckListItem.setUpdatedBy(actualObj.getLastUpdatedBy());
							OBCheckListItem.setUpdatedDate(actualObj.getLastUpdatedOn());
							OBCheckListItem.setApprovedBy(actualObj.getLastApproveBy());
							OBCheckListItem.setApprovedDate(actualObj.getLastApproveOn()); 
							
							aCheckListItemList1.add(OBCheckListItem);
							}
						}
						checkList2.setCheckListItemList((ICheckListItem[]) aCheckListItemList1.toArray(new ICheckListItem[aCheckListItemList1.size()]));
					}
				}
						else
						{
							if (temp != null && temp.length>0) {
								
								List<String> checklistInsuranceId=insuranceGCJdbc.getInsuranceIdFromChecklist(checkList.getCheckListID());
								List<String> insuranceId=insuranceGCJdbc.getInsurancePolicyId(collateralID1);

								Set<String> s1=new HashSet(checklistInsuranceId);
								Set<String> s2=new HashSet(insuranceId);
								
								if(s1.size()>0 || s2.size()>0){
									if(s1.containsAll(s2)){
										isInsuPresentInChecklist2=true;
									}
									if(s2.containsAll(s1)){
										isInsuPresentInChecklist1=true;
									}
								}
								
								if(!isInsuPresentInChecklist2 || !isInsuPresentInChecklist1){
								for (int i = 0; i < temp.length; i++) {
									
									
									/**
									 * skip update of deleted items
									 */
									if (isItemDeleted(checkList, i)) {
										continue;
									}
									
									if(null!=insuranceList){
									for(int j=0;j<insuranceList.size();j++){
										IInsurancePolicy actualObj=(IInsurancePolicy)insuranceList.get(j);
										boolean isDelete=false;
										//OBCheckListItem OBCheckListItem = null;
										OBCheckListItem OBCheckListItem = (OBCheckListItem) checkList.getCheckListItemList()[i];	
										
										if(null!=OBCheckListItem.getInsuranceId()){
										if(String.valueOf(actualObj.getRefID()).equals(OBCheckListItem.getInsuranceId())){
											if("DELETED".equals(actualObj.getStatus())){
												//don't add the object in checklist
												
												// OBCheckListItem = (OBCheckListItem) checkList.getCheckListItemList()[i];	
												 OBCheckListItem.setIsDeletedInd(true);
												 OBCheckListItem.setUpdatedBy(actualObj.getLastUpdatedBy());
												 OBCheckListItem.setUpdatedDate(actualObj.getLastUpdatedOn());
												 OBCheckListItem.setApprovedBy(actualObj.getLastApproveBy());
												 OBCheckListItem.setApprovedDate(actualObj.getLastApproveOn()); 
												 aCheckListItemList1.add(OBCheckListItem);
													break;	
											}else{
											// OBCheckListItem = (OBCheckListItem) checkList.getCheckListItemList()[i];	
												OBCheckListItem.setItemStatus(actualObj.getInsuranceStatus());
												OBCheckListItem.setUpdatedBy(actualObj.getLastUpdatedBy());
												OBCheckListItem.setUpdatedDate(actualObj.getLastUpdatedOn());
												OBCheckListItem.setApprovedBy(actualObj.getLastApproveBy());
												OBCheckListItem.setApprovedDate(actualObj.getLastApproveOn()); 
												aCheckListItemList1.add(OBCheckListItem);
												break;	
											}
											}else if(j==(insuranceList.size()-1)){
												aCheckListItemList1.add(OBCheckListItem);
												break;
											}
										}else{
											//Add the non insurance other checklist item
											// OBCheckListItem = (OBCheckListItem) checkList.getCheckListItemList()[i];	
											aCheckListItemList1.add(OBCheckListItem);
											break;
										}
										
									}
									checkList2.setCheckListItemList((ICheckListItem[]) aCheckListItemList1.toArray(new ICheckListItem[aCheckListItemList1.size()]));
								    }
							      }	
						        }
						      }
							
							//Add newly added insurance details when checklist is already created.
							Set<String> insIdNotPresentinChklist=insuranceGCJdbc.getInsPolicyIdNotPresentInChecklist( checkList.getCheckListID(), collateralID1);
							Iterator<String> it = insIdNotPresentinChklist.iterator();
							while(it.hasNext()){
								String id =  it.next();
							for (int i = 0; i < insuranceList.size(); i++) {
								IInsurancePolicy actualObj=(IInsurancePolicy)insuranceList.get(i);
								if(id.equals(String.valueOf(actualObj.getRefID()))){
									OBCheckListItem OBCheckListItem=new OBCheckListItem();
									
									OBItem obItem=new OBItem();
									obItem.setItemCode("DOC"+actualObj.getRefID());
									obItem.setItemDesc(ICMSConstant.INSURANCE_POLICY2+"-"+itrxValue.getCollateral().getCollateralType().getTypeName()+itrxValue.getCollateral().getCollateralID()+"-"+actualObj.getRefID());
									OBCheckListItem.setItem(obItem);
									OBCheckListItem.setItemStatus(actualObj.getInsuranceStatus());
									OBCheckListItem.setInsuranceId(String.valueOf(actualObj.getRefID()));
									OBCheckListItem.setUpdatedBy(actualObj.getLastUpdatedBy());
									OBCheckListItem.setUpdatedDate(actualObj.getLastUpdatedOn());
									OBCheckListItem.setApprovedBy(actualObj.getLastApproveBy());
									OBCheckListItem.setApprovedDate(actualObj.getLastApproveOn()); 
									aCheckListItemList1.add(OBCheckListItem);
									checkList2.setCheckListItemList((ICheckListItem[]) aCheckListItemList1.toArray(new ICheckListItem[aCheckListItemList1.size()]));
								}
								
							}
							}
							
						}
				//OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				OBTrxContext ctx = new OBTrxContext();
				if(null!=checkList2.getCheckListItemList()){
					checkList.setCheckListItemList(checkList2.getCheckListItemList());
					}
				if (checkListTrxVal == null) {
					if(checkList.getCheckListItemList().length >0){
					if (this.isMaintainChecklistWithoutApproval) {
						try {
							checkListTrxVal = checklistProxyManager.makerCreateCheckListWithoutApproval(ctx, checkList);
						}
						catch (CheckListException ex) {
							throw new CommandProcessingException("failed to submit checklist creation workflow", ex);
						}
					}
					else {
						try {
							checkListTrxVal =checklistProxyManager.makerCreateCheckList(ctx, checkList);
						}
						catch (CheckListException ex) {
							throw new CommandProcessingException("failed to submit checklist creation workflow", ex);
						}
					}
					}
				}
				else if(!isInsuPresentInChecklist1 || !isInsuPresentInChecklist2){ //if there is any addition in insurance doc then only perform this update else nothing
					if (this.isMaintainChecklistWithoutApproval) {
						try {
							checkListTrxVal = checklistProxyManager.makerUpdateCheckListWithoutApproval(ctx,
									checkListTrxVal, checkList);
						}
						catch (CheckListException ex) {
							throw new CommandProcessingException("failed to submit checklist update workflow", ex);
						}
					}
					else {
						try {
							checkListTrxVal = checklistProxyManager.makerUpdateCheckList(ctx, checkListTrxVal, checkList);
						}
						catch (CheckListException ex) {
							throw new CommandProcessingException("failed to submit checklist update workflow", ex);
						}
					}
				}
				DefaultLogger.debug(this, "In SecurityDetailsRestDTOMapper, Going out of SubmitOtherCheckListCommandIP doExecute() related code");
				DefaultLogger.debug(this, "In SecurityDetailsRestDTOMapper, to execute SubmitOtherReceiptCommandIP doExecute() related code");
				ICheckListProxyManager proxy1 = CheckListProxyManagerFactory.getCheckListProxyManager();
				try {
					if(null!=checkListTrxVal){
					
					
					//List insuranceList=(ArrayList)map.get("insuranceList"); to be checked in debug

					//ICheckListProxyManager proxy1 = CheckListProxyManagerFactory.getCheckListProxyManager();
					// DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>Updating" +
					// checkList);
					
					ICheckListTrxValue checkListTrxValOld = proxy1.getCheckListByTrxID(checkListTrxVal.getTransactionID());
					
					ArrayList resultListStage = new ArrayList();
					//ArrayList resultListActual = new ArrayList();
					
					ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
					if(null!=checkListItemList && checkListItemList.length >0){
					
						for(int j=0;j<checkListItemList.length;j++){
							
							ICheckListItem iChecklistItem=checkListItemList[j];
								if(null!=insuranceList){
									for(int i=0;i<insuranceList.size();i++){
										IInsurancePolicy actualObj=(IInsurancePolicy)insuranceList.get(i);
										
										
										if(null!=iChecklistItem.getInsuranceId()){
										if(String.valueOf(actualObj.getRefID()).equals(iChecklistItem.getInsuranceId())){
										if(ICMSConstant.STATE_ITEM_RECEIVED.equals(actualObj.getInsuranceStatus())){
											iChecklistItem.setReceivedDate(actualObj.getReceivedDate());
											iChecklistItem.setDocDate(actualObj.getEffectiveDate());
											iChecklistItem.setCurrency(actualObj.getCurrencyCode());
											iChecklistItem.setHdfcAmt(String.valueOf(actualObj.getInsuredAmount().getAmount()));
											iChecklistItem.setDocAmt(String.valueOf(actualObj.getInsurableAmount().getAmount()));
											iChecklistItem.setItemStatus(ICMSConstant.STATE_ITEM_RECEIVED);
											iChecklistItem.setExpiryDate(actualObj.getExpiryDate());
											
										}else if(ICMSConstant.STATE_ITEM_DEFERRED.equals(actualObj.getInsuranceStatus())){
											iChecklistItem.setOriginalTargetDate(actualObj.getOriginalTargetDate());
											//iChecklistItem.setExpiryDate(actualObj.getOriginalTargetDate());
											iChecklistItem.setDeferExpiryDate(actualObj.getDateDeferred());
											iChecklistItem.setCreditApprover(actualObj.getCreditApprover());
											iChecklistItem.setExpectedReturnDate(actualObj.getNextDueDate());
											iChecklistItem.setItemStatus(ICMSConstant.STATE_ITEM_DEFERRED);
											
											if(iChecklistItem.getDeferCount() == null){
												iChecklistItem.setDeferCount("1");
											}
											
										}else if(ICMSConstant.STATE_ITEM_WAIVED.equals(actualObj.getInsuranceStatus())){
											iChecklistItem.setWaivedDate(actualObj.getWaivedDate());
											iChecklistItem.setCreditApprover(actualObj.getCreditApprover());
											iChecklistItem.setItemStatus(ICMSConstant.STATE_ITEM_WAIVED);
										}else if(ICMSConstant.STATE_ITEM_AWAITING.equals(actualObj.getInsuranceStatus())){
											iChecklistItem.setOriginalTargetDate(actualObj.getOriginalTargetDate());
											//iChecklistItem.setExpectedReturnDate(actualObj.getOriginalTargetDate());
											iChecklistItem.setItemStatus(ICMSConstant.STATE_ITEM_AWAITING);
										}
										//Add the updated insurance other cehcklist item
										resultListStage.add(iChecklistItem);
										
										break;
										}else if(i==(insuranceList.size()-1)){
											resultListStage.add(iChecklistItem);
											break;
										}
									}else{
										//Add the non insurance other cehcklist item
										resultListStage.add(iChecklistItem);
										break;
									}
							}
						}else{
							resultListStage.add(iChecklistItem);
						}
					
				//	checkListTrxVal.getCheckList().setCheckListItemList((ICheckListItem[]) resultListActual.toArray(new ICheckListItem[resultListActual.size()]));
					
					//checkListTrxVal = proxy.makerUpdateCheckListReceipt(ctx, checkListTrxVal, checkListTrxVal.getStagingCheckList());
					
					
					}
					
					
					checkListTrxVal.getStagingCheckList().setCheckListItemList((ICheckListItem[]) resultListStage.toArray(new ICheckListItem[resultListStage.size()]));
					checkList=checkListTrxVal.getStagingCheckList();
						
					if (checkListTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED)
							|| checkListTrxVal.getStatus().equals(ICMSConstant.STATE_OFFICER_REJECTED)) {
						checkListTrxVal = proxy1.makerEditRejectedCheckListReceiptTrx(ctx, checkListTrxVal, checkList);
					}
					else  {
						checkListTrxVal = proxy1.makerUpdateCheckListReceipt(ctx, checkListTrxVal, checkList);
					}
					}
					}
					//resultMap.put("request.ITrxValue", checkListTrxVal);
				}
				catch (Exception e) {
					DefaultLogger.debug(this, "got exception in doExecute" + e);
					e.printStackTrace();
					throw (new CommandProcessingException(e.getMessage()));
				}
				DefaultLogger.debug(this, "In SecurityDetailsRestDTOMapper, Going out of SubmitOtherReceiptCommandIP doExecute() related code");
				DefaultLogger.debug(this, "In SecurityDetailsRestDTOMapper, to execute ApproveOtherReceiptCommand doExecute() related code");
				try {
			
			
			ICheckListProxyManager proxyManager= CheckListProxyManagerFactory.getCheckListProxyManager();
			ICheckListTrxValue checkListTrxValNew = checkListTrxVal;
		
			if(null!=checkListTrxValNew){
			ICheckListTrxValue checkListTrxVal1 = proxyManager.getCheckListByTrxID(checkListTrxValNew.getTransactionID());
			OBTrxContext ctx1 =  new OBTrxContext();
			
			//DefaultLogger.debug(this, "Testing compilation.....................");
			List listDate=new ArrayList();
			DefaultLogger.debug(this, "Testing compilation...........1..........");

			ICheckListItem[] iCheckListItems=checkListTrxVal.getStagingCheckList().getCheckListItemList();
			/*ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			
			DefaultLogger.debug(this,"Login id from global scope:"+user.getLoginID());*/
			
			
			ICheckListProxyManager proxy2 = CheckListProxyManagerFactory.getCheckListProxyManager();
			
			String preStatus = checkListTrxVal.getStatus();
			DefaultLogger.debug(this, "Current status-----" + preStatus);
			if(iCheckListItems.length >0) {
			
				checkListTrxVal = proxy2.checkerApproveCheckListReceipt(ctx1, checkListTrxVal1);
		
			
			proxy2.updateSharedChecklistStatus(checkListTrxVal1); 
			}
			//resultMap.put("request.ITrxValue", checkListTrxVal);
			}
			else{
			//resultMap.put("request.ITrxValue", checkListTrxValNew);
			}
			
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
			
				DefaultLogger.debug(this, "In SecurityDetailsRestDTOMapper, Going out of ApproveOtherReceiptCommand doExecute() related code");
				}
				if(col instanceof OBPropertyCollateral)
				{
					long limitProfileID =itrxResult.getCollateral().getCollateralLimits()[0].getCmsLimitProfileId();
					ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(limitProfileID, collateralID, custCategory,
							applicationType);
					DefaultLogger.debug(this, "No 1 IN");
					ICollateralLimitMap[] map1 = itrxValue.getCollateral().getCollateralLimits();
					
//					String tCollateralID = "200701010000130";
					long collateralID1= 0L;
					ICollateralCheckListOwner owner1 = new OBCollateralCheckListOwner(limitProfileID, collateralID, custCategory,
							applicationType);
					//ICollateralCheckListOwner owner = (OBCollateralCheckListOwner) map.get("ownerObj");
					ICheckList checkList1 = null;
					HashMap limitAndChecklist = new HashMap();
					HashMap limitAndChecklistTrxValue = new HashMap();
					ICustomerDAO customerDao =  CustomerDAOFactory.getDAO();
					try {
					List limitIdList = customerDao.getCollateralMappedCustomerLimitIdList(itrxValue.getCollateral().getCollateralID());
					for(int i=0;i< limitIdList.size();i++){
						long checkListID= -999999999l;
						ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
						String limitId = (String) limitIdList.get(i);
						String checkListId=dao.getChecklistId("O",Long.valueOf(limitId));
						if(null!=checkListId){
							 checkListID = Long.parseLong(checkListId);
						}
						int wip =0;
						if (ICMSConstant.HAS_PENDING_CHECKLIST_TRX == wip) {
							//resultMap.put("wip", "wip");
						}
						else {
							if (checkListID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {

								checkList1 = checklistProxyManager.getDefaultCAMCheckList(owner,"IN", secType, secSubType, "", "", "");
						//		checkList = linkInsuranceReceipt(checkList);
							//	resultMap.put("checkListTrxVal", null);
								//resultMap.put("ownerObj", checkList.getCheckListOwner());
							}
							else {
								ICheckListTrxValue checkListTrxVal1 = checklistProxyManager.getCheckList(checkListID);
								checkList1 = checkListTrxVal1.getCheckList();


								if (checkList1.getTemplateID() <= 0) {
									DefaultLogger.warn(this, "There is template id for checklist id [" + checkListID
											+ "], retrieving template id");

									ITemplate template = checklistTemplateProxyManager.getCollateralTemplate(secType,
											secSubType, "IN");
									if (template != null) {
										checkList1.setTemplateID(template.getTemplateID());
									}
								}

								//resultMap.put("ownerObj", checkList.getCheckListOwner());
								limitAndChecklistTrxValue.put(limitId,checkListTrxVal1);
								//resultMap.put("checkListTrxVal", checkListTrxVal);
							}

							if (checkList1.getCheckListItemList() != null) {
								Arrays.sort(checkList1.getCheckListItemList());
							}

							String checkListStatus = checkList1.getCheckListStatus();
							// perform sorting only if checklist status is not NEW
							if ((checkListStatus == null)
									|| ((checkListStatus != null) && !checkListStatus.equals(ICMSConstant.STATE_CHECKLIST_NEW))) {
								ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList1.getCheckListItemList());
								checkList1.setCheckListItemList(sortedItems);
							}

							String dispatchToMaintain = ("Y".equals(checkList1.getDisableCollaborationInd())) ? "Y" : "N";
							checkList1.setDisableCollaborationInd(dispatchToMaintain);

							// CR-236
							/*String event = (String) map.get("event");
							if ("delete".equals(event)) {
								((OBCheckList) checkList).setObsolete(ICMSConstant.TRUE_VALUE);
							}else if("view".equals(event)){
			                    isViewFlag = true;
			                }*/
							//resultMap.put("checkList", checkList);
						}

						ArrayList outputDocIds = null;
						ArrayList outputDocIdsNew = null;
						if (checkList1 != null) {
							ArrayList docNos = new ArrayList();
							ICheckListItem[] itemList = checkList1.getCheckListItemList();
							for (int count = 0; count < itemList.length; count++) {
								ICheckListItem item = itemList[count];
								long docNoLong = item.getCheckListItemRef();
								String docNo = String.valueOf(docNoLong);
								docNos.add(docNo);
							}
							System.out.println("MaintainOtherChecklistCommandProp in REST=> docNos.size()=>"+docNos.size());
							if(docNos.size() <= 900) {
								outputDocIds = checklistProxyManager.getDocumentIdsForCheckList(docNos);
							}else {
								Object[] arr = docNos.toArray();
						        
						        
						        int chunk = 900; // chunk size to divide
						        for(int j=0;j<arr.length;j+=chunk){
						          String arrDocNosNew =  Arrays.toString(Arrays.copyOfRange(arr, j, Math.min(arr.length,j+chunk)));
						          if(!"[]".equals(arrDocNosNew)) {
						          String[] arrNew = arrDocNosNew.split(",");
						          for(int m=0 ; m< arrNew.length; m++){
						              arrNew[m] = arrNew[m].trim();
						            }
						          
						           arrNew[0] = arrNew[0].replace("[","");
						           arrNew[(arrNew.length) - 1] = arrNew[(arrNew.length) - 1].replace("]","");
						           List newDocNos = new ArrayList();
						           newDocNos = Arrays.asList(arrNew);
						           ArrayList newDocNosFinal = new ArrayList(newDocNos);
						           System.out.println("Going for checklistProxyManager.getDocumentIdsForCheckList(newDocNosFinal)..");
						           outputDocIdsNew = checklistProxyManager.getDocumentIdsForCheckList(newDocNosFinal);
						           System.out.println("After checklistProxyManager.getDocumentIdsForCheckList(newDocNosFinal)..");
						           if(outputDocIdsNew != null) {
						        	   if(outputDocIdsNew.size() > 0 ) {
						        		   for(int k=0;k<outputDocIdsNew.size();k++) {
						        			   outputDocIds.add(outputDocIdsNew.get(k));
						        		   }
						        	   }
						           }
						        }}     
							}
							if(outputDocIds == null) {
								outputDocIds = new ArrayList();
							}
							
//							outputDocIds = checklistProxyManager.getDocumentIdsForCheckList(docNos);
						}
						//resultMap.put("docNos", outputDocIds);
						
						limitAndChecklist.put(limitId,checkList1);
					}
					}
					catch (TemplateNotSetupException e) {
						//resultMap.put("no_template", "true");
					}
					catch (CheckListTemplateException ex) {
						throw new CommandProcessingException("fail to retrieve checklist template of security, type [" + secType
								+ "], subtype [" + secSubType + "]", ex);
					}
					catch (CheckListException ex) {
						throw new CommandProcessingException("fail to maintain security checklist", ex);
					}catch (Exception e) {
						e.printStackTrace();
					}
					DefaultLogger.debug(this, "Going out of doExecute() No 1");
					DefaultLogger.debug(this, "Inside doExecute() No 2");
					List insuranceList=new ArrayList();
					for (Object key : limitAndChecklistTrxValue.keySet()) {
						ICheckListTrxValue checkListTrxVal2 = (ICheckListTrxValue) limitAndChecklistTrxValue.get(key);
					
					
					//ICheckListTrxValue checkListTrxVal2 = (ICheckListTrxValue) map.get("checkListTrxVal2");
					String mandatoryDisplayRows = (String) map.get("mandatoryDisplayRows");
				
					ICheckList checkListNew = (ICheckList) limitAndChecklist.get(key);
					
					//ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
					

					IDocumentDAO documentDao= CheckListTemplateDAOFactory.getDocumentDAO();
					ICheckListItem temp[] = checkListNew.getCheckListItemList();
					ICheckList checkList2=new OBCheckList();
					
					insuranceList=new ArrayList();
					IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
					long collateralID2 = itrxResult.getCollateral().getCollateralID();
					SearchResult allInsuranceList= (SearchResult)insuranceGCJdbc.getAllInsurancePolicy(collateralID2);
					insuranceList=(List) allInsuranceList.getResultList(); 
					List aCheckListItemList1=new ArrayList<ICheckListItem>();
					
					boolean isInsuPresentInChecklist1=false;
					boolean isInsuPresentInChecklist2=false;
					
					if(null == checkListTrxVal2){
						
					//	String checklistStatus="IN_PROGRESS";
						if(null!=insuranceList){
						for(int i=0;i<insuranceList.size();i++){
							IInsurancePolicy actualObj=(IInsurancePolicy)insuranceList.get(i);
							if(!"DELETED".equals(actualObj.getStatus())){
							OBCheckListItem OBCheckListItem=new OBCheckListItem();
							
							OBItem obItem=new OBItem();
							obItem.setItemCode("DOC"+actualObj.getRefID());
							obItem.setItemDesc(ICMSConstant.INSURANCE_POLICY2+"-"+itrxResult.getCollateral().getCollateralType().getTypeName()+itrxResult.getCollateral().getCollateralID()+"-"+actualObj.getRefID());
							OBCheckListItem.setItem(obItem);
							
							OBCheckListItem.setItemStatus(actualObj.getInsuranceStatus());
							OBCheckListItem.setInsuranceId(String.valueOf(actualObj.getRefID()));
							
							OBCheckListItem.setUpdatedBy(actualObj.getLastUpdatedBy());
							OBCheckListItem.setUpdatedDate(actualObj.getLastUpdatedOn());
							OBCheckListItem.setApprovedBy(actualObj.getLastApproveBy());
							OBCheckListItem.setApprovedDate(actualObj.getLastApproveOn()); 
							
							aCheckListItemList1.add(OBCheckListItem);
							}
						}
						checkList2.setCheckListItemList((ICheckListItem[]) aCheckListItemList1.toArray(new ICheckListItem[aCheckListItemList1.size()]));
					}}else{
					if (temp != null && temp.length>0) {
						
						List<String> checklistInsuranceId=insuranceGCJdbc.getInsuranceIdFromChecklist(checkListNew.getCheckListID());
						List<String> insuranceId=insuranceGCJdbc.getInsurancePolicyId(collateralID);

						Set<String> s1=new HashSet(checklistInsuranceId);
						Set<String> s2=new HashSet(insuranceId);
						
						if(s1.size()>0 || s2.size()>0){
							if(s1.containsAll(s2)){
								isInsuPresentInChecklist2=true;
							}
							if(s2.containsAll(s1)){
								isInsuPresentInChecklist1=true;
							}
						}
						
						if(!isInsuPresentInChecklist2 || !isInsuPresentInChecklist1){
						for (int i = 0; i < temp.length; i++) {
							
							
							/**
							 * skip update of deleted items
							 */
							if (isItemDeleted(checkListNew, i)) {
								continue;
							}
							
							if(null!=insuranceList){
							for(int j=0;j<insuranceList.size();j++){
								IInsurancePolicy actualObj=(IInsurancePolicy)insuranceList.get(j);
								boolean isDelete=false;
								//OBCheckListItem OBCheckListItem = null;
								OBCheckListItem OBCheckListItem = (OBCheckListItem) checkListNew.getCheckListItemList()[i];	
								
								if(null!=OBCheckListItem.getInsuranceId()){
								if(String.valueOf(actualObj.getRefID()).equals(OBCheckListItem.getInsuranceId())){
									if("DELETED".equals(actualObj.getStatus())){
										//don't add the object in checklist
										
										// OBCheckListItem = (OBCheckListItem) checkList.getCheckListItemList()[i];	
										 OBCheckListItem.setIsDeletedInd(true);
										 OBCheckListItem.setUpdatedBy(actualObj.getLastUpdatedBy());
										 OBCheckListItem.setUpdatedDate(actualObj.getLastUpdatedOn());
										 OBCheckListItem.setApprovedBy(actualObj.getLastApproveBy());
										 OBCheckListItem.setApprovedDate(actualObj.getLastApproveOn()); 
										 aCheckListItemList1.add(OBCheckListItem);
											break;	
									}else{
									// OBCheckListItem = (OBCheckListItem) checkList.getCheckListItemList()[i];	
										OBCheckListItem.setItemStatus(actualObj.getInsuranceStatus());
										OBCheckListItem.setUpdatedBy(actualObj.getLastUpdatedBy());
										OBCheckListItem.setUpdatedDate(actualObj.getLastUpdatedOn());
										OBCheckListItem.setApprovedBy(actualObj.getLastApproveBy());
										OBCheckListItem.setApprovedDate(actualObj.getLastApproveOn()); 
										aCheckListItemList1.add(OBCheckListItem);
										break;	
									}
									}else if(j==(insuranceList.size()-1)){
										aCheckListItemList1.add(OBCheckListItem);
										break;
									}
								}else{
									//Add the non insurance other checklist item
									// OBCheckListItem = (OBCheckListItem) checkList.getCheckListItemList()[i];	
									aCheckListItemList1.add(OBCheckListItem);
									break;
								}
								
							}
							checkList2.setCheckListItemList((ICheckListItem[]) aCheckListItemList1.toArray(new ICheckListItem[aCheckListItemList1.size()]));
						    }
					      }	
				        }
				      }
					
					//Add newly added insurance details when checklist is already created.
					Set<String> insIdNotPresentinChklist=insuranceGCJdbc.getInsPolicyIdNotPresentInChecklist( checkListNew.getCheckListID(), collateralID);
					Iterator<String> it = insIdNotPresentinChklist.iterator();
					while(it.hasNext()){
						String id =  it.next();
					for (int i = 0; i < insuranceList.size(); i++) {
						IInsurancePolicy actualObj=(IInsurancePolicy)insuranceList.get(i);
						if(id.equals(String.valueOf(actualObj.getRefID()))){
							OBCheckListItem OBCheckListItem=new OBCheckListItem();
							
							OBItem obItem=new OBItem();
							obItem.setItemCode("DOC"+actualObj.getRefID());
							obItem.setItemDesc(ICMSConstant.INSURANCE_POLICY2+"-"+itrxResult.getCollateral().getCollateralType().getTypeName()+itrxResult.getCollateral().getCollateralID()+"-"+actualObj.getRefID());
							OBCheckListItem.setItem(obItem);
							OBCheckListItem.setItemStatus(actualObj.getInsuranceStatus());
							OBCheckListItem.setInsuranceId(String.valueOf(actualObj.getRefID()));
							OBCheckListItem.setUpdatedBy(actualObj.getLastUpdatedBy());
							OBCheckListItem.setUpdatedDate(actualObj.getLastUpdatedOn());
							OBCheckListItem.setApprovedBy(actualObj.getLastApproveBy());
							OBCheckListItem.setApprovedDate(actualObj.getLastApproveOn()); 
							aCheckListItemList1.add(OBCheckListItem);
							checkList2.setCheckListItemList((ICheckListItem[]) aCheckListItemList1.toArray(new ICheckListItem[aCheckListItemList1.size()]));
						}
						
					}
					}
					
					
					}
					//OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
					
					if(null!=checkList2.getCheckListItemList()){
						checkListNew.setCheckListItemList(checkList2.getCheckListItemList());
					}
					if (checkListTrxVal2 == null) {
						if(checkListNew.getCheckListItemList().length >0){
						if (this.isMaintainChecklistWithoutApproval) {
							try {
								checkListTrxVal2 = checklistProxyManager.makerCreateCheckListWithoutApproval(trxContext, checkListNew);
							}
							catch (CheckListException ex) {
								throw new CommandProcessingException("failed to submit checklist creation workflow", ex);
							}
						}
						else {
							try {
								checkListTrxVal2 =checklistProxyManager.makerCreateCheckList(trxContext, checkListNew);
							}
							catch (CheckListException ex) {
								throw new CommandProcessingException("failed to submit checklist creation workflow", ex);
							}
						}
						}
					}else if(!isInsuPresentInChecklist1 || !isInsuPresentInChecklist2){ //if there is any addition in insurance doc then only perform this update else nothing
						if (this.isMaintainChecklistWithoutApproval) {
							try {
								checkListTrxVal2 = checklistProxyManager.makerUpdateCheckListWithoutApproval(trxContext,
										checkListTrxVal2, checkListNew);
							}
							catch (CheckListException ex) {
								throw new CommandProcessingException("failed to submit checklist update workflow", ex);
							}
						}
						else {
							try {
								checkListTrxVal2 = checklistProxyManager.makerUpdateCheckList(trxContext, checkListTrxVal2, checkListNew);
							}
							catch (CheckListException ex) {
								throw new CommandProcessingException("failed to submit checklist update workflow", ex);
							}
						}
					}
					//resultMap.put("request.ITrxValue", checkListTrxVal2);
					//resultMap.put("checkListTrxVal2",checkListTrxVal2);
					limitAndChecklist.put(key,checkListNew);
					limitAndChecklistTrxValue.put(key,checkListTrxVal2);
					//resultMap.put("insuranceList",insuranceList);
					}
					DefaultLogger.debug(this, "Going out of doExecute() No 2");
					DefaultLogger.debug(this, "Inside doExecute() No 3"); 
					try {
						for (Object key : limitAndChecklistTrxValue.keySet()) {
							ICheckListTrxValue checkListTrxVal3 = (ICheckListTrxValue) limitAndChecklistTrxValue.get(key);
						
						if(null!=checkListTrxVal3){
						ICheckList checkList3 = checkListTrxVal3.getCheckList();
						//OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
						//List insuranceList=(ArrayList)map.get("insuranceList");

						ICheckListProxyManager proxy3 = CheckListProxyManagerFactory.getCheckListProxyManager();
						// DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>Updating" +
						// checkList3);
						
						ICheckListTrxValue checkListTrxValOld = proxy3.getCheckListByTrxID(checkListTrxVal3.getTransactionID());
						
						ArrayList resultListStage = new ArrayList();
						//ArrayList resultListActual = new ArrayList();
						
						ICheckListItem[] checkListItemList = checkList3.getCheckListItemList();
						
						if(null!=checkListItemList && checkListItemList.length >0){
						
							for(int j=0;j<checkListItemList.length;j++){
								
								ICheckListItem iChecklistItem=checkListItemList[j];
									if(null!=insuranceList){
										for(int i=0;i<insuranceList.size();i++){
											IInsurancePolicy actualObj=(IInsurancePolicy)insuranceList.get(i);
											
											
											if(null!=iChecklistItem.getInsuranceId()){
											if(String.valueOf(actualObj.getRefID()).equals(iChecklistItem.getInsuranceId())){
											if(ICMSConstant.STATE_ITEM_RECEIVED.equals(actualObj.getInsuranceStatus())){
												iChecklistItem.setReceivedDate(actualObj.getReceivedDate());
												iChecklistItem.setDocDate(actualObj.getEffectiveDate());
												iChecklistItem.setCurrency(actualObj.getCurrencyCode());
												iChecklistItem.setHdfcAmt(String.valueOf(actualObj.getInsuredAmount().getAmount()));
												iChecklistItem.setDocAmt(String.valueOf(actualObj.getInsurableAmount().getAmount()));
												iChecklistItem.setItemStatus(ICMSConstant.STATE_ITEM_RECEIVED);
												iChecklistItem.setExpiryDate(actualObj.getExpiryDate());
												
											}else if(ICMSConstant.STATE_ITEM_DEFERRED.equals(actualObj.getInsuranceStatus())){
												iChecklistItem.setOriginalTargetDate(actualObj.getOriginalTargetDate());
												//iChecklistItem.setExpiryDate(actualObj.getOriginalTargetDate());
												iChecklistItem.setDeferExpiryDate(actualObj.getDateDeferred());
												iChecklistItem.setCreditApprover(actualObj.getCreditApprover());
												iChecklistItem.setExpectedReturnDate(actualObj.getNextDueDate());
												iChecklistItem.setItemStatus(ICMSConstant.STATE_ITEM_DEFERRED);
												
												if(iChecklistItem.getDeferCount() == null){
													iChecklistItem.setDeferCount("1");
												}
												
											}else if(ICMSConstant.STATE_ITEM_WAIVED.equals(actualObj.getInsuranceStatus())){
												iChecklistItem.setWaivedDate(actualObj.getWaivedDate());
												iChecklistItem.setCreditApprover(actualObj.getCreditApprover());
												iChecklistItem.setItemStatus(ICMSConstant.STATE_ITEM_WAIVED);
											}else if(ICMSConstant.STATE_ITEM_AWAITING.equals(actualObj.getInsuranceStatus())){
												iChecklistItem.setOriginalTargetDate(actualObj.getOriginalTargetDate());
												//iChecklistItem.setExpectedReturnDate(actualObj.getOriginalTargetDate());
												iChecklistItem.setItemStatus(ICMSConstant.STATE_ITEM_AWAITING);
											}
											//Add the updated insurance other cehcklist item
											resultListStage.add(iChecklistItem);
											
											break;
											}else if(i==(insuranceList.size()-1)){
												resultListStage.add(iChecklistItem);
												break;
											}
										}else{
											//Add the non insurance other cehcklist item
											resultListStage.add(iChecklistItem);
											break;
										}
								}
							}else{
								resultListStage.add(iChecklistItem);
							}
						
					//	checkListTrxVal3.getCheckList().setCheckListItemList((ICheckListItem[]) resultListActual.toArray(new ICheckListItem[resultListActual.size()]));
						
						//checkListTrxVal3 = proxy3.makerUpdateCheckListReceipt(ctx, checkListTrxVal3, checkListTrxVal3.getStagingCheckList());
						
						
						}
						
						
							checkListTrxVal3.getStagingCheckList().setCheckListItemList((ICheckListItem[]) resultListStage.toArray(new ICheckListItem[resultListStage.size()]));
							checkList3=checkListTrxVal3.getStagingCheckList();
							
						if (checkListTrxVal3.getStatus().equals(ICMSConstant.STATE_REJECTED)
								|| checkListTrxVal3.getStatus().equals(ICMSConstant.STATE_OFFICER_REJECTED)) {
							checkListTrxVal3 = proxy3.makerEditRejectedCheckListReceiptTrx(trxContext, checkListTrxVal3, checkList3);
						}
						else  {
							checkListTrxVal3 = proxy3.makerUpdateCheckListReceipt(trxContext, checkListTrxVal3, checkList3);
						}
						}
						limitAndChecklist.put(key,checkList3);
						limitAndChecklistTrxValue.put(key,checkListTrxVal3);
						}
						//resultMap.put("request.ITrxValue", checkListTrxVal3);
						
						}
						//resultMap.put("limitAndChecklist",limitAndChecklist);
						//resultMap.put("limitAndChecklistTrxValue",limitAndChecklistTrxValue);
					}
					catch (Exception e) {
						DefaultLogger.debug(this, "got exception in doExecute" + e);
						e.printStackTrace();
						throw (new CommandProcessingException(e.getMessage()));
					}
					DefaultLogger.debug(this, "Going out of doExecute() No 3");
					DefaultLogger.debug(this, "Inside doExecute() No 4");
					try {
						
						for (Object key : limitAndChecklistTrxValue.keySet()) {
							ICheckListTrxValue checkListTrxValNew1 = (ICheckListTrxValue) limitAndChecklistTrxValue.get(key);
						
						ICheckListProxyManager proxyManager= CheckListProxyManagerFactory.getCheckListProxyManager();
						//ICheckListTrxValue checkListTrxValNew1 = (ICheckListTrxValue) map.get("checkListTrxVal");
					
						if(null!=checkListTrxValNew1){
						ICheckListTrxValue checkListTrxVal4 = proxyManager.getCheckListByTrxID(checkListTrxValNew1.getTransactionID());
						//OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
						
						//DefaultLogger.debug(this, "Testing compilation.....................");
						List listDate=new ArrayList();
						DefaultLogger.debug(this, "Testing compilation...........1..........");
						

						ICheckListItem[] iCheckListItems=checkListTrxVal4.getStagingCheckList().getCheckListItemList();
						/*ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
						
						DefaultLogger.debug(this,"Login id from global scope:"+user.getLoginID());
						*/
						
						ICheckListProxyManager proxy4 = CheckListProxyManagerFactory.getCheckListProxyManager();
						
						String preStatus = checkListTrxVal4.getStatus();
						DefaultLogger.debug(this, "Current status-----" + preStatus);
						if(iCheckListItems.length >0) {
						
						
							checkListTrxVal4 = proxy4.checkerApproveCheckListReceipt(trxContext, checkListTrxVal4);
					
						
							proxy4.updateSharedChecklistStatus(checkListTrxVal4); 
						}
						//limitAndChecklist.put(key,checkList3);
						limitAndChecklistTrxValue.put(key,checkListTrxVal4);
						}
						else{
							//limitAndChecklist.put(key,checkList3);
							limitAndChecklistTrxValue.put(key,checkListTrxValNew1);
						}
						
						}
						
						//resultMap.put("limitAndChecklistTrxValue", limitAndChecklistTrxValue);
					}
					catch (Exception e) { 
						DefaultLogger.debug(this, "got exception in doExecute" + e);
						e.printStackTrace();
						throw (new CommandProcessingException(e.getMessage()));
					}
					DefaultLogger.debug(this, "Going out of doExecute()");
				}
				
				if(itrxResult.getCollateral() instanceof OBOtherListedLocal)
				{
					
					IOtherListedLocal markObj = (IOtherListedLocal) itrxResult.getCollateral();
					
					List<StockDetailRestResponseDTO> stockResponseList = new ArrayList<StockDetailRestResponseDTO>();
					StockDetailRestResponseDTO stockObj = null;
					StockLineDetailResponseDTO lineStock = null;
					IMarketableEquity[] equitylList = markObj.getEquityList();
					long equityId = 0;
					long lineEquityId = 0;
					if( collateralRestRequestDTO.getBodyDetails().get(0).getStockDetailsList()!=null && !collateralRestRequestDTO.getBodyDetails().get(0).getStockDetailsList().isEmpty() 
							&& collateralRestRequestDTO.getBodyDetails().get(0).getStockDetailsList().size()>0 )
					{

						for (int i = 0;i<collateralRestRequestDTO.getBodyDetails().get(0).getStockDetailsList().size();i++)
						{	
							for (int e = 0;e< equitylList.length;e++)
							
							{

								
								if(null !=equitylList[e].getEquityUniqueID()
										&& !equitylList[e].getEquityUniqueID().trim().isEmpty())
								{
									List<StockLineDetailResponseDTO> lineStockResponseList =null;
									if(equitylList[e].getEquityUniqueID().equals(collateralRestRequestDTO.getBodyDetails().get(0).getStockDetailsList().get(i).getStockSecUniqueId()))
									{
										stockObj = new StockDetailRestResponseDTO();
										IMarketableEquityLineDetail[] lineArray = equitylList[e].getLineDetails();
										if(lineArray!=null) {
											for(IMarketableEquityLineDetail lineList : lineArray)
											{
												lineStockResponseList = new ArrayList<StockLineDetailResponseDTO>();

												if(null !=lineList.getLineEquityUniqueID()
														&& !lineList.getLineEquityUniqueID().trim().isEmpty())
												{
													lineStock = new StockLineDetailResponseDTO();
													lineEquityId =lineList.getLineDetailId();
													lineStock.setLineUniqueId(lineList.getLineEquityUniqueID());
													lineStock.setClimsLineId(String.valueOf(lineEquityId));

												}
												if(null != lineStock)
												{
													lineStockResponseList.add(lineStock);
													lineStock=null;
												}
											}
										}
										equityId =equitylList[e].getEquityID();
										stockObj.setStockSecUniqueId(collateralRestRequestDTO.getBodyDetails().get(0).getStockDetailsList().get(i).getStockSecUniqueId() );
										stockObj.setClimsItemId(String.valueOf(equityId));
										if(null != lineStockResponseList)
										{
										stockObj.setLineDetails(lineStockResponseList);
										}
									}
								}
								if(null != stockObj)
								{
									stockResponseList.add(stockObj);
									stockObj=null;
								}
							}
						}
					}
					

					
					bodyObj.setStockResponseList(stockResponseList);
				}
				
				return securityResponse;
			}else{
				bodyObj.setSecurityId("");
				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageDetailDTOList);
				BodyRestResponseDTOList.add(bodyObj);
				securityResponse.setBodyDetails(BodyRestResponseDTOList);	
				DefaultLogger.error(this, " updateSecurityDetails - no value found in trxResult: "+itrxResult);
				throw new CMSException("Server side error");
			}
		}
		DefaultLogger.debug(this, "============= transaction id ============> " + itrxResult.getTransactionID());
		
		} catch (CollateralException e) {
			
			e.printStackTrace();
		}
		
		return securityResponse;
	}

	private boolean isItemDeleted(ICheckList checkList, int i) {
		return ICMSConstant.STATE_DELETED.equals(checkList.getCheckListItemList()[i].getItemStatus());
	}
	
	private PropertyValuation1 setValuation1(IPropertyCollateral iPropertyCollateral,String name) {
		PropertyValuation1 valuation1=new PropertyValuation1();
		if("Update".equals(name)){
			valuation1.setId(new Long(iPropertyCollateral.getVal1_id()));
		}
		valuation1.setPreviousMortCreationDate(iPropertyCollateral.getPreviousMortCreationDate());
		valuation1.setCollateralId(iPropertyCollateral.getCollateralID());
		valuation1.setSubTypeName(iPropertyCollateral.getCollateralSubType().getSubTypeName());
		valuation1.setTypeName(iPropertyCollateral.getCollateralType().getTypeName());
		valuation1.setSciRefNote(iPropertyCollateral.getSCIReferenceNote());
		valuation1.setSecurityCurrency(iPropertyCollateral.getCurrencyCode());
		valuation1.setSecPriority(iPropertyCollateral.getSecPriority());
		valuation1.setMonitorProcess(iPropertyCollateral.getMonitorProcess());
		valuation1.setMonitorFrequency(iPropertyCollateral.getMonitorFrequency());
		valuation1.setSecLocation(iPropertyCollateral.getCollateralLocation());
		valuation1.setSecOrganization(iPropertyCollateral.getSecurityOrganization());
		valuation1.setCollateralCode(iPropertyCollateral.getCollateralCode());
		valuation1.setRevalFrequency(iPropertyCollateral.getCommonRevalFreq());
		valuation1.setNextValuationDate(iPropertyCollateral.getNextValDate());
		valuation1.setChangeType(iPropertyCollateral.getTypeOfChange());
		valuation1.setOtherBankCharge(iPropertyCollateral.getOtherBankCharge());
		valuation1.setSecOwnership(iPropertyCollateral.getSecurityOwnership());
		valuation1.setOwnerOfProperty(iPropertyCollateral.getOwnerOfProperty());
		valuation1.setThirdPartyEntity(iPropertyCollateral.getThirdPartyEntity());
		valuation1.setCinThirdParty(iPropertyCollateral.getCinForThirdParty());
		valuation1.setCersaiTrxRefNo(iPropertyCollateral.getCersaiTransactionRefNumber());
		valuation1.setCersaiSecurityInterestId(iPropertyCollateral.getCersaiSecurityInterestId());
		valuation1.setCersaiAssetId(iPropertyCollateral.getCersaiAssetId());
		valuation1.setDateCersaiRegi(iPropertyCollateral.getDateOfCersaiRegisteration());
		valuation1.setCersaiRegDate(iPropertyCollateral.getCersiaRegistrationDate());
		valuation1.setCersaiId(iPropertyCollateral.getCersaiId());
		valuation1.setSaleDeedPurchaseDate(iPropertyCollateral.getSaleDeedPurchaseDate());
		valuation1.setThirdPartyAddress(iPropertyCollateral.getThirdPartyAddress());
		valuation1.setThirdPartyState(iPropertyCollateral.getThirdPartyState());
		valuation1.setThirdPartyCity(iPropertyCollateral.getThirdPartyCity());
		valuation1.setThirdPartyPincode(iPropertyCollateral.getThirdPartyPincode());
		valuation1.setPropertyId(iPropertyCollateral.getPropertyId());
		valuation1.setDescOfAsset(iPropertyCollateral.getDescription());
		valuation1.setPropertyType(iPropertyCollateral.getPropertyType());
		
		if(null!=iPropertyCollateral.getSalePurchaseValue())
		valuation1.setSalePurchaseValue(iPropertyCollateral.getSalePurchaseValue().getAmountAsBigDecimal().toString());
		
		valuation1.setSalePurchaseDate(iPropertyCollateral.getSalePurchaseDate());
		valuation1.setMortgageType(iPropertyCollateral.getTypeOfMargage());
		valuation1.setMortgagecretaedBy(iPropertyCollateral.getMorgageCreatedBy());
		valuation1.setDocumentReceived(iPropertyCollateral.getDocumentReceived());
		valuation1.setDocumentBlock(iPropertyCollateral.getDocumentReceived());
		valuation1.setBinNumber(iPropertyCollateral.getBinNumber());
		valuation1.setClaim(iPropertyCollateral.getClaim());
		valuation1.setClaimType(iPropertyCollateral.getClaimType());
		valuation1.setAdvocateLawyerName(iPropertyCollateral.getAdvocateLawyerName());
		valuation1.setDeveloperGroupCompany(iPropertyCollateral.getDevGrpCo());
		valuation1.setLotNo(iPropertyCollateral.getLotNo());
		valuation1.setLotNumberPrefix(iPropertyCollateral.getLotNumberPrefix());
		valuation1.setPropertyLotLocation(iPropertyCollateral.getPropertyLotLocation());
		valuation1.setOtherCity(iPropertyCollateral.getOtherCity());
		valuation1.setPropertyAddress(iPropertyCollateral.getPropertyAddress());
		valuation1.setPropertyAddress2(iPropertyCollateral.getPropertyAddress2());
		valuation1.setPropertyAddress3(iPropertyCollateral.getPropertyAddress3());
		valuation1.setPropertyAddress4(iPropertyCollateral.getPropertyAddress4());
		valuation1.setPropertyAddress5(iPropertyCollateral.getPropertyAddress5());
		valuation1.setPropertyAddress6(iPropertyCollateral.getPropertyAddress6());
		valuation1.setProjectName(iPropertyCollateral.getProjectName());
		valuation1.setValuationDateV1(iPropertyCollateral.getValuationDate_v1());
		valuation1.setValuatorCompanyV1(iPropertyCollateral.getValuatorCompany_v1());
		
		if(null!=iPropertyCollateral.getTotalPropertyAmount_v1())
		valuation1.setTotalPropertyAmontV1(iPropertyCollateral.getTotalPropertyAmount_v1().getAmountAsBigDecimal().toString());
		valuation1.setCategoryOfLandUseV1(iPropertyCollateral.getCategoryOfLandUse_v1());
		valuation1.setDeveloperNameV1(iPropertyCollateral.getDeveloperName_v1());
		valuation1.setCountryV1(iPropertyCollateral.getCountry_v1());
		valuation1.setRegionV1(iPropertyCollateral.getRegion_v1());
		valuation1.setStateV1(iPropertyCollateral.getLocationState_v1());
		valuation1.setNearestCityV1(iPropertyCollateral.getNearestCity_v1());
		valuation1.setPincodeV1(iPropertyCollateral.getPinCode_v1());
		valuation1.setLandAreaV1(iPropertyCollateral.getLandArea_v1());
		valuation1.setLandAreaUOMV1(iPropertyCollateral.getLandAreaUOM_v1());
		valuation1.setInSqfLandAreaV1(iPropertyCollateral.getInSqftLandArea_v1());
		valuation1.setBuildupAreaV1(iPropertyCollateral.getBuiltupArea_v1());
		valuation1.setBuildupAreaUOMV1(iPropertyCollateral.getBuiltupAreaUOM_v1());
		valuation1.setInSqfbuildupAreaV1(iPropertyCollateral.getInSqftBuiltupArea_v1());
		valuation1.setPropertyCompletionStatusV1(iPropertyCollateral.getPropertyCompletionStatus_v1());
		valuation1.setLandValueIRBV1(iPropertyCollateral.getLandValue_v1());
		valuation1.setBuildingValueIRBV1(iPropertyCollateral.getBuildingValue_v1());
		valuation1.setReconstructionValueIRBV1(iPropertyCollateral.getReconstructionValueOfTheBuilding_v1());
		valuation1.setIsPhyInspectionV1(iPropertyCollateral.getIsPhysicalInspection_v1());
		valuation1.setPhyInspectionFreqUnitV1(iPropertyCollateral.getPhysicalInspectionFreqUnit_v1());
		valuation1.setLastPhyInspectionDateV1(iPropertyCollateral.getLastPhysicalInspectDate_v1());
		valuation1.setNextPhyInspectionDateV1(iPropertyCollateral.getNextPhysicalInspectDate_v1());
		valuation1.setRemarksPropertyV1(iPropertyCollateral.getRemarksProperty_v1());
		valuation1.setEnvironmentRiskyStatus(iPropertyCollateral.getEnvRiskyStatus());
		valuation1.setEnvironmentRiskDate(iPropertyCollateral.getEnvRiskyDate());
		valuation1.setTsrDate(iPropertyCollateral.getTsrDate());
		valuation1.setNextTsrDate(iPropertyCollateral.getNextTsrDate());
		valuation1.setTsrFrequency(iPropertyCollateral.getTsrFrequency());
		valuation1.setConstitution(iPropertyCollateral.getConstitution());
		valuation1.setEnvironmentRiskRemark(iPropertyCollateral.getEnvRiskyRemarks());
		
		//set the new details legalAuditDate etc
		valuation1.setLegalAuditDate(iPropertyCollateral.getLegalAuditDate());
		valuation1.setInterveingPeriSerachDate(iPropertyCollateral.getInterveingPeriSearchDate());
		valuation1.setDateOfReceiptTitleDeed(iPropertyCollateral.getDateOfReceiptTitleDeed());
		valuation1.setValCreationDateV1(iPropertyCollateral.getValcreationdate_v1());
	
		valuation1.setPropertyUsage(iPropertyCollateral.getPropertyUsage());
		valuation1.setMortageRegisteredRef(iPropertyCollateral.getMortageRegisteredRef());
		valuation1.setRevalOverride(iPropertyCollateral.getRevalOverride());
		return valuation1; 
	}
	
	private PropertyValuation2 setValuation2(IPropertyCollateral iPropertyCollateral,String name) {
		PropertyValuation2 valuation2=new PropertyValuation2();
		if("Update".equals(name)){
			valuation2.setId(new Long(iPropertyCollateral.getVal2_id()));
		}
		valuation2.setPreviousMortCreationDate(iPropertyCollateral.getPreviousMortCreationDate());
		valuation2.setCollateralId(iPropertyCollateral.getCollateralID());
		valuation2.setSubTypeName(iPropertyCollateral.getCollateralSubType().getSubTypeName());
		valuation2.setTypeName(iPropertyCollateral.getCollateralType().getTypeName());
		valuation2.setSciRefNote(iPropertyCollateral.getSCIReferenceNote());
		valuation2.setSecurityCurrency(iPropertyCollateral.getCurrencyCode());
		valuation2.setSecPriority(iPropertyCollateral.getSecPriority());
		valuation2.setMonitorProcess(iPropertyCollateral.getMonitorProcess());
		valuation2.setMonitorFrequency(iPropertyCollateral.getMonitorFrequency());
		valuation2.setSecLocation(iPropertyCollateral.getCollateralLocation());
		valuation2.setSecOrganization(iPropertyCollateral.getSecurityOrganization());
		valuation2.setCollateralCode(iPropertyCollateral.getCollateralCode());
		valuation2.setRevalFrequency(iPropertyCollateral.getCommonRevalFreq());
		valuation2.setNextValuationDate(iPropertyCollateral.getNextValDate());
		valuation2.setChangeType(iPropertyCollateral.getTypeOfChange());
		valuation2.setOtherBankCharge(iPropertyCollateral.getOtherBankCharge());
		valuation2.setSecOwnership(iPropertyCollateral.getSecurityOwnership());
		valuation2.setOwnerOfProperty(iPropertyCollateral.getOwnerOfProperty());
		valuation2.setThirdPartyEntity(iPropertyCollateral.getThirdPartyEntity());
		valuation2.setCinThirdParty(iPropertyCollateral.getCinForThirdParty());
		valuation2.setCersaiTrxRefNo(iPropertyCollateral.getCersaiTransactionRefNumber());
		valuation2.setCersaiSecurityInterestId(iPropertyCollateral.getCersaiSecurityInterestId());
		valuation2.setCersaiAssetId(iPropertyCollateral.getCersaiAssetId());
		valuation2.setDateCersaiRegi(iPropertyCollateral.getDateOfCersaiRegisteration());
		valuation2.setCersaiRegDate(iPropertyCollateral.getCersiaRegistrationDate());
		valuation2.setCersaiId(iPropertyCollateral.getCersaiId());
		valuation2.setSaleDeedPurchaseDate(iPropertyCollateral.getSaleDeedPurchaseDate());
		valuation2.setThirdPartyAddress(iPropertyCollateral.getThirdPartyAddress());
		valuation2.setThirdPartyState(iPropertyCollateral.getThirdPartyState());
		valuation2.setThirdPartyCity(iPropertyCollateral.getThirdPartyCity());
		valuation2.setThirdPartyPincode(iPropertyCollateral.getThirdPartyPincode());
		valuation2.setPropertyId(iPropertyCollateral.getPropertyId());
		valuation2.setDescOfAsset(iPropertyCollateral.getDescription());
		valuation2.setPropertyType(iPropertyCollateral.getPropertyType());
		
		if(null!=iPropertyCollateral.getSalePurchaseValue())
		valuation2.setSalePurchaseValue(iPropertyCollateral.getSalePurchaseValue().getAmountAsBigDecimal().toString());
		
		valuation2.setSalePurchaseDate(iPropertyCollateral.getSalePurchaseDate());
		valuation2.setMortgageType(iPropertyCollateral.getTypeOfMargage());
		valuation2.setMortgagecretaedBy(iPropertyCollateral.getMorgageCreatedBy());
		valuation2.setDocumentReceived(iPropertyCollateral.getDocumentReceived());
		valuation2.setDocumentBlock(iPropertyCollateral.getDocumentReceived());
		valuation2.setBinNumber(iPropertyCollateral.getBinNumber());
		valuation2.setClaim(iPropertyCollateral.getClaim());
		valuation2.setClaimType(iPropertyCollateral.getClaimType());
		valuation2.setAdvocateLawyerName(iPropertyCollateral.getAdvocateLawyerName());
		valuation2.setDeveloperGroupCompany(iPropertyCollateral.getDevGrpCo());
		valuation2.setLotNo(iPropertyCollateral.getLotNo());
		valuation2.setLotNumberPrefix(iPropertyCollateral.getLotNumberPrefix());
		valuation2.setPropertyLotLocation(iPropertyCollateral.getPropertyLotLocation());
		valuation2.setOtherCity(iPropertyCollateral.getOtherCity());
		valuation2.setPropertyAddress(iPropertyCollateral.getPropertyAddress());
		valuation2.setPropertyAddress2(iPropertyCollateral.getPropertyAddress2());
		valuation2.setPropertyAddress3(iPropertyCollateral.getPropertyAddress3());
		valuation2.setPropertyAddress4(iPropertyCollateral.getPropertyAddress4());
		valuation2.setPropertyAddress5(iPropertyCollateral.getPropertyAddress5());
		valuation2.setPropertyAddress6(iPropertyCollateral.getPropertyAddress6());
		valuation2.setProjectName(iPropertyCollateral.getProjectName());
		valuation2.setValuationDateV2(iPropertyCollateral.getValuationDate_v2());
		valuation2.setValuatorCompanyV2(iPropertyCollateral.getValuatorCompany_v2());
		
		if(null!=iPropertyCollateral.getTotalPropertyAmount_v2())
		valuation2.setTotalPropertyAmontV2(iPropertyCollateral.getTotalPropertyAmount_v2().getAmountAsBigDecimal().toString());
		valuation2.setCategoryOfLandUseV2(iPropertyCollateral.getCategoryOfLandUse_v2());
		valuation2.setDeveloperNameV2(iPropertyCollateral.getDeveloperName_v2());
		valuation2.setCountryV2(iPropertyCollateral.getCountry_v2());
		valuation2.setRegionV2(iPropertyCollateral.getRegion_v2());
		valuation2.setStateV2(iPropertyCollateral.getLocationState_v2());
		valuation2.setNearestCityV2(iPropertyCollateral.getNearestCity_v2());
		valuation2.setPincodeV2(iPropertyCollateral.getPinCode_v2());
		valuation2.setLandAreaV2(iPropertyCollateral.getLandArea_v2());
		valuation2.setLandAreaUOMV2(iPropertyCollateral.getLandAreaUOM_v2());
		valuation2.setInSqfLandAreaV2(iPropertyCollateral.getInSqftLandArea_v2());
		valuation2.setBuildupAreaV2(iPropertyCollateral.getBuiltupArea_v2());
		valuation2.setBuildupAreaUOMV2(iPropertyCollateral.getBuiltupAreaUOM_v2());
		valuation2.setInSqfbuildupAreaV2(iPropertyCollateral.getInSqftBuiltupArea_v2());
		valuation2.setPropertyCompletionStatusV2(iPropertyCollateral.getPropertyCompletionStatus_v2());
		valuation2.setLandValueIRBV2(iPropertyCollateral.getLandValue_v2());
		valuation2.setBuildingValueIRBV2(iPropertyCollateral.getBuildingValue_v2());
		valuation2.setReconstructionValueIRBV2(iPropertyCollateral.getReconstructionValueOfTheBuilding_v2());
		valuation2.setIsPhyInspectionV2(iPropertyCollateral.getIsPhysicalInspection_v2());
		valuation2.setPhyInspectionFreqUnitV2(iPropertyCollateral.getPhysicalInspectionFreqUnit_v2());
		valuation2.setLastPhyInspectionDateV2(iPropertyCollateral.getLastPhysicalInspectDate_v2());
		valuation2.setNextPhyInspectionDateV2(iPropertyCollateral.getNextPhysicalInspectDate_v2());
		valuation2.setRemarksPropertyV2(iPropertyCollateral.getRemarksProperty_v2());
		valuation2.setEnvironmentRiskyStatus(iPropertyCollateral.getEnvRiskyStatus());
		valuation2.setEnvironmentRiskDate(iPropertyCollateral.getEnvRiskyDate());
		valuation2.setTsrDate(iPropertyCollateral.getTsrDate());
		valuation2.setNextTsrDate(iPropertyCollateral.getNextTsrDate());
		valuation2.setTsrFrequency(iPropertyCollateral.getTsrFrequency());
		valuation2.setConstitution(iPropertyCollateral.getConstitution());
		valuation2.setEnvironmentRiskRemark(iPropertyCollateral.getEnvRiskyRemarks());
		
		//set the new details legalAuditDate etc
		valuation2.setLegalAuditDate(iPropertyCollateral.getLegalAuditDate());
		valuation2.setInterveingPeriSerachDate(iPropertyCollateral.getInterveingPeriSearchDate());
		valuation2.setDateOfReceiptTitleDeed(iPropertyCollateral.getDateOfReceiptTitleDeed());
		valuation2.setValCreationDateV2(iPropertyCollateral.getValcreationdate_v2());
	
		valuation2.setPropertyUsage(iPropertyCollateral.getPropertyUsage());
		valuation2.setMortageRegisteredRef(iPropertyCollateral.getMortageRegisteredRef());
		valuation2.setDeferral(iPropertyCollateral.getDeferral() == null ? "off" : iPropertyCollateral.getDeferral().trim());
		valuation2.setDeferralId(iPropertyCollateral.getDeferralId());
		valuation2.setWaiver(iPropertyCollateral.getWaiver() == null ? "off" : iPropertyCollateral.getWaiver().trim());
		return valuation2; 
	}
	
	private PropertyValuation3 setValuation3(IPropertyCollateral iPropertyCollateral,String name) {
		PropertyValuation3 valuation3=new PropertyValuation3();
		if("Update".equals(name)){
			valuation3.setId(new Long(iPropertyCollateral.getVal3_id()));
		}
		valuation3.setPreviousMortCreationDate(iPropertyCollateral.getPreviousMortCreationDate());
		valuation3.setCollateralId(iPropertyCollateral.getCollateralID());
		valuation3.setSubTypeName(iPropertyCollateral.getCollateralSubType().getSubTypeName());
		valuation3.setTypeName(iPropertyCollateral.getCollateralType().getTypeName());
		valuation3.setSciRefNote(iPropertyCollateral.getSCIReferenceNote());
		valuation3.setSecurityCurrency(iPropertyCollateral.getCurrencyCode());
		valuation3.setSecPriority(iPropertyCollateral.getSecPriority());
		valuation3.setMonitorProcess(iPropertyCollateral.getMonitorProcess());
		valuation3.setMonitorFrequency(iPropertyCollateral.getMonitorFrequency());
		valuation3.setSecLocation(iPropertyCollateral.getCollateralLocation());
		valuation3.setSecOrganization(iPropertyCollateral.getSecurityOrganization());
		valuation3.setCollateralCode(iPropertyCollateral.getCollateralCode());
		valuation3.setRevalFrequency(iPropertyCollateral.getCommonRevalFreq());
		valuation3.setNextValuationDate(iPropertyCollateral.getNextValDate());
		valuation3.setChangeType(iPropertyCollateral.getTypeOfChange());
		valuation3.setOtherBankCharge(iPropertyCollateral.getOtherBankCharge());
		valuation3.setSecOwnership(iPropertyCollateral.getSecurityOwnership());
		valuation3.setOwnerOfProperty(iPropertyCollateral.getOwnerOfProperty());
		valuation3.setThirdPartyEntity(iPropertyCollateral.getThirdPartyEntity());
		valuation3.setCinThirdParty(iPropertyCollateral.getCinForThirdParty());
		valuation3.setCersaiTrxRefNo(iPropertyCollateral.getCersaiTransactionRefNumber());
		valuation3.setCersaiSecurityInterestId(iPropertyCollateral.getCersaiSecurityInterestId());
		valuation3.setCersaiAssetId(iPropertyCollateral.getCersaiAssetId());
		valuation3.setDateCersaiRegi(iPropertyCollateral.getDateOfCersaiRegisteration());
		valuation3.setCersaiRegDate(iPropertyCollateral.getCersiaRegistrationDate());
		valuation3.setCersaiId(iPropertyCollateral.getCersaiId());
		valuation3.setSaleDeedPurchaseDate(iPropertyCollateral.getSaleDeedPurchaseDate());
		valuation3.setThirdPartyAddress(iPropertyCollateral.getThirdPartyAddress());
		valuation3.setThirdPartyState(iPropertyCollateral.getThirdPartyState());
		valuation3.setThirdPartyCity(iPropertyCollateral.getThirdPartyCity());
		valuation3.setThirdPartyPincode(iPropertyCollateral.getThirdPartyPincode());
		valuation3.setPropertyId(iPropertyCollateral.getPropertyId());
		valuation3.setDescOfAsset(iPropertyCollateral.getDescription());
		valuation3.setPropertyType(iPropertyCollateral.getPropertyType());
		
		if(null!=iPropertyCollateral.getSalePurchaseValue())
		valuation3.setSalePurchaseValue(iPropertyCollateral.getSalePurchaseValue().getAmountAsBigDecimal().toString());
		
		valuation3.setSalePurchaseDate(iPropertyCollateral.getSalePurchaseDate());
		valuation3.setMortgageType(iPropertyCollateral.getTypeOfMargage());
		valuation3.setMortgagecretaedBy(iPropertyCollateral.getMorgageCreatedBy());
		valuation3.setDocumentReceived(iPropertyCollateral.getDocumentReceived());
		valuation3.setDocumentBlock(iPropertyCollateral.getDocumentReceived());
		valuation3.setBinNumber(iPropertyCollateral.getBinNumber());
		valuation3.setClaim(iPropertyCollateral.getClaim());
		valuation3.setClaimType(iPropertyCollateral.getClaimType());
		valuation3.setAdvocateLawyerName(iPropertyCollateral.getAdvocateLawyerName());
		valuation3.setDeveloperGroupCompany(iPropertyCollateral.getDevGrpCo());
		valuation3.setLotNo(iPropertyCollateral.getLotNo());
		valuation3.setLotNumberPrefix(iPropertyCollateral.getLotNumberPrefix());
		valuation3.setPropertyLotLocation(iPropertyCollateral.getPropertyLotLocation());
		valuation3.setOtherCity(iPropertyCollateral.getOtherCity());
		valuation3.setPropertyAddress(iPropertyCollateral.getPropertyAddress());
		valuation3.setPropertyAddress2(iPropertyCollateral.getPropertyAddress2());
		valuation3.setPropertyAddress3(iPropertyCollateral.getPropertyAddress3());
		valuation3.setPropertyAddress4(iPropertyCollateral.getPropertyAddress4());
		valuation3.setPropertyAddress5(iPropertyCollateral.getPropertyAddress5());
		valuation3.setPropertyAddress6(iPropertyCollateral.getPropertyAddress6());
		valuation3.setProjectName(iPropertyCollateral.getProjectName());
		valuation3.setValuationDateV3(iPropertyCollateral.getValuationDate_v3());
		valuation3.setValuatorCompanyV3(iPropertyCollateral.getValuatorCompany_v3());
		
		if(null!=iPropertyCollateral.getTotalPropertyAmount_v3())
		valuation3.setTotalPropertyAmontV3(iPropertyCollateral.getTotalPropertyAmount_v3().getAmountAsBigDecimal().toString());
		valuation3.setCategoryOfLandUseV3(iPropertyCollateral.getCategoryOfLandUse_v3());
		valuation3.setDeveloperNameV3(iPropertyCollateral.getDeveloperName_v3());
		valuation3.setCountryV3(iPropertyCollateral.getCountry_v3());
		valuation3.setRegionV3(iPropertyCollateral.getRegion_v3());
		valuation3.setStateV3(iPropertyCollateral.getLocationState_v3());
		valuation3.setNearestCityV3(iPropertyCollateral.getNearestCity_v3());
		valuation3.setPincodeV3(iPropertyCollateral.getPinCode_v3());
		valuation3.setLandAreaV3(iPropertyCollateral.getLandArea_v3());
		valuation3.setLandAreaUOMV3(iPropertyCollateral.getLandAreaUOM_v3());
		valuation3.setInSqfLandAreaV3(iPropertyCollateral.getInSqftLandArea_v3());
		valuation3.setBuildupAreaV3(iPropertyCollateral.getBuiltupArea_v3());
		valuation3.setBuildupAreaUOMV3(iPropertyCollateral.getBuiltupAreaUOM_v3());
		valuation3.setInSqfbuildupAreaV3(iPropertyCollateral.getInSqftBuiltupArea_v3());
		valuation3.setPropertyCompletionStatusV3(iPropertyCollateral.getPropertyCompletionStatus_v3());
		valuation3.setLandValueIRBV3(iPropertyCollateral.getLandValue_v3());
		valuation3.setBuildingValueIRBV3(iPropertyCollateral.getBuildingValue_v3());
		valuation3.setReconstructionValueIRBV3(iPropertyCollateral.getReconstructionValueOfTheBuilding_v3());
		valuation3.setIsPhyInspectionV3(iPropertyCollateral.getIsPhysicalInspection_v3());
		valuation3.setPhyInspectionFreqUnitV3(iPropertyCollateral.getPhysicalInspectionFreqUnit_v3());
		valuation3.setLastPhyInspectionDateV3(iPropertyCollateral.getLastPhysicalInspectDate_v3());
		valuation3.setNextPhyInspectionDateV3(iPropertyCollateral.getNextPhysicalInspectDate_v3());
		valuation3.setRemarksPropertyV3(iPropertyCollateral.getRemarksProperty_v3());
		valuation3.setEnvironmentRiskyStatus(iPropertyCollateral.getEnvRiskyStatus());
		valuation3.setEnvironmentRiskDate(iPropertyCollateral.getEnvRiskyDate());
		valuation3.setTsrDate(iPropertyCollateral.getTsrDate());
		valuation3.setNextTsrDate(iPropertyCollateral.getNextTsrDate());
		valuation3.setTsrFrequency(iPropertyCollateral.getTsrFrequency());
		valuation3.setConstitution(iPropertyCollateral.getConstitution());
		valuation3.setEnvironmentRiskRemark(iPropertyCollateral.getEnvRiskyRemarks());
		
		//set the new details legalAuditDate etc
		valuation3.setLegalAuditDate(iPropertyCollateral.getLegalAuditDate());
		valuation3.setInterveingPeriSerachDate(iPropertyCollateral.getInterveingPeriSearchDate());
		valuation3.setDateOfReceiptTitleDeed(iPropertyCollateral.getDateOfReceiptTitleDeed());
		valuation3.setValCreationDateV3(iPropertyCollateral.getValcreationdate_v3());
		valuation3.setPropertyUsage(iPropertyCollateral.getPropertyUsage());
		valuation3.setMortageRegisteredRef(iPropertyCollateral.getMortageRegisteredRef());
	
	
		return valuation3;
	}
	
	private boolean isValidRequest(String pathInfo, String securityID,
			ICollateralDAO collateralDAO) {

		String ColSubTypeId = "";
		ColSubTypeId = collateralDAO.getSubtypeCodeFromSecurity(securityID);

		if (null != ColSubTypeId && !ColSubTypeId.trim().isEmpty()) {
			if (pathInfo.startsWith(
					"/" + WholeSaleApi_Constant.UPDATE_GUARANTEE_CORPORATE)) {
				if ("GT405".equalsIgnoreCase(ColSubTypeId))
					return true;
			} else if (pathInfo.startsWith(
					"/" + WholeSaleApi_Constant.UPDATE_GUARANTEE_INDIVIDUAL)) {
				if ("GT408".equalsIgnoreCase(ColSubTypeId))
					return true;
			} else if (pathInfo.startsWith(
					"/" + WholeSaleApi_Constant.UPDATE_GUARANTEE_GOVERNMENT)) {
				if ("GT406".equalsIgnoreCase(ColSubTypeId))
					return true;
			} else if (pathInfo.startsWith("/"
					+ WholeSaleApi_Constant.UPDATE_AB_SPECIFIC_ASSET_SECURITY)) {
				if ("AB109".equalsIgnoreCase(ColSubTypeId))
					return true;
			} else if (pathInfo
					.startsWith("/" + WholeSaleApi_Constant.UPDATE_PROPERTY)) {
				if ("PT701".equalsIgnoreCase(ColSubTypeId))
					return true;
			} else if (pathInfo.startsWith(
					"/" + WholeSaleApi_Constant.UPDATE_MARKETABLE_SECURITY)) {
				if ("MS605".equalsIgnoreCase(ColSubTypeId))
					return true;
			}
			return false;
		} else {
			return false;
		}
	}
	
	private boolean isValidDeleteRequest(String pathInfo, String securityID,
			ICollateralDAO collateralDAO) {

		String ColSubTypeId = "";
		ColSubTypeId = collateralDAO.getSubtypeCodeFromSecurity(securityID);

		if (null != ColSubTypeId && !ColSubTypeId.trim().isEmpty()) {
			if (pathInfo.startsWith(
					"/" + WholeSaleApi_Constant.DELETE_GUARANTEE_CORPORATE)) {
				if ("GT405".equalsIgnoreCase(ColSubTypeId))
					return true;
			} else if (pathInfo.startsWith(
					"/" + WholeSaleApi_Constant.DELETE_GUARANTEE_INDIVIDUAL)) {
				if ("GT408".equalsIgnoreCase(ColSubTypeId))
					return true;
			} else if (pathInfo.startsWith(
					"/" + WholeSaleApi_Constant.DELETE_GUARANTEE_GOVERNMENT)) {
				if ("GT406".equalsIgnoreCase(ColSubTypeId))
					return true;
			} else if (pathInfo.startsWith("/"
					+ WholeSaleApi_Constant.DELETE_AB_SPECIFIC_ASSET_SECURITY)) {
				if ("AB109".equalsIgnoreCase(ColSubTypeId))
					return true;
			} else if (pathInfo
					.startsWith("/" + WholeSaleApi_Constant.DELETE_PROPERTY)) {
				if ("PT701".equalsIgnoreCase(ColSubTypeId))
					return true;
			} else if (pathInfo.startsWith(
					"/" + WholeSaleApi_Constant.DELETE_MARKETABLE_SECURITY)) {
				if ("MS605".equalsIgnoreCase(ColSubTypeId))
					return true;
			}
			return false;
		} else {
			return false;
		}
	}

	
	public CollateralRestResponseDTO deleteSecurityDetails(
			CollateralDeleteEnquiryRestRequestDTO colDelRestRequestDTO,
			String pathInfo) {

		BodyRestResponseDTO bodyObj = new BodyRestResponseDTO();

		CollateralRestResponseDTO securityResponse = new CollateralRestResponseDTO();
		ICollateralDAO collateralDAO = (ICollateralDAO) BeanHouse
				.get("collateralDao");

		HeaderDetailRestResponseDTO headerObj = new HeaderDetailRestResponseDTO();
		List<HeaderDetailRestResponseDTO> ccHeaderResponse = new LinkedList<HeaderDetailRestResponseDTO>();

		CollateralDeleteEnquiryRestRequestDTO colDelRestReqInstance = new CollateralDeleteEnquiryRestRequestDTO();
		
		

		List<BodyRestResponseDTO> BodyRestResponseDTOList = new ArrayList<BodyRestResponseDTO>();
		List<ResponseMessageDetailDTO> responseMessageDetailDTOList = new ArrayList<ResponseMessageDetailDTO>();

		List<ValidationErrorDetailsDTO> validationErrorDetailsDTOList;
		headerObj.setRequestId(
				colDelRestRequestDTO.getHeaderDetails().get(0).getRequestId());
		headerObj.setChannelCode(colDelRestRequestDTO.getHeaderDetails().get(0)
				.getChannelCode());
		ccHeaderResponse.add(headerObj);
		securityResponse.setHeaderDetails(ccHeaderResponse);

		CollateralDelEnqDetailslRequestDTO colDelDetailslRestRequestDTO = colDelRestRequestDTO
				.getBodyDetails().get(0);
		String securityId = "";

		if (colDelDetailslRestRequestDTO.getSecurityId() != null
				&& !colDelDetailslRestRequestDTO.getSecurityId().trim()
						.isEmpty()) {
			if (ASSTValidator
					.isNumeric(colDelDetailslRestRequestDTO.getSecurityId())) {
				securityId = colDelDetailslRestRequestDTO.getSecurityId()
						.trim();
				boolean securitypresent = collateralDAO
						.checkSecurityId(securityId);
				if (securityId.length() > 19) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(ResponseConstants.SEC_ID_LEN);
					rmd.setResponseMessage(
							ResponseConstants.SEC_ID_LEN_MESSAGE);
					responseMessageDetailDTOList.add(rmd);
				}

				if (!securitypresent) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(ResponseConstants.SEC_STATUS_INVALID);
					rmd.setResponseMessage(
							ResponseConstants.SEC_STATUS_INVALID_MESSAGE);
					responseMessageDetailDTOList.add(rmd);
				}

				if (securitypresent && !isValidDeleteRequest(pathInfo,
						securityId, collateralDAO)) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(ResponseConstants.SEC_ID_PATH_INVALID);
					rmd.setResponseMessage(
							ResponseConstants.SEC_ID_PATH_INVALID_MESSAGE);
					responseMessageDetailDTOList.add(rmd);
				}

			} else {
				ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
				rmd.setResponseCode(ResponseConstants.SEC_ID_NUM);
				rmd.setResponseMessage(ResponseConstants.SEC_ID_NUM_MESSAGE);
				responseMessageDetailDTOList.add(rmd);
			}
		} else {
			ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
			rmd.setResponseCode(ResponseConstants.SEC_MISSING);
			rmd.setResponseMessage(ResponseConstants.SEC_MISSING_MESSAGE);
			responseMessageDetailDTOList.add(rmd);
		}
		/*
		 * String ColSubTypeId = collateralDAO
		 * .getSubtypeCodeFromSecurity(securityId); String[] colarray =
		 * {"MS605", "GT405", "PT701", "GT406", "GT408", "AB109"}; List<String>
		 * colList = Arrays.asList(colarray);
		 * 
		 * if (!colList.contains(ColSubTypeId)) { ResponseMessageDetailDTO rmd =
		 * new ResponseMessageDetailDTO();
		 * rmd.setResponseCode(ResponseConstants.SEC_INVALID_SUBTYPE);
		 * rmd.setResponseMessage(
		 * ResponseConstants.SEC_INVALID_SUBTYPE_MESSAGE);
		 * responseMessageDetailDTOList.add(rmd); }
		 */

		if (responseMessageDetailDTOList.size() > 0) {
			bodyObj.setSecurityId(colDelDetailslRestRequestDTO.getSecurityId());
			bodyObj.setResponseStatus("FAILED");
			bodyObj.setResponseMessageList(responseMessageDetailDTOList);
			BodyRestResponseDTOList.add(bodyObj);
			securityResponse.setBodyDetails(BodyRestResponseDTOList);
			return securityResponse;
		}
		List<ResponseMessageDetailDTO> responseMessageList = new LinkedList<ResponseMessageDetailDTO>();
		ICollateralProxy proxy = CollateralProxyFactory.getProxy();
		ICollateralTrxValue itrxValue = new OBCollateralTrxValue();
		ICollateralTrxValue itrxResult = new OBCollateralTrxValue();
		boolean isDeletable = false;
		boolean isDeleting = false;
		OBTrxContext trxContext = new OBTrxContext();
		String colId = colDelRestRequestDTO.getBodyDetails().get(0)
				.getSecurityId();
		Long colID = Long.parseLong(colId);
		ICollateral col = null;
		ICollateral colNew = new OBCollateral();

		try {
			col = CollateralProxyFactory.getProxy().getCollateral(colID, true);
			if (col != null) {
				try {
					colNew = (ICollateral) securityDetailsRestDTOMapper
							.getActualForDeleteFromDTORest(
									colDelRestRequestDTO, col);

					itrxValue = CollateralProxyFactory.getProxy()
							.getCollateralTrxValue(trxContext, colID);
					if (null != itrxValue) {
						itrxValue.getStagingCollateral().setCollateralStatus(
								ICMSConstant.HOST_COL_STATUS_DELETED);
						ICollateralLimitMap[] slmArray = colNew
								.getCollateralLimits();
						int o = 0;
						if (slmArray != null && slmArray.length > 0) {
							System.out.println(
									"MMMMMM Size of SLM : " + slmArray.length);
							for (int index = 0; index < slmArray.length; index++) {
								if ("D".equalsIgnoreCase(
										slmArray[index].getSCIStatus())) {
									o++;
								}
							}
						}
						if (slmArray != null && slmArray.length > 0) {
							if (slmArray.length == o) {
								isDeletable = true;
								System.out.println(
										"MMMMMM isDeletable : " + isDeletable);
							}
						}
						if (ICMSConstant.STATE_DELETED.equalsIgnoreCase(
								itrxValue.getCollateral().getStatus().trim())) {
							isDeleting = true;
						}
						if (isDeletable && !isDeleting) {
							if (!PropertiesConstantHelper.isSTPRequired()
									|| (PropertiesConstantHelper.isSTPRequired()
											&& !PropertiesConstantHelper
													.isValidSTPSystem(colNew
															.getSourceId()))) {
								if ((itrxValue.getStatus()
										.equals(ICMSConstant.STATE_REJECTED)
										|| itrxValue.getStatus().equals(
												ICMSConstant.STATE_PENDING_UPDATE)
										|| itrxValue.getStatus().equals(
												ICMSConstant.STATE_DRAFT)
										|| itrxValue.getStatus().equals(
												ICMSConstant.STATE_PENDING_DELETE))) {
									ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
									responseMessageDetailDTO
											.setResponseCode("SEC0003");
									responseMessageDetailDTO.setResponseMessage(
											"Cannot delete security, Kindly check security status");
									responseMessageList
											.add(responseMessageDetailDTO);
									bodyObj.setResponseStatus("FAILED");
									bodyObj.setResponseMessageList(
											responseMessageList);
									BodyRestResponseDTOList.add(bodyObj);
									headerObj.setRequestId(colDelRestRequestDTO
											.getHeaderDetails().get(0)
											.getRequestId());
									headerObj.setChannelCode(
											colDelRestRequestDTO
													.getHeaderDetails().get(0)
													.getChannelCode());
									ccHeaderResponse.add(headerObj);
									securityResponse
											.setHeaderDetails(ccHeaderResponse);
									securityResponse.setBodyDetails(
											BodyRestResponseDTOList);
									return securityResponse;
								}
								itrxResult = proxy
										.deleteCollateralWithApprovalThroughREST(
												trxContext, itrxValue, colNew);
								ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
								responseMessageDetailDTO
										.setResponseCode("SECDEL001");
								responseMessageDetailDTO.setResponseMessage(
										"Security is deleted successfully");
								responseMessageList
										.add(responseMessageDetailDTO);
								bodyObj.setResponseStatus("SUCCESS");
								bodyObj.setSecurityId(securityId);
								bodyObj.setResponseMessageList(
										responseMessageList);
								BodyRestResponseDTOList.add(bodyObj);
								securityResponse.setBodyDetails(
										BodyRestResponseDTOList);
								return securityResponse;
							} else {

								ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
								responseMessageDetailDTO
										.setResponseCode("SECDEL0003");
								responseMessageDetailDTO.setResponseMessage(
										"Cannot delete security, Kindly check STP Required and ValidSTPSystem");
								responseMessageList
										.add(responseMessageDetailDTO);
								bodyObj.setResponseStatus("FAILED");
								bodyObj.setResponseMessageList(
										responseMessageList);
								BodyRestResponseDTOList.add(bodyObj);
								headerObj.setRequestId(
										colDelRestRequestDTO.getHeaderDetails()
												.get(0).getRequestId());
								headerObj.setChannelCode(
										colDelRestRequestDTO.getHeaderDetails()
												.get(0).getChannelCode());
								ccHeaderResponse.add(headerObj);
								securityResponse
										.setHeaderDetails(ccHeaderResponse);
								securityResponse.setBodyDetails(
										BodyRestResponseDTOList);
								return securityResponse;

							}

						} else {
							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO
									.setResponseCode("SECDEL0004");
							responseMessageDetailDTO.setResponseMessage(
									"Cannot delete security, Kindly check security status and security linkage with facility");
							responseMessageList.add(responseMessageDetailDTO);
							bodyObj.setResponseStatus("FAILED");
							bodyObj.setResponseMessageList(responseMessageList);
							BodyRestResponseDTOList.add(bodyObj);
							headerObj.setRequestId(colDelRestRequestDTO
									.getHeaderDetails().get(0).getRequestId());
							headerObj.setChannelCode(
									colDelRestRequestDTO.getHeaderDetails()
											.get(0).getChannelCode());
							ccHeaderResponse.add(headerObj);
							securityResponse.setHeaderDetails(ccHeaderResponse);
							securityResponse
									.setBodyDetails(BodyRestResponseDTOList);
							return securityResponse;
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

		} catch (CollateralException e) {

			e.printStackTrace();
		}
		return securityResponse;
	}
	public Object enqirySecurityDetails(
			CollateralDeleteEnquiryRestRequestDTO colEnqRestRequestDTO,
			String pathInfo) {

		ABEnquiryResponseDTO AbMainRes = new ABEnquiryResponseDTO();
		//CollateralEnqiryDetailsResponseDTO bodyObj = new CollateralEnqiryDetailsResponseDTO();

		
		ICollateralDAO collateralDAO = (ICollateralDAO) BeanHouse
				.get("collateralDao");
		CollateralEnqiryDetailsResponseDTO colEnqDetailsResponseDTO = new ABSpecEnqDetailsResponseDTO();
		ABSpecEnqDetailsResponseDTO ABEnqRes = new ABSpecEnqDetailsResponseDTO();
		ABEnqRes = (ABSpecEnqDetailsResponseDTO)colEnqDetailsResponseDTO;
		HeaderDetailRestResponseDTO headerObj = new HeaderDetailRestResponseDTO();
		List<HeaderDetailRestResponseDTO> ccHeaderResponse = new LinkedList<HeaderDetailRestResponseDTO>();

		//CollateralDeleteEnquiryRestRequestDTO colDelRestReqInstance = new CollateralDeleteEnquiryRestRequestDTO();
		
		

		List CollateralEnquiryResList = new ArrayList();
		List<ResponseMessageDetailDTO> responseMessageDetailDTOList = new ArrayList<ResponseMessageDetailDTO>();

		List<ValidationErrorDetailsDTO> validationErrorDetailsDTOList;
		headerObj.setRequestId(
				colEnqRestRequestDTO.getHeaderDetails().get(0).getRequestId());
		headerObj.setChannelCode(colEnqRestRequestDTO.getHeaderDetails().get(0)
				.getChannelCode());
		ccHeaderResponse.add(headerObj);
		AbMainRes.setHeaderDetails(ccHeaderResponse);

		CollateralDelEnqDetailslRequestDTO colDelDetailslRestRequestDTO = colEnqRestRequestDTO
				.getBodyDetails().get(0);
		String securityId = "";

		if (colDelDetailslRestRequestDTO.getSecurityId() != null
				&& !colDelDetailslRestRequestDTO.getSecurityId().trim()
						.isEmpty()) {
			if (ASSTValidator
					.isNumeric(colDelDetailslRestRequestDTO.getSecurityId())) {
				securityId = colDelDetailslRestRequestDTO.getSecurityId()
						.trim();
				boolean securitypresent = collateralDAO
						.checkSecurityId(securityId);
				if (securityId.length() > 19) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(ResponseConstants.SEC_ID_LEN);
					rmd.setResponseMessage(
							ResponseConstants.SEC_ID_LEN_MESSAGE);
					responseMessageDetailDTOList.add(rmd);
				}

				if (!securitypresent) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(ResponseConstants.SEC_STATUS_INVALID);
					rmd.setResponseMessage(
							ResponseConstants.SEC_STATUS_INVALID_MESSAGE);
					responseMessageDetailDTOList.add(rmd);
				}

			} else {
				ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
				rmd.setResponseCode(ResponseConstants.SEC_ID_NUM);
				rmd.setResponseMessage(ResponseConstants.SEC_ID_NUM_MESSAGE);
				responseMessageDetailDTOList.add(rmd);
			}
		} else {
			ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
			rmd.setResponseCode(ResponseConstants.SEC_MISSING);
			rmd.setResponseMessage(ResponseConstants.SEC_MISSING_MESSAGE);
			responseMessageDetailDTOList.add(rmd);
		}
		if (responseMessageDetailDTOList.size() > 0) {
			ABEnqRes.setSecurityId(colDelDetailslRestRequestDTO.getSecurityId());
			ABEnqRes.setResponseStatus("FAILED");
			ABEnqRes.setResponseMessageList(responseMessageDetailDTOList);
			CollateralEnquiryResList.add(ABEnqRes);
			AbMainRes.setBodyDetails(CollateralEnquiryResList);
			return AbMainRes;
		}
		List<ResponseMessageDetailDTO> responseMessageList = new LinkedList<ResponseMessageDetailDTO>();
		ICollateralProxy proxy = CollateralProxyFactory.getProxy();
		ICollateralTrxValue itrxValue = new OBCollateralTrxValue();
		ICollateralTrxValue itrxResult = new OBCollateralTrxValue();
		OBTrxContext trxContext = new OBTrxContext();
		String colId = colEnqRestRequestDTO.getBodyDetails().get(0)
				.getSecurityId();
		Long colID = Long.parseLong(colId);
		ICollateral col = null;
		ICollateral colNew = new OBCollateral();
		String ColSubTypeId = collateralDAO.getSubtypeCodeFromSecurity(securityId);
		try {
			col = CollateralProxyFactory.getProxy().getCollateral(colID, true);
			if (col != null) {
				itrxValue = CollateralProxyFactory.getProxy()
						.getCollateralTrxValue(trxContext, colID);
				if (null != itrxValue) {
					col = itrxValue.getCollateral();
					if ("AB109".equalsIgnoreCase(ColSubTypeId))
					{
					try {
						ABEnqRes = (ABSpecEnqDetailsResponseDTO) securityDetailsRestDTOMapper
								.getCommonRespForColObj(colEnqDetailsResponseDTO, col,ColSubTypeId);
						
						AbMainRes = securityDetailsRestDTOMapper
						.getAbSpecificAssetRespForColObj(ABEnqRes, col,ColSubTypeId);
						
						
						List<InsuranceDetailRestResponseDTO> insuranceResponseList =new ArrayList<InsuranceDetailRestResponseDTO>();
						insuranceResponseList=securityDetailsRestDTOMapper.getInsuranceForColObj(ABEnqRes, col, ColSubTypeId);
						
						
						ABEnqRes.setInsuranceResponseList(insuranceResponseList);
						List<ABSpecEnqDetailsResponseDTO> ABEnqResList = new ArrayList<ABSpecEnqDetailsResponseDTO>();
						
						
						ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
						responseMessageDetailDTO
								.setResponseCode("SECENQ001");
						responseMessageDetailDTO.setResponseMessage(
								"Security Details are shared successfully");
						responseMessageList
								.add(responseMessageDetailDTO);
						ABEnqRes.setResponseStatus("SUCCESS");
						ABEnqResList.add(ABEnqRes);
						AbMainRes.setBodyDetails(ABEnqResList);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return AbMainRes;
					}
				}
			}

		} catch (CollateralException e) {

			e.printStackTrace();
		}
		return AbMainRes;
	}
}
