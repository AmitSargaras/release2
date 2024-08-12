package com.integrosys.cms.app.ws.endpoint;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.ws.dto.ISACRequestDTO;
import com.integrosys.cms.app.ws.dto.ISACResponseDTO;
import com.integrosys.cms.app.ws.facade.ISACServiceFacade;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

@WebService(serviceName = "ISACServiceEndPoint", portName = "ISACServiceEndPointPort", 
			endpointInterface = "com.integrosys.cms.app.ws.endpoint.IISACServiceEndPoint", targetNamespace="http://www.aurionprosolutions.com/ISACUSER/")
@HandlerChain(file = "handler-chain.xml")
public class ISACServiceEndPoint implements IISACServiceEndPoint {

	private ISACServiceFacade isacServiceFacade;

	@SuppressWarnings("null")
	@WebMethod(operationName = "AddIsacUser")

	public ISACResponseDTO AddIsacUser(
			@WebParam(name = "ISACRequest") ISACRequestDTO requestDto)
			throws CMSValidationFault, CMSFault {
		ISACResponseDTO isacResDto = new ISACResponseDTO();
		isacServiceFacade = (ISACServiceFacade) BeanHouse.get("isacServiceFacade");
		if("A".equals(requestDto.getAction() )){
		requestDto.setEvent("WS_ISACUSER_CREATE");
		isacResDto = isacServiceFacade.AddIsacUser(requestDto);
	    }else {
	    	isacResDto.setErrorCode(isacResDto.getRefNumber());
	    	isacResDto.setErrorCode("1");
	    	isacResDto.setErrorMessage("Invalid Action for Add User");
	    	
	    }
		return isacResDto;
	}
	
	@WebMethod(operationName = "UpdateIsacUser")
	public ISACResponseDTO UpdateIsacUser(
			@WebParam(name = "ISACRequest") ISACRequestDTO requestDto)
			throws CMSValidationFault, CMSFault {
		ISACResponseDTO isacResDto= new ISACResponseDTO();
		isacServiceFacade = (ISACServiceFacade) BeanHouse.get("isacServiceFacade");
		if("U".equals(requestDto.getAction() )){
		requestDto.setEvent("WS_ISACUSER_UPDATE");
		isacResDto = isacServiceFacade.UpdateIsacUser(requestDto);
		 }else {
			 isacResDto.setErrorCode(requestDto.getRefNumber());
			 isacResDto.setErrorCode("1");
			 isacResDto.setErrorMessage("Invalid Action for Update User");
		    }
		return isacResDto;
	}
	

}
