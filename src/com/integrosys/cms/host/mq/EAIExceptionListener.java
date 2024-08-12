package com.integrosys.cms.host.mq;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class EAIExceptionListener implements ExceptionListener {
	MessageCenterStartup parent = null;

	/**
	 * Default Constructor
	 */
	public EAIExceptionListener() {
	}

	public EAIExceptionListener(MessageCenterStartup parent) {
		this.parent = parent;
	}

	/**
	 * Exception
	 */
	public void onException(JMSException e) {
		DefaultLogger.error(this, "Caught JMSException in EAIExceptionListener!", e);
		// try to reconnect in reloadable
		if (null != parent) {
			parent.setReconnect(true);
		}
	}
}
