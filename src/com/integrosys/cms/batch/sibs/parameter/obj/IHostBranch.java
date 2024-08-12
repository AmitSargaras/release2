package com.integrosys.cms.batch.sibs.parameter.obj;

import java.util.Date;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 3:09:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHostBranch extends ISynchronizer, Serializable {

//    public static final String ISLAMIC_INDICATOR = "I";
//    public static final String CONVENTIONAL_INDICATOR = "C";

    public long getHostBranchId();

    public void setHostBranchId(long hostBranchId);

    public String getBranchNumber();

    public void setBranchNumber(String branchNumber);

//    public String getBranchName();
//
//    public void setBranchName(String branchName);
//
//    public String getCountry();
//
//    public void setCountry(String country);
//
//    public String getState();
//
//    public void setState(String state);
//
//    public String getRcCode();
//
//    public void setRcCode(String rcCode);
//
//    public String getCacCode();
//
//    public void setCacCode(String cacCode);

    public String getCentreType();

    public void setCentreType(String centreType);

    public String getCentreCode();

    public void setCentreCode(String centreCode);

    public String getStatus();

    public void setStatus(String status);

    public Date getLastUpdatedDate();

    public void setLastUpdatedDate(Date lastUpdatedDate);

//    String getIslamicConventionalIndicator();
//
//    void setIslamicConventionalIndicator(String islamicConventionalIndicator);
//
//    public String getFisBranchId();
//
//    public void setFisBranchId(String fisBranchId);
//
//    public String getBankBranchId();
//
//    public void setBankBranchId(String bankBranchId);
//
//    public String getSptfBranchId();
//
//    public void setSptfBranchId(String sptfBranchId);
//
//    public Date getBranchOpeningDate();
//
//    public void setBranchOpeningDate(Date branchOpeningDate);
//
//    public String getBranchOpeningDateStr();
//
//    public void setBranchOpeningDateStr(String branchOpeningDateStr);
//
//    public String getClosedBranchIndicator();
//
//    public void setClosedBranchIndicator(String closedBranchIndicator);
}
