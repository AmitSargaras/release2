package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: June 27, 2008
 * Time: 11:54:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class OBGroupOtrLimit implements IGroupOtrLimit {


     private long groupOtrLimitID = ICMSConstant.LONG_INVALID_VALUE;
     private long groupOtrLimitIDRef = ICMSConstant.LONG_INVALID_VALUE;

     private String otrLimitTypeCD ;
     private Amount limitAmt ;
     private String currencyCD ;
     private Date  lastReviewedDt  ;
     private String description ;
     private String remarks ;
     private String status ;
     private long grpID  = ICMSConstant.LONG_INVALID_VALUE;


    public OBGroupOtrLimit() {
    }

    public OBGroupOtrLimit(IGroupOtrLimit obj) {
        this();
        AccessorUtil.copyValue (obj, this);
    }

    public String getCurrencyCD() {
        return currencyCD;
    }

    public void setCurrencyCD(String currencyCD) {
        this.currencyCD = currencyCD;
    }


    public long getGroupOtrLimitID() {
        return groupOtrLimitID;
    }

    public void setGroupOtrLimitID(long groupOtrLimitID) {
        this.groupOtrLimitID = groupOtrLimitID;
    }

    public long getGroupOtrLimitIDRef() {
        return groupOtrLimitIDRef;
    }

    public void setGroupOtrLimitIDRef(long groupOtrLimitIDRef) {
        this.groupOtrLimitIDRef = groupOtrLimitIDRef;
    }

    public String getOtrLimitTypeCD() {
        return otrLimitTypeCD;
    }

    public void setOtrLimitTypeCD(String otrLimitTypeCD) {
        this.otrLimitTypeCD = otrLimitTypeCD;
    }

    public Amount getLimitAmt() {
        return limitAmt;
    }

    public void setLimitAmt(Amount limitAmt) {
        this.limitAmt = limitAmt;
    }

    public Date getLastReviewedDt() {
        return lastReviewedDt;
    }

    public void setLastReviewedDt(Date lastReviewedDt) {
        this.lastReviewedDt = lastReviewedDt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getGrpID() {
        return grpID;
    }

    public void setGrpID(long grpID) {
        this.grpID = grpID;
    }


    public String toString() {
        return "OBGroupOtrLimit{" +
                "groupOtrLimitID=" + groupOtrLimitID +
                ", groupOtrLimitIDRef=" + groupOtrLimitIDRef +
                ", otrLimitTypeCD='" + otrLimitTypeCD + '\'' +
                ", limitAmt=" + limitAmt +
                ", lastReviewedDt=" + lastReviewedDt +
                ", description='" + description + '\'' +
                ", remarks='" + remarks + '\'' +
                ", status='" + status + '\'' +
                ", grpID=" + grpID +
                '}';
    }
}