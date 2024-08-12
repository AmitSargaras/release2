package com.integrosys.cms.app.eventmonitor.documentexpiry;

import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

/**
 * Document Expiry Monitor Rule..
 * 
 * @author $Author: kchua $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/08/19 10:51:02 $ Tag: $Name: $
 */
public class DocExpiryMonRule implements IMonRule {
	public String evaluateRule(IRuleParam ruleParam, Object ob) {

		String ss = String.valueOf(((OBDateRuleParam) ruleParam).getNumOfDays());
		ss += " days"; // shouldn't be using this, but for backward compat.

		return ss;

	}
}
