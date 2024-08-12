package com.integrosys.cms.app.ws.endpoint;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.ws.dto.AdhocFacilityRequestDTO;
import com.integrosys.cms.app.ws.dto.AdhocFacilityResponseDTO;
import com.integrosys.cms.app.ws.dto.FacilityDeleteRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityNewFieldsDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityReadRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityReadResponseDTO;
import com.integrosys.cms.app.ws.dto.FacilityResponseDTO;
import com.integrosys.cms.app.ws.dto.FacilitySCODDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilitySCODResponseDTO;
import com.integrosys.cms.app.ws.facade.FacilityServiceFacade;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

@WebService(serviceName = "FacilityServiceEndPoint", portName = "FacilityServiceEndPointPort", 
			endpointInterface = "com.integrosys.cms.app.ws.endpoint.IFacilityServiceEndPoint", targetNamespace="http://www.aurionprosolutions.com/FACILITY/")
@HandlerChain(file = "handler-chain.xml")
public class FacilityServiceEndPoint implements IFacilityServiceEndPoint {

	private FacilityServiceFacade facilityServiceFacade;

	@WebMethod(operationName = "addFacilityDetails")
	public FacilityResponseDTO addFacilityDetails(
			@WebParam(name = "FacilityRequest") FacilityDetailRequestDTO requestDto)
			throws CMSValidationFault, CMSFault {
		facilityServiceFacade = (FacilityServiceFacade) BeanHouse.get("facilityServiceFacade");
		requestDto.setEvent("WS_FAC_CREATE");
		FacilityResponseDTO responseDto = facilityServiceFacade.addFacilityDetails(requestDto);
		return responseDto;
	}
	@WebMethod(operationName = "updateFacilityDetails")
	public FacilityResponseDTO updateFacilityDetails(
			@WebParam(name = "FacilityRequest") FacilityDetailRequestDTO requestDto)
			throws CMSValidationFault, CMSFault {
		facilityServiceFacade = (FacilityServiceFacade) BeanHouse.get("facilityServiceFacade");
		requestDto.setEvent("WS_FAC_EDIT");
		FacilityResponseDTO responseDto = facilityServiceFacade.updateFacilityDetails(requestDto);
		return responseDto;
	}
	@WebMethod(operationName = "updateNewFacilityDetails")
	public FacilityResponseDTO updateNewFacilityDetails(
			@WebParam(name = "FacilityRequest") FacilityNewFieldsDetailRequestDTO requestDto)
			throws CMSValidationFault, CMSFault {
		facilityServiceFacade = (FacilityServiceFacade) BeanHouse.get("facilityServiceFacade");
		requestDto.setEvent("WS_NEW_FAC_EDIT");
		FacilityResponseDTO responseDto = facilityServiceFacade.updateNewFacilityDetails(requestDto);
		return responseDto;
	}
	@WebMethod(operationName = "deleteFacilityDetails")
	public FacilityResponseDTO deleteFacilityDetails(
			@WebParam(name = "facilityDeleteRequestDTO") FacilityDeleteRequestDTO facilityDeleteRequestDTO )
			throws CMSValidationFault, CMSFault {
		facilityServiceFacade = (FacilityServiceFacade) BeanHouse.get("facilityServiceFacade");
		FacilityResponseDTO responseDto = facilityServiceFacade.deleteFacilityDetails(facilityDeleteRequestDTO);
		return responseDto;
	}
	@WebMethod(operationName = "readFacilityDetails")
	public FacilityReadResponseDTO readFacilityDetails(
			@WebParam(name = "facilityReadRequestDTO") FacilityReadRequestDTO facilityReadRequestDTO )
			throws CMSValidationFault, CMSFault {
		facilityServiceFacade = (FacilityServiceFacade) BeanHouse.get("facilityServiceFacade");
		FacilityReadResponseDTO responseDto = facilityServiceFacade.readFacilityDetails(facilityReadRequestDTO);
		return responseDto;
	}
	@WebMethod(operationName = "updateSCODFacilityDetails")
	public FacilitySCODResponseDTO updateSCODFacilityDetails(
			@WebParam(name = "FacilitySCODRequest") FacilitySCODDetailRequestDTO requestDto) throws CMSValidationFault, CMSFault {
		facilityServiceFacade = (FacilityServiceFacade) BeanHouse.get("facilityServiceFacade");
		requestDto.setEvent("WS_SCOD_FAC_EDIT");
		FacilitySCODResponseDTO responseDto = facilityServiceFacade.updateSCODFacilityDetails(requestDto);
		return responseDto;
	}
	
	@WebMethod(operationName = "updateAdhocFacility")
	public AdhocFacilityResponseDTO updateAdhocFacility(
			@WebParam(name = "FacilityAdhocRequest") AdhocFacilityRequestDTO requestDto) throws CMSValidationFault, CMSFault {
		facilityServiceFacade = (FacilityServiceFacade) BeanHouse.get("facilityServiceFacade");
		requestDto.setEvent("WS_ADHOC_FAC_EDIT");
		AdhocFacilityResponseDTO responseDto = facilityServiceFacade.updateAdhocFacility(requestDto);
		return responseDto;
	}

}
