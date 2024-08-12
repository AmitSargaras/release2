/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemValidator.java,v 1.10 2005/11/13 12:06:04 jtan Exp $
 */
package com.integrosys.cms.ui.diaryitem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.diary.bus.IDiaryItemJdbc;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.otherbankbranch.OtherBankValidator;

/**
 * This contains the validation logic for the Diary Item form bean handles
 * validation logic for Create and Update Events //CR-120 Added validation for
 * search also
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2005/11/13 12:06:04 $ Tag: $Name: $
 */

public class DiaryItemValidator {
	public static ActionErrors validateInput(DiaryItemForm aForm, Locale locale) {
		DefaultLogger.debug(DiaryItemValidator.class.getName(), "Entering method validateInput");
		ActionErrors errors = new ActionErrors();
		final String event = aForm.getEvent();

		Date d = DateUtil.getDate();
		d = DateUtil.clearTime(d);
		if (event.equals(DiaryItemAction.EVENT_CREATE)) {
			
			
			 
         
			/*if (isNull(aForm.getLeID())) {
				errors.add("leID", new ActionMessage("error.string.mandatory"));
			}
			else if((aForm.getLeID()!=null && !aForm.getLeID().trim().equals("")) 
					&& aForm.getLeID().length()>30)
			{
					errors.add("leID", new ActionMessage("error.id.length"));
					
			}
			
			else{
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(aForm.getLeID());
				if( codeFlag == true)
					errors.add("leID", new ActionMessage("error.string.invalidCharacter"));
			}*/

			if (isNull(aForm.getCustomerName())) {
				errors.add("customerName", new ActionMessage("error.string.mandatory"));
			}
			
			/*else if((aForm.getCustomerName()!=null && !aForm.getCustomerName().trim().equals("")) 
					&& aForm.getCustomerName().length()>50)
			{
					errors.add("customerName", new ActionMessage("error.name.length"));
					
			}
			else{
				boolean nameFlag = ASSTValidator.isValidGenericASST(aForm.getCustomerName());
				if( nameFlag == true)
					errors.add("customerName", new ActionMessage("error.string.invalidCharacter"));
			}*/
			

			boolean isCountryValid = Validator.validateMandatoryField(aForm.getAllowedCountry());
			//if (!isCountryValid) {
			//	errors.add("allowedCountry", new ActionMessage("error.mandatory"));
			//}

		}

		// Common validator logic for create and update events
		
		
		if (event.equals(DiaryItemAction.EVENT_CREATE)) {
			
			if (isNull(aForm.getFacilityBoardCategory())) {
				errors.add("facilityBoardCategory", new ActionMessage("error.string.mandatory"));
			}
			
			if (isNull(aForm.getFacilityLineNo())) {
				errors.add("facilityLineNo", new ActionMessage("error.string.mandatory"));
			}
			
			
			if (isNull(aForm.getFacilitySerialNo())) {
				errors.add("facilitySerialNo", new ActionMessage("error.string.mandatory"));
			}
			
			
			if (isNull(aForm.getActivity()) && aForm.getDropLineOD().equals("N")) {
				errors.add("activity", new ActionMessage("error.string.mandatory"));
			}
			if(aForm.getDropLineOD().equals("Y")) {
			if (isNull(aForm.getOdScheduleUploadFile().getFileName())) {
				errors.add("odScheduleUploadFile", new ActionMessage("error.string.mandatory"));
			}
			if(!isNull(aForm.getOdScheduleUploadFile().getFileName())) {
				if(!(aForm.getOdScheduleUploadFile().getFileName().contains(".xlsx"))){
					errors.add("odScheduleUploadFile", new ActionMessage("error.date.excelformat"));
				}
			}
			}
			
			/*if (isNull(aForm.getDescription())) {
				errors.add("description", new ActionMessage("error.string.mandatory"));
			}
			else if((aForm.getDescription()!=null && !aForm.getDescription().trim().equals("")) 
					&& aForm.getDescription().length()>100)
			{
					errors.add("description", new ActionMessage("error.description.length"));
					
			}*/
			if (isNull(aForm.getCustomerSegment())) {
				errors.add("segment", new ActionMessage("error.string.mandatory"));
			}

			String narration = aForm.getNarration();
			if (!isNull(narration) && (narration.length() > 200)) {
				errors.add("narration", new ActionMessage("error.string.narration.maxlen", "200"));
			}
			
			if(aForm.getDropLineOD().equals("N")) {
			if (isInvalidDate(aForm.getItemDueDate(), locale)) {
				errors.add("itemDueDate", new ActionMessage("error.date.format"));
			}
			}
			/*if (isInvalidDate(aForm.getItemExpiryDate(), locale)) {
				errors.add("itemExpiryDate", new ActionMessage("error.date.format"));
			}
*/
			try {
				Date dueDate = DateUtil.convertDate(locale, aForm.getItemDueDate());
				/*Date expiryDate = DateUtil.convertDate(locale, aForm.getItemExpiryDate());

				if ((dueDate != null) && (expiryDate != null)) {
					if (isDueDateAfterExpiry(dueDate, expiryDate)) {
						errors.add("itemDueDate", new ActionMessage("error.date.dueafterexpiry"));
					}
				}*/
				
				if(aForm.getDropLineOD().equals("N")) {
				if (d.after(DateUtil.clearTime(dueDate))) {
					errors.add("itemDueDate", new ActionMessage("error.date.dueaftercurrent"));
				}
				}
				
				/*if (d.after(DateUtil.clearTime(expiryDate))) {
					errors.add("itemExpiryDate", new ActionMessage("error.date.expiryaftercurrent"));
				}*/
			}
			catch (Exception e) {
				// Need not handle since errors trapped earlier
			}
		}

		
		if( event.equals(DiaryItemAction.EVENT_UPDATE)) {
			
			
			if (aForm.getDropLineOD().equals("Y") && "Closed".equals(aForm.getAction())) {
				 IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
					IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
					IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
					Date dateApplication=new Date();
					long ladGenIndicator=0l;
					for(int i=0;i<generalParamEntries.length;i++){
						if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
							dateApplication=new Date(generalParamEntries[i].getParamValue());
						}
					}
					SimpleDateFormat StringFormat = new SimpleDateFormat("dd/MM/yyyy");
					
					try {
						dateApplication = new SimpleDateFormat("dd/MM/yyyy").parse(StringFormat.format(dateApplication));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					
					
					IDiaryItemJdbc diaryItemJdbc=(IDiaryItemJdbc)BeanHouse.get("diaryItemJdbc");
					Map<String, String> map = diaryItemJdbc.getODTargetDateList(aForm.getDiaryNumber());
					if("list_non_expired".equals(aForm.getFrompage())){
					for (Map.Entry<String, String> entry : map.entrySet()) {
					    try {
					    	if(entry.getKey() != null && entry.getValue() != null) {
					    		Date reduceOnDate=new SimpleDateFormat("dd/MM/yyyy").parse(entry.getKey());
					    	if((reduceOnDate.equals(dateApplication)) && "N".equals(entry.getValue())) {
					    		errors.add("action", new ActionMessage("error.action.closeValidation"));
								break;
					    	}
							if(reduceOnDate.before(dateApplication) && "N".equals(entry.getValue())) {
								errors.add("action", new ActionMessage("error.action.closeValidation"));
								break;
							}
					    	}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					}
					
					
			}
			
			if (aForm.getDropLineOD().equals("N") && "Closed".equals(aForm.getAction()) && "list_non_expired".equals(aForm.getFrompage()) ) {

				 IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
					IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
					IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
					Date dateApplication=new Date();
					long ladGenIndicator=0l;
					for(int i=0;i<generalParamEntries.length;i++){
						if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
							dateApplication=new Date(generalParamEntries[i].getParamValue());
						}
					}
					SimpleDateFormat StringFormat = new SimpleDateFormat("dd/MM/yyyy");
					
					try {
						dateApplication = new SimpleDateFormat("dd/MM/yyyy").parse(StringFormat.format(dateApplication));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				if(DateUtil.convertDate(aForm.getItemDueDate()).equals(dateApplication)) {
					errors.add("action", new ActionMessage("error.action.closeValidation"));
				}
			}
			
			if (isNull(aForm.getAction())) {
				errors.add("action", new ActionMessage("error.string.mandatory"));
			}
			
			if (aForm.getDropLineOD().equals("N") && "Extend".equals(aForm.getAction())) {
				if (isInvalidDate(aForm.getNextTargetDate(), locale)) {
					errors.add("nextTargetDate", new ActionMessage("error.date.format"));
				}
			}
			
			try {
				Date dueDate = DateUtil.convertDate(locale, aForm.getNextTargetDate());
				if (aForm.getDropLineOD().equals("N") && "Extend".equals(aForm.getAction())) {
					if (d.after(DateUtil.clearTime(dueDate))) {
						errors.add("nextTargetDate", new ActionMessage("error.date.dueaftercurrent"));
					}
				}
			} catch (Exception e) {
				// Need not handle since errors trapped earlier
			}
		}
		if (event.equals(DiaryItemAction.EVENT_PRINT) || event.equals(DiaryItemAction.EVENT_REFRESH)) {

			/**
			 * skip validation if both dates are not entered
			 */
			if (isNull(aForm.getStartExpiryDate()) && isNull(aForm.getEndExpiryDate())) {
				return errors;
			}
			if (isInvalidDate(aForm.getStartExpiryDate(), locale)) {
				errors.add("startExpiryDate", new ActionMessage("error.date.format"));
			}
			if (isInvalidDate(aForm.getEndExpiryDate(), locale)) {
				errors.add("endExpiryDate", new ActionMessage("error.date.format"));
			}

			try {
				Date startExpDate = DateUtil.convertDate(locale, aForm.getStartExpiryDate());
				Date endExpDate = DateUtil.convertDate(locale, aForm.getEndExpiryDate());

				if ((startExpDate != null) && (endExpDate != null)) {
					if (isDueDateAfterExpiry(startExpDate, endExpDate)) {
						errors.add("startExpiryDate", new ActionMessage("error.date.startfterend"));
					}
				}
			}
			catch (Exception e) {
				// Need not handle since errors trapped earlier
			}
		}
		// CR-120 Search
		if (event.equals(DiaryItemAction.EVENT_LIST_DUE_ITEMS)
				|| event.equals(DiaryItemAction.EVENT_LIST_NON_EXPIRED_ITEMS)) {

			// skip validation if both are not entered
			if (isNull(aForm.getSearchCustomerName()) && isNull(aForm.getSearchLeID())) {
				return errors;
			}
             if((aForm.getSearchLeID())!=null && !aForm.getSearchLeID().trim().equals("")){ 
				 
				 boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(aForm.getSearchLeID());
					if( codeFlag == true)
					{
						errors.add("leIDError", new ActionMessage("error.string.invalidCharacter"));
				}
             }
              if((aForm.getSearchLeID()!=null && !aForm.getSearchLeID().trim().equals("")) 
 					&& aForm.getSearchLeID().length()>30)
 			{
 					errors.add("leIDError", new ActionMessage("error.id.length"));
 					
 			}
			String errorCode;
			if((aForm.getSearchCustomerName()!=null && !aForm.getSearchCustomerName().trim().equals("")) 
 					&& aForm.getSearchCustomerName().length()>50)
 			{
 					errors.add("customerNameError", new ActionMessage("error.name.length"));
 					
 			}
			if((aForm.getSearchCustomerName())!=null && !aForm.getSearchCustomerName().trim().equals("")){ 
				 
				 boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(aForm.getSearchCustomerName());
					if( codeFlag == true)
						errors.add("customerNameError", new ActionMessage("error.string.invalidCharacter"));
				}
			 

		}
		return errors;
	}
	

	private static boolean isInvalidDate(String s, Locale locale) {
		return !(Validator.checkDate(s, false, locale).equals(Validator.ERROR_NONE)) || isNull(s);
	}

	private static boolean isInvalidString(String s, int start, int end) {
		return !Validator.checkString(s, true, start, end).equals(Validator.ERROR_NONE);
	}

	private static boolean isDueDateAfterExpiry(Date due, Date expiry) {
		return due.after(expiry);
	}

	public static boolean isNull(String s) {
		return ((s == null) || (s.trim().length() == 0));
	}

}
