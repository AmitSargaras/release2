package com.integrosys.cms.app.ws.dto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.ws.common.WebServiceStatusCode;
import com.integrosys.cms.app.ws.jax.common.JAXBTransformer;
import com.integrosys.cms.app.ws.jax.common.Transformer;

@Service
public class InterfaceHelper {

	/*@Autowired
	private IInterfaceLogDao interfaceLogDao;

	public void setInterfaceLogDao(IInterfaceLogDao interfaceLogDao) {
		this.interfaceLogDao = interfaceLogDao;
	}*/

	public void interfaceLoggingActivity(InterfaceLoggingDTO loggingDTO) {

		try {
			MessageFactory factory;
			RequestDTO request = loggingDTO.getRequestDto();
			ResponseDTO response = loggingDTO.getResponseDTO();

			factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			Transformer transformer = new JAXBTransformer();
			if (request != null) {
				SOAPMessage messageReq = factory.createMessage();
				SOAPBody bodyReq = messageReq.getSOAPBody();
				transformer.dtoToXML(request, bodyReq);
				ByteArrayOutputStream baosReq = new ByteArrayOutputStream();
				messageReq.writeTo(baosReq);
				loggingDTO.setRequestMessage(baosReq.toString());
				
			}
			if (loggingDTO.getErrorDetailDTO() != null) {
				loggingDTO.setErrorCode(loggingDTO.getErrorDetailDTO().getErrorCode());
				loggingDTO.setErrorMessage(loggingDTO.getErrorDetailDTO().toString());
				loggingDTO.setStatus(WebServiceStatusCode.FAIL.name());
				
				// Capturing Error xml in ResponseMessage field.
				SOAPMessage messageRes = factory.createMessage();
				SOAPBody bodyErrRes = messageRes.getSOAPBody();
				transformer.errorDtoToXML(loggingDTO.getErrorDetailDTO(), bodyErrRes);
				ByteArrayOutputStream baosResp = new ByteArrayOutputStream();
				messageRes.writeTo(baosResp);
				loggingDTO.setResponseMessage(baosResp.toString());
			}
			if (response != null) {
				SOAPMessage messageRes = factory.createMessage();
				SOAPBody bodyRes = messageRes.getSOAPBody();
				transformer.dtoToXML(loggingDTO.getResponseDTO(), bodyRes);
				ByteArrayOutputStream baosResp = new ByteArrayOutputStream();
				messageRes.writeTo(baosResp);
				loggingDTO.setResponseMessage(baosResp.toString());

				if (response.getTransactionID() != null && !"".equals(response.getTransactionID())) {
					loggingDTO.setTransactionId(Long.parseLong(response.getTransactionID()));
				}

				loggingDTO.setStatus(response.getResponseStatus());				
			}
			IInterfaceLogDao interfaceLogDao = (IInterfaceLogDao) BeanHouse.get("interfaceLogDao");

			OBInterfaceLog obj = interfaceLogDao.getActualFromDTO(loggingDTO);
			IInterfaceLog createdObj = interfaceLogDao.createInterfaceLog(obj);
			
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	
	
	

}
