package com.integrosys.cms.host.stp.proxy;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Sep 3, 2008
 * Time: 11:55:09 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IStpSyncProxy {

    public final static String REQUEST = "request-";
    public final static String RESPONSE = "response-";
    //Socket Message Header
    public final static String SOCKET_MSGLGH = "SKTMLEN";
    public final static String SOCKET_HDRTYP = "SKTHEAD";
    //DSP Message Header
    public final static String DSPHDR_SOURCEID = "I13SID";
    public final static String DSPHDR_MSGSTAT = "I13MSTA";
    public final static String DSPHDR_BANKIDNUM = "I13BIN";
    public final static String DSPHDR_NODE = "I13NODE";
    public final static String DSPHDR_SENARIONUM = "I13SSNO";
    public final static String DSPHDR_TXNCODE = "I13TRCD";
    public final static String DSPHDR_TRANMSNUM = "I13TMNO";
    public final static String DSPHDR_RECTOBELOAD = "I13NREC";
    public final static String DSPHDR_ERRTOBELOAD = "I13NERR";
    public final static String DSPHDR_USERID = "I13USER";
    public final static String DSPHDR_MOREIND = "I13MORE";
    //MBase Message Header
    public final static String MBASE_RESPCODE = "HDRIND";
    public final static String MBASE_USERID = "HDUSID";
    public final static String MBASE_REFNUM = "HDRNUM";
    public final static String MBASE_SRCID = "HDSRID";
    public final static String MBASE_DATAQUE = "HDRTDQ";
    public final static String MBASE_TRMLID = "HDTMID";
    public final static String MBASE_BANKNO = "HDBKNO";
    public final static String MBASE_BRANCHNO = "HDBRNO";
    public final static String MBASE_TRXCODE = "HDTXCD";
    public final static String MBASE_ACTCODE = "HDACCD";
    public final static String MBASE_TRXMODE = "HDTMOD";
    public final static String MBASE_NUMREC = "HDNREC";
    public final static String MBASE_RECIND = "HDMREC";
    public final static String MBASE_SRHMTHD = "HDSMTD";
    public final static String MBASE_RESPCODE1 = "HDRCD1";
    public final static String MBASE_RESPRECODE1 = "HDRRE1";
    public final static String MBASE_RESPRECODE2 = "HDRRE2";
    public final static String MBASE_RESPRECODE3 = "HDRRE3";
    public final static String MBASE_RESPRECODE4 = "HDRRE4";
    public final static String MBASE_RESPRECODE5 = "HDRRE5";
    public final static String MBASE_TRXDATE = "HDDTIN";
    public final static String MBASE_TRXTIME = "HDTMIN";
    public final static String MBASE_ACCTNO = "HDACTN";
    public final static String MBASE_ACCTTYPE = "HDACTY";
    public final static String MBASE_CIFNO = "HDCIFN";
    public final static String MBASE_UNQKEYOPT = "MBSOPT";
    public final static String MBASE_UNQKEY = "MBUKEY";

    public Object submitTask(String trxType, Object[] object) throws Exception;
}
