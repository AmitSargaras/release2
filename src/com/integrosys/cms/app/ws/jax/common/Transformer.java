package com.integrosys.cms.app.ws.jax.common;

import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.integrosys.cms.app.ws.dto.ErrorDetailDTO;
import com.integrosys.cms.app.ws.dto.RequestDTO;
import com.integrosys.cms.app.ws.dto.ResponseDTO;



public interface Transformer {

	public Document dtoToXML(RequestDTO requestDTO);

	public void dtoToXML(RequestDTO requestDTO, Node methodElement);
	
	public void errorDtoToXML(ErrorDetailDTO errorDetailDTO, Node methodElement);

	public ResponseDTO xmlToDTO(Node incoming);

	public ResponseDTO xmlToDTO(InputStream is); 
	
	public ResponseDTO xmlToDTO(String is); 
	
	public void dtoToXML(ResponseDTO requestDTO, Node methodElement) ;
	
	public void dtoToXML(Object object, Node methodElement);
	
}
