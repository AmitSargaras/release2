package com.integrosys.cms.app.ws.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.cms.app.ws.dto.WMSRequestDTO;
import com.integrosys.cms.app.ws.dto.WMSResponseDTO;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

@WebService(serviceName = "WMSServiceEndPoint", portName = "WMSServiceEndPointPort", targetNamespace="http://www.aurionprosolutions.com/WMS/")
public interface IWMSServiceEndPoint {

	@WebMethod(operationName="updateWMSDetails")
	WMSResponseDTO  updateWMSDetails(@WebParam (name = "WMSRequest") WMSRequestDTO  wmsRequest) throws CMSValidationFault, CMSFault;

}
