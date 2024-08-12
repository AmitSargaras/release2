package com.integrosys.cms.app.ws.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class RequestDTO implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 460259111650274874L;
	
	@XmlElement(required=true)
	private String wsConsumer;
	
	public RequestDTO() {
		super();
	}

	public String getWsConsumer() {
		return wsConsumer;
	}

	public void setWsConsumer(String wsConsumer) {
		this.wsConsumer = wsConsumer;
	}

	@Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }	
		
}

