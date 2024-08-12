/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/reversalcustodian/ReversalCustodianListener.java,v 1.6 2006/03/06 12:35:00 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.reversalcustodian;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.common.CommonCMSEventListener;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.6 $
 * @since $Date: 2006/03/06 12:35:00 $ Tag: $Name: $
 */
public class ReversalCustodianListener extends CommonCMSEventListener {

	public static final String EVENT_ID = "EV_REVERSAL_CUSTODIAN";

	public static final String RULE_ID = "R_REVERSAL_CUSTODIAN";

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
		List reversedItemsList = (List) params.get(0);
		String reversalRemarks = (String) params.get(1);
		DefaultLogger.debug(this, "the reversedItemsList size is " + reversedItemsList.size());
		try {
			for (int i = 0; i < reversedItemsList.size(); i++) {
				OBReversalCustodian cust = (OBReversalCustodian) reversedItemsList.get(i);
				cust.setReversalRemarks(reversalRemarks);
				cust.setDetails(EVENT_ID + " , " + cust.getDocNo());
				ArrayList list = new ArrayList();
				list.add(cust);
				list.add("");
				list.add(constructRuleParam());

				DefaultLogger.debug(this, "start Sending Notification -----------------");
//				sendNotification(EVENT_ID, list, getEventRecipientGroup(eventID));
				DefaultLogger.debug(this, "finish Sending Notification ----------------");
			}
		}
		catch (Exception e) {
			throw new EventHandlingException(e);
		}
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	public IRuleParam constructRuleParam() {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID(RULE_ID);
		param.setSysDate(DateUtil.getDate());
		param.setRuleNum(-1);
		return param;
	}
}