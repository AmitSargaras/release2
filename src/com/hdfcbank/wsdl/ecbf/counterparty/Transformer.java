package com.hdfcbank.wsdl.ecbf.counterparty;

import java.io.InputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public interface Transformer {

	public Document dtoToXML(ClimesBorrowerVo requestDTO);

	public void dtoToXML(ClimesBorrowerVo requestDTO, Node methodElement);
	
	public ClimesBorrowerResponseVo xmlToDTO(Node incoming);

	public ClimesBorrowerResponseVo xmlToDTO(InputStream is); 
	
	public ClimesBorrowerResponseVo xmlToDTO(String is); 
	
	public void dtoToXML(ClimesBorrowerResponseVo responseDTO, Node methodElement) ;
}