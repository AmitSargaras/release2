/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/coverageexcess/MonCoverageExcess.java,v 1.1 2003/09/14 09:19:57 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.coverageexcess;

import java.util.List;

import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.OBRuleParam;

public class MonCoverageExcess extends AbstractMonCommon {
	List monitorDaoList;

	private static final String EVENT_COVERAGE_EXCESS = "EV_COVERAGE_EXCESS";

	public static final String RULE_COVERAGE_EXCESS = "R_COVERAGE_EXCESS";

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
		return EVENT_COVERAGE_EXCESS;
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	public IRuleParam constructRuleParam(int ruleNum) {
		OBRuleParam param = new OBRuleParam();
		param.setRuleID("R_COVERAGE_EXCESS");
		return param;
	}

	/**
	 * this method is to be overriden, which otherwise will be null
	 */
	public IMonRule getTriggerRule() {
		return new CoverageRule();
	}

}
