package com.integrosys.cms.host.mq;

import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

import javax.jms.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class MQTester {

    /*
      * IMPORTANT: converted from SELECTOR_VALUE. If selector value is change,
      * please replace this field with a hexa representation too.
      */
    public static final String SELECTOR_VALUE_CONVERTED = "ID:494e544547524f5359532e4d515445535445522e53454c45";

    private static final String SELECTOR_VALUE = "INTEGROSYS.MQTESTER.SELECTOR.VALUE";

    private static final String SELECTOR_PREFIX = "JMSCorrelationID";

    public static final String SELECTOR = SELECTOR_PREFIX + " = '" + SELECTOR_VALUE_CONVERTED + "'";

    private static final String SYNTAX = "Usage: MQTester put <filename> or MQTester get or MQTester stop";

    private String _outQueueStr = null;

    private String _queueMgrStr = null;

    private String _hostIPStr = null;

    private String _channelStr = null;

    private String _message = null;

    private boolean remote = false;

    MQQueueConnectionFactory _factory = null;

    QueueConnection _con = null;

    QueueSession _session = null;

    Queue _outQueue = null;

    QueueSender _qSender = null;

    public static void main(String argv[]) throws Exception {
        Properties properties = PropertyManager.getProperty();
        Properties prop = new Properties();
        URL fileNameUrl = MQTester.class.getResource("/mq.properties");
        prop.load(fileNameUrl.openStream());
        properties.putAll(prop);

        String action = null;
        if (null == argv) {
            System.out.println(SYNTAX);
            System.exit(1);
        } else if (argv.length == 1) {
            action = argv[0];
            if (action.equalsIgnoreCase("get")) {
                MQTester tester = new MQTester();
                String msg = tester.getMessage();
                System.out.println("Message Read:");
                System.out.println(msg);
                System.exit(0);
            } else if (action.equalsIgnoreCase("stop")) {
                boolean stop = doStop();
                if (true == stop) {
                    System.out
                            .println("\n\nThe MQListener has been stopped. You will have to restart the AppServer for it to start again.");
                } else {
                    System.out
                            .println("\n\nUnable to stop MQListener as the Listener is not responding. Please try again.");
                }
                System.exit(0);
            } else {
                System.out.println(SYNTAX);
                System.exit(1);
            }
        } else if (argv.length == 2) {
            action = argv[0];
            if (action.equalsIgnoreCase("put")) {
//				String fileName = argv[1];
//				File f = new File(fileName);
//				if (f.exists() && f.isFile()) {
//					BufferedReader br = new BufferedReader(new FileReader(f));

//					StringBuffer buf = new StringBuffer();
//					String str = null;
//					while ((str = br.readLine()) != null) {
//						buf.append(str);
//						buf.append("\n");
//					}
                MQTester tester = new MQTester(argv[1]);
                tester.connect();
                tester.disconnect();
                System.exit(0);
//				}
//				else {
//					System.out.println(fileName + " is not a valid file!");
//					System.exit(1);
//				}
            } else {
                System.out.println(SYNTAX);
                System.exit(1);
            }
        } else {
            System.out.println(SYNTAX);
            System.exit(1);
        }
    }

    private static boolean doStop() throws Exception {
        MQTester tester = new MQTester();
        tester.sendStop();
        boolean flag = tester.processStop();
        return flag;
    }

    /**
     * Default Constructor
     */
    public MQTester() {
        DefaultLogger.debug(this, "Initiating MQTester..."); // need this to
        // initialise
        // property
        // manager
    }

    /**
     * Default Constructor
     */
    public MQTester(String message) {
        DefaultLogger.debug(this, "Initiating MQTester..."); // need this to
        // initialise
        // property
        // manager
        DefaultLogger.debug(this, "Please ensure that the native libraries are placed in the following directories: ");
        DefaultLogger.debug(this, System.getProperty("java.library.path") + "\n");

        _message = message;
        DefaultLogger.debug(this, "Message Content: " + _message);

        _outQueueStr = PropertyManager.getValue("mq.in.queue");
        _queueMgrStr = PropertyManager.getValue("mq.queue.manager");
        _hostIPStr = PropertyManager.getValue("mq.host.ip");
        _channelStr = PropertyManager.getValue("mq.in.channel.name");
        remote = PropertyManager.getBoolean("mq.server.isremote");

        if (null == _queueMgrStr) {
            throw new RuntimeException("Queue Manager is null!");
        }
        if (null == _outQueueStr) {
            throw new RuntimeException("Out Queue is null!");
        }
        if (remote && null == _hostIPStr) {
            throw new RuntimeException("Host IP is null!");
        }
        if (remote && null == _channelStr) {
            throw new RuntimeException("Channel is null!");
        }
    }

    /**
     * Method to stop message listener
     */
    public void sendStop() throws Exception {
        DefaultLogger.debug(this, "Initiating Stop sequence...");

        _outQueueStr = PropertyManager.getValue("mq.in.queue");
        _queueMgrStr = PropertyManager.getValue("mq.queue.manager");
        _hostIPStr = PropertyManager.getValue("mq.host.ip");
        _channelStr = PropertyManager.getValue("mq.in.channel.name");

        if (null == _queueMgrStr) {
            throw new RuntimeException("Queue Manager is null!");
        }
        if (null == _outQueueStr) {
            throw new RuntimeException("Out Queue is null!");
        }
        if (remote && null == _hostIPStr) {
            throw new RuntimeException("Host IP is null!");
        }
        /*
           * if(null == _channelStr) { throw new
           * RuntimeException("Channel is null!"); }
           */

        DefaultLogger.debug(this, "OUT Queue: " + _outQueueStr);
        DefaultLogger.debug(this, "Queue Manager: " + _queueMgrStr);
        DefaultLogger.debug(this, "HostIP: " + _hostIPStr);
        DefaultLogger.debug(this, "Channel Name: " + _channelStr);

        try {
            _factory = new MQQueueConnectionFactory();
            _factory.setCCSID(PropertyManager.getInt("mq.ccsid")); // default
            _factory.setQueueManager(_queueMgrStr);
            _factory.setHostName(_hostIPStr);
            _factory.setPort(PropertyManager.getInt("mq.port")); // default
            /*
                * if ( _channelStr != null ) factory.setChannel(_channelStr);
                */
            if (remote) {
                _factory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
            } else {
                _factory.setTransportType(JMSC.MQJMS_TP_BINDINGS_MQ);
            }

            _con = _factory.createQueueConnection();
            _con.setExceptionListener(new EAIExceptionListener());
            _con.start();
            _session = _con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            _outQueue = _session.createQueue(_outQueueStr);
            ((MQQueue) _outQueue).setTargetClient(JMSC.MQJMS_CLIENT_NONJMS_MQ);
            _qSender = _session.createSender(_outQueue);

            TextMessage msg = _session.createTextMessage("Stopping MessageListener...");
            msg.setJMSCorrelationID(SELECTOR_VALUE); // using this field to
            // identify a stop
            // message

            _qSender.send(msg);
        }
        catch (Exception e) {
            DefaultLogger.error(this, "Caught Exception in connect! ", e);
            throw new RuntimeException("Caught Exception in connect! " + e.toString());
        }
        finally {
            disconnect();
        }
    }

    private boolean processStop() throws Exception {
        QueueBrowser browser = null;
        QueueReceiver receiver = null;
        try {
            _factory = new MQQueueConnectionFactory();
            _factory.setCCSID(PropertyManager.getInt("mq.ccsid")); // default
            _factory.setQueueManager(_queueMgrStr);
            _factory.setHostName(_hostIPStr);
            _factory.setPort(PropertyManager.getInt("mq.port")); // default
            /*
                * if ( _channelStr != null ) factory.setChannel(_channelStr);
                */
            if (remote) {
                _factory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
            } else {
                _factory.setTransportType(JMSC.MQJMS_TP_BINDINGS_MQ);
            }

            _con = _factory.createQueueConnection();
            _con.setExceptionListener(new EAIExceptionListener());
            _con.start();
            _session = _con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            _outQueue = _session.createQueue(_outQueueStr);
            ((MQQueue) _outQueue).setTargetClient(JMSC.MQJMS_CLIENT_NONJMS_MQ);

            browser = _session.createBrowser(_outQueue, SELECTOR);
            // boolean quit = false;
            int counter = 10;

            while (true) {
                Enumeration enumeration = browser.getEnumeration();
                /*
                     * while(enum.hasMoreElements()) { System.out.println("ENUM: " +
                     * enum.nextElement()); } try { QueueReceiver r =
                     * session.createReceiver(_outQueue); Message m =
                     * r.receiveNoWait(); System.out.println(">>>>> jms: " +
                     * m.getJMSCorrelationID()); } catch(Exception ex) {
                     * ex.printStackTrace(); }
                     */
                if (enumeration.hasMoreElements()) {
                    counter--;
                    Thread.sleep(1000); // pause 1 second
                    if (counter <= 0) {
                        // try to consume the stop message
                        receiver = _session.createReceiver(_outQueue, SELECTOR);
                        try {
                            receiver.receiveNoWait();
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        finally {
                            return false; // unable to stop server
                        }
                    }
                    continue;
                } else { // no such message. stop request has been received.
                    break;
                }
            }
            // end
            return true; //
        }
        catch (Exception e) {
            DefaultLogger.error(this, "Caught Exception in connect! ", e);
            throw new RuntimeException("Caught Exception in connect! " + e.toString());
        }
        finally {
            try {
                if (browser != null) {
                    browser.close();
                    browser = null;
                }
            }
            catch (Exception e) {
                // consume
            }
            try {
                if (receiver != null) {
                    receiver.close();
                    receiver = null;
                }
            }
            catch (Exception e) {
                // consume
            }
            disconnect();
        }
    }

    /**
     * Method to read from queue
     */
    public String getMessage() throws Exception {
        DefaultLogger.debug(this, "Please ensure that the native libraries are placed in the following directories: ");
        DefaultLogger.debug(this, System.getProperty("java.library.path") + "\n");

        String inQueueStr = PropertyManager.getValue("mq.in.queue");
        String queueMgrStr = PropertyManager.getValue("mq.queue.manager");
        String hostIPStr = PropertyManager.getValue("mq.host.ip");
        String channelStr = PropertyManager.getValue("mq.in.channel.name");
        remote = PropertyManager.getBoolean("mq.server.isremote");

        if (null == queueMgrStr) {
            throw new RuntimeException("Queue Manager is null!");
        }
        if (null == inQueueStr) {
            throw new RuntimeException("In Queue is null!");
        }
        if (remote && null == hostIPStr) {
            throw new RuntimeException("Host IP is null!");
        }
        /*
           * if(null == channelStr) { throw new
           * RuntimeException("Channel is null!"); }
           */
        MQQueueConnectionFactory factory = new MQQueueConnectionFactory();
        factory.setCCSID(PropertyManager.getInt("mq.ccsid")); // default
        factory.setQueueManager(queueMgrStr);
        factory.setHostName(hostIPStr);
        factory.setPort(PropertyManager.getInt("mq.port")); // default
        if (channelStr != null) factory.setChannel(channelStr);

        System.out.println("REMOTE: " + remote);
        if (remote) {
            factory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
        } else {
            factory.setTransportType(JMSC.MQJMS_TP_BINDINGS_MQ);
        }

        QueueConnection con = factory.createQueueConnection();
        con.setExceptionListener(new EAIExceptionListener());
        con.start();
        QueueSession session = con.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
        Queue inQueue = session.createQueue(inQueueStr);
        QueueReceiver receiver = session.createReceiver(inQueue);
        Message msg = receiver.receive();
        String strMsg = null;

        if (msg instanceof TextMessage) {
            strMsg = ((TextMessage) msg).getText();
        } else if (msg instanceof BytesMessage) {
            BytesMessage bytesMessage = (BytesMessage) msg;
            strMsg = bytesMessage.toString();
        }
        msg.acknowledge();
        try {
            if (receiver != null) {
                receiver.close();
                receiver = null;
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
        return strMsg;
    }

    /**
     * Method to disconnect
     */
    public void disconnect() {
        try {
            if (_qSender != null) {
                _qSender.close();
                _qSender = null;
            }
        }
        catch (Exception e) {
            // consume
        }
        try {
            if (_session != null) {
                _session.close();
                _session = null;
            }
        }
        catch (Exception e) {
            // consume
        }
        try {
            if (_con != null) {
                _con.stop();
                _con.close();
                _con = null;
            }
        }
        catch (Exception e) {
            // consume
        }
    }

    /**
     * Method to connect queues
     */
    public void connect() {
        DefaultLogger.debug(this, "OUT Queue: " + _outQueueStr);
        DefaultLogger.debug(this, "Queue Manager: " + _queueMgrStr);
        DefaultLogger.debug(this, "HostIP: " + _hostIPStr);
        DefaultLogger.debug(this, "Channel Name: " + _channelStr);

        try {
            _factory = new MQQueueConnectionFactory();
            _factory.setCCSID(PropertyManager.getInt("mq.ccsid")); // default
            _factory.setQueueManager(_queueMgrStr);
            _factory.setHostName(_hostIPStr);
            _factory.setPort(PropertyManager.getInt("mq.port")); // default
            if ( _channelStr != null ) _factory.setChannel(_channelStr);

            if (remote) {
                /*
                     *  jms with tcpip (need client connection channel)
                     */
                _factory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
            } else {
                /*
                     * jms with local bindings, i.e. mq server running in local
                     * machine (need receiver type channel)
                     */
                _factory.setTransportType(JMSC.MQJMS_TP_BINDINGS_MQ);
            }

            _con = _factory.createQueueConnection();
            _con.setExceptionListener(new EAIExceptionListener());
            _con.start();
            _session = _con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            _outQueue = _session.createQueue(_outQueueStr);
            ((MQQueue) _outQueue).setTargetClient(JMSC.MQJMS_CLIENT_NONJMS_MQ);
            _qSender = _session.createSender(_outQueue);

            TextMessage msg = _session.createTextMessage(_message);
            // msg.setText(_message);

            _qSender.send(msg);
        }
        catch (Exception e) {
            DefaultLogger.error(this, "Caught Exception in connect! ", e);
            throw new RuntimeException("Caught Exception in connect! " + e.toString());
        }
    }
}
