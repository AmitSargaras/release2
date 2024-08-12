/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collateralevaluationdue/MonEvaluationDue.java,v 1.8 2003/09/05 02:13:10 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.collateralevaluationdue;

import java.util.List;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.RuleParamUtil;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

public class MonEvaluationDue extends AbstractMonCommon {

	private List monitorDaoList;

	private static final String EVENT_EVALUATION_DUE = "EV_EVALUATION_DUE";

	/**
	 * @param monitorDaoList the monitorDaoList to set
	 */
	public void setMonitorDaoList(List monitorDaoList) {
		this.monitorDaoList = monitorDaoList;
	}

	/**
	 * @return the monitorDaoList
	 */
	public List getMonitorDaoList() {
		return monitorDaoList;
	}

	public IMonitorDAO[] getDAOArray() {
		return (IMonitorDAO[]) monitorDaoList.toArray(new IMonitorDAO[0]);
	}

	public String getEventName() {
		return EVENT_EVALUATION_DUE;
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	public IRuleParam constructRuleParam(int ruleNum) {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID("R_EVALUATION_DUE");
		param.setNumOfDays(RuleParamUtil.getInt(param.getRuleID(), "num_of_days"));
		param.setSysDate(DateUtil.getDate());

		return param;
	}

	/**
	 * this method is to be overriden, which otherwise will be null
	 */
	public IMonRule getTriggerRule() {
		return new EvaluationDueRule();
	}

}
