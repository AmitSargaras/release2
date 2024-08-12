package com.integrosys.cms.app.email.notification.bus;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.proxy.IGeneralParamProxy;

public class EmailNotificationServiceImpl implements IEmailNotificationService {
	private IEmailNotificationDao emailNotificationDao;
	private IEmailNotificationJdbc emailNotificationJdbc;
	
	public IEmailNotificationDao getEmailNotificationDao() {
		return emailNotificationDao;
	}
	public void setEmailNotificationDao(IEmailNotificationDao emailNotificationDao) {
		this.emailNotificationDao = emailNotificationDao;
	}

	/**
	 * @return the emailNotificationJdbc
	 */
	public IEmailNotificationJdbc getEmailNotificationJdbc() {
		return emailNotificationJdbc;
	}
	/**
	 * @param emailNotificationJdbc the emailNotificationJdbc to set
	 */
	public void setEmailNotificationJdbc(IEmailNotificationJdbc emailNotificationJdbc) {
		this.emailNotificationJdbc = emailNotificationJdbc;
	}
	public IEmailNotification createNotification(String notificationType,
			ICustomerNotificationDetail notificationDetail)
			throws EmailNotificationException {
		IEmailNotification noticationInfo= new OBEmailNotification();
		if(notificationType!=null && notificationType.startsWith("NOT")){
				//message processing will be done here
				String fromServer=PropertyManager.getValue("integrosys.server.identification","app1");
				noticationInfo.setIsSent(SENT_NO);
				noticationInfo.setCreateBy("SYSTEM");
				noticationInfo.setCreationDate(DateUtil.getDate());
				noticationInfo.setNoticationTypeCode(notificationType);
				noticationInfo.setRecipentEmailId(getRecipients(notificationType,notificationDetail.getPartyId(),notificationDetail));
				noticationInfo.setMsgSubject(getSubject(notificationType,notificationDetail));
				noticationInfo.setMsgBody(getMessageBody(notificationType,notificationDetail));
				if(notificationType.equals("NOT00023") && fromServer.equals("app1")) {
					noticationInfo.setFromServer("");
				}else {
				noticationInfo.setFromServer(fromServer);
				}
			return getEmailNotificationDao().createEmailNotification(IEmailNotificationDao.ACTUAL_EMAIL_NOTIFICATION, noticationInfo);
		}else{
			throw new EmailNotificationException("Invalid notification Type"+notificationType);
		}
	}
	
	public IEmailNotification createNotificationCaseCreation(String notificationType,ArrayList notificationDetailList,String branch,String segment)
			throws EmailNotificationException {
		IEmailNotification noticationInfo= new OBEmailNotification();
		if(notificationType!=null && notificationType.startsWith("NOT")){
				//message processing will be done here
				noticationInfo.setIsSent(SENT_NO);
				noticationInfo.setCreateBy("SYSTEM");
				noticationInfo.setCreationDate(DateUtil.getDate());
				noticationInfo.setNoticationTypeCode(notificationType);
				noticationInfo.setRecipentEmailId(getRecipientBranchIds(notificationType,branch));
				noticationInfo.setMsgSubject(getSubjectCaseCreation(notificationType,segment,branch));
				noticationInfo.setMsgBody(getMessageBodyCaseCreation(notificationType,notificationDetailList,branch));
				String fromServer=PropertyManager.getValue("integrosys.server.identification","app1");
				noticationInfo.setFromServer(fromServer);
				DefaultLogger.debug(this, "getRecipentEmailId ::==>"+noticationInfo.getRecipentEmailId());
			return getEmailNotificationDao().createEmailNotification(IEmailNotificationDao.ACTUAL_EMAIL_NOTIFICATION, noticationInfo);
		}else{
			throw new EmailNotificationException("Invalid notification Type"+notificationType);
		}
	}
	private String getRecipients(String notificationType, String partyId, ICustomerNotificationDetail notificationDetail) {
		
		String recipientType = PropertyManager.getValue(NOTIFICATION_MAPPING_PREFIX+notificationType+NOTIFICATION_MAPPING_MSG_RECIPIENT_SUFFIX);
		String[] recipientTypeArray = recipientType.split("\\|");
		String tempRecipient;
		String finalRecipients="";
		StringBuffer diaryRecipients=new StringBuffer();
		for (int i = 0; i < recipientTypeArray.length; i++) {
			tempRecipient = recipientTypeArray[i];
			if(NOTIFICATION_RECIPIENT_TYPE_LSS_UNIT_HEAD.equals(tempRecipient)){
				IGeneralParamProxy generalParamProxy  =(IGeneralParamProxy)BeanHouse.get("generalParamProxy");
				IGeneralParamEntry generalParamEntry = generalParamProxy.getGeneralParamEntryByParamCodeActual(IGeneralParamEntry.PARAM_CODE_LSS_UNIT_HEAD);
				finalRecipients=finalRecipients+generalParamEntry.getParamValue();
				
			}else if(NOTIFICATION_RECIPIENT_TYPE_RM.equals(tempRecipient) 
					// below block will append the RM & RM Head email id hence no extra check is required for RM head 
					//|| NOTIFICATION_RECIPIENT_TYPE_RM_HEAD.equals(tempRecipient)  
					){
				DefaultLogger.debug(this, "finalRecipients ::"+finalRecipients);
				String str = emailNotificationJdbc.getRMAndRMHeadByCustomerId(partyId);
			//	DefaultLogger.debug(this, " emailNotificationJdbc.getRMAndRMHeadByCustomerId(partyId) ::"+str);
				finalRecipients=finalRecipients+","+str;
			}
			
			
		}
		
		if(notificationType.equals("NOT00023") ||notificationType.equals("NOT00024") || notificationType.equals("NOT00025") || notificationType.equals("NOT00026")) {
			 List recipientDiaryTypeArray = emailNotificationJdbc.getTodaysDiaryEmailIdList(notificationDetail.getSegment());
			 ListIterator iter = recipientDiaryTypeArray.listIterator();
			 while(iter.hasNext()) {
				 diaryRecipients.append(","+iter.next());
			 }
			 finalRecipients = diaryRecipients.toString();
			 DefaultLogger.debug(this, "diaryRecipients ::==>"+finalRecipients);
		}
		DefaultLogger.debug(this, "finalRecipients ::==>"+finalRecipients);
		return finalRecipients;
	}
private String getRecipientBranchIds(String notificationType, String branch) {
		
		
		String finalRecipients="";
		if(notificationType.equalsIgnoreCase("NOT0018")||notificationType.equalsIgnoreCase("NOT0022")){
			String recipientType = PropertyManager.getValue(NOTIFICATION_MAPPING_PREFIX+notificationType+NOTIFICATION_MAPPING_MSG_RECIPIENT_SUFFIX);
			String[] recipientTypeArray = recipientType.split("\\|");
			String tempRecipient;
			
			for (int i = 0; i < recipientTypeArray.length; i++) {
				tempRecipient = recipientTypeArray[i];
				if(NOTIFICATION_RECIPIENT_TYPE_LSS_UNIT_HEAD.equals(tempRecipient)){
					IGeneralParamProxy generalParamProxy  =(IGeneralParamProxy)BeanHouse.get("generalParamProxy");
					IGeneralParamEntry generalParamEntry = generalParamProxy.getGeneralParamEntryByParamCodeActual(IGeneralParamEntry.PARAM_CODE_LSS_UNIT_HEAD);
					finalRecipients=finalRecipients+generalParamEntry.getParamValue();
					
				}
				
			}
			
		}else{
		DefaultLogger.debug(this, "finalRecipients ::"+finalRecipients);
		String str = emailNotificationJdbc.getBranchCoordinatorEmailId(branch);
		DefaultLogger.debug(this, " emailNotificationJdbc.getBranchCoordinatorEmailId ::"+str);
		finalRecipients=finalRecipients+","+str;
		DefaultLogger.debug(this, "finalRecipients ::==>"+finalRecipients);
		}
		return finalRecipients;
	}
	private String getSubject(String notificationType, ICustomerNotificationDetail notificationDetail) {
		String subject = PropertyManager.getValue(NOTIFICATION_MAPPING_PREFIX+notificationType+NOTIFICATION_MAPPING_MSG_SUBJECT_SUFFIX," LSS Notification");
		subject = replaceActualValues(subject,notificationDetail);
		return subject;
	}
	private String getSubjectCaseCreation(String notificationType , String segment , String branch) {
		String subject = PropertyManager.getValue(NOTIFICATION_MAPPING_PREFIX+notificationType+NOTIFICATION_MAPPING_MSG_SUBJECT_SUFFIX," LSS Notification");
		
		subject = replaceActualValuesCaseSubjectSegment(subject,segment,branch);
	
		return subject;
	}
	private String getMessageBody(String notificationType, ICustomerNotificationDetail notificationDetail) {
		String messageBody = PropertyManager.getValue(NOTIFICATION_MAPPING_PREFIX+notificationType+NOTIFICATION_MAPPING_MSG_BODY_SUFFIX,"Regards,\n LSS Team");
		messageBody = replaceActualValues(messageBody,notificationDetail);
		return messageBody;
	}
	
	private String getMessageBodyCaseCreation(String notificationType, ArrayList notificationDetail, String branch) {
		String messageBody = PropertyManager.getValue(NOTIFICATION_MAPPING_PREFIX+notificationType+NOTIFICATION_MAPPING_MSG_BODY_SUFFIX,"Regards,\n LSS Team");
		messageBody = replaceActualValuesCaseCreation(notificationType,messageBody,notificationDetail,branch);
		return messageBody;
	}
	private String replaceActualValues(String input,
			ICustomerNotificationDetail notificationDetail) {
		if(input!=null &&!"".equals(input)){
			input=input.replaceAll("<PartyName>", notificationDetail.getPartyName()==null?"":notificationDetail.getPartyName());
			input=input.replaceAll("<CamExpiryDate>", notificationDetail.getCamExpiryDate()==null?"":notificationDetail.getCamExpiryDate());
			input=input.replaceAll("<LadDueDate>", notificationDetail.getLadDueDate()==null?"":notificationDetail.getLadDueDate());
			input=input.replaceAll("<LadExpiryDate>", notificationDetail.getLadExpiryDate()==null?"":notificationDetail.getLadExpiryDate());
			input=input.replaceAll("<CamCreationDate>", notificationDetail.getCamCreationDate()==null?"":notificationDetail.getCamCreationDate());
			input=input.replaceAll("<SecuritySubType>", notificationDetail.getSecuritySubType()==null?"":notificationDetail.getSecuritySubType());
			input=input.replaceAll("<SecurityMaturityDate>", notificationDetail.getSecurityMaturityDate()==null?"":notificationDetail.getSecurityMaturityDate());
			input=input.replaceAll("<DrawingPower>", notificationDetail.getDrawingPower()==null?"":notificationDetail.getDrawingPower());
			input=input.replaceAll("<ReleasableAmount>", notificationDetail.getReleasableAmount()==null?"":notificationDetail.getReleasableAmount());
			input=input.replaceAll("<DocDueDate>", notificationDetail.getDocDueDate()==null?"":notificationDetail.getDocDueDate());
			input=input.replaceAll("<InsMsgString>", notificationDetail.getInsMsgString()==null?"":notificationDetail.getInsMsgString());
			input=input.replaceAll("<FacilityLineNumber>", notificationDetail.getFacilityLineNo()==null?"":notificationDetail.getFacilityLineNo());
			input=input.replaceAll("<FacilitySerialNumber>", notificationDetail.getFacilitySerialNo()==null?"":notificationDetail.getFacilitySerialNo());
			input=input.replaceAll("<Remarks>", notificationDetail.getDescription()==null?"":notificationDetail.getDescription());
			input=input.replaceAll("<closingbalance>", notificationDetail.getClosingBalance()==null?"":notificationDetail.getClosingBalance());
			
			
			
			
			return input;
		}else{
			return input;
		}
	}
	
	private String replaceActualValuesCaseSubjectSegment(String input,String segment, String branch) {
		if(input!=null &&!"".equals(input)){
			input=input.replaceAll("<Segment>", segment==null?"":segment);
			input=input.replaceAll("<Branch>",branch==null?"":getBranchName(branch));
			
			return input;
		}else{
			return input;
		}
	}

	
	private String replaceActualValuesCaseCreation(String notificationType, String input,ArrayList notificationDetail, String branch) {
		
		
		
		StringBuffer tableString=new StringBuffer("<table  border='2' cellspacing='2' cellpadding='2' ><thead>")
		.append("<tr> <td style='font-size:10pt;' width='5%'>S/N</td> <td style='font-size:10pt;' width='16%'>Case Creation No.</td> <td style='font-size:10pt;' width='10%'>Creation Date</td> <td style='font-size:10pt;' width='20%'>Name of the Party</td> <td style='font-size:10pt;' width='10%'> Party Id</td> <td style='font-size:10pt;' width='10%'>Party Segment</td> <td style='font-size:10pt;' width='10%'>Case Creation Branch</td> <td style='font-size:10pt;' width='5%'>No. of Docs </td> ");
		if(notificationType.equalsIgnoreCase("NOT0020")||notificationType.equalsIgnoreCase("NOT0021")){
			tableString.append("<td style='font-size:10pt;'> Pending Days </td>");
		}if(notificationType.equalsIgnoreCase("NOT0022")){
			tableString.append("<td style='font-size:10pt;'> Reason </td>");
		}
		tableString.append("</tr> </thead>")
		.append(" <tbody> ");
		if(notificationDetail!=null && notificationDetail.size()>0){
			for(int i=0;i<notificationDetail.size();i++){
				ICaseCreationNotificationDetail caseCreationNotificationDetail=(ICaseCreationNotificationDetail)notificationDetail.get(i);
				if(caseCreationNotificationDetail!=null){
					tableString.append("<tr><td style='font-size:10pt;'>");
					tableString.append(i+1);
					tableString.append("</td>");
					tableString.append("<td style='font-size:10pt;'>");
					tableString.append(caseCreationNotificationDetail.getCaseCreationId());
					tableString.append("</td>");
					tableString.append("<td style='font-size:10pt;'>");
					tableString.append(caseCreationNotificationDetail.getCaseCreationDate());
					tableString.append("</td>");
					tableString.append("<td style='font-size:10pt;'>");
					tableString.append(caseCreationNotificationDetail.getPartyName());
					tableString.append("</td>");
					tableString.append("<td style='font-size:10pt;'>");
					tableString.append(caseCreationNotificationDetail.getPartyId());
					tableString.append("</td>");
					tableString.append("<td style='font-size:10pt;'>");
					tableString.append(caseCreationNotificationDetail.getSegment());
					tableString.append("</td>");
					tableString.append("<td style='font-size:10pt;'>");
					tableString.append(getBranchName(caseCreationNotificationDetail.getBranch()));
					tableString.append("</td>");
					tableString.append("<td style='font-size:10pt;'>");
					tableString.append(caseCreationNotificationDetail.getDocNos());
					tableString.append("</td>");
					if(notificationType.equalsIgnoreCase("NOT0020")||notificationType.equalsIgnoreCase("NOT0021")){
					tableString.append("<td style='font-size:10pt;'>");
					tableString.append(caseCreationNotificationDetail.getPendingDays());
					tableString.append("</td>");
					}
					if(notificationType.equalsIgnoreCase("NOT0022")){
						tableString.append("<td style='font-size:10pt;'>");
						if(caseCreationNotificationDetail.getRemarks()!=null){
						tableString.append(caseCreationNotificationDetail.getRemarks());
						}
						tableString.append("</td>");
						}
					tableString.append("</tr>");
				}
			}
		}
		tableString.append(" </tbody> </table> ");
		
		
		
		if(input!=null &&!"".equals(input)){
			input=input.replaceAll("<Table>", tableString.toString());
			input=input.replaceAll("<Branch>", getBranchName(branch));
			
			return input;
		}else{
			return input;
		}
	}
	
private String getBranchName( String branch) {
		
		
		
		
		String str = emailNotificationJdbc.getBranchName(branch);
		DefaultLogger.debug(this, " emailNotificationJdbc.getBranchBranchName ::"+str);
	
		return str;
	}
	

}
