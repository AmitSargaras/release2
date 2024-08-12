/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/ddnexpired/DDNExpiredRule.java,v 1.3 2003/08/18 06:27:32 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.ddnexpired;

import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.IRuleParam;

/**
 * @author $Author: phtan $
 * @version $Revision: 1.3 $ Always return true
 */
public class DDNExpiredRule implements IMonRule {
	public String evaluateRule(IRuleParam ruleParam, Object anObject) {
		return IMonitorConstant.EVENT_TRUE;
	}
}
