/**
 * 
 */
package com.integrosys.cms.app.ws.jax.common;

import javax.xml.ws.WebFault;

import com.integrosys.cms.app.ws.dto.ErrorDetailDTO;

/**
 * @author bhushan.malankar
 * 
 */
@WebFault(name = "ErrorDetailDTO", targetNamespace = "http://www.aurionprosolutions.com/CLIMSFault/", faultBean="com.integrosys.cms.app.ws.dto.ErrorDetailDTO" )
public class CMSFault extends Exception {

	protected ErrorDetailDTO faultInfo;

	public CMSFault(String message, ErrorDetailDTO faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	public CMSFault(String message, ErrorDetailDTO faultInfo,
			Throwable cause) {
		super(message, cause);
		this.faultInfo = faultInfo;
	}

	public ErrorDetailDTO getFaultInfo() {
		return faultInfo;
	}

}
