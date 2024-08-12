package com.integrosys.cms.host.eai.service;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.support.EAIHeaderHelper;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.eai.support.IMessageFolder;
import com.integrosys.cms.host.eai.support.MessageHandlerObserver;
import com.integrosys.cms.host.eai.support.MessageHandlerSubject;
import com.integrosys.cms.host.eai.support.MessageHolder;

/**
 * <p>
 * A Listener for taking message for processing from the message folder.
 * <p>
 * Message taken from the meessage folder will be routed to
 * <tt>MessageHandler</tt> for further process. This listener act as a
 * <tt>MessageHandlerSubject</tt> which will update the observer (normally the
 * one subscribe to this subject), after the further process.
 * @author Chong Jun Yong
 * 
 */
public class MessageQueueFiringTask implements IEAIHeaderConstant, MessageHandlerSubject, Runnable {

	private final Logger logger = LoggerFactory.getLogger(MessageQueueFiringTask.class);

	/**
	 * key is the message id, value is the observer
	 */
	private Map msgIdObserverMap = Collections.synchronizedMap(new Hashtable());

	private boolean isRunning = false;

	private Object runningMonitor = new Object();

	private int sleepSecondsAfterEachRun = -1;

	private MessageHandler messageHandler;

	private IMessageFolder messageFolder;

	private IEaiMessageManager eaiMessageManager;

	/**
	 * The time in second for the current thread to sleep after a run of message
	 * process (for a single message)
	 * @param sleepSecondsAfterEachRun time for current thread to sleep (in
	 *        second)
	 */
	public void setSleepSecondsAfterEachRun(int sleepSecondsAfterEachRun) {
		this.sleepSecondsAfterEachRun = sleepSecondsAfterEachRun;
	}

	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	public void setMessageFolder(IMessageFolder messageFolder) {
		this.messageFolder = messageFolder;
	}

	public void setEaiMessageManager(IEaiMessageManager eaiMessageManager) {
		this.eaiMessageManager = eaiMessageManager;
	}

	public void register(String msgId, MessageHandlerObserver observer) {
		msgIdObserverMap.put(msgId, observer);
	}

	public void registerAndProcess(String msgId, MessageHandlerObserver observer) {
		throw new IllegalStateException("#registerAndProcess is not supported by Queue aware message handler");
	}

	public void run() {
		if (this.isRunning) {
			return;
		}

		synchronized (runningMonitor) {
			isRunning = true;
		}

		MessageHolder messageHolder = (MessageHolder) this.messageFolder.popMessage();
		if (messageHolder == null) {
			logger.warn("there is no message holder this round");

			sleepAndSuppressInterruption();
			synchronized (runningMonitor) {
				isRunning = false;
			}
			return;
		}

		String xmlMessage = (String) messageHolder.getMessage();
		String msgId = messageHolder.getMsgId();

		MessageHandlerObserver observer = (MessageHandlerObserver) this.msgIdObserverMap.get(msgId);
		if (observer == null) {
			logger.error("There is no observer subscribe to this subject, for msgId [" + msgId + "], message path ["
					+ messageHolder.getMessageDescription() + "], will discontinue the process.");

			synchronized (runningMonitor) {
				isRunning = false;
			}
			sleepAndSuppressInterruption();
			return;
		}

		logger.debug("Processing msg internally, msg Id [" + msgId + "]");
		String currentThreadName = Thread.currentThread().getName();

		String messageReference = EAIHeaderHelper.getHeaderValue(xmlMessage, IEAIHeaderConstant.MESSAGE_REF_NUM);
		Thread.currentThread().setName("MessageProcess_" + messageReference);
		Message returnedMsg = messageHandler.process(xmlMessage);
		Thread.currentThread().setName(currentThreadName);

		this.eaiMessageManager.logMessage((EAIMessage) returnedMsg, xmlMessage, messageHolder.getMessageDescription());

		observer.update(returnedMsg);
		msgIdObserverMap.remove(msgId);
		logger.info("removing msg observer [" + observer + "], msgId [" + msgId + "]");

		sleepAndSuppressInterruption();

		synchronized (runningMonitor) {
			isRunning = false;
		}
	}

	private void sleepAndSuppressInterruption() {
		if (this.sleepSecondsAfterEachRun > -1) {
			try {
				Thread.sleep(this.sleepSecondsAfterEachRun * 1000);
			}
			catch (InterruptedException ex) {
				// ignored
			}
		}
	}

}
