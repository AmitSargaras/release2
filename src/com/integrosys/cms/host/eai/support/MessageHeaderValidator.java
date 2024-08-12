package com.integrosys.cms.host.eai.support;

import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.host.eai.EAIHeader;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.FieldValueNotAllowedException;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;

/**
 * Message Header validator, mainly to validate the message id given whether is
 * belong to the message type provided, and also the source id of the message.
 * 
 * @author Chong Jun Yong
 * 
 */
public class MessageHeaderValidator implements IEaiMessageValidator {

	private Map messageTypeMessageIdsMap;

	private String[] validSourceIds;

	public Map getMessageTypeMessageIdsMap() {
		return messageTypeMessageIdsMap;
	}

	/**
	 * <p>
	 * The map between valid message type and message ids. Meaning the message
	 * id in the message header must belong to the message type based on this
	 * map.
	 * <p>
	 * The key is the message type, the value is a list of message id belong to
	 * the message type
	 * 
	 * @param messageTypeMessageIdsMap key is message type, value is list of
	 *        message id (in string array format)
	 */
	public void setMessageTypeMessageIdsMap(Map messageTypeMessageIdsMap) {
		this.messageTypeMessageIdsMap = messageTypeMessageIdsMap;
	}

	/**
	 * Valid source ids from the LOS system, or any other source system.
	 * @param validSourceIds valid source ids in array
	 */
	public void setValidSourceIds(String[] validSourceIds) {
		this.validSourceIds = validSourceIds;
	}

	public String[] getValidSourceIds() {
		return validSourceIds;
	}

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		EAIHeader header = scimsg.getMsgHeader();
		String messageType = header.getMessageType();
		String messageId = header.getMessageId();
		String sourceId = header.getSource();

		String[] messageIds = (String[]) getMessageTypeMessageIdsMap().get(messageType);
		if (!ArrayUtils.contains(messageIds, messageId)) {
			throw new MessageTypeMessageIdMismatchException(messageType, messageId);
		}

		if (!ArrayUtils.contains(getValidSourceIds(), sourceId)) {
			throw new FieldValueNotAllowedException("MsgHeader - Source", sourceId, getValidSourceIds());
		}
	}

	/**
	 * Exception to be raised when message id provided is not belong to the
	 * message type provided
	 * 
	 */
	class MessageTypeMessageIdMismatchException extends EAIMessageValidationException {

		private static final long serialVersionUID = -1071267089260820429L;

		private static final String ERROR_CODE_WRONG_MESSAGE_ID = "INV_MSG_ID";

		/**
		 * Default constructor to provide message id and message type
		 * 
		 * @param messageType the message type
		 * @param messageId the message id
		 */
		public MessageTypeMessageIdMismatchException(String messageType, String messageId) {
			super("Message Id provided [" + messageId + "] is not belong to the Message Type provided [" + messageType
					+ "]");
		}

		public String getErrorCode() {
			return ERROR_CODE_WRONG_MESSAGE_ID;
		}
	}
}
