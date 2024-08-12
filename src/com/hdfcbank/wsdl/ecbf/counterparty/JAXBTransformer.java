package com.hdfcbank.wsdl.ecbf.counterparty;

import java.io.InputStream;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


public class JAXBTransformer implements Transformer {

	private static final String PACKAGE_NAME = "com.hdfcbank.wsdl.ecbf.counterparty";

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

	public ClimesBorrowerResponseVo xmlToDTO(Node incoming) {

		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (ClimesBorrowerResponseVo) unmarshaller.unmarshal(incoming);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Document dtoToXML(ClimesBorrowerVo outgoing) {

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
	
	public void dtoToXML(ClimesBorrowerResponseVo responseDTO, Node methodElement) {

		try {
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(new JAXBElement<ClimesBorrowerResponseVo>(new QName("uri","local"), ClimesBorrowerResponseVo.class, responseDTO) , methodElement);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		try {
			JAXBContext context = JAXBContext.newInstance(PACKAGE_NAME);
			Unmarshaller unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public void dtoToXML(ClimesBorrowerVo requestDTO, Node methodElement) {

		try {
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(new JAXBElement<ClimesBorrowerVo>(new QName("uri","local"), ClimesBorrowerVo.class, requestDTO), methodElement);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public ClimesBorrowerResponseVo xmlToDTO(String is) {
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (ClimesBorrowerResponseVo) unmarshaller.unmarshal(new StreamSource(new StringReader(is)));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ClimesBorrowerResponseVo xmlToDTO(InputStream is) {
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (ClimesBorrowerResponseVo) unmarshaller.unmarshal(is);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

}