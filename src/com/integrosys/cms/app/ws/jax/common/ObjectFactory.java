package com.integrosys.cms.app.ws.jax.common;

import javax.xml.bind.annotation.XmlRegistry;

import com.integrosys.cms.app.ws.dto.AADetailRequestDTO;
import com.integrosys.cms.app.ws.dto.AADetailResponseDTO;
import com.integrosys.cms.app.ws.dto.AdhocFacilityRequestDTO;
import com.integrosys.cms.app.ws.dto.AdhocFacilityResponseDTO;
import com.integrosys.cms.app.ws.dto.CAMExtensionDateRequestDTO;
import com.integrosys.cms.app.ws.dto.CAMExtensionDateResponseDTO;
import com.integrosys.cms.app.ws.dto.DigitalLibraryRequestDTO;
import com.integrosys.cms.app.ws.dto.DigitalLibraryResponseDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyReadDetailResponseDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyReadRequestDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyReadResponseDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyRequestDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyResponseDTO;
import com.integrosys.cms.app.ws.dto.DocumentsDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.DocumentsReadDetailResponseDTO;
import com.integrosys.cms.app.ws.dto.DocumentsReadRequestDTO;
import com.integrosys.cms.app.ws.dto.DocumentsReadResponseDTO;
import com.integrosys.cms.app.ws.dto.DocumentsRequestDTO;
import com.integrosys.cms.app.ws.dto.DocumentsResponseDTO;
import com.integrosys.cms.app.ws.dto.ECBFLimitRequestDTO;
import com.integrosys.cms.app.ws.dto.ECBFLimitResponseDTO;
import com.integrosys.cms.app.ws.dto.FacilityDeleteRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityReadRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityReadResponseDTO;
import com.integrosys.cms.app.ws.dto.FacilityResponseDTO;
import com.integrosys.cms.app.ws.dto.FacilitySCODDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilitySCODResponseDTO;
import com.integrosys.cms.app.ws.dto.ISACRequestDTO;
import com.integrosys.cms.app.ws.dto.ISACResponseDTO;
import com.integrosys.cms.app.ws.dto.PartyDetailsRequestDTO;
import com.integrosys.cms.app.ws.dto.PartyDetailsResponseDTO;
import com.integrosys.cms.app.ws.dto.SecurityRequestDTO;
import com.integrosys.cms.app.ws.dto.SecurityResponseDTO;
import com.integrosys.cms.app.ws.dto.WMSRequestDTO;
import com.integrosys.cms.app.ws.dto.WMSResponseDTO;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the com.aurionpro.cashpro.corporate.ws.token.hardtoken package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: com.aurionpro.cashpro.corporate.ws.token.hardtoken
	 * 
	 */
	public ObjectFactory() {
	}

	public AADetailRequestDTO createAADetailRequestDTO() {

		return new AADetailRequestDTO();
	}

	public AADetailResponseDTO createAADetailResponseDTO() {

		return new AADetailResponseDTO();
	}

	public PartyDetailsRequestDTO createPartyDetailsRequestDTO() {

		return new PartyDetailsRequestDTO();
	}

	public PartyDetailsResponseDTO createPartyDetailsResponseDTO() {

		return new PartyDetailsResponseDTO();
	}

	public FacilityDetailRequestDTO createFacilityDetailRequestDTO() {

		return new FacilityDetailRequestDTO();
	}

	public FacilityResponseDTO createFacilityResponseDTO() {

		return new FacilityResponseDTO();
	}

	public DiscrepancyDetailRequestDTO createDiscrepancyDetailRequestDTO() {

		return new DiscrepancyDetailRequestDTO();
	}

	public SecurityRequestDTO createSecurityRequestDTO() {

		return new SecurityRequestDTO();
	}

	public SecurityResponseDTO createSecurityResponseDTO() {

		return new SecurityResponseDTO();
	}

	public DiscrepancyRequestDTO createDiscrepancyRequestDTO() {

		return new DiscrepancyRequestDTO();
	}

	public DiscrepancyResponseDTO createDiscrepancyResponseDTO() {

		return new DiscrepancyResponseDTO();
	}

	public DocumentsRequestDTO createDocumentsRequestDTO() {

		return new DocumentsRequestDTO();
	}

	public DocumentsResponseDTO createDocumentsResponseDTO() {

		return new DocumentsResponseDTO();
	}

	public DocumentsDetailRequestDTO createDocumentsDetailRequestDTO() {

		return new DocumentsDetailRequestDTO();
	}

	public DocumentsReadRequestDTO createDocumentsReadRequestDTO() {
		return new DocumentsReadRequestDTO();
	}

	public DocumentsReadResponseDTO createDocumentsReadResponseDTO() {
		return new DocumentsReadResponseDTO();
	}

	public DocumentsReadDetailResponseDTO createDocumentsReadDetailResponseDTO() {
		return new DocumentsReadDetailResponseDTO();
	}
	public DiscrepancyReadRequestDTO createDiscrepancyReadRequestDTO() {
		return new DiscrepancyReadRequestDTO();
	}
	public DiscrepancyReadDetailResponseDTO createDiscrepancyReadDetailResponseDTO() {
		return new DiscrepancyReadDetailResponseDTO();
	}
	public DiscrepancyReadResponseDTO createDiscrepancyReadResponseDTO() {
		return new DiscrepancyReadResponseDTO();
	}
	public FacilityReadRequestDTO createFacilityReadRequestDTO() {
		return new FacilityReadRequestDTO();
	}
	public FacilityReadResponseDTO createFacilityReadResponseDTO() {
		return new FacilityReadResponseDTO();
	}
	public FacilityDeleteRequestDTO createFacilityDeleteRequestDTO() {
		return new FacilityDeleteRequestDTO();
	}
	//For SCOD
	public FacilitySCODDetailRequestDTO createSCODFacilityDetailRequestDTO() {
		return new FacilitySCODDetailRequestDTO();
	}
	public FacilitySCODResponseDTO createSCODFacilityResponseDTO() {
		return new FacilitySCODResponseDTO();
	}
	
	//For ADHOC 
	public AdhocFacilityRequestDTO createAdhocFacilityDetailRequestDTO() {
		return new AdhocFacilityRequestDTO();
	}
	public AdhocFacilityResponseDTO createAdhocFacilityResponseDTO() {
		return new AdhocFacilityResponseDTO();
	}
	
	//DigitalLibrary
	
	public DigitalLibraryRequestDTO createDigitalLibraryRequestDTO() {
		return new DigitalLibraryRequestDTO();
	}
	public DigitalLibraryResponseDTO createDigitalLibraryResponseDTO() {
		return new DigitalLibraryResponseDTO();
	}
	

	public WMSRequestDTO createWMSRequestDTO() {
		return new WMSRequestDTO();		
	}	
	public WMSResponseDTO createWMSResponseDTO() {
		return new WMSResponseDTO();		
	}
	
	//FOR ISAC USER
	public ISACRequestDTO createIsacRequestDTO() {
		return new ISACRequestDTO();		
	}	
	public ISACResponseDTO createIsacResponseDTO() {
		return new ISACResponseDTO();		
	}

	public ECBFLimitRequestDTO createECBFLimitRequestDTO() {
		return new ECBFLimitRequestDTO();		
	}	
	public ECBFLimitResponseDTO createECBFLimitResponseDTO() {
		return new ECBFLimitResponseDTO();		
	}


	public CAMExtensionDateRequestDTO createCAMExtensionDateRequestDTO() {
		return new CAMExtensionDateRequestDTO();
	}
	public CAMExtensionDateResponseDTO createCAMExtensionDateResponseDTO() {
		return new CAMExtensionDateResponseDTO();
	}

}