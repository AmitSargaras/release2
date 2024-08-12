/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collateralevaluationdue/EvaluationDueRule.java,v 1.4 2003/09/19 09:14:59 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.annualreview;

import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.IRuleParam;

/**
 * @author $Author: phtan $
 * @version $Revision: 1.4 $ Always return true
 */
public class AnnualReviewRule implements IMonRule, java.io.Serializable {
	public String evaluateRule(IRuleParam ruleParam, Object anObject) {
		return IMonitorConstant.EVENT_TRUE;
	}
}
