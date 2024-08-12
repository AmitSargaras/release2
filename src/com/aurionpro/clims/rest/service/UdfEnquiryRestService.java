package com.aurionpro.clims.rest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import com.aurionpro.clims.rest.constants.WholeSaleApi_Constant;
import com.aurionpro.clims.rest.common.ValidationErrorDetailsDTO;
import com.aurionpro.clims.rest.common.ValidationUtilityRest;
import com.aurionpro.clims.rest.constants.ResponseConstants;
import com.aurionpro.clims.rest.dto.*;
import com.integrosys.cms.app.udf.bus.OBUdf;
import com.aurionpro.clims.rest.mapper.UdfEnquiryDTOMapper;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.ws.common.CLIMSWebService;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;

public class UdfEnquiryRestService {

	
	UdfEnquiryDTOMapper udfEnquiryDTOMapper = new UdfEnquiryDTOMapper();

	MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
	
	public String getEntityName(){
		return "actualUdf";
	}	


	public CommonRestResponseDTO getUdfDetails(UdfEnquiryRestRequestDTO requestDto, String pathInfo) throws Exception {
		
		CommonRestResponseDTO obj = new CommonRestResponseDTO();
		HeaderDetailRestResponseDTO headerObj= new  HeaderDetailRestResponseDTO();
		BodyRestResponseDTO bodyObj= new  BodyRestResponseDTO();
		List<ResponseMessageDetailDTO> responseMessageList = new LinkedList<ResponseMessageDetailDTO>();
		List<HeaderDetailRestResponseDTO> ccHeaderResponse = new LinkedList<HeaderDetailRestResponseDTO>();
		List<BodyRestResponseDTO> bodyRestList = new LinkedList<BodyRestResponseDTO>();

		headerObj.setRequestId(requestDto.getHeaderDetails().get(0).getRequestId());
		headerObj.setChannelCode(requestDto.getHeaderDetails().get(0).getChannelCode());
		ccHeaderResponse.add(headerObj);
		obj.setHeaderDetails(ccHeaderResponse);

		List<ValidationErrorDetailsDTO> validationErrorDetailsDTOList;
		UdfEnqRestRequestDTO udfRequest = requestDto.getBodyDetails().get(0);
		
		if(pathInfo.startsWith("/" + WholeSaleApi_Constant.GET_PARTY_UDF)){
			udfRequest.setModuleId("1");
		}else if(pathInfo.startsWith("/" + WholeSaleApi_Constant.GET_CAM_UDF)){
			udfRequest.setModuleId("2");
		}else if(pathInfo.startsWith("/" + WholeSaleApi_Constant.GET_LIMIT1_UDF)){
			udfRequest.setModuleId("3");
		}else if(pathInfo.startsWith("/" + WholeSaleApi_Constant.GET_LIMIT2_UDF)){
			udfRequest.setModuleId("4");
		}

		udfRequest = udfEnquiryDTOMapper.getUdfEnquiryRestReqDTOWithActualValues(udfRequest);
		ActionErrors requestErrors = udfRequest.getErrors();		
		
		HashMap map = new HashMap();
		if(requestErrors.size()>0){
			map.put("1", requestErrors);
			validationErrorDetailsDTOList = ValidationUtilityRest.handleError(map, CLIMSWebService.UDF);
			for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
			    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
			    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
			    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
			    responseMessageList.add(rmd);
			}
		}

		if(responseMessageList.size() > 0) {
			bodyObj.setResponseStatus("FAIL");
			bodyObj.setResponseMessageList(responseMessageList);
			bodyRestList.add(bodyObj);
			obj.setBodyDetails(bodyRestList);
		}
		else 
		{
			try {				
				List<OBUdf> obUdf = new ArrayList<OBUdf>();
				if(udfRequest.getSequence()!=null && udfRequest.getMandatory()!=null && udfRequest.getStatus()!=null) {
					
					if(udfRequest.getSequence().isEmpty() && udfRequest.getMandatory().isEmpty() && udfRequest.getStatus().isEmpty()) {
						obUdf = masterObj.getUdfListEnquiry(getEntityName(), udfRequest.getModuleId().trim(),"ALL",udfRequest.getMandatory().trim(),udfRequest.getStatus().trim());	
					}else if(!udfRequest.getMandatory().isEmpty() && udfRequest.getStatus().isEmpty() && udfRequest.getSequence().isEmpty()) {
						obUdf = masterObj.getUdfListEnquiry(getEntityName(), udfRequest.getModuleId().trim(),udfRequest.getSequence().trim(),udfRequest.getMandatory().trim(),udfRequest.getStatus().trim());	
					}else if(!udfRequest.getStatus().isEmpty() && udfRequest.getSequence().isEmpty() && udfRequest.getMandatory().isEmpty()){
						obUdf = masterObj.getUdfListEnquiry(getEntityName(), udfRequest.getModuleId().trim(),udfRequest.getSequence().trim(),udfRequest.getMandatory().trim(),udfRequest.getStatus().trim());	
					}else if(!udfRequest.getSequence().isEmpty() && udfRequest.getStatus().isEmpty() && udfRequest.getMandatory().isEmpty()){
						obUdf = masterObj.getUdfListEnquiry(getEntityName(), udfRequest.getModuleId().trim(),udfRequest.getSequence().trim(),udfRequest.getMandatory().trim(),udfRequest.getStatus().trim());	
					}else if(!udfRequest.getSequence().isEmpty() && !udfRequest.getMandatory().isEmpty() && !udfRequest.getStatus().isEmpty()){
						obUdf = masterObj.getUdfListEnquiry(getEntityName(), udfRequest.getModuleId().trim(),udfRequest.getSequence().trim(),udfRequest.getMandatory().trim(),udfRequest.getStatus().trim());	
					}else if(!udfRequest.getSequence().isEmpty() && !udfRequest.getMandatory().isEmpty() && udfRequest.getStatus().isEmpty()){
						obUdf = masterObj.getUdfListEnquiry(getEntityName(), udfRequest.getModuleId().trim(),udfRequest.getSequence().trim(),udfRequest.getMandatory().trim(),udfRequest.getStatus().trim());	
					}else if(!udfRequest.getSequence().isEmpty() && !udfRequest.getStatus().isEmpty() && udfRequest.getMandatory().isEmpty()){
						obUdf = masterObj.getUdfListEnquiry(getEntityName(), udfRequest.getModuleId().trim(),udfRequest.getSequence().trim(),udfRequest.getMandatory().trim(),udfRequest.getStatus().trim());	
					}else if(!udfRequest.getMandatory().isEmpty() && !udfRequest.getStatus().isEmpty() && udfRequest.getSequence().isEmpty()){
						obUdf = masterObj.getUdfListEnquiry(getEntityName(), udfRequest.getModuleId().trim(),udfRequest.getSequence().trim(),udfRequest.getMandatory().trim(),udfRequest.getStatus().trim());	
					}
					
					if (obUdf != null && obUdf.size() > 0) {						
						List<UdfEnqRestResponseDTO> udfEnqRestResponseDTOList = new LinkedList<UdfEnqRestResponseDTO>();
						for (OBUdf item : obUdf) {						
							UdfEnqRestResponseDTO udfEnqRestResponseDTO = new UdfEnqRestResponseDTO();
							String sequence = String.valueOf(item.getSequence());
							if (sequence != null && !sequence.isEmpty()) {
								udfEnqRestResponseDTO.setSequence(sequence);
							} else {
								udfEnqRestResponseDTO.setSequence("-");
							}
							if (item.getFieldName() != null && !item.getFieldName().isEmpty()) {
								udfEnqRestResponseDTO.setFieldName(item.getFieldName());
							} else {
								udfEnqRestResponseDTO.setFieldName("-");
							}
							if (item.getOptions() != null && !item.getOptions().isEmpty()) {
								udfEnqRestResponseDTO.setFieldValue(item.getOptions());
							} else {
								udfEnqRestResponseDTO.setFieldValue("-");
							}
							if (item.getMandatory() != null && !item.getMandatory().isEmpty()) {
								udfEnqRestResponseDTO.setMandatory(item.getMandatory());
							} else {
								udfEnqRestResponseDTO.setMandatory("-");
							}
							if (item.getNumericLength() != null && !item.getNumericLength().isEmpty()) {
								udfEnqRestResponseDTO.setFieldLength(item.getNumericLength());
							} else {
								udfEnqRestResponseDTO.setFieldLength("-");
							}
							if (item.getStatus() != null && !item.getStatus().isEmpty()) {
								udfEnqRestResponseDTO.setStatus(item.getStatus());
							} else {
								udfEnqRestResponseDTO.setStatus("-");
							}
							udfEnqRestResponseDTOList.add(udfEnqRestResponseDTO);
						}
						ResponseMessageDetailDTO responseMessageDetailDTO =new ResponseMessageDetailDTO();
						responseMessageDetailDTO.setResponseCode(ResponseConstants.UDF_SUCCESS_RESPONSE_CODE);
						responseMessageDetailDTO.setResponseMessage(ResponseConstants.UDF_SUCCESS_MESSAGE);
						responseMessageList.add(responseMessageDetailDTO);
						bodyObj.setResponseStatus("SUCCESS");
						bodyObj.setResponseMessageList(responseMessageList);
						bodyObj.setUdfEnqRestResponseDTO(udfEnqRestResponseDTOList);
						bodyRestList.add(bodyObj);
						obj.setBodyDetails(bodyRestList);
					}
					else
					{
						ResponseMessageDetailDTO responseMessageDetailDTO =new ResponseMessageDetailDTO();
						responseMessageDetailDTO.setResponseCode(ResponseConstants.UDF_ENQUIRY_REQ);
						responseMessageDetailDTO.setResponseMessage(ResponseConstants.UDF_NO_DATA_FOUND_MESSAGE);
						responseMessageList.add(responseMessageDetailDTO);
						bodyObj.setResponseStatus("FAIL");
						bodyObj.setResponseMessageList(responseMessageList);
						bodyRestList.add(bodyObj);
						obj.setBodyDetails(bodyRestList);
					}

				}
				else
				{
					ResponseMessageDetailDTO responseMessageDetailDTO =new ResponseMessageDetailDTO();
					responseMessageDetailDTO.setResponseCode(ResponseConstants.UDF_ENQUIRY_REQ);
					responseMessageDetailDTO.setResponseMessage(ResponseConstants.UDF_SIGNATURE_MESSAGE);
					responseMessageList.add(responseMessageDetailDTO);
					bodyObj.setResponseStatus("FAIL");
					bodyObj.setResponseMessageList(responseMessageList);
					bodyRestList.add(bodyObj);
					obj.setBodyDetails(bodyRestList);
				}
			}
			catch (Exception e) {
				ResponseMessageDetailDTO responseMessageDetailDTO =new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.EXCEPTION);
				responseMessageDetailDTO.setResponseMessage(e.getMessage());
				responseMessageList.add(responseMessageDetailDTO);			
				bodyObj.setResponseStatus("FAIL");
				bodyRestList.add(bodyObj);
				obj.setBodyDetails(bodyRestList);
				e.printStackTrace();
			}
		}

		return obj;
	}

}