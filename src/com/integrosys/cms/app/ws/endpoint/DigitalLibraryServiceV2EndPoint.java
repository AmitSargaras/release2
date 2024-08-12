package com.integrosys.cms.app.ws.endpoint;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.ws.dto.DigitalLibraryRequestDTO;
import com.integrosys.cms.app.ws.dto.DigitalLibraryResponseDTO;
import com.integrosys.cms.app.ws.facade.DigitalLibraryServiceFacade;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Webservice DigitalLibraryServiceV2EndPoint
 */
@WebService(serviceName = "DigitalLibraryServiceV2EndPoint", portName = "DigitalLibraryServiceV2EndPointPort", endpointInterface = "com.integrosys.cms.app.ws.endpoint.IDigitalLibraryServiceV2EndPoint", targetNamespace = "http://www.aurionprosolutions.com/DIGITALLIBRARYV2/")
@HandlerChain(file = "handler-chain.xml")
public class DigitalLibraryServiceV2EndPoint
		implements
			IDigitalLibraryServiceV2EndPoint {

	@WebMethod(operationName = "getDigitalLibraryDetails")
	public DigitalLibraryResponseDTO getDigitalLibraryDetailsForV2(
			@WebParam(name = "DigitalLibraryRequest") DigitalLibraryRequestDTO requestDto)
			throws CMSValidationFault, CMSFault {
		DigitalLibraryServiceFacade digitalLibraryServiceFacade;
		digitalLibraryServiceFacade = (DigitalLibraryServiceFacade) BeanHouse
				.get("digitalLibraryServiceFacade");
		System.out.println(
				"Going for inside digitalLibraryServiceFacade.getDigitalLibraryDetails=>Step 1");
		DigitalLibraryResponseDTO responseDto = digitalLibraryServiceFacade
				.getDigitalLibraryDetailsForV2(requestDto);
		System.out.println(
				"Returns from  digitalLibraryServiceFacade.getDigitalLibraryDetails=>Step last");
		return responseDto;
	}

}
