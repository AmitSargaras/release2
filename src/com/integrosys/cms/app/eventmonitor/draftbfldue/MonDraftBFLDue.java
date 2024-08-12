/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/draftbfldue/MonDraftBFLDue.java,v 1.9 2003/10/01 08:23:51 btchng Exp $
 */

package com.integrosys.cms.app.eventmonitor.draftbfldue;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

public class MonDraftBFLDue extends AbstractMonCommon {

	private IMonitorDAO[] daoArray = { new DraftBFLDueDAO() };

	private static final String EVENT_DRAFT_BFL_EXPIRY = "EV_DRAFT_BFL_EXPIRY";

	public IMonitorDAO[] getDAOArray() {
		return this.daoArray;
	}

	public String getEventName() {
		return EVENT_DRAFT_BFL_EXPIRY;
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	public IRuleParam constructRuleParam(int ruleNum) {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID("R_DRAFT_BFL_DUE");
		// param.setNumOfDays(RuleParamUtil.getInt(param.getRuleID(),
		// "num_of_days"));
		param.setSysDate(DateUtil.getDate());

		return param;
	}

	/**
	 * this method is to be overriden, which otherwise will be null
	 */
	public IMonRule getTriggerRule() {
		return new DraftBFLDueRule();
	}

}
