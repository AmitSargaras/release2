package com.integrosys.cms.batch.factory;

import java.util.Map;

import com.integrosys.cms.batch.BatchJobException;

/**
 * <p>
 * Batch Job to be called by Batch Job scheduler. Batch Job scheduler should
 * prepare the parameters and create a Map and passed to the individual Batch
 * Job.
 * 
 * <p>
 * One way to construct the parameter, is using key-value pair from the console.
 * eg. country=MY schema=SCH. The constructed map can be passed into this
 * instance and execute.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface BatchJob {
	/**
	 * To execute the batch job and return BatchException if there the job is
	 * failed.
	 * 
	 * @param context key value pair paramters to be used internally, can be
	 *        null if it's not required.
	 * @throws BatchJobException if there is any error encountered
	 */
	public void execute(Map context) throws BatchJobException;
}
