package com.integrosys.cms.app.ws.dto;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.springframework.stereotype.Service;

import com.aurionpro.clims.rest.dto.FacilityBodyRestRequestDTO;
import com.aurionpro.clims.rest.dto.FacilityDetlRestRequestDTO;
import com.aurionpro.clims.rest.dto.FacilityLineDetailRestRequestDTO;
import com.aurionpro.clims.rest.dto.FacilitySCODDetailRestRequestDTO;
import com.aurionpro.clims.rest.mapper.UdfDetailsRestDTOMapper;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ILimitXRefCoBorrower;
import com.integrosys.cms.app.customer.bus.ILimitXRefUdf;
import com.integrosys.cms.app.customer.bus.ILimitXRefUdf2;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBLimitXRefCoBorrower;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.proxy.IGeneralParamProxy;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.riskType.bus.IRiskType;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.limit.LmtDetailForm;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;


@Service

public class FacilityDetailsDTOMapper {
	
	public OBLimit  getActualFromDTO( FacilityDetailRequestDTO dto ) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		SimpleDateFormat dateRequestFormat = new SimpleDateFormat("dd-MMM-yyyy");
		dateRequestFormat.setLenient(false);
		
		OBLimit lmtObj = new OBLimit();
//		MILimitUIHelper helper = new MILimitUIHelper();
//		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		
//		String facilityMasterId = "";
		if(dto.getFacilityCategoryId()!=null && !dto.getFacilityCategoryId().trim().isEmpty()){
			lmtObj.setFacilityCat(dto.getFacilityCategoryId());
		}
		if(dto.getFacilityTypeId()!=null && !dto.getFacilityTypeId().trim().isEmpty()){
			lmtObj.setFacilityType(dto.getFacilityTypeId());		
		}
		
//			lmtObj.setFacilityName(master.getNewFacilityName());		
//			lmtObj.setFacilityCode(master.getNewFacilityCode());
//			lmtObj.setFacilitySystem(master.getNewFacilitySystem());			
//			lmtObj.setLineNo(master.getLineNumber());		
//			lmtObj.setPurpose(master.getPurpose());		
//		}
		
		lmtObj.setIsReleased("N");
		
		if(dto.getSecured()!=null && !dto.getSecured().trim().isEmpty()){
			lmtObj.setIsSecured(dto.getSecured().trim());
		}else{
			lmtObj.setIsSecured("");
		}
		
		if(dto.getGrade()!=null && !dto.getGrade().trim().isEmpty()){
			lmtObj.setGrade(dto.getGrade().trim());		
		}else{
			lmtObj.setGrade("");		
		}
		
		lmtObj.setIsAdhoc("N");		
		lmtObj.setIsAdhocToSum("N");
		if(dto.getCurrency()!=null && !dto.getCurrency().trim().isEmpty()){
			lmtObj.setCurrencyCode(dto.getCurrency().trim());		
		}else{
			lmtObj.setCurrencyCode("INR");		
		}
		lmtObj.setGuarantee("No");	
		if(dto.getSubLimitFlag()!=null && !dto.getSubLimitFlag().trim().isEmpty()){
			lmtObj.setLimitType(dto.getSubLimitFlag().trim());
		}else{
			lmtObj.setLimitType("");
		}
		/*SBMILmtProxy proxy = helper.getSBMILmtProxy();
		ILimitTrxValue lmtTrxObj = null;
		
			try {
				lmtTrxObj = proxy.searchLimitByLmtId(dto.getMainFacilityId());
			} catch (LimitException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			if(lmtTrxObj!=null){
				ILimit limit = (ILimit)lmtTrxObj.getLimit();
				if(limit.getLimitRef()!=null && !limit.getLimitRef().isEmpty()){
					lmtObj.setMainFacilityId(limit.getLimitRef());
				}
			}
		*/
		
		if(dto.getMainFacilityId()!=null && !dto.getMainFacilityId().isEmpty()){
			lmtObj.setMainFacilityId(dto.getMainFacilityId());
		}else{
			lmtObj.setMainFacilityId("");
		}
		
		if(dto.getSubFacilityName()!=null && !dto.getSubFacilityName().trim().isEmpty()){
			lmtObj.setSubFacilityName(dto.getSubFacilityName().trim());
		}else{
			lmtObj.setSubFacilityName("");
		}
		
		lmtObj.setLimitStatus("ACTIVE");
		if(dto.getCamId()!=null && !dto.getCamId().trim().isEmpty()){
			lmtObj.setLimitProfileID(Long.parseLong(dto.getCamId().trim()));
		}else{
			lmtObj.setLimitProfileID(0L);
		}
		
		lmtObj.setBookingLocation(new OBBookingLocation());
		
		if(dto.getSanctionedAmount()!=null && !dto.getSanctionedAmount().trim().isEmpty()){
			lmtObj.setRequiredSecurityCoverage(dto.getSanctionedAmount().trim());
		}else{
			lmtObj.setRequiredSecurityCoverage("0");
		}
		
		lmtObj.setIsFromCamonlineReq('Y');
		
		 //New CAM ONLINE CR Changes Begin		
		if(dto.getRiskType() != null && !dto.getRiskType().trim().isEmpty()) {
			lmtObj.setRiskType(dto.getRiskType());
		}else {
			lmtObj.setRiskType("");
		}
		
		if(dto.getTenorUnit() != null && !dto.getTenorUnit().trim().isEmpty()) {
			lmtObj.setTenorUnit(dto.getTenorUnit().toUpperCase());
		}else {
			lmtObj.setTenorUnit("");
		}
		if(dto.getTenor() != null && !dto.getTenor().trim().isEmpty()) {
			lmtObj.setTenor(Long.parseLong(dto.getTenor()));			
		}else {
			lmtObj.setTenor(null);
		}


		if(dto.getTenorDesc() != null && !dto.getTenorDesc().trim().isEmpty()) {
			lmtObj.setTenorDesc(dto.getTenorDesc());			
		}else {
			lmtObj.setTenorDesc("");
		}

		if(dto.getMargin() != null && !dto.getMargin().trim().isEmpty()) {
			lmtObj.setMargin(Double.parseDouble(dto.getMargin()));			
		}else {
			lmtObj.setMargin(null);
		}

		if(dto.getPutCallOption() != null && !dto.getPutCallOption().trim().isEmpty()) {
			lmtObj.setPutCallOption(dto.getPutCallOption());			
		}else {
			lmtObj.setPutCallOption(null);
		}
				
		try {
			lmtObj.setOptionDate(dateRequestFormat.parse(dto.getOptionDate()));
		} catch (ParseException e) {
			e.printStackTrace();
			lmtObj.setOptionDate(null);
		}
		try {
			lmtObj.setLoanAvailabilityDate(dateRequestFormat.parse(dto.getLoanAvailabilityDate()));
		} catch (ParseException e) {
			e.printStackTrace();
			lmtObj.setLoanAvailabilityDate(null);
		}
		//New CAM ONLINE CR Changes End
		if(dto.getBankingArrangement()!=null && !dto.getBankingArrangement().trim().isEmpty()) {
			lmtObj.setBankingArrangement(dto.getBankingArrangement());			
		}else {
			lmtObj.setBankingArrangement("");
		}

		if(dto.getClauseAsPerPolicy()!=null && !dto.getClauseAsPerPolicy().trim().isEmpty()) {
			lmtObj.setClauseAsPerPolicy(dto.getClauseAsPerPolicy());
		}else {
			lmtObj.setClauseAsPerPolicy("");
		}

		return lmtObj;
	}

	public LmtDetailForm  getFormFromDTO( FacilityDetailRequestDTO dto,ICMSCustomer cust) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		SimpleDateFormat dateRequestFormat = new SimpleDateFormat("dd-MMM-yyyy");
		dateRequestFormat.setLenient(false);
		
		LmtDetailForm lmtDetailForm = new LmtDetailForm();	
		/*MILimitUIHelper helper = new MILimitUIHelper();
		ILimitProfile profile = new OBLimitProfile();*/
//		OBFacilityNewMaster master = new OBFacilityNewMaster();
//	    ICMSCustomer cust = null;
//		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		String camId = "";

		try {
			    /*ILimitProxy limitProxy = LimitProxyFactory.getProxy();*/
			    if(dto.getCamId()!=null && !dto.getCamId().trim().isEmpty()){
			    	camId = dto.getCamId().trim();
			    }
			    
			    /*
			    profile = limitProxy.getLimitProfile(Long.parseLong(camId));
				ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
				cust = custProxy.getCustomerByCIFSource(profile.getLEReference(),null);*/
	
				/*if(dto.getFacilityMasterId()!=null && !dto.getFacilityMasterId().trim().isEmpty()){
					master =(OBFacilityNewMaster)masterObj.getMaster("actualFacilityNewMaster",new Long(dto.getFacilityMasterId().trim()));
				}*/
				
				if(dto.getFacilityCategoryId()!=null && !dto.getFacilityCategoryId().trim().isEmpty()){
					lmtDetailForm.setFacilityCat(dto.getFacilityCategoryId().trim());
				}
				
				if(dto.getFacilityTypeId()!=null && !dto.getFacilityTypeId().trim().isEmpty()){
					lmtDetailForm.setFacilityType(dto.getFacilityTypeId().trim());
				}
		
				if(dto.getMainFacilityId()!=null && !dto.getMainFacilityId().trim().isEmpty()){
					lmtDetailForm.setMainFacilityId(dto.getMainFacilityId().trim());
				}
				
				lmtDetailForm.setCustomerID(String.valueOf(cust.getCustomerID()));
				lmtDetailForm.setFundedAmount("0");
				lmtDetailForm.setNonFundedAmount("0");
				lmtDetailForm.setMemoExposer("0");
				
				Double totalFacSanctionedAmount = 0d;
				String currentFacSanc = null;
				ILimitProxy proxyLimit = null;
				proxyLimit= LimitProxyFactory.getProxy();
				if(dto.getEvent()!=null && "WS_FAC_CREATE".equals(dto.getEvent()) || "WS_FAC_EDIT".equals(dto.getEvent())){
					if(dto.getClimsFacilityId()!=null && !dto.getClimsFacilityId().trim().isEmpty()){
						dto.setClimsFacilityId(dto.getClimsFacilityId().trim());
					}else{
						dto.setClimsFacilityId("");
					}
					DefaultLogger.debug(this, "dto.getClimsFacilityId() value inside FacilityDetailsDTOMapper:::"+dto.getClimsFacilityId()+">>>");
					currentFacSanc = proxyLimit.getTotalAmountByFacType(camId,dto.getFacilityTypeId(),dto.getClimsFacilityId());
				}
				
				if(currentFacSanc!=null){
					totalFacSanctionedAmount = Double.parseDouble(currentFacSanc);
				}
				Double totalPartySanctionedAmount = 0d;
				
				if(dto.getFacilityTypeId()!=null && !dto.getFacilityTypeId().trim().isEmpty()){
					if(dto.getFacilityTypeId().equals("FUNDED")){
						String currentPartySanc =cust.getTotalFundedLimit();
						totalPartySanctionedAmount = Double.parseDouble(currentPartySanc);
						Double difference = totalPartySanctionedAmount-totalFacSanctionedAmount;
						lmtDetailForm.setFundedAmount(String.valueOf(difference));
					}else if(dto.getFacilityTypeId().equals("NON_FUNDED")){
						String currentPartySanc =cust.getTotalNonFundedLimit();
						 totalPartySanctionedAmount = Double.parseDouble(currentPartySanc);
						 Double difference = totalPartySanctionedAmount-totalFacSanctionedAmount;
						lmtDetailForm.setNonFundedAmount(String.valueOf(difference));
					}else if(dto.getFacilityTypeId().equals("MEMO_EXPOSURE")){
						String currentPartySanc =cust.getMemoExposure();
						 totalPartySanctionedAmount = Double.parseDouble(currentPartySanc);
						 Double difference = totalPartySanctionedAmount-totalFacSanctionedAmount;
						lmtDetailForm.setMemoExposer(String.valueOf(difference));
					}
				}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (LimitException e) {
			e.printStackTrace();
		}	catch (RemoteException e) {
			e.printStackTrace();
		}		
		lmtDetailForm.setLimitProfileID(camId);
//		lmtDetailForm.setFacilityName(master.getNewFacilityName());	
//		lmtDetailForm.setFacilityCode(master.getNewFacilityCode());	
//		lmtDetailForm.setFacilityType(master.getNewFacilityType());	
//		lmtDetailForm.setFacilitySystem(master.getNewFacilitySystem());	
//		lmtDetailForm.setLineNo(master.getLineNumber());	
//		lmtDetailForm.setPurpose(master.getPurpose());		
		lmtDetailForm.setGuarantee("No");	
		lmtDetailForm.setIsReleased("N");
		
		if(dto.getSecured()!=null && !dto.getSecured().trim().isEmpty()){
			lmtDetailForm.setIsSecured(dto.getSecured().trim());
		}else{
			lmtDetailForm.setIsSecured("");
		}
		
		if(dto.getGrade()!=null && !dto.getGrade().trim().isEmpty()){
			lmtDetailForm.setGrade(dto.getGrade().trim());	
		}else{
			lmtDetailForm.setGrade("");	
		}
		
		lmtDetailForm.setIsAdhoc("N");	
		lmtDetailForm.setIsAdhocToSum("N");	
//		lmtDetailForm.setFacilityCat(master.getNewFacilityCategory());
		
		if(dto.getCurrency()!=null && !dto.getCurrency().trim().isEmpty()){
			lmtDetailForm.setCurrencyCode(dto.getCurrency().trim());
		}else{
			lmtDetailForm.setCurrencyCode("INR");
		}
		
		if(dto.getSanctionedAmount()!=null && !dto.getSanctionedAmount().trim().isEmpty()){
			lmtDetailForm.setRequiredSecCov(dto.getSanctionedAmount().trim());
		}else{
			lmtDetailForm.setRequiredSecCov("0");
		}
		
		if(dto.getSubLimitFlag()!=null && !dto.getSubLimitFlag().trim().isEmpty()){
			lmtDetailForm.setLimitType(dto.getSubLimitFlag().trim());
		}else{
			lmtDetailForm.setLimitType("");
		}
		
		if(dto.getClimsFacilityId()!=null && !dto.getClimsFacilityId().trim().isEmpty()){
			lmtDetailForm.setClimsFacilityId(dto.getClimsFacilityId().trim());
		}else{
			lmtDetailForm.setClimsFacilityId("");
		}
		
//		ILimitTrxValue lmtTrxObj = null;
		/*if("Yes".equals(dto.getSubLimitFlag())){
			String subFacilityName="";
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
	
			try {
				//retrieve the  main facility Detail
				lmtTrxObj = proxy.searchLimitByLmtId(dto.getMainFacilityId());
				ILimit limit = (ILimit)lmtTrxObj.getLimit();
				
				//Check Facility belongs to same customer
				if(profile.getLimitProfileID()!=0){
					//retrieve detail of all facility belong to that cam
					List subFacNameList = proxy.getSubFacNameList(String.valueOf(profile.getLimitProfileID()));
					
					for (int i = 0; i < subFacNameList.size(); i++) {
						String [] subFacNameArray = (String[])subFacNameList.get(i);
						if(subFacNameArray[1].equals(limit.getFacilityName())){
							subFacilityName=limit.getFacilityName();
							break;
						}
					}
					
				}
				if("".equals(subFacilityName)){
					throw new LimitException("facility Id dosn't belong to CAM");
				}else{
					
					lmtDetailForm.setSubFacilityName(limit.getFacilityName());
				}
			} catch (LimitException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}else{
			lmtDetailForm.setSubFacilityName(null);
		}*/
		
		if(dto.getSubFacilityName()!=null && !dto.getSubFacilityName().trim().isEmpty()){
			lmtDetailForm.setSubFacilityName(dto.getSubFacilityName().trim());
		}else{
			lmtDetailForm.setSubFacilityName("");
		}
		
		if(dto.getSanctionedAmount()!=null && !dto.getSanctionedAmount().trim().isEmpty()){
			lmtDetailForm.setSanctionedLimit(dto.getSanctionedAmount().trim());
		}
		
		lmtDetailForm.setEvent(dto.getEvent());
		lmtDetailForm.setIsAdhoc("N");
		lmtDetailForm.setIsFromCamonlineReq('Y');

		if (dto.getCamId() != null && !dto.getCamId().trim().isEmpty()) {
			camId = dto.getCamId().trim();
		}
		
		if(dto.getRiskType()!=null && !dto.getRiskType().trim().isEmpty()){
			 lmtDetailForm.setRiskType(dto.getRiskType().trim());
		 }else{
			 lmtDetailForm.setRiskType("");
		 }
		 
		 if(dto.getTenorUnit()!=null && !dto.getTenorUnit().trim().isEmpty()){
			 lmtDetailForm.setTenorUnit(dto.getTenorUnit().trim());
		 }else{
			 lmtDetailForm.setTenorUnit("");
		 }
		 
		 if(dto.getTenor()!=null && !dto.getTenor().trim().isEmpty()){
			 lmtDetailForm.setTenor(dto.getTenor().trim());
		 }else{
			 lmtDetailForm.setTenor("");
		 }
		 
		 if(dto.getTenorDesc()!=null && !dto.getTenorDesc().trim().isEmpty()){
			 lmtDetailForm.setTenorDesc(dto.getTenorDesc().trim());
		 }else{
			 lmtDetailForm.setTenorDesc("");
		 }
		 if(dto.getMargin()!=null && !dto.getMargin().trim().isEmpty()){
			 lmtDetailForm.setMargin(dto.getMargin().trim());
		 }else{
			 lmtDetailForm.setMargin("");
		 }
		 if(dto.getPutCallOption()!=null && !dto.getPutCallOption().trim().isEmpty()){
			 lmtDetailForm.setPutCallOption(dto.getPutCallOption().trim());
		 }else{
			 lmtDetailForm.setPutCallOption("");
		 }
		 if(dto.getOptionDate()!=null && !dto.getOptionDate().trim().isEmpty()){
			 lmtDetailForm.setOptionDate(dto.getOptionDate().trim());
		 }else{
			 lmtDetailForm.setOptionDate("");
		 }
		 
		 if(dto.getLoanAvailabilityDate()!=null && !dto.getLoanAvailabilityDate().trim().isEmpty()){
			 lmtDetailForm.setLoanAvailabilityDate(dto.getLoanAvailabilityDate().trim());
		 }else{
			 lmtDetailForm.setLoanAvailabilityDate("");
		 }
		 if(dto.getBankingArrangement()!=null && !dto.getBankingArrangement().trim().isEmpty()){
			 lmtDetailForm.setBankingArrangement(dto.getBankingArrangement().trim());
		 }else{
			 lmtDetailForm.setBankingArrangement("");
		 }
		 if(dto.getClauseAsPerPolicy()!=null && !dto.getClauseAsPerPolicy().trim().isEmpty()){
			 lmtDetailForm.setClauseAsPerPolicy(dto.getClauseAsPerPolicy().trim());
		 }else{
			 lmtDetailForm.setClauseAsPerPolicy("");
		 }
		 
		return lmtDetailForm;
	}

	
	public FacilityDetailRequestDTO getRequestDTOWithActualValues(FacilityDetailRequestDTO requestDTO) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		SimpleDateFormat dateRequestFormat = new SimpleDateFormat("dd-MMM-yyyy");
		dateRequestFormat.setLenient(false);
		
		String errorCode = null;
		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();

		DefaultLogger.debug(this, "Inside getRequestDTOWithActualValues of FacilityDetailsDTOMapper================:::::");
//		DefaultLogger.debug(this, "value of requestDTO.getFacilityMasterId()================::::: "+requestDTO.getFacilityMasterId());
		
		//Facility CR : Below code included as requested in CR document.
		/*if(requestDTO.getFacilityMasterId()!=null && !requestDTO.getFacilityMasterId().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("actualFacilityNewMaster", requestDTO.getFacilityMasterId().trim(),"facilityMasterId",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setFacilityMasterId(String.valueOf(((IFacilityNewMaster)obj).getId()));
			}
		}else{
			errors.add("facilityMasterId",new ActionMessage("error.facilityMasterId.mandatory"));
		}*/
		
		if(requestDTO.getFacilityCategoryId()!=null && !requestDTO.getFacilityCategoryId().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getFacilityCategoryId().trim(),"facilityCategory",errors,"FACILITY_CATEGORY");
			if(!(obj instanceof ActionErrors)){
				requestDTO.setFacilityCategoryId(((ICommonCodeEntry)obj).getEntryCode());
			}
		}else{
			errors.add("facilityCategory",new ActionMessage("error.facilityCategory.mandatory"));
		}
		
		if(requestDTO.getFacilityTypeId()!=null && !requestDTO.getFacilityTypeId().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getFacilityTypeId().trim(),"facilityType",errors,"FACILITY_TYPE");
			if(!(obj instanceof ActionErrors)){
				requestDTO.setFacilityTypeId(((ICommonCodeEntry)obj).getEntryCode());
			}
		}else{
			errors.add("facilityType",new ActionMessage("error.facilityType.mandatory"));
		}
		 
		 ILimitProfile profile= new OBLimitProfile();
		 if(requestDTO.getCamId()!=null && !requestDTO.getCamId().trim().isEmpty()){
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			try {
				profile = limitProxy.getLimitProfile(Long.parseLong(requestDTO.getCamId().trim()));
				if(!(profile != null && !"".equals(profile))){
					 errors.add("camId",new ActionMessage("error.camId.invalid"));
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				 errors.add("camId",new ActionMessage("error.camId.invalid"));
			} catch (LimitException e) {
				e.printStackTrace();
				 errors.add("camId",new ActionMessage("error.camId.invalid"));
			}
		 }else{
			 errors.add("camId",new ActionMessage("error.camId.mandatory"));
		 }
		
		 if(requestDTO.getSubLimitFlag()!=null && !requestDTO.getSubLimitFlag().trim().isEmpty()){
				if(!("Yes".equals(requestDTO.getSubLimitFlag().trim()) || "No".equals(requestDTO.getSubLimitFlag().trim()))){
					errors.add("subLimitFlag",new ActionMessage("error.subLimitFlag.invalid"));
				}
				else if("Yes".equals(requestDTO.getSubLimitFlag().trim())){
					
					if(requestDTO.getMainFacilityId()!= null && !"".equals(requestDTO.getMainFacilityId().trim()))
					{   
						String mainFacIdSentInRequest = requestDTO.getMainFacilityId(); 
						ILimitTrxValue lmtTrxObj = null;
						MILimitUIHelper helper = new MILimitUIHelper();
						SBMILmtProxy proxy = helper.getSBMILmtProxy();
						try {
							lmtTrxObj = proxy.searchLimitByLmtId(requestDTO.getMainFacilityId().trim());
							ILimit limit = (ILimit)lmtTrxObj.getLimit();
							
							if(limit == null || "".equals(limit)){
								errors.add("mainFacilityId",new ActionMessage("error.mainFacilityId.invalid"));
							}else{
								
								//Set mainFacilityId
								if(limit.getLimitRef()!=null && !limit.getLimitRef().isEmpty()){
									requestDTO.setMainFacilityId(limit.getLimitRef());
								}

								//To check mainFacilityID belongs to same CAM or not : Added on 23-Nov-2015
								if(profile.getLimitProfileID()!=0 && limit.getLimitProfileID()!=0){
									if(profile.getLimitProfileID() != limit.getLimitProfileID()){
										errors.add("mainFacilityId",new ActionMessage("error.facility.does.not.belongs.to.cam",String.valueOf(profile.getLimitProfileID()),mainFacIdSentInRequest));
									}
								}
								
								
								
								/*String subFacilityName="";
								
								Check Facility belongs to same customer
								if(profile.getLimitProfileID()!=0){
									//retrieve detail of all facility belong to that cam
									List subFacNameList = proxy.getSubFacNameList(String.valueOf(profile.getLimitProfileID()));
									
									if(subFacNameList!=null && !subFacNameList.isEmpty() && subFacNameList.size()>0){
										for (int i = 0; i < subFacNameList.size(); i++) {
											String [] subFacNameArray = (String[])subFacNameList.get(i);
											if((limit.getFacilityName()== null || limit.getFacilityName().isEmpty())){
												errors.add("mainFacilityId",new ActionMessage("facilityname.isEmpty",requestDTO.getMainFacilityId()));
												break;
											}else{
												if(subFacNameArray[0]!=null && !subFacNameArray[0].isEmpty() && subFacNameArray[0].equals(limit.getFacilityName())){
													subFacilityName=limit.getFacilityName();
													break;
												}
//											}
										}
									}
								}//else cam id is getting validated above
								if("".equals(subFacilityName)){
									errors.add("mainFacilityId",new ActionMessage("error.mainFacilityId.not.matched.cam"));
								}else{
									requestDTO.setSubFacilityName(subFacilityName);
								}*/
								
							}
						} catch (LimitException e) {
							e.printStackTrace();
							errors.add("mainFacilityId",new ActionMessage("error.mainFacilityId.invalid"));
						} catch (RemoteException e) {
							e.printStackTrace();
							errors.add("mainFacilityId",new ActionMessage("error.mainFacilityId.invalid"));
						}
					}else{
						errors.add("mainFacilityId",new ActionMessage("error.mainFacilityId.mandatory"));
					}
				}
			}
		 else{
			 errors.add("subLimitFlag",new ActionMessage("error.subLimitFlag.mandatory"));
		 }
		 
		 if(requestDTO.getSecured()!=null && !requestDTO.getSecured().trim().isEmpty()){
				if(!("Y".equals(requestDTO.getSecured().trim()) || "N".equals(requestDTO.getSecured().trim()))){
					errors.add("secured",new ActionMessage("error.secured.invalid"));
				}
				else if("Y".equals(requestDTO.getSecured().trim())){
					
					if(requestDTO.getSecurityList()!= null && !"".equals(requestDTO.getSecurityList()))
					{   
						if(!(requestDTO.getSecurityList().size()>=1)){
							errors.add("securityList",new ActionMessage("error.securityList.atleastone"));
						}
					}
					else{
						 errors.add("securityList",new ActionMessage("error.securityList.atleastone"));
					}
				}else if("N".equals(requestDTO.getSecured().trim())){
					
					if(requestDTO.getSecurityList()!= null && !"".equals(requestDTO.getSecurityList()))
					{   
						if((requestDTO.getSecurityList().size()>=1)){
							errors.add("secured",new ActionMessage("error.secured.flag.set"));
						}
					}
				}
			}
		 else{
			 errors.add("secured",new ActionMessage("error.secured.mandatory"));
		 }
		 
		 if(requestDTO.getCurrency()!=null && !requestDTO.getCurrency().trim().isEmpty()){
				Object obj = masterObj.getObjectByEntityNameAndCPSId("actualForexFeedEntry", requestDTO.getCurrency().trim(),"currency",errors);
				if(!(obj instanceof ActionErrors)){
					requestDTO.setCurrency(((IForexFeedEntry)obj).getCurrencyIsoCode().trim());
				}
			}else{
				requestDTO.setCurrency("INR");
			}
		 
		 if(requestDTO.getSanctionedAmount()!=null && !requestDTO.getSanctionedAmount().trim().isEmpty()){
			 requestDTO.setSanctionedAmount(requestDTO.getSanctionedAmount().trim());
		 }else{
			 requestDTO.setSanctionedAmount("0");
		 }
		 
		 
		 
		 if(requestDTO.getGrade()!=null && !requestDTO.getGrade().trim().isEmpty()){
			 if (!(errorCode = Validator.checkInteger(requestDTO.getGrade().trim(), true, 1, 10)).equals(Validator.ERROR_NONE)) { 
				 errors.add("grade", new ActionMessage("error.grade.invalid")); 
			 }else {
				 requestDTO.setGrade(requestDTO.getGrade());
			 }
		 }else{
			 errors.add("grade", new ActionMessage("error.grade.invalid")); 
		 }
		 
			if(requestDTO.getRiskType()!=null && !requestDTO.getRiskType().trim().isEmpty()){
				 requestDTO.setRiskType(requestDTO.getRiskType().trim());			 
				 Object obj = masterObj.getObjectByEntityNameAndId("actualRiskType", requestDTO.getRiskType().trim(),"riskType",errors);
					if(!(obj instanceof ActionErrors)){
						requestDTO.setRiskType(((IRiskType)obj).getRiskTypeCode());
					}			 
			 }
			 
			if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("OFF") && ("WS_FAC_CREATE".equals(requestDTO.getEvent()) || "WS_FAC_EDIT".equals(requestDTO.getEvent()))) {
				// bypass mandatory validation
			}else {
				if ((requestDTO.getRiskType() == null) || "".equals(requestDTO.getRiskType().trim())) {
					 errors.add("riskType", new ActionMessage("error.string.mandatory"));
				}
			}
			
			 if(requestDTO.getTenorUnit()!=null && !requestDTO.getTenorUnit().trim().isEmpty()){
				  try {
					Object obj = masterObj.getMasterData("entryCode", Long.parseLong(requestDTO.getTenorUnit().trim()));
					if(obj!=null){
						ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
						if("TENOR".equals(codeEntry.getCategoryCode())){
							requestDTO.setTenorUnit((codeEntry).getEntryCode());
						}else{
							errors.add("tenorUnit",new ActionMessage("error.tenorUnit.invalid"));
						}
					}else{
						errors.add("tenorUnit",new ActionMessage("error.tenorUnit.invalid"));
					}
				  }catch(Exception e) {
						errors.add("tenorUnit",new ActionMessage("error.tenorUnit.invalid"));
				  }
			 }else{
				 requestDTO.setTenorUnit("");
			 }
			 
			 if(requestDTO.getTenorUnit()!=null && !requestDTO.getTenorUnit().trim().isEmpty()) {
				 if(requestDTO.getTenor()!=null && !requestDTO.getTenor().trim().isEmpty()){
					 requestDTO.setTenor(requestDTO.getTenor().trim());
				 }else{
					 errors.add("tenor", new ActionMessage("error.string.mandatory"));
				 }
			 }else {
				 requestDTO.setTenor("");
			 }
			 
			 if(requestDTO.getTenorDesc()!=null && !requestDTO.getTenorDesc().trim().isEmpty()){
				 if(requestDTO.getTenorDesc().length() > 200)
					 errors.add("tenorDesc", new ActionMessage("length is exceeded"));
				 else
					 requestDTO.setTenorDesc(requestDTO.getTenorDesc().trim());
			 }else{
				 requestDTO.setTenorDesc("");
			 }
			 if(requestDTO.getMargin()!=null && !requestDTO.getMargin().trim().isEmpty()){
				 if(ASSTValidator.isNumeric(requestDTO.getMargin().trim())) {
					 requestDTO.setMargin(requestDTO.getMargin().trim());
				 }else {
					 errors.add("margin", new ActionMessage("only numeric is allowed"));
				 }				 
			 }else{
				 requestDTO.setMargin("");
			 }
			 if(requestDTO.getPutCallOption()!=null && !requestDTO.getPutCallOption().trim().isEmpty()){
				 if("PUT".equals(requestDTO.getPutCallOption().trim()) || "CALL".equals(requestDTO.getPutCallOption().trim())){
					 requestDTO.setPutCallOption(requestDTO.getPutCallOption().trim());
				 }else {
					 errors.add("putCallOption",new ActionMessage("Invalid value"));
				 }
			 }else{
				 requestDTO.setPutCallOption("");
			 }	
			 
			if(requestDTO.getOptionDate()!=null && !requestDTO.getOptionDate().trim().isEmpty()){
				try {
					dateRequestFormat.parse(requestDTO.getOptionDate().trim());
					requestDTO.setOptionDate(requestDTO.getOptionDate().toString().trim());
				} catch (ParseException e) {
					errors.add("optionDate",new ActionMessage("error.optionDate.invalid.format"));
				}
			}else{
				requestDTO.setOptionDate("");
			}
			
			if (null != requestDTO.getLoanAvailabilityDate() && !requestDTO.getLoanAvailabilityDate().trim().isEmpty()) {
				try {
					if (isValidDateHyphen(requestDTO.getLoanAvailabilityDate().trim())) {
						dateRequestFormat.parse(requestDTO.getLoanAvailabilityDate().trim());
						requestDTO.setLoanAvailabilityDate(requestDTO.getLoanAvailabilityDate().toString().trim());
					} else {
						errors.add("loanAvailabilityDate", new ActionMessage("error.loanAvailabilityDate.invalid.format"));
					}
				} catch (ParseException e) {
					DefaultLogger.debug(this, "Expection at getLoanAvailabilityDate>>" + e.getMessage());
					e.printStackTrace();
				}
			} else {
				requestDTO.setLoanAvailabilityDate("");
			}	 		 

			if(requestDTO.getBankingArrangement()!=null && !requestDTO.getBankingArrangement().trim().isEmpty()){
				try {
					Object obj = masterObj.getMasterData("entryCode", Long.parseLong(requestDTO.getBankingArrangement().trim()));
					if(obj!=null){
						ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
						if("BANKING_ARRANGEMENT".equals(codeEntry.getCategoryCode())){
							requestDTO.setBankingArrangement((codeEntry).getEntryCode());
						}else{
							errors.add("bankingArrangement",new ActionMessage("error.bankingArrangement.invalid"));
						}
					}else{
						errors.add("bankingArrangement",new ActionMessage("error.bankingArrangement.invalid"));
					}
				}catch(Exception e) {
					errors.add("bankingArrangement",new ActionMessage("error.bankingArrangement.invalid"));
				}
			}
			
			if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("OFF") && ("WS_FAC_CREATE".equals(requestDTO.getEvent()) || "WS_FAC_EDIT".equals(requestDTO.getEvent()))) {
				// bypass mandatory validation
			}else {
				if (requestDTO.getBankingArrangement() == null || "".equals(requestDTO.getBankingArrangement()))
				{
					errors.add("bankingArrangement", new ActionMessage("error.string.mandatory"));
				}
			} 
			 
			if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("OFF") && ("WS_FAC_CREATE".equals(requestDTO.getEvent()) || "WS_FAC_EDIT".equals(requestDTO.getEvent()))) {
					// bypass mandatory validation
			}else {
				if (requestDTO.getClauseAsPerPolicy() == null || "".equals(requestDTO.getClauseAsPerPolicy()))
				{
					errors.add("clauseAsPerPolicy", new ActionMessage("error.string.mandatory"));
				}
			}

			if(requestDTO.getClauseAsPerPolicy()!=null && !requestDTO.getClauseAsPerPolicy().trim().isEmpty()){
				try {
					Object obj = masterObj.getMasterData("entryCode", Long.parseLong(requestDTO.getClauseAsPerPolicy().trim()));
					if(obj!=null){
						ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
						if("CLAUSE_AS_PER_POLICY".equals(codeEntry.getCategoryCode())){
							requestDTO.setClauseAsPerPolicy((codeEntry).getEntryCode());
						}else{
							errors.add("clauseAsPerPolicy",new ActionMessage("error.clauseAsPerPolicy.invalid"));
						}
					}else{
						errors.add("clauseAsPerPolicy",new ActionMessage("error.clauseAsPerPolicy.invalid"));
					}
				}catch(Exception e) {
					errors.add("clauseAsPerPolicy",new ActionMessage("error.clauseAsPerPolicy.invalid"));
				}
			}

		requestDTO.setErrors(errors);
		return requestDTO;
		}
	
	public static boolean isValidDateHyphen(String inDate) {
		 SimpleDateFormat ddMMMyyyyDateformat = new SimpleDateFormat("dd-MMM-yyyy");
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
	
	//for SCOD
	public FacilitySCODDetailRequestDTO getSCODRequestDTOWithActualValues(FacilitySCODDetailRequestDTO requestDTO) {

		FacilitySCODDetailRequestDTO facilityDetailRequestDTO =new FacilitySCODDetailRequestDTO();
		MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();
		DefaultLogger.debug(this,
				"Inside getSCODRequestDTOWithActualValues of FacilityDetailsDTOMapper================:::::");
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		
		ILimitTrxValue lmtTrxObj = null;
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
		ILimitProfile profile = new OBLimitProfile();
		String errorCode = null;
		
		if (requestDTO.getCamId() != null && !requestDTO.getCamId().trim().isEmpty()) {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			try {
				profile = limitProxy.getLimitProfile(Long.parseLong(requestDTO.getCamId().trim()));
				if (!(profile != null && !"".equals(profile))) {
					errors.add("camId", new ActionMessage("error.camId.invalid"));
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				errors.add("camId", new ActionMessage("error.camId.invalid"));
			} catch (LimitException e) {
				e.printStackTrace();
				errors.add("camId", new ActionMessage("error.camId.invalid"));
			}
		} else {
			errors.add("camId", new ActionMessage("error.camId.mandatory"));
		}
		
		try {
			if (requestDTO.getClimsFacilityId() != null
					&& !requestDTO.getClimsFacilityId().trim().isEmpty()) {
				lmtTrxObj = proxy.searchLimitByLmtId(requestDTO.getClimsFacilityId().trim());
			} else {
				errors.add("climsFacilityId", new ActionMessage("error.climsFacilityId.invalid"));
			}
		} catch (Exception e) {
			errors.add("climsFacilityId", new ActionMessage("error.climsFacilityId.invalid"));
		}
		
		
		if(requestDTO.getClimsFacilityId() != null
				&& !requestDTO.getClimsFacilityId().trim().isEmpty() && requestDTO.getCamId() != null && !requestDTO.getCamId().trim().isEmpty()) {
			LimitDAO lmtDao = new LimitDAO();
			String countNum = lmtDao.getFacilityIdAndCamIdCombineCount(requestDTO.getClimsFacilityId().trim(),requestDTO.getCamId().trim());
			if("0".equals(countNum)) {
				errors.add("climsFacilityId", new ActionMessage("error.climsFacilityIdCamId.invalid"));
			}
		}
		if (requestDTO.getCamType() != null && !requestDTO.getCamType().trim().isEmpty()) {
			facilityDetailRequestDTO.setCamType(requestDTO.getCamType().trim());
			System.out.println("666666666666:::::"+requestDTO.getCamType());
		//	if ("Initial".equals(requestDTO.getCamType()) || "Interim".equals(requestDTO.getCamType()) || "Annual".equals(requestDTO.getCamType())) {
				if ("Initial".equals(requestDTO.getCamType()) ) {

				//Initial call i.e. for new facility

				if (requestDTO.getProjectFinance() != null && !requestDTO.getProjectFinance().trim().isEmpty()) {
					if (!("Y".equals(requestDTO.getProjectFinance().trim())
							|| "N".equals(requestDTO.getProjectFinance().trim()))) {
						errors.add("projectFinance", new ActionMessage("error.projectFinance.invalid"));
					} else {
						facilityDetailRequestDTO.setProjectFinance(requestDTO.getProjectFinance());
					}
				} else {
					errors.add("projectFinance", new ActionMessage("error.projectFinance.mandatory"));
				}

				if (requestDTO.getProjectLoan() != null && !requestDTO.getProjectLoan().trim().isEmpty()) {
					if (!("Y".equals(requestDTO.getProjectLoan().trim())
							|| "N".equals(requestDTO.getProjectLoan().trim()))) {
						errors.add("projectLoan", new ActionMessage("error.projectLoan.invalid"));
					} else {
						
						if ("Y".equals(requestDTO.getProjectFinance()) && "N".equals(requestDTO.getProjectLoan())) {
							errors.add("projectLoan", new ActionMessage("error.project.loan.no.finance.yes"));
						}else {
						
						facilityDetailRequestDTO.setProjectLoan(requestDTO.getProjectLoan());
						}
					}
				} else {
					errors.add("projectLoan", new ActionMessage("error.projectLoan.mandatory"));
				}

				
				

				if ("Y".equals(requestDTO.getProjectFinance()) || "Y".equals(requestDTO.getProjectLoan())) {
					if(("Interim".equals(requestDTO.getCamType()) || "Annual".equals(requestDTO.getCamType())) && "1".equals(requestDTO.getDelaylevel())) {
						if (requestDTO.getInfraFlag() != null && !requestDTO.getInfraFlag().trim().isEmpty()) {
							if (!("Y".equals(requestDTO.getInfraFlag().trim())
									|| "N".equals(requestDTO.getInfraFlag().trim()))) {
								errors.add("infraFlag", new ActionMessage("error.infraFlag.invalid"));
							} else {
								facilityDetailRequestDTO.setInfraFlag(requestDTO.getInfraFlag());
							}
						}
						 else {
								errors.add("infraFlag", new ActionMessage("error.infraFlag.mandatory"));
					}
					}

					if (requestDTO.getScod() != null && !requestDTO.getScod().toString().trim().isEmpty()) {
						try {
							Calendar calendar = Calendar.getInstance();
							 calendar.add(Calendar.DATE, -1);
							 
							if (! (errorCode = Validator.checkDate(requestDTO.getScod(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
								errors.add("scod", new ActionMessage("error.scod.invalid.format"));
							 }	else {
								 String d1 = (String)requestDTO.getScod();
									String[] d2 = d1.split("/");
									if(d2.length == 3) {
										if(d2[2].length() != 4) {
											errors.add("scod", new ActionMessage("error.scod.invalid.format"));
										}else {
											/*if ((requestDTO.getScod().length() > 0)
													&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()).before(calendar.getTime())) {
												errors.add("scod", new ActionMessage("error.date.before.notallowed"));
											}else {*/
											relationshipDateFormat.parse(requestDTO.getScod().toString().trim());
											facilityDetailRequestDTO.setScod(requestDTO.getScod().trim());
											//}
									}
									}
						 }
						} catch (ParseException e) {
							errors.add("scod", new ActionMessage("error.scod.invalid.format"));
						}
					} else {
						errors.add("scod", new ActionMessage("error.scod.mandatory"));
					}

					if (requestDTO.getScodRemark() != null && !requestDTO.getScodRemark().trim().isEmpty()) {
						facilityDetailRequestDTO.setScodRemark(requestDTO.getScodRemark());
					} else {
						errors.add("scodRemark", new ActionMessage("error.scodRemark.mandatory"));
					}
				}else {
					if ("N".equals(requestDTO.getProjectFinance()) && "N".equals(requestDTO.getProjectLoan())) {
						if(!"WS_SCOD_FAC_EDIT".equalsIgnoreCase(requestDTO.getEvent())){
						errors.add("projectLoan", new ActionMessage("error.projectLoanFinance.mandatory"));
						errors.add("projectFinance", new ActionMessage("error.projectLoanFinance.mandatory"));
						}
						}
				}
				
				if(requestDTO.getExeAssetClass()!=null && !requestDTO.getExeAssetClass().trim().isEmpty()){
					Object scodObj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getExeAssetClass().trim(),
							"exeAssetClass",errors,"EXE_ASSET_CLASS_ID");
					System.out.println("Called exeAssetClass...");
					System.out.println("BBBBBBBBBBBBB inside iffffff");
					if(!(scodObj instanceof ActionErrors)){
						System.out.println("Is not instanceof ActionErrors...");
						facilityDetailRequestDTO.setExeAssetClass(((ICommonCodeEntry)scodObj).getEntryCode());
						//to set exeAssetClassDate
						if(!requestDTO.getExeAssetClass().equals("1")) {
							if (requestDTO.getExeAssetClassDate() != null && !requestDTO.getExeAssetClassDate().toString().trim().isEmpty()) {
								try {
									if (! (errorCode = Validator.checkDate(requestDTO.getExeAssetClassDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
										errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
									 }	else {
										 String d1 = (String)requestDTO.getExeAssetClassDate();
											String[] d2 = d1.split("/");
											if(d2.length == 3) {
												if(d2[2].length() != 4) {
													errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
												}else {
													relationshipDateFormat.parse(requestDTO.getExeAssetClassDate().toString().trim());
													facilityDetailRequestDTO.setExeAssetClassDate(requestDTO.getExeAssetClassDate().trim());
													}
											}
									}
								} catch (ParseException e) {
									errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
								}
							} else {
								errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.mandatory"));
							}
						} else {
							
							if(!requestDTO.getExeAssetClassDate().equals("")) {
							try {
								if (! (errorCode = Validator.checkDate(requestDTO.getExeAssetClassDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
									errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
								 }	else {
									 String d1 = (String)requestDTO.getExeAssetClassDate();
										String[] d2 = d1.split("/");
										if(d2.length == 3) {
											if(d2[2].length() != 4) {
												errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
											}else {
												relationshipDateFormat.parse(requestDTO.getExeAssetClassDate().toString().trim());
												facilityDetailRequestDTO.setExeAssetClassDate(requestDTO.getExeAssetClassDate().trim());
												}
										}
								}
							} catch (ParseException e) {
								errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
							}
							}else {
								facilityDetailRequestDTO.setExeAssetClassDate("");
							}
							
						}
					} else {
						System.out.println("Error occured on exeAssetClass");
						errors.add("exeAssetClass",new ActionMessage("error.exeAssetClass.invalid"));
					}
				}else{
					errors.add("exeAssetClass",new ActionMessage("error.exeAssetClass.mandatory"));
				}
				
				
				
				
				/*facilityDetailRequestDTO.setExeAssetClass("1");
				facilityDetailRequestDTO.setExeAssetClassDate("");*/
				
			}else if ("Interim".equals(requestDTO.getCamType()) || "Annual".equals(requestDTO.getCamType())) {
				//Interim call i.e. for update facility
				DefaultLogger.debug(this,"======Interim CAM call========");
				if(null!=lmtTrxObj && null!=lmtTrxObj.getLimit()) {
					//INTERIM  CAM (CHANGE IN SCOD)
					ILimit obj=lmtTrxObj.getLimit();
					//project finance,project loan,infra/nonInfra flag, scod, scod remarks are mendatory
					
					Calendar calendar = Calendar.getInstance();
					 calendar.add(Calendar.DATE, -1);

					if (requestDTO.getLimitReleaseFlg() != null && !requestDTO.getLimitReleaseFlg().trim().isEmpty()) {
						DefaultLogger.debug(this,"======getLimitReleaseFlg not null========");
							 if (!("Y".equals(requestDTO.getLimitReleaseFlg().trim()) || "N".equals(requestDTO.getLimitReleaseFlg().trim()))) {
									errors.add("limitReleaseFlg", new ActionMessage("error.limitReleaseFlg.invalid"));
							 } else {
									facilityDetailRequestDTO.setLimitReleaseFlg(requestDTO.getLimitReleaseFlg());
									//case 1: limit not yet released
									if("N".equals(requestDTO.getLimitReleaseFlg().trim())) {
										//to set updated scod

										DefaultLogger.debug(this,"======limit not yet released========");
										/*if (requestDTO.getScod() != null && !requestDTO.getScod().toString().trim().isEmpty()) {
											
											errors.add("scod", new ActionMessage("error.scod.not.allowed"));
										}*/
											
											/*try {
												
												if (! (errorCode = Validator.checkDate(requestDTO.getScod(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
													errors.add("scod", new ActionMessage("error.scod.invalid.format"));
												 }	else {
													 String d1 = (String)requestDTO.getScod();
														String[] d2 = d1.split("/");
														if(d2.length == 3) {
															if(d2[2].length() != 4) {
																errors.add("scod", new ActionMessage("error.scod.invalid.format"));
															}else {
																relationshipDateFormat.parse(requestDTO.getScod().toString().trim());
//																if (DateUtil.convertDate(requestDTO.getScod()).after(obj.getScodDate())) {
																if (DateUtil.convertDate(requestDTO.getScod()).after(calendar.getTime())) {
																	facilityDetailRequestDTO.setScod(requestDTO.getScod().trim());
																}else {
																	errors.add("scod", new ActionMessage("error.scod.future"));
																}
																}
														}
												 }
											} catch (ParseException e) {
												errors.add("scod", new ActionMessage("error.scod.invalid.format"));
											}
										} 
										else {
											errors.add("scod", new ActionMessage("error.scod.mandatory"));//error.scod.not.allowed
										}*/
											
										//to set updated scod remark
										if (requestDTO.getScodRemark() != null && !requestDTO.getScodRemark().trim().isEmpty()) {
											facilityDetailRequestDTO.setScodRemark(requestDTO.getScodRemark());
										} else {
											errors.add("scodRemark", new ActionMessage("error.scodRemark.mandatory"));
										}
										//to set repayment schedule
										facilityDetailRequestDTO.setRepayChngSched(requestDTO.getRepayChngSched());
										if (requestDTO.getRepayChngSched() != null && !requestDTO.getRepayChngSched().trim().isEmpty()) {
											if (!("Y".equals(requestDTO.getRepayChngSched().trim()) || "N".equals(requestDTO.getRepayChngSched().trim()))) {
												errors.add("repayChngSched", new ActionMessage("error.repayChngSched.invalid"));
											} else {
												facilityDetailRequestDTO.setRepayChngSched(requestDTO.getRepayChngSched());
											}
										} else {
											errors.add("repayChngSched", new ActionMessage("error.repayChngSched.mandatory"));
										}
										//to set exeAssetClass
										
										/*if(requestDTO.getExeAssetClass()!=null && !requestDTO.getExeAssetClass().trim().isEmpty()){
											Object scodObj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getExeAssetClass().trim(),
													"exeAssetClass",errors,"EXE_ASSET_CLASS_ID");
											if(!(scodObj instanceof ActionErrors)){
												facilityDetailRequestDTO.setExeAssetClass(((ICommonCodeEntry)scodObj).getEntryCode());
												//to set exeAssetClassDate
												if(!requestDTO.getExeAssetClass().equals("1")) {
													if (requestDTO.getExeAssetClassDate() != null && !requestDTO.getExeAssetClassDate().toString().trim().isEmpty()) {
														try {
															if (! (errorCode = Validator.checkDate(requestDTO.getExeAssetClassDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
															 }	else {
																 String d1 = (String)requestDTO.getExeAssetClassDate();
																	String[] d2 = d1.split("/");
																	if(d2.length == 3) {
																		if(d2[2].length() != 4) {
																			errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
																		}else {
																			relationshipDateFormat.parse(requestDTO.getExeAssetClassDate().toString().trim());
																			facilityDetailRequestDTO.setExeAssetClassDate(requestDTO.getExeAssetClassDate().trim());
																			}
																	}
															}
//														
														} catch (ParseException e) {
															errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
														}
													} else {
														errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.mandatory"));
													}
												} else {
													facilityDetailRequestDTO.setExeAssetClassDate("");
												}
											} else {
												errors.add("exeAssetClass",new ActionMessage("error.exeAssetClass.invalid"));
											}
										}else{
											errors.add("exeAssetClass",new ActionMessage("error.exeAssetClass.mandatory"));
										}*/
										
										//to set revAssetClass

										if(requestDTO.getRevAssetClass()!=null && !requestDTO.getRevAssetClass().trim().isEmpty()){
											Object scodObj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getRevAssetClass().trim(),
													"revAssetClass",errors,"REV_ASSET_CLASS_ID");
											if(!(scodObj instanceof ActionErrors)){
												facilityDetailRequestDTO.setRevAssetClass(((ICommonCodeEntry)scodObj).getEntryCode());
												//to set revAssetClassDt
												if(!requestDTO.getRevAssetClass().equals("1")) {
													if (requestDTO.getRevAssetClassDt() != null && !requestDTO.getRevAssetClassDt().toString().trim().isEmpty()) {
														try {
															if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDt(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
															 }	else {
																 String d1 = (String)requestDTO.getRevAssetClassDt();
																	String[] d2 = d1.split("/");
																	if(d2.length == 3) {
																		if(d2[2].length() != 4) {
																			errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																		}else {
																			relationshipDateFormat.parse(requestDTO.getRevAssetClassDt().toString().trim());
																			facilityDetailRequestDTO.setRevAssetClassDt(requestDTO.getRevAssetClassDt().trim());
																			}
																	}
															 }
														} catch (ParseException e) {
															errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
														}
													} else {
														errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.mandatory"));
													}
												} else {
													
													if(!requestDTO.getRevAssetClassDt().trim().equals("")) {
													try {
														if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDt(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
															errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
														 }	else {
															 String d1 = (String)requestDTO.getRevAssetClassDt();
																String[] d2 = d1.split("/");
																if(d2.length == 3) {
																	if(d2[2].length() != 4) {
																		errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																	}else {
																		relationshipDateFormat.parse(requestDTO.getRevAssetClassDt().toString().trim());
																		facilityDetailRequestDTO.setRevAssetClassDt(requestDTO.getRevAssetClassDt().trim());
																		}
																}
														 }
													} catch (ParseException e) {
														errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
													}
													}else {
														facilityDetailRequestDTO.setRevAssetClassDt("");
													}
													
												}
											} else {
												errors.add("revAssetClass",new ActionMessage("error.revAssetClass.invalid"));
											}
										}else{
											errors.add("revAssetClass",new ActionMessage("error.revAssetClass.mandatory"));
										}
									}
									//case 2: limit released
									
									else if("Y".equals(requestDTO.getLimitReleaseFlg().trim())) {

										DefaultLogger.debug(this,"======limit released========");
										//As per bank request, removed below validation for interim case.
										/*if (requestDTO.getScod() != null && !requestDTO.getScod().toString().trim().isEmpty()) {
											errors.add("scod", new ActionMessage("error.scod.not.allowed"));
										}
										if (requestDTO.getScodRemark() != null && !requestDTO.getScodRemark().trim().isEmpty()) {
											errors.add("scodRemark", new ActionMessage("error.scodRemark.not.allowed"));
										}
										
										if (requestDTO.getExeAssetClass() != null && !requestDTO.getExeAssetClass().trim().isEmpty()) {
											errors.add("exeAssetClass", new ActionMessage("error.exeAssetClass.not.allowed"));
										}
										
										if (requestDTO.getExeAssetClassDate() != null && !requestDTO.getExeAssetClassDate().trim().isEmpty()) {
											errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.not.allowed"));
										}*/
										//to set acod

										if (requestDTO.getAcod() != null && !requestDTO.getAcod().toString().trim().isEmpty()) {
											try {
												if (! (errorCode = Validator.checkDate(requestDTO.getAcod(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
													errors.add("acod", new ActionMessage("error.acod.invalid.format"));
												 }	else {
													 String d1 = (String)requestDTO.getAcod();
														String[] d2 = d1.split("/");
														if(d2.length == 3) {
															if(d2[2].length() != 4) {
																errors.add("acod", new ActionMessage("error.acod.invalid.format"));
															}else {
																relationshipDateFormat.parse(requestDTO.getAcod().toString().trim());
																facilityDetailRequestDTO.setAcod(requestDTO.getAcod().trim());
																}
														}
												 }
											} catch (ParseException e) {
												errors.add("acod", new ActionMessage("error.acod.invalid.format"));
											}
											
											if(requestDTO.getDelaylevel()!=null && !requestDTO.getDelaylevel().trim().isEmpty()){
												errors.add("delaylevel",new ActionMessage("error.delaylevel.not.allowed"));
											}else {
												if(requestDTO.getRevAssetClass()!=null && !requestDTO.getRevAssetClass().trim().isEmpty()){
													Object scodObj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getRevAssetClass().trim(),
															"revAssetClass",errors,"REV_ASSET_CLASS_ID");
													if(!(scodObj instanceof ActionErrors)){
														facilityDetailRequestDTO.setRevAssetClass(((ICommonCodeEntry)scodObj).getEntryCode());
														//to set revAssetClassDt
														if(!requestDTO.getRevAssetClass().equals("1")) {
															if (requestDTO.getRevAssetClassDt() != null && !requestDTO.getRevAssetClassDt().toString().trim().isEmpty()) {
																try {
																	if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDt(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																		errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																	 }	else {

																		 String d1 = (String)requestDTO.getRevAssetClassDt();
																			String[] d2 = d1.split("/");
																			if(d2.length == 3) {
																				if(d2[2].length() != 4) {
																					errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																				}else {
																					relationshipDateFormat.parse(requestDTO.getRevAssetClassDt().toString().trim());
																					facilityDetailRequestDTO.setRevAssetClassDt(requestDTO.getRevAssetClassDt().trim());
																					}
																			}
																	 }
																} catch (ParseException e) {
																	errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																}
															} else {
																errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.mandatory"));
															}
														} else {
															
															
															if(!requestDTO.getRevAssetClassDt().trim().equals("")) {
																try {
																	if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDt(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																		errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																	 }	else {
																		 String d1 = (String)requestDTO.getRevAssetClassDt();
																			String[] d2 = d1.split("/");
																			if(d2.length == 3) {
																				if(d2[2].length() != 4) {
																					errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																				}else {
																					relationshipDateFormat.parse(requestDTO.getRevAssetClassDt().toString().trim());
																					facilityDetailRequestDTO.setRevAssetClassDt(requestDTO.getRevAssetClassDt().trim());
																					}
																			}
																	 }
																} catch (ParseException e) {
																	errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																}
															}else {
																facilityDetailRequestDTO.setRevAssetClassDt("");
															}
														}
													} else {
														errors.add("revAssetClass",new ActionMessage("error.revAssetClass.invalid"));
													}
												}else{
													errors.add("revAssetClass",new ActionMessage("error.revAssetClass.mandatory"));
												}
											}
											
										} 
										//to set level details
										else {
											
											/*if(requestDTO.getDelaylevel() == null || "".equals(requestDTO.getDelaylevel())) {
												if("N".equals(requestDTO.getDelayFlagL1())) {
													errors.add("acod", new ActionMessage("error.acod.mandatory"));
												}
											}else if("2".equals(requestDTO.getDelaylevel())) {
												if("N".equals(requestDTO.getDelayFlagL2())) {
													errors.add("acod", new ActionMessage("error.acod.mandatory"));
												}
											}else if("3".equals(requestDTO.getDelaylevel())) {
												if("N".equals(requestDTO.getDelayFlagL3())) {
													errors.add("acod", new ActionMessage("error.acod.mandatory"));
												}
											}*/
											
//											if("N".equals(requestDTO.getDelayFlagL1())) {
//												errors.add("acod", new ActionMessage("error.acod.mandatory"));
//											}
											
																
											/*if("N".equals(requestDTO.getDelayFlagL1())||"N".equals(requestDTO.getDelayFlagL2())||"N".equals(requestDTO.getDelayFlagL3())) {
												requestDTO.setDelaylevel("");
											}*/
											if(requestDTO.getDelaylevel()!=null && !requestDTO.getDelaylevel().trim().isEmpty()){
												Object scodObj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getDelaylevel().trim(),
														"delaylevel",errors,"DELAY_LEVEL_ID");
												if(!(scodObj instanceof ActionErrors)){
													facilityDetailRequestDTO.setDelaylevel(((ICommonCodeEntry)scodObj).getEntryCode());
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<1st DELAY CAM>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
													if("1".equals(requestDTO.getDelaylevel())) {
														DefaultLogger.debug(this,"======1st DELAY CAM========");
														//to set delayFlagL1
														if (requestDTO.getDelayFlagL1() != null && !requestDTO.getDelayFlagL1().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getDelayFlagL1().trim()) || "N".equals(requestDTO.getDelayFlagL1().trim()))) {
																errors.add("delayFlagL1", new ActionMessage("error.delayFlagL1.invalid"));
															} else {
																if("Y".equals(requestDTO.getDelayFlagL1().trim())) {
																facilityDetailRequestDTO.setDelayFlagL1(requestDTO.getDelayFlagL1());
																}else {
																	errors.add("delayFlagL1", new ActionMessage("error.delayFlagL1.invalid2"));
																}
															}
														} else {
															errors.add("delayFlagL1", new ActionMessage("error.delayFlagL1.mandatory"));
														}
														
														//to set interestFlag
														if (requestDTO.getInterestFlag() != null && !requestDTO.getInterestFlag().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getInterestFlag().trim()) || "N".equals(requestDTO.getInterestFlag().trim()))) {
																errors.add("interestFlag", new ActionMessage("error.interestFlag.invalid"));
															} else {
																facilityDetailRequestDTO.setInterestFlag(requestDTO.getInterestFlag());
															}
														} else {
															errors.add("interestFlag", new ActionMessage("error.interestFlag.mandatory"));
														}
														//to set priorReqFlag
														if (requestDTO.getPriorReqFlag() != null && !requestDTO.getPriorReqFlag().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getPriorReqFlag().trim()) || "N".equals(requestDTO.getPriorReqFlag().trim()))) {
																errors.add("priorReqFlag", new ActionMessage("error.priorReqFlag.invalid"));
															} else {
																facilityDetailRequestDTO.setPriorReqFlag(requestDTO.getPriorReqFlag());
															}
														} else {
															errors.add("priorReqFlag", new ActionMessage("error.priorReqFlag.mandatory"));
														}
														//to set defReasonL1
														if (requestDTO.getDefReasonL1() != null && !requestDTO.getDefReasonL1().trim().isEmpty()) {
															facilityDetailRequestDTO.setDefReasonL1(requestDTO.getDefReasonL1());
														} else {
															errors.add("defReasonL1", new ActionMessage("error.defReasonL1.mandatory"));
														}
														//to set repayChngSchedL1

														if (requestDTO.getRepayChngSchedL1() != null && !requestDTO.getRepayChngSchedL1().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getRepayChngSchedL1().trim()) || "N".equals(requestDTO.getRepayChngSchedL1().trim()))) {
																errors.add("repayChngSchedL1", new ActionMessage("error.repayChngSchedL1.invalid"));
															} else {
																facilityDetailRequestDTO.setRepayChngSchedL1(requestDTO.getRepayChngSchedL1());
															}
														} else {
															errors.add("repayChngSchedL1", new ActionMessage("error.repayChngSchedL1.mandatory"));
														}
														//to set escodL1

														if (requestDTO.getEscodL1() != null && !requestDTO.getEscodL1().toString().trim().isEmpty()) {
															try {
																if (! (errorCode = Validator.checkDate(requestDTO.getEscodL1(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																	errors.add("escodL1", new ActionMessage("error.escodL1.invalid.format"));
																 }	else {
																	 String d1 = (String)requestDTO.getEscodL1();
																		String[] d2 = d1.split("/");
																		if(d2.length == 3) {
																			if(d2[2].length() != 4) {
																				errors.add("escodL1", new ActionMessage("error.escodL1.invalid.format"));
																			}else {
																				relationshipDateFormat.parse(requestDTO.getEscodL1().toString().trim());
																				facilityDetailRequestDTO.setEscodL1(requestDTO.getEscodL1().trim());
																			}
																		}
																 }
//																relationshipDateFormat.parse(requestDTO.getEscodL1().toString().trim());
//																facilityDetailRequestDTO.setEscodL1(requestDTO.getEscodL1().trim());
															} catch (ParseException e) {
																errors.add("escodL1", new ActionMessage("error.escodL1.invalid.format"));
															}
														} else {
															errors.add("escodL1", new ActionMessage("error.escodL1.mandatory"));
														} 
														//to set ownershipQ1FlagL1
														if (requestDTO.getOwnershipQ1FlagL1() != null && !requestDTO.getOwnershipQ1FlagL1().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getOwnershipQ1FlagL1().trim()) || "N".equals(requestDTO.getOwnershipQ1FlagL1().trim()))) {
																errors.add("ownershipQ1FlagL1", new ActionMessage("error.ownershipQ1FlagL1.invalid"));
															} else {
																facilityDetailRequestDTO.setOwnershipQ1FlagL1(requestDTO.getOwnershipQ1FlagL1());
															}
														} else {
															errors.add("ownershipQ1FlagL1", new ActionMessage("error.ownershipQ1FlagL1.mandatory"));
														}
														//to set ownershipQ2FlagL1
														if (requestDTO.getOwnershipQ2FlagL1() != null && !requestDTO.getOwnershipQ2FlagL1().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getOwnershipQ2FlagL1().trim()) || "N".equals(requestDTO.getOwnershipQ2FlagL1().trim()))) {
																errors.add("ownershipQ2FlagL1", new ActionMessage("error.ownershipQ2FlagL1.invalid"));
															} else {
																facilityDetailRequestDTO.setOwnershipQ2FlagL1(requestDTO.getOwnershipQ2FlagL1());
															}
														} else {
															errors.add("ownershipQ2FlagL1", new ActionMessage("error.ownershipQ2FlagL1.mandatory"));
														}
														//to set ownershipQ3FlagL1
														if (requestDTO.getOwnershipQ3FlagL1() != null && !requestDTO.getOwnershipQ3FlagL1().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getOwnershipQ3FlagL1().trim()) || "N".equals(requestDTO.getOwnershipQ3FlagL1().trim()))) {
																errors.add("ownershipQ3FlagL1", new ActionMessage("error.ownershipQ3FlagL1.invalid"));
															} else {
																facilityDetailRequestDTO.setOwnershipQ3FlagL1(requestDTO.getOwnershipQ3FlagL1());
															}
														} else {
															errors.add("ownershipQ3FlagL1", new ActionMessage("error.ownershipQ3FlagL1.mandatory"));
														}
														//to set scopeQ1FlagL1
														if (requestDTO.getScopeQ1FlagL1() != null && !requestDTO.getScopeQ1FlagL1().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getScopeQ1FlagL1().trim()) || "N".equals(requestDTO.getScopeQ1FlagL1().trim()))) {
																errors.add("scopeQ1FlagL1", new ActionMessage("error.scopeQ1FlagL1.invalid"));
															} else {
																facilityDetailRequestDTO.setScopeQ1FlagL1(requestDTO.getScopeQ1FlagL1());
															}
														} else {
															errors.add("scopeQ1FlagL1", new ActionMessage("error.scopeQ1FlagL1.mandatory"));
														}
														//to set scopeQ2FlagL1
														if (requestDTO.getScopeQ2FlagL1() != null && !requestDTO.getScopeQ2FlagL1().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getScopeQ2FlagL1().trim()) || "N".equals(requestDTO.getScopeQ2FlagL1().trim()))) {
																errors.add("scopeQ2FlagL1", new ActionMessage("error.scopeQ2FlagL1.invalid"));
															} else {
																facilityDetailRequestDTO.setScopeQ2FlagL1(requestDTO.getScopeQ2FlagL1());
															}
														} else {
															errors.add("scopeQ2FlagL1", new ActionMessage("error.scopeQ2FlagL1.mandatory"));
														}
														//to set scopeQ3FlagL1
														if (requestDTO.getScopeQ3FlagL1() != null && !requestDTO.getScopeQ3FlagL1().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getScopeQ3FlagL1().trim()) || "N".equals(requestDTO.getScopeQ3FlagL1().trim()))) {
																errors.add("scopeQ3FlagL1", new ActionMessage("error.scopeQ3FlagL1.invalid"));
															} else {
																facilityDetailRequestDTO.setScopeQ3FlagL1(requestDTO.getScopeQ3FlagL1());
															}
														} else {
															errors.add("scopeQ3FlagL1", new ActionMessage("error.scopeQ3FlagL1.mandatory"));
														}
														//to set revisedEscodL1
														if (requestDTO.getRevisedEscodL1() != null && !requestDTO.getRevisedEscodL1().toString().trim().isEmpty()) {
															try {
																if (! (errorCode = Validator.checkDate(requestDTO.getRevisedEscodL1(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																	errors.add("revisedEscodL1", new ActionMessage("error.revisedEscodL1.invalid.format"));
																 }	else {
																	 String d1 = (String)requestDTO.getRevisedEscodL1();
																		String[] d2 = d1.split("/");
																		if(d2.length == 3) {
																			if(d2[2].length() != 4) {
																				errors.add("revisedEscodL1", new ActionMessage("error.revisedEscodL1.invalid.format"));
																			}else {
																				relationshipDateFormat.parse(requestDTO.getRevisedEscodL1().toString().trim());
//																				facilityDetailRequestDTO.setRevisedEscodL1(requestDTO.getRevisedEscodL1().trim());
																				facilityDetailRequestDTO.setRevisedEscodL1("");
																				}
																		}
																 }
															} catch (ParseException e) {
																errors.add("revisedEscodL1", new ActionMessage("error.revisedEscodL1.invalid.format"));
															}
														}
														//to set exeAssetClassL1
														/*if(requestDTO.getExeAssetClassL1()!=null && !requestDTO.getExeAssetClassL1().trim().isEmpty()){
															Object exeAssetClassL1Obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getExeAssetClassL1().trim(),
																	"exeAssetClass",errors,"EXE_ASSET_CLASS_ID");
															if(!(exeAssetClassL1Obj instanceof ActionErrors)){
																facilityDetailRequestDTO.setExeAssetClassL1(((ICommonCodeEntry)exeAssetClassL1Obj).getEntryCode());
																//to set exeAssetClassDtL1
																if(!requestDTO.getExeAssetClassL1().equals("1")) {
																	if (requestDTO.getExeAssetClassDtL1() != null && !requestDTO.getExeAssetClassDtL1().toString().trim().isEmpty()) {
																		try {
																			if (! (errorCode = Validator.checkDate(requestDTO.getExeAssetClassDtL1(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																				errors.add("exeAssetClassDtL1", new ActionMessage("error.exeAssetClassDtL1.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getExeAssetClassDtL1();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("exeAssetClassDtL1", new ActionMessage("error.exeAssetClassDtL1.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getExeAssetClassDtL1().toString().trim());
																							facilityDetailRequestDTO.setExeAssetClassDtL1(requestDTO.getExeAssetClassDtL1().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("exeAssetClassDtL1", new ActionMessage("error.exeAssetClassDtL1.invalid.format"));
																		}
																	} else {
																		errors.add("exeAssetClassDtL1", new ActionMessage("error.exeAssetClassDtL1.mandatory"));
																	}
																} else {
																	facilityDetailRequestDTO.setExeAssetClassDtL1("");
																}
															} else {
																errors.add("exeAssetClassL1",new ActionMessage("error.exeAssetClassL1.invalid"));
															}
														}else{
															errors.add("exeAssetClassL1",new ActionMessage("error.exeAssetClassL1.mandatory"));
														}*/
														//to set revAssetClassL1
														if(requestDTO.getRevAssetClassL1()!=null && !requestDTO.getRevAssetClassL1().trim().isEmpty()){
															Object revAssetClassL1Obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getRevAssetClassL1().trim(),
																	"revAssetClass",errors,"REV_ASSET_CLASS_ID");
															if(!(revAssetClassL1Obj instanceof ActionErrors)){
																facilityDetailRequestDTO.setRevAssetClassL1(((ICommonCodeEntry)revAssetClassL1Obj).getEntryCode());
																//to set revAssetClassDtL1
																if(!requestDTO.getRevAssetClassL1().equals("1")) {
																	if (requestDTO.getRevAssetClassDtL1() != null && !requestDTO.getRevAssetClassDtL1().toString().trim().isEmpty()) {
																		try {
																			if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDtL1(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																				errors.add("revAssetClassDtL1", new ActionMessage("error.revAssetClassDtL1.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getRevAssetClassDtL1();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("revAssetClassDtL1", new ActionMessage("error.revAssetClassDtL1.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getRevAssetClassDtL1().toString().trim());
																							facilityDetailRequestDTO.setRevAssetClassDtL1(requestDTO.getRevAssetClassDtL1().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("revAssetClassDtL1", new ActionMessage("error.revAssetClassDtL1.invalid.format"));
																		}
																	} else {
																		errors.add("revAssetClassDtL1", new ActionMessage("error.revAssetClassDtL1.mandatory"));
																	}
																} else {
																	if(!requestDTO.getRevAssetClassDtL1().trim().equals("")) {
																		try {
																			if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDtL1(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																				errors.add("revAssetClassDtL1", new ActionMessage("error.revAssetClassDtL1.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getRevAssetClassDtL1();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("revAssetClassDtL1", new ActionMessage("error.revAssetClassDtL1.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getRevAssetClassDtL1().toString().trim());
																							facilityDetailRequestDTO.setRevAssetClassDtL1(requestDTO.getRevAssetClassDtL1().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("revAssetClassDtL1", new ActionMessage("error.revAssetClassDtL1.invalid.format"));
																		}
																	}else {
																		facilityDetailRequestDTO.setRevAssetClassDtL1("");
																	}
																}
															} else {
																errors.add("revAssetClassL1",new ActionMessage("error.revAssetClassL1.invalid"));
															}
														} else {
															errors.add("revAssetClassL1",new ActionMessage("error.revAssetClassL1.mandatory"));
														}
													}
      //<<<<<<<<<<<<<<<<<<<<<<<<<<2nd DELAY CAM>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
													else if("2".equals(requestDTO.getDelaylevel())) {
														DefaultLogger.debug(this,"======2nd DELAY CAM========");
														//to set delayFlagL2
														if (requestDTO.getDelayFlagL2() != null && !requestDTO.getDelayFlagL2().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getDelayFlagL2().trim()) || "N".equals(requestDTO.getDelayFlagL2().trim()))) {
																errors.add("delayFlagL2", new ActionMessage("error.delayFlagL2.invalid"));
															} else {
																if("Y".equals(requestDTO.getDelayFlagL2().trim())) {
																	facilityDetailRequestDTO.setDelayFlagL2(requestDTO.getDelayFlagL2());
																}else {
																	errors.add("delayFlagL2", new ActionMessage("error.delayFlagL2.invalid2"));
																}
															}
														} else {
															errors.add("delayFlagL2", new ActionMessage("error.delayFlagL2.mandatory"));
														}
														//to set defReasonL2
														if (requestDTO.getDefReasonL2() != null && !requestDTO.getDefReasonL2().trim().isEmpty()) {
															facilityDetailRequestDTO.setDefReasonL2(requestDTO.getDefReasonL2());
														} else {
															errors.add("defReasonL2", new ActionMessage("error.defReasonL2.mandatory"));
														}
														//to set repayChngSchedL2
														if (requestDTO.getRepayChngSchedL2() != null && !requestDTO.getRepayChngSchedL2().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getRepayChngSchedL2().trim()) || "N".equals(requestDTO.getRepayChngSchedL2().trim()))) {
																errors.add("repayChngSchedL2", new ActionMessage("error.repayChngSchedL2.invalid"));
															} else {
																facilityDetailRequestDTO.setRepayChngSchedL2(requestDTO.getRepayChngSchedL2());
															}
														} else {
															errors.add("repayChngSchedL2", new ActionMessage("error.repayChngSchedL2.mandatory"));
														}
														//to set escodL2
														if (requestDTO.getEscodL2() != null && !requestDTO.getEscodL2().toString().trim().isEmpty()) {
															try {
																if (! (errorCode = Validator.checkDate(requestDTO.getEscodL2(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																	errors.add("escodL2", new ActionMessage("error.escodL2.invalid.format"));
																 }	else {
																	 String d1 = (String)requestDTO.getEscodL2();
																		String[] d2 = d1.split("/");
																		if(d2.length == 3) {
																			if(d2[2].length() != 4) {
																				errors.add("escodL2", new ActionMessage("error.escodL2.invalid.format"));
																			}else {
																				relationshipDateFormat.parse(requestDTO.getEscodL2().toString().trim());
																				facilityDetailRequestDTO.setEscodL2(requestDTO.getEscodL2().trim());
																				}
																		}
																 }
															} catch (ParseException e) {
																errors.add("escodL2", new ActionMessage("error.escodL2.invalid.format"));
															}
														} else {
															errors.add("escodL2", new ActionMessage("error.escodL2.mandatory"));
														} 
														//to set escodRevisionReasonQ1L2 and 
														if(null != obj.getInfaProjectFlag() &&  "Y".equals(obj.getInfaProjectFlag())) {
															if (requestDTO.getEscodRevisionReasonQ1L2() != null && !requestDTO.getEscodRevisionReasonQ1L2().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ1L2().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ1L2().trim()))) {
																	errors.add("escodRevisionReasonQ1L2", new ActionMessage("error.escodRevisionReasonQ1L2.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ1L2(requestDTO.getEscodRevisionReasonQ1L2());
																	//to set legalDetailL2
																	if("Y".equals(requestDTO.getEscodRevisionReasonQ1L2().trim())) {
																		if (requestDTO.getLegalDetailL2() != null && !requestDTO.getLegalDetailL2().trim().isEmpty()) {
																			facilityDetailRequestDTO.setLegalDetailL2(requestDTO.getLegalDetailL2());
																		} else {
																			errors.add("legalDetailL2", new ActionMessage("error.legalDetailL2.mandatory"));
																		}
																	} else {
																		if (requestDTO.getLegalDetailL2() != null && !requestDTO.getLegalDetailL2().trim().isEmpty()) {
																			facilityDetailRequestDTO.setLegalDetailL2(requestDTO.getLegalDetailL2());
																		} else {
																			facilityDetailRequestDTO.setLegalDetailL2(" ");
																		}
																	}
																}
															} else {
																errors.add("escodRevisionReasonQ1L2", new ActionMessage("error.escodRevisionReasonQ1L2.mandatory"));
															}
															//to set escodRevisionReasonQ2L2
															if (requestDTO.getEscodRevisionReasonQ2L2() != null && !requestDTO.getEscodRevisionReasonQ2L2().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ2L2().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ2L2().trim()))) {
																	errors.add("escodRevisionReasonQ2L2", new ActionMessage("error.escodRevisionReasonQ2L2.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ2L2(requestDTO.getEscodRevisionReasonQ2L2());
																	//to set beyondControlL2
																	if("Y".equals(requestDTO.getEscodRevisionReasonQ2L2())) {
																		if (requestDTO.getBeyondControlL2() != null && !requestDTO.getBeyondControlL2().trim().isEmpty()) {
																			facilityDetailRequestDTO.setBeyondControlL2(requestDTO.getBeyondControlL2());
																		} else {
																			errors.add("beyondControlL2", new ActionMessage("error.beyondControlL2.mandatory"));
																		}
																	}else {
																		if (requestDTO.getBeyondControlL2() != null && !requestDTO.getBeyondControlL2().trim().isEmpty()) {
																			facilityDetailRequestDTO.setBeyondControlL2(requestDTO.getBeyondControlL2());
																		}else {
																			facilityDetailRequestDTO.setBeyondControlL2(" ");
																		}
																	}
																}
															} else {
																errors.add("escodRevisionReasonQ2L2", new ActionMessage("error.escodRevisionReasonQ2L2.mandatory"));
															}
															//to set escodRevisionReasonQ3L2 
															if (requestDTO.getEscodRevisionReasonQ3L2() != null && !requestDTO.getEscodRevisionReasonQ3L2().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ3L2().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ3L2().trim()))) {
																	errors.add("escodRevisionReasonQ3L2", new ActionMessage("error.escodRevisionReasonQ3L2.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ3L2(requestDTO.getEscodRevisionReasonQ3L2());
																	
																	//to set ownershipQ1FlagL2
																	if (requestDTO.getOwnershipQ1FlagL2() != null && !requestDTO.getOwnershipQ1FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ1FlagL2().trim()) || "N".equals(requestDTO.getOwnershipQ1FlagL2().trim()))) {
																			errors.add("ownershipQ1FlagL2", new ActionMessage("error.ownershipQ1FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ1FlagL2(requestDTO.getOwnershipQ1FlagL2());
																		}
																	} else {
																		errors.add("ownershipQ1FlagL2", new ActionMessage("error.ownershipQ1FlagL2.mandatory"));
																	}
																	//to set ownershipQ2FlagL2
																	if (requestDTO.getOwnershipQ2FlagL2() != null && !requestDTO.getOwnershipQ2FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ2FlagL2().trim()) || "N".equals(requestDTO.getOwnershipQ2FlagL2().trim()))) {
																			errors.add("ownershipQ2FlagL2", new ActionMessage("error.ownershipQ2FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ2FlagL2(requestDTO.getOwnershipQ2FlagL2());
																		}
																	} else {
																		errors.add("ownershipQ2FlagL2", new ActionMessage("error.ownershipQ2FlagL2.mandatory"));
																	}
																	//to set ownershipQ3FlagL2
																	if (requestDTO.getOwnershipQ3FlagL2() != null && !requestDTO.getOwnershipQ3FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ3FlagL2().trim()) || "N".equals(requestDTO.getOwnershipQ3FlagL2().trim()))) {
																			errors.add("ownershipQ3FlagL2", new ActionMessage("error.ownershipQ3FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ3FlagL2(requestDTO.getOwnershipQ3FlagL2());
																		}
																	} else {
																		errors.add("ownershipQ3FlagL2", new ActionMessage("error.ownershipQ3FlagL2.mandatory"));
																	}
																	
																}
															} else {
																errors.add("escodRevisionReasonQ3L2", new ActionMessage("error.escodRevisionReasonQ3L2.mandatory"));
															}
															
															//to set escodRevisionReasonQ4L2
															if (requestDTO.getEscodRevisionReasonQ4L2() != null && !requestDTO.getEscodRevisionReasonQ4L2().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ4L2().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ4L2().trim()))) {
																	errors.add("escodRevisionReasonQ4L2", new ActionMessage("error.escodRevisionReasonQ4L2.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ4L2(requestDTO.getEscodRevisionReasonQ4L2());
																	
																	//to set scopeQ1FlagL2
																	if (requestDTO.getScopeQ1FlagL2() != null && !requestDTO.getScopeQ1FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ1FlagL2().trim()) || "N".equals(requestDTO.getScopeQ1FlagL2().trim()))) {
																			errors.add("scopeQ1FlagL2", new ActionMessage("error.scopeQ1FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ1FlagL2(requestDTO.getScopeQ1FlagL2());
																		}
																	} else {
																		errors.add("scopeQ1FlagL2", new ActionMessage("error.scopeQ1FlagL2.mandatory"));
																	}
																	//to set scopeQ2FlagL2
																	if (requestDTO.getScopeQ2FlagL2() != null && !requestDTO.getScopeQ2FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ2FlagL2().trim()) || "N".equals(requestDTO.getScopeQ2FlagL2().trim()))) {
																			errors.add("scopeQ2FlagL2", new ActionMessage("error.scopeQ2FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ2FlagL2(requestDTO.getScopeQ2FlagL2());
																		}
																	} else {
																		errors.add("scopeQ2FlagL2", new ActionMessage("error.scopeQ2FlagL2.mandatory"));
																	}
																	//to set scopeQ3FlagL2
																	if (requestDTO.getScopeQ3FlagL2() != null && !requestDTO.getScopeQ3FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ3FlagL2().trim()) || "N".equals(requestDTO.getScopeQ3FlagL2().trim()))) {
																			errors.add("scopeQ3FlagL2", new ActionMessage("error.scopeQ3FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ3FlagL2(requestDTO.getScopeQ3FlagL2());
																		}
																	} else {
																		errors.add("scopeQ3FlagL2", new ActionMessage("error.scopeQ3FlagL2.mandatory"));
																	}
																	
																}
															} else {
																errors.add("escodRevisionReasonQ4L2", new ActionMessage("error.escodRevisionReasonQ4L2.mandatory"));
															}
														} else if( null != obj.getInfaProjectFlag() && "N".equals(obj.getInfaProjectFlag())) {
															//to set escodRevisionReasonQ5L2 
															if (requestDTO.getEscodRevisionReasonQ5L2() != null && !requestDTO.getEscodRevisionReasonQ5L2().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ5L2().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ5L2().trim()))) {
																	errors.add("escodRevisionReasonQ5L2", new ActionMessage("error.escodRevisionReasonQ5L2.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ5L2(requestDTO.getEscodRevisionReasonQ5L2());
																	
																	//to set ownershipQ1FlagL2
																	if (requestDTO.getOwnershipQ1FlagL2() != null && !requestDTO.getOwnershipQ1FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ1FlagL2().trim()) || "N".equals(requestDTO.getOwnershipQ1FlagL2().trim()))) {
																			errors.add("ownershipQ1FlagL2", new ActionMessage("error.ownershipQ1FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ1FlagL2(requestDTO.getOwnershipQ1FlagL2());
																		}
																	} else {
																		errors.add("ownershipQ1FlagL2", new ActionMessage("error.ownershipQ1FlagL2.mandatory"));
																	}
																	//to set ownershipQ2FlagL2
																	if (requestDTO.getOwnershipQ2FlagL2() != null && !requestDTO.getOwnershipQ2FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ2FlagL2().trim()) || "N".equals(requestDTO.getOwnershipQ2FlagL2().trim()))) {
																			errors.add("ownershipQ2FlagL2", new ActionMessage("error.ownershipQ2FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ2FlagL2(requestDTO.getOwnershipQ2FlagL2());
																		}
																	} else {
																		errors.add("ownershipQ2FlagL2", new ActionMessage("error.ownershipQ2FlagL2.mandatory"));
																	}
																	//to set ownershipQ3FlagL2
																	if (requestDTO.getOwnershipQ3FlagL2() != null && !requestDTO.getOwnershipQ3FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ3FlagL2().trim()) || "N".equals(requestDTO.getOwnershipQ3FlagL2().trim()))) {
																			errors.add("ownershipQ3FlagL2", new ActionMessage("error.ownershipQ3FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ3FlagL2(requestDTO.getOwnershipQ3FlagL2());
																		}
																	} else {
																		errors.add("ownershipQ3FlagL2", new ActionMessage("error.ownershipQ3FlagL2.mandatory"));
																	}
																	
																}
															} else {
																errors.add("escodRevisionReasonQ5L2", new ActionMessage("error.escodRevisionReasonQ5L2.mandatory"));
															}
															
															//to set escodRevisionReasonQ6L2
															if (requestDTO.getEscodRevisionReasonQ6L2() != null && !requestDTO.getEscodRevisionReasonQ6L2().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ6L2().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ6L2().trim()))) {
																	errors.add("escodRevisionReasonQ6L2", new ActionMessage("error.escodRevisionReasonQ6L2.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ6L2(requestDTO.getEscodRevisionReasonQ6L2());
																	
																	//to set scopeQ1FlagL2
																	if (requestDTO.getScopeQ1FlagL2() != null && !requestDTO.getScopeQ1FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ1FlagL2().trim()) || "N".equals(requestDTO.getScopeQ1FlagL2().trim()))) {
																			errors.add("scopeQ1FlagL2", new ActionMessage("error.scopeQ1FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ1FlagL2(requestDTO.getScopeQ1FlagL2());
																		}
																	} else {
																		errors.add("scopeQ1FlagL2", new ActionMessage("error.scopeQ1FlagL2.mandatory"));
																	}
																	//to set scopeQ2FlagL2
																	if (requestDTO.getScopeQ2FlagL2() != null && !requestDTO.getScopeQ2FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ2FlagL2().trim()) || "N".equals(requestDTO.getScopeQ2FlagL2().trim()))) {
																			errors.add("scopeQ2FlagL2", new ActionMessage("error.scopeQ2FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ2FlagL2(requestDTO.getScopeQ2FlagL2());
																		}
																	} else {
																		errors.add("scopeQ2FlagL2", new ActionMessage("error.scopeQ2FlagL2.mandatory"));
																	}
																	//to set scopeQ3FlagL2
																	if (requestDTO.getScopeQ3FlagL2() != null && !requestDTO.getScopeQ3FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ3FlagL2().trim()) || "N".equals(requestDTO.getScopeQ3FlagL2().trim()))) {
																			errors.add("scopeQ3FlagL2", new ActionMessage("error.scopeQ3FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ3FlagL2(requestDTO.getScopeQ3FlagL2());
																		}
																	} else {
																		errors.add("scopeQ3FlagL2", new ActionMessage("error.scopeQ3FlagL2.mandatory"));
																	}
																	
																}
															} else {
																errors.add("escodRevisionReasonQ6L2", new ActionMessage("error.escodRevisionReasonQ6L2.mandatory"));
															}
														}
												
														
														//to set revisedEscodL2
														if (requestDTO.getRevisedEscodL2() != null && !requestDTO.getRevisedEscodL2().toString().trim().isEmpty()) {
															try {
																if (! (errorCode = Validator.checkDate(requestDTO.getRevisedEscodL2(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																	errors.add("revisedEscodL2", new ActionMessage("error.revisedEscodL2.invalid.format"));
																 }	else {
																	 String d1 = (String)requestDTO.getRevisedEscodL2();
																		String[] d2 = d1.split("/");
																		if(d2.length == 3) {
																			if(d2[2].length() != 4) {
																				errors.add("revisedEscodL2", new ActionMessage("error.revisedEscodL2.invalid.format"));
																			}else {
																				relationshipDateFormat.parse(requestDTO.getRevisedEscodL2().toString().trim());
																				facilityDetailRequestDTO.setRevisedEscodL2(requestDTO.getRevisedEscodL2().trim());
																				}
																		}
																 }
															} catch (ParseException e) {
																errors.add("revisedEscodL2", new ActionMessage("error.revisedEscodL2.invalid.format"));
															}
														}/*else {
															errors.add("revisedEscodL2", new ActionMessage("error.revisedEscodL2.mandatory"));
														}*/
														//to set exeAssetClassL2
														/*if(requestDTO.getExeAssetClassL2()!=null && !requestDTO.getExeAssetClassL2().trim().isEmpty()){
															Object exeAssetClassL1Obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getExeAssetClassL2().trim(),
																	"exeAssetClass",errors,"EXE_ASSET_CLASS_ID");
															if(!(exeAssetClassL1Obj instanceof ActionErrors)){
																facilityDetailRequestDTO.setExeAssetClassL2(((ICommonCodeEntry)exeAssetClassL1Obj).getEntryCode());
																//to set exeAssetClassDtL2
																if(!requestDTO.getExeAssetClassL2().equals("1")) {
																	if (requestDTO.getExeAssetClassDtL2() != null && !requestDTO.getExeAssetClassDtL2().toString().trim().isEmpty()) {
																		try {
																			if (! (errorCode = Validator.checkDate(requestDTO.getExeAssetClassDtL2(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																				errors.add("exeAssetClassDtL2", new ActionMessage("error.exeAssetClassDtL2.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getExeAssetClassDtL2();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("exeAssetClassDtL2", new ActionMessage("error.exeAssetClassDtL2.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getExeAssetClassDtL2().toString().trim());
																							facilityDetailRequestDTO.setExeAssetClassDtL2(requestDTO.getExeAssetClassDtL2().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("exeAssetClassDtL2", new ActionMessage("error.exeAssetClassDtL2.invalid.format"));
																		}
																	} else {
																		errors.add("exeAssetClassDtL2", new ActionMessage("error.exeAssetClassDtL2.mandatory"));
																	}
																} else {
																	facilityDetailRequestDTO.setExeAssetClassDtL2("");
																}
															}
														}else{
															errors.add("exeAssetClassL2",new ActionMessage("error.exeAssetClassL2.mandatory"));
														}*/
														//to set revAssetClassL2
														if(requestDTO.getRevAssetClassL2()!=null && !requestDTO.getRevAssetClassL2().trim().isEmpty()){
															Object revAssetClassL1Obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getRevAssetClassL2().trim(),
																	"revAssetClass",errors,"REV_ASSET_CLASS_ID");
																facilityDetailRequestDTO.setRevAssetClassL2(((ICommonCodeEntry)revAssetClassL1Obj).getEntryCode());
																//to set revAssetClassDtL2
																if(!requestDTO.getRevAssetClassL2().equals("1")) {
																	if (requestDTO.getRevAssetClassDtL2() != null && !requestDTO.getRevAssetClassDtL2().toString().trim().isEmpty()) {
																		try {
																			if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDtL2(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																				errors.add("revAssetClassDtL2", new ActionMessage("error.revAssetClassDtL2.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getRevAssetClassDtL2();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("revAssetClassDtL2", new ActionMessage("error.revAssetClassDtL2.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getRevAssetClassDtL2().toString().trim());
																							facilityDetailRequestDTO.setRevAssetClassDtL2(requestDTO.getRevAssetClassDtL2().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("revAssetClassDtL2", new ActionMessage("error.revAssetClassDtL2.invalid.format"));
																		}
																	} else {
																		errors.add("revAssetClassDtL2", new ActionMessage("error.revAssetClassDtL2.mandatory"));
																	}
																} else {
																	if(!requestDTO.getRevAssetClassDtL2().trim().equals("")) {
																		try {
																			if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDtL2(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																				errors.add("revAssetClassDtL2", new ActionMessage("error.revAssetClassDtL2.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getRevAssetClassDtL2();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("revAssetClassDtL2", new ActionMessage("error.revAssetClassDtL2.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getRevAssetClassDtL2().toString().trim());
																							facilityDetailRequestDTO.setRevAssetClassDtL2(requestDTO.getRevAssetClassDtL2().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("revAssetClassDtL2", new ActionMessage("error.revAssetClassDtL2.invalid.format"));
																		}
																	}else {
																		facilityDetailRequestDTO.setRevAssetClassDtL2("");
																	}
																}
														} else {
															errors.add("revAssetClassL2",new ActionMessage("error.revAssetClassL2.mandatory"));
														}
													
													}
        //<<<<<<<<<<<<<<<<<<<<<<<3rd DELAY CAM>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
													
													else if("3".equals(requestDTO.getDelaylevel())) {
														//to set delayFlagL3
														DefaultLogger.debug(this,"======3rd DELAY CAM========");
														if (requestDTO.getDelayFlagL3() != null && !requestDTO.getDelayFlagL3().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getDelayFlagL3().trim()) || "N".equals(requestDTO.getDelayFlagL3().trim()))) {
																errors.add("delayFlagL3", new ActionMessage("error.delayFlagL3.invalid"));
															} else {
																if("Y".equals(requestDTO.getDelayFlagL3().trim())) {
																facilityDetailRequestDTO.setDelayFlagL3(requestDTO.getDelayFlagL3());
																}else {
																	errors.add("delayFlagL3", new ActionMessage("error.delayFlagL3.invalid2"));
																}
															}
														} else {
															errors.add("delayFlagL3", new ActionMessage("error.delayFlagL3.mandatory"));
														}
														
														//to set defReasonL3
														if (requestDTO.getDefReasonL3() != null && !requestDTO.getDefReasonL3().trim().isEmpty()) {
															facilityDetailRequestDTO.setDefReasonL3(requestDTO.getDefReasonL3());
														} else {
															errors.add("defReasonL3", new ActionMessage("error.defReasonL3.mandatory"));
														}
														//to set repayChngSchedL3
														if (requestDTO.getRepayChngSchedL3() != null && !requestDTO.getRepayChngSchedL3().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getRepayChngSchedL3().trim()) || "N".equals(requestDTO.getRepayChngSchedL3().trim()))) {
																errors.add("repayChngSchedL3", new ActionMessage("error.repayChngSchedL3.invalid"));
															} else {
																facilityDetailRequestDTO.setRepayChngSchedL3(requestDTO.getRepayChngSchedL3());
															}
														} else {
															errors.add("repayChngSchedL3", new ActionMessage("error.repayChngSchedL3.mandatory"));
														}
														//to set escodL3
														if (requestDTO.getEscodL3() != null && !requestDTO.getEscodL3().toString().trim().isEmpty()) {
															try {
															
															if (! (errorCode = Validator.checkDate(requestDTO.getEscodL3(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																errors.add("escodL3", new ActionMessage("error.escodL3.invalid.format")); 
															 }	else {
																 String d1 = (String)requestDTO.getEscodL3();
																	String[] d2 = d1.split("/");
																	if(d2.length == 3) {
																		if(d2[2].length() != 4) {
																			errors.add("escodL3", new ActionMessage("error.escodL3.invalid.format"));
																		}else {
																			relationshipDateFormat.parse(requestDTO.getEscodL3().toString().trim());
																			facilityDetailRequestDTO.setEscodL3(requestDTO.getEscodL3().trim());
																		}
																	}
																}
															
															} catch (ParseException e) {
																errors.add("escodL3", new ActionMessage("error.escodL3.invalid.format"));
															}
														} else {
															errors.add("escodL3", new ActionMessage("error.escodL3.mandatory"));
														} 
														//to set escodRevisionReasonQ1L3 and 
														if(null != obj.getInfaProjectFlag() &&  "Y".equals(obj.getInfaProjectFlag())) {
															if (requestDTO.getEscodRevisionReasonQ1L3() != null && !requestDTO.getEscodRevisionReasonQ1L3().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ1L3().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ1L3().trim()))) {
																	errors.add("escodRevisionReasonQ1L3", new ActionMessage("error.escodRevisionReasonQ1L3.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ1L3(requestDTO.getEscodRevisionReasonQ1L3());
																	//to set legalDetailL3
																	if("Y".equals(requestDTO.getEscodRevisionReasonQ1L3().trim())) {
																		if (requestDTO.getLegalDetailL3() != null && !requestDTO.getLegalDetailL3().trim().isEmpty()) {
																			facilityDetailRequestDTO.setLegalDetailL3(requestDTO.getLegalDetailL3());
																		} else {
																			errors.add("legalDetailL3", new ActionMessage("error.legalDetailL3.mandatory"));
																		}
																	} else {
																		if (requestDTO.getLegalDetailL3() != null && !requestDTO.getLegalDetailL3().trim().isEmpty()) {
																			facilityDetailRequestDTO.setLegalDetailL3(requestDTO.getLegalDetailL3());
																		}else {
																			facilityDetailRequestDTO.setLegalDetailL3(" ");
																		}
																	}
																}
															} else {
																errors.add("escodRevisionReasonQ1L3", new ActionMessage("error.escodRevisionReasonQ1L3.mandatory"));
															}
															//to set escodRevisionReasonQ2L3
															if (requestDTO.getEscodRevisionReasonQ2L3() != null && !requestDTO.getEscodRevisionReasonQ2L3().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ2L3().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ2L3().trim()))) {
																	errors.add("escodRevisionReasonQ2L3", new ActionMessage("error.escodRevisionReasonQ2L3.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ2L3(requestDTO.getEscodRevisionReasonQ2L3());
																	//to set beyondControlL3
																	if("Y".equals(requestDTO.getEscodRevisionReasonQ2L3().trim())) {
																		if (requestDTO.getBeyondControlL3() != null && !requestDTO.getBeyondControlL3().trim().isEmpty()) {
																			facilityDetailRequestDTO.setBeyondControlL3(requestDTO.getBeyondControlL3());
																		} else {
																			errors.add("beyondControlL3", new ActionMessage("error.beyondControlL3.mandatory"));
																		}
																	} else {
																		if (requestDTO.getBeyondControlL3() != null && !requestDTO.getBeyondControlL3().trim().isEmpty()) {
																			facilityDetailRequestDTO.setBeyondControlL3(requestDTO.getBeyondControlL3());
																		}else {
																			facilityDetailRequestDTO.setBeyondControlL3(" ");
																		}
																	}
																}
															} else {
																errors.add("escodRevisionReasonQ2L3", new ActionMessage("error.escodRevisionReasonQ2L3.mandatory"));
															}
															
															//to set escodRevisionReasonQ3L3 
															if (requestDTO.getEscodRevisionReasonQ3L3() != null && !requestDTO.getEscodRevisionReasonQ3L3().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ3L3().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ3L3().trim()))) {
																	errors.add("escodRevisionReasonQ3L3", new ActionMessage("error.escodRevisionReasonQ3L3.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ3L3(requestDTO.getEscodRevisionReasonQ3L3());
																	
																	//to set ownershipQ1FlagL3
																	if (requestDTO.getOwnershipQ1FlagL3() != null && !requestDTO.getOwnershipQ1FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ1FlagL3().trim()) || "N".equals(requestDTO.getOwnershipQ1FlagL3().trim()))) {
																			errors.add("ownershipQ1FlagL3", new ActionMessage("error.ownershipQ1FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ1FlagL3(requestDTO.getOwnershipQ1FlagL3());
																		}
																	} else {
																		errors.add("ownershipQ1FlagL3", new ActionMessage("error.ownershipQ1FlagL3.mandatory"));
																	}
																	//to set ownershipQ2FlagL3
																	if (requestDTO.getOwnershipQ2FlagL3() != null && !requestDTO.getOwnershipQ2FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ2FlagL3().trim()) || "N".equals(requestDTO.getOwnershipQ2FlagL3().trim()))) {
																			errors.add("ownershipQ2FlagL3", new ActionMessage("error.ownershipQ2FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ2FlagL3(requestDTO.getOwnershipQ2FlagL3());
																		}
																	} else {
																		errors.add("ownershipQ2FlagL3", new ActionMessage("error.ownershipQ2FlagL3.mandatory"));
																	}
																	//to set ownershipQ3FlagL3
																	if (requestDTO.getOwnershipQ3FlagL3() != null && !requestDTO.getOwnershipQ3FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ3FlagL3().trim()) || "N".equals(requestDTO.getOwnershipQ3FlagL3().trim()))) {
																			errors.add("ownershipQ3FlagL3", new ActionMessage("error.ownershipQ3FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ3FlagL3(requestDTO.getOwnershipQ3FlagL3());
																		}
																	} else {
																		errors.add("ownershipQ3FlagL3", new ActionMessage("error.ownershipQ3FlagL3.mandatory"));
																	}
																	
																}
															} else {
																errors.add("escodRevisionReasonQ3L3", new ActionMessage("error.escodRevisionReasonQ3L3.mandatory"));
															}
															
															//to set escodRevisionReasonQ4L3
															if (requestDTO.getEscodRevisionReasonQ4L3() != null && !requestDTO.getEscodRevisionReasonQ4L3().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ4L3().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ4L3().trim()))) {
																	errors.add("escodRevisionReasonQ4L3", new ActionMessage("error.escodRevisionReasonQ4L3.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ4L3(requestDTO.getEscodRevisionReasonQ4L3());
																	
																	//to set scopeQ1FlagL3
																	if (requestDTO.getScopeQ1FlagL3() != null && !requestDTO.getScopeQ1FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ1FlagL3().trim()) || "N".equals(requestDTO.getScopeQ1FlagL3().trim()))) {
																			errors.add("scopeQ1FlagL3", new ActionMessage("error.scopeQ1FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ1FlagL3(requestDTO.getScopeQ1FlagL3());
																		}
																	} else {
																		errors.add("scopeQ1FlagL3", new ActionMessage("error.scopeQ1FlagL3.mandatory"));
																	}
																	//to set scopeQ2FlagL3
																	if (requestDTO.getScopeQ2FlagL3() != null && !requestDTO.getScopeQ2FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ2FlagL3().trim()) || "N".equals(requestDTO.getScopeQ2FlagL3().trim()))) {
																			errors.add("scopeQ2FlagL3", new ActionMessage("error.scopeQ2FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ2FlagL3(requestDTO.getScopeQ2FlagL3());
																		}
																	} else {
																		errors.add("scopeQ2FlagL3", new ActionMessage("error.scopeQ2FlagL3.mandatory"));
																	}
																	//to set scopeQ3FlagL3
																	if (requestDTO.getScopeQ3FlagL3() != null && !requestDTO.getScopeQ3FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ3FlagL3().trim()) || "N".equals(requestDTO.getScopeQ3FlagL3().trim()))) {
																			errors.add("scopeQ3FlagL3", new ActionMessage("error.scopeQ3FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ3FlagL3(requestDTO.getScopeQ3FlagL3());
																		}
																	} else {
																		errors.add("scopeQ3FlagL3", new ActionMessage("error.scopeQ3FlagL3.mandatory"));
																	}
																	
																}
															} else {
																errors.add("escodRevisionReasonQ4L3", new ActionMessage("error.escodRevisionReasonQ4L3.mandatory"));
															}
														} else if(null != obj.getInfaProjectFlag() &&  "N".equals(obj.getInfaProjectFlag())) {
															//to set escodRevisionReasonQ5L3 
															if (requestDTO.getEscodRevisionReasonQ5L3() != null && !requestDTO.getEscodRevisionReasonQ5L3().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ5L3().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ5L3().trim()))) {
																	errors.add("escodRevisionReasonQ5L3", new ActionMessage("error.escodRevisionReasonQ5L3.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ5L3(requestDTO.getEscodRevisionReasonQ5L3());
																	
																	//to set ownershipQ1FlagL3
																	if (requestDTO.getOwnershipQ1FlagL3() != null && !requestDTO.getOwnershipQ1FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ1FlagL3().trim()) || "N".equals(requestDTO.getOwnershipQ1FlagL3().trim()))) {
																			errors.add("ownershipQ1FlagL3", new ActionMessage("error.ownershipQ1FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ1FlagL3(requestDTO.getOwnershipQ1FlagL3());
																		}
																	} else {
																		errors.add("ownershipQ1FlagL3", new ActionMessage("error.ownershipQ1FlagL3.mandatory"));
																	}
																	//to set ownershipQ2FlagL3
																	if (requestDTO.getOwnershipQ2FlagL3() != null && !requestDTO.getOwnershipQ2FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ2FlagL3().trim()) || "N".equals(requestDTO.getOwnershipQ2FlagL3().trim()))) {
																			errors.add("ownershipQ2FlagL3", new ActionMessage("error.ownershipQ2FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ2FlagL3(requestDTO.getOwnershipQ2FlagL3());
																		}
																	} else {
																		errors.add("ownershipQ2FlagL3", new ActionMessage("error.ownershipQ2FlagL3.mandatory"));
																	}
																	//to set ownershipQ3FlagL3
																	if (requestDTO.getOwnershipQ3FlagL3() != null && !requestDTO.getOwnershipQ3FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ3FlagL3().trim()) || "N".equals(requestDTO.getOwnershipQ3FlagL3().trim()))) {
																			errors.add("ownershipQ3FlagL3", new ActionMessage("error.ownershipQ3FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ3FlagL3(requestDTO.getOwnershipQ3FlagL3());
																		}
																	} else {
																		errors.add("ownershipQ3FlagL3", new ActionMessage("error.ownershipQ3FlagL3.mandatory"));
																	}
																	
																}
															} else {
																errors.add("escodRevisionReasonQ5L3", new ActionMessage("error.escodRevisionReasonQ5L3.mandatory"));
															}
															
															//to set escodRevisionReasonQ6L3
															if (requestDTO.getEscodRevisionReasonQ6L3() != null && !requestDTO.getEscodRevisionReasonQ6L3().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ6L3().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ6L3().trim()))) {
																	errors.add("escodRevisionReasonQ6L3", new ActionMessage("error.escodRevisionReasonQ6L3.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ6L3(requestDTO.getEscodRevisionReasonQ6L3());
																	
																	//to set scopeQ1FlagL3
																	if (requestDTO.getScopeQ1FlagL3() != null && !requestDTO.getScopeQ1FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ1FlagL3().trim()) || "N".equals(requestDTO.getScopeQ1FlagL3().trim()))) {
																			errors.add("scopeQ1FlagL3", new ActionMessage("error.scopeQ1FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ1FlagL3(requestDTO.getScopeQ1FlagL3());
																		}
																	} else {
																		errors.add("scopeQ1FlagL3", new ActionMessage("error.scopeQ1FlagL3.mandatory"));
																	}
																	//to set scopeQ2FlagL3
																	if (requestDTO.getScopeQ2FlagL3() != null && !requestDTO.getScopeQ2FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ2FlagL3().trim()) || "N".equals(requestDTO.getScopeQ2FlagL3().trim()))) {
																			errors.add("scopeQ2FlagL3", new ActionMessage("error.scopeQ2FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ2FlagL3(requestDTO.getScopeQ2FlagL3());
																		}
																	} else {
																		errors.add("scopeQ2FlagL3", new ActionMessage("error.scopeQ2FlagL3.mandatory"));
																	}
																	//to set scopeQ3FlagL3
																	if (requestDTO.getScopeQ3FlagL3() != null && !requestDTO.getScopeQ3FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ3FlagL3().trim()) || "N".equals(requestDTO.getScopeQ3FlagL3().trim()))) {
																			errors.add("scopeQ3FlagL3", new ActionMessage("error.scopeQ3FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ3FlagL3(requestDTO.getScopeQ3FlagL3());
																		}
																	} else {
																		errors.add("scopeQ3FlagL3", new ActionMessage("error.scopeQ3FlagL3.mandatory"));
																	}
																	
																}
															} else {
																errors.add("escodRevisionReasonQ6L3", new ActionMessage("error.escodRevisionReasonQ6L3.mandatory"));
															}
														}
														
														//to set revisedEscodL3
														if (requestDTO.getRevisedEscodL3() != null && !requestDTO.getRevisedEscodL3().toString().trim().isEmpty()) {
															try {
																if (! (errorCode = Validator.checkDate(requestDTO.getRevisedEscodL3(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																	errors.add("revisedEscodL3", new ActionMessage("error.revisedEscodL3.invalid.format"));
																 }	else {
																	 String d1 = (String)requestDTO.getRevisedEscodL3();
																		String[] d2 = d1.split("/");
																		if(d2.length == 3) {
																			if(d2[2].length() != 4) {
																				errors.add("revisedEscodL3", new ActionMessage("error.revisedEscodL3.invalid.format"));
																			}else {
																				relationshipDateFormat.parse(requestDTO.getRevisedEscodL3().toString().trim());
																				facilityDetailRequestDTO.setRevisedEscodL3(requestDTO.getRevisedEscodL3().trim());
																				}
																		}
																 }
															} catch (ParseException e) {
																errors.add("revisedEscodL3", new ActionMessage("error.revisedEscodL3.invalid.format"));
															}
														}/*else {
															errors.add("revisedEscodL3", new ActionMessage("error.revisedEscodL3.mandatory"));
														}*/
														//to set exeAssetClassL3
														/*if(requestDTO.getExeAssetClassL3()!=null && !requestDTO.getExeAssetClassL3().trim().isEmpty()){
															Object exeAssetClassL1Obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getExeAssetClassL3().trim(),
																	"exeAssetClass",errors,"EXE_ASSET_CLASS_ID");
															if(!(exeAssetClassL1Obj instanceof ActionErrors)){
																facilityDetailRequestDTO.setExeAssetClassL3(((ICommonCodeEntry)exeAssetClassL1Obj).getEntryCode());
																//to set exeAssetClassDtL2
																if(!requestDTO.getExeAssetClassL3().equals("1")) {
																	if (requestDTO.getExeAssetClassDtL3() != null && !requestDTO.getExeAssetClassDtL3().toString().trim().isEmpty()) {
																		try {
																			if (! (errorCode = Validator.checkDate(requestDTO.getExeAssetClassDtL3(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																				errors.add("exeAssetClassDtL3", new ActionMessage("error.exeAssetClassDtL3.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getExeAssetClassDtL3();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("exeAssetClassDtL3", new ActionMessage("error.exeAssetClassDtL3.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getExeAssetClassDtL3().toString().trim());
																							facilityDetailRequestDTO.setExeAssetClassDtL3(requestDTO.getExeAssetClassDtL3().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("exeAssetClassDtL3", new ActionMessage("error.exeAssetClassDtL3.invalid.format"));
																		}
																	} else {
																		errors.add("exeAssetClassDtL3", new ActionMessage("error.exeAssetClassDtL3.mandatory"));
																	}
																} else {
																	facilityDetailRequestDTO.setExeAssetClassDtL3("");
																}
															}
														}else{
															errors.add("exeAssetClassL3",new ActionMessage("error.exeAssetClassL3.mandatory"));
														}*/
														//to set revAssetClassL3
														if(requestDTO.getRevAssetClassL3()!=null && !requestDTO.getRevAssetClassL3().trim().isEmpty()){
															Object revAssetClassL1Obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getRevAssetClassL3().trim(),
																	"revAssetClass",errors,"REV_ASSET_CLASS_ID");
																facilityDetailRequestDTO.setRevAssetClassL3(((ICommonCodeEntry)revAssetClassL1Obj).getEntryCode());
																//to set revAssetClassDtL3
																if(!requestDTO.getRevAssetClassL3().equals("1")) {
																	if (requestDTO.getRevAssetClassDtL3() != null && !requestDTO.getRevAssetClassDtL3().toString().trim().isEmpty()) {
																		try {
																			if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDtL3(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																				errors.add("revAssetClassDtL3", new ActionMessage("error.revAssetClassDtL3.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getRevAssetClassDtL3();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("revAssetClassDtL3", new ActionMessage("error.revAssetClassDtL3.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getRevAssetClassDtL3().toString().trim());
																							facilityDetailRequestDTO.setRevAssetClassDtL3(requestDTO.getRevAssetClassDtL3().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("revAssetClassDtL3", new ActionMessage("error.revAssetClassDtL3.invalid.format"));
																		}
																	} else {
																		errors.add("revAssetClassDtL3", new ActionMessage("error.revAssetClassDtL3.mandatory"));
																	}
																} else {
																	
																	if(!requestDTO.getRevAssetClassDtL3().trim().equals("")) {
																		try {
																			if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDtL3(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																				errors.add("revAssetClassDtL3", new ActionMessage("error.revAssetClassDtL3.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getRevAssetClassDtL3();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("revAssetClassDtL3", new ActionMessage("error.revAssetClassDtL3.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getRevAssetClassDtL3().toString().trim());
																							facilityDetailRequestDTO.setRevAssetClassDtL3(requestDTO.getRevAssetClassDtL3().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("revAssetClassDtL3", new ActionMessage("error.revAssetClassDtL3.invalid.format"));
																		}
																	}else {
																		facilityDetailRequestDTO.setRevAssetClassDtL3("");
																		
																	}
																}
														} else {
															errors.add("revAssetClassL3",new ActionMessage("error.revAssetClassL3.mandatory"));
														}
													}//end 3rd Level delay
													
												}//end scodObj instanceof ActionErrors
												else {
													errors.add("delaylevel",new ActionMessage("error.delaylevel.invalid"));
												}
											} else {
//												errors.add("delaylevel", new ActionMessage("error.delaylevel.mandatory"));
												
												if("Y".equals(requestDTO.getDelayFlagL1())) {
													errors.add("delaylevel", new ActionMessage("error.delaylevel.mandatory"));
												}
												
												
												if(requestDTO.getRevAssetClass()!=null && !requestDTO.getRevAssetClass().trim().isEmpty()){
													Object scodObj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getRevAssetClass().trim(),
															"revAssetClass",errors,"REV_ASSET_CLASS_ID");
													if(!(scodObj instanceof ActionErrors)){
														facilityDetailRequestDTO.setRevAssetClass(((ICommonCodeEntry)scodObj).getEntryCode());
														//to set revAssetClassDt
														if(!requestDTO.getRevAssetClass().equals("1")) {
															if (requestDTO.getRevAssetClassDt() != null && !requestDTO.getRevAssetClassDt().toString().trim().isEmpty()) {
																try {
																	if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDt(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																		errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																	 }	else {
																		 String d1 = (String)requestDTO.getRevAssetClassDt();
																			String[] d2 = d1.split("/");
																			if(d2.length == 3) {
																				if(d2[2].length() != 4) {
																					errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																				}else {
																					relationshipDateFormat.parse(requestDTO.getRevAssetClassDt().toString().trim());
																					facilityDetailRequestDTO.setRevAssetClassDt(requestDTO.getRevAssetClassDt().trim());
																					}
																			}
																	 }
																} catch (ParseException e) {
																	errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																}
															} else {
																errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.mandatory"));
															}
														} else {
															
															if(!requestDTO.getRevAssetClassDt().trim().equals("")) {
															try {
																if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDt(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																	errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																 }	else {
																	 String d1 = (String)requestDTO.getRevAssetClassDt();
																		String[] d2 = d1.split("/");
																		if(d2.length == 3) {
																			if(d2[2].length() != 4) {
																				errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																			}else {
																				relationshipDateFormat.parse(requestDTO.getRevAssetClassDt().toString().trim());
																				facilityDetailRequestDTO.setRevAssetClassDt(requestDTO.getRevAssetClassDt().trim());
																				}
																		}
																 }
															} catch (ParseException e) {
																errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
															}
															}else {
																facilityDetailRequestDTO.setRevAssetClassDt("");
															}
														}
													} else {
														errors.add("revAssetClass",new ActionMessage("error.revAssetClass.invalid"));
													}
												}else{
													errors.add("revAssetClass",new ActionMessage("error.revAssetClass.mandatory"));
												}
												
											}
										}
									}
							 }
							 
							 if(requestDTO.getDelaylevel() == null || "".equals(requestDTO.getDelaylevel())) {
									facilityDetailRequestDTO.setDelayFlagL1("N");
								}else if("2".equals(requestDTO.getDelaylevel())) {
									facilityDetailRequestDTO.setDelayFlagL1(requestDTO.getDelayFlagL2());
								}else if("3".equals(requestDTO.getDelaylevel())) {
									facilityDetailRequestDTO.setDelayFlagL1(requestDTO.getDelayFlagL3());
								}
							 
					} else {
						errors.add("limitReleaseFlg", new ActionMessage("error.limitReleaseFlg.mandatory"));
					}
				}
			} else if(!"Initial".equals(requestDTO.getCamType())) {
				errors.add("camType", new ActionMessage("error.camType.invalid"));
			}
		} else {
			errors.add("camType", new ActionMessage("error.camType.mandatory"));
		}
		
		
		
		facilityDetailRequestDTO.setErrors(errors);
		return facilityDetailRequestDTO;
	}
	
	public LmtDetailForm getSCODFormFromDTO(FacilitySCODDetailRequestDTO dto, ICMSCustomer cust) {
		LmtDetailForm lmtDetailForm = new LmtDetailForm();
		String camId = "";
		try {
			if (dto.getCamId() != null && !dto.getCamId().trim().isEmpty()) {
				camId = dto.getCamId().trim();
			}

			/*
			 * if(dto.getCamType()!=null && !dto.getCamType().trim().isEmpty()){
			 * lmtDetailForm.setCamType(dto.getCamType().trim()); }
			 */
			lmtDetailForm.setLineNo("");
			//Initial CAM
			if (dto.getProjectFinance() != null && !dto.getProjectFinance().trim().isEmpty()) {
				lmtDetailForm.setProjectFinance(dto.getProjectFinance().trim());
			}

			if (dto.getProjectLoan() != null && !dto.getProjectLoan().trim().isEmpty()) {
				lmtDetailForm.setProjectLoan(dto.getProjectLoan().trim());
			}

			if (dto.getInfraFlag() != null && !dto.getInfraFlag().trim().isEmpty()) {
				lmtDetailForm.setInfaProjectFlag(dto.getInfraFlag().trim());
			}

			if (dto.getScod() != null && !dto.getScod().trim().isEmpty()) {
				lmtDetailForm.setScodDate(dto.getScod().trim());
			}

			if (dto.getScodRemark() != null && !dto.getScodRemark().trim().isEmpty()) {
				lmtDetailForm.setRemarksSCOD(dto.getScodRemark().trim());
			}
			
			if (dto.getExeAssetClass() != null && !dto.getExeAssetClass().trim().isEmpty()) {
				lmtDetailForm.setExstAssetClass(dto.getExeAssetClass().trim());
			}
			
			if (dto.getExeAssetClassDate()!=null && !dto.getExeAssetClassDate().trim().isEmpty()) {
				lmtDetailForm.setExstAssClassDate(dto.getExeAssetClassDate().trim());
			}
			
			//Interim 
			if (dto.getLimitReleaseFlg() != null
					&& !dto.getLimitReleaseFlg().isEmpty()) {
				lmtDetailForm.setWhlmreupSCOD(dto.getLimitReleaseFlg().trim());
			}
			
			if (dto.getRepayChngSched() != null
					&& !dto.getRepayChngSched().isEmpty()) {
				lmtDetailForm.setChngInRepaySchedule(dto.getRepayChngSched().trim());
			}
			
			if (dto.getRevAssetClass()!= null
					&& !dto.getRevAssetClass().isEmpty()) {
				lmtDetailForm.setRevisedAssetClass(dto.getRevAssetClass().trim());
			}
			
			if (dto.getRevAssetClassDt()!=null 
						&& !dto.getRevAssetClassDt().trim().isEmpty()) {
				lmtDetailForm.setRevsdAssClassDate(dto.getRevAssetClassDt().trim());
			}
			
			if (dto.getAcod()!=null 
						&& !dto.getAcod().trim().isEmpty()) {
				lmtDetailForm.setActualCommOpsDate(dto.getAcod().trim());
			}
			
			//Level 1
			
			if (dto.getDelayFlagL1() != null
					&& !dto.getDelayFlagL1().isEmpty()) {
				lmtDetailForm.setProjectDelayedFlag(dto.getDelayFlagL1().trim());
			}
			
			if (dto.getInterestFlag() != null
					&& !dto.getInterestFlag().isEmpty()) {
				lmtDetailForm.setPrincipalInterestSchFlag(dto.getInterestFlag().trim());
			}
			
			if (dto.getPriorReqFlag() != null
					&& !dto.getPriorReqFlag().isEmpty()) {
				lmtDetailForm.setRecvPriorOfSCOD(dto.getPriorReqFlag().trim());
			}
			
			if (dto.getDelaylevel() != null
					&& !dto.getDelaylevel().isEmpty()) {
				lmtDetailForm.setLelvelDelaySCOD(dto.getDelaylevel().trim());
			}
			
			if (dto.getDefReasonL1() != null
					&& !dto.getDefReasonL1().isEmpty()) {
				lmtDetailForm.setReasonLevelOneDelay(dto.getDefReasonL1().trim());
			}
			
			/*if (dto.getRepayChngSchedL1() != null
					&& !dto.getRepayChngSchedL1().isEmpty()) {
				lmtDetailForm.setProjectFinance(dto.getRepayChngSchedL1().trim());
			}*/
			
			if (dto.getEscodL1()!=null 
						&& !dto.getEscodL1().trim().isEmpty()) {
				lmtDetailForm.setEscodLevelOneDelayDate(dto.getEscodL1().trim());
			}
			
			if (dto.getOwnershipQ1FlagL1()!= null
					&& !dto.getOwnershipQ1FlagL1().isEmpty()) {
				lmtDetailForm.setPromotersCapRunFlag(dto.getOwnershipQ1FlagL1().trim());
			}
			
			if (dto.getOwnershipQ2FlagL1()!= null
					&& !dto.getOwnershipQ2FlagL1().isEmpty()) {
				lmtDetailForm.setPromotersHoldEquityFlag(dto.getOwnershipQ2FlagL1().trim());
			}
			
			if (dto.getOwnershipQ3FlagL1()!= null
					&& !dto.getOwnershipQ3FlagL1().isEmpty()) {
				lmtDetailForm.setHasProjectViabReAssFlag(dto.getOwnershipQ3FlagL1().trim());
			}
			
			if (dto.getScopeQ1FlagL1()!= null
					&& !dto.getScopeQ1FlagL1().isEmpty()) {
				lmtDetailForm.setChangeInScopeBeforeSCODFlag(dto.getScopeQ1FlagL1().trim());
			}
			
			if (dto.getScopeQ2FlagL1()!= null
					&& !dto.getScopeQ2FlagL1().isEmpty()) {
				lmtDetailForm.setCostOverrunOrg25CostViabilityFlag(dto.getScopeQ2FlagL1().trim());
			}
			
			if (dto.getScopeQ3FlagL1()!= null
					&& !dto.getScopeQ3FlagL1().isEmpty()) {
				lmtDetailForm.setProjectViabReassesedFlag(dto.getScopeQ3FlagL1().trim());
			}
			
			if (dto.getRevisedEscodL1()!=null 
						&& !dto.getRevisedEscodL1().trim().isEmpty()) {
				lmtDetailForm.setRevsedESCODStpDate(dto.getRevisedEscodL1().trim());
			}
			
			if (dto.getExeAssetClassL1()!= null
					&& !dto.getExeAssetClassL1().isEmpty()) {
				lmtDetailForm.setExstAssetClassL1(dto.getExeAssetClassL1().trim());
			}
			
			if (dto.getExeAssetClassDtL1()!=null 
						&& !dto.getExeAssetClassDtL1().trim().isEmpty()) {
				lmtDetailForm.setExstAssClassDateL1(dto.getExeAssetClassDtL1().trim());
			}
			
			if (dto.getRevAssetClassL1()!= null
					&& !dto.getRevAssetClassL1().isEmpty()) {
				lmtDetailForm.setRevisedAssetClassL1(dto.getRevAssetClassL1().trim());
			}
			
			if (dto.getRevAssetClassDtL1()!=null 
						&& !dto.getRevAssetClassDtL1().trim().isEmpty()) {
				lmtDetailForm.setRevsdAssClassDateL1(dto.getRevAssetClassDtL1().trim());
			}
			
			//Level 2
			
			if (dto.getDelayFlagL2() != null
					&& !dto.getDelayFlagL2().isEmpty()) {
				lmtDetailForm.setProjectDelayedFlagL2(dto.getDelayFlagL2().trim());
			}
			
			if (dto.getDelaylevel() != null
					&& !dto.getDelaylevel().isEmpty()) {
				lmtDetailForm.setLelvelDelaySCOD(dto.getDelaylevel().trim());
			}
			
			if (dto.getDefReasonL2() != null
					&& !dto.getDefReasonL2().isEmpty()) {
				lmtDetailForm.setReasonLevelTwoDelay(dto.getDefReasonL2().trim());
			}
			
			if (dto.getRepayChngSchedL2() != null
					&& !dto.getRepayChngSchedL2().isEmpty()) {
				lmtDetailForm.setChngInRepayScheduleL2(dto.getRepayChngSchedL2().trim());
			}
			
			if (dto.getEscodL2()!=null 
						&& !dto.getEscodL2().trim().isEmpty()) {
				lmtDetailForm.setEscodLevelSecondDelayDate(dto.getEscodL2().trim());
			}
			
			if (dto.getEscodRevisionReasonQ1L2() != null
					&& !dto.getEscodRevisionReasonQ1L2().isEmpty()) {
				lmtDetailForm.setLegalOrArbitrationLevel2Flag(dto.getEscodRevisionReasonQ1L2().trim());
			}
			
			if (dto.getEscodRevisionReasonQ2L2() != null
					&& !dto.getEscodRevisionReasonQ2L2().isEmpty()) {
				lmtDetailForm.setReasonBeyondPromoterLevel2Flag(dto.getEscodRevisionReasonQ2L2().trim());
			}
			
			if (dto.getEscodRevisionReasonQ3L2() != null
					&& !dto.getEscodRevisionReasonQ3L2().isEmpty()) {
				lmtDetailForm.setChngOfOwnPrjFlagInfraLevel2(dto.getEscodRevisionReasonQ3L2().trim());
			}
			
			if (dto.getEscodRevisionReasonQ4L2() != null
					&& !dto.getEscodRevisionReasonQ4L2().isEmpty()) {
				lmtDetailForm.setChngOfProjScopeInfraLevel2(dto.getEscodRevisionReasonQ4L2().trim());
			}
			
			if (dto.getEscodRevisionReasonQ5L2() != null
					&& !dto.getEscodRevisionReasonQ5L2().isEmpty()) {
				lmtDetailForm.setChngOfOwnPrjFlagNonInfraLevel2(dto.getEscodRevisionReasonQ5L2().trim());
			}
			
			if (dto.getEscodRevisionReasonQ6L2() != null
					&& !dto.getEscodRevisionReasonQ6L2().isEmpty()) {
				lmtDetailForm.setChngOfProjScopeNonInfraLevel2(dto.getEscodRevisionReasonQ6L2().trim());
			}
			
			if (dto.getLegalDetailL2() != null
					&& !dto.getLegalDetailL2().isEmpty()) {
				lmtDetailForm.setLeaglOrArbiProceed(dto.getLegalDetailL2().trim());
			}else {
				lmtDetailForm.setLeaglOrArbiProceed(" ");
			}
			
			if (dto.getBeyondControlL2() != null
					&& !dto.getBeyondControlL2().isEmpty()) {
				lmtDetailForm.setDetailsRsnByndPro(dto.getBeyondControlL2().trim());
			}else {
				lmtDetailForm.setDetailsRsnByndPro(" ");
			}
			
			if (dto.getOwnershipQ1FlagL2()!= null
					&& !dto.getOwnershipQ1FlagL2().isEmpty()) {
				lmtDetailForm.setPromotersCapRunFlagL2(dto.getOwnershipQ1FlagL2().trim());
			}
			
			if (dto.getOwnershipQ2FlagL2()!= null
					&& !dto.getOwnershipQ2FlagL2().isEmpty()) {
				lmtDetailForm.setPromotersHoldEquityFlagL2(dto.getOwnershipQ2FlagL2().trim());
			}
			
			if (dto.getOwnershipQ3FlagL2()!= null
					&& !dto.getOwnershipQ3FlagL2().isEmpty()) {
				lmtDetailForm.setHasProjectViabReAssFlagL2(dto.getOwnershipQ3FlagL2().trim());
			}
			
			if (dto.getScopeQ1FlagL2()!= null
					&& !dto.getScopeQ1FlagL2().isEmpty()) {
				lmtDetailForm.setChangeInScopeBeforeSCODFlagL2(dto.getScopeQ1FlagL2().trim());
			}
			
			if (dto.getScopeQ2FlagL2()!= null
					&& !dto.getScopeQ2FlagL2().isEmpty()) {
				lmtDetailForm.setCostOverrunOrg25CostViabilityFlagL2(dto.getScopeQ2FlagL2().trim());
			}
			
			if (dto.getScopeQ3FlagL2()!= null
					&& !dto.getScopeQ3FlagL2().isEmpty()) {
				lmtDetailForm.setProjectViabReassesedFlagL2(dto.getScopeQ3FlagL2().trim());
			}
			
			if (dto.getRevisedEscodL2()!=null && !dto.getRevisedEscodL2().trim().isEmpty()) {
				lmtDetailForm.setRevsedESCODStpDateL2(dto.getRevisedEscodL2().trim());
			}
			
			if (dto.getExeAssetClassL2()!= null
					&& !dto.getExeAssetClassL2().isEmpty()) {
				lmtDetailForm.setExstAssetClassL2(dto.getExeAssetClassL2().trim());
			}
			
			if (dto.getExeAssetClassDtL2()!=null && !dto.getExeAssetClassDtL2().trim().isEmpty()) {
				lmtDetailForm.setExstAssClassDateL2(dto.getExeAssetClassDtL2().trim());
			}
			
			
			if (dto.getRevAssetClassL2()!= null
					&& !dto.getRevAssetClassL2().isEmpty()) {
				lmtDetailForm.setRevisedAssetClass_L2(dto.getRevAssetClassL2().trim());
			}
			
			if (dto.getRevAssetClassDtL2()!=null && !dto.getRevAssetClassDtL2().trim().isEmpty()) {
				lmtDetailForm.setRevsdAssClassDate_L2(dto.getRevAssetClassDtL2().trim());
			}
			
			//Level 3
			
			if (dto.getDelayFlagL3() != null
					&& !dto.getDelayFlagL3().isEmpty()) {
				lmtDetailForm.setProjectDelayedFlagL3(dto.getDelayFlagL3().trim());
			}
			
			if (dto.getDelaylevel() != null
					&& !dto.getDelaylevel().isEmpty()) {
				lmtDetailForm.setLelvelDelaySCOD(dto.getDelaylevel().trim());
			}
			
			if (dto.getDefReasonL3() != null
					&& !dto.getDefReasonL3().isEmpty()) {
				lmtDetailForm.setReasonLevelThreeDelay(dto.getDefReasonL3().trim());
			}
			
			if (dto.getRepayChngSchedL3() != null
					&& !dto.getRepayChngSchedL3().isEmpty()) {
				lmtDetailForm.setChngInRepayScheduleL3(dto.getRepayChngSchedL3().trim());
			}
			
			if (dto.getEscodL3()!=null && !dto.getEscodL3().trim().isEmpty()) {
				lmtDetailForm.setEscodLevelThreeDelayDate(dto.getEscodL3().trim());
			}
			
			if (dto.getEscodRevisionReasonQ1L3() != null
					&& !dto.getEscodRevisionReasonQ1L3().isEmpty()) {
				lmtDetailForm.setLegalOrArbitrationLevel3Flag(dto.getEscodRevisionReasonQ1L3().trim());
			}
			
			if (dto.getEscodRevisionReasonQ2L3() != null
					&& !dto.getEscodRevisionReasonQ2L3().isEmpty()) {
				lmtDetailForm.setReasonBeyondPromoterLevel3Flag(dto.getEscodRevisionReasonQ2L3().trim());
			}
			
			if (dto.getEscodRevisionReasonQ3L3() != null
					&& !dto.getEscodRevisionReasonQ3L3().isEmpty()) {
				lmtDetailForm.setChngOfOwnPrjFlagInfraLevel3(dto.getEscodRevisionReasonQ3L3().trim());
			}
			
			if (dto.getEscodRevisionReasonQ4L3() != null
					&& !dto.getEscodRevisionReasonQ4L3().isEmpty()) {
				lmtDetailForm.setChngOfProjScopeInfraLevel3(dto.getEscodRevisionReasonQ4L3().trim());
			}
			
			if (dto.getEscodRevisionReasonQ5L3() != null
					&& !dto.getEscodRevisionReasonQ5L3().isEmpty()) {
				lmtDetailForm.setChngOfOwnPrjFlagNonInfraLevel3(dto.getEscodRevisionReasonQ5L3().trim());
			}
			
			if (dto.getEscodRevisionReasonQ6L3() != null
					&& !dto.getEscodRevisionReasonQ6L3().isEmpty()) {
				lmtDetailForm.setChngOfProjScopeNonInfraLevel3(dto.getEscodRevisionReasonQ6L3().trim());
			}
			
			if (dto.getLegalDetailL3() != null
					&& !dto.getLegalDetailL3().isEmpty()) {
				lmtDetailForm.setLeaglOrArbiProceedLevel3(dto.getLegalDetailL3().trim());
			}else {
				lmtDetailForm.setLeaglOrArbiProceedLevel3(" ");
			}
			
			if (dto.getBeyondControlL3() != null
					&& !dto.getBeyondControlL3().isEmpty()) {
				lmtDetailForm.setDetailsRsnByndProLevel3(dto.getBeyondControlL3().trim());
			}else {
				lmtDetailForm.setDetailsRsnByndProLevel3(" ");
			}
			
			if (dto.getOwnershipQ1FlagL3()!= null
					&& !dto.getOwnershipQ1FlagL3().isEmpty()) {
				lmtDetailForm.setPromotersCapRunFlagL3(dto.getOwnershipQ1FlagL3().trim());
			}
			
			if (dto.getOwnershipQ2FlagL3()!= null
					&& !dto.getOwnershipQ2FlagL3().isEmpty()) {
				lmtDetailForm.setPromotersHoldEquityFlagL3(dto.getOwnershipQ2FlagL3().trim());
			}
			
			if (dto.getOwnershipQ3FlagL3()!= null
					&& !dto.getOwnershipQ3FlagL3().isEmpty()) {
				lmtDetailForm.setHasProjectViabReAssFlagL3(dto.getOwnershipQ3FlagL3().trim());
			}
			
			if (dto.getScopeQ1FlagL3()!= null
					&& !dto.getScopeQ1FlagL3().isEmpty()) {
				lmtDetailForm.setChangeInScopeBeforeSCODFlagL3(dto.getScopeQ1FlagL3().trim());
			}
			
			if (dto.getScopeQ2FlagL3()!= null
					&& !dto.getScopeQ2FlagL3().isEmpty()) {
				lmtDetailForm.setCostOverrunOrg25CostViabilityFlagL3(dto.getScopeQ2FlagL3().trim());
			}
			
			if (dto.getScopeQ3FlagL3()!= null
					&& !dto.getScopeQ3FlagL3().isEmpty()) {
				lmtDetailForm.setProjectViabReassesedFlagL3(dto.getScopeQ3FlagL3().trim());
			}
			
			if (dto.getRevisedEscodL3()!=null && !dto.getRevisedEscodL3().trim().isEmpty()) {
				lmtDetailForm.setRevsedESCODStpDateL3(dto.getRevisedEscodL3().trim());
			}
			
			if (dto.getExeAssetClassL3()!= null
					&& !dto.getExeAssetClassL3().isEmpty()) {
				lmtDetailForm.setExstAssetClassL3(dto.getExeAssetClassL3().trim());
			}
			
			if (dto.getExeAssetClassDtL3()!=null && !dto.getExeAssetClassDtL3().trim().isEmpty()) {
				lmtDetailForm.setExstAssClassDateL3(dto.getExeAssetClassDtL3().trim());
			}
			
			if (dto.getRevAssetClassL3()!= null
					&& !dto.getRevAssetClassL3().isEmpty()) {
				lmtDetailForm.setRevisedAssetClass_L3(dto.getRevAssetClassL3().trim());
			}
			
			if (dto.getRevAssetClassDtL3()!=null && !dto.getRevAssetClassDtL3().trim().isEmpty()) {
				lmtDetailForm.setRevsdAssClassDate_L3(dto.getRevAssetClassDtL3().trim());
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		lmtDetailForm.setLimitProfileID(camId);

		if (dto.getClimsFacilityId() != null && !dto.getClimsFacilityId().trim().isEmpty()) {
			lmtDetailForm.setClimsFacilityId(dto.getClimsFacilityId().trim());
		} else {
			lmtDetailForm.setClimsFacilityId("");
		}

		lmtDetailForm.setEvent(dto.getEvent());
		lmtDetailForm.setIsFromCamonlineReq('Y');

		return lmtDetailForm;
	}

	
	//FOR ADHOC FACILITY  -Bhushan
	public AdhocFacilityRequestDTO getAdhocRequestDTOWithActualValues(AdhocFacilityRequestDTO requestDTO) {


	AdhocFacilityRequestDTO facilityDetailRequestDTO =new AdhocFacilityRequestDTO();
	MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
	ActionErrors errors = new ActionErrors();
	DefaultLogger.debug(this,
			"Inside getAdhocRequestDTOWithActualValues of FacilityDetailsDTOMapper================:::::");
	
	  SimpleDateFormat adhocInputFormat = new SimpleDateFormat( "dd/MM/yy" );
      SimpleDateFormat adhocDtFormat = new SimpleDateFormat( "dd/MMM/yyyy" );
	
	ILimitTrxValue lmtTrxObj = null;
	MILimitUIHelper helper = new MILimitUIHelper();
	SBMILmtProxy proxy = helper.getSBMILmtProxy();
	ILimitProfile profile = new OBLimitProfile();
	String errorCode = null;
//	System.out.println("requestDTO.getCamId() ::: "+requestDTO.getCamId() );
	if (null != requestDTO.getCamId()  && !requestDTO.getCamId().trim().isEmpty()) {
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		try {
			profile = limitProxy.getLimitProfile(Long.parseLong(requestDTO.getCamId().trim()));
			
			if (!( null != profile && !"".equals(profile))) {
				errors.add("camId", new ActionMessage("error.camId.invalid"));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			errors.add("camId", new ActionMessage("error.camId.invalid"));
		} catch (LimitException e) {
			e.printStackTrace();
			errors.add("camId", new ActionMessage("error.camId.invalid"));
		}
	} else {
		errors.add("camId", new ActionMessage("error.camId.mandatory"));
	}
	
	try {
	//logger 	System.out.println("requestDTO.getClimsFacilityId():: "+requestDTO.getClimsFacilityId());
		if (null !=  requestDTO.getClimsFacilityId() 
				&& !requestDTO.getClimsFacilityId().trim().isEmpty()) {
			lmtTrxObj = proxy.searchLimitByLmtId(requestDTO.getClimsFacilityId().trim());
		} else {
			errors.add("climsFacilityId", new ActionMessage("error.climsFacilityId.mandatory"));
		}
	} catch (Exception e) {
		errors.add("climsFacilityId", new ActionMessage("error.climsFacilityId.invalid"));
	}
	
	
	if(null != requestDTO.getClimsFacilityId()
			&& !requestDTO.getClimsFacilityId().trim().isEmpty() && null != requestDTO.getCamId()  && !requestDTO.getCamId().trim().isEmpty()) {
		LimitDAO lmtDao = new LimitDAO();
		String countNum = lmtDao.getFacilityIdAndCamIdCombineCount(requestDTO.getClimsFacilityId().trim(),requestDTO.getCamId().trim());
		System.out.println("countNum::: "+countNum);
		if("0".equals(countNum)) {
			errors.add("climsFacilityId", new ActionMessage("error.climsFacilityIdCamId.invalid"));
		}
	}
	if (null != requestDTO.getCamType() && !requestDTO.getCamType().trim().isEmpty()) {
		facilityDetailRequestDTO.setCamType(requestDTO.getCamType().trim());
		
		if ("Initial".equals(requestDTO.getCamType())) {
			System.out.println("YYYYYYYYYYYYYYYYRTRRRRRRRRRRRRRRRRRRRRRR");
			//Initial call i.e. for new facility
			DefaultLogger.debug(this,"======Initial CAM call========");
			if (null != requestDTO.getAdhocFacility()  && !requestDTO.getAdhocFacility().trim().isEmpty()) {
				if (!("Y".equals(requestDTO.getAdhocFacility().trim())
						|| "N".equals(requestDTO.getAdhocFacility().trim()))) {
					errors.add("adhocFacility", new ActionMessage("error.adhocFacility.invalid"));
				} else {
					facilityDetailRequestDTO.setAdhocFacility(requestDTO.getAdhocFacility());
				}
			} else {
				errors.add("adhocFacility", new ActionMessage("error.adhocFacility.mandatory"));
			}

			
			if (null != requestDTO.getGeneralPurposeLoan() && !requestDTO.getGeneralPurposeLoan().trim().isEmpty()) {
				if (!("Y".equals(requestDTO.getGeneralPurposeLoan().trim())
						|| "N".equals(requestDTO.getGeneralPurposeLoan().trim()))) {
					errors.add("generalPurposeLoan", new ActionMessage("error.generalPurposeLoan.invalid"));
				} else {
					facilityDetailRequestDTO.setGeneralPurposeLoan(requestDTO.getGeneralPurposeLoan());
				}
			} else {
				errors.add("generalPurposeLoan", new ActionMessage("error.generalPurposeLoan.mandatory"));
			}

			if ("Y".equals(requestDTO.getAdhocFacility())) {
				
				if (null != requestDTO.getMultiDrawDownAllow()  && !requestDTO.getMultiDrawDownAllow().trim().isEmpty()) {
					if (!("Y".equals(requestDTO.getMultiDrawDownAllow().trim())
							|| "N".equals(requestDTO.getMultiDrawDownAllow().trim()))) {
						errors.add("multiDrawDownAllow", new ActionMessage("error.multiDrawDownAllow.invalid"));
					} else {
						facilityDetailRequestDTO.setMultiDrawDownAllow(requestDTO.getMultiDrawDownAllow());
					}
				} else {
					errors.add("multiDrawDownAllow", new ActionMessage("error.multiDrawDownAllow.mandatory"));
				}
				if ( null != requestDTO.getAdhocTenor()  && !requestDTO.getAdhocTenor().trim().isEmpty()) {
					if (!("".equals(requestDTO.getAdhocTenor())) && !(isNumeric(requestDTO.getAdhocTenor()))  || requestDTO.getAdhocTenor().length() > 3)  {
						errors.add("adhocTenor", new ActionMessage("error.adhocTenor.invalidNumbers"));
					} else {
						facilityDetailRequestDTO.setAdhocTenor(requestDTO.getAdhocTenor());
					}
				} else {
					errors.add("adhocTenor", new ActionMessage("error.adhocTenor.mandatory"));
				}
				
				if ( null != requestDTO.getAdhocFacilityExpDate()  && !requestDTO.getAdhocFacilityExpDate().toString().trim().isEmpty()) {
					try {
						String adhocFCExpDt=	 adhocDtFormat.format( adhocInputFormat.parse( requestDTO.getAdhocFacilityExpDate() ) );
						if (! dateValidation(requestDTO.getAdhocFacilityExpDate())) {
							errors.add("adhocFacilityExpDate", new ActionMessage("error.adhocFacilityExpDate.invalid.format"));
						 }	
									else {
										facilityDetailRequestDTO.setAdhocFacilityExpDate(adhocFCExpDt.trim());
								}
					} catch (ParseException e) {
						errors.add("adhocFacilityExpDate", new ActionMessage("error.adhocFacilityExpDate.invalid.format"));
					}
				} else {
					errors.add("adhocFacilityExpDate", new ActionMessage("error.adhocFacilityExpDate.mandatory"));
				}
				
				if ( null != requestDTO.getAdhocLastAvailDate()  && !requestDTO.getAdhocLastAvailDate().toString().trim().isEmpty()) {
					try {
					String adhocLastDt=	 adhocDtFormat.format( adhocInputFormat.parse( requestDTO.getAdhocLastAvailDate() ) );
			      
					System.out.println(requestDTO.getAdhocLastAvailDate()+"adhocLastDt:::::::::: "+adhocLastDt);
					if (! dateValidation(requestDTO.getAdhocLastAvailDate())) {
						errors.add("adhocLastAvailDate", new ActionMessage("error.adhocLastAvailDate.invalid.format"));
						 }	else {
										facilityDetailRequestDTO.setAdhocLastAvailDate(adhocLastDt.trim());
								}
					} catch (ParseException e) {
						errors.add("adhocLastAvailDate", new ActionMessage("error.adhocLastAvailDate.invalid.format"));
					}
				} else {
					errors.add("adhocLastAvailDate", new ActionMessage("error.adhocLastAvailDate.mandatory"));
				}
				
			}else {
				
			}
//Interim or Annual		
		} else if ("Interim".equals(requestDTO.getCamType()) || "Annual".equals(requestDTO.getCamType())) {
			//Interim call i.e. for update facility
			System.out.println("===KKKKKKKKKKKKKKKK==Interim CAM call========");
			DefaultLogger.debug(this,"======Interim CAM call========");
			if(null!=lmtTrxObj && null!=lmtTrxObj.getLimit()) {
				//INTERIM  CAM (CHANGE IN ADHOC)
				ILimit obj=lmtTrxObj.getLimit();
				System.out.println("Interim Existing Facility:: "+obj.getAdhocFacility()+ " &&&  Input:: "+requestDTO.getAdhocFacility());
					if (null != requestDTO.getAdhocFacility() && !requestDTO.getAdhocFacility().trim().isEmpty()) {
						
					if (!("Y".equals(requestDTO.getAdhocFacility().trim())	|| "N".equals(requestDTO.getAdhocFacility().trim()))) {
							errors.add("adhocFacility", new ActionMessage("error.adhocFacility.invalid"));
						}else if (! requestDTO.getAdhocFacility().trim().equalsIgnoreCase(obj.getAdhocFacility())) {
							errors.add("adhocFacility", new ActionMessage("error.adhocFacility.modified"));
						}
						else {
							facilityDetailRequestDTO.setAdhocFacility(requestDTO.getAdhocFacility());
						}
					} else {
						errors.add("adhocFacility", new ActionMessage("error.adhocFacility.mandatory"));
					}

					if (null != requestDTO.getGeneralPurposeLoan()  && !requestDTO.getGeneralPurposeLoan().trim().isEmpty()) {
						if (!("Y".equals(requestDTO.getGeneralPurposeLoan().trim())
								|| "N".equals(requestDTO.getGeneralPurposeLoan().trim()))) {
							errors.add("generalPurposeLoan", new ActionMessage("error.generalPurposeLoan.invalid"));
						} else {
							facilityDetailRequestDTO.setGeneralPurposeLoan(requestDTO.getGeneralPurposeLoan());
						}
					} else {
						errors.add("generalPurposeLoan", new ActionMessage("error.generalPurposeLoan.mandatory"));
					}

					if ("Y".equals(requestDTO.getAdhocFacility())) {
						
						if (null != requestDTO.getMultiDrawDownAllow()  && !requestDTO.getMultiDrawDownAllow().trim().isEmpty()) {
							if (!("Y".equals(requestDTO.getMultiDrawDownAllow().trim())
									|| "N".equals(requestDTO.getMultiDrawDownAllow().trim()))) {
								errors.add("multiDrawDownAllow", new ActionMessage("error.multiDrawDownAllow.invalid"));
							} else {
								facilityDetailRequestDTO.setMultiDrawDownAllow(requestDTO.getMultiDrawDownAllow());
							}
						} else {
							errors.add("multiDrawDownAllow", new ActionMessage("error.multiDrawDownAllow.mandatory"));
						}

						if (  null != requestDTO.getAdhocTenor() && !requestDTO.getAdhocTenor().trim().isEmpty()) {
							if (!("".equals(requestDTO.getAdhocTenor())) && !(isNumeric(requestDTO.getAdhocTenor())) || requestDTO.getAdhocTenor().length() >3 ) {
								errors.add("adhocTenor", new ActionMessage("error.adhocTenor.invalidNumbers"));
							} else {
								facilityDetailRequestDTO.setAdhocTenor(requestDTO.getAdhocTenor());
							}
						} else {
							errors.add("adhocTenor", new ActionMessage("error.adhocTenor.mandatory"));
						}
						
						if ( null != requestDTO.getAdhocFacilityExpDate()  && !requestDTO.getAdhocFacilityExpDate().toString().trim().isEmpty()) {
							try {
								String adhocFCExpDt=	 adhocDtFormat.format( adhocInputFormat.parse( requestDTO.getAdhocFacilityExpDate() ) );
				 				if (! dateValidation(requestDTO.getAdhocFacilityExpDate())) {
									errors.add("adhocFacilityExpDate", new ActionMessage("error.adhocFacilityExpDate.invalid.format"));
								 }	
											else {
												facilityDetailRequestDTO.setAdhocFacilityExpDate(adhocFCExpDt.trim());
										}
							} catch (ParseException e) {
								errors.add("adhocFacilityExpDate", new ActionMessage("error.adhocFacilityExpDate.invalid.format"));
							}
						} else {
							errors.add("adhocFacilityExpDate", new ActionMessage("error.adhocFacilityExpDate.mandatory"));
						}
						
						if ( null != requestDTO.getAdhocLastAvailDate()  && !requestDTO.getAdhocLastAvailDate().toString().trim().isEmpty())
						{
							try {
							String adhocLastDt=	 adhocDtFormat.format( adhocInputFormat.parse( requestDTO.getAdhocLastAvailDate() ) );
					      
							System.out.println(requestDTO.getAdhocLastAvailDate()+"adhocLastDt:::::::::: "+adhocLastDt);
									if (! dateValidation(requestDTO.getAdhocLastAvailDate())) 
									 {
										errors.add("adhocLastAvailDate", new ActionMessage("error.adhocLastAvailDate.invalid.format"));
									  }	else {
												facilityDetailRequestDTO.setAdhocLastAvailDate(adhocLastDt.trim());
										 }
							} catch (ParseException e) {
								errors.add("adhocLastAvailDate", new ActionMessage("error.adhocLastAvailDate.invalid.format"));
							}
						}
						else {
							errors.add("adhocLastAvailDate", new ActionMessage("error.adhocLastAvailDate.mandatory"));
						}
						
					}else {
						
					}
				
			}
		} else {
			errors.add("camType", new ActionMessage("error.camType.invalid"));
		}
	} else {
		
		errors.add("camType", new ActionMessage("error.camtype.mandatory_1"));
	}
	
	facilityDetailRequestDTO.setErrors(errors);
	return facilityDetailRequestDTO;
}// method End
	
	public LmtDetailForm getAdhocFormFromDTO(AdhocFacilityRequestDTO dto, ICMSCustomer cust) {
	LmtDetailForm lmtDetailForm = new LmtDetailForm();
	String camId = "";
  //logger   System.out.println("********* Inside getAdhocFormFromDTO() ****************");
	try {
		if (null != dto.getCamId()  && !dto.getCamId().trim().isEmpty()) {
			camId = dto.getCamId().trim();
		}

	
		
		//Initial CAM
		if (null != dto.getAdhocFacility() && !dto.getAdhocFacility().trim().isEmpty()) {
			lmtDetailForm.setAdhocFacility(dto.getAdhocFacility().trim());
		}

		if (null != dto.getAdhocFacilityExpDate() && !dto.getAdhocFacilityExpDate().trim().isEmpty()) {
			lmtDetailForm.setAdhocFacilityExpDate(dto.getAdhocFacilityExpDate().trim());
		}

		if (null != dto.getAdhocLastAvailDate()  && !dto.getAdhocLastAvailDate().trim().isEmpty()) {
			lmtDetailForm.setAdhocLastAvailDate(dto.getAdhocLastAvailDate().trim());
		}

		if (null != dto.getAdhocTenor()  && !dto.getAdhocTenor().trim().isEmpty()) {
			lmtDetailForm.setAdhocTenor(dto.getAdhocTenor().trim());
		}

		if (null != dto.getMultiDrawDownAllow()  && !dto.getMultiDrawDownAllow().trim().isEmpty()) {
			lmtDetailForm.setMultiDrawdownAllow(dto.getMultiDrawDownAllow().trim());
		}
		
		if (null != dto.getGeneralPurposeLoan()  && !dto.getGeneralPurposeLoan().trim().isEmpty()) {
			lmtDetailForm.setGeneralPurposeLoan(dto.getGeneralPurposeLoan().trim());
		}
		
		//Interim 
		
	
		
	} catch (NumberFormatException e) {
		e.printStackTrace();
	}
	lmtDetailForm.setLimitProfileID(camId);

	if (null != dto.getClimsFacilityId()  && !dto.getClimsFacilityId().trim().isEmpty()) {
		lmtDetailForm.setClimsFacilityId(dto.getClimsFacilityId().trim());
	} else {
		lmtDetailForm.setClimsFacilityId("");
	}

	lmtDetailForm.setEvent(dto.getEvent());
	lmtDetailForm.setIsFromCamonlineReq('Y');

	return lmtDetailForm;
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
	public Boolean dateValidation(String inputDt){
	  SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
	    sdfrmt.setLenient(false);
	   
	    try
	    {
	    	 String[] d2 = inputDt.split("/");
	 		if(d2.length == 3) {
	 			if(d2[0].length() != 2  || d2[1].length() != 2 ||  d2[2].length() != 4) {
	 				return false;
	 			}
	 		}
	        Date javaDate = sdfrmt.parse(inputDt); 
	        System.out.println(inputDt+" is valid date format");
	     }
	    /* Date format is invalid */
	    catch (ParseException e)
	    {
	        System.out.println(inputDt+" is Invalid Date format");
	        return false;
	    }
	    /* Return true if date format is valid */
	    return true;
	}
	public FacilityBodyRestRequestDTO getRestRequestDTOWithActualValues(FacilityBodyRestRequestDTO facilityDetailsRequest) {
		LimitDAO lmtDao = new LimitDAO();
		String errorCode = null;
		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();
		DefaultLogger.debug(this, "Inside getRequestDTOWithActualValues of FacilityDetailsDTOMapper================:::::");

		System.out.println("11111" );
		
		if(facilityDetailsRequest.getFacilityCategoryId()!=null && !facilityDetailsRequest.getFacilityCategoryId().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityName("entryCode", facilityDetailsRequest.getFacilityCategoryId().trim(),"facilityCategory",errors,"FACILITY_CATEGORY");
			if(!(obj instanceof ActionErrors)){
				facilityDetailsRequest.setFacilityCategoryId(((ICommonCodeEntry)obj).getEntryCode());
			}
			else {
				errors.add("facilityCategory",new ActionMessage("error.invalid"));
			}
		}else{
			errors.add("facilityCategory",new ActionMessage("error.facilityCategory.mandatory"));
		}
		
		if(facilityDetailsRequest.getFacilityTypeId()!=null && !facilityDetailsRequest.getFacilityTypeId().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityName("entryCode", facilityDetailsRequest.getFacilityTypeId().trim(),"facilityType",errors,"FACILITY_TYPE");
			if(!(obj instanceof ActionErrors)){
				facilityDetailsRequest.setFacilityTypeId(((ICommonCodeEntry)obj).getEntryCode());
			}
			else {
				errors.add("facilityType",new ActionMessage("error.invalid"));
			}
		}else{
			errors.add("facilityType",new ActionMessage("error.facilityType.mandatory"));
		}
		
			 if (facilityDetailsRequest.getFacilityName() != null && !facilityDetailsRequest.getFacilityName().isEmpty()) {
				 Object obj = masterObj.getObjByEntityNameAndfacilityName( "actualFacilityNewMaster",facilityDetailsRequest.getFacilityName(),"FACILITY_NAME",errors);				
				 if(!(obj instanceof ActionErrors)){
					 facilityDetailsRequest.setFacilityName(((IFacilityNewMaster) obj).getNewFacilityName());
					}
					else {
						errors.add("facilityName",new ActionMessage("error.facilityName.invalid"));
					}
				}else{
					errors.add("facilityName",new ActionMessage("error.facilityName.mandatory"));
				}
		if(facilityDetailsRequest.getSanctionedAmount()!=null && !facilityDetailsRequest.getSanctionedAmount().trim().isEmpty())
		{
			if (!(errorCode =Validator.checkAmount(facilityDetailsRequest.getSanctionedAmount(), true,0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2,IGlobalConstant.DEFAULT_CURRENCY, Locale.getDefault()))
					.equals(Validator.ERROR_NONE)) {
				errors.add("sanctionedAmount", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2_STR));
		 }
			
		}else{
			errors.add("sanctionedAmount",new ActionMessage("error.sanctionedAmount.mandatory"));
		}
		
		if(facilityDetailsRequest.getEvent()!=null && ("WS_FAC_CREATE_REST".equalsIgnoreCase(facilityDetailsRequest.getEvent())
				) ){/*
		if(facilityDetailsRequest.getClimsFacilityId()!=null && !facilityDetailsRequest.getClimsFacilityId().trim().isEmpty())
		{
			System.out.println("22Inside 22 climsFacilityId" );
			errors.add("climsFacilityId",new ActionMessage("error.climsFacilityId.notrequired"));
			
		}
		if(facilityDetailsRequest.getMainFacilityId()!=null && !facilityDetailsRequest.getMainFacilityId().trim().isEmpty())
		{
			System.out.println("22Inside 22 climsFacilityId" );
			errors.add("mainFacilityId",new ActionMessage("error.mainFacilityId.notrequired"));
			
		}
		*/}
		
        if (facilityDetailsRequest.getIsScod() != null && !facilityDetailsRequest.getIsScod().isEmpty()) {
            if((facilityDetailsRequest.getIsScod().equals("Y") || facilityDetailsRequest.getIsScod().equals("N"))) { 
            	facilityDetailsRequest.setIsScod(facilityDetailsRequest.getIsScod().trim());
            }else {
                errors.add("isScod",new ActionMessage("error.isScod.invalid"));                
            }
        }

		if (facilityDetailsRequest.getEvent() != null
				&& ("WS_FAC_EDIT_REST".equalsIgnoreCase(facilityDetailsRequest.getEvent()))) {
			if (null == facilityDetailsRequest.getClimsFacilityId()
					|| facilityDetailsRequest.getClimsFacilityId().trim().isEmpty()) {
				errors.add("climsFacilityId", new ActionMessage("error.mandatory"));

			}
			
			try{
				ILimitTrxValue lmtTrxObj = null;
				MILimitUIHelper helper = new MILimitUIHelper();
				SBMILmtProxy proxy = helper.getSBMILmtProxy();
				if(facilityDetailsRequest.getClimsFacilityId()!=null && !facilityDetailsRequest.getClimsFacilityId().trim().isEmpty()){
					lmtTrxObj = proxy.searchLimitByLmtId(facilityDetailsRequest.getClimsFacilityId().trim());
				}
			}
			catch(Exception e){
				errors.add("climsFacilityId", new ActionMessage("error.invalid"));
				//throw new CMSValidationFault("climsFacilityId","Invalid climsFacilityId");
			}
		}
		
		if(facilityDetailsRequest.getReleasableAmount()!=null && !facilityDetailsRequest.getReleasableAmount().trim().isEmpty())
		{
			if (!(errorCode =Validator.checkAmount(facilityDetailsRequest.getReleasableAmount(), true,0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_22_2,IGlobalConstant.DEFAULT_CURRENCY, Locale.getDefault()))
					.equals(Validator.ERROR_NONE)) {
				errors.add("releasableAmount", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_22_2_STR));
		 }
			
		}
		 
		 ILimitProfile profile= new OBLimitProfile();
		 if(facilityDetailsRequest.getCamId()!=null && !facilityDetailsRequest.getCamId().trim().isEmpty()){
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			try {
				profile = limitProxy.getLimitProfile(Long.parseLong(facilityDetailsRequest.getCamId().trim()));
				if(!(profile != null && !"".equals(profile))){
					 errors.add("camId",new ActionMessage("error.camId.invalid"));
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				 errors.add("camId",new ActionMessage("error.camId.invalid"));
			} catch (LimitException e) {
				e.printStackTrace();
				 errors.add("camId",new ActionMessage("error.camId.invalid"));
			}
		 }else{
			 errors.add("camId",new ActionMessage("error.camId.mandatory"));
		 }
			System.out.println("2222" );

		 if(facilityDetailsRequest.getSubLimitFlag()!=null && !facilityDetailsRequest.getSubLimitFlag().trim().isEmpty()){
				if(!("Yes".equals(facilityDetailsRequest.getSubLimitFlag().trim()) || "No".equals(facilityDetailsRequest.getSubLimitFlag().trim()))){
					errors.add("subLimitFlag",new ActionMessage("error.subLimitFlag.invalid"));
				}
				else if("Yes".equals(facilityDetailsRequest.getSubLimitFlag().trim())){ 
					if(facilityDetailsRequest.getGuaranteeFlag()!=null && !facilityDetailsRequest.getGuaranteeFlag().trim().isEmpty()) {
					if("No".equals(facilityDetailsRequest.getGuaranteeFlag().trim())) {
					if(facilityDetailsRequest.getMainFacilityId()!= null && !"".equals(facilityDetailsRequest.getMainFacilityId().trim())){
						
						if (null != facilityDetailsRequest.getFacilityNameGuar()
								&& !facilityDetailsRequest.getFacilityNameGuar().trim().isEmpty()) {

							boolean flag = false;
							flag = lmtDao.checkFacNameGuar(facilityDetailsRequest.getFacilityNameGuar().trim(),
									facilityDetailsRequest.getMainFacilityId().trim());
							if (flag) {
								facilityDetailsRequest.setFacilityNameGuar(facilityDetailsRequest.getFacilityNameGuar().trim());
							} else {
								errors.add("facilityNameGuar", new ActionMessage("error.invalid"));
							}
						}
						
						String mainFacIdSentInRequest = facilityDetailsRequest.getMainFacilityId(); 
						ILimitTrxValue lmtTrxObj = null;
						MILimitUIHelper helper = new MILimitUIHelper();
						SBMILmtProxy proxy = helper.getSBMILmtProxy();
						try {
							lmtTrxObj = proxy.searchLimitByLmtId(facilityDetailsRequest.getMainFacilityId().trim());
							ILimit limit = (ILimit)lmtTrxObj.getLimit();
							
							if(limit == null || "".equals(limit)){
								errors.add("mainFacilityId",new ActionMessage("error.mainFacilityId.invalid"));
							}else{
								
								//Set mainFacilityId
								if(limit.getLimitRef()!=null && !limit.getLimitRef().isEmpty()){
									facilityDetailsRequest.setMainFacilityId(limit.getLimitRef());
									facilityDetailsRequest.setSubFacilityName(limit.getFacilityName());
								}

								//To check mainFacilityID belongs to same CAM or not : Added on 23-Nov-2015
								if(profile.getLimitProfileID()!=0 && limit.getLimitProfileID()!=0){
									if(profile.getLimitProfileID() != limit.getLimitProfileID()){
										errors.add("mainFacilityId",new ActionMessage("error.facility.does.not.belongs.to.cam",String.valueOf(profile.getLimitProfileID()),mainFacIdSentInRequest));
									}
								}
								
								
							
								
							}
						} catch (LimitException e) {
							e.printStackTrace();
							errors.add("mainFacilityId",new ActionMessage("error.mainFacilityId.invalid"));
						} catch (RemoteException e) {
							e.printStackTrace();
							errors.add("mainFacilityId",new ActionMessage("error.mainFacilityId.invalid"));
						}
						
						   

							
					}else{
						errors.add("mainFacilityId",new ActionMessage("error.mainFacilityId.mandatory"));
					}
					
					}if("Yes".equals(facilityDetailsRequest.getGuaranteeFlag().trim())) {
						ILimitDAO limitDAO = (ILimitDAO) BeanHouse.get("limitJdbcDao");
						if(facilityDetailsRequest.getPartyName()!= null && !"".equals(facilityDetailsRequest.getPartyName().trim())){ 
							
							boolean flag1 = false;
							flag1 = limitDAO.checkpartyCount(facilityDetailsRequest.getCamId(), facilityDetailsRequest.getPartyName() );
							if (!flag1) {
								errors.add("PartyName", new ActionMessage("error.invalid"));
							}
						}
						else{
							errors.add("PartyName",new ActionMessage("error.mandatory"));
						}
						if(facilityDetailsRequest.getLiabilityId()!= null && !"".equals(facilityDetailsRequest.getLiabilityId().trim())){ 

							boolean flag2 = false;
							flag2 = limitDAO.checkLiabilityID(facilityDetailsRequest.getPartyName(), facilityDetailsRequest.getLiabilityId() );
							if (!flag2) {
								errors.add("LiabilityId", new ActionMessage("error.invalid"));
							}
						}
						else{
							errors.add("LiabilityId",new ActionMessage("error.mandatory"));
						}

					}
					
					}
					
				}
			}
		 
		
		 /*else{
			 errors.add("subLimitFlag",new ActionMessage("error.subLimitFlag.mandatory"));
		 }*/
			System.out.println("33333" );

		 if(facilityDetailsRequest.getSecured()!=null && !facilityDetailsRequest.getSecured().trim().isEmpty()){
				if(!("Y".equals(facilityDetailsRequest.getSecured().trim()) || "N".equals(facilityDetailsRequest.getSecured().trim()))){
					errors.add("secured",new ActionMessage("error.secured.invalid"));
				}
				else if("Y".equals(facilityDetailsRequest.getSecured().trim())){
					
					if(facilityDetailsRequest.getSecurityList()!= null && !"".equals(facilityDetailsRequest.getSecurityList()))
					{   
						if(!(facilityDetailsRequest.getSecurityList().size()>=1)){
							errors.add("securityList",new ActionMessage("error.securityList.atleastone"));
						}
					}
					else{
						 errors.add("securityList",new ActionMessage("error.securityList.atleastone"));
					}
				}/*else if("N".equals(facilityDetailsRequest.getSecured().trim())){
					
					if(facilityDetailsRequest.getSecurityList()!= null && !"".equals(facilityDetailsRequest.getSecurityList()))
					{   
						if((facilityDetailsRequest.getSecurityList().size()>=1)){
							errors.add("secured",new ActionMessage("error.secured.flag.set"));
						}
					}
				}*/
			}
		 /*else{
				if("Y".equals(facilityDetailsRequest.getDpRequired().trim()) && "Y".equals(facilityDetailsRequest.getIsDpCalulated().trim()))
					errors.add("secured",new ActionMessage("error.secured.mandatory"));
				}*/
		 
		 if(facilityDetailsRequest.getCurrency()!=null && !facilityDetailsRequest.getCurrency().trim().isEmpty()){
				Object obj = masterObj.getObjectforMasterRelatedCode("actualForexFeedEntry", facilityDetailsRequest.getCurrency().trim(),"currency",errors);
				if(!(obj instanceof ActionErrors)){
					facilityDetailsRequest.setCurrency(((IForexFeedEntry)obj).getCurrencyIsoCode().trim());
				}
			}else{
				facilityDetailsRequest.setCurrency("INR");
			}
		 
		 if(facilityDetailsRequest.getSanctionedAmount()!=null && !facilityDetailsRequest.getSanctionedAmount().trim().isEmpty()){
			 facilityDetailsRequest.setSanctionedAmount(facilityDetailsRequest.getSanctionedAmount().trim());
		 }else{
			 facilityDetailsRequest.setSanctionedAmount("0");
		 }
		 
		 
		 
		 if(facilityDetailsRequest.getGrade()!=null && !facilityDetailsRequest.getGrade().trim().isEmpty()){
			 if (!(errorCode = Validator.checkInteger(facilityDetailsRequest.getGrade().trim(), true, 1, 10)).equals(Validator.ERROR_NONE)) { 
				 errors.add("grade", new ActionMessage("error.grade.invalid")); 
			}
		 }else{
			 errors.add("grade", new ActionMessage("error.mandatory")); 
		 }
		 
		 if(facilityDetailsRequest.getDpRequired()!=null && !facilityDetailsRequest.getDpRequired().trim().isEmpty()){
			 
			 if(!("Y".equals(facilityDetailsRequest.getDpRequired().trim()) || "N".equals(facilityDetailsRequest.getDpRequired().trim()))){
					errors.add("dpRequired",new ActionMessage("error.invalid"));
				}
		 }else{
				errors.add("dpRequired", new ActionMessage("error.string.mandatory"));
		 }
		 
		 if(facilityDetailsRequest.getIsReleased()!=null && !facilityDetailsRequest.getIsReleased().trim().isEmpty()){
			 
			 if(!("Y".equals(facilityDetailsRequest.getIsReleased().trim()) || "N".equals(facilityDetailsRequest.getIsReleased().trim()))){
					errors.add("isReleased",new ActionMessage("error.invalid"));
				}
		 }
		
		 if (facilityDetailsRequest.getBankingArrangement() != null && !facilityDetailsRequest.getBankingArrangement().trim().isEmpty()) 
		 {
			 Object obj = masterObj.getObjectByEntityName("entryCode", facilityDetailsRequest.getBankingArrangement().trim(),"bankingArrangement",errors,"BANKING_ARRANGEMENT");
			 if(!(obj instanceof ActionErrors)){
				 facilityDetailsRequest.setBankingArrangement(facilityDetailsRequest.getBankingArrangement());
			 }
			 else {
					errors.add("bankingArrangement",new ActionMessage("error.invalid"));
				}
		 }else {
				errors.add("bankingArrangement", new ActionMessage("error.mandatory"));
			}
		 
		 if (facilityDetailsRequest.getRelationshipManager() != null && !facilityDetailsRequest.getRelationshipManager().trim().isEmpty()) 
		 {

			 boolean flag = lmtDao.getEmployeeCodeRM(facilityDetailsRequest.getRelationshipManager().trim());
			 if(flag){
				 facilityDetailsRequest.setRelationshipManager(facilityDetailsRequest.getRelationshipManager());
			 }
			 else {
					errors.add("RelationshipManager", new ActionMessage("error.invalid"));
				}
		 }
		 
		
		if (facilityDetailsRequest.getClauseAsPerPolicy() != null && !facilityDetailsRequest.getClauseAsPerPolicy().trim().isEmpty()) 
		 {
			 Object obj = masterObj.getObjectByEntityName("entryCode", facilityDetailsRequest.getClauseAsPerPolicy().trim(),"clauseAsPerPolicy",errors,"CLAUSE_AS_PER_POLICY");
			 if(!(obj instanceof ActionErrors)){
				 facilityDetailsRequest.setClauseAsPerPolicy(((ICommonCodeEntry)obj).getEntryCode());
			 }
		 }else {
				errors.add("clauseAsPerPolicy", new ActionMessage("error.mandatory"));
			}
			System.out.println("444444" );
		
		if(facilityDetailsRequest.getIsDpCalulated()!=null && !facilityDetailsRequest.getIsDpCalulated().trim().isEmpty())
		{
				 if(!("Y".equals(facilityDetailsRequest.getIsDpCalulated().trim()) || "N".equals(facilityDetailsRequest.getIsDpCalulated().trim())))
				 {
						errors.add("IsDpCalulated",new ActionMessage("error.invalid"));
				 }
	    }
		if(facilityDetailsRequest.getSyndicateLoan()!=null && !facilityDetailsRequest.getSyndicateLoan().trim().isEmpty())
		{
				 if(!("Y".equals(facilityDetailsRequest.getSyndicateLoan().trim()) || "N".equals(facilityDetailsRequest.getSyndicateLoan().trim())))
				 {
						errors.add("SyndicateLoan",new ActionMessage("error.invalid"));
				 }
	    }
		if(facilityDetailsRequest.getIsAdhoc()!=null && !facilityDetailsRequest.getIsAdhoc().trim().isEmpty())
		{
				 if(!("Y".equals(facilityDetailsRequest.getIsAdhoc().trim()) || "N".equals(facilityDetailsRequest.getIsAdhoc().trim())))
				 {
						errors.add("IsAdhoc",new ActionMessage("error.invalid"));
				 }
	    }
		if(facilityDetailsRequest.getIsAdhocToSanction()!=null && !facilityDetailsRequest.getIsAdhocToSanction().trim().isEmpty())
		{
				 if(!("Y".equals(facilityDetailsRequest.getIsAdhocToSanction().trim()) || "N".equals(facilityDetailsRequest.getIsAdhocToSanction().trim())))
				 {
						errors.add("IsAdhocToSanction",new ActionMessage("error.invalid"));
				 }
	    }
		if(facilityDetailsRequest.getGuaranteeFlag()!=null && !facilityDetailsRequest.getGuaranteeFlag().trim().isEmpty())
		{
				 if(!("Yes".equals(facilityDetailsRequest.getGuaranteeFlag().trim()) || "No".equals(facilityDetailsRequest.getGuaranteeFlag().trim())))
				 {
						errors.add("GuaranteeFlag",new ActionMessage("error.invalid"));
				 }
	    }	
		
		if(facilityDetailsRequest.getSubLimitFlag()!=null && !facilityDetailsRequest.getSubLimitFlag().trim().isEmpty())
		{
			if(facilityDetailsRequest.getSubLimitFlag().equals("Yes")) 
		    {
				if (!(errorCode = Validator.checkString(facilityDetailsRequest.getGuaranteeFlag(), true, 1, 40)).equals(Validator.ERROR_NONE)) 
			    { 
					errors.add("guarantee", new ActionMessage("error.string.mandatory")); 
			    }
		     }
		}
		
		if(facilityDetailsRequest.getTenor()!=null && !facilityDetailsRequest.getTenor().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityName("entryCode", facilityDetailsRequest.getTenor().trim(),"tenor",errors,"TENOR");
			if(!(obj instanceof ActionErrors)){
				facilityDetailsRequest.setTenor(((ICommonCodeEntry)obj).getEntryCode());
			}
			else {
				errors.add("tenor",new ActionMessage("error.invalid"));
			}
			
		}
		
		
		/*if (facilityDetailsRequest.getDpRequired() != null && facilityDetailsRequest.getIsDpCalulated() != null) {
			if ("Y".equalsIgnoreCase(facilityDetailsRequest.getDpRequired().trim())
					&& "Y".equalsIgnoreCase(facilityDetailsRequest.getIsDpCalulated().trim())) {
				if (facilityDetailsRequest.getSecurityList() != null
						&& !facilityDetailsRequest.getSecurityList().isEmpty()
						&& facilityDetailsRequest.getSecurityList().size() > 0) {
					List<SecurityDetailRestRequestDTO> securityList = facilityDetailsRequest.getSecurityList();
					boolean flag = true;
					for (int i = 0; i < securityList.size(); i++) {

						SecurityDetailRestRequestDTO securityDTO = (SecurityDetailRestRequestDTO) securityList.get(i);
						if (!"AB".equalsIgnoreCase(securityDTO.getSecurityType())
								|| (!"AB100".equalsIgnoreCase(securityDTO.getSecuritySubType()))) {
							flag = false;
						} else {
							flag = true;
							break;
						}

					}
					if (!flag) {
						errors.add("SecurityList", new ActionMessage("error.security.required"));
					}

				} else {
					errors.add("SecurityList", new ActionMessage("error.security.required"));
				}

			}}*/
		
		
		

		facilityDetailsRequest.setErrors(errors);
		return facilityDetailsRequest;
		}
	
	public LmtDetailForm  getFormFromRestDTO( FacilityBodyRestRequestDTO dto,ICMSCustomer cust) {

		LmtDetailForm lmtDetailForm = new LmtDetailForm();	
		/*MILimitUIHelper helper = new MILimitUIHelper();
		ILimitProfile profile = new OBLimitProfile();*/
//		OBFacilityNewMaster master = new OBFacilityNewMaster();
//	    ICMSCustomer cust = null;
//		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		String camId = "";

		try {
			    /*ILimitProxy limitProxy = LimitProxyFactory.getProxy();*/
			    if(dto.getCamId()!=null && !dto.getCamId().trim().isEmpty()){
			    	camId = dto.getCamId().trim();
			    }
			    
			    /*
			    profile = limitProxy.getLimitProfile(Long.parseLong(camId));
				ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
				cust = custProxy.getCustomerByCIFSource(profile.getLEReference(),null);*/
	
				/*if(dto.getFacilityMasterId()!=null && !dto.getFacilityMasterId().trim().isEmpty()){
					master =(OBFacilityNewMaster)masterObj.getMaster("actualFacilityNewMaster",new Long(dto.getFacilityMasterId().trim()));
				}*/
				
				if(dto.getFacilityCategoryId()!=null && !dto.getFacilityCategoryId().trim().isEmpty()){
					lmtDetailForm.setFacilityCat(dto.getFacilityCategoryId().trim());
				}
				
				if(dto.getFacilityTypeId()!=null && !dto.getFacilityTypeId().trim().isEmpty()){
					lmtDetailForm.setFacilityType(dto.getFacilityTypeId().trim());
				}
		
				if(dto.getMainFacilityId()!=null && !dto.getMainFacilityId().trim().isEmpty()){
					lmtDetailForm.setMainFacilityId(dto.getMainFacilityId().trim());
				}
				
				if(dto.getFacilityName()!=null && !dto.getFacilityName().trim().isEmpty()){
					lmtDetailForm.setFacilityName(dto.getFacilityName());
				}
				
				System.out.println("9898" +lmtDetailForm.getFacilityName());
				
				lmtDetailForm.setCustomerID(String.valueOf(cust.getCustomerID()));
				lmtDetailForm.setFundedAmount("0");
				lmtDetailForm.setNonFundedAmount("0");
				lmtDetailForm.setMemoExposer("0");
				
				Double totalFacSanctionedAmount = 0d;
				String currentFacSanc = null;
				ILimitProxy proxyLimit = null;
				proxyLimit= LimitProxyFactory.getProxy();
				
				if(dto.getEvent()!=null && ("WS_FAC_EDIT_REST".equalsIgnoreCase(dto.getEvent()) || "WS_FAC_CREATE_REST".equalsIgnoreCase(dto.getEvent()) ) ){
					if(dto.getClimsFacilityId()!=null && !dto.getClimsFacilityId().trim().isEmpty()){
						dto.setClimsFacilityId(dto.getClimsFacilityId().trim());
					}else{
						dto.setClimsFacilityId("");
					}
					DefaultLogger.debug(this, "dto.getClimsFacilityId() value inside FacilityDetailsDTOMapper:::"+dto.getClimsFacilityId()+">>>");
					currentFacSanc = proxyLimit.getTotalAmountByFacType(camId,dto.getFacilityTypeId(),dto.getClimsFacilityId());
				}
				
				if(currentFacSanc!=null){
					totalFacSanctionedAmount = Double.parseDouble(currentFacSanc);
				}
				Double totalPartySanctionedAmount = 0d;
				
				if(dto.getFacilityTypeId()!=null && !dto.getFacilityTypeId().trim().isEmpty()){
					if(dto.getFacilityTypeId().equalsIgnoreCase("FUNDED")){
						String currentPartySanc =cust.getTotalFundedLimit();
						totalPartySanctionedAmount = Double.parseDouble(currentPartySanc);
						Double difference = totalPartySanctionedAmount-totalFacSanctionedAmount;
						lmtDetailForm.setFundedAmount(String.valueOf(difference));
					}else if(dto.getFacilityTypeId().equalsIgnoreCase("NON_FUNDED")){
						String currentPartySanc =cust.getTotalNonFundedLimit();
						 totalPartySanctionedAmount = Double.parseDouble(currentPartySanc);
						 Double difference = totalPartySanctionedAmount-totalFacSanctionedAmount;
						lmtDetailForm.setNonFundedAmount(String.valueOf(difference));
					}else if(dto.getFacilityTypeId().equalsIgnoreCase("MEMO_EXPOSURE")){
						String currentPartySanc =cust.getMemoExposure();
						 totalPartySanctionedAmount = Double.parseDouble(currentPartySanc);
						 Double difference = totalPartySanctionedAmount-totalFacSanctionedAmount;
						lmtDetailForm.setMemoExposer(String.valueOf(difference));
					}
				}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (LimitException e) {
			e.printStackTrace();
		}	catch (RemoteException e) {
			e.printStackTrace();
		}		
		lmtDetailForm.setLimitProfileID(camId);
//		lmtDetailForm.setFacilityName(master.getNewFacilityName());	
//		lmtDetailForm.setFacilityCode(master.getNewFacilityCode());	
//		lmtDetailForm.setFacilityType(master.getNewFacilityType());	
//		lmtDetailForm.setFacilitySystem(master.getNewFacilitySystem());	
//		lmtDetailForm.setLineNo(master.getLineNumber());	
//		lmtDetailForm.setPurpose(master.getPurpose());		
		
		//lmtDetailForm.setGuarantee("No");	
		//lmtDetailForm.setIsReleased("N");
		if(dto.getGuaranteeFlag()!=null && !dto.getGuaranteeFlag().trim().isEmpty()){
			lmtDetailForm.setGuarantee(dto.getGuaranteeFlag().trim());
		}else{
			lmtDetailForm.setGuarantee("No");
		}		
		
		
		if(dto.getIsReleased()!=null && !dto.getIsReleased().trim().isEmpty()){
			lmtDetailForm.setIsReleased(dto.getIsReleased().trim());
		}else{
			lmtDetailForm.setIsReleased("");
		}
		
		if(dto.getBankingArrangement()!=null && !dto.getBankingArrangement().trim().isEmpty()){
			lmtDetailForm.setBankingArrangement(dto.getBankingArrangement().trim());
		}else{
			lmtDetailForm.setBankingArrangement("");
		}
		
		if(dto.getClauseAsPerPolicy()!=null && !dto.getClauseAsPerPolicy().trim().isEmpty()){
			lmtDetailForm.setClauseAsPerPolicy(dto.getClauseAsPerPolicy().trim());
		}else{
			lmtDetailForm.setClauseAsPerPolicy("");
		}
		
		
		if(dto.getSecured()!=null && !dto.getSecured().trim().isEmpty()){
			lmtDetailForm.setIsSecured(dto.getSecured().trim());
		}else{
			lmtDetailForm.setIsSecured("N");
		}
		
		if(dto.getGrade()!=null && !dto.getGrade().trim().isEmpty()){
			lmtDetailForm.setGrade(dto.getGrade().trim());	
		}else{
			lmtDetailForm.setGrade("");	
		}
		
		if(dto.getIsAdhoc()!=null && !dto.getIsAdhoc().trim().isEmpty()){
			lmtDetailForm.setIsAdhoc(dto.getIsAdhoc().trim());
		}else{
			lmtDetailForm.setIsAdhoc("N");
		}
		
		if(dto.getIsAdhocToSanction()!=null && !dto.getIsAdhocToSanction().trim().isEmpty()){
			lmtDetailForm.setIsAdhocToSum(dto.getIsAdhocToSanction().trim());
		}else{
			lmtDetailForm.setIsAdhocToSum("N");
		}
		
		if(dto.getAdhocLimit()!=null && !dto.getAdhocLimit().trim().isEmpty()){
			lmtDetailForm.setAdhocLmtAmount(dto.getAdhocLimit().trim());
		}else{
			lmtDetailForm.setAdhocLmtAmount("");
		}
		
		if(dto.getSyndicateLoan()!=null && !dto.getSyndicateLoan().trim().isEmpty()){
			lmtDetailForm.setSyndicateLoan(dto.getSyndicateLoan().trim());
		}else{
			lmtDetailForm.setSyndicateLoan("N");
		}
		
		if(dto.getDpRequired()!=null && !dto.getDpRequired().trim().isEmpty()){
			lmtDetailForm.setIsDPRequired(dto.getDpRequired().trim());
		}else{
			lmtDetailForm.setIsDPRequired("N");
		}
		
		if(dto.getIsDpCalulated()!=null && !dto.getIsDpCalulated().trim().isEmpty()){
			lmtDetailForm.setIsDP(dto.getIsDpCalulated().trim());
		}else{
			lmtDetailForm.setIsDP("Y");
		}
		
		if(dto.getRelationshipManager()!=null && !dto.getRelationshipManager().trim().isEmpty()){
			lmtDetailForm.setRelationShipManager((dto.getRelationshipManager().trim()));
		}else{
			lmtDetailForm.setRelationShipManager("");
		}
		//lmtDetailForm.setIsAdhoc("N");	
		//lmtDetailForm.setIsAdhocToSum("N");	
//		lmtDetailForm.setFacilityCat(master.getNewFacilityCategory());
		
		if(dto.getCurrency()!=null && !dto.getCurrency().trim().isEmpty()){
			lmtDetailForm.setCurrencyCode(dto.getCurrency().trim());
		}else{
			lmtDetailForm.setCurrencyCode("INR");
		}
		
		if(dto.getSanctionedAmount()!=null && !dto.getSanctionedAmount().trim().isEmpty()){
			lmtDetailForm.setRequiredSecCov(dto.getSanctionedAmount().trim());
		}else{
			lmtDetailForm.setRequiredSecCov("0");
		}
		
		if(dto.getSubLimitFlag()!=null && !dto.getSubLimitFlag().trim().isEmpty()){
			lmtDetailForm.setLimitType(dto.getSubLimitFlag().trim());
		}else{
			lmtDetailForm.setLimitType("");
		}
		
		if(dto.getSubFacilityName()!=null && !dto.getSubFacilityName().trim().isEmpty()){
			lmtDetailForm.setSubFacilityName(dto.getSubFacilityName().trim());
		}else{
			lmtDetailForm.setSubFacilityName("");
		}
		
		if(dto.getPartyName()!=null && !dto.getPartyName().trim().isEmpty()){
			lmtDetailForm.setSubPartyName(dto.getPartyName().trim());
		}else{
			lmtDetailForm.setSubPartyName("");
		}
		
		if(dto.getLiabilityId()!=null && !dto.getLiabilityId().trim().isEmpty()){
			lmtDetailForm.setLiabilityID(dto.getLiabilityId().trim());
		}else{
			lmtDetailForm.setLiabilityID("");
		}
		
		
		if(dto.getClimsFacilityId()!=null && !dto.getClimsFacilityId().trim().isEmpty()){
			lmtDetailForm.setClimsFacilityId(dto.getClimsFacilityId().trim());
		}else{
			lmtDetailForm.setClimsFacilityId("");
		}
		
//		ILimitTrxValue lmtTrxObj = null;
		/*if("Yes".equalsIgnoreCase(dto.getSubLimitFlag())){
			String subFacilityName="";
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
	
			try {
				//retrieve the  main facility Detail
				lmtTrxObj = proxy.searchLimitByLmtId(dto.getMainFacilityId());
				ILimit limit = (ILimit)lmtTrxObj.getLimit();
				
				//Check Facility belongs to same customer
				if(profile.getLimitProfileID()!=0){
					//retrieve detail of all facility belong to that cam
					List subFacNameList = proxy.getSubFacNameList(String.valueOf(profile.getLimitProfileID()));
					
					for (int i = 0; i < subFacNameList.size(); i++) {
						String [] subFacNameArray = (String[])subFacNameList.get(i);
						if(subFacNameArray[1].equals(limit.getFacilityName())){
							subFacilityName=limit.getFacilityName();
							break;
						}
					}
					
				}
				if("".equals(subFacilityName)){
					throw new LimitException("facility Id dosn't belong to CAM");
				}else{
					
					lmtDetailForm.setSubFacilityName(limit.getFacilityName());
				}
			} catch (LimitException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}else{
			lmtDetailForm.setSubFacilityName(null);
		}*/
		
		if(dto.getFacilityNameGuar()!=null && !dto.getFacilityNameGuar().trim().isEmpty()){
			lmtDetailForm.setSubFacilityName(dto.getFacilityNameGuar().trim());
		}else{
			lmtDetailForm.setSubFacilityName("");
		}
		
		if(dto.getSanctionedAmount()!=null && !dto.getSanctionedAmount().trim().isEmpty()){
			lmtDetailForm.setSanctionedLimit(dto.getSanctionedAmount().trim());
		}
		
		if(dto.getReleasableAmount()!=null && !dto.getReleasableAmount().trim().isEmpty()){
			lmtDetailForm.setReleasableAmount(dto.getReleasableAmount().trim());
		}
		
		/*if(dto.getReleased()!=null && !dto.getReleased().trim().isEmpty()){
			lmtDetailForm.setTotalReleasedAmount(dto.getReleased().trim());
		}*/
		if(dto.getTenor()!=null && !dto.getTenor().trim().isEmpty()) {
			lmtDetailForm.setTenorUnit(dto.getTenor().trim());
		}
		if(dto.getTenorVal()!=null && !dto.getTenorVal().trim().isEmpty()){
			lmtDetailForm.setTenor(dto.getTenorVal().trim());
		}
		if(dto.getTenorDesc()!=null && !dto.getTenorDesc().trim().isEmpty()){
			lmtDetailForm.setTenorDesc(dto.getTenorDesc().trim());
		}
		
		
		
		lmtDetailForm.setEvent(dto.getEvent());
		
		
		lmtDetailForm.setIsFromCamonlineReq('Y');

		return lmtDetailForm;
	}

	
	public OBLimit  getActualFromRestDTO( FacilityBodyRestRequestDTO dto ) {

		OBLimit lmtObj = new OBLimit();
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd/MMM/yyyy");

		if(dto.getFacilityCategoryId()!=null && !dto.getFacilityCategoryId().trim().isEmpty()){
			lmtObj.setFacilityCat(dto.getFacilityCategoryId());
		}
		if(dto.getFacilityTypeId()!=null && !dto.getFacilityTypeId().trim().isEmpty()){
			lmtObj.setFacilityType(dto.getFacilityTypeId());		
		}
		
		if(dto.getFacilityName()!=null && !dto.getFacilityName().trim().isEmpty()){
			lmtObj.setFacilityName(dto.getFacilityName());		
		}

		if(dto.getIsReleased()!=null && !dto.getIsReleased().trim().isEmpty()){
			lmtObj.setIsReleased(dto.getIsReleased().trim());
		}else{
			lmtObj.setIsReleased("N");
		}
		
		if(dto.getBankingArrangement()!=null && !dto.getBankingArrangement().trim().isEmpty()){
			lmtObj.setBankingArrangement(dto.getBankingArrangement().trim());
		}else{
			lmtObj.setBankingArrangement("");
		}
		
		if(dto.getClauseAsPerPolicy()!=null && !dto.getClauseAsPerPolicy().trim().isEmpty()){
			lmtObj.setClauseAsPerPolicy(dto.getClauseAsPerPolicy().trim());
		}else{
			lmtObj.setClauseAsPerPolicy("");
		}
		
		if(dto.getRelationshipManager()!=null && !dto.getRelationshipManager().trim().isEmpty()){
			lmtObj.setRelationShipManager(dto.getRelationshipManager().trim());
		}else{
			lmtObj.setRelationShipManager("");
		}
		
		if(dto.getSecured()!=null && !dto.getSecured().trim().isEmpty()){
			lmtObj.setIsSecured(dto.getSecured().trim());
		}else{
			lmtObj.setIsSecured("N");
		}
		
		if(dto.getGrade()!=null && !dto.getGrade().trim().isEmpty()){
			lmtObj.setGrade(dto.getGrade().trim());		
		}else{
			lmtObj.setGrade("");		
		}
		
		if(dto.getSyndicateLoan()!=null && !dto.getSyndicateLoan().trim().isEmpty()){
			lmtObj.setSyndicateLoan(dto.getSyndicateLoan().trim());
		}else{
			lmtObj.setSyndicateLoan("N");
		}
		if(dto.getDpRequired()!=null && !dto.getDpRequired().trim().isEmpty()){
			lmtObj.setIsDPRequired(dto.getDpRequired().trim());		
		}else{
			lmtObj.setIsDPRequired("");		
		}
		
		if(dto.getIsDpCalulated()!=null && !dto.getIsDpCalulated().trim().isEmpty()){
			lmtObj.setIsDP(dto.getIsDpCalulated().trim());		
		}else{
			lmtObj.setIsDP("Y");		
		}
		
		if(dto.getIsAdhoc()!=null && !dto.getIsAdhoc().trim().isEmpty()){
			lmtObj.setIsAdhoc(dto.getIsAdhoc().trim());		
		}else{
			lmtObj.setIsAdhoc("N");		
		}
		if(dto.getIsAdhocToSanction()!=null && !dto.getIsAdhocToSanction().trim().isEmpty()){
			lmtObj.setIsAdhocToSum(dto.getIsAdhocToSanction().trim());		
		}else{
			lmtObj.setIsAdhocToSum("N");		
		}
		if(dto.getAdhocLimit()!=null && !dto.getAdhocLimit().trim().isEmpty()){
			lmtObj.setAdhocLmtAmount(dto.getAdhocLimit().trim());		
		}else{
			lmtObj.setAdhocLmtAmount("");		
		}
		
		if(dto.getCurrency()!=null && !dto.getCurrency().trim().isEmpty()){
			lmtObj.setCurrencyCode(dto.getCurrency().trim());		
		}else{
			lmtObj.setCurrencyCode("INR");		
		}
		

		if(dto.getGuaranteeFlag()!=null && !dto.getGuaranteeFlag().trim().isEmpty()){
			lmtObj.setGuarantee(dto.getGuaranteeFlag().trim());		
		}else{
			lmtObj.setGuarantee("No");		
		}
		
		if(dto.getSubLimitFlag()!=null && !dto.getSubLimitFlag().trim().isEmpty()){
			lmtObj.setLimitType(dto.getSubLimitFlag().trim());
		}else{
			lmtObj.setLimitType("");
		}
		
		
		if(dto.getSubLimitFlag()!=null && !dto.getSubLimitFlag().trim().isEmpty()
				&& "Yes".equalsIgnoreCase(dto.getSubLimitFlag())){
		if(dto.getMainFacilityId()!=null && !dto.getMainFacilityId().isEmpty()){
			lmtObj.setMainFacilityId(dto.getMainFacilityId());
		}else{
			lmtObj.setMainFacilityId("");
		}
		
		if(dto.getSubFacilityName()!=null && !dto.getSubFacilityName().trim().isEmpty()){
			lmtObj.setSubFacilityName(dto.getSubFacilityName().trim());
			}else{
				lmtObj.setSubFacilityName("");
				}
		
		if(dto.getPartyName()!=null && !dto.getPartyName().trim().isEmpty()){
			lmtObj.setSubPartyName(dto.getPartyName().trim());
			}else{
				lmtObj.setSubPartyName("");
				}
		
		if(dto.getLiabilityId()!=null && !dto.getLiabilityId().trim().isEmpty()){
			lmtObj.setLiabilityID(dto.getLiabilityId().trim());
			}else{
				lmtObj.setLiabilityID("");
				}
		}
		if(dto.getSubLimitFlag()!=null && !dto.getSubLimitFlag().trim().isEmpty()
				&& "No".equalsIgnoreCase(dto.getSubLimitFlag())){
			lmtObj.setMainFacilityId("");
			lmtObj.setSubFacilityName("");
			lmtObj.setSubPartyName("");
			lmtObj.setLiabilityID("");
		}
		lmtObj.setLimitStatus("ACTIVE");
		if(dto.getCamId()!=null && !dto.getCamId().trim().isEmpty()){
			lmtObj.setLimitProfileID(Long.parseLong(dto.getCamId().trim()));
		}else{
			lmtObj.setLimitProfileID(0L);
		}
		
		lmtObj.setBookingLocation(new OBBookingLocation());
		
		if(dto.getSanctionedAmount()!=null && !dto.getSanctionedAmount().trim().isEmpty()){
			lmtObj.setRequiredSecurityCoverage(dto.getSanctionedAmount().trim());
		}else{
			lmtObj.setRequiredSecurityCoverage("0");
		}
		
		if(dto.getReleasableAmount()!=null && !dto.getReleasableAmount().trim().isEmpty()){
			lmtObj.setReleasableAmount(dto.getReleasableAmount().trim());
		}else{
			lmtObj.setReleasableAmount("0");
		}
		
		//lmtObj.setIsFromCamonlineReq('Y');
		
		if(dto.getTenor()!=null && !dto.getTenor().trim().isEmpty()) {
			lmtObj.setTenorUnit(dto.getTenor().trim());
		}
		if(dto.getTenorVal()!=null && !dto.getTenorVal().trim().isEmpty()){
			lmtObj.setTenor(Long.parseLong(dto.getTenorVal().trim()));
		}
		if(dto.getTenorDesc()!=null && !dto.getTenorDesc().trim().isEmpty()){
			lmtObj.setTenorDesc(dto.getTenorDesc().trim());
		}
		
		
		//Scod fields
		if(dto.getIsScod() != null && !(dto.getIsScod()).trim().isEmpty())
		{
			if("Y".equalsIgnoreCase(dto.getIsScod()))
			{
				if (dto.getProjectFinance() != null
						&& !dto.getProjectFinance().isEmpty()) {
					lmtObj.setProjectFinance(dto.getProjectFinance().trim());
				}

				if (dto.getProjectLoan() != null
						&& !dto.getProjectLoan().isEmpty()) {
					lmtObj.setProjectLoan(dto.getProjectLoan().trim());
				}

				if ( dto.getInfraFlag() != null
						&& !dto.getInfraFlag().trim().isEmpty()) {
					lmtObj.setInfaProjectFlag(dto.getInfraFlag());
				}
				
				try {
					if (dto.getScod()!=null 
							&& !dto.getScod().trim().isEmpty()) {
						lmtObj.setScodDate(relationshipDateFormat.parse(dto.getScod().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}

				if (dto.getScodRemark() != null
						&& !dto.getScodRemark().trim().isEmpty()) {
					lmtObj.setRemarksSCOD(dto.getScodRemark());
				}

				if (dto.getExeAssetClass() != null
						&& !dto.getExeAssetClass().trim().isEmpty()) {
					lmtObj.setExstAssetClass(dto.getExeAssetClass().trim());
				}
				
				try {
					if (dto.getExeAssetClassDate()!=null 
							&& !dto.getExeAssetClassDate().trim().isEmpty()) {
						lmtObj.setExstAssClassDate(relationshipDateFormat.parse(dto.getExeAssetClassDate().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				//Interim 
				
				if (dto.getLimitReleaseFlg() != null
						&& !dto.getLimitReleaseFlg().isEmpty()) {
					lmtObj.setWhlmreupSCOD(dto.getLimitReleaseFlg().trim());
				}
				
				if (dto.getRepayChngSched() != null
						&& !dto.getRepayChngSched().isEmpty()) {
					lmtObj.setChngInRepaySchedule(dto.getRepayChngSched().trim());
				}
				
				if (dto.getRevAssetClass()!= null
						&& !dto.getRevAssetClass().isEmpty()) {
					lmtObj.setRevisedAssetClass(dto.getRevAssetClass().trim());
				}
				
				try {
					if (dto.getRevAssetClassDt()!=null 
							&& !dto.getRevAssetClassDt().trim().isEmpty()) {
						lmtObj.setRevsdAssClassDate(relationshipDateFormat.parse(dto.getRevAssetClassDt().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				try {
					if (dto.getAcod()!=null 
							&& !dto.getAcod().trim().isEmpty()) {
						lmtObj.setActualCommOpsDate(relationshipDateFormat.parse(dto.getAcod().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				//Level 1
				
				if (dto.getDelayFlagL1() != null
						&& !dto.getDelayFlagL1().isEmpty()) {
					lmtObj.setProjectDelayedFlag(dto.getDelayFlagL1().trim());
				}
				
				if (dto.getInterestFlag() != null
						&& !dto.getInterestFlag().isEmpty()) {
					lmtObj.setPrincipalInterestSchFlag(dto.getInterestFlag().trim());
				}
				
				if (dto.getPriorReqFlag() != null
						&& !dto.getPriorReqFlag().isEmpty()) {
					lmtObj.setRecvPriorOfSCOD(dto.getPriorReqFlag().trim());
				}
				
				if (dto.getDelaylevel() != null
						&& !dto.getDelaylevel().isEmpty()) {
					lmtObj.setLelvelDelaySCOD(dto.getDelaylevel().trim());
				}
				
				if (dto.getDefReasonL1() != null
						&& !dto.getDefReasonL1().isEmpty()) {
					lmtObj.setReasonLevelOneDelay(dto.getDefReasonL1().trim());
				}
				
				if (dto.getRepayChngSchedL1() != null
						&& !dto.getRepayChngSchedL1().isEmpty()) {
					lmtObj.setChngInRepaySchedule(dto.getRepayChngSchedL1().trim());
				}
				
				try {
					if (dto.getEscodL1()!=null 
							&& !dto.getEscodL1().trim().isEmpty()) {
						lmtObj.setEscodLevelOneDelayDate(relationshipDateFormat.parse(dto.getEscodL1().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				if (dto.getOwnershipQ1FlagL1()!= null
						&& !dto.getOwnershipQ1FlagL1().isEmpty()) {
					lmtObj.setPromotersCapRunFlag(dto.getOwnershipQ1FlagL1().trim());
				}
				
				if (dto.getOwnershipQ2FlagL1()!= null
						&& !dto.getOwnershipQ2FlagL1().isEmpty()) {
					lmtObj.setPromotersHoldEquityFlag(dto.getOwnershipQ2FlagL1().trim());
				}
				
				if (dto.getOwnershipQ3FlagL1()!= null
						&& !dto.getOwnershipQ3FlagL1().isEmpty()) {
					lmtObj.setHasProjectViabReAssFlag(dto.getOwnershipQ3FlagL1().trim());
				}
				
				if (dto.getScopeQ1FlagL1()!= null
						&& !dto.getScopeQ1FlagL1().isEmpty()) {
					lmtObj.setChangeInScopeBeforeSCODFlag(dto.getScopeQ1FlagL1().trim());
				}
				
				if (dto.getScopeQ2FlagL1()!= null
						&& !dto.getScopeQ2FlagL1().isEmpty()) {
					lmtObj.setCostOverrunOrg25CostViabilityFlag(dto.getScopeQ2FlagL1().trim());
				}
				
				if (dto.getScopeQ3FlagL1()!= null
						&& !dto.getScopeQ3FlagL1().isEmpty()) {
					lmtObj.setProjectViabReassesedFlag(dto.getScopeQ3FlagL1().trim());
				}
				
				try {
					if (dto.getRevisedEscodL1()!=null 
							&& !dto.getRevisedEscodL1().trim().isEmpty()) {
						lmtObj.setRevsedESCODStpDate(relationshipDateFormat.parse(dto.getRevisedEscodL1().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				if (lmtObj.getExstAssetClass()!= null
						&& !lmtObj.getExstAssetClass().isEmpty()) {
					lmtObj.setExstAssetClassL1(lmtObj.getExstAssetClass().trim());
				}
				
				//try {
					if (lmtObj.getExstAssClassDate()!=null ) {
						lmtObj.setExstAssClassDateL1(lmtObj.getExstAssClassDate());
					}
					/*}catch (ParseException e) {
					e.printStackTrace();
				}*/
				
				if (dto.getRevAssetClassL1()!= null
						&& !dto.getRevAssetClassL1().isEmpty()) {
					lmtObj.setRevisedAssetClassL1(dto.getRevAssetClassL1().trim());
				}
				
				try {
					if (dto.getRevAssetClassDtL1()!=null 
							&& !dto.getRevAssetClassDtL1().trim().isEmpty()) {
						lmtObj.setRevsdAssClassDateL1(relationshipDateFormat.parse(dto.getRevAssetClassDtL1().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				//Level 2
				
				if (dto.getDelayFlagL2() != null
						&& !dto.getDelayFlagL2().isEmpty()) {
					lmtObj.setProjectDelayedFlag(dto.getDelayFlagL2().trim());
				}
				
				if (dto.getDelaylevel() != null
						&& !dto.getDelaylevel().isEmpty()) {
					lmtObj.setLelvelDelaySCOD(dto.getDelaylevel().trim());
				}
				
				if (dto.getDefReasonL2() != null
						&& !dto.getDefReasonL2().isEmpty()) {
					lmtObj.setReasonLevelTwoDelay(dto.getDefReasonL2().trim());
				}
				
				if (dto.getRepayChngSchedL2() != null
						&& !dto.getRepayChngSchedL2().isEmpty()) {
					lmtObj.setChngInRepayScheduleL2(dto.getRepayChngSchedL2().trim());
				}
				
				try {
					if (dto.getEscodL2()!=null 
							&& !dto.getEscodL2().trim().isEmpty()) {
						lmtObj.setEscodLevelSecondDelayDate(relationshipDateFormat.parse(dto.getEscodL2().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				if (dto.getEscodRevisionReasonQ1L2() != null
						&& !dto.getEscodRevisionReasonQ1L2().isEmpty()) {
					lmtObj.setLegalOrArbitrationLevel2Flag(dto.getEscodRevisionReasonQ1L2().trim());
				}
				
				if (dto.getEscodRevisionReasonQ2L2() != null
						&& !dto.getEscodRevisionReasonQ2L2().isEmpty()) {
					lmtObj.setReasonBeyondPromoterLevel2Flag(dto.getEscodRevisionReasonQ2L2().trim());
				}
				
				if (dto.getEscodRevisionReasonQ3L2() != null
						&& !dto.getEscodRevisionReasonQ3L2().isEmpty()) {
					lmtObj.setChngOfOwnPrjFlagInfraLevel2(dto.getEscodRevisionReasonQ3L2().trim());
				}
				
				if (dto.getEscodRevisionReasonQ4L2() != null
						&& !dto.getEscodRevisionReasonQ4L2().isEmpty()) {
					lmtObj.setChngOfProjScopeInfraLevel2(dto.getEscodRevisionReasonQ4L2().trim());
				}
				
				if (dto.getEscodRevisionReasonQ5L2() != null
						&& !dto.getEscodRevisionReasonQ5L2().isEmpty()) {
					lmtObj.setChngOfOwnPrjFlagNonInfraLevel2(dto.getEscodRevisionReasonQ5L2().trim());
				}
				
				if (dto.getEscodRevisionReasonQ6L2() != null
						&& !dto.getEscodRevisionReasonQ6L2().isEmpty()) {
					lmtObj.setChngOfProjScopeNonInfraLevel2(dto.getEscodRevisionReasonQ6L2().trim());
				}
				
				if (dto.getLegalDetailL2() != null
						&& !dto.getLegalDetailL2().isEmpty()) {
					lmtObj.setLeaglOrArbiProceed(dto.getLegalDetailL2().trim());
				}
				
				if (dto.getBeyondControlL2() != null
						&& !dto.getBeyondControlL2().isEmpty()) {
					lmtObj.setDetailsRsnByndPro(dto.getBeyondControlL2().trim());
				}
				
				if (dto.getOwnershipQ1FlagL2()!= null
						&& !dto.getOwnershipQ1FlagL2().isEmpty()) {
					lmtObj.setPromotersCapRunFlagL2(dto.getOwnershipQ1FlagL2().trim());
				}
				
				if (dto.getOwnershipQ2FlagL2()!= null
						&& !dto.getOwnershipQ2FlagL2().isEmpty()) {
					lmtObj.setPromotersHoldEquityFlagL2(dto.getOwnershipQ2FlagL2().trim());
				}
				
				if (dto.getOwnershipQ3FlagL2()!= null
						&& !dto.getOwnershipQ3FlagL2().isEmpty()) {
					lmtObj.setHasProjectViabReAssFlagL2(dto.getOwnershipQ3FlagL2().trim());
				}
				
				if (dto.getScopeQ1FlagL2()!= null
						&& !dto.getScopeQ1FlagL2().isEmpty()) {
					lmtObj.setChangeInScopeBeforeSCODFlagL2(dto.getScopeQ1FlagL2().trim());
				}
				
				if (dto.getScopeQ2FlagL2()!= null
						&& !dto.getScopeQ2FlagL2().isEmpty()) {
					lmtObj.setCostOverrunOrg25CostViabilityFlagL2(dto.getScopeQ2FlagL2().trim());
				}
				
				if (dto.getScopeQ3FlagL2()!= null
						&& !dto.getScopeQ3FlagL2().isEmpty()) {
					lmtObj.setProjectViabReassesedFlagL2(dto.getScopeQ3FlagL2().trim());
				}
				
				try {
					if (dto.getRevisedEscodL2()!=null 
							&& !dto.getRevisedEscodL2().trim().isEmpty()) {
						lmtObj.setRevsedESCODStpDateL2(relationshipDateFormat.parse(dto.getRevisedEscodL2().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				if (lmtObj.getExstAssetClass()!= null
						&& !lmtObj.getExstAssetClass().isEmpty()) {
					lmtObj.setExstAssetClassL2(lmtObj.getExstAssetClass().trim());
				}
				
				//try {
					if (lmtObj.getExstAssClassDate()!=null) {
						lmtObj.setExstAssClassDateL2(lmtObj.getExstAssClassDate());
					}
				/*}catch (ParseException e) {
					e.printStackTrace();
				}*/
				
				if (dto.getRevAssetClassL2()!= null
						&& !dto.getRevAssetClassL2().isEmpty()) {
					lmtObj.setRevisedAssetClass_L2(dto.getRevAssetClassL2().trim());
				}
				
				try {
					if (dto.getRevAssetClassDtL2()!=null 
							&& !dto.getRevAssetClassDtL2().trim().isEmpty()) {
						lmtObj.setRevsdAssClassDate_L2(relationshipDateFormat.parse(dto.getRevAssetClassDtL2().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				//Level 3
				
				if (dto.getDelayFlagL3() != null
						&& !dto.getDelayFlagL3().isEmpty()) {
					lmtObj.setProjectDelayedFlag(dto.getDelayFlagL3().trim());
				}
				
				if (dto.getDelaylevel() != null
						&& !dto.getDelaylevel().isEmpty()) {
					lmtObj.setLelvelDelaySCOD(dto.getDelaylevel().trim());
				}
				
				if (dto.getDefReasonL3() != null
						&& !dto.getDefReasonL3().isEmpty()) {
					lmtObj.setReasonLevelThreeDelay(dto.getDefReasonL3().trim());
				}
				
				if (dto.getRepayChngSchedL3() != null
						&& !dto.getRepayChngSchedL3().isEmpty()) {
					lmtObj.setChngInRepayScheduleL3(dto.getRepayChngSchedL3().trim());
				}
				
				try {
					if (dto.getEscodL3()!=null 
							&& !dto.getEscodL3().trim().isEmpty()) {
						lmtObj.setEscodLevelThreeDelayDate(relationshipDateFormat.parse(dto.getEscodL3().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				if (dto.getEscodRevisionReasonQ1L3() != null
						&& !dto.getEscodRevisionReasonQ1L3().isEmpty()) {
					lmtObj.setLegalOrArbitrationLevel3Flag(dto.getEscodRevisionReasonQ1L3().trim());
				}
				
				if (dto.getEscodRevisionReasonQ2L3() != null
						&& !dto.getEscodRevisionReasonQ2L3().isEmpty()) {
					lmtObj.setReasonBeyondPromoterLevel3Flag(dto.getEscodRevisionReasonQ2L3().trim());
				}
				
				if (dto.getEscodRevisionReasonQ3L3() != null
						&& !dto.getEscodRevisionReasonQ3L3().isEmpty()) {
					lmtObj.setChngOfOwnPrjFlagInfraLevel3(dto.getEscodRevisionReasonQ3L3().trim());
				}
				
				if (dto.getEscodRevisionReasonQ4L3() != null
						&& !dto.getEscodRevisionReasonQ4L3().isEmpty()) {
					lmtObj.setChngOfProjScopeInfraLevel3(dto.getEscodRevisionReasonQ4L3().trim());
				}
				
				if (dto.getEscodRevisionReasonQ5L3() != null
						&& !dto.getEscodRevisionReasonQ5L3().isEmpty()) {
					lmtObj.setChngOfOwnPrjFlagNonInfraLevel3(dto.getEscodRevisionReasonQ5L3().trim());
				}
				
				if (dto.getEscodRevisionReasonQ6L3() != null
						&& !dto.getEscodRevisionReasonQ6L3().isEmpty()) {
					lmtObj.setChngOfProjScopeNonInfraLevel3(dto.getEscodRevisionReasonQ6L3().trim());
				}
				
				if (dto.getLegalDetailL3() != null
						&& !dto.getLegalDetailL3().isEmpty()) {
					lmtObj.setLeaglOrArbiProceedLevel3(dto.getLegalDetailL3().trim());
				}
				
				if (dto.getBeyondControlL3() != null
						&& !dto.getBeyondControlL3().isEmpty()) {
					lmtObj.setDetailsRsnByndProLevel3(dto.getBeyondControlL3().trim());
				}
				
				if (dto.getOwnershipQ1FlagL3()!= null
						&& !dto.getOwnershipQ1FlagL3().isEmpty()) {
					lmtObj.setPromotersCapRunFlagL3(dto.getOwnershipQ1FlagL3().trim());
				}
				
				if (dto.getOwnershipQ2FlagL3()!= null
						&& !dto.getOwnershipQ2FlagL3().isEmpty()) {
					lmtObj.setPromotersHoldEquityFlagL3(dto.getOwnershipQ2FlagL3().trim());
				}
				
				if (dto.getOwnershipQ3FlagL3()!= null
						&& !dto.getOwnershipQ3FlagL3().isEmpty()) {
					lmtObj.setHasProjectViabReAssFlagL3(dto.getOwnershipQ3FlagL3().trim());
				}
				
				if (dto.getScopeQ1FlagL3()!= null
						&& !dto.getScopeQ1FlagL3().isEmpty()) {
					lmtObj.setChangeInScopeBeforeSCODFlagL3(dto.getScopeQ1FlagL3().trim());
				}
				
				if (dto.getScopeQ2FlagL3()!= null
						&& !dto.getScopeQ2FlagL3().isEmpty()) {
					lmtObj.setCostOverrunOrg25CostViabilityFlagL3(dto.getScopeQ2FlagL3().trim());
				}
				
				if (dto.getScopeQ3FlagL3()!= null
						&& !dto.getScopeQ3FlagL3().isEmpty()) {
					lmtObj.setProjectViabReassesedFlagL3(dto.getScopeQ3FlagL3().trim());
				}
				
				try {
					if (dto.getRevisedEscodL3()!=null 
							&& !dto.getRevisedEscodL3().trim().isEmpty()) {
						lmtObj.setRevsedESCODStpDateL3(relationshipDateFormat.parse(dto.getRevisedEscodL3().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				if (lmtObj.getExstAssetClass()!= null
						&& !lmtObj.getExstAssetClass().isEmpty()) {
					lmtObj.setExstAssetClassL3(lmtObj.getExstAssetClass().trim());
				}
				
				/*try {*/
					if (lmtObj.getExstAssClassDate()!=null) {
						lmtObj.setExstAssClassDateL3(lmtObj.getExstAssClassDate());
					}
				/*}catch (ParseException e) {
					e.printStackTrace();
				}*/
				
				if (dto.getRevAssetClassL3()!= null
						&& !dto.getRevAssetClassL3().isEmpty()) {
					lmtObj.setRevisedAssetClass_L3(dto.getRevAssetClassL3().trim());
				}
				
				try {
					if (dto.getRevAssetClassDtL3()!=null 
							&& !dto.getRevAssetClassDtL3().trim().isEmpty()) {
						lmtObj.setRevsdAssClassDate_L3(relationshipDateFormat.parse(dto.getRevAssetClassDtL3().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
		}
			

		return lmtObj;
	}
	
	public FacilitySCODDetailRestRequestDTO getSCODRestRequestDTOWithActualValues(FacilitySCODDetailRestRequestDTO requestDTO) { //for validation

		FacilitySCODDetailRestRequestDTO facilityDetailRequestDTO =new FacilitySCODDetailRestRequestDTO();
		MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();
		DefaultLogger.debug(this,
				"Inside getSCODRequestDTOWithActualValues of FacilityDetailsDTOMapper================:::::");
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		
		ILimitTrxValue lmtTrxObj = null;
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
		ILimitProfile profile = new OBLimitProfile();
		String errorCode = null;
		
		if (requestDTO.getCamId() != null && !requestDTO.getCamId().trim().isEmpty()) {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			try {
				profile = limitProxy.getLimitProfile(Long.parseLong(requestDTO.getCamId().trim()));
				if (!(profile != null && !"".equals(profile))) {
					errors.add("camId", new ActionMessage("error.camId.invalid"));
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				errors.add("camId", new ActionMessage("error.camId.invalid"));
			} catch (LimitException e) {
				e.printStackTrace();
				errors.add("camId", new ActionMessage("error.camId.invalid"));
			}
		} else {
			errors.add("camId", new ActionMessage("error.camId.mandatory"));
		}
		
		/*try {
			if(null != requestDTO.getEvent()  && !requestDTO.getEvent().trim().isEmpty() && 
					"WS_FAC_EDIT_SCOD_REST".equalsIgnoreCase(requestDTO.getEvent()))
			{
				if (requestDTO.getClimsFacilityId() != null
						&& !requestDTO.getClimsFacilityId().trim().isEmpty()) {
					lmtTrxObj = proxy.searchLimitByLmtId(requestDTO.getClimsFacilityId().trim());
				} else {
					errors.add("climsFacilityId", new ActionMessage("error.climsFacilityId.invalid"));
				}
			}
		} catch (Exception e) {
			errors.add("climsFacilityId", new ActionMessage("error.climsFacilityId.invalid"));
		}*/
		
		/*
		if(requestDTO.getClimsFacilityId() != null
				&& !requestDTO.getClimsFacilityId().trim().isEmpty() && requestDTO.getCamId() != null && !requestDTO.getCamId().trim().isEmpty()) {
			LimitDAO lmtDao = new LimitDAO();
			String countNum = lmtDao.getFacilityIdAndCamIdCombineCount(requestDTO.getClimsFacilityId().trim(),requestDTO.getCamId().trim());
			if("0".equals(countNum)) {
				errors.add("climsFacilityId", new ActionMessage("error.climsFacilityIdCamId.invalid"));
			}
		}*/
		
		/*if (requestDTO.getCamType() != null && !requestDTO.getCamType().trim().isEmpty()) {
			facilityDetailRequestDTO.setCamType(requestDTO.getCamType().trim());*/
			
			/*if ("Initial".equals(requestDTO.getCamType()) || "Interim".equals(requestDTO.getCamType()) 
					|| "Annual".equals(requestDTO.getCamType())) {*/
				//Initial call i.e. for new facility
				DefaultLogger.debug(this,"======Initial CAM call========");
				if (requestDTO.getProjectFinance() != null && !requestDTO.getProjectFinance().trim().isEmpty()) {
					if (!("Y".equals(requestDTO.getProjectFinance().trim())
							|| "N".equals(requestDTO.getProjectFinance().trim()))) {
						errors.add("projectFinance", new ActionMessage("error.projectFinance.invalid"));
					} else {
						facilityDetailRequestDTO.setProjectFinance(requestDTO.getProjectFinance());
					}
				} else {
					errors.add("projectFinance", new ActionMessage("error.projectFinance.mandatory"));
				}

				if (requestDTO.getProjectLoan() != null && !requestDTO.getProjectLoan().trim().isEmpty()) {
					if (!("Y".equals(requestDTO.getProjectLoan().trim())
							|| "N".equals(requestDTO.getProjectLoan().trim()))) {
						errors.add("projectLoan", new ActionMessage("error.projectLoan.invalid"));
					} else {
						facilityDetailRequestDTO.setProjectLoan(requestDTO.getProjectLoan());
					}
				} else {
					errors.add("projectLoan", new ActionMessage("error.projectLoan.mandatory"));
				}

				if ("Y".equals(requestDTO.getProjectFinance()) || "Y".equals(requestDTO.getProjectLoan())) {
					if (requestDTO.getInfraFlag() != null && !requestDTO.getInfraFlag().trim().isEmpty()) {
						if (!("Y".equals(requestDTO.getInfraFlag().trim())
								|| "N".equals(requestDTO.getInfraFlag().trim()))) {
							errors.add("infraFlag", new ActionMessage("error.infraFlag.invalid"));
						} else {
							facilityDetailRequestDTO.setInfraFlag(requestDTO.getInfraFlag());
						}
					} else {
						errors.add("infraFlag", new ActionMessage("error.infraFlag.mandatory"));
					}
	
						if (requestDTO.getScod() != null && !requestDTO.getScod().toString().trim().isEmpty()) {
						try {
							Calendar calendar = Calendar.getInstance();
							 calendar.add(Calendar.DATE, -1);
							 
							if (! (isValidDate((requestDTO.getScod().toString().trim())))) {
								errors.add("scod", new ActionMessage("error.scod.invalid.format"));
							 }	else {
								 String d1 = (String)requestDTO.getScod();
									String[] d2 = d1.split("/");
									if(d2.length == 3) {
										if(d2[2].length() != 4) {
											errors.add("scod", new ActionMessage("error.scod.invalid.format"));
										}else {
											if ((requestDTO.getScod().length() > 0)
													&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()).before(calendar.getTime())) {
												errors.add("scod", new ActionMessage("error.date.before.notallowed"));
											}else {
											relationshipDateFormat.parse(requestDTO.getScod().toString().trim());
											facilityDetailRequestDTO.setScod(requestDTO.getScod().trim());
											}
									}
									}
						 }
						} catch (ParseException e) {
							errors.add("scod", new ActionMessage("error.scod.invalid.format"));
						}
					} else {
						errors.add("scod", new ActionMessage("error.scod.mandatory"));
					}

					if (requestDTO.getScodRemark() != null && !requestDTO.getScodRemark().trim().isEmpty()) {
						if (!(errorCode = Validator.checkString(requestDTO.getScodRemark(), false, 1, 200)).equals(Validator.ERROR_NONE)) {
							errors.add("scodRemark", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "200"));}
							else {
						facilityDetailRequestDTO.setScodRemark(requestDTO.getScodRemark());}
					} else {
						errors.add("scodRemark", new ActionMessage("error.scodRemark.mandatory"));
					}
				}else {
					if ("N".equals(requestDTO.getProjectFinance()) && "N".equals(requestDTO.getProjectLoan())) {
						errors.add("projectLoan", new ActionMessage("error.projectLoanFinance.mandatory"));
						errors.add("projectFinance", new ActionMessage("error.projectLoanFinance.mandatory"));
					}
				}
				
				if(requestDTO.getExeAssetClass()!=null && !requestDTO.getExeAssetClass().trim().isEmpty()){
					Object scodObj = masterObj.getObjectByEntityName("entryCode", requestDTO.getExeAssetClass().trim(),
							"exeAssetClass",errors,"EXE_ASSET_CLASS_ID");
					System.out.println("Called exeAssetClass...");
					if(!(scodObj instanceof ActionErrors)){
						System.out.println("Is not instanceof ActionErrors...");
						facilityDetailRequestDTO.setExeAssetClass(((ICommonCodeEntry)scodObj).getEntryCode());
						//to set exeAssetClassDate
						if(!requestDTO.getExeAssetClass().equals("1")) {
							if (requestDTO.getExeAssetClassDate() != null && !requestDTO.getExeAssetClassDate().toString().trim().isEmpty()) {
								try {
									//if (! (errorCode = Validator.checkDate(requestDTO.getExeAssetClassDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
									if (! (isValidDate((requestDTO.getExeAssetClassDate().toString().trim())))) {
										errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
									 }	else {
										 String d1 = (String)requestDTO.getExeAssetClassDate();
											String[] d2 = d1.split("/");
											if(d2.length == 3) {
												if(d2[2].length() != 4) {
													errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
												}else {
													relationshipDateFormat.parse(requestDTO.getExeAssetClassDate().toString().trim());
													facilityDetailRequestDTO.setExeAssetClassDate(requestDTO.getExeAssetClassDate().trim());
													}
											}
									}
								} catch (ParseException e) {
									errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
								}
							} else {
								errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.mandatory"));
							}
						} else {
							
							if (requestDTO.getExeAssetClassDate() != null && !requestDTO.getExeAssetClassDate().toString().trim().isEmpty()) {
							try {
								//if (! (errorCode = Validator.checkDate(requestDTO.getExeAssetClassDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
								if (! (isValidDate((requestDTO.getExeAssetClassDate().toString().trim())))) {
									errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
								 }	else {
									 String d1 = (String)requestDTO.getExeAssetClassDate();
										String[] d2 = d1.split("/");
										if(d2.length == 3) {
											if(d2[2].length() != 4) {
												errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
											}else {
												relationshipDateFormat.parse(requestDTO.getExeAssetClassDate().toString().trim());
												facilityDetailRequestDTO.setExeAssetClassDate(requestDTO.getExeAssetClassDate().trim());
												}
										}
								}
							} catch (ParseException e) {
								errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
							}
							}else {
								facilityDetailRequestDTO.setExeAssetClassDate("");
							}
							
						}
					} else {
						System.out.println("Error occured on exeAssetClass");
						errors.add("exeAssetClass",new ActionMessage("error.exeAssetClass.invalid"));
					}
				}else{
					errors.add("exeAssetClass", new ActionMessage("error.exeAssetClass.mandatory"));
				}
				
				
				/*facilityDetailRequestDTO.setExeAssetClass("1");
				facilityDetailRequestDTO.setExeAssetClassDate("");*/
				
				if (requestDTO.getEvent()!=null && requestDTO.getEvent().equalsIgnoreCase("WS_FAC_EDIT_SCOD_REST"))//added to validate remaining fields only in case of updatefacility
				{
				//Interim call i.e. for update facility
				DefaultLogger.debug(this,"======Interim CAM call========");
				
				//if(null!=lmtTrxObj && null!=lmtTrxObj.getLimit()) {
					//INTERIM  CAM (CHANGE IN SCOD)
					//ILimit obj=lmtTrxObj.getLimit();
					//project finance,project loan,infra/nonInfra flag, scod, scod remarks are mendatory
					
					Calendar calendar = Calendar.getInstance();
					 calendar.add(Calendar.DATE, -1);
					if (requestDTO.getLimitReleaseFlg() != null && !requestDTO.getLimitReleaseFlg().trim().isEmpty()) {
						DefaultLogger.debug(this,"======getLimitReleaseFlg not null========");
							 if (!("Y".equals(requestDTO.getLimitReleaseFlg().trim()) || "N".equals(requestDTO.getLimitReleaseFlg().trim()))) {
									errors.add("limitReleaseFlg", new ActionMessage("error.limitReleaseFlg.invalid"));
							 } else {
									facilityDetailRequestDTO.setLimitReleaseFlg(requestDTO.getLimitReleaseFlg());
									//case 1: limit not yet released
									if("N".equals(requestDTO.getLimitReleaseFlg().trim())) {
										//to set updated scod
										DefaultLogger.debug(this,"======limit not yet released========");
										/*if (requestDTO.getScod() != null && !requestDTO.getScod().toString().trim().isEmpty()) {
											
											errors.add("scod", new ActionMessage("error.scod.not.allowed"));
										}*/
											
											/*try {
												
												if (! (errorCode = Validator.checkDate(requestDTO.getScod(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
													errors.add("scod", new ActionMessage("error.scod.invalid.format"));
												 }	else {
													 String d1 = (String)requestDTO.getScod();
														String[] d2 = d1.split("/");
														if(d2.length == 3) {
															if(d2[2].length() != 4) {
																errors.add("scod", new ActionMessage("error.scod.invalid.format"));
															}else {
																relationshipDateFormat.parse(requestDTO.getScod().toString().trim());
//																if (DateUtil.convertDate(requestDTO.getScod()).after(obj.getScodDate())) {
																if (DateUtil.convertDate(requestDTO.getScod()).after(calendar.getTime())) {
																	facilityDetailRequestDTO.setScod(requestDTO.getScod().trim());
																}else {
																	errors.add("scod", new ActionMessage("error.scod.future"));
																}
																}
														}
												 }
											} catch (ParseException e) {
												errors.add("scod", new ActionMessage("error.scod.invalid.format"));
											}
										} 
										else {
											errors.add("scod", new ActionMessage("error.scod.mandatory"));//error.scod.not.allowed
										}*/
											
										//to set updated scod remark
										if (requestDTO.getScodRemark() != null && !requestDTO.getScodRemark().trim().isEmpty()) {
											facilityDetailRequestDTO.setScodRemark(requestDTO.getScodRemark());
										} else {
											errors.add("scodRemark", new ActionMessage("error.scodRemark.mandatory"));
										}
										//to set repayment schedule
										facilityDetailRequestDTO.setRepayChngSched(requestDTO.getRepayChngSched());
										if (requestDTO.getRepayChngSched() != null && !requestDTO.getRepayChngSched().trim().isEmpty()) {
											if (!("Y".equals(requestDTO.getRepayChngSched().trim()) || "N".equals(requestDTO.getRepayChngSched().trim()))) {
												errors.add("repayChngSched", new ActionMessage("error.repayChngSched.invalid"));
											} else {
												facilityDetailRequestDTO.setRepayChngSched(requestDTO.getRepayChngSched());
											}
										} /*else {
											errors.add("repayChngSched", new ActionMessage("error.repayChngSched.mandatory"));
										}*/
										//to set exeAssetClass
										
										/*if(requestDTO.getExeAssetClass()!=null && !requestDTO.getExeAssetClass().trim().isEmpty()){
											Object scodObj = masterObj.getObjectByEntityName("entryCode", requestDTO.getExeAssetClass().trim(),
													"exeAssetClass",errors,"EXE_ASSET_CLASS_ID");
											if(!(scodObj instanceof ActionErrors)){
												facilityDetailRequestDTO.setExeAssetClass(((ICommonCodeEntry)scodObj).getEntryCode());
												//to set exeAssetClassDate
												if(!requestDTO.getExeAssetClass().equals("1")) {
													if (requestDTO.getExeAssetClassDate() != null && !requestDTO.getExeAssetClassDate().toString().trim().isEmpty()) {
														try {
															if (! (errorCode = Validator.checkDate(requestDTO.getExeAssetClassDate(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
															 }	else {
																 String d1 = (String)requestDTO.getExeAssetClassDate();
																	String[] d2 = d1.split("/");
																	if(d2.length == 3) {
																		if(d2[2].length() != 4) {
																			errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
																		}else {
																			relationshipDateFormat.parse(requestDTO.getExeAssetClassDate().toString().trim());
																			facilityDetailRequestDTO.setExeAssetClassDate(requestDTO.getExeAssetClassDate().trim());
																			}
																	}
															}
//														
														} catch (ParseException e) {
															errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.invalid.format"));
														}
													} else {
														errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.mandatory"));
													}
												} else {
													facilityDetailRequestDTO.setExeAssetClassDate("");
												}
											} else {
												errors.add("exeAssetClass",new ActionMessage("error.exeAssetClass.invalid"));
											}
										}else{
											errors.add("exeAssetClass",new ActionMessage("error.exeAssetClass.mandatory"));
										}*/
										
										//to set revAssetClass
										if(requestDTO.getRevAssetClass()!=null && !requestDTO.getRevAssetClass().trim().isEmpty()){
											Object scodObj = masterObj.getObjectByEntityName("entryCode", requestDTO.getRevAssetClass().trim(),
													"revAssetClass",errors,"REV_ASSET_CLASS_ID");
											if(!(scodObj instanceof ActionErrors)){
												facilityDetailRequestDTO.setRevAssetClass(((ICommonCodeEntry)scodObj).getEntryCode());
												//to set revAssetClassDt
												if(!requestDTO.getRevAssetClass().equals("1")) {
													if (requestDTO.getRevAssetClassDt() != null && !requestDTO.getRevAssetClassDt().toString().trim().isEmpty()) {
														try {
															//if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDt(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																if (! (isValidDate((requestDTO.getRevAssetClassDt().toString().trim())))) {
																errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
															 }	else {
																 String d1 = (String)requestDTO.getRevAssetClassDt();
																	String[] d2 = d1.split("/");
																	if(d2.length == 3) {
																		if(d2[2].length() != 4) {
																			errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																		}else {
																			relationshipDateFormat.parse(requestDTO.getRevAssetClassDt().toString().trim());
																			facilityDetailRequestDTO.setRevAssetClassDt(requestDTO.getRevAssetClassDt().trim());
																			}
																	}
															 }
														} catch (ParseException e) {
															errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
														}
													} else {
														errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.mandatory"));
													}
												} else {
													
													if (requestDTO.getRevAssetClassDt() != null && !requestDTO.getRevAssetClassDt().toString().trim().isEmpty()) {
													try {
														//if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDt(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
														if (! (isValidDate((requestDTO.getRevAssetClassDt().toString().trim())))) {	
															errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
														 }	else {
															 String d1 = (String)requestDTO.getRevAssetClassDt();
																String[] d2 = d1.split("/");
																if(d2.length == 3) {
																	if(d2[2].length() != 4) {
																		errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																	}else {
																		relationshipDateFormat.parse(requestDTO.getRevAssetClassDt().toString().trim());
																		facilityDetailRequestDTO.setRevAssetClassDt(requestDTO.getRevAssetClassDt().trim());
																		}
																}
														 }
													} catch (ParseException e) {
														errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
													}
													}else {
														facilityDetailRequestDTO.setRevAssetClassDt("");
													}
													
												}
											} else {
												errors.add("revAssetClass",new ActionMessage("error.revAssetClass.invalid"));
											}
										}else{
											errors.add("revAssetClass",new ActionMessage("error.revAssetClass.mandatory"));
										}
									}
									//case 2: limit released
									else if("Y".equals(requestDTO.getLimitReleaseFlg().trim())) {
										
										DefaultLogger.debug(this,"======limit released========");
										//As per bank request, removed below validation for interim case.
										/*if (requestDTO.getScod() != null && !requestDTO.getScod().toString().trim().isEmpty()) {
											errors.add("scod", new ActionMessage("error.scod.not.allowed"));
										}
										if (requestDTO.getScodRemark() != null && !requestDTO.getScodRemark().trim().isEmpty()) {
											errors.add("scodRemark", new ActionMessage("error.scodRemark.not.allowed"));
										}
										
										if (requestDTO.getExeAssetClass() != null && !requestDTO.getExeAssetClass().trim().isEmpty()) {
											errors.add("exeAssetClass", new ActionMessage("error.exeAssetClass.not.allowed"));
										}
										
										if (requestDTO.getExeAssetClassDate() != null && !requestDTO.getExeAssetClassDate().trim().isEmpty()) {
											errors.add("exeAssetClassDate", new ActionMessage("error.exeAssetClassDate.not.allowed"));
										}*/
										//to set acod
										if (requestDTO.getAcod() != null && !requestDTO.getAcod().toString().trim().isEmpty()) {
											try {
												//if (! (errorCode = Validator.checkDate(requestDTO.getAcod(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
												if (! (isValidDate((requestDTO.getAcod().toString().trim())))) {
													errors.add("acod", new ActionMessage("error.acod.invalid.format"));
												 }	else {
													 String d1 = (String)requestDTO.getAcod();
														String[] d2 = d1.split("/");
														if(d2.length == 3) {
															if(d2[2].length() != 4) {
																errors.add("acod", new ActionMessage("error.acod.invalid.format"));
															}else {
																relationshipDateFormat.parse(requestDTO.getAcod().toString().trim());
																facilityDetailRequestDTO.setAcod(requestDTO.getAcod().trim());
																}
														}
												 }
											} catch (ParseException e) {
												errors.add("acod", new ActionMessage("error.acod.invalid.format"));
											}
											
											if(requestDTO.getDelaylevel()!=null && !requestDTO.getDelaylevel().trim().isEmpty()){
												errors.add("delaylevel",new ActionMessage("error.delaylevel.not.allowed"));
											}else {
												if(requestDTO.getRevAssetClass()!=null && !requestDTO.getRevAssetClass().trim().isEmpty()){
													Object scodObj = masterObj.getObjectByEntityName("entryCode", requestDTO.getRevAssetClass().trim(),
															"revAssetClass",errors,"REV_ASSET_CLASS_ID");
													if(!(scodObj instanceof ActionErrors)){
														facilityDetailRequestDTO.setRevAssetClass(((ICommonCodeEntry)scodObj).getEntryCode());
														//to set revAssetClassDt
														if(!requestDTO.getRevAssetClass().equals("1")) {
															if (requestDTO.getRevAssetClassDt() != null && !requestDTO.getRevAssetClassDt().toString().trim().isEmpty()) {
																try {
																	//if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDt(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																	if (! (isValidDate((requestDTO.getRevAssetClassDt().toString().trim())))) {
																		errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																	 }	else {
																		 String d1 = (String)requestDTO.getRevAssetClassDt();
																			String[] d2 = d1.split("/");
																			if(d2.length == 3) {
																				if(d2[2].length() != 4) {
																					errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																				}else {
																					relationshipDateFormat.parse(requestDTO.getRevAssetClassDt().toString().trim());
																					facilityDetailRequestDTO.setRevAssetClassDt(requestDTO.getRevAssetClassDt().trim());
																					}
																			}
																	 }
																} catch (ParseException e) {
																	errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																}
															} else {
																errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.mandatory"));
															}
														} else {
															
															if(requestDTO.getRevAssetClassDt() != null && !requestDTO.getRevAssetClassDt().toString().trim().isEmpty()) {
															
																try {
																	//if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDt(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																	if (! (isValidDate((requestDTO.getRevAssetClassDt().toString().trim())))) {
																		errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																	 }	else {
																		 String d1 = (String)requestDTO.getRevAssetClassDt();
																			String[] d2 = d1.split("/");
																			if(d2.length == 3) {
																				if(d2[2].length() != 4) {
																					errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																				}else {
																					relationshipDateFormat.parse(requestDTO.getRevAssetClassDt().toString().trim());
																					facilityDetailRequestDTO.setRevAssetClassDt(requestDTO.getRevAssetClassDt().trim());
																					}
																			}
																	 }
																} catch (ParseException e) {
																	errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																}
															}else {
																facilityDetailRequestDTO.setRevAssetClassDt(null);
															}
														}
													} else {
														errors.add("revAssetClass",new ActionMessage("error.revAssetClass.invalid"));
													}
												}else{
													errors.add("revAssetClass",new ActionMessage("error.revAssetClass.mandatory"));
												}
											}
											
										} 
										//to set level details
										else {
											
											/*if(requestDTO.getDelaylevel() == null || "".equals(requestDTO.getDelaylevel())) {
												if("N".equals(requestDTO.getDelayFlagL1())) {
													errors.add("acod", new ActionMessage("error.acod.mandatory"));
												}
											}else if("2".equals(requestDTO.getDelaylevel())) {
												if("N".equals(requestDTO.getDelayFlagL2())) {
													errors.add("acod", new ActionMessage("error.acod.mandatory"));
												}
											}else if("3".equals(requestDTO.getDelaylevel())) {
												if("N".equals(requestDTO.getDelayFlagL3())) {
													errors.add("acod", new ActionMessage("error.acod.mandatory"));
												}
											}*/
											
//											if("N".equals(requestDTO.getDelayFlagL1())) {
//												errors.add("acod", new ActionMessage("error.acod.mandatory"));
//											}
											
																
											/*if("N".equals(requestDTO.getDelayFlagL1())||"N".equals(requestDTO.getDelayFlagL2())||"N".equals(requestDTO.getDelayFlagL3())) {
												requestDTO.setDelaylevel("");
											}*/
											
											if(requestDTO.getDelaylevel()!=null && !requestDTO.getDelaylevel().trim().isEmpty()){
												Object scodObj = masterObj.getObjectByEntityName("entryCode", requestDTO.getDelaylevel().trim(),
														"delaylevel",errors,"DELAY_LEVEL_ID");
												if(!(scodObj instanceof ActionErrors)){
													facilityDetailRequestDTO.setDelaylevel(((ICommonCodeEntry)scodObj).getEntryCode());
													
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<1st DELAY CAM>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
													if("1".equals(requestDTO.getDelaylevel())) {
														DefaultLogger.debug(this,"======1st DELAY CAM========");
														//to set delayFlagL1
														if (requestDTO.getDelayFlagL1() != null && !requestDTO.getDelayFlagL1().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getDelayFlagL1().trim()) || "N".equals(requestDTO.getDelayFlagL1().trim()))) {
																errors.add("delayFlagL1", new ActionMessage("error.delayFlagL1.invalid"));
															} else {
																if("Y".equals(requestDTO.getDelayFlagL1().trim())) {
																facilityDetailRequestDTO.setDelayFlagL1(requestDTO.getDelayFlagL1());
																}else {
																	errors.add("delayFlagL1", new ActionMessage("error.delayFlagL1.notvalid"));
																}
															}
														} else {
															errors.add("delayFlagL1", new ActionMessage("error.delayFlagL1.mandatory"));
}

														//to set interestFlag
														if (requestDTO.getInterestFlag() != null && !requestDTO.getInterestFlag().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getInterestFlag().trim()) || "N".equals(requestDTO.getInterestFlag().trim()))) {
																errors.add("interestFlag", new ActionMessage("error.interestFlag.invalid"));
															} else {
																facilityDetailRequestDTO.setInterestFlag(requestDTO.getInterestFlag());
															}
														} else {
															errors.add("interestFlag", new ActionMessage("error.interestFlag.mandatory"));
														}
														//to set priorReqFlag
														if (requestDTO.getPriorReqFlag() != null && !requestDTO.getPriorReqFlag().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getPriorReqFlag().trim()) || "N".equals(requestDTO.getPriorReqFlag().trim()))) {
																errors.add("priorReqFlag", new ActionMessage("error.priorReqFlag.invalid"));
															} else {
																facilityDetailRequestDTO.setPriorReqFlag(requestDTO.getPriorReqFlag());
															}
														} else {
															errors.add("priorReqFlag", new ActionMessage("error.priorReqFlag.mandatory"));
														}
														//to set defReasonL1
														if (requestDTO.getDefReasonL1() != null && !requestDTO.getDefReasonL1().trim().isEmpty()) {
															if (!(errorCode = Validator.checkString(requestDTO.getDefReasonL1(), false, 1, 200)).equals(Validator.ERROR_NONE)) {
																errors.add("defReasonL1", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "200"));
															} else {
															facilityDetailRequestDTO.setDefReasonL1(requestDTO.getDefReasonL1());
															}
														} else {
															errors.add("defReasonL1", new ActionMessage("error.defReasonL1.mandatory"));
														}
														//to set repayChngSchedL1
														if (requestDTO.getRepayChngSchedL1() != null && !requestDTO.getRepayChngSchedL1().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getRepayChngSchedL1().trim()) || "N".equals(requestDTO.getRepayChngSchedL1().trim()))) {
																errors.add("repayChngSchedL1", new ActionMessage("error.repayChngSchedL1.invalid"));
															} else {
																facilityDetailRequestDTO.setRepayChngSchedL1(requestDTO.getRepayChngSchedL1());
															}
														} else {
															errors.add("repayChngSchedL1", new ActionMessage("error.repayChngSchedL1.mandatory"));
														}
														//to set escodL1
														if (requestDTO.getEscodL1() != null && !requestDTO.getEscodL1().toString().trim().isEmpty()) {
															try {
																if (! (errorCode = Validator.checkDate(requestDTO.getEscodL1(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																	errors.add("escodL1", new ActionMessage("error.escodL1.invalid.format"));
																 }	else {
																	 String d1 = (String)requestDTO.getEscodL1();
																		String[] d2 = d1.split("/");
																		if(d2.length == 3) {
																			if(d2[2].length() != 4) {
																				errors.add("escodL1", new ActionMessage("error.escodL1.invalid.format"));
																			}if ((requestDTO.getEscodL1().length() > 0)
																					&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL1()).before(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod())))
																			 {
																				 errors.add("escodL1", new ActionMessage("error.date.before.notallowed.scodDate"));
																			 }
																			else {
																				relationshipDateFormat.parse(requestDTO.getEscodL1().toString().trim());
																				facilityDetailRequestDTO.setEscodL1(requestDTO.getEscodL1().trim());
																			}
																		}
																 }
//																relationshipDateFormat.parse(requestDTO.getEscodL1().toString().trim());
//																facilityDetailRequestDTO.setEscodL1(requestDTO.getEscodL1().trim());
															} catch (ParseException e) {
																errors.add("escodL1", new ActionMessage("error.escodL1.invalid.format"));
															}
														} else {
															errors.add("escodL1", new ActionMessage("error.escodL1.mandatory"));
														} 
														//to set ownershipQ1FlagL1
														if (requestDTO.getOwnershipQ1FlagL1() != null && !requestDTO.getOwnershipQ1FlagL1().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getOwnershipQ1FlagL1().trim()) || "N".equals(requestDTO.getOwnershipQ1FlagL1().trim()))) {
																errors.add("ownershipQ1FlagL1", new ActionMessage("error.ownershipQ1FlagL1.invalid"));
															} else {
																facilityDetailRequestDTO.setOwnershipQ1FlagL1(requestDTO.getOwnershipQ1FlagL1());
															}
														} else {
															errors.add("ownershipQ1FlagL1", new ActionMessage("error.ownershipQ1FlagL1.mandatory"));
														}
														//to set ownershipQ2FlagL1
														if (requestDTO.getOwnershipQ2FlagL1() != null && !requestDTO.getOwnershipQ2FlagL1().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getOwnershipQ2FlagL1().trim()) || "N".equals(requestDTO.getOwnershipQ2FlagL1().trim()))) {
																errors.add("ownershipQ2FlagL1", new ActionMessage("error.ownershipQ2FlagL1.invalid"));
															} else {
																facilityDetailRequestDTO.setOwnershipQ2FlagL1(requestDTO.getOwnershipQ2FlagL1());
															}
														} else {
															errors.add("ownershipQ2FlagL1", new ActionMessage("error.ownershipQ2FlagL1.mandatory"));
														}
														//to set ownershipQ3FlagL1
														if (requestDTO.getOwnershipQ3FlagL1() != null && !requestDTO.getOwnershipQ3FlagL1().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getOwnershipQ3FlagL1().trim()) || "N".equals(requestDTO.getOwnershipQ3FlagL1().trim()))) {
																errors.add("ownershipQ3FlagL1", new ActionMessage("error.ownershipQ3FlagL1.invalid"));
															} else {
																facilityDetailRequestDTO.setOwnershipQ3FlagL1(requestDTO.getOwnershipQ3FlagL1());
															}
														} else {
															errors.add("ownershipQ3FlagL1", new ActionMessage("error.ownershipQ3FlagL1.mandatory"));
														}
														//to set scopeQ1FlagL1
														if (requestDTO.getScopeQ1FlagL1() != null && !requestDTO.getScopeQ1FlagL1().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getScopeQ1FlagL1().trim()) || "N".equals(requestDTO.getScopeQ1FlagL1().trim()))) {
																errors.add("scopeQ1FlagL1", new ActionMessage("error.scopeQ1FlagL1.invalid"));
															} else {
																facilityDetailRequestDTO.setScopeQ1FlagL1(requestDTO.getScopeQ1FlagL1());
															}
														} else {
															errors.add("scopeQ1FlagL1", new ActionMessage("error.scopeQ1FlagL1.mandatory"));
														}
														//to set scopeQ2FlagL1
														if (requestDTO.getScopeQ2FlagL1() != null && !requestDTO.getScopeQ2FlagL1().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getScopeQ2FlagL1().trim()) || "N".equals(requestDTO.getScopeQ2FlagL1().trim()))) {
																errors.add("scopeQ2FlagL1", new ActionMessage("error.scopeQ2FlagL1.invalid"));
															} else {
																facilityDetailRequestDTO.setScopeQ2FlagL1(requestDTO.getScopeQ2FlagL1());
															}
														} else {
															errors.add("scopeQ2FlagL1", new ActionMessage("error.scopeQ2FlagL1.mandatory"));
														}
														//to set scopeQ3FlagL1
														if (requestDTO.getScopeQ3FlagL1() != null && !requestDTO.getScopeQ3FlagL1().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getScopeQ3FlagL1().trim()) || "N".equals(requestDTO.getScopeQ3FlagL1().trim()))) {
																errors.add("scopeQ3FlagL1", new ActionMessage("error.scopeQ3FlagL1.invalid"));
															} else {
																facilityDetailRequestDTO.setScopeQ3FlagL1(requestDTO.getScopeQ3FlagL1());
															}
														} else {
															errors.add("scopeQ3FlagL1", new ActionMessage("error.scopeQ3FlagL1.mandatory"));
														}
														//to set revisedEscodL1
														if (requestDTO.getRevisedEscodL1() != null && !requestDTO.getRevisedEscodL1().toString().trim().isEmpty()) {
															try {
																//if (! (errorCode = Validator.checkDate(requestDTO.getRevisedEscodL1(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																if (! (isValidDate((requestDTO.getRevisedEscodL1().toString().trim())))) {
																	errors.add("revisedEscodL1", new ActionMessage("error.revisedEscodL1.invalid.format"));
																 }	else {
																	 String d1 = (String)requestDTO.getRevisedEscodL1();
																		String[] d2 = d1.split("/");
																		if(d2.length == 3) {
																			if(d2[2].length() != 4) {
																				errors.add("revisedEscodL1", new ActionMessage("error.revisedEscodL1.invalid.format"));
																			}else {
																				relationshipDateFormat.parse(requestDTO.getRevisedEscodL1().toString().trim());
//																				facilityDetailRequestDTO.setRevisedEscodL1(requestDTO.getRevisedEscodL1().trim());
																				facilityDetailRequestDTO.setRevisedEscodL1("");
																				}
																		}
																 }
															} catch (ParseException e) {
																errors.add("revisedEscodL1", new ActionMessage("error.revisedEscodL1.invalid.format"));
															}
														}
														//to set exeAssetClassL1
														/*if(requestDTO.getExeAssetClassL1()!=null && !requestDTO.getExeAssetClassL1().trim().isEmpty()){
															Object exeAssetClassL1Obj = masterObj.getObjectByEntityName("entryCode", requestDTO.getExeAssetClassL1().trim(),
																	"exeAssetClass",errors,"EXE_ASSET_CLASS_ID");
															if(!(exeAssetClassL1Obj instanceof ActionErrors)){
																facilityDetailRequestDTO.setExeAssetClassL1(((ICommonCodeEntry)exeAssetClassL1Obj).getEntryCode());
																//to set exeAssetClassDtL1
																if(!requestDTO.getExeAssetClassL1().equals("1")) {
																	if (requestDTO.getExeAssetClassDtL1() != null && !requestDTO.getExeAssetClassDtL1().toString().trim().isEmpty()) {
																		try {
																			if (! (errorCode = Validator.checkDate(requestDTO.getExeAssetClassDtL1(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																				errors.add("exeAssetClassDtL1", new ActionMessage("error.exeAssetClassDtL1.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getExeAssetClassDtL1();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("exeAssetClassDtL1", new ActionMessage("error.exeAssetClassDtL1.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getExeAssetClassDtL1().toString().trim());
																							facilityDetailRequestDTO.setExeAssetClassDtL1(requestDTO.getExeAssetClassDtL1().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("exeAssetClassDtL1", new ActionMessage("error.exeAssetClassDtL1.invalid.format"));
																		}
																	} else {
																		errors.add("exeAssetClassDtL1", new ActionMessage("error.exeAssetClassDtL1.mandatory"));
																	}
																} else {
																	facilityDetailRequestDTO.setExeAssetClassDtL1("");
																}
															} else {
																errors.add("exeAssetClassL1",new ActionMessage("error.exeAssetClassL1.invalid"));
															}
														}else{
															errors.add("exeAssetClassL1",new ActionMessage("error.exeAssetClassL1.mandatory"));
														}*/
														//to set revAssetClassL1
														if(requestDTO.getRevAssetClassL1()!=null && !requestDTO.getRevAssetClassL1().trim().isEmpty()){
															Object revAssetClassL1Obj = masterObj.getObjectByEntityName("entryCode", requestDTO.getRevAssetClassL1().trim(),
																	"revAssetClass",errors,"REV_ASSET_CLASS_ID");
															if(!(revAssetClassL1Obj instanceof ActionErrors)){
																facilityDetailRequestDTO.setRevAssetClassL1(((ICommonCodeEntry)revAssetClassL1Obj).getEntryCode());
																//to set revAssetClassDtL1
																if(!requestDTO.getRevAssetClassL1().equals("1")) {
																	if (requestDTO.getRevAssetClassDtL1() != null && !requestDTO.getRevAssetClassDtL1().toString().trim().isEmpty()) {
																		try {
																			//if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDtL1(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																			if (! (isValidDate((requestDTO.getRevAssetClassDtL1().toString().trim())))) {
																				errors.add("revAssetClassDtL1", new ActionMessage("error.revAssetClassDtL1.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getRevAssetClassDtL1();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("revAssetClassDtL1", new ActionMessage("error.revAssetClassDtL1.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getRevAssetClassDtL1().toString().trim());
																							facilityDetailRequestDTO.setRevAssetClassDtL1(requestDTO.getRevAssetClassDtL1().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("revAssetClassDtL1", new ActionMessage("error.revAssetClassDtL1.invalid.format"));
																		}
																	} else {
																		errors.add("revAssetClassDtL1", new ActionMessage("error.revAssetClassDtL1.mandatory"));
																	}
																} else {
																	
																	if(requestDTO.getRevAssetClassDtL1() != null && !requestDTO.getRevAssetClassDtL1().toString().trim().isEmpty()) {
																		try {
																			//if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDtL1(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																			if (! (isValidDate((requestDTO.getRevAssetClassDtL1().toString().trim())))) {
																				errors.add("revAssetClassDtL1", new ActionMessage("error.revAssetClassDtL1.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getRevAssetClassDtL1();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("revAssetClassDtL1", new ActionMessage("error.revAssetClassDtL1.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getRevAssetClassDtL1().toString().trim());
																							facilityDetailRequestDTO.setRevAssetClassDtL1(requestDTO.getRevAssetClassDtL1().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("revAssetClassDtL1", new ActionMessage("error.revAssetClassDtL1.invalid.format"));
																		}
																	}else {
																		facilityDetailRequestDTO.setRevAssetClassDtL1(null);
																	}
																}
															} else {
																errors.add("revAssetClassL1",new ActionMessage("error.revAssetClassL1.invalid"));
															}
														} else {
															errors.add("revAssetClassL1",new ActionMessage("error.revAssetClassL1.mandatory"));
														}
													}
      //<<<<<<<<<<<<<<<<<<<<<<<<<<2nd DELAY CAM>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
													else if("2".equals(requestDTO.getDelaylevel())) {
														DefaultLogger.debug(this,"======2nd DELAY CAM========");
														//to set delayFlagL2
														if (requestDTO.getDelayFlagL2() != null && !requestDTO.getDelayFlagL2().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getDelayFlagL2().trim()) || "N".equals(requestDTO.getDelayFlagL2().trim()))) {
																errors.add("delayFlagL2", new ActionMessage("error.delayFlagL2.invalid"));
															} else {
																if("Y".equals(requestDTO.getDelayFlagL2().trim())) {
																	facilityDetailRequestDTO.setDelayFlagL2(requestDTO.getDelayFlagL2());
																}else {
																	errors.add("delayFlagL2", new ActionMessage("error.delayFlagL2.notvalid"));
																}
															}
														} else {
															errors.add("delayFlagL2", new ActionMessage("error.delayFlagL2.mandatory"));
														}
														//to set defReasonL2
														if (requestDTO.getDefReasonL2() != null && !requestDTO.getDefReasonL2().trim().isEmpty()) {
															if (!(errorCode = Validator.checkString(requestDTO.getDefReasonL2(), false, 1, 200)).equals(Validator.ERROR_NONE)) {
																errors.add("defReasonL2", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "200"));
															} else {
															facilityDetailRequestDTO.setDefReasonL2(requestDTO.getDefReasonL2());
															}
														} else {
															errors.add("defReasonL2", new ActionMessage("error.defReasonL2.mandatory"));
														}
														//to set repayChngSchedL2
														if (requestDTO.getRepayChngSchedL2() != null && !requestDTO.getRepayChngSchedL2().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getRepayChngSchedL2().trim()) || "N".equals(requestDTO.getRepayChngSchedL2().trim()))) {
																errors.add("repayChngSchedL2", new ActionMessage("error.repayChngSchedL2.invalid"));
															} else {
																facilityDetailRequestDTO.setRepayChngSchedL2(requestDTO.getRepayChngSchedL2());
															}
														} else {
															errors.add("repayChngSchedL2", new ActionMessage("error.repayChngSchedL2.mandatory"));
														}
														//to set escodL2
														if (requestDTO.getEscodL2() != null && !requestDTO.getEscodL2().toString().trim().isEmpty()) {
															try {
																//if (! (errorCode = Validator.checkDate(requestDTO.getEscodL2(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																if (! (isValidDate((requestDTO.getEscodL2().toString().trim())))) {
																	errors.add("escodL2", new ActionMessage("error.escodL2.invalid.format"));
																 }	else {
																	 String d1 = (String)requestDTO.getEscodL2();
																		String[] d2 = d1.split("/");
																		if(d2.length == 3) {
																			if(d2[2].length() != 4) {
																				errors.add("escodL2", new ActionMessage("error.escodL2.invalid.format"));
																			}else {
																				relationshipDateFormat.parse(requestDTO.getEscodL2().toString().trim());
																				facilityDetailRequestDTO.setEscodL2(requestDTO.getEscodL2().trim());
																				}
																		}
																 }
															} catch (ParseException e) {
																errors.add("escodL2", new ActionMessage("error.escodL2.invalid.format"));
															}
														} else {
															errors.add("escodL2", new ActionMessage("error.escodL2.mandatory"));
														} 
														//to set escodRevisionReasonQ1L2 and 
														if(requestDTO.getInfraFlag().equals("Y")) {
															if (requestDTO.getEscodRevisionReasonQ1L2() != null && !requestDTO.getEscodRevisionReasonQ1L2().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ1L2().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ1L2().trim()))) {
																	errors.add("escodRevisionReasonQ1L2", new ActionMessage("error.escodRevisionReasonQ1L2.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ1L2(requestDTO.getEscodRevisionReasonQ1L2());
																	//to set legalDetailL2
																	if("Y".equals(requestDTO.getEscodRevisionReasonQ1L2().trim())) {
																		if (requestDTO.getLegalDetailL2() != null && !requestDTO.getLegalDetailL2().trim().isEmpty()) {
																			facilityDetailRequestDTO.setLegalDetailL2(requestDTO.getLegalDetailL2());
																		} else {
																			errors.add("legalDetailL2", new ActionMessage("error.legalDetailL2.mandatory"));
																		}
																	} else {
																		if (requestDTO.getLegalDetailL2() != null && !requestDTO.getLegalDetailL2().trim().isEmpty()) {
																			facilityDetailRequestDTO.setLegalDetailL2(requestDTO.getLegalDetailL2());
																		} else {
																			facilityDetailRequestDTO.setLegalDetailL2(" ");
																		}
																	}
																}
															} else {
																errors.add("escodRevisionReasonQ1L2", new ActionMessage("error.escodRevisionReasonQ1L2.mandatory"));
															}
															//to set escodRevisionReasonQ2L2
															if (requestDTO.getEscodRevisionReasonQ2L2() != null && !requestDTO.getEscodRevisionReasonQ2L2().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ2L2().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ2L2().trim()))) {
																	errors.add("escodRevisionReasonQ2L2", new ActionMessage("error.escodRevisionReasonQ2L2.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ2L2(requestDTO.getEscodRevisionReasonQ2L2());
																	//to set beyondControlL2
																	if("Y".equals(requestDTO.getEscodRevisionReasonQ2L2())) {
																		if (requestDTO.getBeyondControlL2() != null && !requestDTO.getBeyondControlL2().trim().isEmpty()) {
																			facilityDetailRequestDTO.setBeyondControlL2(requestDTO.getBeyondControlL2());
																		} else {
																			errors.add("beyondControlL2", new ActionMessage("error.beyondControlL2.mandatory"));
																		}
																	}else {
																		/*if (requestDTO.getBeyondControlL2() != null && !requestDTO.getBeyondControlL2().trim().isEmpty()) {
																			facilityDetailRequestDTO.setBeyondControlL2(requestDTO.getBeyondControlL2());
																		}else {*/
																			facilityDetailRequestDTO.setBeyondControlL2(" ");
														//				}
																	}
																}
															} else {
																errors.add("escodRevisionReasonQ2L2", new ActionMessage("error.escodRevisionReasonQ2L2.mandatory"));
															}
															//to set escodRevisionReasonQ3L2 
															if (requestDTO.getEscodRevisionReasonQ3L2() != null && !requestDTO.getEscodRevisionReasonQ3L2().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ3L2().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ3L2().trim()))) {
																	errors.add("escodRevisionReasonQ3L2", new ActionMessage("error.escodRevisionReasonQ3L2.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ3L2(requestDTO.getEscodRevisionReasonQ3L2());
																	
																	//to set ownershipQ1FlagL2
																	if (requestDTO.getOwnershipQ1FlagL2() != null && !requestDTO.getOwnershipQ1FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ1FlagL2().trim()) || "N".equals(requestDTO.getOwnershipQ1FlagL2().trim()))) {
																			errors.add("ownershipQ1FlagL2", new ActionMessage("error.ownershipQ1FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ1FlagL2(requestDTO.getOwnershipQ1FlagL2());
																		}
																	} else {
																		errors.add("ownershipQ1FlagL2", new ActionMessage("error.ownershipQ1FlagL2.mandatory"));
																	}
																	//to set ownershipQ2FlagL2
																	if (requestDTO.getOwnershipQ2FlagL2() != null && !requestDTO.getOwnershipQ2FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ2FlagL2().trim()) || "N".equals(requestDTO.getOwnershipQ2FlagL2().trim()))) {
																			errors.add("ownershipQ2FlagL2", new ActionMessage("error.ownershipQ2FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ2FlagL2(requestDTO.getOwnershipQ2FlagL2());
																		}
																	} else {
																		errors.add("ownershipQ2FlagL2", new ActionMessage("error.ownershipQ2FlagL2.mandatory"));
																	}
																	//to set ownershipQ3FlagL2
																	if (requestDTO.getOwnershipQ3FlagL2() != null && !requestDTO.getOwnershipQ3FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ3FlagL2().trim()) || "N".equals(requestDTO.getOwnershipQ3FlagL2().trim()))) {
																			errors.add("ownershipQ3FlagL2", new ActionMessage("error.ownershipQ3FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ3FlagL2(requestDTO.getOwnershipQ3FlagL2());
																		}
																	} else {
																		errors.add("ownershipQ3FlagL2", new ActionMessage("error.ownershipQ3FlagL2.mandatory"));
																	}
																	
																}
															} else {
																errors.add("escodRevisionReasonQ3L2", new ActionMessage("error.escodRevisionReasonQ3L2.mandatory"));
															}
															
															//to set escodRevisionReasonQ4L2
															if (requestDTO.getEscodRevisionReasonQ4L2() != null && !requestDTO.getEscodRevisionReasonQ4L2().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ4L2().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ4L2().trim()))) {
																	errors.add("escodRevisionReasonQ4L2", new ActionMessage("error.escodRevisionReasonQ4L2.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ4L2(requestDTO.getEscodRevisionReasonQ4L2());
																	
																	//to set scopeQ1FlagL2
																	if (requestDTO.getScopeQ1FlagL2() != null && !requestDTO.getScopeQ1FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ1FlagL2().trim()) || "N".equals(requestDTO.getScopeQ1FlagL2().trim()))) {
																			errors.add("scopeQ1FlagL2", new ActionMessage("error.scopeQ1FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ1FlagL2(requestDTO.getScopeQ1FlagL2());
																		}
																	} else {
																		errors.add("scopeQ1FlagL2", new ActionMessage("error.scopeQ1FlagL2.mandatory"));
																	}
																	//to set scopeQ2FlagL2
																	if (requestDTO.getScopeQ2FlagL2() != null && !requestDTO.getScopeQ2FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ2FlagL2().trim()) || "N".equals(requestDTO.getScopeQ2FlagL2().trim()))) {
																			errors.add("scopeQ2FlagL2", new ActionMessage("error.scopeQ2FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ2FlagL2(requestDTO.getScopeQ2FlagL2());
																		}
																	} else {
																		errors.add("scopeQ2FlagL2", new ActionMessage("error.scopeQ2FlagL2.mandatory"));
																	}
																	//to set scopeQ3FlagL2
																	if (requestDTO.getScopeQ3FlagL2() != null && !requestDTO.getScopeQ3FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ3FlagL2().trim()) || "N".equals(requestDTO.getScopeQ3FlagL2().trim()))) {
																			errors.add("scopeQ3FlagL2", new ActionMessage("error.scopeQ3FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ3FlagL2(requestDTO.getScopeQ3FlagL2());
																		}
																	} else {
																		errors.add("scopeQ3FlagL2", new ActionMessage("error.scopeQ3FlagL2.mandatory"));
																	}
																	
																}
															} else {
																errors.add("escodRevisionReasonQ4L2", new ActionMessage("error.escodRevisionReasonQ4L2.mandatory"));
															}
														} else if(requestDTO.getInfraFlag().equals("N")) {
															//to set escodRevisionReasonQ5L2 
															if (requestDTO.getEscodRevisionReasonQ5L2() != null && !requestDTO.getEscodRevisionReasonQ5L2().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ5L2().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ5L2().trim()))) {
																	errors.add("escodRevisionReasonQ5L2", new ActionMessage("error.escodRevisionReasonQ5L2.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ5L2(requestDTO.getEscodRevisionReasonQ5L2());
																	
																	//to set ownershipQ1FlagL2
																	if (requestDTO.getOwnershipQ1FlagL2() != null && !requestDTO.getOwnershipQ1FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ1FlagL2().trim()) || "N".equals(requestDTO.getOwnershipQ1FlagL2().trim()))) {
																			errors.add("ownershipQ1FlagL2", new ActionMessage("error.ownershipQ1FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ1FlagL2(requestDTO.getOwnershipQ1FlagL2());
																		}
																	} else {
																		errors.add("ownershipQ1FlagL2", new ActionMessage("error.ownershipQ1FlagL2.mandatory"));
																	}
																	//to set ownershipQ2FlagL2
																	if (requestDTO.getOwnershipQ2FlagL2() != null && !requestDTO.getOwnershipQ2FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ2FlagL2().trim()) || "N".equals(requestDTO.getOwnershipQ2FlagL2().trim()))) {
																			errors.add("ownershipQ2FlagL2", new ActionMessage("error.ownershipQ2FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ2FlagL2(requestDTO.getOwnershipQ2FlagL2());
																		}
																	} else {
																		errors.add("ownershipQ2FlagL2", new ActionMessage("error.ownershipQ2FlagL2.mandatory"));
																	}
																	//to set ownershipQ3FlagL2
																	if (requestDTO.getOwnershipQ3FlagL2() != null && !requestDTO.getOwnershipQ3FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ3FlagL2().trim()) || "N".equals(requestDTO.getOwnershipQ3FlagL2().trim()))) {
																			errors.add("ownershipQ3FlagL2", new ActionMessage("error.ownershipQ3FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ3FlagL2(requestDTO.getOwnershipQ3FlagL2());
																		}
																	} else {
																		errors.add("ownershipQ3FlagL2", new ActionMessage("error.ownershipQ3FlagL2.mandatory"));
																	}
																	
																}
															} else {
																errors.add("escodRevisionReasonQ5L2", new ActionMessage("error.escodRevisionReasonQ5L2.mandatory"));
															}
															
															//to set escodRevisionReasonQ6L2
															if (requestDTO.getEscodRevisionReasonQ6L2() != null && !requestDTO.getEscodRevisionReasonQ6L2().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ6L2().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ6L2().trim()))) {
																	errors.add("escodRevisionReasonQ6L2", new ActionMessage("error.escodRevisionReasonQ6L2.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ6L2(requestDTO.getEscodRevisionReasonQ6L2());
																	
																	//to set scopeQ1FlagL2
																	if (requestDTO.getScopeQ1FlagL2() != null && !requestDTO.getScopeQ1FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ1FlagL2().trim()) || "N".equals(requestDTO.getScopeQ1FlagL2().trim()))) {
																			errors.add("scopeQ1FlagL2", new ActionMessage("error.scopeQ1FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ1FlagL2(requestDTO.getScopeQ1FlagL2());
																		}
																	} else {
																		errors.add("scopeQ1FlagL2", new ActionMessage("error.scopeQ1FlagL2.mandatory"));
																	}
																	//to set scopeQ2FlagL2
																	if (requestDTO.getScopeQ2FlagL2() != null && !requestDTO.getScopeQ2FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ2FlagL2().trim()) || "N".equals(requestDTO.getScopeQ2FlagL2().trim()))) {
																			errors.add("scopeQ2FlagL2", new ActionMessage("error.scopeQ2FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ2FlagL2(requestDTO.getScopeQ2FlagL2());
																		}
																	} else {
																		errors.add("scopeQ2FlagL2", new ActionMessage("error.scopeQ2FlagL2.mandatory"));
																	}
																	//to set scopeQ3FlagL2
																	if (requestDTO.getScopeQ3FlagL2() != null && !requestDTO.getScopeQ3FlagL2().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ3FlagL2().trim()) || "N".equals(requestDTO.getScopeQ3FlagL2().trim()))) {
																			errors.add("scopeQ3FlagL2", new ActionMessage("error.scopeQ3FlagL2.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ3FlagL2(requestDTO.getScopeQ3FlagL2());
																		}
																	} else {
																		errors.add("scopeQ3FlagL2", new ActionMessage("error.scopeQ3FlagL2.mandatory"));
																	}
																	
																}
															} else {
																errors.add("escodRevisionReasonQ6L2", new ActionMessage("error.escodRevisionReasonQ6L2.mandatory"));
															}
														}
												
														
														//to set revisedEscodL2
														if (requestDTO.getRevisedEscodL2() != null && !requestDTO.getRevisedEscodL2().toString().trim().isEmpty()) {
															try {
																//if (! (errorCode = Validator.checkDate(requestDTO.getRevisedEscodL2(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																if (! (isValidDate((requestDTO.getRevisedEscodL2().toString().trim())))) {
																	errors.add("revisedEscodL2", new ActionMessage("error.revisedEscodL2.invalid.format"));
																 }	else {
																	 String d1 = (String)requestDTO.getRevisedEscodL2();
																		String[] d2 = d1.split("/");
																		if(d2.length == 3) {
																			if(d2[2].length() != 4) {
																				errors.add("revisedEscodL2", new ActionMessage("error.revisedEscodL2.invalid.format"));
																			}else {
																				relationshipDateFormat.parse(requestDTO.getRevisedEscodL2().toString().trim());
																				facilityDetailRequestDTO.setRevisedEscodL2(requestDTO.getRevisedEscodL2().trim());
																				}
																		}
																 }
															} catch (ParseException e) {
																errors.add("revisedEscodL2", new ActionMessage("error.revisedEscodL2.invalid.format"));
															}
														}/*else {
															errors.add("revisedEscodL2", new ActionMessage("error.revisedEscodL2.mandatory"));
														}*/
														//to set exeAssetClassL2
														/*if(requestDTO.getExeAssetClassL2()!=null && !requestDTO.getExeAssetClassL2().trim().isEmpty()){
															Object exeAssetClassL1Obj = masterObj.getObjectByEntityName("entryCode", requestDTO.getExeAssetClassL2().trim(),
																	"exeAssetClass",errors,"EXE_ASSET_CLASS_ID");
															if(!(exeAssetClassL1Obj instanceof ActionErrors)){
																facilityDetailRequestDTO.setExeAssetClassL2(((ICommonCodeEntry)exeAssetClassL1Obj).getEntryCode());
																//to set exeAssetClassDtL2
																if(!requestDTO.getExeAssetClassL2().equals("1")) {
																	if (requestDTO.getExeAssetClassDtL2() != null && !requestDTO.getExeAssetClassDtL2().toString().trim().isEmpty()) {
																		try {
																			if (! (errorCode = Validator.checkDate(requestDTO.getExeAssetClassDtL2(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																				errors.add("exeAssetClassDtL2", new ActionMessage("error.exeAssetClassDtL2.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getExeAssetClassDtL2();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("exeAssetClassDtL2", new ActionMessage("error.exeAssetClassDtL2.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getExeAssetClassDtL2().toString().trim());
																							facilityDetailRequestDTO.setExeAssetClassDtL2(requestDTO.getExeAssetClassDtL2().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("exeAssetClassDtL2", new ActionMessage("error.exeAssetClassDtL2.invalid.format"));
																		}
																	} else {
																		errors.add("exeAssetClassDtL2", new ActionMessage("error.exeAssetClassDtL2.mandatory"));
																	}
																} else {
																	facilityDetailRequestDTO.setExeAssetClassDtL2("");
																}
															}
														}else{
															errors.add("exeAssetClassL2",new ActionMessage("error.exeAssetClassL2.mandatory"));
														}*/
														//to set revAssetClassL2
														if(requestDTO.getRevAssetClassL2()!=null && !requestDTO.getRevAssetClassL2().trim().isEmpty()){
															Object revAssetClassL1Obj = masterObj.getObjectByEntityName("entryCode", requestDTO.getRevAssetClassL2().trim(),
																	"revAssetClass",errors,"REV_ASSET_CLASS_ID");
															if(!(revAssetClassL1Obj instanceof ActionErrors)){
																facilityDetailRequestDTO.setRevAssetClassL2(((ICommonCodeEntry)revAssetClassL1Obj).getEntryCode());
																//to set revAssetClassDtL2
																if(!requestDTO.getRevAssetClassL2().equals("1")) {
																	if (requestDTO.getRevAssetClassDtL2() != null && !requestDTO.getRevAssetClassDtL2().toString().trim().isEmpty()) {
																		try {
																			//if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDtL2(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																			if (! (isValidDate((requestDTO.getRevAssetClassDtL2().toString().trim())))) {
																				errors.add("revAssetClassDtL2", new ActionMessage("error.revAssetClassDtL2.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getRevAssetClassDtL2();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("revAssetClassDtL2", new ActionMessage("error.revAssetClassDtL2.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getRevAssetClassDtL2().toString().trim());
																							facilityDetailRequestDTO.setRevAssetClassDtL2(requestDTO.getRevAssetClassDtL2().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("revAssetClassDtL2", new ActionMessage("error.revAssetClassDtL2.invalid.format"));
																		}
																	} else {
																		errors.add("revAssetClassDtL2", new ActionMessage("error.revAssetClassDtL2.mandatory"));
																	}
																} else {
																	
																	if(requestDTO.getRevAssetClassDtL2() != null && !requestDTO.getRevAssetClassDtL2().toString().trim().isEmpty()) {
																		try {
																			//if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDtL2(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																			if (! (isValidDate((requestDTO.getRevAssetClassDtL2().toString().trim())))) {
																				errors.add("revAssetClassDtL2", new ActionMessage("error.revAssetClassDtL2.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getRevAssetClassDtL2();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("revAssetClassDtL2", new ActionMessage("error.revAssetClassDtL2.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getRevAssetClassDtL2().toString().trim());
																							facilityDetailRequestDTO.setRevAssetClassDtL2(requestDTO.getRevAssetClassDtL2().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("revAssetClassDtL2", new ActionMessage("error.revAssetClassDtL2.invalid.format"));
																		}
																	}else {
																		facilityDetailRequestDTO.setRevAssetClassDtL2("");
																	}
																}
														} else {
																errors.add("revAssetClass",new ActionMessage("error.revAssetClass.invalid"));
															}
														} else {
															errors.add("revAssetClassL2",new ActionMessage("error.revAssetClassL2.mandatory"));
														}
													
													}
        //<<<<<<<<<<<<<<<<<<<<<<<3rd DELAY CAM>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
													else if("3".equals(requestDTO.getDelaylevel())) {
														//to set delayFlagL3
														DefaultLogger.debug(this,"======3rd DELAY CAM========");
														if (requestDTO.getDelayFlagL3() != null && !requestDTO.getDelayFlagL3().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getDelayFlagL3().trim()) || "N".equals(requestDTO.getDelayFlagL3().trim()))) {
																errors.add("delayFlagL3", new ActionMessage("error.delayFlagL3.invalid"));
															} else {
																if("Y".equals(requestDTO.getDelayFlagL3().trim())) {
																facilityDetailRequestDTO.setDelayFlagL3(requestDTO.getDelayFlagL3());
																}else {
																	errors.add("delayFlagL3", new ActionMessage("error.delayFlagL3.notvalid"));
																}
															}
														} else {
															errors.add("delayFlagL3", new ActionMessage("error.delayFlagL3.mandatory"));
														}
														
														//to set defReasonL3
														if (requestDTO.getDefReasonL3() != null && !requestDTO.getDefReasonL3().trim().isEmpty()) {
															if (!(errorCode = Validator.checkString(requestDTO.getDefReasonL3(), false, 1, 200)).equals(Validator.ERROR_NONE)) {
																errors.add("defReasonL3", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "200"));
															} else {
															facilityDetailRequestDTO.setDefReasonL3(requestDTO.getDefReasonL3());
															}
														} else {
															errors.add("defReasonL3", new ActionMessage("error.defReasonL3.mandatory"));
														}
														//to set repayChngSchedL3
														if (requestDTO.getRepayChngSchedL3() != null && !requestDTO.getRepayChngSchedL3().trim().isEmpty()) {
															if (!("Y".equals(requestDTO.getRepayChngSchedL3().trim()) || "N".equals(requestDTO.getRepayChngSchedL3().trim()))) {
																errors.add("repayChngSchedL3", new ActionMessage("error.repayChngSchedL3.invalid"));
															} else {
																facilityDetailRequestDTO.setRepayChngSchedL3(requestDTO.getRepayChngSchedL3());
															}
														} else {
															errors.add("repayChngSchedL3", new ActionMessage("error.repayChngSchedL3.mandatory"));
														}
														//to set escodL3
														if (requestDTO.getEscodL3() != null && !requestDTO.getEscodL3().toString().trim().isEmpty()) {
															try {
															
															//if (! (errorCode = Validator.checkDate(requestDTO.getEscodL3(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
															if (! (isValidDate((requestDTO.getEscodL3().toString().trim())))) {
																errors.add("escodL3", new ActionMessage("error.escodL3.invalid.format")); 
															 }	else {
																 String d1 = (String)requestDTO.getEscodL3();
																	String[] d2 = d1.split("/");
																	if(d2.length == 3) {
																		if(d2[2].length() != 4) {
																			errors.add("escodL3", new ActionMessage("error.escodL3.invalid.format"));
																		}else {
																			relationshipDateFormat.parse(requestDTO.getEscodL3().toString().trim());
																			facilityDetailRequestDTO.setEscodL3(requestDTO.getEscodL3().trim());
																		}
																	}
																}
															
															} catch (ParseException e) {
																errors.add("escodL3", new ActionMessage("error.escodL3.invalid.format"));
															}
														} else {
															errors.add("escodL3", new ActionMessage("error.escodL3.mandatory"));
														} 
														//to set escodRevisionReasonQ1L3 and 
														if(requestDTO.getInfraFlag().equals("Y")) {
															if (requestDTO.getEscodRevisionReasonQ1L3() != null && !requestDTO.getEscodRevisionReasonQ1L3().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ1L3().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ1L3().trim()))) {
																	errors.add("escodRevisionReasonQ1L3", new ActionMessage("error.escodRevisionReasonQ1L3.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ1L3(requestDTO.getEscodRevisionReasonQ1L3());
																	//to set legalDetailL3
																	if("Y".equals(requestDTO.getEscodRevisionReasonQ1L3().trim())) {
																		if (requestDTO.getLegalDetailL3() != null && !requestDTO.getLegalDetailL3().trim().isEmpty()) {
																			facilityDetailRequestDTO.setLegalDetailL3(requestDTO.getLegalDetailL3());
																		} else {
																			errors.add("legalDetailL3", new ActionMessage("error.legalDetailL3.mandatory"));
																		}
																	} else {
																		/*if (requestDTO.getLegalDetailL3() != null && !requestDTO.getLegalDetailL3().trim().isEmpty()) {
																			facilityDetailRequestDTO.setLegalDetailL3(requestDTO.getLegalDetailL3());
																		}else {*/
																			facilityDetailRequestDTO.setLegalDetailL3(" ");
														//				}
																	}
																}
															} else {
																errors.add("escodRevisionReasonQ1L3", new ActionMessage("error.escodRevisionReasonQ1L3.mandatory"));
															}
															//to set escodRevisionReasonQ2L3
															if (requestDTO.getEscodRevisionReasonQ2L3() != null && !requestDTO.getEscodRevisionReasonQ2L3().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ2L3().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ2L3().trim()))) {
																	errors.add("escodRevisionReasonQ2L3", new ActionMessage("error.escodRevisionReasonQ2L3.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ2L3(requestDTO.getEscodRevisionReasonQ2L3());
																	//to set beyondControlL3
																	if("Y".equals(requestDTO.getEscodRevisionReasonQ2L3().trim())) {
																		if (requestDTO.getBeyondControlL3() != null && !requestDTO.getBeyondControlL3().trim().isEmpty()) {
																			facilityDetailRequestDTO.setBeyondControlL3(requestDTO.getBeyondControlL3());
																		} else {
																			errors.add("beyondControlL3", new ActionMessage("error.beyondControlL3.mandatory"));
																		}
																	} else {
																		/*if (requestDTO.getBeyondControlL3() != null && !requestDTO.getBeyondControlL3().trim().isEmpty()) {
																			facilityDetailRequestDTO.setBeyondControlL3(requestDTO.getBeyondControlL3());
																		}else {*/
																			facilityDetailRequestDTO.setBeyondControlL3(" ");
																//	}
																	}
																}
															} else {
																errors.add("escodRevisionReasonQ2L3", new ActionMessage("error.escodRevisionReasonQ2L3.mandatory"));
															}
															
															//to set escodRevisionReasonQ3L3 
															if (requestDTO.getEscodRevisionReasonQ3L3() != null && !requestDTO.getEscodRevisionReasonQ3L3().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ3L3().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ3L3().trim()))) {
																	errors.add("escodRevisionReasonQ3L3", new ActionMessage("error.escodRevisionReasonQ3L3.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ3L3(requestDTO.getEscodRevisionReasonQ3L3());
																	
																	//to set ownershipQ1FlagL3
																	if (requestDTO.getOwnershipQ1FlagL3() != null && !requestDTO.getOwnershipQ1FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ1FlagL3().trim()) || "N".equals(requestDTO.getOwnershipQ1FlagL3().trim()))) {
																			errors.add("ownershipQ1FlagL3", new ActionMessage("error.ownershipQ1FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ1FlagL3(requestDTO.getOwnershipQ1FlagL3());
																		}
																	} else {
																		errors.add("ownershipQ1FlagL3", new ActionMessage("error.ownershipQ1FlagL3.mandatory"));
																	}
																	//to set ownershipQ2FlagL3
																	if (requestDTO.getOwnershipQ2FlagL3() != null && !requestDTO.getOwnershipQ2FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ2FlagL3().trim()) || "N".equals(requestDTO.getOwnershipQ2FlagL3().trim()))) {
																			errors.add("ownershipQ2FlagL3", new ActionMessage("error.ownershipQ2FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ2FlagL3(requestDTO.getOwnershipQ2FlagL3());
																		}
																	} else {
																		errors.add("ownershipQ2FlagL3", new ActionMessage("error.ownershipQ2FlagL3.mandatory"));
																	}
																	//to set ownershipQ3FlagL3
																	if (requestDTO.getOwnershipQ3FlagL3() != null && !requestDTO.getOwnershipQ3FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ3FlagL3().trim()) || "N".equals(requestDTO.getOwnershipQ3FlagL3().trim()))) {
																			errors.add("ownershipQ3FlagL3", new ActionMessage("error.ownershipQ3FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ3FlagL3(requestDTO.getOwnershipQ3FlagL3());
																		}
																	} else {
																		errors.add("ownershipQ3FlagL3", new ActionMessage("error.ownershipQ3FlagL3.mandatory"));
																	}
																	
																}
															} else {
																errors.add("escodRevisionReasonQ3L3", new ActionMessage("error.escodRevisionReasonQ3L3.mandatory"));
															}
															
															//to set escodRevisionReasonQ4L3
															if (requestDTO.getEscodRevisionReasonQ4L3() != null && !requestDTO.getEscodRevisionReasonQ4L3().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ4L3().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ4L3().trim()))) {
																	errors.add("escodRevisionReasonQ4L3", new ActionMessage("error.escodRevisionReasonQ4L3.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ4L3(requestDTO.getEscodRevisionReasonQ4L3());
																	
																	//to set scopeQ1FlagL3
																	if (requestDTO.getScopeQ1FlagL3() != null && !requestDTO.getScopeQ1FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ1FlagL3().trim()) || "N".equals(requestDTO.getScopeQ1FlagL3().trim()))) {
																			errors.add("scopeQ1FlagL3", new ActionMessage("error.scopeQ1FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ1FlagL3(requestDTO.getScopeQ1FlagL3());
																		}
																	} else {
																		errors.add("scopeQ1FlagL3", new ActionMessage("error.scopeQ1FlagL3.mandatory"));
																	}
																	//to set scopeQ2FlagL3
																	if (requestDTO.getScopeQ2FlagL3() != null && !requestDTO.getScopeQ2FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ2FlagL3().trim()) || "N".equals(requestDTO.getScopeQ2FlagL3().trim()))) {
																			errors.add("scopeQ2FlagL3", new ActionMessage("error.scopeQ2FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ2FlagL3(requestDTO.getScopeQ2FlagL3());
																		}
																	} else {
																		errors.add("scopeQ2FlagL3", new ActionMessage("error.scopeQ2FlagL3.mandatory"));
																	}
																	//to set scopeQ3FlagL3
																	if (requestDTO.getScopeQ3FlagL3() != null && !requestDTO.getScopeQ3FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ3FlagL3().trim()) || "N".equals(requestDTO.getScopeQ3FlagL3().trim()))) {
																			errors.add("scopeQ3FlagL3", new ActionMessage("error.scopeQ3FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ3FlagL3(requestDTO.getScopeQ3FlagL3());
																		}
																	} else {
																		errors.add("scopeQ3FlagL3", new ActionMessage("error.scopeQ3FlagL3.mandatory"));
																	}
																	
																}
															} else {
																errors.add("escodRevisionReasonQ4L3", new ActionMessage("error.escodRevisionReasonQ4L3.mandatory"));
															}
														} else if(requestDTO.getInfraFlag().equals("N")) {
															//to set escodRevisionReasonQ5L3 
															if (requestDTO.getEscodRevisionReasonQ5L3() != null && !requestDTO.getEscodRevisionReasonQ5L3().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ5L3().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ5L3().trim()))) {
																	errors.add("escodRevisionReasonQ5L3", new ActionMessage("error.escodRevisionReasonQ5L3.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ5L3(requestDTO.getEscodRevisionReasonQ5L3());
																	
																	//to set ownershipQ1FlagL3
																	if (requestDTO.getOwnershipQ1FlagL3() != null && !requestDTO.getOwnershipQ1FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ1FlagL3().trim()) || "N".equals(requestDTO.getOwnershipQ1FlagL3().trim()))) {
																			errors.add("ownershipQ1FlagL3", new ActionMessage("error.ownershipQ1FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ1FlagL3(requestDTO.getOwnershipQ1FlagL3());
																		}
																	} else {
																		errors.add("ownershipQ1FlagL3", new ActionMessage("error.ownershipQ1FlagL3.mandatory"));
																	}
																	//to set ownershipQ2FlagL3
																	if (requestDTO.getOwnershipQ2FlagL3() != null && !requestDTO.getOwnershipQ2FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ2FlagL3().trim()) || "N".equals(requestDTO.getOwnershipQ2FlagL3().trim()))) {
																			errors.add("ownershipQ2FlagL3", new ActionMessage("error.ownershipQ2FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ2FlagL3(requestDTO.getOwnershipQ2FlagL3());
																		}
																	} else {
																		errors.add("ownershipQ2FlagL3", new ActionMessage("error.ownershipQ2FlagL3.mandatory"));
																	}
																	//to set ownershipQ3FlagL3
																	if (requestDTO.getOwnershipQ3FlagL3() != null && !requestDTO.getOwnershipQ3FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getOwnershipQ3FlagL3().trim()) || "N".equals(requestDTO.getOwnershipQ3FlagL3().trim()))) {
																			errors.add("ownershipQ3FlagL3", new ActionMessage("error.ownershipQ3FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setOwnershipQ3FlagL3(requestDTO.getOwnershipQ3FlagL3());
																		}
																	} else {
																		errors.add("ownershipQ3FlagL3", new ActionMessage("error.ownershipQ3FlagL3.mandatory"));
																	}
																	
																}
															} else {
																errors.add("escodRevisionReasonQ5L3", new ActionMessage("error.escodRevisionReasonQ5L3.mandatory"));
															}
															
															//to set escodRevisionReasonQ6L3
															if (requestDTO.getEscodRevisionReasonQ6L3() != null && !requestDTO.getEscodRevisionReasonQ6L3().trim().isEmpty()) {
																if (!("Y".equals(requestDTO.getEscodRevisionReasonQ6L3().trim()) || "N".equals(requestDTO.getEscodRevisionReasonQ6L3().trim()))) {
																	errors.add("escodRevisionReasonQ6L3", new ActionMessage("error.escodRevisionReasonQ6L3.invalid"));
																} else {
																	facilityDetailRequestDTO.setEscodRevisionReasonQ6L3(requestDTO.getEscodRevisionReasonQ6L3());
																	
																	//to set scopeQ1FlagL3
																	if (requestDTO.getScopeQ1FlagL3() != null && !requestDTO.getScopeQ1FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ1FlagL3().trim()) || "N".equals(requestDTO.getScopeQ1FlagL3().trim()))) {
																			errors.add("scopeQ1FlagL3", new ActionMessage("error.scopeQ1FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ1FlagL3(requestDTO.getScopeQ1FlagL3());
																		}
																	} else {
																		errors.add("scopeQ1FlagL3", new ActionMessage("error.scopeQ1FlagL3.mandatory"));
																	}
																	//to set scopeQ2FlagL3
																	if (requestDTO.getScopeQ2FlagL3() != null && !requestDTO.getScopeQ2FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ2FlagL3().trim()) || "N".equals(requestDTO.getScopeQ2FlagL3().trim()))) {
																			errors.add("scopeQ2FlagL3", new ActionMessage("error.scopeQ2FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ2FlagL3(requestDTO.getScopeQ2FlagL3());
																		}
																	} else {
																		errors.add("scopeQ2FlagL3", new ActionMessage("error.scopeQ2FlagL3.mandatory"));
																	}
																	//to set scopeQ3FlagL3
																	if (requestDTO.getScopeQ3FlagL3() != null && !requestDTO.getScopeQ3FlagL3().trim().isEmpty()) {
																		if (!("Y".equals(requestDTO.getScopeQ3FlagL3().trim()) || "N".equals(requestDTO.getScopeQ3FlagL3().trim()))) {
																			errors.add("scopeQ3FlagL3", new ActionMessage("error.scopeQ3FlagL3.invalid"));
																		} else {
																			facilityDetailRequestDTO.setScopeQ3FlagL3(requestDTO.getScopeQ3FlagL3());
																		}
																	} else {
																		errors.add("scopeQ3FlagL3", new ActionMessage("error.scopeQ3FlagL3.mandatory"));
																	}
																	
																}
															} else {
																errors.add("escodRevisionReasonQ6L3", new ActionMessage("error.escodRevisionReasonQ6L3.mandatory"));
															}
														}
														
														//to set revisedEscodL3
														if (requestDTO.getRevisedEscodL3() != null && !requestDTO.getRevisedEscodL3().toString().trim().isEmpty()) {
															try {
																//if (! (errorCode = Validator.checkDate(requestDTO.getRevisedEscodL3(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																if (! (isValidDate((requestDTO.getRevisedEscodL3().toString().trim())))) {
																	errors.add("revisedEscodL3", new ActionMessage("error.revisedEscodL3.invalid.format"));
																 }	else {
																	 String d1 = (String)requestDTO.getRevisedEscodL3();
																		String[] d2 = d1.split("/");
																		if(d2.length == 3) {
																			if(d2[2].length() != 4) {
																				errors.add("revisedEscodL3", new ActionMessage("error.revisedEscodL3.invalid.format"));
																			}else {
																				relationshipDateFormat.parse(requestDTO.getRevisedEscodL3().toString().trim());
																				facilityDetailRequestDTO.setRevisedEscodL3(requestDTO.getRevisedEscodL3().trim());
																				}
																		}
																 }
															} catch (ParseException e) {
																errors.add("revisedEscodL3", new ActionMessage("error.revisedEscodL3.invalid.format"));
															}
														}/*else {
															errors.add("revisedEscodL3", new ActionMessage("error.revisedEscodL3.mandatory"));
														}*/
														//to set exeAssetClassL3
														/*if(requestDTO.getExeAssetClassL3()!=null && !requestDTO.getExeAssetClassL3().trim().isEmpty()){
															Object exeAssetClassL1Obj = masterObj.getObjectByEntityName("entryCode", requestDTO.getExeAssetClassL3().trim(),
																	"exeAssetClass",errors,"EXE_ASSET_CLASS_ID");
															if(!(exeAssetClassL1Obj instanceof ActionErrors)){
																facilityDetailRequestDTO.setExeAssetClassL3(((ICommonCodeEntry)exeAssetClassL1Obj).getEntryCode());
																//to set exeAssetClassDtL2
																if(!requestDTO.getExeAssetClassL3().equals("1")) {
																	if (requestDTO.getExeAssetClassDtL3() != null && !requestDTO.getExeAssetClassDtL3().toString().trim().isEmpty()) {
																		try {
																			if (! (errorCode = Validator.checkDate(requestDTO.getExeAssetClassDtL3(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																				errors.add("exeAssetClassDtL3", new ActionMessage("error.exeAssetClassDtL3.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getExeAssetClassDtL3();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("exeAssetClassDtL3", new ActionMessage("error.exeAssetClassDtL3.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getExeAssetClassDtL3().toString().trim());
																							facilityDetailRequestDTO.setExeAssetClassDtL3(requestDTO.getExeAssetClassDtL3().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("exeAssetClassDtL3", new ActionMessage("error.exeAssetClassDtL3.invalid.format"));
																		}
																	} else {
																		errors.add("exeAssetClassDtL3", new ActionMessage("error.exeAssetClassDtL3.mandatory"));
																	}
																} else {
																	facilityDetailRequestDTO.setExeAssetClassDtL3("");
																}
															}
														}else{
															errors.add("exeAssetClassL3",new ActionMessage("error.exeAssetClassL3.mandatory"));
														}*/
														//to set revAssetClassL3
														if(requestDTO.getRevAssetClassL3()!=null && !requestDTO.getRevAssetClassL3().trim().isEmpty()){
															Object revAssetClassL1Obj = masterObj.getObjectByEntityName("entryCode", requestDTO.getRevAssetClassL3().trim(),
																	"revAssetClass",errors,"REV_ASSET_CLASS_ID");
															if(!(revAssetClassL1Obj instanceof ActionErrors)){
																facilityDetailRequestDTO.setRevAssetClassL3(((ICommonCodeEntry)revAssetClassL1Obj).getEntryCode());
																//to set revAssetClassDtL3
																if(!requestDTO.getRevAssetClassL3().equals("1")) {
																	if (requestDTO.getRevAssetClassDtL3() != null && !requestDTO.getRevAssetClassDtL3().toString().trim().isEmpty()) {
																		try {
																			//if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDtL3(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																			if (! (isValidDate((requestDTO.getRevAssetClassDtL3().toString().trim())))) {
																				errors.add("revAssetClassDtL3", new ActionMessage("error.revAssetClassDtL3.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getRevAssetClassDtL3();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("revAssetClassDtL3", new ActionMessage("error.revAssetClassDtL3.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getRevAssetClassDtL3().toString().trim());
																							facilityDetailRequestDTO.setRevAssetClassDtL3(requestDTO.getRevAssetClassDtL3().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("revAssetClassDtL3", new ActionMessage("error.revAssetClassDtL3.invalid.format"));
																		}
																	} else {
																		errors.add("revAssetClassDtL3", new ActionMessage("error.revAssetClassDtL3.mandatory"));
																	}
																} else {
																	
																	if(requestDTO.getRevAssetClassDtL3() != null && !requestDTO.getRevAssetClassDtL3().toString().trim().isEmpty()) {
																		try {
																			//if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDtL3(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																			if (! (isValidDate((requestDTO.getRevAssetClassDtL3().toString().trim())))) {
																				errors.add("revAssetClassDtL3", new ActionMessage("error.revAssetClassDtL3.invalid.format"));
																			 }	else {
																				 String d1 = (String)requestDTO.getRevAssetClassDtL3();
																					String[] d2 = d1.split("/");
																					if(d2.length == 3) {
																						if(d2[2].length() != 4) {
																							errors.add("revAssetClassDtL3", new ActionMessage("error.revAssetClassDtL3.invalid.format"));
																						}else {
																							relationshipDateFormat.parse(requestDTO.getRevAssetClassDtL3().toString().trim());
																							facilityDetailRequestDTO.setRevAssetClassDtL3(requestDTO.getRevAssetClassDtL3().trim());
																							}
																					}
																			 }
																		} catch (ParseException e) {
																			errors.add("revAssetClassDtL3", new ActionMessage("error.revAssetClassDtL3.invalid.format"));
																		}
																	}else {
																		facilityDetailRequestDTO.setRevAssetClassDtL3("");
																		
																	}
																}
														} else {
																errors.add("revAssetClass",new ActionMessage("error.revAssetClass.invalid"));
															}
														} else {
															errors.add("revAssetClassL3",new ActionMessage("error.revAssetClassL3.mandatory"));
														}
													}//end 3rd Level delay
													
												}//end scodObj instanceof ActionErrors
												else {
													errors.add("delaylevel",new ActionMessage("error.delaylevel.invalid"));
												}
											} else {
//												errors.add("delaylevel", new ActionMessage("error.delaylevel.mandatory"));
												
												if("Y".equals(requestDTO.getDelayFlagL1())) {
													errors.add("delaylevel", new ActionMessage("error.delaylevel.mandatory"));
												}
												
												
												if(requestDTO.getRevAssetClass()!=null && !requestDTO.getRevAssetClass().trim().isEmpty()){
													Object scodObj = masterObj.getObjectByEntityName("entryCode", requestDTO.getRevAssetClass().trim(),
															"revAssetClass",errors,"REV_ASSET_CLASS_ID");
													if(!(scodObj instanceof ActionErrors)){
														facilityDetailRequestDTO.setRevAssetClass(((ICommonCodeEntry)scodObj).getEntryCode());
														//to set revAssetClassDt
														if(!requestDTO.getRevAssetClass().equals("1")) {
															if (requestDTO.getRevAssetClassDt() != null && !requestDTO.getRevAssetClassDt().toString().trim().isEmpty()) {
																try {
																	//if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDt(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																	if (! (isValidDate((requestDTO.getRevAssetClassDt().toString().trim())))) {
																		errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																	 }	else {
																		 String d1 = (String)requestDTO.getRevAssetClassDt();
																			String[] d2 = d1.split("/");
																			if(d2.length == 3) {
																				if(d2[2].length() != 4) {
																					errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																				}else {
																					relationshipDateFormat.parse(requestDTO.getRevAssetClassDt().toString().trim());
																					facilityDetailRequestDTO.setRevAssetClassDt(requestDTO.getRevAssetClassDt().trim());
																					}
																			}
																	 }
																} catch (ParseException e) {
																	errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																}
															} else {
																errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.mandatory"));
															}
														} else {
															
															if(requestDTO.getRevAssetClassDt() != null && !requestDTO.getRevAssetClassDt().toString().trim().isEmpty()) {
															
															try {
																//if (! (errorCode = Validator.checkDate(requestDTO.getRevAssetClassDt(), true, Locale.getDefault())).equals(Validator.ERROR_NONE)) {
																if (! (isValidDate((requestDTO.getRevAssetClassDt().toString().trim())))) {
																	errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																 }	else {
																	 String d1 = (String)requestDTO.getRevAssetClassDt();
																		String[] d2 = d1.split("/");
																		if(d2.length == 3) {
																			if(d2[2].length() != 4) {
																				errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
																			}else {
																				relationshipDateFormat.parse(requestDTO.getRevAssetClassDt().toString().trim());
																				facilityDetailRequestDTO.setRevAssetClassDt(requestDTO.getRevAssetClassDt().trim());
																				}
																		}
																 }
															} catch (ParseException e) {
																errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.invalid.format"));
															}
															}else {
																errors.add("revAssetClassDt", new ActionMessage("error.revAssetClassDt.mandatory"));
															}
														}
													} else {
														errors.add("revAssetClass",new ActionMessage("error.revAssetClass.invalid"));
													}
												}else{
													errors.add("revAssetClass",new ActionMessage("error.revAssetClass.mandatory"));
												}
												
											}
										}
									}
							 }
							 
							 if(requestDTO.getDelaylevel() == null || "".equals(requestDTO.getDelaylevel())) {
									facilityDetailRequestDTO.setDelayFlagL1("N");
								}else if("2".equals(requestDTO.getDelaylevel())) {
									facilityDetailRequestDTO.setDelayFlagL1(requestDTO.getDelayFlagL2());
								}else if("3".equals(requestDTO.getDelaylevel())) {
									facilityDetailRequestDTO.setDelayFlagL1(requestDTO.getDelayFlagL3());
								}
							 
					} else {
						errors.add("limitReleaseFlg", new ActionMessage("error.limitReleaseFlg.mandatory"));
					}
				}//end of WS_FAC_EDIT_SCOD_REST event
			//	}
			/*} else {
				errors.add("camType", new ActionMessage("error.camType.invalid"));
			}
		} else {
			errors.add("camType", new ActionMessage("error.camType.mandatory"));
		}*/
		
		
		
		facilityDetailRequestDTO.setErrors(errors);
		return facilityDetailRequestDTO;
	}

	public LmtDetailForm getSCODFormFromRestDTO(FacilitySCODDetailRestRequestDTO dto, ICMSCustomer cust) {
		LmtDetailForm lmtDetailForm = new LmtDetailForm();
		String camId = "";

		try {
			if (dto.getCamId() != null && !dto.getCamId().trim().isEmpty()) {
				camId = dto.getCamId().trim();
			}

			/*
			 * if(dto.getCamType()!=null && !dto.getCamType().trim().isEmpty()){
			 * lmtDetailForm.setCamType(dto.getCamType().trim()); }
			 */
			lmtDetailForm.setLineNo("");
			//Initial CAM
			if (dto.getProjectFinance() != null && !dto.getProjectFinance().trim().isEmpty()) {
				lmtDetailForm.setProjectFinance(dto.getProjectFinance().trim());
			}

			if (dto.getProjectLoan() != null && !dto.getProjectLoan().trim().isEmpty()) {
				lmtDetailForm.setProjectLoan(dto.getProjectLoan().trim());
			}

			if (dto.getInfraFlag() != null && !dto.getInfraFlag().trim().isEmpty()) {
				lmtDetailForm.setInfaProjectFlag(dto.getInfraFlag().trim());
			}

			if (dto.getScod() != null && !dto.getScod().trim().isEmpty()) {
				lmtDetailForm.setScodDate(dto.getScod().trim());
			}

			if (dto.getScodRemark() != null && !dto.getScodRemark().trim().isEmpty()) {
				lmtDetailForm.setRemarksSCOD(dto.getScodRemark().trim());
			}
			
			if (dto.getExeAssetClass() != null && !dto.getExeAssetClass().trim().isEmpty()) {
				lmtDetailForm.setExstAssetClass(dto.getExeAssetClass().trim());
			}
			
			if (dto.getExeAssetClassDate()!=null && !dto.getExeAssetClassDate().trim().isEmpty()) {
				lmtDetailForm.setExstAssClassDate(dto.getExeAssetClassDate().trim());
			}
			
			//Interim 
			
			if (dto.getLimitReleaseFlg() != null
					&& !dto.getLimitReleaseFlg().isEmpty()) {
				lmtDetailForm.setWhlmreupSCOD(dto.getLimitReleaseFlg().trim());
			}
			
			if (dto.getRepayChngSched() != null
					&& !dto.getRepayChngSched().isEmpty()) {
				lmtDetailForm.setChngInRepaySchedule(dto.getRepayChngSched().trim());
			}
			
			if (dto.getRevAssetClass()!= null
					&& !dto.getRevAssetClass().isEmpty()) {
				lmtDetailForm.setRevisedAssetClass(dto.getRevAssetClass().trim());
			}
			
			if (dto.getRevAssetClassDt()!=null 
						&& !dto.getRevAssetClassDt().trim().isEmpty()) {
				lmtDetailForm.setRevsdAssClassDate(dto.getRevAssetClassDt().trim());
			}
			
			if (dto.getAcod()!=null 
						&& !dto.getAcod().trim().isEmpty()) {
				lmtDetailForm.setActualCommOpsDate(dto.getAcod().trim());
			}
			
			//Level 1
			
			if (dto.getDelayFlagL1() != null
					&& !dto.getDelayFlagL1().isEmpty()) {
				lmtDetailForm.setProjectDelayedFlag(dto.getDelayFlagL1().trim());
			}
			
			if (dto.getInterestFlag() != null
					&& !dto.getInterestFlag().isEmpty()) {
				lmtDetailForm.setPrincipalInterestSchFlag(dto.getInterestFlag().trim());
			}
			
			if (dto.getPriorReqFlag() != null
					&& !dto.getPriorReqFlag().isEmpty()) {
				lmtDetailForm.setRecvPriorOfSCOD(dto.getPriorReqFlag().trim());
			}
			
			if (dto.getDelaylevel() != null
					&& !dto.getDelaylevel().isEmpty()) {
				lmtDetailForm.setLelvelDelaySCOD(dto.getDelaylevel().trim());
			}
			
			if (dto.getDefReasonL1() != null
					&& !dto.getDefReasonL1().isEmpty()) {
				lmtDetailForm.setReasonLevelOneDelay(dto.getDefReasonL1().trim());
			}
			
			/*if (dto.getRepayChngSchedL1() != null
					&& !dto.getRepayChngSchedL1().isEmpty()) {
				lmtDetailForm.setProjectFinance(dto.getRepayChngSchedL1().trim());
			}*/
			
			if (dto.getEscodL1()!=null 
						&& !dto.getEscodL1().trim().isEmpty()) {
				lmtDetailForm.setEscodLevelOneDelayDate(dto.getEscodL1().trim());
			}
			
			if (dto.getOwnershipQ1FlagL1()!= null
					&& !dto.getOwnershipQ1FlagL1().isEmpty()) {
				lmtDetailForm.setPromotersCapRunFlag(dto.getOwnershipQ1FlagL1().trim());
			}
			
			if (dto.getOwnershipQ2FlagL1()!= null
					&& !dto.getOwnershipQ2FlagL1().isEmpty()) {
				lmtDetailForm.setPromotersHoldEquityFlag(dto.getOwnershipQ2FlagL1().trim());
			}
			
			if (dto.getOwnershipQ3FlagL1()!= null
					&& !dto.getOwnershipQ3FlagL1().isEmpty()) {
				lmtDetailForm.setHasProjectViabReAssFlag(dto.getOwnershipQ3FlagL1().trim());
			}
			
			if (dto.getScopeQ1FlagL1()!= null
					&& !dto.getScopeQ1FlagL1().isEmpty()) {
				lmtDetailForm.setChangeInScopeBeforeSCODFlag(dto.getScopeQ1FlagL1().trim());
			}
			
			if (dto.getScopeQ2FlagL1()!= null
					&& !dto.getScopeQ2FlagL1().isEmpty()) {
				lmtDetailForm.setCostOverrunOrg25CostViabilityFlag(dto.getScopeQ2FlagL1().trim());
			}
			
			if (dto.getScopeQ3FlagL1()!= null
					&& !dto.getScopeQ3FlagL1().isEmpty()) {
				lmtDetailForm.setProjectViabReassesedFlag(dto.getScopeQ3FlagL1().trim());
			}
			
			if (dto.getRevisedEscodL1()!=null 
						&& !dto.getRevisedEscodL1().trim().isEmpty()) {
				lmtDetailForm.setRevsedESCODStpDate(dto.getRevisedEscodL1().trim());
			}
			
			if (dto.getExeAssetClassL1()!= null
					&& !dto.getExeAssetClassL1().isEmpty()) {
				lmtDetailForm.setExstAssetClassL1(dto.getExeAssetClassL1().trim());
			}
			
			if (dto.getExeAssetClassDtL1()!=null 
						&& !dto.getExeAssetClassDtL1().trim().isEmpty()) {
				lmtDetailForm.setExstAssClassDateL1(dto.getExeAssetClassDtL1().trim());
			}
			
			if (dto.getRevAssetClassL1()!= null
					&& !dto.getRevAssetClassL1().isEmpty()) {
				lmtDetailForm.setRevisedAssetClassL1(dto.getRevAssetClassL1().trim());
			}
			
			if (dto.getRevAssetClassDtL1()!=null 
						&& !dto.getRevAssetClassDtL1().trim().isEmpty()) {
				lmtDetailForm.setRevsdAssClassDateL1(dto.getRevAssetClassDtL1().trim());
			}
			
			//Level 2
			
			if (dto.getDelayFlagL2() != null
					&& !dto.getDelayFlagL2().isEmpty()) {
				lmtDetailForm.setProjectDelayedFlagL2(dto.getDelayFlagL2().trim());
			}
			
			if (dto.getDelaylevel() != null
					&& !dto.getDelaylevel().isEmpty()) {
				lmtDetailForm.setLelvelDelaySCOD(dto.getDelaylevel().trim());
			}
			
			if (dto.getDefReasonL2() != null
					&& !dto.getDefReasonL2().isEmpty()) {
				lmtDetailForm.setReasonLevelTwoDelay(dto.getDefReasonL2().trim());
			}
			
			if (dto.getRepayChngSchedL2() != null
					&& !dto.getRepayChngSchedL2().isEmpty()) {
				lmtDetailForm.setChngInRepayScheduleL2(dto.getRepayChngSchedL2().trim());
			}
			
			if (dto.getEscodL2()!=null 
						&& !dto.getEscodL2().trim().isEmpty()) {
				lmtDetailForm.setEscodLevelSecondDelayDate(dto.getEscodL2().trim());
			}
			
			if (dto.getEscodRevisionReasonQ1L2() != null
					&& !dto.getEscodRevisionReasonQ1L2().isEmpty()) {
				lmtDetailForm.setLegalOrArbitrationLevel2Flag(dto.getEscodRevisionReasonQ1L2().trim());
			}
			
			if (dto.getEscodRevisionReasonQ2L2() != null
					&& !dto.getEscodRevisionReasonQ2L2().isEmpty()) {
				lmtDetailForm.setReasonBeyondPromoterLevel2Flag(dto.getEscodRevisionReasonQ2L2().trim());
			}
			
			if (dto.getEscodRevisionReasonQ3L2() != null
					&& !dto.getEscodRevisionReasonQ3L2().isEmpty()) {
				lmtDetailForm.setChngOfOwnPrjFlagInfraLevel2(dto.getEscodRevisionReasonQ3L2().trim());
			}
			
			if (dto.getEscodRevisionReasonQ4L2() != null
					&& !dto.getEscodRevisionReasonQ4L2().isEmpty()) {
				lmtDetailForm.setChngOfProjScopeInfraLevel2(dto.getEscodRevisionReasonQ4L2().trim());
			}
			
			if (dto.getEscodRevisionReasonQ5L2() != null
					&& !dto.getEscodRevisionReasonQ5L2().isEmpty()) {
				lmtDetailForm.setChngOfOwnPrjFlagNonInfraLevel2(dto.getEscodRevisionReasonQ5L2().trim());
			}
			
			if (dto.getEscodRevisionReasonQ6L2() != null
					&& !dto.getEscodRevisionReasonQ6L2().isEmpty()) {
				lmtDetailForm.setChngOfProjScopeNonInfraLevel2(dto.getEscodRevisionReasonQ6L2().trim());
			}
			
			if (dto.getLegalDetailL2() != null
					&& !dto.getLegalDetailL2().isEmpty()) {
				lmtDetailForm.setLeaglOrArbiProceed(dto.getLegalDetailL2().trim());
			}else {
				lmtDetailForm.setLeaglOrArbiProceed(" ");
			}
			
			if (dto.getBeyondControlL2() != null
					&& !dto.getBeyondControlL2().isEmpty()) {
				lmtDetailForm.setDetailsRsnByndPro(dto.getBeyondControlL2().trim());
			}else {
				lmtDetailForm.setDetailsRsnByndPro(" ");
			}
			
			if (dto.getOwnershipQ1FlagL2()!= null
					&& !dto.getOwnershipQ1FlagL2().isEmpty()) {
				lmtDetailForm.setPromotersCapRunFlagL2(dto.getOwnershipQ1FlagL2().trim());
			}
			
			if (dto.getOwnershipQ2FlagL2()!= null
					&& !dto.getOwnershipQ2FlagL2().isEmpty()) {
				lmtDetailForm.setPromotersHoldEquityFlagL2(dto.getOwnershipQ2FlagL2().trim());
			}
			
			if (dto.getOwnershipQ3FlagL2()!= null
					&& !dto.getOwnershipQ3FlagL2().isEmpty()) {
				lmtDetailForm.setHasProjectViabReAssFlagL2(dto.getOwnershipQ3FlagL2().trim());
			}
			
			if (dto.getScopeQ1FlagL2()!= null
					&& !dto.getScopeQ1FlagL2().isEmpty()) {
				lmtDetailForm.setChangeInScopeBeforeSCODFlagL2(dto.getScopeQ1FlagL2().trim());
			}
			
			if (dto.getScopeQ2FlagL2()!= null
					&& !dto.getScopeQ2FlagL2().isEmpty()) {
				lmtDetailForm.setCostOverrunOrg25CostViabilityFlagL2(dto.getScopeQ2FlagL2().trim());
			}
			
			if (dto.getScopeQ3FlagL2()!= null
					&& !dto.getScopeQ3FlagL2().isEmpty()) {
				lmtDetailForm.setProjectViabReassesedFlagL2(dto.getScopeQ3FlagL2().trim());
			}
			
			if (dto.getRevisedEscodL2()!=null && !dto.getRevisedEscodL2().trim().isEmpty()) {
				lmtDetailForm.setRevsedESCODStpDateL2(dto.getRevisedEscodL2().trim());
			}
			
			if (dto.getExeAssetClassL2()!= null
					&& !dto.getExeAssetClassL2().isEmpty()) {
				lmtDetailForm.setExstAssetClassL2(dto.getExeAssetClassL2().trim());
			}
			
			if (dto.getExeAssetClassDtL2()!=null && !dto.getExeAssetClassDtL2().trim().isEmpty()) {
				lmtDetailForm.setExstAssClassDateL2(dto.getExeAssetClassDtL2().trim());
			}
			
			
			if (dto.getRevAssetClassL2()!= null
					&& !dto.getRevAssetClassL2().isEmpty()) {
				lmtDetailForm.setRevisedAssetClass_L2(dto.getRevAssetClassL2().trim());
			}
			
			if (dto.getRevAssetClassDtL2()!=null && !dto.getRevAssetClassDtL2().trim().isEmpty()) {
				lmtDetailForm.setRevsdAssClassDate_L2(dto.getRevAssetClassDtL2().trim());
			}
			
			//Level 3
			
			if (dto.getDelayFlagL3() != null
					&& !dto.getDelayFlagL3().isEmpty()) {
				lmtDetailForm.setProjectDelayedFlagL3(dto.getDelayFlagL3().trim());
			}
			
			if (dto.getDelaylevel() != null
					&& !dto.getDelaylevel().isEmpty()) {
				lmtDetailForm.setLelvelDelaySCOD(dto.getDelaylevel().trim());
			}
			
			if (dto.getDefReasonL3() != null
					&& !dto.getDefReasonL3().isEmpty()) {
				lmtDetailForm.setReasonLevelThreeDelay(dto.getDefReasonL3().trim());
			}
			
			if (dto.getRepayChngSchedL3() != null
					&& !dto.getRepayChngSchedL3().isEmpty()) {
				lmtDetailForm.setChngInRepayScheduleL3(dto.getRepayChngSchedL3().trim());
			}
			
			if (dto.getEscodL3()!=null && !dto.getEscodL3().trim().isEmpty()) {
				lmtDetailForm.setEscodLevelThreeDelayDate(dto.getEscodL3().trim());
			}
			
			if (dto.getEscodRevisionReasonQ1L3() != null
					&& !dto.getEscodRevisionReasonQ1L3().isEmpty()) {
				lmtDetailForm.setLegalOrArbitrationLevel3Flag(dto.getEscodRevisionReasonQ1L3().trim());
			}
			
			if (dto.getEscodRevisionReasonQ2L3() != null
					&& !dto.getEscodRevisionReasonQ2L3().isEmpty()) {
				lmtDetailForm.setReasonBeyondPromoterLevel3Flag(dto.getEscodRevisionReasonQ2L3().trim());
			}
			
			if (dto.getEscodRevisionReasonQ3L3() != null
					&& !dto.getEscodRevisionReasonQ3L3().isEmpty()) {
				lmtDetailForm.setChngOfOwnPrjFlagInfraLevel3(dto.getEscodRevisionReasonQ3L3().trim());
			}
			
			if (dto.getEscodRevisionReasonQ4L3() != null
					&& !dto.getEscodRevisionReasonQ4L3().isEmpty()) {
				lmtDetailForm.setChngOfProjScopeInfraLevel3(dto.getEscodRevisionReasonQ4L3().trim());
			}
			
			if (dto.getEscodRevisionReasonQ5L3() != null
					&& !dto.getEscodRevisionReasonQ5L3().isEmpty()) {
				lmtDetailForm.setChngOfOwnPrjFlagNonInfraLevel3(dto.getEscodRevisionReasonQ5L3().trim());
			}
			
			if (dto.getEscodRevisionReasonQ6L3() != null
					&& !dto.getEscodRevisionReasonQ6L3().isEmpty()) {
				lmtDetailForm.setChngOfProjScopeNonInfraLevel3(dto.getEscodRevisionReasonQ6L3().trim());
			}
			
			if (dto.getLegalDetailL3() != null
					&& !dto.getLegalDetailL3().isEmpty()) {
				lmtDetailForm.setLeaglOrArbiProceedLevel3(dto.getLegalDetailL3().trim());
			}else {
				lmtDetailForm.setLeaglOrArbiProceedLevel3(" ");
			}
			
			if (dto.getBeyondControlL3() != null
					&& !dto.getBeyondControlL3().isEmpty()) {
				lmtDetailForm.setDetailsRsnByndProLevel3(dto.getBeyondControlL3().trim());
			}else {
				lmtDetailForm.setDetailsRsnByndProLevel3(" ");
			}
			
			if (dto.getOwnershipQ1FlagL3()!= null
					&& !dto.getOwnershipQ1FlagL3().isEmpty()) {
				lmtDetailForm.setPromotersCapRunFlagL3(dto.getOwnershipQ1FlagL3().trim());
			}
			
			if (dto.getOwnershipQ2FlagL3()!= null
					&& !dto.getOwnershipQ2FlagL3().isEmpty()) {
				lmtDetailForm.setPromotersHoldEquityFlagL3(dto.getOwnershipQ2FlagL3().trim());
			}
			
			if (dto.getOwnershipQ3FlagL3()!= null
					&& !dto.getOwnershipQ3FlagL3().isEmpty()) {
				lmtDetailForm.setHasProjectViabReAssFlagL3(dto.getOwnershipQ3FlagL3().trim());
			}
			
			if (dto.getScopeQ1FlagL3()!= null
					&& !dto.getScopeQ1FlagL3().isEmpty()) {
				lmtDetailForm.setChangeInScopeBeforeSCODFlagL3(dto.getScopeQ1FlagL3().trim());
			}
			
			if (dto.getScopeQ2FlagL3()!= null
					&& !dto.getScopeQ2FlagL3().isEmpty()) {
				lmtDetailForm.setCostOverrunOrg25CostViabilityFlagL3(dto.getScopeQ2FlagL3().trim());
			}
			
			if (dto.getScopeQ3FlagL3()!= null
					&& !dto.getScopeQ3FlagL3().isEmpty()) {
				lmtDetailForm.setProjectViabReassesedFlagL3(dto.getScopeQ3FlagL3().trim());
			}
			
			if (dto.getRevisedEscodL3()!=null && !dto.getRevisedEscodL3().trim().isEmpty()) {
				lmtDetailForm.setRevsedESCODStpDateL3(dto.getRevisedEscodL3().trim());
			}
			
			if (dto.getExeAssetClassL3()!= null
					&& !dto.getExeAssetClassL3().isEmpty()) {
				lmtDetailForm.setExstAssetClassL3(dto.getExeAssetClassL3().trim());
			}
			
			if (dto.getExeAssetClassDtL3()!=null && !dto.getExeAssetClassDtL3().trim().isEmpty()) {
				lmtDetailForm.setExstAssClassDateL3(dto.getExeAssetClassDtL3().trim());
			}
			
			if (dto.getRevAssetClassL3()!= null
					&& !dto.getRevAssetClassL3().isEmpty()) {
				lmtDetailForm.setRevisedAssetClass_L3(dto.getRevAssetClassL3().trim());
			}
			
			if (dto.getRevAssetClassDtL3()!=null && !dto.getRevAssetClassDtL3().trim().isEmpty()) {
				lmtDetailForm.setRevsdAssClassDate_L3(dto.getRevAssetClassDtL3().trim());
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		lmtDetailForm.setLimitProfileID(camId);

		if (dto.getClimsFacilityId() != null && !dto.getClimsFacilityId().trim().isEmpty()) {
			lmtDetailForm.setClimsFacilityId(dto.getClimsFacilityId().trim());
		} else {
			lmtDetailForm.setClimsFacilityId("");
		}

		lmtDetailForm.setEvent(dto.getEvent());
		lmtDetailForm.setIsFromCamonlineReq('Y');

		return lmtDetailForm;
	}

	public FacilityNewFieldsDetailRequestDTO getNewFieldsRequestDTOWithActualValues(
			FacilityNewFieldsDetailRequestDTO requestDTO) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		SimpleDateFormat dateRequestFormat = new SimpleDateFormat("dd-MMM-yyyy");
		dateRequestFormat.setLenient(false);
		
		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();
		
		if(requestDTO.getRiskType()!=null && !requestDTO.getRiskType().trim().isEmpty()){
			 requestDTO.setRiskType(requestDTO.getRiskType().trim());			 
			 Object obj = masterObj.getObjectByEntityNameAndId("actualRiskType", requestDTO.getRiskType().trim(),"riskType",errors);
				if(!(obj instanceof ActionErrors)){
					requestDTO.setRiskType(((IRiskType)obj).getRiskTypeCode());
				}			 
		}
		
		if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("OFF") && ("WS_FAC_CREATE".equals(requestDTO.getEvent()) || "WS_FAC_EDIT".equals(requestDTO.getEvent()))) {
			// bypass mandatory validation
		}else {
			if ((requestDTO.getRiskType() == null) || "".equals(requestDTO.getRiskType().trim())) {
				 errors.add("riskType", new ActionMessage("error.string.mandatory"));
			}
		}
		
		 if(requestDTO.getTenorUnit()!=null && !requestDTO.getTenorUnit().trim().isEmpty()){
			  try {
				Object obj = masterObj.getMasterData("entryCode", Long.parseLong(requestDTO.getTenorUnit().trim()));
				if(obj!=null){
					ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
					if("TENOR".equals(codeEntry.getCategoryCode())){
						requestDTO.setTenorUnit((codeEntry).getEntryCode());
					}else{
						errors.add("tenorUnit",new ActionMessage("error.tenorUnit.invalid"));
					}
				}else{
					errors.add("tenorUnit",new ActionMessage("error.tenorUnit.invalid"));
				}
			  }catch(Exception e) {
					errors.add("tenorUnit",new ActionMessage("error.tenorUnit.invalid"));
			  }
		 }else{
			 requestDTO.setTenorUnit("");
		 }
		 
		 if(requestDTO.getTenorUnit()!=null && !requestDTO.getTenorUnit().trim().isEmpty()) {
			 if(requestDTO.getTenor()!=null && !requestDTO.getTenor().trim().isEmpty()){
				 if(ASSTValidator.isNumeric(requestDTO.getTenor().trim())) {
					 requestDTO.setTenor(requestDTO.getTenor().trim()); 
				 }else {
					 errors.add("tenor", new ActionMessage("only numeric is allowed"));
				 }
			 }else{
				 errors.add("tenor", new ActionMessage("error.string.mandatory"));
			 }
		 }else {
			 requestDTO.setTenor("");
		 }
		 
		 if(requestDTO.getTenorDesc()!=null && !requestDTO.getTenorDesc().trim().isEmpty()){
			 requestDTO.setTenorDesc(requestDTO.getTenorDesc().trim());
		 }else{
			 requestDTO.setTenorDesc("");
		 }
		 if(requestDTO.getMargin()!=null && !requestDTO.getMargin().trim().isEmpty()){
			 requestDTO.setMargin(requestDTO.getMargin().trim());
		 }else{
			 requestDTO.setMargin("");
		 }
		 if(requestDTO.getPutCallOption()!=null && !requestDTO.getPutCallOption().trim().isEmpty()){
			 if("PUT".equals(requestDTO.getPutCallOption().trim()) || "CALL".equals(requestDTO.getPutCallOption().trim())){
				 requestDTO.setPutCallOption(requestDTO.getPutCallOption().trim());
			 }else {
				 errors.add("putCallOption",new ActionMessage("Invalid value"));
			 }
		}else{
			 requestDTO.setPutCallOption("");
		}
		if(requestDTO.getOptionDate()!=null && !requestDTO.getOptionDate().trim().isEmpty()){
			try {
				dateRequestFormat.parse(requestDTO.getOptionDate().trim());
				requestDTO.setOptionDate(requestDTO.getOptionDate().toString().trim());
			} catch (ParseException e) {
				errors.add("optionDate",new ActionMessage("error.optionDate.invalid.format"));
			}
		}else{
			requestDTO.setOptionDate("");
		}
		
		if(requestDTO.getLoanAvailabilityDate()!=null && !requestDTO.getLoanAvailabilityDate().trim().isEmpty()){
			try {
				dateRequestFormat.parse(requestDTO.getLoanAvailabilityDate().trim());
				requestDTO.setLoanAvailabilityDate(requestDTO.getLoanAvailabilityDate().toString().trim());
			} catch (ParseException e) {
				errors.add("loanAvailabilityDate",new ActionMessage("error.loanAvailabilityDate.invalid.format"));
			}
		}else{
			requestDTO.setLoanAvailabilityDate("");
		}
	 
		if(requestDTO.getBankingArrangement()!=null && !requestDTO.getBankingArrangement().trim().isEmpty()){
			try {
				Object obj = masterObj.getMasterData("entryCode", Long.parseLong(requestDTO.getBankingArrangement().trim()));
				if(obj!=null){
					ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
					if("BANKING_ARRANGEMENT".equals(codeEntry.getCategoryCode())){
						requestDTO.setBankingArrangement((codeEntry).getEntryCode());
					}else{
						errors.add("bankingArrangement",new ActionMessage("error.bankingArrangement.invalid"));
					}
				}else{
					errors.add("bankingArrangement",new ActionMessage("error.bankingArrangement.invalid"));
				}
			}catch(Exception e) {
				errors.add("bankingArrangement",new ActionMessage("error.bankingArrangement.invalid"));
			}
		}
		
		if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("OFF") && "WS_NEW_FAC_EDIT".equals(requestDTO.getEvent())) {
			// bypass mandatory validation
		}else {
			if (requestDTO.getBankingArrangement() == null || "".equals(requestDTO.getBankingArrangement()))
			{
				errors.add("bankingArrangement", new ActionMessage("error.string.mandatory"));
			}
		}
		
		if(requestDTO.getClauseAsPerPolicy()!=null && !requestDTO.getClauseAsPerPolicy().trim().isEmpty()){
			try {
				Object obj = masterObj.getMasterData("entryCode", Long.parseLong(requestDTO.getClauseAsPerPolicy().trim()));
				if(obj!=null){
					ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
					if("CLAUSE_AS_PER_POLICY".equals(codeEntry.getCategoryCode())){
						requestDTO.setClauseAsPerPolicy((codeEntry).getEntryCode());
					}else{
						errors.add("clauseAsPerPolicy",new ActionMessage("error.clauseAsPerPolicy.invalid"));
					}
				}else{
					errors.add("clauseAsPerPolicy",new ActionMessage("error.clauseAsPerPolicy.invalid"));
				}
			}catch(Exception e) {
				errors.add("clauseAsPerPolicy",new ActionMessage("error.clauseAsPerPolicy.invalid"));
			}
		}
		
		if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("OFF") && "WS_NEW_FAC_EDIT".equals(requestDTO.getEvent())) {
			// bypass mandatory validation
		}else {
			if (requestDTO.getClauseAsPerPolicy() == null || "".equals(requestDTO.getClauseAsPerPolicy()))
			{
				errors.add("clauseAsPerPolicy", new ActionMessage("error.string.mandatory"));
			}
		}
		
		 requestDTO.setErrors(errors);
		 return requestDTO;
	}

	public LmtDetailForm getNewFieldsFormFromDTO(FacilityNewFieldsDetailRequestDTO facilityDetailRequestDTO, ICMSCustomer cust) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		SimpleDateFormat dateRequestFormat = new SimpleDateFormat("dd-MMM-yyyy");
		dateRequestFormat.setLenient(false);
		LmtDetailForm lmtDetailForm = new LmtDetailForm();	
		String camId = "";

		try {
			    if(facilityDetailRequestDTO.getCamId()!=null && !facilityDetailRequestDTO.getCamId().trim().isEmpty()){
			    	camId = facilityDetailRequestDTO.getCamId().trim();
			    }
				
				if(facilityDetailRequestDTO.getFacilityCategoryId()!=null && !facilityDetailRequestDTO.getFacilityCategoryId().trim().isEmpty()){
					lmtDetailForm.setFacilityCat(facilityDetailRequestDTO.getFacilityCategoryId().trim());
				}
				
				if(facilityDetailRequestDTO.getFacilityTypeId()!=null && !facilityDetailRequestDTO.getFacilityTypeId().trim().isEmpty()){
					lmtDetailForm.setFacilityType(facilityDetailRequestDTO.getFacilityTypeId().trim());
				}
		
				if(facilityDetailRequestDTO.getMainFacilityId()!=null && !facilityDetailRequestDTO.getMainFacilityId().trim().isEmpty()){
					lmtDetailForm.setMainFacilityId(facilityDetailRequestDTO.getMainFacilityId().trim());
				}
				
				lmtDetailForm.setCustomerID(String.valueOf(cust.getCustomerID()));
				lmtDetailForm.setFundedAmount("0");
				lmtDetailForm.setNonFundedAmount("0");
				lmtDetailForm.setMemoExposer("0");				
				Double totalFacSanctionedAmount = 0d;
				String currentFacSanc = null;
				ILimitProxy proxyLimit = null;
				proxyLimit= LimitProxyFactory.getProxy();
				
				if(facilityDetailRequestDTO.getEvent()!=null && "WS_NEW_FAC_EDIT".equals(facilityDetailRequestDTO.getEvent() ) ){
					if(facilityDetailRequestDTO.getClimsFacilityId()!=null && !facilityDetailRequestDTO.getClimsFacilityId().trim().isEmpty()){
						facilityDetailRequestDTO.setClimsFacilityId(facilityDetailRequestDTO.getClimsFacilityId().trim());
					}else{
						facilityDetailRequestDTO.setClimsFacilityId("");
					}
					DefaultLogger.debug(this, "facilityDetailRequestDTO.getClimsFacilityId() value inside FacilityDetailsDTOMapper:::"+facilityDetailRequestDTO.getClimsFacilityId()+">>>");
					currentFacSanc = proxyLimit.getTotalAmountByFacType(camId,facilityDetailRequestDTO.getFacilityTypeId(),facilityDetailRequestDTO.getClimsFacilityId());
				}
				
				if(currentFacSanc!=null){
					totalFacSanctionedAmount = Double.parseDouble(currentFacSanc);
				}
				Double totalPartySanctionedAmount = 0d;
				
				if(facilityDetailRequestDTO.getFacilityTypeId()!=null && !facilityDetailRequestDTO.getFacilityTypeId().trim().isEmpty()){
					if(facilityDetailRequestDTO.getFacilityTypeId().equals("FUNDED")){
						String currentPartySanc =cust.getTotalFundedLimit();
						totalPartySanctionedAmount = Double.parseDouble(currentPartySanc);
						Double difference = totalPartySanctionedAmount-totalFacSanctionedAmount;
						lmtDetailForm.setFundedAmount(String.valueOf(difference));
					}else if(facilityDetailRequestDTO.getFacilityTypeId().equals("NON_FUNDED")){
						String currentPartySanc =cust.getTotalNonFundedLimit();
						 totalPartySanctionedAmount = Double.parseDouble(currentPartySanc);
						 Double difference = totalPartySanctionedAmount-totalFacSanctionedAmount;
						lmtDetailForm.setNonFundedAmount(String.valueOf(difference));
					}else if(facilityDetailRequestDTO.getFacilityTypeId().equals("MEMO_EXPOSURE")){
						String currentPartySanc =cust.getMemoExposure();
						 totalPartySanctionedAmount = Double.parseDouble(currentPartySanc);
						 Double difference = totalPartySanctionedAmount-totalFacSanctionedAmount;
						lmtDetailForm.setMemoExposer(String.valueOf(difference));
					}
				}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (LimitException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
		lmtDetailForm.setLimitProfileID(camId);
		lmtDetailForm.setGuarantee("No");	
		lmtDetailForm.setIsReleased("N");
		
		if (facilityDetailRequestDTO.getCamId() != null && !facilityDetailRequestDTO.getCamId().trim().isEmpty()) {
			camId = facilityDetailRequestDTO.getCamId().trim();
		}
		
		if(facilityDetailRequestDTO.getRiskType()!=null && !facilityDetailRequestDTO.getRiskType().trim().isEmpty()){
			 lmtDetailForm.setRiskType(facilityDetailRequestDTO.getRiskType().trim());
		 }else{
			 lmtDetailForm.setRiskType("");
		 }
		 
		 if(facilityDetailRequestDTO.getTenorUnit()!=null && !facilityDetailRequestDTO.getTenorUnit().trim().isEmpty()){
			 lmtDetailForm.setTenorUnit(facilityDetailRequestDTO.getTenorUnit().trim());
		 }else{
			 lmtDetailForm.setTenorUnit("");
		 }
		 
		 if(facilityDetailRequestDTO.getTenor()!=null && !facilityDetailRequestDTO.getTenor().trim().isEmpty()){
			 lmtDetailForm.setTenor(facilityDetailRequestDTO.getTenor().trim());
		 }else{
			 lmtDetailForm.setTenor("");
		 }
		 
		 if(facilityDetailRequestDTO.getTenorDesc()!=null && !facilityDetailRequestDTO.getTenorDesc().trim().isEmpty()){
			 lmtDetailForm.setTenorDesc(facilityDetailRequestDTO.getTenorDesc().trim());
		 }else{
			 lmtDetailForm.setTenorDesc("");
		 }
		 if(facilityDetailRequestDTO.getMargin()!=null && !facilityDetailRequestDTO.getMargin().trim().isEmpty()){
			 lmtDetailForm.setMargin(facilityDetailRequestDTO.getMargin().trim());
		 }else{
			 lmtDetailForm.setMargin("");
		 }
		 if(facilityDetailRequestDTO.getPutCallOption()!=null && !facilityDetailRequestDTO.getPutCallOption().trim().isEmpty()){
			 lmtDetailForm.setPutCallOption(facilityDetailRequestDTO.getPutCallOption().trim());
		 }else{
			 lmtDetailForm.setPutCallOption("");
		 }
		 if(facilityDetailRequestDTO.getOptionDate()!=null && !facilityDetailRequestDTO.getOptionDate().trim().isEmpty()){
			 lmtDetailForm.setOptionDate(facilityDetailRequestDTO.getOptionDate().trim());
		 }else{
			 lmtDetailForm.setOptionDate("");
		 }
		 
		 if(facilityDetailRequestDTO.getLoanAvailabilityDate()!=null && !facilityDetailRequestDTO.getLoanAvailabilityDate().trim().isEmpty()){
			 lmtDetailForm.setLoanAvailabilityDate(facilityDetailRequestDTO.getLoanAvailabilityDate().trim());
		 }else{
			 lmtDetailForm.setLoanAvailabilityDate("");
		 }
		 
		 if(facilityDetailRequestDTO.getBankingArrangement()!=null && !facilityDetailRequestDTO.getBankingArrangement().trim().isEmpty()){
			 lmtDetailForm.setBankingArrangement(facilityDetailRequestDTO.getBankingArrangement().trim());
		 }else{
			 lmtDetailForm.setBankingArrangement("");
		 }
		 if(facilityDetailRequestDTO.getClauseAsPerPolicy()!=null && !facilityDetailRequestDTO.getClauseAsPerPolicy().trim().isEmpty()){
			 lmtDetailForm.setClauseAsPerPolicy(facilityDetailRequestDTO.getClauseAsPerPolicy().trim());
		 }else{
			 lmtDetailForm.setClauseAsPerPolicy("");
		 }
		 if(facilityDetailRequestDTO.getClimsFacilityId()!=null && !facilityDetailRequestDTO.getClimsFacilityId().trim().isEmpty()){
		 	lmtDetailForm.setClimsFacilityId(facilityDetailRequestDTO.getClimsFacilityId().trim());
		 }else{
			lmtDetailForm.setClimsFacilityId("");
		 }
		 return lmtDetailForm;
	}

	public FacilityBodyRestRequestDTO getActualVAluesFromInput(FacilityDetlRestRequestDTO facilityDetailsRequest) {
		//List<FacilityBodyRestRequestDTO> facilityBodyReqList = new LinkedList<FacilityBodyRestRequestDTO>();
		FacilityBodyRestRequestDTO reqObj= new  FacilityBodyRestRequestDTO();
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getFacilityCategoryId() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getFacilityCategoryId().isEmpty()) {
			reqObj.setFacilityCategoryId(facilityDetailsRequest.getBodyDetails().get(0).getFacilityCategoryId().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getFacilityTypeId() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getFacilityTypeId().isEmpty()) {
			reqObj.setFacilityTypeId(facilityDetailsRequest.getBodyDetails().get(0).getFacilityTypeId().trim());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getFacilityName() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getFacilityName().isEmpty()) {
			reqObj.setFacilityName(facilityDetailsRequest.getBodyDetails().get(0).getFacilityName());
		}
	
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getClimsFacilityId() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getClimsFacilityId().isEmpty()) {
			reqObj.setClimsFacilityId(facilityDetailsRequest.getBodyDetails().get(0).getClimsFacilityId().trim());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getMainFacilityId() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getMainFacilityId().isEmpty()) {
			reqObj.setMainFacilityId(facilityDetailsRequest.getBodyDetails().get(0).getMainFacilityId().trim());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getIsReleased() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getIsReleased().isEmpty()) {
			reqObj.setIsReleased(facilityDetailsRequest.getBodyDetails().get(0).getIsReleased().trim());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getSyndicateLoan() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getSyndicateLoan().isEmpty()) {
			reqObj.setSyndicateLoan(facilityDetailsRequest.getBodyDetails().get(0).getSyndicateLoan().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getDpRequired() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getDpRequired().isEmpty()) {
			reqObj.setDpRequired(facilityDetailsRequest.getBodyDetails().get(0).getDpRequired().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getIsDpCalulated() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getIsDpCalulated().isEmpty()) {
			reqObj.setIsDpCalulated(facilityDetailsRequest.getBodyDetails().get(0).getIsDpCalulated().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getRelationshipManager() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRelationshipManager().isEmpty()) {
			reqObj.setRelationshipManager(facilityDetailsRequest.getBodyDetails().get(0).getRelationshipManager().trim());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getIsReleased() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getIsReleased().isEmpty()) {
			reqObj.setIsReleased(facilityDetailsRequest.getBodyDetails().get(0).getIsReleased().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getBankingArrangement() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getBankingArrangement().isEmpty()) {
			reqObj.setBankingArrangement(facilityDetailsRequest.getBodyDetails().get(0).getBankingArrangement().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getClauseAsPerPolicy() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getClauseAsPerPolicy().isEmpty()) {
			reqObj.setClauseAsPerPolicy(facilityDetailsRequest.getBodyDetails().get(0).getClauseAsPerPolicy().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getGrade() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getGrade().isEmpty()) {
			reqObj.setGrade(facilityDetailsRequest.getBodyDetails().get(0).getGrade().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getSecured() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getSecured().isEmpty()) {
			reqObj.setSecured(facilityDetailsRequest.getBodyDetails().get(0).getSecured().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getCurrency() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getCurrency().isEmpty()) {
			reqObj.setCurrency(facilityDetailsRequest.getBodyDetails().get(0).getCurrency().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getIsAdhoc() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getIsAdhoc().isEmpty()) {
			reqObj.setIsAdhoc(facilityDetailsRequest.getBodyDetails().get(0).getIsAdhoc().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getIsAdhocToSanction() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getIsAdhocToSanction().isEmpty()) {
			reqObj.setIsAdhocToSanction(facilityDetailsRequest.getBodyDetails().get(0).getIsAdhocToSanction().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getAdhocLimit() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getAdhocLimit().isEmpty()) {
			reqObj.setAdhocLimit(facilityDetailsRequest.getBodyDetails().get(0).getAdhocLimit().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getSubLimitFlag() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getSubLimitFlag().isEmpty()) {
			reqObj.setSubLimitFlag(facilityDetailsRequest.getBodyDetails().get(0).getSubLimitFlag().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getFacilityNameGuar() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getFacilityNameGuar().isEmpty()) {
			reqObj.setFacilityNameGuar(facilityDetailsRequest.getBodyDetails().get(0).getFacilityNameGuar().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getGuaranteeFlag() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getGuaranteeFlag().isEmpty()) {
			reqObj.setGuaranteeFlag(facilityDetailsRequest.getBodyDetails().get(0).getGuaranteeFlag().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getPartyName() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getPartyName().isEmpty()) {
			reqObj.setPartyName(facilityDetailsRequest.getBodyDetails().get(0).getPartyName().trim());
		}
		

		if (facilityDetailsRequest.getBodyDetails().get(0).getLiabilityId() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getLiabilityId().isEmpty()) {
			reqObj.setLiabilityId(facilityDetailsRequest.getBodyDetails().get(0).getLiabilityId().trim());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getSanctionedAmount() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getSanctionedAmount().isEmpty()) {
			reqObj.setSanctionedAmount(facilityDetailsRequest.getBodyDetails().get(0).getSanctionedAmount().trim());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getReleasableAmount() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getReleasableAmount().isEmpty()) {
			reqObj.setReleasableAmount(facilityDetailsRequest.getBodyDetails().get(0).getReleasableAmount().trim());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getReleased() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getReleased().isEmpty()) {
			reqObj.setReleased(facilityDetailsRequest.getBodyDetails().get(0).getReleased().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getCamId() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getCamId().isEmpty()) {
			reqObj.setCamId(facilityDetailsRequest.getBodyDetails().get(0).getCamId().trim());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getSecurityList() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getSecurityList().isEmpty()
				&& facilityDetailsRequest.getBodyDetails().get(0).getSecurityList().size()>0) {
			reqObj.setSecurityList(facilityDetailsRequest.getBodyDetails().get(0).getSecurityList());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getLineList() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getLineList().isEmpty()
				&& facilityDetailsRequest.getBodyDetails().get(0).getLineList().size()>0) {
			reqObj.setLineList(facilityDetailsRequest.getBodyDetails().get(0).getLineList());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getIsScod() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getIsScod().isEmpty()) {
			reqObj.setIsScod(facilityDetailsRequest.getBodyDetails().get(0).getIsScod().trim());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getTenor()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getTenor().isEmpty()) {
			reqObj.setTenor(facilityDetailsRequest.getBodyDetails().get(0).getTenor().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getTenorVal()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getTenorVal().isEmpty()) {
			reqObj.setTenorVal(facilityDetailsRequest.getBodyDetails().get(0).getTenorVal().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getTenorDesc()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getTenorDesc().isEmpty()) {
			reqObj.setTenorDesc(facilityDetailsRequest.getBodyDetails().get(0).getTenorDesc().trim());
		}
		
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getBorrowerList() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getBorrowerList().isEmpty()
				&& facilityDetailsRequest.getBodyDetails().get(0).getBorrowerList().size()>0) {
			reqObj.setBorrowerList(facilityDetailsRequest.getBodyDetails().get(0).getBorrowerList());
		}

		// TODO Auto-generated method stub
		return reqObj;
	}

	public FacilitySCODDetailRestRequestDTO getActualVAluesFromInputSCOD(FacilityDetlRestRequestDTO facilityDetailsRequest)
	{
	
		FacilitySCODDetailRestRequestDTO reqObj = new FacilitySCODDetailRestRequestDTO();
		
		//SCOD fields
		if (facilityDetailsRequest.getBodyDetails().get(0).getCamId() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getCamId().isEmpty()) {
			reqObj.setCamId(facilityDetailsRequest.getBodyDetails().get(0).getCamId().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getCamType() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getCamType().isEmpty()) {
			reqObj.setCamType(facilityDetailsRequest.getBodyDetails().get(0).getCamType().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getProjectFinance() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getProjectFinance().isEmpty()) {
			reqObj.setProjectFinance(facilityDetailsRequest.getBodyDetails().get(0).getProjectFinance().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getProjectLoan() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getProjectLoan().isEmpty()) {
			reqObj.setProjectLoan(facilityDetailsRequest.getBodyDetails().get(0).getProjectLoan().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getInfraFlag() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getInfraFlag().isEmpty()) {
			reqObj.setInfraFlag(facilityDetailsRequest.getBodyDetails().get(0).getInfraFlag().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getScod() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getScod().isEmpty()) {
			reqObj.setScod(facilityDetailsRequest.getBodyDetails().get(0).getScod().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getScodRemark() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getScodRemark().isEmpty()) {
			reqObj.setScodRemark(facilityDetailsRequest.getBodyDetails().get(0).getScodRemark().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClass() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClass().isEmpty()) {
			reqObj.setExeAssetClass(facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClass().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassDate() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassDate().isEmpty()) {
			reqObj.setExeAssetClassDate(facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassDate().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getLimitReleaseFlg() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getLimitReleaseFlg().isEmpty()) {
			reqObj.setLimitReleaseFlg(facilityDetailsRequest.getBodyDetails().get(0).getLimitReleaseFlg().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getRepayChngSched() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRepayChngSched().isEmpty()) {
			reqObj.setRepayChngSched(facilityDetailsRequest.getBodyDetails().get(0).getRepayChngSched().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClass() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClass().isEmpty()) {
			reqObj.setRevAssetClass(facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClass().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassDt() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassDt().isEmpty()) {
			reqObj.setRevAssetClassDt(facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassDt().trim());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getAcod() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getAcod().isEmpty()) {
			reqObj.setAcod(facilityDetailsRequest.getBodyDetails().get(0).getAcod().trim());
		}	
		if (facilityDetailsRequest.getBodyDetails().get(0).getDelayFlagL1() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getDelayFlagL1().isEmpty()) {
			reqObj.setDelayFlagL1(facilityDetailsRequest.getBodyDetails().get(0).getDelayFlagL1().trim());
		}
		else {
			reqObj.setDelayFlagL1("N");
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getInterestFlag() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getInterestFlag().isEmpty()) {
			reqObj.setInterestFlag(facilityDetailsRequest.getBodyDetails().get(0).getInterestFlag().trim());
		}
		else {
			reqObj.setInterestFlag("N");
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getPriorReqFlag() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getPriorReqFlag().isEmpty()) {
			reqObj.setPriorReqFlag(facilityDetailsRequest.getBodyDetails().get(0).getPriorReqFlag().trim());
		}
		else {
			reqObj.setPriorReqFlag("N");
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getDelaylevel() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getDelaylevel().isEmpty()) {
			reqObj.setDelaylevel(facilityDetailsRequest.getBodyDetails().get(0).getDelaylevel().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getDefReasonL1() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getDefReasonL1().isEmpty()) {
			reqObj.setDefReasonL1(facilityDetailsRequest.getBodyDetails().get(0).getDefReasonL1().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getRepayChngSchedL1() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRepayChngSchedL1().isEmpty()) {
			reqObj.setRepayChngSchedL1(facilityDetailsRequest.getBodyDetails().get(0).getRepayChngSchedL1().trim());
		}
		else {
			reqObj.setRepayChngSchedL1("N");
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodL1() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodL1().isEmpty()) {
			reqObj.setEscodL1(facilityDetailsRequest.getBodyDetails().get(0).getEscodL1().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ1FlagL1() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ1FlagL1().isEmpty()) {
			reqObj.setOwnershipQ1FlagL1(facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ1FlagL1().trim());
		}
		else {
			reqObj.setOwnershipQ1FlagL1("N");
		} 
		if (facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ2FlagL1() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ2FlagL1().isEmpty()) {
			reqObj.setOwnershipQ2FlagL1(facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ2FlagL1().trim());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ3FlagL1() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ2FlagL1().isEmpty()) {
			reqObj.setOwnershipQ3FlagL1(facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ3FlagL1().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getScopeQ1FlagL1() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getScopeQ1FlagL1().isEmpty()) {
			reqObj.setScopeQ1FlagL1(facilityDetailsRequest.getBodyDetails().get(0).getScopeQ1FlagL1().trim());
		}
		else {
			reqObj.setScopeQ1FlagL1("N");
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getScopeQ2FlagL1() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getScopeQ2FlagL1().isEmpty()) {
			reqObj.setScopeQ2FlagL1(facilityDetailsRequest.getBodyDetails().get(0).getScopeQ2FlagL1().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getScopeQ3FlagL1() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getScopeQ3FlagL1().isEmpty()) {
			reqObj.setScopeQ3FlagL1(facilityDetailsRequest.getBodyDetails().get(0).getScopeQ3FlagL1().trim());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getRevisedEscodL1() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRevisedEscodL1().isEmpty()) {
			reqObj.setRevisedEscodL1(facilityDetailsRequest.getBodyDetails().get(0).getRevisedEscodL1().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassL1() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassL1().isEmpty()) {
			reqObj.setExeAssetClassL1(facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassL1().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassDtL1() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassDtL1().isEmpty()) {
			reqObj.setExeAssetClassDtL1(facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassDtL1().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassL1() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassL1().isEmpty()) {
			reqObj.setRevAssetClassL1(facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassL1().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassDtL1() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassDtL1().isEmpty()) {
			reqObj.setRevAssetClassDtL1(facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassDtL1().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getDelayFlagL2() != null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getDelayFlagL2().isEmpty()) {
			reqObj.setDelayFlagL2(facilityDetailsRequest.getBodyDetails().get(0).getDelayFlagL2().trim());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getDefReasonL2 ()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getDefReasonL2().isEmpty()) {
			reqObj.setDefReasonL2(facilityDetailsRequest.getBodyDetails().get(0).getDefReasonL2().trim());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getRepayChngSchedL2 ()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRepayChngSchedL2 ().isEmpty()) {
			reqObj.setRepayChngSchedL2 (facilityDetailsRequest.getBodyDetails().get(0).getRepayChngSchedL2 ().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodL2 ()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodL2 ().isEmpty()) {
			reqObj.setEscodL2 (facilityDetailsRequest.getBodyDetails().get(0).getEscodL2 ().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ1L2 ()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ1L2 ().isEmpty()) {
			reqObj.setEscodRevisionReasonQ1L2 (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ1L2  ().trim());
		}
		
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ2L2 ()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ2L2 ().isEmpty()) {
			reqObj.setEscodRevisionReasonQ2L2 (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ2L2  ().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ3L2 ()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ3L2 ().isEmpty()) {
			reqObj.setEscodRevisionReasonQ3L2 (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ3L2  ().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ4L2 ()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ4L2 ().isEmpty()) {
			reqObj.setEscodRevisionReasonQ4L2 (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ4L2  ().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ5L2 ()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ5L2 ().isEmpty()) {
			reqObj.setEscodRevisionReasonQ5L2 (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ5L2  ().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ6L2 ()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ6L2 ().isEmpty()) {
			reqObj.setEscodRevisionReasonQ6L2 (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ6L2  ().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getLegalDetailL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getLegalDetailL2 ().isEmpty()) {
			reqObj.setLegalDetailL2 (facilityDetailsRequest.getBodyDetails().get(0).getLegalDetailL2  ().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getBeyondControlL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getBeyondControlL2().isEmpty()) {
			reqObj.setBeyondControlL2(facilityDetailsRequest.getBodyDetails().get(0).getBeyondControlL2 ().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ1FlagL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ1FlagL2().isEmpty()) {
			reqObj.setOwnershipQ1FlagL2(facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ1FlagL2().trim());
		}
		else {
			reqObj.setOwnershipQ1FlagL2("N");
		} 
		if (facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ2FlagL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ2FlagL2().isEmpty()) {
			reqObj.setOwnershipQ2FlagL2(facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ2FlagL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ3FlagL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ3FlagL2().isEmpty()) {
			reqObj.setOwnershipQ3FlagL2(facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ3FlagL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getScopeQ1FlagL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getScopeQ1FlagL2().isEmpty()) {
			reqObj.setScopeQ1FlagL2(facilityDetailsRequest.getBodyDetails().get(0).getScopeQ1FlagL2().trim());
		}
		else {
			reqObj.setScopeQ1FlagL2("N");
		} 
		if (facilityDetailsRequest.getBodyDetails().get(0).getScopeQ2FlagL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getScopeQ2FlagL2().isEmpty()) {
			reqObj.setScopeQ2FlagL2(facilityDetailsRequest.getBodyDetails().get(0).getScopeQ2FlagL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getScopeQ3FlagL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getScopeQ3FlagL2().isEmpty()) {
			reqObj.setScopeQ3FlagL2(facilityDetailsRequest.getBodyDetails().get(0).getScopeQ3FlagL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getRevisedEscodL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRevisedEscodL2().isEmpty()) {
			reqObj.setRevisedEscodL2(facilityDetailsRequest.getBodyDetails().get(0).getRevisedEscodL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassL2().isEmpty()) {
			reqObj.setExeAssetClassL2(facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassDtL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassDtL2().isEmpty()) {
			reqObj.setExeAssetClassDtL2(facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassDtL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassL2().isEmpty()) {
			reqObj.setRevAssetClassL2(facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassDtL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassDtL2().isEmpty()) {
			reqObj.setRevAssetClassDtL2(facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassDtL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getDelayFlagL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getDelayFlagL3().isEmpty()) {
			reqObj.setDelayFlagL3(facilityDetailsRequest.getBodyDetails().get(0).getDelayFlagL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getDefReasonL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getDefReasonL3().isEmpty()) {
			reqObj.setDefReasonL3(facilityDetailsRequest.getBodyDetails().get(0).getDefReasonL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getRepayChngSchedL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRepayChngSchedL3().isEmpty()) {
			reqObj.setRepayChngSchedL3(facilityDetailsRequest.getBodyDetails().get(0).getRepayChngSchedL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodL3().isEmpty()) {
			reqObj.setEscodL3(facilityDetailsRequest.getBodyDetails().get(0).getEscodL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ1L3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ1L3().isEmpty()) {
			reqObj.setEscodRevisionReasonQ1L3(facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ1L3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ2L3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ2L3().isEmpty()) {
			reqObj.setEscodRevisionReasonQ2L3(facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ2L3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ3L3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ3L3().isEmpty()) {
			reqObj.setEscodRevisionReasonQ3L3(facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ3L3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ4L3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ4L3().isEmpty()) {
			reqObj.setEscodRevisionReasonQ4L3(facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ4L3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ5L3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ5L3().isEmpty()) {
			reqObj.setEscodRevisionReasonQ5L3(facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ5L3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ6L3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ6L3().isEmpty()) {
			reqObj.setEscodRevisionReasonQ6L3(facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ6L3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getLegalDetailL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getLegalDetailL2().isEmpty()) {
			reqObj.setLegalDetailL2(facilityDetailsRequest.getBodyDetails().get(0).getLegalDetailL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getBeyondControlL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getBeyondControlL2().isEmpty()) {
			reqObj.setBeyondControlL2(facilityDetailsRequest.getBodyDetails().get(0).getBeyondControlL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ1FlagL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ1FlagL2().isEmpty()) {
			reqObj.setOwnershipQ1FlagL2(facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ1FlagL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ2FlagL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ2FlagL2().isEmpty()) {
			reqObj.setOwnershipQ2FlagL2(facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ2FlagL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ3FlagL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ2FlagL2().isEmpty()) {
			reqObj.setOwnershipQ3FlagL2(facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ3FlagL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getScopeQ1FlagL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getScopeQ1FlagL2().isEmpty()) {
			reqObj.setScopeQ1FlagL2(facilityDetailsRequest.getBodyDetails().get(0).getScopeQ1FlagL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getScopeQ2FlagL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getScopeQ2FlagL2().isEmpty()) {
			reqObj.setScopeQ2FlagL2(facilityDetailsRequest.getBodyDetails().get(0).getScopeQ2FlagL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getScopeQ3FlagL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getScopeQ3FlagL2().isEmpty()) {
			reqObj.setScopeQ3FlagL2(facilityDetailsRequest.getBodyDetails().get(0).getScopeQ3FlagL2().trim());
		}


		if (facilityDetailsRequest.getBodyDetails().get(0).getRevisedEscodL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRevisedEscodL2().isEmpty()) {
			reqObj.setRevisedEscodL2(facilityDetailsRequest.getBodyDetails().get(0).getRevisedEscodL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassL2().isEmpty()) {
			reqObj.setExeAssetClassL2(facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassDtL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassDtL2().isEmpty()) {
			reqObj.setExeAssetClassDtL2(facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassDtL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassL2().isEmpty()) {
			reqObj.setRevAssetClassL2(facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassDtL2()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassDtL2().isEmpty()) {
			reqObj.setRevAssetClassDtL2(facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassDtL2().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getDelayFlagL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getDelayFlagL3().isEmpty()) {
			reqObj.setDelayFlagL3(facilityDetailsRequest.getBodyDetails().get(0).getDelayFlagL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getDefReasonL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getDefReasonL3().isEmpty()) {
			reqObj.setDefReasonL3(facilityDetailsRequest.getBodyDetails().get(0).getDefReasonL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getRepayChngSchedL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRepayChngSchedL3().isEmpty()) {
			reqObj.setRepayChngSchedL3(facilityDetailsRequest.getBodyDetails().get(0).getRepayChngSchedL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodL3().isEmpty()) {
			reqObj.setEscodL3(facilityDetailsRequest.getBodyDetails().get(0).getEscodL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ1L3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ1L3().isEmpty()) {
			reqObj.setEscodRevisionReasonQ1L3(facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ1L3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ2L3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ2L3().isEmpty()) {
			reqObj.setEscodRevisionReasonQ2L3(facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ2L3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ3L3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ3L3().isEmpty()) {
			reqObj.setEscodRevisionReasonQ3L3(facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ3L3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ4L3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ4L3().isEmpty()) {
			reqObj.setEscodRevisionReasonQ4L3(facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ4L3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ5L3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ5L3().isEmpty()) {
			reqObj.setEscodRevisionReasonQ5L3(facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ5L3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ6L3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ1L3().isEmpty()) {
			reqObj.setEscodRevisionReasonQ6L3(facilityDetailsRequest.getBodyDetails().get(0).getEscodRevisionReasonQ6L3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getLegalDetailL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getLegalDetailL3().isEmpty()) {
			reqObj.setLegalDetailL3(facilityDetailsRequest.getBodyDetails().get(0).getLegalDetailL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getBeyondControlL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getBeyondControlL3().isEmpty()) {
			reqObj.setBeyondControlL3(facilityDetailsRequest.getBodyDetails().get(0).getBeyondControlL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ1FlagL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ1FlagL3().isEmpty()) {
			reqObj.setOwnershipQ1FlagL3(facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ1FlagL3().trim());
		}
		else {
			reqObj.setOwnershipQ1FlagL3("N");
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ2FlagL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ2FlagL3().isEmpty()) {
			reqObj.setOwnershipQ2FlagL3(facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ2FlagL3().trim());
		} 
		if (facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ3FlagL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ3FlagL3().isEmpty()) {
			reqObj.setOwnershipQ3FlagL3(facilityDetailsRequest.getBodyDetails().get(0).getOwnershipQ3FlagL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getScopeQ1FlagL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getScopeQ1FlagL3().isEmpty()) {
			reqObj.setScopeQ1FlagL3(facilityDetailsRequest.getBodyDetails().get(0).getScopeQ1FlagL3().trim());
		}
		else {
			reqObj.setScopeQ1FlagL3("N");
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getScopeQ2FlagL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getScopeQ2FlagL3().isEmpty()) {
			reqObj.setScopeQ2FlagL3(facilityDetailsRequest.getBodyDetails().get(0).getScopeQ2FlagL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getScopeQ3FlagL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getScopeQ3FlagL3().isEmpty()) {
			reqObj.setScopeQ3FlagL3(facilityDetailsRequest.getBodyDetails().get(0).getScopeQ3FlagL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getRevisedEscodL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRevisedEscodL3().isEmpty()) {
			reqObj.setRevisedEscodL3(facilityDetailsRequest.getBodyDetails().get(0).getRevisedEscodL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassL3().isEmpty()) {
			reqObj.setExeAssetClassL3(facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassDtL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassDtL3().isEmpty()) {
			reqObj.setExeAssetClassDtL3(facilityDetailsRequest.getBodyDetails().get(0).getExeAssetClassDtL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassL3().isEmpty()) {
			reqObj.setRevAssetClassL3(facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassDtL3()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassDtL3().isEmpty()) {
			reqObj.setRevAssetClassDtL3(facilityDetailsRequest.getBodyDetails().get(0).getRevAssetClassDtL3().trim());
		}
		if (facilityDetailsRequest.getBodyDetails().get(0).getFacilityMasterId()!= null
				&& !facilityDetailsRequest.getBodyDetails().get(0).getFacilityMasterId().isEmpty()) {
			reqObj.setFacilityMasterId(facilityDetailsRequest.getBodyDetails().get(0).getFacilityMasterId().trim());
		}
		
		
		return reqObj;
	}

	public String getFlagValueString(String Flagvalue) {
		
		
		List<String> values = new ArrayList<String>();
		String strr[] = Flagvalue.split(",");
		for (String val : strr )
		{
			val =val.concat("#A");
			values.add(val);
		}
		StringBuilder str1 = new StringBuilder("");
		 
        for (String eachstring : values) {

        	str1.append(eachstring).append(",");
        }
 
        String commaseparatedlist = str1.toString();
 
        // Condition check to remove the last comma
        if (commaseparatedlist.length() > 0)
        {
           
            commaseparatedlist
                = commaseparatedlist.substring(
                    0, commaseparatedlist.length() - 1);
        }
 
        // Printing the comma separated string
        //System.out.println("finalllll "+commaseparatedlist);
       
		
       
		return commaseparatedlist;
	}

	public OBCustomerSysXRef getActualLineValFromRestDTO(FacilityLineDetailRestRequestDTO lineDTO, LmtDetailForm facilityForm, OBCustomerSysXRef xrefobj) {
		// TODO Auto-generated method stub
		FacilityDetailsDTOMapper facilityDetailsDTOMapper = new FacilityDetailsDTOMapper();
		UdfDetailsRestDTOMapper udfDetailsRestDTOMapper=new UdfDetailsRestDTOMapper();
		IGeneralParamProxy generalParamProxy =(IGeneralParamProxy)BeanHouse.get("generalParamProxy");

		xrefobj.setFacilitySystemID(lineDTO.getSystemId());
		xrefobj.setFacilitySystem(facilityForm.getFacilitySystem());					
		xrefobj.setLiabBranch(lineDTO.getLiabBranch());
		xrefobj.setLineNo(facilityForm.getLineNo());
		if("Y".equals(lineDTO.getNewLine()))
		{
			xrefobj.setAction("NEW");
			// set flags
			if (null != lineDTO.getSegment1()) {
				xrefobj.setSegment1Flag(ICMSConstant.FCUBS_ADD);
			}
			if (null != lineDTO.getPslFlag() && null != lineDTO.getPslValue()) {
				xrefobj.setPrioritySectorFlag(ICMSConstant.FCUBS_ADD);
			}
			if (null != lineDTO.getCapitalMarketExposure()) {
				xrefobj.setIsCapitalMarketExposerFlag(ICMSConstant.FCUBS_ADD);
			}
			if (null != lineDTO.getRealEstateExposure() && "Yes".equals(lineDTO.getRealEstateExposure())) {
				xrefobj.setEstateTypeFlag(ICMSConstant.FCUBS_ADD);
			}

			if ("Yes".equals(lineDTO.getRealEstateExposure())
					&& "Commercial Real estate".equals(lineDTO.getRealEstate())) {
				xrefobj.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_ADD);
			}
			if (null != lineDTO.getUncondiCanclCommit()) {
				xrefobj.setUncondiCanclFlag(ICMSConstant.FCUBS_ADD);
			}
			
			String fcunsSystem=PropertyManager.getValue("fcubs.systemName");
			String ubslimitSystem=PropertyManager.getValue("ubs.systemName");
			if(fcunsSystem.equalsIgnoreCase(xrefobj.getFacilitySystem()) || ubslimitSystem.equalsIgnoreCase(xrefobj.getFacilitySystem()))
			{
				Calendar c =Calendar.getInstance();
				String serialNo=""+c.get(Calendar.DAY_OF_MONTH)+c.get(Calendar.MONTH)+c.get(Calendar.HOUR)+c.get(Calendar.MILLISECOND);

				xrefobj.setSerialNo(serialNo);
				xrefobj.setHiddenSerialNo(serialNo);
			}
			else
			{
				if(lineDTO.getSerialNo()!=null && !lineDTO.getSerialNo().trim().isEmpty()){
					xrefobj.setSerialNo(lineDTO.getSerialNo().trim());
					xrefobj.setHiddenSerialNo(lineDTO.getSerialNo());
				}
				else{
					xrefobj.setSerialNo("1");
					xrefobj.setHiddenSerialNo("1");
				}
			}
			
			
			// End set flag
		}
		else
		{
			xrefobj.setAction("MODIFY");
			// set flags
			if(null!=lineDTO.getSegment1()){
				if(!lineDTO.getSegment1().equals(xrefobj.getSegment())){
					xrefobj.setSegment1Flag(ICMSConstant.FCUBS_MODIFY);
				}
			}
			
			if(null!=lineDTO.getPslFlag()){
				if(!lineDTO.getPslFlag().equals(xrefobj.getIsPrioritySector())){
					xrefobj.setPrioritySectorFlag(ICMSConstant.FCUBS_MODIFY);
				}else if(null != lineDTO.getPslValue()){
					if(!lineDTO.getPslValue().equals(xrefobj.getPrioritySector())){
						xrefobj.setPrioritySectorFlag(ICMSConstant.FCUBS_MODIFY);
					}
				}
			}
			
			if(null!=lineDTO.getCapitalMarketExposure()){
				if(!lineDTO.getCapitalMarketExposure().equals(xrefobj.getIsCapitalMarketExposer())){
					xrefobj.setIsCapitalMarketExposerFlag(ICMSConstant.FCUBS_MODIFY);
				}
			}
			
			if (null != lineDTO.getRealEstateExposure()) {
				if (!lineDTO.getRealEstateExposure().equals(xrefobj.getIsRealEstateExposer())) {
					if ("No".equals(lineDTO.getRealEstateExposure())) {
						xrefobj.setEstateTypeFlag(ICMSConstant.FCUBS_DELETE);
						xrefobj.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_DELETE);
					} else if ("Yes".equals(lineDTO.getRealEstateExposure())) {
						xrefobj.setEstateTypeFlag(ICMSConstant.FCUBS_ADD);
						if ("Commercial Real estate".equals(lineDTO.getRealEstate())) {
							xrefobj.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_ADD);
						}
					}
				} else {
					if ("Yes".equals(lineDTO.getRealEstateExposure())) {
						if (null != lineDTO.getRealEstate() && !lineDTO.getRealEstate().equals(xrefobj.getEstateType())) {
							xrefobj.setEstateTypeFlag(ICMSConstant.FCUBS_MODIFY);
							if ("Commercial Real estate".equals(lineDTO.getRealEstate())) {
								xrefobj.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_ADD);
							} else if ("Commercial Real estate".equals(xrefobj.getEstateType())
									&& null == lineDTO.getCommRealEstate() && null != xrefobj.getCommRealEstateType()) {
								xrefobj.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_DELETE);
							}
						} else if (null != lineDTO.getRealEstate() && lineDTO.getRealEstate().equals(xrefobj.getEstateType())
								&& "Commercial Real estate".equals(lineDTO.getRealEstate())) {
							if (null != lineDTO.getCommRealEstate()
									&& !lineDTO.getCommRealEstate().equals(xrefobj.getCommRealEstateType())) {
								xrefobj.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_MODIFY);
							}

						}
					}
				}
			}
			
			if (null != lineDTO.getUncondiCanclCommit()) {
				if (!lineDTO.getUncondiCanclCommit().equals(xrefobj.getUncondiCancl())) {
					xrefobj.setUncondiCanclFlag(ICMSConstant.FCUBS_MODIFY);
				}
			}
			//end flags
			
			
		}
		
		//set source ref number
		ILimitDAO dao1 = LimitDAOFactory.getDAO();
		Date d = DateUtil.getDate();
		String dateFormat = "yyMMdd";
		SimpleDateFormat s=new SimpleDateFormat(dateFormat);
		String date = s.format(d);
		String tempSourceRefNo="";
		tempSourceRefNo=""+dao1.generateSourceSeqNo();
		int len=tempSourceRefNo.length();
		String concatZero="";
		if(null!=tempSourceRefNo && len!=5){
			for(int m=5;m>len;m--){
				concatZero="0"+concatZero;
			}
		}
		tempSourceRefNo=concatZero+tempSourceRefNo;
		String sorceRefNo=ICMSConstant.FCUBS_CAD+date+tempSourceRefNo;	
		DefaultLogger.debug(this, "getActualLineValFromRestDTO()...sorceRefNo: ========================="+sorceRefNo);
		xrefobj.setSourceRefNo(sorceRefNo);
		//end source ref number
		
		xrefobj.setStatus("PENDING");
		xrefobj.setUtilizedAmount("0");
		/*if(lineDTO.getSerialNo()!=null && !lineDTO.getSerialNo().trim().isEmpty()){
			xrefobj.setSerialNo(lineDTO.getSerialNo().trim());
			xrefobj.setHiddenSerialNo(lineDTO.getSerialNo());
		}else{
			xrefobj.setSerialNo("1");
			xrefobj.setHiddenSerialNo("1");
		}*/
		//xrefobj.setSerialNo(lineDTO.getSerialNo());
		//xrefobj.setHiddenSerialNo(lineDTO.getSerialNo());
		xrefobj.setCreatedBy("CPUADM_A");
		xrefobj.setUpdatedBy("CPUADM_C");
		if(lineDTO.getIsCurrencyRestriction()!=null && !lineDTO.getIsCurrencyRestriction().trim().isEmpty()){
			xrefobj.setCurrencyRestriction(lineDTO.getIsCurrencyRestriction());
		}else {
			xrefobj.setCurrencyRestriction("N");
		}
		
		xrefobj.setCurrency(facilityForm.getCurrencyCode()); 
		xrefobj.setMainLineCode(lineDTO.getMainLineCode()); 
		//xrefobj.setUtilizedAmount(lineDTO.getUtilizedAmount()); 
		xrefobj.setReleasedAmount(lineDTO.getReleasedAmount()); 
		try {
			xrefobj.setReleaseDate(new SimpleDateFormat("dd/MMM/yyyy").parse(lineDTO.getReleasedDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xrefobj.setSendToFile(lineDTO.getSendToFile()); 
		try {
			xrefobj.setLimitStartDate(new SimpleDateFormat("dd/MMM/yyyy").parse(lineDTO.getLmtStartDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			xrefobj.setDateOfReset(new SimpleDateFormat("dd/MMM/yyyy").parse(lineDTO.getLmtExpiryDate()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			xrefobj.setIntradayLimitExpiryDate(new SimpleDateFormat("dd/MMM/yyyy").parse(lineDTO.getIntradayLimitExpDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		xrefobj.setDayLightLimit(lineDTO.getDayLightLimit()); 
	    xrefobj.setAvailable(lineDTO.getIsAvailable());
		xrefobj.setFreeze(lineDTO.getFreeze());
		xrefobj.setRevolvingLine(lineDTO.getRevolvingLine()) ;
		xrefobj.setScmFlag(lineDTO.getScmFlag());
		try {
			xrefobj.setLastavailableDate(new SimpleDateFormat("dd/MMM/yyyy").parse(lineDTO.getLastAvailDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			xrefobj.setUploadDate(new SimpleDateFormat("dd/MMM/yyyy").parse(lineDTO.getUploadDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		xrefobj.setSegment(lineDTO.getSegment1()); 
		xrefobj.setRuleId(lineDTO.getRuleId());  
		xrefobj.setIsCapitalMarketExposer(lineDTO.getCapitalMarketExposure()); 
		//xrefobj.setIsRealEstateExposer(lineDTO.getRealEstateExposure());
		if(lineDTO.getRealEstateExposure()!=null && !lineDTO.getRealEstateExposure().trim().isEmpty()){
			xrefobj.setIsRealEstateExposer(lineDTO.getRealEstateExposure().trim());
		}else{
			xrefobj.setIsRealEstateExposer("No");

		}
		xrefobj.setUncondiCancl(lineDTO.getUncondiCanclCommit());
		xrefobj.setIntRateFix(lineDTO.getIntrestRate());
		if(lineDTO.getRealEstateExposure()!=null && !lineDTO.getRealEstateExposure().trim().isEmpty()
				&& "Yes".equals(lineDTO.getRealEstateExposure()))
		{
			xrefobj.setEstateType(lineDTO.getRealEstate());
			xrefobj.setCommRealEstateType(lineDTO.getCommRealEstate());
		}
//		xrefobj.setIsDayLightLimitAvailable(lineDTO.getDayLightLmtAvail());
		if(lineDTO.getDayLightLmtAvail()!=null && !lineDTO.getDayLightLmtAvail().trim().isEmpty()){
			xrefobj.setIsDayLightLimitAvailable(lineDTO.getDayLightLmtAvail().trim());
		}else{
			xrefobj.setIsDayLightLimitAvailable("No");

		}
		xrefobj.setDayLightLimitApproved(lineDTO.getDayLightLmtAppr());
		xrefobj.setLimitTenorDays(lineDTO.getLimiIndays());
		xrefobj.setCloseFlag(lineDTO.getClosedFlag());
		xrefobj.setBankingArrangement(facilityForm.getBankingArrangement());
		xrefobj.setVendorDtls(lineDTO.getVendorDtls()); 
		xrefobj.setLimitRestriction(lineDTO.getLimitCustomerRestrict()); 
		xrefobj.setSecurity1(lineDTO.getSecurity1());
		xrefobj.setSecurity2(lineDTO.getSecurity2());
		xrefobj.setSecurity3(lineDTO.getSecurity3());
		xrefobj.setSecurity4(lineDTO.getSecurity4());
		xrefobj.setSecurity5(lineDTO.getSecurity5());
		xrefobj.setSecurity6(lineDTO.getSecurity6());
		xrefobj.setInternalRemarks(lineDTO.getInternalRemarks());
		//xrefobj.setPrioritySectorFlag(lineDTO.getPslFlag());
		xrefobj.setIsPrioritySector(lineDTO.getPslFlag());
		xrefobj.setPrioritySector(lineDTO.getPslValue());
		xrefobj.setInterestRateType(lineDTO.getIntrestRateType());
		xrefobj.setIntRateFloatingMarginFlag((lineDTO.getMarginAddSub()));
		xrefobj.setSendToCore("N");
//		xrefobj.setIntRateFloatingRange("17.84");
		
		
		try {
			xrefobj.setIdlEffectiveFromDate(new SimpleDateFormat("dd/MMM/yyyy").parse(lineDTO.getIdlEffectiveFromDate()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		try {
			
			Calendar date3 = Calendar.getInstance();
			date3.setTime(xrefobj.getIdlEffectiveFromDate());
			date3.add(Calendar.DATE,1);
			xrefobj.setIdlExpiryDate(date3.getTime());
			
			//xrefobj.setIdlExpiryDate(new SimpleDateFormat("dd/MMM/yyyy").parse(lineDTO.getIdlExpiryDate()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("FacilityDetailsDTOMapper.java=>xrefobj.getIDLExpiryDate()=>"+xrefobj.getIdlExpiryDate());
		xrefobj.setIdlAmount(lineDTO.getIdlAmount());
		
		//if floating
		if (null != lineDTO.getIntrestRateType() && !lineDTO.getIntrestRateType().isEmpty()) {
			if ("floating".equals(lineDTO.getIntrestRateType())) {
				IGeneralParamEntry generalParamEntryBase = generalParamProxy.getGeneralParamEntryByParamCodeActual(IGeneralParamEntry.BASE_INT_RATE);

				if (null != lineDTO.getFloatingRateType() && !lineDTO.getFloatingRateType().isEmpty()) {
					if ("BASE".equals(lineDTO.getFloatingRateType())) {
						if (generalParamEntryBase != null) {
							String strBASE = generalParamEntryBase.getParamValue();
							xrefobj.setIntRateFloatingRange(strBASE);
						}
					}

					if ("BPLR".equals(lineDTO.getFloatingRateType())) {
						IGeneralParamEntry generalParamEntryBPLR = generalParamProxy.getGeneralParamEntryByParamCodeActual(IGeneralParamEntry.BPLR_INT_RATE);
						if (generalParamEntryBPLR != null) {
							String strBPLR = generalParamEntryBPLR.getParamValue();
							xrefobj.setIntRateFloatingRange(strBPLR);
						}
					}
				}
			}
		}
		
		xrefobj.setIntRateFloatingMargin(lineDTO.getMargin());
		
		if((lineDTO.getLineCoborrowerList()!=null && !lineDTO.getLineCoborrowerList().isEmpty() 
				&& lineDTO.getLineCoborrowerList().size()>0)){

			xrefobj.setRestCoBorrowerList(lineDTO.getLineCoborrowerList());
			List coBorrowerList1 = lineDTO.getLineCoborrowerList();
			//OBLimitXRefCoBorrower form1 = new OBLimitXRefCoBorrower();
			int cnt= coBorrowerList1.size();
			ILimitXRefCoBorrower coBorrowerList[] = new ILimitXRefCoBorrower[cnt];


			for(int i=0; i<coBorrowerList1.size(); i++)
			{
				if(null != lineDTO.getLineCoborrowerList().get(i).getCoBorrowerLiabId() && 
						!lineDTO.getLineCoborrowerList().get(i).getCoBorrowerLiabId().isEmpty() &&
						null != lineDTO.getLineCoborrowerList().get(i).getCoBorrowerName() && 
						!lineDTO.getLineCoborrowerList().get(i).getCoBorrowerName().isEmpty())
				{
					ILimitXRefCoBorrower coBorro = new OBLimitXRefCoBorrower();

					coBorro.setCoBorrowerId(lineDTO.getLineCoborrowerList().get(i).getCoBorrowerLiabId());
					coBorro.setCoBorrowerName(lineDTO.getLineCoborrowerList().get(i).getCoBorrowerName());

					coBorrowerList[i] = coBorro;
				}
			}
			//    System.out.println("coBorrowerList======== "+coBorrowerList);
			if(null !=coBorrowerList)
			xrefobj.setXRefCoBorrowerData(coBorrowerList);





		}
		if(lineDTO.getProductAllowed()!=null && !lineDTO.getProductAllowed().trim().isEmpty()){
			xrefobj.setProductAllowed(lineDTO.getProductAllowed().trim());
			xrefobj.setProductAllowedFlag(facilityDetailsDTOMapper.getFlagValueString(lineDTO.getProductAllowed().trim()));
		}
		if(lineDTO.getBranchAllowed()!=null && !lineDTO.getBranchAllowed().trim().isEmpty()){
			xrefobj.setBranchAllowed(lineDTO.getBranchAllowed().trim());
			xrefobj.setBranchAllowedFlag(facilityDetailsDTOMapper.getFlagValueString(lineDTO.getBranchAllowed().trim()));
		}
		if(lineDTO.getCurrencyAllowed()!=null && !lineDTO.getCurrencyAllowed().trim().isEmpty()){
			xrefobj.setCurrencyAllowed(lineDTO.getCurrencyAllowed().trim());
			xrefobj.setCurrencyAllowedFlag(facilityDetailsDTOMapper.getFlagValueString(lineDTO.getCurrencyAllowed().trim()));
		}
		if((lineDTO.getUdfList()!=null && !lineDTO.getUdfList().isEmpty() && lineDTO.getUdfList().size()>0)){

			List<String>sequesnceId= new ArrayList<String>();
			String sequence1="";
			
			StringBuilder str1 = new StringBuilder("");	

			
				for (int i = 0;i<lineDTO.getUdfList().size();i++){

					if("3".equals(lineDTO.getUdfList().get(i).getModuleId()))
					{
						sequesnceId.add(lineDTO.getUdfList().get(i).getUdfSequance());
						str1.append(lineDTO.getUdfList().get(i).getUdfSequance()).append(",");
					}
				}
			
		

			sequence1 = str1.toString();
			// Condition check to remove the last comma
			if (sequence1.length() > 0)
			{ 
				sequence1
				= sequence1.substring(
						0, sequence1.length() - 1);
			}
			
			xrefobj.setUdfAllowed(sequence1);	
			
			xrefobj.setXRefUdfData((ILimitXRefUdf[])udfDetailsRestDTOMapper.getUdfActualFromDTO(lineDTO, "WS_LINE_CREATE_REST_UDF1"));
		}
		
		
		if(lineDTO.getUdf2List()!=null && !lineDTO.getUdf2List().isEmpty() && lineDTO.getUdf2List().size()>0){

			List<String>sequesnceId2= new ArrayList<String>();

			String sequence2="";
			StringBuilder str2 = new StringBuilder("");	
			

			for (int i = 0;i<lineDTO.getUdf2List().size();i++){

				if("4".equals(lineDTO.getUdf2List().get(i).getModuleId()))
				{
					sequesnceId2.add(lineDTO.getUdf2List().get(i).getUdfSequance());
					str2.append(lineDTO.getUdf2List().get(i).getUdfSequance()).append(",");
				}
			}

			sequence2 = str2.toString();

			if (sequence2.length() > 0)
			{ 
				sequence2
				= sequence2.substring(
						0, sequence2.length() - 1);
			}

		
			xrefobj.setUdfAllowed_2(sequence2);
		
			xrefobj.setXRefUdfData2((ILimitXRefUdf2[])udfDetailsRestDTOMapper.getUdfActualFromDTO(lineDTO, "WS_LINE_CREATE_REST_UDF2"));
		}
		return xrefobj;
	}
	
	public ILimit  getActualUpdateFromRestDTO( FacilityBodyRestRequestDTO dto, ILimit lmtObj ) {

		
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd/MMM/yyyy");

//		MILimitUIHelper helper = new MILimitUIHelper();
//		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		
//		String facilityMasterId = "";
		if(dto.getFacilityCategoryId()!=null && !dto.getFacilityCategoryId().trim().isEmpty()){
			lmtObj.setFacilityCat(dto.getFacilityCategoryId());
		}
		if(dto.getFacilityTypeId()!=null && !dto.getFacilityTypeId().trim().isEmpty()){
			lmtObj.setFacilityType(dto.getFacilityTypeId());		
		}
		
		if(dto.getFacilityName()!=null && !dto.getFacilityName().trim().isEmpty()){
			lmtObj.setFacilityName(dto.getFacilityName());		
		}
		
//			lmtObj.setFacilityName(master.getNewFacilityName());		
//			lmtObj.setFacilityCode(master.getNewFacilityCode());
//			lmtObj.setFacilitySystem(master.getNewFacilitySystem());			
//			lmtObj.setLineNo(master.getLineNumber());		
//			lmtObj.setPurpose(master.getPurpose());		
//		}
		if(dto.getIsReleased()!=null && !dto.getIsReleased().trim().isEmpty()){
			lmtObj.setIsReleased(dto.getIsReleased().trim());
		}else{
			lmtObj.setIsReleased("N");
		}
		
		if(dto.getBankingArrangement()!=null && !dto.getBankingArrangement().trim().isEmpty()){
			lmtObj.setBankingArrangement(dto.getBankingArrangement().trim());
		}else{
			lmtObj.setBankingArrangement("");
		}
		
		if(dto.getClauseAsPerPolicy()!=null && !dto.getClauseAsPerPolicy().trim().isEmpty()){
			lmtObj.setClauseAsPerPolicy(dto.getClauseAsPerPolicy().trim());
		}else{
			lmtObj.setClauseAsPerPolicy("");
		}
		
		if(dto.getRelationshipManager()!=null && !dto.getRelationshipManager().trim().isEmpty()){
			lmtObj.setRelationShipManager(dto.getRelationshipManager().trim());
		}else{
			lmtObj.setRelationShipManager("");
		}
		
		if(dto.getSecured()!=null && !dto.getSecured().trim().isEmpty()){
			lmtObj.setIsSecured(dto.getSecured().trim());
		}else{
			lmtObj.setIsSecured("");
		}
		
		if(dto.getGrade()!=null && !dto.getGrade().trim().isEmpty()){
			lmtObj.setGrade(dto.getGrade().trim());		
		}else{
			lmtObj.setGrade("");		
		}
		
		if(dto.getSyndicateLoan()!=null && !dto.getSyndicateLoan().trim().isEmpty()){
			lmtObj.setSyndicateLoan(dto.getSyndicateLoan().trim());
		}else{
			lmtObj.setSyndicateLoan("N");
		}
		if(dto.getDpRequired()!=null && !dto.getDpRequired().trim().isEmpty()){
			lmtObj.setIsDPRequired(dto.getDpRequired().trim());		
		}else{
			lmtObj.setIsDPRequired("");		
		}
		
		if(dto.getIsDpCalulated()!=null && !dto.getIsDpCalulated().trim().isEmpty()){
			lmtObj.setIsDP(dto.getIsDpCalulated().trim());		
		}else{
			lmtObj.setIsDP("Y");		
		}
		
		if(dto.getIsAdhoc()!=null && !dto.getIsAdhoc().trim().isEmpty()){
			lmtObj.setIsAdhoc(dto.getIsAdhoc().trim());		
		}else{
			lmtObj.setIsAdhoc("N");		
		}
		if(dto.getIsAdhocToSanction()!=null && !dto.getIsAdhocToSanction().trim().isEmpty()){
			lmtObj.setIsAdhocToSum(dto.getIsAdhocToSanction().trim());		
		}else{
			lmtObj.setIsAdhocToSum("N");		
		}
		if(dto.getAdhocLimit()!=null && !dto.getAdhocLimit().trim().isEmpty()){
			lmtObj.setAdhocLmtAmount(dto.getAdhocLimit().trim());		
		}else{
			lmtObj.setAdhocLmtAmount("");		
		}
		
		//lmtObj.setIsAdhoc("N");		
		//lmtObj.setIsAdhocToSum("N");
		if(dto.getCurrency()!=null && !dto.getCurrency().trim().isEmpty()){
			lmtObj.setCurrencyCode(dto.getCurrency().trim());		
		}else{
			lmtObj.setCurrencyCode("INR");		
		}
		
		if(dto.getGuaranteeFlag()!=null && !dto.getGuaranteeFlag().trim().isEmpty()){
			lmtObj.setGuarantee(dto.getGuaranteeFlag().trim());		
		}else{
			lmtObj.setGuarantee("No");		
		}
		if(dto.getSubLimitFlag()!=null && !dto.getSubLimitFlag().trim().isEmpty()){
			lmtObj.setLimitType(dto.getSubLimitFlag().trim());
		}else{
			lmtObj.setLimitType("");
		}
		/*SBMILmtProxy proxy = helper.getSBMILmtProxy();
		ILimitTrxValue lmtTrxObj = null;
		
			try {
				lmtTrxObj = proxy.searchLimitByLmtId(dto.getMainFacilityId());
			} catch (LimitException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			if(lmtTrxObj!=null){
				ILimit limit = (ILimit)lmtTrxObj.getLimit();
				if(limit.getLimitRef()!=null && !limit.getLimitRef().isEmpty()){
					lmtObj.setMainFacilityId(limit.getLimitRef());
				}
			}
		*/
		
		if(dto.getMainFacilityId()!=null && !dto.getMainFacilityId().isEmpty()){
			lmtObj.setMainFacilityId(dto.getMainFacilityId());
		}else{
			lmtObj.setMainFacilityId("");
		}
		
		if(dto.getSubFacilityName()!=null && !dto.getSubFacilityName().trim().isEmpty()){
			lmtObj.setSubFacilityName(dto.getSubFacilityName().trim());
		}else{
			lmtObj.setSubFacilityName("");
		}
		
		lmtObj.setLimitStatus("ACTIVE");
		if(dto.getCamId()!=null && !dto.getCamId().trim().isEmpty()){
			lmtObj.setLimitProfileID(Long.parseLong(dto.getCamId().trim()));
		}else{
			lmtObj.setLimitProfileID(0L);
		}
		
		lmtObj.setBookingLocation(new OBBookingLocation());
		
		if(dto.getSanctionedAmount()!=null && !dto.getSanctionedAmount().trim().isEmpty()){
			lmtObj.setRequiredSecurityCoverage(dto.getSanctionedAmount().trim());
		}else{
			lmtObj.setRequiredSecurityCoverage("0");
		}
		
		if(dto.getReleasableAmount()!=null && !dto.getReleasableAmount().trim().isEmpty()){
			lmtObj.setReleasableAmount(dto.getReleasableAmount().trim());
		}else{
			lmtObj.setReleasableAmount("0");
		}
		
		//lmtObj.setIsFromCamonlineReq('Y');
		
		if(dto.getTenor()!=null && !dto.getTenor().trim().isEmpty()) {
			lmtObj.setTenorUnit(dto.getTenor().trim());
		}else {
			lmtObj.setTenorUnit("");
		}
		if(dto.getTenorVal()!=null && !dto.getTenorVal().trim().isEmpty()){
			lmtObj.setTenor(Long.parseLong(dto.getTenorVal().trim()));
		}else {
			lmtObj.setTenor(0L);
		}
		if(dto.getTenorDesc()!=null && !dto.getTenorDesc().trim().isEmpty()){
			lmtObj.setTenorDesc(dto.getTenorDesc().trim());
		}else {
			lmtObj.setTenorDesc("");
		}
		
		
		//Scod fields
		if(dto.getIsScod() != null && !(dto.getIsScod()).trim().isEmpty())
		{
			if("Y".equalsIgnoreCase(dto.getIsScod()))
			{
				if (dto.getProjectFinance() != null
						&& !dto.getProjectFinance().isEmpty()) {
					lmtObj.setProjectFinance(dto.getProjectFinance().trim());
				}

				if (dto.getProjectLoan() != null
						&& !dto.getProjectLoan().isEmpty()) {
					lmtObj.setProjectLoan(dto.getProjectLoan().trim());
				}

				if ( dto.getInfraFlag() != null
						&& !dto.getInfraFlag().trim().isEmpty()) {
					lmtObj.setInfaProjectFlag(dto.getInfraFlag());
				}
				
				try {
					if (dto.getScod()!=null 
							&& !dto.getScod().trim().isEmpty()) {
						lmtObj.setScodDate(relationshipDateFormat.parse(dto.getScod().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}

				if (dto.getScodRemark() != null
						&& !dto.getScodRemark().trim().isEmpty()) {
					lmtObj.setRemarksSCOD(dto.getScodRemark());
				}

				if (dto.getExeAssetClass() != null
						&& !dto.getExeAssetClass().trim().isEmpty()) {
					lmtObj.setExstAssetClass(dto.getExeAssetClass().trim());
				}
				
				try {
					if (dto.getExeAssetClassDate()!=null 
							&& !dto.getExeAssetClassDate().trim().isEmpty()) {
						lmtObj.setExstAssClassDate(relationshipDateFormat.parse(dto.getExeAssetClassDate().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				//Interim 
				
				if (dto.getLimitReleaseFlg() != null
						&& !dto.getLimitReleaseFlg().isEmpty()) {
					lmtObj.setWhlmreupSCOD(dto.getLimitReleaseFlg().trim());
				}
				
				if (dto.getRepayChngSched() != null
						&& !dto.getRepayChngSched().isEmpty()) {
					lmtObj.setChngInRepaySchedule(dto.getRepayChngSched().trim());
				}
				
				if (dto.getRevAssetClass()!= null
						&& !dto.getRevAssetClass().isEmpty()) {
					lmtObj.setRevisedAssetClass(dto.getRevAssetClass().trim());
				}
				
				try {
					if (dto.getRevAssetClassDt()!=null 
							&& !dto.getRevAssetClassDt().trim().isEmpty()) {
						lmtObj.setRevsdAssClassDate(relationshipDateFormat.parse(dto.getRevAssetClassDt().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				try {
					if (dto.getAcod()!=null 
							&& !dto.getAcod().trim().isEmpty()) {
						lmtObj.setActualCommOpsDate(relationshipDateFormat.parse(dto.getAcod().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				//Level 1
				
				if (dto.getDelayFlagL1() != null
						&& !dto.getDelayFlagL1().isEmpty()) {
					lmtObj.setProjectDelayedFlag(dto.getDelayFlagL1().trim());
				}
				
				if (dto.getInterestFlag() != null
						&& !dto.getInterestFlag().isEmpty()) {
					lmtObj.setPrincipalInterestSchFlag(dto.getInterestFlag().trim());
				}
				
				if (dto.getPriorReqFlag() != null
						&& !dto.getPriorReqFlag().isEmpty()) {
					lmtObj.setRecvPriorOfSCOD(dto.getPriorReqFlag().trim());
				}
				
				if (dto.getDelaylevel() != null
						&& !dto.getDelaylevel().isEmpty()) {
					lmtObj.setLelvelDelaySCOD(dto.getDelaylevel().trim());
				}
				
				if (dto.getDefReasonL1() != null
						&& !dto.getDefReasonL1().isEmpty()) {
					lmtObj.setReasonLevelOneDelay(dto.getDefReasonL1().trim());
				}
				
				if (dto.getRepayChngSchedL1() != null
						&& !dto.getRepayChngSchedL1().isEmpty()) {
					lmtObj.setChngInRepaySchedule(dto.getRepayChngSchedL1().trim());
				}
				
				try {
					if (dto.getEscodL1()!=null 
							&& !dto.getEscodL1().trim().isEmpty()) {
						lmtObj.setEscodLevelOneDelayDate(relationshipDateFormat.parse(dto.getEscodL1().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				if (dto.getOwnershipQ1FlagL1()!= null
						&& !dto.getOwnershipQ1FlagL1().isEmpty()) {
					lmtObj.setPromotersCapRunFlag(dto.getOwnershipQ1FlagL1().trim());
				}
				
				if (dto.getOwnershipQ2FlagL1()!= null
						&& !dto.getOwnershipQ2FlagL1().isEmpty()) {
					lmtObj.setPromotersHoldEquityFlag(dto.getOwnershipQ2FlagL1().trim());
				}
				
				if (dto.getOwnershipQ3FlagL1()!= null
						&& !dto.getOwnershipQ3FlagL1().isEmpty()) {
					lmtObj.setHasProjectViabReAssFlag(dto.getOwnershipQ3FlagL1().trim());
				}
				
				if (dto.getScopeQ1FlagL1()!= null
						&& !dto.getScopeQ1FlagL1().isEmpty()) {
					lmtObj.setChangeInScopeBeforeSCODFlag(dto.getScopeQ1FlagL1().trim());
				}
				
				if (dto.getScopeQ2FlagL1()!= null
						&& !dto.getScopeQ2FlagL1().isEmpty()) {
					lmtObj.setCostOverrunOrg25CostViabilityFlag(dto.getScopeQ2FlagL1().trim());
				}
				
				if (dto.getScopeQ3FlagL1()!= null
						&& !dto.getScopeQ3FlagL1().isEmpty()) {
					lmtObj.setProjectViabReassesedFlag(dto.getScopeQ3FlagL1().trim());
				}
				
				try {
					if (dto.getRevisedEscodL1()!=null 
							&& !dto.getRevisedEscodL1().trim().isEmpty()) {
						lmtObj.setRevsedESCODStpDate(relationshipDateFormat.parse(dto.getRevisedEscodL1().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				if (lmtObj.getExstAssetClass()!= null
						&& !lmtObj.getExstAssetClass().isEmpty()) {
					lmtObj.setExstAssetClassL1(lmtObj.getExstAssetClass().trim());
				}
				
				//try {
					if (lmtObj.getExstAssClassDate()!=null ) {
						lmtObj.setExstAssClassDateL1(lmtObj.getExstAssClassDate());
					}
					/*}catch (ParseException e) {
					e.printStackTrace();
				}*/
				
				if (dto.getRevAssetClassL1()!= null
						&& !dto.getRevAssetClassL1().isEmpty()) {
					lmtObj.setRevisedAssetClassL1(dto.getRevAssetClassL1().trim());
				}
				
				try {
					if (dto.getRevAssetClassDtL1()!=null 
							&& !dto.getRevAssetClassDtL1().trim().isEmpty()) {
						lmtObj.setRevsdAssClassDateL1(relationshipDateFormat.parse(dto.getRevAssetClassDtL1().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				//Level 2
				
				if (dto.getDelayFlagL2() != null
						&& !dto.getDelayFlagL2().isEmpty()) {
					lmtObj.setProjectDelayedFlag(dto.getDelayFlagL2().trim());
				}
				
				if (dto.getDelaylevel() != null
						&& !dto.getDelaylevel().isEmpty()) {
					lmtObj.setLelvelDelaySCOD(dto.getDelaylevel().trim());
				}
				
				if (dto.getDefReasonL2() != null
						&& !dto.getDefReasonL2().isEmpty()) {
					lmtObj.setReasonLevelTwoDelay(dto.getDefReasonL2().trim());
				}
				
				if (dto.getRepayChngSchedL2() != null
						&& !dto.getRepayChngSchedL2().isEmpty()) {
					lmtObj.setChngInRepayScheduleL2(dto.getRepayChngSchedL2().trim());
				}
				
				try {
					if (dto.getEscodL2()!=null 
							&& !dto.getEscodL2().trim().isEmpty()) {
						lmtObj.setEscodLevelSecondDelayDate(relationshipDateFormat.parse(dto.getEscodL2().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				if (dto.getEscodRevisionReasonQ1L2() != null
						&& !dto.getEscodRevisionReasonQ1L2().isEmpty()) {
					lmtObj.setLegalOrArbitrationLevel2Flag(dto.getEscodRevisionReasonQ1L2().trim());
				}
				
				if (dto.getEscodRevisionReasonQ2L2() != null
						&& !dto.getEscodRevisionReasonQ2L2().isEmpty()) {
					lmtObj.setReasonBeyondPromoterLevel2Flag(dto.getEscodRevisionReasonQ2L2().trim());
				}
				
				if (dto.getEscodRevisionReasonQ3L2() != null
						&& !dto.getEscodRevisionReasonQ3L2().isEmpty()) {
					lmtObj.setChngOfOwnPrjFlagInfraLevel2(dto.getEscodRevisionReasonQ3L2().trim());
				}
				
				if (dto.getEscodRevisionReasonQ4L2() != null
						&& !dto.getEscodRevisionReasonQ4L2().isEmpty()) {
					lmtObj.setChngOfProjScopeInfraLevel2(dto.getEscodRevisionReasonQ4L2().trim());
				}
				
				if (dto.getEscodRevisionReasonQ5L2() != null
						&& !dto.getEscodRevisionReasonQ5L2().isEmpty()) {
					lmtObj.setChngOfOwnPrjFlagNonInfraLevel2(dto.getEscodRevisionReasonQ5L2().trim());
				}
				
				if (dto.getEscodRevisionReasonQ6L2() != null
						&& !dto.getEscodRevisionReasonQ6L2().isEmpty()) {
					lmtObj.setChngOfProjScopeNonInfraLevel2(dto.getEscodRevisionReasonQ6L2().trim());
				}
				
				if (dto.getLegalDetailL2() != null
						&& !dto.getLegalDetailL2().isEmpty()) {
					lmtObj.setLeaglOrArbiProceed(dto.getLegalDetailL2().trim());
				}
				
				if (dto.getBeyondControlL2() != null
						&& !dto.getBeyondControlL2().isEmpty()) {
					lmtObj.setDetailsRsnByndPro(dto.getBeyondControlL2().trim());
				}
				
				if (dto.getOwnershipQ1FlagL2()!= null
						&& !dto.getOwnershipQ1FlagL2().isEmpty()) {
					lmtObj.setPromotersCapRunFlagL2(dto.getOwnershipQ1FlagL2().trim());
				}
				
				if (dto.getOwnershipQ2FlagL2()!= null
						&& !dto.getOwnershipQ2FlagL2().isEmpty()) {
					lmtObj.setPromotersHoldEquityFlagL2(dto.getOwnershipQ2FlagL2().trim());
				}
				
				if (dto.getOwnershipQ3FlagL2()!= null
						&& !dto.getOwnershipQ3FlagL2().isEmpty()) {
					lmtObj.setHasProjectViabReAssFlagL2(dto.getOwnershipQ3FlagL2().trim());
				}
				
				if (dto.getScopeQ1FlagL2()!= null
						&& !dto.getScopeQ1FlagL2().isEmpty()) {
					lmtObj.setChangeInScopeBeforeSCODFlagL2(dto.getScopeQ1FlagL2().trim());
				}
				
				if (dto.getScopeQ2FlagL2()!= null
						&& !dto.getScopeQ2FlagL2().isEmpty()) {
					lmtObj.setCostOverrunOrg25CostViabilityFlagL2(dto.getScopeQ2FlagL2().trim());
				}
				
				if (dto.getScopeQ3FlagL2()!= null
						&& !dto.getScopeQ3FlagL2().isEmpty()) {
					lmtObj.setProjectViabReassesedFlagL2(dto.getScopeQ3FlagL2().trim());
				}
				
				try {
					if (dto.getRevisedEscodL2()!=null 
							&& !dto.getRevisedEscodL2().trim().isEmpty()) {
						lmtObj.setRevsedESCODStpDateL2(relationshipDateFormat.parse(dto.getRevisedEscodL2().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				if (lmtObj.getExstAssetClass()!= null
						&& !lmtObj.getExstAssetClass().isEmpty()) {
					lmtObj.setExstAssetClassL2(lmtObj.getExstAssetClass().trim());
				}
				
				//try {
					if (lmtObj.getExstAssClassDate()!=null) {
						lmtObj.setExstAssClassDateL2(lmtObj.getExstAssClassDate());
					}
				/*}catch (ParseException e) {
					e.printStackTrace();
				}*/
				
				if (dto.getRevAssetClassL2()!= null
						&& !dto.getRevAssetClassL2().isEmpty()) {
					lmtObj.setRevisedAssetClass_L2(dto.getRevAssetClassL2().trim());
				}
				
				try {
					if (dto.getRevAssetClassDtL2()!=null 
							&& !dto.getRevAssetClassDtL2().trim().isEmpty()) {
						lmtObj.setRevsdAssClassDate_L2(relationshipDateFormat.parse(dto.getRevAssetClassDtL2().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				//Level 3
				
				if (dto.getDelayFlagL3() != null
						&& !dto.getDelayFlagL3().isEmpty()) {
					lmtObj.setProjectDelayedFlag(dto.getDelayFlagL3().trim());
				}
				
				if (dto.getDelaylevel() != null
						&& !dto.getDelaylevel().isEmpty()) {
					lmtObj.setLelvelDelaySCOD(dto.getDelaylevel().trim());
				}
				
				if (dto.getDefReasonL3() != null
						&& !dto.getDefReasonL3().isEmpty()) {
					lmtObj.setReasonLevelThreeDelay(dto.getDefReasonL3().trim());
				}
				
				if (dto.getRepayChngSchedL3() != null
						&& !dto.getRepayChngSchedL3().isEmpty()) {
					lmtObj.setChngInRepayScheduleL3(dto.getRepayChngSchedL3().trim());
				}
				
				try {
					if (dto.getEscodL3()!=null 
							&& !dto.getEscodL3().trim().isEmpty()) {
						lmtObj.setEscodLevelThreeDelayDate(relationshipDateFormat.parse(dto.getEscodL3().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				if (dto.getEscodRevisionReasonQ1L3() != null
						&& !dto.getEscodRevisionReasonQ1L3().isEmpty()) {
					lmtObj.setLegalOrArbitrationLevel3Flag(dto.getEscodRevisionReasonQ1L3().trim());
				}
				
				if (dto.getEscodRevisionReasonQ2L3() != null
						&& !dto.getEscodRevisionReasonQ2L3().isEmpty()) {
					lmtObj.setReasonBeyondPromoterLevel3Flag(dto.getEscodRevisionReasonQ2L3().trim());
				}
				
				if (dto.getEscodRevisionReasonQ3L3() != null
						&& !dto.getEscodRevisionReasonQ3L3().isEmpty()) {
					lmtObj.setChngOfOwnPrjFlagInfraLevel3(dto.getEscodRevisionReasonQ3L3().trim());
				}
				
				if (dto.getEscodRevisionReasonQ4L3() != null
						&& !dto.getEscodRevisionReasonQ4L3().isEmpty()) {
					lmtObj.setChngOfProjScopeInfraLevel3(dto.getEscodRevisionReasonQ4L3().trim());
				}
				
				if (dto.getEscodRevisionReasonQ5L3() != null
						&& !dto.getEscodRevisionReasonQ5L3().isEmpty()) {
					lmtObj.setChngOfOwnPrjFlagNonInfraLevel3(dto.getEscodRevisionReasonQ5L3().trim());
				}
				
				if (dto.getEscodRevisionReasonQ6L3() != null
						&& !dto.getEscodRevisionReasonQ6L3().isEmpty()) {
					lmtObj.setChngOfProjScopeNonInfraLevel3(dto.getEscodRevisionReasonQ6L3().trim());
				}
				
				if (dto.getLegalDetailL3() != null
						&& !dto.getLegalDetailL3().isEmpty()) {
					lmtObj.setLeaglOrArbiProceedLevel3(dto.getLegalDetailL3().trim());
				}
				
				if (dto.getBeyondControlL3() != null
						&& !dto.getBeyondControlL3().isEmpty()) {
					lmtObj.setDetailsRsnByndProLevel3(dto.getBeyondControlL3().trim());
				}
				
				if (dto.getOwnershipQ1FlagL3()!= null
						&& !dto.getOwnershipQ1FlagL3().isEmpty()) {
					lmtObj.setPromotersCapRunFlagL3(dto.getOwnershipQ1FlagL3().trim());
				}
				
				if (dto.getOwnershipQ2FlagL3()!= null
						&& !dto.getOwnershipQ2FlagL3().isEmpty()) {
					lmtObj.setPromotersHoldEquityFlagL3(dto.getOwnershipQ2FlagL3().trim());
				}
				
				if (dto.getOwnershipQ3FlagL3()!= null
						&& !dto.getOwnershipQ3FlagL3().isEmpty()) {
					lmtObj.setHasProjectViabReAssFlagL3(dto.getOwnershipQ3FlagL3().trim());
				}
				
				if (dto.getScopeQ1FlagL3()!= null
						&& !dto.getScopeQ1FlagL3().isEmpty()) {
					lmtObj.setChangeInScopeBeforeSCODFlagL3(dto.getScopeQ1FlagL3().trim());
				}
				
				if (dto.getScopeQ2FlagL3()!= null
						&& !dto.getScopeQ2FlagL3().isEmpty()) {
					lmtObj.setCostOverrunOrg25CostViabilityFlagL3(dto.getScopeQ2FlagL3().trim());
				}
				
				if (dto.getScopeQ3FlagL3()!= null
						&& !dto.getScopeQ3FlagL3().isEmpty()) {
					lmtObj.setProjectViabReassesedFlagL3(dto.getScopeQ3FlagL3().trim());
				}
				
				try {
					if (dto.getRevisedEscodL3()!=null 
							&& !dto.getRevisedEscodL3().trim().isEmpty()) {
						lmtObj.setRevsedESCODStpDateL3(relationshipDateFormat.parse(dto.getRevisedEscodL3().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
				if (lmtObj.getExstAssetClass()!= null
						&& !lmtObj.getExstAssetClass().isEmpty()) {
					lmtObj.setExstAssetClassL3(lmtObj.getExstAssetClass().trim());
				}
				
				/*try {*/
					if (lmtObj.getExstAssClassDate()!=null) {
						lmtObj.setExstAssClassDateL3(lmtObj.getExstAssClassDate());
					}
				/*}catch (ParseException e) {
					e.printStackTrace();
				}*/
				
				if (dto.getRevAssetClassL3()!= null
						&& !dto.getRevAssetClassL3().isEmpty()) {
					lmtObj.setRevisedAssetClass_L3(dto.getRevAssetClassL3().trim());
				}
				
				try {
					if (dto.getRevAssetClassDtL3()!=null 
							&& !dto.getRevAssetClassDtL3().trim().isEmpty()) {
						lmtObj.setRevsdAssClassDate_L3(relationshipDateFormat.parse(dto.getRevAssetClassDtL3().trim()));
					}
				}catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
		}
			

		return lmtObj;
	}
	
	public OBLimit getActualVAluesFromInputSCODToOB(FacilitySCODDetailRestRequestDTO dto , OBLimit lmtObj)
	{
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();

		//Initial call i.e. for new facility
		DefaultLogger.debug(this,"======Initial CAM call========");
		
		if (dto.getProjectFinance() != null && !dto.getProjectFinance().trim().isEmpty()) {
				lmtObj.setProjectFinance(dto.getProjectFinance());
			
		} else {
				lmtObj.setProjectFinance("");
		}

		if (dto.getProjectLoan() != null && !dto.getProjectLoan().trim().isEmpty()) {
				lmtObj.setProjectLoan(dto.getProjectLoan());
			
		} else {
				lmtObj.setProjectLoan("");
		}

		if ("Y".equals(dto.getProjectFinance()) || "Y".equals(dto.getProjectLoan())) {
			if (dto.getInfraFlag() != null && !dto.getInfraFlag().trim().isEmpty()) {
				
					lmtObj.setInfaProjectFlag(dto.getInfraFlag());
				
			} else {
					lmtObj.setInfaProjectFlag("");
			}
			
			try {
			if (dto.getScod() != null && !dto.getScod().toString().trim().isEmpty()) {
				
						lmtObj.setScodDate(relationshipDateFormat.parse(dto.getScod().trim()));
				}else {
					lmtObj.setScodDate(null);
				      }
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (dto.getScodRemark() != null && !dto.getScodRemark().trim().isEmpty()) {
				lmtObj.setRemarksSCOD(dto.getScodRemark());
			} else {
				lmtObj.setRemarksSCOD("");
			}
		}  
		
		if(dto.getExeAssetClass()!=null && !dto.getExeAssetClass().trim().isEmpty()){ 
			Object scodObj = masterObj.getObjectByEntityName("entryCode", dto.getExeAssetClass().trim(),
					"exeAssetClass",errors,"EXE_ASSET_CLASS_ID");
			System.out.println("Called exeAssetClass...");
			if(!(scodObj instanceof ActionErrors)){
				System.out.println("Is not instanceof ActionErrors...");
				lmtObj.setExstAssetClass(((ICommonCodeEntry)scodObj).getEntryCode());
				
				//to set exeAssetClassDate
				if(!dto.getExeAssetClass().equals("1")) {
					if (dto.getExeAssetClassDate() != null && !dto.getExeAssetClassDate().toString().trim().isEmpty()) {
						try {
											relationshipDateFormat.parse(dto.getExeAssetClassDate().toString().trim());
											lmtObj.setExstAssClassDate(relationshipDateFormat.parse(dto.getExeAssetClassDate().trim()));
											
							}
						catch (ParseException e) {
							e.printStackTrace();
						}
					} else {
						lmtObj.setExstAssClassDate(null);
					}
				} else {
					
					if(dto.getExeAssetClassDate() != null && !dto.getExeAssetClassDate().toString().trim().isEmpty()) {
					try {
										relationshipDateFormat.parse(dto.getExeAssetClassDate().toString().trim());
										lmtObj.setExstAssClassDate(relationshipDateFormat.parse(dto.getExeAssetClassDate().trim()));
						}
					catch (ParseException e) {
						 e.printStackTrace();
					}
					}else {
						lmtObj.setExstAssClassDate(null);
					}
					
				}
			} /*else {
				System.out.println("Error occured on exeAssetClass");
				errors.add("exeAssetClass",new ActionMessage("error.exeAssetClass.invalid"));
			}*/
		}else{
			lmtObj.setExstAssetClass("1");
		}
		
		if (dto.getEvent()!=null && dto.getEvent().equalsIgnoreCase("WS_FAC_EDIT_SCOD_REST"))
		{
		//Interim call i.e. for update facility
		DefaultLogger.debug(this,"======Interim CAM call========");
				
			Calendar calendar = Calendar.getInstance();
			 calendar.add(Calendar.DATE, -1);
			if (dto.getLimitReleaseFlg() != null && !dto.getLimitReleaseFlg().trim().isEmpty()) {
				DefaultLogger.debug(this,"======getLimitReleaseFlg not null========");
					 
							lmtObj.setWhlmreupSCOD(dto.getLimitReleaseFlg());
							//case 1: limit not yet released
							
							if("N".equals(dto.getLimitReleaseFlg().trim())) {
								//to set updated scod
								DefaultLogger.debug(this,"======limit not yet released========");
								
									
								//to set updated scod remark
								if (dto.getScodRemark() != null && !dto.getScodRemark().trim().isEmpty()) {
									lmtObj.setRemarksSCOD(dto.getScodRemark());
								} else {
									lmtObj.setRemarksSCOD("");
								}
								
								//to set repayment schedule
								//lmtObj.setChngInRepaySchedule(dto.getRepayChngSched());
								if (dto.getRepayChngSched() != null && !dto.getRepayChngSched().trim().isEmpty()) {
								
										lmtObj.setChngInRepaySchedule(dto.getRepayChngSched());
									
								} else {
									lmtObj.setChngInRepaySchedule("N");
								}
								
								//to set revAssetClass
								if(dto.getRevAssetClass()!=null && !dto.getRevAssetClass().trim().isEmpty()){
									Object scodObj = masterObj.getObjectByEntityName("entryCode", dto.getRevAssetClass().trim(),
											"revAssetClass",errors,"REV_ASSET_CLASS_ID");
									if(!(scodObj instanceof ActionErrors)){
										lmtObj.setRevisedAssetClass(((ICommonCodeEntry)scodObj).getEntryCode());
										
										//to set revAssetClassDt
										if(!dto.getRevAssetClass().equals("1")) {
											if (dto.getRevAssetClassDt() != null && !dto.getRevAssetClassDt().toString().trim().isEmpty()) {
												try {
															lmtObj.setRevsdAssClassDate(relationshipDateFormat.parse(dto.getRevAssetClassDt().trim()));
													}
																										 
												 catch (ParseException e) {
													 e.printStackTrace();
												}
											} else {
												lmtObj.setRevsdAssClassDate(null);
											}
										} else {
											
											if(dto.getRevAssetClassDt() != null && !dto.getRevAssetClassDt().toString().trim().isEmpty()) {
											try {
													lmtObj.setRevsdAssClassDate(relationshipDateFormat.parse(dto.getRevAssetClassDt().trim()));
												}
														
											catch (ParseException e) {
												e.printStackTrace();
											}
											}else {
												lmtObj.setRevsdAssClassDate(null);
											}
											
										}
									} 
								}else{
									lmtObj.setRevisedAssetClass(null);
								}
							}
							//case 2: limit released
							else if("Y".equals(dto.getLimitReleaseFlg().trim())) {
								
								DefaultLogger.debug(this,"======limit released========");
								
								//to set acod
								
								if (dto.getAcod() != null && !dto.getAcod().toString().trim().isEmpty()) {
									try {
									lmtObj.setActualCommOpsDate(relationshipDateFormat.parse(dto.getAcod().trim()));	
									}
									catch (ParseException e) {
										e.printStackTrace();
									}
									
								/*else {
									lmtObj.setActualCommOpsDate(null);
									 }*/
								
									if(dto.getDelaylevel()!=null && !dto.getDelaylevel().trim().isEmpty()){
										errors.add("delaylevel",new ActionMessage("error.delaylevel.not.allowed"));
									}else {
										if(dto.getRevAssetClass()!=null && !dto.getRevAssetClass().trim().isEmpty()){
											Object scodObj = masterObj.getObjectByEntityName("entryCode", dto.getRevAssetClass().trim(),
													"revAssetClass",errors,"REV_ASSET_CLASS_ID");
											if(!(scodObj instanceof ActionErrors)){
												lmtObj.setRevisedAssetClass(((ICommonCodeEntry)scodObj).getEntryCode());
												//to set revAssetClassDt
												if(!dto.getRevAssetClass().equals("1")) {
													if (dto.getRevAssetClassDt() != null && !dto.getRevAssetClassDt().toString().trim().isEmpty()) {
														try {
																lmtObj.setRevsdAssClassDate(relationshipDateFormat.parse(dto.getRevAssetClassDt().trim()));
															
														} catch (ParseException e) {
															e.printStackTrace();
														}
													} else {
														lmtObj.setRevsdAssClassDate(null);
													}
												} else {
													
													if(dto.getRevAssetClassDt() != null && !dto.getRevAssetClassDt().toString().trim().isEmpty()) {
														try {
															lmtObj.setRevsdAssClassDate(relationshipDateFormat.parse(dto.getRevAssetClassDt().trim()));
														} catch (ParseException e) {
															e.printStackTrace();
														}
													}else {
														lmtObj.setRevsdAssClassDate(null);
													}
												}
											} else {
												errors.add("revAssetClass",new ActionMessage("error.revAssetClass.invalid"));
											}
										}else{
											lmtObj.setRevisedAssetClass("");
										}
									}
									
								} 
								//to set level details
								else {
									
									lmtObj.setActualCommOpsDate(null);
								
									if(dto.getDelaylevel()!=null && !dto.getDelaylevel().trim().isEmpty()){
										Object scodObj = masterObj.getObjectByEntityName("entryCode", dto.getDelaylevel().trim(),
												"delaylevel",errors,"DELAY_LEVEL_ID");
										if(!(scodObj instanceof ActionErrors)){
											lmtObj.setLelvelDelaySCOD(((ICommonCodeEntry)scodObj).getEntryCode());
											
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<1st DELAY CAM>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
											if("1".equals(dto.getDelaylevel())) {
												DefaultLogger.debug(this,"======1st DELAY CAM========");
												
												//to set delayFlagL1
												if (dto.getDelayFlagL1() != null && !dto.getDelayFlagL1().trim().isEmpty()) {
													
														if("Y".equals(dto.getDelayFlagL1().trim())) {
														lmtObj.setProjectDelayedFlag(dto.getDelayFlagL1());
														}
												}else {
															lmtObj.setProjectDelayedFlag("N");
														}
												
																								
												//to set interestFlag
												if (dto.getInterestFlag() != null && !dto.getInterestFlag().trim().isEmpty()) {
													
														lmtObj.setPrincipalInterestSchFlag(dto.getInterestFlag());
													}
												 else {
													 lmtObj.setPrincipalInterestSchFlag("N");
												}
												//to set priorReqFlag
												if (dto.getPriorReqFlag() != null && !dto.getPriorReqFlag().trim().isEmpty()) {
													
														lmtObj.setRecvPriorOfSCOD(dto.getPriorReqFlag());
													}
												 else {
													 lmtObj.setRecvPriorOfSCOD("N");
												}
												//to set defReasonL1 
												if (dto.getDefReasonL1() != null && !dto.getDefReasonL1().trim().isEmpty()) {
													lmtObj.setReasonLevelOneDelay(dto.getDefReasonL1());
												} else {
													lmtObj.setReasonLevelOneDelay("");
												}
												//to set repayChngSchedL1
												if (dto.getRepayChngSchedL1() != null && !dto.getRepayChngSchedL1().trim().isEmpty()) {
													
														lmtObj.setChngInRepaySchedule(dto.getRepayChngSchedL1());
													
												} else {
														lmtObj.setChngInRepaySchedule("N");
												}
												//to set escodL1
												try {
												if (dto.getEscodL1() != null && !dto.getEscodL1().toString().trim().isEmpty()) {
													
														lmtObj.setEscodLevelOneDelayDate(relationshipDateFormat.parse(dto.getEscodL1().trim()));
													}
													else {
														lmtObj.setEscodLevelOneDelayDate(relationshipDateFormat.parse(""));
													} 
												}
												catch (ParseException e) {
													e.printStackTrace();
												}
												
												//to set ownershipQ1FlagL1
												if (dto.getOwnershipQ1FlagL1() != null && !dto.getOwnershipQ1FlagL1().trim().isEmpty()) {
													
														lmtObj.setPromotersCapRunFlag(dto.getOwnershipQ1FlagL1());
													}
												else {
													lmtObj.setPromotersCapRunFlag("N");
												}
												//to set ownershipQ2FlagL1
												if (dto.getOwnershipQ2FlagL1() != null && !dto.getOwnershipQ2FlagL1().trim().isEmpty()) {
													
														lmtObj.setPromotersHoldEquityFlag(dto.getOwnershipQ2FlagL1());
													}
												 else {
													 lmtObj.setPromotersHoldEquityFlag("");
												}
												//to set ownershipQ3FlagL1
												if (dto.getOwnershipQ3FlagL1() != null && !dto.getOwnershipQ3FlagL1().trim().isEmpty()) {
													
														lmtObj.setHasProjectViabReAssFlag(dto.getOwnershipQ3FlagL1());
													
												} else {
													lmtObj.setHasProjectViabReAssFlag("");
												}
												//to set scopeQ1FlagL1
												if (dto.getScopeQ1FlagL1() != null && !dto.getScopeQ1FlagL1().trim().isEmpty()) {
													
														lmtObj.setChangeInScopeBeforeSCODFlag(dto.getScopeQ1FlagL1());
													
												} else {
													lmtObj.setChangeInScopeBeforeSCODFlag("N");
												}
												//to set scopeQ2FlagL1
												if (dto.getScopeQ2FlagL1() != null && !dto.getScopeQ2FlagL1().trim().isEmpty()) {
													
														lmtObj.setCostOverrunOrg25CostViabilityFlag(dto.getScopeQ2FlagL1());
													
												} else {
													lmtObj.setCostOverrunOrg25CostViabilityFlag("");
												}
												//to set scopeQ3FlagL1
												if (dto.getScopeQ3FlagL1() != null && !dto.getScopeQ3FlagL1().trim().isEmpty()) {
													
														lmtObj.setProjectViabReassesedFlag(dto.getScopeQ3FlagL1());
													
												} else {
													lmtObj.setProjectViabReassesedFlag("");
												}
												//to set revisedEscodL1
												try {
												if (dto.getRevisedEscodL1() != null && !dto.getRevisedEscodL1().toString().trim().isEmpty()) {
													
															lmtObj.setRevsedESCODStpDate(relationshipDateFormat.parse(dto.getRevisedEscodL1().trim()));	
														}	else {
															lmtObj.setRevsedESCODStpDate(relationshipDateFormat.parse(""));
													      		}
														 
													} catch (ParseException e) {
														e.printStackTrace();
														}
												
												
												//to set revAssetClassL1
												if(dto.getRevAssetClassL1()!=null && !dto.getRevAssetClassL1().trim().isEmpty()){
													Object revAssetClassL1Obj = masterObj.getObjectByEntityName("entryCode", dto.getRevAssetClassL1().trim(),
															"revAssetClass",errors,"REV_ASSET_CLASS_ID");
													if(!(revAssetClassL1Obj instanceof ActionErrors)){
														lmtObj.setRevisedAssetClassL1(((ICommonCodeEntry)revAssetClassL1Obj).getEntryCode());
														
														//to set revAssetClassDtL1
														if(!dto.getRevAssetClassL1().equals("1")) {
															if (dto.getRevAssetClassDtL1() != null && !dto.getRevAssetClassDtL1().toString().trim().isEmpty()) {
																try {
																	lmtObj.setRevsdAssClassDateL1(relationshipDateFormat.parse(dto.getRevAssetClassDtL1().trim()));
																	}
																catch (ParseException e) {
																	e.printStackTrace();
																}
															} else {
																lmtObj.setRevsdAssClassDateL1(null);
															}
														} else {
															
															if(dto.getRevAssetClassDtL1() != null && !dto.getRevAssetClassDtL1().toString().trim().isEmpty()) {
																try {
																		lmtObj.setRevsdAssClassDateL1(relationshipDateFormat.parse(dto.getRevAssetClassDtL1().trim()));
																} catch (ParseException e) {
																	errors.add("revAssetClassDtL1", new ActionMessage("error.revAssetClassDtL1.invalid.format"));
																}
															}else {
																lmtObj.setRevsdAssClassDateL1(null);
															}
														}
													} else {
														errors.add("revAssetClassL1",new ActionMessage("error.revAssetClassL1.invalid"));
													}
												} else {
													lmtObj.setRevisedAssetClassL1("");
												}
											}
//<<<<<<<<<<<<<<<<<<<<<<<<<<2nd DELAY CAM>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
											else if("2".equals(dto.getDelaylevel())) {
												DefaultLogger.debug(this,"======2nd DELAY CAM========");
												//to set delayFlagL2
												if (dto.getDelayFlagL2() != null && !dto.getDelayFlagL2().trim().isEmpty()) {
													 
														if("Y".equals(dto.getDelayFlagL2().trim())) {
															lmtObj.setProjectDelayedFlag(dto.getDelayFlagL2());
														}
													
												} else {
													lmtObj.setProjectDelayedFlag("");
													}
												//to set defReasonL2
												if (dto.getDefReasonL2() != null && !dto.getDefReasonL2().trim().isEmpty()) {
													lmtObj.setReasonLevelTwoDelay(dto.getDefReasonL2());
												} else {
													lmtObj.setReasonLevelTwoDelay("");
												}
												//to set repayChngSchedL2
												if (dto.getRepayChngSchedL2() != null && !dto.getRepayChngSchedL2().trim().isEmpty()) {
													
														lmtObj.setChngInRepayScheduleL2(dto.getRepayChngSchedL2());
													
												} else {
													lmtObj.setChngInRepayScheduleL2("N");
												}
												//to set escodL2
												try {
													if (dto.getEscodL2() != null && !dto.getEscodL2().toString().trim().isEmpty()) {
														
														lmtObj.setEscodLevelSecondDelayDate(relationshipDateFormat.parse(dto.getEscodL2().trim()));
														}
														else {
															lmtObj.setEscodLevelSecondDelayDate(relationshipDateFormat.parse(""));
														} 
													}
													catch (ParseException e) {
														e.printStackTrace();
													} 
												//to set escodRevisionReasonQ1L2 and
												if(dto.getInfraFlag().equals("Y")) {
													if (dto.getEscodRevisionReasonQ1L2() != null && !dto.getEscodRevisionReasonQ1L2().trim().isEmpty()) {
														 
															lmtObj.setLegalOrArbitrationLevel2Flag(dto.getEscodRevisionReasonQ1L2());
															//to set legalDetailL2
															if("Y".equals(dto.getEscodRevisionReasonQ1L2().trim())) {
																if (dto.getLegalDetailL2() != null && !dto.getLegalDetailL2().trim().isEmpty()) {
																	lmtObj.setLeaglOrArbiProceed(dto.getLegalDetailL2());
																} 
															} else {
																if (dto.getLegalDetailL2() != null && !dto.getLegalDetailL2().trim().isEmpty()) {
																	lmtObj.setLeaglOrArbiProceed(dto.getLegalDetailL2());
																} else {
																	lmtObj.setLeaglOrArbiProceed(" ");
																}
															}
														}
													else {
														lmtObj.setLegalOrArbitrationLevel2Flag("");
													}
														
													//to set escodRevisionReasonQ2L2
													if (dto.getEscodRevisionReasonQ2L2() != null && !dto.getEscodRevisionReasonQ2L2().trim().isEmpty()) {
														 
															lmtObj.setReasonBeyondPromoterLevel2Flag(dto.getEscodRevisionReasonQ2L2());
															//to set beyondControlL2
															if("Y".equals(dto.getEscodRevisionReasonQ2L2())) {
																if (dto.getBeyondControlL2() != null && !dto.getBeyondControlL2().trim().isEmpty()) {
																	lmtObj.setDetailsRsnByndPro(dto.getBeyondControlL2());
																} 
															}else {
																if (dto.getBeyondControlL2() != null && !dto.getBeyondControlL2().trim().isEmpty()) {
																	lmtObj.setDetailsRsnByndPro(dto.getBeyondControlL2());
																}else {
																	lmtObj.setDetailsRsnByndPro(" ");
																}
															}
														}
													else {
														lmtObj.setReasonBeyondPromoterLevel2Flag("");
													}
													 
													//to set escodRevisionReasonQ3L2 
													if (dto.getEscodRevisionReasonQ3L2() != null && !dto.getEscodRevisionReasonQ3L2().trim().isEmpty()) {
														
															lmtObj.setChngOfOwnPrjFlagInfraLevel2(dto.getEscodRevisionReasonQ3L2());
															
															//to set ownershipQ1FlagL2
															if (dto.getOwnershipQ1FlagL2() != null && !dto.getOwnershipQ1FlagL2().trim().isEmpty()) {
																
																	lmtObj.setPromotersCapRunFlagL2(dto.getOwnershipQ1FlagL2());
															} 
															else {
																lmtObj.setPromotersCapRunFlagL2("N");
															}
															//to set ownershipQ2FlagL2
															if (dto.getOwnershipQ2FlagL2() != null && !dto.getOwnershipQ2FlagL2().trim().isEmpty()) {
																 
																	lmtObj.setPromotersHoldEquityFlagL2(dto.getOwnershipQ2FlagL2());
															} else {
																lmtObj.setPromotersHoldEquityFlagL2("");
															}
															//to set ownershipQ3FlagL2
															if (dto.getOwnershipQ3FlagL2() != null && !dto.getOwnershipQ3FlagL2().trim().isEmpty()) {
																
																	lmtObj.setHasProjectViabReAssFlagL2(dto.getOwnershipQ3FlagL2());
															} else {
																lmtObj.setHasProjectViabReAssFlagL2("");
															}
															
													} else {
														lmtObj.setChngOfOwnPrjFlagInfraLevel2("");
													}
													
													//to set escodRevisionReasonQ4L2
													if (dto.getEscodRevisionReasonQ4L2() != null && !dto.getEscodRevisionReasonQ4L2().trim().isEmpty()) {
														
															lmtObj.setChngOfProjScopeInfraLevel2(dto.getEscodRevisionReasonQ4L2());
															
															//to set scopeQ1FlagL2
															if (dto.getScopeQ1FlagL2() != null && !dto.getScopeQ1FlagL2().trim().isEmpty()) {
																 
																	lmtObj.setChangeInScopeBeforeSCODFlagL2(dto.getScopeQ1FlagL2());
																
															} else {
																	lmtObj.setChangeInScopeBeforeSCODFlagL2("N");
															}
															//to set scopeQ2FlagL2
															if (dto.getScopeQ2FlagL2() != null && !dto.getScopeQ2FlagL2().trim().isEmpty()) {
																
																	lmtObj.setCostOverrunOrg25CostViabilityFlagL2(dto.getScopeQ2FlagL2());
																
															} else {
																	lmtObj.setCostOverrunOrg25CostViabilityFlagL2("");
															}
															//to set scopeQ3FlagL2
															if (dto.getScopeQ3FlagL2() != null && !dto.getScopeQ3FlagL2().trim().isEmpty()) {
																
																	lmtObj.setProjectViabReassesedFlagL2(dto.getScopeQ3FlagL2());
																
															} else {
																	lmtObj.setProjectViabReassesedFlagL2("");
															}
													} else {
														lmtObj.setChngOfProjScopeInfraLevel2("");
													}
												} else if(dto.getInfraFlag().equals("N")) {
													//to set escodRevisionReasonQ5L2 
													if (dto.getEscodRevisionReasonQ5L2() != null && !dto.getEscodRevisionReasonQ5L2().trim().isEmpty()) {
														
															lmtObj.setChngOfOwnPrjFlagNonInfraLevel2(dto.getEscodRevisionReasonQ5L2());
															
															//to set ownershipQ1FlagL2
															if (dto.getOwnershipQ1FlagL2() != null && !dto.getOwnershipQ1FlagL2().trim().isEmpty()) {
																
																	lmtObj.setPromotersCapRunFlagL2(dto.getOwnershipQ1FlagL2());
																
															} else {
																	lmtObj.setPromotersCapRunFlagL2("");
															}
															//to set ownershipQ2FlagL2
															if (dto.getOwnershipQ2FlagL2() != null && !dto.getOwnershipQ2FlagL2().trim().isEmpty()) {
																
																	lmtObj.setPromotersHoldEquityFlagL2(dto.getOwnershipQ2FlagL2());
																
															} else {
																	lmtObj.setPromotersHoldEquityFlagL2("");
															}
															//to set ownershipQ3FlagL2
															if (dto.getOwnershipQ3FlagL2() != null && !dto.getOwnershipQ3FlagL2().trim().isEmpty()) {
																
																	lmtObj.setHasProjectViabReAssFlagL2(dto.getOwnershipQ3FlagL2());
																
															} else {
																lmtObj.setHasProjectViabReAssFlagL2("");
															}
													} else {
														lmtObj.setChngOfOwnPrjFlagNonInfraLevel2("");
													}
													
													//to set escodRevisionReasonQ6L2
													if (dto.getEscodRevisionReasonQ6L2() != null && !dto.getEscodRevisionReasonQ6L2().trim().isEmpty()) {
														
															lmtObj.setChngOfProjScopeNonInfraLevel2(dto.getEscodRevisionReasonQ6L2());
															
															//to set scopeQ1FlagL2
															if (dto.getScopeQ1FlagL2() != null && !dto.getScopeQ1FlagL2().trim().isEmpty()) {
																
																	lmtObj.setChangeInScopeBeforeSCODFlagL2(dto.getScopeQ1FlagL2());
															} else {
																	lmtObj.setChangeInScopeBeforeSCODFlagL2("");
															}
															//to set scopeQ2FlagL2
															if (dto.getScopeQ2FlagL2() != null && !dto.getScopeQ2FlagL2().trim().isEmpty()) {
																
																	lmtObj.setCostOverrunOrg25CostViabilityFlagL2(dto.getScopeQ2FlagL2());
															} else {
																	lmtObj.setCostOverrunOrg25CostViabilityFlagL2("");
															}
															//to set scopeQ3FlagL2
															if (dto.getScopeQ3FlagL2() != null && !dto.getScopeQ3FlagL2().trim().isEmpty()) {
																
																	lmtObj.setProjectViabReassesedFlagL2(dto.getScopeQ3FlagL2());
															} else {
																	lmtObj.setProjectViabReassesedFlagL2("");
															}
													} else {
															lmtObj.setChngOfProjScopeNonInfraLevel2("");
													}
												}
										
												//to set revisedEscodL2
												try {
												if (dto.getRevisedEscodL2() != null && !dto.getRevisedEscodL2().toString().trim().isEmpty()) {
													
															lmtObj.setRevsedESCODStpDateL2(relationshipDateFormat.parse(dto.getRevisedEscodL2().trim()));;
														
													}
												else {
													lmtObj.setRevsedESCODStpDateL2(relationshipDateFormat.parse(""));
												} 
											}
												catch (ParseException e) {
													e.printStackTrace();
												}
												//to set revAssetClassL2
												if(dto.getRevAssetClassL2()!=null && !dto.getRevAssetClassL2().trim().isEmpty()){
													Object revAssetClassL1Obj = masterObj.getObjectByEntityName("entryCode", dto.getRevAssetClassL2().trim(),
															"revAssetClass",errors,"REV_ASSET_CLASS_ID");
														lmtObj.setRevisedAssetClass_L2(((ICommonCodeEntry)revAssetClassL1Obj).getEntryCode());
														//to set revAssetClassDtL2
														if(!dto.getRevAssetClassL2().equals("1")) {
															if (dto.getRevAssetClassDtL2() != null && !dto.getRevAssetClassDtL2().toString().trim().isEmpty()) {
																try {
																		lmtObj.setRevsdAssClassDate_L2(relationshipDateFormat.parse(dto.getRevAssetClassDtL2().trim()));
																} catch (ParseException e) {
																	e.printStackTrace();
																}
															} else {
																lmtObj.setRevsdAssClassDate_L2(null);
															}
														} else {
															
															if(dto.getRevAssetClassDtL2() != null && !dto.getRevAssetClassDtL2().toString().trim().isEmpty()) {
																try {
																		lmtObj.setRevsdAssClassDate_L2(relationshipDateFormat.parse(dto.getRevAssetClassDtL2().trim()));
																	 
																} catch (ParseException e) {
																	e.printStackTrace();
																}
															}else {
																lmtObj.setRevsdAssClassDate_L2(null);
															}
														}
												} else {
													lmtObj.setRevisedAssetClass_L2("");
												}
											
											}
//<<<<<<<<<<<<<<<<<<<<<<<3rd DELAY CAM>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
											else if("3".equals(dto.getDelaylevel())) {
												//to set delayFlagL3
												DefaultLogger.debug(this,"======3rd DELAY CAM========");
												if (dto.getDelayFlagL3() != null && !dto.getDelayFlagL3().trim().isEmpty()) {
													
														if("Y".equals(dto.getDelayFlagL3().trim())) {
														lmtObj.setProjectDelayedFlag(dto.getDelayFlagL3());
														}
													
												} else {
														lmtObj.setProjectDelayedFlag("");
												}
												
												//to set defReasonL3
												if (dto.getDefReasonL3() != null && !dto.getDefReasonL3().trim().isEmpty()) {
													lmtObj.setReasonLevelThreeDelay(dto.getDefReasonL3());
												} else {
													lmtObj.setReasonLevelThreeDelay("");
												}
												//to set repayChngSchedL3
												if (dto.getRepayChngSchedL3() != null && !dto.getRepayChngSchedL3().trim().isEmpty()) {
													
														lmtObj.setChngInRepayScheduleL3(dto.getRepayChngSchedL3());
													
												} else {
														lmtObj.setChngInRepayScheduleL3("N");
												}
												//to set escodL3
												try {
												if (dto.getEscodL3() != null && !dto.getEscodL3().toString().trim().isEmpty()) {
													
															lmtObj.setEscodLevelThreeDelayDate(relationshipDateFormat.parse(dto.getEscodL3().trim()));
														
												} else {
													lmtObj.setEscodLevelThreeDelayDate(relationshipDateFormat.parse(""));
												} 
												} 
												catch (ParseException e) {
													e.printStackTrace();
												}
												//to set escodRevisionReasonQ1L3 and 
												if(dto.getInfraFlag().equals("Y")) {
													if (dto.getEscodRevisionReasonQ1L3() != null && !dto.getEscodRevisionReasonQ1L3().trim().isEmpty()) {
														
															lmtObj.setLegalOrArbitrationLevel3Flag(dto.getEscodRevisionReasonQ1L3());
															//to set legalDetailL3
															if("Y".equals(dto.getEscodRevisionReasonQ1L3().trim())) {
																if (dto.getLegalDetailL3() != null && !dto.getLegalDetailL3().trim().isEmpty()) {
																	lmtObj.setLeaglOrArbiProceedLevel3(dto.getLegalDetailL3());
																} else {
																	lmtObj.setLeaglOrArbiProceedLevel3("");
																}
															} else {
																/*if (dto.getLegalDetailL3() != null && !dto.getLegalDetailL3().trim().isEmpty()) {
																	lmtObj.setLeaglOrArbiProceedLevel3(dto.getLegalDetailL3());
																}else {*/
																	lmtObj.setLeaglOrArbiProceedLevel3(" ");
													//			}
															}
														
													} else {
														lmtObj.setLegalOrArbitrationLevel3Flag("");
													}
													//to set escodRevisionReasonQ2L3
													if (dto.getEscodRevisionReasonQ2L3() != null && !dto.getEscodRevisionReasonQ2L3().trim().isEmpty()) {
														
															lmtObj.setReasonBeyondPromoterLevel3Flag(dto.getEscodRevisionReasonQ2L3());
															//to set beyondControlL3
															if("Y".equals(dto.getEscodRevisionReasonQ2L3().trim())) {
																if (dto.getBeyondControlL3() != null && !dto.getBeyondControlL3().trim().isEmpty()) {
																	lmtObj.setDetailsRsnByndProLevel3(dto.getBeyondControlL3());
																} else {
																	lmtObj.setDetailsRsnByndProLevel3("");
																}
															} else {
																/*if (dto.getBeyondControlL3() != null && !dto.getBeyondControlL3().trim().isEmpty()) {
																	lmtObj.setDetailsRsnByndProLevel3(dto.getBeyondControlL3());
																}else {*/
																	lmtObj.setDetailsRsnByndProLevel3("");
												//				}
															}
														
													} else {
														lmtObj.setReasonBeyondPromoterLevel3Flag("");
													}
													
													//to set escodRevisionReasonQ3L3 
													if (dto.getEscodRevisionReasonQ3L3() != null && !dto.getEscodRevisionReasonQ3L3().trim().isEmpty()) {
														
															lmtObj.setChngOfOwnPrjFlagInfraLevel3(dto.getEscodRevisionReasonQ3L3());
															
															//to set ownershipQ1FlagL3
															if (dto.getOwnershipQ1FlagL3() != null && !dto.getOwnershipQ1FlagL3().trim().isEmpty()) {
																
																	lmtObj.setPromotersCapRunFlagL3(dto.getOwnershipQ1FlagL3());
															} else {
																	lmtObj.setPromotersCapRunFlagL3("N");
															}
															//to set ownershipQ2FlagL3
															if (dto.getOwnershipQ2FlagL3() != null && !dto.getOwnershipQ2FlagL3().trim().isEmpty()) {
																
																	lmtObj.setPromotersHoldEquityFlagL3(dto.getOwnershipQ2FlagL3());
															} else {
																	lmtObj.setPromotersHoldEquityFlagL3("");
															}
															//to set ownershipQ3FlagL3
															if (dto.getOwnershipQ3FlagL3() != null && !dto.getOwnershipQ3FlagL3().trim().isEmpty()) {
																
																	lmtObj.setHasProjectViabReAssFlagL3(dto.getOwnershipQ3FlagL3());
															} else {
																lmtObj.setHasProjectViabReAssFlagL3("");
															}
													} else {
														lmtObj.setChngOfOwnPrjFlagInfraLevel3("");
													}
													
													//to set escodRevisionReasonQ4L3
													if (dto.getEscodRevisionReasonQ4L3() != null && !dto.getEscodRevisionReasonQ4L3().trim().isEmpty()) {
														
															lmtObj.setChngOfProjScopeInfraLevel3(dto.getEscodRevisionReasonQ4L3());
															
															//to set scopeQ1FlagL3
															if (dto.getScopeQ1FlagL3() != null && !dto.getScopeQ1FlagL3().trim().isEmpty()) {
																
																	lmtObj.setChangeInScopeBeforeSCODFlagL3(dto.getScopeQ1FlagL3());
															} else {
																	lmtObj.setChangeInScopeBeforeSCODFlagL3("N");
															}
															//to set scopeQ2FlagL3
															if (dto.getScopeQ2FlagL3() != null && !dto.getScopeQ2FlagL3().trim().isEmpty()) {
																
																	lmtObj.setCostOverrunOrg25CostViabilityFlagL3(dto.getScopeQ2FlagL3());
															} else {
																	lmtObj.setCostOverrunOrg25CostViabilityFlagL3("");
															}
															//to set scopeQ3FlagL3
															if (dto.getScopeQ3FlagL3() != null && !dto.getScopeQ3FlagL3().trim().isEmpty()) {
																
																	lmtObj.setProjectViabReassesedFlagL3(dto.getScopeQ3FlagL3());
															} else {
																	lmtObj.setProjectViabReassesedFlagL3("");
															}
													} else {
														lmtObj.setChngOfProjScopeInfraLevel3("");
													}
												} else if(dto.getInfraFlag().equals("N")) {
													//to set escodRevisionReasonQ5L3 
													if (dto.getEscodRevisionReasonQ5L3() != null && !dto.getEscodRevisionReasonQ5L3().trim().isEmpty()) {
														
															lmtObj.setChngOfOwnPrjFlagNonInfraLevel3(dto.getEscodRevisionReasonQ5L3());
															
															//to set ownershipQ1FlagL3
															if (dto.getOwnershipQ1FlagL3() != null && !dto.getOwnershipQ1FlagL3().trim().isEmpty()) {
																
																	lmtObj.setPromotersCapRunFlagL3(dto.getOwnershipQ1FlagL3());
															} else {
																	lmtObj.setPromotersCapRunFlagL3("");
															}
															//to set ownershipQ2FlagL3
															if (dto.getOwnershipQ2FlagL3() != null && !dto.getOwnershipQ2FlagL3().trim().isEmpty()) {
																
																	lmtObj.setPromotersHoldEquityFlagL3(dto.getOwnershipQ2FlagL3());
															} else {
																	lmtObj.setPromotersHoldEquityFlagL3("");
															}
															//to set ownershipQ3FlagL3
															if (dto.getOwnershipQ3FlagL3() != null && !dto.getOwnershipQ3FlagL3().trim().isEmpty()) {
																
																	lmtObj.setHasProjectViabReAssFlagL3(dto.getOwnershipQ3FlagL3());
																
															} else {
																	lmtObj.setHasProjectViabReAssFlagL3("");
															}
													} else {
															lmtObj.setChngOfOwnPrjFlagNonInfraLevel3("");
													}
													
													//to set escodRevisionReasonQ6L3
													if (dto.getEscodRevisionReasonQ6L3() != null && !dto.getEscodRevisionReasonQ6L3().trim().isEmpty()) {
														
															lmtObj.setChngOfProjScopeNonInfraLevel3(dto.getEscodRevisionReasonQ6L3());
															
															//to set scopeQ1FlagL3
															if (dto.getScopeQ1FlagL3() != null && !dto.getScopeQ1FlagL3().trim().isEmpty()) {
																
																	lmtObj.setChangeInScopeBeforeSCODFlagL3(dto.getScopeQ1FlagL3());
															} else {
																	lmtObj.setChangeInScopeBeforeSCODFlagL3("");
															}
															//to set scopeQ2FlagL3
															if (dto.getScopeQ2FlagL3() != null && !dto.getScopeQ2FlagL3().trim().isEmpty()) {
																
																	lmtObj.setCostOverrunOrg25CostViabilityFlagL3(dto.getScopeQ2FlagL3());
															} else {
																	lmtObj.setCostOverrunOrg25CostViabilityFlagL3("");
															}
															//to set scopeQ3FlagL3
															if (dto.getScopeQ3FlagL3() != null && !dto.getScopeQ3FlagL3().trim().isEmpty()) {
																
																	lmtObj.setProjectViabReassesedFlagL3(dto.getScopeQ3FlagL3());
															} else {
																	lmtObj.setProjectViabReassesedFlagL3("");
															}
													} else {
															lmtObj.setChngOfProjScopeNonInfraLevel3("");
													}
												}
												
												//to set revisedEscodL3
												if (dto.getRevisedEscodL3() != null && !dto.getRevisedEscodL3().toString().trim().isEmpty()) {
													try {
															lmtObj.setRevsedESCODStpDateL3(relationshipDateFormat.parse(dto.getRevisedEscodL3().trim()));
																		
													}catch (ParseException e) {
														e.printStackTrace();
													}
												}else {
													lmtObj.setRevsedESCODStpDateL3(null);
												}
												//to set revAssetClassL3
												if(dto.getRevAssetClassL3()!=null && !dto.getRevAssetClassL3().trim().isEmpty()){
													Object revAssetClassL1Obj = masterObj.getObjectByEntityName("entryCode", dto.getRevAssetClassL3().trim(),
															"revAssetClass",errors,"REV_ASSET_CLASS_ID");
														lmtObj.setRevisedAssetClass_L3(((ICommonCodeEntry)revAssetClassL1Obj).getEntryCode());
														//to set revAssetClassDtL3
														if(!dto.getRevAssetClassL3().equals("1")) {
															if (dto.getRevAssetClassDtL3() != null && !dto.getRevAssetClassDtL3().toString().trim().isEmpty()) {
																try {
																		lmtObj.setRevsdAssClassDate_L3(relationshipDateFormat.parse(dto.getRevAssetClassDtL3().trim()));
																	 
																} catch (ParseException e) {
																	e.printStackTrace();
																}
															} else {
																lmtObj.setRevsdAssClassDate_L3(null);
															}
														} else {
															
															if(dto.getRevAssetClassDtL3() != null && !dto.getRevAssetClassDtL3().toString().trim().isEmpty()) {
																try {
																		lmtObj.setRevsdAssClassDate_L3(relationshipDateFormat.parse(dto.getRevAssetClassDtL3().trim()));
																		
																} catch (ParseException e) {
																	e.printStackTrace();
																}
															}else {
																lmtObj.setRevsdAssClassDate_L3(null);
																
															}
														}
												} else {
													lmtObj.setRevisedAssetClass_L3("");
												}
											}//end 3rd Level delay
											
										}//end scodObj instanceof ActionErrors
										
									} else {
										lmtObj.setLelvelDelaySCOD("");
										
//										errors.add("delaylevel", new ActionMessage("error.delaylevel.mandatory"));
										
										/*if("Y".equals(dto.getDelayFlagL1())) {
											errors.add("delaylevel", new ActionMessage("error.delaylevel.mandatory"));
										}*/
										
										if(dto.getRevAssetClass()!=null && !dto.getRevAssetClass().trim().isEmpty()){
											Object scodObj = masterObj.getObjectByEntityName("entryCode", dto.getRevAssetClass().trim(),
													"revAssetClass",errors,"REV_ASSET_CLASS_ID");
											if(!(scodObj instanceof ActionErrors)){
												lmtObj.setRevisedAssetClass(((ICommonCodeEntry)scodObj).getEntryCode());
												//to set revAssetClassDt
												if(!dto.getRevAssetClass().equals("1")) {
													if (dto.getRevAssetClassDt() != null && !dto.getRevAssetClassDt().toString().trim().isEmpty()) {
														try {
															lmtObj.setRevsdAssClassDate(relationshipDateFormat.parse(dto.getRevAssetClassDt().trim()));

														} catch (ParseException e) {
															e.printStackTrace();
														}
													} else {
														lmtObj.setRevsdAssClassDate(null);
													}
												} else {
													
													if(dto.getRevAssetClassDt() != null && !dto.getRevAssetClassDt().toString().trim().isEmpty()) {
													try {
															lmtObj.setRevsdAssClassDate(relationshipDateFormat.parse(dto.getRevAssetClassDt().trim()));
													} catch (ParseException e) {
														e.printStackTrace();
													}
													}else {
														lmtObj.setRevsdAssClassDate(null);
													}
												}
											}/*else {
												errors.add("revAssetClass",new ActionMessage("error.revAssetClass.invalid"));
											} */
										}else{
											lmtObj.setRevisedAssetClass("");
											}
										
									}
								}//end of to set level details
							}//end of limit released
					 
			
					 if(dto.getDelaylevel() == null || "".equals(dto.getDelaylevel())) {
							lmtObj.setLelvelDelaySCOD("N");
						}else if("2".equals(dto.getDelaylevel())) {
							lmtObj.setLelvelDelaySCOD(dto.getDelaylevel());
						}else if("3".equals(dto.getDelaylevel())) {
							lmtObj.setLelvelDelaySCOD(dto.getDelaylevel());
						}
					 
			} else {
				errors.add("limitReleaseFlg", new ActionMessage("error.limitReleaseFlg.mandatory"));
			}
	
		}//end of WS_FAC_EDIT_SCOD_REST event
		return lmtObj;
		
	}
	public static boolean isValidDate(String inDate) {
		 SimpleDateFormat ddMMMyyyyDateformat = new SimpleDateFormat("dd/MMM/yyyy");
		 ddMMMyyyyDateformat.setLenient(false);
	        try {
	        	ddMMMyyyyDateformat.parse(inDate.trim());
	        } catch (ParseException pe) {
	            return false;
	        }
	        return true;
	    }
	public FacilitySCODDetailRequestDTO getSCODformValidationRest(FacilitySCODDetailRestRequestDTO requestDTO) {

		FacilitySCODDetailRequestDTO facilityDetailRequestDTO  = new FacilitySCODDetailRequestDTO();
        ActionErrors errors = new ActionErrors();
        /*if(  "WS_SCOD_FAC_EDIT".equalsIgnoreCase(requestDTO.getEvent()) 
            || "WS_SCOD_FAC_EDIT_INITIAL".equalsIgnoreCase(requestDTO.getEvent()))*/
        if (null!= requestDTO.getEvent()&& requestDTO.getEvent().equalsIgnoreCase("WS_FAC_EDIT_SCOD_REST"))
        {

        //SCOD LEVEL 1
        if(!"".equals(requestDTO.getDelaylevel()) && requestDTO.getDelaylevel() != null 
&& "1".equals(requestDTO.getDelaylevel())) {
            if((!"".equals(requestDTO.getScod()) && requestDTO.getScod() != null)) {
                if((!"".equals(requestDTO.getEscodL1()) && requestDTO.getEscodL1() != null )) {        
                    if("Y".equals(requestDTO.getInfraFlag())){
                        Calendar date1 = Calendar.getInstance();
                        date1.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL1()));
                        Calendar date2 = Calendar.getInstance();
                        Calendar date3 = Calendar.getInstance();
                        date2.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()));
                        date3.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()));
                        date3.add(Calendar.DATE,1);
                        date2.add(Calendar.YEAR,2);
                        date2.add(Calendar.DATE,-1);
                        if(date1.after(date2)) {
                            errors.add("escodLevelOneDelayDate", new ActionMessage("error.escodLevelOneDelayDate.dateValidationInfra"));
                        }else if ((requestDTO.getEscodL1().length() > 0)
&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL1()).before(date3.getTime()))
                        {
                            errors.add("escodLevelOneDelayDate", new ActionMessage("error.date.before.notallowed.scodDate"));
                        }
                    }
                }
                if((!"".equals(requestDTO.getEscodL1()) && requestDTO.getEscodL1() != null )) {
                    if("N".equals(requestDTO.getInfraFlag())){
                        Calendar date1 = Calendar.getInstance();
                        date1.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL1()));
                        Calendar date2 = Calendar.getInstance();
                        date2.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()));
                        date2.add(Calendar.YEAR,1);
                        date2.add(Calendar.DATE,-1);
                        Calendar date3 = Calendar.getInstance();
                        date3.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()));
                        date3.add(Calendar.DATE,1);
                        if(date1.after(date2)) {
                            errors.add("escodLevelOneDelayDate", new ActionMessage("error.escodLevelOneDelayDate.dateValidationNonInfra"));
                        }else if ((requestDTO.getEscodL1().length() > 0)
&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL1()).before(date3.getTime()))
                        {
                            errors.add("escodLevelOneDelayDate", new ActionMessage("error.date.before.notallowed.scodDate"));
                        }
                    }
                }
                if("".equals(requestDTO.getEscodL1()) || requestDTO.getEscodL1() == null) {
                    errors.add("escodLevelOneDelayDate", new ActionMessage("error.string.mandatory"));     
                }
            }
        }



        if(("".equals(requestDTO.getRevAssetClassL1()) || requestDTO.getRevAssetClassL1() == null)
&& ("1".equals(requestDTO.getDelaylevel()))){
            errors.add("revisedAssetClassL1", new ActionMessage("error.string.mandatory")); 
        }
        if(!"1".equals(requestDTO.getRevAssetClassL1()) && ("".equals(requestDTO.getRevAssetClassDtL1())||requestDTO.getRevAssetClassDtL1() == null) 
&& "1".equals(requestDTO.getDelaylevel())){
            errors.add("revsdAssClassDateL1", new ActionMessage("error.string.mandatory")); 
        }
        if(("".equals(requestDTO.getDefReasonL1()) || requestDTO.getDefReasonL1() == null)
&& ("1".equals(requestDTO.getDelaylevel()))){
            errors.add("reasonLevelOneDelay", new ActionMessage("error.string.mandatory")); 
        }
        //SCOD LEVEL 2
        if(!"".equals(requestDTO.getDelaylevel()) && requestDTO.getDelaylevel() != null 
&& "2".equals(requestDTO.getDelaylevel())) {
            if((!"".equals(requestDTO.getScod()) && requestDTO.getScod() != null) ) {
                if((!"".equals(requestDTO.getEscodL2()) && requestDTO.getEscodL2() != null )) {
                    if("Y".equals(requestDTO.getInfraFlag())) {
                        Calendar date1 = Calendar.getInstance();
                        date1.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL2()));
                        Calendar date2 = Calendar.getInstance();
                        date2.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()));
                        date2.add(Calendar.YEAR,4);
                        Calendar date3 = Calendar.getInstance();
                        date3.add(Calendar.DATE,1);
                        date3.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()));
                        date2.add(Calendar.DATE,-1);
                        if(date1.after(date2)) {
                            errors.add("escodLevelSecondDelayDate", new ActionMessage("error.escodLevelSecondDelayDate.dateValidationInfra"));
                        }else if ((requestDTO.getEscodL2().length() > 0)
&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL2()).before(date3.getTime()))
                        {
                            errors.add("escodLevelSecondDelayDate", new ActionMessage("error.date.before.notallowed.scodDate"));
                        }else if ((requestDTO.getEscodL2().length() > 0)
&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL2()).equals(date3.getTime()))
                        {
                            errors.add("escodLevelSecondDelayDate", new ActionMessage("error.date.before.notallowed.scodDate"));
                        }
                    }
                }



                if((!"".equals(requestDTO.getEscodL2()) && requestDTO.getEscodL2() != null )) {
                    if("N".equals(requestDTO.getInfraFlag())){
                        Calendar date1 = Calendar.getInstance();
                        date1.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL2()));
                        Calendar date2 = Calendar.getInstance();
                        date2.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()));
                        date2.add(Calendar.YEAR,2);
                        date2.add(Calendar.DATE,-1);
                        Calendar date3 = Calendar.getInstance();
                        date3.add(Calendar.DATE,1);
                        date3.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()));
                        if(date1.after(date2)) {
                            errors.add("escodLevelSecondDelayDate", new ActionMessage("error.escodLevelSecondDelayDate.dateValidationNonInfra"));
                        }else if ((requestDTO.getEscodL2().length() > 0)
&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL2()).before(date3.getTime()))
                        {
                            errors.add("escodLevelSecondDelayDate", new ActionMessage("error.date.before.notallowed.scodDate"));
                        }else if ((requestDTO.getEscodL2().length() > 0)
&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL2()).equals(date3.getTime()))
                        {
                            errors.add("escodLevelSecondDelayDate", new ActionMessage("error.date.before.notallowed.scodDate"));
                        }
                    }
                }


                if("".equals(requestDTO.getEscodL2()) || requestDTO.getEscodL2() == null) {
                    errors.add("escodLevelSecondDelayDate", new ActionMessage("error.string.mandatory"));     
                }
            }
        }


        if(("".equals(requestDTO.getRevAssetClassL2()) || requestDTO.getRevAssetClassL2() == null) 
&& ("2".equals(requestDTO.getDelaylevel()))){
            errors.add("revisedAssetClass_L2", new ActionMessage("error.string.mandatory")); 
        }
        if(!"1".equals(requestDTO.getRevAssetClassL2()) && ("".equals(requestDTO.getRevAssetClassDtL2())||requestDTO.getRevAssetClassDtL2() == null) 
&& "2".equals(requestDTO.getDelaylevel())){
            errors.add("revsdAssClassDate_L2", new ActionMessage("error.string.mandatory")); 
        }
        if(("".equals(requestDTO.getDefReasonL2()) || requestDTO.getDefReasonL2() == null)
&& ("2".equals(requestDTO.getDelaylevel()))){
            errors.add("reasonLevelTwoDelay", new ActionMessage("error.string.mandatory")); 
        }
        //SCOD DELAY LEVEL 3
        if(!"".equals(requestDTO.getDelaylevel()) && requestDTO.getDelaylevel() != null 
&& "3".equals(requestDTO.getDelaylevel())) {
            if((!"".equals(requestDTO.getScod()) && requestDTO.getScod() != null)) {
                if((!"".equals(requestDTO.getEscodL3()) && requestDTO.getEscodL3() != null)) {
                    if("Y".equals(requestDTO.getInfraFlag())) {
                        Calendar date1 = Calendar.getInstance();
                        date1.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL3()));
                        Calendar date2 = Calendar.getInstance();
                        date2.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()));
                        date2.add(Calendar.YEAR,4);
                        Calendar date3 = Calendar.getInstance();
                        date3.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()));
                        date3.add(Calendar.DATE,1);
                        //date2.add(Calendar.DATE,-1);
                        if(date1.before(date2)) {
                            errors.add("escodLevelThreeDelayDate", new ActionMessage("error.escodLevelThreeDelayDate.dateValidationInfra"));
                        }else if ((requestDTO.getEscodL3().length() > 0)
&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL3()).before(date3.getTime()))
                        {
                            errors.add("escodLevelThreeDelayDate", new ActionMessage("error.date.before.notallowed.scodDate"));
                        }
                    }
                }


                if((!"".equals(requestDTO.getEscodL3()) && requestDTO.getEscodL3() != null )) {
                    if("N".equals(requestDTO.getInfraFlag())){
                        Calendar date1 = Calendar.getInstance();
                        date1.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL3()));
                        Calendar date2 = Calendar.getInstance();
                        date2.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()));
                        date2.add(Calendar.YEAR,2);
                        Calendar date3 = Calendar.getInstance();
                        date3.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()));
                        date3.add(Calendar.DATE,1);
                        //date2.add(Calendar.DATE,1);
                        if(date1.before(date2)) {
                            errors.add("escodLevelThreeDelayDate", new ActionMessage("error.escodLevelThreeDelayDate.dateValidationNonInfra"));
                        }else if ((requestDTO.getEscodL3().length() > 0)
&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL3()).before(date3.getTime()))
                        {
                            errors.add("escodLevelThreeDelayDate", new ActionMessage("error.date.before.notallowed.scodDate"));
                        }
                    }
                }



                if("".equals(requestDTO.getEscodL3()) || requestDTO.getEscodL3() == null) {
                    errors.add("escodLevelThreeDelayDate", new ActionMessage("error.string.mandatory"));     
                }
            }
        }



        if(("".equals(requestDTO.getRevAssetClassL3()) || requestDTO.getRevAssetClassL3() == null) 
&& ("3".equals(requestDTO.getDelaylevel()))){
            errors.add("revisedAssetClass_L3", new ActionMessage("error.string.mandatory")); 
        }
        if(!"1".equals(requestDTO.getRevAssetClassL3()) && ("".equals(requestDTO.getRevAssetClassDtL3())||requestDTO.getRevAssetClassDtL3() == null) 
&& "3".equals(requestDTO.getDelaylevel())){
            errors.add("revsdAssClassDate_L3", new ActionMessage("error.string.mandatory")); 
        }
        if(("".equals(requestDTO.getDefReasonL3()) || requestDTO.getDefReasonL3() == null)
&& ("3".equals(requestDTO.getDelaylevel()))){
            errors.add("reasonLevelThreeDelay", new ActionMessage("error.string.mandatory")); 
        }



        //SCOD LEVEL 1
        if(!"".equals(requestDTO.getDelaylevel()) && requestDTO.getDelaylevel() != null 
&& "1".equals(requestDTO.getDelaylevel())) {
            if((!"".equals(requestDTO.getScod()) && requestDTO.getScod() != null)) {
                if((!"".equals(requestDTO.getRevisedEscodL1()) && null!= requestDTO.getRevisedEscodL1() && "Y".equals(requestDTO.getInfraFlag()))) {
                    Calendar date1 = Calendar.getInstance();
                    date1.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getRevisedEscodL1()));
                    Calendar date2 = Calendar.getInstance();
                    Calendar date3 = Calendar.getInstance();
                    date2.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()));
                    date3.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()));
                    date3.add(Calendar.DATE,1);
                    date2.add(Calendar.YEAR,2);
                    date2.add(Calendar.DATE,-1);
                    if(date1.after(date2)) {
                        errors.add("revsedESCODStpDate", new ActionMessage("error.rescodLevelOneDelayDate.dateValidationInfra"));
                    }else if ((requestDTO.getRevisedEscodL1().length() > 0)
&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getRevisedEscodL1()).before(date3.getTime()))
                    {
                        errors.add("revsedESCODStpDate", new ActionMessage("error.redate.before.notallowed.escodDate"));
                    }            
                }else if((!"".equals(requestDTO.getRevisedEscodL1()) && requestDTO.getRevisedEscodL1() != null && "N".equals(requestDTO.getInfraFlag()))) {
                    Calendar date1 = Calendar.getInstance();
                    date1.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getRevisedEscodL1()));
                    Calendar date2 = Calendar.getInstance();
                    date2.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod()));
                    date2.add(Calendar.YEAR,1);
                    date2.add(Calendar.DATE,-1);
                    if(date1.after(date2)) {
                        errors.add("revsedESCODStpDate", new ActionMessage("error.rescodLevelOneDelayDate.dateValidationNonInfra"));
                    }else if ((requestDTO.getRevisedEscodL1().length() > 0)
&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getRevisedEscodL1()).before(DateUtil.convertDate(Locale.getDefault(), requestDTO.getScod())))
                    {
                        errors.add("revsedESCODStpDate", new ActionMessage("error.redate.before.notallowed.escodDate"));
                    }
                }
            }
        }


        //SCOD LEVEL 2
        if(!"".equals(requestDTO.getDelaylevel()) && requestDTO.getDelaylevel() != null 
&& "2".equals(requestDTO.getDelaylevel()) && null != requestDTO.getRevisedEscodL2()) {
            if((!"".equals(requestDTO.getScod()) && requestDTO.getScod() != null) ) {
                if((!"".equals(requestDTO.getRevisedEscodL2()) && requestDTO.getRevisedEscodL2() != null )) {
                    if("Y".equals(requestDTO.getInfraFlag())){
                        Calendar date1 = Calendar.getInstance();
                        date1.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getRevisedEscodL2()));
                        Calendar date2 = Calendar.getInstance();
                        date2.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL2()));
                        date2.add(Calendar.YEAR,1);
                        Calendar date3 = Calendar.getInstance();                        
                        date3.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL2()));
                        date3.add(Calendar.DATE,1);
                        Calendar date4 = Calendar.getInstance();
                        date4.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL2()));
                        date4.add(Calendar.YEAR,2);
                        date2.add(Calendar.DATE,-1);
                        date4.add(Calendar.DATE,-1);
                        if(date1.after(date4) && "Y".equals(requestDTO.getEscodRevisionReasonQ2L2()) && "Y".equals(requestDTO.getEscodRevisionReasonQ1L2())) {
                            errors.add("revsedESCODStpDateL2", new ActionMessage("error.rescodLevelSecondDelayDate.dateValidationInfraRsnbynd"));
                        }
                        else if(date1.after(date4) && "Y".equals(requestDTO.getEscodRevisionReasonQ2L2()) && "N".equals(requestDTO.getEscodRevisionReasonQ1L2())) {
                            errors.add("revsedESCODStpDateL2", new ActionMessage("error.rescodLevelSecondDelayDate.dateValidationInfraRsnbynd"));
                        }else if(date1.after(date2) && "Y".equals(requestDTO.getEscodRevisionReasonQ1L2()) && "N".equals(requestDTO.getEscodRevisionReasonQ2L2())){
                            errors.add("revsedESCODStpDateL2", new ActionMessage("error.rescodLevelSecondDelayDate.dateValidationInfraLglArb"));
                        }else if ((requestDTO.getRevisedEscodL2().length() > 0)
&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getRevisedEscodL2()).before(date3.getTime()))
                        {
                            errors.add("revsedESCODStpDateL2", new ActionMessage("error.redate.before.notallowed.escodDate"));
                        }
                    }
                }



                if((!"".equals(requestDTO.getRevisedEscodL2()) && requestDTO.getRevisedEscodL2() != null)) {



                    if( "N".equals(requestDTO.getInfraFlag())) {
                        Calendar date1 = Calendar.getInstance();
                        date1.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getRevisedEscodL2()));
                        Calendar date2 = Calendar.getInstance();
                        date2.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL2()));
                        date2.add(Calendar.YEAR,1);
                        date2.add(Calendar.DATE,-1);
                        Calendar date3 = Calendar.getInstance();                        
                        date3.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL2()));
                        date3.add(Calendar.DATE,1);
                        if(date1.after(date2)) {
                            errors.add("revsedESCODStpDateL2", new ActionMessage("error.rescodLevelSecondDelayDate.dateValidationNonInfra"));
                        }else if ((requestDTO.getRevisedEscodL2().length() > 0)
&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getRevisedEscodL2()).before(date3.getTime()))
                        {
                            errors.add("revsedESCODStpDateL2", new ActionMessage("error.redate.before.notallowed.escodDate"));
                        }
                    }
                }
            }



            if("".equals(requestDTO.getRevisedEscodL2()) || requestDTO.getRevisedEscodL2() == null) {
            errors.add("revsedESCODStpDateL2", new ActionMessage("error.string.mandatory"));     
        }
        }



        //SCOD LEVEL 3
        if(!"".equals(requestDTO.getDelaylevel()) && requestDTO.getDelaylevel() != null 
&& "3".equals(requestDTO.getDelaylevel()) && null != requestDTO.getRevisedEscodL3()) {
            if((!"".equals(requestDTO.getScod()) && requestDTO.getScod() != null)) {
                if((  null != requestDTO.getRevisedEscodL3()&& !"".equals(requestDTO.getRevisedEscodL3()))) {
                    if("Y".equals(requestDTO.getInfraFlag())){
                        Calendar date1 = Calendar.getInstance();
                        date1.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getRevisedEscodL3()));
                        Calendar date2 = Calendar.getInstance();
                        date2.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL3()));
                        date2.add(Calendar.YEAR,1);
                        Calendar date3 = Calendar.getInstance();
                        date3.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL3()));
                        date3.add(Calendar.DATE,1);
                        Calendar date4 = Calendar.getInstance();
                        date4.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL3()));
                        date4.add(Calendar.YEAR,2);
                        //date2.add(Calendar.DATE,1);
                        //date4.add(Calendar.DATE,1);
                        if(date1.before(date4) && "Y".equals(requestDTO.getEscodRevisionReasonQ2L3())) {
                            errors.add("revsedESCODStpDateL3", new ActionMessage("error.rescodLevelThreeDelayDate.dateValidationInfraRsnbynd"));
                        }else if(date1.before(date2) && "Y".equals(requestDTO.getEscodRevisionReasonQ1L3())){
                            errors.add("revsedESCODStpDateL3", new ActionMessage("error.rescodLevelThreeDelayDate.dateValidationInfraLglArb"));
                        }else if ((requestDTO.getRevisedEscodL3().length() > 0)
&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getRevisedEscodL3()).before(date3.getTime()))
                        {
                            errors.add("revsedESCODStpDateL3", new ActionMessage("error.redate.before.notallowed.escodDate"));
                        }
                    }
                }
                if((!"".equals(requestDTO.getRevisedEscodL3()) && requestDTO.getRevisedEscodL3() != null )) {



                    if("N".equals(requestDTO.getInfraFlag())){
                        Calendar date1 = Calendar.getInstance();
                        date1.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getRevisedEscodL3()));
                        Calendar date2 = Calendar.getInstance();
                        date2.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL3()));
                        date2.add(Calendar.YEAR,1);
                        //date2.add(Calendar.DATE,1);
                        Calendar date3 = Calendar.getInstance();
                        date3.setTime(DateUtil.convertDate(Locale.getDefault(), requestDTO.getEscodL3()));
                        date3.add(Calendar.DATE,1);
                        if(date1.before(date2)) {
                            errors.add("revsedESCODStpDateL3", new ActionMessage("error.rescodLevelThreeDelayDate.dateValidationNonInfra"));
                        }else if ((requestDTO.getRevisedEscodL3().length() > 0)
&& DateUtil.convertDate(Locale.getDefault(), requestDTO.getRevisedEscodL3()).before(date3.getTime()))
                        {
                            errors.add("revsedESCODStpDateL3", new ActionMessage("error.redate.before.notallowed.escodDate"));
                        }
                    }



                }
            }



            if("".equals(requestDTO.getRevisedEscodL3()) || requestDTO.getRevisedEscodL3() == null) {
            errors.add("revsedESCODStpDateL3", new ActionMessage("error.string.mandatory"));     
        }
        }



        if("2".equals(requestDTO.getDelaylevel()) && "Y".equals(requestDTO.getEscodRevisionReasonQ1L2())) {
            if("".equals(requestDTO.getLegalDetailL2())){
                errors.add("leaglOrArbiProceed", new ActionMessage("error.string.mandatory")); 
            }



        }



        if("2".equals(requestDTO.getDelaylevel()) && "Y".equals(requestDTO.getEscodRevisionReasonQ2L2())) {
            if("".equals(requestDTO.getBeyondControlL2())) {
                errors.add("detailsRsnByndPro", new ActionMessage("error.string.mandatory"));
            }
        }
        if("Y".equals(requestDTO.getInfraFlag())) {
            if("2".equals(requestDTO.getDelaylevel()) && "N".equals(requestDTO.getEscodRevisionReasonQ2L2()) && "N".equals(requestDTO.getEscodRevisionReasonQ1L2())
&& "N".equals(requestDTO.getEscodRevisionReasonQ3L2()) && "N".equals(requestDTO.getEscodRevisionReasonQ4L2())) {
                errors.add("reasonForRevisionInfraDelayLvl2", new ActionMessage("error.reaForRevisonLvlDelay.mandatory"));
            }
        }else {



            if("2".equals(requestDTO.getDelaylevel()) && "N".equals(requestDTO.getEscodRevisionReasonQ5L2()) && "N".equals(requestDTO.getEscodRevisionReasonQ6L2())) {
                errors.add("reasonForRevisionNonInfraDelayLvl2", new ActionMessage("error.reaForRevisonLvlDelay.mandatory"));
            }
        }



        if("2".equals(requestDTO.getDelaylevel()) && requestDTO.getDefReasonL2() != null && !"".equals(requestDTO.getDefReasonL2())) {
            if(requestDTO.getDefReasonL2().length() > 200) {
                errors.add("reasonLevelTwoDelay", new ActionMessage("error.lengthofreason.exceed"));
            }



        }



        if("2".equals(requestDTO.getDelaylevel()) && requestDTO.getLegalDetailL2() != null && !"".equals(requestDTO.getLegalDetailL2())) {
            if(requestDTO.getLegalDetailL2().length() > 200) {
                errors.add("leaglOrArbiProceed", new ActionMessage("error.lengthOfLegOrbReason.exceed"));
            }
        }



        if("2".equals(requestDTO.getDelaylevel()) && requestDTO.getBeyondControlL2() != null && !"".equals(requestDTO.getBeyondControlL2())) {
            if(requestDTO.getBeyondControlL2().length() > 4000) {
                errors.add("detailsRsnByndPro", new ActionMessage("error.lengthOfBeyondReason.exceed"));
            }
        }



        if("3".equals(requestDTO.getDelaylevel()) && requestDTO.getBeyondControlL3() != null && !"".equals(requestDTO.getBeyondControlL3())) {
            if(requestDTO.getBeyondControlL3().length() > 4000) {
                errors.add("detailsRsnByndProLevel3", new ActionMessage("error.lengthOfBeyondReason.exceed"));
            }
        }



        if("3".equals(requestDTO.getDelaylevel()) && requestDTO.getLegalDetailL3() != null && !"".equals(requestDTO.getLegalDetailL3())) {
            if(requestDTO.getLegalDetailL3().length() > 4000) {
                errors.add("leaglOrArbiProceedLevel3", new ActionMessage("error.lengthOfLegOrbReason.exceed"));
            }
        }



        if("3".equals(requestDTO.getDelaylevel()) && requestDTO.getDefReasonL3() != null && !"".equals(requestDTO.getDefReasonL3())) {
            if(requestDTO.getDefReasonL3().length() > 200) {
                errors.add("reasonLevelThreeDelay", new ActionMessage("error.lengthofreason.exceed"));
            }



        }




        if("Y".equals(requestDTO.getInfraFlag())) {
            if("3".equals(requestDTO.getDelaylevel()) && "N".equals(requestDTO.getEscodRevisionReasonQ2L3()) && "N".equals(requestDTO.getEscodRevisionReasonQ1L3())
&& "N".equals(requestDTO.getEscodRevisionReasonQ3L3()) && "N".equals(requestDTO.getEscodRevisionReasonQ4L3())) {
                errors.add("reasonForRevisionInfraDelayLvl3", new ActionMessage("error.reaForRevisonLvlDelay.mandatory"));
            }
        }else {



            if("3".equals(requestDTO.getDelaylevel()) && "N".equals(requestDTO.getEscodRevisionReasonQ5L3()) && "N".equals(requestDTO.getEscodRevisionReasonQ6L3())) {
                errors.add("reasonForRevisionNonInfraDelayLvl3", new ActionMessage("error.reaForRevisonLvlDelay.mandatory"));
            }
        }



        if("3".equals(requestDTO.getDelaylevel()) && "Y".equals(requestDTO.getEscodRevisionReasonQ1L3())) {
            if("".equals(requestDTO.getLegalDetailL3())){
                errors.add("leaglOrArbiProceedLevel3", new ActionMessage("error.string.mandatory")); 
            }



        }



        if("3".equals(requestDTO.getDelaylevel()) && "Y".equals(requestDTO.getEscodRevisionReasonQ2L3())) {
            if("".equals(requestDTO.getBeyondControlL3())) {
                errors.add("detailsRsnByndProLevel3", new ActionMessage("error.string.mandatory"));
            }
        }




    }
        facilityDetailRequestDTO.setErrors(errors);
		return facilityDetailRequestDTO;
    }
}