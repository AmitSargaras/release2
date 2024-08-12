/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/IForexFeedEntry.java,v 1.6 2003/08/13 08:41:24 btchng Exp $
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
public interface IDimension extends java.io.Serializable {
	public String getSubLimitType();

	public void setSubLimitType(String subLimitType);

	public String getDescription();

	public void setDescription(String description);

	public String getLimitCurrency();

	public void setLimitCurrency(String limitCurrency);

	public double getLimitAmount();

	public void setLimitAmount(double limitAmount);

	public Date getLastReviewedDate();

	public void setLastReviewedDate(Date lastReviewedDate);
}
