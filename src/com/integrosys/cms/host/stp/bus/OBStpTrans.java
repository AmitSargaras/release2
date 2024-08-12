package com.integrosys.cms.host.stp.bus;

import org.hibernate.Hibernate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Aug 24, 2008
 * Time: 11:32:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBStpTrans implements IStpTrans {
    private Long trxId;

    public Long getTrxId() {
        return trxId;
    }

    public void setTrxId(Long trxId) {
        this.trxId = trxId;
    }

    private Long masterTrxId;

    public Long getMasterTrxId() {
        return masterTrxId;
    }

    public void setMasterTrxId(Long masterTrxId) {
        this.masterTrxId = masterTrxId;
    }

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private String trxType;

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType;
    }

    private Timestamp creationDate;

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    private Timestamp lastUpdateDate;

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String curTrxHistoryId;

    public String getCurTrxHistoryId() {
        return curTrxHistoryId;
    }

    public void setCurTrxHistoryId(String curTrxHistoryId) {
        this.curTrxHistoryId = curTrxHistoryId;
    }

    private Long trxHistoryId;

    public Long getTrxHistoryId() {
        return trxHistoryId;
    }

    public void setTrxHistoryId(Long trxHistoryId) {
        this.trxHistoryId = trxHistoryId;
    }

    private Integer msgCount;

    public Integer getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(Integer msgCount) {
        this.msgCount = msgCount;
    }

    private String responseCode;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    private byte[] requestMsgStream;

    public byte[] getRequestMsgStream() {
        return requestMsgStream;
    }

    public void setRequestMsgStream(byte[] requestMsgStream) {
        this.requestMsgStream = requestMsgStream;
    }

    private Blob requestMsg;
    /**
     * Don't invoke this.  Used by Hibernate only.
     */
    public void setRequestMsg(Blob request) {
     this.requestMsg = request;
       /* if(request != null)
            this.requestMsgStream = this.toByteArray(request);
        else
            this.requestMsgStream = null;*/
    }

    public Blob getRequestMsg() {
    return this.requestMsg;
       /*  if(requestMsgStream != null)
            return Hibernate.createBlob(this.requestMsgStream);
        else
            return null;*/
    }

    private byte[] toByteArray(Blob fromBlob) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            return toByteArrayImpl(fromBlob, baos);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    private byte[] toByteArrayImpl(Blob fromBlob, ByteArrayOutputStream baos)
            throws SQLException, IOException {
        byte[] buf = new byte[4500];
        InputStream is = fromBlob.getBinaryStream();
        try {
            for (; ;) {
                int dataSize = is.read(buf);
                if (dataSize == -1) break;
                baos.write(buf, 0, dataSize);
            }
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                }
            }
        }
        return baos.toByteArray();
    }

    private Long trxUID;

    private Long referenceId;

    public Long getTrxUID() {
        return trxUID;
    }

    public void setTrxUID(Long trxUID) {
        this.trxUID = trxUID;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    private String opsDesc;

    public String getOpsDesc() {
        return opsDesc;
    }

    public void setOpsDesc(String opsDesc) {
        this.opsDesc = opsDesc;
    }

    private Set trxErrorSet;

    public Set getTrxErrorSet() {
        return trxErrorSet;
    }

    public void setTrxErrorSet(Set trxErrorSet) {
        this.trxErrorSet = trxErrorSet;
    }
}
