/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/insuranceexpiry/MonInsuranceExpiry.java,v 1.10 2003/09/12 06:21:35 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.insuranceexpiry;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.RuleParamUtil;

public class MonInsuranceExpiry extends AbstractMonCommon {
	private IMonitorDAO[] daoArray = { new InsuranceExpiryDAO(), new InsuranceExpiryDAO(), new InsuranceExpiryDAO() };

	private static final String EVENT_INSURANCE_EXPIRY = "EV_INSURANCE_EXPIRY";

	public IMonitorDAO[] getDAOArray() {
		return this.daoArray;
	}

	public String getEventName() {
		return EVENT_INSURANCE_EXPIRY;
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	public IRuleParam constructRuleParam(int ruleNum) {
		OBDocumentExpiryRuleParam param = new OBDocumentExpiryRuleParam();
		param.setRuleID("R_INSURANCE_EXPIRY");
		param.setNumOfDays(RuleParamUtil.getInt(param.getRuleID(), "num_of_days" + "." + ruleNum));
		param.setDocumentCode(RuleParamUtil.getString(param.getRuleID(), "document_code" + "." + ruleNum));

		param.setSysDate(DateUtil.getDate());

		return param;
	}

	/**
	 * this method is to be overriden, which otherwise will be null
	 */
	public IMonRule getTriggerRule() {
		return new InsuranceExpiryRule();
	}

}
