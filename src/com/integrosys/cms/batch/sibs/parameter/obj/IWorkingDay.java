package com.integrosys.cms.batch.sibs.parameter.obj;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 7:10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IWorkingDay extends ISynchronizer {

    public Date getProcessingDay();

    public void setProcessingDay(Date processingDay);

    public String getProcessingDayStr();

    public void setProcessingDayStr(String processingDayStr);

    public String getBizDayFlag();

    public void setBizDayFlag(String bizDayFlag);

    public String getBranch();

    public void setBranch(String branch);

    public String getStatus();

    public void setStatus(String status);

    public Date getLastUpdatedDate();

    public void setLastUpdatedDate(Date lastUpdatedDate);

}
