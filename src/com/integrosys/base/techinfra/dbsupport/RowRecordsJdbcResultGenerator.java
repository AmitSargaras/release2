package com.integrosys.base.techinfra.dbsupport;

import org.apache.commons.lang.Validate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * Implementation of <tt>JdbcResultGenerator</tt> to execute the SQL query, and
 * return each row in a same manner, which mean this require a instance of
 * {@link org.springframework.jdbc.core.RowMapper}
 * @author Chong Jun Yong
 * 
 */
public class RowRecordsJdbcResultGenerator implements JdbcResultGenerator {

	private final String sqlQuery;

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper rowMapper;

	/**
	 * Default constructor to provide SQL query, a JdbcTemplate instance and a
	 * RowMapper instance. All values must not be null.
	 * @param sqlQuery the SQL query to be executed
	 * @param jdbcTemplate a JdbcTemplate to execute the SQL query and doing
	 *        whatever to the result set.
	 * @param rowMapper a RowMapper to map each record in result set to desired
	 *        format, and finally put all the records in a list. (must not be
	 *        null)
	 */
	public RowRecordsJdbcResultGenerator(String sqlQuery, JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
		Validate.notEmpty(sqlQuery, "SQL Query must not be null.");
		Validate.notNull(jdbcTemplate, "JdbcTemplate to execute query must not be null.");
		Validate.notNull(rowMapper, "RowMapper to map the records return must not be null.");
		this.sqlQuery = sqlQuery;
		this.jdbcTemplate = jdbcTemplate;
		this.rowMapper = rowMapper;
	}

	public Object getResult(Object[] args) {
		if (args != null) {
			return this.jdbcTemplate.query(this.sqlQuery, args, this.rowMapper);
		}
		else {
			return this.jdbcTemplate.query(this.sqlQuery, this.rowMapper);
		}
	}
}
