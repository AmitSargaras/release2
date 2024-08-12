package com.integrosys.cms.host.eai.web;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.core.MessageMarshallerFactory;
import com.integrosys.cms.host.eai.support.IMessageFolder;
import com.integrosys.cms.host.eai.support.MessageHandlerObserver;
import com.integrosys.cms.host.eai.support.MessageHandlerSubject;

/**
 * <p>
 * Web component to interface with eai message handler. This will act like a
 * observer to subscribe to message handler, which then will be notify if the
 * message passed has been processed.
 * <p>
 * Default message attribute/parameter name is <code>msg</code>. This
 * implementation assume the remote is using valid XML message to passed in.
 * Other type of data need to have separate Action class.
 * 
 * @author Chong Jun Yong
 * @since 25.08.2008
 */
public class EaiMessageAction extends Action implements MessageHandlerObserver {

	private static final Logger logger = LoggerFactory.getLogger(EaiMessageAction.class);

	private static final String DEFAULT_MESSAGE_ATTRIBUTE_NAME = "msg";

	private IMessageFolder messageFolder;

	private MessageHandlerSubject messageHandlerSubject;

	private EAIMessage responseMessage;

	private String messageAttributeName;

	private MessageMarshallerFactory messageMarshallerFactory;

	private long processWaitingSleepingPeriod;

	private String remoteHost;

	private String remoteAddress;

	private String requestedSessionId;

	public EaiMessageAction() {
		this.messageAttributeName = DEFAULT_MESSAGE_ATTRIBUTE_NAME;
	}

	public void setMessageFolder(IMessageFolder messageFolder) {
		this.messageFolder = messageFolder;
	}

	public void setMessageHandlerSubject(MessageHandlerSubject messageHandlerSubject) {
		this.messageHandlerSubject = messageHandlerSubject;
	}

	public void setMessageAttributeName(String messageAttributeName) {
		this.messageAttributeName = messageAttributeName;
	}

	public void setMessageMarshallerFactory(MessageMarshallerFactory messageMarshallerFactory) {
		this.messageMarshallerFactory = messageMarshallerFactory;
	}

	/**
	 * Period of time to sleep while waiting for message handler to be released
	 * by another request.
	 * @param processWaitingSleepingPeriod time to sleep in milli seconds while
	 *        getting notified by message handler subject
	 */
	public void setProcessWaitingSleepingPeriod(long processWaitingSleepingPeriod) {
		this.processWaitingSleepingPeriod = processWaitingSleepingPeriod;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String xmlMessage = request.getParameter(this.messageAttributeName);
		if (xmlMessage == null || xmlMessage.length() == 0) {
			logger.warn("msg passed in from [" + request.getRemoteAddr() + "] is empty, returning empty as well");

			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "value for 'msg' is empty, please provide.");
			return null;
		}

		// for toString() purpose
		this.remoteAddress = request.getRemoteAddr();
		this.remoteHost = request.getRemoteHost();
		this.requestedSessionId = request.getRequestedSessionId();

		String msgId = this.messageFolder.putMessage(xmlMessage);

		this.messageHandlerSubject.registerAndProcess(msgId, this);

		while (this.responseMessage == null) {
			try {
				Thread.sleep(this.processWaitingSleepingPeriod);
			}
			catch (InterruptedException ex) {
				logger.warn("The thread was interrupted while waiting for getting notified, possible ?", ex);
			}
		}

		String responseContent = this.messageMarshallerFactory.marshall(responseMessage);

		response.setStatus(HttpServletResponse.SC_OK);

		Writer writer = response.getWriter();
		writer.write(responseContent);
		writer.flush();

		return null;
	}

	public void update(Object object) {
		this.responseMessage = (EAIMessage) object;
	}

	/**
	 * Return string representation of remote address, host, session id, and
	 * hash code of this object as well, for easier tracing.
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer("EaiMessageAction@");

		buf.append(System.identityHashCode(this)).append(", ");
		buf.append("Remote Address [").append(remoteAddress).append("], ");
		buf.append("Remote Host [").append(remoteHost).append("], ");
		buf.append("Request Session Id [").append(requestedSessionId).append("]");

		return buf.toString();
	}
}