package com.integrosys.cms.ui.custgrpi.groupcreditgrade;

import com.integrosys.cms.ui.common.TrxContextForm;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;

public class GroupCreditGradeForm extends TrxContextForm implements java.io.Serializable {


    private String groupCreditGradeID;
    private String typeCD;
    private String ratingCD;
    private String ratingDt;
    private String expectedTrendRating;
    private String reason;
    private String status;
    private String groupCreditGradeIDRef;
    private String indexID;
    private String nextPage;
    private String itemType;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }


    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }


    public String getIndexID() {
        return indexID;
    }

    public void setIndexID(String indexID) {
        this.indexID = indexID;
    }


    public String getGroupCreditGradeID() {
        return groupCreditGradeID;
    }

    public void setGroupCreditGradeID(String groupCreditGradeID) {
        this.groupCreditGradeID = groupCreditGradeID;
    }

    public String getTypeCD() {
        return typeCD;
    }

    public void setTypeCD(String typeCD) {
        this.typeCD = typeCD;
    }

    public String getRatingCD() {
        return ratingCD;
    }

    public void setRatingCD(String ratingCD) {
        this.ratingCD = ratingCD;
    }

    public String getRatingDt() {
        return ratingDt;
    }

    public void setRatingDt(String ratingDt) {
        this.ratingDt = ratingDt;
    }

    public String getExpectedTrendRating() {
        return expectedTrendRating;
    }

    public void setExpectedTrendRating(String expectedTrendRating) {
        this.expectedTrendRating = expectedTrendRating;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroupCreditGradeIDRef() {
        return groupCreditGradeIDRef;
    }

    public void setGroupCreditGradeIDRef(String groupCreditGradeIDRef) {
        this.groupCreditGradeIDRef = groupCreditGradeIDRef;
    }


    public String[][] getMapper() {

        String[][] input = {
                {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"},
                {CustGroupUIHelper.form_groupCreditGradeObj, "com.integrosys.cms.ui.custgrpi.groupcreditgrade.GroupCreditGradeMapper"},

        };

        return input;
    }
}
