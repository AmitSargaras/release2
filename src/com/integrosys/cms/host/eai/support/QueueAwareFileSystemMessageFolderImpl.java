package com.integrosys.cms.host.eai.support;

import java.util.LinkedList;
import java.util.List;

/**
 * Variation of <tt>FileSystemMessageFolderImpl</tt> which the message id are
 * stored in a queue, and get processed one by one rather than based on the
 * message id passed from caller.
 * 
 * @author Chong Jun Yong
 * 
 */
public class QueueAwareFileSystemMessageFolderImpl extends FileSystemMessageFolderImpl {

	private List messageIdQueue = new LinkedList();

	/** Monitor for message id queue */
	private final Object queueMonitor = new Object();

	public Object popMessageByMsgId(String msgId) {
		throw new IllegalStateException("not available for queue aware message folder. please use #popMessage()");
	}

	public String putMessage(Object message) {
		String msgId = null;
		synchronized (queueMonitor) {
			msgId = super.putMessage(message);
			messageIdQueue.add(msgId);
		}

		return msgId;
	}

	public Object popMessage() {
		synchronized (queueMonitor) {
			if (!messageIdQueue.isEmpty()) {
				String msgId = (String) messageIdQueue.remove(0);
				return super.popMessageByMsgId(msgId);
			}
		}

		return null;
	}

}
