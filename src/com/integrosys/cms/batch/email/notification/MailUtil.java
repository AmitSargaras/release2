package com.integrosys.cms.batch.email.notification;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.email.notification.bus.IEmailNotification;

public class MailUtil {
	/** this constant is used to define mail contentType as plain text*/
	public static final String TEXT="text/plain";

	/** this constant is used to define mail contentType as HTML*/
	public static final String HTML="text/html";
	
	private final String sender=PropertyManager.getValue("mail.sender.emailid", "clims@hdfcbank.com");
	private final String password=PropertyManager.getValue("mail.sender.password");

	public void sendEmailNotification(IEmailNotification notification) throws MessagingException {
		DefaultLogger.debug(this, "inside sendEmailNotification........");
		System.out.println( "inside sendEmailNotification........");
		Properties props = new Properties();
		props.put("mail.smtp.host", PropertyManager.getValue("mail.smtp.host"));
		props.put("mail.smtp.port", PropertyManager.getValue("mail.smtp.port", "25"));
		props.put("mail.debug", PropertyManager.getValue("mail.debug", "false"));
		SMTPAuthenticator authenticator= new SMTPAuthenticator(sender,password);
		Session session = Session.getInstance(props, null);
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(sender));
			System.out.println("notification.getRecipentEmailId()..................."+notification.getRecipentEmailId());
			System.out.println("Message.RecipientType.TO........"+Message.RecipientType.TO);
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(notification.getRecipentEmailId(), true));
			// if(false){
			// msg.addRecipients(Message.RecipientType.CC,InternetAddress.parse("", true));
			// }
			DefaultLogger.debug(this, "msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(notification.getRecipentEmailId(), true))...");
			msg.setSubject(notification.getMsgSubject());
			msg.setSentDate(DateUtil.getDate());
			msg.setText(notification.getMsgBody());
            msg.setContent(notification.getMsgBody(), HTML);
            msg.saveChanges();
			Transport.send(msg);
			DefaultLogger.debug(this, "Transport.send(msg);");
			System.out.println( "Transport.send(msg);");
		} catch (MessagingException mex) {
			while (mex.getNextException() != null) {
				Exception ex = mex.getNextException();
				if (!(ex instanceof MessagingException))
					break;
				else
					mex = (MessagingException) ex;
			}
			throw mex;
		}
	}
	
	class SMTPAuthenticator extends javax.mail.Authenticator {
		private PasswordAuthentication authentication;

		public SMTPAuthenticator(String sender, String password) {
			authentication = new PasswordAuthentication(sender, password);
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}
	
}
