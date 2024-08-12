package com.integrosys.cms.batch.sibs.parameter.obj;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 5:19:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHostLawyer extends ISynchronizer {

    public static final String STATUS_ACTIVE = "A";
    public static final String STATUS_DELETED = "D";

    public String getLawyerCode();

    public void setLawyerCode(String lawyerCode);

    public String getLawyerType();

    public void setLawyerType(String lawyerType);

    public String getLawyerReferenceNumber();

    public void setLawyerReferenceNumber(String lawyerReferenceNumber);

    public String getLawyerName();

    public void setLawyerName(String lawyerName);

    public String getStatus();

    public void setStatus(String status);

    public String getRemarks();

    public void setRemarks(String remarks);

    Date getLastUpdatedDate();

    void setLastUpdatedDate(Date lastUpdatedDate);
}
