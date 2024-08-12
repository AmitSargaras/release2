package com.integrosys.cms.app.ws.endpoint;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.ws.dto.ECBFLimitRequestDTO;
import com.integrosys.cms.app.ws.dto.ECBFLimitResponseDTO;
import com.integrosys.cms.app.ws.facade.ECBFLimitServiceFacade;

@WebService(serviceName = "ECBFLimitServiceEndPoint", portName = "ECBFLimitServiceEndPointPort", 
endpointInterface = "com.integrosys.cms.app.ws.endpoint.IECBFLimitServiceEndPoint", targetNamespace="http://www.aurionprosolutions.com/ECBFLIMIT/")
@HandlerChain(file = "handler-chain.xml")
public class ECBFLimitServiceEndPoint implements IECBFLimitServiceEndPoint{

	@Override
	@WebMethod(operationName = "syncECBFLimitDetails")
	public ECBFLimitResponseDTO syncECBFLimitDetails(
			@WebParam(name = "ECBFLimitRequest") ECBFLimitRequestDTO request) {
		
		ECBFLimitServiceFacade service = (ECBFLimitServiceFacade) BeanHouse.get("ecbfLimitServiceFacade");

		ECBFLimitResponseDTO responseDto = service.syncECBFLimitDetails(request);
		
		return responseDto;
	}
	
}