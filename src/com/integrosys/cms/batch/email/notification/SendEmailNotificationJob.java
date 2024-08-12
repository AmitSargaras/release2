package com.integrosys.cms.batch.email.notification;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Calendar;

import javax.mail.MessagingException;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.email.notification.bus.IEmailNotification;
import com.integrosys.cms.app.email.notification.bus.IEmailNotificationDao;

public class SendEmailNotificationJob {
	private IEmailNotificationDao emailNotificationDao;
	private MailUtil mailUtil;;

	public IEmailNotificationDao getEmailNotificationDao() {
		return emailNotificationDao;
	}

	public void setEmailNotificationDao(IEmailNotificationDao emailNotificationDao) {
		this.emailNotificationDao = emailNotificationDao;
	}

	
	/**
	 * @return the mailUtil
	 */
	public MailUtil getMailUtil() {
		return mailUtil;
	}

	/**
	 * @param mailUtil the mailUtil to set
	 */
	public void setMailUtil(MailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}

	/**
	 * This job is run and executed by quartz schedular. For more details refer
	 * to schedular configuration in
	 * config\spring\recurrent\AppContext_Batch.xml
	 * 
	 */

	public void execute() {/*
		DefaultLogger.debug(this, "start sending emails SendEmailNotificationJob........");
		System.out.println("start sending emails SendEmailNotificationJob........");
		String value = PropertyManager.getValue("mail.notification.on", "true");
		DefaultLogger.debug(this, "mail.notification.on...after" + value);
		System.out.println("mail.notification.on...after" + value);
		ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
		String emailServerName = bundle1.getString("integrosys.server.identification.email");
		DefaultLogger.debug(this, "SendEmailNotificationJob started line 56....emailServerName=>"+emailServerName+" ..Time to start.." + Calendar.getInstance().getTime());
		System.out.println("SendEmailNotificationJob started line 56....emailServerName=>"+emailServerName+" ..Time to start.." + Calendar.getInstance().getTime());
		if (null != emailServerName && "app1".equalsIgnoreCase(emailServerName)) {
			DefaultLogger.debug(this, "Inside emailServerName if condition SendEmailNotificationJob started line 59.");
		
		if ("true".equals(value)) {
			DefaultLogger.debug(this, "inside if condition........" + value);
			System.out.println("inside if condition........" + value);
			try {
				List pendingNotificationList = getEmailNotificationDao().getAllPendingNotificationList();
				IEmailNotification notification=null;
				for (Iterator iterator = pendingNotificationList.iterator(); iterator.hasNext();) {
					boolean errFlag = false;
					notification = (IEmailNotification) iterator.next();
					try {
						getMailUtil().sendEmailNotification(notification);
					} catch (MessagingException mex) {
						DefaultLogger.debug(this,"Exception line no 73 in SendEmailNotificationJob...");
						System.out.println("Exception line no 73 in SendEmailNotificationJob...");
						errFlag = true;
						DefaultLogger.debug(this, "Error while sending mail" + mex);
						System.out.println("Error while sending mail" + mex);
						mex.printStackTrace();
						notification.setIsSent("N");
						notification.setErrorLog(mex.getMessage());
						getEmailNotificationDao().updateEmailNotification(IEmailNotificationDao.ACTUAL_EMAIL_NOTIFICATION,notification);
					}catch(Exception e) {
						errFlag = true;
						e.printStackTrace();
					//	DefaultLogger.debug(this, "Error while sending mail" + mex);
					//	mex.printStackTrace();
						notification.setIsSent("N");
						notification.setErrorLog("Recipent Email Id is blank.");
						getEmailNotificationDao().updateEmailNotification(IEmailNotificationDao.ACTUAL_EMAIL_NOTIFICATION,notification);
						DefaultLogger.debug(this,"Exception line no 87 in SendEmailNotificationJob..." +e);
						System.out.println("Exception line no 87 in SendEmailNotificationJob..." +e);
					}
					if(errFlag == false) {
					notification.setIsSent("Y");
					getEmailNotificationDao().updateEmailNotification(IEmailNotificationDao.ACTUAL_EMAIL_NOTIFICATION,
							notification);
					}
				}

			} catch (Exception e) {
				System.out.println("Exception line no 99 in SendEmailNotificationJob..." );
				e.printStackTrace();
			}
		}
		}
		DefaultLogger.debug(this, "end sending emails SendEmailNotificationJob........" + value);
	*/}

	public SendEmailNotificationJob() {
	}

	public static void main(String[] args) {
	//	new SendEmailNotificationJob().execute();
	}
}
