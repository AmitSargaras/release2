/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/fulldocreview/MonFullDocReview.java,v 1.4 2003/09/05 02:13:10 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.fulldocreview;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

/**
 * Every 2 years from date of Guarantee. (Biennial reminder required). Trigger
 * condition: If (System Date - Date of Guarantee) = 2 years Ends when :If the
 * Trigger Conditions are no longer true. Notification send to: CO Other
 * remarks: Applies to Guarantees only.
 */
public class MonFullDocReview extends AbstractMonCommon {

	protected FullDocReviewDAO[] daoArray = { new FullDocReviewDAO() };

	private static final String EVENT_FULL_DOC_REVIEW = "EV_FULL_DOC_REVIEW";

	public IMonitorDAO[] getDAOArray() {
		DefaultLogger.debug(this, "get DAOArrary() called and length is " + this.daoArray.length);
		return daoArray;
	}

	public String getEventName() {
		return EVENT_FULL_DOC_REVIEW;
	}

	public MonFullDocReview() {
		super();
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	public IRuleParam constructRuleParam(int ruleNum) {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID("R_FULL_DOC_REVIEW");
		// param.setNumOfDays(RuleParamUtil.getInt(param.getRuleID(),
		// "num_of_days");

		param.setSysDate(DateUtil.getDate());

		return param;
	}

	/**
	 * this method is to be overriden, which otherwise will be null
	 */
	public IMonRule getTriggerRule() {
		return new FullDocReviewRule();
	}

}
