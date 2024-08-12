package com.integrosys.cms.app.ws.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.springframework.stereotype.Service;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.ui.discrepency.DiscrepencyForm;

@Service
public class DiscrepancyDetailsDTOMapper {
	public OBDiscrepency  getActualFromDTO( DiscrepancyDetailRequestDTO dto ) {

		SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yyyy");
		OBDiscrepency discrepancy = new OBDiscrepency();
		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		
		discrepancy.setDiscrepencyType("General");//by default general
		try {
			if(dto.getCreationDate()!=null && !dto.getCreationDate().trim().isEmpty()){
				discrepancy.setCreationDate(date.parse(dto.getCreationDate().trim()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			if(dto.getApprovedDate()!=null && !dto.getApprovedDate().trim().isEmpty()){
				discrepancy.setAcceptedDate(date.parse(dto.getApprovedDate().trim()));
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			if(dto.getOriginalTargetDate()!=null && !dto.getOriginalTargetDate().trim().isEmpty()){
				discrepancy.setOriginalTargetDate(date.parse(dto.getOriginalTargetDate().trim()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(dto.getCreditApprover()!=null && !dto.getCreditApprover().trim().isEmpty()){
			Object obj;
			try {
				obj = masterObj.getObjByEntityNameAndCPSId("actualCreditApproval", dto.getCreditApprover().trim());
				if(!(obj instanceof ActionErrors)){
					discrepancy.setCreditApprover(((ICreditApproval)obj).getApprovalCode());
					discrepancy.setApprovedBy(((ICreditApproval)obj).getApprovalCode());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			discrepancy.setCreditApprover("");
			discrepancy.setApprovedBy("");
		}
		
		if(dto.getRemarks()!=null && !dto.getRemarks().trim().isEmpty()){
			discrepancy.setDiscrepencyRemark(dto.getRemarks().trim());
		}else{
			discrepancy.setDiscrepencyRemark("");
		}
		
		
		discrepancy.setCritical("No");
		discrepancy.setDiscrepency("CHQ_NT_SG");
		discrepancy.setDeferedCounter(0l);
		if(dto.getCustomerId()!=null && !dto.getCustomerId().trim().isEmpty()){
			discrepancy.setCustomerId(Long.parseLong(dto.getCustomerId()));//in long
		}else{
			discrepancy.setCustomerId(0l);//in long
		}
		discrepancy.setTransactionStatus("ACTIVE");
		discrepancy.setStatus("ACTIVE");
		
		return discrepancy;
	}

	public DiscrepancyDetailRequestDTO  getRequestDTOWithActualValues( DiscrepancyDetailRequestDTO dto,String customerId ) {

		ActionErrors errors = new ActionErrors();
		SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yyyy");
		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		
		if(dto.getCreationDate()!=null && !dto.getCreationDate().trim().isEmpty()){
			try {
				date.parse(dto.getCreationDate().trim());
				dto.setCreationDate(dto.getCreationDate().trim());
			} catch (ParseException e) {
				errors.add("creationDateError",new ActionMessage("error.creationDate.invalid.format"));
			}
		}
		if(dto.getApprovedDate()!=null && !dto.getApprovedDate().trim().isEmpty()){
			try {
				date.parse(dto.getApprovedDate().trim());
				dto.setApprovedDate(dto.getApprovedDate().trim());
			} catch (ParseException e) {
				errors.add("approvedDate",new ActionMessage("error.approvedDate.invalid.format"));
			}
		}
		if(dto.getOriginalTargetDate()!=null && !dto.getOriginalTargetDate().trim().isEmpty()){
			try {
				date.parse(dto.getOriginalTargetDate().trim());
				dto.setOriginalTargetDate(dto.getOriginalTargetDate().trim());
			} catch (ParseException e) {
				errors.add("originalTargetDateError",new ActionMessage("error.originalTargetDate.invalid.format"));
			}
		}
	
		if(dto.getCreditApprover()!=null && !dto.getCreditApprover().trim().isEmpty()){
			Object obj = masterObj.getObjectByEntityNameAndCPSId("actualCreditApproval", dto.getCreditApprover().trim(),"creditApprover",errors);
			if(!(obj instanceof ActionErrors)){
				dto.setCreditApprover(((ICreditApproval)obj).getCpsId());
			}
		}
		
		if(dto.getCpsDiscrepancyId()!=null && !dto.getCpsDiscrepancyId().trim().isEmpty()){
			dto.setCpsDiscrepancyId(dto.getCpsDiscrepancyId().trim());
		}
		
		if(dto.getRemarks()!=null && !dto.getRemarks().trim().isEmpty()){
			dto.setRemarks(dto.getRemarks().trim());
		}else{
			dto.setRemarks("");
		}
		
		dto.setCustomerId(customerId);

		dto.setErrors(errors);
		return dto;
	}
	
	
	public DiscrepencyForm  getFormFromDTO( DiscrepancyDetailRequestDTO dto) {

		DiscrepencyForm form = new DiscrepencyForm();	
		form.setAcceptedDate(dto.getApprovedDate());
		form.setCreationDate(dto.getCreationDate());
		form.setOriginalTargetDate(dto.getOriginalTargetDate());
		form.setCreditApprover(dto.getCreditApprover());
		form.setDiscrepencyRemark(dto.getRemarks());
		form.setDiscrepencyType("General");
		form.setCritical("No");
		form.setDiscrepency("CHQ_NT_SG");
		form.setDeferedCounter("0");
		form.setCustomerId(dto.getCustomerId());
		form.setApprovedBy(dto.getCreditApprover());
		form.setEvent("WS_Add_Discrepency");
		form.setCpsDiscrepancyId(dto.getCpsDiscrepancyId());
		return form;
	}

}
