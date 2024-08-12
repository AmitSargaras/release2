package com.integrosys.cms.batch.sibs.parameter.obj;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 6:21:32 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHostInsuranceCompany extends ISynchronizer {

    public String getInsNumber();

    public void setInsNumber(String insNumber);

    public String getInsName();

    public void setInsName(String insName);

    public String getInsAddress();

    public void setInsAddress(String insAddress);

    public String getInsCityState();

    public void setInsCityState(String insCityState);

    public String getInsPhone();

    public void setInsPhone(String insPhone);

    public BigDecimal getReservePercent();

    public void setReservePercent(BigDecimal reservePercent);

    public String getPanelIndicator();

    public void setPanelIndicator(String panelIndicator);

    public String getStatus();

    public void setStatus(String status);

    public Date getLastUpdatedDate();

    public void setLastUpdatedDate(Date lastUpdatedDate);
}
