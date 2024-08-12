/**
 * 
 */
package com.integrosys.cms.ws.common;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.integrosys.base.techinfra.logger.DefaultLogger;
//import com.sun.istack.internal.NotNull;
//import com.sun.istack.internal.Nullable;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author bhushan.malankar
 *
 */


public class AuthenticationHandler implements SOAPHandler<SOAPMessageContext>{



	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		System.out.println("In AuthenticationHandler !");
		DefaultLogger.debug(this, "In AuthenticationHandler!");
		if(!(Boolean)context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)){
			Map<String, List<String>> map = (Map<String, List<String>>) context.get(MessageContext.HTTP_REQUEST_HEADERS);
			List<String> contentType = getHTTPHeader(map, "Authorization");
			if(contentType != null){
				StringBuffer strBuf = new StringBuffer();
				for(String type : contentType){
					strBuf.append(type);
				}
				System.out.println("Content-Type:"+strBuf.toString());
			}
		}

		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		// TODO Auto-generated method stub
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
//	Basic YmhhcmF0OjEyMw==


	List<String> getHTTPHeader(Map<String, List<String>> headers,String header){
		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			String name = entry.getKey();
			String password = "";
			List<String> value = entry.getValue();
			if(value!=null){
				password = value.get(0).replaceAll("Basic", "");
				password = password.replace(" ", "");
			}
			if(name.equalsIgnoreCase(header))
			{ 
				BASE64Decoder decoder = new BASE64Decoder();
				BASE64Encoder encoder = new BASE64Encoder();

				// String encodedBytes = encoder.encodeBuffer("JavaTips.net".getBytes());
				//  System.out.println("encodedBytes " + encodedBytes);
				byte[] decodedBytes;
				try {
					decodedBytes = decoder.decodeBuffer(password);
					System.out.println("decodedBytes " + new String(decodedBytes));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				return entry.getValue();
			}
		}
		return null;
	}



}
