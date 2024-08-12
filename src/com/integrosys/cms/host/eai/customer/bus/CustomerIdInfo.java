package com.integrosys.cms.host.eai.customer.bus;

import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

public class CustomerIdInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1058090481518375357L;

	private long cmsId;

	private String idNumber;

	private StandardCode idType;

	private String countryIssued;

	private Date idStartDate;

	public long getCmsId() {
		return cmsId;
	}

	public String getCountryIssued() {
		return countryIssued;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public String getIdStartDate() {
		return MessageDate.getInstance().getString(this.idStartDate);
	}

	public StandardCode getIdType() {
		return idType;
	}

	public Date getJDOIdStartDate() {
		return idStartDate;
	}

	public void setCmsId(long cmsId) {
		this.cmsId = cmsId;
	}

	public void setCountryIssued(String countryIssued) {
		this.countryIssued = countryIssued;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public void setIdStartDate(String idStartDate) {
		this.idStartDate = MessageDate.getInstance().getDate(idStartDate);
	}

	public void setIdType(StandardCode idType) {
		this.idType = idType;
	}

	public void setJDOIdStartDate(Date idStartDate) {
		this.idStartDate = idStartDate;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(getClass().getName());
		buf.append("@").append(this.hashCode());
		buf.append(" ID Number [").append(idNumber).append("], ");
		buf.append(" ID Type [").append(idType).append("], ");
		buf.append(" ID Start Date [").append(idStartDate).append("], ");
		buf.append(" Country Issued [").append(countryIssued).append("]");

		return null;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryIssued == null) ? 0 : countryIssued.hashCode());
		result = prime * result + ((idNumber == null) ? 0 : idNumber.hashCode());
		result = prime * result + ((idStartDate == null) ? 0 : idStartDate.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
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
		CustomerIdInfo other = (CustomerIdInfo) obj;
		if (countryIssued == null) {
			if (other.countryIssued != null) {
				return false;
			}
		}
		else if (!countryIssued.equals(other.countryIssued)) {
			return false;
		}
		if (idNumber == null) {
			if (other.idNumber != null) {
				return false;
			}
		}
		else if (!idNumber.equals(other.idNumber)) {
			return false;
		}
		if (idStartDate == null) {
			if (other.idStartDate != null) {
				return false;
			}
		}
		else if (!idStartDate.equals(other.idStartDate)) {
			return false;
		}
		if (idType == null) {
			if (other.idType != null) {
				return false;
			}
		}
		else if (!idType.equals(other.idType)) {
			return false;
		}
		return true;
	}
}
