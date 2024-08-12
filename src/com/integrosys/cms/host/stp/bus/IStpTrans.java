package com.integrosys.cms.host.stp.bus;

import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: USER
 * Date: Aug 27, 2008
 * Time: 5:23:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IStpTrans {
    Long getTrxId();

    void setTrxId(Long trxId);

    Long getMasterTrxId();

    void setMasterTrxId(Long masterTrxId);

    Long getUserId();

    void setUserId(Long userId);

    String getTrxType();

    void setTrxType(String trxType);

    Timestamp getCreationDate();

    void setCreationDate(Timestamp creationDate);

    Timestamp getLastUpdateDate();

    void setLastUpdateDate(Timestamp lastUpdateDate);

    String getStatus();

    void setStatus(String status);

    String getCurTrxHistoryId();

    void setCurTrxHistoryId(String curTrxHistoryId);

    Long getTrxHistoryId();

    void setTrxHistoryId(Long trxHistoryId);

    Integer getMsgCount();

    void setMsgCount(Integer msgCount);

    String getResponseCode();

    void setResponseCode(String responseCode);

    byte[] getRequestMsgStream();

    void setRequestMsgStream(byte[] requestMsgStream);

    Long getTrxUID();

    void setTrxUID(Long trxUID);

    Long getReferenceId();

    void setReferenceId(Long referenceId);

    String getOpsDesc();

    void setOpsDesc(String opsDesc);

    Set getTrxErrorSet();

    void setTrxErrorSet(Set trxErrorSet);
  
}
