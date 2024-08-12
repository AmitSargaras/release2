package com.integrosys.cms.app.feed.bus.gold;

import java.math.BigDecimal;
import java.util.Date;

public class OBGoldFeedEntry implements IGoldFeedEntry {

	private static final long serialVersionUID = -8272526731667440793L;

	private long goldFeedEntryID;

	private long goldFeedEntryRef;

	private long goldFeedGroupId;

	private long versionTime;

	private String goldGradeNum;

	private String unitMeasurementNum;

	private String currencyCode;

	private BigDecimal unitPrice;

	private Date lastUpdatedDate;

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @return the goldFeedEntryID
	 */
	public long getGoldFeedEntryID() {
		return goldFeedEntryID;
	}

	/**
	 * @return the goldFeedEntryRef
	 */
	public long getGoldFeedEntryRef() {
		return goldFeedEntryRef;
	}

	/**
	 * @return the goldFeedGroupId
	 */
	public long getGoldFeedGroupId() {
		return goldFeedGroupId;
	}

	/**
	 * @return the goldGradeNum
	 */
	public String getGoldGradeNum() {
		return goldGradeNum;
	}

	/**
	 * @return the lastUpdatedDate
	 */
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	/**
	 * @return the unitMeasurementNum
	 */
	public String getUnitMeasurementNum() {
		return unitMeasurementNum;
	}

	/**
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @return the versionTime
	 */
	public long getVersionTime() {
		return versionTime;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @param goldFeedEntryID the goldFeedEntryID to set
	 */
	public void setGoldFeedEntryID(long goldFeedEntryID) {
		this.goldFeedEntryID = goldFeedEntryID;
	}

	/**
	 * @param goldFeedEntryRef the goldFeedEntryRef to set
	 */
	public void setGoldFeedEntryRef(long goldFeedEntryRef) {
		this.goldFeedEntryRef = goldFeedEntryRef;
	}

	/**
	 * @param goldFeedGroupId the goldFeedGroupId to set
	 */
	public void setGoldFeedGroupId(long goldFeedGroupId) {
		this.goldFeedGroupId = goldFeedGroupId;
	}

	/**
	 * @param goldGradeNum the goldGradeNum to set
	 */
	public void setGoldGradeNum(String goldGradeNum) {
		this.goldGradeNum = goldGradeNum;
	}

	/**
	 * @param lastUpdatedDate the lastUpdatedDate to set
	 */
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	/**
	 * @param unitMeasurementNum the unitMeasurementNum to set
	 */
	public void setUnitMeasurementNum(String unitMeasurementNum) {
		this.unitMeasurementNum = unitMeasurementNum;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @param versionTime the versionTime to set
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result + ((goldGradeNum == null) ? 0 : goldGradeNum.hashCode());
		result = prime * result + ((unitMeasurementNum == null) ? 0 : unitMeasurementNum.hashCode());
		result = prime * result + ((unitPrice == null) ? 0 : unitPrice.hashCode());
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
		OBGoldFeedEntry other = (OBGoldFeedEntry) obj;
		if (currencyCode == null) {
			if (other.currencyCode != null) {
				return false;
			}
		}
		else if (!currencyCode.equals(other.currencyCode)) {
			return false;
		}
		if (goldGradeNum == null) {
			if (other.goldGradeNum != null) {
				return false;
			}
		}
		else if (!goldGradeNum.equals(other.goldGradeNum)) {
			return false;
		}
		if (unitMeasurementNum == null) {
			if (other.unitMeasurementNum != null) {
				return false;
			}
		}
		else if (!unitMeasurementNum.equals(other.unitMeasurementNum)) {
			return false;
		}
		if (unitPrice == null) {
			if (other.unitPrice != null) {
				return false;
			}
		}
		else if (!unitPrice.equals(other.unitPrice)) {
			return false;
		}
		return true;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("OBGoldFeedEntry [");
		buf.append("goldGradeNum=");
		buf.append(goldGradeNum);
		buf.append(", unitMeasurementNum=");
		buf.append(unitMeasurementNum);
		buf.append(", currencyCode=");
		buf.append(currencyCode);
		buf.append(", unitPrice=");
		buf.append(unitPrice);
		buf.append(", lastUpdatedDate=");
		buf.append(lastUpdatedDate);
		buf.append("]");
		return buf.toString();
	}
}
