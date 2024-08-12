package com.integrosys.cms.app.eventmonitor;

/**
 * Exception to be raised whenever there is failure to retrieve result from
 * monitor dao.
 * 
 * @author Chong Jun Yong
 * 
 */
public class MonitorDaoResultRetrievalFailureException extends EventMonitorException {
	/**
	 * Constructor with error detail message supplied
	 * 
	 * @param msg error detail message
	 */
	public MonitorDaoResultRetrievalFailureException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with error detail message and cause supplied
	 * 
	 * @param msg error detail message
	 * @param cause the cause that throw this exception
	 */
	public MonitorDaoResultRetrievalFailureException(String msg, Throwable t) {
		super(msg, t);
	}
}
