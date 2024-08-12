package com.integrosys.cms.host.eai.support;

/**
 * <p>
 * Message holder to hold a message backed by a Message Id. This message holder
 * shall then put into a queue, to be processed further.
 * 
 * <p>
 * If the message folder to all the message holder is using file system,
 * consider to provide message description to populate the full file path of the
 * message object, which can be used in later processing, such as to store the
 * file path for the message object.
 * 
 * @author Chong Jun Yong
 * @since 25.08.2008
 */
public final class MessageHolder {
	private final Object message;

	private final String msgId;

	private final String messageDescription;

	/**
	 * <p>
	 * Default constructer to create a new instance with the message object,
	 * message id and message description supplied.
	 * 
	 * <p>
	 * Normally the message description will be the file name depend on the
	 * message folder using it.
	 * 
	 * @param message the message object itself to be processed
	 * @param msgId the message id for the message object, which supposed to be
	 *        the key for this holder.
	 * @param messageDescription the message description, in some case whereby
	 *        if the message folder is using file system, this can be the full
	 *        file path of the message object
	 */
	public MessageHolder(Object message, String msgId, String messageDescription) {
		this.message = message;
		this.msgId = msgId;
		this.messageDescription = messageDescription;
	}

	public Object getMessage() {
		return message;
	}

	public String getMsgId() {
		return msgId;
	}

	public String getMessageDescription() {
		return messageDescription;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((messageDescription == null) ? 0 : messageDescription.hashCode());
		result = prime * result + ((msgId == null) ? 0 : msgId.hashCode());

		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		MessageHolder other = (MessageHolder) obj;
		if (messageDescription == null) {
			if (other.messageDescription != null) {
				return false;
			}
		}
		else if (!messageDescription.equals(other.messageDescription)) {
			return false;
		}

		if (msgId == null) {
			if (other.msgId != null) {
				return false;
			}
		}
		else if (!msgId.equals(other.msgId)) {
			return false;
		}

		return true;
	}

}