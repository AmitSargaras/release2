/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author $Author: Ankit
 * @version $Revision:$
 * @since $Date:05-Aug-2014 $ Tag: $Name$
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PartyDetailsResponse")
public class PartyDetailsResponseDTO extends ResponseDTO{
	
	private static final long serialVersionUID = -114309476199266725L;
	
	@XmlElement(name = "partyId")
	private String partyId;

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	
	
}