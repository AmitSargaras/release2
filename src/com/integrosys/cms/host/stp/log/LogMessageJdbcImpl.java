package com.integrosys.cms.host.stp.log;

import com.integrosys.base.techinfra.dbsupport.ISequenceFormatter;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * @author marvin
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 */

public class LogMessageJdbcImpl extends JdbcDaoSupport implements ISTPLogMessageJdbc {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private ISequenceFormatter sequenceFormatter;

	private String nextSequenceSql;
	//FOR DB2
//	private static final String GET_SEQ_SQL = "VALUES nextval FOR CMS_MQ_MSG_LOG_SEQ";
	//FOR ORACLE
	private static final String GET_SEQ_SQL = "SELECT CMS_MQ_MSG_LOG_SEQ.NEXTVAL FROM DUAL";

    public String getNextSequenceSql() {
        return nextSequenceSql;
    }

    public void setNextSequenceSql(String nextSequenceSql) {
        this.nextSequenceSql = nextSequenceSql;
    }

    public ISequenceFormatter getSequenceFormatter() {
		return sequenceFormatter;
	}

	public void setSequenceFormatter(ISequenceFormatter sequenceFormatter) {
		this.sequenceFormatter = sequenceFormatter;
	}

	public LogMessageJdbcImpl(String nextSequenceSql) {
		this.nextSequenceSql = nextSequenceSql;
	}

	public LogMessageJdbcImpl() {
		this.nextSequenceSql = GET_SEQ_SQL;
	}

	public void persistLogMessage(ILogMessage logMsg) {

		List argList = new ArrayList();
		int[] sqlTypes = null;

		long seq = getJdbcTemplate().queryForLong(nextSequenceSql);
		String seqString = "";
		try {
			seqString = getSequenceFormatter().formatSeq(String.valueOf(seq));
		}
		catch (Exception e) {
			IllegalStateException ise = new IllegalStateException("failed to format using formatter ["
					+ sequenceFormatter + "]; nested exception is " + e);
			ise.initCause(e);
			throw ise;
		}
		seq = new Long(seqString).longValue();

		argList.add(new Long(seq));
		sqlTypes = ArrayUtils.add(sqlTypes, Types.BIGINT);

		argList.add(logMsg.getSCIMsgId());
		sqlTypes = ArrayUtils.add(sqlTypes, Types.VARCHAR);

		if (logMsg.getPublishDate() != null) {
			argList.add(new Timestamp(logMsg.getPublishDate().getTime()));
		}
		else {
			argList.add((Timestamp) null);
		}
		sqlTypes = ArrayUtils.add(sqlTypes, Types.TIMESTAMP);

		if (logMsg.getReceivedDate() != null) {
			argList.add(new Timestamp(logMsg.getReceivedDate().getTime()));
		}
		else {
			argList.add((Timestamp) null);
		}
		sqlTypes = ArrayUtils.add(sqlTypes, Types.TIMESTAMP);

		if (logMsg.getSubscriberAckDate() != null) {
			argList.add(new Timestamp(logMsg.getSubscriberAckDate().getTime()));
		}
		else {
			argList.add((Timestamp) null);
		}
		sqlTypes = ArrayUtils.add(sqlTypes, Types.TIMESTAMP);

		argList.add(new Character(logMsg.getSubscriberResponseType()));
		argList.add(logMsg.getSubscriberResponseCode());
		argList.add(logMsg.getSubscriberResponseDesc());

		sqlTypes = ArrayUtils.add(sqlTypes, Types.VARCHAR);
		sqlTypes = ArrayUtils.add(sqlTypes, Types.VARCHAR);
		sqlTypes = ArrayUtils.add(sqlTypes, Types.VARCHAR);

		argList.add(logMsg.getReceivedMessage());
		sqlTypes = ArrayUtils.add(sqlTypes, Types.CLOB);

		argList.add(logMsg.getSubscriberResponseMessage());
		sqlTypes = ArrayUtils.add(sqlTypes, Types.CLOB);

		argList.add(logMsg.getErrorStackTrace());
		sqlTypes = ArrayUtils.add(sqlTypes, Types.CLOB);

		String insertLogMsgSql = "INSERT INTO cms_mq_message_log "
				+ "(log_id, sci_message_id, publish_date, received_msg_date, subscriber_ack_date, "
				+ "subscriber_response_type, subscriber_response_code, subscriber_response_desc, "
				+ "received_msg, subscriber_response_msg, error_stack_trace) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

		getJdbcTemplate().update(insertLogMsgSql, argList.toArray(), sqlTypes);
	}

    public void persistSTPLogMessage(MessageLog logMessage) {
		/*String query = "INSERT INTO cms_stp_message_log "
				+ "(log_id, message_ref_num, message_type, message_id, source, "
				+ "request_msg_path, response_msg_path, response_msg_id, response_code, response_desc, "
				+ "end_processed_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		long seq = getJdbcTemplate().queryForLong(nextSequenceSql);
		String seqString = "";
		try {
			seqString = getSequenceFormatter().formatSeq(String.valueOf(seq));
		}
		catch (Exception e) {
			IllegalStateException ise = new IllegalStateException("failed to format using formatter ["
					+ sequenceFormatter + "]; nested exception is " + e);
			ise.initCause(e);
			throw ise;
		}

		List argList = new ArrayList();
		argList.add(new Long(seqString));
		argList.add(logMessage.getMessageReferenceNumber());
		argList.add(logMessage.getMessageType());
		argList.add(logMessage.getMessageId());
		argList.add(logMessage.getSource());
		argList.add(logMessage.getRequestMessagePath());
		argList.add(logMessage.getResponseMessagePath());
		argList.add(logMessage.getResponseMessageId());
		argList.add(logMessage.getResponseCode());
		argList.add(logMessage.getResponseDescription());
		argList.add(logMessage.getEndProcessedDate());

		getJdbcTemplate().update(query, argList.toArray());*/
	}

}