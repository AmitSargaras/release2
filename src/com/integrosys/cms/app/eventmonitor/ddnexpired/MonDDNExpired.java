/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/ddnexpired/MonDDNExpired.java,v 1.9 2003/12/26 08:10:49 btchng Exp $
 */

package com.integrosys.cms.app.eventmonitor.ddnexpired;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.RuleParamUtil;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;
import com.integrosys.cms.app.eventmonitor.ddnexpiry.DDNExpiryDAO;

public class MonDDNExpired extends AbstractMonCommon {
	private IMonitorDAO[] daoArray = { new DDNExpiryDAO() };

	private static final String EVENT_DDN_EXPIRED = "EV_DDN_EXPIRED";

	public IMonitorDAO[] getDAOArray() {
		return this.daoArray;
	}

	public String getEventName() {
		return EVENT_DDN_EXPIRED;
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	public IRuleParam constructRuleParam(int ruleNum) {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID("R_DDN_EXPIRED");
		param.setNumOfDays(RuleParamUtil.getInt(param.getRuleID(), "num_of_days"));
		param.setSysDate(DateUtil.getDate());

		return param;
	}

	/**
	 * this method is to be overriden, which otherwise will be null
	 */
	public IMonRule getTriggerRule() {
		return new DDNExpiredRule();
	}

}
