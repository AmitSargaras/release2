package com.integrosys.cms.app.ws.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.cms.app.ws.dto.CAMExtensionDateRequestDTO;
import com.integrosys.cms.app.ws.dto.CAMExtensionDateResponseDTO;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

@WebService(serviceName = "CAMExtensionDateServiceEndPoint", portName = "CAMExtensionDateServiceEndPointPort", targetNamespace="http://www.aurionprosolutions.com/CAMEXTDATE/")
public interface ICAMExtensionDateServiceEndPoint {

	@WebMethod(operationName="updateCAMExtensionDate")
	CAMExtensionDateResponseDTO  updateCAMExtensionDate(@WebParam (name = "CAMExtensionDateRequest") CAMExtensionDateRequestDTO  request) throws CMSValidationFault, CMSFault;
	
}