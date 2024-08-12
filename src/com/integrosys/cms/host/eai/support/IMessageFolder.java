package com.integrosys.cms.host.eai.support;

/**
 * Message holder to hold the message passed from host. Subsequently the message
 * will be processed sequentially or be retrieved providing the message id.
 * 
 * @author Chong Jun Yong
 * @since 25.08.2008
 */
public interface IMessageFolder {

	/**
	 * Put the message into a holder and put into the first 'page' of this
	 * folder
	 * 
	 * @param message message object to be put into this folder, it will first
	 *        put into a message holder
	 * @return the message Id for the message supplied
	 */
	public String putMessage(Object message);

	/**
	 * retrieve the first message holder and remove from the folder as well.
	 * 
	 * @return the first message holder object, else <code>null</code> if there
	 *         is no object in the folder.
	 */
	public Object popMessage();

	/**
	 * retrieve and remove the message matching the <code>msgId</code> supplied
	 * in the folder.
	 * 
	 * @param msgId the message id that used to search through the message
	 *        folder
	 * @return the message match the msgId;
	 */
	public Object popMessageByMsgId(String msgId);

}
