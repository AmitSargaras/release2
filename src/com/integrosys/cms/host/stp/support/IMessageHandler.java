package com.integrosys.cms.host.stp.support;

import com.integrosys.cms.host.stp.STPMessage;
import com.integrosys.cms.host.stp.STPMessageException;

import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * Handler to handle process and post process information passed from host.
 *
 * <p>
 * Program should consider to use one of abstract implementation, instead of
 * directly implement this interface.
 *
 * @author marvin
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 * @since 1.1
 */
public interface IMessageHandler {

	/**
	 * Process message, pass the message into relevant command pattern handler,
	 * which might need to interface with persistent storage
	 *
	 * @param stpmsg message unmarshalled from other source, such as XML
	 * @return a context contain processed message (contain actual objects),
	 *         staging message (contain staging objects) and trx values objects
	 * @throws com.integrosys.cms.host.eai.EAIMessageException if there is any error occurred
	 */
	public Properties processMessage(STPMessage stpmsg) throws STPMessageException;

    public Properties processMessage(Object obj) throws STPMessageException;

	/**
	 * Post process the message, which getting from the context in
	 * {@link #processMessage(com.integrosys.cms.host.stp.STPMessage)}. This post process can include the
	 * process of workflow/transaction value, after the domain objects has been
	 * populated.
	 *
	 * @param msg processed message contain primary key set/get of actual
	 *        objects from persistent storage
	 * @param stagingMsg processed message contain primary key set/get of
	 *        staging objects from persistent storage
	 * @param flatMessage group of trx value objects back by primary key
	 *        individually, and eventually paired by the trx type
	 * @throws com.integrosys.cms.host.eai.EAIMessageException if there is any error occurred
	 */
	public void postprocess(STPMessage msg, STPMessage stagingMsg, Map flatMessage) throws STPMessageException;

    public long generateSeq(String seqCode, boolean formatted) throws STPMessageException;
   // public STPMessage processMessage(Object ob);
}