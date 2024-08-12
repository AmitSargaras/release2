package com.integrosys.base.techinfra.orm;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerationException;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.mapping.Table;
import org.hibernate.type.Type;

import com.integrosys.base.techinfra.dbsupport.ISequenceFormatter;

/**
 * <p>
 * A hibernate id generator which append date in front of zero-padded sequence
 * 
 * <p>
 * Example : a sequence "SEQ_LIMIT_PROFILE" which return 1, and that day is 30
 * May 2008 then the Primary Key will be 20080530000000001. The zero padded will be
 * defined in ofa.properties, "sequence.formatter.pad.length", Default is '9'.
 * 
 * 
 * @author Chong Jun Yong
 * @since 1.0
 * @see com.integrosys.base.techinfra.dbsupport.DateSequencerFormatter
 */
public class DateAppendedSequenceGenerator implements PersistentIdentifierGenerator, Configurable {

	/**
	 * The sequence parameter
	 */
	public static final String SEQUENCE = "sequence";

	/**
	 * The parameters parameter, appended to the create sequence DDL. For
	 * example (Oracle):
	 * <tt>INCREMENT BY 1 START WITH 1 MAXVALUE 100 NOCACHE</tt>.
	 */
	public static final String PARAMETERS = "parameters";

	public static final String APPENDED_DATE_FORMATTER = "formatter";

	public static final String ID_CLASS_TYPE = "type";

	public static final String DEFAULT_DATE_FORMATTER_OBJ = "com.integrosys.base.techinfra.dbsupport.DateSequencerFormatter";

	public static final String DEFAULT_ID_CLASS_TYPE = "java.lang.Long";

	private String sequenceName;

	private String parameters;

	private Type identifierType;

	private String sql;

	private ISequenceFormatter formatter;

	private Class idClassTypeClass;

	private static final Log log = LogFactory.getLog(DateAppendedSequenceGenerator.class);

	public void configure(Type type, Properties params, Dialect dialect) throws MappingException {
		sequenceName = ConfigurationHelper.getString(SEQUENCE, params, "hibernate_sequence");
		parameters = params.getProperty(PARAMETERS);
		String schemaName = params.getProperty(SCHEMA);
		String catalogName = params.getProperty(CATALOG);
		String appendedDateFormatter = params.getProperty(APPENDED_DATE_FORMATTER);
		String idClassType = params.getProperty(ID_CLASS_TYPE);

		if (appendedDateFormatter == null) {
			appendedDateFormatter = DEFAULT_DATE_FORMATTER_OBJ;
		}

		if (idClassType == null) {
			idClassType = DEFAULT_ID_CLASS_TYPE;
		}

		if (sequenceName.indexOf('.') < 0) {
			sequenceName = Table.qualify(catalogName, schemaName, sequenceName);
		}

		try {
			this.formatter = (ISequenceFormatter) Class.forName(appendedDateFormatter).newInstance();
		}
		catch (Throwable t) {
			log.error("failed to instantiate class [" + formatter + "]", t);
			throw new MappingException("failed to instantiate class [" + formatter + "]", t);
		}

		try {
			this.idClassTypeClass = Class.forName(idClassType);
		}
		catch (Throwable t) {
			log.error("failed to instantiate class [" + idClassType + "]", t);
			throw new MappingException("failed to instantiate class [" + idClassType + "]", t);
		}

		this.identifierType = type;
		sql = dialect.getSequenceNextValString(sequenceName);
	}
	
	public static Serializable getSerializableValue(ResultSet rs, Type type)
			throws SQLException, IdentifierGenerationException {
		Class clazz = type.getReturnedClass();
		if (clazz == Long.class) {
			return new Long(rs.getLong(1));
		}
		if (clazz == Integer.class) {
			return new Integer(rs.getInt(1));
		}
		if (clazz == Short.class) {
			return new Short(rs.getShort(1));
		}
		if (clazz == String.class) {
			return rs.getString(1);
		}

		throw new IdentifierGenerationException(
				"this id generator generates long, integer, short or string");
	}

	public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {

		try {
			PreparedStatement st = session.connection().prepareStatement(sql);
			try {
				ResultSet rs = st.executeQuery();
				try {
					rs.next();
					Serializable result = getSerializableValue(rs, identifierType);
					if (log.isDebugEnabled()) {
						log.debug("Sequence identifier generated: " + result);
					}

					result = this.formatter.formatSeq(result.toString());

					return getResultBasedOnIdClassType(result);

				}
				finally {
					rs.close();
				}
			}
			finally {
				st.close();
			}
		}
		catch (SQLException sqle) {
			throw new IdentifierGenerationException(
					"this id generator generates long, integer, short or string");
		}
		catch (Throwable t) {
			log.error("error on formatting sequence using [" + formatter + "]", t);
			throw new HibernateException("error on formatting sequence using [" + formatter + "]", t);
		}

	}

	protected Serializable getResultBasedOnIdClassType(Serializable result) {
		if (idClassTypeClass == String.class) {
			return result;
		}
		else if (idClassTypeClass == Long.class) {
			return new Long((String) result);
		}
		else if (idClassTypeClass == Integer.class) {
			return new Integer((String) result);
		}
		else {
			throw new IllegalStateException("unknown id class type class: [" + idClassTypeClass.getName() + "]");
		}
	}

	public String[] sqlCreateStrings(Dialect dialect) throws HibernateException {
		String[] ddl = dialect.getCreateSequenceStrings(sequenceName);
		if (parameters != null) {
			ddl[ddl.length - 1] += ' ' + parameters;
		}
		return ddl;
	}

	public String[] sqlDropStrings(Dialect dialect) throws HibernateException {
		return dialect.getDropSequenceStrings(sequenceName);
	}

	public Object generatorKey() {
		return sequenceName;
	}

	public String getSequenceName() {
		return sequenceName;
	}

}
