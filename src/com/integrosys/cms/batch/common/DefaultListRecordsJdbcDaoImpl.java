package com.integrosys.cms.batch.common;

import java.lang.ref.SoftReference;
import java.sql.Types;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.Constants;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.BatchSqlUpdate;

/**
 * <p>
 * Default implementation of {@link ListRecordsDao} using JDBC routine.
 * <p>
 * At most this DAO will deal with 1 single table. Table name, Columns names,
 * SQL Type Names need to be provided. Sequence name for the ID field is
 * optional.
 * <p>
 * This DAO will use the JDBC's batch update feature to do bulk insert of
 * records into table. If the database not supported batch operation, normal
 * operation will be taken.
 * <p>
 * <b>Note</b> If there is <i>sequence name</i> provided, meaning the one of the
 * column names is ID field. In such case, SQL Parameter Type might not having
 * same count with the column names.
 * @author Chong Jun Yong
 * @see com.integrosys.cms.batch.common.mapping.DefaultListRecordsFieldSetMapper
 * @see org.springframework.jdbc.object.BatchSqlUpdate
 */
public class DefaultListRecordsJdbcDaoImpl extends JdbcDaoSupport implements ListRecordsDao {

	/** Query cache for current thread */
	private final ThreadLocal sqlQuery = new ThreadLocal();

	private String tableName;

	private String[] columnNames;

	/**
	 * Actual SQL parameter types value that is match the one in
	 * <tt>java.sql.Types</tt>
	 */
	private int[] sqlParameterTypes = new int[0];

	private String sequenceName;

	/** constant of <tt>java.sql.Types</tt> */
	private final Constants sqlTypeContants = new Constants(Types.class);

	/**
	 * Table name for this Jdbc to insert into.
	 * @param tableName DB table name
	 */
	public void setTableName(String tableName) {
		if (StringUtils.isBlank(tableName)) {
			throw new IllegalArgumentException("table name must be provided.");
		}
		this.tableName = tableName;
	}

	/**
	 * Column names required to feed into the table provided. There could be the
	 * case that <i>sequence name</i> is provided. Hence one of the column is
	 * <i>ID</i> field, and so SQL parameter type provided will not be the same
	 * count of this column names.
	 * @param columnNames column names of the table required for the feed
	 *        inserted into.
	 */
	public void setColumnNames(String[] columnNames) {
		if (ArrayUtils.isEmpty(columnNames)) {
			throw new IllegalArgumentException("column names must be provided.");
		}
		this.columnNames = columnNames;
	}

	/**
	 * <p>
	 * SQL Parameter types for the bind variables of those column names required
	 * to feed in from list records.
	 * <p>
	 * It <b>must</b> follow the field name of the <tt>java.sql.Types</tt> to
	 * declare the type of the DB columns.
	 * @param sqlParameterTypesValue a array of field name of SQL parameter in
	 *        <tt>java.sql.Types</tt>
	 * @see java.sql.Types
	 */
	public void setSqlParameterTypes(String[] sqlParameterTypesValue) {
		if (ArrayUtils.isEmpty(sqlParameterTypesValue)) {
			throw new IllegalArgumentException("'SQL Parameter Types' must be provided");
		}

		for (int i = 0; i < sqlParameterTypesValue.length; i++) {
			this.sqlParameterTypes = ArrayUtils.add(this.sqlParameterTypes, sqlTypeContants.asNumber(
					sqlParameterTypesValue[i].trim()).intValue());
		}
	}

	/**
	 * DB sequence name for the ID field for the table
	 * @param sequenceName sequence name for the ID field.
	 */
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public void persist(List feedList) {
		if (feedList == null || feedList.isEmpty()) {
			return;
		}

		String sql = buildSqlQuery();

		BatchSqlUpdate batch = new BatchSqlUpdate(getDataSource(), sql, this.sqlParameterTypes);

		Object[] feeds = feedList.toArray();
		for (int i = 0; i < feeds.length; i++) {
			SoftReference ref = new SoftReference(feeds[i]);
			List records = (List) ref.get();

			SoftReference recordsRef = new SoftReference(records.toArray());

			batch.update((Object[]) recordsRef.get());

			// clearing resource
			recordsRef.clear();
			recordsRef = null;
			records.clear();
			records = null;
			ref.clear();
			ref = null;
			feeds[i] = null;
		}

		feedList.clear();
		feeds = null;

		batch.compile();
		batch.flush();
		batch.reset();
		batch = null;
	}

	/**
	 * If there is one in Thread variable, then return or else, construct a new
	 * SQL Query based on the table name, columns name, and sequence
	 * name(optional)
	 * @return SQL Query used for persistent
	 */
	protected String buildSqlQuery() {
		if (sqlQuery.get() != null) {
			return (String) sqlQuery.get();
		}

		StringBuffer buf = new StringBuffer("INSERT INTO ").append(this.tableName).append(" (");

		for (int i = 0; i < this.columnNames.length; i++) {
			buf.append(this.columnNames[i]);
			if ((i + 1) < this.columnNames.length) {
				buf.append(",");
			}
		}

		buf.append(") VALUES (");

		String bindVariables = StringUtils.repeat("?,", columnNames.length
				- (StringUtils.isNotBlank(sequenceName) ? 1 : 0));

		if (this.sequenceName != null) {
			appendSequenceNextValString(buf);
		}

		buf.append(bindVariables.substring(0, bindVariables.length() - 1)).append(")");

		sqlQuery.set(buf.toString());

		return (String) sqlQuery.get();
	}

	/**
	 * If the Database is not DB2, sub class override this method.
	 */
	protected void appendSequenceNextValString(StringBuffer sqlBuf) {
		sqlBuf.append("NEXT VALUE FOR ").append(this.sequenceName).append(",");
	}
}
