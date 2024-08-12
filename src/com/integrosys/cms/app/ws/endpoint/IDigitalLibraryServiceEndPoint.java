package com.integrosys.cms.app.ws.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.cms.app.ws.dto.DigitalLibraryRequestDTO;
import com.integrosys.cms.app.ws.dto.DigitalLibraryResponseDTO;
import com.integrosys.cms.app.ws.dto.FacilitySCODResponseDTO;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

@WebService(serviceName = "DigitalLibraryServiceEndPoint", portName = "DigitalLibraryServiceEndPointPort", targetNamespace="http://www.aurionprosolutions.com/DIGITALLIBRARY/")
public interface IDigitalLibraryServiceEndPoint {

	@WebMethod(operationName="getDigitalLibraryDetails")
	DigitalLibraryResponseDTO  getDigitalLibraryDetails(@WebParam (name = "DigitalLibraryRequest") DigitalLibraryRequestDTO  digitalLibraryRequestDTO) throws CMSValidationFault, CMSFault;


}
