/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/ddnexpiry/DDNExpiryListener.java,v 1.6 2003/09/29 08:31:47 btchng Exp $
 */

package com.integrosys.cms.app.eventmonitor.ddnexpiry;

import java.util.Date;
import java.util.List;

import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.OBEventInfo;
import com.integrosys.cms.app.eventmonitor.RuleParamUtil;
import com.integrosys.cms.app.eventmonitor.common.CommonCMSEventListener;

public class DDNExpiryListener extends CommonCMSEventListener {

	/**
	 * This method is invoked by the EventManager on IEventListeners when an
	 * event is trigged.
	 * 
	 * @param eventID is the String value of the Event ID that is causing the
	 *        triggering
	 * @param params is a List of String value parameters. This parameter should
	 *        be null if no parameters are required In this listener, only one
	 *        element is expected in the List, which is of type
	 *        OBGuarantorNoticeInfo
	 * @throws EventHandlingException if an exception occurs while servicing the
	 *         event
	 */
	public void fireEvent(String eventID, List params) throws EventHandlingException {
		OBEventInfo eventInfo = (OBEventInfo) params.get(0);
		eventInfo.setSubject(PropertyManager.getValue(IMonitorConstant.SUBJECT_EVENT_KEY_PREFIX + "." + eventID,
				"SUBJECT NOT FOUND"));
		sendNotification(eventID, params, getEventRecipientGroup(eventID));
	}

	/**
	 * Computes the expiry date based on the number of days to expiry. The
	 * number of days to expiry is taken from property file. Retrieval is
	 * dependent of method of computation. The default computation is to take
	 * the current system date plus the given number of days to expiry. There
	 * will not be validation on this "number of days" value, meaning if the
	 * value is a negative number, the expiry date will be computed to be a past
	 * date relative to the current system date. Can be overridden by subclasses
	 * to provide other methods of computation. <b>Choice of number of days to
	 * expiry is grained at the rule parameter value level.</b>
	 * @param eventInfo Event info.
	 * @param ruleParam The rule parameter to this for this event. ??
	 * @return The expiry date.
	 */
	protected Date computeExpiryDate(OBEventInfo eventInfo, IRuleParam ruleParam) {
		int num = 0;

		DefaultLogger.debug(this, "rule ID = " + ruleParam.getRuleID());
		DefaultLogger.debug(this, "rule num = " + ruleParam.getRuleNum());

		if (ruleParam.getRuleNum() == -1) {
			num = RuleParamUtil.getInt(ruleParam.getRuleID(), "elapsedDaysFromExpiry");
		}
		else {
			num = RuleParamUtil.getInt(ruleParam.getRuleID(), "elapsedDaysFromExpiry" + "." + ruleParam.getRuleNum());
		}

		return CommonUtil.getDueDate(eventInfo.getNotificationDate(), num, eventInfo.getOriginatingCountry());
	}
}
