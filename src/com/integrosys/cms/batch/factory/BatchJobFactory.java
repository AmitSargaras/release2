package com.integrosys.cms.batch.factory;

import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Batch Job factory to prepare the query string parameter, 1st will be the
 * batch job bean name, second onwards will be the query string parameter
 * required to run the batch job.
 * 
 * @author Chong Jun Yong
 * 
 */
public class BatchJobFactory {

	private final Logger logger = LoggerFactory.getLogger(BatchJobFactory.class);

	private BatchJobLauncher batchJobLauncher;

	public void setBatchJobLauncher(BatchJobLauncher batchJobLauncher) {
		this.batchJobLauncher = batchJobLauncher;
	}

	public BatchJobLauncher getBatchJobLauncher() {
		return batchJobLauncher;
	}

	/**
	 * Main method to use for batch jobs
	 */
	public void run(final String args[]) {

		if (args == null || args.length == 0) {
			logger.warn("no arguments specified from the front end, exiting.");
			System.exit(1);
		}

		String currentThreadName = Thread.currentThread().getName();

		// setting the job name, which is the bean name as the thread name for
		// the current thread
		String jobName = args[0];
		Thread.currentThread().setName(jobName);

		if (args.length == 1) {
			logger.info("starting job [" + jobName + "] with no arguments at [" + (new Date()) + "]");

			getBatchJobLauncher().launch(jobName, null);

			logger.info("end job [" + jobName + "] at [" + (new Date()) + "]");
		}
		else if (args.length > 1) {
			final String[] parameters = (String[]) ArrayUtils.subarray(args, 1, args.length);

			logger.info("starting job [" + jobName + "] with arguments [" + ArrayUtils.toString(parameters) + "] at ["
					+ (new Date()) + "]");

			getBatchJobLauncher().launch(jobName, parameters);

			logger.info("end job [" + jobName + "] at [" + (new Date()) + "]");
		}

		// set back the current thread name, not to confuse the name in the log
		Thread.currentThread().setName(currentThreadName);
	}
}
