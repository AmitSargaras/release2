/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collateralmaturity/CollateralMaturityListener.java,v 1.4 2003/09/08 02:36:57 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.collateralmaturity;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.common.CollateralUtil;
import com.integrosys.cms.app.eventmonitor.common.CommonCMSEventListener;

public class CollateralMaturityListener extends CommonCMSEventListener {

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
		OBCollateralMaturityInfo eventInfo = (OBCollateralMaturityInfo) params.get(0);
		eventInfo.setSubject(PropertyManager.getValue(IMonitorConstant.SUBJECT_EVENT_KEY_PREFIX + "." + eventID,
				"SUBJECT NOT FOUND"));

		HashMap hm = CollateralUtil.getSubtypeAndTypeName(eventInfo.getSubType()); // this
																					// is
																					// originally
																					// the
																					// subTypeID
		eventInfo.setSubType((String) hm.get(CollateralUtil.SUB_TYPE_NAME));
		eventInfo.setType((String) hm.get(CollateralUtil.TYPE_NAME));
		sendNotification(eventID, params, getEventRecipientGroup(eventID));
	}
}
