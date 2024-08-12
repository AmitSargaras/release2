package com.integrosys.cms.batch.sibs.parameter.obj;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Oct 2, 2008 Time: 8:41:25 PM To
 * change this template use File | Settings | File Templates.
 */
public class OBHostCentre implements IHostCentre, Serializable {

	private static final long serialVersionUID = -6143594031381497L;

	private static final String[] MATCHING_PROPERTIES = new String[] { "centreType", "centreCode" };

	private static final String[] IGNORED_PROPERTIES = new String[] { "centreType", "centreCode", "status",
			"lastUpdatedDate" };

	private String centreType;

	private String centreCode;

	private String centreName;

	private boolean hasBranch;

	private String status;

	private Date lastUpdatedDate;

	private String hasBranchStr;

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

	public String getCentreName() {
		return centreName;
	}

	public void setCentreName(String centreName) {
		this.centreName = centreName;
	}

	public boolean getHasBranch() {
		return hasBranch;
	}

	public void setHasBranch(boolean hasBranch) {
		this.hasBranch = hasBranch;
	}

	public String getHasBranchStr() {
		return hasBranchStr;
	}

	public void setHasBranchStr(String hasBranchStr) {
		this.hasBranchStr = hasBranchStr;
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
		if (getCentreType() != null) {
			setCentreType(getCentreType().trim());
		}
		if (getCentreCode() != null) {
			setCentreCode(getCentreCode().trim());
		}
		if (getCentreName() != null) {
			setCentreName(getCentreName().trim());
		}
		if (getHasBranchStr() != null) {
			setHasBranchStr(getHasBranchStr().trim());

			if (ICMSConstant.TRUE_VALUE.equalsIgnoreCase(getHasBranchStr())) {
				setHasBranchStr(ICMSConstant.TRUE_VALUE);
			}
			else {
				setHasBranchStr(ICMSConstant.FALSE_VALUE);
			}
		}
		else {
			setHasBranchStr(ICMSConstant.FALSE_VALUE);
		}
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((centreCode == null) ? 0 : centreCode.hashCode());
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
		OBHostCentre other = (OBHostCentre) obj;
		if (centreCode == null) {
			if (other.centreCode != null) {
				return false;
			}
		}
		else if (!centreCode.equals(other.centreCode)) {
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
