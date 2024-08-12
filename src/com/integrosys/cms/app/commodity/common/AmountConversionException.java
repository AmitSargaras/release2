/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/common/AmountConversionException.java,v 1.4 2004/08/07 07:08:00 lyng Exp $
 */
package com.integrosys.cms.app.commodity.common;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Exception class represents amount conversion error.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/07 07:08:00 $ Tag: $Name: $
 */
public class AmountConversionException extends OFAException {
	public static final String AMT_CONV_ERR_CODE = "AMT_CONV_EX";
	
	private String fromCcyCode;
	private String toCcyCode;

	/**
	 * Constructs amount conversion exception with an error message.
	 * 
	 * @param errMsg error message
	 */
	public AmountConversionException(String errMsg) {
		super(errMsg);
	}

    public AmountConversionException(String errMsg, Exception e) {
        super(errMsg,  e);
    }

    /**
	 * Get amount conversion exception error code.
	 * 
	 * @return error code
	 */
	public String getErrorCode() {
		return AMT_CONV_ERR_CODE;
	}
	
	public String getFromCcyCode() {
		return this.fromCcyCode;
	}
	
	public void setFromCcyCode(String fromCcyCode) {
		this.fromCcyCode = fromCcyCode;
	}
	
	public String getToCcyCode() {
		return this.toCcyCode;
	}
	
	public void setToCcyCode(String toCcyCode) {
		this.toCcyCode = toCcyCode;
	}
}
