package com.integrosys.cms.batch.factory;

/**
 * ORM based DAO to interface with running of batch job. Can store info such as
 * Batch Job status.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface BatchJobDao {
	/**
	 * Create a new entry of batch job status into persistent storage. Info such
	 * as end date, message, shouldn't exists. And this will be populate just
	 * before invoke {@link #updateBatchJobStatus(OBBatchJobStatus)}
	 * 
	 * @param status a batch job status entity
	 * @return persistent state batch job status
	 */
	public OBBatchJobStatus createBatchJobStatus(OBBatchJobStatus status);

	/**
	 * Update existing batch job status. It should update the end date, status,
	 * and message into the batch job status entity.
	 * 
	 * @param status batch job status to be updated.
	 * @return updated persistent state batch job status
	 */
	public OBBatchJobStatus updateBatchJobStatus(OBBatchJobStatus status);

	/**
	 * Retrieve batch job status entity from persistent storage, this should be
	 * invoked just before to update the entity.
	 * 
	 * @param key batch job status entity key
	 * @return persistent state batch job status
	 */
	public OBBatchJobStatus retrieveBatchJobStatus(Long key);
}
