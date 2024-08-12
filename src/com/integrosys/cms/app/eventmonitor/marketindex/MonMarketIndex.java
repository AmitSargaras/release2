/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/marketindex/MonMarketIndex.java,v 1.8 2006/03/06 12:33:25 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.marketindex;

import java.util.List;

import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.RuleParamUtil;

public class MonMarketIndex extends AbstractMonCommon {

	private List monitorDaoList;

	public static final String EVENT_MARKET_INDEX = "EV_MARKET_INDEX";

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
		return EVENT_MARKET_INDEX;
	}

	public IRuleParam constructRuleParam(int ruleNum) {
		OBPercentRuleParam param = new OBPercentRuleParam();
		param.setRuleID("R_MARKET_INDEX");
		param.setPercent(RuleParamUtil.getDouble(param.getRuleID(), "percent"));
		return param;
	}

	public IMonRule getTriggerRule() {
		return new MarketIndexRule();
	}

}
