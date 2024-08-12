package com.integrosys.cms.host.eai.support;

/**
 * <p>
 * Message handler subject which suppose to be observed by subscriber (instance
 * of {@link MessageHandlerObserver}).
 * 
 * <p>
 * provide {@link #register(String, MessageHandlerObserver)} and
 * {@link #registerAndProcess(String, MessageHandlerObserver)} for observer to
 * just register (which this subject may already running), and register then
 * process the subject (more like adhoc basis)
 * 
 * @author Chong Jun Yong
 * @since 25.08.2008
 */
public interface MessageHandlerSubject {

	/**
	 * Register observer only. It would mean that the subject is already running
	 * message process at all time.
	 * 
	 * @param msgId the message id of the message that passed in by observer, so
	 *        later stage subject can retrieve the observer based on message id
	 *        processed
	 * @param observer the observer that required to subscribe to this subject,
	 *        and notified by subject after message for the message id supplied
	 *        is finished.
	 */
	public void register(String msgId, MessageHandlerObserver observer);

	/**
	 * Register observer and process the message as well.
	 * 
	 * @param msgId the message id of the message that passed in by observer, so
	 *        later stage subject can retrieve the observer based on message id
	 *        processed
	 * @param observer the observer that required to subscribe to this subject,
	 *        and notified by subject after message for the message id supplied
	 *        is finished.
	 */
	public void registerAndProcess(String msgId, MessageHandlerObserver observer);
}
