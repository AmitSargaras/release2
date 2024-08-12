/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/dealapproval/DealApprovalListener.java,v 1.6 2006/03/06 12:30:19 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.dealapproval;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.common.CommonCMSEventListener;
import com.integrosys.cms.app.transaction.CMSTransactionExtDAO;
import com.integrosys.cms.app.transaction.OBCMSTrxRouteInfo;
import com.integrosys.component.notification.bus.OBNotificationRecipient;

public class DealApprovalListener extends CommonCMSEventListener {

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
		OBDealApproval eventInfo = (OBDealApproval) params.get(0);

		if (eventInfo == null) {
			throw new EventHandlingException(eventID + " : Deal approval event info is null.");
		}

		eventInfo.setSubject(PropertyManager.getValue(IMonitorConstant.SUBJECT_EVENT_KEY_PREFIX + "." + eventID,
				"SUBJECT NOT FOUND"));

		eventInfo.setDetails(eventID + ", " + ((OBDealApproval) eventInfo).getLeID() + " , "
				+ ((OBDealApproval) eventInfo).getDealNo());
		// sendNotification(eventID, params, getEventRecipientGroup(eventID));
		sendNotification(eventID, params, getEventRecipients(eventInfo));
	}

	/**
	 * Get list of users who has handled this particular transaction.
	 * 
	 * @param eventInfo
	 * @return
	 * @throws EventHandlingException
	 */
	private ArrayList getEventRecipients(OBDealApproval eventInfo) throws EventHandlingException {
		try {

			String transactionID = eventInfo.getTransactionID();
			DefaultLogger.debug(this, "getEventRecipients for trxID - " + transactionID);
			CMSTransactionExtDAO extDAO = new CMSTransactionExtDAO();
			Collection col = extDAO.getTransactionRouteInfos(transactionID);
			ArrayList receipients = new ArrayList();
			Iterator i = col.iterator();
			while (i.hasNext()) {

				OBCMSTrxRouteInfo info = (OBCMSTrxRouteInfo) i.next();

				// filter out the final trx user ie. the user who
				// approved/rejected the multi-lvl approval
				long userID = Long.parseLong(info.getUserID());
				if (eventInfo.getTrxUserID() == userID) {
					continue;
				}

				OBNotificationRecipient receipient = new OBNotificationRecipient();

				if (userID != ICMSConstant.LONG_INVALID_VALUE) {
					receipient.setUserID(new Long(userID));
				}

				receipient.setTeamID(Long.valueOf(info.getTeamID()));
				DefaultLogger.debug(this, "receipient - " + info.getUserID() + "/" + info.getTeamID());
				receipients.add(receipient);
			}
			return receipients;
		}
		catch (com.integrosys.base.businfra.search.SearchDAOException se) {
			throw new EventHandlingException(se);
		}
	}
}
