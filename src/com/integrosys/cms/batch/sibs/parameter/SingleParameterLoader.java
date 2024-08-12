package com.integrosys.cms.batch.sibs.parameter;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * <p>
 * Parameter Loader to load a single code only.
 * <p>
 * Sub class to supply external data source and internal data source, which is
 * supplied as <tt>JdbcTemplate</tt> instance.
 * <p>
 * Caller to call {@link #run()} to invoke the parameter loader, such that
 * parameter data between external data source and internal data source will be
 * synchronized.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface SingleParameterLoader {
	/**
	 * To set the <tt>JdbcTemplate</tt> having interface with external data
	 * source.
	 * @param externalJdbcTemplate <tt>JdbcTemplate</tt> to be interface with
	 *        external data source
	 */
	public void setExternalJdbcTemplate(JdbcTemplate externalJdbcTemplate);

	/**
	 * To set the <tt>JdbcTemplate</tt> having interface with internal data
	 * source.
	 * @param externalJdbcTemplate <tt>JdbcTemplate</tt> to be interface with
	 *        external data source
	 */
	public void setCmsJdbcTemplate(JdbcTemplate cmsJdbcTemplate);

	/**
	 * To invoke the parameter loader. ie, to synchronize the codes between
	 * external and internal data source
	 */
	public void run();
}
