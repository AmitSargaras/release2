package com.integrosys.cms.batch.common;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.IncompleteBatchJobException;

/**
 * <p>
 * Batch Feed loader which required to run the stored procedure to validate the
 * data in the feeds table and persist the data from the feeds table into
 * respective domain table.
 * 
 * <p>
 * {@link #preprocess(Map)} will be called first before the actual batch job
 * execution start. Default implementation is to clear temp table records. So
 * subclass need to override {@link #doExecute(Map)} for the actual batch job
 * process. Afterwhich, {@link #postprocess(Map)} will be invoked after the
 * actual batch job execution finish, default will be clear the temp table
 * records, just like pre process.
 * 
 * <p>
 * <b>NOTE</b> Validation, Actual run procedure name, and table name must be
 * supplied in order this loader to be executed correctly.
 * 
 * <p>
 * Stored Procedure run sequence (defined in {@link #doRunStoredProcedure()})
 * <ol>
 * <li>calculate number of records in the temp table, defined in
 * {@link #setTempTableName(String)}
 * <li>if there is at least 1 item in the temp table, then run the stored
 * procedure in the following order:
 * <ol>
 * <li>run the validation procedure, defined in
 * {@link #setValidationProcedureName(String)}
 * <li>run the actual persistence procedure, defined in
 * {@link #setRunProcedureName(String)}
 * </ol>
 * </ol>
 * <b>Note</b> if there is nothing in the temp table, the batch job will be
 * skipped
 * 
 * <p>
 * Possible sequence for subclass to execute the whole batch job.
 * <ol>
 * <li>Read the feeds, from flat file to temp table, (via
 * {@link #doReadFeeds(Map)})
 * <li>To rebind all the package for the stored procedure (via
 * {@link #doRebindPackage()})
 * <li>run the validation stored procedure, and actual run stored procedure (to
 * migrate from temp table to actual table) (via {@link #doRunStoredProcedure()})
 * </ol>
 * <p>
 * {@link DefaultDomainAwareBatchFeedLoader} is the default implementation of
 * this batch feed loader to follow the above sequence for the whole batch job.
 * 
 * @author Chong Jun Yong
 * @see #doReadFeeds(Map)
 * @see #doRunStoredProcedure()
 */
public abstract class AbstractDomainAwareBatchFeedLoader extends AbstractBatchFeedLoader {

	/**
	 * Param key retrieve from the batch job context, to determine whether
	 * <b>not</b> to read feeds, once there is value for this key, batch job
	 * will skip reading the feeds.
	 */
	public final static String PARAM_KEY_SKIP_READ_FEEDS = "skip_read_feeds";

	/**
	 * Param key retrieve from the batch job context, to determine whether
	 * <b>not</b> to bind the package, once there is value for this key, batch
	 * job will skip to bind the package.
	 */
	public final static String PARAM_KEY_SKIP_BIND_PACKAGE = "skip_bind_package";

	/**
	 * Param key retrieve from the batch job context, to determine whether
	 * <b>not</b> to run the stored procedure, once there is value for this key,
	 * batch job will skip to run validate and run the stored procedure.
	 */
	public final static String PARAM_KEY_SKIP_RUN_PROC = "skip_run_proc";

	/**
	 * Param key retrieve from the batch job context, to determine whether
	 * <b>not</b> to run the pre process (for this sub class is clear the temp
	 * table), once there is value for this key, batch job will skip to run the
	 * pre process
	 */
	public final static String PARAM_KEY_SKIP_PRE_PROCESS = "skip_pre_process";

	/**
	 * Param key retrieve from the batch job context, to determine whether
	 * <b>not</b> to run the post process (for this sub class is clear the temp
	 * table), once there is value for this key, batch job will skip to run the
	 * post process
	 */
	public final static String PARAM_KEY_SKIP_POST_PROCESS = "skip_post_process";

	private JdbcTemplate jdbcTemplate;

	private String validationProcedureName;

	private String runProcedureName;

	private String tempTableName;

	/**
	 * <p>
	 * Set the batch job temp table name, which the batch feed will be recorded
	 * to.
	 * 
	 * <p>
	 * To be used to check the number of records in the table, so to whether
	 * trigger the stored procedure
	 * 
	 * @param tempTableName the temp table name for the batch feed records
	 */
	public void setTempTableName(String tempTableName) {
		this.tempTableName = tempTableName;
	}

	public String getTempTableName() {
		return tempTableName;
	}

	/**
	 * <p>
	 * Stored Procedure name for the validation checking the records in the temp
	 * table
	 * 
	 * @param validationProcedureName the validation stored procedure name
	 */
	public void setValidationProcedureName(String validationProcedureName) {
		this.validationProcedureName = validationProcedureName;
	}

	public String getValidationProcedureName() {
		return validationProcedureName;
	}

	/**
	 * Stored Procedure name for the actual run on the records in the temp table
	 * to respective domain table(s).
	 * 
	 * @param runProcedureName the actual run stored procedure name
	 */
	public void setRunProcedureName(String runProcedureName) {
		this.runProcedureName = runProcedureName;
	}

	public String getRunProcedureName() {
		return runProcedureName;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public final void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		Validate.notNull(jdbcTemplate,
				"'jdbcTemplate' to be used for the calling on stored procedure must not be null.");
		Validate.notNull(jdbcTemplate.getDataSource(), "'dataSource' must not be null.");

		this.jdbcTemplate = jdbcTemplate;
	}

	protected abstract void doPersistFeedList(List feedList);

	public final void execute(Map context) throws BatchJobException {
		// do pre process
		if (context.get(PARAM_KEY_SKIP_PRE_PROCESS) == null) {
			preprocess(context);
		}
		// do the execution of batch job
		doExecute(context);

		// do post process
		if (context.get(PARAM_KEY_SKIP_POST_PROCESS) == null) {
			postprocess(context);
		}
	}

	/**
	 * <p>
	 * Do Pre Process before the actual batch job start.
	 * <p>
	 * For this FeedLoader, clear the temp table first before read the feed
	 * again. So no duplicate records exists.
	 * <p>
	 * Sub class to override if need special handling.
	 * @param context context for the batch job, such as parameters.
	 */
	protected void preprocess(Map context) {
		doTruncateTempTable();
	}

	/**
	 * <p>
	 * Do Post Process before the actual batch job start.
	 * <p>
	 * For this FeedLoader, clear the temp table first after all the process
	 * (specified by {@link #doExecute(Map)}) has finished.
	 * <p>
	 * Sub class to override if need special handling.
	 * @param context context for the batch job, such as parameters.
	 */
	protected void postprocess(Map context) {
		doTruncateTempTable();
	}

	private void doTruncateTempTable() {
		if (getTempTableName() == null) {
			logger.warn("There is no temp table configured for this batch job, truncate of temp table will be skipped");
			return;
		}

		try {
			getJdbcTemplate().execute("{call UTIL_TRUNCATE_TABLE ('" + getTempTableName() + "')}",
					new CallableStatementCallback() {

						public Object doInCallableStatement(CallableStatement cs) throws SQLException,
								DataAccessException {
							cs.executeUpdate();
							return null;
						}

					});
		}
		catch (DataAccessException ex) {
			throw new IncompleteBatchJobException("failed to truncate temp table [" + getTempTableName() + "]", ex);
		}
	}

	/**
	 * Actual execution of the batch job goes here. This will be invoked after
	 * {@link #preprocess(Map)} is run. Subclass possible to implement this to
	 * do the feeds reading, and running of stored procedure.
	 * @param context context for the batch job, such as parameters.
	 * @throws BatchJobException if there is any batch job related fail
	 */
	protected abstract void doExecute(Map context) throws BatchJobException;

	protected abstract ResourceAwareItemReaderItemStream getFlatFileItemReader();

	/**
	 * To run the procedure, validation then the actual run (if there is at
	 * least one records in the temp table)
	 */
	protected void doRunStoredProcedure() throws IncompleteBatchJobException {
		if (getTempTableName() == null) {
			logger.warn("There is no temp table configured for this batch job, batch job will be skipped");
			return;
		}

		int count = getJdbcTemplate().queryForInt("select count(*) from " + getTempTableName());

		if (count > 0) {
			try {
				getJdbcTemplate().execute("{call " + getValidationProcedureName() + "()}",
						new CallableStatementCallback() {

							public Object doInCallableStatement(CallableStatement cs) throws SQLException,
									DataAccessException {
								cs.executeUpdate();
								return null;
							}

						});
			}
			catch (DataAccessException ex) {
				throw new IncompleteBatchJobException("failed to process validation procedure ["
						+ getValidationProcedureName() + "]", ex);
			}

			try {
				getJdbcTemplate().execute("{call " + getRunProcedureName() + "()}", new CallableStatementCallback() {

					public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
						cs.executeUpdate();
						return null;
					}

				});
			}
			catch (DataAccessException ex) {
				throw new IncompleteBatchJobException("failed to process actual run procedure ["
						+ getRunProcedureName() + "]", ex);
			}
		}
		else {
			logger.info("there is no item in the table [" + getTempTableName() + "], skip this batch job");
		}
	}

	/**
	 * to rebind the routine package of the stored procedure, in some way might
	 * speed up the whole procedure process.
	 */
	protected void doRebindPackage() {
		try {
			getJdbcTemplate().execute(
					"{call SYSPROC.REBIND_ROUTINE_PACKAGE('P', '" + getValidationProcedureName() + "','ANY')}",
					new CallableStatementCallback() {

						public Object doInCallableStatement(CallableStatement cs) throws SQLException,
								DataAccessException {
							cs.executeUpdate();
							return null;
						}

					});
		}
		catch (DataAccessException ex) {
			logger.error("failed to rebind package [" + getValidationProcedureName() + "]", ex);
		}

		try {
			getJdbcTemplate().execute(
					"{call SYSPROC.REBIND_ROUTINE_PACKAGE('P', '" + getRunProcedureName() + "','ANY')}",
					new CallableStatementCallback() {

						public Object doInCallableStatement(CallableStatement cs) throws SQLException,
								DataAccessException {
							cs.executeUpdate();
							return null;
						}

					});
		}
		catch (DataAccessException ex) {
			logger.error("failed to rebind package [" + getRunProcedureName() + "]", ex);
		}
	}
}
