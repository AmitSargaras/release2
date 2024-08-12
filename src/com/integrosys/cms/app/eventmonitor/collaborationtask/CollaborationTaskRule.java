/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collaborationtask/CollaborationTaskRule.java,v 1.5 2003/09/19 09:14:59 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.collaborationtask;

import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.IRuleParam;

/**
 * @author $Author: phtan $
 * @version $Revision: 1.5 $
 * 
 *          This rule checks for emptiness of Task ID. If Task ID is null, it
 *          means that collaboration task has not been created and hence
 *          requires notification
 */
public class CollaborationTaskRule implements IMonRule, java.io.Serializable {
	public String evaluateRule(IRuleParam ruleParam, Object anObject) {
		return IMonitorConstant.EVENT_TRUE;
	}
}
