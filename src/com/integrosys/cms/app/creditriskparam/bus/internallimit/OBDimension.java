/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/OBForexFeedEntry.java,v 1.6 2003/08/13 08:41:24 btchng Exp $
 */
package com.integrosys.cms.app.creditriskparam.bus.internallimit;

import java.util.Date;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.6 $
 * @since $Date: 2003/08/13 08:41:24 $ Tag: $Name: $
 * 
 */
public class OBDimension implements IDimension {

	private String subLimitType;
	private String description;
	private String limitCurrency;
	private double limitAmount;
	private Date lastReviewedDate;

	public String getSubLimitType() {
		return subLimitType;
	}

	public void setSubLimitType(String subLimitType) {
		this.subLimitType = subLimitType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLimitCurrency() {
		return limitCurrency;
	}

	public void setLimitCurrency(String limitCurrency) {
		this.limitCurrency = limitCurrency;
	}

	public double getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(double limitAmount) {
		this.limitAmount = limitAmount;
	}

	public Date getLastReviewedDate() {
		return lastReviewedDate;
	}

	public void setLastReviewedDate(Date lastReviewedDate) {
		this.lastReviewedDate = lastReviewedDate;
	}
}