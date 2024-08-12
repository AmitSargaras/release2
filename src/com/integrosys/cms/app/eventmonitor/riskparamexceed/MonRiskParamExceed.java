/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/riskparamexceed/MonRiskParamExceed.java,v 1.4 2006/03/06 12:36:37 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.riskparamexceed;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

public class MonRiskParamExceed extends AbstractMonCommon {
	private IMonitorDAO[] daoArray = { new RiskParamExceedDAO() };

	public static final String EVENT_RISK_PARAM_EXCEED = "EV_RISK_PARAM_EXCEED";

	public IMonitorDAO[] getDAOArray() {
		return this.daoArray;
	}

	public String getEventName() {
		return EVENT_RISK_PARAM_EXCEED;
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	public IRuleParam constructRuleParam(int ruleNum) {
		OBDateRuleParam param = new OBDateRuleParam();

		param.setRuleID("R_RISK_PARAM_EXCEED");
		param.setSysDate(DateUtil.getDate());

		return param;
	}

	/**
	 * this method is to be overriden, which otherwise will be null
	 */
	public IMonRule getTriggerRule() {
		return new RiskParamExceedRule();
	}

}
