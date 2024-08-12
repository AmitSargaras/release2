package com.integrosys.cms.host.stp.log;

/**
 * Log message for the purpose of tracking.
 *
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 *
 */
public interface ISTPLogMessageJdbc {
	/**
	 * Persist a message log
	 *
	 * @param logMsg message log to be persisted
	 */
	public void persistLogMessage(ILogMessage logMsg);

	/**
	 * Persist a STP Message log
	 *
	 * @param logMessage the STP message log to be persisted
	 */
	public void persistSTPLogMessage(MessageLog logMessage);
}