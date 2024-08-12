package com.integrosys.cms.host.eai.response;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class Response implements java.io.Serializable {

	private static final long serialVersionUID = -3346257318664387773L;

	private String responseCode;

	private String responseMessage = "";

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
}
