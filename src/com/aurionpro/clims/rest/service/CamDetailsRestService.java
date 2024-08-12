package com.aurionpro.clims.rest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aurionpro.clims.rest.common.ValidationErrorDetailsDTO;
import com.aurionpro.clims.rest.common.ValidationUtilityRest;
import com.aurionpro.clims.rest.dto.BodyRestResponseDTO;
import com.aurionpro.clims.rest.dto.CamBodyRestRequestDTO;
import com.aurionpro.clims.rest.dto.CamDetailsRestRequestDTO;
import com.aurionpro.clims.rest.dto.CommonRestResponseDTO;
import com.aurionpro.clims.rest.dto.HeaderDetailRestResponseDTO;
import com.aurionpro.clims.rest.dto.ResponseMessageDetailDTO;
import com.aurionpro.clims.rest.mapper.CamDetailsRestDTOMapper;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.ws.common.CLIMSWebService;
import com.integrosys.cms.app.ws.jax.common.CMSException;
import com.integrosys.cms.host.eai.limit.bus.ILimitJdbc;
import com.integrosys.cms.ui.ecbf.counterparty.ClimesToECBFHelper;
import com.integrosys.cms.ui.manualinput.aa.AADetailForm;
import com.aurionpro.clims.rest.validator.CamDetailsRestValidator;

public class CamDetailsRestService {

	@Autowired
	@Qualifier("camDetailsRestDTOMapper")
	private CamDetailsRestDTOMapper camDetailsRestDTOMapper;

	private ILimitJdbc limitJdbc;
	
	public ILimitJdbc getLimitJdbc() {
		return limitJdbc;
	}

	public void setLimitJdbc(ILimitJdbc limitJdbc) {
		this.limitJdbc = limitJdbc;
	}

	public void setCamDetailsRestDTOMapper(CamDetailsRestDTOMapper camDetailsRestDTOMapper) {
		this.camDetailsRestDTOMapper = camDetailsRestDTOMapper;
	}

	public CommonRestResponseDTO  addCamDetailsRest(CamBodyRestRequestDTO camBodyRestRequestDTO) {

		CommonRestResponseDTO camResponse = new CommonRestResponseDTO();
		List<BodyRestResponseDTO> bodyRestList = new LinkedList<BodyRestResponseDTO>();
		BodyRestResponseDTO bodyObj= new  BodyRestResponseDTO();
		HeaderDetailRestResponseDTO headerObj= new  HeaderDetailRestResponseDTO();
		List<HeaderDetailRestResponseDTO> ccHeaderResponse = new LinkedList<HeaderDetailRestResponseDTO>();

		List<ResponseMessageDetailDTO> responseMessageDetailDTOList = new ArrayList<ResponseMessageDetailDTO>();
	    ResponseMessageDetailDTO responseMessageDetailsDTO = new ResponseMessageDetailDTO();
		List<ValidationErrorDetailsDTO> validationErrorDetailsDTOList;

		headerObj.setRequestId(camBodyRestRequestDTO.getHeaderDetails().get(0).getRequestId());
		headerObj.setChannelCode(camBodyRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
		ccHeaderResponse.add(headerObj);
		camResponse.setHeaderDetails(ccHeaderResponse);

		DefaultLogger.debug(this, "inside addCamDetailsRest");
		CamDetailsRestRequestDTO camRequest = camBodyRestRequestDTO.getBodyDetails().get(0);
		camRequest.setEvent("REST_CAM_CREATE");
		String partyId = "";
		String status = "";
		if(camRequest.getPartyId()!=null && !camRequest.getPartyId().trim().isEmpty()){
			partyId = camRequest.getPartyId().trim();
			status = getLimitJdbc().getPartyStatus(partyId);
		}
		
	    if(camRequest.getPartyId()==null || camRequest.getPartyId().trim().isEmpty()){
		    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
		    rmd.setResponseCode("CAM0001");
		    rmd.setResponseMessage("PartyId is mandatory");
		    responseMessageDetailDTOList.add(rmd);
		}
		else if(status!=null && status.equals("INACTIVE")){
		    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
		    rmd.setResponseCode("CAM0002");
		    rmd.setResponseMessage("Party is inactive in system");
		    responseMessageDetailDTOList.add(rmd);
		}
		else if(status!=null && status.equals("NOTEXIST")){
		    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
		    rmd.setResponseCode("CAM0002");
		    rmd.setResponseMessage("Party does not exists in system");
		    responseMessageDetailDTOList.add(rmd);
		}		

		 
		if(responseMessageDetailDTOList.size() > 0) {
			bodyObj.setCamId("");
			bodyObj.setResponseStatus("FAILED");
		    bodyObj.setResponseMessageList(responseMessageDetailDTOList);
		    bodyRestList.add(bodyObj);
		    camResponse.setBodyDetails(bodyRestList);	
			return camResponse;
		}
		
		String camId = getLimitJdbc().getCamByLlpLeId(partyId);
		CamDetailsRestRequestDTO CamDetailsRestRequestDTO = camDetailsRestDTOMapper.getRequestDTOWithActualValues(camRequest,camId);
		ActionErrors requestErrors = CamDetailsRestRequestDTO.getErrors();		
		HashMap map = new HashMap();
		if(requestErrors.size()>0){
			map.put("1", requestErrors);
			validationErrorDetailsDTOList = ValidationUtilityRest.handleError(map, CLIMSWebService.CAM);
			for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
			    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
			    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
			    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
			    responseMessageDetailDTOList.add(rmd);
			}
		}
		AADetailForm form = camDetailsRestDTOMapper.getFormFromDTO(camRequest);
		ActionErrors errorList = CamDetailsRestValidator.validateInput(form, Locale.getDefault()); 
		HashMap newMap = new HashMap();		
		if(errorList.size()>0){
			newMap.put("1", errorList);
			validationErrorDetailsDTOList = ValidationUtilityRest.handleError(newMap,CLIMSWebService.CAM);
			for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
			    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
			    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
			    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
			    responseMessageDetailDTOList.add(rmd);
			}
		}
				
		if (responseMessageDetailDTOList.isEmpty()) {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			OBTrxContext trxContext = new OBTrxContext();
			ILimitProfileTrxValue limitProfileTrxVal =null;
			OBLimitProfile actualLimitProfile = null;
			ICMSTrxResult trxResult = null;
		
			try {

				actualLimitProfile = (OBLimitProfile)camDetailsRestDTOMapper.getActualFromDTO(camRequest,null);
				
				trxResult = proxy.submitCreateLimitProfile(trxContext, limitProfileTrxVal, actualLimitProfile);
				String id = "";
				if(trxResult != null && trxResult.getTrxValue() != null){
					limitProfileTrxVal = (ILimitProfileTrxValue)trxResult.getTrxValue();
					if(limitProfileTrxVal!= null){
						if(limitProfileTrxVal.getLimitProfile()!= null){
							id = String.valueOf(limitProfileTrxVal.getLimitProfile().getLimitProfileID());
							bodyObj.setCamId(id);
							bodyObj.setResponseStatus("SUCCESS");
						    bodyRestList.add(bodyObj);
							responseMessageDetailsDTO.setResponseCode("CAM0000");
							responseMessageDetailsDTO.setResponseMessage("CAM_CREATED_SUCCESSFULLY");
						    responseMessageDetailDTOList.add(responseMessageDetailsDTO);
						    bodyObj.setResponseMessageList(responseMessageDetailDTOList);
							camResponse.setBodyDetails(bodyRestList);						
							
							DefaultLogger.debug(this, "Updating Sanctioned Amount Flag from addCamDetailsRest for partyId :"+partyId );
							CustomerDAOFactory.getDAO().updateSanctionedAmountUpdatedFlag(partyId, ICMSConstant.YES);
							ICMSCustomer cust = CustomerProxyFactory.getProxy().getCustomerByCIFSource(partyId,null);
							try {
								ClimesToECBFHelper.sendRequest(cust);
							} catch (Exception e) {
								e.printStackTrace();
								DefaultLogger.error(this, "Exception caught inside sendRequest while sending data to ecbf party webservice with error: " + e.getMessage(), e);
							}
						}
						else{
							DefaultLogger.error(this, "no value found in limitProfileTrxVal.getLimitProfileID(): ");
							throw new CMSException("Server side error");
						}
					}
					else{
						DefaultLogger.error(this, "no value found in limitProfileTrxVal: "+limitProfileTrxVal);
						throw new CMSException("Server side error");
					}
				}else{
					DefaultLogger.error(this, "no value found in trxResult: "+trxResult);
					throw new CMSException("Server side error");
				}

			} catch (LimitException e) {	
				if ((e.getErrorCode() != null) && e.getErrorCode().equals(LimitException.ERR_DUPLICATE_AA_NUM)) {
				    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
				    rmd.setResponseCode("CAM0002");
				    rmd.setResponseMessage("Duplicate CAM Number");
				    responseMessageDetailDTOList.add(rmd);
					bodyObj.setCamId("");
					bodyObj.setResponseStatus("FAILED");
					bodyObj.setResponseMessageList(responseMessageDetailDTOList);
					bodyRestList.add(bodyObj);
					camResponse.setBodyDetails(bodyRestList);
				}else{
				    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
				    rmd.setResponseCode("CAM0002");
				    rmd.setResponseMessage("LIMIT_EXCEPTION");
				    responseMessageDetailDTOList.add(rmd);
					bodyObj.setCamId("");
					bodyObj.setResponseStatus("FAILED");
					bodyObj.setResponseMessageList(responseMessageDetailDTOList);
					bodyRestList.add(bodyObj);
					camResponse.setBodyDetails(bodyRestList);
				}
			}
		}else {
			bodyObj.setCamId("");
			bodyObj.setResponseStatus("FAILED");
			bodyObj.setResponseMessageList(responseMessageDetailDTOList);
			bodyRestList.add(bodyObj);
			camResponse.setBodyDetails(bodyRestList);	
		}
		return camResponse;	
	}
	
	public CommonRestResponseDTO  updateCamDetailsRest(CamBodyRestRequestDTO camBodyRestRequestDTO) {

		CommonRestResponseDTO camResponse = new CommonRestResponseDTO();
		List<BodyRestResponseDTO> bodyRestList = new LinkedList<BodyRestResponseDTO>();
		BodyRestResponseDTO bodyObj= new  BodyRestResponseDTO();
		HeaderDetailRestResponseDTO headerObj= new  HeaderDetailRestResponseDTO();
		List<HeaderDetailRestResponseDTO> ccHeaderResponse = new LinkedList<HeaderDetailRestResponseDTO>();

		List<ResponseMessageDetailDTO> responseMessageDetailDTOList = new ArrayList<ResponseMessageDetailDTO>();
	    ResponseMessageDetailDTO responseMessageDetailsDTO = new ResponseMessageDetailDTO();
		List<ValidationErrorDetailsDTO> validationErrorDetailsDTOList;

		headerObj.setRequestId(camBodyRestRequestDTO.getHeaderDetails().get(0).getRequestId());
		headerObj.setChannelCode(camBodyRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
		ccHeaderResponse.add(headerObj);
		camResponse.setHeaderDetails(ccHeaderResponse);

		DefaultLogger.debug(this, "inside updateCamDetailsRest");
		CamDetailsRestRequestDTO camRequest = camBodyRestRequestDTO.getBodyDetails().get(0);
		camRequest.setEvent("REST_CAM_UPDATE");
		String partyId = "";
		String status = "";
		if(camRequest.getPartyId()!=null && !camRequest.getPartyId().trim().isEmpty()){
			partyId = camRequest.getPartyId().trim();
			status = getLimitJdbc().getPartyStatus(partyId);
		}
		
		if(camRequest.getPartyId()==null || camRequest.getPartyId().trim().isEmpty()){
		    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
		    rmd.setResponseCode("CAM0001");
		    rmd.setResponseMessage("PartyId is mandatory");
		    responseMessageDetailDTOList.add(rmd);
		}
		else if(status!=null && status.equals("INACTIVE")){
		    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
		    rmd.setResponseCode("CAM0002");
		    rmd.setResponseMessage("Party is inactive in system");
		    responseMessageDetailDTOList.add(rmd);
		}
		else if(status!=null && status.equals("NOTEXIST")){
		    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
		    rmd.setResponseCode("CAM0002");
		    rmd.setResponseMessage("Party does not exists in system");
		    responseMessageDetailDTOList.add(rmd);
		}		

		if(responseMessageDetailDTOList.size() > 0) {
			bodyObj.setCamId("");
			bodyObj.setResponseStatus("FAILED");
		    bodyObj.setResponseMessageList(responseMessageDetailDTOList);
		    bodyRestList.add(bodyObj);
		    camResponse.setBodyDetails(bodyRestList);	
			return camResponse;
		}
		
		String camId = getLimitJdbc().getCamByLlpLeId(partyId);
		CamDetailsRestRequestDTO CamDetailsRestRequestDTO = camDetailsRestDTOMapper.getRequestDTOWithActualValues(camRequest,camId);
		ActionErrors requestErrors = CamDetailsRestRequestDTO.getErrors();		
		HashMap map = new HashMap();
		if(requestErrors.size()>0){
			map.put("1", requestErrors);
			validationErrorDetailsDTOList = ValidationUtilityRest.handleError(map, CLIMSWebService.CAM);
			for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
			    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
			    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
			    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
			    responseMessageDetailDTOList.add(rmd);
			}
		}
		AADetailForm form = camDetailsRestDTOMapper.getFormFromDTO(camRequest);
		ActionErrors errorList = CamDetailsRestValidator.validateInput(form, Locale.getDefault()); 
		HashMap newMap = new HashMap();		
		if(errorList.size()>0){
			newMap.put("1", errorList);
			validationErrorDetailsDTOList = ValidationUtilityRest.handleError(newMap,CLIMSWebService.CAM);
			for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
			    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
			    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
			    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
			    responseMessageDetailDTOList.add(rmd);
			}
		}
		

		if (responseMessageDetailDTOList.isEmpty()) {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			OBTrxContext trxContext = new OBTrxContext();
			ILimitProfileTrxValue limitProfileTrxVal =null;
			OBLimitProfile actualLimitProfile = null;
			ICMSTrxResult trxResult = null;
			
			try {
				if(camId!=null && !camId.equals("")){
					limitProfileTrxVal = proxy.getTrxLimitProfile(Long.parseLong(camId));
					if(limitProfileTrxVal!=null && ((limitProfileTrxVal.getStatus().equals("PENDING_CREATE"))
							||(limitProfileTrxVal.getStatus().equals("PENDING_UPDATE"))
							||(limitProfileTrxVal.getStatus().equals("PENDING_DELETE"))
							||(limitProfileTrxVal.getStatus().equals("REJECTED"))||(limitProfileTrxVal.getStatus().equals("DRAFT"))))
					{
					    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					    rmd.setResponseCode("CAM0002");
					    rmd.setResponseMessage("Unable to update due to invalid transaction Status");
					    responseMessageDetailDTOList.add(rmd);					    
						bodyObj.setCamId("");
						bodyObj.setResponseStatus("FAILED");
					    bodyObj.setResponseMessageList(responseMessageDetailDTOList);
					    bodyRestList.add(bodyObj);
					    camResponse.setBodyDetails(bodyRestList);	
						return camResponse;
					}
				}
				
				Date actualExpiryDate = null; 
				
				if(limitProfileTrxVal!=null){
					actualLimitProfile =(OBLimitProfile)limitProfileTrxVal.getLimitProfile();
					if(actualLimitProfile.getCamType()!=null && ("Interim".equalsIgnoreCase(actualLimitProfile.getCamType()) || "Annual".equalsIgnoreCase(actualLimitProfile.getCamType())) ){
						if(form.getCamType()!=null && form.getCamType().equals("Initial")){
						    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
						    rmd.setResponseCode("CAM0002");
						    rmd.setResponseMessage("camType is invalid");
						    responseMessageDetailDTOList.add(rmd);					    
							bodyObj.setCamId("");
							bodyObj.setResponseStatus("FAILED");
						    bodyObj.setResponseMessageList(responseMessageDetailDTOList);
						    bodyRestList.add(bodyObj);
						    camResponse.setBodyDetails(bodyRestList);	
							return camResponse;						
						}
					}
					if(limitProfileTrxVal.getLimitProfile() != null)
						actualExpiryDate = limitProfileTrxVal.getLimitProfile().getNextAnnualReviewDate();
						actualLimitProfile = (OBLimitProfile)camDetailsRestDTOMapper.getActualFromDTO(camRequest,actualLimitProfile);
				}
				
				if(actualLimitProfile != null) {
					Date newExpiryDate = actualLimitProfile.getNextAnnualReviewDate();
					if(!(actualExpiryDate.compareTo(newExpiryDate) == 0)) {
						DefaultLogger.debug(this, "Updating Sanctioned Amount Flag from updateCamDetailsRest for partyId :"+partyId );
						CustomerDAOFactory.getDAO().updateSanctionedAmountUpdatedFlag(partyId, ICMSConstant.YES);
					}
				}
				
				trxResult = proxy.submitCreateLimitProfile(trxContext, limitProfileTrxVal, actualLimitProfile);
				String id = "";
				if(trxResult != null && trxResult.getTrxValue() != null){
					limitProfileTrxVal = (ILimitProfileTrxValue)trxResult.getTrxValue();
					if(limitProfileTrxVal!= null){
						if(limitProfileTrxVal.getLimitProfile()!= null){
							id = String.valueOf(limitProfileTrxVal.getLimitProfile().getLimitProfileID());
							bodyObj.setCamId(id);
							bodyObj.setResponseStatus("SUCCESS");
							responseMessageDetailsDTO.setResponseCode("CAM0000");
							responseMessageDetailsDTO.setResponseMessage("CAM_UPDATED_SUCCESSFULLY");
						    responseMessageDetailDTOList.add(responseMessageDetailsDTO);
						    bodyObj.setResponseMessageList(responseMessageDetailDTOList);
						    bodyRestList.add(bodyObj);
						    camResponse.setBodyDetails(bodyRestList);																				
							ICMSCustomer cust = CustomerProxyFactory.getProxy().getCustomerByCIFSource(partyId,null);
							try {
								ClimesToECBFHelper.sendRequest(cust);
							} catch (Exception e) {
								e.printStackTrace();
								DefaultLogger.error(this, "Exception caught inside sendRequest while sending data to ecbf party webservice with error: " + e.getMessage(), e);
							}							
						}
						else{
							DefaultLogger.error(this, "updateCamDetailsRest - no value found in limitProfileTrxVal.getLimitProfileID(): ");
							throw new CMSException("Server side error");
						}
					}
					else{
						DefaultLogger.error(this, "updateCamDetailsRest - no value found in limitProfileTrxVal: "+limitProfileTrxVal);
						throw new CMSException("Server side error");
					}
				}else{
					DefaultLogger.error(this, "updateCamDetailsRest - no value found in trxResult: "+trxResult);
					throw new CMSException("Server side error");
				}
				
			} catch (LimitException e) {	
				if ((e.getErrorCode() != null) && e.getErrorCode().equals(LimitException.ERR_DUPLICATE_AA_NUM)) {
				    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
				    rmd.setResponseCode("CAM0002");
				    rmd.setResponseMessage("Duplicate CAM Number");
				    responseMessageDetailDTOList.add(rmd);
					bodyObj.setCamId("");
					bodyObj.setResponseStatus("FAILED");
					bodyObj.setResponseMessageList(responseMessageDetailDTOList);
					bodyRestList.add(bodyObj);
					camResponse.setBodyDetails(bodyRestList);
				}else{
				    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
				    rmd.setResponseCode("CAM0002");
				    rmd.setResponseMessage("LIMIT_EXCEPTION");
				    responseMessageDetailDTOList.add(rmd);
					bodyObj.setCamId("");
					bodyObj.setResponseStatus("FAILED");
					bodyObj.setResponseMessageList(responseMessageDetailDTOList);
					bodyRestList.add(bodyObj);
					camResponse.setBodyDetails(bodyRestList);
				}
			}
		}else {
			bodyObj.setCamId("");
			bodyObj.setResponseStatus("FAILED");
			bodyObj.setResponseMessageList(responseMessageDetailDTOList);
			bodyRestList.add(bodyObj);
			camResponse.setBodyDetails(bodyRestList);	
		}
		return camResponse;		
	}
}
