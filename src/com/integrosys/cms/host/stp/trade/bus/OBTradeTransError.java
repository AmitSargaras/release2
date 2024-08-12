package com.integrosys.cms.host.stp.trade.bus;

import com.integrosys.cms.host.stp.bus.IStpTransError;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Aug 24, 2008
 * Time: 11:32:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBTradeTransError implements ITradeTransError {
    private Long errorId;
    private Long trxUID;

    public Long getErrorId() {
        return errorId;
    }

    public void setErrorId(Long errorId) {
        this.errorId = errorId;
    }

    public Long getTrxUID() {
        return trxUID;
    }

    public void setTrxUID(Long trxUID) {
        this.trxUID = trxUID;
    }

    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    private String errorDesc;

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}