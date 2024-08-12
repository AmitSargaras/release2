/**
 * 
 */
package com.integrosys.cms.app.ws.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.cms.app.ws.dto.DocumentsReadRequestDTO;
import com.integrosys.cms.app.ws.dto.DocumentsReadResponseDTO;
import com.integrosys.cms.app.ws.dto.DocumentsRequestDTO;
import com.integrosys.cms.app.ws.dto.DocumentsResponseDTO;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

/**
 * @author bhushan.malankar
 *
 */
@WebService(serviceName = "DocumentsServiceEndPoint", portName = "DocumentsServiceEndPointPort", targetNamespace="http://www.aurionprosolutions.com/DOCUMENTS/")
public interface IDocumentsServiceEndPoint {

	@WebMethod(operationName="addDocumentsDetails")
	DocumentsResponseDTO  addDocumentsDetails(@WebParam (name = "DocumentsRequest") DocumentsRequestDTO  DocumentsRequest) throws CMSValidationFault, CMSFault;

	@WebMethod(operationName="readDocumentsDetails")
	DocumentsReadResponseDTO  readDocumentsDetails(@WebParam (name = "documentsReadRequest") DocumentsReadRequestDTO documentsReadRequestDTO ) throws CMSValidationFault, CMSFault;

    

}
