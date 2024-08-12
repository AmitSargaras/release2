package com.integrosys.cms.app.ws.endpoint;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.ws.dto.DocumentsReadRequestDTO;
import com.integrosys.cms.app.ws.dto.DocumentsReadResponseDTO;
import com.integrosys.cms.app.ws.dto.DocumentsRequestDTO;
import com.integrosys.cms.app.ws.dto.DocumentsResponseDTO;
import com.integrosys.cms.app.ws.facade.DocumentsServiceFacade;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

@WebService(serviceName = "DocumentsServiceEndPoint", portName = "DocumentsServiceEndPointPort", 
			endpointInterface = "com.integrosys.cms.app.ws.endpoint.IDocumentsServiceEndPoint", targetNamespace="http://www.aurionprosolutions.com/DOCUMENTS/")
@HandlerChain(file = "handler-chain.xml")
public class DocumentsServiceEndPoint implements IDocumentsServiceEndPoint {

	private DocumentsServiceFacade documentServiceFacade;

	@WebMethod(operationName = "addDocumentsDetails")
	public DocumentsResponseDTO addDocumentsDetails(
			@WebParam(name = "DocumentsRequest")DocumentsRequestDTO requestDto)
			throws CMSValidationFault, CMSFault {
		documentServiceFacade = (DocumentsServiceFacade) BeanHouse.get("documentServiceFacade");
		DocumentsResponseDTO responseDto = documentServiceFacade.addDocumentsDetails(requestDto);
		return responseDto;
	}
	
	@WebMethod(operationName = "readDocumentsDetails")
	public DocumentsReadResponseDTO readDocumentsDetails(
			@WebParam (name = "DocumentsReadRequest") DocumentsReadRequestDTO documentsReadRequestDTO)
			throws CMSValidationFault, CMSFault {
		documentServiceFacade = (DocumentsServiceFacade) BeanHouse.get("documentServiceFacade");
		DocumentsReadResponseDTO documentReadResponseDTO = documentServiceFacade.readDocumentsDetails(documentsReadRequestDTO);
		return documentReadResponseDTO;
	}

}
