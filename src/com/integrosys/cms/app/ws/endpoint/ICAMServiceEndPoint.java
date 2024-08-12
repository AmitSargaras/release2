/**
 * 
 */
package com.integrosys.cms.app.ws.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.cms.app.ws.dto.AADetailRequestDTO;
import com.integrosys.cms.app.ws.dto.AADetailResponseDTO;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

/**
 * @author bhushan.malankar
 *
 */
@WebService(serviceName = "CAMServiceEndPoint", portName = "CAMServiceEndPointPort", targetNamespace="http://www.aurionprosolutions.com/CAM/")
public interface ICAMServiceEndPoint {

	@WebMethod(operationName="addCAMDetails")
	AADetailResponseDTO  addCAMDetails(@WebParam (name = "CAMRequest") AADetailRequestDTO  CAMRequest) throws CMSValidationFault, CMSFault;
	
	@WebMethod(operationName="updateCAMDetails")
	AADetailResponseDTO  updateCAMDetails(@WebParam (name = "CAMRequest") AADetailRequestDTO  CAMRequest) throws CMSValidationFault, CMSFault;
}
