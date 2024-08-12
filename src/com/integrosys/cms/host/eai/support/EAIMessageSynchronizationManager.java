package com.integrosys.cms.host.eai.support;

/**
 * <p>
 * EAI Message Sychronization Manager, to manage the stuff such as what's the
 * stuff of the message coming in as per thread.
 * <p>
 * So through out the process of the message, we can grab the information easily
 * in any point.
 * @author Chong Jun Yong
 * 
 */
public abstract class EAIMessageSynchronizationManager {
	/** the source of the message coming in */
	private static final ThreadLocal source = new ThreadLocal();

	/**
	 * whether the current message is a variation case, applicable for certain
	 * message type only
	 */
	private static final ThreadLocal variation = new ThreadLocal();

	public static void setMessageSource(String sourceId) {
		source.set(sourceId);
	}

	public static String getMessageSource() {
		return (String) source.get();
	}

	public static void setIsMessageVariation(boolean isVariation) {
		variation.set(new Boolean(isVariation));
	}

	public static boolean getIsMessageVariation() {
		Boolean isVariation = (Boolean) variation.get();
		return isVariation.booleanValue();
	}

	/**
	 * clear all the thread local resource
	 */
	public static void clear() {
		source.set(null);
		variation.set(null);
	}
}
