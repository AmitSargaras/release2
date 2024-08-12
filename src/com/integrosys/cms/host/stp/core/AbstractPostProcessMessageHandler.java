package com.integrosys.cms.host.stp.core;

import com.integrosys.cms.host.stp.STPMessage;
import com.integrosys.cms.host.stp.STPMessageException;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.core.AbstractCommonMessageHandler;

import java.util.Map;
import java.util.Properties;

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
 * @author Chin Kok Cheong
 *
 */
public abstract class AbstractPostProcessMessageHandler extends AbstractCommonMessageHandler {

    protected Properties doProcessMessage(STPMessage message) throws STPMessageException {
		try {
			doPostProcessMessage(message);
		}
		catch (Throwable ex) {
			logger.warn("failed to post process message, message header [" + message.getMsgHeader()
					+ "], please verify.", ex);
		}

		return null;
	}

	protected Properties doProcessMessage(Object obj) throws STPMessageException {
        STPMessage response = doPostProcessMessage(obj);

		Properties prop = new Properties();
		prop.put("msgobj", response);

		return prop;
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
	 * @param message
	 */
	protected abstract void doPostProcessMessage(STPMessage message);

    protected abstract STPMessage doPostProcessMessage(Object obj);

	public final void postprocess(STPMessage msg, STPMessage stagingMsg, Map flatMessage) throws STPMessageException {
	}

}