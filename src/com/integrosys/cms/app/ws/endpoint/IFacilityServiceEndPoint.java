/**
 * 
 */
package com.integrosys.cms.app.ws.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.integrosys.cms.app.ws.dto.AdhocFacilityRequestDTO;
import com.integrosys.cms.app.ws.dto.AdhocFacilityResponseDTO;
import com.integrosys.cms.app.ws.dto.FacilityDeleteRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityNewFieldsDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityReadRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityReadResponseDTO;
import com.integrosys.cms.app.ws.dto.FacilityResponseDTO;
import com.integrosys.cms.app.ws.dto.FacilitySCODDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilitySCODResponseDTO;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;

/**
 * @author bhushan.malankar
 *
 */
@WebService(serviceName = "FacilityServiceEndPoint", portName = "FacilityServiceEndPointPort", targetNamespace="http://www.aurionprosolutions.com/FACILITY/")
public interface IFacilityServiceEndPoint {

	@WebMethod(operationName="addFacilityDetails")
	FacilityResponseDTO  addFacilityDetails(@WebParam (name = "FacilityRequest") FacilityDetailRequestDTO  facilityRequest) throws CMSValidationFault, CMSFault;

	@WebMethod(operationName="updateFacilityDetails")
	FacilityResponseDTO  updateFacilityDetails(@WebParam (name = "FacilityRequest") FacilityDetailRequestDTO  facilityRequest) throws CMSValidationFault, CMSFault;

	@WebMethod(operationName="deleteFacilityDetails")
	FacilityResponseDTO  deleteFacilityDetails(
			@WebParam(name = "facilityDeleteRequestDTO") FacilityDeleteRequestDTO facilityDeleteRequestDTO ) throws CMSValidationFault, CMSFault;
	
	@WebMethod(operationName="readFacilityDetails")
	FacilityReadResponseDTO  readFacilityDetails(@WebParam(name = "facilityReadRequestDTO") FacilityReadRequestDTO facilityReadRequestDTO) throws CMSValidationFault, CMSFault;
	
	@WebMethod(operationName="updateSCODFacilityDetails")
	FacilitySCODResponseDTO  updateSCODFacilityDetails(@WebParam (name = "FacilitySCODRequest") FacilitySCODDetailRequestDTO  facilityRequest) throws CMSValidationFault, CMSFault;

	@WebMethod(operationName="updateNewFacilityDetails")
	FacilityResponseDTO  updateNewFacilityDetails(@WebParam(name = "FacilityRequest") FacilityNewFieldsDetailRequestDTO  facilityRequest) throws CMSValidationFault, CMSFault;

	@WebMethod(operationName="updateAdhocFacility")
	AdhocFacilityResponseDTO  updateAdhocFacility(@WebParam (name = "FacilityAdhocRequest") AdhocFacilityRequestDTO  facilityRequest) throws CMSValidationFault, CMSFault;

}
