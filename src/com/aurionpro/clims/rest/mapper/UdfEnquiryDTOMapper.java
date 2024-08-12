package com.aurionpro.clims.rest.mapper;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.springframework.stereotype.Service;
import com.aurionpro.clims.rest.dto.*;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.app.udf.bus.OBUdf;

@Service
public class UdfEnquiryDTOMapper {

	MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");

	public UdfEnqRestRequestDTO getUdfEnquiryRestReqDTOWithActualValues(UdfEnqRestRequestDTO requestDTO) {
		ActionErrors errors = new ActionErrors();
		
		if (requestDTO.getSequence() != null && !requestDTO.getSequence().trim().isEmpty()) {
			if(ASSTValidator.isNumeric(requestDTO.getSequence().trim())) {
				Object obj = masterObj.getObjectByEntityNameAndSequenceForRestUdf("actualUdf",requestDTO.getModuleId(),
						requestDTO.getSequence().trim(), "sequence", errors);
				if (!(obj instanceof ActionErrors)) {
					requestDTO.setSequence(String.valueOf(((OBUdf) obj).getSequence()));
				}
			}else {
				errors.add("sequence", new ActionMessage("sequence must be numeric"));
			}			
		}else {
			requestDTO.setSequence("");
		}		
		if (requestDTO.getFieldName() != null && !requestDTO.getFieldName().trim().isEmpty()) {
			requestDTO.setFieldName(requestDTO.getFieldName().trim());
		}else {
			requestDTO.setFieldName("");
		}
		if (requestDTO.getMandatory() != null && !requestDTO.getMandatory().trim().isEmpty()) {
			if(requestDTO.getMandatory().trim().equals("on")) {
				requestDTO.setMandatory(requestDTO.getMandatory().trim());
			}else {
				errors.add("mandatory", new ActionMessage("error.field.invalid"));
			}
		}else {
			requestDTO.setMandatory("");
		}
		if (requestDTO.getStatus() != null && !requestDTO.getStatus().trim().isEmpty()) {
			if(requestDTO.getStatus().trim().equals("ACTIVE") || requestDTO.getStatus().trim().equals("INACTIVE")) {
				requestDTO.setStatus(requestDTO.getStatus().trim());
			}else {
				errors.add("status", new ActionMessage("error.status.invalid"));
			}
		}else {
			requestDTO.setStatus("");
		}
		
		requestDTO.setErrors(errors);
		
		return requestDTO;
	}	
}