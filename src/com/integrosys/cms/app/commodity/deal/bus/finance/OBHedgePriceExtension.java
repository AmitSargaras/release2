/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/OBHedgePriceExtension.java,v 1.5 2004/07/15 16:14:42 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: lyng $
 * @version $Revision: 1.5 $
 * @since $Date: 2004/07/15 16:14:42 $ Tag: $Name: $
 */
public class OBHedgePriceExtension implements IHedgePriceExtension {

	private long extensionID;

	private Date startDate;

	private Date endDate;

	private PeriodUnit periodUnit;

	private long periodUnitCount;

	private String status = ICMSConstant.STATE_ACTIVE;

	private long commonRefID = ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Gets the extension ID.
	 * 
	 * @return long - ID of this extension
	 */
	public long getExtensionID() {
		return extensionID;
	}

	/**
	 * Sets the extension ID.
	 * 
	 * @param extID - long repersenting the extension ID.
	 */
	public void setExtensionID(long extID) {
		this.extensionID = extID;
	}

	/**
	 * Gets the start date of this extension.
	 * 
	 * @return Date - extension period start date.
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date of this extension.
	 * 
	 * @param startDate - Date on which the extension period starts.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the end date of this extension.
	 * 
	 * @return Date - extension period end date.
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the end date of this extension.
	 * 
	 * @param endDate - Date on which the extension period ends.
	 */
	public void setEndDate(Date endDate) {
		// TODO : consider removing endDate,
		// bcos the enddate shd b derived based on the start date and
		// the period unit and count.
		this.endDate = endDate;
	}

	/**
	 * Gets the period unit used to specify the end date for this extension.
	 * 
	 * @return PeriodUnit
	 */
	public PeriodUnit getPeriodUnit() {
		return periodUnit;
	}

	/**
	 * Sets the period unit used to specify the end date for this extension.
	 * 
	 * @param unit - PeriodUnit
	 */
	public void setPeriodUnit(PeriodUnit unit) {
		this.periodUnit = unit;
	}

	/**
	 * Gets the count that indicates the number of period unit used to specify
	 * the end date for this extension.
	 * 
	 * @return long
	 */
	public long getPeriodUnitCount() {
		return periodUnitCount;
	}

	/**
	 * Sets the count that indicates the number of period unit used to specify
	 * the end date for this extension.
	 * 
	 * @param count - long
	 */
	public void setPeriodUnitCount(long count) {
		this.periodUnitCount = count;
	}

	/**
	 * Gets the status of this extension period.
	 * 
	 * @return String - status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status of this extension period.
	 * 
	 * @param status - String representing the status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the common reference ID. This ID will be used to identify which
	 * staging record corresponds to which actual records.
	 * 
	 * @return long representing the common reference ID
	 */
	public long getCommonReferenceID() {
		return commonRefID;
	}

	/**
	 * Sets the common reference ID. This ID will be used to identify which
	 * staging record correspods to which actual records.
	 * 
	 * @param commonRefID
	 */
	public void setCommonReferenceID(long commonRefID) {
		this.commonRefID = commonRefID;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OBHedgePriceExtension)) {
			return false;
		}

		final OBHedgePriceExtension obHedgePriceExtension = (OBHedgePriceExtension) o;

		if (commonRefID != obHedgePriceExtension.commonRefID) {
			return false;
		}
		if (extensionID != obHedgePriceExtension.extensionID) {
			return false;
		}
		if (endDate != null ? !endDate.equals(obHedgePriceExtension.endDate) : obHedgePriceExtension.endDate != null) {
			return false;
		}
		if (startDate != null ? !startDate.equals(obHedgePriceExtension.startDate)
				: obHedgePriceExtension.startDate != null) {
			return false;
		}
		if (status != null ? !status.equals(obHedgePriceExtension.status) : obHedgePriceExtension.status != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result;
		result = (int) (extensionID ^ (extensionID >>> 32));
		result = 29 * result + (startDate != null ? startDate.hashCode() : 0);
		result = 29 * result + (endDate != null ? endDate.hashCode() : 0);
		result = 29 * result + (status != null ? status.hashCode() : 0);
		result = 29 * result + (int) (commonRefID ^ (commonRefID >>> 32));
		return result;
	}
}
