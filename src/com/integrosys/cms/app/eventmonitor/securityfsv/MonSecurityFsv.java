/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/securityfsv/MonSecurityFsv.java,v 1.1 2003/11/07 06:24:03 btchng Exp $
 */
package com.integrosys.cms.app.eventmonitor.securityfsv;

import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.OBRuleParam;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/11/07 06:24:03 $ Tag: $Name: $
 */
public class MonSecurityFsv extends AbstractMonCommon {

	private IMonitorDAO[] daoArray = { new SecurityFsvDAO() };

	private static final String EVENT_SECURITY_FSV = "EV_SECURITY_FSV";

	public static final String RULE_SECURITY_FSV = "R_SECURITY_FSV";

	protected IMonitorDAO[] getDAOArray() {
		return this.daoArray;
	}

	protected IMonRule getTriggerRule() {
		return new SecurityFsvRule();
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	public IRuleParam constructRuleParam(int ruleNum) throws EventMonitorException {
		OBRuleParam param = new OBRuleParam();
		param.setRuleID(RULE_SECURITY_FSV);
		return param;
	}

	public String getEventName() {
		return EVENT_SECURITY_FSV;
	}
}
