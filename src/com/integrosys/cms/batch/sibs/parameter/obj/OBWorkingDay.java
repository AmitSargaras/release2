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
public class OBWorkingDay implements IWorkingDay, Serializable {

	private static final long serialVersionUID = -4431874785761612048L;

	private static final String[] MATCHING_PROPERTIES = new String[] { "processingDay", "branch" };

	private static final String[] IGNORED_PROPERTIES = new String[] { "processingDay", "branch", "status",
			"lastUpdatedDate" };

	private Date processingDay;

	private String processingDayStr;

	private String bizDayFlag;

	private String branch;

	private String status;

	private Date lastUpdatedDate;

	public Date getProcessingDay() {
		if (processingDay == null) {
			processingDay = parseJulianDate(getProcessingDayStr());
		}
		return processingDay;
	}

	public void setProcessingDay(Date processingDay) {
		this.processingDay = processingDay;
	}

	public String getProcessingDayStr() {
		// if (CommonUtil.isEmpty(processingDayStr) && processingDay != null) {
		// processingDayStr = convertToJulianDate(processingDay);
		// }

		return processingDayStr;
	}

	public void setProcessingDayStr(String processingDayStr) {
		if (processingDayStr != null) {
			processingDayStr = processingDayStr.trim();
		}
		this.processingDayStr = processingDayStr;
	}

	public String getBizDayFlag() {
		return bizDayFlag;
	}

	public void setBizDayFlag(String bizDayFlag) {
		this.bizDayFlag = bizDayFlag;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
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
		if (getBizDayFlag() != null) {
			setBizDayFlag(getBizDayFlag().trim());
		}
		if (getBranch() != null) {
			setBranch(getBranch().trim());
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
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((processingDay == null) ? 0 : processingDay.hashCode());
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
		OBWorkingDay other = (OBWorkingDay) obj;
		if (branch == null) {
			if (other.branch != null) {
				return false;
			}
		}
		else if (!branch.equals(other.branch)) {
			return false;
		}
		if (processingDay == null) {
			if (other.processingDay != null) {
				return false;
			}
		}
		else if (!processingDay.equals(other.processingDay)) {
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
