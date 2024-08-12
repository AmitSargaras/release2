/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/notification/proxy/CMSNotificationProxyImpl.java,v 1.26 2006/11/02 08:08:16 jzhan Exp $
 */

package com.integrosys.cms.app.notification.proxy;

import java.io.StringWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.bus.PaginationBean;
import com.integrosys.cms.app.common.bus.PaginationResult;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.eventmonitor.OBEventInfo;
import com.integrosys.cms.app.eventmonitor.VelocityNotificationUtil;
import com.integrosys.cms.app.eventmonitor.common.CollateralCoverageUtil;
import com.integrosys.cms.app.notification.bus.CMSNotificationException;
import com.integrosys.cms.app.notification.bus.CMSNotificationManagerFactory;
import com.integrosys.cms.app.notification.bus.ICMSNotificationManager;
import com.integrosys.cms.app.notification.bus.OBCMSNotification;
import com.integrosys.component.notification.bus.INotification;
import com.integrosys.component.notification.bus.NotificationCreateException;
import com.integrosys.component.notification.bus.NotificationMessageGeneratorException;
import com.integrosys.component.notification.bus.OBNotificationRecipient;
import com.integrosys.component.notification.bus.OBNotificationType;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.26 $
 * @since $Date: 2006/11/02 08:08:16 $ Tag: $Name: $
 */

public class CMSNotificationProxyImpl implements ICMSNotificationProxy {

	private VelocityEngine velocityEngine;

	private SBCMSNotificationProxyManager cmsNotificationProxyManager;

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setCmsNotificationProxyManager(SBCMSNotificationProxyManager cmsNotificationProxyManager) {
		this.cmsNotificationProxyManager = cmsNotificationProxyManager;
	}

	private void notify(INotification notification) throws NotificationCreateException,
			NotificationMessageGeneratorException {
		ICMSNotificationManager notificationMgr = CMSNotificationManagerFactory.getManager();
		notificationMgr.createNotification(notification);
	}

	/**
	 * Copied Implementation from origianl sendEventNotification() with
	 * different parameter.
	 * @see com.integrosys.cms.app.notification.proxy.ICMSNotificationProxy#sendEventNotification(com.integrosys.cms.app.eventmonitor.OBEventInfo,
	 *      java.util.ArrayList, java.lang.String[], java.lang.String,
	 *      java.lang.String)
	 */
	public void sendEventNotification(OBEventInfo info, ArrayList receipientList, String[] countryList, String segment,
			String velocityTemplate) throws NotificationCreateException, NotificationMessageGeneratorException {
		{

			OBCMSNotification nn = new OBCMSNotification();
			OBNotificationType notiType = new OBNotificationType();
			notiType.setNotificationTypeID(1);
			String title = info.getSubject();
			String leId = info.getLeID();
			String leName = info.getLeName();
			if ((leId != null) && !leId.equals("") && (leName != null) & !leName.equals("")) {
				title = title + " - " + leId + " , " + leName;
			}
			notiType.setNotificationTitle(title);

			nn.setLeID(leId);
			nn.setLeName(leName);
			nn.setNotificationTypeData(notiType);
			nn.setCreationDate(info.getNotificationDate());
			nn.setExpiryDate(info.getNotificationExpiryDate());
			if ((info.getDetails() != null) && (info.getDetails().length() > 0)) {
				nn.setDetails(info.getDetails());
			}
			else {
				nn.setDetails(getNotificationDetails(info));
			}

			try {
				String ss = PropertyManager.getValue("cms.eventmonitor.notification.debugflag", "false");
				if ("true".equals(ss)) {
					nn.setNotificationMessage(generateNotificationMessage(info, velocityTemplate) + "<br/>"
							+ AccessorUtil.printMethodValue(info));
				}
				else {
					DefaultLogger.debug(this, "Setting into message object");

					String ss2 = generateNotificationMessage(info, velocityTemplate);

					nn.setNotificationMessage(ss2);
				}
			}
			catch (Exception e) {
				throw new NotificationMessageGeneratorException("failed to generate notification message, event info ["
						+ info + "], template file name [" + velocityTemplate + "]", e);
			}

			nn.setNotificationRecipients(receipientList);

			// send the notification
			notify(nn);
		}

	}

	/**
	 * Origianl implementation
	 */
	public void sendEventNotification(OBEventInfo info, String[] eventReceipientGroup, String[] countryList,
			String segment, String velocityTemplate) throws NotificationCreateException,
			NotificationMessageGeneratorException {

		OBCMSNotification nn = new OBCMSNotification();
		OBNotificationType notiType = new OBNotificationType();
		notiType.setNotificationTypeID(1);
		String title = info.getSubject();
		String leId = info.getLeID();
		String leName = info.getLeName();
		if ((leId != null) && !leId.equals("") && (leName != null) & !leName.equals("")) {
			title = title + " - " + leId + " , " + leName;
		}
		notiType.setNotificationTitle(title);
		nn.setLeID(leId);
		nn.setLeName(leName);
		nn.setNotificationTypeData(notiType);
		nn.setCreationDate(info.getNotificationDate());
		nn.setExpiryDate(info.getNotificationExpiryDate());
		if ((info.getDetails() != null) && (info.getDetails().length() > 0)) {
			nn.setDetails(info.getDetails());
		}
		else {
			nn.setDetails(getNotificationDetails(info));
		}

		try {
			String ss = PropertyManager.getValue("cms.eventmonitor.notification.debugflag", "false");
			if ("true".equals(ss)) {
				nn.setNotificationMessage(generateNotificationMessage(info, velocityTemplate) + "<br/>"
						+ AccessorUtil.printMethodValue(info));
			}
			else {
				DefaultLogger.debug(this, "Setting into message object");
				String ss2 = generateNotificationMessage(info, velocityTemplate);

				nn.setNotificationMessage(ss2);
			}

		}
		catch (Exception e) {
			throw new NotificationMessageGeneratorException("failed to generate notification message, event info ["
					+ info + "], template file name [" + velocityTemplate + "]", e);
		}

		Long[] teamReceipients = null;
		try {
			teamReceipients = getReceipients(eventReceipientGroup, countryList, segment);
		}
		catch (Exception e) {
			throw new NotificationMessageGeneratorException(e);
		}

		ArrayList notificationReceipient = new ArrayList();

		for (int ii = 0; ii < teamReceipients.length; ii++) {
			OBNotificationRecipient receipient = new OBNotificationRecipient();
			receipient.setTeamID(teamReceipients[ii]);
			receipient.setUserID(new Long(ICMSConstant.LONG_INVALID_VALUE));
			notificationReceipient.add(receipient);
		}
		nn.setNotificationRecipients(notificationReceipient);

		// send the notification
		notify(nn);
	}

	private String generateNotificationMessage(OBEventInfo info, String velocityTemplateFileName) throws Exception {
		VelocityContext ctx = new VelocityContext();
		ctx.put("ob", info);
		ctx.put("DateUtil", new DateUtil());
		ctx.put("VelocityUtil", VelocityNotificationUtil.getInstance());
		ctx.put("CollateralCoverageUtil", CollateralCoverageUtil.getInstance());

		StringWriter sw = new StringWriter();
		this.velocityEngine.getTemplate(velocityTemplateFileName).merge(ctx, sw);

		return sw.toString();
	}

	/**
	 * get notification details = event id + LE ID or security ID + country
	 */
	// cms-2797 only show latest notification
	public String getNotificationDetails(OBEventInfo info) {
		if (info == null) {
			return null;
		}

		String details = info.getEventID() + " , ";

		if ((info.getLeID() != null) && (info.getLeID().length() > 0)) {
			details += info.getLeID() + " , ";
		}
		if ((info.getSecurityID() != null) && (info.getSecurityID().length() > 0)) {
			details += info.getSecurityID() + " , ";
		}

		details += info.getOriginatingCountry();
		return details;
	}

	/**
	 * get number of notification by team id
	 */
	public int countNotifications(long teamID, long userID) throws CMSNotificationException {
		try {
			return this.cmsNotificationProxyManager.countNotifications(teamID, userID);
		}
		catch (RemoteException e) {
			throw new CMSNotificationException(e.getCause());
		}
	}

	/**
	 * get notifications by team id //CR-120 Search Notification
	 */
	public ArrayList getNotificationsByTeamID(long userID, long teamID, String status, String legalName, long leID)
			throws CMSNotificationException {
		try {
			return this.cmsNotificationProxyManager.getNotificationsByTeamID(userID, teamID, status, legalName, leID);
		}
		catch (RemoteException e) {
			throw new CMSNotificationException(e.getCause());
		}
	}

	public PaginationResult getNotificationsByTeamID(long userID, long teamID, String status, String legalName,
			long leID, PaginationBean pgBean) throws CMSNotificationException {
		try {
			return this.cmsNotificationProxyManager.getNotificationsByTeamID(userID, teamID, status, legalName, leID,
					pgBean);
		}
		catch (RemoteException e) {
			throw new CMSNotificationException(e.getCause());
		}
	}

	/**
	 * get notifications by id
	 */
	public INotification getNotificationByID(long notificationID) throws CMSNotificationException {
		try {
			return this.cmsNotificationProxyManager.getNotificationByID(notificationID);
		}
		catch (RemoteException e) {
			throw new CMSNotificationException(e.getCause());
		}
	}

	/**
	 * update notification status
	 */
	public void updateNotificationStatus(String[] notifyIDArray, String status) throws CMSNotificationException {
		try {
			this.cmsNotificationProxyManager.updateNotificationStatus(notifyIDArray, status);
		}
		catch (RemoteException e) {
			throw new CMSNotificationException(e.getCause());
		}
	}

	/**
	 * delete notification by batch job
	 * @throws CMSNotificationException
	 */
	public void deleteNotification() throws CMSNotificationException {
		try {
			this.cmsNotificationProxyManager.deleteNotification();
		}
		catch (RemoteException e) {
			throw new CMSNotificationException(e.getCause());
		}
	}

	/**
	 * put notification into delete list
	 */
	public void deleteNotification(OBCMSNotification notification) throws CMSNotificationException {
		try {
			this.cmsNotificationProxyManager.deleteNotification(notification);
		}
		catch (RemoteException e) {
			throw new CMSNotificationException(e.getCause());
		}
	}

	/**
	 * To get the remote handler for the custodian proxy manager
	 * @return SBCustodianProxyManager - the remote handler for the custodian
	 *         proxy manager
	 */
	private SBCMSNotificationProxyManager getCMSNotificationProxyManager() {
		SBCMSNotificationProxyManager proxymgr = (SBCMSNotificationProxyManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CMS_NOTIFICATION_PROXY_JNDI, SBCMSNotificationProxyManagerHome.class.getName());
		return proxymgr;
	}

	private ArrayList getTeamTypeID(String notificationRecipientType) {
		String NOTIFICATION_TEAM_TYPE_KEY = "cms.notification.recipient.teamtypelist.";

		String teamTypeList = PropertyManager.getValue(NOTIFICATION_TEAM_TYPE_KEY + notificationRecipientType);
		DefaultLogger.debug(this, "Obtaining team type ID list for using key  " + teamTypeList);
		if ((null == teamTypeList) || "".equals(teamTypeList)) {
			return new ArrayList();
		}

		StringTokenizer st = new StringTokenizer(teamTypeList, ",");
		ArrayList ll = new ArrayList();
		while (st.hasMoreTokens()) {
			String ss = st.nextToken();
			if (ss != null) {
				ss = ss.trim();
				DefaultLogger.debug(this, "Added ID is " + ss);
				ll.add(ss);
			}
		}

		return ll;
	}

	/*
	 * Get receipient team ID list by team type id, country list and segment
	 * 
	 * @param receipientList receipient team type id list
	 * 
	 * @param countryList receipient country list
	 * 
	 * @param segment
	 */
	private Long[] getReceipients(String[] receipientList, String[] countryList, String segment)
			throws CMSNotificationException {
		String[] teamTypeIDList = null;
		ArrayList finalTeamTypeList = new ArrayList();
		for (int ii = 0; ii < receipientList.length; ii++) {
			finalTeamTypeList.addAll(getTeamTypeID(receipientList[ii]));
		}
		teamTypeIDList = (String[]) finalTeamTypeList.toArray(new String[0]);

		Long[] teamIDList = null;
		try {
			teamIDList = this.cmsNotificationProxyManager
					.getReceipientsTeamIDList(teamTypeIDList, countryList, segment);
		}
		catch (RemoteException e) {
			throw new CMSNotificationException(e.getCause());
		}

		return teamIDList;
	}
}
