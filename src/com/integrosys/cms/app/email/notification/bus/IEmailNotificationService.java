package com.integrosys.cms.app.email.notification.bus;

import java.util.ArrayList;

public interface IEmailNotificationService {

	public static String NOTIFICATION_MAPPING_PREFIX = "integrosys.notification.";
	public static String NOTIFICATION_MAPPING_MSG_RECIPIENT_SUFFIX = ".msg.recipient";
	public static String NOTIFICATION_MAPPING_MSG_SUBJECT_SUFFIX = ".msg.subject";
	public static String NOTIFICATION_MAPPING_MSG_BODY_SUFFIX = ".msg.body";
	
	public static String NOTIFICATION_RECIPIENT_TYPE_LSS_UNIT_HEAD = "LSS_UNIT_HEAD";
	public static String NOTIFICATION_RECIPIENT_TYPE_RM = "RM";
	public static String NOTIFICATION_RECIPIENT_TYPE_RM_HEAD = "RM_HEAD";
	
	public static String SENT_YES = "Y";
	public static String SENT_NO = "N";

	IEmailNotification createNotification(String notificationType,ICustomerNotificationDetail notificationDetail) throws EmailNotificationException;
	
	IEmailNotification createNotificationCaseCreation(String notificationType,ArrayList notificationDetailList,String branch,String segment) throws EmailNotificationException;
	
}
