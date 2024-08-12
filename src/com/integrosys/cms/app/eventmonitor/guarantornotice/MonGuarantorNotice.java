/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/guarantornotice/MonGuarantorNotice.java,v 1.8 2003/09/02 02:00:11 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.guarantornotice;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.RuleParamUtil;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

/**
 * Every 2 years from date of Guarantee. (Biennial reminder required). Trigger
 * condition: If (System Date - Date of Guarantee) = 2 years Ends when :If the
 * Trigger Conditions are no longer true. Notification send to: CO Other
 * remarks: Applies to Guarantees only.
 */
public class MonGuarantorNotice extends AbstractMonCommon {

	private GuarantorNoticeDAO[] daoArray = { new GuarantorNoticeDAO() };

	private static final String EVENT_GUARANTOR_NOTICE = "EV_GUARANTOR_NOTICE";

	public IMonitorDAO[] getDAOArray() {
		DefaultLogger.debug(this, "get DAOArrary() called and length is " + this.daoArray.length);
		return this.daoArray;
	}

	public String getEventName() {
		return EVENT_GUARANTOR_NOTICE;
	}

	public MonGuarantorNotice() {
		super();
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	public IRuleParam constructRuleParam(int ruleNum) {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID("R_GUARANTOR_NOTICE");
		param.setNumOfDays(RuleParamUtil.getInt(param.getRuleID(), "num_of_days"));

		param.setSysDate(DateUtil.getDate());

		return param;
	}

	/**
	 * this method is to be overriden, which otherwise will be null
	 */
	public IMonRule getTriggerRule() {
		return new GuarantorNoticeRule();
	}

}
