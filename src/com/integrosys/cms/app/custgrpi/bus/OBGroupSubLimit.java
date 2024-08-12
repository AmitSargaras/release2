package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 15, 2007
 * Time: 11:54:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class OBGroupSubLimit implements IGroupSubLimit {


     private long groupSubLimitID = ICMSConstant.LONG_INVALID_VALUE;
     private long groupSubLimitIDRef = ICMSConstant.LONG_INVALID_VALUE;

     private String subLimitTypeCD ;
     private Amount limitAmt ;
     private String currencyCD ;
     private Date  lastReviewedDt  ;
     private String description ;
     private String remarks ;
     private String status ;
     private long grpID  = ICMSConstant.LONG_INVALID_VALUE;


    public OBGroupSubLimit () {
    }

    public OBGroupSubLimit (IGroupSubLimit obj) {
        this();
        AccessorUtil.copyValue (obj, this);
    }

    public String getCurrencyCD() {
        return currencyCD;
    }

    public void setCurrencyCD(String currencyCD) {
        this.currencyCD = currencyCD;
    }


    public long getGroupSubLimitID() {
        return groupSubLimitID;
    }

    public void setGroupSubLimitID(long groupSubLimitID) {
        this.groupSubLimitID = groupSubLimitID;
    }

    public long getGroupSubLimitIDRef() {
        return groupSubLimitIDRef;
    }

    public void setGroupSubLimitIDRef(long groupSubLimitIDRef) {
        this.groupSubLimitIDRef = groupSubLimitIDRef;
    }

    public String getSubLimitTypeCD() {
        return subLimitTypeCD;
    }

    public void setSubLimitTypeCD(String subLimitTypeCD) {
        this.subLimitTypeCD = subLimitTypeCD;
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
        return "OBGroupSubLimit{" +
                "groupSubLimitID=" + groupSubLimitID +
                ", groupSubLimitIDRef=" + groupSubLimitIDRef +
                ", subLimitTypeCD='" + subLimitTypeCD + '\'' +
                ", limitAmt=" + limitAmt +
                ", lastReviewedDt=" + lastReviewedDt +
                ", description='" + description + '\'' +
                ", remarks='" + remarks + '\'' +
                ", status='" + status + '\'' +
                ", grpID=" + grpID +
                '}';
    }
}
