package com.integrosys.cms.batch.sibs.parameter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * <p>
 * Abstract implementation of <tt>SingleParameterLoader</tt>.
 * <p>
 * It's a very simple implementation, which will loop through the result set of
 * external data source, and synchronized with the internal data source one by
 * one.
 * <p>
 * Sub class to provide the query to retrieve the result from external source,
 * and determine what's the key for insertion or update into internal data
 * source.
 * @author Chong Jun Yong
 * 
 */
public abstract class AbstractSingleParameterLoader implements SingleParameterLoader {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate externalJdbcTemplate;

	private JdbcTemplate cmsJdbcTemplate;

	public void setCmsJdbcTemplate(JdbcTemplate cmsJdbcTemplate) {
		this.cmsJdbcTemplate = cmsJdbcTemplate;
	}

	public void setExternalJdbcTemplate(JdbcTemplate externalJdbcTemplate) {
		this.externalJdbcTemplate = externalJdbcTemplate;
	}

	public final void run() {
//        logger.debug("count for list getCmsInquiryQuery() ~~~~~~~~~~~~ " + cmsJdbcTemplate.queryForInt(getCmsInquiryQuery()));
//        logger.debug("list getExternalInquiryQuery() ~~~~~~~~~~~~ " + getExternalInquiryQuery());
//        logger.debug("count for list getExternalInquiryQuery() ~~~~~~~~~~~~ " + externalJdbcTemplate.queryForInt(getExternalInquiryQuery()));

//        externalJdbcTemplate.query(getExternalInquiryQuery(), new ResultSetExtractor() {
        externalJdbcTemplate.query(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                return connection.prepareStatement(getExternalInquiryQuery(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);    
            }
        }, new ResultSetExtractor() {

            public Object extractData(final ResultSet extrs) throws SQLException, DataAccessException {
                int totalProcessd = 0;
                while (extrs.next()) {
                    if (extrs.isFirst() && !getDependencyParamFlag()) {
                        // set all records' status as DELETED before start processing
                        cmsJdbcTemplate.update(getCmsUpdateQueryForDeletion());
                    }

                    totalProcessd ++;
                    final Object[] keys = getKeysValueBetweenDifferentSources(extrs);
                    cmsJdbcTemplate.query(getCmsInquiryQuery(), keys, new ResultSetExtractor() {

                        public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                            if (rs.next()) {
                                cmsJdbcTemplate.update(getCmsUpdateQuery(), getUpdateValuesFromExternal(extrs));
                            }
                            else {
                                if (!getDependencyParamFlag()) {
                                    cmsJdbcTemplate.update(getCmsInsertQuery(), getInsertValuesFromExternal(extrs));
                                }
                            }
                            return null;
                        }
//
                    });
                }

                logger.debug("Total Processed : " + totalProcessd);

                return null;
            }
        });

    }

	/**
	 * <p>
	 * The SQL query to inquiry the external data source, normally a select *
	 * from a table.
	 * <p>
	 * This will be the base result set, which this loader instance will loop
	 * through and synchronize it against the internal data source.
	 * @return The SQL query to inquiry the external data source
	 */
	protected abstract String getExternalInquiryQuery();

	/**
	 * <p>
	 * To construct a array of objects based on the result set of the external
	 * data source.
	 * <p>
	 * Then this array of objects will be used to retrieve the result of
	 * internal data source, hence, the order is very important.
	 * <p>
	 * Example of usage for this.
	 * 
	 * <pre>
	 * return new Object[] { new Long(externalResultSet.getLong(&quot;some_key_1&quot;)), externalResultSet.getString(&quot;code&quot;) };
	 * </pre>
	 * <p>
	 * <b>Note:</b> sub class not suppose to do 'funny' thing to the
	 * externalResultSet, such as .close(), .next(), just directly use the
	 * getXXX method.
	 * @param externalResultSet the result set come from the external data
	 *        source.
	 * @return the keys between the external data source and internal data
	 *         source based on the external result set, must be in order when
	 *         use with the {@link #getCmsInquiryQuery()}
	 * @throws SQLException if there is any error when using external result
	 *         set.
	 */
	protected abstract Object[] getKeysValueBetweenDifferentSources(ResultSet externalResultSet) throws SQLException;

	/**
	 * <p>
	 * The SQL query to inquiry the internal data source, based on the key
	 * provided from {@link #getKeysValueBetweenDifferentSources(ResultSet)},
	 * this should return single record.
	 * <p>
	 * Afterwhich, depend on the existence of the result return for this SQL
	 * query construct to do insert or update.
	 * @return The SQL query to inquiry the internal data source
	 */
	protected abstract String getCmsInquiryQuery();

	/**
	 * <p>
	 * The SQL query to insert a single record into internal data source, value
	 * to be inserted getting from
	 * {@link #getInsertValuesFromExternal(ResultSet)}, which mean the result is
	 * coming from external result set.
	 * @return The SQL query to insert a single record into internal data source
	 */
	protected abstract String getCmsInsertQuery();

	/**
	 * <p>
	 * The records getting from the external data source to be inserted into
	 * internal data source. It should be in order to tally with
	 * {@link #getCmsInquiryQuery()}.
	 * @param externalResultSet the result set come from the external data
	 *        source.
	 * @return values in order after getting from the external result set.
	 * @throws SQLException if there is any error when using external result
	 *         set.
	 */
	protected abstract Object[] getInsertValuesFromExternal(ResultSet externalResultSet) throws SQLException;

	/**
	 * <p>
	 * The SQL query to update a single record into internal data source, value
	 * to be updated getting from
	 * {@link #getInsertValuesFromExternal(ResultSet)}, which mean the result is
	 * coming from external result set.
	 * @return The SQL query to update a single record into internal data source
	 */
	protected abstract String getCmsUpdateQuery();

	/**
	 * <p>
	 * The records getting from the external data source to be updated into
	 * internal data source. It should be in order to tally with
	 * {@link #getCmsInquiryQuery()}.
	 * <p>
	 * The values might included the key value to be used to search the internal
	 * record. ie, last few values might be used for where clause of the
	 * {@link #getCmsUpdateQuery()}
	 * @param externalResultSet the result set come from the external data
	 *        source.
	 * @return values in order after getting from the external result set.
	 * @throws SQLException if there is any error when using external result
	 *         set.
	 */
	protected abstract Object[] getUpdateValuesFromExternal(ResultSet externalResultSet) throws SQLException;

	/**
	 * <p>
	 * The SQL query to update a single record with status=DELETED into internal data source, value
	 * to be updated getting from
	 * @return The SQL query to update a single record with status=DELETED into internal data source
	 */
	protected abstract String getCmsUpdateQueryForDeletion();

    /**
	 * <p>
	 * The SQL query to set flag for Dependency Parameter
	 * @return The SQL query to update flag=true for Dependency Parameter
	 */
    protected abstract boolean getDependencyParamFlag();

}
