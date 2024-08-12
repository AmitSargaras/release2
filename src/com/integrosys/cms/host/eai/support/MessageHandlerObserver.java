package com.integrosys.cms.host.eai.support;

/**
 * <p>
 * An observer to subscribe message handler subject
 * {@link MessageHandlerSubject}.
 * 
 * <p>
 * After message handler subject finish process the message passed in, observer
 * will be notified through {@link #update(Object)}
 * 
 * @author Chong Jun Yong
 * @since 25.08.2008
 * 
 */
public interface MessageHandlerObserver {

	/**
	 * To be called/notified by subject which this observer subscribe to.
	 * 
	 * @param object the object passed from subject to this observer.
	 */
	public void update(Object object);
}
