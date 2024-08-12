/*   Copyright Integro Technologies Pte Ltd
 *   MonitorCMDDeal.java
 *
 */
package com.integrosys.cms.app.eventmonitor.commoditydeallevel;

import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.OBRuleParam;

/**
 * @author heju
 * @version 1.0
 * @since Jun 10, 2004
 */

public class MonCMDDeal extends AbstractMonCommon {
	private IMonitorDAO[] daoArray = { new CMDDealDAO() };

	private static final String EVEVNT_ID = "EV_COMMODITY_DEALLEVEL";

	private static final String RULE_ID = "R_COMMODITY_DEALLEVEL";

	protected IMonitorDAO[] getDAOArray() {
		return this.daoArray;
	}

	protected IMonRule getTriggerRule() {
		return new CMDDealRule();
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	public IRuleParam constructRuleParam(int ruleNum) throws EventMonitorException {
		OBRuleParam param = new OBRuleParam();
		param.setRuleNum(-1);
		param.setRuleID(RULE_ID);
		return param;
	}

	public String getEventName() {
		return EVEVNT_ID;
	}
}
