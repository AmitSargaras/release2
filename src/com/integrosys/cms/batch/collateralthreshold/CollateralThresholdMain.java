/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.batch.collateralthreshold;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.factory.BatchJob;

/**
 * A batch program to perform security allocation computation
 * 
 * @author hmbao
 * @author Chong Jun Yong
 * @since 2006/10/27
 */
public class CollateralThresholdMain implements BatchJob {

	private Logger logger = LoggerFactory.getLogger(CollateralThresholdMain.class);

	private JdbcTemplate jdbcTemplate;

	private String collateralAllocationProcedureName;

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setCollateralAllocationProcedureName(String collateralAllocationProcedureName) {
		this.collateralAllocationProcedureName = collateralAllocationProcedureName;
	}

	public String getCollateralAllocationProcedureName() {
		return this.collateralAllocationProcedureName;
	}

	public CollateralThresholdMain() {
	}

	public void execute(Map context) throws BatchJobException {
		executeInternal();
	}

	/**
	 * Process Collateral Threshold
	 */
	private void executeInternal() {

		logger.info("calling collateral allocation stored procedure [" + getCollateralAllocationProcedureName() + "]");

		try {
			getJdbcTemplate().execute("{call " + getCollateralAllocationProcedureName() + "()}",
					new CallableStatementCallback() {

						public Object doInCallableStatement(CallableStatement cs) throws SQLException,
								DataAccessException {
							cs.executeUpdate();
							return null;
						}
					});
		}
		catch (DataAccessException ex) {
			throw new IncompleteBatchJobException(
					"Failed to finish security coverage batch job, possibly due to database error.", ex);
		}
	}
}