package com.integrosys.cms.batch.sibs.parameter.obj;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 8:52:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHostCentre extends ISynchronizer {
    public String getCentreType();

    public void setCentreType(String centreType);

    public String getCentreCode();

    public void setCentreCode(String centreCode);

    public String getCentreName();

    public void setCentreName(String centreName);

    public boolean getHasBranch();

    public void setHasBranch(boolean hasBranch);

    public String getHasBranchStr();

    public void setHasBranchStr(String hasBranchStr);

    public String getStatus();

    public void setStatus(String status);

    public Date getLastUpdatedDate();

    public void setLastUpdatedDate(Date lastUpdatedDate);
}
