package com.integrosys.cms.host.stp.trade.common;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Aug 27, 2008
 * Time: 11:34:51 AM
 * To change this template use File | Settings | File Templates.
 *
 * @author Andy Wong
 * @since 24 Sept 2008
 * Amendment: extends OFAException for more indept logging
 */
public class TradeCommonException extends OFAException {
    private Long trxId;
    private String errorDesc;

    /**
     * Default Constructor
     */
    public TradeCommonException() {
        super();
    }

    /**
     * Constructor
     *
     * @param t - Throwable
     */
    public TradeCommonException(Throwable t) {
        super(t);
    }

    /**
     * Constructor
     *
     * @param msg - message String
     * @param t   - Throwable
     */
    public TradeCommonException(String msg, Throwable t) {
        super(msg, t);
        setErrorDesc(msg);
    }

    public TradeCommonException(String errCode, String msg, Throwable t) {
        super(msg, t);
        setErrorCode(errCode);
        setErrorDesc(msg);
    }

    /**
     * Constructor for Stp Exception to persist error code desc to DB
     *
     * @param trxId
     * @param msg
     */
    public TradeCommonException(Long trxId, String errCode, String msg) {
        super(msg);
        setTrxId(trxId);
        setErrorCode(errCode);
        setErrorDesc(msg);
    }

    public Long getTrxId() {
        return trxId;
    }

    public void setTrxId(Long trxId) {
        this.trxId = trxId;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}