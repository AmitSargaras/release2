/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/ddnexpiry/MonDDNExpiry.java,v 1.8 2003/09/05 02:13:10 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.ddnexpiry;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.RuleParamUtil;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

public class MonDDNExpiry extends AbstractMonCommon {
	private IMonitorDAO[] daoArray = { new DDNExpiryDAO(), new DDNExpiryDAO() }; // use
																					// same
																					// DAO
																					// but
																					// different
																					// params

	private static final String EVENT_DDN_EXPIRY = "EV_DDN_EXPIRY";

	public IMonitorDAO[] getDAOArray() {
		return this.daoArray;
	}

	public String getEventName() {
		return EVENT_DDN_EXPIRY;
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	public IRuleParam constructRuleParam(int ruleNum) {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID("R_DDN_EXPIRY");
		param.setNumOfDays(RuleParamUtil.getInt(param.getRuleID(), "num_of_days" + "." + ruleNum));
		param.setSysDate(DateUtil.getDate());

		return param;
	}

	/**
	 * this method is to be overriden, which otherwise will be null
	 */
	public IMonRule getTriggerRule() {
		return new DDNExpiryRule();
	}

}
