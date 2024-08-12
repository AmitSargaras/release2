package com.integrosys.cms.batch.sibs.parameter.obj;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 7:10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPublicHoliday extends ISynchronizer {

    public Date getHolidayDate();

    public void setHolidayDate(Date holidayDate);

    public String getHolidayDateStr();

    public void setHolidayDateStr(String holidayDateStr);

    public String getCountryCode();

    public void setCountryCode(String countryCode);

    public String getStateCode();

    public void setStateCode(String stateCode);

    public String getDescription();

    public void setDescription(String description);

    public String getStatus();

	public void setStatus(String status);

	public Date getLastUpdatedDate();

	public void setLastUpdatedDate(Date lastUpdatedDate);

}