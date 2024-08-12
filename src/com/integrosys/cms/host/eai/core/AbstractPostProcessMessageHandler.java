package com.integrosys.cms.host.eai.core;

import java.util.Map;
import java.util.Properties;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.Message;

/**
 * <p>
 * Post Process Message handler to handle the message already processed (full
 * cycle)
 * <p>
 * This is required as to cater Entity Bean and Hibernate Session coexistence,
 * especially the data persisted by Hibernate might not visible for Entity Bean,
 * or vice-versa. So the process of reading domain object using EJB can only
 * happen after everything has been completed/committed.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class AbstractPostProcessMessageHandler extends AbstractCommonMessageHandler {

	protected Properties doProcessMessage(EAIMessage eaiMessage) throws EAIMessageException {
		try {
			doPostProcessMessage(eaiMessage);
		}
		catch (Throwable ex) {
			logger.warn("failed to post process message, message header [" + eaiMessage.getMsgHeader()
					+ "], please verify.", ex);
		}

		return null;
	}

	/**
	 * <p>
	 * Post Process the message which already processed by the normal workflow
	 * aware message handler.
	 * <p>
	 * This should be only used certain info (normally the key info) in the
	 * message to further process extra business logic. And transaction
	 * demarcation will be totally different from the message handler that
	 * process the message.
	 * 
	 * @param eaiMessage
	 */
	protected abstract void doPostProcessMessage(EAIMessage eaiMessage);

	public final void postprocess(Message msg, Message stagingMsg, Map flatMessage) throws EAIMessageException {
	}

}
