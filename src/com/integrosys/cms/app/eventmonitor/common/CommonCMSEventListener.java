/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/common/CommonCMSEventListener.java,v 1.11 2005/12/19 07:53:57 whuang Exp $
 */

package com.integrosys.cms.app.eventmonitor.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.eventmonitor.AbstractCommonListener;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.OBEventInfo;
import com.integrosys.cms.app.eventmonitor.RuleParamUtil;
import com.integrosys.cms.app.eventmonitor.dealapproval.DealApprovalListener;
import com.integrosys.cms.app.eventmonitor.waiverdeferral.WaiverDeferralListener;
import com.integrosys.cms.app.notification.proxy.ICMSNotificationProxy;
import com.integrosys.cms.app.user.bus.StdUserDAO;
import com.integrosys.component.notification.bus.NotificationCreateException;
import com.integrosys.component.notification.bus.NotificationMessageGeneratorException;
import com.integrosys.component.notification.bus.OBNotificationRecipient;

public abstract class CommonCMSEventListener extends AbstractCommonListener {

	private static final String NOTIFICATION_TEMPLATE_KEY = "cms.eventmonitor.eventnotification.template.";

	/** Logger available for subclasses */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private ICMSNotificationProxy cmsNotificationProxy;

	public void setCmsNotificationProxy(ICMSNotificationProxy cmsNotificationProxy) {
		this.cmsNotificationProxy = cmsNotificationProxy;
	}

	public abstract void fireEvent(String eventID, List params) throws EventHandlingException;

	/**
	 * Copied Implementation from previous sendNotification() but with different
	 * parameters.
	 * @param eventID
	 * @param params
	 * @param receipientList
	 * @throws EventHandlingException
	 */
	protected void sendNotification(String eventID, List params, ArrayList receipientList)
			throws EventHandlingException {

		OBEventInfo info = (OBEventInfo) params.get(0); // just one element
		// expected, get it
		info.setNotificationDate(DateUtil.getDate());
		info.setEventID(eventID);
		info.setSubject(PropertyManager.getValue("cms.eventmonitor.eventnotification.subject." + eventID));
		info.setReceipient(getReceipientList(receipientList));
		info.setNotificationExpiryDate(computeExpiryDate(info, (IRuleParam) params.get(2)));

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
		String templateNameKey = NOTIFICATION_TEMPLATE_KEY + eventID;
		String templateFileName = PropertyManager.getValue(templateNameKey);

		logger.info("Generating notification for event [" + eventID + "] using template file name [" + templateFileName
				+ "], subject [" + info.getSubject() + "], recipients [" + info.getReceipient() + "]");

		try {
			this.cmsNotificationProxy.sendEventNotification(info, receipientList, countryList, segment,
					templateFileName);
		}
		catch (NotificationCreateException e) {
			throw new EventHandlingException("Unable to create notification", e);
		}
		catch (NotificationMessageGeneratorException e) {
			throw new EventHandlingException("Unable to generate notification message", e);
		}

	}

	/**
	 * This method is invoked by the EventManager on IEventListeners when an
	 * event is trigged.
	 * 
	 * @param eventID is the String value of the Event ID that is causing the
	 *        triggering
	 * @param params is a List of String value parameters. This parameter should
	 *        be null if no parameters are required (event obj, code, rule
	 *        param)
	 * @throws EventHandlingException if an exception occurs while servicing the
	 *         event
	 */
	protected void sendNotification(String eventID, List params, String[] eventReceipientGroup)
			throws EventHandlingException {

		OBEventInfo info = (OBEventInfo) params.get(0); // just one element
		// expected, get it
		info.setNotificationDate(DateUtil.getDate());
		info.setEventID(eventID);
		info.setSubject(PropertyManager.getValue("cms.eventmonitor.eventnotification.subject." + eventID));
		info.setReceipient(PropertyManager.getValue("cms.eventmonitor.eventnotification.recipient." + eventID));

		info.setNotificationExpiryDate(computeExpiryDate(info, (IRuleParam) params.get(2)));

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
		String templateNameKey = NOTIFICATION_TEMPLATE_KEY + eventID;
		String templateFileName = PropertyManager.getValue(templateNameKey);

		logger.info("Generating notification for event [" + eventID + "] using template file name [" + templateFileName
				+ "], subject [" + info.getSubject() + "], recipients [" + info.getReceipient() + "]");

		try {
			this.cmsNotificationProxy.sendEventNotification(info, eventReceipientGroup, countryList, segment,
					templateFileName);
		}
		catch (NotificationCreateException e) {
			throw new EventHandlingException("Unable to create notification", e);
		}
		catch (NotificationMessageGeneratorException e) {
			throw new EventHandlingException("Unable to generate notification message", e);
		}

	}

	public String[] getEventRecipientGroup(String eventID) {
		String RECIPIENT_GROUP_KEY = "cms.eventmonitor.eventnotification.recipient.";

		String recipientGroup = PropertyManager.getValue(RECIPIENT_GROUP_KEY + eventID);
		if ((null == recipientGroup) || "".equals(recipientGroup)) {
			return new String[0];
		}
		StringTokenizer st = new StringTokenizer(recipientGroup, ",");
		ArrayList ll = new ArrayList();
		while (st.hasMoreTokens()) {
			String ss = st.nextToken();
			if (ss != null) {
				ss = ss.trim();
				ll.add(ss);
			}
		}

		return (String[]) ll.toArray(new String[0]);
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
		if (ruleParam.getRuleNum() == -1) {
			num = RuleParamUtil.getInt(ruleParam.getRuleID(), "elapsedDaysFromExpiry");
		}
		else {
			num = RuleParamUtil.getInt(ruleParam.getRuleID(), "elapsedDaysFromExpiry" + "." + ruleParam.getRuleNum());
		}

		return CommonUtil.rollUpDateByDays(eventInfo.getNotificationDate(), num);
	}

	private String getReceipientList(ArrayList receipientList) throws EventHandlingException {
		StdUserDAO dao = new StdUserDAO();
		if ((receipientList == null) || (receipientList.size() == 0)) {
			return null;
		}
		Iterator itr = receipientList.iterator();
		ArrayList userList = new ArrayList();
		ArrayList userGroupList = new ArrayList();

		while (itr.hasNext()) {
			OBNotificationRecipient receipient = (OBNotificationRecipient) itr.next();
			if ((receipient.getUserID() != null) && (receipient.getUserID().longValue() > 0)) {
				userList.add(receipient.getUserID().toString());
				userGroupList.add(receipient.getTeamID().toString());
			}
		}
		String returnStr = "";
		if (userList.size() > 0) {
			try {
				Collection userInfoList = dao.getUserInfo(userList, userGroupList);
				Iterator itrUser = userInfoList.iterator();
				while (itrUser.hasNext()) {
					returnStr += (String) itrUser.next() + ",";
				}
			}
			catch (SearchDAOException e) {
				throw new EventHandlingException("Unable to retrieve user info", e);
			}

		}
		if (this instanceof DealApprovalListener) {
			returnStr += "CMT";
		}
		if (this instanceof WaiverDeferralListener) {
			returnStr += "CPC";
		}
		return returnStr;
	}
}
