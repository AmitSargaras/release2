/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/guarantornotice/GuarantorNoticeRule.java,v 1.5 2005/05/19 03:29:54 wltan Exp $
 */
package com.integrosys.cms.app.eventmonitor.guarantornotice;

import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.IRuleParam;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.5 $ Always return true
 */
public class GuarantorNoticeRule implements IMonRule, java.io.Serializable {
	public String evaluateRule(IRuleParam ruleParam, Object anObject) {
		return IMonitorConstant.EVENT_TRUE;
	}
}
