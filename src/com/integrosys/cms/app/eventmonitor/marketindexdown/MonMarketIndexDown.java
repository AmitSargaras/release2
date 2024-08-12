/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/marketindexdown/MonMarketIndexDown.java,v 1.2 2003/09/16 11:24:55 btchng Exp $
 */

package com.integrosys.cms.app.eventmonitor.marketindexdown;

import java.util.List;

import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.RuleParamUtil;
import com.integrosys.cms.app.eventmonitor.marketindex.OBPercentRuleParam;

public class MonMarketIndexDown extends AbstractMonCommon {

	private List monitorDaoList;

	private static final String EVENT_MARKET_INDEX_DOWN = "EV_MARKET_INDEX_DOWN";

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
		return EVENT_MARKET_INDEX_DOWN;
	}

	public IRuleParam constructRuleParam(int ruleNum) {
		OBPercentRuleParam param = new OBPercentRuleParam();
		param.setRuleID("R_MARKET_INDEX_DOWN");
		param.setPercent(RuleParamUtil.getDouble(param.getRuleID(), "percent"));

		return param;
	}

	public IMonRule getTriggerRule() {
		return new MarketIndexDownRule();
	}

}
