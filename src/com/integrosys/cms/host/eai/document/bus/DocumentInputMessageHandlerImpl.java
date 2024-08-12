package com.integrosys.cms.host.eai.document.bus;

import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractWorkflowAwareMessageHandler;

/**
 * 
 * @author Iwan Satria
 * 
 */
public class DocumentInputMessageHandlerImpl extends AbstractWorkflowAwareMessageHandler {

	/**
	 * This method will execute the preprocess method of
	 * RepublishActuallHandler. It enable republish message to prepare/sort out
	 * data before proceeding with the normal flow .
	 */
	protected Message preprocess(Message msg) throws EAIMessageException {
		// Do nothing
		return msg;
	}

}
