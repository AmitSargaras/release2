package com.integrosys.cms.batch.factory;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.integrosys.base.techinfra.exception.ExceptionUtil;
import com.integrosys.cms.batch.BatchJobException;

/**
 * <p>
 * Batch Job Lancher to be used tightly with Spring context or Batch Job
 * instance itself. Batch Job instance need to be constructed as a bean in the
 * spring context, so that this scheduler can find the bean through the Spring
 * context. Then subsequently run the batch job.
 * 
 * <p>
 * Batch Job status will be recorded before and after execution, for both fail
 * and success batch job.
 * 
 * @author Chong Jun Yong
 * 
 */
public class BatchJobLauncherImpl implements BatchJobLauncher, ApplicationContextAware {

	private final Logger logger = LoggerFactory.getLogger(BatchJobLauncherImpl.class);

	private static final String STATUS_RUNNING = "RUNNING";

	private static final String STATUS_SUCCESS = "SUCCESS";

	private static final String STATUS_FAILED = "FAILED";

	private BatchJobDao batchJobDao;

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	protected ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	/**
	 * @param batchJobDao the batchJobDao to set
	 */
	public void setBatchJobDao(BatchJobDao batchJobDao) {
		this.batchJobDao = batchJobDao;
	}

	/**
	 * @return the batchJobDao
	 */
	public BatchJobDao getBatchJobDao() {
		return batchJobDao;
	}

	/**
	 * To schedule the batch job using the bean name and parameters provided.
	 * parameters should in the format of key value pair. eg. country=MY
	 * schema=SCH. Then batch job will use it internally.
	 * 
	 * @param beanName the spring context bean name, bean that implement
	 *        BatchJob.
	 * @param parameters parameters array, which each is in key value pair
	 *        format.
	 */
	public void launch(String beanName, String[] parameters) {
		BatchJob job = (BatchJob) getApplicationContext().getBean(beanName);
		launch(job, parameters);
	}

	public void launch(BatchJob batchJob, String[] parameters) {
		Map context = prepareContext(parameters);

		OBBatchJobStatus status = new OBBatchJobStatus();
		try {
			status.setJobName(Thread.currentThread().getName());
			status.setClassName(batchJob.getClass().getName());
			status.setStartDate(new Date());
			status.setParameters(ArrayUtils.toString(parameters));
			status.setStatus(STATUS_RUNNING);

			status = getBatchJobDao().createBatchJobStatus(status);

			batchJob.execute(context);
		}
		catch (BatchJobException ex) {
			logger.error("failed to run batch job [" + batchJob + "]", ex);

			status.setEndDate(new Date());
			status.setStatus(STATUS_FAILED);

			String causePointMessage = getMostSpecificRootCausePoint(ex.getStackTrace());

			StringBuffer msgBuf = new StringBuffer();
			Throwable rootCause = ex.getRootCause();
			if (rootCause != null) {
				msgBuf.append(rootCause.getClass().getName()).append(" : ");
				msgBuf.append(rootCause.getMessage());
			}
			else {
				msgBuf.append(ex.getClass().getName()).append(" : ");
				msgBuf.append(ex.getMessage());
			}

			if (causePointMessage != null) {
				msgBuf.append("; last known at ").append(causePointMessage);
			}

			status.setMessage(msgBuf.toString());

			getBatchJobDao().updateBatchJobStatus(status);
		}
		catch (Throwable t) {
			logger.error("failed to run batch job [" + batchJob + "] due to uncategorized batch job error.", t);

			status.setEndDate(new Date());
			status.setStatus(STATUS_FAILED);

			String causePointMessage = getMostSpecificRootCausePoint(t.getStackTrace());
			StringBuffer msgBuf = new StringBuffer();

			if (t instanceof Error) {
				msgBuf.append(t.getClass().getName()).append(" : ");
				msgBuf.append(t.getMessage());
			}
			else if (t instanceof Exception) {
				Throwable rootCause = ExceptionUtil.getRootCause((Exception) t);
				if (rootCause != null) {
					msgBuf.append(rootCause.getClass().getName()).append(" : ");
					msgBuf.append(rootCause.getMessage());
				}
				else {
					msgBuf.append(t.getClass().getName()).append(" : ");
					msgBuf.append(t.getMessage());
				}
			}

			if (causePointMessage != null) {
				msgBuf.append("; last known at ").append(causePointMessage);
			}

			status.setMessage(msgBuf.toString());

			getBatchJobDao().updateBatchJobStatus(status);
		}
		finally {
			if (STATUS_RUNNING.equals(status.getStatus())) {
				status.setEndDate(new Date());
				status.setStatus(STATUS_SUCCESS);

				getBatchJobDao().updateBatchJobStatus(status);
			}
		}

	}

	/**
	 * <p>
	 * Split the parameters which is in key value pair, eg. X=Y, and put into a
	 * Map. Map's key is the key, value is the value.
	 * 
	 * <p>
	 * Value in the parameters can contain more than 1 value, separated by comma
	 * ','. Such comma separated values will be put in a String array. <b>Eg.
	 * X=a,b,c, in the map, key is 'X', value is [a,b,c]</b>
	 * 
	 * @param parameters parameter array in key value pair form, ie X=Y
	 * @return a map, which key is the parameter key, value is the parameter
	 *         value (might contain array of string). All in String format.
	 */
	protected Map prepareContext(String[] parameters) {
		if (parameters == null) {
			return Collections.EMPTY_MAP;
		}

		Map context = new HashMap();
		for (int i = 0; i < parameters.length; i++) {
			String[] keyValue = StringUtils.split(parameters[i], '=');
			if (keyValue.length == 2) {
				String[] values = StringUtils.split(keyValue[1], ',');
				if (values.length == 1) {
					context.put(keyValue[0], values[0]);
				}
				else {
					context.put(keyValue[0], values);
				}
			}
		}

		return Collections.unmodifiableMap(context);
	}

	/**
	 * To get the last method invocation of integrosys which cause the exception
	 * thrown. If there is no integrosys found in the stack trace, return null
	 * 
	 * @param elements stack trace elements getting from throwable objects
	 * @return the elements stack trace string representation
	 * @see StackTraceElement#toString()
	 */
	protected String getMostSpecificRootCausePoint(StackTraceElement[] elements) {
		for (int i = 0; i < elements.length; i++) {
			StackTraceElement element = elements[i];
			if (element.getClassName().startsWith("com.integrosys")) {
				return element.toString();
			}
		}

		return null;
	}

}
