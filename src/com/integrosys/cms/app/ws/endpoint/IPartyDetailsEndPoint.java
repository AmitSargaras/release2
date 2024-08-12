/**
 * 
 */
package com.integrosys.cms.app.ws.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.cms.app.ws.dto.PartyDetailsRequestDTO;
import com.integrosys.cms.app.ws.dto.PartyDetailsResponseDTO;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

/**
 * @author bhushan.malankar
 *
 */
@WebService(serviceName = "PartyDetailsEndPoint", portName = "PartyDetailsEndPointPort", targetNamespace="http://www.aurionprosolutions.com/PARTY/")
public interface IPartyDetailsEndPoint {

	@WebMethod(operationName="savePartyDetails")
	PartyDetailsResponseDTO  savePartyDetails(@WebParam (name = "partyDetRequestDTO") PartyDetailsRequestDTO partyDetRequestDTO) throws CMSValidationFault,CMSFault;

	@WebMethod(operationName="updatePartyDetails")
	PartyDetailsResponseDTO  updatePartyDetails(@WebParam (name = "partyDetRequestDTO") PartyDetailsRequestDTO partyDetRequestDTO) throws CMSValidationFault,CMSFault;


}
