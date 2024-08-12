/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/bflacceptanceexpiry/MonBFLAcceptanceExpiry.java,v 1.9 2003/12/24 03:27:13 btchng Exp $
 */

package com.integrosys.cms.app.eventmonitor.bflacceptanceexpiry;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

public class MonBFLAcceptanceExpiry extends AbstractMonCommon {

	private BFLAcceptanceExpiryDAO[] daoArray = { new BFLAcceptanceExpiryDAO() };

	private static final String EVENT_BFL_ACCEPTANCE = "EV_BFL_ACCEPTANCE_EXPIRY";

	public IMonitorDAO[] getDAOArray() {
		return this.daoArray;
	}

	public String getEventName() {
		return EVENT_BFL_ACCEPTANCE;
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	public IRuleParam constructRuleParam(int ruleNum) {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID("R_BFL_ACCEPTANCE_EXPIRY");
		param.setSysDate(DateUtil.getDate());
		return param;

	}

	/**
	 * this method is to be overriden, which otherwise will be null
	 */
	public IMonRule getTriggerRule() {
		return new BFLAcceptanceExpiryRule();
	}

}
