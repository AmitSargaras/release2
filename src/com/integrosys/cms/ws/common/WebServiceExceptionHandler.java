/**
 * 
 */
package com.integrosys.cms.ws.common;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Not in use
 * @author bhushan.malankar
 *
 */
public class WebServiceExceptionHandler implements SOAPHandler<SOAPMessageContext>{

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		System.out.println("In ExceptionHandler!!!!");
		DefaultLogger.error(this, "In ExceptionHandler!");
		
		return true;
			 
	}

	@Override
	public void close(MessageContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
