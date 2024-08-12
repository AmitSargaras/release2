package com.integrosys.cms.app.ws.endpoint;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebService;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.ws.dto.CAMExtensionDateRequestDTO;
import com.integrosys.cms.app.ws.dto.CAMExtensionDateResponseDTO;
import com.integrosys.cms.app.ws.facade.CAMExtensionDateServiceFacade;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

@WebService(serviceName = "CAMExtensionDateServiceEndPoint", portName = "CAMExtensionDateServiceEndPointPort", 
endpointInterface = "com.integrosys.cms.app.ws.endpoint.ICAMExtensionDateServiceEndPoint", targetNamespace="http://www.aurionprosolutions.com/CAMEXTDATE/")
@HandlerChain(file = "handler-chain.xml")
public class CAMExtensionDateServiceEndPoint implements ICAMExtensionDateServiceEndPoint {

	@WebMethod(operationName = "updateCAMExtensionDate")
	public CAMExtensionDateResponseDTO updateCAMExtensionDate(
			@WebParam(name = "CAMExtensionDateRequest" ,mode = Mode.IN) CAMExtensionDateRequestDTO request)
			throws CMSValidationFault, CMSFault {
		
		CAMExtensionDateServiceFacade facade = (CAMExtensionDateServiceFacade) BeanHouse.get("camExtensionDateServiceFacade");
		
		CAMExtensionDateResponseDTO response = facade.updateCAMExtensionDate(request);
		
		return response;
	}

}