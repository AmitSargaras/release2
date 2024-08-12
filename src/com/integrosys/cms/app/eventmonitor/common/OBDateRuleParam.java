/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/common/OBDateRuleParam.java,v 1.5 2003/09/29 03:44:36 btchng Exp $
 */

package com.integrosys.cms.app.eventmonitor.common;

import java.util.Date;

import com.integrosys.cms.app.eventmonitor.OBRuleParam;

/**
 * Provides the frequently used parameters used in MonitorDAO and MonitorRule
 */
public class OBDateRuleParam extends OBRuleParam {
	private int _numOfDays;

	private Date sysDate;

	public void setNumOfDays(int nDay) {
		this._numOfDays = nDay;
	}

	public int getNumOfDays() {
		return this._numOfDays;
	}

	public void setSysDate(Date aDate) {
		this.sysDate = aDate;
	}

	public Date getSysDate() {
		return this.sysDate;
	}
}
