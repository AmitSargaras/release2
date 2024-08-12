/*   Copyright Integro Technologies Pte Ltd
 *   CMDLimitRule.java created on  2:59:47 PM Jun 10, 2004
 *
 */
package com.integrosys.cms.app.eventmonitor.commoditylimitlevel;

import java.io.Serializable;

import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.IRuleParam;

/**
 * @author heju
 * @version 1.0
 * @since Jun 10, 2004
 */
public class CMDLimitRule implements IMonRule, Serializable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.eventmonitor.IMonRule#evaluateRule(com.integrosys
	 * .cms.app.eventmonitor.IRuleParam, java.lang.Object)
	 */
	public String evaluateRule(IRuleParam ruleParam, Object anObject) {
		return IMonitorConstant.EVENT_TRUE;
	}
}
