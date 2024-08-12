package com.integrosys.cms.batch.common;

import java.util.Collection;

/**
 * Batch feed error logger, to persist batch feed error object into a persistent
 * storage, either file system or database system.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface BatchFeedErrorLogger {
	/**
	 * Log a batch feed error into persistent storage
	 * 
	 * @param error a batch feed error instance.
	 */
	public void log(BatchFeedError error);

	/**
	 * Log a collection of batch feed error into persistent storage
	 * 
	 * @param batchFeedErrors a collection of batch feed error, all must be same
	 *        type.
	 */
	public void log(Collection batchFeedErrors);
}
