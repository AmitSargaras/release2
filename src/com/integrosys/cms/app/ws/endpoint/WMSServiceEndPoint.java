package com.integrosys.cms.app.ws.endpoint;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.ws.dto.WMSRequestDTO;
import com.integrosys.cms.app.ws.dto.WMSResponseDTO;
import com.integrosys.cms.app.ws.facade.WMSServiceFacade;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

@WebService(serviceName = "WMSServiceEndPoint", portName = "WMSServiceEndPointPort", 
endpointInterface = "com.integrosys.cms.app.ws.endpoint.IWMSServiceEndPoint", targetNamespace="http://www.aurionprosolutions.com/WMS/")
@HandlerChain(file = "handler-chain.xml")
public class WMSServiceEndPoint implements IWMSServiceEndPoint{

	private WMSServiceFacade wmsServiceFacade;
	
	@Override
	@WebMethod(operationName = "updateWMSDetails")
	public WMSResponseDTO updateWMSDetails(@WebParam(name = "WMSRequest") WMSRequestDTO wmsRequestDTO) throws CMSValidationFault, CMSFault {
		wmsServiceFacade = (WMSServiceFacade) BeanHouse.get("wmsServiceFacade");
		wmsRequestDTO.setEvent("WMS_EDIT");
		WMSResponseDTO responseDto = wmsServiceFacade.updateWMSDetails(wmsRequestDTO);
		return responseDto;
	}

}
