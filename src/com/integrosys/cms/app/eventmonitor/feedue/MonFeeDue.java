/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collateralevaluationdue/MonEvaluationDue.java,v 1.8 2003/09/05 02:13:10 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.feedue;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.RuleParamUtil;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

public class MonFeeDue extends AbstractMonCommon {
	private IMonitorDAO[] daoArray = { new FeeDueDAO() };

	private static final String EV_FEE_DUE = "EV_FEE_DUE";

	public IMonitorDAO[] getDAOArray() {
		return this.daoArray;
	}

	public String getEventName() {
		return EV_FEE_DUE;
	}

	public IRuleParam constructRuleParam(int ruleNum) {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID("R_FEE_DUE");
		param.setRuleNum(-1);
		param.setNumOfDays(RuleParamUtil.getInt(param.getRuleID(), "num_of_days"));
		param.setSysDate(DateUtil.getDate());
		return param;
	}

	public IMonRule getTriggerRule() {
		return new FeeDueRule();
	}
}
