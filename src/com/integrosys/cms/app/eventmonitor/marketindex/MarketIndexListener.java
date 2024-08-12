/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/marketindex/MarketIndexListener.java,v 1.6 2006/03/06 12:33:25 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.marketindex;

import java.util.List;

import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.OBEventInfo;
import com.integrosys.cms.app.eventmonitor.common.CommonCMSEventListener;

/**
 * Listener for Guarantor Notice Send notification to CO
 */
public class MarketIndexListener extends CommonCMSEventListener {

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
		eventInfo.setDetails(eventID + " , " + ((OBMarketIndexInfo) eventInfo).getStockExchange());

		sendNotification(eventID, params, getEventRecipientGroup(eventID));

	}
}
