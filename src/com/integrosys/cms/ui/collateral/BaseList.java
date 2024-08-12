/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/BaseList.java,v 1.0 2004/06/04 05:19:56 hltan Exp $
 */
package com.integrosys.cms.ui.collateral;

import java.util.Date;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:19:56 $ Tag: $Name: $
 */
public class BaseList {

	private Date lastDate = null;

	private static long CACHE_TIMING;

	public BaseList() {

		if (lastDate == null) {
			setLastDate(new Date());
		}

		String cachetiming = PropertyManager.getValue("integrosys.commoncode.cachetiming");
		try {
			CACHE_TIMING = Integer.parseInt(cachetiming);
		}
		catch (Exception e) {
			CACHE_TIMING = 300000; // 5 minutes
			DefaultLogger.error(this, "integrosys.commoncode.cachetiming not set, defaulting to 5 minutes");
		}
	}

	public static boolean toRefresh(Date date_now, Date date_created) {
		if ((date_now == null) || (date_created == null)) {
			return true;
		}

		long elapsed_time = date_now.getTime() - date_created.getTime();

		if (elapsed_time > CACHE_TIMING) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setLastDate(Date date_now) {
		lastDate = date_now;
	}

	public Date getLastDate() {
		return (lastDate);
	}

}
