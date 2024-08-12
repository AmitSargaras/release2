package com.integrosys.cms.app.ws.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.springframework.stereotype.Service;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.IOtherCovenant;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.bus.OBOtherCovenant;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.manualinput.aa.AADetailForm;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.manualinput.limit.othercovenantsdetails.IOtherCovenantDetailsDAO;

@Service
public class CamDetailsDTOMapper {

	public ILimitProfile  getActualFromDTO( AADetailRequestDTO dto ,ILimitProfile actualLimitProfile) {
		SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yyyy");
		date.setLenient(false);
		SimpleDateFormat ramRatingDate = new SimpleDateFormat("dd/MMM/yyyy");
		ramRatingDate.setLenient(false);
		
		ILimitProfile newLimitProfile = null;
		if(actualLimitProfile!=null){
		 newLimitProfile = actualLimitProfile;
		}
		else{
	     newLimitProfile = new OBLimitProfile();
		}
		IOtherCovenant obOtherCovenant = new OBOtherCovenant();
		ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
		String partyId = "";
		if(dto.getPartyId()!=null && !dto.getPartyId().trim().isEmpty()){
			partyId = dto.getPartyId().trim();
		}
		ICMSCustomer cust = custProxy.getCustomerByCIFSource(partyId,null);
		newLimitProfile.setCustomerID(cust.getCustomerID());
		newLimitProfile.setLEReference(partyId);
		
		if(dto.getCamNumber()!=null && !dto.getCamNumber().trim().isEmpty()){
			newLimitProfile.setBCAReference(dto.getCamNumber().trim());
		}else{
			newLimitProfile.setBCAReference("");
		}
		
		if(dto.getCamType()!=null && !dto.getCamType().trim().isEmpty()){
			newLimitProfile.setCamType(dto.getCamType().trim());
		}else{
			newLimitProfile.setCamType("");
		}
		
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
        IGeneralParamEntry generalParamEntries= generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
		
		newLimitProfile.setCamLoginDate(new Date(generalParamEntries.getParamValue()));

		try {
			if(dto.getCamDate()!=null && !dto.getCamDate().trim().isEmpty()){
				
				newLimitProfile.setApprovalDate(date.parse(dto.getCamDate().trim()));
				
				if(dto.getExpiryDate()!=null && !dto.getExpiryDate().trim().isEmpty()){
					Date d1 = date.parse(dto.getExpiryDate().trim());
					Calendar c = Calendar.getInstance();
					c.setTime(d1);
//					c.add(Calendar.YEAR, 1);
					c.add(Calendar.DATE, 1);
					
//					if(newLimitProfile.getExtendedNextReviewDate()==null){
						newLimitProfile.setExtendedNextReviewDate(c.getTime());
//					}
				}
				
			}else{
				newLimitProfile.setApprovalDate(null);
				newLimitProfile.setExtendedNextReviewDate(null);
			}
			
			/*Below code commented : 29-JAN-2016 : As confirmed by BPRG team on mail, CAM expiry date should not get calculated, it should reflect 
				 as being sent through CAM online interface.*/

//			if(newLimitProfile.getNextAnnualReviewDate()==null){
				/*if(dto.getCamDate()!=null && !dto.getCamDate().trim().isEmpty()){
					Date d = date.parse(dto.getCamDate().trim());
					d.setYear(d.getYear()+1);
					newLimitProfile.setNextAnnualReviewDate(d);
				}*/
				if(dto.getExpiryDate()!=null && !dto.getExpiryDate().trim().isEmpty()){
					newLimitProfile.setNextAnnualReviewDate(date.parse(dto.getExpiryDate().trim()));
				}else{
					newLimitProfile.setNextAnnualReviewDate(null);
				}
/*			}else{
				if(dto.getExpiryDate()!=null && !dto.getExpiryDate().trim().isEmpty()){
					newLimitProfile.setNextAnnualReviewDate(date.parse(dto.getExpiryDate().trim()));
				}else{
					newLimitProfile.setNextAnnualReviewDate(null);
				}
			}*/
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(dto.getTotalSactionedAmount()!=null && !"".equals(dto.getTotalSactionedAmount().trim())){
			newLimitProfile.setTotalSactionedAmount(Double.parseDouble(dto.getTotalSactionedAmount().trim()));
		}
		else{
			newLimitProfile.setTotalSactionedAmount(Double.parseDouble("0"));
		}
		
		if(dto.getRelationshipMgr()!=null && !dto.getRelationshipMgr().trim().isEmpty()){
			newLimitProfile.setRelationshipManager(dto.getRelationshipMgr().trim());
		}else{
			newLimitProfile.setRelationshipManager("");
		}
		
		if(dto.getBranch()!=null && !dto.getBranch().trim().isEmpty()){
			newLimitProfile.setControllingBranch(dto.getBranch().trim());
		}else{
			newLimitProfile.setControllingBranch("");
		}
		
		if(dto.getCommitteApproval()!=null && !dto.getCommitteApproval().trim().isEmpty()){
			newLimitProfile.setCommitteApproval(dto.getCommitteApproval().trim());
		}else{
			newLimitProfile.setCommitteApproval("N");
		}
		
		if(dto.getFullyCashCollateral()!=null && !dto.getFullyCashCollateral().trim().isEmpty()){
			newLimitProfile.setFullyCashCollateral(dto.getFullyCashCollateral().trim());
		}else{
			newLimitProfile.setFullyCashCollateral("");
		}
		
		if(dto.getBoardApproval()!=null && !dto.getBoardApproval().trim().isEmpty()){
			newLimitProfile.setBoardApproval(dto.getBoardApproval().trim());
		}else{
			newLimitProfile.setBoardApproval("N");
		}
		
		if(dto.getCreditApproval1()!=null && !dto.getCreditApproval1().trim().isEmpty()){
			newLimitProfile.setApproverEmployeeName1(dto.getCreditApproval1().trim());
		}else{
			newLimitProfile.setApproverEmployeeName1("");
		}
		
		if(dto.getCreditApproval2()!=null && !dto.getCreditApproval2().trim().isEmpty()){
			newLimitProfile.setApproverEmployeeName2(dto.getCreditApproval2().trim());
		}else{
			newLimitProfile.setApproverEmployeeName2("");
		}
		
		if(dto.getCreditApproval3()!=null && !dto.getCreditApproval3().trim().isEmpty()){
			newLimitProfile.setApproverEmployeeName3(dto.getCreditApproval3().trim());
		}else{
			newLimitProfile.setApproverEmployeeName3("");
		}
		
		if(dto.getCreditApproval4()!=null && !dto.getCreditApproval4().trim().isEmpty()){
			newLimitProfile.setApproverEmployeeName4(dto.getCreditApproval4().trim());
		}else{
			newLimitProfile.setApproverEmployeeName4("");
		}
		
		if(dto.getCreditApproval5()!=null && !dto.getCreditApproval5().trim().isEmpty()){
			newLimitProfile.setApproverEmployeeName5(dto.getCreditApproval5().trim());
		}else{
			newLimitProfile.setApproverEmployeeName5("");
		}
		
		if(dto.getAssetClassification()!=null && !dto.getAssetClassification().trim().isEmpty()){
			newLimitProfile.setAssetClassification(dto.getAssetClassification().trim());
		}else{
			newLimitProfile.setAssetClassification("");
		}
		
		if(dto.getRBIAssetClassification()!=null && !dto.getRBIAssetClassification().trim().isEmpty()){
			newLimitProfile.setRbiAssetClassification(dto.getRBIAssetClassification().trim());
		}else{
			newLimitProfile.setRbiAssetClassification("");
		}
		
		if(dto.getRamRating()!=null && !dto.getRamRating().trim().isEmpty()){
			newLimitProfile.setRamRating(dto.getRamRating().trim());
		}else{
			newLimitProfile.setRamRating("");
		}
		
		if(dto.getRatingType()!=null && !dto.getRatingType().trim().isEmpty()){
			newLimitProfile.setRamRatingType(dto.getRatingType().trim());
		}else{
			newLimitProfile.setRamRatingType("");
		}
		
		if(dto.getRamRatingYear()!=null && !dto.getRamRatingYear().trim().isEmpty()){
			newLimitProfile.setRamRatingYear(dto.getRamRatingYear().trim());
		}else{
			newLimitProfile.setRamRatingYear("");
		}
		
		if(dto.getOtherCovenantDetailsList()!=null && !dto.getOtherCovenantDetailsList().isEmpty()){
			for(OtherCovenantDetailsListRequestDTO otherCovenantDetailsListRequestDTO: dto.getOtherCovenantDetailsList()){
				//NEW CAM
				
				if(otherCovenantDetailsListRequestDTO.getCovenantType()!=null && !otherCovenantDetailsListRequestDTO.getCovenantType().trim().isEmpty()){
					obOtherCovenant.setCovenantType(otherCovenantDetailsListRequestDTO.getCovenantType().trim());
				}else{
					obOtherCovenant.setCovenantType("");
				}
				if(otherCovenantDetailsListRequestDTO.getCovenantCondition()!=null && !otherCovenantDetailsListRequestDTO.getCovenantCondition().trim().isEmpty()){
					obOtherCovenant.setCovenantCondition(otherCovenantDetailsListRequestDTO.getCovenantCondition().trim());
				}else{
					obOtherCovenant.setCovenantCondition("");
				}
				if(otherCovenantDetailsListRequestDTO.getCovenantDescription()!=null && !otherCovenantDetailsListRequestDTO.getCovenantDescription().trim().isEmpty()){
					obOtherCovenant.setCovenantDescription(otherCovenantDetailsListRequestDTO.getCovenantDescription().trim());
				}else{
					obOtherCovenant.setCovenantDescription("");
				}
				if(otherCovenantDetailsListRequestDTO.getCompiled()!=null && !otherCovenantDetailsListRequestDTO.getCompiled().trim().isEmpty()){
					obOtherCovenant.setCompiled(otherCovenantDetailsListRequestDTO.getCompiled().trim());
				}else{
					obOtherCovenant.setCompiled("");
				}
				if(otherCovenantDetailsListRequestDTO.getAdvised()!=null && !otherCovenantDetailsListRequestDTO.getAdvised().trim().isEmpty()){
					obOtherCovenant.setAdvised(otherCovenantDetailsListRequestDTO.getAdvised().trim());
				}else{
					obOtherCovenant.setAdvised("");
				}
				if(otherCovenantDetailsListRequestDTO.getTargetDate()!=null && !otherCovenantDetailsListRequestDTO.getTargetDate().trim().isEmpty()){
					obOtherCovenant.setTargetDate(otherCovenantDetailsListRequestDTO.getTargetDate().trim());
				}else{
					obOtherCovenant.setTargetDate("");
				}
				if(otherCovenantDetailsListRequestDTO.getCovenantCategory()!=null && !otherCovenantDetailsListRequestDTO.getCovenantCategory().trim().isEmpty()){
					obOtherCovenant.setCovenantCategory(otherCovenantDetailsListRequestDTO.getCovenantCategory().trim());
				}else{
					obOtherCovenant.setCovenantCategory("General");
				}
				if(otherCovenantDetailsListRequestDTO.getRemarks()!=null && !otherCovenantDetailsListRequestDTO.getRemarks().trim().isEmpty()){
					obOtherCovenant.setRemarks(otherCovenantDetailsListRequestDTO.getRemarks().trim());
				}else{
					obOtherCovenant.setRemarks("");
				}

				if(otherCovenantDetailsListRequestDTO.getMonitoringResponsibiltyComboBoxList()!=null){
					for(CAMMonitoringResponsibilityRequestDTO cmrDTO : otherCovenantDetailsListRequestDTO.getMonitoringResponsibiltyComboBoxList()) {
						obOtherCovenant.setMonitoringResponsibilityList1(cmrDTO.getMonitoringResponsibiltyValue());
					}
				}	

				if(otherCovenantDetailsListRequestDTO.getFacilityNameComboBoxList()!=null){
					for(CAMFacilityNameRequestDTO cfnDTO : otherCovenantDetailsListRequestDTO.getFacilityNameComboBoxList()) {
						obOtherCovenant.setFacilityNameValue(cfnDTO.getFacilityNameValue());
					}
				}	
				
				newLimitProfile.setObOtherCovenant(obOtherCovenant);
								
			}
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
		if(dto.getIsSpecialApprGridForCustBelowHDB8()!=null && !dto.getIsSpecialApprGridForCustBelowHDB8().trim().isEmpty()){
			newLimitProfile.setIsSpecialApprGridForCustBelowHDB8(dto.getIsSpecialApprGridForCustBelowHDB8().trim());
		}else{
			newLimitProfile.setIsSpecialApprGridForCustBelowHDB8("No");
		}

		newLimitProfile.setIsSingleBorrowerPrudCeiling(dto.getIsSingleBorrowerPrudCeiling());
		if(dto.getIsSingleBorrowerPrudCeiling()!=null && "No".equals(dto.getIsSingleBorrowerPrudCeiling())) {
			newLimitProfile.setRbiApprovalForSingle(dto.getRbiApprovalForSingle());
			newLimitProfile.setDetailsOfRbiApprovalForSingle(dto.getDetailsOfRbiApprovalForSingle());
		}else {
			newLimitProfile.setRbiApprovalForSingle("");
			newLimitProfile.setDetailsOfRbiApprovalForSingle("");
		}
		
		newLimitProfile.setIsGroupBorrowerPrudCeiling(dto.getIsGroupBorrowerPrudCeiling());
		if(dto.getIsGroupBorrowerPrudCeiling()!=null && "No".equals(dto.getIsGroupBorrowerPrudCeiling())) {
			newLimitProfile.setRbiApprovalForGroup(dto.getRbiApprovalForGroup());
			newLimitProfile.setDetailsOfRbiApprovalForGroup(dto.getDetailsOfRbiApprovalForGroup());
		}else {
			newLimitProfile.setRbiApprovalForGroup("");
			newLimitProfile.setDetailsOfRbiApprovalForGroup("");
		}
				
		return newLimitProfile;
	}

	public AADetailForm  getFormFromDTO( AADetailRequestDTO dto ) {

		SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yyyy");
		date.setLenient(false);
		SimpleDateFormat ramRatingDate = new SimpleDateFormat("dd/MMM/yyyy");
		ramRatingDate.setLenient(false);
		
		AADetailForm form = new AADetailForm();
		if(dto.getCamNumber()!=null && !dto.getCamNumber().trim().isEmpty()){
			form.setAaNum(dto.getCamNumber().trim());
		}else{
			form.setAaNum("");
		}
		
		if(dto.getCamType()!=null && !dto.getCamType().trim().isEmpty()){
			form.setCamType(dto.getCamType().trim());
		}else{
			form.setCamType("");
		}
		
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
        IGeneralParamEntry generalParamEntries= generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
		form.setCamLoginDate(date.format(new Date(generalParamEntries.getParamValue())));
		
		if(dto.getCamDate()!=null && !dto.getCamDate().trim().isEmpty()){
			form.setAaApprovalDate(dto.getCamDate().trim());
		}else{
			form.setAaApprovalDate("");
		}
		
		if(dto.getExpiryDate()!=null && !dto.getExpiryDate().trim().isEmpty()){
			form.setAnnualReviewDate(dto.getExpiryDate().trim());
		}else{
			form.setAnnualReviewDate("");
		}
		
		if(dto.getTotalSactionedAmount()!=null && !dto.getTotalSactionedAmount().trim().isEmpty()){
			form.setTotalSactionedAmount(dto.getTotalSactionedAmount().trim());
		}else{
			form.setTotalSactionedAmount("0");
		}
		
		form.setTotalSanctionedAmountFacLevel("0");
		if(dto.getRelationshipMgr()!=null && !dto.getRelationshipMgr().trim().isEmpty()){
			form.setRelationshipManager(dto.getRelationshipMgr().trim());
		}else{
			form.setRelationshipManager("");
		}
		
		if(dto.getBranch()!=null && !dto.getBranch().trim().isEmpty()){
			form.setControllingBranch(dto.getBranch().trim());
		}else{
			form.setControllingBranch("");
		}
		
		if(dto.getCommitteApproval()!=null && !dto.getCommitteApproval().trim().isEmpty()){
			form.setCommitteApproval(dto.getCommitteApproval().trim());
		}else{
			form.setCommitteApproval("");
		}
		
		if(dto.getFullyCashCollateral()!=null && !dto.getFullyCashCollateral().trim().isEmpty()){
			form.setFullyCashCollateral(dto.getFullyCashCollateral().trim());
		}else{
			form.setFullyCashCollateral("");
		}
		
		if(dto.getBoardApproval()!=null && !dto.getBoardApproval().trim().isEmpty()){
			form.setBoardApproval(dto.getBoardApproval().trim());
		}else{
			form.setBoardApproval("");
		}
		
		if(dto.getCreditApproval1()!=null && !dto.getCreditApproval1().trim().isEmpty()){
			form.setCreditApproval1(dto.getCreditApproval1().trim());
		}else{
			form.setCreditApproval1("");
		}
		
		if(dto.getCreditApproval2()!=null && !dto.getCreditApproval2().trim().isEmpty()){
			form.setCreditApproval2(dto.getCreditApproval2().trim());
		}else{
			form.setCreditApproval2("");
		}
		
		if(dto.getCreditApproval3()!=null && !dto.getCreditApproval3().trim().isEmpty()){
			form.setCreditApproval3(dto.getCreditApproval3().trim());
		}else{
			form.setCreditApproval3("");
		}
		
		if(dto.getCreditApproval4()!=null && !dto.getCreditApproval4().trim().isEmpty()){
			form.setCreditApproval4(dto.getCreditApproval4().trim());
		}else{
			form.setCreditApproval4("");
		}
		
		if(dto.getCreditApproval5()!=null && !dto.getCreditApproval5().trim().isEmpty()){
			form.setCreditApproval5(dto.getCreditApproval5().trim());
		}else{
			form.setCreditApproval5("");
		}
		
		if(dto.getAssetClassification()!=null && !dto.getAssetClassification().trim().isEmpty()){
			form.setAssetClassification(dto.getAssetClassification().trim());
		}else{
			form.setAssetClassification("");
		}
		
		if(dto.getRBIAssetClassification()!=null && !dto.getRBIAssetClassification().trim().isEmpty()){
			form.setRbiAssetClassification(dto.getRBIAssetClassification().trim());
		}else{
			form.setRbiAssetClassification("");
		}
		
		if(dto.getRamRating()!=null && !dto.getRamRating().trim().isEmpty()){
			form.setRamRating(dto.getRamRating().trim());
		}else{
			form.setRamRating("");
		}
		
		if(dto.getRatingType()!=null && !dto.getRatingType().trim().isEmpty()){
			form.setRamRatingType(dto.getRatingType().trim());
		}else{
			form.setRamRatingType("");
		}
		
		if(dto.getRamRatingYear()!=null && !dto.getRamRatingYear().trim().isEmpty()){
			form.setRamRatingYear(dto.getRamRatingYear().trim());
		}else{
			form.setRamRatingYear("");
		}
		
		if(dto.getEvent()!=null && !dto.getEvent().trim().isEmpty()){
			form.setEvent(dto.getEvent().trim());
		}else{
			form.setEvent("");
		}
		
		if(dto.getRamRatingFinalizationDate()!=null && !dto.getRamRatingFinalizationDate().trim().isEmpty()){			
			form.setRamRatingFinalizationDate(dto.getRamRatingFinalizationDate().trim());
		}else{
			form.setRamRatingFinalizationDate(null);
		}
		
		if(dto.getIsSpecialApprGridForCustBelowHDB8()!=null && !dto.getIsSpecialApprGridForCustBelowHDB8().trim().isEmpty()){
			form.setIsSpecialApprGridForCustBelowHDB8(dto.getIsSpecialApprGridForCustBelowHDB8().trim());
		}else{
			form.setIsSpecialApprGridForCustBelowHDB8("No");
		}
		
		if(dto.getIsSingleBorrowerPrudCeiling()!=null && ! dto.getIsSingleBorrowerPrudCeiling().trim().isEmpty()) {
			form.setIsSingleBorrowerPrudCeiling(dto.getIsSingleBorrowerPrudCeiling());
			if("No".equals(dto.getIsSingleBorrowerPrudCeiling())) {
				form.setRbiApprovalForSingle(dto.getRbiApprovalForSingle());
				form.setDetailsOfRbiApprovalForSingle(dto.getDetailsOfRbiApprovalForSingle());
			}else {
				form.setRbiApprovalForSingle("");
				form.setDetailsOfRbiApprovalForSingle("");
			}
		}
		
		if(dto.getIsGroupBorrowerPrudCeiling()!=null && ! dto.getIsGroupBorrowerPrudCeiling().trim().isEmpty()) {
			form.setIsGroupBorrowerPrudCeiling(dto.getIsGroupBorrowerPrudCeiling());
			if("No".equals(dto.getIsGroupBorrowerPrudCeiling())) {
				form.setRbiApprovalForGroup(dto.getRbiApprovalForGroup());
				form.setDetailsOfRbiApprovalForGroup(dto.getDetailsOfRbiApprovalForGroup());
			}else {
				form.setRbiApprovalForGroup("");
				form.setDetailsOfRbiApprovalForGroup("");
			}
		}
		
		//New CAM ONLINE CR START
		if(dto.getOtherCovenantDetailsList()!=null && !dto.getOtherCovenantDetailsList().isEmpty()){
			for(OtherCovenantDetailsListRequestDTO otherCovenantDetailsListRequestDTO: dto.getOtherCovenantDetailsList()){
				if(otherCovenantDetailsListRequestDTO.getCovenantType()!=null && !otherCovenantDetailsListRequestDTO.getCovenantType().trim().isEmpty()){
					form.setCovenantType(otherCovenantDetailsListRequestDTO.getCovenantType().trim());
				}else{
					form.setCovenantType("");
				}
				
				if(otherCovenantDetailsListRequestDTO.getCovenantCondition()!=null && !otherCovenantDetailsListRequestDTO.getCovenantCondition().trim().isEmpty()){
					form.setCovenantCondition(otherCovenantDetailsListRequestDTO.getCovenantCondition().trim());
				}else{
					form.setCovenantCondition("");
				}
				
				if(otherCovenantDetailsListRequestDTO.getCompiled()!=null && !otherCovenantDetailsListRequestDTO.getCompiled().trim().isEmpty()){
					form.setCompiled(otherCovenantDetailsListRequestDTO.getCompiled().trim());
				}else{
					form.setCompiled("");
				}
						
				if(otherCovenantDetailsListRequestDTO.getAdvised()!=null && !otherCovenantDetailsListRequestDTO.getAdvised().trim().isEmpty()){
					form.setAdvised(otherCovenantDetailsListRequestDTO.getAdvised().trim());
				}else{
					form.setAdvised("");
				}
						
				if(otherCovenantDetailsListRequestDTO.getTargetDate()!=null && !otherCovenantDetailsListRequestDTO.getTargetDate().trim().isEmpty()){
						form.setTargetDate(otherCovenantDetailsListRequestDTO.getTargetDate().trim());
				}else{
					form.setTargetDate(null);
				}
				
				if(otherCovenantDetailsListRequestDTO.getCovenantCategory()!=null && !otherCovenantDetailsListRequestDTO.getCovenantCategory().trim().isEmpty()){
					form.setCovenantCategory(otherCovenantDetailsListRequestDTO.getCovenantCategory().trim());
				}else{
					form.setCovenantCategory("General");
				}
				
				if(otherCovenantDetailsListRequestDTO.getCovenantDescription()!=null && !otherCovenantDetailsListRequestDTO.getCovenantDescription().trim().isEmpty()){
					form.setCovenantDescription(otherCovenantDetailsListRequestDTO.getCovenantDescription().trim());
				}else{
					form.setCovenantDescription("");
				}
					

				if(otherCovenantDetailsListRequestDTO.getRemarks()!=null && !otherCovenantDetailsListRequestDTO.getRemarks().trim().isEmpty()){
					form.setCovenantremarks(otherCovenantDetailsListRequestDTO.getRemarks().trim());
				}else{
					form.setCovenantremarks("");
				}
				
				if(otherCovenantDetailsListRequestDTO.getMonitoringResponsibiltyComboBoxList()!=null){
					for(CAMMonitoringResponsibilityRequestDTO cmrDTO : otherCovenantDetailsListRequestDTO.getMonitoringResponsibiltyComboBoxList()) {
						form.setMonitoringResponsibilityList1(cmrDTO.getMonitoringResponsibiltyValue());
						form.setMonitoringResponsibilty(cmrDTO.getMonitoringResponsibiltyValue());
						form.setMonitoringResponsibilityProp(cmrDTO.getMonitoringResponsibiltyValue());
					}
				}else {
					form.setMonitoringResponsibilty("");
					form.setMonitoringResponsibilityProp("");
					form.setMonitoringResponsibilityList1("");
				}

				if(otherCovenantDetailsListRequestDTO.getFacilityNameComboBoxList()!=null){
					for(CAMFacilityNameRequestDTO cfnDTO : otherCovenantDetailsListRequestDTO.getFacilityNameComboBoxList()) {
						form.setFacilityNamesList(cfnDTO.getFacilityNameValue());
						form.setFinalFaciltyName(cfnDTO.getFacilityNameValue());
						form.setFaciltyNameList1(cfnDTO.getFacilityNameValue());
					}
				}else{
					form.setFacilityNamesList("");
					form.setFinalFaciltyName("");
					form.setFaciltyNameList1("");
				}	
								
			  //New CAM ONLINE CR END				
			}
		}	

		return form;
	}

	public AADetailRequestDTO getRequestDTOWithActualValues(AADetailRequestDTO requestDTO, String camId) {

		SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yyyy");
		date.setLenient(false);
		SimpleDateFormat ramRatingDate = new SimpleDateFormat("dd/MMM/yyyy");
		ramRatingDate.setLenient(false);
		
		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();
		/*partyId
		camType
		camNumber *validation need to remove from actual validator.
		camDate
		ramRating
		ramRatingYear
		ratingType
		expiryDate
		totalSactionedAmount
		relationshipMgr
		branch
		fullyCashCollateral
		committeApproval
		boardApproval
		creditApproval1
		creditApproval2
		creditApproval3
		creditApproval4
		creditApproval5
		RBIAssetClassification
		assetClassification*/

		if(requestDTO.getEvent()!=null && !requestDTO.getEvent().trim().isEmpty() 
				&& requestDTO.getEvent().equals("WS_UPDATE_CAM")){
			if(camId==null){
				errors.add("partyId",new ActionMessage("error.cam.notexist"));
			}	
		}
		else if(requestDTO.getEvent()!=null && !requestDTO.getEvent().trim().isEmpty() 
				&& requestDTO.getEvent().equals("WS_CREATE_CAM")){
			if(camId!=null){
				errors.add("partyId",new ActionMessage("error.cam.alreadyexist"));
			}
			if(requestDTO.getCamType()!=null && !requestDTO.getCamType().trim().equals("Initial")){
				errors.add("camType",new ActionMessage("error.camType.invalid"));
			}

		}

		if(requestDTO.getPartyId()!=null && !requestDTO.getPartyId().trim().isEmpty()){

			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			String partyId = requestDTO.getPartyId().trim();
			try{
				ICMSCustomer cust = custProxy.getCustomerByCIFSource(partyId,null);
				if(cust!= null){
					requestDTO.setPartyId(partyId);
					
					requestDTO.setTotalSactionedAmount(cust.getTotalSanctionedLimit());
					requestDTO.setRelationshipMgr(cust.getRelationshipMgr());
					requestDTO.setBranch(cust.getBranchCode());
				}
				else{
					errors.add("partyId",new ActionMessage("error.partyId.invalid"));
				}
			}catch(Exception e){
				errors.add("partyId",new ActionMessage("error.partyId.invalid"));
			}
		}
		else{
			errors.add("partyId",new ActionMessage("error.partyId.empty"));
		}
		
		if(requestDTO.getCamType()!=null && !requestDTO.getCamType().trim().isEmpty()){
			String camType = requestDTO.getCamType().trim();
			if(camType.equals("Annual") ||camType.equals("Interim")||camType.equals("Initial")){
				requestDTO.setCamType(camType);
			}
			else{
				errors.add("camType",new ActionMessage("error.camType.invalid"));
			}
		}
		if(requestDTO.getCamNumber()!=null && !requestDTO.getCamNumber().trim().isEmpty()){
			requestDTO.setCamNumber(requestDTO.getCamNumber().trim());
		}

		if(requestDTO.getCamDate()!=null && !requestDTO.getCamDate().trim().isEmpty()){
			try {
				date.parse(requestDTO.getCamDate().trim());
				requestDTO.setCamDate(requestDTO.getCamDate().trim());
			} catch (ParseException e) {
				errors.add("camDate",new ActionMessage("error.camDate.invalid.format"));
			}
		}

		if(requestDTO.getExpiryDate()!=null && !requestDTO.getExpiryDate().trim().isEmpty()){
			try {
				date.parse(requestDTO.getExpiryDate().trim());
				requestDTO.setExpiryDate(requestDTO.getExpiryDate().trim());
			} catch (ParseException e) {
				errors.add("expiryDate",new ActionMessage("error.expiryDate.invalid.format"));
			}
		}

		if(requestDTO.getRamRating()!=null && !requestDTO.getRamRating().trim().isEmpty()){
			requestDTO.setRamRating(requestDTO.getRamRating().trim());
		}
		
		
		Calendar now = Calendar.getInstance();   // Gets the current date and time
		int year = now.get(Calendar.YEAR);    // Gets the current date and time.
		if (requestDTO.getRamRatingYear() != null
				&& !"".equals(requestDTO.getRamRatingYear().trim())) {
			if (requestDTO.getRamRatingYear().trim().equals(String.valueOf(year))
					|| requestDTO.getRamRatingYear().trim().equals(String.valueOf(year - 1))
					|| requestDTO.getRamRatingYear().trim().equals(String.valueOf(year - 2))) {
				requestDTO.setRamRatingYear(requestDTO.getRamRatingYear().trim());
			} else {
				errors.add("ramRatingYear", new ActionMessage(
						"error.ramRatingYear.invalid.format"));
			}
		}
		
		if(requestDTO.getRatingType()!=null && !requestDTO.getRatingType().trim().isEmpty()){
			//Category Code == SEC_RATING_TYPE
			Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getRatingType().trim(),"ratingType",errors,"SEC_RATING_TYPE");
			if(!(obj instanceof ActionErrors)){
				requestDTO.setRatingType(((ICommonCodeEntry)obj).getEntryCode());
			}
		}

//		requestDTO.setExpiryDate(requestDTO.getExpiryDate());
		/*if(requestDTO.getTotalSactionedAmount()!=null && !requestDTO.getTotalSactionedAmount().trim().isEmpty()){
			requestDTO.setTotalSactionedAmount(requestDTO.getTotalSactionedAmount());
		}*/
		/*if(requestDTO.getRelationshipMgr()!=null && !requestDTO.getRelationshipMgr().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("actualRelationshipMgr", requestDTO.getRelationshipMgr(),"relationshipMgr",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setRelationshipMgr(String.valueOf(((IRelationshipMgr)obj).getId()));
			}
		}*/
		
		if(requestDTO.getFullyCashCollateral()!=null && !requestDTO.getFullyCashCollateral().trim().isEmpty()){
			requestDTO.setFullyCashCollateral(requestDTO.getFullyCashCollateral());
		}
		if(requestDTO.getCommitteApproval()!=null && !requestDTO.getCommitteApproval().trim().isEmpty()){
			requestDTO.setCommitteApproval(requestDTO.getCommitteApproval());
		}
		if(requestDTO.getBoardApproval()!=null && !requestDTO.getBoardApproval().trim().isEmpty()){
			requestDTO.setBoardApproval(requestDTO.getBoardApproval());
		}

		if(requestDTO.getCreditApproval1()!=null && !requestDTO.getCreditApproval1().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("actualCreditApproval", requestDTO.getCreditApproval1().trim(),"creditApproval1",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setCreditApproval1(((ICreditApproval)obj).getApprovalCode());
			}
		}

		if(requestDTO.getCreditApproval2()!=null && !requestDTO.getCreditApproval2().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("actualCreditApproval", requestDTO.getCreditApproval2().trim(),"creditApproval2",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setCreditApproval2(((ICreditApproval)obj).getApprovalCode());
			}
		}
		if(requestDTO.getCreditApproval3()!=null && !requestDTO.getCreditApproval3().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("actualCreditApproval", requestDTO.getCreditApproval3().trim(),"creditApproval3",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setCreditApproval3(((ICreditApproval)obj).getApprovalCode());
			}
		}
		if(requestDTO.getCreditApproval4()!=null && !requestDTO.getCreditApproval4().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("actualCreditApproval", requestDTO.getCreditApproval4().trim(),"creditApproval4",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setCreditApproval4(((ICreditApproval)obj).getApprovalCode());
			}
		}
		if(requestDTO.getCreditApproval5()!=null && !requestDTO.getCreditApproval5().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("actualCreditApproval", requestDTO.getCreditApproval5().trim(),"creditApproval5",errors);
			if(!(obj instanceof ActionErrors)){
				requestDTO.setCreditApproval5(((ICreditApproval)obj).getApprovalCode());
			}
		}

		if(requestDTO.getRBIAssetClassification()!=null && !requestDTO.getRBIAssetClassification().trim().isEmpty()){
			//Category Code == RBI_ASSET_ClASSIFICATION
			Object obj = masterObj.getObjectByEntityNameAndCPSId("entryCode", requestDTO.getRBIAssetClassification().trim(),"RBIAssetClassification",errors,"RBI_ASSET_ClASSIFICATION");
			if(!(obj instanceof ActionErrors)){
				requestDTO.setRBIAssetClassification(((ICommonCodeEntry)obj).getEntryCode());
			}
		}

		if(requestDTO.getAssetClassification()!=null && !requestDTO.getAssetClassification().trim().isEmpty()){
			try{
			Object obj = masterObj.getMaster("entryCode", Long.parseLong(requestDTO.getAssetClassification().trim()));
			if(obj!=null){
				ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
				
				if("ASSET_ClASSIFICATION".equals(codeEntry.getCategoryCode())){
					requestDTO.setAssetClassification((codeEntry).getEntryCode());
				}else{
					errors.add("assetClassification",new ActionMessage("error.assetClassification.invalid"));
				}
				
			}
			else{
				errors.add("assetClassification",new ActionMessage("error.assetClassification.invalid"));
			}
			}
			catch(Exception e){
				errors.add("assetClassification",new ActionMessage("error.assetClassification.invalid"));
			}
		}

		if(requestDTO.getFullyCashCollateral()!=null && !requestDTO.getFullyCashCollateral().trim().isEmpty()){
			if(!("Y".equals(requestDTO.getFullyCashCollateral().trim()) || "N".equals(requestDTO.getFullyCashCollateral().trim()))){
				errors.add("fullyCashCollateral",new ActionMessage("error.fullyCashCollateral.invalid"));
			}
		}
		if(requestDTO.getCommitteApproval()!=null && !requestDTO.getCommitteApproval().trim().isEmpty()){
			if(!("Y".equals(requestDTO.getCommitteApproval().trim()) || "N".equals(requestDTO.getCommitteApproval().trim()))){
				errors.add("committeApproval",new ActionMessage("error.committeApproval.invalid"));
			}
		}
		if(requestDTO.getBoardApproval()!=null && !requestDTO.getBoardApproval().trim().isEmpty()){
			if(!("Y".equals(requestDTO.getBoardApproval().trim()) || "N".equals(requestDTO.getBoardApproval().trim()))){
				errors.add("boardApproval",new ActionMessage("error.boardApproval.invalid"));
			}
		}
		
		if(requestDTO.getRamRatingFinalizationDate()!=null && !requestDTO.getRamRatingFinalizationDate().trim().isEmpty()){
				try {
					ramRatingDate.parse(requestDTO.getRamRatingFinalizationDate().trim());
					requestDTO.setRamRatingFinalizationDate(requestDTO.getRamRatingFinalizationDate().trim());
				} catch (ParseException e) {
					errors.add("ramRatingFinalizationDate", new ActionMessage("error.ramRatingFinalizationDate.invalid.format"));
				}
		}
		if(requestDTO.getIsSpecialApprGridForCustBelowHDB8()!=null && ! requestDTO.getIsSpecialApprGridForCustBelowHDB8().trim().isEmpty()) {
			if(!("Yes".equals(requestDTO.getIsSpecialApprGridForCustBelowHDB8().trim()) || "No".equals(requestDTO.getIsSpecialApprGridForCustBelowHDB8().trim()))){
				errors.add("isSpecialApprGridForCustBelowHDB8",new ActionMessage("error.isSpecialApprGridForCustBelowHDB8.invalid"));
			}else {
				requestDTO.setIsSpecialApprGridForCustBelowHDB8(requestDTO.getIsSpecialApprGridForCustBelowHDB8().trim());		
			}
		}

		if(requestDTO.getIsSingleBorrowerPrudCeiling()!=null && !requestDTO.getIsSingleBorrowerPrudCeiling().trim().isEmpty()) {
			if("Yes".equals(requestDTO.getIsSingleBorrowerPrudCeiling().trim()) || "No".equals(requestDTO.getIsSingleBorrowerPrudCeiling().trim())){
				requestDTO.setIsSingleBorrowerPrudCeiling(requestDTO.getIsSingleBorrowerPrudCeiling());
			}else {
				errors.add("isSingleBorrowerPrudCeiling",new ActionMessage("error.isSingleBorrowerPrudCeiling.invalid"));
			}
			if(requestDTO.getIsSingleBorrowerPrudCeiling().equals("No")) {
				if(requestDTO.getRbiApprovalForSingle()!=null && !requestDTO.getRbiApprovalForSingle().trim().isEmpty()) {					
					try {
						if (ASSTValidator.isNumeric(requestDTO.getRbiApprovalForSingle().trim())){
							Object obj = masterObj.getMaster("entryCode", Long.parseLong(requestDTO.getRbiApprovalForSingle().trim()));
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
						} else {
							errors.add("rbiApprovalForSingle", new ActionMessage("error.numbers.format.only"));
						}
					}catch(Exception e) {
						errors.add("rbiApprovalForSingle",new ActionMessage("error.rbiApprovalForSingle.invalid"));
					}
				}else {
					errors.add("rbiApprovalForSingle",new ActionMessage("error.string.mandatory"));
				}
				if(requestDTO.getDetailsOfRbiApprovalForSingle()!=null && !requestDTO.getDetailsOfRbiApprovalForSingle().trim().isEmpty()){
					if(requestDTO.getDetailsOfRbiApprovalForSingle().length() > 150 )
						errors.add("detailsOfRbiApprovalForSingle",new ActionMessage("length is exceeded"));
					else																						 
						requestDTO.setDetailsOfRbiApprovalForSingle(requestDTO.getDetailsOfRbiApprovalForSingle());
				}else {
					errors.add("detailsOfRbiApprovalForSingle",new ActionMessage("error.string.mandatory"));
				}
			}else if(requestDTO.getIsSingleBorrowerPrudCeiling().trim().equals("Yes")){
				requestDTO.setIsSingleBorrowerPrudCeiling("Yes");
			}	
		}else {
			requestDTO.setIsSingleBorrowerPrudCeiling("Yes");
		}		

		
		if(requestDTO.getIsGroupBorrowerPrudCeiling()!=null && !requestDTO.getIsGroupBorrowerPrudCeiling().trim().isEmpty()) {
			if("Yes".equals(requestDTO.getIsGroupBorrowerPrudCeiling().trim()) || "No".equals(requestDTO.getIsGroupBorrowerPrudCeiling().trim())){
				requestDTO.setIsGroupBorrowerPrudCeiling(requestDTO.getIsGroupBorrowerPrudCeiling());
			}else {
				errors.add("isGroupBorrowerPrudCeiling",new ActionMessage("error.isGroupBorrowerPrudCeiling.invalid"));
			}
			if(requestDTO.getIsGroupBorrowerPrudCeiling().equals("No")) {
				if(requestDTO.getRbiApprovalForGroup()!=null && !requestDTO.getRbiApprovalForGroup().trim().isEmpty()) {					
					try {
						if (ASSTValidator.isNumeric(requestDTO.getRbiApprovalForGroup().trim())){
							Object obj = masterObj.getMaster("entryCode", Long.parseLong(requestDTO.getRbiApprovalForGroup().trim()));
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
						} else {
							errors.add("rbiApprovalForGroup", new ActionMessage("error.numbers.format.only"));
						}
					}catch(Exception e) {
						errors.add("rbiApprovalForGroup",new ActionMessage("error.rbiApprovalForGroup.invalid"));
					}
				}else {
					errors.add("rbiApprovalForGroup",new ActionMessage("error.string.mandatory"));
				}
				if(requestDTO.getDetailsOfRbiApprovalForGroup()!=null && !requestDTO.getDetailsOfRbiApprovalForGroup().trim().isEmpty()){
					if(requestDTO.getDetailsOfRbiApprovalForGroup().length() > 150 )
						errors.add("detailsOfRbiApprovalForGroup",new ActionMessage("length is exceeded"));
					else																						 
						requestDTO.setDetailsOfRbiApprovalForGroup(requestDTO.getDetailsOfRbiApprovalForGroup());
				}else {
					errors.add("detailsOfRbiApprovalForGroup",new ActionMessage("error.string.mandatory"));
				}
			}else if(requestDTO.getIsGroupBorrowerPrudCeiling().trim().equals("Yes")){
				requestDTO.setIsGroupBorrowerPrudCeiling("Yes");
			}	
		}else {
			requestDTO.setIsGroupBorrowerPrudCeiling("Yes");
		}	

		
		List<OtherCovenantDetailsListRequestDTO> otherCamCovList = new LinkedList<OtherCovenantDetailsListRequestDTO>();
		
		if(requestDTO.getOtherCovenantDetailsList()!=null && !requestDTO.getOtherCovenantDetailsList().isEmpty()){
			
			for(OtherCovenantDetailsListRequestDTO otherCovenantDetailsListRequestDTO: requestDTO.getOtherCovenantDetailsList()){
				//New CAM ONLINE CR START
				
				if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("OFF") && ("WS_CREATE_CAM".equals(requestDTO.getEvent()) || "WS_UPDATE_CAM".equals(requestDTO.getEvent()))) {
					// bypass mandatory validation
				}else {
					if(otherCovenantDetailsListRequestDTO.getTargetDate() == null || otherCovenantDetailsListRequestDTO.getTargetDate().trim().isEmpty()){
						errors.add("targetDateError",new ActionMessage("error.string.mandatory"));
					}
				}
				
				if (otherCovenantDetailsListRequestDTO.getTargetDate() != null
						&& !otherCovenantDetailsListRequestDTO.getTargetDate().trim().isEmpty()) {
					try {
						if (isValidDateHyphen(otherCovenantDetailsListRequestDTO.getTargetDate().trim())) {
							date.parse(otherCovenantDetailsListRequestDTO.getTargetDate().trim());
							otherCovenantDetailsListRequestDTO.setTargetDate(ramRatingDate.format(date.parse(otherCovenantDetailsListRequestDTO.getTargetDate().trim())));
						} else {
							errors.add("targetDateError", new ActionMessage("error.targetDate.invalid.format"));

						}
					} catch (ParseException e) {
						DefaultLogger.debug(this, "Expection at getTargetDate>>" + e.getMessage());
						e.printStackTrace();
					}
				}
				
				if(otherCovenantDetailsListRequestDTO.getCovenantCategory()!=null && !otherCovenantDetailsListRequestDTO.getCovenantCategory().trim().isEmpty()){
					if("General".equals(otherCovenantDetailsListRequestDTO.getCovenantCategory().trim()) || "Facility".equals(otherCovenantDetailsListRequestDTO.getCovenantCategory().trim())){
						otherCovenantDetailsListRequestDTO.setCovenantCategory(otherCovenantDetailsListRequestDTO.getCovenantCategory());
					}else {
						errors.add("covenantCategory",new ActionMessage("error.covenantCategory.invalid"));
					}
				}else {
					otherCovenantDetailsListRequestDTO.setCovenantCategory("General");
				}
				
				if(otherCovenantDetailsListRequestDTO.getCovenantType()!=null && !otherCovenantDetailsListRequestDTO.getCovenantType().trim().isEmpty()){
				  try {
					Object obj = masterObj.getMaster("entryCode", Long.parseLong(otherCovenantDetailsListRequestDTO.getCovenantType().trim()));
					if(obj!=null){
						ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
						if("OTHER_COVENANT_TYPE".equals(codeEntry.getCategoryCode())){
							otherCovenantDetailsListRequestDTO.setCovenantType((codeEntry).getEntryCode());
						}else{
							errors.add("covenantType",new ActionMessage("error.covenantType.invalid"));
						}
					}else{
						errors.add("covenantType",new ActionMessage("error.covenantType.invalid"));
					}
				  }catch(Exception e) {
						errors.add("covenantType",new ActionMessage("error.covenantType.invalid"));
				  }
				}else{
					otherCovenantDetailsListRequestDTO.setCovenantType("");
				}
				
				if(otherCovenantDetailsListRequestDTO.getCovenantCondition()!=null && !otherCovenantDetailsListRequestDTO.getCovenantCondition().trim().isEmpty()){
					  try {
						Object obj = masterObj.getMaster("entryCode", Long.parseLong(otherCovenantDetailsListRequestDTO.getCovenantCondition().trim()));
						if(obj!=null){
							ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
							if("OTHER_COVENANT_CONDITIONS".equals(codeEntry.getCategoryCode())){
								otherCovenantDetailsListRequestDTO.setCovenantCondition((codeEntry).getEntryCode());
							}else{
								errors.add("covenantCondition",new ActionMessage("error.covenantCondition.invalid"));
							}
						}else{
							errors.add("covenantCondition",new ActionMessage("error.covenantCondition.invalid"));
						}
					  }catch(Exception e) {
							errors.add("covenantCondition",new ActionMessage("error.covenantCondition.invalid"));
					  }
				}else{
					otherCovenantDetailsListRequestDTO.setCovenantCondition("");
				}

				if(otherCovenantDetailsListRequestDTO.getCovenantDescription()!=null && !otherCovenantDetailsListRequestDTO.getCovenantDescription().trim().isEmpty()){
					if(otherCovenantDetailsListRequestDTO.getCovenantDescription().length() > 200 )
						errors.add("covenantDescription",new ActionMessage("length is exceeded"));
					else
						otherCovenantDetailsListRequestDTO.setCovenantDescription(otherCovenantDetailsListRequestDTO.getCovenantDescription().trim());
				}else{
					otherCovenantDetailsListRequestDTO.setCovenantDescription("");
				}
				
				if(otherCovenantDetailsListRequestDTO.getCompiled()!=null && ! otherCovenantDetailsListRequestDTO.getCompiled().trim().isEmpty()) {
					if(!("Compiled".equals(otherCovenantDetailsListRequestDTO.getCompiled().trim()) || "Non-Compiled".equals(otherCovenantDetailsListRequestDTO.getCompiled().trim()))){
						errors.add("compiled",new ActionMessage("error.compiled.invalid"));
					}else {
						otherCovenantDetailsListRequestDTO.setCompiled(otherCovenantDetailsListRequestDTO.getCompiled().trim());		
					}
				}		
				
				if(otherCovenantDetailsListRequestDTO.getAdvised()!=null && ! otherCovenantDetailsListRequestDTO.getAdvised().trim().isEmpty()) {
					if(!("Advised".equals(otherCovenantDetailsListRequestDTO.getAdvised().trim()) || "Non-Advised".equals(otherCovenantDetailsListRequestDTO.getAdvised().trim()))){
						errors.add("advised",new ActionMessage("error.advised.invalid"));
					}else {
						otherCovenantDetailsListRequestDTO.setAdvised(otherCovenantDetailsListRequestDTO.getAdvised().trim());		
					}
				}
				
				if(otherCovenantDetailsListRequestDTO.getRemarks()!=null && !otherCovenantDetailsListRequestDTO.getRemarks().trim().isEmpty()){
					if(otherCovenantDetailsListRequestDTO.getRemarks().length() > 200 )
						errors.add("remarks",new ActionMessage("length is exceeded"));
					else
						otherCovenantDetailsListRequestDTO.setRemarks(otherCovenantDetailsListRequestDTO.getRemarks().trim());
				}else{
					otherCovenantDetailsListRequestDTO.setRemarks("");
				}
				
				if(otherCovenantDetailsListRequestDTO.getMonitoringResponsibiltyComboBoxList() != null && !otherCovenantDetailsListRequestDTO.getMonitoringResponsibiltyComboBoxList().isEmpty()) {
					String invalidValues = "";
					List<CAMMonitoringResponsibilityRequestDTO> camMRList = new ArrayList<CAMMonitoringResponsibilityRequestDTO>();
					for(CAMMonitoringResponsibilityRequestDTO mrDTO : otherCovenantDetailsListRequestDTO.getMonitoringResponsibiltyComboBoxList()) {
						try {
							if (mrDTO.getMonitoringResponsibiltyValue() != null && !mrDTO.getMonitoringResponsibiltyValue().trim().isEmpty()) {
								if(ASSTValidator.isNumeric(mrDTO.getMonitoringResponsibiltyValue().trim())){								
									Object obj = masterObj.getMaster("entryCode", Long.parseLong(mrDTO.getMonitoringResponsibiltyValue().trim()));
									if(obj != null){
										ICommonCodeEntry codeEntry = (ICommonCodeEntry)obj;
										if("MONITORING_RESPONSIBILITY".equals(codeEntry.getCategoryCode())){
											mrDTO.setMonitoringResponsibiltyValue(((ICommonCodeEntry)obj).getEntryCode()+"-"+((ICommonCodeEntry)obj).getEntryName());
											camMRList.add(mrDTO);
										}else{
											invalidValues += mrDTO.getMonitoringResponsibiltyValue().trim()+",";
										}
									}else {
										invalidValues += mrDTO.getMonitoringResponsibiltyValue().trim()+",";
									}
								}else {
									invalidValues += mrDTO.getMonitoringResponsibiltyValue().trim()+",";
								}
							}
						}catch(Exception e) {
							e.printStackTrace();
							invalidValues += mrDTO.getMonitoringResponsibiltyValue().trim()+",";
						}	
					}

					if (invalidValues != null && invalidValues.length() > 0) {
						errors.add("MonitoringResponsibiltyValue",new ActionMessage("Invalid values :"+invalidValues));						
					}
					otherCovenantDetailsListRequestDTO.setMonitoringResponsibiltyComboBoxList(camMRList);
				}
				
				
				String partyId = requestDTO.getPartyId().trim();
				MILimitUIHelper helper = new MILimitUIHelper();
				SBMILmtProxy proxy = helper.getSBMILmtProxy();
		     	IOtherCovenantDetailsDAO othercovenantdetailsdaoimpl = (IOtherCovenantDetailsDAO)BeanHouse.get("otherCoveantDeatilsDAO");
		     	String cmsLimitProfileId =String.valueOf(othercovenantdetailsdaoimpl.getCMSLimitProfileIdActual(partyId));
				List facilityNamesList = new ArrayList();
				
				if("Facility".equals(otherCovenantDetailsListRequestDTO.getCovenantCategory().trim())){
					if(otherCovenantDetailsListRequestDTO.getFacilityNameComboBoxList()!=null){
						List<CAMFacilityNameRequestDTO> cfnDTOList = new ArrayList<CAMFacilityNameRequestDTO>();
						List<String> fnv1 = new ArrayList<String>();	
						try {
							facilityNamesList = proxy.getFacilityNameByAAId(cmsLimitProfileId);						
							for (int i = 0; i < facilityNamesList.size(); i++) {
								List tempList = (List) facilityNamesList.get(i);
								fnv1.add((String) tempList.get(0));
							}	
						} catch (Exception e) {
							e.printStackTrace();
						}
						boolean flag = true;
						String invalidValues = "";
						if(fnv1 != null && !fnv1.isEmpty()) {
							for(CAMFacilityNameRequestDTO camfnDTO : otherCovenantDetailsListRequestDTO.getFacilityNameComboBoxList()) {
								if(camfnDTO!=null){
									if(fnv1.contains(camfnDTO.getFacilityNameValue())) {
										camfnDTO.setFacilityNameValue(camfnDTO.getFacilityNameValue() +"-"+camfnDTO.getFacilityNameValue());
										cfnDTOList.add(camfnDTO);	
										flag = false;
									}else {
										invalidValues += camfnDTO.getFacilityNameValue().trim()+",";
									}
								}
							}
						} else {
							for(CAMFacilityNameRequestDTO camfnDTO : otherCovenantDetailsListRequestDTO.getFacilityNameComboBoxList()) {
								if(camfnDTO!=null){
									flag = false;
									invalidValues += camfnDTO.getFacilityNameValue().trim()+",";
								}
							}							
						}

						if (invalidValues != null && invalidValues.length() > 0) {
							errors.add("FacilityNameValue",new ActionMessage("Invalid values : "+invalidValues));
						}

						if(flag) {
							errors.add("FacilityNameValue", new ActionMessage("error.string.mandatory"));
						}						
						otherCovenantDetailsListRequestDTO.setFacilityNameComboBoxList(cfnDTOList);
					}else {
						errors.add("FacilityNameValue", new ActionMessage("error.string.mandatory"));
					}
				}	
				
				otherCamCovList.add(otherCovenantDetailsListRequestDTO);

				//New CAM ONLINE CR END
			}
				
		}
		
		requestDTO.setOtherCovenantDetailsList(otherCamCovList);
		
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

}
