/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/IHedgePriceExtension.java,v 1.3 2004/06/18 02:31:55 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import java.io.Serializable;
import java.util.Date;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/18 02:31:55 $ Tag: $Name: $
 */
public interface IHedgePriceExtension extends Serializable {
	/**
	 * Gets the extension ID.
	 * 
	 * @return long - ID of this extension
	 */
	public long getExtensionID();

	/**
	 * Sets the extension ID.
	 * 
	 * @param extID - long repersenting the extension ID
	 */
	public void setExtensionID(long extID);

	/**
	 * Gets the start date of this extension.
	 * 
	 * @return Date - extension period start date
	 */
	public Date getStartDate();

	/**
	 * Sets the start date of this extension.
	 * 
	 * @param startDate - Date on which the extension period starts
	 */
	public void setStartDate(Date startDate);

	/**
	 * Gets the end date of this extension.
	 * 
	 * @return Date - extension period end date
	 */
	public Date getEndDate();

	/**
	 * Sets the end date of this extension.
	 * 
	 * @param endDate - Date on which the extension period ends
	 */
	public void setEndDate(Date endDate);

	/**
	 * Gets the period unit used to specify the end date for this extension.
	 * 
	 * @return PeriodUnit
	 */
	public PeriodUnit getPeriodUnit();

	/**
	 * Sets the period unit used to specify the end date for this extension.
	 * 
	 * @param unit - PeriodUnit
	 */
	public void setPeriodUnit(PeriodUnit unit);

	/**
	 * Gets the count that indicates the number of period unit used to specify
	 * the end date for this extension.
	 * 
	 * @return long
	 */
	public long getPeriodUnitCount();

	/**
	 * Sets the count that indicates the number of period unit used to specify
	 * the end date for this extension.
	 * 
	 * @param count - long
	 */
	public void setPeriodUnitCount(long count);

	/**
	 * Gets the status of this extension period.
	 * 
	 * @return String representing status of the extension period
	 */
	public String getStatus();

	/**
	 * Sets the status of this extension period.
	 * 
	 * @param status - String representing the status
	 */
	public void setStatus(String status);

	/**
	 * Gets the common reference ID. This ID will be used to identify which
	 * staging record corresponds to which actual records.
	 * 
	 * @return long representing the common reference ID
	 */
	public long getCommonReferenceID();

	/**
	 * Sets the common reference ID. This ID will be used to identify which
	 * staging record correspods to which actual records.
	 * 
	 * @param commonRefID
	 */
	public void setCommonReferenceID(long commonRefID);
}
