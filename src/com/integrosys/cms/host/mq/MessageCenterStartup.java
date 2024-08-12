package com.integrosys.cms.host.mq;

import java.util.HashMap;
import java.util.Iterator;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;

import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.reloadable.IReloadable;
import com.integrosys.base.techinfra.reloadable.ReloadException;
import com.integrosys.base.techinfra.reloadable.ReloadManager;
import com.integrosys.base.techinfra.startup.IStartupable;
import com.integrosys.base.techinfra.util.PropertyUtil;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class MessageCenterStartup implements IStartupable, IReloadable {

	// Used as holder for EAIMessenger to send message
	private static MessageCenterStartup instance = null;

	protected static final String[] MQ_IN_QUEUE_KEYS = { "mq.enquiry.gcms.in.queue", "mq.update.gcms.in.queue",
			"mq.enquiry.source.in.queue", "mq.update.source.in.queue" };

	protected static final String[] MQ_OUT_QUEUE_KEYS = { "mq.enquiry.gcms.out.queue", "mq.update.gcms.out.queue",
			"mq.enquiry.source.out.queue", "mq.update.source.out.queue" };

	// AT:
	// Message Type
	public final static String MQ_ENQUIRY = "inquiry";

	public final static String MQ_UPDATE = "update";

	private final static String MQ_KEYS = "mq.pair";

	private int ccsid = 0;

	private int port = 0;

	// private String _inQueueStr = null;
	// private String _outQueueStr = null;

	private String queueMgrStr = null;

	private String hostIPStr = null;

	private String inChannelStr = null;

	private String outChannelStr = null;

	private String qInquiryIn = null;

	private String qInquiryOut = null;

	private String qUpdateIn = null;

	private String qUpdateOut = null;

	private boolean reconnect = true; // by default

	private boolean remote = false; // by default its local

	private HashMap listeningQueues = new HashMap();

	MQQueueConnectionFactory factory = null;

	QueueConnection con = null;

	QueueSession session = null;

	// Queue inQueue = null;
	QueueReceiver[] qReceivers = null;

	EAIExceptionListener outExListner = null;

	/**
	 * Default Constructor
	 */
	public MessageCenterStartup() {
	}

	private static HashMap mqListenerMap = new HashMap();

	/**
	 * Initialization method
	 */
	public int startup(PropertyUtil prop) {
		DefaultLogger.debug(this, ">>>> in startup <<<<");
		DefaultLogger.debug(this, "Please ensure that the native libraries are placed in the following directories: ");
		DefaultLogger.debug(this, System.getProperty("java.library.path") + "\n");

		ccsid = PropertyManager.getInt("mq.ccsid");
		port = PropertyManager.getInt("mq.port");

		queueMgrStr = PropertyManager.getValue("mq.queue.manager");
		hostIPStr = PropertyManager.getValue("mq.host.ip");
		inChannelStr = PropertyManager.getValue("mq.in.channel.name");
		outChannelStr = PropertyManager.getValue("mq.out.channel.name");
		remote = PropertyManager.getBoolean("mq.server.isremote");

		DefaultLogger.debug(this, "CCSID       : " + ccsid);
		DefaultLogger.debug(this, "queueMgrStr : " + queueMgrStr);
		DefaultLogger.debug(this, "hostIPStr   : " + hostIPStr);
		DefaultLogger.debug(this, "port        : " + port);
		DefaultLogger.debug(this, "is remote?  : " + remote);

		qInquiryIn = StringUtils.defaultString(PropertyManager.getValue("mq.enquiry.in.queue"), PropertyManager
				.getValue("mq.in.queue"));
		qInquiryOut = StringUtils.defaultString(PropertyManager.getValue("mq.enquiry.out.queue"), PropertyManager
				.getValue("mq.out.queue"));
		qUpdateIn = PropertyManager.getValue("mq.update.in.queue");
		qUpdateOut = PropertyManager.getValue("mq.enquiry.out.queue");

		DefaultLogger.debug(this, "Remote: " + remote);
		if (null == queueMgrStr) {
			throw new RuntimeException("Queue Manager is null!");
		}

		// Cater for maximum 4 listener
		for (int i = 0; i < MQ_IN_QUEUE_KEYS.length; i++) {
			String inKey = MQ_IN_QUEUE_KEYS[i];
			String outKey = MQ_OUT_QUEUE_KEYS[i];
			String inQueueStr = PropertyManager.getValue(inKey);

			// If key not found , continue
			if ((inQueueStr == null) || "".equals(inQueueStr)) {
				continue;
			}

			String outQueueStr = PropertyManager.getValue(outKey);

			DefaultLogger.debug(this, "Queue : " + inKey + " IN :" + inQueueStr + " OUT :" + outQueueStr);
			mqListenerMap.put(inKey, new MQPair(inQueueStr, outQueueStr));
		}
		DefaultLogger.debug(this, "mqListenerMap size " + mqListenerMap.size());

		// Use Default In & Out Queue
		if (mqListenerMap.size() == 0) {
			mqListenerMap.put("DEFAULT", new MQPair(PropertyManager.getValue("mq.in.queue"), PropertyManager
					.getValue("mq.out.queue")));
		}

		/*
		 * if(null == inQueueStr) { throw new
		 * RuntimeException("In Queue is null!"); } if(null == outQueueStr) {
		 * throw new RuntimeException("Out Queue is null!"); }
		 */

		if (null == hostIPStr) {
			throw new RuntimeException("Host IP is null!");
		}
		/*
		 * if(null == inChannelStr) { throw new
		 * RuntimeException("In Channel is null!"); } if(null == outChannelStr)
		 * { throw new RuntimeException("Out Channel is null!"); }
		 */
		try {
			connect();
			instance = this;
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Caught Exception in startup! ", e);
		}
		finally {
			try {
				ReloadManager.register(this);
			}
			catch (Exception ee) {
				ee.printStackTrace();
				DefaultLogger.error(this, "Caught Exception in startup! ", ee);
				throw new RuntimeException("Caught Exception in startup! " + ee.toString());
			}
			finally {
				DefaultLogger.debug(this, "in finally clause for ReloadManager.register");
			}
		}
		DefaultLogger.debug(this, "Successful connection, return 0");
		return 0;
	}

	/**
	 * Before clearing cache this method would be invoked.
	 */
	public void clearCache() {
	}

	/*
	 * Reloads
	 * 
	 * @param void
	 * 
	 * @return void
	 * 
	 * @exception ReloadException
	 */
	public void reload() throws ReloadException {
		try {
			if (true == reconnect) {
				DefaultLogger.debug(this, ">>>> in reload <<<<");
				disconnect();
				connect();
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in reload!", e);
			// consume
		}
	}

	/*
	 * Reloads time interval
	 * 
	 * @param currentTime
	 * 
	 * @return next execution time
	 * 
	 * @exception exception
	 */
	public long schedule(long currentTime) throws Exception {
		return (currentTime + 1000); // todo: schedule obained from properties
	}

	/**
	 * Set reconnect flag
	 */
	public void setReconnect(boolean value) {
		reconnect = value;
	}

	/**
	 * Get reconnect flag
	 */
	public boolean getReconnect() {
		return reconnect;
	}

	/**
	 * Method to stop the message listener
	 */
	public void stopListener() {
		try {
			for (int i = 0; i < qReceivers.length; i++) {
				qReceivers[i].setMessageListener(null);
			}

			reconnect = false;
			disconnect();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in stopListener! ", e);
		}
	}

	public String getOutQueueStr(String listenerKey) {
		return null;
	}

	public void sendMessage(String msg) throws Exception {

		// default outQueue
		sendMessage(msg, qInquiryOut);
	}

	public void sendMessage(String msg, String outQueueStr) throws Exception {
		sendMessage(msg, outQueueStr, null);
	}

	/**
	 * Method to send message to default out queue
	 */
	public void sendMessage(String msg, String outQueueStr, String correlationId) throws Exception {

		DefaultLogger.debug(this, "--- IN SEND Creating");
		MQQueueConnectionFactory factory = new MQQueueConnectionFactory();
		factory.setCCSID(ccsid); // default
		factory.setQueueManager(queueMgrStr);
		factory.setHostName(hostIPStr);
		factory.setPort(port); // default
		// if ( outChannelStr != null )
		// factory.setChannel(outChannelStr);

		if (remote) {
			factory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
		}
		else {
			factory.setTransportType(JMSC.MQJMS_TP_BINDINGS_MQ);
		}

		DefaultLogger.debug(this, "Creating Q Connection");
		QueueConnection con = factory.createQueueConnection();
		if (outExListner == null) {
			outExListner = new EAIExceptionListener();
		}
		DefaultLogger.debug(this, "Capture out exception listener");
		con.setExceptionListener(outExListner);
		DefaultLogger.debug(this, "Start connection");
		con.start();
		DefaultLogger.debug(this, "Connection started, create new session");
		QueueSession session = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

		DefaultLogger.debug(this, "Creating outQueue");
		Queue outQueue = session.createQueue(outQueueStr);

		((MQQueue) outQueue).setTargetClient(JMSC.MQJMS_CLIENT_NONJMS_MQ);
		DefaultLogger.debug(this, "Creating sender");
		QueueSender sender = session.createSender(outQueue);
		DefaultLogger.debug(this, "Message created");
		TextMessage txtMsg = session.createTextMessage(msg);

		// Set CorrelationId , if correlationId not empty .
		if (StringUtils.isNotEmpty(correlationId)) {
			txtMsg.setJMSCorrelationID(correlationId);
		}

		DefaultLogger.debug(this, "Sending message");
		sender.send(txtMsg);
		DefaultLogger.debug(this, "Message sent!!!");

		try {
			if (sender != null) {
				sender.close();
				sender = null;
			}
		}
		catch (Exception e) {
			// consume
		}
		try {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		catch (Exception e) {
			// consume
		}
		try {
			if (con != null) {
				con.stop();
				con.close();
				con = null;
			}
		}
		catch (Exception e) {
			// consume
		}
	}

	/**
	 * Method to disconnect
	 */
	private void disconnect() {
		try {
			for (int i = 0; i < qReceivers.length; i++) {
				if (qReceivers[i] != null) {
					qReceivers[i].close();
					qReceivers[i] = null;
				}
			}
		}
		catch (Exception e) {
			// consume
		}
		try {
			if (session != null) {
				session.close();
				session = null;
			}
		}
		catch (Exception e) {
			// consume
		}
		try {
			if (con != null) {
				con.stop();
				con.close();
				con = null;
			}
		}
		catch (Exception e) {
			// consume
		}
	}

	/**
	 * Method to connect queues
	 */
	private void connect() {

		MQQueueConnectionFactory factory = null;
		QueueConnection con = null;
		QueueSession session = null;
		DefaultLogger.debug(this, "In MessageCenterStartup connect \n CCSID       : " + ccsid + "\n queueMgrStr : "
				+ queueMgrStr + "\n hostIPStr   : " + hostIPStr + "\n port        : " + port + "\n is remote?  : "
				+ remote);

		try {
			// TODO: create multiple listener
			//
			Iterator ii = this.mqListenerMap.values().iterator();
			DefaultLogger.debug(this, "Iterating mqListenerMap");
			while (ii.hasNext()) {

				DefaultLogger.debug(this, "Create new MQQueueConnectionFactory()");
				factory = new MQQueueConnectionFactory();
				factory.setCCSID(ccsid); // default
				factory.setQueueManager(queueMgrStr);
				factory.setHostName(hostIPStr);
				factory.setPort(port); // default

				if (remote) {
					factory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP); 
					DefaultLogger.debug(this, "factory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP)");
				}
				else {
					factory.setTransportType(JMSC.MQJMS_TP_BINDINGS_MQ);
					DefaultLogger.debug(this, "factory.setTransportType(JMSC.MQJMS_TP_BINDINGS_MQ)");
				}

				DefaultLogger.debug(this, "Creating Queue Connection");
				con = factory.createQueueConnection();
				DefaultLogger.debug(this, "Setting exception Listener");
				con.setExceptionListener(new EAIExceptionListener(this));
				DefaultLogger.debug(this, "Starting connection");
				con.start();
				DefaultLogger.debug(this, "Connection Started!! - create session");
				session = con.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE); 
				DefaultLogger.debug(this, "Session Created!");

				MQPair mqp = (MQPair) ii.next();
				DefaultLogger.debug(this, "Inqueue : " + mqp.getInQueue());
				String inQueueString = mqp.getInQueue();
				String outQueueString = mqp.getOutQueue();
				Queue inQueue = session.createQueue(inQueueString);

				DefaultLogger.debug(this, "In Queue Name: " + inQueueString);
				DefaultLogger.debug(this, "Out Queue Name: " + outQueueString);

				try {
					if (!listeningQueues.containsKey(inQueueString)) {
						DefaultLogger.debug(this, "Creating receiver");
						QueueReceiver qReceiver = session.createReceiver(inQueue);
						DefaultLogger.debug(this, "Setting listener to receiver");
						qReceiver.setMessageListener(new EAIMessageListener(this, mqp.getInQueue(), mqp.getOutQueue()));
						DefaultLogger.debug(this, "Listener set for inQ     : " + inQueue);
						listeningQueues.put(inQueueString, inQueueString);

						// DefaultLogger.debug(this,
						// "Test put message to outQ : " + outQueueString);
						// String testMessage = "MQ Test message from GCMS";
						// sendMessage(testMessage, outQueueString);
					}
					else {
						DefaultLogger.debug(this, "Listener for " + inQueue + " is already started. Skip..");
					}
				}
				catch (Exception e) {
					DefaultLogger.debug(this, "Exception found creating receiver..");
					e.printStackTrace();
				}

			}// end while
			DefaultLogger.debug(this, "Done in connecting!!");
			if (listeningQueues.size() == MQ_IN_QUEUE_KEYS.length) {
				reconnect = false;
				DefaultLogger.debug(this, "All listeners are ready.. ");
			}
			else {
				reconnect = true;
				DefaultLogger.debug(this, "Some listeners not ready, schedule reconnection..");
			}
		}
		catch (Throwable t) {
			reconnect = true;
//			System.out.println("IN Throwable 1" + t.getMessage());
//			System.out.println("IN Throwable 2" + t.toString());
			t.printStackTrace();

		}
	}

	private class MQPair {

		MQPair(String in, String out) {
			inQueue = in;
			outQueue = out;
		}

		private String inQueue;

		private String outQueue;

		public final String getInQueue() {
			return inQueue;
		}

		public final void setInQueue(String inQueue) {
			this.inQueue = inQueue;
		}

		public final String getOutQueue() {
			return outQueue;
		}

		public final void setOutQueue(String outQueue) {
			this.outQueue = outQueue;
		}

	}

	public static MessageCenterStartup getInstance() {
		if (instance == null) {
			synchronized (MessageCenterStartup.class) {
				if (instance == null) {
					instance = new MessageCenterStartup();
				}
			}
		}
		return instance;
	}
}
