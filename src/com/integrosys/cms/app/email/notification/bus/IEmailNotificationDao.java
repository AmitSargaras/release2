package com.integrosys.cms.app.email.notification.bus;

import java.util.List;


public interface IEmailNotificationDao {

	public static final String ACTUAL_EMAIL_NOTIFICATION = "actualEmailNotification";
	
	IEmailNotification createEmailNotification(String entityName, IEmailNotification emailNotification) throws EmailNotificationException;

	IEmailNotification updateEmailNotification(String entityName, IEmailNotification emailNotification) throws EmailNotificationException;
	List getAllPendingNotificationList() throws EmailNotificationException;
}
