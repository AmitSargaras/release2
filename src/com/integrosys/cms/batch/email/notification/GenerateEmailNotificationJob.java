package com.integrosys.cms.batch.email.notification;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.batch.factory.BatchJobFactory;

public class GenerateEmailNotificationJob {

	/**
	 * This job is run and executed by quartz schedular. For more details refer
	 * to schedular configuration in
	 * config\spring\recurrent\AppContext_Batch.xml
	 * 
	 */
	private BatchJobFactory batchJobFactory;

	public BatchJobFactory getBatchJobFactory() {
		return batchJobFactory;
	}

	public void setBatchJobFactory(BatchJobFactory batchJobFactory) {
		this.batchJobFactory = batchJobFactory;
	}

	public void execute() {
		try {
			DefaultLogger.debug(this,"Inside GenerateEmailNotificationJob :)" + this.getClass());
			String[] args = new String[] { "generateEmailNotificationBatchJob" };
			getBatchJobFactory().run(args);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GenerateEmailNotificationJob() {
	}

	public static void main(String[] args) {
		new GenerateEmailNotificationJob().execute();
	}
}
