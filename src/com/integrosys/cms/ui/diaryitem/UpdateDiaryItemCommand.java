/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/UpdateDiaryItemCommand.java,v 1.3 2004/05/28 06:21:10 jtan Exp $
 */
package com.integrosys.cms.ui.diaryitem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.diary.bus.IDiaryItem;
import com.integrosys.cms.app.diary.bus.IDiaryItemJdbc;
import com.integrosys.cms.app.diary.proxy.IDiaryItemProxyManager;
import com.integrosys.cms.app.email.notification.bus.ICustomerNotificationDetail;
import com.integrosys.cms.app.email.notification.bus.IEmailNotificationJdbc;
import com.integrosys.cms.app.email.notification.bus.IEmailNotificationService;
import com.integrosys.cms.app.email.notification.bus.OBCustomerNotificationDetail;
import com.integrosys.cms.batch.email.notification.EmailNotificationMain;

/**
 * This command updates the details of a diary item
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/05/28 06:21:10 $ Tag: $Name: $
 */

public class UpdateDiaryItemCommand extends DiaryItemsCommand {
	
	private final Logger logger = LoggerFactory.getLogger(EmailNotificationMain.class);

	// private IEmailNotificationDao emailNotificationDao;
	private IEmailNotificationJdbc emailNotificationJdbc;
	private IEmailNotificationService emailNotificationService;

	public IEmailNotificationJdbc getEmailNotificationJdbc() {
		return emailNotificationJdbc;
	}

	public void setEmailNotificationJdbc(IEmailNotificationJdbc emailNotificationJdbc) {
		this.emailNotificationJdbc = emailNotificationJdbc;
	}

	public IEmailNotificationService getEmailNotificationService() {
		return emailNotificationService;
	}

	public void setEmailNotificationService(IEmailNotificationService emailNotificationService) {
		this.emailNotificationService = emailNotificationService;
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "diaryItemObj", "com.integrosys.cms.app.diary.bus.OBDiaryItem", FORM_SCOPE },
			{ "frompage", "java.lang.String", REQUEST_SCOPE },});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
	 * @throws CommandValidationException on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		DefaultLogger.debug(this,"Inside UpdateDiaryItemCommand.java" );
		IDiaryItemProxyManager proxyMgr = getDiaryItemProxyManager();
		IEmailNotificationService emailNotificationServiceobj=(IEmailNotificationService)BeanHouse.get("emailNotificationService");
		IDiaryItem item = (IDiaryItem) map.get("diaryItemObj");
		if(item.getDropLineOD().equals("N") && "Extend".equals(item.getAction())) {
			item.setDueDate(item.getNextTargetDate());
			item.setIsDelete("N");
			item.setCloseBy("");
			item.setCloseDate("");
			item.setStatus("Open");
		}
		if("Open".equals(item.getAction()) || "Closed".equals(item.getAction())) {
		item.setStatus(item.getAction());
		}
		IDiaryItemJdbc diaryItemJdbc=(IDiaryItemJdbc)BeanHouse.get("diaryItemJdbc");
		try {
			if("list_non_expired".equals(map.get("frompage"))) {
			proxyMgr.updateDiaryItem(item);
			if("Extend".equals(item.getAction())) {
				DefaultLogger.debug(this," UpdateDiaryItemCommand.java Line no. 98." );
				 ICustomerNotificationDetail notificationDetail=new OBCustomerNotificationDetail();
					DefaultLogger.error(this,"Total Diary Notification found :" );
					notificationDetail.setPartyId(item.getCustomerReference());
					notificationDetail.setPartyName(item.getCustomerName());
					notificationDetail.setFacilityLineNo(item.getFacilityLineNo());
					notificationDetail.setFacilitySerialNo(item.getFacilityLineNo());
					notificationDetail.setDescription(item.getDescription());
					notificationDetail.setSegment(item.getCustomerSegment());
					
					emailNotificationServiceobj.createNotification("NOT00025", notificationDetail);	
			}
			if(item.getDropLineOD().equals("N") && "Closed".equals(item.getAction())) {
				 ICustomerNotificationDetail notificationDetail=new OBCustomerNotificationDetail();
				 DefaultLogger.debug(this," UpdateDiaryItemCommand.java Line no. 112." );	
					DefaultLogger.error(this,"Total Diary Notification found :" );
					notificationDetail.setPartyId(item.getCustomerReference());
					notificationDetail.setPartyName(item.getCustomerName());
					notificationDetail.setFacilityLineNo(item.getFacilityLineNo());
					notificationDetail.setFacilitySerialNo(item.getFacilityLineNo());
					notificationDetail.setDescription(item.getDescription());
					notificationDetail.setSegment(item.getCustomerSegment());
					
					emailNotificationServiceobj.createNotification("NOT00024", notificationDetail);	
			}
			if(item.getDropLineOD().equals("Y") && "Closed".equals(item.getAction())) {
				  System.out.println("###########UpdateDiaryCommand.....FromPage"+map.get("frompage"));
				  if("list_non_expired".equals(map.get("frompage"))) {
					  DefaultLogger.debug(this," UpdateDiaryItemCommand.java Line no. 126." );
					  diaryItemJdbc.closeAllODitems(item.getDiaryNumber());
					  ICustomerNotificationDetail notificationDetail=new OBCustomerNotificationDetail();
						DefaultLogger.error(this,"Total Diary Notification found :" );
						notificationDetail.setPartyId(item.getCustomerReference());
						notificationDetail.setPartyName(item.getCustomerName());
						notificationDetail.setFacilityLineNo(item.getFacilityLineNo());
						notificationDetail.setFacilitySerialNo(item.getFacilityLineNo());
						notificationDetail.setDescription(item.getDescription());
						notificationDetail.setSegment(item.getCustomerSegment());
						emailNotificationServiceobj.createNotification("NOT00024", notificationDetail);
				  }
				  
			}
			}if("list_due_items".equals(map.get("frompage"))){
				if("N".equals(item.getDropLineOD())) {
					DefaultLogger.debug(this," UpdateDiaryItemCommand.java Line no. 142." );
				proxyMgr.updateDiaryItem(item);
				  ICustomerNotificationDetail notificationDetail=new OBCustomerNotificationDetail();
					DefaultLogger.error(this,"Total Diary Notification found :" );
					notificationDetail.setPartyId(item.getCustomerReference());
					notificationDetail.setPartyName(item.getCustomerName());
					notificationDetail.setFacilityLineNo(item.getFacilityLineNo());
					notificationDetail.setFacilitySerialNo(item.getFacilityLineNo());
					notificationDetail.setDescription(item.getDescription());
					notificationDetail.setSegment(item.getCustomerSegment());
					if("Extend".equals(item.getAction())) {
						emailNotificationServiceobj.createNotification("NOT00025", notificationDetail);
					}else if("Closed".equals(item.getAction())) {
						emailNotificationServiceobj.createNotification("NOT00024", notificationDetail);	
					}
					
				}
				if(item.getDropLineOD().equals("Y") && "Closed".equals(item.getAction())) {
					  System.out.println("###########UpdateDiaryCommand.....FromPage"+map.get("frompage"));
					  if("list_due_items".equals(map.get("frompage"))) {
						  DefaultLogger.debug(this," UpdateDiaryItemCommand.java Line no. 162." );
						  diaryItemJdbc.closeODitems(item.getDiaryNumber(),item.getMonth());
						  ICustomerNotificationDetail notificationDetail=new OBCustomerNotificationDetail();
							DefaultLogger.error(this,"Total Diary Notification found :" );
							notificationDetail.setPartyId(item.getCustomerReference());
							notificationDetail.setPartyName(item.getCustomerName());
							notificationDetail.setFacilityLineNo(item.getFacilityLineNo());
							notificationDetail.setFacilitySerialNo(item.getFacilityLineNo());
							notificationDetail.setDescription(item.getDescription());
							notificationDetail.setSegment(item.getCustomerSegment());
							notificationDetail.setClosingBalance(item.getClosingAmount());
							emailNotificationServiceobj.createNotification("NOT00026", notificationDetail);
					  }
					  String maxMonth = String.valueOf(diaryItemJdbc.getMaxMonthOfODScheduleDiary(item.getDiaryNumber()));
					  if(maxMonth.equals(item.getMonth().replace(".00", ""))) {
						  proxyMgr.updateDiaryItem(item);
					  }
					  
				}
				
			}
		}
		catch (ConcurrentUpdateException e) {
			DefaultLogger.error(this,"Exception in UpdateDiaryItemCommand.java Line no. 185." );
			throw new CommandProcessingException("concurrent update of diary item", e);
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
