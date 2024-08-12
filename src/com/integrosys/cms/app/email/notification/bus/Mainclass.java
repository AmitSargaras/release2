package com.integrosys.cms.app.email.notification.bus;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.util.DateUtil;

public class Mainclass {
public static void main(String[] args) {
//	System.out.println("Getting EmailNotificationService");
	IEmailNotificationService service=(IEmailNotificationService)BeanHouse.get("emailNotificationService");
//	System.out.println("Prepairing Notification Object");
	
	IEmailNotification noticationInfo= new OBEmailNotification();
	noticationInfo.setIsSent("N");
	noticationInfo.setCreateBy("SYSTEM");
	noticationInfo.setCreationDate(DateUtil.getDate());
	noticationInfo.setNoticationTypeCode("NOT0001");
	noticationInfo.setMsgSubject("Party Closed");
	noticationInfo.setMsgBody("Hi User,\n The party is closed");
	//TODO: method to retrive recipent emailID
	noticationInfo.setRecipentEmailId("anil@pc251.aurion.net");

//	System.out.println("Creating notification");
//	IEmailNotification createNotification = service.createNotification("NOT0001", noticationInfo);
//	System.out.println("Notification Created"+createNotification.getNotifcationId());
	
	
	}
}
