package com.integrosys.cms.host.mq;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;

import javax.jms.JMSException;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.cms.host.eai.CMSHeader;
import com.integrosys.cms.host.eai.EAIBody;
import com.integrosys.cms.host.eai.EAIHeader;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.castor.EAICastorHelper;
import com.integrosys.cms.host.eai.log.OBLogMessage;
import com.integrosys.cms.host.eai.support.EAIHeaderHelper;
import com.integrosys.cms.host.eai.support.EAIMessageBodyBuilderHelper;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.eai.support.IEAIResponseConstant;

/**
 * Used for sending messages out
 * 
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */

/*Remove the CMS Header from message according to the template*/

public class EAIMessenger implements IMQConstant {
	private final static Logger logger = LoggerFactory.getLogger(EAIMessenger.class);

	public EAIMessenger() {
	}

	/**
	 * This method is used for sending out messages. Asynchronous messages, does
	 * not need to wait for reply.
	 * @param messageInitiator IMQConstant.FROM_HOST / IMQConstant.FROM_GCMS,
	 *        indicates actual originator of the message. If comes from HOST,
	 *        then different queue will be used to send out.
	 * @param mode I - Inquiry / U - Update indicates transaction mode of the
	 *        message. Different queue will be used for Inquiry and Update.
	 * @param messageType
	 * @param messageId
	 * @param dataMap
	 * @return String the xml formed for sending
	 */
	public static String sendMessage(int messageInitiator, String mode, String messageType, String messageId,
			Map headerMap, Map dataMap) {

		return sendMessage(messageInitiator, mode, messageType, messageId, headerMap, dataMap, null);

	}

	/**
	 * This method is used for sending out messages. Asynchronous messages, does
	 * not need to wait for reply.
	 * @param messageInitiator IMQConstant.FROM_HOST / IMQConstant.FROM_GCMS,
	 *        indicates actual originator of the message. If comes from HOST,
	 *        then different queue will be used to send out.
	 * @param mode I - Inquiry / U - Update indicates transaction mode of the
	 *        message. Different queue will be used for Inquiry and Update.
	 * @param messageType
	 * @param messageId
	 * @param dataMap
	 * @return String the xml formed for sending
	 */
	public static String sendMessage(int messageInitiator, String mode, String messageType, String messageId,
			Map headerMap, Map dataMap, String[] queuePair) {
		try {
			//return processSendMessage(messageInitiator, mode, messageType, messageId, headerMap, dataMap, queuePair);
		}
		catch (Exception e) {
			logger.error("Error in sending message " + messageInitiator);
			logger.error("Error in sending message " + "\nmessageInitiator=" + messageInitiator + "\nmode=" + mode
					+ "\nmessageType=" + messageType + "\nmessageId= " + messageId);
		}
		return null;
	}

	/**
	 * This method is used for sending out messages. Asynchronous messages, does
	 * not need to wait for reply.
	 * @param messageInitiator IMQConstant.FROM_HOST / IMQConstant.FROM_GCMS,
	 *        indicates actual originator of the message. If comes from HOST,
	 *        then different queue will be used to send out.
	 * @param mode I - Inquiry / U - Update indicates transaction mode of the
	 *        message. Different queue will be used for Inquiry and Update.
	 * @param messageType
	 * @param messageId
	 * @param dataMap
	 * @return String the xml formed for sending
	 */
	public static String processSendMessage(int messageInitiator, String mode, String messageType, String messageId,
			Map headerMap, Map dataMap, String[] queuePair) {
		String returnedXML = null;
		OBLogMessage logMsg = null;

		messageType = messageType.toUpperCase();

		try {
			EAIMessage eaiMessage = new EAIMessage();

			// Check if is an error message
			if ((dataMap != null) && (null != dataMap.get(IEAIResponseConstant.RESPONSE_CODE_TAG))) {
				String errorMessage = (String) dataMap.get(IEAIResponseConstant.RESPONSE_MESSAGE_TAG);
				if ((errorMessage != null) && (errorMessage.length() > 40)) {
					errorMessage = errorMessage.substring(0, 40);
				}
				headerMap.put(IEAIHeaderConstant.EAIHDR_RESPONSE_CODE, dataMap
						.get(IEAIResponseConstant.RESPONSE_CODE_TAG));
				headerMap.put(IEAIHeaderConstant.EAIHDR_SYSTEM_STATUS, dataMap
						.get(IEAIResponseConstant.RESPONSE_CODE_TAG));
				headerMap.put(IEAIHeaderConstant.EAIHDR_ERROR_MESSAGE, errorMessage);
			}

			// sets EAIMessage Header
			EAIHeader eaiHeader = EAIHeaderHelper.prepareHeader(messageType, messageId, headerMap);
			eaiMessage.setMsgHeader(eaiHeader);

			// Eg sets MessageBody
			EAIBody eaiBody = EAIMessageBodyBuilderHelper.prepareMessage(messageType, messageId, dataMap);

			eaiMessage.setMsgBody(eaiBody);

			logger.debug("*** Begin Processing Message ID: [" + messageId + "]");

			// Try to get Sender CorrelationId , and set CorrelationId .
			if ((queuePair != null) && (queuePair.length > 2)) {
				eaiMessage.setCorrelationId(queuePair[2]);
			}

			returnedXML = delegateSend(eaiMessage);

			String outQueue = getOutQueue(queuePair, messageInitiator, mode);

			logger.debug("*** Send to Queue [" + outQueue + "]");

			MessageCenterStartup.getInstance().sendMessage(returnedXML, outQueue, eaiMessage.getCorrelationId());
		}
		catch (JMSException e) {
			StringWriter strwtr = new StringWriter();
			PrintWriter prt = new PrintWriter(strwtr);
			e.printStackTrace(prt);

			if (logMsg != null) {
				logMsg.setErrorStackTrace(strwtr.toString());
			}

			logger.error("JMSException in onMessage! ", e);
			Exception le = e.getLinkedException();
			if (le != null) {
				logger.error("Linked Exception is: ", le);
			}
		}
		catch (Throwable e) {
			StringWriter strwtr = new StringWriter();
			PrintWriter prt = new PrintWriter(strwtr);
			e.printStackTrace(prt);

			if (logMsg != null) {
				logMsg.setErrorStackTrace(strwtr.toString());
			}

			logger.error("Exception in onMessage! ", e);
		}

		return returnedXML;
	}

	private static String getOutQueue(String[] queuePair, int messageInitiator, String mode) throws Exception {
		String outQueue = null;
		if ((queuePair != null) && (queuePair.length >= 2)) {
			logger.debug("Message came from [" + queuePair[0] + "], returning thru [" + queuePair[1] + "]");
			outQueue = queuePair[1];
		}
		else {
			outQueue = getOutQueueFromMode(messageInitiator, mode);
		}
		return outQueue;
	}

	/**
	 * Delegate to specific handlers
	 */
	private static String delegateSend(EAIMessage eaiMessage) throws Exception {
		/*Remove the CMS Header from message according to the template*/
		//CMSHeader cmsHeader = eaiMessage.getMsgBody().getCMSHeader();
		String messageType = eaiMessage.getMsgHeader().getMessageType();
		String messageId = eaiMessage.getMsgHeader().getMessageId();
		String publishType = eaiMessage.getMsgHeader().getPublishType();

		logger.debug("Delegate to send Message, Message Id [" + messageId + "] Message Type [" + messageType + "]");

		Validate.notNull(messageId, "'meesageId' must not be null.");
		Validate.notNull(messageType, "'messageType' must not be null.");

		ClassLoader ctxClassLoader = Thread.currentThread().getContextClassLoader();

		String returnXML = "";
		if (messageType.equalsIgnoreCase(IEAIHeaderConstant.CUSTOMER_TYPE)) {
			try {
				if (IEAIHeaderConstant.CUSTOMER_CU002.equals(messageId)) {
					URL xmlMappingUrl = ctxClassLoader.getResource("customer-out.cm.xml");
					returnXML = EAICastorHelper.getInstance().marshal(xmlMappingUrl, eaiMessage, EAIMessage.class);
				}
			}
			catch (Exception e) {
				logger.error("Caught Exception in prepareCustomerMessage for message Id [" + messageId + "]", e);
			}

		}
		else if (messageType.equalsIgnoreCase(IEAIHeaderConstant.COLLATERAL_TYPE)) {
			try {
				if (IEAIHeaderConstant.COLLATERAL_CO005.equals(messageId)) {
					URL xmlMappingUrl = ctxClassLoader.getResource("collateral-sharedvalidation.xml");
					returnXML = EAICastorHelper.getInstance().marshal(xmlMappingUrl, eaiMessage, EAIMessage.class);
				}
			}
			catch (Exception e) {
				logger.error("Caught Exception in prepareCustomerMessage for message [" + messageId + "]", e);
			}
		}
		else if (messageType.equalsIgnoreCase(IEAIHeaderConstant.RESPONSE_TYPE)) {
			try {
				if (IEAIHeaderConstant.RESPONSE_AC001.equals(messageId)) {
					URL xmlMappingUrl = ctxClassLoader.getResource("response.cm.xml");
					returnXML = EAICastorHelper.getInstance().marshal(xmlMappingUrl, eaiMessage, EAIMessage.class);
				}
			}
			catch (Exception e) {
				logger.error("Caught Exception in prepareCustomerMessage for message [" + messageId + "]", e);
			}

		}
		else {
			throw new IllegalStateException("Unknown message, messageId [" + messageId + "] messageType ["
					+ messageType + "] and publishType [" + publishType + "]");
		}

		logger.debug("Message Sent : Message Type [" + messageType + "] Message Id [" + messageId + "].");

		return returnXML;
	}

	private static String getOutQueueFromMode(int initiator, String mode) throws Exception {
		switch (initiator) {
		case FROM_GCMS:
			if (INQUIRY_TYPE.equals(mode)) {
				return SEND_INQUIRY_OUT;
			}
			else if (UPDATE_TYPE.equals(mode)) {
				return SEND_UPDATE_OUT;
			}
			break;
		case FROM_HOST:
			if (INQUIRY_TYPE.equals(mode)) {
				return FROM_HOST_INQUIRY_OUT;
			}
			else if (UPDATE_TYPE.equals(mode)) {
				return FROM_HOST_UPDATE_OUT;
			}
			break;

		default:
			throw new Exception("OutQueue - Message Mode [I/U] not defined");
		}

		return null;
	}

}