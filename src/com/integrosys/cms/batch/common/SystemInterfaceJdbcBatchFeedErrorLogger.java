package com.integrosys.cms.batch.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.FieldSet;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * <p>
 * Implementation of {@link BatchFeedErrorLogger} using the Jdbc to persist the
 * error into SI_ERROR_LOG table.
 * <p>
 * {@link BatchFeedError#getDetailMessage()} will be logged into ERROR_MSG
 * column prefix with the line number, for latter retrival.
 * <p>
 * <b>Must</b> provide the system id (via {@link #setSystemId(String)}) and
 * interface id (via {@link #setInterfaceId(String)}) to facilitate the usage on
 * SI_ERROR_LOG table.
 * @author Chong Jun Yong
 * 
 */
public class SystemInterfaceJdbcBatchFeedErrorLogger implements BatchFeedErrorLogger {

	private final Logger logger = LoggerFactory.getLogger(SystemInterfaceJdbcBatchFeedErrorLogger.class);

	/**
	 * Default value of count to search for key value inside
	 * <tt>keyValueFieldSetIndexMap</tt>
	 */
	private static final int DEFAULT_KEY_VALUE_COUNT = 5;

	/** Default error code for message format error. */
	private static final String DEFAULT_MSG_FORMAT_ERROR_CODE = "TechErrSI001";

	private JdbcTemplate jdbcTemplate;

	private String systemId;

	private String interfaceId;

	private LineTokenizer lineTokenizer = new DelimitedLineTokenizer();

	private Map keyValueFieldSetIndexMap;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	/**
	 * <p>
	 * Tokenizer used to tokenize {@link BatchFeedError#getLineString()}, as to
	 * retrieve whichever value for the keyX_value inside SI_ERROR_LOG table.
	 * Default will be the <tt>DelimitedLineTokenizer</tt>, the one used for
	 * CSV.
	 * <p>
	 * <b>Tips</b> Tokenizer could be the same used to tokenize the reading
	 * feeds.
	 * @param lineTokenizer the line tokenizer.
	 */
	public void setLineTokenizer(LineTokenizer lineTokenizer) {
		this.lineTokenizer = lineTokenizer;
	}

	/**
	 * To set the key value to field set index map, key is "keyX_value" where X
	 * = 1,2,3,4,5 , value is the index number (to be used to retrieve the value
	 * from FielSet tokenized from the {@link BatchFeedError#getLineString()}
	 * using LineTokenizer (via {@link #setLineTokenizer(LineTokenizer)})
	 * @param keyValueFieldSetIndexMap key is keyX_value, value is the index
	 */
	public void setKeyValueFieldSetIndexMap(Map keyValueFieldSetIndexMap) {
		this.keyValueFieldSetIndexMap = keyValueFieldSetIndexMap;
	}

	public void log(BatchFeedError error) {
		StringBuffer buf = new StringBuffer("INSERT INTO si_error_log ");
		buf.append("(time_stamp, system_id, interface_id, key1_value, key2_value, ");
		buf.append("key3_value, key4_value, key5_value, error_code, error_msg) ");
		buf.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		Object[] args = prepareArguments(error);

		try {
			this.jdbcTemplate.update(buf.toString(), args);
		}
		catch (DataAccessException ex) {
			logger.error("failed to persist error [" + error + "] into error log table, please verify.", ex);
		}
	}

	public void log(Collection batchFeedErrors) {
		for (Iterator itr = batchFeedErrors.iterator(); itr.hasNext();) {
			BatchFeedError error = (BatchFeedError) itr.next();
			log(error);
		}
	}

	protected Object[] prepareArguments(BatchFeedError error) {
		List args = new ArrayList();
		args.add(new Date());
		args.add(this.systemId);
		args.add(this.interfaceId);
		FieldSet fieldSet = this.lineTokenizer.tokenize(error.getLineString());

		for (int i = 1; i <= DEFAULT_KEY_VALUE_COUNT; i++) {
			Integer fieldSetIndex = (Integer) this.keyValueFieldSetIndexMap.get("key" + i + "_value");
			if (fieldSetIndex != null) {
				args.add(fieldSet.readString(fieldSetIndex.intValue()));
			}
			else {
				args.add(null);
			}
		}
		args.add(DEFAULT_MSG_FORMAT_ERROR_CODE);

		String errorMessage = "#" + error.getLineNumber() + " - " + error.getDetailMessage();
		args.add(errorMessage);

		return args.toArray();
	}
}
