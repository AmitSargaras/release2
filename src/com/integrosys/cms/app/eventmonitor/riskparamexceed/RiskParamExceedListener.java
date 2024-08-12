/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/riskparamexceed/RiskParamExceedListener.java,v 1.4 2003/09/08 02:36:58 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.riskparamexceed;

import java.util.List;

import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.OBEventInfo;
import com.integrosys.cms.app.eventmonitor.common.CommonCMSEventListener;

public class RiskParamExceedListener extends CommonCMSEventListener {

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
	 * @throw EventHandlingException if an exception occurs while servicing the
	 *        event
	 */
	public void fireEvent(String eventID, List params) throws EventHandlingException {
		OBEventInfo eventInfo = (OBEventInfo) params.get(0);
		eventInfo.setSubject(PropertyManager.getValue(IMonitorConstant.SUBJECT_EVENT_KEY_PREFIX + "." + eventID,
				"SUBJECT NOT FOUND"));
		sendNotification(eventID, params, getEventRecipientGroup(eventID));
	}
}
