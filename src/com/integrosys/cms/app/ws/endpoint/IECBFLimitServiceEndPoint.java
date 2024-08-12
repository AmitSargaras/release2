package com.integrosys.cms.app.ws.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.cms.app.ws.dto.ECBFLimitRequestDTO;
import com.integrosys.cms.app.ws.dto.ECBFLimitResponseDTO;


@WebService(serviceName = "ECBFLimitServiceEndPoint", portName = "ECBFLimitServiceEndPointPort", targetNamespace="http://www.aurionprosolutions.com/ECBFLIMIT/")
public interface IECBFLimitServiceEndPoint {

	@WebMethod(operationName="syncECBFLimitDetails")
	ECBFLimitResponseDTO  syncECBFLimitDetails(@WebParam (name = "ECBFLimitRequest") ECBFLimitRequestDTO  ecbfLimitRequest);
	
}