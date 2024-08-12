package com.integrosys.cms.host.stp.trade.bus;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 17, 2008
 * Time: 12:21:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITradeTransJdbc {
    
    /**
     * Method to get Stp transaction UID using seq from DB
     * @return Stp transaction UID
     */
    public String getSeqNum();

    /**
     * Method to get Stp transaction reference number to use in MBASE HDRNUM field
     * @return trx ref number
     */
    public String getTrxRefNum();

    /**
     * get Stp Biz Error Code From STP_COMMON_ERROR_CODE
     *
     * @return biz error code
     */
     public Map getTradeBizErrorCode();

    /**
     * get Stp Error Message From STP_COMMON_ERROR_CODE
     *
     * @return biz error code
     */
     public List getErrorMessage(String transactionId);

    /**
     * Retrieve common code category entry description irregardless the status
     * @param categoryCode
     * @param entryCode
     * @return
     */
    public String getCodeCategoryEntry(String categoryCode, String entryCode);
}