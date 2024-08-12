package com.integrosys.cms.batch.sibs.parameter.obj;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Oct 2, 2008 Time: 7:02:28 PM To
 * change this template use File | Settings | File Templates.
 */
public class OBPublicHoliday implements IPublicHoliday, Serializable {

	private static final long serialVersionUID = 5834307218643174801L;

	private static final String[] MATCHING_PROPERTIES = new String[] { "holidayDate", "countryCode", "stateCode" };

	private static final String[] IGNORED_PROPERTIES = new String[] { "holidayDate", "countryCode", "stateCode", "status",
			"lastUpdatedDate" };

	private Date holidayDate;

	private String holidayDateStr;

	private String countryCode;

	private String stateCode;

    private String description;

    private String status;

	private Date lastUpdatedDate;


    public Date getHolidayDate() {
        if (holidayDate == null) {
			holidayDate = parseJulianDate(getHolidayDateStr());
		}
		return holidayDate;
    }

    public void setHolidayDate(Date holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getHolidayDateStr() {
        return holidayDateStr;
    }

    public void setHolidayDateStr(String holidayDateStr) {
        if (holidayDateStr != null) {
			holidayDateStr = holidayDateStr.trim();
		}
		this.holidayDateStr = holidayDateStr;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	/****** Methods from ISynchronizer ******/
	public String[] getMatchingProperties() {
		return MATCHING_PROPERTIES;
	}

	public String[] getIgnoreProperties() {
		return IGNORED_PROPERTIES;
	}

	public void updatePropertiesForCreateUpdate(IParameterProperty paramProperty) {
		setStatus(ICMSConstant.STATE_ACTIVE);
		setLastUpdatedDate(new Date());

		trimStringAttributes();
	}

	public void updatePropertiesForDelete(IParameterProperty paramProperty) {
		setStatus(ICMSConstant.STATE_DELETED);
		setLastUpdatedDate(new Date());

		trimStringAttributes();
	}

	private void trimStringAttributes() {
		if (getCountryCode() != null) {
			setCountryCode(getCountryCode().trim());
		}
		if (getStateCode() != null) {
			setStateCode(getStateCode().trim());
		}
        if (getDescription() != null) {
			setDescription(getDescription().trim());
		}
    }

	private Date parseJulianDate(String dateStr) {
		Date parsedDate = null;
		if (dateStr != null && dateStr.length() == 7 && !("9999999").equals(dateStr)) {
			parsedDate = DateUtil.parseDate("yyyyDDD", dateStr);
		}

		return parsedDate;
	}

    public int hashCode() {
		final int prime = 31;
		int result = 1;
        result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
        result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
        result = prime * result + ((holidayDate == null) ? 0 : holidayDate.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OBPublicHoliday other = (OBPublicHoliday) obj;
        if (stateCode == null) {
			if (other.stateCode != null) {
				return false;
			}
		}
		else if (!stateCode.equals(other.stateCode)) {
			return false;
		}              
        if (countryCode == null) {
			if (other.countryCode != null) {
				return false;
			}
		}
		else if (!countryCode.equals(other.countryCode)) {
			return false;
		}
        if (holidayDate == null) {
			if (other.holidayDate != null) {
				return false;
			}
		}
		else if (!holidayDate.equals(other.holidayDate)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		}
		else if (!status.equals(other.status)) {
			return false;
		}
		return true;
	}

}