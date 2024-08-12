/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/waiverdeferral/WaiverDeferralListener.java,v 1.6 2005/11/16 10:04:07 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.waiverdeferral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.common.CollateralUtil;
import com.integrosys.cms.app.eventmonitor.common.CommonCMSEventListener;
import com.integrosys.cms.app.transaction.CMSTransactionExtDAO;
import com.integrosys.cms.app.transaction.OBCMSTrxRouteInfo;
import com.integrosys.component.notification.bus.OBNotificationRecipient;

public class WaiverDeferralListener extends CommonCMSEventListener {

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
		OBWaiverDeferralInfo eventInfo = (OBWaiverDeferralInfo) params.get(0);

		if (eventInfo == null) {
			throw new EventHandlingException(eventID + " : CheckList waiver/deferral event info is null.");
		}

		eventInfo.setSubject(PropertyManager.getValue(IMonitorConstant.SUBJECT_EVENT_KEY_PREFIX + "." + eventID,
				"SUBJECT NOT FOUND"));

		if ((eventInfo.getSecuritySubTypeID() != null) && (eventInfo.getSecuritySubTypeID().length() > 0)) {
			HashMap hm = CollateralUtil.getSubtypeAndTypeName(eventInfo.getSecuritySubTypeID());
			eventInfo.setSecuritySubType((String) hm.get(CollateralUtil.SUB_TYPE_NAME));
			eventInfo.setSecurityType((String) hm.get(CollateralUtil.TYPE_NAME));
		}

		sendNotification(eventID, params, getEventRecipients(eventInfo));
	}

	/**
	 * Get list of users who has handled this particular transaction.
	 * 
	 * @param eventInfo
	 * @return
	 * @throws EventHandlingException
	 */
	public ArrayList getEventRecipients(OBWaiverDeferralInfo eventInfo) throws EventHandlingException {
		try {
			String transactionID = eventInfo.getTransactionID();
			DefaultLogger.debug(this, "getEventRecipients for trxID - " + transactionID);
			CMSTransactionExtDAO extDAO = new CMSTransactionExtDAO();
			Collection col = extDAO.getTransactionRouteInfos(transactionID);
			ArrayList receipients = new ArrayList();
			Iterator itr = col.iterator();
			while (itr.hasNext()) {
				OBCMSTrxRouteInfo rinfo = (OBCMSTrxRouteInfo) itr.next();

				// filter out the final trx user ie. the user who
				// approved/rejected the multi-lvl approval
				long userID = Long.parseLong(rinfo.getUserID());
				if (eventInfo.getTrxUserID() == userID) {
					continue;
				}
				OBNotificationRecipient r = new OBNotificationRecipient();

				if (userID != ICMSConstant.LONG_INVALID_VALUE) {
					r.setUserID(new Long(userID));
				}

				r.setTeamID(Long.valueOf(rinfo.getTeamID()));
				DefaultLogger.debug(this, "receipient - " + rinfo.getUserID() + "/" + rinfo.getTeamID());
				receipients.add(r);
			}
			return receipients;
		}
		catch (com.integrosys.base.businfra.search.SearchDAOException se) {
			throw new EventHandlingException(se);
		}
	}
}
