package com.integrosys.cms.batch.sibs.parameter.obj;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 3:14:25 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ICountry extends ISynchronizer {

    public long getCountryID();

    public void setCountryID(long countryID);

    public String getCountryCode();

    public void setCountryCode(String countryCode);

    public String getCountryName();

    public void setCountryName(String countryName);

    Date getUpdateDate();

    void setUpdateDate(Date updateDate);

    String getUpdateStatusIndicator();

    void setUpdateStatusIndicator(String updateStatusIndicator);
}
