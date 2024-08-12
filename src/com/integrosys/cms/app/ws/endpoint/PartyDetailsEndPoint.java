package com.integrosys.cms.app.ws.endpoint;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.ws.dto.PartyDetailsRequestDTO;
import com.integrosys.cms.app.ws.dto.PartyDetailsResponseDTO;
import com.integrosys.cms.app.ws.facade.PartyDetailsFacade;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

@WebService(serviceName = "PartyDetailsEndPoint", portName = "PartyDetailsEndPointPort",
	endpointInterface = "com.integrosys.cms.app.ws.endpoint.IPartyDetailsEndPoint", targetNamespace="http://www.aurionprosolutions.com/PARTY/")
@HandlerChain(file="handler-chain.xml")
public class PartyDetailsEndPoint implements IPartyDetailsEndPoint{
	
	private PartyDetailsFacade partyDetailsFacade;
	
	@WebMethod(operationName="savePartyDetails")
	public PartyDetailsResponseDTO  savePartyDetails(@WebParam (name = "partyDetRequestDTO") PartyDetailsRequestDTO partyDetRequestDTO) throws CMSValidationFault,CMSFault{
		
		DefaultLogger.info(this, "Party Details Web Service called!!! Method :: savePartyDetails() called");
		partyDetailsFacade = (PartyDetailsFacade) BeanHouse.get("partyDetailsFacade");
		partyDetRequestDTO.setEvent("WS_create_customer");
		PartyDetailsResponseDTO partyDetResponseDTO = partyDetailsFacade.savePartyDetails(partyDetRequestDTO);
		return partyDetResponseDTO;
		
	}
	
	@WebMethod(operationName="updatePartyDetails")
	public PartyDetailsResponseDTO  updatePartyDetails(@WebParam (name = "partyDetRequestDTO") PartyDetailsRequestDTO partyDetRequestDTO) throws CMSValidationFault,CMSFault{
		
		DefaultLogger.info(this, "Party Details Web Service called!!! Method :: updatePartyDetails() called ");
		partyDetailsFacade = (PartyDetailsFacade) BeanHouse.get("partyDetailsFacade");
		partyDetRequestDTO.setEvent("WS_update_customer");
		PartyDetailsResponseDTO partyDetResponseDTO = partyDetailsFacade.updatePartyDetails(partyDetRequestDTO);
		return partyDetResponseDTO;
		
	}
	
} 





