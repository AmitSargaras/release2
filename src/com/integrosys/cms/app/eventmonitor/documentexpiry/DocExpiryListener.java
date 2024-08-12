package com.integrosys.cms.app.eventmonitor.documentexpiry;

import java.util.List;

import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.event.IEventListener;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.common.CommonCMSEventListener;
import com.integrosys.cms.app.eventmonitor.common.OBDocumentExpiryInfo;

/**
 * Listener for doc expiry
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/03/06 12:31:47 $ Tag: $Name: $
 */
public class DocExpiryListener extends CommonCMSEventListener implements IEventListener {

	public DocExpiryListener() {
		super();
	}

	/**
	 * This method is invoked by the EventManager on IEventListeners when an
	 * event is trigged.
	 * 
	 * @param eventID is the String value of the Event ID that is causing the
	 *        triggering
	 * @param params is a List of String value parameters. This parameter should
	 *        be null if no parameters are required
	 * @throws EventHandlingException if an exception occurs while servicing the
	 *         event
	 */
	public void fireEvent(String eventID, List params) throws EventHandlingException {
		OBDocumentExpiryInfo info = (OBDocumentExpiryInfo) params.get(0);

		info.setSubject(PropertyManager.getValue(IMonitorConstant.SUBJECT_EVENT_KEY_PREFIX + "." + eventID,
				"SUBJECT NOT FOUND"));

		info.setDetails(eventID + " , " + String.valueOf(info.getCheckListItemRef()));

		String code = (String) params.get(1);
		info.setExpiryCode(code);
		if (code.equals("30 days")) {
			sendNotification(eventID, params, getEventRecipientGroup(eventID));
			// info.setExpiryCode(code);
		}
		else if (code.equals("14 days")) {
			sendNotification(eventID, params, getEventRecipientGroup(eventID));
			// todo : set message expiry date differently
		}
		else if (code.equals("1 days")) {
			// DefaultLogger.info(this, "to be implemented");
			sendNotification(eventID, params, getEventRecipientGroup(eventID));
		}
		else if (code.equals("0 days")) {
			updateStatus(info);
		}
		else {
			DefaultLogger.debug(this, "arbitrary code returned");
			sendNotification(eventID, params, getEventRecipientGroup(eventID));
			DefaultLogger.warn(this, "Un-recognized error code returned in event monitoring");
		}
	}

	private void updateStatus(OBDocumentExpiryInfo info) throws EventHandlingException {
		ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		try {
			proxy.expireCheckListItem(info.getCheckListItemID(), info.getCheckListItemRef());
		}
		catch (CheckListException e) {
			DefaultLogger.error(this, "update status error", e);
			throw new EventHandlingException("Unable to update document status to expired");
		}
	}

}
