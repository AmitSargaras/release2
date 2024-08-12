package com.aurionpro.clims.rest.dto;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.springframework.stereotype.Service;

import com.aurionpro.clims.rest.constants.ResponseConstants;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;

@Service
public class CommonCodeDTOMapper {

	public CommonRestResponseDTO getCommonCodeRestResponseDTOWithActualValues(CommonCodeRestRequestDTO requestDTO) {

		CommonRestResponseDTO commonCodeRestResponseDTO =new CommonRestResponseDTO();
		
		BodyRestResponseDTO bodyObj= new  BodyRestResponseDTO();
		List<BodyRestResponseDTO> bodyRestList = new LinkedList<BodyRestResponseDTO>();
		HeaderDetailRestResponseDTO headerObj= new  HeaderDetailRestResponseDTO();
		List<HeaderDetailRestResponseDTO> ccHeaderResponse = new LinkedList<HeaderDetailRestResponseDTO>();
		MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();
		DefaultLogger.debug(this,
				"Inside getCommonCodeRestResponseDTOWithActualValues of CommonCodeDTOMapper================:::::");
		System.out.println("Inside getCommonCodeRestResponseDTOWithActualValues of CommonCodeDTOMapper================:::::");
		SimpleDateFormat pDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		
		List<ResponseMessageDetailDTO> responseMessageList = new LinkedList<ResponseMessageDetailDTO>();
		
		MILimitUIHelper helper = new MILimitUIHelper();
		ILimitDAO limitDAO = LimitDAOFactory.getDAO();
		
		try {
			
			for(int i=0;i<requestDTO.getHeaderDetails().size();i++){
			
			RestApiHeaderRequestDTO restHeaderRequest = requestDTO.getHeaderDetails().get(i);
			
			if (restHeaderRequest.getChannelCode() != null) {
				if (restHeaderRequest.getChannelCode().trim().isEmpty()) {
					ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
					responseMessageDetailDTO.setResponseMessage(
							ResponseConstants.CHANNEL_CODE_REQ_MESSAGE);
					responseMessageDetailDTO.setResponseCode(
							ResponseConstants.CHANNEL_CODE_REQ);
					responseMessageList.add(responseMessageDetailDTO);
					
					bodyObj.setResponseMessageList(responseMessageList);

				}
			}
			if (restHeaderRequest.getRequestId() != null) {
				if (restHeaderRequest.getRequestId().trim().isEmpty()) {
					ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
					responseMessageDetailDTO.setResponseMessage(
							ResponseConstants.REQUEST_ID_REQ_MESSAGE);
					responseMessageDetailDTO
							.setResponseCode(ResponseConstants.REQUEST_ID_REQ);
					responseMessageList.add(responseMessageDetailDTO);
					
					
					bodyObj.setResponseMessageList(responseMessageList);

				}
			}
			
			if (restHeaderRequest.getPassCode() != null) {
				if (restHeaderRequest.getPassCode().trim().isEmpty()) {
					ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
					responseMessageDetailDTO.setResponseMessage(
							ResponseConstants.PASSCODE_REQ_MESSAGE);
					responseMessageDetailDTO
							.setResponseCode(ResponseConstants.PASSCODE_REQ);
					responseMessageList.add(responseMessageDetailDTO);
					
					bodyObj.setResponseMessageList(responseMessageList);

				}
			}
			
			
			
			
			
			}//End For
			
			for(int i=0;i<requestDTO.getBodyDetails().size();i++){
				
				RestApiBodyRequestDTO restBodyRequest = requestDTO.getBodyDetails().get(i);
			
				if (restBodyRequest.getCategoryCode() != null) {
					if (restBodyRequest.getCategoryCode().trim().isEmpty()) {
						ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
						responseMessageDetailDTO.setResponseMessage(
								ResponseConstants.CC_CATEGORY_CODE_REQ_MESSAGE);
						responseMessageDetailDTO.setResponseCode(
								ResponseConstants.CC_CATEGORY_CODE_REQ);
						responseMessageList.add(responseMessageDetailDTO);
			
						bodyObj.setResponseMessageList(responseMessageList);
					}
				}
				/*if (restBodyRequest.getStatus() != null) {
					if (!restBodyRequest.getStatus().isEmpty()) {
						if (!restBodyRequest.getStatus().contains("ACTIVE")) {
							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(
									ResponseConstants.CC_STATUS_INVALID);
							responseMessageDetailDTO.setResponseMessage(
									ResponseConstants.CC_STATUS_INVALID_MESSAGE);
							responseMessageList.add(responseMessageDetailDTO);

					        
						}
					}
				}*/
				if (restBodyRequest.getCategoryCode() != null) {
					if (!restBodyRequest.getCategoryCode().isEmpty()) {
						if (!limitDAO
								.getCategory_code(restBodyRequest.getCategoryCode())) {
							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(
									ResponseConstants.CC_CATEGORY_CODE_INVALID);
							responseMessageDetailDTO.setResponseMessage(
									ResponseConstants.CC_CATEGORY_CODE_INVALID_MESSAGE);
							responseMessageList.add(responseMessageDetailDTO);

						
					        
						}
					}
				}
				if (restBodyRequest.getEntryCode() != null) {
					if (!restBodyRequest.getEntryCode().isEmpty()) {
						if (restBodyRequest.getEntryCode().length() > 60) {

							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(ResponseConstants.CC_ENTRY_CODE_LEN);
							responseMessageDetailDTO.setResponseMessage(ResponseConstants.CC_ENTRY_CODE_LEN_MESSAGE);
							responseMessageList.add(responseMessageDetailDTO);

						}
						
						if (restBodyRequest.getEntryCode().length() <= 60 && !limitDAO.getEntry_code(restBodyRequest.getEntryCode())) {
							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(
									ResponseConstants.CC_ENTRY_CODE_INVALID);
							responseMessageDetailDTO.setResponseMessage(
									ResponseConstants.CC_ENTRY_CODE_INVALID_MESSAGE);
							responseMessageList.add(responseMessageDetailDTO);

							
					        
						}
					}
				}
				if (restBodyRequest.getEntryName() != null) {
					if (!restBodyRequest.getEntryName().isEmpty()) {
						if (restBodyRequest.getEntryName().length() > 750) {
							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(ResponseConstants.CC_ENTRY_NAME_LEN);
							responseMessageDetailDTO.setResponseMessage(ResponseConstants.CC_ENTRY_NAME_LEN_MESSAGE);
							responseMessageList.add(responseMessageDetailDTO);
						}

						if (restBodyRequest.getEntryName().length() <= 750
								&& !limitDAO.getEntry_name(restBodyRequest.getEntryName())) {
							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(ResponseConstants.CC_ENTRY_NAME_INVALID);
							responseMessageDetailDTO.setResponseMessage(ResponseConstants.CC_ENTRY_NAME_INVALID_MESSAGE);
							responseMessageList.add(responseMessageDetailDTO);

						}
					}
				}
				if (restBodyRequest.getEntryId() != null) {
					if (!restBodyRequest.getEntryId().isEmpty()) {
						if (!ASSTValidator.isNumeric(restBodyRequest.getEntryId())) {
							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(ResponseConstants.CC_ENTRY_ID_NUM);
							responseMessageDetailDTO.setResponseMessage(ResponseConstants.CC_ENTRY_ID_NUM_MESSAGE);
							responseMessageList.add(responseMessageDetailDTO);

						}

						if (ASSTValidator.isNumeric(restBodyRequest.getEntryId())
								&& restBodyRequest.getEntryId().length() > 19) {
							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(ResponseConstants.CC_ENTRY_ID_LEN);
							responseMessageDetailDTO.setResponseMessage(ResponseConstants.CC_ENTRY_ID_LEN_MESSAGE);
							responseMessageList.add(responseMessageDetailDTO);
						}

						if (ASSTValidator.isNumeric(restBodyRequest.getEntryId())
								&& restBodyRequest.getEntryId().length() <= 19
								&& !limitDAO.getEntry_id(restBodyRequest.getEntryId())) {
							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(ResponseConstants.CC_ENTRY_ID_INVALID);
							responseMessageDetailDTO.setResponseMessage(ResponseConstants.CC_ENTRY_ID_INVALID_MESSAGE);
							responseMessageList.add(responseMessageDetailDTO);

						}
					}
				}
				if (restBodyRequest.getStatus() != null) {
					if (!restBodyRequest.getStatus().isEmpty()) {
						if (  !(restBodyRequest.getStatus().contains("ACTIVE")) ) {
							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(
									ResponseConstants.CC_STATUS_INVALID);
							responseMessageDetailDTO.setResponseMessage(
									ResponseConstants.CC_STATUS_INVALID_MESSAGE);
							responseMessageList.add(responseMessageDetailDTO);
					
					        
						}
					}
				}
			}
			
			
		} catch (Exception e) {
			ResponseMessageDetailDTO responseMessageDetailDTO =new ResponseMessageDetailDTO();
			responseMessageDetailDTO.setResponseCode(ResponseConstants.EXCEPTION);
			responseMessageDetailDTO.setResponseMessage(e.getMessage());
			responseMessageList.add(responseMessageDetailDTO);
			
	        bodyObj.setResponseStatus("FAILED");
			e.printStackTrace();
		}
		if(responseMessageList != null && !responseMessageList.isEmpty())
		{
			bodyObj.setResponseMessageList(responseMessageList);
			bodyObj.setResponseStatus("FAILED");
			bodyRestList.add(bodyObj);
			commonCodeRestResponseDTO.setHeaderDetails(ccHeaderResponse);
			commonCodeRestResponseDTO.setBodyDetails(bodyRestList);
			
		}
		
		
		return commonCodeRestResponseDTO;
	}
	
}
