/**
 * 
 */
package com.integrosys.cms.app.ws.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.cms.app.ws.dto.ISACRequestDTO;
import com.integrosys.cms.app.ws.dto.ISACResponseDTO;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

/**
 * @author bhushan.chaudhari
 *
 */
@WebService(serviceName = "ISACServiceEndPoint", portName = "ISACServiceEndPointPort", targetNamespace="http://www.aurionprosolutions.com/ISACUSER/")
public interface IISACServiceEndPoint {

	@WebMethod(operationName="AddIsacUser")
	ISACResponseDTO  AddIsacUser(@WebParam (name = "ISACRequest") ISACRequestDTO  ISACRequest) throws CMSValidationFault, CMSFault;

	
	@WebMethod(operationName="UpdateIsacUser")
	ISACResponseDTO  UpdateIsacUser(@WebParam (name = "ISACRequest") ISACRequestDTO  ISACRequest) throws CMSValidationFault, CMSFault;

}
