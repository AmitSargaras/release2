package com.integrosys.cms.ui.custgrpi.groupsublimit;

import com.integrosys.cms.ui.common.TrxContextForm;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;

public class GroupSubLimitForm extends TrxContextForm implements java.io.Serializable {


    private String groupSubLimitID;
    private String groupSubLimitIDRef;

    private String subLimitTypeCD;
    private String limitAmt;
    private String lastReviewedDt;
    private String desc;
    private String remarks;
    private String status;
    private String grpID;

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

    public String getCurrencyCD() {
        return currencyCD;
    }

    public void setCurrencyCD(String currencyCD) {
        this.currencyCD = currencyCD;
    }

    public String getGroupSubLimitID() {
        return groupSubLimitID;
    }

    public void setGroupSubLimitID(String groupSubLimitID) {
        this.groupSubLimitID = groupSubLimitID;
    }

    public String getGroupSubLimitIDRef() {
        return groupSubLimitIDRef;
    }

    public void setGroupSubLimitIDRef(String groupSubLimitIDRef) {
        this.groupSubLimitIDRef = groupSubLimitIDRef;
    }

    public String getSubLimitTypeCD() {
        return subLimitTypeCD;
    }

    public void setSubLimitTypeCD(String subLimitTypeCD) {
        this.subLimitTypeCD = subLimitTypeCD;
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

  /*  public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }*/

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


    public String[][] getMapper() {

        String[][] input = {
                {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"},
                {CustGroupUIHelper.form_groupSubLimitObj, "com.integrosys.cms.ui.custgrpi.groupsublimit.GroupSubLimitMapper"},

        };

        return input;
    }


    public String toString() {
        return "GroupSubLimitForm{" +
                "groupSubLimitID='" + groupSubLimitID + '\'' +
                ", groupSubLimitIDRef='" + groupSubLimitIDRef + '\'' +
                ", subLimitTypeCD='" + subLimitTypeCD + '\'' +
                ", limitAmt='" + limitAmt + '\'' +
                ", lastReviewedDt='" + lastReviewedDt + '\'' +
                ", desc='" + desc + '\'' +
//                ", remarks='" + remarks + '\'' +
                ", status='" + status + '\'' +
                ", grpID='" + grpID + '\'' +
                ", indexID='" + indexID + '\'' +
                ", nextPage='" + nextPage + '\'' +
                ", itemType='" + itemType + '\'' +
                ", currencyCD='" + currencyCD + '\'' +
                '}';
    }
}
