package com.aurionpro.clims.rest.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.springframework.stereotype.Service;
import com.aurionpro.clims.rest.dto.CamDetailsRestRequestDTO;
import com.aurionpro.clims.rest.dto.UdfRestRequestDTO;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ILimitProfileUdf;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.ui.manualinput.aa.AADetailForm;

@Service
public class CamDetailsRestDTOMapper {
	

	UdfDetailsRestDTOMapper udfDetailsRestDTOMapper = new UdfDetailsRestDTOMapper();
	
	public void setUdfDetailsRestDTOMapper(UdfDetailsRestDTOMapper udfDetailsRestDTOMapper) {
		this.udfDetailsRestDTOMapper = udfDetailsRestDTOMapper;
	}

	public ILimitProfile getActualFromDTO(CamDetailsRestRequestDTO dto, ILimitProfile actualLimitProfile) {

		SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yyyy");
		date.setLenient(false);
		SimpleDateFormat ramRatingDate = new SimpleDateFormat("dd/MMM/yyyy");
		ramRatingDate.setLenient(false);
		
		ILimitProfile newLimitProfile = null;
		if (actualLimitProfile != null) {
			newLimitProfile = actualLimitProfile;
		} else {
			newLimitProfile = new OBLimitProfile();
		}
		ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
		String partyId = "";
		if (dto.getPartyId() != null && !dto.getPartyId().trim().isEmpty()) {
			partyId = dto.getPartyId().trim();
		}
		ICMSCustomer cust = custProxy.getCustomerByCIFSource(partyId, null);
		newLimitProfile.setCustomerID(cust.getCustomerID());
		newLimitProfile.setLEReference(partyId);

		if (dto.getCamNumber() != null && !dto.getCamNumber().trim().isEmpty()) {
			newLimitProfile.setBCAReference(dto.getCamNumber().trim());
		} else {
			newLimitProfile.setBCAReference("");
		}

		if (dto.getCamType() != null && !dto.getCamType().trim().isEmpty()) {
			newLimitProfile.setCamType(dto.getCamType().trim());
		} else {
			newLimitProfile.setCamType("");
		}

//		IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
//		IGeneralParamEntry generalParamEntries = generalParamDao
//				.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
//
//		newLimitProfile.setCamLoginDate(new Date(generalParamEntries.getParamValue()));
		newLimitProfile.setCamLoginDate(new java.util.Date());

		try {
			if (dto.getCamDate() != null && !dto.getCamDate().trim().isEmpty()) {
				
				newLimitProfile.setApprovalDate(date.parse(date.format(ramRatingDate.parse(dto.getCamDate().trim()))));
				Date d1 = null;
				if(dto.getExpiryDate() != null && !dto.getExpiryDate().trim().isEmpty()) {
					newLimitProfile.setNextAnnualReviewDate(date.parse(date.format(ramRatingDate.parse(dto.getExpiryDate().trim()))));
				} else {
					d1 = date.parse(date.format(ramRatingDate.parse(dto.getCamDate().trim())));
					Calendar c = Calendar.getInstance();
					c.setTime(d1);
					c.add(Calendar.YEAR, 1);
					newLimitProfile.setNextAnnualReviewDate(c.getTime());
				}
				
				if (dto.getExtendedNextReviewDate() != null && !dto.getExtendedNextReviewDate().trim().isEmpty()) {
					newLimitProfile.setExtendedNextReviewDate(date.parse(date.format(ramRatingDate.parse(dto.getExtendedNextReviewDate().trim()))));
				}else if(dto.getExpiryDate() != null && !dto.getExpiryDate().trim().isEmpty()) {
					d1 = date.parse(date.format(ramRatingDate.parse(dto.getExpiryDate().trim())));
					Calendar c = Calendar.getInstance();
					c.setTime(d1);
					c.add(Calendar.DATE, 1);
					newLimitProfile.setExtendedNextReviewDate(c.getTime());
				}else {
					d1 = date.parse(date.format(ramRatingDate.parse(dto.getCamDate().trim())));
					Calendar c = Calendar.getInstance();
					c.setTime(d1);
					c.add(Calendar.YEAR, 1);
					c.add(Calendar.DATE, 1);
					newLimitProfile.setExtendedNextReviewDate(c.getTime());
				}

			} else {
				newLimitProfile.setApprovalDate(null);
				newLimitProfile.setExtendedNextReviewDate(null);
				newLimitProfile.setNextAnnualReviewDate(null);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (dto.getTotalSactionedAmount() != null && !"".equals(dto.getTotalSactionedAmount().trim())) {
			newLimitProfile.setTotalSactionedAmount(Double.parseDouble(dto.getTotalSactionedAmount().trim()));
		} else {
			newLimitProfile.setTotalSactionedAmount(Double.parseDouble("0"));
		}

		if (dto.getRelationshipMgr() != null && !dto.getRelationshipMgr().trim().isEmpty()) {
			newLimitProfile.setRelationshipManager(dto.getRelationshipMgr().trim());
		} else {
			newLimitProfile.setRelationshipManager("");
		}

		if (dto.getCommitteApproval() != null && !dto.getCommitteApproval().trim().isEmpty()) {
			newLimitProfile.setCommitteApproval(dto.getCommitteApproval().trim());
		} else {
			newLimitProfile.setCommitteApproval("");
		}

		if (dto.getFullyCashCollateral() != null && !dto.getFullyCashCollateral().trim().isEmpty()) {
			newLimitProfile.setFullyCashCollateral(dto.getFullyCashCollateral().trim());
		} else {
			newLimitProfile.setFullyCashCollateral("");
		}

		if (dto.getBoardApproval() != null && !dto.getBoardApproval().trim().isEmpty()) {
			newLimitProfile.setBoardApproval(dto.getBoardApproval().trim());
		} else {
			newLimitProfile.setBoardApproval("");
		}

		if (dto.getCreditApproval1() != null && !dto.getCreditApproval1().trim().isEmpty()) {
			newLimitProfile.setApproverEmployeeName1(dto.getCreditApproval1().trim());
		} else {
			newLimitProfile.setApproverEmployeeName1("");
		}

		if (dto.getCreditApproval2() != null && !dto.getCreditApproval2().trim().isEmpty()) {
			newLimitProfile.setApproverEmployeeName2(dto.getCreditApproval2().trim());
		} else {
			newLimitProfile.setApproverEmployeeName2("");
		}

		if (dto.getCreditApproval3() != null && !dto.getCreditApproval3().trim().isEmpty()) {
			newLimitProfile.setApproverEmployeeName3(dto.getCreditApproval3().trim());
		} else {
			newLimitProfile.setApproverEmployeeName3("");
		}

		if (dto.getCreditApproval4() != null && !dto.getCreditApproval4().trim().isEmpty()) {
			newLimitProfile.setApproverEmployeeName4(dto.getCreditApproval4().trim());
		} else {
			newLimitProfile.setApproverEmployeeName4("");
		}

		if (dto.getCreditApproval5() != null && !dto.getCreditApproval5().trim().isEmpty()) {
			newLimitProfile.setApproverEmployeeName5(dto.getCreditApproval5().trim());
		} else {
			newLimitProfile.setApproverEmployeeName5("");
		}

		if (dto.getAssetClassification() != null && !dto.getAssetClassification().trim().isEmpty()) {
			newLimitProfile.setAssetClassification(dto.getAssetClassification().trim());
		} else {
			newLimitProfile.setAssetClassification("");
		}

		if (dto.getRBIAssetClassification() != null && !dto.getRBIAssetClassification().trim().isEmpty()) {
			newLimitProfile.setRbiAssetClassification(dto.getRBIAssetClassification().trim());
		} else {
			newLimitProfile.setRbiAssetClassification("");
		}

		if (dto.getRamRating() != null && !dto.getRamRating().trim().isEmpty()) {
			newLimitProfile.setRamRating(dto.getRamRating().trim());
		} else {
			newLimitProfile.setRamRating("");
		}

		if (dto.getRatingType() != null && !dto.getRatingType().trim().isEmpty()) {
			newLimitProfile.setRamRatingType(dto.getRatingType().trim());
		} else {
			newLimitProfile.setRamRatingType("");
		}

		if (dto.getRamRatingYear() != null && !dto.getRamRatingYear().trim().isEmpty()) {
			newLimitProfile.setRamRatingYear(dto.getRamRatingYear().trim());
		} else {
			newLimitProfile.setRamRatingYear("");
		}
		
		if (dto.getBranch() != null && !dto.getBranch().trim().isEmpty()) {
			newLimitProfile.setControllingBranch(dto.getBranch().trim());
		} else {
			newLimitProfile.setControllingBranch("");
		}
		
		if(dto.getRamRatingFinalizationDate()!=null && !dto.getRamRatingFinalizationDate().trim().isEmpty()){
			try {
				newLimitProfile.setRamRatingFinalizationDate(ramRatingDate.parse(dto.getRamRatingFinalizationDate().trim()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			newLimitProfile.setRamRatingFinalizationDate(null);
		}
		
		if (dto.getDocRemarks() != null && !dto.getDocRemarks().trim().isEmpty()) {
			newLimitProfile.setDocRemarks(dto.getDocRemarks().trim());
		}else {
			newLimitProfile.setDocRemarks("");
		}
		
		if(dto.getIsSpecialApprGridForCustBelowHDB8()!=null && !dto.getIsSpecialApprGridForCustBelowHDB8().trim().isEmpty()){
			newLimitProfile.setIsSpecialApprGridForCustBelowHDB8(dto.getIsSpecialApprGridForCustBelowHDB8().trim());
		}else{
			newLimitProfile.setIsSpecialApprGridForCustBelowHDB8("No");
		}

		newLimitProfile.setIsSingleBorrowerPrudCeiling(dto.getIsSingleBorrowerPrudCeiling());
		if(dto.getIsSingleBorrowerPrudCeiling()!=null && "No".equalsIgnoreCase(dto.getIsSingleBorrowerPrudCeiling())) {
			newLimitProfile.setRbiApprovalForSingle(dto.getRbiApprovalForSingle());
			newLimitProfile.setDetailsOfRbiApprovalForSingle(dto.getDetailsOfRbiApprovalForSingle());
		}else {
			newLimitProfile.setRbiApprovalForSingle("");
			newLimitProfile.setDetailsOfRbiApprovalForSingle("");
		}
		
		newLimitProfile.setIsGroupBorrowerPrudCeiling(dto.getIsGroupBorrowerPrudCeiling());
		if(dto.getIsGroupBorrowerPrudCeiling()!=null && "No".equalsIgnoreCase(dto.getIsGroupBorrowerPrudCeiling())) {
			newLimitProfile.setRbiApprovalForGroup(dto.getRbiApprovalForGroup());
			newLimitProfile.setDetailsOfRbiApprovalForGroup(dto.getDetailsOfRbiApprovalForGroup());
		}else {
			newLimitProfile.setRbiApprovalForGroup("");
			newLimitProfile.setDetailsOfRbiApprovalForGroup("");
		}
		
		if(dto.getIsNonCooperativeBorrowers()!=null && !dto.getIsNonCooperativeBorrowers().trim().isEmpty()) {
			newLimitProfile.setIsNonCooperativeBorrowers(dto.getIsNonCooperativeBorrowers());
			if(dto.getIsNonCooperativeBorrowers().equals("Yes")) {
				newLimitProfile.setIsDirectorAsNonCooperativeForOther(dto.getIsDirectorAsNonCooperativeForOther());
				if(dto.getIsDirectorAsNonCooperativeForOther().equals("Yes")) {
					newLimitProfile.setNameOfDirectorsAndCompany(dto.getNameOfDirectorsAndCompany());
				}else {
					newLimitProfile.setNameOfDirectorsAndCompany(null);
				}
			}else {
				newLimitProfile.setIsDirectorAsNonCooperativeForOther("No");
				newLimitProfile.setNameOfDirectorsAndCompany(null);
			}
		}else {
			newLimitProfile.setIsNonCooperativeBorrowers("No");
			newLimitProfile.setIsDirectorAsNonCooperativeForOther("No");
			newLimitProfile.setNameOfDirectorsAndCompany(null);
		}
		
		newLimitProfile.setUdfData((ILimitProfileUdf[])udfDetailsRestDTOMapper.getUdfActualFromDTO(dto, dto.getEvent()));

		return newLimitProfile;
	}

	public AADetailForm getFormFromDTO(CamDetailsRestRequestDTO dto) {

		SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yyyy");
		date.setLenient(false);
		SimpleDateFormat ramRatingDate = new SimpleDateFormat("dd/MMM/yyyy");
		ramRatingDate.setLenient(false);
		
		AADetailForm form = new AADetailForm();
		if (dto.getCamNumber() != null && !dto.getCamNumber().trim().isEmpty()) {
			form.setAaNum(dto.getCamNumber().trim());
		} else {
			form.setAaNum("");
		}

		if (dto.getCamType() != null && !dto.getCamType().trim().isEmpty()) {
			form.setCamType(dto.getCamType().trim());
		} else {
			form.setCamType("");
		}

//		IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
//		IGeneralParamEntry generalParamEntries = generalParamDao
//				.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
//		form.setCamLoginDate(date.format(new Date(generalParamEntries.getParamValue())));
		form.setCamLoginDate(date.format(new java.util.Date()));

		if (dto.getCamDate() != null && !dto.getCamDate().trim().isEmpty()) {
			form.setAaApprovalDate(dto.getCamDate().trim());
		} else {
			form.setAaApprovalDate("");
		}

		if (dto.getExpiryDate() != null && !dto.getExpiryDate().trim().isEmpty()) {
			form.setAnnualReviewDate(dto.getExpiryDate().trim());
		} else {
			form.setAnnualReviewDate("");
		}
		
		if (dto.getExtendedNextReviewDate() != null && !dto.getExtendedNextReviewDate().trim().isEmpty()) {
			form.setExtendedNextReviewDate(dto.getExtendedNextReviewDate().trim());
		} else {
			form.setExtendedNextReviewDate("");
		}
		
		if (dto.getTotalSactionedAmount() != null && !dto.getTotalSactionedAmount().trim().isEmpty()) {
			form.setTotalSactionedAmount(dto.getTotalSactionedAmount().trim());
		} else {
			form.setTotalSactionedAmount("0");
		}

		form.setTotalSanctionedAmountFacLevel("0");
		if (dto.getRelationshipMgr() != null && !dto.getRelationshipMgr().trim().isEmpty()) {
			form.setRelationshipManager(dto.getRelationshipMgr().trim());
		} else {
			form.setRelationshipManager("");
		}

		if (dto.getCommitteApproval() != null && !dto.getCommitteApproval().trim().isEmpty()) {
			form.setCommitteApproval(dto.getCommitteApproval().trim());
		} else {
			form.setCommitteApproval("");
		}

		if (dto.getFullyCashCollateral() != null && !dto.getFullyCashCollateral().trim().isEmpty()) {
			form.setFullyCashCollateral(dto.getFullyCashCollateral().trim());
		} else {
			form.setFullyCashCollateral("");
		}

		if (dto.getBoardApproval() != null && !dto.getBoardApproval().trim().isEmpty()) {
			form.setBoardApproval(dto.getBoardApproval().trim());
		} else {
			form.setBoardApproval("");
		}

		if (dto.getCreditApproval1() != null && !dto.getCreditApproval1().trim().isEmpty()) {
			form.setCreditApproval1(dto.getCreditApproval1().trim());
		} else {
			form.setCreditApproval1("");
		}

		if (dto.getCreditApproval2() != null && !dto.getCreditApproval2().trim().isEmpty()) {
			form.setCreditApproval2(dto.getCreditApproval2().trim());
		} else {
			form.setCreditApproval2("");
		}

		if (dto.getCreditApproval3() != null && !dto.getCreditApproval3().trim().isEmpty()) {
			form.setCreditApproval3(dto.getCreditApproval3().trim());
		} else {
			form.setCreditApproval3("");
		}

		if (dto.getCreditApproval4() != null && !dto.getCreditApproval4().trim().isEmpty()) {
			form.setCreditApproval4(dto.getCreditApproval4().trim());
		} else {
			form.setCreditApproval4("");
		}

		if (dto.getCreditApproval5() != null && !dto.getCreditApproval5().trim().isEmpty()) {
			form.setCreditApproval5(dto.getCreditApproval5().trim());
		} else {
			form.setCreditApproval5("");
		}

		if (dto.getAssetClassification() != null && !dto.getAssetClassification().trim().isEmpty()) {
			form.setAssetClassification(dto.getAssetClassification().trim());
		} else {
			form.setAssetClassification("");
		}

		if (dto.getRBIAssetClassification() != null && !dto.getRBIAssetClassification().trim().isEmpty()) {
			form.setRbiAssetClassification(dto.getRBIAssetClassification().trim());
		} else {
			form.setRbiAssetClassification("");
		}

		if (dto.getRamRating() != null && !dto.getRamRating().trim().isEmpty()) {
			form.setRamRating(dto.getRamRating().trim());
		} else {
			form.setRamRating("");
		}

		if (dto.getRatingType() != null && !dto.getRatingType().trim().isEmpty()) {
			form.setRamRatingType(dto.getRatingType().trim());
		} else {
			form.setRamRatingType("");
		}

		if (dto.getRamRatingYear() != null && !dto.getRamRatingYear().trim().isEmpty()) {
			form.setRamRatingYear(dto.getRamRatingYear().trim());
		} else {
			form.setRamRatingYear("");
		}

		if (dto.getBranch() != null && !dto.getBranch().trim().isEmpty()) {
			form.setControllingBranch(dto.getBranch().trim());
		} else {
			form.setControllingBranch("");
		}
		
		if(dto.getRamRatingFinalizationDate()!=null && !dto.getRamRatingFinalizationDate().trim().isEmpty()){			
			form.setRamRatingFinalizationDate(dto.getRamRatingFinalizationDate().trim());
		}else{
			form.setRamRatingFinalizationDate(null);
		}
		
		if (dto.getDocRemarks() != null && !dto.getDocRemarks().trim().isEmpty()) {
			form.setDocRemarks(dto.getDocRemarks().trim());
		}else {
			form.setDocRemarks("");
		}
		
		if(dto.getIsSpecialApprGridForCustBelowHDB8()!=null && !dto.getIsSpecialApprGridForCustBelowHDB8().trim().isEmpty()){
			form.setIsSpecialApprGridForCustBelowHDB8(dto.getIsSpecialApprGridForCustBelowHDB8().trim());
		}else{
			form.setIsSpecialApprGridForCustBelowHDB8("No");
		}
		
		if(dto.getIsSingleBorrowerPrudCeiling()!=null && ! dto.getIsSingleBorrowerPrudCeiling().trim().isEmpty()) {
			form.setIsSingleBorrowerPrudCeiling(dto.getIsSingleBorrowerPrudCeiling());
			if("No".equalsIgnoreCase(dto.getIsSingleBorrowerPrudCeiling())) {
				form.setRbiApprovalForSingle(dto.getRbiApprovalForSingle());
				form.setDetailsOfRbiApprovalForSingle(dto.getDetailsOfRbiApprovalForSingle());
			}else {
				form.setRbiApprovalForSingle("");
				form.setDetailsOfRbiApprovalForSingle("");
			}
		}
		
		if(dto.getIsGroupBorrowerPrudCeiling()!=null && ! dto.getIsGroupBorrowerPrudCeiling().trim().isEmpty()) {
			form.setIsGroupBorrowerPrudCeiling(dto.getIsGroupBorrowerPrudCeiling());
			if("No".equalsIgnoreCase(dto.getIsGroupBorrowerPrudCeiling())) {
				form.setRbiApprovalForGroup(dto.getRbiApprovalForGroup());
				form.setDetailsOfRbiApprovalForGroup(dto.getDetailsOfRbiApprovalForGroup());
			}else {
				form.setRbiApprovalForGroup("");
				form.setDetailsOfRbiApprovalForGroup("");
			}
		}
		
		if(dto.getIsNonCooperativeBorrowers()!=null && !dto.getIsNonCooperativeBorrowers().trim().isEmpty()) {
			form.setIsNonCooperativeBorrowers(dto.getIsNonCooperativeBorrowers());
			if(dto.getIsNonCooperativeBorrowers().equals("Yes")) {
				form.setIsDirectorAsNonCooperativeForOther(dto.getIsDirectorAsNonCooperativeForOther());
				if(dto.getIsDirectorAsNonCooperativeForOther().equals("Yes")) {
					form.setNameOfDirectorsAndCompany(dto.getNameOfDirectorsAndCompany());
				}else {
					form.setNameOfDirectorsAndCompany(null);
				}
			}else {
				form.setIsDirectorAsNonCooperativeForOther("No");
				form.setNameOfDirectorsAndCompany(null);
			}
		}else {
			form.setIsNonCooperativeBorrowers("No");
			form.setIsDirectorAsNonCooperativeForOther("No");
			form.setNameOfDirectorsAndCompany(null);
		}
		
		if(dto.getEvent()!=null && !dto.getEvent().trim().isEmpty()){
			form.setEvent(dto.getEvent().trim());
		}else{
			form.setEvent("");
		}
		
		udfDetailsRestDTOMapper.getUdfFormFromDTO(dto, form, dto.getEvent());		

		return form;
	}

	public CamDetailsRestRequestDTO getRequestDTOWithActualValues(CamDetailsRestRequestDTO requestDTO, String camId) {

		SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yyyy");
		date.setLenient(false);
		SimpleDateFormat ramRatingDate = new SimpleDateFormat("dd/MMM/yyyy");
		ramRatingDate.setLenient(false);
		
		MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();

		if(requestDTO.getEvent()!=null && !requestDTO.getEvent().trim().isEmpty() 
				&& requestDTO.getEvent().equalsIgnoreCase("REST_CAM_UPDATE")){
			if(camId==null){
				errors.add("partyId",new ActionMessage("error.cam.notexist"));
			}	
		}
		else if(requestDTO.getEvent()!=null && !requestDTO.getEvent().trim().isEmpty() 
				&& requestDTO.getEvent().equalsIgnoreCase("REST_CAM_CREATE")){
			if(camId!=null){
				errors.add("partyId",new ActionMessage("error.cam.alreadyexist"));
			}
			if(requestDTO.getCamType()!=null && !requestDTO.getCamType().trim().isEmpty()) {
				if(!requestDTO.getCamType().trim().equals("Initial")){
					errors.add("camType",new ActionMessage("error.camType.invalid"));
				}
			}else {
				errors.add("camType",new ActionMessage("error.string.mandatory"));
			}

		}

		if (requestDTO.getPartyId() != null && !requestDTO.getPartyId().trim().isEmpty()) {

			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			String partyId = requestDTO.getPartyId().trim();
			try {
				ICMSCustomer cust = custProxy.getCustomerByCIFSource(partyId, null);
				if (cust != null) {
					requestDTO.setPartyId(partyId);
					requestDTO.setTotalSactionedAmount(cust.getTotalSanctionedLimit());
					requestDTO.setRelationshipMgr(cust.getRelationshipMgr());
					requestDTO.setBranch(cust.getBranchCode());
				} else {
					errors.add("partyId", new ActionMessage("error.partyId.invalid"));
				}
			} catch (Exception e) {
				errors.add("partyId", new ActionMessage("error.partyId.invalid"));
			}
		} else {
			errors.add("partyId", new ActionMessage("error.partyId.empty"));
		}

		if (requestDTO.getCamType() != null && !requestDTO.getCamType().trim().isEmpty()) {
			String camType = requestDTO.getCamType().trim();
			if (camType.equalsIgnoreCase("Annual") || camType.equalsIgnoreCase("Interim")
					|| camType.equalsIgnoreCase("Initial")) {
				requestDTO.setCamType(camType);
			} else {
				errors.add("camType", new ActionMessage("error.camType.invalid"));
			}
		}else {
			errors.add("camType", new ActionMessage("error.string.mandatory"));
		}
		if (requestDTO.getCamNumber() != null && !requestDTO.getCamNumber().trim().isEmpty()) {
			requestDTO.setCamNumber(requestDTO.getCamNumber().trim());
		}

		if (requestDTO.getCamDate() != null && !requestDTO.getCamDate().trim().isEmpty()) {
			try {
				ramRatingDate.parse(requestDTO.getCamDate().trim());
				requestDTO.setCamDate(requestDTO.getCamDate().trim());
			} catch (ParseException e) {
				errors.add("camDate", new ActionMessage("error.cam.date.format"));
			}
		}

		if (requestDTO.getExpiryDate() != null && !requestDTO.getExpiryDate().trim().isEmpty()) {
			try {
				ramRatingDate.parse(requestDTO.getExpiryDate().trim());
				requestDTO.setExpiryDate(requestDTO.getExpiryDate().trim());
			} catch (ParseException e) {
				errors.add("expiryDate", new ActionMessage("error.cam.date.format"));
			}
		}
		if (requestDTO.getExtendedNextReviewDate() != null && !requestDTO.getExtendedNextReviewDate().trim().isEmpty()) {
			try {
				ramRatingDate.parse(requestDTO.getExtendedNextReviewDate().trim());
				requestDTO.setExtendedNextReviewDate(requestDTO.getExtendedNextReviewDate().trim());
			} catch (ParseException e) {
				errors.add("extendedNextReviewDate", new ActionMessage("error.cam.date.format"));
			}
		}
		
		if (requestDTO.getRamRating() != null && !requestDTO.getRamRating().trim().isEmpty()) {
			try {
				Object obj = masterObj.getMasterData("entryCode",
						Long.parseLong(requestDTO.getRamRating().trim()));
				if (obj != null) {
					ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
					if ("RISK_GRADE".equals(codeEntry.getCategoryCode())) {
						requestDTO.setRamRating((codeEntry).getEntryCode());
					} else {
						errors.add("ramRating", new ActionMessage("error.ramRating.invalid"));
					}
				} else {
					errors.add("ramRating", new ActionMessage("error.ramRating.invalid"));
				}
			} catch (Exception e) {
				errors.add("ramRating", new ActionMessage("error.ramRating.invalid"));
			}
		}else {
			errors.add("ramRating", new ActionMessage("error.ramRating.mandatory"));
		}

		Calendar now = Calendar.getInstance(); // Gets the current date and time
		int year = now.get(Calendar.YEAR); // Gets the current date and time.
		if (requestDTO.getRamRatingYear() != null && !"".equals(requestDTO.getRamRatingYear().trim())) {
			if (requestDTO.getRamRatingYear().trim().equals(String.valueOf(year))
					|| requestDTO.getRamRatingYear().trim().equals(String.valueOf(year - 1))
					|| requestDTO.getRamRatingYear().trim().equals(String.valueOf(year - 2))) {
				requestDTO.setRamRatingYear(requestDTO.getRamRatingYear().trim());
			} else {
				errors.add("ramRatingYear", new ActionMessage("error.ramRatingYear.invalid.format"));
			}
		}

		if (requestDTO.getRatingType() != null && !requestDTO.getRatingType().trim().isEmpty()) {
			try {
				Object obj = masterObj.getMasterData("entryCode",
						Long.parseLong(requestDTO.getRatingType().trim()));
				if (obj != null) {
					ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
					if ("SEC_RATING_TYPE".equals(codeEntry.getCategoryCode())) {
						requestDTO.setRatingType((codeEntry).getEntryCode());
					} else {
						errors.add("ratingType", new ActionMessage("error.ratingType.invalid"));
					}

				} else {
					errors.add("ratingType", new ActionMessage("error.ratingType.invalid"));
				}
			} catch (Exception e) {
				errors.add("ratingType", new ActionMessage("error.ratingType.invalid"));
			}
		}

		if (requestDTO.getCreditApproval1() != null && !requestDTO.getCreditApproval1().trim().isEmpty()) {
			Object obj = masterObj.getObjByEntityNameAndApprovalCode("actualCreditApproval",
					requestDTO.getCreditApproval1().trim(), "creditApproval1", errors);
			if (!(obj instanceof ActionErrors)) {
				requestDTO.setCreditApproval1(((ICreditApproval) obj).getApprovalCode());
			}
		}

		if (requestDTO.getCreditApproval2() != null && !requestDTO.getCreditApproval2().trim().isEmpty()) {
			Object obj = masterObj.getObjByEntityNameAndApprovalCode("actualCreditApproval",
					requestDTO.getCreditApproval2().trim(), "creditApproval2", errors);
			if (!(obj instanceof ActionErrors)) {
				requestDTO.setCreditApproval2(((ICreditApproval) obj).getApprovalCode());
			}
		}
		if (requestDTO.getCreditApproval3() != null && !requestDTO.getCreditApproval3().trim().isEmpty()) {
			Object obj = masterObj.getObjByEntityNameAndApprovalCode("actualCreditApproval",
					requestDTO.getCreditApproval3().trim(), "creditApproval3", errors);
			if (!(obj instanceof ActionErrors)) {
				requestDTO.setCreditApproval3(((ICreditApproval) obj).getApprovalCode());
			}
		}
		if (requestDTO.getCreditApproval4() != null && !requestDTO.getCreditApproval4().trim().isEmpty()) {
			Object obj = masterObj.getObjByEntityNameAndApprovalCode("actualCreditApproval",
					requestDTO.getCreditApproval4().trim(), "creditApproval4", errors);
			if (!(obj instanceof ActionErrors)) {
				requestDTO.setCreditApproval4(((ICreditApproval) obj).getApprovalCode());
			}
		}
		if (requestDTO.getCreditApproval5() != null && !requestDTO.getCreditApproval5().trim().isEmpty()) {
			Object obj = masterObj.getObjByEntityNameAndApprovalCode("actualCreditApproval",
					requestDTO.getCreditApproval5().trim(), "creditApproval5", errors);
			if (!(obj instanceof ActionErrors)) {
				requestDTO.setCreditApproval5(((ICreditApproval) obj).getApprovalCode());
			}
		}

		if (requestDTO.getRBIAssetClassification() != null && !requestDTO.getRBIAssetClassification().trim().isEmpty()) {
			try {
				Object obj = masterObj.getMasterData("entryCode",
						Long.parseLong(requestDTO.getRBIAssetClassification().trim()));
				if (obj != null) {
					ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;

					if ("RBI_ASSET_ClASSIFICATION".equals(codeEntry.getCategoryCode())) {
						requestDTO.setRBIAssetClassification((codeEntry).getEntryCode());
					} else {
						errors.add("RbiAssetClassification", new ActionMessage("error.assetClassification.invalid"));
					}

				} else {
					errors.add("RbiAssetClassification", new ActionMessage("error.assetClassification.invalid"));
				}
			} catch (Exception e) {
				errors.add("RbiAssetClassification", new ActionMessage("error.assetClassification.invalid"));
			}
		}

		if (requestDTO.getAssetClassification() != null && !requestDTO.getAssetClassification().trim().isEmpty()) {
			try {
				Object obj = masterObj.getMasterData("entryCode",
						Long.parseLong(requestDTO.getAssetClassification().trim()));
				if (obj != null) {
					ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;

					if ("ASSET_ClASSIFICATION".equals(codeEntry.getCategoryCode())) {
						requestDTO.setAssetClassification((codeEntry).getEntryCode());
					} else {
						errors.add("assetClassification", new ActionMessage("error.assetClassification.invalid"));
					}

				} else {
					errors.add("assetClassification", new ActionMessage("error.assetClassification.invalid"));
				}
			} catch (Exception e) {
				errors.add("assetClassification", new ActionMessage("error.assetClassification.invalid"));
			}
		}

		if (requestDTO.getFullyCashCollateral() != null && !requestDTO.getFullyCashCollateral().trim().isEmpty()) {
			if (!("Y".equalsIgnoreCase(requestDTO.getFullyCashCollateral().trim())
					|| "N".equalsIgnoreCase(requestDTO.getFullyCashCollateral().trim()))) {
				errors.add("fullyCashCollateral", new ActionMessage("error.fullyCashCollateral.invalid"));
			}else {
				requestDTO.setFullyCashCollateral(requestDTO.getFullyCashCollateral());
			}
		}else {
			requestDTO.setFullyCashCollateral("");
		}
		
		if (requestDTO.getCommitteApproval() != null && !requestDTO.getCommitteApproval().trim().isEmpty()) {
			if (!("Y".equalsIgnoreCase(requestDTO.getCommitteApproval().trim())
					|| "N".equalsIgnoreCase(requestDTO.getCommitteApproval().trim()))) {
				errors.add("committeApproval", new ActionMessage("error.committeApproval.invalid"));
			}else {
				requestDTO.setCommitteApproval(requestDTO.getCommitteApproval());
			}
		}else {
			requestDTO.setCommitteApproval("");
		}
		
		if (requestDTO.getBoardApproval() != null && !requestDTO.getBoardApproval().trim().isEmpty()) {
			if (!("Y".equalsIgnoreCase(requestDTO.getBoardApproval().trim())
					|| "N".equalsIgnoreCase(requestDTO.getBoardApproval().trim()))) {
				errors.add("boardApproval", new ActionMessage("error.boardApproval.invalid"));
			}else {
				requestDTO.setBoardApproval(requestDTO.getBoardApproval());
			}
		}else {
			requestDTO.setBoardApproval("");
		}

		if(requestDTO.getRamRatingFinalizationDate()!=null && !requestDTO.getRamRatingFinalizationDate().trim().isEmpty()){
			try {
				ramRatingDate.parse(requestDTO.getRamRatingFinalizationDate().trim());
				requestDTO.setRamRatingFinalizationDate(requestDTO.getRamRatingFinalizationDate().trim());
			} catch (ParseException e) {
				errors.add("ramRatingFinalizationDate", new ActionMessage("error.cam.date.format"));
			}
		}
		
		if (requestDTO.getDocRemarks() != null && !requestDTO.getDocRemarks().trim().isEmpty()) {
			requestDTO.setDocRemarks(requestDTO.getDocRemarks().trim());
		}else {
			requestDTO.setDocRemarks("");
		}
		
		if(requestDTO.getIsSpecialApprGridForCustBelowHDB8()!=null && ! requestDTO.getIsSpecialApprGridForCustBelowHDB8().trim().isEmpty()) {
			if(!("Yes".equalsIgnoreCase(requestDTO.getIsSpecialApprGridForCustBelowHDB8().trim()) || "No".equalsIgnoreCase(requestDTO.getIsSpecialApprGridForCustBelowHDB8().trim()))){
				errors.add("isSpecialApprGridForCustBelowHDB8",new ActionMessage("error.isSpecialApprGridForCustBelowHDB8.invalid"));
			}else {
				requestDTO.setIsSpecialApprGridForCustBelowHDB8(requestDTO.getIsSpecialApprGridForCustBelowHDB8().trim());		
			}
		}
		if(requestDTO.getIsSingleBorrowerPrudCeiling()!=null && !requestDTO.getIsSingleBorrowerPrudCeiling().trim().isEmpty()) {
			if("Yes".equalsIgnoreCase(requestDTO.getIsSingleBorrowerPrudCeiling().trim()) || "No".equalsIgnoreCase(requestDTO.getIsSingleBorrowerPrudCeiling().trim())){
				requestDTO.setIsSingleBorrowerPrudCeiling(requestDTO.getIsSingleBorrowerPrudCeiling());
			}else {
				errors.add("isSingleBorrowerPrudCeiling",new ActionMessage("error.isSingleBorrowerPrudCeiling.invalid"));
			}
			if(requestDTO.getIsSingleBorrowerPrudCeiling().equalsIgnoreCase("No")) {
				if(requestDTO.getRbiApprovalForSingle()!=null && !requestDTO.getRbiApprovalForSingle().trim().isEmpty()) {					
				  try {
					Object obj = masterObj.getMasterData("entryCode", Long.parseLong(requestDTO.getRbiApprovalForSingle().trim()));
					if(obj!=null){
						ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
						if("RBI_APPROVAL".equals(codeEntry.getCategoryCode())){
							requestDTO.setRbiApprovalForSingle((codeEntry).getEntryCode());
						}else{
							errors.add("rbiApprovalForSingle",new ActionMessage("error.rbiApprovalForSingle.invalid"));
						}
					}else{
						errors.add("rbiApprovalForSingle",new ActionMessage("error.rbiApprovalForSingle.invalidy"));
					}
				  }catch(Exception e) {
						errors.add("rbiApprovalForSingle",new ActionMessage("error.rbiApprovalForSingle.invalid"));
				  }
				} 
				if(requestDTO.getDetailsOfRbiApprovalForSingle()!=null && !requestDTO.getDetailsOfRbiApprovalForSingle().trim().isEmpty()){
					requestDTO.setDetailsOfRbiApprovalForSingle(requestDTO.getDetailsOfRbiApprovalForSingle());
				}
			}else if(requestDTO.getIsSingleBorrowerPrudCeiling().trim().equalsIgnoreCase("Yes")){
				requestDTO.setIsSingleBorrowerPrudCeiling("Yes");
			}	
		}else {
			requestDTO.setIsSingleBorrowerPrudCeiling("Yes");
		}		

		
		if(requestDTO.getIsGroupBorrowerPrudCeiling()!=null && !requestDTO.getIsGroupBorrowerPrudCeiling().trim().isEmpty()) {
			if("Yes".equalsIgnoreCase(requestDTO.getIsGroupBorrowerPrudCeiling().trim()) || "No".equalsIgnoreCase(requestDTO.getIsGroupBorrowerPrudCeiling().trim())){
				requestDTO.setIsGroupBorrowerPrudCeiling(requestDTO.getIsGroupBorrowerPrudCeiling());
			}else {
				errors.add("isGroupBorrowerPrudCeiling",new ActionMessage("error.isGroupBorrowerPrudCeiling.invalid"));
			}
			if(requestDTO.getIsGroupBorrowerPrudCeiling().equalsIgnoreCase("No")) {
				if(requestDTO.getRbiApprovalForGroup()!=null && !requestDTO.getRbiApprovalForGroup().trim().isEmpty()) {					
				  try {
					Object obj = masterObj.getMasterData("entryCode", Long.parseLong(requestDTO.getRbiApprovalForGroup().trim()));
					if(obj!=null){
						ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
						if("RBI_APPROVAL".equals(codeEntry.getCategoryCode())){
							requestDTO.setRbiApprovalForGroup((codeEntry).getEntryCode());
						}else{
							errors.add("rbiApprovalForGroup",new ActionMessage("error.rbiApprovalForGroup.invalid"));
						}
					}else{
						errors.add("rbiApprovalForGroup",new ActionMessage("error.rbiApprovalForGroup.invalidy"));
					}
				  }catch(Exception e) {
						errors.add("rbiApprovalForGroup",new ActionMessage("error.rbiApprovalForGroup.invalid"));
				  }
				} 
				if(requestDTO.getDetailsOfRbiApprovalForGroup()!=null && !requestDTO.getDetailsOfRbiApprovalForGroup().trim().isEmpty()){
					requestDTO.setDetailsOfRbiApprovalForGroup(requestDTO.getDetailsOfRbiApprovalForGroup());
				}
			}else if(requestDTO.getIsGroupBorrowerPrudCeiling().trim().equalsIgnoreCase("Yes")){
				requestDTO.setIsGroupBorrowerPrudCeiling("Yes");
			}	
		}else {
			requestDTO.setIsGroupBorrowerPrudCeiling("Yes");
		}	
		
		if(requestDTO.getIsNonCooperativeBorrowers()!=null && !requestDTO.getIsNonCooperativeBorrowers().trim().isEmpty()) {
			if("Yes".equals(requestDTO.getIsNonCooperativeBorrowers().trim()) || "No".equals(requestDTO.getIsNonCooperativeBorrowers().trim())){
				requestDTO.setIsNonCooperativeBorrowers(requestDTO.getIsNonCooperativeBorrowers());
			}else {
				errors.add("isNonCooperativeBorrowers",new ActionMessage("error.isNonCooperativeBorrowers.invalid"));
			}
			if(requestDTO.getIsNonCooperativeBorrowers().equals("Yes")) {
				if((requestDTO.getIsDirectorAsNonCooperativeForOther()!=null && !requestDTO.getIsDirectorAsNonCooperativeForOther().trim().isEmpty())){
					if("Yes".equals(requestDTO.getIsDirectorAsNonCooperativeForOther().trim()) || "No".equals(requestDTO.getIsDirectorAsNonCooperativeForOther().trim())){
						requestDTO.setIsDirectorAsNonCooperativeForOther(requestDTO.getIsDirectorAsNonCooperativeForOther().trim());
					}else {
						errors.add("isDirectorAsNonCooperativeForOther",new ActionMessage("error.isDirectorAsNonCooperativeForOther.invalid"));
					}
				}else {
					requestDTO.setIsDirectorAsNonCooperativeForOther("No");
				} 
				if("Yes".equals(requestDTO.getIsDirectorAsNonCooperativeForOther())) {
					if(requestDTO.getNameOfDirectorsAndCompany()!=null && !requestDTO.getNameOfDirectorsAndCompany().trim().isEmpty()){
						requestDTO.setNameOfDirectorsAndCompany(requestDTO.getNameOfDirectorsAndCompany());
					}else {
						errors.add("nameOfDirectorsAndCompany",new ActionMessage("error.string.mandatory"));
					}
				}
			}else {
				requestDTO.setIsDirectorAsNonCooperativeForOther("No");
				requestDTO.setNameOfDirectorsAndCompany(null);
			}	
		}else {
			requestDTO.setIsNonCooperativeBorrowers("No");
			requestDTO.setIsDirectorAsNonCooperativeForOther("No");
			requestDTO.setNameOfDirectorsAndCompany(null);
		}
		
		requestDTO.setUdfList((List<UdfRestRequestDTO>)udfDetailsRestDTOMapper.getUdfRequestDTOWithActualValues(requestDTO, requestDTO.getEvent(),errors));

		requestDTO.setErrors(errors);
		
		return requestDTO;
	}

}
