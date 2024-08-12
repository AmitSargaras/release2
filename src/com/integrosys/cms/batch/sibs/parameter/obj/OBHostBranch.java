package com.integrosys.cms.batch.sibs.parameter.obj;

import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Oct 2, 2008 Time: 3:10:48 PM To
 * change this template use File | Settings | File Templates.
 */
public class OBHostBranch implements IHostBranch {

	private static final long serialVersionUID = 5834307218643174801L;

	private static final String[] MATCHING_PROPERTIES = new String[] { "branchNumber", "centreType" };

	private static final String[] IGNORED_PROPERTIES = new String[] { "hostBranchId", "branchNumber", "centreType",
			"status", "lastUpdatedDate" };

	private long hostBranchId = ICMSConstant.LONG_INVALID_VALUE;

	private String branchNumber;

	private String centreType;

	private String centreCode;

	private String status;

	private Date lastUpdatedDate;

	public long getHostBranchId() {
		return hostBranchId;
	}

	public void setHostBranchId(long hostBranchId) {
		this.hostBranchId = hostBranchId;
	}

	public String getBranchNumber() {
		return branchNumber;
	}

	public void setBranchNumber(String branchNumber) {
		this.branchNumber = branchNumber;
	}

	public String getCentreType() {
		return centreType;
	}

	public void setCentreType(String centreType) {
		this.centreType = centreType;
	}

	public String getCentreCode() {
		return centreCode;
	}

	public void setCentreCode(String centreCode) {
		this.centreCode = centreCode;
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

	/****** Methods from IDependency ******/
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
		if (getBranchNumber() != null) {
			setBranchNumber(getBranchNumber().trim());
		}
		if (getCentreType() != null) {
			setCentreType(getCentreType().trim());
		}
		if (getCentreCode() != null) {
			setCentreCode(getCentreCode().trim());
		}
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branchNumber == null) ? 0 : branchNumber.hashCode());
		result = prime * result + ((centreType == null) ? 0 : centreType.hashCode());
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
		OBHostBranch other = (OBHostBranch) obj;
		if (branchNumber == null) {
			if (other.branchNumber != null) {
				return false;
			}
		}
		else if (!branchNumber.equals(other.branchNumber)) {
			return false;
		}
		if (centreType == null) {
			if (other.centreType != null) {
				return false;
			}
		}
		else if (!centreType.equals(other.centreType)) {
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
