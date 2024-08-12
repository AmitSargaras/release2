package com.integrosys.cms.app.ws.endpoint;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.ws.dto.DiscrepancyReadRequestDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyReadResponseDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyRequestDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyResponseDTO;
import com.integrosys.cms.app.ws.facade.DiscrepancyServiceFacade;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

@WebService(serviceName = "DiscrepancyServiceEndPoint", portName = "DiscrepancyServiceEndPointPort", 
			endpointInterface = "com.integrosys.cms.app.ws.endpoint.IDiscrepancyServiceEndPoint", targetNamespace="http://www.aurionprosolutions.com/DISCREPANCY/")
@HandlerChain(file = "handler-chain.xml")
public class DiscrepancyServiceEndPoint implements IDiscrepancyServiceEndPoint {

	private DiscrepancyServiceFacade discrepancyServiceFacade;

	@WebMethod(operationName = "addDiscrepancyDetails")
	public DiscrepancyResponseDTO addDiscrepancyDetails(
			@WebParam(name = "DiscrepancyRequest")DiscrepancyRequestDTO requestDto)
			throws CMSValidationFault, CMSFault {
		discrepancyServiceFacade = (DiscrepancyServiceFacade) BeanHouse.get("discrepancyServiceFacade");
		DiscrepancyResponseDTO responseDto = discrepancyServiceFacade.addDiscrepancyDetails(requestDto);
		return responseDto;
	}
	
	@WebMethod(operationName = "readDiscrepancyDetails")
	public DiscrepancyReadResponseDTO readDiscrepancyDetails(
			@WebParam(name = "DiscrepancyReadRequest")DiscrepancyReadRequestDTO readRequestDTO)
			throws CMSValidationFault, CMSFault {
		discrepancyServiceFacade = (DiscrepancyServiceFacade) BeanHouse.get("discrepancyServiceFacade");
		DiscrepancyReadResponseDTO responseDto = discrepancyServiceFacade.readDiscrepancyDetails(readRequestDTO);
		return responseDto;
	}

}
