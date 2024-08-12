package com.integrosys.cms.host.eai.log;

/**
 * Log message for the purpose of tracking.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface ILogMessageJdbc {
	/**
	 * Persist a message log
	 * 
	 * @param logMsg message log to be persisted
	 */
	public void persistLogMessage(ILogMessage logMsg);

	/**
	 * Persist a EAI Message log
	 * 
	 * @param logMessage the EAI message log to be persisted
	 */
	public void persistEAILogMessage(EAIMessageLog logMessage);
}
