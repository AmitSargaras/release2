package com.integrosys.cms.app.eventmonitor.updatecustodian;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.OBEventInfo;
import com.integrosys.cms.app.eventmonitor.common.CommonCMSEventListener;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;
import com.integrosys.cms.app.notification.proxy.CMSNotificationFactory;
import com.integrosys.cms.app.notification.proxy.ICMSNotificationProxy;

public class UpdateCustodianListener extends CommonCMSEventListener {

	public static final String EVENT_ID = "EV_UPDATE_CUSTODIAN";

	public static final String RULE_ID = "R_UPDATE_CUSTODIAN";

	private static final String NOTIFICATION_TEMPLATE_KEY = "cms.eventmonitor.eventnotification.template.";

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
	public void fireEvent(String eventID, List paramList) throws EventHandlingException {
		try {
			OBUpdateCustodian cust = (OBUpdateCustodian) paramList.get(0);
			ArrayList recipientList = (ArrayList) paramList.get(1);
			ArrayList list = new ArrayList();
			list.add(cust);
			list.add("");
			list.add(constructRuleParam());
			DefaultLogger.debug(this, "start Sending Notification -----------------");
			this.sendNotification(EVENT_ID, list, recipientList);
			DefaultLogger.debug(this, "finish Sending Notification ----------------");
		}
		catch (Exception e) {
			throw new EventHandlingException(e);
		}
	}

	/**
	 * send notification
	 * @param eventID
	 * @param params
	 * @param receipientGroup
	 * @throws EventHandlingException
	 */
	protected void sendNotification(String eventID, List params, ArrayList recipientList) throws EventHandlingException {
		try {
			OBEventInfo info = (OBEventInfo) params.get(0);

			info.setSubject(PropertyManager.getValue(IMonitorConstant.SUBJECT_EVENT_KEY_PREFIX + "." + eventID));
			info.setNotificationDate(DateUtil.getDate());
			info.setEventID(eventID);
			info.setNotificationExpiryDate(computeExpiryDate(info, (IRuleParam) params.get(2)));
			info.setReceipient(PropertyManager.getValue("cms.eventmonitor.eventnotification.recipient." + eventID));
			String[] secondaryCountryList = info.getSecondaryCountryList();
			String[] countryList = null;
			if (secondaryCountryList == null) {
				countryList = new String[1];
			}
			else {
				countryList = new String[1 + secondaryCountryList.length];
				for (int i = 0; i < secondaryCountryList.length; i++) {
					// Start adding at second element.
					countryList[i + 1] = secondaryCountryList[i];
				}
			}
			countryList[0] = info.getOriginatingCountry();
			String segment = info.getSegment();
			ICMSNotificationProxy notiProxy = CMSNotificationFactory.getCMSNotificationProxy();
			String templateNameKey = NOTIFICATION_TEMPLATE_KEY + eventID;
			String pp = PropertyManager.getValue(templateNameKey);
			notiProxy.sendEventNotification(info, (ArrayList) recipientList, countryList, segment, pp);
		}
		catch (Exception e) {
			DefaultLogger.error(this, e);
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
