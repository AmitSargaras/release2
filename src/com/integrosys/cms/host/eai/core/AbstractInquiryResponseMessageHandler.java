package com.integrosys.cms.host.eai.core;

import java.util.Map;
import java.util.Properties;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.Message;

/**
 * Abstract implementation for the message handler doing inquiry and response.
 * So basically there is no involved of CRUD on actual or staging domain, and
 * also the workflow.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class AbstractInquiryResponseMessageHandler extends AbstractCommonMessageHandler {

	/**
	 * not supposed to be implemented by inquiry response typed message handler,
	 * make it as <code>final</code>
	 */
	public final void postprocess(Message msg, Message stagingMsg, Map flatMessage) throws EAIMessageException {
	}

	protected Properties doProcessMessage(EAIMessage eaiMessage) throws EAIMessageException {
		EAIMessage response = doProcessInquiryMessage(eaiMessage);

		Properties prop = new Properties();
		prop.put(IEaiConstant.MSG_OBJ, response);

		return prop;
	}

	/**
	 * Process the inquiry message and return the message object which contain
	 * the inquiry result (response) ready to send back to the caller/source
	 * system.
	 * 
	 * @param eaiMessage the message object contains inquiry info.
	 * @return the response message which is the result of the inquiry
	 */
	protected abstract EAIMessage doProcessInquiryMessage(EAIMessage eaiMessage);

}
