/**
 * 
 */
package com.integrosys.cms.app.ws.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.cms.app.ws.dto.DiscrepancyReadRequestDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyReadResponseDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyRequestDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyResponseDTO;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

/**
 * @author bhushan.malankar
 *
 */
@WebService(serviceName = "DiscrepancyServiceEndPoint", portName = "DiscrepancyServiceEndPointPort", targetNamespace="http://www.aurionprosolutions.com/DISCREPANCY/")
public interface IDiscrepancyServiceEndPoint {

	@WebMethod(operationName="addDiscrepancyDetails")
	DiscrepancyResponseDTO  addDiscrepancyDetails(@WebParam (name = "DiscrepancyRequest") DiscrepancyRequestDTO  DiscrepancyRequest) throws CMSValidationFault, CMSFault;

	@WebMethod(operationName="readDiscrepancyDetails")
	DiscrepancyReadResponseDTO  readDiscrepancyDetails(
			@WebParam(name = "DiscrepancyReadRequest")DiscrepancyReadRequestDTO readRequestDTO) throws CMSValidationFault, CMSFault;

    

}
