/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/bflacceptanceexpiry/BFLAcceptanceExpiryRule.java,v 1.2 2003/09/19 09:14:59 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.bflacceptanceexpiry;

import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.IRuleParam;

/**
 * @author $Author: phtan $
 * @version $Revision: 1.2 $ Always return true
 */
public class BFLAcceptanceExpiryRule implements IMonRule, java.io.Serializable {
	public String evaluateRule(IRuleParam ruleParam, Object anObject) {
		return IMonitorConstant.EVENT_TRUE;
	}
}
