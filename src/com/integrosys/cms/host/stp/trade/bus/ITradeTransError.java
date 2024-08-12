package com.integrosys.cms.host.stp.trade.bus;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Aug 27, 2008
 * Time: 5:24:29 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITradeTransError {

    Long getErrorId();

    void setErrorId(Long errorId);

    Long getTrxUID();

    void setTrxUID(Long trxId);

    String getErrorCode();

    void setErrorCode(String errorCode);

    String getErrorDesc();

    void setErrorDesc(String errorDesc);
}