package com.integrosys.base.techinfra.dbsupport;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiTemplate;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * <p>
 * An adapter to be implemented by Jdbc Dao class which is not in the context of
 * IoC (Inversion of Control) (such as spring framework). Act as a replacement
 * of {@link DBUtil}.
 * 
 * <p>
 * Sub class can use {@link JdbcTemplate} using {@link #getJdbcTemplate()}
 * provided. Data Source will be resolved internally here rather than being
 * injected.
 * 
 * <p>
 * Data Source will be retrieved via Jndi name
 * <b>dbconfig.weblogic.datasource.jndiname</b>, which reside in the
 * ofa.properties.
 * 
 * <p>
 * <b>NOTE</b> The subclasses shall be exposed in a Session Bean or Entity Bean
 * to participate in the underlying transaction. And also to get the DataSource
 * correctly.
 * 
 * @author Chong Jun Yong
 * @since 28.08.2008
 * @see JdbcTemplate
 * @see DBUtil
 */
public abstract class JdbcTemplateAdapter {

	private JdbcTemplate jdbcTemplate;

	/** The key having jndi name of the data source */
	private static final String DATASOURCE_JNDI_KEY = "dbconfig.weblogic.datasource.jndiname";

	/**
	 * Get the {@link JdbcTemplate} instance with data source set using the jndi
	 * name provided.
	 * 
	 * @return pre-configured {@link JdbcTemplate} instance
	 * @throws IllegalStateException if there is any error when lookup using the
	 *         jndi name.
	 */
	public JdbcTemplate getJdbcTemplate() {

		if (jdbcTemplate == null) {
			JndiTemplate jndiTemplate = new JndiTemplate();

			String dataSourceJndiName = PropertyManager.getValue(DATASOURCE_JNDI_KEY);

			try {
				DataSource dataSource = (DataSource) jndiTemplate
						.lookup(dataSourceJndiName, javax.sql.DataSource.class);
				jdbcTemplate = new JdbcTemplate(dataSource);

				return jdbcTemplate;
			}
			catch (NamingException e) {
				IllegalStateException ise = new IllegalStateException("cannot lookup data source using jndi name ["
						+ dataSourceJndiName + "]");
				ise.initCause(e);
				throw ise;
			}
		}

		return jdbcTemplate;
	}

	/**
	 * <p>
	 * To set the JdbcTemplate instance into this adaptor, ease for unit
	 * testing, such as test case can use Mock DataSource and put into jdbc
	 * template, and start testing Jdbc DAO class.
	 * 
	 * <p>
	 * Also the accessor can supplied specific jdbc template with data source
	 * provided other than the default one mentioned in
	 * {@link #getJdbcTemplate()}
	 * 
	 * @param jdbcTemplate jdbc template instance to be set.
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		if (jdbcTemplate.getDataSource() == null) {
			throw new IllegalArgumentException("dataSource in 'jdbcTemplate' supplied must not be null.");
		}

		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Set the data source to be used by the jdbc template
	 * 
	 * @param dataSource
	 */
	public final void setDataSource(DataSource dataSource) {
		if (jdbcTemplate != null && jdbcTemplate.getDataSource() != null) {
			throw new IllegalArgumentException(
					"data source has been set into jdbc template, please don't try this again");
		}

		jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
