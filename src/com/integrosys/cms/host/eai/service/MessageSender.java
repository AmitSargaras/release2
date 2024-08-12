package com.integrosys.cms.host.eai.service;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageException;

/**
 * Message sender which responsible to send the EAIMessage to the destination.
 * Sub class can use various method such as HTTP, JMS, Web Services, etc.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface MessageSender {
	/**
	 * Send the message to the destionation and doesn't need any acknowledgment
	 * 
	 * @param message the message object to be sent to the destination
	 * @throws EAIMessageException if there is any error occurs
	 */
	public void send(EAIMessage message) throws EAIMessageException;

	/**
	 * Send the message to the destionation and wait for the reply, such as
	 * acknowledgment or inquiry result
	 * 
	 * @param message the message object to be sent to the destination
	 * @return either acknowledgment or the inquiry result from the destination
	 * @throws EAIMessageException if there is any error occurs
	 */
	public Object sendAndReceive(EAIMessage message) throws EAIMessageException;
}
