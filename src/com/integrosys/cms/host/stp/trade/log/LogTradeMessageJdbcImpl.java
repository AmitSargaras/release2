package com.integrosys.cms.host.stp.trade.log;

import com.integrosys.base.techinfra.dbsupport.ISequenceFormatter;
import com.integrosys.cms.host.stp.STPTransactionException;
import com.integrosys.cms.host.stp.log.ILogMessage;
import com.integrosys.cms.host.stp.log.ISTPLogMessageJdbc;
import com.integrosys.cms.host.stp.log.LogMessageJdbcImpl;
import com.integrosys.cms.host.stp.log.MessageLog;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chin Kok Cheong
 */

public class LogTradeMessageJdbcImpl extends LogMessageJdbcImpl {
    //FOR DB2
//	private static final String GET_SEQ_SQL = "VALUES nextval FOR CMS_TRADE_MSG_LOG_SEQ";
	//FOR ORACLE
	private static final String GET_SEQ_SQL = "SELECT CMS_TRADE_MSG_LOG_SEQ.NEXTVAL FROM DUAL";

    public LogTradeMessageJdbcImpl(){
        setNextSequenceSql(GET_SEQ_SQL);
    }

	public void persistSTPLogMessage(MessageLog logMsg) {

        if(logMsg instanceof TradeMessageLog){
            TradeMessageLog logMessage = (TradeMessageLog) logMsg;

            String query = "INSERT INTO cms_trade_message_log "
                    + "(log_id, master_trx_id, correlation_id, message_ref_num, message_type, message_id, publish_type, publish_date, source, "
                    + "request_msg_path, response_msg_path, response_msg_id, response_code, response_desc, "
                    + "end_processed_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            long seq = getJdbcTemplate().queryForLong(getNextSequenceSql());
            String seqString = "";
            try {
                seqString = getSequenceFormatter().formatSeq(String.valueOf(seq));
            }
            catch (Exception e) {
                IllegalStateException ise = new IllegalStateException("failed to format using formatter ["
                        + getSequenceFormatter() + "]; nested exception is " + e);
                ise.initCause(e);
                throw ise;
            }

            List argList = new ArrayList();
            argList.add(new Long(seqString));
            argList.add(logMessage.getMasterTrxId());
            argList.add(logMessage.getCorrelationId());
            argList.add(logMessage.getMessageReferenceNumber());
            argList.add(logMessage.getMessageType());
            argList.add(logMessage.getMessageId());
            argList.add(logMessage.getPublishType());
            argList.add(logMessage.getPublishDate());
            argList.add(logMessage.getSource());
            argList.add(logMessage.getRequestMessagePath());
            argList.add(logMessage.getResponseMessagePath());
            argList.add(logMessage.getResponseMessageId());
            argList.add(logMessage.getResponseCode());
            argList.add(logMessage.getResponseDescription());
            argList.add(logMessage.getEndProcessedDate());

            getJdbcTemplate().update(query, argList.toArray());
        }else{
            throw new STPTransactionException("Fail to log STP message");
        }
	}

}