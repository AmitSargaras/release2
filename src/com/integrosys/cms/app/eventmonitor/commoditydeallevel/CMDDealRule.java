/*   Copyright Integro Technologies Pte Ltd
 *   CMDDealRule.java
 *
 */
package com.integrosys.cms.app.eventmonitor.commoditydeallevel;

import java.io.Serializable;

import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.IRuleParam;

/**
 * @author heju
 * @version 1.0
 * @since Jun 10, 2004
 */

public class CMDDealRule implements IMonRule, Serializable {
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
