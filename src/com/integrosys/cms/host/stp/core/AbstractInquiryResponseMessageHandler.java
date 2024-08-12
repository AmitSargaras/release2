package com.integrosys.cms.host.stp.core;

import com.integrosys.cms.host.stp.STPMessage;
import com.integrosys.cms.host.stp.STPMessageException;

import java.util.Map;
import java.util.Properties;

/**
 * Abstract implementation for the message handler doing inquiry and response.
 * So basically there is no involved of CRUD on actual or staging domain, and
 * also the workflow.
 *
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 *
 */
public abstract class AbstractInquiryResponseMessageHandler extends AbstractCommonMessageHandler {

	/**
	 * not supposed to be implemented by inquiry response typed message handler,
	 * make it as <code>final</code>
	 */
	public final void postprocess(STPMessage msg, STPMessage stagingMsg, Map flatMessage) throws STPMessageException {
	}

	protected Properties doProcessMessage(STPMessage message) throws STPMessageException {
		STPMessage response = doProcessInquiryMessage(message);

		Properties prop = new Properties();
		prop.put("msgobj", response);

		return prop;
	}

	/**
	 * Process the inquiry message and return the message object which contain
	 * the inquiry result (response) ready to send back to the caller/source
	 * system.
	 *
	 * @param message the message object contains inquiry info.
	 * @return the response message which is the result of the inquiry
	 */
	protected abstract STPMessage doProcessInquiryMessage(STPMessage message);

}