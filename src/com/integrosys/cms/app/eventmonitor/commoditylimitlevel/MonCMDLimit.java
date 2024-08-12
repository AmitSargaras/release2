/*   Copyright Integro Technologies Pte Ltd
 *   MonCMDLimit.java
 *
 */
package com.integrosys.cms.app.eventmonitor.commoditylimitlevel;

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
public class MonCMDLimit extends AbstractMonCommon {
	private IMonitorDAO[] daoArray = { new CMDLimitDAO() };

	private static final String EVEVNT_ID = "EV_COMMODITY_LIMITLEVEL";

	private static final String RULE_ID = "R_COMMODITY_LIMITLEVEL";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.cms.app.eventmonitor.AbstractMonCommon#getDAOArray()
	 */
	protected IMonitorDAO[] getDAOArray() {
		return daoArray;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.eventmonitor.AbstractMonCommon#getTriggerRule()
	 */
	protected IMonRule getTriggerRule() {
		return new CMDLimitRule();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.eventmonitor.AbstractMonCommon#constructRuleParam
	 * (int)
	 */
	public IRuleParam constructRuleParam(int ruleNum) throws EventMonitorException {
		OBRuleParam param = new OBRuleParam();
		param.setRuleNum(-1);
		param.setRuleID(RULE_ID);
		return param;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.cms.app.eventmonitor.AbstractMonCommon#getEventName()
	 */
	public String getEventName() {
		return EVEVNT_ID;
	}
}
