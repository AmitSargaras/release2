package com.integrosys.cms.ui.custgrpi.groupotrlimit;

import com.integrosys.cms.ui.common.TrxContextForm;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong 
 * Date: July 1, 2008
 */
public class GroupOtrLimitForm extends TrxContextForm implements java.io.Serializable {

    private String groupOtrLimitID;
    private String groupOtrLimitIDRef;

    private String otrLimitTypeCD;
    private String limitAmt;
    private String lastReviewedDt;
    private String desc;
    private String status;
    private String grpID;
    private String remarks;
    private String indexID;
    private String nextPage;
    private String itemType;
    private String currencyCD;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getGroupOtrLimitID() {
        return groupOtrLimitID;
    }

    public void setGroupOtrLimitID(String groupOtrLimitID) {
        this.groupOtrLimitID = groupOtrLimitID;
    }

    public String getGroupOtrLimitIDRef() {
        return groupOtrLimitIDRef;
    }

    public void setGroupOtrLimitIDRef(String groupOtrLimitIDRef) {
        this.groupOtrLimitIDRef = groupOtrLimitIDRef;
    }

    public String getOtrLimitTypeCD() {
        return otrLimitTypeCD;
    }

    public void setOtrLimitTypeCD(String otrLimitTypeCD) {
        this.otrLimitTypeCD = otrLimitTypeCD;
    }

    public String getLimitAmt() {
        return limitAmt;
    }

    public void setLimitAmt(String limitAmt) {
        this.limitAmt = limitAmt;
    }

    public String getLastReviewedDt() {
        return lastReviewedDt;
    }

    public void setLastReviewedDt(String lastReviewedDt) {
        this.lastReviewedDt = lastReviewedDt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGrpID() {
        return grpID;
    }

    public void setGrpID(String grpID) {
        this.grpID = grpID;
    }

    public String getIndexID() {
        return indexID;
    }

    public void setIndexID(String indexID) {
        this.indexID = indexID;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getCurrencyCD() {
        return currencyCD;
    }

    public void setCurrencyCD(String currencyCD) {
        this.currencyCD = currencyCD;
    }

    public String[][] getMapper() {
        String[][] input = {
                {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"},
                {CustGroupUIHelper.form_groupOtrLimitObj, "com.integrosys.cms.ui.custgrpi.groupotrlimit.GroupOtrLimitMapper"},

        };

        return input;
    }


    public String toString() {
        return "GroupOtrLimitForm{" +
                "groupOtrLimitID='" + groupOtrLimitID + '\'' +
                ", groupOtrLimitIDRef='" + groupOtrLimitIDRef + '\'' +
                ", otrLimitTypeCD='" + otrLimitTypeCD + '\'' +
                ", limitAmt='" + limitAmt + '\'' +
                ", lastReviewedDt='" + lastReviewedDt + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                ", grpID='" + grpID + '\'' +
                ", indexID='" + indexID + '\'' +
                ", nextPage='" + nextPage + '\'' +
                ", itemType='" + itemType + '\'' +
                ", currencyCD='" + currencyCD + '\'' +
                '}';
    }
}
