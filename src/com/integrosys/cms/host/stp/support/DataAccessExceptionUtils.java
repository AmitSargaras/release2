package com.integrosys.cms.host.stp.support;

import com.integrosys.cms.host.stp.DataAccessJdbcException;
import com.integrosys.cms.host.stp.STPMessageException;
import com.integrosys.cms.host.stp.STPProcessFailedException;
import org.apache.commons.lang.Validate;
import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Utility to handle Data Access Exception
 *
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 *
 */
public abstract class DataAccessExceptionUtils {

	private final static Logger logger = LoggerFactory.getLogger(DataAccessExceptionUtils.class);

	/**
	 * <p>
	 * Handle spring framework <code>DataAccessException</code> gracefully, if
	 * the cause having <code>SQLException</code> then more detail will be
	 * printed to the log, and the exception will be wrapped into
	 * <code>DataAccessJdbcException</code>. If the <code>SQLException</code> is
	 * instance of <code>BatchUpdateException</code> all the exception in the
	 * {@link java.sql.SQLException#getNextException()} will be accumulated and printed
	 * to the log as well.
	 * <p>
	 * If there is no SQLException in the cause, then the original exception
	 * will just be wrapped in <code>EAIProcessFailedException</code>, saying
	 * there is error in persistent framework, this could be due to
	 * configuration, code level error.
	 * @param ex the spring framework data access exception
	 * @return Subclass of <code>EAIMessageException</code>, either
	 *         <code>DataAccessJdbcException</code> (if there is SQLException)
	 *         or <code>EAIProcessFailedException</code> (if there is no
	 *         SQLException in the cause)
	 * @see org.springframework.dao.DataAccessException
	 */
	public static STPMessageException handleDataAccessException(DataAccessException ex) {
		Throwable cause = ex.getCause();
		if (cause instanceof JDBCException) {
			JDBCException jdbcEx = (JDBCException) cause;
			logger.error("error encountered when accessing the database system, detail [" + ex.getMessage()
					+ "]; nested exception is [" + jdbcEx.getCause() + "], sql statement caused the problem ["
					+ jdbcEx.getSQL() + "]", jdbcEx.getCause());

			if (jdbcEx.getCause() instanceof BatchUpdateException) {
				BatchUpdateException batchUpdateEx = (BatchUpdateException) jdbcEx.getCause();
				handleBatchUpdateException(batchUpdateEx);
			}

			return new DataAccessJdbcException("failed to access the database system", jdbcEx.getSQLException());
		}

		if (cause instanceof BatchUpdateException) {
			logger.error("error encountered when accessing the database system, detail [" + cause.getMessage() + "]",
					cause);

			BatchUpdateException batchUpdateEx = (BatchUpdateException) cause;
			handleBatchUpdateException(batchUpdateEx);

			return new DataAccessJdbcException("failed to access the database system", batchUpdateEx);
		}

		if (cause instanceof SQLException) {
			logger.error("error encountered when accessing the database system, detail [" + ex.getMessage() + "]",
					cause);

			return new DataAccessJdbcException("failed to access the database system", (SQLException) cause);
		}

		return new STPProcessFailedException("failed to access persistent framework", ex);
	}

	/**
	 * Accumulate all the <code>SQLException</code> in
	 * {@link java.sql.SQLException#getNextException()}
	 *
	 * @param ex the batch update exception which having next exception
	 * @return a list consist of all the next SQLException in the batch update
	 *         exception provided.
	 */
	public static List accumlateAllSQLExceptions(BatchUpdateException ex) {
		List sqlExceptionList = new ArrayList();

		SQLException next = ex.getNextException();
		while (next != null) {
			sqlExceptionList.add(next);
			next = next.getNextException();
		}

		return sqlExceptionList;
	}

	/**
	 * Construct all the sql exception in the collection provided, in the form
	 * of showing state, code and reason.
	 *
	 * @param sqlExceptions a collection of SQLExceptions
	 * @return a predefined format of message (state, code, reason) of all the
	 *         SQLException in the collection provided.
	 */
	public static String constructAllSQLExceptionMessage(Collection sqlExceptions) {
		Validate.notEmpty(sqlExceptions, "sqlExceptions must be provided.");

		StringBuffer buf = new StringBuffer();
		for (Iterator itr = sqlExceptions.iterator(); itr.hasNext();) {
			int i = 1;
			SQLException ex = (SQLException) itr.next();
			buf.append("Error (").append(i).append("), ");
			buf.append("SQL State: [").append(ex.getSQLState()).append("], ");
			buf.append("SQL Code: ").append(ex.getErrorCode()).append("], ");
			buf.append("Reason: ").append(ex.getMessage()).append("]");

			if (itr.hasNext()) {
				buf.append("; ");
			}

			i++;
		}

		return buf.toString();
	}

	/**
	 * <p>
	 * Handle Batch Exception
	 * <p>
	 * First, accumulate all the sql exception in
	 * {@link java.sql.SQLException#getNextException()}, then print out the message of
	 * all the SQLExceptions
	 * @param ex the batch update exception
	 */
	private static void handleBatchUpdateException(BatchUpdateException ex) {
		List fullExceptions = accumlateAllSQLExceptions(ex);
		if (fullExceptions.isEmpty()) {
			logger.warn("There is no next exception in the batch update exception.");
		}
		else {
			logger.error("Batch Update Exception detected, print full error message : ["
					+ constructAllSQLExceptionMessage(fullExceptions));
		}
	}
}