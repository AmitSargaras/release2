/**
 * 
 */
package com.integrosys.cms.app.ws.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.cms.app.ws.dto.SecurityRequestDTO;
import com.integrosys.cms.app.ws.dto.SecurityResponseDTO;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

/**
 * @author Ankit
 *
 */
@WebService(serviceName = "SecurityEndPoint", portName = "SecurityEndPointPort", targetNamespace="http://www.aurionprosolutions.com/SECURITY/")
public interface ISecurityEndPoint {

	@WebMethod(operationName="getSecurityDetails")
	SecurityResponseDTO  getSecurityDetails(@WebParam (name = "securityRequestDTO") SecurityRequestDTO securityRequestDTO) throws CMSValidationFault,CMSFault;
}
