package com.aurionpro.clims.rest.service;

import java.util.LinkedList;
import java.util.List;

import com.aurionpro.clims.rest.dto.CommonRestResponseDTO;
import com.aurionpro.clims.rest.dto.HeaderDetailRestResponseDTO;
import com.aurionpro.clims.rest.dto.PartyDetailsRestRequestDTO;
import com.google.gson.Gson;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

public class PartyDetailsRestService {

	PartyDetailsRestFacade partyDetailsFacade = new PartyDetailsRestFacade();

	public CommonRestResponseDTO createPartyDetailsRest(PartyDetailsRestRequestDTO requestDto, Gson gson) {
		String event = "Rest_create_customer";

		CommonRestResponseDTO obj = new CommonRestResponseDTO();
		HeaderDetailRestResponseDTO headerObj = new HeaderDetailRestResponseDTO();
		List<HeaderDetailRestResponseDTO> ccHeaderResponse = new LinkedList<HeaderDetailRestResponseDTO>();

		requestDto.getBodyDetails().get(0).setEvent(event);

		try {
			obj = partyDetailsFacade.createPartyDetailsRestApi(requestDto);

			if (null != requestDto.getHeaderDetails().get(0).getRequestId()
					&& !requestDto.getHeaderDetails().get(0).getRequestId().isEmpty()) {
				headerObj.setRequestId(requestDto.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(requestDto.getHeaderDetails().get(0).getChannelCode());
				ccHeaderResponse.add(headerObj);
				obj.setHeaderDetails(ccHeaderResponse);
			}

		} catch (CMSValidationFault e) {
			e.printStackTrace();
		} catch (CMSFault e) {
			e.printStackTrace();
		}

		return obj;
	}

	public CommonRestResponseDTO updatePartyDetailsRest(PartyDetailsRestRequestDTO requestDto, Gson gson) {
		String event = "Rest_update_customer";

		CommonRestResponseDTO obj = new CommonRestResponseDTO();
		HeaderDetailRestResponseDTO headerObj = new HeaderDetailRestResponseDTO();
		List<HeaderDetailRestResponseDTO> ccHeaderResponse = new LinkedList<HeaderDetailRestResponseDTO>();

		requestDto.getBodyDetails().get(0).setEvent(event);

		try {
			CommonRestResponseDTO partyDetResponseDTO = partyDetailsFacade.updatePartyDetailsRestApi(requestDto);
			headerObj.setRequestId(requestDto.getHeaderDetails().get(0).getRequestId());
			headerObj.setChannelCode(requestDto.getHeaderDetails().get(0).getChannelCode());
			ccHeaderResponse.add(headerObj);

			obj.setHeaderDetails(ccHeaderResponse);

			obj.setBodyDetails(partyDetResponseDTO.getBodyDetails());

		} catch (CMSValidationFault e) {
			e.printStackTrace();
		} catch (CMSFault e) {
			e.printStackTrace();
		}

		return obj;
	}

}
