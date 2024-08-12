package com.integrosys.cms.app.ws.jax.common;

import java.io.InputStream;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.integrosys.cms.app.ws.dto.ErrorDetailDTO;
import com.integrosys.cms.app.ws.dto.RequestDTO;
import com.integrosys.cms.app.ws.dto.ResponseDTO;


public class JAXBTransformer implements Transformer {

	private static final String PACKAGE_NAME = "com.integrosys.cms.app.ws.jax.common:com.integrosys.cms.app.ws.dto";

	private static JAXBContext context;

	public JAXBTransformer() {

		super();
		if (context == null) {
			try {
				context = JAXBContext.newInstance(PACKAGE_NAME);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
	}

	public ResponseDTO xmlToDTO(Node incoming) {

		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (ResponseDTO) unmarshaller.unmarshal(incoming);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Document dtoToXML(RequestDTO outgoing) {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			  factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
			  factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(outgoing, document);
			return document;
		} catch (ParserConfigurationException parserException) {
			parserException.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void dtoToXML(ResponseDTO requestDTO, Node methodElement) {

		try {
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(requestDTO, methodElement);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public void errorDtoToXML(ErrorDetailDTO errorDetailDTO, Node methodElement) {
		try {
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(errorDetailDTO, methodElement);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		try {
			JAXBContext context = JAXBContext.newInstance(PACKAGE_NAME);
			// Marshaller marshaller = context.createMarshaller();
			//
			// CustomerVerificationRequestDTO outgoing = new
			// CustomerVerificationRequestDTO();
			// outgoing.setAccountNumber("accountNumber");
			// Spare spare = new Spare();
			// spare.setSpareAttribute("whatever");
			// outgoing.getSpareList().add(spare);
			//
			// marshaller.marshal(outgoing, System.out);

			Unmarshaller unmarshaller = context.createUnmarshaller();
			/*CIFEnquiryResponseDTO accountSummaryResponseDTO = (CIFEnquiryResponseDTO) unmarshaller
					.unmarshal(new ByteArrayInputStream(
							"<cor:AccountSummaryResponse xmlns:cor=\"http://acledabank.com.kh/corebanking/\"><response><accountinfo><accountno/><accountname/><status/><type/><branchcode/><currencycode/><availablebalance/><workingbalance/><utilizedlimit/><unutilizedlimit/></accountinfo><error><errorcode>E-168054-NO RECORDS RETURNED BY ROUTINE BASED SELECTION</errorcode><errormessage>No records were found that matched the selection criteria</errormessage></error></response></cor:AccountSummaryResponse>/n"
									.getBytes()));*/
			
			
			/*CIFEnquiryResponseDTO accountSummaryResponseDTO = (CIFEnquiryResponseDTO) unmarshaller
			.unmarshal(new ByteArrayInputStream(
					"<cif:CIFEnquiryResponse xmlns:cif=\"http://www.centralbankofindia.com/CIF/\"><cifnumber>12345</cifnumber><customername>TATA MOTORS</customername><addressline1>ANDHERI</addressline1><addressline2>EAST</addressline2><addressline3>SAHAR ROAD</addressline3><city>MUMBAI</city><stateid>MAHARASHTRA</stateid><country>INDIA</country><pincode>400060</pincode><telephoneno>9898989898</telephoneno><emailid>TATAMOTORS@TATA.COM</emailid></cif:CIFEnquiryResponse>"
							.getBytes()));*/
		
			//CIFEnquiryResponseDTO o = (CIFEnquiryResponseDTO)unmarshaller.unmarshal( new File( "D:/appldev/APS/INTERFACES/ResponseXml/CustomerSummaryResponse2.xml" ) );
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public void dtoToXML(RequestDTO requestDTO, Node methodElement) {

		try {
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(requestDTO, methodElement);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public ResponseDTO xmlToDTO(String is) {

		try {
			
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			
			
			return (ResponseDTO) unmarshaller.unmarshal(new StreamSource(new StringReader(is)));
		
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}


	public ResponseDTO xmlToDTO(InputStream is) {
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (ResponseDTO) unmarshaller.unmarshal(is);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void dtoToXML(Object object, Node methodElement) {

		try {
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(object, methodElement);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}