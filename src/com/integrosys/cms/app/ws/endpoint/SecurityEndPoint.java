package com.integrosys.cms.app.ws.endpoint;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.ws.dto.SecurityRequestDTO;
import com.integrosys.cms.app.ws.dto.SecurityResponseDTO;
import com.integrosys.cms.app.ws.facade.SecurityFacade;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

@WebService(serviceName = "SecurityEndPoint", portName = "SecurityEndPointPort",
	endpointInterface = "com.integrosys.cms.app.ws.endpoint.ISecurityEndPoint", targetNamespace="http://www.aurionprosolutions.com/SECURITY/")
@HandlerChain(file="handler-chain.xml")
public class SecurityEndPoint implements ISecurityEndPoint{
	
	private SecurityFacade securityFacade;
	
	@WebMethod(operationName="getSecurityDetails")
	public SecurityResponseDTO  getSecurityDetails(@WebParam (name = "securityRequestDTO") SecurityRequestDTO securityRequestDTO) throws CMSValidationFault,CMSFault{
		
		DefaultLogger.info(this, "Security Web Service called!!!");
		securityFacade = (SecurityFacade) BeanHouse.get("securityFacade");
		SecurityResponseDTO securityResponseDTO = securityFacade.getSecurityDetails(securityRequestDTO);
		return securityResponseDTO;
		
	}
} 





