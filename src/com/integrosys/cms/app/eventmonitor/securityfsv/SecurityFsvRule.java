/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/securityfsv/SecurityFsvRule.java,v 1.1 2003/11/07 06:24:03 btchng Exp $
 */
package com.integrosys.cms.app.eventmonitor.securityfsv;

import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.IRuleParam;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/11/07 06:24:03 $ Tag: $Name: $
 */
public class SecurityFsvRule implements IMonRule, java.io.Serializable {

	public String evaluateRule(IRuleParam ruleParam, Object anObject) {
		return IMonitorConstant.EVENT_TRUE;
	}
}
