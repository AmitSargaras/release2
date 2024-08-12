/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collateralevaluationdue/MonEvaluationDue.java,v 1.8 2003/09/05 02:13:10 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.annualreview;

import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.OBRuleParam;

public class MonAnnualReview extends AbstractMonCommon {
	private IMonitorDAO[] daoArray = { new AnnualReviewDAO() };

	private static final String EV_ANNUAL_REVIEW = "EV_ANNUAL_REVIEW";

	public IMonitorDAO[] getDAOArray() {
		return this.daoArray;
	}

	public String getEventName() {
		return EV_ANNUAL_REVIEW;
	}

	public IRuleParam constructRuleParam(int ruleNum) {
		OBRuleParam param = new OBRuleParam();
		param.setRuleID("R_ANNUAL_REVIEW");
		param.setRuleNum(-1);
		return param;
	}

	public IMonRule getTriggerRule() {
		return new AnnualReviewRule();
	}
}
