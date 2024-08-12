/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.batch.limitbooking;

import java.sql.CallableStatement;

import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.springframework.batch.repeat.ExitStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.factory.BatchJob;

/**
 * A batch program to auto update limit booking to expired after 180 days from creation date.
 * 
 * @author $Author: Pctan $
 * @version $Revision: $
 * @since $Date: $ Tag: $Name:  $
 */
public class UpdateStatusExpiredMain implements BatchJob {
	
	private JdbcTemplate jdbcTemplate;

    private String runProcedureName;

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
		Validate.notNull(jdbcTemplate, "'jdbcTemplate' to be used for the calling on stored procedure must not be null.");
		Validate.notNull(jdbcTemplate.getDataSource(), "'dataSource' must not be null.");

		this.jdbcTemplate = jdbcTemplate;
	}

    public ExitStatus execute() throws Exception {
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
        return ExitStatus.FINISHED;
    }

    public void execute(Map context) throws BatchJobException {
        try {
            execute();
        } catch (Exception e) {
            throw new IncompleteBatchJobException("Limit Booking Update Expired Batch failed to complete", e);
        }
    }
}