package com.integrosys.cms.host.eai.support;

import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 * Message Folder to hold Message Holder in a Map where key are linked together.
 * This should act like a singleton class, which might interfaced by subcriber
 * and subject. So, subcriber able to let subject know which message holder that
 * it is passing.
 * 
 * @author Chong Jun Yong
 * @since 25.08.2008
 * 
 */
public class MemoryMessageFolderImpl extends AbstractMessageFolder {

	/**
	 * retrieve the first message holder and remove from the folder as well.
	 * 
	 * @return the first message holder object, else <code>null</code> if there
	 *         is no object in the folder.
	 */
	public Object popMessage() {
		if (msgIdHolderMap.isEmpty()) {
			return null;
		}

		Set keySet = msgIdHolderMap.keySet();
		Iterator keyItr = keySet.iterator();
		String firstKeyMsgId = (String) keyItr.next();

		return msgIdHolderMap.remove(firstKeyMsgId);
	}

	/**
	 * Put the message into a holder and put into the first 'page' of this
	 * folder
	 * 
	 * @param message message object to be put into this folder, it will first
	 *        put into a message holder
	 * @return the message Id of the message supplied
	 */
	public String putMessage(Object message) {

		String msgId = getNextMessageId();

		MessageHolder messageHolder = new MessageHolder(message, msgId, msgId);
		msgIdHolderMap.put(msgId, messageHolder);

		return msgId;
	}

	public Object popMessageByMsgId(String msgId) {
		return msgIdHolderMap.remove(msgId);
	}

}