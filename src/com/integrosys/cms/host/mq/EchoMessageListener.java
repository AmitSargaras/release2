package com.integrosys.cms.host.mq;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.host.eai.log.OBLogMessage;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.eai.support.IEAIResponseConstant;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class EchoMessageListener implements MessageListener, IEAIHeaderConstant {

	private static final String DATE_FORMAT = "dd-MMM-yyyy HH:mm:ss";

	private MessageCenterStartup startupCenter = null;

	private SimpleDateFormat dateFormat = null;

	private String inQueue;

	private String outQueue;

	public String getInQueue() {
		return inQueue;
	}

	public String getOutQueue() {
		return outQueue;
	}

	/**
	 * Default Constructor
	 */
	public EchoMessageListener(MessageCenterStartup center) {
		startupCenter = center;
		dateFormat = new SimpleDateFormat(DATE_FORMAT);
		dateFormat.setTimeZone(DateUtil.getTimeZone());
	}

	public EchoMessageListener(MessageCenterStartup center, String inQueue, String outQueue) {
		DefaultLogger.debug(this, "EAIMessageListener inQueue- " + inQueue);
		DefaultLogger.debug(this, "EAIMessageListener outQueue- " + outQueue);

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

		String ack = null;
		String msgID = null;
		OBLogMessage logMsg = null;

		try {
			message.acknowledge();

			ack = ((TextMessage) message).getText();

			if (ack != null) {
				startupCenter.sendMessage(ack, this.outQueue);
			}

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in finally clause of onMessage!", e);
			// consume
		}

	}

	private void persistMQLogMessage(OBLogMessage logMsg) throws Exception {
		// todo
		/*
		 * SBEAIMessageManager remote = (SBEAIMessageManager)
		 * BeanController.getEJB ("SBEAIMessageManagerHome",
		 * "com.integrosys.cms.host.message.castor.eai.SBEAIMessageManagerHome"
		 * ); remote.logMessage(logMsg);
		 */
	}

	/**
	 * Prepare the final log message
	 */
	private OBLogMessage prepareFinalLogMessage(OBLogMessage logMsg, String ack) {
		// subscriber ack date
		logMsg.setSubscriberAckDate(DateUtil.getDate());
		// response code
		logMsg.setSubscriberResponseCode(getHeaderValue(ack, ACK_RESP_CODE_TAG));
		// response desc
		logMsg.setSubscriberResponseDesc(getHeaderValue(ack, ACK_RESP_MESSAGE_TAG));
		// response message
		logMsg.setSubscriberResponseMessage(ack);

		logMsg.setSubscriberResponseType(IEAIResponseConstant.TYPE_TECHNICAL);

		return logMsg;
	}

	/**
	 * Create a log message
	 */
	private OBLogMessage createLogMessage(String msg) {
		OBLogMessage logMsg = new OBLogMessage();
		// message ID
		logMsg.setSCIMsgId(getHeaderValue(msg, MESSAGE_ID));

		// publish date
		String dateStr = getHeaderValue(msg, PUBLISH_DATE);
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
		// received date
		logMsg.setReceivedDate(DateUtil.getDate());
		// received message
		logMsg.setReceivedMessage(msg);

		return logMsg;
	}

	/**
	 * Helper method
	 */
	private String getHeaderValue(String msg, String tag) {
		try {
			String newTag = "<" + tag.toUpperCase() + ">";
			int start = msg.toUpperCase().indexOf(newTag);
			start = start + newTag.length();

			String temp = msg.substring(start, msg.length());
			int end = temp.toUpperCase().indexOf(tag.toUpperCase());
			end = end - 2; // move back 1 for </ symbol

			String value = temp.substring(0, end);
			return value;
		}
		catch (Exception e) {
			// DefaultLogger.error(this,e.getMessage(), e);

			return null;
		}
	}

}
