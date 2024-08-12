package com.integrosys.cms.app.custgrpi.bus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 15, 2007        ICustGrpIdentifier
 * Time: 11:54:18 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IGroupCreditGrade extends Serializable {


    public long getGroupCreditGradeID() ;
    public void setGroupCreditGradeID(long groupCreditGradeID) ;


    public String getTypeCD() ;
    public void setTypeCD(String typeCD) ;

    public String getRatingCD() ;
    public void setRatingCD(String ratingCD);

    public Date getRatingDt() ;
    public void setRatingDt(Date ratingDt) ;

    public String getExpectedTrendRating() ;
    public void setExpectedTrendRating(String expectedTrendRating) ;

    public String getReason() ;
    public void setReason(String reason) ;

    public String getStatus() ;
    public void setStatus(String status) ;



    public long getGroupCreditGradeIDRef();
    public void setGroupCreditGradeIDRef(long groupCreditGradeIDRef);


    public long getGrpID();
    public void setGrpID(long grpID) ;





}
