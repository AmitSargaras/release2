/**
 * 
 */
package com.aurionpro.clims.rest.service;

import com.aurionpro.clims.rest.dto.CommonRestResponseDTO;
import com.aurionpro.clims.rest.dto.FacilityBodyRestRequestDTO;
import com.aurionpro.clims.rest.dto.FacilityDetailRestResponseDTO;
import com.aurionpro.clims.rest.dto.FacilityDetlRestRequestDTO;
import com.google.gson.Gson;
import com.integrosys.cms.app.ws.dto.FacilityDetailRequestDTO;
import com.integrosys.cms.app.ws.facade.FacilityServiceFacade;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

/**
 * @author anil.pandey
 *
 */
public class FacilityDetailsRestService  {

	FacilityServiceFacade facilityDetailsFacade = new FacilityServiceFacade();
	
	public CommonRestResponseDTO updateFacilityDetailsRest(
			FacilityDetlRestRequestDTO facilityDetailsRequest, Gson gson) {
		// TODO Auto-generated method stub
		
		CommonRestResponseDTO commonCodeRestResponseDTO = new CommonRestResponseDTO();
		try {
			commonCodeRestResponseDTO = facilityDetailsFacade.updateFacilityDetailsRest(facilityDetailsRequest,gson);
			System.out.println("CREATE_FACILITY Response Recieved======" );
			//facilityDetailRestResponse.setFacilityId(facilityResponseDTO.getFacilityId());
			
			//System.out.println("CREATE_FACILITY Facility Response Recieved======" +facilityDetailRestResponse.getFacilityId());
				
				
				
			} catch (CMSValidationFault e) {
				System.out.println("updateFacilityDetailsRest" + e);
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CMSFault e) {
				// TODO Auto-generated catch block
				System.out.println("updateFacilityDetailsRest" + e);

				e.printStackTrace();
			}

		/*String event="WS_update_customer";
		
		facilityDetRequestDTO.setEvent(event);*/
		return commonCodeRestResponseDTO;
	}

	public CommonRestResponseDTO addFacilityDetailsRest(
			FacilityDetlRestRequestDTO facilityDetailsRequest, Gson gson) {
		// TODO Auto-generated method stub
		
		CommonRestResponseDTO commonCodeRestResponseDTO = new CommonRestResponseDTO();


		try {
			//	PartyDetailsResponseDTO partyDetResponseDTO = partyDetailsFacade.updatePartyDetailsRestApi(partyDetailsRequest);
			commonCodeRestResponseDTO = facilityDetailsFacade.addFacilityDetailsRest(facilityDetailsRequest,gson);
			System.out.println("CREATE_FACILITY Response Recieved======" );
			//facilityDetailRestResponse.setFacilityId(facilityResponseDTO.getFacilityId());
			
			//System.out.println("CREATE_FACILITY Facility Response Recieved======" +facilityDetailRestResponse.getFacilityId());
				
				
				
			} catch (CMSValidationFault e) {
				System.out.println("updateFacilityDetailsRest" + e);
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CMSFault e) {
				// TODO Auto-generated catch block
				System.out.println("updateFacilityDetailsRest" + e);

				e.printStackTrace();
			}

		/*String event="WS_update_customer";
		
		facilityDetRequestDTO.setEvent(event);*/
		return commonCodeRestResponseDTO;
	}
	 
	

}
