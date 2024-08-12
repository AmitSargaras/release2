/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/waiverdeferral/DummyMonWaiverDeferalRejection.java,v 1.2 2006/03/06 12:39:45 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.waiverdeferral;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

/**
 * Dummy Monitor never called by batch work. Instead it provides a unification
 * EVENT/RULE interface as other normal monitors.
 * @author $Author: hshii $
 * @version $Revision: 1.2 $
 * @since $Date: 2006/03/06 12:39:45 $ Tag: $Name: $
 */
public class DummyMonWaiverDeferalRejection extends AbstractMonCommon {
	public static final String EVEVNT_ID = "EV_WAIVER_DEFERRAL_REJECTION";

	private static final String RULE_ID = "R_WAIVER_DEFERRAL_REJECTION";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.cms.app.eventmonitor.AbstractMonCommon#getDAOArray()
	 */
	protected IMonitorDAO[] getDAOArray() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.eventmonitor.AbstractMonCommon#getTriggerRule()
	 */
	protected IMonRule getTriggerRule() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.eventmonitor.AbstractMonCommon#constructRuleParam
	 * (int)
	 */
	public IRuleParam constructRuleParam(int ruleNum) throws EventMonitorException {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID(RULE_ID);
		param.setSysDate(DateUtil.getDate());
		param.setRuleNum(-1);

		return param;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.cms.app.eventmonitor.AbstractMonCommon#getEventName()
	 */
	public String getEventName() {
		return EVEVNT_ID;
	}
}
