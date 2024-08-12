/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collateralevaluationdue/EvaluationDueListener.java,v 1.4 2003/09/08 02:36:57 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.feedue;

import java.util.List;

import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.cms.app.eventmonitor.common.CommonCMSEventListener;

public class FeeDueListener extends CommonCMSEventListener {
	public void fireEvent(String eventID, List params) throws EventHandlingException {
		// OBEvaluationDueInfo eventInfo = (OBEvaluationDueInfo) params.get(0);
		// eventInfo.setSubject(PropertyManager.getValue(
		// IMonitorConstant.SUBJECT_EVENT_KEY_PREFIX + "." + eventID,
		// "SUBJECT NOT FOUND"));
		//
		// HashMap hm = CollateralUtil.getSubtypeAndTypeName(eventInfo
		// .getSubType()); // this is originally the subTypeID
		// eventInfo.setSubType((String) hm.get(CollateralUtil.SUB_TYPE_NAME));
		// eventInfo.setType((String) hm.get(CollateralUtil.TYPE_NAME));
		sendNotification(eventID, params, getEventRecipientGroup(eventID));
	}
}
