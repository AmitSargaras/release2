package com.integrosys.cms.host.mq;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.log.OBLogMessage;
import com.integrosys.cms.host.eai.service.IEaiMessageManager;
import com.integrosys.cms.host.eai.service.MessageHandler;
import com.integrosys.cms.host.eai.support.EAIHeaderHelper;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;

/**
 * JMS Message Listener implementation
 * 
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.0
 */
public class EAIMessageListener implements MessageListener, IEAIHeaderConstant {

	// private static final String DATE_FORMAT = "dd-MMM-yyyy HH:mm:ss";
	private static final String DATE_FORMAT = "yyyyMMddHHmmss";

	private MessageCenterStartup startupCenter = null;

	private SimpleDateFormat dateFormat = null;

	private String inQueue;

	private String outQueue;

	private MessageHandler messageHandler;

	private IEaiMessageManager eaiMessangeManager;

	public MessageHandler getMessageHandler() {
		return messageHandler;
	}

	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	public IEaiMessageManager getEaiMessangeManager() {
		return eaiMessangeManager;
	}

	public void setEaiMessangeManager(IEaiMessageManager eaiMessangeManager) {
		this.eaiMessangeManager = eaiMessangeManager;
	}

	public String getInQueue() {
		return inQueue;
	}

	public String getOutQueue() {
		return outQueue;
	}

    /**
	 * Default Constructor
	 */
    public EAIMessageListener() {
    }    

	public EAIMessageListener(MessageCenterStartup center) {
		startupCenter = center;
		dateFormat = new SimpleDateFormat(DATE_FORMAT);
		dateFormat.setTimeZone(DateUtil.getTimeZone());
	}

	public EAIMessageListener(MessageCenterStartup center, String inQueue, String outQueue) {
		DefaultLogger.debug(this, "EAIMessageListener inQueue- " + inQueue);
		DefaultLogger.debug(this, "EAIMessageListener outQueue- " + outQueue);
		DefaultLogger.debug(this,"---> Initializing EAIMessageListener for queue" + inQueue);

		this.inQueue = inQueue;
		this.outQueue = outQueue;

		startupCenter = center;
		dateFormat = new SimpleDateFormat(DATE_FORMAT);
		dateFormat.setTimeZone(DateUtil.getTimeZone());
	}

	/**
	 * This method provides the implementation of the MessageListener interface.
	 */
	public void onMessage(javax.jms.Message message) {

		DefaultLogger.debug(this, "---> onMessage is triggered!! from " + this.inQueue);
//		System.out.println("--->  onMessage is triggered!! from " + this.inQueue);
		Message ack = null;
		String msgID = null;
		OBLogMessage logMsg = null;
		String correlationID = null;

		// Try to get correlationID
		try {
			correlationID = message.getJMSCorrelationID();

			DefaultLogger.debug(this, "** [Text Message CorrelationID]: " + correlationID);

		}
		catch (JMSException ex) {
			DefaultLogger.error(this, "Caught Exception while getJMSCorrelationID", ex);
		}

		if (message instanceof TextMessage) {
			// identify if this is a stop message first
			try {

				if (MQTester.SELECTOR_VALUE_CONVERTED.equalsIgnoreCase(correlationID)) {
					DefaultLogger.warn(this, "\n\n********** Stopping MessageListener **********\n\n");
					message.acknowledge();
					startupCenter.stopListener(); // stop after ack
					return;
				}
			}
			catch (Exception ex) {
				DefaultLogger.error(this, "Caught Exception while processing Stop Request!", ex);
				try {
					message.acknowledge(); // IMPT:this is required because the
					// listener is configured to client
					// ack.
				}
				catch (Exception ex2) {
					DefaultLogger.error(this, "Error while doing ack!", ex2);
				}
				finally {
					return;
				}
			}

			try {
				// display the message contents
				String msg = ((TextMessage) message).getText();

				msgID = EAIHeaderHelper.getHeaderValue(msg, MESSAGE_ID);

				DefaultLogger.debug(this, "EAIMessageListener Received Message");
				DefaultLogger.debug(this, "\n*** Begin Processing Message ID: " + msgID + "\n");
				logMsg = createLogMessage(msg);
				ack = processMessage(msg, getQueuePair(correlationID));
				DefaultLogger.debug(this, "Log Message: " + logMsg);
			}
			catch (JMSException e) {
				StringWriter strwtr = new StringWriter();
				PrintWriter prt = new PrintWriter(strwtr);
				e.printStackTrace(prt);

				if (logMsg != null) {
					logMsg.setErrorStackTrace(strwtr.toString());
				}

				DefaultLogger.error(this, "JMSException in onMessage! ", e);
				Exception le = e.getLinkedException();

				if (le != null) {
					DefaultLogger.error(this, "Linked Exception is: ", le);
				}
			}
			catch (Exception e) {
				StringWriter strwtr = new StringWriter();
				PrintWriter prt = new PrintWriter(strwtr);
				e.printStackTrace(prt);

				if (logMsg != null) {
					logMsg.setErrorStackTrace(strwtr.toString());
				}

				DefaultLogger.error(this, "Exception in onMessage! ", e);
			}
			finally {
				try {
					message.acknowledge();
					persistMQLogMessage(logMsg);
					DefaultLogger.debug(this, "\n*** End Processing Message ID: " + msgID + "\n");
				}
				catch (Exception e) {
					DefaultLogger.error(this, "Caught Exception in finally clause of onMessage!", e);
				}
			}
		}
		else {
			try {
				message.acknowledge();
			}
			catch (Exception e) {
				DefaultLogger.error(this, "Caught Exception in finally clause of onMessage!", e);
			}
			finally {
				DefaultLogger.debug(this, "<WARNING>:Message received is not TextMessage!!!");
			}
		}
		DefaultLogger.debug(this, "Listener processing onMessage ended");

	}

	private Message processMessage(String msg, String[] queuePair) throws Exception {

		return getMessageHandler().process(msg);
	}

	private void persistMQLogMessage(OBLogMessage logMsg) {
		getEaiMessangeManager().logMessage(logMsg);
	}

	/**
	 * Create a log message
	 */
	private OBLogMessage createLogMessage(String msg) {
		OBLogMessage logMsg = new OBLogMessage();

		logMsg.setSCIMsgId(EAIHeaderHelper.getHeaderValue(msg, MESSAGE_ID));
		String dateStr = EAIHeaderHelper.getHeaderValue(msg, PUBLISH_DATE);

		Date publishDate = null;
		try {
			if (null != dateStr) {
				publishDate = dateFormat.parse(dateStr);
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception while parsing date. Setting date as null.", e);
			publishDate = null;
		}
		logMsg.setPublishDate(publishDate);
		logMsg.setReceivedDate(DateUtil.getDate());
		logMsg.setReceivedMessage(msg);

		return logMsg;
	}

	/**
	 * Set the 3rd Param with CorrelationId
	 * 
	 * @param correlationId
	 * @return
	 */
	private String[] getQueuePair(String correlationId) {
		String[] pair = new String[3];
		pair[0] = this.inQueue;
		pair[1] = this.outQueue;
		pair[2] = correlationId;
		return pair;
	}
}
