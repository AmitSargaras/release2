package com.integrosys.cms.batch.factory;

/**
 * Batch Job Launcher to start BatchJob instance, support key-value pair
 * parameters.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface BatchJobLauncher {
	/**
	 * To start a BatchJob given the bean name of the BatchJob. the Bean must
	 * implements BatchJob interface.
	 * 
	 * @param beanName name of the BatchJob instance bean, eg, Jndi name or IoC
	 *        container bean name.
	 * @param parameters parameters in key-value pairs
	 */
	public void launch(String beanName, String[] parameters);

	/**
	 * To start a BatchJob given the instance.
	 * 
	 * @param batchJob batch job instance to start execution
	 * @param parameters parameters in key-value pairs
	 */
	public void launch(BatchJob batchJob, String[] parameters);

}
