/* Copyright Integro Technologies Pte Ltd
 * com.integrosys.cms.app.eventmonitor.dealapproval.DummyMonDealApproval.java Created on Jun 23, 2004 10:43:28 AM
 *
 */

package com.integrosys.cms.app.eventmonitor.dealapproval;

import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.OBRuleParam;

//import com.integrosys.base.techinfra.util.DateUtil;
/**
 * Dummy Monitor never called by batch work. Instead it provides a unification
 * EVENT/RULE interface as other normal monitors.
 * @since Jun 23, 2004
 * @author heju
 * @version 1.0.0
 */
public class DummyMonDealApproval extends AbstractMonCommon {
	private static final String EVEVNT_ID = "EV_DEAL_APPROVAL";

	private static final String RULE_ID = "R_DEAL_APPROVAL";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.cms.app.eventmonitor.AbstractMonCommon#getDAOArray()
	 */
	protected IMonitorDAO[] getDAOArray() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.eventmonitor.AbstractMonCommon#getTriggerRule()
	 */
	protected IMonRule getTriggerRule() {
		return null;
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
		param.setRuleID(RULE_ID);
		// param.setSysDate(DateUtil.getDate());
		param.setRuleNum(-1);

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
