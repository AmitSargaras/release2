package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.util.AccessorUtil;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 15, 2007
 * Time: 11:54:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class OBGroupCreditGrade implements IGroupCreditGrade {


     private long groupCreditGradeID = ICMSConstant.LONG_INVALID_VALUE;
     private String typeCD ;
     private String ratingCD ;
     private Date ratingDt ;
     private String expectedTrendRating ;
     private String reason ;
     private String status ;
     private long groupCreditGradeIDRef = ICMSConstant.LONG_INVALID_VALUE;

     private long grpID  = ICMSConstant.LONG_INVALID_VALUE;


     public OBGroupCreditGrade () {
    }

    public OBGroupCreditGrade (IGroupCreditGrade obj) {
        this();
        AccessorUtil.copyValue (obj, this);
    }

    public long getGrpID() {
        return grpID;
    }

    public void setGrpID(long grpID) {
        this.grpID = grpID;
    }


    public long getGroupCreditGradeID() {
        return groupCreditGradeID;
    }

    public void setGroupCreditGradeID(long groupCreditGradeID) {
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

    public Date getRatingDt() {
        return ratingDt;
    }

    public void setRatingDt(Date ratingDt) {
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

    public void setStatus(String deletedInd) {
        this.status = deletedInd;
    }

    public long getGroupCreditGradeIDRef() {
        return groupCreditGradeIDRef;
    }

    public void setGroupCreditGradeIDRef(long groupCreditGradeIDRef) {
        this.groupCreditGradeIDRef = groupCreditGradeIDRef;
    }


    public String toString() {
        return "OBGroupCreditGrade{" +
                "groupCreditGradeID=" + groupCreditGradeID +
                ", typeCD='" + typeCD + '\'' +
                ", ratingCD='" + ratingCD + '\'' +
                ", ratingDt=" + ratingDt +
                ", expectedTrendRating='" + expectedTrendRating + '\'' +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                ", groupCreditGradeIDRef=" + groupCreditGradeIDRef +
                ", grpID=" + grpID +
                '}';
    }
}
