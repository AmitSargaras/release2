package com.integrosys.cms.host.stp.trade.log;

import com.integrosys.cms.host.stp.log.MessageLog;

import java.math.BigDecimal;
import java.util.Date;

/**
 * STP Message log to be persisted for tracking purpose
 *
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 *
 */
public class TradeMessageLog extends MessageLog {

	private String publishType;

	private Date publishDate;

    public String getPublishType() {
        return publishType;
    }

    public void setPublishType(String publishType) {
        this.publishType = publishType;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}